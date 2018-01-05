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
 * An Abstract Syntax Tree (AST) node representing a boolean
 * 
 * @author Victor Kofia
 * @since 1.0
 */
public class ASTBoolean extends AbstractASTNode {

  /**
   * 
   */
  private static final long serialVersionUID = 7734825161484457512L;

  /**
   * A {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger(ASTBoolean.class);

  /**
   * 
   */
  public ASTBoolean() {
    super();
  }

  /**
   * Copy constructor; Creates a deep copy of the given {@link ASTBoolean}.
   * 
   * @param bool
   *            the {@link ASTBoolean} to be copied.
   */
  public ASTBoolean(ASTBoolean bool) {
    super(bool);
  }

  /**
   * Creates a new {@link ASTBoolean}.
   * @param type
   */
  public ASTBoolean(Type type) {
    this();
    setType(type);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractTreeNode#clone()
   */
  @Override
  public ASTBoolean clone() {
    return new ASTBoolean(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#compile(org.sbml.jsbml.util.compilers.ASTNode2Compiler)
   */
  @Override
  public ASTNode2Value<?> compile(ASTNode2Compiler compiler) {
    ASTNode2Value<?> value = null;
    switch(getType()) {
    case CONSTANT_TRUE:
      value = compiler.getConstantTrue();
      break;
    case CONSTANT_FALSE:
      value = compiler.getConstantFalse();
      break;
    default:
      value = compiler.unknownValue();
    }
    return processValue(value);
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return false;
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildAt(int)
   */
  @Override
  public ASTNumber getChildAt(int childIndex) {
    throw new IndexOutOfBoundsException();
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildCount()
   */
  @Override
  public int getChildCount() {
    return 0;
  }

  /**
   * Get the value of this {@link ASTBoolean}
   * 
   * @return bool
   */
  public boolean getValue() {
    if (isSetType()) {
      return getType() == Type.CONSTANT_TRUE;
    }
    PropertyUndefinedError error = new PropertyUndefinedError("boolean", this);
    if (isStrict()) {
      throw error;
    }
    logger.warn(error);
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#isAllowableType(org.sbml.jsbml.ASTNode.Type)
   */
  @Override
  public boolean isAllowableType(Type type) {
    return type == Type.CONSTANT_TRUE || type == Type.CONSTANT_FALSE;
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.AbstractASTNode#isSetType()
   */
  @Override
  public boolean isSetType() {
    return type == Type.CONSTANT_TRUE || type == Type.CONSTANT_FALSE;
  }

  /**
   * Set the value of this {@link ASTBoolean}
   * 
   * @param bool
   */
  public void setValue(boolean bool) {
    setType(bool ? Type.CONSTANT_TRUE : Type.CONSTANT_FALSE);
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
