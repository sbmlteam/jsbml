/*
 * $Id:  ExplicitRule.java 16:44:37 draeger $
 * $URL: ExplicitRule.java $
 *
 * 
 *==================================================================================
 * Copyright (c) 2009 The jsbml team.
 *
 * This file is part of jsbml, the pure java SBML library. Please visit
 * http://sbml.org for more information about SBML, and http://jsbml.sourceforge.net/
 * to get the latest version of jsbml.
 *
 * jsbml is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * jsbml is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with jsbml.  If not, see <http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html>.
 *
 *===================================================================================
 *
 */
package org.sbml.jsbml;

import java.util.HashMap;

/**
 * An explicit {@link Rule} is a rule that explicitly declares its variable
 * element. Hence, this class provides methods to access and manipulate the
 * variable field of the underlying {@link Rule}.
 * 
 * @author Andreas Dr&auml;ger
 * @date 2010-08-05
 */
public abstract class ExplicitRule extends Rule implements Assignment {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = 7458241628289830621L;

	/**
	 * Represents the id of a {@link Variable}. Matches the variable XML
	 * attribute of an assignmentRule or rateRule element.
	 */
	String variableID;

	/**
	 * Represents the 'units' XML attribute of a ParameterRule.
	 * 
	 * @deprecated This is a requirement for Level 1 Version 1 and Version 2,
	 *             but can only be used in conjunction with {@link Parameter}s.
	 *             In this case this {@link AssignmentRule} represents the SBML
	 *             element ParameterRule.
	 */
	@Deprecated
	String unitsID;

	/**
	 * 
	 */
	public ExplicitRule() {
		super();
		initDefaults();
	}

	/**
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
	 * @param level
	 * @param version
	 */
	public ExplicitRule(int level, int version) {
		super(level, version);
		initDefaults();
	}

	/**
	 * 
	 * @param parameter
	 */
	public ExplicitRule(Parameter parameter) {
		this((Variable) parameter);
		UnitDefinition ud = parameter.getDerivedUnitDefinition();
		if ((ud != null) && ud.isSetId()) {
			this.unitsID = new String(ud.getId());
		} else {
			this.unitsID = null;
		}
	}

	/**
	 * 
	 * @param parameter
	 * @param math
	 */
	public ExplicitRule(Parameter parameter, ASTNode math) {
		this((Variable) parameter, math);
		if (parameter.isSetUnits()) {
			this.unitsID = new String(parameter.getUnits());
		} else {
			this.unitsID = null;
		}
	}

	/**
	 * Creates a RateRule instance from a given Symbol. Takes level and version
	 * from the variable.
	 * 
	 * @param variable
	 */
	public ExplicitRule(Variable variable) {
		this(variable.getLevel(), variable.getVersion());
		checkAndSetVariable(variable.getId());
	}

	/**
	 * 
	 * @param variable
	 * @param math
	 */
	public ExplicitRule(Variable variable, ASTNode math) {
		super(math, variable.getLevel(), variable.getVersion());
		initDefaults();
		if (variable.isSetId()) {
			this.variableID = new String(variable.getId());
		} else {
			this.variableID = null;
		}
	}

	/**
	 * Sets the variableID of this {@link ExplicitRule} to 'variable'. If no
	 * {@link Variable} instance has 'variable'as id, an
	 * IllegalArgumentException is thrown.
	 * 
	 * @param variable
	 */
	public void checkAndSetVariable(String variable) {
		Variable v = null;
		Model m = getModel();
		if (m != null) {
			/*
			 * We can only check this condition if the rule has been added to a
			 * model already. Otherwise it is not possible.
			 */
			v = m.findVariable(variable);
			if (v == null) {
				String l3specific = getLevel() > 2 ? "SpeciesReference, " : "";
				throw new IllegalArgumentException(
						"Only the id of an existing Species, "
								+ l3specific
								+ "Compartment, or Parameter allowed as variables");
			}
			setVariable(v);
		} else {
			// TODO: potential source of bugs.
			variableID = variable;
			stateChanged();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Rule#clone()
	 */
	public abstract ExplicitRule clone();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Rule#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (o.getClass().getSimpleName().equals(getClass().getSimpleName())) {
			ExplicitRule r = (ExplicitRule) o;
			boolean equals = super.equals(o);
			equals &= isSetVariable() == r.isSetVariable();
			if (equals && isSetVariable()) {
				equals &= getVariable().equals(r.getVariable());
			}
			equals &= isSetUnits() == r.isSetUnits();
			if (equals && isSetUnits()) {
				equals &= getUnits().equals(r.getUnits());
			}
			return equals;
		}
		return false;
	}

	/**
	 * @return the unitsID of this object.
	 * @deprecated This is a requirement for Level 1 Version 1 and Version 2,
	 *             but can only be used in conjunction with {@link Parameter}s.
	 *             In this case this {@link AssignmentRule} represents the SBML
	 *             element ParameterRule.
	 */
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
	@Deprecated
	public UnitDefinition getUnitsInstance() {
		Model model = getModel();
		return model != null ? model.getUnitDefinition(this.unitsID) : null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Assignment#getVariable()
	 */
	public String getVariable() {
		return isSetVariable() ? this.variableID : "";
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Assignment#getVariableInstance()
	 */
	public Variable getVariableInstance() {
		Model m = getModel();
		return m != null ? m.findVariable(this.variableID) : null;
	}

	/**
	 * 
	 */
	public void initDefaults() {
		variableID = null;
		unitsID = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.Rule#isCompartmentVolume()
	 */
	@Override
	public boolean isCompartmentVolume() {
		return isSetVariable()
				&& (getVariableInstance() instanceof Compartment);
	}

	/*
	 * (non-Javadoc)
	 * 
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
	 * @return true if this Rule is an AssignmentRule (Level 2) or has type
	 *         'scalar' (Level 1), false otherwise.
	 */
	public abstract boolean isScalar();

	/**
	 * 
	 * @return true if the unitsID of this object is not null.
	 * @deprecated This is a requirement for Level 1 Version 1 and Version 2,
	 *             but can only be used in conjunction with {@link Parameter}s.
	 *             In this case this {@link AssignmentRule} represents the SBML
	 *             element ParameterRule.
	 */
	@Deprecated
	public boolean isSetUnits() {
		return unitsID != null;
	}

	/**
	 * 
	 * @return true if the UnitsID of this object matches a no null
	 *         UniDefinition of the model instance.
	 * @deprecated This is a requirement for Level 1 Version 1 and Version 2,
	 *             but can only be used in conjunction with {@link Parameter}s.
	 *             In this case this {@link AssignmentRule} represents the SBML
	 *             element ParameterRule.
	 */
	@Deprecated
	public boolean isSetUnitsInstance() {
		Model model = getModel();
		return model != null ? model.getUnitDefinition(this.unitsID) != null
				: false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Assignment#isSetVariable()
	 */
	public boolean isSetVariable() {
		return variableID != null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Assignment#isSetVariableInstance()
	 */
	public boolean isSetVariableInstance() {
		Model m = getModel();
		return m != null ? m.findVariable(this.variableID) != null : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.Rule#isSpeciesConcentration()
	 */
	@Override
	public boolean isSpeciesConcentration() {
		return isSetVariableInstance()
				&& (getVariableInstance() instanceof Species);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.MathContainer#readAttribute(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix,
			String value) {
		boolean isAttributeRead = super.readAttribute(attributeName, prefix,
				value);
		if (!isAttributeRead) {
			if (attributeName.equals("variable") && getLevel() > 1) {
				this.setVariable(value);
				return true;
			} else if (attributeName.equals("specie") && getLevel() == 1) {
				this.setVariable(value);
				return true;
			} else if (attributeName.equals("compartment") && getLevel() == 1) {
				this.setVariable(value);
				return true;
			} else if (attributeName.equals("name") && getLevel() == 1) {
				this.setVariable(value);
				return true;
			} else if (attributeName.equals("units") && getLevel() == 1) {
				this.setUnits(value);
				return true;
			}
		}
		return isAttributeRead;
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
	 */
	@Deprecated
	public void setUnits(String unitsID) {
		if (isSetVariable() && !isParameter()) {
			throw new IllegalArgumentException(
					"Cannot set units for a variable other than parameter");
		}
		this.unitsID = unitsID;
		stateChanged();
	}

	/**
	 * Sets the unitsID of this object with the id of 'units'.
	 * 
	 * @param variable
	 * @deprecated This is a requirement for Level 1 Version 1 and Version 2,
	 *             but can only be used in conjunction with {@link Parameter}s.
	 *             In this case this {@link AssignmentRule} represents the SBML
	 *             element ParameterRule.
	 */
	@Deprecated
	public void setUnits(UnitDefinition units) {
		setUnits(units.isSetId() ? units.getId() : null);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Assignment#setVariable(java.lang.String)
	 */
	public void setVariable(String variable) {
		checkAndSetVariable(variable);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Assignment#setVariable(org.sbml.jsbml.Variable)
	 */
	public void setVariable(Variable variable) {
		if (variable != null) {
			if (variable.isConstant()) {
				throw new IllegalArgumentException(
						String.format(
										"Cannot set the constant variable %s as the target of this %s.",
										variable.getId(), getClass()
												.getSimpleName()));
			}
			if (isSetUnits() && !(variable instanceof Parameter)) {
				throw new IllegalArgumentException(
						"Variable expected to be an instance of Parameter because a Unit attribute is set allready");
			}
			if ((2 < getLevel()) && (variable instanceof SpeciesReference)) {
				throw new IllegalArgumentException(
						"Cannot set a SpeciesReference as the Variable in an ExpliciteRule for SBML Level < 3");
			}
			if (variable.isSetId()) {
				variableID = variable.getId();
				stateChanged();
			} else {
				unsetVariable();
			}
		} else {
			unsetVariable();
		}
	}

	/**
	 * Unsets the unitsID of this {@link AssignmentRule}.
	 * 
	 * @deprecated This is a requirement for Level 1 Version 1 and Version 2,
	 *             but can only be used in conjunction with {@link Parameter}s.
	 *             In this case this {@link AssignmentRule} represents the SBML
	 *             element ParameterRule.
	 */
	@Deprecated
	public void unsetUnits() {
		this.unitsID = null;
		stateChanged();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Assignment#unsetVariable()
	 */
	public void unsetVariable() {
		variableID = null;
		stateChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#writeXMLAttributes()
	 */
	@Override
	public HashMap<String, String> writeXMLAttributes() {
		HashMap<String, String> attributes = super.writeXMLAttributes();
		if (isSetVariable()) {
			if (getLevel() > 1) {
				attributes.put("variable", getVariable());
			} else if (getLevel() == 1) {
				if (isSpeciesConcentration() && getVersion() == 1) {
					attributes.put("specie", getVariable());
				} else if (isSpeciesConcentration() && getVersion() == 2) {
					attributes.put("species", getVariable());
				} else if (getLevel() == 1 && isCompartmentVolume()) {
					attributes.put("compartment", getVariable());
				} else if (getLevel() == 1 && isParameter()) {
					attributes.put("name", getVariable());
				}
			}
		}
		if (isSetUnits() && getLevel() == 1) {
			attributes.put("units", getUnits());
		}
		return attributes;
	}
}
