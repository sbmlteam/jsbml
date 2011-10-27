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

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.AbstractTreeNode;

/**
 * @author Andreas Dr&auml;ger
 * @since 1.0
 * @version $Rev$
 */
public class InteriorPoint extends AbstractTreeNode {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = 5525511951071345435L;

	/**
	 * 
	 */
	private Double coord1;
	
	/**
	 * 
	 */
	private Double coord2;

	/**
	 * 
	 */
	private Double coord3;

	/**
	 * 
	 */
	public InteriorPoint() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param interiorPoint
	 */
	public InteriorPoint(InteriorPoint interiorPoint) {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractTreeNode#clone()
	 */
	public InteriorPoint clone() {
		return new InteriorPoint(this);
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
	 * @return the coord1
	 */
	public double getCoord1() {
		return isSetCoord1() ? coord1 : Double.NaN;
	}

	/**
	 * @return the coord2
	 */
	public double getCoord2() {
		return isSetCoord2() ? coord2 : Double.NaN;
	}
	
	/**
	 * @return the coord3
	 */
	public double getCoord3() {
		return isSetCoord3() ? coord3 : Double.NaN;
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
	public boolean isSetCoord1() {
		return (coord1 != null) && !Double.isNaN(coord1.doubleValue());
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSetCoord2() {
		return (coord2 != null) && !Double.isNaN(coord2.doubleValue());
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSetCoord3() {
		return (coord3 != null) && !Double.isNaN(coord3.doubleValue());
	}

	/**
	 * @param coord1 the coord1 to set
	 */
	public void setCoord1(double coord1) {
		this.coord1 = Double.valueOf(coord1);
	}

	/**
	 * @param coord2 the coord2 to set
	 */
	public void setCoord2(double coord2) {
		this.coord2 = Double.valueOf(coord2);
	}

	/**
	 * @param coord3 the coord3 to set
	 */
	public void setCoord3(double coord3) {
		this.coord3 = Double.valueOf(coord3);
	}

}
