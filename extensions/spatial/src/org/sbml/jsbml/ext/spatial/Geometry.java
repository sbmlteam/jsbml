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
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.util.filters.NameFilter;

/**
 * @author Alex Thomas
 * @author Andreas Dr&auml;ger
 * @author Piero Dalle Pezze
 * @since 1.0
 */
public class Geometry extends AbstractSpatialNamedSBase {


  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(Geometry.class);

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 9115597691155572976L;

  /**
   * 
   */
  private ListOf<CoordinateComponent> listOfCoordinateComponents;

  /**
   * 
   */
  private ListOf<DomainType> listOfDomainTypes;

  /**
   * 
   */
  private ListOf<Domain> listOfDomains;

  /**
   * 
   */
  private ListOf<AdjacentDomains> listOfAdjacentDomains;

  /**
   * 
   */
  private ListOf<GeometryDefinition> listOfGeometryDefinitions;

  /**
   * 
   */
  private ListOf<SampledField> listOfSampledFields;

  /**
   * 
   */
  private GeometryKind coordinateSystem;

  /**
   * 
   */
  public Geometry() {
    super();
  }

  /**
   * @param sb
   */
  public Geometry(Geometry sb) {
    super(sb);
    if (sb.isSetListOfCoordinateComponents()) {
      setListOfCoordinateComponents(sb.getListOfCoordinateComponents().clone());
    }
    if (sb.isSetListOfDomainTypes()) {
      setListOfDomainTypes(sb.getListOfDomainTypes().clone());
    }
    if (sb.isSetListOfDomains()) {
      setListOfDomains(sb.getListOfDomains().clone());
    }
    if (sb.isSetListOfAdjacentDomains()) {
      setListOfAdjacentDomains(sb.getListOfAdjacentDomains().clone());
    }
    if (sb.isSetListOfGeometryDefinitions()) {
      setListOfGeometryDefinitions(sb.getListOfGeometryDefinitions().clone());
    }
    if (sb.isSetListOfSampledFields()) {
      setListOfSampledFields(sb.getListOfSampledFields().clone());
    }
    if (sb.isSetCoordinateSystem()) {
      setCoordinateSystem(sb.getCoordinateSystem());
    }
  }

  /**
   * @param id
   * @param level
   * @param version
   */
  public Geometry (String id, int level, int version) {
    super(id, level, version);
  }

  /**
   * @param level
   * @param version
   */
  public Geometry(int level, int version) {
    super(level, version);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public Geometry clone() {
    return new Geometry(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equal =  super.equals(object);
    if (equal) {

      Geometry gm = (Geometry) object;

      equal &= gm.isSetCoordinateSystem() == isSetCoordinateSystem();
      if (equal && isSetCoordinateSystem()) {
        equal &= gm.getCoordinateSystem().equals(getCoordinateSystem());
      }
      equal &= gm.isSetListOfAdjacentDomains() == isSetListOfAdjacentDomains();
      if (equal && isSetListOfAdjacentDomains()) {
        equal &= gm.getListOfAdjacentDomains().equals(getListOfAdjacentDomains());
      }
      equal &= gm.isSetListOfCoordinateComponents() == isSetListOfCoordinateComponents();
      if (equal && isSetListOfCoordinateComponents()) {
        equal &= gm.getListOfCoordinateComponents().equals(getListOfCoordinateComponents());
      }
      equal &= gm.isSetListOfDomains() == isSetListOfDomains();
      if (equal && isSetListOfDomains()) {
        equal &= gm.getListOfDomains().equals(getListOfDomains());
      }
      equal &= gm.isSetListOfDomainTypes() == isSetListOfDomainTypes();
      if (equal && isSetListOfDomainTypes()) {
        equal &= gm.getListOfDomainTypes().equals(getListOfDomainTypes());
      }
      equal &= gm.isSetListOfGeometryDefinitions() == isSetListOfGeometryDefinitions();
      if (equal && isSetListOfGeometryDefinitions()) {
        equal &= gm.getListOfGeometryDefinitions().equals(getListOfGeometryDefinitions());
      }
      equal &= gm.isSetListOfSampledFields() == isSetListOfSampledFields();
      if (equal && isSetListOfSampledFields()) {
        equal &= gm.getListOfSampledFields() == getListOfSampledFields();
      }
    }
    return equal;
  }


  /**
   * Returns the value of coordinateSystem
   *
   * @return the value of coordinateSystem
   */
  public GeometryKind getCoordinateSystem() {
    if (isSetCoordinateSystem()) {
      return coordinateSystem;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.coordinateSystem, this);
  }


  /**
   * Returns whether coordinateSystem is set
   *
   * @return whether coordinateSystem is set
   */
  public boolean isSetCoordinateSystem() {
    return coordinateSystem != null;
  }


  /**
   * Sets the value of coordinateSystem
   * @param coordinateSystem
   */
  public void setCoordinateSystem(GeometryKind coordinateSystem) {
    GeometryKind oldCoordinateSystem = this.coordinateSystem;
    this.coordinateSystem = coordinateSystem;
    firePropertyChange(SpatialConstants.coordinateSystem, oldCoordinateSystem, this.coordinateSystem);
  }


  /**
   * Unsets the variable coordinateSystem
   *
   * @return {@code true}, if coordinateSystem was set before,
   *         otherwise {@code false}
   */
  public boolean unsetCoordinateSystem() {
    if (isSetCoordinateSystem()) {
      GeometryKind oldCoordinateSystem = coordinateSystem;
      coordinateSystem = null;
      firePropertyChange(SpatialConstants.coordinateSystem, oldCoordinateSystem, coordinateSystem);
      return true;
    }
    return false;
  }


  /**
   * Returns {@code true}, if listOfSampledFields contains at least one element.
   *
   * @return {@code true}, if listOfSampledFields contains at least one element,
   *         otherwise {@code false}
   */
  public boolean isSetListOfSampledFields() {
    if ((listOfSampledFields == null) || listOfSampledFields.isEmpty()) {
      return false;
    }
    return true;
  }


  /**
   * Returns the listOfSampledFields. Creates it if it is not already existing.
   *
   * @return the listOfSampledFields
   */
  public ListOf<SampledField> getListOfSampledFields() {
    if (!isSetListOfSampledFields()) {
      listOfSampledFields = new ListOf<SampledField>(getLevel(),
          getVersion());
      listOfSampledFields.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'spatial'
      listOfSampledFields.setPackageName(null);
      listOfSampledFields.setPackageName(SpatialConstants.shortLabel);
      listOfSampledFields.setSBaseListType(ListOf.Type.other);
      registerChild(listOfSampledFields);
    }
    return listOfSampledFields;
  }


  /**
   * Sets the given {@code ListOf<SampledField>}. If listOfSampledFields
   * was defined before and contains some elements, they are all unset.
   *
   * @param listOfSampledFields
   */
  public void setListOfSampledFields(ListOf<SampledField> listOfSampledFields) {
    unsetListOfSampledFields();
    this.listOfSampledFields = listOfSampledFields;

    if (listOfSampledFields != null) {
      listOfSampledFields.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'spatial'
      listOfSampledFields.setPackageName(null);
      listOfSampledFields.setPackageName(SpatialConstants.shortLabel);
      listOfSampledFields.setSBaseListType(ListOf.Type.other);

      registerChild(this.listOfSampledFields);
    }
  }


  /**
   * Returns {@code true}, if listOfSampledFields contain at least one element,
   *         otherwise {@code false}
   *
   * @return {@code true}, if listOfSampledFields contain at least one element,
   *         otherwise {@code false}
   */
  public boolean unsetListOfSampledFields() {
    if (isSetListOfSampledFields()) {
      ListOf<SampledField> oldSampledFields = listOfSampledFields;
      listOfSampledFields = null;
      oldSampledFields.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }


  /**
   * Adds a new {@link SampledField} to the listOfSampledFields.
   * <p>The listOfSampledFields is initialized if necessary.
   *
   * @param sampledField the element to add to the list
   * @return true (as specified by {@link Collection#add})
   */
  public boolean addSampledField(SampledField sampledField) {
    return getListOfSampledFields().add(sampledField);
  }


  /**
   * Removes an element from the listOfSampledFields.
   *
   * @param sampledField the element to be removed from the list
   * @return true if the list contained the specified element
   * @see List#remove(Object)
   */
  public boolean removeSampledField(SampledField sampledField) {
    if (isSetListOfSampledFields()) {
      return getListOfSampledFields().remove(sampledField);
    }
    return false;
  }


  /**
   * Removes an element from the listOfSampledFields at the given index.
   *
   * @param i the index where to remove the {@link SampledField}
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index &lt; 0 || index &gt; list.size).
   */
  public void removeSampledField(int i) {
    if (!isSetListOfSampledFields()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    getListOfSampledFields().remove(i);
  }

  /**
   * @param id
   */
  public void removeSampledField(String id) {
    getListOfSampledFields().removeFirst(new NameFilter(id));
  }

  /**
   * Creates a new SampledField element and adds it to the ListOfSampledFields list
   * @return
   */
  public SampledField createSampledField() {
    return createSampledField(null);
  }


  /**
   * Creates a new {@link SampledField} element and adds it to the ListOfSampledFields list
   * @param id
   *
   * @return a new {@link SampledField} element
   */
  public SampledField createSampledField(String id) {
    SampledField sampledField = new SampledField(id, getLevel(), getVersion());
    addSampledField(sampledField);
    return sampledField;
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
      listOfGeometryDefinitions = new ListOf<GeometryDefinition>();
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
   * @param geometryDefinition the element to add to the list
   * @return true (as specified by {@link Collection#add})
   */
  public boolean addGeometryDefinition(GeometryDefinition geometryDefinition) {
    return getListOfGeometryDefinitions().add(geometryDefinition);
  }


  /**
   * Removes an element from the listOfGeometryDefinitions.
   *
   * @param geometryDefinition the element to be removed from the list
   * @return true if the list contained the specified element
   * @see List#remove(Object)
   */
  public boolean removeGeometryDefinition(GeometryDefinition geometryDefinition) {
    if (isSetListOfGeometryDefinitions()) {
      return getListOfGeometryDefinitions().remove(geometryDefinition);
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
   * @param id
   */
  public void removeGeometryDefinition(String id) {
    getListOfGeometryDefinitions().removeFirst(new NameFilter(id));
  }

  /**
   * Creates a new GeometryDefinition element and adds it to the ListOfGeometryDefinitions list
   * @return
   */
  public SampledFieldGeometry createSampledFieldGeometry() {
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
   * Returns {@code true}, if listOfAdjacentDomains contains at least one element.
   *
   * @return {@code true}, if listOfAdjacentDomains contains at least one element,
   *         otherwise {@code false}
   */
  public boolean isSetListOfAdjacentDomains() {
    if ((listOfAdjacentDomains == null) || listOfAdjacentDomains.isEmpty()) {
      return false;
    }
    return true;
  }


  /**
   * Returns the listOfAdjacentDomains. Creates it if it is not already existing.
   *
   * @return the listOfAdjacentDomains
   */
  public ListOf<AdjacentDomains> getListOfAdjacentDomains() {
    if (!isSetListOfAdjacentDomains()) {
      listOfAdjacentDomains = new ListOf<AdjacentDomains>();
      listOfAdjacentDomains.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'spatial'
      listOfAdjacentDomains.setPackageName(null);
      listOfAdjacentDomains.setPackageName(SpatialConstants.shortLabel);
      listOfAdjacentDomains.setSBaseListType(ListOf.Type.other);
      registerChild(listOfAdjacentDomains);
    }
    return listOfAdjacentDomains;
  }


  /**
   * Sets the given {@code ListOf<AdjacentDomain>}. If listOfAdjacentDomains
   * was defined before and contains some elements, they are all unset.
   *
   * @param listOfAdjacentDomains
   */
  public void setListOfAdjacentDomains(ListOf<AdjacentDomains> listOfAdjacentDomains) {
    unsetListOfAdjacentDomains();
    this.listOfAdjacentDomains = listOfAdjacentDomains;

    if (listOfAdjacentDomains != null) {
      listOfAdjacentDomains.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'spatial'
      listOfAdjacentDomains.setPackageName(null);
      listOfAdjacentDomains.setPackageName(SpatialConstants.shortLabel);
      listOfAdjacentDomains.setSBaseListType(ListOf.Type.other);

      registerChild(this.listOfAdjacentDomains);
    }
  }


  /**
   * Returns {@code true}, if listOfAdjacentDomains contain at least one element,
   *         otherwise {@code false}
   *
   * @return {@code true}, if listOfAdjacentDomains contain at least one element,
   *         otherwise {@code false}
   */
  public boolean unsetListOfAdjacentDomains() {
    if (isSetListOfAdjacentDomains()) {
      ListOf<AdjacentDomains> oldAdjacentDomains = listOfAdjacentDomains;
      listOfAdjacentDomains = null;
      oldAdjacentDomains.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }


  /**
   * Adds a new {@link AdjacentDomains} to the {@link #listOfAdjacentDomains}.
   * <p>The {@link #listOfAdjacentDomains} is initialized if necessary.
   *
   * @param adjacentDomains the element to add to the list
   * @return true (as specified by {@link Collection#add})
   */
  public boolean addAdjacentDomain(AdjacentDomains adjacentDomains) {
    return getListOfAdjacentDomains().add(adjacentDomains);
  }


  /**
   * Removes an element from the listOfAdjacentDomains.
   *
   * @param adjacentDomains the element to be removed from the list
   * @return true if the list contained the specified element
   * @see List#remove(Object)
   */
  public boolean removeAdjacentDomain(AdjacentDomains adjacentDomains) {
    if (isSetListOfAdjacentDomains()) {
      return getListOfAdjacentDomains().remove(adjacentDomains);
    }
    return false;
  }


  /**
   * Removes an element from the listOfAdjacentDomains at the given index.
   *
   * @param i the index where to remove the {@link AdjacentDomains}
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index &lt; 0 || index &gt; list.size).
   */
  public void removeAdjacentDomain(int i) {
    if (!isSetListOfAdjacentDomains()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    getListOfAdjacentDomains().remove(i);
  }

  /**
   * @param id
   */
  public void removeAdjacentDomain(String id) {
    getListOfAdjacentDomains().removeFirst(new NameFilter(id));
  }

  /**
   * Creates a new AdjacentDomain element and adds it to the ListOfAdjacentDomains list
   * @return
   */
  public AdjacentDomains createAdjacentDomain() {
    return createAdjacentDomain(null);
  }

  /**
   * Creates a new {@link AdjacentDomains} element and adds it to the
   * {@link #listOfAdjacentDomains} list
   * @param id
   *
   * @return a new {@link AdjacentDomains} element
   */
  public AdjacentDomains createAdjacentDomain(String id) {
    AdjacentDomains adjacentDomains = new AdjacentDomains(id, getLevel(), getVersion());
    addAdjacentDomain(adjacentDomains);
    return adjacentDomains;
  }


  /**
   * Returns {@code true}, if listOfDomainTypes contains at least one element.
   *
   * @return {@code true}, if listOfDomainTypes contains at least one element,
   *         otherwise {@code false}
   */
  public boolean isSetListOfDomainTypes() {
    if ((listOfDomainTypes == null) || listOfDomainTypes.isEmpty()) {
      return false;
    }
    return true;
  }


  /**
   * Returns the listOfDomainTypes. Creates it if it is not already existing.
   *
   * @return the listOfDomainTypes
   */
  public ListOf<DomainType> getListOfDomainTypes() {
    if (!isSetListOfDomainTypes()) {
      listOfDomainTypes = new ListOf<DomainType>();
      listOfDomainTypes.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'spatial'
      listOfDomainTypes.setPackageName(null);
      listOfDomainTypes.setPackageName(SpatialConstants.shortLabel);
      listOfDomainTypes.setSBaseListType(ListOf.Type.other);
      registerChild(listOfDomainTypes);
    }
    return listOfDomainTypes;
  }


  /**
   * Sets the given {@code ListOf<DomainType>}. If listOfDomainTypes
   * was defined before and contains some elements, they are all unset.
   *
   * @param listOfDomainTypes
   */
  public void setListOfDomainTypes(ListOf<DomainType> listOfDomainTypes) {
    unsetListOfDomainTypes();
    this.listOfDomainTypes = listOfDomainTypes;

    if (listOfDomainTypes != null) {
      listOfDomainTypes.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'spatial'
      listOfDomainTypes.setPackageName(null);
      listOfDomainTypes.setPackageName(SpatialConstants.shortLabel);
      listOfDomainTypes.setSBaseListType(ListOf.Type.other);

      registerChild(this.listOfDomainTypes);
    }
  }


  /**
   * Returns {@code true}, if listOfDomainTypes contain at least one element,
   *         otherwise {@code false}
   *
   * @return {@code true}, if listOfDomainTypes contain at least one element,
   *         otherwise {@code false}
   */
  public boolean unsetListOfDomainTypes() {
    if (isSetListOfDomainTypes()) {
      ListOf<DomainType> oldDomainTypes = listOfDomainTypes;
      listOfDomainTypes = null;
      oldDomainTypes.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }


  /**
   * Adds a new {@link DomainType} to the listOfDomainTypes.
   * <p>The listOfDomainTypes is initialized if necessary.
   *
   * @param domainType the element to add to the list
   * @return true (as specified by {@link Collection#add})
   */
  public boolean addDomainType(DomainType domainType) {
    return getListOfDomainTypes().add(domainType);
  }


  /**
   * Removes an element from the listOfDomainTypes.
   *
   * @param domainType the element to be removed from the list
   * @return true if the list contained the specified element
   * @see List#remove(Object)
   */
  public boolean removeDomainType(DomainType domainType) {
    if (isSetListOfDomainTypes()) {
      return getListOfDomainTypes().remove(domainType);
    }
    return false;
  }


  /**
   * Removes an element from the listOfDomainTypes at the given index.
   *
   * @param i the index where to remove the {@link DomainType}
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index &lt; 0 || index &gt; list.size).
   */
  public void removeDomainType(int i) {
    if (!isSetListOfDomainTypes()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    getListOfDomainTypes().remove(i);
  }

  /**
   * @param id
   */
  public void removeDomainType(String id) {
    getListOfDomainTypes().removeFirst(new NameFilter(id));
  }

  /**
   * Creates a new DomainType element and adds it to the ListOfDomainTypes list
   * @return
   */
  public DomainType createDomainType() {
    return createDomainType(null);
  }

  /**
   * Creates a new {@link DomainType} element and adds it to the ListOfDomainTypes list
   * @param id
   *
   * @return a new {@link DomainType} element
   */
  public DomainType createDomainType(String id) {
    DomainType domainType = new DomainType(id, getLevel(), getVersion());
    addDomainType(domainType);
    return domainType;
  }


  /**
   * Returns {@code true}, if listOfDomains contains at least one element.
   *
   * @return {@code true}, if listOfDomains contains at least one element,
   *         otherwise {@code false}
   */
  public boolean isSetListOfDomains() {
    if ((listOfDomains == null) || listOfDomains.isEmpty()) {
      return false;
    }
    return true;
  }


  /**
   * Returns the listOfDomains. Creates it if it is not already existing.
   *
   * @return the listOfDomains
   */
  public ListOf<Domain> getListOfDomains() {
    if (!isSetListOfDomains()) {
      listOfDomains = new ListOf<Domain>();
      listOfDomains.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'spatial'
      listOfDomains.setPackageName(null);
      listOfDomains.setPackageName(SpatialConstants.shortLabel);
      listOfDomains.setSBaseListType(ListOf.Type.other);
      registerChild(listOfDomains);
    }
    return listOfDomains;
  }


  /**
   * Sets the given {@code ListOf<Domain>}. If listOfDomains
   * was defined before and contains some elements, they are all unset.
   *
   * @param listOfDomains
   */
  public void setListOfDomains(ListOf<Domain> listOfDomains) {
    unsetListOfDomains();
    this.listOfDomains = listOfDomains;

    if (listOfDomains != null) {
      listOfDomains.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'spatial'
      listOfDomains.setPackageName(null);
      listOfDomains.setPackageName(SpatialConstants.shortLabel);
      listOfDomains.setSBaseListType(ListOf.Type.other);

      registerChild(this.listOfDomains);
    }
  }


  /**
   * Returns {@code true}, if listOfDomains contain at least one element,
   *         otherwise {@code false}
   *
   * @return {@code true}, if listOfDomains contain at least one element,
   *         otherwise {@code false}
   */
  public boolean unsetListOfDomains() {
    if (isSetListOfDomains()) {
      ListOf<Domain> oldDomains = listOfDomains;
      listOfDomains = null;
      oldDomains.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }


  /**
   * Adds a new {@link Domain} to the listOfDomains.
   * <p>The listOfDomains is initialized if necessary.
   *
   * @param domain the element to add to the list
   * @return true (as specified by {@link Collection#add})
   */
  public boolean addDomain(Domain domain) {
    return getListOfDomains().add(domain);
  }


  /**
   * Removes an element from the listOfDomains.
   *
   * @param domain the element to be removed from the list
   * @return true if the list contained the specified element
   * @see List#remove(Object)
   */
  public boolean removeDomain(Domain domain) {
    if (isSetListOfDomains()) {
      return getListOfDomains().remove(domain);
    }
    return false;
  }


  /**
   * Removes an element from the listOfDomains at the given index.
   *
   * @param i the index where to remove the {@link Domain}
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index &lt; 0 || index &gt; list.size).
   */
  public void removeDomain(int i) {
    if (!isSetListOfDomains()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    getListOfDomains().remove(i);
  }

  /**
   * @param id
   */
  public void removeDomain(String id) {
    getListOfDomains().removeFirst(new NameFilter(id));
  }

  /**
   * Creates a new Domain element and adds it to the ListOfDomains list
   * @return
   */
  public Domain createDomain() {
    return createDomain(null);
  }

  /**
   * Creates a new {@link Domain} element and adds it to the ListOfDomains list
   * @param id
   *
   * @return a new {@link Domain} element
   */
  public Domain createDomain(String id) {
    Domain domain = new Domain(id, getLevel(), getVersion());
    addDomain(domain);
    return domain;
  }


  /**
   * Returns {@code true}, if listOfCoordinateComponents contains at least one element.
   *
   * @return {@code true}, if listOfCoordinateComponents contains at least one element,
   *         otherwise {@code false}
   */
  public boolean isSetListOfCoordinateComponents() {
    if ((listOfCoordinateComponents == null) || listOfCoordinateComponents.isEmpty()) {
      return false;
    }
    return true;
  }


  /**
   * Returns the listOfCoordinateComponents. Creates it if it is not already existing.
   *
   * @return the listOfCoordinateComponents
   */
  public ListOf<CoordinateComponent> getListOfCoordinateComponents() {
    if (!isSetListOfCoordinateComponents()) {
      listOfCoordinateComponents = new ListOf<CoordinateComponent>();
      listOfCoordinateComponents.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'spatial'
      listOfCoordinateComponents.setPackageName(null);
      listOfCoordinateComponents.setPackageName(SpatialConstants.shortLabel);
      listOfCoordinateComponents.setSBaseListType(ListOf.Type.other);
      registerChild(listOfCoordinateComponents);
    }
    return listOfCoordinateComponents;
  }


  /**
   * Sets the given {@code ListOf<CoordinateComponent>}. If listOfCoordinateComponents
   * was defined before and contains some elements, they are all unset.
   *
   * @param listOfCoordinateComponents
   */
  public void setListOfCoordinateComponents(ListOf<CoordinateComponent> listOfCoordinateComponents) {
    unsetListOfCoordinateComponents();
    this.listOfCoordinateComponents = listOfCoordinateComponents;

    if (listOfCoordinateComponents != null) {
      listOfCoordinateComponents.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'spatial'
      listOfCoordinateComponents.setPackageName(null);
      listOfCoordinateComponents.setPackageName(SpatialConstants.shortLabel);
      listOfCoordinateComponents.setSBaseListType(ListOf.Type.other);

      registerChild(this.listOfCoordinateComponents);
    }
  }


  /**
   * Returns {@code true}, if listOfCoordinateComponents contain at least one element,
   *         otherwise {@code false}
   *
   * @return {@code true}, if listOfCoordinateComponents contain at least one element,
   *         otherwise {@code false}
   */
  public boolean unsetListOfCoordinateComponents() {
    if (isSetListOfCoordinateComponents()) {
      ListOf<CoordinateComponent> oldCoordinateComponents = listOfCoordinateComponents;
      listOfCoordinateComponents = null;
      oldCoordinateComponents.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }


  /**
   * Adds a new {@link CoordinateComponent} to the listOfCoordinateComponents.
   * <p>The listOfCoordinateComponents is initialized if necessary.
   *
   * @param coordinateComponents the element to add to the list
   * @return true (as specified by {@link Collection#add})
   */
  public boolean addCoordinateComponent(CoordinateComponent coordinateComponents) {
    return getListOfCoordinateComponents().add(coordinateComponents);
  }


  /**
   * Removes an element from the listOfCoordinateComponents.
   *
   * @param coordinateComponents the element to be removed from the list
   * @return true if the list contained the specified element
   * @see List#remove(Object)
   */
  public boolean removeCoordinateComponent(CoordinateComponent coordinateComponents) {
    if (isSetListOfCoordinateComponents()) {
      return getListOfCoordinateComponents().remove(coordinateComponents);
    }
    return false;
  }


  /**
   * Removes an element from the listOfCoordinateComponents at the given index.
   *
   * @param i the index where to remove the {@link CoordinateComponent}
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index &lt; 0 || index &gt; list.size).
   */
  public void removeCoordinateComponent(int i) {
    if (!isSetListOfCoordinateComponents()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    getListOfCoordinateComponents().remove(i);
  }

  /**
   * @param id
   */
  public void removeCoordinateComponent(String id) {
    getListOfCoordinateComponents().removeFirst(new NameFilter(id));
  }

  /**
   * Creates a new CoordinateComponent element and adds it to the ListOfCoordinateComponents list
   * @return
   */
  public CoordinateComponent createCoordinateComponent() {
    return createCoordinateComponent(null);
  }

  /**
   * Creates a new {@link CoordinateComponent} element and adds it to the ListOfCoordinateComponents list
   * @param id
   *
   * @return a new {@link CoordinateComponent} element
   */
  public CoordinateComponent createCoordinateComponent(String id) {
    CoordinateComponent coordinateComponents = new CoordinateComponent(id, getLevel(), getVersion());
    addCoordinateComponent(coordinateComponents);
    return coordinateComponents;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.AbstractSpatialNamedSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
    if (!isAttributeRead) {
      isAttributeRead = true;
      if (attributeName.equals(SpatialConstants.coordinateSystem)) {
        try {
          setCoordinateSystem(GeometryKind.valueOf(value));
        } catch (Exception e) {
          logger.warn(MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.coordinateSystem, getElementName()));
        }
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetCoordinateSystem()) {
      attributes.remove("coordinateSystem");
      attributes.put(SpatialConstants.shortLabel + ':' + SpatialConstants.coordinateSystem, coordinateSystem.toString());
    }
    return attributes;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 1103;
    int hashCode = super.hashCode();
    if (isSetCoordinateSystem()) {
      hashCode += prime * coordinateSystem.hashCode();
    }
    if (isSetListOfAdjacentDomains()) {
      hashCode += prime * listOfAdjacentDomains.hashCode();
    }
    if (isSetListOfCoordinateComponents()) {
      hashCode += prime * listOfCoordinateComponents.hashCode();
    }
    if (isSetListOfDomains()) {
      hashCode += prime * listOfDomains.hashCode();
    }
    if (isSetListOfDomainTypes()) {
      hashCode += prime * listOfDomainTypes.hashCode();
    }
    if (isSetListOfGeometryDefinitions()) {
      hashCode += prime * listOfGeometryDefinitions.hashCode();
    }
    if (isSetListOfSampledFields()) {
      hashCode += prime * listOfSampledFields.hashCode();
    }
    return hashCode;
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
    if (isSetListOfAdjacentDomains()) {
      count++;
    }
    if (isSetListOfCoordinateComponents()) {
      count++;
    }
    if (isSetListOfDomains()) {
      count++;
    }
    if (isSetListOfDomainTypes()) {
      count++;
    }
    if (isSetListOfGeometryDefinitions()) {
      count++;
    }
    if (isSetListOfSampledFields()) {
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
    if (isSetListOfAdjacentDomains()) {
      if (pos == index) {
        return getListOfAdjacentDomains();
      }
      pos++;
    }
    if (isSetListOfCoordinateComponents()) {
      if (pos == index) {
        return getListOfCoordinateComponents();
      }
      pos++;
    }
    if (isSetListOfDomains()) {
      if (pos == index) {
        return getListOfDomains();
      }
      pos++;
    }
    if (isSetListOfDomainTypes()) {
      if (pos == index) {
        return getListOfDomainTypes();
      }
      pos++;
    }
    if (isSetListOfGeometryDefinitions()) {
      if (pos == index) {
        return getListOfGeometryDefinitions();
      }
      pos++;
    }
    if (isSetListOfSampledFields()) {
      if (pos == index) {
        return getListOfSampledFields();
      }
    }
    throw new IndexOutOfBoundsException(MessageFormat.format(
      resourceBundle.getString("IndexExceedsBoundsException"), index,
      Math.min(pos, 0)));
  }

}
