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
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.codehaus.staxmate.SMOutputFactory;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.ASTNode.Type;
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
	
	
	/**
	 * Formats the real number in a valid way for mathML.
	 * When java read 0.000166, it transforms it to 1.66E-4.
	 * This DecimalFormat will wrote the number as it was read.
	 */
	DecimalFormat realFormat = new DecimalFormat(StringTools.REAL_FORMAT,
			new DecimalFormatSymbols(Locale.ENGLISH));

	/**
	 * 
	 * @param writer
	 * @param indent
	 */
	public MathMLXMLStreamCompiler(XMLStreamWriter writer, String indent) {
		if (writer == null) {
			throw new IllegalArgumentException(
					"Cannot create a MathMLXMLStreamCompiler with a null writer.");
		}

		this.writer = writer;
		this.indent = indent;
	}

	/**
	 * 
	 * @param astNode
	 * @return
	 */
	public static String toMathML(ASTNode astNode) {

		String mathML = "";
		StringWriter stream = new StringWriter();
		
		SMOutputFactory smFactory = new SMOutputFactory(WstxOutputFactory
				.newInstance());

		try {
			XMLStreamWriter writer = smFactory.createStax2Writer(stream);
			
			writer.writeStartDocument();
			writer.writeCharacters("\n");
			writer.writeStartElement("math");
			writer.writeAttribute("xmlns", "http://www.w3.org/1998/Math/MathML");
			writer.writeCharacters("\n");

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

		// System.out.println("MathMLXMLStreamCompiler : compile : node type = " + astNode.getType()) ;
		
		if (astNode.isInfinity()) {
			compilePositiveInfinity(astNode);
		} else if (astNode.isNegInfinity()) {
			compileNegativeInfinity(astNode);
		} else {
			
		
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
					System.out.println("MathMLXMLStreamCompiler : compile : Type.DIVIDE : getNumChildren() != 2 !!!");
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
				compileLog(astNode);
				break;
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
				compileUserFunction(astNode);
				break;
			case FUNCTION_PIECEWISE:
				compilePiecewise(astNode);
				break;
			case LAMBDA:
				compileLambda(astNode);
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
		}
	}

	
	

	private void compileNegativeInfinity(ASTNode astNode) {
		// TODO Auto-generated method stub
		
	}


	private void compilePositiveInfinity(ASTNode astNode) {
		// TODO Auto-generated method stub
		
	}


	private void compileCSymbol(ASTNode astNode) {
		
		try {
			// delay works like a function, so we need to write all the children
			if (astNode.getType() == Type.FUNCTION_DELAY) {
				writer.writeCharacters(indent);
				writer.writeStartElement("apply");
				writer.writeCharacters("\n");
				indent += "  ";
			}
			
			writer.writeCharacters(indent);
			writer.writeStartElement("csymbol");
			writer.writeAttribute("encoding", astNode.getEncoding() != null ? astNode.getEncoding() : "text");
			writer.writeAttribute("definitionURL", astNode.getDefinitionURL() != null ? astNode.getDefinitionURL() : "");
			writer.writeCharacters(" ");
			writer.writeCharacters(astNode.getName());
			writer.writeCharacters(" ");
			writer.writeEndElement();
			writer.writeCharacters("\n");

			// delay works like a function, so we need to write all the children
			if (astNode.getType() == Type.FUNCTION_DELAY) {

				for (ASTNode arg : astNode.getListOfNodes()) {
					compile(arg);
				}
				
				// end apply element
				writeEndElement();
			}
						
		} catch (XMLStreamException e) {			
			e.printStackTrace();
		}
		
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
			writer.writeCharacters("\n");
			
		} catch (XMLStreamException e) {			
			e.printStackTrace();
		}
		
	}



	private void compileReal(ASTNode astNode) {

		try {
			writer.writeCharacters(indent);
			writer.writeStartElement("cn");
			if (astNode.isSetNumberType()) {
				writer.writeAttribute("type", "real");
			}
			writer.writeCharacters(" ");
			
			// We need the DecimalFormat to have number like 0.000166, that get transformed into 1.66E-4 which is 
			// invalid in mathML, written properly 
			String doubleStr = realFormat.format(astNode.getReal());
			
			writer.writeCharacters(doubleStr);
			writer.writeCharacters(" ");
			writer.writeEndElement();
			writer.writeCharacters("\n");
			
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
			writer.writeCharacters(realFormat.format(astNode.getMantissa()));
			writer.writeCharacters(" ");
			writer.writeEmptyElement("sep");
			writer.writeCharacters(" ");
			writer.writeCharacters(realFormat.format(astNode.getExponent()));
			writer.writeCharacters(" ");
			writer.writeEndElement();
			writer.writeCharacters("\n");
			
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
			writer.writeCharacters(Integer.toString(astNode.getNumerator()));
			writer.writeCharacters(" ");
			writer.writeEmptyElement("sep");
			writer.writeCharacters(" ");
			writer.writeCharacters(Integer.toString(astNode.getDenominator()));
			writer.writeCharacters(" ");
			writer.writeEndElement();
			writer.writeCharacters("\n");
			
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
			writer.writeCharacters("\n");
			
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

		if (astNode.getNumChildren() == 1) {
			compileFunctionElement(astNode);
		} else if (astNode.getNumChildren() == 2) {
			try {
				
				writer.writeCharacters(indent);
				writer.writeStartElement("apply");
				writer.writeCharacters("\n");
				indent += "  ";
				
				writer.writeCharacters(indent);
				writer.writeEmptyElement("root");
				writer.writeCharacters("\n");

				// write the degree element
				writer.writeCharacters(indent);
				writer.writeStartElement("degree");
				writer.writeCharacters("\n");
				indent += "  ";
				
				compile(astNode.getChild(0));
				
				// end degree element								
				writeEndElement();
				
				compile(astNode.getChild(1));
				
				// end apply element
				writeEndElement();
				
			} catch (XMLStreamException e) {
				e.printStackTrace();
			}
		} else {
			// log Error
			System.out.println("MathMLXMLStreamCompiler : compileRootElement : cannot have more than 2 children on a root node !!");			
		}
		
	}

	private void compileLambda(ASTNode astNode) {
		try {
			
			writer.writeCharacters(indent);
			writer.writeStartElement("lambda");
			writer.writeCharacters("\n");
			indent += "  ";
			
			int nbChildren = astNode.getChildCount();
			
			if (nbChildren > 1) {				
				for (int i = 0; i < nbChildren - 1; i++ ) {
					ASTNode arg = astNode.getChild(i);
					compileBvar(arg);
				}
			}

			compile(astNode.getRightChild());
			
			writeEndElement();
			
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		
	}


	private void compileBvar(ASTNode arg) {


		try {
			writer.writeCharacters(indent);
			writer.writeStartElement("bvar");
			writer.writeCharacters("\n");
			indent += "  ";
			
			if (!arg.isName()) {
				System.out.println("MathMLXMLStreamCompiler : compileBvar : Can only have node of type NAME there !!!!");
			}
			
			compileCi(arg);
			
			writeEndElement();

		} catch (XMLStreamException e) {
			e.printStackTrace();
		}

	}


	private void compilePiecewise(ASTNode astNode) {

		int nbChildren = astNode.getNumChildren();
		boolean writeOtherwise = true;
		
		if (nbChildren % 2 != 1) {
			writeOtherwise = false;
		}

		try {

			writer.writeCharacters(indent);
			writer.writeStartElement("piecewise");
			writer.writeCharacters("\n");
			indent += "  ";

			for (int i = 0; i < nbChildren - 1; i = i + 2) {

				writer.writeCharacters(indent);
				writer.writeStartElement("piece");
				writer.writeCharacters("\n");
				indent += "  ";
				
				compile(astNode.getChild(i));
				compile(astNode.getChild(i + 1));

				// end piece element								
				writeEndElement();
			}

			if (writeOtherwise) {
				
				writer.writeCharacters(indent);
				writer.writeStartElement("otherwise");
				writer.writeCharacters("\n");
				indent += "  ";
				
				compile(astNode.getRightChild());
				
				writeEndElement();
			}
			
			// end piecewise element
			writeEndElement();
			
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}

	}


	private void compileLog(ASTNode astNode) {
		
		if (astNode.getNumChildren() == 1) {
			compileFunctionElement(astNode);
		} else if (astNode.getNumChildren() == 2) {
			try {
				
				writer.writeCharacters(indent);
				writer.writeStartElement("apply");
				writer.writeCharacters("\n");
				indent += "  ";
				
				writer.writeCharacters(indent);
				writer.writeEmptyElement("log");
				writer.writeCharacters("\n");

				// write the logbase element
				writer.writeCharacters(indent);
				writer.writeStartElement("logbase");
				writer.writeCharacters("\n");
				indent += "  ";
				
				compile(astNode.getChild(0));
				
				// end logbase element								
				writeEndElement();
				
				compile(astNode.getChild(1));
				
				// end apply element
				writeEndElement();
				
			} catch (XMLStreamException e) {
				e.printStackTrace();
			}
		} else {
			// log Error
			System.out.println("MathMLXMLStreamCompiler : compileRootElement : cannot have more than 2 children on a root node !!");			
		}
	}


	private void compileConstantElement(ASTNode astNode) {
		String constantName = astNode.getType().toString().substring(9).toLowerCase();
		
		try {
			
			writer.writeCharacters(indent);
			writer.writeEmptyElement(constantName);
			writer.writeCharacters("\n");

		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 
	 * 
	 * @param string element name
	 * @param astNode the node containing all the element information
	 */
	private void compileElement(String string, ASTNode astNode) {

		// TODO : Not good, we need to write the possible attribute or annotations of the element node
		function(string, astNode.getListOfNodes());
		
	}


	private void compileRelationalOperator(ASTNode astNode) {
		String functionName = astNode.getType().toString().substring(11).toLowerCase();
		
		compileElement(functionName, astNode);
		
	}

	private void compileLogicalOperator(ASTNode astNode) {
		String functionName = astNode.getType().toString().substring(8).toLowerCase();
		
		compileElement(functionName, astNode);
		
	}

	private void function(String functionName, List<ASTNode> args) {

		function(functionName, args.toArray(new ASTNode[args.size()]));
	}
	
	private void function(String functionName, ASTNode... args) {
		try {
			
			writer.writeCharacters(indent);
			writer.writeStartElement("apply");
			writer.writeCharacters("\n");
			indent += "  ";
			
			writer.writeCharacters(indent);
			writer.writeEmptyElement(functionName);
			writer.writeCharacters("\n");

			for (ASTNode arg : args) {
				compile(arg);
			}
			
			writeEndElement();
			
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
	}

	private void compileUserFunction(ASTNode  astNode) {
		try {

			writer.writeCharacters(indent);
			writer.writeStartElement("apply");
			writer.writeCharacters("\n");
			indent += "  ";

			compileCi(astNode);

			for (ASTNode arg : astNode.getChildren()) {
				compile(arg);
			}

			writeEndElement();
			
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}

	}

	private void writeEndElement() {
		try {

			indent = indent.substring(2);
			writer.writeCharacters(indent);
			writer.writeEndElement();
			writer.writeCharacters("\n");

		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		
		double x = 0.0050;
		double y = 1.0;
		double z = 1.66E-4;
		double zz = 12548698515426596325478965230.33254;
		
		System.out.println("Test Double formatting ");
		
		// NumberFormat format = DecimalFormat.getInstance();
		DecimalFormat format = new DecimalFormat(StringTools.REAL_FORMAT, new DecimalFormatSymbols(Locale.ENGLISH));
		
		System.out.println("x = " + format.format(x));
		System.out.println("y = " + format.format(y));
		System.out.println("z = " + format.format(z) + " " + format.getMaximumFractionDigits());
		System.out.println("zz = " + format.format(zz) + " ");
	}
	
}