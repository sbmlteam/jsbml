/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2014 jointly by the following organizations:
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
package org.sbml.jsbml.ext.spatial;

import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.ext.AbstractSBasePlugin;

/**
 * @author Andreas Dr&auml;ger
 * @since 1.0
 * @version $Rev$
 */
public class SpatialReactionPlugin extends AbstractSBasePlugin {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -2154884901226244123L;

  /**
   * 
   */
  private Boolean isLocal;

  /**
   * 
   */
  public SpatialReactionPlugin() {
    super();
  }



  /**
   * @param sr
   */
  public SpatialReactionPlugin(SpatialReactionPlugin sr) {
    super(sr);
    if (sr.isSetIsLocal()) {
      isLocal = Boolean.valueOf(sr.getIsLocal());
    }
  }

  /**
   * @return
   */
  public boolean isSetIsLocal() {
    return isLocal != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public SpatialReactionPlugin clone() {
    return new SpatialReactionPlugin(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equal = super.equals(object);
    if (equal) {
      SpatialReactionPlugin sr = (SpatialReactionPlugin) object;
      equal &= sr.isSetIsLocal() == isSetIsLocal();
      if (equal && isSetIsLocal()) {
        equal &= sr.getIsLocal() == getIsLocal();
      }
    }
    return equal;
  }

  /**
   * @return the isLocal
   */
  public Boolean getIsLocal() {
    return isLocal;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 953;
    int hashCode = super.hashCode();
    if (isSetIsLocal()) {
      hashCode += prime * isLocal.hashCode();
    }
    return hashCode;
  }

  /**
   * @param isLocal the isLocal to set
   */
  public void setIsLocal(Boolean isLocal) {
    this.isLocal = isLocal;
  }



  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    // TODO Auto-generated method stub
    return false;
  }



  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    // TODO Auto-generated method stub
    return null;
  }



  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getElementNamespace()
   */
  @Override
  public String getElementNamespace() {
    // TODO Auto-generated method stub
    return null;
  }



  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getPackageName()
   */
  @Override
  public String getPackageName() {
    // TODO Auto-generated method stub
    return null;
  }



  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getPrefix()
   */
  @Override
  public String getPrefix() {
    // TODO Auto-generated method stub
    return null;
  }



  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getURI()
   */
  @Override
  public String getURI() {
    // TODO Auto-generated method stub
    return null;
  }



  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    // TODO Auto-generated method stub
    return false;
  }



  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildAt(int)
   */
  @Override
  public TreeNode getChildAt(int childIndex) {
    // TODO Auto-generated method stub
    return null;
  }



  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildCount()
   */
  @Override
  public int getChildCount() {
    // TODO Auto-generated method stub
    return 0;
  }

}
