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
import java.util.Arrays;
import java.util.Map;
import java.util.StringTokenizer;

import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.util.TreeNodeChangeEvent;

/**
 * @author Alex Thomas
 * @since 1.0
 * @deprecated
 */
@Deprecated
public class PolygonObject extends AbstractSBase {

  /**
   * 
   */
  private static final long serialVersionUID = -4878021358939009394L;
  /**
   * 
   */
  private Integer[] pointIndex;

  /**
   * 
   */
  public PolygonObject() {
    super();
    initDefaults();
  }

  /**
   * @param po
   */
  public PolygonObject(PolygonObject po) {
    super(po);
    if (po.isSetPointIndex()) {
      setPointIndex(po.getPointIndex().clone());
    }
  }


  /**
   * @param level
   * @param version
   */
  public PolygonObject(int level, int version) {
    super(level, version);
    initDefaults();
  }


  @Override
  public PolygonObject clone() {
    return new PolygonObject(this);
  }

  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    setPackageVersion(-1);
    packageName = SpatialConstants.shortLabel;
  }


  @Override
  public boolean equals(Object object) {
    boolean equal = super.equals(object);
    if (equal) {
      PolygonObject po = (PolygonObject) object;

      equal &= po.isSetPointIndex() == isSetPointIndex();
      if (equal && isSetPointIndex()) {
        equal &= po.getPointIndex().equals(getPointIndex());
      }
    }
    return equal;
  }


  /**
   * Returns the value of pointIndex
   *
   * @return the value of pointIndex
   */
  public Integer[] getPointIndex() {
    if (isSetPointIndex()) {
      return pointIndex;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.pointIndex, this);
  }


  /**
   * Returns whether pointIndex is set
   *
   * @return whether pointIndex is set
   */
  public boolean isSetPointIndex() {
    return pointIndex != null;
  }

  /**
   * Sets the value of pointIndex
   * @param pointIndex
   */
  public void setPointIndex(Integer... pointIndex) {
    Integer[] oldPointIndex = this.pointIndex;
    this.pointIndex = pointIndex;
    firePropertyChange(SpatialConstants.pointIndex, oldPointIndex, this.pointIndex);
  }

  /**
   * Unsets the variable pointIndex
   *
   * @return {@code true}, if pointIndex was set before,
   *         otherwise {@code false}
   */
  public boolean unsetPointIndex() {
    if (isSetPointIndex()) {
      Integer[] oldPointIndex = pointIndex;
      pointIndex = null;
      firePropertyChange(SpatialConstants.pointIndex, oldPointIndex, pointIndex);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 983;//Change this prime number
    int hashCode = super.hashCode();
    if (isSetPointIndex()) {
      hashCode += prime * getPointIndex().hashCode();
    }
    return hashCode;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetPointIndex()) {
      attributes.remove("pointIndex");
      attributes.put(SpatialConstants.shortLabel + ":pointIndex", Arrays.toString(getPointIndex()));
    }
    if (isSetSBOTerm()) {
      attributes.remove(TreeNodeChangeEvent.sboTerm);
      attributes.put(SpatialConstants.shortLabel + ":" + TreeNodeChangeEvent.sboTerm, getSBOTermID());
    }
    if (isSetMetaId()) {
      attributes.remove(TreeNodeChangeEvent.metaId);
      attributes.put(SpatialConstants.shortLabel + ":" + TreeNodeChangeEvent.metaId, getMetaId());
    }
    return attributes;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = (super.readAttribute(attributeName, prefix, value))
        && (SpatialConstants.shortLabel == prefix);
    if (!isAttributeRead) {
      isAttributeRead = true;
      if (attributeName.equals(SpatialConstants.pointIndex)) {
        StringTokenizer test = new StringTokenizer(value);
        Integer[] pointIndexTemp = new Integer[test.countTokens()];
        int i = 0;
        while(test.hasMoreTokens()) {
          try {
            pointIndexTemp[i] = StringTools.parseSBMLInt(test.nextToken());
            i++;
          } catch (Exception e) {
            MessageFormat.format(
              SpatialConstants.bundle.getString("COULD_NOT_READ"), value,
              SpatialConstants.pointIndex);
          }
        }
        if (pointIndexTemp.length > 0) {
          unsetPointIndex();
          setPointIndex(pointIndexTemp);
        }
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }

}
