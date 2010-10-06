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

import org.sbml.jsbml.validator.ModelOverdeterminedException;
import org.sbml.jsbml.validator.OverdeterminationValidator;

/**
 * Represents the algebraicRule XML element of a SBML file.
 * 
 * @author Andreas Dr&auml;ger
 * 
 * @opt attributes
 * @opt types
 * @opt visibility
 */
public class AlgebraicRule extends Rule {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -3952858495318158175L;

	/**
	 * Creates an AlgebraicRule instance.
	 */
	public AlgebraicRule() {
		super();
	}

	/**
	 * Creates an AlgebraicRule instance from a given {@link AlgebraicRule}
	 * instance.
	 * 
	 * @param ar
	 */
	public AlgebraicRule(AlgebraicRule ar) {
		super(ar);
	}

	/**
	 * Creates an AlgebraicRule instance from math, level and version.
	 * 
	 * @param math
	 * @param level
	 * @param version
	 */
	public AlgebraicRule(ASTNode math, int level, int version) {
		super(math, level, version);
	}

	/**
	 * Creates an AlgebraicRule instance from level and version.
	 * 
	 * @param level
	 * @param version
	 */
	public AlgebraicRule(int level, int version) {
		super(level, version);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Rule#clone()
	 */
	@Override
	public AlgebraicRule clone() {
		return new AlgebraicRule(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.Rule#isCompartmentVolume()
	 */
	@Override
	public boolean isCompartmentVolume() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.Rule#isParameter()
	 */
	@Override
	public boolean isParameter() {
		return false;
	}

	/**
	 * This method provides a convenient way to obtain the variable whose amount
	 * is determined by this rule. However, you should better directly use the
	 * {@link OverdeterminationValidator} instead of calling this method. Each
	 * time you call this method, the bipartite matching between all
	 * {@link MathContainer}s in the model and all
	 * {@link NamedSBaseWithDerivedUnit}s will be executed again, leading to a
	 * probably high computational effort.
	 * 
	 * @return The {@link Variable} determined by this {@link AlgebraicRule}
	 * @throws ModelOverdeterminedException
	 *             if the model containing this {@link Rule} is over determined.
	 * @throws NullPointerException
	 *             Calling this method requires that this {@link Rule} is
	 *             already part of a {@link Model}. If you just created a
	 *             {@link Rule} and didn't add it to a {@link Model} you'll
	 *             receive a {@link NullPointerException}.
	 */
	public Variable getDerivedVariable() throws ModelOverdeterminedException {
		OverdeterminationValidator odv = new OverdeterminationValidator(
				getModel());
		if (odv.isOverdetermined()) {
			throw new ModelOverdeterminedException();
		}
		return (Variable) odv.getMatching().get(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.Rule#isSpeciesConcentration()
	 */
	@Override
	public boolean isSpeciesConcentration() {
		return false;
	}
}
