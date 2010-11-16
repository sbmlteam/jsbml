/*
 * $Id$
 * $URL$
 *
 *
 *==================================================================================
 * Copyright (c) 2009 the copyright is held jointly by the individual
 * authors. See the file AUTHORS for the list of authors.
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

package org.sbml.jsbml.ext.groups;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;

/**
 * 
 * 
 *
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
		model.setThisAsParentSBMLObject(this);
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
		if (listOfGroups == null) {
			this.listOfGroups = new ListOf<Group>();
		} else {
			this.listOfGroups = listOfGroups;
		}
		setThisAsParentSBMLObject(listOfGroups);
	}
		
}
