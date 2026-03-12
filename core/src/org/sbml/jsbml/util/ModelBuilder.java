/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2022 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 5. The Babraham Institute, Cambridge, UK
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.util;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.CVTerm;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.LocalParameter;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.ModifierSpeciesReference;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.Unit.Kind;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.text.parser.ParseException;
import org.sbml.jsbml.SBase;

/**
 * This class provides a collection of convenient methods to create SBML models
 * and documents.
 *
 * @author Andreas Dr&auml;ger
 * @since 1.0
 */
public class ModelBuilder {

  /**
   * Default time unit in constraint-based modeling (CBM), 3,600 seconds.
   */
  public static final String HOUR = "hour";
  /**
   * Typical size scale of a cell, hence needed as default volume unit in CBM.
   */
  public static final String F_L = "fL";
  /**
   * Millimoles per gram of dry weight of a probe, used as extend units of
   * reactions.
   */
  public static final String MMOL_PER_G_DW = "mmol_per_gDW";
  /**
   * Extend units per time unit, millimoles per gram of dry weight.
   */
  public static final String MMOL_PER_G_DW_PER_HR = "mmol_per_gDW_per_hr";

  /**
   * @param reaction
   * @param formula
   * @param localParameters
   * @return
   * @throws ParseException
   */
  public static KineticLaw buildKineticLaw(Reaction reaction, String formula, Pair<String, Double>... localParameters) throws ParseException {
    KineticLaw kl = reaction.createKineticLaw();
    kl.setMath(ASTNode.parseFormula(formula));
    if (localParameters != null) {
      for (Pair<String, Double> lp : localParameters) {
        LocalParameter parameter = kl.createLocalParameter();
        if (lp.isSetKey()) {
          parameter.setId(lp.getKey());
        }
        if (lp.isSetValue()) {
          parameter.setValue(lp.getValue());
        }
      }
    }
    return kl;
  }

  /**
   * A convenient method to create multiple modifiers in one single step by
   * passing the {@link Species} along with its role (SBO term) to this method.
   *
   * @param reaction
   *        The {@link Reaction} for which modifiers are to be created.
   * @param modifiers
   *        an array of {@link Species} along with an SBO term to be
   *        used to define the role of the modifier in this reaction.
   */
  public static void buildModifiers(Reaction reaction, Pair<Species, Integer>... modifiers) {
    if (modifiers != null) {
      for (Pair<Species, Integer> modifier : modifiers) {
        ModifierSpeciesReference msr = reaction.createModifier();
        if (modifier.isSetKey()) {
          msr.setSpecies(modifier.getKey());
        }
        if (modifier.isSetValue()) {
          msr.setSBOTerm(modifier.getValue());
        }
      }
    }
  }

  /**
   * This builds multiple reaction participants in one step and adds them to
   * the given list of {@link SpeciesReference}s.
   *
   * @param listOf
   *        where to add new {@link SpeciesReference}
   * @param participants
   *        the definition of stoichiometry and {@link Species} for which a
   *        reaction participant is to be created.
   */
  private static void buildParticipants(ListOf<SpeciesReference> listOf,
    Pair<Double, Species>... participants) {
    for (Pair<Double, Species> participant : participants) {
      SpeciesReference sr = new SpeciesReference(listOf.getLevel(), listOf.getVersion());
      if (participant.isSetKey()) {
        sr.setStoichiometry(participant.getKey().doubleValue());
      }
      if (participant.isSetValue()) {
        sr.setSpecies(participant.getValue());
      }
      if (listOf.getLevel() > 2) {
        sr.setConstant(true);
      }
      listOf.add(sr);
    }
  }

  /**
   * Convenient method to build multiple products together with their
   * stoichiometry in one step. If the level is three or beyond, the constant
   * attributes of the newly created products are set to {@code true} for
   * convenience (as this is a required attribute).
   *
   * @param reaction
   *        the {@link Reaction} for which products are to be created.
   * @param products
   *        the definition of stoichiometry and {@link Species} for which a
   *        product is to be created.
   */
  public static void buildProducts(Reaction reaction, Pair<Double, Species>... products) {
    if (products != null) {
      buildParticipants(reaction.getListOfProducts(), products);
    }
  }

  /**
   * Convenient method to build multiple reactants together with their
   * stoichiometry in one step. If the level is three or beyond, the constant
   * attributes of the newly created reactants are set to {@code true} for
   * convenience (as this is a required attribute).
   *
   * @param reaction
   *        the {@link Reaction} for which reactants are to be created.
   * @param reactants
   *        the definition of stoichiometry and {@link Species} for which a
   *        reactant is to be created.
   */
  @SafeVarargs
  public static void buildReactants(Reaction reaction, Pair<Double, Species>... reactants) {
    if (reactants != null) {
      buildParticipants(reaction.getListOfReactants(), reactants);
    }
  }

  /**
   *
   * @param parent
   * @param multiplier
   * @param scale
   * @param kind
   * @param exponent
   * @return
   */
  public static Unit buildUnit(UnitDefinition parent, double multiplier, int scale, Kind kind, double exponent) {
    Unit unit = parent.createUnit(kind);
    unit.setMultiplier(multiplier);
    unit.setScale(scale);
    unit.setExponent(exponent);
    return unit;
  }

  /**
   *
   */
  private SBMLDocument doc;

  /**
   * @param level
   * @param version
   *
   */
  public ModelBuilder(int level, int version) {
    this(new SBMLDocument(level, version));
  }

  /**
   *
   * @param doc
   */
  public ModelBuilder(SBMLDocument doc) {
    this.doc = doc;
  }

  /**
   * Creates the default units needed in a constraint-based modeling context
   */
  public void buildCBMunits() {
    // TODO: Localize!
    Unit h = buildUnit(3600d, 0, Unit.Kind.SECOND, 1d);
    Unit fL = buildUnit(1d, -3, Unit.Kind.LITRE, 1d);
    Unit mmol = buildUnit(1d, -3, Unit.Kind.MOLE, 1d);
    Unit perGDW = buildUnit(1d, 0, Unit.Kind.GRAM, -1d);

    mmol.addCVTerm(new CVTerm(CVTerm.Qualifier.BQB_IS, "https://identifiers.org/UO:0000040"));
    h.addCVTerm(new CVTerm(CVTerm.Qualifier.BQB_IS, "https://identifiers.org/UO:0000032"));
    fL.addCVTerm(new CVTerm(CVTerm.Qualifier.BQB_IS, "https://identifiers.org/UO:0000104"));

    UnitDefinition hour = buildUnitDefinition(HOUR, HOUR, h.clone());
    UnitDefinition femtoLitre = buildUnitDefinition(F_L, "femtolitre", fL.clone());
    Model m = getModel();
    m.setTimeUnits(hour.getId());
    m.setVolumeUnits(femtoLitre.getId());
    m.setExtentUnits(buildUnitDefinition(MMOL_PER_G_DW, "millimoles per gram dry weight", mmol.clone(), perGDW.clone()));
    m.setSubstanceUnits(m.getExtentUnits());
    h.setExponent(-1d);
    buildUnitDefinition(MMOL_PER_G_DW_PER_HR, "millimoles per gram dry weight per hour", mmol.clone(), perGDW.clone(), h);
  }

  /**
   *
   * @param id
   * @param constant
   * @param name
   * @param spatialDimensions
   * @param size
   * @param sizeUnits
   * @return
   */
  /**
   * Creates or returns a {@link Compartment} with the given identifier.
   * <p>
   * If a compartment with the given {@code id} already exists in the underlying
   * {@link Model}, this method returns that compartment unchanged and does not
   * apply the other arguments. If no such compartment exists, a new one is created
   * and the provided arguments are used to initialise its attributes.
   * <p>
   * If an element with the same {@code id} exists in the model but is not a
   * {@link Compartment}, an {@link IllegalArgumentException} is thrown.
   *
   * @param id
   *        the id of the compartment to create or return
   * @param constant
   *        value for {@code constant} if a new compartment is created
   * @param name
   *        value for {@code name} if a new compartment is created
   * @param spatialDimensions
   *        value for {@code spatialDimensions} if a new compartment is created
   * @param size
   *        value for {@code size} if a new compartment is created
   * @param sizeUnits
   *        units for {@code size} if a new compartment is created
   * @return the existing or newly created {@link Compartment}
   */
  public Compartment buildCompartment(String id, boolean constant, String name,
      double spatialDimensions, double size, String sizeUnits) {
    Model model = getModel();
    Compartment c = model.getCompartment(id);

    if (c != null) {
      return c;
    }

    if (id != null) {
      SBase existing = model.findNamedSBase(id);
      if (existing != null && !(existing instanceof Compartment)) {
        throw new IllegalArgumentException(
            "Element with id '" + id + "' already exists and is of type "
                + existing.getElementName() + ", not a compartment.");
      }
    }

    c = model.createCompartment(id);
    c.setConstant(constant);
    c.setName(name);
    c.setSpatialDimensions(spatialDimensions);
    c.setSize(size);
    if (sizeUnits != null) {
      c.setUnits(sizeUnits);
    }

    return c;
  }

  /**
   *
   * @param id
   * @param constant
   * @param name
   * @param spatialDimensions
   * @param size
   * @param sizeUnits
   * @return
   */
  public Compartment buildCompartment(String id, boolean constant, String name, double spatialDimensions, double size, Unit.Kind sizeUnits) {
    return buildCompartment(id, constant, name, spatialDimensions, size, sizeUnits.toString().toLowerCase());
  }

  /**
   *
   * @param id
   * @param constant
   * @param name
   * @param spatialDimensions
   * @param size
   * @param sizeUnits
   * @return
   */
  public Compartment buildCompartment(String id, boolean constant, String name, double spatialDimensions, double size, UnitDefinition sizeUnits) {
    return buildCompartment(id, constant, name, spatialDimensions, size, sizeUnits != null ? sizeUnits.getId() : (String) null);
  }

  /**
   * Creates or returns the {@link Model} associated with this {@link SBMLDocument}.
   * <p>
   * If the document already contains a model, this method returns that model unchanged
   * and does not alter its identifier. Otherwise, a new model is created with the
   * given {@code id}. If {@code name} is non-null, it is applied as the model's
   * name (only when the model is created or does not yet have a name).
   *
   * @param id
   *        the id to use if a new model is created
   * @param name
   *        an optional name to assign
   * @return the existing or newly created {@link Model}
   */
  public Model buildModel(String id, String name) {
    Model model;

    if (doc.isSetModel()) {
      model = doc.getModel();
    } else {
      model = doc.createModel(id);
    }

    if (name != null && !model.isSetName()) {
      model.setName(name);
    }

    return model;
  }

  /**
   *
   * @param id
   * @param name
   * @param value
   * @param constant
   * @param units
   * @return
   */
  /**
   * Creates or returns a {@link Parameter} with the given identifier.
   * <p>
   * If a parameter with the given {@code id} already exists in the underlying
   * {@link Model}, this method returns that parameter unchanged and does not
   * apply the other arguments. If no such parameter exists, a new one is
   * created and the provided arguments are used to initialise its attributes.
   * <p>
   * If an element with the same {@code id} exists in the model but is not a
   * {@link Parameter}, an {@link IllegalArgumentException} is thrown.
   */
  public Parameter buildParameter(String id, String name, double value,
      boolean constant, String units) {
    Model model = getModel();
    Parameter p = model.getParameter(id);

    if (p != null) {
      return p;
    }

    if (id != null) {
      SBase existing = model.findNamedSBase(id);
      if (existing != null && !(existing instanceof Parameter)) {
        throw new IllegalArgumentException(
            "Element with id '" + id + "' already exists and is of type "
                + existing.getElementName() + ", not a parameter.");
      }
    }

    p = model.createParameter(id);
    p.setName(name);
    p.setValue(value);
    p.setConstant(constant);
    p.setUnits(units);

    return p;
  }

  /**
   *
   * @param id
   * @param name
   * @param value
   * @param constant
   * @param units
   * @return
   */
  public Parameter buildParameter(String id, String name, double value, boolean constant, Unit.Kind units) {
    return buildParameter(id, name, value, constant, units.toString().toLowerCase());
  }

  /**
   *
   * @param id
   * @param name
   * @param value
   * @param constant
   * @param units
   * @return
   */
  public Parameter buildParameter(String id, String name, double value, boolean constant, UnitDefinition units) {
    return buildParameter(id, name, value, constant, units.getId());
  }

  /**
   *
   * @param id
   * @param name
   * @param compartment
   * @param fast
   * @param reversible
   * @return
   */
  public Reaction buildReaction(String id, String name, Compartment compartment, boolean fast, boolean reversible) {
    return buildReaction(id, name, compartment != null ? compartment.getId() : null, fast, reversible);
  }

  /**
   *
   * @param id
   * @param name
   * @param compartment
   * @param fast
   * @param reversible
   * @return
   */
  /**
   * Creates or returns a {@link Reaction} with the given identifier.
   * <p>
   * If a reaction with the given {@code id} already exists in the underlying
   * {@link Model}, this method returns that reaction unchanged and does not
   * apply the other arguments. If no such reaction exists, a new one is created
   * and the provided arguments are used to initialise its attributes.
   * <p>
   * If an element with the same {@code id} exists in the model but is not a
   * {@link Reaction}, an {@link IllegalArgumentException} is thrown.
   */
  public Reaction buildReaction(String id, String name, String compartment,
      boolean fast, boolean reversible) {
    Model model = getModel();
    Reaction r = model.getReaction(id);

    if (r != null) {
      return r;
    }

    if (id != null) {
      SBase existing = model.findNamedSBase(id);
      if (existing != null && !(existing instanceof Reaction)) {
        throw new IllegalArgumentException(
            "Element with id '" + id + "' already exists and is of type "
                + existing.getElementName() + ", not a reaction.");
      }
    }

    r = model.createReaction(id);
    r.setName(name);
    if (compartment != null) {
      r.setCompartment(compartment);
    }
    r.setFast(fast);
    r.setReversible(reversible);

    return r;
  }

  /**
   *
   * @param id
   * @param name
   * @param compartment
   * @param hasOnlySubstanceUnits
   * @param boundaryCondition
   * @param constant
   * @param initialConcentration
   * @param substanceUnits
   * @return
   */
  public Species buildSpecies(String id, String name,
    Compartment compartment, boolean hasOnlySubstanceUnits,
    boolean boundaryCondition, boolean constant, double initialConcentration,
    String substanceUnits) {
    return buildSpecies(id, name, compartment.getId(), hasOnlySubstanceUnits, boundaryCondition, constant, initialConcentration, substanceUnits);
  }

  /**
   *
   * @param id
   * @param name
   * @param compartment
   * @param hasOnlySubstanceUnits
   * @param boundaryCondition
   * @param constant
   * @param initialConcentration
   * @param substanceUnits
   * @return
   */
  public Species buildSpecies(String id, String name,
    Compartment compartment, boolean hasOnlySubstanceUnits,
    boolean boundaryCondition, boolean constant, double initialConcentration,
    Unit.Kind substanceUnits) {
    return buildSpecies(id, name, compartment, hasOnlySubstanceUnits, boundaryCondition, constant, initialConcentration, substanceUnits.toString().toLowerCase());
  }

  /**
   *
   * @param id
   * @param name
   * @param compartment
   * @param hasOnlySubstanceUnits
   * @param boundaryCondition
   * @param constant
   * @param initialConcentration
   * @param substanceUnits
   * @return
   */
  public Species buildSpecies(String id, String name,
    Compartment compartment, boolean hasOnlySubstanceUnits,
    boolean boundaryCondition, boolean constant, double initialConcentration,
    UnitDefinition substanceUnits) {
    return buildSpecies(id, name, compartment, hasOnlySubstanceUnits, boundaryCondition, constant, initialConcentration, substanceUnits.getId());
  }

  /**
   *
   * @param id
   * @param name
   * @param compartmentId
   * @param hasOnlySubstanceUnits
   * @param boundaryCondition
   * @param constant
   * @param initialConcentration
   * @param substanceUnits
   * @return
   */
  /**
   * Creates or returns a {@link Species} with the given identifier.
   * <p>
   * If a species with the given {@code id} already exists in the underlying
   * {@link Model}, this method returns that species unchanged and does not
   * apply the other arguments. If no such species exists, a new one is created
   * and the provided arguments are used to initialise its attributes.
   * <p>
   * If an element with the same {@code id} exists in the model but is not a
   * {@link Species}, an {@link IllegalArgumentException} is thrown.
   */
  public Species buildSpecies(String id, String name,
      String compartmentId, boolean hasOnlySubstanceUnits,
      boolean boundaryCondition, boolean constant, double initialConcentration,
      String substanceUnits) {
    Model model = getModel();
    Species s = model.getSpecies(id);

    if (s != null) {
      return s;
    }

    if (id != null) {
      SBase existing = model.findNamedSBase(id);
      if (existing != null && !(existing instanceof Species)) {
        throw new IllegalArgumentException(
            "Element with id '" + id + "' already exists and is of type "
                + existing.getElementName() + ", not a species.");
      }
    }

    s = model.createSpecies(id);
    s.setName(name);
    s.setCompartment(compartmentId);
    s.setHasOnlySubstanceUnits(hasOnlySubstanceUnits);
    s.setBoundaryCondition(boundaryCondition);
    s.setConstant(constant);
    s.setInitialConcentration(initialConcentration);
    s.setSubstanceUnits(substanceUnits);

    return s;
  }

  public Unit buildUnit(double multiplier, int scale, Kind kind, double exponent) {
    Unit unit = new Unit(doc.getLevel(), doc.getVersion());
    unit.setKind(kind);
    unit.setMultiplier(multiplier);
    unit.setScale(scale);
    unit.setExponent(exponent);
    return unit;
  }

  /**
   *
   * @param id
   * @param name
   * @return
   */
  /**
   * Creates or returns a {@link UnitDefinition} with the given identifier.
   * <p>
   * If a unit definition with the given {@code id} already exists in the
   * underlying {@link Model}, this method returns that unit definition unchanged
   * and does not apply the other arguments. If no such unit definition exists,
   * a new one is created and the provided arguments are used to initialise its
   * attributes.
   * <p>
   * Note that {@link UnitDefinition} objects live in their own namespace and
   * may have the same id as SId-based elements without causing clashes.
   *
   * @param id
   *        the id of the unit definition
   * @param name
   *        the optional name to assign
   * @param units
   *        optional units to add if a new definition is created
   * @return the existing or newly created {@link UnitDefinition}
   */
  public UnitDefinition buildUnitDefinition(String id, String name, Unit... units) {
    Model model = getModel();
    UnitDefinition ud = model.getUnitDefinition(id);

    if (ud == null) {
      ud = model.createUnitDefinition(id);
      ud.setName(name);
      if (units != null) {
        for (Unit unit : units) {
          ud.addUnit(unit);
        }
      }
    }

    return ud;
  }

  /**
   *
   * @return
   */
  public Model getModel() {
    return doc.isSetModel() ? doc.getModel() : doc.createModel();
  }

  /**
   * @return the doc
   */
  public SBMLDocument getSBMLDocument() {
    return doc;
  }

}
