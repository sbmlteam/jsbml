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
 * 
 * Represents the rateRule XML element of a SBML file.
 * 
 * @author Andreas Dr&auml;ger
 * @author marine
 * 
 * @opt attributes
 * @opt types
 * @opt visibility
 */
public class RateRule extends ExplicitRule {

	/**
	 * Creates a RateRule instance. By default, the variableID is null.
	 */
	public RateRule() {
		super();
	}

	/**
	 * Creates a RateRule instance from a given RateRule.
	 * 
	 * @param sb
	 */
	public RateRule(int level, int version) {
		super(level, version);
	}

	/**
	 * @param sb
	 */
	public RateRule(RateRule sb) {
		super(sb);
	}

	/**
	 * Creates a RateRule instance from a given Symbol. Takes level and version
	 * from the variable.
	 * 
	 * @param variable
	 */
	public RateRule(Variable variable) {
		super(variable);
	}

	/**
	 * Creates a RateRule instance from a given Symbol and ASTNode. Takes level
	 * and version from the variable.
	 * 
	 * @param variable
	 * @param math
	 */
	public RateRule(Variable variable, ASTNode math) {
		super(math, variable);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.MathContainer#clone()
	 */
	@Override
	public RateRule clone() {
		return new RateRule(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		return (o instanceof RateRule) ? super.equals(o) : false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.ExplicitRule#isScalar()
	 */
	@Override
	public boolean isScalar() {
		return false;
	}
}
