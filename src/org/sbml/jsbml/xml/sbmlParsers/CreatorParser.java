/*
 * $Id: CreatorParser.java 38 2009-12-11 15:50:38Z marine3 $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/src/org/sbml/jsbml/xml/sbmlParsers/CreatorParser.java $
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

import org.sbml.jsbml.Annotation;
import org.sbml.jsbml.ModelHistory;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.xml.stax.ReadingParser;

/**
 * A CreatorParser is used to parser the subelements of an annotation element which have this namespaceURI :
 * http://purl.org/dc/elements/1.1/.
 * @author marine
 *
 */
public class CreatorParser implements ReadingParser{

	/**
	 * The namespace URI of this ReadingParser.
	 */
	private static final String namespaceURI = "http://purl.org/dc/elements/1.1/";
	
	 /* (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processCharactersOf(String elementName, String characters,
			Object contextObject)
	 */
	public void processCharactersOf(String elementName, String characters,
			Object contextObject) {
		// TODO : there is no text for the element with the namespace "http://purl.org/dc/elements/1.1/", there
		// is a SBML syntax error, throw an exception?
	}

	 /* (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processEndElement(String elementName, String prefix,
			boolean isNested, Object contextObject)
	 */
	public void processEndElement(String elementName, String prefix,
			boolean isNested, Object contextObject) {
	}

	 /* (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processStartElement(String elementName, String prefix,
			boolean hasAttributes, boolean hasNamespaces,
			Object contextObject)
	 */
	public Object processStartElement(String elementName, String prefix,
			boolean hasAttributes, boolean hasNamespaces,
			Object contextObject) {
		System.out.println("coucou");
		System.out.println(hasAttributes);

		// A CreatorParser can only modify a contextObject which is an Annotation instance.
		// The namespace of this parser should be associated with the creator subnode of an annotation.
		// Creates a ModelHistory instance and set the modelHistory of annotation.
		if (elementName.equals("creator") && contextObject instanceof Annotation){
			Annotation annotation = (Annotation) contextObject;
			ModelHistory modelHistory = new ModelHistory();
			annotation.setModelHistory(modelHistory);
			
			return modelHistory;
		}
		else {
			// TODO : !elementName.equals("creator"), SBML syntax error?
			// TODO : !contextObject instanceof ModelAnnotation, for the moment, only a model with a modelAnnotaton
			// can contain an history. Should be changed depending on the version.
		}
		return contextObject;
	}

	 /* (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processEndDocument(SBMLDocument sbmlDocument)
	 */
	public void processEndDocument(SBMLDocument sbmlDocument) {
	}

	/* (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processNamespace(String elementName, String URI, String prefix,
			String localName, boolean hasAttributes, boolean isLastNamespace,
			Object contextObject)
	 */
	public void processNamespace(String elementName, String URI, String prefix,
			String localName, boolean hasAttributes, boolean isLastNamespace,
			Object contextObject) {
		
		// The namespace of this parser is declared in a RDF subnode of an annotation.
		// adds the namespace to the RDFAnnotationNamespaces HashMap of annotation.
		if (elementName.equals("RDF") && contextObject instanceof Annotation){
			Annotation annotation = (Annotation) contextObject;
			
			annotation.addRDFAnnotationNamespace(localName, prefix, URI);
		}
	}

	/* (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processAttribute(String ElementName, String AttributeName,
			String value, String prefix, boolean isLastAttribute,
			Object contextObject)
	 */
	public void processAttribute(String ElementName, String AttributeName,
			String value, String prefix, boolean isLastAttribute,
			Object contextObject) {
		// TODO : there is no attributes with the namespace "http://purl.org/dc/elements/1.1/", there
		// is a SBML syntax error, throw an exception?
	}

	/**
	 * @return the namespaceURI of this parser.
	 */
	public static String getNamespaceURI() {
		return namespaceURI;
	}

}
