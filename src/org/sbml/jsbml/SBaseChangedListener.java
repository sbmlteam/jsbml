/*
 * $Id$
 * $URL$
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

import java.util.EventListener;

/**
 * A listener interface that allows applications to get notified if the state of
 * any SBase object changes.
 * 
 * @author Andreas Dr&auml;ger
 */
public interface SBaseChangedListener extends EventListener {

	/**
	 * 
	 * @param sb
	 */
	void sbaseAdded(SBase sb);

	/**
	 * 
	 * @param sb
	 */
	void sbaseRemoved(SBase sb);

	/**
	 * 
	 * @param ev
	 */
	void stateChanged(SBaseChangedEvent ev);
}
