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
public class SampledVolume extends AbstractSpatialNamedSBase {


  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(SampledVolume.class);

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -8253664900275697978L;

  /**
   * 
   */
  private String domainType;
  /**
   * 
   */
  private Double sampledValue;
  /**
   * 
   */
  private Double minValue;
  /**
   * 
   */
  private Double maxValue;


  /**
   * 
   */
  public SampledVolume() {
    super();
  }

  /**
   * @param sv
   */
  public SampledVolume(SampledVolume sv) {
    super(sv);
    if (sv.isSetDomainType()) {
      setDomainType(sv.getDomainType());
    }
    if (sv.isSetSampledValue()) {
      setSampledValue(sv.getSampledValue());
    }
    if (sv.isSetMinValue()) {
      setMinValue(sv.getMinValue());
    }
    if (sv.isSetMaxValue()) {
      setMaxValue(sv.getMaxValue());
    }
  }

  /**
   * @param id
   * @param level
   * @param version
   */
  public SampledVolume(String id, int level, int version) {
    super(id,level,version);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractTreeNode#clone()
   */
  @Override
  public SampledVolume clone() {
    return new SampledVolume(this);
  }

  @Override
  public boolean equals(Object object) {
    boolean equal = super.equals(object);
    if (equal) {
      SampledVolume sv = (SampledVolume) object;
      equal &= sv.isSetSampledValue() == isSetSampledValue();
      if (equal && isSetSampledValue()) {
        equal &= sv.getSampledValue() == getSampledValue();
      }
      equal &= sv.isSetDomainType() == isSetDomainType();
      if (equal && isSetDomainType()) {
        equal &= sv.getDomainType().equals(getDomainType());
      }
      equal &= sv.isSetMaxValue() == isSetMaxValue();
      if (equal && isSetMaxValue()) {
        equal &= sv.getMaxValue() == getMaxValue();
      }
      equal &= sv.isSetMinValue() == isSetMinValue();
      if (equal && isSetMinValue()) {
        equal &= sv.getMinValue() == getMinValue();
      }
    }
    return equal;
  }


  /**
   * Returns the value of domainType
   *
   * @return the value of domainType
   */
  public String getDomainType() {
    if (isSetDomainType()) {
      return domainType;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.domainType, this);
  }


  /**
   * Returns whether domainType is set
   *
   * @return whether domainType is set
   */
  public boolean isSetDomainType() {
    return domainType != null;
  }


  /**
   * Sets the value of domainType
   * @param domainType
   */
  public void setDomainType(String domainType) {
    String oldDomainType = this.domainType;
    this.domainType = domainType;
    firePropertyChange(SpatialConstants.domainType, oldDomainType, this.domainType);
  }


  /**
   * Unsets the variable domainType
   *
   * @return {@code true}, if domainType was set before,
   *         otherwise {@code false}
   */
  public boolean unsetDomainType() {
    if (isSetDomainType()) {
      String oldDomainType = domainType;
      domainType = null;
      firePropertyChange(SpatialConstants.domainType, oldDomainType, domainType);
      return true;
    }
    return false;
  }


  /**
   * Returns the value of sampledValue
   *
   * @return the value of sampledValue
   */
  public double getSampledValue() {
    if (isSetSampledValue()) {
      return sampledValue.doubleValue();
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.sampledValue, this);
  }

  /**
   * Returns whether sampledValue is set
   *
   * @return whether sampledValue is set
   */
  public boolean isSetSampledValue() {
    return sampledValue != null;
  }

  /**
   * Sets the value of sampledValue
   * @param sampledValue
   */
  public void setSampledValue(double sampledValue) {
    Double oldSampledValue = this.sampledValue;
    this.sampledValue = sampledValue;
    firePropertyChange(SpatialConstants.sampledValue, oldSampledValue, this.sampledValue);
  }


  /**
   * Unsets the variable sampledValue
   *
   * @return {@code true}, if sampledValue was set before,
   *         otherwise {@code false}
   */
  public boolean unsetSampledValue() {
    if (isSetSampledValue()) {
      Double oldSampledValue = sampledValue;
      sampledValue = null;
      firePropertyChange(SpatialConstants.sampledValue, oldSampledValue, sampledValue);
      return true;
    }
    return false;
  }


  /**
   * Returns the value of minValue
   *
   * @return the value of minValue
   */
  public double getMinValue() {
    if (isSetMinValue()) {
      return minValue.doubleValue();
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.minValue, this);
  }


  /**
   * Returns whether minValue is set
   *
   * @return whether minValue is set
   */
  public boolean isSetMinValue() {
    return minValue != null;
  }

  /**
   * Sets the value of minValue
   * @param minValue
   */
  public void setMinValue(double minValue) {
    Double oldMinValue = this.minValue;
    this.minValue = minValue;
    firePropertyChange(SpatialConstants.minValue, oldMinValue, this.minValue);
  }

  /**
   * Unsets the variable minValue
   *
   * @return {@code true}, if minValue was set before,
   *         otherwise {@code false}
   */
  public boolean unsetMinValue() {
    if (isSetMinValue()) {
      Double oldMinValue = minValue;
      minValue = null;
      firePropertyChange(SpatialConstants.minValue, oldMinValue, minValue);
      return true;
    }
    return false;
  }


  /**
   * Returns the value of maxValue
   *
   * @return the value of maxValue
   */
  public double getMaxValue() {
    if (isSetMaxValue()) {
      return maxValue.doubleValue();
    }
    throw new PropertyUndefinedError(SpatialConstants.maxValue, this);
  }


  /**
   * Returns whether maxValue is set
   *
   * @return whether maxValue is set
   */
  public boolean isSetMaxValue() {
    return maxValue != null;
  }


  /**
   * Sets the value of maxValue
   * @param maxValue
   */
  public void setMaxValue(double maxValue) {
    Double oldMaxValue = this.maxValue;
    this.maxValue = maxValue;
    firePropertyChange(SpatialConstants.maxValue, oldMaxValue, this.maxValue);
  }


  /**
   * Unsets the variable maxValue
   *
   * @return {@code true}, if maxValue was set before,
   *         otherwise {@code false}
   */
  public boolean unsetMaxValue() {
    if (isSetMaxValue()) {
      Double oldMaxValue = maxValue;
      maxValue = null;
      firePropertyChange(SpatialConstants.maxValue, oldMaxValue, maxValue);
      return true;
    }
    return false;
  }


  @Override
  public int hashCode() {
    final int prime = 1949;
    int hashCode = super.hashCode();
    if (isSetSampledValue()) {
      hashCode += prime * getSampledValue();
    }
    if (isSetMinValue()) {
      hashCode += prime * getMinValue();
    }
    if (isSetMaxValue()) {
      hashCode += prime * getMaxValue();
    }
    if (isSetDomainType()) {
      hashCode += prime * getDomainType().hashCode();
    }
    return hashCode;
  }


  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetSampledValue()) {
      attributes.remove("sampledValue");
      attributes.put(SpatialConstants.shortLabel + ":sampledValue", String.valueOf(getSampledValue()));
    }
    if (isSetMinValue()) {
      attributes.remove("minValue");
      attributes.put(SpatialConstants.shortLabel + ":minValue", String.valueOf(getMinValue()));
    }
    if (isSetMaxValue()) {
      attributes.remove("maxValue");
      attributes.put(SpatialConstants.shortLabel + ":maxValue", String.valueOf(getMaxValue()));
    }
    if (isSetDomainType()) {
      attributes.remove("domainType");
      attributes.put(SpatialConstants.shortLabel + ":domainType", getDomainType());
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
      if (attributeName.equals(SpatialConstants.domainType)) {
        try {
          setDomainType(value);
        } catch (Exception e) {
          logger.warn(MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.domainType, getElementName()));
        }
      }
      else if (attributeName.equals(SpatialConstants.sampledValue)) {
        try {
          setSampledValue(StringTools.parseSBMLDouble(value));
        } catch (Exception e) {
          logger.warn(MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.sampledValue, getElementName()));
        }
      }
      else if (attributeName.equals(SpatialConstants.minValue)) {
        try {
          setMinValue(StringTools.parseSBMLDouble(value));
        } catch (Exception e) {
          logger.warn(MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.minValue, getElementName()));
        }
      }
      else if (attributeName.equals(SpatialConstants.maxValue)) {
        try {
          setMaxValue(StringTools.parseSBMLDouble(value));
        } catch (Exception e) {
          logger.warn(MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.maxValue, getElementName()));
        }
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }

}
