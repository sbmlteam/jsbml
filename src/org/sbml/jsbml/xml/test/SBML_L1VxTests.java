package org.sbml.jsbml.xml.test;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.sbml.jsbml.element.KineticLaw;
import org.sbml.jsbml.element.Model;
import org.sbml.jsbml.element.Parameter;
import org.sbml.jsbml.element.Reaction;
import org.sbml.jsbml.element.SBMLDocument;
import org.sbml.jsbml.element.Species;
import org.sbml.jsbml.element.UnitDefinition;
import org.sbml.jsbml.xml.SBMLReader;

public class SBML_L1VxTests {
	
	
	public static String DATA_FOLDER = System.getenv("DATA_FOLDER");
	

	@Before public void setUp() { 
	}
	
	@Test public void readL1V2Branch() {
		// URL fileUrl = this.getClass().getResource("./data/BIOMD0000000025.xml");
		String fileName = DATA_FOLDER + "libsbml-test-data/l1v2-branch.xml";
		
		SBMLDocument doc = SBMLReader.readSBMLFile(fileName);
		Model model = doc.getModel();
		
		assertTrue(doc.getLevel() == 1 && doc.getVersion() == 2);
		
		// TODO : assertTrue(model.getLevel() == 1 && model.getVersion() == 2);
		
		assertTrue(model.getId().equals(""));
		assertTrue(model.getName().equals("Branch"));
		
		Species s1 = model.getSpecies("S1");
		
		assertTrue(s1 != null);
		
		assertTrue(s1.getName().equals("S1"));
		assertTrue(s1.getId().equals(""));
		assertTrue(s1.getNumCVTerms() == 0);
		
		assertTrue(s1.getInitialAmount() == 0);
		assertTrue(s1.getBoundaryCondition() == false);
		
		Species x1 = model.getSpecies("X1");
		
		assertTrue(x1 != null);
		
		assertTrue(x1.getName().equals("X1"));
		assertTrue(s1.getInitialAmount() == 0);
		assertTrue(s1.getBoundaryCondition() == true);
		
		
		Reaction r1 = model.getReaction(0);
		
		assertTrue(r1 != null);
		
		assertTrue(r1.getName().equals("reaction_1"));
		assertTrue(r1.getListOfReactants().size() == 1);
		assertTrue(r1.getListOfProducts().size() == 1);
		assertTrue(r1.getListOfModifiers().size() == 0);
		assertTrue(r1.getReversible() == false);
		
		assertTrue(r1.getListOfReactants().get(0).getSpecies().equals("X0"));
		assertTrue(r1.getListOfProducts().get(0).getSpecies().equals("S1"));
		
		KineticLaw rdClkKL = r1.getKineticLaw();

		assertTrue(rdClkKL != null);
		assertTrue(rdClkKL.getListOfParameters().size() == 1);
		assertTrue(rdClkKL.getListOfParameters().get(0).getName().equals("k1"));
		
		System.out.println("L1V2 formula = " + rdClkKL.getMathBufferToString());
	}

	
	@Test public void readL1V1Units() {
		// URL fileUrl = this.getClass().getResource("./data/BIOMD0000000025.xml");
		String fileName = DATA_FOLDER + "libsbml-test-data/l1v1-units.xml";
		
		SBMLDocument doc = SBMLReader.readSBMLFile(fileName);
		Model model = doc.getModel();
		
		assertTrue(doc.getLevel() == 1 && doc.getVersion() == 1);
		
		// TODO : assertTrue(model.getLevel() == 1 && model.getVersion() == 1);
		
		assertTrue(model.getId().equals(""));
		assertTrue(model.getName().equals(""));
		
		Species s1 = model.getSpecies("s1");
		
		assertTrue(s1 != null);
		
		assertTrue(s1.getName().equals("s1"));
		assertTrue(s1.getId().equals(""));
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
		assertTrue(mls.getUnit(0).getKind().equals("mole"));
		
		assertTrue(mls.getUnit(2).getScale() == -1);
		assertTrue(mls.getUnit(2).getKind().equals("second"));
		
	}

}
