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

import org.sbml.jsbml.LevelVersionError;
import org.sbml.jsbml.ListOf;


/**
 * @author Eugen Netz
 * @author Alexander Diamantikos
 * @author Jakob Matthes
 * @author Jan Rudolph
 * @version $Rev$
 * @since 1.0
 * @date 08.05.2012
 */
public class GlobalRenderInformation extends RenderInformationBase {
	/**
   * Creates an GlobalRenderInformation instance 
   */
  public GlobalRenderInformation() {
    super();
    initDefaults();
  }


  /**
   * Creates a GlobalRenderInformation instance with an id. 
   * 
   * @param id
   */
  public GlobalRenderInformation(String id) {
    super(id);
    initDefaults();
  }


  /**
   * Creates a GlobalRenderInformation instance with a level and version. 
   * 
   * @param level
   * @param version
   */
  public GlobalRenderInformation(int level, int version) {
    this(null, null, level, version);
  }


  /**
   * Creates a GlobalRenderInformation instance with an id, level, and version. 
   * 
   * @param id
   * @param level
   * @param version
   */
  public GlobalRenderInformation(String id, int level, int version) {
    this(id, null, level, version);
  }


  /**
   * Creates a GlobalRenderInformation instance with an id, name, level, and version. 
   * 
   * @param id
   * @param name
   * @param level
   * @param version
   */
  public GlobalRenderInformation(String id, String name, int level, int version) {
    super(id, name, level, version);
    if (getLevelAndVersion().compareTo(Integer.valueOf(MIN_SBML_LEVEL),
      Integer.valueOf(MIN_SBML_VERSION)) < 0) {
      throw new LevelVersionError(getElementName(), level, version);
    }
    initDefaults();
  }


  /**
   * Clone constructor
   */
  public GlobalRenderInformation(GlobalRenderInformation obj) {
    super(obj);
    // TODO: copy all class attributes, e.g.:
    // bar = obj.bar;
  }


  /**
   * clones this class
   */
  @Override
  public GlobalRenderInformation clone() {
    return new GlobalRenderInformation(this);
  }


  /**
   * Initializes the default values using the namespace.
   */
  @Override
  public void initDefaults() {
    addNamespace(RenderConstants.namespaceURI);
    // TODO: init default values here if necessary, e.g.:
    // bar = null;
  }

  // TODO: Move to RenderConstants
  public static final int MIN_SBML_LEVEL = 3;
  public static final int MIN_SBML_VERSION = 1;

  /**
   * 
   */
  private static final long serialVersionUID = 855680727119080659L;
  
  //TODO The same as LocalRenderInformation, but with class GlobalStyle
  //GlobalStyle equals Style?
  ListOf<Style> listOfStyles;
  
  
}
