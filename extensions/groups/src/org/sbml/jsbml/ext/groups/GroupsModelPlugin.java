/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2018 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 5. The Babraham Institute, Cambridge, UK
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
import java.util.Collection;
import java.util.Map;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.AbstractSBasePlugin;
import org.sbml.jsbml.util.filters.NameFilter;

/**
 * @author Nicolas Rodriguez
 * @author Clemens Wrzodek
 * @since 1.0
 */
public class GroupsModelPlugin extends AbstractSBasePlugin {


  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 3334444867660252255L;


  /**
   * 
   */
  protected ListOf<Group> listOfGroups;


  /**
   * Creates a new {@link GroupsModelPlugin} instance cloned from the given parameter.
   * 
   * @param groupModelPlugin the instance to clone
   */
  public GroupsModelPlugin(GroupsModelPlugin groupModelPlugin) {
    super(groupModelPlugin);
    // We don't clone the pointer to the containing model.
    if (groupModelPlugin.listOfGroups != null) {
      setListOfGroups(groupModelPlugin.getListOfGroups().clone());
    }
  }


  /**
   * Creates a new {@link GroupsModelPlugin} instance
   * 
   * @param model the parent core {@link Model}.
   */
  public GroupsModelPlugin(Model model) {
    super(model);
    initDefaults();
  }


  /**
   * Adds a new element to the listOfGroups.
   * <p>listOfGroups is initialized if necessary.
   *
   * @param group
   * @return {@code true} (as specified by {@link Collection#add})
   */
  public boolean addGroup(Group group) {
    return getListOfGroups().add(group);
  }
  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.AbstractSBasePlugin#clone()
   */
  @Override
  public GroupsModelPlugin clone() {
    return new GroupsModelPlugin(this);
  }
  /**
   * Creates a new instance of {@link Group} and add it to this {@link GroupsModelPlugin}.
   * 
   * @return the new {@link Group} instance.
   */
  public Group createGroup() {
    return createGroup(null);
  }


  /**
   * Creates a new instance of {@link Group} and add it to this {@link GroupsModelPlugin}.
   * 
   * @param id the id to be set to the new {@link Group}.
   * @return the new {@link Group} instance.
   */
  public Group createGroup(String id) {
    Group g = new Group(getLevel(), getVersion());
    if (id != null) {
      g.setId(id);
    }
    addGroup(g);
    return g;
  }

  /**
   * Creates a new instance of {@link Group} and add it to this {@link GroupsModelPlugin}.
   * For each id in the memberIds array, a new {@link Member} instance is created and added to the {@link Group}
   * as well.
   * 
   * @param id the id to be set to the new {@link Group}.
   * @param memberIds the ids to be set to the new {@link Member} instances.
   * @return the new {@link Group} instance.
   */
  public Group createGroup(String id, String... memberIds) {
    Group g = createGroup(id);

    if (memberIds != null) {
      for (String memberId: memberIds) {
        g.createMember(memberId);
      }
    }

    return g;
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getChildAt(int)
   */
  @Override
  public SBase getChildAt(int childIndex) {
    if (childIndex < 0 || childIndex >= 1) {
      throw new IndexOutOfBoundsException(MessageFormat.format(
        resourceBundle.getString("IndexExceedsBoundsException"), childIndex,
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

  /**
   * Returns the n-th {@link Group} object in this {@link GroupsModelPlugin}.
   *
   * @param i an index
   * @return the {@link Group} with the given index if it exists.
   * @throws IndexOutOfBoundsException
   */
  public Group getGroup(int i) {
    if (!isSetListOfGroups()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }

    return listOfGroups.get(i);
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

  /**
   * Returns the number of {@link Group}s of this {@link GroupsModelPlugin}.
   * 
   * @return the number of {@link Group}s of this {@link GroupsModelPlugin}.
   */
  public int getGroupCount() {
    return isSetListOfGroups() ? listOfGroups.size() : 0;
  }

  /**
   * Returns the listOfGroups. If the {@link ListOf} is not defined, creates an empty one.
   * 
   * @return the listOfGroups
   */
  public ListOf<Group> getListOfGroups() {
    if (!isSetListOfGroups()) {
      listOfGroups = new ListOf<Group>();

      listOfGroups.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'groups'
      listOfGroups.setPackageName(null);
      listOfGroups.setPackageName(GroupsConstants.shortLabel);
      listOfGroups.setSBaseListType(ListOf.Type.other);
      listOfGroups.setOtherListName(GroupsConstants.listOfGroups);

      if (extendedSBase != null) {
        extendedSBase.registerChild(listOfGroups);
      }
    }

    return listOfGroups;
  }

  /**
   * Returns the number of {@link Group}s of this {@link GroupsModelPlugin}.
   * 
   * @return the number of {@link Group}s of this {@link GroupsModelPlugin}.
   * @libsbml.deprecated same as {@link #getGroupCount()}
   */
  public int getNumGroups() {
    return getGroupCount();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getPackageName()
   */
  @Override
  public String getPackageName() {
    return GroupsConstants.shortLabel;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractTreeNode#getParent()
   */
  @Override
  public SBMLDocument getParent() {
    if (isSetExtendedSBase()) {
      return (SBMLDocument) getExtendedSBase().getParent();
    }

    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.AbstractSBasePlugin#getParentSBMLObject()
   */
  @Override
  public SBMLDocument getParentSBMLObject() {
    return getParent();
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

  /**
   * 
   */
  private void initDefaults() {
    setPackageVersion(-1);
  }

  /**
   * 
   * @return
   */
  public boolean isSetListOfGroups() {
    return listOfGroups != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    // No attribute define on this plugin
    return false;
  }

  /**
   * 
   * @param listOfGroups
   */
  public void setListOfGroups(ListOf<Group> listOfGroups) {
    unsetListOfGroups();

    this.listOfGroups = listOfGroups;

    if (listOfGroups != null) {
      listOfGroups.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'groups'
      listOfGroups.setPackageName(null);
      listOfGroups.setPackageName(GroupsConstants.shortLabel);
      listOfGroups.setSBaseListType(ListOf.Type.other);
      listOfGroups.setOtherListName(GroupsConstants.listOfGroups);

      if (isSetExtendedSBase()) {
        extendedSBase.registerChild(listOfGroups);
      }
    }
  }

  /**
   * Removes the {@link #listOfGroups} from this {@link Model} and notifies
   * all registered instances of {@link org.sbml.jsbml.util.TreeNodeChangeListener}.
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
   * @see org.sbml.jsbml.ext.SBasePlugin#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    // No attribute define on this plugin
    return null;
  }

}
