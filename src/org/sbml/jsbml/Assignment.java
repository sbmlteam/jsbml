/*
 * $Id:  Assignment.java 09:44:49 draeger $
 * $URL: Assignment.java $
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

/**
 * An {@link Assignment} can be seen as a mathematical equation with a
 * {@link Variable} on the left hand side and an equation on the right hand side
 * that assigns a value to the {@link Variable}.
 * 
 * @author Andreas Dr&auml;ger
 * @date 20010-10-29
 */
public interface Assignment extends MathContainer {

	/**
	 * @return the variableID of this {@link Assignment}. Returns an empty
	 *         {@link String} if it is not set.
	 */
	public String getVariable();

	/**
	 * @return the {@link Variable} instance which has the variableID of this
	 *         {@link Assignment} as id. Return null if it doesn't exist.
	 */
	public Variable getVariableInstance();

	/**
	 * 
	 * @return true if the variableID of this {@link Assignment} is not null.
	 */
	public boolean isSetVariable();

	/**
	 * 
	 * @return true if the {@link Variable} instance which has the variableID of
	 *         this {@link Assignment} as id is not null.
	 */
	public boolean isSetVariableInstance();

	/**
	 * Sets the variableID of this {@link Assignment} to the given value. Listeners
	 * are notified about this change.
	 * 
	 * @param variableID
	 *            : the variable to set
	 */
	public void setVariable(String variableID);

	/**
	 * Sets the variableID of this {@link Assignment} to the id of the
	 * {@link Variable} 'variable'.Listeners are notified about this change.
	 * 
	 * @param variable
	 *            : the variable to set
	 */
	public void setVariable(Variable variable);

	/**
	 * Removes the reference from this InitialAssignment to its {@link Variable}
	 * if there was any, i.e., the {@link Variable} is set to null. Listeners
	 * are notified about this change.
	 */
	public void unsetVariable();

}
