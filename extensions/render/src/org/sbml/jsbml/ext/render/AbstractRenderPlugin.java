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
package org.sbml.jsbml.ext.render;

import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.AbstractSBasePlugin;

/**
 * @author Jakob Matthes
 * @since 1.0
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
   * @deprecated This field is not specified by the render-package-specification
   */
  @Deprecated
  private GlobalRenderInformation renderInformation; 
  
  /**
   * Creates an AbstractRenderPlugin instance
   * 
   * @param extendedElement the {@link SBase} that is extended
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
   * 
   * @param obj the {@link AbstractRenderPlugin} to clone
   */
  public AbstractRenderPlugin(AbstractRenderPlugin obj) {
    super(obj);

    // TODO 2020/02: remove this already? Definitely remove when removing the deprecated
    // methods!
    if (obj.isSetRenderInformation()) {
      setRenderInformation(obj.getRenderInformation().clone());
    }
  }


  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
  }

  
  /**
   * @deprecated This goes beyond/is besides the render-package's specification
   *             and will be removed in a future release. Use the methods
   *             {@link RenderLayoutPlugin#getListOfLocalRenderInformation()},
   *             {@link RenderLayoutPlugin#getLocalRenderInformation(int)}, and
   *             {@link RenderListOfLayoutsPlugin#getListOfGlobalRenderInformation()}
   *             respectively
   * @return the value of renderInformation
   */
  @Deprecated
  public GlobalRenderInformation getRenderInformation() {
    if (isSetRenderInformation()) {
      return renderInformation;
    }

    return null;
  }

  
  /**
   * @deprecated This goes beyond/is besides the render-package's specification
   *             and will be removed in a future release. Use the
   *             {@link LocalRenderInformation}-related methods of
   *             {@link RenderLayoutPlugin}, and the
   *             {@link GlobalRenderInformation}-related methods of
   *             {@link RenderListOfLayoutsPlugin} instead.
   * @return whether renderInformation is set
   */
  @Deprecated
  public boolean isSetRenderInformation() {
    return renderInformation != null;
  }

  /**
   * @deprecated This goes beyond/is besides the render-package's specification
   *             and will be removed in a future release. Use the
   *             {@link LocalRenderInformation}-related methods of
   *             {@link RenderLayoutPlugin}, and the
   *             {@link GlobalRenderInformation}-related methods of
   *             {@link RenderListOfLayoutsPlugin} instead.
   * 
   * Set the value of renderInformation
   * 
   * @param renderInformation the value of renderInformation
   */
  @Deprecated
  public void setRenderInformation(GlobalRenderInformation renderInformation) {
    GlobalRenderInformation oldRenderInformation = this.renderInformation;
    this.renderInformation = renderInformation;
    firePropertyChange(RenderConstants.renderInformation, oldRenderInformation, this.renderInformation);
  }

  
  /**
   * @deprecated This goes beyond/is besides the render-package's specification
   *             and will be removed in a future release. Use the
   *             {@link LocalRenderInformation}-related methods of
   *             {@link RenderLayoutPlugin}, and the
   *             {@link GlobalRenderInformation}-related methods of
   *             {@link RenderListOfLayoutsPlugin} instead.
   *             Unsets the variable renderInformation
   * @return {@code true}, if renderInformation was set before,
   *         otherwise {@code false}
   */
  @Deprecated
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
    return null; // TODO 2015/04: implement
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getChildCount()
   */
  // @Override
  @Override
  public int getChildCount() {
    return 0; // TODO 2015/04: implement
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getAllowsChildren()
   */
  // @Override
  @Override
  public boolean getAllowsChildren() {
    return false; // TODO 2015/04: implement
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#writeXMLAttributes()
   */
  // @Override
  @Override
  public Map<String, String> writeXMLAttributes() {
    return null; // TODO 2015/04: implement
  }

}
