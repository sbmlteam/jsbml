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
 * Represents the initialAssignment XML element of a SBML file.
 * 
 * @author Andreas Dr&auml;ger
 * @author Marine Dumousseau
 * @since 0.8
 */
public class InitialAssignment extends AbstractMathContainer implements Assignment {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 2798071640186792089L;
  /**
   * Represents the 'symbol' XML attribute of an initialAssignmnent element.
   */
  private String variableID;

  /**
   * Creates an InitialAssignment instance. By default, variableID is {@code null}.
   */
  public InitialAssignment() {
    super();
    variableID = null;
  }

  /**
   * Creates an InitialAssignment instance from a given InitialAssignment.
   * 
   * @param sb
   */
  public InitialAssignment(InitialAssignment sb) {
    super(sb);
    if (sb.isSetVariable()) {
      variableID = new String(sb.getVariable());
    } else {
      variableID = null;
    }
  }

  /**
   * Creates an InitialAssignment from level and version.
   * 
   * @param level
   * @param version
   */
  public InitialAssignment(int level, int version) {
    super(level, version);
    if (getLevelAndVersion().compareTo(Integer.valueOf(2),
      Integer.valueOf(2)) < 0) {
      throw new IllegalArgumentException(MessageFormat.format(
        "Cannot create a {0} with Level = {1,number,integer} and Version = {2,number,integer}.",
        getElementName(), getLevel(), getVersion()));
    }
  }

  /**
   * Creates an InitialAssignment instance from a {@link Variable}. Takes
   * level and version from the given variable.
   * 
   * @param variable
   */
  public InitialAssignment(Variable variable) {
    super(variable.getLevel(), variable.getVersion());
    if (variable.isSetId()) {
      variableID = new String(variable.getId());
    } else {
      variableID = null;
    }
  }

  /**
   * Creates an InitialAssignment from a {@link Variable}, {@link ASTNode},
   * level and version.
   * 
   * @param variable
   * @param math
   * @param level
   * @param version
   */
  public InitialAssignment(Variable variable, ASTNode math, int level,
    int version) {
    super(math, level, version);
    if (variable.isSetId()) {
      variableID = new String(variable.getId());
    } else {
      variableID = null;
    }
  }

  /**
   * Sets the variableID of this {@link InitialAssignment} to 'variable'. If this
   * variableID doesn't match any {@link Variable} id in {@link Model} (
   * {@link Compartment}, {@link Species}, {@link SpeciesReference}, or
   * {@link Parameter}), an {@link IllegalArgumentException} is thrown.
   * 
   * @param variable
   *           the symbol to set
   */
  public void checkAndSetVariable(String variable) {
    Variable nsb = null;
    Model m = getModel();
    if (m != null) {
      nsb = m.findVariable(variable);
    }
    if (nsb == null) {
      throw new IllegalArgumentException(MessageFormat.format(
        resourceBundle.getString("Assignment.NO_SUCH_VARIABLE_EXCEPTION_MSG"),
        m.getId(), variable));
    }
    setVariable(nsb.getId());
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractMathContainer#clone()
   */
  @Override
  public InitialAssignment clone() {
    return new InitialAssignment(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equals = super.equals(object);
    if (equals) {
      InitialAssignment in = (InitialAssignment) object;
      equals &= in.isSetVariable() == isSetVariable();
      if (equals && isSetVariable()) {
        equals &= in.getVariable().equals(getVariable());
      }
    }
    return equals;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getParent()
   */
  @SuppressWarnings("unchecked")
  @Override
  public ListOf<InitialAssignment> getParent() {
    return (ListOf<InitialAssignment>) super.getParent();
  }

  /**
   * This method is for compatibility with libSBML only.
   * 
   * @return the variableID of this {@link InitialAssignment}. Return an empty
   *         {@link String} if it is not set.
   * @deprecated use {@link #getVariable()}
   */
  @Deprecated
  public String getSymbol() {
    return getVariable();
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
    final int prime = 853;
    int hashCode = super.hashCode();
    if (isSetVariable()) {
      hashCode += prime * getVariable().hashCode();
    }
    return hashCode;
  }

  /**
   * 
   * @return {@code true} if the variableID of this InitialAssignment is not
   *         {@code null}.
   */
  public boolean isSetSymbol() {
    return isSetVariable();
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
   * @see org.sbml.jsbml.element.SBase#readAttribute(String attributeName, String prefix, String value)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix,
    String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix,
      value);
    if (!isAttributeRead) {
      if (attributeName.equals("symbol")) {
        this.setVariable(value);
        return true;
      }
    }
    return isAttributeRead;
  }

  /**
   * This method is provided for compatibility with libSBML and also to
   * reflect what is written in the SBML specifications until L3V1, but for
   * consistency, JSBML uses the term {@link Variable} to refer to elements
   * that satisfy the properties of this interface.
   * 
   * @param symbol
   * @deprecated use {@link #setVariable(String)}.
   */
  @Deprecated
  public void setSymbol(String symbol) {
    setVariable(symbol);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.Assignment#setVariable(java.lang.String)
   */
  @Override
  public void setVariable(String variable) {
    if (getLevelAndVersion().compareTo(Integer.valueOf(2), Integer.valueOf(2)) < 0) {
      throw new PropertyNotAvailableException(TreeNodeChangeEvent.variable, this);
    }
    
    if (!isReadingInProgress() && variable != null) {
      // This method will throw IllegalArgumentException if the given id does not respect the SId syntax
      checkIdentifier(variable);
    }
    
    String oldVariableID = variableID;
    variableID = variable;
    firePropertyChange(TreeNodeChangeEvent.variable, oldVariableID, variable);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.Assignment#setVariable(org.sbml.jsbml.Variable)
   */
  @Override
  public void setVariable(Variable variable) {
    if ((getLevel() < 3) && (variable != null) && (variable instanceof SpeciesReference)) {
      throw new IllegalArgumentException(MessageFormat.format(
        resourceBundle.getString("Assignment.ILLEGAL_VARIABLE_EXCEPTION_MSG"),
        variable.getId(), getElementName()));
    }
    setVariable(variable != null ? variable.getId() : null);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.Assignment#unsetVariable()
   */
  @Override
  public void unsetVariable() {
    setVariable((String) null);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.element.SBase#writeXMLAttributes(
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetVariable()) {
      attributes.put(TreeNodeChangeEvent.symbol, getVariable());
    }

    return attributes;
  }

}
