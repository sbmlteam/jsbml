/*
 * $Id:  LibSBMLChangeListenerTest.java 17:00:18 maichele$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2012 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.test;

import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.Unit.Kind;
import org.sbml.jsbml.xml.libsbml.LibSBMLChangeListener;
import org.sbml.jsbml.xml.libsbml.LibSBMLReader;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 * This class is used to test LibSBMLChangeListener with JUnit-tests
 * @author Meike Aichele
 * @version $Rev$
 * @since 1.0
 */
public class LibSBMLChangeListenerTest {
	static SBMLDocument doc = null;
	static org.sbml.libsbml.SBMLDocument libDoc = null;

	@BeforeClass
	public static void beforeTesting(){
		try {
			// Load LibSBML:
			System.loadLibrary("sbmlj");
			// Extra check to be sure we have access to libSBML:
			Class.forName("org.sbml.libsbml.libsbml");

			// Read SBML file using LibSBML and convert it to JSBML:
			libDoc = new org.sbml.libsbml.SBMLDocument(2,4);
			LibSBMLReader reader = new LibSBMLReader();
			doc = reader.convertSBMLDocument(libDoc);

			//org.sbml.libsbml.SBMLDocument libDoc = new org.sbml.libsbml.SBMLDocument(2,4);
			doc.addTreeNodeChangeListener(new LibSBMLChangeListener(doc, libDoc));
			

		} catch (Throwable e) {
			e.printStackTrace();
		}

	}
	
	/*
	 * test-method to check nodeAdded() in LibSBMLChangeListener
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testNodeAdded() {
		doc.createModel("model01");
		assertEquals("model01", libDoc.getModel().getId());
		doc.getModel().createCompartment("comp001");
		assertNotNull(libDoc.getModel().getCompartment("comp001"));
		doc.getModel().createCompartmentType("comptype001");
		assertNotNull(libDoc.getModel().getCompartmentType("comptype001"));
		doc.getModel().createReaction("reac001");
		assertNotNull(libDoc.getModel().getReaction("reac001"));
		doc.getModel().createEvent("evt001");
		assertNotNull(libDoc.getModel().getEvent("evt001"));
		doc.getModel().createKineticLaw();
		assertNotNull(libDoc.getModel().getReaction("reac001").getKineticLaw());
		doc.getModel().createDelay();
		assertNotNull(libDoc.getModel().getEvent("evt001").getDelay());
		doc.getModel().createEventAssignment();
		assertNotNull(libDoc.getModel().getEvent("evt001").getEventAssignment(0));
		doc.getModel().createModifier("mod01");
		assertNotNull(libDoc.getModel().getReaction("reac001").getModifier("mod01"));
		doc.getModel().createFunctionDefinition("funcDef");
		assertNotNull(libDoc.getModel().getFunctionDefinition("funcDef"));
		doc.getModel().createConstraint();
		assertNotNull(libDoc.getModel().getListOfConstraints());
		doc.getModel().createParameter("param01");
		assertNotNull(libDoc.getModel().getParameter("param01"));
		doc.getModel().createProduct();
		assertNotNull(libDoc.getModel().getReaction("reac001").getListOfProducts());
		doc.getModel().createReactant();
		assertNotNull(libDoc.getModel().getReaction("reac001").getListOfReactants());
		doc.getModel().createSpecies("spec01", doc.getModel().getCompartment("comp001"));
		assertNotNull(libDoc.getModel().getSpecies("spec01"));
		assertTrue(libDoc.getModel().getSpecies("spec01").isSetCompartment());
		doc.getModel().createSpeciesType("st01");
		assertNotNull(libDoc.getModel().getSpeciesType("st01"));
		doc.getModel().createTrigger();
		assertTrue(libDoc.getModel().getEvent("evt001").isSetTrigger());
		doc.getModel().createUnitDefinition("udef1");
		assertNotNull(libDoc.getModel().getUnitDefinition("udef1"));
		doc.getModel().createUnit(Kind.METRE);
		assertNotNull(libDoc.getModel().getUnitDefinition("udef1").getListOfUnits());
	}
	
	/*
	 * test-method to check propertyChanged() in LibSBMLChangeListener
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testPropertyChanged() {
		doc.getModel().setId("model02");
		assertEquals(libDoc.getModel().getId(), "model02");
		doc.getModel().setName("modelName");
		assertEquals(libDoc.getModel().getName(), "modelName");
		doc.getModel().getCompartment("comp001").setConstant(true);
		assertTrue(libDoc.getModel().getCompartment("comp001").getConstant());
		doc.getModel().getCompartment("comp001").setCompartmentType("comptype001");
		assertEquals(libDoc.getModel().getCompartment("comp001").getCompartmentType(), "comptype001");
		doc.getModel().getCompartment("comp001").setMetaId("metaCompId");
		assertEquals(libDoc.getModel().getCompartment("comp001").getMetaId(), "metaCompId");
		doc.getModel().getCompartment("comp001").setSize(0.5d);
		assertEquals(libDoc.getModel().getCompartment("comp001").getSize(), 0.5d, 1E-24d);
		doc.getModel().getCompartment("comp001").setSpatialDimensions(2);
		assertEquals(libDoc.getModel().getCompartment("comp001").getSpatialDimensions(), 2);
		doc.getModel().getEvent("evt001").unsetTrigger();
		assertNull(libDoc.getModel().getEvent("evt001").getTrigger());
	}
	
	/*
	 * test-method to check nodeRemoved() in LibSBMLChangeListener
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testNodeRemoved() {
		assertNotNull(doc.getModel().getCompartment("comp001"));
		doc.getModel().removeCompartment("comp001");
		assertNull(libDoc.getModel().getCompartment("comp001"));
		doc.getModel().removeCompartmentType("comptype001");
		assertNull(libDoc.getModel().getCompartmentType("comptype001"));
		doc.getModel().removeReaction("reac001");
		assertNull(libDoc.getModel().getReaction("reac001"));
		doc.getModel().removeEvent("evt001");
		assertNull(libDoc.getModel().getEvent("evt001"));
		doc.getModel().removeFunctionDefinition("funcDef");
		assertNull(libDoc.getModel().getFunctionDefinition("funcDef"));
		doc.getModel().removeParameter("param01");
		assertNull(libDoc.getModel().getParameter("param01"));
		doc.getModel().removeSpeciesType("st01");
		assertNull(libDoc.getModel().getSpeciesType("st01"));
		doc.getModel().removeUnitDefinition("udef1");
		assertNull(libDoc.getModel().getUnitDefinition("udef1"));		
	}
}
