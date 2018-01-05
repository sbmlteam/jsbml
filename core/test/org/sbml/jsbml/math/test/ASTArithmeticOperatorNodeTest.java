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

import junit.framework.TestCase;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.math.ASTArithmeticOperatorNode;
import org.sbml.jsbml.math.ASTCnIntegerNode;
import org.sbml.jsbml.math.ASTFactory;

/**
 * Test cases for {@link ASTArithmeticOperatorNode}
 * 
 * @author Victor Kofia
 * @since 1.0
 */
public class ASTArithmeticOperatorNodeTest extends TestCase {

  /**
   * 
   */
  @Rule
  public ExpectedException exception = ExpectedException.none();

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTArithmeticOperatorNode#clone()}.
   */
  @Test
  public final void testClone() {
    ASTArithmeticOperatorNode operator = new ASTArithmeticOperatorNode();
    ASTArithmeticOperatorNode unknown = operator.clone();
    assertTrue(operator.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTArithmeticOperatorNode#clone()}.
   */
  @Test
  public final void testCloneWithChildren() {
    ASTArithmeticOperatorNode operator = new ASTArithmeticOperatorNode();
    operator.addChild(new ASTCnIntegerNode(1));
    operator.addChild(new ASTCnIntegerNode(10));
    ASTArithmeticOperatorNode unknown = operator.clone();
    assertTrue(operator.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTArithmeticOperatorNode#ASTArithmeticOperatorNode(org.sbml.jsbml.math.ASTArithmeticOperatorNode)}.
   */
  @Test
  public final void testCloneWithConstructor() {
    ASTArithmeticOperatorNode operator = new ASTArithmeticOperatorNode();
    ASTArithmeticOperatorNode unknown = new ASTArithmeticOperatorNode(operator);
    assertTrue(operator.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTArithmeticOperatorNode#isAllowableType(Type)}.
   */
  @Test
  public final void testIsAllowableType() {
    ASTArithmeticOperatorNode operator = new ASTArithmeticOperatorNode();
    assertTrue(operator.isAllowableType(Type.PLUS) && operator.isAllowableType(Type.MINUS)
      && operator.isAllowableType(Type.DIVIDE) && operator.isAllowableType(Type.TIMES)
      && operator.isAllowableType(Type.SUM) && operator.isAllowableType(Type.PRODUCT)
      && !operator.isAllowableType(null));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTArithmeticOperatorNode#setCharacter(char)}.
   */
  @Test
  public final void testSetCharacterDivide() {
    ASTArithmeticOperatorNode operator = new ASTArithmeticOperatorNode();
    operator.setCharacter('/');
    assertTrue(operator.getType() == Type.DIVIDE);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTArithmeticOperatorNode#setCharacter(char)}.
   */
  @Test
  public final void testSetCharacterMinus() {
    ASTArithmeticOperatorNode operator = new ASTArithmeticOperatorNode();
    operator.setCharacter('-');
    assertTrue(operator.getType() == Type.MINUS);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTArithmeticOperatorNode#setCharacter(char)}.
   */
  @Test
  public final void testSetCharacterPlus() {
    ASTArithmeticOperatorNode operator = new ASTArithmeticOperatorNode();
    operator.setCharacter('+');
    assertTrue(operator.getType() == Type.PLUS);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTArithmeticOperatorNode#setCharacter(char)}.
   */
  @Test
  public final void testSetCharacterTimes() {
    ASTArithmeticOperatorNode operator = new ASTArithmeticOperatorNode();
    operator.setCharacter('*');
    assertTrue(operator.getType() == Type.TIMES);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTArithmeticOperatorNode#toFormula()}.
   */
  @Test
  public final void testToFormulaDivide() {
    ASTArithmeticOperatorNode divide = new ASTArithmeticOperatorNode(Type.DIVIDE);
    divide.addChild(new ASTCnIntegerNode(1));
    divide.addChild(new ASTCnIntegerNode(1));
    assertTrue(divide.toFormula().equals("1/1"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTArithmeticOperatorNode#toFormula()}.
   */
  @Test
  public final void testToFormulaMinus() {
    ASTArithmeticOperatorNode minus = new ASTArithmeticOperatorNode(Type.MINUS);
    minus.addChild(new ASTCnIntegerNode(1));
    minus.addChild(new ASTCnIntegerNode(1));
    assertTrue(minus.toFormula().equals("1-1"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTArithmeticOperatorNode#toFormula()}.
   */
  @Test
  public final void testToFormulaPlus() {
    ASTArithmeticOperatorNode plus = new ASTArithmeticOperatorNode(Type.PLUS);
    plus.addChild(new ASTCnIntegerNode(1));
    plus.addChild(new ASTCnIntegerNode(1));
    assertTrue(plus.toFormula().equals("1+1"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTArithmeticOperatorNode#toFormula()}.
   */
  @Test
  public final void testToFormulaTimes() {
    ASTArithmeticOperatorNode times = new ASTArithmeticOperatorNode(Type.TIMES);
    times.addChild(new ASTCnIntegerNode(1));
    times.addChild(new ASTCnIntegerNode(1));
    assertTrue(times.toFormula().equals("1*1"));
  }

  //  /**
  //   * Test method for {@link org.sbml.jsbml.math.ASTArithmeticOperatorNode#setCharacter(char)}.
  //   */
  //  @Test
  //  public final void testSetCharacterUnknown() {
  //    ASTArithmeticOperatorNode operator = new ASTArithmeticOperatorNode();
  //    exception.expect(IllegalArgumentException.class);
  //    operator.setCharacter('!');
  //  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTArithmeticOperatorNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeXDivide() {
    ASTArithmeticOperatorNode divide = new ASTArithmeticOperatorNode(Type.DIVIDE);
    divide.addChild(new ASTCnIntegerNode(1));
    divide.addChild(new ASTCnIntegerNode(1));
    assertTrue(divide.toLaTeX().equals("\\frac{1}{1}"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTArithmeticOperatorNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeXMinus() {
    ASTArithmeticOperatorNode minus = new ASTArithmeticOperatorNode(Type.MINUS);
    minus.addChild(new ASTCnIntegerNode(1));
    minus.addChild(new ASTCnIntegerNode(1));
    assertTrue(minus.toLaTeX().equals("1-1"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTArithmeticOperatorNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeXPlus() {
    ASTArithmeticOperatorNode plus = new ASTArithmeticOperatorNode(Type.PLUS);
    plus.addChild(new ASTCnIntegerNode(1));
    plus.addChild(new ASTCnIntegerNode(1));
    assertTrue(plus.toLaTeX().equals("1+1"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTArithmeticOperatorNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeXTimes() {
    ASTArithmeticOperatorNode times = new ASTArithmeticOperatorNode(Type.TIMES);
    times.addChild(new ASTCnIntegerNode(1));
    times.addChild(new ASTCnIntegerNode(1));
    assertTrue(times.toLaTeX().equals("1\\cdot 1"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTArithmeticOperatorNode#toMathML()}.
   */
  @Test
  public final void testToMathMLDivide() {
    ASTArithmeticOperatorNode divide = new ASTArithmeticOperatorNode(Type.DIVIDE);
    divide.addChild(new ASTCnIntegerNode(1));
    divide.addChild(new ASTCnIntegerNode(1));
    assertTrue(divide.toMathML().equals(ASTFactory.parseMathML("divide.xml")));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTArithmeticOperatorNode#toMathML()}.
   */
  @Test
  public final void testToMathMLMinus() {
    ASTArithmeticOperatorNode minus = new ASTArithmeticOperatorNode(Type.MINUS);
    minus.addChild(new ASTCnIntegerNode(1));
    minus.addChild(new ASTCnIntegerNode(1));
    assertTrue(minus.toMathML().equals(ASTFactory.parseMathML("minus.xml")));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTArithmeticOperatorNode#toMathML()}.
   */
  @Test
  public final void testToMathMLPlus() {
    ASTArithmeticOperatorNode plus = new ASTArithmeticOperatorNode(Type.PLUS);
    plus.addChild(new ASTCnIntegerNode(1));
    plus.addChild(new ASTCnIntegerNode(1));
    assertTrue(plus.toMathML().equals(ASTFactory.parseMathML("plus.xml")));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTArithmeticOperatorNode#toMathML()}.
   */
  @Test
  public final void testToMathMLTimes() {
    ASTArithmeticOperatorNode times = new ASTArithmeticOperatorNode(Type.TIMES);
    times.addChild(new ASTCnIntegerNode(1));
    times.addChild(new ASTCnIntegerNode(1));
    assertTrue(times.toMathML().equals(ASTFactory.parseMathML("times.xml")));
  }

}
