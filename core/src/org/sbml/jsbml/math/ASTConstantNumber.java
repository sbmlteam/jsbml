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
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.math.compiler.ASTNode2Compiler;
import org.sbml.jsbml.util.TreeNodeChangeEvent;
import org.sbml.jsbml.util.compilers.ASTNodeValue;

/**
 * An Abstract Syntax Tree (AST) node representing a constant number
 * in a mathematical expression.
 * 
 * @author Victor Kofia
 * @version $Rev$
 * @since 1.0
 * @date May 30, 2014
 */
public class ASTConstantNumber extends ASTNumber {

  /**
   * 
   */
  private static final long serialVersionUID = -6872240196225149289L;
  
  /**
   * This variable stores the value of the constant number
   */
  private Double value;

  /**
   * Creates a new {@link ASTConstantNumber}.
   */
  public ASTConstantNumber() {
    super();
    value = null;
  }
  
  /**
   * Copy constructor; Creates a deep copy of the given {@link ASTConstantNumber}.
   * 
   * @param node
   *            the {@link ASTConstantNumber} to be copied.
   */
  public ASTConstantNumber(ASTConstantNumber node) {
    super(node);
    setValue(node.getValue());
  }
  
  /**
   * Creates a new {@link ASTConstantNumber} with value {@link double}.
   */
  public ASTConstantNumber(double value) {
    super();
    setValue(value);
  }

  /**
   * Creates a new {@link ASTConstantNumber} of type {@link Type}.
   */
  public ASTConstantNumber(Type type) {
    super();
    value = null;
    setType(type);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNumber#clone()
   */
  @Override
  public ASTConstantNumber clone() {
    return new ASTConstantNumber(this);
  }
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#compile(org.sbml.jsbml.util.compilers.ASTNode2Compiler)
   */
  @Override
  public ASTNodeValue compile(ASTNode2Compiler compiler) {
    ASTNodeValue value = null;
    switch(getType()) {
    case CONSTANT_PI:
      value = compiler.getConstantPi();
      break;
    case CONSTANT_E:
      value = compiler.getConstantE();
      break;
    case CONSTANT_TRUE:
      value = compiler.getConstantTrue();
      break;
    case CONSTANT_FALSE:
      value = compiler.getConstantFalse();
      break;
    case NAME_AVOGADRO:
      value = compiler.getConstantAvogadro(getName());
      break;
    default: // UNKNOWN:
      value = compiler.unknownValue();
      break;
    }
    value.setType(getType());
    MathContainer parent = getParentSBMLObject();
    if (parent != null) {
      value.setLevel(parent.getLevel());
      value.setVersion(parent.getVersion());
    }
    return value;
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
    ASTConstantNumber other = (ASTConstantNumber) obj;
    if (value == null) {
      if (other.value != null)
        return false;
    } else if (!value.equals(other.value))
      return false;
    return true;
  }

  /**
   * Get the value of this ASTConstantNumber
   * 
   * @return Integer value
   */
  public double getValue() {
    return isSetValue() ? value : Double.NaN;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 1583;
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
   * Set the value of this ASTConstantNumber
   * 
   * @param Integer value
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
    builder.append("ASTConstantNumber [value=");
    builder.append(value);
    builder.append(", strict=");
    builder.append(strict);
    builder.append(", type=");
    builder.append(type);
    builder.append("]");
    return builder.toString();
  }

}
