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
public class CSGPseudoPrimitive extends CSGNode{


  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(CSGPseudoPrimitive.class);

  /**
   * 
   */
  private static final long serialVersionUID = 303742063326104808L;

  /**
   * 
   */
  private String csgObjectRef;


  /**
   * 
   */
  public CSGPseudoPrimitive() {
    super();
  }

  /**
   * @param csgp
   */
  public CSGPseudoPrimitive(CSGPseudoPrimitive csgp) {
    super(csgp);
    if (csgp.isSetCsgObjectRef()) {
      setCsgObjectRef(csgp.getCsgObjectRef());
    }
  }


  /**
   * @param level
   * @param version
   */
  public CSGPseudoPrimitive(int level, int version) {
    super(level, version);
  }


  /**
   * 
   * @param id
   * @param level
   * @param version
   */
  public CSGPseudoPrimitive(String id, int level, int version) {
    super(id, level, version);
  }


  @Override
  public CSGPseudoPrimitive clone() {
    return new CSGPseudoPrimitive(this);
  }


  @Override
  public boolean equals(Object object) {
    boolean equal = super.equals(object);
    if (equal) {
      CSGPseudoPrimitive csgp = (CSGPseudoPrimitive) object;
      equal &= csgp.isSetCsgObjectRef() == isSetCsgObjectRef();
      if (equal && isSetCsgObjectRef()) {
        equal &= csgp.getCsgObjectRef().equals(getCsgObjectRef());
      }
    }
    return equal;
  }


  /**
   * Returns the value of csgObjectRef
   *
   * @return the value of csgObjectRef
   */
  public String getCsgObjectRef() {
    if (isSetCsgObjectRef()) {
      return csgObjectRef;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.primitiveType, this);
  }


  /**
   * Returns whether csgObjectRef is set
   *
   * @return whether csgObjectRef is set
   */
  public boolean isSetCsgObjectRef() {
    return csgObjectRef != null;
  }


  /**
   * Sets the value of csgObjectRef
   * @param csgObjectRef
   */
  public void setCsgObjectRef(String csgObjectRef) {
    String oldCsgObjectRef = this.csgObjectRef;
    this.csgObjectRef = csgObjectRef;
    firePropertyChange(SpatialConstants.primitiveType, oldCsgObjectRef, this.csgObjectRef);
  }


  /**
   * Unsets the variable csgObjectRef
   *
   * @return {@code true}, if csgObjectRef was set before,
   *         otherwise {@code false}
   */
  public boolean unsetCsgObjectRef() {
    if (isSetCsgObjectRef()) {
      String oldCsgObjectRef = csgObjectRef;
      csgObjectRef = null;
      firePropertyChange(SpatialConstants.primitiveType, oldCsgObjectRef, csgObjectRef);
      return true;
    }
    return false;
  }


  @Override
  public int hashCode() {
    final int prime = 1741;
    int hashCode = super.hashCode();
    if (isSetCsgObjectRef()) {
      hashCode += prime * getCsgObjectRef().hashCode();
    }
    return hashCode;
  }


  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetCsgObjectRef()) {
      attributes.remove("csgObjectRef");
      attributes.put(SpatialConstants.shortLabel + ":csgObjectRef", getCsgObjectRef());
    }
    return attributes;
  }


  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = (super.readAttribute(attributeName, prefix, value))
        && (SpatialConstants.shortLabel == prefix);
    if (!isAttributeRead) {
      isAttributeRead = true;
      if (attributeName.equals(SpatialConstants.primitiveType)) {
        try {
          setCsgObjectRef(value);
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
    return SpatialConstants.csgPseudoPrimitive;
  }

}
