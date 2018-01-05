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
 * An Abstract Syntax Tree (AST) node representing the power function
 * 
 * @author Victor Kofia
 * @since 1.0
 */
public class ASTPowerNode extends ASTBinaryFunctionNode {

  /**
   * 
   */
  private static final long serialVersionUID = 460517738739696555L;
  /**
   * A {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger(ASTPowerNode.class);

  /**
   * Creates a new {@link ASTPowerNode}.
   */
  public ASTPowerNode() {
    super();
    setType(Type.FUNCTION_POWER);
  }

  /**
   * Creates a new {@link ASTPowerNode} with the specified basis and
   * exponent.
   * @param basis
   * @param exponent
   */
  public ASTPowerNode(ASTNode2 basis, ASTNode2 exponent) {
    super(basis, exponent);
    setType(Type.FUNCTION_POWER);
  }

  /**
   * Copy constructor; Creates a deep copy of the given {@link ASTPowerNode}.
   * 
   * @param node
   *            the {@link ASTPowerNode} to be copied.
   */
  public ASTPowerNode(ASTPowerNode node) {
    super(node);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTFunction#clone()
   */
  @Override
  public ASTPowerNode clone() {
    return new ASTPowerNode(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#compile(org.sbml.jsbml.util.compilers.ASTNode2Compiler)
   */
  @Override
  public ASTNode2Value<?> compile(ASTNode2Compiler compiler) {
    ASTNode2Value<?> value = compiler.pow(getLeftChild(), getRightChild());
    return processValue(value);
  }

  /**
   * Get the basis of this {@link ASTPowerNode}
   * 
   * @return basis {@link ASTNode2}
   */
  public ASTNode2 getBasis() {
    return getLeftChild();
  }

  /**
   * Get the exponent of this {@link ASTPowerNode}
   * 
   * @return exponent {@link ASTNode2}
   */
  public ASTNode2 getExponent() {
    return getRightChild();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTFunction#isAllowableType(org.sbml.jsbml.ASTNode.Type)
   */
  @Override
  public boolean isAllowableType(Type type) {
    return type == Type.FUNCTION_POWER || type == Type.POWER;
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
