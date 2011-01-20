/*
 * $Id$
 * $URL$
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
package org.sbml.jsbml.xml;

/**
 * This kind of {@link Error} indicates that an XML file could not be parsed
 * correctly.
 * 
 * @author Andreas Dr&auml;ger
 * @date 2010-10-30
 */
public class XMLError extends Error {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -7347204499480036729L;

	/**
	 * 
	 */
	public XMLError() {
		super();
	}

	
	/**
	 * @param message
	 */
	public XMLError(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public XMLError(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param cause
	 */
	public XMLError(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 * @return
	 */
	public int getErrorId() {
		return 0;
	}

}
