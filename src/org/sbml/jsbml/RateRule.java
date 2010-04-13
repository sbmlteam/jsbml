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
 * Represents the rateRule XML element of a SBML file.
 * 
 * @author Andreas Dr&auml;ger
 * @author marine
 * 
 * @opt attributes
 * @opt types
 * @opt visibility
 */
public class RateRule extends Rule {

	/**
	 * Represents the 'variable' XML attribute of a rateRule element.
	 */
	private String variableID;

	/**
	 * Creates a RateRule instance. By default, the variableID is null.
	 */
	public RateRule() {
		super();
		this.variableID = null;
	}

	/**
	 * Creates a RateRule instance from a given RateRule.
	 * 
	 * @param sb
	 */
	public RateRule(int level, int version) {
		super(level, version);
		this.variableID = null;
	}

	/**
	 * @param sb
	 */
	public RateRule(RateRule sb) {
		super(sb);
		if (sb.isSetVariable()) {
			this.variableID = new String(sb.getVariable());
		} else {
			this.variableID = null;
		}
	}

	/**
	 * Creates a RateRule instance from a given Symbol. Takes level and version
	 * from the variable.
	 * 
	 * @param variable
	 */
	public RateRule(Symbol variable) {
		super(variable.getLevel(), variable.getVersion());
		if (variable.isSetId()) {
			this.variableID = new String(variable.getId());
		} else {
			this.variableID = null;
		}
	}

	/**
	 * Creates a RateRule instance from a given Symbol and ASTNode. Takes level
	 * and version from the variable.
	 * 
	 * @param variable
	 * @param math
	 */
	public RateRule(Symbol variable, ASTNode math) {
		super(math, variable.getLevel(), variable.getVersion());
		if (variable.isSetId()) {
			this.variableID = new String(variable.getId());
		} else {
			this.variableID = null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.MathContainer#clone()
	 */
	// @Override
	public RateRule clone() {
		return new RateRule(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		if (o instanceof RateRule) {
			RateRule r = (RateRule) o;
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
	 * @return the variableID of this RateRule. Returns an empty String if
	 *         variableID is not set.
	 */
	public String getVariable() {
		return isSetVariable() ? this.variableID : "";
	}

	/**
	 * 
	 * @return the Symbol instance which has the variableID of this RateRule as
	 *         id. Null if it doesn't exist.
	 */
	public State getVariableInstance() {
		Model m = getModel();
		return m != null ? m.findState(this.variableID) : null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.Rule#isCompartmentVolume()
	 */
	@Override
	public boolean isCompartmentVolume() {
		return isSetVariableInstance()
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.Rule#isScalar()
	 */
	@Override
	public boolean isScalar() {
		return false;
	}

	/**
	 * 
	 * @return true if the Symbol instance which has the variableID of this
	 *         RateRule as id is not null.
	 */
	public boolean isSetVariableInstance() {
		Model m = getModel();
		return m != null ? m.findState(this.variableID) != null : false;
	}

	/**
	 * 
	 * @return true if the variableID of this RateRule is not null.
	 */
	public boolean isSetVariable() {
		return variableID != null;
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

	/**
	 * Sets the variableID of this RateRule to 'variable'. If no Symbol instance
	 * has 'variable'as id, an IllegalArgumentException is thrown.
	 * 
	 * @param variable
	 */
	public void checkAndSetVariable(String variable) {
		State nsb = null;
		Model m = getModel();
		if (m != null) {
			nsb = m.findState(variable);
		}
		if (nsb == null)
			throw new IllegalArgumentException(
					"Only the id of an existing Species, SpeciesReferences, Compartments, or Parameters allowed as variables");
		setVariable(nsb.getId());
		stateChanged();
	}

	/**
	 * Sets the variableID of this RateRule to 'variable'.
	 * 
	 * @param variable
	 */
	public void setVariable(String variable) {
		this.variableID = variable;
		stateChanged();
	}

	/**
	 * Sets the variableID of this RateRule to the id of the Symbol 'variable'.
	 * 
	 * @param variable
	 */
	public void setVariable(State variable) {
		this.variableID = variable != null ? variable.getId() : null;
		stateChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#readAttribute(String attributeName,
	 * String prefix, String value)
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
