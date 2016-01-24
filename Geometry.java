/*
 * $Id$
 * $URL$
 * ---------------------------------------------------------------------
 * This file is part of the SysBio API library.
 *
 * Copyright (C) 2009-2016 by the University of Tuebingen, Germany.
 *
 * This library is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation. A copy of the license
 * agreement is provided in the file named "LICENSE.txt" included with
 * this software distribution and also available online as
 * <http://www.gnu.org/licenses/lgpl-3.0-standalone.html>.
 * ---------------------------------------------------------------------
 */
package de.zbit.sbml.layout;

import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;

import org.sbml.jsbml.ext.layout.CubicBezier;
import org.sbml.jsbml.ext.layout.Curve;
import org.sbml.jsbml.ext.layout.CurveSegment;
import org.sbml.jsbml.ext.layout.LineSegment;
import org.sbml.jsbml.ext.layout.Point;

/**
 * @author Andreas Dr&auml;ger
 * @version $Rev$
 */
public class Geometry {
  
  /**
   * 
   * @param curve
   * @return
   */
  public static GeneralPath toGeneralPath(Curve curve) {
    GeneralPath path = new GeneralPath();
    int i = 0;
    for (CurveSegment segment : curve.getListOfCurveSegments()) {
      Point start = segment.getStart();
      Point end = segment.getEnd();
      if (i == 0) {
        path.moveTo(start.getX(), start.getY());
      } else {
        path.lineTo(start.getX(), start.getY());
      }
      if (segment instanceof LineSegment) {
        path.lineTo(end.getX(), end.getY());
      } else {
        CubicBezier cb = (CubicBezier) segment;
        Point bp1 = cb.getBasePoint1();
        Point bp2 = cb.getBasePoint2();
        path.curveTo(bp1.getX(), bp1.getY(), bp2.getX(), bp2.getY(), end.getX(), end.getY());
      }
      i++;
    }
    return path;
  }
  
  /**
   * 
   * @param path
   * @return
   */
  public static GeneralPath normalize(final GeneralPath path) {
    final double[] buffer = new double[6];
    
    double minX = Double.POSITIVE_INFINITY;
    double maxX = Double.NEGATIVE_INFINITY;
    double minY = Double.POSITIVE_INFINITY;
    double maxY = Double.NEGATIVE_INFINITY;
    for (PathIterator it = path.getPathIterator(null); !it.isDone(); it.next()) {
      int coordCount = 0;
      switch (it.currentSegment(buffer)) {
        case PathIterator.SEG_MOVETO:
          coordCount = 2;
          break;
        case PathIterator.SEG_LINETO:
          coordCount = 2;
          break;
        case PathIterator.SEG_QUADTO:
          coordCount = 4;
          break;
        case PathIterator.SEG_CUBICTO:
          coordCount = 6;
          break;
        case PathIterator.SEG_CLOSE:
          coordCount = 0;
          break;
      }
      for (int i = 0; i < coordCount; i += 2) {
        if (minX > buffer[i]) {
          minX = buffer[i];
        }
        if (maxX < buffer[i]) {
          maxX = buffer[i];
        }
      }
      for (int i = 1; i < coordCount; i += 2) {
        if (minY > buffer[i]) {
          minY = buffer[i];
        }
        if (maxY < buffer[i]) {
          maxY = buffer[i];
        }
      }
    }
    
    final double w = maxX - minX;
    final double h = maxY - minY;
    
    final GeneralPath copy = new GeneralPath();
    for (PathIterator it = path.getPathIterator(null); !it.isDone(); it.next()) {
      switch (it.currentSegment(buffer)) {
        case PathIterator.SEG_MOVETO:
          copy.moveTo((buffer[0] - minX)/w, (buffer[1] - minY)/h);
          break;
        case PathIterator.SEG_LINETO:
          copy.lineTo((buffer[0] - minX)/w, (buffer[1] - minY)/h);
          break;
        case PathIterator.SEG_QUADTO:
          copy.quadTo(
            (buffer[0] - minX)/w, (buffer[1] - minY)/h,
            (buffer[2] - minX)/w, (buffer[3] - minY)/h);
          break;
        case PathIterator.SEG_CUBICTO:
          copy.curveTo(
            (buffer[0] - minX)/w, (buffer[1] - minY)/h,
            (buffer[2] - minX)/w, (buffer[3] - minY)/h,
            (buffer[4] - minX)/w, (buffer[5] - minY)/h);
          break;
        case PathIterator.SEG_CLOSE:
          copy.closePath();
          break;
      }
    }
    
    return copy;
  }
  
}
