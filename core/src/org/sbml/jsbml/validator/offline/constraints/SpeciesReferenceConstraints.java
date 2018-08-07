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
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.ValidationTools;;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the {@link SpeciesReference} class.
 * 
 * @author Roman
 * @author rodrigue
 * @since 1.2
 */
public class SpeciesReferenceConstraints extends AbstractConstraintDeclaration {

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

      if (level == 2) {
        set.add(CORE_21113);
      }
      if (level > 2) {
        set.add(CORE_21116);
      }
      if (level > 1) {
        set.add(CORE_20611);
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
      // TODO - we should probably do some caching for this one to improve performance
      if (context.isLevelAndVersionGreaterEqualThan(3, 1)) {
        set.add(CORE_10542);
      }
      
      break;
    }
  }


  @Override
  @SuppressWarnings("deprecation")
  public ValidationFunction<?> getValidationFunction(int errorCode, ValidationContext context) {
    ValidationFunction<SpeciesReference> func = null;

    switch (errorCode) {

      case CORE_10542:
      {
        func = new ValidationFunction<SpeciesReference>() {
          
          @Override
          public boolean check(ValidationContext ctx, SpeciesReference speciesRef) {
          
            // checking that the units of the species is consistent with the unit of the extend multiplied by the species conversionFactor
            
            // get extend units
            Model m = speciesRef.getModel();
            UnitDefinition extendUnits = ValidationTools.getDerivedExtendUnitDefinition(m);
            
            // get species conversionFactor or more conversionFactor - if defined multiply it by the species units
            Species s = speciesRef.getSpeciesInstance();
            UnitDefinition speciesUnits = s != null ? ValidationTools.getDerivedSubstanceUnitDefinition(s) : null;
            
            if (extendUnits != null && !extendUnits.isInvalid()) {
              UnitDefinition cfUnits = null;
              
              if (s.isSetConversionFactor() && m.getParameter(s.getConversionFactor()) != null) {
                cfUnits = m.getParameter(s.getConversionFactor()).getUnitsInstance();
              } 
              else if (m.isSetConversionFactor() && m.getConversionFactorInstance() != null) {
                cfUnits = m.getConversionFactorInstance().getUnitsInstance();
              }
              
              if (cfUnits != null && !cfUnits.isInvalid()) {
                extendUnits = extendUnits.clone().multiplyWith(cfUnits);
              }
              
              // check the extend x CF ~= species
              if (speciesUnits != null && !speciesUnits.isInvalid()) {
                
                return UnitDefinition.areEquivalent(speciesUnits, extendUnits);
              }
            }
            
            return true;
          }
        };
        
        
        break;
      }

      case CORE_20611:
        func = new AbstractValidationFunction<SpeciesReference>() {

          // TODO - maintain a set of species ids to not report the error twice for the same species ?
          
          @Override
          public boolean check(ValidationContext ctx, SpeciesReference sr) {

            Species s = sr.getSpeciesInstance();

            // if the species is constant and 'boundaryCondition = false', it cannot be a reactant or product
            if (s != null && !s.isBoundaryCondition() && s.isConstant()) {

              ValidationConstraint.logError(ctx, CORE_20611, sr, s.getId());
              return false; 
            }

            return true;
          }
        };
        break;
        
    case CORE_21113:
      func = new ValidationFunction<SpeciesReference>() {

        @Override
        public boolean check(ValidationContext ctx, SpeciesReference sr) {
          // Can't be a Modifier
          if (sr.isSetStoichiometryMath()) {
            return !sr.isSetStoichiometry();
          }

          return true;

        }
      };
      break;

    case CORE_21116:
      func = new UnknownAttributeValidationFunction<SpeciesReference>() {
        
        @Override
        public boolean check(ValidationContext ctx, SpeciesReference c) {
          // constant and species are mandatory attributes
          if (!c.isSetSpecies() || !c.isSetConstant()) {
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
