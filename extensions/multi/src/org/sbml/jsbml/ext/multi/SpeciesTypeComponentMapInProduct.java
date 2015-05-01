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
 * 5. The Babraham Institute, Cambridge, UK
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */

package org.sbml.jsbml.ext.multi;

import java.text.MessageFormat;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.ListOf;


/**
 *
 * @author Nicolas Rodriguez
 * @version $Rev$
 * @since 1.1
 */
public class SpeciesTypeComponentMapInProduct extends AbstractSBase {

  /**
   * Serial version identifier.
   */
  private static final long serialVersionUID = 1L;
  /**
   * 
   */
  private String reactant;
  /**
   * 
   */
  private String reactantComponent;
  /**
   * 
   */
  private String productComponent;

  /**
   * 
   */
  private ListOf<SpeciesFeatureChange> listOfSpeciesFeatureChanges;
  
  
  /**
   * Creates an SpeciesTypeComponentMapInProduct instance 
   */
  public SpeciesTypeComponentMapInProduct() {
    super();
    initDefaults();
  }


  /**
   * Creates a SpeciesTypeComponentMapInProduct instance with a level and version.
   * 
   * @param level SBML Level
   * @param version SBML Version
   */
  public SpeciesTypeComponentMapInProduct(int level, int version) {
    super(level, version);
    initDefaults();
  }


  /**
   * Clone constructor
   */
  public SpeciesTypeComponentMapInProduct(SpeciesTypeComponentMapInProduct obj) {
    super(obj);

    // copy all class attributes
    if (obj.isSetListOfSpeciesFeatureChanges()) {
      setListOfSpeciesFeatureChanges(obj.getListOfSpeciesFeatureChanges());
    }
    if (obj.isSetReactant()) {
      setReactant(obj.getReactant());
    }
    if (obj.isSetReactantComponent()) {
      setReactantComponent(obj.getReactantComponent());
    }
    if (obj.isSetProductComponent()) {
      setProductComponent(obj.getProductComponent());
    }
  }


  /**
   * clones this class
   */
  public SpeciesTypeComponentMapInProduct clone() {
    return new SpeciesTypeComponentMapInProduct(this);
  }


  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    setNamespace(MultiConstants.namespaceURI); // TODO - removed once the mechanism are in place to set package version and namespace
    packageName = MultiConstants.shortLabel;
    setPackageVersion(-1);
  }
  

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 6217;
    int result = super.hashCode();
    result = prime
      * result
      + ((listOfSpeciesFeatureChanges == null) ? 0
        : listOfSpeciesFeatureChanges.hashCode());
    result = prime * result
      + ((productComponent == null) ? 0 : productComponent.hashCode());
    result = prime * result + ((reactant == null) ? 0 : reactant.hashCode());
    result = prime * result
      + ((reactantComponent == null) ? 0 : reactantComponent.hashCode());
    return result;
  }


  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
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
    SpeciesTypeComponentMapInProduct other = (SpeciesTypeComponentMapInProduct) obj;
    if (listOfSpeciesFeatureChanges == null) {
      if (other.listOfSpeciesFeatureChanges != null) {
        return false;
      }
    } else if (!listOfSpeciesFeatureChanges.equals(other.listOfSpeciesFeatureChanges)) {
      return false;
    }
    if (productComponent == null) {
      if (other.productComponent != null) {
        return false;
      }
    } else if (!productComponent.equals(other.productComponent)) {
      return false;
    }
    if (reactant == null) {
      if (other.reactant != null) {
        return false;
      }
    } else if (!reactant.equals(other.reactant)) {
      return false;
    }
    if (reactantComponent == null) {
      if (other.reactantComponent != null) {
        return false;
      }
    } else if (!reactantComponent.equals(other.reactantComponent)) {
      return false;
    }
    return true;
  }


  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "SpeciesTypeComponentMapInProduct [reactant = " + reactant
      + ", reactantComponent = " + reactantComponent + ", productComponent = "
      + productComponent + ", listOfSpeciesFeatureChanges.size = "
      + getSpeciesFeatureChangeCount() + "]";
  }
  
  
  /**
   * Returns {@code true} if {@link #listOfSpeciesFeatureChanges} contains at least
   * one element.
   *
   * @return {@code true} if {@link #listOfSpeciesFeatureChanges} contains at least
   *         one element, otherwise {@code false}.
   */
  public boolean isSetListOfSpeciesFeatureChanges() {
    if (listOfSpeciesFeatureChanges == null) {
      return false;
    }
    return true;
  }


  /**
   * Returns the {@link #listOfSpeciesFeatureChanges}.
   * Creates it if it does not already exist.
   *
   * @return the {@link #listOfSpeciesFeatureChanges}.
   */
  public ListOf<SpeciesFeatureChange> getListOfSpeciesFeatureChanges() {
    if (listOfSpeciesFeatureChanges == null) {
      listOfSpeciesFeatureChanges = new ListOf<SpeciesFeatureChange>();
      listOfSpeciesFeatureChanges.setNamespace(MultiConstants.namespaceURI); // TODO - removed once the mechanism are in place to set package version and namespace
      listOfSpeciesFeatureChanges.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'multi'
      listOfSpeciesFeatureChanges.setPackageName(null);
      listOfSpeciesFeatureChanges.setPackageName(MultiConstants.shortLabel);
      listOfSpeciesFeatureChanges.setSBaseListType(ListOf.Type.other);

      registerChild(listOfSpeciesFeatureChanges);
    }
    return listOfSpeciesFeatureChanges;
  }


  /**
   * Sets the given {@code ListOf<SpeciesFeatureChange>}.
   * If {@link #listOfSpeciesFeatureChanges} was defined before and contains some
   * elements, they are all unset.
   *
   * @param listOfSpeciesFeatureChanges
   */
  public void setListOfSpeciesFeatureChanges(ListOf<SpeciesFeatureChange> listOfSpeciesFeatureChanges) {
    unsetListOfSpeciesFeatureChanges();
    this.listOfSpeciesFeatureChanges = listOfSpeciesFeatureChanges;
    if (listOfSpeciesFeatureChanges != null) {
      listOfSpeciesFeatureChanges.unsetNamespace();
      listOfSpeciesFeatureChanges.setNamespace(MultiConstants.namespaceURI); // TODO - removed once the mechanism are in place to set package version and namespace
      listOfSpeciesFeatureChanges.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'multi'
      listOfSpeciesFeatureChanges.setPackageName(null);
      listOfSpeciesFeatureChanges.setPackageName(MultiConstants.shortLabel);
      this.listOfSpeciesFeatureChanges.setSBaseListType(ListOf.Type.other);

      registerChild(listOfSpeciesFeatureChanges);
    }
  }


  /**
   * Returns {@code true} if {@link #listOfSpeciesFeatureChanges} contains at least
   * one element, otherwise {@code false}.
   *
   * @return {@code true} if {@link #listOfSpeciesFeatureChanges} contains at least
   *         one element, otherwise {@code false}.
   */
  public boolean unsetListOfSpeciesFeatureChanges() {
    if (isSetListOfSpeciesFeatureChanges()) {
      ListOf<SpeciesFeatureChange> oldSpeciesFeatureChanges = this.listOfSpeciesFeatureChanges;
      this.listOfSpeciesFeatureChanges = null;
      oldSpeciesFeatureChanges.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }


  /**
   * Adds a new {@link SpeciesFeatureChange} to the {@link #listOfSpeciesFeatureChanges}.
   * <p>The listOfSpeciesFeatureChanges is initialized if necessary.
   *
   * @param speciesFeatureChange the element to add to the list
   * @return {@code true} (as specified by {@link java.util.Collection#add})
   * @see java.util.Collection#add(Object)
   */
  public boolean addSpeciesFeatureChange(SpeciesFeatureChange speciesFeatureChange) {
    return getListOfSpeciesFeatureChanges().add(speciesFeatureChange);
  }


  /**
   * Removes an element from the {@link #listOfSpeciesFeatureChanges}.
   *
   * @param speciesFeatureChange the element to be removed from the list.
   * @return {@code true} if the list contained the specified element and it was
   *         removed.
   * @see java.util.List#remove(Object)
   */
  public boolean removeSpeciesFeatureChange(SpeciesFeatureChange speciesFeatureChange) {
    if (isSetListOfSpeciesFeatureChanges()) {
      return getListOfSpeciesFeatureChanges().remove(speciesFeatureChange);
    }
    return false;
  }


  /**
   * Removes an element from the {@link #listOfSpeciesFeatureChanges}.
   *
   * @param speciesFeatureChangeId the id of the element to be removed from the list.
   * @return the removed element, if it was successfully found and removed or
   *         {@code null}.
   */
  public SpeciesFeatureChange removeSpeciesFeatureChange(String speciesFeatureChangeId) {
    if (isSetListOfSpeciesFeatureChanges()) {
      return getListOfSpeciesFeatureChanges().remove(speciesFeatureChangeId);
    }
    return null;
  }


  /**
   * Removes an element from the {@link #listOfSpeciesFeatureChanges} at the given index.
   *
   * @param i the index where to remove the {@link SpeciesFeatureChange}.
   * @return the specified element if it was successfully found and removed.
   * @throws IndexOutOfBoundsException if the listOf is not set or if the index is
   *         out of bound ({@code (i < 0) || (i > listOfSpeciesFeatureChanges)}).
   */
  public SpeciesFeatureChange removeSpeciesFeatureChange(int i) {
    if (!isSetListOfSpeciesFeatureChanges()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfSpeciesFeatureChanges().remove(i);
  }


  /**
   * Creates a new SpeciesFeatureChange element and adds it to the
   * {@link #listOfSpeciesFeatureChanges} list.
   *
   * @return the newly created element, i.e., the last item in the
   *         {@link #listOfSpeciesFeatureChanges}
   */
  public SpeciesFeatureChange createSpeciesFeatureChange() {
    return createSpeciesFeatureChange(null);
  }


  /**
   * Creates a new {@link SpeciesFeatureChange} element and adds it to the
   * {@link #listOfSpeciesFeatureChanges} list.
   *
   * @param id the identifier that is to be applied to the new element.
   * @return the newly created {@link SpeciesFeatureChange} element, which is the last
   *         element in the {@link #listOfSpeciesFeatureChanges}.
   */
  public SpeciesFeatureChange createSpeciesFeatureChange(String id) {
    SpeciesFeatureChange speciesFeatureChange = new SpeciesFeatureChange(id);
    addSpeciesFeatureChange(speciesFeatureChange);
    return speciesFeatureChange;
  }


  /**
   * Gets an element from the {@link #listOfSpeciesFeatureChanges} at the given index.
   *
   * @param i the index of the {@link SpeciesFeatureChange} element to get.
   * @return an element from the listOfSpeciesFeatureChanges at the given index.
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index < 0 || index > list.size).
   */
  public SpeciesFeatureChange getSpeciesFeatureChange(int i) {
    if (!isSetListOfSpeciesFeatureChanges()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfSpeciesFeatureChanges().get(i);
  }


  /**
   * Gets an element from the listOfSpeciesFeatureChanges, with the given id.
   *
   * @param speciesFeatureChangeId the id of the {@link SpeciesFeatureChange} element to get.
   * @return an element from the listOfSpeciesFeatureChanges with the given id
   *         or {@code null}.
   */
  public SpeciesFeatureChange getSpeciesFeatureChange(String speciesFeatureChangeId) {
    if (isSetListOfSpeciesFeatureChanges()) {
      return getListOfSpeciesFeatureChanges().get(speciesFeatureChangeId);
    }
    return null;
  }


  /**
   * Returns the number of {@link SpeciesFeatureChange}s in this
   * {@link SpeciesTypeComponentMapInProduct}.
   * 
   * @return the number of {@link SpeciesFeatureChange}s in this
   *         {@link SpeciesTypeComponentMapInProduct}.
   */
  public int getSpeciesFeatureChangeCount() {
    return isSetListOfSpeciesFeatureChanges() ? getListOfSpeciesFeatureChanges().size() : 0;
  }


  /**
   * Returns the number of {@link SpeciesFeatureChange}s in this
   * {@link SpeciesTypeComponentMapInProduct}.
   * 
   * @return the number of {@link SpeciesFeatureChange}s in this
   *         {@link SpeciesTypeComponentMapInProduct}.
   * @libsbml.deprecated same as {@link #getSpeciesFeatureChangeCount()}
   */
  public int getNumSpeciesFeatureChanges() {
    return getSpeciesFeatureChangeCount();
  }

  
  /**
   * Returns the value of {@link #reactant}.
   *
   * @return the value of {@link #reactant}.
   */
  public String getReactant() {
    if (isSetReactant()) {
      return reactant;
    }

    return null;
  }


  /**
   * Returns whether {@link #reactant} is set.
   *
   * @return whether {@link #reactant} is set.
   */
  public boolean isSetReactant() {
    return reactant != null;
  }


  /**
   * Sets the value of reactant
   *
   * @param reactant the value of reactant to be set.
   */
  public void setReactant(String reactant) {
    String oldReactant = this.reactant;
    this.reactant = reactant;
    firePropertyChange(MultiConstants.reactant, oldReactant, this.reactant);
  }


  /**
   * Unsets the variable reactant.
   *
   * @return {@code true} if reactant was set before, otherwise {@code false}.
   */
  public boolean unsetReactant() {
    if (isSetReactant()) {
      String oldReactant = this.reactant;
      this.reactant = null;
      firePropertyChange(MultiConstants.reactant, oldReactant, this.reactant);
      return true;
    }
    return false;
  }
  
  
  /**
   * Returns the value of {@link #reactantComponent}.
   *
   * @return the value of {@link #reactantComponent}.
   */
  public String getReactantComponent() {
    if (isSetReactantComponent()) {
      return reactantComponent;
    }

    return null;
  }


  /**
   * Returns whether {@link #reactantComponent} is set.
   *
   * @return whether {@link #reactantComponent} is set.
   */
  public boolean isSetReactantComponent() {
    return reactantComponent != null;
  }


  /**
   * Sets the value of reactantComponent
   *
   * @param reactantComponent the value of reactantComponent to be set.
   */
  public void setReactantComponent(String reactantComponent) {
    String oldReactantComponent = this.reactantComponent;
    this.reactantComponent = reactantComponent;
    firePropertyChange(MultiConstants.reactantComponent, oldReactantComponent, this.reactantComponent);
  }


  /**
   * Unsets the variable reactantComponent.
   *
   * @return {@code true} if reactantComponent was set before, otherwise {@code false}.
   */
  public boolean unsetReactantComponent() {
    if (isSetReactantComponent()) {
      String oldReactantComponent = this.reactantComponent;
      this.reactantComponent = null;
      firePropertyChange(MultiConstants.reactantComponent, oldReactantComponent, this.reactantComponent);
      return true;
    }
    return false;
  }
  
  
  
  /**
   * Returns the value of {@link #productComponent}.
   *
   * @return the value of {@link #productComponent}.
   */
  public String getProductComponent() {
    if (isSetProductComponent()) {
      return productComponent;
    }

    return null;
  }


  /**
   * Returns whether {@link #productComponent} is set.
   *
   * @return whether {@link #productComponent} is set.
   */
  public boolean isSetProductComponent() {
    return productComponent != null;
  }


  /**
   * Sets the value of productComponent
   *
   * @param productComponent the value of productComponent to be set.
   */
  public void setProductComponent(String productComponent) {
    String oldProductComponent = this.productComponent;
    this.productComponent = productComponent;
    firePropertyChange(MultiConstants.productComponent, oldProductComponent, this.productComponent);
  }


  /**
   * Unsets the variable productComponent.
   *
   * @return {@code true} if productComponent was set before, otherwise {@code false}.
   */
  public boolean unsetProductComponent() {
    if (isSetProductComponent()) {
      String oldProductComponent = this.productComponent;
      this.productComponent = null;
      firePropertyChange(MultiConstants.productComponent, oldProductComponent, this.productComponent);
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

    if (isSetListOfSpeciesFeatureChanges()) {
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

    if (isSetListOfSpeciesFeatureChanges()) {
      if (pos == index) {
        return getListOfSpeciesFeatureChanges();
      }
      pos++;
    }
    
    throw new IndexOutOfBoundsException(
      MessageFormat.format(resourceBundle.getString("IndexExceedsBoundsException"),
        index, Math.min(pos, 0)));
  }
  
  
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetReactant()) {
      attributes.put(MultiConstants.shortLabel + ":" + MultiConstants.reactant, getReactant());
    }
    if (isSetReactantComponent()) {
      attributes.put(MultiConstants.shortLabel + ":" + MultiConstants.reactantComponent, getReactantComponent());
    }
    if (isSetProductComponent()) {
      attributes.put(MultiConstants.shortLabel + ":" + MultiConstants.productComponent, getProductComponent());
    }

    return attributes;
  }


  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
    if (!isAttributeRead) {
      isAttributeRead = true;

      if (attributeName.equals(MultiConstants.reactant)) {
        setReactant(value);
      }
      else if (attributeName.equals(MultiConstants.reactantComponent)) {
        setReactantComponent(value);
      }
      else if (attributeName.equals(MultiConstants.productComponent)) {
        setProductComponent(value);
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }
}
