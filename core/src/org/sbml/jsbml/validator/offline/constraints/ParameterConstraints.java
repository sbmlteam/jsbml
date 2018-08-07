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

import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.Rule;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.SBOValidationConstraints;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.ValidationTools;;

/**
 * @author Roman
 * @since 1.2
 */
public class ParameterConstraints extends AbstractConstraintDeclaration {

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

      if (level > 2 || (level == 2 && version > 1)) {
        set.add(CORE_20412);
      }

      if (level > 2) {
        set.add(CORE_20706);
      }

      break;
    case IDENTIFIER_CONSISTENCY:
      break;
    case MATHML_CONSISTENCY:
      break;
    case MODELING_PRACTICE:
      if (context.isEnabledCategory(CHECK_CATEGORY.UNITS_CONSISTENCY)) {
        set.add(CORE_80701);
      }
      set.add(CORE_80702);
      break;
    case OVERDETERMINED_MODEL:
      break;
    case SBO_CONSISTENCY:
      if ((level == 2 && version > 1) || level > 2) {
        set.add(CORE_10703);
      }
      break;
    case UNITS_CONSISTENCY:
      set.add(CORE_20701);
      if (level > 2){
        set.add(CORE_99508);
        set.add(CORE_20702);
      }
      break;
    }
  }


  @Override
  @SuppressWarnings("deprecation")
  public ValidationFunction<?> getValidationFunction(int errorCode, ValidationContext context) {
    ValidationFunction<Parameter> func = null;

    switch (errorCode) {
    case CORE_10703:
      return SBOValidationConstraints.isQuantitativeParameter;

    case CORE_20412:
      func = new ValidationFunction<Parameter>() {

        @Override
        public boolean check(ValidationContext ctx, Parameter p) {

          UnitDefinition def = p.getUnitsInstance();

          if (def != null && def.isSetListOfUnits()
            && def.getUnitCount() == 1) {
            // Celsius not allowed

            return !def.getUnit(0).isCelsius();
          }

          return true;
        }
      };
      break;

    case CORE_20701:
      func = new AbstractValidationFunction<Parameter>() {

        @Override
        public boolean check(ValidationContext ctx, Parameter p) {

          if (p.isSetUnits()) {

            String units = p.getUnits();
            Model m = p.getModel();

            if (! (Unit.isUnitKind(units, ctx.getLevel(), ctx.getVersion())
                || Unit.isPredefined(units, ctx.getLevel())
                || (m != null && m.getUnitDefinition(units) != null))) 
            {
            
              ValidationConstraint.logError(ctx, CORE_20701, p, p.getId(), p.getUnits());
              return false;
            }
          }

          return true;
        }
      };
      break;

    case CORE_20702:
      func = new ValidationFunction<Parameter>() {

        @Override
        public boolean check(ValidationContext ctx, Parameter p) {
          return p.isSetUnits();
        }
      };
      break;

      // TODO - what about 20703 and 20704
      
    case CORE_20706:
      func = new UnknownAttributeValidationFunction<Parameter>() {
        
        @Override
        public boolean check(ValidationContext ctx, Parameter c) {
          // id and constant are mandatory attributes
          if (!c.isSetId() || !c.isSetConstant()) {
            return false;
          }
          return super.check(ctx, c);
        }
      };
      break;
      
    case CORE_80701:
      func = new ValidationFunction<Parameter>() {

        @Override
        public boolean check(ValidationContext ctx, Parameter p) {

          return p.isSetUnits();
        }
      };
      break;

    case CORE_80702:
      func = new ValidationFunction<Parameter>() {

        @Override
        public boolean check(ValidationContext ctx, Parameter p) {

          Model m = p.getModel();

          if (m != null && !p.isSetValue()) {
            boolean setByAssignment = false;

            if (p.isSetId()) {
              setByAssignment = m.getInitialAssignmentBySymbol(p.getId()) != null;

              if (!setByAssignment) {
                Rule r = m.getRuleByVariable(p.getId());
                setByAssignment = r != null && r.isAssignment();
              }
            }

            return setByAssignment;
          }

          return true;
        }
      };
      break;
    case CORE_99508:
      return ValidationTools.checkDerivedUnit;
    }

    return func;
  }
}
