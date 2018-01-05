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
import org.sbml.jsbml.math.ASTCiFunctionNode;
import org.sbml.jsbml.math.ASTCiNumberNode;
import org.sbml.jsbml.math.ASTFactory;
import org.sbml.jsbml.math.ASTLambdaFunctionNode;
import org.sbml.jsbml.math.ASTPlusNode;
import org.sbml.jsbml.math.ASTQualifierNode;

/**
 * Test cases for {@link ASTLambdaFunctionNode}
 * 
 * @author Victor Kofia
 * @since 1.0
 */
public class ASTLambdaFunctionTest {

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTLambdaFunctionNode#clone()}.
   */
  @Test
  public final void testClone() {
    ASTLambdaFunctionNode lambda = new ASTLambdaFunctionNode();
    ASTLambdaFunctionNode unknown = lambda.clone();
    assertTrue(lambda.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTLambdaFunctionNode#clone()}.
   */
  @Test
  public final void testCloneWithChildren() {
    ASTLambdaFunctionNode lambda = new ASTLambdaFunctionNode();
    lambda.addChild(new ASTQualifierNode(Type.QUALIFIER_BVAR));
    lambda.addChild(new ASTQualifierNode(Type.QUALIFIER_DEGREE));
    ASTLambdaFunctionNode unknown = lambda.clone();
    assertTrue(lambda.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTLambdaFunctionNode#ASTLambdaFunctionNode(org.sbml.jsbml.math.ASTLambdaFunctionNode)}.
   */
  @Test
  public final void testCloneWithConstructor() {
    ASTLambdaFunctionNode lambda = new ASTLambdaFunctionNode();
    ASTLambdaFunctionNode unknown = new ASTLambdaFunctionNode(lambda);
    assertTrue(lambda.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTLambdaFunctionNode#getBvarCount()}.
   */
  @Test
  public final void testGetBvarCount() {
    ASTLambdaFunctionNode lambda = new ASTLambdaFunctionNode();
    assertTrue(lambda.getBvarCount() == 0);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTLambdaFunctionNode#getBvarCount()}.
   */
  @Test
  public final void testGetBvarCountWithChildren() {
    ASTLambdaFunctionNode lambda = new ASTLambdaFunctionNode();
    ASTQualifierNode bvarX = new ASTQualifierNode(Type.QUALIFIER_BVAR);
    ASTCiFunctionNode x = new ASTCiFunctionNode();
    x.setName("x");
    bvarX.addChild(x);
    ASTCiFunctionNode y = new ASTCiFunctionNode();
    y.setName("y");
    ASTQualifierNode bvarY = new ASTQualifierNode(Type.QUALIFIER_BVAR);
    bvarY.addChild(y);
    lambda.addChild(bvarX);
    lambda.addChild(bvarY);
    ASTPlusNode plus = new ASTPlusNode(x, y);
    lambda.addChild(plus);
    assertTrue(lambda.getBvarCount() == 2);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTLambdaFunctionNode#getBvarCount()}.
   */
  @Test
  public final void testGetBvarCountWithDuplicate() {
    ASTLambdaFunctionNode lambda = new ASTLambdaFunctionNode();
    ASTQualifierNode bvarX = new ASTQualifierNode(Type.QUALIFIER_BVAR);
    ASTCiFunctionNode x = new ASTCiFunctionNode();
    x.setName("x");
    bvarX.addChild(x);
    lambda.addChild(bvarX);
    lambda.addChild(x);
    assertTrue(lambda.getBvarCount() == 1);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTLambdaFunctionNode#isAllowableType(Type)}.
   */
  @Test
  public final void testIsAllowableType() {
    ASTLambdaFunctionNode lambda = new ASTLambdaFunctionNode();
    assertTrue(lambda.isAllowableType(Type.LAMBDA) && !lambda.isAllowableType(null));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTLambdaFunctionNode#replaceArgument(java.lang.String, org.sbml.jsbml.math.ASTNode2)}.
   */
  @Test
  public final void testReplaceArgument() {
    ASTLambdaFunctionNode lambda = new ASTLambdaFunctionNode();

    ASTQualifierNode bvarX = new ASTQualifierNode(Type.QUALIFIER_BVAR);
    bvarX.setName("bvarX");
    ASTCiFunctionNode x = new ASTCiFunctionNode();
    x.setName("x");
    bvarX.addChild(x);
    lambda.addChild(bvarX);

    ASTQualifierNode bvarY = new ASTQualifierNode(Type.QUALIFIER_BVAR);
    bvarY.setName("bvarY");
    ASTCiFunctionNode y = new ASTCiFunctionNode();
    y.setName("y");
    bvarY.addChild(y);

    lambda.replaceArgument("bvarX", bvarY);

    assertTrue(lambda.getChildAt(0).equals(bvarY));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTLambdaFunctionNode#toFormula()}.
   */
  @Test
  public final void testToFormula() {
    ASTLambdaFunctionNode lambda = new ASTLambdaFunctionNode();
    ASTQualifierNode bvarX = new ASTQualifierNode(Type.QUALIFIER_BVAR);
    ASTCiNumberNode x = new ASTCiNumberNode();
    x.setRefId("x");
    bvarX.addChild(x);
    ASTCiNumberNode y = new ASTCiNumberNode();
    y.setRefId("y");
    ASTQualifierNode bvarY = new ASTQualifierNode(Type.QUALIFIER_BVAR);
    bvarY.addChild(y);
    lambda.addChild(bvarX);
    lambda.addChild(bvarY);
    ASTPlusNode plus = new ASTPlusNode(x, y);
    lambda.addChild(plus);
    assertTrue(lambda.toFormula().equals("lambda(x, y, x+y)"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTLambdaFunctionNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeX() {
    ASTLambdaFunctionNode lambda = new ASTLambdaFunctionNode();
    ASTQualifierNode bvarX = new ASTQualifierNode(Type.QUALIFIER_BVAR);
    ASTCiNumberNode x = new ASTCiNumberNode();
    x.setRefId("x");
    bvarX.addChild(x);
    ASTCiNumberNode y = new ASTCiNumberNode();
    y.setRefId("y");
    ASTQualifierNode bvarY = new ASTQualifierNode(Type.QUALIFIER_BVAR);
    bvarY.addChild(y);
    lambda.addChild(bvarX);
    lambda.addChild(bvarY);
    ASTPlusNode plus = new ASTPlusNode(x, y);
    lambda.addChild(plus);
    assertTrue(lambda.toLaTeX().equals("\\lambda\\left(x, y\\right) = x+y"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTLambdaFunctionNode#toMathML()}.
   */
  @Test
  public final void testToMathML() {
    ASTLambdaFunctionNode lambda = new ASTLambdaFunctionNode();
    ASTQualifierNode bvarX = new ASTQualifierNode(Type.QUALIFIER_BVAR);
    ASTCiNumberNode x = new ASTCiNumberNode();
    x.setRefId("x");
    bvarX.addChild(x);
    ASTCiNumberNode y = new ASTCiNumberNode();
    y.setRefId("y");
    ASTQualifierNode bvarY = new ASTQualifierNode(Type.QUALIFIER_BVAR);
    bvarY.addChild(y);
    lambda.addChild(bvarX);
    lambda.addChild(bvarY);
    ASTPlusNode plus = new ASTPlusNode(x, y);
    lambda.addChild(plus);
    assertTrue(lambda.toMathML().equals(ASTFactory.parseMathML("lambda.xml")));
  }

}
