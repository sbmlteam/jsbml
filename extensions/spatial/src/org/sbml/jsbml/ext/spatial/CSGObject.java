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
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.util.StringTools;


/**
 * @author Alex Thomas
 * @author Andreas Dr&auml;ger
 * @since 1.0
 */
public class CSGObject extends AbstractSpatialNamedSBase {


  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(CSGObject.class);

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 1385748485171648666L;

  /**
   * 
   */
  private String domainType;

  /**
   * 
   */
  private Integer ordinal;

  /**
   * 
   */
  private CSGNode csgNode;


  /**
   * 
   */
  public CSGObject() {
    super();
  }


  /**
   * @param csgo
   */
  public CSGObject(CSGObject csgo) {
    super(csgo);
    if (csgo.isSetDomainType()) {
      setDomainType(csgo.getDomainType());
    }
    if (csgo.isSetOrdinal()) {
      setOrdinal(csgo.getOrdinal());
    }
    if (csgo.isSetCSGNode()) {
      setCSGNode((CSGNode) csgo.getCSGNode().clone());
    }
  }


  /**
   * @param level
   * @param version
   */
  public CSGObject(int level, int version) {
    super(level, version);
  }


  /**
   * 
   * @param id
   * @param level
   * @param version
   */
  public CSGObject(String id, int level, int version) {
    super(id, level, version);
  }


  @Override
  public CSGObject clone() {
    return new CSGObject(this);
  }


  @Override
  public boolean equals(Object object) {
    boolean equal = super.equals(object);
    if (equal) {
      CSGObject csgo = (CSGObject) object;

      equal &= csgo.isSetDomainType() == isSetDomainType();
      if (equal && isSetDomainType()) {
        equal &= csgo.getDomainType().equals(getDomainType());
      }

      equal &= csgo.isSetOrdinal() == isSetOrdinal();
      if (equal && isSetOrdinal()) {
        equal &= csgo.getOrdinal() == getOrdinal();
      }

      equal &= csgo.isSetCSGNode() == isSetCSGNode();
      if (equal && isSetCSGNode()) {
        equal &= csgo.getCSGNode().equals(getCSGNode());
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
   * Returns the value of ordinal
   *
   * @return the value of ordinal
   */
  public Integer getOrdinal() {
    if (isSetOrdinal()) {
      return ordinal;
    }
    // This is necessary if we cannot return null here.
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
   * Returns the value of csgNode
   *
   * @return the value of csgNode
   */
  public CSGNode getCSGNode() {
    if (isSetCSGNode()) {
      return csgNode;
    }
    throw new PropertyUndefinedError(SpatialConstants.csgNode, this);
  }

  /**
   * Returns whether csgNode is set
   *
   * @return whether csgNode is set
   */
  public boolean isSetCSGNode() {
    return csgNode != null;
  }


  /**
   * Sets the value of csgNode
   * @param csgNode
   */
  public void setCSGNode(CSGNode csgNode) {
    CSGNode oldCSGNode = this.csgNode;
    this.csgNode = csgNode;
    registerChild(csgNode);
    firePropertyChange(SpatialConstants.csgNode, oldCSGNode, this.csgNode);
  }


  /**
   * Unsets the variable csgNode
   *
   * @return {@code true}, if csgNode was set before,
   *         otherwise {@code false}
   */
  public boolean unsetCSGNode() {
    if (isSetCSGNode()) {
      CSGNode oldCSGNode = csgNode;
      csgNode = null;
      firePropertyChange(SpatialConstants.csgNode, oldCSGNode, csgNode);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.AbstractSpatialNamedSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 1669;
    int hashCode = super.hashCode();
    if (isSetOrdinal()) {
      hashCode += prime * getOrdinal();
    }
    if (isSetDomainType()) {
      hashCode += prime * getDomainType().hashCode();
    }
    if (isSetCSGNode()) {
      hashCode += prime * getCSGNode().hashCode();
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
      attributes.put(SpatialConstants.shortLabel + ":domainType", getDomainType());
    }

    if (isSetOrdinal()) {
      attributes.remove("ordinal");
      attributes.put(SpatialConstants.shortLabel + ":ordinal",
        String.valueOf(getOrdinal()));
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

      else if (attributeName.equals(SpatialConstants.ordinal)) {
        try {
          setOrdinal(StringTools.parseSBMLInt(value));
        } catch (Exception e) {
          logger.warn(MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.ordinal, getElementName()));
        }
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.AbstractSpatialNamedSBase#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.AbstractSpatialNamedSBase#getChildCount()
   */
  @Override
  public int getChildCount() {
    int count = super.getChildCount();
    if (isSetCSGNode()) {
      count++;
    }
    return count;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.AbstractSpatialNamedSBase#getChildAt(int)
   */
  @Override
  public TreeNode getChildAt(int index) {
    if (index < 0) {
      throw new IndexOutOfBoundsException(MessageFormat.format(
        resourceBundle.getString("IndexSurpassesBoundsException"), index, 0));
    }
    int count = super.getChildCount(), pos = 0;
    if (index < count) {
      return super.getChildAt(index);
    } else {
      index -= count;
    }
    if (isSetCSGNode()) {
      if (pos == index) {
        return getCSGNode();
      }
      pos++;
    }
    throw new IndexOutOfBoundsException(MessageFormat.format(
      resourceBundle.getString("IndexExceedsBoundsException"), index,
      Math.min(pos, 0)));
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getElementName()
   */
  @Override
  public String getElementName() {
    return SpatialConstants.csgObject;
  }

}
