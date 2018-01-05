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
package org.sbml.jsbml.test;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.CompartmentType;
import org.sbml.jsbml.Event;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesType;
import org.sbml.jsbml.UnitDefinition;


/**
 * Tests if adding and removing elements that have an ID to/from a {@link Model}
 * works using a generic implementation.
 * 
 * @author Andreas Dr&auml;ger
 * @since 1.0
 */
@SuppressWarnings("deprecation")
public class RemoveFromModelTest {

  /**
   * 
   */
  private SBMLDocument docL3;
  /**
   * 
   */
  private SBMLDocument docL2V4;
  /**
   * 
   */
  private Model modelL3;
  /**
   * 
   */
  private Model modelL2V4;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    docL3 = new SBMLDocument(3, 1);
    modelL3 = docL3.createModel("test");
    modelL3.createUnitDefinition("ud1");
    Compartment c = modelL3.createCompartment("comp");
    modelL3.createSpecies("s1", c);
    modelL3.createEvent("event");

    docL2V4 = new SBMLDocument(2, 4);
    modelL2V4 = docL2V4.createModel("test");
  }

  /**
   * Test method for {@link org.sbml.jsbml.Model#remove(java.lang.String)}.
   */
  @Test
  public void testRemove() {
    Reaction r = modelL3.createReaction("r1");
    assertTrue(modelL3.getReaction(r.getId()) != null);

    modelL3.remove(r.getId());
    assertTrue(modelL3.getReaction(r.getId()) == null);
  }

  /**
   * Test method for {@link org.sbml.jsbml.Model#removeCompartment(java.lang.String)}.
   */
  @Test
  public void testRemoveCompartmentString() {
    Compartment c = modelL3.createCompartment("c2");
    assertTrue(modelL3.getCompartment(c.getId()) != null);

    modelL3.removeCompartment(c.getId());
    assertTrue(modelL3.getCompartment(c.getId()) == null);
  }

  /**
   * Test method for {@link org.sbml.jsbml.Model#removeCompartmentType(java.lang.String)}.
   */
  @Test
  public void testRemoveCompartmentTypeString() {
    CompartmentType ct = modelL2V4.createCompartmentType("ct");
    assertTrue(modelL2V4.getCompartmentType(ct.getId()) != null);

    modelL2V4.removeCompartmentType(ct.getId());
    assertTrue(modelL2V4.getCompartmentType(ct.getId()) == null);
  }

  /**
   * Test method for {@link org.sbml.jsbml.Model#removeEvent(java.lang.String)}.
   */
  @Test
  public void testRemoveEventString() {
    Event r = modelL3.createEvent("evt1");
    assertTrue(modelL3.getEvent(r.getId()) != null);

    modelL3.removeEvent(r.getId());
    assertTrue(modelL3.getEvent(r.getId()) == null);
  }

  /**
   * Test method for {@link org.sbml.jsbml.Model#removeFunctionDefinition(java.lang.String)}.
   */
  @Test
  public void testRemoveFunctionDefinitionString() {
    FunctionDefinition r = modelL3.createFunctionDefinition("fd1");
    assertTrue(modelL3.getFunctionDefinition(r.getId()) != null);

    modelL3.removeFunctionDefinition(r.getId());
    assertTrue(modelL3.getFunctionDefinition(r.getId()) == null);
  }

  /**
   * Test method for {@link org.sbml.jsbml.Model#removeParameter(java.lang.String)}.
   */
  @Test
  public void testRemoveParameterString() {
    Parameter r = modelL3.createParameter("p1");
    assertTrue(modelL3.getParameter(r.getId()) != null);

    modelL3.removeParameter(r.getId());
    assertTrue(modelL3.getParameter(r.getId()) == null);
  }

  /**
   * Test method for {@link org.sbml.jsbml.Model#removeReaction(java.lang.String)}.
   */
  @Test
  public void testRemoveReactionString() {
    Reaction r = modelL3.createReaction("r1");
    assertTrue(modelL3.getReaction(r.getId()) != null);

    modelL3.removeReaction(r.getId());
    assertTrue(modelL3.getReaction(r.getId()) == null);
  }

  /**
   * Test method for {@link org.sbml.jsbml.Model#removeSpecies(java.lang.String)}.
   */
  @Test
  public void testRemoveSpeciesString() {
    Species c = modelL3.createSpecies("s5");
    assertTrue(modelL3.getSpecies(c.getId()) != null);

    modelL3.removeSpecies(c.getId());
    assertTrue(modelL3.getSpecies(c.getId()) == null);
  }

  /**
   * Test method for {@link org.sbml.jsbml.Model#removeSpeciesType(java.lang.String)}.
   */
  @Test
  public void testRemoveSpeciesTypeString() {
    SpeciesType st = modelL2V4.createSpeciesType("st");
    assertTrue(modelL2V4.getSpeciesType(st.getId()) != null);

    modelL2V4.removeSpeciesType(st.getId());
    assertTrue(modelL2V4.getSpeciesType(st.getId()) == null);
  }

  /**
   * Test method for {@link org.sbml.jsbml.Model#removeUnitDefinition(java.lang.String)}.
   */
  @Test
  public void testRemoveUnitDefinitionString() {
    UnitDefinition ud = modelL3.createUnitDefinition("ud2");
    assertTrue(modelL3.getUnitDefinition(ud.getId()) != null);

    modelL3.removeUnitDefinition(ud.getId());
    assertTrue(modelL3.getUnitDefinition(ud.getId()) == null);
  }

}
