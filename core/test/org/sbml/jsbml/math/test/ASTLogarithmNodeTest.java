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

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.math.ASTBinaryFunctionNode;
import org.sbml.jsbml.math.ASTCnIntegerNode;
import org.sbml.jsbml.math.ASTConstantNumber;
import org.sbml.jsbml.math.ASTFactory;
import org.sbml.jsbml.math.ASTLogarithmNode;

/**
 * Test cases for the ASTLogarithmNode class
 * 
 * @author Victor Kofia
 * @since 1.0
 */
public class ASTLogarithmNodeTest {

  /**
   * 
   */
  @Rule
  public ExpectedException exception = ExpectedException.none();

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTLogarithmNode#clone()}.
   */
  @Test
  public final void testClone() {
    ASTLogarithmNode log = new ASTLogarithmNode();
    ASTLogarithmNode unknown = log.clone();
    assertTrue(log.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTLogarithmNode#clone()}.
   */
  @Test
  public final void testCloneWithChildren() {
    ASTLogarithmNode log = new ASTLogarithmNode(new ASTConstantNumber(Type.CONSTANT_E), new ASTCnIntegerNode(10));
    ASTLogarithmNode unknown = new ASTLogarithmNode(log);
    assertTrue(log.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTLogarithmNode#ASTLogarithmNode(org.sbml.jsbml.math.ASTLogarithmNode)}.
   */
  @Test
  public final void testCloneWithConstructor() {
    ASTLogarithmNode log = new ASTLogarithmNode();
    ASTLogarithmNode unknown = new ASTLogarithmNode(log);
    assertTrue(log.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTLogarithmNode#getType()}.
   */
  @Test
  public final void testDefaultBase10() {
    ASTBinaryFunctionNode log = new ASTLogarithmNode();
    assertTrue(log.getType() == Type.FUNCTION_LOG);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTLogarithmNode#getType()}.
   */
  @Test
  public final void testDefaultBase10wChild() {
    ASTBinaryFunctionNode log = new ASTLogarithmNode(new ASTCnIntegerNode(10));
    assertTrue(log.getType() == Type.FUNCTION_LOG);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTLogarithmNode#getType()}.
   */
  @Test
  public final void testDefaultBase10wSetLeftChild() {
    ASTConstantNumber e = new ASTConstantNumber(Type.CONSTANT_E);
    ASTCnIntegerNode one = new ASTCnIntegerNode(1);
    ASTBinaryFunctionNode log = new ASTLogarithmNode(e, one);
    log.setLeftChild(new ASTCnIntegerNode(10));
    assertTrue(log.getType() == Type.FUNCTION_LOG);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTLogarithmNode#getBase()}.
   */
  @Test
  public final void testGetBaseDefaultBase10() {
    ASTLogarithmNode log = new ASTLogarithmNode(new ASTCnIntegerNode(1));
    assertTrue(log.getBase().equals(new ASTCnIntegerNode(10)));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTLogarithmNode#getBase()}.
   */
  @Test
  public final void testGetBaseNaturalLog() {
    ASTConstantNumber e = new ASTConstantNumber(Type.CONSTANT_E);
    ASTLogarithmNode ln = new ASTLogarithmNode(e, new ASTCnIntegerNode(1));
    assertTrue(ln.getBase().equals(e));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTLogarithmNode#getBase()}.
   */
  @Test
  public final void testGetBaseOther() {
    ASTCnIntegerNode two = new ASTCnIntegerNode(2);
    ASTLogarithmNode log = new ASTLogarithmNode(two, new ASTCnIntegerNode(1));
    assertTrue(log.getBase().equals(two));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTLogarithmNode#getValue()}.
   */
  @Test
  public final void testGetValueDefaultBase10() {
    ASTCnIntegerNode one = new ASTCnIntegerNode(1);
    ASTLogarithmNode log = new ASTLogarithmNode(one);
    assertTrue(log.getValue().equals(one));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTLogarithmNode#getValue()}.
   */
  @Test
  public final void testGetValueNaturalLog() {
    ASTCnIntegerNode one = new ASTCnIntegerNode(1);
    ASTLogarithmNode ln = new ASTLogarithmNode(new ASTConstantNumber(Type.CONSTANT_E), one);
    assertTrue(ln.getValue().equals(one));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTLogarithmNode#isAllowableType(Type)}.
   */
  @Test
  public final void testIsAllowableType() {
    ASTLogarithmNode log = new ASTLogarithmNode();
    assertTrue(log.isAllowableType(Type.FUNCTION_LOG) && log.isAllowableType(Type.FUNCTION_LN)
      && !log.isAllowableType(null));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTLogarithmNode#setType(Type)}.
   */
  @Test
  public final void testNaturalLogReplace() {
    ASTCnIntegerNode one = new ASTCnIntegerNode(1);
    ASTBinaryFunctionNode ln = new ASTLogarithmNode(one);
    ASTConstantNumber e = new ASTConstantNumber(Type.CONSTANT_E);
    ln.setLeftChild(e);
    assertTrue(ln.getType() == Type.FUNCTION_LN);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTLogarithmNode#getType()}.
   */
  @Test
  public final void testNaturalLogWithConstructor() {
    ASTConstantNumber e = new ASTConstantNumber(Type.CONSTANT_E);
    ASTCnIntegerNode one = new ASTCnIntegerNode(1);
    ASTBinaryFunctionNode ln = new ASTLogarithmNode(e, one);
    assertTrue(ln.getType() == Type.FUNCTION_LN);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTLogarithmNode#getType()}.
   */
  @Test
  public final void testNaturalLogWithSetLeftChild() {
    ASTConstantNumber e = new ASTConstantNumber(Type.CONSTANT_E);
    ASTCnIntegerNode one = new ASTCnIntegerNode(1);
    ASTLogarithmNode ln = new ASTLogarithmNode(one);
    ln.setLeftChild(e);
    assertTrue(ln.getType() == Type.FUNCTION_LN);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTLogarithmNode#toFormula()}.
   */
  @Test
  public final void testToFormulaLog() {
    ASTLogarithmNode log = new ASTLogarithmNode(new ASTCnIntegerNode(1));
    assertTrue(log.toFormula().equals("log10(1)"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTLogarithmNode#toFormula()}.
   */
  @Test
  public final void testToFormulaNaturalLog() {
    ASTConstantNumber e = new ASTConstantNumber(Type.CONSTANT_E);
    ASTLogarithmNode ln = new ASTLogarithmNode(e, new ASTCnIntegerNode(1));
    assertTrue(ln.toFormula().equals("log(1)"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTLogarithmNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeXLog() {
    ASTLogarithmNode log = new ASTLogarithmNode(new ASTCnIntegerNode(1));
    assertTrue(log.toLaTeX().equals("\\log{\\left(1\\right)}"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTLogarithmNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeXNaturalLog() {
    ASTConstantNumber e = new ASTConstantNumber(Type.CONSTANT_E);
    ASTLogarithmNode ln = new ASTLogarithmNode(e, new ASTCnIntegerNode(1));
    assertTrue(ln.toLaTeX().equals("\\ln{\\left(1\\right)}"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTLogarithmNode#toMathML()}.
   */
  @Test
  public final void testToMathMLLog() {
    ASTLogarithmNode log = new ASTLogarithmNode(new ASTCnIntegerNode(1));
    assertTrue(log.toMathML().equals(ASTFactory.parseMathML("log.xml")));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTLogarithmNode#toMathML()}.
   */
  @Test
  public final void testToMathMLNaturalLog() {
    ASTConstantNumber e = new ASTConstantNumber(Type.CONSTANT_E);
    ASTLogarithmNode ln = new ASTLogarithmNode(e, new ASTCnIntegerNode(1));
    assertTrue(ln.toMathML().equals(ASTFactory.parseMathML("ln.xml")));
  }

}
