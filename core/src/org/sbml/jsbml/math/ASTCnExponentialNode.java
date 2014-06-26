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

import org.apache.log4j.Logger;
import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.util.TreeNodeChangeEvent;


/**
 * An Abstract Syntax Tree (AST) node representing an exponent
 * in a mathematical expression
 * 
 * @author Victor Kofia
 * @version $Rev$
 * @since 1.0
 * @date May 30, 2014
 */
public class ASTCnExponentialNode extends ASTCnNumberNode {

  /**
   * A {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger(ASTCnExponentialNode.class);

  /**
   * The exponent value of this node
   */
  private Integer exponent;

  /**
   * The mantissa value of this node
   */
  private Double mantissa;

  /**
   * Creates a new {@link ASTCnExponentialNode} that lacks a pointer
   * to its containing {@link MathContainer}.
   */
  public ASTCnExponentialNode() {
    super();
    exponent = null;
    mantissa = null;
  }

  /**
   * Copy constructor; Creates a deep copy of the given
   * {@link ASTCnExponentialNode}.
   * 
   * @param node
   *            the {@link ASTCnExponentialNode} to be copied.
   */
  public ASTCnExponentialNode (ASTCnExponentialNode node) {
    super(node);
    setExponent(node.getExponent());
    setMantissa(node.getMantissa());
  }
  
  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTCnNumberNode#clone()
   */
  @Override
  public ASTCnExponentialNode clone() {
    return new ASTCnExponentialNode(this);
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
    ASTCnExponentialNode other = (ASTCnExponentialNode) obj;
    if (exponent == null) {
      if (other.exponent != null)
        return false;
    } else if (!exponent.equals(other.exponent))
      return false;
    if (mantissa == null) {
      if (other.mantissa != null)
        return false;
    } else if (!mantissa.equals(other.mantissa))
      return false;
    return true;
  }

  /**
   * Get the exponent value of this node. Throws PropertyUndefinedError
   * if no exponent value has been defined.
   * 
   * @return int exponent
   */
  public int getExponent() {
    if (isSetExponent()) {
      return exponent;
    }
    PropertyUndefinedError error = new PropertyUndefinedError("exponent", this);
    if (isStrict()) {
      throw error;
    }
    logger.warn(error);
    return 0;
  }

  /**
   * Set the mantissa value of this node
   * 
   * @return double mantissa
   */
  public double getMantissa() {
    return isSetMantissa() ? mantissa : Double.NaN;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((exponent == null) ? 0 : exponent.hashCode());
    result = prime * result + ((mantissa == null) ? 0 : mantissa.hashCode());
    return result;
  }

  /**
   * Returns True iff exponent has been set
   * 
   * @param null
   * @return boolean
   */
  public boolean isSetExponent() {
    return exponent != null;
  }

  /**
   * Returns True iff mantissa has been set
   * 
   * @param null
   * @return boolean
   */
  public boolean isSetMantissa() {
    return mantissa != null;
  }

  /**
   * Set the exponent value of this node
   * 
   * @param int exponent
   */
  public void setExponent(int exponent) {
    Integer old = this.exponent;
    this.exponent = exponent;
    firePropertyChange(TreeNodeChangeEvent.exponent, old, this.exponent);
  }

  /**
   * Get the mantissa value of this node
   * 
   * @param double mantissa
   */
  public void setMantissa(double mantissa) {
    Double old = this.mantissa;
    this.mantissa = mantissa;
    firePropertyChange(TreeNodeChangeEvent.mantissa, old, this.mantissa);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ASTCnExponentialNode [exponent=");
    builder.append(exponent);
    builder.append(", mantissa=");
    builder.append(mantissa);
    builder.append(", strict=");
    builder.append(strict);
    builder.append(", type=");
    builder.append(type);
    builder.append("]");
    return builder.toString();
  }

}
