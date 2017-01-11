/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2017 jointly by the following organizations:
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

package org.sbml.jsbml.validator.offline.constraints.helper;

import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.ValidationFunction;
import org.sbml.jsbml.xml.parsers.MathMLStaxParser;


/**
 * @author rodrigue
 *
 */
public class DuplicatedMathValidationFunction<T extends MathContainer> implements ValidationFunction<T> {

  @Override
  public boolean check(ValidationContext ctx, T mathContainer) {
    
    if (mathContainer.isSetMath()) {
      if (mathContainer.isSetUserObjects() && mathContainer.getUserObject(MathMLStaxParser.JSBML_MATH_COUNT) != null) {
        int nbMath = (int) mathContainer.getUserObject(MathMLStaxParser.JSBML_MATH_COUNT);
    
        return nbMath == 1;                  
      }
    } else if (mathContainer.getLevelAndVersion().compareTo(3, 2) < 0) { // TODO - get the level and version from the ValidationContext ?
      // math is mandatory before SBML L3V2
      return false;
    }
    
    return true;
  }
}
