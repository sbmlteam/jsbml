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

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.InputStream;

import javax.xml.stream.XMLStreamException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.xml.XMLNode;
import org.sbml.jsbml.xml.XMLToken;
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

}
