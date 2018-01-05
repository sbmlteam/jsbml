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

import org.sbml.jsbml.AssignmentRule;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.EventAssignment;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.Rule;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.Variable;
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
public class EventAssignmentConstraints extends AbstractConstraintDeclaration {

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
        set.add(CORE_21211);
        set.add(CORE_21212);
      }

      if (level == 3) {
        set.add(CORE_21213);
        set.add(CORE_21214);
      }
      break;
    case IDENTIFIER_CONSISTENCY:
      set.add(CORE_10306);
      break;
    case MATHML_CONSISTENCY:
      break;
    case MODELING_PRACTICE:
      break;
    case OVERDETERMINED_MODEL:
      break;
    case SBO_CONSISTENCY:
      if ((level == 2 && version > 1) || level > 2)
      {
        set.add(CORE_10711);
      }
      break;
    case UNITS_CONSISTENCY:
      addRangeToSet(set, CORE_10561, CORE_10564);
      break;
    }
  }


  @Override
  public ValidationFunction<?> getValidationFunction(int errorCode, ValidationContext context) {
    ValidationFunction<EventAssignment> func = null;

    switch (errorCode) {
    case CORE_10306:
      func = new ValidationFunction<EventAssignment>() {
        
        
        @Override
        public boolean check(ValidationContext ctx, EventAssignment ea) {
          Model m = ea.getModel();
          
          if (m != null)
          {
            if (m.getRuleCount() > 0) {
              for (Rule r : m.getListOfRules())
              {
                if (r.isAssignment())
                {
                  AssignmentRule ar = (AssignmentRule) r;

                  if (ar.getVariable().equals(ea.getVariable()))
                  {
                    return false;
                  }
                }
              }
            }
          }
          
          return true;
        }
      };
      break;
      
    case CORE_10561:
      func = new ValidationFunction<EventAssignment>() {

        @Override
        public boolean check(ValidationContext ctx, EventAssignment r) {

          Variable var = r.getVariableInstance();

          if (var != null && var instanceof Compartment) {

            // check that unit from rule are equivalent to the compartment unit
            return ValidationTools.haveEquivalentUnits(ctx, r, var);
          }

          return true;
        }
      };
      break;

    case CORE_10562:
      func = new ValidationFunction<EventAssignment>() { // TODO - check

        @Override
        public boolean check(ValidationContext ctx, EventAssignment r) {

          Variable var = r.getVariableInstance();

          if (var != null && var instanceof Species) {

            // check that unit from assignment are equivalent to the species unit
            return ValidationTools.haveEquivalentUnits(ctx, r, var);
          }

          return true;
        }
      };
      break;

    case CORE_10563:
      func = new ValidationFunction<EventAssignment>() {

        @Override
        public boolean check(ValidationContext ctx, EventAssignment r) {

          Variable var = r.getVariableInstance();

          if (var != null && var instanceof Parameter) {

            // check that unit from rule are equivalent to the parameter unit
            return ValidationTools.haveEquivalentUnits(ctx, r, var);
          }

          return true;
        }
      };
      break;

    case CORE_10564:
      func = new ValidationFunction<EventAssignment>() {

        @Override
        public boolean check(ValidationContext ctx, EventAssignment r) {

          Variable var = r.getVariableInstance();

          if (var != null && var instanceof SpeciesReference) {

            // check that unit from rule are equivalent to the stoichiometry unit: dimensionless
            return ValidationTools.haveEquivalentUnits(ctx, r, var);
          }

          return true;
        }
      };
      break;

    case CORE_10711:
      return SBOValidationConstraints.isMathematicalExpression;
      
    case CORE_21211:
      func = new ValidationFunction<EventAssignment>() {

        @Override
        public boolean check(ValidationContext ctx, EventAssignment ea) {
          
          if (ea.isSetVariable()) {
            Variable var = ea.getVariableInstance();
            
           
            
            if (var == null)
            {
              return false;
            }
            
            boolean isComSpecOrPar = (var instanceof Compartment)
                || (var instanceof Species) || (var instanceof Parameter);

            if (ctx.getLevel() == 2) {
              return isComSpecOrPar;
            } else {
              return isComSpecOrPar || (var instanceof SpeciesReference);
            }

          }

          return true;
        }
      };
      break;
      
    case CORE_21212:
      func = new ValidationFunction<EventAssignment>() {

        @Override
        public boolean check(ValidationContext ctx, EventAssignment ea) {

          if (ea.isSetVariable()) {
            Variable var = ea.getVariableInstance();

            return var != null && !var.isConstant();
          }

          return true;
        }
      };
      break;
      
    case CORE_21213:
      func = new DuplicatedMathValidationFunction<EventAssignment>();
      break;

    case CORE_21214:
      func = new UnknownAttributeValidationFunction<EventAssignment>() {
        
        @Override
        public boolean check(ValidationContext ctx, EventAssignment c) {
          // variable is a mandatory attribute
          if (!c.isSetVariable()) {
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
