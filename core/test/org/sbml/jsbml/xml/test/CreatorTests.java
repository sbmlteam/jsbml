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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.sbml.jsbml.Creator;
import org.sbml.jsbml.History;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;


public class CreatorTests {
  
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
  @Before public void setUp() {
    doc = new SBMLDocument(3, 1);
    model = doc.createModel("model");
    model.setMetaId("M1");
    History h = model.getHistory();
    
    h.addCreator(new Creator());
  }

  /**
   * 
   */
  @Test public void creatorEmailTest() {
    
    Creator c = model.getHistory().getCreator(0);
    
    c.setEmail("valid@email.com");
    
    Assert.assertTrue(c.getEmail().equals("valid@email.com"));
    
    try {
      c.setEmail("  valid.with.spaces@email.com ");
      Assert.assertTrue(c.getEmail().equals("valid.with.spaces@email.com"));
    } catch (Exception e) {
      Assert.fail("Spaces before or after an email String should be removed before checking the validity of an email !!");
    }
    
    try {
      c.setEmail("invalid.email.com ");
      // We do not throw an Exception any more
      // Assert.fail("Invalid emails should throw an Exception !!");
    } catch (Exception e) {
      // Assert.assertTrue(c.getEmail().equals("valid.with.spaces@email.com"));
      Assert.fail("Invalid emails should not throw an Exception !!");
    }
  }
  
}
