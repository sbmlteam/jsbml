/*
 *
 * @file    TestL3Layout.java
 * @brief   L3 Layout package unit tests
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
package org.sbml.jsbml.ext.layout.test;


import javax.xml.stream.XMLStreamException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBMLWriter;
import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.ext.layout.LayoutModelPlugin;
import org.sbml.jsbml.ext.layout.SpeciesGlyph;

/**
 * @author Nicolas Rodriguez
 * @since 1.0
 */
public class TestL3Layout {

  /**
   * 
   */
  public static String DATA_PATH = "/org/sbml/jsbml/xml/test/data/layout";
  /**
   * 
   */
  public static String LAYOUT_NAMESPACE = "http://www.sbml.org/sbml/level3/version1/layout/version1";


  /**
   * 
   * @param x
   * @return
   */
  public boolean isNaN(double x)
  {
    return Double.isNaN(x);
  }

  /**
   * 
   * @throws Exception
   */
  @Before public void setUp() throws Exception
  {
  }

  /**
   * 
   * @throws Exception
   */
  @After public void tearDown() throws Exception
  {
  }

  /**
   * 
   */
  @Test public void test_L3_Layout_read1()
  {
    String filePath = DATA_PATH + "/GlycolysisLayout_small.xml";

    SBMLDocument doc = null;
    try {
      doc = SBMLReader.read(TestL3Layout.class.getResourceAsStream(filePath));
    } catch (XMLStreamException e) {
      // should never happen
      e.printStackTrace();
      Assert.fail();
    }
    
    Assert.assertNotNull(doc);
    
    Model model = doc.getModel();

    System.out.println("Model extension objects: " + model.getExtension(LAYOUT_NAMESPACE));
    LayoutModelPlugin extendedModel = (LayoutModelPlugin) model.getExtension(LAYOUT_NAMESPACE);

    System.out.println("Nb Layouts = " + extendedModel.getListOfLayouts().size());

    Layout layout = extendedModel.getLayout(0);

    // System.out.println("Group sboTerm, id = " + group.getSBOTermID() + ", " + group.getId()); print dimension
    System.out.println("Nb SpeciesGlyphs = " + layout.getListOfSpeciesGlyphs().size());
    Assert.assertTrue(layout.getListOfSpeciesGlyphs().size() == 5);
    
    SpeciesGlyph  speciesGlyph = layout.getSpeciesGlyph(0);
    Assert.assertTrue(speciesGlyph.getId().equals("glyph_Gluc"));
    Assert.assertTrue(speciesGlyph.getSpecies().equals("Glucose"));
    Assert.assertTrue(model.findNamedSBase("glyph_Gluc") != null);

    layout.createGeneralGlyph("LGG1");
    
    // System.out.println("Member(0).symbol = " + member.getSymbol());

    try {
      System.out.println(new SBMLWriter().writeSBMLToString(doc));
    } catch (SBMLException e) {
      // should never happen
      e.printStackTrace();
      Assert.fail();
    } catch (XMLStreamException e) {
      // should never happen
      e.printStackTrace();
      Assert.fail();
    }
    
    SBMLDocument doc2 = new SBMLDocument(3, 1);
    Model m2 = new Model(doc.getModel());
    doc2.setModel(m2);

    try {
      System.out.println(new SBMLWriter().writeSBMLToString(doc2));
    } catch (SBMLException e) {
      // should never happen
      e.printStackTrace();
      Assert.fail();
    } catch (XMLStreamException e) {
      // should never happen
      e.printStackTrace();
      Assert.fail();
    }

  }

//  /**
//   * 
//   */
//  @Test public void test_L3_Layout_write1()
//  {
//    String filePath = DATA_PATH + "/GlycolysisLayout_small.xml";
//
//    SBMLDocument doc = null;
//    try {
//      doc = SBMLReader.read(TestL3Layout.class.getResourceAsStream(filePath));
//    } catch (XMLStreamException e) {
//      // should never happen
//      e.printStackTrace();
//      Assert.fail();
//    }
//
//    Assert.assertNotNull(doc);
//    
//    try {
//      System.out.println(new SBMLWriter().writeSBMLToString(doc));
//    } catch (SBMLException e) {
//      // should never happen
//      e.printStackTrace();
//      Assert.fail();
//    } catch (XMLStreamException e) {
//      // should never happen
//      e.printStackTrace();
//      Assert.fail();
//    }
//    
//  }
}
