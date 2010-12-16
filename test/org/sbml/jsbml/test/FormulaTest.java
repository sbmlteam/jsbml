/*
 * $Id:  FormulaTest.java 10:33:22 draeger $
 * $URL: FormulaTest.java $
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
package org.sbml.jsbml.test;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.AssignmentRule;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.text.parser.ParseException;

/**
 * @author Andreas Dr&auml;ger
 * 
 */
public class FormulaTest {

	public FormulaTest() throws ParseException, SBMLException {
		String formulae[] = {
				"lambda(2)",
				"lambda(x, x+2)",
				"ln(2)/mdt",
				"exp(-mu*D)",
				"log(2)/mdt",
				//"-3 * -5",
				//"-3/5",
//				"-5 -4",
//				"-5 + 5-4",
//				"5 - 5-4",
//				"5 - (5-4)",
//				"-5 + 5+4",
//				"(-5+5) -6",
//				"(-5+5) - (6 -2)",
				"5 + 5 * 6 + 6",
				"(5 + 5) * (6 + 6)",
				//"ln(5)",
				//"5!",				
				// "7 * acos(53) + 7/8",
				// "-3 * (-5) + ((a + b)/(c + 3)+5)",
				// "-3 * (-5)",
				// "-3E-5 + -3*-5+(a+b)/(c+3)+5",
				// "23 + 52^2 - 3",
				// "a - log10(5)^11",
				// "pow(3,5)",
				// "pow(3 + 5,5)",
				// "23 + (52^2 - 3 + 5)/7 - 45",
				// "(y > x) == true",
				// "3 <= 5",
				// "3 == 5",
				// "a xor b",
				// "not c",
				// "not(c)",
				// "--3.5e7",
				// "10+ --3.5e7 *5",
				// "5/4 + 4/5*10",
				// "10 + -0.3E-5*10",
				// "-7.8",
				// "-infinity",
				// "ceil(-2.9) + 6 * -7.8",
				// "Vf*(A*B - P*Q/Keq)/(Kma + A*(1 + P/Kip) + (Vf/(Vr*Keq)) * Kmq*P + Kmp*Q + P*Q)",
				// "a*(b+c)*d/(e+3)*5",
				// "(a * (b + c) * d)/(e +  3) *   5",
				// "lambda(2)",
				// "lambda(x, x+2)",
				"1" };

		for (String formula : formulae) {
			ASTNode testNode = ASTNode.parseFormula(formula);

			System.out.println("===================");
			System.out.printf("[IN]:\t%s\n", formula);
			System.out.printf("[OUT]:\t%s\n", testNode.toFormula());
			System.out.printf("[LTX]:\t%s\n", testNode.toLaTeX());

			AssignmentRule as = new AssignmentRule(2, 4);
			Model m = new Model(2, 4);
			FunctionDefinition fd = new FunctionDefinition("f", 2, 4);
			m.addFunctionDefinition(fd);
			//as.setMath(ASTNode.parseFormula("f(a, b, c, d)"));
			//m.addRule(as);
			//System.out.println(as.getMath().toString());

		}

	}

	/**
	 * @param args
	 * @throws ParseException
	 * @throws SBMLException
	 */
	public static void main(String[] args) throws ParseException, SBMLException {
		new FormulaTest();
	}

}
