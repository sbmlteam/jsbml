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
public class AssignmentRule extends ExplicitRule {

	/**
	 * Represents the 'units' XML attribute of a ParameterRule.
	 * 
	 * @deprecated This is a requirement for Level 1 Version 1 and Version 2,
	 *             but can only be used in conjunction with {@link Parameter}s.
	 *             In this case this {@link AssignmentRule} represents the SBML
	 *             element ParameterRule.
	 */
	@Deprecated
	private String unitsID;

	/**
	 * Creates an AssignmentRule instance. By default, the variableID is null.
	 */
	public AssignmentRule() {
		super();
		this.unitsID = null;
	}

	/**
	 * Creates an AssignmentRule instance from a given AssignmentRule.
	 * 
	 * @param sb
	 */
	public AssignmentRule(AssignmentRule sb) {
		super(sb);
		if (sb.isSetUnits()) {
			this.unitsID = new String(sb.getUnits());
		} else {
			this.unitsID = null;
		}
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

	/**
	 * Creates an AssignmentRule instance from a given variable. Takes level and
	 * version from the variable.
	 */
	public AssignmentRule(Variable variable) {
		super(variable);
		UnitDefinition ud = variable.getDerivedUnitDefinition();
		if ((ud != null) && ud.isSetId()) {
			this.unitsID = new String(ud.getId());
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
	 * @return the unitsID of this object.
	 * @deprecated This is a requirement for Level 1 Version 1 and Version 2,
	 *             but can only be used in conjunction with {@link Parameter}s.
	 *             In this case this {@link AssignmentRule} represents the SBML
	 *             element ParameterRule.
	 */
	@Deprecated
	public String getUnits() {
		return this.unitsID;
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
		this.unitsID = units.isSetId() ? units.getId() : null;
	}

	/**
	 * Unsets the unitsID of this {@link AssignmentRule}.
	 * @deprecated This is a requirement for Level 1 Version 1 and Version 2,
	 *             but can only be used in conjunction with {@link Parameter}s.
	 *             In this case this {@link AssignmentRule} represents the SBML
	 *             element ParameterRule.
	 */
	@Deprecated
	public void unsetUnits() {
		this.unitsID = null;
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
