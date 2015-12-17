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

import org.sbml.jsbml.ext.layout.Point;

/**
 * @author Jakob Matthes
 * @version $Rev$
 * @param <T>
 */
public abstract class ProcessNode<T> extends AbstractSBGNProcessNode<T> {
  
  /**
   *  Position where the curve of the substrate ends at the process node
   */
  private Point pointOfContactToSubstrate;
  
  /**
   * Position where the curve to the product begins at the process node
   */
  private Point pointOfContactToProduct;
  
  /* (non-Javadoc)
   * @see de.zbit.sbml.layout.SBGNReactionNode#setPointOfContactToSubstrate(org.sbml.jsbml.ext.layout.Point)
   */
  @Override
  public void setPointOfContactToSubstrate(Point pointOfContactToSubstrate) {
    this.pointOfContactToSubstrate = pointOfContactToSubstrate;
  }
  
  /* (non-Javadoc)
   * @see de.zbit.sbml.layout.SBGNReactionNode#getPointOfContactToSubstrate()
   */
  @Override
  public Point getPointOfContactToSubstrate() {
    return pointOfContactToSubstrate;
  }
  
  /* (non-Javadoc)
   * @see de.zbit.sbml.layout.SBGNReactionNode#setPointOfContactToProduct(org.sbml.jsbml.ext.layout.Point)
   */
  @Override
  public void setPointOfContactToProduct(Point pointOfContactToProduct) {
    this.pointOfContactToProduct = pointOfContactToProduct;
  }
  
  /* (non-Javadoc)
   * @see de.zbit.sbml.layout.SBGNReactionNode#getPointOfContactToProduct()
   */
  @Override
  public Point getPointOfContactToProduct() {
    return pointOfContactToProduct;
  }
  
}
