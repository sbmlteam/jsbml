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
import org.sbml.jsbml.util.TreeNodeChangeEvent;


/**
 * An Abstract Syntax Tree (AST) node representing a real number
 * in a mathematical expression
 * 
 * @author Victor Kofia
 * @version $Rev$
 * @since 1.0
 * @date May 30, 2014
 */
public class ASTCnRealNode extends ASTCnNumberNode {

  /**
   * The value of this node
   */
  private Double value;

  /**
   * Creates a new {@link ASTCnRealNode} that lacks a pointer
   * to its containing {@link MathContainer}.
   */
  public ASTCnRealNode() {
    super();
    value = null;
  }

  /**
   * Copy constructor; Creates a deep copy of the given {@link ASTCnRealNode}.
   * 
   * @param node
   *            the {@link ASTCnRealNode} to be copied.
   */
  public ASTCnRealNode(ASTCnRealNode node) {
    super(node);
    setValue(node.getValue());
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
    ASTCnRealNode other = (ASTCnRealNode) obj;
    if (value == null) {
      if (other.value != null)
        return false;
    } else if (!value.equals(other.value))
      return false;
    return true;
  }

  /**
   * Return the value of this node
   * 
   * @return double value
   */
  public double getValue() {
    return isSetValue() ? value : Double.NaN;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((value == null) ? 0 : value.hashCode());
    return result;
  }

  /**
   * Returns true iff a value has been set
   * @param null
   * @return boolean
   */
  public boolean isSetValue() {
    return value != null;
  }

  /**
   * Set the value of this node
   * 
   * @param double value
   */
  public void setValue(double value) {
    Double old = this.value;
    this.value = value;
    firePropertyChange(TreeNodeChangeEvent.value, old, this.value);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ASTCnRealNode [value=");
    builder.append(value);
    builder.append(", strict=");
    builder.append(strict);
    builder.append(", type=");
    builder.append(type);
    builder.append("]");
    return builder.toString();
  }

}
