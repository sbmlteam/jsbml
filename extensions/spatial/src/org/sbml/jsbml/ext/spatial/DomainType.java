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


/**
 * @author Andreas Dr&auml;ger
 * @since 1.0
 * @version $Rev$
 */
public class DomainType extends NamedSpatialElement {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -1429902642364213170L;

	/**
	 * 
	 */
	private Short spatialDimension;

	/**
	 * 
	 */
	public DomainType() {
		super();
	}

	/**
	 * @param sb
	 */
	public DomainType(DomainType sb) {
		super(sb);
		if (sb.isSetSpatialDimension()) {
			this.spatialDimension = Short.valueOf(sb.getSpatialDimension());
		}
	}
	
	/**
	 * @param level
	 * @param version
	 */
	public DomainType(int level, int version) {
		super(level, version);
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	@Override
	public DomainType clone() {
		return new DomainType(this);
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		boolean equal = super.equals(object);
		if (equal) {
			DomainType dt = (DomainType) object;
			equal &= dt.isSetSpatialDimension() == isSetSpatialDimension();
			if (equal && isSetSpatialDimension()) {
				equal &= dt.getSpatialDimension() == getSpatialDimension();
			}
		}
		return equal;
	}

	/**
	 * @return the spatialDimension
	 */
	public short getSpatialDimension() {
		return spatialDimension.shortValue();
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 937;
		int hashCode = super.hashCode();
		if (isSetSpatialDimension()) {
			hashCode += prime * getSpatialDimension();
		}
		return hashCode;
	}
	
	/**
	 * @return
	 */
	public boolean isSetSpatialDimension() {
		return spatialDimension != null;
	}

	/**
	 * @param spatialDimension the spatialDimension to set
	 */
	public void setSpatialDimension(short spatialDimension) {
		if ((spatialDimension < 0) || (3 < spatialDimension)) {
			throw new IllegalArgumentException(
					"Spatial dimension must be selected from the set {0, 1, 2, 3}");
		}
		this.spatialDimension = Short.valueOf(spatialDimension);
	}

}
