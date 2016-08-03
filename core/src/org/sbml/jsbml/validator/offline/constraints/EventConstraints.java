/*
 * $Id$
 * $URL$
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

import java.util.HashSet;
import java.util.Set;

import org.sbml.jsbml.Event;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;;

public class EventConstraints extends AbstractConstraintDeclaration {

  @Override
  public AnyConstraint<?> createConstraints(int level, int version,
    CHECK_CATEGORY category) {

    Set<Integer> set = new HashSet<Integer>();

    switch (category) {
    case GENERAL_CONSISTENCY:

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

    return createConstraints(convertToArray(set));
  }


  @Override
  public AnyConstraint<?> createConstraints(int level, int version,
    String attributeName) {
    // TODO Auto-generated method stub
    return null;
  }


  @Override
  @SuppressWarnings("deprecation")
  public ValidationFunction<?> getValidationFunction(int errorCode) {
    ValidationFunction<?> func = null;

    switch (errorCode) {
    case CORE_21201:

      func = new ValidationFunction<Event>() {

        @Override
        public boolean check(ValidationContext ctx, Event e) {

          return e.isSetTrigger();
        }
      };

    case CORE_21203:

      func = new ValidationFunction<Event>() {

        @Override
        public boolean check(ValidationContext ctx, Event e) {

          return e.getNumEventAssignments() != 0;
        }
      };

    case CORE_21204:

      func = new ValidationFunction<Event>() {

        @Override
        public boolean check(ValidationContext ctx, Event e) {

          if (e.isSetTimeUnits()) {
            String s = e.getTimeUnits();
            UnitDefinition def = e.getTimeUnitsInstance();

            boolean isTime =
                (s == "time") || (s == "second") || (def.isVariantOfTime());

            if (ctx.isLevelAndVersionEqualTo(2, 2)) {
              return isTime;
            } else {
              return isTime || (s == "dimensionless");
            }
          }

          return e.getNumEventAssignments() != 0;
        }
      };

    case CORE_21206:

      func = new ValidationFunction<Event>() {

        @Override
        public boolean check(ValidationContext ctx, Event e) {

          if (!e.getUseValuesFromTriggerTime()) {
            return e.isSetDelay();
          }

          return true;
        }
      };

    case CORE_21207:

      func = new ValidationFunction<Event>() {

        @Override
        public boolean check(ValidationContext ctx, Event e) {

          if (e.isSetDelay()) {
            return e.isSetUseValuesFromTriggerTime();
          }

          return true;
        }
      };

    }

    return func;
  }
}
