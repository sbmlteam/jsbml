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
import org.sbml.jsbml.math.ASTPowerNode;

/**
 * Test cases for {@link ASTPowerNode}
 * 
 * @author Victor Kofia
 * @since 1.0
 */
public class ASTPowerNodeTest {

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTPowerNode#clone()}.
   */
  @Test
  public final void testClone() {
    ASTPowerNode power = new ASTPowerNode();
    ASTPowerNode unknown = power.clone();
    assertTrue(power.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTPowerNode#clone()}.
   */
  @Test
  public final void testCloneWithChildren() {
    ASTPowerNode power = new ASTPowerNode(new ASTCnIntegerNode(2), new ASTCnIntegerNode(5));
    ASTPowerNode unknown = power.clone();
    assertTrue(power.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTPowerNode#ASTPowerNode(org.sbml.jsbml.math.ASTPowerNode)}.
   */
  @Test
  public final void testCloneWithConstructor() {
    ASTPowerNode power = new ASTPowerNode();
    ASTPowerNode unknown = new ASTPowerNode(power);
    assertTrue(power.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTPowerNode#isAllowableType(Type)}.
   */
  @Test
  public final void testIsAllowableType() {
    ASTPowerNode power = new ASTPowerNode();
    assertTrue(power.isAllowableType(Type.POWER) && !power.isAllowableType(null));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTPowerNode#toFormula()}.
   */
  @Test
  public final void testToFormula() {
    ASTPowerNode power = new ASTPowerNode(new ASTCnIntegerNode(2), new ASTCnIntegerNode(5));
    assertTrue(power.toFormula().equals("2^5"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTPowerNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeX() {
    ASTPowerNode power = new ASTPowerNode(new ASTCnIntegerNode(2), new ASTCnIntegerNode(5));
    assertTrue(power.toLaTeX().equals("2^{5}"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTPowerNode#toMathML()}.
   */
  @Test
  public final void testToMathML() {
    ASTPowerNode power = new ASTPowerNode(new ASTCnIntegerNode(2), new ASTCnIntegerNode(5));
    assertTrue(power.toMathML().equals(ASTFactory.parseMathML("power.xml")));
  }

}
