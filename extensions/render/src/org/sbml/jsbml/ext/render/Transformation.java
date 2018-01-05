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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.sbml.jsbml.AbstractSBase;

/**
 * @author Eugen Netz
 * @author Alexander Diamantikos
 * @author Jakob Matthes
 * @author Jan Rudolph
 * @since 1.0
 */
public class Transformation extends AbstractSBase {
  /**
   * Generated serial version identifier
   */
  private static final long serialVersionUID = 1845276761656867150L;
  /**
   * 
   */
  protected List<Double> transform;

  /**
   * Creates an Transformation instance
   */
  public Transformation() {
    super();
    initDefaults();
  }

  /**
   * Creates an Transformation instance
   * 
   * @param level the SBML level
   * @param version the SBML version
   */
  public Transformation(int level, int version) {
    super(level, version);
    initDefaults();
  }

  /**
   * Clone constructor
   * 
   * @param obj
   */
  public Transformation(Transformation obj) {
    super(obj);

    if (obj.isSetTransform()) {
      setTransform(new ArrayList<Double>(obj.transform));
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public Transformation clone() {
    return new Transformation(this);
  }

  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    setPackageVersion(-1);
    packageName = RenderConstants.shortLabel;
  }



  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 3191;
    int result = super.hashCode();
    if (isSetTransform()) {
      result = prime * result + transform.hashCode();
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
    Transformation other = (Transformation) obj;
    if ((isSetTransform() != other.isSetTransform()) || (isSetTransform() && !transform.equals(other.transform))) {
      return false;
    }
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetTransform()) {
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.transform,
        XMLTools.encodeArrayDoubleToString(transform.toArray(new Double[0])));
    }
    return attributes;
  }

  /**
   * @return
   */
  private boolean isSetTransform() {
    return transform != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);

    if (!isAttributeRead) {
      isAttributeRead = true;
      // TODO: catch Exception if Enum.valueOf fails, generate logger output
      if (attributeName.equals(RenderConstants.transform)) {
        setTransform(Arrays.asList(XMLTools.decodeStringToArrayDouble(value)));
      }
      else {
        isAttributeRead = false;
      }
    }

    return isAttributeRead;
  }

  /**
   * @param transform
   */
  private boolean setTransform(List<Double> transform) {
    List<Double> oldTransform = this.transform;
    this.transform = transform;
    firePropertyChange(RenderConstants.transform, oldTransform, this.transform);
    return transform != oldTransform;
  }

}
