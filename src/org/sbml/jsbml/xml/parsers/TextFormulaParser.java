/*
 * $Id:  TextFormulaParser.java 10:28:26 draeger $
 * $URL: TextFormulaParser.java $
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
package org.sbml.jsbml.xml.parsers;

import java.util.Stack;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.ASTNode.Type;

/**
 * A parser for SBML Level 1 text formulas that creates {@link ASTNode} objects
 * for easier computational treatment.
 * 
 * @author Andreas Dr&auml;ger
 * @date 2010-09-01
 */
public class TextFormulaParser {

	/**
	 * Switch of whether to be strict or not.
	 */
	private boolean strict;
	/**
	 * Pattern for number matching in case of mantissa and exponent
	 */
	private static final String REAL_E = "[0-9]*\\.?[0-9]*[Ee]";

	/**
	 * 
	 */
	public TextFormulaParser() {
		setStrict(false);
	}

	/**
	 * 
	 * @param node
	 * @throws SBMLException
	 */
	private void checkName(ASTNode node) throws SBMLException {
		if (strict && (node.getVariable() == null)) {
			throw new SBMLException(String.format(
					"Cannot find element with id = %s in the model", node
							.getName()));
		}
	}

	/**
	 * Detects the position of a closing bracket belonging to the first opening
	 * bracket within the given formula {@link String}.
	 * 
	 * @param formula
	 * @param currPos
	 * @return 0 if there is neither an opening nor a closing bracket or if the
	 *         number of brackets is not balanced. Otherwise it delivers the
	 *         index of the last opening bracket.
	 */
	private int findClosingBracket(String formula, int currPos) {
		Stack<Integer> brackets = new Stack<Integer>();
		char ch;
		int i = currPos;
		do {
			ch = formula.charAt(i);
			if ((ch == '(') || (ch == ')')) {
				brackets.push(Integer.valueOf(i));
			}
			i++;
		} while ((i < formula.length())
				&& (brackets.isEmpty() || ((brackets.size() % 2 == 1) || (formula
						.charAt(brackets.peek().intValue()) == '('))));
		return brackets.isEmpty() ? 0 : brackets.pop().intValue();
	}

	/**
	 * 
	 * @param c
	 * @return
	 */
	private boolean isArithmeticOperator(char c) {
		switch (c) {
		case '+':
		case '-':
		case '*':
		case '/':
		case '^':
			return true;
		default:
			return false;
		}
	}

	/**
	 * @return the strict
	 */
	public boolean isStrict() {
		return strict;
	}

	/**
	 * 
	 * @param container
	 * @param formula
	 * @return
	 * @throws SBMLException
	 */
	public ASTNode parse(MathContainer container, String formula)
			throws SBMLException {
		if (strict && (container == null)) {
			throw new NullPointerException(MathContainer.class.getName());
		}
		// TODO: Consider relations such as <, =, >, ...
		// TODO: true, false
		Stack<ASTNode> stack = new Stack<ASTNode>();
		char c;
		StringBuilder name = new StringBuilder();
		boolean isWhiteSpace = false;
		boolean isOperator = false;
		for (int i = 0; i < formula.length(); i++) {
			c = formula.charAt(i);
			if (c == '(') {
				// recursively parse content of the brackets
				int pos = findClosingBracket(formula, i);
				ASTNode inBrackets = parse(container, formula.substring(i + 1,
						pos));
				i = pos;
				if (name.length() > 0) { // function calls
					pushFunction(stack, name.toString(), inBrackets, container);
					name = new StringBuilder();
				} else { // expression
					push(stack, inBrackets);
				}
			} else {
				// parse next symbol
				isWhiteSpace = Character.toString(c).matches("\\s");
				if (isWhiteSpace && (name.length() == 0)) {
					continue;
				}
				isOperator = isArithmeticOperator(c);
				if ((isOperator || isWhiteSpace || (i == formula.length() - 1) || (c == ','))
						&& !((c == '-') && name.toString().matches(REAL_E))) {
					if (i == formula.length() - 1) {
						name.append(c);
					}
					if (name.length() > 0) {
						pushValue(stack, name.toString(), container);
						name = new StringBuilder();
					}
					if (isOperator) {
						push(stack, new ASTNode(c, container));
					}
				} else {
					name.append(c);
				}
			}
		}
		return stack.pop();
	}

	/**
	 * 
	 * @param formula
	 * @return
	 * @throws SBMLException
	 */
	public ASTNode parse(String formula) throws SBMLException {
		strict = false;
		return parse(null, formula);
	}

	/**
	 * 
	 * @param formula
	 * @param container
	 * @return
	 * @throws SBMLException
	 */
	public ASTNode parse(String formula, MathContainer container)
			throws SBMLException {
		return parse(container, formula);
	}

	/**
	 * 
	 * @return
	 */
	private int priority(Type t) {
		switch (t) {
		case CONSTANT_E:
		case CONSTANT_FALSE:
		case CONSTANT_PI:
		case CONSTANT_TRUE:
			return 0;
		case DIVIDE:
		case FUNCTION:
		case FUNCTION_ABS:
		case FUNCTION_ARCCOS:
		case FUNCTION_ARCCOSH:
		case FUNCTION_ARCCOT:
		case FUNCTION_ARCCOTH:
		case FUNCTION_ARCCSC:
		case FUNCTION_ARCCSCH:
		case FUNCTION_ARCSEC:
		case FUNCTION_ARCSECH:
		case FUNCTION_ARCSIN:
		case FUNCTION_ARCSINH:
		case FUNCTION_ARCTAN:
		case FUNCTION_ARCTANH:
		case FUNCTION_CEILING:
		case FUNCTION_COS:
		case FUNCTION_COSH:
		case FUNCTION_COT:
		case FUNCTION_COTH:
		case FUNCTION_CSC:
		case FUNCTION_CSCH:
		case FUNCTION_DELAY:
		case FUNCTION_EXP:
		case FUNCTION_FACTORIAL:
		case FUNCTION_FLOOR:
		case FUNCTION_LN:
		case FUNCTION_LOG:
		case FUNCTION_PIECEWISE:
		case FUNCTION_POWER:
		case FUNCTION_ROOT:
		case FUNCTION_SEC:
		case FUNCTION_SECH:
		case FUNCTION_SIN:
		case FUNCTION_SINH:
		case FUNCTION_TAN:
		case FUNCTION_TANH:
			return 2;
		case INTEGER:
			return 1;
		case LAMBDA:
			return 2;
		case LOGICAL_AND:
		case LOGICAL_NOT:
		case LOGICAL_OR:
		case LOGICAL_XOR:
		case MINUS:
		case NAME:
		case NAME_AVOGADRO:
		case NAME_TIME:
		case PLUS:
			return 1;
		case POWER:
			return 3;
		case RATIONAL:
		case REAL:
		case REAL_E:
			return 0;
		case RELATIONAL_EQ:
		case RELATIONAL_GEQ:
		case RELATIONAL_GT:
		case RELATIONAL_LEQ:
		case RELATIONAL_LT:
		case RELATIONAL_NEQ:
			return 1;
		case TIMES:
			return 2;
		case UNKNOWN:
		default:
			return 0;
		}
	}

	/**
	 * Checks which one of the two given arguments has a higher priority.
	 * 
	 * @param t1
	 * @param t2
	 * @return true if t2 has a higher priority than t1, false otherwise.
	 */
	private boolean priority(Type t1, Type t2) {
		return priority(t1) < priority(t2);
	}

	/**
	 * 
	 * @param stack
	 * @param node
	 * @return
	 */
	private ASTNode push(Stack<ASTNode> stack, ASTNode node) {
		if (stack.isEmpty()) {
			stack.push(node);
		} else {
			ASTNode peek = stack.peek();
			if ((peek.isOperator() && (peek.getNumChildren() < 2))
					|| (peek.isUnknown())) {
				if (peek.isUMinus() && node.isOperator()) {
					uminusContraction(peek);
				} else {
					peek.addChild(node);
					if (node.isOperator()) {
						stack.pop();
						stack.push(node);
					}
				}
			} else if (node.isOperator()) {
				if (!peek.isOperator()
						|| (peek.isOperator() && !priority(peek.getType(), node
								.getType()))) {
					node.addChild(stack.pop());
					stack.push(node);
				} else {
					ASTNode right = peek.getRightChild();
					peek.removeChild(1);
					node.addChild(right);
					peek.addChild(node);
					stack.push(node);
				}
			} else {
				ASTNode unknown = new ASTNode(node.getParentSBMLObject());
				unknown.addChild(stack.pop());
				stack.push(unknown);
			}
		}
		ASTNode peek = stack.peek();
		if (peek.isOperator() && (stack.size() > 1)
				&& (peek.isUMinus() || (peek.getNumChildren() == 2))) {
			if (peek.isUMinus()) {
				uminusContraction(peek);
				stack.pop();
			} else if (peek.getNumChildren() == 2) {
				rationalContraction(peek);
				stack.pop();
			}
			peek = stack.peek();
		}
		return peek;
	}

	/**
	 * check functions and calls of user-defined functions
	 * 
	 * @param stack
	 * @param name
	 * @param inBrackets
	 * @param container
	 * @throws SBMLException
	 */
	private void pushFunction(Stack<ASTNode> stack, String name,
			ASTNode inBrackets, MathContainer container) throws SBMLException {
		Type t = Type.getTypeFor(name);
		if (t == Type.UNKNOWN) {
			t = Type.FUNCTION;
		}
		if (inBrackets.isUnknown()) {
			if (t == Type.FUNCTION) {
				inBrackets.setName(name);
			}
			inBrackets.setType(t);
			checkName(inBrackets);
			push(stack, inBrackets);
		} else {
			ASTNode fun = new ASTNode(t, container);
			if (t == Type.FUNCTION) {
				fun.setName(name);
			}
			if ((t == Type.FUNCTION_LOG) && (name.equalsIgnoreCase("log10"))) {
				fun.addChild(new ASTNode(10d, container));
			} else if ((t == Type.FUNCTION_ROOT)
					&& (name.equalsIgnoreCase("sqrt"))) {
				fun.addChild(new ASTNode(2d, container));
			}
			fun.addChild(inBrackets);
			push(stack, fun);
		}
	}

	/**
	 * Creates a new {@link ASTNode} and pushes this to the {@link Stack}. If
	 * the name can be parsed into a {@link Number}, the new {@link ASTNode}
	 * will contain this number. Otherwise, this method creates an
	 * {@link ASTNode} with a {@link String} that refers to the identifier of
	 * some other object in the {@link Model}.
	 * 
	 * @param stack
	 * @param name
	 * @param container
	 * @throws SBMLException
	 */
	private void pushValue(Stack<ASTNode> stack, String name,
			MathContainer container) throws SBMLException {
		if (strict && (container == null)) {
			throw new NullPointerException(MathContainer.class.getName());
		}
		ASTNode node = new ASTNode(container);
		if (name.matches(REAL_E + "-?[0-9]+") && name.length() > 1) {
			// split into mantissa and exponent
			String vals[] = name.toLowerCase().split("e");
			node.setValue(Double.parseDouble(vals[0]), Integer
					.parseInt(vals[1]));
		} else {
			// the String either represents a number or a reference
			try {
				Number number = Double.parseDouble(name);
				if (number.intValue() - number.doubleValue() == 0d) {
					node.setValue(number.intValue());
				} else {
					node.setValue(number.doubleValue());
				}
			} catch (NumberFormatException exc) {
				node.setName(name);
				checkName(node);
			}
		}
		push(stack, node);
	}

	/**
	 * 
	 * @param node
	 */
	private void rationalContraction(ASTNode node) {
		if ((node.getNumChildren() == 2) && (node.getType() == Type.DIVIDE)
				&& node.getLeftChild().isInteger()
				&& node.getRightChild().isInteger()) {
			node.setValue(node.getLeftChild().getInteger(), node
					.getRightChild().getInteger());
			node.getListOfNodes().clear();
		}
	}

	/**
	 * @param strict
	 *            the strict to set
	 */
	public void setStrict(boolean strict) {
		this.strict = strict;
	}

	/**
	 * 
	 * @param node
	 */
	private void uminusContraction(ASTNode node) {
		if (node.isUMinus()) {
			ASTNode child = node.getLeftChild();
			if (child.isNumber() || child.isInfinity() || child.isNegInfinity()) {
				if (child.isInteger()) {
					node.setValue(-child.getInteger());
				} else if (child.isRational()) {
					node
							.setValue(-child.getNumerator(), child
									.getDenominator());
				} else if (child.getType() == Type.REAL_E) {
					node.setValue(-child.getMantissa(), child.getExponent());
				} else if (child.isInfinity()) {
					node.setValue(Double.NEGATIVE_INFINITY);
				} else if (child.isNegInfinity()) {
					node.setValue(Double.POSITIVE_INFINITY);
				} else {
					node.setValue(-child.getReal());
				}
				node.getListOfNodes().clear();
			}
		}
	}
}
