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
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.math.compiler.ASTNode2Compiler;
import org.sbml.jsbml.math.compiler.ASTNode2Value;
import org.sbml.jsbml.math.compiler.FormulaCompiler;
import org.sbml.jsbml.math.compiler.LaTeXCompiler;
import org.sbml.jsbml.math.compiler.MathMLXMLStreamCompiler;

/**
 * An Abstract Syntax Tree (AST) node representing a logical operator
 * in a mathematical expression.
 * 
 * @author Victor Kofia
 * @since 1.0
 */
public class ASTLogicalOperatorNode extends ASTFunction {

  /**
   * 
   */
  private static final long serialVersionUID = -4435213506053944443L;
  /**
   * A {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger(ASTMinusNode.class);

  /**
   * Creates a new {@link ASTLogicalOperatorNode}.
   */
  public ASTLogicalOperatorNode() {
    super();
  }

  /**
   * Copy constructor; Creates a deep copy of the given {@link ASTLogicalOperatorNode}.
   * 
   * @param node
   *            the {@link ASTLogicalOperatorNode} to be copied.
   */
  public ASTLogicalOperatorNode(ASTLogicalOperatorNode node) {
    super(node);
  }

  /**
   * Creates a new {@link ASTLogicalOperatorNode} of type {@link Type}.
   * @param type
   */
  public ASTLogicalOperatorNode(Type type) {
    this();
    setType(type);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTFunction#clone()
   */
  @Override
  public ASTLogicalOperatorNode clone() {
    return new ASTLogicalOperatorNode(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#compile(org.sbml.jsbml.util.compilers.ASTNode2Compiler)
   */
  @Override
  public ASTNode2Value<?> compile(ASTNode2Compiler compiler) {
    ASTNode2Value<?> value = null;
    switch(getType()) {
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
      value = compiler.not(getChildAt(0));
      break;
    default: // UNKNOWN:
      value = compiler.unknownValue();
      break;
    }
    return processValue(value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTFunction#isAllowableType(org.sbml.jsbml.ASTNode.Type)
   */
  @Override
  public boolean isAllowableType(Type type) {
    if (type != null) {
      switch(type) {
      case LOGICAL_AND:
      case LOGICAL_XOR:
      case LOGICAL_OR:
      case LOGICAL_NOT:
        return true;
      default:
        break;
      }
    }
    return false;
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
