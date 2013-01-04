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
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Species;

/**
 * @author Andreas Dr&auml;ger
 * @since 1.0
 * @version $Rev$
 * @date 09.09.2011
 */
public abstract class VariableReference extends AbstractSBase implements
		SpatialParameterQualifier {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = 1359841900912406174L;
	
	/**
	 * 
	 */
	private String variable;
	
	/**
	 * 
	 */
	public VariableReference() {
		super();
	}
	
	/**
	 * @param level
	 * @param version
	 */
	public VariableReference(int level, int version) {
		super(level, version);
	}

	/**
	 * @param ref
	 */
	public VariableReference(VariableReference ref) {
		super(ref);
		if (ref.isSetVariable()) {
			this.variable = new String(ref.getVariable());
		}
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	public abstract VariableReference clone();
	
	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		boolean equal = super.equals(object);
		if (equal) {
			VariableReference ref = (VariableReference) object;
			equal &= ref.isSetVariable() == isSetVariable();
			if (equal && isSetVariable()) {
				equal &= ref.getVariable().equals(getVariable());
			}
		}
		return equal;
	}
	
	/**
	 * 
	 * @return
	 */
	public Species getSpeciesInstance() {
		Model model = getModel();
		return model != null ? model.getSpecies(getVariable()) : null;
	}

	/**
	 * @return the variable
	 */
	public String getVariable() {
		return isSetVariable() ? variable : "";
	}
	
	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 977;
		int hashCode = super.hashCode();
		if (isSetVariable()) {
			hashCode += prime * getVariable().hashCode();
		}
		return hashCode;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSetVariable() {
		return variable != null;
	}

	/**
	 * @param variable the variable to set
	 */
	public void setVariable(String variable) {
		this.variable = variable;
	}
	
	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#toString()
	 */
	public String toString() {
		return getClass().getSimpleName();
	}
}
