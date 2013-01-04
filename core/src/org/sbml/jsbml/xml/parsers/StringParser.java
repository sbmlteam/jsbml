/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2013 jointly by the following organizations:
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

import org.apache.log4j.Logger;
import org.sbml.jsbml.Constraint;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.xml.XMLAttributes;
import org.sbml.jsbml.xml.XMLNamespaces;
import org.sbml.jsbml.xml.XMLNode;
import org.sbml.jsbml.xml.XMLTriple;

/**
 * A {@link StringParser} can be used to store the HTML expressions into an {@link XMLNode} in the
 * SBML component.
 * 
 * @author Nicolas Rodriguez
 * @since 0.8
 * @version $Rev$
 */
public class StringParser implements ReadingParser {
	
	/**
	 * String to be able to detect what type of String this parser is parsing. It can be 'notes' or 'message'.
	 */
	private String typeOfNotes = "";
	
	private Logger logger = Logger.getLogger(StringParser.class);

	/**
	 * 
	 * @return the typeOfNotes of this ReadingParser.
	 */
	public String getTypeOfNotes() {
		return typeOfNotes;
	}
	
	/* (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processAttribute(String elementName, String attributeName,
			String value, String prefix, boolean isLastAttribute,
			Object contextObject)
	 */
	public void processAttribute(String elementName, String attributeName,
			String value, String prefix, boolean isLastAttribute,
			Object contextObject) 
	{
		
		logger.debug("processAttribute : attribute name = " + attributeName + ", value = " + value);
		
		if (contextObject instanceof XMLNode) {
			
			XMLNode xmlNode = (XMLNode) contextObject;
			xmlNode.addAttr(attributeName, value, null, prefix);
			
			
		} else {
			logger.debug("processAttribute : context Object is not an XMLNode !!! " + contextObject);
		}
		
	}

	/* (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processCharactersOf(String elementName, String characters,
			Object contextObject)
	 */
	public void processCharactersOf(String elementName, String characters,
			Object contextObject) {
		
		logger.debug("processCharactersOf called : characters = @" + characters + "@");
		
		// characters = StringTools.encodeForHTML(characters); // TODO : use an apache util for that.

		XMLNode textNode = new XMLNode(characters);

		if (contextObject instanceof XMLNode) {
			
			XMLNode xmlNode = (XMLNode) contextObject;

			xmlNode.addChild(textNode);
			
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
			else 
			{
				logger.warn("The type of String " + typeOfNotes + " on the element " + 
						parentSBMLElement.getElementName() + " is unknown !! Some data might be lost");
				return;
			}

			xmlNode.addChild(textNode);
			
		} else {	
			logger.debug("processCharactersOf : context Object is not an XMLNode or SBase !!! " + contextObject);
		}

		
	}

	/* (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processEndDocument(SBMLDocument sbmlDocument)
	 */
	public void processEndDocument(SBMLDocument sbmlDocument) {
		// TODO : nothing special to be done I think ??!!!
	}

	/* (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processEndElement(String elementName, String prefix,
			boolean isNested, Object contextObject)
	 */
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
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processNamespace(String elementName, String URI, String prefix,
			String localName, boolean hasAttributes, boolean isLastNamespace,
			Object contextObject)
	 */
	public void processNamespace(String elementName, String URI, String prefix,
			String localName, boolean hasAttributes, boolean isLastNamespace,
			Object contextObject) 
	{
		if (contextObject instanceof XMLNode) {
			
			XMLNode xmlNode = (XMLNode) contextObject;
			if (!xmlNode.isStart()) {
				logger.debug("processNamespace : context Object is not a start node !!! " + contextObject);
			}
			
			xmlNode.addNamespace(URI, prefix);
			
		} else {
			logger.debug("processNamespace : context Object is not an XMLNode !!! " + contextObject);
			logger.debug("processNamespace : element name = " + elementName + ", namespace = " + prefix + ":" + URI);
		}
		
	}

	/* (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processStartElement(String elementName, String prefix,
			boolean hasAttributes, boolean hasNamespaces,
			Object contextObject)
	 */
	public Object processStartElement(String elementName, String prefix,
			boolean hasAttributes, boolean hasNamespaces,
			Object contextObject) 
	{
		logger.debug("processStartElement : element name = " + elementName);
		
		if (elementName.equals("notes")
				&& (contextObject instanceof SBase)) {
			SBase sbase = (SBase) contextObject;
			sbase.setNotes(new XMLNode(new XMLTriple("notes", null, null), new XMLAttributes()));
			return contextObject;
		}
		
		// Creating a StartElement XMLNode !!	
		XMLNode xmlNode = new XMLNode(new XMLTriple(elementName, null, prefix), new XMLAttributes(), new XMLNamespaces());
		
		if (contextObject instanceof SBase) {
			SBase parentSBMLElement = (SBase) contextObject;
			
			if (typeOfNotes.equals("notes")) {
				parentSBMLElement.getNotes().addChild(xmlNode);
			} else if (typeOfNotes.equals("message") && parentSBMLElement instanceof Constraint) {
				((Constraint) parentSBMLElement).getMessage().addChild(xmlNode);
			} else {
				logger.warn("The type of String " + typeOfNotes + " on the element " + 
						parentSBMLElement.getElementName() + " is unknown !! Some data might be lost");
			}
			
		} else if (contextObject instanceof XMLNode) {
			XMLNode parentNode = (XMLNode) contextObject;
			
			parentNode.addChild(xmlNode);
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
}
