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

package org.sbml.jsbml.xml.stax;

import java.util.ArrayList;

/**
 * The interface to implement for a parser which writes a SBML file.
 * @author marine
 *
 */
public interface WritingParser {

	/**
	 * 
	 * @param objectToWrite : the sbase component to write.
	 * @return the list of components that 'sbase' contains. Represents the list of subNodes of this sbase component. 
	 */
	public ArrayList<Object> getListOfSBMLElementsToWrite(Object objectToWrite);
	
	/**
	 * Sets the name of xmlObject (if it is not set) to the element name of sbmlElementToWrite.
	 * @param xmlObject : contains the XML information about sbmlElement.
	 * @param sbmlElementToWrite : the sbase component to write
	 */
	public void writeElement(SBMLObjectForXML xmlObject, Object sbmlElementToWrite); 
	
	/**
	 * Sets the namespace of xmlObject (if it is not set) to the namespace of sbmlElementToWrite.
	 * @param xmlObject : contains the XML information about sbmlElement.
	 * @param sbmlElementToWrite: the sbase component to write
	 */
	public void writeNamespaces(SBMLObjectForXML xmlObject, Object sbmlElementToWrite);
	
	/**
	 * Adds the XML attributes of of sbmlElementToWrite to the attributes HashMap of the xmlObject.
	 * @param xmlObject : contains the XML information about sbmlElement.
	 * @param sbmlElementToWrite : the sbase component to write
	 */
	public void writeAttributes(SBMLObjectForXML xmlObject, Object sbmlElementToWrite);
	
	/**
	 * Sets the characters of xmlObject depending on the sbml element to write.
	 * @param xmlObject : contains the XML information about sbmlElement.
	 * @param sbmlElementToWrite : the sbase component to write
	 */
	public void writeCharacters(SBMLObjectForXML xmlObject, Object sbmlElementToWrite);
}
