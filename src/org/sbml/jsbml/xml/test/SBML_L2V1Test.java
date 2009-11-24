package org.sbml.jsbml.xml.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.sbml.jsbml.element.Model;
import org.sbml.jsbml.element.Reaction;
import org.sbml.jsbml.element.SBMLDocument;
import org.sbml.jsbml.element.Species;
import org.sbml.jsbml.xml.SBMLReader;

public class SBML_L2V1Test {

	public static String DATA_FOLDER = "/home/compneur/workspace/jsbmlStax/src/org/sbml/jsbml/xml/test/data/";
	
	@Test public void read_noanno() {
		String fileName = DATA_FOLDER + "l2v1/BIOMD0000000227-noanno.xml";
		
		SBMLReader.readSBMLFile(fileName);
	}
	
	@Test public void read1() {
		// URL fileUrl = this.getClass().getResource("./data/BIOMD0000000025.xml");
		String fileName = DATA_FOLDER + "l2v1/BIOMD0000000025.xml";
		
		SBMLDocument doc = SBMLReader.readSBMLFile(fileName);
		Model model = doc.getModel();
		
		assertTrue(doc.getLevel() == 2 && doc.getVersion() == 1);
		
		//assertTrue(model.getLevel() == 2 && model.getVersion() == 1);
		
		assertTrue(model.getId().equals("Smolen2002"));
		assertTrue(model.getName().equals("Smolen2002_CircClock"));
		
		assertTrue(model.getUnitDefinition("substance").getName().equals("nanomole (new default)"));
		
		Species dClk = model.getSpecies("dClk");
		
		assertTrue(dClk != null);
		
		//assertTrue(dClk.getName() == null);
		//assertTrue(dClk.getNumCVTerms() == 2);
		
		assertTrue(dClk.getInitialAmount() == Double.parseDouble("1e-16"));
		
		Species dClkF = model.getSpecies("dClkF");
		
		assertTrue(dClkF != null);
		
		assertTrue(dClkF.getName().equals("free dClk"));
		
		Reaction rdClk = model.getReaction("rdClk");
		
		assertTrue(rdClk != null);
		
		assertTrue(rdClk.getName().equals("dClk production"));
		assertTrue(rdClk.getListOfReactants().size() == 1);
		assertTrue(rdClk.getListOfProducts().size() == 1);
		assertTrue(rdClk.getListOfModifiers().size() == 1);
		
		
	}
	
	@Test public void read2() {
		String fileName = DATA_FOLDER + "l2v1/BIOMD0000000227.xml";
		
		SBMLReader.readSBMLFile(fileName);
	}
	
	@Test public void read3() {
		String fileName = DATA_FOLDER + "l2v4/BIOMD0000000228.xml"; // l2v4
		
		SBMLReader.readSBMLFile(fileName);
	}
	
	@Test public void read4() {
		String fileName = DATA_FOLDER + "l2v4/BIOMD0000000229.xml"; // l2v4
		
		SBMLReader.readSBMLFile(fileName);
	}
	
	
}
