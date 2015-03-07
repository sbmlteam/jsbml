/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * 
 * Copyright (C) 2009-2015  jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.fbc;

import java.text.MessageFormat;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.AbstractSBase;

/**
 * @author Andreas Dr&auml;ger
 * @version $Rev$
 * @since 1.1
 * @date 06.03.2015
 */
public abstract class LogicalOperator extends AbstractSBase implements Association {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 7724258767809732147L;

  /**
   * 
   */
  private Association leftChild;
  /**
   * 
   */
  private Association rightChild;

  /**
   * Returns the value of {@link #rightChild}.
   *
   * @return the value of {@link #rightChild}.
   */
  public Association getRightChild() {
    return rightChild;
  }

  /**
   * Returns whether {@link #rightChild} is set.
   *
   * @return whether {@link #rightChild} is set.
   */
  public boolean isSetRightChild() {
    return rightChild != null;
  }

  /**
   * Sets the value of rightChild
   * @param rightChild the value of rightChild to be set.
   */
  public void setRightChild(Association rightChild) {
    Association oldRightField = this.rightChild;
    this.rightChild = rightChild;
    firePropertyChange(FBCConstants.rightChild, oldRightField, this.rightChild);
  }

  /**
   * Unsets the variable rightChild.
   *
   * @return {@code true} if rightChild was set before,
   *         otherwise {@code false}.
   */
  public boolean unsetRightField() {
    if (isSetRightChild()) {
      Association oldRightField = rightChild;
      rightChild = null;
      firePropertyChange(FBCConstants.rightChild, oldRightField, rightChild);
      return true;
    }
    return false;
  }

  /**
   * Returns the value of {@link #leftChild}.
   *
   * @return the value of {@link #leftChild}.
   */
  public Association getLeftChild() {
    return leftChild;
  }


  /**
   * Returns whether {@link #leftChild} is set.
   *
   * @return whether {@link #leftChild} is set.
   */
  public boolean isSetLeftChild() {
    return leftChild != null;
  }


  /**
   * Sets the value of leftChild
   * @param leftChild the value of leftChild to be set.
   */
  public void setLeftChild(Association leftChild) {
    Association oldLeftChild = this.leftChild;
    this.leftChild = leftChild;
    firePropertyChange(FBCConstants.leftChild, oldLeftChild, this.leftChild);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildCount()
   */
  @Override
  public int getChildCount() {
    int count = super.getChildCount();
    if (isSetLeftChild()) {
      count++;
    }
    if (isSetRightChild()) {
      count++;
    }
    return count;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
   */
  @Override
  public TreeNode getChildAt(int index) {
    if (index < 0) {
      throw new IndexOutOfBoundsException(index + " < 0");
    }
    int count = super.getChildCount(), pos = 0;
    if (index < count) {
      return super.getChildAt(index);
    } else {
      index -= count;
    }
    if (isSetLeftChild()) {
      if (pos == index) {
        return getLeftChild();
      }
      pos++;
    }
    if (isSetRightChild()) {
      if (pos == index) {
        return getRightChild();
      }
      pos++;
    }
    throw new IndexOutOfBoundsException(
      MessageFormat.format("Index {0,number,integer} >= {1,number,integer}",
        index, Math.min(pos, 0)));
  }

  /**
   * Unsets the variable leftChild.
   *
   * @return {@code true} if leftChild was set before,
   *         otherwise {@code false}.
   */
  public boolean unsetLeftChild() {
    if (isSetLeftChild()) {
      Association oldLeftChild = leftChild;
      leftChild = null;
      firePropertyChange(FBCConstants.leftChild, oldLeftChild, leftChild);
      return true;
    }
    return false;
  }

  /**
   * 
   */
  public LogicalOperator() {
    super();
  }

  /**
   * @param level
   * @param version
   */
  public LogicalOperator(int level, int version) {
    super(level, version);
  }

  /**
   * @param association
   */
  public LogicalOperator(LogicalOperator association) {
    super(association);
    setNamespace(association.getNamespace());
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public abstract LogicalOperator clone();

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(getClass().getSimpleName());
    builder.append(" [");
    if (isSetMetaId()) {
      builder.append("metaid=");
      builder.append(getMetaId());
    }
    if (isSetLeftChild()) {
      if (isSetMetaId()) {
        builder.append(", ");
      }
      builder.append("leftChild=");
      builder.append(leftChild.toString());
    }
    if (isSetLeftChild()) {
      if (isSetMetaId() || isSetLeftChild()) {
        builder.append(", ");
      }
      builder.append("rightChild=");
      builder.append(rightChild.toString());
    }
    builder.append("]");
    return builder.toString();
  }

}
