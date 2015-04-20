/*
 * $Id: CoordinateReference.java 2181 2015-04-09 13:44:21Z niko-rodrigue $
 * $URL: svn://svn.code.sf.net/p/jsbml/code/trunk/extensions/spatial/src/org/sbml/jsbml/ext/spatial/CoordinateReference.java $
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
package org.sbml.jsbml.ext.spatial;

import java.text.MessageFormat;
import java.util.Map;

import org.sbml.jsbml.AbstractSBase;




/**
 * @author Alex Thomas
 * @author Andreas Dr&auml;ger
 * @version $Rev: 2181 $
 * @since 1.0
 * @date 09.09.2011
 */
public abstract class CoordinateReference extends AbstractSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -7651871640808157489L;

  /**
   * 
   */
  private CoordinateKind coordinate;

  /**
   * 
   */
  public CoordinateReference() {
    super();
    initDefaults();
  }

  /**
   * @param coefficient
   */
  public CoordinateReference(CoordinateReference coefficient) {
    super(coefficient);
    if (coefficient.isSetCoordinate()) {
      coordinate = coefficient.getCoordinate();
    }

  }

  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    setNamespace(SpatialConstants.namespaceURI); // TODO - removed once the mechanism are in place to set package version and namespace
    setPackageVersion(-1);
    packageName = SpatialConstants.shortLabel;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public abstract CoordinateReference clone();

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equal = super.equals(object);
    if (equal) {
      CoordinateReference dc = (CoordinateReference) object;
      equal &= dc.isSetCoordinate() == isSetCoordinate();
      equal &= dc.getCoordinate() == getCoordinate();
    }
    return equal;
  }

  /**
   * @return the coordinate
   */
  public CoordinateKind getCoordinate() {
    return coordinate;
  }

  /**
   * @return
   */
  public boolean isSetCoordinate() {
    return coordinate != null;
  }


  /**
   * @param coordinate the coordinate to set
   */
  public void setCoordinate(CoordinateKind coordinate) {
    this.coordinate = coordinate;
  }


  @Override
  public int hashCode() {
    final int prime = 983;//Change this prime number
    int hashCode = super.hashCode();
    if (isSetCoordinate()) {
      hashCode += prime * getCoordinate().hashCode();
    }
    return hashCode;
  }


  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetCoordinate()) {
      attributes.remove("coordinate");
      attributes.put(SpatialConstants.shortLabel + ":coordinate",
        String.valueOf(getCoordinate()));
    }
    return attributes;
  }


  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = (super.readAttribute(attributeName, prefix, value))
        && (SpatialConstants.shortLabel == prefix);
    if (!isAttributeRead) {
      isAttributeRead = true;
      if (attributeName.equals(SpatialConstants.coordinate)) {
        try {
          setCoordinate(CoordinateKind.valueOf(value));
        } catch (Exception e) {
          MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ"), value,
            SpatialConstants.coordinate);
        }
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }


}
