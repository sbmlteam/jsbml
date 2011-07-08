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

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBaseChangedListener;

/**
 * 
 * @author Nicolas Rodriguez
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * @version $Rev$
 */
public class ExtendedLayoutModel extends Model {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -6666014348571697514L;
	/**
	 * 
	 */
	protected ListOf<Layout> listOfLayouts = new ListOf<Layout>();
	/**
	 * 
	 */
	protected Model model;

	/**
	 * 
	 */
	public ExtendedLayoutModel() {
		super();
	}

	/**
	 * 
	 * @param level
	 * @param version
	 */
	public ExtendedLayoutModel(int level, int version) {
		// TODO : add package version as well
		super(level, version);
	}

	/**
	 * 
	 * @param model
	 */
	public ExtendedLayoutModel(Model model) {
		this.model = model;
		this.model.setThisAsParentSBMLObject(this);
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
			setThisAsParentSBMLObject(layout);
			listOfLayouts.add(layout);
		}
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
	
	/**
	 * Removes the {@link #listOfLayouts} from this {@link Model} and notifies
	 * all registered instances of {@link SBaseChangedListener}.
	 * 
	 * @return <code>true</code> if calling this method lead to a change in this
	 *         data structure.
	 */
	public boolean unsetListOfLayouts() {
		if (this.listOfLayouts != null) {
			ListOf<Layout> oldListOfLayouts = this.listOfLayouts;
			this.listOfLayouts = null;
			oldListOfLayouts.fireSBaseRemovedEvent();
			return true;
		}
		return false;
	}

}
