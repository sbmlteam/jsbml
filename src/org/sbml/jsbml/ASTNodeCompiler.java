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
	 * Creates an {@link ASTNodeValue} that represents a real number in
	 * scientific notation, i.e., mantissa * 10^exponent, and the given units.
	 * 
	 * @param mantissa
	 *            The number to be multiplied with ten to the power of the given
	 *            exponent.
	 * @param exponent
	 *            The exponent for the multiplier ten.
	 * @param units
	 *            The identifier of the units object associated with the number
	 *            represented by this element. Can be null if no units have been
	 *            defined.
	 * @return
	 */
	public ASTNodeValue compile(double mantissa, int exponent, String units);

	/**
	 * 
	 * @param real
	 * @param units
	 * @return
	 */
	public ASTNodeValue compile(double real, String units);

	/**
	 * 
	 * @param integer
	 * @param units
	 * @return
	 */
	public ASTNodeValue compile(int integer, String units);

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
	 * @param delayName
	 *            the name of this delay function.
	 * @param x
	 * @param d
	 * @param timeUnits
	 *            the units for the delay.
	 * @return
	 */
	public ASTNodeValue delay(String delayName, ASTNodeValue x, double d,
			String timeUnits);

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
	 * @param functionDefinition
	 * @param args
	 * @return
	 */
	public ASTNodeValue function(FunctionDefinition functionDefinition,
			ASTNodeValue... args);

	/**
	 * Greater equal.
	 * 
	 * @param left
	 * @param right
	 * @return
	 */
	public ASTNodeValue geq(ASTNodeValue left, ASTNodeValue right);

	/**
	 * Creates an {@link ASTNodeValue} that represent's Avogadro's number.
	 * Optionally, the compiler may associate the given name with this number.
	 * 
	 * @param name
	 *            An optional name for Avogadro's number.
	 * @return
	 */
	public ASTNodeValue getConstantAvogadro(String name);

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
	public ASTNodeValue leq(ASTNodeValue left, ASTNodeValue right);

	/**
	 * 
	 * @param value
	 * @return
	 */
	public ASTNodeValue ln(ASTNodeValue value);

	/**
	 * Logarithm of the given value to base 10.
	 * 
	 * @param value
	 * @return
	 */
	public ASTNodeValue log(ASTNodeValue value);

	/**
	 * Logarithm of the given value to the given base.
	 * 
	 * @param base
	 * @param value
	 * @return
	 */
	public ASTNodeValue log(ASTNodeValue base, ASTNodeValue value);

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
	public ASTNodeValue neq(ASTNodeValue left, ASTNodeValue right);

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
	 * @param base
	 * @param exponent
	 * @return
	 */
	public ASTNodeValue pow(ASTNodeValue base, ASTNodeValue exponent);

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
	 * @param radiant
	 * @return
	 */
	public ASTNodeValue sqrt(ASTNodeValue radiant);

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
	 * Creates and returns a {@link String} representation of the given
	 * {@link ASTNodeValue}.
	 * 
	 * @param value
	 * @return
	 */
	public String toString(ASTNodeValue value);

	/**
	 * 
	 * @param value
	 * @return
	 */
	public ASTNodeValue uMinus(ASTNodeValue value);

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
