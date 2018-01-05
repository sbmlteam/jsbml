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
package org.sbml.jsbml.ext.multi;

import java.text.MessageFormat;
import java.util.Map;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.ListOf.Type;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.ext.AbstractSBasePlugin;
import org.sbml.jsbml.ext.multi.util.ListOfSpeciesFeatureContent;

/**
 * A species in the Multi package is extended from a pool to a
 * template or pattern which multiple pools may map to. An extended species can reference a speciesType that provides
 * the backbone for the species such as components (including binding sites) and speciesFeatureTypes.
 * When referencing a speciesType, a species can be further defined with regard to the binding statuses of its
 * outwardBindingSites and the speciesFeatures. With the options to have variable values selected, such as
 * 'either' for the bindingStatus attribute and multiple possibleSpeciesFeatureValues for a speciesFeature,
 * an extended species can work as a template or pattern how species participate in reactions.
 *
 * @author Nicolas Rodriguez
 * @since 1.0
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
  private ListOf<SubListOfSpeciesFeature> listOfSubListOfSpeciesFeatures;
  
  
  /**
   * 
   */
  private String speciesType;

  /**
   * Creates a new {@link MultiSpeciesPlugin} instance, connected to the given 'core'
   * {@link Species}.
   * 
   * @param species the core species that is extended
   */
  public MultiSpeciesPlugin(Species species) {
    super(species);
  }


  /**
   * Creates a new {@link MultiSpeciesPlugin} instance copied from the given {@link MultiSpeciesPlugin}.
   * 
   * @param multiSpecies the element to copy/clone.
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
    if (multiSpecies.isSetListOfSubListOfSpeciesFeatures()) {
      setListOfSubListOfSpeciesFeatures(multiSpecies.getListOfSubListOfSpeciesFeatures().clone());
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
   * Returns {@code true} if {@link #listOfOutwardBindingSites} is not null.
   *
   * @return {@code true} if {@link #listOfOutwardBindingSites} is not null, {@code false} otherwise.
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
      listOfOutwardBindingSites.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'multi'
      listOfOutwardBindingSites.setPackageName(null);
      listOfOutwardBindingSites.setPackageName(MultiConstants.shortLabel);
      listOfOutwardBindingSites.setSBaseListType(ListOf.Type.other);
      listOfOutwardBindingSites.setOtherListName(MultiConstants.listOfOutwardBindingSites);
      
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
   * @param listOfOutwardBindingSites the list of {@link OutwardBindingSite} to set.
   */
  public void setListOfOutwardBindingSites(ListOf<OutwardBindingSite> listOfOutwardBindingSites) {
    unsetListOfOutwardBindingSites();
    this.listOfOutwardBindingSites = listOfOutwardBindingSites;

    if (listOfOutwardBindingSites != null) {
      listOfOutwardBindingSites.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'multi'
      listOfOutwardBindingSites.setPackageName(null);
      listOfOutwardBindingSites.setPackageName(MultiConstants.shortLabel);
      this.listOfOutwardBindingSites.setSBaseListType(ListOf.Type.other);
      this.listOfOutwardBindingSites.setOtherListName(MultiConstants.listOfOutwardBindingSites);

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
   * if the index is out of bound (index &lt; 0 || index &gt; list.size).
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
   * Returns {@code true} if {@link #listOfSpeciesFeatures} is not null.
   *
   * @return {@code true} if {@link #listOfSpeciesFeatures} is not null, otherwise {@code false}.
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
      listOfSpeciesFeatures.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'multi'
      listOfSpeciesFeatures.setPackageName(null);
      listOfSpeciesFeatures.setPackageName(MultiConstants.shortLabel);
      listOfSpeciesFeatures.setSBaseListType(ListOf.Type.other);
      listOfSpeciesFeatures.setOtherListName(MultiConstants.listOfSpeciesFeatures);
      
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
   * @param listOfSpeciesFeatures the list of {@link SpeciesFeature} to set.
   */
  public void setListOfSpeciesFeatures(ListOf<SpeciesFeature> listOfSpeciesFeatures) {
    unsetListOfSpeciesFeatures();
    this.listOfSpeciesFeatures = listOfSpeciesFeatures;

    if (listOfSpeciesFeatures != null) {
      listOfSpeciesFeatures.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'multi'
      listOfSpeciesFeatures.setPackageName(null);
      listOfSpeciesFeatures.setPackageName(MultiConstants.shortLabel);
      this.listOfSpeciesFeatures.setSBaseListType(ListOf.Type.other);
      this.listOfSpeciesFeatures.setOtherListName(MultiConstants.listOfSpeciesFeatures);

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
   * if the index is out of bound (index &lt; 0 || index &gt; list.size).
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
   * Returns {@code true} if {@link #listOfSubListOfSpeciesFeatures} contains at least
   * one element.
   *
   * @return {@code true} if {@link #listOfSubListOfSpeciesFeatures} contains at least
   *         one element, otherwise {@code false}.
   */
  public boolean isSetListOfSubListOfSpeciesFeatures() {
    if (listOfSubListOfSpeciesFeatures == null) {
      return false;
    }
    return true;
  }

  /**
   * Returns the {@link #listOfSubListOfSpeciesFeatures}.
   * Creates it if it does not already exist.
   *
   * @return the {@link #listOfSubListOfSpeciesFeatures}.
   */
  public ListOf<SubListOfSpeciesFeature> getListOfSubListOfSpeciesFeatures() {
    if (listOfSubListOfSpeciesFeatures == null) {
      listOfSubListOfSpeciesFeatures = new ListOf<SubListOfSpeciesFeature>();
      listOfSubListOfSpeciesFeatures.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'multi'
      listOfSubListOfSpeciesFeatures.setPackageName(null);
      listOfSubListOfSpeciesFeatures.setPackageName(MultiConstants.shortLabel);
      listOfSubListOfSpeciesFeatures.setSBaseListType(ListOf.Type.other);
      listOfSubListOfSpeciesFeatures.setOtherListName(MultiConstants.listOfSubListOfSpeciesFeatures);

      if (isSetExtendedSBase()) {
        extendedSBase.registerChild(listOfSubListOfSpeciesFeatures);
      }
    }
    return listOfSubListOfSpeciesFeatures;
  }

  /**
   * Sets the given {@code ListOf<SubListOfSpeciesFeature>}.
   * If {@link #listOfSubListOfSpeciesFeatures} was defined before and contains some
   * elements, they are all unset.
   *
   * @param listOfSubListOfSpeciesFeatures the list of {@link SubListOfSpeciesFeature}s
   */
  public void setListOfSubListOfSpeciesFeatures(ListOf<SubListOfSpeciesFeature> listOfSubListOfSpeciesFeatures) {
    unsetListOfSubListOfSpeciesFeatures();
    this.listOfSubListOfSpeciesFeatures = listOfSubListOfSpeciesFeatures;

    if (listOfSubListOfSpeciesFeatures != null) {
      listOfSubListOfSpeciesFeatures.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'multi'
      listOfSubListOfSpeciesFeatures.setPackageName(null);
      listOfSubListOfSpeciesFeatures.setPackageName(MultiConstants.shortLabel);
      this.listOfSubListOfSpeciesFeatures.setSBaseListType(ListOf.Type.other);
      this.listOfSubListOfSpeciesFeatures.setOtherListName(MultiConstants.listOfSubListOfSpeciesFeatures);

      if (isSetExtendedSBase()) {
        extendedSBase.registerChild(this.listOfSubListOfSpeciesFeatures);
      }
    }

  }

  /**
   * Returns {@code true} if {@link #listOfSubListOfSpeciesFeatures} contains at least
   * one element, otherwise {@code false}.
   *
   * @return {@code true} if {@link #listOfSubListOfSpeciesFeatures} contains at least
   *         one element, otherwise {@code false}.
   */
  public boolean unsetListOfSubListOfSpeciesFeatures() {
    if (isSetListOfSubListOfSpeciesFeatures()) {
      ListOf<SubListOfSpeciesFeature> oldSubListOfSpeciesFeatures = this.listOfSubListOfSpeciesFeatures;
      this.listOfSubListOfSpeciesFeatures = null;
      oldSubListOfSpeciesFeatures.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /**
   * Adds a new {@link SubListOfSpeciesFeature} to the {@link #listOfSubListOfSpeciesFeatures}.
   * <p>The listOfSubListOfSpeciesFeatures is initialized if necessary.
   *
   * @param SubListOfSpeciesFeature the element to add to the list
   * @return {@code true} (as specified by {@link java.util.Collection#add})
   * @see java.util.Collection#add(Object)
   */
  public boolean addSubListOfSpeciesFeature(SubListOfSpeciesFeature SubListOfSpeciesFeature) {
    return getListOfSubListOfSpeciesFeatures().add(SubListOfSpeciesFeature);
  }

  /**
   * Removes an element from the {@link #listOfSubListOfSpeciesFeatures}.
   *
   * @param SubListOfSpeciesFeature the element to be removed from the list.
   * @return {@code true} if the list contained the specified element and it was
   *         removed.
   * @see java.util.List#remove(Object)
   */
  public boolean removeSubListOfSpeciesFeature(SubListOfSpeciesFeature SubListOfSpeciesFeature) {
    if (isSetListOfSubListOfSpeciesFeatures()) {
      return getListOfSubListOfSpeciesFeatures().remove(SubListOfSpeciesFeature);
    }
    return false;
  }

  /**
   * Removes an element from the {@link #listOfSubListOfSpeciesFeatures}.
   *
   * @param SubListOfSpeciesFeatureId the id of the element to be removed from the list.
   * @return the removed element, if it was successfully found and removed or
   *         {@code null}.
   */
  public SubListOfSpeciesFeature removeSubListOfSpeciesFeature(String SubListOfSpeciesFeatureId) {
    if (isSetListOfSubListOfSpeciesFeatures()) {
      return getListOfSubListOfSpeciesFeatures().remove(SubListOfSpeciesFeatureId);
    }
    return null;
  }

  /**
   * Removes an element from the {@link #listOfSubListOfSpeciesFeatures} at the given index.
   *
   * @param i the index where to remove the {@link SubListOfSpeciesFeature}.
   * @return the specified element if it was successfully found and removed.
   * @throws IndexOutOfBoundsException if the listOf is not set or if the index is
   *         out of bound ({@code (i < 0) || (i > listOfSubListOfSpeciesFeatures)}).
   */
  public SubListOfSpeciesFeature removeSubListOfSpeciesFeature(int i) {
    if (!isSetListOfSubListOfSpeciesFeatures()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfSubListOfSpeciesFeatures().remove(i);
  }

  /**
   * Creates a new SubListOfSpeciesFeature element and adds it to the
   * {@link #listOfSubListOfSpeciesFeatures} list.
   *
   * @return the newly created element, i.e., the last item in the
   *         {@link #listOfSubListOfSpeciesFeatures}
   */
  public SubListOfSpeciesFeature createSubListOfSpeciesFeature() {
    return createSubListOfSpeciesFeature(null);
  }

  /**
   * Creates a new {@link SubListOfSpeciesFeature} element and adds it to the
   * {@link #listOfSubListOfSpeciesFeatures} list.
   *
   * @param id the identifier that is to be applied to the new element.
   * @return the newly created {@link SubListOfSpeciesFeature} element, which is the last
   *         element in the {@link #listOfSubListOfSpeciesFeatures}.
   */
  public SubListOfSpeciesFeature createSubListOfSpeciesFeature(String id) {
    SubListOfSpeciesFeature SubListOfSpeciesFeature = new SubListOfSpeciesFeature(id);
    addSubListOfSpeciesFeature(SubListOfSpeciesFeature);
    return SubListOfSpeciesFeature;
  }

  /**
   * Gets an element from the {@link #listOfSubListOfSpeciesFeatures} at the given index.
   *
   * @param i the index of the {@link SubListOfSpeciesFeature} element to get.
   * @return an element from the listOfSubListOfSpeciesFeatures at the given index.
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index < 0 || index > list.size).
   */
  public SubListOfSpeciesFeature getSubListOfSpeciesFeature(int i) {
    if (!isSetListOfSubListOfSpeciesFeatures()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfSubListOfSpeciesFeatures().get(i);
  }

  /**
   * Gets an element from the listOfSubListOfSpeciesFeatures, with the given id.
   *
   * @param SubListOfSpeciesFeatureId the id of the {@link SubListOfSpeciesFeature} element to get.
   * @return an element from the listOfSubListOfSpeciesFeatures with the given id
   *         or {@code null}.
   */
  public SubListOfSpeciesFeature getSubListOfSpeciesFeature(String SubListOfSpeciesFeatureId) {
    if (isSetListOfSubListOfSpeciesFeatures()) {
      return getListOfSubListOfSpeciesFeatures().get(SubListOfSpeciesFeatureId);
    }
    return null;
  }

  /**
   * Returns the number of {@link SubListOfSpeciesFeature}s in this
   * {@link MultiSpeciesPlugin}.
   * 
   * @return the number of {@link SubListOfSpeciesFeature}s in this
   *         {@link MultiSpeciesPlugin}.
   */
  public int getSubListOfSpeciesFeatureCount() {
    return isSetListOfSubListOfSpeciesFeatures() ? getListOfSubListOfSpeciesFeatures().size() : 0;
  }

  /**
   * Returns the number of {@link SubListOfSpeciesFeature}s in this
   * {@link MultiSpeciesPlugin}.
   * 
   * @return the number of {@link SubListOfSpeciesFeature}s in this
   *         {@link MultiSpeciesPlugin}.
   * @libsbml.deprecated same as {@link #getSubListOfSpeciesFeatureCount()}
   */
  public int getNumSubListOfSpeciesFeatures() {
    return getSubListOfSpeciesFeatureCount();
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
    if (isSetListOfSpeciesFeatures() || isSetListOfSubListOfSpeciesFeatures()) {
      if (pos == childIndex) {
        return getCombinedListOfSpeciesFeatures();
      }
      pos++;
    }
    
    throw new IndexOutOfBoundsException(MessageFormat.format(
      resourceBundle.getString("IndexExceedsBoundsException"),
      childIndex, Math.min(pos, 0)));
  }

  /**
   * Merges listOfSpeciesFeature and listOfSubListOfSpeciesFeatures into one combined ListOf<ListOfSpeciesFeatureContent>.
   * 
   * <p>At the moment the file order is not kept and {@link SpeciesFeature}s are put first, 
   * followed by any {@link SubListOfSpeciesFeature}s.
   * 
   * @return a combined ListOf containing all {@link SpeciesFeature}s and {@link SubListOfSpeciesFeature}s.
   */
  private ListOf<ListOfSpeciesFeatureContent> getCombinedListOfSpeciesFeatures() {
    ListOf<ListOfSpeciesFeatureContent> combinedListOfSpeciesFeatures = new ListOf<ListOfSpeciesFeatureContent>();
    combinedListOfSpeciesFeatures.setPackageVersion(-1);
    combinedListOfSpeciesFeatures.setSBaseListType(Type.other);
    combinedListOfSpeciesFeatures.setPackageName(null);
    combinedListOfSpeciesFeatures.setPackageName(MultiConstants.shortLabel);
    combinedListOfSpeciesFeatures.setOtherListName(MultiConstants.listOfSpeciesFeatures);

    // We cannot register the list as otherwise the ids would be registered and would create exceptions
    // if (isSetExtendedSBase()) {
    //   extendedSBase.registerChild(combinedListOfSpeciesFeatures);
    // }
    
    if (isSetListOfSpeciesFeatures()) {
      combinedListOfSpeciesFeatures.addAll(getListOfSpeciesFeatures());
    }
    if (isSetListOfSubListOfSpeciesFeatures()) {
      combinedListOfSpeciesFeatures.addAll(getListOfSubListOfSpeciesFeatures());
    }
    
    return combinedListOfSpeciesFeatures;
  }


  @Override
  public int getChildCount() {
    int count = 0;

    if (isSetListOfOutwardBindingSites()) {
      count++;
    }
    if (isSetListOfSpeciesFeatures() || isSetListOfSubListOfSpeciesFeatures()) {
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

    if (attributeName.equals(MultiConstants.speciesType)) {
      setSpeciesType(value);
      return true;
    }

    return false;
  }


  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 5903;
    int result = super.hashCode();
    result = prime * result + ((listOfOutwardBindingSites == null) ? 0
        : listOfOutwardBindingSites.hashCode());
    result = prime * result + ((listOfSpeciesFeatures == null) ? 0
        : listOfSpeciesFeatures.hashCode());
    result = prime * result + ((listOfSubListOfSpeciesFeatures == null) ? 0
        : listOfSubListOfSpeciesFeatures.hashCode());
    result = prime * result
        + ((speciesType == null) ? 0 : speciesType.hashCode());
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
    MultiSpeciesPlugin other = (MultiSpeciesPlugin) obj;
    if (listOfOutwardBindingSites == null) {
      if (other.listOfOutwardBindingSites != null) {
        return false;
      }
    } else if (!listOfOutwardBindingSites
        .equals(other.listOfOutwardBindingSites)) {
      return false;
    }
    if (listOfSpeciesFeatures == null) {
      if (other.listOfSpeciesFeatures != null) {
        return false;
      }
    } else if (!listOfSpeciesFeatures.equals(other.listOfSpeciesFeatures)) {
      return false;
    }
    if (listOfSubListOfSpeciesFeatures == null) {
      if (other.listOfSubListOfSpeciesFeatures != null) {
        return false;
      }
    } else if (!listOfSubListOfSpeciesFeatures
        .equals(other.listOfSubListOfSpeciesFeatures)) {
      return false;
    }
    if (speciesType == null) {
      if (other.speciesType != null) {
        return false;
      }
    } else if (!speciesType.equals(other.speciesType)) {
      return false;
    }
    return true;
  }

  
  
}
