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
import org.sbml.jsbml.JSBML;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.ASTNodePlugin;
import org.sbml.jsbml.ext.SBasePlugin;
import org.sbml.jsbml.xml.XMLAttributes;
import org.sbml.jsbml.xml.XMLNamespaces;
import org.sbml.jsbml.xml.XMLNode;
import org.sbml.jsbml.xml.XMLTriple;
import org.sbml.jsbml.xml.stax.SBMLObjectForXML;

/**
 * Contains some code shared by most of the L3 packages parsers.
 * 
 * @author Nicolas Rodriguez
 * @since 1.0
 */
public abstract class AbstractReaderWriter implements ReadingParser, WritingParser {

  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(AbstractReaderWriter.class);

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

    logger.debug("processAttribute -> " + prefix + ":" + attributeName + " = " + value + " (" + contextObject.getClass().getName() + ")");

    boolean isAttributeRead = false;

    if (contextObject instanceof SBase) {

      SBase sbase = (SBase) contextObject;

      // logger.debug("processAttribute: level, version = " + sbase.getLevel() + ", " + sbase.getVersion());

      try {
        isAttributeRead = sbase.readAttribute(attributeName, prefix,
          value);
      } catch (Throwable exc) {
        if (logger.isDebugEnabled()) {
          exc.printStackTrace();
        }
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
    else if (contextObject instanceof ASTNodePlugin)
    {
      isAttributeRead = ((ASTNodePlugin) contextObject).readAttribute(attributeName, prefix, value);
    }

    if (!isAttributeRead) {
      logger.warn(MessageFormat.format(
        "processAttribute: The attribute ''{0}'' on the element {1} is not part of the SBML specifications ({2}) or has an invalid value.",
        attributeName, elementName, contextObject.getClass().getSimpleName()));
    }
    
    return isAttributeRead;
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
    Enumeration<? extends TreeNode> children = null;

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
   * Process an invalid XML attribute. Stores it into an {@link XMLNode}. This XMLNode is
   * put into the user object map of the contextObject, using the key {@link JSBML#INVALID_XML}.
   * 
   * @param attributeName the attribute name.
   * @param namespace the attribute namespace URI.
   * @param value the attribute value.
   * @param prefix the attribute namespace prefix.
   * @param contextObject the context Object.
   */
  public static void processInvalidAttribute(String attributeName, String namespace,
    String value, String prefix, Object contextObject)
  {
    if (contextObject instanceof AbstractTreeNode)
    {
      AbstractTreeNode treeNode = (AbstractTreeNode) contextObject;
      XMLNode invalidNode = (XMLNode) treeNode.getUserObject(JSBML.INVALID_XML);

      if (invalidNode == null) {
        invalidNode = new XMLNode(new XMLTriple("invalid", "", ""), new XMLAttributes(), new XMLNamespaces());
        treeNode.putUserObject(JSBML.INVALID_XML, invalidNode);
      }

      if (logger.isDebugEnabled()) {
        logger.debug("processInvalidAttribute - storing the attribute = '" + attributeName + "', with value = '" + value + "'.");
      }

      invalidNode.addAttr(new XMLTriple(attributeName, namespace, prefix), value);
    }
    else
    {
      if (logger.isDebugEnabled()) {
        logger.warn("processInvalidAttribute - the contextObject is not an AbstractTreeNode ! object = " + contextObject.getClass().getSimpleName());
      }
    }
  }


  /**
   * Process an unknown XML attribute. Stores it into an {@link XMLNode}. This XMLNode is
   * put into the user object map of the contextObject, using the key {@link JSBML#UNKNOWN_XML}.
   * 
   * @param attributeName the attribute name.
   * @param namespace the attribute namespace URI.
   * @param value the attribute value.
   * @param prefix the attribute namespace prefix.
   * @param contextObject the context Object.
   */
  public static void processUnknownAttribute(String attributeName, String namespace,
    String value, String prefix, Object contextObject)
  {
    if (contextObject instanceof AbstractTreeNode)
    {
      AbstractTreeNode treeNode = (AbstractTreeNode) contextObject;
      XMLNode unknownNode = (XMLNode) treeNode.getUserObject(JSBML.UNKNOWN_XML);

      if (unknownNode == null) {
        unknownNode = new XMLNode(new XMLTriple("unknown", "", ""), new XMLAttributes(), new XMLNamespaces());
        treeNode.putUserObject(JSBML.UNKNOWN_XML, unknownNode);
      }

      if (logger.isDebugEnabled()) {
        logger.debug("processUnknownAttribute - storing the attribute = '" + attributeName + "', with value = '" + value + "'.");
      }

      unknownNode.addAttr(new XMLTriple(attributeName, namespace, prefix), value);
    }
    else
    {
      if (logger.isDebugEnabled()) {
        logger.warn("processUnknownAttribute - the contextObject is not an AbstractTreeNode ! object = " + contextObject.getClass().getSimpleName());
      }
    }
  }

  /**
   * Process an unknown XML element. Stores it into an {@link XMLNode}. This XMLNode is
   * put into the user object map of the contextObject, using the key {@link JSBML#UNKNOWN_XML}.
   * 
   * @param elementName the element name.
   * @param namespace the element namespace URI.
   * @param prefix the element namespace prefix.
   * @param contextObject the context Object.
   */
  public static XMLNode processUnknownElement(String elementName, String namespace,
    String prefix, Object contextObject)
  {
    if (contextObject instanceof AbstractTreeNode)
    {
      AbstractTreeNode treeNode = (AbstractTreeNode) contextObject;
      XMLNode unknownNode = (XMLNode) treeNode.getUserObject(JSBML.UNKNOWN_XML);

      if (unknownNode == null) {
        unknownNode = new XMLNode(new XMLTriple("unknown", "", ""), null, null);
        treeNode.putUserObject(JSBML.UNKNOWN_XML, unknownNode);
      }

      if (logger.isDebugEnabled()) {
        logger.debug("processUnknownElement - storing the element = '" + elementName + "'.");
      }

      XMLNode unknownChild = new XMLNode(new XMLTriple(elementName, namespace, prefix), null, null);
      unknownNode.addChild(unknownChild);
      return unknownChild;
    }
    else
    {
      if (logger.isDebugEnabled()) {
        logger.warn("processUnknownElement - the contextObject is not an AbstractTreeNode ! - object = " + contextObject.getClass().getSimpleName());
      }
    }
    
    return null;
  }

  /**
   * Stores a list of elements as they are given. This {@link List} is
   * put into the user object map of the contextObject, using the key {@link JSBML#CHILD_ELEMENT_NAMES}.
   * 
   * @param elementName the child element name
   * @param contextObject the context object
   */
  public static void storeElementsOrder(String elementName, Object contextObject) 
  {   
    // keep a list of element names.
    if (contextObject instanceof AbstractTreeNode)
    {
      AbstractTreeNode treeNode = (AbstractTreeNode) contextObject;
      @SuppressWarnings("unchecked")
      List<String> unknownNode = (List<String>) treeNode.getUserObject(JSBML.CHILD_ELEMENT_NAMES);

      if (unknownNode == null) {
        unknownNode = new ArrayList<String>();
        treeNode.putUserObject(JSBML.CHILD_ELEMENT_NAMES, unknownNode);
      }

      if (logger.isDebugEnabled()) {
        logger.debug("storeElementsOrder - storing the element = '" + elementName + "'.");
      }

      unknownNode.add(elementName);
    }
    else
    {
      if (logger.isDebugEnabled()) {
        logger.warn("storeElementsOrder - the contextObject is not an AbstractTreeNode ! - object = " + contextObject.getClass().getSimpleName());
      }
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
    // The SBML core XML element should not have any content, everything should be stored as attribute.
    if (logger.isDebugEnabled()) {
      logger.debug("writeCharacters: " + xmlObject.getName() + " XML element does not have any characters!");
    }
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

      if (sbase.getNamespace() == null) {
        logger.warn("writeElement: the '" + sbase.getElementName() + "' element of type '" + sbase.getClass().getSimpleName() + "' does not seems to have a namespace defined");        
      }
      
      if (xmlObject.getNamespace() == null && sbase.getNamespace() != null) 
      {
        xmlObject.setNamespace(sbase.getNamespace());
      }
      else if (xmlObject.getNamespace() == null) 
      {
        // Should never happen (the namespace being null on the SBase) and it is not a problem for JSBML if 'xmlObject.getNamespace() == null'
        
        // TODO - fetch the namespaceURI that is defined in the SBMLDocument and set it ?        
      }

      if (sbase instanceof ListOf<?>) {
        xmlObject.setName(sbase.getElementName());
      }

      if (!xmlObject.isSetName()) {
        xmlObject.setName(sbase.getElementName());
      }
      if (!xmlObject.isSetPrefix()) {
        xmlObject.setPrefix(getShortLabel());
      }
      
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

      SBase sbase = (SBase) sbmlElementToWrite;

      if (sbase.getDeclaredNamespaces().size() > 0)
      {
        // writing all declared namespaces
        // TODO: check that the prefix start with xmlns ?

        xmlObject.addAttributes(sbase.getDeclaredNamespaces());
      }

    }
  }

  /**
   * Creates a new object instance using reflection on the {@code listOf} parent.
   * 
   * @param listOf the {@link ListOf} that will contain the new instance
   * @param elementName the element name.
   * @return a new object instance using reflection on the {@code listOf} parent or null
   * if an error occurs when trying to instantiate the new object.
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
        logger.debug(MessageFormat.format(
          "Method ''{0}'' is not accessible on {1}",
          createMethodName, parentSBase.getClass().getSimpleName()));
        e.printStackTrace();
      }
    } catch (NoSuchMethodException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(MessageFormat.format(
          "Method ''{0}'' does not exist on {1}",
          createMethodName, parentSBase.getClass().getSimpleName()));
      }
    } catch (IllegalArgumentException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(MessageFormat.format(
          "Problem invoking the method ''{0}'' on {1}",
          createMethodName, parentSBase.getClass().getSimpleName()));
        logger.debug(e.getMessage());
      }
    } catch (IllegalAccessException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(MessageFormat.format(
          "Problem invoking the method ''{0}'' on {1}",
          createMethodName, parentSBase.getClass().getSimpleName()));
        logger.debug(e.getMessage());
      }
    } catch (InvocationTargetException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(MessageFormat.format(
          "Problem invoking the method ''{0}'' on {1}",
          createMethodName, parentSBase.getClass().getSimpleName()));
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
