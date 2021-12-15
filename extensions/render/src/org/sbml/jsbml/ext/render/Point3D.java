/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2022 jointly by the following organizations:
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
package org.sbml.jsbml.ext.render;

/**
 * @author Alexander Diamantikos
 * @author Jakob Matthes
 * @author Eugen Netz
 * @author Jan Rudolph
 * @author David Vetter
 * @since 1.0
 */
public interface Point3D {
  /**
   * @return the value of x
   */
  public abstract RelAbsVector getX();

  /**
   * @return the value of y
   */
  public abstract RelAbsVector getY();

  /**
   * @return the value of z
   */
  public abstract RelAbsVector getZ();

  /**
   * @return whether x is set
   */
  public abstract boolean isSetX();

  /**
   * @return whether y is set
   */
  public abstract boolean isSetY();

  /**
   * @return whether z is set
   */
  public abstract boolean isSetZ();

  /**
   * Set the value of x
   * @param x
   */
  public abstract void setX(RelAbsVector x);

  /**
   * Set the value of y
   * @param y
   */
  public abstract void setY(RelAbsVector y);

  /**
   * Set the value of z
   * @param z
   */
  public abstract void setZ(RelAbsVector z);

  /**
   * Unsets the variable x
   * @return {@code true}, if x was set before,
   *         otherwise {@code false}
   */
  public abstract boolean unsetX();

  /**
   * Unsets the variable y
   * @return {@code true}, if y was set before,
   *         otherwise {@code false}
   */
  public abstract boolean unsetY();

  /**
   * Unsets the variable z
   * @return {@code true}, if z was set before,
   *         otherwise {@code false}
   */
  public abstract boolean unsetZ();

}
