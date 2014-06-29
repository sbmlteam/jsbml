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

import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.SBMLException;


/**
 * A collection of static methods that can be used to create new abstract syntax
 * trees or alter the topology of existing ones.
 * 
 * @author Victor Kofia
 * @version $Rev$
 * @since 1.0
 * @date Jun 20, 2014
 */
public class ASTFactory {
  
  /**
   * Creates a new {@link ASTArithmeticOperatorNode} of type MINUS and adds the given nodes as children
   * 
   * @param ast the children of the new ASTNode
   * @return a new {@link ASTArithmeticOperatorNode} of type MINUS and adds the given nodes as children
   */
  public static ASTArithmeticOperatorNode diff(ASTNode2... ast) {
    ASTArithmeticOperatorNode minus = new ASTArithmeticOperatorNode(Type.MINUS);
    if (ast != null) {
      for (ASTNode2 astNode : ast) {
        minus.addChild(astNode);
      }
    }
    reduceToBinary(minus);
    return minus;
  }
  
  /**
   * Divides an {@link ASTNode2} by another {@link ASTNode2}
   * 
   * @param numerator {@link ASTNode2}
   * @param denominator {@link ASTNode2}
   * 
   * @return node {@link ASTDivideFunction}
   */
  public static ASTDivideFunction divideBy(ASTNode2 numerator, ASTNode2 denominator) {
    ASTDivideFunction frac = new ASTDivideFunction();
    frac.addChild(numerator);
    frac.addChild(denominator);
    return frac;
  }
  
  /**
   * Creates a new {@link ASTRelationalOperatorNode} of type RELATIONAL_EQ.
   * 
   * @param left the left child.
   * @param right the right child.
   * @return a new {@link ASTRelationalOperatorNode} of type RELATIONAL_EQ.
   */
  public static ASTRelationalOperatorNode eq(ASTNode2 left, ASTNode2 right) {
    ASTRelationalOperatorNode eq = new ASTRelationalOperatorNode(Type.RELATIONAL_EQ);
    eq.addChild(left);
    eq.addChild(right);
    return eq;
  }
  
  /**
   * Returns a new {@link ASTPowerNode} that represents Euler's constant raised by the
   * power of the given exponent.
   * 
   * @param exponent the exponent
   * @return a new {@link ASTPowerNode} that represents Euler's constant raised by the
   * power of the given exponent.
   */
  public static ASTPowerNode exp(ASTNode2 exponent) {
    ASTConstantNumber e = new ASTConstantNumber(Math.E);
    return new ASTPowerNode(e, exponent);
  }


  /**
   * Returns the formula from the given ASTNode2 as an SBML Level 1 text-string
   *         mathematical formula.
   * 
   * 
   * @param tree
   *            the root of the ASTNode2 formula expression tree
   * @return the formula from the given AST as an SBML Level 1 text-string
   *         mathematical formula. The caller owns the returned string and is
   *         responsible for freeing it when it is no longer needed. {@code null} is
   *         returned if the given argument is {@code null}.
   * @throws SBMLException
   * @see #toFormula()
   * 
   */
  public static String formulaToString(ASTNode2 tree) throws SBMLException {
    return tree.toFormula();
  }


  /**
   * Creates a new {@link ASTDivideFunction} with the given nodes as children.
   * 
   * @param numerator the numerator {@link ASTNode2}
   * @param denominator the denominator {@link ASTNode2}
   * @return a new {@link ASTDivideFunction} with the given nodes as children.
   */
  public static ASTDivideFunction frac(ASTNode2 numerator, ASTNode2 denominator) {
    ASTDivideFunction frac = new ASTDivideFunction();
    frac.addChild(numerator);
    frac.addChild(denominator);
    return frac;
  }


  /**
   * Creates a new {@link ASTDivideFunction} with the given numerator 
   * and denominator.
   * 
   * @param numerator the numerator {@link int}
   * @param denominator the denominator {@link ASTNode2}
   * @return a new {@link ASTDivideFunction} with the given numerator
   * and denominator.
   */
  public static ASTDivideFunction frac(int numerator, ASTNode2 denominator) {
    ASTDivideFunction frac = new ASTDivideFunction();
    frac.addChild(new ASTCnIntegerNode(numerator));
    frac.addChild(denominator);
    return frac;
  }


  /**
   * Creates an {@link ASTRelationalOperatorNode} representing greater or equal for
   * the two given nodes.
   * 
   * @param left the left child.
   * @param right the right child.
   * @return an {@link ASTRelationalOperatorNode} representing greater or equal.
   */
  public static ASTRelationalOperatorNode geq(ASTNode2 left, ASTNode2 right) {
    ASTRelationalOperatorNode geq = new ASTRelationalOperatorNode(Type.RELATIONAL_GEQ);
    geq.addChild(left);
    geq.addChild(right);
    return geq;
  }


  /**
   * Creates an {@link ASTRelationalOperatorNode} representing greater than for
   * the two given left and right child.
   * 
   * @param left the left child.
   * @param right the right child.
   * @return an {@link ASTRelationalOperatorNode} representing greater than for
   * the two given left and right child.
   */
  public static ASTRelationalOperatorNode gt(ASTNode2 left, ASTNode2 right) {
    ASTRelationalOperatorNode gt = new ASTRelationalOperatorNode(Type.RELATIONAL_GT);
    gt.addChild(left);
    gt.addChild(right);
    return gt;
  }


  /**
   * Creates an {@link ASTRelationalOperatorNode} representing less or equal for
   * the two given left and right child.
   * 
   * @param left the left child.
   * @param right the right child.
   * @return an {@link ASTRelationalOperatorNode} representing less or equal for
   * the two given left and right child.
   */
  public static ASTRelationalOperatorNode leq(ASTNode2 left, ASTNode2 right) {
    ASTRelationalOperatorNode leq = new ASTRelationalOperatorNode(Type.RELATIONAL_LEQ);
    leq.addChild(left);
    leq.addChild(right);
    return leq;
  }


  /**
   * Creates an {@link ASTLogarithmNode} representing a logarithm to base 10 of the given value.
   * 
   * @param value the value which is the argument of the logarithm.
   * 
   * @return an {@link ASTLogarithmNode} representing a logarithm to base 10 of the given value.
   */
  public static ASTLogarithmNode log(ASTNode2 value) {
    ASTLogarithmNode log = new ASTLogarithmNode();
    log.setLeftChild(value);
    return log;
  }


  /**
   * Creates an {@link ASTNode2} that represents the logarithm function with
   * the given base and value. The parent SBML object will be taken from the
   * {@link ASTNode2} value.
   * 
   * @param base
   *            The basis of this logarithm. Can be null; then a base of 10
   *            will be assumed.
   * @param value
   *            Must not be {@code null}.
   * @return An {@link ASTNode2} representing the logarithm of the given value
   *         with respect to the given base or to the base 10 if base is {@code null}.
   */
  public static ASTLogarithmNode log(ASTNode2 base, ASTNode2 value) {
    if (value == null) {
      throw new NullPointerException(
          "logarithm cannot be created for null values");
    }
    ASTLogarithmNode log = new ASTLogarithmNode(base, value);
    if (base != null) {
      log.setLeftChild(base);
    }
    log.setRightChild(value);
    log.setParentSBMLObject(log.getParentSBMLObject());
    return log;
  }


  /**
   * Creates a {@link ASTRelationalOperatorNode} that performs a less than comparison between
   * two {@link ASTNode2}s. The parent SBML object of the resulting node will
   * be taken from the left node.
   * 
   * @param left the left child.
   * @param right the right child.
   * 
   * @return an {@link ASTRelationalOperatorNode} that performs a less than comparison between
   * two {@link ASTNode2}s.
   */
  public static ASTRelationalOperatorNode lt(ASTNode2 left, ASTNode2 right) {
    ASTRelationalOperatorNode lt = new ASTRelationalOperatorNode(Type.RELATIONAL_LT);
    lt.addChild(left);
    lt.addChild(right);
    return lt;
  }


  /**
   * Creates an {@link ASTRelationalOperatorNode} that performs a less than comparison between a
   * variable and another {@link ASTNode2}. The parent SBML object will be
   * taken from the given {@link ASTNode2}.
   * 
   * @param variable the left child.
   * @param node the right child.
   * 
   * @return an {@link ASTRelationalOperatorNode} that performs a less than comparison between a
   * variable and another {@link ASTNode2}.
   */
  public static ASTRelationalOperatorNode lt(String variable, ASTNode2 node) {
    ASTRelationalOperatorNode lt = new ASTRelationalOperatorNode(Type.RELATIONAL_LT);
    lt.addChild(node);
    lt.addChild(new ASTCnNumberNode(variable));
    return lt;
  }


  /**
   * Subtracts an integer number from {@link ASTCnIntegerNode}.
   * 
   * @param node {@link ASTCnIntegerNode}
   * @param integer {@code int}
   * 
   * @return minus {@link ASTArithmeticOperatorNode}
   */
  public static ASTArithmeticOperatorNode minus(ASTCnIntegerNode node, int integer) {
    ASTArithmeticOperatorNode minus = new ASTArithmeticOperatorNode(Type.MINUS);
    minus.addChild(node);
    minus.addChild(new ASTCnIntegerNode(integer));
    return minus;
  }


  /**
   * Subtracts an integer number from {@link ASTCnIntegerNode} and sets
   * the units of {@link ASTCnIntegerNode} to the specified unitsID.
   * 
   * @param node {@link ASTCnIntegerNode}
   * @param integer {@code int}
   * @param unitsID {@link String}
   * 
   * @return minus {@link ASTArithmeticOperatorNode}
   */
  public static ASTArithmeticOperatorNode minus(ASTCnIntegerNode node, int integer, 
                                                String unitsID) {
    ASTArithmeticOperatorNode minus = new ASTArithmeticOperatorNode(Type.MINUS);
    minus.addChild(node);
    ASTCnIntegerNode integerNode = new ASTCnIntegerNode(integer);
    integerNode.setUnits(unitsID);
    minus.addChild(integerNode);
    return minus;
  }


  /**
   * Subtracts the given number from an {@link ASTCnRealNode}
   * 
   * @param node {@link ASTCnRealNode}
   * @param real {@link double}
   * 
   * @return minus {@linkASTArithmeticOperatorNode}
   */
  public static ASTArithmeticOperatorNode minus(ASTCnRealNode node, double real) {
    ASTArithmeticOperatorNode minus = new ASTArithmeticOperatorNode(Type.MINUS);
    minus.addChild(node);
    minus.addChild(new ASTCnRealNode(real));
    return minus;
  }


  /**
   * Subtracts an {@link ASTNode2} from another {@link ASTNode2}.
   * 
   * @param node1 {@link ASTNode2}
   * @param node2 {@link ASTNode2}
   * 
   * @return minus {@link ASTArithmeticOperatorNode}
   */
  public static ASTArithmeticOperatorNode minus(ASTNode2 node1, ASTNode2 node2) {
    ASTArithmeticOperatorNode minus = new ASTArithmeticOperatorNode(Type.MINUS);
    minus.addChild(node1);
    minus.addChild(node2);
    return minus;
  }


  /**
   * Multiplies an {@link ASTNode2} with the given nodes, i.e., all given nodes
   * will be children of this {@link ASTArithmeticOperatorNode}, whose type will 
   * be set to {@link Type#TIMES}.
   * 
   * @param nodes
   *            some {@code ASTNode2}
   * @return times {@link ASTArithmeticOperatorNode}
   */
  public static ASTArithmeticOperatorNode multiplyWith(ASTNode2... nodes) {
    ASTArithmeticOperatorNode times = new ASTArithmeticOperatorNode(Type.TIMES);
    for (ASTNode2 node : nodes) {
      times.addChild(node);
    }
    reduceToBinary(times);
    return times;
  }


  /**
   * Multiplies an {@link ASTNode2} with another {@link ASTNode2}.
   * 
   * @param node1 {@link ASTNode2}
   * @param node2 {@link ASTNode2}
   * 
   * @return times {@link ASTArithmeticOperatorNode}
   */
  public static ASTArithmeticOperatorNode multiplyWith(ASTNode2 node1, ASTNode2 node2) {
    ASTArithmeticOperatorNode times = new ASTArithmeticOperatorNode(Type.TIMES);
    times.addChild(node1);
    times.addChild(node2);
    return times;
  }


  /**
   * Creates an {@link ASTNode2} that performs a not equal comparison between
   * two {@link ASTNode2}s.
   * 
   * @param left the left child.
   * @param right the right child.
   * @return an {@link ASTRelationalOperatorNode} that performs a not equal comparison between
   * two {@link ASTNode2}s.
   */
  public static ASTRelationalOperatorNode neq(ASTNode2 left, ASTNode2 right) {
    ASTRelationalOperatorNode neq = new ASTRelationalOperatorNode(Type.RELATIONAL_NEQ);
    neq.addChild(left);
    neq.addChild(right);
    return neq;
  }


  /**
   * Creates a piecewise {@link ASTPiecewiseFunctionNode}.
   * 
   * <p>At least one {@link ASTNode2} must be given
   * as a child. The parent SBML object of this first node will be the parent
   * of the resulting {@link ASTNode2}.
   * 
   * @param node the parent SBML object of this node will be the parent
   * of the resulting {@link ASTNode2}.
   * @param nodes the children of the new piecewise ASTNode2
   * @return a piecewise {@link ASTPiecewiseFunctionNode}.
   */
  public static ASTPiecewiseFunctionNode piecewise(ASTNode2 node, ASTNode2... nodes) {
    ASTPiecewiseFunctionNode piecewise = new ASTPiecewiseFunctionNode();
    for (ASTNode2 n : nodes) {
      piecewise.addChild(n);
    }
    piecewise.setParentSBMLObject(node.getParentSBMLObject());
    reduceToBinary(piecewise);
    return piecewise;
  }


  /**
   * Adds an integer number to {@link ASTCnIntegerNode}.
   * 
   * @param node {@link ASTCnIntegerNode}
   * @param integer {@link int}
   * 
   * @return plus {@link ASTArithmeticOperatorNode}
   */
  public static ASTArithmeticOperatorNode plus(ASTCnIntegerNode node, int integer) {
    ASTArithmeticOperatorNode plus = new ASTArithmeticOperatorNode(Type.PLUS);
    plus.addChild(node);
    plus.addChild(new ASTCnIntegerNode(integer));
    return plus;
  }


  /**
   * Adds a number to {@link ASTCnRealNode}.
   * 
   * @param node {@link ASTCnRealNode}
   * @param real {@link double}
   * 
   * @return plus {@link ASTArithmeticOperatorNode}
   */
  public static ASTArithmeticOperatorNode plus(ASTCnRealNode node, double real) {
    ASTArithmeticOperatorNode plus = new ASTArithmeticOperatorNode(Type.PLUS);
    plus.addChild(node);
    plus.addChild(new ASTCnRealNode(real));
    return plus;
  }


  /**
   * Adds an {@link ASTNode2} to an {@link ASTNode2}.
   * 
   * @param node1 {@link ASTNode2}
   * @param node2 {@link ASTNode2}
   * 
   * @return plus {@link ASTArithmeticOperatorNode}
   */
  public static ASTArithmeticOperatorNode plus(ASTNode2 node1, ASTNode2 node2) {
    ASTArithmeticOperatorNode plus = new ASTArithmeticOperatorNode(Type.PLUS);
    plus.addChild(node1);
    plus.addChild(node2);
    return plus;
  }


  /**
   * Creates a power {@link ASTNode2}.
   * 
   * @param basis the basis
   * @param exponent the exponent
   * @return a power {@link ASTPowerNode}.
   */
  public static ASTPowerNode pow(ASTNode2 basis, ASTNode2 exponent) {
    return new ASTPowerNode(basis, exponent);
  }


  /**
   * Creates a power {@link ASTNode2}.
   * 
   * @param basis the basis
   * @param exponent the exponent
   * @return a power {@link ASTPowerNode}.
   */
  public static ASTPowerNode pow(ASTNode2 basis, double exponent) {
    return new ASTPowerNode(basis, new ASTCnRealNode(exponent));
  }


  /**
   * Creates a power {@link ASTNode2}.
   * 
   * @param basis the basis
   * @param exponent the exponent
   * @return a power {@link ASTPowerNode}.
   */
  public static ASTPowerNode pow(ASTNode2 basis, int exponent) {
    return new ASTPowerNode(basis, new ASTCnIntegerNode(exponent));
  }

  /**
   * <p>
   * Reduces an {@link ASTFunction} to a binary tree, e.g., if the formula in the
   * {@link ASTFunction} is and(x, y, z) then the formula of the reduced node would
   * be and(and(x, y), z).
   * </p>
   * @param node {@link ASTFunction}
   */
  public static void reduceToBinary(ASTFunction node) {
    if (node.getChildCount() > 2) {
      ASTArithmeticOperatorNode operator = new ASTArithmeticOperatorNode(node.getType());
      for (int i = node.getChildCount() - 1; i > 0; i--) {
        operator.addChild(node.getListOfNodes().remove(i));
      }
      node.addChild(operator);
    }
    for (ASTNode2 child : node.getListOfNodes()) {
      if (child instanceof ASTFunction) {
        reduceToBinary((ASTFunction) child);
      }
    }
  }

  /**
   * Creates a root of type {@link ASTNode2}.
   * 
   * @param radicand the radicand {@link ASTNode2}
   * @param rootExponent the exponent of the root element {@link ASTNode2}
   * @return a root of type {@link ASTRootNode}.
   */
  public static ASTRootNode root(ASTNode2 rootExponent, ASTNode2 radicand) {
    if ((rootExponent == null) || (radicand == null)) {
      throw new NullPointerException(
          "Cannot create a root node with null arguments.");
    }
    return new ASTRootNode(rootExponent, radicand);
  }


  /**
   * Creates a square root of type {@link ASTRootNode} with the 
   * specified radicand of type {@link ASTNode2}.
   * 
   * @param radicand {@link ASTNode2}
   * @return a root {@link ASTRootNode}.
   */
  public static ASTRootNode sqrt(ASTNode2 radicand) {
    if (radicand == null) {
      throw new NullPointerException(
          "Cannot create a root node with null arguments");
    }
    return root(new ASTCnIntegerNode(2), radicand);
  }


  /**
   * Creates an {@link ASTArithmeticOperatorNode} of type {@link Type.PLUS} and adds
   * the given nodes as children.
   * 
   * @param ast 
   * @return an {@link ASTArithmeticOperatorNode} with the given nodes as children.
   */
  public static ASTArithmeticOperatorNode sum(ASTNode2... ast) {
    ASTArithmeticOperatorNode plus = new ASTArithmeticOperatorNode(Type.PLUS);
    if (ast != null) {
      for (ASTNode2 astNode : ast) {
        plus.addChild(astNode);
      }
    }
    reduceToBinary(plus);
    return plus;
  }


  /**
   * Creates an {@link ASTArithmeticOperatorNode} of type {@link Type.TIMES} and 
   * adds the given nodes as children.
   * 
   * @param ast 
   * @return an {@link ASTArithmeticOperatorNode} with the given nodes as children.
   */
  public static ASTArithmeticOperatorNode times(ASTNode2... ast) {
    ASTArithmeticOperatorNode times = new ASTArithmeticOperatorNode(Type.TIMES);
    if (ast != null) {
      for (ASTNode2 astNode : ast) {
        times.addChild(astNode);
      }
    }
    reduceToBinary(times);
    return times;
  }


  /**
   * Creates a new {@link ASTArithmeticOperatorNode} that has exactly one child and
   * which is of type {@link Type.MINUS}, i.e., this negates what is encoded in ast.
   * 
   * @param ast {@link ASTNode2}
   * 
   * @return a new {@link ASTArithmeticOperatorNode} that has exactly one child and
   * which is of type minus, i.e., this negates what is encoded in ast.
   */
  public static ASTArithmeticOperatorNode uMinus(ASTNode2 ast) {
    ASTArithmeticOperatorNode um = new ASTArithmeticOperatorNode(Type.MINUS);
    um.addChild(ast);
    return um;
  }
  
}
