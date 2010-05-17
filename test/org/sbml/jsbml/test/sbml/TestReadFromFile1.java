/*
 *
 * @file    TestReadFromFile1.java
 * @brief   Reads tests/l1v1-branch.xml into memory and tests it.
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

import static org.junit.Assert.assertTrue;

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
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.xml.stax.SBMLReader;

public class TestReadFromFile1 {

	public static String DATA_FOLDER = null;

	static {

		if (DATA_FOLDER == null) {
			DATA_FOLDER = System.getenv("DATA_FOLDER");
		}
		if (DATA_FOLDER == null) {
			DATA_FOLDER = System.getProperty("DATA_FOLDER");
		}

	}

	/**
	 * 
	 * @throws XMLStreamException
	 */
	@Test
	public void test_read_l1v1_branch() throws XMLStreamException {
		SBMLDocument d;
		Model m;
		Compartment c;
		KineticLaw kl;
		LocalParameter p;
		Reaction r;
		Species s;
		SpeciesReference sr;
		UnitDefinition ud;
		String filename = DATA_FOLDER + "/libsbml-test-data/l1v1-branch.xml";
		d = SBMLReader.readSBML(filename);
		if (d == null)
			;
		{
		}
		assertTrue(d.getLevel() == 1);
		assertTrue(d.getVersion() == 1);
		m = d.getModel();
		assertTrue(m.getName().equals("Branch"));
		assertTrue(m.getNumCompartments() == 1);
		c = m.getCompartment(0);
		assertTrue(c.getName().equals("compartmentOne"));
		assertTrue(c.getVolume() == 1);
		ud = c.getUnitsInstance(); // TODO : check what the method is performing
									// in libsbml =>
									// c.getDerivedUnitDefinition(); API changes
									// ??
		assertTrue(ud.getNumUnits() == 1);
		assertTrue(ud.getUnit(0).getKind() == Unit.Kind.LITRE); // TODO : API
																// changes to
																// document
		assertTrue(m.getNumSpecies() == 4);
		s = m.getSpecies(0);
		assertTrue(s.getName().equals("S1"));
		assertTrue(s.getCompartment().equals("compartmentOne"));
		assertTrue(s.getInitialAmount() == 0);
		assertTrue(s.getBoundaryCondition() == false);
		ud = s.getDerivedUnitDefinition();
		assertTrue(ud.getNumUnits() == 2);
		assertTrue(ud.getUnit(0).getKind() == Unit.Kind.MOLE);
		assertTrue(ud.getUnit(0).getExponent() == 1);
		assertTrue(ud.getUnit(1).getKind() == Unit.Kind.LITRE);
		assertTrue(ud.getUnit(1).getExponent() == -1);
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
		assertTrue(m.getNumReactions() == 3);
		r = m.getReaction(0);
		assertTrue(r.getName().equals("reaction_1"));
		assertTrue(r.getReversible() == false);
		assertTrue(r.getFast() == false);
		ud = r.getKineticLaw().getDerivedUnitDefinition();
		assertTrue(ud.getNumUnits() == 2);
		assertTrue(ud.getUnit(0).getKind() == Unit.Kind.MOLE);
		assertTrue(ud.getUnit(0).getExponent() == 1);
		assertTrue(ud.getUnit(1).getKind() == Unit.Kind.LITRE);
		assertTrue(ud.getUnit(1).getExponent() == -1);
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
		assertTrue(r.getNumReactants() == 1);
		assertTrue(r.getNumProducts() == 1);
		sr = r.getReactant(0);
		assertTrue(sr.getSpecies().equals("X0"));
		assertTrue(sr.getStoichiometry() == 1);
		assertTrue(sr.getDenominator() == 1);
		sr = r.getProduct(0);
		assertTrue(sr.getSpecies().equals("S1"));
		assertTrue(sr.getStoichiometry() == 1);
		assertTrue(sr.getDenominator() == 1);
		kl = r.getKineticLaw();
		assertTrue(kl.getFormula().equals("k1 * X0"));
		assertTrue(kl.getNumParameters() == 1);
		p = kl.getParameter(0);
		assertTrue(p.getName().equals("k1"));
		assertTrue(p.getValue() == 0);
		r = m.getReaction(1);
		assertTrue(r.getNumReactants() == 1);
		assertTrue(r.getNumProducts() == 1);
		sr = r.getReactant(0);
		assertTrue(sr.getSpecies().equals("S1"));
		assertTrue(sr.getStoichiometry() == 1);
		assertTrue(sr.getDenominator() == 1);
		sr = r.getProduct(0);
		assertTrue(sr.getSpecies().equals("X1"));
		assertTrue(sr.getStoichiometry() == 1);
		assertTrue(sr.getDenominator() == 1);
		kl = r.getKineticLaw();
		assertTrue(kl.getFormula().equals("k2 * S1"));
		assertTrue(kl.getNumParameters() == 1);
		p = kl.getParameter(0);
		assertTrue(p.getName().equals("k2"));
		assertTrue(p.getValue() == 0);
		r = m.getReaction(2);
		assertTrue(r.getNumReactants() == 1);
		assertTrue(r.getNumProducts() == 1);
		sr = r.getReactant(0);
		assertTrue(sr.getSpecies().equals("S1"));
		assertTrue(sr.getStoichiometry() == 1);
		assertTrue(sr.getDenominator() == 1);
		sr = r.getProduct(0);
		assertTrue(sr.getSpecies().equals("X2"));
		assertTrue(sr.getStoichiometry() == 1);
		assertTrue(sr.getDenominator() == 1);
		kl = r.getKineticLaw();
		assertTrue(kl.getFormula().equals("k3 * S1"));
		assertTrue(kl.getNumParameters() == 1);
		p = kl.getParameter(0);
		assertTrue(p.getName().equals("k3"));
		assertTrue(p.getValue() == 0);
		d = null;
	}
}
