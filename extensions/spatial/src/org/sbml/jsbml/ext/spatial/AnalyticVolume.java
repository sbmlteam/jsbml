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
import org.sbml.jsbml.AbstractMathContainer;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.util.StringTools;

/**
 * @author Alex Thomas
 * @author Andreas Dr&auml;ger
 * @author Piero Dalle Pezze
 * @since 1.0
 */
public class AnalyticVolume extends AbstractMathContainer implements SpatialNamedSBase {


  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(AnalyticVolume.class);

  /**
   * 
   */
  private String domainType;
  /**
   * 
   */
  private FunctionKind functionType;
  /**
   * 
   */
  private Integer ordinal;

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 1757917501241390228L;


  /**
   * Creates a new {@link AnalyticVolume} instance.
   */
  public AnalyticVolume() {
    super();
    initDefaults();
  }

  /**
   * Creates a new {@link AnalyticVolume} instance.
   * 
   * @param node the {@link AnalyticVolume} to be clone.
   */
  public AnalyticVolume(AnalyticVolume node) {
    super(node);

    if (node.isSetOrdinal()) {
      setOrdinal(node.getOrdinal());
    }
    if (node.isSetFunctionType()) {
      setFunctionType(node.getFunctionType());
    }
    if (node.isSetDomainType()) {
      setDomainType(node.getDomainType());
    }
  }

  /**
   * Creates a new {@link AnalyticVolume} instance.
   * 
   * @param id the id
   * @param level the SBML level
   * @param version the SBML version
   */
  public AnalyticVolume(String id, int level, int version)
  {
    super(id, level,version);
    initDefaults();
  }

  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    setPackageVersion(-1);
    packageName = SpatialConstants.shortLabel;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractTreeNode#clone()
   */
  @Override
  public AnalyticVolume clone() {
    return new AnalyticVolume(this);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equal = super.equals(object);
    if (equal) {
      AnalyticVolume av = (AnalyticVolume) object;

      equal &= av.isSetOrdinal() == isSetOrdinal();
      if (equal && isSetOrdinal()) {
        equal &= av.getOrdinal() == getOrdinal();
      }
      equal &= av.isSetDomainType() == isSetDomainType();
      if (equal && isSetDomainType()) {
        equal &= av.getDomainType() == getDomainType();
      }
      equal &= av.isSetFunctionType() == isSetFunctionType();
      if (equal && isSetFunctionType()) {
        equal &= av.getFunctionType() == getFunctionType();
      }
    }
    return equal;
  }


  /**
   * Returns the value of functionType
   *
   * @return the value of functionType
   */
  public FunctionKind getFunctionType() {
    if (isSetFunctionType()) {
      return functionType;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.functionType, this);
  }


  /**
   * Returns whether functionType is set
   *
   * @return whether functionType is set
   */
  public boolean isSetFunctionType() {
    return functionType != null;
  }


  /**
   * Sets the value of functionType
   * 
   * @param functionType the function type
   */
  public void setFunctionType(FunctionKind functionType) {
    FunctionKind oldFunctionType = this.functionType;
    this.functionType = functionType;
    firePropertyChange(SpatialConstants.functionType, oldFunctionType, this.functionType);
  }


  /**
   * Unsets the variable functionType
   *
   * @return {@code true}, if functionType was set before,
   *         otherwise {@code false}
   */
  public boolean unsetFunctionType() {
    if (isSetFunctionType()) {
      FunctionKind oldFunctionType = functionType;
      functionType = null;
      firePropertyChange(SpatialConstants.functionType, oldFunctionType, functionType);
      return true;
    }
    return false;
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
   * 
   * @param domainType the domain type
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
   * Returns the value of ordinal
   *
   * @return the value of ordinal
   */
  public Integer getOrdinal() {
    if (isSetOrdinal()) {
      return ordinal;
    }
    return null;
  }

  /**
   * Returns whether ordinal is set
   *
   * @return whether ordinal is set
   */
  public boolean isSetOrdinal() {
    return ordinal != null;
  }

  /**
   * Sets the value of ordinal
   * 
   * @param ordinal the ordinal
   */
  public void setOrdinal(int ordinal) {
    Integer oldOrdinal = this.ordinal;
    this.ordinal = ordinal;
    firePropertyChange(SpatialConstants.ordinal, oldOrdinal, this.ordinal);
  }

  /**
   * Unsets the variable ordinal
   *
   * @return {@code true}, if ordinal was set before,
   *         otherwise {@code false}
   */
  public boolean unsetOrdinal() {
    if (isSetOrdinal()) {
      Integer oldOrdinal = ordinal;
      ordinal = null;
      firePropertyChange(SpatialConstants.ordinal, oldOrdinal, ordinal);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.SpatialNamedSBase#unsetSpatialId()
   */
  @Override
  public boolean unsetSpatialId() {
    unsetId();
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.SpatialNamedSBase#setSpatialId(java.lang.String)
   */
  @Override
  public void setSpatialId(String spatialId) {
    setId(spatialId);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.SpatialNamedSBase#isSetSpatialId()
   */
  @Override
  public boolean isSetSpatialId() {
    return isSetId();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.SpatialNamedSBase#getSpatialId()
   */
  @Override
  public String getSpatialId() {
    return getId();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 1093;//Changed. It was 431
    int hashCode = super.hashCode();

    if (isSetFunctionType()) {
      hashCode += prime * getFunctionType().hashCode();
    }
    if (isSetDomainType()) {
      hashCode += prime * getDomainType().hashCode();
    }
    if (isSetOrdinal()) {
      hashCode += prime * getOrdinal().hashCode();
    }
    return hashCode;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractMathContainer#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetSpatialId()) {
      attributes.remove("id");
      attributes.put(SpatialConstants.shortLabel + ":id", getSpatialId());
    }
    if (isSetDomainType()) {
      attributes.remove("domainType");
      attributes.put(SpatialConstants.shortLabel + ":domainType", getDomainType());
    }
    if (isSetOrdinal()) {
      attributes.remove("ordinal");
      attributes.put(SpatialConstants.shortLabel + ":ordinal", String.valueOf(getOrdinal()));
    }
    if (isSetFunctionType()) {
      attributes.remove("functionType");
      attributes.put(SpatialConstants.shortLabel + ":functionType", getFunctionType().toString());
    }

    return attributes;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractMathContainer#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = (super.readAttribute(attributeName, prefix, value))
        && (SpatialConstants.shortLabel == prefix);
    if (!isAttributeRead) {
      isAttributeRead = true;
      if (attributeName.equals(SpatialConstants.spatialId)) {
        try {
          setSpatialId(value);
        } catch (Exception e) {
          logger.warn(MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.spatialId, getElementName()));
        }
      }
      else if (attributeName.equals(SpatialConstants.ordinal)) {
        try {
          setOrdinal(StringTools.parseSBMLInt(value));
        } catch (Exception e) {
          logger.warn(MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.ordinal, getElementName()));
        }
      }
      else if (attributeName.equals(SpatialConstants.domainType)) {
        try {
          setDomainType(value);
        } catch (Exception e) {
          logger.warn(MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.domainType, getElementName()));
        }
      }
      else if (attributeName.equals(SpatialConstants.functionType)) {
        try {
          setFunctionType(FunctionKind.valueOf(value));
        } catch (Exception e) {
          logger.warn(MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.functionType, getElementName()));
        }
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }

}
