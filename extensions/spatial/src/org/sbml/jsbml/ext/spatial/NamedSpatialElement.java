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

import org.sbml.jsbml.AbstractSBase;

/**
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * @version $Rev$
 */
public abstract class NamedSpatialElement extends AbstractSBase {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -1814806955800042477L;

	/**
	 * 
	 */
	String spatialId;
	
	/**
	 * 
	 */
	public NamedSpatialElement() {
		super();
	}

	/**
	 * @param level
	 * @param version
	 */
	public NamedSpatialElement(int level, int version) {
		super(level, version);
	}

	/**
	 * @param nse
	 */
	public NamedSpatialElement(NamedSpatialElement nse) {
		super(nse);
		if (nse.isSetSpatialId()) {
			this.spatialId = new String(nse.getSpatialId());
		}
	}
	
	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		boolean equal = super.equals(object);
		if (equal) {
			NamedSpatialElement nse = (NamedSpatialElement) object;
			equal &= nse.isSetSpatialId() == isSetSpatialId();
			if (equal && isSetSpatialId()) {
				equal &= nse.getSpatialId().equals(getSpatialId());
			}
		}
		return equal;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 983;
		int hashCode = super.hashCode();
		if (isSetSpatialId()) {
			hashCode += prime * getSpatialId().hashCode();
		}
		return hashCode;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	@Override
	public abstract NamedSpatialElement clone();

	/**
	 * @return the spatialId
	 */
	public String getSpatialId() {
		return spatialId;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSetSpatialId() {
		return spatialId != null;
	}

	/**
	 * @param spatialId the spatialId to set
	 */
	public void setSpatialId(String spatialId) {
		this.spatialId = spatialId;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#toString()
	 */
	public String toString() {
		return getClass().getSimpleName();
	}

}
