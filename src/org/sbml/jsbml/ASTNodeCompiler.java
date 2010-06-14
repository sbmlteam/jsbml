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
 * A compiler for abstract syntax trees. This compiler evaluates the values
 * represented by {@link ASTNode}s. It should be noted that it is not
 * responsible for the actual recursion. It only defines how to perform
 * mathematical operations on the result of a previous recursive evaluation.
 * 
 * @author Andreas Dr&auml;ger
 * 
 * @opt attributes
 * @opt types
 * @opt visibility
 */
public interface ASTNodeCompiler {

	/**
	 * 
	 * @param value
	 * @return
	 */
	public ASTNodeValue abs(ASTNodeValue value);

	/**
	 * 
	 * @param values
	 * @return
	 */
	public ASTNodeValue and(ASTNodeValue... values);

	/**
	 * 
	 * @param value
	 * @return
	 */
	public ASTNodeValue arccos(ASTNodeValue value);

	/**
	 * 
	 * @param value
	 * @return
	 */
	public ASTNodeValue arccosh(ASTNodeValue value);

	/**
	 * 
	 * @param value
	 * @return
	 */
	public ASTNodeValue arccot(ASTNodeValue value);

	/**
	 * 
	 * @param value
	 * @return
	 */
	public ASTNodeValue arccoth(ASTNodeValue value);

	/**
	 * 
	 * @param value
	 * @return
	 */
	public ASTNodeValue arccsc(ASTNodeValue value);

	/**
	 * 
	 * @param value
	 * @return
	 */
	public ASTNodeValue arccsch(ASTNodeValue value);

	/**
	 * 
	 * @param value
	 * @return
	 */
	public ASTNodeValue arcsec(ASTNodeValue value);

	/**
	 * 
	 * @param value
	 * @return
	 */
	public ASTNodeValue arcsech(ASTNodeValue value);

	/**
	 * 
	 * @param value
	 * @return
	 */
	public ASTNodeValue arcsin(ASTNodeValue value);

	/**
	 * 
	 * @param value
	 * @return
	 */
	public ASTNodeValue arcsinh(ASTNodeValue value);

	/**
	 * 
	 * @param value
	 * @return
	 */
	public ASTNodeValue arctan(ASTNodeValue value);

	/**
	 * 
	 * @param value
	 * @return
	 */
	public ASTNodeValue arctanh(ASTNodeValue value);

	/**
	 * 
	 * @param value
	 * @return
	 */
	public ASTNodeValue ceiling(ASTNodeValue value);

	/**
	 * 
	 * @param c
	 * @return
	 */
	public ASTNodeValue compile(Compartment c);

	/**
	 * 
	 * @param real
	 * @return
	 */
	public ASTNodeValue compile(double real);

	/**
	 * 
	 * @param integer
	 * @return
	 */
	public ASTNodeValue compile(int integer);

	/**
	 * 
	 * @param variable
	 * @return
	 */
	public ASTNodeValue compile(NamedSBaseWithDerivedUnit variable);

	/**
	 * 
	 * @param name
	 * @return
	 */
	public ASTNodeValue compile(String name);

	/**
	 * 
	 * @param value
	 * @return
	 */
	public ASTNodeValue cos(ASTNodeValue value);

	/**
	 * 
	 * @param value
	 * @return
	 */
	public ASTNodeValue cosh(ASTNodeValue value);

	/**
	 * 
	 * @param value
	 * @return
	 */
	public ASTNodeValue cot(ASTNodeValue value);

	/**
	 * 
	 * @param value
	 * @return
	 */
	public ASTNodeValue coth(ASTNodeValue value);

	/**
	 * 
	 * @param value
	 * @return
	 */
	public ASTNodeValue csc(ASTNodeValue value);

	/**
	 * 
	 * @param value
	 * @return
	 */
	public ASTNodeValue csch(ASTNodeValue value);

	/**
	 * Evaluate delay functions.
	 * 
	 * @param x
	 * @param d
	 * @return
	 */
	public ASTNodeValue delay(ASTNodeValue x, double d);

	/**
	 * Equal.
	 * 
	 * @param left
	 * @param right
	 * @return
	 */
	public ASTNodeValue eq(ASTNodeValue left, ASTNodeValue right);

	/**
	 * 
	 * @param value
	 * @return
	 */
	public ASTNodeValue exp(ASTNodeValue value);

	/**
	 * 
	 * @param value
	 * @return
	 */
	public ASTNodeValue factorial(ASTNodeValue value);

	/**
	 * 
	 * @param value
	 * @return
	 */
	public ASTNodeValue floor(ASTNodeValue value);

	/**
	 * 
	 * @param left
	 * @param right
	 * @return
	 */
	public ASTNodeValue frac(ASTNodeValue left, ASTNodeValue right);

	/**
	 * 
	 * @param numerator
	 * @param denominator
	 * @return
	 */
	public ASTNodeValue frac(int numerator, int denominator);

	/**
	 * 
	 * @param namedSBase
	 * @param args
	 * @return
	 */
	public ASTNodeValue function(FunctionDefinition namedSBase,
			ASTNodeValue... args);

	/**
	 * Greater equal.
	 * 
	 * @param left
	 * @param right
	 * @return
	 */
	public ASTNodeValue ge(ASTNodeValue left, ASTNodeValue right);

	/**
	 * 
	 * @return
	 */
	public ASTNodeValue getConstantE();

	/**
	 * 
	 * @return
	 */
	public ASTNodeValue getConstantFalse();

	/**
	 * 
	 * @return
	 */
	public ASTNodeValue getConstantPi();

	/**
	 * 
	 * @return
	 */
	public ASTNodeValue getConstantTrue();

	/**
	 * 
	 * @return
	 */
	public ASTNodeValue getNegativeInfinity();

	/**
	 * 
	 * @return
	 */
	public ASTNodeValue getPositiveInfinity();

	/**
	 * Greater than.
	 * 
	 * @param left
	 * @param right
	 * @return
	 */
	public ASTNodeValue gt(ASTNodeValue left, ASTNodeValue right);

	/**
	 * 
	 * @param values
	 * @return
	 */
	public ASTNodeValue lambda(ASTNodeValue... values);

	/**
	 * Less equal.
	 * 
	 * @param left
	 * @param right
	 * @return
	 */
	public ASTNodeValue le(ASTNodeValue left, ASTNodeValue right);

	/**
	 * 
	 * @param value
	 * @return
	 */
	public ASTNodeValue ln(ASTNodeValue value);

	/**
	 * 
	 * @param value
	 * @return
	 */
	public ASTNodeValue log(ASTNodeValue value);

	/**
	 * 
	 * @param left
	 * @param right
	 * @return
	 */
	public ASTNodeValue log(ASTNodeValue left, ASTNodeValue right);

	/**
	 * Less than.
	 * 
	 * @param left
	 * @param right
	 * @return
	 */
	public ASTNodeValue lt(ASTNodeValue left, ASTNodeValue right);

	/**
	 * 
	 * @param values
	 * @return
	 */
	public ASTNodeValue minus(ASTNodeValue... values);

	/**
	 * Not equal.
	 * 
	 * @param left
	 * @param right
	 * @return
	 */
	public ASTNodeValue ne(ASTNodeValue left, ASTNodeValue right);

	/**
	 * 
	 * @param value
	 * @return
	 */
	public ASTNodeValue not(ASTNodeValue value);

	/**
	 * 
	 * @param values
	 * @return
	 */
	public ASTNodeValue or(ASTNodeValue... values);

	/**
	 * 
	 * @param values
	 * @return
	 */
	public ASTNodeValue piecewise(ASTNodeValue... values);

	/**
	 * 
	 * @param values
	 * @return
	 */
	public ASTNodeValue plus(ASTNodeValue... values);

	/**
	 * 
	 * @param left
	 * @param right
	 * @return
	 */
	public ASTNodeValue pow(ASTNodeValue left, ASTNodeValue right);

	/**
	 * 
	 * @param rootExponent
	 * @param radiant
	 * @return
	 */
	public ASTNodeValue root(ASTNodeValue rootExponent, ASTNodeValue radiant);

	/**
	 * 
	 * @param rootExponent
	 * @param radiant
	 * @return
	 */
	public ASTNodeValue root(double rootExponent, ASTNodeValue radiant);

	/**
	 * 
	 * @param value
	 * @return
	 */
	public ASTNodeValue sec(ASTNodeValue value);

	/**
	 * 
	 * @param value
	 * @return
	 */
	public ASTNodeValue sech(ASTNodeValue value);

	/**
	 * 
	 * @param value
	 * @return
	 */
	public ASTNodeValue sin(ASTNodeValue value);

	/**
	 * 
	 * @param value
	 * @return
	 */
	public ASTNodeValue sinh(ASTNodeValue value);

	/**
	 * 
	 * @param value
	 * @return
	 */
	public ASTNodeValue sqrt(ASTNodeValue value);

	/**
	 * 
	 * @param time
	 * @return
	 */
	public ASTNodeValue symbolTime(String time);

	/**
	 * 
	 * @param value
	 * @return
	 */
	public ASTNodeValue tan(ASTNodeValue value);

	/**
	 * 
	 * @param value
	 * @return
	 */
	public ASTNodeValue tanh(ASTNodeValue value);

	/**
	 * 
	 * @param values
	 * @return
	 */
	public ASTNodeValue times(ASTNodeValue... values);

	/**
	 * 
	 * @param value
	 * @return
	 */
	public ASTNodeValue uiMinus(ASTNodeValue value);

	/**
	 * 
	 * @return
	 */
	public ASTNodeValue unknownValue();

	/**
	 * 
	 * @param values
	 * @return
	 */
	public ASTNodeValue xor(ASTNodeValue... values);

}
