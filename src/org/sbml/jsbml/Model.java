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

package org.sbml.jsbml;

import java.util.ArrayList;
import java.util.Map;

import org.sbml.jsbml.util.filters.BoundaryConditionFilter;
import org.sbml.jsbml.util.filters.NameFilter;

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
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = 5256379371231860049L;
	/**
	 * Error message to indicate that an element could not be created.
	 */
	private static final String COULD_NOT_CREATE_ELEMENT_MSG = "Could not create %s because no %s have been defined yet.\n";
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
	 * Represents the list of predefined UnitDefinitions for a given SBML level and
	 * version.
	 */
	private ArrayList<UnitDefinition> listOfPredefinedUnitDefinitions;

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
	 * Creates a Model instance. By default, all the listxxx and xxxUnitsID are
	 * null.
	 */
	public Model() {
		super();
		initDefaults();
	}

	/**
	 * 
	 * @param id
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
			setListOfSpeciesTypes((ListOf<SpeciesType>) model
					.getListOfSpeciesTypes().clone());
		}
		if (model.isSetListOfCompartments()) {
			setListOfCompartments((ListOf<Compartment>) model
					.getListOfCompartments().clone());
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
			setListOfConstraints((ListOf<Constraint>) model
					.getListOfConstraints().clone());
		}
		if (model.isSetListOfReactions()) {
			setListOfReactions((ListOf<Reaction>) model.getListOfReactions()
					.clone());
		}
		if (model.isSetListOfEvents()) {
			setListOfEvents((ListOf<Event>) model.getListOfEvents().clone());
		}
	}

	/**
	 * 
	 * @param id
	 */
	public Model(String id) {
		super(id);
		initDefaults();
	}
	
	/**
	 * Creates a Model instance from an id, level and version. By default, all
	 * the listxxx and xxxUnitsID are null.
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
	 * @return <code>true</code> if the {@link #listOfCompartments} was
	 *         changed as a result of this call.
	 */
	public boolean addCompartment(Compartment compartment) {
		return getListOfCompartments().add(compartment);
	}

	/**
	 * Adds a CompartmentType instance to the listOfCompartmentTypes of this
	 * Model.
	 * 
	 * @param compartmentType
	 * @return <code>true</code> if the {@link #listOfCompartmentTypes} was
	 *         changed as a result of this call.
	 * @deprecated Only valid in SBML Level 2 for Versions 2 through 4.
	 */
	@Deprecated
	public boolean addCompartmentType(CompartmentType compartmentType) {
		return getListOfCompartmentTypes().add(compartmentType);
	}

	/**
	 * Adds a {@link Constraint} instance to the listOfConstraints of this {@link Model}.
	 * 
	 * @param constraint
	 * @return <code>true</code> if the {@link #listOfConstraints} was
	 *         changed as a result of this call.
	 */
	public boolean addConstraint(Constraint constraint) {
		return getListOfConstraints().add(constraint);
	}

	/**
	 * Adds an {@link Event} instance to the listOfEvents of this Model.
	 * 
	 * @param event
	 * @return <code>true</code> if the {@link #listOfEvents} was
	 *         changed as a result of this call.
	 */
	public boolean addEvent(Event event) {
		return getListOfEvents().add(event);
	}

	/**
	 * Adds a {@link FunctionDefinition} instance to the listOfFunctionDefinitions of
	 * this {@link Model}.
	 * 
	 * @param functionDefinition
	 * @return <code>true</code> if the {@link #listOfFunctionDefinitions} was
	 *         changed as a result of this call.
	 */
	public boolean addFunctionDefinition(FunctionDefinition functionDefinition) {
		return getListOfFunctionDefinitions().add(functionDefinition);
	}

	/**
	 * Adds an InitialAssignment instance to the listOfInitialAssignments of
	 * this Model.
	 * 
	 * @param initialAssignment
	 * @return <code>true</code> if the {@link #listOfInitialAssignments} was
	 *         changed as a result of this call.
	 */
	public boolean addInitialAssignment(InitialAssignment initialAssignment) {
		return getListOfInitialAssignments().add(initialAssignment);
	}

	/**
	 * Adds a Parameter instance to the listOfParameters of this Model.
	 * 
	 * @param parameter
	 * @return <code>true</code> if the {@link #listOfParameters} was
	 *         changed as a result of this call.
	 */
	public boolean addParameter(Parameter parameter) {
		return getListOfParameters().add(parameter);
	}

	/**
	 * Adds a Reaction instance to the listOfReactions of this Model.
	 * 
	 * @param reaction
	 * @return <code>true</code> if the {@link #listOfReactions} was
	 *         changed as a result of this call.
	 */
	public boolean addReaction(Reaction reaction) {
		return getListOfReactions().add(reaction);
	}

	/**
	 * Adds a Rule instance to the listOfRules of this Model.
	 * 
	 * @param rule
	 * @return <code>true</code> if the {@link #listOfRules} was
	 *         changed as a result of this call.
	 */
	public boolean addRule(Rule rule) {
		return getListOfRules().add(rule);
	}

	/**
	 * Adds a Species instance to the listOfSpecies of this Model.
	 * 
	 * @param spec
	 * @return <code>true</code> if the {@link #listOfSpecies} was
	 *         changed as a result of this call.
	 */
	public boolean addSpecies(Species spec) {
		return getListOfSpecies().add(spec);
	}

	/**
	 * Adds a SpeciesType instance to the listOfSpeciesTypes of this Model.
	 * 
	 * @param speciesType
	 * @return <code>true</code> if the {@link #listOfSpeciesTypes} was changed
	 *         as a result of this call.
	 * @deprecated Only valid in SBML Level 2 for Versions 2 through 4.
	 */
	@Deprecated
	public boolean addSpeciesType(SpeciesType speciesType) {
		return getListOfSpeciesTypes().add(speciesType);
	}

	/**
	 * Adds an UnitDefinition instance to the listOfUnitDefinitions of this
	 * Model.
	 * 
	 * @param unitDefinition
	 * @return <code>true</code> if the {@link #listOfUnitDefinitions} was
	 *         changed as a result of this call.
	 */
	public boolean addUnitDefinition(UnitDefinition unitDefinition) {
		return getListOfUnitDefinitions().add(unitDefinition);
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

	/**
	 * Method to test whether a {@link Compartment} with the given identifier
	 * has been declared in this model.
	 * 
	 * @param id
	 * @return
	 */
	public boolean containsCompartment(String id) {
		return getCompartment(id) != null;
	}

	/**
	 * Method to test whether a {@link FunctionDefinition} with the given
	 * identifier has been declared in this model.
	 * 
	 * @param id
	 * @return
	 */
	public boolean containsFunctionDefinition(String id) {
		return getFunctionDefinition(id) != null;
	}

	/**
	 * Method to test whether a {@link Parameter} with the given identifier has
	 * been declared in this model.
	 * 
	 * @param id
	 * @return
	 */
	public boolean containsParameter(String id) {
		return getParameter(id) != null;
	}

	/**
	 * Returns true if this model contains a reference to the given
	 * {@link Quantity}.
	 * 
	 * @param quantity
	 * @return
	 */
	public boolean containsQuantity(Quantity quantity) {
		Model model = quantity.getModel();
		if (!quantity.isSetId() || (model == null) || (this != model)) {
			return false;
		}
		return findQuantity(quantity.getId()) != null;
	}

	/**
	 * Method to test whether a {@link Species} with the given identifier has
	 * been declared in this model.
	 * 
	 * @param id
	 * @return
	 */
	public boolean containsSpecies(String id) {
		return getSpecies(id) != null;
	}

	/**
	 * Method to test whether a {@link UnitDefinition} with the given identifier
	 * has been declared in this model.
	 * 
	 * @param units
	 * @return
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
	public Rule createAlgebraicRule() {
		AlgebraicRule rule = new AlgebraicRule(level, version);
		addRule(rule);

		return rule;
	}

	/**
	 * Creates a new {@link AssignmentRule} inside this {@link Model} and
	 * returns it.
	 * <p>
	 * 
	 * @return the {@link AssignmentRule} object created
	 *         <p>
	 * @see #addRule(Rule r)
	 */
	public Rule createAssignmentRule() {
		AssignmentRule rule = new AssignmentRule(level, version);
		addRule(rule);

		return rule;
	}

	/**
	 * Creates a new {@link Compartment} inside this {@link Model} and returns
	 * it.
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
	 * 
	 * @param id
	 * @return
	 */
	public Compartment createCompartment(String id) {
		Compartment compartment = new Compartment(id, getLevel(), getVersion());
		addCompartment(compartment);
		return compartment;
	}

	/**
	 * Creates a new {@link CompartmentType} inside this {@link Model} and
	 * returns it.
	 * <p>
	 * 
	 * @return the {@link CompartmentType} object created
	 *         <p>
	 * @see #addCompartmentType(CompartmentType ct)
	 */
	@SuppressWarnings("deprecation")
	public CompartmentType createCompartmentType() {
		return createCompartmentType(null);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@Deprecated
	public CompartmentType createCompartmentType(String id) {
		CompartmentType compartmentType = new CompartmentType(id, getLevel(),
				getVersion());
		addCompartmentType(compartmentType);

		return compartmentType;
	}

	/**
	 * Creates a new {@link Constraint} inside this {@link Model} and returns
	 * it.
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
	 * Creates a new {@link Delay} inside the last {@link Event} object created
	 * in this {@link Model}, and returns a pointer to it.
	 * <p>
	 * The mechanism by which the last {@link Event} object in this model was
	 * created is not significant. It could have been created in a variety of
	 * ways, for example by using createEvent(). If no {@link Event} object
	 * exists in this {@link Model} object, a new {@link Delay} is <em>not</em>
	 * created and NULL is returned instead.
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
	 * 
	 * @param id
	 * @return
	 */
	public Event createEvent(String id) {
		Event event = new Event(id, getLevel(), getVersion());
		addEvent(event);
		return event;
	}

	/**
	 * Creates a new {@link EventAssignment} inside the last {@link Event}
	 * object created in this {@link Model}, and returns a pointer to it.
	 * <p>
	 * The mechanism by which the last {@link Event} object in this model was
	 * created is not significant. It could have been created in a variety of
	 * ways, for example by using createEvent(). If no {@link Event} object
	 * exists in this {@link Model} object, a new {@link EventAssignment} is
	 * <em>not</em> created and NULL is returned instead.
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

		EventAssignment eventAssgnt = new EventAssignment(getLevel(),
				getVersion());
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
	 * 
	 * @param id
	 * @return
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
	 * added to this {@link Model} is not significant. It could have been
	 * created in a variety of ways, for example using createReaction(). If a
	 * {@link Reaction} does not exist for this model, or a {@link Reaction}
	 * exists but already has a {@link KineticLaw}, a new {@link KineticLaw} is
	 * <em>not</em> created and NULL is returned instead.
	 * <p>
	 * 
	 * @return the {@link KineticLaw} object created
	 */
	public KineticLaw createKineticLaw() {

		Reaction lastReaction = (Reaction) getLastElementOf(listOfReactions);

		if (lastReaction == null) {
			System.err.printf(COULD_NOT_CREATE_ELEMENT_MSG, "KineticLaw",
					"reactions");
			return null;
		}

		KineticLaw kineticLaw = new KineticLaw(getLevel(), getVersion());
		lastReaction.setKineticLaw(kineticLaw);

		return kineticLaw;
	}

	/**
	 * Creates a new local {@link Parameter} inside the {@link KineticLaw}
	 * object of the last {@link Reaction} created inside this {@link Model},
	 * and returns a pointer to it.
	 * <p>
	 * The last {@link KineticLaw} object in this {@link Model} could have been
	 * created in a variety of ways. For example, it could have been added using
	 * createKineticLaw(), or it could be the result of using
	 * Reaction.createKineticLaw() on the {@link Reaction} object created by a
	 * createReaction(). If a {@link Reaction} does not exist for this model, or
	 * the last {@link Reaction} does not contain a {@link KineticLaw} object, a
	 * new {@link Parameter} is <em>not</em> created and NULL is returned
	 * instead.
	 * <p>
	 * 
	 * @return the {@link Parameter} object created
	 */
	public LocalParameter createKineticLawParameter() {
		return createKineticParameter(null);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public LocalParameter createKineticParameter(String id) {
		Reaction lastReaction = (Reaction) getLastElementOf(listOfReactions);
		KineticLaw lastKineticLaw = null;

		if (lastReaction == null) {
			System.err.printf(COULD_NOT_CREATE_ELEMENT_MSG,
					"Parameter for KineticLaw", "reactions");
			return null;
		} else {
			lastKineticLaw = lastReaction.getKineticLaw();
			if (lastKineticLaw == null) {
				return null;
			}
		}

		LocalParameter parameter = new LocalParameter(id, getLevel(),
				getVersion());
		lastKineticLaw.addLocalParameter(parameter);

		return parameter;
	}

	/**
	 * Creates a new {@link ModifierSpeciesReference} object for a modifier
	 * species inside the last {@link Reaction} object in this {@link Model},
	 * and returns a pointer to it.
	 * <p>
	 * The mechanism by which the last {@link Reaction} object was created and
	 * added to this {@link Model} is not significant. It could have been
	 * created in a variety of ways, for example using createReaction(). If a
	 * {@link Reaction} does not exist for this model, a new
	 * {@link ModifierSpeciesReference} is <em>not</em> created and NULL is
	 * returned instead.
	 * <p>
	 * 
	 * @return the {@link SpeciesReference} object created
	 */
	public ModifierSpeciesReference createModifier() {
		return createModifier(null);
	}

	/**
	 * 
	 * @param id
	 * @return
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
	 * 
	 * @param id
	 * @return
	 */
	public Parameter createParameter(String id) {
		Parameter parameter = new Parameter(id, getLevel(), getVersion());
		addParameter(parameter);

		return parameter;
	}

	/**
	 * Creates a new {@link SpeciesReference} object for a product inside the
	 * last {@link Reaction} object in this {@link Model}, and returns a pointer
	 * to it.
	 * <p>
	 * The mechanism by which the last {@link Reaction} object was created and
	 * added to this {@link Model} is not significant. It could have been
	 * created in a variety of ways, for example using createReaction(). If a
	 * {@link Reaction} does not exist for this model, a new
	 * {@link SpeciesReference} is <em>not</em> created and NULL is returned
	 * instead.
	 * <p>
	 * 
	 * @return the {@link SpeciesReference} object created
	 */
	public SpeciesReference createProduct() {
		return createProduct(null);
	}

	public SpeciesReference createProduct(String id) {
		Reaction lastReaction = (Reaction) getLastElementOf(listOfReactions);

		if (lastReaction == null) {
			System.err.printf(COULD_NOT_CREATE_ELEMENT_MSG, "Product",
					"reactions");
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
	 * added to this {@link Model} is not significant. It could have been
	 * created in a variety of ways, for example using createReaction(). If a
	 * {@link Reaction} does not exist for this model, a new
	 * {@link SpeciesReference} is <em>not</em> created and NULL is returned
	 * instead.
	 * <p>
	 * 
	 * @return the {@link SpeciesReference} object created
	 */
	public SpeciesReference createReactant() {
		return createReactant(null);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public SpeciesReference createReactant(String id) {
		Reaction lastReaction = (Reaction) getLastElementOf(listOfReactions);
		if (lastReaction == null) {
			System.err.printf(COULD_NOT_CREATE_ELEMENT_MSG, "Reactant",
					"reactions");
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
	 * 
	 * @param id
	 * @return
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
	 * 
	 * @param id
	 * @return
	 */
	public Species createSpecies(String id) {
		Species species = new Species(id, getLevel(), getVersion());
		addSpecies(species);
		return species;
	}
	
	/**
	 * 
	 * @param id
	 * @param c
	 * @return
	 */
	public Species createSpecies(String id, Compartment c) {
		Species s = createSpecies(id);
		s.setCompartment(c);
		return s;
	}

	/**
	 * Creates a new {@link SpeciesType} inside this {@link Model} and returns
	 * it.
	 * <p>
	 * 
	 * @return the {@link SpeciesType} object created
	 *         <p>
	 * @see #addSpeciesType(SpeciesType st)
	 */
	@Deprecated
	public SpeciesType createSpeciesType() {
		return createSpeciesType(null);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@Deprecated
	public SpeciesType createSpeciesType(String id) {
		SpeciesType speciesType = new SpeciesType(id, getLevel(), getVersion());
		addSpeciesType(speciesType);

		return speciesType;
	}

	/**
	 * Creates a new {@link Trigger} inside the last {@link Event} object
	 * created in this {@link Model}, and returns a pointer to it.
	 * <p>
	 * The mechanism by which the last {@link Event} object in this model was
	 * created is not significant. It could have been created in a variety of
	 * ways, for example by using createEvent(). If no {@link Event} object
	 * exists in this {@link Model} object, a new {@link Trigger} is
	 * <em>not</em> created and NULL is returned instead.
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
	 * model, a new {@link Unit} is <em>not</em> created and NULL is returned
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
	 * 
	 * @param kind
	 * @return
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
	 * Creates a new {@link UnitDefinition} inside this {@link Model} and
	 * returns it.
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
	 * 
	 * @param id
	 * @return
	 */
	public UnitDefinition createUnitDefinition(String id) {
		UnitDefinition unitDefinition = new UnitDefinition(id, getLevel(),
				getVersion());
		addUnitDefinition(unitDefinition);

		return unitDefinition;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.NamedSBase#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof Model) {
			Model m = (Model) o;
			boolean equal = super.equals(o);
			if ((m.isSetListOfFunctionDefinitions() && !isSetListOfFunctionDefinitions())
					|| (!m.isSetListOfFunctionDefinitions() && isSetListOfFunctionDefinitions())) {
				return false;
			} else if (m.isSetListOfFunctionDefinitions()
					&& isSetListOfFunctionDefinitions()) {
				equal &= getListOfFunctionDefinitions().equals(
						m.getListOfFunctionDefinitions());
			}
			if ((m.isSetListOfUnitDefinitions() && !isSetListOfUnitDefinitions())
					|| (!m.isSetListOfUnitDefinitions() && isSetListOfUnitDefinitions())) {
				return false;
			} else if (m.isSetListOfUnitDefinitions()
					&& isSetListOfUnitDefinitions()) {
				equal &= getListOfUnitDefinitions().equals(
						m.getListOfUnitDefinitions());
			}
			if ((m.isSetListOfCompartmentTypes() && !isSetListOfCompartmentTypes())
					|| (!m.isSetListOfCompartmentTypes() && isSetListOfCompartmentTypes())) {
				return false;
			} else if (m.isSetListOfCompartmentTypes()
					&& isSetListOfCompartmentTypes()) {
				equal &= getListOfCompartmentTypes().equals(
						m.getListOfCompartmentTypes());
			}
			if ((m.isSetListOfSpeciesTypes() && !isSetListOfSpeciesTypes())
					|| (!m.isSetListOfSpeciesTypes() && isSetListOfSpeciesTypes())) {
				return false;
			} else if (m.isSetListOfSpeciesTypes() && isSetListOfSpeciesTypes()) {
				equal &= getListOfSpeciesTypes().equals(
						m.getListOfSpeciesTypes());
			}
			if ((m.isSetListOfCompartments() && !isSetListOfCompartments())
					|| (!m.isSetListOfCompartments() && isSetListOfCompartments())) {
				return false;
			} else if (m.isSetListOfCompartments() && isSetListOfCompartments()) {
				equal &= getListOfCompartments().equals(
						m.getListOfCompartments());
			}
			if ((m.isSetListOfSpecies() && !isSetListOfSpecies())
					|| (!m.isSetListOfSpecies() && isSetListOfSpecies())) {
				return false;
			} else if (m.isSetListOfSpecies() && isSetListOfSpecies()) {
				equal &= getListOfSpecies().equals(m.getListOfSpecies());
			}
			if ((m.isSetListOfParameters() && !isSetListOfParameters())
					|| (!m.isSetListOfParameters() && isSetListOfParameters())) {
				return false;
			} else if (m.isSetListOfParameters() && isSetListOfParameters()) {
				equal &= getListOfParameters().equals(m.getListOfParameters());
			}
			if ((m.isSetListOfInitialAssignments() && !isSetListOfInitialAssignments())
					|| (!m.isSetListOfInitialAssignments() && isSetListOfInitialAssignments())) {
				return false;
			} else if (m.isSetListOfInitialAssignments()
					&& isSetListOfInitialAssignments()) {
				equal &= getListOfInitialAssignments().equals(
						m.getListOfInitialAssignments());
			}
			if ((m.isSetListOfRules() && !isSetListOfRules())
					|| (!m.isSetListOfRules() && isSetListOfRules())) {
				return false;
			} else if (m.isSetListOfRules() && isSetListOfRules()) {
				equal &= getListOfRules().equals(m.getListOfRules());
			}
			if ((m.isSetListOfConstraints() && !isSetListOfConstraints())
					|| (!m.isSetListOfConstraints() && isSetListOfConstraints())) {
				return false;
			} else if (m.isSetListOfConstraints() && isSetListOfConstraints()) {
				equal &= getListOfConstraints()
						.equals(m.getListOfConstraints());
			}
			if ((m.isSetListOfReactions() && !isSetListOfReactions())
					|| (!m.isSetListOfReactions() && isSetListOfReactions())) {
				return false;
			} else if (m.isSetListOfReactions() && isSetListOfReactions()) {
				equal &= getListOfReactions().equals(m.getListOfReactions());
			}
			if ((m.isSetListOfEvents() && !isSetListOfEvents())
					|| (!m.isSetListOfEvents() && isSetListOfEvents())) {
				return false;
			} else if (m.isSetListOfEvents() && isSetListOfEvents()) {
				equal &= getListOfEvents().equals(m.getListOfEvents());
			}
			if ((m.isSetTimeUnitsInstance() && !isSetTimeUnitsInstance())
					|| (!m.isSetTimeUnitsInstance() && isSetTimeUnitsInstance())) {
				return false;
			} else if (m.isSetTimeUnits() && isSetTimeUnits()) {
				equal &= getTimeUnits().equals(m.getTimeUnits());
			}
			if ((m.isSetAreaUnits() && !isSetAreaUnits())
					|| (!m.isSetAreaUnits() && isSetAreaUnits())) {
				return false;
			} else if (m.isSetAreaUnits() && isSetAreaUnits()) {
				equal &= getAreaUnits().equals(m.getAreaUnits());
			}
			if ((m.isSetConversionFactor() && !isSetConversionFactor())
					|| (!m.isSetConversionFactor() && isSetConversionFactor())) {
				return false;
			} else if (m.isSetConversionFactor() && isSetConversionFactor()) {
				equal &= getConversionFactor().equals(m.getConversionFactor());
			}
			if ((m.isSetExtentUnits() && !isSetExtentUnits())
					|| (!m.isSetExtentUnits() && isSetExtentUnits())) {
				return false;
			} else if (m.isSetExtentUnits() && isSetExtentUnits()) {
				equal &= getExtentUnits().equals(m.getExtentUnits());
			}
			if ((m.isSetLengthUnits() && !isSetLengthUnits())
					|| (!m.isSetLengthUnits() && isSetLengthUnits())) {
				return false;
			} else if (m.isSetLengthUnits() && isSetLengthUnits()) {
				equal &= getLengthUnits().equals(m.getLengthUnits());
			}
			if ((m.isSetSubstanceUnits() && !isSetSubstanceUnits())
					|| (!m.isSetSubstanceUnits() && isSetSubstanceUnits())) {
				return false;
			} else if (m.isSetSubstanceUnits() && isSetSubstanceUnits()) {
				equal &= getSubstanceUnits().equals(m.getSubstanceUnits());
			}
			if ((m.isSetVolumeUnits() && !isSetVolumeUnits())
					|| (!m.isSetVolumeUnits() && isSetVolumeUnits())) {
				return false;
			} else if (m.isSetVolumeUnits() && isSetVolumeUnits()) {
				equal &= getVolumeUnits().equals(m.getVolumeUnits());
			}
			return equal;
		}
		return false;
	}

	/**
	 * try to figure out the meaning of this name.
	 * 
	 * @param idOrName
	 *            an id indicating a variable of the model.
	 * @return null if no model is available or the model does not contain a
	 *         compartment, species, or parameter wit the given id.
	 */
	public NamedSBase findNamedSBase(String idOrName) {
		if (idOrName.equals(getId())) {
			return this;
		}
		NamedSBase nsb = getCompartmentType(idOrName);
		if (nsb == null) {
			nsb = getEvent(idOrName);
		}
		if (nsb == null) {
			for (Reaction r : getListOfReactions()) {
				nsb = r.getModifier(idOrName);
				if (nsb != null) {
					return nsb;
				}
			}
		}
		if (nsb == null) {
			nsb = getSpeciesType(idOrName);
		}
		if (nsb == null) {
			nsb = getUnitDefinition(idOrName);
		}
		return nsb == null ? findNamedSBaseWithDerivedUnit(idOrName) : nsb;
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public NamedSBaseWithDerivedUnit findNamedSBaseWithDerivedUnit(
			String idOrName) {
		NamedSBaseWithDerivedUnit nsb = findQuantity(idOrName);
		if (nsb == null) {
			nsb = getReaction(idOrName);
		}
		if (nsb == null) {
			nsb = getFunctionDefinition(idOrName);
		}
		return nsb;
	}

	/**
	 * 
	 * @param idOrName
	 * @return the Compartment, Species, SpeciesReference or Parameter which has
	 *         'symbol' as id.
	 */
	public Quantity findQuantity(String idOrName) {
		Quantity nsb = findVariable(idOrName);
		if (nsb == null) {
			for (Reaction r : getListOfReactions()) {
				if (r.isSetKineticLaw()) {
					nsb = r.getKineticLaw().getLocalParameter(idOrName);
					if (nsb != null) {
						break;
					}
				}
			}
		}
		return nsb;
	}

	/**
	 * Searches in the list of {@link Compartment}s, {@link Species}, and
	 * {@link Parameter}s for the element with the given identifier.
	 * 
	 * @param id
	 * @return
	 */
	public Symbol findSymbol(String id) {
		Symbol symbol = getCompartment(id);
		if (symbol == null) {
			symbol = getSpecies(id);
		}
		if (symbol == null) {
			symbol = getParameter(id);
		}
		return symbol;
	}

	/**
	 * Searches the variable with the given identifier in this model.
	 * 
	 * @param variable
	 *            The identifier of the {@link Variable} of interest.
	 * @return the {@link Compartment}, {@link Species},
	 *         {@link SpeciesReference}, or {@link Parameter}, which has
	 *         'variable' as id.
	 */
	public Variable findVariable(String variable) {
		Variable nsb = findSymbol(variable);
		if ((nsb == null) && isSetListOfReactions()) {
			for (int i = 0; i < getNumReactions(); i++) {
				Reaction reaction = getReaction(i);
				nsb = reaction.getReactant(variable);
				if (nsb != null) {
					break;
				} else {
					nsb = reaction.getProduct(variable);
					if (nsb != null) {
						break;
					}
				}
			}
		}
		return nsb;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#getAllowsChildren()
	 */
	@Override
	public boolean getAllowsChildren() {
		return true;
	}

	/**
	 * 
	 * @return the areaUnitsID of this {@link Model}. Returns an empty {@link String} if it is
	 *         not set.
	 */
	public String getAreaUnits() {
		return isSetAreaUnits() ? areaUnitsID : "";
	}

	/**
	 * 
	 * @return the UnitDefinition instance which has the areaUnitsID of this
	 *         Model as id. Null if it doesn't exist
	 */
	public UnitDefinition getAreaUnitsInstance() {
		return getUnitDefinition(this.areaUnitsID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
	 */
	@Override
	public SBase getChildAt(int index) {
		int children = getChildCount();
		if (index >= children) {
			throw new IndexOutOfBoundsException(index + " >= " + children);
		}
		int pos = 0;
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
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#getChildCount()
	 */
	@Override
	public int getChildCount() {
		int children = 0;
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
	 * 
	 * @param n
	 * @return the nth {@link Compartment} instance of the listOfCompartments. Null if
	 *         if the listOfCompartments is not set.
	 */
	public Compartment getCompartment(int n) {
		return getListOfCompartments().get(n);
	}

	/**
	 * 
	 * @param id
	 * @return the {@link Compartment} of the listOfCompartments which has 'id' as id
	 *         (or name depending on the version and level). Null if if the
	 *         listOfCompartments is not set.
	 */
	public Compartment getCompartment(String id) {
		return getListOfCompartments().firstHit(new NameFilter(id));
	}

	/**
	 * Gets the nth CompartmentType object in this Model.
	 * 
	 * @param n
	 *            index
	 * @return the nth CompartmentType of this Model. Returns null if there are
	 *         no compartmentType defined or if the index n is too big or lower
	 *         than zero.
	 */
	@Deprecated
	public CompartmentType getCompartmentType(int n) {
		return getListOfCompartmentTypes().get(n);
	}

	/**
	 * Gets the {@link CompartmentType} with the given <code>id</code>.
	 * 
	 * @param id
	 * @return the CompartmentType of the listOfCompartmentTypes which has 'id'
	 *         as id (or name depending on the level and version). Null if 
	 *         the listOfCompartmentTypes is not set or the id is not found.
	 */
	@Deprecated
	public CompartmentType getCompartmentType(String id) {
		return getListOfCompartmentTypes().firstHit(new NameFilter(id));
	}

	/**
	 * Gets the nth Constraint object in this Model.
	 * 
	 * @param n
	 * @return the nth Constraint of this Model. Returns null if there are no
	 *         constraint defined or if the index n is too big or lower than
	 *         zero.
	 */
	public Constraint getConstraint(int n) {
		return getListOfConstraints().get(n);
	}

	/**
	 * Returns the conversionFactor ID of this {@link Model}.
	 * 
	 * @return the conversionFactorID of this {@link Model}. Returns an empty {@link String} if
	 *         it is not set.
	 */
	public String getConversionFactor() {
		return isSetConversionFactor() ? conversionFactorID : "";
	}

	/**
	 * Returns the Parameter instance which has the conversionFactorID of this
	 * Model as id.
	 * 
	 * @return the Parameter instance which has the conversionFactorID of this
	 *         Model as id. Null if it doesn't exist
	 */
	public Parameter getConversionFactorInstance() {
		return getParameter(this.conversionFactorID);
	}

	/**
	 * Gets the nth Event object in this Model.
	 * 
	 * @param n
	 * @return the nth Event of this Model. Returns null if there are no event
	 *         defined or if the index n is too big or lower than zero.
	 */
	public Event getEvent(int n) {
		return getListOfEvents().get(n);
	}

	/**
	 * Gets the {@link Event} which as the given <code>id</code> as id.
	 * 
	 * @param id
	 * @return the Event of the listOfEvents which has 'id' as id (or name
	 *         depending on the level and version). Null if if the listOfEvents
	 *         is not set.
	 */
	public Event getEvent(String id) {
		return getListOfEvents().firstHit(new NameFilter(id));
	}

	/**
	 * 
	 * @return the extentUnitsID of this {@link Model}. Returns an empty {@link String} if it is
	 *         not set.
	 */
	public String getExtentUnits() {
		return isSetExtentUnits() ? extentUnitsID : "";
	}

	/**
	 * 
	 * @return the UnitDefinition instance which has the extentUnitsID of this
	 *         Model as id. Null if it doesn't exist
	 */
	public UnitDefinition getExtentUnitsInstance() {
		return getUnitDefinition(this.extentUnitsID);
	}

	/**
	 * 
	 * @param n
	 * @return the nth {@link FunctionDefinition} instance of the
	 *         listOfFunstionDefinitions. Null if if the
	 *         listOfFunctionDefinitions is not set.
	 */
	public FunctionDefinition getFunctionDefinition(int n) {
		return getListOfFunctionDefinitions().get(n);
	}

	/**
	 * 
	 * @param id
	 * @return the {@link FunctionDefinition} of the listOfFunstionDefinitions which has
	 *         'id' as id (or name depending on the level and version). Null if
	 *         if the listOfFunctionDefinitions is not set.
	 */
	public FunctionDefinition getFunctionDefinition(String id) {
		return getListOfFunctionDefinitions().firstHit(new NameFilter(id));
	}

	/**
	 * Get the nth {@link InitialAssignment} object in this {@link Model}.
	 * 
	 * @param n
	 * @return the nth {@link InitialAssignment} of this {@link Model}. Null if the
	 *         listOfInitialAssignments is not set.
	 */
	public InitialAssignment getInitialAssignment(int n) {
		return getListOfInitialAssignments().get(n);
	}

	/**
	 * Returns the last element added in the given list.
	 * 
	 * @return the last element added in the model, corresponding to the last
	 *         element of the list of these elements, or null is no element
	 *         exist for this type.
	 */
	private <T> T getLastElementOf(ListOf<? extends T> listOf) {
		// added casting and parenthesis because there was a compilation error
		// when using the ant script
		return ((listOf == null) || (listOf.size() == 0)) ? (T) null
				: (T) listOf.getLast();
	}

	/**
	 * 
	 * @return the lengthUnitsID of this {@link Model}. Returns an empty {@link String} if it is
	 *         not set.
	 */
	public String getLengthUnits() {
		return isSetLengthUnits() ? lengthUnitsID : "";
	}

	/**
	 * 
	 * @return the UnitDefinition instance which has the lengthUnitsID of this
	 *         Model as id. Null if it doesn't exist
	 */
	public UnitDefinition getLengthUnitsInstance() {
		return getUnitDefinition(this.lengthUnitsID);
	}

	/**
	 * 
	 * @return the listOfCompartments of this {@link Model}. Can be null if it is not
	 *         set.
	 */
	public ListOf<Compartment> getListOfCompartments() {
		if (listOfCompartments == null) {
			listOfCompartments = ListOf.newInstance(this, Compartment.class);
		}
		return listOfCompartments;
	}

	/**
	 * 
	 * @return the listOfCompartmentTypes of this {@link Model}. Can be null if it is
	 *         not set.
	 * @deprecated
	 */
	@Deprecated
	public ListOf<CompartmentType> getListOfCompartmentTypes() {
		if (listOfCompartmentTypes == null) {
			listOfCompartmentTypes = ListOf.newInstance(this, CompartmentType.class);
		}
		return listOfCompartmentTypes;
	}

	/**
	 * @return the listOfConstraints of this {@link Model}. Can be null if it is not
	 *         set.
	 */
	public ListOf<Constraint> getListOfConstraints() {
		if (listOfConstraints == null) {
			listOfConstraints = ListOf.newInstance(this, Constraint.class);
		}
		return listOfConstraints;
	}

	/**
	 * 
	 * @return the listOfEvents of this {@link Model}. Can be null if it is not set.
	 */
	public ListOf<Event> getListOfEvents() {
		if (listOfEvents == null) {
			listOfEvents = ListOf.newInstance(this, Event.class);
		}
		return listOfEvents;
	}

	/**
	 * 
	 * @return the listOfFunctionDefinitions of this {@link Model}. Can be null if it is
	 *         not set.
	 */
	public ListOf<FunctionDefinition> getListOfFunctionDefinitions() {
		if (listOfFunctionDefinitions == null) {
			listOfFunctionDefinitions = ListOf.newInstance(this,
					FunctionDefinition.class);
		}
		return listOfFunctionDefinitions;
	}

	/**
	 * @return the listOfInitialAssignments of this {@link Model}. Can be null if it is
	 *         not set.
	 */
	public ListOf<InitialAssignment> getListOfInitialAssignments() {
		if (listOfInitialAssignments == null) {
			listOfInitialAssignments = ListOf.newInstance(this, 
					InitialAssignment.class);
		}
		return listOfInitialAssignments;
	}

	/**
	 * 
	 * @return the listOfParameters of this Model. Can be null if it is not set.
	 */
	public ListOf<Parameter> getListOfParameters() {
		if (listOfParameters == null) {
			listOfParameters = ListOf.newInstance(this, Parameter.class);
		}
		return listOfParameters;
	}

	/**
	 * 
	 * @return the listOfReactions of this Model. Can be null if it is not set.
	 */
	public ListOf<Reaction> getListOfReactions() {
		if (listOfReactions == null) {
			listOfReactions = ListOf.newInstance(this, Reaction.class);
		}
		return listOfReactions;
	}

	/**
	 * 
	 * @return the listOfRules of this {@link Model}. Can be null if it is not set.
	 */
	public ListOf<Rule> getListOfRules() {
		if (listOfRules == null) {
			listOfRules = ListOf.newInstance(this, Rule.class);
		}
		return listOfRules;
	}

	/**
	 * 
	 * @return the listOfSpecies of this {@link Model}. Can be null if it is not set.
	 */
	public ListOf<Species> getListOfSpecies() {
		if (listOfSpecies == null) {
			listOfSpecies = ListOf.newInstance(this, Species.class);
		}
		return listOfSpecies;
	}

	/**
	 * 
	 * @return the listOfSpeciesTypes of this {@link Model}. Can be null if it is not
	 *         set.
	 *         @deprecated
	 */
	@Deprecated
	public ListOf<SpeciesType> getListOfSpeciesTypes() {
		if (listOfSpeciesTypes == null) {
			listOfSpeciesTypes = ListOf.newInstance(this, SpeciesType.class);
		}
		return listOfSpeciesTypes;
	}

	/**
	 * 
	 * @return the listOfUnitDefinitions of this {@link Model}. Can be null if it is not
	 *         set.
	 */
	public ListOf<UnitDefinition> getListOfUnitDefinitions() {
		if (listOfUnitDefinitions == null) {
			listOfUnitDefinitions = ListOf.newInstance(this, UnitDefinition.class);
		}
		return listOfUnitDefinitions;
	}

	/**
	 * @see getHistory
	 * @return History of this model
	 * @deprecated use {@link SBase#getHistory()}
	 */
	public History getModelHistory() {
		return getHistory();
	}

	/**
	 * 
	 * @return the number of Compartments of this Model.
	 */
	public int getNumCompartments() {
		return isSetListOfCompartments() ? listOfCompartments.size() : 0;
	}

	/**
	 * 
	 * @return the number of CompartmentTypes of this Model.
	 */
	@Deprecated
	public int getNumCompartmentTypes() {
		return isSetListOfCompartmentTypes() ? listOfCompartmentTypes.size()
				: 0;
	}

	/**
	 * 
	 * @return the number of Constraints of this Model.
	 */
	public int getNumConstraints() {
		return isSetListOfConstraints() ? listOfConstraints.size() : 0;
	}

	/**
	 * 
	 * @return
	 */
	public int getNumDelays() {
		int count = 0;
		for (Event e : getListOfEvents()) {
			if (e.isSetDelay()) {
				count++;
			}
		}
		return count;
	}

	/**
	 * 
	 * @return
	 */
	public int getNumEventAssignments() {
		int count = 0;
		for (Event e : getListOfEvents()) {
			count += e.getNumEventAssignments();
		}
		return count;
	}

	/**
	 * 
	 * @return the number of Events of this Model.
	 */
	public int getNumEvents() {
		return isSetListOfEvents() ? listOfEvents.size() : 0;
	}

	/**
	 * 
	 * @return the number of FunctionDefinitions of this Model.
	 */
	public int getNumFunctionDefinitions() {
		return isSetListOfFunctionDefinitions() ? listOfFunctionDefinitions
				.size() : 0;
	}

	/**
	 * 
	 * @return the number of InitialAssignments of this Model.
	 */
	public int getNumInitialAssignments() {
		return isSetListOfInitialAssignments() ? listOfInitialAssignments
				.size() : 0;
	}

	/**
	 * 
	 * @return
	 */
	public int getNumKineticLaws() {
		int count = 0;
		for (Reaction r : getListOfReactions()) {
			if (r.isSetKineticLaw()) {
				count++;
			}
		}
		return count;
	}

	/**
	 * 
	 * @return
	 */
	public int getNumListsOf() {
		return getChildCount();
	}

	/**
	 * Returns the number of parameters that are contained within kineticLaws in
	 * the reactions of this model.
	 * 
	 * @return
	 */
	public int getNumLocalParameters() {
		int count = 0;
		if (isSetListOfReactions()) {
			for (Reaction reaction : getListOfReactions()) {
				if (reaction.isSetKineticLaw()) {
					count += reaction.getKineticLaw().getNumLocalParameters();
				}
			}
		}
		return count;
	}

	/**
	 * 
	 * @return
	 */
	public int getNumMathContainers() {
		return getNumFunctionDefinitions() + getNumInitialAssignments()
				+ getNumEventAssignments() + getNumDelays()
				+ getNumConstraints() + getNumRules() + getNumTriggers()
				+ getNumStoichiometryMath() + getNumKineticLaws();
	}

	/**
	 * 
	 * @return
	 */
	public int getNumModifierSpeciesReferences() {
		int count = 0;
		for (Reaction r : getListOfReactions()) {
			count += r.getNumModifiers();
		}
		return count;
	}

	/**
	 * 
	 * @return
	 */
	public int getNumNamedSBases() {
		return getNumNamedSBasesWithDerivedUnit() + 1 /* this model */
				+ getNumSpeciesTypes() + getNumCompartmentTypes()
				+ getNumUnitDefinitions() + getNumEvents()
				+ getNumModifierSpeciesReferences();
	}

	/**
	 * 
	 * @return
	 */
	public int getNumNamedSBasesWithDerivedUnit() {
		return getNumQuantities() + getNumFunctionDefinitions()
				+ getNumReactions();
	}

	/**
	 * 
	 * @return the number of Parameters of this Model.
	 */
	public int getNumParameters() {
		return isSetListOfParameters() ? listOfParameters.size() : 0;
	}

	/**
	 * 
	 * @return
	 */
	public int getNumQuantities() {
		return getNumVariables() + getNumLocalParameters();
	}

	/**
	 * 
	 * @return
	 */
	public int getNumQuantitiesWithDefinedUnit() {
		return getNumSymbols() + getNumLocalParameters();
	}

	/**
	 * 
	 * @return the number of Reactions of this Model.
	 */
	public int getNumReactions() {
		return isSetListOfReactions() ? listOfReactions.size() : 0;
	}

	/**
	 * 
	 * @return the number of Rules of this model.
	 */
	public int getNumRules() {
		return isSetListOfRules() ? listOfRules.size() : 0;
	}

	/**
	 * 
	 * @return
	 */
	public int getNumSBases() {
		int count = getNumNamedSBases() - getNumFunctionDefinitions()
				+ getNumMathContainers() + getNumListsOf() + getNumUnits() + 1;
		// one for this model
		if (getParent() != null) {
			count++; // the owning SBML document.
		}
		return count;
	}

	/**
	 * 
	 * @return
	 */
	public int getNumSBasesWithDerivedUnit() {
		return getNumNamedSBasesWithDerivedUnit() + getNumMathContainers()
				- getNumFunctionDefinitions();
	}

	/**
	 * 
	 * @return the number of Species of this Model.
	 */
	public int getNumSpecies() {
		return isSetListOfSpecies() ? listOfSpecies.size() : 0;
	}

	/**
	 * 
	 * @return
	 */
	public int getNumSpeciesReferences() {
		int count = 0;
		for (Reaction r : getListOfReactions()) {
			count += r.getNumReactants() + r.getNumProducts();
		}
		return count;
	}

	/**
	 * 
	 * @return the number of SpeciesTypes of this Model.
	 */
	@Deprecated
	public int getNumSpeciesTypes() {
		return isSetListOfSpeciesTypes() ? listOfSpeciesTypes.size() : 0;
	}

	/**
	 * Counts the number of species whose boundary condition is set to true.
	 * 
	 * @return
	 */
	public int getNumSpeciesWithBoundaryCondition() {
		return getListOfSpecies().filterList(new BoundaryConditionFilter())
				.size();
	}

	/**
	 * 
	 * @return
	 */
	public int getNumStoichiometryMath() {
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
	 * Counts and returns the number of all instances of {@link Symbol}
	 * referenced within the model. There is no dedicated list for
	 * {@link Symbol}s. This is a convenient method to support working with the
	 * model data structure.
	 * 
	 * @return The number of {@link Compartment}s, {@link Species}, and
	 *         {@link Parameter}s in the model.
	 */
	public int getNumSymbols() {
		return getNumParameters() + getNumSpecies() + getNumCompartments();
	}

	/**
	 * 
	 * @return
	 */
	public int getNumTriggers() {
		int count = 0;
		for (Event e : getListOfEvents()) {
			if (e.isSetTrigger()) {
				count++;
			}
		}
		return count;
	}

	/**
	 * 
	 * @return the number of UnitDefinitions of this Model.
	 */
	public int getNumUnitDefinitions() {
		return isSetListOfUnitDefinitions() ? listOfUnitDefinitions.size() : 0;
	}

	/**
	 * 
	 * @return
	 */
	public int getNumUnits() {
		int count = 0;
		for (UnitDefinition ud : getListOfUnitDefinitions()) {
			count += ud.getNumUnits();
		}
		return count;
	}

	/**
	 * 
	 * @return
	 */
	public int getNumVariables() {
		return getNumSymbols() + getNumSpeciesReferences();
	}

	/**
	 * Gets the nth Parameter object in this Model.
	 * 
	 * @param n
	 *            index
	 * @return the nth Parameter of this Model.
	 */
	public Parameter getParameter(int n) {
		return getListOfParameters().get(n);
	}

	/**
	 * 
	 * @param id
	 * @return the Parameter of the listOfParameters which has 'id' as id (or
	 *         name depending on the level and version). Null if it doesn't
	 *         exist.
	 */
	public Parameter getParameter(String id) {
		return getListOfParameters().firstHit(new NameFilter(id));
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getParent()
	 */
	@Override
	public SBMLDocument getParent() {
		return (SBMLDocument) super.getParent();
	}

	/**
	 * Returns a UnitDefinition representing one of the predefined units of SBML, returns null if
	 * the given unit kind is not a valid one for the SBML level and version of this <code>Model</code>.
	 * 
	 * @param unitKind a unit kind for one of the predefined units from the SBML Specifications
	 * @return a UnitDefinition representing one of the predefined units of SBML, null if the unitKind 
	 * is invalid.
	 */
	public UnitDefinition getPredefinedUnitDefinition(String unitKind) {
		for (UnitDefinition unitDefinition : listOfPredefinedUnitDefinitions) {
			if (unitDefinition.getId().equals(unitKind)) {
				return unitDefinition;
			}
		}
		return null;
	}

	/**
	 * Gets the n-th Reaction object in this Model.
	 * 
	 * @param reactionIndex
	 * @return the n-th Reaction of this Model.
	 */
	public Reaction getReaction(int n) {
		return getListOfReactions().get(n);
	}

	/**
	 * 
	 * @param id
	 * @return the Reaction of the listOfReactions which has 'id' as id (or name
	 *         depending on the level and version). Null if it doesn't exist.
	 */
	public Reaction getReaction(String id) {
		return getListOfReactions().firstHit(new NameFilter(id));
	}

	/**
	 * 
	 * @param n
	 * @return the nth Rule of the listOfRules. Null if it doesn't exist.
	 */
	public Rule getRule(int n) {
		return getListOfRules().get(n);
	}

	/**
	 * Gets the n-th Species object in this Model.
	 * 
	 * @param n
	 *            the nth Species of this Model.
	 * @return the species with the given index if it exists.
	 */
	public Species getSpecies(int n) {
		return getListOfSpecies().get(n);
	}

	/**
	 * 
	 * @param id
	 * @return the Species of the listOfSpecies which has 'id' as id (or name
	 *         depending on the level and version). Null if it doesn't exist.
	 */
	public Species getSpecies(String id) {
		return getListOfSpecies().firstHit(new NameFilter(id));
	}

	/**
	 * Gets the nth SpeciesType object in this Model.
	 * 
	 * @param n
	 *            index
	 * @return the nth SpeciesType of this Model. Returns null if there are no
	 *         speciesType defined or if the index n is too big or lower than
	 *         zero.
	 */
	@Deprecated
	public SpeciesType getSpeciesType(int n) {
		return getListOfSpeciesTypes().get(n);
	}

	/**
	 * 
	 * @param id
	 * @return the SpeciesType of the listOfSpeciesTypes which has 'id' as id
	 *         (or name depending on the level and version). Null if it doesn't
	 *         exist.
	 */
	@Deprecated
	public SpeciesType getSpeciesType(String id) {
		return getListOfSpeciesTypes().firstHit(new NameFilter(id));
	}

	/**
	 * 
	 * @return the substanceUnitsID of this model. Returns the empty {@link String} if
	 *         it is not set.
	 */
	public String getSubstanceUnits() {
		return isSetSubstanceUnits() ? this.substanceUnitsID : "";
	}

	/**
	 * 
	 * @return the UnitDefinitions which has the substanceUnitsID of this Model
	 *         as id. Null if it doesn't exist.
	 */
	public UnitDefinition getSubstanceUnitsInstance() {
		return getUnitDefinition(this.substanceUnitsID);
	}

	/**
	 * 
	 * @return the timeUnitsID of this {@link Model}. Returns an empty {@link String} if it is
	 *         not set.
	 */
	public String getTimeUnits() {
		return isSetTimeUnits() ? timeUnitsID : "";
	}

	/**
	 * Gets the UnitDefinition representing the TimeUnits of this {@link Model}.
	 * 
	 * @return the {@link UnitDefinition} representing the TimeUnits of this
	 *         {@link Model}, null if it is not defined in this {@link Model}
	 */
	public UnitDefinition getTimeUnitsInstance() {
		return getUnitDefinition(this.timeUnitsID);
	}

	/**
	 * Gets the nth UnitDefinition object in this Model.
	 * 
	 * @param n
	 * @return the nth UnitDefinition of this Model. Returns null if there are
	 *         no UnitDefinition defined or if the index n is too big or lower
	 *         than zero.
	 */
	public UnitDefinition getUnitDefinition(int n) {
		return getListOfUnitDefinitions().get(n);
	}

	/**
	 * Returns the UnitDefinition of the listOfUnitDefinitions which has 'id' as
	 *         id. If no UnitDefinition are found, we check in the listOfPredefinedUnitDefinition.
	 *         If we still did not find a UnitDefinition, null is returned.
	 * 
	 * @param id
	 * @return the UnitDefinition of the listOfUnitDefinitions which has 'id' as
	 *         id (or name depending on the level and version). Null if it
	 *         doesn't exist.
	 */
	public UnitDefinition getUnitDefinition(String id) {

		UnitDefinition unitDefinition = getListOfUnitDefinitions().firstHit(
				new NameFilter(id));

		// Checking if it is not one of the predefined default units.
		if (unitDefinition == null) {
			unitDefinition = getPredefinedUnitDefinition(id);
		}

		return unitDefinition;
	}

	/**
	 * 
	 * @return the volumeUnitsID of this Model. Returns an empty String if it is
	 *         not set.
	 */
	public String getVolumeUnits() {
		return isSetVolumeUnits() ? volumeUnitsID : "";
	}

	/**
	 * 
	 * @return the UnitDefinition instance which has the volumeUnitsID of this
	 *         Model as id. Null if it doesn't exist
	 */
	public UnitDefinition getVolumeUnitsInstance() {
		return getUnitDefinition(this.volumeUnitsID);
	}

	/**
	 * Convenient method to check whether this model has a reference to the unit
	 * with the given identifier.
	 * 
	 * @param id
	 * @return
	 */
	public boolean hasUnit(String id) {
		return getUnitDefinition(id) != null;
	}

	/**
	 * This method ensures that for all constructors the same basic settings are
	 * made upon creation of new instances of this type.
	 */
	public void initDefaults() {
		listOfPredefinedUnitDefinitions = new ArrayList<UnitDefinition>(5);
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
		switch (getLevel()) {
		case 1:
			listOfUnitDefinitions = ListOf.newInstance(this, UnitDefinition.class);
			ud = UnitDefinition.substance(getLevel(), getVersion());
			substanceUnitsID = ud.getId();
			ud = UnitDefinition.time(getLevel(), getVersion());
			timeUnitsID = ud.getId();
			ud = UnitDefinition.volume(getLevel(), getVersion());
			volumeUnitsID = ud.getId();
			areaUnitsID = null;
			lengthUnitsID = null;
			extentUnitsID = null;
			conversionFactorID = null;
			break;
		case 2:

			// substance
			ud = UnitDefinition.substance(getLevel(), getVersion());
			substanceUnitsID = ud.getId();
			listOfPredefinedUnitDefinitions.add(ud);

			// volume
			ud = UnitDefinition.volume(getLevel(), getVersion());
			volumeUnitsID = ud.getId();
			listOfPredefinedUnitDefinitions.add(ud);

			// area
			ud = UnitDefinition.area(getLevel(), getVersion());
			areaUnitsID = ud.getId();
			listOfPredefinedUnitDefinitions.add(ud);

			// length
			ud = UnitDefinition.length(getLevel(), getVersion());
			lengthUnitsID = ud.getId();
			listOfPredefinedUnitDefinitions.add(ud);

			// time
			ud = UnitDefinition.time(getLevel(), getVersion());
			timeUnitsID = ud.getId();
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

	/**
	 * 
	 * @return true if the areaUnitsID of this Model is not null.
	 */
	public boolean isSetAreaUnits() {
		return this.areaUnitsID != null;
	}

	/**
	 * 
	 * @return true if the UnitDefinition which has the areaUnitsID of this
	 *         Model as id is not null.
	 */
	public boolean isSetAreaUnitsInstance() {
		return isSetListOfUnitDefinitions() && (getUnitDefinition(this.areaUnitsID) != null);
	}

	/**
	 * 
	 * @return true if the conversionFactorID of this Model is not null.
	 */
	public boolean isSetConversionFactor() {
		return this.conversionFactorID != null;
	}

	/**
	 * 
	 * @return true if the Parameter which has the conversionFactorID of this
	 *         Model as id is not null.
	 */
	public boolean isSetConversionFactorInstance() {
		return getUnitDefinition(this.conversionFactorID) != null;
	}

	/**
	 * 
	 * @return true if the extentUnitsID of this Model is not null.
	 */
	public boolean isSetExtentUnits() {
		return this.extentUnitsID != null;
	}

	/**
	 * 
	 * @return true if the UnitDefinition which has the extentUnitsID of this
	 *         Model as id is not null.
	 */
	public boolean isSetExtentUnitsInstance() {
		return isSetListOfUnitDefinitions() && (getUnitDefinition(this.extentUnitsID) != null);
	}

	/**
	 * 
	 * @return true if the lengthUnitsID of this Model is not null.
	 */
	public boolean isSetLengthUnits() {
		return this.lengthUnitsID != null;
	}

	/**
	 * 
	 * @return true if the UnitDefinition which has the lengthUnitsID of this
	 *         Model as id is not null.
	 */
	public boolean isSetLengthUnitsInstance() {
		return isSetListOfUnitDefinitions() && (getUnitDefinition(this.lengthUnitsID) != null);
	}

	/**
	 * 
	 * @return true if the listOfCompartments of this Model is not null and not
	 *         empty.
	 */
	public boolean isSetListOfCompartments() {
		return (listOfCompartments != null) && (listOfCompartments.size() > 0);
	}

	/**
	 * 
	 * @return true if the listOfCompartmentTypes of this Model is not null and
	 *         not empty.
	 * @deprecated
	 */
	@Deprecated
	public boolean isSetListOfCompartmentTypes() {
		return (listOfCompartmentTypes != null)
				&& (listOfCompartmentTypes.size() > 0);
	}

	/**
	 * 
	 * @return true if the listOfConstraints of this Model is not null and not
	 *         empty.
	 */
	public boolean isSetListOfConstraints() {
		return (listOfConstraints != null) && (listOfConstraints.size() > 0);
	}

	/**
	 * 
	 * @return true if the listOfEvents of this Model is not null and not empty.
	 */
	public boolean isSetListOfEvents() {
		return (listOfEvents != null) && (listOfEvents.size() > 0);
	}

	/**
	 * 
	 * @return true if the listOfFunctionDefinitions of this Model is not null
	 *         and not empty.
	 */
	public boolean isSetListOfFunctionDefinitions() {
		return (listOfFunctionDefinitions != null)
				&& (listOfFunctionDefinitions.size() > 0);
	}

	/**
	 * 
	 * @return true if the listOfInitialAssignments of this Model is not null
	 *         and not empty.
	 */
	public boolean isSetListOfInitialAssignments() {
		return (listOfInitialAssignments != null)
				&& (listOfInitialAssignments.size() > 0);
	}

	/**
	 * 
	 * @return true if the listOfParameters of this Model is not null and not
	 *         empty.
	 */
	public boolean isSetListOfParameters() {
		return (listOfParameters != null) && (listOfParameters.size() > 0);
	}

	/**
	 * 
	 * @return true if the listOfReactions of this Model is not null and not
	 *         empty.
	 */
	public boolean isSetListOfReactions() {
		return (listOfReactions != null) && (listOfReactions.size() > 0);
	}

	/**
	 * 
	 * @return true if the listOfRules of this Model is not null and not empty.
	 */
	public boolean isSetListOfRules() {
		return (listOfRules != null) && (listOfRules.size() > 0);
	}

	/**
	 * 
	 * @return true if the listOfSpecies of this Model is not null and not
	 *         empty.
	 */
	public boolean isSetListOfSpecies() {
		return (listOfSpecies != null) && (listOfSpecies.size() > 0);
	}

	/**
	 * 
	 * @return true if the listOfSpeciesTypes of this Model is not null and not
	 *         empty.
	 * @deprecated
	 */
	@Deprecated
	public boolean isSetListOfSpeciesTypes() {
		return (listOfSpeciesTypes != null) && (listOfSpeciesTypes.size() > 0);
	}

	/**
	 * 
	 * @return true if the listOfUnitDefinitions of this Model is not null and
	 *         not empty.
	 */
	public boolean isSetListOfUnitDefinitions() {
		return (listOfUnitDefinitions != null)
				&& (listOfUnitDefinitions.size() > 0);
	}

	/**
	 * This is equivalent to the call {@link SBase#isSetHistory()}.
	 * 
	 * @return
	 * @deprecated use {@link SBase#isSetHistory()}
	 */
	@Deprecated
	public boolean isSetModelHistory() {
		return isSetHistory();
	}

	/**
	 * 
	 * @return true if the substanceUnitsID of this Model is not null.
	 */
	public boolean isSetSubstanceUnits() {
		return this.substanceUnitsID != null;
	}

	/**
	 * 
	 * @return true if the UnitDefinition which has the substanceUnitsID of this
	 *         Model as id is not null.
	 */
	public boolean isSetSubstanceUnitsInstance() {
		return isSetListOfUnitDefinitions() && (getUnitDefinition(this.substanceUnitsID) != null);
	}

	/**
	 * 
	 * @return true if the timeUnitsID of this Model is not null.
	 */
	public boolean isSetTimeUnits() {
		return this.timeUnitsID != null;
	}

	/**
	 * 
	 * @return true if the UnitsDefinition which has the timeUnistID of this
	 *         Model as id is not null.
	 */
	public boolean isSetTimeUnitsInstance() {
		return isSetListOfUnitDefinitions() && (getUnitDefinition(this.timeUnitsID) != null);
	}

	/**
	 * 
	 * @return true if the volumeUnitsID of this Model is not null.
	 */
	public boolean isSetVolumeUnits() {
		return this.volumeUnitsID != null;
	}

	/**
	 * 
	 * @return true if the UnitDefinition which has the volumeUnitsID of this
	 *         Model as id is not null.
	 */
	public boolean isSetVolumeUnitsInstance() {
		return isSetListOfUnitDefinitions() && (getUnitDefinition(this.volumeUnitsID) != null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#readAttribute(String attributeName,
	 * String prefix, String value)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix,
			String value) {
		boolean isAttributeRead = super.readAttribute(attributeName, prefix,
				value);

		if (!isAttributeRead) {
			if (attributeName.equals("substanceUnits") && getLevel() > 2) {
				this.setSubstanceUnits(value);
			} else if (attributeName.equals("timeUnits") && getLevel() > 2) {
				this.setTimeUnits(value);
			} else if (attributeName.equals("volumeUnits") && getLevel() > 2) {
				this.setVolumeUnits(value);
			} else if (attributeName.equals("areaUnits") && getLevel() > 2) {
				this.setAreaUnits(value);
			} else if (attributeName.equals("lengthUnits") && getLevel() > 2) {
				this.setLengthUnits(value);
			} else if (attributeName.equals("extentUnits") && getLevel() > 2) {
				this.setExtentUnits(value);
			} else if (attributeName.equals("conversionFactor")
					&& getLevel() > 2) {
				this.setConversionFactor(value);
			}
		}
		return isAttributeRead;
	}

	/**
	 * 
	 * @param i
	 * @return
	 */
	public Compartment removeCompartment(int i) {
		return getListOfCompartments().remove(i);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Compartment removeCompartment(String id) {
		return getListOfCompartments().removeFirst(new NameFilter(id));
	}

	/**
	 * 
	 * @param n
	 * @return
	 */
	@Deprecated
	public CompartmentType removeCompartmentType(int n) {
		return getListOfCompartmentTypes().remove(n);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@Deprecated
	public CompartmentType removeCompartmentType(String id) {
		return getListOfCompartmentTypes().removeFirst(new NameFilter(id));
	}

	/**
	 * 
	 * @param n
	 * @return
	 */
	public Constraint removeConstraint(int n) {
		return getListOfConstraints().remove(n);
	}

	/**
	 * 
	 * @param n
	 * @return
	 */
	public Event removeEvent(int n) {
		return getListOfEvents().remove(n);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Event removeEvent(String id) {
		return getListOfEvents().removeFirst(new NameFilter(id));
	}

	/**
	 * 
	 * @param n
	 * @return
	 */
	public FunctionDefinition removeFunctionDefinition(int n) {
		return getListOfFunctionDefinitions().remove(n);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public FunctionDefinition removeFunctionDefinition(String id) {
		return getListOfFunctionDefinitions().removeFirst(new NameFilter(id));
	}

	/**
	 * 
	 * @param n
	 * @return
	 */
	public InitialAssignment removeInitialAssignment(int n) {
		return getListOfInitialAssignments().remove(n);
	}

	/**
	 * 
	 * @param n
	 * @return
	 */
	public Parameter removeParameter(int n) {
		return getListOfParameters().remove(n);
	}

	/**
	 * Removes the Parameter 'parameter' from this Model.
	 * 
	 * @param parameter
	 * @return
	 */
	public boolean removeParameter(Parameter parameter) {
		return getListOfParameters().remove(parameter);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Parameter removeParameter(String id) {
		return getListOfParameters().removeFirst(new NameFilter(id));
	}

	/**
	 * 
	 * @param n
	 * @return
	 */
	public Reaction removeReaction(int n) {
		return getListOfReactions().remove(n);
	}

	/**
	 * removes a reaction from the model
	 * 
	 * @param reac
	 * @return
	 */
	public boolean removeReaction(Reaction reac) {
		return getListOfReactions().remove(reac);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Reaction removeReaction(String id) {
		return getListOfReactions().removeFirst(new NameFilter(id));
	}

	/**
	 * 
	 * @param i
	 * @return
	 */
	public Rule removeRule(int i) {
		return getListOfRules().remove(i);
	}

	/**
	 * 
	 * @param i
	 * @return
	 */
	public Species removeSpecies(int i) {
		return getListOfSpecies().remove(i);
	}

	/**
	 * removes a species from the model
	 * 
	 * @param spec
	 * @return success
	 */
	public boolean removeSpecies(Species spec) {
		return getListOfSpecies().remove(spec);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Species removeSpecies(String id) {
		return getListOfSpecies().removeFirst(new NameFilter(id));
	}

	/**
	 * 
	 * @param n
	 * @return
	 */
	@Deprecated
	public SpeciesType removeSpeciesType(int n) {
		return getListOfSpeciesTypes().remove(n);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@Deprecated
	public SpeciesType removeSpeciesType(String id) {
		return getListOfSpeciesTypes().removeFirst(new NameFilter(id));
	}

	/**
	 * 
	 * @param n
	 * @return
	 */
	public UnitDefinition removeUnitDefinition(int n) {
		return getListOfUnitDefinitions().remove(n);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public UnitDefinition removeUnitDefinition(String id) {
		return getListOfUnitDefinitions().removeFirst(new NameFilter(id));
	}

	/**
	 * 
	 * @param unitDefininition
	 * @return true if the UnitDefinition 'unitDefinition' has been removed from
	 *         the Model.
	 */
	public boolean removeUnitDefinition(UnitDefinition unitDefininition) {
		return getListOfUnitDefinitions().remove(unitDefininition);
	}

	/**
	 * Sets the areaUnitsID of this {@link Model} to 'areaUnitsID'
	 * 
	 * @param areaUnitsID
	 */
	public void setAreaUnits(String areaUnitsID) {
		String oldAreaUnitsID = this.areaUnitsID;
		this.areaUnitsID = areaUnitsID;
		firePropertyChange(SBaseChangedEvent.areaUnits, oldAreaUnitsID, areaUnitsID);
	}

	/**
	 * Sets the areaUnitsID of this {@link Model} to the id of the {@link UnitDefinition}
	 * 'areaUnits'.
	 * 
	 * @param areaUnits
	 */
	public void setAreaUnits(UnitDefinition areaUnits) {
		setAreaUnits(areaUnits != null ? areaUnits.getId() : null);
	}

	/**
	 * Sets the conversionFactorID of this {@link Model} to the id of the {@link Parameter}
	 * 'conversionFactor'.
	 * 
	 * @param conversionFactor
	 */
	public void setConversionFactor(Parameter conversionFactor) {
		setConversionFactor(conversionFactor != null ? conversionFactor.getId()
				: null);
	}

	/**
	 * Sets the conversionFactorID of this {@link Model} to 'conversionFactorID'.
	 * 
	 * @param conversionFactorID
	 */
	public void setConversionFactor(String conversionFactorID) {
		if (getLevel() < 3) {
			throw new IllegalArgumentException(JSBML.propertyUndefinedMessage(
					SBaseChangedEvent.conversionFactor, this));
		}
		String oldConversionFactorID = this.conversionFactorID;
		this.conversionFactorID = conversionFactorID;
		firePropertyChange(SBaseChangedEvent.conversionFactor, oldConversionFactorID, conversionFactorID);
	}

	/**
	 * Sets the extendUnitsID of this {@link Model} to 'extentUnitsID'.
	 * 
	 * @param extentUnitsID
	 */
	public void setExtentUnits(String extentUnitsID) {
		String oldExtentUnits = this.extentUnitsID;
		this.extentUnitsID = extentUnitsID;
		firePropertyChange(SBaseChangedEvent.extentUnits, oldExtentUnits, extentUnitsID);
	}

	/**
	 * Sets the extentUnitsID of this {@link Model} to the id of the {@link UnitDefinition}
	 * 'extentUnits'.
	 * 
	 * @param extentUnits
	 */
	public void setExtentUnits(UnitDefinition extentUnits) {
		setExtentUnits(extentUnits != null ? extentUnits.getId() : null);
	}

	/**
	 * Sets the lengthUnitsID of this {@link Model} to 'lengthUnitsID'.
	 * 
	 * @param lengthUnitsID
	 */
	public void setLengthUnits(String lengthUnitsID) {
		String oldLengthUnits = this.lengthUnitsID;
		this.lengthUnitsID = lengthUnitsID;
		firePropertyChange(SBaseChangedEvent.lengthUnits, oldLengthUnits, lengthUnitsID);
	}

	/**
	 * Sets the lengthUnitsID of this {@link Model} to the id of the UnitDefinition
	 * 'lengthUnits'.
	 * 
	 * @param lengthUnits
	 */
	public void setLengthUnits(UnitDefinition lengthUnits) {
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
		this.listOfCompartments = JSBML.setListOf(this,
				this.listOfCompartments, listOfCompartments,
				ListOf.Type.listOfCompartments);
	}

	/**
	 * Sets the listOfCompartmentTypes of this Model to
	 * 'listOfCompartmentTypes'. Automatically sets the parentSBML objects of
	 * 'listOfCompartmentTypes' to this {@link Model}.
	 * 
	 * @param listOfCompartmentTypes
	 *            the listOfCompartmentTypes to set
	 * @deprecated
	 */
	@Deprecated
	public void setListOfCompartmentTypes(
			ListOf<CompartmentType> listOfCompartmentTypes) {
		this.listOfCompartmentTypes = JSBML.setListOf(this,
				this.listOfCompartmentTypes, listOfCompartmentTypes,
				ListOf.Type.listOfCompartmentTypes);
	}

	/**
	 * Sets the listOfConstraints of this {@link Model} to 'listOfConstraints'.
	 * Automatically sets the parentSBML objects of 'listOfCanstraints' to this
	 * Model.
	 * 
	 * @param listOfConstraints
	 *            the listOfConstraints to set
	 */
	public void setListOfConstraints(ListOf<Constraint> listOfConstraints) {
		this.listOfConstraints = JSBML.setListOf(this, this.listOfConstraints,
				listOfConstraints, ListOf.Type.listOfConstraints);
	}

	/**
	 * Sets the listOfEvents of this {@link Model} to 'listOfEvents'. Automatically sets
	 * the parentSBML objects of 'listOfEvents' to this {@link Model}.
	 * 
	 * @param listOfEvents
	 */
	public void setListOfEvents(ListOf<Event> listOfEvents) {
		this.listOfEvents = JSBML.setListOf(this, this.listOfEvents,
				listOfEvents, ListOf.Type.listOfEvents);
	}

	/**
	 * Sets the listOfFunctionDefinitions of this {@link Model} to
	 * 'listOfFunctionDefinitions'. Automatically sets the parentSBML objects of
	 * 'listOfFunctionDefinitions' to this Model.
	 * 
	 * @param listOfFunctionDefinitions
	 *            the listOfFunctionDefinitions to set
	 */
	public void setListOfFunctionDefinitions(
			ListOf<FunctionDefinition> listOfFunctionDefinitions) {
		this.listOfFunctionDefinitions = JSBML.setListOf(this,
				this.listOfFunctionDefinitions, listOfFunctionDefinitions,
				ListOf.Type.listOfFunctionDefinitions);
	}

	/**
	 * Sets the listOfInitialAssignments of this {@link Model} to
	 * 'listOfInitialAssignments'. Automatically sets the parentSBML objects of
	 * 'listOfInitialAssignments' to this Model.
	 * 
	 * @param listOfInitialAssignments
	 *            the listOfInitialAssignments to set
	 */
	public void setListOfInitialAssignments(
			ListOf<InitialAssignment> listOfInitialAssignments) {
		this.listOfInitialAssignments = JSBML.setListOf(this,
				this.listOfInitialAssignments, listOfInitialAssignments,
				ListOf.Type.listOfInitialAssignments);
	}

	/**
	 * Sets the listOfParameters of this {@link Model} to 'listOfParameters'.
	 * Automatically sets the parentSBML objects of 'listOfParameters' to this
	 * Model.
	 * 
	 * @param listOfParameters
	 */
	public void setListOfParameters(ListOf<Parameter> listOfParameters) {
		this.listOfParameters = JSBML.setListOf(this, this.listOfParameters,
				listOfParameters, ListOf.Type.listOfParameters);
	}

	/**
	 * Sets the listOfReactions of this {@link Model} to 'listOfReactions'.
	 * Automatically sets the parentSBML objects of 'listOfReactions' to this
	 * Model.
	 * 
	 * @param listOfReactions
	 */
	public void setListOfReactions(ListOf<Reaction> listOfReactions) {
		this.listOfReactions = JSBML.setListOf(this, this.listOfReactions,
				listOfReactions, ListOf.Type.listOfReactions);
	}

	/**
	 * Sets the listOfRules of this {@link Model} to 'listOfRules'. Automatically sets
	 * the parentSBML objects of 'listOfRules' to this Model.
	 * 
	 * @param listOfRules
	 */
	public void setListOfRules(ListOf<Rule> listOfRules) {
		this.listOfRules = JSBML.setListOf(this, this.listOfRules,
				listOfRules, ListOf.Type.listOfRules);
	}

	/**
	 * Sets the listOfSpecies of this {@link Model} to 'listOfSpecies'. Automatically
	 * sets the parentSBML objects of 'listOfSpecies' to this Model.
	 * 
	 * @param listOfSpecies
	 */
	public void setListOfSpecies(ListOf<Species> listOfSpecies) {
		this.listOfSpecies = JSBML.setListOf(this, this.listOfSpecies,
				listOfSpecies, ListOf.Type.listOfSpecies);
	}

	/**
	 * Sets the listOfSpeciesTypes of this Model to 'listOfSpeciesTypes'.
	 * Automatically sets the parentSBML objects of 'listOfSpeciesTypes' to this
	 * Model.
	 * 
	 * @param listOfSpeciesTypes
	 *            the listOfSpeciesTypes to set
	 */
	@Deprecated
	public void setListOfSpeciesTypes(ListOf<SpeciesType> listOfSpeciesTypes) {
		this.listOfSpeciesTypes = JSBML.setListOf(this,
				this.listOfSpeciesTypes, listOfSpeciesTypes,
				ListOf.Type.listOfSpeciesTypes);
	}

	/**
	 * Sets the listOfUnitDefinitions of this {@link Model} to 'listOfUnitDefinitions'.
	 * Automatically sets the parentSBML objects of 'listOfUnitDefinitions' to
	 * this Model.
	 * 
	 * @param listOfUnitDefinitions
	 */
	public void setListOfUnitDefinitions(
			ListOf<UnitDefinition> listOfUnitDefinitions) {
		this.listOfUnitDefinitions = JSBML.setListOf(this,
				this.listOfUnitDefinitions, listOfUnitDefinitions,
				ListOf.Type.listOfUnitDefinitions);
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
	 * Sets the substanceUnitsID of this {@link Model} to 'substanceUnitsID'
	 * 
	 * @param substanceUnitsID
	 */
	public void setSubstanceUnits(String substanceUnitsID) {
		String oldSubstanceUnitsID = this.substanceUnitsID;
		this.substanceUnitsID = substanceUnitsID;
		firePropertyChange(SBaseChangedEvent.substanceUnits, oldSubstanceUnitsID, substanceUnitsID);
	}

	/**
	 * Sets the substanceUnitsID of this {@link Model} to the id of 'substanceUnits'.
	 * 
	 * @param substanceUnits
	 */
	public void setSubstanceUnits(UnitDefinition substanceUnits) {
		setSubstanceUnits(substanceUnits != null ? substanceUnits.getId()
				: null);
	}

	/**
	 * Sets the timeUnits of this {@link Model} to 'timeUnistID'
	 * 
	 * @param timeUnitsID
	 */
	public void setTimeUnits(String timeUnitsID) {
		String oldTimeUnitsID = this.timeUnitsID;
		this.timeUnitsID = timeUnitsID;
		firePropertyChange(SBaseChangedEvent.timeUnits, oldTimeUnitsID, timeUnitsID);
	}

	/**
	 * Sets the timeUnitsID of this {@link Model} to the id of the {@link UnitDefinition}
	 * 'timeUnits'.
	 * 
	 * @param timeUnits
	 */
	public void setTimeUnits(UnitDefinition timeUnits) {
		setTimeUnits(timeUnits != null ? timeUnits.getId() : null);
	}

	/**
	 * Sets the volumeUnitsID of this {@link Model} to 'volumeUnitsID'
	 * 
	 * @param volumeUnitsID
	 */
	public void setVolumeUnits(String volumeUnitsID) {
		String oldVolumeUnitsID = this.volumeUnitsID;
		this.volumeUnitsID = volumeUnitsID;
		firePropertyChange(SBaseChangedEvent.volumeUnits, oldVolumeUnitsID, this.volumeUnitsID);
	}

	/**
	 * Sets the volumeUnitsID of this {@link Model} to the id of the {@link UnitDefinition}
	 * 'volumeUnits'.
	 * 
	 * @param volumeUnits
	 */
	public void setVolumeUnits(UnitDefinition volumeUnits) {
		setVolumeUnits(volumeUnits != null ? volumeUnits.getId() : null);
	}

	/**
	 * Sets the areaUnitsID of this {@link Model} to null.
	 */
	public void unsetAreaUnits() {
		setAreaUnits((String) null);
	}

	/**
	 * Sets the conversionFactorID of this {@link Model} to null.
	 */
	public void unsetConversionFactor() {
		setConversionFactor((String) null);
	}

	/**
	 * Sets the extentUnitsID of this {@link Model} to null.
	 */
	public void unsetExtentUnits() {
		setExtentUnits((String) null);
	}

	/**
	 * Sets the lengthUnitsID of this {@link Model} to null.
	 */
	public void unsetLengthUnits() {
		setLengthUnits((String) null);
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
	 * Sets the timeUnitsID of this {@link Model} to null.
	 */
	public void unsetTimeUnits() {
		setTimeUnits((String) null);
	}

	/**
	 * Sets the volumeUnitsID of this {@link Model} to null.
	 */
	public void unsetVolumeUnits() {
		setVolumeUnits((String) null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#writeXMLAttributes()
	 */
	@Override
	public Map<String, String> writeXMLAttributes() {
		Map<String, String> attributes = super.writeXMLAttributes();

		if (getLevel() > 2) {
			if (isSetSubstanceUnits()) {
				attributes.put("substanceUnitsID", getSubstanceUnits());
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
