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
import java.util.List;

import org.apache.log4j.Logger;
import org.mangosdk.spi.ProviderFor;
import org.sbml.jsbml.Annotation;
import org.sbml.jsbml.Constraint;
import org.sbml.jsbml.JSBML;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.xml.XMLAttributes;
import org.sbml.jsbml.xml.XMLNamespaces;
import org.sbml.jsbml.xml.XMLNode;
import org.sbml.jsbml.xml.XMLTriple;

/**
 * A {@link XMLNodeReader} can be used to store any XML into an {@link XMLNode}.
 * 
 * <p> At the moment, it is used to read the content of the 'notes', 'annotation'
 * and 'message' SBML elements.
 * 
 * @author Nicolas Rodriguez
 * @since 1.0
 */
@ProviderFor(ReadingParser.class)
public class XMLNodeReader implements ReadingParser {

  /**
   * String to be able to detect what type of String this parser is parsing. It
   * can be 'notes', 'message' or 'annotation'.
   */
  private String typeOfNotes = "";

  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(XMLNodeReader.class);

  /**
   * 
   * @return the typeOfNotes of this ReadingParser.
   */
  public String getTypeOfNotes() {
    return typeOfNotes;
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
        "processAttribute: attribute name = {0}, value = {1}",
        attributeName, value));
    }

    if (contextObject instanceof XMLNode) {

      XMLNode xmlNode = (XMLNode) contextObject;
      xmlNode.addAttr(attributeName, value, uri, prefix);

    } else {
      logger.debug(MessageFormat.format(
        "processAttribute: context Object is not an XMLNode! {0}",
        contextObject));
      return false;
    }

    return true;
  }

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

    // characters = StringTools.encodeForHTML(characters); // TODO: use an apache util for that.

    XMLNode textNode = new XMLNode(characters);

    if (contextObject instanceof XMLNode) {

      XMLNode xmlNode = (XMLNode) contextObject;

      // logger.debug("processCharactersOf : XMLNode before (" + xmlNode.getChildCount()  + ") = \n@" + xmlNode.toXMLString() + "@");

      xmlNode.addChild(textNode);

      //			if (logger.isDebugEnabled()) {
      //				logger.debug("processCharactersOf : XMLNode after (" + xmlNode.getChildCount()  + ") = \n@" + xmlNode.toXMLString() + "@");
      //			}

    } else if (contextObject instanceof SBase) {
      SBase parentSBMLElement = (SBase) contextObject;

      XMLNode xmlNode = null;

      if (parentSBMLElement.isSetNotes() && typeOfNotes.equals("notes"))
      {
        xmlNode = parentSBMLElement.getNotes();
      }
      else if (typeOfNotes.equals("message") && parentSBMLElement instanceof Constraint
          && ((Constraint) parentSBMLElement).isSetMessage())
      {
        xmlNode = ((Constraint) parentSBMLElement).getMessage();
      }
      else if (parentSBMLElement.isSetAnnotation() && typeOfNotes.equals("annotation"))
      {
        xmlNode = parentSBMLElement.getAnnotation().getNonRDFannotation();
      }
      else
      {
        if ((characters != null) && (characters.trim().length() > 0)) {
          logger.warn(MessageFormat.format(
            "The type of String ''{0}'' on the element {1} is unknown! Some data might be lost: ''{2}''.",
            typeOfNotes, parentSBMLElement.getElementName(), characters));
        }
        return;
      }

      xmlNode.addChild(textNode);

    } else if (contextObject instanceof Annotation && typeOfNotes.equals("annotation"))
    {
      XMLNode xmlNode = ((Annotation) contextObject).getNonRDFannotation();
      xmlNode.addChild(textNode);
    }
    else {
      if (logger.isDebugEnabled()) {
        logger.debug(MessageFormat.format(
          "processCharactersOf: context Object is not an XMLNode, SBase or Annotation! {0}",
          contextObject));
      }
    }

  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.ReadingParser#processEndDocument(SBMLDocument sbmlDocument)
   */
  @Override
  public void processEndDocument(SBMLDocument sbmlDocument) {
    // nothing special to be done.
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.ReadingParser#processEndElement(String elementName, String prefix, boolean isNested, Object contextObject)
   */
  @Override
  public boolean processEndElement(String elementName, String prefix,
    boolean isNested, Object contextObject)
  {
    if (contextObject instanceof XMLNode) {
      XMLNode xmlNode = (XMLNode) contextObject;

      if (xmlNode.getChildCount() == 0) {
        xmlNode.setEnd();
      }
    }

    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.ReadingParser#processNamespace(String elementName, String URI, String prefix, String localName, boolean hasAttributes, boolean isLastNamespace, Object contextObject)
   */
  @Override
  public void processNamespace(String elementName, String uri, String prefix,
    String localName, boolean hasAttributes, boolean isLastNamespace,
    Object contextObject)
  {
    if (logger.isDebugEnabled()) {
      logger.debug(MessageFormat.format(
        "processNamespace called: elementName, namespace: {0}, {1} ({2}:{3})",
        elementName, uri, prefix, localName));
    }

    if (contextObject instanceof XMLNode) {

      XMLNode xmlNode = (XMLNode) contextObject;
      if (!xmlNode.isStart()) {
        logger.debug(MessageFormat.format(
          "processNamespace: context Object is not a start node! {0}",
          contextObject));
      }
      if (localName == null || localName.trim().length() == 0) {
        localName = "xmlns";
      }

      xmlNode.addNamespace(uri, localName);

    } else {
      logger.debug(MessageFormat.format("processNamespace: context Object is not an XMLNode! {0}", contextObject));
      logger.debug(MessageFormat.format("processNamespace: element name = {0}, namespace = {1}:{2}", elementName, prefix, uri));
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

    if (elementName.equals("notes") && (contextObject instanceof SBase)) {
      SBase sbase = (SBase) contextObject;
      sbase.setNotes(new XMLNode(new XMLTriple("notes", null, null), new XMLAttributes()));
      return contextObject;
    }

    // Creating a StartElement XMLNode !!
    XMLNode xmlNode = new XMLNode(new XMLTriple(elementName, uri, prefix), new XMLAttributes(), new XMLNamespaces());

    if (contextObject instanceof SBase) {
      SBase parentSBMLElement = (SBase) contextObject;

      if (typeOfNotes.equals("notes")) {
        parentSBMLElement.getNotes().addChild(xmlNode);
      } else if (typeOfNotes.equals("message") && parentSBMLElement instanceof Constraint) {
        ((Constraint) parentSBMLElement).getMessage().addChild(xmlNode);
      } else {
        logger.warn(MessageFormat.format(
          "The type of String ''{0}'' on the element {1} ({2}) is unknown! Some data might be lost.",
          typeOfNotes, parentSBMLElement.getElementName(), parentSBMLElement.getClass().getSimpleName()));
      }
    }
    else if (contextObject instanceof Annotation)
    {
      Annotation annotation = (Annotation) contextObject;

      if (typeOfNotes.equals("annotation")) {
        annotation.getNonRDFannotation().addChild(xmlNode);
        // logger.debug(" type of notes = annotation :\n " + annotation.getNonRDFannotation().toXMLString());
      }
      else {
        logger.warn(MessageFormat.format(
          "The type of String ''{0}'' on the element ''annotation'' is unknown! Some data might be lost",
          typeOfNotes));
      }
    }
    else if (contextObject instanceof XMLNode)
    {
      XMLNode parentNode = (XMLNode) contextObject;

      parentNode.addChild(xmlNode);
      // logger.debug("XMLNode.toXMLString() = \n@" + parentNode.toXMLString() + "@");
      // logger.debug("XMLNode.parent.toXMLString() = \n@" + ((XMLNode) parentNode.getParent()).toXMLString() + "@");
    }

    return xmlNode;
  }

  /**
   * Sets the typeOfNote of this parser.
   * @param typeOfNotes
   */
  public void setTypeOfNotes(String typeOfNotes) {
    this.typeOfNotes = typeOfNotes;
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
    namespaces.add(JSBML.URI_XHTML_DEFINITION);
    namespaces.add("anyXML"); // Special keyword used when inside annotation, notes or message
  }

}
