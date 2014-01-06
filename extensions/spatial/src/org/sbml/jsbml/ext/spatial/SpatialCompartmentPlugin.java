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

import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.ext.AbstractSBasePlugin;

/**
 * 
 * @author Andreas Dr&auml;ger
 * @since 1.0
 * @version $Rev$
 */
public class SpatialCompartmentPlugin extends AbstractSBasePlugin {


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


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractTreeNode#getParent()
   */
  @SuppressWarnings("unchecked")
  @Override
  public ListOf<Compartment> getParent() {
    return (ListOf<Compartment>) getExtendedSBase().getParent();
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.AbstractSBasePlugin#getParentSBMLObject()
   */
  @Override
  public ListOf<Compartment> getParentSBMLObject() {
    return getParent();
  }
  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 1363097365327594433L;

  /**
   * 
   */
  private ListOf<CompartmentMapping> listOfCompartmentMappings;

  /**
   * 
   */
  public SpatialCompartmentPlugin() {
    super();
  }


  /**
   * @param compartment
   */
  public SpatialCompartmentPlugin(Compartment compartment) {
    super(compartment);
    // TODO Auto-generated constructor stub
  }

  /**
   * @param spatialCompartmentPlugin
   */
  public SpatialCompartmentPlugin(
    SpatialCompartmentPlugin spatialCompartmentPlugin) {
    super(spatialCompartmentPlugin);
  }



  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public SpatialCompartmentPlugin clone() {
    return new SpatialCompartmentPlugin(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    // TODO Auto-generated method stub
    return super.equals(object);
  }




  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#hashCode()
   */
  @Override
  public int hashCode() {
    // TODO Auto-generated method stub
    return super.hashCode();
  }

  /**
   * 
   * @return
   */
  public boolean isSetListOfCompartmentMappings() {
    return (listOfCompartmentMappings != null)
        && (listOfCompartmentMappings.size() > 0);
  }



  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix,
    String value) {
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
  public TreeNode getChildAt(int arg0) {
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
