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

import org.sbml.jsbml.Model;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;;

public class ModelConstraints extends AbstractConstraintDeclaration {

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
      set.add(CORE_20204);

      if (level == 3) {
        addRangeToSet(set, CORE_20205, CORE_20232);
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
  @SuppressWarnings("deprecation")
  public ValidationFunction<?> getValidationFunction(int errorCode) {
    ValidationFunction<Model> func = null;

    switch (errorCode) {

    case CORE_20203:
      func = new ValidationFunction<Model>() {

        public boolean check(ValidationContext ctx, Model m) {
          boolean success = true;

          if (m.getCompartmentCount() == 0) {
            success = success && !m.isSetListOfCompartments();
          }

          if (m.getCompartmentTypeCount() == 0) {
            success = success && !m.isSetListOfCompartmentTypes();
          }

          if (m.getConstraintCount() == 0) {
            success = success && !m.isSetListOfConstraints();
          }

          if (m.getEventCount() == 0) {
            success = success && !m.isSetListOfEvents();
          }

          if (m.getFunctionDefinitionCount() == 0) {
            success = success && !m.isSetListOfFunctionDefinitions();
          }

          if (m.getInitialAssignmentCount() == 0) {
            success = success && !m.isSetListOfInitialAssignments();
          }

          if (m.getParameterCount() == 0) {
            success = success && !m.isSetListOfParameters();
          }

          if (m.getReactionCount() == 0) {
            success = success && !m.isSetListOfReactions();
          }

          if (m.getRuleCount() == 0) {
            success = success && !m.isSetListOfRules();
          }

          if (m.getSpeciesCount() == 0) {
            success = success && !m.isSetListOfSpecies();
          }

          if (m.getSpeciesTypeCount() == 0) {
            success = success && !m.isSetListOfSpeciesTypes();
          }

          if (m.getUnitDefinitionCount() == 0) {
            success = success && !m.isSetListOfUnitDefinitions();
          }

          return success;
        };
      };

    case CORE_20204:
      func = new ValidationFunction<Model>() {

        @Override
        public boolean check(ValidationContext ctx, Model m) {
          if (m.getNumSpecies() > 0) {
            return m.getNumCompartments() > 0;
          }

          return true;
        }
      };
      break;

    case CORE_20216:
      func = new ValidationFunction<Model>() {

        @Override
        public boolean check(ValidationContext ctx, Model m) {
          if (m.isSetConversionFactor()) {
            return m.getConversionFactorInstance() != null;
          }
          return true;
        }
      };
      break;

    }

    return func;
  }
}
