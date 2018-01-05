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
package org.sbml.jsbml.validator.offline.i18n;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.sbml.jsbml.SBMLError;
import org.sbml.jsbml.validator.offline.factory.SBMLErrorCodes;

/**
 * Contains the pre messages for each {@link SBMLError} in the English language and for SBML level 2 version 1.
 * 
 * <p>'pre' mean that this is added at the beginning of the generic error message defined in the {@link SBMLErrorMessage} bundle.
 * This part of the error is, in general, a disclaimer about the error, for example, when an error is reported for an SBML level
 * and version where it was not existing initially.</p>
 * 
 * <p>The key for each pre message is the integer defined for each {@link SBMLError} in {@link SBMLErrorCodes}.</p>
 * 
 * @see ResourceBundle
 * @author rodrigue
 * @since 1.3
 */
public class SBMLErrorPreMessage_en_EN_L3V1 extends SBMLErrorPreMessage {

  /**
   * 
   */
  private static final Map<String, String> contents = new HashMap<String, String>();
  
  static {
            
    contents.put(Integer.toString(SBMLErrorCodes.CORE_21173), "[Although SBML Level 3 Version 1 does not explicitly define the following as an error, other Levels and/or Versions of SBML do.]");
  }

  @Override
  protected Object handleGetObject(String key) {

    return contents.get(key);
  }

  @Override
  public Enumeration<String> getKeys() {
    
    return java.util.Collections.enumeration(contents.keySet());
  }

}
