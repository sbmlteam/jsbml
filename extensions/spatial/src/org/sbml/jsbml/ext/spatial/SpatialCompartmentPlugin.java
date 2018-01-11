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

import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.PropertyUndefinedError;

/**
 * @author Alex Thomas
 * @author Andreas Dr&auml;ger
 * @author Piero Dalle Pezze
 * @since 1.0
 */
public class SpatialCompartmentPlugin extends AbstractSpatialSBasePlugin {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 1363097365327594433L;

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractTreeNode#getParent()
   */
  @SuppressWarnings("unchecked")
  @Override
  public ListOf<Compartment> getParent() {
    if (isSetExtendedSBase()) {
      return (ListOf<Compartment>) getExtendedSBase().getParent();
    }

    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.AbstractSBasePlugin#getParentSBMLObject()
   */
  @Override
  public ListOf<Compartment> getParentSBMLObject() {
    return getParent();
  }

  /**
   * 
   */
  private CompartmentMapping compartmentMapping;

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
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.AbstractSBasePlugin#getExtendedSBase()
   */
  @Override
  public Compartment getExtendedSBase() {
    if (isSetExtendedSBase()) {
      return (Compartment) super.getExtendedSBase();
    }

    return null;
  }

  /**
   * @param spatialCompartmentPlugin
   */
  public SpatialCompartmentPlugin(
    SpatialCompartmentPlugin spatialCompartmentPlugin) {
    super(spatialCompartmentPlugin);

    if (spatialCompartmentPlugin.isSetCompartmentMapping()) {
      setCompartmentMapping(spatialCompartmentPlugin.getCompartmentMapping().clone());
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public SpatialCompartmentPlugin clone() {
    return new SpatialCompartmentPlugin(this);
  }

  /**
   * Returns the value of compartmentMapping
   *
   * @return the value of compartmentMapping
   */
  public CompartmentMapping getCompartmentMapping() {
    if (isSetCompartmentMapping()) {
      return compartmentMapping;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.compartmentMapping, this);
  }

  /**
   * Returns whether compartmentMapping is set
   *
   * @return whether compartmentMapping is set
   */
  public boolean isSetCompartmentMapping() {
    return compartmentMapping != null;
  }

  /**
   * Sets the value of compartmentMapping
   * @param compartmentMapping
   */
  public void setCompartmentMapping(CompartmentMapping compartmentMapping) {
    CompartmentMapping oldCompartmentMapping = this.compartmentMapping;
    this.compartmentMapping = compartmentMapping;
    if (isSetExtendedSBase()) {
      getExtendedSBase().registerChild(compartmentMapping);
    }
    firePropertyChange(SpatialConstants.compartmentMapping, oldCompartmentMapping, this.compartmentMapping);
  }

  /**
   * Unsets the variable compartmentMapping
   *
   * @return {@code true}, if compartmentMapping was set before,
   *         otherwise {@code false}
   */
  public boolean unsetCompartmentMapping() {
    if (isSetCompartmentMapping()) {
      CompartmentMapping oldCompartmentMapping = compartmentMapping;
      compartmentMapping = null;
      firePropertyChange(SpatialConstants.compartmentMapping, oldCompartmentMapping, compartmentMapping);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return true;
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildCount()
   */
  @Override
  public int getChildCount() {
    return isSetCompartmentMapping() ? 1 : 0;

  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildAt(int)
   */
  @Override
  public CompartmentMapping getChildAt(int index) {
    if (index < 0) {
      throw new IndexOutOfBoundsException(MessageFormat.format(
        resourceBundle.getString("IndexSurpassesBoundsException"), index, 0));
    }

    int pos = 0;

    if (isSetCompartmentMapping()) {
      if (pos==index) {
        return getCompartmentMapping();
      }
      pos++;
    }

    throw new IndexOutOfBoundsException(MessageFormat.format(
      resourceBundle.getString("IndexExceedsBoundsException"), index,pos));
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.AbstractSBasePlugin#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 1259;
    int hashCode = super.hashCode();
    if (isSetCompartmentMapping()) {
      hashCode += prime * getCompartmentMapping().hashCode();
    }
    return hashCode;
  }

}
