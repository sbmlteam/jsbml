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
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.math.ASTCnNumberNode;


/**
 * Test cases for {@link ASTCnNumberNode}
 * 
 * @author Victor Kofia
 * @version $Rev$
 * @since 1.0
 * @date Jul 25, 2014
 */
public class ASTCnNumberNodeTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnNumberNode#clone()}.
   */
  @Test
  public void testClone() {
    ASTCnNumberNode<Integer> number = new ASTCnNumberNode<Integer>();
    ASTCnNumberNode<Integer> unknown = number.clone();
    assertTrue(number.equals(unknown));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnNumberNode#clone()}.
   */
  @Test
  public void testCloneWithConstructor() {
    ASTCnNumberNode<Integer> number = new ASTCnNumberNode<Integer>();
    ASTCnNumberNode<Integer> unknown = new ASTCnNumberNode<Integer>(number);
    assertTrue(number.equals(unknown));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnNumberNode#clone()}.
   */
  @Test
  public void testCloneWithValue() {
    ASTCnNumberNode<Integer> number = new ASTCnNumberNode<Integer>();
    number.setNumber(9);
    ASTCnNumberNode<Integer> unknown = new ASTCnNumberNode<Integer>(number);
    assertTrue(number.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnNumberNode#getNumber()}.
   */
  @Test
  public void testGetNumberNoValue() {
    ASTCnNumberNode<Integer> number = new ASTCnNumberNode<Integer>();
    exception.expect(PropertyUndefinedError.class);
    number.getNumber();
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnNumberNode#getNumber()}.
   */
  @Test
  public void testGetNumberWithValue() {
    ASTCnNumberNode<Integer> number = new ASTCnNumberNode<Integer>();
    number.setNumber(10);
    assertTrue(number.getNumber() == 10);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnNumberNode#getUnits()}.
   */
  @Test
  public void testGetUnitsExists() {
    ASTCnNumberNode<Integer> number = new ASTCnNumberNode<Integer>();
    number.setUnits("mg");
    assertTrue(number.getUnits().equals("mg"));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnNumberNode#getUnits()}.
   */
  @Test
  public void testGetUnitsNonExistent() {
    ASTCnNumberNode<Integer> number = new ASTCnNumberNode<Integer>();
    exception.expect(PropertyUndefinedError.class);
    number.getNumber();
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnNumberNode#getUnits()}.
   */
  @Test
  public void testGetUnitsNonExistentNonStrict() {
    ASTCnNumberNode<Integer> number = new ASTCnNumberNode<Integer>();
    number.setStrictness(false);
    assertTrue(number.getUnits().isEmpty());
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnNumberNode#getVariable()}.
   */
  @Test
  public void testGetVariableExists() {
    ASTCnNumberNode<Integer> number = new ASTCnNumberNode<Integer>();
    number.setVariable("mg");
    assertTrue(number.getVariable().equals("mg"));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnNumberNode#getVariable()}.
   */
  @Test
  public void testGetVariableNonExistent() {
    ASTCnNumberNode<Integer> number = new ASTCnNumberNode<Integer>();
    exception.expect(PropertyUndefinedError.class);
    number.getVariable();
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnNumberNode#getVariable()}.
   */
  @Test
  public void testGetVariableNonExistentNonStrict() {
    ASTCnNumberNode<Integer> number = new ASTCnNumberNode<Integer>();
    number.setStrictness(false);
    assertTrue(number.getVariable().isEmpty());
  }  
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnNumberNode#isAllowableType()}.
   */
  @Test
  public void testIsAllowableType() {
    ASTCnNumberNode<Integer> number = new ASTCnNumberNode<Integer>();
    assertTrue(number.isAllowableType(Type.INTEGER) && number.isAllowableType(Type.REAL)
            && number.isAllowableType(Type.RATIONAL) && !number.isAllowableType(null));
  }  
  
  
}
