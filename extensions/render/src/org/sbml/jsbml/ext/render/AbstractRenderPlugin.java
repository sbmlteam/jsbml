/*
 * $Id: AbstractRenderPlugin.java 2180 2015-04-08 15:48:28Z niko-rodrigue $
 * $URL: svn://svn.code.sf.net/p/jsbml/code/trunk/extensions/render/src/org/sbml/jsbml/ext/render/AbstractRenderPlugin.java $
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
package org.sbml.jsbml.ext.render;

import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.AbstractSBasePlugin;

/**
 * @author Jakob Matthes
 * @version $Rev: 2180 $
 * @since 1.0
 * @date 16.05.2012
 */
public abstract class AbstractRenderPlugin extends AbstractSBasePlugin {

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getPackageName()
   */
  @Override
  public String getPackageName() {
    return RenderConstants.shortLabel;
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
  /**
   * 
   */
  private Short versionMajor; // TODO - versionMajor and versionMinor are attributes of ListOf! They are int in the specs!
  /**
   * 
   */
  private Short versionMinor;
  /**
   * 
   */
  private GlobalRenderInformation renderInformation; // TODO - one or a ListOf GlobalRenderInformation?


  /**
   * Creates an AbstractRenderPlugin instance
   * @param extendedElement
   */
  public AbstractRenderPlugin(SBase extendedElement) {
    super(extendedElement);
    initDefaults();
  }

  /**
   * Creates a AbstractRenderPlugin instance.
   *
   */
  public AbstractRenderPlugin() {
    super();
    initDefaults();
  }


  /**
   * Clone constructor
   * @param obj
   */
  public AbstractRenderPlugin(AbstractRenderPlugin obj) {
    super(obj);

    if (obj.isSetVersionMinor()) {
      setVersionMinor(obj.getVersionMinor());
    }
    if (obj.isSetVersionMajor()) {
      setVersionMajor(obj.getVersionMajor());
    }
    if (obj.isSetRenderInformation()) {
      setRenderInformation(obj.getRenderInformation().clone());
    }
  }


  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    setNamespace(RenderConstants.namespaceURI); // TODO - removed once the mechanism are in place to set package version and namespace
    versionMajor = 0;
    versionMinor = 0;
  }

  // TODO - return and input values should be int
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
   * @param versionMinor
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
   * @param versionMajor
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

    return null;
  }

  /**
   * @return whether renderInformation is set
   */
  public boolean isSetRenderInformation() {
    return renderInformation != null;
  }

  /**
   * Set the value of renderInformation
   * @param renderInformation
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
    return false; // TODO - implement
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getChildAt(int)
   */
  // @Override
  @Override
  public TreeNode getChildAt(int childIndex) {
    return null; // TODO - implement
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getChildCount()
   */
  // @Override
  @Override
  public int getChildCount() {
    return 0; // TODO - implement
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getAllowsChildren()
   */
  // @Override
  @Override
  public boolean getAllowsChildren() {
    return false; // TODO - implement
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#writeXMLAttributes()
   */
  // @Override
  @Override
  public Map<String, String> writeXMLAttributes() {
    return null; // TODO - implement
  }

}
