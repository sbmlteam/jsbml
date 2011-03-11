/*
 * $Id$
 * $URL$
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

package org.sbml.jsbml;

import java.util.EventListener;

/**
 * A listener interface that allows applications to get notified if the state of
 * any SBase object changes.
 * 
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * @version $Rev$
 */
public interface SBaseChangedListener extends EventListener {

	/**
	 * The {@link SBase} passed to this method has just been added to the
	 * {@link SBMLDocument} or another containing element.
	 * 
	 * @param sb
	 *            This element is now part of the {@link SBMLDocument}.
	 */
	public void sbaseAdded(SBase sb);

	/**
	 * The {@link SBase} passed to this method has been removed from a
	 * containing parent and does hence no longer belong to the
	 * {@link SBMLDocument} anymore.
	 * 
	 * @param sb
	 *            This element is not longer part of the {@link SBMLDocument}.
	 */
	public void sbaseRemoved(SBase sb);

	/**
	 * This method indicates that some property of an {@link SBase} has been
	 * changed. The given {@link SBaseChangedEvent} contains a pointer to the
	 * {@link SBase}, a {@link String} representation of the name of the
	 * property, whose value has been changed together with the previous and the
	 * new value. Casts may be necessary, because the {@link SBaseChangedEvent}
	 * contains the values as {@link Object} instances only.
	 * 
	 * @param ev
	 */
	public void stateChanged(SBaseChangedEvent ev);
}
