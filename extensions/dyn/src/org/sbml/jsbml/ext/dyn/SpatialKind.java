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
 * 6. Boston University, Boston, MA, USA
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.dyn;

/**
 * This is a collection of possible values for the {@code spatialIndex}
 * attribute within a {@link SpatialComponent}. Supported values this type are
 * bound to a Cartesian coordinate system. This has been defined in version 1 of
 * the Dynamic Structures specification.
 * 
 * @author Harold G&oacute;mez
 * @since 1.0
 */
public enum SpatialKind {
  /**
   * Refers to the X coordinate component of an object's position within a
   * Cartesian coordinate system
   */
  cartesianX,
  /**
   * Refers to the Y coordinate component of an object's position within a
   * Cartesian coordinate system
   */
  cartesianY,
  /**
   * Refers to the Z coordinate component of an object's position within a
   * Cartesian coordinate system
   */
  cartesianZ,
  /**
   * Refers to elemental rotation of an object about the X coordinate axis
   */
  alpha,
  /**
   * Refers to elemental rotation of an object about the Y coordinate axis
   */
  beta,
  /**
   * Refers to elemental rotation of an object about the Z coordinate axis
   */
  gamma,
  /**
   * Refers to the X component of the force vector that drives movement
   */
  F_x,
  /**
   * Refers to the Y component of the force vector that drives movement
   */
  F_y,
  /**
   * Refers to the Z component of the force vector that drives movement
   */
  F_z,
}
