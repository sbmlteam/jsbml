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
public interface SBGNNodeWithCloneMarker<T> extends SBGNNode<T> {
  
  /**
   * Method to set the boolean clone marker value if the clone marker should be
   * drawn.
   * 
   * @param hasCloneMarker
   *        if {@code true}, the glyph of the species should be drawn with a
   *        clone marker
   */
  public void setCloneMarker(boolean hasCloneMarker);
  
  /**
   * @return boolean value of the clone marker variable
   */
  public boolean hasCloneMarker();
  
  /**
   * @return {@code true} if the clone marker property has been defined for this
   *         node, {@code false} if this variable is not set.
   */
  public boolean isSetCloneMarker();
  
}
