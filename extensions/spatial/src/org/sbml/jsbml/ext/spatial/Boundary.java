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

import javax.swing.tree.TreeNode;

/**
 * @author Andreas Dr&auml;ger
 * @since 0.8
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
