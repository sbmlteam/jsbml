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
package org.sbml.jsbml.ext.spatial;

import java.text.MessageFormat;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.PropertyUndefinedError;

/**
 * @author Alex-Thomas
 * @author Piero Dalle Pezze
 * @since 1.0
 */
public abstract class CSGTransformation extends CSGNode {

  /**
   * 
   */
  private static final long serialVersionUID = 5388780141573233538L;
  /**
   * 
   */
  private CSGNode csgNode;

  /**
   * 
   */
  public CSGTransformation() {
    super();
  }


  /**
   * @param csgt
   */
  public CSGTransformation(CSGTransformation csgt) {
    super(csgt);

    if (csgt.isSetCSGNode()) {
      setCSGNode((CSGNode) csgt.getCSGNode().clone());
    }

  }

  /**
   * @param level
   * @param version
   */
  public CSGTransformation(int level, int version) {
    super(level, version);
  }


  /**
   * 
   * @param id
   * @param level
   * @param version
   */
  public CSGTransformation(String id, int level, int version) {
    super(id, level, version);
  }


  @Override
  public boolean equals(Object object) {
    boolean equal = super.equals(object);
    if (equal) {
      CSGTransformation csgt = (CSGTransformation) object;

      equal &= csgt.isSetCSGNode() == isSetCSGNode();
      if (equal && isSetCSGNode()) {
        equal &= csgt.getCSGNode().equals(getCSGNode());
      }
    }
    return equal;
  }


  /**
   * Returns the value of csgNode
   *
   * @return the value of csgNode
   */
  public CSGNode getCSGNode() {
    if (isSetCSGNode()) {
      return csgNode;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.csgNode, this);
  }


  /**
   * Returns whether csgNode is set
   *
   * @return whether csgNode is set
   */
  public boolean isSetCSGNode() {
    return csgNode != null;
  }


  /**
   * Sets the value of csgNode
   * @param csgNode
   */
  public void setCSGNode(CSGNode csgNode) {
    CSGNode oldCSGNode = this.csgNode;
    this.csgNode = csgNode;
    registerChild(csgNode);
    firePropertyChange(SpatialConstants.csgNode, oldCSGNode, this.csgNode);
  }


  /**
   * Unsets the variable csgNode
   *
   * @return {@code true}, if csgNode was set before,
   *         otherwise {@code false}
   */
  public boolean unsetCSGNode() {
    if (isSetCSGNode()) {
      CSGNode oldCSGNode = csgNode;
      csgNode = null;
      firePropertyChange(SpatialConstants.csgNode, oldCSGNode, csgNode);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.AbstractSpatialNamedSBase#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.AbstractSpatialNamedSBase#getChildCount()
   */
  @Override
  public int getChildCount() {
    int count = super.getChildCount();
    if (isSetCSGNode()) {
      count++;
    }
    return count;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.AbstractSpatialNamedSBase#getChildAt(int)
   */
  @Override
  public TreeNode getChildAt(int index) {
    if (index < 0) {
      throw new IndexOutOfBoundsException(MessageFormat.format(
        resourceBundle.getString("IndexSurpassesBoundsException"), index, 0));
    }
    int count = super.getChildCount(), pos = 0;
    if (index < count) {
      return super.getChildAt(index);
    } else {
      index -= count;
    }
    if (isSetCSGNode()) {
      if (pos == index) {
        return getCSGNode();
      }
      pos++;
    }
    throw new IndexOutOfBoundsException(MessageFormat.format(
      resourceBundle.getString("IndexExceedsBoundsException"), index,
      Math.min(pos, 0)));
  }

}
