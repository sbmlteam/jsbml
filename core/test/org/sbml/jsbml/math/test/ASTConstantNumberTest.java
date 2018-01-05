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
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.math.ASTConstantNumber;
import org.sbml.jsbml.math.ASTFactory;

/**
 * Test cases for {@link ASTConstantNumber}
 * 
 * @author Victor Kofia
 * @since 1.0
 */
public class ASTConstantNumberTest {

  /**
   * 
   */
  @Rule
  public ExpectedException exception = ExpectedException.none();

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTConstantNumber#clone()}.
   */
  @Test
  public final void testClone() {
    ASTConstantNumber constant = new ASTConstantNumber();
    ASTConstantNumber unknown = constant.clone();
    assertTrue(constant.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTConstantNumber#ASTConstantNumber(org.sbml.jsbml.math.ASTConstantNumber)}.
   */
  @Test
  public final void testCloneWithConstructor() {
    ASTConstantNumber constant = new ASTConstantNumber();
    ASTConstantNumber unknown = new ASTConstantNumber(constant);
    assertTrue(constant.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTConstantNumber#clone()}.
   */
  @Test
  public final void testCloneWithValue() {
    ASTConstantNumber constant = new ASTConstantNumber(Math.PI);
    ASTConstantNumber unknown = constant.clone();
    assertTrue(constant.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTConstantNumber#getValue()}.
   */
  @Test
  public final void testGetValueE() {
    ASTConstantNumber constant = new ASTConstantNumber(Math.E);
    assertTrue(Double.compare(constant.getValue(), Math.E) == 0);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTConstantNumber#getValue()}.
   */
  @Test
  public final void testGetValueNonStrictNonExistent() {
    ASTConstantNumber constant = new ASTConstantNumber();
    constant.setStrictness(false);
    assertTrue(Double.compare(constant.getValue(), Double.NaN) == 0);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTConstantNumber#getValue()}.
   */
  @Test
  public final void testGetValuePi() {
    ASTConstantNumber constant = new ASTConstantNumber(Math.PI);
    assertTrue(Double.compare(constant.getValue(), Math.PI) == 0);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTConstantNumber#getValue()}.
   */
  @Test
  public final void testGetValueStrictNonExistent() {
    ASTConstantNumber constant = new ASTConstantNumber();
    exception.expect(PropertyUndefinedError.class);
    constant.getValue();
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTConstantNumber#isAllowableType(Type)}.
   */
  @Test
  public final void testIsAllowableTypeConstantE() {
    ASTConstantNumber node = new ASTConstantNumber();
    assertTrue(node.isAllowableType(Type.CONSTANT_E));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTConstantNumber#isAllowableType(Type)}.
   */
  @Test
  public final void testIsAllowableTypeConstantPi() {
    ASTConstantNumber node = new ASTConstantNumber();
    assertTrue(node.isAllowableType(Type.CONSTANT_PI));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTConstantNumber#isAllowableType(Type)}.
   */
  @Test
  public final void testIsAllowableTypeNull() {
    ASTConstantNumber node = new ASTConstantNumber();
    assertFalse(node.isAllowableType(null));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTConstantNumber#isSetType()}.
   */
  @Test
  public final void testIsSetTypeConstantE() {
    ASTConstantNumber node = new ASTConstantNumber(Type.CONSTANT_E);
    assertTrue(node.isSetType());
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTConstantNumber#isSetType()}.
   */
  @Test
  public final void testIsSetTypeConstantPi() {
    ASTConstantNumber node = new ASTConstantNumber(Type.CONSTANT_PI);
    assertTrue(node.isSetType());
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTConstantNumber#isSetType()}.
   */
  @Test
  public final void testIsSetTypeNull() {
    ASTConstantNumber node = new ASTConstantNumber();
    assertFalse(node.isSetType());
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTConstantNumber#setValue(double)}.
   */
  @Test
  public final void testSetValueE() {
    ASTConstantNumber constant = new ASTConstantNumber();
    constant.setValue(Math.E);
    assertTrue(constant.getType() == Type.CONSTANT_E);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTConstantNumber#setValue(double)}.
   */
  @Test
  public final void testSetValuePi() {
    ASTConstantNumber constant = new ASTConstantNumber();
    constant.setValue(Math.PI);
    assertTrue(constant.getType() == Type.CONSTANT_PI);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTConstantNumber#toFormula()}.
   */
  @Test
  public final void testToFormulaE() {
    ASTConstantNumber constant = new ASTConstantNumber();
    constant.setValue(Math.E);
    assertTrue(constant.toFormula().equals("e"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTConstantNumber#toFormula()}.
   */
  @Test
  public final void testToFormulaPi() {
    ASTConstantNumber constant = new ASTConstantNumber();
    constant.setValue(Math.PI);
    assertTrue(constant.toFormula().equals("pi"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTConstantNumber#toLaTeX()}.
   */
  @Test
  public final void testToLaTeXE() {
    ASTConstantNumber constant = new ASTConstantNumber();
    constant.setValue(Math.E);
    assertTrue(constant.toLaTeX().equals("\\mathrm{e}"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTConstantNumber#toLaTeX()}.
   */
  @Test
  public final void testToLaTeXPi() {
    ASTConstantNumber constant = new ASTConstantNumber();
    constant.setValue(Math.PI);
    assertTrue(constant.toLaTeX().equals("\\pi"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTConstantNumber#toMathML()}.
   */
  @Test
  public final void testToMathMLE() {
    ASTConstantNumber constant = new ASTConstantNumber();
    constant.setValue(Math.E);
    assertTrue(constant.toMathML().equals(ASTFactory.parseMathML("e.xml")));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTConstantNumber#toMathML()}.
   */
  @Test
  public final void testToMathMLPi() {
    ASTConstantNumber constant = new ASTConstantNumber();
    constant.setValue(Math.PI);
    assertTrue(constant.toMathML().equals(ASTFactory.parseMathML("pi.xml")));
  }

}
