/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2022 jointly by the following organizations:
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

package org.sbml.jsbml.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLReader;

/**
 * Regression-style test related to issue #264: ensure that model annotations
 * containing nested layout elements do not cause a ClassCastException in the
 * StAX-based SBMLReader.
 */
public class SBMLReaderAnnotationCastTest {

  @Test
  public void layoutInModelAnnotationDoesNotThrow() throws Exception {
    String xml =
        "<sbml xmlns=\"http://www.sbml.org/sbml/level2/version4\" " +
        "      xmlns:layout=\"http://www.sbml.org/sbml/level3/version1/layout/version1\" " +
        "      level=\"2\" version=\"4\">" +
        "  <model id=\"m\">" +
        "    <annotation>" +
        "      <layout:listOfLayouts>" +
        "        <layout:layout id=\"L1\"/>" +
        "      </layout:listOfLayouts>" +
        "    </annotation>" +
        "  </model>" +
        "</sbml>";

    SBMLDocument doc = new SBMLReader().readSBMLFromString(xml);

    // If we reach here, no ClassCastException (or other exception) was thrown.
    assertNotNull(doc);
    assertNotNull(doc.getModel());
  }
}