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
package org.sbml.jsbml.validator.offline.constraints;

import java.util.Set;

import org.sbml.jsbml.Rule;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.SBOValidationConstraints;
import org.sbml.jsbml.xml.parsers.MathMLStaxParser;


/**
 * @author Roman
 * @since 1.2
 */
public class RuleConstraints extends AbstractConstraintDeclaration {

  /* (non-Javadoc)
   * @see org.sbml.jsbml.validator.offline.constraints.ConstraintDeclaration#addErrorCodesForCheck(java.util.Set, int, int, org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY)
   */
  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category, ValidationContext context) {
    
    switch (category) {
    case GENERAL_CONSISTENCY:
      
      if (level > 2) {
        set.add(CORE_20907);
      }
      break;
    case IDENTIFIER_CONSISTENCY:
      break;
    case MATHML_CONSISTENCY:
      break;
    case MODELING_PRACTICE:
      break;
    case OVERDETERMINED_MODEL:
      break;
    case SBO_CONSISTENCY:
      if ((level == 2 && version > 1) || level > 2)
      {
        set.add(CORE_10705);
      }
      break;
    case UNITS_CONSISTENCY:
      break;
    }
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.validator.offline.constraints.ConstraintDeclaration#addErrorCodesForAttribute(java.util.Set, int, int, java.lang.String)
   */
  @Override
  public void addErrorCodesForAttribute(Set<Integer> set, int level,
    int version, String attributeName, ValidationContext context) {
    // TODO Auto-generated method stub

  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.validator.offline.constraints.AbstractConstraintDeclaration#getValidationFunction(int)
   */
  @Override
  public ValidationFunction<?> getValidationFunction(int errorCode, ValidationContext context) {
    ValidationFunction<Rule> func = null;
    
    switch (errorCode) {
      
      case CORE_20907: {
        func = new ValidationFunction<Rule>() {

          @Override
          public boolean check(ValidationContext ctx, Rule rule) {

            if (rule.isSetMath()) {
              if (rule.isSetUserObjects() && rule.getUserObject(MathMLStaxParser.JSBML_MATH_COUNT) != null) {
                int nbMath = ((Number) rule.getUserObject(MathMLStaxParser.JSBML_MATH_COUNT)).intValue();

                return nbMath == 1;                  
              }
            } else if (ctx.isLevelAndVersionLessThan(3, 2)) {
              // math is mandatory before SBML L3V2
              return false;
            }

            return true;
          }
        };
        break;
      }

    case CORE_10705:
      return SBOValidationConstraints.isMathematicalExpression;

    }
    
    return func;
  }

}
