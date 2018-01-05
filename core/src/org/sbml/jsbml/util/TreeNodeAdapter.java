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

import static java.text.MessageFormat.format;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.AbstractTreeNode;
import org.sbml.jsbml.ListOf;

/**
 * <p>
 * This class is a wrapper element for any {@link Object} that should be linked
 * into a tree data structure as represented by {@link AbstractTreeNode}, but
 * that might by itself not be an instance of {@link TreeNode}, i.e., not
 * compatible with the remaining tree data structure. In analogy to
 * {@link javax.swing.tree.MutableTreeNode} we call this {@link Object} {@code userObject}
 * (see {@link #getUserObject()} and {@link #setUserObject(Object)}).
 * <p>
 * This wrapper distinguishes the following special cases depending on the type
 * of {@code userObject} when accessing the i<sup>th</sup> child of this
 * {@link TreeNode}. The {@code userObject} is an instance of
 * <ul>
 * <li>{@link TreeNode}: recursive operations are continued at this element</li>
 * <li>{@link Collection}: recursion leads to the i<sup>th</sup> element in the
 * {@link Iterator}</li>
 * <li>{@link Map}: the key set is sorted (only possible if the keys implement
 * the {@link Comparable} interface) and recursion continues at the i<sup>th</sup> key.</li>
 * </ul>
 * 
 * @author Andreas Dr&auml;ger
 * @since 0.8
 */
public class TreeNodeAdapter extends AbstractTreeNode {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -6818272462908634740L;

  /**
   * The object to be wrapped.
   */
  private Object userObject;

  /**
   * Creates a new {@link TreeNode} wrapper for the given {@link #userObject}
   * that will be linked to the given {@link #parent} within an exisiting
   * tree.
   * 
   * @param userObject
   *            the element to be wrapped in a {@link TreeNode}; may be {@code null}.
   * @param parent
   *            the parent {@link TreeNode} of this new node, i.e., the
   *            position within an existing tree where to link this new node.
   *            May be {@code null}.
   */
  public TreeNodeAdapter(Object userObject, TreeNode parent) {
    super();
    this.userObject = userObject;
    this.parent = parent;

    if (parent instanceof TreeNodeWithChangeSupport) {
      TreeNodeWithChangeSupport parentTreeNode = (TreeNodeWithChangeSupport) parent;

      if (parentTreeNode.getListOfTreeNodeChangeListeners() != null) {
        addAllChangeListeners(parentTreeNode.getListOfTreeNodeChangeListeners());
      }
    }
  }

  /**
   * Copy constructor for the given node. Note that the pointer to the parent
   * will not be cloned.
   * 
   * @param node
   */
  public TreeNodeAdapter(TreeNodeAdapter node) {
    super(node);
    userObject = node.getUserObject();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractTreeNode#clone()
   */
  @Override
  public TreeNode clone() {
    return new TreeNodeAdapter(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractTreeNode#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equals = super.equals(object);
    if (equals) {
      TreeNodeAdapter node = (TreeNodeAdapter) object;
      equals &= node.isUserObjectRecursiveDataType() == isUserObjectRecursiveDataType();
      if (equals && isSetUserObject() && !isUserObjectRecursiveDataType()) {
        equals &= node.getUserObject().equals(getUserObject());
      }
    }
    return equals;
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    if (isSetUserObject() && (userObject instanceof Collection<?>)) {
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildAt(int)
   */
  @Override
  public TreeNode getChildAt(int childIndex) {
    if (childIndex < 0) {
      throw new IndexOutOfBoundsException(format(resourceBundle.getString("IndexSurpassesBoundsException"), childIndex, 0));
    }
    if (isSetUserObject()) {
      if (userObject instanceof TreeNode) {
        return ((TreeNode) userObject).getChildAt(childIndex);
      }
      Object child = null;
      if (userObject instanceof Map<?, ?>) {
        Map<?, ?> map = (Map<?, ?>) userObject;
        Object keys[] = map.keySet().toArray();
        Arrays.sort(keys);
        child = map.get(keys[childIndex]);
      }
      if ((userObject instanceof List<?>)) {
        child = ((List<?>) userObject).get(childIndex);
      } else if ((userObject instanceof Collection<?>)) {
        Iterator<?> iterator = ((Collection<?>) userObject).iterator();
        for (int pos = 0; pos < childIndex - 1; pos++) {
          iterator.next();
        }
        child = iterator.next();
      }
      if (child != null) {
        if (child instanceof TreeNode) {
          return (TreeNode) child;
        } else {
          return new TreeNodeAdapter(child, this);
        }
      }
    }
    throw new IndexOutOfBoundsException(format(
      resourceBundle.getString("IndexExceedsBoundsException"),
      childIndex, getChildCount()));
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildCount()
   */
  @Override
  public int getChildCount() {
    if (isSetUserObject()) {
      if (userObject instanceof Collection<?>) {
        return ((Collection<?>) userObject).size();
      }
      if (userObject instanceof Map<?, ?>) {
        return ((Map<?, ?>) userObject).size();
      }
    }
    return 0;
  }

  /**
   * @return
   */
  public Object getUserObject() {
    return userObject;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractTreeNode#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 7;
    int hashCode = super.hashCode();
    if (!isUserObjectRecursiveDataType()) {
      /*
       * We only have to check the user's object in case that it does not
       * belong to the things that would be returned by the getChildAt
       * method.
       */
      hashCode += prime * userObject.hashCode();
    }
    return hashCode;
  }

  /**
   * @return
   */
  public boolean isSetUserObject() {
    return userObject != null;
  }

  /**
   * Checks whether or not the user's object has been set (see
   * {@link #isSetUserObject()}) and if so if it belongs to those elements
   * returned by the method {@link #getChildAt(int)}.
   * 
   * @return {@code true} if the user's object has been defined and
   *         belongs to those classes that are returned by the method
   *         {@link #getChildAt(int)}.
   * @see #getChildAt(int)
   * @see #isSetUserObject()
   */
  public boolean isUserObjectRecursiveDataType() {
    return isSetUserObject()
        && ((userObject instanceof Collection<?>)
            || (userObject instanceof Map<?, ?>) || (userObject instanceof TreeNode));
  }

  /**
   * 
   * @param object
   */
  public void setUserObject(Object object) {
    userObject = object;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractTreeNode#toString()
   */
  @Override
  public String toString() {
    if (isSetUserObject()) {
      if (userObject instanceof Collection<?>) {
        Collection<?> collection = (Collection<?>) userObject;
        if (ListOf.isDebugMode()) {
          return collection.toString();
        } else {
          if (collection.size() > 0) {
            String name = collection.iterator().next().getClass()
                .getSimpleName();
            if (!name.endsWith("s") && !name.toLowerCase().endsWith("information")) {
              name += 's';
            }
            String type = collection instanceof List<?> ? "listOf" : "collectionOf";
            return type + name;
          }
          return collection.getClass().getSimpleName();
        }
      } else if (userObject instanceof Map<?, ?>) {
        Map<?, ?> map = (Map<?, ?>) userObject;
        if (ListOf.isDebugMode()) {
          return map.toString();
        } else {
          if (map.size() > 0) {
            Map.Entry<?, ?> entry = map.entrySet().iterator()
                .next();
            String name = entry.getKey().getClass().getSimpleName()
                + "To"
                + entry.getValue().getClass().getSimpleName();
            return "mapOf" + name;
          }
        }
      }
      return userObject.toString();
    }
    return super.toString();
  }

}
