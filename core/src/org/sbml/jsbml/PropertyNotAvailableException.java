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
package org.sbml.jsbml;

/**
 * An error that indicates that a property of an {@link SBase} is
 * not available for the current SBML Level/Version combination.
 * 
 * @author Andreas Dr&auml;ger
 * @since 0.8
 */
public class PropertyNotAvailableException extends PropertyException {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 3030431702957624218L;

  /**
   * Message to indicate that a certain property cannot be set for the current
   * level/version combination.
   */
  public static final String PROPERTY_UNDEFINED_EXCEPTION_MSG = "Property {0} is not defined in {1} for Level {2,number,integer} and Version {3,number,integer}.";

  /**
   * 
   * @param property
   * @param sbase
   */
  public PropertyNotAvailableException(String property, SBase sbase) {
    super(createMessage(PROPERTY_UNDEFINED_EXCEPTION_MSG, property, sbase));
  }

}
