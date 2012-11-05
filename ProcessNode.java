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
 * @author Jakob Matthes
 * @version $Rev$
 */
public abstract class ProcessNode<T> implements SBGNNode<T> {
	
	// process nodes do not have a clone marker
	private final boolean cloneMarker = false;
	
	/* (non-Javadoc)
	 * @see de.zbit.sbml.layout.SBGNNode#setCloneMarker()
	 */
	public void setCloneMarker() {
		
	}
	
	/* (non-Javadoc)
	 * @see de.zbit.sbml.layout.SBGNNode#isSetCloneMarker()
	 */
	public boolean isSetCloneMarker() {
		return cloneMarker;
	}
	
}