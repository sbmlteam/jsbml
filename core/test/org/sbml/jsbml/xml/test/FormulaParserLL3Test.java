package org.sbml.jsbml.xml.test;

import java.io.StringReader;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.JSBML;
import org.sbml.jsbml.text.parser.FormulaParserLL3;
import org.sbml.jsbml.text.parser.IFormulaParser;

public class FormulaParserLL3Test {


	public static void main(String[] args) throws Exception {
		
		IFormulaParser parser = new FormulaParserLL3(new StringReader(""));
		
		ASTNode node = ASTNode.parseFormula("true || false", parser);
		
		System.out.println(node.toFormula());
		
		node = ASTNode.parseFormula("true && false", parser);
		
		System.out.println(node.toFormula());

		node = ASTNode.parseFormula("and(true,false)", parser);		
		System.out.println(node.toFormula());
		
		node = ASTNode.parseFormula("! true", parser);		
		System.out.println(node.toFormula());

		node = ASTNode.parseFormula("not(true)", parser);		
		System.out.println(node.toFormula());

		node = ASTNode.parseFormula("NOT(true)", parser);		
		System.out.println(node.toFormula());

		node = ASTNode.parseFormula("NOT true", parser);		
		System.out.println(node.toFormula());
		
//		node = ASTNode.parseFormula("not true", parser); // Not supported anymore by the new parser		
//		System.out.println(node.toFormula());

		node = ASTNode.parseFormula("and(or(gt(x,2), lt(S1, 4)),and(x >= 2, (S1 AND true) || (true && true)))", parser);		
		System.out.println(node.toFormula());

//		node = ASTNode.parseFormula("4!", parser);  // Not supported anymore by the new parser		
//		System.out.println(node.toFormula());
		
		node = ASTNode.parseFormula("selector(S, 1)", parser);		
		System.out.println(node.toFormula());

		node = ASTNode.parseFormula("selector(S, 1, 5)", parser);		
		System.out.println(node.toFormula());
		
		String mathMLSelector = node.toMathML();
		
		System.out.println(mathMLSelector);

		node = JSBML.readMathMLFromString(mathMLSelector);
		
		System.out.println(node.toFormula());
		
		mathMLSelector = node.toMathML();
		System.out.println(mathMLSelector);
	}
}
