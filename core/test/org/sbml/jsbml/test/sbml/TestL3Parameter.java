/*
 *
 * @file    TestL3Parameter.java
 * @brief   L3 Parameter unit tests
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
import org.sbml.jsbml.Parameter;

/**
 * @author
 * @since 0.8
 */
public class TestL3Parameter {

  /**
   * 
   */
  private Parameter P;

  /**
   * @param x
   * @return
   */
  public boolean isNaN(double x)
  {
    return Double.isNaN(x);
  }

  /**
   * @throws Exception
   */
  @Before public void setUp() throws Exception
  {
    P = new  Parameter(3,1);
  }

  /**
   * @throws Exception
   */
  @After public void tearDown() throws Exception
  {
    P = null;
  }

  /**
   * 
   */
  @Test public void test_L3_Parameter_constant()
  {
    assertTrue(P.isSetConstant() == false);
    P.setConstant(true);
    assertTrue(P.getConstant() == true);
    assertTrue(P.isSetConstant() == true);
    P.setConstant(false);
    assertTrue(P.getConstant() == false);
    assertTrue(P.isSetConstant() == true);
  }

  /**
   * 
   */
  @Test public void test_L3_Parameter_create()
  {
    //    assertTrue(P.getTypeCode() == libsbml.SBML_PARAMETER);
    assertTrue(P.getMetaId().equals("") == true);
    assertTrue(P.getNotes() == null);
    //    assertTrue(P.getAnnotation() == null);
    assertTrue(P.getId().equals("") == true);
    assertTrue(P.getName().equals("") == true);
    assertTrue(P.getUnits().equals("") == true);
    assertEquals(true, isNaN(P.getValue()));
    assertTrue(P.getConstant() == true);
    assertEquals(false, P.isSetId());
    assertEquals(false, P.isSetName());
    assertEquals(false, P.isSetValue());
    assertEquals(false, P.isSetUnits());
    assertEquals(false, P.isSetConstant());
  }

  /*
   // TODO: put back when implemented or document api changes
  @Test public void test_L3_Parameter_createWithNS()
  {
    XMLNamespaces xmlns = new  XMLNamespaces();
    xmlns.add("http://www.sbml.org", "testsbml");
    SBMLNamespaces sbmlns = new  SBMLNamespaces(3,1);
    sbmlns.addNamespaces(xmlns);
    Parameter p = new  Parameter(sbmlns);
    assertTrue(p.getTypeCode() == libsbml.SBML_PARAMETER);
    assertTrue(p.getMetaId().equals("") == true);
    assertTrue(p.getNotes() == null);
    assertTrue(p.getAnnotation() == null);
    assertTrue(p.getLevel() == 3);
    assertTrue(p.getVersion() == 1);
    assertTrue(p.getNamespaces() != null);
    assertTrue(p.getNamespaces().getLength() == 2);
    assertTrue(p.getId().equals("") == true);
    assertTrue(p.getName().equals("") == true);
    assertTrue(p.getUnits().equals("") == true);
    assertEquals(true, isnan(p.getValue()));
    assertTrue(p.getConstant() == true);
    assertEquals(false, p.isSetId());
    assertEquals(false, p.isSetName());
    assertEquals(false, p.isSetValue());
    assertEquals(false, p.isSetUnits());
    assertEquals(false, p.isSetConstant());
    p = null;
  }
   */


  /**
   * 
   */
  @Test public void test_L3_Parameter_id()
  {
    String id =  "mitochondria";;
    assertEquals(false, P.isSetId());
    P.setId(id);
    assertTrue(P.getId().equals(id));
    assertEquals(true, P.isSetId());
    if (P.getId() == id) {
      ;
    }
    {
    }
  }

  /**
   * 
   */
  @Test public void test_L3_Parameter_name()
  {
    String name =  "My_Favorite_Factory";;
    assertEquals(false, P.isSetName());
    P.setName(name);
    assertTrue(P.getName().equals(name));
    assertEquals(true, P.isSetName());
    if (P.getName() == name) {
      ;
    }
    {
    }
    P.unsetName();
    assertEquals(false, P.isSetName());
    if (P.getName() != null) {
      ;
    }
    {
    }
  }

  /**
   * 
   */
  @Test public void test_L3_Parameter_units()
  {
    String units =  "volume";;
    assertEquals(false, P.isSetUnits());
    P.setUnits(units);
    assertTrue(P.getUnits().equals(units));
    assertEquals(true, P.isSetUnits());
    if (P.getUnits() == units) {
      ;
    }
    {
    }
    P.unsetUnits();
    assertEquals(false, P.isSetUnits());
    if (P.getUnits() != null) {
      ;
    }
    {
    }
  }

  /**
   * 
   */
  @Test public void test_L3_Parameter_value()
  {
    assertEquals(false, P.isSetValue());
    assertEquals(true, isNaN(P.getValue()));
    P.setValue(1.5);
    assertEquals(true, P.isSetValue());
    assertTrue(P.getValue() == 1.5);
    P.unsetValue();
    assertEquals(false, P.isSetValue());
    assertEquals(true, isNaN(P.getValue()));
  }
}
