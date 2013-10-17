/* 
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2013 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.spatial;

import org.sbml.jsbml.AbstractSBase;

/**
 * @author Andreas Dr&auml;ger
 * @since 1.0
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
