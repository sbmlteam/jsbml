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

import java.util.HashMap;

import org.sbml.jsbml.Annotation;
import org.sbml.jsbml.CVTerm;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.CVTerm.Qualifier;
import org.sbml.jsbml.CVTerm.Type;
import org.sbml.jsbml.xml.stax.ReadingParser;

/**
 * A BiologicalQualifierParser is used to parse the XML elements and attributes
 * which have this namespace URI : http://biomodels.net/biology-qualifiers/.
 * 
 * @author marine
 * @author rodrigue
 * 
 */
public class BiologicalQualifierParser implements ReadingParser {

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
		
		biologicalQualifierMap.put("encodes", Qualifier.BQB_ENCODES);
		biologicalQualifierMap.put("hasPart", Qualifier.BQB_HAS_PART);
		biologicalQualifierMap.put("hasVersion", Qualifier.BQB_HAS_VERSION);
		biologicalQualifierMap.put("is", Qualifier.BQB_IS);
		biologicalQualifierMap.put("isDescribedBy", Qualifier.BQB_IS_DESCRIBED_BY);
		biologicalQualifierMap.put("isEncodedBy", Qualifier.BQB_IS_ENCODED_BY);
		biologicalQualifierMap.put("isHomologTo", Qualifier.BQB_IS_HOMOLOG_TO);
		biologicalQualifierMap.put("isPartOf", Qualifier.BQB_IS_PART_OF);
		biologicalQualifierMap.put("isVersionOf", Qualifier.BQB_IS_VERSION_OF);
		biologicalQualifierMap.put("occursIn", Qualifier.BQB_OCCURS_IN);
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
