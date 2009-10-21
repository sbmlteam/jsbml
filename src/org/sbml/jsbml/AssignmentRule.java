/*
 *  SBMLsqueezer creates rate equations for reactions in SBML files
 *  (http://sbml.org).
 *  Copyright (C) 2009 ZBIT, University of Tübingen, Andreas Dräger
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.sbml.jsbml;

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
	private Symbol variable;

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
		return variable.getId();
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
	 * @param variable
	 */
	public void setVariable(Symbol variable) {
		this.variable = variable;
		stateChanged();
	}

}
