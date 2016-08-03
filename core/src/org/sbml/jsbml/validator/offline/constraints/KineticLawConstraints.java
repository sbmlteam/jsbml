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

import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;;

public class KineticLawConstraints extends AbstractConstraintDeclaration {

  @Override
  public AnyConstraint<?> createConstraints(int level, int version,
    CHECK_CATEGORY category) {

    Set<Integer> set = new HashSet<Integer>();

    switch (category) {
    case GENERAL_CONSISTENCY:
      set.add(CORE_21117);
      set.add(CORE_21121);
      set.add(CORE_21130);
      if (level == 2)
      {
        set.add(CORE_21124);
        if (version > 1)
        {
          addRangeToSet(set, CORE_21125, CORE_21126);
        }
      }
      else if (level == 3)
      {
        addRangeToSet(set, CORE_21127, CORE_21130);
        set.add(CORE_21132);
        set.add(CORE_21150);
        set.add(CORE_21151);
        set.add(CORE_21172);
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
    ValidationFunction<KineticLaw> func = null;

    switch (errorCode) {
    case CORE_21125:
      func = new ValidationFunction<KineticLaw>() {

        @Override
        public boolean check(ValidationContext ctx, KineticLaw kl) {

          return !kl.isSetSubstanceUnits();
        }
      };

    case CORE_21126:
      func = new ValidationFunction<KineticLaw>() {

        @Override
        public boolean check(ValidationContext ctx, KineticLaw kl) {

          return !kl.isSetTimeUnits();
        }
      };

    case CORE_21130:
      func = new ValidationFunction<KineticLaw>() {

        @Override
        public boolean check(ValidationContext ctx, KineticLaw kl) {
          return kl.isSetMath();
        }
      };

    }

    return func;
  }
}
