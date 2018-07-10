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

import org.sbml.jsbml.Unit.Kind;
import org.sbml.jsbml.util.TreeNodeChangeEvent;

/**
 * An explicit {@link Rule} is a rule that explicitly declares its variable
 * element. Hence, this class provides methods to access and manipulate the
 * variable field of the underlying {@link Rule}.
 * 
 * @author Andreas Dr&auml;ger
 * @since 0.8
 */
public abstract class ExplicitRule extends Rule implements Assignment,
SBaseWithUnit {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 7458241628289830621L;

  /**
   * Represents the 'units' XML attribute of a ParameterRule.
   * 
   * @deprecated This is a requirement for Level 1 Version 1 and Version 2,
   *             but can only be used in conjunction with {@link Parameter}s.
   *             In this case this {@link AssignmentRule} represents the SBML
   *             element ParameterRule.
   */
  @Deprecated
  protected String unitsID;

  /**
   * Represents the id of a {@link Variable}. Matches the variable XML
   * attribute of an assignmentRule or rateRule element.
   */
  String variableID;

  /**
   * Creates a new {@link ExplicitRule}.
   */
  public ExplicitRule() {
    super();
    initDefaults();
  }

  /**
   * Creates a new {@link ExplicitRule}
   * 
   * @param math
   * @param level
   * @param version
   */
  public ExplicitRule(ASTNode math, int level, int version) {
    super(math, level, version);
    initDefaults();
  }

  /**
   * Creates a new {@link ExplicitRule} with the given {@link ASTNode} element
   * for the assignment to the given {@link Variable} or the derivative of the
   * {@link Variable}.
   * 
   * @param math
   *            An assignment
   * @param variable
   *            Either the variable itself or its derivative is to be modified
   *            with the given.
   */
  public ExplicitRule(ASTNode math, Variable variable) {
    this(variable, math);
  }

  /**
   * Creates a new {@link ExplicitRule}
   * 
   * @param rule
   */
  public ExplicitRule(ExplicitRule rule) {
    super(rule);
    initDefaults();
    if (rule.isSetVariable()) {
      setVariable(new String(rule.getVariable()));
    }
    if (rule.isSetUnits()) {
      setUnits(new String(rule.getUnits()));
    }
  }

  /**
   * Creates a new {@link ExplicitRule}
   * 
   * @param level
   * @param version
   */
  public ExplicitRule(int level, int version) {
    super(level, version);
    initDefaults();
  }

  /**
   * Creates a new {@link ExplicitRule}
   * 
   * @param parameter
   */
  public ExplicitRule(Parameter parameter) {
    this((Variable) parameter);
    UnitDefinition ud = parameter.getDerivedUnitDefinition();
    if ((ud != null) && ud.isSetId()) {
      unitsID = new String(ud.getId());
    } else {
      unitsID = null;
    }
  }

  /**
   * Creates a new {@link ExplicitRule}
   * 
   * @param parameter
   * @param math
   */
  public ExplicitRule(Parameter parameter, ASTNode math) {
    this((Variable) parameter, math);
    if (parameter.isSetUnits()) {
      unitsID = new String(parameter.getUnits());
    } else {
      unitsID = null;
    }
  }

  /**
   * Creates an {@link ExplicitRule} instance from a given {@link Variable}.
   * Takes level and version from the variable.
   * 
   * @param variable
   */
  public ExplicitRule(Variable variable) {
    this(variable.getLevel(), variable.getVersion());
    setVariable(variable);
  }

  /**
   * Creates a new {@link ExplicitRule}
   * 
   * @param variable
   * @param math
   */
  public ExplicitRule(Variable variable, ASTNode math) {
    super(math, variable.getLevel(), variable.getVersion());
    initDefaults();
    if (variable.isSetId()) {
      variableID = new String(variable.getId());
    } else {
      variableID = null;
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.Rule#clone()
   */
  @Override
  public abstract ExplicitRule clone();

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equals = super.equals(object);
    if (equals) {
      ExplicitRule r = (ExplicitRule) object;
      equals &= isSetVariable() == r.isSetVariable();
      if (equals && isSetVariable()) {
        equals &= getVariable().equals(r.getVariable());
      }
      equals &= isSetUnits() == r.isSetUnits();
      if (equals && isSetUnits()) {
        equals &= getUnits().equals(r.getUnits());
      }
    }
    return equals;
  }

  /**
   * Returns the unitsID of this object.
   * 
   * @return the unitsID of this object.
   * @deprecated This is a requirement for Level 1 Version 1 and Version 2,
   *             but can only be used in conjunction with {@link Parameter}s.
   *             In this case this {@link AssignmentRule} represents the SBML
   *             element ParameterRule.
   */
  @Override
  @Deprecated
  public String getUnits() {
    return isSetUnits() ? unitsID : "";
  }

  /**
   * 
   * @return the UnitDefinition instance which matches the unitsID of this
   *         object.
   * @deprecated This is a requirement for Level 1 Version 1 and Version 2,
   *             but can only be used in conjunction with {@link Parameter}s.
   *             In this case this {@link AssignmentRule} represents the SBML
   *             element ParameterRule.
   */
  @Override
  @Deprecated
  public UnitDefinition getUnitsInstance() {
    Model model = getModel();
    return model != null ? model.getUnitDefinition(unitsID) : null;
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
    final int prime = 919;
    int hashCode = super.hashCode();
    if (isSetVariable()) {
      hashCode += prime * getVariable().hashCode();
    }
    if (isSetUnits()) {
      hashCode += prime * getUnits().hashCode();
    }
    return hashCode;
  }

  /**
   * 
   */
  public void initDefaults() {
    variableID = null;
    unitsID = null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.element.Rule#isCompartmentVolume()
   */
  @Override
  public boolean isCompartmentVolume() {
    return isSetVariable()
        && (getVariableInstance() instanceof Compartment);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.element.Rule#isParameter()
   */
  @Override
  public boolean isParameter() {
    return isSetVariableInstance()
        && (getVariableInstance() instanceof Parameter);
  }

  /**
   * Predicate returning true or false depending on whether this Rule is an
   * AssignmentRule (SBML Level 2) or has a 'type' attribute value of 'scalar'
   * (SBML Level 1).
   * 
   * @return {@code true} if this Rule is an AssignmentRule (Level 2) or has type
   *         'scalar' (Level 1), {@code false} otherwise.
   */
  public abstract boolean isScalar();

  /**
   * 
   * @return {@code true} if the unitsID of this object is not {@code null}.
   * @deprecated This is a requirement for Level 1 Version 1 and Version 2,
   *             but can only be used in conjunction with {@link Parameter}s.
   *             In this case this {@link AssignmentRule} represents the SBML
   *             element ParameterRule.
   */
  @Override
  @Deprecated
  public boolean isSetUnits() {
    return unitsID != null;
  }

  /**
   * 
   * @return {@code true} if the UnitsID of this object matches a no null
   *         UniDefinition of the model instance.
   * @deprecated This is a requirement for Level 1 Version 1 and Version 2,
   *             but can only be used in conjunction with {@link Parameter}s.
   *             In this case this {@link AssignmentRule} represents the SBML
   *             element ParameterRule.
   */
  @Override
  @Deprecated
  public boolean isSetUnitsInstance() {
    Model model = getModel();
    return model != null ? model.getUnitDefinition(unitsID) != null
        : false;
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
   * @see org.sbml.jsbml.element.Rule#isSpeciesConcentration()
   */
  @Override
  public boolean isSpeciesConcentration() {
    return isSetVariableInstance()
        && (getVariableInstance() instanceof Species);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.MathContainer#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix,
    String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix,
      value);
    
    // special case for SBML level 1 ParameterRule
    if ((getLevel() == 1) && (attributeName.equals("name"))) {
      isAttributeRead = false;
      unsetName();
      unsetId();
    }
    
    if (!isAttributeRead) {
      if (getLevel() == 1) {
        if ((attributeName.equals("compartment")
            || attributeName.equals("name") || attributeName
            .equals((getVersion() == 1) ? "specie" : "species")))
        {
          setVariable(value);
          return true;
        } else if (attributeName.equals("units")) {
          setUnits(value);
          return true;
        }

      } else if (attributeName.equals("variable")) {
        setVariable(value);
        return true;
      }
    }
    return isAttributeRead;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBaseWithUnit#setUnits(org.sbml.jsbml.Unit.Kind)
   */
  @Override
  @Deprecated
  public void setUnits(Kind unitKind) {
    setUnits(new Unit(1d, 0, unitKind, 1d, getLevel(), getVersion()));
  }

  /**
   * Sets the unitsID to 'unitsID'.
   * 
   * @param unitsID
   *            A valid identifier of a {@link UnitDefinition} in the
   *            {@link Model} or the name of one of the {@link Unit.Kind} base
   *            types.
   * @deprecated This is a requirement for Level 1 Version 1 and Version 2,
   *             but can only be used in conjunction with {@link Parameter}s.
   *             In this case this {@link AssignmentRule} represents the SBML
   *             element ParameterRule.
   * @throws PropertyNotAvailableException if Level is not 1.
   */
  @Override
  @Deprecated
  public void setUnits(String unitsID) {
    if (getLevel() != 1) {
      throw new PropertyNotAvailableException(TreeNodeChangeEvent.units, this);
    }
    if (isSetVariable() && !isParameter()) {
      throw new IllegalArgumentException(MessageFormat.format(
        "Cannot set unit {0} for a variable other than parameter", unitsID));
    }
    String oldUnitsID = this.unitsID;
    this.unitsID = unitsID;
    firePropertyChange(TreeNodeChangeEvent.units, oldUnitsID, unitsID);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBaseWithUnit#setUnits(org.sbml.jsbml.Unit)
   */
  @Override
  @Deprecated
  public void setUnits(Unit unit) {
    UnitDefinition ud = new UnitDefinition(unit.getKind().toString(),
      getLevel(), getVersion());
    ud.addUnit(unit);
    if ((unit.getExponent() != 1) || (unit.getScale() != 0)
        || (unit.getMultiplier() != 1d) || (unit.getOffset() != 0d)) {
      StringBuilder sb = new StringBuilder();
      sb.append(unit.getMultiplier());
      sb.append('_');
      sb.append(unit.getScale());
      sb.append('_');
      sb.append(unit.getKind().toString());
      sb.append('_');
      sb.append(unit.getExponent());
      ud.setId(sb.toString());
      Model m = getModel();
      if (m != null) {
        m.addUnitDefinition(ud);
      }
    }
    setUnits(ud);
  }

  /**
   * Sets the unitsID of this object with the id of 'units'.
   * 
   * @param units the unit of this rule.
   * @deprecated This is a requirement for Level 1 Version 1 and Version 2,
   *             but can only be used in conjunction with {@link Parameter}s.
   *             In this case this {@link AssignmentRule} represents the SBML
   *             element ParameterRule.
   */
  @Override
  @Deprecated
  public void setUnits(UnitDefinition units) {
    if (units != null) {
      setUnits(units.getId());
    } else {
      unsetUnits();
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.Assignment#setVariable(java.lang.String)
   */
  @Override
  public void setVariable(String variable) {
    // checkAndSetVariable(variable); // We cannot use that as the Object might not be defined yet in L3.

    if (variable != null && variable.trim().length() == 0) {
      variable = null;
    }
    
    if (!isReadingInProgress() && variable != null) {
      // This method will throw IllegalArgumentException if the given id does not respect the SId syntax
      checkIdentifier(variable);
    }
    
    String oldVariable = variableID;
    variableID = variable;
    firePropertyChange(TreeNodeChangeEvent.variable, oldVariable, variableID);

  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.Assignment#setVariable(org.sbml.jsbml.Variable)
   */
  @Override
  public void setVariable(Variable variable) {
    if (variable != null) {
      if (variable.isConstant()) {
        throw new IllegalArgumentException(MessageFormat.format(
          resourceBundle.getString("Assignment.ILLEGAL_CONSTANT_VARIABLE_MSG"),
          variable.getId(), getElementName()));
      }
      if (isSetUnits() && !(variable instanceof Parameter)) {
        throw new IllegalArgumentException(MessageFormat.format(
          "Variable expected to be an instance of Parameter because a Unit attribute is set already, but given is an instance of {0}.",
          variable.getElementName()));
      }
      if ((getLevel() < 3) && (variable instanceof SpeciesReference)) {
        throw new IllegalArgumentException(MessageFormat.format(
          resourceBundle.getString("Assignment.ILLEGAL_VARIABLE_EXCEPTION_MSG"),
          variable.getId(), getElementName()));
      }
      if (variable.isSetId()) {
        String oldVariable = variableID;
        variableID = variable.getId();
        firePropertyChange(TreeNodeChangeEvent.variable, oldVariable, variableID);
      } else {
        unsetVariable();
      }
    } else {
      unsetVariable();
    }
  }

  /**
   * Unsets the unitsID of this {@link ExplicitRule}.
   * 
   * @deprecated This is a requirement for Level 1 Version 1 and Version 2,
   *             but can only be used in conjunction with {@link Parameter}s.
   *             In this case this {@link ExplicitRule} represents the SBML
   *             element ParameterRule.
   */
  @Override
  @Deprecated
  public void unsetUnits() {
    String oldUnitsID = unitsID;
    unitsID = null;
    firePropertyChange(TreeNodeChangeEvent.units, oldUnitsID, unitsID);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.Assignment#unsetVariable()
   */
  @Override
  public void unsetVariable() {
    String oldVariableID = variableID;
    variableID = null;
    firePropertyChange(TreeNodeChangeEvent.variable, oldVariableID,
      variableID);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.element.SBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    
    if (isSetVariable()) {
      if (getLevel() > 1) {
        attributes.put("variable", getVariable());
      } else if (getLevel() == 1) {
        if (isSpeciesConcentration()) {
          attributes.put((getVersion() == 1) ? "specie" : "species",
            getVariable());          
        } else if (isCompartmentVolume()) {
          attributes.put("compartment", getVariable());
        } else if (isParameter()) {
          attributes.put("name", getVariable());
        }
        if (isSetUnits()) {
          attributes.put(TreeNodeChangeEvent.units, getUnits());
        }
        
        if (this instanceof RateRule) {
          attributes.put("type", "rate");
        } 
        // no need to write type='scalar' as it is the default value
        //else {
          //attributes.put("type", "scalar");
        //}
      }
    }
        
    return attributes;
  }

}
