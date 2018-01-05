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
 * The {@link LineSegment} class consists of the mandatory attribute xsi:type and two child
 * elements of type {@link Point}. One is called 'start' and represents the starting point
 * of the line, the other is called 'end' and represents the endpoint of the line. The
 * {@link LineSegment} class is also the base class for {@link CubicBezier}, which represent
 * curved lines instead of straight ones.
 * 
 * @author Nicolas Rodriguez
 * @author Andreas Dr&auml;ger
 * @since 1.0
 */
public class LineSegment extends CurveSegment {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -5085246314333062152L;

  /**
   * 
   */
  Point end;

  /**
   * 
   */
  Point start;

  /**
   * 
   */
  public LineSegment() {
    super();
    setType(Type.LINE_SEGMENT);
  }

  /**
   * 
   * @param level
   * @param version
   */
  public LineSegment(int level, int version) {
    super(level, version);
    setType(Type.LINE_SEGMENT);
  }

  /**
   * 
   * @param lineSegment
   */
  public LineSegment(LineSegment lineSegment) {
    super(lineSegment);
    if (lineSegment.isSetStart()) {
      setStart(lineSegment.getStart().clone());
    }
    if (lineSegment.isSetEnd()) {
      setEnd(lineSegment.getEnd().clone());
    }
  }

  /**
   * 
   * @param lineSegment
   */
  public LineSegment(CurveSegment lineSegment) {
    super(lineSegment);
    if (lineSegment.isSetStart()) {
      setStart(lineSegment.getStart().clone());
    }
    if (lineSegment.isSetEnd()) {
      setEnd(lineSegment.getEnd().clone());
    }

    // Make sure that the type is set properly
    setType(Type.LINE_SEGMENT);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public LineSegment clone() {
    return new LineSegment(this);
  }

  /**
   * Creates, sets and returns a {@link Point}
   *
   * @return new {@link Point} object.
   */
  @Override
  public Point createEnd() {
    Point p = new Point(getLevel(), getVersion());
    setEnd(p);
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
  @Override
  public Point createEnd(double x, double y, double z) {
    Point p = new Point(getLevel(), getVersion());
    p.setX(x);
    p.setY(y);
    p.setZ(z);
    setEnd(p);
    return p;
  }

  /**
   * Creates, sets and returns a {@link Point}
   *
   * @return new {@link Point} object.
   */
  @Override
  public Point createStart() {
    Point p = new Point(getLevel(), getVersion());
    setStart(p);
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
  @Override
  public Point createStart(double x, double y, double z) {
    Point p = new Point(getLevel(), getVersion());
    p.setX(x);
    p.setY(y);
    p.setZ(z);
    setStart(p);
    return p;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equals = super.equals(object);
    if (equals) {
      LineSegment lineSegment = (LineSegment) object;
      equals &= lineSegment.isSetStart() == isSetStart();
      if (equals && isSetStart()) {
        equals &= lineSegment.getStart().equals(getStart());
      }
      equals &= lineSegment.isSetEnd() == isSetEnd();
      if (equals && lineSegment.isSetEnd()) {
        equals &= lineSegment.getEnd().equals(getEnd());
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
    if (isSetStart()) {
      if (pos == index) {
        return getStart();
      }
      pos++;
    }
    if (isSetEnd()) {
      if (pos == index) {
        return getEnd();
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
    if (isSetStart()) {
      count++;
    }
    if (isSetEnd()) {
      count++;
    }
    return count;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.layout.CurveSegment#getEnd()
   */
  @Override
  public Point getEnd() {
    return end;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.layout.CurveSegment#getStart()
   */
  @Override
  public Point getStart() {
    return start;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 911;
    int hashCode = super.hashCode();
    if (isSetStart()) {
      hashCode += prime * getStart().hashCode();
    }
    if (isSetEnd()) {
      hashCode += prime * getEnd().hashCode();
    }
    return hashCode;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.layout.CurveSegment#isSetEnd()
   */
  @Override
  public boolean isSetEnd() {
    return end != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.layout.CurveSegment#isSetStart()
   */
  @Override
  public boolean isSetStart() {
    return start != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.layout.CurveSegment#setEnd(org.sbml.jsbml.ext.layout.Point)
   */
  @Override
  public void setEnd(Point end) {
    if (this.end != null) {
      this.end.fireNodeRemovedEvent();
    }
    this.end = end;

    if (end != null) {
      end.setElementName(LayoutConstants.end);
    }
    registerChild(this.end);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.layout.CurveSegment#setStart(org.sbml.jsbml.ext.layout.Point)
   */
  @Override
  public void setStart(Point start) {
    if (this.start != null) {
      this.start.fireNodeRemovedEvent();
    }
    this.start = start;

    if (start != null) {
      start.setElementName(LayoutConstants.start);
    }
    registerChild(this.start);
  }

}
