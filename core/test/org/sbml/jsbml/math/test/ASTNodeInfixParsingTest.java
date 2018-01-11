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

import java.io.StringReader;

import org.junit.BeforeClass;
import org.junit.Test;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.text.parser.FormulaParser;
import org.sbml.jsbml.text.parser.FormulaParserLL3;
import org.sbml.jsbml.text.parser.IFormulaParser;
import org.sbml.jsbml.text.parser.ParseException;
import org.sbml.jsbml.util.compilers.FormulaCompiler;
import org.sbml.jsbml.util.compilers.FormulaCompilerLibSBML;
import org.sbml.jsbml.util.compilers.LibSBMLFormulaCompiler;

/**
 * Tests related to {@link ASTNode#parseFormula(String)} and {@link ASTNode#parseFormula(String, IFormulaParser)}.
 * 
 * @author Nicolas Rodriguez
 * @author Victor Kofia
 * @since 1.0
 */
public class ASTNodeInfixParsingTest {

  /**
   * 
   */
  @BeforeClass public static void init() {
    caseInsensitiveParser.setCaseSensitive(false);
  }

  /**
   * 
   */
  final static FormulaParserLL3 caseSensitiveParser = new FormulaParserLL3(new StringReader(""));

  /**
   * 
   */
  final static FormulaParserLL3 l3Parser = caseSensitiveParser;

  /**
   * 
   */
  final static FormulaParser parser = new FormulaParser(new StringReader(""));

  /**
   * 
   */
  final static FormulaParserLL3 caseInsensitiveParser = new FormulaParserLL3(new StringReader(""));
  
  /**
   * 
   */
  final static FormulaCompiler l3Compiler = new FormulaCompilerLibSBML();  

  /**
   * 
   */
  final static FormulaCompiler oldJsbmlCompiler = new FormulaCompiler();  

  
  /**
   * 
   */
  final static FormulaCompiler oldL1Compiler = new LibSBMLFormulaCompiler();
  
  @Test public void andParsingTests() {

    try {
      // and(x, y)
      ASTNode n = ASTNode.parseFormula("and(x,y)", l3Parser);
      String formula = ASTNode.formulaToString(n, l3Compiler);
      System.out.println("formula 'and(x, y)' = " + formula);
      n = ASTNode.parseFormula(formula, l3Parser); 

      // x && y
      n = ASTNode.parseFormula("x && y", l3Parser);
      formula = ASTNode.formulaToString(n, l3Compiler);
      System.out.println("formula 'x && y' = " + formula);
      n = ASTNode.parseFormula(formula, l3Parser); 

      // x and y
      n = ASTNode.parseFormula("x and y", parser); // and(x,y) does not work ==> difference compared to the L1 supported syntax?
      formula = ASTNode.formulaToString(n);
      System.out.println("formula 'x and y)' = " + formula);
      n = ASTNode.parseFormula(formula, parser); 

      // and(x, y)
      n = ASTNode.parseFormula("and(x,y)", l3Parser);
      formula = ASTNode.formulaToString(n, oldL1Compiler);
      System.out.println("formula 'and(x, y)' = " + formula);
      n = ASTNode.parseFormula(formula, l3Parser);       
      
    } catch (ParseException e) {
      // should never happen
      e.printStackTrace();
      assertTrue(false);
    }
  }
  
  @Test public void factorialParsingTests() {

    try {
      // Parsing and compiling with the L3
      ASTNode n = ASTNode.parseFormula("factorial(n)", l3Parser);
      String formula = ASTNode.formulaToString(n, l3Compiler);
      n = ASTNode.parseFormula(formula, l3Parser); 

      
      // Parsing and compiling with the defaults
      n = ASTNode.parseFormula("factorial(n)");
      formula = ASTNode.formulaToString(n);
      System.out.println("default formula = " + formula);
      n = ASTNode.parseFormula(formula); 

      // n!
      n = ASTNode.parseFormula("n!", parser);
      formula = ASTNode.formulaToString(n);
      n = ASTNode.parseFormula(formula, parser); 

      // (n)!
      n = ASTNode.parseFormula("(n)!", parser);
      formula = ASTNode.formulaToString(n);
      n = ASTNode.parseFormula(formula, parser); 
      
      // (n + 1)!
      n = ASTNode.parseFormula("(n + 1)!", parser);
      formula = ASTNode.formulaToString(n, oldJsbmlCompiler);
      n = ASTNode.parseFormula(formula, parser); 

      try {
        n = ASTNode.parseFormula(formula, l3Parser);
        assertTrue("The L3 parser does not support '(n + 1)!' at the moment, so we expect an Exception.", false);
      } catch (ParseException e) {
        // should happen, the L3 parser does not support '(n + 1)!'
        assertTrue(true);        
      }
      
    } catch (ParseException e) {
      // should never happen
      e.printStackTrace();
      assertTrue(false);
    }
    
  }
  
  @Test public void notParsingTests() {

    try {
      // not(x)
      ASTNode n = ASTNode.parseFormula("not(x)", l3Parser);
      String formula = ASTNode.formulaToString(n, l3Compiler);
      System.out.println("formula 'not(x)' = " + formula);
      n = ASTNode.parseFormula(formula, l3Parser); 

      // !(x)
      n = ASTNode.parseFormula("!(x)", l3Parser);
      formula = ASTNode.formulaToString(n, l3Compiler);
      System.out.println("formula '!(x)' = " + formula);
      n = ASTNode.parseFormula(formula, l3Parser); 

    } catch (ParseException e) {
      // should never happen
      e.printStackTrace();
      assertTrue(false);
    }
  }
  
  @Test public void orParsingTests() {

    try {
      // or(x, y)
      ASTNode n = ASTNode.parseFormula("or(x,y)", l3Parser);
      String formula = ASTNode.formulaToString(n, l3Compiler);
      System.out.println("formula 'or(x, y)' = " + formula);
      n = ASTNode.parseFormula(formula, l3Parser); 

      // x || y
      n = ASTNode.parseFormula("x || y", l3Parser);
      formula = ASTNode.formulaToString(n, l3Compiler);
      System.out.println("formula 'x || y' = " + formula);
      n = ASTNode.parseFormula(formula, l3Parser); 

      // x or y
      n = ASTNode.parseFormula("x or y", parser); // or(x,y) does not work ==> difference compared to the L1 supported syntax?
      formula = ASTNode.formulaToString(n);
      System.out.println("formula 'x or y)' = " + formula);
      n = ASTNode.parseFormula(formula, parser); 

      // or(x, y)
      n = ASTNode.parseFormula("or(x,y)", l3Parser);
      formula = ASTNode.formulaToString(n, oldL1Compiler);
      System.out.println("formula 'or(x, y)' = " + formula);
      n = ASTNode.parseFormula(formula, l3Parser);       
      
    } catch (ParseException e) {
      // should never happen
      e.printStackTrace();
      assertTrue(false);
    }
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testAbsCaseInsensitive() {
    boolean status = false;
    try {
      ASTNode abs = ASTNode.parseFormula("Abs(-1)", caseInsensitiveParser);
      status = (abs.getType() == ASTNode.Type.FUNCTION_ABS) && (abs.getType() != ASTNode.Type.FUNCTION);
      if (status) {
        ASTNode n = abs.getChild(0);
        status = n.getType() == ASTNode.Type.MINUS;
        if (status) {
          n = n.getChild(0);
          status = (n.getType() == ASTNode.Type.INTEGER) && (n.getInteger() == 1);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testAbsCaseSensitive() {
    boolean status = false;
    try {
      ASTNode abs = ASTNode.parseFormula("Abs(-1)", caseSensitiveParser);
      status = (abs.getType() != ASTNode.Type.FUNCTION_ABS) && (abs.getType() == ASTNode.Type.FUNCTION);
      if (status) {
        ASTNode n = abs.getChild(0);
        status = n.getType() == ASTNode.Type.MINUS;
        if (status) {
          n = n.getChild(0);
          status = (n.getType() == ASTNode.Type.INTEGER) && (n.getInteger() == 1);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testArcCosCaseInsensitive() {
    boolean status = false;
    try {
      ASTNode arccos = ASTNode.parseFormula("ArcCos(x)", caseInsensitiveParser);
      status = arccos.getType() == ASTNode.Type.FUNCTION_ARCCOS;
      if (status) {
        ASTNode n = arccos.getChild(0);
        status = n.getType() == ASTNode.Type.NAME && n.getName().equals("x");
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testArcCosCaseSensitive() {
    boolean status = false;
    try {
      ASTNode arccos = ASTNode.parseFormula("ArcCos(x)", caseSensitiveParser);
      status = (arccos.getType() != ASTNode.Type.FUNCTION_ARCCOS) && (arccos.getType() == ASTNode.Type.FUNCTION);
      if (status) {
        ASTNode n = arccos.getChild(0);
        status = n.getType() == ASTNode.Type.NAME && n.getName().equals("x");
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testArcCoshCaseInsensitive() {
    boolean status = false;
    try {
      ASTNode arccosh = ASTNode.parseFormula("ArcCosh(x)", caseInsensitiveParser);
      status = arccosh.getType() == ASTNode.Type.FUNCTION_ARCCOSH;
      if (status) {
        ASTNode n = arccosh.getChild(0);
        status = n.getType() == ASTNode.Type.NAME && n.getName().equals("x");
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testArcCoshCaseSensitive() {
    boolean status = false;
    try {
      ASTNode arccosh = ASTNode.parseFormula("ArcCosh(x)", caseSensitiveParser);
      status = (arccosh.getType() != ASTNode.Type.FUNCTION_ARCCOSH) && (arccosh.getType() == ASTNode.Type.FUNCTION);
      if (status) {
        ASTNode n = arccosh.getChild(0);
        status = n.getType() == ASTNode.Type.NAME && n.getName().equals("x");
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testArcCotCaseInsensitive() {
    boolean status = false;
    try {
      ASTNode arccot = ASTNode.parseFormula("ArcCot(x)", caseInsensitiveParser);
      status = arccot.getType() == ASTNode.Type.FUNCTION_ARCCOT;
      if (status) {
        ASTNode n = arccot.getChild(0);
        status = n.getType() == ASTNode.Type.NAME && n.getName().equals("x");
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testArcCotCaseSensitive() {
    boolean status = false;
    try {
      ASTNode arccot = ASTNode.parseFormula("ArcCot(x)", caseSensitiveParser);
      status = (arccot.getType() != ASTNode.Type.FUNCTION_ARCCOT) && (arccot.getType() == ASTNode.Type.FUNCTION);
      if (status) {
        ASTNode n = arccot.getChild(0);
        status = n.getType() == ASTNode.Type.NAME && n.getName().equals("x");
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testArcCothCaseInsensitive() {
    boolean status = false;
    try {
      ASTNode arccoth = ASTNode.parseFormula("ArcCoth(x)", caseInsensitiveParser);
      status = arccoth.getType() == ASTNode.Type.FUNCTION_ARCCOTH;
      if (status) {
        ASTNode n = arccoth.getChild(0);
        status = n.getType() == ASTNode.Type.NAME && n.getName().equals("x");
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testArcCothCaseSensitive() {
    boolean status = false;
    try {
      ASTNode arccoth = ASTNode.parseFormula("ArcCoth(x)", caseSensitiveParser);
      status = (arccoth.getType() != ASTNode.Type.FUNCTION_ARCCOTH) && (arccoth.getType() == ASTNode.Type.FUNCTION);
      if (status) {
        ASTNode n = arccoth.getChild(0);
        status = n.getType() == ASTNode.Type.NAME && n.getName().equals("x");
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testArcCscCaseInsensitive() {
    boolean status = false;
    try {
      ASTNode arccsc = ASTNode.parseFormula("ArcCsc(x)", caseInsensitiveParser);
      status = arccsc.getType() == ASTNode.Type.FUNCTION_ARCCSC;
      if (status) {
        ASTNode n = arccsc.getChild(0);
        status = n.getType() == ASTNode.Type.NAME && n.getName().equals("x");
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testArcCscCaseSensitive() {
    boolean status = false;
    try {
      ASTNode arccsc = ASTNode.parseFormula("ArcCsc(x)", caseSensitiveParser);
      status = (arccsc.getType() != ASTNode.Type.FUNCTION_ARCCSC) && (arccsc.getType() == ASTNode.Type.FUNCTION);
      if (status) {
        ASTNode n = arccsc.getChild(0);
        status = n.getType() == ASTNode.Type.NAME && n.getName().equals("x");
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testArcCschCaseInsensitive() {
    boolean status = false;
    try {
      ASTNode arccsch = ASTNode.parseFormula("ArcCsch(x)", caseInsensitiveParser);
      status = arccsch.getType() == ASTNode.Type.FUNCTION_ARCCSCH;
      if (status) {
        ASTNode n = arccsch.getChild(0);
        status = n.getType() == ASTNode.Type.NAME && n.getName().equals("x");
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testArcCschCaseSensitive() {
    boolean status = false;
    try {
      ASTNode arccsch = ASTNode.parseFormula("ArcCsch(x)", caseSensitiveParser);
      status = (arccsch.getType() != ASTNode.Type.FUNCTION_ARCCSCH) && (arccsch.getType() == ASTNode.Type.FUNCTION);
      if (status) {
        ASTNode n = arccsch.getChild(0);
        status = n.getType() == ASTNode.Type.NAME && n.getName().equals("x");
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testArcSecCaseInsensitive() {
    boolean status = false;
    try {
      ASTNode arcsec = ASTNode.parseFormula("ArcSec(x)", caseInsensitiveParser);
      status = arcsec.getType() == ASTNode.Type.FUNCTION_ARCSEC;
      if (status) {
        ASTNode n = arcsec.getChild(0);
        status = n.getType() == ASTNode.Type.NAME && n.getName().equals("x");
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testArcSecCaseSensitive() {
    boolean status = false;
    try {
      ASTNode arcsec = ASTNode.parseFormula("ArcSec(x)", caseSensitiveParser);
      status = (arcsec.getType() != ASTNode.Type.FUNCTION_ARCSEC) && (arcsec.getType() == ASTNode.Type.FUNCTION);
      if (status) {
        ASTNode n = arcsec.getChild(0);
        status = n.getType() == ASTNode.Type.NAME && n.getName().equals("x");
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testArcSechCaseInsensitive() {
    boolean status = false;
    try {
      ASTNode arcsech = ASTNode.parseFormula("ArcSech(x)", caseInsensitiveParser);
      status = arcsech.getType() == ASTNode.Type.FUNCTION_ARCSECH;
      if (status) {
        ASTNode n = arcsech.getChild(0);
        status = n.getType() == ASTNode.Type.NAME && n.getName().equals("x");
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testArcSechCaseSensitive() {
    boolean status = false;
    try {
      ASTNode arcsech = ASTNode.parseFormula("ArcSech(x)", caseSensitiveParser);
      status = (arcsech.getType() != ASTNode.Type.FUNCTION_ARCSECH) && (arcsech.getType() == ASTNode.Type.FUNCTION);
      if (status) {
        ASTNode n = arcsech.getChild(0);
        status = n.getType() == ASTNode.Type.NAME && n.getName().equals("x");
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testArcSinCaseInsensitive() {
    boolean status = false;
    try {
      ASTNode arcsin = ASTNode.parseFormula("ArcSin(x)", caseInsensitiveParser);
      status = arcsin.getType() == ASTNode.Type.FUNCTION_ARCSIN;
      if (status) {
        ASTNode n = arcsin.getChild(0);
        status = n.getType() == ASTNode.Type.NAME && n.getName().equals("x");
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testArcSinCaseSensitive() {
    boolean status = false;
    try {
      ASTNode arcsin = ASTNode.parseFormula("ArcSin(x)", caseSensitiveParser);
      status = (arcsin.getType() != ASTNode.Type.FUNCTION_ARCSIN) && (arcsin.getType() == ASTNode.Type.FUNCTION);
      if (status) {
        ASTNode n = arcsin.getChild(0);
        status = n.getType() == ASTNode.Type.NAME && n.getName().equals("x");
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testArcSinhCaseInsensitive() {
    boolean status = false;
    try {
      ASTNode arcsinh = ASTNode.parseFormula("ArcSinh(x)", caseInsensitiveParser);
      status = arcsinh.getType() == ASTNode.Type.FUNCTION_ARCSINH;
      if (status) {
        ASTNode n = arcsinh.getChild(0);
        status = n.getType() == ASTNode.Type.NAME && n.getName().equals("x");
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testArcSinhCaseSensitive() {
    boolean status = false;
    try {
      ASTNode arcsinh = ASTNode.parseFormula("ArcSinh(x)", caseSensitiveParser);
      status = (arcsinh.getType() != ASTNode.Type.FUNCTION_ARCSINH) && (arcsinh.getType() == ASTNode.Type.FUNCTION);
      if (status) {
        ASTNode n = arcsinh.getChild(0);
        status = n.getType() == ASTNode.Type.NAME && n.getName().equals("x");
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testArcTanCaseInsensitive() {
    boolean status = false;
    try {
      ASTNode arctan = ASTNode.parseFormula("ArcTan(x)", caseInsensitiveParser);
      status = arctan.getType() == ASTNode.Type.FUNCTION_ARCTAN;
      if (status) {
        ASTNode n = arctan.getChild(0);
        status = n.getType() == ASTNode.Type.NAME && n.getName().equals("x");
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testArcTanCaseSensitive() {
    boolean status = false;
    try {
      ASTNode arctan = ASTNode.parseFormula("ArcTan(x)", caseSensitiveParser);
      status = (arctan.getType() != ASTNode.Type.FUNCTION_ARCTAN) && (arctan.getType() == ASTNode.Type.FUNCTION);
      if (status) {
        ASTNode n = arctan.getChild(0);
        status = n.getType() == ASTNode.Type.NAME && n.getName().equals("x");
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testArcTanhCaseInsensitive() {
    boolean status = false;
    try {
      ASTNode arctanh = ASTNode.parseFormula("ArcTanh(x)", caseInsensitiveParser);
      status = arctanh.getType() == ASTNode.Type.FUNCTION_ARCTANH;
      if (status) {
        ASTNode n = arctanh.getChild(0);
        status = n.getType() == ASTNode.Type.NAME && n.getName().equals("x");
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testArcTanhCaseSensitive() {
    boolean status = false;
    try {
      ASTNode arctanh = ASTNode.parseFormula("ArcTanh(x)", caseSensitiveParser);
      status = (arctanh.getType() != ASTNode.Type.FUNCTION_ARCTANH) && (arctanh.getType() == ASTNode.Type.FUNCTION);
      if (status) {
        ASTNode n = arctanh.getChild(0);
        status = n.getType() == ASTNode.Type.NAME && n.getName().equals("x");
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testAvogadroCaseInsensitive() {
    boolean status = false;
    try {
      ASTNode avogadro = ASTNode.parseFormula("Avogadro", caseInsensitiveParser);
      status = (avogadro.getType() == ASTNode.Type.NAME_AVOGADRO) && (avogadro.getType() != ASTNode.Type.NAME);
//      if (status) {
//        status = avogadro.getName() == null;
//      } // TODO - name = "Avogadro's number" in the old ASTNode
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testAvogadroCaseSensitive() {
    boolean status = false;
    try {
      ASTNode avogadro = ASTNode.parseFormula("Avogadro", caseSensitiveParser);
      status = (avogadro.getType() != ASTNode.Type.NAME_AVOGADRO) && (avogadro.getType() == ASTNode.Type.NAME);
      if (status) {
        status = avogadro.getName().equals("Avogadro");
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testCeilCaseInsensitive() {
    boolean status = false;
    try {
      ASTNode ceil = ASTNode.parseFormula("Ceil(-x)", caseInsensitiveParser);
      status = (ceil.getType() == ASTNode.Type.FUNCTION_CEILING) && (ceil.getType() != ASTNode.Type.FUNCTION);
      if (status) {
        ASTNode n = ceil.getChild(0);
        status = n.getType() == ASTNode.Type.MINUS;
        if (status) {
          n = n.getChild(0);
          status = (n.getType() == ASTNode.Type.NAME) && (n.getName().equals("x"));
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testCeilCaseSensitive() {
    boolean status = false;
    try {
      ASTNode ceil = ASTNode.parseFormula("Ceil(-x)", caseSensitiveParser);
      status = (ceil.getType() != ASTNode.Type.FUNCTION_CEILING) && (ceil.getType() == ASTNode.Type.FUNCTION);
      if (status) {
        ASTNode n = ceil.getChild(0);
        status = n.getType() == ASTNode.Type.MINUS;
        if (status) {
          n = n.getChild(0);
          status = (n.getType() == ASTNode.Type.NAME) && (n.getName().equals("x"));
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testCosCaseInsensitive() {
    boolean status = false;
    try {
      ASTNode cos = ASTNode.parseFormula("Cos(x)", caseInsensitiveParser);
      status = cos.getType() == ASTNode.Type.FUNCTION_COS;
      if (status) {
        ASTNode n = cos.getChild(0);
        status = n.getType() == ASTNode.Type.NAME && n.getName().equals("x");
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testCosCaseSensitive() {
    boolean status = false;
    try {
      ASTNode cos = ASTNode.parseFormula("Cos(x)", caseSensitiveParser);
      status = (cos.getType() != ASTNode.Type.FUNCTION_COS) && (cos.getType() == ASTNode.Type.FUNCTION);
      if (status) {
        ASTNode n = cos.getChild(0);
        status = n.getType() == ASTNode.Type.NAME && n.getName().equals("x");
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testCoshCaseInsensitive() {
    boolean status = false;
    try {
      ASTNode cosh = ASTNode.parseFormula("Cosh(x)", caseInsensitiveParser);
      status = cosh.getType() == ASTNode.Type.FUNCTION_COSH;
      if (status) {
        ASTNode n = cosh.getChild(0);
        status = n.getType() == ASTNode.Type.NAME && n.getName().equals("x");
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testCoshCaseSensitive() {
    boolean status = false;
    try {
      ASTNode cosh = ASTNode.parseFormula("Cosh(x)", caseSensitiveParser);
      status = (cosh.getType() != ASTNode.Type.FUNCTION_COSH) && (cosh.getType() == ASTNode.Type.FUNCTION);
      if (status) {
        ASTNode n = cosh.getChild(0);
        status = n.getType() == ASTNode.Type.NAME && n.getName().equals("x");
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testCotCaseInsensitive() {
    boolean status = false;
    try {
      ASTNode cot = ASTNode.parseFormula("Cot(x)", caseInsensitiveParser);
      status = cot.getType() == ASTNode.Type.FUNCTION_COT;
      if (status) {
        ASTNode n = cot.getChild(0);
        status = n.getType() == ASTNode.Type.NAME && n.getName().equals("x");
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testCotCaseSensitive() {
    boolean status = false;
    try {
      ASTNode cot = ASTNode.parseFormula("Cot(x)", caseSensitiveParser);
      status = (cot.getType() != ASTNode.Type.FUNCTION_COT) && (cot.getType() == ASTNode.Type.FUNCTION);
      if (status) {
        ASTNode n = cot.getChild(0);
        status = n.getType() == ASTNode.Type.NAME && n.getName().equals("x");
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testCothCaseInsensitive() {
    boolean status = false;
    try {
      ASTNode coth = ASTNode.parseFormula("Coth(x)", caseInsensitiveParser);
      status = coth.getType() == ASTNode.Type.FUNCTION_COTH;
      if (status) {
        ASTNode n = coth.getChild(0);
        status = n.getType() == ASTNode.Type.NAME && n.getName().equals("x");
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testCothCaseSensitive() {
    boolean status = false;
    try {
      ASTNode coth = ASTNode.parseFormula("Coth(x)", caseSensitiveParser);
      status = (coth.getType() != ASTNode.Type.FUNCTION_COTH) && (coth.getType() == ASTNode.Type.FUNCTION);
      if (status) {
        ASTNode n = coth.getChild(0);
        status = n.getType() == ASTNode.Type.NAME && n.getName().equals("x");
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testCscCaseInsensitive() {
    boolean status = false;
    try {
      ASTNode csc = ASTNode.parseFormula("Csc(x)", caseInsensitiveParser);
      status = csc.getType() == ASTNode.Type.FUNCTION_CSC;
      if (status) {
        ASTNode n = csc.getChild(0);
        status = n.getType() == ASTNode.Type.NAME && n.getName().equals("x");
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testCscCaseSensitive() {
    boolean status = false;
    try {
      ASTNode csc = ASTNode.parseFormula("Csc(x)", caseSensitiveParser);
      status = (csc.getType() != ASTNode.Type.FUNCTION_CSC) && (csc.getType() == ASTNode.Type.FUNCTION);
      if (status) {
        ASTNode n = csc.getChild(0);
        status = n.getType() == ASTNode.Type.NAME && n.getName().equals("x");
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testCschCaseInsensitive() {
    boolean status = false;
    try {
      ASTNode csch = ASTNode.parseFormula("Csch(x)", caseInsensitiveParser);
      status = csch.getType() == ASTNode.Type.FUNCTION_CSCH;
      if (status) {
        ASTNode n = csch.getChild(0);
        status = n.getType() == ASTNode.Type.NAME && n.getName().equals("x");
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testCschCaseSensitive() {
    boolean status = false;
    try {
      ASTNode csch = ASTNode.parseFormula("Csch(x)", caseSensitiveParser);
      status = (csch.getType() != ASTNode.Type.FUNCTION_CSCH) && (csch.getType() == ASTNode.Type.FUNCTION);
      if (status) {
        ASTNode n = csch.getChild(0);
        status = n.getType() == ASTNode.Type.NAME && n.getName().equals("x");
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testDelayCaseInsensitive() {
    boolean status = false;
    try {
      ASTNode delay = ASTNode.parseFormula("Delay(-x)", caseInsensitiveParser);
      status = (delay.getType() == ASTNode.Type.FUNCTION_DELAY) && (delay.getType() != ASTNode.Type.FUNCTION);
      System.out.println(status + " " + delay.getType());
      if (status) {
        ASTNode n = delay.getChild(0);
        status = n.getType() == ASTNode.Type.MINUS;
        if (status) {
          n = n.getChild(0);
          status = (n.getType() == ASTNode.Type.NAME) && (n.getName().equals("x"));
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testDelayCaseSensitive() {
    boolean status = false;
    try {
      ASTNode delay = ASTNode.parseFormula("Delay(-x)", caseSensitiveParser);
      status = (delay.getType() != ASTNode.Type.FUNCTION_DELAY) && (delay.getType() == ASTNode.Type.FUNCTION);
      if (status) {
        ASTNode n = delay.getChild(0);
        status = n.getType() == ASTNode.Type.MINUS;
        if (status) {
          n = n.getChild(0);
          status = (n.getType() == ASTNode.Type.NAME) && (n.getName().equals("x"));
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testEulersConstantCaseInsensitive() {
    boolean status = false;
    try {
      ASTNode e = ASTNode.parseFormula("1 + exponentialE", caseInsensitiveParser);
      status = e.getType() == ASTNode.Type.PLUS;
      if (status) {
        ASTNode n = e.getChild(1);
        status = n.getType() == ASTNode.Type.CONSTANT_E;
        if (status) {
          status = n.toFormula().equals("exponentiale") && n.getName() == null;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testEulersConstantCaseSensitive() {
    boolean status = false;
    try {
      ASTNode e = ASTNode.parseFormula("1 + exponentialE", caseSensitiveParser);
      status = e.getType() == ASTNode.Type.PLUS;
      if (status) {
        ASTNode n = e.getChild(1);
        status = (n.getType() != ASTNode.Type.CONSTANT_E) && (n.getType() == ASTNode.Type.NAME);
        if (status) {
          status = n.toFormula().equals("exponentialE") && n.getName().equals("exponentialE");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testExpCaseInsensitive() {
    boolean status = false;
    try {
      ASTNode exp = ASTNode.parseFormula("Exp(-1)", caseInsensitiveParser);
      status = (exp.getType() == ASTNode.Type.FUNCTION_EXP) && (exp.getType() != ASTNode.Type.FUNCTION);
      if (status) {
        ASTNode n = exp.getChild(0);
        status = n.getType() == ASTNode.Type.MINUS;
        if (status) {
          n = n.getChild(0);
          status = (n.getType() == ASTNode.Type.INTEGER) && (n.getInteger() == 1);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testExpCaseSensitive() {
    boolean status = false;
    try {
      ASTNode exp = ASTNode.parseFormula("Exp(-1)", caseSensitiveParser);
      status = (exp.getType() != ASTNode.Type.FUNCTION_EXP) && (exp.getType() == ASTNode.Type.FUNCTION);
      if (status) {
        ASTNode n = exp.getChild(0);
        status = n.getType() == ASTNode.Type.MINUS;
        if (status) {
          n = n.getChild(0);
          status = (n.getType() == ASTNode.Type.INTEGER) && (n.getInteger() == 1);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testFalseCaseInsensitive() {
    boolean status = false;
    try {
      ASTNode booleanFalse = ASTNode.parseFormula("False", caseInsensitiveParser);
      status = (booleanFalse.getType() == ASTNode.Type.CONSTANT_FALSE) && (booleanFalse.getType() != null);
      if (status) {
        status = booleanFalse.getName() == null;
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }

  // TODO - check differences with the new ASTNode
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
//  @Test
//  public void testFalseCaseSensitive() {
//    boolean status = false;
//    try {
//      ASTNode booleanFalse = ASTNode.parseFormula("False", caseSensitiveParser);
//      status = (booleanFalse.getType() != ASTNode.Type.CONSTANT_FALSE) && (booleanFalse.getType() == ASTNode.Type.NAME);
//      if (status) {
//        status = booleanFalse.getName().equalsIgnoreCase("False");
//      }
//    } catch (Exception e) {
//      e.printStackTrace();
//      status = false;
//    }
//    assertTrue(status);
//  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testFloorCaseInsensitive() {
    boolean status = false;
    try {
      ASTNode floor = ASTNode.parseFormula("Floor(-x)", caseInsensitiveParser);
      status = (floor.getType() == ASTNode.Type.FUNCTION_FLOOR) && (floor.getType() != ASTNode.Type.FUNCTION);
      System.out.println(status + "" + floor.getType());
      if (status) {
        ASTNode n = floor.getChild(0);
        status = n.getType() == ASTNode.Type.MINUS;
        if (status) {
          n = n.getChild(0);
          status = (n.getType() == ASTNode.Type.NAME) && (n.getName().equals("x"));
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testFloorCaseSensitive() {
    boolean status = false;
    try {
      ASTNode floor = ASTNode.parseFormula("Floor(-x)", caseSensitiveParser);
      status = (floor.getType() != ASTNode.Type.FUNCTION_FLOOR) && (floor.getType() == ASTNode.Type.FUNCTION);
      if (status) {
        ASTNode n = floor.getChild(0);
        status = n.getType() == ASTNode.Type.MINUS;
        if (status) {
          n = n.getChild(0);
          status = (n.getType() == ASTNode.Type.NAME) && (n.getName().equals("x"));
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testInfinityCaseInsensitive() {
    boolean status = false;
    try {
      ASTNode infinity = ASTNode.parseFormula("Infinity", caseInsensitiveParser);
      status = (infinity.getType() == ASTNode.Type.REAL) && (infinity.getType() != ASTNode.Type.NAME);
      if (status) {
        status = infinity.getReal() == Double.POSITIVE_INFINITY;
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testInfinityCaseSensitive() {
    boolean status = false;
    try {
      ASTNode infinity = ASTNode.parseFormula("Infinity", caseSensitiveParser);
      status = (infinity.getType() != ASTNode.Type.REAL) && (infinity.getType() == ASTNode.Type.NAME);
      if (status) {
        status = infinity.getName().equals("Infinity");
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testLnCaseInsensitive() {
    boolean status = false;
    try {
      ASTNode ln = ASTNode.parseFormula("Ln(1000)", caseInsensitiveParser);
      status = (ln.getType() == ASTNode.Type.FUNCTION_LN) && (ln.getType() != ASTNode.Type.FUNCTION);
      if (status) {
        ASTNode n = (ASTNode) ln.getChildAt(0);
        status = (n.getType() == ASTNode.Type.INTEGER) && (n.getInteger() == 1000);
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testLnCaseSensitive() {
    boolean status = false;
    try {
      ASTNode ln = ASTNode.parseFormula("Ln(1000)", caseSensitiveParser);
      status = (ln.getType() != ASTNode.Type.FUNCTION_LN) && (ln.getType() == ASTNode.Type.FUNCTION);
      if (status) {
        ASTNode n = ln.getChild(0);
        status = (n.getType() == ASTNode.Type.INTEGER) && (n.getInteger() == 1000);
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testLogCaseInsensitive() {
    boolean status = false;
    try {
      ASTNode log = ASTNode.parseFormula("Log(1000)", caseInsensitiveParser);
      status = (log.getType() == ASTNode.Type.FUNCTION_LN) && (log.getType() != ASTNode.Type.FUNCTION);
      if (status) {
        ASTNode n = log.getChild(0);
        status = (n.getType() == ASTNode.Type.INTEGER) && (n.getInteger() == 1000);
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testLogCaseSensitive() {
    boolean status = false;
    try {
      ASTNode log = ASTNode.parseFormula("Log(1000)", caseSensitiveParser);
      status = (log.getType() != ASTNode.Type.FUNCTION_LOG) && (log.getType() == ASTNode.Type.FUNCTION);
      if (status) {
        ASTNode n = log.getChild(0);
        status = (n.getType() == ASTNode.Type.INTEGER) && (n.getInteger() == 1000);
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testMinusInfinityCaseInsensitive() {
    boolean status = false;
    try {
      ASTNode infinity = ASTNode.parseFormula("-Infinity", caseInsensitiveParser);
      status = infinity.getType() == ASTNode.Type.MINUS;
      if (status) {
        ASTNode n = infinity.getChild(0);
        status = (n.getType() == ASTNode.Type.REAL) && (n.getType() != ASTNode.Type.NAME);
        if (status) {
          status = n.getReal() == Double.POSITIVE_INFINITY;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testMinusInfinityCaseSensitive() {
    boolean status = false;
    try {
      ASTNode infinity = ASTNode.parseFormula("-Infinity", caseSensitiveParser);
      status = infinity.getType() == ASTNode.Type.MINUS;
      if (status) {
        ASTNode n = infinity.getChild(0);
        status = (n.getType() != ASTNode.Type.REAL) && (n.getType() == ASTNode.Type.NAME);
        if (status) {
          status = n.getName().equals("Infinity");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }

  // TODO - have a look at the parsing differences for Nan
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
//  @Test
//  public void testNanCaseInsensitive() {
//    boolean status = false;
//    try {
//      // Verify 'NaN'
//      ASTNode nan = ASTNode.parseFormula("Nan", caseInsensitiveParser);
//      status = (nan.getType() == ASTNode.Type.REAL) && (nan.getType() != ASTNode.Type.NAME);
//      System.out.println("status 1 = " + status + " nan.getType() = " + nan.getType());
//      if (status) {
//        status = nan.getReal() == Double.NaN;
//      }
//      System.out.println("status 2 = " + status);
//
//      // Verify 'NotANumber'
//      nan = ASTNode.parseFormula("NotANumber", caseInsensitiveParser);
//      status = (nan.getType() == ASTNode.Type.REAL) && (nan.getType() != ASTNode.Type.NAME);
//      System.out.println("status 3 = " + status);
//
//      if (status) {
//        status = Double.compare(nan.getReal(), Double.NaN) == 0;
//      }
//      System.out.println("status 4 = " + status);
//    } catch (Exception e) {
//      e.printStackTrace();
//      status = false;
//    }
//    assertTrue(status);
//  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
//  @Test
//  public void testNanCaseSensitive() {
//    boolean status = false;
//    try {
//      // Verify 'NaN'
//      ASTNode nan = ASTNode.parseFormula("Nan", caseSensitiveParser);
//      status = (nan.getType() != ASTNode.Type.REAL) && (nan.getType() == ASTNode.Type.NAME);
//      if (status) {
//        status = nan.getName().equals("Nan");
//      }
//      // Verify 'NotANumber'
//      nan = ASTNode.parseFormula("NotANumber", caseSensitiveParser);
//      status = status && (nan.getType() != ASTNode.Type.REAL) && (nan.getType() == ASTNode.Type.NAME);
//      if (status) {
//        status = nan.getName().equals("NotANumber");
//      }
//    } catch (Exception e) {
//      e.printStackTrace();
//      status = false;
//    }
//    assertTrue(status);
//  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testPiCaseInsensitive() {
    boolean status = false;
    try {
      ASTNode pi = ASTNode.parseFormula("1 + Pi", caseInsensitiveParser);
      status = pi.getType() == ASTNode.Type.PLUS;
      if (status) {
        ASTNode n = pi.getChild(1);
        status = n.getType() == ASTNode.Type.CONSTANT_PI;
        if (status) {
          status = n.toFormula().equals("pi") && n.getName() == null;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testPiCaseSensitive() {
    boolean status = false;
    try {
      ASTNode pi = ASTNode.parseFormula("1 + Pi", caseSensitiveParser);
      status = pi.getType() == ASTNode.Type.PLUS;
      if (status) {
        ASTNode n = pi.getChild(1);
        status = (n.getType() != ASTNode.Type.CONSTANT_PI) && (n.getType() == ASTNode.Type.NAME);
        if (status) {
          status = n.toFormula().equals("Pi") && n.getName().equals("Pi");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testPowerFunctionCaseInsensitive() {
    boolean status = false;
    try {
      // Verify 'pow'
      ASTNode power = ASTNode.parseFormula("Pow(1000)", caseInsensitiveParser);
      status = (power.getType() == ASTNode.Type.FUNCTION_POWER) && (power.getType() != ASTNode.Type.FUNCTION);
      if (status) {
        ASTNode n = power.getChild(0);
        status = (n.getType() == ASTNode.Type.INTEGER) && (n.getInteger() == 1000);
      }
      // Verify 'power'
      power = ASTNode.parseFormula("Power(1000)", caseInsensitiveParser);
      status = status && (power.getType() == ASTNode.Type.FUNCTION_POWER) && (power.getType() != ASTNode.Type.FUNCTION);
      if (status) {
        ASTNode n = power.getChild(0);
        status = (n.getType() == ASTNode.Type.INTEGER) && (n.getInteger() == 1000);
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }

  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testPowerFunctionCaseSensitive() {
    boolean status = false;
    try {
      // Verify 'pow'
      ASTNode power = ASTNode.parseFormula("Pow(1000)", caseSensitiveParser);
      status = (power.getType() != ASTNode.Type.FUNCTION_POWER) && (power.getType() == ASTNode.Type.FUNCTION);
      if (status) {
        ASTNode n = power.getChild(0);
        status = (n.getType() == ASTNode.Type.INTEGER) && (n.getInteger() == 1000);
      }
      // Verify 'power'
      power = ASTNode.parseFormula("Power(1000)", caseSensitiveParser);
      status = status && (power.getType() != ASTNode.Type.FUNCTION_POWER) && (power.getType() == ASTNode.Type.FUNCTION);
      if (status) {
        ASTNode n = power.getChild(0);
        status = (n.getType() == ASTNode.Type.INTEGER) && (n.getInteger() == 1000);
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testPowerOperatorCaseInsensitive() {
    boolean status = false;
    try {
      ASTNode power = ASTNode.parseFormula("x^1000", caseInsensitiveParser);
      status = (power.getType() == ASTNode.Type.POWER) && (power.getType() != ASTNode.Type.FUNCTION);
      if (status) {
        ASTNode n = power.getChild(0);
        status = (n.getType() == ASTNode.Type.NAME) && (n.getName().equals("x"));
        if (status) {
            n = power.getChild(1);
            status = (n.getType() == ASTNode.Type.INTEGER) && (n.getInteger() == 1000);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testRootCaseInsensitive() {
    boolean status = false;
    try {
      // Verify 'sqr'
      ASTNode root = ASTNode.parseFormula("Sqr(2, 3)", caseInsensitiveParser);
      status = (root.getType() == ASTNode.Type.FUNCTION_ROOT) && (root.getType() != ASTNode.Type.FUNCTION);
      if (status) {
        ASTNode n = root.getChild(0), radicand = root.getChild(1);
        status = (n.getType() == ASTNode.Type.INTEGER) && (n.getInteger() == 2)
            && (radicand.getType() == ASTNode.Type.INTEGER) && (radicand.getInteger() == 3);
        
      }
      // Verify 'sqrt'
      root = ASTNode.parseFormula("Sqrt(2, 3)", caseInsensitiveParser);
      status = (root.getType() == ASTNode.Type.FUNCTION_ROOT) && (root.getType() != ASTNode.Type.FUNCTION);
      if (status) {
        ASTNode n = root.getChild(0), radicand = root.getChild(1);
        status = (n.getType() == ASTNode.Type.INTEGER) && (n.getInteger() == 2)
            && (radicand.getType() == ASTNode.Type.INTEGER) && (radicand.getInteger() == 3);
        
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testRootCaseSensitive() {
    boolean status = false;
    try {
      // Verify 'sqr'
      ASTNode root = ASTNode.parseFormula("Sqr(2, 3)", caseSensitiveParser);
      status = (root.getType() != ASTNode.Type.FUNCTION_ROOT) && (root.getType() == ASTNode.Type.FUNCTION);
      if (status) {
        ASTNode n = root.getChild(0), radicand = root.getChild(1);
        status = (n.getType() == ASTNode.Type.INTEGER) && (n.getInteger() == 2)
            && (radicand.getType() == ASTNode.Type.INTEGER) && (radicand.getInteger() == 3);
        
      }
      // Verify 'sqrt'
      root = ASTNode.parseFormula("Sqrt(2, 3)", caseSensitiveParser);
      status = (root.getType() != ASTNode.Type.FUNCTION_ROOT) && (root.getType() == ASTNode.Type.FUNCTION);
      if (status) {
        ASTNode n = root.getChild(0), radicand = root.getChild(1);
        status = (n.getType() == ASTNode.Type.INTEGER) && (n.getInteger() == 2)
            && (radicand.getType() == ASTNode.Type.INTEGER) && (radicand.getInteger() == 3);
        
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testSecCaseInsensitive() {
    boolean status = false;
    try {
      ASTNode sec = ASTNode.parseFormula("Sec(x)", caseInsensitiveParser);
      status = sec.getType() == ASTNode.Type.FUNCTION_SEC;
      if (status) {
        ASTNode n = sec.getChild(0);
        status = n.getType() == ASTNode.Type.NAME && n.getName().equals("x");
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testSecCaseSensitive() {
    boolean status = false;
    try {
      ASTNode sec = ASTNode.parseFormula("Sec(x)", caseSensitiveParser);
      status = (sec.getType() != ASTNode.Type.FUNCTION_SEC) && (sec.getType() == ASTNode.Type.FUNCTION);
      if (status) {
        ASTNode n = sec.getChild(0);
        status = n.getType() == ASTNode.Type.NAME && n.getName().equals("x");
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testSechCaseInsensitive() {
    boolean status = false;
    try {
      ASTNode sech = ASTNode.parseFormula("Sech(x)", caseInsensitiveParser);
      status = sech.getType() == ASTNode.Type.FUNCTION_SECH;
      if (status) {
        ASTNode n = sech.getChild(0);
        status = n.getType() == ASTNode.Type.NAME && n.getName().equals("x");
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testSechCaseSensitive() {
    boolean status = false;
    try {
      ASTNode sech = ASTNode.parseFormula("Sech(x)", caseSensitiveParser);
      status = (sech.getType() != ASTNode.Type.FUNCTION_SECH) && (sech.getType() == ASTNode.Type.FUNCTION);
      if (status) {
        ASTNode n = sech.getChild(0);
        status = n.getType() == ASTNode.Type.NAME && n.getName().equals("x");
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testSinCaseInsensitive() {
    boolean status = false;
    try {
      ASTNode sin = ASTNode.parseFormula("Sin(x)", caseInsensitiveParser);
      status = sin.getType() == ASTNode.Type.FUNCTION_SIN;
      if (status) {
        ASTNode n = sin.getChild(0);
        status = n.getType() == ASTNode.Type.NAME && n.getName().equals("x");
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testSinCaseSensitive() {
    boolean status = false;
    try {
      ASTNode sin = ASTNode.parseFormula("Sin(x)", caseSensitiveParser);
      status = (sin.getType() != ASTNode.Type.FUNCTION_SIN) && (sin.getType() == ASTNode.Type.FUNCTION);
      if (status) {
        ASTNode n = sin.getChild(0);
        status = n.getType() == ASTNode.Type.NAME && n.getName().equals("x");
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testSinhCaseInsensitive() {
    boolean status = false;
    try {
      ASTNode sinh = ASTNode.parseFormula("Sinh(x)", caseInsensitiveParser);
      status = sinh.getType() == ASTNode.Type.FUNCTION_SINH;
      if (status) {
        ASTNode n = sinh.getChild(0);
        status = n.getType() == ASTNode.Type.NAME && n.getName().equals("x");
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testSinhCaseSensitive() {
    boolean status = false;
    try {
      ASTNode sinh = ASTNode.parseFormula("Sinh(x)", caseSensitiveParser);
      status = (sinh.getType() != ASTNode.Type.FUNCTION_SINH) && (sinh.getType() == ASTNode.Type.FUNCTION);
      if (status) {
        ASTNode n = sinh.getChild(0);
        status = n.getType() == ASTNode.Type.NAME && n.getName().equals("x");
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testTanCaseInsensitive() {
    boolean status = false;
    try {
      ASTNode tan = ASTNode.parseFormula("Tan(x)", caseInsensitiveParser);
      status = tan.getType() == ASTNode.Type.FUNCTION_TAN;
      if (status) {
        ASTNode n = tan.getChild(0);
        status = n.getType() == ASTNode.Type.NAME && n.getName().equals("x");
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testTanCaseSensitive() {
    boolean status = false;
    try {
      ASTNode tan = ASTNode.parseFormula("Tan(x)", caseSensitiveParser);
      status = (tan.getType() != ASTNode.Type.FUNCTION_TAN) && (tan.getType() == ASTNode.Type.FUNCTION);
      if (status) {
        ASTNode n = tan.getChild(0);
        status = n.getType() == ASTNode.Type.NAME && n.getName().equals("x");
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testTanhCaseInsensitive() {
    boolean status = false;
    try {
      ASTNode tanh = ASTNode.parseFormula("Tanh(x)", caseInsensitiveParser);
      status = tanh.getType() == ASTNode.Type.FUNCTION_TANH;
      if (status) {
        ASTNode n = tanh.getChild(0);
        status = n.getType() == ASTNode.Type.NAME && n.getName().equals("x");
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testTanhCaseSensitive() {
    boolean status = false;
    try {
      ASTNode tanh = ASTNode.parseFormula("Tanh(x)", caseSensitiveParser);
      status = (tanh.getType() != ASTNode.Type.FUNCTION_TANH) && (tanh.getType() == ASTNode.Type.FUNCTION);
      if (status) {
        ASTNode n = tanh.getChild(0);
        status = n.getType() == ASTNode.Type.NAME && n.getName().equals("x");
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  // TODO - check differences with the new ASTNode
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
//  @Test
//  public void testTimeCaseInsensitive() {
//    boolean status = false;
//    try {
//      ASTNode time = ASTNode.parseFormula("Time", caseInsensitiveParser);
//      status = (time.getType() == ASTNode.Type.NAME_TIME) && (time.getType() != ASTNode.Type.NAME);
//      if (status) {
//        status = time.getName() == null;
//      }
//    } catch (Exception e) {
//      e.printStackTrace();
//      status = false;
//    }
//    assertTrue(status);
//  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testTimeCaseSensitive() {
    boolean status = false;
    try {
      ASTNode time = ASTNode.parseFormula("Time", caseSensitiveParser);
      status = (time.getType() != ASTNode.Type.NAME_TIME) && (time.getType() == ASTNode.Type.NAME);
      if (status) {
        status = time.getName().equals("Time");
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
  @Test
  public void testTrueCaseInsensitive() {
    boolean status = false;
    try {
      ASTNode booleanTrue = ASTNode.parseFormula("True", caseInsensitiveParser);
      status = (booleanTrue.getType() == ASTNode.Type.CONSTANT_TRUE) && (booleanTrue.getType() != ASTNode.Type.NAME);
      if (status) {
        status = booleanTrue.getName() == null;
      }
    } catch (Exception e) {
      e.printStackTrace();
      status = false;
    }
    assertTrue(status);
  }
  
  // TODO - check differences with the new ASTNode
  /**
   * Test method for {@link org.sbml.jsbml.ASTNode#parseFormula(java.lang.String, org.sbml.jsbml.text.parser.IFormulaParser)}.
   */
//  @Test
//  public void testTrueCaseSensitive() {
//    boolean status = false;
//    try {
//      ASTNode booleanTrue = ASTNode.parseFormula("True", caseSensitiveParser);
//      status = (booleanTrue.getType() != ASTNode.Type.CONSTANT_TRUE) && (booleanTrue.getType() == ASTNode.Type.NAME);
//      if (status) {
//        status = booleanTrue.getName().equals("True"); // TODO - name is not stored if invalid ??
//      }
//    } catch (Exception e) {
//      e.printStackTrace();
//      status = false;
//    }
//    assertTrue(status);
//  }

  @Test public void xorParsingTests() {

    try {
      // xor(x, y)
      ASTNode n = ASTNode.parseFormula("xor(x,y)", l3Parser);
      String formula = ASTNode.formulaToString(n, l3Compiler);
      System.out.println("formula 'xor(x, y)' = " + formula);
      n = ASTNode.parseFormula(formula, l3Parser);
      // xor(x, y)
      n = ASTNode.parseFormula("xor(x,y)", l3Parser);
      formula = ASTNode.formulaToString(n, oldL1Compiler);
      System.out.println("formula 'xor(x, y)' = " + formula);
      n = ASTNode.parseFormula(formula, l3Parser);       
    } catch (ParseException e) {
      // should never happen
      e.printStackTrace();
      assertTrue(false);
    }
  }

}
