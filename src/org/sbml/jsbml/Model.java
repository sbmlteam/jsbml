/*
 * $Id$ $URL:
 * https://jsbml
 * .svn.sourceforge.net/svnroot/jsbml/trunk/src/org/sbml/jsbml/Model.java $
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML> for
 * the latest version of JSBML and more information about SBML.
 * 
 * Copyright (C) 2009-2012 jointly by the following organizations: 1. The
 * University of Tuebingen, Germany 2. EMBL European Bioinformatics Institute
 * (EBML-EBI), Hinxton, UK 3. The California Institute of Technology, Pasadena,
 * CA, USA
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation. A copy of the license agreement is provided in the file
 * named "LICENSE.txt" included with this software distribution and also
 * available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */

package org.sbml.jsbml;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;
import org.sbml.jsbml.util.TreeNodeChangeEvent;
import org.sbml.jsbml.util.TreeNodeChangeListener;
import org.sbml.jsbml.util.filters.AssignmentVariableFilter;
import org.sbml.jsbml.util.filters.BoundaryConditionFilter;
import org.sbml.jsbml.util.filters.IdenticalUnitDefinitionFilter;
import org.sbml.jsbml.util.filters.NameFilter;

/**
 * <p>
 * JSBML implementation of SBML's {@link Model} construct.
 * </p>
 * <p>
 * In an SBML model definition, a single object of class {@link Model} serves as
 * the overall container for the lists of the various model components. All of
 * the lists are optional, but if a given list container is present within the
 * model, the list must not be empty; that is, it must have length one or more.
 * </p>
 * 
 * @author Andreas Dr&auml;ger
 * @author Marine Dumousseau
 * @author Nicolas Rodriguez
 * @since 0.8
 * @version $Rev$
 */
public class Model extends AbstractNamedSBase implements UniqueNamedSBase {
  
  /**
   * Error message to indicate that an element could not be created.
   */
  private static final String COULD_NOT_CREATE_ELEMENT_MSG = "Could not create %s because no %s have been defined yet.";
  
  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(Model.class);
  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 5256379371231860049L;
  /**
   * Represents the 'areaUnits' XML attribute of a model element.
   */
  private String areaUnitsID;
  /**
   * Represents the 'conversionFactor' XML attribute of a model element.
   */
  private String conversionFactorID;
  /**
   * Represents the 'extentUnits' XML attribute of a model element.
   */
  private String extentUnitsID;
  /**
   * Represents the 'lengthUnits' XML attribute of a model element.
   */
  private String lengthUnitsID;
  /**
   * Represents the listOfCompartments subnode of a model element.
   */
  private ListOf<Compartment> listOfCompartments;
  /**
   * Represents the listOfCompartmentTypes subnode of a model element.
   * @deprecated only valid in SBML Level 2 for Versions 2 through 4.
   */
  @Deprecated
  private ListOf<CompartmentType> listOfCompartmentTypes;
  /**
   * Represents the listOfConstraints subnode of a model element.
   */
  private ListOf<Constraint> listOfConstraints;
  
  /**
   * Represents the listOfEvents subnode of a model element.
   */
  private ListOf<Event> listOfEvents;
  
  /**
   * Represents the listOfFunctionDefinitions subnode of a model element.
   */
  private ListOf<FunctionDefinition> listOfFunctionDefinitions;
  
  /**
   * Represents the listOfInitialAssignments subnode of a model element.
   */
  private ListOf<InitialAssignment> listOfInitialAssignments;
  
  /**
   * Represents the listOfParameters subnode of a model element.
   */
  private ListOf<Parameter> listOfParameters;
  
  /**
   * Represents the list of predefined UnitDefinitions for a given SBML level
   * and version.
   */
  private List<UnitDefinition> listOfPredefinedUnitDefinitions;
  
  /**
   * Represents the listOfReactions subnode of a model element.
   */
  private ListOf<Reaction> listOfReactions;
  
  /**
   * Represents the listOfRules subnode of a model element.
   */
  private ListOf<Rule> listOfRules;
  
  /**
   * Represents the listOfSpecies subnode of a model element.
   */
  private ListOf<Species> listOfSpecies;
  
  /**
   * Represents the listOfSpeciesTypes subnode of a model element.
   * @deprecated only valid in SBML Level 2 for Versions 2 through 4.
   */
  @Deprecated
  private ListOf<SpeciesType> listOfSpeciesTypes;
  
  /**
   * Represents the listOfUnitDefinitions subnode of a model element.
   */
  private ListOf<UnitDefinition> listOfUnitDefinitions;
  
  /**
   * A mapping between the identifiers of {@link LocalParameter}s and the
   * containing {@link Reaction} objects.
   */
  private Map<String, List<Reaction>> mapOfLocalParameters;
  
  /**
   * For internal computation: a mapping between their identifiers and
   * the {@link UniqueNamedSBase}s in {@link Model}s themself:
   */
  private Map<String, UniqueNamedSBase> mapOfUniqueNamedSBases;
  
  /**
   * A mapping between their identifiers and associated {@link UnitDefinition} objects.
   */
  private Map<String, UnitDefinition> mapOfUnitDefinitions;
  
  /**
   * Represents the 'substanceUnits' XML attribute of a model element.
   */
  private String substanceUnitsID;
  
  /**
   * Represents the 'timeUnits' XML attribute of a model element.
   */
  private String timeUnitsID;
  
  /**
   * Represents the 'volumeUnits' XML attribute of a model element.
   */
  private String volumeUnitsID;
  
  /**
   * Creates a Model instance. By default, all the listOfxxx and xxxUnitsID are
   * <code>null</code>.
   */
  public Model() {
    super();
    initDefaults();
  }
  
  /**
   * Creates a Model instance.
   * 
   * @param level
   * @param version
   */
  public Model(int level, int version) {
    this(null, level, version);
    initDefaults();
  }
  
  /**
   * Creates a Model instance from a Model.
   * 
   * @param model
   */
  @SuppressWarnings("deprecation")
  public Model(Model model) {
    super(model);
    initDefaults();
    if (model.isSetListOfFunctionDefinitions()) {
      setListOfFunctionDefinitions((ListOf<FunctionDefinition>) model
          .getListOfFunctionDefinitions().clone());
    }
    if (model.isSetListOfUnitDefinitions()) {
      setListOfUnitDefinitions((ListOf<UnitDefinition>) model
          .getListOfUnitDefinitions().clone());
    }
    if (model.isSetListOfCompartmentTypes()) {
      setListOfCompartmentTypes((ListOf<CompartmentType>) model
          .getListOfCompartmentTypes().clone());
    }
    if (model.isSetListOfSpeciesTypes()) {
      setListOfSpeciesTypes((ListOf<SpeciesType>) model.getListOfSpeciesTypes()
          .clone());
    }
    if (model.isSetListOfCompartments()) {
      setListOfCompartments((ListOf<Compartment>) model.getListOfCompartments()
          .clone());
    }
    if (model.isSetListOfSpecies()) {
      setListOfSpecies((ListOf<Species>) model.getListOfSpecies().clone());
    }
    if (model.isSetListOfParameters()) {
      setListOfParameters((ListOf<Parameter>) model.getListOfParameters()
          .clone());
    }
    if (model.isSetListOfInitialAssignments()) {
      setListOfInitialAssignments((ListOf<InitialAssignment>) model
          .getListOfInitialAssignments().clone());
    }
    if (model.isSetListOfRules()) {
      setListOfRules((ListOf<Rule>) model.getListOfRules().clone());
    }
    if (model.isSetListOfConstraints()) {
      setListOfConstraints((ListOf<Constraint>) model.getListOfConstraints()
          .clone());
    }
    if (model.isSetListOfReactions()) {
      setListOfReactions((ListOf<Reaction>) model.getListOfReactions().clone());
    }
    if (model.isSetListOfEvents()) {
      setListOfEvents((ListOf<Event>) model.getListOfEvents().clone());
    }
    
    // L3 attributes
    if (model.isSetAreaUnits()) {
    	setAreaUnits(model.getAreaUnits());
    }
    if (model.isSetConversionFactor()) {
    	setConversionFactor(model.getConversionFactor());
    }
    if (model.isSetExtentUnits()) {
    	setExtentUnits(model.getExtentUnits());
    }
    if (model.isSetLengthUnits()) {
    	setLengthUnits(model.getLengthUnits());
    }
    if (model.isSetSubstanceUnits()) {
    	setSubstanceUnits(model.getSubstanceUnits());
    }
    if (model.isSetTimeUnits()) {
    	setTimeUnits(model.getTimeUnits());
    }
    if (model.isSetVolumeUnits()) {
    	setVolumeUnits(model.getVolumeUnits());
    }
  }
  
  /**
   * Creates a Model instance from an id.
   * 
   * @param id
   */
  public Model(String id) {
    super(id);
    initDefaults();
  }
  
  /**
   * Creates a Model instance from an id, level and version. By default, all the
   * listOfxxx and xxxUnitsID are <code>null</code>.
   * 
   * @param id
   * @param level
   * @param version
   */
  public Model(String id, int level, int version) {
    super(id, level, version);
    initDefaults();
  }

	/**
   * Adds a Compartment instance to the listOfCompartments of this Model.
   * 
   * @param compartment
   * @return {@code true} if the {@link #listOfCompartments} was changed as
   *         a result of this call.
   */
  public boolean addCompartment(Compartment compartment) {
    return getListOfCompartments().add(compartment);
  }
  
  /**
   * Adds a CompartmentType instance to the listOfCompartmentTypes of this
   * Model.
   * 
   * @param compartmentType
   * @return {@code true} if the {@link #listOfCompartmentTypes} was
   *         changed as a result of this call.
   * @deprecated Only valid in SBML Level 2 for Versions 2 through 4.
   */
  @Deprecated
  public boolean addCompartmentType(CompartmentType compartmentType) {
    return getListOfCompartmentTypes().add(compartmentType);
  }
  
  /**
   * Adds a {@link Constraint} instance to the listOfConstraints of this
   * {@link Model}.
   * 
   * @param constraint
   * @return {@code true} if the {@link #listOfConstraints} was changed as
   *         a result of this call.
   */
  public boolean addConstraint(Constraint constraint) {
    return getListOfConstraints().add(constraint);
  }
  
  /**
   * Adds an {@link Event} instance to the listOfEvents of this Model.
   * 
   * @param event
   * @return {@code true} if the {@link #listOfEvents} was changed as a
   *         result of this call.
   * @throws PropertyNotAvailableException
   *             for inappropriate Level/Version combinations.
   */
  public boolean addEvent(Event event) {
    if (getLevel() < 2) { 
    	throw new PropertyNotAvailableException("event", this); 
    }
    return getListOfEvents().add(event);
  }
  
  /**
   * Adds a {@link FunctionDefinition} instance to the listOfFunctionDefinitions
   * of this {@link Model}.
   * 
   * @param functionDefinition
   * @return {@code true} if the {@link #listOfFunctionDefinitions} was
   *         changed as a result of this call.
   */
  public boolean addFunctionDefinition(FunctionDefinition functionDefinition) {
    return getListOfFunctionDefinitions().add(functionDefinition);
  }
  
  /**
   * Adds an InitialAssignment instance to the listOfInitialAssignments of this
   * Model.
   * 
   * @param initialAssignment
   * @return {@code true} if the {@link #listOfInitialAssignments} was
   *         changed as a result of this call.
   */
  public boolean addInitialAssignment(InitialAssignment initialAssignment) {
    return getListOfInitialAssignments().add(initialAssignment);
  }
  
  /**
   * Adds a Parameter instance to the listOfParameters of this Model.
   * 
   * @param parameter
   * @return {@code true} if the {@link #listOfParameters} was changed as a
   *         result of this call.
   */
  public boolean addParameter(Parameter parameter) {
    return getListOfParameters().add(parameter);
  }
  
  /**
   * Adds all the possible unit kinds as {@link UnitDefinition}, so that the
   * method {@link Model#getUnitDefinition(String)} would be able to return a
   * valid {@link UnitDefinition} even if one of these kinds is passed as
   * parameter.
   * 
   */
  private void addPredefinedUnits() {
    
    List<UnitDefinition> oldValue = new ArrayList<UnitDefinition>(
      this.listOfPredefinedUnitDefinitions);
    
    if ((getLevel() == -1) || (getVersion() == -1)) { 
      return; 
    }
    
    // ampere farad joule lux radian volt
    listOfPredefinedUnitDefinitions.add(UnitDefinition.getPredefinedUnit(
      "ampere", getLevel(), getVersion()));
    listOfPredefinedUnitDefinitions.add(UnitDefinition.getPredefinedUnit(
      "farad", getLevel(), getVersion()));
    listOfPredefinedUnitDefinitions.add(UnitDefinition.getPredefinedUnit(
      "joule", getLevel(), getVersion()));
    listOfPredefinedUnitDefinitions.add(UnitDefinition.getPredefinedUnit("lux",
      getLevel(), getVersion()));
    listOfPredefinedUnitDefinitions.add(UnitDefinition.getPredefinedUnit(
      "radian", getLevel(), getVersion()));
    listOfPredefinedUnitDefinitions.add(UnitDefinition.getPredefinedUnit(
      "volt", getLevel(), getVersion()));
    
    // avogadro gram katal metre second watt 		
    if (getLevel() >= 3) {
      listOfPredefinedUnitDefinitions.add(UnitDefinition.getPredefinedUnit(
        "avogadro", getLevel(), getVersion()));
    }
    listOfPredefinedUnitDefinitions.add(UnitDefinition.getPredefinedUnit(
      "gram", getLevel(), getVersion()));
    listOfPredefinedUnitDefinitions.add(UnitDefinition.getPredefinedUnit(
      "katal", getLevel(), getVersion()));
    listOfPredefinedUnitDefinitions.add(UnitDefinition.getPredefinedUnit(
      "metre", getLevel(), getVersion()));
    listOfPredefinedUnitDefinitions.add(UnitDefinition.getPredefinedUnit(
      "second", getLevel(), getVersion()));
    listOfPredefinedUnitDefinitions.add(UnitDefinition.getPredefinedUnit(
      "watt", getLevel(), getVersion()));
    
    // becquerel gray kelvin mole siemens weber 
    listOfPredefinedUnitDefinitions.add(UnitDefinition.getPredefinedUnit(
      "becquerel", getLevel(), getVersion()));
    listOfPredefinedUnitDefinitions.add(UnitDefinition.getPredefinedUnit(
      "gray", getLevel(), getVersion()));
    listOfPredefinedUnitDefinitions.add(UnitDefinition.getPredefinedUnit(
      "kelvin", getLevel(), getVersion()));
    listOfPredefinedUnitDefinitions.add(UnitDefinition.getPredefinedUnit(
      "mole", getLevel(), getVersion()));
    listOfPredefinedUnitDefinitions.add(UnitDefinition.getPredefinedUnit(
      "siemens", getLevel(), getVersion()));
    listOfPredefinedUnitDefinitions.add(UnitDefinition.getPredefinedUnit(
      "weber", getLevel(), getVersion()));
    
    // candela henry kilogram newton sievert
    listOfPredefinedUnitDefinitions.add(UnitDefinition.getPredefinedUnit(
      "candela", getLevel(), getVersion()));
    listOfPredefinedUnitDefinitions.add(UnitDefinition.getPredefinedUnit(
      "henry", getLevel(), getVersion()));
    listOfPredefinedUnitDefinitions.add(UnitDefinition.getPredefinedUnit(
      "kilogram", getLevel(), getVersion()));
    listOfPredefinedUnitDefinitions.add(UnitDefinition.getPredefinedUnit(
      "newton", getLevel(), getVersion()));
    listOfPredefinedUnitDefinitions.add(UnitDefinition.getPredefinedUnit(
      "sievert", getLevel(), getVersion()));
    
    // coulomb hertz litre ohm steradian
    listOfPredefinedUnitDefinitions.add(UnitDefinition.getPredefinedUnit(
      "coulomb", getLevel(), getVersion()));
    listOfPredefinedUnitDefinitions.add(UnitDefinition.getPredefinedUnit(
      "hertz", getLevel(), getVersion()));
    listOfPredefinedUnitDefinitions.add(UnitDefinition.getPredefinedUnit(
      "litre", getLevel(), getVersion()));
    listOfPredefinedUnitDefinitions.add(UnitDefinition.getPredefinedUnit("ohm",
      getLevel(), getVersion()));
    listOfPredefinedUnitDefinitions.add(UnitDefinition.getPredefinedUnit(
      "steradian", getLevel(), getVersion()));
    
    // dimensionless item lumen pascal tesla
    listOfPredefinedUnitDefinitions.add(UnitDefinition.getPredefinedUnit(
      "dimensionless", getLevel(), getVersion()));
    listOfPredefinedUnitDefinitions.add(UnitDefinition.getPredefinedUnit(
      "item", getLevel(), getVersion()));
    listOfPredefinedUnitDefinitions.add(UnitDefinition.getPredefinedUnit(
      "lumen", getLevel(), getVersion()));
    listOfPredefinedUnitDefinitions.add(UnitDefinition.getPredefinedUnit(
      "pascal", getLevel(), getVersion()));
    listOfPredefinedUnitDefinitions.add(UnitDefinition.getPredefinedUnit(
      "tesla", getLevel(), getVersion()));
    
    // meter liter celsius
    if (getLevel() == 1) {
      listOfPredefinedUnitDefinitions.add(UnitDefinition.getPredefinedUnit(
        "meter", getLevel(), getVersion()));
      listOfPredefinedUnitDefinitions.add(UnitDefinition.getPredefinedUnit(
        "liter", getLevel(), getVersion()));
      listOfPredefinedUnitDefinitions.add(UnitDefinition.getPredefinedUnit(
        "celsius", getLevel(), getVersion()));
    }
    this.firePropertyChange(TreeNodeChangeEvent.units, oldValue,
      listOfPredefinedUnitDefinitions);
  }
  
  /**
   * Adds a Reaction instance to the listOfReactions of this Model.
   * 
   * @param reaction
   * @return {@code true} if the {@link #listOfReactions} was changed as a
   *         result of this call.
   */
  public boolean addReaction(Reaction reaction) {
    return getListOfReactions().add(reaction);
  }
  
  /**
   * Adds a Rule instance to the listOfRules of this Model.
   * 
   * @param rule
   * @return {@code true} if the {@link #listOfRules} was changed as a
   *         result of this call.
   */
  public boolean addRule(Rule rule) {
    return getListOfRules().add(rule);
  }
  
  /**
   * Adds a Species instance to the listOfSpecies of this Model.
   * 
   * @param spec
   * @return {@code true} if the {@link #listOfSpecies} was changed as a
   *         result of this call.
   */
  public boolean addSpecies(Species spec) {
    return getListOfSpecies().add(spec);
  }
  
  /**
   * Adds a SpeciesType instance to the listOfSpeciesTypes of this Model.
   * 
   * @param speciesType
   * @return {@code true} if the {@link #listOfSpeciesTypes} was changed as
   *         a result of this call.
   * @deprecated Only valid in SBML Level 2 for Versions 2 through 4.
   */
  @Deprecated
  public boolean addSpeciesType(SpeciesType speciesType) {
    return getListOfSpeciesTypes().add(speciesType);
  }
  
  /**
   * Adds an UnitDefinition instance to the listOfUnitDefinitions of this Model.
   * 
   * @param unitDefinition
   * @return {@code true} if the {@link #listOfUnitDefinitions} was changed
   *         as a result of this call.
   */
  public boolean addUnitDefinition(UnitDefinition unitDefinition) {
    return getListOfUnitDefinitions().add(unitDefinition);
  }
  
  /**
   * Checks whether an identical {@link UnitDefinition} like the given
   * {@link UnitDefinition} is already in this {@link Model}'s
   * {@link #listOfUnitDefinitions}. If yes, the identifier of the identical
   * {@link UnitDefinition} will be returned. Otherwise, the given unit is added
   * to the {@link #listOfUnitDefinitions} and its identifier will be returned.
   * In any case, this method returns the identifier of a {@link UnitDefinition}
   * that is part of this {@link Model} at least after calling this method.
   * 
   * @param units
   *        The unit to be checked and added if no identical
   *        {@link UnitDefinition} can be found.
   * @return
   */
  public String addUnitDefinitionOrReturnIdenticalUnit(UnitDefinition units) {
    boolean contains = false;
    for (UnitDefinition ud : getListOfUnitDefinitions()) {
      if (UnitDefinition.areIdentical(ud, units)) {
        units = ud;
        contains = true;
        break;
      }
    }
    if (!contains) {
      addUnitDefinition(units);
    }
    return units.getId();
  }
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.element.SBase#clone()
   */
  public Model clone() {
    return new Model(this);
  }
  
  /**
   * Returns {@code true} if this model contains a reference to a {@link Compartment}
   * with the given identifier.
   * 
   * @param id
   * @return {@code true} if this model contains a reference to a {@link Compartment}
   *         with the given identifier.
   */
  public boolean containsCompartment(String id) {
    return getCompartment(id) != null;
  }

  /**
   * Returns {@code true} if this model contains a reference to a
   * {@link FunctionDefinition} with the given identifier.
   * 
   * @param id
   * @return {@code true} if this model contains a reference to a
   *         {@link FunctionDefinition} with the given identifier.
   */
  public boolean containsFunctionDefinition(String id) {
    return getFunctionDefinition(id) != null;
  }
  
  /**
   * Returns {@code true} if this model contains a reference to a {@link Parameter} with
   * the given identifier.
   * 
   * @param id
   * @return {@code true} if this model contains a reference to a {@link Parameter} with
   *         the given identifier.
   */
  public boolean containsParameter(String id) {
    return getParameter(id) != null;
  }
  
  /**
   * Returns {@code true} if this {@link Model} contains a reference to the given
   * {@link Quantity}.
   * 
   * @param quantity
   * @return {@code true} if this {@link Model} contains a reference to the given
   *         {@link Quantity}.
   */
  public boolean containsQuantity(Quantity quantity) {
    Model model = quantity.getModel();
    if (!quantity.isSetId() || (model == null) || (this != model)) { 
      return false; 
    }
    return findQuantity(quantity.getId()) != null;
  }

  /**
   * Returns {@code true} if this {@link Model} contains a reference to the
   * given {@link Reaction}.
   * 
   * @param id
   *        the identifier of a potential reaction.
   * @return {@code true} if this {@link Model} contains a reference to the
   *         given {@link Reaction}.
   */
  public boolean containsReaction(String id) {
    return getReaction(id) != null;
  }
  
  /**
   * Returns {@code true} if this {@link Model} contains a reference to the given
   * {@link Species}.
   * 
   * @param id
   * @return {@code true} if this {@link Model} contains a reference to the given
   *         {@link Species}.
   */
  public boolean containsSpecies(String id) {
    return getSpecies(id) != null;
  }
  
  /**
   * Returns {@code true} if this {@link Model} contains a reference to the given
   * {@link UniqueNamedSBase}.
   * 
   * @param units
   * @return {@code true} if this {@link Model} contains a reference to the given
   *         {@link UniqueNamedSBase}.
   */
  public boolean containsUniqueNamedSBase(String id) {
    return mapOfUniqueNamedSBases.containsKey(id);
  }
  
  /**
   * Returns {@code true} if this {@link Model} contains a reference to the given
   * {@link UnitDefinition}.
   * 
   * @param units
   * @return {@code true} if this {@link Model} contains a reference to the given
   *         {@link UnitDefinition}.
   */
  public boolean containsUnitDefinition(String units) {
    return getUnitDefinition(units) != null;
  }
  
  /**
   * Creates a new {@link AlgebraicRule} inside this {@link Model} and returns
   * it.
   * <p>
   * 
   * @return the {@link AlgebraicRule} object created
   *         <p>
   * @see #addRule(Rule r)
   */
  public AlgebraicRule createAlgebraicRule() {
    AlgebraicRule rule = new AlgebraicRule(getLevel(), getVersion());
    addRule(rule);
    return rule;
  }
  
  /**
   * Creates a new {@link AssignmentRule} inside this {@link Model} and returns
   * it.
   * <p>
   * 
   * @return the {@link AssignmentRule} object created
   *         <p>
   * @see #addRule(Rule r)
   */
  public AssignmentRule createAssignmentRule() {
    AssignmentRule rule = new AssignmentRule(getLevel(), getVersion());
    addRule(rule);
    return rule;
  }
  
  /**
   * Creates a new {@link Compartment} inside this {@link Model} and returns it.
   * <p>
   * 
   * @return the {@link Compartment} object created
   *         <p>
   * @see #addCompartment(Compartment c)
   */
  public Compartment createCompartment() {
    return createCompartment(null);
  }
  
  /**
   * Creates a new {@link Compartment} inside this {@link Model} and returns it.
   * 
   * @param id
   *        the id of the new element to create
   * @return the {@link Compartment} object created
   */
  public Compartment createCompartment(String id) {
    Compartment compartment = new Compartment(id, getLevel(), getVersion());
    addCompartment(compartment);
    return compartment;
  }

  /**
   * Creates a new {@link CompartmentType} inside this {@link Model} and returns
   * it.
   * <p>
   * 
   * @return the {@link CompartmentType} object created
   *         <p>
   * @see #addCompartmentType(CompartmentType ct)
   * @deprecated {@link CompartmentType}s should no longer be used, because this
   *             data structure is only valid in SBML Level 2 for Versions 2
   *             through 4.
   */
  @Deprecated
  public CompartmentType createCompartmentType() {
    return createCompartmentType(null);
  }

  /**
   * Creates a new {@link CompartmentType} inside this {@link Model} and returns
   * it.
   * 
   * @param id
   *        the id of the new element to create
   * @return the {@link CompartmentType} object created
   * @deprecated {@link CompartmentType}s should no longer be used, because this
   *             data structure is only valid in SBML Level 2 for Versions 2
   *             through 4.
   */
  @Deprecated
  public CompartmentType createCompartmentType(String id) {
    CompartmentType compartmentType = new CompartmentType(id, getLevel(),
      getVersion());
    addCompartmentType(compartmentType);
    
    return compartmentType;
  }
  
  /**
   * Creates a new {@link Constraint} inside this {@link Model} and returns it.
   * <p>
   * 
   * @return the {@link Constraint} object created
   *         <p>
   * @see #addConstraint(Constraint c)
   */
  public Constraint createConstraint() {
    Constraint constraint = new Constraint(getLevel(), getVersion());
    addConstraint(constraint);
    
    return constraint;
  }
  
  /**
   * Creates a new {@link Delay} inside the last {@link Event} object created in
   * this {@link Model}, and returns a pointer to it.
   * <p>
   * The mechanism by which the last {@link Event} object in this model was
   * created is not significant. It could have been created in a variety of
   * ways, for example by using createEvent(). If no {@link Event} object exists
   * in this {@link Model} object, a new {@link Delay} is <em>not</em> created
   * and <code>null</code> is returned instead.
   * <p>
   * 
   * @return the {@link Delay} object created
   */
  public Delay createDelay() {
    return getLastElementOf(getListOfEvents()).createDelay();
  }
  
  /**
   * Creates a new {@link Event} inside this {@link Model} and returns it.
   * <p>
   * 
   * @return the {@link Event} object created
   */
  public Event createEvent() {
    return createEvent(null);
  }
  
  /**
   * Creates a new {@link Event} inside this {@link Model} and returns it.
   * 
   * @param id
   *        the id of the new element to create
   * @return the {@link Event} object created
   */
  public Event createEvent(String id) {
    Event event = new Event(id, getLevel(), getVersion());
    addEvent(event);
    return event;
  }
  
  /**
   * Creates a new {@link EventAssignment} inside the last {@link Event} object
   * created in this {@link Model}, and returns a pointer to it.
   * <p>
   * The mechanism by which the last {@link Event} object in this model was
   * created is not significant. It could have been created in a variety of
   * ways, for example by using createEvent(). If no {@link Event} object exists
   * in this {@link Model} object, a new {@link EventAssignment} is <em>not</em>
   * created and <code>null</code> is returned instead.
   * <p>
   * 
   * @return the {@link EventAssignment} object created
   */
  public EventAssignment createEventAssignment() {
    
    int numEvent = getNumEvents();
    Event lastEvent = null;
    
    if (numEvent == 0) {
      return null;
    } else {
      lastEvent = getEvent(numEvent - 1);
    }
    
    EventAssignment eventAssgnt = new EventAssignment(getLevel(), getVersion());
    lastEvent.addEventAssignment(eventAssgnt);
    
    return eventAssgnt;
  }
  
  /**
   * Creates a new {@link FunctionDefinition} inside this {@link Model} and
   * returns it.
   * <p>
   * 
   * @return the {@link FunctionDefinition} object created
   *         <p>
   * @see #addFunctionDefinition(FunctionDefinition fd)
   */
  public FunctionDefinition createFunctionDefinition() {
    return createFunctionDefinition(null);
  }
  
  /**
   * Creates a new {@link FunctionDefinition} inside this {@link Model} and
   * returns it.
   * 
   * @param id
   *        the id of the new element to create
   * @return the {@link FunctionDefinition} object created
   */
  public FunctionDefinition createFunctionDefinition(String id) {
    FunctionDefinition functionDef = new FunctionDefinition(id, getLevel(),
      getVersion());
    addFunctionDefinition(functionDef);
    
    return functionDef;
  }
  
  /**
   * Creates a new {@link InitialAssignment} inside this {@link Model} and
   * returns it.
   * <p>
   * 
   * @return the {@link InitialAssignment} object created
   *         <p>
   * @see #addInitialAssignment(InitialAssignment ia)
   */
  public InitialAssignment createInitialAssignment() {
    InitialAssignment initAssgmt = new InitialAssignment(getLevel(),
      getVersion());
    addInitialAssignment(initAssgmt);
    
    return initAssgmt;
  }
  
  /**
   * Creates a new {@link KineticLaw} inside the last {@link Reaction} object
   * created in this {@link Model}, and returns a pointer to it.
   * <p>
   * The mechanism by which the last {@link Reaction} object was created and
   * added to this {@link Model} is not significant. It could have been created
   * in a variety of ways, for example using createReaction(). If a
   * {@link Reaction} does not exist for this model, or a {@link Reaction}
   * exists but already has a {@link KineticLaw}, a new {@link KineticLaw} is
   * <em>not</em> created and <code>null</code> is returned instead.
   * <p>
   * 
   * @return the {@link KineticLaw} object created
   */
  public KineticLaw createKineticLaw() {
    Reaction lastReaction = (Reaction) getLastElementOf(listOfReactions);
    
    if (lastReaction == null) {
      logger.warn(String.format(COULD_NOT_CREATE_ELEMENT_MSG, "KineticLaw",
        "reactions"));
      return null;
    }
    
    KineticLaw kineticLaw = new KineticLaw(getLevel(), getVersion());
    lastReaction.setKineticLaw(kineticLaw);
    
    return kineticLaw;
  }
  
  /**
   * Creates a new local {@link LocalParameter} inside the {@link KineticLaw}
   * object of the last {@link Reaction} created inside this {@link Model}, and
   * returns a pointer to it.
   * <p>
   * The last {@link KineticLaw} object in this {@link Model} could have been
   * created in a variety of ways. For example, it could have been added using
   * createKineticLaw(), or it could be the result of using
   * Reaction.createKineticLaw() on the {@link Reaction} object created by a
   * createReaction(). If a {@link Reaction} does not exist for this model, or
   * the last {@link Reaction} does not contain a {@link KineticLaw} object, a
   * new {@link LocalParameter} is <em>not</em> created and <code>null</code> is returned
   * instead.
   * <p>
   * 
   * @return the {@link LocalParameter} object created
   */
  public LocalParameter createKineticLawParameter() {
    return createKineticParameter(null);
  }
  
  /**
   * Creates a new local {@link LocalParameter} inside the {@link KineticLaw}
   * object of the last {@link Reaction} created inside this {@link Model}, and
   * returns a pointer to it.
   * 
   * @param id
   *        the id of the new element to create
   * @return the {@link LocalParameter} object created
   */
  public LocalParameter createKineticParameter(String id) {
    Reaction lastReaction = (Reaction) getLastElementOf(listOfReactions);
    KineticLaw lastKineticLaw = null;
    
    if (lastReaction == null) {
      logger.warn(String.format(COULD_NOT_CREATE_ELEMENT_MSG,
        "LocalParameter for KineticLaw", "reactions"));
      return null;
    } else {
      lastKineticLaw = lastReaction.getKineticLaw();
      if (lastKineticLaw == null) { 
        return null; 
      }
    }
    
    LocalParameter parameter = new LocalParameter(id, getLevel(), getVersion());
    lastKineticLaw.addLocalParameter(parameter);
    
    return parameter;
  }
  
  /**
   * Creates a new {@link ModifierSpeciesReference} object for a modifier
   * species inside the last {@link Reaction} object in this {@link Model}, and
   * returns a pointer to it.
   * <p>
   * The mechanism by which the last {@link Reaction} object was created and
   * added to this {@link Model} is not significant. It could have been created
   * in a variety of ways, for example using createReaction(). If a
   * {@link Reaction} does not exist for this model, a new
   * {@link ModifierSpeciesReference} is <em>not</em> created and <code>null</code> is
   * returned instead.
   * <p>
   * 
   * @return the {@link SpeciesReference} object created
   */
  public ModifierSpeciesReference createModifier() {
    return createModifier(null);
  }
  
  /**
   * Creates a new {@link ModifierSpeciesReference} with the specified 'id'.
   * 
   * @param id
   *        the id of the new element to create
   * @return the {@link SpeciesReference} object created
   */
  public ModifierSpeciesReference createModifier(String id) {
    Reaction lastReaction = (Reaction) getLastElementOf(listOfReactions);
    
    if (lastReaction == null) {
    	return null;
    }
    
    ModifierSpeciesReference modifier = lastReaction.createModifier(id);
    
    return modifier;
  }
  
  /**
   * Creates a new {@link Parameter} inside this {@link Model} and returns it.
   * <p>
   * 
   * @return the {@link Parameter} object created
   *         <p>
   * @see #addParameter(Parameter p)
   */
  public Parameter createParameter() {
    return createParameter(null);
  }
  
  /**
   * Creates a new {@link Parameter}.
   * 
   * @param id
   *        the id of the new element to create
   * @return the {@link Parameter} object created
   */
  public Parameter createParameter(String id) {
    Parameter parameter = new Parameter(id, getLevel(), getVersion());
    addParameter(parameter);
    return parameter;
  }
  
  /**
   * Creates a new {@link SpeciesReference} object for a product inside the last
   * {@link Reaction} object in this {@link Model}, and returns a pointer to it.
   * <p>
   * The mechanism by which the last {@link Reaction} object was created and
   * added to this {@link Model} is not significant. It could have been created
   * in a variety of ways, for example using createReaction(). If a
   * {@link Reaction} does not exist for this model, a new
   * {@link SpeciesReference} is <em>not</em> created and <code>null</code> is returned
   * instead.
   * <p>
   * 
   * @return the {@link SpeciesReference} object created
   */
  public SpeciesReference createProduct() {
    return createProduct(null);
  }
  
  /**
   * Creates an instance of {@link SpeciesReference} and adds it to the last
   * {@link Reaction} element that has been added to this {@link Model}.
   * 
   * @param id
   * @return
   */
  public SpeciesReference createProduct(String id) {
    Reaction lastReaction = (Reaction) getLastElementOf(listOfReactions);
    
    if (lastReaction == null) {
      logger.warn(String.format(COULD_NOT_CREATE_ELEMENT_MSG, "Product",
        "reactions"));
      return null;
    }
    
    SpeciesReference product = lastReaction.createProduct(id);
    
    return product;
  }
  
  /**
   * Creates a new {@link RateRule} inside this {@link Model} and returns it.
   * <p>
   * 
   * @return the {@link RateRule} object created
   *         <p>
   * @see #addRule(Rule r)
   */
  public RateRule createRateRule() {
    RateRule rule = new RateRule(getLevel(), getVersion());
    addRule(rule);
    
    return rule;
  }
  
  /**
   * Creates a new {@link SpeciesReference} object for a reactant inside the
   * last {@link Reaction} object in this {@link Model}, and returns a pointer
   * to it.
   * <p>
   * The mechanism by which the last {@link Reaction} object was created and
   * added to this {@link Model} is not significant. It could have been created
   * in a variety of ways, for example using createReaction(). If a
   * {@link Reaction} does not exist for this model, a new
   * {@link SpeciesReference} is <em>not</em> created and <code>null</code> is returned
   * instead.
   * <p>
   * 
   * @return the {@link SpeciesReference} object created
   */
  public SpeciesReference createReactant() {
    return createReactant(null);
  }
  
  /**
   * Creates a new {@link SpeciesReference} object, with the specified 'id', for
   * a reactant inside the last {@link Reaction} object in this {@link Model},
   * and returns a pointer to it.
   * 
   * @param id
   *        the id of the new element to create
   * @return the {@link SpeciesReference} object created.
   */
  public SpeciesReference createReactant(String id) {
    Reaction lastReaction = (Reaction) getLastElementOf(listOfReactions);
    if (lastReaction == null) {
      logger.warn(String.format(COULD_NOT_CREATE_ELEMENT_MSG, "Reactant",
        "reactions"));
      return null;
    }
    SpeciesReference reactant = lastReaction.createReactant(id);
    
    return reactant;
  }
  
  /**
   * Creates a new {@link Reaction} inside this {@link Model} and returns it.
   * <p>
   * 
   * @return the {@link Reaction} object created
   *         <p>
   * @see #addReaction(Reaction r)
   */
  public Reaction createReaction() {
    return createReaction(null);
  }
  
  /**
   * Creates a new {@link Reaction} inside this {@link Model} and returns it.
   * 
   * @param id
   *        the id of the new element to create
   * @return the {@link Reaction} object created
   */
  public Reaction createReaction(String id) {
    Reaction reaction = new Reaction(id, getLevel(), getVersion());
    addReaction(reaction);
    
    return reaction;
  }
  
  /**
   * Creates a new {@link Species} inside this {@link Model} and returns it.
   * <p>
   * 
   * @return the {@link Species} object created
   *         <p>
   * @see #addSpecies(Species s)
   */
  public Species createSpecies() {
    return createSpecies(null);
  }
  
  /**
   * Creates a new {@link Species} inside this {@link Model} and returns it.
   * 
   * @param id
   *        the id of the new element to create
   * @return the {@link Species} object created
   */
  public Species createSpecies(String id) {
    Species species = new Species(id, getLevel(), getVersion());
    addSpecies(species);
    return species;
  }
  
  /**
   * Creates a new {@link Species} inside this {@link Model} and returns it.
   * 
   * @param id
   *        the id of the new element to create
   * @param c
   *        the Compartment of the new {@link Species}
   * @return the {@link Species} object created
   */
  public Species createSpecies(String id, Compartment c) {
    Species s = createSpecies(id);
    s.setCompartment(c);
    return s;
  }
  
  /**
   * Creates a new {@link Species} inside this {@link Model} and returns it.
   * 
   * @param id
   *        the id of the new element to create
   * @param name
   *        the name of the new element to create
   * @param c
   *        the Compartment of the new {@link Species}
   * @return the {@link Species} object created
   */
  public Species createSpecies(String id, String name, Compartment c) {
    Species s = createSpecies(id, c);
    s.setName(name);
    return s;
  }
  
  /**
   * Creates a new {@link SpeciesType} inside this {@link Model} and returns it.
   * <p>
   * 
   * @return the {@link SpeciesType} object created
   *         <p>
   * @see #addSpeciesType(SpeciesType st)
   * @deprecated Only valid in SBML Level 2 for Versions 2 through 4.
   */
  @Deprecated
  public SpeciesType createSpeciesType() {
    return createSpeciesType(null);
  }
  
  /**
   * Creates a new {@link SpeciesType} inside this {@link Model} and returns it.
   * 
   * @param id
   *        the id of the new element to create
   * @return the {@link SpeciesType} object created
   * @deprecated Only valid in SBML Level 2 for Versions 2 through 4.
   */
  @Deprecated
  public SpeciesType createSpeciesType(String id) {
    SpeciesType speciesType = new SpeciesType(id, getLevel(), getVersion());
    addSpeciesType(speciesType);
    
    return speciesType;
  }
  
  /**
   * Creates a new {@link Trigger} inside the last {@link Event} object created
   * in this {@link Model}, and returns a pointer to it.
   * <p>
   * The mechanism by which the last {@link Event} object in this model was
   * created is not significant. It could have been created in a variety of
   * ways, for example by using createEvent(). If no {@link Event} object exists
   * in this {@link Model} object, a new {@link Trigger} is <em>not</em> created
   * and <code>null</code> is returned instead.
   * <p>
   * 
   * @return the {@link Trigger} object created
   */
  public Trigger createTrigger() {
    return getLastElementOf(getListOfEvents()).createTrigger();
  }
  
  /**
   * Creates a new {@link Unit} object within the last {@link UnitDefinition}
   * object created in this model and returns a pointer to it.
   * <p>
   * The mechanism by which the {@link UnitDefinition} was created is not
   * significant. If a {@link UnitDefinition} object does not exist in this
   * model, a new {@link Unit} is <em>not</em> created and <code>null</code> is returned
   * instead.
   * <p>
   * 
   * @return the {@link Unit} object created
   *         <p>
   * @see #addUnitDefinition(UnitDefinition ud)
   */
  public Unit createUnit() {
    return createUnit(null);
  }
  
  /**
   * Creates a new {@link Unit} object within the last {@link UnitDefinition}
   * object created in this model and returns a pointer to it.
   * 
   * @param kind
   *        the kind of the new unit.
   * @return the {@link Unit} object created
   */
  public Unit createUnit(Unit.Kind kind) {
    int numUnitDef = getNumUnitDefinitions();
    UnitDefinition lastUnitDef = null;
    
    if (numUnitDef == 0) {
      return null;
    } else {
      lastUnitDef = getUnitDefinition(numUnitDef - 1);
    }
    
    Unit unit = lastUnitDef.createUnit(kind);
    
    return unit;
  }
  
  /**
   * Creates a new {@link UnitDefinition} inside this {@link Model} and returns
   * it.
   * <p>
   * 
   * @return the {@link UnitDefinition} object created
   *         <p>
   * @see #addUnitDefinition(UnitDefinition ud)
   */
  public UnitDefinition createUnitDefinition() {
    return createUnitDefinition(null);
  }
  
  /**
   * Creates a new {@link UnitDefinition} inside this {@link Model} and returns
   * it.
   * 
   * @param id
   *        the id of the new element to create
   * @return the {@link UnitDefinition} object created
   */
  public UnitDefinition createUnitDefinition(String id) {
    UnitDefinition unitDefinition = new UnitDefinition(id, getLevel(),
      getVersion());
    addUnitDefinition(unitDefinition);
    
    return unitDefinition;
  }
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equals = super.equals(object);
    if (equals) {
      Model m = (Model) object;
      equals &= m.isSetTimeUnits() == isSetTimeUnits();
      if (equals && isSetTimeUnits()) {
        equals &= getTimeUnits().equals(m.getTimeUnits());
      }
      equals &= m.isSetAreaUnits() == isSetAreaUnits();
      if (equals && isSetAreaUnits()) {
        equals &= getAreaUnits().equals(m.getAreaUnits());
      }
      equals &= m.isSetConversionFactor() == isSetConversionFactor();
      if (equals && isSetConversionFactor()) {
        equals &= getConversionFactor().equals(m.getConversionFactor());
      }
      equals &= m.isSetExtentUnits() == isSetExtentUnits();
      if (equals && isSetExtentUnits()) {
        equals &= getExtentUnits().equals(m.getExtentUnits());
      }
      equals &= m.isSetLengthUnits() == isSetLengthUnits();
      if (equals && isSetLengthUnits()) {
        equals &= getLengthUnits().equals(m.getLengthUnits());
      }
      equals &= m.isSetSubstanceUnits() == isSetSubstanceUnits();
      if (equals && m.isSetSubstanceUnits()) {
        equals &= getSubstanceUnits().equals(m.getSubstanceUnits());
      }
      equals &= m.isSetVolumeUnits() == isSetVolumeUnits();
      if (equals && isSetVolumeUnits()) {
        equals &= getVolumeUnits().equals(m.getVolumeUnits());
      }
    }
    return equals;
  }
  
  /**
   * Returns a {@link CallableSBase} element of the {@link Model} that has the
   * given 'id' as identifier or <code>null</code> if no element is found.
   * 
   * @param id
   *        an identifier indicating an element of the {@link Model}.
   * @return a {@link CallableSBase} element of the {@link Model} that has the
   *         given 'id' as id or <code>null</code> if no element is found.
   */
  public CallableSBase findCallableSBase(String id) {
    UniqueNamedSBase found = findUniqueNamedSBase(id);
    if ((found != null) && (found instanceof CallableSBase)) {
      return (CallableSBase) found;
    }
    return null;
  }
  
  /**
   * Searches the first {@link UnitDefinition} within this {@link Model}'s
   * {@link #listOfUnitDefinitions} that
   * is identical to the given {@link UnitDefinition}. If no element can be
   * found that satisfies the method
   * {@link UnitDefinition#areIdentical(UnitDefinition, UnitDefinition)},
   * <code>null</code> will be returned.
   * 
   * @param unitDefinition
   * @return A {@link UnitDefinition} object that is already part of this
   *         {@link Model}'s {@link #listOfUnitDefinitions} and satisfies the
   *         condition of
   *         {@link UnitDefinition#areIdentical(UnitDefinition, UnitDefinition)}
   *         in comparison to the given {@link UnitDefinition}, or
   *         <code>null</code> if no such element can be found.
   */
  public UnitDefinition findIdentical(UnitDefinition unitDefinition) {
    return getListOfUnitDefinitions().firstHit(new IdenticalUnitDefinitionFilter(unitDefinition));
  }
  
  /**
   * Finds all instances of {@link LocalParameter} in this {@link Model} and
   * adds them to a {@link List}.
   * 
   * @param id
   * @return A {@link List} of all {@link LocalParameter} instances with the
   *         given name or identifier. This {@link List} can be empty, but never
   *         <code>null</code>.
   */
  public List<LocalParameter> findLocalParameters(String id) 
  {
    List<LocalParameter> list = new LinkedList<LocalParameter>();
    List<Reaction> rList = mapOfLocalParameters.get(id);
    
    if ((rList == null) || rList.isEmpty()) {
      return list;
    }
    
    for (Reaction r : rList) 
    {
      // This must always be true, otherwise there is an error elsewhere:
      if (r.isSetKineticLaw()) {
    	LocalParameter p = r.getKineticLaw().getLocalParameter(id);
        if (p != null) {
          list.add(p);
        }
      } else {
    	  logger.warn("A reaction that is supposed to have a local parameter defined has no kineticLaw !!!");
      }
    }
    return list;
  }
  
  /**
   * Returns the {@link ModifierSpeciesReference} with all {@link Reaction}s
   * of this {@link Model} that has 'id' as id.
   * 
   * @param id
   *        the identifier of the {@link ModifierSpeciesReference} of interest.
   *        Note that this is not the identifier of the {@link Species} instance
   *        referred to by the {@link ModifierSpeciesReference}.
   * @return the {@link ModifierSpeciesReference} out of all {@link Reaction}s
   *         which has 'id' as id (or name depending on level and version).
   *         <code>null</code> if it doesn't exist.
   */
  public ModifierSpeciesReference findModifierSpeciesReference(String id) {
    SimpleSpeciesReference ssr = findSimpleSpeciesReference(id);
    if ((ssr != null) && (ssr instanceof ModifierSpeciesReference)) {
      return (ModifierSpeciesReference) ssr;
    }
    return null;
  }
  
  /**
   * Returns a {@link NamedSBase} element of the model that has the given 'id'
   * as id or <code>null</code> if no element is found.
   * 
   * @param id
   *        an id indicating an element of the model.
   * @return a {@link NamedSBase} element of the model that has the given 'id'
   *         as id or <code>null</code> if no element is found.
   */
  public NamedSBase findNamedSBase(String id) {
    UniqueNamedSBase found = findUniqueNamedSBase(id);
    if ((found != null) && (found instanceof NamedSBase)) {
      return (NamedSBase) found;
    }
    return null;
  }

  /**
   * Returns a {@link NamedSBaseWithDerivedUnit} element of the {@link Model}
   * that has the given 'id' as id or <code>null</code> if no element is found. It first
   * tries to find a {@link CallableSBase} with the given identifier and, if
   * this is not successful, it searches for an instance of {@link Event} with
   * the given id.
   * 
   * @param id
   *        an id indicating an element of the {@link Model}.
   * @return a {@link NamedSBaseWithDerivedUnit} element of the {@link Model}
   *         that has the given 'id' as id or <code>null</code> if no element is found.
   * @see #findCallableSBase(String)
   */
  public NamedSBaseWithDerivedUnit findNamedSBaseWithDerivedUnit(String id) {
    UniqueNamedSBase found = findUniqueNamedSBase(id);
    if ((found != null) && (found instanceof NamedSBaseWithDerivedUnit)) {
      return (NamedSBaseWithDerivedUnit) found;
    }
    return null;
  }

  /**
   * Searches for an instance of {@link Quantity} within all of this
   * {@link Model}'s components that has the given identifier or name attribute
   * and returns it. There might be multiple instances of {@link LocalParameter}
   * with the same identifier or name, each located in different
   * {@link Reaction}s. In this case, the first match will be returned. Note
   * that also global {@link Parameter} instances have a higher priority and are
   * returned first.
   * 
   * @param id
   * @return the {@link Compartment}, {@link Species}, {@link SpeciesReference}
   *         or {@link Parameter}, or the first {@link LocalParameter} which has
   *         'id' as id.
   * @see #findLocalParameters(String)
   */
  public Quantity findQuantity(String id) {
    Quantity nsb = findVariable(id);
    if (nsb == null) {
      List<LocalParameter> list = findLocalParameters(id);
      if (!list.isEmpty()) { 
      	return list.get(0); 
      }
    }
    return nsb;
  }
  
  /**
   * Searches for an instance of {@link QuantityWithUnit} within all of this
   * {@link Model}'s components that has the given identifier or name attribute
   * and returns it. There might be multiple instances of {@link LocalParameter}
   * with the same identifier or name, each located in different
   * {@link Reaction}s. In this case, the first match will be returned. Note
   * that also global {@link Parameter} instances have a higher priority and are
   * returned first.
   * 
   * @param idOrName
   * @return
   * @see #findLocalParameters(String)
   */
  public QuantityWithUnit findQuantityWithUnit(String idOrName) {
    QuantityWithUnit q = findSymbol(idOrName);
    if (q == null) {
      List<LocalParameter> list = findLocalParameters(idOrName);
      if (!list.isEmpty()) { 
        return list.get(0); 
      }
    }
    return q;
  }
  
  /**
   * Returns a {@link SortedSet} of identifiers of all {@link Reaction} elements
   * within
   * this {@link Model} whose {@link KineticLaw}s contain a
   * {@link LocalParameter} that has the given 'id' or <code>null</code> if no
   * element cannot be found.
   * 
   * @param id
   *        an id indicating an {@link LocalParameter} element of the
   *        {@link Model}.
   * @return a {@link SortedSet} of the identifiers of all {@link Reaction}
   *         elements within this {@link Model} whose {@link KineticLaw}
   *         contains a {@link LocalParameter} that has the given 'id' as id 
   *         or <code>null</code> if no such element with this 'id' can be
   *         found.
   */
  public SortedSet<String> findReactionsForLocalParameter(String id) 
  {
	  List<Reaction> reactionList = mapOfLocalParameters.get(id);
	  SortedSet<String> reactionIdSet = null;
	  
	  if (reactionList != null && reactionList.size() > 0)
	  {
		  reactionIdSet = new TreeSet<String>();
		  
		  for (Reaction reaction : reactionList)
		  {
			  if (reaction.isSetId()) 
			  {
				  reactionIdSet.add(reaction.getId());
			  }
		  }
		  
		  if (reactionIdSet.size() != reactionList.size())
		  {
			  logger.warn(MessageFormat.format("Some of the reactions containing the local" +
			  		" parameter {0} have no id defined !!", id));
		  }
	  }
	  
	  return reactionIdSet;
  }
  
  /**
   * Returns the {@link SimpleSpeciesReference} within all {@link Reaction}s of
   * this {@link Model} that has 'id' as id.
   * 
   * @param id
   *        the identifier of the {@link SimpleSpeciesReference} of interest.
   *        Note that this is not the identifier of the {@link Species} instance
   *        referred to by the {@link SimpleSpeciesReference}.
   * @return the {@link SimpleSpeciesReference} out of all {@link Reaction}s
   *         which has 'id' as id (or name depending on level and version).
   *         <code>null</code> if it doesn't exist.
   */
  public SimpleSpeciesReference findSimpleSpeciesReference(String id) {
    UniqueNamedSBase found = findUniqueNamedSBase(id);
    if ((found != null) && (found instanceof SimpleSpeciesReference)) {
      return (SimpleSpeciesReference) found;
    }
    return null;
  }
  
  /**
   * Returns the {@link SpeciesReference} with all {@link Reaction}s
   * of this {@link Model} that has 'id' as id.
   * 
   * @param id
   *        the identifier of the {@link SpeciesReference} of interest.
   *        Note that this is not the identifier of the {@link Species} instance
   *        referred to by the {@link SpeciesReference}.
   * @return the {@link SpeciesReference} out of all {@link Reaction}s
   *         which has 'id' as id (or name depending on level and version).
   *         <code>null</code> if it doesn't exist.
   */
  public SpeciesReference findSpeciesReference(String id) {
    SimpleSpeciesReference ssr = findSimpleSpeciesReference(id);
    if ((ssr != null) && (ssr instanceof SpeciesReference)) {
      return (SpeciesReference) ssr;
    }
    return null;
  }
  
  /**
   * Searches in the list of {@link Compartment}s, {@link Species}, and
   * {@link Parameter}s for the element with the given identifier.
   * 
   * @param id
   * @return a {@link Symbol} element with the given identifier or <code>null</code> if there
   *         is no such element.
   */
  public Symbol findSymbol(String id) {
    UniqueNamedSBase found = findUniqueNamedSBase(id);
    if ((found != null) && (found instanceof Symbol)) {
      return (Symbol) found;
    }
    return null;
  }
  
  /**
   * Returns a {@link UniqueNamedSBase} element that has the given 'id' within
   * this {@link Model} or <code>null</code> if no such element can be found.
   * 
   * @param id
   *        an id indicating an {@link UniqueNamedSBase} element of the
   *        {@link Model}.
   * @return a {@link UniqueNamedSBase} element of the {@link Model} that has
   *         the given 'id' as id or <code>null</code> if no element with this
   *         'id' can be found.
   */
  public UniqueNamedSBase findUniqueNamedSBase(String id) {
    return mapOfUniqueNamedSBases == null ? null : mapOfUniqueNamedSBases.get(id);
  }
  
  /**
   * Returns a {@link UnitDefinition} element that has the given 'id' within
   * this {@link Model} or <code>null</code> if no such element can be found.
   * 
   * @param id
   *        an id indicating an {@link UnitDefinition} element of the
   *        {@link Model}.
   * @return a {@link UniqueNamedSBase} element of the {@link Model} that has
   *         the given 'id' as id or <code>null</code> if no such element with
   *         this 'id' can be found.
   */
  public UnitDefinition findUnitDefinition(String id) {
    return mapOfUnitDefinitions == null ? null : mapOfUnitDefinitions.get(id);
  }
  
  /**
   * Searches the {@link Variable} with the given identifier in this model.
   * 
   * @param id
   *        The identifier of the {@link Variable} of interest.
   * @return the {@link Compartment}, {@link Species}, {@link SpeciesReference},
   *         or {@link Parameter}, which has 'variable' as id.
   */
  public Variable findVariable(String id) {
    UniqueNamedSBase found = findUniqueNamedSBase(id);
    if ((found != null) && (found instanceof Variable)) {
      return (Variable) found;
    }
    return null;
  }
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return true;
  }
  
  /**
   * Returns the area units ID of this {@link Model}.
   * 
   * @return the area units ID of this {@link Model}. Returns an empty
   *         {@link String} if it is not set.
   */
  public String getAreaUnits() {
    if (isSetAreaUnits()) {
      return areaUnitsID;
    } else if (getLevel() == 2) {
      return UnitDefinition.AREA;
    }
    return "";
  }
  
  /**
   * Returns the {@link UnitDefinition} instance which has the {@link #areaUnitsID} of
   * this {@link Model} as id.
   * 
   * @return the {@link UnitDefinition} instance which has the {@link #areaUnitsID} of
   *         this {@link Model} as id. <code>null</code> if it doesn't exist.
   */
  public UnitDefinition getAreaUnitsInstance() {
    return getUnitDefinition(getAreaUnits());
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
   */
  @Override
  public TreeNode getChildAt(int index) {
    if (index < 0) {
    	throw new IndexOutOfBoundsException(index + " < 0");
    }
    int count = super.getChildCount(), pos = 0;
    if (index < count) {
      return super.getChildAt(index);
    } else {
      index -= count;
    }
    if (isSetListOfFunctionDefinitions()) {
      if (pos == index) {
    	  return getListOfFunctionDefinitions();
      }
      pos++;
    }
    if (isSetListOfUnitDefinitions()) {
      if (pos == index) {
    	  return getListOfUnitDefinitions();
      }
      pos++;
    }
    if (isSetListOfCompartmentTypes()) {
      if (pos == index) {
    	  return getListOfCompartmentTypes();
      }
      pos++;
    }
    if (isSetListOfSpeciesTypes()) {
      if (pos == index) {
    	  return getListOfSpeciesTypes();
      }
      pos++;
    }
    if (isSetListOfCompartments()) {
      if (pos == index) {
    	  return getListOfCompartments();
      }
      pos++;
    }
    if (isSetListOfSpecies()) {
      if (pos == index) {
    	  return getListOfSpecies();
      }
      pos++;
    }
    if (isSetListOfParameters()) {
      if (pos == index) {
    	  return getListOfParameters();
      }
      pos++;
    }
    if (isSetListOfInitialAssignments()) {
      if (pos == index) {
    	  return getListOfInitialAssignments();
      }
      pos++;
    }
    if (isSetListOfRules()) {
      if (pos == index) {
    	  return getListOfRules();
      }
      pos++;
    }
    if (isSetListOfConstraints()) {
      if (pos == index) {
    	  return getListOfConstraints();
      }
      pos++;
    }
    if (isSetListOfReactions()) {
      if (pos == index) {
    	  return getListOfReactions();
      }
      pos++;
    }
    if (isSetListOfEvents()) {
      if (pos == index) {
    	  return getListOfEvents();
      }
      pos++;
    }
    throw new IndexOutOfBoundsException(String.format("Index %d >= %d", index,
      +((int) Math.min(pos, 0))));
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildCount()
   */
  @Override
  public int getChildCount() {
    int children = super.getChildCount();
    if (isSetListOfFunctionDefinitions()) {
      children++;
    }
    if (isSetListOfUnitDefinitions()) {
      children++;
    }
    if (isSetListOfCompartmentTypes()) {
      children++;
    }
    if (isSetListOfSpeciesTypes()) {
      children++;
    }
    if (isSetListOfCompartments()) {
      children++;
    }
    if (isSetListOfSpecies()) {
      children++;
    }
    if (isSetListOfParameters()) {
      children++;
    }
    if (isSetListOfInitialAssignments()) {
      children++;
    }
    if (isSetListOfRules()) {
      children++;
    }
    if (isSetListOfConstraints()) {
      children++;
    }
    if (isSetListOfReactions()) {
      children++;
    }
    if (isSetListOfEvents()) {
      children++;
    }
    return children;
  }
  
  /**
   * Gets the nth {@link Compartment} instance of the listOfCompartments.
   * 
   * @param n
   * @return the nth {@link Compartment} instance of the listOfCompartments.
   *         <code>null</code> if if the listOfCompartments is not set.
   */
  public Compartment getCompartment(int n) {
    return getListOfCompartments().get(n);
  }
  
  /**
   * Gets the {@link Compartment} of the listOfCompartments which has 'id' as
   * id.
   * 
   * @param id
   * @return the {@link Compartment} of the listOfCompartments which has 'id' as
   *         id (or name depending on the version and level). <code>null</code> if if the
   *         listOfCompartments is not set.
   */
  public Compartment getCompartment(String id) {
    UniqueNamedSBase found = findUniqueNamedSBase(id);
    if ((found != null) && (found instanceof Compartment)) {
      return (Compartment) found;
    }
    return null;
  }
  
  /**
   * Returns the number of {@link Compartment}s of this {@link Model}.
   * 
   * @return the number of {@link Compartment}s of this {@link Model}.
   */
  public int getCompartmentCount() {
    return isSetListOfCompartments() ? listOfCompartments.size() : 0;
  }
  
  /**
   * Gets the nth CompartmentType object in this Model.
   * 
   * @param n
   *        index
   * @return the nth CompartmentType of this Model. Returns <code>null</code> if there are no
   *         compartmentType defined or if the index n is too big or lower than
   *         zero.
   * @deprecated Only valid in SBML Level 2 for Versions 2 through 4.
   */
  @Deprecated
  public CompartmentType getCompartmentType(int n) {
    return getListOfCompartmentTypes().get(n);
  }
  
  /**
   * Gets the {@link CompartmentType} with the given <code>id</code>.
   * 
   * @param id
   * @return the CompartmentType of the {@link #listOfCompartmentTypes} which has 'id' as
   *         id (or name depending on the level and version). <code>null</code> if the
   *         {@link #listOfCompartmentTypes} is not set or the id is not found.
   * @deprecated Only valid in SBML Level 2 for Versions 2 through 4.
   */
  @Deprecated
  public CompartmentType getCompartmentType(String id) {
    UniqueNamedSBase found = findUniqueNamedSBase(id);
    if ((found != null) && (found instanceof CompartmentType)) {
      return (CompartmentType) found;
    }
    return null;
  }
  
  /**
   * Returns the number of {@link CompartmentType}s of this {@link Model}.
   * 
   * @return the number of {@link CompartmentType}s of this {@link Model}.
   * @deprecated using {@link CompartmentType} is not recommended, because this
   *             data structure is only valid in SBML Level 2 for Versions 2
   *             through 4.
   */
  @Deprecated
  public int getCompartmentTypeCount() {
    return isSetListOfCompartmentTypes() ? listOfCompartmentTypes.size() : 0;
  }

  /**
   * Gets the nth Constraint object in this Model.
   * 
   * @param n
   * @return the nth Constraint of this Model. Returns <code>null</code> if there are no
   *         constraint defined or if the index n is too big or lower than zero.
   */
  public Constraint getConstraint(int n) {
    return getListOfConstraints().get(n);
  }
  
  /**
   * Returns the number of {@link Constraint}s of this {@link Model}.
   * 
   * @return the number of {@link Constraint}s of this {@link Model}.
   */
  public int getConstraintCount() {
    return isSetListOfConstraints() ? listOfConstraints.size() : 0;
  }
  
  /**
   * Returns the conversionFactor ID of this {@link Model}.
   * 
   * @return the conversionFactorID of this {@link Model}. Returns an empty
   *         {@link String} if it is not set.
   */
  public String getConversionFactor() {
    return isSetConversionFactor() ? conversionFactorID : "";
  }
  
  /**
   * Returns the Parameter instance which has the conversionFactorID of this
   * Model as id.
   * 
   * @return the Parameter instance which has the conversionFactorID of this
   *         Model as id. <code>null</code> if it doesn't exist
   */
  public Parameter getConversionFactorInstance() {
    return getParameter(this.conversionFactorID);
  }
  
  /**
   * Returns the number of {@link Delay}s of this {@link Model}.
   * 
   * @return the number of {@link Delay}s of this {@link Model}.
   */
  public int getDelayCount() {
    int count = 0;
    for (Event e : getListOfEvents()) {
      if (e.isSetDelay()) {
        count++;
      }
    }
    return count;
  }
  
  /**
   * Gets the nth Event object in this Model.
   * 
   * @param n
   * @return the nth Event of this Model. Returns <code>null</code> if there are no event
   *         defined or if the index n is too big or lower than zero.
   */
  public Event getEvent(int n) {
    return getListOfEvents().get(n);
  }
  
  /**
   * Gets the {@link Event} which as the given <code>id</code> as id.
   * 
   * @param id
   * @return the {@link Event} of the {@link #listOfEvents} which has 'id' as id
   *         (or name depending on the level and version). <code>null</code> if if the
   *         {@link #listOfEvents} is not set.
   */
  public Event getEvent(String id) {
    UniqueNamedSBase found = findUniqueNamedSBase(id);
    if ((found != null) && (found instanceof Event)) {
      return (Event) found;
    }
    return null;
  }
  
  /**
   * Returns the number of {@link EventAssignment}s of this {@link Model}.
   * 
   * @return return the number of {@link EventAssignment}s of this {@link Model}.
   */
  public int getEventAssignmentCount() {
    int count = 0;
    for (Event e : getListOfEvents()) {
      count += e.getEventAssignmentCount();
    }
    return count;
  }
  
  /**
   * Returns the number of {@link Event}s of this {@link Model}.
   * 
   * @return the number of {@link Event}s of this {@link Model}.
   */
  public int getEventCount() {
    return isSetListOfEvents() ? listOfEvents.size() : 0;
  }
  
  /**
   * Returns the extent units ID of this {@link Model}.
   * 
   * @return the extent units ID of this {@link Model}. Returns an empty
   *         {@link String} if it is not set.
   */
  public String getExtentUnits() {
    return isSetExtentUnits() ? extentUnitsID : "";
  }
  
  /**
   * Returns the {@link UnitDefinition} instance which has the extent units ID
   * of this Model as id.
   * 
   * @return the {@link UnitDefinition} instance which has the extent units ID
   *         of this Model as id. <code>null</code> if it doesn't exist
   */
  public UnitDefinition getExtentUnitsInstance() {
    return getUnitDefinition(getExtentUnits());
  }
  
  /**
   * Gets the nth {@link FunctionDefinition} instance of the
   * listOfFunstionDefinitions.
   * 
   * @param n
   * @return the nth {@link FunctionDefinition} instance of the
   *         listOfFunstionDefinitions. <code>null</code> if if the listOfFunctionDefinitions
   *         is not set.
   */
  public FunctionDefinition getFunctionDefinition(int n) {
    return getListOfFunctionDefinitions().get(n);
  }
  
  /**
   * Returns the {@link FunctionDefinition} of the {@link #listOfFunctionDefinitions}
   * which has 'id' as id.
   * 
   * @param id
   * @return the {@link FunctionDefinition} of the {@link #listOfFunctionDefinitions}
   *         which has 'id' as id (or name depending on the level and version).
   *         <code>null</code> if if the {@link #listOfFunctionDefinitions} is not set.
   */
  public FunctionDefinition getFunctionDefinition(String id) {
    UniqueNamedSBase found = findUniqueNamedSBase(id);
    if ((found != null) && (found instanceof FunctionDefinition)) {
      return (FunctionDefinition) found;
    }
    return null;
  }
  
  /**
   * Returns the number of {@link FunctionDefinition}s of this {@link Model}.
   * 
   * @return the number of {@link FunctionDefinition}s of this {@link Model}.
   */
  public int getFunctionDefinitionCount() {
    return isSetListOfFunctionDefinitions() ? listOfFunctionDefinitions.size()
      : 0;
  }
  
  /**
   * Gets the nth {@link InitialAssignment} object in this {@link Model}.
   * 
   * @param n
   * @return the nth {@link InitialAssignment} of this {@link Model}. <code>null</code> if
   *         the listOfInitialAssignments is not set.
   */
  public InitialAssignment getInitialAssignment(int n) {
    return getListOfInitialAssignments().get(n);
  }
  
  /**
   * Returns the {@link InitialAssignment} of the
   * {@link #listOfInitialAssignments} whose <code>symbol</code> attribute,
   * i.e., whose {@link Variable} has the given <code>variable</code> as
   * identifier.
   * 
   * @param variable
   *        The identifier of a variable, for which a corresponding
   *        {@link InitialAssignment} is requested.
   * @return the first {@link InitialAssignment} of the
   *         {@link #listOfInitialAssignments}, whose {@link Variable} has the
   *         <code>variable</code> as identifier (or name depending on the level
   *         and version). <code>null</code> if it doesn't exist.
   */
  public InitialAssignment getInitialAssignment(String variable) {
    return getListOfInitialAssignments().firstHit(
      new AssignmentVariableFilter(variable));
  }
  
  /**
   * Returns the number of {@link InitialAssignment}s of this {@link Model}.
   * 
   * @return the number of {@link InitialAssignment}s of this {@link Model}.
   */
  public int getInitialAssignmentCount() {
    return isSetListOfInitialAssignments() ? listOfInitialAssignments.size()
      : 0;
  }
  
  /**
   * Returns the number of {@link KineticLaw}s of this {@link Model}.
   * 
   * @return the number of {@link KineticLaw}s of this {@link Model}.
   */
  public int getKineticLawCount() {
    int count = 0;
    for (Reaction r : getListOfReactions()) {
      if (r.isSetKineticLaw()) {
        count++;
      }
    }
    return count;
  }
  
  /**
   * Returns the last element added in the given list.
   * 
   * @return the last element added in the model, corresponding to the last
   *         element of the list of these elements, or <code>null</code> is no element exist
   *         for this type.
   */
  private <T> T getLastElementOf(ListOf<? extends T> listOf) {
    // added casting and parenthesis because there was a compilation error
    // when using the ant script
    return ((listOf == null) || (listOf.size() == 0)) ? (T) null : (T) listOf
        .getLast();
  }
  
  /**
   * Returns the length units ID of this {@link Model}.
   * 
   * @return the length units ID of this {@link Model}. Returns an empty
   *         {@link String} if it is not set.
   */
  public String getLengthUnits() {
    if (isSetLengthUnits()) {
      return lengthUnitsID;
    } else if (getLevel() == 2) { return UnitDefinition.LENGTH; }
    return "";
  }
  
  /**
   * Returns the length units of this {@link Model} as a {@link UnitDefinition}.
   * 
   * @return the {@link UnitDefinition} instance which has the {@link #lengthUnitsID} of this
   *         {@link Model} as id. <code>null</code> if it doesn't exist.
   */
  public UnitDefinition getLengthUnitsInstance() {
    return getUnitDefinition(getLengthUnits());
  }
  
  /**
   * Returns the listOfCompartments of this {@link Model}.
   * 
   * @return the listOfCompartments of this {@link Model}.
   */
  public ListOf<Compartment> getListOfCompartments() {
    if (listOfCompartments == null) {
      listOfCompartments = ListOf.newInstance(this, Compartment.class);
    }
    return listOfCompartments;
  }
  
  /**
   * Returns the listOfCompartmentTypes of this {@link Model}.
   * 
   * @return the listOfCompartmentTypes of this {@link Model}.
   * @deprecated Only valid in SBML Level 2 for Versions 2 through 4.
   */
  @Deprecated
  public ListOf<CompartmentType> getListOfCompartmentTypes() {
    if (listOfCompartmentTypes == null) {
      listOfCompartmentTypes = ListOf.newInstance(this, CompartmentType.class);
    }
    return listOfCompartmentTypes;
  }
  
  /**
   * Returns the listOfConstraints of this {@link Model}.
   * 
   * @return the listOfConstraints of this {@link Model}. set.
   */
  public ListOf<Constraint> getListOfConstraints() {
    if (listOfConstraints == null) {
      listOfConstraints = ListOf.newInstance(this, Constraint.class);
    }
    return listOfConstraints;
  }
  
  /**
   * Returns the number of {@link ListOf}s of this {@link Model}.
   * 
   * @return the number of {@link ListOf}s of this {@link Model}.
   */
  public int getListOfCount() {
    return getChildCount();
  }
  
  /**
   * Returns the listOfEvents of this {@link Model}.
   * 
   * @return the listOfEvents of this {@link Model}.
   */
  public ListOf<Event> getListOfEvents() {
    if (listOfEvents == null) {
      listOfEvents = ListOf.newInstance(this, Event.class);
    }
    return listOfEvents;
  }
  
  /**
   * Returns the listOfFunctionDefinitions of this {@link Model}.
   * 
   * @return the listOfFunctionDefinitions of this {@link Model}.
   */
  public ListOf<FunctionDefinition> getListOfFunctionDefinitions() {
    if (listOfFunctionDefinitions == null) {
      listOfFunctionDefinitions = ListOf.newInstance(this,
        FunctionDefinition.class);
    }
    return listOfFunctionDefinitions;
  }
  
  /**
   * Returns the listOfInitialAssignments of this {@link Model}.
   * 
   * @return the listOfInitialAssignments of this {@link Model}.
   */
  public ListOf<InitialAssignment> getListOfInitialAssignments() {
    if (listOfInitialAssignments == null) {
      listOfInitialAssignments = ListOf.newInstance(this,
        InitialAssignment.class);
    }
    return listOfInitialAssignments;
  }
  
  /**
   * Returns the listOfParameters of this {@link Model}.
   * 
   * @return the listOfParameters of this {@link Model}.
   */
  public ListOf<Parameter> getListOfParameters() {
    if (listOfParameters == null) {
      listOfParameters = ListOf.newInstance(this, Parameter.class);
    }
    return listOfParameters;
  }
  
  /**
   * 
   * @return
   */
  public List<UnitDefinition> getListOfPredefinedUnitDefinitions() {
    return (listOfPredefinedUnitDefinitions != null) ? listOfPredefinedUnitDefinitions
        : new ArrayList<UnitDefinition>(0);
  }
  
  /**
   * Returns the listOfReactions of this {@link Model}.
   * 
   * @return the listOfReactions of this {@link Model}.
   */
  public ListOf<Reaction> getListOfReactions() {
    if (listOfReactions == null) {
      listOfReactions = ListOf.newInstance(this, Reaction.class);
    }
    return listOfReactions;
  }
  
  /**
   * Returns the listOfRules of this {@link Model}.
   * 
   * @return the listOfRules of this {@link Model}.
   */
  public ListOf<Rule> getListOfRules() {
    if (listOfRules == null) {
      listOfRules = ListOf.newInstance(this, Rule.class);
    }
    return listOfRules;
  }
  
  /**
   * Returns the listOfSpecies of this {@link Model}.
   * 
   * @return the listOfSpecies of this {@link Model}.
   */
  public ListOf<Species> getListOfSpecies() {
    if (listOfSpecies == null) {
      listOfSpecies = ListOf.newInstance(this, Species.class);
    }
    return listOfSpecies;
  }
  
  /**
   * Returns the listOfSpeciesTypes of this {@link Model}.
   * 
   * @return the listOfSpeciesTypes of this {@link Model}.
   * @deprecated Only valid in SBML Level 2 for Versions 2 through 4.
   */
  @Deprecated
  public ListOf<SpeciesType> getListOfSpeciesTypes() {
    if (listOfSpeciesTypes == null) {
      listOfSpeciesTypes = ListOf.newInstance(this, SpeciesType.class);
    }
    return listOfSpeciesTypes;
  }
  
  /**
   * Returns the listOfUnitDefinitions of this {@link Model}.
   * 
   * @return the listOfUnitDefinitions of this {@link Model}.
   */
  public ListOf<UnitDefinition> getListOfUnitDefinitions() {
    if (listOfUnitDefinitions == null) {
      listOfUnitDefinitions = ListOf.newInstance(this, UnitDefinition.class);
    }
    return listOfUnitDefinitions;
  }
  
  /**
   * Returns the number of parameters that are contained within kineticLaws in
   * the reactions of this model.
   * 
   * @return the number of parameters that are contained within kineticLaws in
   *         the reactions of this model.
   */
  public int getLocalParameterCount() {
    int count = 0;
    if (isSetListOfReactions()) {
      for (Reaction reaction : getListOfReactions()) {
        if (reaction.isSetKineticLaw()) {
          count += reaction.getKineticLaw().getLocalParameterCount();
        }
      }
    }
    return count;
  }
  
  /**
   * Returns the number of elements that can contain math in the {@link Model} .
   * 
   * @return the number of elements that can contain math in the {@link Model} .
   * @see MathContainer
   */
  public int getMathContainerCount() {
    return getFunctionDefinitionCount() + getInitialAssignmentCount()
        + getEventAssignmentCount() + getDelayCount() + getConstraintCount()
        + getRuleCount() + getTriggerCount() + getStoichiometryMathCount()
        + getKineticLawCount();
  }
  
  /**
   * Returns {@link History} of this model.
   * 
   * @return the {@link History} of this model.
   * 
   * @see SBase#getHistory
   * @deprecated use {@link SBase#getHistory()}
   */
  @Deprecated
  public History getModelHistory() {
    return getHistory();
  }
  
  /**
   * Returns the number of {@link ModifierSpeciesReference}s in the
   * {@link Model}.
   * 
   * @return the number of {@link ModifierSpeciesReference}s in the
   *         {@link Model}.
   */
  public int getModifierSpeciesReferenceCount() {
    int count = 0;
    for (Reaction r : getListOfReactions()) {
      count += r.getModifierCount();
    }
    return count;
  }
  
  /**
   * Returns all {@link ModifierSpeciesReference}s, registered in
   * any {@link Reaction}.
   * @return 
   * @return all {@link ModifierSpeciesReference} contained in this {@link Model}.
   */
  public Set<ModifierSpeciesReference> getModifierSpeciesReferences() {
    Set<ModifierSpeciesReference> listOfModifiers = new HashSet<ModifierSpeciesReference>();
    for (Reaction r : getListOfReactions()) {
      if (r.isSetListOfModifiers()) {
        listOfModifiers.addAll( r.getListOfModifiers() );
      }
    }
    return listOfModifiers;
  }
  
  /**
   * Returns the number of {@link NamedSBase}s in the {@link Model}, so elements
   * that can have a name.
   * 
   * @return the number of {@link NamedSBase}s in the {@link Model}, so elements
   *         that can have a name.
   */
  public int getNamedSBaseCount() {
    return getNamedSBaseWithDerivedUnitCount() + 1 /* this model */
        + getSpeciesTypeCount() + getCompartmentTypeCount()
        + getUnitDefinitionCount() + getEventCount()
        + getModifierSpeciesReferenceCount();
  }
  
  public int getNamedSBaseWithDerivedUnitCount() {
    return getQuantityCount() + getFunctionDefinitionCount() + getReactionCount();
  }
  
  /**
   * Returns the number of {@link Compartment}s of this {@link Model}.
   * 
   * @return the number of {@link Compartment}s of this {@link Model}.
   * @deprecated use {@link #getCompartmentCount()}
   */
  @Deprecated
  public int getNumCompartments() {
    return getCompartmentCount();
  }
  
  /**
   * Returns the number of {@link CompartmentType}s of this {@link Model}.
   * 
   * @return the number of {@link CompartmentType}s of this {@link Model}.
   * @deprecated use {@link #getCompartmentTypeCount()}
   */
  @Deprecated
  public int getNumCompartmentTypes() {
    return getCompartmentTypeCount();
  }
  
  /**
   * Returns the number of {@link Constraint}s of this {@link Model}.
   * 
   * @return the number of {@link Constraint}s of this {@link Model}.
   * @deprecated use {@link #getConstraintCount()}
   */
  @Deprecated
  public int getNumConstraints() {
    return getConstraintCount();
  }
  
  /**
   * Returns the number of {@link Delay}s of this {@link Model}.
   * 
   * @return the number of {@link Delay}s of this {@link Model}.
   * @deprecated use {@link #getDelayCount()}
   */
  @Deprecated
  public int getNumDelays() {
    return getDelayCount();
  }
  
  /**
   * Returns the number of {@link EventAssignment}s of this {@link Model}.
   * 
   * @return return the number of {@link EventAssignment}s of this {@link Model}.
   * @deprecated use {@link #getEventAssignmentCount()}
   */
  @Deprecated
  public int getNumEventAssignments() {
    return getEventAssignmentCount();
  }
  
  /**
   * Returns the number of {@link Event}s of this {@link Model}.
   * 
   * @return the number of {@link Event}s of this {@link Model}.
   * @deprecated use {@link #getEventCount()}
   */
  @Deprecated
  public int getNumEvents() {
    return getEventCount();
  }
  
  /**
   * Returns the number of {@link FunctionDefinition}s of this {@link Model}.
   * 
   * @return the number of {@link FunctionDefinition}s of this {@link Model}.
   * @deprecated use {@link #getFunctionDefinitionCount()}
   */
  @Deprecated
  public int getNumFunctionDefinitions() {
    return getFunctionDefinitionCount();
  }
  
  /**
   * Returns the number of {@link InitialAssignment}s of this {@link Model}.
   * 
   * @return the number of {@link InitialAssignment}s of this {@link Model}.
   * @deprecated use {@link #getInitialAssignmentCount()}
   */
  @Deprecated
  public int getNumInitialAssignments() {
    return getInitialAssignmentCount();
  }
  
  /**
   * Returns the number of {@link KineticLaw}s of this {@link Model}.
   * 
   * @return the number of {@link KineticLaw}s of this {@link Model}.
   * @deprecated use {@link #getKineticLawCount()}
   */
  @Deprecated
  public int getNumKineticLaws() {
    return getKineticLawCount();
  }
  
  /**
   * Returns the number of {@link ListOf}s of this {@link Model}.
   * 
   * @return the number of {@link ListOf}s of this {@link Model}.
   * @deprecated use {@link #getListOfCount()}
   */
  @Deprecated
  public int getNumListsOf() {
    return getListOfCount();
  }
  
  /**
   * Returns the number of parameters that are contained within kineticLaws in
   * the reactions of this model.
   * 
   * @return the number of parameters that are contained within kineticLaws in
   *         the reactions of this model.
   * @deprecated use {@link #getLocalParameterCount()}
   */
  @Deprecated
  public int getNumLocalParameters() {
    return getLocalParameterCount();
  }
  
  /**
   * Returns the number of elements that can contain math in the {@link Model} .
   * 
   * @return the number of elements that can contain math in the {@link Model} .
   * @see MathContainer
   * @deprecated use {@link #getMathContainerCount()}
   */
  @Deprecated
  public int getNumMathContainers() {
    return getMathContainerCount();
  }
  
  /**
   * Returns the number of {@link ModifierSpeciesReference}s in the
   * {@link Model}.
   * 
   * @return the number of {@link ModifierSpeciesReference}s in the
   *         {@link Model}.
   * @deprecated use {@link #getModifierSpeciesReferenceCount()}
   */
  @Deprecated
  public int getNumModifierSpeciesReferences() {
    return getModifierSpeciesReferenceCount();
  }
  
  /**
   * Returns the number of {@link NamedSBase}s in the {@link Model}, so elements
   * that can have a name.
   * 
   * @return the number of {@link NamedSBase}s in the {@link Model}, so elements
   *         that can have a name.
   * @deprecated use {@link #getNamedSBaseCount()}
   */
  @Deprecated
  public int getNumNamedSBases() {
    return getNamedSBaseCount();
  }
  
  /**
   * Returns the number of {@link NamedSBaseWithDerivedUnit}s in the
   * {@link Model}, so elements that can have a name and a unit that can be
   * derived.
   * 
   * @return the number of {@link NamedSBaseWithDerivedUnit}s in the
   *         {@link Model}, so elements that can have a name and a unit that can
   *         be derived.
   * @deprecated use {@link #getNamedSBaseWithDerivedUnitCount()}
   */
  @Deprecated
  public int getNumNamedSBasesWithDerivedUnit() {
    return getNamedSBaseWithDerivedUnitCount();
  }
  
  /**
   * Returns the number of {@link Parameter}s of this {@link Model}.
   * 
   * @return the number of {@link Parameter}s of this {@link Model}.
   * @deprecated use {@link #getParameterCount()}
   */
  @Deprecated
  public int getNumParameters() {
    return getParameterCount();
  }
  
  /**
   * Returns the number of {@link Quantity}s of this {@link Model}.
   * 
   * @return the number of {@link Quantity}s of this {@link Model}.
   * @deprecated use {@link #getQuantityCount()}
   */
  @Deprecated
  public int getNumQuantities() {
    return getQuantityCount();
  }
  
  /**
   * Returns the number of {@link QuantityWithUnit}s of this {@link Model}.
   * 
   * @return the number of {@link QuantityWithUnit}s of this {@link Model}.
   * @deprecated use {@link #getQuantityWithUnitCount()}
   */
  @Deprecated
  public int getNumQuantitiesWithUnit() {
    return getQuantityWithUnitCount();
  }
  
  /**
   * Returns the number of {@link Reaction}s of this {@link Model}.
   * 
   * @return the number of {@link Reaction}s of this {@link Model}.
   * @deprecated use {@link #getReactionCount()}
   */
  @Deprecated
  public int getNumReactions() {
    return getReactionCount();
  }
  
  /**
   * Returns the number of {@link Rule}s of this {@link Model}.
   * 
   * @return the number of {@link Rule}s of this {@link Model}.
   * @deprecated use {@link #getRuleCount()}
   */
  @Deprecated
  public int getNumRules() {
    return getRuleCount();
  }
  
  /**
   * Returns the number of {@link SBase}s of this {@link Model}.
   * 
   * @return the number of {@link SBase}s of this {@link Model}.
   * @deprecated use {@link #getSBaseCount()}
   */
  @Deprecated
  public int getNumSBases() {
    return getSBaseCount();
  }
  
  /**
   * Returns the number of {@link SBaseWithDerivedUnit}s of this {@link Model}.
   * 
   * @return the number of {@link SBaseWithDerivedUnit}s of this {@link Model}.
   * @deprecated use {@link #getSBaseWithDerivedUnitCount()}
   */
  @Deprecated
  public int getNumSBasesWithDerivedUnit() {
    return getSBaseWithDerivedUnitCount(); 
  }
  
  /**
   * Returns the number of {@link Species} of this {@link Model}.
   * 
   * @return the number of {@link Species} of this {@link Model}.
   * @deprecated use {@link #getSpeciesCount()}
   */
  @Deprecated
  public int getNumSpecies() {
    return getSpeciesCount();
  }
  
  /**
   * Returns the number of {@link SpeciesReferences}s of this {@link Model}.
   * 
   * @return the number of {@link SpeciesReferences}s of this {@link Model}.
   * @deprecated use {@link #getSpeciesReferenceCount()}
   */
  @Deprecated
  public int getNumSpeciesReferences() {
    return getSpeciesReferenceCount();
  }
  
  /**
   * Returns the number of {@link SpeciesType}s of this {@link Model}.
   * 
   * @return the number of {@link SpeciesType}s of this {@link Model}.
   * @deprecated use {@link #getSpeciesTypeCount()}
   */
  @Deprecated
  public int getNumSpeciesTypes() {
    return getSpeciesTypeCount();
  }
  
  /**
   * Returns the number of {@link Species} whose boundary condition is set to
   * {@code true}.
   * 
   * @return the number of {@link Species} whose boundary condition is set to
   *         {@code true}.
   * @deprecated use {@link #getSpeciesWithBoundaryConditionCount()}
   */
  @Deprecated
  public int getNumSpeciesWithBoundaryCondition() {
    return getSpeciesWithBoundaryConditionCount();
  }
  
  /**
   * Returns the number of {@link StoichiometryMath} in the {@link Model}.
   * 
   * @return the number of {@link StoichiometryMath} in the {@link Model}.
   * @deprecated use {@link #getStoichiometryMathCount()}
   */
  @Deprecated
  public int getNumStoichiometryMath() {
    return getStoichiometryMathCount();
  }
  
  /**
   * Returns the number of all instances of {@link Symbol} referenced within the
   * model. There is no dedicated list for {@link Symbol}s. This is a convenient
   * method to support working with the model data structure.
   * 
   * @return The number of {@link Compartment}s, {@link Species}, and
   *         {@link Parameter}s in the model.
   * @deprecated use {@link #getSymbolCount()}
   */
  @Deprecated
  public int getNumSymbols() {
    return getSymbolCount();
  }
  
  /**
   * Returns the number of {@link Trigger} of this {@link Model}.
   * 
   * @return the number of {@link Trigger} of this {@link Model}.
   * @deprecated use {@link #getTriggerCount()}
   */
  @Deprecated
  public int getNumTriggers() {
    return getTriggerCount();
  }
  
  /**
   * Returns the number of {@link UnitDefinition}s of this {@link Model}.
   * 
   * @return the number of {@link UnitDefinition}s of this {@link Model}.
   * @deprecated use {@link #getUnitDefinitionCount()}
   */
  @Deprecated
  public int getNumUnitDefinitions() {
    return getUnitDefinitionCount();
  }
  
  /**
   * Returns the number of {@link Unit}s of this {@link Model}.
   * 
   * @return the number of {@link Unit}s of this {@link Model}.
   * @deprecated use {@link #getUnitCount()}
   */
  @Deprecated
  public int getNumUnits() {
    return getUnitCount();
  }
  
  /**
   * Returns the number of {@link Variable}s of this {@link Model}.
   * 
   * @return the number of {@link Variable}s of this {@link Model}.
   * @deprecated use {@link #getVariableCount()}
   */
  @Deprecated
  public int getNumVariables() {
    return getVariableCount();
  }
  
  /**
   * Gets the nth {@link Parameter} object in this {@link Model}.
   * 
   * @param n
   *        index
   * @return the nth {@link Parameter} of this {@link Model}.
   */
  public Parameter getParameter(int n) {
    return getListOfParameters().get(n);
  }
  
  /**
   * Returns the {@link Parameter} of the listOfParameters which has 'id' as id
   * 
   * @param id
   * @return the {@link Parameter} of the listOfParameters which has 'id' as id
   *         (or name depending on the level and version). <code>null</code> if it doesn't
   *         exist.
   */
  public Parameter getParameter(String id) {
    return getListOfParameters().firstHit(new NameFilter(id));
  }
  
  /**
   * Returns the number of {@link Parameter}s of this {@link Model}.
   * 
   * @return the number of {@link Parameter}s of this {@link Model}.
   */
  public int getParameterCount() {
    return isSetListOfParameters() ? listOfParameters.size() : 0;
  }
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getParent()
   */
  @Override
  public SBMLDocument getParent() {
    return (SBMLDocument) super.getParent();
  }
  
  /**
   * Returns a UnitDefinition representing one of the predefined units of SBML,
   * returns <code>null</code> if the given unit kind is not a valid one for the SBML level
   * and version of this {@link Model}.
   * 
   * @param unitKind
   *        a unit kind for one of the predefined units from the SBML
   *        Specifications
   * @return a UnitDefinition representing one of the predefined units of SBML,
   *         <code>null</code> if the unitKind is invalid.
   */
  public UnitDefinition getPredefinedUnitDefinition(String unitKind) {
    // TODO: This might be more efficient than ALWAYS storing ALL base units in the listOfPredefinedUnits:
//    if ((unitKind != null) && !unitKind.isEmpty()) {
//      int level = getLevel(), version = getVersion();
//      if (Unit.isUnitKind(unitKind, level, version)) {
//        UnitDefinition ud = new UnitDefinition(unitKind + "_base", level, version);
//        ud.addUnit(Unit.Kind.valueOf(unitKind.toUpperCase()));
//        return ud;
//      }
//    }
    if (listOfPredefinedUnitDefinitions != null) {
      for (UnitDefinition unitDefinition : listOfPredefinedUnitDefinitions) {
        // TODO: It can never happen that a unitDef has the id that is a unit kind.
        if (unitDefinition.getId().equals(unitKind)
        		|| unitDefinition.getId().equals(unitKind + "_base")) 
        { 
        	return unitDefinition; 
        }
      }
    }
    return null;
  }
  
  /**
   * Returns the number of {@link Quantity}s of this {@link Model}.
   * 
   * @return the number of {@link Quantity}s of this {@link Model}.
   */
  public int getQuantityCount() {
    return getNumVariables() + getLocalParameterCount();
  }
  
  /**
   * Returns the number of {@link QuantityWithUnit}s of this {@link Model}.
   * 
   * @return the number of {@link QuantityWithUnit}s of this {@link Model}.
   */
  public int getQuantityWithUnitCount() {
    return getNumSymbols() + getLocalParameterCount();
  }
  
  /**
   * Gets the n-th {@link Reaction} object in this Model.
   * 
   * @param reactionIndex
   * @return the n-th {@link Reaction} of this Model.
   */
  public Reaction getReaction(int n) {
    return getListOfReactions().get(n);
  }
  
  /**
   * Returns the {@link Reaction} of the {@link #listOfReactions} which has 'id' as id.
   * 
   * @param id
   * @return the {@link Reaction} of the {@link #listOfReactions} which has 'id' as id
   *         (or name depending on the level and version). <code>null</code> if it doesn't
   *         exist.
   */
  public Reaction getReaction(String id) {
    UniqueNamedSBase found = findUniqueNamedSBase(id);
    if ((found != null) && (found instanceof Reaction)) {
      return (Reaction) found;
    }
    return null;
  }
  
  /**
   * Returns the number of {@link Reaction}s of this {@link Model}.
   * 
   * @return the number of {@link Reaction}s of this {@link Model}.
   */
  public int getReactionCount() {
    return isSetListOfReactions() ? listOfReactions.size() : 0;
  }
  
  /**
   * Gets the nth {@link Rule} of the listOfRules.
   * 
   * @param n
   *        an index
   * @return the nth {@link Rule} of the listOfRules. <code>null</code> if it doesn't exist.
   */
  public Rule getRule(int n) {
    return getListOfRules().get(n);
  }
  
  /**
   * Searches for the first instance of {@link ExplicitRule} within this
   * {@link Model}'s {@link #listOfRules}, whose variable attribute is set to
   * the value passed to this method.
   * 
   * @param variable
   * @return <code>null</code> if no element with the required property exists.
   */
  public ExplicitRule getRule(String variable) {
    Rule rule = listOfRules.firstHit(new AssignmentVariableFilter(variable));
    return (rule != null) && (rule instanceof ExplicitRule) ? (ExplicitRule) rule
        : null;
  }
  
  /**
   * Returns the number of {@link Rule}s of this {@link Model}.
   * 
   * @return the number of {@link Rule}s of this {@link Model}.
   */
  public int getRuleCount() {
    return isSetListOfRules() ? listOfRules.size() : 0;
  }
  
  /**
   * Returns the number of {@link SBase}s of this {@link Model}.
   * 
   * @return the number of {@link SBase}s of this {@link Model}.
   */
  public int getSBaseCount() {
    int count = getNamedSBaseCount() - getFunctionDefinitionCount()
        + getMathContainerCount() + getListOfCount() + getUnitCount() + 1;
    // one for this model
    if (getParent() != null) {
      count++; // the owning SBML document.
    }
    return count;
  }
  
  /**
   * Returns the number of {@link SBaseWithDerivedUnit}s of this {@link Model}.
   * 
   * @return the number of {@link SBaseWithDerivedUnit}s of this {@link Model}.
   */
  public int getSBaseWithDerivedUnitCount() {
    return getNamedSBaseWithDerivedUnitCount() + getMathContainerCount()
        - getFunctionDefinitionCount();
  }
  
  /**
   * Gets the n-th {@link Species} object in this Model.
   * 
   * @param n
   *        an index
   * @return the {@link Species} with the given index if it exists, 
   *         <code>null</code> otherwise.
   */
  public Species getSpecies(int n) {
    return getListOfSpecies().get(n);
  }
  
  /**
   * Gets the {@link Species} of the {@link #listOfSpecies} which has 'id' as
   * id.
   * 
   * @param id
   * @return the {@link Species} of the listOfSpecies which has 'id' as id (or
   *         name depending on the level and version). <code>null</code> if 
   *         it doesn't exist.
   */
  public Species getSpecies(String id) {
    UniqueNamedSBase found = findUniqueNamedSBase(id);
    if ((found != null) && (found instanceof Species)) {
      return (Species) found;
    }
    return null;
  }
  
  /**
   * Returns the number of {@link Species} of this {@link Model}.
   * 
   * @return the number of {@link Species} of this {@link Model}.
   */
  public int getSpeciesCount() {
    return isSetListOfSpecies() ? listOfSpecies.size() : 0;
  }
  
  /**
   * Returns the number of {@link SpeciesReferences}s of this {@link Model}.
   * 
   * @return the number of {@link SpeciesReferences}s of this {@link Model}.
   */
  public int getSpeciesReferenceCount() {
    int count = 0;
    for (Reaction r : getListOfReactions()) {
      count += r.getReactantCount() + r.getProductCount();
    }
    return count;
  }
  
  /**
   * Gets the nth {@link SpeciesType} object in this Model.
   * 
   * @param n
   *        index
   * @return the nth {@link SpeciesType} of this Model. Returns <code>null</code> if there
   *         are no speciesType defined or if the index n is too big or lower
   *         than zero.
   * @deprecated Only valid in SBML Level 2 for Versions 2 through 4.
   */
  @Deprecated
  public SpeciesType getSpeciesType(int n) {
    return getListOfSpeciesTypes().get(n);
  }
  
  /**
   * Returns the {@link SpeciesType} of the {@link #listOfSpeciesTypes} which has 'id' as
   * id.
   * 
   * @param id
   * @return the {@link SpeciesType} of the {@link #listOfSpeciesTypes} which has 'id' as
   *         id (or name depending on the level and version). <code>null</code> if it doesn't
   *         exist.
   * @deprecated Only valid in SBML Level 2 for Versions 2 through 4.
   */
  @Deprecated
  public SpeciesType getSpeciesType(String id) {
    UniqueNamedSBase found = findUniqueNamedSBase(id);
    if ((found != null) && (found instanceof SpeciesType)) {
      return (SpeciesType) found;
    }
    return null;
  }
  
  /**
   * Returns the number of {@link SpeciesType}s of this {@link Model}.
   * 
   * @return the number of {@link SpeciesType}s of this {@link Model}.
   * @deprecated the use of {@link SpeciesType} is not recommended, because this
   *             data structure is only valid in SBML Level 2 for Versions 2
   *             through 4.
   */
  @Deprecated
  public int getSpeciesTypeCount() {
    return isSetListOfSpeciesTypes() ? listOfSpeciesTypes.size() : 0;
  }

  /**
   * Returns the number of {@link Species} whose boundary condition is set to
   * {@code true}.
   * 
   * @return the number of {@link Species} whose boundary condition is set to
   *         {@code true}.
   */
  public int getSpeciesWithBoundaryConditionCount() {
    return getListOfSpecies().filterList(new BoundaryConditionFilter()).size();
  }

  /**
   * Returns the number of {@link StoichiometryMath} in the {@link Model}.
   * 
   * @return the number of {@link StoichiometryMath} in the {@link Model}.
   */
  public int getStoichiometryMathCount() {
    int count = 0;
    for (Reaction r : getListOfReactions()) {
      for (SpeciesReference sr : r.getListOfReactants()) {
        if (sr.isSetStoichiometryMath()) {
          count++;
        }
      }
      for (SpeciesReference sr : r.getListOfProducts()) {
        if (sr.isSetStoichiometryMath()) {
          count++;
        }
      }
    }
    return count;
  }
  
  /**
   * Returns the substance units ID of this model.
   * 
   * @return the substance units ID of this model. Returns the empty
   *         {@link String} if it is not set.
   */
  public String getSubstanceUnits() {
    if (isSetSubstanceUnits()) {
      return substanceUnitsID;
    } else if (getLevel() < 3) { 
      return UnitDefinition.SUBSTANCE; 
    }
    return "";
  }
  
  /**
   * Returns the {@link UnitDefinition} which has the {@link #substanceUnitsID} of this
   * {@link Model} as id.
   * 
   * @return the {@link UnitDefinition} which has the {@link #substanceUnitsID} of this
   *         {@link Model} as id. <code>null</code> if it doesn't exist.
   */
  public UnitDefinition getSubstanceUnitsInstance() {
    return getUnitDefinition(getSubstanceUnits());
  }
  
  /**
   * Returns the number of all instances of {@link Symbol} referenced within the
   * model. There is no dedicated list for {@link Symbol}s. This is a convenient
   * method to support working with the model data structure.
   * 
   * @return The number of {@link Compartment}s, {@link Species}, and
   *         {@link Parameter}s in the model.
   */
  public int getSymbolCount() {
    return getParameterCount() + getSpeciesCount() + getCompartmentCount();
  }
  
  /**
   * Returns the time units ID of this {@link Model}.
   * 
   * @return the time units ID of this {@link Model} or an empty {@link String}
   *         if it is not set.
   */
  public String getTimeUnits() {
    if (isSetTimeUnits()) {
      return timeUnitsID;
    } else if (getLevel() < 3) {
      return UnitDefinition.TIME; 
    }
    return "";
  }
  
  /**
   * Gets the {@link UnitDefinition} representing the time units of this
   * {@link Model}.
   * 
   * @return the {@link UnitDefinition} representing the time units of this
   *         {@link Model}, <code>null</code> if it is not defined in this {@link Model}
   */
  public UnitDefinition getTimeUnitsInstance() {
    return getUnitDefinition(getTimeUnits());
  }
  
  /**
   * Returns the number of {@link Trigger} of this {@link Model}.
   * 
   * @return the number of {@link Trigger} of this {@link Model}.
   */
  public int getTriggerCount() {
    int count = 0;
    for (Event e : getListOfEvents()) {
      if (e.isSetTrigger()) {
        count++;
      }
    }
    return count;
  }
  
  /**
   * Returns the number of {@link Unit}s of this {@link Model}.
   * 
   * @return the number of {@link Unit}s of this {@link Model}.
   */
  public int getUnitCount() {
    int count = 0;
    for (UnitDefinition ud : getListOfUnitDefinitions()) {
      count += ud.getUnitCount();
    }
    return count;
  }
  
  /**
   * Gets the nth {@link UnitDefinition} object in this {@link Model}.
   * 
   * @param n
   * @return the nth {@link UnitDefinition} of this {@link Model}. Returns
   *         <code>null</code> if there are no {@link UnitDefinition}s defined 
   *         or if the index n is too big or lower than zero.
   */
  public UnitDefinition getUnitDefinition(int n) {
    return getListOfUnitDefinitions().get(n);
  }
  
  /**
   * Returns the {@link UnitDefinition} of the {@link #listOfUnitDefinitions}
   * which has 'id' as id. If no {@link UnitDefinition} are found, we check in the
   * {@link #listOfPredefinedUnitDefinitions}. If we still did not find a
   * {@link UnitDefinition}, <code>null</code> is returned.
   * 
   * @param id
   * @return the {@link UnitDefinition} of the {@link #listOfUnitDefinitions}s
   *         which has 'id' as id (or name depending on the level and version). 
   *         <code>null</code> if it doesn't exist.
   */
  public UnitDefinition getUnitDefinition(String id) {
    UnitDefinition unitDefinition = mapOfUnitDefinitions != null ? mapOfUnitDefinitions.get(id) : null;
    // Checking if it is not one of the predefined default units.
    if (unitDefinition == null) {
      unitDefinition = getPredefinedUnitDefinition(id);
    }
    return unitDefinition;
  }
  
  /**
   * Returns the number of {@link UnitDefinition}s of this {@link Model}.
   * 
   * @return the number of {@link UnitDefinition}s of this {@link Model}.
   */
  public int getUnitDefinitionCount() {
    return isSetListOfUnitDefinitions() ? listOfUnitDefinitions.size() : 0;
  }

  /**
   * Returns the number of {@link Variable}s of this {@link Model}.
   * 
   * @return the number of {@link Variable}s of this {@link Model}.
   */
  public int getVariableCount() {
    return getSymbolCount() + getSpeciesReferenceCount();
  }
  
  /**
   * Returns the volume units ID of this {@link Model}.
   * 
   * @return the volume nits ID of this {@link Model}. Returns an empty String
   *         if it is not set.
   */
  public String getVolumeUnits() {
    if (isSetVolumeUnits()) {
      return volumeUnitsID;
    } else if (getLevel() < 3) {
      return UnitDefinition.VOLUME;
    }
    return "";
  }
  
  /**
   * Returns the {@link UnitDefinition} instance which has the {@link #volumeUnitsID}
   * of this {@link Model} as id.
   * 
   * @return the {@link UnitDefinition} instance which has the {@link #volumeUnitsID}
   *         of this {@link Model} as id. <code>null</code> if it doesn't exist.
   */
  public UnitDefinition getVolumeUnitsInstance() {
    return getUnitDefinition(getVolumeUnits());
  }
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 887;
    int hashCode = super.hashCode();
    if (isSetTimeUnits()) {
      hashCode += prime * getTimeUnits().hashCode();
    }
    if (isSetAreaUnits()) {
      hashCode += prime * getAreaUnits().hashCode();
    }
    if (isSetConversionFactor()) {
      hashCode += prime * getConversionFactor().hashCode();
    }
    if (isSetExtentUnits()) {
      hashCode += prime * getExtentUnits().hashCode();
    }
    if (isSetLengthUnits()) {
      hashCode += prime * getLengthUnits().hashCode();
    }
    if (isSetSubstanceUnits()) {
      hashCode += prime * getSubstanceUnits().hashCode();
    }
    if (isSetVolumeUnits()) {
      hashCode += prime * getVolumeUnits().hashCode();
    }
    return hashCode;
  }
  
  /**
   * Returns {@code true} if this model has a reference to the unit with the given
   * identifier.
   * 
   * @param id
   * @return {@code true} if this model has a reference to the unit with the given
   *         identifier, {@code false} otherwise.
   */
  public boolean hasUnit(String id) {
    return getUnitDefinition(id) != null;
  }
  
  /**
   * Initializes the default values using the current Level/Version
   * configuration.
   */
  public void initDefaults() {
    initDefaults(getLevel(), getVersion());
  }
  
  /**
   * Initializes the default values of the attributes of the {@link Model}
   */
  public void initDefaults(int level, int version) {
    listOfCompartments = null;
    listOfCompartmentTypes = null;
    listOfConstraints = null;
    listOfEvents = null;
    listOfFunctionDefinitions = null;
    listOfInitialAssignments = null;
    listOfParameters = null;
    listOfReactions = null;
    listOfRules = null;
    listOfSpecies = null;
    listOfSpeciesTypes = null;
    UnitDefinition ud;
    listOfPredefinedUnitDefinitions = new ArrayList<UnitDefinition>();
    addPredefinedUnits();
    
    switch (level) {
      case 1:
        // substance
        ud = UnitDefinition.substance(getLevel(), getVersion());
        listOfPredefinedUnitDefinitions.add(ud);
        
        // time
        ud = UnitDefinition.time(getLevel(), getVersion());
        listOfPredefinedUnitDefinitions.add(ud);
        
        // volume
        ud = UnitDefinition.volume(getLevel(), getVersion());
        listOfPredefinedUnitDefinitions.add(ud);
        
        areaUnitsID = null;
        lengthUnitsID = null;
        extentUnitsID = null;
        conversionFactorID = null;
        break;
      case 2:
        // substance
        ud = UnitDefinition.substance(getLevel(), getVersion());
        listOfPredefinedUnitDefinitions.add(ud);
        
        // volume
        ud = UnitDefinition.volume(getLevel(), getVersion());
        listOfPredefinedUnitDefinitions.add(ud);
        
        // area
        ud = UnitDefinition.area(getLevel(), getVersion());
        listOfPredefinedUnitDefinitions.add(ud);
        
        // length
        ud = UnitDefinition.length(getLevel(), getVersion());
        listOfPredefinedUnitDefinitions.add(ud);
        
        // time
        ud = UnitDefinition.time(getLevel(), getVersion());
        listOfPredefinedUnitDefinitions.add(ud);
        
        extentUnitsID = null;
        conversionFactorID = null;
        break;
      default: // undefined or level 3
        substanceUnitsID = null;
        timeUnitsID = null;
        volumeUnitsID = null;
        areaUnitsID = null;
        lengthUnitsID = null;
        extentUnitsID = null;
        conversionFactorID = null;
        break;
    }
  }
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
   */
  public boolean isIdMandatory() {
    return false;
  }
  
  /**
   * Returns {@code true} if the area units ID of this Model is not <code>null</code>.
   * 
   * @return {@code true} if the area units ID of this Model is not <code>null</code>.
   */
  public boolean isSetAreaUnits() {
    return this.areaUnitsID != null;
  }
  
  /**
   * Returns {@code true} if the UnitDefinition which has the area units ID of this
   * Model as id is not <code>null</code>.
   * 
   * @return {@code true} if the UnitDefinition which has the area units ID of this
   *         Model as id is not <code>null</code>.
   */
  public boolean isSetAreaUnitsInstance() {
    return getAreaUnitsInstance() != null;
  }
  
  /**
   * Returns {@code true} if the conversionFactorID of this Model is not <code>null</code>.
   * 
   * @return {@code true} if the conversionFactorID of this Model is not <code>null</code>.
   */
  public boolean isSetConversionFactor() {
    return this.conversionFactorID != null;
  }
  
  /**
   * Returns {@code true} if the Parameter which has the conversionFactorID of this
   * Model as id is not <code>null</code>.
   * 
   * @return {@code true} if the Parameter which has the conversionFactorID of this
   *         Model as id is not <code>null</code>.
   */
  public boolean isSetConversionFactorInstance() {
    return getParameter(this.conversionFactorID) != null;
  }
  
  /**
   * Returns {@code true} if the extentUnitsID of this Model is not <code>null</code>.
   * 
   * @return {@code true} if the extentUnitsID of this Model is not <code>null</code>.
   */
  public boolean isSetExtentUnits() {
    return this.extentUnitsID != null;
  }
  
  /**
   * Returns {@code true} if the UnitDefinition which has the extentUnitsID of this
   * Model as id is not <code>null</code>.
   * 
   * @return {@code true} if the UnitDefinition which has the extentUnitsID of this
   *         Model as id is not <code>null</code>.
   */
  public boolean isSetExtentUnitsInstance() {
    return getExtentUnitsInstance() != null;
  }
  
  /**
   * Returns {@code true} if the lengthUnitsID of this Model is not <code>null</code>.
   * 
   * @return {@code true} if the lengthUnitsID of this Model is not <code>null</code>.
   */
  public boolean isSetLengthUnits() {
    return this.lengthUnitsID != null;
  }
  
  /**
   * Returns {@code true} if the UnitDefinition which has the lengthUnitsID of this
   * Model as id is not <code>null</code>.
   * 
   * @return {@code true} if the UnitDefinition which has the lengthUnitsID of this
   *         Model as id is not <code>null</code>.
   */
  public boolean isSetLengthUnitsInstance() {
    return getLengthUnitsInstance() != null;
  }
  
  /**
   * Returns {@code true} if the listOfCompartments of this Model is not <code>null</code> and not
   * empty.
   * 
   * @return {@code true} if the listOfCompartments of this Model is not <code>null</code> and not
   *         empty.
   */
  public boolean isSetListOfCompartments() {
    return (listOfCompartments != null) && (listOfCompartments.size() > 0);
  }
  
  /**
   * Returns {@code true} if the listOfCompartmentTypes of this Model is not <code>null</code> and
   * not empty.
   * 
   * @return {@code true} if the listOfCompartmentTypes of this Model is not <code>null</code> and
   *         not empty.
   * @deprecated Only valid in SBML Level 2 for Versions 2 through 4.
   */
  @Deprecated
  public boolean isSetListOfCompartmentTypes() {
    return (listOfCompartmentTypes != null)
        && (listOfCompartmentTypes.size() > 0);
  }
  
  /**
   * Returns {@code true} if the listOfConstraints of this Model is not <code>null</code> and not
   * empty.
   * 
   * @return {@code true} if the listOfConstraints of this Model is not <code>null</code> and not
   *         empty.
   */
  public boolean isSetListOfConstraints() {
    return (listOfConstraints != null) && (listOfConstraints.size() > 0);
  }
  
  /**
   * Returns {@code true} if the listOfEvents of this Model is not <code>null</code> and not empty.
   * 
   * @return {@code true} if the listOfEvents of this Model is not <code>null</code> and not empty.
   */
  public boolean isSetListOfEvents() {
    return (listOfEvents != null) && (listOfEvents.size() > 0);
  }
  
  /**
   * Returns {@code true} if the listOfFunctionDefinitions of this Model is not <code>null</code> and
   * not empty.
   * 
   * @return {@code true} if the listOfFunctionDefinitions of this Model is not <code>null</code> and
   *         not empty.
   */
  public boolean isSetListOfFunctionDefinitions() {
    return (listOfFunctionDefinitions != null)
        && (listOfFunctionDefinitions.size() > 0);
  }
  
  /**
   * Returns {@code true} if the listOfInitialAssignments of this Model is not <code>null</code> and
   * not empty.
   * 
   * @return {@code true} if the listOfInitialAssignments of this Model is not <code>null</code> and
   *         not empty.
   */
  public boolean isSetListOfInitialAssignments() {
    return (listOfInitialAssignments != null)
        && (listOfInitialAssignments.size() > 0);
  }
  
  /**
   * Returns {@code true} if the listOfParameters of this Model is not <code>null</code> and not
   * empty.
   * 
   * @return {@code true} if the listOfParameters of this Model is not <code>null</code> and not
   *         empty.
   */
  public boolean isSetListOfParameters() {
    return (listOfParameters != null) && (listOfParameters.size() > 0);
  }
  
  /**
   * Returns {@code true} if the listOfReactions of this Model is not <code>null</code> and not
   * empty.
   * 
   * @return {@code true} if the listOfReactions of this Model is not <code>null</code> and not
   *         empty.
   */
  public boolean isSetListOfReactions() {
    return (listOfReactions != null) && (listOfReactions.size() > 0);
  }
  
  /**
   * Returns {@code true} if the listOfRules of this Model is not <code>null</code> and not empty.
   * 
   * @return {@code true} if the listOfRules of this Model is not <code>null</code> and not empty.
   */
  public boolean isSetListOfRules() {
    return (listOfRules != null) && (listOfRules.size() > 0);
  }
  
  /**
   * Returns {@code true} if the listOfSpecies of this Model is not <code>null</code> and not empty.
   * 
   * @return {@code true} if the listOfSpecies of this Model is not <code>null</code> and not empty.
   */
  public boolean isSetListOfSpecies() {
    return (listOfSpecies != null) && (listOfSpecies.size() > 0);
  }
  
  /**
   * Returns {@code true} if the listOfSpeciesTypes of this Model is not <code>null</code> and not
   * empty.
   * 
   * @return {@code true} if the listOfSpeciesTypes of this Model is not <code>null</code> and not
   *         empty.
   * @deprecated Only valid in SBML Level 2 for Versions 2 through 4.
   */
  @Deprecated
  public boolean isSetListOfSpeciesTypes() {
    return (listOfSpeciesTypes != null) && (listOfSpeciesTypes.size() > 0);
  }
  
  /**
   * Returns {@code true} if the listOfUnitDefinitions of this Model is not <code>null</code> and not
   * empty.
   * 
   * @return {@code true} if the listOfUnitDefinitions of this Model is not <code>null</code> and not
   *         empty.
   */
  public boolean isSetListOfUnitDefinitions() {
    return (listOfUnitDefinitions != null)
        && (listOfUnitDefinitions.size() > 0);
  }
  
  /**
   * Returns the {@link History} of the Model. This is equivalent to the call
   * {@link SBase#isSetHistory()}.
   * 
   * @return the {@link History} of the Model.
   * @deprecated use {@link SBase#isSetHistory()}
   */
  @Deprecated
  public boolean isSetModelHistory() {
    return isSetHistory();
  }
  
  /**
   * Returns {@code true} if the substanceUnitsID of this Model is not <code>null</code>.
   * 
   * @return {@code true} if the substanceUnitsID of this Model is not <code>null</code>.
   */
  public boolean isSetSubstanceUnits() {
    return this.substanceUnitsID != null;
  }
  
  /**
   * Returns {@code true} if the UnitDefinition which has the substanceUnitsID of this
   * Model as id is not <code>null</code>.
   * 
   * @return {@code true} if the UnitDefinition which has the substanceUnitsID of this
   *         Model as id is not <code>null</code>.
   */
  public boolean isSetSubstanceUnitsInstance() {
    return getSubstanceUnitsInstance() != null;
  }
  
  /**
   * Returns {@code true} if the timeUnitsID of this {@link Model} is not <code>null</code>.
   * 
   * @return {@code true} if the timeUnitsID of this {@link Model} is not <code>null</code>.
   */
  public boolean isSetTimeUnits() {
    return this.timeUnitsID != null;
  }
  
  /**
   * Returns {@code true} if the UnitsDefinition which has the timeUnistID of this Model
   * as id is not <code>null</code>.
   * 
   * @return {@code true} if the UnitsDefinition which has the timeUnistID of this Model
   *         as id is not <code>null</code>.
   */
  public boolean isSetTimeUnitsInstance() {
    return getTimeUnitsInstance() != null;
  }
  
  /**
   * Returns {@code true} if the volumeUnitsID of this Model is not <code>null</code>.
   * 
   * @return {@code true} if the volumeUnitsID of this Model is not <code>null</code>.
   */
  public boolean isSetVolumeUnits() {
    return this.volumeUnitsID != null;
  }
  
  /**
   * Returns {@code true} if the UnitDefinition which has the volumeUnitsID of this
   * Model as id is not <code>null</code>.
   * 
   * @return {@code true} if the UnitDefinition which has the volumeUnitsID of this
   *         Model as id is not <code>null</code>.
   */
  public boolean isSetVolumeUnitsInstance() {
    return getVolumeUnitsInstance() != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.element.SBase#readAttribute(String attributeName, String prefix, String value)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
    
    if (!isAttributeRead) {
      if (attributeName.equals("substanceUnits")) {
        setSubstanceUnits(value);
        return true;
      } else if (attributeName.equals("timeUnits")) {
        setTimeUnits(value);
        return true;
      } else if (attributeName.equals("volumeUnits")) {
        setVolumeUnits(value);
        return true;
      } else if (attributeName.equals("areaUnits")) {
        setAreaUnits(value);
        return true;
      } else if (attributeName.equals("lengthUnits")) {
        setLengthUnits(value);
        return true;
      } else if (attributeName.equals("extentUnits")) {
        setExtentUnits(value);
        return true;
      } else if (attributeName.equals("conversionFactor")) {
        setConversionFactor(value);
        return true;
      }
    }
    return isAttributeRead;
  }

  /**
   * Registration of {@link LocalParameter} instances in the {@link Model}.
   * 
   * @param kl
   *        the {@link KineticLaw} that contains the given
   *        {@link LocalParameter}
   * @param lp
   *        the {@link LocalParameter} whose identifier is to be registered.
   * @param delete
   * @return {@code true} in case of success, otherwise {@code false}.
   */
  private boolean registerId(KineticLaw kl, LocalParameter lp, boolean delete, boolean alreadyRegisteredInKL) {
    if (!alreadyRegisteredInKL) {
        // Register local parameter within its kinetic law first.
    	logger.debug("registerIds (LP) : calling kineticLaw.registerLocalParameter !");
    	// should never be called from the model in fact
    	
    	kl.registerLocalParameter(lp, delete);
    }
    
    if (logger.isDebugEnabled()) {
    	logger.debug((delete ? "un" : "") + "registerIds (LP) : id = " + lp.getId() + "");
    }

    if (lp.isSetId()) 
    {
      Reaction r = kl.getParentSBMLObject();      
      String pId = lp.getId();

      if (logger.isDebugEnabled()) {
    	  logger.debug("registerIds (LP) : reaction = " + r + " (r.isSetId = " + r.isSetId() + ")");
      }
      if ((r != null)) 
      {
        if (delete) 
        {
          if (mapOfLocalParameters != null) {
            List<Reaction> reactionList = mapOfLocalParameters.get(pId);

            if (reactionList != null) 
            {
            	
            	boolean removed = reactionList.remove(r);

            	if (!removed && logger.isDebugEnabled()) 
            	{
            		logger.debug("Reaction '" + r + "' was not removed from the mapOfLocalParameters");
            	}
            	
              if (reactionList.isEmpty()) {
                mapOfLocalParameters.remove(pId);
              }
            }
          }
          return true;
        }
        else // register 
        {
          // add new key or reaction for this local parameter.
          if (mapOfLocalParameters == null) {
            mapOfLocalParameters = new HashMap<String, List<Reaction>>();
          }
          if (!mapOfLocalParameters.containsKey(pId)) {
            mapOfLocalParameters.put(pId, new ArrayList<Reaction>());
          }
          mapOfLocalParameters.get(pId).add(r);
          
          return true;          
        }        
      }      
    }
    
    return false;
  }

  /**
   * Registers the identifier of a {@link NamedSBase} and its associated object
   * in this {@link Model}.
   * 
   * @param nsb
   *        the element, whose identifier is to be registered.
   * @param add
   *        If {@code true} the identifier of the given {@link NamedSBase}
   *        will be registered in this {@link Model} Otherwise, the given
   *        identifier will be removed from this {@link Model}'s hash.
   * @return {@code true} if this operation was successfully performed,
   *         {@code false} otherwise.
   */
  boolean registerId(NamedSBase nsb, boolean add) {
    return registerIds(nsb.getParentSBMLObject(), nsb, true, !add);
  }
  
  /**
   * 
   * @param unsb
   * @param recursively
   * @param delete
   * @return
   */
  private boolean registerId(UniqueNamedSBase unsb, boolean delete) {
    String id = unsb.getId();
    if (delete && (mapOfUniqueNamedSBases != null)) {

      mapOfUniqueNamedSBases.remove(id);
      if (logger.isDebugEnabled()) {
    	  logger.debug(MessageFormat.format("removed id={0} from model{1}",
    			  id, (isSetId() ? " " + getId() : "")));
      }
    } else if (unsb.isSetId()) {
      if (mapOfUniqueNamedSBases == null) {
        mapOfUniqueNamedSBases = new HashMap<String, UniqueNamedSBase>();
      }
      /*
       * Two reasons for non acceptance:
       * (1) another UniqueNamedSBase is already registered with the identical id.
       * 
       * (2) In Level 1 UnitDefinitions and UniqueNamedSBases use the same namespace.
       */
      if ((mapOfUniqueNamedSBases.containsKey(id) && 
          (mapOfUniqueNamedSBases.get(id) != unsb))
        || ((unsb.getLevel() == 1) && (mapOfUnitDefinitions != null) && 
            (mapOfUnitDefinitions.containsKey(id)))) 
      {
        logger.error(String.format(
          "An element with the id '%s' is already present in this model%s. The new element will not be added to the model.",
          id, (isSetId() ? " " + getId() : "")));
        return false;
      }
      mapOfUniqueNamedSBases.put(id, unsb);

      if (logger.isDebugEnabled()) {      
    	  logger.debug(String.format("registered id=%s in model%s",
    			  id, (isSetId() ? " " + getId() : "")));
      }
    }

    return true;
  }
  
  /**
   * Registers the identifier and the corresponding {@link UnitDefinition}
   * itself
   * in this {@link Model}.
   * 
   * @param ud
   *        the {@link UnitDefinition} to be registered.
   * @param add
   * @return
   */
  private boolean registerId(UnitDefinition ud, boolean add) {
    if (mapOfUnitDefinitions == null) {
      mapOfUnitDefinitions = new HashMap<String, UnitDefinition>();
    }
    if (add) {
      return mapOfUnitDefinitions.put(ud.getId(), ud) == null;
    }
    
    return mapOfUnitDefinitions.remove(ud.getId()) != null;    
  }

  /**
   * Registers the given element in this {@link Model}.
   * 
   * @param parent
   * @param newElem
   * @param recursively
   * @param delete
   * @return {@code true} if this operation was successfully performed,
   *         {@code false} otherwise.
   */
  boolean registerIds(SBase parent, SBase newElem, boolean recursively, boolean delete) 
  {
      /*
       * the default return value is true to be able to register successfully objects that are
       * not NamedSBase when the recursive boolean is set to false (happen with listOf objects for example).
       * For these, the AbstractSBase.registerChild(SBase) method was throwing an exception although there was no problem.
       */
      boolean success = true;

      logger.debug("registerIds (main) : newElem = " + newElem.getElementName() + " (recursive = " + recursively + ")");

      if (newElem instanceof NamedSBase) 
      {
    	  NamedSBase newNsb = (NamedSBase) newElem;
    	
    	  if (newNsb.isSetId()) 
    	  {
    		  if (newNsb instanceof UniqueNamedSBase) 
    		  {
    			  success &= registerId((UniqueNamedSBase) newNsb, delete);
    		  }
    		  else if ((newNsb instanceof LocalParameter) && (parent.getParent() != null)) 
    		  {
    			  success &= registerId((KineticLaw) parent.getParent(), (LocalParameter) newNsb, delete, true);
    		  }
    		  else if (newNsb instanceof UnitDefinition) 
    		  {
    			  success &= registerId((UnitDefinition) newNsb, !delete);
    		  }
    		  else 
    		  {
    			  // in L3 packages we might have different id namespaces
    			  logger.error(MessageFormat.format(
    					  "registerIds: the object {0} is neither a UniqueNamedSBase, a LocalParameter or a UnitDefinition so its id will not be registered in the Model.",
    					  newNsb.getClass().getCanonicalName()));
    		  }
    	  } else if (!newNsb.isIdMandatory()) {
    		  // do nothing
    	  }
      } 
      
      logger.debug("registerIds (main) : success = " + success);
      
      if (recursively) {
    	  for (int i = 0; (i < newElem.getChildCount()) && success; i++) {
    		  TreeNode child = newElem.getChildAt(i);
    		  if (child instanceof SBase) {
    			  if (child instanceof LocalParameter) {
    				  // The local parameter have already been registered in the KineticLaw in this case
    				  logger.debug("registerIds (main) : registering a LocalParameter.");
    				  success &= registerId((KineticLaw) parent, (LocalParameter) child, delete, true);
    				  
    				  // we still need to register recursively the children of a LocalParameter
    				  if (child.getChildCount() > 0) {
    					  for (int j = 0; (j < child.getChildCount()) && success; j++) {
    			    		  TreeNode lpChild = child.getChildAt(j);
    			    		  
    			    		  if (lpChild instanceof SBase) {
    			    			  success &= registerIds((SBase) child, (SBase) lpChild, recursively, delete);
    			    		  }
    					  }
    				  }
    			  } else {
    				  success &= registerIds(newElem, (SBase) child, recursively, delete);
    			  }
    		  }
    	  }
    	  
    	  logger.debug("registerIds (main) : success after recursion = " + success);
    	  
    	  return success;
      }

      return success;
  }

  /**
   * Removes the i-th {@link Compartment} of the {@link Model}.
   * 
   * @param i
   *        the index of the {@link Compartment} to remove
   * @return the removed {@link Compartment}.
   */
  public Compartment removeCompartment(int i) {
    return getListOfCompartments().remove(i);
  }
  
  /**
   * Removes the {@link Compartment} of the {@link Model} with 'id' as id.
   * 
   * @param id
   * @return the removed {@link Compartment}.
   */
  public Compartment removeCompartment(String id) {
    return getListOfCompartments().removeFirst(new NameFilter(id));
  }
  
  /**
   * Removes the n-th {@link CompartmentType} of the {@link Model}.
   * 
   * @param n
   *        the index of the {@link Compartment} to remove
   * @return the removed {@link CompartmentType}.
   * @deprecated Only valid in SBML Level 2 for Versions 2 through 4.
   */
  @Deprecated
  public CompartmentType removeCompartmentType(int n) {
    return getListOfCompartmentTypes().remove(n);
  }
  
  /**
   * Removes the {@link CompartmentType} of the {@link Model} with 'id' as id.
   * 
   * @param id
   * @return the removed element.
   * @deprecated Only valid in SBML Level 2 for Versions 2 through 4.
   */
  @Deprecated
  public CompartmentType removeCompartmentType(String id) {
    return getListOfCompartmentTypes().removeFirst(new NameFilter(id));
  }
  
  /**
   * Removes the n-th {@link Constraint} of the {@link Model}.
   * 
   * @param n
   * @return the removed element.
   */
  public Constraint removeConstraint(int n) {
    return getListOfConstraints().remove(n);
  }
  
  /**
   * Removes the n-th {@link Event} of the {@link Model}.
   * 
   * @param n
   * @return the removed element.
   */
  public Event removeEvent(int n) {
    return getListOfEvents().remove(n);
  }
  
  /**
   * Removes the {@link Event} of the {@link Model} with 'id' as id.
   * 
   * @param id
   * @return the removed element.
   */
  public Event removeEvent(String id) {
    return getListOfEvents().removeFirst(new NameFilter(id));
  }
  
  /**
   * Removes the n-th {@link FunctionDefinition} of the {@link Model}.
   * 
   * @param n
   * @return the removed element.
   */
  public FunctionDefinition removeFunctionDefinition(int n) {
    return getListOfFunctionDefinitions().remove(n);
  }
  
  /**
   * Removes the {@link FunctionDefinition} of the {@link Model} with 'id' as
   * id.
   * 
   * @param id
   * @return the removed element.
   */
  public FunctionDefinition removeFunctionDefinition(String id) {
    return getListOfFunctionDefinitions().removeFirst(new NameFilter(id));
  }
  
  /**
   * Removes the n-th {@link InitialAssignment} of the {@link Model}.
   * 
   * @param n
   * @return the removed element.
   */
  public InitialAssignment removeInitialAssignment(int n) {
    return getListOfInitialAssignments().remove(n);
  }
  
  /**
   * Removes the n-th {@link Parameter} of the {@link Model}.
   * 
   * @param n
   * @return the removed element.
   */
  public Parameter removeParameter(int n) {
    return getListOfParameters().remove(n);
  }
  
  /**
   * Removes the Parameter 'parameter' from this Model.
   * 
   * @param parameter
   * @return {@code true} if the {@link Parameter} was found and removed.
   */
  public boolean removeParameter(Parameter parameter) {
    return getListOfParameters().remove(parameter);
  }
  
  /**
   * Removes the {@link Parameter} of the {@link Model} with 'id' as id.
   * 
   * @param id
   * @return the removed element.
   */
  public Parameter removeParameter(String id) {
    return getListOfParameters().removeFirst(new NameFilter(id));
  }
  
  /**
   * Removes the n-th {@link Reaction} of the {@link Model}.
   * 
   * @param n
   * @return the removed element.
   */
  public Reaction removeReaction(int n) {
    return getListOfReactions().remove(n);
  }
  
  /**
   * Removes a reaction from the model.
   * 
   * @param reac
   * @return {@code true} if the {@link Reaction} was found and removed.
   */
  public boolean removeReaction(Reaction reac) {
    return getListOfReactions().remove(reac);
  }
  
  /**
   * Removes the {@link Reaction} of the {@link Model} with 'id' as id.
   * 
   * @param id
   * @return the removed element.
   */
  public Reaction removeReaction(String id) {
    return getListOfReactions().removeFirst(new NameFilter(id));
  }
  
  /**
   * Removes the i-th {@link Rule} of the {@link Model}.
   * 
   * @param i
   * @return the removed element.
   */
  public Rule removeRule(int i) {
    return getListOfRules().remove(i);
  }
  
  /**
   * Removes the {@link Rule} of the {@link Model} with 'variableId' as
   * variable.
   * 
   * @param variableId
   * @return the removed element.
   */
  public Rule removeRule(String variableId) {
    return getListOfRules().removeFirst(
      new AssignmentVariableFilter(variableId));
  }
  
  /**
   * Removes the i-th {@link Species} of the {@link Model}.
   * 
   * @param i
   * @return the removed element.
   */
  public Species removeSpecies(int i) {
    return getListOfSpecies().remove(i);
  }
  
  /**
   * Removes a species from the model.
   * 
   * @param spec
   * @return {@code true} if the {@link Species} was found and removed.
   */
  public boolean removeSpecies(Species spec) {
    return getListOfSpecies().remove(spec);
  }
  
  /**
   * Removes the {@link Species} of the {@link Model} with 'id' as id.
   * 
   * @param id
   * @return the removed element.
   */
  public Species removeSpecies(String id) {
    return getListOfSpecies().removeFirst(new NameFilter(id));
  }
  
  /**
   * Removes the n-th {@link SpeciesType} of the {@link Model}.
   * 
   * @param n
   * @return the removed element.
   * @deprecated Only valid in SBML Level 2 for Versions 2 through 4.
   */
  @Deprecated
  public SpeciesType removeSpeciesType(int n) {
    return getListOfSpeciesTypes().remove(n);
  }
  
  /**
   * Removes the {@link SpeciesType} of the {@link Model} with 'id' as id.
   * 
   * @param id
   * @return the removed element.
   * @deprecated Only valid in SBML Level 2 for Versions 2 through 4.
   */
  @Deprecated
  public SpeciesType removeSpeciesType(String id) {
    return getListOfSpeciesTypes().removeFirst(new NameFilter(id));
  }
  
  /**
   * Removes the n-th {@link UnitDefinition} of the {@link Model}.
   * 
   * @param n
   * @return the removed element.
   */
  public UnitDefinition removeUnitDefinition(int n) {
    return getListOfUnitDefinitions().remove(n);
  }
  
	/**
   * Removes the {@link UnitDefinition} of the {@link Model} with 'id' as id.
   * 
   * @param id
   * @return the removed element.
   */
  public UnitDefinition removeUnitDefinition(String id) {
    return getListOfUnitDefinitions().removeFirst(new NameFilter(id));
  }
  
  /**
   * Removes a {@link UnitDefinition} of the {@link Model}.
   * 
   * @param unitDefininition
   * @return {@code true} if the UnitDefinition 'unitDefinition' has been removed from
   *         the Model.
   */
  public boolean removeUnitDefinition(UnitDefinition unitDefininition) {
    return getListOfUnitDefinitions().remove(unitDefininition);
  }

  /**
   * Sets the areaUnitsID of this {@link Model} to 'areaUnitsID'
   * 
   * @param areaUnitsID
   * @throws PropertyNotAvailableException
   *             if Level < 3.
   */
  public void setAreaUnits(String areaUnitsID) {
	  if (getLevel() < 3) {
		  throw new PropertyNotAvailableException(TreeNodeChangeEvent.areaUnits,
				  this);
	  }
	  String oldAreaUnitsID = this.areaUnitsID;
	  this.areaUnitsID = areaUnitsID;
	  firePropertyChange(TreeNodeChangeEvent.areaUnits, oldAreaUnitsID,
			  areaUnitsID);
  }

  /**
   * Sets the areaUnitsID of this {@link Model} to the id of the
   * {@link UnitDefinition} 'areaUnits'.
   * 
   * @param areaUnits
   */
  public void setAreaUnits(UnitDefinition areaUnits) {
	  if (!getListOfUnitDefinitions().contains(areaUnits)) {
		  addUnitDefinition(areaUnits);
	  }
	  setAreaUnits(areaUnits != null ? areaUnits.getId() : null);
  }

  /**
   * Sets the conversionFactorID of this {@link Model} to the id of the
   * {@link Parameter} 'conversionFactor'.
   * 
   * @param conversionFactor
   */
  public void setConversionFactor(Parameter conversionFactor) {
	  setConversionFactor(conversionFactor != null ? conversionFactor.getId()
			  : null);
  }
  
  /**
   * Sets the conversionFactorID of this {@link Model} to
   * 'conversionFactorID'.
   * 
   * @param conversionFactorID
   * @throws PropertyNotAvailableException
   *             if Level < 3.
   */
  public void setConversionFactor(String conversionFactorID) {
	  if (getLevel() < 3) { throw new PropertyNotAvailableException(
			  TreeNodeChangeEvent.conversionFactor, this); }
	  String oldConversionFactorID = this.conversionFactorID;
	  this.conversionFactorID = conversionFactorID;
	  firePropertyChange(TreeNodeChangeEvent.conversionFactor,
			  oldConversionFactorID, conversionFactorID);
  }
  
  /**
   * Sets the extendUnitsID of this {@link Model} to 'extentUnitsID'.
   * 
   * @param extentUnitsID
   * @throws PropertyNotAvailableException
   *             if Level < 3.
   */
  public void setExtentUnits(String extentUnitsID) {
	  if (getLevel() < 3) { 
		  throw new PropertyNotAvailableException(
			  TreeNodeChangeEvent.extentUnits, this);
	  }
	  String oldExtentUnits = this.extentUnitsID;
	  this.extentUnitsID = extentUnitsID;
	  firePropertyChange(TreeNodeChangeEvent.extentUnits, oldExtentUnits,
			  extentUnitsID);
  }
  
  /**
   * Sets the extentUnitsID of this {@link Model} to the id of the
   * {@link UnitDefinition} 'extentUnits'.
   * 
   * @param extentUnits
   */
  public void setExtentUnits(UnitDefinition extentUnits) {
    if (!getListOfUnitDefinitions().contains(extentUnits)) {
      addUnitDefinition(extentUnits);
    }
    setExtentUnits(extentUnits != null ? extentUnits.getId() : null);
  }
  
  /**
   * Sets the lengthUnitsID of this {@link Model} to 'lengthUnitsID'.
   * 
   * @param lengthUnitsID
   * @throws PropertyNotAvailableException
   *             if Level < 3.
   */
  public void setLengthUnits(String lengthUnitsID) {
    if (getLevel() < 3) { throw new PropertyNotAvailableException(
      TreeNodeChangeEvent.lengthUnits, this); }
    String oldLengthUnits = this.lengthUnitsID;
    this.lengthUnitsID = lengthUnitsID;
    firePropertyChange(TreeNodeChangeEvent.lengthUnits, oldLengthUnits,
      lengthUnitsID);
  }
  
  /**
   * Sets the lengthUnitsID of this {@link Model} to the id of the
   * UnitDefinition 'lengthUnits'.
   * 
   * @param lengthUnits
   */
  public void setLengthUnits(UnitDefinition lengthUnits) {
    if (!getListOfUnitDefinitions().contains(lengthUnits)) {
      addUnitDefinition(lengthUnits);
    }
    setLengthUnits(lengthUnits != null ? lengthUnits.getId() : null);
  }
  
  /**
   * Sets the listOfCompartments of this {@link Model} to 'listOfCompartments'.
   * Automatically sets the parentSBML objects of 'listOfCompartments' to this
   * Model.
   * 
   * @param listOfCompartments
   */
  public void setListOfCompartments(ListOf<Compartment> listOfCompartments) {
    unsetListOfCompartments();
    this.listOfCompartments = listOfCompartments;
    if ((this.listOfCompartments != null)
        && (this.listOfCompartments.getSBaseListType() != ListOf.Type.listOfCompartments)) {
      this.listOfCompartments.setSBaseListType(ListOf.Type.listOfCompartments);    
    }
    registerChild(this.listOfCompartments);    
  }
  
  /**
   * Sets the listOfCompartmentTypes of this Model to 'listOfCompartmentTypes'.
   * Automatically sets the parentSBML objects of 'listOfCompartmentTypes' to
   * this {@link Model}.
   * 
   * @param listOfCompartmentTypes
   *        the listOfCompartmentTypes to set
   * @deprecated Only valid in SBML Level 2 for Versions 2 through 4.
   */
  @Deprecated
  public void setListOfCompartmentTypes(
    ListOf<CompartmentType> listOfCompartmentTypes) {
    unsetListOfCompartmentTypes();
    this.listOfCompartmentTypes = listOfCompartmentTypes;
    if ((this.listOfCompartmentTypes != null)
        && (this.listOfCompartmentTypes.getSBaseListType() != ListOf.Type.listOfCompartmentTypes)) {
      this.listOfCompartmentTypes
          .setSBaseListType(ListOf.Type.listOfCompartmentTypes);
    }
    registerChild(this.listOfCompartmentTypes);
  }
  
  /**
   * Sets the listOfConstraints of this {@link Model} to 'listOfConstraints'.
   * Automatically sets the parentSBML objects of 'listOfCanstraints' to this
   * Model.
   * 
   * @param listOfConstraints
   *        the listOfConstraints to set
   */
  public void setListOfConstraints(ListOf<Constraint> listOfConstraints) {
    unsetListOfConstraints();
    this.listOfConstraints = listOfConstraints;
    if ((this.listOfConstraints != null)
        && (this.listOfConstraints.getSBaseListType() != ListOf.Type.listOfConstraints)) {
      this.listOfConstraints.setSBaseListType(ListOf.Type.listOfConstraints);
    }
    registerChild(this.listOfConstraints);
  }
  
  /**
   * Sets the listOfEvents of this {@link Model} to 'listOfEvents'.
   * Automatically sets the parentSBML objects of 'listOfEvents' to this
   * {@link Model}.
   * 
   * @param listOfEvents
   */
  public void setListOfEvents(ListOf<Event> listOfEvents) {
    unsetListOfEvents();
    this.listOfEvents = listOfEvents;
    if ((this.listOfEvents != null)
        && (this.listOfEvents.getSBaseListType() != ListOf.Type.listOfEvents)) {
      this.listOfEvents.setSBaseListType(ListOf.Type.listOfEvents);
    }
    registerChild(this.listOfEvents);
  }
  
  /**
   * Sets the listOfFunctionDefinitions of this {@link Model} to
   * 'listOfFunctionDefinitions'. Automatically sets the parentSBML objects of
   * 'listOfFunctionDefinitions' to this Model.
   * 
   * @param listOfFunctionDefinitions
   *        the listOfFunctionDefinitions to set
   */
  public void setListOfFunctionDefinitions(
    ListOf<FunctionDefinition> listOfFunctionDefinitions) {
    unsetListOfFunctionDefinitions();
    this.listOfFunctionDefinitions = listOfFunctionDefinitions;
    if ((this.listOfFunctionDefinitions != null)
        && (this.listOfFunctionDefinitions.getSBaseListType() != ListOf.Type.listOfFunctionDefinitions)) {
      this.listOfFunctionDefinitions
          .setSBaseListType(ListOf.Type.listOfFunctionDefinitions);
    }
    registerChild(this.listOfFunctionDefinitions);
  }
  
  /**
   * Sets the {@link #listOfInitialAssignments} of this {@link Model} to
   * 'listOfInitialAssignments'. Automatically sets the parentSBML objects of
   * 'listOfInitialAssignments' to this Model.
   * 
   * @param listOfInitialAssignments
   *        the listOfInitialAssignments to set
   */
  public void setListOfInitialAssignments(
    ListOf<InitialAssignment> listOfInitialAssignments) {
    unsetListOfInitialAssignments();
    this.listOfInitialAssignments = listOfInitialAssignments;
    if ((this.listOfInitialAssignments != null)
        && (this.listOfInitialAssignments.getSBaseListType() != ListOf.Type.listOfInitialAssignments)) {
      this.listOfInitialAssignments
          .setSBaseListType(ListOf.Type.listOfInitialAssignments);
    }
    registerChild(this.listOfInitialAssignments);
  }
  
  /**
   * Sets the {@link #listOfParameters} of this {@link Model} to
   * 'listOfParameters'. Automatically sets the parentSBML objects of
   * 'listOfParameters' to this Model.
   * 
   * @param listOfParameters
   */
  public void setListOfParameters(ListOf<Parameter> listOfParameters) {
    unsetListOfParameters();
    this.listOfParameters = listOfParameters;
    if ((this.listOfParameters != null)
        && (this.listOfParameters.getSBaseListType() != ListOf.Type.listOfParameters)) {
      this.listOfParameters.setSBaseListType(ListOf.Type.listOfParameters);
    }
    registerChild(listOfParameters);
  }
  
  /**
   * Sets the {@link #listOfReactions} of this {@link Model} to
   * 'listOfReactions'. Automatically sets the parentSBML objects of
   * 'listOfReactions' to this Model.
   * 
   * @param listOfReactions
   */
  public void setListOfReactions(ListOf<Reaction> listOfReactions) {
    unsetListOfReactions();
    this.listOfReactions = listOfReactions;
    if ((this.listOfReactions != null)
        && (this.listOfReactions.getSBaseListType() != ListOf.Type.listOfReactions)) {
      this.listOfReactions.setSBaseListType(ListOf.Type.listOfReactions);
    }
    registerChild(this.listOfReactions); 
  }
  
  /**
   * Sets the {@link #listOfRules} of this {@link Model} to 'listOfRules'.
   * Automatically sets the parentSBML objects of 'listOfRules' to this Model.
   * 
   * @param listOfRules
   */
  public void setListOfRules(ListOf<Rule> listOfRules) {
    unsetListOfRules();
    this.listOfRules = listOfRules;
    if ((this.listOfRules != null)
        && (this.listOfRules.getSBaseListType() != ListOf.Type.listOfRules)) {
      this.listOfRules.setSBaseListType(ListOf.Type.listOfRules);
    }
    registerChild(this.listOfRules);
  }
  
  /**
   * Sets the listOfSpecies of this {@link Model} to 'listOfSpecies'.
   * Automatically sets the parentSBML objects of 'listOfSpecies' to this Model.
   * 
   * @param listOfSpecies
   */
  public void setListOfSpecies(ListOf<Species> listOfSpecies) {
    unsetListOfSpecies();
    this.listOfSpecies = listOfSpecies;
    if ((this.listOfSpecies != null)
        && (this.listOfSpecies.getSBaseListType() != ListOf.Type.listOfSpecies)) {
      this.listOfSpecies.setSBaseListType(ListOf.Type.listOfSpecies);
    }
    registerChild(this.listOfSpecies);
  }
  
  /**
   * Sets the listOfSpeciesTypes of this Model to 'listOfSpeciesTypes'.
   * Automatically sets the parentSBML objects of 'listOfSpeciesTypes' to this
   * Model.
   * 
   * @param listOfSpeciesTypes
   *        the listOfSpeciesTypes to set
   * @deprecated Only valid in SBML Level 2 for Versions 2 through 4.
   */
  @Deprecated
  public void setListOfSpeciesTypes(ListOf<SpeciesType> listOfSpeciesTypes) {
    unsetListOfSpeciesTypes();
    this.listOfSpeciesTypes = listOfSpeciesTypes;
    if ((this.listOfSpeciesTypes != null)
        && (this.listOfSpeciesTypes.getSBaseListType() != ListOf.Type.listOfSpeciesTypes)) {
      this.listOfSpeciesTypes.setSBaseListType(ListOf.Type.listOfSpeciesTypes);
    }
    registerChild(this.listOfSpeciesTypes);
  }
  
  /**
   * Sets the listOfUnitDefinitions of this {@link Model} to
   * 'listOfUnitDefinitions'. Automatically sets the parentSBML objects of
   * 'listOfUnitDefinitions' to this Model.
   * 
   * @param listOfUnitDefinitions
   */
  public void setListOfUnitDefinitions(
		  ListOf<UnitDefinition> listOfUnitDefinitions) {
	  unsetListOfUnitDefinitions();
	  this.listOfUnitDefinitions = listOfUnitDefinitions;
	  if ((this.listOfUnitDefinitions != null)
			  && (this.listOfUnitDefinitions.getSBaseListType() != ListOf.Type.listOfUnitDefinitions)) {
		  this.listOfUnitDefinitions
		  .setSBaseListType(ListOf.Type.listOfUnitDefinitions);
	  }
	  registerChild(this.listOfUnitDefinitions);
  }
  
  /**
   * @see #setHistory(History history)
   * @param history
   * @deprecated use {@link #setHistory(History)}
   */
  @Deprecated
  public void setModelHistory(History history) {
    setHistory(history);
  }

  /**
   * Sets the {@link #substanceUnitsID} of this {@link Model} to 'substanceUnitsID'
   * 
   * @param substanceUnitsID
   * @throws PropertyNotAvailableException
   *             if Level < 3.
   */
  public void setSubstanceUnits(String substanceUnitsID) {
	  if (getLevel() < 3) { 
		  throw new PropertyNotAvailableException(
			  TreeNodeChangeEvent.substanceUnits, this);
	  }
	  String oldSubstanceUnitsID = this.substanceUnitsID;
	  this.substanceUnitsID = substanceUnitsID;
	  firePropertyChange(TreeNodeChangeEvent.substanceUnits, oldSubstanceUnitsID,
			  substanceUnitsID);
  }

  /**
   * Sets the substanceUnitsID of this {@link Model} to the id of
   * 'substanceUnits'.
   * 
   * @param substanceUnits
   */
  public void setSubstanceUnits(UnitDefinition substanceUnits) {
    if (!getListOfUnitDefinitions().contains(substanceUnits)) {
      addUnitDefinition(substanceUnits);
    }
    setSubstanceUnits(substanceUnits != null ? substanceUnits.getId() : null);
  }
  
  /**
   * Sets the timeUnits of this {@link Model} to 'timeUnistID'
   * 
   * @param timeUnitsID
   * @throws PropertyNotAvailableException
   *             if Level < 3.
   */
  public void setTimeUnits(String timeUnitsID) {
	  if (getLevel() < 3) { 
	    throw new PropertyNotAvailableException(
			  TreeNodeChangeEvent.timeUnits, this);
	  }
	  String oldTimeUnitsID = this.timeUnitsID;
	  this.timeUnitsID = timeUnitsID;
	  firePropertyChange(TreeNodeChangeEvent.timeUnits, oldTimeUnitsID,
			  timeUnitsID);
  }
  
  /**
   * Sets the timeUnitsID of this {@link Model} to the id of the
   * {@link UnitDefinition} 'timeUnits'.
   * 
   * @param timeUnits
   */
  public void setTimeUnits(UnitDefinition timeUnits) {
    if (!getListOfUnitDefinitions().contains(timeUnits)) {
      addUnitDefinition(timeUnits);
    }
    setTimeUnits(timeUnits != null ? timeUnits.getId() : null);
  }
  
  /**
   * Sets the volumeUnitsID of this {@link Model} to 'volumeUnitsID'
   * 
   * @param volumeUnitsID
   * @throws PropertyNotAvailableException
   *             if Level < 3.
   */
  public void setVolumeUnits(String volumeUnitsID) {
	  if (getLevel() < 3) { 
		  throw new PropertyNotAvailableException(TreeNodeChangeEvent.volumeUnits,
				  this);
	  }
	  String oldVolumeUnitsID = this.volumeUnitsID;
	  this.volumeUnitsID = volumeUnitsID;
	  firePropertyChange(TreeNodeChangeEvent.volumeUnits, oldVolumeUnitsID,
			  this.volumeUnitsID);
  }
  
  /**
   * Sets the volumeUnitsID of this {@link Model} to the id of the
   * {@link UnitDefinition} 'volumeUnits'.
   * 
   * @param volumeUnits
   */
  public void setVolumeUnits(UnitDefinition volumeUnits) {
    if (!getListOfUnitDefinitions().contains(volumeUnits)) {
      addUnitDefinition(volumeUnits);
    }
    setVolumeUnits(volumeUnits != null ? volumeUnits.getId() : null);
  }
  
  /**
   * Sets the {@link #areaUnitsID} of this {@link Model} to <code>null</code>.
   */
  public void unsetAreaUnits() {
    setAreaUnits((String) null);
  }
  
  /**
   * Sets the {@link #conversionFactorID} of this {@link Model} to <code>null</code>.
   */
  public void unsetConversionFactor() {
    setConversionFactor((String) null);
  }
  
  /**
   * Sets the {@link #extentUnitsID} of this {@link Model} to <code>null</code>.
   */
  public void unsetExtentUnits() {
    setExtentUnits((String) null);
  }
  
  /**
   * Sets the {@link #lengthUnitsID} of this {@link Model} to <code>null</code>.
   */
  public void unsetLengthUnits() {
    setLengthUnits((String) null);
  }
  
  /**
   * Removes the {@link #listOfCompartments} from this {@link Model} and
   * notifies all registered instances of {@link TreeNodeChangeListener}.
   * 
   * @return {@code true} if calling this method lead to a change in this
   *         data structure.
   */
  public boolean unsetListOfCompartments() {
    if (this.listOfCompartments != null) {
      ListOf<Compartment> oldListOfCompartments = this.listOfCompartments;
      this.listOfCompartments = null;
      oldListOfCompartments.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }
  
  /**
   * Removes the {@link #listOfCompartmentTypes} from this {@link Model} and
   * notifies all registered instances of {@link TreeNodeChangeListener}.
   * 
   * @return {@code true} if calling this method lead to a change in this
   *         data structure.
   * @deprecated Only valid in SBML Level 2 for Versions 2 through 4.
   */
  @Deprecated
  public boolean unsetListOfCompartmentTypes() {
    if (this.listOfCompartmentTypes != null) {
      ListOf<CompartmentType> oldListOfCompartmentTypes = this.listOfCompartmentTypes;
      this.listOfCompartmentTypes = null;
      oldListOfCompartmentTypes.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }
  
  /**
   * Removes the {@link #listOfConstraints} from this {@link Model} and notifies
   * all registered instances of {@link TreeNodeChangeListener}.
   * 
   * @return {@code true} if calling this method lead to a change in this
   *         data structure.
   */
  public boolean unsetListOfConstraints() {
    if (this.listOfConstraints != null) {
      ListOf<Constraint> oldListOfConstraints = this.listOfConstraints;
      this.listOfConstraints = null;
      oldListOfConstraints.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }
  
  /**
   * Removes the {@link #listOfEvents} from this {@link Model} and notifies all
   * registered instances of {@link TreeNodeChangeListener}.
   * 
   * @return {@code true} if calling this method lead to a change in this
   *         data structure.
   */
  public boolean unsetListOfEvents() {
    if (this.listOfEvents != null) {
      ListOf<Event> oldListOfEvents = this.listOfEvents;
      this.listOfEvents = null;
      oldListOfEvents.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }
  
  /**
   * Removes the {@link #listOfFunctionDefinitions} from this {@link Model} and
   * notifies all registered instances of {@link TreeNodeChangeListener}.
   * 
   * @return {@code true} if calling this method lead to a change in this
   *         data structure.
   */
  public boolean unsetListOfFunctionDefinitions() {
    if (this.listOfFunctionDefinitions != null) {
      ListOf<FunctionDefinition> oldListOfFunctionDefinitions = this.listOfFunctionDefinitions;
      this.listOfFunctionDefinitions = null;
      oldListOfFunctionDefinitions.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }
  
  /**
   * Removes the {@link #listOfInitialAssignments} from this {@link Model} and
   * notifies all registered instances of {@link TreeNodeChangeListener}.
   * 
   * @return {@code true} if calling this method lead to a change in this
   *         data structure.
   */
  public boolean unsetListOfInitialAssignments() {
    if (this.listOfInitialAssignments != null) {
      ListOf<InitialAssignment> oldListOfInitialAssignments = this.listOfInitialAssignments;
      this.listOfInitialAssignments = null;
      oldListOfInitialAssignments.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }
  
  /**
   * Removes the {@link #listOfParameters} from this {@link Model} and notifies
   * all registered instances of {@link TreeNodeChangeListener}.
   * 
   * @return {@code true} if calling this method lead to a change in this
   *         data structure.
   */
  public boolean unsetListOfParameters() {
    if (this.listOfParameters != null) {
      ListOf<Parameter> oldListOfParameters = this.listOfParameters;
      this.listOfParameters = null;
      oldListOfParameters.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }
  
  /**
   * Removes the {@link #listOfReactions} from this {@link Model} and notifies
   * all registered instances of {@link TreeNodeChangeListener}.
   * 
   * @return {@code true} if calling this method lead to a change in this
   *         data structure.
   */
  public boolean unsetListOfReactions() {
    if (this.listOfReactions != null) {
      ListOf<Reaction> oldListOfReactions = this.listOfReactions;
      this.listOfReactions = null;
      oldListOfReactions.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }
  
  /**
   * Removes the {@link #listOfRules} from this {@link Model} and notifies all
   * registered instances of {@link TreeNodeChangeListener}.
   * 
   * @return {@code true} if calling this method lead to a change in this
   *         data structure.
   */
  public boolean unsetListOfRules() {
    if (this.listOfRules != null) {
      ListOf<Rule> oldListOfRules = this.listOfRules;
      this.listOfRules = null;
      oldListOfRules.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }
  
  /**
   * Removes the {@link #listOfSpecies} from this {@link Model} and notifies all
   * registered instances of {@link TreeNodeChangeListener}.
   * 
   * @return {@code true} if calling this method lead to a change in this
   *         data structure.
   */
  public boolean unsetListOfSpecies() {
    if (this.listOfSpecies != null) {
      ListOf<Species> oldListOfSpecies = this.listOfSpecies;
      this.listOfSpecies = null;
      oldListOfSpecies.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }
  
  /**
   * Removes the {@link #listOfSpeciesTypes} from this {@link Model} and
   * notifies all registered instances of {@link TreeNodeChangeListener}.
   * 
   * @return {@code true} if calling this method lead to a change in this
   *         data structure.
   * @deprecated Only valid in SBML Level 2 for Versions 2 through 4.
   */
  @Deprecated
  public boolean unsetListOfSpeciesTypes() {
    if (this.listOfSpeciesTypes != null) {
      ListOf<SpeciesType> oldListOfSpeciesTypes = this.listOfSpeciesTypes;
      this.listOfSpeciesTypes = null;
      oldListOfSpeciesTypes.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }
  
  /**
   * Removes the {@link #listOfUnitDefinitions} from this {@link Model} and
   * notifies all registered instances of {@link TreeNodeChangeListener}.
   * 
   * @return {@code true} if calling this method lead to a change in this
   *         data structure.
   */
  public boolean unsetListOfUnitDefinitions() {
    if (this.listOfUnitDefinitions != null) {
      ListOf<UnitDefinition> oldListOfUnitDefinitions = this.listOfUnitDefinitions;
      this.listOfUnitDefinitions = null;
      oldListOfUnitDefinitions.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }
  
  /**
   * @see #unsetHistory()
   * @deprecated use {@link #unsetHistory()}.
   */
  @Deprecated
  public void unsetModelHistory() {
    unsetHistory();
  }
  
  /**
   * Sets the {@link #substanceUnitsID} of this {@link Model} to <code>null</code>.
   */
  public void unsetSubstanceUnits() {
    setSubstanceUnits((String) null);
  }

  /**
   * Sets the timeUnitsID of this {@link Model} to <code>null</code>.
   */
  public void unsetTimeUnits() {
    setTimeUnits((String) null);
  }
  
  /**
   * Sets the {@link #volumeUnitsID} of this {@link Model} to <code>null</code>.
   */
  public void unsetVolumeUnits() {
    setVolumeUnits((String) null);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.element.SBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (getLevel() > 2) {
      if (isSetSubstanceUnits()) {
        attributes.put("substanceUnits", getSubstanceUnits());
      }
      if (isSetTimeUnits()) {
        attributes.put("timeUnits", getTimeUnits());
      }
      if (isSetVolumeUnits()) {
        attributes.put("volumeUnits", getVolumeUnits());
      }
      if (isSetAreaUnits()) {
        attributes.put("areaUnits", getAreaUnits());
      }
      if (isSetLengthUnits()) {
        attributes.put("lengthUnits", getLengthUnits());
      }
      if (isSetExtentUnits()) {
        attributes.put("extentUnits", getExtentUnits());
      }
      if (isSetConversionFactor()) {
        attributes.put("conversionFactor", getConversionFactor());
      }
    }
    return attributes;
  }

}
