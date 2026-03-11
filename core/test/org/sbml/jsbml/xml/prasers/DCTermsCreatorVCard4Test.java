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

package org.sbml.jsbml.xml.parsers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.InputStream;
import java.util.List;

import org.junit.Test;
import org.sbml.jsbml.Creator;
import org.sbml.jsbml.History;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLReader;

/**
 * Regression test for PR #274 / issue #270:
 * ensure that creators written using the dcterms:creator + vCard4 format
 * are parsed into Creator objects.
 */
public class DCTermsCreatorVCard4Test {

  private static final String RESOURCE = "dcterms-creator-vcard4-example.xml";

  @Test
  public void parsesCreatorsFromDCTermsVCard4() throws Exception {
    SBMLDocument doc;
    try (InputStream is = DCTermsCreatorVCard4Test.class.getResourceAsStream(RESOURCE)) {
      assertNotNull("Test SBML resource not found: " + RESOURCE, is);
      doc = new SBMLReader().readSBMLFromStream(is);
    }

    Model model = doc.getModel();
    assertNotNull(model);

    History history = model.getHistory();
    assertNotNull(history);

    List<Creator> creators = history.getListOfCreators();
    assertEquals(2, creators.size());

    Creator c1 = creators.get(0);
    Creator c2 = creators.get(1);

    // Check that vCard4 name and organization were parsed correctly
    assertEquals("Given1", c1.getGivenName());
    assertEquals("Family1", c1.getFamilyName());
    assertEquals("Org1", c1.getOrganisation());

    assertEquals("Given2", c2.getGivenName());
    assertEquals("Family2", c2.getFamilyName());
    assertEquals("Org2", c2.getOrganisation());
  }
}