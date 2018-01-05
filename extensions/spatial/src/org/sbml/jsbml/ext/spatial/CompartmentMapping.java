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
import org.sbml.jsbml.Model;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.util.StringTools;

/**
 * @author Alex Thomas
 * @author Andreas Dr&auml;ger
 * @author Piero Dalle Pezze
 * @since 1.0
 */
public class CompartmentMapping extends AbstractSpatialNamedSBase {


  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(CompartmentMapping.class);

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -4623759168043277022L;

  /**
   * 
   */
  private String domainType;

  /**
   * 
   */
  private Double unitSize;


  /**
   * 
   */
  public CompartmentMapping() {
    super();
  }

  /**
   * @param cm
   */
  public CompartmentMapping(CompartmentMapping cm) {
    super(cm);

    if (cm.isSetDomainType()) {
      domainType = new String(cm.getDomainType());
    }
    if (cm.isSetUnitSize()) {
      unitSize = new Double(cm.getUnitSize());
    }

  }

  /**
   * @param level
   * @param version
   */
  public CompartmentMapping(int level, int version) {
    super(level, version);
  }

  /**
   * 
   * @param id
   * @param level
   * @param version
   */
  public CompartmentMapping(String id, int level, int version) {
    super(id, level, version);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public CompartmentMapping clone() {
    return new CompartmentMapping(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.AbstractSpatialNamedSBase#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equal = super.equals(object);
    if (equal) {
      CompartmentMapping cm = (CompartmentMapping) object;

      equal &= cm.isSetDomainType() == isSetDomainType();
      if (equal && isSetDomainType()) {
        equal &= cm.getDomainType().equals(getDomainType());
      }
      equal &= cm.isSetUnitSize() == isSetUnitSize();
      if (equal && isSetUnitSize()) {
        equal &= cm.getUnitSize() == getUnitSize();
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
   * Returns the value of unitSize
   *
   * @return the value of unitSize
   */
  public double getUnitSize() {
    if (isSetUnitSize()) {
      return unitSize;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.unitSize, this);
  }


  /**
   * Returns whether unitSize is set
   *
   * @return whether unitSize is set
   */
  public boolean isSetUnitSize() {
    return unitSize != null;
  }


  /**
   * Sets the value of unitSize
   * @param unitSize
   */
  public void setUnitSize(double unitSize) {
    Double oldUnitSize = this.unitSize;
    this.unitSize = unitSize;
    firePropertyChange(SpatialConstants.unitSize, oldUnitSize, this.unitSize);
  }


  /**
   * Unsets the variable unitSize
   *
   * @return {@code true}, if unitSize was set before,
   *         otherwise {@code false}
   */
  public boolean unsetUnitSize() {
    if (isSetUnitSize()) {
      Double oldUnitSize = unitSize;
      unitSize = null;
      firePropertyChange(SpatialConstants.unitSize, oldUnitSize, unitSize);
      return true;
    }
    return false;
  }

  /**
   * 
   * @return the domainType instance of the {@link DomainType} class
   */
  public DomainType getDomainTypeInstance() {
    Model m = getModel();
    if (m!=null) {
      SpatialModelPlugin sm = (SpatialModelPlugin) m.getExtension(packageName);
      sm.getGeometry().getListOfDomainTypes().get(getDomainType());
    }
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.AbstractSpatialNamedSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 1319;//Changed, it was 983
    int hashCode = super.hashCode();
    if (isSetDomainType()) {
      hashCode += prime * getDomainType().hashCode();
    }
    if (isSetUnitSize()) {
      hashCode += prime * getUnitSize();
    }
    return hashCode;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.AbstractSpatialNamedSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetDomainType()) {
      attributes.remove("domainType");
      attributes.put(SpatialConstants.shortLabel + ":domainType",
        getDomainType());
    }
    if (isSetUnitSize()) {
      attributes.remove("unitSize");
      attributes.put(SpatialConstants.shortLabel + ":unitSize",
        String.valueOf(getUnitSize()));
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
      if (attributeName.equals(SpatialConstants.domainType)) {
        try {
          setDomainType(value);
        } catch (Exception e) {
          logger.warn(MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.domainType, getElementName()));
        }
      }
      else if (attributeName.equals(SpatialConstants.unitSize)) {
        try {
          setUnitSize(StringTools.parseSBMLDouble(value));
        } catch (Exception e) {
          logger.warn(MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.unitSize, getElementName()));
        }
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }

}
