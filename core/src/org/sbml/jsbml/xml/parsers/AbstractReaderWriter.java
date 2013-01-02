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
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;
import org.sbml.jsbml.Annotation;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.SBasePlugin;
import org.sbml.jsbml.xml.stax.SBMLObjectForXML;

/**
 * Contains some code shared by most of the L3 packages parsers.
 * 
 * @author Nicolas Rodriguez
 * @since 1.0
 * @version $Rev$
 *
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
	public void processAttribute(String elementName, String attributeName,
			String value, String prefix, boolean isLastAttribute,
			Object contextObject) 
	{

		logger.debug("processAttribute -> " + prefix + " : " + attributeName + " = " + value + " (" + contextObject.getClass().getName() + ")");
		
		boolean isAttributeRead = false;

		if (contextObject instanceof SBase) {

			SBase sbase = (SBase) contextObject;
			
			// logger.debug("processAttribute : level, version = " + sbase.getLevel() + ", " + sbase.getVersion());
			
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
			logger.warn("processAttribute : The attribute " + attributeName
					+ " on the element " + elementName +
					" is not part of the SBML specifications");
		}
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.xml.stax.ReadingParser#processCharactersOf(java.lang.String, java.lang.String, java.lang.Object)
	 */
	public void processCharactersOf(String elementName, String characters,
			Object contextObject) 
	{		
		logger.debug("processCharactersOf : the element " + elementName + " does not have any text. " +
				"SBML syntax error. Characters lost = " + characters);
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.xml.parsers.ReadingParser#processEndDocument(org.sbml.jsbml.SBMLDocument)
	 */
	public void processEndDocument(SBMLDocument sbmlDocument) {
		// does nothing		
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.xml.parsers.ReadingParser#processEndElement(java.lang.String, java.lang.String, boolean, java.lang.Object)
	 */
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
	public void processNamespace(String elementName, String URI, String prefix,
			String localName, boolean hasAttributes, boolean isLastNamespace,
			Object contextObject) 
	{
		// TODO : read the namespace, it could be some other extension objects
	}


	/* (non-Javadoc)
	 * @see org.sbml.jsbml.xml.parsers.ReadingParser#processStartElement(java.lang.String, java.lang.String, boolean, boolean, java.lang.Object)
	 */
	public abstract Object processStartElement(String elementName, String prefix,
			boolean hasAttributes, boolean hasNamespaces, Object contextObject);

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.xml.WritingParser#getListOfSBMLElementsToWrite(Object sbase)
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getListOfSBMLElementsToWrite(Object sbase) {

		logger.debug("getListOfSBMLElementsToWrite : " + sbase.getClass().getCanonicalName());
		
		List<Object> listOfElementsToWrite = new ArrayList<Object>();
		Enumeration<TreeNode> children = null;
		
		if (sbase instanceof SBMLDocument) {
			// nothing to do
			// TODO : the 'required' attribute is written even if there is no plugin class for the SBMLDocument
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

		logger.debug("getListOfSBMLElementsToWrite :  nb children = " + listOfElementsToWrite.size());
		logger.debug("getListOfSBMLElementsToWrite :  children = " + listOfElementsToWrite);

		if (listOfElementsToWrite.isEmpty()) {
			listOfElementsToWrite = null;
		} 

		return listOfElementsToWrite;
	}

	
	/* (non-Javadoc)
	 * @see org.sbml.jsbml.xml.parsers.WritingParser#writeAttributes(SBMLObjectForXML xmlObject, Object sbmlElementToWrite)
	 */
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
	public void writeCharacters(SBMLObjectForXML xmlObject,
			Object sbmlElementToWrite) 
	{
		logger.error("writeCharacters : " + xmlObject.getName() + " XML element do not have any characters !!");
	}


	/* (non-Javadoc)
	 * @see org.sbml.jsbml.xml.parsers.WritingParser#writeElement(SBMLObjectForXML
	 *      xmlObject, Object sbmlElementToWrite)
	 */
	public void writeElement(SBMLObjectForXML xmlObject,
			Object sbmlElementToWrite) {

		logger.debug("writeElement : " + sbmlElementToWrite.getClass().getSimpleName());

		if (sbmlElementToWrite instanceof SBase) {
			SBase sbase = (SBase) sbmlElementToWrite;

			logger.debug("writeElement : sbase.namespaces size = " + sbase.getNamespaces().size());
			logger.debug("writeElement : sbase.namespaces = " + sbase.getNamespaces());
			
		      if (!sbase.getNamespaces().contains(getNamespaceURI())) {
		    	  logger.debug("writeElement : rejected element");
		    	  return;
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
	 * @see org.sbml.jsbml.xml.parsers.WritingParser#writeNamespaces(SBMLObjectForXML
	 *      xmlObject, Object sbmlElementToWrite)
	 */
	public void writeNamespaces(SBMLObjectForXML xmlObject,
			Object sbmlElementToWrite) 
	{
		if (sbmlElementToWrite instanceof SBase) {
			xmlObject.setPrefix(getShortLabel());
		}

		// TODO : write all namespaces
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
