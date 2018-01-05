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
   * 
   */
  private GlobalRenderInformation renderInformation; // TODO - one or a ListOf GlobalRenderInformation?


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
   * 
   * @param renderInformation the value of renderInformation
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
