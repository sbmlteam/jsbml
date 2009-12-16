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

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.ASTNodeCompiler;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.NamedSBase;
import org.sbml.jsbml.SpeciesReference;

/**
 * @author Andreas Dr&auml;ger
 * 
 */
public class TextFormula extends StringTools implements ASTNodeCompiler {

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
			if (sb != null && sb.toString().length() > 0)
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#abs(org.sbml.jsbml.ASTNode)
	 */
	public String abs(ASTNode node) {
		return function("abs", node);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.ASTNodeCompiler#and(org.sbml.jsbml.ASTNode[])
	 */
	public Object and(ASTNode... nodes) {
		return logicalOperation(" and ", nodes);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arccos(org.sbml.jsbml.ASTNode)
	 */
	public String arccos(ASTNode node) {
		return function("arccos", node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arccosh(org.sbml.jsbml.ASTNode)
	 */
	public String arccosh(ASTNode node) {
		return function("arccos", node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arccot(org.sbml.jsbml.ASTNode)
	 */
	public String arccot(ASTNode node) {
		return function("arccot", node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arccoth(org.sbml.jsbml.ASTNode)
	 */
	public String arccoth(ASTNode node) {
		return function("arccoth", node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arccsc(org.sbml.jsbml.ASTNode)
	 */
	public String arccsc(ASTNode node) {
		return function("arccsc", node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arccsch(org.sbml.jsbml.ASTNode)
	 */
	public String arccsch(ASTNode node) {
		return function("arccsch", node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arcsec(org.sbml.jsbml.ASTNode)
	 */
	public String arcsec(ASTNode node) {
		return function("arcsec", node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arcsech(org.sbml.jsbml.ASTNode)
	 */
	public String arcsech(ASTNode node) {
		return function("arcsech", node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arcsin(org.sbml.jsbml.ASTNode)
	 */
	public String arcsin(ASTNode node) {
		return function("arcsin", node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arcsinh(org.sbml.jsbml.ASTNode)
	 */
	public String arcsinh(ASTNode node) {
		return function("arcsinh", node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arctan(org.sbml.jsbml.ASTNode)
	 */
	public String arctan(ASTNode node) {
		return function("arctan", node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arctanh(org.sbml.jsbml.ASTNode)
	 */
	public String arctanh(ASTNode node) {
		return function("arctanh", node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#ceiling(org.sbml.jsbml.ASTNode)
	 */
	public String ceiling(ASTNode node) {
		return function("ceil", node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#compile(org.sbml.jsbml.Compartment)
	 */
	public String compile(Compartment c) {
		return c.getId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#compile(double)
	 */
	public String compile(double real) {
		return toString(real);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#compile(int)
	 */
	public String compile(int integer) {
		return Integer.toString(integer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#compile(org.sbml.jsbml.NamedSBase)
	 */
	public String compile(NamedSBase variable) {
		return variable.getId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#compile(java.lang.String)
	 */
	public String compile(String name) {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#cos(org.sbml.jsbml.ASTNode)
	 */
	public String cos(ASTNode node) {
		return function("cos", node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#cosh(org.sbml.jsbml.ASTNode)
	 */
	public String cosh(ASTNode node) {
		return function("cosh", node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#cot(org.sbml.jsbml.ASTNode)
	 */
	public String cot(ASTNode node) {
		return function("cot", node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#coth(org.sbml.jsbml.ASTNode)
	 */
	public String coth(ASTNode node) {
		return function("coth", node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#csc(org.sbml.jsbml.ASTNode)
	 */
	public String csc(ASTNode node) {
		return function("csc", node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#csch(org.sbml.jsbml.ASTNode)
	 */
	public String csch(ASTNode node) {
		return function("csch", node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#exp(org.sbml.jsbml.ASTNode)
	 */
	public String exp(ASTNode node) {
		return function("exp", node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#factorial(org.sbml.jsbml.ASTNode)
	 */
	public String factorial(ASTNode node) {
		return append(brackets(node.compile(this)), Character.valueOf('!'))
				.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#floor(org.sbml.jsbml.ASTNode)
	 */
	public String floor(ASTNode node) {
		return function("floor", node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#frac(org.sbml.jsbml.ASTNode,
	 * org.sbml.jsbml.ASTNode)
	 */
	public String frac(ASTNode left, ASTNode right) {
		String numerator = checkBrackets(left);
		String denominator = checkBrackets(right);
		return concat(numerator, Character.valueOf('/'), denominator)
				.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#frac(int, int)
	 */
	public String frac(int numerator, int denominator) {
		return concat(
				numerator < 0 ? brackets(compile(numerator))
						: compile(numerator),
				Character.valueOf('/'),
				denominator < 0 ? brackets(compile(denominator))
						: compile(denominator)).toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#function(org.sbml.jsbml.NamedSBase,
	 * org.sbml.jsbml.ASTNode[])
	 */
	public String function(FunctionDefinition func, ASTNode... nodes) {
		return function(func.getName(), nodes);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#functionDelay(java.lang.String)
	 */
	public String functionDelay(String delay) {
		return delay;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#getConstantE()
	 */
	public String getConstantE() {
		return Character.toString('e');
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#getConstantFalse()
	 */
	public String getConstantFalse() {
		return Boolean.toString(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#getConstantPi()
	 */
	public String getConstantPi() {
		return "pi";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#getConstantTrue()
	 */
	public String getConstantTrue() {
		return Boolean.toString(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#getNegativeInfinity()
	 */
	public String getNegativeInfinity() {
		return Double.toString(Double.NEGATIVE_INFINITY);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#getPositiveInfinity()
	 */
	public String getPositiveInfinity() {
		return Double.toString(Double.POSITIVE_INFINITY);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#lambda(org.sbml.jsbml.ASTNode[])
	 */
	public String lambda(ASTNode... nodes) {
		StringBuffer lambda = new StringBuffer();
		for (int i = 0; i < nodes.length; i++) {
			if (i > 0)
				lambda.append(", ");
			lambda.append(nodes[i].compile(this));
		}
		return brackets(lambda).toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#ln(org.sbml.jsbml.ASTNode)
	 */
	public String ln(ASTNode node) {
		return function("log", node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#log(org.sbml.jsbml.ASTNode)
	 */
	public Object log(ASTNode node) {
		return function("log", node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#log(org.sbml.jsbml.ASTNode,
	 * org.sbml.jsbml.ASTNode)
	 */
	public String log(ASTNode left, ASTNode right) {
		return function("log", left, right);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#logicalAND(org.sbml.jsbml.ASTNode[])
	 */
	public String logicalAND(ASTNode... nodes) {
		return logicalOperation(" and ", nodes);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#logicalNot(org.sbml.jsbml.ASTNode)
	 */
	public String logicalNot(ASTNode node) {
		return function("not", node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#logicalOR(org.sbml.jsbml.ASTNode[])
	 */
	public String logicalOR(ASTNode... nodes) {
		return logicalOperation(" or ", nodes);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#logicalXOR(org.sbml.jsbml.ASTNode[])
	 */
	public String logicalXOR(ASTNode... nodes) {
		return logicalOperation(" xor ", nodes);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#minus(org.sbml.jsbml.ASTNode[])
	 */
	public String minus(ASTNode... nodes) {
		StringBuffer minus = new StringBuffer();
		for (int i = 0; i < nodes.length; i++) {
			if (i > 0)
				minus.append('-');
			minus.append(checkBrackets(nodes[i]));
		}
		return minus.toString();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.ASTNodeCompiler#or(org.sbml.jsbml.ASTNode[])
	 */
	public Object or(ASTNode... nodes) {
		return logicalOperation(" or ", nodes);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#piecewise(org.sbml.jsbml.ASTNode[])
	 */
	public String piecewise(ASTNode... nodes) {
		return function("piecewise", nodes);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#plus(org.sbml.jsbml.ASTNode[])
	 */
	public String plus(ASTNode... nodes) {
		StringBuffer plus = new StringBuffer();
		for (int i = 0; i < nodes.length; i++) {
			if (i > 0)
				plus.append('+');
			plus.append(nodes[i].compile(this));
		}
		return plus.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#pow(org.sbml.jsbml.ASTNode,
	 * org.sbml.jsbml.ASTNode)
	 */
	public String pow(ASTNode left, ASTNode right) {
		return function("pow", left, right);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#relationEqual(org.sbml.jsbml.ASTNode,
	 * org.sbml.jsbml.ASTNode)
	 */
	public String relationEqual(ASTNode left, ASTNode right) {
		return concat(left.compile(this), " == ", right.compile(this))
				.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jsbml.ASTNodeCompiler#relationGreaterEqual(org.sbml.jsbml.ASTNode
	 * , org.sbml.jsbml.ASTNode)
	 */
	public String relationGreaterEqual(ASTNode left, ASTNode right) {
		return concat(left.compile(this), ">=", right.compile(this)).toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jsbml.ASTNodeCompiler#relationGraterThan(org.sbml.jsbml.ASTNode,
	 * org.sbml.jsbml.ASTNode)
	 */
	public String relationGreaterThan(ASTNode left, ASTNode right) {
		return concat(left.compile(this), Character.valueOf('>'),
				right.compile(this)).toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jsbml.ASTNodeCompiler#relationLessEqual(org.sbml.jsbml.ASTNode,
	 * org.sbml.jsbml.ASTNode)
	 */
	public String relationLessEqual(ASTNode left, ASTNode right) {
		return concat(left.compile(this), "<=", right.compile(this)).toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jsbml.ASTNodeCompiler#relationLessThan(org.sbml.jsbml.ASTNode,
	 * org.sbml.jsbml.ASTNode)
	 */
	public String relationLessThan(ASTNode left, ASTNode right) {
		return concat(left.compile(this), Character.valueOf('<'),
				right.compile(this)).toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jsbml.ASTNodeCompiler#relationNotEqual(org.sbml.jsbml.ASTNode,
	 * org.sbml.jsbml.ASTNode)
	 */
	public String relationNotEqual(ASTNode left, ASTNode right) {
		return concat(left.compile(this), "!=", right.compile(this)).toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#root(org.sbml.jsbml.ASTNode,
	 * org.sbml.jsbml.ASTNode)
	 */
	public String root(ASTNode rootExponent, ASTNode radiant) {
		return function("root", rootExponent, radiant);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#sec(org.sbml.jsbml.ASTNode)
	 */
	public String sec(ASTNode node) {
		return function("sec", node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#sech(org.sbml.jsbml.ASTNode)
	 */
	public String sech(ASTNode node) {
		return function("sech", node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#sin(org.sbml.jsbml.ASTNode)
	 */
	public String sin(ASTNode node) {
		return function("sin", node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#sinh(org.sbml.jsbml.ASTNode)
	 */
	public String sinh(ASTNode node) {
		return function("sin", node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#sqrt(org.sbml.jsbml.ASTNode)
	 */
	public String sqrt(ASTNode node) {
		return function("sqrt", node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#symbolTime(java.lang.String)
	 */
	public String symbolTime(String time) {
		return time;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#tan(org.sbml.jsbml.ASTNode)
	 */
	public String tan(ASTNode node) {
		return function("tan", node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#tanh(org.sbml.jsbml.ASTNode)
	 */
	public String tanh(ASTNode node) {
		return function("tanh", node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#times(org.sbml.jsbml.ASTNode[])
	 */
	public String times(ASTNode... nodes) {
		Object n[] = new Object[nodes.length];
		for (int i = 0; i < nodes.length; i++)
			n[i] = nodes[i].compile(this);
		return times(n).toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#uiMinus(org.sbml.jsbml.ASTNode)
	 */
	public String uiMinus(ASTNode node) {
		return concat(Character.valueOf('-'),
				checkBrackets(node.getLeftChild())).toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#unknownASTNode()
	 */
	public String unknownASTNode() {
		return Character.toString('?');
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.ASTNodeCompiler#xor(org.sbml.jsbml.ASTNode[])
	 */
	public Object xor(ASTNode... nodes) {
		return logicalOperation(" xor ", nodes);
	}

	/**
	 * Creates brackets if needed.
	 * 
	 * @param nodes
	 * @return
	 */
	private String checkBrackets(ASTNode nodes) {
		String term = nodes.compile(this).toString();
		if ((nodes.isOperator() && (nodes.getCharacter() == '+' || nodes
				.getCharacter() == '-'))
				|| nodes.isUMinus())
			term = brackets(term).toString();
		return term;
	}

	/**
	 * 
	 * @param name
	 * @param nodes
	 * @return
	 */
	private String function(String name, ASTNode... nodes) {
		return concat(name, lambda(nodes)).toString();
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jsbml.ASTNodeCompiler#logicalOperation(org.sbml.jsbml.ASTNode)
	 */
	public String logicalOperation(ASTNode ast) {
		StringBuffer value = new StringBuffer();
		if (1 < ast.getLeftChild().getNumChildren())
			value.append('(');
		value.append(ast.getLeftChild().compile(this));
		if (1 < ast.getLeftChild().getNumChildren())
			value.append(')');
		switch (ast.getType()) {
		case LOGICAL_AND:
			value.append(" and ");
			break;
		case LOGICAL_XOR:
			value.append(" xor ");
			break;
		case LOGICAL_OR:
			value.append(" or ");
			break;
		default:
			break;
		}
		if (1 < ast.getRightChild().getNumChildren())
			value.append('(');
		value.append(ast.getRightChild().compile(this));
		if (1 < ast.getRightChild().getNumChildren())
			value.append(')');
		return value.toString();
	}

	
	/**
	 * 
	 * @param operator
	 * @param nodes
	 * @return
	 */
	private String logicalOperation(String operator, ASTNode... nodes) {
		StringBuffer value = new StringBuffer();
		boolean first = true;
		for (ASTNode node : nodes) {
			if (!first)
				value.append(operator);
			else
				first = true;
			if (1 < node.getNumChildren())
				append(value, Character.valueOf('('), node.compile(this),
						Character.valueOf(')'));
			else
				value.append(node.compile(this));
		}
		return value.toString();
	}
}
