/*
 * $Id:  SpatialSpecies.java 15:13:05 draeger $
 * $URL: SpatialSpecies.java $
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

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.SBase;

/**
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * @version $Rev$
 */
public class SpatialSpecies extends AbstractSBase {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = 1636970127352490380L;

	/**
	 * 
	 */
	private Boolean isSpatial;

	/**
	 * 
	 */
	private String mathOverridden;

	/**
	 * 
	 */
	private Boolean coreHasAlternateMath;
	
	/**
	 * 
	 */
	public SpatialSpecies() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param level
	 * @param version
	 */
	public SpatialSpecies(int level, int version) {
		super(level, version);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param sb
	 */
	public SpatialSpecies(SBase sb) {
		super(sb);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	@Override
	public SpatialSpecies clone() {
		return new SpatialSpecies(this);
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		// TODO Auto-generated method stub
		return super.equals(object);
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
	 */
	@Override
	public TreeNode getChildAt(int childIndex) {
		// TODO Auto-generated method stub
		return super.getChildAt(childIndex);
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getChildCount()
	 */
	@Override
	public int getChildCount() {
		// TODO Auto-generated method stub
		return super.getChildCount();
	}

	/**
	 * @return the coreHasAlternateMath
	 */
	public boolean getCoreHasAlternateMath() {
		return coreHasAlternateMath.booleanValue();
	}

	/**
	 * @return the isSpatial
	 */
	public boolean getIsSpatial() {
		return isSpatial;
	}

	/**
	 * @return the mathOverridden
	 */
	public String getMathOverridden() {
		return isSetMathOverridden() ? mathOverridden : "";
	}
	
	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#hashCode()
	 */
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isSetMathOverridden() {
		return mathOverridden != null;
	}

	/**
	 * @param coreHasAlternateMath the coreHasAlternateMath to set
	 */
	public void setCoreHasAlternateMath(boolean coreHasAlternateMath) {
		this.coreHasAlternateMath = Boolean.valueOf(coreHasAlternateMath);
	}

	/**
	 * @param isSpatial the isSpatial to set
	 */
	public void setIsSpatial(boolean isSpatial) {
		this.isSpatial = Boolean.valueOf(isSpatial);
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
