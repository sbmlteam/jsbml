/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2014 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 5. The Babraham Institute, Cambridge, UK
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml;

import java.io.StringReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.tree.TreeNode;
import javax.xml.stream.XMLStreamException;

import org.apache.log4j.Logger;
import org.sbml.jsbml.Unit.Kind;
import org.sbml.jsbml.math.ASTArithmeticOperatorNode;
import org.sbml.jsbml.math.ASTBoolean;
import org.sbml.jsbml.math.ASTCSymbolBaseNode;
import org.sbml.jsbml.math.ASTCSymbolNode;
import org.sbml.jsbml.math.ASTCiFunctionNode;
import org.sbml.jsbml.math.ASTCiNumberNode;
import org.sbml.jsbml.math.ASTCnExponentialNode;
import org.sbml.jsbml.math.ASTCnIntegerNode;
import org.sbml.jsbml.math.ASTCnNumberNode;
import org.sbml.jsbml.math.ASTCnRationalNode;
import org.sbml.jsbml.math.ASTCnRealNode;
import org.sbml.jsbml.math.ASTConstantNumber;
import org.sbml.jsbml.math.ASTDivideNode;
import org.sbml.jsbml.math.ASTFactory;
import org.sbml.jsbml.math.ASTFunction;
import org.sbml.jsbml.math.ASTHyperbolicNode;
import org.sbml.jsbml.math.ASTLambdaFunctionNode;
import org.sbml.jsbml.math.ASTLogarithmNode;
import org.sbml.jsbml.math.ASTLogicalOperatorNode;
import org.sbml.jsbml.math.ASTMinusNode;
import org.sbml.jsbml.math.ASTNode2;
import org.sbml.jsbml.math.ASTPiecewiseFunctionNode;
import org.sbml.jsbml.math.ASTPlusNode;
import org.sbml.jsbml.math.ASTPowerNode;
import org.sbml.jsbml.math.ASTQualifierNode;
import org.sbml.jsbml.math.ASTRelationalOperatorNode;
import org.sbml.jsbml.math.ASTRootNode;
import org.sbml.jsbml.math.ASTTimesNode;
import org.sbml.jsbml.math.ASTTrigonometricNode;
import org.sbml.jsbml.math.ASTUnaryFunctionNode;
import org.sbml.jsbml.math.compiler.ASTNode2Compiler;
import org.sbml.jsbml.math.compiler.ASTNode2Value;
import org.sbml.jsbml.text.parser.FormulaParser;
import org.sbml.jsbml.text.parser.IFormulaParser;
import org.sbml.jsbml.text.parser.ParseException;
import org.sbml.jsbml.util.compilers.ASTNodeCompiler;
import org.sbml.jsbml.util.compilers.ASTNodeValue;
import org.sbml.jsbml.util.compilers.FormulaCompiler;
import org.sbml.jsbml.util.compilers.LaTeXCompiler;
import org.sbml.jsbml.util.compilers.MathMLXMLStreamCompiler;
import org.sbml.jsbml.util.filters.Filter;
import org.sbml.jsbml.xml.stax.SBMLReader;

/**
 * A node in the Abstract Syntax Tree (AST) representation of a mathematical
 * expression.
 * 
 * @author Andreas Dr&auml;ger
 * @author Nicolas Rodriguez
 * @author Alexander D&ouml;rr
 * @since 0.8
 * @version $Rev$
 */
public class ASTNode extends AbstractTreeNode {

  /**
   * An enumeration of all possible types that can be represented by an
   * abstract syntax tree node.
   * 
   * @author Andreas Dr&auml;ger
   * 
   */
  public static enum Type {
    /**
     * If the {@link ASTNode} represents Euler's constant, it should have
     * this {@link Type}.
     */
    CONSTANT_E,
    /**
     * If an {@link ASTNode} represents the {@link Boolean} attribute
     * {@link Boolean#FALSE} it should have this {@link Type}.
     */
    CONSTANT_FALSE,
    /**
     * If the {@link ASTNode} represents the constant &#960;, it should have
     * this {@link Type}.
     */
    CONSTANT_PI,
    /**
     * 
     */
    CONSTANT_TRUE,
    /**
     * 
     */
    CONSTRUCTOR_PIECE,
    /**
     * 
     */
    CONSTRUCTOR_OTHERWISE,
    /**
     * 
     */
    DIVIDE,
    /**
     * The type of an {@link ASTNode} containing a reference to a user-defined
     * {@link FunctionDefinition}.
     */
    FUNCTION,
    /**
     * 
     */
    FUNCTION_ABS,
    /**
     * 
     */
    FUNCTION_ARCCOS,
    /**
     * 
     */
    FUNCTION_ARCCOSH,
    /**
     * 
     */
    FUNCTION_ARCCOT,
    /**
     * 
     */
    FUNCTION_ARCCOTH,
    /**
     * 
     */
    FUNCTION_ARCCSC,
    /**
     * 
     */
    FUNCTION_ARCCSCH,
    /**
     * 
     */
    FUNCTION_ARCSEC,
    /**
     * 
     */
    FUNCTION_ARCSECH,
    /**
     * 
     */
    FUNCTION_ARCSIN,
    /**
     * 
     */
    FUNCTION_ARCSINH,
    /**
     * 
     */
    FUNCTION_ARCTAN,
    /**
     * 
     */
    FUNCTION_ARCTANH,
    /**
     * 
     */
    FUNCTION_CEILING,
    /**
     * 
     */
    FUNCTION_COS,
    /**
     * 
     */
    FUNCTION_COSH,
    /**
     * 
     */
    FUNCTION_COT,
    /**
     * 
     */
    FUNCTION_COTH,
    /**
     * 
     */
    FUNCTION_CSC,
    /**
     * 
     */
    FUNCTION_CSCH,
    /**
     * 
     */
    FUNCTION_DELAY,
    /**
     * 
     */
    FUNCTION_EXP,
    /**
     * 
     */
    FUNCTION_FACTORIAL,
    /**
     * 
     */
    FUNCTION_FLOOR,
    /**
     * 
     */
    FUNCTION_LN,
    /**
     * 
     */
    FUNCTION_LOG,
    /**
     * 
     */
    FUNCTION_PIECEWISE,
    /**
     * An {@link ASTNode} of this {@link Type} represents a function call of
     * the 'pow' function. This function takes two arguments, the base and
     * the exponent. Alternatively, also {@link Type#POWER} can be used,
     * which represents the simple text symbol '^' to achieve the same
     * effect.
     */
    FUNCTION_POWER,
    /**
     * 
     */
    FUNCTION_ROOT,
    /**
     * 
     */
    FUNCTION_SEC,
    /**
     * 
     */
    FUNCTION_SECH,
    /**
     * 
     */
    FUNCTION_SELECTOR,
    /**
     * 
     */
    FUNCTION_SIN,
    /**
     * 
     */
    FUNCTION_SINH,
    /**
     * 
     */
    FUNCTION_TAN,
    /**
     * 
     */
    FUNCTION_TANH,
    /**
     * 
     */
    INTEGER,
    /**
     * This type describes function definitions: The first n children of a
     * node of this type are the arguments, and the last child is the
     * function body.
     */
    LAMBDA,
    /**
     * 
     */
    LOGICAL_AND,
    /**
     * 
     */
    LOGICAL_NOT,
    /**
     * 
     */
    LOGICAL_OR,
    /**
     * 
     */
    LOGICAL_XOR,
    /**
     * 
     */
    MINUS,
    /**
     * {@link ASTNode}s of this {@link Type} refer to a
     * {@link CallableSBase}.
     */
    NAME,
    /**
     * A type to express Avogadro's number.
     */
    NAME_AVOGADRO,
    /**
     * 
     */
    NAME_TIME,
    /**
     * 
     */
    PLUS,
    /**
     * This {@link Type} represents an operation with two children: a base
     * and an exponent. In textual form, this type is represented by the
     * symbol '^'.
     */
    POWER,
    /**
     * 
     */
    PRODUCT,
    /**
     * 
     */
    QUALIFIER_BVAR,
    /**
     * 
     */
    QUALIFIER_DEGREE,
    /**
     * 
     */
    QUALIFIER_LOGBASE,
    /**
     * An {@link ASTNode} of this {@link Type} contains two integer values:
     * a numerator and a denominator.
     */
    RATIONAL,
    /**
     * {@link Type} of an {@link ASTNode} that represents a single real
     * value, i.e., a double number.
     */
    REAL,
    /**
     * {@link Type} of an {@link ASTNode} with a real value that is split in
     * a double mantissa and an integer exponent.
     */
    REAL_E,
    /**
     * An {@link ASTNode} of this {@link Type} represents the relation
     * symbol '=' to compare the values of all of its successors in the tree
     * for equality.
     */
    RELATIONAL_EQ,
    /**
     * Greater or equal
     */
    RELATIONAL_GEQ,
    /**
     * Greater than
     */
    RELATIONAL_GT,
    /**
     * Less or equal
     */
    RELATIONAL_LEQ,
    /**
     * Less than
     */
    RELATIONAL_LT,
    /**
     * Not equal
     */
    RELATIONAL_NEQ,
    /**
     * 
     */
    SUM,
    /**
     * 
     */
    TIMES,
    /**
     * 
     */
    UNKNOWN,
    /**
     * 
     */
    VECTOR;

    /**
     * Returns the {@link Type} corresponding to the given {@link String}.
     * 
     * @param type
     *            e.g., sin, asin, exp, and so on. See the specification of
     *            the MathML subset used in SBML.
     * @return The type corresponding to the given {@link String} or
     *         {@link #UNKNOWN} if no matching can be found.
     */
    public static Type getTypeFor(String type) {
      // Arithmetic operators
      if (type.equals("plus")) {
        return PLUS;
      } else if (type.equals("minus")) {
        return MINUS;
      } else if (type.equals("times")) {
        return TIMES;
      } else if (type.equals("divide")) {
        return DIVIDE;
      } else if (type.equals("power")) {
        return FUNCTION_POWER;
      } else if (type.equals("root")) {
        return FUNCTION_ROOT;
      } else if (type.equals("abs")) {
        return FUNCTION_ABS;
      } else if (type.equals("exp")) {
        return FUNCTION_EXP;
      } else if (type.equals("ln")) {
        return FUNCTION_LN;
      } else if (type.equals("log")) {
        return FUNCTION_LOG;
      } else if (type.equals("floor")) {
        return FUNCTION_FLOOR;
      } else if (type.equals("ceiling")) {
        return FUNCTION_CEILING;
      } else if (type.equals("factorial")) {
        return FUNCTION_FACTORIAL;
      }

      // Logical operators
      else if (type.equals("and") || type.equals("&&")) {
        return LOGICAL_AND;
      } else if (type.equals("or") || type.equals("||")) {
        return LOGICAL_OR;
      } else if (type.equals("xor")) {
        return LOGICAL_XOR;
      } else if (type.equals("not") || type.equals("!")) {
        return LOGICAL_NOT;
      }

      // Trigonometric operators
      else if (type.equals("cos")) {
        return FUNCTION_COS;
      } else if (type.equals("sin")) {
        return FUNCTION_SIN;
      } else if (type.equals("tan")) {
        return FUNCTION_TAN;
      } else if (type.equals("sec")) {
        return FUNCTION_SEC;
      } else if (type.equals("csc")) {
        return FUNCTION_CSC;
      } else if (type.equals("cot")) {
        return FUNCTION_COT;
      } else if (type.equals("sinh")) {
        return FUNCTION_SINH;
      } else if (type.equals("cosh")) {
        return FUNCTION_COSH;
      } else if (type.equals("tanh")) {
        return FUNCTION_TANH;
      } else if (type.equals("sech")) {
        return FUNCTION_SECH;
      } else if (type.equals("csch")) {
        return FUNCTION_CSCH;
      } else if (type.equals("coth")) {
        return FUNCTION_COTH;
      } else if (type.equals("arcsin")) {
        return FUNCTION_ARCSIN;
      } else if (type.equals("arccos")) {
        return FUNCTION_ARCCOS;
      } else if (type.equals("arctan")) {
        return FUNCTION_ARCTAN;
      } else if (type.equals("arcsec")) {
        return FUNCTION_ARCSEC;
      } else if (type.equals("arccsc")) {
        return FUNCTION_ARCCSC;
      } else if (type.equals("arccot")) {
        return FUNCTION_ARCCOT;
      } else if (type.equals("arcsinh")) {
        return FUNCTION_ARCSINH;
      } else if (type.equals("arccosh")) {
        return FUNCTION_ARCCOSH;
      } else if (type.equals("arctanh")) {
        return FUNCTION_ARCTANH;
      } else if (type.equals("arcsech")) {
        return FUNCTION_ARCSECH;
      } else if (type.equals("arccsch")) {
        return FUNCTION_ARCCSCH;
      } else if (type.equals("arccoth")) {
        return FUNCTION_ARCCOTH;
      }

      // Relational operators
      else if (type.equals("eq") || type.equals("=")) {
        return RELATIONAL_EQ;
      } else if (type.equals("neq") || type.equals("!")) {
        return RELATIONAL_NEQ;
      } else if (type.equals("gt") || type.equals(">")) {
        return RELATIONAL_GT;
      } else if (type.equals("lt") || type.equals("<")) {
        return RELATIONAL_LT;
      } else if (type.equals("geq") || type.equals(">=")) {
        return RELATIONAL_GEQ;
      } else if (type.equals("leq") || type.equals("<=")) {
        return RELATIONAL_LEQ;
      }

      // token: cn, ci, csymbol, sep
      // for ci, we have to check if it is a functionDefinition
      // for cn, we pass the type attribute to this function to determine the
      // proper astNode type
      // for csymbol, we pass the definitionURL
      else if (type.equalsIgnoreCase("real") || type.equals("cn")) {
        // we put the type by default to real in case the type attribute is
        // not define on the cn element.
        return REAL;
      } else if (type.equalsIgnoreCase("e-notation")) {
        return REAL_E;
      } else if (type.equalsIgnoreCase("integer")) {
        return INTEGER;
      } else if (type.equalsIgnoreCase("rational")) {
        return RATIONAL;
      } else if (type.equals("ci")) {
        return NAME;
      } else if (type.equals("csymbol")) {
        return UNKNOWN;
      } else if (type.equals("sep")) {
        return UNKNOWN;
      } else if (type.equals(ASTNode.URI_TIME_DEFINITION)) {
        return NAME_TIME;
      } else if (type.equals(ASTNode.URI_DELAY_DEFINITION)) {
        return FUNCTION_DELAY;
      } else if (type.equals(ASTNode.URI_AVOGADRO_DEFINITION)) {
        return NAME_AVOGADRO;
      }

      // general: apply, piecewise, piece, otherwise, lambda, bvar
      else if (type.equals("lambda")) {
        return LAMBDA;
      } else if (type.equals("bvar")) {
        // nothing to do, node ignore when parsing
      } else if (type.equals("piecewise")) {
        return FUNCTION_PIECEWISE;
      } else if (type.equals("piece")) {
        // nothing to do, node ignore when parsing
      } else if (type.equals("otherwise")) {
        // nothing to do, node ignore when parsing
      }

      // qualifiers: degree, logbase
      else if (type.equals("degree")) {
        // nothing to do, node ignore when parsing
      } else if (type.equals("logbase")) {
        // nothing to do, node ignore when parsing
      }

      // constants: true, false, notanumber, pi, infinity, exponentiale
      else if (type.equals("true")) {
        return CONSTANT_TRUE;
      } else if (type.equals("false")) {
        return CONSTANT_FALSE;
      } else if (type.equals("notanumber")) {
        return REAL;
      } else if (type.equals("pi")) {
        return CONSTANT_PI;
      } else if (type.equals("infinity")) {
        return REAL;
      } else if (type.equals("exponentiale")) {
        return CONSTANT_E;
      }

      // arrays package additional mathML elements
      else if (type.equals("selector")) {
        return FUNCTION_SELECTOR;
      }
      else if (type.equals("vector")) {
        return VECTOR;
      }

      // TODO: possible annotations: semantics, annotation, annotation-xml

      return UNKNOWN;
    }

    /**
     * Checks whether this type is valid for the given SBML
     * Level/Version combination.
     * 
     * @jsbml.warning this method is not implemented
     * 
     * @param level
     * @param version
     * @return whether this type is valid for the given SBML
     * Level/Version combination.
     */
    public boolean isDefinedIn(int level, int version) {
      // TODO
      return false;
    }

  }

  /**
   * Message to indicate that an {@link ASTNode.Type} type has been chosen
   * which cannot be used as an operator.
   */
  public static final transient String INVALID_OPERATOR_MSG = "Invalid operator %s. The operator must be one of the following constants: PLUS, MINUS, TIMES, DIVIDE, or POWER.";

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -1391327698196553142L;

  /**
   * The URI for the definition of the csymbol for avogadro.
   */
  public static final transient String URI_AVOGADRO_DEFINITION = "http://www.sbml.org/sbml/symbols/avogadro";

  /**
   * The URI for the definition of the csymbol for delay.
   */
  public static final transient String URI_DELAY_DEFINITION = "http://www.sbml.org/sbml/symbols/delay";

  /**
   * URI for the definition of MathML.
   */
  public static final transient String URI_MATHML_DEFINITION = "http://www.w3.org/1998/Math/MathML";

  // TODO: check how we set the math in level 1

  /**
   * URI prefix for the definition of MathML, it will be used to write the sbml file
   */
  public static final String URI_MATHML_PREFIX = "";

  /**
   * The URI for the definition of the csymbol for time.
   */
  public static final transient String URI_TIME_DEFINITION = "http://www.sbml.org/sbml/symbols/time";

  /**
   * Creates and returns an {@link ASTNode} that computes the absolute value
   * of the given double value.
   * 
   * @param d a double value
   * @param parent the parent {@link ASTNode}
   * @return an {@link ASTNode} that computes the absolute value
   * of the given double value.
   */
  public static ASTNode abs(double d, MathContainer parent) {
    ASTUnaryFunctionNode node = new ASTUnaryFunctionNode(Type.FUNCTION_ABS);
    node.addChild(new ASTCnRealNode(d));
    node.setParentSBMLObject(parent);
    return new ASTNode(node);
  }

  /**
   * Creates and returns an {@link ASTNode} that computes the absolute value
   * of the given integer value.
   * 
   * @param integer an integer value
   * @param parent  the parent {@link ASTNode}
   * @return an {@link ASTNode} that computes the absolute value
   * of the given integer value.
   */
  public static ASTNode abs(int integer, MathContainer parent) {
    ASTUnaryFunctionNode node = new ASTUnaryFunctionNode(Type.FUNCTION_ABS);
    node.addChild(new ASTCnIntegerNode(integer));
    node.setParentSBMLObject(parent);
    return new ASTNode(node);
  }

  /**
   * Creates a new {@link ASTNode} of type {@code operator} and adds the given nodes as children.
   * 
   * @param operator the type of arithmetic operation
   * @param ast the children of the new ASTNode
   * @return a new {@link ASTNode} of type {@code operator} and adds the given nodes as children.
   */
  private static ASTNode arithmethicOperation(Type operator, ASTNode... ast) {
    ASTNode2[] list = new ASTNode2[ast.length];
    for (int i = 0; i < ast.length; i++) {
      list[i] = ast[i].toASTNode2();
    }  
    return new ASTNode(ASTFactory.arithmeticOperation(operator, list));
  }

  /**
   * Creates a new {@link ASTNode} of type MINUS and adds the given nodes as children
   * 
   * @param ast the children of the new ASTNode
   * @return a new {@link ASTNode} of type MINUS and adds the given nodes as children
   */
  public static ASTNode diff(ASTNode... ast) {
    ASTNode2[] ast2 = new ASTNode2[ast.length];
    for (int i = 0; i < ast.length; i++) {
      ast2[i] = ast[i].toASTNode2();
    }
    return new ASTNode(ASTFactory.diff(ast2));
  }

  /**
   * Creates a new {@link ASTNode} of type RELATIONAL_EQ.
   * 
   * @param left the left child.
   * @param right the right child.
   * @return a new {@link ASTNode} of type RELATIONAL_EQ.
   */
  public static ASTNode eq(ASTNode left, ASTNode right) {
    return new ASTNode(ASTFactory.eq(left.toASTNode2(), right.toASTNode2()));
  }

  /**
   * Returns a new {@link ASTNode} that represents Euler's constant raised by the
   * power of the given exponent.
   * 
   * @param exponent the exponent
   * @return a new {@link ASTNode} that represents Euler's constant raised by the
   * power of the given exponent.
   */
  public static ASTNode exp(ASTNode exponent) {
    return new ASTNode(ASTFactory.exp(exponent.toASTNode2()));
  }

  /**
   * Returns the formula from the given ASTNode as an SBML Level 1 text-string
   *         mathematical formula.
   * 
   * 
   * @param tree
   *            the root of the ASTNode formula expression tree
   * @return the formula from the given AST as an SBML Level 1 text-string
   *         mathematical formula. The caller owns the returned string and is
   *         responsible for freeing it when it is no longer needed. {@code null} is
   *         returned if the given argument is {@code null}.
   * @throws SBMLException
   * @see #toFormula()
   * 
   */
  public static String formulaToString(ASTNode tree) throws SBMLException {
    return tree.toFormula();
  }

  /**
   * Creates a new {@link ASTNode} of type DIVIDE with the given nodes as children.
   * 
   * @param numerator the numerator
   * @param denominator the denominator
   * @return a new {@link ASTNode} of type DIVIDE with the given nodes as children.
   */
  public static ASTNode frac(ASTNode numerator, ASTNode denominator) {
    return new ASTNode(ASTFactory.frac(numerator.toASTNode2(), denominator.toASTNode2()));
  }

  /**
   * Creates a new {@link ASTNode} that of type DIVIDE with the given numerator and
   * denominator.
   * 
   * @param numerator the numerator
   * @param denominator the denominator
   * @return a new {@link ASTNode} that of type DIVIDE with the given numerator and
   * denominator.
   */
  public static ASTNode frac(int numerator, ASTNode denominator) {
    return frac(new ASTNode(numerator, denominator.getParentSBMLObject()),
        denominator);
  }

  /**
   * Creates a new {@link ASTNode} that divides two {@link CallableSBase} objects.
   * 
   * @param container the parent object
   * @param numerator the numerator
   * @param denominator the denominator
   * @return a new {@link ASTNode} that divides two {@link CallableSBase} objects.
   */
  public static ASTNode frac(MathContainer container,
      CallableSBase numerator,
      CallableSBase denominator) {
    return frac(new ASTNode(numerator, container), new ASTNode(denominator,
        container));
  }

  /**
   * Returns a new {@link ASTNode} that of type DIVIDE with the two entities as numerator and denominator.
   * 
   * @param container the parent object
   * @param numeratorId the numerator
   * @param denominatorId the numerator
   * @return a new {@link ASTNode} that of type DIVIDE with the two entities as numerator and denominator.
   */
  public static ASTNode frac(MathContainer container, String numeratorId,
      String denominatorId) {
    return frac(new ASTNode(numeratorId, container), new ASTNode(
        denominatorId, container));
  }

  /**
   * Creates an {@link ASTNode} representing greater or equal for
   * the two given nodes.
   * 
   * @param left the left child.
   * @param right the right child.
   * @return an {@link ASTNode} representing greater or equal.
   */
  public static ASTNode geq(ASTNode left, ASTNode right) {
    return new ASTNode(ASTFactory.geq(left.toASTNode2(), right.toASTNode2()));
  }

  /**
   * Creates an {@link ASTNode} representing greater than for
   * the two given left and right child.
   * 
   * @param left the left child.
   * @param right the right child.
   * @return an {@link ASTNode} representing greater than for
   * the two given left and right child.
   */
  public static ASTNode gt(ASTNode left, ASTNode right) {
    return new ASTNode(ASTFactory.gt(left.toASTNode2(), right.toASTNode2()));
  }

  /**
   * Creates an {@link ASTNode} representing less or equal for
   * the two given left and right child.
   * 
   * @param left the left child.
   * @param right the right child.
   * @return an {@link ASTNode} representing less or equal for
   * the two given left and right child.
   */
  public static ASTNode leq(ASTNode left, ASTNode right) {
    return new ASTNode(ASTFactory.leq(left.toASTNode2(), right.toASTNode2()));
  }

  /**
   * Creates an {@link ASTNode} representing a logarithm to base 10 of the given value.
   * 
   * @param value the value which is the argument of the logarithm.
   * @return an {@link ASTNode} representing a logarithm to base 10 of the given value.
   */
  public static ASTNode log(ASTNode value) {
    return new ASTNode(ASTFactory.log(value.toASTNode2()));
  }

  /**
   * Creates an {@link ASTNode} that represents the logarithm function with
   * the given base and value. The parent SBML object will be taken from the
   * {@link ASTNode} value.
   * 
   * @param base
   *            The basis of this logarithm. Can be null; then a base of 10
   *            will be assumed.
   * @param value
   *            Must not be {@code null}.
   * @return An {@link ASTNode} representing the logarithm of the given value
   *         with respect to the given base or to the base 10 if base is {@code null}.
   */
  public static ASTNode log(ASTNode base, ASTNode value) {
    return new ASTNode(ASTFactory.log(base.toASTNode2(), value.toASTNode2()));
  }

  /**
   * Creates an {@link ASTNode} that performs a less than comparison between
   * two {@link ASTNode}s. The parent SBML object of the resulting node will
   * be taken from the left node.
   * 
   * @param left the left child.
   * @param right the right child.
   * @return an {@link ASTNode} that performs a less than comparison between
   * two {@link ASTNode}s.
   */
  public static ASTNode lt(ASTNode left, ASTNode right) {
    return new ASTNode(ASTFactory.lt(left.toASTNode2(), right.toASTNode2()));
  }

  /**
   * Creates an {@link ASTNode} that performs a less than comparison between a
   * variable and another {@link ASTNode}. The parent SBML object will be
   * taken from the given {@link ASTNode}.
   * 
   * @param variable the left child.
   * @param node the right child.
   * @return an {@link ASTNode} that performs a less than comparison between a
   * variable and another {@link ASTNode}.
   */
  public static ASTNode lt(String variable, ASTNode node) {
    return lt(new ASTNode(variable, node.getParentSBMLObject()), node);
  }

  /**
   * Creates an {@link ASTNode} that performs a not equal comparison between
   * two {@link ASTNode}s.
   * 
   * @param left the left child.
   * @param right the right child.
   * @return an {@link ASTNode} that performs a not equal comparison between
   * two {@link ASTNode}s.
   */
  public static ASTNode neq(ASTNode left, ASTNode right) {
    return new ASTNode(ASTFactory.neq(left.toASTNode2(), right.toASTNode2()));
  }

  /**
   * Parses a text-string mathematical formula and returns a representation as
   * an Abstract Syntax Tree.
   * 
   * @param formula
   *            a text-string mathematical formula.
   * @return an {@link ASTNode} representing the formula.
   * @throws ParseException
   *             If the given formula is not of valid format or cannot be
   *             parsed for other reasons.
   */
  public static ASTNode parseFormula(String formula) throws ParseException {    
    FormulaParser parser = new FormulaParser(new StringReader(formula));
    ASTNode result = null;

    try {
      result = parser.parse();
    } catch (Throwable e) {
      // The JavaCC parser can throw a TokenMgrError at least
      throw new ParseException(e);
    }

    return result; 
  }

  /**
   * Parses a text-string mathematical formula and returns a representation as
   * an Abstract Syntax Tree.
   * 
   * @param formula
   *            a text-string mathematical formula.
   * @param parser
   *            a formula parser.
   * @return an {@link ASTNode} representing the formula.
   * @throws ParseException
   *             If the given formula is not of valid format or cannot be
   *             parsed for other reasons.
   */
  public static ASTNode parseFormula(String formula, IFormulaParser parser) throws ParseException {
    parser.ReInit(new StringReader(formula));
    ASTNode result = null;
    try {
      result = (ASTNode) parser.parse();
    } catch (Throwable e) {
      // The JavaCC parser can throw a TokenMgrError at least
      throw new ParseException(e);
    }

    return result; 
  }

  /**
   * Creates a piecewise {@link ASTNode}.
   * 
   * <p>At least one {@link ASTNode} must be given
   * as a child. The parent SBML object of this first node will be the parent
   * of the resulting {@link ASTNode}.
   * 
   * @param node the parent SBML object of this node will be the parent
   * of the resulting {@link ASTNode}.
   * @param nodes the children of the new piecewise ASTNode
   * @return a piecewise {@link ASTNode}.
   */
  public static ASTNode piecewise(ASTNode node, ASTNode... nodes) {
    // TODO: ASTFactory.piecewise() only accepts ASTQualifierNode[]
    // A few assumptions are being made here: i.e. that every pair
    // of ASTNodes in list nodes consists of a node that has a number
    // type and another node that is a relational operator.
    ASTQualifierNode qualifier = null;
    ASTQualifierNode[] qualifiers = null;
    if (node != null && nodes.length > 0) {
      
      // Construct PIECE node
      qualifier = new ASTQualifierNode(Type.CONSTRUCTOR_PIECE);
      qualifier.addChild(node.toASTNode2());
      qualifier.addChild(nodes[0].toASTNode2());
      
      qualifiers = new ASTQualifierNode[(nodes.length - 2) / 2];
      int j, k = 0;
      for (int i = 2; i < nodes.length; i++) {
        j = i + 1;
        if (j != nodes.length) {
          qualifiers[k] = new ASTQualifierNode(Type.CONSTRUCTOR_PIECE);
          qualifiers[k].addChild(nodes[i].toASTNode2());
          qualifiers[k].addChild(nodes[j].toASTNode2());
        } else {
          qualifiers[k] = new ASTQualifierNode(Type.CONSTRUCTOR_OTHERWISE);
          qualifiers[k].addChild(nodes[i].toASTNode2());
        }
        k++;
      }      
    } else {
      throw new IllegalArgumentException();
    }
    return new ASTNode(ASTFactory.piecewise(qualifier, qualifiers));
  }

  /**
   * Creates a power {@link ASTNode}.
   * 
   * @param basis the basis
   * @param exponent the exponent
   * @return a power {@link ASTNode}.
   */
  public static ASTNode pow(ASTNode basis, ASTNode exponent) {
    return new ASTNode(ASTFactory.pow(basis.toASTNode2(), exponent.toASTNode2()));
  }

  /**
   * Creates a power {@link ASTNode}.
   * 
   * @param basis the basis
   * @param exponent the exponent
   * @return a power {@link ASTNode}.
   */
  public static ASTNode pow(ASTNode basis, double exponent) {
    return new ASTNode(ASTFactory.pow(basis.toASTNode2(), new ASTCnRealNode(exponent)));
  }

  /**
   * Creates a power {@link ASTNode}.
   * 
   * @param basis the basis
   * @param exponent the exponent
   * @return a power {@link ASTNode}.
   */
  public static ASTNode pow(ASTNode basis, int exponent) {
    return new ASTNode(ASTFactory.pow(basis.toASTNode2(), new ASTCnIntegerNode(exponent)));
  }

  /**
   * Raises the given basis by the power of the given exponent.
   * 
   * @param container the parent object
   * @param basis the basis
   * @param exponent the exponent
   * @return a power {@link ASTNode}.
   */
  public static ASTNode pow(MathContainer container, CallableSBase basis, CallableSBase exponent) {
    ASTCSymbolBaseNode ciBasis = basis instanceof FunctionDefinition ? 
        new ASTCiFunctionNode() : new ASTCiNumberNode();
    ciBasis.setName(basis.getId());
    ASTCSymbolBaseNode ciExponent = basis instanceof FunctionDefinition ? 
        new ASTCiFunctionNode() : new ASTCiNumberNode();
    ciExponent.setName(basis.getId());
    ASTPowerNode pow = ASTFactory.pow(ciBasis, ciExponent);
    pow.setParentSBMLObject(container);
    return new ASTNode(pow);
  }

  /**
   * Reads the MathML from the given XML string.
   * 
   * <p>Constructs a corresponding
   * abstract syntax tree, and returns a pointer to the root of the tree.
   * 
   * @param xml
   *            the MathML XML string.
   * @return an {@link ASTNode}
   */
  public static ASTNode readMathMLFromString(String xml) {
    try {
      return new SBMLReader().readMathML(xml);
    } catch (XMLStreamException e) {
      e.printStackTrace();
    }

    return null;
  }

  /**
   * Creates a relational {@link ASTNode} of the given type with the two given
   * children left and right.
   * <p> Sets the parent SBML object of all nodes to
   * the one provided by the left child.
   * 
   * @param type the type of relational node.
   * @param left the left child.
   * @param right the right child.
   * @return a relational {@link ASTNode} of the given type with the two given
   * children left and right.
   */
  private static ASTNode relational(ASTNode.Type type, ASTNode left, ASTNode right) {
    if ((left == null) || (right == null)) {
      throw new NullPointerException(
          "Cannot create a relational node with null arguments.");
    }
    return new ASTNode(ASTFactory.relational(type, left.toASTNode2(), right.toASTNode2()));
  }

  /**
   * Creates a root {@link ASTNode}.
   * 
   * @param radicand the radicand
   * @param rootExponent the exponent of the root element.
   * @return a root {@link ASTNode}.
   */
  public static ASTNode root(ASTNode rootExponent, ASTNode radicand) {
    return new ASTNode(ASTFactory.root(rootExponent.toASTNode2(), radicand.toASTNode2()));
  }

  /**
   * Sets the Parent of the node and its children to the given value
   * 
   * @param node the orphan node
   * @param parent the parent
   */
  static void setParentSBMLObject(ASTNode node, MathContainer parent) {
    node.toASTNode2().setParentSBMLObject(parent);
  }

  /**
   * Sets the parent of the node and its children to the given value
   * 
   * @param node the orphan node
   * @param parent the parent
   * @param depth the current depth in the {@link ASTNode} tree.
   *            It is just here for testing purposes to track the depth in the tree
   *            during the process.
   */
  private static void setParentSBMLObject(ASTNode node, MathContainer parent,
      int depth) {
    // TODO: Is using depth for testing still required? Can we remove this method?
    setParentSBMLObject(node, parent);
  }

  /**
   * Creates a root {@link ASTNode}.
   * 
   * @param radicand
   * @return a root {@link ASTNode}.
   */
  public static ASTNode sqrt(ASTNode radicand) {
    return new ASTNode(ASTFactory.sqrt(radicand.toASTNode2()));
  }

  /**
   * Creates a new {@link ASTNode} of type Plus with the given nodes as children.
   * 
   * @param ast the children nodes.
   * @return a new {@link ASTNode} of type Plus with the given nodes as children.
   */
  public static ASTNode sum(ASTNode... ast) {
    ASTNode2[] ast2 = new ASTNode2[ast.length];
    for (int i = 0; i < ast.length; i++) {
      ast2[i] = ast[i].toASTNode2();
    }
    return new ASTNode(ASTFactory.sum(ast2));
  }

  /**
   * Sum of several NamedSBase objects.
   * 
   * @param parent the parent
   * @param sbase
   * @return the sum of several NamedSBase objects.
   */
  public static ASTNode sum(MathContainer parent, CallableSBase... sbase) {
    ASTCSymbolBaseNode[] ref = new ASTCSymbolBaseNode[sbase.length];
    for (int i = 0; i < sbase.length; i++) {
      ref[i] =  sbase[i] instanceof FunctionDefinition ? 
          new ASTCiFunctionNode() : new ASTCiNumberNode();
      ref[i].setName(sbase[i].getId());
    }
    ASTArithmeticOperatorNode sum = ASTFactory.sum(ref);
    sum.setParentSBMLObject(parent);
    return new ASTNode(sum);
  }

  /**
   * Creates an {@link ASTNode} of type product and adds the given nodes as children.
   * 
   * @param ast
   * @return an {@link ASTNode} of type product and adds the given nodes as children.
   */
  public static ASTNode times(ASTNode... ast) {
    ASTNode2[] ast2 = new ASTNode2[ast.length];
    for (int i = 0; i < ast.length; i++) {
      ast2[i] = ast[i].toASTNode2();
    }
    return new ASTNode(ASTFactory.product(ast2));
  }

  /**
   * Multiplies several {@link CallableSBase} objects.
   * 
   * @param parent
   * @param sbase
   * @return the multiplication of several {@link CallableSBase} objects.
   */
  public static ASTNode times(MathContainer parent, CallableSBase... sbase) {
    ASTCSymbolBaseNode[] ref = new ASTCSymbolBaseNode[sbase.length];
    for (int i = 0; i < sbase.length; i++) {
      ref[i] =  sbase[i] instanceof FunctionDefinition ? 
          new ASTCiFunctionNode() : new ASTCiNumberNode();
      ref[i].setName(sbase[i].getId());
    }
    ASTArithmeticOperatorNode times = ASTFactory.product(ref);
    times.setParentSBMLObject(parent);
    return new ASTNode(times);
  }

  /**
   * Creates a new {@link ASTNode} that has exactly one child and which is of type
   * minus, i.e., this negates what is encoded in ast.
   * 
   * @param ast
   * @return a new {@link ASTNode} that has exactly one child and which is of type
   * minus, i.e., this negates what is encoded in ast.
   */
  public static ASTNode uMinus(ASTNode ast) {
    return new ASTNode(ASTFactory.uMinus(ast.toASTNode2()));
  }

  /**
   * Creates a new {@link ASTNode} that has exactly one child and which is of type
   * minus, i.e., this negates what is encoded in ast.
   * 
   * @param container
   * @param sbase
   * @return a new {@link ASTNode} that has exactly one child and which is of type
   * minus, i.e., this negates what is encoded in ast.
   */
  public static ASTNode uMinus(MathContainer container) {
    if (container instanceof CallableSBase) {
      ASTCSymbolBaseNode ci = container instanceof FunctionDefinition ? 
          new ASTCiFunctionNode() : new ASTCiNumberNode();
          ci.setName(((CallableSBase)container).getId());
          return new ASTNode(ASTFactory.multiplyWith(ci));      
    }
    return null;
  }

  /**
   * A pointer to the {@link ASTNode2} corresponding to the current {@link ASTNode}
   */
  private ASTNode2 astnode2;

  /**
   * A {@link Logger} for this class.
   */
  private static transient final Logger logger = Logger.getLogger(ASTNode.class);
  
  /**
   * Returns true iff astnode2 has been set
   * @return {@link boolean} {@code true}
   */
  private boolean isSetASTNode2() {
    return astnode2 != null;
  }

  /**
   * Creates a new {@link ASTNode} of unspecified type and without a pointer
   * to its containing {@link MathContainer}.
   */
  public ASTNode() {
    super();
  }

  /**
   * Copy constructor; Creates a deep copy of the given {@link ASTNode}.
   * 
   * @param astNode
   *            the {@link ASTNode} to be copied.
   */
  public ASTNode(ASTNode astNode) {
    this();
    
    if (astNode.isSetASTNode2()) {
      astnode2 = astNode.toASTNode2().clone();      
    }
  }

  /**
   * Creates a new {@link ASTNode} of the given {@link ASTNode.Type} but without a
   * pointer to its {@link MathContainer}.
   * 
   * @param type
   */
  public ASTNode(ASTNode.Type type) {
    setType(type);
  }

  /**
   * Creates and returns a new {@link ASTNode}.
   * 
   * @param type
   *            the type of the ASTNode to create.
   * @param parent
   *            the parent SBML object.
   */
  public ASTNode(ASTNode.Type type, MathContainer parent) {
    this(parent);
    setType(type);
  }

  /**
   * Create a new node of type {@link ASTNode} from the given
   * {@link ASTNode2}
   * 
   * @param node {@link ASTNode2}
   */
  public ASTNode(ASTNode2 node) {
    astnode2 = node;
  }

  /**
   * Creates and returns a new {@link ASTNode} referring to the given {@link CallableSBase}.
   * @param nsb
   */
  public ASTNode(CallableSBase nsb) {
    this(ASTNode.Type.NAME);
    setVariable(nsb);
  }

  /**
   * Creates and returns a new {@link ASTNode} referring to the given {@link CallableSBase}.
   * 
   * @param nsb
   * @param parent
   */
  public ASTNode(CallableSBase nsb, MathContainer parent) {
    this(ASTNode.Type.NAME, parent);
    setVariable(nsb);
  }

  /**
   * Creates a new {@link ASTNode} representing an operator, i.e., an internal
   * node.
   * 
   * @param operator
   */
  public ASTNode(char operator) {
    this();
    setCharacter(operator);
  }

  /**
   * Creates a new {@link ASTNode} representing an operator, i.e., an internal
   * node.
   * 
   * @param operator
   * @param parent
   */
  public ASTNode(char operator, MathContainer parent) {
    this(parent);
    setCharacter(operator);
  }

  /**
   * Creates and returns a new {@link ASTNode}.
   * 
   * @param real
   */
  public ASTNode(double real) {
    switch(Double.compare(real, Math.E)) {
      case 0:
        astnode2 = new ASTConstantNumber(Math.E);
        break;
      default:
        astnode2 = new ASTCnRealNode(real);
        break;
    }
  }

  /**
   * Creates and returns a new {@link ASTNode}.
   * 
   * @param mantissa
   * @param exponent
   */
  public ASTNode(double mantissa, int exponent) {
    astnode2 = new ASTCnExponentialNode();
    ((ASTCnExponentialNode)astnode2).setMantissa(mantissa);
    ((ASTCnExponentialNode)astnode2).setExponent(exponent);
  }

  /**
   * Creates and returns a new {@link ASTNode}.
   * 
   * @param mantissa
   * @param exponent
   * @param parent
   */
  public ASTNode(double mantissa, int exponent, MathContainer parent) {
    this(ASTNode.Type.REAL_E, parent);
    setValue(mantissa, exponent);
  }

  /**
   * Creates and returns a new {@link ASTNode}.
   * 
   * @param real
   * @param parent
   */
  public ASTNode(double real, MathContainer parent) {
    this(ASTNode.Type.REAL, parent);
    setValue(real);
  }

  /**
   * Creates and returns a new {@link ASTNode} with the given integer value.
   * 
   * @param integer
   */
  public ASTNode(int integer) {
    astnode2 = new ASTCnIntegerNode(integer);
  }

  /**
   * Creates and returns a new {@link ASTNode} with the given integer value for
   * the
   * given {@link MathContainer} as its parent SBML object.
   * 
   * @param integer
   * @param parent
   */
  public ASTNode(int integer, MathContainer parent) {
    this(integer, null, parent);
  }

  /**
   * Creates and returns a new {@link ASTNode} with the given integer value with
   * the given associated {@link #unitId} for the
   * given {@link MathContainer} as its parent SBML object.
   * 
   * @param integer
   * @param unitsID
   * @param parent
   */
  public ASTNode(int integer, String unitsID, MathContainer parent) {
    this(ASTNode.Type.INTEGER, parent);
    setValue(integer);
    if (unitsID != null) {
      setUnits(unitsID);
    }
  }

  /**
   * Creates and returns a new {@link ASTNode}.
   * 
   * By default, the returned node will have a type of {@link Type#UNKNOWN}.
   * The calling code should set the node type to something else as soon as
   * possible using setType(int)
   * 
   * @param parent
   *            the parent SBML object
   */
  public ASTNode(MathContainer parent) {
    this();
    // TODO: 
  }

  /**
   * Creates and returns a new {@link ASTNode} with the given name.
   * 
   * @param name  the name of this ASTNode
   */
  public ASTNode(String name) {
    this(ASTNode.Type.NAME);
    setName(name);
  }


  /**
   * Creates and returns a new {@link ASTNode} with the given name.
   * 
   * @param name the name of this ASTNode
   * @param parent
   *            the parent SBML object.
   */
  public ASTNode(String name, MathContainer parent) {
    this(ASTNode.Type.NAME, parent);
    setName(name);
  }

  /**
   * Adds a child to this node.
   * 
   * @param child
   *            the node to add as child.
   */
  public void addChild(ASTNode child) {
    if (isFunction()) {
      ((ASTFunction)astnode2).addChild(child.toASTNode2());
    }
  }

  /**
   * Creates a new node with the type of this node, moves all children of this
   * node to this new node, sets the type of this node to the given operator,
   * adds the new node as left child of this node and the given {@link ASTNode} as the
   * right child of this node. The parentSBMLObject of the whole resulting
   * {@link ASTNode} is then set to the parent of this node.
   * 
   * @param operator
   *        The new type of this node. This has to be one of the
   *        following: {@link Type#PLUS}, {@link Type#MINUS}, {@link Type#TIMES},
   *        {@link Type#DIVIDE}, {@link Type#POWER},
   *        {@link Type#FUNCTION_ROOT}. Otherwise an
   *        {@link IllegalArgumentException} is thrown.
   * @param astnode
   *        The new right child of this node
   * @throws IllegalArgumentException
   *         if
   *         <ul>
   *         <li>this {@link ASTNode} is zero ({@link #isZero()}) and the given
   *         operator is {@link Type#DIVIDE}</li>
   *         <li>the operator is not one of the following: {@link Type#PLUS},
   *         {@link Type#MINUS}, {@link Type#TIMES}, {@link Type#DIVIDE},
   *         {@link Type#POWER}, {@link Type#FUNCTION_ROOT}</li>
   *         </ul>
   */
  private void arithmeticOperation(Type operator, ASTNode astnode) {
    astnode2 = ASTFactory.arithmeticOperation(operator, astnode.toASTNode2());
  }

  /* (non-Javadoc)
   * @see java.lang.Object#clone()
   */
  @Override
  public ASTNode clone() {
    return new ASTNode(this);
  }

  /**
   * Compiles this {@link ASTNode} and returns the result.
   * 
   * @param compiler
   *            An instance of an {@link ASTNodeCompiler} that provides
   *            methods to translate this {@link ASTNode} into something
   *            different.
   * @return Some value wrapped in an {@link ASTNodeValue}. The content of the
   *         wrapper depends on the {@link ASTNodeCompiler} used to create it.
   *         However, this {@link ASTNode} will ensure that level and version
   *         are set appropriately according to this node's parent SBML
   *         object.
   * @throws SBMLException
   *             Thrown if an error occurs during the compilation process.
   * 
   */
  public ASTNodeValue compile(ASTNodeCompiler compiler) throws SBMLException {
    ASTNodeValue value;
    switch (getType()) {
    /*
     * Numbers
     */
    case REAL:
      double real = getReal();
      if (Double.isInfinite(real)) {
        value = (real > 0d) ? compiler.getPositiveInfinity() : compiler
          .getNegativeInfinity();
      } else {
        value = compiler.compile(real, getUnits());
      }
      break;
    case INTEGER:
      value = compiler.compile(getInteger(), getUnits());
      break;
      /*
       * Operators
       */
    case POWER:
      value = compiler.pow(getLeftChild(), getRightChild());
      break;
    case PLUS:
      value = compiler.plus(getChildren());
      value.setUIFlag(getChildCount() <= 1);
      break;
    case MINUS:
      if (getChildCount() < 2) {
        value = compiler.uMinus(getLeftChild());
        value.setUIFlag(true);
      } else {
        value = compiler.minus(getChildren());
        value.setUIFlag(false);
      }
      break;
    case TIMES:
      value = compiler.times(getChildren());
      value.setUIFlag(getChildCount() <= 1);
      break;
    case DIVIDE:
      int childCount = getChildCount();
      if (childCount != 2) {
        throw new SBMLException(MessageFormat.format(
          "Fractions must have one numerator and one denominator, here {0,number,integer} elements are given.",
          childCount));
      }
      value = compiler.frac(getLeftChild(), getRightChild());
      break;
    case RATIONAL:
      value = compiler.frac(getNumerator(), getDenominator());
      break;
    case NAME_TIME:
      value = compiler.symbolTime(getName());
      break;
    case FUNCTION_DELAY:
      value = compiler.delay(getName(), getLeftChild(), getRightChild(),
        getUnits());
      break;
      /*
       * Names of identifiers: parameters, functions, species etc.
       */
    case NAME:
      CallableSBase variable = getVariable();

      if (variable != null) {
        if (variable instanceof FunctionDefinition) {
          value = compiler.function((FunctionDefinition) variable,
            getChildren());
        } else {
          value = compiler.compile(variable);
        }
      } else {
        value = compiler.compile(getName());
      }
      break;
      /*
       * Type: pi, e, true, false, Avogadro
       */
    case CONSTANT_PI:
      value = compiler.getConstantPi();
      break;
    case CONSTANT_E:
      value = compiler.getConstantE();
      break;
    case CONSTANT_TRUE:
      value = compiler.getConstantTrue();
      break;
    case CONSTANT_FALSE:
      value = compiler.getConstantFalse();
      break;
    case NAME_AVOGADRO:
      value = compiler.getConstantAvogadro(getName());
      break;
    case REAL_E:
      value = compiler.compile(getMantissa(), getExponent(),
        isSetUnits() ? getUnits() : null);
      break;
      /*
       * Basic Functions
       */
    case FUNCTION_LOG:
      if (getChildCount() == 2) {
        value = compiler.log(getLeftChild(), getRightChild());
      } else {
        value = compiler.log(getRightChild());
      }
      break;
    case FUNCTION_ABS:
      value = compiler.abs(getRightChild());
      break;
    case FUNCTION_ARCCOS:
      value = compiler.arccos(getLeftChild());
      break;
    case FUNCTION_ARCCOSH:
      value = compiler.arccosh(getLeftChild());
      break;
    case FUNCTION_ARCCOT:
      value = compiler.arccot(getLeftChild());
      break;
    case FUNCTION_ARCCOTH:
      value = compiler.arccoth(getLeftChild());
      break;
    case FUNCTION_ARCCSC:
      value = compiler.arccsc(getLeftChild());
      break;
    case FUNCTION_ARCCSCH:
      value = compiler.arccsch(getLeftChild());
      break;
    case FUNCTION_ARCSEC:
      value = compiler.arcsec(getLeftChild());
      break;
    case FUNCTION_ARCSECH:
      value = compiler.arcsech(getLeftChild());
      break;
    case FUNCTION_ARCSIN:
      value = compiler.arcsin(getLeftChild());
      break;
    case FUNCTION_ARCSINH:
      value = compiler.arcsinh(getLeftChild());
      break;
    case FUNCTION_ARCTAN:
      value = compiler.arctan(getLeftChild());
      break;
    case FUNCTION_ARCTANH:
      value = compiler.arctanh(getLeftChild());
      break;
    case FUNCTION_CEILING:
      value = compiler.ceiling(getLeftChild());
      break;
    case FUNCTION_COS:
      value = compiler.cos(getLeftChild());
      break;
    case FUNCTION_COSH:
      value = compiler.cosh(getLeftChild());
      break;
    case FUNCTION_COT:
      value = compiler.cot(getLeftChild());
      break;
    case FUNCTION_COTH:
      value = compiler.coth(getLeftChild());
      break;
    case FUNCTION_CSC:
      value = compiler.csc(getLeftChild());
      break;
    case FUNCTION_CSCH:
      value = compiler.csch(getLeftChild());
      break;
    case FUNCTION_EXP:
      value = compiler.exp(getLeftChild());
      break;
    case FUNCTION_FACTORIAL:
      value = compiler.factorial(getLeftChild());
      break;
    case FUNCTION_FLOOR:
      value = compiler.floor(getLeftChild());
      break;
    case FUNCTION_LN:
      value = compiler.ln(getLeftChild());
      break;
    case FUNCTION_POWER:
      value = compiler.pow(getLeftChild(), getRightChild());
      break;
    case FUNCTION_ROOT:
      ASTNode left = getLeftChild();
      if (getChildCount() == 2) {
        if (left.isInteger()) {
          int leftValue = left.getInteger();
          if (leftValue == 2) {
            value = compiler.sqrt(getRightChild());
          } else {
            value = compiler.root(leftValue, getRightChild());
          }
        } else if (left.isReal()) {
          double leftValue = left.getReal();
          if (leftValue == 2d) {
            value = compiler.sqrt(getRightChild());
          } else {
            value = compiler.root(leftValue, getRightChild());
          }
        } else {
          value = compiler.root(left, getRightChild());
        }
      } else if (getChildCount() == 1) {
        value = compiler.sqrt(getRightChild());
      } else {
        value = compiler.root(left, getRightChild());
      }
      break;
    case FUNCTION_SEC:
      value = compiler.sec(getLeftChild());
      break;
    case FUNCTION_SECH:
      value = compiler.sech(getLeftChild());
      break;
    case FUNCTION_SELECTOR:
      value = compiler.selector(getChildren());
      break;
    case FUNCTION_SIN:
      value = compiler.sin(getLeftChild());
      break;
    case FUNCTION_SINH:
      value = compiler.sinh(getLeftChild());
      break;
    case FUNCTION_TAN:
      value = compiler.tan(getLeftChild());
      break;
    case FUNCTION_TANH:
      value = compiler.tanh(getLeftChild());
      break;
    case FUNCTION: {
      variable = getVariable();
      
      if (variable != null) {
        if (variable instanceof FunctionDefinition) {
          value = compiler.function((FunctionDefinition) variable,
            getChildren());
        } else {
          logger
          .warn("ASTNode of type FUNCTION but the variable is not a FunctionDefinition! ("
              + getName()
              + ", "
              + getParentSBMLObject().getElementName()
              + ")");
          throw new SBMLException(
            "ASTNode of type FUNCTION but the variable is not a FunctionDefinition! ("
                + getName() + ", " + getParentSBMLObject().getElementName()
                + ")");
          // value = compiler.compile(variable);
        }
      } else {
        logger.debug(MessageFormat.format(
          "ASTNode of type FUNCTION but the variable is null: ({0}, {1})! Check that your object is linked to a Model.",
          getName(), (getParentSBMLObject() != null ? getParentSBMLObject().getElementName() : null)));
        value = compiler.function(getName(), getChildren());
      }
      break;
    }
    case FUNCTION_PIECEWISE:
      value = compiler.piecewise(getChildren());
      value.setUIFlag(getChildCount() <= 1);
      break;
    case LAMBDA:
      value = compiler.lambda(getChildren());
      value.setUIFlag(getChildCount() <= 1);
      break;
      /*
       * Logical and relational functions
       */
    case LOGICAL_AND:
      value = compiler.and(getChildren());
      value.setUIFlag(getChildCount() <= 1);
      break;
    case LOGICAL_XOR:
      value = compiler.xor(getChildren());
      value.setUIFlag(getChildCount() <= 1);
      break;
    case LOGICAL_OR:
      value = compiler.or(getChildren());
      value.setUIFlag(getChildCount() <= 1);
      break;
    case LOGICAL_NOT:
      value = compiler.not(getLeftChild());
      break;
    case RELATIONAL_EQ:
      value = compiler.eq(getLeftChild(), getRightChild());
      break;
    case RELATIONAL_GEQ:
      value = compiler.geq(getLeftChild(), getRightChild());
      break;
    case RELATIONAL_GT:
      value = compiler.gt(getLeftChild(), getRightChild());
      break;
    case RELATIONAL_NEQ:
      value = compiler.neq(getLeftChild(), getRightChild());
      break;
    case RELATIONAL_LEQ:
      value = compiler.leq(getLeftChild(), getRightChild());
      break;
    case RELATIONAL_LT:
      value = compiler.lt(getLeftChild(), getRightChild());
      break;
    case VECTOR:
      value = compiler.vector(getChildren());
      value.setUIFlag(getChildCount() <= 1);
      break;
    default: // UNKNOWN:
      value = compiler.unknownValue();
      break;
    }
    value.setType(getType());
    MathContainer parent = getParentSBMLObject();
    if (parent != null) {
      value.setLevel(parent.getLevel());
      value.setVersion(parent.getVersion());
    }
    return value;
  }

  /**
   * Compiles this {@link ASTNode} and returns the result.
   * 
   * @param compiler
   *            An instance of an {@link ASTNodeCompiler} that provides
   *            methods to translate this {@link ASTNode} into something
   *            different.
   * @return Some value wrapped in an {@link ASTNodeValue}. The content of the
   *         wrapper depends on the {@link ASTNodeCompiler} used to create it.
   *         However, this {@link ASTNode} will ensure that level and version
   *         are set appropriately according to this node's parent SBML
   *         object.
   * @throws SBMLException
   *             Thrown if an error occurs during the compilation process.
   * 
   */
  public ASTNode2Value<?> compile2(ASTNode2Compiler compiler) throws SBMLException {
    ASTNode2Value<?> value;
    switch (getType()) {
      /*
       * Numbers
       */
      case REAL:
        double real = getReal();
        if (Double.isInfinite(real)) {
          value = (real > 0d) ? compiler.getPositiveInfinity() : compiler
              .getNegativeInfinity();
        } else {
          value = compiler.compile(real, isSetUnits() ? getUnits() : null);
        }
        break;
      case INTEGER:
        value = compiler.compile(getInteger(), isSetUnits() ? getUnits() : null);
        break;
        /*
         * Operators
         */
      case POWER:
        value = compiler.pow(((ASTPowerNode)toASTNode2()).getChildAt(0), ((ASTPowerNode)toASTNode2()).getChildAt(
            ((ASTPowerNode)toASTNode2()).getChildCount() - 1));
        break;
      case PLUS:
        value = compiler.plus(((ASTFunction)toASTNode2()).getChildren());
        value.setUIFlag(getChildCount() <= 1);
        break;
      case MINUS:
        if (getChildCount() < 2) {
          value = compiler.uMinus(((ASTMinusNode)toASTNode2()).getChildAt(0));
          value.setUIFlag(true);
        } else {
          value = compiler.minus(((ASTFunction)toASTNode2()).getChildren());
          value.setUIFlag(false);
        }
        break;
      case TIMES:
        value = compiler.times(((ASTFunction)toASTNode2()).getChildren());
        value.setUIFlag(getChildCount() <= 1);
        break;
      case DIVIDE:
        int childCount = getChildCount();
        if (childCount != 2) {
          throw new SBMLException(MessageFormat.format(
              "Fractions must have one numerator and one denominator, here {0,number,integer} elements are given.",
              childCount));
        }
        value = compiler.frac(((ASTCnRationalNode)toASTNode2()).getChildAt(0), ((ASTCnRationalNode)toASTNode2()).getChildAt(
            ((ASTCnRationalNode)toASTNode2()).getChildCount() - 1));
        break;
      case RATIONAL:
        value = compiler.frac(getNumerator(), getDenominator());
        break;
      case NAME_TIME:
        value = compiler.symbolTime(getName());
        break;
        //    case FUNCTION_DELAY:
        //      value = compiler.delay(getName(), ((ASTCSymbolDelayNode)toASTNode2()).getChildAt(0), getRightChild(),
        //        getUnits());
        //      break;
        //      /*
        //       * Names of identifiers: parameters, functions, species etc.
        //       */
        //    case NAME:
        //      if (variable == null) {
        //        variable = getVariable();
        //      }
        //      if (variable != null) {
        //        if (variable instanceof FunctionDefinition) {
        //          value = compiler.function((FunctionDefinition) variable,
        //            getChildren());
        //        } else {
        //          value = compiler.compile(variable);
        //        }
        //      } else {
        //        value = compiler.compile(getName());
        //      }
        //      break;
        /*
         * Type: pi, e, true, false, Avogadro
         */
      case CONSTANT_PI:
        value = compiler.getConstantPi();
        break;
      case CONSTANT_E:
        value = compiler.getConstantE();
        break;
      case CONSTANT_TRUE:
        value = compiler.getConstantTrue();
        break;
      case CONSTANT_FALSE:
        value = compiler.getConstantFalse();
        break;
      case NAME_AVOGADRO:
        value = compiler.getConstantAvogadro(getName());
        break;
      case REAL_E:
        value = compiler.compile(getMantissa(), getExponent(),
            isSetUnits() ? getUnits() : null);
        break;
        /*
         * Basic Functions
         */
      case FUNCTION_LOG:
        if (getChildCount() == 2) {
          value = compiler.log(((ASTLogarithmNode)toASTNode2()).getChildAt(0), 
              ((ASTLogarithmNode)toASTNode2()).getChildAt(
                  ((ASTLogarithmNode)toASTNode2()).getChildCount() - 1));
        } else {
          value = compiler.log(((ASTLogarithmNode)toASTNode2()).getChildAt(
              ((ASTLogarithmNode)toASTNode2()).getChildCount() - 1));
        }
        break;
      case FUNCTION_ABS:
        value = compiler.abs(((ASTUnaryFunctionNode)toASTNode2()).getChildAt(0));
        break;
      case FUNCTION_ARCCOS:
        value = compiler.arccos(((ASTTrigonometricNode)toASTNode2()).getChildAt(0));
        break;
      case FUNCTION_ARCCOSH:
        value = compiler.arccosh(((ASTHyperbolicNode)toASTNode2()).getChildAt(0));
        break;
      case FUNCTION_ARCCOT:
        value = compiler.arccot(((ASTTrigonometricNode)toASTNode2()).getChildAt(0));
        break;
      case FUNCTION_ARCCOTH:
        value = compiler.arccoth(((ASTHyperbolicNode)toASTNode2()).getChildAt(0));
        break;
      case FUNCTION_ARCCSC:
        value = compiler.arccsc(((ASTTrigonometricNode)toASTNode2()).getChildAt(0));
        break;
      case FUNCTION_ARCCSCH:
        value = compiler.arccsch(((ASTHyperbolicNode)toASTNode2()).getChildAt(0));
        break;
      case FUNCTION_ARCSEC:
        value = compiler.arcsec(((ASTTrigonometricNode)toASTNode2()).getChildAt(0));
        break;
      case FUNCTION_ARCSECH:
        value = compiler.arcsech(((ASTHyperbolicNode)toASTNode2()).getChildAt(0));
        break;
      case FUNCTION_ARCSIN:
        value = compiler.arcsin(((ASTTrigonometricNode)toASTNode2()).getChildAt(0));
        break;
      case FUNCTION_ARCSINH:
        value = compiler.arcsinh(((ASTHyperbolicNode)toASTNode2()).getChildAt(0));
        break;
      case FUNCTION_ARCTAN:
        value = compiler.arctan(((ASTTrigonometricNode)toASTNode2()).getChildAt(0));
        break;
      case FUNCTION_ARCTANH:
        value = compiler.arctanh(((ASTHyperbolicNode)toASTNode2()).getChildAt(0));
        break;
      case FUNCTION_CEILING:
        value = compiler.ceiling(((ASTUnaryFunctionNode)toASTNode2()).getChildAt(0));
        break;
      case FUNCTION_COS:
        value = compiler.cos(((ASTTrigonometricNode)toASTNode2()).getChildAt(0));
        break;
      case FUNCTION_COSH:
        value = compiler.cosh(((ASTHyperbolicNode)toASTNode2()).getChildAt(0));
        break;
      case FUNCTION_COT:
        value = compiler.cot(((ASTTrigonometricNode)toASTNode2()).getChildAt(0));
        break;
      case FUNCTION_COTH:
        value = compiler.coth(((ASTHyperbolicNode)toASTNode2()).getChildAt(0));
        break;
      case FUNCTION_CSC:
        value = compiler.csc(((ASTTrigonometricNode)toASTNode2()).getChildAt(0));
        break;
      case FUNCTION_CSCH:
        value = compiler.csch(((ASTHyperbolicNode)toASTNode2()).getChildAt(0));
        break;
      case FUNCTION_EXP:
        value = compiler.exp((ASTFunction)toASTNode2().getChildAt(0));
        break;
      case FUNCTION_FACTORIAL:
        value = compiler.factorial(((ASTUnaryFunctionNode)toASTNode2()).getChildAt(0));
        break;
      case FUNCTION_FLOOR:
        value = compiler.floor(((ASTUnaryFunctionNode)toASTNode2()).getChildAt(0));
        break;
      case FUNCTION_LN:
        value = compiler.ln(((ASTLogarithmNode)toASTNode2()).getChildAt(0));
        break;
      case FUNCTION_POWER:
        value = compiler.pow(((ASTPowerNode)toASTNode2()).getChildAt(0), ((ASTPowerNode)toASTNode2()).getChildAt(
            ((ASTPowerNode)toASTNode2()).getChildCount() - 1));
        break;
      case FUNCTION_ROOT:
        ASTNode2 left = ((ASTFunction)toASTNode2()).getChildAt(0);
        if (getChildCount() == 2) {
          if (left.getType() == Type.INTEGER) {
            int leftValue = ((ASTCnIntegerNode)left).getInteger();
            if (leftValue == 2) {
              value = compiler.sqrt(((ASTRootNode)toASTNode2()).getChildAt(
                  ((ASTRootNode)toASTNode2()).getChildCount() - 1));
            } else {
              value = compiler.root(leftValue, ((ASTRootNode)toASTNode2()).getChildAt(
                  ((ASTRootNode)toASTNode2()).getChildCount() - 1));
            }
          } else if (left.getType() == Type.REAL) {
            double leftValue = ((ASTCnRealNode)left).getReal();
            if (leftValue == 2d) {
              value = compiler.sqrt(((ASTRootNode)toASTNode2()).getChildAt(
                  ((ASTRootNode)toASTNode2()).getChildCount() - 1));
            } else {
              value = compiler.root(leftValue, ((ASTRootNode)toASTNode2()).getChildAt(
                  ((ASTRootNode)toASTNode2()).getChildCount() - 1));
            }
          } else {
            value = compiler.root(left, ((ASTRootNode)toASTNode2()).getChildAt(
                ((ASTRootNode)toASTNode2()).getChildCount() - 1));
          }
        } else if (getChildCount() == 1) {
          value = compiler.sqrt(((ASTRootNode)toASTNode2()).getChildAt(
              ((ASTRootNode)toASTNode2()).getChildCount() - 1));
        } else {
          value = compiler.root(left, ((ASTRootNode)toASTNode2()).getChildAt(
              ((ASTRootNode)toASTNode2()).getChildCount() - 1));
        }
        break;
      case FUNCTION_SEC:
        value = compiler.sec(((ASTTrigonometricNode)toASTNode2()).getChildAt(0));
        break;
      case FUNCTION_SECH:
        value = compiler.sech(((ASTHyperbolicNode)toASTNode2()).getChildAt(0));
        break;
      case FUNCTION_SELECTOR:
        value = compiler.selector(((ASTFunction)toASTNode2()).getChildren());
        break;
      case FUNCTION_SIN:
        value = compiler.sin(((ASTTrigonometricNode)toASTNode2()).getChildAt(0));
        break;
      case FUNCTION_SINH:
        value = compiler.sinh(((ASTHyperbolicNode)toASTNode2()).getChildAt(0));
        break;
      case FUNCTION_TAN:
        value = compiler.tan(((ASTTrigonometricNode)toASTNode2()).getChildAt(0));
        break;
      case FUNCTION_TANH:
        value = compiler.tanh(((ASTHyperbolicNode)toASTNode2()).getChildAt(0));
        break;
        //    case FUNCTION: {
        //      if (variable == null) {
        //        variable = getVariable();
        //      }
        //      if (variable != null) {
        //        if (variable instanceof FunctionDefinition) {
        //          value = compiler.function((FunctionDefinition) variable,
        //            ((ASTFunction)toASTNode2()).getChildren());
        //        } else {
        //          logger
        //          .warn("ASTNode of type FUNCTION but the variable is not a FunctionDefinition! ("
        //              + getName()
        //              + ", "
        //              + getParentSBMLObject().getElementName()
        //              + ")");
        //          throw new SBMLException(
        //            "ASTNode of type FUNCTION but the variable is not a FunctionDefinition! ("
        //                + getName() + ", " + getParentSBMLObject().getElementName()
        //                + ")");
        //          // value = compiler.compile(variable);
        //        }
        //      } else {
        //        logger.warn(MessageFormat.format(
        //          "ASTNode of type FUNCTION but the variable is null: ({0}, {1})! Check that your object is linked to a Model.",
        //          getName(), (getParentSBMLObject() != null ? getParentSBMLObject().getElementName() : null)));
        //        value = compiler.function(getName(), (ASTFunction)toASTNode2()).getChildren());
        //      }
        //      break;
        //    }
        //    case FUNCTION_PIECEWISE:
        //      value = compiler.piecewise((ASTFunction)toASTNode2()).getChildren());
        //      value.setUIFlag(getChildCount() <= 1);
        //      break;
        //    case LAMBDA:
        //      value = compiler.lambda((ASTFunction)toASTNode2()).getChildren());
        //      value.setUIFlag(getChildCount() <= 1);
        //      break;
        //      /*
        //       * Logical and relational functions
        //       */
        //    case LOGICAL_AND:
        //      value = compiler.and(getChildren());
        //      value.setUIFlag(getChildCount() <= 1);
        //      break;
        //    case LOGICAL_XOR:
        //      value = compiler.xor(getChildren());
        //      value.setUIFlag(getChildCount() <= 1);
        //      break;
        //    case LOGICAL_OR:
        //      value = compiler.or((ASTLogicalOperatorNode)toASTNode2().children());
        //      value.setUIFlag(getChildCount() <= 1);
        //      break;
      case LOGICAL_NOT:
        value = compiler.not((ASTLogicalOperatorNode)toASTNode2().getChildAt(0));
        break;
      case RELATIONAL_EQ:
        value = compiler.eq((ASTRelationalOperatorNode)toASTNode2().getChildAt(0), 
            ((ASTRelationalOperatorNode)toASTNode2()).getChildAt(
                ((ASTRelationalOperatorNode)toASTNode2()).getChildCount() - 1));
        break;
      case RELATIONAL_GEQ:
        value = compiler.geq((ASTRelationalOperatorNode)toASTNode2().getChildAt(0), 
            ((ASTRelationalOperatorNode)toASTNode2()).getChildAt(
                ((ASTRelationalOperatorNode)toASTNode2()).getChildCount() - 1));
        break;
      case RELATIONAL_GT:
        value = compiler.gt((ASTRelationalOperatorNode)toASTNode2().getChildAt(0), 
            ((ASTRelationalOperatorNode)toASTNode2()).getChildAt(
                ((ASTRelationalOperatorNode)toASTNode2()).getChildCount() - 1));
        break;
      case RELATIONAL_NEQ:
        value = compiler.neq((ASTRelationalOperatorNode)toASTNode2().getChildAt(0), 
            ((ASTRelationalOperatorNode)toASTNode2()).getChildAt(
                ((ASTRelationalOperatorNode)toASTNode2()).getChildCount() - 1));
        break;
      case RELATIONAL_LEQ:
        value = compiler.leq((ASTRelationalOperatorNode)toASTNode2().getChildAt(0), 
            ((ASTRelationalOperatorNode)toASTNode2()).getChildAt(
                ((ASTRelationalOperatorNode)toASTNode2()).getChildCount() - 1));
        break;
      case RELATIONAL_LT:
        value = compiler.lt((ASTRelationalOperatorNode)toASTNode2().getChildAt(0), 
            ((ASTRelationalOperatorNode)toASTNode2()).getChildAt(
                ((ASTRelationalOperatorNode)toASTNode2()).getChildCount() - 1));
        break;
      case VECTOR:
        value = compiler.vector(((ASTFunction)toASTNode2()).getChildren());
        value.setUIFlag(getChildCount() <= 1);
        break;
      default: // UNKNOWN:
        value = compiler.unknownValue();
        break;
    }
    value.setType(getType());
    MathContainer parent = null;
    if (isSetParentSBMLObject()) {
      parent = getParentSBMLObject();
    }
    if (parent != null) {
      value.setLevel(parent.getLevel());
      value.setVersion(parent.getVersion());
    }
    return value;
  }

  /**
   * Returns {@code true} or {@code false} depending on whether this
   * {@link ASTNode} refers to elements such as parameters or numbers with
   * undeclared units.
   * 
   * A return value of {@code true} indicates that the {@code UnitDefinition}
   * returned by {@link Variable#getDerivedUnitDefinition()} may not accurately
   * represent the units of the expression.
   * 
   * @return {@code true} if the math expression of this {@link ASTNode}
   *         includes parameters/numbers with undeclared units,
   *         {@code false} otherwise.
   */
  public boolean containsUndeclaredUnits() {
    return astnode2 instanceof ASTCiNumberNode ? 
      ((ASTCiNumberNode)astnode2).containsUndeclaredUnits() : 
      true;
  }

  /**
   * Evaluates recursively this ASTNode and creates a new UnitDefinition with
   * respect to all referenced elements.
   * 
   * @return the derived unit of the node.
   * @throws SBMLException
   *             if they are problems going through the ASTNode tree.
   */
  public UnitDefinition deriveUnit() throws SBMLException {
    return isName() ?
       ((ASTCnNumberNode<?>)astnode2).deriveUnit() : null;
  }

  /**
   * Divides this node by the given node
   * 
   * @param ast
   *            an ASTNode
   * @return the current node for convenience.
   */
  public ASTNode divideBy(ASTNode ast) {
    return new ASTNode(ASTFactory.divideBy(astnode2, ast.toASTNode2()));
  }

  /**
   * Divides this node by the given SBML element.
   * 
   * @param namedSBase
   *            an SBML element that can be represented by a value.
   * @return the current node for convenience.
   */
  public ASTNode divideBy(CallableSBase namedSBase) {
    ASTCSymbolBaseNode ci = namedSBase instanceof FunctionDefinition ? 
        new ASTCiFunctionNode() : new ASTCiNumberNode();
    ci.setName(namedSBase.getId());
    return new ASTNode(ASTFactory.divideBy(astnode2, ci));
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractTreeNode#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    return astnode2.equals(((ASTNode)object).toASTNode2());  
  }

  /**
   * Goes through the formula and identifies all global parameters that are
   * referenced by this rate equation.
   * 
   * @return all global parameters that are referenced by this rate equation.
   */
  public List<Parameter> findReferencedGlobalParameters() {
    return (List<Parameter>) (astnode2 instanceof ASTFunction ? 
        ((ASTFunction)astnode2).findReferencedCallableSBases() 
        : new ArrayList<Parameter>());
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return !(isConstant() || isInfinity() || isNumber() || isNegInfinity()
        || isNaN() || isRational());
  }

  /**
   * Gets the value of this node as a single character. This function should
   * be called only when ASTNode.getType() is one of PLUS, MINUS, TIMES,
   * DIVIDE or POWER.
   * 
   * @return the value of this ASTNode as a single character
   * @throws IllegalArgumentException
   *             if the type of the node is not one of PLUS, MINUS, TIMES,
   *             DIVIDE or POWER.
   */
  public char getCharacter() {
    if (isOperator()) {
      switch (getType()) {
        case PLUS:
          return '+';
        case MINUS:
          return '-';
        case TIMES:
          return '*';
        case DIVIDE:
          return '/';
        case POWER:
          return '^';
        default:
          break;
      }
    }
    throw new IllegalArgumentException(
        "getCharacter() should be called only when isOperator().");
  }

  /**
   * Gets a child of this node according to an index number.
   * 
   * @param index
   *            the index of the child to get
   * @return the child of this {@link ASTNode} with the given index.
   * @throws IndexOutOfBoundsException
   *             - if the index is out of range (index < 0 || index >=
   *             size()).
   */
  public ASTNode getChild(int index) {
    return astnode2 instanceof ASTFunction ? new ASTNode(((ASTFunction)astnode2)
        .getListOfNodes().get(index)) : null;
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildAt(int)
   */
  @Override
  public TreeNode getChildAt(int i) {
    return getChild(i);
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildCount()
   */
  @Override
  public int getChildCount() {
    return isSetASTNode2() ? astnode2.getChildCount() : 0;
  }

  /**
   * Returns the list of children of the current ASTNode.
   * 
   * @return the list of children of the current ASTNode.
   */
  public List<ASTNode> getChildren() {
    return getListOfNodes();
  }

  /**
   * Returns the class name of the mathML element represented by this ASTNode.
   * 
   * @return the class name of the mathML element represented by this ASTNode.
   */
  public String getClassName() {
    return astnode2.getMathMLClass();
  }

  /**
   * Returns the definitionURL
   * 
   * @return the definitionURL
   */
  public String getDefinitionURL() {
    return astnode2 instanceof ASTCSymbolBaseNode ? 
        ((ASTCSymbolBaseNode)astnode2).getDefinitionURL() : null;      
  }

  /**
   * Gets the value of the denominator of this node. This function should be
   * called only when getType() == RATIONAL, otherwise an Exception is thrown.
   * 
   * @return the value of the denominator of this ASTNode.
   * @throws IllegalArgumentException
   *             if the method is called on a node that is not of type
   *             rational.
   */
  public int getDenominator() {
    if (isRational()) {
      return ((ASTCnRationalNode)astnode2).getDenominator();
    }
    throw new IllegalArgumentException(
        "getDenominator() should be called only when getType() == RATIONAL.");
  }

  /**
   * Returns the encoding of the mathML element represented by this ASTNode.
   * 
   * @return the encoding of the mathML element represented by this ASTNode.
   */
  public String getEncoding() {
    return astnode2 instanceof ASTCSymbolNode ? 
        ((ASTCSymbolNode)astnode2).getEncoding() : null;      
  }

  /**
   * Gets the exponent value of this ASTNode. This function should be called
   * only when getType() returns REAL_E or REAL, otherwise an Exception is
   * thrown.
   * 
   * @return the value of the exponent of this ASTNode.
   * @throws IllegalArgumentException
   *             if the method is called on a node that is not of type real.
   */
  public int getExponent() {
    if (astnode2 instanceof ASTCnExponentialNode) {
      return ((ASTCnExponentialNode)astnode2).getExponent();
    }
    throw new IllegalArgumentException(
        "getExponent() should be called only when getType() == REAL_E or REAL");
  }

  /**
   * Returns the id of the mathML element represented by this ASTNode.
   * 
   * @return the id of the mathML element represented by this ASTNode.
   */
  public String getId() {
    return astnode2.getId();
  }

  /**
   * Gets the value of this node as an integer. This function should be called
   * only when getType() == INTEGER, otherwise an Exception is thrown.
   * 
   * @return the value of this ASTNode as an integer.
   * @throws IllegalArgumentException
   *             if the node is not of type integer.
   */
  public int getInteger() {
    if (isInteger()) {
      return ((ASTCnIntegerNode)astnode2).getInteger();
    }
    throw new IllegalArgumentException(
        "getInteger() should be called only when getType() == INTEGER");
  }

  /**
   * Gets the left child of this node.
   * 
   * @return the left child of this ASTNode. This is equivalent to
   *         getChild(0);
   */
  public ASTNode getLeftChild() {
    return astnode2 instanceof ASTFunction ? 
        new ASTNode(((ASTFunction)astnode2).getChildAt(0)) : null;
  }

  /**
   * Returns the list of children of the current ASTNode.
   * 
   * @return the list of children of the current ASTNode.
   */
  public List<ASTNode> getListOfNodes() {
    if (isFunction()) {
      List<ASTNode2> ast2 = ((ASTFunction)astnode2).getListOfNodes();
      List<ASTNode> ast = new ArrayList<ASTNode>();
      for (ASTNode2 node : ast2) {
        ast.add(new ASTNode(node));
      }
      return ast;
    }
    throw new IllegalArgumentException("ASTNodes of type BOOLEAN or NUMBER do not contain children");
  }

  /**
   * Returns the list of children of the current ASTNode that satisfy the
   * given filter.
   * 
   * @param filter
   * @return the list of children of the current ASTNode that satisfy the
   *         given filter.
   */
  public List<ASTNode> getListOfNodes(Filter filter) {
    if (isFunction()) {
      List<ASTNode2> ast2 = ((ASTFunction)astnode2).getListOfNodes(filter);
      List<ASTNode> ast = new ArrayList<ASTNode>();
      for (ASTNode2 node : ast2) {
        ast.add(new ASTNode(node));
      }
      return ast;
    }
    throw new IllegalArgumentException("ASTNodes of type BOOLEAN or NUMBER do not contain children");
  }

  /**
   * Gets the mantissa value of this node. This function should be called only
   * when getType() returns REAL_E or REAL, otherwise an Exception is thrown.
   * If getType() returns REAL, this method is identical to getReal().
   * 
   * @return the value of the mantissa of this ASTNode.
   */
  public double getMantissa() {
    if (astnode2 instanceof ASTCnExponentialNode) {
      return ((ASTCnExponentialNode)toASTNode2()).getMantissa();
    }
    throw new IllegalArgumentException(
        "getMantissa() should be called only when getType() == REAL or REAL_E");
  }

  /**
   * Gets the name of this node. This method may be called on nodes that are
   * not operators ({@code isOperator() == false}) or numbers ({@code isNumber() == false}).
   * 
   * @return the name of this node.
   * @throws IllegalArgumentException
   *             if the method is called on nodes that are operators or
   *             numbers.
   */
  public String getName() {
    if (isName()) {
      return ((ASTCiNumberNode)astnode2).getRefId();
    }
    throw new IllegalArgumentException(
        "getName() should be called only when !isNumber() && !isOperator()");
  }

  /**
   * Gets the value of the numerator of this node. This method should be
   * called only when getType() == RATIONAL, otherwise an Exception is thrown.
   * 
   * 
   * @return the value of the numerator of this ASTNode.
   * @throws IllegalArgumentException
   *             if this method is called on a node type other than rational.
   */
  public int getNumerator() {
    if (isRational()) {
      return ((ASTCnRationalNode)astnode2).getNumerator();
    }
    throw new IllegalArgumentException(
        "getNumerator() should be called only when isRational()");
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getParent()
   */
  @Override
  public TreeNode getParent() {
    return astnode2.getParent();
  }

  /**
   * This method is convenient when holding an object nested inside other
   * objects in an SBML model. It allows direct access to the
   * {@link MathContainer}; element containing it. From this
   * {@link MathContainer} even the overall {@link Model} can be accessed.
   * 
   * @return the parent SBML object.
   */
  public MathContainer getParentSBMLObject() {
    return astnode2.getParentSBMLObject();
  }

  /**
   * Gets the real-numbered value of this node. This function should be called
   * only when {@code isReal() == true}, otherwise and Exception is thrown.
   * 
   * This function performs the necessary arithmetic if the node type is
   * REAL_E (mantissa^exponent) or RATIONAL (numerator / denominator).
   * 
   * @return the value of this ASTNode as a real (double).
   * @throws IllegalArgumentException
   *             if this node is not of type real.
   */
  public double getReal() {
      return isReal() ? ((ASTCnRealNode)astnode2).getReal() : Double.NaN;
  }

  /**
   * Returns a set of all the {@link NamedSBase} referenced on this node and
   * all his descendant.
   * 
   * Just for testing purposes...
   * 
   * @return a set of all the {@link NamedSBase} referenced on this node and
   *         all his descendant.
   */
  public Set<NamedSBase> getReferencedNamedSBases() {
    // TODO: Has no analogous method in the new math package.
    // TODO: Is this method still necessary or is the 'testing' complete?
    return null;
  }

  /**
   * Returns the last child in the list of children of this node.
   * 
   * @return This is equivalent to calling
   *         {@code getListOfNodes().getLast()}.
   */
  public ASTNode getRightChild() {
    if (isFunction()) {
      int childCount = ((ASTFunction)toASTNode2()).getChildCount();
      return new ASTNode(((ASTFunction)toASTNode2()).getChildAt
          (childCount - 1));      
    }
    return null;
  }

  /**
   * Returns the style of the mathML element represented by this ASTNode.
   * 
   * @return the style of the mathML element represented by this ASTNode.
   */
  public String getStyle() {
    return astnode2.getStyle();
  }

  /**
   * Returns the type of this node.
   * 
   * @return the type of this node.
   */
  public ASTNode.Type getType() {
    return astnode2.getType();
  }

  /**
   * Returns the units attribute.
   * 
   * @return the units attribute.
   */
  public String getUnits() {
    return astnode2 instanceof ASTCnNumberNode ? ((ASTCnNumberNode<?>)astnode2)
        .getUnits().getKind().getName() : null;
  }

  /**
   * Creates or obtains a {@link UnitDefinition} corresponding to the unit
   * that has been set for this {@link ASTNode} and returns a pointer to it.
   * Note that in case that this {@link ASTNode} is associated with a
   * {@link Kind}, the created {@link UnitDefinition} will not be part of the
   * model, it is just a container for the {@link Kind}.
   * 
   * @return A {@link UnitDefinition} or {@code null}.
   */
  public UnitDefinition getUnitsInstance() {
    // TODO: Also ASTCiFunctionNodes should have getUnitsInstance()
    return astnode2 instanceof ASTCnNumberNode 
        ? ((ASTCnNumberNode<?>)astnode2).getUnitsInstance() : null;
  }

  /**
   * Returns the variable of this node. This function should be called only
   * when {@code isVariable()} == true}, otherwise an Exception is thrown.
   * 
   * @return the variable of this node
   * @throws RuntimeException
   *             if {@link #isVariable()} returns {@code false}.
   */
  public CallableSBase getVariable() {
    if (isName()) {
      return ((ASTCiNumberNode)astnode2).getReferenceInstance();  
    } else if (astnode2 instanceof ASTCiFunctionNode) {
      return ((ASTCiFunctionNode)astnode2).getReferenceInstance();
    }
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractTreeNode#hashCode()
   */
  @Override
  public int hashCode() {
    return astnode2.hashCode();
  }

  /**
   * Returns {@code true} if the current ASTNode or any of his descendant has a unit
   * defined.
   * 
   * @return {@code true} if the current ASTNode or any of his descendant has a unit
   *         defined.
   */
  public boolean hasUnits() {
    return astnode2 instanceof ASTCnNumberNode ? 
        ((ASTCnNumberNode<?>)astnode2).hasUnits() : false;
  }


  /**
   * Inserts the given {@link ASTNode} at point n in the list of children of this
   * {@link ASTNode}. Inserting a child within an {@link ASTNode} may result in an inaccurate
   * representation.
   * 
   * @param n
   *            long the index of the {@link ASTNode} being added
   * @param newChild
   *            {@link ASTNode} to insert as the n<sup>th</sup> child
   */
  public void insertChild(int n, ASTNode newChild) {
    if (isFunction()) {
      ((ASTFunction)astnode2).insertChild(n, newChild.toASTNode2());
    }
  }

  /**
   * Returns {@code true} if this node has a boolean type (a logical operator, a
   * relational operator, or the constants {@code true} or {@code false}).
   * 
   * @return {@code true} if this ASTNode is a boolean, {@code false} otherwise.
   */
  public boolean isBoolean() {
    return astnode2 instanceof ASTBoolean;
  }

  /**
   * Returns {@code true} if this node represents a MathML constant (e.g., {@code true}, Pi).
   * 
   * @return {@code true} if this ASTNode is a MathML constant, {@code false} otherwise.
   */
  public boolean isConstant() {
    return astnode2 instanceof ASTConstantNumber;
  }

  /**
   * Checks if this {@link ASTNode} represents a difference.
   * 
   * @return {@code true} if this {@link ASTNode} represents a difference, {@code false}
   *         otherwise.
   */
  public boolean isDifference() {
    return astnode2 instanceof ASTMinusNode;
  }

  /**
   * Returns {@code true} if this node represents a function. In this context, the
   * term function means pre-defined functions such as "ceil", "abs" or "sin"
   * or whether this {@link ASTNode} refers to a user-defined
   * {@link FunctionDefinition} object. Without having a valid reference to
   * the {@link MathContainer} that owns this {@link ASTNode} it is impossible
   * to identify the referenced {@link FunctionDefinition}.
   * 
   * @return {@code true} if this {@link ASTNode} is a function, {@code false} otherwise.
   */
  public boolean isFunction() {
    return astnode2 instanceof ASTFunction;
  }

  /**
   * Returns {@code true} if this node represents the special IEEE 754 value infinity,
   * {@code false} otherwise.
   * 
   * @return {@code true} if this ASTNode is the special IEEE 754 value infinity,
   *         {@code false} otherwise.
   */
  public boolean isInfinity() {
    return isReal() && ((ASTCnRealNode)astnode2).isInfinity();
  }

  /**
   * Returns {@code true} if this node contains an integer value, {@code false} otherwise.
   * 
   * @return {@code true} if this ASTNode is of type INTEGER, {@code false} otherwise.
   */
  public boolean isInteger() {
    return astnode2 instanceof ASTCnIntegerNode;
  }

  /**
   * Returns {@code true} if this node is a MathML &lt;lambda&gt;, {@code false} otherwise.
   * 
   * @return {@code true} if this ASTNode is of type LAMBDA, {@code false} otherwise.
   */
  public boolean isLambda() {
    return astnode2 instanceof ASTLambdaFunctionNode;
  }

  /**
   * Returns {@code true} if this node represents a log10() function, {@code false} otherwise.
   * More precisely, this predicate returns {@code true} if the node type is
   * FUNCTION_LOG with two children, the first of which is an INTEGER equal to
   * 10.
   * 
   * @return {@code true} if the given ASTNode represents a log10() function, {@code false}
   *         otherwise.
   */
  public boolean isLog10() {
    return astnode2 instanceof ASTLogarithmNode 
        && astnode2.getType() == Type.FUNCTION_LOG;
  }

  /**
   * Returns {@code true} if this node is a MathML logical operator (i.e., and, or,
   * not, xor).
   * 
   * @return {@code true} if this ASTNode is a MathML logical operator.
   */
  public boolean isLogical() {
    return astnode2 instanceof ASTLogicalOperatorNode;
  }

  /**
   * Returns {@code true} if this astnode represents the number minus one (either as
   * integer or as real value).
   * 
   * @return {@code true} if this astnode represents the number minus one (either as
   * integer or as real value).
   */
  public boolean isMinusOne() {
    return (isReal() && (getReal() == -1d))
        || (isInteger() && (getInteger() == -1))
        || (isUMinus() && getLeftChild().isOne());
  }

  /**
   * Returns {@code true} if this node is a user-defined {@link Variable} name in SBML L1, L2
   * (MathML), or the special symbols delay or time. The predicate returns
   * {@code false} otherwise.
   * 
   * @return {@code true} if this {@link ASTNode} is a user-defined variable name in SBML L1,
   *         L2 (MathML) or the special symbols time or Avogadro.
   */
  public boolean isName() {
    return astnode2 instanceof ASTCiNumberNode;
  }

  /**
   * Returns {@code true} if this node is a type Real and represents the special IEEE
   * 754 value 'not a number' {@link Double#NaN}, {@code false} otherwise.
   * 
   * @return {@code true} if this ASTNode is the {@link Double#NaN}
   */
  public boolean isNaN() {
    return isReal() && Double.isNaN(getReal());
  }

  /**
   * Returns {@code true} if this node represents the special IEEE 754 value 'negative
   * infinity' {@link Double#NEGATIVE_INFINITY}, {@code false} otherwise.
   * 
   * @return {@code true} if this ASTNode is {@link Double#NEGATIVE_INFINITY}, {@code false}
   *         otherwise.
   */
  public boolean isNegInfinity() {
    return isReal() && ((ASTCnRealNode)toASTNode2()).isNegInfinity();
  }

  /**
   * Returns {@code true} if this node contains a number, {@code false} otherwise. This is
   * functionally equivalent to the following code:
   * 
   * <pre class="brush:java">
   * isInteger() || isReal()
   * </pre>
   * 
   * @return {@code true} if this ASTNode is a number, {@code false} otherwise.
   */
  public boolean isNumber() {
    return isInteger() || isReal();
  }

  /**
   * Returns {@code true} if this {@link ASTNode} represents the number one (either as
   * integer or as real value).
   * 
   * @return {@code true} if this {@link ASTNode} represents the number one.
   */
  public boolean isOne() {
    return (isReal() && getReal() == 1d)
        || (isInteger() && getInteger() == 1);
  }

  /**
   * Returns {@code true} if this node is a mathematical operator, meaning, +, -, *, /
   * or ^ (power).
   * 
   * @return {@code true} if this ASTNode is an operator.
   */
  public boolean isOperator() {
    return astnode2 instanceof ASTPlusNode  || astnode2 instanceof ASTMinusNode 
        || astnode2 instanceof ASTTimesNode || astnode2 instanceof ASTDivideNode 
        || astnode2 instanceof ASTPowerNode || astnode2 instanceof ASTArithmeticOperatorNode;
  }

  /**
   * Returns {@code true} if this node is the MathML &lt;piecewise&gt; construct,
   * {@code false} otherwise.
   * 
   * @return {@code true} if this ASTNode is a MathML piecewise function
   */
  public boolean isPiecewise() {
    return astnode2 instanceof ASTPiecewiseFunctionNode;
  }

  /**
   * Returns {@code true} if this node represents a rational number, {@code false} otherwise.
   * 
   * @return {@code true} if this ASTNode is of type {@link Type#RATIONAL}.
   */
  public boolean isRational() {
    return astnode2 instanceof ASTCnRationalNode;
  }

  /**
   * Returns {@code true} if this node can represent a real number, {@code false} otherwise.
   * More precisely, this node must be of one of the following types: REAL,
   * REAL_E or RATIONAL.
   * 
   * @return {@code true} if the value of this ASTNode can represented a real number,
   *         {@code false} otherwise.
   */
  public boolean isReal() {
    return astnode2 instanceof ASTCnRealNode || astnode2 instanceof ASTCnExponentialNode
        || astnode2 instanceof ASTCnRationalNode;
  }

  /**
   * Returns {@code true} if this node is a MathML relational operator, meaning ==,
   * >=, >, <, and !=.
   * 
   * @return {@code true} if this ASTNode is a MathML relational operator, {@code false}
   *         otherwise.
   */
  public boolean isRelational() {
    return astnode2 instanceof ASTRelationalOperatorNode;
  }

  /**
   * @return
   */
  public boolean isSetClassName() {
    return astnode2.isSetMathMLClass();
  }

  /**
   * @return
   */
  public boolean isSetDefinitionURL() {
    if (astnode2 instanceof ASTCSymbolBaseNode) {
      return ((ASTCSymbolBaseNode)astnode2).isSetDefinitionURL();		
    }
    return false;
  }

  /**
   * @return
   */
  public boolean isSetEncoding() {
    if (astnode2 instanceof ASTCSymbolNode) {
      return ((ASTCSymbolNode)astnode2).isSetEncoding();		
    }
    return false;
  }

  /**
   * @return
   */
  public boolean isSetId() {
    return astnode2.isSetId();
  }

  /**
   * @return
   */
  public boolean isSetName() {
    if (astnode2 instanceof ASTFunction) {
      return ((ASTFunction)astnode2).isSetName();
    } else if (astnode2 instanceof ASTCSymbolBaseNode) {
      return ((ASTCSymbolBaseNode)astnode2).isSetName();		  
    }
    return false;
  }

  /**
   * Returns {@code true} if the number type is set.
   * 
   * @return {@code true} if the number type is set.
   */
  public boolean isSetNumberType() {
    return astnode2 instanceof ASTCnNumberNode;
  }

  /**
   * Checks if a parent SBML object, i.e., a {@link MathContainer}, is set as a
   * parent SBML object for this {@link ASTNode}.
   * 
   * @return
   */
  public boolean isSetParentSBMLObject() {
    return astnode2.isSetParentSBMLObject();
  }

  /**
   * @return
   */
  public boolean isSetStyle() {
    return astnode2.isSetStyle();
  }

  /**
   * Returns {@code true} if a unit is defined on this node.
   * 
   * @return {@code true} if a unit is defined on this node.
   */
  public boolean isSetUnits() {
    return astnode2 instanceof ASTCnNumberNode ?
           ((ASTCnNumberNode<?>)astnode2).isSetUnits() : false;
  }

  /**
   * Returns {@code true} if this node represents a square root function, {@code false}
   * otherwise.
   * 
   * More precisely, the node type must be {@link Type#FUNCTION_ROOT} with two
   * children, the first of which is an {@link Type#INTEGER} node having value
   * equal to 2.
   * 
   * @return {@code true} if the given ASTNode represents a sqrt() function, {@code false}
   *         otherwise.
   */
  public boolean isSqrt() {
    return astnode2 instanceof ASTRootNode
        && getLeftChild().isInteger()
        && getLeftChild().getInteger() == 2;
  }

  /**
   * Returns {@code true} if this node is a name or refers to a
   * {@link FunctionDefinition}.
   * 
   * @return {@code true} if this {@link ASTNode} is a user-defined variable name in SBML L1,
   *         L2 (MathML) or the special symbols time or Avogadro.
   * @see #isName()
   */
  public boolean isString() {
    return isName() || isFunction();
  }

  /**
   * Checks if this {@link ASTNode} represents a sum.
   * 
   * @return {@code true} if this {@link ASTNode} represents a sum, {@code false} otherwise.
   */
  public boolean isSum() {
    return astnode2 instanceof ASTArithmeticOperatorNode;
  }

  /**
   * Returns {@code true} if this node is a unary minus operator, {@code false} otherwise. A
   * node is defined as a unary minus node if it is of type MINUS and has
   * exactly one child.
   * 
   * For numbers, unary minus nodes can be 'collapsed' by negating the number.
   * In fact, SBML_parseFormula() does this during its parse. However, unary
   * minus nodes for symbols (NAMES) cannot be 'collapsed', so this predicate
   * function is necessary.
   * 
   * @return {@code true} if this ASTNode is a unary minus, {@code false} otherwise.
   */
  public boolean isUMinus() {
    return (astnode2 instanceof ASTMinusNode) && (getChildCount() == 1);
  }

  /**
   * Checks whether the number of child nodes is exactly one.
   * 
   * @return {@code true} if the number of child nodes is exactly one.
   */
  public boolean isUnary() {
    return getChildCount() == 1;
  }

  /**
   * Returns {@code true} if this node has an {@link Type#UNKNOWN} type.
   * 
   * 'Unknown' nodes have the type {@link Type#UNKNOWN}. Nodes with unknown types will not
   * appear in an ASTNode tree returned by JSBML based upon valid SBML
   * input; the only situation in which a node with type UNKNOWN may appear is
   * immediately after having created a new, untyped node using the ASTNode
   * constructor. Callers creating nodes should endeavor to set the type to a
   * valid node type as soon as possible after creating new nodes.
   * 
   * @return {@code true} if this ASTNode is of type {@link Type#UNKNOWN}, {@code false} otherwise.
   */
  public boolean isUnknown() {
    return astnode2.getType() == ASTNode.Type.UNKNOWN;
  }


  /**
   * Returns {@code true} if this node represents a {@link Variable}.
   * 
   * @return {@code true} if this node represents a {@link Variable}.
   */
  public boolean isVariable() {
    return astnode2 instanceof ASTCiNumberNode || astnode2 instanceof ASTCiFunctionNode;
  }

  /**
   * Checks if this {@link ASTNode} represents a vector.
   * 
   * @return {@code true} if this {@link ASTNode} represents a vector, {@code false} otherwise.
   */
  public boolean isVector() {
    return astnode2 instanceof ASTFunction;
  }

  /**
   * Returns {@code true} if this node represents the number zero (either as integer
   * or as real value).
   * 
   * @return {@code true} if this node represents the number zero.
   */
  public boolean isZero() {
    return (isReal() && getReal() == 0d)
        || (isInteger() && getInteger() == 0);
  }

  /**
   * Subtracts the given ASTNode from this node.
   * 
   * @param ast
   *            an {@code ASTNode}
   * @return the current node for convenience.
   */
  public ASTNode minus(ASTNode ast) {
    return new ASTNode(ASTFactory.minus(astnode2, ast.toASTNode2()));
  }

  /**
   * Subtracts the given number from this node.
   * 
   * @param real
   *            a double number.
   * @return the current node for convenience.
   */
  public ASTNode minus(double real) {
    return new ASTNode(ASTFactory.minus(astnode2, new ASTCnRealNode(real)));
  }

  /**
   * Subtracts the given integer from this node.
   * 
   * @param integer
   *            an integer number.
   * @return the current node for convenience.
   */
  public ASTNode minus(int integer) {
    return new ASTNode(ASTFactory.minus(astnode2, new ASTCnIntegerNode(integer)));
  }

  /**
   * 
   * @param integer
   * @param unitsID
   * @return
   */
  public ASTNode minus(int integer, String unitsID) {
    ASTCnIntegerNode node = new ASTCnIntegerNode(integer);
    Unit units = new Unit();
    units.setKind(Unit.Kind.valueOf(unitsID));
    node.setUnits(units);
    return new ASTNode(ASTFactory.minus(astnode2, node));
  }

  /**
   * Multiplies this {@link ASTNode} with the given node
   * 
   * @param ast
   *            an {@code ASTNode}
   * @return the current node for convenience.
   */
  public ASTNode multiplyWith(ASTNode ast) {
    return new ASTNode(ASTFactory.multiplyWith(astnode2, ast.toASTNode2()));
  }

  /**
   * Multiplies this {@link ASTNode} with the given nodes, i.e., all given nodes
   * will be children of this node, whose type will be set to {@link Type#TIMES}.
   * 
   * @param nodes
   *            some {@code ASTNode}
   * @return The current node for convenience.
   */
  public ASTNode multiplyWith(ASTNode... nodes) {
    ASTNode2[] list = new ASTNode2[nodes.length + 1];
    list[0] = astnode2;
    for (int i = 0; i < nodes.length; i++) {
      list[i + 1] = nodes[i].toASTNode2();
    }
    astnode2 = ASTFactory.reduceToBinary(ASTFactory.multiplyWith(list));
    return new ASTNode(astnode2);
  }

  /**
   * Multiplies this {@link ASTNode} with the given SBML element.
   * 
   * @param nsb
   *            an SBML element that can be represented by a value.
   * @return the current node for convenience.
   */
  public ASTNode multiplyWith(CallableSBase nsb) {
    ASTCSymbolBaseNode ci = nsb instanceof FunctionDefinition ? 
        new ASTCiFunctionNode() : new ASTCiNumberNode();
    ci.setName(nsb.getId());
    return new ASTNode(ASTFactory.multiplyWith(astnode2, ci));
  }

  /**
   * Adds a given node to this node.
   * 
   * @param ast
   *            an {@code ASTNode}
   * @return the current node for convenience.
   */
  public ASTNode plus(ASTNode ast) {
    return new ASTNode(ASTFactory.plus(astnode2, ast.toASTNode2()));
  }

  /**
   * Adds an SBML element to this node.
   * 
   * @param nsb
   *            an SBML element that can be represented by a value.
   * @return the current node for convenience.
   */
  public ASTNode plus(CallableSBase nsb) {
    ASTCSymbolBaseNode ci = nsb instanceof FunctionDefinition ? 
        new ASTCiFunctionNode() : new ASTCiNumberNode();
    ci.setName(nsb.getId());
    return new ASTNode(ASTFactory.plus(astnode2, ci));
  }

  /**
   * Adds a number to this node.
   * 
   * @param real
   *            a double number.
   * @return the current node for convenience.
   */
  public ASTNode plus(double real) {
    return new ASTNode(ASTFactory.plus(astnode2, new ASTCnRealNode(real)));
  }

  /**
   * Adds an integer number to this node.
   * 
   * @param integer
   *            an integer number.
   * @return the current node for convenience.
   */
  public ASTNode plus(int integer) {
    return new ASTNode(ASTFactory.plus(astnode2, new ASTCnIntegerNode(integer)));
  }

  /**
   * Adds the given node as a child of this ASTNode. This method adds child
   * nodes from right to left.
   * 
   * @param child
   *            an {@code ASTNode}
   */
  public void prependChild(ASTNode child) {
    if (astnode2 instanceof ASTFunction) {
      ((ASTFunction)astnode2).prependChild(child.toASTNode2());
    }
  }

  /**
   * Raises this ASTNode by the power of the value of the given node.
   * 
   * @param exponent
   *            an {@code ASTNode}
   * @return the current node for convenience.
   */
  public ASTNode raiseByThePowerOf(ASTNode exponent) {
    return new ASTNode(ASTFactory.pow(astnode2, exponent.toASTNode2()));
  }

  /**
   * Raises this ASTNode by the power of the value of this named SBase object.
   * 
   * @param nsb
   *            an SBML element that can be represented by a value.
   * @return the current node for convenience.
   */
  public ASTNode raiseByThePowerOf(CallableSBase nsb) {
    ASTCSymbolBaseNode ci = nsb instanceof FunctionDefinition ? 
             new ASTCiFunctionNode() : new ASTCiNumberNode();
    ci.setName(nsb.getId());
    astnode2 = ASTFactory.pow(astnode2, ci);
    return this;
  }

  /**
   * Raises this {@link ASTNode} by the power of the given number.
   * 
   * @param exponent
   *            a double number.
   * @return the current node for convenience.
   */
  public ASTNode raiseByThePowerOf(double exponent) {
    return new ASTNode(ASTFactory.pow(astnode2, new ASTCnRealNode(exponent)));
  }

  /**
   * <p>
   * Reduces this {@link ASTNode} to a binary tree, e.g., if the formula in this
   * {@link ASTNode} is and(x, y, z) then the formula of the reduced node would
   * be and(and(x, y), z).
   * </p>
   * <p>
   * This method is not yet completed. Currently, only {@link Type#PLUS},
   * {@link Type#TIMES}, {@link Type#LOGICAL_AND}, {@link Type#LOGICAL_OR} are
   * touched by the method. All other nodes are left unchanged, but it traverses
   * the entire tree rooted at this node.
   * </p>
   */
  private void reduceToBinary() {
    if (astnode2 instanceof ASTFunction) {
      astnode2 = ASTFactory.reduceToBinary((ASTFunction)astnode2);		
    }
  }

  /**
   * Returns {@code true} if this node or one of its descendants contains some
   * identifier with the given id. This method can be used to scan a formula
   * for a specific parameter or species and detect whether this component is
   * used by this formula. This search is done using a DFS.
   * 
   * @param id
   *            the id of an SBML element.
   * @return {@code true} if this node or one of its descendants contains the
   *            given id.
   */
  public boolean refersTo(String id) {
    return astnode2 instanceof ASTCSymbolBaseNode ? 
        ((ASTCSymbolBaseNode) astnode2).refersTo(id) : false;
  }

  /**
   * Removes child n of this ASTNode. Removing a child from an ASTNode may
   * result in an inaccurate representation.
   * 
   * @param n
   *            the index of the child to remove
   * @return boolean indicating the success or failure of the operation
   * 
   */
  public boolean removeChild(int n) {
    return astnode2 instanceof ASTFunction ? 
        ((ASTFunction)astnode2).removeChild(n) : false;
  }

  /**
   * Replaces occurrences of a name within this ASTNode with the
   * name/value/formula represented by the second argument ASTNode, e.g., if
   * the formula in this ASTNode is x + y; bvar is x and arg is an ASTNode
   * representing the real value 3 ReplaceArgument substitutes 3 for x within
   * this ASTNode.
   * 
   * @param bvar
   *            a string representing the variable name to be substituted
   * @param arg
   *            an ASTNode representing the name/value/formula to substitute
   */
  public void replaceArgument(String bvar, ASTNode arg) {
    if (astnode2 instanceof ASTLambdaFunctionNode) {
      ((ASTLambdaFunctionNode)astnode2).replaceArgument(bvar, arg.toASTNode2());
    }
  }

  /**
   * Replaces the n<sup>th</sup> child of this ASTNode with the given ASTNode.
   * 
   * @param n
   *            long the index of the child to replace
   * @param newChild
   *            {@link ASTNode} to replace the n<sup>th</sup> child
   * @return the element previously at the specified position
   */
  public ASTNode replaceChild(int n, ASTNode newChild) {
    if (astnode2 instanceof ASTFunction) {
      return new ASTNode(((ASTFunction)astnode2).replaceChild(n, newChild.toASTNode2()));
    }
    return null;
  }

  /**
   * Resets the parentSBMLObject to null recursively.
   * 
   * @param removed
   */
  private void resetParentSBMLObject(ASTNode node) {
    node.toASTNode2().unsetParentSBMLObject();
  }

  /**
   * Sets the value of this ASTNode to the given character. If character is
   * one of +, -, *, / or ^, the node type will be set accordingly. For all
   * other characters, the node type will be set to UNKNOWN.
   * 
   * @param value
   *            the character value to which the node's value should be set.
   */
  public void setCharacter(char value) {
    switch (value) {
      case '+':
        setType(Type.PLUS);
        break;
      case '-':
        setType(Type.MINUS);
        break;
      case '*':
        setType(Type.TIMES);
        break;
      case '/':
        setType(Type.DIVIDE);
        break;
      case '^':
        setType(Type.POWER);
        break;
      default:
        setType(Type.UNKNOWN);
        break;
    }
  }

  /**
   * Sets the class name of the mathML element represented by this ASTNode.
   * 
   * @param className
   *            the class name.
   */
  public void setClassName(String className) {
    astnode2.setMathMLClass(className);
  }

  /**
   * 
   * @param definitionURL
   */
  public void setDefinitionURL(String definitionURL) {
    // TODO: DefinitionURL type is set automatically for ASTCSymbolBaseNodes
  }

  /**
   * Sets the encoding of the mathML element represented by this ASTNode.
   * 
   * @param encoding
   *            the encoding
   */
  public void setEncoding(String encoding) {
    // TODO: Encoding type is always set to 'text' for ASTCSymbolNodes
  }

  /**
   * Sets the id of the mathML element represented by this ASTNode.
   * 
   * @param id
   *            the id.
   */
  public void setId(String id) {
    astnode2.setId(id);
  }

  /**
   * 
   * @param isSetNumberType
   */
  public void setIsSetNumberType(boolean isSetNumberType) {
    // TODO: Number type set automatically for ASTCnNumberNodes. 
  }

  /**
   * Sets the value of this {@link ASTNode} to the given name.
   * 
   * The node type will be set (to {@link Type#NAME}) only if the {@link ASTNode} was previously an
   * operator ({@code isOperator(node) == true}) or number ({@code isNumber(node) == true}).
   * This allows names to be set for {@link Type#FUNCTION}s and the like.
   * 
   * @param name
   */
  // TODO: javadoc not synchronized with the code, we are not using
  // isOperator() or isNumber() but may be we should.
  public void setName(String name) {
    if (astnode2 instanceof ASTFunction) {
      ((ASTFunction)astnode2).setName(name);
    } else if (astnode2 instanceof ASTCSymbolBaseNode) {
      ((ASTCSymbolBaseNode)astnode2).setName(name);		  
    }
  }

  /**
   * Sets the style of the mathML element represented by this {@link ASTNode}.
   * 
   * @param style
   *            the style.
   */
  public void setStyle(String style) {
    astnode2.setStyle(style);
  }

  /**
   * Sets the type of this ASTNode to the given Type. A side-effect of doing
   * this is that any numerical values previously stored in this node are
   * reset to zero.
   * 
   * @param type
   *            the type to which this node should be set
   */
  public void setType(ASTNode.Type type) {
    switch(type) {
      case LAMBDA:
        astnode2 = new ASTLambdaFunctionNode();
        break;
      case LOGICAL_AND:
        astnode2 = new ASTLogicalOperatorNode(Type.LOGICAL_AND);
        break;
      case LOGICAL_OR:
        astnode2 = new ASTLogicalOperatorNode(Type.LOGICAL_OR);
        break;
      case LOGICAL_NOT:
        astnode2 = new ASTLogicalOperatorNode(Type.LOGICAL_NOT);
        break;
      case LOGICAL_XOR:
        astnode2 = new ASTLogicalOperatorNode(Type.LOGICAL_XOR);
        break;
      case RATIONAL:
        astnode2 = new ASTCnRationalNode();
        break;
      case FUNCTION:
        astnode2 = new ASTCiFunctionNode();
        break;
      case FUNCTION_ARCCSC:
        astnode2 = new ASTTrigonometricNode(Type.FUNCTION_ARCCSC);
        break;
      case FUNCTION_ARCCOS:
        astnode2 = new ASTTrigonometricNode(Type.FUNCTION_ARCCOS);
        break;
      case FUNCTION_ARCCOT:
        astnode2 = new ASTTrigonometricNode(Type.FUNCTION_ARCCOT);
        break;
      case FUNCTION_ARCSIN:
        astnode2 = new ASTTrigonometricNode(Type.FUNCTION_ARCSIN);
        break;
      case FUNCTION_ARCSEC:
        astnode2 = new ASTTrigonometricNode(Type.FUNCTION_ARCSEC);
        break;
      case FUNCTION_ARCTAN:
        astnode2 = new ASTTrigonometricNode(Type.FUNCTION_ARCTAN);
        break;
      case FUNCTION_CSC:
        astnode2 = new ASTTrigonometricNode(Type.FUNCTION_CSC);
        break;
      case FUNCTION_COS:
        astnode2 = new ASTTrigonometricNode(Type.FUNCTION_COS);
        break;
      case FUNCTION_COT:
        astnode2 = new ASTTrigonometricNode(Type.FUNCTION_COT);
        break;
      case FUNCTION_SIN:
        astnode2 = new ASTTrigonometricNode(Type.FUNCTION_SIN);
        break;
      case FUNCTION_SEC:
        astnode2 = new ASTTrigonometricNode(Type.FUNCTION_SEC);
        break;
      case FUNCTION_TAN:
        astnode2 = new ASTTrigonometricNode(Type.FUNCTION_TAN);
        break;
      case FUNCTION_ARCCSCH:
        astnode2 = new ASTHyperbolicNode(Type.FUNCTION_ARCCSCH);
        break;
      case FUNCTION_ARCCOSH:
        astnode2 = new ASTHyperbolicNode(Type.FUNCTION_ARCCOSH);
        break;
      case FUNCTION_ARCCOTH:
        astnode2 = new ASTHyperbolicNode(Type.FUNCTION_ARCCOTH);
        break;
      case FUNCTION_ARCSINH:
        astnode2 = new ASTHyperbolicNode(Type.FUNCTION_ARCSINH);
        break;
      case FUNCTION_ARCSECH:
        astnode2 = new ASTHyperbolicNode(Type.FUNCTION_ARCSECH);
        break;
      case FUNCTION_ARCTANH:
        astnode2 = new ASTHyperbolicNode(Type.FUNCTION_ARCTANH);
        break;
      case FUNCTION_CSCH:
        astnode2 = new ASTHyperbolicNode(Type.FUNCTION_CSCH);
        break;
      case FUNCTION_COSH:
        astnode2 = new ASTHyperbolicNode(Type.FUNCTION_COSH);
        break;
      case FUNCTION_COTH:
        astnode2 = new ASTHyperbolicNode(Type.FUNCTION_COTH);
        break;
      case FUNCTION_SINH:
        astnode2 = new ASTHyperbolicNode(Type.FUNCTION_SINH);
        break;
      case FUNCTION_SECH:
        astnode2 = new ASTHyperbolicNode(Type.FUNCTION_SECH);
        break;
      case FUNCTION_TANH:
        astnode2 = new ASTHyperbolicNode(Type.FUNCTION_TANH);
        break;
      default:
        break;
    }
  }

  /**
   * Sets the type from a String. The method accept all the supported mathML
   * elements, the possible types of cn elements or the possible definitionURL
   * of csymbol elements.
   * 
   * @param typeStr
   *            the type as a String.
   */
  public void setType(String typeStr) {
    astnode2.setType(typeStr);
  }

  /**
   * Sets the units attribute.
   * 
   * @param unitId
   * @throws IllegalArgumentException
   *             if the ASTNode is not a kind of numbers (&lt;cn&gt; in mathml) or
   *             if the {@code unitId} is not a valid unit kind or the id
   *             of a unit definition.
   */
  public void setUnits(String unitId) {
    Unit units = new Unit();
    units.setKind(Unit.Kind.valueOf(unitId));
    ((ASTCnNumberNode<?>)astnode2).setUnits(units);
  }

  /**
   * 
   * @param unit
   */
  public void setUnits(Unit.Kind unit) {
    Unit units = new Unit();
    units.setKind(unit);
    ((ASTCnNumberNode<?>)astnode2).setUnits(units);
  }

  /**
   * 
   * @param ud
   */
  public void setUnits(UnitDefinition ud) {
    ((ASTCnNumberNode<?>)astnode2).setUnits(ud.getListOfUnits().get(0));
  }

  /**
   * Sets the value of this ASTNode to the given double number and sets the
   * node type to REAL.
   * 
   * This is functionally equivalent to:
   * 
   * <pre class="brush:java">
   * setValue(value, 0);
   * </pre>
   * 
   * @param value
   *            the double format number to which this node's value should be
   *            set
   */
  public void setValue(double value) {
    if (astnode2 instanceof ASTCnRealNode) {
      ((ASTCnRealNode)astnode2).setReal(value);
    }
  }

  /**
   * Sets the value of this ASTNode to the given real (double) in two parts:
   * the mantissa and the exponent. The node type is set to REAL_E.
   * 
   * @param mantissa
   *            the mantissa of this node's real-numbered value
   * @param exponent
   *            the exponent of this node's real-numbered value
   */
  public void setValue(double mantissa, int exponent) {
    if (astnode2 instanceof ASTCnExponentialNode) {
      ((ASTCnExponentialNode)astnode2).setMantissa(mantissa);
      ((ASTCnExponentialNode)astnode2).setExponent(exponent);
    }
  }

  /**
   * Sets the value of this ASTNode to the given (long) integer and sets the
   * node type to INTEGER.
   * 
   * @param value
   */
  public void setValue(int value) {
    if (astnode2 instanceof ASTCnIntegerNode) {
      ((ASTCnIntegerNode)astnode2).setInteger(value);
    }
  }

  /**
   * Sets the value of this ASTNode to the given rational in two parts: the
   * numerator and denominator. The node type is set to RATIONAL.
   * 
   * @param numerator
   *            the numerator value of the rational
   * @param denominator
   *            the denominator value of the rational
   */
  public void setValue(int numerator, int denominator) {
    if (astnode2 instanceof ASTCnRationalNode) {
      ((ASTCnRationalNode)astnode2).setNumerator(numerator);
      ((ASTCnRationalNode)astnode2).setDenominator(denominator);		  
    }
  }

  /**
   * Allows you to directly set an instance of {@link CallableSBase} as the
   * variable of this {@link ASTNode}. Note that if the given variable
   * does not have a declared {@code id} field, the pointer to this variable
   * will get lost when cloning this node. Only references to identifiers are
   * permanently stored. The pointer can also not be written to an SBML file
   * without a valid identifier.
   * 
   * @param variable a pointer to a {@link CallableSBase}.
   */
  public void setVariable(CallableSBase variable) {
    if (astnode2 instanceof ASTCiNumberNode) {
      ((ASTCiNumberNode)astnode2).setRefId(variable.getId());
    } else if (astnode2 instanceof ASTCiFunctionNode) {
      ((ASTCiFunctionNode)astnode2).setRefId(variable.getId());
    }
  }

  /**
   * Applies the square root function on this syntax tree and returns the
   * resulting tree.
   * 
   * @return the current node for convenience.
   */
  public ASTNode sqrt() {
    return new ASTNode(ASTFactory.sqrt(astnode2));
  }

  /**
   * <p>
   * Swaps the children of this {@link ASTNode} with the children of that
   * {@link ASTNode}.
   * </p>
   * <p>
   * Unfortunately, when swapping child nodes, we have to recursively traverse
   * the entire subtrees in order to make sure that all pointers to the parent
   * SBML object are correct. However, this must only be done if the parent SBML
   * object of that differs from the one surrounding this node.
   * </p>
   * <p>
   * In any case, the pointer from each sub-node to its parent must be changed.
   * In contrast to other SBML elements, {@link ASTNode}s have sub-nodes as
   * direct children, i.e., there is no child called 'ListOfNodes'. The
   * {@code setParent} method is also not recursive.
   * </p>
   * <p>
   * However, this might cause many calls to listeners.
   * </p>
   * 
   * @param that
   *        the other node whose children should be used to replace this
   *        node's children
   */
  public void swapChildren(ASTNode that) {
    if (astnode2 instanceof ASTFunction && that.toASTNode2() instanceof ASTFunction) {
      ((ASTFunction)astnode2).swapChildren((ASTFunction) that.toASTNode2());
    }
  }

  /**
   * Return the {@link ASTNode2} corresponding to the current {@link ASTNode}
   * @return node {@link ASTNode2}
   */
  public ASTNode2 toASTNode2() {
    return astnode2;
  }

  /**
   * <p>
   * Converts this ASTNode to a text string using a specific syntax for
   * mathematical formulas.
   * </p>
   * <p>
   * The text-string form of mathematical formulas produced by
   * formulaToString() and read by parseFormula() are simple C-inspired infix
   * notation taken from SBML Level 1. A formula in this text-string form
   * therefore can be handed to a program that understands SBML Level 1
   * mathematical expressions, or used as part of a formula translation
   * system. The syntax is described in detail in the documentation for
   * ASTNode.
   * </p>
   * 
   * @return the formula from the given AST as an SBML Level 1 text-string
   *         mathematical formula. The caller owns the returned string and is
   *         responsible for freeing it when it is no longer needed. {@code null} is
   *         returned if the given argument is {@code null}.
   * @throws SBMLException
   *             if there is a problem in the ASTNode tree.
   */
  public String toFormula() throws SBMLException {
    return compile(new FormulaCompiler()).toString();
  }

  /**
   * Converts this node recursively into a LaTeX formatted String.
   * 
   * @return A String representing the LaTeX code necessary to write the
   *         formula corresponding to this node in a document.
   * @throws SBMLException
   *             if there is a problem in the ASTNode tree.
   */
  public String toLaTeX() throws SBMLException {
    return compile(new LaTeXCompiler()).toString();
  }

  /**
   * Converts this node recursively into a MathML string that corresponds to
   * the subset of MathML defined in the SBML specification.
   * 
   * @return the representation of this node in MathML.
   */
  public String toMathML() {
    try {
      return MathMLXMLStreamCompiler.toMathML(this);
    } catch (RuntimeException e) {
      // added to prevent a crash when we cannot create the mathML
      // TODO: log the exception
      // e.printStackTrace();
    }
    return "";
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    String formula = "";
    String errorMsg = "Could not compile ASTNode to formula: ";
    try {
      formula = compile(new FormulaCompiler()).toString();
    } catch (SBMLException e) {
      // log the exception
      e.printStackTrace();

      if (logger.isDebugEnabled()) {
        logger.error(errorMsg, e);
      } else {
        // TODO: Do not print this message if parsing the file !!! Or remove it
        logger.warn(errorMsg + e.getMessage());
      }
    } catch (RuntimeException e) {
      // added to prevent a crash when we cannot create the formula
      if (logger.isDebugEnabled()) {
        logger.error(errorMsg, e);
      }
    }
    return formula;
  }

  /**
   * Unset the units attribute.
   * 
   */
  public void unsetUnits() {
    ((ASTCnNumberNode<?>)astnode2).unsetUnits();
  }

  /**
   * For a better performance {@link ASTNode}s can store a direct pointer to a
   * variable element. This is particularly useful when performing more
   * complex computation on these data structures. However, if the model is
   * changed, it may happen that these pointer become invalid. For instance, a
   * previously local parameter may be added to the model in form of a global
   * parameter while keeping the same identifier. The local parameter may then
   * be removed. Whenever performing changes like this, you may want to update
   * pointers within {@link ASTNode} constructs as well.
   */
  public void updateVariables() {
   // TODO: Of no utility anymore as none of the new ci nodes store a direct pointer
   // to whatever they are referencing. 
  }

}
