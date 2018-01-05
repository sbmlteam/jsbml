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
import org.sbml.jsbml.util.filters.NameFilter;

/**
 * @author Alex Thomas
 * @author Andreas Dr&auml;ger
 * @author Piero Dalle Pezze
 * @since 1.0
 */
public class AnalyticGeometry extends GeometryDefinition {

  /**
   * 
   */
  private ListOf<AnalyticVolume> listOfAnalyticVolumes;

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -6680739495215471035L;

  /**
   * 
   */
  public AnalyticGeometry() {
    super();
  }

  /**
   * @param analyticGeometry
   */
  public AnalyticGeometry(AnalyticGeometry analyticGeometry) {
    super(analyticGeometry);
    if (analyticGeometry.isSetListOfAnalyticVolumes()) {
      setListOfAnalyticVolumes(analyticGeometry.getListOfAnalyticVolumes().clone());
    }
  }

  /**
   * @param id
   * @param level
   * @param version
   */
  public AnalyticGeometry(String id, int level, int version) {
    super(id,level,version);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public AnalyticGeometry clone() {
    return new AnalyticGeometry(this);
  }



  /**
   * Returns {@code true}, if listOfAnalyticVolumes contains at least one element.
   *
   * @return {@code true}, if listOfAnalyticVolumes contains at least one element,
   *         otherwise {@code false}
   */
  public boolean isSetListOfAnalyticVolumes() {
    if ((listOfAnalyticVolumes == null) || listOfAnalyticVolumes.isEmpty()) {
      return false;
    }
    return true;
  }


  /**
   * Returns the listOfAnalyticVolumes. Creates it if it is not already existing.
   *
   * @return the listOfAnalyticVolumes
   */
  public ListOf<AnalyticVolume> getListOfAnalyticVolumes() {
    if (!isSetListOfAnalyticVolumes()) {
      listOfAnalyticVolumes = new ListOf<AnalyticVolume>();
      listOfAnalyticVolumes.setNamespace(SpatialConstants.namespaceURI);
      listOfAnalyticVolumes.setSBaseListType(ListOf.Type.other);
      registerChild(listOfAnalyticVolumes);
    }
    return listOfAnalyticVolumes;
  }


  /**
   * Sets the given {@code ListOf<AnalyticVolume>}. If listOfAnalyticVolumes
   * was defined before and contains some elements, they are all unset.
   *
   * @param listOfAnalyticVolumes
   */
  public void setListOfAnalyticVolumes(ListOf<AnalyticVolume> listOfAnalyticVolumes) {
    unsetListOfAnalyticVolumes();
    this.listOfAnalyticVolumes = listOfAnalyticVolumes;
    registerChild(this.listOfAnalyticVolumes);
  }


  /**
   * Returns {@code true}, if listOfAnalyticVolumes contain at least one element,
   *         otherwise {@code false}
   *
   * @return {@code true}, if listOfAnalyticVolumes contain at least one element,
   *         otherwise {@code false}
   */
  public boolean unsetListOfAnalyticVolumes() {
    if (isSetListOfAnalyticVolumes()) {
      ListOf<AnalyticVolume> oldAnalyticVolumes = listOfAnalyticVolumes;
      listOfAnalyticVolumes = null;
      oldAnalyticVolumes.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }


  /**
   * Adds a new {@link AnalyticVolume} to the listOfAnalyticVolumes.
   * <p>The listOfAnalyticVolumes is initialized if necessary.
   *
   * @param analyticVolume the element to add to the list
   * @return true (as specified by {@link Collection#add})
   */
  public boolean addAnalyticVolume(AnalyticVolume analyticVolume) {
    return getListOfAnalyticVolumes().add(analyticVolume);
  }

  /**
   * Removes an element from the listOfAnalyticVolumes.
   *
   * @param analyticVolume the element to be removed from the list
   * @return true if the list contained the specified element
   * @see List#remove(Object)
   */
  public boolean removeAnalyticVolume(AnalyticVolume analyticVolume) {
    if (isSetListOfAnalyticVolumes()) {
      if (getListOfAnalyticVolumes().size()<=1) {
        return false; //There must be at least one AnalyticVolume defined for this list
      } else {
        return getListOfAnalyticVolumes().remove(analyticVolume);
      }
    }
    return false;
  }


  /**
   * Removes an element from the listOfAnalyticVolumes at the given index.
   *
   * @param i the index where to remove the {@link AnalyticVolume}
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index &lt; 0 || index &gt; list.size).
   */
  public void removeAnalyticVolume(int i) {
    if (!isSetListOfAnalyticVolumes()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    if (getListOfAnalyticVolumes().size()<=1) {
      throw new SBMLException("There must be at least one AnalyticVolume defined for this list");
    }
    getListOfAnalyticVolumes().remove(i);
  }

  /**
   * 
   * @param id
   */
  public void removeAnalyticVolume(String id) {
    getListOfAnalyticVolumes().removeFirst(new NameFilter(id));
  }

  /**
   * Creates a new AnalyticVolume element and adds it to the ListOfAnalyticVolumes list
   * @return
   */
  public AnalyticVolume createAnalyticVolume() {
    return createAnalyticVolume(null);
  }

  /**
   * Creates a new {@link AnalyticVolume} element and adds it to the ListOfAnalyticVolumes list
   * 
   * @param id
   * @return a new {@link AnalyticVolume} element
   */
  public AnalyticVolume createAnalyticVolume(String id) {
    AnalyticVolume analyticVolume = new AnalyticVolume(id, getLevel(), getVersion());
    addAnalyticVolume(analyticVolume);
    return analyticVolume;
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
    if (isSetListOfAnalyticVolumes()) {
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
    if (isSetListOfAnalyticVolumes()) {
      if (pos == index) {
        return getListOfAnalyticVolumes();
      }
      pos++;
    }
    throw new IndexOutOfBoundsException(MessageFormat.format(
      resourceBundle.getString("IndexExceedsBoundsException"), index,
      Math.min(pos, 0)));
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.GeometryDefinition#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equal =  super.equals(object);
    if (equal) {
      AnalyticGeometry gm = (AnalyticGeometry) object;
      equal &= gm.isSetListOfAnalyticVolumes() == isSetListOfAnalyticVolumes();
      if (equal && isSetListOfAnalyticVolumes()) {
        equal &= gm.getListOfAnalyticVolumes().equals(getListOfAnalyticVolumes());
      }

    }
    return equal;
  }

  @Override
  public int hashCode() {
    final int prime = 1039;//Change this prime number
    int hashCode = super.hashCode();
    if (isSetListOfAnalyticVolumes()) {
      hashCode += prime * getListOfAnalyticVolumes().hashCode();
    }
    return hashCode;
  }


}
