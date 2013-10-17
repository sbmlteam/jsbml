/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2013 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
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
import org.junit.Test;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.LocalParameter;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;


/**
 * @author Alex Thomas
 * @version $Rev$
 * @since 1.0
 */
public class RemoveFromParentTest {

  /**
   * Test method for {@link org.sbml.jsbml.AbstractTreeNode#removeFromParent()}.
   */
  
  private SBMLDocument doc;
  private Model model;
  private Compartment compartment;
  private Compartment compartment2; 
  private KineticLaw k;
  
  @Before public void setUp() throws Exception
  {
	  doc = new SBMLDocument(3, 1);
	  model = doc.createModel("test_model");
	  model.setMetaId("M1");
	  compartment = model.createCompartment("cytoplasm");
	  compartment.setMetaId("M2");
	  compartment2 = model.createCompartment("periplasm"); 
	  Reaction reaction = model.createReaction("R1");
	  k = reaction.createKineticLaw();
	  k.setMetaId("M3");
	  LocalParameter param1 = k.createLocalParameter("LP1");
	  param1.setMetaId("M4");
  }
  
  @Test
  public void testRemoveFromParentRoot() {
    // Check if method fails if it tries to delete object with no parent (SBMLDocument)
    assertTrue(!doc.removeFromParent());
  }
  
  @Test
  public void testRemoveFromParentList() {
    
    // Check if method can delete both compartment 
    assertTrue(model.isSetListOfCompartments());
    compartment.removeFromParent();
    compartment2.removeFromParent();
    assertTrue(!model.isSetListOfCompartments());
    
    assertTrue(doc.findSBase("M2") == null);
    assertTrue(model.findCallableSBase("cytoplasm") == null);
  }
  
  @Test
  public void testRemoveFromParentNoList() {
	  // Check if method can delete model from SBMLDocument

	  assertTrue(doc.findSBase("M1") != null);

	  model.removeFromParent();
	  assertTrue(!doc.isSetModel());

	  assertTrue(doc.findSBase("M1") == null);
	  assertTrue(doc.findSBase("M2") == null);
	  assertTrue(doc.findSBase("M4") == null);
	  
	  Model m = doc.createModel("test_model");
	  m.setMetaId("M4");
	  m.createParameter("LP1");
  }
  
  @Test
  public void testRemoveFromParentKineticLaw() {

	  assertTrue(k.removeFromParent());

	  Reaction reaction = model.getReaction(0);
	  
	  assertTrue(reaction.isSetKineticLaw() == false);
	  assertTrue(doc.findSBase("M1") != null);
	  assertTrue(doc.findSBase("M3") == null);
	  assertTrue(doc.findSBase("M4") == null);
	  
	  k = reaction.createKineticLaw();
	  k.setMetaId("M4");
	  k.createLocalParameter("LP1");
	  
  }

}
