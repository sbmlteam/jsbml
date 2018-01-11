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

import java.text.MessageFormat;
import java.util.Map;

import org.apache.log4j.Logger;
import org.sbml.jsbml.PropertyUndefinedError;


/**
 * @author Alex Thomas
 * @author Andreas Dr&auml;ger
 * @since 1.0
 */
public class AdvectionCoefficient extends ParameterType {

  
  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(AdvectionCoefficient.class);
  
  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 8982184068116596444L;
  
  /**
   * 
   */
  private CoordinateKind coordinate;

  /**
   * 
   */
  public AdvectionCoefficient() {
    super();
  }


  /**
   * @param advCoeff
   */
  public AdvectionCoefficient(AdvectionCoefficient advCoeff) {
    super(advCoeff);
    if (advCoeff.isSetCoordinate()) {
      coordinate = advCoeff.getCoordinate();
    }
  }


  /**
   * @param level
   * @param version
   */
  public AdvectionCoefficient(int level, int version) {
    super(level, version);
  }


  @Override
  public AdvectionCoefficient clone() {
    return new AdvectionCoefficient(this);
  }


  @Override
  public boolean equals(Object object) {
    boolean equal = super.equals(object);
    if (equal) {
      AdvectionCoefficient advCoeff = (AdvectionCoefficient) object;
      equal &= advCoeff.isSetCoordinate() == isSetCoordinate();
      if (equal && isSetCoordinate()) {
        equal &= advCoeff.getCoordinate().equals(getCoordinate());
      }
    }
    return equal;
  }


  /**
   * Returns the value of coordinate
   *
   * @return the value of coordinate
   */
  public CoordinateKind getCoordinate() {
    if (isSetCoordinate()) {
      return coordinate;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.coordinate, this);
  }


  /**
   * Returns whether coordinate is set
   *
   * @return whether coordinate is set
   */
  public boolean isSetCoordinate() {
    return coordinate != null;
  }


  /**
   * Sets the value of coordinate
   * @param coordinate
   */
  public void setCoordinate(CoordinateKind coordinate) {
    CoordinateKind oldCoordinate = this.coordinate;
    this.coordinate = coordinate;
    firePropertyChange(SpatialConstants.coordinate, oldCoordinate, this.coordinate);
  }


  /**
   * Unsets the variable coordinate
   *
   * @return {@code true}, if coordinate was set before,
   *         otherwise {@code false}
   */
  public boolean unsetCoordinate() {
    if (isSetCoordinate()) {
      CoordinateKind oldCoordinate = coordinate;
      coordinate = null;
      firePropertyChange(SpatialConstants.coordinate, oldCoordinate, coordinate);
      return true;
    }
    return false;
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
      attributes.put(SpatialConstants.shortLabel + ":coordinate", getCoordinate().toString());
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
          logger.warn(MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.coordinate, getElementName()));
        }
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }


}
