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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.sbml.jsbml.xml.libsbml.LibSBMLConstants.LINK_TO_LIBSBML;

import org.junit.BeforeClass;
import org.junit.Test;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.Event;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.text.parser.ParseException;
import org.sbml.jsbml.xml.libsbml.LibSBMLChangeListener;
import org.sbml.jsbml.xml.libsbml.LibSBMLReader;
import org.sbml.libsbml.libsbml;

/**
 * This class is used to test {@link LibSBMLChangeListener} with JUnit tests
 * 
 * @author Meike Aichele
 * @version $Rev$
 * @since 1.0
 */
public class LibSBMLChangeListenerTest {

  /**
   * 
   */
  static SBMLDocument doc = null;
  /**
   * 
   */
  static org.sbml.libsbml.SBMLDocument libDoc = null;
  /**
   * 
   */
  final static double delta = 1E-24d;

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

    try {
      // Read SBML file using LibSBML and convert it to JSBML:
      libDoc = new org.sbml.libsbml.SBMLDocument(2, 4);
      libDoc.createModel("model01");
      LibSBMLReader reader = new LibSBMLReader();
      doc = reader.convertSBMLDocument(libDoc);
    } catch (Exception exc) {
      exc.printStackTrace();
      System.exit(1);
    }
  }

  /**
   * test-method to check nodeAdded() in LibSBMLChangeListener
   */
  @SuppressWarnings("deprecation")
  @Test
  public void testNodeAdded() {
    doc.getModel().createSpeciesType("test");
    //		assertEquals("model01", libDoc.getModel().getId());
    //		doc.getModel().createCompartment("comp001");
    //		assertNotNull(libDoc.getModel().getCompartment("comp001"));
    //		doc.getModel().createCompartmentType("comptype001");
    //		assertNotNull(libDoc.getModel().getCompartmentType("comptype001"));
    //		doc.getModel().createReaction("reac001");
    //		assertNotNull(libDoc.getModel().getReaction("reac001"));
    //		doc.getModel().createEvent("evt001");
    //		assertNotNull(libDoc.getModel().getEvent("evt001"));
    //		doc.getModel().createKineticLaw();
    //		assertNotNull(libDoc.getModel().getReaction("reac001").getKineticLaw());
    //		doc.getModel().createDelay();
    //		assertNotNull(libDoc.getModel().getEvent("evt001").getDelay());
    //		doc.getModel().createEventAssignment();
    //		assertNotNull(libDoc.getModel().getEvent("evt001").getEventAssignment(0));
    //		doc.getModel().createModifier("mod01");
    //		assertNotNull(libDoc.getModel().getReaction("reac001").getModifier("mod01"));
    //		doc.getModel().createFunctionDefinition("funcDef");
    //		assertNotNull(libDoc.getModel().getFunctionDefinition("funcDef"));
    //		doc.getModel().createConstraint();
    //		assertNotNull(libDoc.getModel().getListOfConstraints());
    //		doc.getModel().createParameter("param01");
    //		assertNotNull(libDoc.getModel().getParameter("param01"));
    //		doc.getModel().createProduct();
    //		assertNotNull(libDoc.getModel().getReaction("reac001").getListOfProducts());
    //		doc.getModel().createReactant();
    //		assertNotNull(libDoc.getModel().getReaction("reac001").getListOfReactants());
    //		doc.getModel().createSpecies("spec01", doc.getModel().getCompartment("comp001"));
    //		assertNotNull(libDoc.getModel().getSpecies("spec01"));
    //		assertTrue(libDoc.getModel().getSpecies("spec01").isSetCompartment());
    //		doc.getModel().createSpeciesType("st01");
    //		assertNotNull(libDoc.getModel().getSpeciesType("st01"));
    //		doc.getModel().createTrigger();
    //		assertTrue(libDoc.getModel().getEvent("evt001").isSetTrigger());
    //		doc.getModel().createUnitDefinition("udef1");
    //		assertNotNull(libDoc.getModel().getUnitDefinition("udef1"));
    //		doc.getModel().createUnit(Kind.METRE);
    //		assertNotNull(libDoc.getModel().getUnitDefinition("udef1").getListOfUnits());
  }

  /**
   * test-method to check propertyChanged() in LibSBMLChangeListener
   */
  @SuppressWarnings("deprecation")
  @Test
  public void testPropertyChanged() {
    assertTrue(doc.getModel().getUserObject(LINK_TO_LIBSBML).equals(libDoc.getModel()));
    Model model = doc.createModel("model02");
    assertTrue(model.getUserObject(LINK_TO_LIBSBML).equals(libDoc.getModel()));
    assertEquals(libDoc.getModel().getId(), model.getId());
    model.setName("modelName");
    assertEquals(libDoc.getModel().getName(), model.getName());
    Compartment compartment = model.createCompartment("comp001");
    compartment.setConstant(true);
    assertTrue(libDoc.getModel().getCompartment("comp001").getConstant());
    model.getCompartment("comp001").setCompartmentType("comptype001");
    assertEquals(libDoc.getModel().getCompartment("comp001").getCompartmentType(), "comptype001");
    model.getCompartment("comp001").setMetaId("metaCompId");
    assertEquals(libDoc.getModel().getCompartment("comp001").getMetaId(), "metaCompId");
    model.getCompartment("comp001").setSize(0.5d);
    assertEquals(libDoc.getModel().getCompartment("comp001").getSize(), 0.5d, delta);
    model.getCompartment("comp001").setSpatialDimensions(2);
    assertEquals(libDoc.getModel().getCompartment("comp001").getSpatialDimensionsAsDouble(), 2d, delta);

    Event event = model.createEvent("evt001");
    org.sbml.libsbml.Event libEvent = libDoc.getModel().getEvent("evt001");
    try {
      event.createTrigger().setMath(ASTNode.parseFormula("0 < comp001"));
      event.createEventAssignment("comp001", ASTNode.parseFormula("3"));

      assertEquals(libEvent.getEventAssignment(0).getVariable(), "comp001");
      assertTrue(libEvent.getEventAssignment("comp001").isSetMath());
      assertEquals(libsbml.formulaToString(libEvent.getTrigger().getMath()), "lt(0, comp001)");
      assertEquals(libsbml.formulaToString(libEvent.getEventAssignment("comp001").getMath()), "3");
    } catch (ParseException exc) {
      exc.printStackTrace();
    }

    assertNotNull(libEvent);
    assertNull(libEvent.getDelay());
    assertEquals(libEvent, event.getUserObject(LINK_TO_LIBSBML));
  }

  /**
   * test-method to check nodeRemoved() in LibSBMLChangeListener
   */
  @SuppressWarnings("deprecation")
  @Test
  public void testNodeRemoved() {
    assertNotNull(doc.getModel().getCompartment("comp001"));
    doc.getModel().removeCompartment("comp001");
    assertNull(libDoc.getModel().getCompartment("comp001"));
    doc.getModel().createCompartmentType("comptype001");
    assertNotNull(libDoc.getModel().getCompartmentType("comptype001"));
    doc.getModel().removeCompartmentType("comptype001");
    assertNull(libDoc.getModel().getCompartmentType("comptype001"));
    doc.getModel().createReaction("reac001");
    assertNotNull(libDoc.getModel().getReaction("reac001"));
    doc.getModel().removeReaction("reac001");
    assertNull(libDoc.getModel().getReaction("reac001"));
    doc.getModel().removeEvent("evt001");
    assertNull(libDoc.getModel().getEvent("evt001"));
    doc.getModel().createFunctionDefinition("funcDef");
    assertNotNull(libDoc.getModel().getFunctionDefinition("funcDef"));
    doc.getModel().removeFunctionDefinition("funcDef");
    assertNull(libDoc.getModel().getFunctionDefinition("funcDef"));
    doc.getModel().createParameter("param01");
    assertNotNull(libDoc.getModel().getParameter("param01"));
    doc.getModel().removeParameter("param01");
    assertNull(libDoc.getModel().getParameter("param01"));
    doc.getModel().createSpeciesType("st01");
    assertNotNull(libDoc.getModel().getSpeciesType("st01"));
    doc.getModel().removeSpeciesType("st01");
    assertNull(libDoc.getModel().getSpeciesType("st01"));
    doc.getModel().createUnitDefinition("udef1");
    assertNotNull(libDoc.getModel().getUnitDefinition("udef1"));
    doc.getModel().removeUnitDefinition("udef1");
    assertNull(libDoc.getModel().getUnitDefinition("udef1"));
  }

}
