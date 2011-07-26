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

import java.util.Map;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.util.SBaseChangeListener;

/**
 * 
 * 
 * @since 0.8
 * @version $Rev$
 */
public class Group extends AbstractNamedSBase {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = 2361503116934849753L;
	/**
	 * 
	 */
	protected ListOf<Member> listOfMembers = new ListOf<Member>(); 

	/**
	 * 
	 */
	public Group() {
		super();
	}
	
	/**
	 * 
	 * @param group
	 */
	public Group(Group group) {
		super(group);
		for (Member m : group.listOfMembers) {
			listOfMembers.add(m.clone());
		}
	}
	
	/**
	 * 
	 * @param level
	 * @param version
	 */
	public Group(int level, int version) {
		super(level, version);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	public Group clone() {
		return new Group(this);
	}

	/**
	 * 
	 * @return
	 */
	public ListOf<Member> getListOfMembers() {
		return listOfMembers;
	}

	/**
	 * 
	 * @param i
	 * @return
	 */
	public Member getMember(int i) {
		if (i >= 0 && i < listOfMembers.size()) {
			return listOfMembers.get(i);
		}
		
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isSetListOfMembers() {
		if ((listOfMembers == null) || listOfMembers.isEmpty()) {
			return false;			
		}
		return true;
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

	/**
	 * 
	 * @param listOfMembers
	 */
	public void setListOfMembers(ListOf<Member> listOfMembers) {
		unsetListOfMembers();
		this.listOfMembers = listOfMembers;
		if ((this.listOfMembers != null) && (this.listOfMembers.getSBaseListType() != ListOf.Type.other)) {
			this.listOfMembers.setSBaseListType(ListOf.Type.other);
		}
		setThisAsParentSBMLObject(this.listOfMembers);
	}

	/**
	 * Removes the {@link #listOfMembers} from this {@link Model} and notifies
	 * all registered instances of {@link SBaseChangeListener}.
	 * 
	 * @return <code>true</code> if calling this method lead to a change in this
	 *         data structure.
	 */
	public boolean unsetListOfMembers() {
		if (this.listOfMembers != null) {
			ListOf<Member> oldListOfMembers = this.listOfMembers;
			this.listOfMembers = null;
			oldListOfMembers.fireSBaseRemovedEvent();
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#toString()
	 */
	@Override
	public String toString() {
		// TODO 
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#writeXMLAttributes()
	 */
	@Override
	public Map<String, String> writeXMLAttributes() {
		Map<String, String> attributes = super.writeXMLAttributes();

		return attributes;
	}
}
