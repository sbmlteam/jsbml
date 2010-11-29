/*
 *
 * @file    TestCompartment.java
 * @brief   Compartment unit tests
 *
 * @author rodrigue (jsbml conversion)
 * @author  Akiya Jouraku (Java conversion)
 * @author  Ben Bornstein 
 *
 * $Id$
 * $URL$
 *
 * This test file was converted from libsbml http://sbml.org/software/libsbml
 *
 *==================================================================================
 * Copyright (c) 2009 The jsbml team.
 *
 * This file is part of jsbml, the pure java SBML library. Please visit
 * http://sbml.org for more information about SBML, and http://jsbml.sourceforge.net/
 * to get the latest version of jsbml.
 *
 * jsbml is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * jsbml is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with jsbml.  If not, see <http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html>.
 *
 *===================================================================================
 *
 */

package org.sbml.jsbml.test.sbml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sbml.jsbml.Compartment;


// TODO : test what libsbml is returning with getValue(), when the value is not set, probably not NaN ?


public class TestCompartment {
  private Compartment C;

  @Before public void setUp() throws Exception
  {
    C = new  Compartment(2,4);
    if (C == null);
    {
    }
  }

  @After public void tearDown() throws Exception
  {
    C = null;
  }

  
  @Test public void test_Compartment_get_notes_xml_node()
  {
	  assertTrue( C.getNotes() == null );
  }
  
  @Test public void test_Compartment_create()
  {
//    assertTrue( C.getTypeCode() == libsbml.SBML_COMPARTMENT );
    assertTrue( C.getMetaId().equals("") == true );
    assertTrue( C.getNotes() == null );
    assertTrue( C.getNotesString() == null );
    // assertTrue( C.getAnnotation() == null );
    assertTrue( C.getId().equals("") == true );
    assertTrue( C.getName().equals("") == true );
    assertTrue( C.getUnits().equals("") == true );
    assertTrue( C.getOutside().equals("") == true );
    assertTrue( C.getSpatialDimensions() == 3 );
    assertTrue( C.getVolume() == 1.0 );
    assertTrue( C.getConstant() == true );
    assertEquals( false, C.isSetId() );
    assertEquals( false, C.isSetName() );
    assertEquals( false, C.isSetSize() );
    assertEquals( false, C.isSetVolume() );
    assertEquals( false, C.isSetUnits() );
    assertEquals( false, C.isSetOutside() );
  }

  @Test public void test_Compartment_createWith()
  {
    Compartment c = new  Compartment(2,4);
    c.setId( "A");
//    assertTrue( c.getTypeCode() == libsbml.SBML_COMPARTMENT );
    assertTrue( c.getMetaId().equals("") == true );
    assertTrue( c.getNotes() == null );
    // assertTrue( c.getAnnotation() == null );
    assertTrue( c.getName().equals("") == true );
    assertTrue( c.getSpatialDimensions() == 3 );
    assertTrue(c.getId().equals( "A"     ));
    assertTrue( c.getConstant() == true );
    assertEquals( true, c.isSetId() );
    assertEquals( false, c.isSetName() );
    c = null;
  }


  @Test public void test_Compartment_getSpatialDimensions()
  {
    C.setSpatialDimensions(1);
    assertTrue( C.getSpatialDimensions() == 1 );
  }

  @Test public void test_Compartment_getsetConstant()
  {
    C.setConstant(true);
    assertTrue( C.getConstant() == true );
  }

  @SuppressWarnings("deprecation")
@Test public void test_Compartment_getsetType()
  {
    C.setCompartmentType( "cell");
    assertTrue(C.getCompartmentType().equals( "cell" ));
    assertEquals( true, C.isSetCompartmentType() );
    C.unsetCompartmentType();
    assertEquals( false, C.isSetCompartmentType() );
  }

  @Test public void test_Compartment_initDefaults()
  {
    Compartment c = new  Compartment(2,4);
    c.setId( "A");
    c.initDefaults();
    assertTrue(c.getId().equals( "A"));
    assertTrue( c.getName().equals("") == true );
    assertTrue( c.getUnits().equals("") == true );
    assertTrue( c.getOutside().equals("") == true );
    assertTrue( c.getSpatialDimensions() == 3 );
    assertTrue( c.getVolume() == 1.0 );
    assertTrue( c.getConstant() == true );
    assertEquals( true, c.isSetId() );
    assertEquals( false, c.isSetName() );
    assertEquals( false, c.isSetSize() );
    assertEquals( false, c.isSetVolume() );
    assertEquals( false, c.isSetUnits() );
    assertEquals( false, c.isSetOutside() );
    c = null;
  }

  @Test public void test_Compartment_setId()
  {
    String id =  "mitochondria";
    C.setId(id);
    assertTrue(C.getId().equals(id));
    assertEquals( true, C.isSetId() );
    if (C.getId() == id);
    {
    }
    C.setId(C.getId());
    assertTrue(C.getId().equals(id));
    C.setId("");
    assertEquals( false, C.isSetId() );
    if (C.getId() != null);
    {
    }
  }

  @Test public void test_Compartment_setName()
  {
    String name =  "My_Favorite_Factory";
    C.setName(name);
    assertTrue(C.getName().equals(name));
    assertEquals( true, C.isSetName() );
    if (C.getName() == name);
    {
    }
    C.setName(C.getName());
    assertTrue(C.getName().equals(name));
    C.setName("");
    assertEquals( false, C.isSetName() );
    if (C.getName() != null);
    {
    }
  }

  @SuppressWarnings("deprecation")
@Test public void test_Compartment_setOutside()
  {
    String outside =  "cell";
    C.setOutside(outside);
    assertTrue(C.getOutside().equals(outside));
    assertEquals( true, C.isSetOutside() );
    if (C.getOutside() == outside);
    {
    }
    C.setOutside(C.getOutside());
    assertTrue(C.getOutside().equals(outside));
    C.setOutside("");
    assertEquals( false, C.isSetOutside() );
    if (C.getOutside() != null);
    {
    }
  }

  @Test public void test_Compartment_setUnits()
  {
    String units =  "volume";
    C.setUnits(units);
    assertTrue(C.getUnits().equals(units));
    assertEquals( true, C.isSetUnits() );
    C.setUnits(C.getUnits());
    assertTrue(C.getUnits().equals(units));
    C.setUnits("");
    assertEquals( false, C.isSetUnits() );
    assertTrue(C.getUnits() == ""); // in libsbml it returns null
  }

  @Test public void test_Compartment_unsetSize()
  {
    C.setSize(0.2);
    assertTrue( C.getSize() == 0.2 );
    assertEquals( true, C.isSetSize() );
    C.unsetSize();
    assertEquals( false, C.isSetSize() );
  }



}
