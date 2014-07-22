/*
 * $Id$
 * $URL$
 * ---------------------------------------------------------------------------- 
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML> 
 * for the latest version of JSBML and more information about SBML. 
 * 
 * Copyright (C) 2009-2014  jointly by the following organizations: 
 * 1. The University of Tuebingen, Germany 
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK 
 * 3. The California Institute of Technology, Pasadena, CA, USA 
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 5. The Babraham Institute, Cambridge, UK
 * 6. The University of Utah, Salt Lake City, UT, USA
 *
 * This library is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by 
 * the Free Software Foundation. A copy of the license agreement is provided 
 * in the file named "LICENSE.txt" included with this software distribution 
 * and also available online as <http://sbml.org/Software/JSBML/License>. 
 * ---------------------------------------------------------------------------- 
 */
package org.sbml.jsbml.ext.arrays.validator.constraints;

import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.ext.arrays.util.ArraysMath;


/**
 * @author Leandro Watanabe
 * @version $Rev$
 * @since 1.0
 * @date Jun 29, 2014
 */
public class MathDimConsistencyCheck extends ArraysConstraint{

  MathContainer mathContainer;

  public MathDimConsistencyCheck(Model model, MathContainer mathContainer) {
    super(model);
    this.mathContainer = mathContainer;  
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.arrays.validator.constraints.ArraysConstraint#check()
   */
  @Override
  public void check() {
    if(model == null || mathContainer == null) {
      return;
    }

    if(!ArraysMath.checkVectorMath(model, mathContainer)) {
      String shortMsg = "";
      logMathVectorIrregular(shortMsg);
    }
    if(!ArraysMath.checkVectorAssignment(model, mathContainer)) {
      String shortMsg = "";
      logMathVectorIrregular(shortMsg);
    }
  }

  /**
   * Log an error indicating that the first argument of the selector math is invalid.
   * 
   * @param shortMsg
   */
  private void logMathVectorIrregular(String shortMsg) {
    int code = 10211, severity = 0, category = 0, line = 0, column = 0;

    String pkg = "arrays";
    String msg = "For MathML operations with two or more operands involving MathML vectors or SBase objects with a list of Dimension"+
        "objects, the number of dimensions and their size must agree for all operands unless the operand is a scalar type"+
        "(i.e., it does not have a list of Dimension 31 objects). (Reference: SBML Level 3 Package Specification for"+
        "Arrays, Version 1, Section 3.5 on page 10.)";


    logFailure(code, severity, category, line, column, pkg, msg, shortMsg);
  }


}
