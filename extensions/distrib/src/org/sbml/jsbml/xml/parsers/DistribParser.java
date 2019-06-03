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

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.mangosdk.spi.ProviderFor;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.ASTNodePlugin;
import org.sbml.jsbml.ext.SBasePlugin;
import org.sbml.jsbml.ext.distrib.DistribConstants;
import org.sbml.jsbml.ext.distrib.DistribSBasePlugin;
import org.sbml.jsbml.ext.distrib.UncertParameter;
import org.sbml.jsbml.ext.distrib.UncertSpan;
import org.sbml.jsbml.ext.distrib.Uncertainty;
import org.sbml.jsbml.xml.stax.SBMLObjectForXML;

/**
 * This class is used to parse the distrib extension package elements and
 * attributes. The namespaceURI URI of this parser is
 * {@code http://www.sbml.org/sbml/level3/version1/distrib/version1}. This parser is
 * able to read and write elements of the distrib package (extends
 * {@link AbstractReaderWriter}).
 * 
 * @author Nicolas Rodriguez
 * @since 1.0
 */
@ProviderFor(ReadingParser.class)
public class DistribParser extends AbstractReaderWriter implements PackageParser {

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.AbstractReaderWriter#getNamespaceURI()
   */
  @Override
  public String getNamespaceURI() {
    return DistribConstants.namespaceURI;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.AbstractReaderWriter#getShortLabel()
   */
  @Override
  public String getShortLabel() {
    return DistribConstants.shortLabel;
  }

  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(DistribParser.class);

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.WritingParser#getListOfSBMLElementsToWrite(Object sbase)
   */
  @Override
  public List<Object> getListOfSBMLElementsToWrite(Object sbase) {

    if (logger.isDebugEnabled()) {
      logger.debug("getListOfSBMLElementsToWrite: " + sbase.getClass().getCanonicalName());
    }

    List<Object> listOfElementsToWrite = new ArrayList<Object>();

    // test if this sbase is an extended SBase.
    if (sbase instanceof SBase && ((SBase) sbase).getExtension(getNamespaceURI()) != null) {
      SBasePlugin sbasePlugin = ((SBase) sbase).getExtension(getNamespaceURI());

      listOfElementsToWrite = super.getListOfSBMLElementsToWrite(sbasePlugin);
      logger.debug("getListOfSBMLElementsToWrite: nb children = " + sbasePlugin.getChildCount());
    } else {
      listOfElementsToWrite = super.getListOfSBMLElementsToWrite(sbase);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("listOfElementsToWrite.size() not null = " + (listOfElementsToWrite != null ? listOfElementsToWrite.size() : 0));
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
    if (logger.isDebugEnabled()) {
      logger.debug("processAttribute -> " + prefix + ":" + attributeName + " = " + value + " (" + contextObject.getClass().getName() + ")");
    }
    
    return super.processAttribute(elementName, attributeName, value, uri, prefix, isLastAttribute, contextObject);
  }



  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.ReadingParser#processStartElement(java.lang.String, java.lang.String, boolean, boolean, java.lang.Object)
   */
  // Create the proper object and link it to his parent.
  @Override
  public Object processStartElement(String elementName, String uri, String prefix,
    boolean hasAttributes, boolean hasNamespaces, Object contextObject)
  {
    if (contextObject instanceof ListOf<?>) {
      ListOf<?> listOf = (ListOf<?>) contextObject;

      Object newElement = createListOfChild(listOf, elementName);

      if (newElement != null) {
        return newElement;
      }
      
    } else if (contextObject instanceof Uncertainty) {
      Uncertainty uncert = (Uncertainty) contextObject;

      if (elementName.equals(DistribConstants.uncertParameter)) {

        UncertParameter uncertParam = uncert.createUncertParameter();        
        
        return uncertParam;
      } else if (elementName.equals(DistribConstants.uncertSpan)) {
        UncertSpan uncertSpan = uncert.createUncertSpan();
        
        return uncertSpan;
      }
    } else if (contextObject instanceof UncertParameter) {

      if (elementName.equals(DistribConstants.listOfUncertParameters)) {
        return ((UncertParameter) contextObject).getListOfUncertParameters();
      }
    }
    
    // If not other elements recognized the new element to read, it might be
    // on of the extended SBase children
    
    if (contextObject instanceof SBase)
    {
      SBase sbase = (SBase) contextObject;
      DistribSBasePlugin distribSBase = null;

      if (sbase.getExtension(DistribConstants.shortLabel) != null) {
        distribSBase = (DistribSBasePlugin) sbase.getExtension(DistribConstants.shortLabel);
      } else {
        distribSBase = new DistribSBasePlugin(sbase);
        sbase.addExtension(DistribConstants.shortLabel, distribSBase);
      }

      if (elementName.equals(DistribConstants.uncertainty))
      {
        return distribSBase.createUncertainty();
      }
    }
    
    return contextObject;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.WritingParser#writeElement(org.sbml.jsbml.xml.stax.SBMLObjectForXML, java.lang.Object)
   */
  @Override
  public void writeElement(SBMLObjectForXML xmlObject,
    Object sbmlElementToWrite) {

    if (sbmlElementToWrite instanceof SBase) {
      SBase sbase = (SBase) sbmlElementToWrite;

      if (!xmlObject.isSetName()) {
        xmlObject.setName(sbase.getElementName());
      }
      
      if (xmlObject.getName().equals("listOfUncertaintys")) {
        xmlObject.setName(DistribConstants.listOfUncertainties);
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("writeElement " + xmlObject.getName());
    }
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.PackageParser#getNamespaceFor(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public String getNamespaceFor(int level, int version,	int packageVersion) {

     return DistribConstants.namespaceURI_L3V1V1;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.ReadingParser#getNamespaces()
   */
  @Override
  public List<String> getNamespaces() {
    return DistribConstants.namespaces;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.PackageParser#getPackageNamespaces()
   */
  @Override
  public List<String> getPackageNamespaces() {
    return getNamespaces();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.PackageParser#getPackageName()
   */
  @Override
  public String getPackageName() {
    return DistribConstants.shortLabel;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.PackageParser#isRequired()
   */
  @Override
  public boolean isRequired() {
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.PackageParser#createPluginFor(org.sbml.jsbml.SBase)
   */
  @Override
  public SBasePlugin createPluginFor(SBase sbase) {
    if (sbase != null) {
      return new DistribSBasePlugin(sbase);
    }
    return null;
  }
  
  @Override
  public ASTNodePlugin createPluginFor(ASTNode astNode) {
    // This package does not extends ASTNode
    return null;
  }

}
