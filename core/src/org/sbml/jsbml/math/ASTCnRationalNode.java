/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * 
 * Copyright (C) 2009-2014  joIntegerly by the following organizations:
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
import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.util.TreeNodeChangeEvent;


/**
 * An Abstract Syntax Tree (AST) node representing a rational number
 * in a mathematical expression
 * 
 * @author Victor Kofia
 * @version $Rev$
 * @since 1.0
 * @date May 30, 2014
 */
public class ASTCnRationalNode extends ASTCnNumberNode {

  /**
   * A {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger(ASTCnRationalNode.class);

  /**
   * The numerator of this rational number
   */
  private Integer numerator;

  /**
   * The denominator of this rational number
   */
  private Integer denominator;

  /**
   * Creates a new {@link ASTCnRationalNode} that lacks a pointer
   * to its containing {@link MathContainer}.
   */
  public ASTCnRationalNode() {
    super();
    numerator = null;
    denominator = null;
  }

  /**
   * Copy constructor; Creates a deep copy of the given {@link ASTCnRationalNode}.
   * 
   * @param node
   *            the {@link ASTCnRationalNode} to be copied.
   */
  public ASTCnRationalNode(ASTCnRationalNode node) {
    super(node);
    setNumerator(node.getNumerator());
    setDenominator(node.getDenominator());
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
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    ASTCnRationalNode other = (ASTCnRationalNode) obj;
    if (denominator == null) {
      if (other.denominator != null)
        return false;
    } else if (!denominator.equals(other.denominator))
      return false;
    if (numerator == null) {
      if (other.numerator != null)
        return false;
    } else if (!numerator.equals(other.numerator))
      return false;
    return true;
  }

  /**
   * Get the value of the denominator
   * 
   * @return int denominator
   */
  public int getDenominator() {
    if (isSetDenominator()) {
      return denominator;
    }
    PropertyUndefinedError error = new PropertyUndefinedError("denominator", this);
    if (isStrict()) {
      throw error;
    }
    logger.warn(error);
    return 0;
  }

  /**
   * Get the value of the numerator
   * 
   * @return int numerator
   */
  public int getNumerator() {
    if (isSetNumerator()) {
      return numerator;
    }
    PropertyUndefinedError error = new PropertyUndefinedError("numerator", this);
    if (isStrict()) {
      throw error;
    }
    logger.warn(error);
    return 0;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result
      + ((denominator == null) ? 0 : denominator.hashCode());
    result = prime * result + ((numerator == null) ? 0 : numerator.hashCode());
    return result;
  }

  /**
   * Returns True iff denominator has been set
   * 
   * @param null
   * @return boolean
   */
  public boolean isSetDenominator() {
    return denominator != null;
  }

  /**
   * Returns True iff numerator has been set
   * 
   * @param null
   * @return boolean
   */
  public boolean isSetNumerator() {
    return numerator != null;
  }

  /**
   * Set the value of the denominator
   * 
   * @param int denominator
   */
  public void setDenominator(int denominator) {
    Integer old = this.denominator;
    this.denominator = denominator;
    firePropertyChange(TreeNodeChangeEvent.denominator, old, this.denominator);
  }

  /**
   * Set the value of the numerator
   * 
   * @param int numerator
   */
  public void setNumerator(int numerator) {
    Integer old = this.numerator;
    this.numerator = numerator;
    firePropertyChange(TreeNodeChangeEvent.numerator, old, this.numerator);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ASTCnRationalNode [numerator=");
    builder.append(numerator);
    builder.append(", denominator=");
    builder.append(denominator);
    builder.append(", strict=");
    builder.append(strict);
    builder.append(", type=");
    builder.append(type);
    builder.append("]");
    return builder.toString();
  }

}
