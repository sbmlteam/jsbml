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

import org.sbml.jsbml.Priority;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.DuplicatedMathValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownAttributeValidationFunction;;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the {@link Priority} class.
 * 
 * @author Roman
 * @since 1.2
 */
public class PriorityConstraints extends AbstractConstraintDeclaration {

  @Override
  public void addErrorCodesForAttribute(Set<Integer> set, int level,
      int version, String attributeName, ValidationContext context) 
  {
    // TODO 
  }


  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category, ValidationContext context) {
    switch (category) {
    case GENERAL_CONSISTENCY:
      if (level == 3)
      {
        set.add(CORE_21231);
        set.add(CORE_21232);
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
      
      if (context.isLevelAndVersionGreaterEqualThan(3, 1)) {
        set.add(CORE_10565);
      }
      
      break;
    }
  }


  @Override
  public ValidationFunction<?> getValidationFunction(int errorCode, ValidationContext context) {
    ValidationFunction<Priority> func = null;

    switch (errorCode) {

      case CORE_10565: {
        func = new ValidationFunction<Priority>() {

          @Override
          public boolean check(ValidationContext ctx, Priority r) {

            if (r.isSetMath()) {

              // check that unit are equivalent to dimensionless
              return r.getDerivedUnitDefinition().isVariantOfDimensionless();
            }

            return true;
          }
        };
        break;
      }
      
    case CORE_21231:
      func = new DuplicatedMathValidationFunction<Priority>();
      break;

    case CORE_21232:
      func = new UnknownAttributeValidationFunction<Priority>();
      break;

    }

    return func;
  }
}
