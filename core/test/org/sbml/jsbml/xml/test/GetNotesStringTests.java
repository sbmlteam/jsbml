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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.stream.XMLStreamException;

import org.junit.Before;
import org.junit.Test;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLReader;

/**
 * 
 * @author Nicolas Rodriguez
 */
public class GetNotesStringTests {

  /**
   * 
   */
  @Before public void setUp() {
  }

  /**
   * Tries to use getNotesString on a model without a notes element.
   * @throws IOException
   * @throws XMLStreamException
   */
  @Test public void getNotesStringOnModel() throws IOException, XMLStreamException {
    InputStream fileStream = GetNotesStringTests.class.getResourceAsStream("/org/sbml/jsbml/xml/test/data/libsbml-test-data/l1v1-minimal.xml");
    SBMLDocument doc = new SBMLReader().readSBMLFromStream(fileStream);

    assertNotNull(doc.getModel().getNotesString());
    assertTrue(doc.getModel().getNotesString() == "");
  }

}
