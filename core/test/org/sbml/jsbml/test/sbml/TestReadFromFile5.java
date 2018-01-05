/*
 * @file    TestReadFromFile5.java
 * @brief   Reads test-data/l2v1-assignment.xml into memory and tests it.
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

import java.io.InputStream;

import javax.xml.stream.XMLStreamException;

import org.junit.Test;
import org.sbml.jsbml.AssignmentRule;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.LocalParameter;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.Rule;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;

/**
 * 
 * @author  Akiya Jouraku
 * @author  Ben Bornstein
 * @since 0.8
 */
public class TestReadFromFile5 {

  /**
   * 
   * @param condition
   * @throws AssertionError
   */
  static void assertTrue(boolean condition) throws AssertionError
  {
    if (condition == true)
    {
      return;
    }
    throw new AssertionError();
  }

  /**
   * 
   * @param a
   * @param b
   * @throws AssertionError
   */
  static void assertEquals(Object a, Object b) throws AssertionError
  {
    if ((a == null) && (b == null))
    {
      return;
    }
    else if ((a == null) || (b == null))
    {
      throw new AssertionError();
    }
    else if (a.equals(b))
    {
      return;
    }

    throw new AssertionError();
  }

  /**
   * 
   * @param a
   * @param b
   * @throws AssertionError
   */
  static void assertNotEquals(Object a, Object b) throws AssertionError
  {
    if ((a == null) && (b == null))
    {
      throw new AssertionError();
    }
    else if ((a == null) || (b == null))
    {
      return;
    }
    else if (a.equals(b))
    {
      throw new AssertionError();
    }
  }

  /**
   * 
   * @param a
   * @param b
   * @throws AssertionError
   */
  static void assertEquals(boolean a, boolean b) throws AssertionError
  {
    if (a == b)
    {
      return;
    }
    throw new AssertionError();
  }

  /**
   * 
   * @param a
   * @param b
   * @throws AssertionError
   */
  static void assertNotEquals(boolean a, boolean b) throws AssertionError
  {
    if (a != b)
    {
      return;
    }
    throw new AssertionError();
  }

  /**
   * 
   * @param a
   * @param b
   * @throws AssertionError
   */
  static void assertEquals(int a, int b) throws AssertionError
  {
    if (a == b)
    {
      return;
    }
    throw new AssertionError();
  }

  /**
   * 
   * @param a
   * @param b
   * @throws AssertionError
   */
  static void assertNotEquals(int a, int b) throws AssertionError
  {
    if (a != b)
    {
      return;
    }
    throw new AssertionError();
  }

  /**
   * 
   */
  @SuppressWarnings("deprecation")
  @Test public void test_read_l2v1_assignment()
  {
    SBMLReader reader = new SBMLReader();
    SBMLDocument d = null;
    Model m;
    Compartment c;
    Species s;
    Parameter gp;
    LocalParameter lp;
    AssignmentRule ar;
    Reaction r;
    SpeciesReference sr;
    KineticLaw kl;
    Reaction r1;
    ListOf<Compartment> loc;
    Compartment c1;
    ListOf<Rule> lor;
    AssignmentRule ar1;
    ListOf<Parameter> lop;
    Parameter p1;
    ListOf<Species> los;
    Species s1;
    InputStream fileStream = TestReadFromFile5.class.getResourceAsStream("/org/sbml/jsbml/xml/test/data/libsbml-test-data/l2v1-assignment.xml");

    try {
      d = reader.readSBMLFromStream(fileStream);
    } catch (XMLStreamException e) {
      e.printStackTrace();
      assert(false);
    }

    assertTrue(d.getLevel() == 2);
    assertTrue(d.getVersion() == 1);
    m = d.getModel();
    assertTrue(m != null);
    assertTrue(m.getCompartmentCount() == 1);
    c = m.getCompartment(0);
    assertTrue(c != null);
    assertTrue(c.getId().equals("cell"));
    /*    ud = c.getDerivedUnitDefinition();
    assertTrue(ud.getUnitCount() == 1);
    assertTrue(ud.getUnit(0).getKind() == Kind.LITRE);
     */
    loc = m.getListOfCompartments();
    c1 = loc.get(0);
    assertTrue(c1.equals(c));
    c1 = loc.get("cell");
    assertTrue(c1.equals(c));
    assertTrue(m.getSpeciesCount() == 5);
    s = m.getSpecies(0);
    assertTrue(s != null);
    assertTrue(s.getId().equals("X0" ));
    assertTrue(s.getCompartment().equals("cell"));
    assertTrue(s.getInitialConcentration() == 1.0);
    los = m.getListOfSpecies();
    s1 = los.get(0);
    assertTrue(s1.equals(s));
    s1 = los.get("X0");
    assertTrue(s1.equals(s));
    s = m.getSpecies(1);
    assertTrue(s != null);
    assertTrue(s.getId().equals("X1" ));
    assertTrue(s.getCompartment().equals("cell"));
    assertTrue(s.getInitialConcentration() == 0.0);
    s = m.getSpecies(2);
    assertTrue(s != null);
    assertTrue(s.getId().equals("T"  ));
    assertTrue(s.getCompartment().equals("cell"));
    assertTrue(s.getInitialConcentration() == 0.0);
    s = m.getSpecies(3);
    assertTrue(s != null);
    assertTrue(s.getId().equals("S1" ));
    assertTrue(s.getCompartment().equals("cell"));
    assertTrue(s.getInitialConcentration() == 0.0);
    s = m.getSpecies(4);
    assertTrue(s != null);
    assertTrue(s.getId().equals("S2" ));
    assertTrue(s.getCompartment().equals("cell"));
    assertTrue(s.getInitialConcentration() == 0.0);
    assertTrue(m.getParameterCount() == 1);
    gp = m.getParameter(0);
    assertTrue(gp != null);
    assertTrue(gp.getId().equals("Keq"));
    assertTrue(gp.getValue() == 2.5);
    lop = m.getListOfParameters();
    p1 = lop.get(0);
    assertTrue(p1.equals(gp));
    p1 = lop.get("Keq");
    assertTrue(p1.equals(gp));
    /*
    ud = gp.getDerivedUnitDefinition();
    assertTrue(ud.getUnitCount() == 0);
    assertTrue(m.getRuleCount() == 2);
     */
    ar = (AssignmentRule)  m.getRule(0);
    assertTrue(ar != null);
    assertTrue(ar.getVariable().equals("S1"          ));
    assertTrue(ar.getFormula().equals("T/(1+Keq)"));
    /*
    ud = ar.getDerivedUnitDefinition();
    assertTrue(ud.getUnitCount() == 2);
    assertTrue(ud.getUnit(0).getKind() == Kind.MOLE);
    assertTrue(ud.getUnit(0).getExponent() == 1);
    assertTrue(ud.getUnit(1).getKind() == Kind.LITRE);
    assertTrue(ud.getUnit(1).getExponent() == -1);
     */
    assertTrue(ar.containsUndeclaredUnits() == true);
    lor = m.getListOfRules();
    ar1 = (AssignmentRule) lor.get(0);
    assertTrue(ar1.equals(ar));
    ar1 = (AssignmentRule) lor.get("S1");
    assertTrue(ar1.equals(ar));
    ar = (AssignmentRule)  m.getRule(1);
    assertTrue(ar != null);
    assertTrue(ar.getVariable().equals("S2"     ));
    assertTrue(ar.getFormula().equals("Keq*S1"));
    assertTrue(m.getReactionCount() == 2);
    r = m.getReaction(0);
    assertTrue(r != null);
    assertTrue(r.getId().equals("in"));
    assertTrue(r.getReactantCount() == 1);
    assertTrue(r.getProductCount() == 1);
    sr = r.getReactant(0);
    assertTrue(sr != null);
    assertTrue(sr.getSpecies().equals("X0"));
    sr = r.getProduct(0);
    assertTrue(sr != null);
    assertTrue(sr.getSpecies().equals("T"));
    kl = r.getKineticLaw();
    assertTrue(kl != null);
    assertTrue(kl.getFormula().equals("k1*X0"));
    assertTrue(kl.getLocalParameterCount() == 1);
    r1 = kl.getParentSBMLObject();
    assertTrue(r1 != null);
    assertTrue(r1.getId().equals("in"));
    assertTrue(r1.getReactantCount() == 1);
    assertTrue(r1.getProductCount() == 1);
    lp = kl.getLocalParameter(0);
    assertTrue(lp != null);
    assertTrue(lp.getId().equals("k1"));
    assertTrue(lp.getValue() == 0.1);
    kl = (KineticLaw) lp.getParentSBMLObject().getParentSBMLObject();
    assertTrue(kl != null);
    assertTrue(kl.getFormula().equals("k1*X0"));
    assertTrue(kl.getLocalParameterCount() == 1);
    r = m.getReaction(1);
    assertTrue(r != null);
    assertTrue(r.getId().equals("out"));
    assertTrue(r.getReactantCount() == 1);
    assertTrue(r.getProductCount() == 1);
    sr = r.getReactant(0);
    assertTrue(sr != null);
    assertTrue(sr.getSpecies().equals("T"));
    sr = r.getProduct(0);
    assertTrue(sr != null);
    assertTrue(sr.getSpecies().equals("X1"));
    kl = r.getKineticLaw();
    assertTrue(kl != null);
    assertTrue(kl.getFormula().equals("k2*T"));
    assertTrue(kl.getLocalParameterCount() == 1);
    lp = kl.getLocalParameter(0);
    assertTrue(lp != null);
    assertTrue(lp.getId().equals("k2"));
    assertTrue(lp.getValue() == 0.15);
    d = null;
  }

}
