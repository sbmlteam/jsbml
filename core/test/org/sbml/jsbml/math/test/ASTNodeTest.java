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
import org.sbml.jsbml.ASTNode;

/**
 * @author Victor Kofia
 * @version $Rev$
 * @since 1.0
 * @date May 1, 2014
 */
public class ASTNodeTest {

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#getChildren()}.
   */
  @Test
  public final void testNoChildren() {
    ASTNode node = new ASTNode();
    assertTrue(node.getChildren().size() == 0);
  }


  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#getChildCount()}.
   */
  @Test
  public void testGetChildCount() {
    ASTNode node = new ASTNode();
    assertTrue(node.getChildCount() == 0);
  }


  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#getInteger()} and
   * {@link org.sbml.jsbml.ASTNode#setValue(int value)}.
   */
  @Test
  public void testInteger() {
    ASTNode integerNode = new ASTNode(21);
    assertTrue(integerNode.isInteger());
    assertTrue(integerNode.getInteger() == 21);
    integerNode.setValue(27);
    assertTrue(integerNode.getInteger() == 27);
  }


  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#getReal()} and
   * {@link org.sbml.jsbml.ASTNode#setValue(double value)}.
   */
  @Test
  public void testReal() {
    ASTNode realNode = new ASTNode(3.14);
    assertTrue(realNode.isReal());
    assertTrue(realNode.getReal() == 3.14);
    realNode.setValue(6.28);
    assertTrue(realNode.getReal() == 6.28);
  }


  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#setValue(double mantissa, int exponent)},
   * {@link org.sbml.jsbml.ASTNode#getMantissa()},
   * {@link org.sbml.jsbml.ASTNode#getExponent()} and
   * {@link org.sbml.jsbml.ASTNode#getReal()}.
   */
  @Test
  public final void testRealE() {
    ASTNode realENode = new ASTNode(0.14, 4);
    assertTrue(realENode.isReal());
    assertTrue(realENode.getReal() == 1400.0);
    assertTrue(realENode.getMantissa() == 0.14);
    assertTrue(realENode.getExponent() == 4);
    realENode.setValue(0.27, 3);
    assertTrue(realENode.getReal() == 270.0);
    assertTrue(realENode.getMantissa() == 0.27);
    assertTrue(realENode.getExponent() == 3);
  }


  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#setValue(int numerator, int denominator)},
   * {@link org.sbml.jsbml.ASTNode#getNumerator()} and
   * {@link org.sbml.jsbml.ASTNode#getDenominator()}.
   */
  @Test
  public void testRational() {
    ASTNode rationalNode = new ASTNode();
    rationalNode.setValue(22, 7);
    assertTrue(rationalNode.isReal());
    assertTrue(rationalNode.getNumerator() == 22);
    assertTrue(rationalNode.getDenominator() == 7);
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#eq(ASTNode left, ASTNode right)}.
   */
  @Test
  public void testRelationalEq() {
    ASTNode nodeA = new ASTNode(1), nodeB = new ASTNode(1);
    ASTNode relationalEq = ASTNode.eq(nodeA, nodeB);
    assertTrue(relationalEq.isRelational());
    assertTrue(relationalEq.getLeftChild().equals(nodeA));
    assertTrue(relationalEq.getRightChild().equals(nodeB));
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#geq(ASTNode left, ASTNode right)}.
   */
  @Test
  public void testRelationalGeq() {
    ASTNode five = new ASTNode(5), three = new ASTNode(3);
    ASTNode geq = ASTNode.geq(five, three);
    assertTrue(geq.isRelational());
    assertTrue(geq.getLeftChild().equals(five));
    assertTrue(geq.getRightChild().equals(three));
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#gt(ASTNode left, ASTNode right)}.
   */
  @Test
  public void testRelationalGt() {
    ASTNode six = new ASTNode(6), two = new ASTNode(2);
    ASTNode gt = ASTNode.gt(six, two);
    assertTrue(gt.isRelational());
    assertTrue(gt.getLeftChild().equals(six));
    assertTrue(gt.getRightChild().equals(two));
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#leq(ASTNode left, ASTNode right)}.
   */
  @Test
  public void testRelationalLeq() {
    ASTNode three = new ASTNode(3), one = new ASTNode(1);
    ASTNode leq = ASTNode.leq(one, three);
    assertTrue(leq.isRelational());
    assertTrue(leq.getLeftChild().equals(one));
    assertTrue(leq.getRightChild().equals(three));
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#lt(ASTNode left, ASTNode right)}.
   */
  @Test
  public void testRelationalLt() {
    ASTNode seven = new ASTNode(7), five = new ASTNode(5);
    ASTNode lt = ASTNode.lt(five, seven);
    assertTrue(lt.isRelational());
    assertTrue(lt.getLeftChild().equals(five));
    assertTrue(lt.getRightChild().equals(seven));
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#neq(ASTNode left, ASTNode right)}.
   */
  @Test
  public void testRelationalNeq() {
    ASTNode nine = new ASTNode(9), ten = new ASTNode(10);
    ASTNode neq = ASTNode.neq(nine, ten);
    assertTrue(neq.isRelational());
    assertTrue(neq.getLeftChild().equals(nine));
    assertTrue(neq.getRightChild().equals(ten));
  }


  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#diff(ASTNode... ast)}.
   */
  @Test
  public void testMinus() {
    ASTNode[] nodeList = new ASTNode[10];
    for (int i = 0; i < 10; i++) {
      nodeList[i] = new ASTNode(i);
    }
    ASTNode minus = ASTNode.diff(nodeList);
    assertTrue(minus.isOperator());
    for (int j = 0; j < 10; j++) {
      assertTrue(minus.getChild(j).equals(nodeList[j]));
    }
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#sum(ASTNode... ast)}.
   */
  @Test
  public void testPlus() {
    ASTNode four = new ASTNode(4), eight = new ASTNode(8);
    ASTNode plus = ASTNode.sum(four, eight);
    assertTrue(plus.isOperator());
    assertTrue(plus.getLeftChild().equals(four));
    assertTrue(plus.getRightChild().equals(eight));
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#times(ASTNode... ast)}.
   */
  @Test
  public void testTimes() {
    ASTNode nodeA = new ASTNode(5), nodeB = new ASTNode(5);
    ASTNode times = ASTNode.times(nodeA, nodeB);
    assertTrue(times.isOperator());
    assertTrue(times.getLeftChild().equals(nodeA));
    assertTrue(times.getRightChild().equals(nodeB));
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#exp(ASTNode exponent)}.
   */
  @Test
  public void testPower() {
    ASTNode exponent = new ASTNode(2);
    ASTNode eNode = ASTNode.exp(exponent);
    assertTrue(eNode.isOperator());
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#frac(ASTNode numerator, ASTNode denominator)}.
   */
  @Test
  public void testFrac() {
    ASTNode numerator = new ASTNode(22), denominator = new ASTNode(7);
    ASTNode frac = ASTNode.frac(numerator, denominator);
    assertTrue(frac.isOperator());
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#log(ASTNode value)}.
   */
  @Test
  public void testLogBase10() {
    ASTNode two = new ASTNode(2);
    ASTNode log = ASTNode.log(two);
    //assertTrue(log.isLog10());
    assertTrue(log.isFunction());
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#log(ASTNode base, ASTNode value)}.
   */
  @Test
  public void testLogWithBase() {
    ASTNode two = new ASTNode(2), four = new ASTNode(4);
    ASTNode log = ASTNode.log(two, four);
    assertTrue(log.isFunction());
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#pow(ASTNode base, ASTNode value)}.
   */
  @Test
  public void testPow() {
    ASTNode two = new ASTNode(2), three = new ASTNode(3);
    ASTNode pow = ASTNode.pow(two, three);
    assertTrue(pow.isOperator());
    //assertTrue(pow.getLeftChild().equals(two));
    //assertTrue(pow.getRightChild().equals(three));
  }

}
