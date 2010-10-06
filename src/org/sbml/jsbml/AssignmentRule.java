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

/**
 * 
 * Represents the assignmentRule XML element of a SBML file.
 * 
 * @author Andreas Dr&auml;ger
 * @author marine
 * 
 * @opt attributes
 * @opt types
 * @opt visibility
 */
public class AssignmentRule extends ExplicitRule {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = 683172080347613789L;

	/**
	 * Creates an AssignmentRule instance. By default, the variableID is null.
	 */
	public AssignmentRule() {
		super();
	}

	/**
	 * Creates an AssignmentRule instance from a given AssignmentRule.
	 * 
	 * @param sb
	 */
	public AssignmentRule(AssignmentRule sb) {
		super(sb);
	}

	/**
	 * 
	 * @param math
	 * @param level
	 * @param version
	 */
	public AssignmentRule(ASTNode math, int level, int version) {
		super(math, level, version);
	}

	/**
	 * 
	 * @param math
	 * @param parameter
	 */
	public AssignmentRule(ASTNode math, Parameter parameter) {
		this(parameter, math);
	}

	/**
	 * 
	 * @param variable
	 * @param math
	 */
	public AssignmentRule(ASTNode math, Variable variable) {
		this(variable, math);
	}

	/**
	 * Creates an AssignmentRule instance with the given level and version.
	 * 
	 * @param level
	 *            the SBML level
	 * @param version
	 *            the SBML version
	 */
	public AssignmentRule(int level, int version) {
		super(level, version);
	}

	/**
	 * 
	 * @param parameter
	 */
	public AssignmentRule(Parameter parameter) {
		super(parameter);
	}

	/**
	 * 
	 * @param parameter
	 * @param math
	 */
	public AssignmentRule(Parameter parameter, ASTNode math) {
		super(parameter, math);
	}

	/**
	 * Creates an AssignmentRule instance from a given variable. Takes level and
	 * version from the variable.
	 */
	public AssignmentRule(Variable variable) {
		super(variable);
	}

	/**
	 * Creates an AssignmentRule instance from a given variable and math. Takes
	 * level and version from the variable.
	 * 
	 * @param math
	 */
	public AssignmentRule(Variable variable, ASTNode math) {
		super(variable, math);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.MathContainer#clone()
	 */
	@Override
	public AssignmentRule clone() {
		return new AssignmentRule(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ExplicitRule#isScalar()
	 */
	@Override
	public boolean isScalar() {
		return true;
	}
}
