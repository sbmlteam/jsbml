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
package org.sbml.jsbml;


/**
 * Represents the trigger XML element of a SBML file.
 * 
 * @author Andreas Dr&auml;ger
 * 
 * @opt attributes
 * @opt types
 * @opt visibility
 */
public class Trigger extends MathContainer {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -6964409168614117235L;

	/**
	 * Creates a Trigger instance.
	 */
	public Trigger() {
		super();
	}

	/**
	 * Creates a Trigger instance from a level and version.
	 * 
	 * @param level
	 * @param version
	 */
	public Trigger(int level, int version) {
		super(level, version);
	}

	/**
	 * Creates a Trigger instance from a given Trigger.
	 * 
	 * @param trigger
	 */
	public Trigger(Trigger trigger) {
		super(trigger);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.MathContainer#clone()
	 */
	@Override
	public Trigger clone() {
		return new Trigger(this);
	}
}
