/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2014 jointly by the following organizations:
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

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.Constraint;
import org.sbml.jsbml.LocalParameter;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.ext.comp.CompModelPlugin;
import org.sbml.jsbml.ext.comp.CompSBMLDocumentPlugin;
import org.sbml.jsbml.ext.comp.ModelDefinition;
import org.sbml.jsbml.ext.comp.Port;

/**
 * Tests the registration and un-registration of global or local ids using package element.
 * 
 * @version $Rev$
 * @since 1.0
 */
public class UnregisterTests {

  SBMLDocument doc;
  Model model;
  CompModelPlugin compMainModel;
  
  @BeforeClass public static void initialSetUp() {}
  /**
   * 
   */
  @Before public void setUp() {
    doc = new SBMLDocument(3, 1);
    model = doc.createModel("model");

    compMainModel = (CompModelPlugin) model.getPlugin("comp");
    
    Compartment comp = model.createCompartment("cell");
    comp.setMetaId("cell");

    Species s1 = model.createSpecies("S1", comp);
    s1.setMetaId("S1");

    Species s2 = model.createSpecies("S2", comp);
    s2.setMetaId("S2");

    Reaction r1 = model.createReaction("R1");
    r1.setMetaId("R1");

    SpeciesReference reactant = model.createReactant("SP1");
    reactant.setMetaId("SP1");
    reactant.setSpecies(s1);

    SpeciesReference product = model.createProduct("SP2");
    product.setMetaId("SP2");
    product.setSpecies(s2);

    LocalParameter lp1 = r1.createKineticLaw().createLocalParameter("LP1");
    lp1.setMetaId("LP1");

    Constraint c1 = model.createConstraint();
    c1.setMetaId("c0");
  }



  @Test public void testRegisterCompPort() {

    Species s3 = new Species();
    s3.setId("S3");

    assertTrue(compMainModel.getPortCount() == 0);
    
    // create a Port with the same id as any existing one from the SIdRef scope.
    Port portS3 = null;
    try {
    	portS3 = compMainModel.createPort("S3");
    	assertTrue(true);
    } catch (IllegalArgumentException e) {
    	assertTrue("We should be allowed to create a Port with the same id as a Species in the model", false);
    }

    assertTrue(compMainModel.getPortCount() == 1);
    
    // create a second Port with the same id as the first Port and check that this is not allowed
    try {
    	Port portS3bis = compMainModel.createPort("S3");
    	assertTrue("We should not be allowed to have several Port with the same id inside the same model", false);
    } catch (IllegalArgumentException e) {
    	assertTrue(true);
    }

    assertTrue(compMainModel.getPortCount() == 1);
    
    // create a second Port with a different id. Create a core element with the same id and check that it is allowed
    Port portPo1 = null;
    try {
    	portPo1 = compMainModel.createPort("Po1");
    	assertTrue(true);

    	model.createParameter("Po1");
    	assertTrue(true);
    } catch (IllegalArgumentException e) {
    	assertTrue("We should be allowed to create a core element with the same id as an existing Port", false);
    }

    assertTrue(compMainModel.getPortCount() == 2);
    
    assertTrue(compMainModel.getPortById("Po1") != null);
    assertTrue(compMainModel.getPortById("Po1").equals(portPo1));
    assertTrue(compMainModel.getPortById("S3") != null);
    assertTrue(compMainModel.getPortById("S3").equals(portS3));
    
    assertTrue(! portS3.equals(portPo1));
    
    CompSBMLDocumentPlugin compDoc = (CompSBMLDocumentPlugin) doc.getPlugin("comp");
    
    ModelDefinition modelDef = (ModelDefinition) compDoc.createModelDefinition("modelDef1");
    
    CompModelPlugin compModelDef = (CompModelPlugin) modelDef.getPlugin("comp");
    
    // create a ModelDefinition with a portId and id the same as in the main model
    compModelDef.createPort("S3");
    modelDef.createSpecies("S3");

    ModelDefinition modelDef2 = (ModelDefinition) compDoc.createModelDefinition("modelDef2"); 
    
    // TODO - which id namespace does the modelDefinition id belong to ? itself I suppose
    // Then it would not be really possible to get modelFedinition by id at the SBMLDocument level ?
    
    CompModelPlugin compModelDef2 = (CompModelPlugin) modelDef2.getPlugin("comp");
    
    // create a ModelDefinition with a portId and id the same as in the main model
    modelDef2.createSpecies("S3");
    compModelDef2.createPort("S3");

    assertTrue(modelDef.getSpecies("S3") != modelDef2.getSpecies("S3"));
    assertTrue(modelDef.getSpecies("S3").equals(modelDef2.getSpecies("S3")));
    
    // create a second Port with the same id as the first Port in a ModelDefinition and check that this is not allowed
    try {
    	Port portS3bis = compModelDef2.createPort("S3");
    	assertTrue("We should not be allowed to have several Port with the same id inside the same modelDefinition", false);
    } catch (IllegalArgumentException e) {
    	assertTrue(true);
    }

    // create a second Species with the same id as the first Species in a ModelDefinition and check that this is not allowed
    try {
    	Species s3bis = modelDef2.createSpecies("S3");
    	assertTrue("We should not be allowed to have several Species with the same id inside the same modelDefinition", false);
    } catch (IllegalArgumentException e) {
    	assertTrue(true);
    }
    
    
    assertTrue(modelDef2.getSpeciesCount() == 1);
    assertTrue(compModelDef2.getPortCount() == 1);
    
    Model clonedModel = model.clone(); // TODO - problem there, there are warning message that should not be there
    
    CompModelPlugin compMainModelPluginCloned = (CompModelPlugin) clonedModel.getPlugin("comp");
    
    assertTrue(compMainModelPluginCloned.getPortCount() == 2);
    // TODO - assertTrue(compMainModelPluginCloned.getPortById("S3") != null);
    
    
    compMainModel.removePort(0);
    
    assertTrue(compMainModel.getPortCount() == 1);
    assertTrue(compMainModel.getPortById("Po1") != null);
    assertTrue(compMainModel.getPortById("S3") == null);
    
  }
  
  
  
  // TODO - clone a ModelDefinition and check the map are ok
  
  
  // TODO - add test with other packages
  
}
