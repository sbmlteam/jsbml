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
package org.sbml.jsbml.ext.spatial;

import org.sbml.jsbml.SBase;

/**
 * @author Alex Thomas
 * @since 1.0
 */
public interface SpatialNamedSBase extends SBase {

  /**
   * Unsets the id
   *
   * @return {@code true}, if id was set before, otherwise {@code false}
   */
  public boolean unsetSpatialId();

  /**
   * Sets the value of the id
   * 
   * @param id
   */
  public void setSpatialId(String id);

  /**
   * Returns whether id is set
   *
   * @return whether id is set
   */
  public boolean isSetSpatialId();

  /**
   * Returns the value of the id attribute
   *
   * @return the value of id
   */
  public String getSpatialId();

}
