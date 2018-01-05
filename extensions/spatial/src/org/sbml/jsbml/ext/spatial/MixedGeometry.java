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

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.ListOf;


/**
 * 
 * @author Alex Thomas
 * @since 0.8
 */
public class MixedGeometry extends GeometryDefinition {

  /**
   * 
   */
  private static final long serialVersionUID = -7544034155315224945L;

  /**
   * 
   */
  ListOf<GeometryDefinition> listOfGeometryDefinitions;
  /**
   * 
   */
  ListOf<OrdinalMapping> listOfOrdinalMappings;

  /**
   * 
   */
  public MixedGeometry() {
    super();
  }


  /**
   * @param mg
   */
  public MixedGeometry(MixedGeometry mg) {
    super(mg);

    if (mg.isSetListOfOrdinalMappings()) {
      setListOfOrdinalMappings(mg.getListOfOrdinalMappings().clone());
    }
    if (mg.isSetListOfGeometryDefinitions()) {
      setListOfGeometryDefinitions(mg.getListOfGeometryDefinitions().clone());
    }

  }


  /**
   * @param level
   * @param version
   */
  public MixedGeometry(int level, int version) {
    super(level, version);
  }


  /**
   * 
   * @param id
   * @param level
   * @param version
   */
  public MixedGeometry(String id, int level, int version) {
    super(id, level, version);
  }


  @Override
  public MixedGeometry clone() {
    return new MixedGeometry(this);
  }


  @Override
  public boolean equals(Object object) {
    boolean equal = super.equals(object);
    if (equal) {
      MixedGeometry mg = (MixedGeometry) object;

      equal &= mg.isSetListOfGeometryDefinitions() == isSetListOfGeometryDefinitions();
      if (equal && isSetListOfGeometryDefinitions()) {
        equal &= mg.getListOfGeometryDefinitions().equals(getListOfGeometryDefinitions());
      }

      equal &= mg.isSetListOfOrdinalMappings() == isSetListOfOrdinalMappings();
      if (equal && isSetListOfOrdinalMappings()) {
        equal &= mg.getListOfOrdinalMappings().equals(getListOfOrdinalMappings());
      }
    }
    return equal;
  }

  @Override
  public int hashCode() {
    final int prime = 1543;
    int hashCode = super.hashCode();
    if (isSetListOfGeometryDefinitions()) {
      hashCode += prime * getListOfGeometryDefinitions().hashCode();
    }
    if (isSetListOfOrdinalMappings()) {
      hashCode += prime * getListOfOrdinalMappings().hashCode();
    }
    return hashCode;
  }

  /**
   * Returns {@code true}, if listOfGeometryDefinitions contains at least one element.
   *
   * @return {@code true}, if listOfGeometryDefinitions contains at least one element,
   *         otherwise {@code false}
   */
  public boolean isSetListOfGeometryDefinitions() {
    if ((listOfGeometryDefinitions == null) || listOfGeometryDefinitions.isEmpty()) {
      return false;
    }
    return true;
  }


  /**
   * Returns the listOfGeometryDefinitions. Creates it if it is not already existing.
   *
   * @return the listOfGeometryDefinitions
   */
  public ListOf<GeometryDefinition> getListOfGeometryDefinitions() {
    if (!isSetListOfGeometryDefinitions()) {
      listOfGeometryDefinitions = new ListOf<GeometryDefinition>(getLevel(),
          getVersion());
      listOfGeometryDefinitions.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'spatial'
      listOfGeometryDefinitions.setPackageName(null);
      listOfGeometryDefinitions.setPackageName(SpatialConstants.shortLabel);
      listOfGeometryDefinitions.setSBaseListType(ListOf.Type.other);
      registerChild(listOfGeometryDefinitions);
    }
    return listOfGeometryDefinitions;
  }


  /**
   * Sets the given {@code ListOf<GeometryDefinition>}. If listOfGeometryDefinitions
   * was defined before and contains some elements, they are all unset.
   *
   * @param listOfGeometryDefinitions
   */
  public void setListOfGeometryDefinitions(ListOf<GeometryDefinition> listOfGeometryDefinitions) {
    unsetListOfGeometryDefinitions();
    this.listOfGeometryDefinitions = listOfGeometryDefinitions;

    if (listOfGeometryDefinitions != null) {
      listOfGeometryDefinitions.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'spatial'
      listOfGeometryDefinitions.setPackageName(null);
      listOfGeometryDefinitions.setPackageName(SpatialConstants.shortLabel);
      listOfGeometryDefinitions.setSBaseListType(ListOf.Type.other);

      registerChild(this.listOfGeometryDefinitions);
    }
  }


  /**
   * Returns {@code true}, if listOfGeometryDefinitions contain at least one element,
   *         otherwise {@code false}
   *
   * @return {@code true}, if listOfGeometryDefinitions contain at least one element,
   *         otherwise {@code false}
   */
  public boolean unsetListOfGeometryDefinitions() {
    if (isSetListOfGeometryDefinitions()) {
      ListOf<GeometryDefinition> oldGeometryDefinitions = listOfGeometryDefinitions;
      listOfGeometryDefinitions = null;
      oldGeometryDefinitions.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }


  /**
   * Adds a new {@link GeometryDefinition} to the listOfGeometryDefinitions.
   * <p>The listOfGeometryDefinitions is initialized if necessary.
   *
   * @param listOfGeometryDefinitions the element to add to the list
   * @return true (as specified by {@link Collection#add})
   */
  public boolean addGeometryDefinition(GeometryDefinition listOfGeometryDefinitions) {
    return getListOfGeometryDefinitions().add(listOfGeometryDefinitions);
  }


  /**
   * Removes an element from the listOfGeometryDefinitions.
   *
   * @param listOfGeometryDefinitions the element to be removed from the list
   * @return true if the list contained the specified element
   * @see List#remove(Object)
   */
  public boolean removeGeometryDefinition(GeometryDefinition listOfGeometryDefinitions) {
    if (isSetListOfGeometryDefinitions()) {
      return getListOfGeometryDefinitions().remove(listOfGeometryDefinitions);
    }
    return false;
  }


  /**
   * Removes an element from the listOfGeometryDefinitions at the given index.
   *
   * @param i the index where to remove the {@link GeometryDefinition}
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index &lt; 0 || index &gt; list.size).
   */
  public void removeGeometryDefinition(int i) {
    if (!isSetListOfGeometryDefinitions()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    getListOfGeometryDefinitions().remove(i);
  }




  /**
   * Creates a new GeometryDefinition element and adds it to the ListOfGeometryDefinitions list
   * @return
   */
  public SampledFieldGeometry createSampledFieldGeometryn() {
    return createSampledFieldGeometry(null);
  }

  /**
   * @return
   */
  public AnalyticGeometry createAnalyticGeometry() {
    return createAnalyticGeometry(null);
  }

  /**
   * @return
   */
  public CSGeometry createCSGeometry() {
    return createCSGeometry(null);
  }

  /**
   * @return
   */
  public ParametricGeometry createParametricGeometry() {
    return createParametricGeometry(null);
  }

  /**
   * @return
   */
  public MixedGeometry createMixedGeometry() {
    return createMixedGeometry(null);
  }

  /**
   * Creates a new {@link GeometryDefinition} element and adds it to the ListOfGeometryDefinitions list
   * @param id
   *
   * @return a new {@link GeometryDefinition} element
   */
  public MixedGeometry createMixedGeometry(String id) {
    MixedGeometry def = new MixedGeometry(id, getLevel(), getVersion());
    addGeometryDefinition(def);
    return def;
  }

  /**
   * Creates a new {@link GeometryDefinition} element and adds it to the ListOfGeometryDefinitions list
   * @param id
   *
   * @return a new {@link GeometryDefinition} element
   */
  public SampledFieldGeometry createSampledFieldGeometry(String id) {
    SampledFieldGeometry def = new SampledFieldGeometry(id, getLevel(), getVersion());
    addGeometryDefinition(def);
    return def;
  }

  /**
   * @param id
   * @return
   */
  public AnalyticGeometry createAnalyticGeometry(String id) {
    AnalyticGeometry def = new AnalyticGeometry(id, getLevel(), getVersion());
    addGeometryDefinition(def);
    return def;
  }

  /**
   * @param id
   * @return
   */
  public CSGeometry createCSGeometry(String id) {
    CSGeometry def = new CSGeometry(id, getLevel(), getVersion());
    addGeometryDefinition(def);
    return def;
  }

  /**
   * @param id
   * @return
   */
  public ParametricGeometry createParametricGeometry(String id) {
    ParametricGeometry def = new ParametricGeometry(id, getLevel(), getVersion());
    addGeometryDefinition(def);
    return def;
  }

  /**
   * Returns {@code true}, if listOfOrdinalMappings contains at least one element.
   *
   * @return {@code true}, if listOfOrdinalMappings contains at least one element,
   *         otherwise {@code false}
   */
  public boolean isSetListOfOrdinalMappings() {
    if ((listOfOrdinalMappings == null) || listOfOrdinalMappings.isEmpty()) {
      return false;
    }
    return true;
  }


  /**
   * Returns the listOfOrdinalMappings. Creates it if it is not already existing.
   *
   * @return the listOfOrdinalMappings
   */
  public ListOf<OrdinalMapping> getListOfOrdinalMappings() {
    if (!isSetListOfOrdinalMappings()) {
      listOfOrdinalMappings = new ListOf<OrdinalMapping>(getLevel(),
          getVersion());
      listOfOrdinalMappings.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'spatial'
      listOfOrdinalMappings.setPackageName(null);
      listOfOrdinalMappings.setPackageName(SpatialConstants.shortLabel);
      listOfOrdinalMappings.setSBaseListType(ListOf.Type.other);
      registerChild(listOfOrdinalMappings);
    }
    return listOfOrdinalMappings;
  }


  /**
   * Sets the given {@code ListOf<OrdinalMapping>}. If listOfOrdinalMappings
   * was defined before and contains some elements, they are all unset.
   *
   * @param listOfOrdinalMappings
   */
  public void setListOfOrdinalMappings(ListOf<OrdinalMapping> listOfOrdinalMappings) {
    unsetListOfOrdinalMappings();
    this.listOfOrdinalMappings = listOfOrdinalMappings;

    if (listOfOrdinalMappings != null) {
      listOfOrdinalMappings.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'spatial'
      listOfOrdinalMappings.setPackageName(null);
      listOfOrdinalMappings.setPackageName(SpatialConstants.shortLabel);
      listOfOrdinalMappings.setSBaseListType(ListOf.Type.other);

      registerChild(this.listOfOrdinalMappings);
    }
  }


  /**
   * Returns {@code true}, if listOfOrdinalMappings contain at least one element,
   *         otherwise {@code false}
   *
   * @return {@code true}, if listOfOrdinalMappings contain at least one element,
   *         otherwise {@code false}
   */
  public boolean unsetListOfOrdinalMappings() {
    if (isSetListOfOrdinalMappings()) {
      ListOf<OrdinalMapping> oldOrdinalMappings = listOfOrdinalMappings;
      listOfOrdinalMappings = null;
      oldOrdinalMappings.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }


  /**
   * Adds a new {@link OrdinalMapping} to the listOfOrdinalMappings.
   * <p>The listOfOrdinalMappings is initialized if necessary.
   *
   * @param ordinalMapping the element to add to the list
   * @return true (as specified by {@link Collection#add})
   */
  public boolean addOrdinalMapping(OrdinalMapping ordinalMapping) {
    return getListOfOrdinalMappings().add(ordinalMapping);
  }


  /**
   * Removes an element from the listOfOrdinalMappings.
   *
   * @param ordinalMapping the element to be removed from the list
   * @return true if the list contained the specified element
   * @see List#remove(Object)
   */
  public boolean removeOrdinalMapping(OrdinalMapping ordinalMapping) {
    if (isSetListOfOrdinalMappings()) {
      return getListOfOrdinalMappings().remove(ordinalMapping);
    }
    return false;
  }


  /**
   * Removes an element from the listOfOrdinalMappings at the given index.
   *
   * @param i the index where to remove the {@link OrdinalMapping}
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index &lt; 0 || index &gt; list.size).
   */
  public void removeOrdinalMapping(int i) {
    if (!isSetListOfOrdinalMappings()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    getListOfOrdinalMappings().remove(i);
  }

  /**
   * Creates a new {@link OrdinalMapping} element and adds it to the ListOfOrdinalMappings list
   *
   * @return a new {@link OrdinalMapping} element
   */
  public OrdinalMapping createOrdinalMapping() {
    OrdinalMapping ordinalMapping = new OrdinalMapping(getLevel(), getVersion());
    addOrdinalMapping(ordinalMapping);
    return ordinalMapping;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.AbstractSpatialNamedSBase#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.AbstractSpatialNamedSBase#getChildCount()
   */
  @Override
  public int getChildCount() {
    int count = super.getChildCount();
    if (isSetListOfGeometryDefinitions()) {
      count++;
    }
    if (isSetListOfOrdinalMappings()) {
      count++;
    }
    return count;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.AbstractSpatialNamedSBase#getChildAt(int)
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
    if (isSetListOfGeometryDefinitions()) {
      if (pos == index) {
        return getListOfGeometryDefinitions();
      }
      pos++;
    }
    if (isSetListOfOrdinalMappings()) {
      if (pos == index) {
        return getListOfOrdinalMappings();
      }
      pos++;
    }
    throw new IndexOutOfBoundsException(MessageFormat.format(
      resourceBundle.getString("IndexExceedsBoundsException"), index,
      Math.min(pos, 0)));
  }


}
