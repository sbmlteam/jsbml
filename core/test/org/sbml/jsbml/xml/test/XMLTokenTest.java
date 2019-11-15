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

package org.sbml.jsbml.xml.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.InputStream;

import javax.xml.stream.XMLStreamException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.xml.XMLAttributes;
import org.sbml.jsbml.xml.XMLNode;
import org.sbml.jsbml.xml.XMLToken;
import org.sbml.jsbml.xml.XMLTriple;
import org.sbml.jsbml.xml.stax.SBMLReader;


/**
 * Tests the {@link XMLToken} class for a reported bug about
 * {@link StringBuilder} equals comparison.
 * 
 * @author Nicolas Rodriguez
 * @since 1.0
 */
public class XMLTokenTest {

  /**
   * 
   */
  @BeforeClass public static void initialSetUp() {}

  /**
   * 
   */
  @Before public void setUp() {}

  /**
   * 
   */
  @Test public void xmlTokenEqualsTest() {
    XMLToken token = new XMLNode("Test character XML token equals");
    assertTrue("The equals method between an object and it's clone should return true", token.equals(token.clone()));
  }

  /**
   * 
   * 
   * 
   */
  @Test public void documentEqualsTest() {
    InputStream fileStream = SBML_L2V1Test.class.getResourceAsStream("/org/sbml/jsbml/xml/test/data/l2v1/BIOMD0000000025.xml");
    SBMLDocument doc = null;
    try {
      doc = new SBMLReader().readSBMLFromStream(fileStream);
    } catch (XMLStreamException exc) {
      exc.printStackTrace();
      fail("There was an error reading a valid SBML model file.");
    }
    assertTrue("The equals method between an object and it's clone should return true", doc.equals(doc.clone()));
  }

  /**
   * Checks behaviour if the searched child is absent (including: the list of children is empty)
   */
  @Test public void test_XMLNode_indexOf_NotFound( ) {
  	XMLNode node = new XMLNode(new XMLTriple("name", "uri", "prefix"));
  	assertEquals(-1, node.indexOf("name")); // this is the name of the parent, NOT the immediate child!
  	assertEquals(-1, node.indexOf(new XMLNode(new XMLTriple("name", "uri", "prefix"))));
  	node.addChild(new XMLNode(new XMLTriple("child1", null, null)));
  	assertEquals(-1, node.indexOf("notTheChild"));
  	assertEquals(-1, node.indexOf(new XMLNode(new XMLTriple("notTheChild", "uri", "prefix"))));
  }
  
  /**
   * Checks general behaviour of {@link XMLNode#indexOf(String)}/{@link XMLNode#indexOf(XMLNode)}
   */
  @Test public void test_XMLNode_indexOf() {
  	XMLNode node = new XMLNode(new XMLTriple("name", "uri", "prefix"));
  	node.addChild(new XMLNode(new XMLTriple("child1", null, null)));
  	node.addChild(new XMLNode(new XMLTriple("child2", null, null)));
  	node.addChild(new XMLNode(new XMLTriple("child1", null, null)));
  	node.addChild(new XMLNode(new XMLTriple("child3", null, null)));
  	
  	assertEquals(0, node.indexOf("child1"));
  	assertEquals("child2", node.getChildAt(node.indexOf("child2")).getName());
  	assertEquals("child3", node.getChildAt(node.indexOf(new XMLNode(new XMLTriple("child3", null, null)))).getName());
  	assertEquals(-1, node.indexOf("notAChild"));
  }
  
  /**
   * Checks behaviour for special names (null, "", "*"): Do not search, these elements are 
   * considered 'not present'
   */
  @Test public void test_XMLNode_indexOf_unspecificName() {
  	XMLNode node = new XMLNode(new XMLTriple("name", "uri", "prefix"));
  	node.addChild(new XMLNode(new XMLTriple("child1", null, null)));
  	node.addChild(new XMLNode(new XMLTriple("child2", null, null)));
  	assertEquals(-1, node.indexOf((String) null));
  	assertEquals(-1, node.indexOf(""));
  	assertEquals(-1, node.indexOf("*"));
  }
  
  /**
   * Checks behaviour for an XMLNode that is an Element
   */
  @Test public void test_XMLNode_toString_IsElement() {
  	XMLAttributes attributes = new XMLAttributes();
  	attributes.add("attr1", "something");
  	attributes.add("attr2", "false");
  	attributes.add("attr3", "10");
  	XMLNode node = new XMLNode(new XMLTriple("testNode", "uri", "prefix"), attributes);
  	assertEquals("XMLNode 'testNode' [attr1=\"something\", attr2=\"false\", attr3=\"10\"]", node.toString());
  	
  	node.addChild(new XMLNode());
  	assertEquals("XMLNode 'testNode' [attr1=\"something\", attr2=\"false\", attr3=\"10\", childElements size=1]", node.toString());
  }
  
  /**
   * Checks behaviour for an XMLNode that is just text
   */
  @Test public void test_XMLNode_toString_IsText() {
  	String content = "some content within the node";
  	XMLNode node = new XMLNode(content);
  	assertEquals("XMLNode [characters=" + content + "]", node.toString());
  	
  	// TODO: Can currently add Children to Textnodes, which are then not shown in the toString.
  	node.addChild(new XMLNode());
  	assertEquals("XMLNode [characters=" + content + "]", node.toString());
  }
}
