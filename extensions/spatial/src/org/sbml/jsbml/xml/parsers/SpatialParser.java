/*
 * $Id:  SpatialParser.java 19:44:27 draeger $
 * $URL: SpatialParser.java $
 *
 * 
 *==================================================================================
 * Copyright (c) 2009 The jsbml team.
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

import java.util.List;

import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.xml.stax.SBMLObjectForXML;

/**
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * @version $Rev$
 */
public class SpatialParser implements ReadingParser, WritingParser {

	/**
	 * The namespace URI of this parser.
	 */
	private static final String namespaceURI = "http://www.sbml.org/sbml/level3/version1/spatial/version1";
	
	/**
	 * The name space of required elements.
	 */
	private static final String namespaceURIrequired = "http://www.sbml.org/sbml/level3/version1/requiredElements/version1";
	
	/**
	 * 
	 */
	public SpatialParser() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.xml.parsers.WritingParser#getListOfSBMLElementsToWrite(java.lang.Object)
	 */
	public List<Object> getListOfSBMLElementsToWrite(Object objectToWrite) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.xml.parsers.WritingParser#writeAttributes(org.sbml.jsbml.xml.stax.SBMLObjectForXML, java.lang.Object)
	 */
	public void writeAttributes(SBMLObjectForXML xmlObject,
			Object sbmlElementToWrite) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.xml.parsers.WritingParser#writeCharacters(org.sbml.jsbml.xml.stax.SBMLObjectForXML, java.lang.Object)
	 */
	public void writeCharacters(SBMLObjectForXML xmlObject,
			Object sbmlElementToWrite) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.xml.parsers.WritingParser#writeElement(org.sbml.jsbml.xml.stax.SBMLObjectForXML, java.lang.Object)
	 */
	public void writeElement(SBMLObjectForXML xmlObject,
			Object sbmlElementToWrite) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.xml.parsers.WritingParser#writeNamespaces(org.sbml.jsbml.xml.stax.SBMLObjectForXML, java.lang.Object)
	 */
	public void writeNamespaces(SBMLObjectForXML xmlObject,
			Object sbmlElementToWrite) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.xml.parsers.ReadingParser#processAttribute(java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean, java.lang.Object)
	 */
	public void processAttribute(String elementName, String attributeName,
			String value, String prefix, boolean isLastAttribute,
			Object contextObject) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.xml.parsers.ReadingParser#processCharactersOf(java.lang.String, java.lang.String, java.lang.Object)
	 */
	public void processCharactersOf(String elementName, String characters,
			Object contextObject) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.xml.parsers.ReadingParser#processEndDocument(org.sbml.jsbml.SBMLDocument)
	 */
	public void processEndDocument(SBMLDocument sbmlDocument) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.xml.parsers.ReadingParser#processEndElement(java.lang.String, java.lang.String, boolean, java.lang.Object)
	 */
	public boolean processEndElement(String elementName, String prefix,
			boolean isNested, Object contextObject) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.xml.parsers.ReadingParser#processNamespace(java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean, boolean, java.lang.Object)
	 */
	public void processNamespace(String elementName, String URI, String prefix,
			String localName, boolean hasAttributes, boolean isLastNamespace,
			Object contextObject) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.xml.parsers.ReadingParser#processStartElement(java.lang.String, java.lang.String, boolean, boolean, java.lang.Object)
	 */
	public Object processStartElement(String elementName, String prefix,
			boolean hasAttributes, boolean hasNamespaces, Object contextObject) {
		// TODO Auto-generated method stub
		return null;
	}

}
