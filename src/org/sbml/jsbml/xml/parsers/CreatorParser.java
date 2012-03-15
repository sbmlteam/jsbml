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

import org.sbml.jsbml.Annotation;
import org.sbml.jsbml.History;
import org.sbml.jsbml.SBMLDocument;

/**
 * A CreatorParser is used to parser the subelements of an annotation element which have this namespaceURI :
 * http://purl.org/dc/elements/1.1/.
 * 
 * @author Marine Dumousseau 
 * @author Nicolas Rodriguez
 * @since 0.8
 * @version $Rev$
 */
public class CreatorParser implements ReadingParser {

	/**
	 * The namespace URI of this ReadingParser.
	 */
	private static final String namespaceURI = "http://purl.org/dc/elements/1.1/";
	
	 /**
	 * @return the namespaceURI of this parser.
	 */
	public static String getNamespaceURI() {
		return namespaceURI;
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
		// is a SBML syntax error, log an error ?
	}

	 /* (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processCharactersOf(String elementName, String characters,
			Object contextObject)
	 */
	public void processCharactersOf(String elementName, String characters,
			Object contextObject) {
		// TODO : there is no text for the element with the namespace "http://purl.org/dc/elements/1.1/", there
		// is a SBML syntax error, log an error ?
	}

	 /* (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processEndDocument(SBMLDocument sbmlDocument)
	 */
	public void processEndDocument(SBMLDocument sbmlDocument) {
	}

	/* (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processEndElement(String elementName, String prefix,
			boolean isNested, Object contextObject)
	 */
	public boolean processEndElement(String elementName, String prefix,
			boolean isNested, Object contextObject) {
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
			Object contextObject) {
		
		// The namespace of this parser is declared in a RDF subnode of an annotation.
		// adds the namespace to the RDFAnnotationNamespaces HashMap of annotation.
		if (elementName.equals("RDF") && (contextObject instanceof Annotation)) {
			Annotation annotation = (Annotation) contextObject;
			
			annotation.addRDFAnnotationNamespace(localName, prefix, URI);
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
			Object contextObject) {

		// A CreatorParser can only modify a contextObject which is an Annotation instance.
		// The namespace of this parser should be associated with the creator subnode of an annotation.
		// Creates a ModelHistory instance and set the modelHistory of annotation.
		if (elementName.equals("creator") && (contextObject instanceof Annotation)) {
			Annotation annotation = (Annotation) contextObject;
			History modelHistory = new History();
			annotation.setHistory(modelHistory);
			
			return modelHistory;
		}
		else {
			// TODO : !elementName.equals("creator"), SBML syntax error?
			// TODO : !contextObject instanceof ModelAnnotation, for the moment, only a model with a modelAnnotaton
			// can contain an history. Should be changed depending on the version.
		}
		return contextObject;
	}

}
