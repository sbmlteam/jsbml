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
package org.sbml.jsbml.ext.layout;

import java.text.MessageFormat;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.ListOf;

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
 */
public class Curve extends AbstractSBase implements ICurve {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -5435135643993920570L;

  /**
   * 
   */
  ListOf<CurveSegment> listOfCurveSegments;

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

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.layout.ICurve#addCurveSegment(org.sbml.jsbml.ext.layout.CurveSegment)
   */
  @Override
  public boolean addCurveSegment(CurveSegment curveSegment) {
    return getListOfCurveSegments().add(curveSegment);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.layout.ICurve#addCurveSegment(int, org.sbml.jsbml.ext.layout.CurveSegment)
   */
  @Override
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
   * 
   * @param start
   * @param end
   * @return
   */
  public LineSegment createLineSegment(Point start, Point end) {
    LineSegment cs = createLineSegment();
    cs.setStart(start);
    cs.setEnd(end);
    return cs;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.layout.ICurve#createCubicBezier()
   */
  @Override
  public CubicBezier createCubicBezier() {
    CubicBezier cs = new CubicBezier(getLevel(), getVersion());
    addCurveSegment(cs);
    return cs;
  }

  /**
   * 
   * @param start
   * @param basePoint1
   * @param basePoint2
   * @param end
   * @return
   */
  public CubicBezier createCubicBezier(Point start, Point basePoint1, Point basePoint2, Point end) {
    CubicBezier cs = createCubicBezier();
    cs.setStart(start);
    cs.setBasePoint1(basePoint1);
    cs.setBasePoint2(basePoint2);
    cs.setEnd(end);
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
      resourceBundle.getString("IndexExceedsBoundsException"),
      index, Math.min(pos, 0)));
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

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.layout.ICurve#getCurveSegment(int)
   */
  @Override
  public CurveSegment getCurveSegment(int n) {
    return getListOfCurveSegments().get(n);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.layout.ICurve#getCurveSegmentCount()
   */
  @Override
  public int getCurveSegmentCount() {
    return isSetListOfCurveSegments() ? getListOfCurveSegments().size() : 0;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.layout.ICurve#getListOfCurveSegments()
   */
  @Override
  public ListOf<CurveSegment> getListOfCurveSegments() {
    if (listOfCurveSegments == null) {
      listOfCurveSegments = new ListOf<CurveSegment>(getLevel(), getVersion());
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
    setPackageVersion(-1);
    packageName = LayoutConstants.shortLabel;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
   */
  @Override
  public boolean isIdMandatory() {
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.layout.ICurve#isSetListOfCurveSegments()
   */
  @Override
  public boolean isSetListOfCurveSegments() {
    return (listOfCurveSegments != null) && (listOfCurveSegments.size() > 0);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.layout.ICurve#removeCurveSegment(org.sbml.jsbml.ext.layout.CurveSegment)
   */
  @Override
  public boolean removeCurveSegment(CurveSegment cs) {
    if (!isSetListOfCurveSegments()) {
      throw new IndexOutOfBoundsException(Integer.toString(0));
    }
    return getListOfCurveSegments().remove(cs);
  }

  /**
   * 
   * @param i
   * @return
   */
  public CurveSegment removeCurveSegment(int i) {
    if (!isSetListOfCurveSegments()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfCurveSegments().remove(i);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.layout.ICurve#setListOfCurveSegments(org.sbml.jsbml.ListOf)
   */
  @Override
  public void setListOfCurveSegments(ListOf<CurveSegment> listOfCurveSegments) {
    unsetListOfCurveSegments();
    this.listOfCurveSegments = listOfCurveSegments;
    if (this.listOfCurveSegments != null) {
      listOfCurveSegments.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'layout'
      listOfCurveSegments.setPackageName(null);
      listOfCurveSegments.setPackageName(LayoutConstants.shortLabel);
      listOfCurveSegments.setSBaseListType(ListOf.Type.other);
      listOfCurveSegments.setOtherListName(LayoutConstants.listOfCurveSegments);
    }
    registerChild(this.listOfCurveSegments);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.layout.ICurve#unsetListOfCurveSegments()
   */
  @Override
  public boolean unsetListOfCurveSegments() {
    if (listOfCurveSegments != null) {
      ListOf<CurveSegment> oldListOfCurveSegments = listOfCurveSegments;
      listOfCurveSegments = null;
      oldListOfCurveSegments.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  public ReactionGlyph getParentReactionGlyph() {
    if (isSetParent() && getParent().isSetParent()) {
	  return (ReactionGlyph) getParent().getParent();
    }
    return null;
  }

}
