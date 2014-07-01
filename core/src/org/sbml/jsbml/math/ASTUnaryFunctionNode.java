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

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;



/**
 * An Abstract Syntax Tree (AST) node representing a function with only one
 * parameter
 * 
 * @author Victor Kofia
 * @version $Rev$
 * @since 1.0
 * @date May 30, 2014
 */
public class ASTUnaryFunctionNode extends ASTFunction {

  /**
   * 
   */
  private static final long serialVersionUID = -8088831456874690229L;
  
  /**
   * A {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger(ASTBinaryFunctionNode.class);

  /**
   * Creates a new {@link ASTUnaryFunctionNode}.
   */
  public ASTUnaryFunctionNode() {
    super();
  }

  /**
   * Copy constructor; Creates a deep copy of the given {@link ASTUnaryFunctionNode}.
   * 
   * @param node
   *            the {@link ASTUnaryFunctionNode} to be copied.
   */
  public ASTUnaryFunctionNode(ASTUnaryFunctionNode node) {
    super(node);
  }
  
  /**
   * Adds a child to this node.
   * 
   * @param child
   *            the node to add as child.
   */
  public void addChild(ASTNode2 child) {
    if (! isSetList())  {
      listOfNodes = new ArrayList<ASTNode2>();
    } 
    if (isStrict() && getChildCount() > 0) {
      logger.warn("Max child limit exceeded. To add more children " +
                  "to ASTUnaryFunctionNode set strictness to false.");
      throw new IndexOutOfBoundsException("max child limit exceeded");
    }
    listOfNodes.add(child);
    setParentSBMLObject(child, parentSBMLObject, 0);
    child.setParent(this);
    child.fireNodeAddedEvent();
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTFunction#clone()
   */
  @Override
  public ASTUnaryFunctionNode clone() {
    return new ASTUnaryFunctionNode(this);
  }

  /**
   * Get the child of this node
   * 
   * @return {@link ASTNode2} child
   */
  public ASTNode2 getChild() {
    return getChildAt(0);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTFunction#getChildAt(int)
   */
  @Override
  public ASTNode2 getChildAt(int childIndex) {
    if (!isSetList() || (isSetList() && isStrict() && childIndex > 1)) {
      throw new IndexOutOfBoundsException(childIndex + " < child count");
    }
    return listOfNodes.get(childIndex);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTFunction#insertChild(int, org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public void insertChild(int n, ASTNode2 newChild) {
    if (! isSetList()) {
      listOfNodes = new ArrayList<ASTNode2>();
    }
    if (isStrict() && (getChildCount() + 1) > 1) {
      logger.warn("Max child limit exceeded. To add more children " +
                  "to ASTUnaryFunctionNode set strictness to false.");
      throw new IndexOutOfBoundsException("max child limit exceeded");
    }
    listOfNodes.add(n, newChild);
    setParentSBMLObject(newChild, parentSBMLObject, 0);
    newChild.setParent(this);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTFunction#prependChild(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public void prependChild(ASTNode2 child) {
    if (! isSetList()) {
      listOfNodes = new ArrayList<ASTNode2>();
    }
    if (isStrict() && (getChildCount() + 1) > 1) {
      logger.warn("Max child limit exceeded. To add more children " +
                  "to ASTUnaryFunctionNode set strictness to false.");
      throw new IndexOutOfBoundsException("max child limit exceeded");
    }
    listOfNodes.add(0, child);
    setParentSBMLObject(child, parentSBMLObject, 0);
    child.setParent(this);
  }
  
  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTFunction#removeChild(int)
   */
  @Override
  public boolean removeChild(int n) {
    if (! isSetList()) {
      listOfNodes = new ArrayList<ASTNode2>();
    }
    int childCount = isStrict() ? 1 : getChildCount();
    if ((childCount > n) && (n >= 0)) {
      ASTNode2 removed = listOfNodes.remove(n);
      removed.unsetParentSBMLObject();
      removed.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /**
   * Set the child of this node
   * 
   * @param {@link ASTNode2} child
   */
  public void setChild(ASTNode2 child) {
    replaceChild(0, child);
  }
  
  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTFunction#swapChildren(org.sbml.jsbml.math.ASTFunction)
   */
  @Override
  public void swapChildren(ASTFunction that) {
    if (isStrict() && that.getChildCount() > 1) {
      logger.warn("Max child limit exceeded. To add more children " +
          "to ASTUnaryFunctionNode set strictness to false.");
      throw new IndexOutOfBoundsException("max child limit exceeded");
    }
    List<ASTNode2> swap = that.listOfNodes;
    that.listOfNodes = listOfNodes;
    listOfNodes = swap;
  }
  
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ASTUnaryFunctionNode [strict=");
    builder.append(strict);
    builder.append(", type=");
    builder.append(type);
    builder.append("]");
    return builder.toString();
  }

}
