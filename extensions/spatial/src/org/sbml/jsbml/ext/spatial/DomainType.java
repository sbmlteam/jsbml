/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2014 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
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
import java.util.ResourceBundle;

import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.util.StringTools;

import de.zbit.util.ResourceManager;


/**
 * @author Alex Thomas
 * @author Andreas Dr&auml;ger
 * @since 1.0
 * @version $Rev$
 */
public class DomainType extends AbstractSpatialNamedSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -1429902642364213170L;

  /**
   * 
   */
  private Integer spatialDimension;

  private static final ResourceBundle bundle = ResourceManager.getBundle("org.sbml.jsbml.ext.spatial.Messages");

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
    if (sb.isSetSpatialDimension()) {
      spatialDimension = Integer.valueOf(sb.getSpatialDimension());
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
      equal &= dt.isSetSpatialDimension() == isSetSpatialDimension();
      if (equal && isSetSpatialDimension()) {
        equal &= dt.getSpatialDimension() == getSpatialDimension();
      }
    }
    return equal;
  }


  /**
   * Returns the value of spatialDimension
   *
   * @return the value of spatialDimension
   */
  public int getSpatialDimension() {
    if (isSetSpatialDimension()) {
      return spatialDimension;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.spatialDimension, this);
  }


  /**
   * Returns whether spatialDimension is set
   *
   * @return whether spatialDimension is set
   */
  public boolean isSetSpatialDimension() {
    return spatialDimension != null;
  }


  /**
   * Sets the value of spatialDimension
   */
  public void setSpatialDimension(int spatialDimension) {
    int oldSpatialDimension = this.spatialDimension;
    if (!((spatialDimension >= 0) && (spatialDimension <= 3))) {
      throw new SBMLException("Not a valid spatial dimension. Must be 0, 1, 2, or 3.");
    }
    this.spatialDimension = spatialDimension;
    firePropertyChange(SpatialConstants.spatialDimension, oldSpatialDimension, this.spatialDimension);
  }


  /**
   * Unsets the variable spatialDimension
   *
   * @return {@code true}, if spatialDimension was set before,
   *         otherwise {@code false}
   */
  public boolean unsetSpatialDimension() {
    if (isSetSpatialDimension()) {
      int oldSpatialDimension = spatialDimension;
      spatialDimension = null;
      firePropertyChange(SpatialConstants.spatialDimension, oldSpatialDimension, spatialDimension);
      return true;
    }
    return false;
  }


  @Override
  public int hashCode() {
    final int prime = 1489;//Change this prime number
    int hashCode = super.hashCode();
    if (isSetSpatialDimension()) {
      hashCode += prime * getSpatialDimension();
    }
    return hashCode;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.AbstractSpatialNamedSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetSpatialDimension()) {
      attributes.remove("spatialDimension");
      attributes.put(SpatialConstants.shortLabel + ":spatialDimension", String.valueOf(getSpatialDimension()));
    }
    return attributes;
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.AbstractSpatialNamedSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = (super.readAttribute(attributeName, prefix, value))
        && (SpatialConstants.shortLabel == prefix);
    if (!isAttributeRead) {
      isAttributeRead = true;
      if (attributeName.equals(SpatialConstants.spatialDimension)) {
        try {
          setSpatialDimension(StringTools.parseSBMLInt(value));
        } catch (Exception e) {
          MessageFormat.format(bundle.getString("COULD_NOT_READ"), value,
            SpatialConstants.spatialDimension);
        }
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("DomainType [spatialDimension=");
    builder.append(spatialDimension);
    builder.append("]");
    return builder.toString();
  }



}
