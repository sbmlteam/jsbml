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
import org.sbml.jsbml.util.TreeNodeChangeEvent;


/**
 * An Abstract Syntax Tree (AST) node representing a real number
 * in a mathematical expression
 * 
 * @author Victor Kofia
 * @since 1.0
 */
public class ASTCnRealNode extends ASTCnNumberNode<Double> {

  /**
   * 
   */
  private static final long serialVersionUID = 6142072741733701867L;
  /**
   * A {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger(ASTCnRealNode.class);

  /**
   * Creates a new {@link ASTCnRealNode} that lacks a pointer
   * to its containing {@link MathContainer}.
   */
  public ASTCnRealNode() {
    super();
    number = null;
    setType(Type.REAL);
  }

  /**
   * Copy constructor; Creates a deep copy of the given {@link ASTCnRealNode}.
   * 
   * @param node
   *            the {@link ASTCnRealNode} to be copied.
   */
  public ASTCnRealNode(ASTCnRealNode node) {
    super(node);
  }

  /**
   * Creates a new {@link ASTCnRealNode} that lacks a pointer
   * to its containing {@link MathContainer} but has the
   * specified real value.
   * @param value
   */
  public ASTCnRealNode(double value) {
    this();
    setReal(value);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTCnNumberNode#clone()
   */
  @Override
  public ASTCnRealNode clone() {
    return new ASTCnRealNode(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#compile(org.sbml.jsbml.util.compilers.ASTNode2Compiler)
   */
  @Override
  public ASTNode2Value<?> compile(ASTNode2Compiler compiler) {
    ASTNode2Value<?> value = null;
    double real = getReal();
    if (Double.isInfinite(real)) {
      value = (real > 0d) ? compiler.getPositiveInfinity() : compiler
        .getNegativeInfinity();
    } else {
      value = compiler.compile(real, isSetUnits() ? getUnits().toString() : null);
    }
    return processValue(value);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!super.equals(obj)) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    ASTCnRealNode other = (ASTCnRealNode) obj;
    if (number == null) {
      if (other.number != null) {
        return false;
      }
    } else if (!number.equals(other.number)) {
      return false;
    }
    return true;
  }

  /**
   * Get the real value of this node.
   * 
   * @return double real
   */
  public double getReal() {
    return isSetReal() ? number : Double.NaN;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 1777;
    int result = super.hashCode();
    result = prime * result + ((number == null) ? 0 : number.hashCode());
    return result;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTCnNumberNode#isAllowableType(org.sbml.jsbml.ASTNode.Type)
   */
  @Override
  public boolean isAllowableType(Type type) {
    return type == Type.REAL;
  }

  /**
   * Returns {@code true} if this {@link ASTCnRealNode} represents the special IEEE 754 value infinity,
   * {@code false} otherwise.
   * 
   * @return {@code true} if this {@link ASTCnRealNode} is the special IEEE 754 value
   * infinity,
   *         {@code false} otherwise.
   */
  public boolean isInfinity() {
    double real = getReal();
    return Double.isInfinite(real) && (real > 0d);
  }

  /**
   * Returns {@code true} if this {@link ASTCnRealNode} represents the special IEEE 754 value 'negative
   * infinity' {@link Double#NEGATIVE_INFINITY}, {@code false} otherwise.
   * 
   * @return {@code true} if this {@link ASTCnRealNode} is {@link Double#NEGATIVE_INFINITY}, {@code false}
   *         otherwise.
   */
  public boolean isNegInfinity() {
    double real = getReal();
    return Double.isInfinite(real) && (real < 0);
  }

  /**
   * Returns {@code true} iff a value has been set
   * @return boolean
   */
  public boolean isSetReal() {
    return number != null;
  }

  /**
   * Set the value of this node
   * 
   * @param real
   */
  public void setReal(double real) {
    Double old = number;
    number = real;
    firePropertyChange(TreeNodeChangeEvent.number, old, number);
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
