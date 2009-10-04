/*
 *  SBMLsqueezer creates rate equations for reactions in SBML files
 *  (http://sbml.org).
 *  Copyright (C) 2009 ZBIT, University of Tübingen, Andreas Dräger
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.sbml.jsbml;

/**
 * @author Andreas Dr&auml;ger <a
 *         href="mailto:andreas.draeger@uni-tuebingen.de">
 *         andreas.draeger@uni-tuebingen.de</a>
 * 
 */
public class AlgebraicRule extends Rule {

	/**
	 * 
	 * @param level
	 * @param version
	 */
	public AlgebraicRule(int level, int version) {
		super(level, version);
	}

	/**
	 * 
	 * @param math
	 * @param level
	 * @param version
	 */
	public AlgebraicRule(ASTNode math, int level, int version) {
		super(math, level, version);
	}

	/**
	 * @param sb
	 */
	public AlgebraicRule(MathContainer sb) {
		super(sb);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.MathContainer#clone()
	 */
	// @Override
	public AlgebraicRule clone() {
		return new AlgebraicRule(this);
	}

}
