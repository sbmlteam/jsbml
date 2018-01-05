/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2018 jointly by the following organizations:
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
package org.sbml.jsbml.ext.layout;

/**
 * Defines classes that are inside a {@link BoundingBox}, which
 * specifies the position and the size of the object.
 * 
 * @author rodrigue
 * @since 1.2
 */
public interface IBoundingBox {

  /**
   * Creates and sets a {@link BoundingBox} for this object.
   * 
   * @return {@link BoundingBox}.
   */
  public BoundingBox createBoundingBox();

  /**
   * Returns the {@link BoundingBox}.
   * 
   * @return the {@link BoundingBox}.
   */
  public BoundingBox getBoundingBox();

  /**
   * Returns {@code true} is the {@link BoundingBox} is set.
   * 
   * @return if the {@link BoundingBox} is set.
   */
  public boolean isSetBoundingBox();

  /**
   * Sets the {@link BoundingBox}.
   * 
   * @param boundingBox the {@link BoundingBox} to set.
   */
  public void setBoundingBox(BoundingBox boundingBox);

  /**
   * Unsets the {@link BoundingBox}.
   */
  public void unsetBoundingBox();

}