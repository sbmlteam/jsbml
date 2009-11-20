/*
 * $Id: Rule.java 38 2009-11-05 15:50:38Z niko-rodrigue $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/src/org/sbml/jsbml/Rule.java $
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

package org.sbml.jsbml.element;

/**
 * @author Andreas Dr&auml;ger <a
 *         href="mailto:andreas.draeger@uni-tuebingen.de">
 *         andreas.draeger@uni-tuebingen.de</a>
 * 
 */
public abstract class Rule extends MathContainer {

	/**
	 * 
	 */
	public Rule() {
		super();
	}
	
	/**
	 * 
	 * @param level
	 * @param version
	 */
	public Rule(int level, int version) {
		super(level, version);
	}

	/**
	 * 
	 * @param math
	 * @param level
	 * @param version
	 */
	public Rule(ASTNode math, int level, int version) {
		super(math, level, version);
	}

	/**
	 * @param sb
	 */
	public Rule(MathContainer sb) {
		super(sb);
	}

	/**
	 * 
	 * @return
	 */
	public boolean isAlgebraic() {
		return this instanceof AlgebraicRule;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isAssignment() {
		return this instanceof AssignmentRule;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isRate() {
		return this instanceof RateRule;
	}

	/**
	 * Predicate returning true or false depending on whether this Rule is an
	 * AssignmentRule (SBML Level 2) or has a 'type' attribute value of 'scalar'
	 * (SBML Level 1).
	 * 
	 * @return true if this Rule is an AssignmentRule (Level 2) or has type
	 *         'scalar' (Level 1), false otherwise.
	 */
	public abstract boolean isScalar();

	/**
	 * (SBML Level 1 only) Predicate returning true or false depending on
	 * whether this Rule is an CompartmentVolumeRule.
	 * 
	 * @return true if this Rule is a CompartmentVolumeRule, false otherwise.
	 */
	public abstract boolean isCompartmentVolume();

	/**
	 * (SBML Level 1 only) Predicate returning true or false depending on
	 * whether this Rule is an ParameterRule.
	 * 
	 * @return true if this Rule is a ParameterRule, false otherwise.
	 */
	public abstract boolean isParameter();

	/**
	 * (SBML Level 1 only) Predicate returning true or false depending on
	 * whether this Rule is an SpeciesConcentrationRule.
	 * 
	 * @return true if this Rule is a SpeciesConcentrationRule, false otherwise.
	 */
	public abstract boolean isSpeciesConcentration();

}
