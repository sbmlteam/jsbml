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
package org.sbml.jsbml.ext.render;

import java.text.MessageFormat;
import java.util.Map;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.layout.CubicBezier;
import org.sbml.jsbml.ext.layout.CurveSegment;
import org.sbml.jsbml.ext.layout.ICurve;
import org.sbml.jsbml.ext.layout.LayoutConstants;
import org.sbml.jsbml.ext.layout.LineSegment;

/**
 * Implements the curve concept from the SBML render extension.
 * 
 * <p>
 * The curve concept in the SBML render extension is similar to the curves in
 * the SBML layout. Each curve consists of a number of either straight line
 * segments or cubic B&eacute;zier elements. The two element types can also be
 * mixed in a single curve object.</p>
 * 
 * <p>
 * In contrast to layout curves, render curves can not have gaps and the
 * individual coordinates of the curve elements can be specified as a
 * combination of absolute and relative values.</p>
 * 
 * <p>
 * Another difference to layout curves is the fact that render curves can
 * specify decorations to be applied to the start and/or the end of the curve
 * (@see LineEnding).</p>
 * 
 * <p>
 * Since {@link RenderCurve} is derived from {@link GraphicalPrimitive1D}, it
 * inherits all its attributes and methods.</p>
 *
 * @author Eugen Netz
 * @author Alexander Diamantikos
 * @author Jakob Matthes
 * @author Jan Rudolph
 * @since 1.0
 */
public class RenderCurve extends GraphicalPrimitive1D implements ICurve {
  /**
   * 
   */
  private static final long serialVersionUID = -1941713884972334826L;
  /**
   * 
   */
  protected String startHead;
  /**
   * 
   */
  protected String endHead;

  /**
   * 
   */
  protected ListOf<RenderPoint> listOfElements;

  /**
   * 
   */
  protected ListOf<CurveSegment> listOfCurveSegments;
  
  /**
   * Creates an Curve instance
   */
  public RenderCurve() {
    super();
    initDefaults();
  }

  /**
   * Clone constructor
   * 
   * @param obj the {@link RenderCurve} to clone
   */
  public RenderCurve(RenderCurve obj) {
    super();
    
    if (obj.isSetStartHead()) {
      setStartHead(obj.startHead);
    }    
    if (obj.isSetEndHead()) {
      setEndHead(obj.endHead);
    }

    if (obj.isSetListOfElements()) {
      setListOfElements(obj.getListOfElements().clone());
    }
    if (obj.isSetListOfCurveSegments()) {
      setListOfCurveSegments(obj.getListOfCurveSegments().clone());
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.GraphicalPrimitive1D#clone()
   */
  @Override
  public RenderCurve clone() {
    return new RenderCurve(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.GraphicalPrimitive1D#initDefaults()
   */
  @Override
  public void initDefaults() {
    setPackageVersion(-1);
    packageName = RenderConstants.shortLabel;
  }



  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 3119;
    int result = super.hashCode();
    result = prime * result + ((endHead == null) ? 0 : endHead.hashCode());
    result = prime * result
        + ((listOfCurveSegments == null) ? 0 : listOfCurveSegments.hashCode());
    result = prime * result
        + ((listOfElements == null) ? 0 : listOfElements.hashCode());
    result = prime * result + ((startHead == null) ? 0 : startHead.hashCode());
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
    RenderCurve other = (RenderCurve) obj;
    if (endHead == null) {
      if (other.endHead != null) {
        return false;
      }
    } else if (!endHead.equals(other.endHead)) {
      return false;
    }
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
    if (startHead == null) {
      if (other.startHead != null) {
        return false;
      }
    } else if (!startHead.equals(other.startHead)) {
      return false;
    }
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getElementName()
   */
  @Override
  public String getElementName() {
    return RenderConstants.renderCurve;
  }

  /**
   * Returns the value of startHead
   * 
   * @return the value of startHead
   */
  public String getStartHead() {
    if (isSetStartHead()) {
      return startHead;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.startHead, this);
  }

  /**
   * Returns {@code true} if startHead is set
   * 
   * @return whether startHead is set
   */
  public boolean isSetStartHead() {
    return startHead != null;
  }

  /**
   * Sets the value of startHead.
   * 
   * @param startHead the start head
   */
  public void setStartHead(String startHead) {
    String oldStartHead = this.startHead;
    this.startHead = startHead;
    firePropertyChange(RenderConstants.startHead, oldStartHead, this.startHead);
  }

  /**
   * Unsets the variable startHead
   * 
   * @return {@code true}, if startHead was set before,
   *         otherwise {@code false}
   */
  public boolean unsetStartHead() {
    if (isSetStartHead()) {
      String oldStartHead = startHead;
      startHead = null;
      firePropertyChange(RenderConstants.startHead, oldStartHead, startHead);
      return true;
    }
    return false;
  }


  /**
   * Returns the value of endHead
   * 
   * @return the value of endHead
   */
  public String getEndHead() {
    if (isSetEndHead()) {
      return endHead;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.endHead, this);
  }

  /**
   * Returns {@code true} if endHead is set.
   * 
   * @return whether endHead is set
   */
  public boolean isSetEndHead() {
    return endHead != null;
  }

  /**
   * Sets the value of endHead
   * 
   * @param endHead the end head
   */
  public void setEndHead(String endHead) {
    String oldEndHead = this.endHead;
    this.endHead = endHead;
    firePropertyChange(RenderConstants.endHead, oldEndHead, this.endHead);
  }

  /**
   * Unsets the variable endHead
   * 
   * @return {@code true}, if endHead was set before,
   *         otherwise {@code false}
   */
  public boolean unsetEndHead() {
    if (isSetEndHead()) {
      String oldEndHead = endHead;
      endHead = null;
      firePropertyChange(RenderConstants.endHead, oldEndHead, endHead);
      return true;
    }
    return false;
  }


  /**
   * @return {@code true}, if listOfElements contains at least one element,
   *         otherwise {@code false}
   */
  public boolean isSetListOfElements() {
    if ((listOfElements == null) || listOfElements.isEmpty()) {
      return false;
    }
    return true;
  }

  /**
   * @return the listOfElements
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

  /**
   * @param listOfElements the list of {@link RenderPoint}s
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
      listOfElements.setOtherListName(RenderConstants.listOfElements);
      
      registerChild(this.listOfElements);
    }
  }

  /**
   * @return {@code true}, if listOfElements contained at least one element,
   *         otherwise {@code false}
   */
  public boolean unsetListOfElements() {
    if (isSetListOfElements()) {
      ListOf<RenderPoint> oldElements = listOfElements;
      listOfElements = null;
      oldElements.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /**
   * @param element
   * @return
   */
  public boolean addElement(RenderPoint element) {
    return getListOfElements().add(element);
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
   * Creates a new {@link RenderPoint} instance and adds it to the ListOfElements list
   * 
   * @return a new {@link RenderPoint} instance
   * @deprecated use#createRenderPoint()
   */
  public RenderPoint createElement() {
    return createRenderPoint();
  }

  /**
   * Creates a new {@link RenderCubicBezier} instance and adds it to the ListOfElements list
   * 
   * @return a new {@link RenderCubicBezier} instance
   */
  public RenderPoint createRenderCubicBezier() {
    RenderPoint element = new RenderCubicBezier();
    addElement(element);
    return element;
  }

  /**
   * Creates a new {@link RenderPoint} instance and adds it to the ListOfElements list
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
    if (listOfCurveSegments == null) {
      return false;
    }
    return true;
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
   * @param listOfCurveSegments the list of {@link CurveSegment}s
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
   * <p>The listOfCurveSegments is initialized if necessary.
   *
   * @param curveSegment the element to add to the list
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
   * @param curveSegment the element to be removed from the list.
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
   * Removes an element from the {@link #listOfCurveSegments} at the given index.
   *
   * @param i the index where to remove the {@link CurveSegment}.
   * @return the specified element if it was successfully found and removed.
   * @throws IndexOutOfBoundsException if the listOf is not set or if the index is
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
   * @param i the index of the {@link CurveSegment} element to get.
   * @return an element from the listOfCurveSegments at the given index.
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index < 0 || index > list.size).
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

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetStartHead()) {
      attributes.remove(RenderConstants.startHead);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.startHead,
        getStartHead());
    }
    if (isSetEndHead()) {
      attributes.remove(RenderConstants.endHead);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.endHead,
        getEndHead());
    }
    return attributes;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
    if (!isAttributeRead) {
      isAttributeRead = true;
      if (attributeName.equals(RenderConstants.startHead)) {
        setStartHead(value);
      }
      else if (attributeName.equals(RenderConstants.endHead)) {
        setEndHead(value);
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }
    
  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.GraphicalPrimitive1D#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.GraphicalPrimitive1D#getChildAt(int)
   */
  @Override
  public SBase getChildAt(int childIndex) {
    if (childIndex < 0) {
      throw new IndexOutOfBoundsException(MessageFormat.format(resourceBundle.getString("IndexSurpassesBoundsException"), childIndex, 0));
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
    
    throw new IndexOutOfBoundsException(MessageFormat.format(
      resourceBundle.getString("IndexExceedsBoundsException"), childIndex,
      Math.min(pos, 0)));
  }

  /* (non-Javadoc)
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

}
