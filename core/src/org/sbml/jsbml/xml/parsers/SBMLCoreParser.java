/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2012 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */

package org.sbml.jsbml.xml.parsers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.AlgebraicRule;
import org.sbml.jsbml.Annotation;
import org.sbml.jsbml.AssignmentRule;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.CompartmentType;
import org.sbml.jsbml.Constraint;
import org.sbml.jsbml.Delay;
import org.sbml.jsbml.Event;
import org.sbml.jsbml.EventAssignment;
import org.sbml.jsbml.ExplicitRule;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.InitialAssignment;
import org.sbml.jsbml.JSBML;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.LocalParameter;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.ModifierSpeciesReference;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.Priority;
import org.sbml.jsbml.RateRule;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.Rule;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.SpeciesType;
import org.sbml.jsbml.StoichiometryMath;
import org.sbml.jsbml.Trigger;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.xml.XMLAttributes;
import org.sbml.jsbml.xml.XMLNode;
import org.sbml.jsbml.xml.XMLTriple;
import org.sbml.jsbml.xml.stax.SBMLObjectForXML;
import org.sbml.jsbml.xml.stax.XMLLogger;

/**
 * Parses the SBML core elements of an SBML file. It can read and write SBML
 * core elements (implements {@link ReadingParser} and {@link WritingParser}).
 * 
 * @author Nicolas Rodriguez
 * @author Andreas Dr&auml;ger
 * @author Marine Dumousseau
 * @since 0.8
 * @version $Rev$
 */
// It might be better to have one parser per level and version
// rather than one SBMLCoreParser which parses everything. 

@SuppressWarnings("deprecation")
public class SBMLCoreParser implements ReadingParser, WritingParser {

	/**
	 * This map contains all the relationships XML element name <=> matching
	 * java class.
	 */
	private Map<String, Class<? extends Object>> sbmlCoreElements;

	/**
	 * The logger of this parser
	 */
	private XMLLogger logger;

	/**
	 * Log4j logger
	 */
	private Logger log4jLogger = Logger.getLogger(SBMLCoreParser.class);
	
	/**
	 * Creates a SBMLCoreParser instance. Initializes the sbmlCoreElements of
	 * this Parser.
	 * 
	 */
	public SBMLCoreParser() {
		sbmlCoreElements = new HashMap<String, Class<? extends Object>>();
		// Initializes the sbmlCoreElements of this parser.
		JSBML.loadClasses("org/sbml/jsbml/resources/cfg/SBMLCoreElements.xml",
				sbmlCoreElements);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.WritingParser#getListOfSBMLElementsToWrite(Object
	 * sbase)
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getListOfSBMLElementsToWrite(Object sbase) {
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
								if (ud.isPredefined()) {
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

				if (kineticLaw.isSetListOfLocalParameters()) {
					listOfElementsToWrite = new ArrayList<Object>();
					listOfElementsToWrite.add(kineticLaw.getListOfLocalParameters());
				}
			} else if (sbase instanceof SpeciesReference) {
				SpeciesReference speciesReference = (SpeciesReference) sbase;

				if (speciesReference.isSetStoichiometryMath()) {
					listOfElementsToWrite = new ArrayList<Object>();
					listOfElementsToWrite.add(speciesReference.getStoichiometryMath());
				}
			} else if (sbase instanceof Event) {
				Event event = (Event) sbase;
				listOfElementsToWrite = new ArrayList<Object>();

				if (event.isSetTrigger()) {
					listOfElementsToWrite.add(event.getTrigger());
				}
				if (event.isSetPriority()) {
					listOfElementsToWrite.add(event.getPriority());
				}
				if (event.isSetDelay()) {
					listOfElementsToWrite.add(event.getDelay());
				}
				if (event.isSetListOfEventAssignments()) {
					listOfElementsToWrite.add(event.getListOfEventAssignments());
				}

				if (listOfElementsToWrite.isEmpty()) {
					listOfElementsToWrite = null;
				}
			}

			/*
			 * // Level 3 packages support 
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
			 * catch (InstantiationException e) { //  Auto-generated catch
			 * block e.printStackTrace(); } catch (IllegalAccessException e) {
			 * //  Auto-generated catch block e.printStackTrace(); } SBase
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
		return SBMLDocument.URI_NAMESPACE_L3V1Core;
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
			Object contextObject) 
	{

		if (log4jLogger.isDebugEnabled()) {
			log4jLogger.debug(" process the attribute : '" + attributeName + "' (value = " + value + ") on element '" + elementName + "' (" + contextObject + ").");
		}
		
		boolean isAttributeRead = false;

		// A SBMLCoreParser can modify a contextObject which is an instance of
		// SBase.
		// Try to read the attributes.
		if (contextObject instanceof SBase) {
			SBase sbase = (SBase) contextObject;
			try {
				isAttributeRead = sbase.readAttribute(attributeName, prefix,
						value);
			} catch (Throwable exc) {
				log4jLogger.error(exc.getMessage());
				log4jLogger.info("Attribute = " + attributeName + ", element = " + elementName);
			}
		}
		// A SBMLCoreParser can modify a contextObject which is an instance of
		// Annotation.
		// Try to read the attributes.
		else if (contextObject instanceof Annotation) {
			Annotation annotation = (Annotation) contextObject;
			isAttributeRead = annotation.readAttribute(attributeName, prefix,
					value);
		} else if (contextObject instanceof ASTNode) {
			ASTNode astNode = (ASTNode) contextObject;
			
			try {
				astNode.setUnits(value);
				isAttributeRead = true;
			} catch (IllegalArgumentException e) {
				// TODO : Write from which element the error is coming from : astNode.getParentSBMLObject();
				log4jLogger.info(e.getMessage());
				// Log the error to the ErrorLog object ??
			}
			
			log4jLogger.debug("SBMLCoreParser : processAttribute : adding an unit to an ASTNode");
		}

		if (!isAttributeRead) {
			log4jLogger.warn(String.format(
					"Could not recognize the attribute '%s' on the element %s. Please check the SBML specifications.",
					attributeName, elementName));
			//  Log the error to the ErrorLog object ??
		}
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processCharactersOf(String
	 * elementName, String characters, Object contextObject)
	 */
	public void processCharactersOf(String elementName, String characters,
			Object contextObject) 
	{
		
		// TODO : we have to check if we are in the context of a Notes or an Annotation
		
		if (elementName != null && elementName.equals("notes")) {
			
		} else if (characters != null && characters.trim().length() != 0) {
			// log4jLogger.warn("The SBML core XML element should not have any content, everything should be stored as attribute.");
			// log4jLogger.warn("The Characters are : @" + characters.trim() + "@");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processEndDocument(SBMLDocument
	 * sbmlDocument)
	 */
	public void processEndDocument(SBMLDocument sbmlDocument) {

		log4jLogger.debug("SBMLCoreParser : processEndDocument");
		
		if (sbmlDocument.isSetModel()) {
			Model model = sbmlDocument.getModel();

			if (model.isSetAreaUnits() && !model.isSetAreaUnitsInstance()) {
				log4jLogger.warn("No unitDefinition matches the areaUnitsID of Model.");
			}
			if (model.isSetConversionFactor()
					&& !model.isSetConversionFactorInstance()) {
				log4jLogger.warn("No parameter matches the conversionFactorID of Model.");
			}
			if (model.isSetExtentUnits() && !model.isSetExtentUnitsInstance()) {
				log4jLogger.warn("No unitDefinition matches the extentUnitsID of Model.");
			}
			if (model.isSetLengthUnits() && !model.isSetLengthUnitsInstance()) {
				log4jLogger.warn("No unitDefinition matches the lengthUnitsID of Model.");
			}
			if (model.isSetSubstanceUnits()
					&& !model.isSetSubstanceUnitsInstance()) {
				log4jLogger.warn("No unitDefinition matches the substanceUnitsID of Model.");
			}
			if (model.isSetTimeUnits() && !model.isSetTimeUnitsInstance()) {
				log4jLogger.warn("No unitDefinition matches the timeUnitsID of Model.");
			}
			if (model.isSetVolumeUnits() && !model.isSetVolumeUnitsInstance()) {
				log4jLogger.warn("No unitDefinition matches the volumeUnitsID of Model.");
			}

			if (model.isSetListOfRules()) {
				
				if (model.getLevel() == 1) {

					log4jLogger.debug("Transformed SBMLLevel1Rule : processEndDocument : model is level 1");

					int i = 0;
					for (Rule rule : model.getListOfRules().clone()) {
						if (rule instanceof SBMLLevel1Rule) {
							Rule realRule;
							
							if (((SBMLLevel1Rule) rule).isScalar()) {
								realRule = ((SBMLLevel1Rule) rule).cloneAsAssignmentRule();
								log4jLogger.debug("Transformed SBMLLevel1Rule : " + ((SBMLLevel1Rule) rule).getVariable() + " into AssignmentRule.");
							} else {
								realRule = ((SBMLLevel1Rule) rule).cloneAsRateRule();
								log4jLogger.debug("Transformed SBMLLevel1Rule : " + ((SBMLLevel1Rule) rule).getVariable() + " into RateRule.");
							}
							
							log4jLogger.debug("Transformed SBMLLevel1Rule : realRule = " + realRule);

							model.getListOfRules().remove(i);
							model.getListOfRules().add(i, realRule);
						}
						i++;
					}
				}
				
				for (int i = 0; i < model.getRuleCount(); i++) {
					Rule rule = model.getRule(i);
					if (rule instanceof AssignmentRule) {
						AssignmentRule assignmentRule = (AssignmentRule) rule;
						if (assignmentRule.isSetVariable()
								&& !assignmentRule.isSetVariableInstance()) {
							log4jLogger.warn("No Symbol matches the variableID of AssignmentRule.");
						}
						if (assignmentRule.isSetUnits()
								&& !assignmentRule.isSetUnitsInstance()
								&& assignmentRule.isParameter()) {
							log4jLogger.warn("No UnitDefinition matches the unitsID of AssignmentRule.");
						}
					} else if (rule instanceof RateRule) {
						RateRule rateRule = (RateRule) rule;
						if (rateRule.isSetVariable()
								&& !rateRule.isSetVariableInstance()) {
							log4jLogger.warn("No Symbol matches the variableID of RateRule.");
						}
					}
				}
			}
			if (model.isSetListOfCompartments()) {
				for (int i = 0; i < model.getCompartmentCount(); i++) {
					Compartment compartment = model.getCompartment(i);
					if (compartment.isSetCompartmentType()
							&& !compartment.isSetCompartmentTypeInstance()) {
						log4jLogger.warn("No CompartmentType matches the compartmentTypeID of compartment.");
					}
					if (compartment.isSetOutside()
							&& !compartment.isSetOutsideInstance()) {
						log4jLogger.warn("No Compartment matches the outsideID of compartment.");
					}
					if (compartment.isSetUnits()
							&& !compartment.isSetUnitsInstance()) {
						log4jLogger.warn("No UnitDefinition matches the unitsID of compartment.");
					}
				}
			}
			if (model.isSetListOfEvents()) {
				for (int i = 0; i < model.getEventCount(); i++) {
					Event event = model.getEvent(i);

					if (event.isSetTimeUnits()
							&& !event.isSetTimeUnitsInstance()) {
						log4jLogger.warn("No UnitDefinition matches the timeUnitsID of event.");
					}

					if (event.isSetListOfEventAssignments()) {

						for (int j = 0; j < event.getEventAssignmentCount(); j++) {
							EventAssignment eventAssignment = event
									.getEventAssignment(j);

							if (eventAssignment.isSetVariable()
									&& !eventAssignment.isSetVariableInstance()) {
								log4jLogger.warn("No Symbol matches the variableID of eventAssignment.");
							}
						}
					}
				}
			}
			if (model.isSetListOfInitialAssignments()) {
				for (int i = 0; i < model.getInitialAssignmentCount(); i++) {
					InitialAssignment initialAssignment = model
							.getInitialAssignment(i);

					if (initialAssignment.isSetVariable()
							&& !initialAssignment.isSetVariableInstance()) {
						log4jLogger.warn("No Symbol matches the symbolID of initialAssignment.");
					}
				}
			}
			if (model.isSetListOfReactions()) {
				for (int i = 0; i < model.getReactionCount(); i++) {
					Reaction reaction = model.getReaction(i);
					if (reaction.isSetCompartment()
							&& !reaction.isSetCompartmentInstance()) {
						log4jLogger.warn("No Compartment matches the compartmentID of reaction.");
					}

					if (reaction.isSetListOfReactants()) {
						for (int j = 0; j < reaction.getReactantCount(); j++) {
							SpeciesReference speciesReference = reaction
									.getReactant(j);

							if (speciesReference.isSetSpecies()
									&& !speciesReference.isSetSpeciesInstance()) {
								log4jLogger.warn(String.format("No Species matches the speciesID '%s' of %s.", 
										speciesReference.getId(), speciesReference.getElementName()));
							}
						}
					}
					if (reaction.isSetListOfProducts()) {
						for (int j = 0; j < reaction.getProductCount(); j++) {
							SpeciesReference speciesReference = reaction
									.getProduct(j);

							if (speciesReference.isSetSpecies()
									&& !speciesReference.isSetSpeciesInstance()) {
								log4jLogger.warn(String.format("No Species matches the speciesID '%s' of %s.", 
										speciesReference.getId(), speciesReference.getElementName()));
							}
						}
					}
					if (reaction.isSetListOfModifiers()) {
						for (int j = 0; j < reaction.getModifierCount(); j++) {
							ModifierSpeciesReference modifierSpeciesReference = reaction
									.getModifier(j);

							if (modifierSpeciesReference.isSetSpecies()
									&& !modifierSpeciesReference
											.isSetSpeciesInstance()) {
								log4jLogger.warn(String.format("No Species matches the speciesID '%s' of %s.", 
										modifierSpeciesReference.getId(), modifierSpeciesReference.getElementName()));
							}
						}
					}
					if (reaction.isSetKineticLaw()) {
						KineticLaw kineticLaw = reaction.getKineticLaw();
						if (kineticLaw.isSetTimeUnits()
								&& !kineticLaw.isSetTimeUnitsInstance()) {
							log4jLogger.warn("No UnitDefinition matches the timeUnitsID of kineticLaw.");
						}
						if (kineticLaw.isSetSubstanceUnits()
								&& !kineticLaw.isSetSubstanceUnitsInstance()) {
							log4jLogger.warn("No UnitDefinition matches the substanceUnitsID of kineticLaw.");
						}
						if (kineticLaw.isSetListOfLocalParameters()) {
							for (int j = 0; j < kineticLaw.getLocalParameterCount(); j++) {
								LocalParameter parameter = kineticLaw
										.getLocalParameter(j);
								if (parameter.isSetUnits()
										&& !parameter.isSetUnitsInstance()) {
									log4jLogger.warn(String.format(
										"No UnitDefinition matches the unitsID '%s'of the parameter %s.",
										parameter.getUnits(), parameter.getId()));
								}
							}
						}
					}
				}
			}
			if (model.isSetListOfSpecies()) {
				for (int i = 0; i < model.getSpeciesCount(); i++) {
					Species species = model.getSpecies(i);

					if (species.isSetSubstanceUnits()
							&& !species.isSetSubstanceUnitsInstance()) {
						log4jLogger.warn("No UnitDefinition matches the subtsanceUnitsID of species.");
					}
					if (species.isSetSpeciesType()
							&& !species.isSetSpeciesTypeInstance()) {
						log4jLogger.warn("No SpeciesType matches the speciesTypeID of species.");
					}
					if (species.isSetConversionFactor()
							&& !species.isSetConversionFactorInstance()) {
						log4jLogger.warn("No Parameter matches the conversionFactorID of species.");
					}
					if (species.isSetCompartment()
							&& !species.isSetCompartmentInstance()) {
						log4jLogger.warn("No Compartment matches the compartmentID of species.");
					}
					if (species.isSetSpatialSizeUnits()
							&& !species.isSetSpatialSizeUnitsInstance()) {
						log4jLogger.warn("No UnitDefinition matches the spatialSizeUnitsID of species.");
					}
				}
			}
			if (model.isSetListOfParameters()) {
				for (int i = 0; i < model.getParameterCount(); i++) {
					Parameter parameter = model.getParameter(i);
					if (parameter.isSetUnits()
							&& !parameter.isSetUnitsInstance()) {
						log4jLogger.warn("No UnitDefinition matches the unitsID of parameter.");
					}
				}
			}

		} else {
			log4jLogger.error("The Model element was not been created");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processEndElement(String
	 * elementName, String prefix, boolean isNested, Object contextObject)
	 */
	public boolean processEndElement(String elementName, String prefix,
			boolean isNested, Object contextObject) 
	{
		return true;
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
			Object contextObject) 
	{

		if (contextObject instanceof SBMLDocument) {
			SBMLDocument sbmlDocument = (SBMLDocument) contextObject;

			sbmlDocument.addNamespace(localName, prefix, URI);
			
			log4jLogger.debug("processNamespace : " + prefix + " = " + URI);
		} 
		else if (contextObject instanceof SBase) {
			SBase sbase = (SBase) contextObject;
			
			if (prefix != null && prefix.length() > 0) {
				sbase.addDeclaredNamespace(prefix + ":" + localName, URI);
			} else {
				sbase.addDeclaredNamespace(localName, URI);
			}

			log4jLogger.debug("processNamespace : " + prefix + " = " + URI);
		} 
		else if (contextObject instanceof Annotation) {
			Annotation sbase = (Annotation) contextObject;
			sbase.addAnnotationNamespace(localName, prefix, URI);
			
			log4jLogger.debug("processNamespace : " + prefix + " = " + URI);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processStartElement(String
	 * elementName, String prefix, boolean hasAttributes, boolean hasNamespaces,
	 * Object contextObject)
	 */
	@SuppressWarnings("unchecked")
	public Object processStartElement(String elementName, String prefix,
			boolean hasAttributes, boolean hasNamespaces, Object contextObject) {

		// some of the warning logs could be added in the ErrorLog also ??
		
		// All the possible elements name should be present in the HashMap
		// sbmlCoreElements of this parser.
		if (sbmlCoreElements.containsKey(elementName)) {
			try {
				
				Object newContextObject = sbmlCoreElements.get(elementName).newInstance();
				
				if (contextObject instanceof SBase) {
					setLevelAndVersionFor(newContextObject,
							(SBase) contextObject);
				}

				if (elementName.equals("notes")
						&& (contextObject instanceof SBase)) {
					SBase sbase = (SBase) contextObject;
					sbase.setNotes(new XMLNode(new XMLTriple("notes", null, null), new XMLAttributes()));
				} else if (elementName.equals("annotation")
						&& (contextObject instanceof SBase)) {
					SBase sbase = (SBase) contextObject;
					Annotation annotation = (Annotation) newContextObject;
					sbase.setAnnotation(annotation);

					return annotation;
				} else if (contextObject instanceof SBMLDocument) {
					SBMLDocument sbmlDocument = (SBMLDocument) contextObject;
					if (elementName.equals("model")) {
						Model model = (Model) newContextObject;
						model.setLevel(sbmlDocument.getLevel());
						model.setVersion(sbmlDocument.getVersion());
						model.initDefaults();
						model.setParentSBML(sbmlDocument);
						sbmlDocument.setModel(model);

						return model;
					}
				} else if (contextObject instanceof Model) {

					Model model = (Model) contextObject;
					if (newContextObject instanceof ListOf<?>) {
						if (elementName.equals("listOfFunctionDefinitions")
								&& model.getLevel() > 1) {
							ListOf<FunctionDefinition> listOfFunctionDefinitions = (ListOf<FunctionDefinition>) newContextObject;
							model.setListOfFunctionDefinitions(listOfFunctionDefinitions);

							return listOfFunctionDefinitions;
						} else if (elementName.equals("listOfUnitDefinitions")) {
							ListOf<UnitDefinition> listOfUnitDefinitions = (ListOf<UnitDefinition>) newContextObject;
							model.setListOfUnitDefinitions(listOfUnitDefinitions);

							return listOfUnitDefinitions;
						} else if (elementName.equals("listOfCompartments")) {
							ListOf<Compartment> listOfCompartments = (ListOf<Compartment>) newContextObject;
							model.setListOfCompartments(listOfCompartments);

							return listOfCompartments;
						} else if (elementName.equals("listOfSpecies")) {
							ListOf<Species> listOfSpecies = (ListOf<Species>) newContextObject;
							model.setListOfSpecies(listOfSpecies);

							return listOfSpecies;
						} else if (elementName.equals("listOfParameters")) {
							ListOf<Parameter> listOfParameters = (ListOf<Parameter>) newContextObject;
							model.setListOfParameters(listOfParameters);

							return listOfParameters;
						} else if (elementName.equals("listOfInitialAssignments")
								&& ((model.getLevel() == 2 && model.getVersion() > 1) 
										|| model.getLevel() >= 3)) {
							ListOf<InitialAssignment> listOfInitialAssignments = (ListOf<InitialAssignment>) newContextObject;
							model.setListOfInitialAssignments(listOfInitialAssignments);

							return listOfInitialAssignments;
						} else if (elementName.equals("listOfRules")) {
							ListOf<Rule> listOfRules = (ListOf<Rule>) newContextObject;
							model.setListOfRules(listOfRules);

							return listOfRules;
						} else if (elementName.equals("listOfConstraints")
								&& ((model.getLevel() == 2 && model.getVersion() > 1) 
										|| model.getLevel() >= 3)) {
							ListOf<Constraint> listOfConstraints = (ListOf<Constraint>) newContextObject;
							model.setListOfConstraints(listOfConstraints);

							return listOfConstraints;
						} else if (elementName.equals("listOfReactions")) {
							ListOf<Reaction> listOfReactions = (ListOf<Reaction>) newContextObject;
							model.setListOfReactions(listOfReactions);

							return listOfReactions;
						} else if (elementName.equals("listOfEvents")
								&& model.getLevel() > 1) {
							ListOf<Event> listOfEvents = (ListOf<Event>) newContextObject;
							model.setListOfEvents(listOfEvents);

							return listOfEvents;
						} else if (elementName.equals("listOfCompartmentTypes")
								&& (model.getLevel() == 2 && model.getVersion() > 1)) {
							ListOf<CompartmentType> listOfCompartmentTypes = (ListOf<CompartmentType>) newContextObject;
							model.setListOfCompartmentTypes(listOfCompartmentTypes);

							return listOfCompartmentTypes;
						} else if (elementName.equals("listOfSpeciesTypes")
								&& (model.getLevel() == 2 && model.getVersion() > 1)) {
							ListOf<SpeciesType> listOfSpeciesTypes = (ListOf<SpeciesType>) newContextObject;
							model.setListOfSpeciesTypes(listOfSpeciesTypes);

							return listOfSpeciesTypes;
						} else {
							log4jLogger.warn("The element " + elementName + " is not recognized");
						}
					} else {
						log4jLogger.warn("The element " + elementName + " is not recognized");
					}
				} else if (contextObject instanceof ListOf<?>) {
					ListOf<?> list = (ListOf<?>) contextObject;
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
							model.addUnitDefinition(unitDefinition);

							return unitDefinition;
						} else if (elementName.equals("compartment")
								&& list.getSBaseListType().equals(
										ListOf.Type.listOfCompartments)) {
							Compartment compartment = (Compartment) newContextObject;
							compartment.initDefaults();
							model.addCompartment(compartment);

							return compartment;
						} else if (elementName.equals("species")
								&& list.getSBaseListType().equals(
										ListOf.Type.listOfSpecies)
								&& ((model.getLevel() == 1 && model
										.getVersion() > 1) || model.getLevel() > 1)) {
							Species species = (Species) newContextObject;
							species.initDefaults();
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
							species.initDefaults();
							model.addSpecies(species);

							return species;
						} else if (elementName.equals("parameter")
								&& list.getSBaseListType().equals(
										ListOf.Type.listOfParameters)) {
							Parameter parameter = (Parameter) newContextObject;
							parameter.initDefaults();
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
								&& list.getSBaseListType().equals(ListOf.Type.listOfRules)
								&& model.getLevel() == 1) 
						{
							ExplicitRule rule = (ExplicitRule) newContextObject;
							model.addRule(rule);

							return rule;
						} else if (elementName.equals("specieConcentrationRule")
								&& list.getSBaseListType().equals(ListOf.Type.listOfRules)
								&& model.getLevel() == 1
								&& model.getVersion() == 1) 
						{
							ExplicitRule rule = (ExplicitRule) newContextObject;
							model.addRule(rule);

							return rule;
						} else if (elementName.equals("speciesConcentrationRule")
								&& list.getSBaseListType().equals(ListOf.Type.listOfRules)
								&& model.getLevel() == 1
								&& model.getVersion() == 2) 
						{
							ExplicitRule rule = (ExplicitRule) newContextObject;
							model.addRule(rule);

							return rule;
						} else if (elementName.equals("compartmentVolumeRule")
								&& list.getSBaseListType().equals(ListOf.Type.listOfRules)
								&& model.getLevel() == 1) 
						{
							ExplicitRule rule = (ExplicitRule) newContextObject;
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
							reaction.initDefaults();

							return reaction;
						} else if (elementName.equals("event")
								&& list.getSBaseListType().equals(
										ListOf.Type.listOfEvents)
								&& model.getLevel() > 1) {
							Event event = (Event) newContextObject;
							model.addEvent(event);
							event.initDefaults();

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
							log4jLogger.warn("The element " + elementName + " is not recognized");
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
							log4jLogger.warn("The element " + elementName + " is not recognized");
						}
					} else if (list.getParentSBMLObject() instanceof Reaction) {
						Reaction reaction = (Reaction) list
								.getParentSBMLObject();

						if (elementName.equals("speciesReference")
								&& (reaction.getLevel() > 1 || (reaction
										.getLevel() == 1 && reaction
										.getVersion() == 2))) {
							SpeciesReference speciesReference = (SpeciesReference) newContextObject;
							speciesReference.initDefaults();

							if (list.getSBaseListType().equals(
									ListOf.Type.listOfReactants)) {
								reaction.addReactant(speciesReference);

								return speciesReference;
							} else if (list.getSBaseListType().equals(
									ListOf.Type.listOfProducts)) {
								reaction.addProduct(speciesReference);

								return speciesReference;
							} else {
								log4jLogger.warn("The element " + elementName + " is not recognized");
							}
						} else if (elementName.equals("specieReference")
								&& reaction.getLevel() == 1) {
							SpeciesReference speciesReference = (SpeciesReference) newContextObject;
							speciesReference.initDefaults();

							if (list.getSBaseListType().equals(
									ListOf.Type.listOfReactants)) {
								reaction.addReactant(speciesReference);

								return speciesReference;
							} else if (list.getSBaseListType().equals(
									ListOf.Type.listOfProducts)) {
								reaction.addProduct(speciesReference);

								return speciesReference;
							} else {
								log4jLogger.warn("The element " + elementName + " is not recognized");
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
							log4jLogger.warn("The element " + elementName + " is not recognized");
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
							kineticLaw.addLocalParameter(localParameter);

							return localParameter;
						} else if (elementName.equals("parameter")
								&& list.getSBaseListType().equals(
										ListOf.Type.listOfLocalParameters)
								&& kineticLaw.isSetLevel()
								&& kineticLaw.getLevel() < 3) {
							LocalParameter localParameter = new LocalParameter(
									(Parameter) newContextObject);
							kineticLaw.addLocalParameter(localParameter);

							return localParameter;
						} else {
							log4jLogger.warn("The element " + elementName + " is not recognized");
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
							log4jLogger.warn("The element " + elementName + " is not recognized");
						}
					} else {
						log4jLogger.warn("The element " + elementName + " is not recognized");
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

					if (elementName.equals("listOfEventAssignments")) {
						ListOf<EventAssignment> listOfEventAssignments = (ListOf<EventAssignment>) newContextObject;
						event.setListOfEventAssignments(listOfEventAssignments);

						return listOfEventAssignments;
					} else if (elementName.equals("trigger")) {
						Trigger trigger = (Trigger) newContextObject;
						event.setTrigger(trigger);

						return trigger;
					} else if (elementName.equals("delay")) {
						Delay delay = (Delay) newContextObject;
						event.setDelay(delay);

						return delay;
					} else if (elementName.equals("priority")) {
						Priority priority = (Priority) newContextObject;
						event.setPriority(priority);

						return priority;
					} else {
						log4jLogger.warn("The element " + elementName + " is not recognized");
					}
				} else if (contextObject instanceof Reaction) {
					Reaction reaction = (Reaction) contextObject;
					if (elementName.equals("listOfReactants")) {
						ListOf<SpeciesReference> listOfReactants = (ListOf<SpeciesReference>) newContextObject;
						reaction.setListOfReactants(listOfReactants);

						return listOfReactants;
					} else if (elementName.equals("listOfProducts")) {
						ListOf<SpeciesReference> listOfProducts = (ListOf<SpeciesReference>) newContextObject;
						reaction.setListOfProducts(listOfProducts);

						return listOfProducts;
					} else if (elementName.equals("listOfModifiers")
							&& reaction.getLevel() > 1) {
						ListOf<ModifierSpeciesReference> listOfModifiers = (ListOf<ModifierSpeciesReference>) newContextObject;
						reaction.setListOfModifiers(listOfModifiers);

						return listOfModifiers;
					} else if (elementName.equals("kineticLaw")) {
						KineticLaw kineticLaw = (KineticLaw) newContextObject;
						reaction.setKineticLaw(kineticLaw);

						return kineticLaw;
					} else {
						log4jLogger.warn("The element " + elementName + " is not recognized");
					}
				} else if (contextObject instanceof SpeciesReference) {
					SpeciesReference speciesReference = (SpeciesReference) contextObject;

					if (elementName.equals("stoichiometryMath")) {
						StoichiometryMath stoichiometryMath = (StoichiometryMath) newContextObject;
						speciesReference.setStoichiometryMath(stoichiometryMath);

						return stoichiometryMath;
					} else {
						log4jLogger.warn("The element " + elementName + " is not recognized");
					}
				} else if (contextObject instanceof KineticLaw) {
					KineticLaw kineticLaw = (KineticLaw) contextObject;

					if (elementName.equals("listOfLocalParameters")
							&& kineticLaw.getLevel() >= 3) {
						ListOf<LocalParameter> listOfLocalParameters = (ListOf<LocalParameter>) newContextObject;
						kineticLaw.setListOfLocalParameters(listOfLocalParameters);
						listOfLocalParameters.setSBaseListType(ListOf.Type.listOfLocalParameters);

						return listOfLocalParameters;
					} else if (elementName.equals("listOfParameters")
							&& kineticLaw.isSetLevel() && kineticLaw.getLevel() < 3) {
						ListOf<LocalParameter> listOfLocalParameters = (ListOf<LocalParameter>) newContextObject;
						kineticLaw.setListOfLocalParameters(listOfLocalParameters);
						listOfLocalParameters.setSBaseListType(ListOf.Type.listOfLocalParameters);

						return listOfLocalParameters;
					} else {
						log4jLogger.warn("The element " + elementName + " is not recognized");
					}
				} else if (contextObject instanceof Constraint) {
					Constraint constraint = (Constraint) contextObject;

					if (elementName.equals("message")
							&& ((constraint.getLevel() == 2 && constraint
									.getVersion() > 1) || constraint.getLevel() >= 3)) 
					{
						constraint.setMessage(new XMLNode(new XMLTriple("message", null, null), new XMLAttributes()));

						return constraint;
					} else {
						log4jLogger.warn("The element " + elementName + " is not recognized");
					}
				} else {
					log4jLogger.warn("The element " + elementName + " is not recognized");
				}
			} catch (InstantiationException e) {
				log4jLogger.error("The element " + elementName + " could not be instanciated as a Java object !!");
				log4jLogger.debug(e.getMessage());
				if (log4jLogger.isDebugEnabled()) {
					e.getStackTrace();
				}
			} catch (IllegalAccessException e) {
				log4jLogger.error("The element " + elementName + " could not be instanciated as a Java object !!");
				log4jLogger.debug(e.getMessage());
				if (log4jLogger.isDebugEnabled()) {
					e.getStackTrace();
				}
			}
		}
		return contextObject;
	}

	/**
	 * Sets level and version properties of the new object according to the
	 * value in the model.
	 * 
	 * @param newContextObject
	 * @param parent
	 */
	private void setLevelAndVersionFor(Object newContextObject, SBase parent) {
		if (newContextObject instanceof SBase) {
			SBase sb = (SBase) newContextObject;
			// Level and version will be -1 if not set, so we don't
			// have to check.
			sb.setLevel(parent.getLevel());
			sb.setVersion(parent.getVersion());
		}
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
		// The method should never be called !
		log4jLogger.warn("The SBML core XML element should not have any content, everything should be stored as attribute.");
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
				xmlObject.setName(sbase.getElementName());
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
