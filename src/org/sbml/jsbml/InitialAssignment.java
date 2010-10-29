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
 * Represents the initialAssignment XML element of a SBML file.
 * 
 * @author Andreas Dr&auml;ger
 * @author marine
 * 
 * @opt attributes
 * @opt types
 * @opt visibility
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
	 * Creates an InitialAssignment instance. By default, variableID is null.
	 */
	public InitialAssignment() {
		super();
		this.variableID = null;
	}

	/**
	 * Creates an InitialAssignment instance from a given InitialAssignment.
	 * 
	 * @param sb
	 */
	public InitialAssignment(InitialAssignment sb) {
		super(sb);
		if (sb.isSetVariable()) {
			this.variableID = new String(sb.getVariable());
		} else {
			this.variableID = null;
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
			this.variableID = new String(variable.getId());
		} else {
			this.variableID = null;
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
			this.variableID = new String(variable.getId());
		} else {
			this.variableID = null;
		}
	}

	/**
	 * Sets the variableID of this InitialAssignment to 'variable'. If this
	 * variableID doesn't match any {@link Variable} id in {@link Model} (
	 * {@link Compartment}, {@link Species}, {@link SpeciesReference}, or
	 * {@link Parameter}), an {@link IllegalArgumentException} is thrown.
	 * 
	 * @param variable
	 *            : the symbol to set
	 */
	public void checkAndSetVariable(String variable) {
		Variable nsb = null;
		Model m = getModel();
		if (m != null) {
			nsb = m.findVariable(variable);
		}
		if (nsb == null) {
			throw new IllegalArgumentException(
					"only the id of an existing Species, Compartments, or Parameters allowed as variables");
		}
		setVariable(nsb.getId());
		stateChanged();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractMathContainer#clone()
	 */
	public InitialAssignment clone() {
		return new InitialAssignment(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.MathContainer#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof InitialAssignment) {
			InitialAssignment in = (InitialAssignment) o;
			boolean equals = super.equals(in);
			if ((!in.isSetVariable() && isSetVariable())
					|| (in.isSetVariable() && !isSetVariable())) {
				return false;
			}
			if (in.isSetVariable() && isSetVariable()) {
				equals &= in.getVariable().equals(getVariable());
			}
			return equals;
		}
		return false;
	}

	/**
	 * @return the variableID of this InitialAssignment. Return an empty String
	 *         if it is not set.
	 */
	public String getSymbol() {
		return getVariable();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Assignment#getVariable()
	 */
	public String getVariable() {
		return isSetVariable() ? variableID : "";
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
	 * @return true if the variableID of this InitialAssignment is not null.
	 */
	public boolean isSetSymbol() {
		return isSetVariable();
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
	 * @see org.sbml.jsbml.element.SBase#readAttribute(String attributeName,
	 * String prefix, String value)
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

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Assignment#setVariable(java.lang.String)
	 */
	public void setVariable(String variable) {
		this.variableID = variable;
		stateChanged();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Assignment#setVariable(org.sbml.jsbml.Variable)
	 */
	public void setVariable(Variable variable) {
		this.variableID = variable != null ? variable.getId() : null;
		stateChanged();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Assignment#unsetVariable()
	 */
	public void unsetVariable() {
		this.variableID = null;
		stateChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#writeXMLAttributes(
	 */
	@Override
	public HashMap<String, String> writeXMLAttributes() {
		HashMap<String, String> attributes = super.writeXMLAttributes();

		if (isSetVariable()) {
			attributes.put("symbol", getVariable());
		}

		return attributes;
	}
}
