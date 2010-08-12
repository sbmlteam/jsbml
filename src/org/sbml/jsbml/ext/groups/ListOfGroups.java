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
import org.sbml.jsbml.SBase;

/**
 * This class represents the listOf extension for the group package
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * 
 */

public class ListOfGroups<T extends SBase> extends ListOf<T>{

	/**
	 * 
	 * @return
	 */
    public GroupList getCurrentList() {
        return listType;
    }

    /**
     * name of the list at it appears in the SBMLFile.
     */
    private GroupList listType = GroupList.none;

    /*
      * (non-Javadoc)
      *
      * @see java.lang.Object#equals(java.lang.Object)
      */
    @Override
    public boolean equals(Object o) {
        if (o instanceof ListOfGroups<?>) {
            boolean equals = super.equals(o);
            // TODO : test the type of list
            ListOfGroups<SBase> listOf = (ListOfGroups<SBase>) o;
            equals &= getCurrentList() == listOf.getCurrentList();
            return listOf.containsAll(this) && equals;
        }
        return false;
    }

    /*
	 * (non-Javadoc)
	 *
	 * @see org.sbml.jlibsbml.SBase#getAnnotationString()
	 */
	public String getElementName() {
		String name = getCurrentList().toString();
		return name;
	}

   /**
	 * Sets the SBaseListType of this ListOf instance to 'listType'.
	 *
	 * @param listType
	 */
	public void setCurrentList(GroupList currentList) {
		this.listType = currentList;
		stateChanged();
	}

}
