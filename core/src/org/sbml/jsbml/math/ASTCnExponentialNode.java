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

import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.PropertyUndefinedError;


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
  }

  /**
   * Copy constructor; Creates a deep copy of the given
   * {@link ASTCnExponentialNode}.
   * 
   * @param cnExponentialNode
   *            the {@link ASTCnExponentialNode} to be copied.
   */
  public ASTCnExponentialNode (ASTCnExponentialNode cnExponentialNode) {
    super(cnExponentialNode);
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
    throw error;
  }

  /**
   * Set the mantissa value of this node
   * 
   * @return double mantissa
   */
  public double getMantissa() {
    return isSetMantissa() ? mantissa : Double.NaN;
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
    this.exponent = exponent;
  }

  /**
   * Get the mantissa value of this node
   * 
   * @param double mantissa
   */
  public void setMantissa(double mantissa) {
    this.mantissa = mantissa;
  }

}
