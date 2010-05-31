/*
 * $Id$
 * $URL$
 *
 *
 *==================================================================================
 * Copyright (c) 2009 the copyright is held jointly by the individual
 * authors. See the file AUTHORS for the list of authors.
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

package org.sbml.jsbml.xml.test;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.InvalidPropertiesFormatException;

import javax.xml.stream.XMLStreamException;

import org.junit.Before;
import org.junit.Test;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.Unit.Kind;
import org.sbml.jsbml.xml.stax.SBMLReader;

public class SBML_L1VxTests {
	
	
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
	 */
	@Before public void setUp() { 
	}
	
	/**
	 * 
	 * @throws XMLStreamException
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 * @throws InvalidPropertiesFormatException 
	 */
	@Test public void readL1V2Branch() throws XMLStreamException, InvalidPropertiesFormatException, IOException, ClassNotFoundException {
		// URL fileUrl = this.getClass().getResource("./data/BIOMD0000000025.xml");
		String fileName = DATA_FOLDER + "/libsbml-test-data/l1v2-branch.xml";
		
		SBMLDocument doc = SBMLReader.readSBMLFile(fileName);
		Model model = doc.getModel();
		
		assertTrue(doc.getLevel() == 1 && doc.getVersion() == 2);
		
		assertTrue(model.getLevel() == 1 && model.getVersion() == 2);
		
		// assertTrue(model.getId().equals("")); // TODO : document. Different behavior than libsbml, we set the id as the name for SBML level 1 models.
		assertTrue(model.getId().equals("Branch"));
		assertTrue(model.getName().equals("Branch"));
		
		Species s1 = model.getSpecies("S1");
		
		assertTrue(s1 != null);
		
		assertTrue(s1.getName().equals("S1"));
		assertTrue(s1.getId().equals("S1")); // changed, was assertTrue(s1.getId().equals("")); cf comment above.
		assertTrue(s1.getNumCVTerms() == 0);
		
		assertTrue(s1.getInitialAmount() == 0);
		assertTrue(s1.getBoundaryCondition() == false);
		
		Species x1 = model.getSpecies("X1");
		
		assertTrue(x1 != null);
		
		assertTrue(x1.getName().equals("X1"));
		assertTrue(x1.getInitialAmount() == 0);
		assertTrue(x1.getBoundaryCondition() == true);
		
		
		Reaction r1 = model.getReaction(0);
		
		assertTrue(r1 != null);
		
		assertTrue(r1.getName().equals("reaction_1"));
		assertTrue(r1.getListOfReactants().size() == 1);
		assertTrue(r1.getListOfProducts().size() == 1);
		assertTrue(r1.getNumModifiers() == 0);
		assertTrue(r1.getReversible() == false);
		
		assertTrue(r1.getListOfReactants().get(0).getSpecies().equals("X0"));
		assertTrue(r1.getListOfProducts().get(0).getSpecies().equals("S1"));
		
		KineticLaw rdClkKL = r1.getKineticLaw();

		assertTrue(rdClkKL != null);
		assertTrue(rdClkKL.getListOfParameters().size() == 1);
		assertTrue(rdClkKL.getListOfParameters().get(0).getName().equals("k1"));
		
		System.out.println("L1V2 formula = " + rdClkKL.getFormula());
	}

	/**
	 * 
	 * @throws XMLStreamException
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 * @throws InvalidPropertiesFormatException 
	 */
	@Test public void readL1V1Units() throws XMLStreamException, InvalidPropertiesFormatException, IOException, ClassNotFoundException {
		// URL fileUrl = this.getClass().getResource("./data/BIOMD0000000025.xml");
		String fileName = DATA_FOLDER + "/libsbml-test-data/l1v1-units.xml";
		
		SBMLDocument doc = SBMLReader.readSBMLFile(fileName);
		Model model = doc.getModel();
		
		assertTrue(doc.getLevel() == 1 && doc.getVersion() == 1);
		
		assertTrue(model.getLevel() == 1 && model.getVersion() == 1);
		
		assertTrue(model.getId().equals(""));
		assertTrue(model.getName().equals(""));
		
		Species s1 = model.getSpecies("s1");
		
		assertTrue(s1 != null);
		
		assertTrue(s1.getName().equals("s1"));
		assertTrue(s1.getId().equals("s1")); // changed, was :  assertTrue(s1.getId().equals(""));
		assertTrue(s1.getNumCVTerms() == 0);
		
		assertTrue(s1.getInitialAmount() == 1);
		
		Parameter vm = model.getParameter(0);
		
		assertTrue(vm != null);
		assertTrue(vm.getUnits().equals("mls"));
		
		UnitDefinition mls = model.getUnitDefinition(1);
		
		assertTrue(mls != null);
		assertTrue(mls.getNumUnits() == 3);
		assertTrue(mls.getName().equals("mls"));
		assertTrue(mls.getUnit(0).getScale() == -3);
		assertTrue(mls.getUnit(0).getKind().getName().equals("mole"));
		
		assertTrue(mls.getUnit(2).getExponent() == -1);
		assertTrue(mls.getUnit(2).getKind().getName().equals("second"));
		assertTrue(mls.getUnit(2).getKind().equals(Kind.SECOND));
		
	}

}
