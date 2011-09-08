/*
 * $Id:  BoundaryCondition.java 15:19:27 draeger $
 * $URL: BoundaryCondition.java $
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

import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.SBase;

/**
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * @version $Rev$
 */
public class BoundaryCondition extends AbstractSBase {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -6986768113234565819L;

	/**
	 * 
	 */
	private String variable;

	/**
	 * 
	 */
	private String type;

	/**
	 * 
	 */
	private String coordinateBoundary;
	
	/**
	 * 
	 */
	private String boundaryDomainType;

	/**
	 * 
	 */
	public BoundaryCondition() {
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
	
	/**
	 * @param sb
	 */
	public BoundaryCondition(SBase sb) {
		super(sb);
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	@Override
	public BoundaryCondition clone() {
		return new BoundaryCondition(this);
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		// TODO Auto-generated method stub
		return super.equals(object);
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
	public String getType() {
		return isSetType() ? type : "";
	}

	/**
	 * @return the variable
	 */
	public String getVariable() {
		return variable;
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
	public void setType(String type) {
		this.type = type;
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
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
