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

import org.sbml.jsbml.Trigger;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.DuplicatedMathValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.SBOValidationConstraints;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.ValidationTools;;

/**
 * @author Roman
 * @since 1.2
 */
public class TriggerConstraints extends AbstractConstraintDeclaration {

  @Override
  public void addErrorCodesForAttribute(Set<Integer> set, int level,
    int version, String attributeName, ValidationContext context) {
    // TODO Auto-generated method stub

  }


  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category, ValidationContext context) {
    switch (category) {
    case GENERAL_CONSISTENCY:
      if (level > 1) {
        set.add(CORE_21202);
      }

      if (level == 3) {
        set.add(CORE_21209);
        set.add(CORE_21226);
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
      if ((level == 2 && version > 2) || level > 2)
      {
        set.add(CORE_10716);
      }
      break;
    case UNITS_CONSISTENCY:
      break;
    }
  }


  @Override
  public ValidationFunction<?> getValidationFunction(int errorCode, ValidationContext context) {
    ValidationFunction<Trigger> func = null;

    switch (errorCode) {
    case CORE_10716:
      return SBOValidationConstraints.isMathematicalExpression;

    case CORE_21202:
      func = new ValidationFunction<Trigger>() {

        @Override
        public boolean check(ValidationContext ctx, Trigger t) {

          if (t.isSetMath()) {

            boolean isValid = ValidationTools.getDataType(t.getMath()) == ValidationTools.DT_BOOLEAN;
            
            // in SBML L3V2, number as equivalent to boolean
            if (!isValid && ctx.getLevelAndVersion().compareTo(3, 2) >= 0) {
              isValid = ValidationTools.getDataType(t.getMath()) == ValidationTools.DT_NUMBER;
            }
            
            return isValid;
          }

          return true;
        }
      };
      break;

    case CORE_21209:
      func = new DuplicatedMathValidationFunction<Trigger>();
      break;

    case CORE_21226:
      func = new UnknownAttributeValidationFunction<Trigger>() {

        @Override
        public boolean check(ValidationContext ctx, Trigger c) {
          // 'persistent' and 'initialValue'are mandatory attributes
          if (!c.isSetPersistent() || !c.isSetInitialValue()) {
            return false;
          }
          return super.check(ctx, c);
        }
      };
      break;


    }

    return func;
  }
}
