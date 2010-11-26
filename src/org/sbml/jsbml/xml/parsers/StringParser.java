/*
 * $Id$
 * $URL$
 *
 *
 *==================================================================================
 * Copyright (c) 2009 the copyright is held jointly by the individual
 * authors. See the file AUTHORS for the list of authors.
 *
 * This file is part of jsbml, the pure java SBML library. Please visit
 * http://sbml.org for more information about SBML, and http://jsbml.sourceforge.net/
 * to get the latest version of jsbml.
 *
 * jsbml is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * jsbml is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with jsbml.  If not, see <http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html>.
 *
 *===================================================================================
 *
 */

package org.sbml.jsbml.xml.parsers;

import org.apache.log4j.Logger;

import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.xml.XMLAttributes;
import org.sbml.jsbml.xml.XMLNamespaces;
import org.sbml.jsbml.xml.XMLNode;
import org.sbml.jsbml.xml.XMLTriple;
import org.sbml.jsbml.xml.stax.ReadingParser;

/**
 * A StringParser can be used to store the html expressions into an XMLNode in the
 * SBML component.
 * 
 * @author rodrigue
 *
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
		
		logger.debug("processCharactersOf called.");
		
		// characters = StringTools.encodeForHTML(characters); // TODO : use an apache util for that.

		if (contextObject instanceof XMLNode) {
			
			XMLNode xmlNode = (XMLNode) contextObject;
			XMLNode textNode = new XMLNode(characters);

			xmlNode.addChild(textNode);
			
		} else {
			logger.debug("processCharactersOf : context Object is not an XMLNode !!! " + contextObject);
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
			
			if (xmlNode.getNumChildren() == 0) {
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
		
		// Creating a StartElement XMLNode !!	
		XMLNode xmlNode = new XMLNode(new XMLTriple(elementName, null, prefix), new XMLAttributes(), new XMLNamespaces());
		
		if (contextObject instanceof SBase) {
			SBase parentSBMLElement = (SBase) contextObject;
			
			parentSBMLElement.getNotes().addChild(xmlNode);
			
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
