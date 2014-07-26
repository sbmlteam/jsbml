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

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.math.ASTCnIntegerNode;
import org.sbml.jsbml.math.ASTHyperbolicNode;


/**
 * Test cases for {@link ASTHyperbolicNode}
 * 
 * @author Victor Kofia
 * @version $Rev$
 * @since 1.0
 * @date Jul 25, 2014
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
    sinh.addChild(new ASTCnIntegerNode(5));
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
            && sinh.isAllowableType(Type.FUNCTION_ARCCSCH) && sinh.isAllowableType(Type.FUNCTION_ARCCSCH)
            && sinh.isAllowableType(Type.FUNCTION_ARCSECH) && sinh.isAllowableType(Type.FUNCTION_ARCSINH)
            && sinh.isAllowableType(Type.FUNCTION_ARCTANH) && sinh.isAllowableType(Type.FUNCTION_COSH)
            && sinh.isAllowableType(Type.FUNCTION_COTH) && sinh.isAllowableType(Type.FUNCTION_CSCH)
            && sinh.isAllowableType(Type.FUNCTION_SECH) && sinh.isAllowableType(Type.FUNCTION_SINH)
            && sinh.isAllowableType(Type.FUNCTION_TANH) && !sinh.isAllowableType(null));
  }
  
}
