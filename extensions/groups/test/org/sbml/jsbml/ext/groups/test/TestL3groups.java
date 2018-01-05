/*
 *
 * @file    TestL3Group.java
 * @brief   L3 Groups package unit tests
 *
 * @author  Nicolas Rodriguez (JSBML conversion)
 * @author  Akiya Jouraku (Java conversion)
 * @author  Sarah Keating
 *
 * This test file was converted from libsbml http://sbml.org/software/libsbml
 *
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
package org.sbml.jsbml.ext.groups.test;

import java.io.InputStream;

import javax.xml.stream.XMLStreamException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.ext.groups.Group;
import org.sbml.jsbml.ext.groups.GroupsModelPlugin;
import org.sbml.jsbml.ext.groups.Member;
import org.sbml.jsbml.xml.stax.SBMLReader;
import org.sbml.jsbml.xml.stax.SBMLWriter;

/**
 * @author Nicolas Rodriguez
 * @since 1.0
 */
public class TestL3groups {

  /**
   * 
   */
  public static String GROUPS_NAMESPACE = "http://www.sbml.org/sbml/level3/version1/groups/version1";


  /**
   * @param x
   * @return
   */
  public boolean isNaN(double x) {
    return Double.isNaN(x);
  }

  /**
   * @throws Exception
   */
  @Before
  public void setUp() throws Exception {
  }

  /**
   * @throws Exception
   */
  @After
  public void tearDown() throws Exception {
  }

  /**
   * @throws XMLStreamException
   */
  @Test
  public void test_L3_Groups_read1() throws XMLStreamException {
    InputStream fileStream = TestL3groups.class.getResourceAsStream("/org/sbml/jsbml/test/data/groups/groups1.xml");
    SBMLDocument doc = new SBMLReader().readSBMLFromStream(fileStream);
    Model model = doc.getModel();

    System.out.println("Model extension objects: "
        + model.getExtension(GROUPS_NAMESPACE));
    GroupsModelPlugin extendedModel = (GroupsModelPlugin) model
        .getExtension(GROUPS_NAMESPACE);

    System.out.println("Nb Groups = "
        + extendedModel.getListOfGroups().size());

    Group group = extendedModel.getGroup(0);

    System.out.println("Group sboTerm, id = " + group.getSBOTermID() + ", "
        + group.getId());
    System.out.println("Nb Members = " + group.getListOfMembers().size());

    Member member = group.getMember(0);

    System.out.println("Member(0).idRef = " + member.getIdRef());

  }

  /**
   * @throws XMLStreamException
   */
  @Test
  public void test_L3_Groups_write1() throws XMLStreamException {
    InputStream fileStream = TestL3groups.class.getResourceAsStream("/org/sbml/jsbml/test/data/groups/groups1.xml");
    SBMLDocument doc = new SBMLReader().readSBMLFromStream(fileStream);

    /*String docStr =*/ new SBMLWriter().writeSBMLToString(doc);

    // TODO - do some extra tests on the written file


  }
}
