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
public class BoundaryCondition extends ParameterType {

  
  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(BoundaryCondition.class);
  
  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -6986768113234565819L;

  /**
   * 
   */
  private String boundaryDomainType;

  /**
   * 
   */
  private String coordinateBoundary;

  /**
   * 
   */
  private BoundaryConditionKind type;

  /**
   * 
   */
  public BoundaryCondition() {
    super();
  }

  /**
   * @param sb
   */
  public BoundaryCondition(BoundaryCondition sb) {
    super(sb);

    if (sb.isSetBoundaryDomainType()) {
      boundaryDomainType = new String(sb.getBoundaryDomainType());
    }
    if (sb.isSetCoordinateBoundary()) {
      coordinateBoundary = new String(sb.getCoordinateBoundary());
    }
    if (sb.isSetType()) {
      type = sb.getType();
    }

  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public BoundaryCondition clone() {
    return new BoundaryCondition(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equal = super.equals(object);
    if (equal) {
      BoundaryCondition bc = (BoundaryCondition) object;
      equal &= bc.isSetBoundaryDomainType() == isSetBoundaryDomainType();
      if (equal && isSetBoundaryDomainType()) {
        equal &= bc.getBoundaryDomainType().equals(getBoundaryDomainType());
      }
      equal &= bc.isSetCoordinateBoundary() == isSetCoordinateBoundary();
      if (equal && isSetCoordinateBoundary()) {
        equal &= bc.getCoordinateBoundary().equals(getCoordinateBoundary());
      }
      equal &= bc.isSetType() == isSetType();
      if (equal && isSetType()) {
        equal &= bc.getType().equals(getType());
      }
    }
    return equal;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 971;
    int hashCode = super.hashCode();
    if (isSetBoundaryDomainType()) {
      hashCode += prime * getBoundaryDomainType().hashCode();
    }
    if (isSetCoordinateBoundary()) {
      hashCode += prime * getCoordinateBoundary().hashCode();
    }
    if (isSetType()) {
      hashCode += prime * getType().hashCode();
    }
    return hashCode;
  }


  /**
   * Returns the value of type
   *
   * @return the value of type
   */
  public BoundaryConditionKind getType() {
    if (isSetType()) {
      return type;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.type, this);
  }


  /**
   * Returns whether type is set
   *
   * @return whether type is set
   */
  public boolean isSetType() {
    return type != null;
  }


  /**
   * Sets the value of type
   * @param type
   */
  public void setType(BoundaryConditionKind type) {
    BoundaryConditionKind oldType = this.type;
    this.type = type;
    firePropertyChange(SpatialConstants.type, oldType, this.type);
  }

  /**
   * @param type
   */
  public void setType(String type) {
    setType(BoundaryConditionKind.valueOf(type));
  }

  /**
   * Unsets the variable type
   *
   * @return {@code true}, if type was set before,
   *         otherwise {@code false}
   */
  public boolean unsetType() {
    if (isSetType()) {
      BoundaryConditionKind oldType = type;
      type = null;
      firePropertyChange(SpatialConstants.type, oldType, type);
      return true;
    }
    return false;
  }


  /**
   * Returns the value of coordinateBoundary
   *
   * @return the value of coordinateBoundary
   */
  public String getCoordinateBoundary() {
    if (isSetCoordinateBoundary()) {
      return coordinateBoundary;
    }
    return null;

  }


  /**
   * Returns whether coordinateBoundary is set
   *
   * @return whether coordinateBoundary is set
   */
  public boolean isSetCoordinateBoundary() {
    return coordinateBoundary != null;
  }


  /**
   * Sets the value of coordinateBoundary
   * @param coordinateBoundary
   */
  public void setCoordinateBoundary(String coordinateBoundary) {
    String oldCoordinateBoundary = this.coordinateBoundary;
    this.coordinateBoundary = coordinateBoundary;
    firePropertyChange(SpatialConstants.coordinateBoundary, oldCoordinateBoundary, this.coordinateBoundary);
  }


  /**
   * Unsets the variable coordinateBoundary
   *
   * @return {@code true}, if coordinateBoundary was set before,
   *         otherwise {@code false}
   */
  public boolean unsetCoordinateBoundary() {
    if (isSetCoordinateBoundary()) {
      String oldCoordinateBoundary = coordinateBoundary;
      coordinateBoundary = null;
      firePropertyChange(SpatialConstants.coordinateBoundary, oldCoordinateBoundary, coordinateBoundary);
      return true;
    }
    return false;
  }


  /**
   * Returns the value of boundaryDomainType
   *
   * @return the value of boundaryDomainType
   */
  public String getBoundaryDomainType() {
    if (isSetBoundaryDomainType()) {
      return boundaryDomainType;
    }
    return null;

  }


  /**
   * Returns whether boundaryDomainType is set
   *
   * @return whether boundaryDomainType is set
   */
  public boolean isSetBoundaryDomainType() {
    return boundaryDomainType != null;
  }


  /**
   * Sets the value of boundaryDomainType
   * @param boundaryDomainType
   */
  public void setBoundaryDomainType(String boundaryDomainType) {
    String oldBoundaryDomainType = this.boundaryDomainType;
    this.boundaryDomainType = boundaryDomainType;
    firePropertyChange(SpatialConstants.boundaryDomainType, oldBoundaryDomainType, this.boundaryDomainType);
  }


  /**
   * Unsets the variable boundaryDomainType
   *
   * @return {@code true}, if boundaryDomainType was set before,
   *         otherwise {@code false}
   */
  public boolean unsetBoundaryDomainType() {
    if (isSetBoundaryDomainType()) {
      String oldBoundaryDomainType = boundaryDomainType;
      boundaryDomainType = null;
      firePropertyChange(SpatialConstants.boundaryDomainType, oldBoundaryDomainType, boundaryDomainType);
      return true;
    }
    return false;
  }


  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetType()) {
      attributes.remove("type");
      attributes.put(SpatialConstants.shortLabel + ":type", getType().toString());
    }

    if (isSetCoordinateBoundary()) {
      attributes.remove("coordinateBoundary");
      attributes.put(SpatialConstants.shortLabel + ":coordinateBoundary",
        getCoordinateBoundary());
    }

    if (isSetBoundaryDomainType()) {
      attributes.remove("boundaryDomainType");
      attributes.put(SpatialConstants.shortLabel + ":boundaryDomainType",
        getBoundaryDomainType());
    }
    return attributes;
  }


  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = (super.readAttribute(attributeName, prefix, value))
        && (SpatialConstants.shortLabel == prefix);
    if (!isAttributeRead) {
      isAttributeRead = true;
      if (attributeName.equals(SpatialConstants.type)) {
        try {
          setType(value);
        } catch (Exception e) {
          logger.warn(MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.type, getElementName()));
        }
      }
      else if (attributeName.equals(SpatialConstants.coordinateBoundary)) {
        try {
          setCoordinateBoundary(value);
        } catch (Exception e) {
          logger.warn(MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.coordinateBoundary, getElementName()));
        }
      }

      else if (attributeName.equals(SpatialConstants.boundaryDomainType)) {
        try {
          setBoundaryDomainType(value);
        } catch (Exception e) {
          logger.warn(MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.boundaryDomainType, getElementName()));
        }
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }



}
