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
import org.sbml.jsbml.math.ASTCSymbolDelayNode;


/**
 * Test cases for {@link ASTCSymbolDelayNode}
 * 
 * @author Victor Kofia
 * @version $Rev$
 * @since 1.0
 * @date Jul 25, 2014
 */
public class ASTCSymbolDelayNodeTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();


  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCSymbolDelayNode#clone()}.
   */
  @Test
  public final void testClone() {
    ASTCSymbolDelayNode delay = new ASTCSymbolDelayNode();
    ASTCSymbolDelayNode unknown = delay.clone();
    assertTrue(delay.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCSymbolDelayNode#ASTCSymbolDelayNode(org.sbml.jsbml.math.ASTCSymbolDelayNode)}.
   */
  @Test
  public final void testCloneWithConstructor() {
    ASTCSymbolDelayNode delay = new ASTCSymbolDelayNode();
    ASTCSymbolDelayNode unknown = new ASTCSymbolDelayNode(delay);
    assertTrue(delay.equals(unknown));
  }


  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCSymbolDelayNode#getDefinitionURL()}.
   */
  @Test
  public final void testGetDefinitionURL() {
    ASTCSymbolDelayNode delay = new ASTCSymbolDelayNode();
    delay.setDefinitionURL("definitionURL");
    assertTrue(delay.getDefinitionURL().equals("definitionURL"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCSymbolDelayNode#getDefinitionURL()}.
   */
  @Test
  public final void testGetDefinitionURLNonExistent() {
    ASTCSymbolDelayNode delay = new ASTCSymbolDelayNode();
    exception.expect(PropertyUndefinedError.class);
    delay.getDefinitionURL();
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCSymbolDelayNode#getDefinitionURL()}.
   */
  @Test
  public final void testGetDefinitionURLNonExistentNonStrict() {
    ASTCSymbolDelayNode delay = new ASTCSymbolDelayNode();
    delay.setStrictness(false);
    assertTrue(delay.getDefinitionURL().length() == 0);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCSymbolDelayNode#getEncodingURL()}.
   */
  @Test
  public final void testGetEncodingURL() {
    ASTCSymbolDelayNode delay = new ASTCSymbolDelayNode();
    delay.setEncodingURL("encoding");
    assertTrue(delay.getEncodingURL().equals("encoding"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCSymbolDelayNode#getEncodingURL()}.
   */
  @Test
  public final void testGetEncodingURLNonExistent() {
    ASTCSymbolDelayNode delay = new ASTCSymbolDelayNode();
    exception.expect(PropertyUndefinedError.class);
    delay.getEncodingURL();
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCSymbolDelayNode#getEncodingURL()}.
   */
  @Test
  public final void testGetEncodingURLNonExistentNonStrict() {
    ASTCSymbolDelayNode delay = new ASTCSymbolDelayNode();
    delay.setStrictness(false);
    assertTrue(delay.getEncodingURL().length() == 0);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCSymbolDelayNode#isAllowableType(org.sbml.jsbml.ASTNode.Type)}.
   */
  @Test
  public final void testIsAllowableType() {
    ASTCSymbolDelayNode delay = new ASTCSymbolDelayNode();
    assertTrue(delay.isAllowableType(Type.FUNCTION_DELAY) && !delay.isAllowableType(null));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCSymbolDelayNode#isSetDefinitionURL()}.
   */
  @Test
  public final void testIsSetDefinitionURL() {
    ASTCSymbolDelayNode delay = new ASTCSymbolDelayNode();
    delay.setDefinitionURL("definitionURL");
    assertTrue(delay.isSetDefinitionURL());
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCSymbolDelayNode#isSetEncodingURL()}.
   */
  @Test
  public final void testIsSetEncodingURL() {
    ASTCSymbolDelayNode delay = new ASTCSymbolDelayNode();
    delay.setEncodingURL("encoding");
    assertTrue(delay.isSetEncodingURL());
  }
}
