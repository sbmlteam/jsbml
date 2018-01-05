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
package org.sbml.jsbml.ext.render;

/**
 * @author Alexander Diamantikos
 * @author Jakob Matthes
 * @author Eugen Netz
 * @author Jan Rudolph
 * @since 1.0
 */
public interface Point3D {

  /**
   * @return the value of absoluteY
   */
  public abstract boolean isAbsoluteY();

  /**
   * @return the value of absoluteZ
   */
  public abstract boolean isAbsoluteZ();

  /**
   * @return the value of x
   */
  public abstract double getX();

  /**
   * @return the value of y
   */
  public abstract double getY();

  /**
   * @return the value of z
   */
  public abstract double getZ();

  /**
   * @return the value of absoluteX
   */
  public abstract boolean isAbsoluteX();

  /**
   * @return whether absoluteX is set
   */
  public abstract boolean isSetAbsoluteX();

  /**
   * @return whether absoluteY is set
   */
  public abstract boolean isSetAbsoluteY();

  /**
   * @return whether absoluteZ is set
   */
  public abstract boolean isSetAbsoluteZ();

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
   * Set the value of absoluteX
   * @param absoluteX
   */
  public abstract void setAbsoluteX(boolean absoluteX);

  /**
   * Set the value of absoluteY
   * @param absoluteY
   */
  public abstract void setAbsoluteY(boolean absoluteY);

  /**
   * Set the value of absoluteZ
   * @param absoluteZ
   */
  public abstract void setAbsoluteZ(boolean absoluteZ);

  /**
   * Set the value of x
   * @param x
   */
  public abstract void setX(double x);

  /**
   * Set the value of y
   * @param y
   */
  public abstract void setY(double y);

  /**
   * Set the value of z
   * @param z
   */
  public abstract void setZ(double z);

  /**
   * Unsets the variable absoluteX
   * @return {@code true}, if absoluteX was set before,
   *         otherwise {@code false}
   */
  public abstract boolean unsetAbsoluteX();

  /**
   * Unsets the variable absoluteY
   * @return {@code true}, if absoluteY was set before,
   *         otherwise {@code false}
   */
  public abstract boolean unsetAbsoluteY();

  /**
   * Unsets the variable absoluteZ
   * @return {@code true}, if absoluteZ was set before,
   *         otherwise {@code false}
   */
  public abstract boolean unsetAbsoluteZ();

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
