/*
 *
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
import org.sbml.jsbml.Event;
import org.sbml.jsbml.LocalParameter;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.SBaseWithUnit;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.validator.SyntaxChecker;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;

/**
 * @author rodrigue
 * @since 1.2
 * 
 */
public class SBaseWithUnitConstraints
  extends AbstractConstraintDeclaration {

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.validator.offline.constraints.ConstraintDeclaration#
   * addErrorCodesForCheck(java.util.Set, int, int,
   * org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY)
   */
  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category, ValidationContext context) {

    switch (category) {
    case GENERAL_CONSISTENCY:
      break;
    case IDENTIFIER_CONSISTENCY:
      set.add(CORE_10311);
      set.add(CORE_10313);
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


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.validator.offline.constraints.ConstraintDeclaration#
   * addErrorCodesForAttribute(java.util.Set, int, int, java.lang.String)
   */
  @Override
  public void addErrorCodesForAttribute(Set<Integer> set, int level,
    int version, String attributeName, ValidationContext context) {
    // TODO Auto-generated method stub

  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.validator.offline.constraints.ConstraintDeclaration#
   * getValidationFunction(int)
   */
  @Override
  public ValidationFunction<?> getValidationFunction(int errorCode, ValidationContext context) {
    ValidationFunction<SBaseWithUnit> func = null;

    switch (errorCode) {
      
      case CORE_10311: {
        func = new AbstractValidationFunction<SBaseWithUnit>() {

          @Override
          public boolean check(ValidationContext ctx, SBaseWithUnit sb) {

            if (sb.isSetUnits() && (sb instanceof Compartment || sb instanceof Species 
                || sb instanceof Parameter || sb instanceof LocalParameter))
            {
              String unit = sb.getUnits();

              boolean isUnitsValid = SyntaxChecker.isValidId(unit, ctx.getLevel(), ctx.getVersion());

              if (!isUnitsValid) {
                ValidationConstraint.logError(ctx, CORE_10311, sb, unit, sb.getElementName(), sb.getId());
                return false;
              }
            }

            if (sb instanceof Species && ((Species) sb).isSetSpatialSizeUnits()) {
              String unit = ((Species) sb).getSpatialSizeUnits();
              
              boolean isUnitsValid = SyntaxChecker.isValidId(unit, ctx.getLevel(), ctx.getVersion());

              if (!isUnitsValid) {
                ValidationConstraint.logError(ctx, CORE_10311, sb, unit, sb.getElementName(), sb.getId());
                return false;
              }
            }
            
            if (sb instanceof Event && ((Event) sb).isSetTimeUnits()) {
              String unit = ((Event) sb).getTimeUnits();
              
              boolean isUnitsValid = SyntaxChecker.isValidId(unit, ctx.getLevel(), ctx.getVersion());

              if (!isUnitsValid) {
                ValidationConstraint.logError(ctx, CORE_10311, sb, unit, sb.getElementName(), sb.getId());
                return false;
              }
            }
            
            return true;
          }
        };
        break;
      }

      // 
      case CORE_10313: {
        func = new AbstractValidationFunction<SBaseWithUnit>() {

          @Override
          public boolean check(ValidationContext ctx, SBaseWithUnit sb) {
                  
            if (sb.isSetUnits() && (sb instanceof Compartment || sb instanceof Species 
                || sb instanceof Parameter || sb instanceof LocalParameter))
            {
              String unit = sb.getUnits();
            
              Model m = sb.getModel();
              boolean definedInModel = false;
        
              if (m != null) {
                definedInModel = m.getUnitDefinition(unit) != null;
              }

              if (! (definedInModel
                || Unit.isUnitKind(unit, ctx.getLevel(), ctx.getVersion())
                || Unit.isPredefined(unit, ctx.getLevel()))) 
              {
                if (ctx.getLevel() > 2 || (ctx.getLevel() == 2 && ctx.getVersion() >= 5)) {
                  ValidationConstraint.logError(ctx, CORE_10313, sb, sb.getUnits(), sb.getElementName(), sb.getId());
                } else {
                  ValidationConstraint.logError(ctx, CORE_99303, sb, sb.getUnits(), sb.getElementName(), sb.getId());
                }
                return false;
              }
            }
            
            return true;
          }
        };
        break;
      }
      
      // case CORE_99303: // nothing to do, taken care by CORE_10313
    }
    return func;
  }

}
