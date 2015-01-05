/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2015 jointly by the following organizations:
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
package org.sbml.jsbml.xml.test;

import java.io.StringReader;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.JSBML;
import org.sbml.jsbml.text.parser.FormulaParserLL3;
import org.sbml.jsbml.text.parser.IFormulaParser;

/**
 * 
 * @author Nicolas Rodriguez
 * @version $Rev$
 * @since 1.0
 * @date 17.10.2013
 */
public class FormulaParserLL3Test {

  /**
   * 
   * @param args
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {

    IFormulaParser parser = new FormulaParserLL3(new StringReader(""));

    ASTNode node = ASTNode.parseFormula("true || false", parser);

    System.out.println(node.toFormula());

    node = ASTNode.parseFormula("true && false", parser);
    System.out.println(node.toFormula());

    node = ASTNode.parseFormula("and(true,false)", parser);
    System.out.println(node.toFormula());

    node = ASTNode.parseFormula("! true", parser);
    System.out.println(node.toFormula());

    node = ASTNode.parseFormula("not(true)", parser);
    System.out.println(node.toFormula());

    node = ASTNode.parseFormula("NOT(true)", parser);
    System.out.println(node.toFormula());

    node = ASTNode.parseFormula("NOT true", parser);
    System.out.println(node.toFormula());

    //		node = ASTNode.parseFormula("true and false", parser); 	 // Not supported anymore by the new parser
    //		System.out.println(node.toFormula());

    //		node = ASTNode.parseFormula("not true", parser); // Not supported anymore by the new parser
    //		System.out.println(node.toFormula());

    node = ASTNode.parseFormula("and(or(gt(x,2), lt(S1, 4)),and(x >= 2, (S1 AND true) || (true && true)))", parser);
    System.out.println(node.toFormula());

    //		node = ASTNode.parseFormula("4!", parser);  // Not supported anymore by the new parser
    //		System.out.println(node.toFormula());

    node = ASTNode.parseFormula("selector(S, 1)", parser);
    System.out.println(node.toFormula());

    node = ASTNode.parseFormula("selector(S, 1, 5)", parser);
    System.out.println(node.toFormula());

    String mathMLSelector = node.toMathML();

    System.out.println(mathMLSelector);

    node = JSBML.readMathMLFromString(mathMLSelector);

    System.out.println(node.toFormula());

    mathMLSelector = node.toMathML();
    System.out.println(mathMLSelector);

    node = ASTNode.parseFormula("plus(1, 2, 3, 4)", parser);
    System.out.println(node.toFormula());

    node = ASTNode.parseFormula("minus(1, 2, 3, 4)", parser);
    System.out.println(node.toFormula());

    node = ASTNode.parseFormula("times(1, 2, 3, 4)", parser);
    System.out.println(node.toFormula());

    node = ASTNode.parseFormula("divide(2, 4)", parser);
    System.out.println(node.toFormula());

    node = ASTNode.parseFormula("modulo(13, 5)", parser);
    System.out.println(node.toFormula());
    System.out.println(node.toMathML());

    node = ASTNode.parseFormula("18 % 5", parser);
    System.out.println(node.toFormula());
  }

}
