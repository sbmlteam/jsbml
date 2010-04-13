/*
 * $Id$
 * $URL$
 *
 *
 *==================================================================================
 * Copyright (c) 2009 the copyright is held jointly by the individual
 * authors. See the file AUTHORS for the list of authors.
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
 * 
 * Represents the assignmentRule XML element of a SBML file.
 * 
 * @author Andreas Dr&auml;ger
 * @author marine
 * 
 * @opt attributes
 * @opt types
 * @opt visibility
 */
public class AssignmentRule extends Rule {

	/**
	 * represents the id of a compartment, species, speciesReference or
	 * parameter. Matches the variable XML attribute of an assignmentRule
	 * element.
	 */
	private String variableID;

	/**
	 * represents the 'units' XML attribute of a ParameterRule.
	 */
	@Deprecated
	private String unitsID;

	/**
	 * Creates an AssignmentRule instance. By default, the variableID is null.
	 */
	public AssignmentRule() {
		super();
		this.variableID = null;
		this.unitsID = null;
	}

	/**
	 * Creates an AssignmentRule instance with the given level and version.
	 * 
	 * @param level
	 *            the SBML level
	 * @param version
	 *            the SBML version
	 */
	public AssignmentRule(int level, int version) {
		super(level, version);
		this.variableID = null;
	}

	/**
	 * Creates an AssignmentRule instance from a given AssignmentRule.
	 * 
	 * @param sb
	 */
	public AssignmentRule(AssignmentRule sb) {
		super(sb);
		if (sb.isSetVariable()) {
			this.variableID = new String(sb.getVariable());
		} else {
			this.variableID = null;
		}
		if (sb.isSetUnits()) {
			this.unitsID = new String(sb.getUnits());
		} else {
			this.unitsID = null;
		}
	}

	/**
	 * Creates an AssignmentRule instance from a given variable. Takes level and
	 * version from the variable.
	 */
	public AssignmentRule(State variable) {
		super(variable.getLevel(), variable.getVersion());
		if (variable.isSetId()) {
			this.variableID = new String(variable.getId());
		} else {
			this.variableID = null;
		}
		UnitDefinition ud = variable.getDerivedUnitDefinition();
		if (ud != null && ud.isSetId()) {
			this.unitsID = new String(ud.getId());
		} else {
			this.unitsID = null;
		}
	}

	/**
	 * Creates an AssignmentRule instance from a given variable and math. Takes
	 * level and version from the variable.
	 * 
	 * @param math
	 */
	public AssignmentRule(Symbol variable, ASTNode math) {
		super(math, variable.getLevel(), variable.getVersion());
		if (variable.isSetId()) {
			this.variableID = new String(variable.getId());
		} else {
			this.variableID = null;
		}
		if (variable.isSetUnits()) {
			this.unitsID = new String(variable.getUnits());
		} else {
			this.unitsID = null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.MathContainer#clone()
	 */
	// @Override
	public AssignmentRule clone() {
		return new AssignmentRule(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		if (o instanceof AssignmentRule) {
			AssignmentRule rule = (AssignmentRule) o;
			boolean equals = super.equals(rule);
			if (equals) {
				equals &= isSetVariable() == rule.isSetVariable();
				if (equals && isSetVariable()) {
					equals &= getVariable().equals(rule.getVariable());
				}
				equals &= isSetUnits() == rule.isSetUnits();
				if (equals && isSetUnits()) {
					equals &= getUnits().equals(rule.getUnits());
				}
			}
			return equals;
		}
		return false;
	}

	/**
	 * 
	 * @return the variableID of this object.
	 */
	public String getVariable() {
		return variableID;
	}

	/**
	 * 
	 * @return the UnitDefinition instance which matches the unitsID of this
	 *         object.
	 */
	@Deprecated
	public UnitDefinition getUnitsInstance() {
		Model model = getModel();
		return model != null ? model.getUnitDefinition(this.unitsID) : null;
	}

	/**
	 * @return the unitsID of this object.
	 */
	@Deprecated
	public String getUnits() {
		return this.unitsID;
	}

	/**
	 * 
	 * @return the variable instance which matches the variableID of this
	 *         object.
	 */
	public State getVariableInstance() {
		Model model = getModel();
		return model != null ? model.findState(this.variableID) : null;
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
		return isSetVariable() && (getVariableInstance() instanceof Parameter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.Rule#isScalar()
	 */
	@Override
	public boolean isScalar() {
		// TODO
		return true;
	}

	/**
	 * 
	 * @return true if the variableID of this object matches a no null
	 *         compartment, parameter, species or speciesReference of the model
	 *         instance.
	 */
	public boolean isSetVariableInstance() {
		Model model = getModel();
		return model != null ? model.findState(this.variableID) != null
				: false;
	}

	/**
	 * 
	 * @return true if the variableID of this object is not null.
	 */
	public boolean isSetVariable() {
		return variableID != null;
	}

	/**
	 * 
	 * @return true if the UnitsID of this object matches a no null
	 *         UniDefinition of the model instance.
	 */
	@Deprecated
	public boolean isSetUnitsInstance() {
		Model model = getModel();
		return model != null ? model.getUnitDefinition(this.unitsID) != null
				: false;
	}

	/**
	 * 
	 * @return true if the unitsID of this object is not null.
	 */
	@Deprecated
	public boolean isSetUnits() {
		return unitsID != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.Rule#isSpeciesConcentration()
	 */
	@Override
	public boolean isSpeciesConcentration() {
		return isSetVariable() && (getVariableInstance() instanceof Species);
	}

	/**
	 * Sets the variableID of this object with 'variable'. It looks first for an
	 * existing instance of compartment, species, speciesReference or parameter
	 * with 'variable' as is value, and then initialises the variableID of this
	 * object with the id of the variable instance. If no variable instance
	 * matches the 'variable' String, an exception is thrown.
	 * 
	 * @param variable
	 */
	public void checkAndSetVariable(String variable) {
		State nsb = null;
		Model model = getModel();
		if (model != null) {
			nsb = model.findState(variable);
		}
		if (nsb == null) {
			throw new IllegalArgumentException(
					"Only the id of an existing Species, Compartments, or Parameters allowed as variables");
		}
		setVariable(nsb.getId());
	}

	/**
	 * Sets the unitsID to 'unitsID'.
	 * 
	 * @param unitsID
	 */
	@Deprecated
	public void setUnits(String unitsID) {
		this.unitsID = unitsID;
		stateChanged();
	}

	/**
	 * Sets the unitsID of this object with the id of 'units'.
	 * 
	 * @param variable
	 */
	@Deprecated
	public void setUnits(UnitDefinition units) {
		this.unitsID = units.isSetId() ? units.getId() : null;
	}

	/**
	 * Sets the variableID to 'variableID'.
	 * 
	 * @param variableID
	 */
	public void setVariable(String variableID) {
		this.variableID = variableID;
		stateChanged();
	}

	/**
	 * Sets the variableID to the id of 'variable'.
	 * 
	 * @param variable
	 */
	public void setVariable(State variable) {
		this.variableID = variable != null ? variable.getId() : null;
		stateChanged();
	}

	/**
	 * Unsets the unitsID of this AssignmentRule.
	 */
	@Deprecated
	public void unsetUnits() {
		this.unitsID = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.Rule#readAttribute(String attributeName,
	 * String prefix, String value)
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.Rule#writeXMLAttributes()
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
