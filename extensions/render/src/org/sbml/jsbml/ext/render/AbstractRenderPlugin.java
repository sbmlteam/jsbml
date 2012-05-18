/*
 * $Id$
 * $URL$
 *
 * ---------------------------------------------------------------------------- 
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML> 
 * for the latest version of JSBML and more information about SBML. 
 * 
 * Copyright (C) 2009-2012 jointly by the following organizations: 
 * 1. The University of Tuebingen, Germany 
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK 
 * 3. The California Institute of Technology, Pasadena, CA, USA 
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
import org.sbml.jsbml.ext.SBasePlugin;


/**
 * @author Jakob Matthes
 * @version $Rev$
 * @since 1.0
 * @date 16.05.2012
 */
public class AbstractRenderPlugin extends AbstractSBasePlugin {
  /**
   * 
   */
  private static final long serialVersionUID = -4225426173177528441L;
  Short versionMajor;
  Short versionMinor;
  GlobalRenderInformation renderInformation;
  
  /**
   * Creates an AbstractRenderPlugin instance 
   */
  public AbstractRenderPlugin() {
    super();
    initDefaults();
  }

  public AbstractRenderPlugin(SBase extendedElement) {
    super(extendedElement);
    initDefaults();
  }

  /**
   * Clone constructor
   */
  public AbstractRenderPlugin(AbstractRenderPlugin obj) {
    super();
    // TODO: copy all class attributes, e.g.:
    // bar = obj.bar;
  }


  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
//    addNamespace(RenderConstants.namespaceURI);
    // TODO: init default values here if necessary, e.g.:
    // bar = null;
  }

  public static final int MIN_SBML_LEVEL = 3;
                                              public static final int MIN_SBML_VERSION = 1;
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    // TODO Auto-generated method stub
    return false;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getChildAt(int)
   */
  @Override
  public TreeNode getChildAt(int childIndex) {
    // TODO Auto-generated method stub
    return null;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getChildCount()
   */
  @Override
  public int getChildCount() {
    // TODO Auto-generated method stub
    return 0;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    // TODO Auto-generated method stub
    return false;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    // TODO Auto-generated method stub
    return null;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.AbstractSBasePlugin#clone()
   */
  @Override
  public SBasePlugin clone() {
    // TODO Auto-generated method stub
    return null;
  }
}
