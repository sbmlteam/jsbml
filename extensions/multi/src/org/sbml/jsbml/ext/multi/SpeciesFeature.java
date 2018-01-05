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

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.LevelVersionError;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.ext.multi.util.ListOfSpeciesFeatureContent;
import org.sbml.jsbml.util.StringTools;

/**
 * Defines the state of a component in a species by selecting values from
 * the listOfPossibleSpeciesFeatureValues of the referenced {@link SpeciesFeatureType}.
 *
 * @author Nicolas Rodriguez
 * @since 1.1
 */
public class SpeciesFeature extends AbstractNamedSBase implements ListOfSpeciesFeatureContent {

  /**
   * Serial version identifier.
   */
  private static final long serialVersionUID = 1L;

  /**
   * 
   */
  private String speciesFeatureType;

  /**
   * 
   */
  private Integer occur;

  /**
   * 
   */
  private String component;

  /**
   * 
   */
  private ListOf<SpeciesFeatureValue> listOfSpeciesFeatureValues;

  /**
   * Creates an SpeciesFeature instance
   */
  public SpeciesFeature() {
    super();
    initDefaults();
  }


  /**
   * Creates a SpeciesFeature instance with an id.
   * 
   * @param id the identifier for the new element.
   */
  public SpeciesFeature(String id) {
    super(id);
    initDefaults();
  }


  /**
   * Creates a SpeciesFeature instance with a level and version.
   * 
   * @param level SBML Level
   * @param version SBML Version
   */
  public SpeciesFeature(int level, int version) {
    this(null, null, level, version);
  }


  /**
   * Creates a SpeciesFeature instance with an id, level, and version.
   * 
   * @param id the identifier for this element.
   * @param level the SBML Level
   * @param version the SBML Version
   */
  public SpeciesFeature(String id, int level, int version) {
    this(id, null, level, version);
  }


  /**
   * Creates a SpeciesFeature instance with an id, name, level, and version.
   * 
   * @param id the identifier for this element.
   * @param name a human-readable name for this element that can be used for display purposes.
   * @param level the SBML Level
   * @param version the SBML Version
   */
  public SpeciesFeature(String id, String name, int level, int version) {
    super(id, name, level, version);
    if (getLevelAndVersion().compareTo(
      Integer.valueOf(MultiConstants.MIN_SBML_LEVEL),
      Integer.valueOf(MultiConstants.MIN_SBML_VERSION)) < 0) {
      throw new LevelVersionError(getElementName(), level, version);
    }
    initDefaults();
  }


  /**
   * Clone constructor
   */
  public SpeciesFeature(SpeciesFeature obj) {
    super(obj);

    // copy all class attributes
    if (obj.isSetListOfSpeciesFeatureValues()) {
      setListOfSpeciesFeatureValues(obj.getListOfSpeciesFeatureValues().clone());
    }
    if (obj.isSetSpeciesFeatureType()) {
      setSpeciesFeatureType(obj.getSpeciesFeatureType());
    }
    if (obj.isSetOccur()) {
      setOccur(obj.getOccur());
    }
    if (obj.isSetComponent()) {
      setComponent(obj.getComponent());
    }
  }


  /**
   * clones this class
   */
  @Override
  public SpeciesFeature clone() {
    return new SpeciesFeature(this);
  }


  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    packageName = MultiConstants.shortLabel;
    setPackageVersion(-1);
  }




  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 6133;
    int result = super.hashCode();
    result = prime * result + ((component == null) ? 0 : component.hashCode());
    result = prime
        * result
        + ((listOfSpeciesFeatureValues == null) ? 0
          : listOfSpeciesFeatureValues.hashCode());
    result = prime * result + ((occur == null) ? 0 : occur.hashCode());
    result = prime * result
        + ((speciesFeatureType == null) ? 0 : speciesFeatureType.hashCode());
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
    SpeciesFeature other = (SpeciesFeature) obj;
    if (component == null) {
      if (other.component != null) {
        return false;
      }
    } else if (!component.equals(other.component)) {
      return false;
    }
    if (listOfSpeciesFeatureValues == null) {
      if (other.listOfSpeciesFeatureValues != null) {
        return false;
      }
    } else if (!listOfSpeciesFeatureValues.equals(other.listOfSpeciesFeatureValues)) {
      return false;
    }
    if (occur == null) {
      if (other.occur != null) {
        return false;
      }
    } else if (!occur.equals(other.occur)) {
      return false;
    }
    if (speciesFeatureType == null) {
      if (other.speciesFeatureType != null) {
        return false;
      }
    } else if (!speciesFeatureType.equals(other.speciesFeatureType)) {
      return false;
    }
    return true;
  }


  /**
   * Returns {@code true} if {@link #listOfSpeciesFeatureValues} contains at least
   * one element.
   *
   * @return {@code true} if {@link #listOfSpeciesFeatureValues} contains at least
   *         one element, otherwise {@code false}.
   */
  public boolean isSetListOfSpeciesFeatureValues() {
    if (listOfSpeciesFeatureValues == null) {
      return false;
    }
    return true;
  }


  /**
   * Returns the {@link #listOfSpeciesFeatureValues}.
   * Creates it if it does not already exist.
   *
   * @return the {@link #listOfSpeciesFeatureValues}.
   */
  public ListOf<SpeciesFeatureValue> getListOfSpeciesFeatureValues() {
    if (listOfSpeciesFeatureValues == null) {
      listOfSpeciesFeatureValues = new ListOf<SpeciesFeatureValue>();
      listOfSpeciesFeatureValues.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'multi'
      listOfSpeciesFeatureValues.setPackageName(null);
      listOfSpeciesFeatureValues.setPackageName(MultiConstants.shortLabel);
      listOfSpeciesFeatureValues.setSBaseListType(ListOf.Type.other);
      listOfSpeciesFeatureValues.setOtherListName(MultiConstants.listOfSpeciesFeatureValues);

      registerChild(listOfSpeciesFeatureValues);
    }
    return listOfSpeciesFeatureValues;
  }


  /**
   * Sets the given {@code ListOf<SpeciesFeatureValue>}.
   * If {@link #listOfSpeciesFeatureValues} was defined before and contains some
   * elements, they are all unset.
   *
   * @param listOfSpeciesFeatureValues the list of {@link SpeciesFeatureValue}s to set
   */
  public void setListOfSpeciesFeatureValues(ListOf<SpeciesFeatureValue> listOfSpeciesFeatureValues) {
    unsetListOfSpeciesFeatureValues();
    this.listOfSpeciesFeatureValues = listOfSpeciesFeatureValues;

    if (listOfSpeciesFeatureValues != null) {
      listOfSpeciesFeatureValues.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'multi'
      listOfSpeciesFeatureValues.setPackageName(null);
      listOfSpeciesFeatureValues.setPackageName(MultiConstants.shortLabel);
      listOfSpeciesFeatureValues.setSBaseListType(ListOf.Type.other);
      listOfSpeciesFeatureValues.setOtherListName(MultiConstants.listOfSpeciesFeatureValues);
      
      registerChild(listOfSpeciesFeatureValues);
    }
  }


  /**
   * Returns {@code true} if {@link #listOfSpeciesFeatureValues} contains at least
   * one element, otherwise {@code false}.
   *
   * @return {@code true} if {@link #listOfSpeciesFeatureValues} contains at least
   *         one element, otherwise {@code false}.
   */
  public boolean unsetListOfSpeciesFeatureValues() {
    if (isSetListOfSpeciesFeatureValues()) {
      ListOf<SpeciesFeatureValue> oldSpeciesFeatureValues = listOfSpeciesFeatureValues;
      listOfSpeciesFeatureValues = null;
      oldSpeciesFeatureValues.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }


  /**
   * Adds a new {@link SpeciesFeatureValue} to the {@link #listOfSpeciesFeatureValues}.
   * <p>The listOfSpeciesFeatureValues is initialized if necessary.
   *
   * @param speciesFeatureValue the element to add to the list
   * @return {@code true} (as specified by {@link java.util.Collection#add})
   * @see java.util.Collection#add(Object)
   */
  public boolean addSpeciesFeatureValue(SpeciesFeatureValue speciesFeatureValue) {
    return getListOfSpeciesFeatureValues().add(speciesFeatureValue);
  }


  /**
   * Removes an element from the {@link #listOfSpeciesFeatureValues}.
   *
   * @param speciesFeatureValue the element to be removed from the list.
   * @return {@code true} if the list contained the specified element and it was
   *         removed.
   * @see java.util.List#remove(Object)
   */
  public boolean removeSpeciesFeatureValue(SpeciesFeatureValue speciesFeatureValue) {
    if (isSetListOfSpeciesFeatureValues()) {
      return getListOfSpeciesFeatureValues().remove(speciesFeatureValue);
    }
    return false;
  }


  /**
   * Removes an element from the {@link #listOfSpeciesFeatureValues} at the given index.
   *
   * @param i the index where to remove the {@link SpeciesFeatureValue}.
   * @return the specified element if it was successfully found and removed.
   * @throws IndexOutOfBoundsException if the listOf is not set or if the index is
   *         out of bound ({@code (i < 0) || (i > listOfSpeciesFeatureValues)}).
   */
  public SpeciesFeatureValue removeSpeciesFeatureValue(int i) {
    if (!isSetListOfSpeciesFeatureValues()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfSpeciesFeatureValues().remove(i);
  }


  /**
   * Creates a new SpeciesFeatureValue element and adds it to the
   * {@link #listOfSpeciesFeatureValues} list.
   *
   * @return the newly created element, i.e., the last item in the
   *         {@link #listOfSpeciesFeatureValues}
   */
  public SpeciesFeatureValue createSpeciesFeatureValue() {
    SpeciesFeatureValue sfv = new SpeciesFeatureValue();
    addSpeciesFeatureValue(sfv);
    return sfv;
  }


  /**
   * Gets an element from the {@link #listOfSpeciesFeatureValues} at the given index.
   *
   * @param i the index of the {@link SpeciesFeatureValue} element to get.
   * @return an element from the listOfSpeciesFeatureValues at the given index.
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index &lt; 0 || index &gt; list.size).
   */
  public SpeciesFeatureValue getSpeciesFeatureValue(int i) {
    if (!isSetListOfSpeciesFeatureValues()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfSpeciesFeatureValues().get(i);
  }


  /**
   * Returns the number of {@link SpeciesFeatureValue}s in this
   * {@link SpeciesFeature}.
   * 
   * @return the number of {@link SpeciesFeatureValue}s in this
   *         {@link SpeciesFeature}.
   */
  public int getSpeciesFeatureValueCount() {
    return isSetListOfSpeciesFeatureValues() ? getListOfSpeciesFeatureValues().size() : 0;
  }


  /**
   * Returns the number of {@link SpeciesFeatureValue}s in this
   * {@link SpeciesFeature}.
   * 
   * @return the number of {@link SpeciesFeatureValue}s in this
   *         {@link SpeciesFeature}.
   * @libsbml.deprecated same as {@link #getSpeciesFeatureValueCount()}
   */
  public int getNumSpeciesFeatureValues() {
    return getSpeciesFeatureValueCount();
  }



  /**
   * Returns the value of {@link #speciesFeatureType}.
   *
   * @return the value of {@link #speciesFeatureType}.
   */
  public String getSpeciesFeatureType() {
    if (isSetSpeciesFeatureType()) {
      return speciesFeatureType;
    }

    return null;
  }


  /**
   * Returns whether {@link #speciesFeatureType} is set.
   *
   * @return whether {@link #speciesFeatureType} is set.
   */
  public boolean isSetSpeciesFeatureType() {
    return speciesFeatureType != null;
  }


  /**
   * Sets the value of speciesFeatureType
   *
   * @param speciesFeatureType the value of speciesFeatureType to be set.
   */
  public void setSpeciesFeatureType(String speciesFeatureType) {
    String oldSpeciesFeatureType = this.speciesFeatureType;
    this.speciesFeatureType = speciesFeatureType;
    firePropertyChange(MultiConstants.speciesFeatureType, oldSpeciesFeatureType, this.speciesFeatureType);
  }


  /**
   * Unsets the variable speciesFeatureType.
   *
   * @return {@code true} if speciesFeatureType was set before, otherwise {@code false}.
   */
  public boolean unsetSpeciesFeatureType() {
    if (isSetSpeciesFeatureType()) {
      String oldSpeciesFeatureType = speciesFeatureType;
      speciesFeatureType = null;
      firePropertyChange(MultiConstants.speciesFeatureType, oldSpeciesFeatureType, speciesFeatureType);
      return true;
    }
    return false;
  }


  /**
   * Returns the value of {@link #occur}.
   *
   * @return the value of {@link #occur}.
   */
  public int getOccur() {
    if (isSetOccur()) {
      return occur;
    }
    // This is necessary if we cannot return null here. For variables of type String return an empty String if no value is defined.
    throw new PropertyUndefinedError(MultiConstants.occur, this);
  }


  /**
   * Returns whether {@link #occur} is set.
   *
   * @return whether {@link #occur} is set.
   */
  public boolean isSetOccur() {
    return occur != null;
  }


  /**
   * Sets the value of occur
   *
   * @param occur the value of occur to be set.
   */
  public void setOccur(int occur) {
    Integer oldOccur = this.occur;
    this.occur = occur;
    firePropertyChange(MultiConstants.occur, oldOccur, this.occur);
  }


  /**
   * Unsets the variable occur.
   *
   * @return {@code true} if occur was set before, otherwise {@code false}.
   */
  public boolean unsetOccur() {
    if (isSetOccur()) {
      Integer oldOccur = occur;
      occur = null;
      firePropertyChange(MultiConstants.occur, oldOccur, occur);
      return true;
    }
    return false;
  }


  /**
   * Returns the value of {@link #component}.
   *
   * @return the value of {@link #component}.
   */
  public String getComponent() {
    if (isSetComponent()) {
      return component;
    }

    return null;
  }


  /**
   * Returns whether {@link #component} is set.
   *
   * @return whether {@link #component} is set.
   */
  public boolean isSetComponent() {
    return component != null;
  }


  /**
   * Sets the value of component
   *
   * @param component the value of component to be set.
   */
  public void setComponent(String component) {
    String oldComponent = this.component;
    this.component = component;
    firePropertyChange(MultiConstants.component, oldComponent, this.component);
  }


  /**
   * Unsets the variable component.
   *
   * @return {@code true} if component was set before, otherwise {@code false}.
   */
  public boolean unsetComponent() {
    if (isSetComponent()) {
      String oldComponent = component;
      component = null;
      firePropertyChange(MultiConstants.component, oldComponent, component);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
   */
  @Override
  public boolean isIdMandatory() {
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return true;
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildCount()
   */
  @Override
  public int getChildCount() {
    int count = super.getChildCount();

    if (isSetListOfSpeciesFeatureValues()) {
      count++;
    }

    return count;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
   */
  @Override
  public TreeNode getChildAt(int index) {
    if (index < 0) {
      throw new IndexOutOfBoundsException(MessageFormat.format(
        resourceBundle.getString("IndexSurpassesBoundsException"), index, 0));
    }
    int count = super.getChildCount(), pos = 0;
    if (index < count) {
      return super.getChildAt(index);
    } else {
      index -= count;
    }

    if (isSetListOfSpeciesFeatureValues()) {
      if (pos == index) {
        return getListOfSpeciesFeatureValues();
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

    if (isSetId()) {
      attributes.remove("id");
      attributes.put(MultiConstants.shortLabel+ ":id", getId());
    }
    if (isSetName()) {
      attributes.remove("name");
      attributes.put(MultiConstants.shortLabel+ ":name", getName());
    }

    if (isSetOccur()) {
      attributes.put(MultiConstants.shortLabel + ":" + MultiConstants.occur, occur.toString());
    }
    if (isSetSpeciesFeatureType()) {
      attributes.put(MultiConstants.shortLabel + ":" + MultiConstants.speciesFeatureType, getSpeciesFeatureType());
    }
    if (isSetComponent()) {
      attributes.put(MultiConstants.shortLabel + ":" + MultiConstants.component, getComponent());
    }

    return attributes;
  }


  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
    if (!isAttributeRead) {
      isAttributeRead = true;

      if (attributeName.equals(MultiConstants.occur)) {
        setOccur(StringTools.parseSBMLInt(value));
      }
      else if (attributeName.equals(MultiConstants.speciesFeatureType)) {
        setSpeciesFeatureType(value);
      }
      else if (attributeName.equals(MultiConstants.component)) {
        setComponent(value);
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }
}
