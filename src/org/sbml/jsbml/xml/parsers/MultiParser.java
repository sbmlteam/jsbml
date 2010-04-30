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

import java.util.ArrayList;

import org.sbml.jsbml.Annotation;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.ext.multi.InitialSpeciesInstance;
import org.sbml.jsbml.ext.multi.MultiList;
import org.sbml.jsbml.ext.multi.MultiSpecies;
import org.sbml.jsbml.xml.stax.ReadingParser;
import org.sbml.jsbml.xml.stax.SBMLObjectForXML;
import org.sbml.jsbml.xml.stax.WritingParser;

/**
 * A MultiParser is used to parse the multi extension package elements and
 * attributes. The namespaceURI URI of this parser is
 * "http://www.sbml.org/sbml/level3/version1/multi/version1". This parser is
 * able to read and write elements of the multi package (implements
 * ReadingParser and WritingParser).
 * 
 * @author marine
 * 
 */
public class MultiParser implements ReadingParser, WritingParser {

	/**
	 * The namespace URI of this parser.
	 */
	private static final String namespaceURI = "http://www.sbml.org/sbml/level3/version1/multi/version1";

	/**
	 * The MultiList enum which represents the name of the list this parser is
	 * currently reading. If the Multi package have some lists included into
	 * other lists, it is maybe better to extend the ListOf class and add an new
	 * enum instance with the names of the possible lists of the multi package.
	 */
	private MultiList multiList = MultiList.none;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processAttribute(String
	 * elementName, String attributeName, String value, String prefix, boolean
	 * isLastAttribute, Object contextObject)
	 */
	public void processAttribute(String elementName, String attributeName,
			String value, String prefix, boolean isLastAttribute,
			Object contextObject) {

		boolean isAttributeRead = false;
		if (contextObject instanceof SBase) {
			SBase sbase = (SBase) contextObject;
			isAttributeRead = sbase.readAttribute(attributeName, prefix, value);
		} else if (contextObject instanceof Annotation) {
			Annotation annotation = (Annotation) contextObject;
			isAttributeRead = annotation.readAttribute(attributeName, prefix,
					value);
		}

		if (!isAttributeRead) {
			// TODO : throw new SBMLException ("The attribute " + attributeName
			// + " on the element " + elementName +
			// "is not part of the SBML specifications");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processAttribute(String
	 * elementName, String attributeName, String value, String prefix, boolean
	 * isLastAttribute, Object contextObject)
	 */
	public void processCharactersOf(String elementName, String characters,
			Object contextObject) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processStartElement(String
	 * elementName, String prefix, boolean hasAttributes, boolean hasNamespaces,
	 * Object contextObject)
	 */
	@SuppressWarnings("unchecked")
	public Object processStartElement(String elementName, String prefix,
			boolean hasAttributes, boolean hasNamespaces, Object contextObject) {

		if (contextObject instanceof Species) {
			Species species = (Species) contextObject;
			if (elementName.equals("listOfInitialSpeciesInstances")) {
				ListOf<InitialSpeciesInstance> listOfInitialSpeciesInstances = new ListOf<InitialSpeciesInstance>();
				listOfInitialSpeciesInstances
						.setSBaseListType(ListOf.Type.other);
				this.multiList = MultiList.listOfInitialSpeciesInstances;

				MultiSpecies multiSpecies = new MultiSpecies(species);
				multiSpecies
						.setListOfInitialSpeciesInstance(listOfInitialSpeciesInstances);
				species.addExtension(MultiParser.namespaceURI, multiSpecies);

				return listOfInitialSpeciesInstances;
			}
		} else if (contextObject instanceof ListOf<?>) {
			ListOf<SBase> listOf = (ListOf<SBase>) contextObject;

			if (elementName.equals("initialSpeciesInstance")
					&& this.multiList
							.equals(MultiList.listOfInitialSpeciesInstances)) {
				InitialSpeciesInstance initialSpeciesInstance = new InitialSpeciesInstance();
				listOf.add(initialSpeciesInstance);

				return initialSpeciesInstance;
			}

		}
		return contextObject;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processEndElement(String
	 * elementName, String prefix, boolean isNested, Object contextObject)
	 */
	public void processEndElement(String elementName, String prefix,
			boolean isNested, Object contextObject) {

		if (elementName.equals("listOfSpeciesInstances")) {
			this.multiList = MultiList.none;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processEndDocument(SBMLDocument
	 * sbmlDocument)
	 */
	public void processEndDocument(SBMLDocument sbmlDocument) {
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 * @return the namespaceURI of this parser.
	 */
	public static String getNamespaceURI() {
		return namespaceURI;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.WritingParser#getListOfSBMLElementsToWrite(Object
	 * sbase)
	 */
	public ArrayList<Object> getListOfSBMLElementsToWrite(Object sbase) {
		if (sbase instanceof Species) {
			// TODO return the listOf..
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.WritingParser#writeElement(SBMLObjectForXML
	 * xmlObject, Object sbmlElementToWrite)
	 */
	public void writeElement(SBMLObjectForXML xmlObject,
			Object sbmlElementToWrite) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.WritingParser#writeAttributes(SBMLObjectForXML
	 * xmlObject, Object sbmlElementToWrite)
	 */
	public void writeAttributes(SBMLObjectForXML xmlObject,
			Object sbmlElementToWrite) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.WritingParser#writeCharacters(SBMLObjectForXML
	 * xmlObject, Object sbmlElementToWrite)
	 */
	public void writeCharacters(SBMLObjectForXML xmlObject,
			Object sbmlElementToWrite) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.WritingParser#writeNamespaces(SBMLObjectForXML
	 * xmlObject, Object sbmlElementToWrite)
	 */
	public void writeNamespaces(SBMLObjectForXML xmlObject,
			Object sbmlElementToWrite) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processNamespace(String
	 * elementName, String URI, String prefix, String localName, boolean
	 * hasAttributes, boolean isLastNamespace, Object contextObject)
	 */
	public void processNamespace(String elementName, String URI, String prefix,
			String localName, boolean hasAttributes, boolean isLastNamespace,
			Object contextObject) {
		// TODO Auto-generated method stub

	}

}
