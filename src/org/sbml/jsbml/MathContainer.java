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

import org.sbml.jsbml.text.parser.ParseException;

/**
 * Base interface for all the SBML components which contain MathML nodes.
 * 
 * @author Andreas Dr&auml;ger
 */
public interface MathContainer extends SBaseWithDerivedUnit {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.SBase#clone()
	 */
	public MathContainer clone();

	/**
	 * Converts this {@link MathContainer}'s internal {@link ASTNode} to a
	 * C-like {@link String} according to the SBML Level 1 specifications and
	 * returns it.
	 * 
	 * @return the math {@link ASTNode} of this object as a String. It returns
	 *         the empty String if the math {@link ASTNode} is not set.
	 * @deprecated As this is part of SBML Level 1, it is strongly recommended
	 *             not to work with the {@link String} representation of a
	 *             formula, but to deal with a more flexible {@link ASTNode}.
	 * @see #getMath()
	 */
	@Deprecated
	public String getFormula();

	/**
	 * If {@link #isSetMath()} returns true, this method returns the
	 * {@link ASTNode} belonging to this {@link MathContainer}.
	 * 
	 * @return the math {@link ASTNode} of this object. It return null if the
	 *         math {@link ASTNode} is not set.
	 */
	public ASTNode getMath();

	/**
	 * If {@link #isSetMath()} returns true, this method returns the
	 * corresponding MathML {@link String}, otherwise an empty {@link String}
	 * will be returned.
	 * 
	 * @return the MathML representation of this {@link MathContainer}'s math
	 *         element.
	 */
	public String getMathMLString();

	/**
	 * Checks if an {@link ASTNode} has been set for this {@link MathContainer}.
	 * 
	 * @return true if the math {@link ASTNode} of this object is not null.
	 */
	public boolean isSetMath();

	/**
	 * Sets the mathematical expression of this {@link MathContainer} instance
	 * to the given formula. This method parses the given {@link String} and
	 * stores the result in an {@link ASTNode} object.
	 * 
	 * @param formula
	 *            a C-like {@link String} according to the definition in the
	 *            SBML Level 1 specifications.
	 * @throws ParseException
	 *             If the given formula is invalid or cannot be parsed properly.
	 * @deprecated As this is part of SBML Level 1, it is strongly recommended
	 *             not to work with the {@link String} representation of a
	 *             formula, but to deal with a more flexible {@link ASTNode}.
	 */
	@Deprecated
	public void setFormula(String formula) throws ParseException;

	/**
	 * Sets the math {@link ASTNode} of this {@link MathContainer} to the given
	 * value.
	 * 
	 * @param math
	 *            an abstract syntax tree.
	 */
	public void setMath(ASTNode math);
}
