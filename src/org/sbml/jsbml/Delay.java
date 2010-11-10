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

package org.sbml.jsbml;


/**
 * Represents the delay subnode of an event element.
 * 
 * @author Andreas Dr&auml;ger
 * 
 * @opt attributes
 * @opt types
 * @opt visibility
 */
public class Delay extends AbstractMathContainer {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -1578051749680028593L;

	/**
	 * Creates a Delay instance.
	 */
	public Delay() {
		super();
	}

	/**
	 * Creates a Delay instance from an ASTNode, level and version.
	 * 
	 * @param math
	 */
	public Delay(ASTNode math, int level, int version) {
		super(math, level, version);
	}

	/**
	 * Creates a Delay instance from a given Delay.
	 * 
	 * @param sb
	 */
	public Delay(Delay sb) {
		super(sb);
	}

	/**
	 * Creates a Delay instance from a level and version.
	 * 
	 * @param level
	 * @param version
	 */
	public Delay(int level, int version) {
		super(level, version);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractMathContainer#clone()
	 */
	public Delay clone() {
		return new Delay(this);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getParent()
	 */
	@Override
	public Event getParent() {
		return (Event) super.getParent();
	}
}
