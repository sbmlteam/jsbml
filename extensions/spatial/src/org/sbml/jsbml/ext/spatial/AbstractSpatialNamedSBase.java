/*
 * $Id: AbstractSpatialNamedSBase.java 2181 2015-04-09 13:44:21Z niko-rodrigue $
 * $URL: svn://svn.code.sf.net/p/jsbml/code/trunk/extensions/spatial/src/org/sbml/jsbml/ext/spatial/AbstractSpatialNamedSBase.java $
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2015 jointly by the following organizations:
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
import java.util.ResourceBundle;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.util.ResourceManager;

/**
 * @author Alex Thomas
 * @author Andreas Dr&auml;ger
 * @since 1.0
 * @version $Rev: 2181 $
 */
public abstract class AbstractSpatialNamedSBase extends AbstractSBase implements SpatialNamedSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -1814806955800042477L;

  /**
   * 
   */
  String spatialId;

  /**
   * 
   */
  private static final ResourceBundle bundle = ResourceManager.getBundle("org.sbml.jsbml.ext.spatial.Messages");

  /**
   * 
   */
  public AbstractSpatialNamedSBase() {
    super();
    initDefaults();
  }

  /**
   * @param level
   * @param version
   */
  public AbstractSpatialNamedSBase(int level, int version) {
    super(level, version);
    initDefaults();
  }

  /**
   * @param nse
   */
  public AbstractSpatialNamedSBase(SpatialNamedSBase nse) {
    super(nse);
    if (nse.isSetSpatialId()) {
      spatialId = new String(nse.getSpatialId());
    }
  }

  /**
   * 
   * @param spatialId
   * @param level
   * @param version
   */
  public AbstractSpatialNamedSBase(String spatialId, int level, int version) {
    super(level,version);
    initDefaults();
    this.spatialId = spatialId;
  }

  /**
   * 
   * @param spatialId
   */
  public AbstractSpatialNamedSBase(String spatialId) {
    super();
    initDefaults();
    this.spatialId = spatialId;
  }

  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    setNamespace(SpatialConstants.namespaceURI); // TODO - removed once the mechanism are in place to set package version and namespace
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
      SpatialNamedSBase nse = (SpatialNamedSBase) object;
      equal &= nse.isSetSpatialId() == isSetSpatialId();
      if (equal && isSetSpatialId()) {
        equal &= nse.getSpatialId().equals(getSpatialId());
      }
    }
    return equal;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 983;
    int hashCode = super.hashCode();
    if (isSetSpatialId()) {
      hashCode += prime * getSpatialId().hashCode();
    }
    return hashCode;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.SpatialNamedSBase#getSpatialId()
   */
  @Override
  public String getSpatialId() {
    if (isSetSpatialId()) {
      return spatialId;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.spatialId, this);
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.SpatialNamedSBase#isSetSpatialId()
   */
  @Override
  public boolean isSetSpatialId() {
    return spatialId != null;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.SpatialNamedSBase#setSpatialId(java.lang.String)
   */
  @Override
  public void setSpatialId(String spatialId) {
    String oldSpatialId = this.spatialId; // TODO - the IdManager need to be updated
    this.spatialId = spatialId;
    firePropertyChange(SpatialConstants.spatialId, oldSpatialId, this.spatialId);
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.SpatialNamedSBase#unsetSpatialId()
   */
  @Override
  public boolean unsetSpatialId() {
    if (isSetSpatialId()) {
      String oldSpatialId = spatialId;
      spatialId = null;
      firePropertyChange(SpatialConstants.spatialId, oldSpatialId, spatialId);
      return true;
    }
    return false;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetSpatialId()) {
      attributes.put(SpatialConstants.shortLabel + ":" + SpatialConstants.spatialId, getSpatialId());
    }

    return attributes;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(getClass().getSimpleName());
    builder.append(" [spatialId=");
    builder.append(getSpatialId());
    builder.append("]");
    return builder.toString();
  }


  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = (super.readAttribute(attributeName, prefix, value)) && (SpatialConstants.shortLabel == prefix);
    if (!isAttributeRead) {
      isAttributeRead = true;
      if (attributeName.equals(SpatialConstants.spatialId)) {
        try {
          setSpatialId(value);
        } catch (Exception e) {
          MessageFormat.format(bundle.getString("COULD_NOT_READ"), value,
            SpatialConstants.spatialId);
        }
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return false;
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildCount()
   */
  @Override
  public int getChildCount() {
    return 0;
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
   */
  @Override
  public TreeNode getChildAt(int index) {
    return null;
  }

}
