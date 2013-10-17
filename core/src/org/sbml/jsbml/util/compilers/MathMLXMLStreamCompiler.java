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

package org.sbml.jsbml.util.compilers;

import java.io.StringWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.log4j.Logger;
import org.codehaus.staxmate.SMOutputFactory;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.util.StringTools;

import com.ctc.wstx.stax.WstxOutputFactory;



/**
 * Writes an {@link ASTNode} the mathML.
 * 
 * @author Nicolas Rodriguez
 * @since 0.8
 * @version $Rev$
 */
// TODO: Let this implement the regular ASTNodeCompiler interface.
public class MathMLXMLStreamCompiler {

	private String indent;
	private XMLStreamWriter writer;
	private Logger logger = Logger.getLogger(MathMLXMLStreamCompiler.class);
	
	private FindUnitsCompiler findUnitsCompiler = new FindUnitsCompiler();
	
	/**
	 * Formats the real number in a valid way for mathML.
	 * When java read 0.000166, it transforms it to 1.66E-4.
	 * This DecimalFormat will wrote the number as it was read.
	 */
	DecimalFormat realFormat = new DecimalFormat(StringTools.REAL_FORMAT,
			new DecimalFormatSymbols(Locale.ENGLISH));

	/**
	 * Create a new MathMLXMLStreamCompiler.
	 * 
	 * @param writer the writer
	 * @param indent the starting indentation
	 * 
	 * @throws IllegalArgumentException if the writer is null
	 */
	public MathMLXMLStreamCompiler(XMLStreamWriter writer, String indent) {
		if (writer == null) {
			throw new IllegalArgumentException(
					"Cannot create a XMLNodeWriter with a null writer.");
		}

		this.writer = writer;
		this.indent = indent;
	}

	/**
	 * Writes an {@link ASTNode} the mathML.
	 * 
	 * @param astNode the {@link ASTNode} to serialize as mathML
	 * @return a String representing this ASTNode as mathML.
	 */
	public static String toMathML(ASTNode astNode) {

		String mathML = "";
		StringWriter stream = new StringWriter();
		
		SMOutputFactory smFactory = new SMOutputFactory(WstxOutputFactory
				.newInstance());
		
		try {
			XMLStreamWriter writer = smFactory.createStax2Writer(stream);
			MathMLXMLStreamCompiler compiler = new MathMLXMLStreamCompiler(writer, "  ");
			boolean isSBMLNamespaceNeeded = compiler.isSBMLNamespaceNeeded(astNode);
			
			writer.writeStartDocument();
			writer.writeCharacters("\n");
			writer.writeStartElement("math");
			writer.writeNamespace(null, ASTNode.URI_MATHML_DEFINITION);	
			
			if (isSBMLNamespaceNeeded) {
				// writing the SBML namespace
				SBMLDocument doc = null;
				SBase sbase = astNode.getParentSBMLObject();
				String sbmlNamespace = SBMLDocument.URI_NAMESPACE_L3V1Core;
				
				if (sbase != null) {
					doc = sbase.getSBMLDocument();
					sbmlNamespace = doc.getSBMLDocumentNamespaces().get("xmlns");					
				}
				writer.writeNamespace("sbml", sbmlNamespace);
			}
			
			writer.writeCharacters("\n");

			writer.setPrefix("math", ASTNode.URI_MATHML_DEFINITION);
			
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
	
	
	public boolean isSBMLNamespaceNeeded(ASTNode astNode) {

		SBase sbase = astNode.getParentSBMLObject();
		
		if ((sbase != null) && (sbase.getLevel() < 3)) {
			return false;
		}

		findUnitsCompiler.reset();
		
		try {
			astNode.compile(findUnitsCompiler);
		} catch (SBMLException e) {
			// normal behavior, we use Exception to stop the recursion
			// as soon as a units is found
		}
		
		return findUnitsCompiler.isUnitsDefined();
	}


	/**
	 * Compiles this {@link ASTNode} and produce an XMLStreamWriter representing this node in mathML.
	 * 
	 *
	 * @throws SBMLException if any problems occur while checking the ASTNode tree.
	 */
	public void compile(ASTNode astNode) {

		logger.debug("compile : node type = " + astNode.getType()) ;
		
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
				if (astNode.getChildCount() != 2) {
					// TODO : add it to an error log like libsbml ?
					logger.warn(String.format(
						"compile : Type.DIVIDE : getChildCount() = %d but required is 2!",
						astNode.getChildCount()));
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
				if (astNode.getChildCount() > 0) { // In case the id is not a valid functionDefinition id, the type is set to NAME
					
					// TODO : check for SBML level 3 where the order of the listOf is not mandatory any more.
					// TODO : check how and when we put the type FUNCTION to a node.
					
					compileUserFunction(astNode);
				} else {
					compileCi(astNode);
				}
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
			case FUNCTION_SELECTOR:
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
				logger.warn("!!!!! I don't know what to do with the node of type " + astNode.getType());
				break;
			}
		}
	}

	
	

	private void compileNegativeInfinity(ASTNode astNode) {

		try {
			writer.writeCharacters(indent);
			writer.writeStartElement(ASTNode.URI_MATHML_DEFINITION, "apply");
			writer.writeCharacters("\n");
			writer.writeCharacters(indent + "  ");
			writer.writeEmptyElement(ASTNode.URI_MATHML_DEFINITION, "minus");
			writer.writeCharacters("\n");
			writer.writeCharacters(indent + "  ");
			writer.writeEmptyElement(ASTNode.URI_MATHML_DEFINITION, "infinity");
			writer.writeEndElement();
			writer.writeCharacters("\n");

		} catch (XMLStreamException e) {			
			e.printStackTrace();
		}		
	}


	private void compilePositiveInfinity(ASTNode astNode) {

		try {

			writer.writeCharacters(indent);
			writer.writeEmptyElement(ASTNode.URI_MATHML_DEFINITION, "infinity");
			writer.writeCharacters("\n");

		} catch (XMLStreamException e) {			
			e.printStackTrace();
		}		
	}

	private void compileNotANumber(ASTNode astNode) {

		try {

			writer.writeCharacters(indent);
			writer.writeEmptyElement(ASTNode.URI_MATHML_DEFINITION, "notanumber");
			writer.writeCharacters("\n");

		} catch (XMLStreamException e) {			
			e.printStackTrace();
		}

	}

	private void compilePi(ASTNode astNode) {

		try {

			writer.writeCharacters(indent);
			writer.writeEmptyElement(ASTNode.URI_MATHML_DEFINITION, "pi");
			writer.writeCharacters("\n");

		} catch (XMLStreamException e) {			
			e.printStackTrace();
		}

	}
	
	private void compileExponentiale(ASTNode astNode) {

		try {

			writer.writeCharacters(indent);
			writer.writeEmptyElement(ASTNode.URI_MATHML_DEFINITION, "exponentiale");
			writer.writeCharacters("\n");

		} catch (XMLStreamException e) {			
			e.printStackTrace();
		}

	}


	
	private void compileCSymbol(ASTNode astNode) {
		
		try {
			// delay works like a function, so we need to write all the children
			if (astNode.getType() == Type.FUNCTION_DELAY) {
				writer.writeCharacters(indent);
				writer.writeStartElement(ASTNode.URI_MATHML_DEFINITION, "apply");
				writer.writeCharacters("\n");
				indent += "  ";
			}
			
			writer.writeCharacters(indent);
			writer.writeStartElement(ASTNode.URI_MATHML_DEFINITION, "csymbol");
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
			writer.writeStartElement(ASTNode.URI_MATHML_DEFINITION, "cn");
			writer.writeAttribute("type", "integer"); // writer.writeAttribute(ASTNode.URI_MATHML_DEFINITION, "type", "integer");
			
			if (astNode.isSetUnits()) {
				writer.writeAttribute("sbml:units", astNode.getUnits());
			}
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
			
			if (Double.isNaN(astNode.getReal())) {
				compileNotANumber(astNode);
				return;
			} else if (Double.isInfinite(astNode.getReal())) {
				compilePositiveInfinity(astNode);
				return;
			} else if (Math.PI == astNode.getReal()) {
				compilePi(astNode);
				return;
			} else if (Math.E == astNode.getReal()) {
				compileExponentiale(astNode);
				return;
			} 
			
			writer.writeCharacters(indent);
			writer.writeStartElement(ASTNode.URI_MATHML_DEFINITION, "cn");
			if (astNode.isSetNumberType()) {
				writer.writeAttribute("type", "real");
			}
			if (astNode.isSetUnits()) {
				writer.writeAttribute(" sbml:units", astNode.getUnits());
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
			writer.writeStartElement(ASTNode.URI_MATHML_DEFINITION, "cn");
			writer.writeAttribute("type", "e-notation");
			writer.writeCharacters(" ");
			if (astNode.isSetUnits()) {
				writer.writeAttribute("sbml:units", astNode.getUnits());
			}
			writer.writeCharacters(realFormat.format(astNode.getMantissa()));
			writer.writeCharacters(" ");
			writer.writeEmptyElement(ASTNode.URI_MATHML_DEFINITION, "sep");
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
			writer.writeStartElement(ASTNode.URI_MATHML_DEFINITION, "cn");
			writer.writeAttribute("type", "rational");
			writer.writeCharacters(" ");
			if (astNode.isSetUnits()) {
				writer.writeAttribute("sbml:units", astNode.getUnits());
			}
			writer.writeCharacters(Integer.toString(astNode.getNumerator()));
			writer.writeCharacters(" ");
			writer.writeEmptyElement(ASTNode.URI_MATHML_DEFINITION, "sep");
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
			writer.writeStartElement(ASTNode.URI_MATHML_DEFINITION, "ci");
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

		if (astNode.getChildCount() == 1) {
			compileFunctionElement(astNode);
		} else if (astNode.getChildCount() == 2) {
			try {
				
				writer.writeCharacters(indent);
				writer.writeStartElement(ASTNode.URI_MATHML_DEFINITION, "apply");
				writer.writeCharacters("\n");
				indent += "  ";
				
				writer.writeCharacters(indent);
				writer.writeEmptyElement(ASTNode.URI_MATHML_DEFINITION, "root");
				writer.writeCharacters("\n");

				// write the degree element
				writer.writeCharacters(indent);
				writer.writeStartElement(ASTNode.URI_MATHML_DEFINITION, "degree");
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
			logger.warn("Cannot have more than 2 children on a root node !!");			
		}
		
	}

	private void compileLambda(ASTNode astNode) {
		try {
			
			writer.writeCharacters(indent);
			writer.writeStartElement(ASTNode.URI_MATHML_DEFINITION, "lambda");
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
			writer.writeStartElement(ASTNode.URI_MATHML_DEFINITION, "bvar");
			writer.writeCharacters("\n");
			indent += "  ";
			
			if (!arg.isString()) {
				logger.warn("compileBvar : can only have node of type NAME there !!!!");
			}
			
			compileCi(arg);
			
			writeEndElement();

		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
	}


	private void compilePiecewise(ASTNode astNode) {

		int nbChildren = astNode.getChildCount();
		boolean writeOtherwise = true;
		
		if (nbChildren % 2 != 1) {
			writeOtherwise = false;
		}

		try {

			writer.writeCharacters(indent);
			writer.writeStartElement(ASTNode.URI_MATHML_DEFINITION, "piecewise");
			writer.writeCharacters("\n");
			indent += "  ";

			for (int i = 0; i < nbChildren - 1; i = i + 2) {

				writer.writeCharacters(indent);
				writer.writeStartElement(ASTNode.URI_MATHML_DEFINITION, "piece");
				writer.writeCharacters("\n");
				indent += "  ";
				
				compile(astNode.getChild(i));
				compile(astNode.getChild(i + 1));

				// end piece element								
				writeEndElement();
			}

			if (writeOtherwise) {
				
				writer.writeCharacters(indent);
				writer.writeStartElement(ASTNode.URI_MATHML_DEFINITION, "otherwise");
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
		
		if (astNode.getChildCount() == 1) {
			compileFunctionElement(astNode);
		} else if (astNode.getChildCount() == 2) {
			try {
				
				writer.writeCharacters(indent);
				writer.writeStartElement(ASTNode.URI_MATHML_DEFINITION, "apply");
				writer.writeCharacters("\n");
				indent += "  ";
				
				writer.writeCharacters(indent);
				writer.writeEmptyElement(ASTNode.URI_MATHML_DEFINITION, "log");
				writer.writeCharacters("\n");

				// write the logbase element
				writer.writeCharacters(indent);
				writer.writeStartElement(ASTNode.URI_MATHML_DEFINITION, "logbase");
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
			logger.warn("compileLog : cannot have more than 2 children on a log node !!");			
		}
	}


	private void compileConstantElement(ASTNode astNode) {
		String constantName = astNode.getType().toString().substring(9).toLowerCase();
		
		if (constantName.equals("e")) {
			constantName = "exponentiale";
		}
		
		try {
			
			writer.writeCharacters(indent);
			writer.writeEmptyElement(ASTNode.URI_MATHML_DEFINITION, constantName);
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
			writer.writeStartElement(ASTNode.URI_MATHML_DEFINITION, "apply");
			writer.writeCharacters("\n");
			indent += "  ";
			
			writer.writeCharacters(indent);
			writer.writeEmptyElement(ASTNode.URI_MATHML_DEFINITION, functionName);
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
			writer.writeStartElement(ASTNode.URI_MATHML_DEFINITION, "apply");
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
		
		ASTNode formula_base = new ASTNode(Double.NaN);
		
		System.out.println(formula_base.toMathML());
		try {
			System.out.println(formula_base.toFormula());
		} catch (SBMLException e) {
			e.printStackTrace();
		}
		
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
