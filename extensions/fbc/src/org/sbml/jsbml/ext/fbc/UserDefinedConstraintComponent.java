/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2022 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 5. The Babraham Institute, Cambridge, UK
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.fbc;

import java.util.Map;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.LevelVersionError;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.NamedSBase;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.UniqueNamedSBase;

/**
 * A {@link UserDefinedConstraintComponent}, introduced in FBC version 3, is a
 * term of a {@link UserDefinedConstraint}: the product of a coefficient (a
 * {@link Parameter}) and a variable (a {@link Reaction} or a non constant
 * {@link Parameter}), or, for the {@link FBCVariableType#QUADRATIC} variable
 * type, the product of a coefficient and two variables.
 * 
 * @since 1.7
 */
public class UserDefinedConstraintComponent extends AbstractNamedSBase implements UniqueNamedSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 4935611337592873385L;

  /**
   * The id of the {@link Parameter} that is the coefficient.
   */
  private String coefficient;

  /**
   * The id of the {@link Reaction} or {@link Parameter} that is the variable.
   */
  private String variable;

  /**
   * The id of the {@link Reaction} or {@link Parameter} that is the second
   * variable of a quadratic component.
   */
  private String variable2;

  /**
   * The type of the variable.
   */
  private FBCVariableType variableType;

  /**
   * Creates a {@link UserDefinedConstraintComponent} instance.
   */
  public UserDefinedConstraintComponent() {
    super();
    initDefaults();
  }

  /**
   * Creates a {@link UserDefinedConstraintComponent} instance with a level and
   * version.
   * 
   * @param level the SBML level
   * @param version the SBML version
   */
  public UserDefinedConstraintComponent(int level, int version) {
    this(null, null, level, version);
  }

  /**
   * Creates a {@link UserDefinedConstraintComponent} instance with an id.
   * 
   * @param id the id
   */
  public UserDefinedConstraintComponent(String id) {
    super(id);
    initDefaults();
  }

  /**
   * Creates a {@link UserDefinedConstraintComponent} instance with an id,
   * level, and version.
   * 
   * @param id the id
   * @param level the SBML level
   * @param version the SBML version
   */
  public UserDefinedConstraintComponent(String id, int level, int version) {
    this(id, null, level, version);
  }

  /**
   * Creates a {@link UserDefinedConstraintComponent} instance with an id,
   * name, level, and version.
   * 
   * @param id the id
   * @param name the name
   * @param level the SBML level
   * @param version the SBML version
   */
  public UserDefinedConstraintComponent(String id, String name, int level, int version) {
    super(id, name, level, version);
    if (getLevelAndVersion().compareTo(
      Integer.valueOf(FBCConstants.MIN_SBML_LEVEL),
      Integer.valueOf(FBCConstants.MIN_SBML_VERSION)) < 0) {
      throw new LevelVersionError(getElementName(), level, version);
    }
    initDefaults();
  }

  /**
   * Clone constructor
   * 
   * @param obj the instance to clone
   */
  public UserDefinedConstraintComponent(UserDefinedConstraintComponent obj) {
    super(obj);

    if (obj.isSetCoefficient()) {
      setCoefficient(obj.getCoefficient());
    }
    if (obj.isSetVariable()) {
      setVariable(obj.getVariable());
    }
    if (obj.isSetVariable2()) {
      setVariable2(obj.getVariable2());
    }
    if (obj.isSetVariableType()) {
      setVariableType(obj.getVariableType());
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public UserDefinedConstraintComponent clone() {
    return new UserDefinedConstraintComponent(this);
  }

  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    setPackageVersion(-1);
    packageName = FBCConstants.shortLabel;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
   */
  @Override
  public boolean isIdMandatory() {
    return false;
  }

  /**
   * Returns the id of the {@link Parameter} that is the coefficient.
   *
   * @return the coefficient, the empty {@link String} if it is not set.
   */
  public String getCoefficient() {
    return isSetCoefficient() ? coefficient : "";
  }

  /**
   * Returns the {@link Parameter} that is the coefficient.
   *
   * @return the {@link Parameter} of the coefficient, {@code null} if the
   *         coefficient is not set or no parameter of the model.
   */
  public Parameter getCoefficientInstance() {
    Model model = getModel();
    return (isSetCoefficient() && (model != null)) ? model.getParameter(coefficient) : null;
  }

  /**
   * Returns whether the coefficient is set.
   *
   * @return whether the coefficient is set.
   */
  public boolean isSetCoefficient() {
    return coefficient != null;
  }

  /**
   * Sets the id of the {@link Parameter} that is the coefficient.
   *
   * @param coefficient the id of a {@link Parameter}.
   */
  public void setCoefficient(String coefficient) {
    String oldCoefficient = this.coefficient;
    this.coefficient = coefficient;
    firePropertyChange(FBCConstants.coefficient, oldCoefficient, this.coefficient);
  }

  /**
   * Unsets the coefficient.
   *
   * @return {@code true}, if the coefficient was set before, otherwise
   *         {@code false}.
   */
  public boolean unsetCoefficient() {
    if (isSetCoefficient()) {
      setCoefficient(null);
      return true;
    }
    return false;
  }

  /**
   * Returns the id of the {@link Reaction} or {@link Parameter} that is the
   * variable.
   *
   * @return the variable, the empty {@link String} if it is not set.
   */
  public String getVariable() {
    return isSetVariable() ? variable : "";
  }

  /**
   * Returns the {@link Reaction} or {@link Parameter} that is the variable.
   *
   * @return the {@link Reaction} or {@link Parameter} of the variable,
   *         {@code null} if the variable is not set or neither a reaction nor
   *         a parameter of the model.
   */
  public NamedSBase getVariableInstance() {
    return isSetVariable() ? findVariable(variable) : null;
  }

  /**
   * Returns whether the variable is set.
   *
   * @return whether the variable is set.
   */
  public boolean isSetVariable() {
    return variable != null;
  }

  /**
   * Sets the id of the {@link Reaction} or {@link Parameter} that is the
   * variable.
   *
   * @param variable the id of a {@link Reaction} or {@link Parameter}.
   */
  public void setVariable(String variable) {
    String oldVariable = this.variable;
    this.variable = variable;
    firePropertyChange(FBCConstants.variable, oldVariable, this.variable);
  }

  /**
   * Unsets the variable.
   *
   * @return {@code true}, if the variable was set before, otherwise
   *         {@code false}.
   */
  public boolean unsetVariable() {
    if (isSetVariable()) {
      setVariable(null);
      return true;
    }
    return false;
  }

  /**
   * Returns the id of the {@link Reaction} or {@link Parameter} that is the
   * second variable of a quadratic component.
   *
   * @return the second variable, the empty {@link String} if it is not set.
   */
  public String getVariable2() {
    return isSetVariable2() ? variable2 : "";
  }

  /**
   * Returns the {@link Reaction} or {@link Parameter} that is the second
   * variable.
   *
   * @return the {@link Reaction} or {@link Parameter} of the second variable,
   *         {@code null} if the second variable is not set or neither a
   *         reaction nor a parameter of the model.
   */
  public NamedSBase getVariable2Instance() {
    return isSetVariable2() ? findVariable(variable2) : null;
  }

  /**
   * Returns whether the second variable is set.
   *
   * @return whether the second variable is set.
   */
  public boolean isSetVariable2() {
    return variable2 != null;
  }

  /**
   * Sets the id of the {@link Reaction} or {@link Parameter} that is the
   * second variable of a quadratic component.
   *
   * @param variable2 the id of a {@link Reaction} or {@link Parameter}.
   */
  public void setVariable2(String variable2) {
    String oldVariable2 = this.variable2;
    this.variable2 = variable2;
    firePropertyChange(FBCConstants.variable2, oldVariable2, this.variable2);
  }

  /**
   * Unsets the second variable.
   *
   * @return {@code true}, if the second variable was set before, otherwise
   *         {@code false}.
   */
  public boolean unsetVariable2() {
    if (isSetVariable2()) {
      setVariable2(null);
      return true;
    }
    return false;
  }

  /**
   * Returns the type of the variable (linear or quadratic).
   *
   * @return the type of the variable, {@code null} if it is not set.
   */
  public FBCVariableType getVariableType() {
    return variableType;
  }

  /**
   * Returns whether the type of the variable is set.
   *
   * @return whether the type of the variable is set.
   */
  public boolean isSetVariableType() {
    return variableType != null;
  }

  /**
   * Sets the type of the variable (linear or quadratic).
   *
   * @param variableType the type of the variable, {@code null} unsets it.
   */
  public void setVariableType(FBCVariableType variableType) {
    FBCVariableType oldVariableType = this.variableType;
    this.variableType = variableType;
    firePropertyChange(FBCConstants.variableType, oldVariableType, this.variableType);
  }

  /**
   * Sets the type of the variable from its value in SBML.
   *
   * @param variableType {@code linear} or {@code quadratic}.
   * @throws IllegalArgumentException if the value is not a variable type.
   * @see FBCVariableType#fromString(String)
   */
  public void setVariableType(String variableType) {
    setVariableType(FBCVariableType.fromString(variableType));
  }

  /**
   * Unsets the type of the variable.
   *
   * @return {@code true}, if the type of the variable was set before,
   *         otherwise {@code false}.
   */
  public boolean unsetVariableType() {
    if (isSetVariableType()) {
      setVariableType((FBCVariableType) null);
      return true;
    }
    return false;
  }

  /**
   * Returns the {@link Reaction} or else the {@link Parameter} of the model
   * with the given id.
   *
   * @param id the id of a reaction or parameter.
   * @return the {@link Reaction} or {@link Parameter}, {@code null} if the
   *         model has neither.
   */
  private NamedSBase findVariable(String id) {
    Model model = getModel();
    if (model == null) {
      return null;
    }
    Reaction reaction = model.getReaction(id);
    return (reaction != null) ? reaction : model.getParameter(id);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 2647;
    int result = super.hashCode();
    result = prime * result + ((coefficient == null) ? 0 : coefficient.hashCode());
    result = prime * result + ((variable == null) ? 0 : variable.hashCode());
    result = prime * result + ((variable2 == null) ? 0 : variable2.hashCode());
    result = prime * result + ((variableType == null) ? 0 : variableType.hashCode());
    return result;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#equals(java.lang.Object)
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
    UserDefinedConstraintComponent other = (UserDefinedConstraintComponent) obj;
    return equal(coefficient, other.coefficient) && equal(variable, other.variable)
        && equal(variable2, other.variable2) && (variableType == other.variableType);
  }

  /**
   * @param a an object or {@code null}
   * @param b an object or {@code null}
   * @return whether both are {@code null} or equal.
   */
  private static boolean equal(Object a, Object b) {
    return (a == null) ? (b == null) : a.equals(b);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.element.SBase#readAttribute(String attributeName, String prefix, String value)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);

    if (!isAttributeRead) {
      isAttributeRead = true;

      if (attributeName.equals(FBCConstants.coefficient)) {
        setCoefficient(value);
      } else if (attributeName.equals(FBCConstants.variable)) {
        setVariable(value);
      } else if (attributeName.equals(FBCConstants.variable2)) {
        setVariable2(value);
      } else if (attributeName.equals(FBCConstants.variableType)) {
        setVariableType(value);
      } else {
        isAttributeRead = false;
      }
    }

    return isAttributeRead;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.element.SBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetId()) {
      attributes.remove("id");
      attributes.put(FBCConstants.shortLabel + ":id", getId());
    }
    if (isSetName()) {
      attributes.remove("name");
      attributes.put(FBCConstants.shortLabel + ":name", getName());
    }
    if (isSetCoefficient()) {
      attributes.put(FBCConstants.shortLabel + ":" + FBCConstants.coefficient, getCoefficient());
    }
    if (isSetVariable()) {
      attributes.put(FBCConstants.shortLabel + ":" + FBCConstants.variable, getVariable());
    }
    if (isSetVariable2()) {
      attributes.put(FBCConstants.shortLabel + ":" + FBCConstants.variable2, getVariable2());
    }
    if (isSetVariableType()) {
      attributes.put(FBCConstants.shortLabel + ":" + FBCConstants.variableType,
        getVariableType().toString());
    }

    return attributes;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(getElementName());
    builder.append(" [id=").append(getId());
    builder.append(", name=").append(getName());
    builder.append(", coefficient=").append(coefficient);
    builder.append(", variable=").append(variable);
    builder.append(", variable2=").append(variable2);
    builder.append(", variableType=").append(variableType);
    builder.append("]");
    return builder.toString();
  }

}
