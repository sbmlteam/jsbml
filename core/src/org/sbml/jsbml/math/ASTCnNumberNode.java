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
 * An Abstract Syntax Tree (AST) node representing a MathML cn element in a
 * mathematical expression. cn elements are used to specify actual numerical
 * constants.
 * 
 * @author Victor Kofia
 * @version $Rev$
 * @since 1.0
 * @date May 30, 2014
 */
// TODO: Should this class be abstract?
public class ASTCnNumberNode extends ASTNumber {

  /**
   * A {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger(ASTCnNumberNode.class);

  /**
   * Numerical base for MathML element
   */
  private Double base;

  /**
   * units attribute for MathML element
   */
  private String units;

  /**
   * Creates a new {@link ASTCnNumberNode} that lacks a pointer
   * to its containing {@link MathContainer}.
   */
  public ASTCnNumberNode() {
    super();
    base = null;
    setUnits(null);
  }

  /**
   * Copy constructor; Creates a deep copy of the given {@link ASTCnNumberNode}.
   * 
   * @param node
   *            the {@link ASTCnNumberNode} to be copied.
   */
  public ASTCnNumberNode(ASTCnNumberNode node) {
    super(node);
    setBase(node.getBase());
    setUnits(node.getUnits());
  }

  /**
   * Get the numerical base of MathML element. Number (CDATA for XML DTD)
   * between 2 and 36.
   * 
   * @return Double base
   */
  public Double getBase() {
    // TODO: return a base type with check to avoid NPE.
    return isSetBase() ? base : Double.NaN;
  }

  /**
   * Returns the units of the MathML element represented by this ASTCnNumberNode
   * 
   * @return String units
   */
  public String getUnits() {
    if (isSetUnits()) {
      return units;
    }
    PropertyUndefinedError error = new PropertyUndefinedError("units", this);
    if (isStrict()) {
      throw error;
    }
    logger.warn(error);
    return "";
  }

  /**
   * Returns True iff base has been set
   * 
   * @return boolean
   */
  private boolean isSetBase() {
    return base != null;
  }

  /**
   * Returns True iff units has been set
   * 
   * @return boolean
   */
  private boolean isSetUnits() {
    return units != null;
  }

  /**
   * Set the numerical base of MathML element. Number (CDATA for XML DTD)
   * between 2 and 36
   * 
   * @param Double base
   * @return null
   */
  // TODO: work with base types, i.e., double.
  // TODO: in JavaDoc, you do not have to again declare the type of a parameter. Just use the name, here this is "base" (without quotes). In other words, remove "Double" after the @param keyword.
  public void setBase(Double base) {
    double old = this.base;
    this.base = base;
    firePropertyChange(TreeNodeChangeEvent.base, old, this.base);
  }

  /**
   * Set the units of the MathML element represented by this ASTCnNumberNode
   * 
   * @param String units
   */
  public void setUnits(String units) {
    String old = this.units;
    this.units = units;
    firePropertyChange(TreeNodeChangeEvent.units, old, this.units);
  }

  // TODO: Override clone method with specific return type.

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ASTCnNumberNode [base=");
    builder.append(base);
    builder.append(", units=");
    builder.append(units);
    builder.append(", strict=");
    builder.append(strict);
    builder.append(", type=");
    builder.append(type);
    builder.append("]");
    return builder.toString();
  }

}
