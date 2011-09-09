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

import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.ListOf;

/**
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * @version $Rev$
 */
public class Geometry extends AbstractSBase {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = 9115597691155572976L;

	/**
	 * 
	 */
	private ListOf<CoordinateComponent> listOfCoordinateComponents;

	/**
	 * 
	 */
	private ListOf<DomainType> listOfDomainTypes;

	/**
	 * 
	 */
	private ListOf<Domain> listOfDomains;
	
	/**
	 * 
	 */
	private ListOf<AdjacentDomains> listOfAdjacentDomains;

	/**
	 * 
	 */
	private ListOf<GeometryDefinition> listOfGeometryDefinitions;

	/**
	 * 
	 */
	private String coordinateSystem;
	
	/**
	 * 
	 */
	public Geometry() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param sb
	 */
	public Geometry(Geometry sb) {
		super(sb);
		if (sb.isSetCoordinateSystem()) {
			this.coordinateSystem = new String(sb.getCoordinateSystem());
		}
		if (sb.isSetListOfAdjacentDomains()) {
			this.listOfAdjacentDomains = sb.getListOfAdjacentDomains().clone();
		}
		if (sb.isSetListOfDomains()) {
			this.listOfDomains = sb.getListOfDomains().clone();
		}
		if (sb.isSetListOfDomainTypes()) {
			this.listOfDomainTypes = sb.getListOfDomainTypes().clone();
		}
		if (sb.isSetListOfGeometryDefinitions()) {
			this.listOfGeometryDefinitions = sb.getListOfGeometryDefinitions()
					.clone();
		}
	}
	
	/**
	 * @param level
	 * @param version
	 */
	public Geometry(int level, int version) {
		super(level, version);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	@Override
	public Geometry clone() {
		return new Geometry(this);
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
	 * @return the coordinateSystem
	 */
	public String getCoordinateSystem() {
		return isSetCoordinateSystem() ? coordinateSystem : "";
	}

	/**
	 * @return the listOfAdjacentDomains
	 */
	public ListOf<AdjacentDomains> getListOfAdjacentDomains() {
		if (listOfAdjacentDomains == null) {
			listOfAdjacentDomains = ListOf.newInstance(this, AdjacentDomains.class);
		}
		return listOfAdjacentDomains;
	}

	/**
	 * @return the listOfCoordinateComponents
	 */
	public ListOf<CoordinateComponent> getListOfCoordinateComponents() {
		if (listOfCoordinateComponents == null) {
			listOfCoordinateComponents = ListOf.newInstance(this, CoordinateComponent.class);
		}
		return listOfCoordinateComponents;
	}
	
	/**
	 * @return the listOfDomains
	 */
	public ListOf<Domain> getListOfDomains() {
		if (listOfDomains == null) {
			listOfDomains = ListOf.newInstance(this, Domain.class);
		}
		return listOfDomains;
	}

	/**
	 * @return the listOfDomainTypes
	 */
	public ListOf<DomainType> getListOfDomainTypes() {
		if (listOfDomainTypes == null) {
			listOfDomainTypes = ListOf.newInstance(this, DomainType.class);
		}
		return listOfDomainTypes;
	}

	/**
	 * @return the listOfGeometryDefinitions
	 */
	public ListOf<GeometryDefinition> getListOfGeometryDefinitions() {
		if (listOfGeometryDefinitions == null) {
			listOfGeometryDefinitions = ListOf.newInstance(this, GeometryDefinition.class);
		}
		return listOfGeometryDefinitions;
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
	public boolean isSetCoordinateSystem() {
		return coordinateSystem != null;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isSetListOfAdjacentDomains() {
		return (listOfAdjacentDomains != null) && (listOfAdjacentDomains.size() > 0);
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isSetListOfDomains() {
		return (listOfDomains != null) && (listOfDomains.size() > 0);
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isSetListOfDomainTypes() {
		return (listOfDomainTypes != null) && (listOfDomainTypes.size() > 0);
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSetListOfGeometryDefinitions() {
		return (listOfGeometryDefinitions != null) && (listOfGeometryDefinitions.size() > 0);
	}

	/**
	 * @param coordinateSystem the coordinateSystem to set
	 */
	public void setCoordinateSystem(String coordinateSystem) {
		this.coordinateSystem = coordinateSystem;
	}

	/**
	 * @param listOfAdjacentDomains the listOfAdjacentDomains to set
	 */
	public void setListOfAdjacentDomains(
			ListOf<AdjacentDomains> listOfAdjacentDomains) {
		this.listOfAdjacentDomains = listOfAdjacentDomains;
		setThisAsParentSBMLObject(this.listOfAdjacentDomains);
	}
	
	

	/**
	 * @param listOfCoordinateComponents the listOfCoordinateComponents to set
	 */
	public void setListOfCoordinateComponents(
			ListOf<CoordinateComponent> listOfCoordinateComponents) {
		this.listOfCoordinateComponents = listOfCoordinateComponents;
		setThisAsParentSBMLObject(this.listOfCoordinateComponents);
	}

	/**
	 * @param listOfDomains the listOfDomains to set
	 */
	public void setListOfDomains(ListOf<Domain> listOfDomains) {
		this.listOfDomains = listOfDomains;
		setThisAsParentSBMLObject(this.listOfDomains);
	}

	/**
	 * @param listOfDomainTypes the listOfDomainTypes to set
	 */
	public void setListOfDomainTypes(ListOf<DomainType> listOfDomainTypes) {
		this.listOfDomainTypes = listOfDomainTypes;
		setThisAsParentSBMLObject(this.listOfDomainTypes);
	}

	/**
	 * @param listOfGeometryDefinitions the listOfGeometryDefinitions to set
	 */
	public void setListOfGeometryDefinitions(
			ListOf<GeometryDefinition> listOfGeometryDefinitions) {
		this.listOfGeometryDefinitions = listOfGeometryDefinitions;
		setThisAsParentSBMLObject(this.listOfGeometryDefinitions);
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
