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

/**
 * Represents smooth curves in the {@link Layout} package.
 * 
 * <p>It represents a Bezier curve, and is readily available in
 * most graphics APIs. The class {@link CubicBezier} is derived from {@link LineSegment}.
 * It consists of four elements: the two inherited elements 'start' and 'end', which
 * specify the starting point and the endpoint of the cubic bezier curve, and two elements
 * 'basePoint1' and 'basePoint2', which specify the two additional base points that are
 * needed to describe a cubic bezier curve. These basepoints also allow tools that cannot
 * render bezier curves to approximate them by directly connecting the four points.</p>
 * 
 * @author Nicolas Rodriguez
 * @author Andreas Dr&auml;ger
 * @since 1.0
 */
public class CubicBezier extends LineSegment {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -6747242964512145076L;

  /**
   * 
   */
  private Point basePoint1;

  /**
   * 
   */
  private Point basePoint2;

  /**
   * 
   */
  public CubicBezier() {
    super();
    setType(Type.CUBIC_BEZIER);
  }

  /**
   * @param cubicBezier
   */
  public CubicBezier(CubicBezier cubicBezier) {
    super(cubicBezier);

    if (cubicBezier.isSetBasePoint1()) {
      setBasePoint1(cubicBezier.getBasePoint1().clone());
    }
    if (cubicBezier.isSetBasePoint2()) {
      setBasePoint2(cubicBezier.getBasePoint2().clone());
    }
  }


  /**
   * 
   * @param level
   * @param version
   */
  public CubicBezier(int level, int version) {
    super(level, version);
    setType(Type.CUBIC_BEZIER);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.layout.LineSegment#clone()
   */
  @Override
  public CubicBezier clone() {
    return new CubicBezier(this);
  }

  /**
   * Creates, sets and returns a {@link Point}
   *
   * @return new {@link Point} object.
   */
  public Point createBasePoint1() {
    Point p = new Point(getLevel(), getVersion());
    setBasePoint1(p);
    return p;
  }

  /**
   * Creates, sets and returns a {@link Point} based on the
   * given values.
   * @param x
   * @param y
   * @param z
   * @return new {@link Point} object.
   */
  public Point createBasePoint1(double x, double y, double z) {
    Point p = new Point(getLevel(), getVersion());
    p.setX(x);
    p.setY(y);
    p.setZ(z);
    setBasePoint1(p);
    return p;
  }

  /**
   * Creates, sets and returns a {@link Point}
   *
   * @return new {@link Point} object.
   */
  public Point createBasePoint2() {
    Point p = new Point(getLevel(), getVersion());
    setBasePoint2(p);
    return p;
  }

  /**
   * Creates, sets and returns a {@link Point} based on the
   * given values.
   * @param x
   * @param y
   * @param z
   * @return new {@link Point} object.
   */
  public Point createBasePoint2(double x, double y, double z) {
    Point p = new Point(getLevel(), getVersion());
    p.setX(x);
    p.setY(y);
    p.setZ(z);
    setBasePoint2(p);
    return p;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equals = super.equals(object);
    if (equals) {
      CubicBezier cb = (CubicBezier) object;
      equals &= cb.isSetBasePoint1() == isSetBasePoint1();
      if (equals && isSetBasePoint1()) {
        equals &= cb.getBasePoint1().equals(getBasePoint1());
      }
      equals &= cb.isSetBasePoint2() == isSetBasePoint2();
      if (equals && cb.isSetBasePoint2()) {
        equals &= cb.getBasePoint2().equals(getBasePoint2());
      }
    }
    return equals;
  }

  /**
   * 
   * @return
   */
  public Point getBasePoint1() {
    return basePoint1;
  }

  /**
   * 
   * @return
   */
  public Point getBasePoint2() {
    return basePoint2;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.layout.LineSegment#getChildAt(int)
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
    if (isSetBasePoint1()) {
      if (pos == index) {
        return getBasePoint1();
      }
      pos++;
    }
    if (isSetBasePoint2()) {
      if (pos == index) {
        return getBasePoint2();
      }
      pos++;
    }
    throw new IndexOutOfBoundsException(MessageFormat.format(
      resourceBundle.getString("IndexExceedsBoundsException"),
      index, Math.min(pos, 0)));
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.layout.LineSegment#getChildCount()
   */
  @Override
  public int getChildCount() {
    int count = super.getChildCount();
    if (isSetBasePoint1()) {
      count++;
    }
    if (isSetBasePoint2()) {
      count++;
    }
    return count;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 919;
    int hashCode = super.hashCode();
    if (isSetBasePoint1()) {
      hashCode += prime * getBasePoint1().hashCode();
    }
    if (isSetBasePoint2()) {
      hashCode += prime * getBasePoint2().hashCode();
    }
    return hashCode;
  }

  /**
   * @return
   */
  public boolean isSetBasePoint1() {
    return basePoint1 != null;
  }

  /**
   * @return
   */
  public boolean isSetBasePoint2() {
    return basePoint2 != null;
  }

  /**
   * 
   * @return
   */
  public Point removeBasePoint1() {
    if (!isSetBasePoint1()) {
      return null;
    }
    Point bp1 = getBasePoint1();
    setBasePoint1(null);
    return bp1;
  }

  /**
   * 
   * @return
   */
  public Point removeBasePoint2() {
    if (!isSetBasePoint2()) {
      return null;
    }
    Point bp2 = getBasePoint2();
    setBasePoint2(null);
    return bp2;
  }

  /**
   * basePoint1 specifies represents the base point closer
   * to the start point.
   * 
   * @param basePoint1
   */
  public void setBasePoint1(Point basePoint1) {
    if (this.basePoint1 != null) {
      Point oldValue = this.basePoint1;
      this.basePoint1 = null;
      oldValue.fireNodeRemovedEvent();
    }
    this.basePoint1 = basePoint1;

    if (basePoint1 != null) {
      basePoint1.setElementName(LayoutConstants.basePoint1);
    }
    registerChild(this.basePoint1);
  }

  /**
   * 
   * @param basePoint2
   */
  public void setBasePoint2(Point basePoint2) {
    if (this.basePoint2 != null) {
      Point oldValue = this.basePoint2;
      this.basePoint2 = null;
      oldValue.fireNodeRemovedEvent();
    }
    this.basePoint2 = basePoint2;

    if (basePoint2 != null) {
      basePoint2.setElementName(LayoutConstants.basePoint2);
    }
    registerChild(this.basePoint2);
  }

}
