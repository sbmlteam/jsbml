/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2012 jointly by the following organizations:
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
 * 
 * @author Andreas Dr&auml;ger
 * @version $Rev$
 * @since 1.0
 * @date 09.09.2011
 */
public abstract class Coefficient extends VariableReference {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -7651871640808157489L;

	/**
	 * 
	 */
	private Integer coordinateIndex;

	/**
	 * 
	 */
	private String domain;
	
	/**
	 * 
	 */
	public Coefficient() {
		super();
	}

	/**
	 * @param coefficient
	 */
	public Coefficient(Coefficient coefficient) {
		super(coefficient);
		if (coefficient.isSetCoordinateIndex()) {
			this.coordinateIndex = Integer.valueOf(coefficient.getCoordinateIndex());
		}
		if (coefficient.isSetDomain()) {
			this.domain = new String(coefficient.getDomain());
		}
	}
	
	/**
	 * @param level
	 * @param version
	 */
	public Coefficient(int level, int version) {
		super(level, version);
	}
	
	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	public abstract Coefficient clone();
	
	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		boolean equal = super.equals(object);
		if (equal) {
			Coefficient dc = (Coefficient) object;
			equal &= dc.isSetCoordinateIndex() == isSetCoordinateIndex();
			if (equal && isSetCoordinateIndex()) {
				equal &= dc.getCoordinateIndex() == getCoordinateIndex();
			}
			equal &= dc.isSetDomain() == isSetDomain();
			if (equal && isSetDomain()) {
				equal &= dc.getDomain().equals(getDomain());
			}
		}
		return equal;
	}

	/**
	 * @return the coordinateIndex
	 */
	public int getCoordinateIndex() {
		return coordinateIndex.intValue();
	}

	/**
	 * @return the domain
	 */
	public String getDomain() {
		return isSetDomain() ? domain : "";
	}	

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 967;
		int hashCode = super.hashCode();
		if (isSetCoordinateIndex()) {
			hashCode += prime * getCoordinateIndex();
		}
		if (isSetDomain()) {
			hashCode += prime * getDomain().hashCode();
		}
		return hashCode;
	}
	
	/**
	 * @return
	 */
	public boolean isSetCoordinateIndex() {
		return coordinateIndex != null;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSetDomain() {
		return domain != null;
	}

	/**
	 * @param coordinateIndex the coordinateIndex to set
	 */
	public void setCoordinateIndex(Integer coordinateIndex) {
		this.coordinateIndex = coordinateIndex;
	}

	/**
	 * @param domain the domain to set
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}

}
