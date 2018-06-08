/*
 * ---------------------------------------------------------------------
 * This file is part of the SysBio API library.
 *
 * Copyright (C) 2009-2018 by the University of Tuebingen, Germany.
 *
 * This library is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation. A copy of the license
 * agreement is provided in the file named "LICENSE.txt" included with
 * this software distribution and also available online as
 * <http://www.gnu.org/licenses/lgpl-3.0-standalone.html>.
 * ---------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.render.director;

import org.sbml.jsbml.ext.layout.Curve;
import org.sbml.jsbml.ext.layout.CurveSegment;
import org.sbml.jsbml.ext.layout.SpeciesReferenceGlyph;

/**
 * interface for the different types of connecting arcs
 * <ul>
 * <li>consumption</li>
 * <li>reversible consumption</li>
 * <li>production</li>
 * <li>catalysis</li>
 * <li>inhibition</li>
 * </ul>
 * 
 * @author Mirjam Gutekunst
 * @since 1.4
 * @param <T> The concrete datatype for a particular implementation of this arc.
 */
public interface SBGNArc<T> {
  
  /**
   * Method for drawing the connecting arc, with appropriate line ending and the
   * given line width
   * 
   * @param curveSegment
   *        a {@link CurveSegment} of the {@link Curve} of the
   *        {@link SpeciesReferenceGlyph}
   * @param lineWidth the width of the curve on the screen.
   * @return T as a graphical representation of any form
   */
  public T draw(CurveSegment curveSegment, double lineWidth);
  
  /**
   * Draw a whole curve consisting of multiple curve segments with the given
   * line width.
   * 
   * @param curve
   *        the {@link Curve} to draw
   * @param lineWidth the width of the curve on the screen.
   * @return T graphical representation of the curve
   */
  public T draw(Curve curve, double lineWidth);
  
  /**
   * Draw a whole curve consisting of multiple curve segments.
   * 
   * @param curve
   *        the {@link Curve} to draw
   * @return T graphical representation of the curve
   */
  public T draw(Curve curve);
  
}
