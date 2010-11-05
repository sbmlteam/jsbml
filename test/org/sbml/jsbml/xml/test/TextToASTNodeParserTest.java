package org.sbml.jsbml.xml.test;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.text.parser.TextToASTNodeParser;

public class TextToASTNodeParserTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Model m = new Model("test", 2, 4);
			m.createFunctionDefinition("f");
			m.createRateRule();
			while (true) {
				TextToASTNodeParser parser = new TextToASTNodeParser(System.in);
				System.out.println("Reading from standard input...");
				System.out.print("Enter an expression like \"1+(2+3)*4;\" :");
				ASTNode node = parser.parse();
				System.out.println(node.toLaTeX());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
