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
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.util.StringTools;


/**
 * @author Alex Thomas
 * @author Andreas Dr&auml;ger
 * @since 1.0
 */
public class DomainType extends AbstractSpatialNamedSBase {


  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(DomainType.class);

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -1429902642364213170L;

  /**
   * 
   */
  private Integer spatialDimensions;

  // private static final ResourceBundle bundle = ResourceManager.getBundle("org.sbml.jsbml.ext.spatial.Messages");

  /**
   * 
   */
  public DomainType() {
    super();
  }

  /**
   * @param sb
   */
  public DomainType(DomainType sb) {
    super(sb);
    if (sb.isSetSpatialDimensions()) {
      setSpatialDimensions(sb.getSpatialDimensions());
    }
  }

  /**
   * @param level
   * @param version
   */
  public DomainType(int level, int version) {
    super(level, version);
  }

  /**
   * 
   * @param id
   * @param level
   * @param version
   */
  public DomainType(String id, int level, int version) {
    super(id,level,version);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public DomainType clone() {
    return new DomainType(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equal = super.equals(object);
    if (equal) {
      DomainType dt = (DomainType) object;
      equal &= dt.isSetSpatialDimensions() == isSetSpatialDimensions();
      if (equal && isSetSpatialDimensions()) {
        equal &= dt.getSpatialDimensions() == getSpatialDimensions();
      }
    }
    return equal;
  }


  /**
   * Returns the value of spatialDimension
   *
   * @return the value of spatialDimension
   */
  public int getSpatialDimensions() {
    if (isSetSpatialDimensions()) {
      return spatialDimensions;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.spatialDimensions, this);
  }


  /**
   * Returns whether spatialDimension is set
   *
   * @return whether spatialDimension is set
   */
  public boolean isSetSpatialDimensions() {
    return spatialDimensions != null;
  }


  /**
   * Sets the value of spatialDimension
   * @param spatialDimension
   */
  public void setSpatialDimensions(int spatialDimension) {
    Integer oldSpatialDimension = spatialDimensions;
    if (!((spatialDimension >= 0) && (spatialDimension <= 3))) {
      throw new SBMLException("Not a valid spatial dimension. Must be 0, 1, 2, or 3.");
    }
    spatialDimensions = spatialDimension;
    firePropertyChange(SpatialConstants.spatialDimensions, oldSpatialDimension, spatialDimensions);
  }


  /**
   * Unsets the variable spatialDimension
   *
   * @return {@code true}, if spatialDimension was set before,
   *         otherwise {@code false}
   */
  public boolean unsetSpatialDimension() {
    if (isSetSpatialDimensions()) {
      int oldSpatialDimension = spatialDimensions;
      spatialDimensions = null;
      firePropertyChange(SpatialConstants.spatialDimensions, oldSpatialDimension, spatialDimensions);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.AbstractSpatialNamedSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 1489;//Change this prime number
    int hashCode = super.hashCode();
    if (isSetSpatialDimensions()) {
      hashCode += prime * getSpatialDimensions();
    }
    return hashCode;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.AbstractSpatialNamedSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetSpatialDimensions()) {
      attributes.put(SpatialConstants.shortLabel + ":" + SpatialConstants.spatialDimensions, String.valueOf(getSpatialDimensions()));
    }
    return attributes;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.AbstractSpatialNamedSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);

    if (!isAttributeRead) {
      isAttributeRead = true;
      if (attributeName.equals(SpatialConstants.spatialDimensions)) {
        try {
          setSpatialDimensions(StringTools.parseSBMLInt(value));
        } catch (Exception e) {
          logger.warn(MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.spatialDimensions, getElementName()));
          isAttributeRead = false;
        }
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }

}
