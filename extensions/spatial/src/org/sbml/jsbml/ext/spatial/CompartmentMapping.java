/*
 * $Id:  CompartmentMapping.java 15:11:29 draeger $
 * $URL: CompartmentMapping.java $
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

import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.SBase;

/**
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * @version $Rev$
 */
public class CompartmentMapping extends NamedSpatialElement {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -4623759168043277022L;

	/**
	 * 
	 */
	private String compartment;

	/**
	 * 
	 */
	private String domainType;

	/**
	 * 
	 */
	private Double unitSize;
	
	/**
	 * 
	 */
	public CompartmentMapping() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param level
	 * @param version
	 */
	public CompartmentMapping(int level, int version) {
		super(level, version);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param sb
	 */
	public CompartmentMapping(SBase sb) {
		super(sb);
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	@Override
	public CompartmentMapping clone() {
		return new CompartmentMapping(this);
	}
	
	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		// TODO Auto-generated method stub
		return super.equals(object);
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
	 * @return the compartment
	 */
	public String getCompartment() {
		return isSetCompartment() ? compartment : "";
	}

	/**
	 * 
	 * @return
	 */
	public Compartment getCompartmentInstance() {
		return getModel().getCompartment(getCompartment());
	}
	
	/**
	 * @return the domainType
	 */
	public String getDomainType() {
		return domainType;
	}

	/**
	 * @return the unitSize
	 */
	public double getUnitSize() {
		return isSetUnitSize() ? unitSize.doubleValue() : Double.NaN;
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
	public boolean isSetCompartment() {
		return compartment != null;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSetUnitSize() {
		return (unitSize == null) || (Double.isNaN(unitSize.doubleValue()));
	}

	/**
	 * @param compartment the compartment to set
	 */
	public void setCompartment(String compartment) {
		this.compartment = compartment;
	}

	/**
	 * @param domainType the domainType to set
	 */
	public void setDomainType(String domainType) {
		this.domainType = domainType;
	}

	/**
	 * @param unitSize the unitSize to set
	 */
	public void setUnitSize(Double unitSize) {
		this.unitSize = unitSize;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
