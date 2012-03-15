/*
 * $Id: FormulaTest.java 102 2009-12-13 19:52:50Z andreas-draeger $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/src/org/sbml/jsbml/io/FormulaTest.java $
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
package org.sbml.jsbml.libsbmlio;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.Annotation;
import org.sbml.jsbml.CVTerm;
import org.sbml.jsbml.Delay;
import org.sbml.jsbml.EventAssignment;
import org.sbml.jsbml.History;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.RateRule;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.Unit.Kind;
import org.sbml.jsbml.test.gui.JTreeOfSBML;
import org.sbml.jsbml.xml.libsbml.LibSBMLChangeListener;
import org.sbml.jsbml.xml.libsbml.LibSBMLReader;

/**
 * @author Andreas Dr&auml;ger
 * @date 2011-02-03
 * @version $Rev$
 */
public class SimpleApplication {

	/**
	 * @param args the path to a valid SBML file.
	 */
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		try {
			// Load LibSBML:
			System.loadLibrary("sbmlj");
			// Extra check to be sure we have access to libSBML:
			Class.forName("org.sbml.libsbml.libsbml");

			// Read SBML file using LibSBML and convert it to JSBML:
			LibSBMLReader reader = new LibSBMLReader();
			SBMLDocument doc = reader.convertSBMLDocument(args[0]);
			//SBMLDocument doc = new SBMLDocument(2,4);

			org.sbml.libsbml.SBMLDocument libDoc = reader.getOriginalModel().getSBMLDocument();
			//org.sbml.libsbml.SBMLDocument libDoc = new org.sbml.libsbml.SBMLDocument(2,4);
			doc.addTreeNodeChangeListener(new LibSBMLChangeListener(doc, libDoc));

			/*
			 * Some tests to test nodeAdded in LibSBMLChangeListener
			 */
			Model model = doc.getModel();
			if (model == null) {
				model = doc.createModel("test_model");
			}
			model.createCompartmentType("ct001");
			model.createCompartmentType("ct002");
			model.createCompartment("c001");
			model.createCompartment("c002");
			model.createAlgebraicRule();
			model.createAssignmentRule();
			model.createConstraint();
			model.createEvent("ev001");
			model.createEvent("ev002");
			model.getEvent("ev002").addCVTerm(new CVTerm());
			model.createDelay();
			model.createUnitDefinition();
			model.createUnit(Kind.AMPERE);
			model.createUnit(Kind.FARAD);
			model.createEventAssignment();
			model.createFunctionDefinition("func001"); 
			model.createFunctionDefinition("func002"); 
			model.createInitialAssignment();
			model.createParameter("param001");
			model.createReaction("newReac001");
			model.createKineticLaw();
			model.createModifier();
			model.createProduct("prod001");
			model.createReactant();
			RateRule rr = model.createRateRule();
			model.createSpecies("s001", model.getCompartment("c001"));
			//model.createKineticParameter("param001");

			/*
			 * some tests to test propertyChanged in LibSBMLChangeListener
			 */
			model.getSpecies("s001").setConstant(true);
			model.getSpecies("s001").setBoundaryCondition(true);
			model.getSpecies("s001").setHasOnlySubstanceUnits(false);
			model.getSpecies("s001").setId("s01");
			model.getSpecies("s01").setInitialAmount(0.3);
			model.getFunctionDefinition("func001").setAnnotation(new Annotation("new annotation"));
			model.getFunctionDefinition("func001").setHistory(new History());
			model.getFunctionDefinition("func001").setLevel(2);
			model.getFunctionDefinition("func001").setMath(new ASTNode(Type.LAMBDA));
			model.getFunctionDefinition("func001").setName("funcdef001");
			model.getFunctionDefinition("func001").setNotes("new notes");
			model.getFunctionDefinition("func001").setVersion(3);
			model.getFunctionDefinition("func001").setSBOTerm(3);
			model.getEvent("ev001").setDelay(new Delay());
			model.getEvent("ev001").setMetaId("new metaid event");
			model.getEvent("ev001").addEventAssignment(new EventAssignment());
			model.getEvent("ev001").getEventAssignment(0).setVariable("variable");
			model.getEvent("ev001").getEventAssignment(0).setMath(new ASTNode(Type.DIVIDE));
//			model.getEvent("ev001").setTimeUnits("NewTimeUnits");
//			model.getEvent("ev001").setUseValuesFromTriggerTime(true);
//			model.getEvent("ev001").setUnits(Kind.GRAM);
			

			/*
			 * some tests to test nodeRemoved in LibSBMLChangeListener
			 */
			model.removeCompartment("c001");
			model.getListOfSpecies().remove(model.getListOfSpecies().size()-1);
			model.removeConstraint(model.getListOfConstraints().size()-1);
			model.removeEvent("ev002");
			model.removeFunctionDefinition("func002");
			model.removeCompartmentType("ct001");
			model.removeParameter("param001");
			model.removeUnitDefinition(model.getListOfUnitDefinitions().size()-1);
			model.getReaction("newReac001").removeProduct("prod001");
			model.removeInitialAssignment(model.getListOfInitialAssignments().size()-1);
			model.removeRule(rr.getVariable());
//			model.getEvent("ev001").removeEventAssignment("variable");

			// Run some application:
			new JTreeOfSBML(doc);

			System.out.println(new org.sbml.libsbml.SBMLWriter().writeSBMLToString(libDoc));

		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}
