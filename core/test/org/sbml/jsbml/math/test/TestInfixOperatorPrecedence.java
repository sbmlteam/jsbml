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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.text.parser.FormulaParserLL3;
import org.sbml.jsbml.text.parser.ParseException;
import org.sbml.jsbml.util.compilers.FormulaCompiler;

/**
 * Tests in order to insure that the operator precedence is respected when parsing
 * an infix formula into an ASTNode.
 *
 * @see FormulaParserLL3
 * @see FormulaCompiler
 * @author Nicolas Rodriguez
 * @since 1.0
 */
public class TestInfixOperatorPrecedence {

  /**
   * @throws Exception - never
   */
  @Before public void setUp() throws Exception
  {
  }

  /**
   * @throws Exception - never
   */
  @After public void tearDown() throws Exception
  {
  }


  /**
   *
   */
  @Test public void testParseLogicalOperator() {


    ASTNode n = null;
    try {
      n = ASTNode.parseFormula("x + 1 && 2 || y * 2 ");
    } catch (ParseException e) {
      e.printStackTrace();
      assertTrue(false);
    }

    System.out.println(n.toMathML());
    System.out.println(n.toFormula());
    assertTrue(n.toFormula().equals("((x+1) && 2) || (y*2)"));

    try {
      n = ASTNode.parseFormula("V0 > 3 && V1 > 3");
    } catch (ParseException e) {
      e.printStackTrace();
      assertTrue(false);
    }

    System.out.println(n.toMathML());
    System.out.println(n.toFormula());

    assertTrue(n.toFormula().equals("(V0 > 3) && (V1 > 3)")); // L3Compiler output: (gt(V0, 3)) && (gt(V1, 3))
  }

  /**
   *
   */
  @Test public void testPlus() {


    ASTNode n = null;
    try {
      n = ASTNode.parseFormula("5 + 2 * 4 + 4 * 8 ");
    } catch (ParseException e) {
      e.printStackTrace();
      assertTrue(false);
    }

    // TODO: Test by comparing nodes in the tree
    System.out.println(n.toMathML());
    System.out.println(n.toFormula());
    // we are doing two tests, to be able to pass the test if we use the new ASTNode class or the old one.
    assertTrue(n.toFormula().equals("5+(2*4)+(4*8)") || n.toFormula().equals("5+2*4+4*8"));
  }

  /**
   *
   */
  @Test public void testPower() {


    ASTNode n = null;
    try {
      n = ASTNode.parseFormula("5+1^(-1)");
    } catch (ParseException e) {
      e.printStackTrace();
      assertTrue(false);
    }

    System.out.println(n.toMathML());
    System.out.println(n.toFormula());

    try {
      n = ASTNode.parseFormula("5+1^(-1 * 2)");
    } catch (ParseException e) {
      e.printStackTrace();
      assertTrue(false);
    }

    System.out.println(n.toMathML());
    System.out.println(n.toFormula());

    try {
      n = ASTNode.parseFormula("32 / 2 ^ 4 * 1e2");
    } catch (ParseException e) {
      e.printStackTrace();
      assertTrue(false);
    }

    System.out.println(n.toMathML());
    System.out.println(n.toFormula());
    assertTrue(n.toFormula().equals("32/2^4*1E2"));

    try {
      n = ASTNode.parseFormula("x^8%3");
    } catch (ParseException e) {
      e.printStackTrace();
      assertTrue(false);
    }

    System.out.println(n.toMathML());
    System.out.println(n.toFormula());

    // we are doing two tests, to be able to pass the test if we use the new ASTNode class or the old one.
    // TODO - restore the test when we output modulo without the full piecewise - assertTrue(n.toFormula().equals("piecewise(floor(x^8/3), (x^8/3) > 0, ceil(x^8/3))") ||  n.toFormula().equals("piecewise(floor(x^8/3), x^8/3 > 0, ceil(x^8/3))")); // L3Compiler output: piecewise(floor(x^8/3), gt(x^8/3, 0), ceil(x^8/3))
  }

  /**
   *
   */
  @Test public void testModulo() {


    ASTNode n = null;
    try {
      n = ASTNode.parseFormula("5 + 1 + 8 % 3 + 2");
    } catch (ParseException e) {
      e.printStackTrace();
      assertTrue(false);
    }

    System.out.println(n.toMathML());
    System.out.println(n.toFormula());

    // we are doing two tests, to be able to pass the test if we use the new ASTNode class or the old one.
    // TODO - restore the test when we output modulo without the full piecewise - assertTrue(n.toFormula().equals("5+1+piecewise(floor(8/3), (8/3) > 0, ceil(8/3))+2") || n.toFormula().equals("5+1+piecewise(floor(8/3), 8/3 > 0, ceil(8/3))+2"));
  }
}
