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

import java.util.Set;

import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.InitialAssignment;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.AssignmentCycleValidation;
import org.sbml.jsbml.validator.offline.constraints.helper.DuplicatedMathValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.SBOValidationConstraints;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownAttributeValidationFunction;;

/**
 * @author Roman
 * @since 1.2
 * @date 04.08.2016
 */
public class InitialAssignmentConstraints
extends AbstractConstraintDeclaration {

  @Override
  public void addErrorCodesForAttribute(Set<Integer> set, int level,
    int version, String attributeName) {
    // TODO Auto-generated method stub

  }


  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category) {

    switch (category) {
    case GENERAL_CONSISTENCY:
      if (level == 2 && version > 1) {
        addRangeToSet(set, CORE_20801, CORE_20803);
        set.add(CORE_20906);
        
        if (version == 5)
        {
          set.add(CORE_20806);
        }
      } else if (level == 3) {
        addRangeToSet(set, CORE_20801, CORE_20805);
        set.add(CORE_20906);
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
      if (level > 2 || (level == 2 && version > 1))
      {
        set.add(CORE_10704);
      }
      break;
    case UNITS_CONSISTENCY:
      break;
    }
  }


  @Override
  @SuppressWarnings("deprecation")
  public ValidationFunction<?> getValidationFunction(int errorCode) {
    ValidationFunction<InitialAssignment> func = null;

    switch (errorCode) {
    case CORE_10704:
      return SBOValidationConstraints.isMathematicalExpression;
      
    case CORE_20801:
      func = new ValidationFunction<InitialAssignment>() {

        @Override
        public boolean check(ValidationContext ctx, InitialAssignment ia) {
          Model m = ia.getModel();

          if (ia.isSetSymbol() && m != null) {

            String symbol = ia.getSymbol();

            boolean checkL2 = (m.getCompartment(symbol) != null)
                || (m.getSpecies(symbol) != null)
                || (m.getParameter(symbol) != null);

            if (ctx.getLevel() == 2) {
              return checkL2;
            } else {
              return checkL2
                  || (m.findNamedSBase(symbol) instanceof SpeciesReference);
            }
          }

          return false;
        }
      };
      break;
      
    case CORE_20804:
      func =  new DuplicatedMathValidationFunction<InitialAssignment>();
      break;

    case CORE_20805:
      func = new UnknownAttributeValidationFunction<InitialAssignment>() {
        
        @Override
        public boolean check(ValidationContext ctx, InitialAssignment c) {
          // symbol is a mandatory attribute
          if (!c.isSetSymbol()) {
            return false;
          }
          return super.check(ctx, c);
        }
      };
      break;
      
    case CORE_20806:
      func = new ValidationFunction<InitialAssignment>() {

        @Override
        public boolean check(ValidationContext ctx, InitialAssignment ia) {
          
          Model m = ia.getModel();

          if (ia.isSetSymbol() && m != null) {
            String s = ia.getSymbol();

            Compartment c = m.getCompartment(s);

            if (c != null) {
              return c.getSpatialDimensions() != 0;
            }
          }

          return true;
        }
      };
      break;
    case CORE_20906:
      return new AssignmentCycleValidation();
    }

    return func;
  }
}
