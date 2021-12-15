/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * Copyright (C) 2009-2022 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 5. The Babraham Institute, Cambridge, UK
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.render;

import java.text.MessageFormat;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.ext.layout.CubicBezier;
import org.sbml.jsbml.ext.layout.CurveSegment;
import org.sbml.jsbml.ext.layout.ICurve;
import org.sbml.jsbml.ext.layout.LayoutConstants;
import org.sbml.jsbml.ext.layout.LineSegment;

/**
 * @author Eugen Netz
 * @author Alexander Diamantikos
 * @author Jakob Matthes
 * @author Jan Rudolph
 * @since 1.0
 */
public class Polygon extends GraphicalPrimitive2D implements ICurve {

  /**
   * Generated serial version identifier
   */
  private static final long    serialVersionUID = 9207043017589271103L;
  /**
   * 
   */
  private ListOf<RenderPoint>  listOfElements;
  /**
   * 
   */
  private ListOf<CurveSegment> listOfCurveSegments;

  /**
   * Creates an Polygon instance
   */
  public Polygon() {
    super();
    initDefaults();
  }


  /**
   * Clone constructor
   * 
   * @param obj
   *        the {@link Polygon} instance to clone
   */
  public Polygon(Polygon obj) {
    super(obj);
    if (obj.isSetListOfElements()) {
      setListOfElements(obj.getListOfElements().clone());
    }
    if (obj.isSetListOfCurveSegments()) {
      setListOfCurveSegments(obj.getListOfCurveSegments().clone());
    }
  }


  /**
   * @param element
   * @return
   */
  public boolean addElement(RenderPoint element) {
    return getListOfElements().add(element);
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.GraphicalPrimitive2D#clone()
   */
  @Override
  public Polygon clone() {
    return new Polygon(this);
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.GraphicalPrimitive1D#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return true;
  }


  /**
   * The listOfElements and listOfCurveSegments will take the indices 0 and 1,
   * or 0 (if only one is set). ChildElements like Annotation will be shifted
   * accordingly
   */
  @Override
  public TreeNode getChildAt(int childIndex) {
    if (childIndex < 0) {
      throw new IndexOutOfBoundsException(MessageFormat.format(
        resourceBundle.getString("IndexSurpassesBoundsException"), childIndex,
        0));
    }
    int pos = 0;
    if (isSetListOfElements()) {
      if (pos == childIndex) {
        return getListOfElements();
      }
      pos++;
    }
    if (isSetListOfCurveSegments()) {
      if (pos == childIndex) {
        return getListOfCurveSegments();
      }
      pos++;
    }
    // Super will throw exception, if necessary
    return super.getChildAt(childIndex - pos);
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.GraphicalPrimitive1D#getChildCount()
   */
  @Override
  public int getChildCount() {
    int count = super.getChildCount();
    if (isSetListOfElements()) {
      count++;
    }
    if (isSetListOfCurveSegments()) {
      count++;
    }
    return count;
  }


  /**
   * @return the value of listOfElements
   */
  public ListOf<RenderPoint> getListOfElements() {
    if (!isSetListOfElements()) {
      listOfElements = new ListOf<RenderPoint>(getLevel(), getVersion());
      listOfElements.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'render'
      listOfElements.setPackageName(null);
      listOfElements.setPackageName(RenderConstants.shortLabel);
      listOfElements.setSBaseListType(ListOf.Type.other);
      listOfElements.setOtherListName(RenderConstants.listOfElements);
      registerChild(listOfElements);
    }
    return listOfElements;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.GraphicalPrimitive2D#initDefaults()
   */
  @Override
  public void initDefaults() {
    setPackageVersion(-1);
    packageName = RenderConstants.shortLabel;
  }


  /**
   * To check, whether {@link #listOfElements} is non-{@code null}, but empty,
   * use {@link #isListOfElementsEmpty()}
   * 
   * @return whether listOfElements is set (to a non-empty and non-null list)
   */
  public boolean isSetListOfElements() {
    return listOfElements != null && !listOfElements.isEmpty();
  }


  /**
   * @return {@code true} iff listOfElements is not {@code null}, but empty
   *         (relevant for validation)
   */
  public boolean isListOfElementsEmpty() {
    return listOfElements != null && listOfElements.isEmpty();
  }


  /**
   * Set the value of listOfElements
   * 
   * @param listOfElements
   */
  public void setListOfElements(ListOf<RenderPoint> listOfElements) {
    unsetListOfElements();
    this.listOfElements = listOfElements;
    if (listOfElements != null) {
      listOfElements.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'render'
      listOfElements.setPackageName(null);
      listOfElements.setPackageName(RenderConstants.shortLabel);
      listOfElements.setSBaseListType(ListOf.Type.other);
      registerChild(this.listOfElements);
    }
  }


  /**
   * Unsets the variable listOfElements
   * 
   * @return {@code true}, if listOfElements was set before,
   *         otherwise {@code false}
   */
  public boolean unsetListOfElements() {
    if (isSetListOfElements()) {
      ListOf<RenderPoint> oldListOfElements = listOfElements;
      unregisterChild(listOfElements);
      listOfElements = null;
      firePropertyChange(RenderConstants.listOfElements, oldListOfElements,
        listOfElements);
      return true;
    }
    return false;
  }


  /**
   * @param element
   * @return
   */
  public boolean removeElement(RenderPoint element) {
    if (isSetListOfElements()) {
      return getListOfElements().remove(element);
    }
    return false;
  }


  /**
   * @param i
   */
  public void removeElement(int i) {
    if (!isSetListOfElements()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    getListOfElements().remove(i);
  }


  /**
   * Creates a new {@link RenderCubicBezier} instance and adds it to the
   * ListOfElements list
   * 
   * @return a new {@link RenderCubicBezier} instance
   */
  public RenderCubicBezier createRenderCubicBezier() {
    RenderCubicBezier element = new RenderCubicBezier();
    addElement(element);
    return element;
  }


  /**
   * Creates a new {@link RenderPoint} instance and adds it to the
   * ListOfElements list
   * 
   * @return a new {@link RenderPoint} instance
   */
  public RenderPoint createRenderPoint() {
    RenderPoint element = new RenderPoint();
    addElement(element);
    return element;
  }


  /**
   * Returns {@code true} if {@link #listOfCurveSegments} contains at least
   * one element.
   *
   * @return {@code true} if {@link #listOfCurveSegments} contains at least
   *         one element, otherwise {@code false}.
   */
  public boolean isSetListOfCurveSegments() {
    return listOfCurveSegments != null && !listOfCurveSegments.isEmpty();
  }


  /**
   * @return {@code true} iff listOfCurveSegments is not {@code null}, but empty
   *         (relevant for validation)
   */
  public boolean isListOfCurveSegmentsEmpty() {
    return listOfCurveSegments != null && listOfCurveSegments.isEmpty();
  }


  /**
   * Returns the {@link #listOfCurveSegments}.
   * Creates it if it does not already exist.
   *
   * @return the {@link #listOfCurveSegments}.
   */
  public ListOf<CurveSegment> getListOfCurveSegments() {
    if (listOfCurveSegments == null) {
      listOfCurveSegments = new ListOf<CurveSegment>();
      listOfCurveSegments.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'layout'
      listOfCurveSegments.setPackageName(null);
      listOfCurveSegments.setPackageName(LayoutConstants.shortLabel);
      listOfCurveSegments.setSBaseListType(ListOf.Type.other);
      listOfCurveSegments.setOtherListName(LayoutConstants.listOfCurveSegments);
      registerChild(listOfCurveSegments);
    }
    return listOfCurveSegments;
  }


  /**
   * Sets the given {@code ListOf<CurveSegment>}.
   * If {@link #listOfCurveSegments} was defined before and contains some
   * elements, they are all unset.
   *
   * @param listOfCurveSegments
   *        the list of {@link CurveSegment}s
   */
  public void setListOfCurveSegments(ListOf<CurveSegment> listOfCurveSegments) {
    unsetListOfCurveSegments();
    this.listOfCurveSegments = listOfCurveSegments;
    if (listOfCurveSegments != null) {
      listOfCurveSegments.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'layout'
      listOfCurveSegments.setPackageName(null);
      listOfCurveSegments.setPackageName(LayoutConstants.shortLabel);
      listOfCurveSegments.setSBaseListType(ListOf.Type.other);
      listOfCurveSegments.setOtherListName(LayoutConstants.listOfCurveSegments);
      registerChild(listOfCurveSegments);
    }
  }


  /**
   * Returns {@code true} if {@link #listOfCurveSegments} contains at least
   * one element, otherwise {@code false}.
   *
   * @return {@code true} if {@link #listOfCurveSegments} contains at least
   *         one element, otherwise {@code false}.
   */
  public boolean unsetListOfCurveSegments() {
    if (isSetListOfCurveSegments()) {
      ListOf<CurveSegment> oldCurveSegments = this.listOfCurveSegments;
      this.listOfCurveSegments = null;
      oldCurveSegments.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }


  /**
   * Adds a new {@link CurveSegment} to the {@link #listOfCurveSegments}.
   * <p>
   * The listOfCurveSegments is initialized if necessary.
   *
   * @param curveSegment
   *        the element to add to the list
   * @return {@code true} (as specified by {@link java.util.Collection#add})
   * @see java.util.Collection#add(Object)
   */
  public boolean addCurveSegment(CurveSegment curveSegment) {
    return getListOfCurveSegments().add(curveSegment);
  }


  @Override
  public void addCurveSegment(int index, CurveSegment element) {
    getListOfCurveSegments().add(index, element);
  }


  /**
   * Removes an element from the {@link #listOfCurveSegments}.
   *
   * @param curveSegment
   *        the element to be removed from the list.
   * @return {@code true} if the list contained the specified element and it was
   *         removed.
   * @see java.util.List#remove(Object)
   */
  public boolean removeCurveSegment(CurveSegment curveSegment) {
    if (isSetListOfCurveSegments()) {
      return getListOfCurveSegments().remove(curveSegment);
    }
    return false;
  }


  /**
   * Removes an element from the {@link #listOfCurveSegments} at the given
   * index.
   *
   * @param i
   *        the index where to remove the {@link CurveSegment}.
   * @return the specified element if it was successfully found and removed.
   * @throws IndexOutOfBoundsException
   *         if the listOf is not set or if the index is
   *         out of bound ({@code (i < 0) || (i > listOfCurveSegments)}).
   */
  public CurveSegment removeCurveSegment(int i) {
    if (!isSetListOfCurveSegments()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfCurveSegments().remove(i);
  }


  /**
   * Creates a new {@link LineSegment} instance and adds it to the
   * {@link #listOfCurveSegments} list.
   *
   * @return the newly created element, i.e., the last item in the
   *         {@link #listOfCurveSegments}
   */
  public LineSegment createLineSegment() {
    LineSegment curveSegment = new LineSegment(getLevel(), getVersion());
    addCurveSegment(curveSegment);
    return curveSegment;
  }


  /**
   * Creates a new {@link CubicBezier} instance and adds it to the
   * {@link #listOfCurveSegments} list.
   *
   * @return the newly created element, i.e., the last item in the
   *         {@link #listOfCurveSegments}
   */
  public CubicBezier createCubicBezier() {
    CubicBezier curveSegment = new CubicBezier(getLevel(), getVersion());
    addCurveSegment(curveSegment);
    return curveSegment;
  }


  /**
   * Gets an element from the {@link #listOfCurveSegments} at the given index.
   *
   * @param i
   *        the index of the {@link CurveSegment} element to get.
   * @return an element from the listOfCurveSegments at the given index.
   * @throws IndexOutOfBoundsException
   *         if the listOf is not set or
   *         if the index is out of bound (index < 0 || index > list.size).
   */
  public CurveSegment getCurveSegment(int i) {
    if (!isSetListOfCurveSegments()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfCurveSegments().get(i);
  }


  /**
   * Returns the number of {@link CurveSegment}s in this
   * {@link RenderCurve}.
   * 
   * @return the number of {@link CurveSegment}s in this
   *         {@link RenderCurve}.
   */
  public int getCurveSegmentCount() {
    return isSetListOfCurveSegments() ? getListOfCurveSegments().size() : 0;
  }


  /**
   * Returns the number of {@link CurveSegment}s in this
   * {@link RenderCurve}.
   * 
   * @return the number of {@link CurveSegment}s in this
   *         {@link RenderCurve}.
   * @libsbml.deprecated same as {@link #getCurveSegmentCount()}
   */
  public int getNumCurveSegments() {
    return getCurveSegmentCount();
  }


  /*
   * (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 3167;
    int result = super.hashCode();
    result = prime * result
      + ((listOfCurveSegments == null) ? 0 : listOfCurveSegments.hashCode());
    result = prime * result
      + ((listOfElements == null) ? 0 : listOfElements.hashCode());
    return result;
  }


  /*
   * (non-Javadoc)
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
    Polygon other = (Polygon) obj;
    if (listOfCurveSegments == null) {
      if (other.listOfCurveSegments != null) {
        return false;
      }
    } else if (!listOfCurveSegments.equals(other.listOfCurveSegments)) {
      return false;
    }
    if (listOfElements == null) {
      if (other.listOfElements != null) {
        return false;
      }
    } else if (!listOfElements.equals(other.listOfElements)) {
      return false;
    }
    return true;
  }
}
