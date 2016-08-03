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
import org.sbml.jsbml.ExplicitRule;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.Rule;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;

public class SpeciesConstraintsDeclaration extends AbstractConstraintDeclaration{
  
  @Override
  public AnyConstraint<?> createConstraints(int level, int version,
    CHECK_CATEGORY category) {
    
    Set<Integer> set = new HashSet<Integer>();
    
    switch (category) {
    case GENERAL_CONSISTENCY:
      set.add(CORE_20601);
      addRangeToSet(set, CORE_20608, CORE_20610);
      set.add(CORE_20614);
      set.add(CORE_20617);
      set.add(CORE_20623);
      break;

    default:
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
    ValidationFunction<Species> func = null;
    
    switch (errorCode) {
    case CORE_20601:
      func = new ValidationFunction<Species>() {
        @Override
        public boolean check(ValidationContext ctx, Species s) {
          /*
           * Invalid value found for Species 'compartment' attribute
           */
          if (s.isSetCompartment() && s.getModel() != null) {
            return s.getCompartmentInstance() != null;
          }

          return true;
        }
      };
      break;
    case CORE_20602:
      func = new ValidationFunction<Species>() {

        
        @Override
        public boolean check(ValidationContext ctx, Species s) {
          /*
           * Invalid value found for Species 'compartment' attribute
           */
          if (s.hasOnlySubstanceUnits()) {
            return s.isSetSpatialSizeUnits();
          }

          return true;
        }
      };
      break;

    case CORE_20603:
      func = new ValidationFunction<Species>() {


        @Override
        public boolean check(ValidationContext ctx, Species s) {

          if (s.getModel() != null)
          {
            Compartment c = s.getCompartmentInstance();

            if (c != null && c.getSpatialDimensions() == 0) {
              return !s.isSetSpatialSizeUnits();
            }
          }

          return true;
        }
      };
      break;

    case CORE_20604:
      func = new ValidationFunction<Species>() {
        @Override
        public boolean check(ValidationContext ctx, Species s) {

          Compartment c = s.getCompartmentInstance();

          if (c != null && c.getSpatialDimensions() == 0) {
            return !s.isSetInitialConcentration();
          }

          return true;
        }
      };
      break;

    case CORE_20605:
      func = new ValidationFunction<Species>() {
        @Override
        public boolean check(ValidationContext ctx, Species s) {

          Compartment c = s.getCompartmentInstance();

          if (c != null && c.getSpatialDimensions() == 0 && s.isSetSpatialSizeUnits()) {
            String unit = s.getUnits();
            UnitDefinition def = s.getUnitsInstance();

            boolean isLength = ValidationContext.isLength(unit, def);

            if (ctx.getLevel() == 2 && ctx.getLevel() == 1) {
              return isLength;
            }

            if (ctx.getLevel() >= 2) {
              boolean isDimensionless = ValidationContext.isDimensionless(unit);

              return isDimensionless || isLength;
            }
          }

          return true;
        }
      };
      break;

    case CORE_20606:
      func = new ValidationFunction<Species>() {
        @Override
        public boolean check(ValidationContext ctx, Species s) {

          Compartment c = s.getCompartmentInstance();

          if (c != null && c.getSpatialDimensions() == 0 && s.isSetSpatialSizeUnits()) {
            String unit = s.getSpatialSizeUnits();
            UnitDefinition def = s.getUnitsInstance();

            boolean isArea = ValidationContext.isArea(unit, def);

            if (ctx.getLevel() == 2 && ctx.getLevel() == 1) {
              return isArea;
            }

            if (ctx.getLevel() >= 2) {
              boolean isDimensionless = ValidationContext.isDimensionless(unit);

              return isDimensionless || isArea;
            }
          }

          return true;
        }
      };
      break;

    case CORE_20607:
      func = new ValidationFunction<Species>() {

        @Override
        public boolean check(ValidationContext ctx, Species s) {

          Compartment c = s.getCompartmentInstance();

          if (c != null && c.getSpatialDimensions() == 3 && s.isSetSpatialSizeUnits()) {
            String unit = s.getSpatialSizeUnits();
            UnitDefinition def = s.getUnitsInstance();

            boolean isVolume = ValidationContext.isVolume(unit, def);

            if (ctx.getLevel() == 2 && ctx.getLevel() == 1) {
              return isVolume;
            }

            if (ctx.getLevel() >= 2) {
              boolean isDimensionless = ValidationContext.isDimensionless(unit);

              return isDimensionless || isVolume;
            }
          }

          return true;
        }
      };
      break;
      
    case CORE_20610:
      func = new ValidationFunction<Species>() {

        @Override
        public boolean check(ValidationContext ctx, Species s) {
          Model m = s.getModel();
          
          if (!s.isBoundaryCondition() && 
              !s.isConstant() && 
              m != null)
          {
            
            boolean found = false;
            
            for (Rule r: m.getListOfRules())
            {
              if (r.isAssignment() || r.isRate())
              {
                ExplicitRule er = (ExplicitRule) r;
                if (er.getVariable() == s.getId())
                {
                  found = true;
                  break;
                }
              }
            }
            
            // If the Species is not assigned by a rule, there couldn't be a collision
            if (!found)
            {
              return true;
            }
            
            // This species can't be part of a Reaction
            for (Reaction r:m.getListOfReactions())
            {
              for (SpeciesReference sr: r.getListOfProducts())
              {
                if (sr.getSpecies().equals(s.getId()))
                {
                  return false;
                }
              }
              
              for (SpeciesReference sr: r.getListOfReactants())
              {
                if (sr.getSpecies().equals(s.getId()))
                {
                  return false;
                }
              }
            }
          }
          
          return true;
        }
      };
      break;
      
    case CORE_20612:
      func = new ValidationFunction<Species>() {

        @Override
        public boolean check(ValidationContext ctx, Species s) {
          if (s.isSetSpeciesType()) {

            return s.getSpeciesTypeInstance() != null;
          }
          return true;
        }
      };
      break;
      
    case CORE_20614:
      func = new ValidationFunction<Species>() {
        @Override
        public boolean check(ValidationContext ctx, Species s) {
          return s.isSetCompartment();
        }
      };
      break;

    case CORE_20615:
      func = new ValidationFunction<Species>() {
        @Override
        public boolean check(ValidationContext ctx, Species s) {
          return !s.isSetSpatialSizeUnits();
        }
      };
      break;

    case CORE_20617:
      func = new ValidationFunction<Species>() {
        @Override
        public boolean check(ValidationContext ctx, Species s) {
          if (s.isSetConversionFactor()) {
            return s.getConversionFactorInstance() != null;
          }

          return true;
        }
      };
      break;
    }
    
    return func;
  }
}
