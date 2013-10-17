/* 
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2013 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.test;	

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.AssignmentRule;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.JSBML;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.text.parser.ParseException;

public class TestFunctionDefinition {

	/**
	 * @param args
	 * @throws ParseException 
	 * @throws XMLStreamException 
	 * @throws SBMLException 
	 */
	public static void main(String[] args) throws ParseException, SBMLException, XMLStreamException {
		
		SBMLDocument document = new SBMLDocument(2,4);
		Model model = document.createModel("testFD");
		
		FunctionDefinition location = model.createFunctionDefinition("location");
		
		location.setMath(ASTNode.parseFormula("lambda(x,0)"));
		
		model.createCompartment("cell");
		model.createParameter("P1");
		
		AssignmentRule rule = model.createAssignmentRule();
		rule.setVariable("P1");
		rule.setMath(ASTNode.parseFormula("location(cell)"));
		
		System.out.println(rule.getMathMLString());
		
		System.out.println(rule.getMath().toFormula());
		
		printASTNoteType(rule.getMath());
		
		// System.out.println("\n\nWhole SBML Document as XML :\n\n" + JSBML.writeSBMLToString(document));
		
		System.out.println("PARSING MATHML NOW ----------");
		
		model.createParameter("P2");
		
		rule = model.createAssignmentRule();
		rule.setVariable("P2");
		
		
		String mathML = "<?xml version='1.0' encoding='UTF-8'?>\n" +
				"<math xmlns=\"http://www.w3.org/1998/Math/MathML\">\n" +
				"  <apply>\n" +
				"    <ci> location </ci>\n" +
				"    <ci> cell </ci>\n" +
				"  </apply>\n" +
				"</math>";

		rule.setMath(ASTNode.readMathMLFromString(mathML));

		System.out.println(rule.getMathMLString());
		
		System.out.println(rule.getMath().toFormula());

		printASTNoteType(rule.getMath());

		String sbmlDocument = JSBML.writeSBMLToString(document);
		
		System.out.println("\n\nWhole SBML Document as XML :\n\n" + sbmlDocument);
		
		SBMLDocument wrongDocument = JSBML.readSBMLFromString(sbmlDocument.replaceFirst("location", "locatoin"));
		
		rule = (AssignmentRule) wrongDocument.getModel().getRule(0);
				
		System.out.println(rule.getMathMLString());
		
		printASTNoteType(rule.getMath());

		System.out.println(rule.getMath().toFormula());
		
	}

	public static void printASTNoteType(ASTNode astNode) 
	{
		System.out.println("" + astNode.getType());
		
		for (ASTNode child : astNode.getChildren())
		{
			printASTNoteType(child);
		}
	}
}
