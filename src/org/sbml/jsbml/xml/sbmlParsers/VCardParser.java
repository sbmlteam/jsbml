/*
 * $Id: VCardParser.java 38 2009-12-11 15:50:38Z marine3 $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/src/org/sbml/jsbml/xml/sbmlParsers/VCardParser.java $
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

import org.sbml.jsbml.element.Annotation;
import org.sbml.jsbml.element.ModelCreator;
import org.sbml.jsbml.element.SBMLDocument;
import org.sbml.jsbml.xml.ReadingParser;

/**
 * A VCardParser is used to parser the subNodes of an annotation which have this namespace URI :
 * "http://www.w3.org/2001/vcard-rdf/3.0#".
 * @author marine
 *
 */
public class VCardParser implements ReadingParser{

	/**
	 * The namespace URI of this ReadindParser.
	 */
	private static final String namespaceURI = "http://www.w3.org/2001/vcard-rdf/3.0#";
	
	/**
	 * Boolean value to know if the 'N' node has been read.
	 */
	private boolean hasReadNNode = false;
	/**
	 * Boolean value to know if the 'Family' node has been read.
	 */
	private boolean hasReadFamilyName = false;
	/**
	 * Boolean value to know if the 'Given' node has been read.
	 */
	private boolean hasReadGivenName = false;
	/**
	 * Boolean value to know if the 'Orgname' node has been read.
	 */
	private boolean hasReadOrgName = false;
	/**
	 * Boolean value to know if the 'EMAIL' node has been read.
	 */
	private boolean hasReadEMAIL = false;
	/**
	 * Boolean value to know if the 'ORG' node has been read.
	 */
	private boolean hasReadORGNode = false;
	
	/* (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processAttribute(String elementName, String attributeName,
			String value, String prefix, boolean isLastAttribute, Object contextObject)
	 */
	public void processAttribute(String elementName, String attributeName,
			String value, String prefix, boolean isLastAttribute, Object contextObject) {
		// TODO : There is no attribute with a namespace "http://www.w3.org/2001/vcard-rdf/3.0#", SBML syntax error.
		// Throw an exception?
	}

	/* (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processCharactersOf(String elementName, String characters,
			Object contextObject)
	 */
	public void processCharactersOf(String elementName, String characters,
			Object contextObject) {
		
		// An elementName can be null if the text appears after a ending element tag.
		if (elementName != null){
			// A VCardParser can only modify a contextObject which is a ModelCreator instance.
			if (contextObject instanceof ModelCreator){
				ModelCreator modelCreator = (ModelCreator) contextObject;
				
				// Sets the familyName String of modelCreator.
				if (elementName.equals("Family") && hasReadFamilyName){
					modelCreator.setFamilyName(characters);
				}
				// Sets the givenName String of modelCreator.
				else if (elementName.equals("Given") && hasReadGivenName){
					modelCreator.setGivenName(characters);
				}
				// Sets the email String of modelCreator.
				else if (elementName.equals("EMAIL") && hasReadEMAIL){
					modelCreator.setEmail(characters);
				}
				// Sets the orgname String of modelCreator.
				else if (elementName.equals("Orgname") && hasReadOrgName){
					modelCreator.setOrganisation(characters);
				}
				else {
					// TODO : SBML syntax error, throw an exception?
				}
			}
			else {
				// TODO : SBML syntax error, throw an exception?
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
		
		// A VCardParser can only modify a contextObject which is a ModelCreator instance.
		if (contextObject instanceof ModelCreator){
			// End of a 'N' node, sets hasReadNNode, hasReadFamilyName and hasReadGivenName to false.
			if (elementName.equals("N")){
				hasReadNNode = false;
				hasReadFamilyName = false;
				hasReadGivenName = false;
			}
			// End of a 'EMAIL' node, sets hasReadEMAIL to false.
			else if (elementName.equals("EMAIL")){
				hasReadEMAIL = false;
			}
			// End of a 'ORG' node, sets hasReadORGNode, hasReadOrgName.
			else if (elementName.equals("ORG")){
				hasReadORGNode = false;
				hasReadOrgName = false;

			}
			else {
				// TODO : SBML syntax error, throw an exception?
			}
		}
		else {
			// TODO : SBML syntax error, throw an exception?
		}
	}

	/* (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processStartElement(String elementName, String prefix, boolean hasAttribute, boolean hasNamespaces, Object contextObject)
	 */
	public Object processStartElement(String elementName, String prefix, boolean hasAttribute, boolean hasNamespaces, Object contextObject) {
		
		// A VCardParser can only modify a contextObject which is a ModelCreator instance.
		if (contextObject instanceof ModelCreator){
			// Reads the 'N' node.
			if (elementName.equals("N") && !hasReadNNode){
				hasReadNNode = true;
			}
			// Reads the 'Family' node.
			else if (elementName.equals("Family") && hasReadNNode && !hasReadFamilyName && !hasReadGivenName){
				hasReadFamilyName = true;
			}
			// Reads the 'Given' node.
			else if (elementName.equals("Given") && hasReadNNode && hasReadFamilyName && !hasReadGivenName){
				hasReadGivenName = true;
			}
			// Reads the 'EMAIL' node.
			else if (elementName.equals("EMAIL") && !hasReadEMAIL){
				hasReadEMAIL = true;
			}
			// Reads the 'ORG' node.
			else if (elementName.equals("ORG") && !hasReadORGNode){
				hasReadORGNode = true;
			}
			// Reads the 'Orgname' node.
			else if (elementName.equals("Orgname") && hasReadORGNode && !hasReadOrgName){
				hasReadOrgName = true;
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

	/* (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processEndDocument(SBMLDocument sbmlDocument)
	 */
	public void processEndDocument(SBMLDocument sbmlDocument) {
		hasReadEMAIL = false;
		hasReadFamilyName = false;
		hasReadGivenName = false;
		hasReadNNode = false;
		hasReadORGNode = false;
		hasReadOrgName = false;
	}

	/* (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processNamespace(String elementName, String URI, String prefix,
			String localName, boolean hasAttributes, boolean isLastNamespace, Object contextObject)
	 */
	public void processNamespace(String elementName, String URI, String prefix,
			String localName, boolean hasAttributes, boolean isLastNamespace, Object contextObject) {
		
		// The namespace of this parser should be declared in a 'RDF' subnode of annotation.
		// Adds the namespace to RDFAnnotationNamespaces HashMap of annotation.
		if (elementName.equals("RDF") && contextObject instanceof Annotation){
			Annotation annotation = (Annotation) contextObject;
			
			annotation.addRDFAnnotationNamespace(localName, prefix, URI);
		}
	}

	/**
	 * @return the namespaceURI
	 */
	public static String getNamespaceURI() {
		return namespaceURI;
	}
}
