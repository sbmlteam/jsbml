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
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.math.ASTFactory;

/**
 * Test cases for {@link ASTNode}
 * 
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
    ASTNode loneNode = new ASTNode();
    assertTrue(loneNode.getChildren().size() == 0);
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#getChildCount()}.
   */
  @Test
  public void testGetChildCount() {
    ASTNode node = new ASTNode(Type.FUNCTION);
    assertTrue(node.getChildCount() == 0);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#getInteger()} and
   * {@link org.sbml.jsbml.ASTNode#setValue(int value)}.
   */
  @Test
  public void testInteger() {
    ASTNode integer = new ASTNode(21);
    assertTrue(integer.isInteger() && integer.getInteger() == 21);
  }


  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#getReal()} and
   * {@link org.sbml.jsbml.ASTNode#setValue(double value)}.
   */
  @Test
  public void testReal() {
    ASTNode real = new ASTNode(3.14);
    assertTrue(real.isReal() && real.getReal() == 3.14);
  }


  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isOne()}.
   */
  @Test
  public void testIntegerIsOne() {
    ASTNode one = new ASTNode(1);
    assertTrue(one.isOne());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isOne()}.
   */
  @Test
  public void testRealIsOne() {
    ASTNode one = new ASTNode(1d);
    assertTrue(one.isOne());
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isNumber()}.
   */
  @Test
  public void testRealIsNumber() {
    ASTNode real = new ASTNode(1d);
    assertTrue(real.isNumber());
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isNumber()}.
   */
  @Test
  public void testIntegerIsNumber() {
    ASTNode integer = new ASTNode(1);
    assertTrue(integer.isNumber());
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isLambda()}.
   */
  @Test
  public void testLambda() {
    ASTNode lambda = new ASTNode(Type.LAMBDA);
    assertTrue(lambda.isLambda());
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#setValue(double mantissa, int exponent)},
   * {@link org.sbml.jsbml.ASTNode#getMantissa()},
   * {@link org.sbml.jsbml.ASTNode#getExponent()} and
   * {@link org.sbml.jsbml.ASTNode#getReal()}.
   */
  @Test
  public final void testRealE() {
    ASTNode realE = new ASTNode(0.14, 4);
    assertTrue(realE.isReal()&& 
               realE.getType() == Type.REAL_E &&
               realE.getMantissa() == 0.14 &&
               realE.getExponent() == 4);
  }


  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#setValue(int numerator, int denominator)},
   * {@link org.sbml.jsbml.ASTNode#getNumerator()} and
   * {@link org.sbml.jsbml.ASTNode#getDenominator()}.
   */
  @Test
  public void testRational() {
    ASTNode rationalNode = new ASTNode(Type.RATIONAL);
    rationalNode.setValue(22, 7);
    assertTrue(rationalNode.isRational() &&
               rationalNode.getNumerator() == 22 &&
               rationalNode.getDenominator() == 7);
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#eq(ASTNode left, ASTNode right)}.
   */
  @Test
  public void testRelationalEq() {
    ASTNode one = new ASTNode(1), two = new ASTNode(2);
    ASTNode relationalEq = ASTNode.eq(one, two);
    assertTrue(relationalEq.isRelational() &&
               relationalEq.getType() == Type.RELATIONAL_EQ &&
               relationalEq.getLeftChild().equals(one) &&
               relationalEq.getRightChild().equals(two));
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#geq(ASTNode left, ASTNode right)}.
   */
  @Test
  public void testRelationalGeq() {
    ASTNode five = new ASTNode(5), three = new ASTNode(3);
    ASTNode geq = ASTNode.geq(five, three);
    assertTrue(geq.isRelational() &&
               geq.getLeftChild().equals(five) &&
               geq.getRightChild().equals(three));
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#gt(ASTNode left, ASTNode right)}.
   */
  @Test
  public void testRelationalGt() {
    ASTNode six = new ASTNode(6), two = new ASTNode(2);
    ASTNode gt = ASTNode.gt(six, two);
    assertTrue(gt.isRelational() &&
               gt.getLeftChild().equals(six) &&
               gt.getRightChild().equals(two));
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#leq(ASTNode left, ASTNode right)}.
   */
  @Test
  public void testRelationalLeq() {
    ASTNode three = new ASTNode(3), one = new ASTNode(1);
    ASTNode leq = ASTNode.leq(one, three);
    assertTrue(leq.isRelational() && 
               leq.getLeftChild().equals(one) &&
               leq.getRightChild().equals(three));
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#lt(ASTNode left, ASTNode right)}.
   */
  @Test
  public void testRelationalLt() {
    ASTNode seven = new ASTNode(7), five = new ASTNode(5);
    ASTNode lt = ASTNode.lt(five, seven);
    assertTrue(lt.isRelational() &&
               lt.getLeftChild().equals(five) &&
               lt.getRightChild().equals(seven));
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#neq(ASTNode left, ASTNode right)}.
   */
  @Test
  public void testRelationalNeq() {
    ASTNode nine = new ASTNode(9), ten = new ASTNode(10);
    ASTNode neq = ASTNode.neq(nine, ten);
    assertTrue(neq.isRelational() &&
               neq.getLeftChild().equals(nine) &&
               neq.getRightChild().equals(ten));
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#diff(ASTNode... ast)}.
   */
  @Test
  public void testMinus() {
    ASTNode one = new ASTNode(1), two = new ASTNode(2);
    ASTNode minus = ASTNode.diff(one, two);
    assertTrue(minus.isOperator() &&
               minus.getType() == Type.MINUS &&
               minus.getLeftChild().equals(one) &&
               minus.getRightChild().equals(two));
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#sum(ASTNode... ast)}.
   */
  @Test
  public void testSum() {
    ASTNode four = new ASTNode(4), eight = new ASTNode(8);
    ASTNode sum = ASTNode.sum(four, eight);
    assertTrue(sum.isOperator() &&
               sum.getType() == Type.SUM && 
               sum.getLeftChild().equals(four) &&
               sum.getRightChild().equals(eight));
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#times(ASTNode... ast)}.
   */
  @Test
  public void testTimes() {
    ASTNode five = new ASTNode(5), four = new ASTNode(4);
    ASTNode times = ASTNode.times(five, four);
    assertTrue(times.isOperator() &&
               times.getType() == Type.PRODUCT &&
               times.getLeftChild().equals(five) &&
               times.getRightChild().equals(four));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#multiplyWith(ASTNode ast)}.
   */
  @Test
  public void testMultiplyWith() {
    ASTNode five = new ASTNode(5);
    ASTNode four = new ASTNode(4);
    ASTNode times = five.multiplyWith(four);
    assertTrue(times.isOperator() &&
               times.getType() == ASTNode.Type.TIMES &&
               times.getLeftChild().equals(five) &&
               times.getRightChild().equals(four));
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#exp(ASTNode exponent)}.
   */
  @Test
  public void testPower() {
    ASTNode exponent = new ASTNode(2);
    ASTNode e = ASTNode.exp(exponent);
    assertTrue(e.isOperator() && e.getType() == Type.POWER);
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#frac(ASTNode numerator, ASTNode denominator)}.
   */
  @Test
  public void testFrac() {
    ASTNode numerator = new ASTNode(22), denominator = new ASTNode(7);
    ASTNode frac = ASTNode.frac(numerator, denominator);
    assertTrue(frac.isOperator() && frac.getType() == Type.DIVIDE);
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#log(ASTNode base, ASTNode value)}.
   */
  @Test
  public void testLogBase10() {
    ASTNode two = new ASTNode(2);
    ASTNode log = ASTNode.log(two);
    assertTrue(log.isFunction() && log.isLog10());
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#log(ASTNode base, ASTNode value)}.
   */
  @Test
  public void testNaturalLog() {
    ASTNode e = new ASTNode(Math.E), four = new ASTNode(4);
    ASTNode ln = ASTNode.log(e, four);
    assertTrue(! ln.isLog10() && ln.isFunction() && ln.getType() == Type.FUNCTION_LN);
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#pow(ASTNode base, ASTNode value)}.
   */
  @Test
  public void testPow() {
    ASTNode two = new ASTNode(2), three = new ASTNode(3);
    ASTNode pow = ASTNode.pow(two, three);
    assertTrue(pow.isOperator() && pow.getType() == Type.POWER);
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
    ASTNode minusOne = new ASTNode(-1d);
    assertTrue(minusOne.isMinusOne());
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isLogical()}.
   */
  @Test
  public void testLogicalAND() {
    ASTNode logicalAND = new ASTNode(Type.LOGICAL_AND);
    assertTrue(logicalAND.isLogical());
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isLogical()}.
   */
  @Test
  public void testLogicalOR() {
    ASTNode logicalOR = new ASTNode(Type.LOGICAL_OR);
    assertTrue(logicalOR.isLogical());
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isLogical()}.
   */
  @Test
  public void testLogicalXOR() {
    ASTNode logicalXOR = new ASTNode(Type.LOGICAL_XOR);
    assertTrue(logicalXOR.isLogical());
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isLogical()}.
   */
  @Test
  public void testLogicalNOT() {
    ASTNode logicalNOT = new ASTNode(Type.LOGICAL_NOT);
    assertTrue(logicalNOT.isLogical());
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
    ASTNode ten = new ASTNode(10d);
    assertTrue(ten.toFormula().equalsIgnoreCase("10"));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#toLaTeX()}.
   */
  @Test
  public void testLaTeX() {
    ASTNode ten = new ASTNode(10d);
    assertTrue(ten.toLaTeX().equalsIgnoreCase("10"));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#toMathML()}.
   */
  @Test
  public void testMathML() {
    ASTNode ten = new ASTNode(10d);
    String mathml = ASTFactory.parseMathML("real.xml");
    assertTrue(ten.toMathML().equalsIgnoreCase(mathml));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isFunction()}.
   */
  @Test
  public void testCos() {
    ASTNode cos = new ASTNode(Type.FUNCTION_COS);
    assertTrue(cos.isFunction() && cos.getType() == Type.FUNCTION_COS);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isFunction()}.
   */
  @Test
  public void testSin() {
    ASTNode sin = new ASTNode(Type.FUNCTION_SIN);
    assertTrue(sin.isFunction() && sin.getType() == Type.FUNCTION_SIN);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isFunction()}.
   */
  @Test
  public void testTan() {
    ASTNode tan = new ASTNode(Type.FUNCTION_TAN);
    assertTrue(tan.isFunction() && tan.getType() == Type.FUNCTION_TAN);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isFunction()}.
   */
  @Test
  public void testSec() {
    ASTNode sec = new ASTNode(Type.FUNCTION_SEC);
    assertTrue(sec.isFunction() && sec.getType() == Type.FUNCTION_SEC);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isFunction()}.
   */
  @Test
  public void testCsc() {
    ASTNode csc = new ASTNode(Type.FUNCTION_CSC);
    assertTrue(csc.isFunction() && csc.getType() == Type.FUNCTION_CSC);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isFunction()}.
   */
  @Test
  public void testCot() {
    ASTNode cot = new ASTNode(Type.FUNCTION_COT);
    assertTrue(cot.isFunction() && cot.getType() == Type.FUNCTION_COT);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isFunction()}.
   */
  @Test
  public void testSinh() {
    ASTNode sinh = new ASTNode(Type.FUNCTION_SINH);
    assertTrue(sinh.isFunction() && sinh.getType() == Type.FUNCTION_SINH);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isFunction()}.
   */
  @Test
  public void testCosh() {
    ASTNode cosh = new ASTNode(Type.FUNCTION_COSH);
    assertTrue(cosh.isFunction() && cosh.getType() == Type.FUNCTION_COSH);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isFunction()}.
   */
  @Test
  public void testTanh() {
    ASTNode tanh = new ASTNode(Type.FUNCTION_TANH);
    assertTrue(tanh.isFunction() && tanh.getType() == Type.FUNCTION_TANH);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isFunction()}.
   */
  @Test
  public void testSech() {
    ASTNode sech = new ASTNode(Type.FUNCTION_SECH);
    assertTrue(sech.isFunction() && sech.getType() == Type.FUNCTION_SECH);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isFunction()}.
   */
  @Test
  public void testCsch() {
    ASTNode csch = new ASTNode(Type.FUNCTION_CSCH);
    assertTrue(csch.isFunction() && csch.getType() == Type.FUNCTION_CSCH);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isFunction()}.
   */
  @Test
  public void testCoth() {
    ASTNode coth = new ASTNode(Type.FUNCTION_COTH);
    assertTrue(coth.isFunction() && coth.getType() == Type.FUNCTION_COTH);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isFunction()}.
   */
  @Test
  public void testArcSin() {
    ASTNode arcSin = new ASTNode(Type.FUNCTION_ARCSIN);
    assertTrue(arcSin.isFunction() && arcSin.getType() == Type.FUNCTION_ARCSIN);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isFunction()}.
   */
  @Test
  public void testArcCos() {
    ASTNode arcCos = new ASTNode(Type.FUNCTION_ARCCOS);
    assertTrue(arcCos.isFunction() && arcCos.getType() == Type.FUNCTION_ARCCOS);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isFunction()}.
   */
  @Test
  public void testArcTan() {
    ASTNode arcTan = new ASTNode(Type.FUNCTION_ARCTAN);
    assertTrue(arcTan.isFunction() && arcTan.getType() == Type.FUNCTION_ARCTAN);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isFunction()}.
   */
  @Test
  public void testArcSec() {
    ASTNode arcSec = new ASTNode(Type.FUNCTION_ARCSEC);
    assertTrue(arcSec.isFunction() && arcSec.getType() == Type.FUNCTION_ARCSEC);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isFunction()}.
   */
  @Test
  public void testArcCsc() {
    ASTNode arcCsc = new ASTNode(Type.FUNCTION_ARCCSC);
    assertTrue(arcCsc.isFunction() && arcCsc.getType() == Type.FUNCTION_ARCCSC);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isFunction()}.
   */
  @Test
  public void testArcCot() {
    ASTNode arcCot = new ASTNode(Type.FUNCTION_ARCCOT);
    assertTrue(arcCot.isFunction() && arcCot.getType() == Type.FUNCTION_ARCCOT);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isFunction()}.
   */
  @Test
  public void testArcSinh() {
    ASTNode arcSinh = new ASTNode(Type.FUNCTION_ARCSINH);
    assertTrue(arcSinh.isFunction() && arcSinh.getType() == Type.FUNCTION_ARCSINH);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isFunction()}.
   */
  @Test
  public void testArcCosh() {
    ASTNode arcCosh = new ASTNode(Type.FUNCTION_ARCCOSH);
    assertTrue(arcCosh.isFunction() && arcCosh.getType() == Type.FUNCTION_ARCCOSH);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isFunction()}.
   */
  @Test
  public void testArcTanh() {
    ASTNode arcTanh = new ASTNode(Type.FUNCTION_ARCTANH);
    assertTrue(arcTanh.isFunction() && arcTanh.getType() == Type.FUNCTION_ARCTANH);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isFunction()}.
   */
  @Test
  public void testArcSech() {
    ASTNode arcSech = new ASTNode(Type.FUNCTION_ARCSECH);
    assertTrue(arcSech.isFunction() && arcSech.getType() == Type.FUNCTION_ARCSECH);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isFunction()}.
   */
  @Test
  public void testArcCsch() {
    ASTNode arcCsch = new ASTNode(Type.FUNCTION_ARCCSCH);
    assertTrue(arcCsch.isFunction() && arcCsch.getType() == Type.FUNCTION_ARCCSCH);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#isFunction()}.
   */
  @Test
  public void testArcCoth() {
    ASTNode arcCoth = new ASTNode(Type.FUNCTION_ARCCOTH);
    assertTrue(arcCoth.isFunction() && arcCoth.getType() == Type.FUNCTION_ARCCOTH);
  }
  
}
