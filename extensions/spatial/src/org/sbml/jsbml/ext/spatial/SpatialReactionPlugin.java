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
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.util.StringTools;

/**
 * @author Alex Thomas
 * @author Andreas Dr&auml;ger
 * @author Piero Dalle Pezze
 * @since 1.0
 */
public class SpatialReactionPlugin extends AbstractSpatialSBasePlugin {


  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(SpatialReactionPlugin.class);

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -2154884901226244123L;

  /**
   * 
   */
  private Boolean isLocal;


  /**
   * 
   */
  public SpatialReactionPlugin() {
    super();
  }

  /**
   * @param rxn
   */
  public SpatialReactionPlugin(Reaction rxn) {
    super(rxn);
  }

  /**
   * @param spr
   */
  public SpatialReactionPlugin(SpatialReactionPlugin spr) {
    super(spr);

    if (spr.isSetIsLocal()) {
      setIsLocal(new Boolean(spr.getIsLocal()));
    }

  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.AbstractSpatialSBasePlugin#clone()
   */
  @Override
  public SpatialReactionPlugin clone() {
    return new SpatialReactionPlugin(this);
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.AbstractSBasePlugin#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equal = super.equals(object);
    if (equal) {
      SpatialReactionPlugin spr = (SpatialReactionPlugin) object;

      equal &= spr.isSetIsLocal() == isSetIsLocal();
      if (equal && isSetIsLocal()) {
        equal &= spr.getIsLocal() == getIsLocal();
      }

    }
    return equal;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.AbstractSBasePlugin#getExtendedSBase()
   */
  @Override
  public Reaction getExtendedSBase() {
    if (isSetExtendedSBase()) {
      return (Reaction) super.getExtendedSBase();
    }

    return null;
  }


  /**
   * Returns the value of isLocal
   *
   * @return the value of isLocal
   */
  public boolean getIsLocal() {
    if (isSetIsLocal()) {
      return isLocal;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.isLocal, this);
  }


  /**
   * Returns whether isLocal is set
   *
   * @return whether isLocal is set
   */
  public boolean isSetIsLocal() {
    return isLocal != null;
  }

  /**
   * @return
   */
  public boolean isLocal() {
    return getIsLocal();
  }

  /**
   * Sets the value of isLocal
   * @param isLocal
   */
  public void setIsLocal(boolean isLocal) {
    Boolean oldIsLocal = this.isLocal;
    this.isLocal = isLocal;
    firePropertyChange(SpatialConstants.isLocal, oldIsLocal, this.isLocal);
  }


  /**
   * Unsets the variable isLocal
   *
   * @return {@code true}, if isLocal was set before,
   *         otherwise {@code false}
   */
  public boolean unsetIsLocal() {
    if (isSetIsLocal()) {
      Boolean oldIsLocal = isLocal;
      isLocal = null;
      firePropertyChange(SpatialConstants.isLocal, oldIsLocal, isLocal);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return false;
  }

  /*
   * (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildCount()
   */
  @Override
  public int getChildCount() {
    return 0;
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildAt(int)
   */
  @Override
  public TreeNode getChildAt(int index) {
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.AbstractSBasePlugin#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 2039;
    int hashCode = super.hashCode();
    if (isSetIsLocal()) {
      hashCode += prime * isLocal.hashCode();
    }
    return hashCode;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.AbstractSpatialSBasePlugin#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetIsLocal()) {
      attributes.remove("isLocal");
      attributes.put(SpatialConstants.shortLabel + ":isLocal", String.valueOf(getIsLocal()));
    }
    return attributes;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.AbstractSpatialSBasePlugin#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = (super.readAttribute(attributeName, prefix, value))
        && (SpatialConstants.shortLabel == prefix);
    if (!isAttributeRead) {
      isAttributeRead = true;
      if (attributeName.equals(SpatialConstants.isLocal)) {
        try {
          setIsLocal(StringTools.parseSBMLBoolean(value));
        } catch (Exception e) {
          logger.warn(MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.isLocal, getClass().getSimpleName()));
        }
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }

}
