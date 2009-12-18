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

import java.util.HashMap;

/**
 * Base class for all the SBML components which contain MathML nodes.
 * 
 * @author Andreas Dr&auml;ger
 * @author marine
 */
public abstract class MathContainer extends AbstractSBase {

	/**
	 * The math formula as a Tree.
	 */
	private ASTNode math;
	/**
	 * The MathMl subnodes as a StringBuffer.
	 */
	private StringBuffer mathBuffer;
	/**
	 * Represents the 'formula' XML attribute of this object.
	 */
	@Deprecated
	private String formula;

	/**
	 * Creates a MathContainer instance. By default, the formula, math and
	 * mathBuffer are null.
	 */
	public MathContainer() {
		super();
		math = null;
		this.mathBuffer = null;
		this.formula = null;
	}

	/**
	 * Creates a MathContainer instance from a level and version. By default,
	 * the formula, math and mathBuffer are null.
	 * 
	 * @param level
	 * @param version
	 */
	public MathContainer(int level, int version) {
		super(level, version);
		math = null;
		this.mathBuffer = null;
		this.formula = null;
	}

	/**
	 * Creates a MathContainer instance from an ASTNode, level and version. By
	 * default, the formula and mathBuffer are null.
	 * 
	 * @param math
	 * @param level
	 * @param version
	 */
	public MathContainer(ASTNode math, int level, int version) {
		super(level, version);
		if (math != null) {
			setMath(math.clone());
		} else {
			this.math = null;
		}
		this.formula = null;
		this.mathBuffer = null;
	}

	/**
	 * Creates a MathContainer instance from a given MathContainer.
	 * 
	 * @param sb
	 */
	public MathContainer(MathContainer sb) {
		super(sb);
		if (sb.isSetMath()) {
			setMath(sb.getMath().clone());
		} else {
			this.math = null;
		}
		if (sb.isSetMathBuffer()) {
			this.mathBuffer = new StringBuffer(sb.getMathBufferToString());
		} else {
			this.mathBuffer = null;
		}
		if (sb.isSetFormulaString()) {
			this.formula = new String(sb.getFormulaString());
		} else {
			this.formula = null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#clone()
	 */
	// @Override
	public abstract MathContainer clone();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#equals(java.lang.Object)
	 */
	// @Override
	public boolean equals(Object o) {
		boolean equal = super.equals(o);
		if (o.getClass().getName().equals(this.getClass().getName())) {
			MathContainer c = (MathContainer) o;
			if ((c.isSetMath() && !isSetMath())
					|| (!c.isSetMath() && isSetMath())) {
				return false;
			}
			if (c.isSetMath() && isSetMath()) {
				equal &= getMath().equals(c.getMath());
			}
			if ((c.isSetMathBuffer() && !isSetMathBuffer())
					|| (!c.isSetMathBuffer() && isSetMathBuffer())) {
				return false;
			}
			if (c.isSetMathBuffer() && isSetMathBuffer()) {
				equal &= getMathBuffer().equals(c.getMathBuffer());
			}
			if ((c.isSetFormulaString() && !isSetFormulaString())
					|| (!c.isSetFormulaString() && isSetFormulaString())) {
				return false;
			}
			if (c.isSetFormulaString() && isSetFormulaString()) {
				equal &= getFormulaString().equals(c.getFormulaString());
			}
			return equal;
		}
		return false;
	}

	/**
	 * 
	 * @return the math ASTNode of this object as a String. It returns the empty
	 *         String if the math ASTNode is not set.
	 */
	public String getFormula() {
		return isSetMath() ? getMath().toFormula() : "";
	}

	/**
	 * 
	 * @return the math ASTNode of this object. It return null if the math
	 *         ASTNode is not set.
	 */
	public ASTNode getMath() {
		return math;
	}

	/**
	 * 
	 * @return true if the math ASTNode of this object is not null.
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
	 * Sets the math ASTNode of this object to 'math'.
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
	 * @see org.sbml.jsbml.element.SBase#toString()
	 */
	// @Override
	public String toString() {
		return isSetMath() ? math.toString() : "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#readAttribute(String attributeName,
	 * String prefix, String value)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix,
			String value) {
		boolean isAttributeRead = super.readAttribute(attributeName, prefix,
				value);

		if (!isAttributeRead) {
			if (attributeName.equals("formula") && isSetLevel()
					&& getLevel() < 2) {
				setFormulaString(value);
			}
		}
		return isAttributeRead;
	}

	/**
	 * Sets the mathBuffer of this object to 'mathBuffer'.
	 * 
	 * @param mathBuffer
	 */
	public void setMathBuffer(StringBuffer mathBuffer) {
		this.mathBuffer = mathBuffer;
		stateChanged();
	}

	/**
	 * 
	 * @return the mathBuffer of this object. Null if it is not set.
	 */
	public StringBuffer getMathBuffer() {
		return mathBuffer;
	}

	/**
	 * 
	 * @return true if the mathBuffer of this object is not null.
	 */
	public boolean isSetMathBuffer() {
		return this.mathBuffer != null;
	}

	/**
	 * 
	 * @return the mathBuffer of this object as a String.
	 */
	public String getMathBufferToString() {
		if (isSetMathBuffer()) {
			return mathBuffer.toString();
		}
		return "";
	}

	/**
	 * 
	 * @return the formula of this object. If the formula is not set, it returns
	 *         the empty String.
	 */
	public String getFormulaString() {
		return isSetFormulaString() ? formula : "";
	}

	/**
	 * Sets the formula of this object to 'formula'.
	 * 
	 * @param formula
	 */
	@Deprecated
	public void setFormulaString(String formula) {
		this.formula = formula;
		stateChanged();
	}

	/**
	 * 
	 * @return true if the formula of this Object is not null.
	 */
	@Deprecated
	public boolean isSetFormulaString() {
		return this.formula != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#writeXMLAttributes()
	 */
	@Override
	public HashMap<String, String> writeXMLAttributes() {
		HashMap<String, String> attributes = super.writeXMLAttributes();

		if (isSetFormulaString() && isSetLevel() && getLevel() < 2) {
			attributes.put("formula", getFormulaString());
		}
		return attributes;
	}
}
