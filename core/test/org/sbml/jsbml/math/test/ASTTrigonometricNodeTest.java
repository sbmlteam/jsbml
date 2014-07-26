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
import org.sbml.jsbml.math.ASTTrigonometricNode;


/**
 * Test cases for {@link ASTTrigonometricNode}
 * 
 * @author Victor Kofia
 * @version $Rev$
 * @since 1.0
 * @date Jul 24, 2014
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
   * Test method for {@link org.sbml.jsbml.math.ASTTrigonometricNode#isAllowableType()}.
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
  
}
