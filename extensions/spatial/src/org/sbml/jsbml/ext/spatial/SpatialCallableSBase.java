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
import org.sbml.jsbml.SBase;

/**
 * @author Andreas Dr&auml;ger
 * @since 1.0
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
	Boolean coreHasAlternateMath;

	/**
	 * 
	 */
	String mathOverridden;

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
	
	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		boolean equals = super.equals(object);
		if (equals) {
			SpatialCallableSBase sb = (SpatialCallableSBase) object;
			equals &= sb.isSetMathOverridden() == isSetMathOverridden();
			if (equals && isSetMathOverridden()) {
				equals &= sb.getMathOverridden().equals(getMathOverridden());
			}
			equals &= sb.isSetCoreHasAlternateMath() == isSetCoreHasAlternateMath();
			if (equals && isSetCoreHasAlternateMath()) {
				equals &= sb.getCoreHasAlternateMath() == getCoreHasAlternateMath();
			}
		}
		return equals;
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

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 997;
		int hashCode = super.hashCode();
		if (isSetMathOverridden()) {
			hashCode += prime * mathOverridden.hashCode();
		}
		if (isSetCoreHasAlternateMath()) {
			hashCode += prime * coreHasAlternateMath.hashCode();
		}
		return hashCode;
	}
	
	/**
	 * @return
	 */
	public boolean isSetCoreHasAlternateMath() {
		return coreHasAlternateMath != null;
	}

	/**
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
	 * @param mathOverridden the mathOverridden to set
	 */
	public void setMathOverridden(String mathOverridden) {
		this.mathOverridden = mathOverridden;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#toString()
	 */
	public String toString() {
		return getClass().getName();
	}

}
