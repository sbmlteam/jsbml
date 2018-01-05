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

import org.junit.Test;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.math.ASTCnIntegerNode;
import org.sbml.jsbml.math.ASTFactory;
import org.sbml.jsbml.math.ASTTrigonometricNode;

/**
 * Test cases for {@link ASTTrigonometricNode}
 * 
 * @author Victor Kofia
 * @since 1.0
 */
public class ASTTrigonometricNodeTest {

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTTrigonometricNode#clone()}.
   */
  @Test
  public final void testClone() {
    ASTTrigonometricNode sin = new ASTTrigonometricNode();
    ASTTrigonometricNode unknown = sin.clone();
    assertTrue(sin.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTTrigonometricNode#clone()}.
   */
  @Test
  public final void testCloneWithType() {
    ASTTrigonometricNode sin = new ASTTrigonometricNode(Type.FUNCTION_SIN);
    ASTTrigonometricNode unknown = sin.clone();
    assertTrue(sin.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTTrigonometricNode#clone()}.
   */
  @Test
  public final void testCloneWithConstructor() {
    ASTTrigonometricNode sin = new ASTTrigonometricNode(Type.FUNCTION_SIN);
    ASTTrigonometricNode unknown = new ASTTrigonometricNode(sin);
    assertTrue(sin.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTTrigonometricNode#isAllowableType(Type)}.
   */
  @Test
  public final void testIsAllowableType() {
    ASTTrigonometricNode sin = new ASTTrigonometricNode();
    assertTrue(sin.isAllowableType(Type.FUNCTION_ARCCOS) && sin.isAllowableType(Type.FUNCTION_ARCCOT)
      && sin.isAllowableType(Type.FUNCTION_ARCCSC) && sin.isAllowableType(Type.FUNCTION_ARCCSC)
      && sin.isAllowableType(Type.FUNCTION_ARCSEC) && sin.isAllowableType(Type.FUNCTION_ARCSIN)
      && sin.isAllowableType(Type.FUNCTION_ARCTAN) && sin.isAllowableType(Type.FUNCTION_COS)
      && sin.isAllowableType(Type.FUNCTION_COT) && sin.isAllowableType(Type.FUNCTION_CSC)
      && sin.isAllowableType(Type.FUNCTION_SEC) && sin.isAllowableType(Type.FUNCTION_SIN)
      && sin.isAllowableType(Type.FUNCTION_TAN) && !sin.isAllowableType(null));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTTrigonometricNode#toFormula()}.
   */
  @Test
  public final void testToFormulaSin() {
    ASTTrigonometricNode sin = new ASTTrigonometricNode(Type.FUNCTION_SIN);
    sin.addChild(new ASTCnIntegerNode(2));
    assertTrue(sin.toFormula().equals("sin(2)"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTTrigonometricNode#toFormula()}.
   */
  @Test
  public final void testToFormulaCos() {
    ASTTrigonometricNode cos = new ASTTrigonometricNode(Type.FUNCTION_COS);
    cos.addChild(new ASTCnIntegerNode(2));
    assertTrue(cos.toFormula().equals("cos(2)"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTTrigonometricNode#toFormula()}.
   */
  @Test
  public final void testToFormulaTan() {
    ASTTrigonometricNode tan = new ASTTrigonometricNode(Type.FUNCTION_TAN);
    tan.addChild(new ASTCnIntegerNode(2));
    assertTrue(tan.toFormula().equals("tan(2)"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTTrigonometricNode#toFormula()}.
   */
  @Test
  public final void testToFormulaSec() {
    ASTTrigonometricNode sec = new ASTTrigonometricNode(Type.FUNCTION_SEC);
    sec.addChild(new ASTCnIntegerNode(2));
    assertTrue(sec.toFormula().equals("sec(2)"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTTrigonometricNode#toFormula()}.
   */
  @Test
  public final void testToFormulaCsc() {
    ASTTrigonometricNode csc = new ASTTrigonometricNode(Type.FUNCTION_CSC);
    csc.addChild(new ASTCnIntegerNode(2));
    assertTrue(csc.toFormula().equals("csc(2)"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTTrigonometricNode#toFormula()}.
   */
  @Test
  public final void testToFormulaArcsin() {
    ASTTrigonometricNode arcsin = new ASTTrigonometricNode(Type.FUNCTION_ARCSIN);
    arcsin.addChild(new ASTCnIntegerNode(2));
    assertTrue(arcsin.toFormula().equals("asin(2)"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTTrigonometricNode#toFormula()}.
   */
  @Test
  public final void testToFormulaArccos() {
    ASTTrigonometricNode arccos = new ASTTrigonometricNode(Type.FUNCTION_ARCCOS);
    arccos.addChild(new ASTCnIntegerNode(2));
    assertTrue(arccos.toFormula().equals("acos(2)"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTTrigonometricNode#toFormula()}.
   */
  @Test
  public final void testToFormulaArctan() {
    ASTTrigonometricNode arctan = new ASTTrigonometricNode(Type.FUNCTION_ARCTAN);
    arctan.addChild(new ASTCnIntegerNode(2));
    assertTrue(arctan.toFormula().equals("atan(2)"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTTrigonometricNode#toFormula()}.
   */
  @Test
  public final void testToFormulaArcsec() {
    ASTTrigonometricNode arcsec = new ASTTrigonometricNode(Type.FUNCTION_ARCSEC);
    arcsec.addChild(new ASTCnIntegerNode(2));
    assertTrue(arcsec.toFormula().equals("asec(2)"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTTrigonometricNode#toFormula()}.
   */
  @Test
  public final void testToFormulaArccsc() {
    ASTTrigonometricNode arccsc = new ASTTrigonometricNode(Type.FUNCTION_ARCCSC);
    arccsc.addChild(new ASTCnIntegerNode(2));
    assertTrue(arccsc.toFormula().equals("acsc(2)"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTTrigonometricNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeXArccos() {
    ASTTrigonometricNode arccos = new ASTTrigonometricNode(Type.FUNCTION_ARCCOS);
    arccos.addChild(new ASTCnIntegerNode(2));
    assertTrue(arccos.toLaTeX().equals("\\arccos{2}"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTTrigonometricNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeXArccsc() {
    ASTTrigonometricNode arccsc = new ASTTrigonometricNode(Type.FUNCTION_ARCCSC);
    arccsc.addChild(new ASTCnIntegerNode(2));
    assertTrue(arccsc.toLaTeX().equals("\\mathrm{arccsc}\\left(2\\right)"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTTrigonometricNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeXArcsec() {
    ASTTrigonometricNode arcsec = new ASTTrigonometricNode(Type.FUNCTION_ARCSEC);
    arcsec.addChild(new ASTCnIntegerNode(2));
    assertTrue(arcsec.toLaTeX().equals("\\mathrm{arcsec}\\left(2\\right)"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTTrigonometricNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeXArcsin() {
    ASTTrigonometricNode arcsin = new ASTTrigonometricNode(Type.FUNCTION_ARCSIN);
    arcsin.addChild(new ASTCnIntegerNode(2));
    assertTrue(arcsin.toLaTeX().equals("\\mathrm{arcsin}\\left(2\\right)"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTTrigonometricNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeXArctan() {
    ASTTrigonometricNode arctan = new ASTTrigonometricNode(Type.FUNCTION_ARCTAN);
    arctan.addChild(new ASTCnIntegerNode(2));
    assertTrue(arctan.toLaTeX().equals("\\mathrm{arctan}\\left(2\\right)"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTTrigonometricNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeXCos() {
    ASTTrigonometricNode cos = new ASTTrigonometricNode(Type.FUNCTION_COS);
    cos.addChild(new ASTCnIntegerNode(2));
    assertTrue(cos.toLaTeX().equals("\\cos{\\left(2\\right)}"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTTrigonometricNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeXCsc() {
    ASTTrigonometricNode csc = new ASTTrigonometricNode(Type.FUNCTION_CSC);
    csc.addChild(new ASTCnIntegerNode(2));
    assertTrue(csc.toLaTeX().equals("\\csc{\\left(2\\right)}"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTTrigonometricNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeXSec() {
    ASTTrigonometricNode sec = new ASTTrigonometricNode(Type.FUNCTION_SEC);
    sec.addChild(new ASTCnIntegerNode(2));
    assertTrue(sec.toLaTeX().equals("\\sec{\\left(2\\right)}"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTTrigonometricNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeXSin() {
    ASTTrigonometricNode sin = new ASTTrigonometricNode(Type.FUNCTION_SIN);
    sin.addChild(new ASTCnIntegerNode(2));
    assertTrue(sin.toLaTeX().equals("\\sin{\\left(2\\right)}"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTTrigonometricNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeXTan() {
    ASTTrigonometricNode tan = new ASTTrigonometricNode(Type.FUNCTION_TAN);
    tan.addChild(new ASTCnIntegerNode(2));
    assertTrue(tan.toLaTeX().equals("\\tan{\\left(2\\right)}"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTTrigonometricNode#toMathML()}.
   */
  @Test
  public final void testToMathMLArccos() {
    ASTTrigonometricNode arccos = new ASTTrigonometricNode(Type.FUNCTION_ARCCOS);
    arccos.addChild(new ASTCnIntegerNode(2));
    assertTrue(arccos.toMathML().equals(ASTFactory.parseMathML("arccos.xml")));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTTrigonometricNode#toMathML()}.
   */
  @Test
  public final void testToMathMLArccsc() {
    ASTTrigonometricNode arccsc = new ASTTrigonometricNode(Type.FUNCTION_ARCCSC);
    arccsc.addChild(new ASTCnIntegerNode(2));
    assertTrue(arccsc.toMathML().equals(ASTFactory.parseMathML("arccsc.xml")));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTTrigonometricNode#toMathML()}.
   */
  @Test
  public final void testToMathMLArcsec() {
    ASTTrigonometricNode arcsec = new ASTTrigonometricNode(Type.FUNCTION_ARCSEC);
    arcsec.addChild(new ASTCnIntegerNode(2));
    assertTrue(arcsec.toMathML().equals(ASTFactory.parseMathML("arcsec.xml")));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTTrigonometricNode#toMathML()}.
   */
  @Test
  public final void testToMathMLArcsin() {
    ASTTrigonometricNode arcsin = new ASTTrigonometricNode(Type.FUNCTION_ARCSIN);
    arcsin.addChild(new ASTCnIntegerNode(2));
    assertTrue(arcsin.toMathML().equals(ASTFactory.parseMathML("arcsin.xml")));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTTrigonometricNode#toMathML()}.
   */
  @Test
  public final void testToMathMLArctan() {
    ASTTrigonometricNode arctan = new ASTTrigonometricNode(Type.FUNCTION_ARCTAN);
    arctan.addChild(new ASTCnIntegerNode(2));
    assertTrue(arctan.toMathML().equals(ASTFactory.parseMathML("arctan.xml")));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTTrigonometricNode#toMathML()}.
   */
  @Test
  public final void testToMathMLCos() {
    ASTTrigonometricNode cos = new ASTTrigonometricNode(Type.FUNCTION_COS);
    cos.addChild(new ASTCnIntegerNode(2));
    assertTrue(cos.toMathML().equals(ASTFactory.parseMathML("cos.xml")));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTTrigonometricNode#toMathML()}.
   */
  @Test
  public final void testToMathMLCsc() {
    ASTTrigonometricNode csc = new ASTTrigonometricNode(Type.FUNCTION_CSC);
    csc.addChild(new ASTCnIntegerNode(2));
    assertTrue(csc.toMathML().equals(ASTFactory.parseMathML("csc.xml")));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTTrigonometricNode#toMathML()}.
   */
  @Test
  public final void testToMathMLSec() {
    ASTTrigonometricNode sec = new ASTTrigonometricNode(Type.FUNCTION_SEC);
    sec.addChild(new ASTCnIntegerNode(2));
    assertTrue(sec.toMathML().equals(ASTFactory.parseMathML("sec.xml")));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTTrigonometricNode#toMathML()}.
   */
  @Test
  public final void testToMathMLSin() {
    ASTTrigonometricNode sin = new ASTTrigonometricNode(Type.FUNCTION_SIN);
    sin.addChild(new ASTCnIntegerNode(2));
    assertTrue(sin.toMathML().equals(ASTFactory.parseMathML("sin.xml")));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTTrigonometricNode#toMathML()}.
   */
  @Test
  public final void testToMathMLTan() {
    ASTTrigonometricNode tan = new ASTTrigonometricNode(Type.FUNCTION_TAN);
    tan.addChild(new ASTCnIntegerNode(2));
    assertTrue(tan.toMathML().equals(ASTFactory.parseMathML("tan.xml")));
  }

}
