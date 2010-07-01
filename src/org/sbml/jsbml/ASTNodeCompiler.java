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
	 * @throws SBMLException 
	 */
	public ASTNodeValue abs(ASTNodeValue value) throws SBMLException;

	/**
	 * 
	 * @param values
	 * @return
	 * @throws SBMLException 
	 */
	public ASTNodeValue and(ASTNodeValue... values) throws SBMLException;

	/**
	 * 
	 * @param value
	 * @return
	 * @throws SBMLException 
	 */
	public ASTNodeValue arccos(ASTNodeValue value) throws SBMLException;

	/**
	 * 
	 * @param value
	 * @return
	 * @throws SBMLException 
	 */
	public ASTNodeValue arccosh(ASTNodeValue value) throws SBMLException;

	/**
	 * 
	 * @param value
	 * @return
	 * @throws SBMLException 
	 */
	public ASTNodeValue arccot(ASTNodeValue value) throws SBMLException;

	/**
	 * 
	 * @param value
	 * @return
	 * @throws SBMLException 
	 */
	public ASTNodeValue arccoth(ASTNodeValue value) throws SBMLException;

	/**
	 * 
	 * @param value
	 * @return
	 * @throws SBMLException 
	 */
	public ASTNodeValue arccsc(ASTNodeValue value) throws SBMLException;

	/**
	 * 
	 * @param value
	 * @return
	 * @throws SBMLException 
	 */
	public ASTNodeValue arccsch(ASTNodeValue value) throws SBMLException;

	/**
	 * 
	 * @param value
	 * @return
	 * @throws SBMLException 
	 */
	public ASTNodeValue arcsec(ASTNodeValue value) throws SBMLException;

	/**
	 * 
	 * @param value
	 * @return
	 * @throws SBMLException 
	 */
	public ASTNodeValue arcsech(ASTNodeValue value) throws SBMLException;

	/**
	 * 
	 * @param value
	 * @return
	 * @throws SBMLException 
	 */
	public ASTNodeValue arcsin(ASTNodeValue value) throws SBMLException;

	/**
	 * 
	 * @param value
	 * @return
	 * @throws SBMLException 
	 */
	public ASTNodeValue arcsinh(ASTNodeValue value) throws SBMLException;

	/**
	 * 
	 * @param value
	 * @return
	 * @throws SBMLException 
	 */
	public ASTNodeValue arctan(ASTNodeValue value) throws SBMLException;

	/**
	 * 
	 * @param value
	 * @return
	 * @throws SBMLException 
	 */
	public ASTNodeValue arctanh(ASTNodeValue value) throws SBMLException;

	/**
	 * 
	 * @param value
	 * @return
	 * @throws SBMLException 
	 */
	public ASTNodeValue ceiling(ASTNodeValue value) throws SBMLException;

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
	 * @throws SBMLException 
	 */
	public ASTNodeValue compile(NamedSBaseWithDerivedUnit variable) throws SBMLException;

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
	 * @throws SBMLException 
	 */
	public ASTNodeValue cos(ASTNodeValue value) throws SBMLException;

	/**
	 * 
	 * @param value
	 * @return
	 * @throws SBMLException 
	 */
	public ASTNodeValue cosh(ASTNodeValue value) throws SBMLException;

	/**
	 * 
	 * @param value
	 * @return
	 * @throws SBMLException 
	 */
	public ASTNodeValue cot(ASTNodeValue value) throws SBMLException;

	/**
	 * 
	 * @param value
	 * @return
	 * @throws SBMLException 
	 */
	public ASTNodeValue coth(ASTNodeValue value) throws SBMLException;

	/**
	 * 
	 * @param value
	 * @return
	 * @throws SBMLException 
	 */
	public ASTNodeValue csc(ASTNodeValue value) throws SBMLException;

	/**
	 * 
	 * @param value
	 * @return
	 * @throws SBMLException 
	 */
	public ASTNodeValue csch(ASTNodeValue value) throws SBMLException;

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
	 * @throws SBMLException 
	 */
	public ASTNodeValue eq(ASTNodeValue left, ASTNodeValue right) throws SBMLException;

	/**
	 * 
	 * @param value
	 * @return
	 * @throws SBMLException 
	 */
	public ASTNodeValue exp(ASTNodeValue value) throws SBMLException;

	/**
	 * 
	 * @param value
	 * @return
	 * @throws SBMLException 
	 */
	public ASTNodeValue factorial(ASTNodeValue value) throws SBMLException;

	/**
	 * 
	 * @param value
	 * @return
	 * @throws SBMLException 
	 */
	public ASTNodeValue floor(ASTNodeValue value) throws SBMLException;

	/**
	 * 
	 * @param left
	 * @param right
	 * @return
	 * @throws SBMLException 
	 */
	public ASTNodeValue frac(ASTNodeValue left, ASTNodeValue right) throws SBMLException;

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
	 * @throws SBMLException 
	 */
	public ASTNodeValue function(FunctionDefinition functionDefinition,
			ASTNodeValue... args) throws SBMLException;

	/**
	 * Greater equal.
	 * 
	 * @param left
	 * @param right
	 * @return
	 * @throws SBMLException 
	 */
	public ASTNodeValue geq(ASTNodeValue left, ASTNodeValue right) throws SBMLException;

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
	 * @throws SBMLException 
	 */
	public ASTNodeValue gt(ASTNodeValue left, ASTNodeValue right) throws SBMLException;

	/**
	 * 
	 * @param values
	 * @return
	 * @throws SBMLException 
	 */
	public ASTNodeValue lambda(ASTNodeValue... values) throws SBMLException;

	/**
	 * Less equal.
	 * 
	 * @param left
	 * @param right
	 * @return
	 * @throws SBMLException 
	 */
	public ASTNodeValue leq(ASTNodeValue left, ASTNodeValue right) throws SBMLException;

	/**
	 * 
	 * @param value
	 * @return
	 * @throws SBMLException 
	 */
	public ASTNodeValue ln(ASTNodeValue value) throws SBMLException;

	/**
	 * Logarithm of the given value to base 10.
	 * 
	 * @param value
	 * @return
	 * @throws SBMLException 
	 */
	public ASTNodeValue log(ASTNodeValue value) throws SBMLException;

	/**
	 * Logarithm of the given value to the given base.
	 * 
	 * @param base
	 * @param value
	 * @return
	 * @throws SBMLException 
	 */
	public ASTNodeValue log(ASTNodeValue base, ASTNodeValue value) throws SBMLException;

	/**
	 * Less than.
	 * 
	 * @param left
	 * @param right
	 * @return
	 * @throws SBMLException 
	 */
	public ASTNodeValue lt(ASTNodeValue left, ASTNodeValue right) throws SBMLException;

	/**
	 * 
	 * @param values
	 * @return
	 * @throws SBMLException 
	 */
	public ASTNodeValue minus(ASTNodeValue... values) throws SBMLException;

	/**
	 * Not equal.
	 * 
	 * @param left
	 * @param right
	 * @return
	 * @throws SBMLException 
	 */
	public ASTNodeValue neq(ASTNodeValue left, ASTNodeValue right) throws SBMLException;

	/**
	 * 
	 * @param value
	 * @return
	 * @throws SBMLException 
	 */
	public ASTNodeValue not(ASTNodeValue value) throws SBMLException;

	/**
	 * 
	 * @param values
	 * @return
	 * @throws SBMLException 
	 */
	public ASTNodeValue or(ASTNodeValue... values) throws SBMLException;

	/**
	 * 
	 * @param values
	 * @return
	 * @throws SBMLException 
	 */
	public ASTNodeValue piecewise(ASTNodeValue... values) throws SBMLException;

	/**
	 * 
	 * @param values
	 * @return
	 * @throws SBMLException 
	 */
	public ASTNodeValue plus(ASTNodeValue... values) throws SBMLException;

	/**
	 * 
	 * @param base
	 * @param exponent
	 * @return
	 * @throws SBMLException 
	 */
	public ASTNodeValue pow(ASTNodeValue base, ASTNodeValue exponent) throws SBMLException;

	/**
	 * 
	 * @param rootExponent
	 * @param radiant
	 * @return
	 * @throws SBMLException 
	 */
	public ASTNodeValue root(ASTNodeValue rootExponent, ASTNodeValue radiant) throws SBMLException;

	/**
	 * 
	 * @param rootExponent
	 * @param radiant
	 * @return
	 * @throws SBMLException 
	 */
	public ASTNodeValue root(double rootExponent, ASTNodeValue radiant) throws SBMLException;

	/**
	 * 
	 * @param value
	 * @return
	 * @throws SBMLException 
	 */
	public ASTNodeValue sec(ASTNodeValue value) throws SBMLException;

	/**
	 * 
	 * @param value
	 * @return
	 * @throws SBMLException 
	 */
	public ASTNodeValue sech(ASTNodeValue value) throws SBMLException;

	/**
	 * 
	 * @param value
	 * @return
	 * @throws SBMLException 
	 */
	public ASTNodeValue sin(ASTNodeValue value) throws SBMLException;

	/**
	 * 
	 * @param value
	 * @return
	 * @throws SBMLException 
	 */
	public ASTNodeValue sinh(ASTNodeValue value) throws SBMLException;

	/**
	 * 
	 * @param radiant
	 * @return
	 * @throws SBMLException 
	 */
	public ASTNodeValue sqrt(ASTNodeValue radiant) throws SBMLException;

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
	 * @throws SBMLException 
	 */
	public ASTNodeValue tan(ASTNodeValue value) throws SBMLException;

	/**
	 * 
	 * @param value
	 * @return
	 * @throws SBMLException 
	 */
	public ASTNodeValue tanh(ASTNodeValue value) throws SBMLException;

	/**
	 * 
	 * @param values
	 * @return
	 * @throws SBMLException 
	 */
	public ASTNodeValue times(ASTNodeValue... values) throws SBMLException;

	/**
	 * 
	 * @param value
	 * @return
	 * @throws SBMLException 
	 */
	public ASTNodeValue uMinus(ASTNodeValue value) throws SBMLException;

	/**
	 * 
	 * @return
	 * @throws SBMLException 
	 */
	public ASTNodeValue unknownValue() throws SBMLException;

	/**
	 * 
	 * @param values
	 * @return
	 * @throws SBMLException 
	 */
	public ASTNodeValue xor(ASTNodeValue... values) throws SBMLException;
}
