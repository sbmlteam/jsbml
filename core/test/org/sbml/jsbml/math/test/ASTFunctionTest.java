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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.AssignmentRule;
import org.sbml.jsbml.CallableSBase;
import org.sbml.jsbml.Constraint;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.math.ASTCiNumberNode;
import org.sbml.jsbml.math.ASTCnIntegerNode;
import org.sbml.jsbml.math.ASTCnNumberNode;
import org.sbml.jsbml.math.ASTFunction;
import org.sbml.jsbml.math.ASTNode2;
import org.sbml.jsbml.math.ASTPiecewiseFunctionNode;
import org.sbml.jsbml.math.ASTPlusNode;
import org.sbml.jsbml.math.ASTTimesNode;
import org.sbml.jsbml.util.filters.NameFilter;

/**
 * Test cases for {@link ASTFunction}
 * 
 * @author Victor Kofia
 * @since 1.0
 */
public class ASTFunctionTest {

  /**
   * 
   */
  @Rule
  public ExpectedException exception = ExpectedException.none();

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFunction#addChild(org.sbml.jsbml.math.ASTNode2)}.
   */
  @Test
  public final void testAddChildNonExistent() {
    ASTFunction function = new ASTFunction();
    exception.expect(NullPointerException.class);
    function.addChild(null);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFunction#addChild(org.sbml.jsbml.math.ASTNode2)}.
   */
  @Test
  public final void testAddChildWithChildren() {
    ASTFunction function = new ASTFunction();
    ASTCnIntegerNode integer = new ASTCnIntegerNode(21);
    function.addChild(integer);
    assertTrue(function.getChildAt(0).equals(integer));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFunction#clone()}.
   */
  @Test
  public final void testClone() {
    ASTFunction function = new ASTFunction();
    ASTFunction unknown = function.clone();
    assertTrue(function.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFunction#clone()}.
   */
  @Test
  public final void testCloneWithChildren() {
    ASTFunction function = new ASTFunction();
    function.addChild(new ASTCnIntegerNode(10));
    function.addChild(new ASTCnIntegerNode(50));
    ASTFunction unknown = function.clone();
    assertTrue(function.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFunction#ASTFunction(org.sbml.jsbml.math.ASTFunction)}.
   */
  @Test
  public final void testCloneWithConstructor() {
    ASTFunction function = new ASTFunction();
    ASTFunction unknown = new ASTFunction(function);
    assertTrue(function.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFunction#ASTFunction(org.sbml.jsbml.math.ASTFunction)}.
   */
  @Test
  public final void testFindReferencedGlobalParameters() {
    Model model = new Model();
    Constraint constraint = new Constraint();
    model.addConstraint(constraint);

    ASTTimesNode times = new ASTTimesNode();
    times.setParentSBMLObject(constraint);

    Parameter tau = new Parameter();
    tau.setId("tau2");
    tau.setValue(3e-2);
    tau.setUnits("seconds");
    tau.setConstant(true);
    model.addParameter(tau);
    ASTCiNumberNode tauCi = new ASTCiNumberNode();
    tauCi.setReference(tau);

    Parameter alpha = new Parameter();
    alpha.setId("alpha2");
    alpha.setValue(3e-2);
    alpha.setUnits("seconds");
    alpha.setConstant(true);
    model.addParameter(alpha);
    ASTCiNumberNode alphaCi = new ASTCiNumberNode();
    alphaCi.setReference(alpha);

    ASTPlusNode plus = new ASTPlusNode();
    plus.addChild(alphaCi);
    plus.addChild(new ASTCnIntegerNode(2));
    times.addChild(tauCi);
    times.addChild(plus);

    List<CallableSBase> params = times.findReferencedCallableSBases();

    assertTrue(params.size() == 2 && params.contains(tau) && params.contains(alpha));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFunction#getAllowsChildren()}.
   */
  @Test
  public final void testGetAllowsChildren() {
    ASTFunction function = new ASTFunction();
    assertTrue(function.getAllowsChildren());
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFunction#getChildCount()}.
   */
  @Test
  public final void testGetChildCountNonExistent() {
    ASTFunction function = new ASTFunction();
    assertTrue(function.getChildCount() == 0);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFunction#getChildCount()}.
   */
  @Test
  public final void testGetChildCountWithChildren() {
    ASTFunction function = new ASTFunction();
    function.addChild(new ASTCnIntegerNode(10));
    function.addChild(new ASTCnIntegerNode(50));
    assertTrue(function.getChildCount() == 2);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFunction#getChildren()}.
   */
  @Test
  public final void testGetChildrenWithChildren() {
    ASTFunction function = new ASTFunction();
    function.addChild(new ASTCnIntegerNode(10));
    function.addChild(new ASTCnIntegerNode(50));
    assertTrue(function.getChildren().getClass().equals(ArrayList.class));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.AbstractASTNode#getId()}.
   */
  @Test
  public final void testGetId() {
    ASTFunction x = new ASTFunction();
    String id = "10010";
    x.setId(id);
    assertTrue(x.getId().equals(id));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.AbstractASTNode#getId()}.
   */
  @Test
  public final void testGetIdNonExistent() {
    ASTFunction x = new ASTFunction();
    exception.expect(PropertyUndefinedError.class);
    x.getId();
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.AbstractASTNode#getId()}.
   */
  @Test
  public final void testGetIdNonExistentNonStrict() {
    ASTFunction x = new ASTFunction();
    x.setStrictness(false);
    assertTrue(x.getId().isEmpty());
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFunction#getListOfNodes(org.sbml.jsbml.util.filters.Filter)}.
   */
  @Test
  public final void testGetListOfNodesFilterNonExistent() {
    ASTFunction function = new ASTFunction();
    ASTPiecewiseFunctionNode piece = new ASTPiecewiseFunctionNode();
    piece.setId("id");
    piece.setName("name");
    function.addChild(piece);
    function.addChild(new ASTCnIntegerNode(50));
    NameFilter filter = new NameFilter("id#", "name#");
    ArrayList<ASTNode2> listOfNodes = (ArrayList<ASTNode2>) function.getListOfNodes(filter);
    assertTrue(listOfNodes.size() == 0);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFunction#getListOfNodes(org.sbml.jsbml.util.filters.Filter)}.
   */
  @Test
  public final void testGetListOfNodesFilterNotInitialized() {
    ASTFunction function = new ASTFunction();
    NameFilter filter = new NameFilter("id#", "name#");
    assertTrue(function.getListOfNodes(filter).size() == 0);
  }

  //  /**
  //   * Test method for {@link org.sbml.jsbml.math.ASTFunction#getListOfNodes(org.sbml.jsbml.util.filters.Filter)}.
  //   */
  //  @Test
  //  public final void testGetListOfNodesFilterWithChildren() {
  //    ASTFunction function = new ASTFunction();
  //    Reaction reaction = new Reaction();
  //    reaction.setId("id");
  //    reaction.setName("name");
  //    function.addChild(reaction);
  //    function.addChild(new ASTCnIntegerNode(50));
  //    NameFilter filter = new NameFilter("id", "name");
  //    ArrayList<ASTNode2> listOfNodes = (ArrayList<ASTNode2>) function.getListOfNodes(filter);
  //    assertTrue(listOfNodes.size() == 1 && listOfNodes.get(0).equals(reaction));
  //  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFunction#getListOfNodes()}.
   */
  @Test
  public final void testGetListOfNodesWithChildren() {
    ASTFunction function = new ASTFunction();
    function.addChild(new ASTCnIntegerNode(10));
    function.addChild(new ASTCnIntegerNode(50));
    assertTrue(function.getChildren().getClass().equals(ArrayList.class));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.AbstractASTNode#getMathMLClass()}.
   */
  @Test
  public final void testGetMathMLClass() {
    ASTFunction x = new ASTFunction();
    String mathMLClass = "class";
    x.setMathMLClass(mathMLClass);
    assertTrue(x.getMathMLClass().equals(mathMLClass));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.AbstractASTNode#getMathMLClass()}.
   */
  @Test
  public final void testGetMathMLClassNonExistent() {
    ASTFunction x = new ASTFunction();
    exception.expect(PropertyUndefinedError.class);
    x.getMathMLClass();
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.AbstractASTNode#getMathMLClass()}.
   */
  @Test
  public final void testGetMathMLClassNonExistentNonStrict() {
    ASTFunction x = new ASTFunction();
    x.setStrictness(false);
    assertTrue(x.getMathMLClass().isEmpty());
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.AbstractASTNode#getParent()}.
   */
  @Test
  public final void testGetParent() {
    ASTFunction x = new ASTFunction();
    x.setName("x");
    ASTFunction y = new ASTFunction();
    y.setName("y");
    ASTPlusNode plus = new ASTPlusNode();
    x.setParent(plus);
    y.setParent(plus);
    assertTrue(x.getParent().equals(plus) && y.getParent().equals(plus));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.AbstractASTNode#getParent()}.
   */
  @Test
  public final void testGetParentNonExistent() {
    ASTFunction x = new ASTFunction();
    x.setName("x");
    ASTFunction y = new ASTFunction();
    y.setName("y");
    assertTrue(x.getParent() == null && y.getParent() == null);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.AbstractASTNode#getParentSBMLObject()}.
   */
  @Test
  public final void testGetParentSBMLObject() {
    AssignmentRule rule = new AssignmentRule();
    ASTFunction function = new ASTFunction(rule);
    function.setParentSBMLObject(rule);
    assertTrue(function.getParentSBMLObject().equals(rule));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.AbstractASTNode#getParentSBMLObject()}.
   */
  @Test
  public final void testGetParentSBMLObjectNonExistent() {
    ASTFunction function = new ASTFunction();
    assertTrue(function.getParentSBMLObject() == null);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.AbstractASTNode#getParentSBMLObject()}.
   */
  @Test
  public final void testGetParentSBMLObjectNonExistentNonStrict() {
    ASTFunction function = new ASTFunction();
    function.setStrictness(false);
    assertTrue(function.getParentSBMLObject() == null);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.AbstractASTNode#getParentSBMLObject()}.
   */
  @Test
  public final void testGetParentSBMLObjectWithConstructor() {
    AssignmentRule rule = new AssignmentRule();
    ASTFunction function = new ASTFunction(rule);
    assertTrue(function.getParentSBMLObject().equals(rule));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.AbstractASTNode#getStyle()}.
   */
  @Test
  public final void testGetStyle() {
    ASTFunction x = new ASTFunction();
    String style = "style";
    x.setStyle(style);
    assertTrue(x.getStyle().equals(style));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.AbstractASTNode#getStyle()}.
   */
  @Test
  public final void testGetStyleNonExistent() {
    ASTFunction x = new ASTFunction();
    exception.expect(PropertyUndefinedError.class);
    x.getStyle();
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.AbstractASTNode#getStyle()}.
   */
  @Test
  public final void testGetStyleNonExistentNonStrict() {
    ASTFunction x = new ASTFunction();
    x.setStrictness(false);
    assertTrue(x.getStyle().isEmpty());
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.AbstractASTNode#getType()}.
   */
  @Test
  public final void testGetType() {
    ASTFunction x = new ASTFunction();
    x.setType(Type.FUNCTION_ABS);
    assertTrue(x.getType() == Type.FUNCTION_ABS);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.AbstractASTNode#getType()}.
   */
  @Test
  public final void testGetTypeNonExistent() {
    ASTFunction x = new ASTFunction();
    exception.expect(PropertyUndefinedError.class);
    x.getType();
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.AbstractASTNode#getType()}.
   */
  @Test
  public final void testGetTypeNonExistentNonStrict() {
    ASTFunction x = new ASTFunction();
    x.setStrictness(false);
    assertTrue(x.getType() == null);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFunction#hasUnits()}.
   */
  @Test
  public final void testHasUnitsFalse() {
    ASTFunction a = new ASTFunction();
    ASTCnNumberNode<Integer> number = new ASTCnNumberNode<Integer>();
    a.addChild(number);
    assertTrue(!a.hasUnits());
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFunction#hasUnits()}.
   */
  @Test
  public final void testHasUnitsTrue() {
    ASTFunction a = new ASTFunction();
    ASTCnNumberNode<Integer> number = new ASTCnNumberNode<Integer>();
    number.setUnits("mole");
    a.addChild(number);
    assertTrue(a.hasUnits());
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFunction#isAllowableType(Type)}.
   */
  @Test
  public final void testIsAllowableType() {
    ASTFunction function = new ASTFunction();
    assertTrue(function.isAllowableType(Type.VECTOR));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFunction#prependChild(org.sbml.jsbml.math.ASTNode2)}.
   */
  @Test
  public final void testPrependChildNonExistent() {
    ASTFunction function = new ASTFunction();
    exception.expect(NullPointerException.class);
    function.prependChild(null);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFunction#prependChild(org.sbml.jsbml.math.ASTNode2)}.
   */
  @Test
  public final void testPrependChildWithChildren() {
    ASTFunction function = new ASTFunction();
    ASTCnIntegerNode ten = new ASTCnIntegerNode(10);
    function.prependChild(ten);
    ASTCnIntegerNode five = new ASTCnIntegerNode(5);
    function.prependChild(five);
    assertTrue(function.getChildAt(0).equals(five) && function.getChildAt(1).equals(ten));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFunction#removeChild(int)}.
   */
  @Test
  public final void testRemoveChildNonExistent() {
    ASTFunction function = new ASTFunction();
    assertFalse(function.removeChild(0));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFunction#removeChild(int)}.
   */
  @Test
  public final void testRemoveChildWithChildren() {
    ASTFunction function = new ASTFunction();
    function.addChild(new ASTCnIntegerNode(50));
    assertTrue(function.removeChild(0) && function.getChildCount() == 0);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFunction#replaceChild(int, org.sbml.jsbml.math.ASTNode2)}.
   */
  @Test
  public final void testReplaceChildNonExistent() {
    ASTFunction function = new ASTFunction();
    exception.expect(IndexOutOfBoundsException.class);
    function.replaceChild(0, new ASTCnIntegerNode(9));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFunction#replaceChild(int, org.sbml.jsbml.math.ASTNode2)}.
   */
  @Test
  public final void testReplaceChildWithChildren() {
    ASTFunction function = new ASTFunction();
    ASTCnIntegerNode ten = new ASTCnIntegerNode(10);
    function.addChild(ten);
    ASTCnIntegerNode five = new ASTCnIntegerNode(5);
    function.replaceChild(0, five);
    assertTrue(function.getChildAt(0).equals(five));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFunction#swapChildren(org.sbml.jsbml.math.ASTFunction)}.
   */
  @Test
  public final void testSwapChildren() {
    ASTFunction a = new ASTFunction();
    a.addChild(new ASTCnIntegerNode(2));
    a.addChild(new ASTCnIntegerNode(4));
    a.addChild(new ASTCnIntegerNode(6));
    ASTFunction b = new ASTFunction();
    b.addChild(new ASTCnIntegerNode(15));
    b.addChild(new ASTCnIntegerNode(20));
    b.addChild(new ASTCnIntegerNode(25));
    b.addChild(new ASTCnIntegerNode(30));
    a.swapChildren(b);
    assertTrue((a.getChildCount() == 4) && (b.getChildCount() == 3));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.AbstractASTNode#unsetParentSBMLObject()}.
   */
  @Test
  public final void testUnsetParentSBMLObject() {
    ASTFunction function = new ASTFunction();
    function.setStrictness(false);
    AssignmentRule rule = new AssignmentRule();
    function.setParentSBMLObject(rule);
    boolean isSetParentSBMLObject = function.isSetParentSBMLObject();
    function.unsetParentSBMLObject();
    assertTrue(isSetParentSBMLObject && function.getParentSBMLObject() == null);
  }


}
