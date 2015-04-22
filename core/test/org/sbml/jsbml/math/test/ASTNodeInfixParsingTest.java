/*
 * $Id: ASTNodeInfixParsingTest.java 2157 2015-03-25 14:00:27Z niko-rodrigue $
 * $URL: svn://svn.code.sf.net/p/jsbml/code/trunk/core/test/org/sbml/jsbml/math/test/ASTNodeInfixParsingTest.java $
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

package org.sbml.jsbml.math.test;

import static org.junit.Assert.assertTrue;

import java.io.StringReader;

import org.junit.BeforeClass;
import org.junit.Test;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.text.parser.FormulaParser;
import org.sbml.jsbml.text.parser.FormulaParserLL3;
import org.sbml.jsbml.text.parser.IFormulaParser;
import org.sbml.jsbml.text.parser.ParseException;
import org.sbml.jsbml.util.compilers.FormulaCompiler;
import org.sbml.jsbml.util.compilers.FormulaCompilerLibSBML;
import org.sbml.jsbml.util.compilers.LibSBMLFormulaCompiler;

/**
 * Tests related to {@link ASTNode#parseFormula(String)} and {@link ASTNode#parseFormula(String, IFormulaParser)}.
 * 
 * @author Nicolas Rodriguez
 * @version $Rev: 2157 $
 * @since 1.0
 */
public class ASTNodeInfixParsingTest {

  /**
   * 
   */
  final static FormulaParserLL3 caseSensitiveParser = new FormulaParserLL3(new StringReader(""));

  /**
   * 
   */
  final static FormulaParserLL3 l3Parser = caseSensitiveParser;

  /**
   * 
   */
  final static FormulaParser parser = new FormulaParser(new StringReader(""));

  /**
   * 
   */
  final static FormulaParserLL3 caseInsensitiveParser = new FormulaParserLL3(new StringReader(""));

  /**
   * 
   */
  final static FormulaCompiler l3Compiler = new FormulaCompilerLibSBML();
  
  /**
   * 
   */
  final static FormulaCompiler oldJsbmlCompiler = new FormulaCompiler();  

  /**
   * 
   */
  final static FormulaCompiler oldL1Compiler = new LibSBMLFormulaCompiler();  

  
  /**
   * 
   */
  @BeforeClass public static void init() {
    caseInsensitiveParser.setCaseSensitive(false);
  }

  /**
   * 
   */
  @Test public void caseSensitivityTests() {

    try {
      ASTNode n = ASTNode.parseFormula("Cos(x)", caseSensitiveParser);

      assertTrue(n.getType() != ASTNode.Type.FUNCTION_COS);
      assertTrue(n.getType() == ASTNode.Type.FUNCTION);
      assertTrue(n.getName().equals("Cos"));

      n = ASTNode.parseFormula("1 + Pi", caseSensitiveParser);

      assertTrue(n.getChild(1).getType() != ASTNode.Type.CONSTANT_PI);
      assertTrue(n.getChild(1).getType() == ASTNode.Type.NAME);
      assertTrue(n.getChild(1).getName().equals("Pi"));


    } catch (ParseException e) {
      // should never happen
      e.printStackTrace();
      assertTrue(false);
    }
  }

  /**
   * 
   */
  @Test public void caseInsensitivityTests() {

    try {
      ASTNode n = ASTNode.parseFormula("Cos(x)", caseInsensitiveParser);

      assertTrue(n.getType() == ASTNode.Type.FUNCTION_COS);
      assertTrue(n.isFunction() == true);
      assertTrue(n.isName() == false);
      assertTrue(n.getChildCount() == 1);
      assertTrue(n.getName() == null); // TODO - should the original String be conserved and accessible through getName()
      assertTrue(n.toFormula().equals("cos(x)"));

      n = ASTNode.parseFormula("COS(x)", caseInsensitiveParser);

      assertTrue(n.getType() == ASTNode.Type.FUNCTION_COS);
      assertTrue(n.toFormula().equals("cos(x)"));

      n = ASTNode.parseFormula("cos(x)", caseInsensitiveParser);

      assertTrue(n.getType() == ASTNode.Type.FUNCTION_COS);
      assertTrue(n.toFormula().equals("cos(x)"));

      n = ASTNode.parseFormula("coS(x)", caseInsensitiveParser);

      assertTrue(n.getType() == ASTNode.Type.FUNCTION_COS);
      assertTrue(n.toFormula().equals("cos(x)"));


      n = ASTNode.parseFormula("1 + Pi", caseInsensitiveParser);

      assertTrue(n.getChild(1).getType() == ASTNode.Type.CONSTANT_PI);
      assertTrue(n.getChild(1).getName() == null);  // TODO - should the original String be conserved and accessible through getName()
      assertTrue(n.toFormula().equals("1+pi"));


    } catch (ParseException e) {
      // should never happen
      e.printStackTrace();
      assertTrue(false);
    }
  }
  
  @Test public void factorialParsingTests() {

    try {
      // Parsing and compiling with the L3
      ASTNode n = ASTNode.parseFormula("factorial(n)", l3Parser);
      String formula = ASTNode.formulaToString(n, l3Compiler);
      n = ASTNode.parseFormula(formula, l3Parser); 

      
      // Parsing and compiling with the defaults
      n = ASTNode.parseFormula("factorial(n)");
      formula = ASTNode.formulaToString(n);
      System.out.println("default formula = " + formula);
      n = ASTNode.parseFormula(formula); 

      // n!
      n = ASTNode.parseFormula("n!", parser);
      formula = ASTNode.formulaToString(n);
      n = ASTNode.parseFormula(formula, parser); 

      // (n)!
      n = ASTNode.parseFormula("(n)!", parser);
      formula = ASTNode.formulaToString(n);
      n = ASTNode.parseFormula(formula, parser); 
      
      // (n + 1)!
      n = ASTNode.parseFormula("(n + 1)!", parser);
      formula = ASTNode.formulaToString(n, oldJsbmlCompiler);
      n = ASTNode.parseFormula(formula, parser); 

      try {
        n = ASTNode.parseFormula(formula, l3Parser);
        assertTrue("The L3 parser does not support '(n + 1)!' at the moment, so we expect an Exception.", false);
      } catch (ParseException e) {
        // should happen, the L3 parser does not support '(n + 1)!'
        assertTrue(true);        
      }
      
    } catch (ParseException e) {
      // should never happen
      e.printStackTrace();
      assertTrue(false);
    }
    
  }

  @Test public void andParsingTests() {

    try {
      // and(x, y)
      ASTNode n = ASTNode.parseFormula("and(x,y)", l3Parser);
      String formula = ASTNode.formulaToString(n, l3Compiler);
      System.out.println("formula 'and(x, y)' = " + formula);
      n = ASTNode.parseFormula(formula, l3Parser); 

      // x && y
      n = ASTNode.parseFormula("x && y", l3Parser);
      formula = ASTNode.formulaToString(n, l3Compiler);
      System.out.println("formula 'x && y' = " + formula);
      n = ASTNode.parseFormula(formula, l3Parser); 

      // x and y
      n = ASTNode.parseFormula("x and y", parser); // and(x,y) does not work ==> difference compared to the L1 supported syntax?
      formula = ASTNode.formulaToString(n);
      System.out.println("formula 'x and y)' = " + formula);
      n = ASTNode.parseFormula(formula, parser); 

      // and(x, y)
      n = ASTNode.parseFormula("and(x,y)", l3Parser);
      formula = ASTNode.formulaToString(n, oldL1Compiler);
      System.out.println("formula 'and(x, y)' = " + formula);
      n = ASTNode.parseFormula(formula, l3Parser);       
      
    } catch (ParseException e) {
      // should never happen
      e.printStackTrace();
      assertTrue(false);
    }
  }

}
