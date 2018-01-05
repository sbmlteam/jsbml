/*
 * @file    TestSpecies.java
 * @brief   Species unit tests
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
package org.sbml.jsbml.test.sbml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.sbml.jsbml.Species;

/**
 * @author  Nicolas Rodriguez
 * @author  Akiya Jouraku
 * @author  Ben Bornstein
 * @since 0.8
 */
public class TestSpecies {

  /**
   * 
   */
  private Species S;

  /**
   * @throws Exception
   */
  @Before public void setUp() throws Exception
  {
    S = new  Species(2,4);
    if (S == null) {
      ;
    }
    {
    }
  }

  /**
   * @throws Exception
   */
  @After public void tearDown() throws Exception
  {
    S = null;
  }

  /**
   * 
   */
  @SuppressWarnings("deprecation")
  @Test public void test_Species_create()
  {
    //    assertTrue(S.getTypeCode() == libsbml.SBML_SPECIES);
    assertTrue(S.getMetaId().equals("") == true);
    assertTrue(S.getNotes() == null);
    // assertTrue(S.getAnnotation() == null);
    assertTrue(S.getId().equals("") == true);
    assertTrue(S.getName().equals("") == true);
    assertTrue(S.getCompartment().equals("") == true);
    assertTrue(Double.isNaN(S.getInitialAmount())); // TODO: difference to add
    assertTrue(Double.isNaN(S.getInitialConcentration()));
    // assertTrue(S.getInitialConcentration() == 0.0); // test from libsbml
    assertTrue(S.getSubstanceUnits().equals("") == true);
    assertTrue(S.getSpatialSizeUnits().equals("") == true);
    assertTrue(S.getHasOnlySubstanceUnits() == false);
    assertTrue(S.getBoundaryCondition() == false);
    assertTrue(S.getCharge() == 0);
    assertTrue(S.getConstant() == false);
    assertEquals(false, S.isSetId());
    assertEquals(false, S.isSetName());
    assertEquals(false, S.isSetCompartmentInstance());
    assertEquals(false, S.isSetInitialAmount());
    assertEquals(false, S.isSetInitialConcentration());
    assertEquals(false, S.isSetSubstanceUnits());
    assertEquals(false, S.isSetSpatialSizeUnits());
    assertEquals(false, S.isSetUnits());
    assertEquals(false, S.isSetCharge());
  }

  /*
   * Removed from now as we do not have constructor taking namespace
  @Test public void test_Species_createWithNS()
  {

    XMLNamespaces xmlns = new  XMLNamespaces();
    xmlns.add("http://www.sbml.org", "testsbml");
    SBMLNamespaces sbmlns = new  SBMLNamespaces(2,1);
    sbmlns.addNamespaces(xmlns);
    Species object = new  Species(sbmlns);

    assertTrue(object.getTypeCode() == libsbml.SBML_SPECIES);
    assertTrue(object.getMetaId().equals("") == true);
    assertTrue(object.getNotes() == null);
    assertTrue(object.getAnnotation() == null);
    assertTrue(object.getLevel() == 2);
    assertTrue(object.getVersion() == 1);
    assertTrue(object.getNamespaces() != null);
    assertTrue(object.getNamespaces().getLength() == 2);
    object = null;
  }
   */


  /**
   * 
   */
  @Test public void test_Species_setCompartment()
  {
    String compartment =  "cell";;
    S.setCompartment(compartment);
    assertTrue(S.getCompartment().equals(compartment));
    assertEquals(true, S.isSetCompartment());
    if (S.getCompartment() == compartment) {
      ;
    }
    {
    }
    S.setCompartment(S.getCompartment());
    assertTrue(S.getCompartment().equals(compartment));
    S.setCompartment("");
    assertEquals(false, S.isSetCompartment());
    S.setCompartment((String) null);
    if (S.getCompartment() != null) {
      ;
    }
    {
    }
  }

  /**
   * 
   */
  @Test public void test_Species_setId()
  {
    String id =  "Glucose";;
    S.setId(id);
    assertTrue(S.getId().equals(id));
    assertEquals(true, S.isSetId());
    if (S.getId() == id) {
      ;
    }
    {
    }
    S.setId(S.getId());
    assertTrue(S.getId().equals(id));
    S.setId("");
    assertEquals(false, S.isSetId());
    if (S.getId() != null) {
      ;
    }
    {
    }
  }

  /**
   * 
   */
  @Test public void test_Species_setInitialAmount()
  {
    assertEquals(false, S.isSetInitialAmount());
    assertEquals(false, S.isSetInitialConcentration());
    S.setInitialAmount(1.2);
    assertEquals(true, S.isSetInitialAmount());
    assertEquals(false, S.isSetInitialConcentration());
    assertTrue(S.getInitialAmount() == 1.2);
  }

  /**
   * 
   */
  @Test public void test_Species_setInitialConcentration()
  {
    assertEquals(false, S.isSetInitialAmount());
    assertEquals(false, S.isSetInitialConcentration());
    S.setInitialConcentration(3.4);
    assertEquals(false, S.isSetInitialAmount());
    assertEquals(true, S.isSetInitialConcentration());
    assertTrue(S.getInitialConcentration() == 3.4);
  }

  /**
   * 
   */
  @Test public void test_Species_setName()
  {
    String name =  "So_Sweet";;
    S.setName(name);
    assertTrue(S.getName().equals(name));
    assertEquals(true, S.isSetName());
    if (S.getName() == name) {
      ;
    }
    {
    }
    S.setName(S.getName());
    assertTrue(S.getName().equals(name));
    S.setName("");
    assertEquals(false, S.isSetName());
    if (S.getName() != null) {
      ;
    }
    {
    }
  }


  /**
   * 
   */
  @SuppressWarnings("deprecation")
  @Test public void test_Species_setSpatialSizeUnits()
  {
    Species s = new  Species(2,1);
    String units =  "volume";;
    s.setSpatialSizeUnits(units);
    assertTrue(s.getSpatialSizeUnits().equals(units));
    assertEquals(true, s.isSetSpatialSizeUnits());
    if (s.getSpatialSizeUnits() == units) {
      ;
    }
    {
    }
    s.setSpatialSizeUnits(s.getSpatialSizeUnits());
    assertTrue(s.getSpatialSizeUnits().equals(units));
    s.setSpatialSizeUnits("");
    assertEquals(false, s.isSetSpatialSizeUnits());
    if (s.getSpatialSizeUnits() != null) {
      ;
    }
    {
    }
    s = null;
  }

  /**
   * 
   */
  @Test public void test_Species_setSubstanceUnits()
  {
    String units =  "item";;
    S.setSubstanceUnits(units);
    assertTrue(S.getSubstanceUnits().equals(units));
    assertEquals(true, S.isSetSubstanceUnits());
    if (S.getSubstanceUnits() == units) {
      ;
    }
    {
    }
    S.setSubstanceUnits(S.getSubstanceUnits());
    assertTrue(S.getSubstanceUnits().equals(units));
    S.setSubstanceUnits("");
    assertEquals(false, S.isSetSubstanceUnits());
    if (S.getSubstanceUnits() != null) {
      ;
    }
    {
    }
  }

  /**
   * 
   */
  @Test public void test_Species_setUnits()
  {
    String units =  "mole";;
    S.setUnits(units);
    assertTrue(S.getUnits().equals(units));
    assertEquals(true, S.isSetUnits());
    if (S.getSubstanceUnits() == units) {
      ;
    }
    {
    }
    S.setUnits(S.getSubstanceUnits());
    assertTrue(S.getUnits().equals(units));
    S.setUnits("");
    assertEquals(false, S.isSetUnits());
    if (S.getSubstanceUnits() != null) {
      ;
    }
    {
    }
    S.unsetUnits();
  }
}
