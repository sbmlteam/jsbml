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
package org.sbml.jsbml.math;

import org.apache.log4j.Logger;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.math.compiler.ASTNode2Compiler;
import org.sbml.jsbml.math.compiler.ASTNode2Value;
import org.sbml.jsbml.math.compiler.FormulaCompiler;
import org.sbml.jsbml.math.compiler.LaTeXCompiler;
import org.sbml.jsbml.math.compiler.MathMLXMLStreamCompiler;

/**
 * An Abstract Syntax Tree (AST) node representing a root function
 * 
 * @author Victor Kofia
 * @since 1.0
 */
public class ASTRootNode extends ASTBinaryFunctionNode {

  /**
   * 
   */
  private static final long serialVersionUID = -8803559492792642628L;

  /**
   * A {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger(ASTRootNode.class);

  /**
   * Creates a new {@link ASTRootNode}.
   */
  public ASTRootNode() {
    super();
    setType(Type.FUNCTION_ROOT);
  }

  /**
   * Creates a new {@link ASTRootNode} with the specified radicand and
   * a root exponent of 2.
   * 
   * @param radicand {@link ASTNode2}
   */
  public ASTRootNode(ASTNode2 radicand) {
    this();
    addChild(radicand);
  }

  /**
   * Creates a new {@link ASTRootNode} with the specified radicand and
   * rootExponent.
   * 
   * @param rootExponent {@link ASTNode2}
   * @param radicand {@link ASTNode2}
   */
  public ASTRootNode(ASTNode2 rootExponent, ASTNode2 radicand) {
    this();
    addChild(rootExponent);
    addChild(radicand);
  }

  /**
   * Copy constructor; Creates a deep copy of the given {@link ASTRootNode}.
   * 
   * @param node
   *            the {@link ASTRootNode} to be copied.
   */
  public ASTRootNode(ASTRootNode node) {
    super(node);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTUnaryFunctionNode#clone()
   */
  @Override
  public ASTRootNode clone() {
    return new ASTRootNode(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#compile(org.sbml.jsbml.util.compilers.ASTNode2Compiler)
   */
  @Override
  public ASTNode2Value<?> compile(ASTNode2Compiler compiler) {
    ASTNode2Value<?> value = null;
    ASTNode2 left = getLeftChild();
    if (getChildCount() == 2) {
      if (left instanceof ASTCnIntegerNode) {
        int leftValue = ((ASTCnIntegerNode)left).getInteger();
        if (leftValue == 2) {
          value = compiler.sqrt(getRightChild());
        } else {
          value = compiler.root(leftValue, getRightChild());
        }
      } else if (left instanceof ASTCnRealNode) {
        double leftValue = ((ASTCnRealNode)left).getReal();
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
    return processValue(value);
  }

  /**
   * Return the radicand of this {@link ASTRootNode}
   * 
   * @return radicand {@link ASTNode2}
   */
  public ASTNode2 getRadicand() {
    if (isSetRightChild()) {
      return getRightChild();
    }
    PropertyUndefinedError error = new PropertyUndefinedError("radicand", this);
    if (isStrict()) {
      throw error;
    }
    logger.warn(error);
    return null;
  }

  /**
   * Return the root exponent of this {@link ASTRootNode}
   * 
   * @return rootExponent {@link ASTNode2}
   */
  public ASTNode2 getRootExponent() {
    if (getChildCount() > 1) {
      return getLeftChild();
    }
    return new ASTCnIntegerNode(2);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTFunction#isAllowableType(org.sbml.jsbml.ASTNode.Type)
   */
  @Override
  public boolean isAllowableType(Type type) {
    return type == Type.FUNCTION_ROOT;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTBinaryFunctionNode#isSetLeftChild()
   */
  @Override
  public boolean isSetLeftChild() {
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTBinaryFunctionNode#isSetRightChild()
   */
  @Override
  public boolean isSetRightChild() {
    return getChildCount() > 0;
  }

  /**
   * Set the radicand of this {@link ASTRootNode}
   * 
   * @param radicand {@link ASTNode2}
   */
  public void setRadicand(ASTNode2 radicand) {
    setRightChild(radicand);
  }

  /**
   * Set the root exponent of this {@link ASTRootNode}
   * 
   * @param rootExponent {@link ASTNode2}
   */
  public void setRootExponent(ASTNode2 rootExponent) {
    switch(getChildCount()) {
    case 0:
      addChild(rootExponent);
      break;
    case 1:
      insertChild(0, rootExponent);
      break;
    default:
      replaceChild(0, rootExponent);
      break;
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.AbstractASTNode#toFormula()
   */
  @Override
  public String toFormula() throws SBMLException {
    return compile(new FormulaCompiler()).toString();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.AbstractASTNode#toLaTeX()
   */
  @Override
  public String toLaTeX() throws SBMLException {
    return compile(new LaTeXCompiler()).toString();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.AbstractASTNode#toMathML()
   */
  @Override
  public String toMathML() {
    try {
      return MathMLXMLStreamCompiler.toMathML(this);
    } catch (RuntimeException e) {
      logger.error("Unable to create MathML");
      return null;
    }
  }

}
