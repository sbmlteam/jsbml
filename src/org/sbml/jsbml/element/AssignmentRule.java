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

/**
 * @author Andreas Dr&auml;ger <a
 *         href="mailto:andreas.draeger@uni-tuebingen.de">
 *         andreas.draeger@uni-tuebingen.de</a>
 * 
 */
public class AssignmentRule extends Rule {

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
	public AssignmentRule() {
		super();
		this.variable = null;
		this.variableID = null;
	}
	
	/**
	 * @param sb
	 */
	public AssignmentRule(AssignmentRule sb) {
		super(sb);
		this.variable = sb.getVariableInstance();
	}

	/**
	 * Takes level and version from the variable.
	 */
	public AssignmentRule(Symbol variable) {
		super(variable.getLevel(), variable.getVersion());
		this.variable = variable;
	}

	/**
	 * Takes level and version from the variable.
	 * 
	 * @param math
	 */
	public AssignmentRule(Symbol variable, ASTNode math) {
		super(math, variable.getLevel(), variable.getVersion());
		this.variable = variable;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.MathContainer#clone()
	 */
	// @Override
	public AssignmentRule clone() {
		return new AssignmentRule(this);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Rule#isCompartmentVolume()
	 */
	@Override
	public boolean isCompartmentVolume() {
		return isSetVariable() && (variable instanceof Compartment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Rule#isParameter()
	 */
	@Override
	public boolean isParameter() {
		return isSetVariable() && (variable instanceof Parameter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Rule#isScalar()
	 */
	@Override
	public boolean isScalar() {
		// TODO
		return true;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Rule#isSpeciesConcentration()
	 */
	@Override
	public boolean isSpeciesConcentration() {
		return isSetVariable() && (variable instanceof Species);
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
	 * @param variableID
	 */
	public void setVariableID(String variableID) {
		this.variableID = variableID;
	}

	/**
	 * 
	 * @param variable
	 */
	public void setVariable(Symbol variable) {
		this.variable = variable;
		stateChanged();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Rule#isSpeciesConcentration()
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

}
