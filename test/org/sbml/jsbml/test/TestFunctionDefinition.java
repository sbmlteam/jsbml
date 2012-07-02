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
