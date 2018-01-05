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

/**
 * @author Alex Thomas
 * @author Andreas Dr&auml;ger
 * @since 1.0
 */
public class SpatialSymbolReference extends ParameterType {

  
  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -8906622500258765056L;

  /**
   * Creates a new {@link SpatialSymbolReference} instance.
   */
  public SpatialSymbolReference() {
    super();
  }

  /**
   * Creates a new {@link SpatialSymbolReference} instance.
   * 
   * @param ssr the instance to clone
   */
  public SpatialSymbolReference(SpatialSymbolReference ssr) {
    super(ssr);
  }

  /**
   * Creates a new {@link SpatialSymbolReference} instance.
   * 
   * @param level the SBML level
   * @param version the SBML version
   */
  public SpatialSymbolReference(int level, int version) {
    super(level, version);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.ParameterType#clone()
   */
  @Override
  public SpatialSymbolReference clone() {
    return new SpatialSymbolReference(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.ParameterType#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    return super.equals(object);
  }

}
