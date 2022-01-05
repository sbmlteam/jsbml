/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2022 jointly by the following organizations:
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
import org.sbml.jsbml.NamedSBase;

/**
 * The base class for each spatial class.
 * 
 * @author Alex Thomas
 * @author Andreas Dr&auml;ger
 * @author Piero Dalle Pezze
 * @since 1.0
 */
@SuppressWarnings("deprecation")
public abstract class AbstractSpatialNamedSBase extends AbstractSBase implements NamedSBase, SpatialNamedSBase {

  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(AbstractSpatialNamedSBase.class);

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -1814806955800042477L;

  /**
   * Creates a new {@link AbstractSpatialNamedSBase} instance.
   */
  public AbstractSpatialNamedSBase() {
    super();
    initDefaults();
  }

  /**
   * Creates a new {@link AbstractSpatialNamedSBase} instance.
   * 
   * @param level the SBML level
   * @param version the SBML version
   */
  public AbstractSpatialNamedSBase(int level, int version) {
    super(level, version);
    initDefaults();
  }

  /**
   * Creates a new {@link AbstractSpatialNamedSBase} instance.
   * 
   * @param nse the instance to be clone.
   */
  public AbstractSpatialNamedSBase(SpatialNamedSBase nse) {
    super(nse);
  }

  /**
   * Creates a new {@link AbstractSpatialNamedSBase} instance.
   * 
   * @param id the id
   * @param level the SBML level
   * @param version the SBML version
   */
  public AbstractSpatialNamedSBase(String id, int level, int version) {
    super(id, level,version);
    initDefaults();
  }

  /**
   * Creates a new {@link AbstractSpatialNamedSBase} instance.
   * 
   * @param id the id
   */
  public AbstractSpatialNamedSBase(String id) {
    super(id);
    initDefaults();
  }

  @Override
  public abstract AbstractSpatialNamedSBase clone();

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
    return equal;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#hashCode()
   */
  @Override
  public int hashCode() {
    int hashCode = super.hashCode();
    return hashCode;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.SpatialNamedSBase#getSpatialId()
   */
  @Override
  public String getSpatialId() {
    return getId();
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.SpatialNamedSBase#isSetSpatialId()
   */
  @Override
  public boolean isSetSpatialId() {
    return isSetId();
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.SpatialNamedSBase#setSpatialId(java.lang.String)
   */
  @Override
  public void setSpatialId(String id) {
    setId(id);
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.SpatialNamedSBase#unsetSpatialId()
   */
  @Override
  public boolean unsetSpatialId() {
    if (isSetId()) {
      unsetId();
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

    if (isSetId()) {
      attributes.remove(SpatialConstants.spatialId);
      attributes.put(SpatialConstants.shortLabel + ":" + SpatialConstants.spatialId, getId());
    }
    if (isSetName()) {
      attributes.remove("name");
      attributes.put(SpatialConstants.shortLabel + ":name", getName());
    }
    
    return attributes;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildCount()
   */
  @Override
  public int getChildCount() {
    return 0;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
   */
  @Override
  public TreeNode getChildAt(int index) {
    return null;
  }

}
