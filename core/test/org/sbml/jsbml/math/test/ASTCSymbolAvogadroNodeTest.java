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

import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.math.ASTCSymbolAvogadroNode;
import org.sbml.jsbml.math.ASTFactory;


/**
 * Test cases for {@link ASTCSymbolAvogadroNode}
 * 
 * @author Victor Kofia
 * @since 1.0
 */
public class ASTCSymbolAvogadroNodeTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCSymbolAvogadroNode#clone()}.
   */
  @Test
  public final void testClone() {
    ASTCSymbolAvogadroNode avogadro = new ASTCSymbolAvogadroNode();
    ASTCSymbolAvogadroNode unknown = avogadro.clone();
    assertTrue(avogadro.equals(unknown));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCSymbolAvogadroNode#ASTCSymbolAvogadroNode(org.sbml.jsbml.math.ASTCSymbolAvogadroNode)}.
   */
  @Test
  public final void testCloneWithConstructor() {
    ASTCSymbolAvogadroNode avogadro = new ASTCSymbolAvogadroNode();
    ASTCSymbolAvogadroNode unknown = new ASTCSymbolAvogadroNode(avogadro);
    assertTrue(avogadro.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCSymbolAvogadroNode#getDefinitionURL()}.
   */
  @Test
  public final void testGetDefinitionURL() {
    ASTCSymbolAvogadroNode avogadro = new ASTCSymbolAvogadroNode();
    assertTrue(avogadro.getDefinitionURL().equals(ASTNode.URI_AVOGADRO_DEFINITION));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCSymbolAvogadroNode#getEncoding()}.
   */
  @Test
  public final void testGetEncodingURL() {
    ASTCSymbolAvogadroNode avogadro = new ASTCSymbolAvogadroNode();
    assertTrue(avogadro.getEncoding().equals("text"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCSymbolAvogadroNode#getName()}.
   */
  @Test
  public final void testGetName() {
    ASTCSymbolAvogadroNode avogadro = new ASTCSymbolAvogadroNode();
    avogadro.setName("name");
    assertTrue(avogadro.getName().equals("name"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCSymbolAvogadroNode#getName()}.
   */
  @Test
  public final void testGetNameNonExistent() {
    ASTCSymbolAvogadroNode avogadro = new ASTCSymbolAvogadroNode();
    exception.expect(PropertyUndefinedError.class);
    avogadro.getName();
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCSymbolAvogadroNode#getName()}.
   */
  @Test
  public final void testGetNameNonExistentNonStrict() {
    ASTCSymbolAvogadroNode avogadro = new ASTCSymbolAvogadroNode();
    avogadro.setStrictness(false);
    assertTrue(avogadro.getName().isEmpty());
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCSymbolAvogadroNode#getType()}.
   */
  @Test
  public final void testGetType() {
    ASTCSymbolAvogadroNode avogadro = new ASTCSymbolAvogadroNode();
    assertTrue(avogadro.getType() == Type.NAME_AVOGADRO);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCSymbolAvogadroNode#isAllowableType(org.sbml.jsbml.ASTNode.Type)}.
   */
  @Test
  public final void testIsAllowableType() {
    ASTCSymbolAvogadroNode avogadro = new ASTCSymbolAvogadroNode();
    assertTrue(avogadro.isAllowableType(Type.NAME_AVOGADRO) && !avogadro.isAllowableType(null));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCSymbolAvogadroNode#isSetDefinitionURL()}.
   */
  @Test
  public final void testIsSetDefinitionURL() {
    ASTCSymbolAvogadroNode avogadro = new ASTCSymbolAvogadroNode();
    assertTrue(avogadro.isSetDefinitionURL());
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCSymbolAvogadroNode#isSetEncoding()}.
   */
  @Test
  public final void testIsSetEncodingURL() {
    ASTCSymbolAvogadroNode avogadro = new ASTCSymbolAvogadroNode();
    assertTrue(avogadro.isSetEncoding());
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCSymbolAvogadroNode#toFormula()}.
   */
  @Test
  public final void testToFormula() {
    ASTCSymbolAvogadroNode avogadro = new ASTCSymbolAvogadroNode();
    assertTrue(avogadro.toFormula().equals("avogadro"));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCSymbolAvogadroNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeX() {
    ASTCSymbolAvogadroNode avogadro = new ASTCSymbolAvogadroNode();
    assertTrue(avogadro.toLaTeX().equals("avogadro"));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCSymbolAvogadroNode#toMathML()}.
   */
  @Test
  public final void testToMathML() {
    ASTCSymbolAvogadroNode avogadro = new ASTCSymbolAvogadroNode();
    assertTrue(avogadro.toMathML().equals(ASTFactory.parseMathML("csymbol-avogadro.xml")));
  }

}
