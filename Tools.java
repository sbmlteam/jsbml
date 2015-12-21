/*
 * $Id$
 * $URL$
 * ---------------------------------------------------------------------
 * This file is part of the SysBio API library.
 *
 * Copyright (C) 2009-2015 by the University of Tuebingen, Germany.
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

import org.sbml.jsbml.ext.layout.CubicBezier;
import org.sbml.jsbml.ext.layout.Curve;
import org.sbml.jsbml.ext.layout.CurveSegment;
import org.sbml.jsbml.ext.layout.LineSegment;
import org.sbml.jsbml.ext.layout.Point;

/**
 * @author Andreas Dr&auml;ger
 * @version $Rev$
 */
public class Tools {
  
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
  
}
