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
package org.sbml.jsbml;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.util.TreeNodeChangeEvent;
import org.sbml.jsbml.util.TreeNodeChangeListener;
import org.sbml.jsbml.util.TreeNodeRemovedEvent;
import org.sbml.jsbml.util.TreeNodeWithChangeSupport;
import org.sbml.jsbml.util.filters.Filter;

/**
 * A basic implementation of the {@link TreeNode} interface.
 * 
 * @author Andreas Dr&auml;ger
 * @version $Rev$
 * @since 0.8
 * @date 11.07.2011
 */
public abstract class AbstractTreeNode implements TreeNodeWithChangeSupport {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 8629109724566600238L;

  /**
   * Searches the given child in the list of sub-nodes of the parent element.
   * 
   * @param parent
   * @param child
   * @return the index of the child in the parent's list of children or -1 if no
   *         such child can be found.
   */
  @SuppressWarnings("unchecked")
  public static int indexOf(TreeNode parent, TreeNode child) {
    if (child == null) {
      throw new IllegalArgumentException("Argument is null.");
    }
    // linear search
    Enumeration<TreeNode> e = parent.children();
    for (int i = 0; e.hasMoreElements(); i++) {
      TreeNode elem = e.nextElement();
      if ((child == elem) || child.equals(elem)) {
        return i;
      }
    }
    // not found => node is not a child.
    return -1;
  }

  /**
   * {@link List} of listeners for this component
   */
  protected List<TreeNodeChangeListener> listOfListeners;

  /**
   * The parent element of this {@link Annotation}.
   */
  protected TreeNode parent;

  /**
   * Any kind of {@link Object} that can be stored in addition to all other
   * features of this {@link AbstractTreeNode} in form of key-value pairs.
   * Note that things stored here will not be written to SBML files. This
   * only provides a possibility to attach some in-memory information to
   * derived classes. 
   */
  private Map<Object, Object> userObjects;

  /**
   * Creates an empty {@link AbstractTreeNode} without child nodes and an
   * empty list of {@link TreeNodeChangeListener}s. The pointer to the parent
   * of this node is set to <code>null</code>.
   */
  public AbstractTreeNode() {
    super();
    this.listOfListeners = new LinkedList<TreeNodeChangeListener>();
    this.parent = null;
  }

  /**
   * Constructor for cloning. {@link AbstractTreeNode} has two properties:
   * {@link #parent} and {@link #listOfListeners}. Both of them are not cloned
   * by this method, for two reasons:
   * <ul>
   * <li>The {@link #parent} is not cloned and is left as <code>null</code>
   * because the new {@link AbstractTreeNode} will get a parent set as soon as
   * it is added/linked again to a {@link Model}. Note that only the top-level
   * element of the cloned sub-tree will have a <code>null</code> value as its
   * parent. All sub-element will point to their correct parent element.</li>
   * <li>{@link #listOfListeners} is needed in all other setXX() methods.
   * Cloning these might lead to strange and unexpected behavior, because when
   * doing a deep cloning, the listeners of the old object would suddenly be
   * informed about all value changes within this new object. Since we do
   * cloning, all values of all child elements have to be touched, i.e., all
   * listeners would be informed many times, but each time receive the identical
   * value as it was before. Since it is totally unclear of which type listeners
   * are, a deep cloning of these is not possible.</li>
   * </ul>
   * Therefore, it is necessary to keep in mind that the parent of the clone
   * will be null and that you have to care by yourself if you are using
   * {@link TreeNodeChangeListener}s.
   * 
   * @param node
   *        The original {@link TreeNode} to be cloned.
   */
  public AbstractTreeNode(TreeNode node) {
    this();
    // the parent is not cloned and is left as null
    // The Object will get a parent set as soon as it is added/linked
    // again to a model somehow.		
    // this.parent = node.getParent();

    /* listOfListeners is needed in all other setXX() methods.
     * Cloning these might lead to strange and unexpected behavior. 
     * This is actually not deep cloning anyway:
     */
    if (node instanceof AbstractTreeNode) {
      AbstractTreeNode anode = (AbstractTreeNode) node;
      // Do not clone listeners!
      //   this.listOfListeners.addAll(anode.listOfListeners);
      if (anode.isSetUserObjects()) {
        this.userObjects = new HashMap<Object, Object>();
        this.userObjects.putAll(anode.userObjects);
      }
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#addAllChangeListeners(java.util.Collection)
   */
  //@Override
  public boolean addAllChangeListeners(
    Collection<TreeNodeChangeListener> listeners) {
    boolean success = listOfListeners.addAll(listeners);
    Enumeration<TreeNode> children = children();
    while (children.hasMoreElements()) {
      TreeNode node = children.nextElement();
      if (node instanceof TreeNodeWithChangeSupport) {
        success &= ((TreeNodeWithChangeSupport) node)
            .addAllChangeListeners(listeners);
      }
    }
    return success;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#addTreeNodeChangeListener(org.sbml.jsbml.util.TreeNodeChangeListener)
   */
  //@Override
  public void addTreeNodeChangeListener(TreeNodeChangeListener listener) {
    if (!listOfListeners.contains(listener)) {
      listOfListeners.add(listener);
    }
    Enumeration<TreeNode> children = children();
    while (children.hasMoreElements()) {
      TreeNode node = children.nextElement();
      if (node instanceof TreeNodeWithChangeSupport) {
        ((TreeNodeWithChangeSupport) node).addTreeNodeChangeListener(listener);
      }
    }
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#children()
   */
  //@Override
  public Enumeration<TreeNode> children() {
    return new Enumeration<TreeNode>() {
      /**
       * Total number of children in this enumeration.
       */
      private int childCount = getChildCount();
      /**
       * Current position in the list of children.
       */
      private int index;

      /* (non-Javadoc)
       * @see java.util.Enumeration#hasMoreElements()
       */
      //@Override
      public boolean hasMoreElements() {
        return index < childCount;
      }

      /* (non-Javadoc)
       * @see java.util.Enumeration#nextElement()
       */
      //@Override
      public TreeNode nextElement() {
        synchronized (this) {
          if (index < childCount) {
            return getChildAt(index++);
          }
        }
        throw new NoSuchElementException("Enumeration");
      }
    };
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#clearUserObjects()
   */
  //@Override
  public void clearUserObjects() {
    if (isSetUserObjects()) {
      userObjects.clear();
    }
  }

  /* (non-Javadoc)
   * @see java.lang.Object#clone()
   */
  @Override
  public abstract TreeNode clone();

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#containsUserObjectKey(java.lang.Object)
   */
  //@Override
  public boolean containsUserObjectKey(Object key) {
    return isSetUserObjects() ? userObjects.containsKey(key) : false;
  }	

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    // Check if the given object is a pointer to precisely the same object:
    if (super.equals(object)) {
      return true;
    }
    // Check if the given object is of identical class and not null: 
    if ((object == null) || (!getClass().equals(object.getClass()))) {
      return false;
    }
    // Check all child nodes recursively:
    if (object instanceof TreeNode) {
      TreeNode stn = (TreeNode) object;
      int childCount = getChildCount();
      boolean equal = stn.isLeaf() == isLeaf();
      /*
       * This is not good because cloned AbstractTreeNodes may not point
       * to the same parent as the original and would hence not be equal
       * to the cloned object.
       */
      //	equal &= ((stn.getParent() == null) && isRoot())
      //           || (stn.getParent() == getParent());
      equal &= stn.getChildCount() == childCount;
      if (equal && (childCount > 0)) {
        for (int i = 0; i < childCount; i++) {
          if (!stn.getChildAt(i).equals(getChildAt(i))) {
            return false;
          }
        }
      }
      return equal;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#filter(org.sbml.jsbml.util.filters.Filter)
   */
  //@Override
  public List<? extends TreeNode> filter(Filter filter) {
    return filter(filter, false);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#filter(org.sbml.jsbml.util.filters.Filter, boolean)
   */
  //@Override
  public List<TreeNode> filter(Filter filter, boolean retainInternalNodes) {
    List<TreeNode> list = new LinkedList<TreeNode>(), childList;
    TreeNode child;
    boolean accepts = filter.accepts(this);
    if (accepts) {
      list.add(this);
    }
    for (int i = 0; i < getChildCount(); i++) {
      child = getChildAt(i);
      if (child instanceof TreeNodeWithChangeSupport) {
        childList = ((TreeNodeWithChangeSupport) child).filter(filter, retainInternalNodes);
        if (!accepts && retainInternalNodes && (childList.size() > 0)) {
          list.add(this);
          // prevent adding the current node more often than once:
          accepts = true;
        }
        list.addAll(childList);
      }
    }
    return list;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#fireNodeAddedEvent()
   */
  //@Override
  public void fireNodeAddedEvent() {
    for (TreeNodeChangeListener listener : listOfListeners) {
      listener.nodeAdded(this);
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#fireNodeRemovedEvent()
   */
  //@Override
  public void fireNodeRemovedEvent() {
    TreeNode previousParent = getParent();
    parent = null;
    for (TreeNodeChangeListener listener : listOfListeners) {
      listener.nodeRemoved(new TreeNodeRemovedEvent(this, previousParent));
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#firePropertyChange(java.lang.String, java.lang.Object, java.lang.Object)
   */
  //@Override
  public void firePropertyChange(String propertyName, Object oldValue,
    Object newValue) {
    if (listOfListeners.size() > 0) {
      short changeType = -1; // no property change at all
      if ((oldValue == null) && (newValue != null)) {
        changeType = 0; // element added
      } else if ((oldValue != null) && (newValue == null)) {
        changeType = 1; // element removed
      } else if ((oldValue != null) && !oldValue.equals(newValue)) {
        changeType = 2; // real property change
      }
      if (-1 < changeType) {
        boolean newValTreeNode = newValue instanceof TreeNodeWithChangeSupport;
        boolean oldValTreeNode = oldValue instanceof TreeNodeWithChangeSupport;
        if ((changeType == 0) && newValTreeNode) {
          ((TreeNodeWithChangeSupport) newValue).fireNodeAddedEvent();
        } else if ((changeType == 1) && oldValTreeNode) {
          ((TreeNodeWithChangeSupport) oldValue).fireNodeRemovedEvent();
        } else {
          // TODO: check if notifying and updating the metaId is necessary
          // because of the method AbstractSBase.setThisAsParentSBMLObject
          if (oldValTreeNode && newValTreeNode) {
            notifyChildChange((TreeNode) oldValue, (TreeNode) newValue);
          }
          /*
           * It is not necessary to add the metaId of the new value to
           * the SBMLDocument because this is already done in the
           * method setThisAsParentSBMLObject, a method that is called
           * to link a new element to an existing SBML tree.
           */
          // Now we can notify all listeners about the change:
          TreeNodeChangeEvent changeEvent = new TreeNodeChangeEvent(this,
            propertyName, oldValue, newValue);
          for (TreeNodeChangeListener listener : listOfListeners) {
            listener.propertyChange(changeEvent);
          }
        }
      }
    }
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getIndex(javax.swing.tree.TreeNode)
   */
  //@Override
  public int getIndex(TreeNode node) {
    return indexOf(this, node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#getListOfTreeNodeChangeListeners()
   */
  //@Override
  public List<TreeNodeChangeListener> getListOfTreeNodeChangeListeners() {
    return listOfListeners;
  }

  /**
   * Returns the number of child elements of this {@link TreeNode}.
   * 
   * @return the number of children TreeNodes the receiver contains.
   * @deprecated use {@link #getChildCount()}
   */
  @Deprecated
  public int getNumChildren() {
    return getChildCount();
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getParent()
   */
  //@Override
  public TreeNode getParent() {
    return parent;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#getUserObject(java.lang.Object)
   */
  //@Override
  public Object getUserObject(Object key) {
    if (userObjects == null) {
      userObjects = new HashMap<Object, Object>();
    }
    return userObjects.get(key);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    // A constant and arbitrary, sufficiently large prime number:
    final int prime = 769;
    /*
     * This method is implemented as suggested in the JavaDoc API
     * documentation of the List interface.
     */

    // Compute the initial hashCode based on the name of the actual class.
    int hashCode = getClass().getName().hashCode();
    /*
     * The following start wouldn't work because it will compute the
     * hashCode from the address in memory of the object.
     */
    // int hashCode = super.hashCode();

    // Recursively compute the hashCode for each child node:
    TreeNode child;
    for (int i = 0; i < getChildCount(); i++) {
      child = getChildAt(i);
      hashCode = prime * hashCode + (child == null ? 0 : child.hashCode());
    }

    return hashCode;
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#isLeaf()
   */
  //@Override
  public boolean isLeaf() {
    return getChildCount() == 0;
  }

  /**
   * Opposite of {@link #isSetParent()}.
   * 
   * Returns <code>true</code> if this {@link AbstractTreeNode} is the root
   * node of a tree, <code>false</code> otherwise.
   * 
   * @return <code>True</code> if this {@link AbstractTreeNode} is the root
   *         node of a tree, <code>false</code> otherwise.
   * 
   * @see #isSetParent()
   */
  public boolean isRoot() {
    return !isSetParent();
  }

  /**
   * 
   * @return
   */
  public boolean isSetParent() {
    return parent != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#isSetUserObjects()
   */
  //@Override
  public boolean isSetUserObjects() {
    return (userObjects != null) && !userObjects.isEmpty();
  }

  /**
   * This method is called when one child has been swapped with another one
   * and can be used to check certain properties of the resulting changed
   * tree.
   * 
   * @param oldChild
   *            the element that was a child of this node before the change.
   * @param newChild
   *            the new child whose new parent is this node.
   */
  protected void notifyChildChange(TreeNode oldChild, TreeNode newChild) {
    // default: empty body, nothing to do.
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#putUserObject(java.lang.Object, java.lang.Object)
   */
  //@Override
  public void putUserObject(Object key, Object userObject) {
    if (userObjects == null) {
      userObjects = new HashMap<Object, Object>();
    }
    Object oldObject = userObjects.put(key, userObject);
    firePropertyChange(key.toString(), oldObject, userObject);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#removeAllTreeNodeChangeListeners()
   */
  //@Override
  public void removeAllTreeNodeChangeListeners() {
    listOfListeners.clear();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#removeTreeNodeChangeListener(org.sbml.jsbml.util.TreeNodeChangeListener)
   */
  //@Override
  public void removeTreeNodeChangeListener(TreeNodeChangeListener l) {
    listOfListeners.remove(l);
    Enumeration<TreeNode> children = children();
    while (children.hasMoreElements()) {
      TreeNode node = children.nextElement();
      if (node instanceof TreeNodeWithChangeSupport) {
        ((TreeNodeWithChangeSupport) node).removeTreeNodeChangeListener(l);
      }
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#removeUserObject(java.lang.Object)
   */
  //@Override
  public Object removeUserObject(Object key) {
    if (userObjects != null) {
      return userObjects.remove(key);
    }
    return null;
  }

  /**
   * @param parent
   *            the parent to set
   */
  protected void setParent(TreeNode parent) {
    TreeNode oldValue = this.parent;
    this.parent = parent;
    this.firePropertyChange(TreeNodeChangeEvent.parentSBMLObject, oldValue, this.parent);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return StringTools.firstLetterLowerCase(getClass().getSimpleName());
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#userObjectKeySet()
   */
  //@Override
  public Set<Object> userObjectKeySet() {
    return userObjects.keySet();
  }

}
