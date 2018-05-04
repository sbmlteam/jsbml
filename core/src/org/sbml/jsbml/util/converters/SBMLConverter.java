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
package org.sbml.jsbml.util.converters;

import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;

/**
 * @author rodrigue
 * @since 1.3
 */
public interface SBMLConverter {
  
  /**
   * Converts a given {@link SBMLDocument}.
   * 
   * @param doc the document that need to be converted, it should not be modified
   * @return the converted {@link SBMLDocument}
   * @throws SBMLException if a problem occurred during conversion
   */
  public SBMLDocument convert(SBMLDocument doc) throws SBMLException;
  
  /**
   * Sets options
   * 
   * @param option the option that should be set
   */
  public void setOption(String name, String value);
}
