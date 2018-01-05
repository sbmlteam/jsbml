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

import org.junit.Test;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.math.ASTCnIntegerNode;
import org.sbml.jsbml.math.ASTFactory;
import org.sbml.jsbml.math.ASTPlusNode;


/**
 * Test cases for {@link ASTPlusNode}
 * 
 * @author Victor Kofia
 * @since 1.0
 */
public class ASTPlusNodeTest {

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTPlusNode#clone()}.
   */
  @Test
  public final void testClone() {
    ASTPlusNode plus = new ASTPlusNode();
    ASTPlusNode unknown = plus.clone();
    assertTrue(plus.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTPlusNode#clone()}.
   */
  @Test
  public final void testCloneWithChildren() {
    ASTPlusNode plus = new ASTPlusNode(new ASTCnIntegerNode(1), new ASTCnIntegerNode(1));
    ASTPlusNode unknown = plus.clone();
    assertTrue(plus.equals(unknown));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTPlusNode#ASTPlusNode(org.sbml.jsbml.math.ASTPlusNode)}.
   */
  @Test
  public final void testCloneWithConstructor() {
    ASTPlusNode plus = new ASTPlusNode();
    ASTPlusNode unknown = new ASTPlusNode(plus);
    assertTrue(plus.equals(unknown));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTPlusNode#isAllowableType(org.sbml.jsbml.ASTNode.Type)}.
   */
  @Test
  public final void testIsAllowableType() {
    ASTPlusNode plus = new ASTPlusNode();
    assertTrue(plus.isAllowableType(Type.PLUS) && !plus.isAllowableType(null));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTPlusNode#toFormula()}.
   */
  @Test
  public final void testToFormula() {
    ASTPlusNode plus = new ASTPlusNode(new ASTCnIntegerNode(1), new ASTCnIntegerNode(1));
    assertTrue(plus.toFormula().equals("1+1"));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTPlusNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeX() {
    ASTPlusNode plus = new ASTPlusNode(new ASTCnIntegerNode(1), new ASTCnIntegerNode(1));
    assertTrue(plus.toLaTeX().equals("1+1"));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTPlusNode#toMathML()}.
   */
  @Test
  public final void testToMathML() {
    ASTPlusNode plus = new ASTPlusNode(new ASTCnIntegerNode(1), new ASTCnIntegerNode(1));
    assertTrue(plus.toMathML().equals(ASTFactory.parseMathML("plus.xml")));
  }
  
}
