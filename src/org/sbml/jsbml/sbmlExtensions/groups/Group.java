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

package org.sbml.jsbml.sbmlExtensions.groups;

import java.util.HashMap;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.SBase;

public class Group extends AbstractNamedSBase {

	protected ListOf<Member> listOfMembers = new ListOf<Member>(); 


	public Group() {
		
	}
	
	public Group(int level, int version) {
		super(level, version);
	}
	
	public Group(Group group) {
		// TODO
	}

	public ListOf<Member> getListOfMembers() {
		return listOfMembers;
	}

	public void setListOfMembers(ListOf<Member> listOfMembers) {
		this.listOfMembers = listOfMembers;
	}

	public boolean isSetListOfMembers() {
		if (listOfMembers == null || listOfMembers.isEmpty()) {
			return false;			
		}
		
		return true;
	}
		
	@Override
	public SBase clone() {
		return new Group(this);
	}

	@Override
	public String toString() {
		// TODO 
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#readAttribute(String attributeName,
	 * String prefix, String value)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix,
			String value) {
		boolean isAttributeRead = super.readAttribute(attributeName, prefix,
				value);
		
		return isAttributeRead;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#writeXMLAttributes()
	 */
	@Override
	public HashMap<String, String> writeXMLAttributes() {
		HashMap<String, String> attributes = super.writeXMLAttributes();

		return attributes;
	}

	public Member getMember(int i) {
		if (i >= 0 && i < listOfMembers.size()) {
			return listOfMembers.get(i);
		}
		
		return null;
	}
	
}
