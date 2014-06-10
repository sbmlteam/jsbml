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

import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.PropertyUndefinedError;


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
  }

  /**
   * Copy constructor; Creates a deep copy of the given {@link ASTCnRationalNode}.
   * 
   * @param cnRationalNode
   *            the {@link ASTCnRationalNode} to be copied.
   */
  public ASTCnRationalNode(ASTCnRationalNode cnRationalNode) {
    super();
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
    throw error;
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
    throw error;
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
    this.denominator = denominator;
  }

  /**
   * Set the value of the numerator
   * 
   * @param int numerator
   */
  public void setNumerator(int numerator) {
    this.numerator = numerator;
  }

}
