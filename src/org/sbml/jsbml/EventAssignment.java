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
import java.util.LinkedList;

import javax.swing.tree.TreeNode;

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
	}

	/**
	 * Sets the variableID of this EventAssignment to 'variable'. If 'variable'
	 * doesn't match any id of Compartment , Species, SpeciesReference or
	 * Parameter in Model, an IllegalArgumentException is thrown.
	 * 
	 * @param variable
	 */
	public void checkAndSetVariable(String variable) {
		Model m = getModel();
		if (m != null) {
			Variable nsb = getModel().findVariable(variable);
			if (nsb == null) {
				throw new IllegalArgumentException(
						"Only the id of an existing Species, Compartments, Parameters, or SpeciesReferences allowed as variables");
			}
			setVariable(nsb);
			stateChanged();
		} else {
			throw new NullPointerException(
					"Cannot find a model for this EventAssignment");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.MathContainer#clone()
	 */
	@Override
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
	public Variable getVariableInstance() {
		Model m = getModel();
		return m != null ? m.findVariable(this.variableID) : null;
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
	 * @return true if the id of the Symbol which matches the variableID of this
	 *         Event is not null.
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

	/**
	 * Sets the variableID of this EventAssignment to 'variable'.
	 * 
	 * @param variable
	 */
	public void setVariable(String variable) {
		this.variableID = variable;
	}

	/**
	 * Sets the variableID of this EventAssignment to the id of the Symbol
	 * variable only if the Symbol is a Species, Compartment, SpeciesReference
	 * or Parameter.
	 * 
	 * @param variable
	 */
	public void setVariable(Variable variable) {
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
		return "";
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

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.MathContainer#getChildAt(int)
	 */
	@Override
	public TreeNode getChildAt(int index) {
		int children = getChildCount();
		if ((0 < children) && (index < children)) {
			LinkedList<TreeNode> l = new LinkedList<TreeNode>();
			if (isSetVariable()) {
				l.add(getVariableInstance());
			}
			if (isSetMath()) {
				l.add(getMath());
			}
			return l.get(index);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeNode#getChildCount()
	 */
	public int getChildCount() {
		int children = super.getChildCount();
		if (isSetVariable()) {
			children++;
		}
		return children;
	}
}
