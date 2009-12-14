/*
 * $Id: DatesParser.java 38 2009-12-11 15:50:38Z marine3 $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/src/org/sbml/jsbml/xml/sbmlParsers/DatesParser.java $
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

import java.util.Date;

import org.sbml.jsbml.element.Annotation;
import org.sbml.jsbml.element.ModelHistory;
import org.sbml.jsbml.element.SBMLDocument;
import org.sbml.jsbml.xml.DateProcessor;
import org.sbml.jsbml.xml.ReadingParser;
import org.w3c.util.DateParser;
import org.w3c.util.InvalidDateException;

/**
 * A DatesParser is used to parse the subNodes of an annotation which have this namespace URI :
 * http://purl.org/dc/terms/.
 * @author marine
 *
 */
public class DatesParser implements ReadingParser{
	
	/**
	 * The namespace URI of this parser.
	 */
	private static final String namespaceURI = "http://purl.org/dc/terms/";
	
	/**
	 * Stores the localName of the last element read by this parser.
	 */
	private String previousElement = "";
	/**
	 * Boolean value to know if a 'created' element has been read by this parser.
	 */
	private boolean hasReadCreated = false;
	/**
	 * Boolean value to know if a 'W3CDTF' element has been read by this parser.
	 */
	boolean hasReadW3CDTF = false;
	/**
	 * Boolean value to know if a 'modified' element has been read by this parser.
	 */
	boolean hasReadModified = false;

	/* (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processCharactersOf(String elementName, String characters,
			Object contextObject)
	 */
	public void processCharactersOf(String elementName, String characters,
			Object contextObject) {
		
		// An elementName can be null if the text appears after an element ending tag. In this case,
		// this parser will not parse the text.
		if (elementName != null){
			
			// A DatesParser can only modify a contextObject which is a ModelHistory instance.
			if (contextObject instanceof ModelHistory){
				ModelHistory modelHistory = (ModelHistory) contextObject;
				DateProcessor dateProcessor = new DateProcessor();
				
				// The date to parse is the text of a 'W3CDTF' element.
				// However, the date will be parsed only if the syntax of this node and the previous
				// node respects the SBML specifications.
				if (elementName.equals("W3CDTF") && hasReadW3CDTF){
					
					// If the previous node was a 'created' element and respected the SBML specifications,
					// a new Date will be created and set to the text value of this node.
					// Sets the created Date of modelHistory.
					if (hasReadCreated && previousElement.equals("created")){
						String stringDate = dateProcessor.formatToW3CDTF(characters);

						try {
							Date createdDate = DateParser.parse(stringDate);
							modelHistory.setCreatedDate(createdDate);
						} catch (InvalidDateException e) {
							// TODO : can't create a Date, what to do?
							e.printStackTrace();
						}
					}
					// If the previous node was a 'modified' element and respected the SBML specifications,
					// a new Date will be created and set to the text value of this node.
					// Sets the modified Date and adds the new Date to the listOfModifications of modelHistory.
					else if (previousElement.equals("modified")){
						String stringDate = dateProcessor.formatToW3CDTF(characters);

						try {
							Date modifiedDate = DateParser.parse(stringDate);
							modelHistory.setModifiedDate(modifiedDate);
						} catch (InvalidDateException e) {
							// TODO : can't create a Date, what to do?
							e.printStackTrace();
						}
					}
					else {
						// TODO : SBML syntax error, what to do?
					}
				}
				else {
					// TODO : SBML syntax error, what to do?
				}
			}
			else {
				// TODO : the date instances are only created for the model history object in the annotation. Throw an error?
			}
		}
	}

	/* (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processEndElement(String elementName, String prefix,
			boolean isNested, Object contextObject)
	 */
	public void processEndElement(String elementName, String prefix,
			boolean isNested, Object contextObject) {
		
		// A DatesParser can only modify a contextObject which is a ModelHistory instance.
		if (contextObject instanceof ModelHistory){
			// When a 'created' or 'modified' node is closed, the previousElement of this parser is set
			// to "", the boolean hasReadW3CDTF, hasReadCreated and hasReadModified are set to false.
			if (elementName.equals("created") || elementName.equals("modified")){
				this.previousElement = "";
				hasReadW3CDTF = false;
				if (elementName.equals("created")){
					hasReadCreated = false;
				}
				if (elementName.equals("modified")){
					hasReadModified = false;
				}
			}
			else {
				// TODO : the date instances are only created for the created and/or modified nodes in the annotation. Throw an error?
			}
		}
		else {
			// TODO : the date instances are only created for the model history object in the annotation. Throw an error?
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

		// When this parser read a starting element tag, it can modify a contextObject
		// which is an Annotation instance.
		if (contextObject instanceof Annotation){
			Annotation modelAnnotation = (Annotation) contextObject;

			// This parser doesn't have to create a ModelHistory instance(in the SBML specifications, the created and modified dates
			// should come after the creator list and so, the modelHistory of the annotation should not be null for this parser).
			if (modelAnnotation.isSetModelHistory()){
				ModelHistory modelHistory = modelAnnotation.getModelHistory();

				// If the localName of the node is 'created' and if it has not been read yet,
				// the previousElement of this parser is set to 'created' and hasReadCreated is set
				// to true. This element should not have attributes or namespace declarations.
				// The modelHistory of annotation is not changed but is returned.
				if (elementName.equals("created") && !hasReadCreated && !hasNamespaces && !hasAttributes){
					hasReadCreated = true;
					this.previousElement = elementName;
					
					return modelHistory;
				}
				// If the localName of the node is 'modified' and if it has not been read yet,
				// the previousElement of this parser is set to 'modified' and hasReadModified is set
				// to true. This element should not have attributes or namespace declarations.
				// The modelHistory of annotation is not changed but is returned.
				else if (elementName.equals("modified") && !hasReadModified && !hasNamespaces && !hasAttributes){
					this.previousElement = elementName;
					hasReadModified = true;
					return modelHistory;
				}
				else {
					// TODO : SBML syntax error, what to do?
				}
			}
			else {
				// TODO : create a modelHistory instance? throw an exception?
			}
		}
		// When this parser read a starting element tag, it can modify a contextObject
		// which is a ModelHistory instance.
		else if (contextObject instanceof ModelHistory){
			
			// If the node is a 'W3CDTF' subElement of a 'created' or 'modified' element, the boolean hasReadW3CDTF
			// of this node is set to true. This element should not have attributes or namespace declarations.
			if (elementName.equals("W3CDTF") && (previousElement.equals("created") || previousElement.equals("modified")) && !hasNamespaces && !hasAttributes){
				hasReadW3CDTF = true;
			}
		}
		else {
			// TODO : should be changed depending on the version. Now, there is not only the model which contain a model history. 
		}
		return contextObject;
	}

	/* (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processEndDocument(SBMLDocument sbmlDocument)
	 */
	public void processEndDocument(SBMLDocument sbmlDocument) {
		previousElement = "";
		hasReadCreated = false;
		hasReadModified = false;
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
		// The namespace of this parser should be declared in a 'RDF' subNode of an annotation.
		// Sets the namespace to the RDFAnnotationNamespaces HashMap of annotation.
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
		// TODO : There is no attributes with the namespace "http://purl.org/dc/terms/". There is a SBML
		// syntax error, throw an exception?
	}

	/**
	 * @return the namespaceURI
	 */
	public static String getNamespaceURI() {
		return namespaceURI;
	}
}
