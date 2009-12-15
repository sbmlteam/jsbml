/*
 *
 * @file    TestParameter.java
 * @brief   Parameter unit tests
 *
 * @author rodrigue (jsbml conversion)
 * @author  Akiya Jouraku (Java conversion)
 * @author  Ben Bornstein 
 *
 * $Id$
 * $URL$
 *
 * This test file was converted libsbml http://sbml.org/software/libsbml
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

public class TestParameter {

    private Parameter P;

  @Before public void setUp() throws Exception
  {
    P = new  Parameter(2,4);
    if (P == null);
    {
    }
  }

  @After public void tearDown() throws Exception
  {
    P = null;
  }

  @Test public void test_Parameter_create()
  {
//    assertTrue( P.getTypeCode() == libsbml.SBML_PARAMETER );
    assertTrue( P.getMetaId().equals("") == true );
//    assertTrue( P.getNotes() == null );
    assertTrue( P.getAnnotation() == null );
    assertTrue( P.getId().equals("") == true );
    assertTrue( P.getName().equals("") == true );
    assertTrue( P.getUnits().equals("") == true );
    assertTrue( P.getConstant() == true );
    assertEquals( false, P.isSetId() );
    assertEquals( false, P.isSetName() );
    assertEquals( false, P.isSetValue() );
    assertEquals( false, P.isSetUnits() );
  }

  /*
  // TODO : put back if we support constructor with namespaces
  public void test_Parameter_createWithNS()
  {
    XMLNamespaces xmlns = new  XMLNamespaces();
    xmlns.add( "http://www.sbml.org", "testsbml");
    SBMLNamespaces sbmlns = new  SBMLNamespaces(2,1);
    sbmlns.addNamespaces(xmlns);
    Parameter object = new  Parameter(sbmlns);
    assertTrue( object.getTypeCode() == libsbml.SBML_PARAMETER );
    assertTrue( object.getMetaId().equals("") == true );
    assertTrue( object.getNotes() == null );
    assertTrue( object.getAnnotation() == null );
    assertTrue( object.getLevel() == 2 );
    assertTrue( object.getVersion() == 1 );
    assertTrue( object.getNamespaces() != null );
    assertTrue( object.getNamespaces().getLength() == 2 );
    object = null;
  }
*/

  @Test public void test_Parameter_setId()
  {
    String id =  "Km1";;
    P.setId(id);
    assertTrue(P.getId().equals(id));
    assertEquals( true, P.isSetId() );
    if (P.getId() == id);
    {
    }
    P.setId(P.getId());
    assertTrue(P.getId().equals(id));
    P.setId("");
    assertEquals( false, P.isSetId() );
    if (P.getId() != null);
    {
    }
  }

  @Test public void test_Parameter_setName()
  {
    String name =  "Forward_Michaelis_Menten_Constant";;
    P.setName(name);
    assertTrue(P.getName().equals(name));
    assertEquals( true, P.isSetName() );
    if (P.getName() == name);
    {
    }
    P.setName(P.getName());
    assertTrue(P.getName().equals(name));
    P.setName("");
    assertEquals( false, P.isSetName() );
    if (P.getName() != null);
    {
    }
  }

  @Test public void test_Parameter_setUnits()
  {
    String units =  "second";;
    P.setUnits(units);
    assertTrue(P.getUnits().equals(units));
    assertEquals( true, P.isSetUnits() );
    if (P.getUnits() == units);
    {
    }
    P.setUnits(P.getUnits());
    assertTrue(P.getUnits().equals(units));
    P.setUnits("");
    assertEquals( false, P.isSetUnits() );
    if (P.getUnits() != null);
    {
    }
  }
}
