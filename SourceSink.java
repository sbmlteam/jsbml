/*
 * $Id$
 * $URL$
 * ---------------------------------------------------------------------
 * This file is part of the SysBio API library.
 *
 * Copyright (C) 2009-2012 by the University of Tuebingen, Germany.
 *
 * This library is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation. A copy of the license
 * agreement is provided in the file named "LICENSE.txt" included with
 * this software distribution and also available online as
 * <http://www.gnu.org/licenses/lgpl-3.0-standalone.html>.
 * ---------------------------------------------------------------------
 */

package de.zbit.sbml.layout;

/**
 * interface all the different graphical representations for the empty set sign
 * have to implement
 * 
 * @author Mirjam Gutekunst
 * @version $Rev$
 */
public abstract class SourceSink<T> implements SBGNNode<T> {
	
	//final because source or sink never have clone markers	
	private final boolean cloneMarker = false;
	
	/* (non-Javadoc)
	 * @see de.zbit.sbml.layout.SBGNNode#setCloneMarker()
	 */
	//does nothing
	public void setCloneMarker(){}
	
	/* (non-Javadoc)
	 * @see de.zbit.sbml.layout.SBGNNode#isSetCloneMarker()
	 */
	public boolean isSetCloneMarker() {
		return cloneMarker;
	}
	
}
