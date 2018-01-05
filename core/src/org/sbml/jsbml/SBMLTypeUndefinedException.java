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

import java.text.MessageFormat;

import org.sbml.jsbml.util.Message;

/**
 * This error can be thrown whenever a data type is being initialized that is
 * undefined for a certain SBML Level/Version combination. Examples are the
 * classes {@link SpeciesType} or {@link CompartmentType} that can only be used
 * in certain versions of SBML Level 2.
 * 
 * @author Andreas Dr&auml;ger
 * @since 1.0
 */
public class SBMLTypeUndefinedException extends SBMLError {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -6120311866715719809L;

  /**
   * 
   * @param clazz
   * @param level
   * @param version
   */
  public SBMLTypeUndefinedException(Class<?> clazz, int level, int version) {
    super();
    Message message = new Message();
    message.setMessage(MessageFormat.format(
      "The element \"{0}\" is not defined in SBML Level {1,number,integer} Version {2,number,integer}.",
      clazz.getSimpleName(), level, version));
    setMessage(message);
  }

}
