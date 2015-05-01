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
package org.sbml.jsbml.ext.multi;

import java.text.MessageFormat;
import java.util.Map;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.ext.AbstractSBasePlugin;

/**
 * 
 * @author Nicolas Rodriguez
 * @author Marine Dumousseau
 * @since 1.0
 * @version $Rev$
 */
public class MultiSpeciesPlugin extends AbstractSBasePlugin { 

  // TODO - implements IdManager: SpeciesFeature are id unique within one Species
  

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getPackageName()
   */
  @Override
  public String getPackageName() {
    return MultiConstants.shortLabel;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getPrefix()
   */
  @Override
  public String getPrefix() {
    return MultiConstants.shortLabel;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getURI()
   */
  @Override
  public String getURI() {
    return getElementNamespace();
  }


  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -5396837209115412420L;

  /**
   * 
   */
  private ListOf<OutwardBindingSite> listOfOutwardBindingSites;

  /**
   * 
   */
  private ListOf<SpeciesFeature> listOfSpeciesFeatures;

  /**
   * 
   */
  private String speciesType;

  /**
   * 
   * @param species
   */
  public MultiSpeciesPlugin(Species species) {
    super(species);
    setNamespace(MultiConstants.namespaceURI);  // TODO - removed once the mechanism are in place to set package version and namespace
  }


  /**
   * @param multiSpecies
   */
  public MultiSpeciesPlugin(MultiSpeciesPlugin multiSpecies) {
    super(multiSpecies);
    
    // copy all attributes
    if (multiSpecies.isSetListOfOutwardBindingSites()) {
      setListOfOutwardBindingSites(multiSpecies.getListOfOutwardBindingSites().clone());
    }
    if (multiSpecies.isSetListOfSpeciesFeatures()) {
      setListOfSpeciesFeatures(multiSpecies.getListOfSpeciesFeatures().clone());
    }
    if (multiSpecies.isSetSpeciesType()) {
      setSpeciesType(multiSpecies.getSpeciesType());
    }
  }


  @Override
  public MultiSpeciesPlugin clone() {
    return new MultiSpeciesPlugin(this);
  }

  
  
  /**
   * Returns {@code true} if {@link #listOfOutwardBindingSites} contains at least
   * one element.
   *
   * @return {@code true} if {@link #listOfOutwardBindingSites} contains at least
   *         one element, otherwise {@code false}.
   */
  public boolean isSetListOfOutwardBindingSites() {
    if (listOfOutwardBindingSites == null) {
      return false;
    }
    return true;
  }


  /**
   * Returns the {@link #listOfOutwardBindingSites}.
   * Creates it if it does not already exist.
   *
   * @return the {@link #listOfOutwardBindingSites}.
   */
  public ListOf<OutwardBindingSite> getListOfOutwardBindingSites() {
    if (listOfOutwardBindingSites == null) {
      listOfOutwardBindingSites = new ListOf<OutwardBindingSite>();
      listOfOutwardBindingSites.setNamespace(MultiConstants.namespaceURI); // TODO - removed once the mechanism are in place to set package version and namespace
      listOfOutwardBindingSites.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'multi'
      listOfOutwardBindingSites.setPackageName(null);
      listOfOutwardBindingSites.setPackageName(MultiConstants.shortLabel);
      listOfOutwardBindingSites.setSBaseListType(ListOf.Type.other);

      if (isSetExtendedSBase()) {
        extendedSBase.registerChild(listOfOutwardBindingSites);
      }
    }
    return listOfOutwardBindingSites;
  }


  /**
   * Sets the given {@code ListOf<OutwardBindingSite>}.
   * If {@link #listOfOutwardBindingSites} was defined before and contains some
   * elements, they are all unset.
   *
   * @param listOfOutwardBindingSites
   */
  public void setListOfOutwardBindingSites(ListOf<OutwardBindingSite> listOfOutwardBindingSites) {
    unsetListOfOutwardBindingSites();
    this.listOfOutwardBindingSites = listOfOutwardBindingSites;

    if (listOfOutwardBindingSites != null) {
      listOfOutwardBindingSites.unsetNamespace();
      listOfOutwardBindingSites.setNamespace(MultiConstants.namespaceURI); // TODO - removed once the mechanism are in place to set package version and namespace
      listOfOutwardBindingSites.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'multi'
      listOfOutwardBindingSites.setPackageName(null);
      listOfOutwardBindingSites.setPackageName(MultiConstants.shortLabel);
      this.listOfOutwardBindingSites.setSBaseListType(ListOf.Type.other);

      if (isSetExtendedSBase()) {
        extendedSBase.registerChild(this.listOfOutwardBindingSites);
      }
    }
  }


  /**
   * Returns {@code true} if {@link #listOfOutwardBindingSites} contains at least
   * one element, otherwise {@code false}.
   *
   * @return {@code true} if {@link #listOfOutwardBindingSites} contains at least
   *         one element, otherwise {@code false}.
   */
  public boolean unsetListOfOutwardBindingSites() {
    if (isSetListOfOutwardBindingSites()) {
      ListOf<OutwardBindingSite> oldOutwardBindingSites = this.listOfOutwardBindingSites;
      this.listOfOutwardBindingSites = null;
      oldOutwardBindingSites.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }


  /**
   * Adds a new {@link OutwardBindingSite} to the {@link #listOfOutwardBindingSites}.
   * <p>The listOfOutwardBindingSites is initialized if necessary.
   *
   * @param outwardBindingSite the element to add to the list
   * @return {@code true} (as specified by {@link java.util.Collection#add})
   * @see java.util.Collection#add(Object)
   */
  public boolean addOutwardBindingSite(OutwardBindingSite outwardBindingSite) {
    return getListOfOutwardBindingSites().add(outwardBindingSite);
  }


  /**
   * Removes an element from the {@link #listOfOutwardBindingSites}.
   *
   * @param outwardBindingSite the element to be removed from the list.
   * @return {@code true} if the list contained the specified element and it was
   *         removed.
   * @see java.util.List#remove(Object)
   */
  public boolean removeOutwardBindingSite(OutwardBindingSite outwardBindingSite) {
    if (isSetListOfOutwardBindingSites()) {
      return getListOfOutwardBindingSites().remove(outwardBindingSite);
    }
    return false;
  }


  /**
   * Removes an element from the {@link #listOfOutwardBindingSites} at the given index.
   *
   * @param i the index where to remove the {@link OutwardBindingSite}.
   * @return the specified element if it was successfully found and removed.
   * @throws IndexOutOfBoundsException if the listOf is not set or if the index is
   *         out of bound ({@code (i < 0) || (i > listOfOutwardBindingSites)}).
   */
  public OutwardBindingSite removeOutwardBindingSite(int i) {
    if (!isSetListOfOutwardBindingSites()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfOutwardBindingSites().remove(i);
  }


  /**
   * Creates a new OutwardBindingSite element and adds it to the
   * {@link #listOfOutwardBindingSites} list.
   *
   * @return the newly created element, i.e., the last item in the
   *         {@link #listOfOutwardBindingSites}
   */
  public OutwardBindingSite createOutwardBindingSite() {
    OutwardBindingSite outwardBindingSite = new OutwardBindingSite();
    addOutwardBindingSite(outwardBindingSite);
    return outwardBindingSite;
  }


  /**
   * Gets an element from the {@link #listOfOutwardBindingSites} at the given index.
   *
   * @param i the index of the {@link OutwardBindingSite} element to get.
   * @return an element from the listOfOutwardBindingSites at the given index.
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index < 0 || index > list.size).
   */
  public OutwardBindingSite getOutwardBindingSite(int i) {
    if (!isSetListOfOutwardBindingSites()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfOutwardBindingSites().get(i);
  }


  /**
   * Returns the number of {@link OutwardBindingSite}s in this
   * {@link MultiSpeciesPlugin}.
   * 
   * @return the number of {@link OutwardBindingSite}s in this
   *         {@link MultiSpeciesPlugin}.
   */
  public int getOutwardBindingSiteCount() {
    return isSetListOfOutwardBindingSites() ? getListOfOutwardBindingSites().size() : 0;
  }


  /**
   * Returns the number of {@link OutwardBindingSite}s in this
   * {@link MultiSpeciesPlugin}.
   * 
   * @return the number of {@link OutwardBindingSite}s in this
   *         {@link MultiSpeciesPlugin}.
   * @libsbml.deprecated same as {@link #getOutwardBindingSiteCount()}
   */
  public int getNumOutwardBindingSites() {
    return getOutwardBindingSiteCount();
  }


  
  /**
   * Returns {@code true} if {@link #listOfSpeciesFeatures} contains at least
   * one element.
   *
   * @return {@code true} if {@link #listOfSpeciesFeatures} contains at least
   *         one element, otherwise {@code false}.
   */
  public boolean isSetListOfSpeciesFeatures() {
    if (listOfSpeciesFeatures == null) {
      return false;
    }
    return true;
  }


  /**
   * Returns the {@link #listOfSpeciesFeatures}.
   * Creates it if it does not already exist.
   *
   * @return the {@link #listOfSpeciesFeatures}.
   */
  public ListOf<SpeciesFeature> getListOfSpeciesFeatures() {
    if (listOfSpeciesFeatures == null) {
      listOfSpeciesFeatures = new ListOf<SpeciesFeature>();
      listOfSpeciesFeatures.setNamespace(MultiConstants.namespaceURI); // TODO - removed once the mechanism are in place to set package version and namespace
      listOfSpeciesFeatures.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'multi'
      listOfSpeciesFeatures.setPackageName(null);
      listOfSpeciesFeatures.setPackageName(MultiConstants.shortLabel);
      listOfSpeciesFeatures.setSBaseListType(ListOf.Type.other);

      if (isSetExtendedSBase()) {
        extendedSBase.registerChild(listOfSpeciesFeatures);
      }
    }
    return listOfSpeciesFeatures;
  }


  /**
   * Sets the given {@code ListOf<SpeciesFeature>}.
   * If {@link #listOfSpeciesFeatures} was defined before and contains some
   * elements, they are all unset.
   *
   * @param listOfSpeciesFeatures
   */
  public void setListOfSpeciesFeatures(ListOf<SpeciesFeature> listOfSpeciesFeatures) {
    unsetListOfSpeciesFeatures();
    this.listOfSpeciesFeatures = listOfSpeciesFeatures;

    if (listOfSpeciesFeatures != null) {
      listOfSpeciesFeatures.unsetNamespace();
      listOfSpeciesFeatures.setNamespace(MultiConstants.namespaceURI); // TODO - removed once the mechanism are in place to set package version and namespace
      listOfSpeciesFeatures.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'multi'
      listOfSpeciesFeatures.setPackageName(null);
      listOfSpeciesFeatures.setPackageName(MultiConstants.shortLabel);
      this.listOfSpeciesFeatures.setSBaseListType(ListOf.Type.other);

      if (isSetExtendedSBase()) {
        extendedSBase.registerChild(this.listOfSpeciesFeatures);
      }
    }
  }


  /**
   * Returns {@code true} if {@link #listOfSpeciesFeatures} contains at least
   * one element, otherwise {@code false}.
   *
   * @return {@code true} if {@link #listOfSpeciesFeatures} contains at least
   *         one element, otherwise {@code false}.
   */
  public boolean unsetListOfSpeciesFeatures() {
    if (isSetListOfSpeciesFeatures()) {
      ListOf<SpeciesFeature> oldSpeciesFeatures = this.listOfSpeciesFeatures;
      this.listOfSpeciesFeatures = null;
      oldSpeciesFeatures.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }


  /**
   * Adds a new {@link SpeciesFeature} to the {@link #listOfSpeciesFeatures}.
   * <p>The listOfSpeciesFeatures is initialized if necessary.
   *
   * @param speciesFeature the element to add to the list
   * @return {@code true} (as specified by {@link java.util.Collection#add})
   * @see java.util.Collection#add(Object)
   */
  public boolean addSpeciesFeature(SpeciesFeature speciesFeature) {
    return getListOfSpeciesFeatures().add(speciesFeature);
  }


  /**
   * Removes an element from the {@link #listOfSpeciesFeatures}.
   *
   * @param speciesFeature the element to be removed from the list.
   * @return {@code true} if the list contained the specified element and it was
   *         removed.
   * @see java.util.List#remove(Object)
   */
  public boolean removeSpeciesFeature(SpeciesFeature speciesFeature) {
    if (isSetListOfSpeciesFeatures()) {
      return getListOfSpeciesFeatures().remove(speciesFeature);
    }
    return false;
  }


  /**
   * Removes an element from the {@link #listOfSpeciesFeatures}.
   *
   * @param speciesFeatureId the id of the element to be removed from the list.
   * @return the removed element, if it was successfully found and removed or
   *         {@code null}.
   */
  public SpeciesFeature removeSpeciesFeature(String speciesFeatureId) {
    if (isSetListOfSpeciesFeatures()) {
      return getListOfSpeciesFeatures().remove(speciesFeatureId);
    }
    return null;
  }


  /**
   * Removes an element from the {@link #listOfSpeciesFeatures} at the given index.
   *
   * @param i the index where to remove the {@link SpeciesFeature}.
   * @return the specified element if it was successfully found and removed.
   * @throws IndexOutOfBoundsException if the listOf is not set or if the index is
   *         out of bound ({@code (i < 0) || (i > listOfSpeciesFeatures)}).
   */
  public SpeciesFeature removeSpeciesFeature(int i) {
    if (!isSetListOfSpeciesFeatures()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfSpeciesFeatures().remove(i);
  }


  /**
   * Creates a new SpeciesFeature element and adds it to the
   * {@link #listOfSpeciesFeatures} list.
   *
   * @return the newly created element, i.e., the last item in the
   *         {@link #listOfSpeciesFeatures}
   */
  public SpeciesFeature createSpeciesFeature() {
    return createSpeciesFeature(null);
  }


  /**
   * Creates a new {@link SpeciesFeature} element and adds it to the
   * {@link #listOfSpeciesFeatures} list.
   *
   * @param id the identifier that is to be applied to the new element.
   * @return the newly created {@link SpeciesFeature} element, which is the last
   *         element in the {@link #listOfSpeciesFeatures}.
   */
  public SpeciesFeature createSpeciesFeature(String id) {
    SpeciesFeature speciesFeature = new SpeciesFeature();
    speciesFeature.setId(id);
    addSpeciesFeature(speciesFeature);
    return speciesFeature;
  }


  /**
   * Gets an element from the {@link #listOfSpeciesFeatures} at the given index.
   *
   * @param i the index of the {@link SpeciesFeature} element to get.
   * @return an element from the listOfSpeciesFeatures at the given index.
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index < 0 || index > list.size).
   */
  public SpeciesFeature getSpeciesFeature(int i) {
    if (!isSetListOfSpeciesFeatures()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfSpeciesFeatures().get(i);
  }


  /**
   * Gets an element from the listOfSpeciesFeatures, with the given id.
   *
   * @param speciesFeatureId the id of the {@link SpeciesFeature} element to get.
   * @return an element from the listOfSpeciesFeatures with the given id
   *         or {@code null}.
   */
  public SpeciesFeature getSpeciesFeature(String speciesFeatureId) {
    if (isSetListOfSpeciesFeatures()) {
      return getListOfSpeciesFeatures().get(speciesFeatureId);
    }
    return null;
  }


  /**
   * Returns the number of {@link SpeciesFeature}s in this
   * {@link MultiSpeciesPlugin}.
   * 
   * @return the number of {@link SpeciesFeature}s in this
   *         {@link MultiSpeciesPlugin}.
   */
  public int getSpeciesFeatureCount() {
    return isSetListOfSpeciesFeatures() ? getListOfSpeciesFeatures().size() : 0;
  }


  /**
   * Returns the number of {@link SpeciesFeature}s in this
   * {@link MultiSpeciesPlugin}.
   * 
   * @return the number of {@link SpeciesFeature}s in this
   *         {@link MultiSpeciesPlugin}.
   * @libsbml.deprecated same as {@link #getSpeciesFeatureCount()}
   */
  public int getNumSpeciesFeatures() {
    return getSpeciesFeatureCount();
  }

  
  /**
   * Returns the value of {@link #speciesType}.
   *
   * @return the value of {@link #speciesType}.
   */
  public String getSpeciesType() {
    if (isSetSpeciesType()) {
      return speciesType;
    }

    return null;
  }


  /**
   * Returns whether {@link #speciesType} is set.
   *
   * @return whether {@link #speciesType} is set.
   */
  public boolean isSetSpeciesType() {
    return speciesType != null;
  }


  /**
   * Sets the value of speciesType
   *
   * @param speciesType the value of speciesType to be set.
   */
  public void setSpeciesType(String speciesType) {
    String oldSpeciesType = this.speciesType;
    this.speciesType = speciesType;
    firePropertyChange(MultiConstants.speciesType, oldSpeciesType, this.speciesType);
  }


  /**
   * Unsets the variable speciesType.
   *
   * @return {@code true} if speciesType was set before, otherwise {@code false}.
   */
  public boolean unsetSpeciesType() {
    if (isSetSpeciesType()) {
      String oldSpeciesType = this.speciesType;
      this.speciesType = null;
      firePropertyChange(MultiConstants.speciesType, oldSpeciesType, this.speciesType);
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
   * @see org.sbml.jsbml.ext.SBasePlugin#getChildAt(int)
   */
  @Override
  public SBase getChildAt(int childIndex) {
    if (childIndex < 0) {
      throw new IndexOutOfBoundsException(MessageFormat.format(resourceBundle.getString("IndexSurpassesBoundsException"), childIndex, 0));
    }

    int pos = 0;
    if (isSetListOfOutwardBindingSites()) {
      if (pos == childIndex) {
        return getListOfOutwardBindingSites();
      }
      pos++;
    }
    if (isSetListOfSpeciesFeatures()) {
      if (pos == childIndex) {
        return getListOfSpeciesFeatures();
      }
      pos++;
    }
    
    throw new IndexOutOfBoundsException(MessageFormat.format(
      resourceBundle.getString("IndexExceedsBoundsException"),
      childIndex, Math.min(pos, 0)));
  }

  @Override
  public int getChildCount() {
    int count = 0;

    if (isSetListOfOutwardBindingSites()) {
      count++;
    }
    if (isSetListOfSpeciesFeatures()) {
      count++;
    }
    
    return count;
  }



  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetSpeciesType()) {
      attributes.put(MultiConstants.shortLabel + ":" + MultiConstants.speciesType, getSpeciesType());
    }

    return attributes;
  }


  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = true;

    if (!isAttributeRead) {

      if (attributeName.equals(MultiConstants.speciesType)) {
        setSpeciesType(value);
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }

}
