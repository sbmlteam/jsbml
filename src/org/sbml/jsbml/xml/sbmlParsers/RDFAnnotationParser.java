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

package org.sbml.jsbml.xml.sbmlParsers;

import java.util.HashMap;

import org.sbml.jsbml.Annotation;
import org.sbml.jsbml.CVTerm;
import org.sbml.jsbml.Event;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Creator;
import org.sbml.jsbml.History;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.xml.stax.ReadingParser;

/**
 * A RDFAnnotationParser is used to parser the subNodes of an annotation which have the namespace URI :
 * "http://www.w3.org/1999/02/22-rdf-syntax-ns#". This parser can only read the rdf annotations.
 * @author marine
 *
 */
public class RDFAnnotationParser implements ReadingParser{
	
	/**
	 * The namespaceURI of this parser.
	 */
	private static final String namespaceURI = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
	
	/**
	 * A map containing the hitory of the previous element within a RDF node this parser has been read.
	 */
	private HashMap<String, String> previousElements = new HashMap<String, String>();

	/* (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processAttribute(String elementName, String attributeName,
			String value, String prefix, boolean isLastAttribute,
			Object contextObject)
	 */
	public void processAttribute(String elementName, String attributeName,
			String value, String prefix, boolean isLastAttribute,
			Object contextObject) {
		
		boolean isReadAttribute = false;

		// A RDFAnnotationParser can modify a contextObject which is an Annotation instance.
		// The annotation element can contain other attributes. Try to read them.
		if (contextObject instanceof Annotation){
			Annotation modelAnnotation = (Annotation) contextObject;
			isReadAttribute = modelAnnotation.readAttribute(attributeName, prefix, value);
		}
		// A RDFAnnotationParser can modify a contextObject which is an ModelHistory instance.
		// When this parser is parsing the model history, some rdf attributes can appear. Try to
		// read them.
		else if (contextObject instanceof History){
			History modelHistory = (History) contextObject;
			isReadAttribute = modelHistory.readAttribute(elementName, attributeName, prefix, value);
		}
		// A RDFAnnotationParser can modify a contextObject which is an ModelCreator instance.
		// If the contextObject is a ModelCreator instance, the rdf attributes should appear in the
		// 'li' subelement of the 'Bag' subelement of the 'creator' node.
		// When this parser is parsing the model history, some rdf attributes can appear. Try to
		// read them.
		else if (contextObject instanceof Creator && previousElements.containsKey("creator")){
			if (previousElements.get("creator").equals("li")){
				Creator modelCreator = (Creator) contextObject;
				isReadAttribute = modelCreator.readAttribute(elementName, attributeName, prefix, value);
			}
		}
		// A RDFAnnotationParser can modify a contextObject which is an CVTerm instance.
		// If the contextObject is a CVTerm instance, the rdf attributes should appear in the
		// 'li' subelement of the 'Bag' subelement of the 'Miriam-Qualifier' node.
		// When this parser is parsing the rdf annotation, some rdf attributes can appear. Try to
		// read them.
		else if (contextObject instanceof CVTerm && previousElements.containsKey("CVTerm")){
			if (previousElements.get("CVTerm").equals("li")){
				CVTerm cvterm = (CVTerm) contextObject;
				isReadAttribute = cvterm.readAttribute(elementName, attributeName, prefix, value);
			}
		}
		
		if (!isReadAttribute){
			// TODO the attribute is not read, throw an error?
		}
		
	}

	/* (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processCharactersOf(String elementName, String characters,
			Object contextObject)
	 */
	public void processCharactersOf(String elementName, String characters,
			Object contextObject) {
		// TODO : there is no text for element with the namespace "http://www.w3.org/1999/02/22-rdf-syntax-ns#".
		// There is a syntax error, throw an exception?
	}

	/* (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processEndElement(String elementName, String prefix,
			boolean isNested, Object contextObject)
	 */
	public void processEndElement(String elementName, String prefix,
			boolean isNested, Object contextObject) {
		
		// If the contextObject is a ModelCreator, the current element should be included into a 'creator'
		// element.
		if (contextObject instanceof Creator){
			// If it is a ending Bag element, there is no other creators to parse in the 'creator' node, we can reinitialise the
			// previousElements HashMap of this parser and remove the Entry which has 'creator' as key.
			if (elementName.equals("Bag")){
				previousElements.remove("creator");
			}
			// If it is a ending li element, we can reinitialise the
			// previousElements HashMap of this parser and set the value of the 'creator' key to 'Bag'.
			else if (elementName.equals("li")){
				previousElements.put("creator", "Bag");
			}
		}
		else if (contextObject instanceof CVTerm){
			// If it is a ending Bag element, there is no other resource URI to parse for this CVTerm, we can reinitialise the
			// previousElements HashMap of this parser and remove the Entry which has 'CVTerm' as key.
			if (elementName.equals("Bag")){
				previousElements.remove("CVTerm");
			}
			// If it is a ending li element, we can reinitialise the
			// previousElements HashMap of this parser and set the value of the 'CVTerm' key to 'Bag'.
			else if (elementName.equals("li")){
				previousElements.put("CVTerm", "Bag");
			}
		}
		
		// If it is the end of a RDF element, we can clear the previousElements HashMap of this parser.
		if (elementName.equals("RDF")){
			this.previousElements.clear();
		}
	}

	/* (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processStartElement(String elementName, String prefix,
			boolean hasAttributes, boolean hasNamespaces,
			Object contextObject)
	 */
	public Object processStartElement(String elementName, String prefix,
			boolean hasAttributes, boolean hasNamespaces,
			Object contextObject) {
		// A RDFAnnotationParser can modify a ContextObject which is an Annotation instance.
		if (contextObject instanceof Annotation){
			
			// If the node is a RDF node, adds ("RDF", null) to the previousElements of this parser.
			if (elementName.equals("RDF")){
				this.previousElements.put(elementName, null);
			}
			// The Description element should be the first child node of a RDF element.
			// If the SBML specifications are respected, sets the value of the 'RDF' key to 'Description'.
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
		// If the contextObject is not an Annotation instance, we should be into the Description subNode of the RDF element.
		else if (this.previousElements.containsKey("RDF")){
			if (this.previousElements.get("RDF") != null){
				// The Description subNode of RDF has been read.
				if (this.previousElements.get("RDF").equals("Description")){
					// A RDFAnnotation can modify a contextObject which is a CVTerm instance.
					if (contextObject instanceof CVTerm){
						// The first element of the 'miriam-qualifier' node (the CVTerm) should be a Bag element.
						if (elementName.equals("Bag")){
							this.previousElements.put("CVTerm", "Bag");
						}
						// If a 'Bag' subNode has been read and if the current element is a 'li' subNode
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
					// A RDFAnnotation can modify a contextObject which is a ModelHistory instance.
					else if (contextObject instanceof History){
						History modelHistory = (History) contextObject;
						// we should be into a 'creator' node and the first element should be a Bag element.
						if (elementName.equals("Bag")){
							this.previousElements.put("creator", "Bag");
						}
						// After the 'Bag' node of the 'creator' element, it should be a 'li' node.
						// If the SBML specifications are respected, a new ModelCreator will be created
						// and added to the listOfCreators of modelHistory. In this case, it will return the new ModelCreator instance.
						else if (elementName.equals("li") && previousElements.containsKey("creator")){
							if (previousElements.get("creator").equals("Bag")){
								this.previousElements.put("creator", "li");
								Creator modelCreator = new Creator();
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

	/* (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processEndDocument(SBMLDocument sbmlDocument)
	 */
	@SuppressWarnings("deprecation")
	public void processEndDocument(SBMLDocument sbmlDocument) {
		// Check if sbmlDocument and all the  SBML components have a valid Annotation.
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
				if (model.isSetListOfInitialAssignments()){
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

	/* (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processNamespace(String elementName, String URI, String prefix,
			String localName, boolean hasAttributes, boolean isLastNamespace,
			Object contextObject)
	 */
	public void processNamespace(String elementName, String URI, String prefix,
			String localName, boolean hasAttributes, boolean isLastNamespace,
			Object contextObject) {

		// Adds the namespace to the RDFAnnotationNamespaces HashMap of annotation.
		if (elementName.equals("RDF") && contextObject instanceof Annotation){
			Annotation annotation = (Annotation) contextObject;
			annotation.addRDFAnnotationNamespace(localName, prefix, URI);;
		}
	}

	/**
	 * @return the namespaceURI
	 */
	public static String getNamespaceURI() {
		return namespaceURI;
	}
}
