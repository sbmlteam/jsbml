package org.sbml.jsbml.xml.sbmlParsers;


import java.util.HashMap;

import org.sbml.jsbml.element.Annotation;
import org.sbml.jsbml.element.CVTerm;
import org.sbml.jsbml.element.Event;
import org.sbml.jsbml.element.KineticLaw;
import org.sbml.jsbml.element.Model;
import org.sbml.jsbml.element.ModelAnnotation;
import org.sbml.jsbml.element.ModelCreator;
import org.sbml.jsbml.element.ModelHistory;
import org.sbml.jsbml.element.Reaction;
import org.sbml.jsbml.element.SBMLDocument;
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
			// error?
		}
		
	}

	public void processCharactersOf(String elementName, String characters,
			Object contextObject) {
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
				}
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
						}
					}
				}
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
							//error
						}
					}
				}
				if (model.isSetListOfCompartmentTypes()){
					for (int i = 0; i < model.getNumCompartmentTypes(); i++){
						if (!model.getCompartmentType(i).hasValidAnnotation()){
							//error
						}
					}
				}
				if (model.isSetListOfEvents()){
					for (int i = 0; i < model.getNumEvents(); i++){
						if (model.getEvent(i).hasValidAnnotation()){
							Event event = model.getEvent(i);
							if (event.isSetDelay()){
								if (! event.getDelay().hasValidAnnotation()){
									// error
								}
							}
							if (event.isSetListOfEventAssignments()){
								for (int j = 0; j < event.getNumEventAssignments(); j++){
									if (!event.getEventAssignment(j).hasValidAnnotation()){
										// error
									}
								}
							}
							if (event.isSetTrigger()){
								if (! event.getTrigger().hasValidAnnotation()){
									// error
								}
							}
						}
						else {
							// error
						}
					}
				}
				if (model.isSetListOfInitialAssignemnts()){
					for (int i = 0; i < model.getNumInitialAssignments(); i++){
						if (!model.getInitialAssignment(i).hasValidAnnotation()){
							//error
						}
					}
				}
				if (model.isSetListOfParameters()){
					for (int i = 0; i < model.getNumParameters(); i++){
						if (!model.getParameter(i).hasValidAnnotation()){
							//error
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
												//error
											}
										}
									}
								}
								else {
									//error
								}
							}
							if (reaction.isSetListOfReactants()){
								for (int j = 0; j < reaction.getNumReactants(); j++){
									if (!reaction.getReactant(j).hasValidAnnotation()){
										//error
									}
								}
							}
							if (reaction.isSetListOfProducts()){
								for (int j = 0; j < reaction.getNumProducts(); j++){
									if (!reaction.getProduct(j).hasValidAnnotation()){
										//error
									}
								}
							}
							if (reaction.isSetListOfModifiers()){
								for (int j = 0; j < reaction.getNumModifiers(); j++){
									if (!reaction.getModifier(j).hasValidAnnotation()){
										//error
									}
								}
							}
						}
						else {
							//error
						}
					}
				}
				if (model.isSetListOfRules()){
					for (int i = 0; i < model.getNumRules(); i++){
						if (!model.getRule(i).hasValidAnnotation()){
							//error
						}
					}
				}
				if (model.isSetListOfSpecies()){
					for (int i = 0; i < model.getNumSpecies(); i++){
						if (!model.getSpecies(i).hasValidAnnotation()){
							//error
						}
					}
				}
				if (model.isSetListOfSpeciesTypes()){
					for (int i = 0; i < model.getNumSpeciesTypes(); i++){
						if (!model.getSpeciesType(i).hasValidAnnotation()){
							//error
						}
					}
				}
				if (model.isSetListOfUnitDefinitions()){
					
				}
			}
			else {
				// error
			}
		}
		else {
			// error
		}
	}

}
