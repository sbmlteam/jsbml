/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * 
 * Copyright (C) 2009-2018 jointly by the following organizations:
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
import org.sbml.jsbml.Model;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.math.compiler.ASTNode2Compiler;
import org.sbml.jsbml.math.compiler.ASTNode2Value;
import org.sbml.jsbml.util.TreeNodeChangeEvent;

/**
 * An Abstract Syntax Tree (AST) node representing a MathML cn element in a
 * mathematical expression. cn elements are used to specify actual numerical
 * constants.
 * 
 * @author Victor Kofia
 * @param <T>
 * @since 1.0
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
   * @param variable
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
    //TODO: Method is unnecessary as ASTCnNumberNode is never called directly.
    // Maybe make ASTCnNumberNode abstract?
    return null;
  }

  /**
   * Evaluates recursively this {@link ASTCnNumberNode} and creates a new
   * UnitDefinition with respect to all referenced elements.
   * 
   * @return the derived unit of the node.
   * @throws SBMLException
   *             if they are problems going through the {@link ASTNode2} tree.
   */
  public UnitDefinition deriveUnit() throws SBMLException {
    // TODO:
    /*
     * if there's a unit defined:
     *    base unit <- get unit definition and return it
     *    unitdefinition <- return unit definition
     * else
     *   return null
     */
    if (! isSetUnits()) {
      return null;
    }
    int level = -1;
    int version = -1;
    MathContainer parent = getParentSBMLObject();
    if (parent != null) {
      level = parent.getLevel();
      version = parent.getVersion();
    }
    if (Unit.Kind.isValidUnitKindString(getUnits(), level, version)) {
      return UnitDefinition.getPredefinedUnit(getUnits(), level, version);
    }
    return getUnitsInstance();
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!super.equals(obj)) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    ASTCnNumberNode<?> other = (ASTCnNumberNode<?>) obj;
    if (number == null) {
      if (other.number != null) {
        return false;
      }
    } else if (!number.equals(other.number)) {
      return false;
    }
    if (units == null) {
      if (other.units != null) {
        return false;
      }
    } else if (!units.equals(other.units)) {
      return false;
    }
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
    return null;
  }

  /**
   * Creates or obtains a {@link UnitDefinition} corresponding to the unit
   * that has been set for this {@link ASTCnNumberNode} and returns a pointer to it.
   * Note that in case that this {@link ASTCnNumberNode} is associated with a
   * {@link Kind}, the created {@link UnitDefinition} will not be part of the
   * model, it is just a container for the {@link Kind}.
   * 
   * @return A {@link UnitDefinition} or {@code null}.
   */
  public UnitDefinition getUnitsInstance() {
    if (isSetUnits()) {
      //      MathContainer container = isSetParentSBMLObject() ? getParentSBMLObject() : null;
      //      int level = -1;
      //      int version = -1;
      //      if (container != null) {
      //        level = container.getLevel();
      //        version = container.getVersion();
      //      }
      // TODO:
      Model model = null;
      if (isSetParentSBMLObject()) {
        model = getParentSBMLObject().getModel();
      }
      if (model != null) {
        return model.getUnitDefinition(units);
      }
      return null;
    }
    PropertyUndefinedError error = new PropertyUndefinedError("units", this);
    if (isStrict()) {
      throw error;
    }
    logger.warn(error);
    return null;
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

  /**
   * Returns {@code true} if the current {@link ASTCnNumberNode} or
   * any of its descendants has a unit defined.
   * 
   * @return {@code true} if the current {@link ASTCnNumberNode} or
   * any of its descendants has a unit defined.
   */
  public boolean hasUnits()  {
    return isSetUnits();
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
   * @param number
   */
  public void setNumber(T number) {
    T old = this.number;
    this.number = number;
    firePropertyChange(TreeNodeChangeEvent.number, old, this.number);
  }

  /**
   * Set the units of the MathML element represented by this ASTCnNumberNode
   * 
   * @param units
   */
  public void setUnits(String units) {
    // TODO - add some checks on the unit, like it was done before?
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
