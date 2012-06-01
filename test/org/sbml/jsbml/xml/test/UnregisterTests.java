package org.sbml.jsbml.xml.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.IdentifierException;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.LocalParameter;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;

public class UnregisterTests {

	SBMLDocument doc;
	Model model;
	
	@BeforeClass public static void initialSetUp() {
		
	
	}
	
	/**
	 * 
	 */
	@Before public void setUp() { 
		doc = new SBMLDocument(2, 4);
		model = doc.createModel("model");
		
		Compartment comp = model.createCompartment("cell");
		comp.setMetaId("cell");
		
		Species s1 = model.createSpecies("S1", comp);
		s1.setMetaId("S1");
		
		Species s2 = model.createSpecies("S2", comp);
		s2.setMetaId("S2");
		
		Reaction r1 = model.createReaction("R1");
		r1.setMetaId("R1");
		
		SpeciesReference reactant = model.createReactant("SP1");
		reactant.setMetaId("SP1");
		reactant.setSpecies(s1);
		
		SpeciesReference product = model.createProduct("SP2");
		product.setMetaId("SP2");
		product.setSpecies(s2);
		
		LocalParameter lp1 = r1.createKineticLaw().createLocalParameter("LP1");
		lp1.setMetaId("LP1");
		
	}
	
	/**
	 * 
	 */
	@Test public void testRegister() {
		
		try {
			model.createSpecies("R1");
			fail("We should not be able to register twice the same id.");
		} catch (IllegalArgumentException e) {
			assertTrue(true);
			// success
		}

		Species s3 = new Species();
		s3.setId("SP1");
		
		try {
			model.addSpecies(s3);
			fail("We should not be able to register twice the same id.");
		} catch (IllegalArgumentException e) {
			assertTrue(true);
			// success
		}
		assertTrue(model.getSpecies("S3") == null);

		s3.setId("LP1");
		
		try {
			model.addSpecies(s3);
			assertTrue(true);
		} catch (IllegalArgumentException e) {
			fail("We should be able to register an id that is the same as a local parameter id.");
		}

		model.removeSpecies(s3);
		assertTrue(model.getSpecies("S3") == null);
		
		// Testing again after having removed the Species
		try {
			model.addSpecies(s3);
			assertTrue(true);
		} catch (IllegalArgumentException e) {
			fail("We should be able to register an id that is the same as a local parameter id.");
		}

		model.removeSpecies(s3);
		assertTrue(model.getSpecies("S3") == null);
		
		// Setting the same metaid as a SpeciesReference
		try {
			s3.setMetaId("SP1");
			assertTrue(true);
		} catch (IdentifierException e) {
			fail("We should be able to put any metaId to a Species removed from a model.");
		}

		try {
			model.addSpecies(s3);
			fail("We should not be able to register twice the same metaid.");
		} catch (IllegalArgumentException e) {
			assertTrue(true);
			// success
		}

		assertTrue(model.getSpecies("S3") == null);

		try {
			s3.setId("S1");
			assertTrue(true);
		} catch (IdentifierException e) {
			fail("We should be able to put any id to a Species removed from a model.");
		}

		try {
			model.addSpecies(s3);
			fail("We should not be able to register twice the same id.");
		} catch (IllegalArgumentException e) {
			assertTrue(true);
			// success
		}
		
		assertTrue(model.getSpecies("S3") == null);

		// Setting the same metaid as a LocalParameter

		try {
			s3.setMetaId("LP1");
			assertTrue(true);
		} catch (IdentifierException e) {
			fail("We should be able to put any metaId to a Species not linked to a model.");
		}

		try {
			model.addSpecies(s3);
			fail("We should not be able to register twice the same metaid.");
		} catch (IllegalArgumentException e) {
			assertTrue(true);
			// success
		}

	}
	
	/**
	 * 
	 */
	@Test public void testRegister2() {
		
		Species s3 = new Species();
		// Setting the same id as a LocalParameter
		s3.setId("LP1");

		// Setting the same metaid as a LocalParameter
		try {
			s3.setMetaId("LP1");
			assertTrue(true);
		} catch (IdentifierException e) {
			fail("We should be able to put any metaId to a Species not linked to a model.");
		}

		try {
			model.addSpecies(s3);
			fail("We should not be able to register twice the same metaid.");
		} catch (IllegalArgumentException e) {
			assertTrue(true);
			// success
		}
	}

	/**
	 * 
	 */
	@Test public void testRegister3() {
		
		Reaction r2 = new Reaction(2,4);
		KineticLaw k = r2.createKineticLaw();
		
		k.createLocalParameter("LP1");
		
		// We should not be allowed to register an other local parameter with the same id
		try {
			k.createLocalParameter("LP1");
			// fail("We should not be able to add a local parameter with the same id as an other local parameter in the same kineticLaw.");
		} catch (IdentifierException e) {
			// success
		}

		assertTrue(k.getLocalParameterCount() == 1);
		
	}

	/**
	 * 
	 */
	@Test public void testRegister4() {
		
		assertTrue(model.getId().equals("model"));
		assertTrue(model.getNumSpecies() == 2);
		
		// We should be allowed to change an id !
		model.setId("newModelID");

		try {
			// fail("We should not be able to add a local parameter with the same id as an other local parameter in the same kineticLaw.");
		} catch (IdentifierException e) {
			// success
		}

		assertTrue(model.getId().equals("newModelID"));
		
	}
	
	
	/**
	 * 
	 */
	@Test public void testUnRegister() {
		
		
		Reaction r1 = model.removeReaction("R1");
		
		assertTrue(model.getReaction("R1") == null);		
		assertTrue(model.getReaction(0) == null);
		assertTrue(model.findNamedSBase("SP1") == null);
		assertTrue(model.findNamedSBase("SP2") == null);
		// assertTrue(model.findNamedSBase("LP1") == null);
		assertTrue(doc.findSBase("SP1") == null);
		assertTrue(doc.findSBase("R1") == null);
		assertTrue(doc.findSBase("LP1") == null);
		
		Species s3 = new Species();
		
		// Setting the same id as a removed SpeciesReference
		s3.setId("SP1");

		// Setting the same metaid as a removed LocalParameter
		try {
			s3.setMetaId("LP1");
			assertTrue(true);
		} catch (IdentifierException e) {
			fail("We should be able to put any metaId to a Species not linked to a model.");
		}

		try {
			model.addSpecies(s3);
			assertTrue(true);
		} catch (IllegalArgumentException e) {
			fail("We should be able to register an id or metaid from a removed element.");
			// success
		}
		
		try {
			SpeciesReference reactant = r1.createReactant("S1");
			reactant.setMetaId("S1");
		} catch (IllegalArgumentException e) {
			fail("We should be able to put any id to an element removed from a model.");
		} catch (IdentifierException e) {
			fail("We should be able to put any id to an element removed from a model.");
		}
	}

	/**
	 * 
	 */
	@Test public void testUnRegister2() {
		
		Reaction r1 = model.removeReaction("R1");

		assertTrue(r1.getReactant("SP1") != null);

		assertTrue(model.findNamedSBase("LP1") == null);
	}
	
}
