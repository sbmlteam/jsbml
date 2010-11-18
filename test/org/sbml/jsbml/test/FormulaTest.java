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
import org.sbml.jsbml.JSBML;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.text.parser.ParseException;

/**
 * @author Andreas Dr&auml;ger
 * 
 */
public class FormulaTest {

	public FormulaTest() throws ParseException, SBMLException {
		String formulae[] = { "7 * acos(53) + 7/8",
				"-3 * (-5) + ((a + b)/(c + 3)+5)",
				"-3E-5 + -3*-5+(a+b)/(c+3)+5 " };

		for (String formula : formulae) {
			ASTNode testNode = JSBML.parseFormula(formula);

			System.out.println("===================");
			System.out.printf("[IN]:\t%s\n", formula);
			System.out.printf("[OUT]:\t%s\n", testNode.toFormula());
			System.out.printf("[LTX]:\t%s\n", testNode.toLaTeX());
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
