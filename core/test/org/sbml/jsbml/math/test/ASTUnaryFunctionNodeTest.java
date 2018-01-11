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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.math.ASTCnIntegerNode;
import org.sbml.jsbml.math.ASTFactory;
import org.sbml.jsbml.math.ASTUnaryFunctionNode;

/**
 * Test cases for {@link ASTUnaryFunctionNode}
 * 
 * @author Victor Kofia
 * @since 1.0
 */
public class ASTUnaryFunctionNodeTest {

  /**
   * 
   */
  @Rule
  public ExpectedException exception = ExpectedException.none();

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTUnaryFunctionNode#addChild(org.sbml.jsbml.math.ASTNode2)}.
   */
  @Test
  public final void testAddChild() {
    ASTCnIntegerNode one = new ASTCnIntegerNode(1);
    ASTUnaryFunctionNode unary = new ASTUnaryFunctionNode();
    unary.addChild(one);
    assertTrue(unary.getChild().equals(one));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTUnaryFunctionNode#addChild(org.sbml.jsbml.math.ASTNode2)}.
   */
  @Test
  public final void testAddChildNotStrict() {
    ASTCnIntegerNode one = new ASTCnIntegerNode(1);
    ASTCnIntegerNode two = new ASTCnIntegerNode(2);
    ASTUnaryFunctionNode unary = new ASTUnaryFunctionNode();
    unary.setStrictness(false);
    unary.addChild(one);
    unary.addChild(two);
    assertTrue(unary.getChildAt(1).equals(two));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTUnaryFunctionNode#addChild(org.sbml.jsbml.math.ASTNode2)}.
   */
  @Test
  public final void testAddChildStrict() {
    ASTCnIntegerNode one = new ASTCnIntegerNode(1);
    ASTCnIntegerNode two = new ASTCnIntegerNode(2);
    ASTUnaryFunctionNode unary = new ASTUnaryFunctionNode();
    unary.addChild(one);
    exception.expect(IndexOutOfBoundsException.class);
    unary.addChild(two);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTUnaryFunctionNode#getChild()}.
   */
  @Test
  public final void testClone() {
    ASTUnaryFunctionNode unary = new ASTUnaryFunctionNode();
    ASTUnaryFunctionNode unknown = unary.clone();
    assertTrue(unary.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTUnaryFunctionNode#ASTUnaryFunctionNode(org.sbml.jsbml.math.ASTUnaryFunctionNode)}.
   */
  @Test
  public final void testCloneWithConstructor() {
    ASTUnaryFunctionNode unary = new ASTUnaryFunctionNode();
    ASTUnaryFunctionNode unknown = new ASTUnaryFunctionNode(unary);
    assertTrue(unary.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTUnaryFunctionNode#getChild()}.
   */
  @Test
  public final void testGetChild() {
    ASTCnIntegerNode one = new ASTCnIntegerNode(1);
    ASTUnaryFunctionNode unary = new ASTUnaryFunctionNode();
    unary.setChild(one);
    assertTrue(unary.getChild().equals(one));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTUnaryFunctionNode#getChildAt(int)}.
   */
  @Test
  public final void testGetChildAt() {
    ASTCnIntegerNode one = new ASTCnIntegerNode(1);
    ASTUnaryFunctionNode unary = new ASTUnaryFunctionNode();
    unary.addChild(one);
    assertTrue(unary.getChildAt(0).equals(one));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTUnaryFunctionNode#getChildAt(int)}.
   */
  @Test
  public final void testGetChildAtNonExistent() {
    ASTUnaryFunctionNode unary = new ASTUnaryFunctionNode();
    exception.expect(IndexOutOfBoundsException.class);
    unary.getChildAt(0);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTUnaryFunctionNode#getChildAt(int)}.
   */
  @Test
  public final void testGetChildAtNotStrict() {
    ASTCnIntegerNode one = new ASTCnIntegerNode(1);
    ASTCnIntegerNode two = new ASTCnIntegerNode(2);
    ASTUnaryFunctionNode unary = new ASTUnaryFunctionNode();
    unary.setStrictness(false);
    unary.addChild(one);
    unary.addChild(two);
    assertTrue(unary.getChildAt(1).equals(two));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTUnaryFunctionNode#getChildAt(int)}.
   */
  @Test
  public final void testGetChildAtStrict() {
    ASTCnIntegerNode one = new ASTCnIntegerNode(1);
    ASTUnaryFunctionNode unary = new ASTUnaryFunctionNode();
    unary.addChild(one);
    exception.expect(IndexOutOfBoundsException.class);
    unary.getChildAt(1);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTUnaryFunctionNode#getChild()}.
   */
  @Test
  public final void testGetChildNonExistent() {
    ASTUnaryFunctionNode unary = new ASTUnaryFunctionNode();
    exception.expect(IndexOutOfBoundsException.class);
    unary.getChild();
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTUnaryFunctionNode#insertChild(int, org.sbml.jsbml.math.ASTNode2)}.
   */
  @Test
  public final void testInsertChild() {
    ASTCnIntegerNode one = new ASTCnIntegerNode(1);
    ASTUnaryFunctionNode unary = new ASTUnaryFunctionNode();
    unary.insertChild(0, one);
    assertTrue(unary.getChild().equals(one));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTUnaryFunctionNode#insertChild(int, org.sbml.jsbml.math.ASTNode2)}.
   */
  @Test
  public final void testInsertChildNotStrict() {
    ASTCnIntegerNode one = new ASTCnIntegerNode(1);
    ASTCnIntegerNode two = new ASTCnIntegerNode(2);
    ASTUnaryFunctionNode unary = new ASTUnaryFunctionNode();
    unary.setStrictness(false);
    unary.insertChild(0, one);
    unary.insertChild(1, two);
    assertTrue(unary.getChildAt(1).equals(two));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTUnaryFunctionNode#insertChild(int, org.sbml.jsbml.math.ASTNode2)}.
   */
  @Test
  public final void testInsertChildStrict() {
    ASTCnIntegerNode one = new ASTCnIntegerNode(1);
    ASTCnIntegerNode two = new ASTCnIntegerNode(2);
    ASTUnaryFunctionNode unary = new ASTUnaryFunctionNode();
    unary.insertChild(0, one);
    exception.expect(IndexOutOfBoundsException.class);
    unary.insertChild(1, two);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTUnaryFunctionNode#isAllowableType(Type)}.
   */
  @Test
  public final void testIsAllowableType() {
    ASTUnaryFunctionNode A = new ASTUnaryFunctionNode();
    assertTrue(A.isAllowableType(Type.FUNCTION_CEILING) && A.isAllowableType(Type.FUNCTION_FLOOR)
      && A.isAllowableType(Type.FUNCTION_ABS) && A.isAllowableType(Type.FUNCTION_EXP)
      && !A.isAllowableType(null));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTUnaryFunctionNode#prependChild(org.sbml.jsbml.math.ASTNode2)}.
   */
  @Test
  public final void testPrependChild() {
    ASTCnIntegerNode one = new ASTCnIntegerNode(1);
    ASTUnaryFunctionNode unary = new ASTUnaryFunctionNode();
    unary.prependChild(one);
    assertTrue(unary.getChildAt(0).equals(one));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTUnaryFunctionNode#prependChild(org.sbml.jsbml.math.ASTNode2)}.
   */
  @Test
  public final void testPrependChildNotStrict() {
    ASTCnIntegerNode one = new ASTCnIntegerNode(1);
    ASTCnIntegerNode two = new ASTCnIntegerNode(2);
    ASTUnaryFunctionNode unary = new ASTUnaryFunctionNode();
    unary.setStrictness(false);
    unary.prependChild(one);
    unary.prependChild(two);
    assertTrue(unary.getChildAt(0).equals(two) && unary.getChildAt(1).equals(one));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTUnaryFunctionNode#prependChild(org.sbml.jsbml.math.ASTNode2)}.
   */
  @Test
  public final void testPrependChildStrict() {
    ASTCnIntegerNode one = new ASTCnIntegerNode(1);
    ASTCnIntegerNode two = new ASTCnIntegerNode(2);
    ASTUnaryFunctionNode unary = new ASTUnaryFunctionNode();
    unary.prependChild(one);
    exception.expect(IndexOutOfBoundsException.class);
    unary.prependChild(two);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTUnaryFunctionNode#removeChild(int)}.
   */
  @Test
  public final void testRemoveChild() {
    ASTCnIntegerNode one = new ASTCnIntegerNode(1);
    ASTUnaryFunctionNode unary = new ASTUnaryFunctionNode();
    unary.addChild(one);
    assertTrue(unary.removeChild(0) && unary.getChildCount() == 0);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTUnaryFunctionNode#removeChild(int)}.
   */
  @Test
  public final void testRemoveChildNonExistent() {
    ASTUnaryFunctionNode unary = new ASTUnaryFunctionNode();
    assertFalse(unary.removeChild(0));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTUnaryFunctionNode#setChild(org.sbml.jsbml.math.ASTNode2)}.
   */
  @Test
  public final void testSetChild() {
    ASTCnIntegerNode one = new ASTCnIntegerNode(1);
    ASTUnaryFunctionNode unary = new ASTUnaryFunctionNode();
    unary.setChild(one);
    assertTrue(unary.getChild().equals(one));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTUnaryFunctionNode#setChild(org.sbml.jsbml.math.ASTNode2)}.
   */
  @Test
  public final void testSetChildReplacement() {
    ASTCnIntegerNode one = new ASTCnIntegerNode(1);
    ASTCnIntegerNode two = new ASTCnIntegerNode(2);
    ASTUnaryFunctionNode unary = new ASTUnaryFunctionNode();
    unary.addChild(one);
    unary.setChild(two);
    assertTrue(unary.getChild().equals(two));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTUnaryFunctionNode#swapChildren(org.sbml.jsbml.math.ASTFunction)}.
   */
  @Test
  public final void testSwapChildren() {
    ASTCnIntegerNode one = new ASTCnIntegerNode(1);
    ASTCnIntegerNode two = new ASTCnIntegerNode(2);
    ASTUnaryFunctionNode A = new ASTUnaryFunctionNode();
    A.setChild(one);
    ASTUnaryFunctionNode B = new ASTUnaryFunctionNode();
    B.setChild(two);
    A.swapChildren(B);
    assertTrue(A.getChild().equals(two) && B.getChild().equals(one));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTUnaryFunctionNode#swapChildren(org.sbml.jsbml.math.ASTFunction)}.
   */
  @Test
  public final void testSwapChildrenNotStrict() {
    ASTCnIntegerNode one = new ASTCnIntegerNode(1);
    ASTCnIntegerNode two = new ASTCnIntegerNode(2);
    ASTCnIntegerNode three = new ASTCnIntegerNode(3);
    ASTUnaryFunctionNode A = new ASTUnaryFunctionNode();
    A.setStrictness(false);
    A.setChild(one);
    ASTUnaryFunctionNode B = new ASTUnaryFunctionNode();
    B.setStrictness(false);
    B.addChild(two);
    B.addChild(three);
    A.swapChildren(B);
    assertTrue(A.getChildAt(0).equals(two) && A.getChildAt(1).equals(three)
      && B.getChildAt(0).equals(one));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTUnaryFunctionNode#swapChildren(org.sbml.jsbml.math.ASTFunction)}.
   */
  @Test
  public final void testSwapChildrenStrict() {
    ASTCnIntegerNode one = new ASTCnIntegerNode(1);
    ASTCnIntegerNode two = new ASTCnIntegerNode(2);
    ASTCnIntegerNode three = new ASTCnIntegerNode(3);
    ASTUnaryFunctionNode A = new ASTUnaryFunctionNode();
    A.setChild(one);
    ASTUnaryFunctionNode B = new ASTUnaryFunctionNode();
    B.setStrictness(false);
    B.addChild(two);
    B.addChild(three);
    exception.expect(IndexOutOfBoundsException.class);
    A.swapChildren(B);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTUnaryFunctionNode#toFormula()}.
   */
  @Test
  public final void testToFormulaAbs() {
    ASTCnIntegerNode negativeFive = new ASTCnIntegerNode(-5);
    ASTUnaryFunctionNode abs = new ASTUnaryFunctionNode(Type.FUNCTION_ABS);
    abs.setChild(negativeFive);
    assertTrue(abs.toFormula().equals("abs(-5)"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTUnaryFunctionNode#toFormula()}.
   */
  @Test
  public final void testToFormulaCeil() {
    ASTCnIntegerNode negativeFive = new ASTCnIntegerNode(-5);
    ASTUnaryFunctionNode ceil = new ASTUnaryFunctionNode(Type.FUNCTION_CEILING);
    ceil.setChild(negativeFive);
    assertTrue(ceil.toFormula().equals("ceil(-5)"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTUnaryFunctionNode#toFormula()}.
   */
  @Test
  public final void testToFormulaExp() {
    ASTCnIntegerNode negativeFive = new ASTCnIntegerNode(-5);
    ASTUnaryFunctionNode exp = new ASTUnaryFunctionNode(Type.FUNCTION_EXP);
    exp.setChild(negativeFive);
    assertTrue(exp.toFormula().equals("exp(-5)"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTUnaryFunctionNode#toFormula()}.
   */
  @Test
  public final void testToFormulaFactorial() {
    ASTCnIntegerNode five = new ASTCnIntegerNode(5);
    ASTUnaryFunctionNode factorial = new ASTUnaryFunctionNode(Type.FUNCTION_FACTORIAL);
    factorial.setChild(five);
    assertTrue(factorial.toFormula().equals("(5)!"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTUnaryFunctionNode#toFormula()}.
   */
  @Test
  public final void testToFormulaFloor() {
    ASTCnIntegerNode five = new ASTCnIntegerNode(5);
    ASTUnaryFunctionNode floor = new ASTUnaryFunctionNode(Type.FUNCTION_FLOOR);
    floor.setChild(five);
    assertTrue(floor.toFormula().equals("floor(5)"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTUnaryFunctionNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeXAbs() {
    ASTCnIntegerNode negativeFive = new ASTCnIntegerNode(-5);
    ASTUnaryFunctionNode abs = new ASTUnaryFunctionNode(Type.FUNCTION_ABS);
    abs.setChild(negativeFive);
    assertTrue(abs.toLaTeX().equals("\\left\\lvert-5\\right\\rvert"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTUnaryFunctionNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeXCeil() {
    ASTCnIntegerNode negativeFive = new ASTCnIntegerNode(-5);
    ASTUnaryFunctionNode ceil = new ASTUnaryFunctionNode(Type.FUNCTION_CEILING);
    ceil.setChild(negativeFive);
    assertTrue(ceil.toLaTeX().equals("\\left\\lceil -5\\right\\rceil "));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTUnaryFunctionNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeXExp() {
    ASTCnIntegerNode negativeFive = new ASTCnIntegerNode(-5);
    ASTUnaryFunctionNode exp = new ASTUnaryFunctionNode(Type.FUNCTION_EXP);
    exp.setChild(negativeFive);
    assertTrue(exp.toLaTeX().equals("\\exp{\\left(-5\\right)}"));
  }


  /**
   * Test method for {@link org.sbml.jsbml.math.ASTUnaryFunctionNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeXFactorial() {
    ASTCnIntegerNode five = new ASTCnIntegerNode(5);
    ASTUnaryFunctionNode factorial = new ASTUnaryFunctionNode(Type.FUNCTION_FACTORIAL);
    factorial.setChild(five);
    assertTrue(factorial.toLaTeX().equals("\\left(5\\right)!"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTUnaryFunctionNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeXFloor() {
    ASTCnIntegerNode five = new ASTCnIntegerNode(5);
    ASTUnaryFunctionNode floor = new ASTUnaryFunctionNode(Type.FUNCTION_FLOOR);
    floor.setChild(five);
    assertTrue(floor.toLaTeX().equals("\\left\\lfloor 5\\right\\rfloor "));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTUnaryFunctionNode#toMathML()}.
   */
  @Test
  public final void testToMathMLAbs() {
    ASTCnIntegerNode negativeFive = new ASTCnIntegerNode(-5);
    ASTUnaryFunctionNode abs = new ASTUnaryFunctionNode(Type.FUNCTION_ABS);
    abs.setChild(negativeFive);
    assertTrue(abs.toMathML().equals(ASTFactory.parseMathML("abs.xml")));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTUnaryFunctionNode#toMathML()}.
   */
  @Test
  public final void testToMathMLCeil() {
    ASTCnIntegerNode negativeFive = new ASTCnIntegerNode(-5);
    ASTUnaryFunctionNode ceil = new ASTUnaryFunctionNode(Type.FUNCTION_CEILING);
    ceil.setChild(negativeFive);
    assertTrue(ceil.toMathML().equals(ASTFactory.parseMathML("ceil.xml")));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTUnaryFunctionNode#toMathML()}.
   */
  @Test
  public final void testToMathMLExp() {
    ASTCnIntegerNode negativeFive = new ASTCnIntegerNode(-5);
    ASTUnaryFunctionNode exp = new ASTUnaryFunctionNode(Type.FUNCTION_EXP);
    exp.setChild(negativeFive);
    assertTrue(exp.toMathML().equals(ASTFactory.parseMathML("exp.xml")));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTUnaryFunctionNode#toMathML()}.
   */
  @Test
  public final void testToMathMLFactorial() {
    ASTCnIntegerNode five = new ASTCnIntegerNode(5);
    ASTUnaryFunctionNode factorial = new ASTUnaryFunctionNode(Type.FUNCTION_FACTORIAL);
    factorial.setChild(five);
    assertTrue(factorial.toMathML().equals(ASTFactory.parseMathML("factorial.xml")));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTUnaryFunctionNode#toMathML()}.
   */
  @Test
  public final void testToMathMLFloor() {
    ASTCnIntegerNode five = new ASTCnIntegerNode(5);
    ASTUnaryFunctionNode floor = new ASTUnaryFunctionNode(Type.FUNCTION_FLOOR);
    floor.setChild(five);
    assertTrue(floor.toMathML().equals(ASTFactory.parseMathML("floor.xml")));
  }

}
