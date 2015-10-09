/*
 * Copyright (c) 1997, 2005, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package javax.swing.tree;

import java.util.Enumeration;

/**
 * Android-wrapper for the original Java TreeNode class that
 * defines the requirements for an object that can be used as a
 * tree node in any Tree.
 * @author Clemens Wrzodek
 * Original authors:
 * @author Rob Davis
 * @author Scott Violet
 * 
 */
public interface TreeNode {
  /**
   * Returns the child {@code TreeNode} at index 
   * {@code childIndex}.
   */
  TreeNode getChildAt(int childIndex);
  
  /**
   * Returns the number of children {@code TreeNode}s the receiver
   * contains.
   */
  int getChildCount();
  
  /**
   * Returns the parent {@code TreeNode} of the receiver.
   */
  TreeNode getParent();
  
  /**
   * Returns the index of {@code node} in the receivers children.
   * If the receiver does not contain {@code node}, -1 will be
   * returned.
   */
  int getIndex(TreeNode node);
  
  /**
   * Returns {@code true} if the receiver allows children.
   */
  boolean getAllowsChildren();
  
  /**
   * Returns {@code true} if the receiver is a leaf.
   */
  boolean isLeaf();
  
  /**
   * Returns the children of the receiver as an {@code Enumeration}.
   */
  @SuppressWarnings("rawtypes")
  Enumeration children();  
}
