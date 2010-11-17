/*
 * $Id:  SimpleSBaseChangeListener.java 17:21:54 draeger $
 * $URL: SimpleSBaseChangeListener.java $
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
package org.sbml.jsbml.test;

import org.sbml.jsbml.SBase;
import org.sbml.jsbml.SBaseChangedEvent;
import org.sbml.jsbml.SBaseChangedListener;

/**
 * This very simple implementation of an {@link SBaseChangedListener} writes all
 * the events to the standard out stream.
 * 
 * @author Andreas Dr&auml;ger
 * @date 2010-11-16
 */
public class SimpleSBaseChangeListener implements SBaseChangedListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.SBaseChangedListener#sbaseAdded(org.sbml.jsbml.SBase)
	 */
	public void sbaseAdded(SBase sb) {
		System.out.printf("[ADD]\t%s\n", sb);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jsbml.SBaseChangedListener#sbaseRemoved(org.sbml.jsbml.SBase)
	 */
	public void sbaseRemoved(SBase sb) {
		System.out.printf("[DEL]\t%s\n", sb);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.sbml.jsbml.SBaseChangedListener#stateChanged(org.sbml.jsbml.
	 * SBaseChangedEvent)
	 */
	public void stateChanged(SBaseChangedEvent ev) {
		System.out.printf("[CHG]\t%s\n", ev);
	}

}
