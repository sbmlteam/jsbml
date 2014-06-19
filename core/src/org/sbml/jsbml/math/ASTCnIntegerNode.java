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
   * Copy constructor; Creates a deep copy of the given {@link ASTCnIntegerNode}.
   * 
   * @param node
   *            the {@link ASTCnIntegerNode} to be copied.
   */
  public ASTCnIntegerNode(ASTCnIntegerNode node) {
    super(node);
    setValue(node.getValue());
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

  // TODO: Override clone method with specific return type.

  /**
   * Returns True iff value has been set
   * 
   * @param null
   * @return boolean
   */
  // TODO: Make sure that JavaDoc matches the method's declaration, here is no parameter.
  public boolean isSetValue() {
    return value != null;
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
