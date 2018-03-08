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

package org.sbml.jsbml.validator.offline.constraints.helper;

import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.ValidationFunction;
import org.sbml.jsbml.xml.parsers.MathMLStaxParser;


/**
 * Class used to check if the 'math' mathML element is duplicated. 
 * 
 * <p>In some cases, check as well if at least one 'math' element is present</p>
 * 
 * @author rodrigue
 */
public class DuplicatedMathValidationFunction<T extends MathContainer> implements ValidationFunction<T> {

  /**
   * Indicates if the number of math element can be zero or not.
   */
  private boolean mathIsRequired;
  
  /**
   * Creates a new {@link DuplicatedElementValidationFunction} instance.
   */
  public DuplicatedMathValidationFunction() {
    mathIsRequired = false;
  }
  
  /**
   * Creates a new {@link DuplicatedElementValidationFunction} instance.
   * 
   * @param mathIsRequired boolean to indicates if the math is required of not. It allows to differentiate rules that
   * tell "must have one and only one math element", from rules that tell "may have one and only one math element". Or
   * make the math required for some elements for SBML Level 3 if required.
   */
  public DuplicatedMathValidationFunction(boolean mathIsRequired) {
    this.mathIsRequired = mathIsRequired;
  }
  
  
  @Override
  public boolean check(ValidationContext ctx, T mathContainer) {
    
    if (mathContainer.isSetMath()) {
      if (mathContainer.isSetUserObjects() && mathContainer.getUserObject(MathMLStaxParser.JSBML_MATH_COUNT) != null) {
        int nbMath = ((Number) mathContainer.getUserObject(MathMLStaxParser.JSBML_MATH_COUNT)).intValue();
    
        return nbMath == 1;                  
      }
    } else if (mathContainer.getLevelAndVersion().compareTo(3, 2) < 0 || mathIsRequired) {
      return false;
    }
    
    return true;
  }
}
