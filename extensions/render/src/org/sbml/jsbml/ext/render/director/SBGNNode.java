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

import org.sbml.jsbml.ext.layout.BoundingBox;

/**
 * interface for the different types of entity pool nodes
 * <ul>
 * <li>unspecified entity</li>
 * <li>simple chemical</li>
 * <li>macromolecule</li>
 * <li>source/sink</li>
 * </ul>
 * 
 * @author Mirjam Gutekunst
 * @since 1.4
 * @param <T>
 *        The concrete product type of this node. This can be, for instance, a
 *        {@link String} command in some drawing language to actually draw this
 *        node or some kind of node data structure in a graph drawing library
 *        etc.
 */
public interface SBGNNode<T> {
  
  /**
   * Method for drawing an entity pool node with the specified position and
   * size of a {@link BoundingBox}
   * 
   * @param x coordinate of the point of a BoundingBox
   * @param y coordinate of the point of a BoundingBox
   * @param z coordinate of the point of a BoundingBox
   * @param width of a BoundingBox
   * @param height of a BoundingBox
   * @param depth of a BoundingBox
   * @return T as a graphical representation of any form
   */
  public T draw(double x, double y, double z,
    double width, double height, double depth);
  
  /**
   * @return the lineWidth
   */
  public double getLineWidth();
  
  /**
   * @param lineWidth the lineWidth to set
   */
  public void setLineWidth(double lineWidth);
  
}
