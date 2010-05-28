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

import javax.swing.tree.TreeNode;

/**
 * Base class for all the SBML components which contain MathML nodes.
 * 
 * @author Andreas Dr&auml;ger
 * @author marine
 * 
 * @opt attributes
 * @opt types
 * @opt visibility
 * @composed 0..1 math 1 ASTNode
 */
public abstract class MathContainer extends AbstractSBase implements
		SBaseWithDerivedUnit {

	/**
	 * Represents the 'formula' XML attribute of this object.
	 */
	@Deprecated
	private String formula;

	/**
	 * The math formula as a Tree.
	 */
	private ASTNode math;

	/**
	 * The MathMl subnodes as a StringBuffer.
	 */
	private StringBuffer mathBuffer;

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
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	public abstract MathContainer clone();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.SBaseWithDerivedUnit#containsUndeclaredUnits()
	 */
	public boolean containsUndeclaredUnits() {
		return isSetMath() ? math.containsUndeclaredUnits() : false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (o.getClass().getName().equals(this.getClass().getName())) {
			MathContainer c = (MathContainer) o;
			if ((c.isSetMath() && !isSetMath())
					|| (!c.isSetMath() && isSetMath())) {
				return false;
			}
			boolean equal = super.equals(o);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeNode#getAllowsChildren()
	 */
	@Override
	public boolean getAllowsChildren() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
	 */
	@Override
	public TreeNode getChildAt(int index) {
		int children = getChildCount();
		if (index >= children) {
			throw new IndexOutOfBoundsException(index + " >= " + children);
		}
		int pos = 0;
		if (isSetMath()) {
			if (index == pos) {
				return getMath();
			}
			pos++;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeNode#getChildCount()
	 */
	@Override
	public int getChildCount() {
		return isSetMath() ? 1 : 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.SBaseWithDerivedUnit#getDerivedUnitDefinition()
	 */
	public UnitDefinition getDerivedUnitDefinition() {
		UnitDefinition ud = null;
		if (isSetMath()) {
			ud = math.deriveUnit();
		}
		if (ud != null) {
			Model m = getModel();
			if (m != null)
				for (UnitDefinition u : m.getListOfUnitDefinitions()) {
					if (UnitDefinition.areEquivalent(u, ud)) {
						ud = u;
						break;
					}
				}
		}
		return ud;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.SBaseWithDerivedUnit#getDerivedUnits()
	 */
	@SuppressWarnings("deprecation")
	public String getDerivedUnits() {
		UnitDefinition ud = getDerivedUnitDefinition();
		Model m = getModel();
		if (m != null) {
			if (m.getUnitDefinition(ud.getId()) != null)
				return ud.getId();
		}
		if (ud.getNumUnits() == 1) {
			Unit u = ud.getUnit(0);
			if (u.getOffset() == 0 && u.getMultiplier() == 1
					&& u.getScale() == 0 && u.getExponent() == 1)
				return u.getKind().toString();
		}
		return null;
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
	 * @return the formula of this object. If the formula is not set, it returns
	 *         the empty String.
	 */
	public String getFormulaString() {
		return isSetFormulaString() ? formula : "";
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
	 * @return the mathBuffer of this object. Null if it is not set.
	 */
	public StringBuffer getMathBuffer() {
		return mathBuffer;
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
	 * @return true if the formula of this Object is not null.
	 */
	@Deprecated
	public boolean isSetFormulaString() {
		return this.formula != null;
	}

	/**
	 * 
	 * @return true if the math ASTNode of this object is not null.
	 */
	public boolean isSetMath() {
		return math != null;
	}

	/**
	 * 
	 * @return true if the mathBuffer of this object is not null.
	 */
	public boolean isSetMathBuffer() {
		return this.mathBuffer != null;
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
	 * Sets the math ASTNode of this object to 'math'.
	 * 
	 * @param math
	 */
	public void setMath(ASTNode math) {
		this.math = math;
		this.math.parentSBMLObject = this;
		stateChanged();
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

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#toString()
	 */
	public String toString() {
		return isSetMath() ? math.toString() : "";
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
