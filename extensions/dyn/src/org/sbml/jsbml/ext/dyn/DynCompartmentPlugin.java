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
 * 6. Boston University, Boston, MA, USA
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.dyn;

import java.text.MessageFormat;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.ListOf;

/**
 * @author Harold G&oacute;mez
 * @since 1.0
 */
public class DynCompartmentPlugin extends DynSBasePlugin {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -3432424478606541965L;

  /**
   * List of spatial components which describe compartment in space
   */
  protected ListOf<SpatialComponent> listOfSpatialComponents;

  /**
   * Empty constructor
   */
  public DynCompartmentPlugin() {
    super();
    initDefaults();
  }

  /**
   * Constructor
   * 
   * @param compartment
   */
  public DynCompartmentPlugin(Compartment compartment) {
    super(compartment);
    initDefaults();
  }

  /**
   * Constructor
   * 
   * @param dynCompartmentPlugin
   */
  public DynCompartmentPlugin(DynCompartmentPlugin dynCompartmentPlugin) {
    super(dynCompartmentPlugin);

    if (dynCompartmentPlugin.isSetListOfSpatialComponents()) {
      setListOfSpatialComponents(dynCompartmentPlugin.getListOfSpatialComponents().clone());
    }
  }

  /**
   * Initializes custom Class attributes
   * */
  @Override
  public void initDefaults() {
    setPackageVersion(-1);
  }

  /**
   * Returns the value of listOfSpatialComponents
   * 
   * @return the value of listOfSpatialComponents
   */
  public ListOf<SpatialComponent> getListOfSpatialComponents() {
    if (listOfSpatialComponents == null) {
      listOfSpatialComponents = new ListOf<SpatialComponent>();
      listOfSpatialComponents.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'dyn'
      listOfSpatialComponents.setPackageName(null);
      listOfSpatialComponents.setPackageName(DynConstants.shortLabel);
      listOfSpatialComponents.setSBaseListType(ListOf.Type.other);

      if (isSetExtendedSBase()) {
        extendedSBase.registerChild(listOfSpatialComponents);
      }
    }
    return listOfSpatialComponents;
  }

  /**
   * Returns whether listOfSpatialComponents is set
   * 
   * @return whether listOfSpatialComponents is set
   */
  public boolean isSetListOfSpatialComponents() {
    if ((listOfSpatialComponents == null)
        || listOfSpatialComponents.isEmpty()) {
      return false;
    }
    return true;
  }

  /**
   * Sets the value of listOfSpatialComponents
   * @param listOfSpatialComponents
   */
  public void setListOfSpatialComponents(ListOf<SpatialComponent> listOfSpatialComponents) {
    unsetListOfSpatialComponents();

    this.listOfSpatialComponents = listOfSpatialComponents;

    if ((this.listOfSpatialComponents != null)) {
      listOfSpatialComponents.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'dyn'
      listOfSpatialComponents.setPackageName(null);
      listOfSpatialComponents.setPackageName(DynConstants.shortLabel);
      listOfSpatialComponents.setSBaseListType(ListOf.Type.other);
    }

    if (isSetExtendedSBase()) {
      extendedSBase.registerChild(listOfSpatialComponents);
    }
  }

  /**
   * Unsets the variable listOfSpatialComponents
   * 
   * @return {@code true}, if listOfSpatialComponents was set before,
   *         otherwise {@code false}
   */
  public boolean unsetListOfSpatialComponents() {
    if (isSetListOfSpatialComponents()) {
      ListOf<SpatialComponent> oldListOfSpatialComponents = listOfSpatialComponents;
      listOfSpatialComponents = null;
      firePropertyChange(DynConstants.listOfSpatialComponents,
        oldListOfSpatialComponents, listOfSpatialComponents);
      return true;
    }
    return false;
  }

  /**
   * Creates a new SpatialComponent element and adds it to the
   * listOfSpatialComponents list
   * @return
   */
  public SpatialComponent createSpatialComponent() {
    return createSpatialComponent(null);
  }

  /**
   * Creates a new {@link SpatialComponent} element and adds it to the
   * {@link #getListOfSpatialComponents()}
   * 
   * @param id
   * @return a new {@link SpatialComponent} element
   */
  public SpatialComponent createSpatialComponent(String id) {
    SpatialComponent spatialComponent = new SpatialComponent(id, null,
      getLevel(), getVersion());
    addSpatialComponent(spatialComponent);
    return spatialComponent;
  }

  /**
   * Adds a new {@link SpatialComponent} to the ListOfSpatialComponents. The
   * ListOfSpatialComponents is initialized if necessary.
   *
   * @param spatialComponent
   *            the element to add to the list
   * @return {@code true} (as specified by {@link Collection#add})
   */
  public boolean addSpatialComponent(SpatialComponent spatialComponent) {
    return getListOfSpatialComponents().add(spatialComponent);
  }

  /**
   * Removes the ith element from the ListOfSpatialComponents
   * 
   * @param i
   */
  public void removeSpatialComponent(int i) {
    if (!isSetListOfSpatialComponents()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    getListOfSpatialComponents().remove(i);
  }

  /**
   * Removes an element from the ListOfSpatialComponents
   *
   * @param spatialComponent
   *            the element to be removed from the list
   * @return {@code true} if the list contained the specified element
   * @see List#remove(Object)
   */
  public boolean removeSpatialComponent(SpatialComponent spatialComponent) {
    if (isSetListOfSpatialComponents()) {
      return getListOfSpatialComponents().remove(spatialComponent);
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.dyn.DynSBasePlugin#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!super.equals(obj)) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    DynCompartmentPlugin other = (DynCompartmentPlugin) obj;

    if (listOfSpatialComponents == null) {
      if (other.listOfSpatialComponents != null) {
        return false;
      }
    } else if (!listOfSpatialComponents
        .equals(other.listOfSpatialComponents)) {
      return false;
    }
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.dyn.DynSBasePlugin#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 2141;
    int hashCode = super.hashCode();
    hashCode = prime * hashCode + ((listOfSpatialComponents == null) ?
      0 : listOfSpatialComponents.hashCode());
    return hashCode;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.dyn.DynSBasePlugin#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.dyn.DynSBasePlugin#getChildAt(int)
   */
  @Override
  public TreeNode getChildAt(int childIndex) {
    if (childIndex < 0 || childIndex >= 1) {
      throw new IndexOutOfBoundsException(MessageFormat.format(
        resourceBundle.getString("IndexExceedsBoundsException"),
        childIndex, +Math.min(getChildCount(), 0)));
    }

    return listOfSpatialComponents;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.dyn.DynSBasePlugin#getChildCount()
   */
  @Override
  public int getChildCount() {
    return isSetListOfSpatialComponents() ? 1 : 0;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.dyn.DynSBasePlugin#clone()
   */
  @Override
  public DynCompartmentPlugin clone() {
    return new DynCompartmentPlugin(this);
  }

}
