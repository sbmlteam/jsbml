/*
 *
 * @file    TestASTNode.java
 * @brief   ASTNode unit tests
 *
 * @author  Akiya Jouraku (Java conversion)
 * @author  Ben Bornstein
 *
 *
 * This test file was converted from src/sbml/test/TestASTNode.c
 * with the help of conversion sciprt (ctest_converter.pl).
 *
 *<!---------------------------------------------------------------------------
 * This file is part of libSBML.  Please visit http://sbml.org for more
 * information about SBML, and the latest version of libSBML.
 *
 * Copyright 2005-2009 California Institute of Technology.
 * Copyright 2002-2005 California Institute of Technology and
 *                     Japan Science and Technology Corporation.
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation.  A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as http://sbml.org/software/libsbml/license.html
 *--------------------------------------------------------------------------->*/
package org.sbml.jsbml.test.sbml;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.JSBML;
import org.sbml.jsbml.ASTNode.Type;

/**
 * 
 */
public class TestASTNode {

  /**
   * @throws Exception
   */
  @Before public void setUp() throws Exception
  {
  }

  /**
   * @throws Exception
   */
  @After public void tearDown() throws Exception
  {
  }

  /**
   * 
   */
  public static final double DBL_EPSILON =  2.2204460492503131e-016;

  /**
   * 
   */
  @Test
  public void test_ASTNode_addChild1()
  {
    ASTNode node = new  ASTNode(Type.NAME); // TODO: setName will assign Type.FUNCTION if type and variable are not defined !!!
    ASTNode c1 = new  ASTNode(Type.NAME);
    ASTNode c2 = new  ASTNode(Type.NAME);
    ASTNode c1_1 = new  ASTNode(Type.NAME);

    node.setType(ASTNode.Type.LOGICAL_AND);
    c1.setName("a");
    c2.setName("b");
    node.addChild(c1);
    node.addChild(c2);
    assertTrue(node.getNumChildren() == 2);

    // System.out.println("test_ASTNode_addChild1: formula = " + node.toFormula());

    assertTrue(JSBML.formulaToString(node).equals("a and b")); // libsbml output that as a function: and(a, b)
    c1_1.setName("d");
    node.addChild(c1_1);
    assertTrue(node.getNumChildren() == 3);

    // System.out.println("test_ASTNode_addChild1: formula = " + node.toFormula());

    assertTrue(JSBML.formulaToString(node).equals("a and b and d"));
    assertTrue(node.getChild(0).getName().equals("a"));
    assertTrue(node.getChild(1).getName().equals("b"));
    assertTrue(node.getChild(2).getName().equals("d"));
    node = null;
  }

  /*
   // Not supported in JSBML (semantic annotation)
  public void test_ASTNode_addSemanticsAnnotation()
  {
    XMLNode ann = new XMLNode();
    ASTNode node = new  ASTNode();
    long i = 0;
    i = node.addSemanticsAnnotation(ann);
    assertTrue(i == libsbml.LIBSBML_OPERATION_SUCCESS);
    assertTrue(node.getNumSemanticsAnnotations() == 1);
    i = node.addSemanticsAnnotation(null);
    assertTrue(i == libsbml.LIBSBML_OPERATION_FAILED);
    assertTrue(node.getNumSemanticsAnnotations() == 1);
    node = null;
  }
   */
  /*
  // Not supported in JSBML (canonicalize method on ASTNode)
  public void test_ASTNode_canonicalizeConstants()
  {
  ...
	}

  public void test_ASTNode_canonicalizeFunctions()
  {
  ...
  }

  public void test_ASTNode_canonicalizeFunctionsL1()
  {
  ...
  }

  public void test_ASTNode_canonicalizeLogical()
  {
  ...
  }

  public void test_ASTNode_canonicalizeRelational()
  {
  ...
  }
   */

  /**
   * 
   */
  @Test
  public void test_ASTNode_children()
  {
    ASTNode parent = new  ASTNode();
    ASTNode left = new  ASTNode();
    ASTNode right = new  ASTNode();
    ASTNode right2 = new  ASTNode();
    parent.setType(ASTNode.Type.PLUS);
    left.setValue(1);
    right.setValue(2);
    right2.setValue(3);
    parent.addChild(left);
    parent.addChild(right);
    assertTrue(parent.getNumChildren() == 2);
    assertTrue(left.getNumChildren() == 0);
    assertTrue(right.getNumChildren() == 0);
    assertTrue(parent.getLeftChild().equals(left));
    assertTrue(parent.getRightChild().equals(right));
    assertTrue(parent.getChild(0).equals(left));
    assertTrue(parent.getChild(1).equals(right));

    try {
      parent.getChild(2); // libsbml would return null instead of throwing an exception: assertTrue(parent.getChild(2) == null);
      assertTrue(false);
    } catch (IndexOutOfBoundsException e) {
      assertTrue(true);
    }

    parent.addChild(right2);
    assertTrue(parent.getNumChildren() == 3);
    assertTrue(left.getNumChildren() == 0);
    assertTrue(right.getNumChildren() == 0);
    assertTrue(right2.getNumChildren() == 0);
    assertTrue(parent.getLeftChild().equals(left));
    assertTrue(parent.getRightChild().equals(right2));
    assertTrue(parent.getChild(0).equals(left));
    assertTrue(parent.getChild(1).equals(right));
    assertTrue(parent.getChild(2).equals(right2));

    try {
      parent.getChild(3);
      assertTrue(false);
    } catch (IndexOutOfBoundsException e) {
      assertTrue(true);
    }

    parent = null;
  }

  // The ASTNode.getXXX methods will throw exception in JSBML if not called with the right Type
  /**
   * 
   */
  @Test
  public void test_ASTNode_create()
  {
    ASTNode n = new  ASTNode();

    assertTrue(n.getType() == ASTNode.Type.UNKNOWN);

    try {
      n.getCharacter();
      assertTrue(false);
    } catch (Exception e) {
      assertTrue(true);
    }

    assertTrue(n.getName() == null);

    try {
      n.getInteger();
      assertTrue(false);
    } catch (Exception e) {
      assertTrue(true);
    }
    try {
      n.getExponent();
      assertTrue(false);
    } catch (Exception e) {
      assertTrue(true);
    }

    assertTrue(n.getNumChildren() == 0);

    assertTrue(n.getParentSBMLObject() == null);

    n = null;
  }

  // libsbml deepCopy() == JSBML clone()
  /**
   * 
   */
  @Test
  public void test_ASTNode_deepCopy_1()
  {
    ASTNode node = new  ASTNode();
    ASTNode child,copy;
    node.setCharacter('+');
    node.addChild(new  ASTNode());
    node.addChild(new  ASTNode());
    node.getLeftChild().setValue(1);
    node.getRightChild().setValue(2);
    assertTrue(node.getType() == ASTNode.Type.PLUS);
    assertTrue(node.getCharacter() ==  '+'      );
    assertTrue(node.getNumChildren() == 2);
    child = node.getLeftChild();
    assertTrue(child.getType() == ASTNode.Type.INTEGER);
    assertTrue(child.getInteger() == 1);
    assertTrue(child.getNumChildren() == 0);
    child = node.getRightChild();
    assertTrue(child.getType() == ASTNode.Type.INTEGER);
    assertTrue(child.getInteger() == 2);
    assertTrue(child.getNumChildren() == 0);
    copy = node.clone();
    assertTrue( copy.equals(node)); // libsbml would say that they are not equals
    assertTrue(copy.getType() == ASTNode.Type.PLUS);
    assertTrue(copy.getCharacter() ==  '+'      );
    assertTrue(copy.getNumChildren() == 2);
    child = copy.getLeftChild();
    assertTrue(child.equals(node.getLeftChild()));
    assertTrue(child.getType() == ASTNode.Type.INTEGER);
    assertTrue(child.getInteger() == 1);
    assertTrue(child.getNumChildren() == 0);
    child = copy.getRightChild();
    assertTrue(child.equals(node.getRightChild()));
    assertTrue(child.getType() == ASTNode.Type.INTEGER);
    assertTrue(child.getInteger() == 2);
    assertTrue(child.getNumChildren() == 0);
    node = null;
    copy = null;
  }

  /**
   * 
   */
  @Test
  public void test_ASTNode_deepCopy_2()
  {
    ASTNode node = new  ASTNode(Type.NAME); // TODO: setName will assign Type.FUNCTION if type and variable are not defined !!!
    ASTNode copy;
    node.setName("Foo");
    assertTrue(node.getType() == ASTNode.Type.NAME);
    assertTrue(node.getName().equals("Foo"));
    assertTrue(node.getNumChildren() == 0);
    copy = node.clone();
    assertTrue(copy.equals(node));
    assertTrue(copy.getType() == ASTNode.Type.NAME);
    assertTrue(copy.getName().equals("Foo"));
    assertTrue(copy.getNumChildren() == 0);
    node = null;
    copy = null;
  }

  /**
   * 
   */
  @Test
  public void test_ASTNode_deepCopy_3()
  {
    ASTNode node = new  ASTNode(ASTNode.Type.FUNCTION);
    ASTNode copy;
    node.setName("Foo");
    assertTrue(node.getType() == ASTNode.Type.FUNCTION);
    assertTrue(node.getName().equals("Foo"));
    assertTrue(node.getNumChildren() == 0);
    copy = node.clone();
    assertTrue(copy.equals(node));
    assertTrue(copy.getType() == ASTNode.Type.FUNCTION);
    assertTrue(copy.getName().equals("Foo"));
    assertTrue(copy.getNumChildren() == 0);
    node = null;
    copy = null;
  }

  /**
   * 
   */
  @Test
  public void test_ASTNode_deepCopy_4()
  {
    ASTNode node = new  ASTNode(ASTNode.Type.FUNCTION_ABS);
    ASTNode copy;
    node.setName("ABS"); // TODO setName() is changing the type of the node to Type.Function ??
    assertTrue(node.getType() == ASTNode.Type.FUNCTION_ABS);
    assertTrue(node.getName().equals("ABS"));
    assertTrue(node.getNumChildren() == 0);
    copy = node.clone();
    assertTrue(copy.equals(node));
    assertTrue(copy.getType() == ASTNode.Type.FUNCTION_ABS);
    assertTrue(copy.getName().equals("ABS"));
    assertTrue(copy.getNumChildren() == 0);
    node = null;
    copy = null;
  }

  /**
   * freeName does not exist in jsbml
   */
  @Test
  public void test_ASTNode_freeName()
  {
    ASTNode node = new  ASTNode(Type.NAME);
    node.setName("a");
    assertTrue(JSBML.formulaToString(node).equals("a"));
    assertTrue(node.getName().equals("a"));
    node.setName(null);
    assertTrue(node.getName() == null);
    node.setType(ASTNode.Type.UNKNOWN);
    assertTrue(node.getName() == null);
    node = null;
  }

  /*
  public void test_ASTNode_free_NULL()
  {
  }
   */

  /**
   * 
   */
  @Test
  public void test_ASTNode_getName()
  {
    ASTNode n = new  ASTNode();
    n.setName("foo");
    assertTrue(n.getName().equals("foo"));
    n.setType(ASTNode.Type.NAME_TIME);
    assertTrue(n.getName().equals("foo")); // TODO: name reset after calling setType ??
    n.setName(null);
    assertTrue(n.getName() == null);
    n.setType(ASTNode.Type.CONSTANT_E);
    assertTrue(n.getName().equals("exponentiale"));
    n.setType(ASTNode.Type.CONSTANT_FALSE);
    assertTrue(n.getName().equals("false"));
    n.setType(ASTNode.Type.CONSTANT_PI);
    assertTrue(n.getName().equals("pi"));
    n.setType(ASTNode.Type.CONSTANT_TRUE);
    assertTrue(n.getName().equals("true"));
    n.setType(ASTNode.Type.LAMBDA);
    assertTrue(n.getName().equals("lambda"));
    n.setType(ASTNode.Type.FUNCTION);
    n.setName("f");
    assertTrue(n.getName().equals("f"));
    n.setType(ASTNode.Type.FUNCTION_DELAY);
    assertTrue(n.getName().equals("f"));
    n.setName(null);
    assertTrue(n.getName().equals("delay"));
    n.setType(ASTNode.Type.FUNCTION);
    assertTrue(n.getName() == null);
    n.setType(ASTNode.Type.FUNCTION_ABS);
    assertTrue(n.getName().equals("abs"));
    n.setType(ASTNode.Type.FUNCTION_ARCCOS);
    assertTrue(n.getName().equals("arccos"));
    n.setType(ASTNode.Type.FUNCTION_TAN);
    assertTrue(n.getName().equals("tan"));
    n.setType(ASTNode.Type.FUNCTION_TANH);
    assertTrue(n.getName().equals("tanh"));
    n.setType(ASTNode.Type.LOGICAL_AND);
    assertTrue(n.getName().equals("and"));
    n.setType(ASTNode.Type.LOGICAL_NOT);
    assertTrue(n.getName().equals("not"));
    n.setType(ASTNode.Type.LOGICAL_OR);
    assertTrue(n.getName().equals("or"));
    n.setType(ASTNode.Type.LOGICAL_XOR);
    assertTrue(n.getName().equals("xor"));
    n.setType(ASTNode.Type.RELATIONAL_EQ);
    assertTrue(n.getName().equals("eq"));
    n.setType(ASTNode.Type.RELATIONAL_GEQ);
    assertTrue(n.getName().equals("geq"));
    n.setType(ASTNode.Type.RELATIONAL_LT);
    assertTrue(n.getName().equals("lt"));
    n.setType(ASTNode.Type.RELATIONAL_NEQ);
    assertTrue(n.getName().equals("neq"));
    n = null;
  }

  /*
   // getPrecedence() not available in jsbml
  public void test_ASTNode_getPrecedence()
  {
    ASTNode n = new  ASTNode();
    n.setType(ASTNode.Type.PLUS);
    assertTrue(n.getPrecedence() == 2);
    n.setType(ASTNode.Type.MINUS);
    assertTrue(n.getPrecedence() == 2);
    n.setType(ASTNode.Type.TIMES);
    assertTrue(n.getPrecedence() == 3);
    n.setType(ASTNode.Type.DIVIDE);
    assertTrue(n.getPrecedence() == 3);
    n.setType(ASTNode.Type.POWER);
    assertTrue(n.getPrecedence() == 4);
    n.setType(ASTNode.Type.MINUS);
    n.addChild(new  ASTNode(ASTNode.Type.NAME));
    assertTrue(n.isUMinus() == true);
    assertTrue(n.getPrecedence() == 5);
    n.setType(ASTNode.Type.NAME);
    assertTrue(n.getPrecedence() == 6);
    n.setType(ASTNode.Type.FUNCTION);
    assertTrue(n.getPrecedence() == 6);
    n = null;
  }
   */

  /**
   * 
   */
  @Test
  public void test_ASTNode_getReal()
  {
    ASTNode n = new  ASTNode();
    n.setType(ASTNode.Type.REAL);
    n.setValue(1.6);
    assertTrue(n.getReal() == 1.6);
    n.setType(ASTNode.Type.REAL_E);
    n.setValue(12.3,3);
    assertTrue(java.lang.Math.abs(n.getReal() - 12300.0) < DBL_EPSILON);
    n.setType(ASTNode.Type.RATIONAL);
    n.setValue(1,2);
    assertTrue(n.getReal() == 0.5);
    n = null;
  }

  /**
   * 
   */
  @Test
  public void test_ASTNode_insertChild()
  {
    ASTNode node = new  ASTNode();
    ASTNode c1 = new  ASTNode(Type.NAME);
    ASTNode c2 = new  ASTNode(Type.NAME);
    ASTNode c3 = new  ASTNode(Type.NAME);
    ASTNode newc = new  ASTNode(Type.NAME);
    ASTNode newc1 = new  ASTNode(Type.NAME);

    node.setType(ASTNode.Type.LOGICAL_AND);
    c1.setName("a");
    c2.setName("b");
    c3.setName("c");
    node.addChild(c1);
    node.addChild(c2);
    node.addChild(c3);
    assertTrue(node.getNumChildren() == 3);
    assertTrue(JSBML.formulaToString(node).equals("a and b and c"));
    newc.setName("d");
    newc1.setName("e");
    node.insertChild(1,newc);
    assertTrue(node.getNumChildren() == 4);
    assertTrue(JSBML.formulaToString(node).equals("a and d and b and c"));

    try {
      node.insertChild(5,newc);
      assertTrue(false);
    } catch(IndexOutOfBoundsException e) {
      assertTrue(true);
    }
    assertTrue(node.getNumChildren() == 4);
    assertTrue(JSBML.formulaToString(node).equals("a and d and b and c"));
    node.insertChild(2,newc1);
    assertTrue(node.getNumChildren() == 5);
    assertTrue(JSBML.formulaToString(node).equals("a and d and e and b and c"));
    node = null;
  }

  /**
   * 
   */
  @Test
  public void test_ASTNode_isLog10()
  {
    ASTNode n = new  ASTNode();
    ASTNode c;
    n.setType(ASTNode.Type.FUNCTION);
    assertTrue(n.isLog10() == false);
    n.setType(ASTNode.Type.FUNCTION_LOG);
    assertTrue(n.isLog10() == false);
    c = new  ASTNode();
    n.addChild(c);
    c.setValue(10);
    assertTrue(n.isLog10() == false);
    n.addChild(new  ASTNode());
    assertTrue(n.isLog10() == true);
    c.setValue(2);
    assertTrue(n.isLog10() == false);
    n = null;
  }

  /**
   * 
   */
  @Test
  public void test_ASTNode_isSqrt()
  {
    ASTNode n = new  ASTNode();
    ASTNode c;
    n.setType(ASTNode.Type.FUNCTION);
    assertTrue(n.isSqrt() == false);
    n.setType(ASTNode.Type.FUNCTION_ROOT);
    assertTrue(n.isSqrt() == false);
    c = new  ASTNode();
    n.addChild(c);
    c.setValue(2);
    assertTrue(n.isSqrt() == false);
    n.addChild(new  ASTNode());
    assertTrue(n.isSqrt() == true);
    c.setValue(3);
    assertTrue(n.isSqrt() == false);
    n = null;
  }

  /**
   * 
   */
  @Test
  public void test_ASTNode_isUMinus()
  {
    ASTNode n = new  ASTNode();
    n.setType(ASTNode.Type.MINUS);
    assertTrue(n.isUMinus() == false);
    n.addChild(new  ASTNode(ASTNode.Type.NAME));
    assertTrue(n.isUMinus() == true);
    n = null;
  }

  /**
   * 
   */
  @Test
  public void test_ASTNode_no_children()
  {
    ASTNode node = new  ASTNode();
    assertTrue(node.getNumChildren() == 0);

    try {
      node.getLeftChild();
      assertTrue(false);
    } catch(IndexOutOfBoundsException e) {
      assertTrue(true);
    }
    try {
      node.getRightChild();
      assertTrue(false);
    } catch(IndexOutOfBoundsException e) {
      assertTrue(true);
    }
    try {
      node.getChild(0);
      assertTrue(false);
    } catch(IndexOutOfBoundsException e) {
      assertTrue(true);
    }
    node = null;
  }

  /**
   * 
   */
  @Test
  public void test_ASTNode_one_child()
  {
    ASTNode node = new  ASTNode();
    ASTNode child = new  ASTNode();
    node.addChild(child);
    assertTrue(node.getNumChildren() == 1);
    assertTrue(node.getLeftChild().equals(child));

    assertTrue(node.getRightChild().equals(child)); // libsbml would return null for the rightChild here

    assertTrue(node.getChild(0).equals(child));

    try {
      assertTrue(node.getChild(1) == null);
      assertTrue(false);
    } catch(IndexOutOfBoundsException e) {
      assertTrue(true);
    }
    node = null;
  }

  /**
   * 
   */
  @Test
  public void test_ASTNode_prependChild1()
  {
    ASTNode node = new  ASTNode();
    ASTNode c1 = new  ASTNode(Type.NAME);
    ASTNode c2 = new  ASTNode(Type.NAME);
    ASTNode c1_1 = new  ASTNode(Type.NAME);

    node.setType(ASTNode.Type.LOGICAL_AND);
    c1.setName("a");
    c2.setName("b");
    node.addChild(c1);
    node.addChild(c2);
    assertTrue(node.getNumChildren() == 2);
    assertTrue(JSBML.formulaToString(node).equals("a and b"));
    c1_1.setName("d");
    node.prependChild(c1_1);
    assertTrue(node.getNumChildren() == 3);
    assertTrue(node.getChild(0).getName().equals("d"));
    assertTrue(node.getChild(1).getName().equals("a"));
    assertTrue(node.getChild(2).getName().equals("b"));
    assertTrue(JSBML.formulaToString(node).equals("d and a and b"));
    node = null;
  }

  /**
   * 
   */
  @Test
  public void test_ASTNode_removeChild()
  {
    ASTNode node = new  ASTNode();
    ASTNode c1 = new  ASTNode();
    ASTNode c2 = new  ASTNode();

    node.setType(ASTNode.Type.PLUS);
    c1.setName("foo");
    c2.setName("foo2");
    node.addChild(c1);
    node.addChild(c2);
    assertTrue(node.getNumChildren() == 2);
    node.removeChild(0);
    assertTrue(node.getNumChildren() == 1);
    node.removeChild(1); // exception thrown ??
    // assertTrue(i == libsbml.LIBSBML_INDEX_EXCEEDS_SIZE);
    assertTrue(node.getNumChildren() == 1);
    node.removeChild(0);
    assertTrue(node.getNumChildren() == 0);
    node = null;
  }

  /**
   * 
   */
  @Test
  public void test_ASTNode_replaceChild()
  {
    ASTNode node = new  ASTNode();
    ASTNode c1 = new  ASTNode(Type.NAME);
    ASTNode c2 = new  ASTNode(Type.NAME);
    ASTNode c3 = new  ASTNode(Type.NAME);
    ASTNode newc = new  ASTNode(Type.NAME);

    node.setType(ASTNode.Type.LOGICAL_AND);
    c1.setName("a");
    c2.setName("b");
    c3.setName("c");
    node.addChild(c1);
    node.addChild(c2);
    node.addChild(c3);
    assertTrue(node.getNumChildren() == 3);
    assertTrue(JSBML.formulaToString(node).equals("a and b and c"));
    newc.setName("d");
    node.replaceChild(0,newc);
    assertTrue(node.getNumChildren() == 3);
    assertTrue(JSBML.formulaToString(node).equals("d and b and c"));

    try {
      node.replaceChild(3,newc);
      assertTrue(false);
    } catch (IndexOutOfBoundsException e) {
      assertTrue(true);
    }

    assertTrue(node.getNumChildren() == 3);
    assertTrue(JSBML.formulaToString(node).equals("d and b and c"));
    node.replaceChild(1,c1);
    assertTrue(node.getNumChildren() == 3);
    assertTrue(JSBML.formulaToString(node).equals("d and a and c"));
    node = null;
  }

  /**
   * 
   */
  @Test
  public void test_ASTNode_setCharacter()
  {
    ASTNode node = new  ASTNode(Type.NAME);
    node.setName("foo");
    assertTrue(node.getType() == ASTNode.Type.NAME);
    // assertTrue(node.getCharacter() == '\0');
    assertTrue(node.getName().equals("foo"));
    // assertTrue(node.getInteger() == 0);
    // assertTrue(node.getReal() == 0);
    // assertTrue(node.getExponent() == 0);
    // assertTrue(node.getDenominator() == 1);

    node.setCharacter('+');
    assertTrue(node.getType() == ASTNode.Type.PLUS);
    assertTrue(node.getCharacter() ==  '+'      );
    // assertTrue(node.getName() == null);
    // assertTrue(node.getInteger() == 0);
    // assertTrue(node.getReal() == 0);
    // assertTrue(node.getExponent() == 0);
    // assertTrue(node.getDenominator() == 1);

    node.setCharacter('-');
    assertTrue(node.getType() == ASTNode.Type.MINUS);
    assertTrue(node.getCharacter() ==  '-'       );

    node.setCharacter('*');
    assertTrue(node.getType() == ASTNode.Type.TIMES);
    assertTrue(node.getCharacter() ==  '*'       );

    node.setCharacter('/');
    assertTrue(node.getType() == ASTNode.Type.DIVIDE);
    assertTrue(node.getCharacter() ==  '/'        );

    node.setCharacter('^');
    assertTrue(node.getType() == ASTNode.Type.POWER);
    assertTrue(node.getCharacter() ==  '^'       );

    node.setCharacter('$');
    assertTrue(node.getType() == ASTNode.Type.UNKNOWN);
    // assertTrue(node.getCharacter() ==  '$'         );  // Exception thrown

    node = null;
  }

  /**
   * 
   */
  @Test
  public void test_ASTNode_setInteger()
  {
    ASTNode node = new  ASTNode(Type.NAME);
    node.setName("foo");
    assertTrue(node.getType() == ASTNode.Type.NAME);
    assertTrue(node.getName().equals("foo"));
    // assertTrue(node.getCharacter() == '\0');
    // assertTrue(node.getInteger() == 0);
    // assertTrue(node.getReal() == 0);
    // assertTrue(node.getExponent() == 0);
    // assertTrue(node.getDenominator() == 1);

    node.setValue(3.2);

    assertTrue(node.getType() == ASTNode.Type.REAL);
    // assertTrue(node.getInteger() == 0);
    // assertTrue(node.getName() == null);
    // assertTrue(node.getCharacter() == '\0');
    assertTrue(node.getReal() == 3.2);
    assertTrue(node.getExponent() == 0);
    // assertTrue(node.getDenominator() == 1);

    node.setValue(321);

    assertTrue(node.getType() == ASTNode.Type.INTEGER);
    assertTrue(node.getInteger() == 321);
    // assertTrue(node.getName() == null);
    // assertTrue(node.getCharacter() == '\0');
    assertTrue(node.getReal() == 321);
    // assertTrue(node.getExponent() == 0);
    // assertTrue(node.getDenominator() == 1);
    node = null;
  }

  /**
   * 
   */
  @Test
  public void test_ASTNode_setName()
  {
    String name =  "foo";;
    ASTNode node = new  ASTNode();
    assertTrue(node.getType() == ASTNode.Type.UNKNOWN);
    node.setName(name);
    assertTrue(node.getType() == ASTNode.Type.NAME);
    assertTrue(node.getName().equals(name));
    // assertTrue(node.getCharacter() == '\0');
    // assertTrue(node.getInteger() == 0);
    // assertTrue(node.getReal() == 0);
    // assertTrue(node.getExponent() == 0);
    // assertTrue(node.getDenominator() == 1);

    node.setName(null);
    assertTrue(node.getType() == ASTNode.Type.NAME);

    assertTrue(node.getName() == null);

    node.setType(ASTNode.Type.FUNCTION_COS);
    assertTrue(node.getType() == ASTNode.Type.FUNCTION_COS);
    assertTrue(node.getName().equals("cos"));
    assertTrue(node.getCharacter() == '\0');
    assertTrue(node.getInteger() == 0);
    assertTrue(node.getReal() == 0);
    assertTrue(node.getExponent() == 0);
    assertTrue(node.getDenominator() == 1);
    node.setType(ASTNode.Type.PLUS);
    node.setName(name);
    assertTrue(node.getType() == ASTNode.Type.NAME);
    assertTrue(node.getName().equals(name));
    assertTrue(node.getCharacter() ==  '+'        );
    assertTrue(node.getInteger() == 0);
    assertTrue(node.getReal() == 0);
    assertTrue(node.getExponent() == 0);
    assertTrue(node.getDenominator() == 1);
    node = null;
  }

  /**
   * 
   */
  @Test
  public void test_ASTNode_setName_override()
  {
    ASTNode node = new  ASTNode(ASTNode.Type.FUNCTION_SIN);
    assertTrue(node.getName().equals("sin"));
    assertTrue(node.getType() == ASTNode.Type.FUNCTION_SIN);
    node.setName("MySinFunc");
    assertTrue(node.getName().equals("MySinFunc"));
    assertTrue(node.getType() == ASTNode.Type.FUNCTION_SIN);
    node.setName(null);
    assertTrue(node.getName().equals("sin"));
    assertTrue(node.getType() == ASTNode.Type.FUNCTION_SIN);
    node = null;
  }

  /**
   * 
   */
  @Test
  public void test_ASTNode_setReal()
  {
    ASTNode node = new  ASTNode();

    node.setValue(32.1);
    assertTrue(node.getType() == ASTNode.Type.REAL);

    // assertTrue(node.getInteger() == 0); // Exception thrown
    // assertTrue(node.getName() == null); // Exception thrown
    // assertTrue(node.getCharacter() == '\0');  // Exception thrown
    assertTrue(node.getReal() == 32.1);
    assertTrue(node.getExponent() == 0);
    // assertTrue(node.getDenominator() == 1);  // Exception thrown
    assertTrue(node.getMantissa() == 32.1);

    node.setValue(45,90);

    assertTrue(node.getType() == ASTNode.Type.RATIONAL);
    // assertTrue(node.getInteger() == 45);
    // assertTrue(node.getName() == null);
    // assertTrue(node.getCharacter() == '\0');
    assertTrue(node.getReal() == 0.5);
    // assertTrue(node.getExponent() == 0); // Exception thrown
    assertTrue(node.getDenominator() == 90);
    // assertTrue(node.getMantissa() == 0); // Exception thrown

    node.setValue(32.0,4);

    assertTrue(node.getType() == ASTNode.Type.REAL_E);
    // assertTrue(node.getInteger() == 0);
    // assertTrue(node.getName() == null);
    // assertTrue(node.getCharacter() == '\0');
    assertTrue(node.getReal() == 320000);
    assertTrue(node.getExponent() == 4);
    // assertTrue(node.getDenominator() == 1);
    assertTrue(node.getMantissa() == 32);
    node = null;
  }

  /**
   * 
   */
  @Test
  public void test_ASTNode_setType()
  {
    ASTNode node = new  ASTNode(Type.NAME);
    node.setName("foo");
    assertTrue(node.getType() == ASTNode.Type.NAME);
    node.setType(ASTNode.Type.FUNCTION);
    assertTrue(node.getType() == ASTNode.Type.FUNCTION);
    assertTrue(node.getName().equals("foo"));
    node.setType(ASTNode.Type.NAME);
    assertTrue(node.getType() == ASTNode.Type.NAME);
    assertTrue(node.getName() == null); // in jsbml setType will reset the name !! assertTrue(node.getName().equals("foo"));
    node.setType(ASTNode.Type.INTEGER);
    assertTrue(node.getType() == ASTNode.Type.INTEGER);
    node.setType(ASTNode.Type.REAL);
    assertTrue(node.getType() == ASTNode.Type.REAL);
    node.setType(ASTNode.Type.UNKNOWN);
    assertTrue(node.getType() == ASTNode.Type.UNKNOWN);
    node.setType(ASTNode.Type.PLUS);
    assertTrue(node.getType() == ASTNode.Type.PLUS);
    assertTrue(node.getCharacter() ==  '+'      );
    node.setType(ASTNode.Type.MINUS);
    assertTrue(node.getType() == ASTNode.Type.MINUS);
    assertTrue(node.getCharacter() ==  '-'       );
    node.setType(ASTNode.Type.TIMES);
    assertTrue(node.getType() == ASTNode.Type.TIMES);
    assertTrue(node.getCharacter() ==  '*'       );
    node.setType(ASTNode.Type.DIVIDE);
    assertTrue(node.getType() == ASTNode.Type.DIVIDE);
    assertTrue(node.getCharacter() ==  '/'        );
    node.setType(ASTNode.Type.POWER);
    assertTrue(node.getType() == ASTNode.Type.POWER);
    assertTrue(node.getCharacter() ==  '^'       );
    node = null;
  }

  /**
   * 
   */
  @Test
  public void test_ASTNode_swapChildren()
  {
    ASTNode node = new  ASTNode();
    ASTNode c1 = new  ASTNode(Type.NAME);
    ASTNode c2 = new  ASTNode(Type.NAME);
    ASTNode node_1 = new  ASTNode();
    ASTNode c1_1 = new  ASTNode(Type.NAME);
    ASTNode c2_1 = new  ASTNode(Type.NAME);

    node.setType(ASTNode.Type.LOGICAL_AND);
    c1.setName("a");
    c2.setName("b");
    node.addChild(c1);
    node.addChild(c2);
    assertTrue(node.getNumChildren() == 2);
    assertTrue(JSBML.formulaToString(node).equals("a and b"));
    node_1.setType(ASTNode.Type.LOGICAL_AND);
    c1_1.setName("d");
    c2_1.setName("f");
    node_1.addChild(c1_1);
    node_1.addChild(c2_1);
    assertTrue(node_1.getNumChildren() == 2);
    assertTrue(JSBML.formulaToString(node_1).equals("d and f"));
    node.swapChildren(node_1);
    assertTrue(node.getNumChildren() == 2);
    assertTrue(JSBML.formulaToString(node).equals("d and f"));
    assertTrue(node_1.getNumChildren() == 2);
    assertTrue(JSBML.formulaToString(node_1).equals("a and b"));
    node_1 = null;
    node = null;
  }

}
