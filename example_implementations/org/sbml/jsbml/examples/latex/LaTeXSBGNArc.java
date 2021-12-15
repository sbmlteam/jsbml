/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2022 jointly by the following organizations:
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
package org.sbml.jsbml.examples.latex;

import org.sbml.jsbml.ext.layout.CubicBezier;
import org.sbml.jsbml.ext.layout.Curve;
import org.sbml.jsbml.ext.layout.CurveSegment;
import org.sbml.jsbml.ext.render.director.SBGNArc;


/**
 * Abstract Class that collects generic behaviour for the various Arc-Classes:
 * All arcs will have to draw an arrow-Head (via {@link LaTeXSBGNArc#drawHead}),
 * and the specifics of this head are the only differences between the different
 * types of arcs (which connect {@link SpeciesReferenceGlyph}s and
 * {@link ReactionGlyph}s). Note that the layout-specification requires certain
 * directionalities from the arcs, so the implementations here do not need to
 * worry about which end to put the arrow-head onto
 * 
 * @author DavidVetter
 */
public abstract class LaTeXSBGNArc implements SBGNArc<String> {

  @Override
  public String draw(CurveSegment curveSegment, double lineWidth) {
    if(curveSegment.isCubicBezier())  {
      return String.format(
        "\t\\draw[line width=%s] %s;",
        lineWidth, coordinatesForCurveSegment(curveSegment));
    } else {
      return String.format("\t\\draw[line width=%s] %s;",
        lineWidth, coordinatesForCurveSegment(curveSegment));  
    }
  }
  
  @Override
  public String draw(Curve curve, double lineWidth) {
    StringBuffer result = new StringBuffer();
    for (int i = 0; i < curve.getCurveSegmentCount(); i++) {
      /** The final curve-segment gets a head, all others are simply lines */
      if(i == curve.getCurveSegmentCount() - 1) {
        result.append(drawHead(curve.getCurveSegment(i), lineWidth));
      } else {
        result.append(draw(curve.getCurveSegment(i), lineWidth));
      }
      result.append(System.lineSeparator());
    }
    return result.toString();
  }


  @Override
  public String draw(Curve curve) {
    return draw(curve, 1);
  }
  
  
  /**
   * Creates the coordinate-part of a tikz-draw command, based on whether the
   * curveSegment is a cubic bezier curve.
   * 
   * @param curveSegment
   *        the curve to be expressed in coordinates
   * @return a String of form "(xpt, ypt) sth (xpt, ypt)" which can be used for
   *         tikz-draw.
   */
  public String coordinatesForCurveSegment(CurveSegment curveSegment) {
    if(curveSegment.isCubicBezier())  {
      return String.format(
        "(%spt, %spt) .. controls (%spt,%spt) and (%spt, %spt) .. (%spt, %spt)",
        curveSegment.getStart().getX(),
        curveSegment.getStart().getY(),
        ((CubicBezier) curveSegment).getBasePoint1().getX(),
        ((CubicBezier) curveSegment).getBasePoint1().getY(),
        ((CubicBezier) curveSegment).getBasePoint2().getX(),
        ((CubicBezier) curveSegment).getBasePoint2().getY(),
        curveSegment.getEnd().getX(), curveSegment.getEnd().getY());
    } else {
      return String.format("(%spt, %spt) -- (%spt, %spt)",
        curveSegment.getStart().getX(), curveSegment.getStart().getY(),
        curveSegment.getEnd().getX(), curveSegment.getEnd().getY());  
    }
  }
  
  
  /**
   * Draws the final segment of a curve: This one (and only this one) carries
   * the arrow-head marking the type of curve (Production, Consumption etc)
   * 
   * @param curveSegment
   * @param lineWidth
   * @return
   */
  public abstract String drawHead(CurveSegment curveSegment, double lineWidth);
}
