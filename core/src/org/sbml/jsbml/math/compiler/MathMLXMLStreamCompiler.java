/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * 
 * Copyright (C) 2009-2018 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 5. The Babraham Institute, Cambridge, UK
 * 6. The University of Toronto, Toronto, ON, Canada
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.math.compiler;

import java.io.StringWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.log4j.Logger;
import org.codehaus.staxmate.SMOutputFactory;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.math.ASTCSymbolBaseNode;
import org.sbml.jsbml.math.ASTCSymbolDelayNode;
import org.sbml.jsbml.math.ASTCSymbolNode;
import org.sbml.jsbml.math.ASTCiFunctionNode;
import org.sbml.jsbml.math.ASTCnExponentialNode;
import org.sbml.jsbml.math.ASTCnIntegerNode;
import org.sbml.jsbml.math.ASTCnRationalNode;
import org.sbml.jsbml.math.ASTCnRealNode;
import org.sbml.jsbml.math.ASTConstantNumber;
import org.sbml.jsbml.math.ASTFunction;
import org.sbml.jsbml.math.ASTLambdaFunctionNode;
import org.sbml.jsbml.math.ASTLogarithmNode;
import org.sbml.jsbml.math.ASTLogicalOperatorNode;
import org.sbml.jsbml.math.ASTNode2;
import org.sbml.jsbml.math.ASTPiecewiseFunctionNode;
import org.sbml.jsbml.math.ASTQualifierNode;
import org.sbml.jsbml.math.ASTRelationalOperatorNode;
import org.sbml.jsbml.math.ASTRootNode;
import org.sbml.jsbml.util.StringTools;


/**
 * Writes an {@link ASTNode2} to mathML.
 * 
 * @author Nicolas Rodriguez
 * @author Victor Kofia
 * @since 1.0
 */
public class MathMLXMLStreamCompiler {

  /**
   * @param args
   */
  public static void main(String[] args) {
    ASTCnRealNode formula_base = new ASTCnRealNode(Double.NaN);
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
  /**
   * Writes an {@link ASTNode2} the mathML.
   * 
   * @param astNode the {@link ASTNode2} to serialize as mathML
   * @return a String representing this ASTNode2 as mathML.
   */
  public static String toMathML(ASTNode2 astNode) {
    String mathML = "";
    StringWriter stream = new StringWriter();
    SMOutputFactory smFactory = new SMOutputFactory(XMLOutputFactory.newInstance());
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
          sbmlNamespace = doc.getDeclaredNamespaces().get("xmlns");
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

  /**
   * 
   */
  private String indent;

  /**
   * 
   */
  private final XMLStreamWriter writer;

  /**
   * 
   */
  private final Logger logger = Logger.getLogger(MathMLXMLStreamCompiler.class);

  /**
   * 
   */
  private final FindUnitsCompiler findUnitsCompiler = new FindUnitsCompiler();

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
   * Compiles this {@link ASTNode2} and produce an XMLStreamWriter representing this node in mathML.
   * @param node
   * 
   *
   * @throws SBMLException if any problems occur while checking the ASTNode2 tree.
   */
  public void compile(ASTNode2 node) {
    logger.debug("compile: node type = " + node.getType()) ;
    switch (node.getType()) {
    /*
     * Numbers
     */
    case REAL:
      if (((ASTCnRealNode)node).isInfinity()) {
        compilePositiveInfinity((ASTCnRealNode) node);
      } else if (((ASTCnRealNode)node).isNegInfinity()) {
        compileNegativeInfinity((ASTCnRealNode) node);
      } else {
        compileReal((ASTCnRealNode) node);
      }
      break;
    case INTEGER:
      compileInteger((ASTCnIntegerNode) node);
      break;
      /*
       * Operators
       */
    case DIVIDE:
      if (node.getChildCount() != 2) {
        logger.error(String.format(
          "compile: Type.DIVIDE: getChildCount() = %d but required is 2!",
          node.getChildCount()));
      }
    case POWER:
    case PLUS:
    case MINUS:
    case TIMES:
      compileElement((ASTFunction) node);
      break;
    case RATIONAL:
      compileRational((ASTCnRationalNode) node);
      break;
      /*
       * Names of identifiers: parameters, functions, species etc.
       */
    case NAME:
      if (node.getChildCount() > 0) { // In case the id is not a valid functionDefinition id, the type is set to NAME
        // TODO: check for SBML level 3 where the order of the listOf is not mandatory any more.
        // TODO: check how and when we put the type FUNCTION to a node.
        compileUserFunction((ASTCiFunctionNode) node);
      } else {
        compileCi((ASTCSymbolBaseNode) node);
      }
      break;
      /*
       * Type: pi, e, true, false, Avogadro
       */
    case CONSTANT_PI:
    case CONSTANT_E:
    case CONSTANT_TRUE:
    case CONSTANT_FALSE:
      compileConstantElement(node);
      break;
      /*
       * csymbol element
       *
       */
    case NAME_TIME:
    case FUNCTION_DELAY:
    case NAME_AVOGADRO:
      compileCSymbol((ASTCSymbolNode) node);
      break;
    case FUNCTION_RATE_OF:
      // TODO
      break;
    case REAL_E:
      compileReal_e((ASTCnExponentialNode) node);
      break;
      /*
       * Basic Functions
       */
    case FUNCTION_LOG:
      compileLog((ASTLogarithmNode) node);
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
    case FUNCTION_MAX:
    case FUNCTION_MIN:
    case FUNCTION_POWER:
    case FUNCTION_QUOTIENT:
    case FUNCTION_REM:
    case FUNCTION_SEC:
    case FUNCTION_SECH:
    case FUNCTION_SELECTOR:
    case FUNCTION_SIN:
    case FUNCTION_SINH:
    case FUNCTION_TAN:
    case FUNCTION_TANH:
      compileFunctionElement((ASTFunction) node);
      break;
    case FUNCTION_ROOT:
      compileRootElement((ASTRootNode) node);
      break;
    case FUNCTION:
      compileUserFunction((ASTCiFunctionNode) node);
      break;
    case FUNCTION_PIECEWISE:
      compilePiecewise((ASTPiecewiseFunctionNode) node);
      break;
    case LAMBDA:
      compileLambda((ASTLambdaFunctionNode) node);
      break;
      /*
       * Logical and relational functions
       */
    case LOGICAL_AND:
    case LOGICAL_XOR:
    case LOGICAL_OR:
    case LOGICAL_NOT:
    case LOGICAL_IMPLIES:
      compileLogicalOperator((ASTLogicalOperatorNode) node);
      break;
    case RELATIONAL_EQ:
    case RELATIONAL_GEQ:
    case RELATIONAL_GT:
    case RELATIONAL_NEQ:
    case RELATIONAL_LEQ:
    case RELATIONAL_LT:
      compileRelationalOperator((ASTRelationalOperatorNode) node);
      break;
    case VECTOR:
      compileVector((ASTFunction) node);
      break;
    default: // UNKNOWN:
      logger.warn("!!!!! I don't know what to do with the node of type " + node.getType() + " " + node);
      break;
    }
  }

  /**
   * @param bvar
   */
  private void compileBvar(ASTQualifierNode bvar) {
    try {
      writer.writeCharacters(indent);
      writer.writeStartElement(ASTNode.URI_MATHML_DEFINITION, "bvar");
      writer.writeCharacters("\n");
      indent += "  ";
      compileCi((ASTCSymbolBaseNode) bvar.getChildAt(0));
      writeEndElement();
    } catch (XMLStreamException e) {
      e.printStackTrace();
    }
  }

  /**
   * @param ci
   */
  private void compileCi(ASTCSymbolBaseNode ci) {
    try {
      writer.writeCharacters(indent);
      writer.writeStartElement(ASTNode.URI_MATHML_DEFINITION, "ci");
      writer.writeCharacters(" ");
      writer.writeCharacters(ci.getName());
      writer.writeCharacters(" ");
      writer.writeEndElement();
      writer.writeCharacters("\n");
    } catch (XMLStreamException e) {
      e.printStackTrace();
    }
  }

  /**
   * @param astNode
   */
  private void compileConstantElement(ASTNode2 astNode) {
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
   * @param csymbol
   */
  private void compileCSymbol(ASTCSymbolNode csymbol) {
    try {
      String name = null;
      switch(csymbol.getType()) {
      case FUNCTION_DELAY:
        name = csymbol.isSetName() ? csymbol.getName() : "delay";
        writer.writeCharacters(indent);
        writer.writeStartElement(ASTNode.URI_MATHML_DEFINITION, "apply");
        writer.writeCharacters("\n");
        indent += "  ";
        break;
      case NAME_AVOGADRO:
        name = csymbol.isSetName() ? csymbol.getName() : "avogadro";
        break;
      case NAME_TIME:
        name = csymbol.isSetName() ? csymbol.getName() : "time";
        break;
      default:
        throw new IllegalArgumentException("Argument should extend type ASTCSymbolNode");
      }
      writer.writeCharacters(indent);
      writer.writeStartElement(ASTNode.URI_MATHML_DEFINITION, "csymbol");
      writer.writeAttribute("encoding", csymbol.getEncoding());
      writer.writeAttribute("definitionURL", csymbol.getDefinitionURL());
      writer.writeCharacters(" ");
      writer.writeCharacters(name);
      writer.writeCharacters(" ");
      writer.writeEndElement();
      writer.writeCharacters("\n");
      // delay works like a function, so we need to write all the children
      if (csymbol.getType() == Type.FUNCTION_DELAY) {
        for (ASTNode2 arg : ((ASTCSymbolDelayNode)csymbol).getListOfNodes()) {
          compile(arg);
        }
        // end apply element
        writeEndElement();
      }
    } catch (XMLStreamException e) {
      e.printStackTrace();
    }
  }

  /**
   * @param astNode
   */
  private void compileElement(ASTFunction astNode) {
    compileElement(astNode.getType().toString().toLowerCase(), astNode);
  }



  /**
   *  @param string element name
   * @param function the node containing all the element information
   */
  private void compileElement(String string, ASTFunction function) {
    // TODO: Not good, we need to write the possible attribute or annotations of the element node.
    function(string, function.getListOfNodes());
  }


  /**
   * @param astConstantNumber
   */
  private void compileExponentiale(ASTConstantNumber astConstantNumber) {
    try {
      writer.writeCharacters(indent);
      writer.writeEmptyElement(ASTNode.URI_MATHML_DEFINITION, "exponentiale");
      writer.writeCharacters("\n");
    } catch (XMLStreamException e) {
      e.printStackTrace();
    }
  }



  /**
   * @param astNode
   */
  private void compileFunctionElement(ASTFunction astNode) {
    String functionName = astNode.getType().toString().substring(9).toLowerCase();
    compileElement(functionName, astNode);
  }


  /**
   * @param integer
   */
  private void compileInteger(ASTCnIntegerNode integer) {
    try {
      writer.writeCharacters(indent);
      writer.writeStartElement(ASTNode.URI_MATHML_DEFINITION, "cn");
      writer.writeAttribute("type", "integer"); // writer.writeAttribute(ASTNode.URI_MATHML_DEFINITION, "type", "integer");
      if (integer.isSetUnits()) {
        writer.writeAttribute("sbml:units", integer.getUnits());
      }
      writer.writeCharacters(" ");
      writer.writeCharacters(Integer.toString(integer.getInteger()));
      writer.writeCharacters(" ");
      writer.writeEndElement();
      writer.writeCharacters("\n");
    } catch (XMLStreamException e) {
      e.printStackTrace();
    }
  }


  /**
   * @param lambda
   */
  private void compileLambda(ASTLambdaFunctionNode lambda) {
    try {
      writer.writeCharacters(indent);
      writer.writeStartElement(ASTNode.URI_MATHML_DEFINITION, "lambda");
      writer.writeCharacters("\n");
      indent += "  ";
      int nbChildren = lambda.getChildCount();
      if (nbChildren > 1) {
        for (int i = 0; i < nbChildren - 1; i++ ) {
          ASTNode2 child = lambda.getChildAt(i);
          if (child.getType() == Type.QUALIFIER_BVAR) {
            compileBvar((ASTQualifierNode) child);
          }
        }
      }
      compile(lambda.getChildAt(lambda.getChildCount() - 1));
      writeEndElement();
    } catch (XMLStreamException e) {
      e.printStackTrace();
    }
  }


  /**
   * @param log
   */
  private void compileLog(ASTLogarithmNode log) {
    if (log.getChildCount() == 1) {
      compileFunctionElement(log);
    } else if (log.getChildCount() == 2) {
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
        compile(log.getBase());
        // end logbase element
        writeEndElement();
        compile(log.getValue());
        // end apply element
        writeEndElement();
      } catch (XMLStreamException e) {
        e.printStackTrace();
      }
    } else {
      logger.warn("compileLog: cannot have more than 2 children on a log node !!");
    }
  }

  /**
   * @param operator
   */
  private void compileLogicalOperator(ASTLogicalOperatorNode operator) {
    String functionName = operator.getType().toString().substring(8).toLowerCase();
    compileElement(functionName, operator);
  }

  /**
   * @param real
   */
  private void compileNegativeInfinity(ASTCnRealNode real) {
    try {
      writer.writeCharacters(indent);
      writer.writeStartElement(ASTNode.URI_MATHML_DEFINITION, "apply");
      writer.writeCharacters("\n");
      writer.writeCharacters(indent + "  ");
      writer.writeEmptyElement(ASTNode.URI_MATHML_DEFINITION, "minus");
      writer.writeCharacters("\n");
      writer.writeCharacters(indent + "  ");
      writer.writeEmptyElement(ASTNode.URI_MATHML_DEFINITION, "infinity");
      writer.writeCharacters("\n");
      writer.writeCharacters(indent);
      writer.writeEndElement();
      writer.writeCharacters("\n");
    } catch (XMLStreamException e) {
      e.printStackTrace();
    }
  }

  /**
   * @param astNode
   */
  private void compileNotANumber(ASTCnRealNode astNode) {
    try {
      writer.writeCharacters(indent);
      writer.writeEmptyElement(ASTNode.URI_MATHML_DEFINITION, "notanumber");
      writer.writeCharacters("\n");
    } catch (XMLStreamException e) {
      e.printStackTrace();
    }
  }

  /**
   * @param astNode
   */
  private void compilePi(ASTConstantNumber astNode) {
    try {
      writer.writeCharacters(indent);
      writer.writeEmptyElement(ASTNode.URI_MATHML_DEFINITION, "pi");
      writer.writeCharacters("\n");
    } catch (XMLStreamException e) {
      e.printStackTrace();
    }
  }

  /**
   * @param piecewise
   */
  private void compilePiecewise(ASTPiecewiseFunctionNode piecewise) {
    try {
      writer.writeCharacters(indent);
      writer.writeStartElement(ASTNode.URI_MATHML_DEFINITION, "piecewise");
      writer.writeCharacters("\n");
      indent += "  ";
      ASTNode2 node = null;
      int i = 0;
      while (i < piecewise.getChildCount()) {
        node = piecewise.getChildAt(i);
        if (node.getType() != Type.CONSTRUCTOR_PIECE) {
          break;
        }
        writer.writeCharacters(indent);
        writer.writeStartElement(ASTNode.URI_MATHML_DEFINITION, "piece");
        writer.writeCharacters("\n");
        indent += "  ";
        compile((ASTNode2) node.getChildAt(0));
        compileRelationalOperator((ASTRelationalOperatorNode) node.getChildAt(1));
        // end piece element
        writeEndElement();
        i++;
      }
      if (i != piecewise.getChildCount()) {
        writer.writeCharacters(indent);
        writer.writeStartElement(ASTNode.URI_MATHML_DEFINITION, "otherwise");
        writer.writeCharacters("\n");
        indent += "  ";
        compile((ASTNode2) piecewise.getChildAt(i).getChildAt(0));
        writeEndElement();
      }
      // end piecewise element
      writeEndElement();
    } catch (XMLStreamException e) {
      e.printStackTrace();
    }
  }

  /**
   * @param astNode
   */
  private void compilePositiveInfinity(ASTCnRealNode astNode) {
    try {
      writer.writeCharacters(indent);
      writer.writeEmptyElement(ASTNode.URI_MATHML_DEFINITION, "infinity");
      writer.writeCharacters("\n");
    } catch (XMLStreamException e) {
      e.printStackTrace();
    }
  }

  /**
   * @param rational
   */
  private void compileRational(ASTCnRationalNode rational) {
    try {
      writer.writeCharacters(indent);
      writer.writeStartElement(ASTNode.URI_MATHML_DEFINITION, "cn");
      writer.writeAttribute("type", "rational");
      writer.writeCharacters(" ");
      if (rational.isSetUnits()) {
        writer.writeAttribute("sbml:units", rational.getUnits());
      }
      writer.writeCharacters(Integer.toString(rational.getNumerator()));
      writer.writeCharacters(" ");
      writer.writeEmptyElement(ASTNode.URI_MATHML_DEFINITION, "sep");
      writer.writeCharacters(" ");
      writer.writeCharacters(Integer.toString(rational.getDenominator()));
      writer.writeCharacters(" ");
      writer.writeEndElement();
      writer.writeCharacters("\n");
    } catch (XMLStreamException e) {
      e.printStackTrace();
    }
  }


  /**
   * @param real
   */
  private void compileReal(ASTCnRealNode real) {
    try {
      if (Double.isNaN(real.getReal())) {
        compileNotANumber(real);
        return;
      } else if (Double.isInfinite(real.getReal())) {
        compilePositiveInfinity(real);
        return;
      } else if (Math.PI == real.getReal()) {
        compilePi(new ASTConstantNumber(Type.CONSTANT_PI));
        return;
      } else if (Math.E == real.getReal()) {
        compileExponentiale(new ASTConstantNumber(Type.CONSTANT_E));
        return;
      }
      writer.writeCharacters(indent);
      writer.writeStartElement(ASTNode.URI_MATHML_DEFINITION, "cn");
      writer.writeAttribute("type", "real");
      if (real.isSetUnits()) {
        writer.writeAttribute(" sbml:units", real.getUnits());
      }
      writer.writeCharacters(" ");
      // We need the DecimalFormat to have number like 0.000166, that get transformed into 1.66E-4 which is
      // invalid in mathML, written properly
      String doubleStr = realFormat.format(real.getReal());
      writer.writeCharacters(doubleStr);
      writer.writeCharacters(" ");
      writer.writeEndElement();
      writer.writeCharacters("\n");
    } catch (XMLStreamException e) {
      e.printStackTrace();
    }
  }

  /**
   * @param exponential
   */
  private void compileReal_e(ASTCnExponentialNode exponential) {
    try {
      writer.writeCharacters(indent);
      writer.writeStartElement(ASTNode.URI_MATHML_DEFINITION, "cn");
      writer.writeAttribute("type", "e-notation");
      writer.writeCharacters(" ");
      if (exponential.isSetUnits()) {
        writer.writeAttribute("sbml:units", exponential.getUnits());
      }
      writer.writeCharacters(realFormat.format(exponential.getMantissa()));
      writer.writeCharacters(" ");
      writer.writeEmptyElement(ASTNode.URI_MATHML_DEFINITION, "sep");
      writer.writeCharacters(" ");
      writer.writeCharacters(realFormat.format(exponential.getExponent()));
      writer.writeCharacters(" ");
      writer.writeEndElement();
      writer.writeCharacters("\n");
    } catch (XMLStreamException e) {
      e.printStackTrace();
    }
  }

  /**
   * @param operator
   */
  private void compileRelationalOperator(ASTRelationalOperatorNode operator) {
    String functionName = operator.getType().toString().substring(11).toLowerCase();
    compileElement(functionName, operator);
  }

  /**
   * @param root
   */
  private void compileRootElement(ASTRootNode root) {
    if (root.getChildCount() == 1) {
      compileFunctionElement(root);
    } else if (root.getChildCount() == 2) {
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
        compile(root.getRootExponent());
        // end degree element
        writeEndElement();
        compile(root.getRadicand());
        // end apply element
        writeEndElement();
      } catch (XMLStreamException e) {
        e.printStackTrace();
      }
    } else {
      logger.warn("Cannot have more than 2 children on a root node !!");
    }
  }

  /**
   * @param astNode
   */
  private void compileUserFunction(ASTCiFunctionNode astNode) {
    try {
      writer.writeCharacters(indent);
      writer.writeStartElement(ASTNode.URI_MATHML_DEFINITION, "apply");
      writer.writeCharacters("\n");
      indent += "  ";
      compileCi(astNode);
      for (ASTNode2 arg : astNode.getChildren()) {
        compile(arg);
      }
      writeEndElement();
    } catch (XMLStreamException e) {
      e.printStackTrace();
    }
  }

  /**
   * @param astNode
   */
  private void compileVector(ASTFunction astNode) {
    String functionName = astNode.getType().toString().toLowerCase();
    List<ASTNode2> args = astNode.getListOfNodes();
    try {
      writer.writeCharacters(indent);
      writer.writeStartElement(ASTNode.URI_MATHML_DEFINITION, functionName);
      writer.writeCharacters("\n");
      indent += "  ";
      for (ASTNode2 arg : args) {
        compile(arg);
      }
      writeEndElement();
    } catch (XMLStreamException e) {
      e.printStackTrace();
    }
  }

  /**
   * @param functionName
   * @param args
   */
  private void function(String functionName, ASTNode2... args) {
    try {
      writer.writeCharacters(indent);
      writer.writeStartElement(ASTNode.URI_MATHML_DEFINITION, "apply");
      writer.writeCharacters("\n");
      indent += "  ";
      writer.writeCharacters(indent);
      writer.writeEmptyElement(ASTNode.URI_MATHML_DEFINITION, functionName);
      writer.writeCharacters("\n");
      for (ASTNode2 arg : args) {
        compile(arg);
      }
      writeEndElement();
    } catch (XMLStreamException e) {
      e.printStackTrace();
    }
  }

  /**
   * @param functionName
   * @param args
   */
  private void function(String functionName, List<ASTNode2> args) {
    function(functionName, args.toArray(new ASTNode2[args.size()]));
  }

  /**
   * @param astNode
   * @return
   */
  public boolean isSBMLNamespaceNeeded(ASTNode2 astNode) {
    SBase sbase = astNode.isSetParentSBMLObject() ? astNode.getParentSBMLObject() : null;
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
   * 
   */
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

}
