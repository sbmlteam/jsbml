/*
 * $Id:  PropertyNotAvailableError.java 19:06:45 draeger $
 * $URL: PropertyNotAvailableError.java $
 *
 * 
 *==================================================================================
 * Copyright (c) 2009 The jsbml team.
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
package org.sbml.jsbml;

/**
 * An error that indicates that a property of an {@link SBase} is
 * not available for the current SBML Level/Version combination.
 * 
 * @author Andreas Dr&auml;ger
 * @date 2010-11-21
 */
public class PropertyNotAvailableError extends SBMLError {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = 3030431702957624218L;

	/**
	 * Message to indicate that a certain property cannot be set for the current level/version combination.
	 */
	public static final String PROPERTY_UNDEFINED_EXCEPTION_MSG = "Property %s is not defined for Level %s and Version %s";
	
	/**
	 * Creates an error message pointing out that the property of the given name is not defined
	 * in the Level/Version combination of the given {@link SBase}.
	 * 
	 * @param property
	 * @param sbase
	 * @return
	 */
	public static String propertyUndefinedMessage(String property, SBase sbase) {
		return String.format(PROPERTY_UNDEFINED_EXCEPTION_MSG, property,
				Integer.valueOf(sbase.getLevel()), Integer.valueOf(sbase.getVersion()));
	}
	
	/**
	 * 
	 * @param property
	 * @param sbase
	 */
	public PropertyNotAvailableError(String property, SBase sbase) {
		super(propertyUndefinedMessage(property, sbase));
	}
	
	/* (non-Javadoc)
	 * @see org.sbml.jsbml.SBMLError#toString()
	 */
	public String toString() {
		return getMessage();
	}
}
