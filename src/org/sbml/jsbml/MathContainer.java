/*
 * $Id$
 * $URL$
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
 * Base class for all the SBML components which contain MathML nodes.
 * 
 * @author Andreas Dr&auml;ger
 * @author marine
 * @opt attributes
 * @opt types
 * @opt visibility
 * @composed 0..1 math 1 ASTNode
 */
public interface MathContainer extends SBaseWithDerivedUnit {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.SBase#clone()
	 */
	public MathContainer clone();

	/**
	 * @return the math ASTNode of this object as a String. It returns the empty
	 *         String if the math ASTNode is not set.
	 */
	public String getFormula();

	/**
	 * @return the formula of this object. If the formula is not set, it returns
	 *         the empty String.
	 */
	public String getFormulaString();

	/**
	 * @return the math ASTNode of this object. It return null if the math
	 *         ASTNode is not set.
	 */
	public ASTNode getMath();

	/**
	 * @return the mathBuffer of this object as a String.
	 */
	public String getMathAsString();

	/**
	 * @return true if the formula of this Object is not null.
	 * @deprecated
	 */
	@Deprecated
	public boolean isSetFormulaString();

	/**
	 * @return true if the math ASTNode of this object is not null.
	 */
	public boolean isSetMath();

	/**
	 * Sets the mathematical expression of this KineticLaw instance to the given
	 * formula.
	 * 
	 * @param formula
	 */
	public void setFormula(String formula);

	/**
	 * Sets the formula of this object to 'formula'.
	 * 
	 * @param formula
	 * @deprecated
	 */
	@Deprecated
	public void setFormulaString(String formula);

	/**
	 * Sets the math ASTNode of this object to 'math'.
	 * 
	 * @param math
	 */
	public void setMath(ASTNode math);
}
