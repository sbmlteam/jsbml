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
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.util.Maths;


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
   * Creates a new node with the type of this node, moves all children of this
   * node to this new node, sets the type of this node to the given operator,
   * adds the new node as left child of this node and the given {@link ASTNode2} as the
   * right child of this node. The parentSBMLObject of the whole resulting
   * {@link ASTNode2} is then set to the parent of this node.
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
   *         <li>this {@link ASTNode2} is zero ({@link #isZero()}) and the given
   *         operator is {@link Type#DIVIDE}</li>
   *         <li>the operator is not one of the following: {@link Type#PLUS},
   *         {@link Type#MINUS}, {@link Type#TIMES}, {@link Type#DIVIDE},
   *         {@link Type#POWER}, {@link Type#FUNCTION_ROOT}</li>
   *         </ul>
   */
  public static ASTNode2 arithmeticOperation(Type operator, ASTNode2 node1, 
                                         ASTNode2 node2) {
    return null;
  }
  
  public static ASTNode2 arithmeticOperation(Type operator, ASTNode2... node) {
    return null;
  }
  
  /**
   * Creates a new {@link ASTNode2} of type MINUS and adds the given nodes as children
   * 
   * @param ast the children of the new ASTNode
   * @return a new {@link ASTNode2} of type MINUS and adds the given nodes as children
   */
  public static ASTNode2 diff(ASTNode2... ast) {
    return arithmeticOperation(Type.MINUS, ast);
  }
  
  /**
   * Divides an {@link ASTNode2} by another {@link ASTNode2}
   * 
   * @param numerator {@link ASTNode2}
   * @param denominator {@link ASTNode2}
   * 
   * @return node {@link ASTNode2}
   */
  public static ASTNode2 divideBy(ASTNode2 numerator, ASTNode2 denominator) {
    return arithmeticOperation(Type.DIVIDE, numerator, denominator);
  }
  
  /**
   * Creates a new {@link ASTNode2} of type RELATIONAL_EQ.
   * 
   * @param left the left child.
   * @param right the right child.
   * @return a new {@link ASTNode2} of type RELATIONAL_EQ.
   */
  public static ASTNode2 eq(ASTNode2 left, ASTNode2 right) {
    return relational(Type.RELATIONAL_EQ, left, right);
  }
  
  /**
   * Returns a new {@link ASTNode2} that represents Euler's constant raised by the
   * power of the given exponent.
   * 
   * @param exponent the exponent
   * @return a new {@link ASTNode2} that represents Euler's constant raised by the
   * power of the given exponent.
   */
  public static ASTNode2 exp(ASTNode2 exponent) {
    ASTNode2 e = new ASTConstantNumber(Math.E);
    return raiseByThePowerOf(e, exponent);
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
   * Creates a new {@link ASTNode2} of type {@link ASTDivideFunction} with the given nodes as children.
   * 
   * @param numerator the numerator {@link ASTNode2}
   * @param denominator the denominator {@link ASTNode2}
   * @return a new {@link ASTNode2} of type {@link ASTDivideFunction} with the given nodes as children.
   */
  public static ASTNode2 frac(ASTNode2 numerator, ASTNode2 denominator) {
    return divideBy(numerator, denominator);
  }


  /**
   * Creates a new {@link ASTNode2} of type {@link ASTDivideFunction} with the given numerator
   * and denominator.
   * 
   * @param numerator the numerator {@link int}
   * @param denominator the denominator {@link ASTNode2}
   * @return a new {@link ASTNode2} of type {@link ASTDivideFunction} with the given numerator
   * and denominator.
   */
  public static ASTNode2 frac(int numerator, ASTNode2 denominator) {
    return frac(new ASTCnIntegerNode(numerator), denominator);
  }


  /**
   * Creates an {@link ASTNode2} representing greater or equal for
   * the two given nodes.
   * 
   * @param left the left child.
   * @param right the right child.
   * @return an {@link ASTNode2} representing greater or equal.
   */
  public static ASTNode2 geq(ASTNode2 left, ASTNode2 right) {
    return relational(Type.RELATIONAL_GEQ, left, right);
  }


  /**
   * Creates an {@link ASTNode2} representing greater than for
   * the two given left and right child.
   * 
   * @param left the left child.
   * @param right the right child.
   * @return an {@link ASTNode2} representing greater than for
   * the two given left and right child.
   */
  public static ASTNode2 gt(ASTNode2 left, ASTNode2 right) {
    return relational(Type.RELATIONAL_GT, left, right);
  }


  /**
   * Creates an {@link ASTNode2} representing less or equal for
   * the two given left and right child.
   * 
   * @param left the left child.
   * @param right the right child.
   * @return an {@link ASTNode2} representing less or equal for
   * the two given left and right child.
   */
  public static ASTNode2 leq(ASTNode2 left, ASTNode2 right) {
    return relational(Type.RELATIONAL_LEQ, left, right);
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
  public static ASTNode2 log(ASTNode2 base, ASTNode2 value) {
    if (value == null) {
      throw new NullPointerException(
          "logarithm cannot be created for null values");
    }
    ASTFunction log = new ASTLogarithmNode(base, value);
    if (base != null) {
      log.addChild(base);
    }
    log.addChild(value);
    log.setParentSBMLObject(log.getParentSBMLObject());
    return log;
  }


  /**
   * Creates an {@link ASTNode2} representing a logarithm to base 10 of the given value.
   * 
   * @param value the value which is the argument of the logarithm.
   * 
   * @return an {@link ASTNode2} representing a logarithm to base 10 of the given value.
   */
  public static ASTNode2 log(ASTNode2 value) {
    return log(null, value);
  }


  /**
   * Creates a {@link ASTNode2} that performs a less than comparison between
   * two {@link ASTNode2}s. The parent SBML object of the resulting node will
   * be taken from the left node.
   * 
   * @param left the left child.
   * @param right the right child.
   * 
   * @return an {@link ASTNode2} that performs a less than comparison between
   * two {@link ASTNode2}s.
   */
  public static ASTNode2 lt(ASTNode2 left, ASTNode2 right) {
    return relational(Type.RELATIONAL_LT, left, right);
  }


  /**
   * Creates an {@link ASTNode2} that performs a less than comparison between a
   * variable and another {@link ASTNode2}. The parent SBML object will be
   * taken from the given {@link ASTNode2}.
   * 
   * @param variable the left child.
   * @param node the right child.
   * 
   * @return an {@link ASTNode2} that performs a less than comparison between a
   * variable and another {@link ASTNode2}.
   */
  public static ASTNode2 lt(String variable, ASTNode2 node) {
    return lt(new ASTCnNumberNode(variable), node);
  }


  /**
   * Subtracts an integer number from {@link ASTCnIntegerNode}.
   * 
   * @param node {@link ASTCnIntegerNode}
   * @param integer {@code int}
   * 
   * @return node {@link ASTNode2}
   */
  public static ASTNode2 minus(ASTCnIntegerNode node, int integer) {
    if (node != null) {
      node.setValue(node.getValue() + integer);
    }
    return node;
  }


  /**
   * Subtracts an integer number from {@link ASTCnIntegerNode} and sets
   * the units of {@link ASTCnIntegerNode} to the specified unitsID.
   * 
   * @param node {@link ASTCnIntegerNode}
   * @param integer {@code int}
   * @param unitsID {@link String}
   * 
   * @return node {@link ASTNode2}
   */
  public static ASTNode2 minus(ASTCnIntegerNode node, int integer, String unitsID) {
    if (node != null) {
      node.setValue(node.getValue() + integer);
      node.setUnits(unitsID);
    }
    return node;
  }


  /**
   * Subtracts an {@link ASTNode2} from another {@link ASTNode2}.
   * 
   * @param node1 {@link ASTNode2}
   * @param node2 {@link ASTNode2}
   * 
   * @return node {@link ASTNode2}
   */
  public static ASTNode2 minus(ASTNode2 node1, ASTNode2 node2) {
    return arithmeticOperation(Type.MINUS, node1, node2);
  }


  /**
   * Subtracts the given number from an {@link ASTCnRealNode}
   * 
   * @param node {@link ASTCnRealNode}
   * @param real {@link double}
   * 
   * @return node {@link ASTCnRealNode}
   */
  public static ASTNode2 minus(ASTCnRealNode node, double real) {
    if (node != null) {
      node.setValue(node.getValue() - real);
    }
    return node;
  }


  /**
   * Multiplies an {@link ASTNode2} with the given nodes, i.e., all given nodes
   * will be children of this node, whose type will be set to {@link Type#TIMES}.
   * 
   * @param nodes
   *            some {@code ASTNode2}
   * @return node {@link ASTNode2}
   */
  public static ASTNode2 multiplyWith(ASTNode2... nodes) {
    ASTNode2 node = null;
    for (int i = 0; i < nodes.length; i++) {
      node = nodes[i];
      multiplyWith(node);
    }
    reduceToBinary(node);
    return nodes[0];
  }


  /**
   * Multiplies an {@link ASTNode2} with another {@link ASTNode2}.
   * 
   * @param node1 {@link ASTNode2}
   * @param node2 {@link ASTNode2}
   * 
   * @return node {@link ASTNode2}
   */
  public static ASTNode2 multiplyWith(ASTNode2 node1, ASTNode2 node2) {
    return arithmeticOperation(Type.TIMES, node1, node2);
  }


  /**
   * Creates an {@link ASTNode2} that performs a not equal comparison between
   * two {@link ASTNode2}s.
   * 
   * @param left the left child.
   * @param right the right child.
   * @return an {@link ASTNode2} that performs a not equal comparison between
   * two {@link ASTNode2}s.
   */
  public static ASTNode2 neq(ASTNode2 left, ASTNode2 right) {
    return relational(Type.RELATIONAL_NEQ, left, right);
  }


  /**
   * Creates a piecewise {@link ASTNode2}.
   * 
   * <p>At least one {@link ASTNode2} must be given
   * as a child. The parent SBML object of this first node will be the parent
   * of the resulting {@link ASTNode2}.
   * 
   * @param node the parent SBML object of this node will be the parent
   * of the resulting {@link ASTNode2}.
   * @param nodes the children of the new piecewise ASTNode2
   * @return a piecewise {@link ASTNode2}.
   */
  public static ASTNode2 piecewise(ASTNode2 node, ASTNode2... nodes) {
    ASTPiecewiseFunctionNode piecewise = new ASTPiecewiseFunctionNode();
    for (ASTNode2 n : nodes) {
      piecewise.addChild(n);
    }
    if (nodes.length > 0) {
      piecewise.unsetParentSBMLObject();
    }
    return piecewise;
  }


  /**
   * Adds an integer number to {@link ASTCnIntegerNode}.
   * 
   * @param node {@link ASTCnIntegerNode}
   * @param integer {@link int}
   * 
   * @return node {@link ASTNode2}
   */
  public static ASTNode2 plus(ASTCnIntegerNode node, int integer) {
    if (node != null) {
      node.setValue(node.getValue() + integer);
    }
    return node;
  }


  /**
   * Adds a number to {@link ASTCnRealNode}.
   * 
   * @param node {@link ASTCnRealNode}
   * @param real {@link double}
   * 
   * @return node {@link ASTNode2}
   */
  public static ASTNode2 plus(ASTCnRealNode node, double real) {
    if (node != null) {
      node.setValue(node.getValue() + real);
    }
    return node;
  }


  /**
   * Adds an {@link ASTNode2} to an {@link ASTNode2}.
   * 
   * @param node1 {@link ASTNode2}
   * @param node2 {@link ASTNode2}
   * 
   * @return node {@link ASTNode2}
   */
  public static ASTNode2 plus(ASTNode2 node1, ASTNode2 node2) {
    return arithmeticOperation(Type.PLUS, node1, node2);
  }


  /**
   * Creates a power {@link ASTNode2}.
   * 
   * @param basis the basis
   * @param exponent the exponent
   * @return a power {@link ASTNode2}.
   */
  public static ASTNode2 pow(ASTNode2 basis, ASTNode2 exponent) {
    // TODO: IMPLEMENT
    return null;
  }


  /**
   * Creates a power {@link ASTNode2}.
   * 
   * @param basis the basis
   * @param exponent the exponent
   * @return a power {@link ASTNode2}.
   */
  public static ASTNode2 pow(ASTNode2 basis, double exponent) {
    return raiseByThePowerOf(basis, exponent);
  }


  /**
   * Creates a power {@link ASTNode2}.
   * 
   * @param basis the basis
   * @param exponent the exponent
   * @return a power {@link ASTNode2}.
   */
  public static ASTNode2 pow(ASTNode2 basis, int exponent) {
    return raiseByThePowerOf(basis, exponent);
  }


  /**
   * Raises this {@link ASTNode2} by the power of the value of the given node.
   * 
   * @param node1 {@link ASTNode2}
   * @param node2 {@link ASTNode2}
   * 
   * @return the current node for convenience.
   */
  public static ASTNode2 raiseByThePowerOf(ASTNode2 node1, ASTNode2 node2) {
    return arithmeticOperation(Type.POWER, node1, node2); 
  }


  /**
   * Raises this {@link ASTNode2} by the power of the given number.
   * 
   * @param exponent
   *            a double number.
   * @return the current node for convenience.
   */
  public static ASTNode2 raiseByThePowerOf(ASTNode2 node, double exponent) {
    // TODO: IMPLEMENT
    return null;
  }


  /**
   * <p>
   * Reduces an {@link ASTNode2} to a binary tree, e.g., if the formula in the
   * {@link ASTNode2} is and(x, y, z) then the formula of the reduced node would
   * be and(and(x, y), z).
   * </p>
   * <p>
   * This method is not yet completed. Currently, only {@link Type#PLUS},
   * {@link Type#TIMES}, {@link Type#LOGICAL_AND}, {@link Type#LOGICAL_OR} are
   * touched by the method. All other nodes are left unchanged, but it traverses
   * the entire tree rooted at this node.
   * </p>
   * 
   * @param node {@link ASTNode2}
   */
  private static void reduceToBinary(ASTNode2 node) {
    // TODO: IMPLEMENT
    return;
  }


  /**
   * Creates a relational {@link ASTNode2} of the given type with the two given
   * children left and right.
   * <p> Sets the parent SBML object of all nodes to
   * the one provided by the left child.
   * 
   * @param type the type of relational node.
   * @param left the left child.
   * @param right the right child.
   * @return a relational {@link ASTNode2} of the given type with the two given
   * children left and right.
   */
  private static ASTNode2 relational(Type type, ASTNode2 left, ASTNode2 right) {
    if ((left == null) || (right == null)) {
      throw new NullPointerException(
          "Cannot create a relational node with null arguments.");
    }
    ASTRelationalOperatorNode relational = new ASTRelationalOperatorNode();
    relational.addChild(left);
    relational.addChild(right);
    relational.unsetParentSBMLObject();
    return relational;
  }


  /**
   * Creates a root {@link ASTNode2}.
   * 
   * @param radicand the radicand
   * @param rootExponent the exponent of the root element.
   * @return a root {@link ASTNode2}.
   */
  public static ASTNode2 root(ASTNode2 rootExponent, ASTNode2 radicand) {
    // TODO: IMPLEMENT
    return null;
  }


  /**
   * Creates a root {@link ASTNode2}.
   * 
   * @param radicand
   * @return a root {@link ASTNode2}.
   */
  public static ASTNode2 sqrt(ASTNode2 radicand) {
    return root(new ASTSqrtNode(), radicand);
  }


  /**
   * Creates a new {@link ASTNode2} of type Plus with the given nodes as children.
   * 
   * @param ast the children nodes.
   * @return a new {@link ASTNode2} of type Plus with the given nodes as children.
   */
  public static ASTNode2 sum(ASTNode2... ast) {
    return arithmeticOperation(Type.PLUS, ast);
  }


  /**
   * Creates an {@link ASTNode2} of type times and adds the given nodes as children.
   * 
   * @param ast
   * @return an {@link ASTNode2} of type times and adds the given nodes as children.
   */
  public static ASTNode2 times(ASTNode2... ast) {
    return arithmeticOperation(Type.TIMES, ast);
  }


  /**
   * Creates a new {@link ASTNode2} that has exactly one child and which is of type
   * minus, i.e., this negates what is encoded in ast.
   * 
   * @param ast {@link ASTNode2}
   * 
   * @return a new {@link ASTNode2} that has exactly one child and which is of type
   * minus, i.e., this negates what is encoded in ast.
   */
  public static ASTNode2 uMinus(ASTNode2 ast) {
    ASTArithmeticOperatorNode um = new ASTArithmeticOperatorNode(Type.MINUS);
    um.addChild(ast);
    return um;
  }
  
}
