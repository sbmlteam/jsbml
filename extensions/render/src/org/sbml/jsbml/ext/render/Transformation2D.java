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

import java.util.Arrays;

/**
 * @author Eugen Netz
 * @author Alexander Diamantikos
 * @author Jakob Matthes
 * @author Jan Rudolph
 * @since 1.0
 */
public class Transformation2D extends Transformation {
  /**
   * Generated serial version identifier
   */
  private static final long serialVersionUID = -1737694519381619398L;


  /**
   * 
   */
  protected Double[] transform;

  /**
   * Creates an Transformation2D instance
   */
  public Transformation2D() {
    super();
    initDefaults();
  }

  /**
   * @param level
   * @param version
   */
  public Transformation2D(int level, int version) {
    super(level, version);
    initDefaults();
  }

  /**
   * Clone constructor
   * @param obj
   */
  public Transformation2D(Transformation2D obj) {
    super(obj);
    
    if (obj.isSetTransform()) {
      System.arraycopy(obj.getTransform(), 0, transform, 0, obj.getTransform().length);
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Transformation#clone()
   */
  @Override
  public Transformation2D clone() {
    return new Transformation2D(this);
  }

  /**
   * @return the value of transform
   */
  public Double[] getTransform() {
    if (!isSetTransform()) {
      transform = new Double[6];
    }
    return transform;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Transformation#initDefaults()
   */
  @Override
  public void initDefaults() {
    setPackageVersion(-1);
    packageName = RenderConstants.shortLabel;
  }

  /**
   * @return whether transform is set
   */
  public boolean isSetTransform() {
    return transform != null;
  }

  /**
   * Set the value of transform
   * @param transform
   */
  public boolean setTransform(Double[] transform) {
    Double[] oldTransform = this.transform;
    this.transform = transform;
    firePropertyChange(RenderConstants.transform, oldTransform, this.transform);
    return transform != oldTransform;
  }

  /**
   * Unsets the variable transform
   * @return {@code true}, if transform was set before,
   *         otherwise {@code false}
   */
  public boolean unsetTransform() {
    return setTransform(null);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 3191;
    int result = super.hashCode();
    if (isSetTransform()) {
      result = prime * result + Arrays.hashCode(transform);
    }
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!super.equals(obj)) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Transformation2D other = (Transformation2D) obj;
    if (!Arrays.equals(transform, other.transform)) {
      return false;
    }
    return true;
  }

}
