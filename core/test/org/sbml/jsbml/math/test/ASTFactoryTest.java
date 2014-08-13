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

import javax.swing.tree.TreeNode;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.AssignmentRule;
import org.sbml.jsbml.math.ASTArithmeticOperatorNode;
import org.sbml.jsbml.math.ASTBoolean;
import org.sbml.jsbml.math.ASTCiNumberNode;
import org.sbml.jsbml.math.ASTCnIntegerNode;
import org.sbml.jsbml.math.ASTCnRealNode;
import org.sbml.jsbml.math.ASTConstantNumber;
import org.sbml.jsbml.math.ASTDivideNode;
import org.sbml.jsbml.math.ASTFactory;
import org.sbml.jsbml.math.ASTLogarithmNode;
import org.sbml.jsbml.math.ASTLogicalOperatorNode;
import org.sbml.jsbml.math.ASTMinusNode;
import org.sbml.jsbml.math.ASTNode2;
import org.sbml.jsbml.math.ASTPiecewiseFunctionNode;
import org.sbml.jsbml.math.ASTPlusNode;
import org.sbml.jsbml.math.ASTPowerNode;
import org.sbml.jsbml.math.ASTQualifierNode;
import org.sbml.jsbml.math.ASTRelationalOperatorNode;
import org.sbml.jsbml.math.ASTRootNode;
import org.sbml.jsbml.math.ASTTimesNode;
import org.sbml.jsbml.math.ASTUnaryFunctionNode;
import org.sbml.jsbml.text.parser.ParseException;


/**
 * Test cases for {@link ASTFactory}
 * 
 * @author Victor Kofia
 * @version $Rev$
 * @since 1.0
 * @date Jul 25, 2014
 */
public class ASTFactoryTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  /**
   * Counts the number of nodes that have {@link Type} type
   * in the tree rooted at node.
   * 
   * @param node {@link ASTNode2}
   * @param type {@link Type}
   * @return int
   */
  public int countType(ASTNode2 node, Type type) {
    int count = node.getType() == type ? 1 : 0;
    for (int i  = 0; i < node.getChildCount(); i++) {
      TreeNode child = node.getChildAt(i);
      if (child instanceof ASTNode2) {
        count += countType((ASTNode2) child, type);        
      }
    }
    return count;
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#diff(org.sbml.jsbml.math.ASTNode2)}.
   */
  @Test
  public final void testDiff() {
    ASTCnIntegerNode one = new ASTCnIntegerNode(1);
    ASTCnIntegerNode two = new ASTCnIntegerNode(2);
    ASTArithmeticOperatorNode diff = ASTFactory.diff(one, two);
    assertTrue(diff.getType() == Type.MINUS && diff.getChildCount() == 2);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#diff(org.sbml.jsbml.math.ASTNode2)}.
   */
  @Test
  public final void testDiffNoArgument() {
    exception.expect(NullPointerException.class);
    ASTNode2[] list = null;
    ASTFactory.diff(list);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#divideBy(org.sbml.jsbml.math.ASTNode2, org.sbml.jsbml.math.ASTNode2)}.
   */
  @Test
  public final void testDivideBy() {
    ASTCnIntegerNode four = new ASTCnIntegerNode(4);
    ASTCnIntegerNode eight = new ASTCnIntegerNode(8);
    ASTDivideNode divideBy = ASTFactory.divideBy(four, eight);
    assertTrue(divideBy.getType() == Type.DIVIDE
        && divideBy.getChildCount() == 2 
        && divideBy.getLeftChild().equals(four)
        && divideBy.getRightChild().equals(eight));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#eq(org.sbml.jsbml.math.ASTNode2, org.sbml.jsbml.math.ASTNode2)}.
   */
  @Test
  public final void testEq() {
    ASTBoolean bTrue = new ASTBoolean(Type.CONSTANT_TRUE);
    ASTBoolean bFalse = new ASTBoolean(Type.CONSTANT_FALSE);
    ASTRelationalOperatorNode eq = ASTFactory.eq(bTrue, bFalse);
    assertTrue(eq.getType() == Type.RELATIONAL_EQ
        && eq.getChildCount() == 2 
        && eq.getChildAt(0).equals(bTrue)
        && eq.getChildAt(1).equals(bFalse));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#exp(org.sbml.jsbml.math.ASTNode2)}.
   */
  @Test
  public final void testExp() {
    ASTCnIntegerNode eight = new ASTCnIntegerNode(8);
    ASTPowerNode pow = ASTFactory.exp(eight);
    ASTNode2 unknown = pow.getLeftChild();
    assertTrue(pow.getType() == Type.POWER
        && pow.getChildCount() == 2 
        && unknown.getType() == Type.CONSTANT_E
        && ((ASTConstantNumber)unknown).getValue() == Math.E
        && pow.getRightChild().equals(eight));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#frac(org.sbml.jsbml.math.ASTNode2, org.sbml.jsbml.math.ASTNode2)}.
   */
  @Test
  public final void testFrac() {
    ASTCnIntegerNode four = new ASTCnIntegerNode(4);
    ASTCnIntegerNode eight = new ASTCnIntegerNode(8);
    ASTDivideNode frac = ASTFactory.frac(four, eight);
    assertTrue(frac.getType() == Type.DIVIDE
        && frac.getChildCount() == 2 
        && frac.getLeftChild().equals(four)
        && frac.getRightChild().equals(eight));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#frac(int, org.sbml.jsbml.math.ASTNode2)}.
   */
  @Test
  public final void testFracWithInteger() {
    ASTCnIntegerNode eight = new ASTCnIntegerNode(8);
    ASTDivideNode frac = ASTFactory.frac(4, eight);
    ASTNode2 unknown = frac.getLeftChild();
    assertTrue(frac.getType() == Type.DIVIDE
        && frac.getChildCount() == 2 
        && unknown.getType() == Type.INTEGER
        && ((ASTCnIntegerNode)unknown).getInteger() == 4
        && frac.getRightChild().equals(eight));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#geq(org.sbml.jsbml.math.ASTNode2, org.sbml.jsbml.math.ASTNode2)}.
   */
  @Test
  public final void testGeq() {
    ASTBoolean bTrue = new ASTBoolean(Type.CONSTANT_TRUE);
    ASTBoolean bFalse = new ASTBoolean(Type.CONSTANT_FALSE);
    ASTRelationalOperatorNode geq = ASTFactory.geq(bTrue, bFalse);
    assertTrue(geq.getType() == Type.RELATIONAL_GEQ
        && geq.getChildCount() == 2 
        && geq.getChildAt(0).equals(bTrue)
        && geq.getChildAt(1).equals(bFalse));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#gt(org.sbml.jsbml.math.ASTNode2, org.sbml.jsbml.math.ASTNode2)}.
   */
  @Test
  public final void testGt() {
    ASTBoolean bTrue = new ASTBoolean(Type.CONSTANT_TRUE);
    ASTBoolean bFalse = new ASTBoolean(Type.CONSTANT_FALSE);
    ASTRelationalOperatorNode gt = ASTFactory.gt(bTrue, bFalse);
    assertTrue(gt.getType() == Type.RELATIONAL_GT
        && gt.getChildCount() == 2 
        && gt.getChildAt(0).equals(bTrue)
        && gt.getChildAt(1).equals(bFalse));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#log(org.sbml.jsbml.math.ASTNode2, org.sbml.jsbml.math.ASTNode2)}.
   */
  @Test
  public final void testLog() {
    ASTCnRealNode one = new ASTCnRealNode(Math.E);
    ASTCnIntegerNode two = new ASTCnIntegerNode(2);
    ASTLogarithmNode log = ASTFactory.log(one, two);
    assertTrue(log.getType() == Type.FUNCTION_LOG
        && log.getChildCount() == 2 
        && log.getLeftChild().equals(one)
        && log.getRightChild().equals(two));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#log(org.sbml.jsbml.math.ASTNode2)}.
   */
  @Test
  public final void testLogBase10() {
    ASTCnIntegerNode ten = new ASTCnIntegerNode(10);
    ASTLogarithmNode log = ASTFactory.log(ten);
    ASTNode2 unknown = log.getBase();
    assertTrue(log.getType() == Type.FUNCTION_LOG
        && log.getChildCount() == 1 
        && unknown.getType() == Type.INTEGER
        && ((ASTCnIntegerNode)unknown).getInteger() == 10
        && log.getValue().equals(ten));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#lt(org.sbml.jsbml.math.ASTNode2, org.sbml.jsbml.math.ASTNode2)}.
   */
  @Test
  public final void testLt() {
    ASTCnIntegerNode one = new ASTCnIntegerNode(1);
    ASTCnIntegerNode two = new ASTCnIntegerNode(2);
    ASTRelationalOperatorNode lt = ASTFactory.lt(one, two);
    assertTrue(lt.getType() == Type.RELATIONAL_LT
        && lt.getChildCount() == 2 
        && lt.getChildAt(0).equals(one)
        && lt.getChildAt(1).equals(two));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#lt(String, org.sbml.jsbml.math.ASTNode2)}.
   */
  @Test
  public final void testLtWithVariable() {
    ASTCnIntegerNode one = new ASTCnIntegerNode(1);
    String variable = "x";
    ASTRelationalOperatorNode lt = ASTFactory.lt(variable, one);
    ASTNode2 unknown = lt.getChildAt(0);
    assertTrue(lt.getType() == Type.RELATIONAL_LT
        && lt.getChildCount() == 2 
        && unknown.getType() == Type.NAME
        && ((ASTCiNumberNode)unknown).getRefId().equals(variable)
        && lt.getChildAt(1).equals(one));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#minus(org.sbml.jsbml.math.ASTNode2, org.sbml.jsbml.math.ASTNode2)}.
   */
  @Test
  public final void testMinus() {
    ASTCnIntegerNode one = new ASTCnIntegerNode(1);
    ASTCnIntegerNode five = new ASTCnIntegerNode(5);
    ASTMinusNode minus = ASTFactory.minus(one, five);
    assertTrue(minus.getChildCount() == 2 
        && minus.getLeftChild().equals(one)
        && minus.getRightChild().equals(five));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#minus(org.sbml.jsbml.math.ASTNode2, int)}.
   */
  @Test
  public final void testMinusWithInteger() {
    ASTCnIntegerNode one = new ASTCnIntegerNode(1);
    ASTMinusNode minus = ASTFactory.minus(one, 5);
    ASTNode2 unknown = minus.getRightChild();
    assertTrue(minus.getChildCount() == 2 
        && minus.getLeftChild().equals(one)
        && unknown.getType() == Type.INTEGER
        && ((ASTCnIntegerNode)unknown).getInteger() == 5);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#minus(org.sbml.jsbml.math.ASTNode2, int, String)}.
   */
  @Test
  public final void testMinusWithIntegerAndUnits() {
    ASTCnIntegerNode one = new ASTCnIntegerNode(1);
    String unitsId = "mg";
    ASTMinusNode minus = ASTFactory.minus(one, 5, unitsId);
    ASTNode2 unknown = minus.getRightChild();
    assertTrue(minus.getChildCount() == 2 
        && minus.getLeftChild().equals(one)
        && unknown.getType() == Type.INTEGER
        && ((ASTCnIntegerNode)unknown).getInteger() == 5
        && ((ASTCnIntegerNode)unknown).getUnits().equals(unitsId));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#multiplyWith(org.sbml.jsbml.math.ASTQualifierNode, org.sbml.jsbml.math.ASTQualifierNode)}.
   */
  @Test
  public final void testMultiplyWith() {
    ASTCnIntegerNode five = new ASTCnIntegerNode(5);
    ASTCnIntegerNode ten = new ASTCnIntegerNode(10);
    ASTTimesNode times = ASTFactory.multiplyWith(five, ten);
    assertTrue(times.getChildCount() == 2
            && times.getLeftChild().equals(five)
            && times.getRightChild().equals(ten));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#neq(org.sbml.jsbml.math.ASTQualifierNode, org.sbml.jsbml.math.ASTQualifierNode)}.
   */
  @Test
  public final void testNeq() {
    ASTBoolean bTrue = new ASTBoolean(Type.CONSTANT_TRUE);
    ASTBoolean bFalse = new ASTBoolean(Type.CONSTANT_FALSE);
    ASTRelationalOperatorNode neq = ASTFactory.neq(bTrue, bFalse);
    assertTrue(neq.getType() == Type.RELATIONAL_NEQ
            && neq.getChildCount() == 2
            && neq.getChildAt(0).equals(bTrue)
            && neq.getChildAt(1).equals(bFalse));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#parseFormula(java.lang.String)}.
   */
  @Test
  public final void testParseFormulaBooleanLogicAnd() {
    boolean status = false;
    try {
      ASTLogicalOperatorNode and = (ASTLogicalOperatorNode) ASTFactory.parseFormula("false && true");
      status = and.getType() == Type.LOGICAL_AND
            && ((ASTBoolean)and.getChildAt(0)).getValue() == false
            && ((ASTBoolean)and.getChildAt(1)).getValue() == true;
    } catch (ParseException e) {
      e.printStackTrace();
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#parseFormula(java.lang.String)}.
   */
  @Test
  public final void testParseFormulaBooleanLogicNot() {
    boolean status = false;
    try {
      ASTNode2 not = ASTFactory.parseFormula("!false");
      status = not.getType() == Type.LOGICAL_NOT
            && ((ASTBoolean)not.getChildAt(0)).getValue() == false;
    } catch (ParseException e) {
      e.printStackTrace();
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#parseFormula(java.lang.String)}.
   */
  @Test
  public final void testParseFormulaBooleanLogicOr() {
    boolean status = false;
    try {
      ASTLogicalOperatorNode or = (ASTLogicalOperatorNode) ASTFactory.parseFormula("false || true");
      status = or.getType() == Type.LOGICAL_OR
            && ((ASTBoolean)or.getChildAt(0)).getValue() == false
            && ((ASTBoolean)or.getChildAt(1)).getValue() == true;
    } catch (ParseException e) {
      e.printStackTrace();
    }
    assertTrue(status);
  }  
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#parseFormula(java.lang.String)}.
   */
  @Test
  public final void testParseFormulaBooleanLogicXor() {
    boolean status = false;
    try {
      ASTLogicalOperatorNode xor = (ASTLogicalOperatorNode) ASTFactory.parseFormula("false xor true");
      status = xor.getType() == Type.LOGICAL_XOR
            && ((ASTBoolean)xor.getChildAt(0)).getValue() == false
            && ((ASTBoolean)xor.getChildAt(1)).getValue() == true;
    } catch (ParseException e) {
      e.printStackTrace();
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#parseFormula(java.lang.String)}.
   */
  @Test
  public final void testParseFormulaDivide() {
    boolean status = false;
    try {
      ASTDivideNode divide = (ASTDivideNode) ASTFactory.parseFormula("1000/25");
      status = divide.getType() == Type.DIVIDE
            && ((ASTCnIntegerNode)divide.getLeftChild()).getInteger() == 1000
            && ((ASTCnIntegerNode)divide.getRightChild()).getInteger() == 25;
    } catch (ParseException e) {
      e.printStackTrace();
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#parseFormula(java.lang.String)}.
   */
  @Test
  public final void testParseFormulaFactorialStringToken() {
    boolean status = false;
    try {
      ASTUnaryFunctionNode factorial = (ASTUnaryFunctionNode) ASTFactory.parseFormula("factorial(10)");
      status = factorial.getType() == Type.FUNCTION_FACTORIAL
            && ((ASTCnIntegerNode)factorial.getChild()).getInteger() == 10;
    } catch (ParseException e) {
      e.printStackTrace();
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#parseFormula(java.lang.String)}.
   */
  @Test
  public final void testParseFormulaLn() {
    boolean status = false;
    try {
      ASTLogarithmNode ln = (ASTLogarithmNode) ASTFactory.parseFormula("ln(8)");
      status = ln.getType() == Type.FUNCTION_LN
            && ((ASTConstantNumber)ln.getLeftChild()).getValue() == Math.E
            && ((ASTCnIntegerNode)ln.getRightChild()).getInteger() == 8;
    } catch (ParseException e) {
      e.printStackTrace();
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#parseFormula(java.lang.String)}.
   */
  @Test
  public final void testParseFormulaLogDefaultBase10() {
    boolean status = false;
    try {
      ASTLogarithmNode log = (ASTLogarithmNode) ASTFactory.parseFormula("log(100)");
      status = log.getType() == Type.FUNCTION_LOG
            && ((ASTCnIntegerNode)log.getLeftChild()).getInteger() == 10
            && ((ASTCnIntegerNode)log.getRightChild()).getInteger() == 100;
    } catch (ParseException e) {
      e.printStackTrace();
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#parseFormula(java.lang.String)}.
   */
  @Test
  public final void testParseFormulaLogDefaultBase10Alternate() {
    boolean status = false;
    try {
      ASTLogarithmNode log = (ASTLogarithmNode) ASTFactory.parseFormula("log10(100)");
      status = log.getType() == Type.FUNCTION_LOG
            && ((ASTCnIntegerNode)log.getLeftChild()).getInteger() == 10
            && ((ASTCnIntegerNode)log.getRightChild()).getInteger() == 100;
    } catch (ParseException e) {
      e.printStackTrace();
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#parseFormula(java.lang.String)}.
   */
  @Test
  public final void testParseFormulaMinus() {
    boolean status = false;
    try {
      ASTMinusNode minus = (ASTMinusNode) ASTFactory.parseFormula("50-10");
      status = minus.getType() == Type.MINUS 
            && ((ASTCnIntegerNode)minus.getLeftChild()).getInteger() == 50
            && ((ASTCnIntegerNode)minus.getRightChild()).getInteger() == 10;
    } catch (ParseException e) {
      e.printStackTrace();
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#parseFormula(java.lang.String)}.
   */
  @Test
  public final void testParseFormulaPlus() {
    boolean status = false;
    try {
      ASTPlusNode plus = (ASTPlusNode) ASTFactory.parseFormula("5+10");
      status = plus.getType() == Type.PLUS 
            && ((ASTCnIntegerNode)plus.getLeftChild()).getInteger() == 5
            && ((ASTCnIntegerNode)plus.getRightChild()).getInteger() == 10;
    } catch (ParseException e) {
      e.printStackTrace();
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#parseFormula(java.lang.String)}.
   */
  @Test
  public final void testParseFormulaPower() {
    boolean status = false;
    try {
      ASTPowerNode power = (ASTPowerNode) ASTFactory.parseFormula("e^5");
      status = power.getType() == Type.POWER
            && ((ASTConstantNumber)power.getLeftChild()).getValue() == Math.E
            && ((ASTCnIntegerNode)power.getRightChild()).getInteger() == 5;
    } catch (ParseException e) {
      e.printStackTrace();
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#parseFormula(java.lang.String)}.
   */
  @Test
  public final void testParseFormulaRelationalOperator() {
    boolean status = false;
    try {
      ASTRelationalOperatorNode geq = (ASTRelationalOperatorNode) ASTFactory.parseFormula("5 >= 10");
      status = geq.getType() == Type.RELATIONAL_GEQ
          && ((ASTCnIntegerNode)geq.getChildAt(0)).getInteger() == 5
          && ((ASTCnIntegerNode)geq.getChildAt(1)).getInteger() == 10;
    } catch (ParseException e) {
      e.printStackTrace();
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#parseFormula(java.lang.String)}.
   */
  @Test
  public final void testParseFormulaTimes() {
    boolean status = false;
    try {
      ASTTimesNode times = (ASTTimesNode) ASTFactory.parseFormula("7*9");
      status = times.getType() == Type.TIMES
            && ((ASTCnIntegerNode)times.getLeftChild()).getInteger() == 7
            && ((ASTCnIntegerNode)times.getRightChild()).getInteger() == 9;
    } catch (ParseException e) {
      e.printStackTrace();
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#piecewise(org.sbml.jsbml.math.ASTQualifierNode, org.sbml.jsbml.math.ASTQualifierNode)}.
   */
  @Test
  public final void testPiecewise() {
    ASTQualifierNode piece = new ASTQualifierNode(Type.CONSTRUCTOR_PIECE);
    ASTQualifierNode otherwise = new ASTQualifierNode(Type.CONSTRUCTOR_OTHERWISE);
    ASTPiecewiseFunctionNode piecewise = ASTFactory.piecewise(piece, otherwise);
    assertTrue(piecewise.getType() == Type.FUNCTION_PIECEWISE
            && piecewise.getChildCount() == 2
            && piecewise.getChildAt(0).equals(piece)
            && piecewise.getChildAt(1).equals(otherwise));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#plus(org.sbml.jsbml.math.ASTNode2, org.sbml.jsbml.math.ASTNode2)}.
   */
  @Test
  public final void testPlus() {
    ASTCnIntegerNode four = new ASTCnIntegerNode(4);
    ASTCnIntegerNode eight = new ASTCnIntegerNode(8);
    ASTPlusNode plus = ASTFactory.plus(four, eight);
    assertTrue(plus.getType() == Type.PLUS
            && plus.getChildCount() == 2
            && plus.getLeftChild().equals(four)
            && plus.getRightChild().equals(eight));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#plus(org.sbml.jsbml.math.ASTNode2, int)}.
   */
  @Test
  public final void testPlusWithInteger() {
    ASTCnIntegerNode four = new ASTCnIntegerNode(4);
    ASTPlusNode plus = ASTFactory.plus(four, 8);
    ASTNode2 unknown = plus.getRightChild();
    assertTrue(plus.getType() == Type.PLUS
            && plus.getChildCount() == 2
            && plus.getLeftChild().equals(four)
            && unknown.getType() == Type.INTEGER
            && ((ASTCnIntegerNode)unknown).getInteger() == 8);
  }  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#plus(org.sbml.jsbml.math.ASTNode2, double)}.
   */
  @Test
  public final void testPlusWithReal() {
    ASTCnIntegerNode four = new ASTCnIntegerNode(4);
    ASTPlusNode plus = ASTFactory.plus(four, 8.0);
    ASTNode2 unknown = plus.getRightChild();
    assertTrue(plus.getType() == Type.PLUS
            && plus.getChildCount() == 2
            && plus.getLeftChild().equals(four)
            && unknown.getType() == Type.REAL
            && ((ASTCnRealNode)unknown).getReal() == 8.0);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#pow(org.sbml.jsbml.math.ASTNode2, org.sbml.jsbml.math.ASTNode2)}.
   */
  @Test
  public final void testPower() {
    ASTCnIntegerNode four = new ASTCnIntegerNode(4);
    ASTCnIntegerNode eight = new ASTCnIntegerNode(8);
    ASTPowerNode pow = ASTFactory.pow(four, eight);
    assertTrue(pow.getType() == Type.POWER
            && pow.getChildCount() == 2
            && pow.getLeftChild().equals(four)
            && pow.getRightChild().equals(eight));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#pow(org.sbml.jsbml.math.ASTNode2, int)}.
   */
  @Test
  public final void testPowerWithInteger() {
    ASTCnIntegerNode four = new ASTCnIntegerNode(4);
    ASTPowerNode pow = ASTFactory.pow(four, 8);
    ASTNode2 unknown = pow.getRightChild();
    assertTrue(pow.getType() == Type.POWER
            && pow.getChildCount() == 2
            && pow.getLeftChild().equals(four)
            && unknown.getType() == Type.INTEGER
            && ((ASTCnIntegerNode)unknown).getInteger() == 8);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#pow(org.sbml.jsbml.math.ASTNode2, double)}.
   */
  @Test
  public final void testPowerWithReal() {
    ASTCnIntegerNode four = new ASTCnIntegerNode(4);
    ASTPowerNode pow = ASTFactory.pow(four, 8.0);
    ASTNode2 unknown = pow.getRightChild();
    assertTrue(pow.getType() == Type.POWER
            && pow.getChildCount() == 2
            && pow.getLeftChild().equals(four)
            && unknown.getType() == Type.REAL
            && ((ASTCnRealNode)unknown).getReal() == 8.0);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#product(org.sbml.jsbml.math.ASTNode2)}.
   */
  @Test
  public final void testProduct() {
    ASTCnIntegerNode four = new ASTCnIntegerNode(4);
    ASTCnIntegerNode eight = new ASTCnIntegerNode(8);
    ASTArithmeticOperatorNode product = ASTFactory.product(four, eight);
    assertTrue(product.getType() == Type.PRODUCT
            && product.getChildCount() == 2
            && product.getChildAt(0).equals(four)
            && product.getChildAt(1).equals(eight));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#product(org.sbml.jsbml.math.ASTNode2)}.
   */
  @Test
  public final void testProductNoArguments() {
    exception.expect(NullPointerException.class);
    ASTNode2[] list = null;
    ASTFactory.product(list);
  }  
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#reduceToBinary()}.
   */
  @Test
  public final void testReduceToBinaryDiff() {
    ASTCnIntegerNode[] integers = new ASTCnIntegerNode[10];
    for (int i = 0; i < 10; i++) {
      integers[i] = new ASTCnIntegerNode(i);
    }
    ASTArithmeticOperatorNode diff = ASTFactory.diff(integers);
    ASTMinusNode minus = (ASTMinusNode) ASTFactory.reduceToBinary(diff);
    assertTrue(countType(minus, Type.MINUS) == 9);
  }   
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#reduceToBinary()}.
   */
  @Test
  public final void testReduceToBinaryIllegalArgument() {
    exception.expect(IllegalArgumentException.class);
    ASTFactory.reduceToBinary(new ASTUnaryFunctionNode(Type.FUNCTION_ABS));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#reduceToBinary()}.
   */
  @Test
  public final void testReduceToBinaryProduct() {
    ASTCnIntegerNode[] integers = new ASTCnIntegerNode[10];
    for (int i = 0; i < 10; i++) {
      integers[i] = new ASTCnIntegerNode(i);
    }
    ASTArithmeticOperatorNode product = ASTFactory.product(integers);
    ASTTimesNode times = (ASTTimesNode) ASTFactory.reduceToBinary(product);
    assertTrue(countType(times, Type.TIMES) == 9);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#reduceToBinary()}.
   */
  @Test
  public final void testReduceToBinarySum() {
    ASTCnIntegerNode[] integers = new ASTCnIntegerNode[10];
    for (int i = 0; i < 10; i++) {
      integers[i] = new ASTCnIntegerNode(i);
    }
    ASTArithmeticOperatorNode sum = ASTFactory.sum(integers);
    ASTPlusNode plus = (ASTPlusNode) ASTFactory.reduceToBinary(sum);
    assertTrue(countType(plus, Type.PLUS) == 9);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#root(org.sbml.jsbml.math.ASTNode2, org.sbml.jsbml.math.ASTNode2)}.
   */
  @Test
  public final void testRoot() {
    ASTCnIntegerNode four = new ASTCnIntegerNode(4);
    ASTCnIntegerNode eight = new ASTCnIntegerNode(8);
    ASTRootNode root = ASTFactory.root(four, eight);
    assertTrue(root.getType() == Type.FUNCTION_ROOT 
            && root.getChildCount() == 2
            && root.getLeftChild().equals(four)
            && root.getRightChild().equals(eight));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#setParentSBMLObject(org.sbml.jsbml.math.ASTNode2, org.sbml.jsbml.MathContainer)}.
   */
  @Test
  public final void testSetParentSBMLObject() {
    ASTArithmeticOperatorNode plus = new ASTArithmeticOperatorNode(Type.PLUS);
    ASTCnIntegerNode one = new ASTCnIntegerNode(1);
    plus.addChild(one);
    ASTCnIntegerNode two = new ASTCnIntegerNode(2);
    plus.addChild(two);
    AssignmentRule rule = new AssignmentRule();
    ASTFactory.setParentSBMLObject(plus, rule);
    assertTrue(plus.getParentSBMLObject().equals(rule) 
             && one.getParentSBMLObject().equals(rule)
            && two.getParentSBMLObject().equals(rule));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#sqrt(org.sbml.jsbml.math.ASTNode2)}.
   */
  @Test
  public final void testSqrt() {
    ASTCnIntegerNode four = new ASTCnIntegerNode(4);
    ASTRootNode sqrt = ASTFactory.sqrt(four);
    assertTrue(sqrt.getType() == Type.FUNCTION_ROOT && sqrt.getChildCount() == 1);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#sum(org.sbml.jsbml.math.ASTNode2)}.
   */
  @Test
  public final void testSum() {
    ASTCnIntegerNode one = new ASTCnIntegerNode(1);
    ASTCnIntegerNode two = new ASTCnIntegerNode(2);
    ASTArithmeticOperatorNode sum = ASTFactory.sum(one, two);
    assertTrue(sum.getType() == Type.SUM && sum.getChildCount() == 2);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#sum(org.sbml.jsbml.math.ASTNode2)}.
   */
  @Test
  public final void testSumNoArgument() {
    exception.expect(NullPointerException.class);
    ASTNode2[] list = null;
    ASTFactory.sum(list);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#times(org.sbml.jsbml.math.ASTNode2, org.sbml.jsbml.math.ASTNode2)}.
   */
  @Test
  public final void testTimes() {
    ASTCnIntegerNode five = new ASTCnIntegerNode(5);
    ASTCnIntegerNode ten = new ASTCnIntegerNode(10);
    ASTTimesNode times = ASTFactory.times(five, ten);
    assertTrue(times.getChildCount() == 2
            && times.getLeftChild().equals(five)
            && times.getRightChild().equals(ten));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#times(org.sbml.jsbml.math.ASTNode2, int)}.
   */
  @Test
  public final void testTimesWithInteger() {
    ASTCnIntegerNode five = new ASTCnIntegerNode(5);
    ASTTimesNode times = ASTFactory.times(five, 10);
    ASTNode2 unknown = times.getRightChild();
    assertTrue(times.getChildCount() == 2
            && times.getLeftChild().equals(five)
            && unknown.getType() == Type.INTEGER 
            && ((ASTCnIntegerNode)unknown).getInteger() == 10);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#times(org.sbml.jsbml.math.ASTNode2, double)}.
   */
  @Test
  public final void testTimesWithReal() {
    ASTCnIntegerNode five = new ASTCnIntegerNode(5);
    ASTTimesNode times = ASTFactory.times(five, 10.0);
    ASTNode2 unknown = times.getRightChild();
    assertTrue(times.getChildCount() == 2
            && times.getLeftChild().equals(five)
            && unknown.getType() == Type.REAL 
            && ((ASTCnRealNode)unknown).getReal() == 10.0);
  }  
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#uMinus(org.sbml.jsbml.math.ASTNode2)}.
   */
  @Test
  public final void testUMinus() {
    ASTCnIntegerNode one = new ASTCnIntegerNode(1);
    ASTMinusNode minus = ASTFactory.uMinus(one);
    assertTrue(minus.getChildCount() == 1 && minus.getChildAt(0).equals(one));
  }
  
}
