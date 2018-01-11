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
import org.sbml.jsbml.util.TreeNodeChangeEvent;
import org.sbml.jsbml.util.ValuePair;


/**
 * An Abstract Syntax Tree (AST) node representing a rational number
 * in a mathematical expression
 * 
 * @author Victor Kofia
 * @since 1.0
 */
public class ASTCnRationalNode extends ASTCnNumberNode<ValuePair<Integer,Integer>> {

  /**
   * 
   */
  private static final long serialVersionUID = -6844137576018089451L;

  /**
   * A {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger(ASTCnRationalNode.class);

  /**
   * Creates a new {@link ASTCnRationalNode} that lacks a pointer
   * to its containing {@link MathContainer}.
   */
  public ASTCnRationalNode() {
    super();
    setType(Type.RATIONAL);
  }

  /**
   * Copy constructor; Creates a deep copy of the given {@link ASTCnRationalNode}.
   * 
   * @param node
   *            the {@link ASTCnRationalNode} to be copied.
   */
  public ASTCnRationalNode(ASTCnRationalNode node) {
    super(node);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTCnNumberNode#clone()
   */
  @Override
  public ASTCnRationalNode clone() {
    return new ASTCnRationalNode(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#compile(org.sbml.jsbml.util.compilers.ASTNode2Compiler)
   */
  @Override
  public ASTNode2Value<?> compile(ASTNode2Compiler compiler) {
    ASTNode2Value<?> value = compiler.frac(getNumerator(), getDenominator());
    return processValue(value);
  }

  /**
   * Get the denominator value of this node.
   * 
   * @return int denominator
   */
  public int getDenominator() {
    if (isSetDenominator()) {
      return number.getV();
    }
    PropertyUndefinedError error = new PropertyUndefinedError("denominator", this);
    if (isStrict()) {
      throw error;
    }
    logger.warn(error);
    return 0;
  }

  /**
   * Get the numerator value of this node.
   * 
   * @return int numerator
   */
  public int getNumerator() {
    if (isSetNumerator()) {
      return number.getL();
    }
    PropertyUndefinedError error = new PropertyUndefinedError("numerator", this);
    if (isStrict()) {
      throw error;
    }
    logger.warn(error);
    return 0;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTCnNumberNode#isAllowableType(org.sbml.jsbml.ASTNode.Type)
   */
  @Override
  public boolean isAllowableType(Type type) {
    return type == Type.RATIONAL;
  }

  /**
   * Returns {@code true} iff denominator has been set
   * @return boolean
   */
  public boolean isSetDenominator() {
    return number == null ? false : number.getV() != null;
  }

  /**
   * Returns {@code true} iff numerator has been set
   * 
   * @return boolean
   */
  public boolean isSetNumerator() {
    return number == null ? false : number.getL() != null;
  }

  /**
   * Set the value of the denominator
   * 
   * @param denominator
   */
  public void setDenominator(int denominator) {
    if (! isSetNumber()) {
      setNumber(new ValuePair<Integer,Integer>());
    }
    Integer old = number.getV();
    number.setV(denominator);
    firePropertyChange(TreeNodeChangeEvent.denominator, old, number.getV());
  }

  /**
   * Set the value of the numerator
   * 
   * @param numerator
   */
  public void setNumerator(int numerator) {
    if (! isSetNumber()) {
      setNumber(new ValuePair<Integer,Integer>());
    }
    Integer old = number.getL();
    number.setL(numerator);
    firePropertyChange(TreeNodeChangeEvent.numerator, old, number.getL());
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
