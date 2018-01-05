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

import org.sbml.jsbml.Delay;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.DuplicatedMathValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.SBOValidationConstraints;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownAttributeValidationFunction;;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the {@link Delay} class.
 * 
 * @author Roman
 * @since 1.2
 */
public class DelayConstraints extends AbstractConstraintDeclaration {

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
      if (level == 3) {
        set.add(CORE_21210);
        set.add(CORE_21227);
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
        set.add(CORE_10717);
      }
      break;
    case UNITS_CONSISTENCY:
      set.add(CORE_10551);
      
      break;
    }

  }


  @Override
  public ValidationFunction<?> getValidationFunction(int errorCode, ValidationContext context) {
    ValidationFunction<Delay> func = null;

    switch (errorCode) {
      
      case CORE_10551: {
        func = new ValidationFunction<Delay>() {

          @SuppressWarnings("deprecation")
          @Override
          public boolean check(ValidationContext ctx, Delay d) {

            if (d.isSetMath() && (d.getModel().isSetTimeUnits() || ctx.getLevel() < 3)) {

              UnitDefinition timeUnits = d.getModel().getTimeUnitsInstance();
              UnitDefinition delayUnits = d.getDerivedUnitDefinition();

              if (ctx.isLevelAndVersionLesserEqualThan(2, 2) && d.getParent().isSetTimeUnits()) {
                timeUnits = d.getParent().getTimeUnitsInstance();
              }
              
              if (delayUnits.isInvalid()) {
                return true;
              }

              // check that the units are equivalent
              return UnitDefinition.areIdentical(delayUnits, timeUnits);
            }

            return true;
          }
        };
        break;
      }
  
    case CORE_10717:
      return SBOValidationConstraints.isMathematicalExpression;
      
    case CORE_21210:
      func = new DuplicatedMathValidationFunction<Delay>();
      break;

    case CORE_21227:
      func = new UnknownAttributeValidationFunction<Delay>();
      break;

    }

    return func;
  }
}
