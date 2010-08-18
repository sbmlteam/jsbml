/*
 * $Id: MathMLComnpiler.java 97 2009-12-10 09:08:54Z andreas-draeger $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/src/org/sbml/jsbml/util/MathMLComnpiler.java $
 *
 * 
 *==================================================================================
 * Copyright (c) 2009 The jsbml team.
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
package org.sbml.jsbml.util.compilers;

import java.io.StringWriter;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.codehaus.staxmate.SMOutputFactory;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.util.StringTools;

import com.ctc.wstx.stax.WstxOutputFactory;



/**
 *
 * 
 * @author rodrigue
 * 
 */
public class MathMLXMLStreamCompiler {

	private String indent;
	private XMLStreamWriter writer;

	public MathMLXMLStreamCompiler(XMLStreamWriter writer, String indent) {
		if (writer == null) {
			throw new IllegalArgumentException(
					"Cannot create a MathMLXMLStreamCompiler with a null writer !!");
		}

		this.writer = writer;
		this.indent = indent;
	}

	
	public static String toMathML(ASTNode astNode) {

		String mathML = "";
		StringWriter stream = new StringWriter();
		
		SMOutputFactory smFactory = new SMOutputFactory(WstxOutputFactory
				.newInstance());

		try {
			XMLStreamWriter writer = smFactory.createStax2Writer(stream);
			
			writer.writeStartDocument();
			writer.writeCharacters(StringTools.newLine());
			writer.writeStartElement("math");
			writer.writeAttribute("xmlns", "http://www.w3.org/1998/Math/MathML");
			writer.writeCharacters(StringTools.newLine());

			MathMLXMLStreamCompiler compiler = new MathMLXMLStreamCompiler(writer, "  ");
			compiler.compile(astNode);

			writer.writeEndElement();
			writer.writeEndDocument();
			writer.close();

		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		
		mathML = stream.toString();
		
		return mathML;
	}
	
	
	/**
	 * Compiles this {@link ASTNode} and produce an XMLStreamWriter representing this node in mathML.
	 * 
	 *
	 * @throws SBMLException
	 */
	public void compile(ASTNode astNode) {

		// System.out.println("MathMLXMLStreamCompiler : compile : node type = " + getType()) ;
		
		/*
		 // TODO : check if we need these if/else and if any others Type are missing

		} else if (isInfinity()) {
			value = compiler.getPositiveInfinity();
		} else if (isNegInfinity()) {
			value = compiler.getNegativeInfinity();
		} else {
			*/
		
			switch (astNode.getType()) {
			/*
			 * Numbers
			 */
			case REAL:
				compileReal(astNode);
				break;
			case INTEGER:
				compileInteger(astNode);
				break;
			/*
			 * Operators
			 */
			case DIVIDE:
				if (astNode.getNumChildren() != 2) {
					// TODO : log that to a file and also add it to an error log like libsbml
					// new SBMLException("fractions can only have one numerator and one denominator, here " + astNode.getNumChildren() +" elements are given");
				}
			case POWER:
			case PLUS:
			case MINUS:
			case TIMES:
				compileElement(astNode);
				break;
			case RATIONAL:
				compileRational(astNode);
				break;
			/*
			 * Names of identifiers: parameters, functions, species etc.
			 */
			case NAME:
				compileCi(astNode);
				break;
			/*
			 * Type: pi, e, true, false, Avogadro
			 */
			case CONSTANT_PI:
			case CONSTANT_E:
			case CONSTANT_TRUE:
			case CONSTANT_FALSE:
				compileConstantElement(astNode);
				break;
				/*
				* csymbol element
				*
				*/
			case NAME_TIME:
			case FUNCTION_DELAY:
			case NAME_AVOGADRO:
				compileCSymbol(astNode);
				break;
			case REAL_E:
				compileReal_e(astNode);
				break;
			/*
			 * Basic Functions
			 */
			case FUNCTION_LOG:
				// TODO
/*				if (getNumChildren() == 2) {
					value = compiler.log(getLeftChild(), getRightChild());
				} else {
					value = compiler.log(getRightChild());
				}
*/				break;
			case FUNCTION_ABS:
			case FUNCTION_ARCCOS:
			case FUNCTION_ARCCOSH:
			case FUNCTION_ARCCOT:
			case FUNCTION_ARCCOTH:
			case FUNCTION_ARCCSC:
			case FUNCTION_ARCCSCH:
			case FUNCTION_ARCSEC:
			case FUNCTION_ARCSECH:
			case FUNCTION_ARCSIN:
			case FUNCTION_ARCSINH:
			case FUNCTION_ARCTAN:
			case FUNCTION_ARCTANH:
			case FUNCTION_CEILING:
			case FUNCTION_COS:
			case FUNCTION_COSH:
			case FUNCTION_COT:
			case FUNCTION_COTH:
			case FUNCTION_CSC:
			case FUNCTION_CSCH:
			case FUNCTION_EXP:
			case FUNCTION_FACTORIAL:
			case FUNCTION_FLOOR:
			case FUNCTION_LN:
			case FUNCTION_POWER:
			case FUNCTION_SEC:
			case FUNCTION_SECH:
			case FUNCTION_SIN:
			case FUNCTION_SINH:
			case FUNCTION_TAN:
			case FUNCTION_TANH:
				compileFunctionElement(astNode);
				break;
			case FUNCTION_ROOT:
				compileRootElement(astNode);
				break;
			case FUNCTION:
				compileFunctionElement(astNode);
				break;
			case FUNCTION_PIECEWISE:
				// TODO
				break;
			case LAMBDA:
				// TODO
				break;
			/*
			 * Logical and relational functions
			 */
			case LOGICAL_AND:
			case LOGICAL_XOR:
			case LOGICAL_OR:
			case LOGICAL_NOT:
				compileLogicalOperator(astNode);
				break;
			case RELATIONAL_EQ:
			case RELATIONAL_GEQ:
			case RELATIONAL_GT:
			case RELATIONAL_NEQ:
			case RELATIONAL_LEQ:
			case RELATIONAL_LT:
				compileRelationalOperator(astNode);
				break;
			default: // UNKNOWN:
				// TODO : log a problem
				System.out.println("MathMLXMLStreamCompiler : !!!!! I don't know what to do with the node of type " + astNode.getType());
				break;
			}
		
//		value.setType(getType());
//		value.setLevel(getParentSBMLObject().getLevel());
//		value.setVersion(getParentSBMLObject().getVersion());
//		
//		return value;
	}

	
	
	private void compileCSymbol(ASTNode astNode) {
		
		try {
			writer.writeCharacters(indent);
			writer.writeStartElement("csymbol");
			writer.writeAttribute("definitionURL", getDefinitionURL(astNode));
			writer.writeCharacters(astNode.getName());
			
			// delay works like a function, so we need to write all the children
			if (astNode.getType() == Type.FUNCTION_DELAY) {
				indent += "  ";

				for (ASTNode arg : astNode.getListOfNodes()) {
					compile(arg);
				}
				
				indent = indent.substring(2);
			}
			
			writer.writeEndElement();
			writer.writeCharacters(StringTools.newLine());
			
		} catch (XMLStreamException e) {			
			e.printStackTrace();
		}
		
	}


	private String getDefinitionURL(ASTNode astNode) {

		String definitionURL = "";
		
		switch (astNode.getType()) {
			/*
			* csymbol element
			*
			*/
		case NAME_TIME:
			definitionURL = "http://www.sbml.org/sbml/symbols/time";
			break;
		case FUNCTION_DELAY:
			definitionURL = "http://www.sbml.org/sbml/symbols/delay";
			break;
		case NAME_AVOGADRO:
			definitionURL = "http://www.sbml.org/sbml/symbols/avogadro";
			break;
		}
		
		return definitionURL;
	}



	private void compileInteger(ASTNode astNode) {
		
		try {
			writer.writeCharacters(indent);
			writer.writeStartElement("cn");
			writer.writeAttribute("type", "integer");
			writer.writeCharacters(" ");
			writer.writeCharacters(Integer.toString(astNode.getInteger()));
			writer.writeCharacters(" ");
			writer.writeEndElement();
			writer.writeCharacters(StringTools.newLine());
			
		} catch (XMLStreamException e) {			
			e.printStackTrace();
		}
		
	}



	private void compileReal(ASTNode astNode) {

		try {
			writer.writeCharacters(indent);
			writer.writeStartElement("cn");
			writer.writeAttribute("type", "real");
			writer.writeCharacters(" ");
			writer.writeCharacters(Double.toString(astNode.getReal()));
			writer.writeCharacters(" ");
			writer.writeEndElement();
			writer.writeCharacters(StringTools.newLine());
			
		} catch (XMLStreamException e) {			
			e.printStackTrace();
		}
		
	}

	private void compileReal_e(ASTNode astNode) {

		try {
			writer.writeCharacters(indent);
			writer.writeStartElement("cn");
			writer.writeAttribute("type", "e-notation");
			writer.writeCharacters(" ");
			writer.writeCharacters(Double.toString(astNode.getMantissa()));
			writer.writeCharacters(" ");
			writer.writeEmptyElement("sep");
			writer.writeCharacters(" ");
			writer.writeCharacters(Double.toString(astNode.getExponent()));
			writer.writeCharacters(" ");
			writer.writeEndElement();
			writer.writeCharacters(StringTools.newLine());
			
		} catch (XMLStreamException e) {			
			e.printStackTrace();
		}

		
	}


	private void compileRational(ASTNode astNode) {

		try {
			writer.writeCharacters(indent);
			writer.writeStartElement("cn");
			writer.writeAttribute("type", "rational");
			writer.writeCharacters(" ");
			writer.writeCharacters(Double.toString(astNode.getNumerator()));
			writer.writeCharacters(" ");
			writer.writeEmptyElement("sep");
			writer.writeCharacters(" ");
			writer.writeCharacters(Double.toString(astNode.getDenominator()));
			writer.writeCharacters(" ");
			writer.writeEndElement();
			writer.writeCharacters(StringTools.newLine());
			
		} catch (XMLStreamException e) {			
			e.printStackTrace();
		}

		
	}


	private void compileCi(ASTNode astNode) {

		try {
			writer.writeCharacters(indent);
			writer.writeStartElement("ci");
			writer.writeCharacters(" ");
			writer.writeCharacters(astNode.getName());
			writer.writeCharacters(" ");
			writer.writeEndElement();
			writer.writeCharacters(StringTools.newLine());
			
		} catch (XMLStreamException e) {			
			e.printStackTrace();
		}

		
	}



	private void compileElement(ASTNode astNode) {

		compileElement(astNode.getType().toString().toLowerCase(), astNode);
	}

	private void compileFunctionElement(ASTNode astNode) {
		String functionName = astNode.getType().toString().substring(9).toLowerCase();
		
		compileElement(functionName, astNode);
	}

	private void compileRootElement(ASTNode astNode) {

		// TODO
	}

	private void compileConstantElement(ASTNode astNode) {
		String functionName = astNode.getType().toString().substring(9).toLowerCase();
		
		compileElement(functionName, astNode);
	}
	
	/**
	 * 
	 * 
	 * @param string element name
	 * @param astNode the node containing all the element information
	 */
	private void compileElement(String string, ASTNode astNode) {

		function(string, astNode.getListOfNodes());
		
	}


	private void compileRelationalOperator(ASTNode astNode) {
		String functionName = astNode.getType().toString().substring(11).toLowerCase();
		
		compileElement(functionName, astNode);
		
	}

	private void compileLogicalOperator(ASTNode astNode) {
		String functionName = astNode.getType().toString().substring(11).toLowerCase();
		
		compileElement(functionName, astNode);
		
	}


	
	private void function(String functionName, List<ASTNode> args) {

		function(functionName, args.toArray(new ASTNode[args.size()]));
	}
	
	private void function(String functionName, ASTNode... args) {
		try {
			
			writer.writeCharacters(indent);
			writer.writeStartElement("apply");
			writer.writeCharacters(StringTools.newLine());
			indent += "  ";
			
			writer.writeCharacters(indent);
			writer.writeEmptyElement(functionName);
			writer.writeCharacters(StringTools.newLine());

			for (ASTNode arg : args) {
				compile(arg);
			}
			
			indent = indent.substring(2);
			writer.writeCharacters(indent);
			writer.writeEndElement();
			writer.writeCharacters(StringTools.newLine());			
			
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}

	}
	
}