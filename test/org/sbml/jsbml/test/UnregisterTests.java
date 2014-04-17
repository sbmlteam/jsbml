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

/**
 * Tests the registration and un-registration of global or local ids using package element.
 * 
 * @version $Rev$
 * @since 1.0
 */
public class UnregisterTests {

  SBMLDocument doc;
  Model model;

  @BeforeClass public static void initialSetUp() {}
  /**
   * 
   */
  @Before public void setUp() {
    doc = new SBMLDocument(2, 4);
    model = doc.createModel("model");

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

    // TODO - create a Port with the same id as any existing one
    
    // TODO - create a second Port with the same id as the first Port and check that this is not allowed
    
    // TODO - create a second Port with a different id. Create a core element with the same id and check that it is allowed
    

  }
  
  // TODO - add test with other packages
  
}
