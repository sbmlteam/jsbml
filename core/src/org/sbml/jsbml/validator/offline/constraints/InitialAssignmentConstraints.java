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

import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.InitialAssignment;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;;

public class InitialAssignmentConstraints
extends AbstractConstraintDeclaration {

  @Override
  public AnyConstraint<?> createConstraints(int level, int version,
    CHECK_CATEGORY category) {

    Set<Integer> set = new HashSet<Integer>();

    switch (category) {
    case GENERAL_CONSISTENCY:
      if (level == 2 && version > 1)
      {
        addRangeToSet(set, CORE_20801, CORE_20803);
      }
      else if (level == 3)
      {
        addRangeToSet(set, CORE_20801, CORE_20805);
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
    ValidationFunction<InitialAssignment> func = null;

    switch (errorCode) {
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
      func = new ValidationFunction<InitialAssignment>() {

        @Override
        public boolean check(ValidationContext ctx, InitialAssignment ia) {
          return ia.isSetMath();
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

    }

    return func;
  }
}
