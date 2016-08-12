/*
 * $IdRateRuleConstraints.java 01:12:47 roman $
 * $URLRateRuleConstraints.java $
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * Copyright (C) 2009-2016 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 5. The Babraham Institute, Cambridge, UK
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.validator.offline.constraints;

import java.util.Set;

import org.sbml.jsbml.RateRule;
import org.sbml.jsbml.Variable;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.ValidationTools;

/**
 * @author Roman
 * @since 1.2
 * @date 11.08.2016
 */
public class RateRuleConstraints extends AbstractConstraintDeclaration {

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.validator.offline.constraints.ConstraintDeclaration#
   * addErrorCodesForCheck(java.util.Set, int, int,
   * org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY)
   */
  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category) {
   
    switch (category) {
    case GENERAL_CONSISTENCY:
      set.add(CORE_20902);
      
      if (level > 1)
      {
        set.add(CORE_20904);
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
      break;
    case UNITS_CONSISTENCY:
      break;
    }
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.validator.offline.constraints.ConstraintDeclaration#
   * addErrorCodesForAttribute(java.util.Set, int, int, java.lang.String)
   */
  @Override
  public void addErrorCodesForAttribute(Set<Integer> set, int level,
    int version, String attributeName) {
    // TODO Auto-generated method stub

  }


  /*
   * (non-Javadoc)
   * @see
   * org.sbml.jsbml.validator.offline.constraints.AbstractConstraintDeclaration#
   * getValidationFunction(int)
   */
  @Override
  public ValidationFunction<?> getValidationFunction(int errorCode) {
    ValidationFunction<RateRule> func = null;

    switch (errorCode) {
    case CORE_20902:
      func = new ValidationFunction<RateRule>() {

        @Override
        public boolean check(ValidationContext ctx, RateRule r) {
          // TODO Auto-generated method stub

          if (r.isRate() && r.isSetVariable()) {

            return ValidationTools.isValidVariable(r.getVariableInstance(),
              ctx.getLevel());
          }

          return true;
        }
      };
      break;
      
    case CORE_20904:
      func = new ValidationFunction<RateRule>() {

        @Override
        public boolean check(ValidationContext ctx, RateRule r) {
          // TODO Auto-generated method stub

          Variable var = r.getVariableInstance();

          if (var != null) {
            return !var.isConstant();
          }

          return true;
        }
      };
      break;
    }

    return func;
  }

}
