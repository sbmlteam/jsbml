/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2018 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 5. The Babraham Institute, Cambridge, UK
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;
import org.mangosdk.spi.ProviderFor;
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
import org.sbml.jsbml.util.ResourceManager;
import org.sbml.jsbml.util.SBMLtools;
import org.sbml.jsbml.util.TreeNodeWithChangeSupport;
import org.sbml.jsbml.util.filters.Filter;
import org.sbml.jsbml.xml.XMLAttributes;
import org.sbml.jsbml.xml.XMLNode;
import org.sbml.jsbml.xml.XMLTriple;
import org.sbml.jsbml.xml.stax.SBMLObjectForXML;

/**
 * Parses the SBML core elements of an SBML file. It can read and write SBML
 * core elements (implements {@link ReadingParser} and {@link WritingParser}).
 * 
 * @author Nicolas Rodriguez
 * @author Andreas Dr&auml;ger
 * @author Marine Dumousseau
 * @since 0.8
 */
// It might be better to have one parser per level and version
// rather than one SBMLCoreParser which parses everything.

@SuppressWarnings("deprecation")
@ProviderFor(ReadingParser.class)
public class SBMLCoreParser implements ReadingParser, WritingParser {

  /**
   * This map contains all the relationships XML element name <=> matching
   * java class.
   */
  private Map<String, Class<? extends Object>> sbmlCoreElements;

  /**
   * Log4j logger
   */
  private static final transient Logger logger = Logger.getLogger(SBMLCoreParser.class);

  /**
   * Localization support.
   */
  private static final transient ResourceBundle bundle = ResourceManager.getBundle("org.sbml.jsbml.resources.cfg.Messages");

  /**
   * Creates a SBMLCoreParser instance. Initializes the sbmlCoreElements of
   * this PackageParser.
   * 
   */
  public SBMLCoreParser() {
    sbmlCoreElements = new HashMap<String, Class<? extends Object>>();
    // Initializes the sbmlCoreElements of this parser.
    JSBML.loadClasses("org/sbml/jsbml/resources/cfg/SBMLCoreElements.xml",
      sbmlCoreElements);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.WritingParser#getListOfSBMLElementsToWrite(Object sbase)
   */
  @Override
  public List<Object> getListOfSBMLElementsToWrite(Object sbase)
  {
    ArrayList<Object> listOfElementsToWrite = null;

    if (sbase instanceof TreeNode) {
      TreeNode treeNode = (TreeNode) sbase;
      int nbChild = treeNode.getChildCount();

      if (nbChild > 0) {
        listOfElementsToWrite = new ArrayList<Object>();

        for (int i = 0; i < nbChild; i++) {
          listOfElementsToWrite.add(treeNode.getChildAt(i));
        }
      }
    }

    return listOfElementsToWrite;
  }

  /**
   * 
   * @return the namespace URI of this parser.
   */
  public String getNamespaceURI() {
    return SBMLDocument.URI_NAMESPACE_L3V1Core;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.ReadingParser#processAttribute(String elementName, String attributeName, String value, String prefix, boolean isLastAttribute, Object contextObject)
   */
  @Override
  public boolean processAttribute(String elementName, String attributeName,
    String value, String uri, String prefix, boolean isLastAttribute,
    Object contextObject)
  {

    if (logger.isDebugEnabled()) {
      logger.debug(MessageFormat.format(
        "processing the attribute: ''{0}'' (value = {1}) on element ''{2}'' ({3}).",
        attributeName, value, elementName, contextObject));
    }

    boolean isAttributeRead = false;

    // A SBMLCoreParser can modify a contextObject which is an instance of
    // SBase.
    // Try to read the attributes.
    if (contextObject instanceof SBase) {
      SBase sbase = (SBase) contextObject;
      try {
        isAttributeRead = sbase.readAttribute(attributeName, prefix, value);
      } catch (Throwable exc) {
        logger.error(exc.getMessage());
        logger.info("Attribute = " + attributeName + ", value '" + value + "' , element name = " + elementName);
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

      // TODO - make sure that the attributeName is 'units' !! If not put the attribute into the unknown attributes.
      
      // TODO - test if the elementName is one of the ignored mathML elements. If yes, where to store the attribute !?
      
      try {
        astNode.setUnits(value);
        isAttributeRead = true;
      } catch (IllegalArgumentException e) {
        // TODO: Write from which element the error is coming from: astNode.getParentSBMLObject();
        logger.warn(e.getMessage());
        // Log the error to the ErrorLog object ??
      }

      logger.debug("SBMLCoreParser: processAttribute: adding an unit to an ASTNode");
    }

    if (!isAttributeRead) {
      // TODO: Here we should add a hint which SBML Level/Version combination is used.
      logger.warn(MessageFormat.format(
        bundle.getString("SBMLCoreParser.unknownAttributeOnElement"), // Level {2,number,integer} Version {3,number,integer}
        attributeName, elementName));
      //  Log the error to the ErrorLog object ??

      // TODO : store the unknownAttribute -> do a generic utility method on TreeNode element
      // Done in SBMLReader !! AbstractReaderWriter.processUnknownAttribute(attributeName, value, prefix, contextObject); // namespace ?
    }
    
    return isAttributeRead;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.ReadingParser#processCharactersOf(String elementName, String characters, Object contextObject)
   */
  @Override
  public void processCharactersOf(String elementName, String characters,
    Object contextObject)
  {

    // TODO: we have to check if we are in the context of a Notes or an Annotation

    if ((elementName != null) && elementName.equals("notes")) {

    } else if ((characters != null) && (characters.trim().length() != 0)) {
      // logger.warn("The SBML core XML element should not have any content, everything should be stored as attribute.");
      // logger.warn("The Characters are: @" + characters.trim() + "@");
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.ReadingParser#processEndDocument(SBMLDocument sbmlDocument)
   */
  @Override
  public void processEndDocument(SBMLDocument sbmlDocument) {

    logger.debug("SBMLCoreParser: processEndDocument");

    if (sbmlDocument.isSetModel()) {
      Model model = sbmlDocument.getModel();

      if (model.isSetAreaUnits() && !model.isSetAreaUnitsInstance()) {
        logger.warn(MessageFormat.format(
          bundle.getString("SBMLCoreParser.unknownReferenceError1"),
          "UnitDefinition", "areaUnitsID", model.getAreaUnits(),
          model.getElementName(), SBMLtools.getIdOrName(model)));
      }
      if (model.isSetConversionFactor()
          && !model.isSetConversionFactorInstance()) {
        logger.warn(MessageFormat.format(
          bundle.getString("SBMLCoreParser.unknownReferenceError1"),
          "Parameter", "conversionFactorID", model.getConversionFactor(),
          model.getElementName(), SBMLtools.getIdOrName(model)));
      }
      if (model.isSetExtentUnits() && !model.isSetExtentUnitsInstance()) {
        logger.warn(MessageFormat.format(
          bundle.getString("SBMLCoreParser.unknownReferenceError1"),
          "UnitDefinition", "extentUnitsID", model.getExtentUnits(),
          model.getElementName(), SBMLtools.getIdOrName(model)));
      }
      if (model.isSetLengthUnits() && !model.isSetLengthUnitsInstance()) {
        logger.warn(MessageFormat.format(
          bundle.getString("SBMLCoreParser.unknownReferenceError1"),
          "UnitDefinition", "lengthUnitsID", model.getLengthUnits(),
          model.getElementName(), SBMLtools.getIdOrName(model)));
      }
      if (model.isSetSubstanceUnits()
          && !model.isSetSubstanceUnitsInstance()) {
        logger.warn(MessageFormat.format(
          bundle.getString("SBMLCoreParser.unknownReferenceError1"),
          "UnitDefinition", "substanceUnitsID", model.getSubstanceUnits(),
          model.getElementName(), SBMLtools.getIdOrName(model)));
      }
      if (model.isSetTimeUnits() && !model.isSetTimeUnitsInstance()) {
        logger.warn(MessageFormat.format(
          bundle.getString("SBMLCoreParser.unknownReferenceError1"),
          "UnitDefinition", "timeUnitsID", model.getTimeUnits(),
          model.getElementName(), SBMLtools.getIdOrName(model)));
      }
      if (model.isSetVolumeUnits() && !model.isSetVolumeUnitsInstance()) {
        logger.warn(MessageFormat.format(
          bundle.getString("SBMLCoreParser.unknownReferenceError1"),
          "UnitDefinition", "volumeUnitsID", model.getVolumeUnits(),
          model.getElementName(), SBMLtools.getIdOrName(model)));
      }

      if (model.isSetListOfRules()) {

        if (model.getLevel() == 1) {

          logger.debug("Transformed SBMLLevel1Rule: processEndDocument: model is level 1");

          int i = 0;
          for (Rule rule : model.getListOfRules().clone()) {
            if (rule instanceof SBMLLevel1Rule) {
              Rule realRule;

              if (((SBMLLevel1Rule) rule).isScalar()) {
                realRule = ((SBMLLevel1Rule) rule).cloneAsAssignmentRule();
                if (logger.isDebugEnabled()) {
                  logger.debug(MessageFormat.format(
                    "Transformed SBMLLevel1Rule: {0} into AssignmentRule.",
                    ((SBMLLevel1Rule) rule).getVariable()));
                }
              } else {
                realRule = ((SBMLLevel1Rule) rule).cloneAsRateRule();
                if (logger.isDebugEnabled()) {
                  logger.debug(MessageFormat.format(
                    "Transformed SBMLLevel1Rule: {0} into RateRule.",
                    ((SBMLLevel1Rule) rule).getVariable()));
                }
              }

              if (logger.isDebugEnabled()) {
                logger.debug(MessageFormat.format(
                  "Transformed SBMLLevel1Rule: realRule = {0}",
                  realRule));
              }

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
              logger.warn(MessageFormat.format(
                bundle.getString("SBMLCoreParser.unknownReferenceError2"),
                "Symbol", "variableID", assignmentRule.getVariable(),
                assignmentRule.getElementName()));
            }
            if (assignmentRule.isSetUnits()
                && !assignmentRule.isSetUnitsInstance()
                && assignmentRule.isParameter()) {
              logger.warn(MessageFormat.format(
                bundle.getString("SBMLCoreParser.unknownReferenceError2"),
                "UnitDefinition", "unitsID", assignmentRule.getUnits(),
                assignmentRule.getElementName()));
            }
          } else if (rule instanceof RateRule) {
            RateRule rateRule = (RateRule) rule;
            if (rateRule.isSetVariable()
                && !rateRule.isSetVariableInstance()) {
              logger.warn(MessageFormat.format(
                bundle.getString("SBMLCoreParser.unknownReferenceError3"),
                "Symbol", "variableID", rateRule.getVariable(),
                rateRule.getElementName()));
            }
          }
        }
      }
      if (model.isSetListOfCompartments()) {
        for (int i = 0; i < model.getCompartmentCount(); i++) {
          Compartment compartment = model.getCompartment(i);
          if (compartment.isSetCompartmentType()
              && !compartment.isSetCompartmentTypeInstance()) {
            logger.warn(MessageFormat.format(
              bundle.getString("SBMLCoreParser.unknownReferenceError1"),
              "CompartmentType", "compartmentTypeID", compartment.getCompartmentType(),
              compartment.getElementName(), SBMLtools.getIdOrName(compartment)));
          }
          if (compartment.isSetOutside()
              && !compartment.isSetOutsideInstance()) {
            logger.warn(MessageFormat.format(
              bundle.getString("SBMLCoreParser.unknownReferenceError1"),
              "Compartment", "outsideID", compartment.getOutside(),
              compartment.getElementName(), SBMLtools.getIdOrName(compartment)));
          }
          if (compartment.isSetUnits() && !compartment.isSetUnitsInstance()) {
            logger.warn(MessageFormat.format(
              bundle.getString("SBMLCoreParser.unknownReferenceError1"),
              "UnitDefinition", "unitsID", compartment.getUnits(),
              compartment.getElementName(), SBMLtools.getIdOrName(compartment)));
          }
        }
      }
      if (model.isSetListOfEvents()) {
        for (int i = 0; i < model.getEventCount(); i++) {
          Event event = model.getEvent(i);

          if (event.isSetTimeUnits() && !event.isSetTimeUnitsInstance()) {
            logger.warn(MessageFormat.format(
              bundle.getString("SBMLCoreParser.unknownReferenceError1"),
              "UnitDefinition", "timeUnitsID", event.getTimeUnits(),
              event.getElementName(), SBMLtools.getIdOrName(event)));
          }

          if (event.isSetListOfEventAssignments()) {

            for (int j = 0; j < event.getEventAssignmentCount(); j++) {
              EventAssignment eventAssignment = event
                  .getEventAssignment(j);

              if (eventAssignment.isSetVariable()
                  && !eventAssignment.isSetVariableInstance()) {
                logger.warn(MessageFormat.format(
                  bundle.getString("SBMLCoreParser.unknownReferenceError4"),
                  "Symbol", "variableID", eventAssignment.getVariable(),
                  eventAssignment.getElementName(), event.getElementName(),
                  SBMLtools.getIdOrName(event)));
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
            logger.warn(MessageFormat.format(
              bundle.getString("SBMLCoreParser.unknownReferenceError5"),
              "Symbol", "symbolID",
              initialAssignment.getVariable(),
              initialAssignment.getElementName()));
          }
        }
      }
      if (model.isSetListOfReactions()) {
        for (int i = 0; i < model.getReactionCount(); i++) {
          Reaction reaction = model.getReaction(i);
          if (reaction.isSetCompartment()
              && !reaction.isSetCompartmentInstance()) {
            logger.warn(MessageFormat.format(
              bundle.getString("SBMLCoreParser.unknownReferenceError1"),
              "Compartment", "compartmentID", reaction.getCompartment(),
              reaction.getElementName(), SBMLtools.getIdOrName(reaction)));
          }

          boolean neitherReactantsNorProducts = true;
          if (reaction.isSetListOfReactants()) {
            neitherReactantsNorProducts &= reaction.getReactantCount() == 0;
            for (int j = 0; j < reaction.getReactantCount(); j++) {
              SpeciesReference speciesReference = reaction.getReactant(j);

              if (speciesReference.isSetSpecies()
                  && !speciesReference.isSetSpeciesInstance()) {
                logger.warn(MessageFormat.format(
                  bundle.getString("SBMLCoreParser.unknownReferenceError1"),
                  "Species", "speciesID", speciesReference.getSpecies(),
                  speciesReference.getElementName(),
                  SBMLtools.getIdOrName(speciesReference)));
              }
            }
          }
          if (reaction.isSetListOfProducts()) {
            neitherReactantsNorProducts &= reaction.getProductCount() == 0;
            for (int j = 0; j < reaction.getProductCount(); j++) {
              SpeciesReference speciesReference = reaction.getProduct(j);

              if (speciesReference.isSetSpecies()
                  && !speciesReference.isSetSpeciesInstance()) {
                logger.warn(MessageFormat.format(
                  bundle.getString("SBMLCoreParser.unknownReferenceError1"),
                  "Species", "speciesID",
                  speciesReference.getSpecies(), speciesReference.getElementName(),
                  SBMLtools.getIdOrName(speciesReference)));
              }
            }
          }
          if (neitherReactantsNorProducts) {
            logger.warn(MessageFormat.format(
              bundle.getString("SBMLCoreParser.reactionWithoutParticipantsError"),
              SBMLtools.getIdOrName(reaction)));
          }
          if (reaction.isSetListOfModifiers()) {
            for (int j = 0; j < reaction.getModifierCount(); j++) {
              ModifierSpeciesReference modifierSpeciesReference = reaction
                  .getModifier(j);

              if (modifierSpeciesReference.isSetSpecies()
                  && !modifierSpeciesReference
                  .isSetSpeciesInstance()) {
                logger.warn(MessageFormat.format(
                  bundle.getString("SBMLCoreParser.unknownReferenceError1"),
                  "Species", "speciesID",
                  modifierSpeciesReference.getSpecies(),
                  modifierSpeciesReference.getElementName()));
              }
            }
          }
          if (reaction.isSetKineticLaw()) {
            KineticLaw kineticLaw = reaction.getKineticLaw();
            if (kineticLaw.isSetTimeUnits()
                && !kineticLaw.isSetTimeUnitsInstance()) {
              logger.warn(MessageFormat.format(
                bundle.getString("SBMLCoreParser.unknownReferenceError6"),
                "UnitDefinition", "timeUnitsID", kineticLaw.getTimeUnits(),
                kineticLaw.getElementName(), reaction.getElementName(),
                SBMLtools.getIdOrName(reaction)));
            }
            if (kineticLaw.isSetSubstanceUnits()
                && !kineticLaw.isSetSubstanceUnitsInstance()) {
              logger.warn(MessageFormat.format(
                bundle.getString("SBMLCoreParser.unknownReferenceError6"),
                "UnitDefinition", "substanceUnitsID",
                kineticLaw.getSubstanceUnits(), kineticLaw.getElementName(),
                reaction.getElementName(), SBMLtools.getIdOrName(reaction)));
            }
            if (kineticLaw.isSetListOfLocalParameters()) {
              for (int j = 0; j < kineticLaw.getLocalParameterCount(); j++) {
                LocalParameter parameter = kineticLaw
                    .getLocalParameter(j);
                if (parameter.isSetUnits()
                    && !parameter.isSetUnitsInstance()) {
                  logger.warn(MessageFormat.format(
                    bundle.getString("SBMLCoreParser.unknownReferenceError1"),
                    "UnitDefinition", "unitsID",
                    parameter.getUnits(), parameter.getElementName(),
                    SBMLtools.getIdOrName(parameter)));
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
            logger.warn(MessageFormat.format(
              bundle.getString("SBMLCoreParser.unknownReferenceError1"),
              "UnitDefinition", "subtsanceUnitsID",
              species.getSubstanceUnits(), species.getElementName(),
              SBMLtools.getIdOrName(species)));
          }
          if (species.isSetSpeciesType()
              && !species.isSetSpeciesTypeInstance()) {
            logger.warn(MessageFormat.format(
              bundle.getString("SBMLCoreParser.unknownReferenceError1"),
              "SpeciesType", "speciesTypeID", species.getSpeciesType(),
              species.getElementName(), SBMLtools.getIdOrName(species)));
          }
          if (species.isSetConversionFactor()
              && !species.isSetConversionFactorInstance()) {
            logger.warn(MessageFormat.format(
              bundle.getString("SBMLCoreParser.unknownReferenceError1"),
              "Parameter", "conversionFactorID", species.getConversionFactor(),
              species.getElementName(), SBMLtools.getIdOrName(species)));
          }
          if (species.isSetCompartment()
              && !species.isSetCompartmentInstance()) {
            logger.warn(MessageFormat.format(
              bundle.getString("SBMLCoreParser.unknownReferenceError1"),
              "Compartment", "compartmentID", species.getCompartment(),
              species.getElementName(), SBMLtools.getIdOrName(species)));
          }
          if (species.isSetSpatialSizeUnits()
              && !species.isSetSpatialSizeUnitsInstance()) {
            logger.warn(MessageFormat.format(
              bundle.getString("SBMLCoreParser.unknownReferenceError1"),
              "UnitDefinition", "spatialSizeUnitsID",
              species.getSpatialSizeUnits(), species.getElementName(),
              SBMLtools.getIdOrName(species)));
          }
        }
      }
      if (model.isSetListOfParameters()) {
        for (int i = 0; i < model.getParameterCount(); i++) {
          Parameter parameter = model.getParameter(i);
          if (parameter.isSetUnits()
              && !parameter.isSetUnitsInstance()) {
            logger.warn(MessageFormat.format(
              bundle.getString("SBMLCoreParser.unknownReferenceError1"),
              "UnitDefinition", "unitsID", parameter.getUnits(),
              parameter.getElementName(), SBMLtools.getIdOrName(parameter)));
          }
        }
      }

    } else { // no Model element defined
      // logger.error("The Model element has not been created."); // No need to log this error. And in L3V2, it is allowed to not have a Model element
    }

    // Go through the whole document (using a fake filter!) to remove the variable that says that we were in the process of reading an xml stream.
    sbmlDocument.filter(new Filter() {
      
      @Override
      public boolean accepts(Object o) {
        if (o instanceof TreeNodeWithChangeSupport) {
          if (((TreeNodeWithChangeSupport) o).isSetUserObjects()) {
            ((TreeNodeWithChangeSupport) o).userObjectKeySet().remove(JSBML.READING_IN_PROGRESS);
          } // else if (! ((o instanceof TreeNodeAdapter) || (o instanceof XMLNode))) {
//            System.out.println("######### user objects not set !!!!!!!! " + o + " class name = " + o.getClass().getSimpleName());
//          }
        }
        return false;
      }
    });
    
    logger.debug("Starting to check the package version and namespace for all package elements");
    // checks silently package version and namespace and try to fix any problems encountered.
    PackageUtil.checkPackages(sbmlDocument, true, true);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.ReadingParser#processEndElement(String elementName, String prefix, boolean isNested, Object contextObject)
   */
  @Override
  public boolean processEndElement(String elementName, String prefix,
    boolean isNested, Object contextObject)
  {
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.ReadingParser#processNamespace(String elementName, String URI, String prefix, String localName, boolean hasAttributes, boolean isLastNamespace, Object contextObject)
   */
  @Override
  public void processNamespace(String elementName, String URI, String prefix,
    String localName, boolean hasAttributes, boolean isLastNamespace,
    Object contextObject)
  {
    if (contextObject instanceof SBase) {
      SBase sbase = (SBase) contextObject;

      if (prefix != null && prefix.length() > 0) {
        sbase.addDeclaredNamespace(prefix + ":" + localName, URI);
      } else {
        sbase.addDeclaredNamespace(localName, URI);
      }
      if (logger.isDebugEnabled()) {
        logger.debug(MessageFormat.format(
          "processNamespace: {0} = {1}",
          prefix, URI));
      }
    }
    else if (contextObject instanceof Annotation)
    {
      Annotation annotation = (Annotation) contextObject;

      if ((prefix != null) && (prefix.length() > 0)) {
        annotation.addDeclaredNamespace(prefix + ":" + localName, URI);
      } else {
        annotation.addDeclaredNamespace(localName, URI);
      }
      if (logger.isDebugEnabled()) {
        logger.debug(MessageFormat.format(
          "processNamespace: {0} = {1}",
          prefix, URI));
      }
    }
    else
    {
      logger.warn(MessageFormat.format(
        bundle.getString("SBMLCoreParser.namespaceIgnored"),
        elementName, localName, URI));
      // TODO - store into the XMLNode for unknown things
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.ReadingParser#processStartElement(String elementName, String prefix, boolean hasAttributes, boolean hasNamespaces, Object contextObject)
   */
  @Override
  @SuppressWarnings("unchecked")
  public Object processStartElement(String elementName, String uri, String prefix,
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
            && (contextObject instanceof SBase)) 
        {
          SBase sbase = (SBase) contextObject;
          sbase.setNotes(new XMLNode(new XMLTriple("notes", null, null), new XMLAttributes()));
          
          // keep order of elements for later validation
          AbstractReaderWriter.storeElementsOrder(elementName, contextObject);          
        }
        else if (elementName.equals("annotation")
            && (contextObject instanceof SBase)) 
        {
          SBase sbase = (SBase) contextObject;
          Annotation annotation = (Annotation) newContextObject;
          annotation.setNonRDFAnnotation(new XMLNode(new XMLTriple("annotation", null, null), new XMLAttributes()));
          sbase.setAnnotation(annotation);
          
          // keep order of elements for later validation
          AbstractReaderWriter.storeElementsOrder(elementName, contextObject);
          
          return annotation;
        } else if (contextObject instanceof SBMLDocument) {
          SBMLDocument sbmlDocument = (SBMLDocument) contextObject;

          // keep order of elements for later validation
          AbstractReaderWriter.storeElementsOrder(elementName, contextObject);

          if (elementName.equals("model")) {
            Model model = (Model) newContextObject;
            model.setLevel(sbmlDocument.getLevel());
            model.setVersion(sbmlDocument.getVersion());
            model.initDefaults();
            sbmlDocument.setModel(model);

            return model;
          } else {
            logger.warn(MessageFormat.format(bundle.getString("SBMLCoreParser.unknownElement"), elementName));
            return AbstractReaderWriter.processUnknownElement(elementName, uri, prefix, contextObject);
          }
        } else if (contextObject instanceof Model) {

          Model model = (Model) contextObject;
          
          // keep order of elements for later validation
          AbstractReaderWriter.storeElementsOrder(elementName, contextObject);
          
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
              logger.warn(MessageFormat.format(bundle.getString("SBMLCoreParser.unknownElement"), elementName));
              return AbstractReaderWriter.processUnknownElement(elementName, uri, prefix, contextObject);
            }
          } else {
            logger.warn(MessageFormat.format(bundle.getString("SBMLCoreParser.unknownElement"), elementName));
            return AbstractReaderWriter.processUnknownElement(elementName, uri, prefix, contextObject);
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
            // level 1: species => specie
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
              logger.warn(MessageFormat.format(bundle.getString("SBMLCoreParser.unknownElement"), elementName));
              return AbstractReaderWriter.processUnknownElement(elementName, uri, prefix, contextObject);
            }
          } else if (list.getParentSBMLObject() instanceof UnitDefinition) {
            UnitDefinition unitDefinition = (UnitDefinition) list.getParentSBMLObject();

            if (elementName.equals("unit")
                && list.getSBaseListType().equals(
                  ListOf.Type.listOfUnits)) {
              Unit unit = (Unit) newContextObject;
              unit.initDefaults();
              unitDefinition.addUnit(unit);

              return unit;
            } else {
              logger.warn(MessageFormat.format(bundle.getString("SBMLCoreParser.unknownElement"), elementName));
              return AbstractReaderWriter.processUnknownElement(elementName, uri, prefix, contextObject);
            }
          } else if (list.getParentSBMLObject() instanceof Reaction) {
            Reaction reaction = (Reaction) list.getParentSBMLObject();

            if (elementName.equals("speciesReference")
                && (reaction.getLevel() > 1 ||
                    ((reaction.getLevel() == 1) && (reaction.getVersion() == 2)))) {
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
                logger.warn(MessageFormat.format(bundle.getString("SBMLCoreParser.unknownElement"), elementName));
                return AbstractReaderWriter.processUnknownElement(elementName, uri, prefix, contextObject);
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
                logger.warn(MessageFormat.format(bundle.getString("SBMLCoreParser.unknownElement"), elementName));
                return AbstractReaderWriter.processUnknownElement(elementName, uri, prefix, contextObject);
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
              logger.warn(MessageFormat.format(bundle.getString("SBMLCoreParser.unknownElement"), elementName));
              return AbstractReaderWriter.processUnknownElement(elementName, uri, prefix, contextObject);
            }
          } else if (list.getParentSBMLObject() instanceof KineticLaw) {
            KineticLaw kineticLaw = (KineticLaw) list
                .getParentSBMLObject();
            // Level 3: parameter and listOfParameters =>
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
              logger.warn(MessageFormat.format(bundle.getString("SBMLCoreParser.unknownElement"), elementName));
              return AbstractReaderWriter.processUnknownElement(elementName, uri, prefix, contextObject);
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
              logger.warn(MessageFormat.format(bundle.getString("SBMLCoreParser.unknownElement"), elementName));
              return AbstractReaderWriter.processUnknownElement(elementName, uri, prefix, contextObject);
            }
          } else {
            logger.warn(MessageFormat.format(bundle.getString("SBMLCoreParser.unknownElement"), elementName));
            return AbstractReaderWriter.processUnknownElement(elementName, uri, prefix, contextObject);
          }
        } else if (contextObject instanceof UnitDefinition) {
          UnitDefinition unitDefinition = (UnitDefinition) contextObject;

          // keep order of elements for later validation
          AbstractReaderWriter.storeElementsOrder(elementName, contextObject);

          if (elementName.equals("listOfUnits")) {
            ListOf<Unit> listOfUnits = (ListOf<Unit>) newContextObject;
            unitDefinition.setListOfUnits(listOfUnits);

            return listOfUnits;
          } else {
            logger.warn(MessageFormat.format(bundle.getString("SBMLCoreParser.unknownElement"), elementName));
            return AbstractReaderWriter.processUnknownElement(elementName, uri, prefix, contextObject);
          }
        } else if (contextObject instanceof Event) {
          Event event = (Event) contextObject;

          // keep order of elements for later validation
          AbstractReaderWriter.storeElementsOrder(elementName, contextObject);

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
            logger.warn(MessageFormat.format(bundle.getString("SBMLCoreParser.unknownElement"), elementName));
            return AbstractReaderWriter.processUnknownElement(elementName, uri, prefix, contextObject);
          }
        } else if (contextObject instanceof Reaction) {
          Reaction reaction = (Reaction) contextObject; 
          
          // keep order of elements for later validation
          AbstractReaderWriter.storeElementsOrder(elementName, contextObject);
          
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
            logger.warn(MessageFormat.format(bundle.getString("SBMLCoreParser.unknownElement"), elementName));
            return AbstractReaderWriter.processUnknownElement(elementName, uri, prefix, contextObject);
          }
        } else if (contextObject instanceof SpeciesReference) {
          SpeciesReference speciesReference = (SpeciesReference) contextObject;

          // if level = 1 or level >= 3 - stoichiometryMath is an unknown/invalid element
          if (elementName.equals("stoichiometryMath") && speciesReference.getLevel() == 2) {
            StoichiometryMath stoichiometryMath = (StoichiometryMath) newContextObject;
            speciesReference.setStoichiometryMath(stoichiometryMath);

            return stoichiometryMath;
          } else {
            logger.warn(MessageFormat.format(bundle.getString("SBMLCoreParser.unknownElement"), elementName));
            return AbstractReaderWriter.processUnknownElement(elementName, uri, prefix, contextObject);
          }
        } else if (contextObject instanceof KineticLaw) {
          KineticLaw kineticLaw = (KineticLaw) contextObject;

          // keep order of elements for later validation
          AbstractReaderWriter.storeElementsOrder(elementName, contextObject);          

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
            logger.warn(MessageFormat.format(bundle.getString("SBMLCoreParser.unknownElement"), elementName));
            return AbstractReaderWriter.processUnknownElement(elementName, uri, prefix, contextObject);
          }
        } else if (contextObject instanceof Constraint) {
          Constraint constraint = (Constraint) contextObject;

          // keep order of elements for later validation
          AbstractReaderWriter.storeElementsOrder(elementName, contextObject);          
          
          if (elementName.equals("message")
              && ((constraint.getLevel() == 2 && constraint
              .getVersion() > 1) || constraint.getLevel() >= 3))
          {
            constraint.setMessage(new XMLNode(new XMLTriple("message", null, null), new XMLAttributes()));

            return constraint.getMessage();
          } else {
            logger.warn(MessageFormat.format(bundle.getString("SBMLCoreParser.unknownElement"), elementName));
            return AbstractReaderWriter.processUnknownElement(elementName, uri, prefix, contextObject);
          }
        } else {
          logger.warn(MessageFormat.format(bundle.getString("SBMLCoreParser.unknownElement"), elementName));
          return AbstractReaderWriter.processUnknownElement(elementName, uri, prefix, contextObject);
        }
      } catch (InstantiationException exc) {
        logger.error(MessageFormat.format(
          bundle.getString("SBMLCoreParser.instanciationError"), elementName));
        if (logger.isDebugEnabled()) {
          logger.debug(exc.getMessage());
          logger.debug(exc.getStackTrace());
        }
      } catch (IllegalAccessException exc) {
        logger.error(MessageFormat.format(
          bundle.getString("SBMLCoreParser.instanciationError"), elementName));
        if (logger.isDebugEnabled()) {
          logger.debug(exc.getMessage());
          logger.debug(exc.getStackTrace());
        }
      }
    } else {
      logger.warn(MessageFormat.format(bundle.getString("SBMLCoreParser.unknownElement"), elementName));
      return AbstractReaderWriter.processUnknownElement(elementName, uri, prefix, contextObject);
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

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.WritingParser#writeAttributes(SBMLObjectForXML xmlObject, Object sbmlElementToWrite)
   */
  @Override
  public void writeAttributes(SBMLObjectForXML xmlObject,
    Object sbmlElementToWrite) {
    if (sbmlElementToWrite instanceof SBase) {
      SBase sbase = (SBase) sbmlElementToWrite;
      xmlObject.addAttributes(sbase.writeXMLAttributes());
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.WritingParser#writeCharacters(SBMLObjectForXML xmlObject, Object sbmlElementToWrite)
   */
  @Override
  public void writeCharacters(SBMLObjectForXML xmlObject,
    Object sbmlElementToWrite) {
    // The SBML core XML element should not have any content, everything should be stored as attribute.
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.WritingParser#writeElement(SBMLObjectForXML xmlObject, Object sbmlElementToWrite)
   */
  @Override
  public void writeElement(SBMLObjectForXML xmlObject, Object sbmlElementToWrite) {

    if (sbmlElementToWrite instanceof SBase) {
      SBase sbase = (SBase) sbmlElementToWrite;
      if (logger.isDebugEnabled()) {
        logger.debug(MessageFormat.format("writeElement: {0}", sbase.getElementName()));
      }

      // dealing with level 1 rules
      if (sbase.getLevel() == 1 && sbase instanceof ExplicitRule) {
        ExplicitRule rule = (ExplicitRule) sbase;
        
        if (rule.isSpeciesConcentration()) {
          if (rule.getVersion() == 1) {
            xmlObject.setName("specieConcentrationRule");            
          } else {
            xmlObject.setName("speciesConcentrationRule");
          }
        } else if (rule.isCompartmentVolume()) {
          xmlObject.setName("compartmentVolumeRule");
        } else if (rule.isParameter()) {
          xmlObject.setName("parameterRule");
        }
      }
      
      if (!xmlObject.isSetName()) {
        xmlObject.setName(sbase.getElementName());
      }
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.WritingParser#writeNamespaces(SBMLObjectForXML xmlObject, Object sbmlElementToWrite)
   */
  @Override
  public void writeNamespaces(SBMLObjectForXML xmlObject,
    Object sbmlElementToWrite)
  {
    if (sbmlElementToWrite instanceof SBase) {
      SBase sbase = (SBase) sbmlElementToWrite;

      if (sbase.getDeclaredNamespaces().size() > 0)
      {
        xmlObject.addAttributes(sbase.getDeclaredNamespaces());
      }

      xmlObject.setPrefix("");
      xmlObject.setNamespace(JSBML.getNamespaceFrom(sbase.getLevel(), sbase.getVersion()));
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.ReadingParser#getNamespaces()
   */
  @Override
  public List<String> getNamespaces() {
    return namespaces;
  }

  /**
   * 
   */
  private static final List<String> namespaces = new ArrayList<String>();

  static {
    namespaces.add(SBMLDocument.URI_NAMESPACE_L3V2Core);
    namespaces.add(SBMLDocument.URI_NAMESPACE_L3V1Core);
    namespaces.add(SBMLDocument.URI_NAMESPACE_L2V5);
    namespaces.add(SBMLDocument.URI_NAMESPACE_L2V4);
    namespaces.add(SBMLDocument.URI_NAMESPACE_L2V3);
    namespaces.add(SBMLDocument.URI_NAMESPACE_L2V2);
    namespaces.add(SBMLDocument.URI_NAMESPACE_L2V1);
    namespaces.add(SBMLDocument.URI_NAMESPACE_L1);
  }

}
