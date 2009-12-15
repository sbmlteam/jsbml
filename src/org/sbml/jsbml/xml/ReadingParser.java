/*
 * $Id: ReadingParser.java 38 2009-12-14 15:50:38Z marine3 $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/src/org/sbml/jsbml/xml/ReadingParser.java $
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

package org.sbml.jsbml.xml;

import org.sbml.jsbml.element.SBMLDocument;

/**
 * The interface to implement for the SBML parsers reading SBML files.
 * @author marine
 *
 */
public interface ReadingParser {

	/**
	 * Process the XML element and modify 'contextObject' in consequence.
	 * @param elementName : the localName of the XML element to process
	 * @param prefix : the prefix of the XML element to process
	 * @param hasAttributes : boolean value to know if this XML element has attributes.
	 * @param hasNamespaces : boolean value to know if this XML element contains namespace declarations.
	 * @param contextObject : the object to set or modify depending on the identity of the current XML element. This object represents the context of the XML element in the SBMLDocument.
	 * @return a new contextObject which represents the environment of the next node/subnode in the SBMLDocument.
	 * 
	 * Ex : if the contextObject is an instance of Event and the elementName is 'trigger', this method will create a new Trigger instance
	 * and will set the trigger instance of the 'contextObject' to the new Trigger. Then the method will return the new Trigger
	 * instance which is the new environment.
	 */
	public Object processStartElement(String elementName, String prefix, boolean hasAttributes, boolean hasNamespaces, Object contextObject);
	
	/**
	 * Process the XML attribute and modify 'contextObject' in consequence.
	 * @param elementName : the localName of the XML element.
	 * @param attributeName : the attribute localName of the XML element.
	 * @param value : the value of the XML attribute.
	 * @param prefix : the attribute prefix 
	 * @param isLastAttribute : boolean value to know if this attribute is the last attribute of the XML element.
	 * @param contextObject : the object to set or modify depending on the identity of the current attribute. This object represents the context of the XML attribute in the SBMLDocument.
	 * 
	 * Ex : if the contextObject is an instance of Reaction and the attributeName is 'fast', this method will set the 'fast' variable of the 'contextObject' to 'value'.
	 * Then it will return the modified Reaction instanc.
	 */
	public void processAttribute(String elementName, String attributeName, String value, String prefix, boolean isLastAttribute, Object contextObject);

	/**
	 * Process the text of a XML element and modify 'contextObject' in consequence.
	 * @param elementName : the localName of the XML element.
	 * @param characters : the text of this XML element.
	 * @param contextObject : the object to set or modify depending on the identity of the current element. This object represents the context of the XML element in the SBMLDocument.
	 * 
	 * Ex : if the contextObject is an instance of ModelCreator and the elementName is 'Family',
	 * this method will set the familyName of the 'contextObject' to the text value. Then it will return the changed ModelCreator instance.
	 */
	public void processCharactersOf(String elementName, String characters, Object contextObject);
	
	/**
	 * Process the end of the element 'elementName'. Modify or not the contextObject.
	 * @param elementName : the localName of the XML element.
	 * @param prefix : the prefix of the XML element.
	 * @param isNested : boolean value to know if the XML element is a nested element.
	 * @param contextObject : the object to set or modify depending on the identity of the current element. This object represents the context of the XML element in the SBMLDocument.
	 * 
	 */
	public void processEndElement(String elementName, String prefix, boolean isNested, Object contextObject);
	
	/**
	 * Process the end of the document. Do the necessary changes in the SBMLDocument.
	 * @param sbmlDocument : the final initialised SBMLDocument instance.
	 * 
	 * Ex : check if all the annotations are valid, etc.
	 */
	public void processEndDocument(SBMLDocument sbmlDocument);
	
	/**
	 * Process the namespace and modify the contextObject in consequence.
	 * @param elementName : the localName of the XML element.
	 * @param URI : the URI of the namepace
	 * @param prefix : the prefix of the namesapce.
	 * @param localName : the localName of the namespace.
	 * @param hasAttributes : boolean value to know if there are attributes after the namespace declarations.
	 * @param isLastNamespace : boolean value to know if this namespace is the last namespace of this element.
	 * @param contextObject : the object to set or modify depending on the identity of the current element. This object represents the context of the XML element in the SBMLDocument.
	 * 
	 * Ex : if the contextObject is an instance of SBMLDocument, the namespaces will be stored in the SBMLNamespaces HashMap of this SBMLDocument.
	 */
	public void processNamespace(String elementName, String URI, String prefix, String localName, boolean hasAttributes, boolean isLastNamespace, Object contextObject);
}
