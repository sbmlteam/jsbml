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
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.xml.parsers;

import static org.sbml.jsbml.ext.req.ReqConstants.namespaceURI;
import static org.sbml.jsbml.ext.req.ReqConstants.shortLabel;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.mangosdk.spi.ProviderFor;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.ASTNodePlugin;
import org.sbml.jsbml.ext.SBasePlugin;
import org.sbml.jsbml.ext.req.ReqConstants;
import org.sbml.jsbml.ext.req.ReqSBasePlugin;
import org.sbml.jsbml.xml.stax.SBMLObjectForXML;

/**
 * A ReqParser is used to parse the req extension package elements and
 * attributes. The namespaceURI URI of this parser is
 * "http://www.sbml.org/sbml/level3/version1/req/version1". This parser is
 * able to read and write elements of the req package (implements
 * ReadingParser and WritingParser).
 * 
 * @author Nicolas Rodriguez
 * @since 1.0
 * @version $$
 */
@ProviderFor(ReadingParser.class)
public class ReqParser extends AbstractReaderWriter implements PackageParser {

  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(ReqParser.class);

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.AbstractReaderWriter#getNamespaceURI()
   */
  @Override
  public String getNamespaceURI() {
    // TODO - use the SBMLDocument to know which namespace to return !!!
    // TODO - or create the parser with the correct namespace set to it
    // TODO - or we can remove all call to getNamespaceURI() and replace it by getShortLabel()
    // once all the methods work with package name as well (probably most easy and straightforward solution)
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
    if (treeNode instanceof SBase && ((SBase) treeNode).getExtension(getNamespaceURI()) != null) {
      SBasePlugin sbasePlugin = ((SBase) treeNode).getExtension(getNamespaceURI());

      if (sbasePlugin != null) {
        listOfElementsToWrite = super.getListOfSBMLElementsToWrite(sbasePlugin);
        logger.debug("getListOfSBMLElementsToWrite: nb children = " + sbasePlugin.getChildCount());
      }
    } else {
      listOfElementsToWrite = super.getListOfSBMLElementsToWrite(treeNode);
    }

    return listOfElementsToWrite;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.ReadingParser#processStartElement(String
   * elementName, String prefix, boolean hasAttributes, boolean hasNamespaces,
   * Object contextObject)
   */
  @Override
  public Object processStartElement(String elementName, String uri, String prefix,
    boolean hasAttributes, boolean hasNamespaces, Object contextObject) {

    if (logger.isDebugEnabled()) {
      logger.debug("processStartElement: " + elementName);
    }
    if (contextObject instanceof ListOf<?>)
    {
      ListOf<?> listOf = (ListOf<?>) contextObject;

      Object newElement = createListOfChild(listOf, elementName);

      if (newElement != null) {
        return newElement;
      }
    }

    // If not other elements recognized the new element to read, it might be
    // one of the extended SBase children
    if (contextObject instanceof SBase)
    {
      SBase sbase = (SBase) contextObject;
      ReqSBasePlugin reqSBase = null;

      if (sbase.getExtension(getNamespaceURI()) != null) {
        reqSBase = (ReqSBasePlugin) sbase.getExtension(getNamespaceURI());
      } else {
        reqSBase = new ReqSBasePlugin(sbase);
        sbase.addExtension(getNamespaceURI(), reqSBase);
      }

      if (elementName.equals(ReqConstants.listOfChangedMaths))
      {
        return reqSBase.getListOfChangedMaths();
      }
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

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.PackageParser#getNamespaceFor(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public String getNamespaceFor(int level, int version,	int packageVersion) {

    return ReqConstants.getNamespaceURI(level, version, packageVersion);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.ReadingParser#getNamespaces()
   */
  @Override
  public List<String> getNamespaces() {
    return ReqConstants.namespaces;
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
    return shortLabel;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.PackageParser#isRequired()
   */
  @Override
  public boolean isRequired() {
    return true;
  }

  @Override
  public SBasePlugin createPluginFor(SBase sbase) {

    if (sbase != null) {
      return new ReqSBasePlugin(sbase);
    }

    return null;
  }
  
  @Override
  public ASTNodePlugin createPluginFor(ASTNode astNode) {
    // This package does not extends ASTNode
    return null;
  }

}
