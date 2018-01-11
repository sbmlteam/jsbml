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
import org.sbml.jsbml.math.ASTCnIntegerNode;
import org.sbml.jsbml.math.ASTFactory;


/**
 * Test cases for {@link ASTCnIntegerNode}
 * 
 * @author Victor Kofia
 * @since 1.0
 */
public class ASTCnIntegerNodeTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnIntegerNode#clone()}.
   */
  @Test
  public final void testClone() {
    ASTCnIntegerNode integer = new ASTCnIntegerNode();
    ASTCnIntegerNode unknown = integer.clone();
    assertTrue(integer.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnIntegerNode#ASTCnIntegerNode(org.sbml.jsbml.math.ASTCnIntegerNode)}.
   */
  @Test
  public final void testCloneWithConstructor() {
    ASTCnIntegerNode integer = new ASTCnIntegerNode();
    ASTCnIntegerNode unknown = new ASTCnIntegerNode(integer);
    assertTrue(integer.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnIntegerNode#clone()}.
   */
  @Test
  public final void testCloneWithValue() {
    ASTCnIntegerNode integer = new ASTCnIntegerNode(10);
    ASTCnIntegerNode unknown = integer.clone();
    assertTrue(integer.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnIntegerNode#getInteger()}.
   */
  @Test
  public void testGetIntegerNoValue() {
    ASTCnIntegerNode integer = new ASTCnIntegerNode();
    exception.expect(PropertyUndefinedError.class);
    integer.getInteger();
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnIntegerNode#getInteger()}.
   */
  @Test
  public void testGetIntegerWithValue() {
    ASTCnIntegerNode integer = new ASTCnIntegerNode(10);
    assertTrue(integer.getInteger() == 10);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnIntegerNode#isAllowableType(org.sbml.jsbml.ASTNode.Type)}.
   */
  @Test
  public final void testIsAllowableType() {
    ASTCnIntegerNode integer = new ASTCnIntegerNode();
    assertTrue(integer.isAllowableType(Type.INTEGER) && !integer.isAllowableType(null));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnIntegerNode#isSetInteger()}.
   */
  @Test
  public final void testIsSetInteger() {
    ASTCnIntegerNode isSet = new ASTCnIntegerNode(10);
    ASTCnIntegerNode notSet = new ASTCnIntegerNode();
    assertTrue(isSet.isSetInteger() && !notSet.isSetInteger());
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnIntegerNode#toFormula()}.
   */
  @Test
  public final void testToFormula() {
    ASTCnIntegerNode ten = new ASTCnIntegerNode(10);
    assertTrue(ten.toFormula().equals("10"));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnIntegerNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeX() {
    ASTCnIntegerNode ten = new ASTCnIntegerNode(10);
    assertTrue(ten.toLaTeX().equals("10"));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCnIntegerNode#toMathML()}.
   */
  @Test
  public final void testToMathML() {
    ASTCnIntegerNode ten = new ASTCnIntegerNode(10);
    assertTrue(ten.toMathML().equals(ASTFactory.parseMathML("integer.xml")));
  }

}
