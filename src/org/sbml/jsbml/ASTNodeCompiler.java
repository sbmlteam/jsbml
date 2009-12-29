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
 * A compiler for abstract syntax trees.
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
	 * @param node
	 * @return
	 */
	public Object abs(ASTNode node);

	/**
	 * 
	 * @param nodes
	 * @return
	 */
	public Object and(ASTNode... nodes);

	/**
	 * 
	 * @param node
	 * @return
	 */
	public Object arccos(ASTNode node);

	/**
	 * 
	 * @param node
	 * @return
	 */
	public Object arccosh(ASTNode node);

	/**
	 * 
	 * @param node
	 * @return
	 */
	public Object arccot(ASTNode node);

	/**
	 * 
	 * @param node
	 * @return
	 */
	public Object arccoth(ASTNode node);

	/**
	 * 
	 * @param node
	 * @return
	 */
	public Object arccsc(ASTNode node);

	/**
	 * 
	 * @param node
	 * @return
	 */
	public Object arccsch(ASTNode node);

	/**
	 * 
	 * @param node
	 * @return
	 */
	public Object arcsec(ASTNode node);

	/**
	 * 
	 * @param node
	 * @return
	 */
	public Object arcsech(ASTNode node);

	/**
	 * 
	 * @param node
	 * @return
	 */
	public Object arcsin(ASTNode node);

	/**
	 * 
	 * @param node
	 * @return
	 */
	public Object arcsinh(ASTNode node);

	/**
	 * 
	 * @param node
	 * @return
	 */
	public Object arctan(ASTNode node);

	/**
	 * 
	 * @param node
	 * @return
	 */
	public Object arctanh(ASTNode node);

	/**
	 * 
	 * @param node
	 * @return
	 */
	public Object ceiling(ASTNode node);

	/**
	 * 
	 * @param c
	 * @return
	 */
	public Object compile(Compartment c);

	/**
	 * 
	 * @param real
	 * @return
	 */
	public Object compile(double real);

	/**
	 * 
	 * @param integer
	 * @return
	 */
	public Object compile(int integer);

	/**
	 * 
	 * @param variable
	 * @return
	 */
	public Object compile(NamedSBase variable);

	/**
	 * 
	 * @param name
	 * @return
	 */
	public Object compile(String name);

	/**
	 * 
	 * @param node
	 * @return
	 */
	public Object cos(ASTNode node);

	/**
	 * 
	 * @param node
	 * @return
	 */
	public Object cosh(ASTNode node);

	/**
	 * 
	 * @param node
	 * @return
	 */
	public Object cot(ASTNode node);

	/**
	 * 
	 * @param node
	 * @return
	 */
	public Object coth(ASTNode node);

	/**
	 * 
	 * @param node
	 * @return
	 */
	public Object csc(ASTNode node);

	/**
	 * 
	 * @param node
	 * @return
	 */
	public Object csch(ASTNode node);

	/**
	 * 
	 * @param node
	 * @return
	 */
	public Object exp(ASTNode node);

	/**
	 * 
	 * @param node
	 * @return
	 */
	public Object factorial(ASTNode node);

	/**
	 * 
	 * @param node
	 * @return
	 */
	public Object floor(ASTNode node);

	/**
	 * 
	 * @param left
	 * @param right
	 * @return
	 */
	public Object frac(ASTNode left, ASTNode right);

	/**
	 * 
	 * @param numerator
	 * @param denominator
	 * @return
	 */
	public Object frac(int numerator, int denominator);

	/**
	 * 
	 * @param namedSBase
	 * @param args
	 * @return
	 */
	public Object function(FunctionDefinition namedSBase, ASTNode... args);

	/**
	 * 
	 * @param delay
	 * @return
	 */
	public Object functionDelay(String delay);

	/**
	 * 
	 * @return
	 */
	public Object getConstantE();

	/**
	 * 
	 * @return
	 */
	public Object getConstantFalse();

	/**
	 * 
	 * @return
	 */
	public Object getConstantPi();

	/**
	 * 
	 * @return
	 */
	public Object getConstantTrue();

	/**
	 * 
	 * @return
	 */
	public Object getNegativeInfinity();

	/**
	 * 
	 * @return
	 */
	public Object getPositiveInfinity();

	/**
	 * 
	 * @param nodes
	 * @return
	 */
	public Object lambda(ASTNode... nodes);

	/**
	 * 
	 * @param node
	 * @return
	 */
	public Object ln(ASTNode node);

	/**
	 * 
	 * @param node
	 * @return
	 */
	public Object log(ASTNode node);

	/**
	 * 
	 * @param left
	 * @param right
	 * @return
	 */
	public Object log(ASTNode left, ASTNode right);

	/**
	 * 
	 * @param nodes
	 * @return
	 */
	public Object minus(ASTNode... nodes);

	/**
	 * 
	 * @param node
	 * @return
	 */
	public Object not(ASTNode node);

	/**
	 * 
	 * @param nodes
	 * @return
	 */
	public Object or(ASTNode... nodes);

	/**
	 * 
	 * @param nodes
	 * @return
	 */
	public Object piecewise(ASTNode... nodes);

	/**
	 * 
	 * @param nodes
	 * @return
	 */
	public Object plus(ASTNode... nodes);

	/**
	 * 
	 * @param left
	 * @param right
	 * @return
	 */
	public Object pow(ASTNode left, ASTNode right);

	/**
	 * 
	 * @param left
	 * @param right
	 * @return
	 */
	public Object relationEqual(ASTNode left, ASTNode right);

	/**
	 * 
	 * @param left
	 * @param right
	 * @return
	 */
	public Object relationGreaterEqual(ASTNode left, ASTNode right);

	/**
	 * 
	 * @param leftChild
	 * @param rightChild
	 * @return
	 */
	public Object relationGreaterThan(ASTNode left, ASTNode right);

	/**
	 * 
	 * @param left
	 * @param right
	 * @return
	 */
	public Object relationLessEqual(ASTNode left, ASTNode right);

	/**
	 * 
	 * @param left
	 * @param right
	 * @return
	 */
	public Object relationLessThan(ASTNode left, ASTNode right);

	/**
	 * 
	 * @param left
	 * @param right
	 * @return
	 */
	public Object relationNotEqual(ASTNode left, ASTNode right);

	/**
	 * 
	 * @param rootExponent
	 * @param radiant
	 * @return
	 */
	public Object root(ASTNode rootExponent, ASTNode radiant);

	/**
	 * 
	 * @param node
	 * @return
	 */
	public Object sec(ASTNode node);

	/**
	 * 
	 * @param node
	 * @return
	 */
	public Object sech(ASTNode node);

	/**
	 * 
	 * @param node
	 * @return
	 */
	public Object sin(ASTNode node);

	/**
	 * 
	 * @param node
	 * @return
	 */
	public Object sinh(ASTNode node);

	/**
	 * 
	 * @param node
	 * @return
	 */
	public Object sqrt(ASTNode node);

	/**
	 * 
	 * @param time
	 * @return
	 */
	public Object symbolTime(String time);

	/**
	 * 
	 * @param node
	 * @return
	 */
	public Object tan(ASTNode node);

	/**
	 * 
	 * @param node
	 * @return
	 */
	public Object tanh(ASTNode node);

	/**
	 * 
	 * @param nodes
	 * @return
	 */
	public Object times(ASTNode... nodes);

	/**
	 * 
	 * @param node
	 * @return
	 */
	public Object uiMinus(ASTNode node);

	/**
	 * 
	 * @return
	 */
	public Object unknownASTNode();

	/**
	 * 
	 * @param nodes
	 * @return
	 */
	public Object xor(ASTNode... nodes);

}
