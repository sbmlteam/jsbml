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

import javax.swing.tree.TreeNode;

/**
 * @author Andreas Dr&auml;ger
 * @since 1.0
 * @version $Rev$
 */
public class BoundaryCondition extends VariableReference {

	/**
	 * 
	 * @author Andreas Dr&auml;ger
	 * @version $Rev$
	 * @since 0.8
	 */
	public static enum Type {
		/**
		 * Neumann
		 */
		FLUX,
		/**
		 * Dirichlet
		 */
		VALUE;
	}
	
	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -6986768113234565819L;

	/**
	 * 
	 */
	private String boundaryDomainType;

	/**
	 * 
	 */
	private String coordinateBoundary;
	
	/**
	 * 
	 */
	private Type type;

	/**
	 * 
	 */
	public BoundaryCondition() {
		super();
	}
	
	/**
	 * @param sb
	 */
	public BoundaryCondition(BoundaryCondition sb) {
		super(sb);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param level
	 * @param version
	 */
	public BoundaryCondition(int level, int version) {
		super(level, version);
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	public BoundaryCondition clone() {
		return new BoundaryCondition(this);
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		boolean equal = super.equals(object);
		if (equal) {
			BoundaryCondition bc = (BoundaryCondition) object;
			equal &= bc.isSetBoundaryDomainType() == isSetBoundaryDomainType();
			if (equal && isSetBoundaryDomainType()) {
				equal &= bc.getBoundaryDomainType().equals(getBoundaryDomainType());
			}
			equal &= bc.isSetCoordinateBoundary() == isSetCoordinateBoundary();
			if (equal && isSetCoordinateBoundary()) {
				equal &= bc.getCoordinateBoundary().equals(getCoordinateBoundary());
			}
			equal &= bc.isSetType() == isSetType();
			if (equal && isSetType()) {
				equal &= bc.getType().equals(getType());
			}
		}
		return equal;
	}

	/**
	 * @return the boundaryDomainType
	 */
	public String getBoundaryDomainType() {
		return isSetBoundaryDomainType() ? boundaryDomainType : "";
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
	 * @return the coordinateBoundary
	 */
	public String getCoordinateBoundary() {
		return isSetCoordinateBoundary() ? coordinateBoundary : "";
	}

	/**
	 * @return the type
	 */
	public Type getType() {
		return type;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 971;
		int hashCode = super.hashCode();
		if (isSetBoundaryDomainType()) {
			hashCode += prime * getBoundaryDomainType().hashCode();
		}
		if (isSetCoordinateBoundary()) {
			hashCode += prime * getCoordinateBoundary().hashCode();
		}
		if (isSetType()) {
			hashCode += prime * getType().hashCode();
		}
		return hashCode;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSetBoundaryDomainType() {
		return boundaryDomainType != null;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSetCoordinateBoundary() {
		return coordinateBoundary != null;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSetType() {
		return type != null;
	}
	
	/**
	 * @param boundaryDomainType the boundaryDomainType to set
	 */
	public void setBoundaryDomainType(String boundaryDomainType) {
		this.boundaryDomainType = boundaryDomainType;
	}

	/**
	 * @param coordinateBoundary the coordinateBoundary to set
	 */
	public void setCoordinateBoundary(String coordinateBoundary) {
		this.coordinateBoundary = coordinateBoundary;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Type type) {
		this.type = type;
	}

}
