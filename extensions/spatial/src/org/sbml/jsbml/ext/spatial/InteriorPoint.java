/*
 * $Id:  InteriorPoint.java 16:04:05 draeger $
 * $URL: InteriorPoint.java $
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

import org.sbml.jsbml.AbstractTreeNode;

/**
 * @author Andreas Dr&auml;ger
 * @since 0.8
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
