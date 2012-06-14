/*
 * $Id$
 * $URL$
 *
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
package org.sbml.jsbml.util;

import java.util.EventObject;

import javax.swing.tree.TreeNode;


/**
 * A special {@link EventObject} to notify a {@link TreeNodeChangeListener}
 * about a removed node and its previous position within the tree. To this end,
 * this {@link TreeNodeRemovedEvent} contains a pointer to both the actual
 * removed node and its previous parent node. Note that when removing an element
 * from the SBML tree, its pointer to its parent is set to {@code null}.
 * To give an example why information about the previous parent is necessary:
 * Imagine, an instance of {@code speciesReference} has been removed from
 * the SBML tree. After removing it, the model does no longer point to an
 * element with its id. Many {@code reaction}s
 * might have been the origin of this removed node. If a
 * {@link TreeNodeChangeListener} wants to, e.g., undo this change,
 * it is necessary to identify the previous position of the node within the
 * tree. That's why this class has two important
 * methods, namely {@link #getSource()} and {@link #getPreviousParent()}.
 * 
 * @author Andreas Dr&auml;ger
 * @version $Rev$
 * @since 0.8
 * @date 02.06.2012
 */
public class TreeNodeRemovedEvent extends EventObject {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 4860717212990689980L;
  
  /**
   * The previous parent node in the tree, i.e., the node that was the parent of
   * the deleted node (see {@link #getSource()}) before the deletion.
   */
  private TreeNode previosParent;

  /**
   * Creates a new {@link TreeNodeRemovedEvent} that contains information about
   * an element that has just been removed from a tree together with its
   * previous parent node.
   * 
   * @param source
   *        the node that has just been removed from the tree.
   * @param previousParent
   *        the node that was the parent node of the removed element, may be
   *        {@code null} if the node didn't have a parent.
   */
  public TreeNodeRemovedEvent(TreeNode source, TreeNode previousParent) {
    super(source);
    this.previosParent = previousParent;
  }

  /**
   * Clone constructor.
   * 
   * @param treeNodeRemovedEvent
   */
  public TreeNodeRemovedEvent(TreeNodeRemovedEvent treeNodeRemovedEvent) {
    this(treeNodeRemovedEvent.getSource(), treeNodeRemovedEvent.getPreviousParent());
  }

  /* (non-Javadoc)
   * @see java.lang.Object#clone()
   */
  @Override
  protected Object clone() throws CloneNotSupportedException {
    return new TreeNodeRemovedEvent(this);
  }

  /**
   * Access the pointer to the previous parent node of the removed node, may be
   * {@code null}.
   * 
   * @return the previosParent
   * @see #getSource()
   */
  public TreeNode getPreviousParent() {
    return previosParent;
  }

  /* (non-Javadoc)
   * @see java.util.EventObject#getSource()
   */
  @Override
  public TreeNode getSource() {
    return (TreeNode) super.getSource();
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("TreeNodeRemovedEvent [");
    if (source != null) {
      builder.append("source=");
      builder.append(source);
    }
    if (previosParent != null) {
      builder.append("previosParent=");
      builder.append(previosParent);
      builder.append(", ");
    }
    builder.append(']');
    return builder.toString();
  }

}
