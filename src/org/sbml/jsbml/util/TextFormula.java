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

package org.sbml.jsbml.util;

import java.util.IllegalFormatException;
import java.util.List;
import java.util.Vector;

import org.sbml.jsbml.element.SpeciesReference;

/**
 * @author Andreas Dr&auml;ger <a
 *         href="mailto:andreas.draeger@uni-tuebingen.de">
 *         andreas.draeger@uni-tuebingen.de</a>
 * 
 */
public class TextFormula extends StringTools {

	/**
	 * 
	 * @param sb
	 * @return
	 */
	public static final StringBuffer brackets(Object sb) {
		return concat(Character.valueOf('('), sb, Character.valueOf(')'));
	}

	/**
	 * Returns the difference of the given elements as StringBuffer.
	 * 
	 * @param subtrahents
	 * @return
	 */
	public static final StringBuffer diff(Object... subtrahents) {
		if (subtrahents.length == 1)
			return brackets(concat(Character.valueOf('-'), subtrahents));
		return brackets(arith('-', subtrahents));
	}

	/**
	 * Returns a fraction with the given elements as numerator and denominator.
	 * 
	 * @param numerator
	 * @param denominator
	 * @return
	 */
	public static final StringBuffer frac(Object numerator, Object denominator) {
		return brackets(arith('/',
				(containsArith(numerator) ? brackets(numerator) : numerator),
				containsArith(denominator) ? brackets(denominator)
						: denominator));
	}

	/**
	 * Returns the basis to the power of the exponent as StringBuffer. Several
	 * special cases are treated.
	 * 
	 * @param basis
	 * @param exponent
	 * @return
	 */
	public static final StringBuffer pow(Object basis, Object exponent) {
		try {
			if (Double.parseDouble(exponent.toString()) == 0f)
				return new StringBuffer("1");
			if (Double.parseDouble(exponent.toString()) == 1f)
				return basis instanceof StringBuffer ? (StringBuffer) basis
						: new StringBuffer(basis.toString());
		} catch (NumberFormatException exc) {
		}
		String b = basis.toString();
		if (b.contains("*") || b.contains("-") || b.contains("+")
				|| b.contains("/") || b.contains("^"))
			basis = brackets(basis);
		String e = exponent.toString();
		if (e.contains("*") || e.contains("-") || e.contains("+")
				|| e.contains("/") || e.contains("^"))
			exponent = brackets(e);
		return arith('^', basis, exponent);
	}

	/**
	 * Returns the exponent-th root of the basis as StringBuffer.
	 * 
	 * @param exponent
	 * @param basis
	 * @return
	 * @throws IllegalFormatException
	 *             If the given exponent represents a zero.
	 */
	public static final StringBuffer root(Object exponent, Object basis)
			throws NumberFormatException {
		if (Double.parseDouble(exponent.toString()) == 0f)
			throw new NumberFormatException(
					"Cannot extract a zeroth root of anything");
		if (Double.parseDouble(exponent.toString()) == 1f)
			return new StringBuffer(basis.toString());
		return concat("root(", exponent, Character.valueOf(','), basis,
				Character.valueOf(')'));
	}

	public static final StringBuffer sqrt(Object basis) {
		try {
			return root(Integer.valueOf(2), basis);
		} catch (IllegalFormatException e) {
			return pow(basis, frac(Integer.valueOf(1), Integer.valueOf(2)));
		}
	}

	/**
	 * Returns the sum of the given elements as StringBuffer.
	 * 
	 * @param summands
	 * @return
	 */
	public static final StringBuffer sum(Object... summands) {
		return brackets(arith('+', summands));
	}

	/**
	 * Returns the product of the given elements as StringBuffer.
	 * 
	 * @param factors
	 * @return
	 */
	public static final StringBuffer times(Object... factors) {
		return arith('*', factors);
	}

	/**
	 * Basic method which links several elements with a mathematical operator.
	 * All empty StringBuffer object are excluded.
	 * 
	 * @param operator
	 * @param elements
	 * @return
	 */
	private static final StringBuffer arith(char operator, Object... elements) {
		List<Object> vsb = new Vector<Object>();
		for (Object sb : elements)
			if (sb.toString().length() > 0)
				vsb.add(sb);
		StringBuffer equation = new StringBuffer();
		if (vsb.size() > 0)
			equation.append(vsb.get(0));
		Character op = Character.valueOf(operator);
		for (int count = 1; count < vsb.size(); count++)
			append(equation, op, vsb.get(count));
		return equation;
	}

	/**
	 * Tests whether the String representation of the given object contains any
	 * arithmetic symbols and if the given object is already sorrounded by
	 * brackets.
	 * 
	 * @param something
	 * @return True if either brackets are set around the given object or the
	 *         object does not contain any symbols such as +, -, *, /.
	 */
	private static boolean containsArith(Object something) {
		boolean arith = false;
		String d = something.toString();
		if (d.length() > 0) {
			char c;
			for (int i = 0; (i < d.length()) && !arith; i++) {
				c = d.charAt(i);
				arith = ((c == '+') || (c == '-') || (c == '*') || (c == '/'));
			}
		}
		return arith;
	}

	/**
	 * Returns the id of a PluginSpeciesReference object's belonging species as
	 * an object of type StringBuffer.
	 * 
	 * @param ref
	 * @return
	 */
	protected static final StringBuffer getSpecies(SpeciesReference ref) {
		return new StringBuffer(ref.getSpecies());
	}

	/**
	 * Returns the value of a PluginSpeciesReference object's stoichiometry
	 * either as a double or, if the stoichiometry has an integer value, as an
	 * int object.
	 * 
	 * @param ref
	 * @return
	 */
	protected static final double getStoichiometry(SpeciesReference ref) {
		double stoich = ref.getStoichiometry();
		return stoich;
	}
}
