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
package org.sbml.jsbml.ext.layout;

import java.text.MessageFormat;
import java.util.List;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.util.TreeNodeChangeListener;

/**
 * The {@link Curve} class describes how to connect elements in a diagram defined with
 * the use of the {@link Layout} package. A curve is fully specified by a mandatory
 * listOfCurveSegments element and is used in four places in the {@link Layout}
 * package: {@link SpeciesReferenceGlyph}, {@link ReactionGlyph},
 * {@link ReferenceGlyph}, and {@link GeneralGlyph}.
 * 
 * @author Nicolas Rodriguez
 * @author Andreas Dr&auml;ger
 * @since 1.0
 * @version $Rev$
 */
public class Curve extends AbstractNamedSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -5435135643993920570L;

  /**
   * 
   */
  ListOf<CurveSegment> listOfCurveSegments = new ListOf<CurveSegment>();

  /**
   * 
   */
  public Curve() {
    super();
    initDefaults();
  }

  /**
   * @param curve
   * 
   */
  public Curve(Curve curve) {
    super(curve);
    if (curve.isSetListOfCurveSegments()) {
      setListOfCurveSegments(curve.getListOfCurveSegments().clone());
    }
  }

  /**
   * 
   * @param level
   * @param version
   */
  public Curve(int level, int version) {
    super(level, version);
    initDefaults();
  }

  /**
   * 
   * @param curveSegment
   * @return
   * @see List#add(Object)
   */
  public boolean addCurveSegment(CurveSegment curveSegment) {
    return getListOfCurveSegments().add(curveSegment);
  }

  /**
   * 
   * @param index
   * @param element
   */
  public void addCurveSegment(int index, CurveSegment element) {
    getListOfCurveSegments().add(index, element);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public Curve clone() {
    return new Curve(this);
  }


  /**
   * Creates a new {@link LineSegment} instance, adds it to this {@link Curve} and returns it.
   * 
   * @return the new {@link LineSegment} instance
   */
  public LineSegment createLineSegment() {
    LineSegment cs = new LineSegment(getLevel(), getVersion());
    addCurveSegment(cs);
    return cs;
  }

  /**
   * Creates a new {@link CubicBezier} instance, adds it to this {@link Curve} and returns it.
   * 
   * @return the new {@link CubicBezier} instance
   */
  public CubicBezier createCubicBezier() {
    CubicBezier cs = new CubicBezier(getLevel(), getVersion());
    addCurveSegment(cs);
    return cs;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equals = super.equals(object);
    if (equals) {
      Curve curve = (Curve) object;
      equals &= curve.isSetListOfCurveSegments() == isSetListOfCurveSegments();
      if (equals && isSetListOfCurveSegments()) {
        equals &= curve.getListOfCurveSegments().equals(getListOfCurveSegments());
      }
    }
    return equals;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
   */
  @Override
  public TreeNode getChildAt(int index) {
    if (index < 0) {
      throw new IndexOutOfBoundsException(Integer.toString(index));
    }
    int count = super.getChildCount(), pos = 0;
    if (index < count) {
      return super.getChildAt(index);
    } else {
      index -= count;
    }
    if (isSetListOfCurveSegments()) {
      if (pos == index) {
        return getListOfCurveSegments();
      }
      pos++;
    }
    throw new IndexOutOfBoundsException(MessageFormat.format(
      "Index {0,number,integer} >= {1,number,integer}",
      index, +Math.min(pos, 0)));
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildCount()
   */
  @Override
  public int getChildCount() {
    int count = super.getChildCount();
    if (isSetListOfCurveSegments()) {
      count++;
    }
    return count;
  }

  /**
   * 
   * @param n
   * @return
   */
  public CurveSegment getCurveSegment(int n) {
    return getListOfCurveSegments().get(n);
  }

  /**
   * 
   * @return
   */
  public int getCurveSegmentCount() {
    return isSetListOfCurveSegments() ? getListOfCurveSegments().size() : 0;
  }

  /**
   * 
   * @return
   */
  public ListOf<CurveSegment> getListOfCurveSegments() {
    return listOfCurveSegments;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 929;
    int hashCode = super.hashCode();
    if (isSetListOfCurveSegments()) {
      hashCode += prime * getListOfCurveSegments().hashCode();
    }
    return hashCode;
  }

  /**
   * 
   */
  private void initDefaults() {
    setNamespace(LayoutConstants.namespaceURI); // TODO - removed once the mechanism are in place to set package version and namespace
    setPackageVersion(-1);
    packageName = LayoutConstants.shortLabel;
    listOfCurveSegments.setNamespace(LayoutConstants.namespaceURI);
    listOfCurveSegments.setSBaseListType(ListOf.Type.other);
    registerChild(listOfCurveSegments);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
   */
  @Override
  public boolean isIdMandatory() {
    return false;
  }

  /**
   * 
   * @return
   */
  public boolean isSetListOfCurveSegments() {
    return (listOfCurveSegments != null) && (listOfCurveSegments.size() > 0);
  }

  /**
   * 
   * @param cs
   * @return
   */
  public boolean removeCurveSegment(CurveSegment cs) {
    return getListOfCurveSegments().remove(cs);
  }

  /**
   * The listOfCurveSegments element contains arbitrary number of cuve segments that
   * can be either of type {@link LineSegment} or of type {@link CubicBezier}. Here,
   * both classes are child classes of the abstract type {@link CurveSegment}.
   * 
   * @param listOfCurveSegments
   */
  public void setListOfCurveSegments(ListOf<CurveSegment> listOfCurveSegments) {
    unsetListOfCurveSegments();
    this.listOfCurveSegments = listOfCurveSegments;
    if ((this.listOfCurveSegments != null) && (this.listOfCurveSegments.getSBaseListType() != ListOf.Type.other)) {
      this.listOfCurveSegments.setSBaseListType(ListOf.Type.other);
    }
    registerChild(this.listOfCurveSegments);
  }

  /**
   * Removes the {@link #listOfCurveSegments} from this
   * {@link org.sbml.jsbml.Model} and notifies
   * all registered instances of
   * {@link TreeNodeChangeListener}.
   * 
   * @return {@code true} if calling this method lead to a change in this
   *         data structure.
   */
  public boolean unsetListOfCurveSegments() {
    if (listOfCurveSegments != null) {
      ListOf<CurveSegment> oldListOfCurveSegments = listOfCurveSegments;
      listOfCurveSegments = null;
      oldListOfCurveSegments.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

}
