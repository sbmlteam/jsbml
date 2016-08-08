/*
 * $IdMathContainerConstraints.java 18:59:35 roman $
 * $URLMathContainerConstraints.java $
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

import org.sbml.jsbml.Delay;
import org.sbml.jsbml.EventAssignment;
import org.sbml.jsbml.InitialAssignment;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.Rule;
import org.sbml.jsbml.SimpleSpeciesReference;
import org.sbml.jsbml.StoichiometryMath;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;

/**
 * @author Roman
 * @since 1.2
 * @date 07.08.2016
 */
public class MathContainerConstraints extends AbstractConstraintDeclaration {

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
      break;
    case IDENTIFIER_CONSISTENCY:
      break;
    case MATHML_CONSISTENCY:
      if (level > 1) {
        set.add(CORE_10217);
      }

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
    ValidationFunction<MathContainer> func = null;

    switch (errorCode) {
    case CORE_10217:
      func = new ValidationFunction<MathContainer>() {

        @SuppressWarnings("deprecation")
        @Override
        public boolean check(ValidationContext ctx, MathContainer mc) {

          if (mc.isSetMath()) {
            // ASTNode must return a number
            if (mc instanceof KineticLaw || mc instanceof StoichiometryMath
              || mc instanceof SimpleSpeciesReference
              || mc instanceof InitialAssignment || mc instanceof Delay
              || mc instanceof EventAssignment || mc instanceof Rule) {
              return mc.getMath().isNumber();
            }
          }

          return true;
        }
      };
      break;

    }

    return func;
  }

}
