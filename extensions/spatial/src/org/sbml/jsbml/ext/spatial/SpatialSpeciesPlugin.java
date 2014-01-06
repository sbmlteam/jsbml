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
public class SpatialSpeciesPlugin extends AbstractSBasePlugin {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 1636970127352490380L;

  /**
   * 
   */
  private Boolean isSpatial;

  /**
   * 
   */
  public SpatialSpeciesPlugin() {
    // TODO Auto-generated constructor stub
  }



  /**
   * @param sb
   */
  public SpatialSpeciesPlugin(SpatialSpeciesPlugin sb) {
    super(sb);
    if (sb.isSetIsSpatial()) {
      isSpatial = Boolean.valueOf(sb.isSpatial());
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public SpatialSpeciesPlugin clone() {
    return new SpatialSpeciesPlugin(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equal = super.equals(object);
    if (equal) {
      SpatialSpeciesPlugin ss = (SpatialSpeciesPlugin) object;
      equal &= ss.isSetIsSpatial() == isSetIsSpatial();
      if (equal && isSetIsSpatial()) {
        equal &= ss.isSpatial() == isSpatial();
      }
    }
    return equal;
  }

  /**
   * @return the isSpatial
   */
  public boolean getIsSpatial() {
    return isSpatial;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 997;
    int hashCode = super.hashCode();
    if (isSetIsSpatial()) {
      hashCode += prime * isSpatial.hashCode();
    }
    return hashCode;
  }

  /**
   * @return
   */
  public boolean isSetIsSpatial() {
    return isSpatial != null;
  }

  /**
   * 
   * @return
   * @see #getIsSpatial()
   */
  public boolean isSpatial() {
    return getIsSpatial();
  }

  /**
   * @param isSpatial the isSpatial to set
   */
  public void setIsSpatial(boolean isSpatial) {
    this.isSpatial = Boolean.valueOf(isSpatial);
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
