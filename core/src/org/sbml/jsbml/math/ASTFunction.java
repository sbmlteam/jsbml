/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * 
 * Copyright (C) 2009-2014  jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 5. The Babraham Institute, Cambridge, UK
 * 6. The University of Toronto, Toronto, ON, Canada
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.math;

import java.util.List;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.Model;


/**
 * An Abstract Syntax Tree (AST) node representing a function
 * in a mathematical expression.
 * 
 * @author Victor Kofia
 * @version $Rev$
 * @since 1.0
 * @date May 30, 2014
 */
public class ASTFunction extends AbstractASTNode {

  /**
   * Sets the parent of the node and its children to the given value
   * 
   * @param node the orphan node
   * @param parent the parent
   * @param depth the current depth in the {@link AbstractASTNode} tree.
   *            It is just here for testing purposes to track the depth in the tree
   *            during the process.
   */
  private static void setParentSBMLObject(AbstractASTNode node, MathContainer parent,
    int depth) {
  }

  /**
   * Sets the Parent of the node and its children to the given value
   * 
   * @param node the orphan node
   * @param parent the parent
   */
  static void setParentSBMLObject(ASTFunction node, MathContainer parent) {
    node.setParent(parent);
    setParentSBMLObject(node, parent, 0);
  }

  /**
   * Child nodes.
   */
  protected List<AbstractASTNode> listOfNodes;

  /**
   * The container that holds this ASTFunctionNode.
   */
  protected MathContainer parentSBMLObject;

  /**
   * Creates a new {@link ASTFunction} that lacks a pointer
   * to its containing {@link MathContainer}.
   */
  public ASTFunction() {
    parentSBMLObject = null;
    listOfNodes = null;
  }

  /**
   * Adds a child to this node.
   * 
   * @param child
   *            the node to add as child.
   */
  public void addChild(AbstractASTNode child) {
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractTreeNode#clone()
   */
  @Override
  public TreeNode clone() {
    return null;
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return false;
  }

  /**
   * Gets a child of this node according to an index number.
   * 
   * @param index
   *            the index of the child to get
   * @return the child of this {@link AbstractASTNode} with the given index.
   * @throws IndexOutOfBoundsException
   *             - if the index is out of range (index < 0 || index >=
   *             size()).
   */
  public AbstractASTNode getChild(int index) {
    return listOfNodes.get(index);
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildAt(int)
   */
  @Override
  public TreeNode getChildAt(int childIndex) {
    return null;
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildCount()
   */
  @Override
  public int getChildCount() {
    return 0;
  }


  /**
   * Returns the list of children of the current ASTNode.
   * 
   * @return the list of children of the current ASTNode.
   */
  public List<AbstractASTNode> getListOfNodes() {
    return listOfNodes;
  }

  /**
   * This method is convenient when holding an object nested inside other
   * objects in an SBML model. It allows direct access to the
   * {@link MathContainer}; element containing it. From this
   * {@link MathContainer} even the overall {@link Model} can be accessed.
   * 
   * @return the parent SBML object.
   */
  public MathContainer getParentSBMLObject() {
    return parentSBMLObject;
  }

  /**
   * Inserts the given {@link AbstractASTNode} at point n in the list of children of this
   * {@link AbstractASTNode}. Inserting a child within an {@link AbstractASTNode} may result in an inaccurate
   * representation.
   * 
   * @param n
   *            long the index of the {@link AbstractASTNode} being added
   * @param newChild
   *            {@link ASTNode} to insert as the n<sup>th</sup> child
   */
  public void insertChild(int n, AbstractASTNode newChild) {
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#resetParentSBMLObject(org.sbml.jsbml.math.AbstractASTNode)
   */
  public void resetParentSBMLObject(AbstractASTNode node) {
  }

  /**
   * Resets the parentSBMLObject to null recursively.
   * 
   * @param node
   */
  public void resetParentSBMLObject(ASTFunction node) {
  }

  /**
   * <p>
   * Swaps the children of this {@link AbstractASTNode} with the children of that
   * {@link AbstractASTNode}.
   * </p>
   * <p>
   * Unfortunately, when swapping child nodes, we have to recursively traverse
   * the entire subtrees in order to make sure that all pointers to the parent
   * SBML object are correct. However, this must only be done if the parent SBML
   * object of that differs from the one surrounding this node.
   * </p>
   * <p>
   * In any case, the pointer from each sub-node to its parent must be changed.
   * In contrast to other SBML elements, {@link AbstractASTNode}s have sub-nodes as
   * direct children, i.e., there is no child called 'ListOfNodes'. The
   * {@code setParent} method is also not recursive.
   * </p>
   * <p>
   * However, this might cause many calls to listeners.
   * </p>
   * 
   * @param that
   *        the other node whose children should be used to replace this
   *        node's children
   */
  public void swapChildren(AbstractASTNode that) {
  }

}
