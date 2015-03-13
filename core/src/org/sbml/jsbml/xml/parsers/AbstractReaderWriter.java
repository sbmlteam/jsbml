/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2015 jointly by the following organizations:
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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;
import org.sbml.jsbml.AbstractTreeNode;
import org.sbml.jsbml.Annotation;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.SBasePlugin;
import org.sbml.jsbml.util.ListOfWithName;
import org.sbml.jsbml.xml.XMLAttributes;
import org.sbml.jsbml.xml.stax.SBMLObjectForXML;

/**
 * Contains some code shared by most of the L3 packages parsers.
 * 
 * @author Nicolas Rodriguez
 * @since 1.0
 * @version $Rev$
 */
public abstract class AbstractReaderWriter implements ReadingParser, WritingParser {

  /**
   * A {@link Logger} for this class.
   */
  private Logger logger = Logger.getLogger(AbstractReaderWriter.class);

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.ReadingParser#processAttribute(String
   *      elementName, String attributeName, String value, String prefix,
   *      boolean isLastAttribute, Object contextObject)
   */
  @Override
  public void processAttribute(String elementName, String attributeName,
    String value, String uri, String prefix, boolean isLastAttribute,
    Object contextObject)
  {

    logger.debug("processAttribute -> " + prefix + ":" + attributeName + " = " + value + " (" + contextObject.getClass().getName() + ")");

    boolean isAttributeRead = false;

    if (contextObject instanceof SBase) {

      SBase sbase = (SBase) contextObject;

      // logger.debug("processAttribute: level, version = " + sbase.getLevel() + ", " + sbase.getVersion());

      try {
        isAttributeRead = sbase.readAttribute(attributeName, prefix,
          value);
      } catch (Throwable exc) {
        logger.error(exc.getMessage());
      }
    }
    else if (contextObject instanceof Annotation)
    {
      Annotation annotation = (Annotation) contextObject;
      isAttributeRead = annotation.readAttribute(attributeName, prefix, value);
    }
    else if (contextObject instanceof SBasePlugin)
    {
      isAttributeRead = ((SBasePlugin) contextObject).readAttribute(attributeName, prefix, value);
    }

    if (!isAttributeRead) {
      logger.warn(MessageFormat.format(
        "processAttribute: The attribute ''{0}'' on the element {1} is not part of the SBML specifications.",
        attributeName, elementName));
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.stax.ReadingParser#processCharactersOf(java.lang.String, java.lang.String, java.lang.Object)
   */
  @Override
  public void processCharactersOf(String elementName, String characters,
    Object contextObject)
  {
    logger.debug("processCharactersOf: the element " + elementName + " does not have any text. " +
        "SBML syntax error. Characters lost = " + characters);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.ReadingParser#processEndDocument(org.sbml.jsbml.SBMLDocument)
   */
  @Override
  public void processEndDocument(SBMLDocument sbmlDocument) {
    // does nothing
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.ReadingParser#processEndElement(java.lang.String, java.lang.String, boolean, java.lang.Object)
   */
  @Override
  public boolean processEndElement(String elementName, String prefix,
    boolean isNested, Object contextObject)
  {
    // does nothing
    return true;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.ReadingParser#processNamespace(java.lang.String,
   * java.lang.String, java.lang.String, java.lang.String, boolean, boolean, java.lang.Object)
   */
  @Override
  public void processNamespace(String elementName, String URI, String prefix,
    String localName, boolean hasAttributes, boolean isLastNamespace,
    Object contextObject)
  {
    // TODO: read the namespace, not sure if it is handled by other parsers
    
    if (contextObject instanceof SBMLDocument && getNamespaces().contains(URI) && this instanceof PackageParser) {
      if (logger.isDebugEnabled()) {
        logger.debug("processNamespace - uri = " + URI);
      }
      ((SBMLDocument) contextObject).enablePackage(URI);
    }
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.ReadingParser#processStartElement(java.lang.String, java.lang.String, boolean, boolean, java.lang.Object)
   */
  @Override
  public abstract Object processStartElement(String elementName, String uri, String prefix,
    boolean hasAttributes, boolean hasNamespaces, Object contextObject);

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.WritingParser#getListOfSBMLElementsToWrite(Object sbase)
   */
  @Override
  @SuppressWarnings("unchecked")
  public List<Object> getListOfSBMLElementsToWrite(Object sbase) {

    if (logger.isDebugEnabled()) {
      logger.debug("getListOfSBMLElementsToWrite: " + sbase.getClass().getCanonicalName());
    }

    List<Object> listOfElementsToWrite = new ArrayList<Object>();
    Enumeration<TreeNode> children = null;

    if (sbase instanceof SBMLDocument) {
      // nothing to do
      // TODO: the 'required' attribute is written even if there is no plugin class for the SBMLDocument
      // we will have to change that !!!
      return null;
    }
    else if (sbase instanceof SBasePlugin)
    {
      SBasePlugin elementPlugin = (SBasePlugin) sbase;

      children = elementPlugin.children();
    }
    else if (sbase instanceof TreeNode)
    {
      children = ((TreeNode) sbase).children();

    } else {
      return null;
    }

    while (children.hasMoreElements()) {
      listOfElementsToWrite.add(children.nextElement());
    }

    if (logger.isDebugEnabled()) {
      logger.debug("getListOfSBMLElementsToWrite: nb children = " + listOfElementsToWrite.size());
      logger.debug("getListOfSBMLElementsToWrite: children = " + listOfElementsToWrite);
    }

    if (listOfElementsToWrite.isEmpty()) {
      listOfElementsToWrite = null;
    }

    return listOfElementsToWrite;
  }

  /**
   * 
   * @param attributeName
   * @param value
   * @param prefix
   * @param contextObject
   */
  public static void processUnknownAttribute(String attributeName,
    String value, String prefix, Object contextObject)
  {
    if (contextObject instanceof AbstractTreeNode)
    {
      XMLAttributes unknownAttributes = null;
      Object unknownAttributesObj = ((AbstractTreeNode) contextObject).getUserObject(AbstractTreeNode.UNKNOWN_ATTRIBUTES);

      if (unknownAttributesObj == null)
      {
        unknownAttributes = new XMLAttributes();
        ((AbstractTreeNode) contextObject).putUserObject(AbstractTreeNode.UNKNOWN_ATTRIBUTES, unknownAttributes);
      }
      else if (unknownAttributesObj instanceof XMLAttributes)
      {
        unknownAttributes = (XMLAttributes) unknownAttributesObj;
      }
      else
      {
        // TODO : exception/log ?? Problem !!!!
        return;
      }

      unknownAttributes.add(attributeName, value, null, prefix);
    }
    else
    {
      // TODO : exception/log/other
    }

  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.WritingParser#writeAttributes(SBMLObjectForXML xmlObject, Object sbmlElementToWrite)
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
   * @see org.sbml.jsbml.xml.parsers.WritingParser#writeCharacters(SBMLObjectForXML
   *      xmlObject, Object sbmlElementToWrite)
   */
  @Override
  public void writeCharacters(SBMLObjectForXML xmlObject,
    Object sbmlElementToWrite)
  {
    logger.error("writeCharacters: " + xmlObject.getName() + " XML element do not have any characters !!");
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.WritingParser#writeElement(SBMLObjectForXML
   *      xmlObject, Object sbmlElementToWrite)
   */
  @Override
  public void writeElement(SBMLObjectForXML xmlObject,
    Object sbmlElementToWrite) {

    logger.debug("writeElement: " + sbmlElementToWrite.getClass().getSimpleName());

    if (sbmlElementToWrite instanceof SBase) {
      SBase sbase = (SBase) sbmlElementToWrite;

      // TODO - not sure this code is ready for different package versions !!
      if (!getNamespaceURI().equals(sbase.getNamespace())) {
        logger.debug("writeElement: rejected an element as it does not seems to have the good namespace definition");
        logger.debug("writeElement: sbase.namespaces = " + sbase.getNamespace());

        return;
      }

      if (sbase instanceof ListOfWithName<?>) {
        xmlObject.setName(sbase.getElementName());
      }

      if (!xmlObject.isSetName()) {
        if (sbase instanceof ListOf<?>) {
          ListOf<?> listOf = (ListOf<?>) sbase;

          if (listOf.size() > 0) {
            String listOfName = "listOf" + listOf.get(0).getClass().getSimpleName();
            if (!listOfName.endsWith("s") && !listOfName.toLowerCase().endsWith("information")) {
              listOfName += 's';
            }
            xmlObject.setName(listOfName);
          }

        } else {
          xmlObject.setName(sbase.getElementName());
        }
      }
      if (!xmlObject.isSetPrefix()) {
        xmlObject.setPrefix(getShortLabel());
      }
      xmlObject.setNamespace(getNamespaceURI());

    }

  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.WritingParser#writeNamespaces(SBMLObjectForXML xmlObject, Object sbmlElementToWrite)
   */
  @Override
  public void writeNamespaces(SBMLObjectForXML xmlObject,
    Object sbmlElementToWrite)
  {
    if (sbmlElementToWrite instanceof SBase) {
      xmlObject.setPrefix(getShortLabel());
      xmlObject.setNamespace(getNamespaceURI());

      SBase sbase = (SBase) sbmlElementToWrite;

      if (sbase.getDeclaredNamespaces().size() > 0)
      {
        // writing all declared namespaces
        // TODO: check that the prefix start with xmlns

        xmlObject.addAttributes(sbase.getDeclaredNamespaces());
      }

    }
  }

  /**
   * 
   * @param listOf
   * @param elementName
   * @return
   */
  protected Object createListOfChild(ListOf<?> listOf, String elementName) {

    Object parentSBase = listOf.getParent();
    SBasePlugin parentPlugin = null;

    if (parentSBase == null) {
      return null;
    }
    parentPlugin = ((SBase) parentSBase).getExtension(getNamespaceURI());

    if (parentPlugin != null) {
      parentSBase = parentPlugin;
    }

    String createMethodName = "create" + elementName.substring(0, 1).toUpperCase() + elementName.substring(1);
    Method createMethod = null;

    if (logger.isDebugEnabled()) {
      logger.debug("Method '" + createMethodName + "' will be used");
    }

    try {
      createMethod = parentSBase.getClass().getMethod(createMethodName, (Class<?>[]) null);

      return createMethod.invoke(parentSBase, (Object[]) null);

    } catch (SecurityException e) {
      if (logger.isDebugEnabled()) {
        logger.debug("Method '" + createMethodName + "' is not accessible on " + parentSBase.getClass().getSimpleName());
        e.printStackTrace();
      }
    } catch (NoSuchMethodException e) {
      if (logger.isDebugEnabled()) {
        logger.debug("Method '" + createMethodName + "' does not exist on " + parentSBase.getClass().getSimpleName());
      }
    } catch (IllegalArgumentException e) {
      if (logger.isDebugEnabled()) {
        logger.debug("Problem invoking the method '" + createMethodName + "' on " + parentSBase.getClass().getSimpleName());
        logger.debug(e.getMessage());
      }
    } catch (IllegalAccessException e) {
      if (logger.isDebugEnabled()) {
        logger.debug("Problem invoking the method '" + createMethodName + "' on " + parentSBase.getClass().getSimpleName());
        logger.debug(e.getMessage());
      }
    } catch (InvocationTargetException e) {
      if (logger.isDebugEnabled()) {
        logger.debug("Problem invoking the method '" + createMethodName + "' on " + parentSBase.getClass().getSimpleName());
        logger.debug(e.getMessage());
      }
    }

    // TODO : try to use the default constructor + the addXX method

    return null;
  }

  /**
   * Returns the shortLabel of the namespace parsed by this class.
   * 
   * @return the shortLabel of the namespace parsed by this class.
   */
  public abstract String getShortLabel();

  /**
   * Return the namespace URI of the namespace parsed by this class.
   * 
   * @return the namespace URI of the namespace parsed by this class.
   */
  public abstract String getNamespaceURI();

}
