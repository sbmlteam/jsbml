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
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.math.compiler.ASTNode2Compiler;
import org.sbml.jsbml.math.compiler.ASTNode2Value;
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
public class ASTCnNumberNode<T> extends ASTNumber {

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
  protected T number;

  /**
   * units attribute for MathML element
   */
  private String units;

  /**
   * variable attribute for MathML element
   */
  private String variable;

  /**
   * Creates a new {@link ASTCnNumberNode} that lacks a pointer
   * to its containing {@link MathContainer}.
   */
  public ASTCnNumberNode() {
    super();
    number = null;
    units = null;
    variable = null;
  }
  
  /**
   * Copy constructor; Creates a deep copy of the given {@link ASTCnNumberNode}.
   * 
   * @param node
   *            the {@link ASTCnNumberNode} to be copied.
   */
  public ASTCnNumberNode(ASTCnNumberNode<T> node) {
    super(node);
    if (node.isSetNumber()) {
      setNumber(node.getNumber());      
    }
    if (node.isSetUnits()) {
      setUnits(node.getUnits());      
    }
    if (node.isSetVariable()) {
      setVariable(node.getVariable());
    }
  }

  /**
   * Creates a new {@link ASTCnNumberNode} that lacks a pointer
   * to its containing {@link MathContainer} and has a variable
   * {@link String}.
   */
  public ASTCnNumberNode(String variable) {
    this();
    setVariable(variable);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNumber#clone()
   */
  @Override
  public ASTCnNumberNode<T> clone() {
    return new ASTCnNumberNode<T>(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#compile(org.sbml.jsbml.math.compiler.ASTNode2Compiler)
   */
  @Override
  public ASTNode2Value<?> compile(ASTNode2Compiler compiler) {
    //TODO: 
    return null;
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
    ASTCnNumberNode<T> other = (ASTCnNumberNode<T>) obj;
    if (number == null) {
      if (other.number != null)
        return false;
    } else if (!number.equals(other.number))
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
   * @return T number
   * @throws PropertyUndefinedError
   */
  public T getNumber() {
    if (isSetNumber()) {
      return this.number;
    }
    PropertyUndefinedError error = new PropertyUndefinedError("number", this);
    if (isStrict()) {
      throw error;
    }
    logger.warn(error);
    return null;
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
    if (isSetVariable()) {
      return variable;
    }
    PropertyUndefinedError error = new PropertyUndefinedError("variable", this);
    if (isStrict()) {
      throw error;
    }
    logger.warn(error);
    return "";
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 1483;
    int result = super.hashCode();
    result = prime * result + ((number == null) ? 0 : number.hashCode());
    result = prime * result + ((units == null) ? 0 : units.hashCode());
    return result;
  }
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#isAllowableType(org.sbml.jsbml.ASTNode.Type)
   */
  @Override
  public boolean isAllowableType(Type type) {
    return type == Type.INTEGER || type == Type.RATIONAL || type == Type.REAL
           || type == Type.REAL_E;
  }

  /**
   * Returns True iff base has been set
   * 
   * @return boolean
   */
  protected boolean isSetNumber() {
    return number != null;
  }
  
  /**
   * Returns True iff units has been set
   * 
   * @return boolean
   */
  public boolean isSetUnits() {
    return units != null;
  }

  /**
   * Returns true iff this {@link ASTNumber} node 
   * represents a variable. 
   * 
   * @return
   */
  protected boolean isSetVariable() {
    return this.variable != null;
  }

  /**
   * Set the numerical base of MathML element. Number (CDATA for XML DTD)
   * between 2 and 36
   * 
   * @param Double number
   */
  public void setNumber(T number) {
    T old = this.number;
    this.number = number;
    firePropertyChange(TreeNodeChangeEvent.number, old, this.number);
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
  public void setVariable(String variable) {
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
    builder.append("ASTCnNumberNode [number=");
    builder.append(number);
    builder.append(", units=");
    builder.append(units);
    builder.append(", variable=");
    builder.append(variable);
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
