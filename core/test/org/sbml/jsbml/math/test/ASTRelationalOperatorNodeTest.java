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
 * 6. The University of Toronto, Toronto, ON, Canada
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.math.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.math.ASTBoolean;
import org.sbml.jsbml.math.ASTCnIntegerNode;
import org.sbml.jsbml.math.ASTFactory;
import org.sbml.jsbml.math.ASTRelationalOperatorNode;

/**
 * Test cases for {@link ASTRelationalOperatorNode}
 * 
 * @author Victor Kofia
 * @since 1.0
 */
public class ASTRelationalOperatorNodeTest {

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTRelationalOperatorNode#clone()}.
   */
  @Test
  public final void testClone() {
    ASTRelationalOperatorNode operator = new ASTRelationalOperatorNode();
    ASTRelationalOperatorNode unknown = operator.clone();
    assertTrue(operator.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTRelationalOperatorNode#clone()}.
   */
  @Test
  public final void testCloneWithChildren() {
    ASTRelationalOperatorNode operator = new ASTRelationalOperatorNode(Type.RELATIONAL_EQ);
    operator.addChild(new ASTCnIntegerNode(1));
    operator.addChild(new ASTCnIntegerNode(4));
    ASTRelationalOperatorNode unknown = operator.clone();
    assertTrue(operator.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTRelationalOperatorNode#ASTRelationalOperatorNode(org.sbml.jsbml.math.ASTRelationalOperatorNode)}.
   */
  @Test
  public final void testCloneWithConstructor() {
    ASTRelationalOperatorNode operator = new ASTRelationalOperatorNode();
    ASTRelationalOperatorNode unknown = new ASTRelationalOperatorNode(operator);
    assertTrue(operator.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTRelationalOperatorNode#clone()}.
   */
  @Test
  public final void testCloneWithType() {
    ASTRelationalOperatorNode operator = new ASTRelationalOperatorNode(Type.RELATIONAL_EQ);
    ASTRelationalOperatorNode unknown = operator.clone();
    assertTrue(operator.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTRelationalOperatorNode#isAllowableType(Type)}.
   */
  @Test
  public final void testIsAllowableType() {
    ASTRelationalOperatorNode operator = new ASTRelationalOperatorNode();
    assertTrue(operator.isAllowableType(Type.RELATIONAL_EQ) && operator.isAllowableType(Type.RELATIONAL_GEQ)
      && operator.isAllowableType(Type.RELATIONAL_GT) && operator.isAllowableType(Type.RELATIONAL_LEQ)
      && operator.isAllowableType(Type.RELATIONAL_LT) && operator.isAllowableType(Type.RELATIONAL_NEQ)
      && ! operator.isAllowableType(null));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTRelationalOperatorNode#toFormula()}.
   */
  @Test
  public final void testToFormulaEq() {
    ASTRelationalOperatorNode eq = new ASTRelationalOperatorNode(Type.RELATIONAL_EQ);
    eq.addChild(new ASTBoolean(Type.CONSTANT_TRUE));
    eq.addChild(new ASTBoolean(Type.CONSTANT_FALSE));
    assertTrue(eq.toFormula().equals("true == false"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTRelationalOperatorNode#toFormula()}.
   */
  @Test
  public final void testToFormulaGeq() {
    ASTRelationalOperatorNode geq = new ASTRelationalOperatorNode(Type.RELATIONAL_GEQ);
    geq.addChild(new ASTBoolean(Type.CONSTANT_TRUE));
    geq.addChild(new ASTBoolean(Type.CONSTANT_FALSE));
    assertTrue(geq.toFormula().equals("true >= false"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTRelationalOperatorNode#toFormula()}.
   */
  @Test
  public final void testToFormulaGt() {
    ASTRelationalOperatorNode gt = new ASTRelationalOperatorNode(Type.RELATIONAL_GT);
    gt.addChild(new ASTBoolean(Type.CONSTANT_TRUE));
    gt.addChild(new ASTBoolean(Type.CONSTANT_FALSE));
    assertTrue(gt.toFormula().equals("true > false"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTRelationalOperatorNode#toFormula()}.
   */
  @Test
  public final void testToFormulaLeq() {
    ASTRelationalOperatorNode leq = new ASTRelationalOperatorNode(Type.RELATIONAL_LEQ);
    leq.addChild(new ASTBoolean(Type.CONSTANT_TRUE));
    leq.addChild(new ASTBoolean(Type.CONSTANT_FALSE));
    assertTrue(leq.toFormula().equals("true <= false"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTRelationalOperatorNode#toFormula()}.
   */
  @Test
  public final void testToFormulaLt() {
    ASTRelationalOperatorNode lt = new ASTRelationalOperatorNode(Type.RELATIONAL_LT);
    lt.addChild(new ASTBoolean(Type.CONSTANT_TRUE));
    lt.addChild(new ASTBoolean(Type.CONSTANT_FALSE));
    assertTrue(lt.toFormula().equals("true < false"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTRelationalOperatorNode#toFormula()}.
   */
  @Test
  public final void testToFormulaNeq() {
    ASTRelationalOperatorNode neq = new ASTRelationalOperatorNode(Type.RELATIONAL_NEQ);
    neq.addChild(new ASTBoolean(Type.CONSTANT_TRUE));
    neq.addChild(new ASTBoolean(Type.CONSTANT_FALSE));
    assertTrue(neq.toFormula().equals("true != false"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTRelationalOperatorNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeXEq() {
    ASTRelationalOperatorNode eq = new ASTRelationalOperatorNode(Type.RELATIONAL_EQ);
    eq.addChild(new ASTBoolean(Type.CONSTANT_TRUE));
    eq.addChild(new ASTBoolean(Type.CONSTANT_FALSE));
    assertTrue(eq.toLaTeX().equals("\\mathrm{true} = \\mathrm{false}"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTRelationalOperatorNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeXGeq() {
    ASTRelationalOperatorNode geq = new ASTRelationalOperatorNode(Type.RELATIONAL_GEQ);
    geq.addChild(new ASTBoolean(Type.CONSTANT_TRUE));
    geq.addChild(new ASTBoolean(Type.CONSTANT_FALSE));
    assertTrue(geq.toLaTeX().equals("\\mathrm{true} \\geq \\mathrm{false}"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTRelationalOperatorNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeXGt() {
    ASTRelationalOperatorNode gt = new ASTRelationalOperatorNode(Type.RELATIONAL_GT);
    gt.addChild(new ASTBoolean(Type.CONSTANT_TRUE));
    gt.addChild(new ASTBoolean(Type.CONSTANT_FALSE));
    assertTrue(gt.toLaTeX().equals("\\mathrm{true} > \\mathrm{false}"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTRelationalOperatorNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeXLeq() {
    ASTRelationalOperatorNode leq = new ASTRelationalOperatorNode(Type.RELATIONAL_LEQ);
    leq.addChild(new ASTBoolean(Type.CONSTANT_TRUE));
    leq.addChild(new ASTBoolean(Type.CONSTANT_FALSE));
    assertTrue(leq.toLaTeX().equals("\\mathrm{true} \\leq \\mathrm{false}"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTRelationalOperatorNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeXLt() {
    ASTRelationalOperatorNode lt = new ASTRelationalOperatorNode(Type.RELATIONAL_LT);
    lt.addChild(new ASTBoolean(Type.CONSTANT_TRUE));
    lt.addChild(new ASTBoolean(Type.CONSTANT_FALSE));
    assertTrue(lt.toLaTeX().equals("\\mathrm{true} < \\mathrm{false}"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTRelationalOperatorNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeXNeq() {
    ASTRelationalOperatorNode neq = new ASTRelationalOperatorNode(Type.RELATIONAL_NEQ);
    neq.addChild(new ASTBoolean(Type.CONSTANT_TRUE));
    neq.addChild(new ASTBoolean(Type.CONSTANT_FALSE));
    assertTrue(neq.toLaTeX().equals("\\mathrm{true} \\neq \\mathrm{false}"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTRelationalOperatorNode#toMathML()}.
   */
  @Test
  public final void testToMathMLEq() {
    ASTRelationalOperatorNode eq = new ASTRelationalOperatorNode(Type.RELATIONAL_EQ);
    eq.addChild(new ASTBoolean(Type.CONSTANT_TRUE));
    eq.addChild(new ASTBoolean(Type.CONSTANT_FALSE));
    assertTrue(eq.toMathML().equals(ASTFactory.parseMathML("eq.xml")));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTRelationalOperatorNode#toMathML()}.
   */
  @Test
  public final void testToMathMLGeq() {
    ASTRelationalOperatorNode geq = new ASTRelationalOperatorNode(Type.RELATIONAL_GEQ);
    geq.addChild(new ASTBoolean(Type.CONSTANT_TRUE));
    geq.addChild(new ASTBoolean(Type.CONSTANT_FALSE));
    assertTrue(geq.toMathML().equals(ASTFactory.parseMathML("geq.xml")));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTRelationalOperatorNode#toMathML()}.
   */
  @Test
  public final void testToMathMLGt() {
    ASTRelationalOperatorNode gt = new ASTRelationalOperatorNode(Type.RELATIONAL_GT);
    gt.addChild(new ASTBoolean(Type.CONSTANT_TRUE));
    gt.addChild(new ASTBoolean(Type.CONSTANT_FALSE));
    assertTrue(gt.toMathML().equals(ASTFactory.parseMathML("gt.xml")));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTRelationalOperatorNode#toMathML()}.
   */
  @Test
  public final void testToMathMLLeq() {
    ASTRelationalOperatorNode leq = new ASTRelationalOperatorNode(Type.RELATIONAL_LEQ);
    leq.addChild(new ASTBoolean(Type.CONSTANT_TRUE));
    leq.addChild(new ASTBoolean(Type.CONSTANT_FALSE));
    assertTrue(leq.toMathML().equals(ASTFactory.parseMathML("leq.xml")));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTRelationalOperatorNode#toMathML()}.
   */
  @Test
  public final void testToMathMLLt() {
    ASTRelationalOperatorNode lt = new ASTRelationalOperatorNode(Type.RELATIONAL_LT);
    lt.addChild(new ASTBoolean(Type.CONSTANT_TRUE));
    lt.addChild(new ASTBoolean(Type.CONSTANT_FALSE));
    assertTrue(lt.toMathML().equals(ASTFactory.parseMathML("lt.xml")));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTRelationalOperatorNode#toMathML()}.
   */
  @Test
  public final void testToMathMLNeq() {
    ASTRelationalOperatorNode neq = new ASTRelationalOperatorNode(Type.RELATIONAL_NEQ);
    neq.addChild(new ASTBoolean(Type.CONSTANT_TRUE));
    neq.addChild(new ASTBoolean(Type.CONSTANT_FALSE));
    assertTrue(neq.toMathML().equals(ASTFactory.parseMathML("neq.xml")));
  }

}
