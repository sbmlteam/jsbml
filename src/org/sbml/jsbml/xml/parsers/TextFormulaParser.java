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

import java.util.LinkedList;
import java.util.Stack;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.util.ValuePair;
import org.sbml.jsbml.util.compilers.LaTeX;

/**
 * A parser for SBML Level 1 text formulas that creates {@link ASTNode} objects
 * for easier computational treatment.
 * 
 * @author Andreas Dr&auml;ger
 * @date 2010-09-01
 */
public class TextFormulaParser {

	/**
	 * 
	 */
	public TextFormulaParser() {
	}

	/**
	 * 
	 * @param formula
	 * @return
	 * @throws SBMLException
	 */
	public ASTNode parse(String formula) throws SBMLException {
		return parse(null, formula);
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
		ASTNode root = new ASTNode(container);
		// get rid of white spaces
		String f = removeWhiteSpaces(formula);
		System.out.println(formula);
		System.out.println(f);

		// Stores the beginning and the end of a pair of brackets.
		Stack<Integer> bracketsOpen = new Stack<Integer>();
		LinkedList<ValuePair<Integer, Integer>> list = new LinkedList<ValuePair<Integer, Integer>>();
		for (int i = 0; i < f.length(); i++) {
			switch (f.charAt(i)) {
			case '(':
				bracketsOpen.push(Integer.valueOf(i));
				break;
			case ')':
				list.add(new ValuePair<Integer, Integer>(bracketsOpen.pop(),
						Integer.valueOf(i)));
				break;
			default:
				break;
			}
		}
		System.out.println();
		while (!list.isEmpty()) {
			ValuePair<Integer, Integer> curr = list.removeFirst();
			String snippet = f.substring(curr.getA().intValue() + 1, curr
					.getB().intValue());
			System.out.println(curr + "\t" + snippet);
			System.out.println(parseSnippet(container, snippet).compile(
					new LaTeX()).toString());
		}
		System.out.println();

		return root;
	}

	/**
	 * 
	 * @param formula
	 * @return
	 */
	private String removeWhiteSpaces(String formula) {
		StringBuilder sb = new StringBuilder();
		char c;
		for (int i=0; i<formula.length(); i++) {
			c = formula.charAt(i);
			if ((c != ' ') && (c != '\t')) {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * Parses a given formula that does not contain any brackets. This is the
	 * actual parsing method.
	 * 
	 * @param container
	 * @param formula
	 * @return
	 */
	private ASTNode parseSnippet(MathContainer container, String formula) {
		ASTNode root = new ASTNode(container);

		StringBuilder curr = new StringBuilder();
		char c;
		for (int i = 0; i < formula.length(); i++) {
			c = formula.charAt(i);
			if (isArithmeticOperator(c)) {
				root.setType(getTypeFor(c));
			} else {
				curr.append(c);
			}
		}

		return root;
	}

	private ASTNode.Type getTypeFor(char c) {
		switch (c) {
		case '-':
			return Type.MINUS;
		case '^':
			return Type.POWER;
		case '*':
			return Type.TIMES;
		case '/':
			return Type.DIVIDE;
		case '+':
			return Type.PLUS;
		case 'e':
			return Type.CONSTANT_E;
		default:
			return null;
		}
	}

	private boolean isArithmeticOperator(char c) {
		switch (c) {
		case '+':
		case '-':
		case '*':
		case '/':
			return true;
		default:
			return false;
		}
	}

	/**
	 * For tests
	 * 
	 * @param args
	 * @throws SBMLException
	 */
	public static void main(String args[]) throws SBMLException {
		TextFormulaParser parser = new TextFormulaParser();
		String formula = "(a * (b + c) * d)/(e +  3) *   5";
		// "Vf*(A*B - P*Q/Keq)/(Kma + A*(1 + P/Kip) + (Vf/(Vr*Keq)) * Kmq*P + Kmp*Q + P*Q)";
		System.out.println(parser.parse(formula).compile(new LaTeX()));
	}

}
