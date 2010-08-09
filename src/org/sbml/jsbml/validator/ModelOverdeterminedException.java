/*
 * $Id: ModelOverdeterminedException.java 273 2010-06-10 13:17:41Z andreas-draeger $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/src/org/sbml/jsbml/validator/ModelOverdeterminedException.java $
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
package org.sbml.jsbml.validator;

/**
 * This class represents an exception that is thrown when the model to be
 * simulated is overdetermined
 * 
 * @author Alexander D&ouml;rr
 * @since 0.8
 */
public class ModelOverdeterminedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5288546434951201722L;

	/**
	 * 
	 */
	public ModelOverdeterminedException() {
		super();
	}

	/**
	 * @param message
	 */
	public ModelOverdeterminedException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ModelOverdeterminedException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param cause
	 */
	public ModelOverdeterminedException(Throwable cause) {
		super(cause);
	}
}
