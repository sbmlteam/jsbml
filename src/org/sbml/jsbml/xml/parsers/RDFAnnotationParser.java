/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2013 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */

package org.sbml.jsbml.xml.parsers;

import java.text.MessageFormat;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.sbml.jsbml.Annotation;
import org.sbml.jsbml.CVTerm;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.CompartmentType;
import org.sbml.jsbml.Constraint;
import org.sbml.jsbml.Creator;
import org.sbml.jsbml.Event;
import org.sbml.jsbml.EventAssignment;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.History;
import org.sbml.jsbml.InitialAssignment;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.LocalParameter;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.ModifierSpeciesReference;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.Rule;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.UnitDefinition;

/**
 * A RDFAnnotationParser is used to parser the subNodes of an annotation which
 * have the namespace URI : "http://www.w3.org/1999/02/22-rdf-syntax-ns#". This
 * parser can only read the RDF annotations.
 * 
 * @author Marine Dumousseau
 * @since 0.8
 * @version $Rev$
 */
@SuppressWarnings("deprecation")
public class RDFAnnotationParser implements ReadingParser {
	
  /**
   * 
   */
	private transient Logger logger = Logger.getLogger(RDFAnnotationParser.class); 
	
	/**
	 * The namespaceURI of this parser.
	 */
	private static final String namespaceURI = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
	
	/**
	 * @return the namespaceURI
	 */
	public static String getNamespaceURI() {
		return namespaceURI;
	}

	/**
	 * A map containing the history of the previous element within a RDF node this parser has been read.
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
			Object contextObject) 
	{
		
		boolean isReadAttribute = false;

		// A RDFAnnotationParser can modify a contextObject which is an Annotation instance.
		// The annotation element can contain other attributes. Try to read them.
		if (contextObject instanceof Annotation) {
			Annotation modelAnnotation = (Annotation) contextObject;
			isReadAttribute = modelAnnotation.readAttribute(attributeName, prefix, value);
		}
		// A RDFAnnotationParser can modify a contextObject which is an ModelHistory instance.
		// When this parser is parsing the model history, some rdf attributes can appear. Try to
		// read them.
		else if (contextObject instanceof History) {
			History modelHistory = (History) contextObject;
			isReadAttribute = modelHistory.readAttribute(elementName, attributeName, prefix, value);
		}
		// A RDFAnnotationParser can modify a contextObject which is an ModelCreator instance.
		// If the contextObject is a ModelCreator instance, the rdf attributes should appear in the
		// 'li' subelement of the 'Bag' subelement of the 'creator' node.
		// When this parser is parsing the model history, some rdf attributes can appear. Try to
		// read them.
		else if (contextObject instanceof Creator && previousElements.containsKey("creator")) {
			if (previousElements.get("creator").equals("li")) {
				Creator modelCreator = (Creator) contextObject;
				isReadAttribute = modelCreator.readAttribute(elementName, attributeName, prefix, value);
			}
		}
		// A RDFAnnotationParser can modify a contextObject which is an CVTerm instance.
		// If the contextObject is a CVTerm instance, the rdf attributes should appear in the
		// 'li' subelement of the 'Bag' subelement of the 'Miriam-Qualifier' node.
		// When this parser is parsing the rdf annotation, some rdf attributes can appear. Try to
		// read them.
		else if (contextObject instanceof CVTerm && previousElements.containsKey("CVTerm")) {
			if (previousElements.get("CVTerm").equals("li")) {
				CVTerm cvterm = (CVTerm) contextObject;
				isReadAttribute = cvterm.readAttribute(elementName, attributeName, prefix, value);
			}
		}
		
		if (!isReadAttribute) {
			logger.warn("RDFAnnotationParser : processAttribute : found an unknown attribute '" + 
					attributeName + "', the attribute is ignored (elementName = " + elementName + ").");
		}
		
	}

	/* (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processCharactersOf(String elementName, String characters,
			Object contextObject)
	 */
	public void processCharactersOf(String elementName, String characters,
			Object contextObject) 
	{
		// there is no text for element with the namespace "http://www.w3.org/1999/02/22-rdf-syntax-ns#".
		if (characters.trim().length() > 0) {
			logger.warn("RDFAnnotationParser: processCharactersOf called !!!! Found some unexpected characters");
		}
	}

	
	/**
	 * 
	 * 
	 * @param sbase
	 */
	private void setRDFAbout(SBase sbase) {
		// We assume that the method is called only if there is an annotation defined.
		
		if (sbase.isSetMetaId()) {
			sbase.getAnnotation().setAbout("#" + sbase.getMetaId());
		} else {
			// TODO: generate the metaid at this point ??
			
			logger.warn(String.format(
				"The element %s has no metaid, so the rdf:about inside his annotation cannot be defined properly.",
				sbase.getElementName()));
		}
	}
	
	/**
	 * Checks if the sbmlDocument and all the  SBML components have a valid rdf:about value.
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processEndDocument(SBMLDocument sbmlDocument)
	 */
	public void processEndDocument(SBMLDocument sbmlDocument) {
		
		if (!sbmlDocument.hasValidAnnotation()) {
			logger.warn("The SBMLDocument element has an invalid rdf:about inside his annotation.");
			setRDFAbout(sbmlDocument);
		}

		Model model = sbmlDocument.getModel();

		if (!model.hasValidAnnotation()) {
			logger.warn(MessageFormat.format("The model element with metaid = \"{0}\" has an invalid rdf:about = \"{1}\" inside his annotation.", model.getMetaId(), model.getAnnotation().getAbout()));	
			setRDFAbout(model);
		}

		if (model.isSetListOfFunctionDefinitions()) {
			for (FunctionDefinition functionDefinition : model.getListOfFunctionDefinitions()) {
				if (!functionDefinition.hasValidAnnotation()) {
					logger.warn("The functionDefinition element '" + functionDefinition.getMetaId() + "' has an invalid rdf:about inside his annotation.");
					setRDFAbout(functionDefinition);
				}
			}
		}
		
		if (model.isSetListOfCompartments()) {
			for (Compartment compartment : model.getListOfCompartments()) {
				if (!compartment.hasValidAnnotation()) {
					logger.warn("The compartment '" + compartment.getId() + "' has an invalid rdf:about inside his annotation.");
					setRDFAbout(compartment);
				}
			}
		}
		
		if (model.isSetListOfCompartmentTypes()) {
			for (CompartmentType compartmentType : model.getListOfCompartmentTypes()) {
				if (!compartmentType.hasValidAnnotation()) {
					logger.warn("The compartmentType '" + compartmentType.getId() + "' has an invalid rdf:about inside his annotation.");
					setRDFAbout(compartmentType);
				}
			}
		}
		
		logger.debug("compartments checked");
		
		
		if (model.isSetListOfConstraints()) {
			for (Constraint constraint : model.getListOfConstraints()) {
				if (!constraint.hasValidAnnotation()) {
					logger.warn("The constraint element '" + constraint.getMetaId() + "' has an invalid rdf:about inside his annotation.");
					setRDFAbout(constraint);
				}
			}
		}
		
		if (model.isSetListOfEvents()) {
			for (Event event : model.getListOfEvents()) {
				if (!event.hasValidAnnotation()) {
					logger.warn("The event element '" + event.getMetaId() + "' has an invalid rdf:about inside his annotation.");
					setRDFAbout(event);
				}
				if (event.isSetDelay()) {
					if (! event.getDelay().hasValidAnnotation()) {
						logger.warn("The delay element '" + event.getDelay().getMetaId() + "' has an invalid rdf:about inside his annotation.");
						setRDFAbout(event.getDelay());
					}
				}
				if (event.isSetListOfEventAssignments()) {
					for (EventAssignment eventAssignment : event.getListOfEventAssignments()) {
						if (!eventAssignment.hasValidAnnotation()) {
							logger.warn("The eventAssignment element '" + eventAssignment.getMetaId() + "' has an invalid rdf:about inside his annotation.");
							setRDFAbout(eventAssignment);
						}
					}
				}
				if (event.isSetTrigger()) {
					if (! event.getTrigger().hasValidAnnotation()) {
						logger.warn("The trigger element '" + event.getTrigger().getMetaId() + "' has an invalid rdf:about inside his annotation.");
						setRDFAbout(event.getTrigger());
					}
				}
			}			
		}
		
		logger.debug("events checked");
		
		if (model.isSetListOfInitialAssignments()) {
			for (InitialAssignment initAssgnt : model.getListOfInitialAssignments()) {
				if (!initAssgnt.hasValidAnnotation()) {
					logger.warn("The initialAssignment element '" + initAssgnt.getMetaId() + "' has an invalid rdf:about inside his annotation.");
					setRDFAbout(initAssgnt);
				}
			}
		}
		
		if (model.isSetListOfParameters()) {
			for (Parameter parameter : model.getListOfParameters()) {
				if (!parameter.hasValidAnnotation()) {
					logger.warn("The parameter element '" + parameter.getId() + "' has an invalid rdf:about inside his annotation.");
					setRDFAbout(parameter);
				}
			}
		}
		
		logger.debug("parameters checked");
		
		if (model.isSetListOfReactions()) {
			for (Reaction reaction : model.getListOfReactions()) {

				if (!reaction.hasValidAnnotation()) {
					logger.warn("The reaction element '" + reaction.getId() + "' has an invalid rdf:about inside his annotation.");
					setRDFAbout(reaction);
				}

				if (reaction.isSetKineticLaw()) {
					KineticLaw kineticLaw = reaction.getKineticLaw();

					if (!kineticLaw.hasValidAnnotation()) {
						logger.warn("The kineticLaw element '" + kineticLaw.getMetaId() + "' has an invalid rdf:about inside his annotation.");
						setRDFAbout(kineticLaw);
					}

					if (kineticLaw.isSetListOfLocalParameters()) {
						for (LocalParameter parameter : kineticLaw.getListOfLocalParameters()) {
							if (!parameter.hasValidAnnotation()) {
								logger.warn("The local parameter element '" + parameter.getId() + "' has an invalid rdf:about inside his annotation.");
								setRDFAbout(parameter);
							}
						}
					}					
				}

				if (reaction.isSetListOfReactants()) {
					for (SpeciesReference reactant : reaction.getListOfReactants()) {
						if (!reactant.hasValidAnnotation()) {
							logger.warn("The reactant element '" + reactant.getMetaId() + "' has an invalid rdf:about inside his annotation.");
							setRDFAbout(reactant);
						}
					}
				}
				if (reaction.isSetListOfProducts()) {
					for (SpeciesReference product : reaction.getListOfProducts()) {
						if (!product.hasValidAnnotation()) {
							logger.warn("The product element '" + product.getMetaId() + "' has an invalid rdf:about inside his annotation.");
							setRDFAbout(product);
						}
					}
				}
				if (reaction.isSetListOfModifiers()) {
					for (ModifierSpeciesReference modifier : reaction.getListOfModifiers()) {
						if (!modifier.hasValidAnnotation()) {
							logger.warn("The modifier element '" + modifier.getMetaId() + "' has an invalid rdf:about inside his annotation.");
							setRDFAbout(modifier);
						}
					}
				}
			}			
		}

		logger.debug("reactions checked");
		
		if (model.isSetListOfRules()) {
			for (Rule rule : model.getListOfRules()) {
				if (!rule.hasValidAnnotation()) {
					logger.warn("The rule element '" + rule.getMetaId() + "' has an invalid rdf:about inside his annotation.");
					setRDFAbout(rule);
				}
			}
		}
		if (model.isSetListOfSpecies()) {
			for (Species species : model.getListOfSpecies()) {
				if (!species.hasValidAnnotation()) {
					logger.warn("The species element '" + species.getId() + "' has an invalid rdf:about inside his annotation.");
					setRDFAbout(species);
				}
			}
		}
		if (model.isSetListOfSpeciesTypes()) {
			for (Species speciesType : model.getListOfSpecies()) {
				if (!speciesType.hasValidAnnotation()) {
					logger.warn("The speciesType element '" + speciesType.getId() + "' has an invalid rdf:about inside his annotation.");
					setRDFAbout(speciesType);
				}
			}
		}
		
		logger.debug("species checked");

		if (model.isSetListOfUnitDefinitions()) {
			for (UnitDefinition unitDefinition : model.getListOfUnitDefinitions()) {			

				if (!unitDefinition.hasValidAnnotation()) {
					logger.warn("The unitDefinition element '" + unitDefinition.getId() + "' has an invalid rdf:about inside his annotation.");
					setRDFAbout(unitDefinition);
				}
				if (unitDefinition.isSetListOfUnits()) {
					for (Unit unit : unitDefinition.getListOfUnits()) {
						if (!unit.hasValidAnnotation()) {
							logger.warn("The unit element '" + unit.getMetaId() + "' has an invalid rdf:about inside his annotation.");
							setRDFAbout(unit);
						}
					}
				}
			}
		}
		
	}

	/* (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processEndElement(String elementName, String prefix,
			boolean isNested, Object contextObject)
	 */
	public boolean processEndElement(String elementName, String prefix,
			boolean isNested, Object contextObject) {
		
		// If the contextObject is a ModelCreator, the current element should be included into a 'creator'
		// element.
		if (contextObject instanceof Creator) {
			// If it is a ending Bag element, there is no other creators to parse in the 'creator' node, we can reinitialise the
			// previousElements HashMap of this parser and remove the Entry which has 'creator' as key.
			if (elementName.equals("Bag")) {
				previousElements.remove("creator");
			}
			// If it is a ending li element, we can reinitialise the
			// previousElements HashMap of this parser and set the value of the 'creator' key to 'Bag'.
			else if (elementName.equals("li")) {
				previousElements.put("creator", "Bag");
			}
		}
		else if (contextObject instanceof CVTerm) {
			// If it is a ending Bag element, there is no other resource URI to parse for this CVTerm, we can reinitialise the
			// previousElements HashMap of this parser and remove the Entry which has 'CVTerm' as key.
			if (elementName.equals("Bag")) {
				previousElements.remove("CVTerm");
			}
			// If it is a ending li element, we can reinitialise the
			// previousElements HashMap of this parser and set the value of the 'CVTerm' key to 'Bag'.
			else if (elementName.equals("li")) {
				previousElements.put("CVTerm", "Bag");
			}
		}
		
		// If it is the end of a RDF element, we can clear the previousElements HashMap of this parser.
		if (elementName.equals("RDF")) {
			this.previousElements.clear();
		}
		
		return true;
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
		if (elementName.equals("RDF") && (contextObject instanceof Annotation)) {
			Annotation annotation = (Annotation) contextObject;
			annotation.addRDFAnnotationNamespace(localName, prefix, URI);;
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
			Object contextObject) 
	{
		// A RDFAnnotationParser can modify a ContextObject which is an Annotation instance.
		if (contextObject instanceof Annotation) {
			
			// If the node is a RDF node, adds ("RDF", null) to the previousElements of this parser.
			if (elementName.equals("RDF")) {
				this.previousElements.put(elementName, null);
			}
			// The Description element should be the first child node of a RDF element.
			// If the SBML specifications are respected, sets the value of the 'RDF' key to 'Description'.
			else if (elementName.equals("Description") && previousElements.containsKey("RDF"))
			{
				this.previousElements.put("RDF", "Description");
			} 
			else 
			{
				logger.warn("Found a RDF:Description that is not a child of the RDF:RDF element !! Element prefix:name = " + prefix + ":" + elementName);
				
			}
		}
		// If the contextObject is not an Annotation instance, we should be into the Description subNode of the RDF element.
		else if (this.previousElements.containsKey("RDF")) {
			if (this.previousElements.get("RDF") != null) {
				// The Description subNode of RDF has been read.
				if (this.previousElements.get("RDF").equals("Description")) {
					// A RDFAnnotation can modify a contextObject which is a CVTerm instance.
					if (contextObject instanceof CVTerm) {
						// The first element of the 'miriam-qualifier' node (the CVTerm) should be a Bag element.
						if (elementName.equals("Bag")) {
							this.previousElements.put("CVTerm", "Bag");
						}
						// If a 'Bag' subNode has been read and if the current element is a 'li' subNode
						else if (elementName.equals("li") && previousElements.containsKey("CVTerm")) {
							if (this.previousElements.get("CVTerm").equals("Bag")) {
								this.previousElements.put("CVTerm", "li");
							}
							else {
								logger.warn("Syntax error in your RDF annotation. An RDF:li element should be inside a RDF:Bag element.");
							}
						}
						else {
							logger.warn("Syntax error in your RDF annotation");
						}
					}
					// A RDFAnnotation can modify a contextObject which is a ModelHistory instance.
					else if (contextObject instanceof History) {
						History modelHistory = (History) contextObject;
						// we should be into a 'creator' node and the first element should be a Bag element.
						if (elementName.equals("Bag")) {
							this.previousElements.put("creator", "Bag");
						}
						// After the 'Bag' node of the 'creator' element, it should be a 'li' node.
						// If the SBML specifications are respected, a new ModelCreator will be created
						// and added to the listOfCreators of modelHistory. In this case, it will return the new ModelCreator instance.
						else if (elementName.equals("li") && previousElements.containsKey("creator")) {
							if (previousElements.get("creator").equals("Bag")) {
								this.previousElements.put("creator", "li");
								Creator modelCreator = new Creator();
								modelHistory.addCreator(modelCreator);	
								return modelCreator;
							}
							else {
								logger.warn("Syntax error in your RDF annotation");
							}
						}
						else {
							logger.warn("Syntax error in your RDF annotation");
						}
					}
					else {
						logger.warn("Syntax error in your RDF annotation");
					}
				}
				else {
					logger.warn("Syntax error in your RDF annotation");
				}
			}
			else {
				logger.warn("Found several children for the RDF:RDF element. This is currently not well supported in JSBML");
				// if the RDF element does contain several Description child nodes, they are passed to the StringParser but
				// the whole annotation should be read into an XMLNode for the 1.0 release to make all of that a bit more
				// robust
			}
		}
		return contextObject;
	}
}
