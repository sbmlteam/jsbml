package org.sbml.jsbml.xml.sbmlParsers;


import java.util.HashMap;

import org.sbml.jsbml.element.Annotation;
import org.sbml.jsbml.element.CVTerm;
import org.sbml.jsbml.element.Event;
import org.sbml.jsbml.element.KineticLaw;
import org.sbml.jsbml.element.Model;
import org.sbml.jsbml.element.ModelCreator;
import org.sbml.jsbml.element.ModelHistory;
import org.sbml.jsbml.element.Reaction;
import org.sbml.jsbml.element.SBMLDocument;
import org.sbml.jsbml.element.UnitDefinition;
import org.sbml.jsbml.xml.SBMLParser;

public class RDFAnnotationParser implements SBMLParser{
	
	private HashMap<String, String> previousElements = new HashMap<String, String>();

	public void processAttribute(String elementName, String attributeName,
			String value, String prefix, boolean isLastAttribute,
			Object contextObject) {
		
		boolean isReadAttribute = false;

		if (contextObject instanceof Annotation){
			Annotation modelAnnotation = (Annotation) contextObject;
			isReadAttribute = modelAnnotation.readAttribute(attributeName, prefix, value);
		}
		else if (contextObject instanceof ModelHistory){
			ModelHistory modelHistory = (ModelHistory) contextObject;
			isReadAttribute = modelHistory.readAttribute(elementName, attributeName, prefix, value);
		}
		else if (contextObject instanceof ModelCreator && previousElements.containsKey("creator")){
			if (previousElements.get("creator").equals("li")){
				ModelCreator modelCreator = (ModelCreator) contextObject;
				isReadAttribute = modelCreator.readAttribute(elementName, attributeName, prefix, value);
			}
		}
		else if (contextObject instanceof CVTerm && previousElements.containsKey("CVTerm")){
			if (previousElements.get("CVTerm").equals("li")){
				CVTerm cvterm = (CVTerm) contextObject;
				isReadAttribute = cvterm.readAttribute(elementName, attributeName, prefix, value);
			}
		}
		
		if (!isReadAttribute){
			// the attribute is not read, throw an error?
		}
		
	}

	public void processCharactersOf(String elementName, String characters,
			Object contextObject) {
		// TODO : there is no text for element with the namespace "http://www.w3.org/1999/02/22-rdf-syntax-ns#".
		// There is a syntax error, throw an exception?
	}

	public void processEndElement(String elementName, String prefix,
			boolean isNested, Object contextObject) {
		
		if (contextObject instanceof ModelCreator){
			if (elementName.equals("Bag")){
				previousElements.remove("creator");
			}
		}
		else if (contextObject instanceof CVTerm){
			if (elementName.equals("Bag")){
				previousElements.remove("CVTerm");
			}
		}
		
		if (elementName.equals("RDF")){
			this.previousElements.clear();
		}
	}

	public Object processStartElement(String elementName, String prefix,
			boolean hasAttributes, Object contextObject) {
		if (contextObject instanceof Annotation){
			
			if (elementName.equals("RDF")){
				this.previousElements.put(elementName, null);
			}
			else if(elementName.equals("Description") && previousElements.containsKey("RDF")){
				if (this.previousElements.get("RDF") == null){
					this.previousElements.put("RDF", "Description");
				}
				else {
					this.previousElements.put("RDF", "error");
					// TODO : a description is the unique child of RDF node, SBML syntax error, what to do?
				}
			}
			else {
				// TODO : SBML syntax error, what to do?
			}
		}
		else if (this.previousElements.containsKey("RDF")){
			if (this.previousElements.get("RDF") != null){
				if (this.previousElements.get("RDF").equals("Description")){
					if (contextObject instanceof CVTerm){
						if (elementName.equals("Bag")){
							this.previousElements.put("CVTerm", "Bag");
						}
						else if (elementName.equals("li") && previousElements.containsKey("CVTerm")){
							if (this.previousElements.get("CVTerm").equals("Bag")){
								this.previousElements.put("CVTerm", "li");
							}
							else {
								// TODO : sbml syntax error, what to do?
							}
						}
						else {
							// TODO : sbml syntax error, what to do?
						}
					}
					else if (contextObject instanceof ModelHistory){
						ModelHistory modelHistory = (ModelHistory) contextObject;
						if (elementName.equals("Bag")){
							this.previousElements.put("creator", "Bag");
						}
						else if (elementName.equals("Li") && previousElements.containsKey("creator")){
							if (previousElements.get("creator").equals("Bag")){
								this.previousElements.put("creator", "li");
								ModelCreator modelCreator = new ModelCreator();
								modelHistory.addCreator(modelCreator);	
								return modelCreator;
							}
							else {
								// TODO : sbml syntax error, what to do?
							}
						}
						else {
							// TODO : sbml syntax error, what to do?
						}
					}
					else {
						// TODO : sbml syntax error, what to do?
					}
				}
				else {
					// TODO : sbml syntax error, what to do?
				}
			}
			else {
				// TODO : the RDF element doesn't contain a unique Description child node. SBML syntax error, throw an exception?
			}
		}
		return contextObject;
	}

	public void processEndDocument(SBMLDocument sbmlDocument) {
		if (sbmlDocument.hasValidAnnotation()){
			Model model = sbmlDocument.getModel();
			
			if (model.hasValidAnnotation()){
				if (model.isSetListOfCompartments()){
					for (int i = 0; i < model.getNumCompartments(); i++){
						if (!model.getCompartment(i).hasValidAnnotation()){
							// TODO : change the about value of the annotation.
						}
					}
				}
				if (model.isSetListOfCompartmentTypes()){
					for (int i = 0; i < model.getNumCompartmentTypes(); i++){
						if (!model.getCompartmentType(i).hasValidAnnotation()){
							// TODO : change the about value of the annotation.
						}
					}
				}
				if (model.isSetListOfEvents()){
					for (int i = 0; i < model.getNumEvents(); i++){
						if (model.getEvent(i).hasValidAnnotation()){
							Event event = model.getEvent(i);
							if (event.isSetDelay()){
								if (! event.getDelay().hasValidAnnotation()){
									// TODO : change the about value of the annotation.
								}
							}
							if (event.isSetListOfEventAssignments()){
								for (int j = 0; j < event.getNumEventAssignments(); j++){
									if (!event.getEventAssignment(j).hasValidAnnotation()){
										// TODO : change the about value of the annotation.
									}
								}
							}
							if (event.isSetTrigger()){
								if (! event.getTrigger().hasValidAnnotation()){
									// TODO : change the about value of the annotation.
								}
							}
						}
						else {
							// TODO : change the about value of the annotation.
						}
					}
				}
				if (model.isSetListOfInitialAssignemnts()){
					for (int i = 0; i < model.getNumInitialAssignments(); i++){
						if (!model.getInitialAssignment(i).hasValidAnnotation()){
							// TODO : change the about value of the annotation.
						}
					}
				}
				if (model.isSetListOfParameters()){
					for (int i = 0; i < model.getNumParameters(); i++){
						if (!model.getParameter(i).hasValidAnnotation()){
							// TODO : change the about value of the annotation.
						}
					}
				}
				if (model.isSetListOfReactions()){
					for (int i = 0; i < model.getNumReactions(); i++){
						Reaction reaction = model.getReaction(i);
						
						if (reaction.hasValidAnnotation()){
							if (reaction.isSetKineticLaw()){
								KineticLaw kineticLaw = reaction.getKineticLaw();
								if (kineticLaw.hasValidAnnotation()){
									if (kineticLaw.isSetListOfParameters()){
										for (int j = 0; j < model.getNumParameters(); i++){
											if (!kineticLaw.getParameter(j).hasValidAnnotation()){
												// TODO : change the about value of the annotation.
											}
										}
									}
								}
								else {
									// TODO : change the about value of the annotation.
								}
							}
							if (reaction.isSetListOfReactants()){
								for (int j = 0; j < reaction.getNumReactants(); j++){
									if (!reaction.getReactant(j).hasValidAnnotation()){
										// TODO : change the about value of the annotation.
									}
								}
							}
							if (reaction.isSetListOfProducts()){
								for (int j = 0; j < reaction.getNumProducts(); j++){
									if (!reaction.getProduct(j).hasValidAnnotation()){
										// TODO : change the about value of the annotation.
									}
								}
							}
							if (reaction.isSetListOfModifiers()){
								for (int j = 0; j < reaction.getNumModifiers(); j++){
									if (!reaction.getModifier(j).hasValidAnnotation()){
										// TODO : change the about value of the annotation.
									}
								}
							}
						}
						else {
							// TODO : change the about value of the annotation.
						}
					}
				}
				if (model.isSetListOfRules()){
					for (int i = 0; i < model.getNumRules(); i++){
						if (!model.getRule(i).hasValidAnnotation()){
							// TODO : change the about value of the annotation.
						}
					}
				}
				if (model.isSetListOfSpecies()){
					for (int i = 0; i < model.getNumSpecies(); i++){
						if (!model.getSpecies(i).hasValidAnnotation()){
							// TODO : change the about value of the annotation.
						}
					}
				}
				if (model.isSetListOfSpeciesTypes()){
					for (int i = 0; i < model.getNumSpeciesTypes(); i++){
						if (!model.getSpeciesType(i).hasValidAnnotation()){
							// TODO : change the about value of the annotation.
						}
					}
				}
				if (model.isSetListOfUnitDefinitions()){
					for (int i = 0; i < model.getNumUnitDefinitions(); i++){
						UnitDefinition unitDefinition = model.getUnitDefinition(i);
						if (unitDefinition.hasValidAnnotation()){
							if (unitDefinition.isSetListOfUnits()){
								for (int j = 0; j < model.getNumSpeciesTypes(); j++){
									if (!unitDefinition.getUnit(j).hasValidAnnotation()){
										// TODO : change the about value of the annotation.
									}
								}
							}
						}
						else {
							// TODO : change the about value of the annotation.
						}
					}
				}
			}
			else {
				// TODO : change the about value of the annotation.
			}
		}
		else {
			// TODO : change the about value of the annotation.
		}
	}

}
