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

package org.sbml.jsbml.ext.layout;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.util.TreeNodeChangeListener;

/**
 * 
 * @author Nicolas Rodriguez
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * @version $Rev$
 */
public class ExtendedLayoutModel extends AbstractSBase {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -6666014348571697514L;
	/**
	 * 
	 */
	protected ListOf<Layout> listOfLayouts;

	/**
	 * 
	 */
	public ExtendedLayoutModel() {
		super();
		listOfLayouts = new ListOf<Layout>();
	}

	/**
	 * 
	 * @param elm
	 */
	public ExtendedLayoutModel(ExtendedLayoutModel elm) {
		super(elm);
		// We don't clone the pointer to the containing model.
		if (elm.listOfLayouts != null) {
			this.listOfLayouts = elm.listOfLayouts.clone();
		}
	}

	/**
	 * 
	 * @param level
	 * @param version
	 */
	public ExtendedLayoutModel(int level, int version) {
		// TODO : add package version as well
		super(level, version);
		listOfLayouts = new ListOf<Layout>();
	}

	/**
	 * 
	 * @param layout
	 */
	public void add(Layout layout) {
		addLayout(layout);
	}

	/**
	 * 
	 * @param layout
	 */
	public void addLayout(Layout layout) {
		if (layout != null) {
			listOfLayouts.add(layout);
		}
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.Model#clone()
	 */
	@Override
	public ExtendedLayoutModel clone() {
		return new ExtendedLayoutModel(this);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Model#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		boolean equals = super.equals(object);
		if (equals) {
			// ExtendedLayoutModel elm = (ExtendedLayoutModel) object;
			// An equals call on the model would cause a cyclic check!
			// Actually, I'm not sure if we should compare the model
			// here at all because this would be like checking a pointer
			// to the parent node in the SBML tree, which we never do.
			// Therefore, there's also no hashCode method here, because
			// nothing to check, in my opinion.
			// Hence, we can delete this method here.
			// equals &= getModel() == elm.getModel();
		}
		return equals;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Model#getChildAt(int)
	 */
	@Override
	public TreeNode getChildAt(int index) {
		if (isSetListOfLayouts() && (index == getChildCount() - 1)) {
			return getListOfLayouts();
		}
		return super.getChildAt(index);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Model#getChildCount()
	 */
	@Override
	public int getChildCount() {
		int count = super.getChildCount();
		if (isSetListOfLayouts()) {
			count++;
		}
		return count;
	}
	
	/**
	 * 
	 * @param i
	 * @return
	 */
	public Layout getLayout(int i) {
		return listOfLayouts.get(i);
	}
	
	/**
	 * 
	 * @return
	 */
	public ListOf<Layout> getListOfLayouts() {
		return listOfLayouts;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getParent()
	 */
	@Override
	public Model getParent() {
		return (Model) super.getParent();
	}
	
	/* (non-Javadoc)
	 * @see org.sbml.jsbml.Model#getParentSBMLObject()
	 */
	@Override
	public Model getParentSBMLObject() {
		return (Model) super.getParentSBMLObject();
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isSetListOfLayouts() {
		return ((listOfLayouts == null) || listOfLayouts.isEmpty()) ? false
				: true;
	}

	/**
	 * 
	 * @param listOfLayouts
	 */
	public void setListOfLayouts(ListOf<Layout> listOfLayouts) {
		unsetListOfLayouts();
		if (listOfLayouts == null) {
			this.listOfLayouts = new ListOf<Layout>();
		} else {
			this.listOfLayouts = listOfLayouts;
		}
		if ((this.listOfLayouts != null) && (this.listOfLayouts.getSBaseListType() != ListOf.Type.other)) {
			this.listOfLayouts.setSBaseListType(ListOf.Type.other);
		}
		setThisAsParentSBMLObject(listOfLayouts);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Removes the {@link #listOfLayouts} from this {@link Model} and notifies
	 * all registered instances of {@link TreeNodeChangeListener}.
	 * 
	 * @return <code>true</code> if calling this method lead to a change in this
	 *         data structure.
	 */
	public boolean unsetListOfLayouts() {
		if (this.listOfLayouts != null) {
			ListOf<Layout> oldListOfLayouts = this.listOfLayouts;
			this.listOfLayouts = null;
			oldListOfLayouts.fireNodeRemovedEvent();
			return true;
		}
		return false;
	}	

}
