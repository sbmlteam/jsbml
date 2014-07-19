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

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.math.ASTBoolean;


/**
 * Test cases for the ASTBoolean class
 * 
 * @author Victor Kofia
 * @version $Rev$
 * @since 1.0
 * @date Jul 18, 2014
 */
public class ASTBooleanTest {
  
  @Rule
  public ExpectedException exception = ExpectedException.none();

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTBoolean#clone()}.
   */
  @Test
  public final void testClone() {
    ASTBoolean node = new ASTBoolean(Type.CONSTANT_TRUE);
    node.setStyle("style");
    node.setId("id");
    node.setMathMLClass("class");
    ASTBoolean unknown = (ASTBoolean) node.clone();
    assertTrue(unknown.equals(node));
  }


  /**
   * Test method for {@link org.sbml.jsbml.math.ASTBoolean#ASTBoolean(org.sbml.jsbml.math.ASTBoolean)}.
   */
  @Test
  public final void testConstructorClone() {
    ASTBoolean node = new ASTBoolean(Type.CONSTANT_TRUE);
    node.setStyle("style");
    node.setId("id");
    node.setMathMLClass("class");
    ASTBoolean unknown = new ASTBoolean(node);
    assertTrue(unknown.equals(node));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTBoolean#ASTBoolean#getAllowsChildren()}.
   */
  @Test
  public final void testGetAllowsChildren() {
    ASTBoolean node = new ASTBoolean(Type.CONSTANT_TRUE);
    assertFalse(node.getAllowsChildren());
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTBoolean#ASTBoolean#getChildAt()}.
   */
  @Test
  public final void testGetChildAt() {
    ASTBoolean node = new ASTBoolean(Type.CONSTANT_TRUE);
    exception.expect(IndexOutOfBoundsException.class);
    node.getChildAt(0);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTBoolean#ASTBoolean#getAllowsChildren()}.
   */
  @Test
  public final void testGetChildCount() {
    ASTBoolean node = new ASTBoolean(Type.CONSTANT_TRUE);
    assertTrue(node.getChildCount() == 0);
  }


}
