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

import static org.sbml.jsbml.ext.multi.MultiConstants.listOfSpeciesTypes;
import static org.sbml.jsbml.ext.multi.MultiConstants.namespaceURI;
import static org.sbml.jsbml.ext.multi.MultiConstants.shortLabel;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.mangosdk.spi.ProviderFor;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.JSBML;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.SimpleSpeciesReference;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.ext.ASTNodePlugin;
import org.sbml.jsbml.ext.SBasePlugin;
import org.sbml.jsbml.ext.multi.IntraSpeciesReaction;
import org.sbml.jsbml.ext.multi.MultiASTNodePlugin;
import org.sbml.jsbml.ext.multi.MultiCompartmentPlugin;
import org.sbml.jsbml.ext.multi.MultiConstants;
import org.sbml.jsbml.ext.multi.MultiModelPlugin;
import org.sbml.jsbml.ext.multi.MultiSimpleSpeciesReferencePlugin;
import org.sbml.jsbml.ext.multi.MultiSpeciesPlugin;
import org.sbml.jsbml.ext.multi.MultiSpeciesReferencePlugin;
import org.sbml.jsbml.ext.multi.SpeciesFeature;
import org.sbml.jsbml.ext.multi.SpeciesFeatureType;
import org.sbml.jsbml.ext.multi.MultiSpeciesType;
import org.sbml.jsbml.xml.stax.SBMLObjectForXML;

/**
 * A MultiParser is used to parse the multi extension package elements and
 * attributes. The namespaceURI URI of this parser is
 * "http://www.sbml.org/sbml/level3/version1/multi/version1". This parser is
 * able to read and write elements of the multi package (implements
 * ReadingParser and WritingParser).
 * 
 * @author Nicolas Rodriguez
 * @since 1.0
 */
@ProviderFor(ReadingParser.class)
public class MultiParser extends AbstractReaderWriter implements PackageParser {

  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(MultiParser.class);

  /**
   * 
   * @return the namespaceURI of this parser.
   */
  @Override
  public String getNamespaceURI() {
    return namespaceURI;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.AbstractReaderWriter#getShortLabel()
   */
  @Override
  public String getShortLabel() {
    return shortLabel;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.WritingParser#getListOfSBMLElementsToWrite(Object sbase)
   */
  @Override
  public List<Object> getListOfSBMLElementsToWrite(Object treeNode) {

    if (logger.isDebugEnabled()) {
      logger.debug("getListOfSBMLElementsToWrite: " + treeNode.getClass().getCanonicalName());
    }

    List<Object> listOfElementsToWrite = new ArrayList<Object>();

    // test if this treeNode is an extended SBase.
    if (treeNode instanceof SBase && ((SBase) treeNode).getExtension(shortLabel) != null) {
      SBasePlugin sbasePlugin = ((Model) treeNode).getExtension(shortLabel);

      if (sbasePlugin != null) {
        listOfElementsToWrite = super.getListOfSBMLElementsToWrite(sbasePlugin);
        logger.debug("getListOfSBMLElementsToWrite: nb children = " + sbasePlugin.getChildCount());
      }
    } else {
      listOfElementsToWrite = super.getListOfSBMLElementsToWrite(treeNode);
    }

    // IntraSpeciesReaction children
    if (treeNode instanceof IntraSpeciesReaction) {
      String sbmlNamespace = JSBML.getNamespaceFrom(((IntraSpeciesReaction) treeNode).getLevel(), ((IntraSpeciesReaction) treeNode).getVersion());

      for (Object child : listOfElementsToWrite) {
        if (child instanceof AbstractSBase && ((AbstractSBase) child).getNamespace() == null) {
          AbstractSBase sbase = (AbstractSBase) child;
          logger.debug("Found one suspect Model child: " + sbase.getElementName() + ". Setting the SBML namespace to it.");
          sbase.setNamespace(sbmlNamespace);
        }
      }
    }


    
    return listOfElementsToWrite;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.ReadingParser#processAttribute(String
   *      elementName, String attributeName, String value, String prefix,
   *      boolean isLastAttribute, Object contextObject)
   */
  @Override
  public boolean processAttribute(String elementName, String attributeName,
    String value, String uri, String prefix, boolean isLastAttribute,
    Object contextObject)
  {
    // logger.debug("processAttribute -> " + prefix + ":" + attributeName + " = " + value + " (" + contextObject.getClass().getName() + ")");

    if (contextObject instanceof SBase && ((SBase) contextObject).getPackageName().equals("core")) {
      if (!(contextObject instanceof SBMLDocument)) {
        contextObject = ((SBase) contextObject).getPlugin(getShortLabel());
      }
    } else if (contextObject instanceof ASTNode) {
      contextObject = ((ASTNode) contextObject).getPlugin(getShortLabel());
    } // TODO - ASTNode2 when it is used for reading.
    
    return super.processAttribute(elementName, attributeName, value, uri, prefix, isLastAttribute, contextObject);
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.ReadingParser#processStartElement(String
   * elementName, String prefix, boolean hasAttributes, boolean hasNamespaces,
   * Object contextObject)
   */
  @Override
  public Object processStartElement(String elementName, String uri, String prefix,
    boolean hasAttributes, boolean hasNamespaces, Object contextObject) {

    // Model
    if (contextObject instanceof Model)
    {
      Model model = (Model) contextObject;
      MultiModelPlugin multiModel = (MultiModelPlugin) model.getPlugin(shortLabel);

      if (elementName.equals(listOfSpeciesTypes))
      {
        return multiModel.getListOfSpeciesTypes();
      }
    } // end Model
    // Compartment
    else if (contextObject instanceof Compartment)
    {
      Compartment compartment = (Compartment) contextObject;
      MultiCompartmentPlugin multiCompartment = (MultiCompartmentPlugin) compartment.getPlugin(shortLabel);

      if (elementName.equals(MultiConstants.listOfCompartmentReferences))
      {
        return multiCompartment.getListOfCompartmentReferences();
      }
    } // end Compartment
    // MultiSpeciesType
    else if (contextObject instanceof MultiSpeciesType)
    {
      MultiSpeciesType speciesType = (MultiSpeciesType) contextObject;

      if (elementName.equals(MultiConstants.listOfSpeciesFeatureTypes)) {
        return speciesType.getListOfSpeciesFeatureTypes();
      } else if (elementName.equals(MultiConstants.listOfSpeciesTypeInstances)) {
        return speciesType.getListOfSpeciesTypeInstances();
      } else if (elementName.equals(MultiConstants.listOfSpeciesTypeComponentIndexes)) {
        return speciesType.getListOfSpeciesTypeComponentIndexes();
      } else if (elementName.equals(MultiConstants.listOfInSpeciesTypeBonds)) {
        return speciesType.getListOfInSpeciesTypeBonds();
      }
    } // end MultiSpeciesType
    // SpeciesFeatureType
    else if (contextObject instanceof SpeciesFeatureType)
    {
      SpeciesFeatureType speciesFeatureType = (SpeciesFeatureType) contextObject;

      if (elementName.equals(MultiConstants.listOfPossibleSpeciesFeatureValues)) {
        return speciesFeatureType.getListOfPossibleSpeciesFeatureValues();
      }
    } // end SpeciesFeatureType
    // Species
    else if (contextObject instanceof Species)
    {
      Species species = (Species) contextObject;
      MultiSpeciesPlugin multiSpecies = (MultiSpeciesPlugin) species.getPlugin(shortLabel);

      if (elementName.equals(MultiConstants.listOfOutwardBindingSites)) {
        return multiSpecies.getListOfOutwardBindingSites();
      } else if (elementName.equals(MultiConstants.listOfSpeciesFeatures)) {
        return multiSpecies.getListOfSpeciesFeatures();
      }
    } // end Species
    // SpeciesFeature
    else if (contextObject instanceof SpeciesFeature)
    {
      SpeciesFeature speciesFeature = (SpeciesFeature) contextObject;

      if (elementName.equals(MultiConstants.listOfSpeciesFeatureValues)) {
        return speciesFeature.getListOfSpeciesFeatureValues();
      }
    } // end SpeciesFeature
    // SpeciesReference
    else if (contextObject instanceof SpeciesReference)
    {
      SpeciesReference speciesReference = (SpeciesReference) contextObject;
      MultiSpeciesReferencePlugin multiSpeciesReference = (MultiSpeciesReferencePlugin) speciesReference.getPlugin(shortLabel);

      if (elementName.equals(MultiConstants.listOfSpeciesTypeComponentMapInProducts)) {
        return multiSpeciesReference.getListOfSpeciesTypeComponentMapInProducts();
      }
    } // end SpeciesReference
//    // SpeciesTypeComponentMapInProduct
//    else if (contextObject instanceof SpeciesTypeComponentMapInProduct)
//    {
//      SpeciesTypeComponentMapInProduct speciesTypeComponentMapInProduct = (SpeciesTypeComponentMapInProduct) contextObject;
//
//      if (elementName.equals(MultiConstants.listOfSpeciesFeatureChanges)) {
//        return speciesTypeComponentMapInProduct.getListOfSpeciesFeatureChanges();
//      }
//    } // end SpeciesTypeComponentMapInProduct
    // Any ListOf
    else if (contextObject instanceof ListOf<?>)
    {
      ListOf<?> listOf = (ListOf<?>) contextObject;

      Object newElement = createListOfChild(listOf, elementName);

      return newElement;

    }

    return contextObject;
  }

  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.AbstractReaderWriter#writeElement(org.sbml.jsbml.xml.stax.SBMLObjectForXML, java.lang.Object)
   */
  @Override
  public void writeElement(SBMLObjectForXML xmlObject,
    Object sbmlElementToWrite)
  {
    super.writeElement(xmlObject, sbmlElementToWrite);

    if (logger.isDebugEnabled()) {
      logger.debug("writeElement: " + sbmlElementToWrite.getClass().getSimpleName());
    }
  }

  @Override
  public String getNamespaceFor(int level, int version, int packageVersion) {

    if (level == 3 && version == 1 && packageVersion == 1) {
      return MultiConstants.namespaceURI_L3V1V1;
    }

    return null;
  }

  @Override
  public List<String> getNamespaces() {
    return MultiConstants.namespaces;
  }

  @Override
  public List<String> getPackageNamespaces() {
    return getNamespaces();
  }

  @Override
  public String getPackageName() {
    return MultiConstants.shortLabel;
  }

  @Override
  public boolean isRequired() {
    return true;
  }

  @Override
  public SBasePlugin createPluginFor(SBase sbase) {

    if (sbase != null) {
      if (sbase instanceof Model) {
        return new MultiModelPlugin((Model) sbase);
      } else if (sbase instanceof Species) {
        return new MultiSpeciesPlugin((Species) sbase);
      } else if (sbase instanceof Compartment) {
        return new MultiCompartmentPlugin((Compartment) sbase);
      } else if (sbase instanceof SpeciesReference) {
        return new MultiSpeciesReferencePlugin((SpeciesReference) sbase);
      } else if (sbase instanceof SimpleSpeciesReference) {
        return new MultiSimpleSpeciesReferencePlugin((SimpleSpeciesReference) sbase);
      } 
    }

    return null;
  }

  @Override
  public ASTNodePlugin createPluginFor(ASTNode astNode) {

    if (astNode != null) {
      return new MultiASTNodePlugin(astNode);
    }

    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.AbstractReaderWriter#createListOfChild(org.sbml.jsbml.ListOf, java.lang.String)
   */
  @Override
  protected Object createListOfChild(ListOf<?> listOf, String elementName) {
    
    // We need to create the plugin beforehand in some cases
    if (elementName.equals(MultiConstants.intraSpeciesReaction) 
        || elementName.equals(MultiConstants.bindingSiteSpeciesType)) 
    {
      // getPlugin will create the plugin if it does not already exist
      listOf.getParent().getPlugin(MultiConstants.shortLabel);
    }

    return super.createListOfChild(listOf, elementName);
  }

  
}
