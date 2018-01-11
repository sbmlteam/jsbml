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
import org.sbml.jsbml.math.ASTQualifierNode;

/**
 * Test cases for {@link ASTQualifierNode}
 * 
 * @author Victor Kofia
 * @since 1.0
 */
public class ASTQualifierNodeTest {

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTQualifierNode#clone()}.
   */
  @Test
  public final void testClone() {
    ASTQualifierNode qualifier = new ASTQualifierNode();
    ASTQualifierNode unknown = qualifier.clone();
    assertTrue(qualifier.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTQualifierNode#clone()}.
   */
  @Test
  public final void testCloneWithType() {
    ASTQualifierNode qualifier = new ASTQualifierNode(Type.QUALIFIER_BVAR);
    ASTQualifierNode unknown = qualifier.clone();
    assertTrue(qualifier.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTQualifierNode#clone()}.
   */
  @Test
  public final void testCloneWithChildren() {
    ASTQualifierNode qualifier = new ASTQualifierNode(Type.QUALIFIER_BVAR);
    qualifier.addChild(new ASTCnIntegerNode(1));
    qualifier.addChild(new ASTCnIntegerNode(2));
    ASTQualifierNode unknown = qualifier.clone();
    assertTrue(qualifier.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTQualifierNode#ASTQualifierNode(org.sbml.jsbml.math.ASTQualifierNode)}.
   */
  @Test
  public final void testCloneWithConstructor() {
    ASTQualifierNode qualifier = new ASTQualifierNode();
    ASTQualifierNode unknown = new ASTQualifierNode(qualifier);
    assertTrue(qualifier.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTQualifierNode#isAllowableType(Type)}.
   */
  @Test
  public final void testIsAllowableType() {
    ASTQualifierNode qualifier = new ASTQualifierNode();
    assertTrue(qualifier.isAllowableType(Type.QUALIFIER_BVAR) &&
      qualifier.isAllowableType(Type.QUALIFIER_DEGREE) &&
      qualifier.isAllowableType(Type.QUALIFIER_LOGBASE) &&
      qualifier.isAllowableType(Type.CONSTRUCTOR_OTHERWISE) &&
      qualifier.isAllowableType(Type.CONSTRUCTOR_PIECE) &&
      !qualifier.isAllowableType(null));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTQualifierNode#toFormula()}.
   */
  @Test
  public final void testToFormulaBvar() {
    ASTQualifierNode qualifier = new ASTQualifierNode(Type.QUALIFIER_BVAR);
    qualifier.addChild(new ASTCnIntegerNode(1));
    System.out.println(qualifier.toFormula());
    assertTrue(qualifier.toFormula().equals("1"));
  }

}
