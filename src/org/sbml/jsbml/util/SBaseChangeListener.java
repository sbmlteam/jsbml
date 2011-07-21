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

package org.sbml.jsbml.util;

import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBase;

/**
 * A listener interface that allows applications to get notified if the state of
 * any {@link SBase} object changes.
 * 
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * @version $Rev$
 */
public interface SBaseChangeListener extends ChangeListener<SBaseChangeEvent> {

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
}
