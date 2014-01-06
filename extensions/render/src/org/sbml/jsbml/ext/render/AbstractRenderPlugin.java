/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2014 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.render;

import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.AbstractSBasePlugin;

/**
 * @author Jakob Matthes
 * @version $Rev$
 * @since 1.0
 * @date 16.05.2012
 */
public class AbstractRenderPlugin extends AbstractSBasePlugin {

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getElementNamespace()
   */
  @Override
  public String getElementNamespace() {
    return RenderConstants.getNamespaceURI(getLevel(), getVersion());
  }
  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getPackageName()
   */
  @Override
  public String getPackageName() {
    return RenderConstants.packageName;
  }
  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getPrefix()
   */
  @Override
  public String getPrefix() {
    return RenderConstants.shortLabel;
  }
  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getURI()
   */
  @Override
  public String getURI() {
    return getElementNamespace();
  }


  /**
   *
   */
  private static final long serialVersionUID = -4225426173177528441L;
  private Short versionMajor;
  private Short versionMinor;
  private GlobalRenderInformation renderInformation;


  /**
   * Creates an AbstractRenderPlugin instance
   */
  public AbstractRenderPlugin(SBase extendedElement) {
    super(extendedElement);
    initDefaults();
  }

  /**
   * Creates a AbstractRenderPlugin instance with a level and version.
   *
   * @param level
   * @param version
   */
  public AbstractRenderPlugin(int level, int version) {
    this(null, null, level, version);
  }

  /**
   * Creates a AbstractRenderPlugin instance with an id, level, and version.
   *
   * @param id
   * @param level
   * @param version
   */
  public AbstractRenderPlugin(String id, int level, int version) {
    this(id, null, level, version);
  }

  /**
   * Creates a AbstractRenderPlugin instance with an id, name, level, and version.
   *
   * @param id
   * @param name
   * @param level
   * @param version
   */
  public AbstractRenderPlugin(String id, String name, int level, int version) {
    super();
    // FIXME getLevelAndVersion, getElementName
    //		if (getLevelAndVersion().compareTo(Integer.valueOf(MIN_SBML_LEVEL),
    //		  Integer.valueOf(MIN_SBML_VERSION)) < 0) {
    //		  throw new LevelVersionError(getElementName(), level, version);
    //		}
    initDefaults();
  }

  /**
   * Clone constructor
   */
  public AbstractRenderPlugin(AbstractRenderPlugin obj) {
    super(obj);

    versionMinor = obj.versionMinor;
    versionMajor = obj.versionMajor;
    renderInformation = obj.renderInformation.clone();
  }

  /**
   * clones this class
   */
  @Override
  public AbstractRenderPlugin clone() {
    return new AbstractRenderPlugin(this);
  }

  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    // TODO setNamespace(RenderConstants.namespaceURI);
    versionMajor = 0;
    versionMinor = 0;
  }

  /**
   * @return the value of versionMinor
   */
  public Short getVersionMinor() {
    if (isSetVersionMinor()) {
      return versionMinor;
    }
    return null;
    // TODO new PropertyUndefinedError(RenderConstants.versionMinor, this);
  }

  /**
   * @return whether versionMinor is set
   */
  public boolean isSetVersionMinor() {
    return versionMinor != null;
  }

  /**
   * Set the value of versionMinor
   */
  public void setVersionMinor(Short versionMinor) {
    Short oldVersionMinor = this.versionMinor;
    this.versionMinor = versionMinor;
    firePropertyChange(RenderConstants.versionMinor, oldVersionMinor, this.versionMinor);
  }

  /**
   * Unsets the variable versionMinor
   * @return {@code true}, if versionMinor was set before,
   *         otherwise {@code false}
   */
  public boolean unsetVersionMinor() {
    if (isSetVersionMinor()) {
      Short oldVersionMinor = versionMinor;
      versionMinor = null;
      firePropertyChange(RenderConstants.versionMinor, oldVersionMinor, versionMinor);
      return true;
    }
    return false;
  }


  /**
   * @return the value of versionMajor
   */
  public Short getVersionMajor() {
    if (isSetVersionMajor()) {
      return versionMajor;
    }
    // This is necessary if we cannot return null here.
    return null;
    //FIXME throw new PropertyUndefinedError(RenderConstants.versionMajor, this);
  }

  /**
   * @return whether versionMajor is set
   */
  public boolean isSetVersionMajor() {
    return versionMajor != null;
  }

  /**
   * Set the value of versionMajor
   */
  public void setVersionMajor(short versionMajor) {
    Short oldVersionMajor = this.versionMajor;
    this.versionMajor = versionMajor;
    firePropertyChange(RenderConstants.versionMajor, oldVersionMajor, this.versionMajor);
  }

  /**
   * Unsets the variable versionMajor
   * @return {@code true}, if versionMajor was set before,
   *         otherwise {@code false}
   */
  public boolean unsetVersionMajor() {
    if (isSetVersionMajor()) {
      Short oldVersionMajor = versionMajor;
      versionMajor = null;
      firePropertyChange(RenderConstants.versionMajor, oldVersionMajor, versionMajor);
      return true;
    }
    return false;
  }


  /**
   * @return the value of renderInformation
   */
  public GlobalRenderInformation getRenderInformation() {
    if (isSetRenderInformation()) {
      return renderInformation;
    }
    // This is necessary if we cannot return null here.
    return null;
    //FIXME throw new PropertyUndefinedError(RenderConstants.renderInformation, this);
  }

  /**
   * @return whether renderInformation is set
   */
  public boolean isSetRenderInformation() {
    return renderInformation != null;
  }

  /**
   * Set the value of renderInformation
   */
  public void setRenderInformation(GlobalRenderInformation renderInformation) {
    GlobalRenderInformation oldRenderInformation = this.renderInformation;
    this.renderInformation = renderInformation;
    firePropertyChange(RenderConstants.renderInformation, oldRenderInformation, this.renderInformation);
  }

  /**
   * Unsets the variable renderInformation
   * @return {@code true}, if renderInformation was set before,
   *         otherwise {@code false}
   */
  public boolean unsetRenderInformation() {
    if (isSetRenderInformation()) {
      GlobalRenderInformation oldRenderInformation = renderInformation;
      renderInformation = null;
      firePropertyChange(RenderConstants.renderInformation, oldRenderInformation, renderInformation);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  // @Override
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getChildAt(int)
   */
  // @Override
  @Override
  public TreeNode getChildAt(int childIndex) {
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getChildCount()
   */
  // @Override
  @Override
  public int getChildCount() {
    return 0;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getAllowsChildren()
   */
  // @Override
  @Override
  public boolean getAllowsChildren() {
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#writeXMLAttributes()
   */
  // @Override
  @Override
  public Map<String, String> writeXMLAttributes() {
    return null;
  }

}
