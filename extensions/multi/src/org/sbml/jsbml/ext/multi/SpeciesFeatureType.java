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
import org.sbml.jsbml.Species;
import org.sbml.jsbml.UniqueNamedSBase;
import org.sbml.jsbml.util.StringTools;

/**
 * A species type ({@link MultiSpeciesType}) can carry any number of features
 * ({@link SpeciesFeatureType}), which are characteristic properties specific
 * for this type of species ({@link Species}). The element {@link SpeciesFeatureType}
 * of SBML Level 3 Version 1 multi Version 1
 * corresponds to the "state variable" of the SBGN Entity Relationship language.
 * A {@link SpeciesFeatureType} is identified by an id and an optional name. A
 * {@link SpeciesFeatureType} is linked to a list of possible values.
 * 
 * @author Nicolas Rodriguez
 * @since 1.0
 */
public class SpeciesFeatureType extends AbstractNamedSBase implements UniqueNamedSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -1796119514784158560L;
  /**
   * 
   */
  private ListOf<PossibleSpeciesFeatureValue> listOfPossibleSpeciesFeatureValues;

  /**
   * 
   */
  private Integer occur;



  /**
   * Creates an SpeciesFeatureType instance
   */
  public SpeciesFeatureType() {
    super();
    initDefaults();
  }


  /**
   * Creates a SpeciesFeatureType instance with an id.
   * 
   * @param id the identifier for the new element.
   */
  public SpeciesFeatureType(String id) {
    super(id);
    initDefaults();
  }


  /**
   * Creates a SpeciesFeatureType instance with a level and version.
   * 
   * @param level SBML Level
   * @param version SBML Version
   */
  public SpeciesFeatureType(int level, int version) {
    this(null, null, level, version);
  }


  /**
   * Creates a SpeciesFeatureType instance with an id, level, and version.
   * 
   * @param id the identifier for this element.
   * @param level the SBML Level
   * @param version the SBML Version
   */
  public SpeciesFeatureType(String id, int level, int version) {
    this(id, null, level, version);
  }


  /**
   * Creates a SpeciesFeatureType instance with an id, name, level, and version.
   * 
   * @param id the identifier for this element.
   * @param name a human-readable name for this element that can be used for display purposes.
   * @param level the SBML Level
   * @param version the SBML Version
   */
  public SpeciesFeatureType(String id, String name, int level, int version) {
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
  public SpeciesFeatureType(SpeciesFeatureType obj) {
    super(obj);

    if (obj.isSetListOfPossibleSpeciesFeatureValues()) {
      setListOfPossibleSpeciesFeatureValues(obj.getListOfPossibleSpeciesFeatureValues().clone());
    }
    if (obj.isSetOccur()) {
      setOccur(obj.getOccur());
    }

  }


  /**
   * clones this class
   */
  @Override
  public SpeciesFeatureType clone() {
    return new SpeciesFeatureType(this);
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
    final int prime = 5623;
    int result = super.hashCode();
    result = prime
        * result
        + ((listOfPossibleSpeciesFeatureValues == null) ? 0
          : listOfPossibleSpeciesFeatureValues.hashCode());
    result = prime * result + ((occur == null) ? 0 : occur.hashCode());
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
    SpeciesFeatureType other = (SpeciesFeatureType) obj;
    if (listOfPossibleSpeciesFeatureValues == null) {
      if (other.listOfPossibleSpeciesFeatureValues != null) {
        return false;
      }
    } else if (!listOfPossibleSpeciesFeatureValues.equals(other.listOfPossibleSpeciesFeatureValues)) {
      return false;
    }
    if (occur == null) {
      if (other.occur != null) {
        return false;
      }
    } else if (!occur.equals(other.occur)) {
      return false;
    }
    return true;
  }

  /**
   * Returns {@code true} if {@link #listOfPossibleSpeciesFeatureValues} contains at least
   * one element.
   *
   * @return {@code true} if {@link #listOfPossibleSpeciesFeatureValues} contains at least
   *         one element, otherwise {@code false}.
   */
  public boolean isSetListOfPossibleSpeciesFeatureValues() {
    if ((listOfPossibleSpeciesFeatureValues == null) || listOfPossibleSpeciesFeatureValues.isEmpty()) {
      return false;
    }
    return true;
  }


  /**
   * Returns the {@link #listOfPossibleSpeciesFeatureValues}.
   * Creates it if it does not already exist.
   *
   * @return the {@link #listOfPossibleSpeciesFeatureValues}.
   */
  public ListOf<PossibleSpeciesFeatureValue> getListOfPossibleSpeciesFeatureValues() {
    if (listOfPossibleSpeciesFeatureValues == null) {
      listOfPossibleSpeciesFeatureValues = new ListOf<PossibleSpeciesFeatureValue>();
      listOfPossibleSpeciesFeatureValues.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'multi'
      listOfPossibleSpeciesFeatureValues.setPackageName(null);
      listOfPossibleSpeciesFeatureValues.setPackageName(MultiConstants.shortLabel);
      listOfPossibleSpeciesFeatureValues.setSBaseListType(ListOf.Type.other);
      listOfPossibleSpeciesFeatureValues.setOtherListName(MultiConstants.listOfPossibleSpeciesFeatureValues);
      
      registerChild(listOfPossibleSpeciesFeatureValues);
    }
    return listOfPossibleSpeciesFeatureValues;
  }


  /**
   * Sets the given {@code ListOf<PossibleSpeciesFeatureValue>}.
   * If {@link #listOfPossibleSpeciesFeatureValues} was defined before and contains some
   * elements, they are all unset.
   *
   * @param listOfPossibleSpeciesFeatureValues the list of {@link PossibleSpeciesFeatureValue}
   */
  public void setListOfPossibleSpeciesFeatureValues(ListOf<PossibleSpeciesFeatureValue> listOfPossibleSpeciesFeatureValues) {
    unsetListOfPossibleSpeciesFeatureValues();
    this.listOfPossibleSpeciesFeatureValues = listOfPossibleSpeciesFeatureValues;

    if (listOfPossibleSpeciesFeatureValues != null) {
      listOfPossibleSpeciesFeatureValues.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'multi'
      listOfPossibleSpeciesFeatureValues.setPackageName(null);
      listOfPossibleSpeciesFeatureValues.setPackageName(MultiConstants.shortLabel);
      listOfPossibleSpeciesFeatureValues.setSBaseListType(ListOf.Type.other);
      listOfPossibleSpeciesFeatureValues.setOtherListName(MultiConstants.listOfPossibleSpeciesFeatureValues);
      
      registerChild(listOfPossibleSpeciesFeatureValues);
    }
  }


  /**
   * Returns {@code true} if {@link #listOfPossibleSpeciesFeatureValues} contains at least
   * one element, otherwise {@code false}.
   *
   * @return {@code true} if {@link #listOfPossibleSpeciesFeatureValues} contains at least
   *         one element, otherwise {@code false}.
   */
  public boolean unsetListOfPossibleSpeciesFeatureValues() {
    if (isSetListOfPossibleSpeciesFeatureValues()) {
      ListOf<PossibleSpeciesFeatureValue> oldPossibleSpeciesFeatureValues = listOfPossibleSpeciesFeatureValues;
      listOfPossibleSpeciesFeatureValues = null;
      oldPossibleSpeciesFeatureValues.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }


  /**
   * Adds a new {@link PossibleSpeciesFeatureValue} to the {@link #listOfPossibleSpeciesFeatureValues}.
   * <p>The listOfPossibleSpeciesFeatureValues is initialized if necessary.
   *
   * @param possibleSpeciesFeatureValue the element to add to the list
   * @return {@code true} (as specified by {@link java.util.Collection#add})
   * @see java.util.Collection#add(Object)
   */
  public boolean addPossibleSpeciesFeatureValue(PossibleSpeciesFeatureValue possibleSpeciesFeatureValue) {
    return getListOfPossibleSpeciesFeatureValues().add(possibleSpeciesFeatureValue);
  }


  /**
   * Removes an element from the {@link #listOfPossibleSpeciesFeatureValues}.
   *
   * @param possibleSpeciesFeatureValue the element to be removed from the list.
   * @return {@code true} if the list contained the specified element and it was
   *         removed.
   * @see java.util.List#remove(Object)
   */
  public boolean removePossibleSpeciesFeatureValue(PossibleSpeciesFeatureValue possibleSpeciesFeatureValue) {
    if (isSetListOfPossibleSpeciesFeatureValues()) {
      return getListOfPossibleSpeciesFeatureValues().remove(possibleSpeciesFeatureValue);
    }
    return false;
  }

  /**
   * Removes an element from the {@link #listOfPossibleSpeciesFeatureValues}.
   *
   * @param possibleSpeciesFeatureValueId the id of the element to be removed from the list.
   * @return the removed element, if it was successfully found and removed or
   *         {@code null}.
   */
  public PossibleSpeciesFeatureValue removePossibleSpeciesFeatureValue(String possibleSpeciesFeatureValueId) {
    if (isSetListOfPossibleSpeciesFeatureValues()) {
      return getListOfPossibleSpeciesFeatureValues().remove(possibleSpeciesFeatureValueId);
    }
    return null;
  }


  /**
   * Removes an element from the {@link #listOfPossibleSpeciesFeatureValues} at the given index.
   *
   * @param i the index where to remove the {@link PossibleSpeciesFeatureValue}.
   * @return the specified element if it was successfully found and removed.
   * @throws IndexOutOfBoundsException if the listOf is not set or if the index is
   *         out of bound ({@code (i < 0) || (i > listOfPossibleSpeciesFeatureValues)}).
   */
  public PossibleSpeciesFeatureValue removePossibleSpeciesFeatureValue(int i) {
    if (!isSetListOfPossibleSpeciesFeatureValues()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfPossibleSpeciesFeatureValues().remove(i);
  }


  /**
   * Creates a new PossibleSpeciesFeatureValue element and adds it to the
   * {@link #listOfPossibleSpeciesFeatureValues} list.
   *
   * @return the newly created element, i.e., the last item in the
   *         {@link #listOfPossibleSpeciesFeatureValues}
   */
  public PossibleSpeciesFeatureValue createPossibleSpeciesFeatureValue() {
    return createPossibleSpeciesFeatureValue(null);
  }


  /**
   * Creates a new {@link PossibleSpeciesFeatureValue} element and adds it to the
   * {@link #listOfPossibleSpeciesFeatureValues} list.
   *
   * @param id the identifier that is to be applied to the new element.
   * @return the newly created {@link PossibleSpeciesFeatureValue} element, which is the last
   *         element in the {@link #listOfPossibleSpeciesFeatureValues}.
   */
  public PossibleSpeciesFeatureValue createPossibleSpeciesFeatureValue(String id) {
    PossibleSpeciesFeatureValue possibleSpeciesFeatureValue = new PossibleSpeciesFeatureValue(id);
    addPossibleSpeciesFeatureValue(possibleSpeciesFeatureValue);
    return possibleSpeciesFeatureValue;
  }


  /**
   * Gets an element from the {@link #listOfPossibleSpeciesFeatureValues} at the given index.
   *
   * @param i the index of the {@link PossibleSpeciesFeatureValue} element to get.
   * @return an element from the listOfPossibleSpeciesFeatureValues at the given index.
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index &lt; 0 || index &gt; list.size).
   */
  public PossibleSpeciesFeatureValue getPossibleSpeciesFeatureValue(int i) {
    if (!isSetListOfPossibleSpeciesFeatureValues()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfPossibleSpeciesFeatureValues().get(i);
  }


  /**
   * Gets an element from the listOfPossibleSpeciesFeatureValues, with the given id.
   *
   * @param possibleSpeciesFeatureValueId the id of the {@link PossibleSpeciesFeatureValue} element to get.
   * @return an element from the listOfPossibleSpeciesFeatureValues with the given id
   *         or {@code null}.
   */
  public PossibleSpeciesFeatureValue getPossibleSpeciesFeatureValue(String possibleSpeciesFeatureValueId) {
    if (isSetListOfPossibleSpeciesFeatureValues()) {
      return getListOfPossibleSpeciesFeatureValues().get(possibleSpeciesFeatureValueId);
    }
    return null;
  }


  /**
   * Returns the number of {@link PossibleSpeciesFeatureValue}s in this
   * {@link SpeciesFeatureType}.
   * 
   * @return the number of {@link PossibleSpeciesFeatureValue}s in this
   *         {@link SpeciesFeatureType}.
   */
  public int getPossibleSpeciesFeatureValueCount() {
    return isSetListOfPossibleSpeciesFeatureValues() ? getListOfPossibleSpeciesFeatureValues().size() : 0;
  }


  /**
   * Returns the number of {@link PossibleSpeciesFeatureValue}s in this
   * {@link SpeciesFeatureType}.
   * 
   * @return the number of {@link PossibleSpeciesFeatureValue}s in this
   *         {@link SpeciesFeatureType}.
   * @libsbml.deprecated same as {@link #getPossibleSpeciesFeatureValueCount()}
   */
  public int getNumPossibleSpeciesFeatureValues() {
    return getPossibleSpeciesFeatureValueCount();
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

  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
   */
  @Override
  public boolean isIdMandatory() {
    return false;
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
    if (isSetListOfPossibleSpeciesFeatureValues()) {
      if (pos == index) {
        return getListOfPossibleSpeciesFeatureValues();
      }
      pos++;
    }

    throw new IndexOutOfBoundsException(MessageFormat.format(
      resourceBundle.getString("IndexExceedsBoundsException"),
      index, Math.min(pos, 0)));
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildCount()
   */
  @Override
  public int getChildCount() {
    int count = super.getChildCount();

    if (isSetListOfPossibleSpeciesFeatureValues()) {
      count++;
    }

    return count;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#writeXMLAttributes()
   */
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
      attributes.put(MultiConstants.shortLabel+ ":" + MultiConstants.occur, occur.toString());
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
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }
}
