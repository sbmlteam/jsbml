/*
 *
 * @file    TestModel.java
 * @brief   SBML Model unit tests
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

import java.util.Calendar;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.AlgebraicRule;
import org.sbml.jsbml.AssignmentRule;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.CompartmentType;
import org.sbml.jsbml.Constraint;
import org.sbml.jsbml.Creator;
import org.sbml.jsbml.Event;
import org.sbml.jsbml.EventAssignment;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.History;
import org.sbml.jsbml.InitialAssignment;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.LocalParameter;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.ModifierSpeciesReference;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.RateRule;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.Rule;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.SpeciesType;
import org.sbml.jsbml.Trigger;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.Unit.Kind;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.text.parser.ParseException;

/**
 * @author  Nicolas Rodriguez
 * @author  Akiya Jouraku
 * @author  Ben Bornstein
 * @since 0.8
 *
 */
@SuppressWarnings("deprecation")
public class TestModel {

  /**
   * @param a
   * @param b
   */
  static void assertNotEquals(Object a, Object b) {
    assertTrue(!a.equals(b));
  }

  /**
   * 
   */
  private Model M;

  /**
   * @throws Exception
   */
  @Before
  public void setUp() throws Exception {
    M = new Model(2, 4);
  }

  /**
   * @throws Exception
   */
  @After
  public void tearDown() throws Exception {
    M = null;
  }

  /**
   * @throws ParseException
   */
  @Test
  public void test_KineticLaw_getParameterById() throws ParseException {
    Parameter k1 = new Parameter(2, 4);
    Parameter k2 = new Parameter(2, 4);
    k1.setId("k1");
    k2.setId("k2");
    k1.setValue(3.14);
    k2.setValue(2.72);
    M.addParameter(k1);
    M.addParameter(k2);
    Reaction r1 = new Reaction(2, 4);
    r1.setId("reaction_1");
    KineticLaw kl = new KineticLaw(2, 4);
    kl.setFormula("k1 * X0");
    LocalParameter k3 = new LocalParameter(2, 4);
    LocalParameter k4 = new LocalParameter(2, 4);
    k3.setId("k1");
    k4.setId("k2");
    k3.setValue(2.72);
    k4.setValue(3.14);
    kl.addParameter(k3);
    kl.addParameter(k4);
    r1.setKineticLaw(kl);
    M.addReaction(r1);
    KineticLaw kl1 = M.getReaction(0).getKineticLaw();
    // assertTrue(!kl1.getParameter("k1").equals(k3)); // TODO: compare Parameter and LocalParameter
    // assertTrue(!kl1.getParameter("k1").equals(k1)); // We are not doinga clone of the object and even it will return true
    // assertTrue(!kl1.getParameter("k2").equals(k4)); // TODO: compare Parameter and LocalParameter
    assertEquals(kl1.getParameter("k3"), null);
  }

  /**
   * 
   */
  @Test
  public void test_Model_addCompartment() {
    Compartment c = new Compartment(2, 4);
    c.setId("c");
    M.addCompartment(c);
    assertTrue(M.getCompartmentCount() == 1);
  }

  /**
   * 
   */
  @Test
  public void test_Model_addParameter() {
    Parameter p = new Parameter(2, 4);
    p.setId("p");
    M.addParameter(p);
    assertTrue(M.getParameterCount() == 1);
  }

  /**
   * 
   */
  @Test
  public void test_Model_addReaction() {
    Reaction r = new Reaction(2, 4);
    r.setId("r");
    M.addReaction(r);
    assertTrue(M.getReactionCount() == 1);
  }

  /**
   * @throws ParseException
   */
  @Test
  public void test_Model_addRules() throws ParseException {
    Rule r1 = new AlgebraicRule(2, 4);
    AssignmentRule r2 = new AssignmentRule(2, 4);
    RateRule r3 = new RateRule(2, 4);
    r2.setVariable("r2");
    r3.setVariable("r3");
    r1.setMath(ASTNode.parseFormula("2"));
    r2.setMath(ASTNode.parseFormula("2"));
    r3.setMath(ASTNode.parseFormula("2"));
    M.addRule(r1);
    M.addRule(r2);
    M.addRule(r3);
    assertTrue(M.getRuleCount() == 3);
  }

  /**
   * 
   */
  @Test
  public void test_Model_addSpecies() {
    Species s = new Species(2, 4);
    s.setId("s");
    s.setCompartment("c");
    M.addSpecies(s);
    assertTrue(M.getSpeciesCount() == 1);
  }

  /**
   * 
   */
  @Test
  public void test_Model_add_get_Event() {
    ASTNode math = null;
    try {
      math = ASTNode.parseFormula("0");
    } catch (ParseException e) {
      assertTrue(false);
    }

    Event e1 = new Event(2, 4);
    Event e2 = new Event(2, 4);
    Trigger t = new Trigger(2, 4);
    e1.setTrigger(t);
    e2.setTrigger(t); // TODO: Document the difference: Setting the same trigger object there in jsbml !!!
    e1.createEventAssignment("k1", math);
    e2.createEventAssignment("k2", math);
    M.addEvent(e1);
    M.addEvent(e2);
    assertTrue(M.getEventCount() == 2);
    // assertTrue(!M.getEvent(0).equals(e1)); // the eventAssignement would be the same in jsbml
    // assertTrue(!M.getEvent(1).equals(e2));
    assertEquals(M.getEvent(2), null);
    assertEquals(M.getEvent(-2), null);
  }

  /**
   * @throws ParseException
   */
  @Test
  public void test_Model_add_get_FunctionDefinitions() throws ParseException {
    FunctionDefinition fd1 = new FunctionDefinition(2, 4);
    FunctionDefinition fd2 = new FunctionDefinition(2, 4);
    fd1.setId("fd1");
    fd2.setId("fd2");
    fd1.setMath(ASTNode.parseFormula("lambda(2)"));
    fd2.setMath(ASTNode.parseFormula("lambda(x, x+2)"));
    M.addFunctionDefinition(fd1);
    M.addFunctionDefinition(fd2);
    assertTrue(M.getFunctionDefinitionCount() == 2);
    // assertTrue(!M.getFunctionDefinition(0).equals(fd1)); // would be the same in jsbml
    // assertTrue(!M.getFunctionDefinition(1).equals(fd2));
    assertEquals(M.getFunctionDefinition(2), null);
    assertEquals(M.getFunctionDefinition(-2), null);
  }

  /**
   * 
   */
  @Test
  public void test_Model_add_get_UnitDefinitions() {
    UnitDefinition ud1 = new UnitDefinition(2, 4);
    UnitDefinition ud2 = new UnitDefinition(2, 4);
    ud1.setId("ud1");
    ud2.setId("ud2");
    ud1.createUnit(Kind.LITRE);
    ud2.createUnit(Kind.METRE);
    M.addUnitDefinition(ud1);
    M.addUnitDefinition(ud2);
    assertTrue(M.getUnitDefinitionCount() == 2);
    // assertTrue(!M.getUnitDefinition(0).equals(ud1)); // would be the same in jsbml
    // assertTrue(!M.getUnitDefinition(1).equals(ud2));
    assertEquals(M.getUnitDefinition(2), null);
    assertEquals(M.getUnitDefinition(-2), null);
  }

  /**
   * 
   */
  @Test
  public void test_Model_create() {
    // assertTrue(M.getTypeCode() == libsbml.SBML_MODEL);
    assertTrue(M.getMetaId().equals("") == true);
    assertTrue(M.getNotes() == null);
    //assertTrue(M.getAnnotation() == null);
    assertTrue(M.getId().equals("") == true);
    assertTrue(M.getName().equals("") == true);
    assertEquals(false, M.isSetId());
    assertEquals(false, M.isSetName());
    assertTrue(M.getUnitDefinitionCount() == 0);
    assertTrue(M.getCompartmentCount() == 0);
    assertTrue(M.getSpeciesCount() == 0);
    assertTrue(M.getParameterCount() == 0);
    assertTrue(M.getReactionCount() == 0);
  }

  /**
   * 
   */
  @Test
  public void test_Model_createAlgebraicRule() {
    Rule ar = M.createAlgebraicRule();
    assertTrue(ar != null);
    assertTrue(M.getRuleCount() == 1);
    assertEquals(M.getRule(0), ar);
  }

  /**
   * 
   */
  @Test
  public void test_Model_createAssignmentRule() {
    Rule ar = M.createAssignmentRule();
    assertTrue(ar != null);
    assertTrue(M.getRuleCount() == 1);
    assertEquals(M.getRule(0), ar);
  }

  /**
   * 
   */
  @Test
  public void test_Model_createCompartment() {
    Compartment c = M.createCompartment();
    assertTrue(c != null);
    assertTrue(M.getCompartmentCount() == 1);
    assertTrue(c.equals(c));
    assertTrue(M.getCompartment(0).equals(c)); // TODO: check why this is failing
  }

  /**
   * 
   */
  @Test
  public void test_Model_createCompartmentType() {
    CompartmentType c = M.createCompartmentType();
    assertTrue(c != null);
    assertTrue(M.getCompartmentTypeCount() == 1);
    assertEquals(M.getCompartmentType(0), c);
  }

  /**
   * 
   */
  @Test
  public void test_Model_createConstraint() {
    Constraint c = M.createConstraint();
    assertTrue(c != null);
    assertTrue(M.getConstraintCount() == 1);
    assertEquals(M.getConstraint(0), c);
  }

  /**
   * 
   */
  @Test
  public void test_Model_createEvent() {
    Event e = M.createEvent();
    assertTrue(e != null);
    assertTrue(M.getEventCount() == 1);
    assertEquals(M.getEvent(0), e);
  }

  /**
   * 
   */
  @Test
  public void test_Model_createEventAssignment() {
    Event e;
    EventAssignment ea;
    M.createEvent();
    M.createEvent();
    ea = M.createEventAssignment();
    assertTrue(ea != null);
    assertTrue(M.getEventCount() == 2);
    e = M.getEvent(1);
    assertTrue(e.getEventAssignmentCount() == 1);
    assertEquals(e.getEventAssignment(0), ea);
  }

  /**
   * 
   */
  @Test
  public void test_Model_createEventAssignment_noEvent() {
    assertTrue(M.getEventCount() == 0);
    assertTrue(M.createEventAssignment() == null);
  }

  /**
   * 
   */
  @Test
  public void test_Model_createFunctionDefinition() {
    FunctionDefinition fd = M.createFunctionDefinition();
    assertTrue(fd != null);
    assertTrue(M.getFunctionDefinitionCount() == 1);
    assertEquals(M.getFunctionDefinition(0), fd);
  }

  /**
   * 
   */
  @Test
  public void test_Model_createInitialAssignment() {
    InitialAssignment c = M.createInitialAssignment();
    assertTrue(c != null);
    assertTrue(M.getInitialAssignmentCount() == 1);
    assertEquals(M.getInitialAssignment(0), c);
  }

  /**
   * 
   */
  @Test
  public void test_Model_createKineticLaw() {
    Reaction r;
    KineticLaw kl;
    M.createReaction();
    M.createReaction();
    kl = M.createKineticLaw();
    assertTrue(kl != null);
    assertTrue(M.getReactionCount() == 2);
    r = M.getReaction(0);
    assertEquals(r.getKineticLaw(), null);
    r = M.getReaction(1);
    assertEquals(r.getKineticLaw(), kl);
  }

  /**
   * 
   */
  @Test
  public void test_Model_createKineticLawParameter() {
    Reaction r;
    KineticLaw kl;
    LocalParameter p;
    M.createReaction();
    M.createReaction();
    M.createKineticLaw();
    p = M.createKineticLawParameter();
    assertTrue(M.getReactionCount() == 2);
    r = M.getReaction(0);
    assertEquals(r.getKineticLaw(), null);
    r = M.getReaction(1);
    assertTrue(r.getKineticLaw() != null);
    kl = r.getKineticLaw();
    assertTrue(kl.getLocalParameterCount() == 1);
    assertEquals(kl.getLocalParameter(0), p);
  }

  /**
   * 
   */
  @Test
  public void test_Model_createKineticLawParameter_noKineticLaw() {
    Reaction r;
    r = M.createReaction();
    assertEquals(r.getKineticLaw(), null);
    assertTrue(M.createKineticLawParameter() == null);
  }

  /**
   * 
   */
  @Test
  public void test_Model_createKineticLawParameter_noReaction() {
    assertTrue(M.getReactionCount() == 0);
    assertTrue(M.createKineticLawParameter() == null);
  }

  /**
   * 
   */
  @Test
  public void test_Model_createKineticLaw_alreadyExists() {
    Reaction r;
    KineticLaw kl;
    r = M.createReaction();
    kl = M.createKineticLaw();
    assertEquals(r.getKineticLaw(), kl);
  }

  /**
   * 
   */
  @Test
  public void test_Model_createKineticLaw_noReaction() {
    assertTrue(M.getReactionCount() == 0);
    assertTrue(M.createKineticLaw() == null);
  }

  /**
   * 
   */
  @Test
  public void test_Model_createModifier() {
    Reaction r;
    ModifierSpeciesReference msr;
    M.createReaction();
    M.createReaction();
    msr = M.createModifier();
    assertTrue(msr != null);
    assertTrue(M.getReactionCount() == 2);
    r = M.getReaction(1);
    assertTrue(r.getModifierCount() == 1);
    assertEquals(r.getModifier(0), msr);
  }

  /**
   * 
   */
  @Test
  public void test_Model_createModifier_noReaction() {
    assertTrue(M.getReactionCount() == 0);
    assertTrue(M.createModifier() == null);
  }

  /**
   * 
   */
  @Test
  public void test_Model_createParameter() {
    Parameter p = M.createParameter();
    assertTrue(p != null);
    assertTrue(M.getParameterCount() == 1);
    assertEquals(M.getParameter(0), p); // TODO: check why this failing
  }

  /**
   * 
   */
  @Test
  public void test_Model_createProduct() {
    Reaction r;
    SpeciesReference sr;
    M.createReaction();
    M.createReaction();
    sr = M.createProduct();
    assertTrue(sr != null);
    assertTrue(M.getReactionCount() == 2);
    r = M.getReaction(1);
    assertTrue(r.getProductCount() == 1);
    assertEquals(r.getProduct(0), sr);
  }

  /**
   * 
   */
  @Test
  public void test_Model_createProduct_noReaction() {
    assertTrue(M.getReactionCount() == 0);
    assertTrue(M.createProduct() == null);
  }

  /**
   * 
   */
  @Test
  public void test_Model_createRateRule() {
    Rule rr = M.createRateRule();
    assertTrue(rr != null);
    assertTrue(M.getRuleCount() == 1);
    assertEquals(M.getRule(0), rr);
  }

  /**
   * 
   */
  @Test
  public void test_Model_createReactant() {
    Reaction r;
    SpeciesReference sr;
    M.createReaction();
    M.createReaction();
    sr = M.createReactant();
    assertTrue(sr != null);
    assertTrue(M.getReactionCount() == 2);
    r = M.getReaction(1);
    assertTrue(r.getReactantCount() == 1);
    assertEquals(r.getReactant(0), sr);
  }

  /**
   * 
   */
  @Test
  public void test_Model_createReactant_noReaction() {
    assertTrue(M.getReactionCount() == 0);
    assertTrue(M.createReactant() == null);
  }

  /**
   * 
   */
  @Test
  public void test_Model_createReaction() {
    Reaction r = M.createReaction();
    assertTrue(r != null);
    assertTrue(M.getReactionCount() == 1);
    assertEquals(M.getReaction(0), r);
  }

  /**
   * 
   */
  @Test
  public void test_Model_createSpecies() {
    Species s = M.createSpecies();
    assertTrue(s != null);
    assertTrue(M.getSpeciesCount() == 1);
    assertTrue(M.getSpecies(0).equals(s) == true);
  }

  /**
   * 
   */
  @Test
  public void test_Model_createSpeciesType() {
    SpeciesType c = M.createSpeciesType();
    assertTrue(c != null);
    assertTrue(M.getSpeciesTypeCount() == 1);
    assertEquals(M.getSpeciesType(0), c);
  }

  /**
   * 
   */
  @Test
  public void test_Model_createUnit() {
    UnitDefinition ud;
    Unit u;
    M.createUnitDefinition();
    M.createUnitDefinition();
    u = M.createUnit(Kind.LITRE);
    assertTrue(u != null);
    assertTrue(M.getUnitDefinitionCount() == 2);
    ud = M.getUnitDefinition(1);
    assertTrue(ud.getUnitCount() == 1);
    assertEquals(ud.getUnit(0), u);
  }

  /**
   * 
   */
  @Test
  public void test_Model_createUnitDefinition() {
    UnitDefinition ud = M.createUnitDefinition();
    assertTrue(ud != null);
    assertTrue(M.getUnitDefinitionCount() == 1);
    assertEquals(M.getUnitDefinition(0), ud);
  }

  /**
   * 
   */
  @Test
  public void test_Model_createUnit_noUnitDefinition() {
    assertTrue(M.getUnitDefinitionCount() == 0);
    assertTrue(M.createUnit() == null);
  }

  /*
   * @Test public void test_Model_createWithNS() { XMLNamespaces xmlns = new
   * XMLNamespaces(); xmlns.add("http://www.sbml.org", "testsbml");
   * SBMLNamespaces sbmlns = new SBMLNamespaces(2,1);
   * sbmlns.addNamespaces(xmlns); Model object = new Model(sbmlns);
   * assertTrue(object.getTypeCode() == libsbml.SBML_MODEL); assertTrue(
   * object.getMetaId().equals("") == true); assertTrue(object.getNotes() ==
   * null); assertTrue(object.getAnnotation() == null); assertTrue(
   * object.getLevel() == 2); assertTrue(object.getVersion() == 1);
   * assertTrue(object.getNamespaces() != null); assertTrue(
   * object.getNamespaces().getLength() == 2); object = null; }
   */

  /**
   * 
   */
  @Test
  public void test_Model_getCompartment() {
    Compartment c1 = new Compartment(2, 4);
    Compartment c2 = new Compartment(2, 4);
    c1.setId("A");
    c2.setId("B");
    M.addCompartment(c1);
    M.addCompartment(c2);
    assertTrue(M.getCompartmentCount() == 2);
    c1 = M.getCompartment(0);
    c2 = M.getCompartment(1);
    assertTrue(c1.getId().equals("A"));
    assertTrue(c2.getId().equals("B"));
  }

  /**
   * 
   */
  @Test
  public void test_Model_getCompartmentById() {
    Compartment c1 = new Compartment(2, 4);
    Compartment c2 = new Compartment(2, 4);
    c1.setId("A");
    c2.setId("B");
    M.addCompartment(c1);
    M.addCompartment(c2);
    assertTrue(M.getCompartmentCount() == 2);
    // assertTrue(!M.getCompartment("A").equals(c1)); // would be the same in jsbml
    // assertTrue(!M.getCompartment("B").equals(c2));
    assertTrue(M.getCompartment("B").equals(c2));
    assertTrue(M.getCompartment("C") == null);
  }

  /**
   * 
   */
  @Test
  public void test_Model_getEventById() {
    Event e1 = new Event(2, 4);
    Event e2 = new Event(2, 4);
    Trigger t = new Trigger(2, 4);
    e1.setTrigger(t);
    e2.setTrigger(t);
    e1.createEventAssignment();
    e2.createEventAssignment();
    e1.setId("e1");
    e2.setId("e2");
    M.addEvent(e1);
    M.addEvent(e2);
    assertTrue(M.getEventCount() == 2);
    // assertTrue(!M.getEvent("e1").equals(e1));
    // assertTrue(!M.getEvent("e2").equals(e2));
    assertEquals(M.getEvent("e3"), null);
  }

  /**
   * @throws ParseException
   */
  @Test
  public void test_Model_getFunctionDefinitionById() throws ParseException {
    FunctionDefinition fd1 = new FunctionDefinition(2, 4);
    FunctionDefinition fd2 = new FunctionDefinition(2, 4);
    fd1.setId("sin");
    fd2.setId("cos");
    fd1.setMath(ASTNode.parseFormula("lambda(2)"));
    fd2.setMath(ASTNode.parseFormula("lambda(2)"));
    M.addFunctionDefinition(fd1);
    M.addFunctionDefinition(fd2);
    assertTrue(M.getFunctionDefinitionCount() == 2);
    //assertTrue(!M.getFunctionDefinition("sin").equals(fd1));
    // assertTrue(!M.getFunctionDefinition("cos").equals(fd2));
    assertEquals(M.getFunctionDefinition("tan"), null);
  }

  /**
   * 
   */
  @Test
  public void test_Model_getSpeciesWithBoundaryCondition() {
    Species s1 = new Species(2, 4);
    Species s2 = new Species(2, 4);
    Species s3 = new Species(2, 4);
    s1.setId("s1");
    s2.setId("s2");
    s3.setId("s3");
    s1.setCompartment("c1");
    s2.setCompartment("c2");
    s3.setCompartment("c3");
    s1.setBoundaryCondition(true);
    s2.setBoundaryCondition(false);
    s3.setBoundaryCondition(true);
    assertTrue(M.getSpeciesCount() == 0);
    assertTrue(M.getSpeciesWithBoundaryConditionCount() == 0);
    M.addSpecies(s1);
    assertTrue(M.getSpeciesCount() == 1);
    assertTrue(M.getSpeciesWithBoundaryConditionCount() == 1);
    M.addSpecies(s2);
    assertTrue(M.getSpeciesCount() == 2);
    assertTrue(M.getSpeciesWithBoundaryConditionCount() == 1);
    M.addSpecies(s3);
    assertTrue(M.getSpeciesCount() == 3);
    assertTrue(M.getSpeciesWithBoundaryConditionCount() == 2);
  }

  /**
   * 
   */
  @Test
  public void test_Model_getParameter() {
    Parameter p1 = new Parameter(2, 4);
    Parameter p2 = new Parameter(2, 4);
    p1.setId("Km1");
    p2.setId("Km2");
    M.addParameter(p1);
    M.addParameter(p2);
    assertTrue(M.getParameterCount() == 2);
    p1 = M.getParameter(0);
    p2 = M.getParameter(1);
    assertTrue(p1.getId().equals("Km1"));
    assertTrue(p2.getId().equals("Km2"));
  }

  /**
   * 
   */
  @Test
  public void test_Model_getParameterById() {
    Parameter p1 = new Parameter(2, 4);
    Parameter p2 = new Parameter(2, 4);
    p1.setId("Km1");
    p2.setId("Km2");
    M.addParameter(p1);
    M.addParameter(p2);
    assertTrue(M.getParameterCount() == 2);
    // assertNotEquals(M.getParameter("Km1"), p1);
    // assertNotEquals(M.getParameter("Km2"), p2);
    assertEquals(M.getParameter("Km3"), null);
  }

  /**
   * 
   */
  @Test
  public void test_Model_getReaction() {
    Reaction r1 = new Reaction(2, 4);
    Reaction r2 = new Reaction(2, 4);
    r1.setId("reaction_1");
    r2.setId("reaction_2");
    M.addReaction(r1);
    M.addReaction(r2);
    assertTrue(M.getReactionCount() == 2);
    r1 = M.getReaction(0);
    r2 = M.getReaction(1);
    assertTrue(r1.getId().equals("reaction_1"));
    assertTrue(r2.getId().equals("reaction_2"));
  }

  /**
   * 
   */
  @Test
  public void test_Model_getReactionById() {
    Reaction r1 = new Reaction(2, 4);
    Reaction r2 = new Reaction(2, 4);
    r1.setId("reaction_1");
    r2.setId("reaction_2");
    M.addReaction(r1);
    M.addReaction(r2);
    assertTrue(M.getReactionCount() == 2);
    // assertNotEquals(M.getReaction("reaction_1"), r1);
    // assertNotEquals(M.getReaction("reaction_2"), r2);
    assertEquals(M.getReaction("reaction_2"), r2);
    assertEquals(M.getReaction("reaction_3"), null);
  }

  // TODO: API changes, setVariable function is not in the Rule Class.

  /**
   * @throws ParseException
   */
  @Test
  public void test_Model_getRules() throws ParseException {
    Rule ar = new AlgebraicRule(2, 4);
    AssignmentRule scr = new AssignmentRule(2, 4);
    AssignmentRule cvr = new AssignmentRule(2, 4);
    AssignmentRule pr = new AssignmentRule(2, 4);
    scr.setVariable("r2");
    cvr.setVariable("r3");
    pr.setVariable("r4");
    ar.setFormula("x + 1");
    // TODO: parser automatically creates the most compact
    // ast but this might not alwasy be desirable. fix. 
    scr.setFormula("k * t/(1 + k)");
    cvr.setFormula("0.10 * t");
    pr.setFormula("k3/k2");
    M.addRule(ar);
    M.addRule(scr);
    M.addRule(cvr);
    M.addRule(pr);
    assertTrue(M.getRuleCount() == 4);
    ar = M.getRule(0);
    scr = (AssignmentRule) M.getRule(1);
    cvr = (AssignmentRule) M.getRule(2);
    pr = (AssignmentRule) M.getRule(3);
    scr.getFormula();
    assertTrue(ar.getFormula().equals("x+1")); // .equals("x + 1")
    assertTrue(scr.getFormula().equals("(k*t)/(1+k)") || scr.getFormula().equals("k*t/(1+k)")); // .equals("k * t/(1 + k)"));
    assertTrue(cvr.getFormula().equals("0.1*t")); // .equals("0.10 * t"));
    assertTrue(pr.getFormula().equals("k3/k2"));
  }

  /**
   * 
   */
  @Test
  public void test_Model_getSpecies() {
    Species s1 = new Species(2, 4);
    Species s2 = new Species(2, 4);
    s1.setId("Glucose");
    s2.setId("Glucose_6_P");
    s1.setCompartment("c");
    s2.setCompartment("c");
    M.addSpecies(s1);
    M.addSpecies(s2);
    assertTrue(M.getSpeciesCount() == 2);
    s1 = M.getSpecies(0);
    s2 = M.getSpecies(1);
    assertTrue(s1.getId().equals("Glucose"));
    assertTrue(s2.getId().equals("Glucose_6_P"));
  }

  /**
   * 
   */
  @Test
  public void test_Model_getSpeciesById() {
    Species s1 = new Species(2, 4);
    Species s2 = new Species(2, 4);
    s1.setId("Glucose");
    s2.setId("Glucose_6_P");
    s1.setCompartment("c");
    s2.setCompartment("c");
    M.addSpecies(s1);
    M.addSpecies(s2);
    assertTrue(M.getSpeciesCount() == 2);
    // assertTrue(M.getSpecies("Glucose").equals(s1) != true);
    // assertTrue(M.getSpecies("Glucose_6_P").equals(s2) != true);
    assertTrue(M.getSpecies("Glucose_6_P").equals(s2));
    assertTrue(M.getSpecies("Glucose2") == null);
  }

  /**
   * 
   */
  @Test
  public void test_Model_getUnitDefinition() {
    UnitDefinition ud1 = new UnitDefinition(2, 4);
    UnitDefinition ud2 = new UnitDefinition(2, 4);
    ud1.setId("mmls");
    ud2.setId("volume");
    ud1.createUnit(Kind.LITRE);
    ud2.createUnit(Kind.LITRE);
    M.addUnitDefinition(ud1);
    M.addUnitDefinition(ud2);
    assertTrue(M.getUnitDefinitionCount() == 2);
    ud1 = M.getUnitDefinition(0);
    ud2 = M.getUnitDefinition(1);
    assertTrue(ud1.getId().equals("mmls"));
    assertTrue(ud2.getId().equals("volume"));
  }

  /**
   * 
   */
  @Test
  public void test_Model_getUnitDefinitionById() {
    UnitDefinition ud1 = new UnitDefinition(2, 4);
    UnitDefinition ud2 = new UnitDefinition(2, 4);
    ud1.setId("mmls");
    ud2.setId("volume");
    ud1.createUnit(Kind.LITRE);
    ud2.createUnit(Kind.LITRE);
    M.addUnitDefinition(ud1);
    M.addUnitDefinition(ud2);
    assertTrue(M.getUnitDefinitionCount() == 2);
    // assertNotEquals(M.getUnitDefinition("mmls"), ud1);
    // assertNotEquals(M.getUnitDefinition("volume"), ud2);
    assertEquals(M.getUnitDefinition("rototillers"), null);
  }

  /**
   * 
   */
  @Test
  public void test_Model_removeCompartment() {
    Compartment o1, o2, o3;
    o1 = M.createCompartment();
    o2 = M.createCompartment();
    o3 = M.createCompartment();
    o3.setId("test");
    assertTrue(M.removeCompartment(0).equals(o1));
    assertTrue(M.getCompartmentCount() == 2);
    assertTrue(M.removeCompartment(0).equals(o2));
    assertTrue(M.getCompartmentCount() == 1);
    assertTrue(M.removeCompartment("test").equals(o3));
    assertTrue(M.getCompartmentCount() == 0);
    o1 = null;
    o2 = null;
    o3 = null;
  }

  /**
   * 
   */
  @Test
  public void test_Model_removeCompartmentType() {
    CompartmentType o1, o2, o3;
    o1 = M.createCompartmentType();
    o2 = M.createCompartmentType();
    o3 = M.createCompartmentType();
    o3.setId("test");
    assertTrue(M.removeCompartmentType(0).equals(o1));
    assertTrue(M.getCompartmentTypeCount() == 2);
    assertTrue(M.removeCompartmentType(0).equals(o2));
    assertTrue(M.getCompartmentTypeCount() == 1);
    assertTrue(M.removeCompartmentType("test").equals(o3));
    assertTrue(M.getCompartmentTypeCount() == 0);
    o1 = null;
    o2 = null;
    o3 = null;
  }

  /**
   * 
   */
  @Test
  public void test_Model_removeConstraint() {
    Constraint o1, o2, o3;
    o1 = M.createConstraint(); // TODO: document diff: cannot add twice the same element
    o1.setMetaId("c1");
    o2 = M.createConstraint();
    o2.setMetaId("c2");
    o3 = M.createConstraint();
    o3.setMetaId("c3");
    assertTrue(M.removeConstraint(0).equals(o1));
    assertTrue(M.getConstraintCount() == 2);
    assertTrue(M.removeConstraint(0).equals(o2));
    assertTrue(M.getConstraintCount() == 1);
    assertTrue(M.removeConstraint(0).equals(o3));
    assertTrue(M.getConstraintCount() == 0);
    o1 = null;
    o2 = null;
    o3 = null;
  }

  /**
   * 
   */
  @Test
  public void test_Model_removeEvent() {
    Event o1, o2, o3;
    o1 = M.createEvent();
    o2 = M.createEvent();
    o3 = M.createEvent();
    o3.setId("test");
    assertTrue(M.removeEvent(0).equals(o1));
    assertTrue(M.getEventCount() == 2);
    assertTrue(M.removeEvent(0).equals(o2));
    assertTrue(M.getEventCount() == 1);
    assertTrue(M.removeEvent("test").equals(o3));
    assertTrue(M.getEventCount() == 0);
    o1 = null;
    o2 = null;
    o3 = null;
  }

  /**
   * 
   */
  @Test
  public void test_Model_removeFunctionDefinition() {
    FunctionDefinition o1, o2, o3;
    o1 = M.createFunctionDefinition();
    o2 = M.createFunctionDefinition();
    o3 = M.createFunctionDefinition();
    o3.setId("test");
    assertTrue(M.removeFunctionDefinition(0).equals(o1));
    assertTrue(M.getFunctionDefinitionCount() == 2);
    assertTrue(M.removeFunctionDefinition(0).equals(o2));
    assertTrue(M.getFunctionDefinitionCount() == 1);
    assertTrue(M.removeFunctionDefinition("test").equals(o3));
    assertTrue(M.getFunctionDefinitionCount() == 0);
    o1 = null;
    o2 = null;
    o3 = null;
  }

  /**
   * 
   */
  @Test
  public void test_Model_removeInitialAssignment() {
    InitialAssignment o1, o2, o3;
    o1 = M.createInitialAssignment();
    o1.setMetaId("c1");
    o2 = M.createInitialAssignment();
    o2.setMetaId("c2");
    o3 = M.createInitialAssignment();
    o3.setMetaId("c3");
    o3.setVariable("test");
    assertTrue(M.removeInitialAssignment(0).equals(o1));
    assertTrue(M.getInitialAssignmentCount() == 2);
    assertTrue(M.removeInitialAssignment(0).equals(o2));
    assertTrue(M.getInitialAssignmentCount() == 1);
    //		assertTrue(M.removeInitialAssignment("test").equals(o2));
    //		assertTrue(M.getInitialAssignmentCount() == 0);
    o1 = null;
    o2 = null;
    o3 = null;
  }

  /**
   * 
   */
  @Test
  public void test_Model_removeParameter() {
    Parameter o1, o2, o3;
    o1 = M.createParameter();
    o2 = M.createParameter();
    o3 = M.createParameter();
    o3.setId("test");
    assertTrue(M.removeParameter(0).equals(o1));
    assertTrue(M.getParameterCount() == 2);
    assertTrue(M.removeParameter(0).equals(o2));
    assertTrue(M.getParameterCount() == 1);
    assertTrue(M.removeParameter("test").equals(o3));
    assertTrue(M.getParameterCount() == 0);
    o1 = null;
    o2 = null;
    o3 = null;
  }

  /**
   * 
   */
  @Test
  public void test_Model_removeReaction() {
    Reaction o1, o2, o3;
    o1 = M.createReaction();
    o2 = M.createReaction();
    o3 = M.createReaction();
    o3.setId("test");
    assertTrue(M.removeReaction(0).equals(o1));
    assertTrue(M.getReactionCount() == 2);
    assertTrue(M.removeReaction(0).equals(o2));
    assertTrue(M.getReactionCount() == 1);
    assertTrue(M.removeReaction("test").equals(o3));
    assertTrue(M.getReactionCount() == 0);
    o1 = null;
    o2 = null;
    o3 = null;
  }

  /**
   * 
   */
  @Test
  public void test_Model_removeRule() {
    Rule o1, o2;
    RateRule o3;
    o1 = M.createAssignmentRule();
    o2 = M.createAlgebraicRule();
    o3 = M.createRateRule();
    o3.setVariable("test");
    assertTrue(M.removeRule(0).equals(o1));
    assertTrue(M.getRuleCount() == 2);
    assertTrue(M.removeRule(0).equals(o2));
    assertTrue(M.getRuleCount() == 1);
    assertTrue(M.removeRule("test").equals(o3));
    assertTrue(M.getRuleCount() == 0);
    o1 = null;
    o2 = null;
    o3 = null;
  }

  /**
   * 
   */
  @Test
  public void test_Model_removeSpecies() {
    Species o1, o2, o3;
    o1 = M.createSpecies();
    o2 = M.createSpecies();
    o3 = M.createSpecies();
    o3.setId("test");
    assertTrue(M.removeSpecies(0).equals(o1));
    assertTrue(M.getSpeciesCount() == 2);
    assertTrue(M.removeSpecies(0).equals(o2));
    assertTrue(M.getSpeciesCount() == 1);
    assertTrue(M.removeSpecies("test").equals(o3));
    assertTrue(M.getSpeciesCount() == 0);
    o1 = null;
    o2 = null;
    o3 = null;
  }

  /**
   * 
   */
  @Test
  public void test_Model_removeSpeciesType() {
    SpeciesType o1, o2, o3;
    o1 = M.createSpeciesType();
    o2 = M.createSpeciesType();
    o3 = M.createSpeciesType();
    o3.setId("test");
    assertTrue(M.removeSpeciesType(0).equals(o1));
    assertTrue(M.getSpeciesTypeCount() == 2);
    assertTrue(M.removeSpeciesType(0).equals(o2));
    assertTrue(M.getSpeciesTypeCount() == 1);
    assertTrue(M.removeSpeciesType("test").equals(o3));
    assertTrue(M.getSpeciesTypeCount() == 0);
    o1 = null;
    o2 = null;
    o3 = null;
  }

  /**
   * 
   */
  @Test
  public void test_Model_removeUnitDefinition() {
    UnitDefinition o1, o2, o3;
    o1 = M.createUnitDefinition();
    o2 = M.createUnitDefinition();
    o3 = M.createUnitDefinition();
    o3.setId("test");
    assertTrue(M.removeUnitDefinition(0).equals(o1));
    assertTrue(M.getUnitDefinitionCount() == 2);
    assertTrue(M.removeUnitDefinition(0).equals(o2));
    assertTrue(M.getUnitDefinitionCount() == 1);
    assertTrue(M.removeUnitDefinition("test").equals(o3));
    assertTrue(M.getUnitDefinitionCount() == 0);
    o1 = null;
    o2 = null;
    o3 = null;
  }

  /**
   * 
   */
  @Test
  public void test_Model_setId() {
    String id = "Branch";
    ;
    M.setId(id);
    assertTrue(M.getId().equals(id));
    assertEquals(true, M.isSetId());
    if (M.getId() == id) {
      ;
    }
    {
    }
    M.setId(M.getId());
    assertTrue(M.getId().equals(id));
    M.setId("");
    assertEquals(false, M.isSetId());
    if (M.getId() != null) {
      ;
    }
    {
    }
    M.setId(id);
    M.unsetId();
    assertEquals(false, M.isSetId());
  }

  /**
   * 
   */
  @Test
  public void test_Model_setName() {
    String name = "My_Branch_Model";
    ;
    M.setName(name);
    assertTrue(M.getName().equals(name));
    assertEquals(true, M.isSetName());
    if (M.getName() == name) {
      ;
    }
    {
    }
    M.setName(M.getName());
    assertTrue(M.getName().equals(name));
    M.setName("");
    assertEquals(false, M.isSetName());
    if (M.getName() != null) {
      ;
    }
    {
    }
  }

  /**
   * 
   */
  @Test
  public void test_Model_setgetModelHistory() {
    History history = new History();
    Creator mc = new Creator();
    Date date = Calendar.getInstance().getTime();
    mc.setFamilyName("Keating");
    mc.setGivenName("Sarah");
    mc.setEmail("sbml-team@caltech.edu");
    mc.setOrganisation("UH");
    history.addCreator(mc);
    history.setCreatedDate(date);
    history.setModifiedDate(date);
    assertTrue(M.isSetHistory() == false);
    M.setHistory(history);
    assertTrue(M.isSetHistory() == true);
    Creator newMC = history.getCreator(0);
    assertTrue(newMC != null);
    assertTrue(newMC.getFamilyName().equals("Keating"));
    assertTrue(newMC.getGivenName().equals("Sarah"));
    assertTrue(newMC.getEmail().equals("sbml-team@caltech.edu"));
    assertTrue(newMC.getOrganisation().equals("UH"));
    M.unsetHistory();
    assertTrue(M.isSetHistory() == false);
    history = null;
    mc = null;
  }
}
