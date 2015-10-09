/*
 * $Id$
 * $URL$
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

import org.apache.log4j.Logger;
import org.sbml.jsbml.PropertyUndefinedError;

/**
 * @author Alex Thomas
 * @author Andreas Dr&auml;ger
 * @since 1.0
 * @version $Rev$
 */
public class SpatialSymbolReference extends ParameterType {

  
  /**
   * A {@link Logger} for this class.
   */
  private Logger logger = Logger.getLogger(SpatialSymbolReference.class);
  
  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -8906622500258765056L;

  /**
   * 
   */
  private String spatialRef;
  
  /**
   * 
   */
  public SpatialSymbolReference() {
    super();
  }

  /**
   * @param ssr
   */
  public SpatialSymbolReference(SpatialSymbolReference ssr) {
    super(ssr);
    if (ssr.isSetSpatialRef()) {
      setSpatialRef(ssr.getSpatialRef());
    }    
  }

  /**
   * @param level
   * @param version
   */
  public SpatialSymbolReference(int level, int version) {
    super(level, version);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.ParameterType#clone()
   */
  @Override
  public SpatialSymbolReference clone() {
    return new SpatialSymbolReference(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.ParameterType#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    return super.equals(object);
  }

  
  /**
   * Returns the value of {@link #spatialRef}.
   *
   * @return the value of {@link #spatialRef}.
   */
  public String getSpatialRef() {
    //TODO: if variable is boolean, create an additional "isVar"
    //TODO: return primitive data type if possible (e.g., int instead of Integer)
    if (isSetSpatialRef()) {
      return spatialRef;
    }
    // This is necessary if we cannot return null here. For variables of type String return an empty String if no value is defined.
    throw new PropertyUndefinedError(SpatialConstants.spatialRef, this);
  }


  /**
   * Returns whether {@link #spatialRef} is set.
   *
   * @return whether {@link #spatialRef} is set.
   */
  public boolean isSetSpatialRef() {
    return this.spatialRef != null;
  }


  /**
   * Sets the value of spatialRef
   *
   * @param spatialRef the value of spatialRef to be set.
   */
  public void setSpatialRef(String spatialRef) {
    String oldSpatialRef = this.spatialRef;
    this.spatialRef = spatialRef;
    firePropertyChange(SpatialConstants.spatialRef, oldSpatialRef, this.spatialRef);
  }


  /**
   * Unsets the variable spatialRef.
   *
   * @return {@code true} if spatialRef was set before, otherwise {@code false}.
   */
  public boolean unsetSpatialRef() {
    if (isSetSpatialRef()) {
      String oldSpatialRef = this.spatialRef;
      this.spatialRef = null;
      firePropertyChange(SpatialConstants.spatialRef, oldSpatialRef, this.spatialRef);
      return true;
    }
    return false;
  }
  
  
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetSpatialRef()) {
      attributes.remove(SpatialConstants.spatialRef);
      attributes.put(SpatialConstants.shortLabel + ":" + SpatialConstants.spatialRef, getSpatialRef());
    }
    return attributes;
  }


  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
    if (!isAttributeRead) {
      isAttributeRead = true;
      if (attributeName.equals(SpatialConstants.spatialRef)) {
        try {
          setSpatialRef(value);          
        } catch (Exception e) {
          logger.warn(MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.spatialRef, getElementName()));
        }
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }
}
