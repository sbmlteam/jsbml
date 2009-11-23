/*
 * $Id: Model.java 38 2009-11-05 15:50:38Z niko-rodrigue $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/src/org/sbml/jsbml/Model.java $
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

import org.sbml.jsbml.xml.CurrentListOfSBMLElements;

/**
 * 
 * @author Andreas Dr&auml;ger <a
 *         href="mailto:andreas.draeger@uni-tuebingen.de">
 *         andreas.draeger@uni-tuebingen.de</a>
 * 
 */
public class Model extends AbstractNamedSBase {
	
	private Unit substanceUnits;
	
	private Unit timeUnits;
	
	private Unit volumeUnits;
	
	private Unit areaUnits;
	
	private Unit lengthUnits;
	
	private Unit extentUnits;
	
	private Parameter conversionFactor;
	
	private String substanceUnitsID;
	
	private String timeUnitsID;
	
	private String volumeUnitsID;
	
	private String areaUnitsID;
	
	private String lengthUnitsID;
	
	private String extentUnitsID;
	
	private String conversionFactorID;

	private ListOf<Compartment> listOfCompartments;

	private ListOf<CompartmentType> listOfCompartmentTypes;

	private ListOf<Constraint> listOfConstraints;

	private ListOf<Event> listOfEvents;

	private ListOf<FunctionDefinition> listOfFunctionDefinitions;

	private ListOf<InitialAssignment> listOfInitialAssignments;

	private ListOf<Parameter> listOfParameters;

	private ListOf<Reaction> listOfReactions;

	private ListOf<Rule> listOfRules;

	private ListOf<Species> listOfSpecies;

	private ListOf<SpeciesType> listOfSpeciesTypes;

	private ListOf<UnitDefinition> listOfUnitDefinitions;

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
		substanceUnits = null;
		timeUnits = null;
		volumeUnits = null;
		areaUnits = null;
		lengthUnits = null;
		extentUnits = null;
		conversionFactor = null;
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
	 * @param model
	 */
	@SuppressWarnings("unchecked")
	public Model(Model model) {
		super(model);
		listOfFunctionDefinitions = (ListOf<FunctionDefinition>) model.getListOfFunctionDefinitions().clone();
		setThisAsParentSBMLObject(listOfFunctionDefinitions);
		listOfUnitDefinitions = (ListOf<UnitDefinition>) model.getListOfUnitDefinitions().clone();
		setThisAsParentSBMLObject(listOfUnitDefinitions);
		listOfCompartmentTypes = (ListOf<CompartmentType>) model.getListOfCompartmentTypes().clone();
		setThisAsParentSBMLObject(listOfCompartmentTypes);
		listOfSpeciesTypes = (ListOf<SpeciesType>) model.getListOfSpeciesTypes().clone();
		setThisAsParentSBMLObject(listOfSpeciesTypes);
		listOfCompartments = (ListOf<Compartment>) model.getListOfCompartments().clone();
		setThisAsParentSBMLObject(listOfCompartments);
		listOfSpecies = (ListOf<Species>) model.getListOfSpecies().clone();
		setThisAsParentSBMLObject(listOfSpecies);
		listOfParameters = (ListOf<Parameter>) model.getListOfParameters().clone();
		setThisAsParentSBMLObject(listOfParameters);
		listOfInitialAssignments = (ListOf<InitialAssignment>) model.getListOfInitialAssignments().clone();
		setThisAsParentSBMLObject(listOfInitialAssignments);
		listOfRules = (ListOf<Rule>) model.getListOfRules().clone();
		setThisAsParentSBMLObject(listOfRules);
		listOfConstraints = (ListOf<Constraint>) model.getListOfConstraints().clone();
		setThisAsParentSBMLObject(listOfConstraints);
		listOfReactions = (ListOf<Reaction>) model.getListOfReactions().clone();
		setThisAsParentSBMLObject(listOfReactions);
		listOfEvents = (ListOf<Event>) model.getListOfEvents().clone();
		setThisAsParentSBMLObject(listOfEvents);
	}

	/**
	 * 
	 * @param id
	 */
	public Model(String id, int level, int version) {
		super(id, level, version);
		listOfFunctionDefinitions = new ListOf<FunctionDefinition>(level, version);
		setThisAsParentSBMLObject(listOfFunctionDefinitions);
		listOfUnitDefinitions = new ListOf<UnitDefinition>(level, version);
		setThisAsParentSBMLObject(listOfUnitDefinitions);
		listOfCompartmentTypes = new ListOf<CompartmentType>(level, version);
		setThisAsParentSBMLObject(listOfCompartmentTypes);
		listOfSpeciesTypes = new ListOf<SpeciesType>(level, version);
		setThisAsParentSBMLObject(listOfSpeciesTypes);
		listOfCompartments = new ListOf<Compartment>(level, version);
		setThisAsParentSBMLObject(listOfCompartments);
		listOfSpecies = new ListOf<Species>(level, version);
		setThisAsParentSBMLObject(listOfSpecies);
		listOfParameters = new ListOf<Parameter>(level, version);
		setThisAsParentSBMLObject(listOfParameters);
		listOfInitialAssignments = new ListOf<InitialAssignment>(level, version);
		setThisAsParentSBMLObject(listOfInitialAssignments);
		listOfRules = new ListOf<Rule>(level, version);
		setThisAsParentSBMLObject(listOfRules);
		listOfConstraints = new ListOf<Constraint>(level, version);
		setThisAsParentSBMLObject(listOfRules);
		listOfReactions = new ListOf<Reaction>(level, version);
		setThisAsParentSBMLObject(listOfReactions);
		listOfEvents = new ListOf<Event>(level, version);
		setThisAsParentSBMLObject(listOfEvents);
	}
	
	public Unit getTimeUnits() {
		return timeUnits;
	}

	public void setTimeUnits(Unit timeUnits) {
		this.timeUnits = timeUnits;
	}

	public Unit getVolumeUnits() {
		return volumeUnits;
	}

	public void setVolumeUnits(Unit volumeUnits) {
		this.volumeUnits = volumeUnits;
	}

	public Unit getAreaUnits() {
		return areaUnits;
	}

	public void setAreaUnits(Unit areaUnits) {
		this.areaUnits = areaUnits;
	}

	public Unit getLengthUnits() {
		return lengthUnits;
	}

	public void setLengthUnits(Unit lengthUnits) {
		this.lengthUnits = lengthUnits;
	}

	public Unit getExtentUnits() {
		return extentUnits;
	}

	public void setExtentUnits(Unit extentUnits) {
		this.extentUnits = extentUnits;
	}

	public Parameter getConversionFactor() {
		return conversionFactor;
	}

	public void setConversionFactor(Parameter conversionFactor) {
		this.conversionFactor = conversionFactor;
	}

	public String getTimeUnitsID() {
		return timeUnitsID;
	}

	public void setTimeUnitsID(String timeUnitsID) {
		this.timeUnitsID = timeUnitsID;
	}

	public String getVolumeUnitsID() {
		return volumeUnitsID;
	}

	public void setVolumeUnitsID(String volumeUnitsID) {
		this.volumeUnitsID = volumeUnitsID;
	}

	public String getAreaUnitsID() {
		return areaUnitsID;
	}

	public void setAreaUnitsID(String areaUnitsID) {
		this.areaUnitsID = areaUnitsID;
	}

	public String getLengthUnitsID() {
		return lengthUnitsID;
	}

	public void setLengthUnitsID(String lengthUnitsID) {
		this.lengthUnitsID = lengthUnitsID;
	}

	public String getExtentUnitsID() {
		return extentUnitsID;
	}

	public void setExtentUnitsID(String extentUnitsID) {
		this.extentUnitsID = extentUnitsID;
	}

	public String getConversionFactorID() {
		return conversionFactorID;
	}

	public void setConversionFactorID(String conversionFactorID) {
		this.conversionFactorID = conversionFactorID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.SBase#addChangeListener(org.sbml.squeezer.io.SBaseChangedListener
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
	 * 
	 * @param compartment
	 */
	public void addCompartment(Compartment compartment) {
		if (!isSetListOfCompartments()){
			this.listOfCompartments = new ListOf<Compartment>();
		}
		if (!listOfCompartments.contains(compartment)) {
			compartment.parentSBMLObject = this;
			listOfCompartments.add(compartment);
		}
	}

	/**
	 * 
	 * @param compartmentType
	 */
	public void addCompartmentType(CompartmentType compartmentType) {
		if (!isSetListOfCompartmentTypes()){
			this.listOfCompartmentTypes = new ListOf<CompartmentType>();
		}
		if (!listOfCompartmentTypes.contains(compartmentType)) {
			compartmentType.parentSBMLObject = this;
			listOfCompartmentTypes.add(compartmentType);
		}
	}

	/**
	 * 
	 * @param constraint
	 */
	public void addConstraint(Constraint constraint) {
		if (!isSetListOfConstraints()){
			this.listOfConstraints = new ListOf<Constraint>();
		}
		if (!listOfConstraints.contains(constraint)) {
			constraint.parentSBMLObject = this;
			listOfConstraints.add(constraint);
		}
	}

	/**
	 * 
	 * @param event
	 */
	public void addEvent(Event event) {
		if (!isSetListOfEvents()){
			this.listOfEvents = new ListOf<Event>();
		}
		if (!listOfEvents.contains(event)) {
			event.parentSBMLObject = this;
			listOfEvents.add(event);
		}
	}

	/**
	 * 
	 * @param
	 */
	public void addFunctionDefinition(FunctionDefinition functionDefinition) {
		if (!isSetListOfFunctionDefinitions()){
			this.listOfFunctionDefinitions = new ListOf<FunctionDefinition>();
		}
		if (!listOfFunctionDefinitions.contains(functionDefinition)) {
			functionDefinition.parentSBMLObject = this;
			listOfFunctionDefinitions.add(functionDefinition);
		}
	}

	/**
	 * 
	 * @param initialAssignment
	 */
	public void addInitialAssignment(InitialAssignment initialAssignment) {
		if (!isSetListOfInitialAssignemnts()){
			this.listOfInitialAssignments = new ListOf<InitialAssignment>();
		}
		if (!listOfInitialAssignments.contains(initialAssignment)) {
			initialAssignment.parentSBMLObject = this;
			listOfInitialAssignments.add(initialAssignment);
		}
	}

	/**
	 * 
	 * @param parameter
	 */
	public void addParameter(Parameter parameter) {
		if (!isSetListOfParameters()){
			this.listOfParameters = new ListOf<Parameter>();
		}
		if (!listOfParameters.contains(parameter)) {
			parameter.parentSBMLObject = this;
			listOfParameters.add(parameter);
		}
	}

	/**
	 * adds a reaction to the model
	 * 
	 * @param reaction
	 */
	public void addReaction(Reaction reaction) {
		if (!isSetListOfReactions()){
			this.listOfReactions = new ListOf<Reaction>();
		}
		if (!listOfReactions.contains(reaction)) {
			reaction.parentSBMLObject = this;
			listOfReactions.add(reaction);
		}
	}

	/**
	 * 
	 * @param rule
	 */
	public void addRule(Rule rule) {
		if (!isSetListOfRules()){
			this.listOfRules = new ListOf<Rule>();
		}
		if (!listOfRules.contains(rule)) {
			rule.parentSBMLObject = this;
			listOfRules.add(rule);
		}
	}

	/**
	 * adds a species to the model
	 * 
	 * @param spec
	 */
	public void addSpecies(Species spec) {
		if (!isSetListOfSpecies()){
			this.listOfSpecies = new ListOf<Species>();
		}
		if (!listOfSpecies.contains(spec)) {
			spec.parentSBMLObject = this;
			listOfSpecies.add(spec);
		}
	}

	/**
	 * 
	 * @param speciesType
	 */
	public void addSpeciesType(SpeciesType speciesType) {
		if (!isSetListOfSpeciesTypes()){
			this.listOfSpeciesTypes = new ListOf<SpeciesType>();
		}
		if (!listOfSpeciesTypes.contains(speciesType)) {
			speciesType.parentSBMLObject = this;
			listOfSpeciesTypes.add(speciesType);
		}
	}

	/**
	 * 
	 * @param unitDefinition
	 */
	public void addUnitDefinition(UnitDefinition unitDefinition) {
		if (!isSetListOfUnitDefinitions()){
			this.listOfUnitDefinitions = new ListOf<UnitDefinition>();
		}
		if (!listOfUnitDefinitions.contains(unitDefinition)) {
			unitDefinition.parentSBMLObject = this;
			listOfUnitDefinitions.add(unitDefinition);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBase#clone()
	 */
	// @Override
	public Model clone() {
		return new Model(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.NamedSBase#equals(java.lang.Object)
	 */
	// @Override
	public boolean equals(Object o) {
		boolean equal = super.equals(o);
		if (o instanceof Model) {
			Model m = (Model) o;
			equal &= m.getListOfFunctionDefinitions().equals(
					listOfFunctionDefinitions);
			equal &= m.getListOfUnitDefinitions().equals(listOfUnitDefinitions);
			equal &= m.getListOfCompartmentTypes().equals(
					listOfCompartmentTypes);
			equal &= m.getListOfSpeciesTypes().equals(listOfSpeciesTypes);
			equal &= m.getListOfCompartments().equals(listOfCompartments);
			equal &= m.getListOfSpecies().equals(listOfSpecies);
			equal &= m.getListOfParameters().equals(listOfParameters);
			equal &= m.getListOfInitialAssignments().equals(
					listOfInitialAssignments);
			equal &= m.getListOfRules().equals(listOfRules);
			equal &= m.getListOfConstraints().equals(listOfConstraints);
			equal &= m.getListOfReactions().equals(listOfReactions);
			equal &= m.getListOfEvents().equals(listOfEvents);
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
		if (namedSBase == null)
			namedSBase = getSpecies(id);
		if (namedSBase == null)
			namedSBase = getParameter(id);
		if (namedSBase == null)
			namedSBase = getReaction(id);
		if (namedSBase == null)
			namedSBase = getFunctionDefinition(id);
		if (namedSBase == null)
			namedSBase = getUnitDefinition(id);
		// check all local parameters
		if (namedSBase == null)
			for (Reaction reaction : getListOfReactions()) {
				if (reaction.isSetKineticLaw())
					namedSBase = reaction.getKineticLaw().getParameter(id);
				if (namedSBase != null)
					break;
			}
		return namedSBase;
	}

	/**
	 * 
	 * @param symbol
	 * @return
	 */
	public Symbol findSymbol(String symbol) {
		Symbol nsb = getSpecies(symbol);
		if (nsb == null)
			nsb = getParameter(symbol);
		if (nsb == null)
			nsb = getCompartment(symbol);
		return nsb;
	}

	/**
	 * 
	 * @param n
	 * @return
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
	 * @return
	 */
	public Compartment getCompartment(String id) {
		if (isSetListOfCompartments()){
			for (Compartment comp : listOfCompartments) {
				if (comp.getId().equals(id)){
					return comp;
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public CompartmentType getCompartmentType(String id) {
		if (isSetListOfCompartmentTypes()){
			for (CompartmentType ct : listOfCompartmentTypes){
				if (ct.getId().equals(id)){
					return ct;
				}
			}
		}
		return null;
	}
	
	public CompartmentType getCompartmentType(int n) {
		if (isSetListOfCompartmentTypes()){
			return listOfCompartmentTypes.get(n);
		}
		return null;
	}
	
	public SpeciesType getSpeciesType(int n) {
		if (isSetListOfSpeciesTypes()){
			return listOfSpeciesTypes.get(n);
		}
		return null;
	}
	
	public boolean isSetListOfConstraints(){
		return this.listOfConstraints != null;
	}

	/**
	 * Get the nth Constraint object in this Model.
	 * 
	 * @param n
	 * @return the nth Constraint of this Model.
	 */
	public Constraint getConstraint(int n) {
		if (isSetListOfConstraints()){
			return listOfConstraints.get(n);
		}
		return null;
	}

	/**
	 * 
	 * @param i
	 * @return
	 */
	public Event getEvent(int i) {
		if (isSetListOfEvents()){
			return listOfEvents.get(i);
		}
		return null;
	}
	
	/**
	 * 
	 * @param i
	 * @return
	 */
	public UnitDefinition getUnitDefinition(int i) {
		if (isSetListOfUnitDefinitions()){
			return listOfUnitDefinitions.get(i);
		}
		return null;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Event getEvent(String id) {
		if (isSetListOfEvents()){
			for (Event ev : listOfEvents){
				if (ev.getId().equals(id)){
					return ev;
				}
			}
		}
		return null;
	}
	
	public boolean isSetListOfFunctionDefinitions(){
		return this.listOfFunctionDefinitions != null;
	}

	/**
	 * 
	 * @param n
	 * @return
	 */
	public FunctionDefinition getFunctionDefinition(int n) {
		if (isSetListOfFunctionDefinitions()){
			return listOfFunctionDefinitions.get(n);
		}
		return null;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public FunctionDefinition getFunctionDefinition(String id) {
		if (isSetListOfFunctionDefinitions()){
			for (FunctionDefinition f : listOfFunctionDefinitions) {
				if (f.getId().equals(id)){
					return f;
				}
			}
		}
		return null;
	}

	/**
	 * Get the nth InitialAssignment object in this Model.
	 * 
	 * @param n
	 * @return the nth InitialAssignment of this Model.
	 */
	public InitialAssignment getInitialAssignment(int n) {
		if (isSetListOfInitialAssignemnts()){
			return listOfInitialAssignments.get(n);
		}
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public ListOf<Compartment> getListOfCompartments() {
		return listOfCompartments;
	}

	/**
	 * 
	 * @return
	 */
	public ListOf<CompartmentType> getListOfCompartmentTypes() {
		return listOfCompartmentTypes;
	}

	/**
	 * @return the listOfConstraints
	 */
	public ListOf<Constraint> getListOfConstraints() {
		return listOfConstraints;
	}

	/**
	 * 
	 * @return
	 */
	public ListOf<Event> getListOfEvents() {
		return listOfEvents;
	}

	/**
	 * 
	 * @return
	 */
	public ListOf<FunctionDefinition> getListOfFunctionDefinitions() {
		return listOfFunctionDefinitions;
	}

	/**
	 * @return the listOfInitialAssignments
	 */
	public ListOf<InitialAssignment> getListOfInitialAssignments() {
		return listOfInitialAssignments;
	}

	/**
	 * 
	 * @return
	 */
	public ListOf<Parameter> getListOfParameters() {
		return listOfParameters;
	}

	/**
	 * 
	 * @return
	 */
	public ListOf<Reaction> getListOfReactions() {
		return listOfReactions;
	}

	/**
	 * 
	 * @return
	 */
	public ListOf<Rule> getListOfRules() {
		return listOfRules;
	}

	/**
	 * 
	 * @return
	 */
	public ListOf<Species> getListOfSpecies() {
		return listOfSpecies;
	}

	/**
	 * 
	 * @return
	 */
	public ListOf<SpeciesType> getListOfSpeciesTypes() {
		return listOfSpeciesTypes;
	}

	/**
	 * 
	 * @return
	 */
	public ListOf<UnitDefinition> getListOfUnitDefinitions() {
		return listOfUnitDefinitions;
	}

	/**
	 * 
	 * @return
	 */
	public int getNumCompartments() {
		if (isSetListOfCompartments()){
			return listOfCompartments.size();
		}
		return 0;
	}

	/**
	 * 
	 * @return
	 */
	public int getNumCompartmentTypes() {
		if (isSetListOfCompartmentTypes()){
			return listOfCompartmentTypes.size();
		}
		return 0;
	}

	/**
	 * 
	 * @return
	 */
	public int getNumConstraints() {
		if (isSetListOfConstraints()){
			return listOfConstraints.size();
		}
		return 0;
	}

	/**
	 * 
	 * @return
	 */
	public int getNumEvents() {
		if (isSetListOfEvents()){
			return listOfEvents.size();
		}
		return 0;
	}

	/**
	 * 
	 * @return
	 */
	public int getNumFunctionDefinitions() {
		if (isSetListOfFunctionDefinitions()){
			return listOfFunctionDefinitions.size();
		}
		return 0;
	}

	/**
	 * 
	 * @return
	 */
	public int getNumInitialAssignments() {
		if (isSetListOfInitialAssignemnts()){
			return listOfInitialAssignments.size();
		}
		return 0;
	}

	/**
	 * Returns the number of parameters that are containt within kineticLaws in
	 * the reactions of this model.
	 * 
	 * @return
	 */
	public int getNumLocalParameters() {
		int count = 0;
		for (Reaction reaction : listOfReactions){
			if (reaction.isSetKineticLaw()){
				count += reaction.getKineticLaw().getNumParameters();
			}
		}
		return count;
	}

	/**
	 * 
	 * @return
	 */
	public int getNumParameters() {
		if (isSetListOfParameters()){
			return listOfParameters.size();
		}
		return 0;
	}

	/**
	 * 
	 * @return
	 */
	public int getNumReactions() {
		if (isSetListOfReactions()){
			return listOfReactions.size();
		}
		return 0;
	}

	/**
	 * 
	 * @return
	 */
	public int getNumRules() {
		if (isSetListOfRules()){
			return listOfRules.size();
		}
		return 0;
	}

	/**
	 * 
	 * @return
	 */
	public int getNumSpecies() {
		if (isSetListOfSpecies()){
			return listOfSpecies.size();
		}
		return 0;
	}

	/**
	 * 
	 * @return
	 */
	public int getNumSpeciesTypes() {
		if (isSetListOfSpeciesTypes()){
			return listOfSpeciesTypes.size();
		}
		return 0;
	}

	/**
	 * 
	 * @return
	 */
	public int getNumUnitDefinitions() {
		if (isSetListOfUnitDefinitions()){
			return listOfUnitDefinitions.size();
		}
		return 0;
	}

	/**
	 * 
	 * @param n
	 * @return
	 */
	public Parameter getParameter(int n) {
		if (isSetListOfParameters()){
			return listOfParameters.get(n);
		}
		return null;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Parameter getParameter(String id) {
		if (isSetListOfParameters()){
			for (Parameter parameter : listOfParameters) {
				if (parameter.getId().equals(id))
					return parameter;
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jlibsbml.AbstractSBase#getParentSBMLObject()
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
		if (isSetListOfReactions()){
			return listOfReactions.get(n);
		}
		return null;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Reaction getReaction(String id) {
		if (isSetListOfReactions()){
			for (Reaction reaction : listOfReactions) {
				if (reaction.getId().equals(id)){
					return reaction;
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @param n
	 * @return
	 */
	public Rule getRule(int n) {
		if (isSetListOfRules()){
			return listOfRules.get(n);
		}
		return null;
	}

	/**
	 * Get the n-th Species object in this Model.
	 * 
	 * @param n
	 *            the nth Species of this Model.
	 * @return
	 */
	public Species getSpecies(int n) {
		if (isSetListOfSpecies()){
			return listOfSpecies.get(n);
		}
		return null;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Species getSpecies(String id) {
		if (isSetListOfSpecies()){
			for (Species species : listOfSpecies) {
				if (species.getId().equals(id)){
					return species;
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public SpeciesType getSpeciesType(String id) {
		if (isSetListOfSpeciesTypes()){
			for (SpeciesType st : listOfSpeciesTypes){
				if (st.getId().equals(id)){
					return st;
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public UnitDefinition getUnitDefinition(String id) {
		if (isSetListOfUnitDefinitions()){
			for (UnitDefinition unitdef : listOfUnitDefinitions){
				if (unitdef.getId().equals(id)){
					return unitdef;
				}
			}
		}
		
		UnitDefinition ud = null;
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
		return ud;
	}

	/**
	 * 
	 * @param parameter
	 */
	public void removeParameter(Parameter parameter) {
		if (isSetListOfParameters()){
			listOfParameters.remove(parameter);
			parameter.sbaseRemoved();
		}
	/*for (UnitDefinition unitdef : listOfUnitDefinitions){
		if (unitdef.getId().equals(id)){
			return unitdef;*/
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
		if (success)
			spec.sbaseRemoved();
		return success;
	}

	/**
	 * 
	 * @param unitDefininition
	 * @return success
	 */
	public boolean removeUnitDefinition(UnitDefinition unitDefininition) {
		boolean success = false;
		if (isSetListOfUnitDefinitions()){
			success = listOfUnitDefinitions.remove(unitDefininition);

		}

		if (success)
			unitDefininition.sbaseRemoved();
		return success;
	}

	/**
	 * 
	 * @param listOfCompartments
	 */
	public void setListOfCompartments(ListOf<Compartment> listOfCompartments) {
		this.listOfCompartments = listOfCompartments;
		setThisAsParentSBMLObject(listOfCompartments);
		this.listOfCompartments.setCurrentList(CurrentListOfSBMLElements.listOfCompartments);

		stateChanged();
	}

	/**
	 * @param listOfCompartmentTypes
	 *            the listOfCompartmentTypes to set
	 */
	public void setListOfCompartmentTypes(
			ListOf<CompartmentType> listOfCompartmentTypes) {
		this.listOfCompartmentTypes = listOfCompartmentTypes;
		setThisAsParentSBMLObject(listOfCompartmentTypes);
		this.listOfCompartmentTypes.setCurrentList(CurrentListOfSBMLElements.listOfCompartmentTypes);
		stateChanged();
	}

	/**
	 * @param listOfConstraints
	 *            the listOfConstraints to set
	 */
	public void setListOfConstraints(ListOf<Constraint> listOfConstraints) {
		this.listOfConstraints = listOfConstraints;
		setThisAsParentSBMLObject(listOfConstraints);
		this.listOfConstraints.setCurrentList(CurrentListOfSBMLElements.listOfConstraints);

	}

	/**
	 * 
	 * @param listOfEvents
	 */
	public void setListOfEvents(ListOf<Event> listOfEvents) {
		this.listOfEvents = listOfEvents;
		setThisAsParentSBMLObject(listOfEvents);
		this.listOfEvents.setCurrentList(CurrentListOfSBMLElements.listOfEvents);
		stateChanged();
	}

	/**
	 * @param listOfFunctionDefinitions
	 *            the listOfFunctionDefinitions to set
	 */
	public void setListOfFunctionDefinitions(
			ListOf<FunctionDefinition> listOfFunctionDefinitions) {
		this.listOfFunctionDefinitions = listOfFunctionDefinitions;
		setThisAsParentSBMLObject(listOfFunctionDefinitions);
		this.listOfFunctionDefinitions.setCurrentList(CurrentListOfSBMLElements.listOfFunctionDefinitions);

		stateChanged();
	}

	/**
	 * @param listOfInitialAssignments
	 *            the listOfInitialAssignments to set
	 */
	public void setListOfInitialAssignments(
			ListOf<InitialAssignment> listOfInitialAssignments) {
		this.listOfInitialAssignments = listOfInitialAssignments;
		setThisAsParentSBMLObject(listOfInitialAssignments);
		this.listOfInitialAssignments.setCurrentList(CurrentListOfSBMLElements.listOfInitialAssignments);

	}

	/**
	 * 
	 * @param listOfParameters
	 */
	public void setListOfParameters(ListOf<Parameter> listOfParameters) {
		this.listOfParameters = listOfParameters;
		setThisAsParentSBMLObject(listOfParameters);
		this.listOfParameters.setCurrentList(CurrentListOfSBMLElements.listOfParameters);

		stateChanged();
	}

	/**
	 * 
	 * @param listOfReactions
	 */
	public void setListOfReactions(ListOf<Reaction> listOfReactions) {
		this.listOfReactions = listOfReactions;
		setThisAsParentSBMLObject(listOfReactions);
		this.listOfReactions.setCurrentList(CurrentListOfSBMLElements.listOfReactions);

		stateChanged();
	}

	/**
	 * 
	 * @return
	 */
	public void setListOfRules(ListOf<Rule> listOfRules) {
		this.listOfRules = listOfRules;
		setThisAsParentSBMLObject(listOfRules);
		this.listOfRules.setCurrentList(CurrentListOfSBMLElements.listOfRules);

		stateChanged();
	}

	/**
	 * 
	 * @param listOfSpecies
	 */
	public void setListOfSpecies(ListOf<Species> listOfSpecies) {
		this.listOfSpecies = listOfSpecies;
		setThisAsParentSBMLObject(listOfSpecies);
		this.listOfSpecies.setCurrentList(CurrentListOfSBMLElements.listOfSpecies);

		stateChanged();
	}

	/**
	 * @param listOfSpeciesTypes
	 *            the listOfSpeciesTypes to set
	 */
	public void setListOfSpeciesTypes(ListOf<SpeciesType> listOfSpeciesTypes) {
		this.listOfSpeciesTypes = listOfSpeciesTypes;
		setThisAsParentSBMLObject(listOfSpeciesTypes);
		this.listOfSpeciesTypes.setCurrentList(CurrentListOfSBMLElements.listOfSpeciesTypes);

		stateChanged();
	}

	/**
	 * 
	 * @param listOfUnitDefinitions
	 */
	public void setListOfUnitDefinitions(
			ListOf<UnitDefinition> listOfUnitDefinitions) {
		this.listOfUnitDefinitions = listOfUnitDefinitions;
		setThisAsParentSBMLObject(listOfUnitDefinitions);
		this.listOfUnitDefinitions.setCurrentList(CurrentListOfSBMLElements.listOfUnitDefinitions);
		stateChanged();
	}
	
	public Unit getSubstanceUnits() {
		return substanceUnits;
	}

	public void setSubstanceUnits(Unit substanceUnits) {
		this.substanceUnits = substanceUnits;
	}

	public String getSubstanceUnitsID() {
		return substanceUnitsID;
	}

	public void setSubstanceUnitsID(String substanceUnitsID) {
		this.substanceUnitsID = substanceUnitsID;
	}
	
	public boolean isSetListOfRules(){
		return this.listOfRules != null;
	}
	
	public boolean isSetListOfCompartments(){
		return this.listOfCompartments != null;
	}
	
	public boolean isSetListOfSpecies(){
		return this.listOfSpecies != null;
	}
	
	public boolean isSetListOfParameters(){
		return this.listOfParameters != null;
	}
	
	public boolean isSetListOfReactions(){
		return this.listOfReactions != null;
	}
	
	public boolean isSetListOfCompartmentTypes(){
		return this.listOfCompartmentTypes != null;
	}
	
	public boolean isSetListOfEvents(){
		return this.listOfEvents != null;
	}
	
	public boolean isSetListOfUnitDefinitions(){
		return this.listOfUnitDefinitions != null;
	}
	
	public boolean isSetListOfInitialAssignemnts(){
		return this.listOfInitialAssignments != null;
	}
	
	public boolean isSetListOfSpeciesTypes(){
		return this.listOfSpeciesTypes != null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Rule#isSpeciesConcentration()
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix, String value){
		boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
	
		if (!isAttributeRead){
			if (attributeName.equals("substanceUnits")){
				this.setSubstanceUnitsID(value);
			}
			else if (attributeName.equals("timeUnits")){
				this.setTimeUnitsID(value);
			}
			else if (attributeName.equals("volumeUnits")){
				this.setVolumeUnitsID(value);
			}
			else if (attributeName.equals("areaUnits")){
				this.setAreaUnitsID(value);
			}
			else if (attributeName.equals("lengthUnits")){
				this.setLengthUnitsID(value);
			}
			else if (attributeName.equals("extentUnits")){
				this.setExtentUnitsID(value);
			}
			else if (attributeName.equals("conversionFactor")){
				this.setConversionFactorID(value);
			}
		}
		return isAttributeRead;
	}
}
