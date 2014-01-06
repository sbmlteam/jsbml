/* 
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2014 jointly by the following organizations:
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

import javax.swing.tree.TreeNode;

/**
 * @author Andreas Dr&auml;ger
 * @since 1.0
 * @version $Rev$
 */
public class Boundary extends NamedSpatialElement {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -5283759970799753982L;

	/**
	 * 
	 */
	private Double value;
	
	/**
	 * 
	 */
	public Boundary() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param boundary
	 */
	public Boundary(Boundary boundary) {
		// TODO Auto-generated constructor stub
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractTreeNode#clone()
	 */
	public Boundary clone() {
		return new Boundary(this);
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		// TODO Auto-generated method stub
		return super.equals(object);
	}

	public boolean getAllowsChildren() {
		// TODO Auto-generated method stub
		return false;
	}

	public TreeNode getChildAt(int childIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getChildCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @return the value
	 */
	public double getValue() {
		return isSetValue() ? value.doubleValue() : Double.NaN;
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
	public boolean isSetValue() {
		return value != null;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(double value) {
		this.value = Double.valueOf(value);
	}

}
