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
 * The base class for the {@link AlgebraicRule}, {@link RateRule},
 * {@link AssignmentRule}.
 * 
 * @author Andreas Dr&auml;ger
 * 
 * @opt attributes
 * @opt types
 * @opt visibility
 */
public abstract class Rule extends AbstractMathContainer {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -8151628772496225902L;

	/**
	 * Creates a Rule instance.
	 */
	public Rule() {
		super();
	}

	/**
	 * Creates a Rule instance from an id, level and version.
	 * 
	 * @param math
	 * @param level
	 * @param version
	 */
	public Rule(ASTNode math, int level, int version) {
		super(math, level, version);
	}

	/**
	 * Creates a Rule instance from a level and version.
	 * 
	 * @param level
	 * @param version
	 */
	public Rule(int level, int version) {
		super(level, version);
	}

	/**
	 * Creates a new {@link Rule} instance from a given {@link Rule}.
	 * 
	 * @param sb
	 */
	public Rule(Rule sb) {
		super(sb);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractMathContainer#clone()
	 */
	@Override
	public abstract Rule clone();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.MathContainer#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		return (o instanceof Rule) ? super.equals(o) : false;
	}

	/**
	 * 
	 * @return true if this Rule is an AlgebraicRule instance.
	 */
	public boolean isAlgebraic() {
		return this instanceof AlgebraicRule;
	}

	/**
	 * 
	 * @return true if this Rule is an AssignmentRule instance.
	 */
	public boolean isAssignment() {
		return this instanceof AssignmentRule;
	}

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
	 * 
	 * @return true if this Rule is a RateRule instance.
	 */
	public boolean isRate() {
		return this instanceof RateRule;
	}

	/**
	 * (SBML Level 1 only) Predicate returning true or false depending on
	 * whether this Rule is an SpeciesConcentrationRule.
	 * 
	 * @return true if this Rule is a SpeciesConcentrationRule, false otherwise.
	 */
	public abstract boolean isSpeciesConcentration();

}
