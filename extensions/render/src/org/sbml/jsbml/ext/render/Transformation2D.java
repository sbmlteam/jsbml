/*
 * $Id: Transformation2D.java 2180 2015-04-08 15:48:28Z niko-rodrigue $
 * $URL: svn://svn.code.sf.net/p/jsbml/code/trunk/extensions/render/src/org/sbml/jsbml/ext/render/Transformation2D.java $
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2015 jointly by the following organizations:
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

import org.sbml.jsbml.PropertyUndefinedError;

/**
 * @author Eugen Netz
 * @author Alexander Diamantikos
 * @author Jakob Matthes
 * @author Jan Rudolph
 * @version $Rev: 2180 $
 * @since 1.0
 * @date 08.05.2012
 */
public class Transformation2D extends Transformation {
  /**
   * Generated serial version identifier
   */
  private static final long serialVersionUID = -1737694519381619398L;


  /**
   * 
   */
  protected Double[] transform = new Double[6];

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
    transform = obj.transform; // TODO - do a copy of the array !!
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
    if (isSetTransform()) {
      return transform;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.transform, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Transformation#initDefaults()
   */
  @Override
  public void initDefaults() {
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
  public void setTransform(Double[] transform) {
    Double[] oldTransform = this.transform;
    this.transform = transform;
    firePropertyChange(RenderConstants.transform, oldTransform, this.transform);
  }

  /**
   * Unsets the variable transform
   * @return {@code true}, if transform was set before,
   *         otherwise {@code false}
   */
  public boolean unsetTransform() {
    if (isSetTransform()) {
      Double[] oldTransform = transform;
      transform = null;
      firePropertyChange(RenderConstants.transform, oldTransform, transform);
      return true;
    }
    return false;
  }
}
