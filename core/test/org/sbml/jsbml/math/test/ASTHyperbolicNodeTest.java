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
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.math.ASTCnIntegerNode;
import org.sbml.jsbml.math.ASTFactory;
import org.sbml.jsbml.math.ASTHyperbolicNode;


/**
 * Test cases for {@link ASTHyperbolicNode}
 * 
 * @author Victor Kofia
 * @since 1.0
 */
public class ASTHyperbolicNodeTest {

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTHyperbolicNode#clone()}.
   */
  @Test
  public final void testClone() {
    ASTHyperbolicNode sinh = new ASTHyperbolicNode();
    ASTHyperbolicNode unknown = sinh.clone();
    assertTrue(sinh.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTHyperbolicNode#clone()}.
   */
  @Test
  public final void testCloneWithChildren() {
    ASTHyperbolicNode sinh = new ASTHyperbolicNode();
    sinh.addChild(new ASTCnIntegerNode(1));
    ASTHyperbolicNode unknown = sinh.clone();
    assertTrue(sinh.equals(unknown));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTHyperbolicNode#ASTHyperbolicNode(org.sbml.jsbml.math.ASTHyperbolicNode)}.
   */
  @Test
  public final void testCloneWithConstructor() {
    ASTHyperbolicNode sinh = new ASTHyperbolicNode();
    ASTHyperbolicNode unknown = new ASTHyperbolicNode(sinh);
    assertTrue(sinh.equals(unknown));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTHyperbolicNode#isAllowableType(org.sbml.jsbml.ASTNode.Type)}.
   */
  @Test
  public final void testIsAllowableType() {
    ASTHyperbolicNode sinh = new ASTHyperbolicNode();
    assertTrue(sinh.isAllowableType(Type.FUNCTION_ARCCOSH) && sinh.isAllowableType(Type.FUNCTION_ARCCOTH)
            && sinh.isAllowableType(Type.FUNCTION_ARCCSCH)
            && sinh.isAllowableType(Type.FUNCTION_ARCSECH) && sinh.isAllowableType(Type.FUNCTION_ARCSINH)
            && sinh.isAllowableType(Type.FUNCTION_ARCTANH) && sinh.isAllowableType(Type.FUNCTION_COSH)
            && sinh.isAllowableType(Type.FUNCTION_COTH) && sinh.isAllowableType(Type.FUNCTION_CSCH)
            && sinh.isAllowableType(Type.FUNCTION_SECH) && sinh.isAllowableType(Type.FUNCTION_SINH)
            && sinh.isAllowableType(Type.FUNCTION_TANH) && !sinh.isAllowableType(null));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTHyperbolicNode#toFormula()}.
   */
  @Test
  public final void testToFormulaArccosh() {
    ASTHyperbolicNode arccosh = new ASTHyperbolicNode(Type.FUNCTION_ARCCOSH);
    arccosh.addChild(new ASTCnIntegerNode(2));
    assertTrue(arccosh.toFormula().equals("acosh(2)"));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTHyperbolicNode#toFormula()}.
   */
  @Test
  public final void testToFormulaArccsch() {
    ASTHyperbolicNode arccsch = new ASTHyperbolicNode(Type.FUNCTION_ARCCSCH);
    arccsch.addChild(new ASTCnIntegerNode(2));
    assertTrue(arccsch.toFormula().equals("acsch(2)"));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTHyperbolicNode#toFormula()}.
   */
  @Test
  public final void testToFormulaArcsech() {
    ASTHyperbolicNode arcsech = new ASTHyperbolicNode(Type.FUNCTION_ARCSECH);
    arcsech.addChild(new ASTCnIntegerNode(2));
    assertTrue(arcsech.toFormula().equals("asech(2)"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTHyperbolicNode#toFormula()}.
   */
  @Test
  public final void testToFormulaArcsinh() {
    ASTHyperbolicNode arcsinh = new ASTHyperbolicNode(Type.FUNCTION_ARCSINH);
    arcsinh.addChild(new ASTCnIntegerNode(2));
    assertTrue(arcsinh.toFormula().equals("asinh(2)"));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTHyperbolicNode#toFormula()}.
   */
  @Test
  public final void testToFormulaArctanh() {
    ASTHyperbolicNode arctanh = new ASTHyperbolicNode(Type.FUNCTION_ARCTANH);
    arctanh.addChild(new ASTCnIntegerNode(2));
    assertTrue(arctanh.toFormula().equals("atanh(2)"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTHyperbolicNode#toFormula()}.
   */
  @Test
  public final void testToFormulaCosh() {
    ASTHyperbolicNode cosh = new ASTHyperbolicNode(Type.FUNCTION_COSH);
    cosh.addChild(new ASTCnIntegerNode(2));
    assertTrue(cosh.toFormula().equals("cosh(2)"));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTHyperbolicNode#toFormula()}.
   */
  @Test
  public final void testToFormulaCsch() {
    ASTHyperbolicNode csch = new ASTHyperbolicNode(Type.FUNCTION_CSCH);
    csch.addChild(new ASTCnIntegerNode(2));
    assertTrue(csch.toFormula().equals("csch(2)"));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTHyperbolicNode#toFormula()}.
   */
  @Test
  public final void testToFormulaSech() {
    ASTHyperbolicNode sech = new ASTHyperbolicNode(Type.FUNCTION_SECH);
    sech.addChild(new ASTCnIntegerNode(2));
    assertTrue(sech.toFormula().equals("sech(2)"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTHyperbolicNode#toFormula()}.
   */
  @Test
  public final void testToFormulaSinh() {
    ASTHyperbolicNode sinh = new ASTHyperbolicNode(Type.FUNCTION_SINH);
    sinh.addChild(new ASTCnIntegerNode(2));
    assertTrue(sinh.toFormula().equals("sinh(2)"));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTHyperbolicNode#toFormula()}.
   */
  @Test
  public final void testToFormulaTanh() {
    ASTHyperbolicNode tanh = new ASTHyperbolicNode(Type.FUNCTION_TANH);
    tanh.addChild(new ASTCnIntegerNode(2));
    assertTrue(tanh.toFormula().equals("tanh(2)"));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTHyperbolicNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeXArccosh() {
    ASTHyperbolicNode arccosh = new ASTHyperbolicNode(Type.FUNCTION_ARCCOSH);
    arccosh.addChild(new ASTCnIntegerNode(2));
    assertTrue(arccosh.toLaTeX().equals("\\mathrm{arccosh}\\left(2\\right)"));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTHyperbolicNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeXArccsch() {
    ASTHyperbolicNode arccsch = new ASTHyperbolicNode(Type.FUNCTION_ARCCSCH);
    arccsch.addChild(new ASTCnIntegerNode(2));
    assertTrue(arccsch.toLaTeX().equals("\\mathrm{arccsch}\\left(2\\right)"));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTHyperbolicNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeXArcsech() {
    ASTHyperbolicNode arcsech = new ASTHyperbolicNode(Type.FUNCTION_ARCSECH);
    arcsech.addChild(new ASTCnIntegerNode(2));
    assertTrue(arcsech.toLaTeX().equals("\\mathrm{arcsech}\\left(2\\right)"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTHyperbolicNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeXArcsinh() {
    ASTHyperbolicNode arcsinh = new ASTHyperbolicNode(Type.FUNCTION_ARCSINH);
    arcsinh.addChild(new ASTCnIntegerNode(2));
    assertTrue(arcsinh.toLaTeX().equals("\\mathrm{arcsinh}\\left(2\\right)"));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTHyperbolicNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeXArctanh() {
    ASTHyperbolicNode arctanh = new ASTHyperbolicNode(Type.FUNCTION_ARCTANH);
    arctanh.addChild(new ASTCnIntegerNode(2));
    assertTrue(arctanh.toLaTeX().equals("\\arctanh{\\left(2\\right)}"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTHyperbolicNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeXCosh() {
    ASTHyperbolicNode cosh = new ASTHyperbolicNode(Type.FUNCTION_COSH);
    cosh.addChild(new ASTCnIntegerNode(2));
    assertTrue(cosh.toLaTeX().equals("\\cosh{\\left(2\\right)}"));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTHyperbolicNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeXCsch() {
    ASTHyperbolicNode csch = new ASTHyperbolicNode(Type.FUNCTION_CSCH);
    csch.addChild(new ASTCnIntegerNode(2));
    assertTrue(csch.toLaTeX().equals("\\mathrm{csch}\\left(2\\right)"));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTHyperbolicNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeXSech() {
    ASTHyperbolicNode sech = new ASTHyperbolicNode(Type.FUNCTION_SECH);
    sech.addChild(new ASTCnIntegerNode(2));
    assertTrue(sech.toLaTeX().equals("\\mathrm{sech}\\left(2\\right)"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTHyperbolicNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeXSinh() {
    ASTHyperbolicNode sinh = new ASTHyperbolicNode(Type.FUNCTION_SINH);
    sinh.addChild(new ASTCnIntegerNode(2));
    assertTrue(sinh.toLaTeX().equals("\\sinh{\\left(2\\right)}"));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTHyperbolicNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeXTanh() {
    ASTHyperbolicNode tanh = new ASTHyperbolicNode(Type.FUNCTION_TANH);
    tanh.addChild(new ASTCnIntegerNode(2));
    assertTrue(tanh.toLaTeX().equals("\\tanh{\\left(2\\right)}"));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTHyperbolicNode#toMathML()}.
   */
  @Test
  public final void testToMathMLArccosh() {
    ASTHyperbolicNode arccosh = new ASTHyperbolicNode(Type.FUNCTION_ARCCOSH);
    arccosh.addChild(new ASTCnIntegerNode(2));
    assertTrue(arccosh.toMathML().equals(ASTFactory.parseMathML("arccosh.xml")));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTHyperbolicNode#toMathML()}.
   */
  @Test
  public final void testToMathMLArccsch() {
    ASTHyperbolicNode arccsch = new ASTHyperbolicNode(Type.FUNCTION_ARCCSCH);
    arccsch.addChild(new ASTCnIntegerNode(2));
    assertTrue(arccsch.toMathML().equals(ASTFactory.parseMathML("arccsch.xml")));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTHyperbolicNode#toMathML()}.
   */
  @Test
  public final void testToMathMLArcsech() {
    ASTHyperbolicNode arcsech = new ASTHyperbolicNode(Type.FUNCTION_ARCSECH);
    arcsech.addChild(new ASTCnIntegerNode(2));
    assertTrue(arcsech.toMathML().equals(ASTFactory.parseMathML("arcsech.xml")));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTHyperbolicNode#toMathML()}.
   */
  @Test
  public final void testToMathMLArcsinh() {
    ASTHyperbolicNode arcsinh = new ASTHyperbolicNode(Type.FUNCTION_ARCSINH);
    arcsinh.addChild(new ASTCnIntegerNode(2));
    assertTrue(arcsinh.toMathML().equals(ASTFactory.parseMathML("arcsinh.xml")));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTHyperbolicNode#toMathML()}.
   */
  @Test
  public final void testToMathMLArctanh() {
    ASTHyperbolicNode arctanh = new ASTHyperbolicNode(Type.FUNCTION_ARCTANH);
    arctanh.addChild(new ASTCnIntegerNode(2));
    assertTrue(arctanh.toMathML().equals(ASTFactory.parseMathML("arctanh.xml")));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTHyperbolicNode#toMathML()}.
   */
  @Test
  public final void testToMathMLCosh() {
    ASTHyperbolicNode cosh = new ASTHyperbolicNode(Type.FUNCTION_COSH);
    cosh.addChild(new ASTCnIntegerNode(2));
    assertTrue(cosh.toMathML().equals(ASTFactory.parseMathML("cosh.xml")));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTHyperbolicNode#toMathML()}.
   */
  @Test
  public final void testToMathMLCsch() {
    ASTHyperbolicNode csch = new ASTHyperbolicNode(Type.FUNCTION_CSCH);
    csch.addChild(new ASTCnIntegerNode(2));
    assertTrue(csch.toMathML().equals(ASTFactory.parseMathML("csch.xml")));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTHyperbolicNode#toMathML()}.
   */
  @Test
  public final void testToMathMLSech() {
    ASTHyperbolicNode sech = new ASTHyperbolicNode(Type.FUNCTION_SECH);
    sech.addChild(new ASTCnIntegerNode(2));
    assertTrue(sech.toMathML().equals(ASTFactory.parseMathML("sech.xml")));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTHyperbolicNode#toMathML()}.
   */
  @Test
  public final void testToMathMLSinh() {
    ASTHyperbolicNode sinh = new ASTHyperbolicNode(Type.FUNCTION_SINH);
    sinh.addChild(new ASTCnIntegerNode(2));
    assertTrue(sinh.toMathML().equals(ASTFactory.parseMathML("sinh.xml")));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTHyperbolicNode#toMathML()}.
   */
  @Test
  public final void testToMathMLTanh() {
    ASTHyperbolicNode tanh = new ASTHyperbolicNode(Type.FUNCTION_TANH);
    tanh.addChild(new ASTCnIntegerNode(2));
    assertTrue(tanh.toMathML().equals(ASTFactory.parseMathML("tanh.xml")));
  }

  
}
