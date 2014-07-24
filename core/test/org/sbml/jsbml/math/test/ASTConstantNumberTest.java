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

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.math.ASTConstantNumber;


/**
 * Test cases for {@link ASTConstantNumber}
 * 
 * @author Victor Kofia
 * @version $Rev$
 * @since 1.0
 * @date Jul 24, 2014
 */
public class ASTConstantNumberTest {
  
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
   * Test method for {@link org.sbml.jsbml.math.ASTConstantNumber#clone()}.
   */
  @Test
  public final void testCloneWithValue() {
    ASTConstantNumber constant = new ASTConstantNumber(Math.PI);
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
  public final void testGetValueE() {
    ASTConstantNumber constant = new ASTConstantNumber(Math.E);
    assertTrue(Double.compare(constant.getValue(), Math.E) == 0);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTConstantNumber#getValue()}.
   */
  @Test
  public final void testGetValueAvogadrosConstant() {
    ASTConstantNumber constant = new ASTConstantNumber(Type.NAME_AVOGADRO);
    assertTrue(Double.compare(constant.getValue(), 6.023e23) == 0);
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
   * Test method for {@link org.sbml.jsbml.math.ASTConstantNumber#getValue()}.
   */
  @Test
  public final void testGetValueNonStrictNonExistent() {
    ASTConstantNumber constant = new ASTConstantNumber();
    constant.setStrictness(false);
    assertTrue(Double.compare(constant.getValue(), Double.NaN) == 0);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTConstantNumber#setValue()}.
   */
  @Test
  public final void testSetValuePi() {
    ASTConstantNumber constant = new ASTConstantNumber();
    constant.setValue(Math.PI);
    assertTrue(constant.getType() == Type.CONSTANT_PI);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTConstantNumber#setValue()}.
   */
  @Test
  public final void testSetValueE() {
    ASTConstantNumber constant = new ASTConstantNumber();
    constant.setValue(Math.E);
    assertTrue(constant.getType() == Type.CONSTANT_E);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTConstantNumber#setValue()}.
   */
  @Test
  public final void testSetValueAvogadrosConstant() {
    ASTConstantNumber constant = new ASTConstantNumber();
    constant.setValue(6.023e23);
    assertTrue(constant.getType() == Type.NAME_AVOGADRO);
  }

}
