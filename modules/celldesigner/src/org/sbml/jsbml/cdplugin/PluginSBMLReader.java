/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2013 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.cdplugin;

import static org.sbml.jsbml.cdplugin.CellDesignerConstants.LINK_TO_CELLDESIGNER;
import static org.sbml.jsbml.xml.libsbml.LibSBMLConstants.LINK_TO_LIBSBML;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.xml.stream.XMLStreamException;

import jp.sbi.celldesigner.plugin.PluginAlgebraicRule;
import jp.sbi.celldesigner.plugin.PluginAssignmentRule;
import jp.sbi.celldesigner.plugin.PluginCompartment;
import jp.sbi.celldesigner.plugin.PluginCompartmentType;
import jp.sbi.celldesigner.plugin.PluginConstraint;
import jp.sbi.celldesigner.plugin.PluginEvent;
import jp.sbi.celldesigner.plugin.PluginEventAssignment;
import jp.sbi.celldesigner.plugin.PluginFunctionDefinition;
import jp.sbi.celldesigner.plugin.PluginInitialAssignment;
import jp.sbi.celldesigner.plugin.PluginKineticLaw;
import jp.sbi.celldesigner.plugin.PluginModel;
import jp.sbi.celldesigner.plugin.PluginModifierSpeciesReference;
import jp.sbi.celldesigner.plugin.PluginParameter;
import jp.sbi.celldesigner.plugin.PluginRateRule;
import jp.sbi.celldesigner.plugin.PluginReaction;
import jp.sbi.celldesigner.plugin.PluginRule;
import jp.sbi.celldesigner.plugin.PluginSBase;
import jp.sbi.celldesigner.plugin.PluginSimpleSpeciesReference;
import jp.sbi.celldesigner.plugin.PluginSpecies;
import jp.sbi.celldesigner.plugin.PluginSpeciesAlias;
import jp.sbi.celldesigner.plugin.PluginSpeciesReference;
import jp.sbi.celldesigner.plugin.PluginSpeciesType;
import jp.sbi.celldesigner.plugin.PluginUnit;
import jp.sbi.celldesigner.plugin.PluginUnitDefinition;
import jp.sbi.celldesigner.plugin.util.PluginSpeciesSymbolType;

import org.apache.log4j.Logger;
import org.sbml.jsbml.AlgebraicRule;
import org.sbml.jsbml.AssignmentRule;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.CompartmentType;
import org.sbml.jsbml.Constraint;
import org.sbml.jsbml.Event;
import org.sbml.jsbml.EventAssignment;
import org.sbml.jsbml.ExplicitRule;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.InitialAssignment;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.LocalParameter;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.ModifierSpeciesReference;
import org.sbml.jsbml.NamedSBase;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.RateRule;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.Rule;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLInputConverter;
import org.sbml.jsbml.SBO;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.SpeciesType;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.util.TreeNodeChangeListener;
import org.sbml.jsbml.xml.libsbml.LibSBMLReader;
import org.sbml.jsbml.xml.libsbml.LibSBMLUtils;
import org.sbml.libsbml.XMLNode;
import org.sbml.libsbml.libsbml;

/**
 * 
 * @author Andreas Dr&auml;ger
 * @version $Rev$
 * @since 0.8
 */
@SuppressWarnings("deprecation")
public class PluginSBMLReader implements SBMLInputConverter<PluginModel> {

  /**
   * 
   */
  private static final int level = 2;
  /**
   * 
   */
  private static final int version = 4;
  /**
   * 
   */
  protected LinkedList<TreeNodeChangeListener> listOfTreeNodeChangeListeners;

  /**
   * A {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger(PluginSBMLReader.class);

  /**
   * 
   */
  private Model model;

  /**
   * 
   */
  private Set<Integer> possibleEnzymes;

  /**
   * get a model from the CellDesigner output, converts it to JSBML
   * format and stores it
   * 
   * @param model
   */
  public PluginSBMLReader(PluginModel model, Set<Integer> possibleEnzymes) {
    this(possibleEnzymes);
    this.model = convertModel(model);
  }

  /**
   * @return the model
   */
  public Model getModel() {
    return model;
  }

  /**
   * 
   */
  public PluginSBMLReader(Set<Integer> possibleEnzymes) {
    super();
    this.possibleEnzymes = possibleEnzymes;
    listOfTreeNodeChangeListeners = new LinkedList<TreeNodeChangeListener>();
  }

  /**
   * 
   * @param sb
   */
  public void addAllSBaseChangeListenersTo(SBase sb) {
    for (TreeNodeChangeListener listener : listOfTreeNodeChangeListeners) {
      sb.addTreeNodeChangeListener(listener);
    }
  }

  /**
   * 
   * @param c
   * @param pc
   */
  private void transferNamedSBaseProperties(NamedSBase sbase, PluginSBase pluginSBase) {
    transferSBaseProperties(sbase, pluginSBase);
    if (pluginSBase instanceof PluginCompartment) {
      PluginCompartment c = (PluginCompartment) pluginSBase;
      if (c.getId() != null && c.getId().length() > 0) {
        sbase.setId(c.getId());
      }
      if ((c.getName() != null) && (c.getName().length() > 0)) {
        sbase.setName(c.getName());
      }
    } else if (pluginSBase instanceof PluginCompartmentType) {
      PluginCompartmentType c = (PluginCompartmentType) pluginSBase;
      if ((c.getId() != null) && (c.getId().length() > 0)) {
        sbase.setId(c.getId());
      }
      if ((c.getName() != null) && (c.getName().length() > 0)) {
        sbase.setName(c.getName());
      }
    } else if (pluginSBase instanceof PluginEvent) {
      PluginEvent c = (PluginEvent) pluginSBase;
      if ((c.getId() != null) && (c.getId().length() > 0)) {
        sbase.setId(c.getId());
      }
      if ((c.getName() != null) && (c.getName().length() > 0)) {
        sbase.setName(c.getName());
      }
    } else if (pluginSBase instanceof PluginFunctionDefinition) {
      PluginFunctionDefinition c = (PluginFunctionDefinition) pluginSBase;
      if ((c.getId() != null) && (c.getId().length() > 0)) {
        sbase.setId(c.getId());
      }
      if ((c.getName() != null) && (c.getName().length() > 0)) {
        sbase.setName(c.getName());
      }
    } else if (pluginSBase instanceof PluginModel) {
      PluginModel c = (PluginModel) pluginSBase;
      if ((c.getId() != null) && (c.getId().length() > 0)) {
        sbase.setId(c.getId());
      }
      if ((c.getName() != null) && (c.getName().length() > 0)) {
        sbase.setName(c.getName());
      }
    } else if (pluginSBase instanceof PluginParameter) {
      PluginParameter c = (PluginParameter) pluginSBase;
      if ((c.getId() != null) && (c.getId().length() > 0)) {
        sbase.setId(c.getId());
      }
      if ((c.getName() != null) && (c.getName().length() > 0)) {
        sbase.setName(c.getName());
      }
    } else if (pluginSBase instanceof PluginReaction) {
      PluginReaction c = (PluginReaction) pluginSBase;
      if ((c.getId() != null) && (c.getId().length() > 0)) {
        sbase.setId(c.getId());
      }
      if ((c.getName() != null) && (c.getName().length() > 0)) {
        sbase.setName(c.getName());
      }
    } else if (pluginSBase instanceof PluginSimpleSpeciesReference) {
      //      PluginSimpleSpeciesReference c = (PluginSimpleSpeciesReference) pluginSBase;
      //      if ((c.getId() != null) && (c.getId().length() > 0)) {
      //        n.setId(c.getId());
      //      }
      //      if ((c.getName() != null) && (c.getName().length() > 0)) {
      //        sbase.setName(c.getName());
      //      }
    } else if (pluginSBase instanceof PluginSpecies) {
      PluginSpecies c = (PluginSpecies) pluginSBase;
      if ((c.getId() != null) && (c.getId().length() > 0)) {
        sbase.setId(c.getId());
      }
      if ((c.getName() != null) && (c.getName().length() > 0)) {
        sbase.setName(c.getName());
      }
    } else if (pluginSBase instanceof PluginSpeciesType) {
      PluginSpeciesType c = (PluginSpeciesType) pluginSBase;
      if ((c.getId() != null) && (c.getId().length() > 0)) {
        sbase.setId(c.getId());
      }
      if ((c.getName() != null) && (c.getName().length() > 0)) {
        sbase.setName(c.getName());
      }
    } else if (pluginSBase instanceof PluginUnitDefinition) {
      PluginUnitDefinition c = (PluginUnitDefinition) pluginSBase;
      if ((c.getId() != null) && (c.getId().length() > 0)) {
        sbase.setId(c.getId());
      }
      if ((c.getName() != null) && (c.getName().length() > 0)) {
        sbase.setName(c.getName());
      }
    }
  }

  /**
   * 
   * @param sbase
   * @param pluginSBase
   */
  private void transferSBaseProperties(SBase sbase, PluginSBase pluginSBase) {

    // Memorize the corresponding original element for each SBase:
    sbase.putUserObject(LINK_TO_CELLDESIGNER, pluginSBase);

    if (pluginSBase.getNotesString().length() > 0) {
      try {
        sbase.setNotes(pluginSBase.getNotesString());
      } catch (XMLStreamException exc) {
        logger.warn(exc.getLocalizedMessage() != null ? exc.getLocalizedMessage() : exc.getMessage(), exc);
      }
    }
    for (int i = 0; i < pluginSBase.getNumCVTerms(); i++) {
      sbase.addCVTerm(LibSBMLUtils.convertCVTerm(pluginSBase.getCVTerm(i)));
    }

    if (pluginSBase.getAnnotation() != null) {
      for (int i = 0; i < pluginSBase.getNumCVTerms(); i++) {
        sbase.addCVTerm(LibSBMLUtils.convertCVTerm(pluginSBase.getCVTerm(i)));
      }
      // Parse the XML annotation nodes that are non-RDF
      XMLNode annotation = pluginSBase.getAnnotation();
      StringBuilder sb = new StringBuilder();
      boolean newLine = false;
      for (long i = 0; i < annotation.getNumChildren(); i++) {
        String annot = annotation.getChild(i).toXMLString();
        if (!annot.trim().startsWith("<rdf:")) {
          if (newLine) {
            sb.append('\n');
          }
          sb.append(annot);
          newLine = true;
        }
      }
      if (sb.toString().trim().length() > 0) {
        try {
          sbase.getAnnotation().setNonRDFAnnotation(sb.toString());
        } catch (XMLStreamException exc) {
          logger.warn(exc.getLocalizedMessage() != null ? exc.getLocalizedMessage() : exc.getMessage(), exc);
        }
      }
      sbase.getAnnotation().putUserObject(LINK_TO_LIBSBML, pluginSBase.getAnnotation());
    }
    // no MetaId accessible.
  }

  /**
   * 
   * @return
   */
  public int getNumErrors() {
    return getErrorCount();
  }

  /**
   * 
   * @return
   */
  public int getErrorCount() {
    return 0;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBMLInputConverter#getOriginalModel()
   */
  @Override
  public PluginModel getOriginalModel() {
    return (PluginModel) model.getUserObject(LINK_TO_CELLDESIGNER);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBMLReader#getWarnings()
   */
  @Override
  public List<SBMLException> getWarnings() {
    return new LinkedList<SBMLException>();
  }

  /**
   * 
   * @param compartment
   * @return
   */
  private Compartment readCompartment(PluginCompartment compartment) {
    Compartment c = new Compartment(compartment.getId(), level, version);
    transferNamedSBaseProperties(c, compartment);
    if ((compartment.getOutside() != null) && (compartment.getOutside().length() > 0)) {
      Compartment outside = model.getCompartment(compartment.getOutside());
      if (outside == null) {
        outside = readCompartment(((PluginModel) model.getUserObject(LINK_TO_CELLDESIGNER)).getCompartment(compartment.getOutside()));
        model.addCompartment(outside);
      }
      c.setOutside(outside);
    }
    if ((compartment.getCompartmentType() != null)
        && (compartment.getCompartmentType().length() > 0)) {
      c.setCompartmentType(compartment.getCompartmentType());
    }
    c.setConstant(compartment.getConstant());
    c.setSize(compartment.getSize());
    c.setSpatialDimensions((short) compartment.getSpatialDimensions());
    if (compartment.getUnits().length() > 0) {
      c.setUnits(compartment.getUnits());
    }
    return c;
  }

  /**
   * 
   * @param compartmentType
   * @return
   */
  private CompartmentType readCompartmentType(PluginCompartmentType compartmentType) {
    CompartmentType com = new CompartmentType(compartmentType.getId(), level, version);
    transferNamedSBaseProperties(com, compartmentType);
    return com;
  }

  /**
   * 
   * @param constraint
   * @return
   */
  private Constraint readConstraint(PluginConstraint constraint) {
    Constraint c = new Constraint(level, version);
    transferSBaseProperties(c, constraint);
    if (constraint.getMath() != null) {
      c.setMath(LibSBMLUtils.convert(libsbml.parseFormula(constraint.getMath()), c));
    }
    if (constraint.getMessage().length() > 0) {
      try {
        c.setMessage(constraint.getMessage());
      } catch (XMLStreamException exc) {
        logger.warn(exc.getLocalizedMessage() != null ? exc.getLocalizedMessage() : exc.getMessage(), exc);
      }
    }
    return c;
  }

  /**
   * 
   * @param event
   * @return
   */
  private Event readEvent(PluginEvent event) {
    Event e = new Event(level, version);
    transferNamedSBaseProperties(e, event);
    if (event.getTrigger() != null) {
      e.setTrigger(LibSBMLReader.readTrigger(event.getTrigger()));
    }
    if (event.getDelay() != null) {
      e.setDelay(LibSBMLReader.readDelay(event.getDelay()));
    }
    for (int i = 0; i < event.getNumEventAssignments(); i++) {
      e.addEventAssignment(readEventAssignment(event.getEventAssignment(i)));
    }
    if ((event.getTimeUnits() != null) && (event.getTimeUnits().length() > 0)) {
      e.setTimeUnits(event.getTimeUnits());
    }
    e.setUseValuesFromTriggerTime(event.getUseValuesFromTriggerTime());
    return e;
  }

  /**
   * 
   * @param eventass
   * @return
   */
  private EventAssignment readEventAssignment(PluginEventAssignment eventass) {
    EventAssignment ev = new EventAssignment(level, version);
    transferSBaseProperties(ev, eventass);
    if ((eventass.getVariable() != null)
        && (eventass.getVariable().length() > 0)) {
      ev.setVariable(eventass.getVariable());
    }
    if (eventass.getMath() != null) {
      ev.setMath(LibSBMLUtils.convert(eventass.getMath(), ev));
    }
    return ev;
  }

  /**
   * 
   * @param functionDefinition
   * @return
   */
  private FunctionDefinition readFunctionDefinition(PluginFunctionDefinition functionDefinition) {
    FunctionDefinition f = new FunctionDefinition(level, version);
    transferNamedSBaseProperties(f, functionDefinition);
    if (functionDefinition.getMath() != null) {
      f.setMath(LibSBMLUtils.convert(functionDefinition.getMath(), f));
    }
    return f;
  }

  /**
   * 
   * @param initialAssignment
   * @return
   */
  private InitialAssignment readInitialAssignment(PluginInitialAssignment initialAssignment) {
    if (initialAssignment.getSymbol() == null) {
      throw new IllegalArgumentException(
          "Symbol attribute not set for InitialAssignment");
    }
    InitialAssignment ia = new InitialAssignment(level, version);
    ia.setVariable(initialAssignment.getSymbol());
    transferSBaseProperties(ia, initialAssignment);
    if (initialAssignment.getMath() != null) {
      ia.setMath(LibSBMLUtils.convert(libsbml.parseFormula(initialAssignment.getMath()), ia));
    }
    return ia;
  }

  /**
   * 
   * @param kineticLaw
   * @return
   */
  private KineticLaw readKineticLaw(PluginKineticLaw kineticLaw) {
    KineticLaw kinlaw = new KineticLaw(level, version);
    transferSBaseProperties(kinlaw, kineticLaw);
    for (int i = 0; i < kineticLaw.getNumParameters(); i++) {
      kinlaw.addLocalParameter(readLocalParameter(kineticLaw.getParameter(i)));
    }
    if (kineticLaw.getMath() != null) {
      kinlaw.setMath(LibSBMLUtils.convert(kineticLaw.getMath(), kinlaw));
    } else if (kineticLaw.getFormula().length() > 0) {
      kinlaw.setMath(LibSBMLUtils.convert(libsbml.readMathMLFromString(kineticLaw.getFormula()), kinlaw));
    }
    return kinlaw;
  }

  /**
   * 
   * @param parameter
   * @return
   */
  private LocalParameter readLocalParameter(PluginParameter parameter) {
    return new LocalParameter(readParameter(parameter));
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBMLReader#readModel(java.lang.Object)
   */
  @Override
  public Model convertModel(PluginModel originalModel) {

    model = new Model(originalModel.getId(), level, version);
    transferNamedSBaseProperties(model, originalModel);

    int i;
    for (i = 0; i < originalModel.getNumUnitDefinitions(); i++) {
      model.addUnitDefinition(readUnitDefinition(originalModel.getUnitDefinition(i)));
    }
    for (i = 0; i < originalModel.getNumFunctionDefinitions(); i++) {
      model.addFunctionDefinition(readFunctionDefinition(originalModel.getFunctionDefinition(i)));
    }
    for (i = 0; i < originalModel.getNumCompartmentTypes(); i++) {
      model.addCompartmentType(readCompartmentType(originalModel.getCompartmentType(i)));
    }
    for (i = 0; i < originalModel.getNumSpeciesTypes(); i++) {
      model.addSpeciesType(readSpeciesType(originalModel.getSpeciesType(i)));
    }
    for (i = 0; i < originalModel.getNumCompartments(); i++) {
      model.addCompartment(readCompartment(originalModel.getCompartment(i)));
    }
    for (i = 0; i < originalModel.getNumSpecies(); i++) {
      model.addSpecies(readSpecies(originalModel.getSpecies(i)));
    }
    for (i = 0; i < originalModel.getNumParameters(); i++) {
      model.addParameter(readParameter(originalModel.getParameter(i)));
    }
    for (i = 0; i < originalModel.getNumInitialAssignments(); i++) {
      model.addInitialAssignment(readInitialAssignment(originalModel.getInitialAssignment(i)));
    }
    for (i = 0; i < originalModel.getNumRules(); i++) {
      model.addRule(readRule(originalModel.getRule(i)));
    }
    for (i = 0; i < originalModel.getNumConstraints(); i++) {
      model.addConstraint(readConstraint(originalModel.getConstraint(i)));
    }
    for (i = 0; i < originalModel.getNumReactions(); i++) {
      model.addReaction(readReaction(originalModel.getReaction(i)));
    }
    for (i = 0; i < originalModel.getNumEvents(); i++) {
      model.addEvent(readEvent(originalModel.getEvent(i)));
    }
    addAllSBaseChangeListenersTo(model);
    return model;
  }

  /**
   * 
   * @param modifierSpeciesReference
   * @return
   */
  private ModifierSpeciesReference readModifierSpeciesReference(
    PluginModifierSpeciesReference modifierSpeciesReference) {
    ModifierSpeciesReference mod = new ModifierSpeciesReference(level, version);
    mod.setSpecies(modifierSpeciesReference.getSpecies());
    transferNamedSBaseProperties(mod, modifierSpeciesReference);
    /*
     * Set SBO term.
     */
    mod.setSBOTerm(SBO.convertAlias2SBO(modifierSpeciesReference.getModificationType()));
    if (SBO.isCatalyst(mod.getSBOTerm())) {
      PluginSpecies species = modifierSpeciesReference.getSpeciesInstance();
      String speciesAliasType = species.getSpeciesAlias(0).getType()
          .equals("PROTEIN") ? species.getSpeciesAlias(0)
            .getProtein().getType() : species.getSpeciesAlias(0)
            .getType();
            int sbo = SBO.convertAlias2SBO(speciesAliasType);
            if (possibleEnzymes.contains(Integer.valueOf(sbo))) {
              mod.setSBOTerm(SBO.getEnzymaticCatalysis());
            }
    }
    return mod;
  }

  /**
   * 
   * @param parameter
   * @return
   */
  private Parameter readParameter(PluginParameter parameter) {
    Parameter para = new Parameter(level, version);
    transferNamedSBaseProperties(para, parameter);
    para.setValue(parameter.getValue());
    para.setConstant(parameter.getConstant());
    if ((parameter.getUnits() != null) && (parameter.getUnits().length() > 0)) {
      para.setUnits(parameter.getUnits());
    }
    return para;
  }

  /**
   * 
   * @param reac
   * @return
   */
  private Reaction readReaction(PluginReaction reac) {
    Reaction reaction = new Reaction(reac.getId(), level, version);
    transferNamedSBaseProperties(reaction, reac);
    for (int i = 0; i < reac.getNumReactants(); i++) {
      reaction.addReactant(readSpeciesReference(reac.getReactant(i)));
    }
    for (int i = 0; i < reac.getNumProducts(); i++) {
      reaction.addProduct(readSpeciesReference(reac.getProduct(i)));
    }
    for (int i = 0; i < reac.getNumModifiers(); i++) {
      reaction.addModifier(readModifierSpeciesReference(reac.getModifier(i)));
    }
    reaction.setFast(reac.getFast());
    reaction.setReversible(reac.getReversible());
    int sbo = SBO.convertAlias2SBO(reac.getReactionType());
    if (SBO.checkTerm(sbo)) {
      reaction.setSBOTerm(sbo);
    }
    if (reac.getKineticLaw() != null) {
      reaction.setKineticLaw(readKineticLaw(reac.getKineticLaw()));
    }
    return reaction;
  }

  /**
   * 
   * @param rule
   * @return
   */
  private Rule readRule(PluginRule rule) {
    Rule r;
    if (rule instanceof PluginAlgebraicRule) {
      r = new AlgebraicRule(level, version);
    } else {
      String variable = null;
      if (rule instanceof PluginAssignmentRule) {
        r = new AssignmentRule(level, version);
        variable = ((PluginAssignmentRule) rule).getVariable();
      } else {
        r = new RateRule(level, version);
        variable = ((PluginRateRule) rule).getVariable();
      }
      ExplicitRule er = (ExplicitRule) r;
      er.setVariable(variable);
    }
    transferSBaseProperties(r, rule);
    if (rule.getMath() != null) {
      r.setMath(LibSBMLUtils.convert(rule.getMath(), r));
    }
    return r;
  }

  /**
   * 
   * @param species
   * @return
   */
  private Species readSpecies(PluginSpecies species) {
    Species s = new Species(level, version);
    transferNamedSBaseProperties(s, species);
    int sbo = SBO.convertAlias2SBO(species.getSpeciesAlias(0).getType());
    PluginSpeciesAlias alias = species.getSpeciesAlias(0);
    String type = alias.getType();
    if (alias.getType().equals(PluginSpeciesSymbolType.PROTEIN)) {
      type = alias.getProtein().getType();
    }
    sbo = SBO.convertAlias2SBO(type);
    if (SBO.checkTerm(sbo)) {
      s.setSBOTerm(sbo);
    }
    s.setCharge(species.getCharge());
    if ((species.getCompartment() != null) && (species.getCompartment().length() > 0)) {
      s.setCompartment(model.getCompartment(species.getCompartment()));
    }
    s.setBoundaryCondition(species.getBoundaryCondition());
    s.setConstant(species.getConstant());
    s.setHasOnlySubstanceUnits(species.getHasOnlySubstanceUnits());
    if (species.isSetInitialAmount()) {
      s.setInitialAmount(species.getInitialAmount());
    } else if (species.isSetInitialConcentration()) {
      s.setInitialConcentration(species.getInitialConcentration());
    }
    // before L2V3...
    if ((species.getSpatialSizeUnits() != null) && (species.getSpatialSizeUnits().length() > 0)) {
      s.setSpatialSizeUnits(species.getSpatialSizeUnits());
    }
    if (species.getSubstanceUnits().length() > 0) {
      s.setSubstanceUnits(Unit.Kind.valueOf(species.getSubstanceUnits()));
    }
    if (species.getSpeciesType().length() > 0) {
      s.setSpeciesType(model.getSpeciesType(species.getSpeciesType()));
    }
    return s;
  }

  /**
   * 
   * @param speciesReference
   * @return
   */
  private SpeciesReference readSpeciesReference(PluginSpeciesReference speciesReference) {
    SpeciesReference spec = new SpeciesReference(level, version);
    transferNamedSBaseProperties(spec, speciesReference);
    if (speciesReference.getStoichiometryMath() == null) {
      spec.setStoichiometry(speciesReference.getStoichiometry());
    } else {
      spec.setStoichiometryMath(LibSBMLReader.readStoichiometricMath(speciesReference.getStoichiometryMath()));
    }
    if (speciesReference.getReferenceType().length() > 0) {
      int sbo = SBO.convertAlias2SBO(speciesReference.getReferenceType());
      if (SBO.checkTerm(sbo)) {
        spec.setSBOTerm(sbo);
      }
    }
    spec.setConstant(true);
    spec.setSpecies(speciesReference.getSpecies());
    return spec;
  }

  /**
   * 
   * @param speciesType
   * @return
   */
  private SpeciesType readSpeciesType(PluginSpeciesType speciesType) {
    SpeciesType st = new SpeciesType(level, version);
    transferNamedSBaseProperties(st, speciesType);
    return st;
  }

  /**
   * 
   * @param unit
   * @return
   */
  private Unit readUnit(PluginUnit unit) {
    Unit u = new Unit(level, version);
    transferSBaseProperties(u, unit);
    u.setKind(LibSBMLUtils.convertUnitKind(unit.getKind()));
    u.setExponent(unit.getExponent());
    u.setMultiplier(unit.getMultiplier());
    u.setScale(unit.getScale());
    if (u.isSetOffset()) {
      u.setOffset(unit.getOffset());
    }
    return u;
  }

  /**
   * 
   * @param unitDefinition
   * @return
   */
  private UnitDefinition readUnitDefinition(PluginUnitDefinition unitDefinition) {
    UnitDefinition ud = new UnitDefinition(level, version);
    transferNamedSBaseProperties(ud, unitDefinition);
    for (int i = 0; i < unitDefinition.getNumUnits(); i++) {
      ud.addUnit(readUnit(unitDefinition.getUnit(i)));
    }
    return ud;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBMLInputConverter#convertSBMLDocument(java.lang.String)
   */
  @Override
  public SBMLDocument convertSBMLDocument(String fileName) throws Exception {
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBMLInputConverter#convertSBMLDocument(java.io.File)
   */
  @Override
  public SBMLDocument convertSBMLDocument(File sbmlFile) throws Exception {
    return null;
  }

}
