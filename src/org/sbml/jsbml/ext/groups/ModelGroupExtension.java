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

package org.sbml.jsbml.ext.groups;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.util.SBaseChangedListener;

/**
 * 
 * @author 
 * @since 0.8
 * @version $Rev$
 */
public class ModelGroupExtension extends Model {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = 3334444867660252255L;
	/**
	 * 
	 */
	protected ListOf<Group> listOfGroups = new ListOf<Group>();
	/**
	 * 
	 */
	protected Model model;
	
	/**
	 * 
	 */
	public ModelGroupExtension() {
		
	}
	
	/**
	 * 
	 * @param level
	 * @param version
	 */
	public ModelGroupExtension(int level, int version) {
		super(level, version);
	}
	
	/**
	 * 
	 * @param model
	 */
	public ModelGroupExtension(Model model) {
		this.model = model;
		this.model.setThisAsParentSBMLObject(this);
	}
	
	/**
	 * 
	 * @param group
	 */
	public void addGroup(Group group) {
		setThisAsParentSBMLObject(group);
		listOfGroups.add(group);		
	}

	/**
	 * 
	 * @param i
	 * @return
	 */
	public Group getGroup(int i) {
		if (i >= 0 && i < listOfGroups.size()) {
			return listOfGroups.get(i);
		}
		
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public ListOf<Group> getListOfGroups() {
		return listOfGroups;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSetListOfGroups() {
		if ((listOfGroups == null) || listOfGroups.isEmpty()) {
			return false;			
		}		
		return true;
	}

	/**
	 * 
	 * @param listOfGroups
	 */
	public void setListOfGroups(ListOf<Group> listOfGroups) {
		unsetListOfGroups();
		if (listOfGroups == null) {
			this.listOfGroups = new ListOf<Group>();
		} else {
			this.listOfGroups = listOfGroups;
		}
		if ((this.listOfGroups != null) && (this.listOfGroups.getSBaseListType() != ListOf.Type.other)) {
			this.listOfGroups.setSBaseListType(ListOf.Type.other);
		}
		setThisAsParentSBMLObject(listOfGroups);
	}

	/**
	 * Removes the {@link #listOfGroups} from this {@link Model} and notifies
	 * all registered instances of {@link SBaseChangedListener}.
	 * 
	 * @return <code>true</code> if calling this method lead to a change in this
	 *         data structure.
	 */
	public boolean unsetListOfGroups() {
		if (this.listOfGroups != null) {
			ListOf<Group> oldListOfGroups = this.listOfGroups;
			this.listOfGroups = null;
			oldListOfGroups.fireSBaseRemovedEvent();
			return true;
		}
		return false;
	}
		
}
