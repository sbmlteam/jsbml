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
import org.sbml.jsbml.math.ASTBinaryFunctionNode;
import org.sbml.jsbml.math.ASTCnIntegerNode;


/**
 * Test cases for {@link ASTBinaryFunctionNode}
 * 
 * @author Victor Kofia
 * @since 1.0
 */
public class ASTBinaryFunctionNodeTest {

  /**
   * 
   */
  @Rule
  public ExpectedException exception = ExpectedException.none();

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTBinaryFunctionNode#getChildAt(int)}.
   */
  @Test
  public void getChildAtNotStrict() {
    ASTBinaryFunctionNode binary = new ASTBinaryFunctionNode();
    binary.setStrictness(false);
    ASTCnIntegerNode integer = new ASTCnIntegerNode(20);
    binary.addChild(new ASTCnIntegerNode(10));
    binary.addChild(new ASTCnIntegerNode(15));
    binary.addChild(integer);
    assertTrue(binary.getChildAt(2).equals(integer));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTBinaryFunctionNode#getChildAt(int)}.
   */
  @Test
  public void getChildAtStrict() {
    ASTBinaryFunctionNode binary = new ASTBinaryFunctionNode();
    binary.addChild(new ASTCnIntegerNode(10));
    binary.addChild(new ASTCnIntegerNode(15));
    exception.expect(IndexOutOfBoundsException.class);
    binary.getChildAt(2);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTBinaryFunctionNode#insertChild(int, org.sbml.jsbml.math.ASTNode2)}.
   */
  @Test
  public void insertChildNotStrict() {
    ASTBinaryFunctionNode binary = new ASTBinaryFunctionNode();
    binary.setStrictness(false);
    ASTCnIntegerNode integer = new ASTCnIntegerNode(20);
    binary.insertChild(0, new ASTCnIntegerNode(10));
    binary.insertChild(1, new ASTCnIntegerNode(15));
    binary.insertChild(2, integer);
    assertTrue(binary.getChildAt(2).equals(integer));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTBinaryFunctionNode#insertChild(int, org.sbml.jsbml.math.ASTNode2)}.
   */
  @Test
  public void insertChildStrict() {
    ASTBinaryFunctionNode binary = new ASTBinaryFunctionNode();
    binary.insertChild(0, new ASTCnIntegerNode(10));
    binary.insertChild(1, new ASTCnIntegerNode(15));
    exception.expect(IndexOutOfBoundsException.class);
    binary.insertChild(2, new ASTCnIntegerNode(10));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTBinaryFunctionNode#prependChild(org.sbml.jsbml.math.ASTNode2)}.
   */
  @Test
  public void prependChildNotStrict() {
    ASTBinaryFunctionNode binary = new ASTBinaryFunctionNode();
    binary.setStrictness(false);
    ASTCnIntegerNode integer = new ASTCnIntegerNode(20);
    binary.prependChild(new ASTCnIntegerNode(10));
    binary.prependChild(new ASTCnIntegerNode(15));
    binary.prependChild(integer);
    assertTrue(binary.getChildAt(0).equals(integer));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTBinaryFunctionNode#prependChild(org.sbml.jsbml.math.ASTNode2)}.
   */
  @Test
  public void prependChildStrict() {
    ASTBinaryFunctionNode binary = new ASTBinaryFunctionNode();
    binary.prependChild(new ASTCnIntegerNode(10));
    binary.prependChild(new ASTCnIntegerNode(15));
    exception.expect(IndexOutOfBoundsException.class);
    binary.prependChild(new ASTCnIntegerNode(10));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTBinaryFunctionNode#removeChild(int)}.
   */
  @Test
  public void removeChildNotStrict() {
    ASTBinaryFunctionNode binary = new ASTBinaryFunctionNode();
    binary.setStrictness(false);
    binary.addChild(new ASTCnIntegerNode(10));
    binary.addChild(new ASTCnIntegerNode(15));
    binary.addChild(new ASTCnIntegerNode(20));
    assertTrue(binary.removeChild(2));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTBinaryFunctionNode#removeChild(int)}.
   */
  @Test
  public void removeChildStrict() {
    ASTBinaryFunctionNode binary = new ASTBinaryFunctionNode();
    binary.addChild(new ASTCnIntegerNode(10));
    binary.addChild(new ASTCnIntegerNode(15));
    assertFalse(binary.removeChild(2));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTBinaryFunctionNode#swapChildren(org.sbml.jsbml.math.ASTFunction)}.
   */
  @Test
  public void swapChildrenNotStrict() {
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
   * Test method for {@link org.sbml.jsbml.math.ASTBinaryFunctionNode#swapChildren(org.sbml.jsbml.math.ASTFunction)}.
   */
  @Test
  public void swapChildrenStrict() {
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
   * Test method for {@link org.sbml.jsbml.math.ASTBinaryFunctionNode#addChild(org.sbml.jsbml.math.ASTNode2)}.
   */
  @Test
  public void testAddChildNotStrict() {
    ASTBinaryFunctionNode binary = new ASTBinaryFunctionNode();
    binary.setStrictness(false);
    binary.addChild(new ASTCnIntegerNode(10));
    binary.addChild(new ASTCnIntegerNode(15));
    binary.addChild(new ASTCnIntegerNode(20));
    assertTrue(binary.getChildCount() == 3);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTBinaryFunctionNode#addChild(org.sbml.jsbml.math.ASTNode2)}.
   */
  @Test
  public void testAddChildStrict() {
    ASTBinaryFunctionNode binary = new ASTBinaryFunctionNode();
    binary.addChild(new ASTCnIntegerNode(10));
    binary.addChild(new ASTCnIntegerNode(15));
    exception.expect(IndexOutOfBoundsException.class);
    binary.addChild(new ASTCnIntegerNode(20));
  }

}
