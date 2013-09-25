/*
 * $Id$
 * $URL$
 *
 * ---------------------------------------------------------------------------- 
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML> 
 * for the latest version of JSBML and more information about SBML. 
 * 
 * Copyright (C) 2009-2013 jointly by the following organizations: 
 * 1. The University of Tuebingen, Germany 
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK 
 * 3. The California Institute of Technology, Pasadena, CA, USA 
 * 
 * This library is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by 
 * the Free Software Foundation. A copy of the license agreement is provided 
 * in the file named "LICENSE.txt" included with this software distribution 
 * and also available online as <http://sbml.org/Software/JSBML/License>. 
 * ---------------------------------------------------------------------------- 
 */ 
package org.sbml.jsbml;

import org.sbml.jsbml.ext.SBasePlugin;

/**
 * This {@link PropertyException} indicates that the value belonging to a mandatory
 * property, for which there is no default value, has not been declared by the
 * user.
 * 
 * @author Andreas Dr&auml;ger
 * @version $Rev$
 * @since 0.8
 * @date 24.03.2011
 */
public class PropertyUndefinedError extends PropertyException {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = 2990146647017147195L;

	/**
	 * Message to indicate that a certain property has not been set for the
	 * current {@link SBase} in its level/version combination.
	 */
	public static final String PROPERTY_UNDEFINED_EXCEPTION_MSG = "The value for property %s is not defined in %s for Level %d and Version %d.";
	
	/**
	 * @param property 
	 * @param sbase
	 */
	public PropertyUndefinedError(String property, SBase sbase) {
		super(createMessage(PROPERTY_UNDEFINED_EXCEPTION_MSG, property, sbase));
	}

	public PropertyUndefinedError(String property, SBasePlugin sbasePlugin) {
		// TODO : change to include package short name or namespace ??
		super(createMessage(PROPERTY_UNDEFINED_EXCEPTION_MSG, property, sbasePlugin));
	}

}
