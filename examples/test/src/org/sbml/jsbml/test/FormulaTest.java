/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2014 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 5. The Babraham Institute, Cambridge, UK
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.test;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.AssignmentRule;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.math.parser.ParseException;

/**
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * @version $Rev$
 */
public class FormulaTest {

  public FormulaTest() throws ParseException, SBMLException {
    String formulae[] = {
      "lambda(2)",
      "lambda(x, x+2)",
      "ln(2)/mdt",
      "exp(-mu*D)",
      "log(2)/mdt",
      "a_b__3_c + 5",
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
      "-infinIty",
      "inf",
      "nan",
      "NaN",
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
      m.addRule(as);
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
