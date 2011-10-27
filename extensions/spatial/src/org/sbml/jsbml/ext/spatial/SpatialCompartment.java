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

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.SBase;

/**
 * 
 * @author Andreas Dr&auml;ger
 * @since 1.0
 * @version $Rev$
 */
public class SpatialCompartment extends SpatialCallableSBase {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = 1363097365327594433L;

	/**
	 * 
	 */
	private ListOf<CompartmentMapping> listOfCompartmentMappings;
	
	/**
	 * 
	 */
	public SpatialCompartment() {
		super();
	}
	
	/**
	 * @param level
	 * @param version
	 */
	public SpatialCompartment(int level, int version) {
		super(level, version);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param sb
	 */
	public SpatialCompartment(SBase sb) {
		super(sb);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param cm
	 * @return
	 */
	public boolean addCompartmentMapping(CompartmentMapping cm) {
		return getListOfCompartmentMappings().add(cm);
	}
	
	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	@Override
	public SpatialCompartment clone() {
		return new SpatialCompartment(this);
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
		if (childIndex < 0) {
			throw new IndexOutOfBoundsException(childIndex + " < 0");
		}
		int pos = 0;
		if (isSetListOfCompartmentMappings()) {
			if (childIndex == pos)  {
				return getListOfCompartmentMappings();
			}
			pos++;
		}
		throw new IndexOutOfBoundsException(isLeaf() ? String
				.format("Node %s has no children.", getElementName())
				: String.format("Index %d >= %d", childIndex, +((int) Math.min(
						pos, 0))));
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getChildCount()
	 */
	@Override
	public int getChildCount() {
		int childCount = super.getChildCount();
		if (isSetListOfCompartmentMappings()) {
			childCount++;
		}
		return childCount;
	}

	/**
	 * @return the listOfCompartmentMappings
	 */
	public ListOf<CompartmentMapping> getListOfCompartmentMappings() {
		if (listOfCompartmentMappings == null) {
			listOfCompartmentMappings = ListOf.newInstance(this,
					CompartmentMapping.class);
		}
		return listOfCompartmentMappings;
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
	public boolean isSetListOfCompartmentMappings() {
		return (listOfCompartmentMappings != null)
				&& (listOfCompartmentMappings.size() > 0);
	}

	/**
	 * 
	 * @param cm
	 * @return
	 */
	public boolean removeCompartmentMapping(CompartmentMapping cm) {
		return getListOfCompartmentMappings().remove(cm);
	}

	/**
	 * @param listOfCompartmentMappings the listOfCompartmentMappings to set
	 */
	public void setListOfCompartmentMappings(
			ListOf<CompartmentMapping> listOfCompartmentMappings) {
		this.listOfCompartmentMappings = listOfCompartmentMappings;
		setThisAsParentSBMLObject(listOfCompartmentMappings);
	}
}
