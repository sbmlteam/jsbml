/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2017 jointly by the following organizations:
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
import java.util.List;

import org.apache.log4j.Logger;
import org.mangosdk.spi.ProviderFor;
import org.sbml.jsbml.Constraint;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.distrib.DistribConstants;
import org.sbml.jsbml.ext.distrib.DrawFromDistribution;
import org.sbml.jsbml.ext.distrib.Uncertainty;
import org.sbml.jsbml.xml.XMLAttributes;
import org.sbml.jsbml.xml.XMLNamespaces;
import org.sbml.jsbml.xml.XMLNode;
import org.sbml.jsbml.xml.XMLTriple;

/**
 * A {@link XMLNodeReader} can be used to store any XML into an {@link XMLNode}.
 * 
 * <p> At the moment, it is used to read the content of the 'UncertML' SBML element.
 * 
 * @author Nicolas Rodriguez
 * @since 1.1
 */
@ProviderFor(ReadingParser.class)
public class UncertMLXMLNodeReader extends XMLNodeReader {

  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(UncertMLXMLNodeReader.class);

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.ReadingParser#processCharactersOf(String elementName, String characters, Object contextObject)
   */
  @Override
  public void processCharactersOf(String elementName, String characters,
    Object contextObject)
  {
    if (logger.isDebugEnabled()) {
      logger.debug(MessageFormat.format(
        "processCharactersOf called: characters = @{0}@",
        characters));
    }

    XMLNode textNode = new XMLNode(characters);

    if (contextObject instanceof XMLNode) {

      XMLNode xmlNode = (XMLNode) contextObject;

      xmlNode.addChild(textNode);

    } else if (contextObject instanceof DrawFromDistribution) {
      DrawFromDistribution parentSBMLElement = (DrawFromDistribution) contextObject;

      XMLNode xmlNode = null;

      if (parentSBMLElement.isSetUncertML())
      {
        xmlNode = parentSBMLElement.getUncertML();
      }
      else
      {
        if ((characters != null) && (characters.trim().length() > 0)) {
          logger.warn(MessageFormat.format(
            "The type of String ''{0}'' on the element {1} is unknown! Some data might be lost: ''{2}''.",
            "UncertML", parentSBMLElement.getElementName(), characters));
        }
        return;
      }

      xmlNode.addChild(textNode);

    }
    else if (contextObject instanceof Uncertainty) { // TODO - create an interface for elements have some uncertML ???
      Uncertainty parentSBMLElement = (Uncertainty) contextObject;

      XMLNode xmlNode = null;

      if (parentSBMLElement.isSetUncertML())
      {
        xmlNode = parentSBMLElement.getUncertML();
      }
      else
      {
        if ((characters != null) && (characters.trim().length() > 0)) {
          logger.warn(MessageFormat.format(
            "The type of String ''{0}'' on the element {1} is unknown! Some data might be lost: ''{2}''.",
            "UncertML", parentSBMLElement.getElementName(), characters));
        }
        return;
      }

      xmlNode.addChild(textNode);

    }
    else
    {
      if ((characters != null) && (characters.trim().length() > 0)) {
        logger.warn(MessageFormat.format(
          "The type of String ''{0}'' on the element {1} is unknown! Some data might be lost: ''{2}''.",
          "UncertML", contextObject.getClass().getSimpleName(), characters));
      }
      return;
    }
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.ReadingParser#processStartElement(String elementName, String prefix, boolean hasAttributes, boolean hasNamespaces, Object contextObject)
   */
  @Override
  public Object processStartElement(String elementName, String uri, String prefix,
    boolean hasAttributes, boolean hasNamespaces,
    Object contextObject)
  {
    logger.debug(MessageFormat.format("processStartElement: element name = {0}", elementName));

    if (elementName.equalsIgnoreCase("UncertML") && (contextObject instanceof DrawFromDistribution)) {
      DrawFromDistribution sbase = (DrawFromDistribution) contextObject;

      // Creating a StartElement XMLNode !!
      XMLNode xmlNode = new XMLNode(new XMLTriple(elementName, uri, prefix), new XMLAttributes(), new XMLNamespaces());
      sbase.setUncertML(xmlNode);

      return xmlNode;
    } else if (elementName.equalsIgnoreCase("UncertML") && (contextObject instanceof Uncertainty)) {
      Uncertainty sbase = (Uncertainty) contextObject;

      // Creating a StartElement XMLNode !!
      XMLNode xmlNode = new XMLNode(new XMLTriple(elementName, uri, prefix), new XMLAttributes(), new XMLNamespaces());
      sbase.setUncertML(xmlNode);

      return xmlNode;
    }


    // Creating a StartElement XMLNode !!
    XMLNode xmlNode = new XMLNode(new XMLTriple(elementName, uri, prefix), new XMLAttributes(), new XMLNamespaces());

    if (contextObject instanceof XMLNode)
    {
      XMLNode parentNode = (XMLNode) contextObject;

      parentNode.addChild(xmlNode);
      // logger.debug("XMLNode.toXMLString() = \n@" + parentNode.toXMLString() + "@");
      // logger.debug("XMLNode.parent.toXMLString() = \n@" + ((XMLNode) parentNode.getParent()).toXMLString() + "@");
    } else if (contextObject instanceof Constraint) {

      // We assume that we are parsing an UncertML String on it's own.
      // We store the xmlNode in the user objects
      ((SBase) contextObject).putUserObject(SBMLReader.UNKNOWN_XML_NODE, xmlNode);

    } else if (contextObject == null) {

      // Could happen when we are parsing an UncertML String on it's own, if we don't use the SBase user object.
      // nothing to do

    } else {
      logger.warn("XMLNode might be lost !!! elementName = '" + elementName + "' contextObject = " + contextObject.getClass().getSimpleName());
    }

    return xmlNode;
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
    namespaces.add(DistribConstants.UNCERT_ML_URI_L3);
  }

}
