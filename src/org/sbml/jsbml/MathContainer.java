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
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.sbml.jsbml;

/**
 * @author Andreas Dr&auml;ger <a
 *         href="mailto:andreas.draeger@uni-tuebingen.de">
 *         andreas.draeger@uni-tuebingen.de</a>
 * 
 */
public abstract class MathContainer extends AbstractSBase {

	/**
	 * 
	 */
	private ASTNode math;

	/**
	 * 
	 */
	public MathContainer(int level, int version) {
		super(level, version);
		math = null;
	}

	/**
	 * 
	 * @param math
	 * @param level
	 * @param version
	 */
	public MathContainer(ASTNode math, int level, int version) {
		super(level, version);
		setMath(math.clone());
	}

	/**
	 * @param sb
	 */
	public MathContainer(MathContainer sb) {
		super(sb);
		setMath(sb.getMath().clone());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBase#clone()
	 */
	// @Override
	public abstract MathContainer clone();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBase#equals(java.lang.Object)
	 */
	// @Override
	public boolean equals(Object o) {
		boolean equal = super.equals(o);
		if (o.getClass().getName().equals(this.getClass().getName())) {
			MathContainer c = (MathContainer) o;
			if ((c.isSetMath() && !isSetMath()) || (!c.isSetMath() && isSetMath()))
				return false;
			if (c.isSetMath() && isSetMath())
				equal &= getMath().equals(c.getMath());
			return equal;
		}
		return false;
	}

	/**
	 * 
	 * @return
	 */
	public String getFormula() {
		return isSetMath() ? getMath().toString() : "";
	}

	/**
	 * 
	 * @return
	 */
	public ASTNode getMath() {
		return math;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSetMath() {
		return math != null;
	}

	/**
	 * Sets the mathematical expression of this KineticLaw instance to the given
	 * formula.
	 * 
	 * @param formula
	 */
	public void setFormula(String formula) {
		math = ASTNode.parseFormula(formula);
		stateChanged();
	}

	/**
	 * 
	 * @param math
	 */
	public void setMath(ASTNode math) {
		this.math = math.clone();
		this.math.parentSBMLObject = this;
		stateChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBase#toString()
	 */
	// @Override
	public String toString() {
		return isSetMath() ? math.toString() : "";
	}
}
