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

import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.math.ASTCSymbolTimeNode;
import org.sbml.jsbml.math.ASTFactory;


/**
 * Test cases for {@link ASTCSymbolTimeNode}
 * 
 * @author Victor Kofia
 * @since 1.0
 */
public class ASTCSymbolTimeNodeTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCSymbolTimeNode#clone()}.
   */
  @Test
  public final void testClone() {
    ASTCSymbolTimeNode time = new ASTCSymbolTimeNode();
    ASTCSymbolTimeNode unknown = time.clone();
    assertTrue(time.equals(unknown));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCSymbolTimeNode#ASTCSymbolTimeNode(org.sbml.jsbml.math.ASTCSymbolTimeNode)}.
   */
  @Test
  public final void testCloneWithConstructor() {
    ASTCSymbolTimeNode time = new ASTCSymbolTimeNode();
    ASTCSymbolTimeNode unknown = new ASTCSymbolTimeNode(time);
    assertTrue(time.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCSymbolTimeNode#getDefinitionURL()}.
   */
  @Test
  public final void testGetDefinitionURL() {
    ASTCSymbolTimeNode time = new ASTCSymbolTimeNode();
    assertTrue(time.getDefinitionURL().equals(ASTNode.URI_TIME_DEFINITION));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCSymbolTimeNode#getEncoding()}.
   */
  @Test
  public final void testGetEncodingURL() {
    ASTCSymbolTimeNode time = new ASTCSymbolTimeNode();
    assertTrue(time.getEncoding().equals("text"));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCSymbolTimeNode#getName()}.
   */
  @Test
  public final void testGetName() {
    ASTCSymbolTimeNode time = new ASTCSymbolTimeNode();
    time.setName("name");
    assertTrue(time.getName().equals("name"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCSymbolTimeNode#getName()}.
   */
  @Test
  public final void testGetNameNonExistent() {
    ASTCSymbolTimeNode time = new ASTCSymbolTimeNode();
    exception.expect(PropertyUndefinedError.class);
    time.getName();
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCSymbolTimeNode#getName()}.
   */
  @Test
  public final void testGetNameNonExistentNonStrict() {
    ASTCSymbolTimeNode time = new ASTCSymbolTimeNode();
    time.setStrictness(false);
    assertTrue(time.getName().isEmpty());
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCSymbolTimeNode#isAllowableType(org.sbml.jsbml.ASTNode.Type)}.
   */
  @Test
  public final void testIsAllowableType() {
    ASTCSymbolTimeNode time = new ASTCSymbolTimeNode();
    assertTrue(time.isAllowableType(Type.NAME_TIME) && !time.isAllowableType(null));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCSymbolTimeNode#isSetDefinitionURL()}.
   */
  @Test
  public final void testIsSetDefinitionURL() {
    ASTCSymbolTimeNode time = new ASTCSymbolTimeNode();
    assertTrue(time.isSetDefinitionURL());
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCSymbolTimeNode#isSetEncoding()}.
   */
  @Test
  public final void testIsSetEncodingURL() {
    ASTCSymbolTimeNode time = new ASTCSymbolTimeNode();
    assertTrue(time.isSetEncoding());
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCSymbolTimeNode#isSetName()}.
   */
  @Test
  public final void testIsSetNameTrue() {
    ASTCSymbolTimeNode time = new ASTCSymbolTimeNode();
    time.setName("csymbol-time");
    assertTrue(time.isSetName());
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCSymbolTimeNode#isSetName()}.
   */
  @Test
  public final void testIsSetNameFalse() {
    ASTCSymbolTimeNode time = new ASTCSymbolTimeNode();
    assertFalse(time.isSetName());
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCSymbolTimeNode#toFormula()}.
   */
  @Test
  public final void testToFormula() {
    ASTCSymbolTimeNode time = new ASTCSymbolTimeNode();
    assertTrue(time.toFormula().equals("time"));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCSymbolTimeNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeX() {
    ASTCSymbolTimeNode time = new ASTCSymbolTimeNode();
    assertTrue(time.toLaTeX().equals("\\mathrm{time}"));
  }  
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCSymbolTimeNode#toMathML()}.
   */
  @Test
  public final void testToMathML() {
    ASTCSymbolTimeNode time = new ASTCSymbolTimeNode();
    assertTrue(time.toMathML().equals(ASTFactory.parseMathML("csymbol-time.xml")));
  }  
  
}
