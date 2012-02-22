/*
 * $Id$
 * $URL$
 *
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
package org.sbml.jsbml.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.AbstractTreeNode;

/**
 * This interface extends the regular recursively defined {@link TreeNode} by
 * adding methods to keep track of changes within the tree, such as
 * adding/removing, or exchanging of child nodes or the change of any other 
 * attributes.
 * 
 * @author Andreas Dr&auml;ger
 * @version $Rev$
 * @since 0.8
 * @date 2011-09-15
 */
public interface TreeNodeWithChangeSupport extends Cloneable, TreeNode,
  Serializable {
  
  /**
   * Removes all SBase change listeners from this element.
   */
  public void removeAllTreeNodeChangeListeners();
  
  /**
   * Removes recursively the given change listener from this element.
   * 
   * @param l the listener to remove.
   */
  public void removeTreeNodeChangeListener(TreeNodeChangeListener l);
    
  /**
   * Returns all {@link TreeNodeChangeListener}s that are assigned to this
   * element.
   * 
   * @return all {@link TreeNodeChangeListener}s that are assigned to this
   * element.
   */
  public List<TreeNodeChangeListener> getListOfTreeNodeChangeListeners();
  
  /**
   * All {@link TreeNodeChangeListener}s are informed about the change in this
   * {@link AbstractTreeNode}.
   * 
   * @param propertyName
   *            Tells the {@link TreeNodeChangeListener} the name of the
   *            property whose value has been changed.
   * @param oldValue
   *            This is the value before the change.
   * @param newValue
   *            This gives the new value that is now the new value for the
   *            given property..
   */
  public void firePropertyChange(String propertyName, Object oldValue,
      Object newValue);
  
  /**
   * All {@link TreeNodeChangeListener} instances linked to this
   * {@link TreeNode} are informed about the deletion of this {@link TreeNode}
   * from a parent {@link Object}.
   */
  public void fireNodeRemovedEvent();
  
  /**
   * All {@link TreeNodeChangeListener} instances linked to this
   * {@link TreeNode} are informed about the adding of this {@link Object} to
   * an owning parent {@link Object}.
   */
  public void fireNodeAddedEvent();
  
  /**
   * Adds recursively a listener to the {@link AbstractTreeNode} object and
   * all of its sub-elements.
   * 
   * @param listener
   *            the listener to add
   */
  public void addTreeNodeChangeListener(TreeNodeChangeListener listener);
  
  /**
   * Adds recursively all given {@link TreeNodeChangeListener} instances to
   * this element.
   * 
   * @param listeners
   *            the set of listeners to add
   * @return <code>true</code> if the set of listeners is added with success.
   * 
   */
  public boolean addAllChangeListeners(
      Collection<TreeNodeChangeListener> listeners);
}
