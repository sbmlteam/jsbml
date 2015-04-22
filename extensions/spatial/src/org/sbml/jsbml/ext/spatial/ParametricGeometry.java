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
package org.sbml.jsbml.ext.spatial;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.ListOf;

/**
 * @author Alex Thomas
 * @author Andreas Dr&auml;ger
 * @author Piero Dalle Pezze
 * @since 1.0
 * @version $Rev$
 */
public class ParametricGeometry extends GeometryDefinition {

  /**
   * 
   */
  ListOf<SpatialPoints> listOfSpatialPoints;
  /**
   * 
   */
  ListOf<ParametricObject> listOfParametricObjects;

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 5947368889263003716L;

  /**
   * 
   */
  public ParametricGeometry() {
    super();
  }

  /**
   * @param pg
   */
  public ParametricGeometry(ParametricGeometry pg) {
    super(pg);

    if (pg.isSetListOfParametricObjects()) {
      setListOfParametricObjects(pg.getListOfParametricObjects().clone());
    }

    if (pg.isSetListOfSpatialPoints()) {
      setListOfSpatialPoints(pg.getListOfSpatialPoints().clone());
    }

  }


  /**
   * @param level
   * @param version
   */
  public ParametricGeometry(int level, int version) {
    super(level, version);
  }


  /**
   * 
   * @param id
   * @param level
   * @param version
   */
  public ParametricGeometry(String id, int level, int version) {
    super(id, level, version);
  }


  @Override
  public ParametricGeometry clone() {
    return new ParametricGeometry(this);
  }


  @Override
  public boolean equals(Object object) {
    boolean equal = super.equals(object);
    if (equal) {
      ParametricGeometry pg = (ParametricGeometry) object;

      equal &= pg.isSetListOfParametricObjects() == isSetListOfParametricObjects();
      if (equal && isSetListOfParametricObjects()) {
        equal &= pg.getListOfParametricObjects().equals(getListOfParametricObjects());
      }

      equal &= pg.isSetListOfSpatialPoints() == isSetListOfSpatialPoints();
      if (equal && isSetListOfSpatialPoints()) {
        equal &= pg.getListOfSpatialPoints().equals(getListOfSpatialPoints());
      }

    }
    return equal;
  }

  @Override
  public int hashCode() {
    final int prime = 1999;
    int hashCode = super.hashCode();
    if (isSetListOfParametricObjects()) {
      hashCode += prime * getListOfParametricObjects().hashCode();
    }
    if (isSetListOfSpatialPoints()) {
      hashCode += prime * getListOfSpatialPoints().hashCode();
    }
    return hashCode;
  }

  /**
   * Returns {@code true}, if listOfParametricObjects contains at least one element.
   *
   * @return {@code true}, if listOfParametricObjects contains at least one element,
   *         otherwise {@code false}
   */
  public boolean isSetListOfParametricObjects() {
    if ((listOfParametricObjects == null) || listOfParametricObjects.isEmpty()) {
      return false;
    }
    return true;
  }


  /**
   * Returns the listOfParametricObjects. Creates it if it is not already existing.
   *
   * @return the listOfParametricObjects
   */
  public ListOf<ParametricObject> getListOfParametricObjects() {
    if (!isSetListOfParametricObjects()) {
      listOfParametricObjects = new ListOf<ParametricObject>(getLevel(),
          getVersion());
      listOfParametricObjects.setNamespace(SpatialConstants.namespaceURI);
      listOfParametricObjects.setSBaseListType(ListOf.Type.other);
      registerChild(listOfParametricObjects);
    }
    return listOfParametricObjects;
  }


  /**
   * Sets the given {@code ListOf<ParametricObject>}. If listOfParametricObjects
   * was defined before and contains some elements, they are all unset.
   *
   * @param listOfParametricObjects
   */
  public void setListOfParametricObjects(ListOf<ParametricObject> listOfParametricObjects) {
    unsetListOfParametricObjects();
    this.listOfParametricObjects = listOfParametricObjects;
    registerChild(this.listOfParametricObjects);
  }


  /**
   * Returns {@code true}, if listOfParametricObjects contain at least one element,
   *         otherwise {@code false}
   *
   * @return {@code true}, if listOfParametricObjects contain at least one element,
   *         otherwise {@code false}
   */
  public boolean unsetListOfParametricObjects() {
    if (isSetListOfParametricObjects()) {
      ListOf<ParametricObject> oldParametricObjects = listOfParametricObjects;
      listOfParametricObjects = null;
      oldParametricObjects.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }


  /**
   * Adds a new {@link ParametricObject} to the listOfParametricObjects.
   * <p>The listOfParametricObjects is initialized if necessary.
   *
   * @param parametricObject the element to add to the list
   * @return true (as specified by {@link Collection#add})
   */
  public boolean addParametricObject(ParametricObject parametricObject) {
    return getListOfParametricObjects().add(parametricObject);
  }


  /**
   * Removes an element from the listOfParametricObjects.
   *
   * @param parametricObject the element to be removed from the list
   * @return true if the list contained the specified element
   * @see List#remove(Object)
   */
  public boolean removeParametricObject(ParametricObject parametricObject) {
    if (isSetListOfParametricObjects()) {
      return getListOfParametricObjects().remove(parametricObject);
    }
    return false;
  }


  /**
   * Removes an element from the listOfParametricObjects at the given index.
   *
   * @param i the index where to remove the {@link ParametricObject}
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index < 0 || index > list.size)
   */
  public void removeParametricObject(int i) {
    if (!isSetListOfParametricObjects()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    //    if (getListOfParametricObjects().size() == 1) {
    //      throw new SBMLException("There must be at least one ParametricObject defined for this list");
    //    }
    getListOfParametricObjects().remove(i);
  }


  /**
   * TODO: if the ID is mandatory for ParametricObject objects,
   * one should also add this methods
   */
  //public void removeParametricObject(String id) {
  //  getListOfParametricObjects().removeFirst(new NameFilter(id));
  //}
  /**
   * Creates a new ParametricObject element and adds it to the ListOfParametricObjects list
   * @return
   */
  public ParametricObject createParametricObject() {
    return createParametricObject(null);
  }


  /**
   * Creates a new {@link ParametricObject} element and adds it to the ListOfParametricObjects list
   * @param id
   *
   * @return a new {@link ParametricObject} element
   */
  public ParametricObject createParametricObject(String id) {
    ParametricObject parametricObject = new ParametricObject(id, getLevel(), getVersion());
    addParametricObject(parametricObject);
    return parametricObject;
  }

  /**
   * 
   */


  /**
   * Returns {@code true}, if listOfSpatialPoints contains at least one element.
   *
   * @return {@code true}, if listOfSpatialPoints contains at least one element,
   *         otherwise {@code false}
   */
  public boolean isSetListOfSpatialPoints() {
    if ((listOfSpatialPoints == null) || listOfSpatialPoints.isEmpty()) {
      return false;
    }
    return true;
  }


  /**
   * Returns the listOfSpatialPoints. Creates it if it is not already existing.
   *
   * @return the listOfSpatialPoints
   */
  public ListOf<SpatialPoints> getListOfSpatialPoints() {
    if (!isSetListOfSpatialPoints()) {
      listOfSpatialPoints = new ListOf<SpatialPoints>(getLevel(),
          getVersion());
      listOfSpatialPoints.setNamespace(SpatialConstants.namespaceURI);
      listOfSpatialPoints.setSBaseListType(ListOf.Type.other);
      registerChild(listOfSpatialPoints);
    }
    return listOfSpatialPoints;
  }


  /**
   * Sets the given {@code ListOf<SpatialPoint>}. If listOfSpatialPoints
   * was defined before and contains some elements, they are all unset.
   *
   * @param listOfSpatialPoints
   */
  public void setListOfSpatialPoints(ListOf<SpatialPoints> listOfSpatialPoints) {
    unsetListOfSpatialPoints();
    this.listOfSpatialPoints = listOfSpatialPoints;
    registerChild(this.listOfSpatialPoints);
  }


  /**
   * Returns {@code true}, if listOfSpatialPoints contain at least one element,
   *         otherwise {@code false}
   *
   * @return {@code true}, if listOfSpatialPoints contain at least one element,
   *         otherwise {@code false}
   */
  public boolean unsetListOfSpatialPoints() {
    if (isSetListOfSpatialPoints()) {
      ListOf<SpatialPoints> oldSpatialPoints = listOfSpatialPoints;
      listOfSpatialPoints = null;
      oldSpatialPoints.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }


  /**
   * Adds a new {@link SpatialPoints} to the listOfSpatialPoints.
   * <p>The listOfSpatialPoints is initialized if necessary.
   *
   * @param spatialPoints the element to add to the list
   * @return true (as specified by {@link Collection#add})
   */
  public boolean addSpatialPoints(SpatialPoints spatialPoints) {
    return getListOfSpatialPoints().add(spatialPoints);
  }


  /**
   * Removes an element from the listOfSpatialPoints.
   *
   * @param spatialPoints the element to be removed from the list
   * @return true if the list contained the specified element
   * @see List#remove(Object)
   */
  public boolean removeSpatialPoints(SpatialPoints spatialPoints) {
    if (isSetListOfSpatialPoints()) {
      return getListOfSpatialPoints().remove(spatialPoints);
    }
    return false;
  }


  /**
   * Removes an element from the listOfSpatialPoints at the given index.
   *
   * @param i the index where to remove the {@link SpatialPoint}
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index < 0 || index > list.size)
   */
  public void removeSpatialPoints(int i) {
    if (!isSetListOfSpatialPoints()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    //    if (getListOfSpatialPoints().size() == 1) {
    //      throw new SBMLException("There must be at least one SpatialPoint defined for this list");
    //    }
    getListOfSpatialPoints().remove(i);
  }


  /*
   * TODO: if the ID is mandatory for SpatialPoints objects,
   * one should also add this methods
   */
  //public void removeSpatialPoints(String id) {
  //  getListOfSpatialPoints().removeFirst(new NameFilter(id));
  //}
  /**
   * Creates a new SpatialPoints element and adds it to the ListOfSpatialPoints list
   * @return
   */
  public SpatialPoints createSpatialPoints() {
    return createSpatialPoints(null);
  }


  /**
   * Creates a new {@link SpatialPoints} element and adds it to the ListOfSpatialPoints list
   * @param id
   *
   * @return a new {@link SpatialPoints} element
   */
  public SpatialPoints createSpatialPoints(String id) {
    SpatialPoints spatialPoints = new SpatialPoints(getLevel(), getVersion());
    addSpatialPoints(spatialPoints);
    return spatialPoints;
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
    if (isSetListOfParametricObjects()) {
      count++;
    }
    if (isSetListOfSpatialPoints()) {
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
      throw new IndexOutOfBoundsException(index + " < 0");
    }
    int count = super.getChildCount(), pos = 0;
    if (index < count) {
      return super.getChildAt(index);
    } else {
      index -= count;
    }
    if (isSetListOfParametricObjects()) {
      if (pos == index) {
        return getListOfParametricObjects();
      }
      pos++;
    }
    if (isSetListOfSpatialPoints()) {
      if (pos == index) {
        return getListOfSpatialPoints();
      }
      pos++;
    }
    throw new IndexOutOfBoundsException(MessageFormat.format(
      "Index {0,number,integer} >= {1,number,integer}", index,
      +Math.min(pos, 0)));
  }

}
