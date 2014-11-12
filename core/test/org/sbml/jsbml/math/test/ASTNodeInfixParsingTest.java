/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2014 jointly by the following organizations:
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
import org.sbml.jsbml.math.parser.FormulaParserLL3;
import org.sbml.jsbml.math.parser.ParseException;

public class ASTNodeInfixParsingTest {
  
  final static FormulaParserLL3 caseSensitiveParser = new FormulaParserLL3(new StringReader(""));
  final static FormulaParserLL3 caseInsensitiveParser = new FormulaParserLL3(new StringReader(""));
  
  @BeforeClass public static void init() {
    caseInsensitiveParser.setCaseSensitive(false);
  }
  
  @Test public void caseSensitivityTests() {
    
    try {
      ASTNode n = ASTNode.parseFormula("Cos(x)", caseSensitiveParser);

      assertTrue(n.getType() != ASTNode.Type.FUNCTION_COS);
      assertTrue(n.getType() == ASTNode.Type.FUNCTION);
      assertTrue(n.getName().equals("Cos"));
      
      n = ASTNode.parseFormula("1 + Pi", caseSensitiveParser);
      
      assertTrue(n.getChild(1).getType() != ASTNode.Type.CONSTANT_PI);
      assertTrue(n.getChild(1).getType() == ASTNode.Type.NAME);
      assertTrue(n.getChild(1).getName().equals("Pi"));
      
      
    } catch (ParseException e) {
      // should never happen
      e.printStackTrace();
      assertTrue(false);
    } 
  }
  
  @Test public void caseInsensitivityTests() {
    
    try {
      ASTNode n = ASTNode.parseFormula("Cos(x)", caseInsensitiveParser);

      assertTrue(n.getType() == ASTNode.Type.FUNCTION_COS);
      assertTrue(n.isFunction() == true);
      assertTrue(n.isName() == false);
      assertTrue(n.getName() == null); // TODO - should the original String be conserved and accessible through getName()
      assertTrue(n.toFormula().equals("cos(x)"));

      n = ASTNode.parseFormula("COS(x)", caseInsensitiveParser);

      assertTrue(n.getType() == ASTNode.Type.FUNCTION_COS);
      assertTrue(n.toFormula().equals("cos(x)"));
      
      n = ASTNode.parseFormula("cos(x)", caseInsensitiveParser);

      assertTrue(n.getType() == ASTNode.Type.FUNCTION_COS);
      assertTrue(n.toFormula().equals("cos(x)"));

      n = ASTNode.parseFormula("coS(x)", caseInsensitiveParser);

      assertTrue(n.getType() == ASTNode.Type.FUNCTION_COS);
      assertTrue(n.toFormula().equals("cos(x)"));

      
      n = ASTNode.parseFormula("1 + Pi", caseInsensitiveParser);
      
      assertTrue(n.getChild(1).getType() == ASTNode.Type.CONSTANT_PI);
      assertTrue(n.getChild(1).getName() == null);  // TODO - should the original String be conserved and accessible through getName()
      assertTrue(n.toFormula().equals("1+pi"));
      
      
    } catch (ParseException e) {
      // should never happen
      e.printStackTrace();
      assertTrue(false);
    } 
  }

}
