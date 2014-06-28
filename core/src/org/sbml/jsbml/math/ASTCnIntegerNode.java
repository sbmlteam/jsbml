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
 * An Abstract Syntax Tree (AST) node representing an Integer
 * in a mathematical expression
 * 
 * @author Victor Kofia
 * @version $Rev$
 * @since 1.0
 * @date May 30, 2014
 */
public class ASTCnIntegerNode extends ASTCnNumberNode {

  /**
   * 
   */
  private static final long serialVersionUID = 5478874063299110266L;

  /**
   * A {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger(ASTCnIntegerNode.class);

  /**
   * The value of this node
   */
  private Integer value;

  /**
   * Creates a new {@link ASTCnIntegerNode} that lacks a pointer
   * to its containing {@link MathContainer}.
   */
  public ASTCnIntegerNode() {
    super();
    value = null;
  }
  
  /**
   * Creates a new {@link ASTCnIntegerNode} with value {@link int}.
   */
  public ASTCnIntegerNode(int value) {
    super();
    setValue(value);
  }

  /**
   * Copy constructor; Creates a deep copy of the given {@link ASTCnIntegerNode}.
   * 
   * @param node
   *            the {@link ASTCnIntegerNode} to be copied.
   */
  public ASTCnIntegerNode(ASTCnIntegerNode node) {
    super(node);
    setValue(node.getValue());
  }
  
  @Override
  public ASTCnIntegerNode clone() {
    return new ASTCnIntegerNode(this);
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
    ASTCnIntegerNode other = (ASTCnIntegerNode) obj;
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
   * @return Integer value
   */
  public int getValue() {
    if (isSetValue()) {
      return value;
    }
    PropertyUndefinedError error = new PropertyUndefinedError("integer", this);
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
    result = prime * result + ((value == null) ? 0 : value.hashCode());
    return result;
  }

  /**
   * Returns True iff value has been set
   * 
   * @param null
   * @return boolean
   */
  public boolean isSetValue() {
    return value != null;
  }

  /**
   * Set the value of this node
   * 
   * @param Integer value
   */
  public void setValue(int value) {
    Integer old = this.value;
    this.value = value;
    firePropertyChange(TreeNodeChangeEvent.value, old, this.value);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ASTCnIntegerNode [value=");
    builder.append(value);
    builder.append(", strict=");
    builder.append(strict);
    builder.append(", type=");
    builder.append(type);
    builder.append("]");
    return builder.toString();
  }

}
