/*
 * $Id: GroupModelPlugin.java 834 2011-10-24 13:10:20Z niko-rodrigue $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/extensions/groups/src/org/sbml/jsbml/ext/groups/GroupModelPlugin.java $
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2013 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.groups;

import java.text.MessageFormat;
import java.util.Map;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.AbstractSBasePlugin;
import org.sbml.jsbml.util.TreeNodeChangeListener;
import org.sbml.jsbml.util.filters.NameFilter;

/**
 * @author Nicolas Rodriguez
 * @author Clemens Wrzodek
 * @since 1.0
 * @version $Rev: 834 $
 */
public class GroupsModelPlugin extends AbstractSBasePlugin {


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getElementNamespace()
   */
  @Override
  public String getElementNamespace() {
    return GroupsConstants.getNamespaceURI(getLevel(), getVersion());
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getPackageName()
   */
  @Override
  public String getPackageName() {
    return GroupsConstants.packageName;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getPrefix()
   */
  @Override
  public String getPrefix() {
    return GroupsConstants.shortLabel;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getURI()
   */
  @Override
  public String getURI() {
    return getElementNamespace();
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractTreeNode#getParent()
   */
  @Override
  public SBMLDocument getParent() {
    return (SBMLDocument) getExtendedSBase().getParent();
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.AbstractSBasePlugin#getParentSBMLObject()
   */
  @Override
  public SBMLDocument getParentSBMLObject() {
    return getParent();
  }
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
   * @param model
   */
  public GroupsModelPlugin(Model model) {
    super(model);
    initDefaults();
  }

  /**
   * @param groupModelPlugin
   */
  public GroupsModelPlugin(GroupsModelPlugin groupModelPlugin) {
    super(groupModelPlugin);
    // We don't clone the pointer to the containing model.
    if (groupModelPlugin.listOfGroups != null) {
      listOfGroups = groupModelPlugin.listOfGroups.clone();
    }
  }

  private void initDefaults() {
    listOfGroups.addNamespace(GroupsConstants.namespaceURI);
    listOfGroups.setSBaseListType(ListOf.Type.other);
    extendedSBase.registerChild(listOfGroups);
  }

  /**
   * 
   * @param group
   */
  public void addGroup(Group group) {
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

  public int getGroupCount() {
    return listOfGroups.size();
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
    extendedSBase.registerChild(listOfGroups);
  }

  /**
   * Removes the {@link #listOfGroups} from this {@link Model} and notifies
   * all registered instances of {@link TreeNodeChangeListener}.
   * 
   * @return {@code true} if calling this method lead to a change in this
   *         data structure.
   */
  public boolean unsetListOfGroups() {
    if (listOfGroups != null) {
      ListOf<Group> oldListOfGroups = listOfGroups;
      listOfGroups = null;
      oldListOfGroups.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.AbstractSBasePlugin#clone()
   */
  @Override
  public GroupsModelPlugin clone() {
    return new GroupsModelPlugin(this);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "GroupModelPlugin [listOfGroups=" + listOfGroups + ", model=" + extendedSBase
        + "]";
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    // No attribute define on this plugin
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    // No attribute define on this plugin
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getChildAt(int)
   */
  @Override
  public SBase getChildAt(int childIndex) {
    if (childIndex < 0 || childIndex >= 1) {
      throw new IndexOutOfBoundsException(MessageFormat.format(
        "Index {0,number,integer} >= {1,number,integer}", childIndex,
        +Math.min(getChildCount(), 0)));
    }

    return listOfGroups;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getChildCount()
   */
  @Override
  public int getChildCount() {
    if (isSetListOfGroups()) {
      return 1;
    }
    return 0;
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return true;
  }

  /**
   * @param symbol_of_members
   * @return
   */
  public Group createGroup() {
    Group g = new Group();
    addGroup(g);
    return g;
  }

  /**
   * @param symbol_of_members
   * @return
   */
  public Group createGroup(String... symbol_of_members) {
    Group g = createGroup();

    if (symbol_of_members!=null) {
      for (String s_member: symbol_of_members) {
        g.createMember(s_member);
      }
    }

    return g;
  }

  /**
   * Gets the {@link Group} that has the given id.
   * 
   * @param sbmlID
   * @return the {@link Group} that has the given id or {@code null} if no
   *         {@link Group} is found that matches {@code sbmlID}.
   */
  public SBase getGroup(String sbmlID) {
    if (isSetListOfGroups()) {
      return listOfGroups.firstHit(new NameFilter(sbmlID));
    }
    return null;
  }

}
