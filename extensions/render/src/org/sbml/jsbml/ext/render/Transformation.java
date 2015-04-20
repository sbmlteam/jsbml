/*
 * $Id: Transformation.java 2180 2015-04-08 15:48:28Z niko-rodrigue $
 * $URL: svn://svn.code.sf.net/p/jsbml/code/trunk/extensions/render/src/org/sbml/jsbml/ext/render/Transformation.java $
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

import java.util.Arrays;
import java.util.Map;

import org.sbml.jsbml.AbstractSBase;

/**
 * @author Eugen Netz
 * @author Alexander Diamantikos
 * @author Jakob Matthes
 * @author Jan Rudolph
 * @version $Rev: 2180 $
 * @since 1.0
 * @date 08.05.2012
 */
public class Transformation extends AbstractSBase {
  /**
   * Generated serial version identifier
   */
  private static final long serialVersionUID = 1845276761656867150L;
  /**
   * 
   */
  protected Double[] transform; // TODO - why using the Double class instance of the double primitive !?

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
   * @param obj
   */
  public Transformation(Transformation obj) {
    super(obj);
    transform = obj.transform; // TODO - do a proper copy of the array
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
    setNamespace(RenderConstants.namespaceURI); // TODO - removed once the mechanism are in place to set package version and namespace
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
    result = prime * result + Arrays.hashCode(transform);
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
    if (!Arrays.equals(transform, other.transform)) {
      return false;
    }
    return true;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "Transformation [transform=" + Arrays.toString(transform) + "]";
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetTransform()) {
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.transform,
        XMLTools.encodeArrayDoubleToString(transform));
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
        setTransform(XMLTools.decodeStringToArrayDouble(value));
      }
    }
    return isAttributeRead;
  }

  /**
   * @param transform
   */
  private void setTransform(Double[] transform) {
    Double[] oldTransform = this.transform;
    this.transform = transform;
    firePropertyChange(RenderConstants.transform, oldTransform, this.transform);
  }

}
