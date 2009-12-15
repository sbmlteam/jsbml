/*
 * $Id$
 * $URL$
 *
 *
 *==================================================================================
 * Copyright (c) 2009 the copyright is held jointly by the individual
 * authors. See the file AUTHORS for the list of authors.
 *
 * This file is part of jsbml, the pure java SBML library. Please visit
 * http://sbml.org for more information about SBML, and http://jsbml.sourceforge.net/
 * to get the latest version of jsbml.
 *
 * jsbml is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * jsbml is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with jsbml.  If not, see <http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html>.
 *
 *===================================================================================
 *
 */

package org.sbml.jsbml.element;


import java.util.HashMap;

import org.sbml.jsbml.xml.SBaseListType;

/**
 * <p>
 * JSBML implementation of SBML's Model construct.
 * </p>
 * <p>
 * In an SBML model definition, a single object of class Model serves as the
 * overall container for the lists of the various model components. All of the
 * lists are optional, but if a given list container is present within the
 * model, the list must not be empty; that is, it must have length one or more.
 * </p>
 * 
 * @author Andreas Dr&auml;ger 
 * @author Marine
 * 
 */
public class Model extends AbstractNamedSBase {
	
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
	 * Represents the 'areaUnits' XML attribute of a model element.
	 */
	private String areaUnitsID;
	/**
	 * Represents the 'lengthUnits' XML attribute of a model element.
	 */
	private String lengthUnitsID;
	/**
	 * Represents the 'extentUnits' XML attribute of a model element.
	 */
	private String extentUnitsID;
	/**
	 * Represents the 'conversionFactor' XML attribute of a model element.
	 */
	private String conversionFactorID;

	/**
	 * Represents the listOfCompartments subnode of a model element.
	 */
	private ListOf<Compartment> listOfCompartments;

	/**
	 * Represents the listOfCompartmentTypes subnode of a model element.
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
	 */
	@Deprecated
	private ListOf<SpeciesType> listOfSpeciesTypes;

	/**
	 * Represents the listOfUnitDefinitions subnode of a model element.
	 */
	private ListOf<UnitDefinition> listOfUnitDefinitions;

	/**
	 * Creates a Model instance. By default, all the listxxx and xxxUnitsID are null.
	 */
	public Model() {
		super();
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
		listOfUnitDefinitions = null;
		substanceUnitsID = null;
		timeUnitsID = null;
		volumeUnitsID = null;
		areaUnitsID = null;
		lengthUnitsID = null;
		extentUnitsID = null;
		conversionFactorID = null;
	}
	

	/**
	 * Creates a Model instance from a Model.
	 * @param model
	 */
	@SuppressWarnings("unchecked")
	public Model(Model model) {
		super(model);
		if (model.isSetListOfFunctionDefinitions()){
			setListOfFunctionDefinitions((ListOf<FunctionDefinition>) model.getListOfFunctionDefinitions().clone());
		}
		else {
			this.listOfFunctionDefinitions = null;
		}
		if (model.isSetListOfUnitDefinitions()){
			setListOfUnitDefinitions((ListOf<UnitDefinition>) model.getListOfUnitDefinitions().clone());
		}
		else {
			this.listOfUnitDefinitions = null;
		}
		if (model.isSetListOfCompartmentTypes()){
			setListOfCompartmentTypes((ListOf<CompartmentType>) model.getListOfCompartmentTypes().clone());
		}
		else {
			this.listOfCompartmentTypes = null;
		}
		if (model.isSetListOfSpeciesTypes()){
			setListOfSpeciesTypes((ListOf<SpeciesType>) model.getListOfSpeciesTypes().clone());
		}
		else {
			this.listOfSpeciesTypes = null;
		}
		if (model.isSetListOfCompartments()){
			setListOfCompartments((ListOf<Compartment>) model.getListOfCompartments().clone());
		}
		else {
			this.listOfCompartments = null;
		}
		if (model.isSetListOfSpecies()){
			setListOfSpecies((ListOf<Species>) model.getListOfSpecies().clone());
		}
		else {
			this.listOfSpecies = null;
		}
		if (model.isSetListOfParameters()){
			setListOfParameters((ListOf<Parameter>) model.getListOfParameters().clone());
		}
		else {
			this.listOfParameters = null;
		}
		if (model.isSetListOfInitialAssignments()){
			setListOfInitialAssignments((ListOf<InitialAssignment>) model.getListOfInitialAssignments().clone());
		}
		else {
			this.listOfInitialAssignments = null;
		}
		if (model.isSetListOfRules()){
			setListOfRules((ListOf<Rule>) model.getListOfRules().clone());
		}
		else {
			this.listOfRules = null;
		}
		if (model.isSetListOfConstraints()){
			setListOfConstraints((ListOf<Constraint>) model.getListOfConstraints().clone());
		}
		else {
			this.listOfConstraints = null;
		}
		if (model.isSetListOfReactions()){
			setListOfReactions((ListOf<Reaction>) model.getListOfReactions().clone());
		}
		else {
			this.listOfReactions = null;
		}
		if (model.isSetListOfEvents()){
			setListOfEvents((ListOf<Event>) model.getListOfEvents().clone());
		}
		else {
			this.listOfEvents = null;
		}
	}

	/**
	 * Creates a Model instance from an id, level and version. By default, all the listxxx and xxxUnitsID are null.
	 * @param id
	 * @param level
	 * @param version
	 */
	public Model(String id, int level, int version) {
		super(id, level, version);
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
		listOfUnitDefinitions = null;
		substanceUnitsID = null;
		timeUnitsID = null;
		volumeUnitsID = null;
		areaUnitsID = null;
		lengthUnitsID = null;
		extentUnitsID = null;
		conversionFactorID = null;
	}
	/**
	 * 
	 * @return the UnitDefinition instance which has the timeUnitsID of this Model as id. Null if it doesn't exist
	 */
	/**
	 * 
	 * @param id
	 */
	public Model(int level, int version) {
		super(null, level, version);
	}
	
	
	public UnitDefinition getTimeUnitsInstance() {
		if (getModel() != null){
			return getModel().getUnitDefinition(this.timeUnitsID);
		}
		return null;
	}
	/**
	 * Sets the timeUnitsID of this Model to the id of the UnitDefinition 'timeUnits'.
	 * @param timeUnits
	 */
	public void setTimeUnits(UnitDefinition timeUnits) {
		this.timeUnitsID = timeUnits != null ? timeUnits.getId() : null;
		stateChanged();
	}
	/**
	 * 
	 * @return the UnitDefinition instance which has the volumeUnitsID of this Model as id. Null if it doesn't exist
	 */
	public UnitDefinition getVolumeUnitsInstance() {
		if (getModel() != null){
			return getModel().getUnitDefinition(this.volumeUnitsID);
		}
		return null;
	}
	/**
	 * Sets the volumeUnitsID of this Model to the id of the UnitDefinition 'volumeUnits'.
	 * @param volumeUnits
	 */
	public void setVolumeUnits(UnitDefinition volumeUnits) {
		this.volumeUnitsID = volumeUnits != null ? volumeUnits.getId() : null;
		stateChanged();
	}
	/**
	 * 
	 * @return the UnitDefinition instance which has the areaUnitsID of this Model as id. Null if it doesn't exist
	 */
	public UnitDefinition getAreaUnitsInstance() {
		if (getModel() != null){
			return getModel().getUnitDefinition(this.areaUnitsID);
		}
		return null;
	}
	/**
	 * Sets the areaUnitsID of this Model to the id of the UnitDefinition 'areaUnits'.
	 * @param areaUnits
	 */
	public void setAreaUnits(UnitDefinition areaUnits) {
		this.areaUnitsID = areaUnits != null ? areaUnits.getId() : null;
		stateChanged();
	}
	/**
	 * 
	 * @return the UnitDefinition instance which has the lengthUnitsID of this Model as id. Null if it doesn't exist
	 */
	public UnitDefinition getLengthUnitsInstance() {
		if (getModel() != null){
			return getModel().getUnitDefinition(this.lengthUnitsID);
		}
		return null;
	}
	/**
	 * Sets the lengthUnitsID of this Model to the id of the UnitDefinition 'lengthUnits'.
	 * @param lengthUnits
	 */
	public void setLengthUnits(UnitDefinition lengthUnits) {
		this.lengthUnitsID = lengthUnits != null ? lengthUnits.getId() : null;
		stateChanged();
	}
	/**
	 * 
	 * @return the UnitDefinition instance which has the extentUnitsID of this Model as id. Null if it doesn't exist
	 */
	public UnitDefinition getExtentUnitsInstance() {
		if (getModel() != null){
			return getModel().getUnitDefinition(this.extentUnitsID);
		}
		return null;
	}
	/**
	 * Sets the extentUnitsID of this Model to the id of the UnitDefinition 'extentUnits'.
	 * @param extentUnits
	 */
	public void setExtentUnits(UnitDefinition extentUnits) {
		this.extentUnitsID = extentUnits != null ? extentUnits.getId() : null;
		stateChanged();
	}
	/**
	 * 
	 * @return the Parameter instance which has the conversionFactorID of this Model as id. Null if it doesn't exist
	 */
	public Parameter getConversionFactorInstance() {
		if (getModel() != null){
			return getModel().getParameter(this.conversionFactorID);
		}
		return null;
	}
	/**
	 * Sets the conversionFactorID of this Model to the id of the Parameter 'conversionFactor'.
	 * @param conversionFactor
	 */
	public void setConversionFactor(Parameter conversionFactor) {
		this.conversionFactorID = conversionFactor != null ? conversionFactor.getId() : null;
		stateChanged();
	}

	/**
	 * 
	 * @return the timeUnitsID of this Model. Returns an empty String if it is not set.
	 */
	public String getTimeUnits() {
		return isSetTimeUnits() ? timeUnitsID : "";
	}

	/**
	 * Sets the timeUnits of this Model to 'timeUnistID'
	 * @param timeUnitsID
	 */
	public void setTimeUnits(String timeUnitsID) {
		this.timeUnitsID = timeUnitsID;
		stateChanged();
	}
	/**
	 * 
	 * @return the volumeUnitsID of this Model. Returns an empty String if it is not set.
	 */
	public String getVolumeUnits() {
		return isSetVolumeUnits() ? volumeUnitsID : "";
	}

	/**
	 * Sets the volumeUnitsID of this Model to 'volumeUnitsID'
	 * @param volumeUnitsID
	 */
	public void setVolumeUnits(String volumeUnitsID) {
		this.volumeUnitsID = volumeUnitsID;
		stateChanged();
	}
	/**
	 * 
	 * @return the areaUnitsID of this Model. Returns an empty String if it is not set.
	 */
	public String getAreaUnits() {
		return isSetAreaUnits() ? areaUnitsID : "";
	}

	/**
	 * Sets the areaUnitsID of this Model to 'areaUnitsID'
	 * @param areaUnitsID
	 */
	public void setAreaUnits(String areaUnitsID) {
		this.areaUnitsID = areaUnitsID;
		stateChanged();
	}
	/**
	 * 
	 * @return the lengthUnitsID of this Model. Returns an empty String if it is not set.
	 */
	public String getLengthUnits() {
		return isSetLengthUnits() ? lengthUnitsID : "";
	}

	/**
	 * Sets the lengthUnitsID of this Model to 'lengthUnitsID'.
	 * @param lengthUnitsID
	 */
	public void setLengthUnits(String lengthUnitsID) {
		this.lengthUnitsID = lengthUnitsID;
		stateChanged();
	}
	/**
	 * 
	 * @return the extentUnitsID of this Model. Returns an empty String if it is not set.
	 */
	public String getExtentUnits() {
		return isSetExtentUnits() ? extentUnitsID : "";
	}

	/**
	 * Sets the extendUnitsID of this Model to 'extentUnitsID'.
	 * @param extentUnitsID
	 */
	public void setExtentUnits(String extentUnitsID) {
		this.extentUnitsID = extentUnitsID;
		stateChanged();
	}
	/**
	 * 
	 * @return the conversionFactorID of this Model. Returns an empty String if it is not set.
	 */
	public String getConversionFactor() {
		return isSetConversionFactor() ? conversionFactorID : "";
	}

	/**
	 * Sets the conversionFactorID of this Model to 'conversionFactorID'.
	 * @param conversionFactorID
	 */
	public void setConversionFactor(String conversionFactorID) {
		this.conversionFactorID = conversionFactorID;
		stateChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jsbml.element.SBase#addChangeListener(org.sbml.squeezer.io.SBaseChangedListener
	 * )
	 */
	public void addChangeListener(SBaseChangedListener l) {
		super.addChangeListener(l);
		listOfFunctionDefinitions.addChangeListener(l);
		listOfUnitDefinitions.addChangeListener(l);
		listOfCompartmentTypes.addChangeListener(l);
		listOfSpeciesTypes.addChangeListener(l);
		listOfCompartments.addChangeListener(l);
		listOfSpecies.addChangeListener(l);
		listOfParameters.addChangeListener(l);
		listOfInitialAssignments.addChangeListener(l);
		listOfRules.addChangeListener(l);
		listOfConstraints.addChangeListener(l);
		listOfReactions.addChangeListener(l);
		listOfEvents.addChangeListener(l);
	}

	/**
	 * Adds a Compartment instance to the listOfCompartments of this Model.
	 * @param compartment
	 */
	public void addCompartment(Compartment compartment) {
		if (!isSetListOfCompartments()){
			this.listOfCompartments = new ListOf<Compartment>();
			setThisAsParentSBMLObject(this.listOfCompartments);
		}
		if (!listOfCompartments.contains(compartment)) {
			setThisAsParentSBMLObject(compartment);
			listOfCompartments.add(compartment);
		}
	}

	/**
	 * Adds a CompartmentType instance to the listOfCompartmentTypes of this Model.
	 * @param compartmentType
	 */
	@Deprecated
	public void addCompartmentType(CompartmentType compartmentType) {
		if (!isSetListOfCompartmentTypes()){
			this.listOfCompartmentTypes = new ListOf<CompartmentType>();
			setThisAsParentSBMLObject(this.listOfCompartmentTypes);
		}
		if (!listOfCompartmentTypes.contains(compartmentType)) {
			setThisAsParentSBMLObject(compartmentType);
			listOfCompartmentTypes.add(compartmentType);
		}
	}

	/**
	 * Adds a Constraint instance to the listOfConstraints of this Model.
	 * @param constraint
	 */
	public void addConstraint(Constraint constraint) {
		if (!isSetListOfConstraints()){
			this.listOfConstraints = new ListOf<Constraint>();
			setThisAsParentSBMLObject(this.listOfConstraints);
		}
		if (!listOfConstraints.contains(constraint)) {
			setThisAsParentSBMLObject(constraint);
			listOfConstraints.add(constraint);
		}
	}

	/**
	 * Adds an Event instance to the listOfEvents of this Model.
	 * @param event
	 */
	public void addEvent(Event event) {
		if (!isSetListOfEvents()){
			this.listOfEvents = new ListOf<Event>();
			setThisAsParentSBMLObject(this.listOfEvents);
		}
		if (!listOfEvents.contains(event)) {
			setThisAsParentSBMLObject(event);
			listOfEvents.add(event);
		}
	}

	/**
	 * Adds a FunctionDefinition instance to the listOfFunctionDefinitions of this Model.
	 * @param functionDefinition
	 */
	public void addFunctionDefinition(FunctionDefinition functionDefinition) {
		if (!isSetListOfFunctionDefinitions()){
			this.listOfFunctionDefinitions = new ListOf<FunctionDefinition>();
			setThisAsParentSBMLObject(this.listOfFunctionDefinitions);
		}
		if (!listOfFunctionDefinitions.contains(functionDefinition)) {
			setThisAsParentSBMLObject(functionDefinition);
			listOfFunctionDefinitions.add(functionDefinition);
		}
	}

	/**
	 * Adds an InitialAssignment instance to the listOfInitialAssignments of this Model.
	 * @param initialAssignment
	 */
	public void addInitialAssignment(InitialAssignment initialAssignment) {
		if (!isSetListOfInitialAssignments()){
			this.listOfInitialAssignments = new ListOf<InitialAssignment>();
			setThisAsParentSBMLObject(this.listOfInitialAssignments);
		}
		if (!listOfInitialAssignments.contains(initialAssignment)) {
			setThisAsParentSBMLObject(initialAssignment);
			listOfInitialAssignments.add(initialAssignment);
		}
	}

	/**
	 * Adds a Parameter instance to the listOfParameters of this Model.
	 * @param parameter
	 */
	public void addParameter(Parameter parameter) {
		if (!isSetListOfParameters()){
			this.listOfParameters = new ListOf<Parameter>();
			setThisAsParentSBMLObject(this.listOfParameters);
		}
		if (!listOfParameters.contains(parameter)) {
			setThisAsParentSBMLObject(parameter);
			listOfParameters.add(parameter);
		}
	}

	/**
	 * Adds a Reaction instance to the listOfReactions of this Model.
	 * 
	 * @param reaction
	 */
	public void addReaction(Reaction reaction) {
		if (!isSetListOfReactions()){
			this.listOfReactions = new ListOf<Reaction>();
			setThisAsParentSBMLObject(this.listOfReactions);
		}
		if (!listOfReactions.contains(reaction)) {
			setThisAsParentSBMLObject(reaction);
			listOfReactions.add(reaction);
		}
	}

	/**
	 * Adds a Rule instance to the listOfRules of this Model.
	 * @param rule
	 */
	public void addRule(Rule rule) {
		if (!isSetListOfRules()){
			this.listOfRules = new ListOf<Rule>();
			setThisAsParentSBMLObject(this.listOfRules);
		}
		if (!listOfRules.contains(rule)) {
			setThisAsParentSBMLObject(rule);
			listOfRules.add(rule);
		}
	}

	/**
	 * Adds a Species instance to the listOfSpecies of this Model.
	 * 
	 * @param spec
	 */
	public void addSpecies(Species spec) {
		if (!isSetListOfSpecies()){
			this.listOfSpecies = new ListOf<Species>();
			setThisAsParentSBMLObject(this.listOfSpecies);
		}
		if (!listOfSpecies.contains(spec)) {
			setThisAsParentSBMLObject(spec);
			listOfSpecies.add(spec);
		}
	}

	/**
	 * Adds a SpeciesType instance to the listOfSpeciesTypes of this Model.
	 * @param speciesType
	 */
	@Deprecated
	public void addSpeciesType(SpeciesType speciesType) {
		if (!isSetListOfSpeciesTypes()){
			this.listOfSpeciesTypes = new ListOf<SpeciesType>();
			setThisAsParentSBMLObject(this.listOfSpeciesTypes);
		}
		if (!listOfSpeciesTypes.contains(speciesType)) {
			setThisAsParentSBMLObject(speciesType);
			listOfSpeciesTypes.add(speciesType);
		}
	}

	/**
	 * Adds an UnitDefinition instance to the listOfUnitDefinitions of this Model.
	 * @param unitDefinition
	 */
	public void addUnitDefinition(UnitDefinition unitDefinition) {
		if (!isSetListOfUnitDefinitions()){
			this.listOfUnitDefinitions = new ListOf<UnitDefinition>();
			setThisAsParentSBMLObject(this.listOfUnitDefinitions);
		}
		if (!listOfUnitDefinitions.contains(unitDefinition)) {
			setThisAsParentSBMLObject(unitDefinition);
			listOfUnitDefinitions.add(unitDefinition);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#clone()
	 */
	// @Override
	public Model clone() {
		return new Model(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.NamedSBase#equals(java.lang.Object)
	 */
	// @Override
	public boolean equals(Object o) {
		boolean equal = super.equals(o);
		if (o instanceof Model) {
			Model m = (Model) o;
			if ((m.isSetListOfFunctionDefinitions() && !isSetListOfFunctionDefinitions()) || (!m.isSetListOfFunctionDefinitions() && isSetListOfFunctionDefinitions())){
				return false;
			}
			else if (m.isSetListOfFunctionDefinitions() && isSetListOfFunctionDefinitions()){
				equal &= getListOfFunctionDefinitions().equals(m.getListOfFunctionDefinitions());
			}
			if ((m.isSetListOfUnitDefinitions() && !isSetListOfUnitDefinitions()) || (!m.isSetListOfUnitDefinitions() && isSetListOfUnitDefinitions())){
				return false;
			}
			else if (m.isSetListOfUnitDefinitions() && isSetListOfUnitDefinitions()){
				equal &= getListOfUnitDefinitions().equals(m.getListOfUnitDefinitions());
			}
			if ((m.isSetListOfCompartmentTypes() && !isSetListOfCompartmentTypes()) || (!m.isSetListOfCompartmentTypes() && isSetListOfCompartmentTypes())){
				return false;
			}
			else if (m.isSetListOfCompartmentTypes() && isSetListOfCompartmentTypes()){
				equal &= getListOfCompartmentTypes().equals(m.getListOfCompartmentTypes());
			}
			if ((m.isSetListOfSpeciesTypes() && !isSetListOfSpeciesTypes()) || (!m.isSetListOfSpeciesTypes() && isSetListOfSpeciesTypes())){
				return false;
			}
			else if (m.isSetListOfSpeciesTypes() && isSetListOfSpeciesTypes()){
				equal &= getListOfSpeciesTypes().equals(m.getListOfSpeciesTypes());
			}
			if ((m.isSetListOfCompartments() && !isSetListOfCompartments()) || (!m.isSetListOfCompartments() && isSetListOfCompartments())){
				return false;
			}
			else if (m.isSetListOfCompartments() && isSetListOfCompartments()){
				equal &= getListOfCompartments().equals(m.getListOfCompartments());
			}
			if ((m.isSetListOfSpecies() && !isSetListOfSpecies()) || (!m.isSetListOfSpecies() && isSetListOfSpecies())){
				return false;
			}
			else if (m.isSetListOfSpecies() && isSetListOfSpecies()){
				equal &= getListOfSpecies().equals(m.getListOfSpecies());
			}
			if ((m.isSetListOfParameters() && !isSetListOfParameters()) || (!m.isSetListOfParameters() && isSetListOfParameters())){
				return false;
			}
			else if (m.isSetListOfParameters() && isSetListOfParameters()){
				equal &= getListOfParameters().equals(m.getListOfParameters());
			}
			if ((m.isSetListOfInitialAssignments() && !isSetListOfInitialAssignments()) || (!m.isSetListOfInitialAssignments() && isSetListOfInitialAssignments())){
				return false;
			}
			else if (m.isSetListOfInitialAssignments() && isSetListOfInitialAssignments()){
				equal &= getListOfInitialAssignments().equals(m.getListOfInitialAssignments());
			}
			if ((m.isSetListOfRules() && !isSetListOfRules()) || (!m.isSetListOfRules() && isSetListOfRules())){
				return false;
			}
			else if (m.isSetListOfRules() && isSetListOfRules()){
				equal &= getListOfRules().equals(m.getListOfRules());
			}
			if ((m.isSetListOfConstraints() && !isSetListOfConstraints()) || (!m.isSetListOfConstraints() && isSetListOfConstraints())){
				return false;
			}
			else if (m.isSetListOfConstraints() && isSetListOfConstraints()){
				equal &= getListOfConstraints().equals(m.getListOfConstraints());
			}
			if ((m.isSetListOfReactions() && !isSetListOfReactions()) || (!m.isSetListOfReactions() && isSetListOfReactions())){
				return false;
			}
			else if (m.isSetListOfReactions() && isSetListOfReactions()){
				equal &= getListOfReactions().equals(m.getListOfReactions());
			}
			if ((m.isSetListOfEvents() && !isSetListOfEvents()) || (!m.isSetListOfEvents() && isSetListOfEvents())){
				return false;
			}
			else if (m.isSetListOfEvents() && isSetListOfEvents()){
				equal &= getListOfEvents().equals(m.getListOfEvents());
			}
			if ((m.isSetTimeUnitsInstance() && !isSetTimeUnitsInstance()) || (!m.isSetTimeUnitsInstance() && isSetTimeUnitsInstance())){
				return false;
			}
			else if (m.isSetTimeUnits() && isSetTimeUnits()){
				equal &= getTimeUnits().equals(m.getTimeUnits());
			}
			if ((m.isSetAreaUnits() && !isSetAreaUnits()) || (!m.isSetAreaUnits() && isSetAreaUnits())){
				return false;
			}
			else if (m.isSetAreaUnits() && isSetAreaUnits()){
				equal &= getAreaUnits().equals(m.getAreaUnits());
			}
			if ((m.isSetConversionFactor() && !isSetConversionFactor()) || (!m.isSetConversionFactor() && isSetConversionFactor())){
				return false;
			}
			else if (m.isSetConversionFactor() && isSetConversionFactor()){
				equal &= getConversionFactor().equals(m.getConversionFactor());
			}
			if ((m.isSetExtentUnits() && !isSetExtentUnits()) || (!m.isSetExtentUnits() && isSetExtentUnits())){
				return false;
			}
			else if (m.isSetExtentUnits() && isSetExtentUnits()){
				equal &= getExtentUnits().equals(m.getExtentUnits());
			}
			if ((m.isSetLengthUnits() && !isSetLengthUnits()) || (!m.isSetLengthUnits() && isSetLengthUnits())){
				return false;
			}
			else if (m.isSetLengthUnits() && isSetLengthUnits()){
				equal &= getLengthUnits().equals(m.getLengthUnits());
			}
			if ((m.isSetSubstanceUnits() && !isSetSubstanceUnits()) || (!m.isSetSubstanceUnits() && isSetSubstanceUnits())){
				return false;
			}
			else if (m.isSetSubstanceUnits() && isSetSubstanceUnits()){
				equal &= getSubstanceUnits().equals(m.getSubstanceUnits());
			}
			if ((m.isSetVolumeUnits() && !isSetVolumeUnits()) || (!m.isSetVolumeUnits() && isSetVolumeUnits())){
				return false;
			}
			else if (m.isSetVolumeUnits() && isSetVolumeUnits()){
				equal &= getVolumeUnits().equals(m.getVolumeUnits());
			}
			return equal;
		}
		return false;
	}

	/**
	 * try to figure out the meaning of this name.
	 * 
	 * @param id
	 *            an id indicating a variable of the model.
	 * @return null if no model is available or the model does not contain a
	 *         compartment, species, or parameter wit the given id.
	 */
	public NamedSBase findNamedSBase(String id) {
		NamedSBase namedSBase = null;

		namedSBase = getCompartment(id);
		if (namedSBase == null){
			namedSBase = getSpecies(id);
		}
		if (namedSBase == null){
			namedSBase = getParameter(id);
		}
		if (namedSBase == null){
			namedSBase = getReaction(id);
		}
		if (namedSBase == null){
			namedSBase = getFunctionDefinition(id);
		}
		if (namedSBase == null){
			namedSBase = getUnitDefinition(id);
		}
		// check all local parameters
		if (namedSBase == null){
			for (Reaction reaction : getListOfReactions()) {
				if (reaction.isSetKineticLaw()){
					namedSBase = reaction.getKineticLaw().getParameter(id);
				}
				if (namedSBase != null){
					break;
				}
			}

		}
		return namedSBase;
	}

	/**
	 * 
	 * @param symbol
	 * @return the Compartment, Species, SpeciesReference or Parameter which has 'symbol' as id.
	 */
	public Symbol findSymbol(String symbol) {
		Symbol nsb = getSpecies(symbol);
		if (nsb == null){
			nsb = getParameter(symbol);
		}
		if (nsb == null){
			nsb = getCompartment(symbol);
		}
		if (nsb == null && isSetListOfReactions()){
			for (int i = 0; i < getNumReactions(); i++){
				Reaction reaction = getReaction(i);
				nsb = reaction.getReactant(symbol);
				if (nsb != null){
					break;
				}
				else {
					nsb = reaction.getProduct(symbol);
					if (nsb != null){
						break;
					}
				}
			}
			
		}
		return nsb;
	}

	/**
	 * 
	 * @param n
	 * @return the nth Compartment instance of the listOfCompartments. Null if if the listOfCompartments is not set.
	 */
	public Compartment getCompartment(int n) {
		if (isSetListOfCompartments()){
			return listOfCompartments.get(n);
		}
		return null;
	}

	/**
	 * 
	 * @param id
	 * @return the Compartment of the listOfCompartments which has 'id' as id (or name depending on the version and level). Null if if the listOfCompartments is not set.
	 */
	public Compartment getCompartment(String id) {
		if (isSetListOfCompartments() && id != null){
			for (Compartment comp : listOfCompartments) {
				if (comp.isSetId()){
					if (comp.getId().equals(id)){
						return comp;
					}
				}
				else if (comp.isSetName()){
					if (comp.getName().equals(id)){
						return comp;
					}
				}
			}
		}
		return null;
	}

	/**
	 * Gets the nth CompartmentType object in this Model.
	 * 
	 * @param n
	 *            index
	 * @return the nth CompartmentType of this Model. Returns null if there are no compartmentType defined
	 * or if the index n is too big or lower than zero.
	 */
	@Deprecated
	public CompartmentType getCompartmentType(int n) {
		if (isSetListOfCompartmentTypes() && n < listOfCompartmentTypes.size() && n >= 0){
			return listOfCompartmentTypes.get(n);
		}
		
		return null;
	}

	/**
	 * 
	 * @param id
	 * @return the CompartmentType of the listOfCompartmentTypes which has 'id' as id (or name depending on the level and version). Null if if the listOfCompartmentTypes is not set.
	 */
	@Deprecated
	public CompartmentType getCompartmentType(String id) {
		if (isSetListOfCompartmentTypes() && id != null){
			for (CompartmentType ct : listOfCompartmentTypes){
				if (ct.isSetId()){
					if (ct.getId().equals(id)){
						return ct;
					}
				}
				else if (ct.isSetName()){
					if (ct.getName().equals(id)){
						return ct;
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * Gets the nth SpeciesType object in this Model.
	 * 
	 * @param n
	 *            index
	 * @return the nth SpeciesType of this Model. Returns null if there are no speciesType defined
	 * or if the index n is too big or lower than zero.
	 */
	public SpeciesType getSpeciesType(int n) {
		if (isSetListOfSpeciesTypes() && n < listOfSpeciesTypes.size() && n >= 0){
			return listOfSpeciesTypes.get(n);
		}
		return null;
	}
	
	/**
	 * 
	 * @return true if the listOfConstraints of this Model is not null.
	 */
	public boolean isSetListOfConstraints(){
		return this.listOfConstraints != null;
	}
	
	/**
	 * 
	 * @return true if the substanceUnitsID of this Model is not null.
	 */
	public boolean isSetSubstanceUnits(){
		return this.substanceUnitsID != null;
	}
	
	/**
	 * 
	 * @return true if the UnitDefinition which has the substanceUnitsID of this Model as id is not null.
	 */
	public boolean isSetSubstanceUnitsInstance(){
		if (getModel() == null){
			return false;
		}
		return getModel().getUnitDefinition(this.substanceUnitsID) != null;
	}

	/**
	 * Get the nth Constraint object in this Model.
	 * 
	 * @param n
	 * @return the nth Constraint of this Model. Returns null if there are no constraint defined
	 * or if the index n is too big or lower than zero.
	 */
	public Constraint getConstraint(int n) {
		if (isSetListOfConstraints() && n < listOfConstraints.size() && n >= 0){
			return listOfConstraints.get(n);
		}
		return null;
	}
	

	/**
	 * Gets the nth Event object in this Model.
	 * 
	 * @param i
	 * @return the nth Event of this Model. Returns null if there are no event defined
	 * or if the index n is too big or lower than zero.
	 */
	public Event getEvent(int i) {
		if (isSetListOfEvents() && i < listOfEvents.size() && i >= 0){
			return listOfEvents.get(i);
		}
		return null;
	}
	
	/**
	 * Gets the nth UnitDefinition object in this Model.
	 * 
	 * @param i
	 * @return the nth UnitDefinition of this Model. Returns null if there are no UnitDefinition defined
	 * or if the index n is too big or lower than zero.
	 */
	public UnitDefinition getUnitDefinition(int i) {
		if (isSetListOfUnitDefinitions() && i < listOfUnitDefinitions.size() && i >= 0){
			return listOfUnitDefinitions.get(i);
		}
		return null;
	}

	/**
	 * 
	 * @param id
	 * @return the Event of the listOfEvents which has 'id' as id (or name depending on the level and version). Null if if the listOfEvents is not set.
	 */
	public Event getEvent(String id) {
		if (isSetListOfEvents() && id != null){
			for (Event ev : listOfEvents){
				if (ev.isSetId()){
					if (ev.getId().equals(id)){
						return ev;
					}
				}
				else if (ev.isSetName()){
					if (ev.getName().equals(id)){
						return ev;
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @return true if the listOfFunctionDefinitions of this Model is not null.
	 */
	public boolean isSetListOfFunctionDefinitions(){
		return this.listOfFunctionDefinitions != null;
	}
	
	/**
	 * 
	 * @return true if the timeUnitsID of this Model is not null.
	 */
	public boolean isSetTimeUnits(){
		return this.timeUnitsID != null;
	}
	
	/**
	 * 
	 * @return true if the UnitsDefinition which has the timeUnistID of this Model as id is not null.
	 */
	public boolean isSetTimeUnitsInstance(){
		if (getModel() == null){
			return false;
		}
		return getModel().getUnitDefinition(this.timeUnitsID) != null;
	}

	/**
	 * 
	 * @param n
	 * @return the nth FunctionDefinition instance of the listOfFunstionDefinitions. Null if if the listOfFunctionDefinitions is not set.
	 */
	public FunctionDefinition getFunctionDefinition(int n) {
		if (isSetListOfFunctionDefinitions() && n < listOfFunctionDefinitions.size() && n >= 0){
			return listOfFunctionDefinitions.get(n);
		}
		return null;
	}

	/**
	 * 
	 * @param id
	 * @return the FunctionDefinition of the listOfFunstionDefinitions which has 'id' as id (or name depending on the level and version). Null if if the listOfFunctionDefinitions is not set.
	 */
	public FunctionDefinition getFunctionDefinition(String id) {
		if (isSetListOfFunctionDefinitions() && id != null){
			for (FunctionDefinition f : listOfFunctionDefinitions) {
				if (f.isSetId()){
					if (f.getId().equals(id)){
						return f;
					}
				}
				else if (f.isSetName()){
					if (f.getName().equals(id)){
						return f;
					}
				}
			}
		}
		return null;
	}

	/**
	 * Get the nth InitialAssignment object in this Model.
	 * 
	 * @param n
	 * @return the nth InitialAssignment of this Model. Null if the listOfInitialAssignments is not set.
	 */
	public InitialAssignment getInitialAssignment(int n) {
		if (isSetListOfInitialAssignments() && n < listOfInitialAssignments.size() && n >= 0){
			return listOfInitialAssignments.get(n);
		}
		return null;
	}

	/**
	 * 
	 * @return the listOfCompartments of this Model. Can be null if it is not set.
	 */
	public ListOf<Compartment> getListOfCompartments() {
		return listOfCompartments;
	}

	/**
	 * 
	 * @return the listOfCompartmentTypes of this Model. Can be null if it is not set.
	 */
	@Deprecated
	public ListOf<CompartmentType> getListOfCompartmentTypes() {
		return listOfCompartmentTypes;
	}

	/**
	 * @return the listOfConstraints of this Model. Can be null if it is not set.
	 */
	public ListOf<Constraint> getListOfConstraints() {
		return listOfConstraints;
	}

	/**
	 * 
	 * @return the listOfEvents of this Model. Can be null if it is not set.
	 */
	public ListOf<Event> getListOfEvents() {
		return listOfEvents;
	}

	/**
	 * 
	 * @return the listOfFunctionDefinitions of this Model. Can be null if it is not set.
	 */
	public ListOf<FunctionDefinition> getListOfFunctionDefinitions() {
		return listOfFunctionDefinitions;
	}

	/**
	 * @return the listOfInitialAssignments of this Model. Can be null if it is not set.
	 */
	public ListOf<InitialAssignment> getListOfInitialAssignments() {
		return listOfInitialAssignments;
	}

	/**
	 * 
	 * @return the listOfParameters of this Model. Can be null if it is not set.
	 */
	public ListOf<Parameter> getListOfParameters() {
		return listOfParameters;
	}

	/**
	 * 
	 * @return the listOfReactions of this Model. Can be null if it is not set.
	 */
	public ListOf<Reaction> getListOfReactions() {
		return listOfReactions;
	}

	/**
	 * 
	 * @return the listOfRules of this Model. Can be null if it is not set.
	 */
	public ListOf<Rule> getListOfRules() {
		return listOfRules;
	}

	/**
	 * 
	 * @return the listOfSpecies of this Model. Can be null if it is not set.
	 */
	public ListOf<Species> getListOfSpecies() {
		return listOfSpecies;
	}

	/**
	 * 
	 * @return the listOfSpeciesTypes of this Model. Can be null if it is not set.
	 */
	@Deprecated
	public ListOf<SpeciesType> getListOfSpeciesTypes() {
		return listOfSpeciesTypes;
	}

	/**
	 * 
	 * @return the listOfUnitDefinitions of this Model. Can be null if it is not set.
	 */
	public ListOf<UnitDefinition> getListOfUnitDefinitions() {
		return listOfUnitDefinitions;
	}

	/**
	 * 
	 * @return the number of Compartments of this Model.
	 */
	public int getNumCompartments() {
		if (isSetListOfCompartments()){
			return listOfCompartments.size();
		}
		return 0;
	}

	/**
	 * 
	 * @return the number of CompartmentTypes of this Model.
	 */
	@Deprecated
	public int getNumCompartmentTypes() {
		if (isSetListOfCompartmentTypes()){
			return listOfCompartmentTypes.size();
		}
		return 0;
	}

	/**
	 * 
	 * @return the number of Constraints of this Model.
	 */
	public int getNumConstraints() {
		if (isSetListOfConstraints()){
			return listOfConstraints.size();
		}
		return 0;
	}

	/**
	 * 
	 * @return the number of Events of this Model.
	 */
	public int getNumEvents() {
		if (isSetListOfEvents()){
			return listOfEvents.size();
		}
		return 0;
	}

	/**
	 * 
	 * @return the number of FunctionDefinitions of this Model.
	 */
	public int getNumFunctionDefinitions() {
		if (isSetListOfFunctionDefinitions()){
			return listOfFunctionDefinitions.size();
		}
		return 0;
	}

	/**
	 * 
	 * @return the number of InitialAssignments of this Model.
	 */
	public int getNumInitialAssignments() {
		if (isSetListOfInitialAssignments()){
			return listOfInitialAssignments.size();
		}
		return 0;
	}

	/**
	 * Returns the number of parameters that are contained within kineticLaws in
	 * the reactions of this model.
	 * 
	 * @return
	 */
	public int getNumLocalParameters() {
		int count = 0;
		if (isSetListOfReactions()){
			for (Reaction reaction : listOfReactions){
				if (reaction.isSetKineticLaw()){
					count += reaction.getKineticLaw().getNumParameters();
				}
			}
		}
		return count;
	}

	/**
	 * 
	 * @return the number of Parameters of this Model.
	 */
	public int getNumParameters() {
		if (isSetListOfParameters()){
			return listOfParameters.size();
		}
		return 0;
	}

	/**
	 * 
	 * @return the number of Reactions of this Model.
	 */
	public int getNumReactions() {
		if (isSetListOfReactions()){
			return listOfReactions.size();
		}
		return 0;
	}

	/**
	 * 
	 * @return the number of Rules of this model.
	 */
	public int getNumRules() {
		if (isSetListOfRules()){
			return listOfRules.size();
		}
		return 0;
	}

	/**
	 * 
	 * @return the number of Species of this Model.
	 */
	public int getNumSpecies() {
		if (isSetListOfSpecies()){
			return listOfSpecies.size();
		}
		return 0;
	}

	/**
	 * 
	 * @return the number of SpeciesTypes of this Model.
	 */
	@Deprecated
	public int getNumSpeciesTypes() {
		if (isSetListOfSpeciesTypes()){
			return listOfSpeciesTypes.size();
		}
		return 0;
	}

	/**
	 * 
	 * @return the number of UnitDefinitions of this Model.
	 */
	public int getNumUnitDefinitions() {
		if (isSetListOfUnitDefinitions()){
			return listOfUnitDefinitions.size();
		}
		return 0;
	}

	/**
	 * Gets the nth Parameter object in this Model.
	 * 
	 * @param n index
	 * @return the nth Parameter of this Model.
	 */
	public Parameter getParameter(int n) {
		if (isSetListOfParameters() && n < listOfParameters.size() && n >= 0){
			return listOfParameters.get(n);
		}
		return null;
	}

	/**
	 * 
	 * @param id
	 * @return the Parameter of the listOfParameters which has 'id' as id (or name depending on the level and version). Null if it doesn't exist.
	 */
	public Parameter getParameter(String id) {
		if (isSetListOfParameters() && id != null){
			for (Parameter parameter : listOfParameters) {
				if (parameter.isSetId()){
					if (parameter.getId().equals(id)){
						return parameter;
					}
				}
				else if (parameter.isSetName()){
					if (parameter.getName().equals(id)){
						return parameter;
					}
				}
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.AbstractSBase#getParentSBMLObject()
	 */
	// @Override
	public SBMLDocument getParentSBMLObject() {
		return (SBMLDocument) parentSBMLObject;
	}

	/**
	 * Gets the n-th Reaction object in this Model.
	 * 
	 * @param reactionIndex
	 * @return the n-th Reaction of this Model.
	 */
	public Reaction getReaction(int n) {
		if (isSetListOfReactions() && n < getNumReactions() && n >= 0){
			return listOfReactions.get(n);
		}
		return null;
	}

	/**
	 * 
	 * @param id
	 * @return the Reaction of the listOfReactions which has 'id' as id (or name depending on the level and version). Null if it doesn't exist.
	 */
	public Reaction getReaction(String id) {
		if (isSetListOfReactions() && id != null){
			for (Reaction reaction : listOfReactions) {
				if (reaction.isSetId()){
					if (reaction.getId().equals(id)){
						return reaction;
					}
				}
				else if (reaction.isSetName()){
					if (reaction.getName().equals(id)){
						return reaction;
					}
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @param n
	 * @return the nth Rule of the listOfRules. Null if it doesn't exist.
	 */
	public Rule getRule(int n) {
		if (isSetListOfRules() && n < listOfRules.size() && n >= 0){
			return listOfRules.get(n);
		}
		return null;
	}

	/**
	 * Gets the n-th Species object in this Model.
	 * 
	 * @param n
	 *            the nth Species of this Model.
	 * @return
	 */
	public Species getSpecies(int n) {
		if (isSetListOfSpecies() && n < listOfSpecies.size() && n >= 0){
			return listOfSpecies.get(n);
		}
		return null;
	}

	/**
	 * 
	 * @param id
	 * @return the Species of the listOfSpecies which has 'id' as id (or name depending on the level and version). Null if it doesn't exist.
	 */
	public Species getSpecies(String id) {
		if (isSetListOfSpecies() && id != null){
			for (Species species : listOfSpecies) {
				if (species.isSetId()){
					if (species.getId().equals(id)){
						return species;
					}
				}
				else if (species.isSetName()){
					if (species.getName().equals(id)){
						return species;
					}
				}
			}
		}
		return null;
	}


	/**
	 * 
	 * @param id
	 * @return the SpeciesType of the listOfSpeciesTypes which has 'id' as id (or name depending on the level and version). Null if it doesn't exist.
	 */
	@Deprecated
	public SpeciesType getSpeciesType(String id) {
		if (isSetListOfSpeciesTypes() && id != null){
			for (SpeciesType st : listOfSpeciesTypes){
				if (st.isSetId()){
					if (st.getId().equals(id)){
						return st;
					}
				}
				else if (st.isSetName()){
					if (st.getName().equals(id)){
						return st;
					}
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @param id
	 * @return the UnitDefinition of the listOfUnitDefinitions which has 'id' as id (or name depending on the level and version). Null if it doesn't exist.
	 */
	public UnitDefinition getUnitDefinition(String id) {
		if (isSetListOfUnitDefinitions() && id != null){
			for (UnitDefinition unitdef : listOfUnitDefinitions){
				if (unitdef.isSetId()){
					if (unitdef.getId().equals(id)){
						return unitdef;
					}
				}
				else if (unitdef.isSetName()){
					if (unitdef.getName().equals(id)){
						return unitdef;
					}
				}
			}
		}

		UnitDefinition ud = null;
		if (id != null){
			if (id.equals("area")) {
				ud = UnitDefinition.area(getLevel(), getVersion());
				addUnitDefinition(ud);
			} else if (id.equals("length")) {
				ud = UnitDefinition.length(getLevel(), getVersion());
				addUnitDefinition(ud);
			} else if (id.equals("substance")) {
				ud = UnitDefinition.substance(getLevel(), getVersion());
				addUnitDefinition(ud);
			} else if (id.equals("time")) {
				ud = UnitDefinition.time(getLevel(), getVersion());
				addUnitDefinition(ud);
			} else if (id.equals("volume")) {
				ud = UnitDefinition.volume(getLevel(), getVersion());
				addUnitDefinition(ud);
			}
		}
		return ud;
	}

	/**
	 * Removes the Parameter 'parameter' from this Model.
	 * @param parameter
	 */
	public void removeParameter(Parameter parameter) {
		if (isSetListOfParameters()){
			listOfParameters.remove(parameter);
			parameter.sbaseRemoved();
		}
	}

	/**
	 * removes a reaction from the model
	 * 
	 * @param reac
	 */
	public void removeReaction(Reaction reac) {
		if (isSetListOfReactions()){
			listOfReactions.remove(reac);
			reac.sbaseRemoved();
		}
	}

	/**
	 * removes a species from the model
	 * 
	 * @param spec
	 * @return success
	 */
	public boolean removeSpecies(Species spec) {
		boolean success = false;

		if (isSetListOfSpecies()){
			success = listOfSpecies.remove(spec);

		}
		if (success){
			spec.sbaseRemoved();
		}

		return success;
	}

	/**
	 * 
	 * @param unitDefininition
	 * @return true if the UnitDefinition 'unitDefinition' has been removed from the Model.
	 */
	public boolean removeUnitDefinition(UnitDefinition unitDefininition) {
		boolean success = false;
		if (isSetListOfUnitDefinitions()){
			success = listOfUnitDefinitions.remove(unitDefininition);

		}

		if (success) {
		    unitDefininition.sbaseRemoved();
		}

		return success;
	}

	/**
	 * Sets the listOfCompartments of this Model to 'listOfCompartments'. Automatically sets the parentSBML objects
	 * of 'listOfCompartments' to this Model.
	 * @param listOfCompartments
	 */
	public void setListOfCompartments(ListOf<Compartment> listOfCompartments) {
		this.listOfCompartments = listOfCompartments;
		setThisAsParentSBMLObject(this.listOfCompartments);
		this.listOfCompartments.setSBaseListType(SBaseListType.listOfCompartments);

		stateChanged();
	}

	/**
	 * Sets the listOfCompartmentTypes of this Model to 'listOfCompartmentTypes'. Automatically sets the parentSBML objects
	 * of 'listOfCompartmentTypes' to this Model.
	 * @param listOfCompartmentTypes
	 *            the listOfCompartmentTypes to set
	 */
	@Deprecated
	public void setListOfCompartmentTypes(
			ListOf<CompartmentType> listOfCompartmentTypes) {
		this.listOfCompartmentTypes = listOfCompartmentTypes;
		setThisAsParentSBMLObject(this.listOfCompartmentTypes);
		this.listOfCompartmentTypes.setSBaseListType(SBaseListType.listOfCompartmentTypes);
		stateChanged();
	}

	/**
	 * Sets the listOfConstraints of this Model to 'listOfConstraints'. Automatically sets the parentSBML objects
	 * of 'listOfCanstraints' to this Model.
	 * @param listOfConstraints
	 *            the listOfConstraints to set
	 */
	public void setListOfConstraints(ListOf<Constraint> listOfConstraints) {
		this.listOfConstraints = listOfConstraints;
		setThisAsParentSBMLObject(this.listOfConstraints);
		this.listOfConstraints.setSBaseListType(SBaseListType.listOfConstraints);
		stateChanged();

	}

	/**
	 * Sets the listOfEvents of this Model to 'listOfEvents'. Automatically sets the parentSBML objects
	 * of 'listOfEvents' to this Model.
	 * @param listOfEvents
	 */
	public void setListOfEvents(ListOf<Event> listOfEvents) {
		this.listOfEvents = listOfEvents;
		setThisAsParentSBMLObject(this.listOfEvents);
		this.listOfEvents.setSBaseListType(SBaseListType.listOfEvents);
		stateChanged();
	}

	/**
	 * Sets the listOfFunctionDefinitions of this Model to 'listOfFunctionDefinitions'. Automatically sets the parentSBML objects
	 * of 'listOfFunctionDefinitions' to this Model.
	 * @param listOfFunctionDefinitions
	 *            the listOfFunctionDefinitions to set
	 */
	public void setListOfFunctionDefinitions(
			ListOf<FunctionDefinition> listOfFunctionDefinitions) {
		this.listOfFunctionDefinitions = listOfFunctionDefinitions;
		setThisAsParentSBMLObject(this.listOfFunctionDefinitions);
		this.listOfFunctionDefinitions.setSBaseListType(SBaseListType.listOfFunctionDefinitions);

		stateChanged();
	}

	/**
	 * Sets the listOfInitialAssignments of this Model to 'listOfInitialAssignments'. Automatically sets the parentSBML objects
	 * of 'listOfInitialAssignments' to this Model. 
	 * @param listOfInitialAssignments
	 *            the listOfInitialAssignments to set
	 */
	public void setListOfInitialAssignments(
			ListOf<InitialAssignment> listOfInitialAssignments) {
		this.listOfInitialAssignments = listOfInitialAssignments;
		setThisAsParentSBMLObject(this.listOfInitialAssignments);
		this.listOfInitialAssignments.setSBaseListType(SBaseListType.listOfInitialAssignments);
		stateChanged();

	}

	/**
	 * Sets the listOfParameters of this Model to 'listOfParameters'. Automatically sets the parentSBML objects
	 * of 'listOfParameters' to this Model.
	 * @param listOfParameters
	 */
	public void setListOfParameters(ListOf<Parameter> listOfParameters) {
		this.listOfParameters = listOfParameters;
		setThisAsParentSBMLObject(this.listOfParameters);
		this.listOfParameters.setSBaseListType(SBaseListType.listOfParameters);

		stateChanged();
	}

	/**
	 * Sets the listOfReactions of this Model to 'listOfReactions'. Automatically sets the parentSBML objects
	 * of 'listOfReactions' to this Model.
	 * @param listOfReactions
	 */
	public void setListOfReactions(ListOf<Reaction> listOfReactions) {
		this.listOfReactions = listOfReactions;
		setThisAsParentSBMLObject(this.listOfReactions);
		this.listOfReactions.setSBaseListType(SBaseListType.listOfReactions);

		stateChanged();
	}

	/**
	 * Sets the listOfRules of this Model to 'listOfRules'. Automatically sets the parentSBML objects
	 * of 'listOfRules' to this Model.
	 * @param listOfRules
	 */
	public void setListOfRules(ListOf<Rule> listOfRules) {
		this.listOfRules = listOfRules;
		setThisAsParentSBMLObject(this.listOfRules);
		this.listOfRules.setSBaseListType(SBaseListType.listOfRules);

		stateChanged();
	}

	/**
	 * Sets the listOfSpecies of this Model to 'listOfSpecies'. Automatically sets the parentSBML objects
	 * of 'listOfSpecies' to this Model. 
	 * @param listOfSpecies
	 */
	public void setListOfSpecies(ListOf<Species> listOfSpecies) {
		this.listOfSpecies = listOfSpecies;
		setThisAsParentSBMLObject(this.listOfSpecies);
		this.listOfSpecies.setSBaseListType(SBaseListType.listOfSpecies);

		stateChanged();
	}

	/**
	 * Sets the listOfSpeciesTypes of this Model to 'listOfSpeciesTypes'. Automatically sets the parentSBML objects
	 * of 'listOfSpeciesTypes' to this Model.
	 * @param listOfSpeciesTypes
	 *            the listOfSpeciesTypes to set
	 */
	@Deprecated
	public void setListOfSpeciesTypes(ListOf<SpeciesType> listOfSpeciesTypes) {
		this.listOfSpeciesTypes = listOfSpeciesTypes;
		setThisAsParentSBMLObject(this.listOfSpeciesTypes);
		this.listOfSpeciesTypes.setSBaseListType(SBaseListType.listOfSpeciesTypes);

		stateChanged();
	}

	/**
	 * Sets the listOfUnitDefinitions of this Model to 'listOfUnitDefinitions'. Automatically sets the parentSBML objects
	 * of 'listOfUnitDefinitions' to this Model.
	 * @param listOfUnitDefinitions
	 */
	public void setListOfUnitDefinitions(
			ListOf<UnitDefinition> listOfUnitDefinitions) {
		this.listOfUnitDefinitions = listOfUnitDefinitions;
		setThisAsParentSBMLObject(this.listOfUnitDefinitions);
		this.listOfUnitDefinitions.setSBaseListType(SBaseListType.listOfUnitDefinitions);
		stateChanged();
	}
	
	/**
	 * 
	 * @return the UnitDefinitions which has the substanceUnitsID of this Model as id. Null if it doesn't exist.
	 */
	public UnitDefinition getSubstanceUnitsInstance() {
		if (getModel() == null){
			return null;
		}
		return getModel().getUnitDefinition(this.substanceUnitsID);
	}
	/**
	 * Sets the substanceUnitsID of this Model to the id of 'substanceUnits'.
	 * @param substanceUnits
	 */
	public void setSubstanceUnits(UnitDefinition substanceUnits) {
		this.substanceUnitsID = substanceUnits != null ? substanceUnits.getId() : null;
	}

	/**
	 * 
	 * @return the substanceUnitsID of this model. Returns the empty String if it is not set.
	 */
	public String getSubstanceUnits() {
		return isSetSubstanceUnits() ? this.substanceUnitsID : "";
	}

	/**
	 * Sets the substanceUnitsID of this Model to 'substanceUnitsID'
	 * @param substanceUnitsID
	 */
	public void setSubstanceUnits(String substanceUnitsID) {
		this.substanceUnitsID = substanceUnitsID;
		stateChanged();
	}
	
	/**
	 * 
	 * @return true if the listOfRules of this Model is not null.
	 */
	public boolean isSetListOfRules(){
		return this.listOfRules != null;
	}
	
	/**
	 * 
	 * @return true if the listOfCompartments of this Model is not null.
	 */
	public boolean isSetListOfCompartments(){
		return this.listOfCompartments != null;
	}
	
	/**
	 * 
	 * @return true if the listOfSpecies of this Model is not null.
	 */
	public boolean isSetListOfSpecies(){
		return this.listOfSpecies != null;
	}
	
	/**
	 * 
	 * @return true if the listOfParameters of this Model is not null.
	 */
	public boolean isSetListOfParameters(){
		return this.listOfParameters != null;
	}
	
	/**
	 * 
	 * @return true if the listOfReactions of this Model is not null.
	 */
	public boolean isSetListOfReactions(){
		return this.listOfReactions != null;
	}
	
	/**
	 * 
	 * @return true if the listOfCompartmentTypes of this Model is not null.
	 */
	@Deprecated
	public boolean isSetListOfCompartmentTypes(){
		return this.listOfCompartmentTypes != null;
	}
	
	/**
	 * 
	 * @return true if the listOfEvents of this Model is not null.
	 */
	public boolean isSetListOfEvents(){
		return this.listOfEvents != null;
	}
	
	/**
	 * 
	 * @return true if the listOfUnitDefinitions of this Model is not null.
	 */
	public boolean isSetListOfUnitDefinitions(){
		return this.listOfUnitDefinitions != null;
	}
	
	/**
	 * 
	 * @return true if the listOfInitialAssignments of this Model is not null.
	 */
	public boolean isSetListOfInitialAssignments(){
		return this.listOfInitialAssignments != null;
	}
	
	/**
	 * 
	 * @return true if the listOfSpeciesTypes of this Model is not null.
	 */
	@Deprecated
	public boolean isSetListOfSpeciesTypes(){
		return this.listOfSpeciesTypes != null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.element.SBase#readAttribute(String attributeName, String prefix, String value)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix, String value){
		boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
	
		if (!isAttributeRead){
			if (attributeName.equals("substanceUnits") && getLevel() > 2){
				this.setSubstanceUnits(value);
			}
			else if (attributeName.equals("timeUnits") && getLevel() > 2){
				this.setTimeUnits(value);
			}
			else if (attributeName.equals("volumeUnits") && getLevel() > 2){
				this.setVolumeUnits(value);
			}
			else if (attributeName.equals("areaUnits") && getLevel() > 2){
				this.setAreaUnits(value);
			}
			else if (attributeName.equals("lengthUnits") && getLevel() > 2){
				this.setLengthUnits(value);
			}
			else if (attributeName.equals("extentUnits") && getLevel() > 2){
				this.setExtentUnits(value);
			}
			else if (attributeName.equals("conversionFactor") && getLevel() > 2){
				this.setConversionFactor(value);
			}
		}
		return isAttributeRead;
	}
	
	/**
	 * Sets the timeUnitsID of this Model to null.
	 */
	public void unsetTimeUnits(){
		this.timeUnitsID = null;
	}
	
	/**
	 * 
	 * @return true if the volumeUnitsID of this Model is not null.
	 */
	public boolean isSetVolumeUnits(){
		return this.volumeUnitsID != null;
	}
	
	/**
	 * 
	 * @return true if the UnitDefinition which has the volumeUnitsID of this Model as id is not null.
	 */
	public boolean isSetVolumeUnitsInstance(){
		if (getModel() == null){
			return false;
		}
		return getModel().getUnitDefinition(this.volumeUnitsID) != null;
	}
	
	/**
	 * Sets the volumeUnitsID of this Model to null.
	 */
	public void unsetVolumeUnits(){
		this.volumeUnitsID = null;
	}
	
	/**
	 *
	 * @return true if the areaUnitsID of this Model is not null.
	 */
	public boolean isSetAreaUnits(){
		return this.areaUnitsID != null;
	}
	
	/**
	 * 
	 * @return true if the UnitDefinition which has the areaUnitsID of this Model as id is not null.
	 */
	public boolean isSetAreaUnitsInstance(){
		if (getModel() == null){
			return false;
		}
		return getModel().getUnitDefinition(this.areaUnitsID) != null;
	}
	
	/**
	 * Sets the areaUnitsID of this Model to null.
	 */
	public void unsetAreaUnits(){
		this.areaUnitsID = null;
	}
	
	/**
	 * 
	 * @return true if the lengthUnitsID of this Model is not null.
	 */
	public boolean isSetLengthUnits(){
		return this.lengthUnitsID != null;
	}
	
	/**
	 * 
	 * @return true if the UnitDefinition which has the lengthUnitsID of this Model as id is not null.
	 */
	public boolean isSetLengthUnitsInstance(){
		if (getModel() == null){
			return false;
		}
		return getModel().getUnitDefinition(this.lengthUnitsID) != null;
	}
	
	/**
	 * Sets the lengthUnitsID of this Model to null.
	 */
	public void unsetLengthUnits(){
		this.lengthUnitsID = null;
	}
	
	/**
	 * 
	 * @return true if the extentUnitsID of this Model is not null.
	 */
	public boolean isSetExtentUnits(){
		return this.extentUnitsID != null;
	}
	
	/**
	 * 
	 * @return true if the UnitDefinition which has the extentUnitsID of this Model as id is not null.
	 */
	public boolean isSetExtentUnitsInstance(){
		if (getModel() == null){
			return false;
		}
		return getModel().getUnitDefinition(this.extentUnitsID) != null;
	}
	
	/**
	 * Sets the extentUnitsID of this Model to null.
	 */
	public void unsetExtentUnits(){
		this.extentUnitsID = null;
	}
	
	/**
	 * 
	 * @return true if the conversionFactorID of this Model is not null.
	 */
	public boolean isSetConversionFactor(){
		return this.conversionFactorID != null;
	}
	
	/**
	 * 
	 * @return true if the Parameter which has the conversionFactorID of this Model as id is not null.
	 */
	public boolean isSetConversionFactorInstance(){
		if (getModel() == null){
			return false;
		}
		return getModel().getUnitDefinition(this.conversionFactorID) != null;
	}
	
	/**
	 * Sets the conversionFactorID of this Model to null.
	 */
	public void unsetConversionFactor(){
		this.conversionFactorID = null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.element.SBase#writeXMLAttributes()
	 */
	@Override
	public HashMap<String, String> writeXMLAttributes() {
		HashMap<String, String> attributes = super.writeXMLAttributes();
		
		if (isSetSubstanceUnits() && getLevel() > 2){
			attributes.put("substanceUnitsID", getSubstanceUnits());
		}
		if (isSetTimeUnits() && getLevel() > 2){
			attributes.put("timeUnits", getTimeUnits());
		}
		else if (isSetVolumeUnits() && getLevel() > 2){
			attributes.put("volumeUnits", getVolumeUnits());
		}
		else if (isSetAreaUnits() && getLevel() > 2){
			attributes.put("areaUnits", getAreaUnits());
		}
		else if (isSetLengthUnits() && getLevel() > 2){
			attributes.put("lengthUnits", getLengthUnits());
		}
		else if (isSetExtentUnits() && getLevel() > 2){
			attributes.put("extentUnits", getExtentUnits());
		}
		else if (isSetConversionFactor() && getLevel() > 2){
			attributes.put("conversionFactor", getConversionFactor());
		}

		return attributes;
	}


	/**
	 * Creates a new {@link AlgebraicRule} inside this {@link Model} and returns it.
	 * <p>
	 * @return the {@link AlgebraicRule} object created
	 * <p>
	 * @see #addRule(Rule  r)
	 */
	public Rule createAlgebraicRule() {
		AlgebraicRule rule = new AlgebraicRule(level, version);
		addRule(rule);

		return rule;
	}


	/**
	 * Creates a new {@link AssignmentRule} inside this {@link Model} and returns it.
	 * <p>
	 * @return the {@link AssignmentRule} object created
	 * <p>
	 * @see #addRule(Rule  r)
	 */
	public Rule createAssignmentRule() {
		AssignmentRule rule = new AssignmentRule(level, version);
		addRule(rule);

		return rule;
	}

	/**
	 * Creates a new {@link RateRule} inside this {@link Model} and returns it.
	 * <p>
	 * @return the {@link RateRule} object created
	 * <p>
	 * @see #addRule(Rule  r)
	 */
	public RateRule createRateRule() {
		RateRule rule = new RateRule(level, version);
		addRule(rule);

		return rule;
	}


	/**
	 * Creates a new {@link Compartment} inside this {@link Model} and returns it.
	 * <p>
	 * @return the {@link Compartment} object created
	 * <p>
	 * @see #addCompartment(Compartment c)
	 */
	public Compartment createCompartment() {
		Compartment compartment = new Compartment(level, version);
		addCompartment(compartment);

		return compartment;
	}


	/**
	 * Creates a new {@link CompartmentType} inside this {@link Model} and returns it.
	 * <p>
	 * @return the {@link CompartmentType} object created
	 * <p>
	 * @see #addCompartmentType(CompartmentType  ct)
	 */
	@SuppressWarnings("deprecation")
	public CompartmentType createCompartmentType() {
		CompartmentType compartmentType = new CompartmentType(level, version);
		addCompartmentType(compartmentType);

		return compartmentType;
	}


	/**
	 * Creates a new {@link Constraint} inside this {@link Model} and returns it.
	 * <p>
	 * @return the {@link Constraint} object created
	 * <p>
	 * @see #addConstraint(Constraint c)
	 */
	public Constraint createConstraint() {
		Constraint constraint = new Constraint(level, version);
		addConstraint(constraint);

		return constraint;
	}


	/**
	 * Creates a new {@link Event} inside this {@link Model} and returns it.
	 * <p>
	 * @return the {@link Event} object created
	 */
	public Event createEvent() {
		Event event = new Event(level, version);
		addEvent(event);

		return event;
	}


	/**
	 * Creates a new {@link EventAssignment} inside the last {@link Event} object created in
	 * this {@link Model}, and returns a pointer to it.
	 * <p>
	 * The mechanism by which the last {@link Event} object in this model was created
	 * is not significant.  It could have been created in a variety of ways,
	 * for example by using createEvent().  If no {@link Event} object exists in this
	 * {@link Model} object, a new {@link EventAssignment} is <em>not</em> created and NULL is
	 * returned instead.
	 * <p>
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

		EventAssignment eventAssgnt = new EventAssignment(level, version);
		lastEvent.addEventAssignment(eventAssgnt);

		return eventAssgnt;

	}

	/**
	 * Creates a new {@link Trigger} inside the last {@link Event} object created in
	 * this {@link Model}, and returns a pointer to it.
	 * <p>
	 * The mechanism by which the last {@link Event} object in this model was created
	 * is not significant.  It could have been created in a variety of ways,
	 * for example by using createEvent().  If no {@link Event} object exists in this
	 * {@link Model} object, a new {@link Trigger} is <em>not</em> created and NULL is
	 * returned instead.
	 * <p>
	 * @return the {@link Trigger} object created
	 */
	public Trigger createTrigger() {
		return null; // TODO : implement
	}

	/**
	 * Creates a new {@link Delay} inside the last {@link Event} object created in
	 * this {@link Model}, and returns a pointer to it.
	 * <p>
	 * The mechanism by which the last {@link Event} object in this model was created
	 * is not significant.  It could have been created in a variety of ways,
	 * for example by using createEvent().  If no {@link Event} object exists in this
	 * {@link Model} object, a new {@link Delay} is <em>not</em> created and NULL is
	 * returned instead.
	 * <p>
	 * @return the {@link Delay} object created
	 */
	public Delay createDelay() {
		return null; // TODO : implement
	}


	/**
	 * Creates a new {@link FunctionDefinition} inside this {@link Model} and returns it.
	 * <p>
	 * @return the {@link FunctionDefinition} object created
	 * <p>
	 * @see #addFunctionDefinition(FunctionDefinition  fd)
	 */
	public FunctionDefinition createFunctionDefinition() {
		FunctionDefinition functionDef = new FunctionDefinition(level, version);
		addFunctionDefinition(functionDef);

		return functionDef;
	}


	/**
	 * Creates a new {@link InitialAssignment} inside this {@link Model} and returns it.
	 * <p>
	 * @return the {@link InitialAssignment} object created
	 * <p>
	 * @see #addInitialAssignment(InitialAssignment  ia)
	 */
	public InitialAssignment createInitialAssignment() {
		InitialAssignment initAssgmt = new InitialAssignment(level, version);
		addInitialAssignment(initAssgmt);

		return initAssgmt;
	}


	/**
	 * Creates a new {@link Reaction} inside this {@link Model} and returns it.
	 * <p>
	 * @return the {@link Reaction} object created
	 * <p>
	 * @see #addReaction(Reaction r)
	 */
	public Reaction createReaction() {
		Reaction reaction = new Reaction(level, version);
		addReaction(reaction);

		return reaction;
	}


	/**
	 * Returns the last reaction added in the model, corresponding to the last element of the list of reactions
	 * 
	 * @return the last reaction added in the model, corresponding to the last element of the list of reactions, or null is no reaction exist.
	 */
	private Reaction getLastReactionAdded() {
		int numReaction = getNumReactions();
		Reaction lastReaction = null;

		if (numReaction == 0) {
			return null;
		} else {
			lastReaction = getReaction(numReaction - 1);
		}

		return lastReaction;
	}

	/**
	 * Creates a new {@link KineticLaw} inside the last {@link Reaction} object created in
	 * this {@link Model}, and returns a pointer to it.
	 * <p>
	 * The mechanism by which the last {@link Reaction} object was created and added
	 * to this {@link Model} is not significant.  It could have been created in a
	 * variety of ways, for example using createReaction().  If a {@link Reaction}
	 * does not exist for this model, or a {@link Reaction} exists but already has a
	 * {@link KineticLaw}, a new {@link KineticLaw} is <em>not</em> created and NULL is returned
	 * instead.
	 * <p>
	 * @return the {@link KineticLaw} object created
	 */
	public KineticLaw createKineticLaw() {

		Reaction lastReaction = getLastReactionAdded();

		if (lastReaction == null) {
			return null;
		} 

		KineticLaw kineticLaw = new KineticLaw(level, version);
		lastReaction.setKineticLaw(kineticLaw);

		return kineticLaw;
	}


	/**
	 * Creates a new local {@link Parameter} inside the {@link KineticLaw} object of the last
	 * {@link Reaction} created inside this {@link Model}, and returns a pointer to it.
	 * <p>
	 * The last {@link KineticLaw} object in this {@link Model} could have been created in a
	 * variety of ways.  For example, it could have been added using
	 * createKineticLaw(), or it could be the result of using
	 * Reaction.createKineticLaw() on the {@link Reaction} object created by a
	 * createReaction().  If a {@link Reaction} does not exist for this model, or the
	 * last {@link Reaction} does not contain a {@link KineticLaw} object, a new {@link Parameter} is
	 * <em>not</em> created and NULL is returned instead.
	 * <p>
	 * @return the {@link Parameter} object created
	 */
	public Parameter createKineticLawParameter() {

		Reaction lastReaction = null;
		KineticLaw lastKineticLaw = null;

		if (lastReaction == null) {
			return null;
		} else {
			lastKineticLaw = lastReaction.getKineticLaw();
			if (lastKineticLaw == null) {
				return null;
			}
		}

		Parameter parameter = new Parameter(); 
		// TODO : should we use localParameter ??? createKineticLawLocalParameter exist in libsbml-5. 
		// Difference exist between global parameter and local parameter in SBML level 3
		lastKineticLaw.addParameter(parameter);

		return parameter;
	}

	/**
	 * Creates a new {@link ModifierSpeciesReference} object for a modifier species
	 * inside the last {@link Reaction} object in this {@link Model}, and returns a pointer
	 * to it.
	 * <p>
	 * The mechanism by which the last {@link Reaction} object was created and added
	 * to this {@link Model} is not significant.  It could have been created in a
	 * variety of ways, for example using createReaction().  If a {@link Reaction}
	 * does not exist for this model, a new {@link ModifierSpeciesReference} is 
	 * <em>not</em> created and NULL is returned instead.
	 * <p>
	 * @return the {@link SpeciesReference} object created
	 */
	public ModifierSpeciesReference createModifier() {

		Reaction lastReaction = getLastReactionAdded();

		if (lastReaction == null) {
			return null;
		}

		ModifierSpeciesReference modifier = lastReaction.createModifier();

		return modifier;
	}


	/**
	 * Creates a new {@link Parameter} inside this {@link Model} and returns it.
	 * <p>
	 * @return the {@link Parameter} object created
	 * <p>
	 * @see #addParameter(Parameter p)
	 */
	public Parameter createParameter() {

		Parameter parameter = new Parameter(level, version);
		addParameter(parameter);

		return parameter;
	}


	/**
	 * Creates a new {@link SpeciesReference} object for a product inside the last
	 * {@link Reaction} object in this {@link Model}, and returns a pointer to it.
	 * <p>
	 * The mechanism by which the last {@link Reaction} object was created and added
	 * to this {@link Model} is not significant.  It could have been created in a
	 * variety of ways, for example using createReaction().  If a {@link Reaction}
	 * does not exist for this model, a new {@link SpeciesReference} is <em>not</em>
	 * created and NULL is returned instead.
	 * <p>
	 * @return the {@link SpeciesReference} object created
	 */
	public SpeciesReference createProduct() {
		Reaction lastReaction = getLastReactionAdded();

		if (lastReaction == null) {
			return null;
		} 

		SpeciesReference product = lastReaction.createProduct();

		return product;
	}


	/**
	 * Creates a new {@link SpeciesReference} object for a reactant inside the last
	 * {@link Reaction} object in this {@link Model}, and returns a pointer to it.
	 * <p>
	 * The mechanism by which the last {@link Reaction} object was created and added
	 * to this {@link Model} is not significant.  It could have been created in a
	 * variety of ways, for example using createReaction().  If a {@link Reaction}
	 * does not exist for this model, a new {@link SpeciesReference} is <em>not</em>
	 * created and NULL is returned instead.
	 * <p>
	 * @return the {@link SpeciesReference} object created
	 */
	public SpeciesReference createReactant() {
		Reaction lastReaction = getLastReactionAdded();

		if (lastReaction == null) {
			return null;
		} 

		SpeciesReference reactant = lastReaction.createReactant();

		return reactant;
	}


	/**
	 * Creates a new {@link Species} inside this {@link Model} and returns it.
	 * <p>
	 * @return the {@link Species} object created
	 * <p>
	 * @see #addSpecies(Species s)
	 */
	public Species createSpecies() {

		Species species = new Species(level, version);
		addSpecies(species);

		return species;
	}


	/**
	 * Creates a new {@link SpeciesType} inside this {@link Model} and returns it.
	 * <p>
	 * @return the {@link SpeciesType} object created
	 * <p>
	 * @see #addSpeciesType(SpeciesType  st)
	 */
	@SuppressWarnings("deprecation")
	public SpeciesType createSpeciesType() {

		SpeciesType speciesType = new SpeciesType(level, version);
		addSpeciesType(speciesType);

		return speciesType;
	}


	/**
	 * Creates a new {@link UnitDefinition} inside this {@link Model} and returns it.
	 * <p>
	 * @return the {@link UnitDefinition} object created
	 * <p>
	 * @see #addUnitDefinition(UnitDefinition  ud)
	 */
	public UnitDefinition createUnitDefinition() {

		UnitDefinition unitDefinition = new UnitDefinition(level, version);
		addUnitDefinition(unitDefinition);

		return unitDefinition;
	}


	/**
	 * Creates a new {@link Unit} object within the last {@link UnitDefinition} object
	 * created in this model and returns a pointer to it.
	 * <p>
	 * The mechanism by which the {@link UnitDefinition} was created is not
	 * significant.  If a {@link UnitDefinition} object does not exist in this model,
	 * a new {@link Unit} is <em>not</em> created and NULL is returned instead.
	 * <p>
	 * @return the {@link Unit} object created
	 * <p>
	 * @see #addUnitDefinition(UnitDefinition  ud)
	 */
	public Unit createUnit() {

		int numUnitDef = getNumUnitDefinitions();
		UnitDefinition lastUnitDef = null;

		if (numUnitDef == 0) {
			return null;
		} else {
			lastUnitDef = getUnitDefinition(numUnitDef - 1);
		}

		Unit unit = lastUnitDef.createUnit();

		return unit;
	}


	public int getNumSpeciesWithBoundaryCondition() {
		// TODO Auto-generated method stub
		return 0;
	}


	public Compartment removeCompartment(int i) {
		// TODO Auto-generated method stub
		return null;
	}


	public Compartment removeCompartment(String string) {
		// TODO Auto-generated method stub
		return null;
	}


	public CompartmentType removeCompartmentType(int i) {
		// TODO Auto-generated method stub
		return null;
	}


	public CompartmentType removeCompartmentType(String string) {
		// TODO Auto-generated method stub
		return null;
	}


	public Constraint removeConstraint(int i) {
		// TODO Auto-generated method stub
		return null;
	}


	public Event removeEvent(int i) {
		// TODO Auto-generated method stub
		return null;
	}


	public Event removeEvent(String string) {
		// TODO Auto-generated method stub
		return null;
	}


	public FunctionDefinition removeFunctionDefinition(int i) {
		// TODO Auto-generated method stub
		return null;
	}


	public FunctionDefinition removeFunctionDefinition(String string) {
		// TODO Auto-generated method stub
		return null;
	}


	public InitialAssignment removeInitialAssignment(int i) {
		// TODO Auto-generated method stub
		return null;
	}


	public InitialAssignment removeInitialAssignment(String string) {
		// TODO Auto-generated method stub
		return null;
	}


	public Parameter removeParameter(int i) {
		// TODO Auto-generated method stub
		return null;
	}


	public Parameter removeParameter(String string) {
		// TODO Auto-generated method stub
		return null;
	}


	public Reaction removeReaction(int i) {
		// TODO Auto-generated method stub
		return null;
	}


	public Reaction removeReaction(String string) {
		// TODO Auto-generated method stub
		return null;
	}


	public Rule removeRule(int i) {
		// TODO Auto-generated method stub
		return null;
	}


	public Rule removeRule(String string) {
		// TODO Auto-generated method stub
		return null;
	}


	public Species removeSpecies(int i) {
		// TODO Auto-generated method stub
		return null;
	}


	public Species removeSpecies(String string) {
		// TODO Auto-generated method stub
		return null;
	}


	public SpeciesType removeSpeciesType(int i) {
		// TODO Auto-generated method stub
		return null;
	}


	public SpeciesType removeSpeciesType(String string) {
		// TODO Auto-generated method stub
		return null;
	}


	public UnitDefinition removeUnitDefinition(int i) {
		// TODO Auto-generated method stub
		return null;
	}


	public UnitDefinition removeUnitDefinition(String string) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
