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
 * 
 * @author Andreas Dr&auml;ger
 * @since 1.4
 * @param <T>  The concrete data type for a particular implementation of this node.
 */
public abstract class AbstractSBGNProcessNode<T> extends AbstractSBGNnode<T> implements
SBGNProcessNode<T> {
  
  /**
   * Position where the curve to the product begins at the process node
   */
  private Point pointOfContactToProduct;
  
  /**
   *  Position where the curve of the substrate ends at the process node
   */
  private Point pointOfContactToSubstrate;
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.director.SBGNProcessNode#draw(org.sbml.jsbml.ext.layout.Curve, double, org.sbml.jsbml.ext.layout.Point)
   */
  @Override
  public T draw(Curve curve, double rotationAngle, Point rotationCenter) {
    T product = null;
    if (curve.isSetListOfCurveSegments()) {
      for (CurveSegment segment : curve.getListOfCurveSegments()) {
        product = drawCurveSegment(segment, rotationAngle, rotationCenter);
      }
    }
    return product;
  }
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.director.SBGNReactionNode#getPointOfContactToProduct()
   */
  @Override
  public Point getPointOfContactToProduct() {
    return pointOfContactToProduct;
  }
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.director.SBGNReactionNode#getPointOfContactToSubstrate()
   */
  @Override
  public Point getPointOfContactToSubstrate() {
    return pointOfContactToSubstrate;
  }
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.director.SBGNReactionNode#setPointOfContactToProduct(org.sbml.jsbml.ext.layout.Point)
   */
  @Override
  public void setPointOfContactToProduct(Point pointOfContactToProduct) {
    this.pointOfContactToProduct = pointOfContactToProduct;
  }
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.director.SBGNReactionNode#setPointOfContactToSubstrate(org.sbml.jsbml.ext.layout.Point)
   */
  @Override
  public void setPointOfContactToSubstrate(Point pointOfContactToSubstrate) {
    this.pointOfContactToSubstrate = pointOfContactToSubstrate;
  }
  
}
