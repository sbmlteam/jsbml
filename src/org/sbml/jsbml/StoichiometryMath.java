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
 * Contains the MathMl expression of the Stoichiometry.
 * 
 * @author Andreas Dr&auml;ger
 * 
 * @opt attributes
 * @opt types
 * @opt visibility
 */
public class StoichiometryMath extends MathContainer {

	/**
	 * Creates a StoichiometryMath instance.
	 */
	public StoichiometryMath() {
		super();
	}

	/**
	 * Creates a StoichiometryMath instance from a level and version.
	 * 
	 * @param level
	 * @param version
	 */
	public StoichiometryMath(int level, int version) {
		super(level, version);
	}

	/**
	 * Creates a StoichiometryMath instance from a given StoichiometryMath.
	 * 
	 * @param stoichiometryMath
	 */
	public StoichiometryMath(StoichiometryMath stoichiometryMath) {
		super(stoichiometryMath);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.MathContainer#clone()
	 */
	// @Override
	public StoichiometryMath clone() {
		return new StoichiometryMath(this);
	}
}
