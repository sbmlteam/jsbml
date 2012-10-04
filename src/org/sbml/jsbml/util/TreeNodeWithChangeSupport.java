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
   * Removes recursively the given change listener from this element. A call to
   * this method
   * is equivalent to calling
   * {@link #removeTreeNodeChangeListener(TreeNodeChangeListener, boolean)}
   * where the second argument is {@code true}.
   * 
   * @param listener
   *        the listener to remove.
   * @see #removeTreeNodeChangeListener(TreeNodeChangeListener, boolean)
   */
  public void removeTreeNodeChangeListener(TreeNodeChangeListener listener);

  /**
   * Removes the given change listener from this element.
   * 
   * @param listener
   *        the listener to remove.
   * @param recursive
   *        switch to decide whether or not the given listener should be removed
   *        in a recursive manner.
   * @see #removeTreeNodeChangeListener(TreeNodeChangeListener)
   */
  public void removeTreeNodeChangeListener(TreeNodeChangeListener listener, boolean recursive);
    
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
   * Adds recursively a listener to the {@link TreeNodeWithChangeSupport} object and
   * all of its sub-elements. Calling this method is effectively identical to
   * the call
   * {@link #addTreeNodeChangeListener(TreeNodeChangeListener, boolean)} where
   * {@code recursively = true}.
   * 
   * @param listener
   *        the listener to add
   * @see #addTreeNodeChangeListener(TreeNodeChangeListener, boolean)
   */
  public void addTreeNodeChangeListener(TreeNodeChangeListener listener);
  
  /**
   * Adds a listener to this {@link TreeNodeWithChangeSupport} object and
   * optionally also to all of its child nodes.
   * 
   * @param listener
   *        the listener to add
   * @param recursive
   *        if {@code true} the given listener will be added to this node and
   *        also recursively to all of its child nodes. If {@code false}, the
   *        listener will only be added to the current node.
   * @see #addTreeNodeChangeListener(TreeNodeChangeListener)
   */
  public void addTreeNodeChangeListener(TreeNodeChangeListener listener, boolean recursive);
  
  /**
   * @return the number of {@link TreeNodeChangeListener}s currently assigned to
   *         this {@link TreeNodeWithChangeSupport}
   */
  public abstract int getTreeNodeChangeListenerCount();
  
  /**
   * 
   * @return
   */
  public boolean isSetParent();
  
  /**
   * Opposite of {@link #isSetParent()}.
   * 
   * Returns {@code true} if this {@link AbstractTreeNode} is the root
   * node of a tree, {@code false} otherwise.
   * 
   * @return {@code true} if this {@link AbstractTreeNode} is the root
   *         node of a tree, {@code false} otherwise.
   * 
   * @see #isSetParent()
   */
  public abstract boolean isRoot();
  
  /**
   * Adds recursively all given {@link TreeNodeChangeListener} instances to
   * this element.
   * 
   * @param listeners
   *            the set of listeners to add
   * @return {@code true} if the set of listeners is added with success.
   * 
   */
  public boolean addAllChangeListeners(
      Collection<TreeNodeChangeListener> listeners);
}
