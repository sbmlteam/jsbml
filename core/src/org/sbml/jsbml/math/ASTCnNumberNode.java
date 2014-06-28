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
   * 
   */
  private static final long serialVersionUID = 5649353555778706414L;

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

  private String variable;

  /**
   * Creates a new {@link ASTCnNumberNode} that lacks a pointer
   * to its containing {@link MathContainer}.
   */
  public ASTCnNumberNode() {
    super();
    base = null;
    setUnits(null);
    setVariable(null);
  }
  
  /**
   * Creates a new {@link ASTCnNumberNode} that lacks a pointer
   * to its containing {@link MathContainer} and has a variable
   * {@link String}.
   */
  public ASTCnNumberNode(String variable) {
    super();
    base = null;
    setUnits(null);
    setVariable(variable);
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

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNumber#clone()
   */
  @Override
  public ASTCnNumberNode clone() {
    return new ASTCnNumberNode(this);
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
    ASTCnNumberNode other = (ASTCnNumberNode) obj;
    if (base == null) {
      if (other.base != null)
        return false;
    } else if (!base.equals(other.base))
      return false;
    if (units == null) {
      if (other.units != null)
        return false;
    } else if (!units.equals(other.units))
      return false;
    return true;
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
   * Get the variable for this {@link ASTNumber} node
   * 
   * @return variable {@link String}
   */
  public String getVariable() {
    return variable;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((base == null) ? 0 : base.hashCode());
    result = prime * result + ((units == null) ? 0 : units.hashCode());
    return result;
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
   * Returns true iff this {@link ASTNumber} node 
   * represents a variable. 
   * 
   * @return
   */
  public boolean isVariable() {
    return this.variable != null;
  }
  
  /**
   * Set the numerical base of MathML element. Number (CDATA for XML DTD)
   * between 2 and 36
   * 
   * @param Double base
   * @return null
   */
  // TODO: work with base types, i.e., double.
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

  /**
   * Set the variable for this {@link ASTNumber} node
   * 
   * @param variable {@link String}
   */
  private void setVariable(String variable) {
    String old = this.variable;
    this.variable = variable;
    firePropertyChange(TreeNodeChangeEvent.variable, old, this.variable);
  }

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

  /**
   * Unset the units attribute.
   * 
   */
  public void unsetUnits() {
    String oldValue = units;
    units = null;
    firePropertyChange(TreeNodeChangeEvent.units, oldValue, null);
  }

}
