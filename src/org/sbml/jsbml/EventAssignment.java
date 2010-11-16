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

import java.util.Map;

/**
 * Represents the eventAssignment XML element of a SBML file.
 * 
 * @author Andreas Dr&auml;ger
 * @author marine
 * 
 * @opt attributes
 * @opt types
 * @opt visibility
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
	 * Creates an EventAssignment instance. By default, the variableID is null.
	 */
	public EventAssignment() {
		super();
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

	/**
	 * Creates an EventAssignment instance from a level and version. By default,
	 * the variableID is null.
	 */
	public EventAssignment(int level, int version) {
		super(level, version);
		this.variableID = null;
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
				throw new IllegalArgumentException(String.format(
										NO_SUCH_VARIABLE_EXCEPTION_MSG,
										m.getId(), variable));
			}
			setVariable(nsb);
		} else {
			throw new NullPointerException(JSBML.UNDEFINED_MODEL_MSG);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractMathContainer#clone()
	 */
	public EventAssignment clone() {
		return new EventAssignment(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.MathContainer#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof EventAssignment) {
			EventAssignment ea = (EventAssignment) o;
			if ((!ea.isSetVariable() && isSetVariable())
					|| (ea.isSetVariable() && !isSetVariable())) {
				return false;
			}
			boolean equal = super.equals(o);
			if (ea.isSetVariable() && isSetVariable()) {
				equal &= ea.getVariable().equals(getVariable());
			}
			return equal;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getParent()
	 */
	@Override
	public Event getParent() {
		return (Event) super.getParent();
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
	 * @see org.sbml.jsbml.Assignment#setVariable(java.lang.String)
	 */
	public void setVariable(String variable) {
		String oldVariable = this.variableID;
		this.variableID = variable;
		firePropertyChange(SBaseChangedEvent.variable, oldVariable, variable);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Assignment#setVariable(org.sbml.jsbml.Variable)
	 */
	public void setVariable(Variable variable) {
		if (!variable.isConstant()) {
			if ((getLevel() < 3) && (variable instanceof SpeciesReference)) {
				throw new IllegalArgumentException(String.format(
						ILLEGAL_VARIABLE_EXCEPTION_MSG, variable.getId(),
						getElementName()));
			}
			setVariable(variable.getId());
		} else {
			throw new IllegalArgumentException(String.format(
					ILLEGAL_CONSTANT_VARIABLE_MSG, variable.getId(),
					getElementName()));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.MathContainer#toString()
	 */
	@Override
	public String toString() {
		if (getMath() != null && getVariable() != null) {
			return getVariable() + " = " + getMath().toString();
		} else if (isSetMath()) {
			return getMath().toString();
		} else if (isSetVariable()) {
			return getVariable() + " = 0";
		}
		return getElementName();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Assignment#unsetVariable()
	 */
	public void unsetVariable() {
		setVariable((String) null);
	}
	
	/*
	 * (non-Javadoc)
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
