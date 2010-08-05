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
public abstract class ExplicitRule extends Rule {

	/**
	 * Represents the id of a {@link Variable}. Matches the variable XML
	 * attribute of an assignmentRule or rateRule element.
	 */
	String variableID;

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
	 * @param rule
	 */
	public ExplicitRule(ExplicitRule rule) {
		super(rule);
		variableID = rule.isSetVariable() ? new String(rule.getVariable())
				: null;
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
	 * Creates a RateRule instance from a given Symbol. Takes level and version
	 * from the variable.
	 * 
	 * @param variable
	 */
	public ExplicitRule(Variable variable) {
		super(variable.getLevel(), variable.getVersion());
		checkAndSetVariable(variable.getId());
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
		super(math, variable.getLevel(), variable.getVersion());
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
		Variable nsb = null;
		Model m = getModel();
		if (m != null) {
			nsb = m.findVariable(variable);
		}
		if (nsb == null) {
			throw new IllegalArgumentException(
					"Only the id of an existing Species, SpeciesReferences, Compartments, or Parameters allowed as variables");
		}
		variableID = nsb.getId();
		stateChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Rule#clone()
	 */
	@Override
	public abstract ExplicitRule clone();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Rule#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof ExplicitRule) {
			ExplicitRule r = (ExplicitRule) o;
			boolean equal = super.equals(o);
			equal &= isSetVariable() == r.isSetVariable();
			if (equal && isSetVariable()) {
				equal &= getVariable().equals(r.getVariable());
			}
			return equal;
		}
		return false;
	}

	/**
	 * 
	 * @return the variableID of this {@link ExplicitRule}. Returns an empty
	 *         String if variableID is not set.
	 */
	public String getVariable() {
		return isSetVariable() ? this.variableID : "";
	}

	/**
	 * 
	 * @return the {@link Variable} instance which has the variableID of this
	 *         {@link ExplicitRule} as id. Null if it doesn't exist.
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
	 * @return true if the variableID of this {@link ExplicitRule} is not null.
	 */
	public boolean isSetVariable() {
		return variableID != null;
	}

	/**
	 * 
	 * @return true if the {@link Variable} instance which has the variableID of
	 *         this {@link ExplicitRule} as id is not null.
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
		if (attributeName.equals("variable")) {
			this.checkAndSetVariable(value);
			return true;
		}
		return isAttributeRead;
	}

	/**
	 * Sets the variableID of this {@link ExplicitRule} to 'variable'.
	 * 
	 * @param variable
	 */
	public void setVariable(String variable) {
		checkAndSetVariable(variable);
	}

	/**
	 * Sets the variableID of this {@link ExplicitRule} to the id of the
	 * {@link Variable} 'variable'.
	 * 
	 * @param variable
	 */
	public void setVariable(Variable variable) {
		this.variableID = variable != null ? variable.getId() : null;
		stateChanged();
	}

	/**
	 * Removes the reference of this {@link ExplicitRule} to the identifier of a
	 * {@link Variable} if there was any, i.e., the variableID field is set to
	 * null.
	 */
	public void unsetVariable() {
		variableID = null;
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
			attributes.put("variable", getVariable());
		}
		return attributes;
	}
}
