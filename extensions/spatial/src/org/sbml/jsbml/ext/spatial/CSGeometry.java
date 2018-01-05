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
import org.sbml.jsbml.SBMLException;

/**
 * @author Alex Thomas
 * @author Andreas Dr&auml;ger
 * @since 1.0
 */
public class CSGeometry extends GeometryDefinition {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -3162376343741365280L;

  /**
   * 
   */
  private ListOf<CSGObject> listOfCSGObjects;

  /**
   * 
   */
  public CSGeometry() {
    super();
  }


  /**
   * @param csg
   */
  public CSGeometry(CSGeometry csg) {
    super(csg);
    if (csg.isSetListOfCSGObjects()) {
      setListOfCSGObjects(csg.getListOfCSGObjects().clone());
    }
  }


  /**
   * @param level
   * @param version
   */
  public CSGeometry(int level, int version) {
    super(level, version);
  }


  /**
   * 
   * @param id
   * @param level
   * @param version
   */
  public CSGeometry(String id, int level, int version) {
    super(id, level, version);
  }


  @Override
  public CSGeometry clone() {
    return new CSGeometry(this);
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
    CSGeometry other = (CSGeometry) obj;
    if (listOfCSGObjects == null) {
      if (other.listOfCSGObjects != null) {
        return false;
      }
    } else if (!listOfCSGObjects.equals(other.listOfCSGObjects)) {
      return false;
    }
    return true;
  }


  /**
   * Returns {@code true}, if listOfCSGObjects contains at least one element.
   *
   * @return {@code true}, if listOfCSGObjects contains at least one element,
   *         otherwise {@code false}
   */
  public boolean isSetListOfCSGObjects() {
    if ((listOfCSGObjects == null) || listOfCSGObjects.isEmpty()) {
      return false;
    }
    return true;
  }


  /**
   * Returns the listOfCSGObjects. Creates it if it is not already existing.
   *
   * @return the listOfCSGObjects
   */
  public ListOf<CSGObject> getListOfCSGObjects() {
    if (!isSetListOfCSGObjects()) {
      listOfCSGObjects = new ListOf<CSGObject>(getLevel(),
          getVersion());
      listOfCSGObjects.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'spatial'
      listOfCSGObjects.setPackageName(null);
      listOfCSGObjects.setPackageName(SpatialConstants.shortLabel);
      listOfCSGObjects.setSBaseListType(ListOf.Type.other);
      registerChild(listOfCSGObjects);
    }
    return listOfCSGObjects;
  }


  /**
   * Sets the given {@code ListOf<CSGObject>}. If listOfCSGObjects
   * was defined before and contains some elements, they are all unset.
   *
   * @param listOfCSGObjects
   */
  public void setListOfCSGObjects(ListOf<CSGObject> listOfCSGObjects) {
    unsetListOfCSGObjects();
    this.listOfCSGObjects = listOfCSGObjects;

    if (listOfCSGObjects != null) {
      listOfCSGObjects.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'spatial'
      listOfCSGObjects.setPackageName(null);
      listOfCSGObjects.setPackageName(SpatialConstants.shortLabel);
      listOfCSGObjects.setSBaseListType(ListOf.Type.other);

      registerChild(this.listOfCSGObjects);
    }
  }


  /**
   * Returns {@code true}, if listOfCSGObjects contain at least one element,
   *         otherwise {@code false}
   *
   * @return {@code true}, if listOfCSGObjects contain at least one element,
   *         otherwise {@code false}
   */
  public boolean unsetListOfCSGObjects() {
    if (isSetListOfCSGObjects()) {
      ListOf<CSGObject> oldCSGObjects = listOfCSGObjects;
      listOfCSGObjects = null;
      oldCSGObjects.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }


  /**
   * Adds a new {@link CSGObject} to the listOfCSGObjects.
   * <p>The listOfCSGObjects is initialized if necessary.
   *
   * @param csgo the element to add to the list
   * @return true (as specified by {@link Collection#add})
   */
  public boolean addCSGObject(CSGObject csgo) {
    return getListOfCSGObjects().add(csgo);
  }


  /**
   * Removes an element from the listOfCSGObjects.
   *
   * @param csgo the element to be removed from the list
   * @return true if the list contained the specified element
   * @see List#remove(Object)
   */
  public boolean removeCSGObject(CSGObject csgo) {
    if (isSetListOfCSGObjects()) {
      if (getListOfCSGObjects().size() == 1) {
        return false;
      }  else {
        return getListOfCSGObjects().remove(csgo);
      }
    }
    return false;
  }


  /**
   * Removes an element from the listOfCSGObjects at the given index.
   *
   * @param i the index where to remove the {@link CSGObject}
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index &lt; 0 || index &gt; list.size).
   */
  public void removeCSGObject(int i) {
    if (!isSetListOfCSGObjects()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    if (getListOfCSGObjects().size() == 1) {
      throw new SBMLException("There must be at least one AnalyticVolume defined for this list");
    }
    getListOfCSGObjects().remove(i);
  }


  /**
   * Creates a new CSGObject element and adds it to the ListOfCSGObjects list
   * @return
   */
  public CSGObject createCSGObject() {
    return createCSGObject(null);
  }


  /**
   * Creates a new {@link CSGObject} element and adds it to the ListOfCSGObjects list
   * @param id
   *
   * @return a new {@link CSGObject} element
   */
  public CSGObject createCSGObject(String id) {
    CSGObject csgo = new CSGObject(id, getLevel(), getVersion());
    addCSGObject(csgo);
    return csgo;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 1301;
    int result = super.hashCode();
    result = prime * result
        + ((listOfCSGObjects == null) ? 0 : listOfCSGObjects.hashCode());
    return result;
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

    if (isSetListOfCSGObjects()) {
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

    if (isSetListOfCSGObjects()) {
      if (pos == index) {
        return getListOfCSGObjects();
      }
      pos++;
    }
    throw new IndexOutOfBoundsException(
      MessageFormat.format("Index {0,number,integer} >= {1,number,integer}",
        index, Math.min(pos, 0)));
  }


  /**
   * Returns the number of {@link CSGObject}s in this
   * {@link CSGeometry}.
   * 
   * @return the number of {@link CSGObject}s in this
   *         {@link CSGeometry}.
   */
  public int getCSGObjectCount() {
    return isSetListOfCSGObjects() ? getListOfCSGObjects().size() : 0;
  }


  /**
   * Returns the number of {@link CSGObject}s in this
   * {@link CSGeometry}.
   * 
   * @return the number of {@link CSGObject}s in this
   *         {@link CSGeometry}.
   * @libsbml.deprecated same as {@link #getCSGObjectCount()}
   */
  public int getNumCSGObjects() {
    return getCSGObjectCount();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getElementName()
   */
  @Override
  public String getElementName() {
    return SpatialConstants.csGeometry;
  }

}
