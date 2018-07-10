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
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml;

import java.text.MessageFormat;
import java.util.Map;

import org.sbml.jsbml.util.TreeNodeChangeEvent;

/**
 * Represents the eventAssignment XML element of a SBML file.
 * 
 * @author Andreas Dr&auml;ger
 * @author Marine Dumousseau
 * @since 0.8
 */
public class EventAssignment extends AbstractMathContainer implements Assignment {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -263409745456083049L;

  /**
   * Represents the 'variable' XML attribute of an eventAssignment element.
   */
  private String variableID;

  /**
   * Creates an EventAssignment instance. By default, the variableID is {@code null}.
   */
  public EventAssignment() {
    super();
    variableID = null;
  }

  /**
   * Creates an EventAssignment instance from a given EventAssignment.
   * 
   * @param eventAssignment
   */
  public EventAssignment(EventAssignment eventAssignment) {
    super(eventAssignment);
    if (eventAssignment.isSetVariable()) {
      variableID = new String(eventAssignment.getVariable());
    }
  }

  /**
   * Creates an EventAssignment instance from a level and version. By default,
   * the variableID is {@code null}.
   * @param level
   * @param version
   */
  public EventAssignment(int level, int version) {
    super(level, version);
    variableID = null;
    if (isSetLevel() && (getLevel() < 2)) {
      throw new IllegalAccessError("Cannot create an EventAssignment with Level < 2.");
    }
  }

  /**
   * Sets the variableID of this EventAssignment to 'variable'. If 'variable'
   * doesn't match any id of Compartment , Species, SpeciesReference or
   * Parameter in Model, an {@link IllegalArgumentException} is thrown.
   * 
   * @param variable
   * @throws IllegalArgumentException
   */
  public void checkAndSetVariable(String variable) {
    Model m = getModel();
    if (m != null) {
      Variable nsb = getModel().findVariable(variable);
      if (nsb == null) {
        throw new IllegalArgumentException(MessageFormat.format(
          resourceBundle.getString("Assignment.NO_SUCH_VARIABLE_EXCEPTION_MSG"),
          m.getId(), variable));
      }
      setVariable(nsb);
    } else {
      throw new NullPointerException(JSBML.UNDEFINED_MODEL_MSG);
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractMathContainer#clone()
   */
  @Override
  public EventAssignment clone() {
    return new EventAssignment(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equals = super.equals(object);
    if (equals) {
      EventAssignment ea = (EventAssignment) object;
      equals &= ea.isSetVariable() == isSetVariable();
      if (equals && isSetVariable()) {
        equals &= ea.getVariable().equals(getVariable());
      }
    }
    return equals;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getParent()
   */
  @SuppressWarnings("unchecked")
  @Override
  public ListOf<EventAssignment> getParent() {
    return (ListOf<EventAssignment>) super.getParent();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.Assignment#getVariable()
   */
  @Override
  public String getVariable() {
    return isSetVariable() ? variableID : "";
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.Assignment#getVariableInstance()
   */
  @Override
  public Variable getVariableInstance() {
    Model m = getModel();
    return m != null ? m.findVariable(variableID) : null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 839;
    int hashCode = super.hashCode();
    if (isSetVariable()) {
      hashCode += prime * getVariable().hashCode();
    }
    return hashCode;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.Assignment#isSetVariable()
   */
  @Override
  public boolean isSetVariable() {
    return variableID != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.Assignment#isSetVariableInstance()
   */
  @Override
  public boolean isSetVariableInstance() {
    Model m = getModel();
    return m != null ? m.findVariable(variableID) != null : false;
  }

  /* (non-Javadoc)
   * @see readAttribute(String attributeName, String prefix, String value)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix,
    String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix,
      value);
    if (!isAttributeRead) {
      if (attributeName.equals("variable")) {
        setVariable(value);
        return true;
      }
    }
    return isAttributeRead;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.Assignment#setVariable(java.lang.String)
   */
  @Override
  public void setVariable(String variable) {
    
    if (!isReadingInProgress() && variable != null) {
      // This method will throw IllegalArgumentException if the given id does not respect the SId syntax
      checkIdentifier(variable);
    }
    
    String oldVariable = variableID;
    variableID = variable;
    firePropertyChange(TreeNodeChangeEvent.variable, oldVariable, variable);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.Assignment#setVariable(org.sbml.jsbml.Variable)
   */
  @Override
  public void setVariable(Variable variable) {
    if (!variable.isConstant()) {
      if ((getLevel() < 3) && (variable instanceof SpeciesReference)) {
        throw new IllegalArgumentException(MessageFormat.format(
          resourceBundle.getString("Assignment.ILLEGAL_VARIABLE_EXCEPTION_MSG"),
          variable.getId(), getElementName()));
      }
      if (variable.isSetId()) {
        setVariable(variable.getId());
      } else {
        unsetVariable();
      }
    } else {
      throw new IllegalArgumentException(MessageFormat.format(
        resourceBundle.getString("Assignment.ILLEGAL_CONSTANT_VARIABLE_MSG"),
        variable.getId(), getElementName()));
    }
  }

  /**
   * Creates a formula {@link String} representation of this assignment.
   * 
   * @return
   */
  public String toFormula() {
    if (getMath() != null && getVariable() != null) {
      return getVariable() + " = " + getMath().toFormula();
    } else if (isSetMath()) {
      return getMath().toFormula();
    } else if (isSetVariable()) {
      return getVariable() + " = 0";
    }
    return getElementName();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.Assignment#unsetVariable()
   */
  @Override
  public void unsetVariable() {
    setVariable((String) null);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractMathContainer#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetVariable() && getLevel() >= 2) {
      attributes.put("variable", getVariable());
    }
    return attributes;
  }

}
