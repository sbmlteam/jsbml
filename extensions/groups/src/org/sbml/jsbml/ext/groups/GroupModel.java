/*
 * $Id: GroupModel.java 834 2011-10-24 13:10:20Z niko-rodrigue $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/extensions/groups/src/org/sbml/jsbml/ext/groups/GroupModel.java $
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2012 jointly by the following organizations:
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

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.AbstractSBasePlugin;
import org.sbml.jsbml.util.TreeNodeChangeListener;
import org.sbml.jsbml.xml.parsers.GroupsParser;

/**
 * @author Nicolas Rodriguez
 * @author Clemens Wrzodek
 * @since 1.0
 * @version $Rev: 834 $
 */
public class GroupModel extends AbstractSBasePlugin {

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
	 * @param model
	 */
	public GroupModel(Model model) {
		this.model = model;
		initDefaults();
	}
	
	/**
   * @param groupModel
   */
  public GroupModel(GroupModel groupModel) {
    // We don't clone the pointer to the containing model.
    if (groupModel.listOfGroups != null) {
      this.listOfGroups = groupModel.listOfGroups.clone();
    }
  }

  private void initDefaults() {
		listOfGroups.addNamespace(GroupsParser.namespaceURI);
		listOfGroups.setSBaseListType(ListOf.Type.other);
		model.registerChild(listOfGroups);
	}
	
	/**
	 * 
	 * @param group
	 */
	public void addGroup(Group group) {
		model.registerChild(group);
		listOfGroups.add(group);		
	}

	/**
	 * 
	 * @param i
	 * @return
	 */
	public Group getGroup(int i) {
		if ((i >= 0) && (i < listOfGroups.size())) {
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
		listOfGroups.setParentSBMLObject(model);
	}

	/**
	 * Removes the {@link #listOfGroups} from this {@link Model} and notifies
	 * all registered instances of {@link TreeNodeChangeListener}.
	 * 
	 * @return <code>true</code> if calling this method lead to a change in this
	 *         data structure.
	 */
	public boolean unsetListOfGroups() {
		if (this.listOfGroups != null) {
			ListOf<Group> oldListOfGroups = this.listOfGroups;
			this.listOfGroups = null;
			oldListOfGroups.fireNodeRemovedEvent();
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.ext.AbstractSBasePlugin#clone()
	 */
	public GroupModel clone() {
		return new GroupModel(this);
	}

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "GroupModel [listOfGroups=" + listOfGroups + ", model=" + model
        + "]";
  }
  
	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.ext.SBasePlugin#readAttribute(java.lang.String, java.lang.String, java.lang.String)
	 */
	public boolean readAttribute(String attributeName, String prefix, String value) {
		// No attribute define on this plugin
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.ext.SBasePlugin#writeXMLAttributes()
	 */
	public Map<String, String> writeXMLAttributes() {
		// No attribute define on this plugin
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.ext.SBasePlugin#getChildAt(int)
	 */
	public SBase getChildAt(int childIndex) {
		if (childIndex < 0 || childIndex >= 1) {
			return null;
		}
		return listOfGroups;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.ext.SBasePlugin#getChildCount()
	 */
	public int getChildCount() {		
		if (isSetListOfGroups()) {
			return 1;
		}
		return 0;
	}



	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeNode#getAllowsChildren()
	 */
	public boolean getAllowsChildren() {
		return true;
	}

  /**
   * @param symbol_of_members
   * @return
   */
  public Group createGroup(String... symbol_of_members) {
    Group g = new Group();
    for (String s_member: symbol_of_members) {
      g.createMember(s_member);
    }
    addGroup(g);
    return g;
  }
  
}
