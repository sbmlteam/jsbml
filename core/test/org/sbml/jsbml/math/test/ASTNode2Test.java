/*
 * $Id$
 * $URL$
 * ---------------------------------------------------------------------------- 
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML> 
 * for the latest version of JSBML and more information about SBML. 
 * 
 * Copyright (C) 2009-2014  jointly by the following organizations: 
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
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.math.ASTArithmeticOperatorNode;
import org.sbml.jsbml.math.ASTBinaryFunctionNode;
import org.sbml.jsbml.math.ASTCnExponentialNode;
import org.sbml.jsbml.math.ASTCnIntegerNode;
import org.sbml.jsbml.math.ASTCnNumberNode;
import org.sbml.jsbml.math.ASTCnRationalNode;
import org.sbml.jsbml.math.ASTCnRealNode;
import org.sbml.jsbml.math.ASTFactory;
import org.sbml.jsbml.math.ASTNode2;
import org.sbml.jsbml.math.ASTUnaryFunctionNode;


/**
 * Test cases for {@link ASTNode2}
 * 
 * @author Victor Kofia
 * @version $Rev$
 * @since 1.0
 * @date Jun 29, 2014
 */
public class ASTNode2Test {
  
  @Rule
  public ExpectedException exception = ExpectedException.none();
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#reduceToBinary()}.
   */
  @Test
  public void testReduceToBinary() {
    ASTArithmeticOperatorNode plus = new ASTArithmeticOperatorNode(Type.PLUS);
    plus.addChild(new ASTCnIntegerNode(15));
    plus.addChild(new ASTCnIntegerNode(20));
    plus.addChild(new ASTCnIntegerNode(25));
    ASTFactory.reduceToBinary(plus);
    assertTrue(plus.getChildCount() == 2);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTBinaryFunctionNode#addChild()}.
   */
  @Test
  public void testAddChildStrictBinaryFunction() {
    ASTBinaryFunctionNode binary = new ASTBinaryFunctionNode();
    binary.addChild(new ASTCnIntegerNode(10));
    binary.addChild(new ASTCnIntegerNode(15));
    exception.expect(IndexOutOfBoundsException.class);
    binary.addChild(new ASTCnIntegerNode(20));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTBinaryFunctionNode#addChild()}.
   */
  @Test
  public void testAddChildNotStrictBinaryFunction() {
    ASTBinaryFunctionNode binary = new ASTBinaryFunctionNode();
    binary.setStrictness(false);
    binary.addChild(new ASTCnIntegerNode(10));
    binary.addChild(new ASTCnIntegerNode(15));
    binary.addChild(new ASTCnIntegerNode(20));
    assertTrue(binary.getChildCount() == 3);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTUnaryFunctionNode#addChild()}.
   */
  @Test
  public void testAddChildStrictUnaryFunction() {
    ASTUnaryFunctionNode unary = new ASTUnaryFunctionNode();
    unary.addChild(new ASTCnIntegerNode(10));
    exception.expect(IndexOutOfBoundsException.class);
    unary.addChild(new ASTCnIntegerNode(15));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTUnaryFunctionNode#addChild()}.
   */
  @Test
  public void testAddChildNotStrictUnaryFunction() {
    ASTUnaryFunctionNode unary = new ASTUnaryFunctionNode();
    unary.setStrictness(false);
    unary.addChild(new ASTCnIntegerNode(10));
    unary.addChild(new ASTCnIntegerNode(15));
    assertTrue(unary.getChildCount() == 2);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTBinaryFunctionNode#getChildAt()}.
   */
  @Test
  public void getChildAtStrictBinaryFunction() {
    ASTBinaryFunctionNode binary = new ASTBinaryFunctionNode();
    binary.addChild(new ASTCnIntegerNode(10));
    binary.addChild(new ASTCnIntegerNode(15));
    exception.expect(IndexOutOfBoundsException.class);
    binary.getChildAt(2);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTBinaryFunctionNode#getChildAt()}.
   */
  @Test
  public void getChildAtNotStrictBinaryFunction() {
    ASTBinaryFunctionNode binary = new ASTBinaryFunctionNode();
    binary.setStrictness(false);
    ASTCnIntegerNode integer = new ASTCnIntegerNode(20);
    binary.addChild(new ASTCnIntegerNode(10));
    binary.addChild(new ASTCnIntegerNode(15));
    binary.addChild(integer);
    assertTrue(binary.getChildAt(2).equals(integer));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTUnaryFunctionNode#getChildAt()}.
   */
  @Test
  public void getChildAtStrictUnaryFunction() {
    ASTUnaryFunctionNode unary = new ASTUnaryFunctionNode();
    unary.addChild(new ASTCnIntegerNode(10));
    exception.expect(IndexOutOfBoundsException.class);
    unary.getChildAt(1);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTUnaryFunctionNode#getChildAt()}.
   */
  @Test
  public void getChildAtNotStrictUnaryFunction() {
    ASTUnaryFunctionNode unary = new ASTUnaryFunctionNode();
    unary.setStrictness(false);
    ASTCnIntegerNode integer = new ASTCnIntegerNode(15);
    unary.addChild(new ASTCnIntegerNode(10));
    unary.addChild(integer);
    assertTrue(unary.getChildAt(1).equals(integer));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTBinaryFunctionNode#insertChild()}.
   */
  @Test
  public void insertChildStrictBinaryFunction() {
    ASTBinaryFunctionNode binary = new ASTBinaryFunctionNode();
    binary.insertChild(0, new ASTCnIntegerNode(10));
    binary.insertChild(1, new ASTCnIntegerNode(15));
    exception.expect(IndexOutOfBoundsException.class);
    binary.insertChild(2, new ASTCnIntegerNode(10));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTBinaryFunctionNode#insertChild()}.
   */
  @Test
  public void insertChildNotStrictBinaryFunction() {
    ASTBinaryFunctionNode binary = new ASTBinaryFunctionNode();
    binary.setStrictness(false);
    ASTCnIntegerNode integer = new ASTCnIntegerNode(20);
    binary.insertChild(0, new ASTCnIntegerNode(10));
    binary.insertChild(1, new ASTCnIntegerNode(15));
    binary.insertChild(2, integer);
    assertTrue(binary.getChildAt(2).equals(integer));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTUnaryFunctionNode#insertChild()}.
   */
  @Test
  public void insertChildStrictUnaryFunction() {
    ASTUnaryFunctionNode unary = new ASTUnaryFunctionNode();
    unary.insertChild(0, new ASTCnIntegerNode(10));
    exception.expect(IndexOutOfBoundsException.class);
    unary.insertChild(1, new ASTCnIntegerNode(15));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTUnaryFunctionNode#insertChild()}.
   */
  @Test
  public void insertChildNotStrictUnaryFunction() {
    ASTUnaryFunctionNode unary = new ASTUnaryFunctionNode();
    unary.setStrictness(false);
    ASTCnIntegerNode integer = new ASTCnIntegerNode(20);
    unary.insertChild(0, new ASTCnIntegerNode(10));
    unary.insertChild(1, integer);
    assertTrue(unary.getChildAt(1).equals(integer));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTBinaryFunctionNode#prependChild()}.
   */
  @Test
  public void prependChildStrictBinaryFunction() {
    ASTBinaryFunctionNode binary = new ASTBinaryFunctionNode();
    binary.prependChild(new ASTCnIntegerNode(10));
    binary.prependChild(new ASTCnIntegerNode(15));
    exception.expect(IndexOutOfBoundsException.class);
    binary.prependChild(new ASTCnIntegerNode(10));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTBinaryFunctionNode#prependChild()}.
   */
  @Test
  public void prependChildNotStrictBinaryFunction() {
    ASTBinaryFunctionNode binary = new ASTBinaryFunctionNode();
    binary.setStrictness(false);
    ASTCnIntegerNode integer = new ASTCnIntegerNode(20);
    binary.prependChild(new ASTCnIntegerNode(10));
    binary.prependChild(new ASTCnIntegerNode(15));
    binary.prependChild(integer);
    assertTrue(binary.getChildAt(0).equals(integer));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTUnaryFunctionNode#prependChild()}.
   */
  @Test
  public void prependChildStrictUnaryFunction() {
    ASTUnaryFunctionNode unary = new ASTUnaryFunctionNode();
    unary.prependChild(new ASTCnIntegerNode(10));
    exception.expect(IndexOutOfBoundsException.class);
    unary.prependChild(new ASTCnIntegerNode(15));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTUnaryFunctionNode#prependChild()}.
   */
  @Test
  public void prependChildNotStrictUnaryFunction() {
    ASTUnaryFunctionNode unary = new ASTUnaryFunctionNode();
    unary.setStrictness(false);
    ASTCnIntegerNode integer = new ASTCnIntegerNode(20);
    unary.prependChild(new ASTCnIntegerNode(10));
    unary.prependChild(integer);
    assertTrue(unary.getChildAt(0).equals(integer));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTBinaryFunctionNode#removeChild()}.
   */
  @Test
  public void removeChildStrictBinaryFunction() {
    ASTBinaryFunctionNode binary = new ASTBinaryFunctionNode();
    binary.addChild(new ASTCnIntegerNode(10));
    binary.addChild(new ASTCnIntegerNode(15));
    assertFalse(binary.removeChild(2));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTBinaryFunctionNode#removeChild()}.
   */
  @Test
  public void removeChildNotStrictBinaryFunction() {
    ASTBinaryFunctionNode binary = new ASTBinaryFunctionNode();
    binary.setStrictness(false);
    binary.addChild(new ASTCnIntegerNode(10));
    binary.addChild(new ASTCnIntegerNode(15));
    binary.addChild(new ASTCnIntegerNode(20));
    assertTrue(binary.removeChild(2));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTUnaryFunctionNode#removeChild()}.
   */
  @Test
  public void removeChildStrictUnaryFunction() {
    ASTUnaryFunctionNode unary = new ASTUnaryFunctionNode();
    unary.addChild(new ASTCnIntegerNode(10));
    assertFalse(unary.removeChild(1));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTUnaryFunctionNode#removeChild()}.
   */
  @Test
  public void removeChildNotStrictUnaryFunction() {
    ASTUnaryFunctionNode unary = new ASTUnaryFunctionNode();
    unary.setStrictness(false);
    unary.addChild(new ASTCnIntegerNode(10));
    unary.addChild(new ASTCnIntegerNode(15));
    assertTrue(unary.removeChild(1));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTBinaryFunctionNode#swapChildren()}.
   */
  @Test
  public void swapChildrenStrictBinaryFunction() {
    ASTBinaryFunctionNode strict = new ASTBinaryFunctionNode();
    ASTBinaryFunctionNode notStrict = new ASTBinaryFunctionNode();
    notStrict.setStrictness(false);
    notStrict.addChild(new ASTCnIntegerNode(15));
    notStrict.addChild(new ASTCnIntegerNode(20));
    notStrict.addChild(new ASTCnIntegerNode(25));
    exception.expect(IndexOutOfBoundsException.class);
    strict.swapChildren(notStrict);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTBinaryFunctionNode#swapChildren()}.
   */
  @Test
  public void swapChildrenNotStrictBinaryFunction() {
    ASTBinaryFunctionNode a = new ASTBinaryFunctionNode();
    a.setStrictness(false);
    a.addChild(new ASTCnIntegerNode(2));
    a.addChild(new ASTCnIntegerNode(4));
    a.addChild(new ASTCnIntegerNode(6));
    ASTBinaryFunctionNode b = new ASTBinaryFunctionNode();
    b.setStrictness(false);
    b.addChild(new ASTCnIntegerNode(15));
    b.addChild(new ASTCnIntegerNode(20));
    b.addChild(new ASTCnIntegerNode(25));
    b.addChild(new ASTCnIntegerNode(30));
    a.swapChildren(b);
    assertTrue((a.getChildCount() == 4) && (b.getChildCount() == 3));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTUnaryFunctionNode#swapChildren()}.
   */
  @Test
  public void swapChildrenStrictUnaryFunction() {
    ASTUnaryFunctionNode strict = new ASTUnaryFunctionNode();
    ASTUnaryFunctionNode notStrict = new ASTUnaryFunctionNode();
    notStrict.setStrictness(false);
    notStrict.addChild(new ASTCnIntegerNode(15));
    notStrict.addChild(new ASTCnIntegerNode(20));
    exception.expect(IndexOutOfBoundsException.class);
    strict.swapChildren(notStrict);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTBinaryFunctionNode#swapChildren()}.
   */
  @Test
  public void swapChildrenNotStrictUnaryFunction() {
    ASTUnaryFunctionNode a = new ASTUnaryFunctionNode();
    a.setStrictness(false);
    a.addChild(new ASTCnIntegerNode(5));
    ASTUnaryFunctionNode b = new ASTUnaryFunctionNode();
    b.setStrictness(false);
    b.addChild(new ASTCnIntegerNode(15));
    b.addChild(new ASTCnIntegerNode(20));
    b.addChild(new ASTCnIntegerNode(25));
    a.swapChildren(b);
    assertTrue((a.getChildCount() == 3) && (b.getChildCount() == 1));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnNumberNode#getNumber()}.
   */
  @Test
  public void testASTCnNumberNodeNoValue() {
    ASTCnNumberNode<Integer> number = new ASTCnNumberNode<Integer>();
    exception.expect(PropertyUndefinedError.class);
    number.getNumber();
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnNumberNode#getNumber()}.
   */
  @Test
  public void testASTCnNumberNodeWithValue() {
    ASTCnNumberNode<Integer> number = new ASTCnNumberNode<Integer>();
    number.setNumber(10);
    assertTrue(number.getNumber() == 10);
  }
  
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnIntegerNode#getInteger()}.
   */
  @Test
  public void testASTCnIntegerWithValue() {
    ASTCnIntegerNode integer = new ASTCnIntegerNode(10);
    assertTrue(integer.getInteger() == 10);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnIntegerNode#getInteger()}.
   */
  @Test
  public void testASTCnIntegerNoValue() {
    ASTCnIntegerNode integer = new ASTCnIntegerNode();
    exception.expect(PropertyUndefinedError.class);
    integer.getInteger();
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnRealNode#getReal()}.
   */
  @Test
  public void testASTCnRealWithValue() {
    ASTCnRealNode real = new ASTCnRealNode(10.0);
    assertTrue(real.getReal() == 10.0);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnRealNode#getReal()}.
   */
  @Test
  public void testASTCnRealNoValue() {
    ASTCnRealNode real = new ASTCnRealNode();
    assertTrue(Double.isNaN(real.getReal()));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnRationalNode#getNumerator()}.
   */
  @Test
  public void testASTCnRationalNumeratorNoValue() {
    ASTCnRationalNode rational = new ASTCnRationalNode();
    rational.setDenominator(10);
    exception.expect(PropertyUndefinedError.class);
    rational.getNumerator();
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnRationalNode#getNumerator()}.
   */
  @Test
  public void testASTCnRationalNumeratorWithValue() {
    ASTCnRationalNode rational = new ASTCnRationalNode();
    rational.setNumerator(10);
    rational.setDenominator(10);
    assertTrue(rational.getNumerator() == 10);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnRationalNode#getDenominator()}.
   */
  @Test
  public void testASTCnRationalDenominatorNoValue() {
    ASTCnRationalNode rational = new ASTCnRationalNode();
    rational.setNumerator(10);
    exception.expect(PropertyUndefinedError.class);
    rational.getDenominator();
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnRationalNode#getDenominator()}.
   */
  @Test
  public void testASTCnRationalDenominatorWithValue() {
    ASTCnRationalNode rational = new ASTCnRationalNode();
    rational.setNumerator(10);
    rational.setDenominator(10);
    assertTrue(rational.getDenominator() == 10);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnExponentialNode#getMantissa()}.
   */
  @Test
  public void testASTCnExponentialMantissaNoValue() {
    ASTCnExponentialNode exponential = new ASTCnExponentialNode();
    exponential.setExponent(10);
    exception.expect(PropertyUndefinedError.class);
    exponential.getMantissa();
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnExponentialNode#getMantissa()}.
   */
  @Test
  public void testASTCnExponentialMantissaWithValue() {
    ASTCnExponentialNode exponential = new ASTCnExponentialNode();
    exponential.setExponent(10);
    exponential.setMantissa(10);
    assertTrue(exponential.getMantissa() == 10);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnExponentialNode#getExponent()}.
   */
  @Test
  public void testASTCnExponentialMantissaNoExponent() {
    ASTCnExponentialNode exponential = new ASTCnExponentialNode();
    exponential.setMantissa(10);
    exception.expect(PropertyUndefinedError.class);
    exponential.getExponent();
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnExponentialNode#getExponent()}.
   */
  @Test
  public void testASTCnExponentialMantissaWithExponent() {
    ASTCnExponentialNode exponential = new ASTCnExponentialNode();
    exponential.setExponent(10);
    exponential.setMantissa(10);
    assertTrue(exponential.getExponent() == 10);
  }
    
}
