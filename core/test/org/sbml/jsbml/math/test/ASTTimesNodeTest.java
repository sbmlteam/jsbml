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

import org.junit.Test;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.math.ASTCnIntegerNode;
import org.sbml.jsbml.math.ASTFactory;
import org.sbml.jsbml.math.ASTTimesNode;

/**
 * Test cases for {@link ASTTimesNode}
 * 
 * @author Victor Kofia
 * @since 1.0
 */
public class ASTTimesNodeTest {

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTTimesNode#clone()}.
   */
  @Test
  public final void testClone() {
    ASTTimesNode times = new ASTTimesNode();
    ASTTimesNode unknown = times.clone();
    assertTrue(times.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTTimesNode#clone()}.
   */
  @Test
  public final void testCloneWithChildren() {
    ASTTimesNode times = new ASTTimesNode(new ASTCnIntegerNode(1), new ASTCnIntegerNode(5));
    ASTTimesNode unknown = times.clone();
    assertTrue(times.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTTimesNode#ASTTimesNode(org.sbml.jsbml.math.ASTTimesNode)}.
   */
  @Test
  public final void testCloneWithConstructor() {
    ASTTimesNode times = new ASTTimesNode();
    ASTTimesNode unknown = new ASTTimesNode(times);
    assertTrue(times.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTTimesNode#isAllowableType(Type)}.
   */
  @Test
  public final void testIsAllowableType() {
    ASTTimesNode times = new ASTTimesNode();
    assertTrue(times.isAllowableType(Type.TIMES) && !times.isAllowableType(null));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTTimesNode#toFormula()}.
   */
  @Test
  public final void testToFormula() {
    ASTTimesNode times = new ASTTimesNode(new ASTCnIntegerNode(1), new ASTCnIntegerNode(5));
    assertTrue(times.toFormula().equals("1*5"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTTimesNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeX() {
    ASTTimesNode times = new ASTTimesNode(new ASTCnIntegerNode(1), new ASTCnIntegerNode(5));
    assertTrue(times.toLaTeX().equals("1\\cdot 5"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTTimesNode#toMathML()}.
   */
  @Test
  public final void testToMathML() {
    ASTTimesNode times = new ASTTimesNode(new ASTCnIntegerNode(1), new ASTCnIntegerNode(1));
    assertTrue(times.toMathML().equals(ASTFactory.parseMathML("times.xml")));
  }

}
