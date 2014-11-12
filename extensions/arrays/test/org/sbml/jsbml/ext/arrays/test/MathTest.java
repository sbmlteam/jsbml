/*
 * $Id:  MathTest.java 10:51:36 AM lwatanabe $
 * $URL: MathTest.java $
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
 * 6. The University of Utah, Salt Lake City, UT, USA
 *
 * This library is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by 
 * the Free Software Foundation. A copy of the license agreement is provided 
 * in the file named "LICENSE.txt" included with this software distribution 
 * and also available online as <http://sbml.org/Software/JSBML/License>. 
 * ---------------------------------------------------------------------------- 
 */
package org.sbml.jsbml.ext.arrays.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;

import org.junit.Test;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.math.parser.FormulaParserLL3;
import org.sbml.jsbml.math.parser.FormulaParser;
import org.sbml.jsbml.math.parser.ParseException;


/**
 * @author Leandro Watanabe
 * @version $Rev$
 * @since 1.0
 * @date May 27, 2014
 */
public class MathTest {
  @Test
  public void testASTNodeVector() {

    ASTNode n = null;
    try {
      n = ASTNode.parseFormula("vector(0, 1,2,3,4)");
      assertTrue(n.getType() == ASTNode.Type.VECTOR);
      assertTrue(n.getChild(0).equals(new ASTNode(0)));
      assertTrue(n.getChild(1).equals(new ASTNode(1)));
      assertTrue(n.getChild(2).equals(new ASTNode(2)));
      assertTrue(n.getChild(3).equals(new ASTNode(3)));
      assertFalse(n.getChild(4).equals(new ASTNode(5)));
    } catch (ParseException e) {
      assertTrue(false);
    }
    System.out.println(n.toMathML());


  }

  @Test
  public void testEmptyInfixVector() {

    ASTNode n = null;
    String formula = "{ }";
    FormulaParser parser = new FormulaParser(new StringReader(formula));
    try {
      n = parser.parse();
      assertTrue(n.getType() == ASTNode.Type.VECTOR);
      assertTrue(n.getListOfNodes().size() == 0);
    } catch (ParseException e) {
      e.printStackTrace();
      assertTrue(false);
    }
  }
  
  @Test
  public void testInfixVector() {

    ASTNode n = null;
    String formula = "{1, 2}";
    FormulaParser parser = new FormulaParser(new StringReader(formula));
    try {
      n = parser.parse();
      assertTrue(n.getType() == ASTNode.Type.VECTOR);
      assertTrue(n.getListOfNodes().size() == 2);
      assertTrue(n.getListOfNodes().contains(new ASTNode(1)));
      assertTrue(n.getListOfNodes().contains(new ASTNode(2)));
    } catch (ParseException e) {
      e.printStackTrace();
      assertTrue(false);
    }
  }

  @Test
  public void testInfixNestedVector() {

    ASTNode n = null;
    String formula = "{{1, 2, 3}, {2, 1, 0}}";
    FormulaParser parser = new FormulaParser(new StringReader(formula));
    try {
      n = parser.parse();
      assertTrue(n.getType() == ASTNode.Type.VECTOR);
      assertTrue(n.getChild(0).getType() == ASTNode.Type.VECTOR);
      assertTrue(n.getListOfNodes().size() == 2);
      assertTrue(n.getListOfNodes().get(0).getListOfNodes().size() == 3);
      assertTrue(n.getListOfNodes().get(0).getListOfNodes().contains(new ASTNode(3))
        && !n.getListOfNodes().get(0).getListOfNodes().contains(new ASTNode(0)));
      assertTrue(n.getListOfNodes().get(1).getListOfNodes().contains(new ASTNode(0))
        && !n.getListOfNodes().get(1).getListOfNodes().contains(new ASTNode(3)));
    } catch (ParseException e) {
      e.printStackTrace();
      assertTrue(false);
    }
  }
  
  @Test
  public void testInfixSelector() {

    ASTNode n = null;
    String formula = "y[0]";
    FormulaParser parser = new FormulaParser(new StringReader(formula));
    try {
      n = parser.parse();
      assertTrue(n.getType() == ASTNode.Type.FUNCTION_SELECTOR);
      assertTrue(n.getChildCount() == 2);
      assertTrue(n.getChild(0).equals(new ASTNode("y")));
      assertTrue(n.getChild(1).equals(new ASTNode(0)));
    } catch (ParseException e) {
      e.printStackTrace();
      assertTrue(false);
    }
  }
  
  @Test
  public void testNestedInfixSelector() {

    ASTNode n = null;
    String formula = "y[0][2]";
    FormulaParser parser = new FormulaParser(new StringReader(formula));
    try {
      n = parser.parse();
      assertTrue(n.getType() == ASTNode.Type.FUNCTION_SELECTOR);
      assertTrue(n.getChildCount() == 3);
      assertTrue(n.getChild(0).equals(new ASTNode("y")));
      assertTrue(n.getChild(1).equals(new ASTNode(0)));
      assertTrue(n.getChild(2).equals(new ASTNode(2)));
    } catch (ParseException e) {
      e.printStackTrace();
      assertTrue(false);
    }
  }
  
  @Test
  public void testInfixSelectorWithoutMath() {

    ASTNode n = null;
    String formula = "y[]";
    FormulaParser parser = new FormulaParser(new StringReader(formula));
    try {
      n = parser.parse();
      assertTrue(false);
    } catch (ParseException e) {
      assertTrue(true);
    }
  }
  
  @Test
  public void testInfixSelectorWithoutArrayedObj() {

    ASTNode n = null;
    String formula = "[i]";
    FormulaParser parser = new FormulaParser(new StringReader(formula));
    try {
      n = parser.parse();
      assertTrue(false);
    } catch (ParseException e) {
      assertTrue(true);
    }
  }

  @Test
  public void testEmptyInfixVectorLL3() {

    ASTNode n = null;
    String formula = "{ }";
    FormulaParserLL3 parser = new FormulaParserLL3(new StringReader(formula));
    try {
      n = parser.parse();
      assertTrue(n.getType() == ASTNode.Type.VECTOR);
      assertTrue(n.getChildCount() == 0);
    } catch (ParseException e) {
      e.printStackTrace();
      assertTrue(false);
    }
  }
  
  @Test
  public void testInfixVectorLL3() {

    ASTNode n = null;
    String formula = "{1, 2}";
    FormulaParserLL3 parser = new FormulaParserLL3(new StringReader(formula));
    try {
      n = parser.parse();
      assertTrue(n.getType() == ASTNode.Type.VECTOR);
      assertTrue(n.getChildCount() == 2);
      assertTrue(n.getChild(0).equals(new ASTNode(1)));
      assertTrue(n.getChild(1).equals(new ASTNode(2)));
    } catch (ParseException e) {
      e.printStackTrace();
      assertTrue(false);
    }
  }

  @Test
  public void testInfixNestedVectorLL3() {

    ASTNode n = null;
    String formula = "{{1, 2}, {2, 1}}";
    FormulaParserLL3 parser = new FormulaParserLL3(new StringReader(formula));
    try {
      n = parser.parse();
      assertTrue(n.getType() == ASTNode.Type.VECTOR);
      assertTrue(n.getChild(0).getType() == ASTNode.Type.VECTOR);
      assertTrue(n.getChildCount() == 2);
      assertTrue(n.getChild(0).equals(ASTNode.parseFormula("vector(1,2)")));
      assertTrue(n.getChild(1).equals(ASTNode.parseFormula("vector(2,1)")));
    } catch (ParseException e) {
      e.printStackTrace();
      assertTrue(false);
    }
  }
  
  @Test
  public void testInfixSelectorLL3() {

    ASTNode n = null;
    String formula = "y[0]";
    FormulaParserLL3 parser = new FormulaParserLL3(new StringReader(formula));
    try {
      n = parser.parse();
      assertTrue(n.getType() == ASTNode.Type.FUNCTION_SELECTOR);
      assertTrue(n.equals(ASTNode.parseFormula("selector(y,0)")));
    } catch (ParseException e) {
      e.printStackTrace();
      assertTrue(false);
    }
  }
  
  @Test
  public void testNestedInfixSelectorLL3() {

    ASTNode n = null;
    String formula = "y[0][2]";
    FormulaParserLL3 parser = new FormulaParserLL3(new StringReader(formula));
    try {
      n = parser.parse();
      assertTrue(n.getType() == ASTNode.Type.FUNCTION_SELECTOR);
      assertTrue(n.equals(ASTNode.parseFormula("selector(y,0,2)")));
    } catch (ParseException e) {
      e.printStackTrace();
      assertTrue(false);
    }
  }
  
  @Test
  public void testInfixSelectorWithoutMathLL3() {

    ASTNode n = null;
    String formula = "y[]";
    FormulaParserLL3 parser = new FormulaParserLL3(new StringReader(formula));
    try {
      n = parser.parse();
      assertTrue(false);
    } catch (ParseException e) {
      assertTrue(true);
    }
  }
  
  @Test
  public void testInfixSelectorWithoutArrayedObjLL3() {

    ASTNode n = null;
    String formula = "[i]";
    FormulaParserLL3 parser = new FormulaParserLL3(new StringReader(formula));
    try {
      n = parser.parse();
      assertTrue(false);
    } catch (ParseException e) {
      assertTrue(true);
    }
  }
  
  @Test
  public void testInfixSelectorWithFunction() {

    ASTNode n = null;
    String formula = "y[i+(3*6)-sin(x)]";
    FormulaParser parser = new FormulaParser(new StringReader(formula));
    try {
      n = parser.parse();
      assertTrue(n.getType() == ASTNode.Type.FUNCTION_SELECTOR);
      assertTrue(n.equals(ASTNode.parseFormula("selector(y,i+(3*6)-sin(x))")));
    } catch (ParseException e) {
      e.printStackTrace();
      assertTrue(false);
    }
  }
  
  @Test
  public void testInfixSelectorWithFunctionLL3() {

    ASTNode n = null;
    String formula = "y[i+(3*6)-sin(x)]";
    FormulaParserLL3 parser = new FormulaParserLL3(new StringReader(formula));
    try {
      n = parser.parse();
      assertTrue(n.getType() == ASTNode.Type.FUNCTION_SELECTOR);
      assertTrue(n.equals(ASTNode.parseFormula("selector(y,i+(3*6)-sin(x))")));
    } catch (ParseException e) {
      e.printStackTrace();
      assertTrue(false);
    }
  }
  
  @Test
  public void testInfixVectorWithFunction() {

    ASTNode n = null;
    String formula = "{sin(x), 96+21, 6^2, boo}";
    FormulaParser parser = new FormulaParser(new StringReader(formula));
    try {
      n = parser.parse();
      assertTrue(n.getType() == ASTNode.Type.VECTOR);
      assertTrue(n.equals(ASTNode.parseFormula("vector(sin(x), 96+21, 6^2, boo)")));
    } catch (ParseException e) {
      e.printStackTrace();
      assertTrue(false);
    }
  }
  
  @Test
  public void testInfixVectorWithFunctionLL3() {

    ASTNode n = null;
    String formula = "{sin(x), 96+21, 6^2, boo}";
    FormulaParserLL3 parser = new FormulaParserLL3(new StringReader(formula));
    try {
      n = parser.parse();
      assertTrue(n.getType() == ASTNode.Type.VECTOR);
      ASTNode node = ASTNode.parseFormula("vector(sin(x), 96+21, 6^2, boo)");
      assertTrue(n.equals(node));
    } catch (ParseException e) {
      e.printStackTrace();
      assertTrue(false);
    }
  }
}
