/*
 *
 * @file    TestCompartment.java
 * @brief   Compartment unit tests
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
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.util.SBMLtools;


/**
 * @author  Nicolas Rodriguez
 * @author  Akiya Jouraku
 * @author  Ben Bornstein
 * @since 0.8
 */
public class TestCompartment {
  /**
   * 
   */
  private Compartment C;

  /**
   * Model needed otherwise some validation rules prevent the setting of some attributes in the method we want to test
   */
  private Model m;
  
  /**
   * @throws Exception
   */
  @Before public void setUp() throws Exception
  {
    SBMLDocument doc = new SBMLDocument(2, 4);
    m = doc.createModel();    
    
    C = new  Compartment(2,4);
    m.addCompartment(C);
    
    if (C == null) {
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
    C = null;
  }


  /**
   * 
   */
  @Test public void test_Compartment_get_notes_xml_node()
  {
    assertTrue(C.getNotes() == null);
  }

  /**
   * 
   */
  @SuppressWarnings("deprecation")
  @Test public void test_Compartment_create()
  {
    //    assertTrue(C.getTypeCode() == libsbml.SBML_COMPARTMENT);
    assertTrue(C.getMetaId().equals("") == true);
    assertTrue(C.getNotes() == null);
    assertTrue(SBMLtools.toXML(C.getNotes()) == "");
    // assertTrue(C.getAnnotation() == null);
    assertTrue(C.getId().equals("") == true);
    assertTrue(C.getName().equals("") == true);
    assertTrue(C.getUnits().equals("") == true);
    assertTrue(C.getOutside().equals("") == true);
    assertTrue(C.getSpatialDimensions() == 3d);
    // assertTrue(C.getVolume() == 1d); // There is no default for the volume/size in L2 !!
    assertTrue(C.getConstant() == true);
    assertEquals(false, C.isSetId());
    assertEquals(false, C.isSetName());
    assertEquals(false, C.isSetSize());
    assertEquals(false, C.isSetVolume());
    assertEquals(false, C.isSetUnits());
    assertEquals(false, C.isSetOutside());
  }

  /**
   * 
   */
  @Test public void test_Compartment_createWith()
  {
    Compartment c = new  Compartment(2,4);
    c.setId("A");
    //    assertTrue(c.getTypeCode() == libsbml.SBML_COMPARTMENT);
    assertTrue(c.getMetaId().equals("") == true);
    assertTrue(c.getNotes() == null);
    // assertTrue(c.getAnnotation() == null);
    assertTrue(c.getName().equals("") == true);
    assertTrue(c.getSpatialDimensions() == 3d);
    assertTrue(c.getId().equals("A"    ));
    assertTrue(c.getConstant() == true);
    assertEquals(true, c.isSetId());
    assertEquals(false, c.isSetName());
    c = null;
  }


  /**
   * 
   */
  @Test public void test_Compartment_getSpatialDimensions()
  {
    C.setSpatialDimensions(1);
    assertTrue(C.getSpatialDimensions() == 1d);
  }

  /**
   * 
   */
  @Test public void test_Compartment_getsetConstant()
  {
    C.setConstant(true);
    assertTrue(C.getConstant() == true);
  }

  /**
   * 
   */
  @SuppressWarnings("deprecation")
  @Test public void test_Compartment_getsetType()
  {
    m.createCompartmentType("cell"); // Creating a 'cell' compartmentType to be able to pass the validation in setCompartmentType
    
    C.setCompartmentType("cell");
    assertTrue(C.getCompartmentType().equals("cell"));
    assertEquals(true, C.isSetCompartmentType());
    C.unsetCompartmentType();
    assertEquals(false, C.isSetCompartmentType());
  }

  /**
   * 
   */
  @SuppressWarnings("deprecation")
  @Test public void test_Compartment_initDefaults()
  {
    Compartment c = new  Compartment(2,4);
    c.setId("A");
    c.initDefaults();
    assertTrue(c.getId().equals("A"));
    assertTrue(c.getName().equals("") == true);
    assertTrue(c.getUnits().equals("") == true);
    assertTrue(c.getOutside().equals("") == true);
    assertTrue(c.getSpatialDimensions() == 3d);
    // assertTrue(c.getVolume() == 1.0); // There is no default for the volume/size in L2 !!
    assertTrue(Double.isNaN(c.getVolume()));
    assertTrue(c.getConstant() == true);
    assertEquals(true, c.isSetId());
    assertEquals(false, c.isSetName());
    assertEquals(false, c.isSetSize());
    assertEquals(false, c.isSetVolume());
    assertEquals(false, c.isSetUnits());
    assertEquals(false, c.isSetOutside());
    c = null;
  }

  /**
   * 
   */
  @Test public void test_Compartment_setId()
  {
    String id =  "mitochondria";
    C.setId(id);
    assertTrue(C.getId().equals(id));
    assertEquals(true, C.isSetId());
    if (C.getId() == id) {
      ;
    }
    {
    }
    C.setId(C.getId());
    assertTrue(C.getId().equals(id));
    C.setId("");
    assertEquals(false, C.isSetId());
    if (C.getId() != null) {
      ;
    }
    {
    }
  }

  /**
   * 
   */
  @Test public void test_Compartment_setName()
  {
    String name =  "My_Favorite_Factory";
    C.setName(name);
    assertTrue(C.getName().equals(name));
    assertEquals(true, C.isSetName());
    if (C.getName() == name) {
      ;
    }
    {
    }
    C.setName(C.getName());
    assertTrue(C.getName().equals(name));
    C.setName("");
    assertEquals(false, C.isSetName());
    if (C.getName() != null) {
      ;
    }
    {
    }
  }

  /**
   * 
   */
  @SuppressWarnings("deprecation")
  @Test public void test_Compartment_setOutside()
  {
    String outside =  "cell";
    C.setOutside(outside);
    assertTrue(C.getOutside().equals(outside));
    assertEquals(true, C.isSetOutside());
    if (C.getOutside() == outside) {
      ;
    }
    {
    }
    C.setOutside(C.getOutside());
    assertTrue(C.getOutside().equals(outside));
    C.setOutside("");
    assertEquals(false, C.isSetOutside());
    if (C.getOutside() != null) {
      ;
    }
    {
    }
  }

  /**
   * 
   */
  @Test public void test_Compartment_setUnits()
  {
    String units =  "volume";
    C.setUnits(units);
    assertTrue(C.getUnits().equals(units));
    assertEquals(true, C.isSetUnits());
    C.setUnits(C.getUnits());
    assertTrue(C.getUnits().equals(units));
    C.setUnits("");
    assertEquals(false, C.isSetUnits());
    assertTrue(C.getUnits() == ""); // in libsbml it returns null
  }

  /**
   * 
   */
  @Test public void test_Compartment_unsetSize()
  {
    C.setSize(0.2);
    assertTrue(C.getSize() == 0.2);
    assertEquals(true, C.isSetSize());
    C.unsetSize();
    assertEquals(false, C.isSetSize());
  }



}
