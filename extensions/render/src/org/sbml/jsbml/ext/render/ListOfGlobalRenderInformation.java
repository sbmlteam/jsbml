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

import org.sbml.jsbml.ListOf;

/**
 * Derives from the {@link ListOf} class and contains one or more objects of type {@link GlobalRenderInformation}.
 * 
 * <p>In addition it has the optional attributes versionMajor and versionMinor
 * as well as an optional DefaultValues element that provides the default values for the {@link GlobalRenderInformation}
 * objects contained in the list.</p>
 * 
 * @author rodrigue
 *
 */
public class ListOfGlobalRenderInformation extends ListOfRenderInformation<GlobalRenderInformation> {

  

  /**
   * Creates a new {@link ListOfGlobalRenderInformation} instance 
   */
  public ListOfGlobalRenderInformation() {
    super();
    initDefaults();
  }

  /**
   * Creates a new {@link ListOfGlobalRenderInformation} instance with a level and version.
   * 
   * @param level SBML Level
   * @param version SBML Version
   */
  public ListOfGlobalRenderInformation(int level, int version) {
    super(level, version);
    initDefaults();
  }

  /**
   * Clone constructor
   */
  public ListOfGlobalRenderInformation(ListOfGlobalRenderInformation obj) {
    super(obj);
  }

  /**
   * clones this class
   */
  public ListOfGlobalRenderInformation clone() {
    return new ListOfGlobalRenderInformation(this);
  }

  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    super.initDefaults();
    setOtherListName(RenderConstants.listOfGlobalRenderInformation);
  }

}
