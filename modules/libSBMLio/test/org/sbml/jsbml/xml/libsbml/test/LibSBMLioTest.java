/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2015 jointly by the following organizations:
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
package org.sbml.jsbml.xml.libsbml.test;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.xml.libsbml.LibSBMLReader;

/**
 * @author Andreas Dr&auml;ger
 * @date 2010-12-01
 * @version $Rev$
 */
public class LibSBMLioTest {

  /**
   * 
   */
  @BeforeClass
  public static void beforeTesting() {
    try {
      // Load LibSBML:
      System.loadLibrary("sbmlj");
      // Extra check to be sure we have access to libSBML:
      Class.forName("org.sbml.libsbml.libsbml");
    } catch (Throwable exc) {
      exc.printStackTrace();
      System.exit(1);
    }
  }

  /**
   * 
   * @throws Exception
   */
  @Test
  public void sbmlReadingTest() throws Exception {
    String files[] = new String[] {
      "../../xml/test/data/l2v1/BIOMD0000000025.xml",
      "../../xml/test/data/l2v1/BIOMD0000000227.xml",
      "../../xml/test/data/l2v3/BIOMD0000000191.xml",
      //      "../xml/test/data/l2v4/BIOMD0000000228.xml", // Does not work because of differences in XML nodes.
      "../../xml/test/data/l2v4/BIOMD0000000229.xml",
    };
    for (String file : files) {
      testReading(file);
    }
  }

  /**
   * The actual test.
   * @param fileName
   * @throws Exception
   */
  private void testReading(String fileName) throws Exception {
    File file = new File(LibSBMLioTest.class.getResource(fileName).toURI());
    org.sbml.libsbml.SBMLDocument libDoc = new org.sbml.libsbml.SBMLReader().readSBML(file.getAbsolutePath());
    LibSBMLReader libTranslator = new LibSBMLReader();
    SBMLDocument doc1 = SBMLReader.read(file);
    SBMLDocument doc2 = libTranslator.convertSBMLDocument(libDoc);

    Model model1 = doc1.getModel();
    Model model2 = doc2.getModel();

    //    for (int i = 0; i < model1.getSpeciesCount(); i++) {
    //      Species ud1 = model1.getSpecies(i);
    //      Species ud2 = model2.getSpecies(i);
    //      boolean equal = ud1.equals(ud2);
    //      System.out.println(i + ".\t" + ud1.toString() + ":\t" + equal);
    //      if (!equal) {
    //        equal = ud1.equals(ud2);
    //      }
    //      //assertEquals(ud1, ud2);
    //    }

    assertEquals(model1.getListOfUnitDefinitions(), model2.getListOfUnitDefinitions());
    assertEquals(model1.getListOfCompartments(), model2.getListOfCompartments());
    assertEquals(model1.getListOfSpecies(), model2.getListOfSpecies());
    assertEquals(model1.getListOfParameters(), model2.getListOfParameters());
    // All tests involving Math are likely to fail in case that there is a non-binary MathML structure in the test file:
    //    assertEquals(model1.getListOfReactions(), model2.getListOfReactions());


    assertEquals(model1.getListOfEvents(), model2.getListOfEvents());
    //
    //    assertEquals(model1, model2);
    //    assertEquals(doc1, doc2);
  }

}
