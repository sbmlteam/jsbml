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
import org.sbml.jsbml.ASTNode;

/**
 * @author Victor Kofia
 * @since 1.0
 */
public class ASTNodeTest {

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#getChildren()}.
   */
  @Test
  public final void testNoChildren() {
    ASTNode loneNode = new ASTNode();
    assertTrue(loneNode.getChildren().size() == 0);
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#getChildCount()}.
   */
  @Test
  public void testGetChildCount() {
    ASTNode loneNode = new ASTNode();
    assertTrue(loneNode.getChildCount() == 0);
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
   * Test method for {@link org.sbml.jsbml.ASTNode#isOne()}.
   */
  @Test
  public void testIntegerValueOne() {
    ASTNode integerNode = new ASTNode(1);
    assertTrue(integerNode.isOne());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isOne()}.
   */
  @Test
  public void testRealValueOne() {
    ASTNode realNode = new ASTNode(1.0);
    assertTrue(realNode.isOne());
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isNumber()}.
   */
  @Test
  public void testRealNumber() {
    ASTNode realNum = new ASTNode(1.0);
    assertTrue(realNum.isNumber());
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isNumber()}.
   */
  @Test
  public void testIntegerNumber() {
    ASTNode integerNum = new ASTNode(1);
    assertTrue(integerNum.isNumber());
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isName()}.
   */
  @Test
  public void testName() {
    ASTNode name = new ASTNode("Reaction");
    assertTrue(name.isName());
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isLambda()}.
   */
  @Test
  public void testLambda() {
    ASTNode lambda = new ASTNode();
    lambda.setType("lambda");
    assertTrue(lambda.isLambda());
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#setValue(double mantissa, int exponent)},
   * {@link org.sbml.jsbml.ASTNode#getMantissa()},
   * {@link org.sbml.jsbml.ASTNode#getExponent()} and
   * {@link org.sbml.jsbml.ASTNode#getReal()}.
   */
  @Test
  public void testRealE() {
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
   * Test method for {@link org.sbml.jsbml.ASTNode#getMantissa()},
   * {@link org.sbml.jsbml.ASTNode#getMantissa()},
   * {@link org.sbml.jsbml.ASTNode#isReal()} and
   * {@link org.sbml.jsbml.ASTNode#getReal()}.
   */
  @Test
  public void testGetMantissaForReal() {
    ASTNode realNode = new ASTNode(1.0001);
    assertTrue(realNode.isReal());
    assertTrue(realNode.getReal() == 1.0001);
    assertTrue(realNode.getExponent() == 0);
    assertTrue(realNode.getMantissa() == 1.0001);
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
    ASTNode one = new ASTNode(1), two = new ASTNode(2);
    ASTNode relationalEq = ASTNode.eq(one, two);
    assertTrue(relationalEq.isRelational());
    assertTrue(relationalEq.getLeftChild().equals(one));
    assertTrue(relationalEq.getRightChild().equals(two));
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
   * Test method for {@link org.sbml.jsbml.ASTNode#isBoolean()}.
   */
  @Test
  public void testRelationalBoolean() {
    ASTNode nine = new ASTNode(9), ten = new ASTNode(10);
    ASTNode relationalBoolean = ASTNode.neq(nine, ten);
    assertTrue(relationalBoolean.isBoolean());
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
    assertTrue(minus.isDifference());
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
    ASTNode five = new ASTNode(5), four = new ASTNode(4);
    ASTNode times = ASTNode.times(five, four);
    assertTrue(times.isOperator());
    assertTrue(times.getLeftChild().equals(five));
    assertTrue(times.getRightChild().equals(four));
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
   * Test method for {@link org.sbml.jsbml.ASTNode#log(ASTNode base, ASTNode value)}.
   */
  @Test
  public void testLogBase10() {
    ASTNode two = new ASTNode(2), ten = new ASTNode(10);
    ASTNode log = ASTNode.log(ten, two);
    assertTrue(log.isLog10());
    assertTrue(log.isFunction());
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#log(ASTNode base, ASTNode value)}.
   */
  @Test
  public void testLogBaseOther() {
    ASTNode two = new ASTNode(2), four = new ASTNode(4);
    ASTNode log = ASTNode.log(two, four);
    assertTrue(! log.isLog10());
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
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isPiecewise()}.
   */
  @Test
  public void testPiecewise() {
    ASTNode plus = new ASTNode('+'), minus = new ASTNode('-');
    ASTNode piecewise = ASTNode.piecewise(plus, minus);
    assertTrue(piecewise.isPiecewise());
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isInfinity()}.
   */
  @Test
  public void testPositiveInfinity() {
    ASTNode plusInfinity = new ASTNode(Double.POSITIVE_INFINITY);
    assertTrue(plusInfinity.isInfinity());
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isNegInfinity()}.
   */
  @Test
  public void testNegativeInfinity() {
    ASTNode minusInfinity = new ASTNode(Double.NEGATIVE_INFINITY);
    assertTrue(minusInfinity.isNegInfinity());
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isMinusOne()}.
   */
  @Test
  public void testIntegerValMinusOne() {
    ASTNode minusOne = new ASTNode(-1);
    assertTrue(minusOne.isMinusOne());
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isMinusOne()}.
   */
  @Test
  public void testRealValMinusOne() {
    ASTNode minusOne = new ASTNode(-1.0);
    assertTrue(minusOne.isMinusOne());
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isLogical()}.
   */
  @Test
  public void testLogicalAND() {
    ASTNode logicalAND = new ASTNode();
    logicalAND.setType("and");
    assertTrue(logicalAND.isLogical());
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isLogical()}.
   */
  @Test
  public void testLogicalOR() {
    ASTNode logicalOR = new ASTNode();
    logicalOR.setType("or");
    assertTrue(logicalOR.isLogical());
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isLogical()}.
   */
  @Test
  public void testLogicalXOR() {
    ASTNode logicalXOR = new ASTNode();
    logicalXOR.setType("xor");
    assertTrue(logicalXOR.isLogical());
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isLogical()}.
   */
  @Test
  public void testLogicalNOT() {
    ASTNode logicalNOT = new ASTNode();
    logicalNOT.setType("not");
    assertTrue(logicalNOT.isLogical());
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isBoolean()}.
   */
  @Test
  public void testLogicalBoolean() {
    ASTNode logicalBoolean = new ASTNode();
    logicalBoolean.setType("not");
    assertTrue(logicalBoolean.isBoolean());
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isNaN()}.
   */
  @Test
  public void testNaN() {
    ASTNode NaN = new ASTNode(Double.NaN);
    assertTrue(NaN.isNaN());
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#toFormula()}.
   */
  @Test
  public void testFormula() {
    ASTNode numerator = new ASTNode(22), denominator = new ASTNode(7);
    ASTNode frac = ASTNode.frac(numerator, denominator);
    assertTrue(frac.toFormula().equalsIgnoreCase("22/7"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#toLaTeX()}.
   */
  @Test
  public void testLaTeX() {
    ASTNode exponent = new ASTNode(2);
    ASTNode eNode = ASTNode.exp(exponent);
    assertTrue(eNode.toLaTeX().equalsIgnoreCase("\\mathrm{e}^{2}"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#toMathML()}.
   */
  @Test
  public void testMathML() {
    ASTNode numerator = new ASTNode(22), denominator = new ASTNode(7);
    ASTNode frac = ASTNode.frac(numerator, denominator);
    String mathml = "<?xml version='1.0' encoding='UTF-8'?>\n"
        + "<math xmlns=\"http://www.w3.org/1998/Math/MathML\">\n"
        + "  <apply>\n"
        + "    <divide/>\n"
        + "    <cn type=\"integer\"> 22 </cn>\n"
        + "    <cn type=\"integer\"> 7 </cn>\n"
        + "  </apply>\n"
        + "</math>";
    assertTrue(frac.toMathML().equalsIgnoreCase(mathml));
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isFunction()}.
   */
  @Test
  public void testCos() {
    ASTNode cos = new ASTNode();
    cos.setType("cos");
    assertTrue(cos.isFunction());
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isFunction()}.
   */
  @Test
  public void testSin() {
    ASTNode sin = new ASTNode();
    sin.setType("sin");
    assertTrue(sin.isFunction());
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isFunction()}.
   */
  @Test
  public void testTan() {
    ASTNode tan = new ASTNode();
    tan.setType("tan");
    assertTrue(tan.isFunction());
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isFunction()}.
   */
  @Test
  public void testSec() {
    ASTNode sec = new ASTNode();
    sec.setType("sec");
    assertTrue(sec.isFunction());
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isFunction()}.
   */
  @Test
  public void testCsc() {
    ASTNode csc = new ASTNode();
    csc.setType("csc");
    assertTrue(csc.isFunction());
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isFunction()}.
   */
  @Test
  public void testCot() {
    ASTNode cot = new ASTNode();
    cot.setType("cot");
    assertTrue(cot.isFunction());
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isFunction()}.
   */
  @Test
  public void testSinh() {
    ASTNode sinh = new ASTNode();
    sinh.setType("sinh");
    assertTrue(sinh.isFunction());
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isFunction()}.
   */
  @Test
  public void testCosh() {
    ASTNode cosh = new ASTNode();
    cosh.setType("cosh");
    assertTrue(cosh.isFunction());
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isFunction()}.
   */
  @Test
  public void testTanh() {
    ASTNode tanh = new ASTNode();
    tanh.setType("tanh");
    assertTrue(tanh.isFunction());
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isFunction()}.
   */
  @Test
  public void testSech() {
    ASTNode sech = new ASTNode();
    sech.setType("sech");
    assertTrue(sech.isFunction());
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isFunction()}.
   */
  @Test
  public void testCsch() {
    ASTNode csch = new ASTNode();
    csch.setType("csch");
    assertTrue(csch.isFunction());
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isFunction()}.
   */
  @Test
  public void testCoth() {
    ASTNode coth = new ASTNode();
    coth.setType("coth");
    assertTrue(coth.isFunction());
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isFunction()}.
   */
  @Test
  public void testArcSin() {
    ASTNode arcSin = new ASTNode();
    arcSin.setType("arcsin");
    assertTrue(arcSin.isFunction());
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isFunction()}.
   */
  @Test
  public void testArcCos() {
    ASTNode arcCos = new ASTNode();
    arcCos.setType("arccos");
    assertTrue(arcCos.isFunction());
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isFunction()}.
   */
  @Test
  public void testArcTan() {
    ASTNode arcTan = new ASTNode();
    arcTan.setType("arctan");
    assertTrue(arcTan.isFunction());
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isFunction()}.
   */
  @Test
  public void testArcSec() {
    ASTNode arcSec = new ASTNode();
    arcSec.setType("arcsec");
    assertTrue(arcSec.isFunction());
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isFunction()}.
   */
  @Test
  public void testArcCsc() {
    ASTNode arcCsc = new ASTNode();
    arcCsc.setType("arccsc");
    assertTrue(arcCsc.isFunction());
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isFunction()}.
   */
  @Test
  public void testArcCot() {
    ASTNode arcCot = new ASTNode();
    arcCot.setType("arccot");
    assertTrue(arcCot.isFunction());
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isFunction()}.
   */
  @Test
  public void testArcSinh() {
    ASTNode arcSinh = new ASTNode();
    arcSinh.setType("arcsinh");
    assertTrue(arcSinh.isFunction());
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isFunction()}.
   */
  @Test
  public void testArcCosh() {
    ASTNode arcCosh = new ASTNode();
    arcCosh.setType("arccosh");
    assertTrue(arcCosh.isFunction());
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isFunction()}.
   */
  @Test
  public void testArcTanh() {
    ASTNode arcTanh = new ASTNode();
    arcTanh.setType("arctanh");
    assertTrue(arcTanh.isFunction());
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isFunction()}.
   */
  @Test
  public void testArcSech() {
    ASTNode arcSech = new ASTNode();
    arcSech.setType("arcsech");
    assertTrue(arcSech.isFunction());
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isFunction()}.
   */
  @Test
  public void testArcCsch() {
    ASTNode arcCsch = new ASTNode();
    arcCsch.setType("arccsch");
    assertTrue(arcCsch.isFunction());
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isFunction()}.
   */
  @Test
  public void testArcCoth() {
    ASTNode arcCoth = new ASTNode();
    arcCoth.setType("arccoth");
    assertTrue(arcCoth.isFunction());
  }

}
