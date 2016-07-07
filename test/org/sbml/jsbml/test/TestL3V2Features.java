/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2016 jointly by the following organizations:
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

import static org.junit.Assert.assertTrue;

import javax.xml.stream.XMLStreamException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sbml.jsbml.CVTerm;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBMLWriter;
import org.sbml.jsbml.ext.comp.CompSBMLDocumentPlugin;

/**
 * Tests the new feature introduced in SBML L3V2.
 * 
 * @author Nicolas Rodriguez
 * @since 1.2
 */
public class TestL3V2Features {

  /**
   * 
   */
  private SBMLDocument docWithoutModel;
  /**
   * 
   */
  private SBMLDocument doc;
  /**
   * 
   */
  private Model model;


  /**
   * 
   */
  @BeforeClass public static void initialSetUp() {}

  /**
   * 
   */
  @Before public void setUp() {
    docWithoutModel = new SBMLDocument(3, 2);
    
    CompSBMLDocumentPlugin compDoc = (CompSBMLDocumentPlugin) docWithoutModel.getPlugin("comp");
    compDoc.createModelDefinition("md1").setName("model definition 1");;
    
    doc = new SBMLDocument(3, 2);
    model = doc.createModel();
  }

  /**
   * 
   */
  @Test public void modelIsOptional() {

    assertTrue(docWithoutModel.getLevel() == 3 && docWithoutModel.getVersion() == 2);

    assertTrue(docWithoutModel.getModel() == null);
    
    String docString = null;
    try {
      docString = new SBMLWriter().writeSBMLToString(docWithoutModel);
    } catch (SBMLException e) {
      assertTrue(false);
    } catch (XMLStreamException e) {
      assertTrue(false);
    }

    System.out.println("Doc without Model: \n" + docString);
    SBMLDocument newDoc = null;
    try {
      newDoc = new SBMLReader().readSBMLFromString(docString);
    } catch (XMLStreamException e) {
      assertTrue(false);
    }
    
    assertTrue(newDoc.getLevel() == 3 && newDoc.getVersion() == 2);

    assertTrue(newDoc.getModel() == null);
    
    
  }

  /**
   * Writes an {@link SBMLDocument} to a String then reads it back and checks that
   * the {@link CVTerm}s are the same. Tests as well the {@link CVTerm#removeFromParent()} method.
   */
  @Test public void cvTermTest2() {

    String docString = null;
    try {
      docString = new SBMLWriter().writeSBMLToString(doc);
    } catch (SBMLException e) {
      assertTrue(false);
    } catch (XMLStreamException e) {
      assertTrue(false);
    }

    try {
      doc = new SBMLReader().readSBMLFromString(docString);
    } catch (XMLStreamException e) {
      assertTrue(false);
    }
    model = doc.getModel();

    assertTrue(doc.getLevel() == 3 && doc.getVersion() == 2);
    assertTrue(model.getLevel() == 3 && model.getVersion() == 2);


  }


}
