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

import org.sbml.jsbml.util.StringTools;




/**
 * @author Alex Thomas
 * @author Andreas Dr&auml;ger
 * @version $Rev$
 * @since 1.0
 * @date 09.09.2011
 */
public abstract class Coefficient extends ParameterType {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -7651871640808157489L;

  /**
   * 
   */
  private Integer coordinateIndex;

  /**
   * 
   */
  public Coefficient() {
    super();
  }

  /**
   * @param coefficient
   */
  public Coefficient(Coefficient coefficient) {
    super(coefficient);
    if (coefficient.isSetCoordinateIndex()) {
      coordinateIndex = Integer.valueOf(coefficient.getCoordinateIndex());
    }

  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public abstract Coefficient clone();

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equal = super.equals(object);
    if (equal) {
      Coefficient dc = (Coefficient) object;
      equal &= dc.isSetCoordinateIndex() == isSetCoordinateIndex();
      equal &= dc.getCoordinateIndex() == getCoordinateIndex();
    }
    return equal;
  }

  /**
   * @return the coordinateIndex
   */
  public int getCoordinateIndex() {
    return coordinateIndex.intValue();
  }

  /**
   * @return
   */
  public boolean isSetCoordinateIndex() {
    return coordinateIndex != null;
  }


  /**
   * @param coordinateIndex the coordinateIndex to set
   */
  public void setCoordinateIndex(Integer coordinateIndex) {
    this.coordinateIndex = coordinateIndex;
  }


  @Override
  public int hashCode() {
    final int prime = 983;//Change this prime number
    int hashCode = super.hashCode();
    if (isSetCoordinateIndex()) {
      hashCode += prime * getCoordinateIndex();
    }
    return hashCode;
  }


  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetCoordinateIndex()) {
      attributes.remove("coordinateIndex");
      attributes.put(SpatialConstants.shortLabel + ":coordinateIndex",
        String.valueOf(getCoordinateIndex()));
    }
    return attributes;
  }


  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = (super.readAttribute(attributeName, prefix, value))
        && (SpatialConstants.shortLabel == prefix);
    if (!isAttributeRead) {
      isAttributeRead = true;
      if (attributeName.equals(SpatialConstants.coordinateIndex)) {
        try {
          setCoordinateIndex(StringTools.parseSBMLInt(value));
        } catch (Exception e) {
          MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ"), value,
            SpatialConstants.coordinateIndex);
        }
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }


}
