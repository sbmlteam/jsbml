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

package org.sbml.jsbml.ext.layout;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;

public class ExtendedLayoutModel extends Model {

	protected ListOf<Layout> listOfLayouts = new ListOf<Layout>();
	protected Model model;
	
	public ExtendedLayoutModel() {
		
	}
	
	public ExtendedLayoutModel(int level, int version) {
		// TODO : add package version as well
		super(level, version);
	}
	
	public ExtendedLayoutModel(Model model) {
		this.model = model;
		model.setThisAsParentSBMLObject(this);
	}
	

	public ListOf<Layout> getListOfLayouts() {
		return listOfLayouts;
	}

	public void setListOfLayouts(ListOf<Layout> listOfLayouts) {
		if (listOfLayouts == null) {
			this.listOfLayouts = new ListOf<Layout>();
		} else {
			this.listOfLayouts = listOfLayouts;
		}
		setThisAsParentSBMLObject(listOfLayouts);
	}

	public boolean isSetListOfLayouts() {
		if (listOfLayouts == null || listOfLayouts.isEmpty()) {
			return false;			
		}
		
		return true;
	}

	public Layout getLayout(int i) {
		if (i >= 0 && i < listOfLayouts.size()) {
			return listOfLayouts.get(i);
		}
		
		return null;
	}

	public void addLayout(Layout layout) {
		if (layout != null) {
			setThisAsParentSBMLObject(layout);
			listOfLayouts.add(layout);
		}
	}
	
	public void add(Layout layout) {
		addLayout(layout);
	}
		
}
