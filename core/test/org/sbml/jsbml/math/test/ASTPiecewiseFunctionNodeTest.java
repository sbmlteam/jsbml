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

import org.junit.Test;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.math.ASTCiNumberNode;
import org.sbml.jsbml.math.ASTCnIntegerNode;
import org.sbml.jsbml.math.ASTFactory;
import org.sbml.jsbml.math.ASTPiecewiseFunctionNode;
import org.sbml.jsbml.math.ASTPlusNode;
import org.sbml.jsbml.math.ASTQualifierNode;
import org.sbml.jsbml.math.ASTRelationalOperatorNode;


/**
 * Test cases for {@link ASTPiecewiseFunctionNode}
 * 
 * @author Victor Kofia
 * @since 1.0
 */
public class ASTPiecewiseFunctionNodeTest {

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTPiecewiseFunctionNode#clone()}.
   */
  @Test
  public final void testClone() {
    ASTPiecewiseFunctionNode piecewise = new ASTPiecewiseFunctionNode();
    ASTPiecewiseFunctionNode unknown = piecewise.clone();
    assertTrue(piecewise.equals(unknown));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTPiecewiseFunctionNode#clone()}.
   */
  @Test
  public final void testCloneWithChildren() {
    ASTPiecewiseFunctionNode piecewise = new ASTPiecewiseFunctionNode();
    ASTQualifierNode piece = new ASTQualifierNode(Type.CONSTRUCTOR_PIECE);
    piece.addChild(new ASTCnIntegerNode(0));
    ASTRelationalOperatorNode geq = new ASTRelationalOperatorNode(Type.RELATIONAL_GEQ);
    ASTCiNumberNode x = new ASTCiNumberNode();
    x.setRefId("x");
    geq.addChild(x);
    geq.addChild(new ASTCnIntegerNode(10));
    piece.addChild(geq);
    piecewise.addChild(piece);
    ASTQualifierNode otherwise = new ASTQualifierNode(Type.CONSTRUCTOR_OTHERWISE);
    otherwise.addChild(new ASTCnIntegerNode(1));
    piecewise.addChild(otherwise);
    ASTPiecewiseFunctionNode unknown = piecewise.clone();
    assertTrue(piecewise.equals(unknown));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTPiecewiseFunctionNode#ASTPiecewiseFunctionNode(org.sbml.jsbml.math.ASTPiecewiseFunctionNode)}.
   */
  @Test
  public final void testCloneWithConstructor() {
    ASTPiecewiseFunctionNode piecewise = new ASTPiecewiseFunctionNode();
    ASTPiecewiseFunctionNode unknown = new ASTPiecewiseFunctionNode(piecewise);
    assertTrue(piecewise.equals(unknown));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTPiecewiseFunctionNode#getPieceCount()}.
   */
  @Test
  public final void testGetPieceCount() {
    ASTPiecewiseFunctionNode piecewise = new ASTPiecewiseFunctionNode();
    assertTrue(piecewise.getPieceCount() == 0);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTPiecewiseFunctionNode#getPieceCount()}.
   */
  @Test
  public final void testGetPieceCountWithChildren() {
    ASTPiecewiseFunctionNode piecewise = new ASTPiecewiseFunctionNode();
    ASTQualifierNode piece = new ASTQualifierNode(Type.CONSTRUCTOR_PIECE);
    piece.addChild(new ASTCnIntegerNode(0));
    ASTRelationalOperatorNode geq = new ASTRelationalOperatorNode(Type.RELATIONAL_GEQ);
    ASTCiNumberNode x = new ASTCiNumberNode();
    x.setRefId("x");
    geq.addChild(x);
    geq.addChild(new ASTCnIntegerNode(10));
    piece.addChild(geq);
    piecewise.addChild(piece);
    ASTQualifierNode otherwise = new ASTQualifierNode(Type.CONSTRUCTOR_OTHERWISE);
    otherwise.addChild(new ASTCnIntegerNode(1));
    piecewise.addChild(otherwise);
    assertTrue(piecewise.getPieceCount() == 1);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTPiecewiseFunctionNode#hasOtherwise()}.
   */
  @Test
  public final void testHasOtherwiseFalse() {
    ASTPiecewiseFunctionNode piecewise = new ASTPiecewiseFunctionNode();
    assertFalse(piecewise.hasOtherwise());
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTPiecewiseFunctionNode#hasOtherwise()}.
   */
  @Test
  public final void testHasOtherwiseTrue() {
    ASTPiecewiseFunctionNode piecewise = new ASTPiecewiseFunctionNode();
    ASTQualifierNode piece = new ASTQualifierNode(Type.CONSTRUCTOR_PIECE);
    piece.addChild(new ASTCnIntegerNode(0));
    ASTRelationalOperatorNode geq = new ASTRelationalOperatorNode(Type.RELATIONAL_GEQ);
    ASTCiNumberNode x = new ASTCiNumberNode();
    x.setRefId("x");
    geq.addChild(x);
    geq.addChild(new ASTCnIntegerNode(10));
    piece.addChild(geq);
    piecewise.addChild(piece);
    ASTQualifierNode otherwise = new ASTQualifierNode(Type.CONSTRUCTOR_OTHERWISE);
    otherwise.addChild(new ASTCnIntegerNode(1));
    piecewise.addChild(otherwise);
    assertTrue(piecewise.hasOtherwise());
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTPiecewiseFunctionNode#toFormula()}.
   */
  @Test
  public final void testToFormula() {
    ASTPiecewiseFunctionNode piecewise = new ASTPiecewiseFunctionNode();
    ASTQualifierNode piece = new ASTQualifierNode(Type.CONSTRUCTOR_PIECE);
    piece.addChild(new ASTCnIntegerNode(0));
    ASTRelationalOperatorNode geq = new ASTRelationalOperatorNode(Type.RELATIONAL_GEQ);
    ASTCiNumberNode x = new ASTCiNumberNode();
    x.setRefId("x");
    geq.addChild(x);
    geq.addChild(new ASTCnIntegerNode(10));
    piece.addChild(geq);
    piecewise.addChild(piece);
    ASTQualifierNode otherwise = new ASTQualifierNode(Type.CONSTRUCTOR_OTHERWISE);
    otherwise.addChild(new ASTCnIntegerNode(1));
    piecewise.addChild(otherwise);
    assertTrue(piecewise.toFormula().equals("piecewise(0, 1)"));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTPiecewiseFunctionNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeX() {
    ASTPiecewiseFunctionNode piecewise = new ASTPiecewiseFunctionNode();
    ASTQualifierNode piece = new ASTQualifierNode(Type.CONSTRUCTOR_PIECE);
    piece.addChild(new ASTCnIntegerNode(0));
    ASTRelationalOperatorNode geq = new ASTRelationalOperatorNode(Type.RELATIONAL_GEQ);
    ASTCiNumberNode x = new ASTCiNumberNode();
    x.setRefId("x");
    geq.addChild(x);
    geq.addChild(new ASTCnIntegerNode(10));
    piece.addChild(geq);
    piecewise.addChild(piece);
    ASTQualifierNode otherwise = new ASTQualifierNode(Type.CONSTRUCTOR_OTHERWISE);
    otherwise.addChild(new ASTCnIntegerNode(1));
    piecewise.addChild(otherwise);
    assertTrue(piecewise.toLaTeX().equals("\\begin{dcases}\n"
                                        + "0 & \\text{if\\ } 1\\end{dcases}"));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTPiecewiseFunctionNode#toMathML()}.
   */
  @Test
  public final void testToMathML() {
    ASTPiecewiseFunctionNode piecewise = new ASTPiecewiseFunctionNode();
    ASTQualifierNode piece = new ASTQualifierNode(Type.CONSTRUCTOR_PIECE);
    piece.addChild(new ASTCnIntegerNode(0));
    ASTRelationalOperatorNode geq = new ASTRelationalOperatorNode(Type.RELATIONAL_GEQ);
    ASTCiNumberNode x = new ASTCiNumberNode();
    x.setRefId("x");
    geq.addChild(x);
    geq.addChild(new ASTCnIntegerNode(10));
    piece.addChild(geq);
    piecewise.addChild(piece);
    ASTQualifierNode otherwise = new ASTQualifierNode(Type.CONSTRUCTOR_OTHERWISE);
    ASTPlusNode plus = new ASTPlusNode(new ASTCnIntegerNode(2), new ASTCnIntegerNode(28));
    otherwise.addChild(plus);
    piecewise.addChild(otherwise);
    assertTrue(piecewise.toMathML().equals(ASTFactory.parseMathML("piecewise.xml")));
  }
  
}
