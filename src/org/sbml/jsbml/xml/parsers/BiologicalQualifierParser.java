/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2011 jointly by the following organizations:
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

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.sbml.jsbml.Annotation;
import org.sbml.jsbml.CVTerm;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.CVTerm.Qualifier;
import org.sbml.jsbml.CVTerm.Type;

/**
 * A BiologicalQualifierParser is used to parse the XML elements and attributes
 * which have this namespace URI : http://biomodels.net/biology-qualifiers/.
 * 
 * @author Marine Dumousseau
 * @author Nicolas Rodriguez
 * @since 0.8
 * @version $Rev$
 */
public class BiologicalQualifierParser implements ReadingParser {

	private Logger logger = Logger.getLogger(BiologicalQualifierParser.class);
	
	/**
	 * The namespace URI of this ReadingParser.
	 */
	private static final String namespaceURI = "http://biomodels.net/biology-qualifiers/";

	/**
	 * @return the namespaceURI of this parser.
	 */
	public static String getNamespaceURI() {
		return namespaceURI;
	}

	/**
	 * The map containing all the relationships Miriam qualifier String <=>
	 * Miriam qualifier Qualifier.
	 */
	private HashMap<String, Qualifier> biologicalQualifierMap = new HashMap<String, Qualifier>();

	/**
	 * Creates a BiologicalQualifierParser instance and initialises the
	 * biologicalQualifierMap.
	 */
	public BiologicalQualifierParser() {
		initialisesBiologicalQualifierMap();
	}

	/**
	 * Initialises the biologicalQualifierMap of this parser.
	 */
	private void initialisesBiologicalQualifierMap() {
		// TODO : loading from a file would be better.
		
		// TODO : We need to try to make it work with qualifier that we do not know !!!
		
		for (CVTerm.Qualifier qualifier : CVTerm.Qualifier.values()) {
			if (qualifier.isBiologicalQualifier()) {
				biologicalQualifierMap.put(qualifier.getElementNameEquivalent(), qualifier);
				logger.debug("initialisesBiologicalQualifierMap : " + qualifier.getElementNameEquivalent());
			}			
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processAttribute(String
	 * ElementName, String AttributeName, String value, String prefix, boolean
	 * isLastAttribute, Object contextObject)
	 */
	public void processAttribute(String ElementName, String AttributeName,
			String value, String prefix, boolean isLastAttribute,
			Object contextObject) {
		// TODO : a node with a biological qualifier can't have attributes,
		// there is a SBML syntax error, log the error ?

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processCharactersOf(String
	 * elementName, String characters, Object contextObject)
	 */
	public void processCharactersOf(String elementName, String characters,
			Object contextObject) {
		// TODO : a node with a biological qualifier can't have text, there is a
		// SBML syntax error, log the error ?
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processEndDocument(SBMLDocument
	 * sbmlDocument)
	 */
	public void processEndDocument(SBMLDocument sbmlDocument) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processEndElement(String
	 * ElementName, String prefix, boolean isNested, Object contextObject)
	 */
	public boolean processEndElement(String ElementName, String prefix,
			boolean isNested, Object contextObject) {
		return true;
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

		// The namespace of this parser should be declared in the RDF subnode of
		// the annotation.
		// Adds the namespace to the RDFAnnotationNamespaces HashMap of
		// annotation.
		if (elementName.equals("RDF") && contextObject instanceof Annotation) {
			Annotation annotation = (Annotation) contextObject;

			annotation.addRDFAnnotationNamespace(localName, prefix, URI);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processStartElement(String
	 * elementName, String prefix, boolean hasAttributes, boolean hasNamespaces,
	 * Object contextObject)
	 */
	public Object processStartElement(String elementName, String prefix,
			boolean hasAttributes, boolean hasNamespaces, Object contextObject) {

		// A BiologicalQualifierParser can only modify a contextObject which is
		// an instance of Annotation.
		// That will probably change in SBML level 3 with the Annotation package
		if (contextObject instanceof Annotation) {
			Annotation annotation = (Annotation) contextObject;

			// This parser can parse only biological Miriam qualifiers. This
			// element should not have attributes or namespace declarations.
			// Creates a new CVTerm and
			// sets the qualifierType and biologicalQualifierType of this
			// CVTerm. Then, adds the
			// initialized CVTerm to annotation.
			if (biologicalQualifierMap.containsKey(elementName)
					&& !hasAttributes && !hasNamespaces) {
				CVTerm cvTerm = new CVTerm();
				cvTerm.setQualifierType(Type.BIOLOGICAL_QUALIFIER);
				cvTerm.setBiologicalQualifierType(biologicalQualifierMap
						.get(elementName));

				annotation.addCVTerm(cvTerm);
				return cvTerm;
			} else {
				// TODO : SBML syntax error, log an error
			}
		} else {
			// TODO the context object of a biological qualifier node should be
			// an annotation instance, there is a SBML syntax error, log an error ?
		}
		return contextObject;
	}
}
