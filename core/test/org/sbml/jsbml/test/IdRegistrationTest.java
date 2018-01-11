/*
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
package org.sbml.jsbml.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.LocalParameter;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLWriter;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.util.filters.NameFilter;


/**
 * @author Andreas Dr&auml;ger
 * @since 0.8
 */
public class IdRegistrationTest {

  /**
   * 
   */
  private static final transient Logger logger = Logger.getLogger(IdRegistrationTest.class);

  /**
   * 
   */
  @Test
  public void idTest() {
    int level = 2, version = 4;
    SBMLDocument doc = new SBMLDocument(level, version);
    Model model = doc.createModel("myModel");
    Reaction r1 = model.createReaction("r1");

    /*
     * Local Parameters
     */
    logger.debug("==== LocalParameters ====");
    KineticLaw kl = r1.createKineticLaw();
    LocalParameter parameter = kl.createLocalParameter("lp1");
    parameter.setValue(2d);
    kl.getListOfLocalParameters().add(new LocalParameter("lp2"));
    try {
      kl.createLocalParameter("lp1");
      fail();
    } catch (IllegalArgumentException exc) {
      logger.debug(exc.getLocalizedMessage());
      assertTrue(kl.getListOfLocalParameters()
        .filterList(new NameFilter(parameter.getId())).size() == 1);
    }
    kl.removeLocalParameter(parameter);
    assertTrue(kl.getListOfLocalParameters()
      .filterList(new NameFilter(parameter.getId())).size() == 0);
    assertTrue(kl.getLocalParameter(parameter.getId()) == null);
    // remove ListOfLocalParameters
    ListOf<LocalParameter> listOfLP = kl.getListOfLocalParameters();
    kl.unsetListOfLocalParameters();
    assertTrue(!listOfLP.contains(parameter));
    assertTrue(kl.getLocalParameter("lp2") == null);
    // add ListOfLocalParameters
    listOfLP.add(parameter);
    kl.setListOfLocalParameters(listOfLP);
    assertTrue(kl.getLocalParameter(parameter.getId()) != null);

    /*
     * Compartments
     */
    logger.debug("==== Compartments ====");
    Compartment c1 = model.createCompartment("c1");
    c1.setSize(2d);
    Compartment c2 = model.createCompartment("c2");
    try {
      c2.setId(c1.getId());
      fail();
    } catch (IllegalArgumentException exc) {
      System.err.println(exc.getLocalizedMessage());
      assertTrue(!c2.getId().equals(c1.getId()));
    }

    assertTrue(!model.addCompartment(c1));

    // remove ListOfCompartments
    ListOf<Compartment> listOfC = model.getListOfCompartments();
    model.unsetListOfCompartments();
    // add ListOfCompartments
    model.setListOfCompartments(listOfC);
    Compartment c3 = new Compartment(level, version);
    c3.setId("c3");
    model.addCompartment(c3);
    c3.setId("c4");

    /*
     * Species and Species References
     */
    logger.debug("==== Species ====");
    Species s1 = model.createSpecies("s1", c1);
    Species p1 = model.createSpecies("p1", c1);
    r1.createReactant(s1);
    r1.createProduct(p1);
    r1.getProduct(0).setId("p1ref");

    /*
     * Reactions
     */
    logger.debug("==== Reactions ====");
    Reaction r2 = new Reaction(level, version);
    SpeciesReference sr1 = r2.createReactant(s1);
    sr1.setId("sr1");
    r2.createModifier(p1);
    r2.setId("r2");
    model.addReaction(r2);

    /*
     * Function Definitions
     */
    logger.debug("==== FunctionDefinitions ====");
    FunctionDefinition f1 = model.createFunctionDefinition("f1");
    model.removeFunctionDefinition(f1.getId());

    try {
      SBMLWriter.write(doc, System.out, ' ', (short) 2);
    } catch (Exception exc) {
      exc.printStackTrace();
    }
  }
}
