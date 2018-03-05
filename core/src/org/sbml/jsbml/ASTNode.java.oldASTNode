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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.swing.tree.TreeNode;
import javax.xml.stream.XMLStreamException;

import org.apache.log4j.Logger;
import org.sbml.jsbml.ext.ASTNodePlugin;
import org.sbml.jsbml.math.ASTFactory;
import org.sbml.jsbml.math.ASTNode2;
import org.sbml.jsbml.text.parser.FormulaParser;
import org.sbml.jsbml.text.parser.FormulaParserLL3;
import org.sbml.jsbml.text.parser.IFormulaParser;
import org.sbml.jsbml.text.parser.ParseException;
import org.sbml.jsbml.util.Maths;
import org.sbml.jsbml.util.TreeNodeChangeEvent;
import org.sbml.jsbml.util.compilers.ASTNodeCompiler;
import org.sbml.jsbml.util.compilers.ASTNodeValue;
import org.sbml.jsbml.util.compilers.FormulaCompiler;
import org.sbml.jsbml.util.compilers.FormulaCompilerLibSBML;
import org.sbml.jsbml.util.compilers.LaTeXCompiler;
import org.sbml.jsbml.util.compilers.MathMLXMLStreamCompiler;
import org.sbml.jsbml.util.compilers.UnitsCompiler;
import org.sbml.jsbml.util.filters.Filter;
import org.sbml.jsbml.xml.XMLNode;
import org.sbml.jsbml.xml.parsers.PackageParser;
import org.sbml.jsbml.xml.parsers.ParserManager;
import org.sbml.jsbml.xml.stax.SBMLReader;

/**
 * A node in the Abstract Syntax Tree (AST) representation of a mathematical
 * expression.
 * 
 * @author Andreas Dr&auml;ger
 * @author Nicolas Rodriguez
 * @author Alexander D&ouml;rr
 * @since 0.8
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
    CONSTRUCTOR_PIECE,
    /**
     *
     */
    CONSTRUCTOR_OTHERWISE,
    /**
     * 
     */
    CONSTANT_TRUE,
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
    FUNCTION_MAX,
    /**
     * 
     */
    FUNCTION_MIN,
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
     * The quotient element is the operator used for division modulo a particular base.
     * When the quotient operator is applied to integer arguments a and b, the result is
     * the "quotient of a divided by b". That is, quotient returns the unique integer q
     * such that a = q b + r. (In common usage, q is called the quotient and r is the remainder.)
     */
    FUNCTION_QUOTIENT,
    /**
     * 
     */
    FUNCTION_RATE_OF,
    /**
     * The rem element is the operator that returns the "remainder" of a division modulo
     * a particular base. When the rem operator is applied to integer arguments a and b,
     * the result is the "remainder of a divided by b". That is, rem returns the unique
     * integer, r such that a = q b+ r, where r < q. (In common usage, q is called the
     * quotient and r is the remainder.)
     */
    FUNCTION_REM,
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
     * In classical logic, the compound 'p implies q' (p→q) is logically equivalent to the negative
     * compound: not both p and not q. Thus the compound p→q is false if and only
     * if both p is true and q is false. By the same stroke, p→q is true if and only
     * if either p is false or q is true (or both). Thus → is a function from pairs
     * of truth values of the components p, q to truth values of the compound p→q,
     * whose truth value is entirely a function of the truth values of the components.
     * Hence, this interpretation is called truth-functional. The compound p→q is
     * logically equivalent also to ¬p∨q (either not p, or q (or both)), and
     * to ¬q→¬p (if not q then not p). But it is not equivalent to ¬p→¬q, which is
     * equivalent to q→p.
     * 
     *<p>Truth table:
     *
     *<pre>
     * p   q  p→q
     * 
     * T   T   T
     * T   F   F
     * F   T   T
     * F   F   T
     *</pre>
     *
     */
    LOGICAL_IMPLIES,
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
     * MathML markup can be combined with other markup languages, and these
     * mixing constructions are realized by the semantic annotation elements.
     * The semantic annotation elements provide an important tool for making
     * associations between alternate representations of an expression, and for
     * associating semantic properties and other attributions with a MathML
     * expression.
     */
    SEMANTICS,
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
      } else if (type.equals("max")) {
        return FUNCTION_MAX;
      } else if (type.equals("min")) {
        return FUNCTION_MIN;
      } else if (type.equals("quotient")) {
        return FUNCTION_QUOTIENT;
      } else if (type.equals("rem")) {
        return FUNCTION_REM;
      }

      // Logical operators
      else if (type.equals("and")) {
        return LOGICAL_AND;
      } else if (type.equals("or")) {
        return LOGICAL_OR;
      } else if (type.equals("xor")) {
        return LOGICAL_XOR;
      } else if (type.equals("not")) {
        return LOGICAL_NOT;
      } else if (type.equals("implies")) {
        return LOGICAL_IMPLIES;
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
      else if (type.equals("eq")) {
        return RELATIONAL_EQ;
      } else if (type.equals("neq")) {
        return RELATIONAL_NEQ;
      } else if (type.equals("gt")) {
        return RELATIONAL_GT;
      } else if (type.equals("lt")) {
        return RELATIONAL_LT;
      } else if (type.equals("geq")) {
        return RELATIONAL_GEQ;
      } else if (type.equals("leq")) {
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
      } else if (type.equals(URI_TIME_DEFINITION)) {
        return NAME_TIME;
      } else if (type.equals(URI_DELAY_DEFINITION)) {
        return FUNCTION_DELAY;
      } else if (type.equals(URI_AVOGADRO_DEFINITION)) {
        return NAME_AVOGADRO;
      } else if (type.equals(URI_RATE_OF_DEFINITION)) {
        return FUNCTION_RATE_OF;
      }

      // general: apply, piecewise, piece, otherwise, lambda, bvar
      else if (type.equals("lambda")) {
        return LAMBDA;
      } else if (type.equals("bvar")) {
        // nothing to do, node ignore when parsing
      } else if (type.equals("piecewise")) {
        return FUNCTION_PIECEWISE;
      } else if (type.equals("piece")) {
        return CONSTRUCTOR_PIECE;
      } else if (type.equals("otherwise")) {
        return CONSTRUCTOR_OTHERWISE;
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
   * The URI for the definition of the csymbol for rateOf.
   */
  public static final transient String URI_RATE_OF_DEFINITION = "http://www.sbml.org/sbml/symbols/rateOf";

  /**
   * The URI for the definition of the csymbol for time.
   */
  public static final transient String URI_TIME_DEFINITION = "http://www.sbml.org/sbml/symbols/time";

  /**
   * The String representing the first implementation of the ASTNode, since 0.8.
   */
  public static final String IMPLEMENTATION_HISTORIC = "old ASTNode";

  /**
   * The String representing the facade implementation for the new {@link ASTNode2} implementation.
   */
  public static final String IMPLEMENTATION_ASTNODE2 = "new ASTNode2";

  /**
   * The String representing the actual implementation of this class, values can be {@link #IMPLEMENTATION_HISTORIC}
   * or {@link #IMPLEMENTATION_ASTNODE2}.
   */
  public static final String IMPLEMENTATION_VERSION = IMPLEMENTATION_HISTORIC;


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
    ASTNode abs = new ASTNode(Type.FUNCTION_ABS, parent);
    abs.addChild(new ASTNode(d, parent));
    return abs;
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
    ASTNode abs = new ASTNode(Type.FUNCTION_ABS, parent);
    abs.addChild(new ASTNode(integer, parent));
    return abs;
  }

  /**
   * Creates a new {@link ASTNode} of type {@code operator} and adds the given nodes as children.
   * 
   * @param operator the type of arithmetic operation
   * @param ast the children of the new ASTNode
   * @return a new {@link ASTNode} of type {@code operator} and adds the given nodes as children.
   */
  private static ASTNode arithmethicOperation(Type operator, ASTNode... ast) {
    ArrayList<ASTNode> astList = new ArrayList<ASTNode>();
    if (ast != null) {
      for (ASTNode node : ast) {
        if ((node != null)
            && !((operator == Type.TIMES) && node.isOne() && (ast.length > 1))) {
          astList.add(node);
        }
      }
    }
    if (astList.size() == 0) {
      return null;
    }
    if (astList.size() == 1) {
      return astList.get(0).clone();
    }
    if ((operator == Type.PLUS) || (operator == Type.MINUS)
        || (operator == Type.TIMES) || (operator == Type.DIVIDE)
        || (operator == Type.POWER)) {
      MathContainer mc = astList.get(0).parentSBMLObject;
      ASTNode arithmetic = new ASTNode(operator, mc);
      for (ASTNode nodes : astList) {
        arithmetic.addChild(nodes);
        setParentSBMLObject(nodes, mc, 0);
      }
      if (arithmetic.getChildCount() > 2) {
        arithmetic.reduceToBinary();
      }
      return arithmetic;
    } else {
      throw new IllegalArgumentException(MessageFormat.format(
        resourceBundle.getString("ASTNode.INVALID_OPERATOR_MSG"), operator));
    }
  }

  /**
   * Creates a new {@link ASTNode} of type MINUS and adds the given nodes as children
   * 
   * @param ast the children of the new ASTNode
   * @return a new {@link ASTNode} of type MINUS and adds the given nodes as children
   */
  public static ASTNode diff(ASTNode... ast) {
    return arithmethicOperation(Type.MINUS, ast);
  }

  /**
   * Creates a new {@link ASTNode} of type RELATIONAL_EQ.
   * 
   * @param left the left child.
   * @param right the right child.
   * @return a new {@link ASTNode} of type RELATIONAL_EQ.
   */
  public static ASTNode eq(ASTNode left, ASTNode right) {
    return relational(Type.RELATIONAL_EQ, left, right);
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
    ASTNode e = new ASTNode(Type.CONSTANT_E, exponent.getParentSBMLObject());
    return e.raiseByThePowerOf(exponent);
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
   * @throws SBMLException if the {@link ASTNode} is not well formed.
   * @see #toFormula()
   * 
   */
  public static String formulaToString(ASTNode tree) throws SBMLException {
    if (tree == null) {
      return null;
    }

    return tree.toFormula();
  }

  /**
   * Returns the formula from the given {@link ASTNode} as an
   * infix mathematical formula produce by the given {@link FormulaCompiler}.
   * 
   * 
   * @param tree
   *            the root of the ASTNode formula expression tree
   * @param compiler the {@link FormulaCompiler} to use
   * @return the formula from the given AST as an infix
   *         mathematical formula. {@code null} is
   *         returned if one of the given arguments is {@code null}.
   * @throws SBMLException if the {@link ASTNode} is not well formed.
   * @see #toFormula()
   * @see FormulaCompiler
   * @see FormulaCompilerLibSBML
   * 
   */
  public static String formulaToString(ASTNode tree, FormulaCompiler compiler) throws SBMLException {
    if (tree == null || compiler == null) {
      return null;
    }

    return tree.toFormula(compiler);
  }


  /**
   * Creates a new {@link ASTNode} of type DIVIDE with the given nodes as children.
   * 
   * @param numerator the numerator
   * @param denominator the denominator
   * @return a new {@link ASTNode} of type DIVIDE with the given nodes as children.
   */
  public static ASTNode frac(ASTNode numerator, ASTNode denominator) {
    return numerator.divideBy(denominator);
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
    return relational(Type.RELATIONAL_GEQ, left, right);
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
    return relational(Type.RELATIONAL_GT, left, right);
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
    return relational(Type.RELATIONAL_LEQ, left, right);
  }

  /**
   * Creates an {@link ASTNode} representing a logarithm to base 10 of the given value.
   * 
   * @param value the value which is the argument of the logarithm.
   * @return an {@link ASTNode} representing a logarithm to base 10 of the given value.
   */
  public static ASTNode log(ASTNode value) {
    return log(null, value);
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
    if (value == null) {
      throw new NullPointerException(resourceBundle.getString("ASTNode.log"));
    }
    ASTNode log = new ASTNode(Type.FUNCTION_LOG, value
      .getParentSBMLObject());
    if (base != null) {
      log.addChild(base);
    }
    log.addChild(value);
    setParentSBMLObject(log, log.getParentSBMLObject(), 0);
    return log;
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
    return relational(Type.RELATIONAL_LT, left, right);
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
    return relational(Type.RELATIONAL_NEQ, left, right);
  }

  /**
   * Parses a text-string mathematical formula and returns a representation as
   * an Abstract Syntax Tree.
   * 
   * <p>
   * Support the syntax defined in {@link FormulaParserLL3} which is almost the same syntax as defined in
   * <a href="http://sbml.org/Software/libSBML/docs/java-api/org/sbml/libsbml/libsbml.html#parseL3Formula(java.lang.String)">
   * the LibSBML L3 parser</a>. The things not supported for now are the units associated with numbers.
   *
   * <p>
   * Parsing of the various MathML functions and constants are all
   * case-sensitive by default: names such as
   * {@code Cos} and {@code COS} are not parsed as the MathML cosine
   * operator, {@code <cos>}. As well, if you have an SBML entities (species, parameter, ...) that
   * has an id identical to one of the supported mathML element, the parser will interpret the String as the
   * mathML element and not the SBML entity.
   * 
   * <p> You can change this behaviour by using the {@link FormulaParserLL3#setCaseSensitive(boolean)}
   * method and using the {@link ASTNode#parseFormula(String, IFormulaParser)} method instead of this one:
<p><blockquote><pre>
   FormulaParserLL3 caseSensitiveParser = new FormulaParserLL3(new StringReader(""));
   caseInsensitiveParser.setCaseSensitive(false);
   ASTNode n = ASTNode.parseFormula("Cos(x)", caseInsensitiveParser);
</pre></blockquote></p>
   *
   * <p> This method has a different behaviour since JSBML-1.0 compare to JSBML-0.8. There is a different
   * operator precedence, the parsing is now case sensitive for mathML elements and boolean operators are
   * now differently interpreted: '&amp;&amp;' and '||' are used instead of 'and' and 'or'.<br>
   * If you want to use the parser used in JSBML-0.8, you can do that by using the {@link FormulaParser}
   * parser class and using the {@link ASTNode#parseFormula(String, IFormulaParser)} method instead of this one:
<p><blockquote><pre>
   FormulaParser oldParser = new FormulaParser(new StringReader(""));
   ASTNode n = ASTNode.parseFormula("x and y", oldParser);
</pre></blockquote></p>
   * 
   * <p> If you are not satisfied with the behavior of the existing parsers, you can create
   * your own, you just need to implement the {@link IFormulaParser} interface.
   * 
   * @param formula
   *            a text-string mathematical formula.
   * @return an {@link ASTNode} representing the formula.
   * @throws ParseException
   *             If the given formula is not of valid format or cannot be
   *             parsed for other reasons.
   * @see ASTNode#parseFormula(String, IFormulaParser)
   * @see FormulaParserLL3
   * @see FormulaParser
   */
  public static ASTNode parseFormula(String formula) throws ParseException {
    FormulaParserLL3 parser = new FormulaParserLL3(new StringReader(formula));
    ASTNode result = null;

    try {
      result = parser.parse();
    } catch (Throwable e) {
      // the javacc parser can throw some TokenMgrError at least
      throw new ParseException(e);
    }

    return result;
  }

  /**
   * Parses a text-string mathematical formula, using the given {@link IFormulaParser} and returns a representation as
   * an Abstract Syntax Tree.
   * 
   * <p> Support the syntax defined in the {@link IFormulaParser} provided.
   *
   * <p> You can for example use you own parser or use an existing parser with different options.
   * 
   * <p>For example, you can change the behaviour of the {@link FormulaParserLL3} class by using the
   * {@link FormulaParserLL3#setCaseSensitive(boolean)} method:
<p><blockquote><pre>
   FormulaParserLL3 caseSensitiveParser = new FormulaParserLL3(new StringReader(""));
   caseInsensitiveParser.setCaseSensitive(false);
   ASTNode n = ASTNode.parseFormula("Cos(x)", caseInsensitiveParser);
</pre></blockquote></p>
   *
   * <p> You can as well use the {@link FormulaParser} class to get the same parsing behaviour as in JSBML-0.8:
<p><blockquote><pre>
   FormulaParser oldParser = new FormulaParser(new StringReader(""));
   ASTNode n = ASTNode.parseFormula("x and y", oldParser);
</pre></blockquote></p>
   * 
   * <p> If you are not satisfied with the behaviour of the existing parsers, you can create
   * your own, you just need to implement the {@link IFormulaParser} interface.
   * 
   * 
   * @param formula
   *            a text-string mathematical formula.
   * @param parser
   *            a formula parser.
   * @return an {@link ASTNode} representing the formula.
   * @throws ParseException
   *             If the given formula is not of valid format or cannot be
   *             parsed for other reasons.
   * @see ASTNode#parseFormula(String)
   * @see FormulaParserLL3
   * @see FormulaParser
   */
  public static ASTNode parseFormula(String formula, IFormulaParser parser) throws ParseException {
    parser.ReInit(new StringReader(formula));
    ASTNode result = null;

    try {
      result = parser.parse();
    } catch (Throwable e) {
      // the javacc parser can throw some TokenMgrError at least
      throw new ParseException(e);
    }

    return result;
  }

  /**
   * @param mathML a {@link String} in MathML format.
   * @return An {@link ASTNode} representation of the given MathML {@link String}.
   * @see #readMathMLFromString(String)
   */
  public static ASTNode parseMathML(String mathML) {
    return readMathMLFromString(mathML);
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
    return piecewise(node.getParentSBMLObject(), nodes);
  }

  /**
   * @param parentSBMLObject
   *        a link to the container in which this {@link ASTNode} will be
   *        inserted. This can be useful if complex operations on units,
   *        species, etc. are necessary when creating this formula.
   *        Can be {@code null}.
   * @param nodes
   * @return
   * @see #piecewise(ASTNode, ASTNode...)
   */
  public static ASTNode piecewise(MathContainer parentSBMLObject,
    ASTNode[] nodes) {
    ASTNode piecewise = new ASTNode(Type.FUNCTION_PIECEWISE, parentSBMLObject);
    for (ASTNode n : nodes) {
      piecewise.addChild(n);
    }
    if (nodes.length > 0) {
      setParentSBMLObject(piecewise, piecewise.getParentSBMLObject(), 0);
    }
    return piecewise;
  }

  /**
   * Creates a power {@link ASTNode}.
   * 
   * @param basis the basis
   * @param exponent the exponent
   * @return a power {@link ASTNode}.
   */
  public static ASTNode pow(ASTNode basis, ASTNode exponent) {
    if (!(exponent.isInteger() && exponent.getInteger() == 1)
        && !(exponent.getType() == Type.REAL && exponent.getReal() == 1d)) {
      if ((exponent.isInteger() && exponent.getInteger() == 0)
          || (exponent.getType() == Type.REAL && exponent.getReal() == 0d)) {
        basis = new ASTNode(1, basis.getParentSBMLObject());
      } else {
        setParentSBMLObject(exponent, basis.getParentSBMLObject(), 0);
        basis.raiseByThePowerOf(exponent);
      }
    }
    return basis;
  }

  /**
   * Creates a power {@link ASTNode}.
   * 
   * @param basis the basis
   * @param exponent the exponent
   * @return a power {@link ASTNode}.
   */
  public static ASTNode pow(ASTNode basis, double exponent) {
    basis.raiseByThePowerOf(exponent);
    return basis;
  }

  /**
   * Creates a power {@link ASTNode}.
   * 
   * @param basis the basis
   * @param exponent the exponent
   * @return a power {@link ASTNode}.
   */
  public static ASTNode pow(ASTNode basis, int exponent) {
    basis.raiseByThePowerOf(exponent);
    return basis;
  }

  /**
   * Raises the given basis by the power of the given exponent.
   * 
   * @param container the parent object
   * @param basis the basis
   * @param exponent the exponent
   * @return a power {@link ASTNode}.
   */
  public static ASTNode pow(MathContainer container,
    CallableSBase basis, CallableSBase exponent) {
    return pow(new ASTNode(basis, container), new ASTNode(exponent,
      container));
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
  private static ASTNode relational(Type type, ASTNode left, ASTNode right) {
    if ((left == null) || (right == null)) {
      throw new NullPointerException(resourceBundle.getString("ASTNode.relational"));
    }
    ASTNode relational = new ASTNode(type, left.getParentSBMLObject());
    relational.addChild(left);
    relational.addChild(right);
    setParentSBMLObject(relational, left.getParentSBMLObject(), 0);
    return relational;
  }

  /**
   * Creates a root {@link ASTNode}.
   * 
   * @param radicand the radicand
   * @param rootExponent the exponent of the root element.
   * @return a root {@link ASTNode}.
   */
  public static ASTNode root(ASTNode rootExponent, ASTNode radicand) {
    ASTNode root = new ASTNode(Type.FUNCTION_ROOT, radicand
      .getParentSBMLObject());
    root.addChild(rootExponent);
    root.addChild(radicand);
    setParentSBMLObject(rootExponent, radicand.getParentSBMLObject(), 0);
    return root;
  }

  /**
   * Sets the Parent of the node and its children to the given value
   * 
   * @param node the orphan node
   * @param parent the parent
   */
  static void setParentSBMLObject(ASTNode node, MathContainer parent) {
    node.setParent(parent);
    setParentSBMLObject(node, parent, 0);
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
    node.parentSBMLObject = parent;
    for (ASTNode child : node.listOfNodes) {
      setParentSBMLObject(child, parent, depth + 1);
    }
  }

  /**
   * Creates a root {@link ASTNode}.
   * 
   * @param radicand
   * @return a root {@link ASTNode}.
   */
  public static ASTNode sqrt(ASTNode radicand) {
    return root(new ASTNode(2, radicand.getParentSBMLObject()), radicand);
  }

  /**
   * Creates a new {@link ASTNode} of type Plus with the given nodes as children.
   * 
   * @param ast the children nodes.
   * @return a new {@link ASTNode} of type Plus with the given nodes as children.
   */
  public static ASTNode sum(ASTNode... ast) {
    return arithmethicOperation(Type.PLUS, ast);
  }

  /**
   * Sum of several NamedSBase objects.
   * 
   * @param parent the parent
   * @param sbase
   * @return the sum of several NamedSBase objects.
   */
  public static ASTNode sum(MathContainer parent,
    CallableSBase... sbase) {
    ASTNode elements[] = new ASTNode[sbase.length];
    for (int i = 0; i < sbase.length; i++) {
      elements[i] = new ASTNode(sbase[i], parent);
    }
    return sum(elements);
  }

  /**
   * Creates an {@link ASTNode} of type times and adds the given nodes as children.
   * 
   * @param ast
   * @return an {@link ASTNode} of type times and adds the given nodes as children.
   */
  public static ASTNode times(ASTNode... ast) {
    return arithmethicOperation(Type.TIMES, ast);
  }

  /**
   * Multiplies several {@link CallableSBase} objects.
   * 
   * @param parent
   * @param sbase
   * @return the multiplication of several {@link CallableSBase} objects.
   */
  public static ASTNode times(MathContainer parent,
    CallableSBase... sbase) {
    ASTNode elements[] = new ASTNode[sbase.length];
    for (int i = 0; i < sbase.length; i++) {
      elements[i] = new ASTNode(sbase[i], parent);
    }
    return times(elements);
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
    ASTNode um = new ASTNode(Type.MINUS, ast.getParentSBMLObject());
    um.addChild(ast);
    return um;
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
  public static ASTNode uMinus(MathContainer container,
    CallableSBase sbase) {
    return uMinus(new ASTNode(sbase, container));
  }

  /**
   * The value of the definitionURL for csymbol element. Level 3 extensions
   * can create new csymbol element that we would not necessary be aware of,
   * so we need to store the attribute value.
   */
  private String definitionURL;

  /**
   * 
   */
  private int denominator;

  /**
   * 
   */
  private int exponent;

  /**
   * Possible attributes for a MathML element
   */
  private String id;
  /**
   * 
   */
  private String style;
  /**
   * 
   */
  private String className;
  /**
   * 
   */
  private String encoding;

  /**
   * XMLNode that will hold the content of the 'semantics' mathML element.
   */
  private List<XMLNode> semanticsAnnotationList;


  /**
   * Tells if the type attribute of the cn element was set and we need to
   * write it back or if it is set to the default (REAL).
   * 
   */
  private boolean isSetNumberType = false;

  /**
   * Child nodes.
   */
  private List<ASTNode> listOfNodes;

  /**
   * A {@link Logger} for this class.
   */
  private static transient final Logger logger = Logger.getLogger(ASTNode.class);
  private static final transient boolean isDebugEnabled = logger.isDebugEnabled();

  /**
   * The part of a number in scientific format (with an E) that is on the left
   * side of the E (the prefix of the number). For instance, in the number
   * 3.5E-2.7 the mantissa is 3.5.
   */
  private double mantissa;

  /**
   * If no NamedSBase object exists or can be identified when
   * {@link #setName(String)} is called, the given name is stored in this
   * field.
   */
  private String name;

  /**
   * This value stores the numerator if this.isRational() is {@code true}, or the
   * value of an integer if this.isInteger() is {@code true}.
   */
  private int numerator;

  /**
   * The container that holds this ASTNode.
   */
  private MathContainer parentSBMLObject;

  /**
   * The type of this ASTNode.
   */
  private Type type;

  /**
   * Since Level 3 SBML allows to equip numbers with unit identifiers. In this
   * case a reference to an identifier of a {@link UnitDefinition} in the
   * model can be stored here.
   */
  private String unitId;

  /**
   * A direct pointer to a referenced variable. This can save a lot of
   * computation time because it will then not be necessary to query the
   * corresponding model again and again for this variable.
   */
  private CallableSBase variable;

  /**
   * {@link Map} containing the ASTNode plugin object of additional packages
   * with the appropriate name of the package.
   */
  private SortedMap<String, ASTNodePlugin> plugins;

  /**
   * Creates a new {@link ASTNode} of unspecified type and without a pointer
   * to its containing {@link MathContainer}.
   */
  public ASTNode() {
    super();
    parentSBMLObject = null;
    listOfNodes = null;
    initDefaults();
  }

  /**
   * Copy constructor; Creates a deep copy of the given {@link ASTNode}.
   * 
   * @param astNode
   *            the {@link ASTNode} to be copied.
   */
  public ASTNode(ASTNode astNode) {
    super(astNode);
    parentSBMLObject = null;
    initDefaults();

    if (logger.isDebugEnabled()) {
      logger.debug(MessageFormat.format(resourceBundle.getString("ASTNode1"), astNode.type));
    }

    setType(astNode.getType());
    denominator = astNode.denominator;
    exponent = astNode.exponent;
    mantissa = astNode.mantissa;
    name = astNode.name == null ? null : new String(astNode.name);
    variable = null; // the clone is not linked anymore to any model so we cannot have any 'variable' set
    numerator = astNode.numerator;
    unitId = astNode.unitId == null ? null : new String(astNode.unitId);
    definitionURL = astNode.definitionURL;
    id = astNode.id;
    className = astNode.className;
    encoding = astNode.encoding;
    style = astNode.style;

    if (astNode.getChildCount() > 0) {
      for (ASTNode child : astNode.listOfNodes) {
        ASTNode c = child.clone();
        c.parent = this;
        listOfNodes.add(c);
      }
    }

    if (astNode.semanticsAnnotationList != null) {
      semanticsAnnotationList = new ArrayList<XMLNode>();

      for (XMLNode semanticsAnnotation : astNode.semanticsAnnotationList) {
        semanticsAnnotationList.add(semanticsAnnotation.clone());
      }
    }
  }

  /**
   * Creates and returns a new {@link ASTNode} referring to the given {@link CallableSBase}.
   * @param nsb
   */
  public ASTNode(CallableSBase nsb) {
    this(Type.NAME);
    setVariable(nsb);
  }

  /**
   * Create a new node of type {@link ASTNode} from the given {@link ASTNode2}
   *
   * @param node
   *          {@link ASTNode2}
   */
  public ASTNode(ASTNode2 node) {
    // TODO - check the different classes of ASTNode2 to set the proper ASTNode fields
  }

  /**
   * Creates and returns a new {@link ASTNode} referring to the given {@link CallableSBase}.
   * 
   * @param nsb
   * @param parent
   */
  public ASTNode(CallableSBase nsb, MathContainer parent) {
    this(Type.NAME, parent);
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
    this(Type.REAL);
    setValue(real);
  }

  /**
   * Creates and returns a new {@link ASTNode}.
   * 
   * @param mantissa
   * @param exponent
   */
  public ASTNode(double mantissa, int exponent) {
    this(Type.REAL_E);
    setValue(mantissa, exponent);
  }

  /**
   * Creates and returns a new {@link ASTNode}.
   * 
   * @param mantissa
   * @param exponent
   * @param parent
   */
  public ASTNode(double mantissa, int exponent, MathContainer parent) {
    this(Type.REAL_E, parent);
    setValue(mantissa, exponent);
  }

  /**
   * Creates and returns a new {@link ASTNode}.
   * 
   * @param real
   * @param parent
   */
  public ASTNode(double real, MathContainer parent) {
    this(Type.REAL, parent);
    setValue(real);
  }

  /**
   * Creates and returns a new {@link ASTNode} with the given integer value.
   * 
   * @param integer
   */
  public ASTNode(int integer) {
    this(integer, null);
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
    this(Type.INTEGER, parent);
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
    parentSBMLObject = parent;
    if (parentSBMLObject != null) {
      addAllChangeListeners(parent.getListOfTreeNodeChangeListeners());
    }
  }

  /**
   * Creates and returns a new {@link ASTNode} with the given name.
   * 
   * @param name  the name of this ASTNode
   */
  public ASTNode(String name) {
    this(Type.NAME);
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
    this(Type.NAME, parent);
    setName(name);
  }

  /**
   * Creates a new {@link ASTNode} of the given {@link Type} but without a
   * pointer to its {@link MathContainer}.
   * 
   * @param type
   */
  public ASTNode(Type type) {
    this();
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
  public ASTNode(Type type, MathContainer parent) {
    this(parent);
    setType(type);
  }

  /**
   * Adds a child to this node.
   * 
   * @param child
   *            the node to add as child.
   */
  public void addChild(ASTNode child) {
    if (isDebugEnabled) {
      logger.debug(" adding child current node: \n" + astNodeToTree(this, "", ""));
      logger.debug(" adding child: \n" + astNodeToTree(child, "", ""));
    }
    listOfNodes.add(child);
    setParentSBMLObject(child, parentSBMLObject, 0);
    child.setParent(this);
    child.fireNodeAddedEvent();
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
    if ((operator == Type.PLUS) || (operator == Type.MINUS)
        || (operator == Type.TIMES) || (operator == Type.DIVIDE)
        || (operator == Type.POWER) || (operator == Type.FUNCTION_ROOT)) {
      if (astnode.isZero() && (operator == Type.DIVIDE)) {
        throw new IllegalArgumentException(resourceBundle.getString("ASTNode.arithmeticOperation"));
      }
      if (!(astnode.isOne() && ((operator == Type.TIMES) || (operator == Type.DIVIDE)))) {
        /*
         * Here we want to restructure the tree by making an equivalent of the current node
         * being a child of the current node. This node will then become of some different type.
         * 
         * In order to avoid deep-cloning we save a pointer to the children, remove all
         * children, clone this current node, and add all children to the copy. At the end,
         * the copied node will become some child of the current node
         */
        List<ASTNode> children = listOfNodes;
        listOfNodes = null;
        ASTNode swap = clone(); // only clones the current node, no children
        listOfNodes = children;

        /*
         * TODO: What should be done to userObjects? Actually it can be assumed
         * that these apply to the swap node only and maybe all user objects
         * should be removed from the current node?
         */
        swapChildren(swap);
        setType(operator);
        if (operator == Type.FUNCTION_ROOT) {
          addChild(astnode);
          addChild(swap);
        } else {
          addChild(swap);
          addChild(astnode);
        }
        setParentSBMLObject(astnode, getParentSBMLObject(), 0);
      }
    } else {
      throw new IllegalArgumentException(MessageFormat.format(
        resourceBundle.getString("ASTNode.INVALID_OPERATOR_MSG"), operator));
    }
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
        value = (real > 0d) ? compiler.getPositiveInfinity() :
          compiler.getNegativeInfinity();
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
      if (childCount != 2 && !isInvalidSBMLAllowed()) {
        throw new SBMLException(MessageFormat.format(
          resourceBundle.getString("ASTNode.compile1"), childCount));
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
      if (variable == null) {
        variable = getVariable();
      }
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
    case FUNCTION_RATE_OF:
      value = compiler.getRateOf(getLeftChild());
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
      if (getChildCount() > 1) { // TODO - in general, should we throw an SBMLException if we encounter more child than expected ?
        value = compiler.log(getLeftChild(), getRightChild()); // TODO - temporary fix as we read log(1, 2) as ln(1, 2) currently (07/04/2017)
        break;
      }
      value = compiler.ln(getLeftChild());
      break;
    case FUNCTION_MAX:
      value = compiler.max(getChildren());
      break;
    case FUNCTION_MIN:
      value = compiler.min(getChildren());
      break;
    case FUNCTION_POWER:
      value = compiler.pow(getLeftChild(), getRightChild());
      break;
    case FUNCTION_QUOTIENT:
      value = compiler.quotient(getChildren());
      break;
    case FUNCTION_REM:
      value = compiler.rem(getChildren());
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
      if (variable == null) {
        variable = getVariable();
      }
      if (variable != null) {
        if (variable instanceof FunctionDefinition) {
          value = compiler.function((FunctionDefinition) variable,
            getChildren());
        } else {
          String message = MessageFormat.format(
            resourceBundle.getString("ASTNode.compile2"),
            getName(), getParentSBMLObject().getElementName());
          logger.warn(message);

          value = compiler.function(getName(), getChildren());

          //throw new SBMLException(message); // We need to be able to print invalid formula so that user can correct it
        }
      } else {
        logger.debug(MessageFormat.format(
          resourceBundle.getString("ASTNode.compile3"),
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
    case LOGICAL_IMPLIES:
      value = compiler.implies(getChildren());
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
    if (isLeaf()) {
      if ((isNumber() || isRational() || isUnknown()) &&
          (!isSetUnits() || (isSetParentSBMLObject() &&
              (-1 < getParentSBMLObject().getLevel()) &&
              (getParentSBMLObject().getLevel() < 3)))) {
        return true;
      }
      if (isString()) {
        if ((type == Type.NAME_TIME) && (isSetParentSBMLObject())) {
          Model model = getParentSBMLObject().getModel();
          if ((model != null) && model.isSetTimeUnits()) {
            return false;
          }
        } else if ((type == Type.NAME_AVOGADRO) || (getVariable() != null)
            && (!getVariable().containsUndeclaredUnits())) {
          return false;
        }

        // TODO - add a block for FUNCTION_RATE_OF ?

        return true;
      }
    } else {
      for (ASTNode child : getListOfNodes()) {
        if (child.containsUndeclaredUnits()) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Evaluates recursively this ASTNode and creates a new UnitDefinition with
   * respect of all referenced elements.
   * 
   * @return the derived unit of the node.
   * @throws SBMLException
   *             if they are problems going through the ASTNode tree.
   */
  public UnitDefinition deriveUnit() throws SBMLException {
    MathContainer container = getParentSBMLObject();
    int level = -1;
    int version = -1;
    if (container != null) {
      level = container.getLevel();
      version = container.getVersion();
    }
    UnitsCompiler compiler = null;
    if (isSetParentSBMLObject()) {
      Model model = getParentSBMLObject().getModel();
      compiler = new UnitsCompiler(model);
    }
    if (compiler == null) {
      compiler = new UnitsCompiler(level, version);
    }

    UnitDefinition derivedUnit = compile(compiler).getUnits();
    UnitDefinition simplifiedUnit = derivedUnit.simplify();

    if (logger.isDebugEnabled()) {
      logger.debug("ASTNode.deriveUnit - before simplify - units = " + UnitDefinition.printUnits(derivedUnit));
      logger.debug("ASTNode.deriveUnit - after simplify  - units = " + UnitDefinition.printUnits(simplifiedUnit));
    }

    return simplifiedUnit;
  }

  /**
   * Divides this node by the given node
   * 
   * @param ast
   *            an ASTNode
   * @return the current node for convenience.
   */
  public ASTNode divideBy(ASTNode ast) {
    arithmeticOperation(Type.DIVIDE, ast);
    return this;
  }

  /**
   * Divides this node by the given SBML element.
   * 
   * @param namedSBase
   *            an SBML element that can be represented by a value.
   * @return the current node for convenience.
   */
  public ASTNode divideBy(CallableSBase namedSBase) {
    return divideBy(new ASTNode(namedSBase, getParentSBMLObject()));
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractTreeNode#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equal = super.equals(object);

    if (equal) {
      ASTNode ast = (ASTNode) object;
      equal &= ast.getType() == type;

      if (isInteger() && ast.isInteger()) {
        equal &= ast.getInteger() == getInteger();
      }
      if (isString() && ast.isString()) {
        equal &= ast.isSetName() == isSetName();
        if (equal && isSetName()) {
          equal &= ast.getName().equals(getName());
        }
      }
      if (isRational() && ast.isRational()) {
        equal &= ast.getNumerator() == getNumerator()
            && ast.getDenominator() == getDenominator();
      }
      if ((ast.getType() == Type.REAL_E) && (type == Type.REAL_E)) {
        equal &= Double.isNaN(getMantissa()) ? Double.isNaN(ast.getMantissa()) : ast.getMantissa() == getMantissa()
            && ast.getExponent() == getExponent();
      } else if (isReal() && ast.isReal()) {
        equal &= Double.isNaN(getReal()) ? Double.isNaN(ast.getReal()) : ast.getReal() == getReal();
      }

      equal &= isSetClassName() == ast.isSetClassName();
      if (equal && isSetClassName()) {
        equal &= getClassName().equals(ast.getClassName());
      }
      equal &= isSetDefinitionURL() == ast.isSetDefinitionURL();
      if (equal && isSetDefinitionURL()) {
        equal &= getDefinitionURL().equals(ast.getDefinitionURL());
      }
      equal &= isSetEncoding() == ast.isSetEncoding();
      if (equal && isSetEncoding()) {
        equal &= getEncoding().equals(ast.getEncoding());
      }
      equal &= isSetId() == ast.isSetId();
      if (equal && isSetId()) {
        equal &= getId().equals(ast.getId());
      }
      equal &= isSetStyle() == ast.isSetStyle();
      if (equal && isSetStyle()) {
        equal &= getStyle().equals(ast.getStyle());
      }
      equal &= isSetUnits() == ast.isSetUnits();
      if (equal && isSetUnits()) {
        equal &= getUnits().equals(ast.getUnits());
      }

      // TODO - semanticsAnnotationList not part of equals or hashcode ?
    }
    return equal;
  }

  /**
   * Goes through the formula and identifies all global parameters that are
   * referenced by this rate equation.
   * 
   * @return all global parameters that are referenced by this rate equation.
   */
  public List<Parameter> findReferencedGlobalParameters() {
    ArrayList<Parameter> pList = new ArrayList<Parameter>();
    if (getType().equals(ASTNode.Type.NAME)
        && (getVariable() instanceof Parameter)
        && (getParentSBMLObject().getModel().getParameter(
          getVariable().getId()) != null)) {
      pList.add((Parameter) getVariable());
    }
    for (ASTNode child : getListOfNodes()) {
      pList.addAll(child.findReferencedGlobalParameters());
    }
    return pList;
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
      switch (type) {
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
    throw new IllegalArgumentException(resourceBundle.getString("ASTNode.compile4"));
  }

  /**
   * Gets a child of this node according to an index number.
   * 
   * @param index
   *            the index of the child to get
   * @return the child of this {@link ASTNode} with the given index.
   * @throws IndexOutOfBoundsException
   *             - if the index is out of range (index &lt; 0 || index &gt;=
   *             size()).
   */
  public ASTNode getChild(int index) {
    return listOfNodes.get(index);
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildAt(int)
   */
  @Override
  public TreeNode getChildAt(int i) {
    return getChild(i);

    // TODO - add semantics XMLNode ??
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildCount()
   */
  @Override
  public int getChildCount() {
    return listOfNodes == null ? 0 : listOfNodes.size();

    // TODO - add semantics XMLNode ??
  }

  /**
   * Returns the list of children of the current ASTNode.
   * 
   * @return the list of children of the current ASTNode.
   */
  public List<ASTNode> getChildren() {
    return listOfNodes;
  }

  /**
   * Returns the class name of the mathML element represented by this ASTNode.
   * 
   * @return the class name of the mathML element represented by this ASTNode.
   */
  public String getClassName() {
    return className;
  }

  /**
   * Returns the definitionURL
   * 
   * @return the definitionURL
   */
  public String getDefinitionURL() {
    return definitionURL;
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
      return denominator;
    }
    throw new IllegalArgumentException(resourceBundle.getString("ASTNode.getDenominator"));
  }

  /**
   * Returns the encoding of the mathML element represented by this ASTNode.
   * 
   * @return the encoding of the mathML element represented by this ASTNode.
   */
  public String getEncoding() {
    return encoding;
  }

  /**
   * Gets the exponent value of this ASTNode. This function should be called
   * only when {@link #getType()} returns REAL_E or REAL, otherwise an Exception is
   * thrown.
   * 
   * @return the value of the exponent of this ASTNode.
   * @throws IllegalArgumentException
   *             if the method is called on a node that is not of type real.
   */
  public int getExponent() {
    if (type == Type.REAL || type == Type.REAL_E) {
      return exponent;
    }
    throw new IllegalArgumentException(resourceBundle.getString("ASTNode.getExponent"));
  }

  /**
   * Returns the id of the mathML element represented by this ASTNode.
   * 
   * @return the id of the mathML element represented by this ASTNode.
   */
  public String getId() {
    return id;
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
      return numerator;
    }
    throw new IllegalArgumentException(resourceBundle.getString("ASTNode.getInteger"));
  }

  /**
   * Gets the left child of this node.
   * 
   * @return the left child of this ASTNode. This is equivalent to
   *         getChild(0);
   */
  public ASTNode getLeftChild() {
    return getChild(0);
  }

  /**
   * Returns the list of children of the current ASTNode.
   * 
   * @return the list of children of the current ASTNode.
   */
  public List<ASTNode> getListOfNodes() {
    return listOfNodes;
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
    ArrayList<ASTNode> filteredList = new ArrayList<ASTNode>();

    for (ASTNode node : listOfNodes) {
      if (filter.accepts(node)) {
        filteredList.add(node);
      }
    }

    return filteredList;
  }

  /**
   * Gets the mantissa value of this node. This function should be called only
   * when getType() returns REAL_E or REAL, otherwise an Exception is thrown.
   * If getType() returns REAL, this method is identical to getReal().
   * 
   * @return the value of the mantissa of this ASTNode.
   */
  public double getMantissa() {
    if ((type == Type.REAL) || type == Type.REAL_E) {
      return mantissa;
    }
    throw new IllegalArgumentException(resourceBundle.getString("ASTNode.getMantissa"));
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
    if (!isOperator() && !isNumber()) {
      return (variable == null) ? name : variable.getId();
    }
    if (!isInvalidSBMLAllowed()) {
      throw new IllegalArgumentException(resourceBundle.getString("ASTNode.getName"));
    }

    return name;
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
      return numerator;
    }
    throw new IllegalArgumentException(resourceBundle.getString("ASTNode.getNumerator"));
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getParent()
   */
  @Override
  public TreeNode getParent() {
    return parent;
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
    return parentSBMLObject;
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
    if (isReal() || (type == Type.CONSTANT_E) || (type == Type.CONSTANT_PI) || (type == Type.NAME_AVOGADRO)) {
      switch (type) {
      case NAME_AVOGADRO:
        // TODO: in case that there will be different values for this constant in later versions, we will need a LV check here.
        return Maths.AVOGADRO_L3V1;
      case REAL:
        return mantissa;
      case REAL_E:{
        //  mantissa * Math.pow(10, getExponent())) ==> this formula does not give exact values.
        // for example: mantissa = 3.0, exponent = -17 ==> 2.9999999999999994E-17 instead of 3.0E-17
        if (Double.isNaN(mantissa) || Double.isInfinite(mantissa)) {
          return mantissa;
        }
        return Double.parseDouble(mantissa + "E" + getExponent());
      }
      case RATIONAL:
        // One cast is enough; we don't need to cast twice here.
        return ((double) getNumerator()) / getDenominator();
      case CONSTANT_E:
        return Math.E;
      case CONSTANT_PI:
        return Math.PI;
      default:
        break;
      }
    } else if (isInteger()) {
      return getInteger();
    }
    throw new IllegalArgumentException(resourceBundle.getString("ASTNode.getReal"));
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
    Set<NamedSBase> l = new HashSet<NamedSBase>();
    if (isString()) {
      if (getVariable() != null) {
        l.add(getVariable());
      } else {
        logger.warn(MessageFormat.format(
          resourceBundle.getString("ASTNode.getReferencedNamedSBases"),
          getName()));
      }
    }
    for (ASTNode child : listOfNodes) {
      l.addAll(child.getReferencedNamedSBases());
    }
    return l;
  }

  /**
   * Returns the last child in the list of children of this node.
   * 
   * @return This is equivalent to calling
   *         {@code getListOfNodes().getLast()}.
   */
  public ASTNode getRightChild() {
    int n = listOfNodes.size() - 1;
    return (n >= 0) ? listOfNodes.get(n) : null;
  }

  /**
   * Returns the style of the mathML element represented by this ASTNode.
   * 
   * @return the style of the mathML element represented by this ASTNode.
   */
  public String getStyle() {
    return style;
  }

  /**
   * Returns the type of this node.
   * 
   * @return the type of this node.
   */
  public Type getType() {
    return type;
  }

  /**
   * Returns the units attribute.
   * 
   * @return the units attribute.
   */
  public String getUnits() {
    return unitId;
  }

  /**
   * Creates or obtains a {@link UnitDefinition} corresponding to the unit
   * that has been set for this {@link ASTNode} and returns a pointer to it.
   * Note that in case that this {@link ASTNode} is associated with a
   * {@link Unit.Kind}, the created {@link UnitDefinition} will not be part of
   * the model, it is just a container for the {@link Unit.Kind}.
   * 
   * @return A {@link UnitDefinition} or {@code null}.
   */
  public UnitDefinition getUnitsInstance() {
    MathContainer parent = getParentSBMLObject();
    int level = parent != null ? parent.getLevel() : -1;
    int version = parent != null ? parent.getVersion() : -1;

    if (!isSetUnits() || (parent == null)) {
      if (isVariable()) {
        CallableSBase variable = getVariable();
        if (variable != null) {
          return variable.getDerivedUnitDefinition();
        } else if (isConstant()) {
          UnitDefinition ud = new UnitDefinition(level, level);
          ud.addUnit(Unit.Kind.DIMENSIONLESS);
          return ud;
        }
      }
      return null;
    }
    if (Unit.Kind.isValidUnitKindString(getUnits(), level, version)) {
      return UnitDefinition.getPredefinedUnit(getUnits(), level, version);
    } else if (parent.getModel() == null) {
      return null;
    }

    return parent.getModel().getUnitDefinition(getUnits());
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
    if (isVariable()) {
      if (variable == null) {
        /*
         * Possibly the name is just from the argument list
         * of some function definition. Hence, it won't be
         * possible to determine an element with the same
         * identifier within the model. In this case, this
         * warning is kind of senseless.
         */
        TreeNode parent = getParent();
        if ((parent != null) && (parent instanceof ASTNode)) {
          ASTNode parentNode = (ASTNode) parent;
          if ((parentNode.getType() == Type.LAMBDA) && (parentNode.getRightChild() != this)) {
            /*
             * The second condition is important, because the argument list
             * comprises only the first n children. Child n + 1 is the
             * expression for the function.
             */
            if (logger.isDebugEnabled()) {
              logger.debug(MessageFormat.format(
                resourceBundle.getString("ASTNode.getVariable1"),
                getName()));
            }
            return variable;
          }
        }
        if (getParentSBMLObject() != null) {
          if (getParentSBMLObject() instanceof KineticLaw) {
            variable = ((KineticLaw) getParentSBMLObject()).getLocalParameter(getName());
          }
          // TODO: Add a check if the parent is a FunctionDefinition that contains the variable in its list of parameters. If this is not the case, an error must be thrown!
          if (variable == null) {
            Model m = getParentSBMLObject().getModel();
            if (m != null) {
              variable = m.findCallableSBase(getName());
              if (variable instanceof LocalParameter) {
                // in this case the parameter originates from a
                // different kinetic law.
                variable = null;
              } else if ((variable == null) && logger.isDebugEnabled()) {
                // Could be any L3 package elements
                // that is not a CallableSBase
                // TODO: Actually, if something can be addressed in an ASTNode,
                // it MUST implement CallableSBase, no matter in which extension
                // package.
                // variable = m.findNamedSBase(getName());
                logger.debug(MessageFormat.format(
                  resourceBundle.getString("ASTNode.getVariable2"),
                  getName()));
              }
            } else if (logger.isDebugEnabled()) {
              logger.debug(MessageFormat.format(
                resourceBundle.getString("ASTNode.getVariable3"),
                getName()));
            }
          }
        }
      }
      return variable;
    }
    throw new RuntimeException(resourceBundle.getString("ASTNode.getVariable4"));
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractTreeNode#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 787;
    int hashCode = super.hashCode();
    hashCode += prime * getType().hashCode();
    if (isInteger()) {
      hashCode += prime * getInteger();
    } else if (!isNumber() && !isOperator() && (isSetName() || (variable != null))) {
      hashCode += prime * getName().hashCode();
    } else if (isRational()) {
      hashCode += prime * getNumerator() + prime * getDenominator();
    } else if (isReal()) {
      hashCode += prime * getReal();
    }
    if (isSetDefinitionURL()) {
      hashCode += prime * getDefinitionURL().hashCode();
    }
    if (isSetEncoding()) {
      hashCode += prime * getEncoding().hashCode();
    }
    if (isSetId()) {
      hashCode += prime * getId().hashCode();
    }
    if (isSetStyle()) {
      hashCode += prime * getStyle().hashCode();
    }
    if (isSetClassName()) {
      hashCode += prime * getClassName().hashCode();
    }
    if (isSetUnits()) {
      hashCode += prime * getUnits().hashCode();
    }
    // TODO - semanticsAnnotationList not part of equals or hashcode ?


    return hashCode;
  }

  /**
   * Returns {@code true} if the current ASTNode or any of his descendant has a unit
   * defined.
   * 
   * @return {@code true} if the current ASTNode or any of his descendant has a unit
   *         defined.
   */
  public boolean hasUnits() {
    boolean hasUnits = isSetUnits();

    if (!hasUnits) {
      for (ASTNode child : getChildren()) {
        hasUnits = child.hasUnits();
        if (hasUnits) {
          break;
        }
      }
    }

    return hasUnits;
  }

  /**
   * Initializes the default values/attributes of the node.
   */
  private void initDefaults() {
    ASTNode old = this;
    if (isDebugEnabled) {
      logger.debug(MessageFormat.format(resourceBundle.getString("ASTNode.initDefaults"), (type == null ? Type.UNKNOWN : type)));
    }

    type = Type.UNKNOWN;

    id = null;
    style = null;
    className = null;
    encoding = null;
    denominator = 0;
    exponent = 0;
    name = null;
    numerator = 0;
    // parent = null; // don't remove this node from the tree
    isSetNumberType = false;
    definitionURL = null;
    unitId = null;

    if (listOfNodes == null) {
      listOfNodes = new ArrayList<ASTNode>();
    } else {
      for (int i = listOfNodes.size() - 1; i >= 0; i--) {
        // This also removes the pointer from the previous child to this object, i.e., its previous parent node.
        ASTNode removed = listOfNodes.remove(i);
        resetParentSBMLObject(removed);
        removed.fireNodeRemovedEvent();
      }
    }
    variable = null;
    mantissa = Double.NaN;

    if (plugins == null) {
      plugins = new TreeMap<String, ASTNodePlugin>();
    }
    firePropertyChange(TreeNodeChangeEvent.initialValue, old, this);
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
    listOfNodes.add(n, newChild);
    setParentSBMLObject(newChild, parentSBMLObject, 0);
    newChild.setParent(this);
  }

  /**
   * Returns {@code true} if this node has a boolean type (a logical operator, a
   * relational operator, or the constants {@code true} or {@code false}).
   * 
   * @return {@code true} if this ASTNode is a boolean, {@code false} otherwise.
   */
  public boolean isBoolean() {
    return type == Type.CONSTANT_FALSE || type == Type.CONSTANT_TRUE
        || isLogical() || isRelational();
  }

  /**
   * Returns {@code true} if this node represents a MathML constant (e.g., {@code true}, Pi).
   * 
   * @return {@code true} if this ASTNode is a MathML constant, {@code false} otherwise.
   */
  public boolean isConstant() {
    return type.toString().startsWith("CONSTANT") || type == Type.NAME_AVOGADRO;
  }

  /**
   * Checks if this {@link ASTNode} represents a difference.
   * 
   * @return {@code true} if this {@link ASTNode} represents a difference, {@code false}
   *         otherwise.
   */
  public boolean isDifference() {
    return type == Type.MINUS;
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
    return type.toString().startsWith("FUNCTION");
  }

  /**
   * Returns {@code true} if this node represents the special IEEE 754 value infinity,
   * {@code false} otherwise.
   * 
   * @return {@code true} if this ASTNode is the special IEEE 754 value infinity,
   *         {@code false} otherwise.
   */
  public boolean isInfinity() {
    if (isReal()) {
      double real = getReal();
      return Double.isInfinite(real) && (real > 0d);
    }
    return false;
  }

  /**
   * Returns {@code true} if this node contains an integer value, {@code false} otherwise.
   * 
   * @return {@code true} if this ASTNode is of type INTEGER, {@code false} otherwise.
   */
  public boolean isInteger() {
    return type == Type.INTEGER;
  }

  /**
   * Returns {@code true} if this node is a MathML &lt;lambda&gt;, {@code false} otherwise.
   * 
   * @return {@code true} if this ASTNode is of type LAMBDA, {@code false} otherwise.
   */
  public boolean isLambda() {
    return type == Type.LAMBDA;
  }

  /**
   * Returns {@code true} if this node is constant or of type INTEGER, REAL or RATIONAL, {@code false} otherwise.
   * 
   * @return {@code true} if this ASTNode is constant or of type INTEGER, REAL or RATIONAL, {@code false} otherwise.
   */
  public boolean isLiteral() {
    return isConstant() || type == Type.INTEGER || type == Type.RATIONAL || type == Type.REAL;
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
    return type == Type.FUNCTION_LOG && listOfNodes.size() == 2
        && getLeftChild().isInteger()
        && getLeftChild().getInteger() == 10;
  }

  /**
   * Returns {@code true} if this node is a MathML logical operator (i.e., and, or,
   * not, xor).
   * 
   * @return {@code true} if this ASTNode is a MathML logical operator.
   */
  public boolean isLogical() {
    return type.toString().startsWith("LOGICAL_");
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
   * (MathML), or the special symbols time, Avogadro or rateOf. The predicate returns
   * {@code false} otherwise.
   * 
   * @return {@code true} if this {@link ASTNode} is a user-defined variable name in SBML L1,
   *         L2 (MathML) or the special symbols time, Avogadro or rateOf.
   */
  public boolean isName() {
    return (type == Type.NAME) || (type == Type.NAME_TIME)
        || (type == Type.NAME_AVOGADRO);
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
    if (isReal()) {
      double real = getReal();
      return Double.isInfinite(real) && (real < 0);
    }
    return false;
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
    return type == Type.PLUS || type == Type.MINUS || type == Type.TIMES
        || type == Type.DIVIDE || type == Type.POWER;
  }

  /**
   * Returns {@code true} if this node is the MathML &lt;piecewise&gt; construct,
   * {@code false} otherwise.
   * 
   * @return {@code true} if this ASTNode is a MathML piecewise function
   */
  public boolean isPiecewise() {
    return type == Type.FUNCTION_PIECEWISE;
  }

  /**
   * Returns {@code true} if this node represents a rational number, {@code false} otherwise.
   * 
   * @return {@code true} if this ASTNode is of type {@link Type#RATIONAL}.
   */
  public boolean isRational() {
    return type == Type.RATIONAL;
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
    return type == Type.REAL || type == Type.REAL_E
        || type == Type.RATIONAL;
  }

  /**
   * Returns {@code true} if this node is a MathML relational operator, meaning ==,
   * &gt;=, &gt;, &lt;, &lt;=, and !=.
   * 
   * @return {@code true} if this ASTNode is a MathML relational operator, {@code false}
   *         otherwise.
   */
  public boolean isRelational() {
    return type == Type.RELATIONAL_EQ || type == Type.RELATIONAL_GEQ
        || type == Type.RELATIONAL_GT || type == Type.RELATIONAL_LEQ
        || type == Type.RELATIONAL_LT || type == Type.RELATIONAL_NEQ;
  }


  /**
   * Return true if this node contain some semantics annotations.
   * 
   * @return true if this node contain some semantics annotations.
   * @see #getNumSemanticsAnnotations()
   */
  public boolean isSemantics() {
    return getNumSemanticsAnnotations() > 0;
  }


  /**
   * @return
   */
  public boolean isSetClassName() {
    return className != null;
  }

  /**
   * @return
   */
  public boolean isSetDefinitionURL() {
    return definitionURL != null;
  }

  /**
   * @return
   */
  public boolean isSetEncoding() {
    return encoding != null;
  }

  /**
   * @return
   */
  public boolean isSetId() {
    return id != null;
  }

  /**
   * @return
   */
  public boolean isSetName() {
    return name != null;
  }

  /**
   * Returns {@code true} if the number type is set.
   * 
   * @return {@code true} if the number type is set.
   */
  public boolean isSetNumberType() {
    return isSetNumberType;
  }

  /**
   * Checks if a parent SBML object, i.e., a {@link MathContainer}, is set as a
   * parent SBML object for this {@link ASTNode}.
   * 
   * @return
   */
  public boolean isSetParentSBMLObject() {
    return parentSBMLObject != null;
  }

  /**
   * @return
   */
  public boolean isSetStyle() {
    return style != null;
  }

  /**
   * Returns {@code true} if a unit is defined on this node.
   * 
   * @return {@code true} if a unit is defined on this node.
   */
  public boolean isSetUnits() {
    return unitId != null;
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
    return type == Type.FUNCTION_ROOT && listOfNodes.size() == 2
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
    return isName() || (type == Type.FUNCTION);
  }

  /**
   * Checks if this {@link ASTNode} represents a sum.
   * 
   * @return {@code true} if this {@link ASTNode} represents a sum, {@code false} otherwise.
   */
  public boolean isSum() {
    return type == Type.PLUS;
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
    return (type == Type.MINUS) && (getChildCount() == 1);
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
    return type == Type.UNKNOWN;
  }

  /**
   * Returns {@code true} if this node represents a {@link Variable}.
   * 
   * @return {@code true} if this node represents a {@link Variable}.
   */
  public boolean isVariable() {
    return type == Type.NAME || type == Type.FUNCTION;
  }

  /**
   * Checks if this {@link ASTNode} represents a vector.
   * 
   * @return {@code true} if this {@link ASTNode} represents a vector, {@code false} otherwise.
   */
  public boolean isVector() {
    return type == Type.VECTOR;
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
    arithmeticOperation(Type.MINUS, ast);
    return this;
  }

  /**
   * Subtracts the given number from this node.
   * 
   * @param real
   *            a double number.
   * @return the current node for convenience.
   */
  public ASTNode minus(double real) {
    minus(new ASTNode(real, getParentSBMLObject()));
    return this;
  }

  /**
   * Subtracts the given integer from this node.
   * 
   * @param integer
   *            an integer number.
   * @return the current node for convenience.
   */
  public ASTNode minus(int integer) {
    return minus(integer, null);
  }

  /**
   * 
   * @param integer
   * @param unitsID
   * @return
   */
  public ASTNode minus(int integer, String unitsID) {
    minus(new ASTNode(integer, unitsID, getParentSBMLObject()));
    return this;
  }

  /**
   * Multiplies this {@link ASTNode} with the given node
   * 
   * @param ast
   *            an {@code ASTNode}
   * @return the current node for convenience.
   */
  public ASTNode multiplyWith(ASTNode ast) {
    arithmeticOperation(Type.TIMES, ast);
    return this;
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
    for (ASTNode node : nodes) {
      multiplyWith(node);
    }
    reduceToBinary();
    return this;
  }

  /**
   * Multiplies this {@link ASTNode} with the given SBML element.
   * 
   * @param nsb
   *            an SBML element that can be represented by a value.
   * @return the current node for convenience.
   */
  public ASTNode multiplyWith(CallableSBase nsb) {
    return multiplyWith(new ASTNode(nsb, getParentSBMLObject()));
  }

  /**
   * Adds a given node to this node.
   * 
   * @param ast
   *            an {@code ASTNode}
   * @return the current node for convenience.
   */
  public ASTNode plus(ASTNode ast) {
    arithmeticOperation(Type.PLUS, ast);
    return this;
  }

  /**
   * Adds an SBML element to this node.
   * 
   * @param nsb
   *            an SBML element that can be represented by a value.
   * @return the current node for convenience.
   */
  public ASTNode plus(CallableSBase nsb) {
    plus(new ASTNode(nsb, getParentSBMLObject()));
    return this;
  }

  /**
   * Adds a number to this node.
   * 
   * @param real
   *            a double number.
   * @return the current node for convenience.
   */
  public ASTNode plus(double real) {
    plus(new ASTNode(real, getParentSBMLObject()));
    return this;
  }

  /**
   * Adds an integer number to this node.
   * 
   * @param integer
   *            an integer number.
   * @return the current node for convenience.
   */
  public ASTNode plus(int integer) {
    plus(new ASTNode(integer, getParentSBMLObject()));
    return this;
  }

  /**
   * Adds the given node as a child of this ASTNode. This method adds child
   * nodes from right to left.
   * 
   * @param child
   *            an {@code ASTNode}
   */
  public void prependChild(ASTNode child) {
    listOfNodes.add(0, child);
    setParentSBMLObject(child, parentSBMLObject, 0);
    child.setParent(this);
  }

  /**
   * Raises this ASTNode by the power of the value of the given node.
   * 
   * @param exponent
   *            an {@code ASTNode}
   * @return the current node for convenience.
   */
  public ASTNode raiseByThePowerOf(ASTNode exponent) {
    arithmeticOperation(Type.POWER, exponent);
    return this;
  }

  /**
   * Raises this ASTNode by the power of the value of this named SBase object.
   * 
   * @param nsb
   *            an SBML element that can be represented by a value.
   * @return the current node for convenience.
   */
  public ASTNode raiseByThePowerOf(CallableSBase nsb) {
    return raiseByThePowerOf(new ASTNode(nsb, getParentSBMLObject()));
  }

  /**
   * Raises this {@link ASTNode} by the power of the given number.
   * 
   * @param exponent
   *            a double number.
   * @return the current node for convenience.
   */
  public ASTNode raiseByThePowerOf(double exponent) {
    if (exponent == 0d) {
      // Clear list of nodes first because this won't notify any listeners.
      listOfNodes.clear();
      // This will notify listeners that will receive this ASTNode with an empty list of children.
      setValue(1);
      // The units of this ASTNode must be dimensionless now.
      if (isSetParentSBMLObject() && (getParentSBMLObject().getLevel() > 2)) {
        setUnits(Unit.Kind.DIMENSIONLESS.toString().toLowerCase());
      }
    } else if (exponent != 1d) {
      ASTNode exp;
      if (Maths.isInt(exponent)) {
        exp = new ASTNode((int) exponent, getParentSBMLObject());
      } else {
        exp	= new ASTNode(exponent, getParentSBMLObject());
      }
      if (isSetParentSBMLObject() && (getParentSBMLObject().getLevel() > 2)) {
        // Exponents must be dimensionless!
        exp.setUnits(Unit.Kind.DIMENSIONLESS.toString().toLowerCase());
      }
      return raiseByThePowerOf(exp);
    }
    return this;
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
    if (getChildCount() > 2) {
      int i;
      switch (type) {
      case PLUS:
        ASTNode plus = new ASTNode(Type.PLUS, parentSBMLObject);
        for (i = getChildCount() - 1; i > 0; i--) {
          plus.addChild(listOfNodes.remove(i));
        }
        addChild(plus);
        break;
      case MINUS:
        // TODO
        if (isDebugEnabled) {
          logger.debug(MessageFormat.format(resourceBundle.getString("ASTNode.reduceToBinary1"), getChildCount()));
        }
        break;
      case TIMES:
        ASTNode times = new ASTNode(Type.TIMES, parentSBMLObject);
        for (i = getChildCount() - 1; i > 0; i--) {
          times.addChild(listOfNodes.remove(i));
        }
        addChild(times);
        // if (getLeftChild().isMinusOne() ||
        // getRightChild().isMinusOne()) {
        // TODO
        // }
        break;
      case DIVIDE:
        // TODO
        if (isDebugEnabled) {
          logger.debug(MessageFormat.format(resourceBundle.getString("ASTNode.reduceToBinary2"), getChildCount()));
        }
        break;
      case LOGICAL_AND:
        ASTNode and = new ASTNode(Type.LOGICAL_AND, parentSBMLObject);
        for (i = getChildCount() - 1; i > 0; i--) {
          and.addChild(listOfNodes.remove(i));
        }
        addChild(and);
        break;
      case LOGICAL_OR:
        ASTNode or = new ASTNode(Type.LOGICAL_OR, parentSBMLObject);
        for (i = getChildCount() - 1; i > 0; i--) {
          or.addChild(listOfNodes.remove(i));
        }
        addChild(or);
        break;
      case LOGICAL_NOT:
        // TODO
        if (isDebugEnabled) {
          logger.debug(MessageFormat.format(resourceBundle.getString("ASTNode.reduceToBinary3"), getChildCount()));
        }
        break;
      case LOGICAL_XOR:
        // TODO
        if (isDebugEnabled) {
          logger.debug(MessageFormat.format(resourceBundle.getString("ASTNode.reduceToBinary4"), getChildCount()));
        }
        break;
      default:
        // TODO
        if (isDebugEnabled) {
          logger.debug(MessageFormat.format(resourceBundle.getString("ASTNode.reduceToBinary5"), getType(), getChildCount()));
        }
        break;
      }
    }
    // recursively restructure this tree.
    for (ASTNode child : listOfNodes) {
      child.reduceToBinary();
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
    if (isString() && (getName() != null) && getName().equals(id)) {
      return true;
    }
    boolean childContains = false;
    for (ASTNode child : listOfNodes) {
      childContains |= child.refersTo(id);
    }
    return childContains;
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
    if ((listOfNodes.size() > n) && (n >= 0)) {
      ASTNode removed = listOfNodes.remove(n);
      resetParentSBMLObject(removed);
      removed.fireNodeRemovedEvent();
      return true;
    }
    return false;
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
    int n = 0;
    for (ASTNode child : listOfNodes) {
      if (child.isString() && child.getName().equals(bvar)) {
        replaceChild(n, arg.clone());
      } else if (child.getChildCount() > 0) {
        child.replaceArgument(bvar, arg);
      }
      n++;
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
    // Removing the node at position n
    ASTNode oldChild = listOfNodes.remove(n);
    resetParentSBMLObject(oldChild);
    oldChild.fireNodeRemovedEvent();

    // Adding the new child at position n
    setParentSBMLObject(newChild, parentSBMLObject, 0);
    newChild.parent = this;
    listOfNodes.add(n, newChild);
    newChild.addAllChangeListeners(getListOfTreeNodeChangeListeners());
    newChild.fireNodeAddedEvent();
    return newChild;
  }

  /**
   * Resets the parentSBMLObject to null recursively.
   * 
   * @param node
   */
  private void resetParentSBMLObject(ASTNode node) {

    node.parentSBMLObject = null;
    for (ASTNode child : node.listOfNodes) {
      resetParentSBMLObject(child);
    }
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
    Type oldValue = type;
    switch (value) {
    case '+':
      type = Type.PLUS;
      break;
    case '-':
      type = Type.MINUS;
      break;
    case '*':
      type = Type.TIMES;
      break;
    case '/':
      type = Type.DIVIDE;
      break;
    case '^':
      type = Type.POWER;
      break;
    default:
      type = Type.UNKNOWN;
      break;
    }
    firePropertyChange(TreeNodeChangeEvent.value, oldValue, type);
  }

  /**
   * Sets the class name of the mathML element represented by this ASTNode.
   * 
   * @param className
   *            the class name.
   */
  public void setClassName(String className) {
    String oldValue = this.className;
    this.className = className;
    firePropertyChange(TreeNodeChangeEvent.className, oldValue, className);
  }

  /**
   * 
   * @param definitionURL
   */
  public void setDefinitionURL(String definitionURL) {
    String oldValue = this.definitionURL;
    this.definitionURL = definitionURL;
    firePropertyChange(TreeNodeChangeEvent.definitionURL, oldValue, definitionURL);
  }

  /**
   * Sets the encoding of the mathML element represented by this ASTNode.
   * 
   * @param encoding
   *            the encoding
   */
  public void setEncoding(String encoding) {
    String oldValue = this.encoding;
    this.encoding = encoding;
    firePropertyChange(TreeNodeChangeEvent.encoding, oldValue, encoding);
  }

  /**
   * Sets the id of the mathML element represented by this ASTNode.
   * 
   * @param id
   *            the id.
   */
  public void setId(String id) {
    String oldValue = this.id;
    this.id = id;
    firePropertyChange(TreeNodeChangeEvent.id, oldValue, id);
  }

  /**
   * 
   * @param isSetNumberType
   */
  public void setIsSetNumberType(boolean isSetNumberType) {
    Boolean oldValue = this.isSetNumberType;
    this.isSetNumberType = isSetNumberType;
    firePropertyChange(TreeNodeChangeEvent.isSetNumberType, oldValue, isSetNumberType);
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
    String oldValue = this.name; // TODO: if oldValue != null ==> set variable = null or update it ??
    this.name = name;
    firePropertyChange(TreeNodeChangeEvent.name, oldValue, name);
    if ((!type.toString().startsWith("NAME")) && (type != Type.FUNCTION)
        && (type != Type.FUNCTION_DELAY) && (type != Type.FUNCTION_RATE_OF)) {
      Type oldType = type;
      type = variable == null ? Type.FUNCTION : Type.NAME;
      firePropertyChange(TreeNodeChangeEvent.type, oldType, type);
    }
  }

  /**
   * Sets the Parent of the node to the given value
   * 
   * @param parent the parent
   */
  public void setParentSBMLObject(MathContainer parent) {
    parentSBMLObject = parent;
  }

  /**
   * Sets the style of the mathML element represented by this {@link ASTNode}.
   * 
   * @param style
   *            the style.
   */
  public void setStyle(String style) {
    String oldValue = this.style;
    this.style = style;
    firePropertyChange(TreeNodeChangeEvent.style, oldValue, style);
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
    if (isDebugEnabled) {
      logger.debug("ASTNode: setType(String) called: " + typeStr);
    }

    Type type = Type.getTypeFor(typeStr);
    setType(type);
    if (type != Type.UNKNOWN) {
      if (type == Type.REAL) {
        if (typeStr.equals("notanumber")) {
          setValue(Double.NaN);
        } else if (typeStr.equals("infinity")) {
          setValue(Double.POSITIVE_INFINITY);
        }
      }
    }
  }

  /**
   * Sets the type of this ASTNode to the given Type. A side-effect of doing
   * this is that any numerical values previously stored in this node are
   * reset to zero.
   * 
   * @param type
   *            the type to which this node should be set
   */
  // TODO: javadoc not synchronized, we are not reseting previously stored
  // values but we are modifying the name.
  // TODO: we should probably simplify the code to avoid future problems
  public void setType(Type type) {

    // TODO: check that the calls to initDefaults() do not delete anything
    // important when reading the XML file, see MathMLStaxParser.java
    // System.out.println("ASTNode: setType(Type) called: type = " +
    // type);

    String sType = type.toString();

    if (isDebugEnabled) {
      logger.debug(MessageFormat.format(resourceBundle.getString("ASTNode.setType"), this.type, sType));
    }

    if (sType.startsWith("NAME") || sType.startsWith("CONSTANT")) {
      // TODO: check, a user might have set some values before calling
      // the setType()
      initDefaults();
    }
    // TODO: setting the name should not be necessary and a user could have
    // set a name before calling setType
    if (type == Type.NAME_TIME) {
      name = "time";
      definitionURL = URI_TIME_DEFINITION;
      encoding = "text";
    } else if (type == Type.FUNCTION_DELAY) {
      initDefaults();
      name = "delay";
      definitionURL = URI_DELAY_DEFINITION;
      encoding = "text";
    } else if (type == Type.NAME_AVOGADRO) {
      name = "Avogadro's number";
      setValue(Maths.AVOGADRO_L3V1);
      definitionURL = URI_AVOGADRO_DEFINITION;
      encoding = "text";
    } else if (type == Type.FUNCTION_RATE_OF) {
      initDefaults();
      name = "rateOf";
      definitionURL = URI_RATE_OF_DEFINITION;
      encoding = "text";
    }

    Type oldValue = this.type;
    this.type = type;
    firePropertyChange(TreeNodeChangeEvent.type, oldValue, type);
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

    if (!isNumber()) {
      if (!isReadingInProgress()) {
        throw new IllegalArgumentException(MessageFormat.format(
          resourceBundle.getString("ASTNode.setUnits1"),
          unitId));
      } else {
        logger.warn(MessageFormat.format(resourceBundle.getString("ASTNode.setUnits1"), unitId));
      }
    }
    if (parentSBMLObject != null) {
      if (!Unit.isValidUnit(parentSBMLObject.getModel(), unitId)) {
        if (!isReadingInProgress()) {
          throw new IllegalArgumentException(MessageFormat.format(
            resourceBundle.getString("ASTNode.setUnits2"),
            unitId));
        } else {
          logger.warn(MessageFormat.format(resourceBundle.getString("ASTNode.setUnits2"), unitId));
        }
      }
      if (parentSBMLObject.isSetLevel() && (parentSBMLObject.getLevel() < 3)) {
        if (!isReadingInProgress()) {
          throw new IllegalArgumentException(MessageFormat.format(
            resourceBundle.getString("ASTNode.setUnits3"),
            unitId));
        } else {
          logger.warn(MessageFormat.format(resourceBundle.getString("ASTNode.setUnits3"), unitId));
        }
      }
    }
    String oldValue = this.unitId;
    this.unitId = unitId;
    firePropertyChange(TreeNodeChangeEvent.units, oldValue, unitId);
  }

  /**
   * 
   * @param unit
   */
  public void setUnits(Unit.Kind unit) {
    setUnits(unit.toString().toLowerCase());
  }

  /**
   * 
   * @param ud
   */
  public void setUnits(UnitDefinition ud) {
    setUnits(ud.getId());
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
    Type oldType = type;
    double oldMantissa = mantissa;
    int oldExponent = exponent;
    type = Type.REAL;
    firePropertyChange(TreeNodeChangeEvent.type, oldType, type);
    mantissa = value;
    firePropertyChange(TreeNodeChangeEvent.mantissa, oldMantissa, mantissa);
    exponent = 0;
    firePropertyChange(TreeNodeChangeEvent.exponent, oldExponent, exponent);
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
    Type oldType = type;
    double oldMantissa = this.mantissa;
    int oldExponent = this.exponent;
    type = Type.REAL_E;
    firePropertyChange(TreeNodeChangeEvent.type, oldType, type);
    this.mantissa = mantissa;
    firePropertyChange(TreeNodeChangeEvent.mantissa, oldMantissa, mantissa);
    this.exponent = exponent;
    firePropertyChange(TreeNodeChangeEvent.exponent, oldExponent, exponent);
  }

  /**
   * Sets the value of this ASTNode to the given (long) integer and sets the
   * node type to INTEGER.
   * 
   * @param value
   */
  public void setValue(int value) {
    Type oldType = type;
    int oldNumerator = numerator;
    int oldDenominator = denominator;
    type = Type.INTEGER;
    firePropertyChange(TreeNodeChangeEvent.type, oldType, type);
    numerator = value;
    firePropertyChange(TreeNodeChangeEvent.numerator, oldNumerator, numerator);
    denominator = 1;
    firePropertyChange(TreeNodeChangeEvent.denominator, oldDenominator, denominator);
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
    Type oldType = type;
    int oldNumerator = this.numerator;
    int oldDenominator = this.denominator;
    type = Type.RATIONAL;
    firePropertyChange(TreeNodeChangeEvent.type, oldType, type);
    this.numerator = numerator;
    firePropertyChange(TreeNodeChangeEvent.numerator, oldNumerator, numerator);
    this.denominator = denominator;
    firePropertyChange(TreeNodeChangeEvent.denominator, oldDenominator, denominator);
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
    CallableSBase oldValue = this.variable;
    if (variable instanceof FunctionDefinition) {
      type = Type.FUNCTION;
    } else {
      type = Type.NAME;
    }
    if ((variable != null) && variable.isSetId()) {
      /*
       * Although we memorize a direct pointer to the variable, we also have to
       * store its id. Otherwise, this knowledge will got lost when cloning this
       * node.
       */
      name = variable.getId();
    }
    this.variable = variable;
    firePropertyChange(TreeNodeChangeEvent.variable, oldValue, variable);
  }

  /**
   * Applies the square root function on this syntax tree and returns the
   * resulting tree.
   * 
   * @return the current node for convenience.
   */
  public ASTNode sqrt() {
    arithmeticOperation(Type.FUNCTION_ROOT, new ASTNode(2, getParentSBMLObject()));
    return this;
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
    List<ASTNode> swap = that.listOfNodes;
    that.listOfNodes = listOfNodes;
    listOfNodes = swap;
    for (ASTNode child : that.listOfNodes) {
      if (that.getParentSBMLObject() != getParentSBMLObject()) {
        setParentSBMLObject(child, that.getParentSBMLObject(), 0);
      }
      child.fireNodeRemovedEvent();
      child.getListOfTreeNodeChangeListeners().removeAll(that.getListOfTreeNodeChangeListeners());
      child.setParent(that);
      child.fireNodeAddedEvent();
    }
    for (ASTNode child : listOfNodes) {
      if (that.getParentSBMLObject() != getParentSBMLObject()) {
        setParentSBMLObject(child, getParentSBMLObject(), 0);
      }
      child.fireNodeRemovedEvent();
      child.getListOfTreeNodeChangeListeners().removeAll(getListOfTreeNodeChangeListeners());
      child.setParent(this);
      child.fireNodeAddedEvent();
    }
  }

  /**
   * Return the {@link ASTNode2} corresponding to the current {@link ASTNode}
   *
   * @return node {@link ASTNode2}
   */
  public ASTNode2 toASTNode2() {
    return ASTFactory.toASTNode2(this);
  }


  /**
   * Converts this {@link ASTNode} to a text string using a specific syntax for
   * mathematical formulas.
   * 
   * <p>
   * The text-string form of mathematical formulas produced by
   * toFormula() and read by parseFormula() are simple C-inspired infix
   * notation taken from SBML Level 1. A formula in this text-string form
   * therefore can be handed to a program that understands SBML Level 1
   * mathematical expressions, or used as part of a formula translation
   * system. Be careful that the default {@link FormulaCompilerLibSBML} used produce an output
   * a bit different than pure SBML level 1 mathematical expressions, in particular
   * for logical and relational operators.
   * </p>
   * 
   * @return the formula representing this {@link ASTNode} as an SBML Level 1 text-string
   *         mathematical formula.
   * @throws SBMLException
   *             if there is a problem in the ASTNode tree.
   */
  public String toFormula() throws SBMLException {
    ASTNodeValue a = compile(new FormulaCompilerLibSBML());
    return a.toString();
  }

  /**
   * Converts this {@link ASTNode} to a text string using a specific {@link FormulaCompiler}.
   * 
   * @param compiler
   * @return the formula representing this {@link ASTNode}. {@code null} is
   *         returned if the given compiler is {@code null}.
   * @throws SBMLException
   *             if there is a problem in the ASTNode tree.
   * @see FormulaCompiler
   * @see FormulaCompilerLibSBML
   */
  public String toFormula(FormulaCompiler compiler) throws SBMLException {
    if (compiler == null) {
      return null;
    }

    return compile(compiler).toString();
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
  public String toString() {  // TODO - potentially remove the method if the users agree and once we correct all the formula compilers (and other code) to never use toString() when the expect the formula !
    return printASTNode(); // users need to have warning for some time for this change which is complicated to track if the toString() method is not called implicitly
  }

  /**
   * Returns the infix formula representing this ASTNode or an empty String if there was
   * a problem to construct the formula.
   * 
   * @return the infix formula representing this ASTNode
   */
  public String printASTNode() {
    String formula = "";
    try {
      formula = toFormula();
    } catch (SBMLException e) {
      // log the exception
      e.printStackTrace();

      if (isDebugEnabled) {
        logger.error(MessageFormat.format(resourceBundle.getString("ASTNode.toString"), e.getMessage()), e);
      } else {
        // TODO: Do not print this message if parsing the file !!! Or remove it
        logger.warn(MessageFormat.format(resourceBundle.getString("ASTNode.toString"), e.getMessage()));
      }
    } catch (RuntimeException e) {
      // added to prevent a crash when we cannot create the formula
      if (isDebugEnabled) {
        logger.error(MessageFormat.format(resourceBundle.getString("ASTNode.toString"), e.getMessage()), e);
      }
    }
    return formula;
  }


  /**
   * Returns a simple String representing the content of the ASTNode.
   * 
   * @return a simple String representing the content of the ASTNode.
   */
  public String toSimpleString() {
    StringBuffer buffer = new StringBuffer("ASTNode [type=");
    buffer.append(type);

    if (isInteger())
    {
      buffer.append(", ").append(getInteger());
    }
    else if (isReal())
    {
      buffer.append(", ").append(getReal());

      if (isRational())
      {
        buffer.append(" (").append(getNumerator()).append(" / ").append(getDenominator());
      }
      else if (exponent != 0)
      {
        buffer.append(" (").append(getMantissa()).append("e").append(getExponent());
      }
    }
    else if (isName())
    {
      buffer.append(", name = ").append(getName());
    }

    if (definitionURL != null)
    {
      buffer.append(", definitionURL = ").append(getDefinitionURL());
    }

    if (unitId != null) {
      buffer.append(", units = ").append(getUnits());
    }

    if (getChildCount() > 0) {
      buffer.append(", nb children = ").append(getChildCount());
    }

    if (isSetUserObjects()) {
      buffer.append(", user object keys = ").append(userObjectKeySet());
    }

    if (getParent() == null) {
      buffer.append(", no parent defined");
    }

    if (id != null) {
      buffer.append(", id = ").append(getId());
    }
    if (style != null) {
      buffer.append(", style = ").append(getStyle());
    }
    if (encoding != null) {
      buffer.append(", encoding = ").append(getEncoding());
    }
    if (className != null) {
      buffer.append(", className = ").append(getClassName());
    }
    buffer.append("] ");

    return buffer.toString();
  }

  /**
   * Unset the units attribute.
   * 
   */
  public void unsetUnits() {
    String oldValue = unitId;
    unitId = null;
    firePropertyChange(TreeNodeChangeEvent.units, oldValue, null);
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
    if (isString()) {
      if (variable != null) {
        CallableSBase oldValue = getVariable();
        name = variable.getId();
        variable = null;
        variable = getVariable();
        firePropertyChange(TreeNodeChangeEvent.variable, oldValue, variable);
      } else if ((type != Type.NAME_TIME) && (type != Type.NAME_AVOGADRO)) {
        // Try to register a direct link to the variable (if the name represent one).
        variable = getVariable();
      }
    }
    for (ASTNode child : getChildren()) {
      child.updateVariables();
    }
  }

  /**
   * Returns a simple tree view of the ASTNode internal, including mainly
   * node type and hierarchy.
   * 
   * @param n
   * @param tree
   * @param indent
   * @return a simple tree view of the ASTNode internal
   */
  public static String astNodeToTree(ASTNode n, String tree, String indent) {
    //tree = tree + indent + n.getType() + " " +
    //    (n.isInteger() ? n.getInteger() : "") + (n.isReal() ? n.getReal() : "") + '\n';
    tree = tree + indent + n.toSimpleString() + " unit:" + n.deriveUnit() + '\n';

    for (ASTNode child : n.getChildren()) {
      tree = astNodeToTree(child, tree, indent + "  ");
    }

    return tree;
  }

  /**
   * Adds the given {@link XMLNode} as a MathML  {@code <semantics>}
   * element to this {@link ASTNode}.
   * 
   * <p>
   * The {@code <semantics>} element is a MathML&nbsp;2.0 construct
   * that can be used to associate additional information with a MathML
   * construct.  The construct can be used to decorate a MathML expressions with
   * a sequence of one or more {@code &lt;annotation&gt;} or
   * {@code &lt;annotation-xml&gt;} elements.  Each such element contains a
   * pair of items; the first is a symbol that acts as an attribute or key, and
   * the second is the value associated with the attribute or key.  Please refer
   * to the MathML&nbsp;2.0 documentation, particularly the <a target="_blank"
   * href="http://www.w3.org/TR/2007/WD-MathML3-20071005/chapter5.html#mixing.semantic.annotations">Section
   * 5.2, Semantic Annotations</a> for more information about these constructs.
   *
   * @param semanticsAnnotation the annotation to add.
   * @return the added {@link XMLNode}.
   */
  public XMLNode addSemanticsAnnotation(XMLNode semanticsAnnotation) {
    if (semanticsAnnotation == null) {
      return null;
    }
    if (semanticsAnnotationList == null) {
      semanticsAnnotationList = new ArrayList<XMLNode>();
    }

    semanticsAnnotationList.add(semanticsAnnotation);

    return semanticsAnnotation;
  }

  /**
   * Gets the number of <em>semantic annotation</em> elements inside this node.
   * 
   * <p>
   * The <code>&lt;semantics&gt;</code> element is a MathML&nbsp;2.0 construct
   * that can be used to associate additional information with a MathML
   * construct.  The construct can be used to decorate a MathML expressions with
   * a sequence of one or more <code>&lt;annotation&gt;</code> or
   * <code>&lt;annotation-xml&gt;</code> elements.  Each such element contains a
   * pair of items; the first is a symbol that acts as an attribute or key, and
   * the second is the value associated with the attribute or key.  Please refer
   * to the MathML&nbsp;2.0 documentation, particularly the <a target="_blank"
   * href="http://www.w3.org/TR/2007/WD-MathML3-20071005/chapter5.html#mixing.semantic.annotations">Section
   * 5.2, Semantic Annotations</a> for more information about these constructs.
   *
   * @return the number of annotations of this {@link ASTNode}.
   * @see ASTNode#addSemanticsAnnotation(XMLNode semanticsAnnotation)
   */
  public int getNumSemanticsAnnotations() {
    if (semanticsAnnotationList == null) {
      return 0;
    }

    return semanticsAnnotationList.size();
  }

  /**
   * Gets the nth <code>&lt;semantics&gt;</code> annotation of this node.
   * 
   * <p>
   * The <code>&lt;semantics&gt;</code> element is a MathML&nbsp;2.0 construct
   * that can be used to associate additional information with a MathML
   * construct.  The construct can be used to decorate a MathML expressions with
   * a sequence of one or more <code>&lt;annotation&gt;</code> or
   * <code>&lt;annotation-xml&gt;</code> elements.  Each such element contains a
   * pair of items; the first is a symbol that acts as an attribute or key, and
   * the second is the value associated with the attribute or key.  Please refer
   * to the MathML&nbsp;2.0 documentation, particularly the <a target="_blank"
   * href="http://www.w3.org/TR/2007/WD-MathML3-20071005/chapter5.html#mixing.semantic.annotations">Section
   * 5.2, Semantic Annotations</a> for more information about these constructs.
   * 
   * @return the nth annotation of this {@link ASTNode}, or <code>null</code> if this node has
   * no nth annotation (<code>n &gt;</code>
   * {@link ASTNode#getNumSemanticsAnnotations()}
   * <code>- 1</code> or n < 0).
   * @see ASTNode#addSemanticsAnnotation(XMLNode sAnnotation)
   */
  public XMLNode getSemanticsAnnotation(int n) {
    if (semanticsAnnotationList == null || n < 0 || (n > (getNumSemanticsAnnotations() -1))) {
      return null;
    }

    return semanticsAnnotationList.get(n);
  }

  /**
   * Returns the list of semantics annotations of this node.
   * 
   * @return the list of semantics annotations of this node or null if no semantics annotation are present.
   */
  public List<XMLNode> getListOfSemanticsAnnotations() {
    return semanticsAnnotationList;
  }

  // TODO - removeSemanticsAnnotation
  // TODO - unsetsemanticsAnnotation

  /**
   * Creates a new {@link ASTNodePlugin} for the given package name or URI
   * and adds it to this {@link ASTNode}.
   * 
   * <p>If an {@link ASTNodePlugin} was already present in this {@link ASTNode}
   * it will be replaced.
   * 
   * @param nameOrUri the package name or URI
   * @return a new {@link ASTNodePlugin} for the given package name or URI
   */
  public ASTNodePlugin createPlugin(String nameOrUri) {
    // use always the package name in the map
    PackageParser packageParser = ParserManager.getManager().getPackageParser(nameOrUri);

    if (packageParser != null) {
      ASTNodePlugin sbasePlugin = packageParser.createPluginFor(this);
      addExtension(nameOrUri, sbasePlugin);
      return sbasePlugin;
    }

    throw new IllegalArgumentException(MessageFormat.format(
      resourceBundle.getString("AbstractSBase.createPlugin"),
      nameOrUri));
  }

  /**
   * Adds a {@link ASTNodePlugin} object to this {@link ASTNode}.
   * 
   * <p>If a previous {@link ASTNodePlugin} associated with the same package
   * was present before, it will be replaced.
   * 
   * @param nameOrUri the name or URI of the package extension.
   * @param sbasePlugin the {@link ASTNodePlugin} to add.
   */
  public void addExtension(String nameOrUri, ASTNodePlugin sbasePlugin) {

    if (!isPackageEnabled(nameOrUri)) {
      enablePackage(nameOrUri);
    }

    // use always the package name in the map
    PackageParser packageParser = ParserManager.getManager().getPackageParser(nameOrUri);

    if (packageParser != null) {

      // unset the previous plugin if needed
      if (plugins.get(packageParser.getPackageName()) != null) {
        unsetPlugin(packageParser.getPackageName());
      }

      plugins.put(packageParser.getPackageName(), sbasePlugin);

      // Making sure that the correct extendedSBase is set in the SBasePlugin
      // And that all the ids and metaids are registered
      if ((sbasePlugin.getExtendedASTNode() == null) || (sbasePlugin.getExtendedASTNode() != this)) {
        // ((ASTNodePlugin) sbasePlugin).setPackageVersion(this); // TODO?
      }

      // the package namespace and version will be set in firePropertyChange
      firePropertyChange(TreeNodeChangeEvent.addExtension, null, sbasePlugin);
    } else {
      throw new IllegalArgumentException(MessageFormat.format(
        resourceBundle.getString("AbstractSBase.addExtensionExc"), nameOrUri));
    }
  }

  /**
   * Adds a {@link ASTNodePlugin} object to this {@link ASTNode}.
   * 
   * <p>If a previous {@link ASTNodePlugin} associated with the same package
   * was present before, it will be replaced.
   * 
   * @param nameOrUri the name or URI of the package extension.
   * @param sbasePlugin the {@link ASTNodePlugin} to add.
   * @see #addExtension(String, ASTNodePlugin)
   */
  public void addPlugin(String nameOrUri, ASTNodePlugin sbasePlugin) {
    addExtension(nameOrUri, sbasePlugin);
  }

  /**
   * Disables the given SBML Level 3 package on this {@link SBMLDocument}.
   * 
   * @param packageURIOrName a package namespace URI or package name
   */
  public void disablePackage(String packageURIOrName) {
    enablePackage(packageURIOrName, false);
  }

  /**
   * Enables the given SBML Level 3 package on this {@link SBMLDocument}.
   * 
   * @param packageURIOrName a package namespace URI or package name
   */
  public void enablePackage(String packageURIOrName) {
    enablePackage(packageURIOrName, true);
  }

  /**
   * Enables or disables the given SBML Level 3 package on this
   * {@link SBMLDocument}.
   * 
   * @param packageURIOrName
   *        a package namespace URI or package name
   * @param enabled
   *        a boolean to tell if the package need to be enabled or disabled. It
   *        {@code true} the package will be enabled, otherwise it will be
   *        disabled.
   */
  public void enablePackage(String packageURIOrName, boolean enabled) {
    SBMLDocument doc = getParentSBMLObject().getSBMLDocument();

    if (doc != null) {
      doc.enablePackage(packageURIOrName, enabled);
    } else if (isDebugEnabled) {
      logger.debug(resourceBundle.getString("AbstractSBase.enablePackage"));
    }
  }

  /**
   * Returns the {@link ASTNodePlugin} object which matches this package
   * name or URI.
   * 
   * @param nameOrUri
   *        the package name or URI
   * @return the {@link ASTNodePlugin} object which matches this package
   *         name or URI,
   *         null is returned if nothing matching the name or URI is found.
   */
  public ASTNodePlugin getExtension(String nameOrUri) {
    // use always the package name in the map
    PackageParser packageParser = ParserManager.getManager().getPackageParser(nameOrUri);

    if (packageParser != null) {
      return plugins.get(packageParser.getPackageName());
    }

    throw new IllegalArgumentException(MessageFormat.format(
      resourceBundle.getString("AbstractSBase.createPlugin"), nameOrUri));
  }

  /**
   * Returns the number of {@link ASTNodePlugin}s of this {@link ASTNode}.
   * 
   * @return the number of {@link ASTNodePlugin}s of this {@link ASTNode}.
   */
  public int getExtensionCount() {

    if (plugins != null) {
      return plugins.size();
    }

    return 0;
  }

  //  /**
  //   * Returns the map containing all the {@link SBasePlugin} extension objects
  //   * of this {@link SBase}.
  //   *
  //   * @return the map containing all the {@link SBasePlugin} extension objects
  //   * of this {@link SBase}.
  //   */
  //  public Map<String, ASTNodePlugin> getExtensionPackages() { // TODO - remove this method to prevent access to the map directly ?? Or provide a copy of the Map
  //    return plugins;
  //  }

  /**
   * Returns the number of {@link ASTNodePlugin}s of this {@link ASTNode}.
   * 
   * @return the number of {@link ASTNodePlugin}s of this {@link ASTNode}.
   */
  public int getNumPlugins() {
    return getExtensionCount();
  }

  /**
   * Returns an {@link ASTNodePlugin} for an SBML Level 3 package extension
   * with the given package name or URI.
   * <p>
   * If no {@link ASTNodePlugin} is found for this package, a new
   * {@link ASTNodePlugin} is created, added to this {@link ASTNode} and returned.
   * 
   * @param nameOrUri
   *        the name or URI of the package
   * @return an {@link ASTNodePlugin} for an SBML Level 3 package extension
   *         with the given package name or URI.
   */
  public ASTNodePlugin getPlugin(String nameOrUri) {

    // use always the package name in the map
    PackageParser packageParser = ParserManager.getManager().getPackageParser(nameOrUri);

    if (packageParser != null) {
      ASTNodePlugin plugin = plugins.get(packageParser.getPackageName());
      if (plugin != null) {
        return plugin;
      } else {
        return createPlugin(nameOrUri);
      }
    }

    throw new IllegalArgumentException(MessageFormat.format(
      resourceBundle.getString("AbstractSBase.createPlugin"), nameOrUri));
  }

  /**
   * Returns {@code true} if this object is extended by other packages.
   * 
   * @return {@code true} if this object is extended by other packages.
   */
  public boolean isExtendedByOtherPackages() {
    return !plugins.isEmpty();
  }

  /**
   * Returns {@code true} if the given SBML Level 3 package is enabled within
   * the containing {@link SBMLDocument}.
   * 
   * @param packageURIOrName
   *        the name or URI of the package extension.
   * @return {@code true} if the given SBML Level 3 package is enabled within
   *         the containing {@link SBMLDocument}, {@code false} otherwise.
   */
  public boolean isPackageEnabled(String packageURIOrName) {

    SBMLDocument doc = getParentSBMLObject().getSBMLDocument(); // TODO - need to prevent NullPointerException

    if (doc != null) {
      return doc.isPackageEnabled(packageURIOrName);
    }

    return false;
  }

  /**
   * Returns {@code true} if the given SBML Level 3 package is enabled within
   * the containing {@link SBMLDocument}.
   * 
   * @param packageURIOrName
   *        the name or URI of the package extension.
   * @return {@code true} if the given SBML Level 3 package is enabled within
   *         the containing {@link SBMLDocument}, {@code false} otherwise.
   * @libsbml.deprecated
   * @see #isPackageEnabled(String)
   */
  public boolean isPackageURIEnabled(String packageURIOrName) {
    return isPackageEnabled(packageURIOrName);
  }

  /**
   * Returns {@code true} if an {@link ASTNodePlugin} is defined
   * for the given package.
   * 
   * @param nameOrUri the package name or URI
   * @return {@code true} if an {@link ASTNodePlugin} is defined
   * for the given package.
   */
  public boolean isSetPlugin(String nameOrUri) {

    // use always the package name in the map
    PackageParser packageParser = ParserManager.getManager().getPackageParser(nameOrUri);

    if (packageParser != null) {
      return plugins.get(packageParser.getPackageName()) != null;
    }

    throw new IllegalArgumentException(MessageFormat.format(
      resourceBundle.getString("AbstractSBase.createPlugin"), nameOrUri));
  }

  /**
   * Unsets the {@link ASTNodePlugin} extension object which matches this package
   * name or URI.
   * 
   * @param nameOrUri
   *        the package name or URI
   */
  public void unsetExtension(String nameOrUri) {

    // use always the package name in the map
    PackageParser packageParser = ParserManager.getManager().getPackageParser(nameOrUri);

    if (packageParser != null) {

      ASTNodePlugin sbasePlugin = plugins.remove(packageParser.getPackageName());
      firePropertyChange(TreeNodeChangeEvent.extension, sbasePlugin, null);
      return;
    }

    throw new IllegalArgumentException(MessageFormat.format(
      resourceBundle.getString("AbstractSBase.createPlugin"),
      nameOrUri));
  }

  /**
   * Unsets the {@link ASTNodePlugin} plugin object which matches this package
   * name or URI.
   * 
   * @param nameOrUri
   *        the package name or URI
   * @see #unsetExtension(String)
   */
  public void unsetPlugin(String nameOrUri) {
    unsetExtension(nameOrUri);
  }

  /**
   * Returns the map containing all the {@link ASTNodePlugin} extension objects
   * of this {@link ASTNode}.
   * 
   * @return the map containing all the {@link ASTNodePlugin} extension objects
   * of this {@link ASTNode}.
   */
  public Map<String, ASTNodePlugin> getExtensionPackages() {
    return plugins;
  }

}