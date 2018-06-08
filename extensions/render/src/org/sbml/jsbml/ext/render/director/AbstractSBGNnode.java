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

/**
 * @author Andreas Dr&auml;ger
 * @since 1.4
 * @param <T> @see {@link SBGNNode}
 */
public abstract class AbstractSBGNnode<T> implements SBGNNode<T> {
  
  /**
   * Line width in px.
   */
  private double lineWidth = 1d;
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.director.SBGNNode#getLineWidth()
   */
  @Override
  public double getLineWidth() {
    return lineWidth;
  }
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.director.SBGNNode#setLineWidth(double)
   */
  @Override
  public void setLineWidth(double lineWidth) {
    this.lineWidth = lineWidth;
  }
  
}
