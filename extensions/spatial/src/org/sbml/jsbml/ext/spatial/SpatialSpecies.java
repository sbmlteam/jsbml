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
package org.sbml.jsbml.ext.spatial;


/**
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * @version $Rev$
 */
public class SpatialSpecies extends SpatialCallableSBase {

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
	public SpatialSpecies(SpatialSpecies sb) {
		super(sb);
		if (sb.isSetIsSpatial()) {
			this.isSpatial = Boolean.valueOf(sb.isSpatial());
		}
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
		boolean equal = super.equals(object);
		if (equal) {
			SpatialSpecies ss = (SpatialSpecies) object;
			equal &= ss.isSetIsSpatial() == isSetIsSpatial();
			if (equal && isSetIsSpatial()) {
				equal &= ss.isSpatial() == isSpatial();
			}
		}
		return equal;
	}

	/**
	 * @return the isSpatial
	 */
	public boolean getIsSpatial() {
		return isSpatial;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 997;
		int hashCode = super.hashCode();
		if (isSetIsSpatial()) {
			hashCode += prime * isSpatial.hashCode();
		}
		return hashCode;
	}
	
	/**
	 * @return
	 */
	public boolean isSetIsSpatial() {
		return isSpatial != null;
	}

	/**
	 * 
	 * @return
	 * @see #getIsSpatial()
	 */
	public boolean isSpatial() {
		return getIsSpatial();
	}
	
	/**
	 * @param isSpatial the isSpatial to set
	 */
	public void setIsSpatial(boolean isSpatial) {
		this.isSpatial = Boolean.valueOf(isSpatial);
	}

}
