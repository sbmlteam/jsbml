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
 * @since 1.0
 */
public class CSGPrimitive extends CSGNode{


  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(CSGPrimitive.class);

  /**
   * 
   */
  private static final long serialVersionUID = -6783804853380306509L;

  /**
   * 
   */
  private PrimitiveKind primitiveType;


  /**
   * 
   */
  public CSGPrimitive() {
    super();
  }


  /**
   * @param csgp
   */
  public CSGPrimitive(CSGPrimitive csgp) {
    super(csgp);
    if (csgp.isSetPrimitiveType()) {
      setPrimitiveType(csgp.getPrimitiveType());
    }
  }


  /**
   * @param level
   * @param version
   */
  public CSGPrimitive(int level, int version) {
    super(level, version);
  }


  /**
   * 
   * @param id
   * @param level
   * @param version
   */
  public CSGPrimitive(String id, int level, int version) {
    super(id, level, version);
  }


  @Override
  public CSGPrimitive clone() {
    return new CSGPrimitive(this);
  }


  @Override
  public boolean equals(Object object) {
    boolean equal = super.equals(object);
    if (equal) {
      CSGPrimitive csgp = (CSGPrimitive) object;
      equal &= csgp.isSetPrimitiveType() == isSetPrimitiveType();
      if (equal && isSetPrimitiveType()) {
        equal &= csgp.getPrimitiveType().equals(getPrimitiveType());
      }
    }
    return equal;
  }


  /**
   * Returns the value of primitiveType
   *
   * @return the value of primitiveType
   */
  public PrimitiveKind getPrimitiveType() {
    if (isSetPrimitiveType()) {
      return primitiveType;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.primitiveType, this);
  }


  /**
   * Returns whether primitiveType is set
   *
   * @return whether primitiveType is set
   */
  public boolean isSetPrimitiveType() {
    return primitiveType != null;
  }


  /**
   * Sets the value of primitiveType
   * @param primitiveType
   */
  public void setPrimitiveType(String primitiveType) {
    setPrimitiveType(PrimitiveKind.valueOf(primitiveType));
  }

  /**
   * Sets the value of primitiveType
   * @param primitiveType
   */
  public void setPrimitiveType(PrimitiveKind primitiveType) {
    PrimitiveKind oldPrimitiveType = this.primitiveType;
    this.primitiveType = primitiveType;
    firePropertyChange(SpatialConstants.primitiveType, oldPrimitiveType, this.primitiveType);
  }

  /**
   * Unsets the variable primitiveType
   *
   * @return {@code true}, if primitiveType was set before,
   *         otherwise {@code false}
   */
  public boolean unsetPrimitiveType() {
    if (isSetPrimitiveType()) {
      PrimitiveKind oldPrimitiveType = primitiveType;
      primitiveType = null;
      firePropertyChange(SpatialConstants.primitiveType, oldPrimitiveType, primitiveType);
      return true;
    }
    return false;
  }


  @Override
  public int hashCode() {
    final int prime = 1081;
    int hashCode = super.hashCode();
    if (isSetPrimitiveType()) {
      hashCode += prime * getPrimitiveType().hashCode();
    }
    return hashCode;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.AbstractSpatialNamedSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetPrimitiveType()) {
      attributes.put(SpatialConstants.shortLabel + ":primitiveType", getPrimitiveType().toString());
    }
    return attributes;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.AbstractSpatialNamedSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = (super.readAttribute(attributeName, prefix, value))
        && (SpatialConstants.shortLabel == prefix);
    if (!isAttributeRead) {
      isAttributeRead = true;
      if (attributeName.equals(SpatialConstants.primitiveType)) {
        try {
          setPrimitiveType(value);
        } catch (Exception e) {
          logger.warn(MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.primitiveType, getElementName()));
        }
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getElementName()
   */
  @Override
  public String getElementName() {
    return SpatialConstants.csgPrimitive;
  }

}
