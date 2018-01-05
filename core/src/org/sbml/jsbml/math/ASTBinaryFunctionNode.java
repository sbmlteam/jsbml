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

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * An Abstract Syntax Tree (AST) node representing a function with two
 * parameters
 * 
 * @author Victor Kofia
 * @since 1.0
 */
public class ASTBinaryFunctionNode extends ASTFunction {

  /**
   * 
   */
  private static final long serialVersionUID = -4036869111918452252L;

  /**
   * A {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger(ASTBinaryFunctionNode.class);

  /**
   * Creates a new {@link ASTBinaryFunctionNode}.
   */
  public ASTBinaryFunctionNode() {
    super();
  }

  /**
   * Copy constructor; Creates a deep copy of the given {@link ASTBinaryFunctionNode}.
   * 
   * @param node
   *            the {@link ASTBinaryFunctionNode} to be copied.
   */
  public ASTBinaryFunctionNode(ASTBinaryFunctionNode node) {
    super(node);
  }

  /**
   * Creates a new {@link ASTBinaryFunctionNode} with the specified
   * children.
   * @param leftChild
   * @param rightChild
   */
  public ASTBinaryFunctionNode(ASTNode2 leftChild, ASTNode2 rightChild) {
    this();
    addChild(leftChild);
    addChild(rightChild);
  }

  /**
   * Adds a child to this node.
   * 
   * @param child
   *            the node to add as child.
   * 
   * @throws NullPointerException if the child is null
   * @throws IndexOutOfBoundsException if strictness is set to true
   *            and max child limit has been exceeded
   */
  @Override
  public void addChild(ASTNode2 child) {
    if (! isSetList())  {
      listOfNodes = new ArrayList<ASTNode2>(2);
    }
    if (isStrict() && getChildCount() == 2) {
      throw new IndexOutOfBoundsException("max child limit exceeded");
    }
    if (getChildCount() >= 2) {
      logger.debug("Max child limit exceeded. To add more children " +
          "to ASTBinaryFunctionNode set strictness to false.");
    }
    listOfNodes.add(child);
    ASTFactory.setParentSBMLObject(child, parentSBMLObject);
    child.setParent(this);
    child.fireNodeAddedEvent();
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTFunction#clone()
   */
  @Override
  public ASTBinaryFunctionNode clone() {
    return new ASTBinaryFunctionNode(this);
  }

  /**
   * @throws IndexOutOfBoundsException if strictness is set to true
   *            and max child limit has been exceeded
   */
  @Override
  public ASTNode2 getChildAt(int childIndex) {
    if (!isSetList() || (isSetList() && isStrict() && childIndex > 2)) {
      throw new IndexOutOfBoundsException(childIndex + " < child count");
    }
    return listOfNodes.get(childIndex);
  }

  /**
   * Get the left child of this {@link ASTBinaryFunctionNode}
   * 
   * @return {@link ASTNode2} leftChild
   */
  public ASTNode2 getLeftChild() {
    return getChildAt(0);
  }

  /**
   * Get the right child of this {@link ASTBinaryFunctionNode}
   * 
   * @return {@link ASTNode2} rightChild
   */
  public ASTNode2 getRightChild() {
    return getChildAt(getChildCount() - 1);
  }

  /**
   * Inserts the given {@link ASTNode2} at point n in the list of children of this
   * {@link ASTNode2}. Inserting a child within an {@link ASTNode2} may result in an inaccurate
   * representation.
   * 
   * @param n
   *            long the index of the {@link ASTNode2} being added
   * @param newChild
   *            {@link ASTNode2} to insert as the n<sup>th</sup> child
   * 
   * @throws NullPointerException if the child is null
   * @throws IndexOutOfBoundsException if strictness is set to true
   *           and max child limit has been exceeded
   * 
   */
  @Override
  public void insertChild(int n, ASTNode2 newChild) {
    if (! isSetList()) {
      listOfNodes = new ArrayList<ASTNode2>(2);
    }
    if (isStrict() && getChildCount() == 2) {
      throw new IndexOutOfBoundsException("max child limit exceeded");
    }
    if (getChildCount() == 2) {
      logger.debug("Max child limit exceeded. To add more children " +
          "to ASTBinaryFunctionNode set strictness to false.");
    }
    listOfNodes.add(n, newChild);
    ASTFactory.setParentSBMLObject(newChild, parentSBMLObject);
    newChild.setParent(this);
  }

  /**
   * Return true iff left child has been set
   * 
   * @return boolean
   */
  public boolean isSetLeftChild() {
    return getChildCount() > 0;
  }

  /**
   * Return true iff right child has been set
   * 
   * @return boolean
   */
  public boolean isSetRightChild() {
    return getChildCount() > 1;
  }

  /**
   * Adds the given node as a child of this {@link ASTBinaryFunctionNode}.
   * This method adds child nodes from right to left.
   * 
   * @param child
   *            an {@code ASTNode2}
   * 
   * @throws NullPointerException if the child is null
   * @throws IndexOutOfBoundsException if strictness is set to true
   *           and max child limit has been exceeded
   * 
   */
  @Override
  public void prependChild(ASTNode2 child) {
    if (! isSetList()) {
      listOfNodes = new ArrayList<ASTNode2>(2);
    }
    if (isStrict() && getChildCount() == 2) {
      throw new IndexOutOfBoundsException("max child limit exceeded");
    }
    if (getChildCount() == 2) {
      logger.debug("Max child limit exceeded. To add more children " +
          "to ASTBinaryFunctionNode set strictness to false.");
    }
    listOfNodes.add(0, child);
    ASTFactory.setParentSBMLObject(child, parentSBMLObject);
    child.setParent(this);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTFunction#removeChild(int)
   */
  @Override
  public boolean removeChild(int n) {
    if (! isSetList()) {
      listOfNodes = new ArrayList<ASTNode2>(2);
    }
    if ((getChildCount() > n) && (n >= 0)) {
      ASTNode2 removed = listOfNodes.remove(n);
      removed.unsetParentSBMLObject();
      removed.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /**
   * Set the left child of this {@link ASTBinaryFunctionNode}
   * 
   * @param child {@link ASTNode2}
   */
  public void setLeftChild(ASTNode2 child) {
    if (isSetLeftChild()) {
      replaceChild(0, child);
      return;
    }
    addChild(child);
  }

  /**
   * Set the right child of this {@link ASTBinaryFunctionNode}
   * 
   * @param child {@link ASTNode2}
   */
  public void setRightChild(ASTNode2 child) {
    if (isSetRightChild()) {
      replaceChild(getChildCount() - 1, child);
      return;
    }
    addChild(child);
  }

  /**
   * <p>
   * Swaps the children of this {@link ASTFunction} with the children of that
   * {@link ASTFunction}.
   * </p>
   * <p>
   * Unfortunately, when swapping child nodes, we have to recursively traverse
   * the entire subtrees in order to make sure that all pointers to the parent
   * SBML object are correct. However, this must only be done if the parent SBML
   * object of that differs from the one surrounding this node.
   * </p>
   * <p>
   * In any case, the pointer from each sub-node to its parent must be changed.
   * In contrast to other SBML elements, {@link ASTFunction}s have sub-nodes as
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
   * 
   * @throws IndexOutOfBoundsException if strictness is set to true
   *           and max child limit has been exceeded
   * 
   */
  @Override
  public void swapChildren(ASTFunction that) {
    if (isStrict() && that.getChildCount() > 2) {
      throw new IndexOutOfBoundsException("max child limit exceeded");
    }
    if (that.getChildCount() > 2) {
      logger.debug("Max child limit exceeded. To add more children " +
          "to ASTBinaryFunctionNode set strictness to false.");
    }
    List<ASTNode2> swap = that.listOfNodes;
    that.listOfNodes = listOfNodes;
    listOfNodes = swap;
  }

}
