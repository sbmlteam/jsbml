/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2013 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */

package org.sbml.jsbml;

/**
 * An {@link Assignment} can be seen as a mathematical equation with a
 * {@link Variable} on the left hand side and an equation on the right hand side
 * that assigns a value to the {@link Variable}.
 * 
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * @version $Rev$
 */
public interface Assignment extends MathContainer {
	
	/**
	 * Error message to indicate that a {@link SpeciesReference} cannot be used
	 * as the {@link Variable} in this {@link Assignment} because the SBML Level
	 * < 3.
	 */
	public static final String ILLEGAL_VARIABLE_EXCEPTION_MSG = "Cannot set SpeciesReference %s as the Variable in %s for SBML Level < 3";
	
	/**
	 * Error message to be displayed in case that a {@link Variable} with
	 * constant property set to {@code true} is to be assigned to this
	 * {@link Assignment}.
	 */
	public static final String ILLEGAL_CONSTANT_VARIABLE_MSG = "Cannot set the constant variable %s as the target of this %s.";
	
	/**
	 * Message to be displayed if no {@link Variable} can be found in the
	 * associated {@link Model} that would have the desired identifier.
	 */
	public static final String NO_SUCH_VARIABLE_EXCEPTION_MSG = "Model %s does not contain any variable with identifier %s.";
	
	/**
	 * Returns the variableID of this {@link Assignment}. Returns an empty
	 *         {@link String} if it is not set.
	 * 
	 * @return the variableID of this {@link Assignment}. Returns an empty
	 *         {@link String} if it is not set.
	 */
	public String getVariable();

	/**
	 * Returns the {@link Variable} instance which has the variableID of this
	 *         {@link Assignment} as id. Return null if it doesn't exist.
	 * 
	 * @return the {@link Variable} instance which has the variableID of this
	 *         {@link Assignment} as id. Return null if it doesn't exist.
	 */
	public Variable getVariableInstance();

	/**
	 * Returns true if the variableID of this {@link Assignment} is not null.
	 * 
	 * @return true if the variableID of this {@link Assignment} is not null.
	 */
	public boolean isSetVariable();

	/**
	 * Returns true if the {@link Variable} instance which has the variableID of
	 *         this {@link Assignment} as id is not null.
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
