/*
 * $Id: SpeciesTypeComponentIndex.java 2186 2015-04-20 11:37:47Z andreas-draeger $
 * $URL: svn://svn.code.sf.net/p/jsbml/code/trunk/extensions/multi/src/org/sbml/jsbml/ext/multi/SpeciesTypeComponentIndex.java $
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

import java.util.Map;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.LevelVersionError;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.util.StringTools;


/**
 *
 * @author Nicolas Rodriguez
 * @version $Rev: 2186 $
 * @since 1.1
 */
public class SpeciesTypeComponentIndex extends AbstractNamedSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 8707971676849587247L;

  /**
   * 
   */
  private String component;

  /**
   * 
   */
  private String identifyingParent;

  /**
   * 
   */
  private Integer occur;

  /**
   * 
   */
  private ListOf<DenotedSpeciesTypeComponentIndex> listOfDenotedSpeciesTypeComponentIndexes;


  /**
   * Creates an SpeciesTypeComponentIndex instance
   */
  public SpeciesTypeComponentIndex() {
    super();
    initDefaults();
  }


  /**
   * Creates a SpeciesTypeComponentIndex instance with an id.
   * 
   * @param id the identifier for the new element.
   */
  public SpeciesTypeComponentIndex(String id) {
    super(id);
    initDefaults();
  }


  /**
   * Creates a SpeciesTypeComponentIndex instance with a level and version.
   * 
   * @param level SBML Level
   * @param version SBML Version
   */
  public SpeciesTypeComponentIndex(int level, int version) {
    this(null, null, level, version);
  }


  /**
   * Creates a SpeciesTypeComponentIndex instance with an id, level, and version.
   * 
   * @param id the identifier for this element.
   * @param level the SBML Level
   * @param version the SBML Version
   */
  public SpeciesTypeComponentIndex(String id, int level, int version) {
    this(id, null, level, version);
  }


  /**
   * Creates a SpeciesTypeComponentIndex instance with an id, name, level, and version.
   * 
   * @param id the identifier for this element.
   * @param name a human-readable name for this element that can be used for display purposes.
   * @param level the SBML Level
   * @param version the SBML Version
   */
  public SpeciesTypeComponentIndex(String id, String name, int level,
    int version) {
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
   * @param obj
   */
  public SpeciesTypeComponentIndex(SpeciesTypeComponentIndex obj) {
    super(obj);

    // copy all class attributes
    if (obj.isSetListOfDenotedSpeciesTypeComponentIndexes()) {
      setListOfDenotedSpeciesTypeComponentIndexes(obj.getListOfDenotedSpeciesTypeComponentIndexes());
    }
    if (obj.isSetComponent()) {
      setComponent(obj.getComponent());
    }
    if (obj.isSetIndentifyingParent()) {
      setIndentifyingParent(obj.getIndentifyingParent());
    }
    if (obj.isSetOccur()) {
      setOccur(obj.getOccur());
    }
  }


  /**
   * clones this class
   */
  @Override
  public SpeciesTypeComponentIndex clone() {
    return new SpeciesTypeComponentIndex(this);
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
    final int prime = 5743;
    int result = super.hashCode();
    result = prime * result + ((component == null) ? 0 : component.hashCode());
    result = prime * result
        + ((identifyingParent == null) ? 0 : identifyingParent.hashCode());
    result = prime
        * result
        + ((listOfDenotedSpeciesTypeComponentIndexes == null) ? 0
          : listOfDenotedSpeciesTypeComponentIndexes.hashCode());
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
    SpeciesTypeComponentIndex other = (SpeciesTypeComponentIndex) obj;
    if (component == null) {
      if (other.component != null) {
        return false;
      }
    } else if (!component.equals(other.component)) {
      return false;
    }
    if (identifyingParent == null) {
      if (other.identifyingParent != null) {
        return false;
      }
    } else if (!identifyingParent.equals(other.identifyingParent)) {
      return false;
    }
    if (listOfDenotedSpeciesTypeComponentIndexes == null) {
      if (other.listOfDenotedSpeciesTypeComponentIndexes != null) {
        return false;
      }
    } else if (!listOfDenotedSpeciesTypeComponentIndexes.equals(other.listOfDenotedSpeciesTypeComponentIndexes)) {
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


  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "SpeciesTypeComponentIndex [component = " + component
        + ", identifyingParent = " + identifyingParent + ", occur = " + occur
        + ", listOfDenotedSpeciesTypeComponentIndexes.size = "
        + getDenotedSpeciesTypeComponentIndexCount() + ", id = " + getId() + "]";
  }


  @Override
  public boolean isIdMandatory() {
    return true;
  }


  /**
   * Returns {@code true} if {@link #listOfDenotedSpeciesTypeComponentIndexes} is not null.
   *
   * @return {@code true} if {@link #listOfDenotedSpeciesTypeComponentIndexes} is not null.
   */
  public boolean isSetListOfDenotedSpeciesTypeComponentIndexes() {
    if (listOfDenotedSpeciesTypeComponentIndexes == null) {
      return false;
    }
    return true;
  }


  /**
   * Returns the {@link #listOfDenotedSpeciesTypeComponentIndexes}.
   * Creates it if it does not already exist.
   *
   * @return the {@link #listOfDenotedSpeciesTypeComponentIndexes}.
   */
  public ListOf<DenotedSpeciesTypeComponentIndex> getListOfDenotedSpeciesTypeComponentIndexes() {
    if (listOfDenotedSpeciesTypeComponentIndexes == null) {
      listOfDenotedSpeciesTypeComponentIndexes = new ListOf<DenotedSpeciesTypeComponentIndex>();
      listOfDenotedSpeciesTypeComponentIndexes.setNamespace(MultiConstants.namespaceURI); // TODO - removed once the mechanism are in place to set package version and namespace
      listOfDenotedSpeciesTypeComponentIndexes.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'multi'
      listOfDenotedSpeciesTypeComponentIndexes.setPackageName(null);
      listOfDenotedSpeciesTypeComponentIndexes.setPackageName(MultiConstants.shortLabel);
      listOfDenotedSpeciesTypeComponentIndexes.setSBaseListType(ListOf.Type.other);

      registerChild(listOfDenotedSpeciesTypeComponentIndexes);
    }
    return listOfDenotedSpeciesTypeComponentIndexes;
  }


  /**
   * Sets the given {@code ListOf<DenotedSpeciesTypeComponentIndex>}.
   * If {@link #listOfDenotedSpeciesTypeComponentIndexes} was defined before and contains some
   * elements, they are all unset.
   *
   * @param listOfDenotedSpeciesTypeComponentIndices
   */
  public void setListOfDenotedSpeciesTypeComponentIndexes(ListOf<DenotedSpeciesTypeComponentIndex> listOfDenotedSpeciesTypeComponentIndices) {
    unsetListOfDenotedSpeciesTypeComponentIndexes();
    listOfDenotedSpeciesTypeComponentIndexes = listOfDenotedSpeciesTypeComponentIndices;
    if (listOfDenotedSpeciesTypeComponentIndices != null) {
      listOfDenotedSpeciesTypeComponentIndices.unsetNamespace();
      listOfDenotedSpeciesTypeComponentIndices.setNamespace(MultiConstants.namespaceURI); // TODO - removed once the mechanism are in place to set package version and namespace
      listOfDenotedSpeciesTypeComponentIndices.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'multi'
      listOfDenotedSpeciesTypeComponentIndices.setPackageName(null);
      listOfDenotedSpeciesTypeComponentIndices.setPackageName(MultiConstants.shortLabel);
      listOfDenotedSpeciesTypeComponentIndexes.setSBaseListType(ListOf.Type.other);

      registerChild(listOfDenotedSpeciesTypeComponentIndexes);
    }
  }


  /**
   * Returns {@code true} if {@link #listOfDenotedSpeciesTypeComponentIndexes} contains at least
   * one element, otherwise {@code false}.
   *
   * @return {@code true} if {@link #listOfDenotedSpeciesTypeComponentIndexes} contains at least
   *         one element, otherwise {@code false}.
   */
  public boolean unsetListOfDenotedSpeciesTypeComponentIndexes() {
    if (isSetListOfDenotedSpeciesTypeComponentIndexes()) {
      ListOf<DenotedSpeciesTypeComponentIndex> oldDenotedSpeciesTypeComponentIndexs = listOfDenotedSpeciesTypeComponentIndexes;
      listOfDenotedSpeciesTypeComponentIndexes = null;
      oldDenotedSpeciesTypeComponentIndexs.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }


  /**
   * Adds a new {@link DenotedSpeciesTypeComponentIndex} to the {@link #listOfDenotedSpeciesTypeComponentIndexes}.
   * <p>The listOfDenotedSpeciesTypeComponentIndexes is initialized if necessary.
   *
   * @param denotedSpeciesTypeComponentIndex the element to add to the list
   * @return {@code true} (as specified by {@link java.util.Collection#add})
   * @see java.util.Collection#add(Object)
   */
  public boolean addDenotedSpeciesTypeComponentIndex(DenotedSpeciesTypeComponentIndex denotedSpeciesTypeComponentIndex) {
    return getListOfDenotedSpeciesTypeComponentIndexes().add(denotedSpeciesTypeComponentIndex);
  }


  /**
   * Removes an element from the {@link #listOfDenotedSpeciesTypeComponentIndexes}.
   *
   * @param denotedSpeciesTypeComponentIndex the element to be removed from the list.
   * @return {@code true} if the list contained the specified element and it was
   *         removed.
   * @see java.util.List#remove(Object)
   */
  public boolean removeDenotedSpeciesTypeComponentIndex(DenotedSpeciesTypeComponentIndex denotedSpeciesTypeComponentIndex) {
    if (isSetListOfDenotedSpeciesTypeComponentIndexes()) {
      return getListOfDenotedSpeciesTypeComponentIndexes().remove(denotedSpeciesTypeComponentIndex);
    }
    return false;
  }


  /**
   * Removes an element from the {@link #listOfDenotedSpeciesTypeComponentIndexes} at the given index.
   *
   * @param i the index where to remove the {@link DenotedSpeciesTypeComponentIndex}.
   * @return the specified element if it was successfully found and removed.
   * @throws IndexOutOfBoundsException if the listOf is not set or if the index is
   *         out of bound ({@code (i < 0) || (i > listOfDenotedSpeciesTypeComponentIndexes)}).
   */
  public DenotedSpeciesTypeComponentIndex removeDenotedSpeciesTypeComponentIndex(int i) {
    if (!isSetListOfDenotedSpeciesTypeComponentIndexes()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfDenotedSpeciesTypeComponentIndexes().remove(i);
  }


  /**
   * Creates a new DenotedSpeciesTypeComponentIndex element and adds it to the
   * {@link #listOfDenotedSpeciesTypeComponentIndexes} list.
   *
   * @return the newly created element, i.e., the last item in the
   *         {@link #listOfDenotedSpeciesTypeComponentIndexes}
   */
  public DenotedSpeciesTypeComponentIndex createDenotedSpeciesTypeComponentIndex() {
    return createDenotedSpeciesTypeComponentIndex();
  }


  /**
   * Gets an element from the {@link #listOfDenotedSpeciesTypeComponentIndexes} at the given index.
   *
   * @param i the index of the {@link DenotedSpeciesTypeComponentIndex} element to get.
   * @return an element from the listOfDenotedSpeciesTypeComponentIndexes at the given index.
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index < 0 || index > list.size).
   */
  public DenotedSpeciesTypeComponentIndex getDenotedSpeciesTypeComponentIndex(int i) {
    if (!isSetListOfDenotedSpeciesTypeComponentIndexes()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfDenotedSpeciesTypeComponentIndexes().get(i);
  }


  /**
   * Returns the number of {@link DenotedSpeciesTypeComponentIndex}s in this
   * {@link SpeciesTypeComponentIndex}.
   * 
   * @return the number of {@link DenotedSpeciesTypeComponentIndex}s in this
   *         {@link SpeciesTypeComponentIndex}.
   */
  public int getDenotedSpeciesTypeComponentIndexCount() {
    return isSetListOfDenotedSpeciesTypeComponentIndexes() ? getListOfDenotedSpeciesTypeComponentIndexes().size() : 0;
  }


  /**
   * Returns the number of {@link DenotedSpeciesTypeComponentIndex}s in this
   * {@link SpeciesTypeComponentIndex}.
   * 
   * @return the number of {@link DenotedSpeciesTypeComponentIndex}s in this
   *         {@link SpeciesTypeComponentIndex}.
   * @libsbml.deprecated same as {@link #getDenotedSpeciesTypeComponentIndexCount()}
   */
  public int getNumDenotedSpeciesTypeComponentIndexes() {
    return getDenotedSpeciesTypeComponentIndexCount();
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


  /**
   * Returns the value of {@link #identifyingParent}.
   *
   * @return the value of {@link #identifyingParent}.
   */
  public String getIndentifyingParent() {
    if (isSetIndentifyingParent()) {
      return identifyingParent;
    }

    return null;
  }


  /**
   * Returns whether {@link #identifyingParent} is set.
   *
   * @return whether {@link #identifyingParent} is set.
   */
  public boolean isSetIndentifyingParent() {
    return identifyingParent != null;
  }


  /**
   * Sets the value of identifyingParent
   *
   * @param identifyingParent the value of identifyingParent to be set.
   */
  public void setIndentifyingParent(String identifyingParent) {
    String oldIndentifyingParent = this.identifyingParent;
    this.identifyingParent = identifyingParent;
    firePropertyChange(MultiConstants.identifyingParent, oldIndentifyingParent, this.identifyingParent);
  }


  /**
   * Unsets the variable identifyingParent.
   *
   * @return {@code true} if identifyingParent was set before, otherwise {@code false}.
   */
  public boolean unsetIndentifyingParent() {
    if (isSetIndentifyingParent()) {
      String oldIndentifyingParent = identifyingParent;
      identifyingParent = null;
      firePropertyChange(MultiConstants.identifyingParent, oldIndentifyingParent, identifyingParent);
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

    if (isSetComponent()) {
      attributes.put(MultiConstants.shortLabel + ":" + MultiConstants.component, getComponent());
    }
    if (isSetIndentifyingParent()) {
      attributes.put(MultiConstants.shortLabel + ":" + MultiConstants.identifyingParent, getComponent());
    }
    if (isSetOccur()) {
      attributes.put(MultiConstants.shortLabel + ":" + MultiConstants.occur, occur.toString());
    }

    return attributes;
  }


  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
    if (!isAttributeRead) {
      isAttributeRead = true;

      if (attributeName.equals(MultiConstants.component)) {
        setComponent(value);
      }
      if (attributeName.equals(MultiConstants.identifyingParent)) {
        setIndentifyingParent(value);
      }
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
