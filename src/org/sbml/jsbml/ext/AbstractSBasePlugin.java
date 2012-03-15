/*
 * $Id$
 * $URL$
 *
 * ---------------------------------------------------------------------------- 
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML> 
 * for the latest version of JSBML and more information about SBML. 
 * 
 * Copyright (C) 2009-2012 jointly by the following organizations: 
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
package org.sbml.jsbml.ext;

import java.util.Enumeration;
import java.util.NoSuchElementException;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.SBase;


/**
 * @author Andreas Dr&auml;ger
 * @author Florian Mittag
 * @version $Rev$
 * @since 1.0
 * @date 28.10.2011
 */
public abstract class AbstractSBasePlugin implements SBasePlugin {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 3741496965840142920L;

  /**
   * 
   */
  protected SBase extendedSBase;
  
  /**
   * 
   */
  public AbstractSBasePlugin() {
    super();
  }


  /**
   * @param extendedSBase
   */
  public AbstractSBasePlugin(SBase extendedSBase) {
    this();
    this.extendedSBase = extendedSBase;
  }

  /**
   * Returns the SBase object that is extended by this plug-in.
   * 
   * @return the SBase object that is extended by this plug-in.
   */
  public SBase getExtendedSBase() {
    return extendedSBase;
  }
  
  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#children()
   */
  public Enumeration<TreeNode> children() {
    return new Enumeration<TreeNode>() {
      /**
       * Total number of children in this enumeration.
       */
      private int childCount = getChildCount();
      /**
       * Current position in the list of children.
       */
      private int index;

      /*
       * (non-Javadoc)
       * 
       * @see java.util.Enumeration#hasMoreElements()
       */
      public boolean hasMoreElements() {
        return index < childCount;
      }

      /*
       * (non-Javadoc)
       * 
       * @see java.util.Enumeration#nextElement()
       */
      public TreeNode nextElement() {
        synchronized (this) {
          if (index < childCount) {
            return getChildAt(index++);
          }
        }
        throw new NoSuchElementException("Enumeration");
      }
    };
  }

  /*
   * (non-Javadoc)
   * @see java.lang.Object#clone()
   */
  @Override
  public abstract SBasePlugin clone();

  /*
   * (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    // Check if the given object is a pointer to precisely the same object:
    if (super.equals(object)) {
      return true;
    }
    // Check if the given object is of identical class and not null: 
    if ((object == null) || (!getClass().equals(object.getClass()))) {
      return false;
    }
    // Check all child nodes recursively:
    if (object instanceof SBase) {
      SBase stn = (SBase) object;
      int childCount = getChildCount();
      boolean equal = stn.isLeaf() == isLeaf();
      /*
       * This is not good because cloned SBase may not point
       * to the same parent as the original and would hence not be equal
       * to the cloned object.
       */
            //  equal &= ((stn.getParent() == null) && isRoot())
            //           || (stn.getParent() == getParent());
      equal &= stn.getChildCount() == childCount;
      if (equal && (childCount > 0)) {
        for (int i = 0; i < childCount; i++) {
          if (!stn.getChildAt(i).equals(getChildAt(i))) {
            return false;
          }
        }
      }
      return equal;
    }
    return false;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getIndex(org.sbml.jsbml.SBase)
   */
  public int getIndex(SBase node) {
    // TODO Auto-generated method stub
    return 0;
  }

  /*
   * (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    // A constant and arbitrary, sufficiently large prime number:
    final int prime = 769;
    /*
     * This method is implemented as suggested in the JavaDoc API
     * documentation of the List interface.
     */
    
    // Compute the initial hashCode based on the name of the actual class.
    int hashCode = getClass().getName().hashCode();
    /*
     * The following start wouldn't work because it will compute the
     * hashCode from the address in memory of the object.
     */
    // int hashCode = super.hashCode();
    
    // Recursively compute the hashCode for each child node:
    TreeNode child;
    for (int i = 0; i < getChildCount(); i++) {
      child = getChildAt(i);
      hashCode = prime * hashCode + (child == null ? 0 : child.hashCode());
    }
    
    return hashCode;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#isLeaf()
   */
  public boolean isLeaf() {    
    return getChildCount() == 0;
  }


}
