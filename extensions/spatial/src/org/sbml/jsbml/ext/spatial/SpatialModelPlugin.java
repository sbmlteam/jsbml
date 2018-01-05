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
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.util.IdManager;

/**
 * @author Alex Thomas
 * @since 1.0
 */
public class SpatialModelPlugin extends AbstractSpatialSBasePlugin implements IdManager {

  /**
   * Generated serial version ID
   */
  private static final long serialVersionUID = 8449591192464040411L;

  /**
   * 
   */
  private Geometry geometry;

  /**
   * 
   */
  private Map<String, SpatialNamedSBase> spatialIdMap;

  /**
   * A logger for this class.
   */
  private static final transient Logger logger = Logger.getLogger(SpatialModelPlugin.class);

  /**
   * Returns the value of geometry
   *
   * @return the value of geometry
   */
  public Geometry getGeometry() {
    if (isSetGeometry()) {
      return geometry;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.geometry, this);
  }

  /**
   * Creates a new {@link Geometry}, adds it to this object
   * and returns a pointer to it.
   * 
   * @return a new {@link Geometry} object.
   * 
   */
  public Geometry createGeometry() {
    Geometry geometry = new Geometry();

    setGeometry(geometry);

    return geometry;
  }

  /**
   * Returns whether geometry is set
   *
   * @return whether geometry is set
   */
  public boolean isSetGeometry() {
    return geometry != null;
  }

  /**
   * Sets the value of geometry
   * @param geometry
   */
  public void setGeometry(Geometry geometry) {
    Geometry oldGeometry = this.geometry;
    this.geometry = geometry;

    if (isSetExtendedSBase()) {
      getExtendedSBase().registerChild(geometry);
    }

    firePropertyChange(SpatialConstants.geometry, oldGeometry, this.geometry);
  }

  /**
   * Unsets the variable geometry
   *
   * @return {@code true}, if geometry was set before,
   *         otherwise {@code false}
   */
  public boolean unsetGeometry() {
    if (isSetGeometry()) {
      Geometry oldGeometry = geometry;
      geometry = null;
      firePropertyChange(SpatialConstants.geometry, oldGeometry, geometry);
      return true;
    }
    return false;
  }

  /**
   * 
   */
  public SpatialModelPlugin() {
    super();
  }

  /**
   * @param model
   */
  public SpatialModelPlugin(Model model) {
    super(model);
  }

  /**
   * @param spatialModelPlugin
   */
  public SpatialModelPlugin(SpatialModelPlugin spatialModelPlugin) {
    super(spatialModelPlugin);

    if (spatialModelPlugin.isSetGeometry()) {
      setGeometry(spatialModelPlugin.getGeometry().clone());
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.AbstractSBasePlugin#getExtendedSBase()
   */
  @Override
  public Model getExtendedSBase() {
    if (isSetExtendedSBase()) {
      return (Model) super.getExtendedSBase();
    }
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.AbstractSBasePlugin#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    return null;
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return true;
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildAt(int)
   */
  @Override
  public Geometry getChildAt(int index) {
    if (index < 0) {
      throw new IndexOutOfBoundsException(MessageFormat.format(
        resourceBundle.getString("IndexSurpassesBoundsException"), index, 0));
    }
    int pos = 0;

    if (isSetGeometry()) {
      if (pos == index) {
        return getGeometry();
      }
      pos++;
    }

    throw new IndexOutOfBoundsException(MessageFormat.format(
      resourceBundle.getString("IndexExceedsBoundsException"),
      index, pos));
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildCount()
   */
  @Override
  public int getChildCount() {
    return isSetGeometry() ? 1 : 0;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.AbstractSBasePlugin#clone()
   */
  @Override
  public SpatialModelPlugin clone() {
    return new SpatialModelPlugin(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.IdManager#accept(org.sbml.jsbml.SBase)
   */
  @Override
  public boolean accept(SBase sbase) {
    if (logger.isDebugEnabled()) {
      logger.debug(MessageFormat.format("accept called on {0}", sbase.getElementName()));
    }

    if (sbase instanceof SpatialNamedSBase) {
      return true;
    }

    if (sbase instanceof ListOf<?>) {
      ListOf<?> listOf = (ListOf<?>) sbase;

      if ((listOf.size() > 0) && (listOf.get(0) instanceof SpatialNamedSBase)) {
        return true;
      }
    }

    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.IdManager#register(org.sbml.jsbml.SBase)
   */
  @Override
  public boolean register(SBase sbase) {

    boolean success = true;

    if (sbase instanceof SpatialNamedSBase) {
      SpatialNamedSBase spatialSBase = (SpatialNamedSBase) sbase;

      if (spatialSBase.isSetSpatialId()) {
        String id = spatialSBase.getSpatialId();

        if (spatialIdMap == null) {
          spatialIdMap = new HashMap<String, SpatialNamedSBase>();
        }

        if (spatialIdMap.containsKey(id)) {

          logger.error(MessageFormat.format(
            "The spatial id \"{0}\" is already present in the spatial model assigned to the model {1}. The new element will not be added to the model.",
            id, (isSetExtendedSBase() ? getExtendedSBase().getId() : "")));
          success = false;
        } else {
          spatialIdMap.put(id, spatialSBase);

          if (logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format("registered spatial id={0} in {1}",
              id, (isSetExtendedSBase() ? getExtendedSBase().getElementName() : "")));
          }
        }

      }
    } else {
      logger.error(MessageFormat.format(
        "Trying to register something that does not have a spatialId: \"{0}\".", sbase));
    }

    // Register all spatial model children if any

    return success;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.IdManager#unregister(org.sbml.jsbml.SBase)
   */
  @Override
  public boolean unregister(SBase sbase) {

    // Always returning true at the moment to avoid exception when unregistering element
    boolean success = true;

    if (sbase instanceof SpatialNamedSBase) {
      SpatialNamedSBase spatialSbase = (SpatialNamedSBase) sbase;

      if (spatialSbase.isSetSpatialId()) {
        String id = spatialSbase.getSpatialId();

        if (spatialIdMap == null) {
          logger.warn(MessageFormat.format(
            "No elements with spatial ids have been registered in this {0}. Nothing to be done.",
            (isSetExtendedSBase() ? getExtendedSBase().getElementName() : "")));
          return success;
        }

        if (spatialIdMap.containsKey(id)) {
          spatialIdMap.remove(id);
          if (logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format("unregistered spatial id={0} in {1}",
              id, (isSetExtendedSBase() ? getExtendedSBase().getElementName() : "")));
          }
        } else {

          logger.warn(MessageFormat.format(
            "The spatial id \"{0}\" is not present in this model {1}. Nothing to be done.",
            id, (isSetExtendedSBase() ? getExtendedSBase().getId() : "")));
        }
      }
    } else {
      logger.error(MessageFormat.format(
        "Trying to unregister something that does not have a spatial id: \"{0}\".", sbase));
    }

    // Unregister all spatial model children if any

    return success;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.AbstractSBasePlugin#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 2039;
    int hashCode = super.hashCode();
    if (isSetGeometry()) {
      hashCode += prime * getGeometry().hashCode();
    }
    return hashCode;
  }

}
