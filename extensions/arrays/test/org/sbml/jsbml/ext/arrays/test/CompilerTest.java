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

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.ext.arrays.compiler.ArraysCompiler;
import org.sbml.jsbml.text.parser.ParseException;
import org.sbml.jsbml.util.compilers.ASTNodeValue;


/**
 * Test cases for the arrays compiler
 * 
 * @author Leandro Watanabe
 * @version $Rev$
 * @since 1.0
 * @date Jun 26, 2014
 */
public class CompilerTest {

  @Test
  public void arithTest() {
    try {
      ArraysCompiler compiler = new ArraysCompiler();
      
      ASTNode plus = ASTNode.parseFormula("1+2+3+4");
      ASTNodeValue result = plus.compile(compiler);
      assertTrue(result.isNumber());
      assertTrue(result.toInteger() == 10);
      
      ASTNode minus = ASTNode.parseFormula("5-3-1");
      result = minus.compile(compiler);
      assertTrue(result.isNumber());
      assertTrue(result.toInteger() == 1);
      
      ASTNode times = ASTNode.parseFormula("10*10*10");
      result = times.compile(compiler);
      assertTrue(result.isNumber());
      assertTrue(result.toInteger() == 1000);
      
      ASTNode divide = ASTNode.parseFormula("8/2/2");
      result = divide.compile(compiler);
      assertTrue(result.isNumber());
      assertTrue(result.toInteger() == 2);
      
    } catch (ParseException e) {
      assertTrue(false);
      e.printStackTrace();
    }
  }
  
  @Test
  public void inequalityTest() {
    try {
      ArraysCompiler compiler = new ArraysCompiler();
      
      ASTNode lt = ASTNode.parseFormula("6 < 3");
      ASTNodeValue result = lt.compile(compiler);
      assertTrue(result.isBoolean());
      assertTrue(!result.toBoolean());
      
      ASTNode leq = ASTNode.parseFormula("3 <= 3");
      result = leq.compile(compiler);
      assertTrue(result.isBoolean());
      assertTrue(result.toBoolean());
      
    } catch (ParseException e) {
      assertTrue(false);
      e.printStackTrace();
    }
  }
  
  @Test
  public void opsWithIdTest() {
    try {
      ArraysCompiler compiler = new ArraysCompiler();
      compiler.addValue("i", 3);
      ASTNode function = ASTNode.parseFormula("i*3-1");
      ASTNodeValue result = function.compile(compiler);
      assertTrue(result.isNumber());
      assertTrue(result.toInteger() == 8);

      
    } catch (ParseException e) {
      assertTrue(false);
      e.printStackTrace();
    }
  }
}
