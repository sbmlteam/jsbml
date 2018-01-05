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

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.Constraint;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.math.ASTCiFunctionNode;
import org.sbml.jsbml.math.ASTCiNumberNode;
import org.sbml.jsbml.math.ASTCnIntegerNode;
import org.sbml.jsbml.math.ASTFactory;
import org.sbml.jsbml.math.ASTLambdaFunctionNode;
import org.sbml.jsbml.math.ASTPowerNode;
import org.sbml.jsbml.math.ASTQualifierNode;

/**
 * Test cases for {@link ASTCiFunctionNode}
 * 
 * @author Victor Kofia
 * @since 1.0
 */
public class ASTCiFunctionNodeTest {

  /**
   * 
   */
  @Rule
  public ExpectedException exception = ExpectedException.none();

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCiFunctionNode#clone()}.
   */
  @Test
  public final void testClone() {
    ASTCiFunctionNode ci = new ASTCiFunctionNode();
    ASTCiFunctionNode unknown = ci.clone();
    assertTrue(ci.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCiFunctionNode#clone()}.
   */
  @Test
  public final void testCloneWithChildren() {
    ASTCiFunctionNode ci = new ASTCiFunctionNode();
    ci.addChild(new ASTCnIntegerNode(1));
    ci.addChild(new ASTCnIntegerNode(10));
    ASTCiFunctionNode unknown = ci.clone();
    assertTrue(ci.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCiFunctionNode#ASTCiFunctionNode(org.sbml.jsbml.math.ASTCiFunctionNode)}.
   */
  @Test
  public final void testCloneWithConstructor() {
    ASTCiFunctionNode ci = new ASTCiFunctionNode();
    ASTCiFunctionNode unknown = new ASTCiFunctionNode(ci);
    assertTrue(ci.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCiFunctionNode#getDefinitionURL()}.
   */
  @Test
  public final void testGetDefinitionURLExists() {
    ASTCiFunctionNode ci = new ASTCiFunctionNode();
    String url = "http://some-url.com";
    ci.setDefinitionURL(url);
    assertTrue(ci.getDefinitionURL().equals(url));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCiFunctionNode#getDefinitionURL()}.
   */
  @Test
  public final void testGetDefinitionURLNonExistent() {
    ASTCiFunctionNode ci = new ASTCiFunctionNode();
    exception.expect(PropertyUndefinedError.class);
    ci.getDefinitionURL();
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCiFunctionNode#getDefinitionURL()}.
   */
  @Test
  public final void testGetDefinitionURLNonExistentNonStrict() {
    ASTCiFunctionNode ci = new ASTCiFunctionNode();
    ci.setStrictness(false);
    assertTrue(ci.getDefinitionURL().isEmpty());
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCiFunctionNode#getRefId()}.
   */
  @Test
  public final void testGetRefIdExists() {
    ASTCiFunctionNode ci = new ASTCiFunctionNode();
    String reference = "reference";
    ci.setRefId(reference);
    assertTrue(ci.getRefId().equals(reference));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCiFunctionNode#getRefId()}.
   */
  @Test
  public final void testGetRefIdNonExistent() {
    ASTCiFunctionNode ci = new ASTCiFunctionNode();
    exception.expect(PropertyUndefinedError.class);
    ci.getRefId();
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCiFunctionNode#getRefId()}.
   */
  @Test
  public final void testGetRefIdNonExistentNonStrict() {
    ASTCiFunctionNode ci = new ASTCiFunctionNode();
    ci.setStrictness(false);
    assertTrue(ci.getRefId().isEmpty());
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCiFunctionNode#getType()}.
   */
  @Test
  public final void testGetType() {
    ASTCiFunctionNode ci = new ASTCiFunctionNode();
    assertTrue(ci.getType() == Type.FUNCTION);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCiFunctionNode#isSetType()}.
   */
  @Test
  public final void testIsSetType() {
    ASTCiFunctionNode ci = new ASTCiFunctionNode();
    assertTrue(ci.isSetType());
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCiFunctionNode#setType(String)}.
   */
  @Test
  public final void testSetTypeAllowed() {
    ASTCiFunctionNode ci = new ASTCiFunctionNode();
    ci.setType(Type.FUNCTION);
    assertTrue(ci.getType() == Type.FUNCTION);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCiFunctionNode#setType(String)}.
   */
  @Test
  public final void testSetTypeNotAllowed() {
    ASTCiFunctionNode ci = new ASTCiFunctionNode();
    exception.expect(IllegalArgumentException.class);
    ci.setType(Type.UNKNOWN);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCiFunctionNode#toFormula()}.
   */
  /*  @Test
  public final void testToFormula() {
    // TODO: This test case might not be consistent with how real SBML elements
    // are manipulated. Verify.
    ASTCiFunctionNode ci = new ASTCiFunctionNode();

    Model model = new Model(3, 1);

    ASTLambdaFunctionNode lambda = new ASTLambdaFunctionNode();
    ASTQualifierNode bvar = new ASTQualifierNode(Type.QUALIFIER_BVAR);
    ASTCiNumberNode variable = new ASTCiNumberNode();
    variable.setRefId("x");
    bvar.addChild(variable);
    lambda.addChild(bvar);
    ASTPowerNode pow = ASTFactory.pow(variable, 10);
    lambda.addChild(pow);

    FunctionDefinition function = new FunctionDefinition(3, 1);
    function.setId("pow3");
    function.setMath(new ASTNode(lambda));
    model.addFunctionDefinition(function);

    Constraint constraint = new Constraint();
    model.addConstraint(constraint);
    ci.setRefId("pow3");
    ci.setParentSBMLObject(constraint);

    assertTrue(ci.toFormula().equals("pow3()"));
  }
*/
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCiFunctionNode#toFormula()}.
   */
  @Test
  public final void testToFormulaNoVariable() {
    ASTCiFunctionNode ci = new ASTCiFunctionNode();
    exception.expect(SBMLException.class);
    ci.toFormula();
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCiFunctionNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeXNoVariable() {
    ASTCiFunctionNode ci = new ASTCiFunctionNode();
    exception.expect(SBMLException.class);
    ci.toLaTeX();
  }

}
