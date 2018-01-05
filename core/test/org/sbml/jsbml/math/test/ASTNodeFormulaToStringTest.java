/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2018 jointly by the following organizations:
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

package org.sbml.jsbml.math.test;

import static org.junit.Assert.assertTrue;

import java.io.StringReader;

import org.junit.BeforeClass;
import org.junit.Test;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.text.parser.FormulaParser;
import org.sbml.jsbml.text.parser.FormulaParserLL3;
import org.sbml.jsbml.text.parser.ParseException;
import org.sbml.jsbml.util.compilers.FormulaCompiler;
import org.sbml.jsbml.util.compilers.FormulaCompilerLibSBML;
import org.sbml.jsbml.util.compilers.LibSBMLFormulaCompiler;

/**
 * Tests related to {@link ASTNode#toFormula()} and {@link ASTNode#toFormula(FormulaCompiler)}.
 * 
 * @author Nicolas Rodriguez
 * @since 1.0
 */
public class ASTNodeFormulaToStringTest {

  /**
   * 
   */
  final static FormulaCompiler oldFormulaCompiler = new FormulaCompiler();
  /**
   * 
   */
  final static FormulaCompiler formulaCompilerLibsbml = new FormulaCompilerLibSBML();
  
  /**
   * 
   */
  final static FormulaCompiler formulaCompilerOldLibsbml = new LibSBMLFormulaCompiler();
  

  /**
   * 
   */
  static ASTNode relationalAnd;
  /**
   * 
   */
  static ASTNode relationalAnd2;
  /**
   * 
   */
  static ASTNode relationalAnd3;
  /**
   * 
   */
  static ASTNode logicalEq;
  /**
   * 
   */
  static ASTNode simplePlus;

  /**
   * 
   */
  @BeforeClass public static void init() {
    try {
      relationalAnd = ASTNode.parseFormula("x and y", new FormulaParser(new StringReader("")));
      relationalAnd2 = ASTNode.parseFormula("x && y", new FormulaParserLL3(new StringReader("")));
      relationalAnd3 = ASTNode.parseFormula("and(x, y)", new FormulaParserLL3(new StringReader("")));
      logicalEq = ASTNode.parseFormula("x == y");
      simplePlus = ASTNode.parseFormula("x + y");

    } catch (ParseException e) {
      e.printStackTrace();
      assertTrue(false);
    }
  }

  /**
   * 
   */
  @Test public void relationalOperatorTests() {

    String formula = relationalAnd.toFormula();

    assertTrue(formula.equals("x && y"));

    formula = relationalAnd.toFormula(formulaCompilerLibsbml);

    assertTrue(formula.equals("x && y"));

    formula = relationalAnd.toFormula(oldFormulaCompiler);

    assertTrue(formula.equals("x && y"));

    formula = relationalAnd.toFormula(formulaCompilerOldLibsbml);
    
    assertTrue(formula.equals("and(x, y)"));    
    
  }

  /**
   * 
   */
  @Test public void relationalOperator2Tests() {

    String formula = relationalAnd2.toFormula();

    assertTrue(formula.equals("x && y"));

    formula = relationalAnd2.toFormula(formulaCompilerLibsbml);

    assertTrue(formula.equals("x && y"));

    formula = relationalAnd2.toFormula(oldFormulaCompiler);

    assertTrue(formula.equals("x && y"));
  }

  /**
   * 
   */
  @Test public void relationalOperator3Tests() {

    String formula = relationalAnd3.toFormula();

    assertTrue(formula.equals("x && y"));

    formula = relationalAnd3.toFormula(formulaCompilerLibsbml);

    assertTrue(formula.equals("x && y"));

    formula = relationalAnd3.toFormula(oldFormulaCompiler);

    assertTrue(formula.equals("x && y"));
  }


  /**
   * 
   */
  @Test public void logicalOperatorTests() {

    String formula = logicalEq.toFormula();

    assertTrue(formula.equals("x == y"));

    formula = logicalEq.toFormula(formulaCompilerLibsbml);

    assertTrue(formula.equals("x == y"));

    formula = logicalEq.toFormula(oldFormulaCompiler);

    assertTrue(formula.equals("x == y"));

    formula = logicalEq.toFormula(formulaCompilerOldLibsbml);
  
    assertTrue(formula.equals("eq(x, y)"));
}

  /**
   * 
   */
  @Test public void simplePlusTests() {

    String formula = simplePlus.toFormula();

    assertTrue(formula.equals("x+y"));

    formula = simplePlus.toFormula(formulaCompilerLibsbml);

    assertTrue(formula.equals("x+y")); // TODO - add a space for this compiler ??!

    formula = simplePlus.toFormula(oldFormulaCompiler);

    assertTrue(formula.equals("x+y"));
  }


}
