/*
 * $Id:  ChangeListener.java 14:53:48 draeger $
 * $URL: ChangeListener.java $
 *
 * ---------------------------------------------------------------------------- 
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML> 
 * for the latest version of JSBML and more information about SBML. 
 * 
 * Copyright (C) 2009-2011 jointly by the following organizations: 
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
package org.sbml.jsbml.util;

import java.util.EventListener;
import java.util.EventObject;

/**
 * Common interface to gather all specialized change listeners.
 * 
 * @author Andreas Dr&auml;ger
 * @version $Rev$
 * @since 0.8
 * @date 18.07.2011
 */
public interface ChangeListener<E extends ChangeEvent<?>> extends EventListener {

	/**
	 * This method indicates that some property of an element has been changed.
	 * The given {@link EventObject} contains a pointer to the element, a
	 * {@link String} representation of the name of the property, whose value
	 * has been changed together with the previous and the new value. Casts may
	 * be necessary, because the {@link EventObject} contains the values as
	 * {@link Object} instances only.
	 * 
	 * @param event
	 */
	public void stateChanged(E event);

}
