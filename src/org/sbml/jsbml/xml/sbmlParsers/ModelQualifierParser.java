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

package org.sbml.jsbml.xml.sbmlParsers;

import java.util.HashMap;

import org.sbml.jsbml.Annotation;
import org.sbml.jsbml.CVTerm;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.CVTerm.Qualifier;
import org.sbml.jsbml.xml.stax.ReadingParser;

/**
 * A ModelQualifierParser is used to parse the XML elements and attributes which have this
 * namespace URI : http://biomodels.net/model-qualifiers/.
 * @author marine
 *
 */
public class ModelQualifierParser implements ReadingParser{

	/**
	 * The namespace URI of this ReadingParser.
	 */
	private static final String namespaceURI = "http://biomodels.net/biology-qualifiers/";
	
	/**
	 * The map containing all the relationships Miriam qualifier String <=> Miriam qualifier Qualifier.
	 */
	private HashMap<String, Qualifier> modelQualifierMap = new HashMap<String, Qualifier>();

	/**
	 * Creates a ModelQualifierParser instance and initialises the modelQualifierMap.
	 */
	public ModelQualifierParser(){
		initialisesModelQualifierMap();
	}
	
	/**
	 * Initialises the modelQualifierMap of this parser.
	 */
	private void initialisesModelQualifierMap(){
		// TODO maybe loading from a file would be better.
		modelQualifierMap.put("is", Qualifier.BQM_IS);
		modelQualifierMap.put("isDescribedBy", Qualifier.BQM_IS_DESCRIBED_BY);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processCharactersOf(String elementName, String characters,
			Object contextObject)
	 */
	public void processCharactersOf(String elementName, String characters,
			Object contextObject) {	
		// TODO : a node with the namespace "http://biomodels.net/model-qualifiers/" can't have text.
		// Throw an error?
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processEndElement(String ElementName, String prefix,
			boolean isNested, Object contextObject)
	 */
	public void processEndElement(String ElementName, String prefix,
			boolean isNested, Object contextObject) {		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processStartElement(String elementName, String prefix,
			boolean hasAttributes, boolean hasNamespaces,
			Object contextObject)
	 */
	public Object processStartElement(String elementName, String prefix,
			boolean hasAttributes, boolean hasNamespaces,
			Object contextObject) {

		// A ModelQualifierParser can only modify a contextObject which is an instance of Annotation.
		if (contextObject instanceof Annotation){
			Annotation annotation = (Annotation) contextObject;
			
			// This parser can parse only model Miriam qualifiers. This element should not have attributes or namespace declarations. 
			// Creates a new CVTerm and 
			// sets the qualifierType and modelQualifierType of this CVTerm. Then, adds the 
			// initialised CVTerm to annotation.
			if (modelQualifierMap.containsKey(elementName) && !hasNamespaces && !hasAttributes){
				CVTerm cvTerm = new CVTerm();
				cvTerm.setQualifierType(Qualifier.MODEL_QUALIFIER);
				cvTerm.setModelQualifierType(modelQualifierMap.get(elementName));
				
				annotation.addCVTerm(cvTerm);
				return cvTerm;
			}
			else {
				// TODO : SBML syntax error, throw an exception?
			}
		}
		else {
			// TODO : SBML syntax error, throw an exception?
		}
		return contextObject;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processEndDocument(SBMLDocument sbmlDocument)
	 */
	public void processEndDocument(SBMLDocument sbmlDocument) {		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processNamespace(String elementName, String URI, String prefix,
			String localName, boolean hasAttributes, boolean isLastNamespace,
			Object contextObject)
	 */
	public void processNamespace(String elementName, String URI, String prefix,
			String localName, boolean hasAttributes, boolean isLastNamespace,
			Object contextObject) {
		
		// The namespace of this parser should be declared in the RDF subnode of the annotation.
		// Adds the namespace to the RDFAnnotationNamespaces HashMap of annotation.
		if (elementName.equals("RDF") && contextObject instanceof Annotation){
			Annotation annotation = (Annotation) contextObject;
			
			annotation.addRDFAnnotationNamespace(localName, prefix, URI);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processAttribute(String ElementName, String AttributeName,
			String value, String prefix, boolean isLastAttribute,
			Object contextObject)
	 */
	public void processAttribute(String ElementName, String AttributeName,
			String value, String prefix, boolean isLastAttribute,
			Object contextObject) {
		// TODO : a node with the namespace "http://biomodels.net/model-qualifiers/" can't have attributes.
		// Throw an error?
	}

	/**
	 * @return the namespaceURI
	 */
	public static String getNamespaceURI() {
		return namespaceURI;
	}
}
