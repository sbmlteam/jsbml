/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * 
 * Copyright (C) 2009-2014  jointly by the following organizations:
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
package org.sbml.jsbml.math;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.CallableSBase;
import org.sbml.jsbml.FunctionDefinition;


/**
 * An enumeration of all possible types that can be represented by an
 * abstract syntax tree node.
 * 
 * @author Andreas Dr&auml;ger
 * @author Victor Kofia
 * @version $Rev$
 * @since 1.0
 * @date Jun 10, 2014
 */
// TODO: We can delete this class again because we can directly use ASTNode.Type and don't need this copy. Otherwise these both types will become out of sync and the effect to ease the transition from the old ASTNode to the new ASTNode2 will be hampered.
public enum Type {
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
    else if (type.equals("and")) {
      return LOGICAL_AND;
    } else if (type.equals("or")) {
      return LOGICAL_OR;
    } else if (type.equals("xor")) {
      return LOGICAL_XOR;
    } else if (type.equals("not")) {
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
    } else if (type.equals(ASTCSymbolTimeNode.URI_TIME_DEFINITION)) {
      return NAME_TIME;
    } else if (type.equals(ASTCSymbolDelayNode.URI_DELAY_DEFINITION)) {
      return FUNCTION_DELAY;
    } else if (type.equals(ASTCSymbolAvogadroNode.URI_AVOGADRO_DEFINITION)) {
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
