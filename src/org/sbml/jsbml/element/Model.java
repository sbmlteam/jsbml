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

	private ListOf listOfCompartments;

	private ListOf listOfCompartmentTypes;

	private ListOf listOfConstraints;

	private ListOf listOfEvents;

	private ListOf listOfFunctionDefinitions;

	private ListOf listOfInitialAssignments;

	private ListOf listOfParameters;

	private ListOf listOfReactions;

	private ListOf listOfRules;

	private ListOf listOfSpecies;

	private ListOf listOfSpeciesTypes;

	private ListOf listOfUnitDefinitions;

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
	public Model(Model model) {
		super(model);
		listOfFunctionDefinitions = (ListOf) model.getListOfFunctionDefinitions()
				.clone();
		setThisAsParentSBMLObject(listOfFunctionDefinitions);
		listOfUnitDefinitions = (ListOf) model.getListOfUnitDefinitions();
		setThisAsParentSBMLObject(listOfUnitDefinitions);
		listOfCompartmentTypes = (ListOf) model.getListOfCompartmentTypes().clone();
		setThisAsParentSBMLObject(listOfCompartmentTypes);
		listOfSpeciesTypes = (ListOf) model.getListOfSpeciesTypes().clone();
		setThisAsParentSBMLObject(listOfSpeciesTypes);
		listOfCompartments = (ListOf) model.getListOfCompartments().clone();
		setThisAsParentSBMLObject(listOfCompartments);
		listOfSpecies = (ListOf) model.getListOfSpecies().clone();
		setThisAsParentSBMLObject(listOfSpecies);
		listOfParameters = (ListOf) model.getListOfParameters().clone();
		setThisAsParentSBMLObject(listOfParameters);
		listOfInitialAssignments = (ListOf) model.getListOfInitialAssignments().clone();
		setThisAsParentSBMLObject(listOfInitialAssignments);
		listOfRules = (ListOf) model.getListOfRules().clone();
		setThisAsParentSBMLObject(listOfRules);
		listOfConstraints = (ListOf) model.getListOfConstraints().clone();
		setThisAsParentSBMLObject(listOfConstraints);
		listOfReactions = (ListOf) model.getListOfReactions().clone();
		setThisAsParentSBMLObject(listOfReactions);
		listOfEvents = (ListOf) model.getListOfEvents().clone();
		setThisAsParentSBMLObject(listOfEvents);
	}

	/**
	 * 
	 * @param id
	 */
	public Model(String id, int level, int version) {
		super(id, level, version);
		listOfFunctionDefinitions = new ListOf(level,
				version);
		setThisAsParentSBMLObject(listOfFunctionDefinitions);
		listOfUnitDefinitions = new ListOf(level, version);
		setThisAsParentSBMLObject(listOfUnitDefinitions);
		listOfCompartmentTypes = new ListOf(level, version);
		setThisAsParentSBMLObject(listOfCompartmentTypes);
		listOfSpeciesTypes = new ListOf(level, version);
		setThisAsParentSBMLObject(listOfSpeciesTypes);
		listOfCompartments = new ListOf(level, version);
		setThisAsParentSBMLObject(listOfCompartments);
		listOfSpecies = new ListOf(level, version);
		setThisAsParentSBMLObject(listOfSpecies);
		listOfParameters = new ListOf(level, version);
		setThisAsParentSBMLObject(listOfParameters);
		listOfInitialAssignments = new ListOf(level, version);
		setThisAsParentSBMLObject(listOfInitialAssignments);
		listOfRules = new ListOf(level, version);
		setThisAsParentSBMLObject(listOfRules);
		listOfConstraints = new ListOf(level, version);
		setThisAsParentSBMLObject(listOfRules);
		listOfReactions = new ListOf(level, version);
		setThisAsParentSBMLObject(listOfReactions);
		listOfEvents = new ListOf(level, version);
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
		if (!listOfCompartments.getListOf().contains(compartment)) {
			compartment.parentSBMLObject = this;
			listOfCompartments.getListOf().add(compartment);
		}
	}

	/**
	 * 
	 * @param compartmentType
	 */
	public void addCompartmentType(CompartmentType compartmentType) {
		if (!listOfCompartmentTypes.getListOf().contains(compartmentType)) {
			compartmentType.parentSBMLObject = this;
			listOfCompartmentTypes.getListOf().add(compartmentType);
		}
	}

	/**
	 * 
	 * @param constraint
	 */
	public void addConstraint(Constraint constraint) {
		if (!listOfConstraints.getListOf().contains(constraint)) {
			constraint.parentSBMLObject = this;
			listOfConstraints.getListOf().add(constraint);
		}
	}

	/**
	 * 
	 * @param event
	 */
	public void addEvent(Event event) {
		if (!listOfEvents.getListOf().contains(event)) {
			event.parentSBMLObject = this;
			listOfEvents.getListOf().add(event);
		}
	}

	/**
	 * 
	 * @param
	 */
	public void addFunctionDefinition(FunctionDefinition functionDefinition) {
		if (!listOfFunctionDefinitions.getListOf().contains(functionDefinition)) {
			functionDefinition.parentSBMLObject = this;
			listOfFunctionDefinitions.getListOf().add(functionDefinition);
		}
	}

	/**
	 * 
	 * @param initialAssignment
	 */
	public void addInitialAssignment(InitialAssignment initialAssignment) {
		if (!listOfInitialAssignments.getListOf().contains(initialAssignment)) {
			initialAssignment.parentSBMLObject = this;
			listOfInitialAssignments.getListOf().add(initialAssignment);
		}
	}

	/**
	 * 
	 * @param parameter
	 */
	public void addParameter(Parameter parameter) {
		if (!listOfParameters.getListOf().contains(parameter)) {
			parameter.parentSBMLObject = this;
			listOfParameters.getListOf().add(parameter);
		}
	}

	/**
	 * adds a reaction to the model
	 * 
	 * @param reaction
	 */
	public void addReaction(Reaction reaction) {
		if (!listOfReactions.getListOf().contains(reaction)) {
			reaction.parentSBMLObject = this;
			listOfReactions.getListOf().add(reaction);
		}
	}

	/**
	 * 
	 * @param rule
	 */
	public void addRule(Rule rule) {
		if (!listOfRules.getListOf().contains(rule)) {
			rule.parentSBMLObject = this;
			listOfRules.getListOf().add(rule);
		}
	}

	/**
	 * adds a species to the model
	 * 
	 * @param spec
	 */
	public void addSpecies(Species spec) {
		if (!listOfSpecies.getListOf().contains(spec)) {
			spec.parentSBMLObject = this;
			listOfSpecies.getListOf().add(spec);
		}
	}

	/**
	 * 
	 * @param speciesType
	 */
	public void addSpeciesType(SpeciesType speciesType) {
		if (!listOfSpeciesTypes.getListOf().contains(speciesType)) {
			speciesType.parentSBMLObject = this;
			listOfSpeciesTypes.getListOf().add(speciesType);
		}
	}

	/**
	 * 
	 * @param unitDefinition
	 */
	public void addUnitDefinition(UnitDefinition unitDefinition) {
		if (!listOfUnitDefinitions.getListOf().contains(unitDefinition)) {
			unitDefinition.parentSBMLObject = this;
			listOfUnitDefinitions.getListOf().add(unitDefinition);
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
			for (SBase r : getListOfReactions().getListOf()) {
				Reaction reaction = (Reaction) r;
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
		return (Compartment) listOfCompartments.getListOf().get(n);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Compartment getCompartment(String id) {
		for (SBase c : listOfCompartments.getListOf()) {
			Compartment comp = (Compartment) c;
			if (comp.getId().equals(id)){
				return comp;
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
		for (SBase c : listOfCompartmentTypes.getListOf()){
			CompartmentType ct = (CompartmentType) c;
			if (ct.getId().equals(id)){
				return ct;
			}
		}
		return null;
	}
	
	public CompartmentType getCompartmentType(int n) {
		return (CompartmentType) listOfCompartmentTypes.getListOf().get(n);
	}
	
	public SpeciesType getSpeciesType(int n) {
		return (SpeciesType) listOfSpeciesTypes.getListOf().get(n);
	}

	/**
	 * Get the nth Constraint object in this Model.
	 * 
	 * @param n
	 * @return the nth Constraint of this Model.
	 */
	public Constraint getConstraint(int n) {
		return (Constraint) listOfConstraints.getListOf().get(n);
	}

	/**
	 * 
	 * @param i
	 * @return
	 */
	public Event getEvent(int i) {
		return (Event) listOfEvents.getListOf().get(i);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Event getEvent(String id) {
		for (SBase e : listOfEvents.getListOf()){
			Event ev = (Event) e;
			if (ev.getId().equals(id)){
				return ev;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param n
	 * @return
	 */
	public FunctionDefinition getFunctionDefinition(int n) {
		return (FunctionDefinition) listOfFunctionDefinitions.getListOf().get(n);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public FunctionDefinition getFunctionDefinition(String id) {
		for (SBase fd : listOfFunctionDefinitions.getListOf()){
			FunctionDefinition f = (FunctionDefinition) fd;
			if (f.getId().equals(id)){
				return f;
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
		return (InitialAssignment) listOfInitialAssignments.getListOf().get(n);
	}

	/**
	 * 
	 * @return
	 */
	public ListOf getListOfCompartments() {
		return listOfCompartments;
	}

	/**
	 * 
	 * @return
	 */
	public ListOf getListOfCompartmentTypes() {
		return listOfCompartmentTypes;
	}

	/**
	 * @return the listOfConstraints
	 */
	public ListOf getListOfConstraints() {
		return listOfConstraints;
	}

	/**
	 * 
	 * @return
	 */
	public ListOf getListOfEvents() {
		return listOfEvents;
	}

	/**
	 * 
	 * @return
	 */
	public ListOf getListOfFunctionDefinitions() {
		return listOfFunctionDefinitions;
	}

	/**
	 * @return the listOfInitialAssignments
	 */
	public ListOf getListOfInitialAssignments() {
		return listOfInitialAssignments;
	}

	/**
	 * 
	 * @return
	 */
	public ListOf getListOfParameters() {
		return listOfParameters;
	}

	/**
	 * 
	 * @return
	 */
	public ListOf getListOfReactions() {
		return listOfReactions;
	}

	/**
	 * 
	 * @return
	 */
	public ListOf getListOfRules() {
		return listOfRules;
	}

	/**
	 * 
	 * @return
	 */
	public ListOf getListOfSpecies() {
		return listOfSpecies;
	}

	/**
	 * 
	 * @return
	 */
	public ListOf getListOfSpeciesTypes() {
		return listOfSpeciesTypes;
	}

	/**
	 * 
	 * @return
	 */
	public ListOf getListOfUnitDefinitions() {
		return listOfUnitDefinitions;
	}

	/**
	 * 
	 * @return
	 */
	public int getNumCompartments() {
		return listOfCompartments.getListOf().size();
	}

	/**
	 * 
	 * @return
	 */
	public int getNumCompartmentTypes() {
		return listOfCompartmentTypes.getListOf().size();
	}

	/**
	 * 
	 * @return
	 */
	public int getNumConstraints() {
		return listOfConstraints.getListOf().size();
	}

	/**
	 * 
	 * @return
	 */
	public int getNumEvents() {
		return listOfEvents.getListOf().size();
	}

	/**
	 * 
	 * @return
	 */
	public int getNumFunctionDefinitions() {
		return listOfFunctionDefinitions.getListOf().size();
	}

	/**
	 * 
	 * @return
	 */
	public int getNumInitialAssignments() {
		return listOfInitialAssignments.getListOf().size();
	}

	/**
	 * Returns the number of parameters that are containt within kineticLaws in
	 * the reactions of this model.
	 * 
	 * @return
	 */
	public int getNumLocalParameters() {
		int count = 0;
		for (SBase r : listOfReactions.getListOf()){
			Reaction reaction = (Reaction) r;
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
		return listOfParameters.getListOf().size();
	}

	/**
	 * 
	 * @return
	 */
	public int getNumReactions() {
		return listOfReactions.getListOf().size();
	}

	/**
	 * 
	 * @return
	 */
	public int getNumRules() {
		return listOfRules.getListOf().size();
	}

	/**
	 * 
	 * @return
	 */
	public int getNumSpecies() {
		return listOfSpecies.getListOf().size();
	}

	/**
	 * 
	 * @return
	 */
	public int getNumSpeciesTypes() {
		return listOfSpeciesTypes.getListOf().size();
	}

	/**
	 * 
	 * @return
	 */
	public int getNumUnitDefinitions() {
		return listOfUnitDefinitions.getListOf().size();
	}

	/**
	 * 
	 * @param n
	 * @return
	 */
	public Parameter getParameter(int n) {
		return (Parameter) listOfParameters.getListOf().get(n);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Parameter getParameter(String id) {
		for (SBase p : listOfParameters.getListOf()) {
			Parameter parameter = (Parameter) p;
			if (parameter.getId().equals(id))
				return parameter;
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
	 * Get the n-th Reaction object in this Model.
	 * 
	 * @param reactionIndex
	 * @return the n-th Reaction of this Model.
	 */
	public Reaction getReaction(int n) {
		return (Reaction) listOfReactions.getListOf().get(n);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Reaction getReaction(String id) {
		for (SBase r : listOfReactions.getListOf()) {
			Reaction reaction = (Reaction) r;
			if (reaction.getId().equals(id)){
				return reaction;

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
		return (Rule) listOfRules.getListOf().get(n);
	}

	/**
	 * Get the n-th Species object in this Model.
	 * 
	 * @param n
	 *            the nth Species of this Model.
	 * @return
	 */
	public Species getSpecies(int n) {
		return (Species) listOfSpecies.getListOf().get(n);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Species getSpecies(String id) {
		for (SBase s : listOfSpecies.getListOf()) {
			Species species = (Species) s;
			if (species.getId().equals(id)){
				return species;
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
		for (SBase s : listOfSpeciesTypes.getListOf()){
			SpeciesType st = (SpeciesType) s;
			if (st.getId().equals(id)){
				return st;
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
		for (SBase ud : listOfUnitDefinitions.getListOf()){
			UnitDefinition unitdef = (UnitDefinition) ud;
			if (unitdef.getId().equals(id)){
				return unitdef;
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
		listOfParameters.getListOf().remove(parameter);
		parameter.sbaseRemoved();
	}

	/**
	 * removes a reaction from the model
	 * 
	 * @param reac
	 */
	public void removeReaction(Reaction reac) {
		listOfReactions.getListOf().remove(reac);
		reac.sbaseRemoved();
	}

	/**
	 * removes a species from the model
	 * 
	 * @param spec
	 * @return success
	 */
	public boolean removeSpecies(Species spec) {
		boolean success = listOfSpecies.getListOf().remove(spec);
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
		boolean success = listOfUnitDefinitions.getListOf().remove(unitDefininition);
		if (success)
			unitDefininition.sbaseRemoved();
		return success;
	}

	/**
	 * 
	 * @param listOfCompartments
	 */
	public void setListOfCompartments(ListOf listOfCompartments) {
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
			ListOf listOfCompartmentTypes) {
		this.listOfCompartmentTypes = listOfCompartmentTypes;
		setThisAsParentSBMLObject(listOfCompartmentTypes);
		this.listOfCompartmentTypes.setCurrentList(CurrentListOfSBMLElements.listOfCompartmentTypes);
		stateChanged();
	}

	/**
	 * @param listOfConstraints
	 *            the listOfConstraints to set
	 */
	public void setListOfConstraints(ListOf listOfConstraints) {
		this.listOfConstraints = listOfConstraints;
		setThisAsParentSBMLObject(listOfConstraints);
		this.listOfConstraints.setCurrentList(CurrentListOfSBMLElements.listOfConstraints);

	}

	/**
	 * 
	 * @param listOfEvents
	 */
	public void setListOfEvents(ListOf listOfEvents) {
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
			ListOf listOfFunctionDefinitions) {
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
			ListOf listOfInitialAssignments) {
		this.listOfInitialAssignments = listOfInitialAssignments;
		setThisAsParentSBMLObject(listOfInitialAssignments);
		this.listOfInitialAssignments.setCurrentList(CurrentListOfSBMLElements.listOfInitialAssignments);

	}

	/**
	 * 
	 * @param listOfParameters
	 */
	public void setListOfParameters(ListOf listOfParameters) {
		this.listOfParameters = listOfParameters;
		setThisAsParentSBMLObject(listOfParameters);
		this.listOfParameters.setCurrentList(CurrentListOfSBMLElements.listOfParameters);

		stateChanged();
	}

	/**
	 * 
	 * @param listOfReactions
	 */
	public void setListOfReactions(ListOf listOfReactions) {
		this.listOfReactions = listOfReactions;
		setThisAsParentSBMLObject(listOfReactions);
		this.listOfReactions.setCurrentList(CurrentListOfSBMLElements.listOfReactions);

		stateChanged();
	}

	/**
	 * 
	 * @return
	 */
	public void setListOfRules(ListOf listOfRules) {
		this.listOfRules = listOfRules;
		setThisAsParentSBMLObject(listOfRules);
		this.listOfRules.setCurrentList(CurrentListOfSBMLElements.listOfRules);

		stateChanged();
	}

	/**
	 * 
	 * @param listOfSpecies
	 */
	public void setListOfSpecies(ListOf listOfSpecies) {
		this.listOfSpecies = listOfSpecies;
		setThisAsParentSBMLObject(listOfSpecies);
		this.listOfSpecies.setCurrentList(CurrentListOfSBMLElements.listOfSpecies);

		stateChanged();
	}

	/**
	 * @param listOfSpeciesTypes
	 *            the listOfSpeciesTypes to set
	 */
	public void setListOfSpeciesTypes(ListOf listOfSpeciesTypes) {
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
			ListOf listOfUnitDefinitions) {
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
