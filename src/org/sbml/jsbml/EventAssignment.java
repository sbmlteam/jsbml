/*
 * $Id$
 * $URL$
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
 * Represents the eventAssignment XML element of a SBML file.
 * 
 * @author Andreas Dr&auml;ger
 * @author marine
 */
public class EventAssignment extends MathContainer {

	/**
	 * Represents the 'variable' XML attribute of an eventAssignment element.
	 */
	private String variableID;

	/**
	 * Creates an EventAssignment instance. By default, the variableID is null.
	 */
	public EventAssignment() {
		super();
		this.variableID = null;
	}

	/**
	 * Creates an EventAssignment instance from a level and version. By default,
	 * the variableID is null.
	 */
	public EventAssignment(int level, int version) {
		super(level, version);
		this.variableID = null;
	}

	/**
	 * Creates an EventAssignment instance from a given EventAssignment.
	 * 
	 * @param eventAssignment
	 */
	public EventAssignment(EventAssignment eventAssignment) {
		super(eventAssignment);
		if (isSetVariable()) {
			this.variableID = new String(eventAssignment.getVariable());
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
	public EventAssignment clone() {
		return new EventAssignment(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.MathContainer#equals(java.lang.Object)
	 */
	// @Override
	public boolean equals(Object o) {
		boolean equal = super.equals(o);
		if (o instanceof EventAssignment) {
			EventAssignment ea = (EventAssignment) o;
			if ((!ea.isSetVariable() && isSetVariable())
					|| (ea.isSetVariable() && !isSetVariable())) {
				return false;
			}
			if (ea.isSetVariable() && isSetVariable()) {
				equal &= ea.getVariable().equals(getVariable());
			}
			return equal;
		}
		return false;
	}

	/**
	 * 
	 * @return true if the id of the Symbol which matches the variableID of this
	 *         Event is not null.
	 */
	public boolean isSetVariableInstance() {
		if (getModel() == null) {
			return false;
		}
		return getModel().findSymbol(this.variableID) != null;
	}

	/**
	 * 
	 * @return true if the variableID of this Event is not null.
	 */
	public boolean isSetVariable() {
		return variableID != null;
	}

	/**
	 * 
	 * @return the variableID of this Event. Return an empty String if it is not
	 *         set.
	 */
	public String getVariable() {
		return isSetVariable() ? this.variableID : "";
	}

	/**
	 * 
	 * @return the Symbol instance (Compartment, Species, SpeciesReference or
	 *         Parameter) which has the variableID of this EventAssignment as
	 *         id. Return null if it doesn't exist.
	 */
	public Symbol getVariableInstance() {
		if (getModel() == null) {
			return null;
		}
		return getModel().findSymbol(this.variableID);
	}

	/**
	 * Sets the variableID of this EventAssignment to the id of the Symbol
	 * variable only if the Symbol is a Species, Compartment, SpeciesReference
	 * or Parameter.
	 * 
	 * @param variable
	 */
	public void setVariable(Symbol variable) {
		if ((variable instanceof Species) || variable instanceof Compartment
				|| (variable instanceof Parameter)
				|| (variable instanceof SpeciesReference)) {
			this.variableID = variable.getId();
			stateChanged();
		} else {
			throw new IllegalArgumentException(
					"Only Species, Compartments, SpeciesReferences or Parameters allowed as variables");
		}
	}

	/**
	 * Sets the variableID of this EventAssignment to 'variable'. If 'variable'
	 * doesn't match any id of Compartment , Species, SpeciesReference or
	 * Parameter in Model, an IllegalArgumentException is thrown.
	 * 
	 * @param variable
	 */
	public void checkAndSetVariable(String variable) {
		Symbol nsb = getModel().findSymbol(variable);
		if (nsb == null) {
			throw new IllegalArgumentException(
					"Only the id of an existing Species, Compartments, or Parameters allowed as variables");
		}
		setVariable(nsb);
		stateChanged();
	}

	/**
	 * Sets the variableID of this EventAssignment to 'variable'.
	 * 
	 * @param variable
	 */
	public void setVariable(String variable) {
		this.variableID = variable;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#toString()
	 */
	// @Override
	public String toString() {
		if (getMath() != null && getVariable() != null) {
			return getVariable() + " = " + getMath().toString();
		} else if (isSetMath()) {
			return getMath().toString();
		} else if (isSetVariable()) {
			return getVariable() + " = 0";
		}
		return "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see readAttribute(String attributeName, String prefix, String value)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix,
			String value) {
		boolean isAttributeRead = super.readAttribute(attributeName, prefix,
				value);

		if (!isAttributeRead && getLevel() >= 2) {
			if (attributeName.equals("variable")) {
				this.setVariable(value);
				return true;
			}
		}
		return isAttributeRead;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see writeXMLAttributes()
	 */
	@Override
	public HashMap<String, String> writeXMLAttributes() {
		HashMap<String, String> attributes = super.writeXMLAttributes();

		if (isSetVariable() && getLevel() >= 2) {
			attributes.put("variable", getVariable());
		}

		return attributes;
	}
}
