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

import java.util.List;

import org.sbml.jsbml.SBase;

/**
 * The interface to implement for a parser which writes a SBML file.
 * 
 * @author marine
 * 
 */
public interface WritingParser {

	/**
	 * Returns the list of children of the <code>objectToWrite</code>.
	 * 
	 * @param objectToWrite
	 *            : the {@link SBase} component to write.
	 * @return the list of components that '{@link SBase}' contains. Represents
	 *         the list of subNodes of this {@link SBase} component.
	 */
	public List<Object> getListOfSBMLElementsToWrite(Object objectToWrite);

	/**
	 * Adds the XML attributes of the <code>sbmlElementToWrite</code> to the attributes
	 * HashMap of the <code>xmlObject</code>.
	 * 
	 * @param xmlObject
	 *            : contains the XML information about sbmlElement.
	 * @param sbmlElementToWrite
	 *            : the {@link SBase} component to write
	 */
	public void writeAttributes(SBMLObjectForXML xmlObject,
			Object sbmlElementToWrite);

	/**
	 * Sets the characters of xmlObject depending on the sbml element to write.
	 * 
	 * @param xmlObject
	 *            : contains the XML information about sbmlElement.
	 * @param sbmlElementToWrite
	 *            : the {@link SBase} component to write
	 */
	public void writeCharacters(SBMLObjectForXML xmlObject,
			Object sbmlElementToWrite);

	/**
	 * Sets the name of xmlObject (if it is not set) to the element name of
	 * sbmlElementToWrite.
	 * 
	 * @param xmlObject
	 *            : contains the XML information about sbmlElement.
	 * @param sbmlElementToWrite
	 *            : the {@link SBase} component to write
	 * @param indent
	 */
	public void writeElement(SBMLObjectForXML xmlObject,
			Object sbmlElementToWrite);

	/**
	 * Sets the namespace of xmlObject (if it is not set) to the namespace of
	 * sbmlElementToWrite.
	 * 
	 * @param xmlObject
	 *            : contains the XML information about sbmlElement.
	 * @param sbmlElementToWrite
	 *            : the {@link SBase} component to write
	 */
	public void writeNamespaces(SBMLObjectForXML xmlObject,
			Object sbmlElementToWrite);
}
