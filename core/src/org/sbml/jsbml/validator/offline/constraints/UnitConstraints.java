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

import org.sbml.jsbml.Unit;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownAttributeValidationFunction;

/**
 * @author Roman
 * @since 1.2
 */
public class UnitConstraints extends AbstractConstraintDeclaration {

  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category, ValidationContext context) {

    switch (category) {
    case GENERAL_CONSISTENCY:
      
      if (level > 2) {
        set.add(CORE_20421);
      }
      
      if (level > 1) {
        if (level == 2 && version == 1) {
          break;
        }

        set.add(CORE_20412);        
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


  @Override
  public void addErrorCodesForAttribute(Set<Integer> set, int level,
    int version, String attributeName, ValidationContext context) {
    // TODO Auto-generated method stub

  }


  @Override
  public ValidationFunction<?> getValidationFunction(int errorCode, ValidationContext context) {
    ValidationFunction<Unit> func = null;

    switch (errorCode) {
    case CORE_20412:
      func = new ValidationFunction<Unit>() {

        @SuppressWarnings("deprecation")
        @Override
        public boolean check(ValidationContext ctx, Unit u) {
          
          return !u.isCelsius();
        }
      };
      break;

    case CORE_20421:
      func = new UnknownAttributeValidationFunction<Unit>() {

        @Override
        public boolean check(ValidationContext ctx, Unit unit) {
          // 'kind', 'exponent', 'scale' and 'multiplier' are mandatory attributes
          if (!unit.isSetKind() || !unit.isSetExponent()
              || !unit.isSetScale() || !unit.isSetMultiplier())
          {
            return false;
          }
          return super.check(ctx, unit);
        }
      };
      break;
      
    default:
      break;
    }

    return func;
  }

}
