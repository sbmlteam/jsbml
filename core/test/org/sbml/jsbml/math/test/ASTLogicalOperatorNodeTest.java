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
import org.sbml.jsbml.math.ASTBoolean;
import org.sbml.jsbml.math.ASTFactory;
import org.sbml.jsbml.math.ASTLogicalOperatorNode;


/**
 * Test cases for {@link ASTLogicalOperatorNode}
 * 
 * @author Victor Kofia
 * @since 1.0
 */
public class ASTLogicalOperatorNodeTest {

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTLogicalOperatorNode#clone()}.
   */
  @Test
  public final void testClone() {
    ASTLogicalOperatorNode operator = new ASTLogicalOperatorNode();
    ASTLogicalOperatorNode unknown = operator.clone();
    assertTrue(operator.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTLogicalOperatorNode#ASTLogicalOperatorNode(org.sbml.jsbml.math.ASTLogicalOperatorNode)}.
   */
  @Test
  public final void testCloneWithConstructor() {
    ASTLogicalOperatorNode operator = new ASTLogicalOperatorNode();
    ASTLogicalOperatorNode unknown = new ASTLogicalOperatorNode(operator);
    assertTrue(operator.equals(unknown));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTLogicalOperatorNode#clone()}.
   */
  @Test
  public final void testCloneWithType() {
    ASTLogicalOperatorNode operator = new ASTLogicalOperatorNode(Type.LOGICAL_AND);
    ASTLogicalOperatorNode unknown = operator.clone();
    assertTrue(operator.equals(unknown));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTLogicalOperatorNode#isAllowableType(org.sbml.jsbml.ASTNode.Type)}.
   */
  @Test
  public final void testIsAllowableType() {
    ASTLogicalOperatorNode operator = new ASTLogicalOperatorNode();
    assertTrue(operator.isAllowableType(Type.LOGICAL_AND) && operator.isAllowableType(Type.LOGICAL_NOT)
            && operator.isAllowableType(Type.LOGICAL_OR) && operator.isAllowableType(Type.LOGICAL_XOR)
            && !operator.isAllowableType(null));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTLogicalOperatorNode#toFormula()}.
   */
  @Test
  public final void testToFormulaAnd() {
    ASTLogicalOperatorNode operator = new ASTLogicalOperatorNode(Type.LOGICAL_AND);
    operator.addChild(new ASTBoolean(Type.CONSTANT_TRUE));
    operator.addChild(new ASTBoolean(Type.CONSTANT_FALSE));
    assertTrue(operator.toFormula().equals("true && false"));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTLogicalOperatorNode#toFormula()}.
   */
  @Test
  public final void testToFormulaOr() {
    ASTLogicalOperatorNode operator = new ASTLogicalOperatorNode(Type.LOGICAL_OR);
    operator.addChild(new ASTBoolean(Type.CONSTANT_TRUE));
    operator.addChild(new ASTBoolean(Type.CONSTANT_FALSE));
    assertTrue(operator.toFormula().equals("true || false"));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTLogicalOperatorNode#toFormula()}.
   */
  @Test
  public final void testToFormulaNot() {
    ASTLogicalOperatorNode operator = new ASTLogicalOperatorNode(Type.LOGICAL_NOT);
    operator.addChild(new ASTBoolean(Type.CONSTANT_TRUE));
    assertTrue(operator.toFormula().equals("!(true)"));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTLogicalOperatorNode#toFormula()}.
   */
  @Test
  public final void testToFormulaXor() {
    ASTLogicalOperatorNode operator = new ASTLogicalOperatorNode(Type.LOGICAL_XOR);
    operator.addChild(new ASTBoolean(Type.CONSTANT_TRUE));
    operator.addChild(new ASTBoolean(Type.CONSTANT_FALSE));
    assertTrue(operator.toFormula().equals("true xor false"));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTLogicalOperatorNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeXAnd() {
    ASTLogicalOperatorNode operator = new ASTLogicalOperatorNode(Type.LOGICAL_AND);
    operator.addChild(new ASTBoolean(Type.CONSTANT_TRUE));
    operator.addChild(new ASTBoolean(Type.CONSTANT_FALSE));
    assertTrue(operator.toLaTeX().equals("\\mathrm{true}\\wedge \\mathrm{false}"));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTLogicalOperatorNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeXOr() {
    ASTLogicalOperatorNode operator = new ASTLogicalOperatorNode(Type.LOGICAL_OR);
    operator.addChild(new ASTBoolean(Type.CONSTANT_TRUE));
    operator.addChild(new ASTBoolean(Type.CONSTANT_FALSE));
    assertTrue(operator.toLaTeX().equals("\\mathrm{true}\\lor \\mathrm{false}"));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTLogicalOperatorNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeXNot() {
    ASTLogicalOperatorNode operator = new ASTLogicalOperatorNode(Type.LOGICAL_NOT);
    operator.addChild(new ASTBoolean(Type.CONSTANT_TRUE));
    assertTrue(operator.toLaTeX().equals("\\neg \\mathrm{true}"));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTLogicalOperatorNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeXXor() {
    ASTLogicalOperatorNode operator = new ASTLogicalOperatorNode(Type.LOGICAL_XOR);
    operator.addChild(new ASTBoolean(Type.CONSTANT_TRUE));
    operator.addChild(new ASTBoolean(Type.CONSTANT_FALSE));
    assertTrue(operator.toLaTeX().equals("\\mathrm{true}\\oplus \\mathrm{false}"));
  }  
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTLogicalOperatorNode#toMathML()}.
   */
  @Test
  public final void testToMathMLAnd() {
    ASTLogicalOperatorNode operator = new ASTLogicalOperatorNode(Type.LOGICAL_AND);
    operator.addChild(new ASTBoolean(Type.CONSTANT_TRUE));
    operator.addChild(new ASTBoolean(Type.CONSTANT_FALSE));
    assertTrue(operator.toMathML().equals(ASTFactory.parseMathML("and.xml")));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTLogicalOperatorNode#toMathML()}.
   */
  @Test
  public final void testToMathMLOr() {
    ASTLogicalOperatorNode operator = new ASTLogicalOperatorNode(Type.LOGICAL_OR);
    operator.addChild(new ASTBoolean(Type.CONSTANT_TRUE));
    operator.addChild(new ASTBoolean(Type.CONSTANT_FALSE));
    assertTrue(operator.toMathML().equals(ASTFactory.parseMathML("or.xml")));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTLogicalOperatorNode#toMathML()}.
   */
  @Test
  public final void testToMathMLNot() {
    ASTLogicalOperatorNode operator = new ASTLogicalOperatorNode(Type.LOGICAL_NOT);
    operator.addChild(new ASTBoolean(Type.CONSTANT_TRUE));
    assertTrue(operator.toMathML().equals(ASTFactory.parseMathML("not.xml")));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTLogicalOperatorNode#toMathML()}.
   */
  @Test
  public final void testToMathMLXor() {
    ASTLogicalOperatorNode operator = new ASTLogicalOperatorNode(Type.LOGICAL_XOR);
    operator.addChild(new ASTBoolean(Type.CONSTANT_TRUE));
    operator.addChild(new ASTBoolean(Type.CONSTANT_FALSE));
    assertTrue(operator.toMathML().equals(ASTFactory.parseMathML("xor.xml")));
  }  
  
}
