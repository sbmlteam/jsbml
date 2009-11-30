/*
 * $Id: EventAssignment.java 38 2009-11-05 15:50:38Z niko-rodrigue $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/src/org/sbml/jsbml/EventAssignment.java $
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

package org.sbml.jsbml.element;

import java.util.HashMap;

/**
 * @author Andreas Dr&auml;ger <a
 *         href="mailto:andreas.draeger@uni-tuebingen.de">
 *         andreas.draeger@uni-tuebingen.de</a>
 * 
 */
public class EventAssignment extends MathContainer {

	/**
	 * 
	 */
	private String variableID;
	
	/**
	 * 
	 */
	private Symbol variable;

	/**
	 * 
	 */
	public EventAssignment() {
		super();
		variable = null;
		this.variableID = null;
	}
	
	/**
	 * 
	 */
	public EventAssignment(int level, int version) {
		super(level, version);
		variable = null;
		this.variableID = null;
	}

	/**
	 * 
	 * @param eventAssignment
	 */
	public EventAssignment(EventAssignment eventAssignment) {
		super(eventAssignment);
		setVariable(eventAssignment.getVariable());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.MathElement#clone()
	 */
	// @Override
	public EventAssignment clone() {
		return new EventAssignment(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.MathElement#equals(java.lang.Object)
	 */
	// @Override
	public boolean equals(Object o) {
		boolean equal = super.equals(o);
		if (o instanceof EventAssignment) {
			EventAssignment ea = (EventAssignment) o;
			if ((!ea.isSetVariable() && isSetVariable())
					|| (ea.isSetVariable() && !isSetVariable()))
				return false;
			if (ea.isSetVariable() && isSetVariable())
				equal &= ea.getVariable().equals(getVariable());
			return equal;
		}
		return false;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSetVariable() {
		return variable != null;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isSetVariableID() {
		return variableID != null;
	}

	/**
	 * 
	 * @return
	 */
	public String getVariable() {
		return variableID;
	}

	/**
	 * 
	 * @return
	 */
	public Symbol getVariableInstance() {
		return variable;
	}

	/**
	 * 
	 * @param variable
	 */
	public void setVariable(Symbol variable) {
		if ((variable instanceof Species) || variable instanceof Compartment
				|| (variable instanceof Parameter))
			this.variable = variable;
		else
			throw new IllegalArgumentException(
					"Only Species, Compartments, or Parameters allowed as variables");
	}

	/**
	 * 
	 * @param variable
	 */
	public void setVariable(String variable) {
		Symbol nsb = getModel().findSymbol(variable);
		if (nsb == null)
			throw new IllegalArgumentException(
					"Only the id of an existing Species, Compartments, or Parameters allowed as variables");
		setVariable(nsb);
	}
	
	/**
	 * 
	 * @param variable
	 */
	public void setVariableID(String variable) {
		this.variableID = variable;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBase#toString()
	 */
	// @Override
	public String toString() {
		if (getMath() != null && getVariable() != null){
			return getVariable() + " = " + getMath().toString();
		}
		else if (getMath() != null){
			return getMath().toString();
		}
		else if (getVariable() != null){
			return getVariable().toString() + " = 0";
		}
		return "";
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix, String value){
		boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
		
		if (!isAttributeRead){
			if (attributeName.equals("variable")){
				this.setVariableID(value);
				return true;
			}
		}
		return isAttributeRead;
	}

	@Override
	public HashMap<String, String> writeXMLAttributes() {
		HashMap<String, String> attributes = super.writeXMLAttributes();
		
		if (isSetVariableID()){
			attributes.put("variable", getVariable());
		}
		
		return attributes;
	}
}
