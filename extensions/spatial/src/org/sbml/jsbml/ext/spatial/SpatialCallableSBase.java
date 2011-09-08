/*
 * $Id:  SpatialCallableSBase.java 14:31:08 draeger $
 * $URL: SpatialCallableSBase.java $
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
package org.sbml.jsbml.ext.spatial;

import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.SBase;

/**
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * @version $Rev$
 */
public abstract class SpatialCallableSBase extends AbstractSBase {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -942837770161862224L;

	/**
	 * 
	 */
	String mathOverridden;

	/**
	 * 
	 */
	Boolean coreHasAlternateMath;
	
	/**
	 * 
	 */
	public SpatialCallableSBase() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param level
	 * @param version
	 */
	public SpatialCallableSBase(int level, int version) {
		super(level, version);
		// TODO Auto-generated constructor stub
	}
	

	/**
	 * @param sb
	 */
	public SpatialCallableSBase(SBase sb) {
		super(sb);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	@Override
	public AbstractSBase clone() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @return the coreHasAlternateMath
	 */
	public boolean getCoreHasAlternateMath() {
		return coreHasAlternateMath.booleanValue();
	}

	/**
	 * @return the mathOverridden
	 */
	public String getMathOverridden() {
		return mathOverridden;
	}

	/**
	 * @param coreHasAlternateMath the coreHasAlternateMath to set
	 */
	public void setCoreHasAlternateMath(boolean coreHasAlternateMath) {
		this.coreHasAlternateMath = Boolean.valueOf(coreHasAlternateMath);
	}

	/**
	 * @param mathOverridden the mathOverridden to set
	 */
	public void setMathOverridden(String mathOverridden) {
		this.mathOverridden = mathOverridden;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
