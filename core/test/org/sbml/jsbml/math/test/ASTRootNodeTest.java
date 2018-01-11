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

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.math.ASTCnIntegerNode;
import org.sbml.jsbml.math.ASTFactory;
import org.sbml.jsbml.math.ASTRootNode;

/**
 * Test cases for {@link ASTRootNode}
 * 
 * @author Victor Kofia
 * @since 1.0
 */
public class ASTRootNodeTest {

  /**
   * 
   */
  @Rule
  public ExpectedException exception = ExpectedException.none();

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTRootNode#clone()}.
   */
  @Test
  public final void testClone() {
    ASTRootNode squareRoot = new ASTRootNode();
    ASTRootNode unknown = squareRoot.clone();
    assertTrue(unknown.equals(squareRoot));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTRootNode#ASTRootNode(org.sbml.jsbml.math.ASTRootNode)}.
   */
  @Test
  public final void testCloneWithConstructor() {
    ASTRootNode squareRoot = new ASTRootNode();
    ASTRootNode unknown = new ASTRootNode(squareRoot);
    assertTrue(unknown.equals(squareRoot));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTRootNode#clone()}.
   */
  @Test
  public final void testCloneWithRadicand() {
    ASTRootNode squareRoot = new ASTRootNode(new ASTCnIntegerNode(10000));
    ASTRootNode unknown = squareRoot.clone();
    assertTrue(unknown.equals(squareRoot));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTRootNode#getChildCount()}.
   */
  @Test
  public final void testGetChildCount() {
    assertTrue(true);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTRootNode#getRadicand()}.
   */
  @Test
  public final void testGetRadicandNonExistent() {
    ASTRootNode squareRoot = new ASTRootNode();
    exception.expect(PropertyUndefinedError.class);
    squareRoot.getRadicand();
  }


  /**
   * Test method for {@link org.sbml.jsbml.math.ASTRootNode#getRootExponent()}.
   */
  @Test
  public final void testGetRootExponentOther() {
    ASTCnIntegerNode ten = new ASTCnIntegerNode(10);
    ASTRootNode root = new ASTRootNode(ten, new ASTCnIntegerNode(1000));
    assertTrue(root.getRootExponent().equals(ten));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTRootNode#getRootExponent()}.
   */
  @Test
  public final void testGetSquareRootExponent() {
    ASTRootNode squareRoot = new ASTRootNode();
    assertTrue(squareRoot.getRootExponent().equals(new ASTCnIntegerNode(2)));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTRootNode#getRootExponent()}.
   */
  @Test
  public final void testGetSquareRootExponentWithRadicand() {
    ASTRootNode squareRoot = new ASTRootNode(new ASTCnIntegerNode(49));
    ASTCnIntegerNode integer = new ASTCnIntegerNode(10);
    squareRoot.setRootExponent(integer);
    assertTrue(squareRoot.getRootExponent().equals(integer));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTRootNode#getRadicand()}.
   */
  @Test
  public final void testGetSquareRootRadicand() {
    ASTCnIntegerNode four = new ASTCnIntegerNode(4);
    ASTRootNode squareRoot = new ASTRootNode(four);
    assertTrue(squareRoot.getRadicand().equals(four));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTRootNode#isAllowableType(Type)}.
   */
  @Test
  public final void testIsAllowableType() {
    ASTRootNode root = new ASTRootNode();
    assertTrue(root.isAllowableType(Type.FUNCTION_ROOT) && !root.isAllowableType(null));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTRootNode#setRadicand(org.sbml.jsbml.math.ASTNode2)}.
   */
  @Test
  public final void testSetRadicand() {
    ASTRootNode squareRoot = new ASTRootNode();
    ASTCnIntegerNode radicand = new ASTCnIntegerNode(10);
    squareRoot.setRadicand(radicand);
    assertTrue(squareRoot.getRadicand().equals(radicand));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTRootNode#setRootExponent(org.sbml.jsbml.math.ASTNode2)}.
   */
  @Test
  public final void testSetRootExponent() {
    ASTRootNode root = new ASTRootNode(new ASTCnIntegerNode(4), new ASTCnIntegerNode(10000));
    ASTCnIntegerNode rootExponent = new ASTCnIntegerNode(10);
    root.setRootExponent(rootExponent);
    assertTrue(root.getRootExponent().equals(rootExponent));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTRootNode#toFormula()}.
   */
  @Test
  public final void testToFormula() {
    ASTRootNode root = new ASTRootNode(new ASTCnIntegerNode(4), new ASTCnIntegerNode(10000));
    assertTrue(root.toFormula().equals("(10000)^(1/4.0)"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTRootNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeX() {
    ASTRootNode root = new ASTRootNode(new ASTCnIntegerNode(4), new ASTCnIntegerNode(10000));
    assertTrue(root.toLaTeX().equals("\\sqrt[4.0]{10000}"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTRootNode#toMathML()}.
   */
  @Test
  public final void testToMathML() {
    ASTRootNode root = new ASTRootNode(new ASTCnIntegerNode(4), new ASTCnIntegerNode(10000));
    assertTrue(root.toMathML().equals(ASTFactory.parseMathML("root.xml")));
  }

}
