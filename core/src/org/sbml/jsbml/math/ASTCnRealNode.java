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
 * An Abstract Syntax Tree (AST) node representing a real number
 * in a mathematical expression
 * 
 * @author Victor Kofia
 * @version $Rev$
 * @since 1.0
 * @date May 30, 2014
 */
public class ASTCnRealNode extends ASTCnNumberNode<Double> {

  /**
   * 
   */
  private static final long serialVersionUID = 6142072741733701867L;

  /**
   * Creates a new {@link ASTCnRealNode} that lacks a pointer
   * to its containing {@link MathContainer}.
   */
  public ASTCnRealNode() {
    super();
    number = null;
    setType(Type.REAL);
  }
  
  /**
   * Copy constructor; Creates a deep copy of the given {@link ASTCnRealNode}.
   * 
   * @param node
   *            the {@link ASTCnRealNode} to be copied.
   */
  public ASTCnRealNode(ASTCnRealNode node) {
    super(node);
  }

  /**
   * Creates a new {@link ASTCnRealNode} that lacks a pointer
   * to its containing {@link MathContainer} but has the 
   * specified real value {@link double}.
   */
  public ASTCnRealNode(double value) {
    super();
    setReal(value);
    setType(Type.REAL);
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
   * @see org.sbml.jsbml.math.ASTNode2#compile(org.sbml.jsbml.util.compilers.ASTNode2Compiler)
   */
  @Override
  public ASTNodeValue compile(ASTNode2Compiler compiler) {
    ASTNodeValue value = null;
    double real = getReal();
    if (Double.isInfinite(real)) {
      value = (real > 0d) ? compiler.getPositiveInfinity() : compiler
        .getNegativeInfinity();
    } else {
      value = compiler.compile(real, getUnits());
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
    ASTCnRealNode other = (ASTCnRealNode) obj;
    if (number == null) {
      if (other.number != null)
        return false;
    } else if (!number.equals(other.number))
      return false;
    return true;
  }

  /**
   * Get the real value of this node. 
   * 
   * @return double real
   */
  public double getReal() {
    return isSetReal() ? number : Double.NaN;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 1777;
    int result = super.hashCode();
    result = prime * result + ((number == null) ? 0 : number.hashCode());
    return result;
  }

  /**
   * Returns true iff a value has been set
   * @param null
   * @return boolean
   */
  public boolean isSetReal() {
    return number != null;
  }

  /**
   * Set the value of this node
   * 
   * @param double value
   */
  public void setReal(double real) {
    Double old = this.number;
    this.number = real;
    firePropertyChange(TreeNodeChangeEvent.number, old, this.number);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ASTCnRealNode [number=");
    builder.append(number);
    builder.append(", parentSBMLObject=");
    builder.append(parentSBMLObject);
    builder.append(", strict=");
    builder.append(strict);
    builder.append(", type=");
    builder.append(type);
    builder.append(", id=");
    builder.append(id);
    builder.append(", style=");
    builder.append(style);
    builder.append(", listOfListeners=");
    builder.append(listOfListeners);
    builder.append(", parent=");
    builder.append(parent);
    builder.append("]");
    return builder.toString();
  }

}
