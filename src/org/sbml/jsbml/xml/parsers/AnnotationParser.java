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

import org.sbml.jsbml.Annotation;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.xml.stax.ReadingParser;

/**
 * The AnnotationParser is used when some subelements of an annotation XML node
 * have a namespace URI/prefix which doesn't match any ReadingParser class and
 * so can't be parsed with another ReadingParser than this one. This class
 * allows to store the subelements of this annotation XML node into the
 * 'otherAnnotation' String of the matching Annotation instance.
 * 
 * @author marine
 * @author rodrigue
 */
public class AnnotationParser implements ReadingParser {

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

		// an AnnotationParser can only be used for the annotations of a SBML
		// component. If the contextObject is not
		// an Annotation instance, this parser doesn't process any XML
		// attributes.
		if (contextObject instanceof Annotation) {
			Annotation annotation = (Annotation) contextObject;

			// If there is a prefix, the AnnotationParser will also store the
			// prefix within the key
			// and appends the attribute to the 'otherAnnotation' String of
			// annotation.
			if (!prefix.equals("")) {
				annotation.appendNoRDFAnnotation(" " + prefix + ":"
						+ attributeName + "=\"" + value + "\"");
			} else {
				annotation.appendNoRDFAnnotation(" " + attributeName + "=\""
						+ value + "\"");
			}

			// If the attribute is the last attribute of its element, we need to
			// close the element tag.
			if (isLastAttribute) {
				annotation.appendNoRDFAnnotation("> \n");
			}
		} else {
			// There is a syntax error, the attribute can't be read?
			// TODO : log the problem 
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processCharactersOf(String
	 * elementName, String characters, Object contextObject)
	 */
	public void processCharactersOf(String elementName, String characters,
			Object contextObject) {
		// characters = characters.replaceAll("&", "&amp;").replaceAll("<",
		// "&lt;").replaceAll(">", "&gt;");
		characters = StringTools.encodeForHTML(characters);

		// an AnnotationParser can only be used for the annotations of a SBML
		// component. If the contextObject is not
		// an Annotation instance, this parser doesn't process any texts.
		if (contextObject instanceof Annotation) {
			Annotation annotation = (Annotation) contextObject;
			annotation.appendNoRDFAnnotation(characters + " \n");

		} else {
			// There is a syntax error, the text can't be read?
			// TODO : log the problem 
		}
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
	 * elementName, String prefix, boolean isNested, Object contextObject)
	 */
	public boolean processEndElement(String elementName, String prefix,
			boolean isNested, Object contextObject) {

		// an AnnotationParser can only be used for the annotations of a SBML
		// component. If the contextObject is not
		// an Annotation instance, this parser doesn't process any elements.
		if (contextObject instanceof Annotation) {
			Annotation annotation = (Annotation) contextObject;
			StringBuilder builder = annotation.getAnnotationBuilder();

			// If the element is nested, we need to remove the default ending
			// tag of the element.
			if (isNested && annotation.getNonRDFannotation().endsWith("> \n")) {
				int builderLength = builder.length();
				builder.delete(builderLength - 3, builderLength);
			}

			// If the element is nested, we need to add a nested element ending
			// tag.
			if (isNested) {
				annotation.appendNoRDFAnnotation("/> \n");
			}
			// else, write a entire ending tag with the name of the element
			// (Store the prefix too into the String).
			else {
				if (!prefix.equals("")) {
					annotation.appendNoRDFAnnotation("</" + prefix + ":"
							+ elementName + "> \n");
				} else {
					annotation.appendNoRDFAnnotation("</" + elementName
							+ "> \n");
				}
			}
		} else {
			// There is a syntax error, the node can't be read?
			// TODO : log the problem 
		}
		
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

		// If the element is an annotation and the contextObject is an
		// Annotation instance,
		// we need to add the namespace to the 'annotationNamespaces' HashMap of
		// annotation.
		if (elementName.equals("annotation")
				&& contextObject instanceof Annotation) {
			Annotation annotation = (Annotation) contextObject;
			annotation.addAnnotationNamespace(localName, prefix, URI);
		}
		// If the namespaces are declared in the sbml node, we need to add the
		// namespace to
		// the 'SBMLDocumentNamespaces' map of the SBMLDocument instance.
		else if (elementName.equals("sbml")
				&& contextObject instanceof SBMLDocument) {
			SBMLDocument sbmlDocument = (SBMLDocument) contextObject;
			sbmlDocument.addNamespace(localName, prefix, URI);
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

		// an AnnotationParser can only be used for the annotations of a SBML
		// component. If the contextObject is not
		// an Annotation instance, this parser doesn't process any elements.
		if (contextObject instanceof Annotation) {

			Annotation annotation = (Annotation) contextObject;

			// If there is a prefix, the AnnotationParser will also store the
			// prefix within the key
			// and appends the element to the 'otherAnnotation' String of
			// annotation.
			if (!prefix.equals("")) {
				annotation.appendNoRDFAnnotation("<" + prefix + ":"
						+ elementName);
			} else {
				annotation.appendNoRDFAnnotation("<" + elementName);
			}

			// If the element has no attributes and namespaces, we need to close
			// the element tag.
			if (!hasAttributes && !hasNamespaces) {
				annotation.appendNoRDFAnnotation("> \n");
			}
			return annotation;
		} else {
			// There is a syntax error, the node can't be read ?
			// TODO : log the problem 
		}
		return contextObject;
	}
}
