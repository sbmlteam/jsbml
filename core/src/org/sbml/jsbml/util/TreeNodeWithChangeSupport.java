/*
 *
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
package org.sbml.jsbml.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.util.filters.Filter;

/**
 * This interface extends the regular recursively defined {@link TreeNode} by
 * adding methods to keep track of changes within the tree, such as
 * adding/removing, or exchanging of child nodes or the change of any other
 * attributes.
 * 
 * @author Andreas Dr&auml;ger
 * @since 0.8
 */
public interface TreeNodeWithChangeSupport extends Cloneable, TreeNode,
Serializable {

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

  /**
   * Adds recursively a listener to the {@link TreeNodeWithChangeSupport}
   * object and all of its sub-elements. Calling this method is effectively
   * identical to the call
   * {@link #addTreeNodeChangeListener(TreeNodeChangeListener, boolean)} where
   * {@code recursively = true}.
   * 
   * @param listener
   *            the listener to add
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
   * Removes all of the mappings from the map of user objects (optional
   * operation). The map will be empty after this call returns.
   * 
   * @see Map#clear()
   */
  public abstract void clearUserObjects();

  /**
   * @param key
   * @return
   * @see Map#containsKey(java.lang.Object)
   */
  public abstract boolean containsUserObjectKey(Object key);

  /**
   * Filters this tree data structure recursively and returns a list of all
   * {@link TreeNode}s that are accepted by the {@link Filter}. Although
   * internal nodes that do not satisfy the filter criterion by themselves
   * are not contained in the resulting list, the recursion continues at
   * their children.
   * 
   * @param filter
   *        A criterion to select a sub-set of nodes of this tree.
   * @return A {@link List} of {@link TreeNode}s that do all satisfy the
   *         criterion of the given {@link Filter}.
   * @see #filter(Filter, boolean)
   */
  public List<? extends TreeNode> filter(Filter filter);

  /**
   * Filters this tree data structure recursively and returns a list of all
   * {@link TreeNode}s that are accepted by the {@link Filter}. The second
   * argument decides whether or not internal nodes that do not by themselves
   * satisfy the filter criterion should still be retained in the result list.
   * 
   * @param filter
   *            A criterion to select a sub-set of nodes of this tree.
   * @param retainInternalNodes
   *            Decides if internal nodes should also be included in the
   *            resulting {@link List} if they do not by themselves satisfy
   *            the {@link Filter} but if these do have child elements that do
   *            so. This might be useful in order to obtain a complete tree
   *            path to interesting sub-elements.
   * @return A {@link List} representing a subset of {@link TreeNode}s that
   *         satisfy the {@link Filter}'s criterion, or whose child nodes do
   *         so.
   * @see #filter(Filter)
   */
  public List<? extends TreeNode> filter(Filter filter, boolean retainInternalNodes);

  /**
   * Filters this tree data structure recursively and returns a list of all
   * {@link TreeNode}s that are accepted by the {@link Filter}. The two
   * boolean switches let you decide if internal nodes that do not by
   * themselves satisfy the filter criterion should be retained in the list,
   * and if the recursion should be aborted as soon as the first hit is
   * discovered.
   * 
   * @param filter
   * @param retainInternalNodes
   *            decides whether or not internal nodes should be added to the
   *            list of results even though these might not by themselves
   *            satisfy the filter's criterion, but whose children do. This
   *            feature can be useful, e.g., in order to keep the full tree
   *            path to nodes of interest
   * @param prune
   *            if this argument is {@code true}, the recursive search is
   *            aborted upon the discovery of the first hit.
   * @return A {@link List} representing a subset of {@link TreeNode}s that
   *         satisfy the {@link Filter}'s criterion, or whose child nodes do
   *         so.
   * @see #filter(Filter, boolean)
   */
  public List<? extends TreeNode> filter(Filter filter, boolean retainInternalNodes, boolean prune);

  /**
   * All {@link TreeNodeChangeListener} instances linked to this
   * {@link TreeNode} are informed about the adding of this {@link Object} to
   * an owning parent {@link Object}.
   */
  public void fireNodeAddedEvent();

  /**
   * All {@link TreeNodeChangeListener} instances linked to this
   * {@link TreeNode} are informed about the deletion of this {@link TreeNode}
   * from a parent {@link Object}.
   */
  public void fireNodeRemovedEvent();

  /**
   * All {@link TreeNodeChangeListener}s are informed about the change in this
   * {@link TreeNodeWithChangeSupport}.
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
   * Returns all {@link TreeNodeChangeListener}s that are assigned to this
   * element.
   * 
   * @return all {@link TreeNodeChangeListener}s that are assigned to this
   * element.
   */
  public List<TreeNodeChangeListener> getListOfTreeNodeChangeListeners();

  /**
   * @return A {@link TreeNode} without parent, which is the top-most ancestor
   *         of this node.
   */
  public TreeNode getRoot();

  /**
   * @return the number of {@link TreeNodeChangeListener}s currently assigned to
   *         this {@link TreeNodeWithChangeSupport}
   */
  public abstract int getTreeNodeChangeListenerCount();

  /**
   * @param key
   * @return the userObject
   */
  public abstract Object getUserObject(Object key);

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
   * 
   * @return
   */
  public boolean isSetParent();

  /**
   * Checks whether any user-defined key-value pairs have been attached
   * to this object.
   * 
   * @return {@code true} if at least one user-defined key-value pair has
   *         been attached to this object.
   */
  public abstract boolean isSetUserObjects();

  /**
   * @param key
   *            some user-defined key under which the given userObject can be
   *            found.
   * @param userObject
   *            the userObject to set
   */
  public abstract void putUserObject(Object key, Object userObject);

  /**
   * Removes all tree node change listeners from this element.
   */
  public void removeAllTreeNodeChangeListeners();

  /**
   * Removes all tree node change listeners recursively from this element.
   * @param recursive
   */
  public void removeAllTreeNodeChangeListeners(boolean recursive);

  /**
   * Removes itself from its parent. This will remove the element from its
   * parent and remove the pointer from the element to its parent. If the
   * element is a member of a {@link List}, it is simply cleared.
   * Will fail (and not delete itself) if it has no parent object. This function
   * will work for all objects whose parent is of type {@link List} and most
   * instances of {@link TreeNode}.
   * 
   * @return {@code false} if this element is a root node, {@code true}
   *         otherwise.
   */
  public abstract boolean removeFromParent();

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
   * 
   * @param key
   * @return
   */
  public abstract Object removeUserObject(Object key);

  /**
   * @return
   * @see Map#keySet()
   */
  public abstract Set<Object> userObjectKeySet();

  /**
   * @param listeners
   * @param recursive
   * @return
   */
  public boolean addAllChangeListeners(
    Collection<TreeNodeChangeListener> listeners, boolean recursive);

}
