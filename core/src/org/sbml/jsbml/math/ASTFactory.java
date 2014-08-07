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
import org.sbml.jsbml.MathContainer;
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
   * Creates a new {@link ASTArithmeticOperatorNode} of type MINUS and adds the given nodes as children.
   * Resulting abstract syntax tree will be reduced to binary form.
   * 
   * @param ast the children of the new ASTNode
   * @return a new {@link ASTArithmeticOperatorNode} of type MINUS and adds the given nodes as children
   */
  public static ASTArithmeticOperatorNode diff(ASTNode2... ast) {
    if (ast == null) {
      throw new NullPointerException();
    }
    ASTArithmeticOperatorNode diff = new ASTArithmeticOperatorNode(Type.MINUS);
    for (ASTNode2 astNode : ast) {
      diff.addChild(astNode);
    }
    return diff;
  }
  
  /**
   * Divides an {@link ASTNode2} by another {@link ASTNode2}
   * 
   * @param numerator {@link ASTNode2}
   * @param denominator {@link ASTNode2}
   * 
   * @return node {@link ASTDivideNode}
   */
  public static ASTDivideNode divideBy(ASTNode2 numerator, ASTNode2 denominator) {
    return new ASTDivideNode(numerator, denominator);
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
   * Creates a new {@link ASTDivideNode} with the given nodes as children.
   * 
   * @param numerator the numerator {@link ASTNode2}
   * @param denominator the denominator {@link ASTNode2}
   * @return a new {@link ASTDivideNode} with the given nodes as children.
   */
  public static ASTDivideNode frac(ASTNode2 numerator, ASTNode2 denominator) {
    ASTDivideNode frac = new ASTDivideNode();
    frac.addChild(numerator);
    frac.addChild(denominator);
    return frac;
  }


  /**
   * Creates a new {@link ASTDivideNode} with the given numerator 
   * and denominator.
   * 
   * @param numerator the numerator {@link int}
   * @param denominator the denominator {@link ASTNode2}
   * @return a new {@link ASTDivideNode} with the given numerator
   * and denominator.
   */
  public static ASTDivideNode frac(int numerator, ASTNode2 denominator) {
    ASTDivideNode frac = new ASTDivideNode();
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
    return new ASTLogarithmNode(null, value);

  }


  /**
   * Creates an {@link ASTLogarithmNode} that represents the logarithm function with
   * the given base and value. The parent SBML object will be taken from the
   * {@link ASTNode2} value.
   * 
   * @param base
   *            The basis of this logarithm. Can be null; then a base of 10
   *            will be assumed.
   * @param value
   *            Must not be {@code null}.
   * @return An {@link ASTLogarithmNode} representing the logarithm of the given value
   *         with respect to the given base or to the base 10 if base is {@code null}.
   */
  public static ASTLogarithmNode log(ASTNode2 base, ASTNode2 value) {
    return new ASTLogarithmNode(base, value);
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
    ASTCiNumberNode ci = new ASTCiNumberNode();
    ci.setRefId(variable);
    lt.addChild(ci);
    lt.addChild(node);
    return lt;
  }


  /**
   * Subtracts an {@link ASTNode2} from another {@link ASTNode2}.
   * 
   * @param node1 {@link ASTNode2}
   * @param node2 {@link ASTNode2}
   * 
   * @return minus {@link ASTMinusNode}
   */
  public static ASTMinusNode minus(ASTNode2 node1, ASTNode2 node2) {
    return new ASTMinusNode(node1, node2);
  }


  /**
   * Subtracts the given number from an {@link ASTNode2}
   * 
   * @param node {@link ASTNode2}
   * @param real {@link double}
   * 
   * @return minus {@link ASTMinusNode}
   */
  public static ASTMinusNode minus(ASTNode2 node, double real) {
    return new ASTMinusNode(node, new ASTCnRealNode(real));
  }


  /**
   * Subtracts an integer number from {@link ASTNode2}.
   * 
   * @param node {@link ASTNode2}
   * @param integer {@code int}
   * 
   * @return minus {@link ASTMinusNode}
   */
  public static ASTMinusNode minus(ASTNode2 node, int integer) {
    return new ASTMinusNode(node, new ASTCnIntegerNode(integer));
  }


  /**
   * Subtracts an integer number from {@link ASTNode2} and sets
   * the units of {@link ASTCnIntegerNode} to the specified unitsID.
   * 
   * @param node {@link ASTNode2}
   * @param integer {@code int}
   * @param unitsID {@link String}
   * 
   * @return minus {@link ASTMinusNode}
   */
  public static ASTMinusNode minus(ASTNode2 node, int integer, 
                                                String unitsID) {
    ASTMinusNode minus = new ASTMinusNode();
    minus.addChild(node);
    ASTCnIntegerNode integerNode = new ASTCnIntegerNode(integer);
    integerNode.setUnits(unitsID);
    minus.addChild(integerNode);
    return minus;
  }


  /**
   * Multiplies an {@link ASTNode2} with the given nodes, i.e., all given nodes
   * will be children of this {@link ASTArithmeticOperatorNode}, whose type will 
   * be set to {@link Type#TIMES}. Resulting abstract syntax tree will be reduced
   * to binary form.
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
   * @return times {@link ASTTimesNode}
   */
  public static ASTTimesNode multiplyWith(ASTNode2 node1, ASTNode2 node2) {
    return new ASTTimesNode(node1, node2);
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
   * <p>At least one {@link ASTQualifierNode} must be given
   * as a child. The parent SBML object of this first node will be the parent
   * of the resulting {@link ASTPiecewiseFunctionNode}.
   * 
   * @param node the parent SBML object of this node will be the parent
   * of the resulting {@link ASTPiecewiseFunctionNode}.
   * @param nodes the children of the new piecewise ASTNode2
   * @return a piecewise {@link ASTPiecewiseFunctionNode}.
   */
  public static ASTPiecewiseFunctionNode piecewise(ASTQualifierNode node, ASTQualifierNode... nodes) {
    ASTPiecewiseFunctionNode piecewise = new ASTPiecewiseFunctionNode();
    piecewise.addChild(node);
    for (ASTQualifierNode n : nodes) {
      piecewise.addChild(n);
    }
    if (node.isSetParentSBMLObject()) {
      piecewise.setParentSBMLObject(node.getParentSBMLObject());
    }
    return piecewise;
  }


  /**
   * Adds an {@link ASTNode2} to an {@link ASTNode2}.
   * 
   * @param node1 {@link ASTNode2}
   * @param node2 {@link ASTNode2}
   * 
   * @return plus {@link ASTArithmeticOperatorNode}
   */
  public static ASTPlusNode plus(ASTNode2 node1, ASTNode2 node2) {
    return new ASTPlusNode(node1, node2);
  }


  /**
   * Adds a real number to {@link ASTNode2}.
   * 
   * @param node {@link ASTNode2}
   * @param real {@link double}
   * 
   * @return plus {@link ASTPlusNode}
   */
  public static ASTPlusNode plus(ASTNode2 node, double real) {
    return new ASTPlusNode(node, new ASTCnRealNode(real));
  }


  /**
   * Adds an integer number to {@link ASTNode2}.
   * 
   * @param node {@link ASTNode2}
   * @param integer {@code int}
   * 
   * @return plus {@link ASTPlusNode}
   */
  public static ASTPlusNode plus(ASTNode2 node, int integer) {
    return new ASTPlusNode(node, new ASTCnIntegerNode(integer));
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
   * Creates a power {@link ASTPowerNode} with an {@link ASTNode2} 
   * basis and a real exponent.
   * 
   * @param basis the basis
   * @param exponent the exponent
   * @return a power {@link ASTPowerNode}.
   */
  public static ASTPowerNode pow(ASTNode2 basis, double exponent) {
    return new ASTPowerNode(basis, new ASTCnRealNode(exponent));
  }


  /**
   * Creates a power {@link ASTPowerNode} with an {@link ASTNode2} 
   * basis and an integer exponent.
   * 
   * @param basis the basis
   * @param exponent the exponent
   * @return a power {@link ASTPowerNode}.
   */
  public static ASTPowerNode pow(ASTNode2 basis, int exponent) {
    return new ASTPowerNode(basis, new ASTCnIntegerNode(exponent));
  }

  /**
   * Creates an {@link ASTArithmeticOperatorNode} of type {@link Type.PRODUCT} and 
   * adds the given nodes as children. Resulting abstract syntax tree will be reduced 
   * to binary form.
   * 
   * @param ast 
   * @return an {@link ASTArithmeticOperatorNode} with the given nodes as children.
   */
  public static ASTArithmeticOperatorNode product(ASTNode2... ast) {
    if (ast == null) {
      throw new NullPointerException();
    }
    ASTArithmeticOperatorNode product = new ASTArithmeticOperatorNode(Type.PRODUCT);
    for (ASTNode2 astNode : ast) {
      product.addChild(astNode);
    }
    return product;
  }

  /**
   * <p>
   * Reduces an {@link ASTFunction} to a binary tree, e.g., if the formula in the
   * {@link ASTFunction} is and(x, y, z) then the formula of the reduced node would
   * be and(and(x, y), z).
   * </p>
   * @param node {@link ASTFunction}
   * @return binaryNode {@link ASTBinaryFunctionNode}
   */
  public static ASTBinaryFunctionNode reduceToBinary(ASTFunction node) {
    ASTBinaryFunctionNode rootNode = null, childOperator = null;
    switch(node.getType()) {
    case SUM:
      rootNode = new ASTPlusNode();
      childOperator = new ASTPlusNode();
      break;
    case PRODUCT:
      rootNode = new ASTTimesNode();
      childOperator = new ASTTimesNode();
      break;
    case MINUS:
      rootNode = (ASTBinaryFunctionNode) (node instanceof ASTMinusNode ? node 
      : new ASTMinusNode());
      childOperator = new ASTMinusNode();
      break;
    case TIMES:
    case PLUS:
      rootNode = (ASTBinaryFunctionNode) node;
      childOperator = node.getType() == Type.PLUS ? new ASTPlusNode() 
      : new ASTTimesNode();
      break;
    default:
      throw new IllegalArgumentException("Argument must be of type SUM or PRODUCT");
    }
    if (node.getChildCount() <= 2) {
      if (!rootNode.equals(node)) {
        rootNode.swapChildren(node);        
      }
      return rootNode;
    }
    if (!rootNode.equals(node)) {
      rootNode.addChild(node.getChildAt(0));        
    }
    childOperator.setStrictness(false);
    for (int i = node.getChildCount() - 1; i > 0; i--) {
      childOperator.addChild(node.getListOfNodes().remove(i));
    }
    rootNode.addChild(reduceToBinary(childOperator));
    childOperator.setStrictness(true);
    return rootNode;
  }

  /**
   * Creates a root of type {@link ASTNode2}.
   * 
   * @param radicand the radicand {@link ASTNode2}
   * @param rootExponent the exponent of the root element {@link ASTNode2}
   * @return a root of type {@link ASTRootNode}.
   */
  public static ASTRootNode root(ASTNode2 rootExponent, ASTNode2 radicand) {
    return rootExponent != null ? new ASTRootNode(rootExponent, radicand) : new ASTRootNode(radicand);
  }


  /**
   * Sets the Parent of the node and its children to the given value
   *
   * @param node the orphan node
   * @param parent the parent
   */
  public static void setParentSBMLObject(ASTNode2 node, MathContainer parent) {
    node.setParentSBMLObject(parent);
    if (node.getChildCount() > 0) {
      for (ASTNode2 child : ((ASTFunction)node).getChildren()) {
        setParentSBMLObject(child, parent);        
      }
    }
  }

  /**
   * Creates a square root of type {@link ASTRootNode} with the 
   * specified radicand of type {@link ASTNode2}.
   * 
   * @param radicand {@link ASTNode2}
   * @return a root {@link ASTRootNode}.
   */
  public static ASTRootNode sqrt(ASTNode2 radicand) {
    return root(null, radicand);
  }
  
  /**
   * Creates an {@link ASTArithmeticOperatorNode} of type {@link Type.SUM} and adds
   * the given nodes as children. Resulting abstract syntax tree will be reduced to 
   * binary form.
   * 
   * @param ast 
   * @return an {@link ASTArithmeticOperatorNode} with the given nodes as children.
   */
  public static ASTArithmeticOperatorNode sum(ASTNode2... ast) {
    if (ast == null) {
      throw new NullPointerException();
    }
    ASTArithmeticOperatorNode sum = new ASTArithmeticOperatorNode(Type.SUM);
    for (ASTNode2 astNode : ast) {
      sum.addChild(astNode);
    }
    return sum;
  }
  
  /**
   * Creates a new {@link ASTTimesNode} with exactly two children
   * 
   * @param node1 {@link ASTNode2}
   * @param node2  {@link ASTNode2}
   * 
   * @return a new {@link ASTTimesNode} that has exactly two children
   */
  public static ASTTimesNode times(ASTNode2 node1, ASTNode2 node2) {
    return new ASTTimesNode(node1, node2);
  }
  
  /**
   * Adds a real number to {@link ASTNode2}
   * 
   * @param node1 {@link ASTNode2}
   * @param real {@code double}
   * 
   * @return a new {@link ASTTimesNode} that has exactly two children
   */
  public static ASTTimesNode times(ASTNode2 node1, double real) {
    return new ASTTimesNode(node1, new ASTCnRealNode(real));
  }

  /**
   * Adds an integer to {@link ASTNode2}
   * 
   * @param node1 {@link ASTNode2}
   * @param integer {@code int}
   * 
   * @return a new {@link ASTTimesNode} that has exactly two children
   */
  public static ASTTimesNode times(ASTNode2 node1, int integer) {
    return new ASTTimesNode(node1, new ASTCnIntegerNode(integer));
  }
  
  /**
   * Creates a new {@link ASTMinusNode} that has exactly one child and
   * which is of type {@link Type.MINUS}, i.e., this negates what is encoded in ast.
   * 
   * @param ast {@link ASTNode2}
   * 
   * @return a new {@link ASTMinusNode} that has exactly one child and
   * which is of type minus, i.e., this negates what is encoded in ast.
   */
  public static ASTMinusNode uMinus(ASTNode2 ast) {
    ASTMinusNode um = new ASTMinusNode();
    um.addChild(ast);
    return um;
  }
  
}
