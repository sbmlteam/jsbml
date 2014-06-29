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
import org.sbml.jsbml.math.ASTArithmeticOperatorNode;
import org.sbml.jsbml.math.ASTBinaryFunctionNode;
import org.sbml.jsbml.math.ASTCnIntegerNode;
import org.sbml.jsbml.math.ASTFactory;
import org.sbml.jsbml.math.ASTUnaryFunctionNode;


/**
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
  
}
