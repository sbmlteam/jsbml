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
import org.sbml.jsbml.math.ASTCnRationalNode;
import org.sbml.jsbml.math.ASTFactory;


/**
 * Test cases for {@link ASTCnRationalNode}
 * 
 * @author Victor Kofia
 * @since 1.0
 */
public class ASTCnRationalNodeTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnRationalNode#clone()}.
   */
  @Test
  public final void testClone() {
    ASTCnRationalNode rational = new ASTCnRationalNode();
    ASTCnRationalNode unknown = rational.clone();
    assertTrue(rational.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnRationalNode#ASTCnRationalNode(org.sbml.jsbml.math.ASTCnRationalNode)}.
   */
  @Test
  public final void testCloneWithConstructor() {
    ASTCnRationalNode rational = new ASTCnRationalNode();
    ASTCnRationalNode unknown = new ASTCnRationalNode(rational);
    assertTrue(rational.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnRationalNode#clone()}.
   */
  @Test
  public final void testCloneWithValues() {
    ASTCnRationalNode rational = new ASTCnRationalNode();
    rational.setDenominator(10);
    rational.setNumerator(5);
    ASTCnRationalNode unknown = rational.clone();
    assertTrue(rational.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnRationalNode#getDenominator()}.
   */
  @Test
  public void testGetDenominatorNoValue() {
    ASTCnRationalNode rational = new ASTCnRationalNode();
    rational.setNumerator(10);
    exception.expect(PropertyUndefinedError.class);
    rational.getDenominator();
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnRationalNode#getDenominator()}.
   */
  @Test
  public void testGetDenominatorWithValue() {
    ASTCnRationalNode rational = new ASTCnRationalNode();
    rational.setNumerator(10);
    rational.setDenominator(10);
    assertTrue(rational.getDenominator() == 10);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnRationalNode#getNumerator()}.
   */
  @Test
  public void testGetNumeratorNoValue() {
    ASTCnRationalNode rational = new ASTCnRationalNode();
    rational.setDenominator(10);
    exception.expect(PropertyUndefinedError.class);
    rational.getNumerator();
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnRationalNode#getNumerator()}.
   */
  @Test
  public void testGetNumeratorWithValue() {
    ASTCnRationalNode rational = new ASTCnRationalNode();
    rational.setNumerator(10);
    rational.setDenominator(10);
    assertTrue(rational.getNumerator() == 10);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnRationalNode#isAllowableType(org.sbml.jsbml.ASTNode.Type)}.
   */
  @Test
  public final void testIsAllowableType() {
    ASTCnRationalNode rational = new ASTCnRationalNode();
    assertTrue(rational.isAllowableType(Type.RATIONAL) && !rational.isAllowableType(null));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnRationalNode#isSetDenominator()}.
   */
  @Test
  public final void testIsSetDenominator() {
    ASTCnRationalNode isSet = new ASTCnRationalNode();
    isSet.setDenominator(10);
    ASTCnRationalNode notSet = new ASTCnRationalNode();
    assertTrue(isSet.isSetDenominator() && !notSet.isSetDenominator());
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnRationalNode#isSetNumerator()}.
   */
  @Test
  public final void testIsSetNumerator() {
    ASTCnRationalNode isSet = new ASTCnRationalNode();
    isSet.setNumerator(10);
    ASTCnRationalNode notSet = new ASTCnRationalNode();
    assertTrue(isSet.isSetNumerator() && !notSet.isSetNumerator());
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnRationalNode#toFormula()}.
   */
  @Test
  public final void testToFormula() {
    ASTCnRationalNode rational = new ASTCnRationalNode();
    rational.setNumerator(1);
    rational.setDenominator(2);
    assertTrue(rational.toFormula().equals("1/2"));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnRationalNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeX() {
    ASTCnRationalNode rational = new ASTCnRationalNode();
    rational.setNumerator(1);
    rational.setDenominator(2);
    assertTrue(rational.toLaTeX().equals("\\frac{1}{2}"));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnRationalNode#toMathML()}.
   */
  @Test
  public final void testToMathML() {
    ASTCnRationalNode rational = new ASTCnRationalNode();
    rational.setNumerator(1);
    rational.setDenominator(2);
    assertTrue(rational.toMathML().equals(ASTFactory.parseMathML("rational.xml")));
  }
  
}
