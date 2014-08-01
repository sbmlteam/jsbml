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

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.math.ASTCiNumberNode;


/**
 * Test cases for {@link ASTCiNumberNode}
 * 
 * @author Victor Kofia
 * @version $Rev$
 * @since 1.0
 * @date Jul 30, 2014
 */
public class ASTCiNumberNodeTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCiNumberNode#clone()}.
   */
  @Test
  public final void testClone() {
    ASTCiNumberNode ci = new ASTCiNumberNode();
    ASTCiNumberNode unknown = ci.clone();
    assertTrue(ci.equals(unknown));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCiNumberNode#ASTCiNumberNode(org.sbml.jsbml.math.ASTCiNumberNode)}.
   */
  @Test
  public final void testCloneWithConstructor() {
    ASTCiNumberNode ci = new ASTCiNumberNode();
    ASTCiNumberNode unknown = new ASTCiNumberNode(ci);
    assertTrue(ci.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCiNumberNode#getDefinitionURL()}.
   */
  @Test
  public final void testGetDefinitionURLExists() {
    ASTCiNumberNode ci = new ASTCiNumberNode();
    String url = "http://some-url.com";
    ci.setDefinitionURL(url);
    assertTrue(ci.getDefinitionURL().equals(url));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCiNumberNode#getDefinitionURL()}.
   */
  @Test
  public final void testGetDefinitionURLNonExistent() {
    ASTCiNumberNode ci = new ASTCiNumberNode();
    exception.expect(PropertyUndefinedError.class);
    ci.getDefinitionURL();
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCiNumberNode#getDefinitionURL()}.
   */
  @Test
  public final void testGetDefinitionURLNonExistentNonStrict() {
    ASTCiNumberNode ci = new ASTCiNumberNode();
    ci.setStrictness(false);
    assertTrue(ci.getDefinitionURL().isEmpty());
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCiNumberNode#getRefId()}.
   */
  @Test
  public final void testGetRefIdExists() {
    ASTCiNumberNode ci = new ASTCiNumberNode();
    String reference = "reference";
    ci.setRefId(reference);
    assertTrue(ci.getRefId().equals(reference));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCiNumberNode#getRefId()}.
   */
  @Test
  public final void testGetRefIdNonExistent() {
    ASTCiNumberNode ci = new ASTCiNumberNode();
    exception.expect(PropertyUndefinedError.class);
    ci.getRefId();
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCiNumberNode#getRefId()}.
   */
  @Test
  public final void testGetRefIdNonExistentNonStrict() {
    ASTCiNumberNode ci = new ASTCiNumberNode();
    ci.setStrictness(false);
    assertTrue(ci.getRefId().isEmpty());
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCiNumberNode#getType()}.
   */
  @Test
  public final void testGetType() {
    ASTCiNumberNode ci = new ASTCiNumberNode();
    assertTrue(ci.getType() == Type.NAME);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCiNumberNode#isSetType()}.
   */
  @Test
  public final void testIsSetType() {
    ASTCiNumberNode ci = new ASTCiNumberNode();
    assertTrue(ci.isSetType());
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCiNumberNode#replaceArgument()}.
   */
  @Test
  public final void testReplaceArgument() {
    assertTrue(true);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCiNumberNode#setType()}.
   */
  @Test
  public final void testSetTypeAllowed() {
    ASTCiNumberNode ci = new ASTCiNumberNode();
    ci.setType(Type.NAME);
    assertTrue(ci.getType() == Type.NAME);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCiNumberNode#setType()}.
   */
  @Test
  public final void testSetTypeNotAllowed() {
    ASTCiNumberNode ci = new ASTCiNumberNode();
    exception.expect(IllegalArgumentException.class);
    ci.setType(Type.UNKNOWN);
  }
  
}
