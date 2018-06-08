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
import org.sbml.jsbml.ext.layout.Point;

/**
 * Interface for all SBGN specified process nodes.
 * 
 * <p>
 * This interface defines the {@link #draw} methods to draw the process node,
 * it allows to set the line width and to calculate the contact points at
 * the process node.
 * 
 * @author Meike Aichele
 * @since 1.4
 * @param <T> The concrete data type for a particular implementation of this node.
 */
public interface SBGNProcessNode<T> extends SBGNNode<T> {
  
  /**
   * Draw a whole curve consisting of multiple curve segments with the given
   * line width.
   * 
   * @param curve
   *        the {@link Curve} to draw
   * @param lineWidth the width of the curve on the screen.
   * @param rotationAngle
   * @param rotationCenter
   * @return T graphical representation of the curve
   */
  public T draw(Curve curve, double rotationAngle, Point rotationCenter);
  
  /**
   * 
   * @param segment
   * @param rotationAngle
   * @param rotationCenter
   * @return
   */
  public T drawCurveSegment(CurveSegment segment, double rotationAngle,
    Point rotationCenter);
  
  /**
   * 
   * @param x
   * @param y
   * @param z
   * @param width
   * @param height
   * @param depth
   * @param rotationAngle
   * @param rotationCenter
   * @return
   */
  public T draw(double x, double y, double z, double width,
    double height, double depth, double rotationAngle, Point rotationCenter);
  
  /**
   * @param pointOfContactToSubstrate the pointOfContactToSubstrate to set
   */
  public void setPointOfContactToSubstrate(Point pointOfContactToSubstrate);
  
  /**
   * @return the pointOfContactToSubstrate
   */
  public Point getPointOfContactToSubstrate();
  
  /**
   * @param pointOfContactToProduct the pointOfContactToProduct to set
   */
  public void setPointOfContactToProduct(Point pointOfContactToProduct);
  
  /**
   * @return the pointOfContactToProduct
   */
  public Point getPointOfContactToProduct();
  
}
