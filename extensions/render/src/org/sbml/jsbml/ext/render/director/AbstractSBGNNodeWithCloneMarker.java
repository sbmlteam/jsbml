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
 * @param <T>
 */
public abstract class AbstractSBGNNodeWithCloneMarker<T> extends AbstractSBGNnode<T> implements
SBGNNodeWithCloneMarker<T> {
  
  /**
   * This switch decides if a clone marker should be drawn or not. If
   * {@code null} this property is undefined and then defaults to {@code false}.
   */
  private Boolean cloneMarker = null;
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.director.SBGNNode#isSetCloneMarker()
   */
  @Override
  public boolean hasCloneMarker() {
    return isSetCloneMarker() ? cloneMarker.booleanValue() : false;
  }
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.director.SBGNNode#draw(double, double, double, double, double, double)
   */
  @Override
  public T draw(double x, double y, double z, double width, double height,
    double depth) {
    // TODO Auto-generated method stub
    return null;
  }
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.director.SBGNNodeWithCloneMarker#setCloneMarker(boolean)
   */
  @Override
  public void setCloneMarker(boolean hasCloneMarker) {
    cloneMarker = Boolean.valueOf(hasCloneMarker);
  }
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.director.SBGNNodeWithCloneMarker#isSetCloneMarker()
   */
  @Override
  public boolean isSetCloneMarker() {
    return cloneMarker != null;
  }
  
}
