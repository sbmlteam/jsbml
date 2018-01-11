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

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;
import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.util.StringTools;

/**
 * 
 * @author Alex Thomas
 * @since 0.8
 */
public class OrdinalMapping extends AbstractSBase {


  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(OrdinalMapping.class);

  /**
   * 
   */
  private static final long serialVersionUID = -7174553771288567408L;

  /**
   * 
   */
  String geometryDefinition;
  /**
   * 
   */
  Integer ordinal;

  /**
   * 
   */
  public OrdinalMapping() {
    super();
    initDefaults();
  }

  /**
   * @param om
   */
  public OrdinalMapping(OrdinalMapping om) {
    super(om);

    if (om.isSetGeometryDefinition()) {
      setGeometryDefinition(om.getGeometryDefinition());
    }
    if (om.isSetOrdinal()) {
      setOrdinal(om.getOrdinal());
    }
  }

  /**
   * 
   * @param level
   * @param version
   */
  public OrdinalMapping(int level, int version) {
    super(level, version);
    initDefaults();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public OrdinalMapping clone() {
    return new OrdinalMapping(this);
  }

  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    setPackageVersion(-1);
    packageName = SpatialConstants.shortLabel;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equal = super.equals(object);
    if (equal) {
      OrdinalMapping om = (OrdinalMapping) object;

      equal &= om.isSetOrdinal() == isSetOrdinal();
      if (equal && isSetOrdinal()) {
        equal &= om.getOrdinal() == getOrdinal();
      }

      equal &= om.isSetGeometryDefinition() == isSetGeometryDefinition();
      if (equal && isSetGeometryDefinition()) {
        equal &= om.getGeometryDefinition().equals(getGeometryDefinition());
      }
    }
    return equal;
  }

  /**
   * Returns the value of ordinal
   *
   * @return the value of ordinal
   */
  public int getOrdinal() {
    if (isSetOrdinal()) {
      return ordinal;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.ordinal, this);
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
   * @param ordinal
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

  /**
   * Returns the value of geometryDefinition
   *
   * @return the value of geometryDefinition
   */
  public String getGeometryDefinition() {
    if (isSetGeometryDefinition()) {
      return geometryDefinition;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.geometryDefinition, this);
  }

  /**
   * Returns whether geometryDefinition is set
   *
   * @return whether geometryDefinition is set
   */
  public boolean isSetGeometryDefinition() {
    return geometryDefinition != null;
  }

  /**
   * Sets the value of geometryDefinition
   * @param geometryDefinition
   */
  public void setGeometryDefinition(String geometryDefinition) {
    String oldGeometryDefinition = this.geometryDefinition;
    this.geometryDefinition = geometryDefinition;
    firePropertyChange(SpatialConstants.geometryDefinition, oldGeometryDefinition, this.geometryDefinition);
  }

  /**
   * Unsets the variable geometryDefinition
   *
   * @return {@code true}, if geometryDefinition was set before,
   *         otherwise {@code false}
   */
  public boolean unsetGeometryDefinition() {
    if (isSetGeometryDefinition()) {
      String oldGeometryDefinition = geometryDefinition;
      geometryDefinition = null;
      firePropertyChange(SpatialConstants.geometryDefinition, oldGeometryDefinition, geometryDefinition);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 2039;
    int hashCode = super.hashCode();
    if (isSetOrdinal()) {
      hashCode += prime * getOrdinal();
    }
    if (isSetGeometryDefinition()) {
      hashCode += prime * getGeometryDefinition().hashCode();
    }
    return hashCode;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetOrdinal()) {
      attributes.put(SpatialConstants.shortLabel + ":ordinal", ordinal.toString());
    }
    if (isSetGeometryDefinition()) {
      attributes.put(SpatialConstants.shortLabel + ":geometryDefinition",
        getGeometryDefinition());
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
      if (attributeName.equals(SpatialConstants.ordinal)) {
        try {
          setOrdinal(StringTools.parseSBMLInt(value));
        } catch (Exception e) {
          logger.warn(MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.ordinal, getElementName()));
        }
      }
      else if (attributeName.equals(SpatialConstants.geometryDefinition)) {
        try {
          setGeometryDefinition(value);
        } catch (Exception e) {
          logger.warn(MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.geometryDefinition, getElementName()));
        }
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
   */
  @Override
  public TreeNode getChildAt(int childIndex) {
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildCount()
   */
  @Override
  public int getChildCount() {
    return 0;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return false;
  }

}
