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

package org.sbml.jsbml.ext.comp.test;

import static org.junit.Assert.assertTrue;

import javax.xml.stream.XMLStreamException;

import org.junit.Assert;
import org.junit.Test;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBMLWriter;
import org.sbml.jsbml.ext.comp.CompSBMLDocumentPlugin;
import org.sbml.jsbml.ext.comp.ModelDefinition;

/**
 *
 * @author Nicolas Rodriguez
 * @since 1.1
 */
public class TestModelDefinitionCloning {
  
  /**
   * Test if extension is successfully binding to SBase object
   */
  @Test
  public void testAddExtension() {

    SBMLDocument doc = new SBMLDocument(3, 1);
    doc.createModel("testModelDefinitionCloning");
    CompSBMLDocumentPlugin compDoc = (CompSBMLDocumentPlugin) doc.createPlugin("comp");
    ModelDefinition md1 = (ModelDefinition) compDoc.createModelDefinition("modeDef1");
    
    md1.createCompartment("c1");
    md1.createParameter("k1");
    md1.createParameter("k2");

    ModelDefinition md2 = new ModelDefinition(md1);
    md2.setId("modelDef2");
    
    compDoc.addModelDefinition(md2);
    
    md2.createCompartment("c2");
    md2.createSpecies("s1");
    md2.createSpecies("s2");

    try {
      String doc1Str = new SBMLWriter().writeSBMLToString(doc);
      // System.out.println(doc1Str);
      SBMLDocument doc1Bis = new SBMLReader().readSBMLFromString(doc1Str);

      assertTrue(doc1Bis.isSetPlugin("comp") == true);
      assertTrue(doc1Bis.getNumPlugins() == 1);
      CompSBMLDocumentPlugin compDocBis = (CompSBMLDocumentPlugin) doc1Bis.getPlugin("comp");
      assertTrue(compDocBis.getModelDefinitionCount() == 2);
      
    } catch (SBMLException e) {
      Assert.fail(e.getMessage());
    } catch (XMLStreamException e) {
      Assert.fail(e.getMessage());
    }

    SBMLDocument doc2 = new SBMLDocument(3, 1);
    doc2.setModel(new Model(md1));

    try {
      String doc2Str = new SBMLWriter().writeSBMLToString(doc2);
      
      SBMLDocument doc3 = new SBMLReader().readSBMLFromString(doc2Str);
      Model m3 = doc3.getModel();
      
      assertTrue(doc3.isSetPlugin("comp") == false);
      assertTrue(m3.getPackageName().equals("core"));
      assertTrue(m3.getPackageVersion() == 0);
      
      assertTrue(m3.getCompartmentCount() == 1);
      assertTrue(m3.getParameterCount() == 2);
      
      assertTrue(m3.findNamedSBase("c1") != null);
      assertTrue(m3.findNamedSBase("k1") != null);
      assertTrue(m3.findNamedSBase("k2") != null);

//      System.out.println(doc2Str);
//      System.out.println(new SBMLWriter().writeSBMLToString(doc3));

    } catch (SBMLException e) {
      Assert.fail(e.getMessage());
    } catch (XMLStreamException e) {
      Assert.fail(e.getMessage());
    }
    
    
    assertTrue(true);
  }

}
