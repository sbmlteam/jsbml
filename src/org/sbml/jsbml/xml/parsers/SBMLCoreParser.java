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

package org.sbml.jsbml.xml.parsers;

import java.util.ArrayList;
import java.util.HashMap;

import org.sbml.jsbml.AlgebraicRule;
import org.sbml.jsbml.Annotation;
import org.sbml.jsbml.AssignmentRule;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.CompartmentType;
import org.sbml.jsbml.Constraint;
import org.sbml.jsbml.Delay;
import org.sbml.jsbml.Event;
import org.sbml.jsbml.EventAssignment;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.InitialAssignment;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.LocalParameter;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.ModifierSpeciesReference;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.RateRule;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.Rule;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.SpeciesType;
import org.sbml.jsbml.Trigger;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.ListOf.Type;
import org.sbml.jsbml.xml.stax.ReadingParser;
import org.sbml.jsbml.xml.stax.SBMLObjectForXML;
import org.sbml.jsbml.xml.stax.WritingParser;
import org.sbml.jsbml.xml.stax.XMLLogger;

/**
 * A SBMLCoreParser is used to parse the SBML core elements of a SBML file. It
 * can read and write SBML core elements (implements ReadingParser and
 * WritingParser). It would be better to have one parser per level and version
 * rather than one SBMLCoreParser which parses everything. To change ?
 * 
 * @author marine
 * 
 */
@SuppressWarnings("deprecation")
public class SBMLCoreParser implements ReadingParser, WritingParser {

	/**
	 * The namespace URI of this parser is by default the namespaceURI of SBML
	 * level 3 version 1.
	 */
	private static final String namespaceURI = "http://www.sbml.org/sbml/level3/version1/core";

	/**
	 * This map contains all the relationships XML element name <=> matching
	 * java class.
	 */
	private HashMap<String, Class<? extends Object>> SBMLCoreElements;

	/**
	 * The logger of this parser
	 */
	private XMLLogger logger;

	/**
	 * Creates a SBMLCoreParser instance. Initializes the SBMLCoreElements of
	 * this Parser.
	 */
	public SBMLCoreParser() {
		SBMLCoreElements = new HashMap<String, Class<? extends Object>>();
		initializeCoreElements();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.WritingParser#getListOfSBMLElementsToWrite(Object
	 * sbase)
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Object> getListOfSBMLElementsToWrite(Object sbase) {
		ArrayList<Object> listOfElementsToWrite = null;
		if (sbase instanceof SBase) {
			if (sbase instanceof SBMLDocument) {
				SBMLDocument sbmlDocument = (SBMLDocument) sbase;
				if (sbmlDocument.isSetModel()) {
					listOfElementsToWrite = new ArrayList<Object>();
					listOfElementsToWrite.add(sbmlDocument.getModel());
				}
			} else if (sbase instanceof Model) {

				Model model = (Model) sbase;
				listOfElementsToWrite = new ArrayList<Object>();
				if (model.isSetListOfFunctionDefinitions()) {
					listOfElementsToWrite.add(model
							.getListOfFunctionDefinitions());
				}
				if (model.isSetListOfUnitDefinitions()) {
					listOfElementsToWrite.add(model.getListOfUnitDefinitions());
				}
				if (model.isSetListOfCompartmentTypes()) {
					listOfElementsToWrite
							.add(model.getListOfCompartmentTypes());
				}
				if (model.isSetListOfSpeciesTypes()) {
					listOfElementsToWrite.add(model.getListOfSpeciesTypes());
				}
				if (model.isSetListOfCompartments()) {
					listOfElementsToWrite.add(model.getListOfCompartments());
				}
				if (model.isSetListOfSpecies()) {
					listOfElementsToWrite.add(model.getListOfSpecies());
				}
				if (model.isSetListOfParameters()) {
					listOfElementsToWrite.add(model.getListOfParameters());
				}
				if (model.isSetListOfInitialAssignments()) {
					listOfElementsToWrite.add(model
							.getListOfInitialAssignments());
				}
				if (model.isSetListOfRules()) {
					listOfElementsToWrite.add(model.getListOfRules());
				}
				if (model.isSetListOfConstraints()) {
					listOfElementsToWrite.add(model.getListOfConstraints());
				}
				if (model.isSetListOfReactions()) {
					listOfElementsToWrite.add(model.getListOfReactions());
				}
				if (model.isSetListOfEvents()) {
					listOfElementsToWrite.add(model.getListOfEvents());
				}

				if (listOfElementsToWrite.isEmpty()) {
					listOfElementsToWrite = null;
				}
			} else if (sbase instanceof ListOf<?>) {
				ListOf<SBase> listOf = (ListOf<SBase>) sbase;

				if (!listOf.isEmpty()) {
					listOfElementsToWrite = new ArrayList<Object>();
					for (int i = 0; i < listOf.size(); i++) {
						SBase element = listOf.get(i);

						if (element != null) {
							boolean add = true;
							if (element instanceof UnitDefinition) {
								UnitDefinition ud = (UnitDefinition) element;
								if (ud.isBuiltIn()) {
									add = false;
								}
							}
							if (add) {
								listOfElementsToWrite.add(element);
							}
						}
					}
					if (listOfElementsToWrite.isEmpty()) {
						listOfElementsToWrite = null;
					}
				}
			} else if (sbase instanceof UnitDefinition) {
				UnitDefinition unitDefinition = (UnitDefinition) sbase;

				if (unitDefinition.isSetListOfUnits()) {
					listOfElementsToWrite = new ArrayList<Object>();
					listOfElementsToWrite.add(unitDefinition.getListOfUnits());
				}
			} else if (sbase instanceof Reaction) {
				Reaction reaction = (Reaction) sbase;
				listOfElementsToWrite = new ArrayList<Object>();

				if (reaction.isSetListOfReactants()) {
					listOfElementsToWrite.add(reaction.getListOfReactants());
				}
				if (reaction.isSetListOfProducts()) {
					listOfElementsToWrite.add(reaction.getListOfProducts());
				}
				if (reaction.isSetListOfModifiers()) {
					listOfElementsToWrite.add(reaction.getListOfModifiers());
				}
				if (reaction.isSetKineticLaw()) {
					listOfElementsToWrite.add(reaction.getKineticLaw());
				}

				if (listOfElementsToWrite.isEmpty()) {
					listOfElementsToWrite = null;
				}
			} else if (sbase instanceof KineticLaw) {
				KineticLaw kineticLaw = (KineticLaw) sbase;

				if (kineticLaw.isSetListOfParameters()) {
					listOfElementsToWrite = new ArrayList<Object>();
					listOfElementsToWrite.add(kineticLaw.getListOfParameters());
				}
			} else if (sbase instanceof Event) {
				Event event = (Event) sbase;
				listOfElementsToWrite = new ArrayList<Object>();

				if (event.isSetTrigger()) {
					listOfElementsToWrite.add(event.getTrigger());
				}
				if (event.isSetDelay()) {
					listOfElementsToWrite.add(event.getDelay());
				}
				if (event.isSetListOfEventAssignments()) {
					listOfElementsToWrite
							.add(event.getListOfEventAssignments());
				}

				if (listOfElementsToWrite.isEmpty()) {
					listOfElementsToWrite = null;
				}
			}

			/*
			 * HashMap<String, SBase> extentionObjects = ((SBase)
			 * sbase).getExtensionPackages();
			 * 
			 * if (extentionObjects != null && extentionObjects.size() > 0) {
			 * 
			 * for (String namespace : extentionObjects.keySet()) {
			 * 
			 * // System.out.println();
			 * 
			 * WritingParser parser = null; try { parser =
			 * SBMLWriter.getWritingPackageParsers(namespace).newInstance(); }
			 * catch (InstantiationException e) { // TODO Auto-generated catch
			 * block e.printStackTrace(); } catch (IllegalAccessException e) {
			 * // TODO Auto-generated catch block e.printStackTrace(); } SBase
			 * extendedSBase = extentionObjects.get(namespace);
			 * listOfElementsToWrite
			 * .addAll(parser.getListOfSBMLElementsToWrite(extendedSBase)); } }
			 */
		}

		return listOfElementsToWrite;
	}

	/**
	 * @return the logger
	 */
	public XMLLogger getLogger() {
		return logger;
	}

	/**
	 * 
	 * @return the namespace URI of this parser.
	 */
	public String getNamespaceURI() {
		return namespaceURI;
	}

	/**
	 * Initializes the SBMLCoreElements of this parser.
	 */
	private void initializeCoreElements() {
		// TODO : loading from a file would be better.

		SBMLCoreElements.put("model", Model.class);
		SBMLCoreElements.put("listOfFunctionDefinitions", ListOf.class);
		SBMLCoreElements.put("listOfUnitDefinitions", ListOf.class);
		SBMLCoreElements.put("listOfCompartments", ListOf.class);
		SBMLCoreElements.put("listOfSpecies", ListOf.class);
		SBMLCoreElements.put("listOfParameters", ListOf.class);
		SBMLCoreElements.put("listOfInitialAssignments", ListOf.class);
		SBMLCoreElements.put("listOfRules", ListOf.class);
		SBMLCoreElements.put("listOfConstraints", ListOf.class);
		SBMLCoreElements.put("listOfReactions", ListOf.class);
		SBMLCoreElements.put("listOfEvents", ListOf.class);
		SBMLCoreElements.put("listOfUnits", ListOf.class);
		SBMLCoreElements.put("listOfReactants", ListOf.class);
		SBMLCoreElements.put("listOfProducts", ListOf.class);
		SBMLCoreElements.put("listOfEventAssignments", ListOf.class);
		SBMLCoreElements.put("listOfModifiers", ListOf.class);
		SBMLCoreElements.put("listOfLocalParameters", ListOf.class);
		SBMLCoreElements.put("listOfCompartmentTypes", ListOf.class);
		SBMLCoreElements.put("listOfSpeciesTypes", ListOf.class);
		SBMLCoreElements.put("functionDefinition", FunctionDefinition.class);
		SBMLCoreElements.put("unitDefinition", UnitDefinition.class);
		SBMLCoreElements.put("compartment", Compartment.class);
		SBMLCoreElements.put("species", Species.class);
		SBMLCoreElements.put("specie", Species.class);
		SBMLCoreElements.put("parameter", Parameter.class);
		SBMLCoreElements.put("initialAssignment", InitialAssignment.class);
		SBMLCoreElements.put("algebraicRule", AlgebraicRule.class);
		SBMLCoreElements.put("assignmentRule", AssignmentRule.class);
		SBMLCoreElements.put("specieConcentrationRule", AssignmentRule.class);
		SBMLCoreElements.put("speciesConcentrationRule", AssignmentRule.class);
		SBMLCoreElements.put("compartmentVolumeRule", AssignmentRule.class);
		SBMLCoreElements.put("parameterRule", AssignmentRule.class);
		SBMLCoreElements.put("rateRule", RateRule.class);
		SBMLCoreElements.put("constraint", Constraint.class);
		SBMLCoreElements.put("reaction", Reaction.class);
		SBMLCoreElements.put("event", Event.class);
		SBMLCoreElements.put("annotation", Annotation.class);
		SBMLCoreElements.put("event", Event.class);
		SBMLCoreElements.put("unit", Unit.class);
		SBMLCoreElements.put("speciesReference", SpeciesReference.class);
		SBMLCoreElements.put("specieReference", SpeciesReference.class);
		SBMLCoreElements.put("modifierSpeciesReference",
				ModifierSpeciesReference.class);
		SBMLCoreElements.put("trigger", Trigger.class);
		SBMLCoreElements.put("delay", Delay.class);
		SBMLCoreElements.put("eventAssignment", EventAssignment.class);
		SBMLCoreElements.put("kineticLaw", KineticLaw.class);
		SBMLCoreElements.put("localParameter", Parameter.class);
		SBMLCoreElements.put("notes", StringBuffer.class);
		SBMLCoreElements.put("message", StringBuffer.class);
		SBMLCoreElements.put("math", StringBuffer.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processAttribute(String
	 * elementName, String attributeName, String value, String prefix, boolean
	 * isLastAttribute, Object contextObject)
	 */
	public void processAttribute(String elementName, String attributeName,
			String value, String prefix, boolean isLastAttribute,
			Object contextObject) {

		boolean isAttributeRead = false;

		// A SBMLCoreParser can modify a contextObject which is an instance of
		// SBase.
		// Try to read the attributes.
		if (contextObject instanceof SBase) {
			SBase sbase = (SBase) contextObject;
			isAttributeRead = sbase.readAttribute(attributeName, prefix, value);
		}
		// A SBMLCoreParser can modify a contextObject which is an instance of
		// Annotation.
		// Try to read the attributes.
		else if (contextObject instanceof Annotation) {
			Annotation annotation = (Annotation) contextObject;
			isAttributeRead = annotation.readAttribute(attributeName, prefix,
					value);
		}

		if (!isAttributeRead) {
			// TODO : throw new SBMLException ("The attribute " + attributeName
			// + " on the element " + elementName +
			// "is not part of the SBML specifications");
		}
	}

	/*
	 * private void setRateRuleVariable(RateRule rule, Model model){ if
	 * (rule.isSetVariable()){ String variableID = rule.getVariable();
	 * 
	 * Compartment compartment = model.getCompartment(variableID); Species
	 * species = null; SpeciesReference speciesReference = null; Parameter
	 * parameter = null;
	 * 
	 * if (compartment == null){ species = model.getSpecies(variableID);
	 * 
	 * if (species == null){ parameter = model.getParameter(variableID);
	 * 
	 * if (parameter == null){ if (model.isSetListOfReactions()){
	 * 
	 * int i = 0; SpeciesReference sr = null;
	 * 
	 * while (i <= model.getNumReactions() - 1 && sr == null){ Reaction reaction
	 * = model.getReaction(i);
	 * 
	 * if (reaction != null){ sr = reaction.getReactant(variableID); if (sr ==
	 * null){ sr = reaction.getProduct(variableID); } } }
	 * 
	 * speciesReference = sr;
	 * 
	 * if (speciesReference != null){ rule.setVariable(speciesReference); } else
	 * { // TODO : the variable ID doesn't match a SBML component, throw an
	 * exception? } } } else { rule.setVariable(parameter); } } else {
	 * rule.setVariable(species); } } else { rule.setVariable(compartment); } }
	 * }
	 * 
	 * private boolean setAssignmentRuleVariable(AssignmentRule rule, Model
	 * model){
	 * 
	 * if (rule.isSetVariable() && rule.isSetVariableInstance()){ return true; }
	 * return false; }
	 * 
	 * private void setCompartmentCompartmentType(Compartment compartment, Model
	 * model){ if (compartment.isSetCompartmentType()){ String compartmentTypeID
	 * = compartment.getCompartmentType();
	 * 
	 * CompartmentType compartmentType =
	 * model.getCompartmentType(compartmentTypeID);
	 * 
	 * if (compartmentType != null){
	 * compartment.setCompartmentType(compartmentType); } else { // TODO : the
	 * compartmentType ID doesn't match a compartment, throw an exception? } } }
	 * 
	 * private void setCompartmentOutside(Compartment compartment, Model model){
	 * 
	 * if (compartment.isSetOutside()){ String outsideID =
	 * compartment.getOutside();
	 * 
	 * Compartment outside = model.getCompartment(outsideID);
	 * 
	 * if (outside != null){ compartment.setOutside(outside); } else { // TODO :
	 * the compartment ID doesn't match a compartment, throw an exception? } } }
	 * 
	 * private void setCompartmentUnits(Compartment compartment, Model model){
	 * 
	 * if (compartment.isSetUnits()){ String unitsID = compartment.getUnits();
	 * 
	 * UnitDefinition unitDefinition = model.getUnitDefinition(unitsID);
	 * 
	 * if (unitDefinition != null){ compartment.setUnits(unitDefinition); } else
	 * { // TODO : the unitDefinition ID doesn't match a unitDefinition, throw
	 * an exception? } } }
	 * 
	 * private void setModelUnits(Model model){
	 * 
	 * if (model.isSetAreaUnits()){ String unitsID = model.getAreaUnits();
	 * 
	 * UnitDefinition unitDefinition = model.getUnitDefinition(unitsID);
	 * 
	 * if (unitDefinition != null){ model.setAreaUnits(unitDefinition); } else {
	 * // TODO : the unitDefinition ID doesn't match a unitDefinition, throw an
	 * exception? } } }
	 * 
	 * private void setEventTimeUnits(Event event, Model model){
	 * 
	 * if (event.isSetTimeUnits()){ String timeUnitsID = event.getTimeUnits();
	 * 
	 * UnitDefinition unitDefinition = model.getUnitDefinition(timeUnitsID);
	 * 
	 * if (unitDefinition != null){ event.setTimeUnits(unitDefinition); } else {
	 * // TODO : the unitDefinition ID doesn't match a unitDefinition, throw an
	 * exception? } } }
	 * 
	 * private void setEventAssignmentVariable(EventAssignment eventAssignment,
	 * Model model){
	 * 
	 * if (eventAssignment.isSetVariable()){ String variableID =
	 * eventAssignment.getVariable();
	 * 
	 * Compartment compartment = model.getCompartment(variableID); Species
	 * species = null; SpeciesReference speciesReference = null; Parameter
	 * parameter = null;
	 * 
	 * if (compartment == null){ species = model.getSpecies(variableID);
	 * 
	 * if (species == null){ parameter = model.getParameter(variableID);
	 * 
	 * if (parameter == null){ if (model.isSetListOfReactions()){
	 * 
	 * int i = 0; SpeciesReference sr = null;
	 * 
	 * while (i <= model.getNumReactions() - 1 && sr == null){ Reaction reaction
	 * = model.getReaction(i);
	 * 
	 * if (reaction != null){ sr = reaction.getReactant(variableID); if (sr ==
	 * null){ sr = reaction.getProduct(variableID); } } }
	 * 
	 * speciesReference = sr;
	 * 
	 * if (speciesReference != null){
	 * eventAssignment.setVariable(speciesReference); } else { // TODO : the
	 * variable ID doesn't match a SBML component, throw an exception? } } }
	 * else { eventAssignment.setVariable(parameter); } } else {
	 * eventAssignment.setVariable(species); } } else {
	 * eventAssignment.setVariable(compartment); } } }
	 * 
	 * private void setInitialAssignmentSymbol(InitialAssignment
	 * initialAssignment, Model model){
	 * 
	 * if (initialAssignment.isSetSymbol()){ String variableID =
	 * initialAssignment.getSymbol();
	 * 
	 * Compartment compartment = model.getCompartment(variableID); Species
	 * species = null; SpeciesReference speciesReference = null; Parameter
	 * parameter = null;
	 * 
	 * if (compartment == null){ species = model.getSpecies(variableID);
	 * 
	 * if (species == null){ parameter = model.getParameter(variableID);
	 * 
	 * if (parameter == null){ if (model.isSetListOfReactions()){
	 * 
	 * int i = 0; SpeciesReference sr = null;
	 * 
	 * while (i <= model.getNumReactions() - 1 && sr == null){ Reaction reaction
	 * = model.getReaction(i);
	 * 
	 * if (reaction != null){ sr = reaction.getReactant(variableID); if (sr ==
	 * null){ sr = reaction.getProduct(variableID); } } }
	 * 
	 * speciesReference = sr;
	 * 
	 * if (speciesReference != null){
	 * initialAssignment.setSymbol(speciesReference); } else { // TODO : the
	 * variable ID doesn't match a SBML component, throw an exception? } } }
	 * else { initialAssignment.setSymbol(parameter); } } else {
	 * initialAssignment.setSymbol(species); } } else {
	 * initialAssignment.setSymbol(compartment); } } }
	 * 
	 * private void setReactionCompartment(Reaction reaction, Model model){
	 * 
	 * if (reaction.isSetCompartment()){ String compartmentID =
	 * reaction.getCompartment();
	 * 
	 * Compartment compartment = model.getCompartment(compartmentID);
	 * 
	 * if (compartment != null){ reaction.setCompartment(compartment); } else {
	 * // TODO : the compartment ID doesn't match a compartment, throw an
	 * exception? } } }
	 * 
	 * private void setSpeciesReferenceSpecies(SimpleSpeciesReference
	 * speciesReference, Model model){
	 * 
	 * if (speciesReference.isSetSpecies()){ String speciesID =
	 * speciesReference.getSpecies();
	 * 
	 * Species species = model.getSpecies(speciesID);
	 * 
	 * if (species != null){ speciesReference.setSpecies(species); } else { //
	 * TODO : the species ID doesn't match a species, throw an exception? } } }
	 * 
	 * private void setSpeciesSubstanceUnits(Species species, Model model){
	 * 
	 * if (species.isSetSubstanceUnits()){ String substanceUnitsID =
	 * species.getSubstanceUnits();
	 * 
	 * UnitDefinition unitDefinition =
	 * model.getUnitDefinition(substanceUnitsID);
	 * 
	 * if (unitDefinition != null){ species.setSubstanceUnits(unitDefinition); }
	 * } }
	 * 
	 * private void setSpeciesConversionFactor(Species species, Model model){
	 * 
	 * if (species.isSetConversionFactor()){ String conversionFactorID =
	 * species.getConversionFactor();
	 * 
	 * Parameter parameter = model.getParameter(conversionFactorID);
	 * 
	 * if (parameter != null){ species.setConversionFactor(parameter); } else {
	 * // TODO : the parameter ID doesn't match a parameter, throw an exception?
	 * } } }
	 * 
	 * private void setSpeciesSpeciesType(Species species, Model model){
	 * 
	 * if (species.isSetSpeciesType()){ String speciesTypeID =
	 * species.getSpeciesType();
	 * 
	 * SpeciesType speciesType = model.getSpeciesType(speciesTypeID);
	 * 
	 * if (speciesType != null){ species.setSpeciesType(speciesType); } else {
	 * // TODO : the speciesType ID doesn't match a speciesType, throw an
	 * exception? } } }
	 * 
	 * private void setSpeciesCompartment(Species species, Model model){
	 * 
	 * if (species.isSetCompartment()){ String compartmentID =
	 * species.getCompartment();
	 * 
	 * Compartment compartment = model.getCompartment(compartmentID);
	 * 
	 * if (compartment != null){ species.setCompartment(compartment); } else {
	 * // TODO : the compartment ID doesn't match a compartment, throw an
	 * exception? } } }
	 * 
	 * private void setParameterUnits(Parameter parameter, Model model){
	 * 
	 * if (parameter.isSetUnits()){ String unitsID = parameter.getUnits();
	 * 
	 * UnitDefinition unitDefinition = model.getUnitDefinition(unitsID);
	 * 
	 * if (unitDefinition != null){ parameter.setUnits(unitDefinition); } else {
	 * // TODO : the unitDefinition ID doesn't match an unitDefinition, throw an
	 * exception? } } }
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processCharactersOf(String
	 * elementName, String characters, Object contextObject)
	 */
	public void processCharactersOf(String elementName, String characters,
			Object contextObject) {
		// TODO : the basic SBML elements don't have any text. SBML syntax
		// error, throw an exception?
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processEndDocument(SBMLDocument
	 * sbmlDocument)
	 */
	public void processEndDocument(SBMLDocument sbmlDocument) {

		if (sbmlDocument.isSetModel()) {
			Model model = sbmlDocument.getModel();

			if (model.isSetAreaUnits() && !model.isSetAreaUnitsInstance()) {
				// TODO : throw an exception : No unitDefinition matches the
				// areaUnitsID of Model.
			}
			if (model.isSetConversionFactor()
					&& !model.isSetConversionFactorInstance()) {
				// TODO : throw an exception : No parameter matches the
				// conversionFactorID of Model.
			}
			if (model.isSetExtentUnits() && !model.isSetExtentUnitsInstance()) {
				// TODO : throw an exception : No unitDefinition matches the
				// extentUnitsID of Model.
			}
			if (model.isSetLengthUnits() && !model.isSetLengthUnitsInstance()) {
				// TODO : throw an exception : No unitDefinition matches the
				// lengthUnitsID of Model.
			}
			if (model.isSetSubstanceUnits()
					&& !model.isSetSubstanceUnitsInstance()) {
				// TODO : throw an exception : No unitDefinition matches the
				// substanceUnitsID of Model.
			}
			if (model.isSetTimeUnits() && !model.isSetTimeUnitsInstance()) {
				// TODO : throw an exception : No unitDefinition matches the
				// timeUnitsID of Model.
			}
			if (model.isSetVolumeUnits() && !model.isSetVolumeUnitsInstance()) {
				// TODO : throw an exception : No unitDefinition matches the
				// volumeUnitsID of Model.
			}

			if (model.isSetListOfRules()) {
				for (int i = 0; i < model.getNumRules(); i++) {
					Rule rule = model.getRule(i);
					if (rule instanceof AssignmentRule) {
						AssignmentRule assignmentRule = (AssignmentRule) rule;
						if (assignmentRule.isSetVariable()
								&& !assignmentRule.isSetVariableInstance()) {
							// TODO : throw an exception : No Symbol matches the
							// variableID of AssignmentRule.
						}
						if (assignmentRule.isSetUnits()
								&& !assignmentRule.isSetUnitsInstance()
								&& assignmentRule.isParameter()) {
							// TODO : throw an exception : No UnitDefinition
							// matches the unitsID of AssignmentRule.
						}
					} else if (rule instanceof RateRule) {
						RateRule rateRule = (RateRule) rule;
						if (rateRule.isSetVariable()
								&& !rateRule.isSetVariableInstance()) {
							// TODO : throw an exception : No Symbol matches the
							// variableID of RateRule.
						}
					}
				}
			}
			if (model.isSetListOfCompartments()) {
				for (int i = 0; i < model.getNumCompartments(); i++) {
					Compartment compartment = model.getCompartment(i);
					if (compartment.isSetCompartmentType()
							&& !compartment.isSetCompartmentTypeInstance()) {
						// TODO : throw an exception : No CompartmentType
						// matches the compartmentTypeID of compartment.
					}
					if (compartment.isSetOutside()
							&& !compartment.isSetOutsideInstance()) {
						// TODO : throw an exception : No Compartment matches
						// the outsideID of compartment.
					}
					if (compartment.isSetUnits()
							&& !compartment.isSetUnitsInstance()) {
						// TODO : throw an exception : No UnitDefinition matches
						// the unitsID of compartment.
					}
				}
			}
			if (model.isSetListOfEvents()) {
				for (int i = 0; i < model.getNumEvents(); i++) {
					Event event = model.getEvent(i);

					if (event.isSetTimeUnits()
							&& !event.isSetTimeUnitsInstance()) {
						// TODO : throw an exception : No UnitDefinition matches
						// the timeUnitsID of event.
					}

					if (event.isSetListOfEventAssignments()) {

						for (int j = 0; j < event.getNumEventAssignments(); j++) {
							EventAssignment eventAssignment = event
									.getEventAssignment(j);

							if (eventAssignment.isSetVariable()
									&& !eventAssignment.isSetVariableInstance()) {
								// TODO : throw an exception : No Symbol matches
								// the variableID of eventAssignment.
							}
						}
					}
				}
			}
			if (model.isSetListOfInitialAssignments()) {
				for (int i = 0; i < model.getNumInitialAssignments(); i++) {
					InitialAssignment initialAssignment = model
							.getInitialAssignment(i);

					if (initialAssignment.isSetSymbol()
							&& !initialAssignment.isSetSymbolInstance()) {
						// TODO : throw an exception : No Symbol matches the
						// symbolID of initialAssignment.
					}
				}
			}
			if (model.isSetListOfReactions()) {
				for (int i = 0; i < model.getNumReactions(); i++) {
					Reaction reaction = model.getReaction(i);
					if (reaction.isSetCompartment()
							&& !reaction.isSetCompartmentInstance()) {
						// TODO : throw an exception : No Compartment matches
						// the compartmentID of reaction.
					}

					if (reaction.isSetListOfReactants()) {
						for (int j = 0; j < reaction.getNumReactants(); j++) {
							SpeciesReference speciesReference = reaction
									.getReactant(j);

							if (speciesReference.isSetSpecies()
									&& !speciesReference.isSetSpeciesInstance()) {
								// TODO : throw an exception : No Species
								// matches the speciesID of speciesReference.
							}
						}
					}
					if (reaction.isSetListOfProducts()) {
						for (int j = 0; j < reaction.getNumProducts(); j++) {
							SpeciesReference speciesReference = reaction
									.getProduct(j);

							if (speciesReference.isSetSpecies()
									&& !speciesReference.isSetSpeciesInstance()) {
								// TODO : throw an exception : No Species
								// matches the speciesID of speciesReference.
							}
						}
					}
					if (reaction.isSetListOfModifiers()) {
						for (int j = 0; j < reaction.getNumModifiers(); j++) {
							ModifierSpeciesReference modifierSpeciesReference = reaction
									.getModifier(j);

							if (modifierSpeciesReference.isSetSpecies()
									&& !modifierSpeciesReference
											.isSetSpeciesInstance()) {
								// TODO : throw an exception : No Species
								// matches the speciesID of
								// modifierSpeciesReference.
							}
						}
					}
					if (reaction.isSetKineticLaw()) {
						KineticLaw kineticLaw = reaction.getKineticLaw();
						if (kineticLaw.isSetTimeUnits()
								&& !kineticLaw.isSetTimeUnitsInstance()) {
							// TODO : throw an exception : No UnitDefinition
							// matches the timeUnitsID of kineticLaw.
						}
						if (kineticLaw.isSetSubstanceUnits()
								&& !kineticLaw.isSetSubstanceUnitsInstance()) {
							// TODO : throw an exception : No UnitDefinition
							// matches the substanceUnitsID of kineticLaw.
						}
						if (kineticLaw.isSetListOfParameters()) {
							for (int j = 0; j < kineticLaw.getNumParameters(); j++) {
								LocalParameter parameter = kineticLaw
										.getParameter(j);
								if (parameter.isSetUnits()
										&& !parameter.isSetUnitsInstance()) {
									// TODO : throw an exception : No
									// UnitDefinition matches the unitsID of
									// parameter.
								}
							}
						}
					}
				}
			}
			if (model.isSetListOfSpecies()) {
				for (int i = 0; i < model.getNumSpecies(); i++) {
					Species species = model.getSpecies(i);

					if (species.isSetSubstanceUnits()
							&& !species.isSetSubstanceUnitsInstance()) {
						// TODO : throw an exception : No UnitDefinition matches
						// the subtsanceUnitsID of species.
					}
					if (species.isSetSpeciesType()
							&& !species.isSetSpeciesTypeInstance()) {
						// TODO : throw an exception : No SpeciesType matches
						// the speciesTypeID of species.
					}
					if (species.isSetConversionFactor()
							&& !species.isSetConversionFactorInstance()) {
						// TODO : throw an exception : No Parameter matches the
						// conversionFactorID of species.
					}
					if (species.isSetCompartment()
							&& !species.isSetCompartmentInstance()) {
						// TODO : throw an exception : No Compartment matches
						// the compartmentID of species.
					}
					if (species.isSetSpatialSizeUnits()
							&& !species.isSetSpatialSizeUnitsInstance()) {
						// TODO : throw an exception : No UnitDefinition matches
						// the spatialSizeUnitsID of species.
					}
				}
			}
			if (model.isSetListOfParameters()) {
				for (int i = 0; i < model.getNumParameters(); i++) {
					Parameter parameter = model.getParameter(i);
					if (parameter.isSetUnits()
							&& !parameter.isSetUnitsInstance()) {
						// TODO : throw an exception : No UnitDefinition matches
						// the unitsID of parameter.
					}
				}
			}

		} else {
			// TODO : SBML syntax error, what to do?
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processEndElement(String
	 * elementName, String prefix, boolean isNested, Object contextObject)
	 */
	public void processEndElement(String elementName, String prefix,
			boolean isNested, Object contextObject) {

		if (elementName.equals("notes") && contextObject instanceof SBase) {
			SBase sbase = (SBase) contextObject;
			sbase.setNotes(sbase.getNotesBuffer().toString());
		} else if (elementName.equals("message")
				&& contextObject instanceof Constraint) {
			Constraint constraint = (Constraint) contextObject;

			if (constraint.getLevel() >= 3
					|| (constraint.getLevel() == 2 && constraint.getVersion() > 1)) {

			}
			constraint.setMessage(constraint.getMessageBuffer().toString());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processNamespace(String
	 * elementName, String URI, String prefix, String localName, boolean
	 * hasAttributes, boolean isLastNamespace, Object contextObject)
	 */
	public void processNamespace(String elementName, String URI, String prefix,
			String localName, boolean hasAttributes, boolean isLastNamespace,
			Object contextObject) {

		if (contextObject instanceof SBMLDocument) {
			SBMLDocument sbmlDocument = (SBMLDocument) contextObject;
			if (!URI.equals(namespaceURI)) {
				sbmlDocument.addNamespace(localName, prefix, URI);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processStartElement(String
	 * elementName, String prefix, boolean hasAttributes, boolean hasNamespaces,
	 * Object contextObject)
	 */
	public Object processStartElement(String elementName, String prefix,
			boolean hasAttributes, boolean hasNamespaces, Object contextObject) {

		// All the possible elements name should be present in the HashMap
		// SBMLCoreElements of this parser.
		if (SBMLCoreElements.containsKey(elementName)) {
			try {
				Object newContextObject = SBMLCoreElements.get(elementName)
						.newInstance();

				if (elementName.equals("notes")
						&& contextObject instanceof SBase) {
					SBase sbase = (SBase) contextObject;
					StringBuffer notes = (StringBuffer) newContextObject;
					sbase.setNotesBuffer(notes);
				} else if (elementName.equals("annotation")
						&& contextObject instanceof SBase) {
					SBase sbase = (SBase) contextObject;
					Annotation annotation = (Annotation) newContextObject;
					sbase.setAnnotation(annotation);

					return annotation;
				} else if (contextObject instanceof SBMLDocument) {
					SBMLDocument sbmlDocument = (SBMLDocument) contextObject;
					if (elementName.equals("model")) {
						Model model = (Model) newContextObject;
						model.setParentSBML(sbmlDocument);
						sbmlDocument.setModel(model);

						return model;
					}
				} else if (contextObject instanceof Model) {

					Model model = (Model) contextObject;
					if (newContextObject instanceof ListOf<?>) {
						if (elementName.equals("listOfFunctionDefinitions")
								&& model.getLevel() > 1) {
							ListOf listOfFunctionDefinitions = (ListOf) newContextObject;
							model
									.setListOfFunctionDefinitions(listOfFunctionDefinitions);

							return listOfFunctionDefinitions;
						} else if (elementName.equals("listOfUnitDefinitions")) {
							ListOf listOfUnitDefinitions = (ListOf) newContextObject;
							model
									.setListOfUnitDefinitions(listOfUnitDefinitions);

							return listOfUnitDefinitions;
						} else if (elementName.equals("listOfCompartments")) {
							ListOf listofCompartments = (ListOf) newContextObject;
							model.setListOfCompartments(listofCompartments);

							return listofCompartments;
						} else if (elementName.equals("listOfSpecies")) {
							ListOf listOfSpecies = (ListOf) newContextObject;
							model.setListOfSpecies(listOfSpecies);

							return listOfSpecies;
						} else if (elementName.equals("listOfParameters")) {
							ListOf listOfParameters = (ListOf) newContextObject;
							model.setListOfParameters(listOfParameters);

							return listOfParameters;
						} else if (elementName
								.equals("listOfInitialAssignments")
								&& ((model.getLevel() == 2 && model
										.getVersion() > 1) || model.getLevel() >= 3)) {
							ListOf listOfInitialAssignments = (ListOf) newContextObject;
							model
									.setListOfInitialAssignments(listOfInitialAssignments);

							return listOfInitialAssignments;
						} else if (elementName.equals("listOfRules")) {
							ListOf listOfRules = (ListOf) newContextObject;
							model.setListOfRules(listOfRules);

							return listOfRules;
						} else if (elementName.equals("listOfConstraints")
								&& ((model.getLevel() == 2 && model
										.getVersion() > 1) || model.getLevel() >= 3)) {
							ListOf listOfConstraints = (ListOf) newContextObject;
							model.setListOfConstraints(listOfConstraints);

							return listOfConstraints;
						} else if (elementName.equals("listOfReactions")) {
							ListOf listOfReactions = (ListOf) newContextObject;
							model.setListOfReactions(listOfReactions);

							return listOfReactions;
						} else if (elementName.equals("listOfEvents")
								&& model.getLevel() > 1) {
							ListOf listOfEvents = (ListOf) newContextObject;
							model.setListOfEvents(listOfEvents);

							return listOfEvents;
						} else if (elementName.equals("listOfCompartmentTypes")
								&& (model.getLevel() == 2 && model.getVersion() > 1)) {
							ListOf listOfCompartmentTypes = (ListOf) newContextObject;
							model
									.setListOfCompartmentTypes(listOfCompartmentTypes);

							return listOfCompartmentTypes;
						} else if (elementName.equals("listOfSpeciesTypes")
								&& (model.getLevel() == 2 && model.getVersion() > 1)) {
							ListOf listOfSpeciesTypes = (ListOf) newContextObject;
							model.setListOfSpeciesTypes(listOfSpeciesTypes);

							return listOfSpeciesTypes;
						} else {
							// TODO : SBML syntax error, throw an exception?
						}
					} else {
						// TODO : SBML syntax error, throw an exception?
					}
				} else if (contextObject instanceof ListOf) {
					ListOf list = (ListOf) contextObject;
					if (list.getParentSBMLObject() instanceof Model) {

						Model model = (Model) list.getParentSBMLObject();
						if (elementName.equals("functionDefinition")
								&& list.getSBaseListType().equals(
										ListOf.Type.listOfFunctionDefinitions)
								&& model.getLevel() > 1) {
							FunctionDefinition functionDefinition = (FunctionDefinition) newContextObject;
							model.addFunctionDefinition(functionDefinition);

							return functionDefinition;
						} else if (elementName.equals("unitDefinition")
								&& list.getSBaseListType().equals(
										ListOf.Type.listOfUnitDefinitions)) {
							UnitDefinition unitDefinition = (UnitDefinition) newContextObject;
							if (model.isSetLevel() && model.getLevel() < 3) {
								unitDefinition.initDefaults();
							}
							model.addUnitDefinition(unitDefinition);

							return unitDefinition;
						} else if (elementName.equals("compartment")
								&& list.getSBaseListType().equals(
										ListOf.Type.listOfCompartments)) {
							Compartment compartment = (Compartment) newContextObject;
							if (model.isSetLevel() && model.getLevel() < 3) {
								compartment.initDefaults();
							}
							model.addCompartment(compartment);

							return compartment;
						} else if (elementName.equals("species")
								&& list.getSBaseListType().equals(
										ListOf.Type.listOfSpecies)
								&& ((model.getLevel() == 1 && model
										.getVersion() > 1) || model.getLevel() > 1)) {
							Species species = (Species) newContextObject;
							if (model.isSetLevel() && model.getLevel() < 3) {
								species.initDefaults();
							}
							model.addSpecies(species);

							return species;
						}
						// level 1 : species => specie
						else if (elementName.equals("specie")
								&& list.getSBaseListType().equals(
										ListOf.Type.listOfSpecies)
								&& model.getLevel() == 1
								&& model.getVersion() == 1) {
							Species species = (Species) newContextObject;

							if (model.isSetLevel() && model.getLevel() < 3) {
								species.initDefaults();
							}
							model.addSpecies(species);

							return species;
						} else if (elementName.equals("parameter")
								&& list.getSBaseListType().equals(
										ListOf.Type.listOfParameters)) {
							Parameter parameter = (Parameter) newContextObject;
							if (model.isSetLevel() && model.getLevel() < 3) {
								parameter.initDefaults();
							}
							model.addParameter(parameter);

							return parameter;
						} else if (elementName.equals("initialAssignment")
								&& list.getSBaseListType().equals(
										ListOf.Type.listOfInitialAssignments)
								&& ((model.getLevel() == 2 && model
										.getVersion() > 1) || model.getLevel() >= 3)) {
							InitialAssignment initialAssignment = (InitialAssignment) newContextObject;
							model.addInitialAssignment(initialAssignment);

							return initialAssignment;
						} else if (elementName.equals("algebraicRule")
								&& list.getSBaseListType().equals(
										ListOf.Type.listOfRules)) {
							AlgebraicRule rule = (AlgebraicRule) newContextObject;
							model.addRule(rule);

							return rule;
						} else if (elementName.equals("assignmentRule")
								&& list.getSBaseListType().equals(
										ListOf.Type.listOfRules)
								&& model.getLevel() > 1) {
							AssignmentRule rule = (AssignmentRule) newContextObject;
							model.addRule(rule);

							return rule;
						} else if (elementName.equals("parameterRule")
								&& list.getSBaseListType().equals(
										ListOf.Type.listOfRules)
								&& model.getLevel() == 1) {
							AssignmentRule rule = (AssignmentRule) newContextObject;
							model.addRule(rule);

							return rule;
						} else if (elementName
								.equals("specieConcentrationRule")
								&& list.getSBaseListType().equals(
										ListOf.Type.listOfRules)
								&& model.getLevel() == 1
								&& model.getVersion() == 1) {
							AssignmentRule rule = (AssignmentRule) newContextObject;
							model.addRule(rule);

							return rule;
						} else if (elementName
								.equals("speciesConcentrationRule")
								&& list.getSBaseListType().equals(
										ListOf.Type.listOfRules)
								&& model.getLevel() == 1
								&& model.getVersion() == 2) {
							AssignmentRule rule = (AssignmentRule) newContextObject;
							model.addRule(rule);

							return rule;
						} else if (elementName.equals("compartmentVolumeRule")
								&& list.getSBaseListType().equals(
										ListOf.Type.listOfRules)
								&& model.getLevel() == 1) {
							AssignmentRule rule = (AssignmentRule) newContextObject;
							model.addRule(rule);

							return rule;
						} else if (elementName.equals("rateRule")
								&& list.getSBaseListType().equals(
										ListOf.Type.listOfRules)) {
							RateRule rule = (RateRule) newContextObject;
							model.addRule(rule);

							return rule;
						} else if (elementName.equals("constraint")
								&& list.getSBaseListType().equals(
										ListOf.Type.listOfConstraints)
								&& ((model.getLevel() == 2 && model
										.getVersion() > 1) || model.getLevel() >= 3)) {
							Constraint constraint = (Constraint) newContextObject;
							model.addConstraint(constraint);

							return constraint;
						} else if (elementName.equals("reaction")
								&& list.getSBaseListType().equals(
										ListOf.Type.listOfReactions)) {
							Reaction reaction = (Reaction) newContextObject;
							model.addReaction(reaction);
							if (model.isSetLevel() && model.getLevel() < 3) {
								reaction.initDefaults();
							}

							return reaction;
						} else if (elementName.equals("event")
								&& list.getSBaseListType().equals(
										ListOf.Type.listOfEvents)
								&& model.getLevel() > 1) {
							Event event = (Event) newContextObject;
							model.addEvent(event);
							if (model.isSetLevel() && model.getLevel() < 3) {
								event.initDefaults();
							}

							return event;
						} else if (elementName.equals("compartmentType")
								&& list.getSBaseListType().equals(
										ListOf.Type.listOfCompartmentTypes)
								&& (model.getLevel() == 2 && model.getVersion() > 1)) {
							CompartmentType compartmentType = (CompartmentType) newContextObject;
							model.addCompartmentType(compartmentType);

							return compartmentType;
						} else if (elementName.equals("speciesType")
								&& list.getSBaseListType().equals(
										ListOf.Type.listOfSpeciesTypes)
								&& (model.getLevel() == 2 && model.getVersion() > 1)) {
							SpeciesType speciesType = (SpeciesType) newContextObject;
							model.addSpeciesType(speciesType);

							return speciesType;
						} else {
							// TODO : SBML syntax error, throw an exception?
						}
					} else if (list.getParentSBMLObject() instanceof UnitDefinition) {
						UnitDefinition unitDefinition = (UnitDefinition) list
								.getParentSBMLObject();

						if (elementName.equals("unit")
								&& list.getSBaseListType().equals(
										ListOf.Type.listOfUnits)) {
							Unit unit = (Unit) newContextObject;
							unit.initDefaults();
							unitDefinition.addUnit(unit);

							return unit;
						} else {
							// TODO : SBML syntax error, throw an exception?
						}
					} else if (list.getParentSBMLObject() instanceof Reaction) {
						Reaction reaction = (Reaction) list
								.getParentSBMLObject();

						if (elementName.equals("speciesReference")
								&& (reaction.getLevel() > 1 || (reaction
										.getLevel() == 1 && reaction
										.getVersion() == 2))) {
							SpeciesReference speciesReference = (SpeciesReference) newContextObject;

							if (reaction.isSetLevel()
									&& reaction.getLevel() < 3) {
								speciesReference.initDefaults();
							}

							if (list.getSBaseListType().equals(
									ListOf.Type.listOfReactants)) {
								reaction.addReactant(speciesReference);

								return speciesReference;
							} else if (list.getSBaseListType().equals(
									ListOf.Type.listOfProducts)) {
								reaction.addProduct(speciesReference);

								return speciesReference;
							} else {
								// TODO : SBML syntax error, throw an exception?
							}
						} else if (elementName.equals("specieReference")
								&& reaction.getLevel() > 1) {
							SpeciesReference speciesReference = (SpeciesReference) newContextObject;
							if (reaction.isSetLevel()
									&& reaction.getLevel() < 3) {
								speciesReference.initDefaults();
							}

							if (list.getSBaseListType().equals(
									ListOf.Type.listOfReactants)) {
								reaction.addReactant(speciesReference);

								return speciesReference;
							} else if (list.getSBaseListType().equals(
									ListOf.Type.listOfProducts)) {
								reaction.addProduct(speciesReference);

								return speciesReference;
							} else {
								// TODO : SBML syntax error, throw an exception?
							}
						} else if (elementName
								.equals("modifierSpeciesReference")
								&& list.getSBaseListType().equals(
										ListOf.Type.listOfModifiers)
								&& reaction.getLevel() > 1) {
							ModifierSpeciesReference modifierSpeciesReference = (ModifierSpeciesReference) newContextObject;
							reaction.addModifier(modifierSpeciesReference);

							return modifierSpeciesReference;
						} else {
							// TODO : SBML syntax error, throw an exception?
						}
					} else if (list.getParentSBMLObject() instanceof KineticLaw) {
						KineticLaw kineticLaw = (KineticLaw) list
								.getParentSBMLObject();

						// Level 3 : parameter and listOfParameters =>
						// localParameter and listOfLocalParameter
						if (elementName.equals("localParameter")
								&& list.getSBaseListType().equals(
										ListOf.Type.listOfLocalParameters)
								&& kineticLaw.getLevel() >= 3) {
							LocalParameter localParameter = (LocalParameter) newContextObject;
							kineticLaw.addParameter(localParameter);

							return localParameter;
						} else if (elementName.equals("parameter")
								&& list.getSBaseListType().equals(
										ListOf.Type.listOfLocalParameters)
								&& kineticLaw.isSetLevel()
								&& kineticLaw.getLevel() < 3) {
							LocalParameter localParameter = new LocalParameter(
									(Parameter) newContextObject);
							kineticLaw.addParameter(localParameter);

							return localParameter;
						} else {
							// TODO : SBML syntax error, throw an exception?
						}
					} else if (list.getParentSBMLObject() instanceof Event) {
						Event event = (Event) list.getParentSBMLObject();

						if (elementName.equals("eventAssignment")
								&& list.getSBaseListType().equals(
										ListOf.Type.listOfEventAssignments)
								&& event.getLevel() > 1) {
							EventAssignment eventAssignment = (EventAssignment) newContextObject;
							event.addEventAssignment(eventAssignment);

							return eventAssignment;
						} else {
							// TODO : SBML syntax error, throw an exception?
						}
					} else {
						// TODO : SBML syntax error, throw an exception?
					}
				} else if (contextObject instanceof UnitDefinition) {
					UnitDefinition unitDefinition = (UnitDefinition) contextObject;

					if (elementName.equals("listOfUnits")) {
						ListOf<Unit> listOfUnits = (ListOf<Unit>) newContextObject;
						unitDefinition.setListOfUnits(listOfUnits);

						return listOfUnits;
					}
				} else if (contextObject instanceof Event) {
					Event event = (Event) contextObject;

					if (elementName.equals("listOfEventAssignments")
							&& event.getLevel() > 1) {
						ListOf listOfEventAssignments = (ListOf) newContextObject;
						event.setListOfEventAssignments(listOfEventAssignments);

						return listOfEventAssignments;
					} else if (elementName.equals("trigger")
							&& event.getLevel() > 1) {
						Trigger trigger = (Trigger) newContextObject;
						event.setTrigger(trigger);

						return trigger;
					} else if (elementName.equals("Delay")
							&& event.getLevel() > 1) {
						Delay delay = (Delay) newContextObject;
						event.setDelay(delay);

						return delay;
					} else {
						// TODO : SBML syntax error, throw an exception?
					}
				} else if (contextObject instanceof Reaction) {
					Reaction reaction = (Reaction) contextObject;

					if (elementName.equals("listOfReactants")) {
						ListOf listOfReactants = (ListOf) newContextObject;
						reaction.setListOfReactants(listOfReactants);

						return listOfReactants;
					} else if (elementName.equals("listOfProducts")) {
						ListOf listOfProducts = (ListOf) newContextObject;
						reaction.setListOfProducts(listOfProducts);

						return listOfProducts;
					} else if (elementName.equals("listOfModifiers")
							&& reaction.getLevel() > 1) {
						ListOf listOfModifiers = (ListOf) newContextObject;
						listOfModifiers.setSBaseListType(Type.listOfModifiers);
						// TODO : check why it is needed for listOfModifiers and
						// not the others
						// probably something wrong before
						reaction.setListOfModifiers(listOfModifiers);

						return listOfModifiers;
					} else if (elementName.equals("kineticLaw")) {
						KineticLaw kineticLaw = (KineticLaw) newContextObject;
						reaction.setKineticLaw(kineticLaw);

						return kineticLaw;
					} else {
						// TODO : SBML syntax error, throw an exception?
					}
				} else if (contextObject instanceof KineticLaw) {
					KineticLaw kineticLaw = (KineticLaw) contextObject;

					if (elementName.equals("listOfLocalParameters")
							&& kineticLaw.getLevel() >= 3) {
						ListOf listOfLocalParameters = (ListOf) newContextObject;
						kineticLaw
								.setListOfLocalParameters(listOfLocalParameters);
						listOfLocalParameters
								.setSBaseListType(ListOf.Type.listOfLocalParameters);

						return listOfLocalParameters;
					} else if (elementName.equals("listOfParameters")
							&& kineticLaw.isSetLevel()
							&& kineticLaw.getLevel() < 3) {
						ListOf listOfLocalParameters = (ListOf) newContextObject;
						kineticLaw
								.setListOfLocalParameters(listOfLocalParameters);
						listOfLocalParameters
								.setSBaseListType(ListOf.Type.listOfLocalParameters);

						return listOfLocalParameters;
					} else {
						// TODO : SBML syntax error, throw an exception?
					}
				} else if (contextObject instanceof Constraint) {
					Constraint constraint = (Constraint) contextObject;

					if (elementName.equals("message")
							&& ((constraint.getLevel() == 2 && constraint
									.getVersion() > 1) || constraint.getLevel() >= 3)) {
						StringBuffer message = new StringBuffer();
						constraint.setMessageBuffer(message);
						return constraint;
					} else {
						// TODO : SBML syntax error, throw an exception?
					}
				} else {
					// TODO : SBML syntax error, throw an exception?
				}
			} catch (InstantiationException e) {
				// TODO : SBML object can't be instantiated, throw an exception?
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO : SBML object can't be instantiated, throw an exception?
				e.printStackTrace();
			}
		}
		return contextObject;
	}

	/**
	 * @param logger
	 *            the logger to set
	 */
	public void setLogger(XMLLogger logger) {
		this.logger = logger;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.WritingParser#writeAttributes(SBMLObjectForXML
	 * xmlObject, Object sbmlElementToWrite)
	 */
	public void writeAttributes(SBMLObjectForXML xmlObject,
			Object sbmlElementToWrite) {
		if (sbmlElementToWrite instanceof SBase) {
			SBase sbase = (SBase) sbmlElementToWrite;
			xmlObject.addAttributes(sbase.writeXMLAttributes());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.WritingParser#writeCharacters(SBMLObjectForXML
	 * xmlObject, Object sbmlElementToWrite)
	 */
	public void writeCharacters(SBMLObjectForXML xmlObject,
			Object sbmlElementToWrite) {
		// TODO SBML components don't have any characters in the XML file. what
		// to do?
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.WritingParser#writeElement(SBMLObjectForXML
	 * xmlObject, Object sbmlElementToWrite)
	 */
	public void writeElement(SBMLObjectForXML xmlObject,
			Object sbmlElementToWrite) {

		if (sbmlElementToWrite instanceof SBase) {
			SBase sbase = (SBase) sbmlElementToWrite;
			if (!xmlObject.isSetName()) {
				if (sbmlElementToWrite instanceof AssignmentRule) {
					AssignmentRule assignmentRule = (AssignmentRule) sbmlElementToWrite;
					if (assignmentRule.getLevel() == 1) {
						if (assignmentRule.getVersion() == 1
								&& assignmentRule.isSpeciesConcentration()) {
							xmlObject.setName("specieConcentrationRule");
						} else if (assignmentRule.getVersion() == 2
								&& assignmentRule.isSpeciesConcentration()) {
							xmlObject.setName("speciesConcentrationRule");
						} else if (assignmentRule.isCompartmentVolume()) {
							xmlObject.setName("compartmentVolumeRule");
						} else if (assignmentRule.isParameter()) {
							xmlObject.setName("parameterRule");
						}
					} else {
						xmlObject.setName(sbase.getElementName());
					}
				} else if (sbmlElementToWrite instanceof Species) {
					Species species = (Species) sbmlElementToWrite;
					if (species.getLevel() == 1 && species.getVersion() == 1) {
						xmlObject.setName("specie");
					} else {
						xmlObject.setName(sbase.getElementName());
					}
				} else if (sbmlElementToWrite instanceof SpeciesReference) {
					SpeciesReference speciesReference = (SpeciesReference) sbmlElementToWrite;
					if (speciesReference.getLevel() == 1
							&& speciesReference.getVersion() == 1) {
						xmlObject.setName("specieReference");
					} else {
						xmlObject.setName(sbase.getElementName());
					}
				} else if (sbmlElementToWrite instanceof Parameter) {
					Parameter parameter = (Parameter) sbmlElementToWrite;

					if (parameter.getLevel() == 3) {
						// TODO: Commented, because Eclipse says
						// "Incompatible conditional operand types Model and ListOf<?>"
						/*
						 * if (parameter.getParentSBMLObject() instanceof
						 * ListOf<?>) { ListOf<Parameter> list =
						 * (ListOf<Parameter>) parameter .getParentSBMLObject();
						 * if (list.getSBaseListType() ==
						 * ListOf.Type.listOfLocalParameters) {
						 * xmlObject.setName("localParameter"); } else {
						 * xmlObject.setName(sbase.getElementName()); } } else {
						 * // TODO throw an error, all the parameters should be
						 * // included into a listOf. }
						 */
					} else {
						xmlObject.setName(sbase.getElementName());
					}
				} else if (sbmlElementToWrite instanceof ListOf<?>) {
					ListOf<SBase> list = (ListOf<SBase>) sbmlElementToWrite;

					if (list.getLevel() < 3
							&& list.getSBaseListType() == ListOf.Type.listOfLocalParameters) {
						xmlObject.setName("listOfParameters");
					} else {
						xmlObject.setName(sbase.getElementName());
					}
				} else {
					xmlObject.setName(sbase.getElementName());
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.WritingParser#writeNamespaces(SBMLObjectForXML
	 * xmlObject, Object sbmlElementToWrite)
	 */
	public void writeNamespaces(SBMLObjectForXML xmlObject,
			Object sbmlElementToWrite) {
		if (sbmlElementToWrite instanceof SBase) {
			SBase sbase = (SBase) sbmlElementToWrite;

			if (sbase instanceof SBMLDocument) {
				SBMLDocument sbmlDocument = (SBMLDocument) sbmlElementToWrite;

				xmlObject.addAttributes(sbmlDocument
						.getSBMLDocumentNamespaces());
			}

			xmlObject.setPrefix("");
		}
	}
}
