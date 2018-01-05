/*
 *
 * @file    TestReaction.java
 * @brief   SBML Reaction unit tests
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

import org.sbml.jsbml.ModifierSpeciesReference;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SpeciesReference;


/**
 * @author  Nicolas Rodriguez
 * @author  Akiya Jouraku
 * @author  Ben Bornstein
 * @since 0.8
 */
public class TestReaction {

  /**
   * @param a
   * @param b
   */
  static void assertNotEquals(Object a, Object b)
  {
    if (a == null && b == null) {
      assertTrue(false);
    } else if ((a == null) || (b == null)) {
      return;
    }
    assertTrue(!a.equals(b));
  }


  /**
   * 
   */
  private Reaction R;

  /**
   * @throws Exception
   */
  @Before public void setUp() throws Exception
  {
    R = new  Reaction(2,4);
    if (R == null) {
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
    R = null;
  }

  /**
   * 
   */
  @Test public void test_Reaction_addModifier()
  {
    ModifierSpeciesReference msr = new  ModifierSpeciesReference(2,4);
    msr.setSpecies("s");
    R.addModifier(msr);
    assertTrue(R.getReactantCount() == 0);
    assertTrue(R.getProductCount() == 0);
    assertTrue(R.getModifierCount() == 1);
  }

  /**
   * 
   */
  @Test public void test_Reaction_addProduct()
  {
    SpeciesReference sr = new  SpeciesReference(2,4);
    sr.setSpecies("s");
    R.addProduct(sr);
    assertTrue(R.getReactantCount() == 0);
    assertTrue(R.getProductCount() == 1);
    assertTrue(R.getModifierCount() == 0);
    sr = null;
  }

  /**
   * 
   */
  @Test public void test_Reaction_addReactant()
  {
    SpeciesReference sr = new  SpeciesReference(2,4);
    sr.setSpecies("s");
    R.addReactant(sr);
    assertTrue(R.getReactantCount() == 1);
    assertTrue(R.getProductCount() == 0);
    assertTrue(R.getModifierCount() == 0);
    sr = null;
  }

  /**
   * 
   */
  @Test public void test_Reaction_create()
  {
    //assertTrue(R.getTypeCode() == libsbml.SBML_REACTION);
    assertTrue(R.getMetaId().equals("") == true);
    assertTrue(R.getNotes() == null);
    //    assertTrue(R.getAnnotation() == null);
    assertTrue(R.getId().equals("") == true);
    assertTrue(R.getName().equals("") == true);
    assertEquals(R.getKineticLaw(),null);
    assertTrue(R.getReversible() != false);
    assertTrue(R.getFast() == false);
    assertEquals(false, R.isSetId());
    assertEquals(false, R.isSetName());
    assertEquals(false, R.isSetKineticLaw());
    assertTrue(R.getReactantCount() == 0);
    assertTrue(R.getProductCount() == 0);
    assertTrue(R.getModifierCount() == 0);
  }

  /*
  @Test public void test_Reaction_createWithNS()
  {
    XMLNamespaces xmlns = new  XMLNamespaces();
    xmlns.add("http://www.sbml.org", "testsbml");
    SBMLNamespaces sbmlns = new  SBMLNamespaces(2,1);
    sbmlns.addNamespaces(xmlns);
    Reaction object = new  Reaction(sbmlns);
    assertTrue(object.getTypeCode() == libsbml.SBML_REACTION);
    assertTrue(object.getMetaId().equals("") == true);
    assertTrue(object.getNoteCount() == null);
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
  @Test public void test_Reaction_getModifier()
  {
    ModifierSpeciesReference msr1 = new  ModifierSpeciesReference(2,4);
    ModifierSpeciesReference msr2 = new  ModifierSpeciesReference(2,4);
    msr1.setSpecies("M1");
    msr2.setSpecies("M2");
    R.addModifier(msr1);
    R.addModifier(msr2);
    msr1 = null;
    msr2 = null;
    assertTrue(R.getReactantCount() == 0);
    assertTrue(R.getProductCount() == 0);
    assertTrue(R.getModifierCount() == 2);
    msr1 = R.getModifier(0);
    msr2 = R.getModifier(1);
    assertTrue(msr1.getSpecies().equals("M1"));
    assertTrue(msr2.getSpecies().equals("M2"));
  }

  /**
   * 
   */
  @Test public void test_Reaction_getModifierById()
  {
    ModifierSpeciesReference msr1 = new  ModifierSpeciesReference(2,4);
    ModifierSpeciesReference msr2 = new  ModifierSpeciesReference(2,4);
    msr1.setSpecies("M1");
    msr2.setSpecies("M2");
    R.addModifier(msr1);
    R.addModifier(msr2);
    assertTrue(R.getReactantCount() == 0);
    assertTrue(R.getProductCount() == 0);
    assertTrue(R.getModifierCount() == 2);
    assertNotEquals(R.getModifier("M1"),msr1);
    assertNotEquals(R.getModifier("M2"),msr2);
    assertEquals(R.getModifier("M3"),null);
    msr1 = null;
    msr2 = null;
  }

  /**
   * 
   */
  @Test public void test_Reaction_getProduct()
  {
    SpeciesReference sr1 = new  SpeciesReference(2,4);
    SpeciesReference sr2 = new  SpeciesReference(2,4);
    sr1.setSpecies("P1");
    sr2.setSpecies("P2");
    R.addProduct(sr1);
    R.addProduct(sr2);
    sr1 = null;
    sr2 = null;
    assertTrue(R.getReactantCount() == 0);
    assertTrue(R.getProductCount() == 2);
    assertTrue(R.getModifierCount() == 0);
    sr1 = R.getProduct(0);
    sr2 = R.getProduct(1);
    assertTrue(sr1.getSpecies().equals("P1"));
    assertTrue(sr2.getSpecies().equals("P2"));
  }

  /**
   * 
   */
  @Test public void test_Reaction_getProductById()
  {
    SpeciesReference sr1 = new  SpeciesReference(2,4);
    sr1.setSpecies("P1");
    SpeciesReference sr2 = new  SpeciesReference(2,4);
    sr2.setSpecies("P1");
    R.addProduct(sr1);
    R.addProduct(sr2);
    assertTrue(R.getReactantCount() == 0);
    assertTrue(R.getProductCount() == 2);
    assertTrue(R.getModifierCount() == 0);
    assertNotEquals(R.getProduct("P1"),sr1);
    assertNotEquals(R.getProduct("P2"),sr2);
    assertEquals(R.getProduct("P3"),null);
    sr1 = null;
    sr2 = null;
  }

  /**
   * 
   */
  @Test public void test_Reaction_getReactant()
  {
    SpeciesReference sr1 = new  SpeciesReference(2,4);
    SpeciesReference sr2 = new  SpeciesReference(2,4);
    sr1.setSpecies("R1");
    sr2.setSpecies("R2");
    R.addReactant(sr1);
    R.addReactant(sr2);
    sr1 = null;
    sr2 = null;
    assertTrue(R.getReactantCount() == 2);
    assertTrue(R.getProductCount() == 0);
    assertTrue(R.getModifierCount() == 0);
    sr1 = R.getReactant(0);
    sr2 = R.getReactant(1);
    assertTrue(sr1.getSpecies().equals("R1"));
    assertTrue(sr2.getSpecies().equals("R2"));
  }

  /**
   * 
   */
  @Test public void test_Reaction_getReactantById()
  {
    SpeciesReference sr1 = new  SpeciesReference(2,4);
    sr1.setSpecies("R1");
    SpeciesReference sr2 = new  SpeciesReference(2,4);
    sr2.setSpecies("R2");
    R.addReactant(sr1);
    R.addReactant(sr2);
    assertTrue(R.getReactantCount() == 2);
    assertTrue(R.getProductCount() == 0);
    assertTrue(R.getModifierCount() == 0);
    assertNotEquals(R.getReactant("R1"),sr1);
    assertNotEquals(R.getReactant("R2"),sr2);
    assertEquals(R.getReactant("R3"),null);
    sr1 = null;
    sr2 = null;
  }

  /**
   * 
   */
  @Test public void test_Reaction_removeModifier()
  {
    ModifierSpeciesReference o1,o2,o3;
    o1 = R.createModifier();
    o2 = R.createModifier();
    o3 = R.createModifier();
    o3.setSpecies("test");
    assertTrue(R.removeModifier(0).equals(o1));
    assertTrue(R.getModifierCount() == 2);
    assertTrue(R.removeModifier(0).equals(o2));
    assertTrue(R.getModifierCount() == 1);
    assertTrue(R.removeModifier("test").equals(o3));
    assertTrue(R.getModifierCount() == 0);
    o1 = null;
    o2 = null;
    o3 = null;
  }

  /**
   * 
   */
  @Test public void test_Reaction_removeProduct()
  {
    SpeciesReference o1,o2,o3;
    o1 = R.createProduct();
    o2 = R.createProduct();
    o3 = R.createProduct();
    o3.setSpecies("test");
    assertTrue(R.removeProduct(0).equals(o1));
    assertTrue(R.getProductCount() == 2);
    assertTrue(R.removeProduct(0).equals(o2));
    assertTrue(R.getProductCount() == 1);
    assertTrue(R.removeProduct("test").equals(o3));
    assertTrue(R.getProductCount() == 0);
    o1 = null;
    o2 = null;
    o3 = null;
  }

  /**
   * 
   */
  @Test public void test_Reaction_removeReactant()
  {
    SpeciesReference o1,o2,o3;
    o1 = R.createReactant();
    o2 = R.createReactant();
    o3 = R.createReactant();
    o3.setSpecies("test");
    assertTrue(R.removeReactant(0).equals(o1));
    assertTrue(R.getReactantCount() == 2);
    assertTrue(R.removeReactant(0).equals(o2));
    assertTrue(R.getReactantCount() == 1);
    assertTrue(R.removeReactant("test").equals(o3));
    assertTrue(R.getReactantCount() == 0);
    o1 = null;
    o2 = null;
    o3 = null;
  }

  /**
   * 
   */
  @Test public void test_Reaction_setId()
  {
    String id =  "J1";;
    R.setId(id);
    assertTrue(R.getId().equals(id));
    assertEquals(true, R.isSetId());
    if (R.getId() == id) {
      ;
    }
    {
    }
    R.setId(R.getId());
    assertTrue(R.getId().equals(id));
    R.setId("");
    assertEquals(false, R.isSetId());
    if (R.getId() != null) {
      ;
    }
    {
    }
  }

  /**
   * 
   */
  @Test public void test_Reaction_setName()
  {
    String name =  "MapK_Cascade";;
    R.setName(name);
    assertTrue(R.getName().equals(name));
    assertEquals(true, R.isSetName());
    if (R.getName() == name) {
      ;
    }
    {
    }
    R.setName(R.getName());
    assertTrue(R.getName().equals(name));
    R.setName("");
    assertEquals(false, R.isSetName());
    if (R.getName() != null) {
      ;
    }
    {
    }
  }
}
