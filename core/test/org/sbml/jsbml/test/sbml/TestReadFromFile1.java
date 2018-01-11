/*
 *
 * @file    TestReadFromFile1.java
 * @brief   Reads tests/l1v1-branch.xml into memory and tests it.
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

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.InvalidPropertiesFormatException;

import javax.xml.stream.XMLStreamException;

import org.junit.Test;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.LocalParameter;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.xml.stax.SBMLReader;

/**
 * 
 * @author  Nicolas Rodriguez
 * @author  Akiya Jouraku
 * @author  Ben Bornstein
 * @since 0.8
 */
public class TestReadFromFile1 {

  /**
   * 
   * @throws XMLStreamException
   * @throws ClassNotFoundException
   * @throws IOException
   * @throws InvalidPropertiesFormatException
   */
  @SuppressWarnings("deprecation")
  @Test
  public void test_read_l1v1_branch() throws XMLStreamException, InvalidPropertiesFormatException, IOException, ClassNotFoundException {
    SBMLDocument d;
    Model m;
    Compartment c;
    KineticLaw kl;
    LocalParameter p;
    Reaction r;
    Species s;
    SpeciesReference sr;
    InputStream fileStream = TestReadFromFile1.class.getResourceAsStream("/org/sbml/jsbml/xml/test/data/libsbml-test-data/l1v1-branch.xml");

    d = new SBMLReader().readSBMLFromStream(fileStream);
    if (d == null) {
      ;
    }
    {
    }
    assertTrue(d.getLevel() == 1);
    assertTrue(d.getVersion() == 1);
    m = d.getModel();
    assertTrue(m.getName().equals("Branch"));
    assertTrue(m.getCompartmentCount() == 1);
    c = m.getCompartment(0);
    assertTrue(c.getName().equals("compartmentOne"));
    assertTrue(c.getVolume() == 1);
    // assertTrue(ud.getUnitCount() == 1); // getDerivedUnitDefinition not working properly
    // assertTrue(ud.getUnit(0).getKind() == Unit.Kind.LITRE);
    assertTrue(m.getSpeciesCount() == 4);
    s = m.getSpecies(0);
    assertTrue(s.getName().equals("S1"));
    assertTrue(s.getCompartment().equals("compartmentOne"));
    assertTrue(s.getInitialAmount() == 0);
    assertTrue(s.getBoundaryCondition() == false);

    //		ud = s.getDerivedUnitDefinition(); // getDerivedUnitDefinition not working properly
    //		assertTrue(ud.getUnitCount() == 2);
    //		assertTrue(ud.getUnit(0).getKind() == Unit.Kind.MOLE);
    //		assertTrue(ud.getUnit(0).getExponent() == 1);
    //		assertTrue(ud.getUnit(1).getKind() == Unit.Kind.LITRE);
    //		assertTrue(ud.getUnit(1).getExponent() == -1);

    s = m.getSpecies(1);
    assertTrue(s.getName().equals("X0"));
    assertTrue(s.getCompartment().equals("compartmentOne"));
    assertTrue(s.getInitialAmount() == 0);
    assertTrue(s.getBoundaryCondition() == true);
    s = m.getSpecies(2);
    assertTrue(s.getName().equals("X1"));
    assertTrue(s.getCompartment().equals("compartmentOne"));
    assertTrue(s.getInitialAmount() == 0);
    assertTrue(s.getBoundaryCondition() == true);
    s = m.getSpecies(3);
    assertTrue(s.getName().equals("X2"));
    assertTrue(s.getCompartment().equals("compartmentOne"));
    assertTrue(s.getInitialAmount() == 0);
    assertTrue(s.getBoundaryCondition() == true);
    assertTrue(m.getReactionCount() == 3);
    r = m.getReaction(0);
    assertTrue(r.getName().equals("reaction_1"));
    assertTrue(r.getReversible() == false);
    assertTrue(r.getFast() == false);


    //		assertTrue(ud.getUnitCount() == 2);
    //		assertTrue(ud.getUnit(0).getKind() == Unit.Kind.MOLE);
    //		assertTrue(ud.getUnit(0).getExponent() == 1);
    //		assertTrue(ud.getUnit(1).getKind() == Unit.Kind.LITRE);
    //		assertTrue(ud.getUnit(1).getExponent() == -1);

    assertTrue(r.getKineticLaw().containsUndeclaredUnits() == true);
    r = m.getReaction(1);
    assertTrue(r.getName().equals("reaction_2"));
    assertTrue(r.getReversible() == false);
    assertTrue(r.getFast() == false);
    r = m.getReaction(2);
    assertTrue(r.getName().equals("reaction_3"));
    assertTrue(r.getReversible() == false);
    assertTrue(r.getFast() == false);
    r = m.getReaction(0);
    assertTrue(r.getReactantCount() == 1);
    assertTrue(r.getProductCount() == 1);
    sr = r.getReactant(0);
    assertTrue(sr.getSpecies().equals("X0"));
    assertTrue(sr.getStoichiometry() == 1);
    assertTrue(sr.getDenominator() == 1);
    sr = r.getProduct(0);
    assertTrue(sr.getSpecies().equals("S1"));
    assertTrue(sr.getStoichiometry() == 1);
    assertTrue(sr.getDenominator() == 1);
    kl = r.getKineticLaw();
    assertTrue(kl.getFormula().equals("k1*X0")); // We are not putting the same space in the formula
    assertTrue(kl.getLocalParameterCount() == 1);
    p = kl.getParameter(0);
    assertTrue(p.getName().equals("k1"));
    assertTrue(p.getValue() == 0);
    r = m.getReaction(1);
    assertTrue(r.getReactantCount() == 1);
    assertTrue(r.getProductCount() == 1);
    sr = r.getReactant(0);
    assertTrue(sr.getSpecies().equals("S1"));
    assertTrue(sr.getStoichiometry() == 1);
    assertTrue(sr.getDenominator() == 1);
    sr = r.getProduct(0);
    assertTrue(sr.getSpecies().equals("X1"));
    assertTrue(sr.getStoichiometry() == 1);
    assertTrue(sr.getDenominator() == 1);
    kl = r.getKineticLaw();
    assertTrue(kl.getFormula().equals("k2*S1")); // equals("k2 * S1")
    assertTrue(kl.getLocalParameterCount() == 1);
    p = kl.getParameter(0);
    assertTrue(p.getName().equals("k2"));
    assertTrue(p.getValue() == 0);
    r = m.getReaction(2);
    assertTrue(r.getReactantCount() == 1);
    assertTrue(r.getProductCount() == 1);
    sr = r.getReactant(0);
    assertTrue(sr.getSpecies().equals("S1"));
    assertTrue(sr.getStoichiometry() == 1);
    assertTrue(sr.getDenominator() == 1);
    sr = r.getProduct(0);
    assertTrue(sr.getSpecies().equals("X2"));
    assertTrue(sr.getStoichiometry() == 1);
    assertTrue(sr.getDenominator() == 1);
    kl = r.getKineticLaw();
    assertTrue(kl.getFormula().equals("k3*S1")); // equals("k3 * S1")
    assertTrue(kl.getLocalParameterCount() == 1);
    p = kl.getParameter(0);
    assertTrue(p.getName().equals("k3"));
    assertTrue(p.getValue() == 0);
    d = null;
  }
}
