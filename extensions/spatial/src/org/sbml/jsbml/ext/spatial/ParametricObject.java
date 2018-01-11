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
import org.sbml.jsbml.util.StringTools;


/**
 * @author Alex Thomas
 * @author Andreas Dr&auml;ger
 * @author Piero Dalle Pezze
 * @since 1.0
 */
public class ParametricObject extends AbstractSpatialNamedSBase {

  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(ParametricObject.class);

  /**
   * 
   */
  private PolygonKind polygonType;
  /**
   * 
   */
  private String domainType;
  /**
   * 
   */
  private CompressionKind compression;
  /**
   * 
   */
  private String pointIndex;
  /**
   * 
   */
  private Integer pointIndexLength;
  /**
   * 
   */
  private DataKind dataType;


  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -8093810090391511545L;

  /**
   * 
   */
  public ParametricObject() {
    super();
  }

  /**
   * @param po
   */
  public ParametricObject(ParametricObject po) {
    super(po);
    if (po.isSetDomainType()) {
      setDomainType(po.getDomainType());
    }
    if (po.isSetPolygonType()) {
      setPolygonType(po.getPolygonType());
    }
    if (po.isSetCompression()) {
      setCompression(po.getCompression());
    }
    if (po.isSetPointIndex()) {
      setPointIndex(po.getPointIndex());
    }
    if (po.isSetPointIndexLength()) {
      setPointIndexLength(po.getPointIndexLength());
    }
    if (po.isSetDataType()) {
      setDataType(po.getDataType());
    }
  }


  /**
   * @param level
   * @param version
   */
  public ParametricObject(int level, int version) {
    super(level, version);
  }


  /**
   * 
   * @param id
   * @param level
   * @param version
   */
  public ParametricObject(String id, int level, int version) {
    super(id, level, version);
  }


  @Override
  public ParametricObject clone() {
    return new ParametricObject(this);
  }


  @Override
  public boolean equals(Object object) {
    boolean equal = super.equals(object);
    if (equal) {
      ParametricObject po = (ParametricObject) object;

      equal &= po.isSetDomainType() == isSetDomainType();
      if (equal && isSetDomainType()) {
        equal &= po.getDomainType().equals(getDomainType());
      }
      equal &= po.isSetPolygonType() == isSetPolygonType();
      if (equal && isSetPolygonType()) {
        equal &= po.getPolygonType().equals(getPolygonType());
      }
      equal &= po.isSetCompression() == isSetCompression();
      if (equal && isSetCompression()) {
        equal &= po.getCompression().equals(getCompression());
      }
      equal &= po.isSetPointIndex() == isSetPointIndex();
      if (equal && isSetPointIndex()) {
        equal &= po.getPointIndex() == getPointIndex();
      }
      equal &= po.isSetPointIndexLength() == isSetPointIndexLength();
      if (equal && isSetPointIndexLength()) {
        equal &= po.getPointIndexLength() == getPointIndexLength();
      }
      equal &= po.isSetDataType() == isSetDataType();
      if (equal && isSetDataType()) {
        equal &= po.getDataType().equals(getDataType());
      }
    }
    return equal;
  }


  /**
   * Returns the value of polygonType
   *
   * @return the value of polygonType
   */
  public PolygonKind getPolygonType() {
    if (isSetPolygonType()) {
      return polygonType;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.polygonType, this);
  }


  /**
   * Returns whether polygonType is set
   *
   * @return whether polygonType is set
   */
  public boolean isSetPolygonType() {
    return polygonType != null;
  }


  /**
   * Sets the value of polygonType
   * @param polygonType
   */
  public void setPolygonType(String polygonType) {
    setPolygonType(PolygonKind.valueOf(polygonType));
  }

  /**
   * Sets the value of polygonType
   * @param polygonType
   */
  public void setPolygonType(PolygonKind polygonType) {
    PolygonKind oldPolygonType = this.polygonType;
    this.polygonType = polygonType;
    firePropertyChange(SpatialConstants.polygonType, oldPolygonType, this.polygonType);
  }


  /**
   * Unsets the variable polygonType
   *
   * @return {@code true}, if polygonType was set before,
   *         otherwise {@code false}
   */
  public boolean unsetPolygonType() {
    if (isSetPolygonType()) {
      PolygonKind oldPolygonType = polygonType;
      polygonType = null;
      firePropertyChange(SpatialConstants.polygonType, oldPolygonType, polygonType);
      return true;
    }
    return false;
  }

  /**
   * Returns the value of domain
   *
   * @return the value of domain
   */
  public String getDomainType() {
    if (isSetDomainType()) {
      return domainType;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.domain, this);
  }


  /**
   * Returns whether domain is set
   *
   * @return whether domain is set
   */
  public boolean isSetDomainType() {
    return domainType != null;
  }


  /**
   * Sets the value of domain
   * @param domain
   */
  public void setDomainType(String domain) {
    String oldDomain = domainType;
    domainType = domain;
    firePropertyChange(SpatialConstants.domain, oldDomain, domainType);
  }


  /**
   * Unsets the variable domain
   *
   * @return {@code true}, if domain was set before,
   *         otherwise {@code false}
   */
  public boolean unsetDomain() {
    if (isSetDomainType()) {
      String oldDomain = domainType;
      domainType = null;
      firePropertyChange(SpatialConstants.domain, oldDomain, domainType);
      return true;
    }
    return false;
  }



  /**
   * Returns the value of compression.
   *
   * @return the value of compression.
   */
  public CompressionKind getCompression() {
    if (isSetCompression()) {
      return compression;
    }
    // This is necessary if we cannot return null here. For variables of type String return an empty String if no value is defined.
    throw new PropertyUndefinedError(SpatialConstants.compression, this);
  }


  /**
   * Returns whether compression is set.
   *
   * @return whether compression is set.
   */
  public boolean isSetCompression() {
    return compression != null;
  }

  /**
   * Sets the value of compression
   * @param compression
   */
  public void setCompression(String compression) {
    setCompression(CompressionKind.valueOf(compression));
  }

  /**
   * Sets the value of compression
   *
   * @param compression the value of compression to be set.
   */
  public void setCompression(CompressionKind compression) {
    CompressionKind oldCompression = this.compression;
    this.compression = compression;
    firePropertyChange(SpatialConstants.compression, oldCompression, this.compression);
  }


  /**
   * Unsets the variable compression.
   *
   * @return {@code true} if compression was set before, otherwise {@code false}.
   */
  public boolean unsetCompression() {
    if (isSetCompression()) {
      CompressionKind oldCompression = compression;
      compression = null;
      firePropertyChange(SpatialConstants.compression, oldCompression, compression);
      return true;
    }
    return false;
  }


  /**
   * Returns the value of {@link #pointIndex}.
   *
   * @return the value of {@link #pointIndex}.
   */
  public String getPointIndex() {
    if (isSetPointIndex()) {
      return pointIndex;
    }
    // This is necessary if we cannot return null here. For variables of type String return an empty String if no value is defined.
    throw new PropertyUndefinedError(SpatialConstants.pointIndex, this);
  }


  /**
   * Returns whether {@link #pointIndex} is set.
   *
   * @return whether {@link #pointIndex} is set.
   */
  public boolean isSetPointIndex() {
    return pointIndex != null;
  }


  /**
   * Sets the value of pointIndex
   *
   * @param pointIndex the value of pointIndex to be set.
   */
  public void setPointIndex(String pointIndex) {
    String oldPointIndex = this.pointIndex;
    this.pointIndex = pointIndex;
    firePropertyChange(SpatialConstants.pointIndex, oldPointIndex, this.pointIndex);
  }


  /**
   * Unsets the variable pointIndex.
   *
   * @return {@code true} if pointIndex was set before, otherwise {@code false}.
   */
  public boolean unsetPointIndex() {
    if (isSetPointIndex()) {
      String oldPointIndex = pointIndex;
      pointIndex = null;
      pointIndexLength = null;
      firePropertyChange(SpatialConstants.pointIndex, oldPointIndex, pointIndex);
      return true;
    }
    return false;
  }

  /**
   * Appends the variable data to pointIndex.
   * @return {@code true} if data was appended to pointIndex, otherwise {@code false}.
   */
  public boolean append(String data) {
    if (data == null) { return false; }
    if (isSetPointIndex()) {
      String oldPointIndex = pointIndex;
      pointIndex = pointIndex + data;
      firePropertyChange(SpatialConstants.pointIndex, oldPointIndex, pointIndex);
    } else {
      setPointIndex(data);
    }
    return true;
  }

  /**
   * Returns the value of pointIndexLength.
   *
   * @return the value of pointIndexLength.
   */
  public int getPointIndexLength() {
    if (isSetPointIndexLength()) {
      return pointIndexLength;
    }
    // This is necessary if we cannot return null here. For variables of type String return an empty String if no value is defined.
    throw new PropertyUndefinedError(SpatialConstants.pointIndexLength, this);
  }


  /**
   * Returns whether pointIndexLength is set.
   *
   * @return whether pointIndexLength is set.
   */
  public boolean isSetPointIndexLength() {
    return pointIndexLength != null;
  }


  /**
   * Sets the value of pointIndexLength
   *
   * @param pointIndexLength the value of pointIndexLength to be set.
   */
  public void setPointIndexLength(int pointIndexLength) {
    Integer oldPointIndexLength = this.pointIndexLength;
    this.pointIndexLength = pointIndexLength;
    firePropertyChange(SpatialConstants.pointIndexLength, oldPointIndexLength, this.pointIndexLength);
  }


  /**
   * Unsets the variable pointIndexLength.
   *
   * @return {@code true} if pointIndexLength was set before, otherwise {@code false}.
   */
  public boolean unsetPointIndexLength() {
    if (isSetPointIndexLength()) {
      Integer oldPointIndexLength = pointIndexLength;
      pointIndexLength = null;
      firePropertyChange(SpatialConstants.pointIndexLength, oldPointIndexLength, pointIndexLength);
      return true;
    }
    return false;
  }


  /**
   * Returns the value of dataType.
   *
   * @return the value of dataType.
   */
  public DataKind getDataType() {
    if (isSetDataType()) {
      return dataType;
    }
    // This is necessary if we cannot return null here. For variables of type String return an empty String if no value is defined.
    throw new PropertyUndefinedError(SpatialConstants.dataType, this);
  }


  /**
   * Returns whether dataType is set.
   *
   * @return whether dataType is set.
   */
  public boolean isSetDataType() {
    return dataType != null;
  }

  /**
   * Sets the value of dataType
   * @param dataType
   */
  public void setDataType(String dataType) {
    setDataType(DataKind.valueOf(dataType));
  }

  /**
   * Sets the value of dataType
   *
   * @param dataType the value of dataType to be set.
   */
  public void setDataType(DataKind dataType) {
    DataKind oldDataType = this.dataType;
    this.dataType = dataType;
    firePropertyChange(SpatialConstants.dataType, oldDataType, this.dataType);
  }


  /**
   * Unsets the variable dataType.
   *
   * @return {@code true} if dataType was set before, otherwise {@code false}.
   */
  public boolean unsetDataType() {
    if (isSetDataType()) {
      DataKind oldDataType = dataType;
      dataType = null;
      firePropertyChange(SpatialConstants.dataType, oldDataType, dataType);
      return true;
    }
    return false;
  }



  @Override
  public int hashCode() {
    final int prime = 2003;
    int hashCode = super.hashCode();
    if (isSetDomainType()) {
      hashCode += prime * getDomainType().hashCode();
    }
    if (isSetPolygonType()) {
      hashCode += prime * getPolygonType().hashCode();
    }
    if (isSetCompression()) {
      hashCode += prime * getCompression().hashCode();
    }
    if (isSetPointIndex()) {
      hashCode += prime * getPointIndex().hashCode();
    }
    if (isSetPointIndexLength()) {
      hashCode += prime * getPointIndexLength();
    }
    if (isSetDataType()) {
      hashCode += prime * getDataType().hashCode();
    }
    return hashCode;
  }


  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetDomainType()) {
      attributes.remove("domainType");
      attributes.put(SpatialConstants.shortLabel + ":domainType", getDomainType());
    }
    if (isSetPolygonType()) {
      attributes.remove("polygonType");
      attributes.put(SpatialConstants.shortLabel + ":polygonType",
        getPolygonType().toString());
    }
    if (isSetCompression()) {
      attributes.remove("compression");
      attributes.put(SpatialConstants.shortLabel + ":compression",
        getCompression().toString());
    }
    if (isSetPointIndexLength()) {
      attributes.remove("pointIndexLength");
      attributes.put(SpatialConstants.shortLabel + ":pointIndexLength",
        String.valueOf(getPointIndexLength()));
    }
    if (isSetDataType()) {
      attributes.remove("dataType");
      // see DataKind.java
      attributes.put(SpatialConstants.shortLabel + ":dataType",
        getDataType().toString().toLowerCase());
    }

    return attributes;
  }


  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = (super.readAttribute(attributeName, prefix, value))
        && (SpatialConstants.shortLabel == prefix);
    if (!isAttributeRead) {
      isAttributeRead = true;
      if (attributeName.equals(SpatialConstants.domainType)) {
        try {
          setDomainType(value);
        } catch (Exception e) {
          logger.warn(MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.domainType, getElementName()));
        }
      }
      else if (attributeName.equals(SpatialConstants.polygonType)) {
        try {
          setPolygonType(value);
        } catch (Exception e) {
          logger.warn(MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.polygonType, getElementName()));
        }
      }
      else if (attributeName.equals(SpatialConstants.compression)) {
        try {
          setCompression(value);
        } catch (Exception e) {
          logger.warn(MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.compression, getElementName()));
        }
      }
      else if (attributeName.equals(SpatialConstants.pointIndexLength)) {
        try {
          setPointIndexLength(StringTools.parseSBMLInt(value));
        } catch (Exception e) {
          logger.warn(MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.pointIndexLength, getElementName()));
        }
      }
      else if (attributeName.equals(SpatialConstants.dataType)) {
        try {
          // see DataKind.java
          setDataType(value.toUpperCase());
        } catch (Exception e) {
          logger.warn(MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.dataType, getElementName()));
        }
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }

}
