/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * 
 * Copyright (C) 2009-2015 jointly by the following organizations:
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
 * @version $Rev$
 * @since 1.0
 * @date Jan 20, 2014
 */
public class CSGHomogeneousTransformation extends CSGTransformation {

  /**
   * 
   */
  private static final long serialVersionUID = 1247170964151052926L;

  /**
   * 
   */
  private TransformationComponent forwardTransformation;
  /**
   * 
   */
  private TransformationComponent reverseTransformation;


  /**
   * 
   */
  public CSGHomogeneousTransformation() {
    super();
  }


  /**
   * @param csght
   */
  public CSGHomogeneousTransformation(CSGHomogeneousTransformation csght) {
    super(csght);

    if (csght.isSetForwardTransformation()) {
      setForwardTransformation(csght.getForwardTransformation().clone());
    }

    if (csght.isSetReverseTransformation()) {
      setReverseTransformation(csght.getReverseTransformation().clone());
    }
  }


  /**
   * @param level
   * @param version
   */
  public CSGHomogeneousTransformation(int level, int version) {
    super(level, version);
  }


  /**
   * 
   * @param id
   * @param level
   * @param version
   */
  public CSGHomogeneousTransformation(String id, int level, int version) {
    super(id, level, version);
  }


  @Override
  public CSGHomogeneousTransformation clone() {
    return new CSGHomogeneousTransformation(this);
  }

  @Override
  public int hashCode() {
    final int prime = 1697;
    int hashCode = super.hashCode();
    if (isSetForwardTransformation()) {
      hashCode += prime * getForwardTransformation().hashCode();
    }
    if (isSetReverseTransformation()) {
      hashCode += prime * getReverseTransformation().hashCode();
    }
    return hashCode;
  }
  
  @Override
  public boolean equals(Object object) {
    boolean equal = super.equals(object);
    if (equal) {
      CSGHomogeneousTransformation csght = (CSGHomogeneousTransformation) object;

      equal &= csght.isSetForwardTransformation() == isSetForwardTransformation();
      if (equal && isSetForwardTransformation()) {
        equal &= csght.getForwardTransformation().equals(getForwardTransformation());
      }

      equal &= csght.isSetReverseTransformation() == isSetReverseTransformation();
      if (equal && isSetReverseTransformation()) {
        equal &= csght.getReverseTransformation().equals(getReverseTransformation());
      }
    }
    return equal;
  }


  /**
   * Returns the value of forwardTransformation
   *
   * @return the value of forwardTransformation
   */
  public TransformationComponent getForwardTransformation() {
    if (isSetForwardTransformation()) {
      return forwardTransformation;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.forwardTransformation, this);
  }


  /**
   * Returns whether forwardTransformation is set
   *
   * @return whether forwardTransformation is set
   */
  public boolean isSetForwardTransformation() {
    return forwardTransformation != null;
  }


  /**
   * Sets the value of forwardTransformation
   * @param forwardTransformation
   */
  public void setForwardTransformation(TransformationComponent forwardTransformation) {
    TransformationComponent oldForwardTransformation = this.forwardTransformation;
    this.forwardTransformation = forwardTransformation;
    firePropertyChange(SpatialConstants.forwardTransformation, oldForwardTransformation, this.forwardTransformation);
  }


  /**
   * Unsets the variable forwardTransformation
   *
   * @return {@code true}, if forwardTransformation was set before,
   *         otherwise {@code false}
   */
  public boolean unsetForwardTransformation() {
    if (isSetForwardTransformation()) {
      TransformationComponent oldForwardTransformation = forwardTransformation;
      forwardTransformation = null;
      firePropertyChange(SpatialConstants.forwardTransformation, oldForwardTransformation, forwardTransformation);
      return true;
    }
    return false;
  }


  /**
   * Returns the value of reverseTransformation
   *
   * @return the value of reverseTransformation
   */
  public TransformationComponent getReverseTransformation() {
    if (isSetReverseTransformation()) {
      return reverseTransformation;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.reverseTransformation, this);
  }


  /**
   * Returns whether reverseTransformation is set
   *
   * @return whether reverseTransformation is set
   */
  public boolean isSetReverseTransformation() {
    return reverseTransformation != null;
  }


  /**
   * Sets the value of reverseTransformation
   * @param reverseTransformation
   */
  public void setReverseTransformation(TransformationComponent reverseTransformation) {
    TransformationComponent oldReverseTransformation = this.reverseTransformation;
    this.reverseTransformation = reverseTransformation;
    firePropertyChange(SpatialConstants.reverseTransformation, oldReverseTransformation, this.reverseTransformation);
  }


  /**
   * Unsets the variable reverseTransformation
   *
   * @return {@code true}, if reverseTransformation was set before,
   *         otherwise {@code false}
   */
  public boolean unsetReverseTransformation() {
    if (isSetReverseTransformation()) {
      TransformationComponent oldReverseTransformation = reverseTransformation;
      reverseTransformation = null;
      firePropertyChange(SpatialConstants.reverseTransformation, oldReverseTransformation, reverseTransformation);
      return true;
    }
    return false;
  }


  @Override
  public boolean getAllowsChildren() {
    return true;
  }


  @Override
  public int getChildCount() {
    int count = super.getChildCount();
    if (isSetForwardTransformation()) {
      count++;
    }
    if (isSetReverseTransformation()) {
      count++;
    }
    return count;
  }


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
    if (isSetForwardTransformation()) {
      if (pos == index) {
        return getForwardTransformation();
      }
      pos++;
    }
    if (isSetReverseTransformation()) {
      if (pos == index) {
        return getReverseTransformation();
      }
      pos++;
    }
    throw new IndexOutOfBoundsException(MessageFormat.format(
      resourceBundle.getString("IndexExceedsBoundsException"), index,
      Math.min(pos, 0)));
  }


  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("CSGHomogeneousTransformation [forwardTransformation=");
    builder.append(forwardTransformation);
    builder.append(", reverseTransformation=");
    builder.append(reverseTransformation);
    builder.append("]");
    return builder.toString();
  }



}
