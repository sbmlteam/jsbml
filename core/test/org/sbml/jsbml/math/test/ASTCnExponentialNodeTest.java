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

import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.math.ASTCnExponentialNode;
import org.sbml.jsbml.math.ASTFactory;


/**
 * Test cases for {@link ASTCnExponentialNode}
 * 
 * @author Victor Kofia
 * @since 1.0
 */
public class ASTCnExponentialNodeTest {

  /**
   * 
   */
  @Rule
  public ExpectedException exception = ExpectedException.none();

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnExponentialNode#clone()}.
   */
  @Test
  public final void testClone() {
    ASTCnExponentialNode exponential = new ASTCnExponentialNode();
    ASTCnExponentialNode unknown = exponential.clone();
    assertTrue(exponential.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnExponentialNode#ASTCnExponentialNode(org.sbml.jsbml.math.ASTCnExponentialNode)}.
   */
  @Test
  public final void testCloneWithConstructor() {
    ASTCnExponentialNode exponential = new ASTCnExponentialNode();
    ASTCnExponentialNode unknown = new ASTCnExponentialNode(exponential);
    assertTrue(exponential.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnExponentialNode#clone()}.
   */
  @Test
  public final void testCloneWithValues() {
    ASTCnExponentialNode exponential = new ASTCnExponentialNode();
    exponential.setExponent(10);
    exponential.setMantissa(10);
    ASTCnExponentialNode unknown = exponential.clone();
    assertTrue(exponential.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnExponentialNode#getExponent()}.
   */
  @Test
  public final void testGetExponentNoMantissa() {
    ASTCnExponentialNode exponential = new ASTCnExponentialNode();
    exponential.setExponent(10);
    assertTrue(exponential.getExponent() == 10);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnExponentialNode#getExponent()}.
   */
  @Test
  public final void testGetExponentNoValue() {
    ASTCnExponentialNode exponential = new ASTCnExponentialNode();
    exception.expect(PropertyUndefinedError.class);
    exponential.getExponent();
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnExponentialNode#getExponent()}.
   */
  @Test
  public final void testGetExponentWithMantissa() {
    ASTCnExponentialNode exponential = new ASTCnExponentialNode();
    exponential.setExponent(10);
    exponential.setMantissa(10);
    assertTrue(exponential.getExponent() == 10);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnExponentialNode#getMantissa()}.
   */
  @Test
  public final void testGetMantissaNoExponent() {
    ASTCnExponentialNode exponential = new ASTCnExponentialNode();
    exponential.setMantissa(10);
    assertTrue(exponential.getMantissa() == 10);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnExponentialNode#getMantissa()}.
   */
  @Test
  public final void testGetMantissaNonExistent() {
    ASTCnExponentialNode exponential = new ASTCnExponentialNode();
    exponential.setExponent(10);
    exception.expect(PropertyUndefinedError.class);
    exponential.getMantissa();
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnExponentialNode#getExponent()}.
   */
  @Test
  public final void testGetMantissaWithExponent() {
    ASTCnExponentialNode exponential = new ASTCnExponentialNode();
    exponential.setExponent(10);
    exponential.setMantissa(10);
    assertTrue(exponential.getExponent() == 10);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnExponentialNode#isSetExponent()}.
   */
  @Test
  public final void testIsSetExponent() {
    ASTCnExponentialNode isSet = new ASTCnExponentialNode();
    isSet.setExponent(10);
    ASTCnExponentialNode notSet = new ASTCnExponentialNode();
    assertTrue(isSet.isSetExponent() && !notSet.isSetExponent());
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnExponentialNode#isSetMantissa()}.
   */
  @Test
  public final void testIsSetMantissa() {
    ASTCnExponentialNode isSet = new ASTCnExponentialNode();
    isSet.setMantissa(10);
    ASTCnExponentialNode notSet = new ASTCnExponentialNode();
    assertTrue(isSet.isSetMantissa() && !notSet.isSetMantissa());
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnExponentialNode#toFormula()}.
   */
  @Test
  public final void testToFormula() {
    ASTCnExponentialNode exponential = new ASTCnExponentialNode();
    exponential.setExponent(2);
    exponential.setMantissa(7);
    assertTrue(exponential.toFormula().equals("7E2"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnExponentialNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeX() {
    ASTCnExponentialNode exponential = new ASTCnExponentialNode();
    exponential.setExponent(2);
    exponential.setMantissa(7);
    assertTrue(exponential.toLaTeX().equals("7\\cdot 10^{2}"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnExponentialNode#toMathML()}.
   */
  @Test
  public final void testToMathML() {
    ASTCnExponentialNode exponential = new ASTCnExponentialNode();
    exponential.setExponent(2);
    exponential.setMantissa(7);
    assertTrue(exponential.toMathML().equals(ASTFactory.parseMathML("exponential.xml")));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnExponentialNode#getType()}.
   */
  @Test
  public final void testGetType() {
    ASTCnExponentialNode exponential = new ASTCnExponentialNode();
    assertTrue(exponential.getType() == Type.REAL_E);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnExponentialNode#isSetType()}.
   */
  @Test
  public final void testIsSetType() {
    ASTCnExponentialNode exponential = new ASTCnExponentialNode();
    assertTrue(exponential.isSetType());
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnExponentialNode#isAllowableType(Type)}.
   */
  @Test
  public final void testIsAllowableType() {
    ASTCnExponentialNode exponential = new ASTCnExponentialNode();
    assertTrue(exponential.isAllowableType(Type.REAL_E) && !exponential.isAllowableType(null));
  }

}
