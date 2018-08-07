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

import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.ExplicitRule;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.AssignmentCycleValidation;;

/**
 * @author Roman
 * @since 1.2
 */
public class ExplicitRuleConstraints extends AbstractConstraintDeclaration {

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

      if (level == 1) {
        set.add(CORE_99106);
      } else if (level == 2) {
        
        
        if (version == 5)
        {
          set.add(CORE_20911);
        }
        
        if (version > 1) {
          set.add(CORE_20906);
        } else // l2v1
        {
          set.add(CORE_99106);
        }
      } else if (level == 3) {
        
        addRangeToSet(set, CORE_20905, CORE_20910);
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
  public ValidationFunction<?> getValidationFunction(int errorCode, ValidationContext context) {
    ValidationFunction<ExplicitRule> func = null;

    switch (errorCode) {

    case CORE_20906:
      return new AssignmentCycleValidation();
      
    case CORE_20911:
      func = new AbstractValidationFunction<ExplicitRule>() {

        @Override
        public boolean check(ValidationContext ctx, ExplicitRule r) {
          Model m = r.getModel();
          String var = r.getVariable();
          
          if (r.isSetVariable() && m != null) {
            Compartment c = m.getCompartment(var);

            if (c != null && c.isSetSpatialDimensions() && c.getSpatialDimensions() == 0) {
              
              ValidationConstraint.logError(ctx, CORE_20911, r, r.getElementName(), r.getVariable());
              return false;
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
