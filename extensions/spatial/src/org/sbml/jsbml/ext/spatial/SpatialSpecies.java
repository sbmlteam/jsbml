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
package org.sbml.jsbml.ext.spatial;


/**
 * @author Andreas Dr&auml;ger
 * @since 1.0
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
