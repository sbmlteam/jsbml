/*
 * $Id: AssignmentRule.java 38 2009-11-05 15:50:38Z niko-rodrigue $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/src/org/sbml/jsbml/AssignmentRule.java $
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

package org.sbml.jsbml.element;

import java.util.HashMap;

/**
 * Represents the assignmentRule XML element of a SBML file.
 * 
 * @author Andreas Dr&auml;ger <a
 *         href="mailto:andreas.draeger@uni-tuebingen.de">
 *         andreas.draeger@uni-tuebingen.de</a>
 * @author marine
 */
public class AssignmentRule extends Rule {

	/**
	 * represents the id of a compartment, species, speciesReference or parameter. Matches the variable XML
	 * attribute of an assignmentRule element.
	 */
	private String variableID;

	/**
	 * Creates an AssignmentRule instance. By default, the variableID is null.
	 */
	public AssignmentRule() {
		super();
		this.variableID = null;
	}
	
	/**
	 * Creates an AssignmentRule instance from a given AssignmentRule.
	 * @param sb
	 */
	public AssignmentRule(AssignmentRule sb) {
		super(sb);
		if (sb.isSetVariable()){
			this.variableID = new String(sb.getVariable());
		}
	}

	/**
	 * Creates an AssignmentRule instance from a given variable.
	 * Takes level and version from the variable.
	 */
	public AssignmentRule(Symbol variable) {
		super(variable.getLevel(), variable.getVersion());
		if (variable.isSetId()){
			this.variableID = new String(variable.getId());
		}
	}

	/**
	 * Creates an AssignmentRule instance from a given variable and math.
	 * Takes level and version from the variable.
	 * @param math
	 */
	public AssignmentRule(Symbol variable, ASTNode math) {
		super(math, variable.getLevel(), variable.getVersion());
		if (variable.isSetId()){
			this.variableID = new String(variable.getId());
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
	public boolean equals(Object o){
		if (o instanceof AssignmentRule){
			AssignmentRule rule = (AssignmentRule) o;
			boolean equals = super.equals(rule);
			
			if (equals){
				equals &= isSetVariable() == rule.isSetVariable();
				if (equals && isSetVariable()){
					equals &= getVariable().equals(rule.getVariable());
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
	 * @return the variable instance which matches the variableID of this object.
	 */
	public Symbol getVariableInstance() {
		Symbol variable = null;
		if (getModel() != null){
			variable = getModel().findSymbol(this.variableID);
		}
		return variable;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.Rule#isCompartmentVolume()
	 */
	@Override
	public boolean isCompartmentVolume() {
		return isSetVariableInstance() && (getVariableInstance() instanceof Compartment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.Rule#isParameter()
	 */
	@Override
	public boolean isParameter() {
		return isSetVariableInstance() && (getVariableInstance() instanceof Parameter);
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
	 * @return true if the variableID of this object matches a no null compartment, parameter, species or speciesReference
	 * of the model instance.
	 */
	public boolean isSetVariableInstance() {
		if (getModel() == null){
			return false;
		}
		return getModel().findSymbol(this.variableID) != null;
	}
	
	/**
	 * 
	 * @return true if the variableID of this object is not null.
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
		return isSetVariableInstance() && (getVariableInstance() instanceof Species);
	}

	/**
	 * Sets the variableID of this object with 'variable'. It looks first for an existing instance of
	 * compartment, species, speciesReference or parameter with 'variable' as is value, and then initialises
	 * the variableID of this object with the id of the variable instance. If no variable instance matches the
	 * 'variable' String, an exception is thrown.
	 * @param variable
	 */
	public void setVariable(String variable) {
		Symbol nsb = null;
		if (getModel() != null){
			nsb = getModel().findSymbol(variable);
		}
		if (nsb == null){
			throw new IllegalArgumentException(
			"Only the id of an existing Species, Compartments, or Parameters allowed as variables");
		}
		setVariable(nsb);
	}
	
	/**
	 * Sets the variableID to 'variableID'.
	 * @param variableID
	 */
	public void setVariableID(String variableID) {
		this.variableID = variableID;
		stateChanged();
	}

	/**
	 * Sets the variableID to the id of 'variable'.
	 * @param variable
	 */
	public void setVariable(Symbol variable) {
		this.variableID = variable != null ? variable.getId() : null;
		stateChanged();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.element.Rule#readAttribute(String attributeName, String prefix, String value)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix, String value){
		boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
		
		if (!isAttributeRead){
			if (attributeName.equals("variable")){
				this.setVariable(value);
				return true;
			}
		}
		return isAttributeRead;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.element.Rule#writeXMLAttributes()
	 */
	@Override
	public HashMap<String, String> writeXMLAttributes() {
		HashMap<String, String> attributes = super.writeXMLAttributes();
		
		if (isSetVariable()){
			attributes.put("variable", getVariable());
		}

		return attributes;
	}

}
