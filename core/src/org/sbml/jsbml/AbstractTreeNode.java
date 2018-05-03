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
package org.sbml.jsbml;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;
import org.sbml.jsbml.util.ResourceManager;
import org.sbml.jsbml.util.TreeNodeChangeEvent;
import org.sbml.jsbml.util.TreeNodeChangeListener;
import org.sbml.jsbml.util.TreeNodeRemovedEvent;
import org.sbml.jsbml.util.TreeNodeWithChangeSupport;
import org.sbml.jsbml.util.filters.Filter;

/**
 * A basic implementation of the {@link TreeNode} interface.
 * 
 * @author Andreas Dr&auml;ger
 * @since 0.8
 */
public abstract class AbstractTreeNode implements TreeNodeWithChangeSupport {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 8629109724566600238L;

  /**
   * Localization support.
   */
  protected static final transient ResourceBundle resourceBundle = ResourceManager.getBundle("org.sbml.jsbml.resources.Messages");

  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(AbstractTreeNode.class);


  /**
   * Searches the given child in the list of sub-nodes of the parent element.
   * 
   * @param parent the parent
   * @param child the child to search for
   * @return the index of the child in the parent's list of children or -1 if no
   *         such child can be found.
   */
  @SuppressWarnings("unchecked")
  public static int indexOf(TreeNode parent, TreeNode child) {
    if (child == null) {
      throw new IllegalArgumentException(resourceBundle.getString("NullArgument"));
    }
    // linear search
    Enumeration<? extends TreeNode> e = parent.children();
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
  protected transient List<TreeNodeChangeListener> listOfListeners;

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
   * of this node is set to {@code null}.
   */
  public AbstractTreeNode() {
    super();
    listOfListeners = new ArrayList<TreeNodeChangeListener>();
    parent = null;
  }

  /**
   * Constructor for cloning. {@link AbstractTreeNode} has two properties:
   * {@link #parent} and {@link #listOfListeners}. Both of them are not cloned
   * by this method, for two reasons:
   * <ul>
   * <li>The {@link #parent} is not cloned and is left as {@code null}
   * because the new {@link AbstractTreeNode} will get a parent set as soon as
   * it is added/linked again to a {@link Model}. Note that only the top-level
   * element of the cloned sub-tree will have a {@code null} value as its
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
        userObjects = new HashMap<Object, Object>();
        userObjects.putAll(anode.userObjects);
      }
      
      // TODO - add this for all objects when we start using the checkAttribute method for other classes than Compartment.
      if (anode instanceof Compartment) {
        putUserObject(JSBML.CLONING_IN_PROGRESS, "true");
      }
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#addAllChangeListeners(java.util.Collection)
   */
  @Override
  public boolean addAllChangeListeners(
    Collection<TreeNodeChangeListener> listeners) {

    return addAllChangeListeners(listeners, true);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#addAllChangeListeners(java.util.Collection, boolean)
   */
  @Override
  public boolean addAllChangeListeners(
    Collection<TreeNodeChangeListener> listeners, boolean recursive) {
    boolean success = listOfListeners.addAll(listeners);
    if (recursive) {
      Enumeration<TreeNode> children = children();
      while (children.hasMoreElements()) {
        TreeNode node = children.nextElement();
        if (node instanceof TreeNodeWithChangeSupport) {
          success &= ((TreeNodeWithChangeSupport) node)
              .addAllChangeListeners(listeners, recursive);
        }
      }
    }
    return success;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#addTreeNodeChangeListener(org.sbml.jsbml.util.TreeNodeChangeListener)
   */
  @Override
  public void addTreeNodeChangeListener(TreeNodeChangeListener listener) {
    addTreeNodeChangeListener(listener, true);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#addTreeNodeChangeListener(org.sbml.jsbml.util.TreeNodeChangeListener, boolean)
   */
  @Override
  public void addTreeNodeChangeListener(TreeNodeChangeListener listener, boolean recursive) {
    if (!listOfListeners.contains(listener)) {
      listOfListeners.add(listener);
    }
    if (recursive) {
      Enumeration<TreeNode> children = children();
      while (children.hasMoreElements()) {
        TreeNode node = children.nextElement();
        if (node instanceof TreeNodeWithChangeSupport) {
          ((TreeNodeWithChangeSupport) node).addTreeNodeChangeListener(listener);
        }
      }
    }
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#children()
   */
  @Override
  public Enumeration<TreeNode> children() {
    return new Enumeration<TreeNode>() {
      /**
       * Total number of children in this enumeration.
       */
      private final int childCount = getChildCount();
      /**
       * Current position in the list of children.
       */
      private int index;

      /* (non-Javadoc)
       * @see java.util.Enumeration#hasMoreElements()
       */
      @Override
      public boolean hasMoreElements() {
        return index < childCount;
      }

      /* (non-Javadoc)
       * @see java.util.Enumeration#nextElement()
       */
      @Override
      public TreeNode nextElement() {
        synchronized (this) {
          if (index < childCount) {
            return getChildAt(index++);
          }
        }
        throw new NoSuchElementException(Enumeration.class.getSimpleName());
      }
    };
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#clearUserObjects()
   */
  @Override
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
  @Override
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
  @Override
  public List<? extends TreeNode> filter(Filter filter) {
    return filter(filter, false);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#filter(org.sbml.jsbml.util.filters.Filter, boolean)
   */
  @Override
  public List<? extends TreeNode> filter(Filter filter, boolean retainInternalNodes) {
    return filter(filter, retainInternalNodes, false);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#filter(org.sbml.jsbml.util.filters.Filter, boolean, boolean)
   */
  @Override
  public List<? extends TreeNode> filter(Filter filter, boolean retainInternalNodes, boolean prune) {
    List<TreeNode> list = new ArrayList<TreeNode>();
    boolean accepts = filter.accepts(this);
    if (accepts) {
      list.add(this);
      if (prune) {
        return list;
      }
    }

    List<? extends TreeNode> childList;
    TreeNode child;
    for (int i = 0; i < getChildCount(); i++) {
      child = getChildAt(i);
      if (child instanceof TreeNodeWithChangeSupport) {
        childList = ((TreeNodeWithChangeSupport) child).filter(filter, retainInternalNodes, prune);
        if (childList.size() > 0) {
          // Somewhere in the subtree rooted at this child is an interesting node.
          if (!accepts && retainInternalNodes) {
            list.add(this);
            // prevent adding the current node more often than once:
            accepts = true;
          }
          list.addAll(childList);
          if (prune) {
            // Since we found at least one hit, we are done.
            return list;
          }
        }
      }
    }
    return list;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#fireNodeAddedEvent()
   */
  @Override
  public void fireNodeAddedEvent() {
    for (int i = listOfListeners.size() - 1; i >= 0; i--) {
      listOfListeners.get(i).nodeAdded(this);
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#fireNodeRemovedEvent()
   */
  @Override
  public void fireNodeRemovedEvent() {
    TreeNode previousParent = getParent();
    parent = null;

    if (getTreeNodeChangeListenerCount() > 0) {
      // memorize all listeners before deleting them from this object.
      List<TreeNodeChangeListener> listOfTreeNodeChangeListeners = new ArrayList<TreeNodeChangeListener>(listOfListeners);
      // remove all changeListeners
      removeAllTreeNodeChangeListeners();

      for (TreeNodeChangeListener listener : listOfTreeNodeChangeListeners) {
        listener.nodeRemoved(new TreeNodeRemovedEvent(this, previousParent));
      }
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#firePropertyChange(java.lang.String, java.lang.Object, java.lang.Object)
   */
  @Override
  public void firePropertyChange(String propertyName, Object oldValue,
    Object newValue) {

    // TODO - if the property is of type TreeNode, we should set the parent of the new value
    // and unset the parent of the oldValue ??

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
        boolean userObjectChange = propertyName.equals(TreeNodeChangeEvent.userObject);
        boolean parentSBMLChange = propertyName.equals(TreeNodeChangeEvent.parentSBMLObject);
        boolean newValIsTreeNode = newValue instanceof TreeNodeWithChangeSupport;
        boolean oldValIsTreeNode = oldValue instanceof TreeNodeWithChangeSupport;
        if ((changeType == 0) && newValIsTreeNode && !userObjectChange && !parentSBMLChange) {
          /*
           * This last condition is important because otherwise a treeNodeAdded
           * event would be triggered, i.e., the attempt to re-add the parent
           * object to the model. However, this is just a property change,
           * because the parent is already in the model.
           */
          ((TreeNodeWithChangeSupport) newValue).fireNodeAddedEvent();
        } else if ((changeType == 1) && oldValIsTreeNode && !userObjectChange && !parentSBMLChange) {
          ((TreeNodeWithChangeSupport) oldValue).fireNodeRemovedEvent();
        } else {
          // TODO: check if notifying and updating the metaId is necessary
          // because of the method AbstractSBase.setThisAsParentSBMLObject
          if (oldValIsTreeNode && newValIsTreeNode) {
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
  @Override
  public int getIndex(TreeNode node) {
    return indexOf(this, node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#getListOfTreeNodeChangeListeners()
   */
  @Override
  public List<TreeNodeChangeListener> getListOfTreeNodeChangeListeners() {
    return listOfListeners;
  }

  /**
   * Returns the number of child elements of this {@link TreeNode}.
   * 
   * @return the number of children TreeNodes the receiver contains.
   * @libsbml.deprecated you could use {@link #getChildCount()}
   */
  public int getNumChildren() {
    return getChildCount();
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getParent()
   */
  @Override
  public TreeNode getParent() {
    return parent;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#getRoot()
   */
  @Override
  public TreeNode getRoot() {
    if (isRoot()) {
      return this;
    }
    TreeNode parent = getParent();
    if (parent instanceof TreeNodeWithChangeSupport) {
      return ((TreeNodeWithChangeSupport) parent).getRoot();
    }
    while (parent.getParent() != null) {
      parent = parent.getParent();
    }
    return parent;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#getTreeNodeChangeListenerCount()
   */
  @Override
  public int getTreeNodeChangeListenerCount() {
    return listOfListeners != null ? listOfListeners.size() : 0;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#getUserObject(java.lang.Object)
   */
  @Override
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
  @Override
  public boolean isLeaf() {
    return getChildCount() == 0;
  }

  /**
   * Returns true if JSBML is in the process of reading a model through the
   * {@link SBMLReader} methods.
   * 
   * @return true if JSBML is in the process of reading a model
   * @see #isInvalidSBMLAllowed()
   */
  protected boolean isReadingInProgress() {
    return isInvalidSBMLAllowed();
  }

  /**
   * Returns true if JSBML is set so that invalid SBML is allowed to be set,
   * for example, for the purpose of reading a file or doing validation.
   * 
   * @return true if JSBML is in the process of reading a model
   */
  protected boolean isInvalidSBMLAllowed() {
    if (isSetUserObjects()
        && userObjectKeySet().contains(JSBML.ALLOW_INVALID_SBML)) {
      return true;
    }

    return false;
  }

  /**
   * Returns true if JSBML is in the process of cloning an element.
   * 
   * <p>In this case, the attribute validation cannot happen as when cloning
   * the parent is not set. And without parent, we cannot get
   * the {@link Model} or {@link SBMLDocument} to check all the maps there and more.</p>
   * 
   * @return true if JSBML is in the process of cloning an element.
   */
  protected boolean isCloningInProgress() {
    if (isSetUserObjects()
        && userObjectKeySet().contains(JSBML.CLONING_IN_PROGRESS)) {
      return true;
    }

    return false;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#isRoot()
   */
  @Override
  public boolean isRoot() {
    return !isSetParent();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#isSetParent()
   */
  @Override
  public boolean isSetParent() {
    return parent != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#isSetUserObjects()
   */
  @Override
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
  @Override
  public void putUserObject(Object key, Object userObject) {
    if (userObjects == null) {
      userObjects = new HashMap<Object, Object>();
    }
    Object oldObject = userObjects.put(key, userObject);
    // Avoid that by mistake a nodeAdded event is generated!
    Map.Entry<Object, Object> oldEntry = new AbstractMap.SimpleImmutableEntry<Object, Object>(key, oldObject);
    Map.Entry<Object, Object> newEntry = new AbstractMap.SimpleImmutableEntry<Object, Object>(key, userObject);
    firePropertyChange(TreeNodeChangeEvent.userObject, oldEntry, newEntry);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#removeAllTreeNodeChangeListeners()
   */
  @Override
  public void removeAllTreeNodeChangeListeners() {
    listOfListeners.clear();
  }

  /**
   * This method is designed to be overridden for non-independent child nodes
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#removeFromParent()
   */
  @Override
  public boolean removeFromParent() {

    // Check if this is a root node, return false if it is root
    if (isRoot()) {
      return false;
    }

    // Check if the object's parent is a list, then clear from list
    if (parent instanceof List<?>) {
      List<?> parentList = (List<?>) parent;
      parentList.remove(this);
      if (!(parentList instanceof TreeNodeWithChangeSupport)) {
        // avoid doing it twice.
        fireNodeRemovedEvent();
      }
    } else {
      try {
        Class<?> clazz = parent.getClass();
        Method method = clazz.getMethod("removeChild", int.class);
        if (method != null) {
          Object result = method.invoke(parent, parent.getIndex(this));
          if ((result == null) && !method.getReturnType().equals(Void.class)) {
            return false;
          } else if (result.getClass() == Boolean.class) {
            return ((Boolean) result).booleanValue();
          }
        }
      } catch (Throwable t) {
        logger.debug(t.getMessage(), t);
        /* If the object's parent is not a list, mimic "unset" methods
         * using reflection
         */
        Class<?> clazz = parent.getClass();
        Field fields[] = clazz.getDeclaredFields();
        for (Field field : fields) {
          try {
            boolean isAccessible = true;
            if (!field.isAccessible()) {
              field.setAccessible(isAccessible);
              isAccessible = false;
            }
            Object object = field.get(parent);
            if (object == this) {
              field.set(parent, null);
            }
            if (!isAccessible) {
              // reset original state
              field.setAccessible(isAccessible);
            }
          } catch (IllegalAccessException exc) {
            // ignore
            logger.debug(exc.getMessage(), exc);
          }
        }
        fireNodeRemovedEvent();
      }
    }
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#removeTreeNodeChangeListener(org.sbml.jsbml.util.TreeNodeChangeListener)
   */
  @Override
  public void removeTreeNodeChangeListener(TreeNodeChangeListener listener) {
    removeTreeNodeChangeListener(listener, true);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#removeAllTreeNodeChangeListeners(boolean)
   */
  @Override
  public void removeAllTreeNodeChangeListeners(boolean recursive) {
    removeAllTreeNodeChangeListeners();
    if (recursive) {
      Enumeration<TreeNode> children = children();
      while (children.hasMoreElements()) {
        TreeNode node = children.nextElement();
        if (node instanceof TreeNodeWithChangeSupport) {
          ((TreeNodeWithChangeSupport) node).removeAllTreeNodeChangeListeners(recursive);
        }
      }
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#removeTreeNodeChangeListener(org.sbml.jsbml.util.TreeNodeChangeListener, boolean)
   */
  @Override
  public void removeTreeNodeChangeListener(TreeNodeChangeListener listener, boolean recursive) {
    listOfListeners.remove(listener);
    if (recursive) {
      Enumeration<TreeNode> children = children();
      while (children.hasMoreElements()) {
        TreeNode node = children.nextElement();
        if (node instanceof TreeNodeWithChangeSupport) {
          ((TreeNodeWithChangeSupport) node).removeTreeNodeChangeListener(listener, recursive);
        }
      }
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#removeUserObject(java.lang.Object)
   */
  @Override
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
  public void setParent(TreeNode parent) {
    TreeNode oldValue = this.parent;
    this.parent = parent;
    if (parent instanceof TreeNodeWithChangeSupport) {
      addAllChangeListeners(((TreeNodeWithChangeSupport) parent).getListOfTreeNodeChangeListeners());
    }
    firePropertyChange(TreeNodeChangeEvent.parentSBMLObject, oldValue, this.parent);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    Class<?> clazz = getClass();

    StringBuilder sb = new StringBuilder();
    sb.append(clazz.getSimpleName());

    // gather attributes
    SortedMap<String, String> done = new TreeMap<String, String>();
    String isSet = "isSet";
    do {
      // Let's analyze all get, set, and isSet methods and see what could be useful
      // information for the toString representation of this object.
      for (Method m : clazz.getDeclaredMethods()) {
        String mName = m.getName();
        if (mName.startsWith(isSet)) {
          String fLUC = mName.substring(isSet.length());
          String fName = fLUC;
          char c = fLUC.charAt(0);
          // The following is needed to avoid strange names such as sBOTerm etc.
          // We try to match the getter/setter names to actually declared fields
          StringBuilder nameBuilder = new StringBuilder();
          nameBuilder.append(Character.toLowerCase(fLUC.charAt(0)));
          for (int i = 1; i < fLUC.length(); i++) {
            c = fLUC.charAt(i);
            if (Character.isUpperCase(c) && Character.isUpperCase(fLUC.charAt(i - 1))) {
              if ((i < fLUC.length() - 1) && (Character.isLowerCase(fLUC.charAt(i + 1)))) {
                nameBuilder.append(c);
              } else {
                nameBuilder.append(Character.toLowerCase(c));
              }
            } else {
              nameBuilder.append(c);
            }
          }
          fName = nameBuilder.toString();

          try {
            if ((clazz.getDeclaredField(fName) != null) && !fName.equals("parent") && !done.containsKey(fName)) {
              Method getter = clazz.getMethod("get" + fLUC);
              Method setter = clazz.getMethod("set" + fLUC, getter.getReturnType());
              if ((setter != null) && (getter != null)) {
                Object o = null;
                if (((Boolean) m.invoke(this)).booleanValue()) {
                  o = getter.invoke(this);
                }
                String value = null;
                if (o != null) {
                  if (o == parent) {
                    // Make sure we never create a recursive dependency!
                    continue;
                  }
                  // We need to have a look at the type of object we got and
                  // provide a few special cases.
                  if (o instanceof String) {
                    // Let actual String values be wrapped in double quotes.
                    value = "\"" + o + "\"";
                  } else if (o instanceof Collection<?>) {
                    fName += ".size";
                    value = Integer.valueOf(((Collection<?>) o).size()).toString();
                  } else if (o.getClass().isArray()) {
                    fName += ".length";
                    value = Integer.valueOf(((Object[]) o).length).toString();
                  } else if (o instanceof TreeNode) {
                    // Other SBase or XMLNode or ASTNode
                    fName += ".isSet";
                    value = "true";
                  } else {
                    value = o.toString();
                  }
                }
                done.put(fName, (value != null) ? value : "");
              }
            }
          } catch (NoSuchMethodException exc) {
          } catch (SecurityException exc) {
          } catch (IllegalArgumentException exc) {
          } catch (IllegalAccessException exc) {
          } catch (InvocationTargetException exc) {
          } catch (NoSuchFieldException exc) {
            // No matter what goes wrong, we are not interested in the error.
          }
        }
      }
      clazz = clazz.getSuperclass();
    } while (!clazz.equals(Object.class));

    // write the attributes
    boolean first = true;
    sb.append(" [");
    for (Map.Entry<String, String> entry : done.entrySet()) {
      if (!first) {
        sb.append(", ");
      } else {
        first = false;
      }
      sb.append(entry.getKey());
      sb.append('=');
      sb.append(entry.getValue());
    }
    sb.append(']');
    return sb.toString();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#userObjectKeySet()
   */
  @Override
  public Set<Object> userObjectKeySet() {
    return userObjects.keySet();
  }

  /**
   * Initializes the state of the object during deserialization.
   * 
   * @param in the object input stream
   * @throws IOException - If any of the usual Input/Output related exceptions occur.
   * @throws ClassNotFoundException - If the class of a serialized object cannot be found.
   * @see Serializable
   */
  private void readObject(java.io.ObjectInputStream in)
      throws IOException, ClassNotFoundException {
    in.defaultReadObject();
    listOfListeners = new ArrayList<TreeNodeChangeListener>();
  }

}
