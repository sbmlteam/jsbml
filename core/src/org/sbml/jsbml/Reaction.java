/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2018 jointly by the following organizations:
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
package org.sbml.jsbml;

import java.text.MessageFormat;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.util.TreeNodeChangeEvent;
import org.sbml.jsbml.util.filters.NameFilter;
import org.sbml.jsbml.util.filters.SpeciesReferenceFilter;

/**
 * Represents the reaction XML element of a SBML file.
 * 
 * @author Andreas Dr&auml;ger
 * @author Nicolas Rodriguez
 * @author Marine Dumousseau
 * @since 0.8
 */
public class Reaction extends AbstractNamedSBase
implements CallableSBase, CompartmentalizedSBase, UniqueNamedSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 1385417662249487643L;
  /**
   * Represents the 'compartment' XML attribute of a reaction element.
   */
  private String compartmentID;
  /**
   * Represents the 'fast' XML attribute of a reaction element.
   */
  @Deprecated
  private Boolean fast;
  /**
   * Checks whether the {@link #fast} attribute has been set by using a default or
   * by changing its value.
   */
  private boolean isSetFast = false;
  /**
   * Checks whether the {@link #reversible} attribute has been set by using a default or
   * by changing its value.
   */
  private boolean isSetReversible = false;
  /**
   * Represents the 'kineticLaw' XML subNode of a reaction element.
   */
  private KineticLaw kineticLaw;
  /**
   * Represents the 'listOfModifiers' XML subNode of a reaction element.
   */
  private ListOf<ModifierSpeciesReference> listOfModifiers;
  /**
   * Represents the 'listOfProducts' XML subNode of a reaction element.
   */
  private ListOf<SpeciesReference> listOfProducts;
  /**
   * Represents the 'listOfReactants' XML subNode of a reaction element.
   */
  private ListOf<SpeciesReference> listOfReactants;
  /**
   * Represents the 'reversible' XML attribute of a reaction element.
   */
  private Boolean reversible;

  /**
   * Creates a Reaction instance. By default, the compartmentID, kineticLaw,
   * {@link #listOfReactants}, {@link #listOfProducts} and
   * {@link #listOfModifiers} are empty.
   */
  public Reaction() {
    super();
    initDefaults();
  }

  /**
   * Creates a new Reaction instance from a given SBML level and version.
   * 
   * @param level the SBML level
   * @param version the SBML version
   */
  public Reaction(int level, int version) {
    super(level, version);
    initDefaults();
  }

  /**
   * Creates a Reaction instance from a given reaction.
   * 
   * @param reaction
   */
  public Reaction(Reaction reaction) {
    super(reaction);

    if (reaction.isSetFast()) {
      setFast(new Boolean(reaction.getFast()));
    } else {
      fast = reaction.fast == null ? null : new Boolean(reaction.fast.booleanValue());
    }
    if (reaction.isSetKineticLaw()) {
      setKineticLaw(reaction.getKineticLaw().clone());
    }
    if (reaction.isSetListOfReactants()) {
      listOfReactants = reaction
          .getListOfReactants().clone();
      registerChild(listOfReactants);
    }
    if (reaction.isSetListOfProducts()) {
      listOfProducts = reaction
          .getListOfProducts().clone();
      registerChild(listOfProducts);
    }
    if (reaction.isSetListOfModifiers()) {
      listOfModifiers = reaction
          .getListOfModifiers().clone();
      registerChild(listOfModifiers);
    }
    if (reaction.isSetReversible()) {
      setReversible(reaction.getReversible());
    } else {
      reversible = reaction.reversible == null ? null : new Boolean(reaction.reversible.booleanValue());
    }
    if (reaction.isSetCompartment()) {
      setCompartment(reaction.getCompartment());
    }
  }

  /**
   * 
   * @param id
   */
  public Reaction(String id) {
    super(id);
    initDefaults();
  }

  /**
   * Creates a {@link Reaction} instance from an id, level and version. By default,
   * the compartmentID, {@link #kineticLaw}, {@link #listOfReactants}, {@link #listOfProducts} and
   * {@link #listOfModifiers} are empty.
   * 
   * @param id
   * @param level
   * @param version
   */
  public Reaction(String id, int level, int version) {
    super(id, level, version);
    initDefaults();
  }

  /**
   * Adds a {@link ModifierSpeciesReference} instance to this {@link Reaction}.
   * 
   * @param modspecref
   * @return {@code true} if the {@link #listOfModifiers} was
   *         changed as a result of this call.
   */
  public boolean addModifier(ModifierSpeciesReference modspecref) {
    return getListOfModifiers().add(modspecref);
  }

  /**
   * Adds a {@link SpeciesReference} instance to the listOfProducts of this {@link Reaction}.
   * 
   * @param specref
   * @return {@code true} if the {@link #listOfProducts} was
   *         changed as a result of this call.
   */
  public boolean addProduct(SpeciesReference specref) {
    return getListOfProducts().add(specref);
  }

  /**
   * Adds a {@link SpeciesReference} instance to the listOfReactants of this {@link Reaction}.
   * 
   * @param specref
   * @return {@code true} if the {@link #listOfReactants} was
   *         changed as a result of this call.
   */
  public boolean addReactant(SpeciesReference specref) {
    return getListOfReactants().add(specref);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.element.SBase#clone()
   */
  @Override
  public Reaction clone() {
    return new Reaction(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBaseWithDerivedUnit#containsUndeclaredUnits()
   */
  @Override
  public boolean containsUndeclaredUnits() {
    return isSetKineticLaw() ? getKineticLaw().containsUndeclaredUnits()
      : false;
  }

  /**
   * Creates a new {@link KineticLaw} object, installs it as this
   * {@link Reaction}'s 'kineticLaw' sub-element, and returns it.
   * 
   * If this {@link Reaction} had a previous KineticLaw, it will be destroyed.
   * 
   * @return the new {@link KineticLaw} object
   */
  public KineticLaw createKineticLaw() {
    KineticLaw kl = new KineticLaw(this);
    return kl;
  }

  /**
   * Creates a new {@link ModifierSpeciesReference}, adds it to this
   * {@link Reaction}'s list of modifiers and returns it.
   * 
   * @return a new {@link ModifierSpeciesReference} object.
   */
  public ModifierSpeciesReference createModifier() {
    return createModifier((String) null);
  }

  /**
   * Creates a new {@link ModifierSpeciesReference}, which points to the given
   * {@link Species}, adds it to this {@link Reaction}'s
   * {@link #listOfModifiers}, and returns a pointer to it.
   * 
   * @param species
   *            the {@link Species} to which this modifier should point.
   * @return
   * @see #createModifier(String, Species)
   */
  public ModifierSpeciesReference createModifier(Species species) {
    return createModifier(null, species);
  }

  /**
   * 
   * @param id
   * @return
   */
  public ModifierSpeciesReference createModifier(String id) {
    ModifierSpeciesReference modifier = new ModifierSpeciesReference(id,
      getLevel(), getVersion());
    addModifier(modifier);
    return modifier;
  }

  /**
   * Creates a new {@link ModifierSpeciesReference} with the given
   * {@code id} as its identifier, which points to the given
   * {@link Species}, adds it to this {@link Reaction}'s
   * {@link #listOfModifiers}, and returns a pointer to it.
   * 
   * @param id
   *            the identifier of the {@link ModifierSpeciesReference} to be
   *            created.
   * @param species
   *            the {@link Species} to which this modifier should point.
   * @return a pointer to the newly created {@link ModifierSpeciesReference}.
   */
  public ModifierSpeciesReference createModifier(String id, Species species) {
    ModifierSpeciesReference modSpecRef = createModifier(id);
    modSpecRef.setSpecies(species);
    return modSpecRef;
  }

  /**
   * Creates a new {@link ModifierSpeciesReference} with the given
   * {@code id} as its identifier, which points to the {@link Species}
   * with the given {@code id}, adds it to this {@link Reaction}'s
   * {@link #listOfModifiers}, and returns a pointer to it.
   * 
   * @param id
   *            the identifier of the {@link ModifierSpeciesReference} to be
   *            created.
   * @param species
   *            the identifier of the {@link Species} to be referenced.
   * @return a pointer to the newly created {@link ModifierSpeciesReference}.
   */
  public ModifierSpeciesReference createModifier(String id, String species) {
    ModifierSpeciesReference modSpecRef = createModifier(id);
    modSpecRef.setSpecies(species);
    return modSpecRef;
  }

  /**
   * Creates a new {@link SpeciesReference}, adds it to this {@link Reaction}'s
   * {@link #listOfProducts} and returns it.
   * 
   * @return a new {@link SpeciesReference} object.
   */
  public SpeciesReference createProduct() {
    return createProduct((String) null);
  }

  /**
   * Creates a new {@link SpeciesReference}, which points to the given
   * {@link Species}, adds it to this {@link Reaction}'s
   * {@link #listOfProducts} and returns a pointer to it.
   * 
   * @param species
   *            the {@link Species} to which the {@link SpeciesReference}
   *            should point.
   * @return a pointer to a newly created {@link SpeciesReference} that has
   *         been added to this {@link Reaction}'s {@link #listOfProducts}.
   * @see #createProduct(String, Species)
   */
  public SpeciesReference createProduct(Species species) {
    return createProduct(null, species);
  }

  /**
   * 
   * @param id
   * @return
   */
  public SpeciesReference createProduct(String id) {
    SpeciesReference product = new SpeciesReference(id, getLevel(),
      getVersion());
    addProduct(product);
    return product;
  }

  /**
   * Creates a new {@link SpeciesReference} with the given {@code id} as
   * its identifier, which points to the given {@link Species}, adds it to
   * this {@link Reaction}'s {@link #listOfProducts} and returns a pointer to
   * it.
   * 
   * @param id
   *            the identifier of the {@link SpeciesReference} to be created.
   * @param species
   *            the {@link Species} to which the {@link SpeciesReference}
   *            should point.
   * @return a pointer to a newly created {@link SpeciesReference} that has
   *         been added to this {@link Reaction}'s {@link #listOfProducts}.
   */
  public SpeciesReference createProduct(String id, Species species) {
    SpeciesReference specRef = createProduct(id);
    specRef.setSpecies(species);
    return specRef;
  }

  /**
   * 
   * @param id
   * @param species
   * @return
   */
  public SpeciesReference createProduct(String id, String species) {
    SpeciesReference specRef = createProduct(id);
    specRef.setSpecies(species);
    return specRef;
  }

  /**
   * Creates a new {@link SpeciesReference}, adds it to this {@link Reaction}'s
   * {@link #listOfReactants} and returns it.
   * 
   * @return a new SpeciesReference object.
   */
  public SpeciesReference createReactant() {
    return createReactant((String) null);
  }

  /**
   * Creates a new {@link SpeciesReference} to the given {@link Species} and
   * adds it to the {@link #listOfReactants}.
   * 
   * @param species
   * @return the newly created instance of {@link SpeciesReference} that
   *         points to the given {@link Species}.
   * @see #createReactant(String, Species)
   */
  public SpeciesReference createReactant(Species species) {
    return createReactant(null, species);
  }

  /**
   * Creates a new {@link SpeciesReference} with the given identifier and adds
   * it to this {@link Reaction}'s {@link #listOfReactants}.
   * 
   * @param id
   *        the id of the {@link SpeciesReference} that is to be created.
   * @return a new instance of {@link SpeciesReference}, which is already linked
   *         to this {@link Reaction}.
   */
  public SpeciesReference createReactant(String id) {
    SpeciesReference reactant = new SpeciesReference(id, getLevel(),
      getVersion());
    addReactant(reactant);
    return reactant;
  }

  /**
   * Creates a new {@link SpeciesReference} with the given {@code id} as
   * identifier, which points to the given {@link Species} and adds it to the
   * {@link #listOfReactants}.
   * 
   * @param id
   *            the identifier of the {@link SpeciesReference} to be created.
   * @param species
   *            the {@link Species} to which this reactant should point.
   * @return a pointer to the newly created {@link SpeciesReference}.
   */
  public SpeciesReference createReactant(String id, Species species) {
    SpeciesReference specRef = createReactant(id);
    specRef.setSpecies(species);
    return specRef;
  }

  /**
   * 
   * @param id
   * @param species
   * @return
   */
  public SpeciesReference createReactant(String id, String species) {
    SpeciesReference specRef = createReactant(id);
    specRef.setSpecies(species);
    return specRef;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    // Check all child elements recursively in super class first:
    boolean equals = super.equals(object);
    if (equals) {
      // Cast is possible because super class checks the class attributes
      Reaction r = (Reaction) object;
      equals &= r.isSetFast() == isSetFast();
      if (equals && isSetFast()) {
        equals &= r.fast.equals(fast);
      }
      equals &= r.isSetReversible() == isSetReversible();
      if (equals && isSetReversible()) {
        equals &= r.reversible.equals(reversible);
      }
      equals &= r.getCompartment().equals(getCompartment());
    }
    return equals;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
   */
  @Override
  public TreeNode getChildAt(int index) {
    if (index < 0) {
      throw new IndexOutOfBoundsException(MessageFormat.format(
        resourceBundle.getString("IndexSurpassesBoundsException"), index, 0));
    }
    int count = super.getChildCount(), pos = 0;
    if (index < count) {
      return super.getChildAt(index);
    } else {
      index -= count;
    }
    if (listOfReactants != null) {
      if (pos == index) {
        return getListOfReactants();
      }
      pos++;
    }
    if (listOfProducts != null) {
      if (pos == index) {
        return getListOfProducts();
      }
      pos++;
    }
    if (listOfModifiers != null) {
      if (pos == index) {
        return getListOfModifiers();
      }
      pos++;
    }
    if (isSetKineticLaw()) {
      if (pos == index) {
        return getKineticLaw();
      }
      pos++;
    }
    throw new IndexOutOfBoundsException(MessageFormat.format(
      resourceBundle.getString("IndexExceedsBoundsException"),
      index, Math.min(pos, 0)));
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildCount()
   */
  @Override
  public int getChildCount() {
    int children = super.getChildCount();
    if (listOfReactants != null) {
      children++;
    }
    if (listOfProducts != null) {
      children++;
    }
    if (listOfModifiers != null) {
      children++;
    }
    if (isSetKineticLaw()) {
      children++;
    }
    return children;
  }

  /**
   * Only available if Level &gt;= 3.
   * 
   * @return the compartmentID of this {@link Reaction}. The empty
   *         {@link String} if it is not set.
   */
  @Override
  public String getCompartment() {
    return isSetCompartment() ? compartmentID : "";
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.CompartmentalizedSBase#getCompartmentInstance()
   */
  @Override
  public Compartment getCompartmentInstance() {
    if (isSetCompartment()) {
      Model m = getModel();
      if (m != null) {
        return m.getCompartment(compartmentID);
      }
    }
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBaseWithDerivedUnit#getDerivedUnitDefinition()
   */
  @Override
  public UnitDefinition getDerivedUnitDefinition() {
    return isSetKineticLaw() ? kineticLaw.getDerivedUnitDefinition() : null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBaseWithDerivedUnit#getDerivedUnits()
   */
  @Override
  public String getDerivedUnits() {
    return isSetKineticLaw() ? kineticLaw.getDerivedUnits() : null;
  }

  /**
   * Returns the fast Boolean of this Reaction.
   * 
   * <p>In SBML Level 3 Version 2, the fast attribute has been removed: every Reaction in a Level 3 Version 2 Core
   * model is equivalent to an SBML Level 3 Version 1 Reaction with a fast value of 'false'. This means that
   * for Level 3 Version 2 Core, the speed of every Reaction will always be determined by its KineticLaw. To
   * achieve the same or similar effects as setting the fast attribute to 'true' in a previous version of SBML,
   * the {@link KineticLaw} should be constructed to produce a value in the desired time scale, or the reaction can be
   * replaced with an {@link AssignmentRule} or {@link AlgebraicRule} object as in the example of Section 7.5 on p. 122
   * of the SBML Level 3 Version 2 specification.
   * 
   * @return the fast Boolean of this Reaction.
   * @throws PropertyNotAvailableException if used for SBML L3V2 or above.
   */
  @Deprecated
  public boolean getFast() {
    
    if (!isReadingInProgress() && getLevelAndVersion().compareTo(3, 2) >= 0) {
      throw new PropertyNotAvailableException("fast", this);
    }
    
    // Not using the isSetFast here to allow the value set in initDefaults() to
    // be returned.
    return fast != null ? fast : false;
  }

  /**
   * 
   * @return the kineticLaw of this Reaction. Can be null if not set.
   */
  public KineticLaw getKineticLaw() {
    return kineticLaw;
  }

  /**
   * 
   * @return the listOfModifiers of this Reaction. Is initialized here if not
   *         yet set.
   */
  public ListOf<ModifierSpeciesReference> getListOfModifiers() {
    if (listOfModifiers == null) {
      listOfModifiers = ListOf.newInstance(this, ModifierSpeciesReference.class);
      registerChild(listOfModifiers);
    }
    return listOfModifiers;
  }

  /**
   * 
   * @return the listOfProducts of this Reaction. Is initialized here if not
   *         yet set.
   */
  public ListOf<SpeciesReference> getListOfProducts() {
    if (listOfProducts == null) {
      listOfProducts = ListOf.initListOf(this,
        new ListOf<SpeciesReference>(), ListOf.Type.listOfProducts);
      registerChild(listOfProducts);
    }
    return listOfProducts;
  }

  /**
   * 
   * @return the listOfReactants of this Reaction. Is initialized here if not
   *         yet set.
   */
  public ListOf<SpeciesReference> getListOfReactants() {
    if (listOfReactants == null) {
      listOfReactants = ListOf.initListOf(this, new ListOf<SpeciesReference>(),
        ListOf.Type.listOfReactants);
      registerChild(listOfReactants);
    }
    return listOfReactants;
  }

  /**
   * 
   * @param i
   * @return the ith ModifierSpeciesReference of the listOfModifiers. Can be
   *         null if it doesn't exist.
   */
  public ModifierSpeciesReference getModifier(int i) {
    return getListOfModifiers().get(i);
  }

  /**
   * Searches the first {@link ModifierSpeciesReference} in the
   * {@link #listOfModifiers} of this {@link Reaction} with the given
   * identifier.
   * 
   * @param id
   *            identifier of the desired {@link ModifierSpeciesReference}.
   *            Note that this is not the identifier of the {@link Species}.
   * @return the {@link ModifierSpeciesReference} of the
   *         {@link #listOfModifiers} which has 'id' as id (or name depending
   *         on the level and version). Can be null if it doesn't exist.
   */
  public ModifierSpeciesReference getModifier(String id) {
    if (isSetListOfModifiers()) {
      return getListOfModifiers().firstHit(new NameFilter(id));
    }

    return null;
  }

  /**
   * 
   * @return the number of {@link ModifierSpeciesReference}s of this
   *         {@link Reaction}.
   */
  public int getModifierCount() {
    return listOfModifiers == null ? 0 : listOfModifiers.size();
  }

  /**
   * Returns the first {@link ModifierSpeciesReference} in the
   * {@link #listOfModifiers} of this {@link Reaction} whose 'species'
   * attribute points to a {@link Species} with the given identifier.
   * 
   * @param id
   *            The identifier of a referenced {@link Species}
   * @return the {@link ModifierSpeciesReference} of the
   *         {@link #listOfModifiers} which has 'id' as species attribute (or
   *         name depending on the level and version). Can be null if it
   *         doesn't exist.
   */
  public ModifierSpeciesReference getModifierForSpecies(String id) {
    if (isSetListOfModifiers()) {
      SpeciesReferenceFilter srf = new SpeciesReferenceFilter(id);
      srf.setFilterForSpecies(true);
      return getListOfModifiers().firstHit(srf);
    }

    return null;
  }

  /**
   * @return the number of {@link ModifierSpeciesReference}s of this
   *         {@link Reaction}.
   * @libsbml.deprecated you could use {@link #getModifierCount()}
   */
  public int getNumModifiers() {
    return getModifierCount();
  }

  /**
   * 
   * @return the number of products {@link SpeciesReference}.
   * @libsbml.deprecated you could use {@link #getProductCount()}
   */
  public int getNumProducts() {
    return getProductCount();
  }

  /**
   * 
   * @return the number of reactants {@link SpeciesReference}.
   * @libsbml.deprecated you could use {@link #getReactantCount()}
   */
  public int getNumReactants() {
    return getReactantCount();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getParent()
   */
  @SuppressWarnings("unchecked")
  @Override
  public ListOf<Reaction> getParent() {
    return (ListOf<Reaction>) super.getParent();
  }

  /**
   * 
   * @param i
   * @return the ith product SpeciesReference of the listOfProducts. Can be
   *         null if it doesn't exist.
   */
  public SpeciesReference getProduct(int i) {
    return getListOfProducts().get(i);
  }

  /**
   * Searches the first {@link SpeciesReference} in the listOfProducts of this
   * {@link Reaction} with the given identifier.
   * 
   * @param id
   *            identifier of the desired {@link SpeciesReference}. Note that
   *            this is not the identifier of the {@link Species}.
   * @return the {@link SpeciesReference} of the {@link #listOfProducts} which has 'id' as id
   *         (or name depending on the level and version). Can be null if it
   *         doesn't exist.
   */
  public SpeciesReference getProduct(String id) {
    if (isSetListOfProducts()) {
      return getListOfProducts().firstHit(new NameFilter(id));
    }
    
    return null;
  }

  /**
   * 
   * @return the number of products {@link SpeciesReference}.
   */
  public int getProductCount() {
    return listOfProducts == null ? 0 : listOfProducts.size();
  }

  /**
   * Returns the first {@link SpeciesReference} in the {@link #listOfProducts}
   * of this {@link Reaction} whose 'species' attribute points to a
   * {@link Species} with the given identifier.
   * 
   * @param id
   *            The identifier of a referenced {@link Species}
   * @return the {@link SpeciesReference} of the {@link #listOfProducts} which
   *         has 'id' as species attribute (or name depending on the level and
   *         version). Can be null if it doesn't exist.
   */
  public SpeciesReference getProductForSpecies(String id) {
    if (isSetListOfProducts()) {
      SpeciesReferenceFilter srf = new SpeciesReferenceFilter(id);
      srf.setFilterForSpecies(true);
      return getListOfProducts().firstHit(srf);
    }
    
    return null;
  }

  /**
   * 
   * @param i
   * @return the ith reactant {@link SpeciesReference} of the listOfReactants.
   *         Can be null if it doesn't exist.
   */
  public SpeciesReference getReactant(int i) {
    return getListOfReactants().get(i);
  }

  /**
   * Searches the first {@link SpeciesReference} in the listOfReactants of
   * this {@link Reaction} with the given identifier.
   * 
   * @param id
   *            identifier of the desired {@link SpeciesReference}. Note that
   *            this is not the identifier of the {@link Species}.
   * @return the {@link SpeciesReference} of the listOfReactants which has
   *         'id' as id (or name depending on the level and version). Can be
   *         null if it doesn't exist.
   */
  public SpeciesReference getReactant(String id) {
    if (isSetListOfReactants()) {
      return getListOfReactants().firstHit(new NameFilter(id));
    }
    
    return null;
  }

  /**
   * 
   * @return the number of reactants {@link SpeciesReference}.
   */
  public int getReactantCount() {
    return listOfReactants == null ? 0 : listOfReactants.size();
  }

  /**
   * Returns the first {@link SpeciesReference} in the
   * {@link #listOfReactants} of this {@link Reaction} whose 'species'
   * attribute points to a {@link Species} with the given identifier.
   * 
   * @param id
   *            The identifier of a referenced {@link Species}
   * @return the {@link SpeciesReference} of the {@link #listOfReactants}
   *         which has 'id' as species attribute (or name depending on the
   *         level and version). Can be null if it doesn't exist.
   */
  public SpeciesReference getReactantForSpecies(String id) {
    if (isSetListOfReactants()) {
      SpeciesReferenceFilter srf = new SpeciesReferenceFilter(id);
      srf.setFilterForSpecies(true);
      return getListOfReactants().firstHit(srf);
    }
    
    return null;
  }

  /**
   * 
   * @return the reversible Boolean of this reaction.
   */
  // Not using the isSetReversible here to allow the value set in
  // initDefaults() to be returned.
  public boolean getReversible() {
    return reversible != null ? reversible : true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 881;
    int hashCode = super.hashCode();
    if (isSetFast()) {
      hashCode += prime * fast.hashCode();
    }
    if (isSetReversible()) {
      hashCode += prime * reversible.hashCode();
    }
    if (isSetCompartment()) {
      hashCode += prime * getCompartment().hashCode();
    }
    return hashCode;
  }

  /**
   * 
   * @param s
   * @return
   */
  public boolean hasModifier(Species s) {
    return references(listOfModifiers, s);
  }

  /**
   * 
   * @param s
   * @return
   */
  public boolean hasProduct(Species s) {
    return references(listOfProducts, s);
  }

  /**
   * 
   * @param s
   * @return
   */
  public boolean hasReactant(Species s) {
    return references(listOfReactants, s);
  }

  /**
   * Initializes the default values using the current Level/Version configuration.
   */
  public void initDefaults() {
    initDefaults(getLevel(), getVersion());
  }

  /**
   * Initializes the default variables of this Reaction.
   * @param level
   * @param version
   */
  public void initDefaults(int level, int version) {
    initDefaults(level, version, false);
  }

  /**
   * 
   * @param level
   * @param version
   * @param explicit
   */
  public void initDefaults(int level, int version, boolean explicit) {
    if ((0 < level) && (0 < version)) {
      if (level < 3) {
        reversible = new Boolean(true);
        fast = new Boolean(false);
        isSetReversible = isSetFast = explicit;
      } else {
        reversible = fast = null;
      }
    }
  }

  /**
   * Convenient test if the given species takes part in this reaction as a
   * reactant, product, or modifier.
   * 
   * @param s
   * @return
   */
  public boolean involves(Species s) {
    return hasReactant(s) || hasProduct(s) || hasModifier(s);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.CompartmentalizedSBase#isCompartmentMandatory()
   */
  @Override
  public boolean isCompartmentMandatory() {
    return false;
  }

  /**
   *  Returns the boolean value of fast if it is set, {@code false} otherwise.
   *  
   * <p>In SBML Level 3 Version 2, the fast attribute has been removed: every Reaction in a Level 3 Version 2 Core
   * model is equivalent to an SBML Level 3 Version 1 Reaction with a fast value of 'false'. This means that
   * for Level 3 Version 2 Core, the speed of every Reaction will always be determined by its KineticLaw. To
   * achieve the same or similar effects as setting the fast attribute to 'true' in a previous version of SBML,
   * the {@link KineticLaw} should be constructed to produce a value in the desired time scale, or the reaction can be
   * replaced with an {@link AssignmentRule} or {@link AlgebraicRule} object as in the example of Section 7.5 on p. 122
   * of the SBML Level 3 Version 2 specification.
   * 
   * @return the boolean value of fast if it is set, {@code false} otherwise.
   * @see #getFast()
   * @throws PropertyNotAvailableException if used for SBML L3V2 or above.
   */
  @Deprecated
  public boolean isFast() {
    return getFast();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
   */
  @Override
  public boolean isIdMandatory() {
    return true;
  }

  /**
   * Returns {@code true} if the listOfModifiers of this Reaction is empty.
   * 
   * @return {@code true} if the listOfModifiers of this Reaction is empty.
   */
  public boolean isListOfModifiersEmpty() {
    return (listOfModifiers != null) && (listOfModifiers.size() == 0);
  }

  /**
   * Returns {@code true} if the listOfProducts of this reaction is empty.
   * 
   * @return {@code true} if the listOfProducts of this reaction is empty.
   */
  public boolean isListOfProductsEmpty() {
    return (listOfProducts != null) && (listOfProducts.size() == 0);
  }

  /**
   * Returns {@code true} if the listOfReactants of this Reaction is empty.
   * 
   * @return {@code true} if the listOfReactants of this Reaction is empty.
   */
  public boolean isListOfReactantsEmpty() {
    return (listOfReactants != null) && (listOfReactants.size() == 0);
  }


  /**
   * 
   * @return the value of reversible if it is set, {@code false} otherwise.
   */
  public boolean isReversible() {
    return getReversible();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.CompartmentalizedSBase#isSetCompartment()
   */
  @Override
  public boolean isSetCompartment() {
    return compartmentID != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.CompartmentalizedSBase#isSetCompartmentInstance()
   */
  @Override
  public boolean isSetCompartmentInstance() {
    return getCompartmentInstance() != null;
  }

  /**
   * 
   * @return {@code true} if fast is not {@code null}.
   */
  public boolean isSetFast() {
    return isSetFast;
  }

  /**
   * 
   * @return {@code true} if the kineticLaw of this Reaction is not {@code null}.
   */
  public boolean isSetKineticLaw() {
    return kineticLaw != null;
  }

  /**
   * 
   * @return {@code true} if the listOfModifiers of this Reaction is not {@code true} and not
   *         empty.
   */
  public boolean isSetListOfModifiers() {
    return (listOfModifiers != null) && (listOfModifiers.size() > 0);
  }

  /**
   * 
   * @return {@code true} if the listOfProducts of this reaction is not {@code true} and not
   *         empty.
   */
  public boolean isSetListOfProducts() {
    return (listOfProducts != null) && (listOfProducts.size() > 0);
  }

  /**
   * 
   * @return {@code true} if the listOfReactants of this Reaction is not {@code true} and not
   *         empty.
   */
  public boolean isSetListOfReactants() {
    return (listOfReactants != null) && (listOfReactants.size() > 0);
  }

  /**
   * 
   * @return {@code true} if reversible is not {@code null}.
   */
  public boolean isSetReversible() {
    return isSetReversible;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.element.SBase#readAttribute(String attributeName, String prefix, String value)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix,
    String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix,
      value);
    if (!isAttributeRead) {
      isAttributeRead = true;

      if (attributeName.equals(TreeNodeChangeEvent.reversible)) {
        setReversible(StringTools.parseSBMLBoolean(value));
      } else if (attributeName.equals(TreeNodeChangeEvent.fast)) {
        setFast(StringTools.parseSBMLBoolean(value));
      } else if (attributeName.equals(TreeNodeChangeEvent.compartment)) {
        this.setCompartment(value);
      } else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }

  /**
   * Checks whether the given list references the given species.
   * 
   * @param list
   * @param s
   * @return
   */
  private boolean references(ListOf<? extends SimpleSpeciesReference> list,
    Species s) {
    if (list != null) {
      return list.firstHit(new SpeciesReferenceFilter(s)) != null;
    }
    return false;
  }

  /**
   * 
   * @param list
   * @param id
   * @return
   */
  private <T extends SimpleSpeciesReference> T remove(
    ListOf<T> list, String id) {
    T deleted = null;
    int index = 0;
    if ((list != null) && (list.size() > 0)) {
      for (T reference : list) {
        if (reference.getSpecies().equals(id)) {
          deleted = reference;
          break;
        }
        index++;
      }
      if (deleted != null) {
        list.remove(index);
      }
    }
    return deleted;
  }

  /**
   * Removes the nth modifier species (ModifierSpeciesReference object) in the
   * list of modifiers in this Reaction and returns it.
   * 
   * @param i
   *            the index of the ModifierSpeciesReference object to remove.
   * @return the removed ModifierSpeciesReference object, or null if the given
   *         index is out of range.
   */
  public ModifierSpeciesReference removeModifier(int i) {
    return getListOfModifiers().remove(i);
  }

  /**
   * Removes the ModifierSpeciesReference 'modspecref' from this Reaction.
   * 
   * @param modspecref
   * @return
   */
  public boolean removeModifier(ModifierSpeciesReference modspecref) {
    return getListOfModifiers().remove(modspecref);
  }

  /**
   * Removes the modifier species (ModifierSpeciesReference object) having the
   * given 'species' attribute in the list of modifiers in this Reaction and
   * returns it.
   * 
   * @param id
   *            the 'species' attribute of the ModifierSpeciesReference object
   *            (which correspond to a species id).
   * @return
   */
  public ModifierSpeciesReference removeModifier(String id) {
    return remove(listOfModifiers, id);
  }

  /**
   * Removes the nth product species (SpeciesReference object) in the list of
   * products in this Reaction and returns it.
   * 
   * @param i
   *            the index of the SpeciesReference object to remove.
   * @return the removed SpeciesReference object, or null if the given index
   *         is out of range.
   */
  public SpeciesReference removeProduct(int i) {
    return listOfProducts.remove(i);
  }

  /**
   * Removes the SpeciesReference 'modspecref' from the listOfProducts of this
   * Reaction.
   * 
   * @param specref
   */
  public void removeProduct(SpeciesReference specref) {
    listOfProducts.remove(specref);
  }

  /**
   * Removes the product species (SpeciesReference object) having the given
   * 'species' attribute in the list of products in this Reaction and returns
   * it.
   * 
   * @param id
   *            the 'species' attribute of the SpeciesReference object (which
   *            correspond to a species id).
   * @return
   */
  public SpeciesReference removeProduct(String id) {
    return remove(listOfProducts, id);
  }

  /**
   * Removes the nth reactant species (SpeciesReference object) in the list of
   * reactants in this Reaction and returns it.
   * 
   * @param i
   *            the index of the SpeciesReference object to remove.
   * @return the removed SpeciesReference object, or null if the given index
   *         is out of range.
   */
  public SpeciesReference removeReactant(int i) {
    return getListOfReactants().remove(i);
  }

  /**
   * Removes the SpeciesReference 'modspecref' from the listOfReactants of
   * this Reaction.
   * 
   * @param specref
   * @return
   */
  public boolean removeReactant(SpeciesReference specref) {
    return getListOfReactants().remove(specref);
  }

  /**
   * Removes the reactant species (SpeciesReference object) having the given
   * 'species' attribute in the list of reactants in this Reaction and returns
   * it.
   * 
   * @param id
   *            the 'species' attribute of the SpeciesReference object (which
   *            correspond to a species id).
   * @return
   */
  public SpeciesReference removeReactant(String id) {
    return remove(listOfReactants, id);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.CompartmentalizedSBase#setCompartment(org.sbml.jsbml.Compartment)
   */
  @Override
  public boolean setCompartment(Compartment compartment) {
    return setCompartment(compartment != null ? compartment.getId() : null);
  }

  /**
   * Sets the compartmentID of this Reaction to 'compartmentID'. This method
   * is only available for Level &gt;= 3.
   * 
   * @param compartmentID
   * @throws PropertyNotAvailableException
   *             if Level &lt; 3.
   */
  @Override
  public boolean setCompartment(String compartmentID) {
    if (getLevel() < 3) {
      throw new PropertyNotAvailableException(TreeNodeChangeEvent.compartment,
        this);
    }
    if (compartmentID != this.compartmentID) {
      String oldCompartmentID = this.compartmentID;
      this.compartmentID = compartmentID;
      firePropertyChange(TreeNodeChangeEvent.compartment, oldCompartmentID,
        compartmentID);
      return true;
    }
    return false;
  }

  /**
   * Sets the fast Boolean of this {@link Reaction}.
   * 
   * <p>In SBML Level 3 Version 2, the fast attribute has been removed: every Reaction in a Level 3 Version 2 Core
   * model is equivalent to an SBML Level 3 Version 1 Reaction with a fast value of 'false'. This means that
   * for Level 3 Version 2 Core, the speed of every Reaction will always be determined by its KineticLaw. To
   * achieve the same or similar effects as setting the fast attribute to 'true' in a previous version of SBML,
   * the {@link KineticLaw} should be constructed to produce a value in the desired time scale, or the reaction can be
   * replaced with an {@link AssignmentRule} or {@link AlgebraicRule} object as in the example of Section 7.5 on p. 122
   * of the SBML Level 3 Version 2 specification.
   * 
   * @param fast - the value to set for the fast attribute
   * @throws PropertyNotAvailableException if used for SBML L3V2 or above.
   */
  @Deprecated
  public void setFast(boolean fast) {
    
    if (!isReadingInProgress() && getLevelAndVersion().compareTo(3, 2) >= 0) {
      throw new PropertyNotAvailableException("fast", this);
    }
    
    Boolean oldFast = this.fast;
    this.fast = Boolean.valueOf(fast);
    isSetFast = true;
    firePropertyChange(TreeNodeChangeEvent.fast, oldFast, fast);
  }

  /**
   * Sets the kineticLaw of this {@link Reaction}.
   * 
   * @param kineticLaw
   */
  public void setKineticLaw(KineticLaw kineticLaw) {
    unsetKineticLaw();
    this.kineticLaw = kineticLaw;
    registerChild(this.kineticLaw);
  }

  /**
   * Sets the listOfModifiers of this {@link Reaction}. Automatically sets the
   * parentSBML object of the list to this {@link Reaction} instance.
   * 
   * @param listOfModifiers
   */
  public void setListOfModifiers(ListOf<ModifierSpeciesReference> listOfModifiers) {
    unsetListOfModifiers();
    this.listOfModifiers = listOfModifiers;
    if ((this.listOfModifiers != null) && (this.listOfModifiers.getSBaseListType() != ListOf.Type.listOfModifiers)) {
      this.listOfModifiers.setSBaseListType(ListOf.Type.listOfModifiers);
    }
    registerChild(this.listOfModifiers);
  }

  /**
   * Sets the {@link #listOfProducts} of this {@link Reaction}. Automatically sets the
   * parentSBML object of the list to this {@link Reaction} instance.
   * 
   * @param listOfProducts
   */
  public void setListOfProducts(ListOf<SpeciesReference> listOfProducts) {
    unsetListOfProducts();
    this.listOfProducts = listOfProducts;
    if ((this.listOfProducts != null) && (this.listOfProducts.getSBaseListType() != ListOf.Type.listOfProducts)) {
      this.listOfProducts.setSBaseListType(ListOf.Type.listOfProducts);
    }
    registerChild(this.listOfProducts);
  }

  /**
   * Sets the listOfReactants of this {@link Reaction}. Automatically sets the
   * parentSBML object of the list to this {@link Reaction} instance.
   * 
   * @param listOfReactants
   */
  public void setListOfReactants(ListOf<SpeciesReference> listOfReactants) {
    unsetListOfReactants();
    this.listOfReactants = listOfReactants;
    if ((this.listOfReactants != null) && (this.listOfReactants.getSBaseListType() != ListOf.Type.listOfReactants)) {
      this.listOfReactants.setSBaseListType(ListOf.Type.listOfReactants);
    }
    registerChild(this.listOfReactants);
  }

  /**
   * Sets the reversible Boolean of this {@link Reaction}.
   * 
   * @param reversible
   */
  public void setReversible(boolean reversible) {
    Boolean oldReversible = this.reversible;
    this.reversible = Boolean.valueOf(reversible);
    isSetReversible = true;
    firePropertyChange(TreeNodeChangeEvent.reversible, oldReversible, reversible);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.CompartmentalizedSBase#unsetCompartment()
   */
  @Override
  public boolean unsetCompartment() {
    if (getLevel() > 2) {
      // Avoid exception when unsetting a compartment attribute.
      return setCompartment((String) null);
    }
    return false;
  }

  /**
   * Sets the fast Boolean of this Reaction to {@code null}.
   */
  @Deprecated
  public void unsetFast() {
    Boolean oldFast = fast;
    isSetFast = false;
    fast = null;
    firePropertyChange(TreeNodeChangeEvent.fast, oldFast, fast);
  }

  /**
   * Sets the {@link KineticLaw} of this {@link Reaction} to null and notifies
   * all {@link TreeNodeChangeListener} about changes.
   * 
   * @return {@code true} if calling this method changed the properties
   *         of this element.
   */
  public boolean unsetKineticLaw() {
    if (kineticLaw != null) {
      KineticLaw oldKinticLaw = kineticLaw;
      kineticLaw = null;
      oldKinticLaw.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /**
   * Removes the {@link #listOfModifiers} from this {@link Reaction} and notifies
   * all registered instances of {@link TreeNodeChangeListener}.
   * 
   * @return {@code true} if calling this method lead to a change in this
   *         data structure.
   */
  public boolean unsetListOfModifiers() {
    if (listOfModifiers != null) {
      ListOf<ModifierSpeciesReference> oldListOfModifiers = listOfModifiers;
      listOfModifiers = null;
      oldListOfModifiers.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /**
   * Removes the {@link #listOfProducts} from this {@link Reaction} and notifies
   * all registered instances of {@link TreeNodeChangeListener}.
   * 
   * @return {@code true} if calling this method lead to a change in this
   *         data structure.
   */
  public boolean unsetListOfProducts() {
    if (listOfProducts != null) {
      ListOf<SpeciesReference> oldListOfProducts = listOfProducts;
      listOfProducts = null;
      oldListOfProducts.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /**
   * Removes the {@link #listOfReactants} from this {@link Reaction} and notifies
   * all registered instances of {@link TreeNodeChangeListener}.
   * 
   * @return {@code true} if calling this method lead to a change in this
   *         data structure.
   */
  public boolean unsetListOfReactants() {
    if (listOfReactants != null) {
      ListOf<SpeciesReference> oldListOfReactants = listOfReactants;
      listOfReactants = null;
      oldListOfReactants.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /**
   * Sets the reversible Boolean of this {@link Reaction} to {@code null}.
   */
  public void unsetReversible() {
    Boolean oldReversible = reversible;
    isSetReversible = false;
    reversible = null;
    firePropertyChange(TreeNodeChangeEvent.reversible, oldReversible, reversible);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.element.SBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetReversible()) {
      attributes.put(TreeNodeChangeEvent.reversible, Boolean.toString(getReversible()));
    }
    if (isSetFast()) {
      attributes.put(TreeNodeChangeEvent.fast, Boolean.toString(getFast()));
    }

    if (2 < getLevel()) {
      if (isSetCompartment()) {
        attributes.put(TreeNodeChangeEvent.compartment, getCompartment());
      }
    }

    return attributes;
  }

}
