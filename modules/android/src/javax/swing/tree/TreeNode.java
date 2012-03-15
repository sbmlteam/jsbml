
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
   * Returns the child <code>TreeNode</code> at index 
   * <code>childIndex</code>.
   */
  TreeNode getChildAt(int childIndex);
  
  /**
   * Returns the number of children <code>TreeNode</code>s the receiver
   * contains.
   */
  int getChildCount();
  
  /**
   * Returns the parent <code>TreeNode</code> of the receiver.
   */
  TreeNode getParent();
  
  /**
   * Returns the index of <code>node</code> in the receivers children.
   * If the receiver does not contain <code>node</code>, -1 will be
   * returned.
   */
  int getIndex(TreeNode node);
  
  /**
   * Returns true if the receiver allows children.
   */
  boolean getAllowsChildren();
  
  /**
   * Returns true if the receiver is a leaf.
   */
  boolean isLeaf();
  
  /**
   * Returns the children of the receiver as an <code>Enumeration</code>.
   */
  @SuppressWarnings("rawtypes")
  Enumeration children();  
}
