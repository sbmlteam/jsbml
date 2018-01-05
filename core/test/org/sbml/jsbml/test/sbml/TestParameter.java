/*
 *
 * @file    TestParameter.java
 * @brief   Parameter unit tests
 *
 * This test file was converted libsbml http://sbml.org/software/libsbml
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
 * 
 * @author  Nicolas Rodriguez
 * @author  Akiya Jouraku
 * @author  Ben Bornstein
 * @since 0.8
 *
 */
public class TestParameter {

  /**
   * 
   */
  private Parameter P;

  /**
   * @throws Exception
   */
  @Before public void setUp() throws Exception
  {
    P = new  Parameter(2,4);
    if (P == null) {
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
    P = null;
  }

  /**
   * 
   */
  @Test public void test_Parameter_create()
  {
    //    assertTrue(P.getTypeCode() == libsbml.SBML_PARAMETER);
    assertTrue(P.getMetaId().equals("") == true);
    assertTrue(P.getNotes() == null);
    //    assertTrue(P.getAnnotation() == null);
    assertTrue(P.getId().equals("") == true);
    assertTrue(P.getName().equals("") == true);
    assertTrue(P.getUnits().equals("") == true);
    assertTrue(P.getConstant() == true);
    assertEquals(false, P.isSetId());
    assertEquals(false, P.isSetName());
    assertEquals(false, P.isSetValue());
    assertEquals(false, P.isSetUnits());
  }

  /*
  // TODO: put back if we support constructor with namespaces
  public void test_Parameter_createWithNS()
  {
    XMLNamespaces xmlns = new  XMLNamespaces();
    xmlns.add("http://www.sbml.org", "testsbml");
    SBMLNamespaces sbmlns = new  SBMLNamespaces(2,1);
    sbmlns.addNamespaces(xmlns);
    Parameter object = new  Parameter(sbmlns);
    assertTrue(object.getTypeCode() == libsbml.SBML_PARAMETER);
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
  @Test public void test_Parameter_setId()
  {
    String id =  "Km1";;
    P.setId(id);
    assertTrue(P.getId().equals(id));
    assertEquals(true, P.isSetId());
    if (P.getId() == id) {
      ;
    }
    {
    }
    P.setId(P.getId());
    assertTrue(P.getId().equals(id));
    P.setId("");
    assertEquals(false, P.isSetId());
    if (P.getId() != null) {
      ;
    }
    {
    }
  }

  /**
   * 
   */
  @Test public void test_Parameter_setName()
  {
    String name =  "Forward_Michaelis_Menten_Constant";;
    P.setName(name);
    assertTrue(P.getName().equals(name));
    assertEquals(true, P.isSetName());
    if (P.getName() == name) {
      ;
    }
    {
    }
    P.setName(P.getName());
    assertTrue(P.getName().equals(name));
    P.setName("");
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
  @Test public void test_Parameter_setUnits()
  {
    String units =  "second";;
    P.setUnits(units);
    assertTrue(P.getUnits().equals(units));
    assertEquals(true, P.isSetUnits());
    if (P.getUnits() == units) {
      ;
    }
    {
    }
    P.setUnits(P.getUnits());
    assertTrue(P.getUnits().equals(units));
    P.setUnits("");
    assertEquals(false, P.isSetUnits());
    if (P.getUnits() != null) {
      ;
    }
    {
    }
  }
}
