/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2013 jointly by the following organizations:
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

import java.util.Date;

import org.apache.log4j.Logger;
import org.sbml.jsbml.Annotation;
import org.sbml.jsbml.History;
import org.sbml.jsbml.JSBML;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.xml.stax.DateProcessor;
import org.w3c.util.DateParser;
import org.w3c.util.InvalidDateException;

/**
 * A DatesParser is used to parse the subNodes of an annotation which have this
 * namespace URI : http://purl.org/dc/terms/.
 * 
 * @author Marine Dumousseau
 * @author Nicolas Rodriguez
 * @since 0.8
 * @version $Rev$
 */
public class DatesParser implements ReadingParser {

	/**
	 * Stores the localName of the last element read by this parser.
	 */
	private String previousElement = "";
	/**
	 * Boolean value to know if a 'created' element has been read by this
	 * parser.
	 */
	private boolean hasReadCreated = false;
	/**
	 * Boolean value to know if a 'W3CDTF' element has been read by this parser.
	 */
	boolean hasReadW3CDTF = false;

	/**
	 * Boolean value to know if a 'modified' element has been read by this
	 * parser.
	 */
	boolean hasReadModified = false;

	private Logger logger = Logger.getLogger(DatesParser.class);
	
	/**
	 * @return the namespaceURI
	 */
	public static String getNamespaceURI() {
		return JSBML.URI_PURL_TERMS;
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
			Object contextObject) 
	{
		// does nothing
		logger.debug("processCharactersOf : attribute " + AttributeName + " ignored.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processCharactersOf(String
	 * elementName, String characters, Object contextObject)
	 */
	public void processCharactersOf(String elementName, String characters,
			Object contextObject) {
		
		logger.debug("processCharactersOf : " + characters + ", element name = " + elementName);
		
		// An elementName can be null if the text appears after an element
		// ending tag. In this case,
		// this parser will not parse the text.
		if (elementName != null) {

			// A DatesParser can only modify a contextObject which is a
			// ModelHistory instance.
			if (contextObject instanceof History) {
				History modelHistory = (History) contextObject;
				DateProcessor dateProcessor = new DateProcessor();

				// The date to parse is the text of a 'W3CDTF' element.
				// However, the date will be parsed only if the syntax of this
				// node and the previous
				// node respects the SBML specifications.
				if (elementName.equals("W3CDTF") && hasReadW3CDTF) {

					// If the previous node was a 'created' element and
					// respected the SBML specifications,
					// a new Date will be created and set to the text value of
					// this node.
					// Sets the created Date of modelHistory.
					if (hasReadCreated && previousElement.equals("created")) {
						String stringDate = dateProcessor.formatToW3CDTF(characters);
						
						try {
							Date createdDate = DateParser.parse(stringDate);
							modelHistory.setCreatedDate(createdDate);

							logger.debug("processCharactersOf : getIsoDateNoMillis " + DateParser.getIsoDateNoMillis(createdDate));
							
						} catch (InvalidDateException e) {
							logger.warn("Cannot read the following date properly :" + stringDate);
							if (logger.isDebugEnabled()) {
								e.printStackTrace();
							}
						}
						
					}
					// If the previous node was a 'modified' element and
					// respected the SBML specifications,
					// a new Date will be created and set to the text value of
					// this node.
					// Sets the modified Date and adds the new Date to the
					// listOfModifications of modelHistory.
					else if (previousElement.equals("modified")) {
						String stringDate = dateProcessor.formatToW3CDTF(characters);

						try {
							Date modifiedDate = DateParser.parse(stringDate);
							modelHistory.setModifiedDate(modifiedDate);
						} catch (InvalidDateException e) {
							logger.warn("Cannot read the following date properly :" + stringDate);
							if (logger.isDebugEnabled()) {
								e.printStackTrace();
							}
						}
					} else {
						logger.debug("processCharactersOf : previousElement is not created or modified !!!");
					}
				} else {
					logger.debug("processCharactersOf : context object is not a W3CDTF element !! ");
				}
			} else {
				logger.debug("processCharactersOf : context object is not an History !! -> "
						+ contextObject.getClass().getName());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processEndDocument(SBMLDocument
	 * sbmlDocument)
	 */
	public void processEndDocument(SBMLDocument sbmlDocument) {
		previousElement = "";
		hasReadCreated = false;
		hasReadModified = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processEndElement(String
	 * elementName, String prefix, boolean isNested, Object contextObject)
	 */
	public boolean processEndElement(String elementName, String prefix,
			boolean isNested, Object contextObject) {

		logger.debug("processEndElement : " + elementName);
		
		// A DatesParser can only modify a contextObject which is a ModelHistory
		// instance.
		if (contextObject instanceof History) {
			// When a 'created' or 'modified' node is closed, the
			// previousElement of this parser is set
			// to "", the boolean hasReadW3CDTF, hasReadCreated and
			// hasReadModified are set to false.
			if (elementName.equals("created") || elementName.equals("modified")) {
				this.previousElement = "";
				hasReadW3CDTF = false;
				if (elementName.equals("created")) {
					hasReadCreated = false;
				}
				if (elementName.equals("modified")) {
					hasReadModified = false;
				}
			} else if (!elementName.equals("W3CDTF")) {
				logger.debug("Found an element other than 'created', 'modified' or 'W3CDTF', " +
						"do not know what to do with '" + elementName + "'");
			}
		} else {
			logger.debug("Do not know what to do with the element '" + elementName + "'.");
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
		// The namespace of this parser should be declared in a 'RDF' subNode of
		// an annotation.
		// Sets the namespace to the RDFAnnotationNamespaces HashMap of
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

		logger.debug("processStartElement : " + elementName);
		
		// When this parser read a starting element tag, it can modify a
		// contextObject which is an Annotation instance.
		if (contextObject instanceof Annotation) {
			Annotation annotation = (Annotation) contextObject;

			if (!annotation.isSetHistory()) {
				annotation.setHistory(new History());
			}
			
			if (annotation.isSetHistory()) {
				History history = annotation.getHistory();

				// If the localName of the node is 'created' and if it has not
				// been read yet,
				// the previousElement of this parser is set to 'created' and
				// hasReadCreated is set
				// to true.
				// The modelHistory of annotation is not changed but is
				// returned.
				if (elementName.equals("created") && !hasReadCreated) {
					hasReadCreated = true;
					this.previousElement = elementName;

					return history;
				}
				// If the localName of the node is 'modified' and if it has not
				// been read yet,
				// the previousElement of this parser is set to 'modified' and
				// hasReadModified is set
				// to true.
				// The modelHistory of annotation is not changed but is
				// returned.
				else if (elementName.equals("modified") && !hasReadModified) {
					this.previousElement = elementName;
					hasReadModified = true;
					return history;
				} else {
					logger.debug("processStartElement : element unknown !!");
				}
			} else {
				logger.debug("processStartElement : history not set yet !!");
			}
		}
		// When this parser read a starting element tag, it can modify a
		// contextObject
		// which is a ModelHistory instance.
		else if (contextObject instanceof History) {

			// If the node is a 'W3CDTF' subElement of a 'created' or 'modified'
			// element, the boolean hasReadW3CDTF
			// of this node is set to true.
			if (elementName.equals("W3CDTF")
					&& (previousElement.equals("created") || previousElement
							.equals("modified"))) {
				hasReadW3CDTF = true;
			}
		} 

		return contextObject;
	}
}
