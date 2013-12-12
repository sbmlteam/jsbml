/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * 
 * Copyright (C) 2009-2013  jointly by the following organizations:
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

import java.text.MessageFormat;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.ext.AbstractSBasePlugin;

/**
 * @author Alex Thomas
 * @version $Rev$
 * @since 1.0
 * @date Dec 10, 2013
 */
public class SpatialModelPlugin extends AbstractSBasePlugin {


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractTreeNode#getParent()
   */
  @Override
  public SBMLDocument getParent() {
    return (SBMLDocument) getExtendedSBase().getParent();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.AbstractSBasePlugin#getParentSBMLObject()
   */
  @Override
  public SBMLDocument getParentSBMLObject() {
    return getParent();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getElementNamespace()
   */
  @Override
  public String getElementNamespace() {
    return SpatialConstants.getNamespaceURI(getLevel(), getVersion());
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getPackageName()
   */
  @Override
  public String getPackageName() {
    return SpatialConstants.packageName;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getPrefix()
   */
  @Override
  public String getPrefix() {
    return SpatialConstants.shortLabel;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getURI()
   */
  @Override
  public String getURI() {
    return getElementNamespace();
  }

  /**
   * Generated serial version ID
   */
  private static final long serialVersionUID = 8449591192464040411L;

  private Geometry geometry;

  /**
   * Returns the value of geometry
   *
   * @return the value of geometry
   */
  public Geometry getGeometry() {
    //TODO: if variable is boolean, create an additional "isVar"
    //TODO: return primitive data type if possible (e.g. int instead of Integer)
    if (isSetGeometry()) {
      return geometry;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.geometry, this);
  }

  /**
   * Creates a new {@link Geometry}, adds it to this object
   * and returns a pointer to it.
   * 
   * @return a new {@link Geometry} object.
   * 
   */
  public Geometry createGeometry() {
    geometry = new Geometry(getLevel(), getVersion());
    geometry.addNamespace(SpatialConstants.getNamespaceURI(getLevel(), getVersion()));
    getExtendedSBase().registerChild(geometry);
    return geometry;
  }

  /**
   * Returns whether geometry is set
   *
   * @return whether geometry is set
   */
  public boolean isSetGeometry() {
    return geometry != null;
  }

  /**
   * Sets the value of geometry
   */
  public void setGeometry(Geometry geometry) {
    Geometry oldGeometry = this.geometry;
    this.geometry = geometry;
    firePropertyChange(SpatialConstants.geometry, oldGeometry, this.geometry);
  }

  /**
   * Unsets the variable geometry
   *
   * @return {@code true}, if geometry was set before,
   *         otherwise {@code false}
   */
  public boolean unsetGeometry() {
    if (isSetGeometry()) {
      Geometry oldGeometry = geometry;
      geometry = null;
      firePropertyChange(SpatialConstants.geometry, oldGeometry, geometry);
      return true;
    }
    return false;
  }
  /**
   * 
   */
  public SpatialModelPlugin() {
    super();
  }

  /**
   * @param model
   */
  public SpatialModelPlugin(Model model) {
    super(model);
  }

  /**
   * @param spatialModelPlugin
   */
  public SpatialModelPlugin(SpatialModelPlugin spatialModelPlugin) {
    super(spatialModelPlugin);
    if (spatialModelPlugin.isSetGeometry()) {
      geometry = spatialModelPlugin.getGeometry().clone();
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.AbstractSBasePlugin#getExtendedSBase()
   */
  @Override
  public Model getExtendedSBase() {
    return (Model) super.getExtendedSBase();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix,
    String value) {
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    return null;
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return true;
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildAt(int)
   */
  @Override
  public SBase getChildAt(int index) {
    if (index < 0) {
      throw new IndexOutOfBoundsException(index + " < 0");
    }
    int pos = 0;

    if (isSetGeometry()) {
      if (pos == index) {
        return getGeometry();
      }
      pos++;
    }

    throw new IndexOutOfBoundsException(MessageFormat.format(
      "Index {0,number,integer} >= {1,number,integer}",
      index, pos));
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildCount()
   */
  @Override
  public int getChildCount() {
    return isSetGeometry() ? 1 : 0;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.AbstractSBasePlugin#clone()
   */
  @Override
  public SpatialModelPlugin clone() {
    return new SpatialModelPlugin(this);
  }

}
