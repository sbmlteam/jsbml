/*
 *
 * @file    TestEvent.java
 * @brief   SBML Event unit tests
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

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.Delay;
import org.sbml.jsbml.Event;
import org.sbml.jsbml.EventAssignment;
import org.sbml.jsbml.Trigger;
import org.sbml.jsbml.text.parser.ParseException;

/**
 * @author  Nicolas Rodriguez
 * @author  Akiya Jouraku
 * @author  Ben Bornstein
 * @since 0.8
 */
public class TestEvent {

  /**
   * 
   */
  private Event E;

  /**
   * @throws Exception
   */
  @Before public void setUp() throws Exception
  {
    E = new  Event(2,4);
    if (E == null) {
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
    E = null;
  }

  /**
   * 
   */
  @SuppressWarnings("deprecation")
  @Test public void test_Event_create()
  {
    //    assertTrue(E.getTypeCode() == libsbml.SBML_EVENT);
    assertTrue(E.getMetaId().equals("") == true);
    assertTrue(E.getNotes() == null);
    //    assertTrue(E.getAnnotation() == null);
    assertTrue(E.getId().equals("") == true);
    assertTrue(E.getName().equals("") == true);
    assertEquals(E.getTrigger(),null);
    assertEquals(E.getDelay(),null);
    assertTrue(E.getTimeUnits().equals("") == true);
    assertTrue(E.getEventAssignmentCount() == 0);
  }

  /*
   // TODO: add again if we have constructor with SBMLNamespaces
  @Test public void test_Event_createWithNS()
  {
    XMLNamespaces xmlns = new  XMLNamespaces();
    xmlns.add("http://www.sbml.org", "testsbml");
    SBMLNamespaces sbmlns = new  SBMLNamespaces(2,4);
    sbmlns.addNamespaces(xmlns);
    Event object = new  Event(sbmlns);
    assertTrue(object.getTypeCode() == libsbml.SBML_EVENT);
    assertTrue(object.getMetaId().equals("") == true);
    assertTrue(object.getNotes() == null);
    assertTrue(object.getAnnotation() == null);
    assertTrue(object.getLevel() == 2);
    assertTrue(object.getVersion() == 4);
    assertTrue(object.getNamespaces() != null);
    assertTrue(object.getNamespaces().getLength() == 2);
    object = null;
  }
   */

  /**
   * @throws ParseException
   */
  @Test public void test_Event_full() throws ParseException
  {
    ASTNode math1 = ASTNode.parseFormula("0");
    Trigger trigger = new  Trigger(2,4);
    ASTNode math = ASTNode.parseFormula("0");
    Event e = new  Event(2,4);
    EventAssignment ea = new  EventAssignment(2,4);
    ea.setVariable("k");
    ea.setMath(math);
    trigger.setMath(math1);
    e.setTrigger(trigger);
    e.setId("e1");
    e.setName("Set k2 to zero when P1 <= t");
    e.addEventAssignment(ea);
    assertTrue(e.getEventAssignmentCount() == 1);
    assertTrue(e.getEventAssignment(0) == ea);
    math = null;
    e = null;
  }

  /**
   * 
   */
  @Test public void test_Event_removeEventAssignment()
  {
    EventAssignment o1,o2,o3;
    ASTNode math = null;
    try {
      math = ASTNode.parseFormula("0");
    } catch (ParseException e) {
      assertTrue(false);
    }
    o1 = E.createEventAssignment("t1", math); // We cannot add several time the same element to a list of anything
    o2 = E.createEventAssignment("t2", math);
    o3 = E.createEventAssignment("t3", math);
    o3.setVariable("test");
    assertTrue(E.removeEventAssignment(0).equals(o1));
    assertTrue(E.getEventAssignmentCount() == 2);
    assertTrue(E.removeEventAssignment(0).equals(o2));
    assertTrue(E.getEventAssignmentCount() == 1);
    assertTrue(E.removeEventAssignment("test").equals(o3));
    assertTrue(E.getEventAssignmentCount() == 0);
    o1 = null;
    o2 = null;
    o3 = null;
  }

  /**
   * @throws ParseException
   */
  @Test public void test_Event_setDelay() throws ParseException
  {
    ASTNode math1 = ASTNode.parseFormula("0");
    Delay delay = new  Delay(2,4);
    delay.setMath(math1);
    E.setDelay(delay);
    assertTrue(E.getDelay() != null);
    assertEquals(true, E.isSetDelay());
    if (E.getDelay() == delay) {
      ;
    }
    {
    }
    E.setDelay(E.getDelay());
    // assertTrue(E.getDelay() != delay); // We are not doing a copy of the object, so it is the same
    E.setDelay(null);
    assertEquals(false, E.isSetDelay());
    if (E.getDelay() != null) {
      ;
    }
    {
    }
  }

  /**
   * 
   */
  @Test public void test_Event_setId()
  {
    String id =  "e1";;
    E.setId(id);
    assertTrue(E.getId().equals(id));
    assertEquals(true, E.isSetId());
    if (E.getId() == id) {
      ;
    }
    {
    }
    E.setId(E.getId());
    assertTrue(E.getId().equals(id));
    E.setId("");
    assertEquals(false, E.isSetId());
    if (E.getId() != null) {
      ;
    }
    {
    }
  }

  /**
   * 
   */
  @Test public void test_Event_setName()
  {
    String name =  "Set_k2";;
    E.setName(name);
    assertTrue(E.getName().equals(name));
    assertEquals(true, E.isSetName());
    if (E.getName() == name) {
      ;
    }
    {
    }
    E.setName(E.getName());
    assertTrue(E.getName().equals(name));
    E.setName("");
    assertEquals(false, E.isSetName());
    if (E.getName() != null) {
      ;
    }
    {
    }
  }

  /**
   * 
   */
  @SuppressWarnings("deprecation")
  @Test public void test_Event_setTimeUnits()
  {
    Event E1 = new  Event(2,1);
    String units =  "second";;
    E1.setTimeUnits(units);
    assertTrue(E1.getTimeUnits().equals(units));
    assertEquals(true, E1.isSetTimeUnits());
    if (E1.getTimeUnits() == units) {
      ;
    }
    {
    }
    E1.setTimeUnits(E1.getTimeUnits());
    assertTrue(E1.getTimeUnits().equals(units));
    E1.setTimeUnits("");
    assertEquals(false, E1.isSetTimeUnits());
    if (E1.getTimeUnits() != null) {
      ;
    }
    {
    }
    E1 = null;
  }

  /**
   * @throws ParseException
   */
  @Test public void test_Event_setTrigger() throws ParseException
  {
    ASTNode math1 =  ASTNode.parseFormula("0");
    Trigger trigger = new  Trigger(2,4);
    trigger.setMath(math1);
    E.setTrigger(trigger);
    assertTrue(E.getTrigger() != null);
    assertEquals(true, E.isSetTrigger());
    if (E.getTrigger() == trigger) {
      ;
    }
    {
    }
    E.setTrigger(E.getTrigger());
    // assertTrue(E.getTrigger() != trigger); // We are not doing a copy of the object, so it is the same
    E.setTrigger(null);
    assertEquals(false, E.isSetTrigger());
    if (E.getTrigger() != null) {
      ;
    }
    {
    }
  }

  /**
   * 
   */
  @Test public void test_Event_setUseValuesFromTriggerTime()
  {
    Event object = new  Event(2,4);
    object.setUseValuesFromTriggerTime(false);
    assertTrue(object.getUseValuesFromTriggerTime() == false);
    object.setUseValuesFromTriggerTime(true);
    assertTrue(object.getUseValuesFromTriggerTime() == true);
    object = null;
  }

}
