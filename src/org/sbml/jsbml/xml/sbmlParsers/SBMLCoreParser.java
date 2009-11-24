package org.sbml.jsbml.xml.sbmlParsers;

import java.util.HashMap;

import org.sbml.jsbml.element.AlgebraicRule;
import org.sbml.jsbml.element.Annotation;
import org.sbml.jsbml.element.AssignmentRule;
import org.sbml.jsbml.element.Compartment;
import org.sbml.jsbml.element.CompartmentType;
import org.sbml.jsbml.element.Constraint;
import org.sbml.jsbml.element.Delay;
import org.sbml.jsbml.element.Event;
import org.sbml.jsbml.element.EventAssignment;
import org.sbml.jsbml.element.FunctionDefinition;
import org.sbml.jsbml.element.InitialAssignment;
import org.sbml.jsbml.element.KineticLaw;
import org.sbml.jsbml.element.Model;
import org.sbml.jsbml.element.ModifierSpeciesReference;
import org.sbml.jsbml.element.Parameter;
import org.sbml.jsbml.element.RateRule;
import org.sbml.jsbml.element.Reaction;
import org.sbml.jsbml.element.Rule;
import org.sbml.jsbml.element.SBMLDocument;
import org.sbml.jsbml.element.ListOf;
import org.sbml.jsbml.element.SBase;
import org.sbml.jsbml.element.SimpleSpeciesReference;
import org.sbml.jsbml.element.Species;
import org.sbml.jsbml.element.SpeciesReference;
import org.sbml.jsbml.element.SpeciesType;
import org.sbml.jsbml.element.Trigger;
import org.sbml.jsbml.element.Unit;
import org.sbml.jsbml.element.UnitDefinition;
import org.sbml.jsbml.xml.CurrentListOfSBMLElements;
import org.sbml.jsbml.xml.SBMLParser;

public class SBMLCoreParser implements SBMLParser{
	
	private String parserNamespace = "http://www.sbml.org/sbml/level3/version1/core";
	
	private HashMap<String, Class<? extends Object>> SBMLCoreElements;
	
	public SBMLCoreParser(){
		SBMLCoreElements = new HashMap<String, Class<? extends Object>>();
		initializeCoreElements();
	}
	
	private void initializeCoreElements(){
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
		SBMLCoreElements.put("listOfReactions", ListOf.class);
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
		SBMLCoreElements.put("parameter", Parameter.class);
		SBMLCoreElements.put("initialAssignment", InitialAssignment.class);
		SBMLCoreElements.put("algebraicRule", AlgebraicRule.class);
		SBMLCoreElements.put("assignmentRule", AssignmentRule.class);
		SBMLCoreElements.put("rateRule", RateRule.class);
		SBMLCoreElements.put("constraint", Constraint.class);
		SBMLCoreElements.put("reaction", Reaction.class);
		SBMLCoreElements.put("event", Event.class);
		SBMLCoreElements.put("annotation", Annotation.class);
		SBMLCoreElements.put("event", Event.class);
		SBMLCoreElements.put("unit", Unit.class);
		SBMLCoreElements.put("algebraicRule", AlgebraicRule.class);
		SBMLCoreElements.put("assignmentRule", AssignmentRule.class);
		SBMLCoreElements.put("rateRule", RateRule.class);
		SBMLCoreElements.put("reaction", Reaction.class);
		SBMLCoreElements.put("speciesReference", SpeciesReference.class);
		SBMLCoreElements.put("modifierSpeciesReference", ModifierSpeciesReference.class);
		SBMLCoreElements.put("trigger", Trigger.class);
		SBMLCoreElements.put("delay", Delay.class);
		SBMLCoreElements.put("eventAssignment", EventAssignment.class);
		SBMLCoreElements.put("kineticLaw", KineticLaw.class);
		SBMLCoreElements.put("localParameter", Parameter.class);
		SBMLCoreElements.put("notes", StringBuffer.class);
		SBMLCoreElements.put("message", StringBuffer.class);
	}

	public void processAttribute(String elementName, String attributeName, String value,  String prefix,
			boolean isLastAttribute, Object contextObject) {
		boolean isAttributeRead = false;
		if (contextObject instanceof SBase){
			SBase sbase = (SBase) contextObject;
			isAttributeRead = sbase.readAttribute(attributeName, prefix, value);
		}
		else if (contextObject instanceof Annotation){
			Annotation annotation = (Annotation) contextObject;
			isAttributeRead = annotation.readAttribute(attributeName, prefix, value);
		}
		
		if (!isAttributeRead){
			// TODO : throw new SBMLException ("The attribute " + attributeName + " on the element " + elementName + "is not part of the SBML specifications");
		}
	}

	public void processCharactersOf(String elementName, String characters,
			Object contextObject) {	
		// TODO : the basic SBML elements don't have any text. SBML syntax error, throw an exception?
	}

	public Object processStartElement(String elementName, String prefix,
			boolean hasAttributes, Object contextObject) {

		if (SBMLCoreElements.containsKey(elementName)){
			try {
				Object newContextObject = SBMLCoreElements.get(elementName).newInstance();
				//System.out.println(elementName);
				
				if (elementName.equals("notes") && contextObject instanceof SBase){
					SBase sbase = (SBase) contextObject;
					StringBuffer notes = (StringBuffer) newContextObject;
					sbase.setNotesBuffer(notes);
				}
				else if (elementName.equals("annotation") && contextObject instanceof SBase){
					SBase sbase = (SBase) contextObject;
					Annotation annotation = (Annotation) newContextObject;
					sbase.setAnnotation(annotation);
					
					return annotation;
				}
				else if (contextObject instanceof SBMLDocument){
					SBMLDocument sbmlDocument = (SBMLDocument) contextObject;
					if (elementName.equals("model")){
						Model model = (Model) newContextObject;
						model.setParentSBML(sbmlDocument);
						sbmlDocument.setModel(model);
						
						return model;
					}
				}
				else if (contextObject instanceof Model){

					Model model = (Model) contextObject;
					if (newContextObject instanceof ListOf){
						if (elementName.equals("listOfFunctionDefinitions")){
							ListOf listOfFunctionDefinitions = (ListOf) newContextObject;
							model.setListOfFunctionDefinitions(listOfFunctionDefinitions);
							
							return listOfFunctionDefinitions;
						}
						else if (elementName.equals("listOfUnitDefinitions")){
							ListOf listOfUnitDefinitions = (ListOf) newContextObject;
							model.setListOfUnitDefinitions(listOfUnitDefinitions);	
							
							return listOfUnitDefinitions;
						}
						else if (elementName.equals("listOfCompartments")){
							ListOf listofCompartments = (ListOf) newContextObject;
							model.setListOfCompartments(listofCompartments);

							return listofCompartments;
						}
						else if (elementName.equals("listOfSpecies")){
							ListOf listOfSpecies = (ListOf) newContextObject;
							model.setListOfSpecies(listOfSpecies);
							
							return listOfSpecies;
						}
						else if (elementName.equals("listOfParameters")){
							ListOf listOfParameters = (ListOf) newContextObject;
							model.setListOfParameters(listOfParameters);	
							
							return listOfParameters;
						}
						else if (elementName.equals("listOfInitialAssignments")){
							ListOf listOfInitialAssignments = (ListOf) newContextObject;
							model.setListOfInitialAssignments(listOfInitialAssignments);
							
							return listOfInitialAssignments;
						}
						else if (elementName.equals("listOfRules")){
							ListOf listOfRules = (ListOf) newContextObject;
							model.setListOfRules(listOfRules);
							
							return listOfRules;
						}
						else if (elementName.equals("listOfConstraints")){
							ListOf listOfConstraints = (ListOf) newContextObject;
							model.setListOfConstraints(listOfConstraints);
							
							return listOfConstraints;
						}
						else if (elementName.equals("listOfReactions")){
							ListOf listOfReactions = (ListOf) newContextObject;
							model.setListOfReactions(listOfReactions);	
							
							return listOfReactions;
						}
						else if (elementName.equals("listOfEvents")){
							ListOf listOfEvents = (ListOf) newContextObject;
							model.setListOfEvents(listOfEvents);
							
							return listOfEvents;
						}
						else if (elementName.equals("listOfCompartmentTypes")){
							ListOf listOfCompartmentTypes = (ListOf) newContextObject;
							model.setListOfCompartmentTypes(listOfCompartmentTypes);
							
							return listOfCompartmentTypes;
						}
						else if (elementName.equals("listOfSpeciesTypes")){
							ListOf listOfSpeciesTypes = (ListOf) newContextObject;
							model.setListOfSpeciesTypes(listOfSpeciesTypes);
							
							return listOfSpeciesTypes;
						}
						else {
							// TODO : SBML syntax error, throw an exception?
						}
					}
					else {
						// TODO : SBML syntax error, throw an exception?
					}
				}	
				else if (contextObject instanceof ListOf){
					ListOf list = (ListOf) contextObject;
					if (list.getParentSBMLObject() instanceof Model){
						
						Model model = (Model) list.getParentSBMLObject();
						if (elementName.equals("functionDefinition") && list.getCurrentList().equals(CurrentListOfSBMLElements.listOfFunctionDefinitions)){
							FunctionDefinition functionDefinition = (FunctionDefinition) newContextObject;
							model.addFunctionDefinition(functionDefinition);
						
							return functionDefinition;
						}
						else if (elementName.equals("unitDefinition") && list.getCurrentList().equals(CurrentListOfSBMLElements.listOfUnitDefinitions)){
							UnitDefinition unitDefinition = (UnitDefinition) newContextObject;
							model.addUnitDefinition(unitDefinition);
						
							return unitDefinition;
						}
						else if (elementName.equals("compartment") && list.getCurrentList().equals(CurrentListOfSBMLElements.listOfCompartments)){
							Compartment compartment = (Compartment) newContextObject;
							model.addCompartment(compartment);
						
							return compartment;
						}
						else if (elementName.equals("species") && list.getCurrentList().equals(CurrentListOfSBMLElements.listOfSpecies)){
							Species species = (Species) newContextObject;
							model.addSpecies(species);

							return species;
						}
						else if (elementName.equals("parameter") && list.getCurrentList().equals(CurrentListOfSBMLElements.listOfParameters)){
							Parameter parameter = (Parameter) newContextObject;
							model.addParameter(parameter);
						
							return parameter;
						}
						else if (elementName.equals("initialAssignment") && list.getCurrentList().equals(CurrentListOfSBMLElements.listOfInitialAssignments)){
							InitialAssignment initialAssignment = (InitialAssignment) newContextObject;
							model.addInitialAssignment(initialAssignment);
						
							return initialAssignment;
						}
						else if (elementName.equals("algebraicRule") && list.getCurrentList().equals(CurrentListOfSBMLElements.listOfRules)){
							AlgebraicRule rule = (AlgebraicRule) newContextObject;
							model.addRule(rule);
						
							return rule;
						}
						else if (elementName.equals("assignmentRule") && list.getCurrentList().equals(CurrentListOfSBMLElements.listOfRules)){
							AssignmentRule rule = (AssignmentRule) newContextObject;
							model.addRule(rule);
						
							return rule;
						}
						else if (elementName.equals("rateRule") && list.getCurrentList().equals(CurrentListOfSBMLElements.listOfRules)){
							RateRule rule = (RateRule) newContextObject;
							model.addRule(rule);
						
							return rule;
						}
						else if (elementName.equals("constraint") && list.getCurrentList().equals(CurrentListOfSBMLElements.listOfConstraints)){
							Constraint constraint = (Constraint) newContextObject;
							model.addConstraint(constraint);
						
							return constraint;
						}
						else if (elementName.equals("reaction") && list.getCurrentList().equals(CurrentListOfSBMLElements.listOfReactions)){
							Reaction reaction = (Reaction) newContextObject;
							model.addReaction(reaction);
						
							return reaction;
						}
						else if (elementName.equals("event") && list.getCurrentList().equals(CurrentListOfSBMLElements.listOfEvents)){
							Event event = (Event) newContextObject;
							model.addEvent(event);
						
							return event;
						}
						else if (elementName.equals("compartmentType") && list.getCurrentList().equals(CurrentListOfSBMLElements.listOfCompartmentTypes)){
							CompartmentType compartmentType = (CompartmentType) newContextObject;
							model.addCompartmentType(compartmentType);
						
							return compartmentType;
						}
						else if (elementName.equals("speciesType") && list.getCurrentList().equals(CurrentListOfSBMLElements.listOfSpeciesTypes)){
							SpeciesType speciesType = (SpeciesType) newContextObject;
							model.addSpeciesType(speciesType);
						
							return speciesType;
						}
						else {
							// TODO : SBML syntax error, throw an exception?
						}
					} 
					else if (list.getParentSBMLObject() instanceof UnitDefinition){
						UnitDefinition unitDefinition = (UnitDefinition) list.getParentSBMLObject();
						
						if (elementName.equals("unit") && list.getCurrentList().equals(CurrentListOfSBMLElements.listOfUnits)){
							Unit unit = (Unit) newContextObject;
							unitDefinition.addUnit(unit);
							
							return unit;
						}
						else {
							// TODO : SBML syntax error, throw an exception?
						}
					}
					else if (list.getParentSBMLObject() instanceof Reaction){
						Reaction reaction = (Reaction) list.getParentSBMLObject();
						
						if (elementName.equals("speciesReference")){
							SpeciesReference speciesReference = (SpeciesReference) newContextObject;
							
							if (list.getCurrentList().equals(CurrentListOfSBMLElements.listOfReactants)){
								reaction.addReactant(speciesReference);
								
								return speciesReference;
							}
							else if (list.getCurrentList().equals(CurrentListOfSBMLElements.listOfProducts)){
								reaction.addProduct(speciesReference);
								
								return speciesReference;
							}
							else {
								// TODO : SBML syntax error, throw an exception?
							}
						}
						else if (elementName.equals("modifierSpeciesReference") && list.getCurrentList().equals(CurrentListOfSBMLElements.listOfModifiers)){
							ModifierSpeciesReference modifierSpeciesReference = (ModifierSpeciesReference) newContextObject;
							reaction.addModifier(modifierSpeciesReference);
								
							return modifierSpeciesReference;
						}
						else {
							// TODO : SBML syntax error, throw an exception?
						}
					}
					else if (list.getParentSBMLObject() instanceof KineticLaw){
						KineticLaw kineticLaw = (KineticLaw) list.getParentSBMLObject();
						
						if (elementName.equals("localParameter") && list.getCurrentList().equals(CurrentListOfSBMLElements.listOfLocalParameters)){
							Parameter localParameter = (Parameter) newContextObject;
							kineticLaw.addParameter(localParameter);
							
							return localParameter;
						}
						else {
							// TODO : SBML syntax error, throw an exception?
						}
					}
					else if (list.getParentSBMLObject() instanceof Event){
						Event event = (Event) list.getParentSBMLObject();
						
						if (elementName.equals("eventAssignment") && list.getCurrentList().equals(CurrentListOfSBMLElements.listOfEventAssignments)){
							EventAssignment eventAssignment = (EventAssignment) newContextObject;
							event.addEventAssignement(eventAssignment);
							
							return eventAssignment;
						}
						else {
							// TODO : SBML syntax error, throw an exception?
						}
					}
					else {
						// TODO : SBML syntax error, throw an exception?
					}
				}
				else if (contextObject instanceof Event){
					Event event = (Event) contextObject;
					
					if (elementName.equals("listOfEventAssignments")){
						ListOf listOfEventAssignments = (ListOf) newContextObject;
						event.setListOfEventAssignments(listOfEventAssignments);
						
						return listOfEventAssignments;
					}
					else if (elementName.equals("trigger")){
						Trigger trigger = (Trigger) newContextObject;
						event.setTrigger(trigger);
						
						return trigger;
					}
					else if (elementName.equals("Delay")){
						Delay delay = (Delay) newContextObject;
						event.setDelay(delay);
						
						return delay;
					}
					else {
						// TODO : SBML syntax error, throw an exception?
					}
				}
				else if (contextObject instanceof Reaction){
					Reaction reaction = (Reaction) contextObject;
					
					if (elementName.equals("listOfReactants")){
						ListOf listOfReactants = (ListOf) newContextObject;
						reaction.setListOfReactants(listOfReactants);
						listOfReactants.setCurrentList(CurrentListOfSBMLElements.listOfReactants);
						
						return listOfReactants;
					}
					else if (elementName.equals("listOfProducts")){
						ListOf listOfProducts = (ListOf) newContextObject;
						reaction.setListOfProducts(listOfProducts);
						listOfProducts.setCurrentList(CurrentListOfSBMLElements.listOfProducts);

						return listOfProducts;
					}
					else if (elementName.equals("listOfModifiers")){
						ListOf listOfModifiers = (ListOf) newContextObject;
						reaction.setListOfModifiers(listOfModifiers);
						listOfModifiers.setCurrentList(CurrentListOfSBMLElements.listOfModifiers);
						
						return listOfModifiers;
					}
					else if (elementName.equals("kineticLaw")){
						KineticLaw kineticLaw = (KineticLaw) newContextObject;
						reaction.setKineticLaw(kineticLaw);
						
						return kineticLaw;
					}
					else {
						// TODO : SBML syntax error, throw an exception?
					}
				}
				else if (contextObject instanceof KineticLaw){
					KineticLaw kineticLaw = (KineticLaw) contextObject;
					
					if (elementName.equals("listOfLocalParameters")){
						ListOf listOfLocalParameters = (ListOf) newContextObject;
						kineticLaw.setListOfLocalParameters(listOfLocalParameters);
						
						return listOfLocalParameters;
					}
					else {
						// TODO : SBML syntax error, throw an exception?
					}
				}
				else if (contextObject instanceof Constraint){
					Constraint constraint = (Constraint) contextObject;
					
					if (elementName.equals("message")){
						StringBuffer message = new StringBuffer();
						constraint.setMessageBuffer(message);
						return constraint;
					}
					else {
						// TODO : SBML syntax error, throw an exception?
					}
				}
				else {
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

	public void setParserNamespace(String parserNamespace) {
		this.parserNamespace = parserNamespace;
	}

	public String getParserNamespace() {
		return parserNamespace;
	}
	
	private void setRateRuleVariable(RateRule rule, Model model){
		if (rule.isSetVariableID()){
			String variableID = rule.getVariable();
			
			Compartment compartment = model.getCompartment(variableID);
			Species species = null;
			SpeciesReference speciesReference = null;
			Parameter parameter = null;
			
			if (compartment == null){
				species = model.getSpecies(variableID);
				
				if (species == null){
					parameter = model.getParameter(variableID);
					
					if (parameter == null){
						if (model.isSetListOfReactions()){
							
							int i = 0;
							SpeciesReference sr = null;
							
							while (i <= model.getNumReactions() - 1 && sr == null){
								Reaction reaction = model.getReaction(i);
								
								if (reaction != null){
									sr = reaction.getReactant(variableID);
									if (sr == null){
										sr = reaction.getProduct(variableID);
									}
								}
							}
							
							speciesReference = sr;
							
							if (speciesReference != null){
								rule.setVariable(speciesReference);
							}
							else {
								// TODO : the variable ID doesn't match a SBML component, throw an exception?
							}
						}
					}
					else {
						rule.setVariable(parameter);
					}
				}
				else {
					rule.setVariable(species);
				}
			}
			else {
				rule.setVariable(compartment);
			}
		}
	}
	
	private void setAssignmentRuleVariable(AssignmentRule rule, Model model){
		
		if (rule.isSetVariableID()){
			String variableID = rule.getVariable();
			
			Compartment compartment = model.getCompartment(variableID);
			Species species = null;
			SpeciesReference speciesReference = null;
			Parameter parameter = null;
			
			if (compartment == null){
				species = model.getSpecies(variableID);
				
				if (species == null){
					parameter = model.getParameter(variableID);
					
					if (parameter == null){
						if (model.isSetListOfReactions()){
							
							int i = 0;
							SpeciesReference sr = null;
							
							while (i <= model.getNumReactions() - 1 && sr == null){
								Reaction reaction = model.getReaction(i);
								
								if (reaction != null){
									sr = reaction.getReactant(variableID);
									if (sr == null){
										sr = reaction.getProduct(variableID);
									}
								}
							}
							
							speciesReference = sr;
							
							if (speciesReference != null){
								rule.setVariable(speciesReference);
							}
							else {
								// TODO : the variable ID doesn't match a SBML component, throw an exception?
							}
						}
					}
					else {
						rule.setVariable(parameter);
					}
				}
				else {
					rule.setVariable(species);
				}
			}
			else {
				rule.setVariable(compartment);
			}
		}
	}
	
	private void setCompartmentCompartmentType(Compartment compartment, Model model){
		if (compartment.isSetCompartmentTypeID()){
			String compartmentTypeID = compartment.getCompartmentTypeID();
			
			CompartmentType compartmentType = model.getCompartmentType(compartmentTypeID);
			
			if (compartmentType != null){
				compartment.setCompartmentType(compartmentType);
			}
			else {
				// TODO : the compartmentType ID doesn't match a compartment, throw an exception?
			}
		}
	}
	
	private void setCompartmentOutside(Compartment compartment, Model model){
		
		if (compartment.isSetOutsideID()){
			String outsideID = compartment.getOutside();
			
			Compartment outside = model.getCompartment(outsideID);
			
			if (outside != null){
				compartment.setOutside(outside);
			}
			else {
				// TODO : the compartment ID doesn't match a compartment, throw an exception?
			}
		}
	}
	
	private void setCompartmentUnits(Compartment compartment, Model model){
		
		if (compartment.isSetUnitsID()){
			String unitsID = compartment.getUnits();
			
			UnitDefinition unitDefinition = model.getUnitDefinition(unitsID);
			
			if (unitDefinition != null){
				compartment.setUnits(unitDefinition);
			}
			else {
				// TODO : the unitDefinition ID doesn't match a unitDefinition, throw an exception?
			}
		}
	}
	
	private void setEventTimeUnits(Event event, Model model){
		
		if (event.isSetTimeUnitsID()){
			String timeUnitsID = event.getTimeUnits();
			
			UnitDefinition unitDefinition = model.getUnitDefinition(timeUnitsID);
			
			if (unitDefinition != null){
				event.setTimeUnits(unitDefinition);
			}
			else {
				// TODO : the unitDefinition ID doesn't match a unitDefinition, throw an exception?
			}
		}
	}
	
	private void setEventAssignmentVariable(EventAssignment eventAssignment, Model model){
		
		if (eventAssignment.isSetVariableID()){
			String variableID = eventAssignment.getVariable();
			
			Compartment compartment = model.getCompartment(variableID);
			Species species = null;
			SpeciesReference speciesReference = null;
			Parameter parameter = null;
			
			if (compartment == null){
				species = model.getSpecies(variableID);
				
				if (species == null){
					parameter = model.getParameter(variableID);
					
					if (parameter == null){
						if (model.isSetListOfReactions()){
							
							int i = 0;
							SpeciesReference sr = null;
							
							while (i <= model.getNumReactions() - 1 && sr == null){
								Reaction reaction = model.getReaction(i);
								
								if (reaction != null){
									sr = reaction.getReactant(variableID);
									if (sr == null){
										sr = reaction.getProduct(variableID);
									}
								}
							}
							
							speciesReference = sr;
							
							if (speciesReference != null){
								eventAssignment.setVariable(speciesReference);
							}
							else {
								// TODO : the variable ID doesn't match a SBML component, throw an exception?
							}
						}
					}
					else {
						eventAssignment.setVariable(parameter);
					}
				}
				else {
					eventAssignment.setVariable(species);
				}
			}
			else {
				eventAssignment.setVariable(compartment);
			}
		}
	}
	
	private void setInitialAssignmentSymbol(InitialAssignment initialAssignment, Model model){
		
		if (initialAssignment.isSetSymbolID()){
			String variableID = initialAssignment.getSymbol();
			
			Compartment compartment = model.getCompartment(variableID);
			Species species = null;
			SpeciesReference speciesReference = null;
			Parameter parameter = null;
			
			if (compartment == null){
				species = model.getSpecies(variableID);
				
				if (species == null){
					parameter = model.getParameter(variableID);
					
					if (parameter == null){
						if (model.isSetListOfReactions()){
							
							int i = 0;
							SpeciesReference sr = null;
							
							while (i <= model.getNumReactions() - 1 && sr == null){
								Reaction reaction = model.getReaction(i);
								
								if (reaction != null){
									sr = reaction.getReactant(variableID);
									if (sr == null){
										sr = reaction.getProduct(variableID);
									}
								}
							}
							
							speciesReference = sr;
							
							if (speciesReference != null){
								initialAssignment.setSymbol(speciesReference);
							}
							else {
								// TODO : the variable ID doesn't match a SBML component, throw an exception?
							}
						}
					}
					else {
						initialAssignment.setSymbol(parameter);
					}
				}
				else {
					initialAssignment.setSymbol(species);
				}
			}
			else {
				initialAssignment.setSymbol(compartment);
			}
		}
	}
	
	private void setReactionCompartment(Reaction reaction, Model model){
		
		if (reaction.isSetCompartmentID()){
			String compartmentID = reaction.getCompartmentID();
			
			Compartment compartment = model.getCompartment(compartmentID);
			
			if (compartment != null){
				reaction.setCompartment(compartment);
			}
			else {
				// TODO : the compartment ID doesn't match a compartment, throw an exception?
			}
		}
	}
	
	private void setSpeciesReferenceSpecies(SimpleSpeciesReference speciesReference, Model model){
		
		if (speciesReference.isSetSpeciesID()){
			String speciesID = speciesReference.getSpecies();
			
			Species species = model.getSpecies(speciesID);
			
			if (species != null){
				speciesReference.setSpecies(species);
			}
			else {
				// TODO : the species ID doesn't match a species, throw an exception?
			}
		}
	}
	
	private void setSpeciesSubstanceUnits(Species species, Model model){
		
		if (species.isSetSubstanceUnitsID()){
			String substanceUnitsID = species.getSubstanceUnits();
			
			UnitDefinition unitDefinition = model.getUnitDefinition(substanceUnitsID);
			
			if (unitDefinition != null){
				species.setSubstanceUnits(unitDefinition);
			}
		}
	}
	
	private void setSpeciesConversionFactor(Species species, Model model){
		
		if (species.isSetConversionFactorID()){
			String conversionFactorID = species.getConversionFactorID();
			
			Parameter parameter = model.getParameter(conversionFactorID);
			
			if (parameter != null){
				species.setConversionFactor(parameter);
			}
			else {
				// TODO : the parameter ID doesn't match a parameter, throw an exception?
			}
		}
	}
	
	private void setSpeciesSpeciesType(Species species, Model model){
		
		if (species.isSetSpeciesTypeID()){
			String speciesTypeID = species.getSpeciesType();
			
			SpeciesType speciesType = model.getSpeciesType(speciesTypeID);
			
			if (speciesType != null){
				species.setSpeciesType(speciesType);
			}
			else {
				// TODO : the speciesType ID doesn't match a speciesType, throw an exception?
			}
		}
	}
	
	private void setSpeciesCompartment(Species species, Model model){
		
		if (species.isSetCompartmentID()){
			String compartmentID = species.getSpeciesType();
			
			Compartment compartment = model.getCompartment(compartmentID);
			
			if (compartment != null){
				species.setCompartment(compartment);
			}
			else {
				// TODO : the compartment ID doesn't match a compartment, throw an exception?
			}
		}
	}
	
	private void setParameterUnits(Parameter parameter, Model model){
		
		if (parameter.isSetUnitsID()){
			String unitsID = parameter.getUnits();
			
			UnitDefinition unitDefinition = model.getUnitDefinition(unitsID);
			
			if (unitDefinition != null){
				parameter.setUnits(unitDefinition);
			}
			else {
				// TODO : the unitDefinition ID doesn't match an unitDefinition, throw an exception?
			}
		}
	}


	public void processEndElement(String elementName, String prefix, boolean isNested,
			Object contextObject) {
	
			if (elementName.equals("notes") && contextObject instanceof SBase){
				SBase sbase = (SBase) contextObject;
				sbase.setNotes(sbase.getNotesBuffer().toString());
			}
			else if (elementName.equals("message") && contextObject instanceof Constraint){
				Constraint constraint = (Constraint) contextObject;
				constraint.setMessage(constraint.getMessageBuffer().toString());
			}
	}

	public void processEndDocument(SBMLDocument sbmlDocument) {
		
		if (sbmlDocument.isSetModel()){
			Model model = sbmlDocument.getModel();
			
			if (model.isSetListOfRules()){
				for (int i = 0; i < model.getNumRules(); i++){
					Rule rule = model.getRule(i);
					if (rule instanceof AssignmentRule){
						AssignmentRule assignmentRule = (AssignmentRule) rule;
						setAssignmentRuleVariable(assignmentRule, model);
					}
					else if (rule instanceof RateRule){
						RateRule rateRule = (RateRule) rule;
						setRateRuleVariable(rateRule, model);
					}
				}
			}
			if (model.isSetListOfCompartments()){
				for (int i = 0; i < model.getNumCompartments(); i++){
					Compartment compartment = model.getCompartment(i);
					
					setCompartmentCompartmentType(compartment, model);
					setCompartmentOutside(compartment, model);
					setCompartmentUnits(compartment, model);
				}
			}
			if (model.isSetListOfEvents()){
				for (int i = 0; i < model.getNumEvents(); i++){
					Event event = model.getEvent(i);
					
					setEventTimeUnits(event, model);
					
					if (event.isSetListOfEventAssignments()){
						
						for (int j = 0; j < event.getNumEventAssignments(); j++){
							EventAssignment eventAssignment = event.getEventAssignment(j);
						
							setEventAssignmentVariable(eventAssignment, model);
						}
					}
				}
			}
			if (model.isSetListOfInitialAssignemnts()){
				for (int i = 0; i < model.getNumInitialAssignments(); i++){
					InitialAssignment initialAssignment = model.getInitialAssignment(i);
					
					setInitialAssignmentSymbol(initialAssignment, model);
				}
			}
			if (model.isSetListOfReactions()){
				for (int i = 0; i < model.getNumReactions(); i++){
					Reaction reaction = model.getReaction(i);
					
					setReactionCompartment(reaction, model);
					
					if (reaction.isSetListOfReactants()){
						for (int j = 0; j < reaction.getNumReactants(); j++){
							SpeciesReference speciesReference = reaction.getReactant(j);
							
							setSpeciesReferenceSpecies(speciesReference, model);
						}
					}
					if (reaction.isSetListOfProducts()){
						for (int j = 0; j < reaction.getNumProducts(); j++){
							SpeciesReference speciesReference = reaction.getProduct(j);
							
							setSpeciesReferenceSpecies(speciesReference, model);
						}
					}
					if (reaction.isSetListOfModifiers()){
						for (int j = 0; j < reaction.getNumModifiers(); j++){
							ModifierSpeciesReference modifierSpeciesReference = reaction.getModifier(j);
							
							setSpeciesReferenceSpecies(modifierSpeciesReference, model);
						}
					}
					if (reaction.isSetKineticLaw()){
						KineticLaw kineticLaw = reaction.getKineticLaw();
						if (kineticLaw.isSetListOfParameters()){
							for (int j = 0; j < kineticLaw.getNumParameters(); j++){
								Parameter parameter = kineticLaw.getParameter(i);
								
								setParameterUnits(parameter, model);
							}
						}
					}
				}
			}
			if (model.isSetListOfSpecies()){
				for (int i = 0; i < model.getNumSpecies(); i++){
					Species species = model.getSpecies(i);
					
					setSpeciesSubstanceUnits(species, model);
					setSpeciesSpeciesType(species, model);
					setSpeciesConversionFactor(species, model);
					setSpeciesCompartment(species, model);
				}
			}
			if (model.isSetListOfParameters()){
				for (int i = 0; i < model.getNumParameters(); i++){
					Parameter parameter = model.getParameter(i);
					
					setParameterUnits(parameter, model);
				}
			}

		}
		else {
			// TODO : SBML syntax error, what to do?
		}
	}
}
