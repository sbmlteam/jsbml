/*
 *
 * @file    TestL3Parameter.java
 * @brief   L3 Parameter unit tests
 *
 * @author rodrigue (jsbml conversion)
 * @author  Akiya Jouraku (Java conversion)
 * @author  Sarah Keating 
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
import org.sbml.jsbml.element.Parameter;

public class TestL3Parameter {

  private Parameter P;

  public boolean isNaN(double x)
  {
    return Double.isNaN(x);
  }

  @Before public void setUp() throws Exception
  {
    P = new  Parameter(3,1);
  }

  @After public void tearDown() throws Exception
  {
    P = null;
  }

  @Test public void test_L3_Parameter_constant()
  {
    assertTrue( P.isSetConstant() == false );
    P.setConstant(true);
    assertTrue( P.getConstant() == true );
    assertTrue( P.isSetConstant() == true );
    P.setConstant(false);
    assertTrue( P.getConstant() == false );
    assertTrue( P.isSetConstant() == true );
  }

  @Test public void test_L3_Parameter_create()
  {
//    assertTrue( P.getTypeCode() == libsbml.SBML_PARAMETER );
    assertTrue( P.getMetaId().equals("") == true );
//    assertTrue( P.getNotes() == null );
    assertTrue( P.getAnnotation() == null );
    assertTrue( P.getId().equals("") == true );
    assertTrue( P.getName().equals("") == true );
    assertTrue( P.getUnits().equals("") == true );
    assertEquals( true, isNaN(P.getValue()) );
    assertTrue( P.getConstant() == false ); // TODO : check with libsbml guys assertTrue( P.getConstant() == true );
    assertEquals( false, P.isSetId() );
    assertEquals( false, P.isSetName() );
    assertEquals( false, P.isSetValue() );
    assertEquals( false, P.isSetUnits() );
    assertEquals( false, P.isSetConstant() );
  }

  /*
   // TODO : put back when implemented or document api changes
  @Test public void test_L3_Parameter_createWithNS()
  {
    XMLNamespaces xmlns = new  XMLNamespaces();
    xmlns.add( "http://www.sbml.org", "testsbml");
    SBMLNamespaces sbmlns = new  SBMLNamespaces(3,1);
    sbmlns.addNamespaces(xmlns);
    Parameter p = new  Parameter(sbmlns);
    assertTrue( p.getTypeCode() == libsbml.SBML_PARAMETER );
    assertTrue( p.getMetaId().equals("") == true );
    assertTrue( p.getNotes() == null );
    assertTrue( p.getAnnotation() == null );
    assertTrue( p.getLevel() == 3 );
    assertTrue( p.getVersion() == 1 );
    assertTrue( p.getNamespaces() != null );
    assertTrue( p.getNamespaces().getLength() == 2 );
    assertTrue( p.getId().equals("") == true );
    assertTrue( p.getName().equals("") == true );
    assertTrue( p.getUnits().equals("") == true );
    assertEquals( true, isnan(p.getValue()) );
    assertTrue( p.getConstant() == true );
    assertEquals( false, p.isSetId() );
    assertEquals( false, p.isSetName() );
    assertEquals( false, p.isSetValue() );
    assertEquals( false, p.isSetUnits() );
    assertEquals( false, p.isSetConstant() );
    p = null;
  }
*/

  @Test public void test_L3_Parameter_hasRequiredAttributes()
  {
	  assertTrue(false); // TODO : we need to implement the method sbase.hasRequiredAttributes() for all element
	  
    Parameter p = new  Parameter(3,1);
//    assertEquals( false, p.hasRequiredAttributes() );
    p.setId( "id");
//    assertEquals( false, p.hasRequiredAttributes() );
    p.setConstant(false);
//    assertEquals( true, p.hasRequiredAttributes() );
    p = null;
  }

  @Test public void test_L3_Parameter_id()
  {
    String id =  "mitochondria";;
    assertEquals( false, P.isSetId() );
    P.setId(id);
    assertTrue(P.getId().equals(id));
    assertEquals( true, P.isSetId() );
    if (P.getId() == id);
    {
    }
  }

  @Test public void test_L3_Parameter_name()
  {
    String name =  "My_Favorite_Factory";;
    assertEquals( false, P.isSetName() );
    P.setName(name);
    assertTrue(P.getName().equals(name));
    assertEquals( true, P.isSetName() );
    if (P.getName() == name);
    {
    }
    P.unsetName();
    assertEquals( false, P.isSetName() );
    if (P.getName() != null);
    {
    }
  }

  @Test public void test_L3_Parameter_units()
  {
    String units =  "volume";;
    assertEquals( false, P.isSetUnits() );
    P.setUnits(units);
    assertTrue(P.getUnits().equals(units));
    assertEquals( true, P.isSetUnits() );
    if (P.getUnits() == units);
    {
    }
    P.unsetUnits();
    assertEquals( false, P.isSetUnits() );
    if (P.getUnits() != null);
    {
    }
  }

  @Test public void test_L3_Parameter_value()
  {
    assertEquals( false, P.isSetValue() );
    assertEquals( true, isNaN(P.getValue()) );
    P.setValue(1.5);
    assertEquals( true, P.isSetValue() );
    assertTrue( P.getValue() == 1.5 );
    P.unsetValue();
    assertEquals( false, P.isSetValue() );
    assertEquals( true, isNaN(P.getValue()) );
  }
}
