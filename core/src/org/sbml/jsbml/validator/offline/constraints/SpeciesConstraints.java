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
import org.sbml.jsbml.JSBML;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.Rule;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.SBOValidationConstraints;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.ValidationTools;
import org.sbml.jsbml.xml.XMLNode;

/**
 * 
 * @author Roman
 * @since 1.2
 */
@SuppressWarnings("deprecation")
public class SpeciesConstraints extends AbstractConstraintDeclaration{

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
      set.add(CORE_20601);
      
      if (context.isEnabledCategory(CHECK_CATEGORY.UNITS_CONSISTENCY)) {
        set.add(CORE_20608);
      }
      set.add(CORE_20610);
      set.add(CORE_20614);

      if (level == 2)
      {
        set.add(CORE_20604);
        set.add(CORE_20609);
        // set.add(CORE_20611); // implemented in SpeciesReferenceConstraint

        if (version < 3)
        {
          set.add(CORE_20602);
          set.add(CORE_20603);
        }

        if (version > 1)
        {
          set.add(CORE_20612);
          set.add(CORE_20613);
        }

        if (version > 2)
        {

          set.add(CORE_20615);
        }
      }
      else if (level >= 3)
      {
        set.add(CORE_20609);
        set.add(CORE_20617);
        set.add(CORE_20623);
        set.add(CORE_20705);
      }

      break;

    case MODELING_PRACTICE:
      if (level > 1)
      {
        set.add(CORE_80601);
      }
      break;
    case SBO_CONSISTENCY:
      if ((level == 2 && version > 2) || level > 2)
      {
        set.add(CORE_10713);
      }
      break;
    case IDENTIFIER_CONSISTENCY:
      break;
    case MATHML_CONSISTENCY:
      break;
    case OVERDETERMINED_MODEL:
      break;
    case UNITS_CONSISTENCY:

      if (level == 2 && version < 3)
      {
        addRangeToSet(set, CORE_20605, CORE_20607);
      }

      if (level > 2)
      {
        set.add(CORE_20616);
        set.add(CORE_99508);
      }

      break;
    }
  }



  @Override
  public ValidationFunction<?> getValidationFunction(int errorCode, ValidationContext context) {
    ValidationFunction<Species> func = null;

    switch (errorCode) {
    case CORE_10713:
      return SBOValidationConstraints.isMaterialEntity;

    case CORE_20601:
      func = new AbstractValidationFunction<Species>() {
        @Override
        public boolean check(ValidationContext ctx, Species s) {
          /*
           * Invalid value found for Species 'compartment' attribute
           */
          if (s.isSetCompartment() && s.getModel() != null && s.getCompartmentInstance() == null) {
            
            ValidationConstraint.logError(ctx, CORE_20601, s, s.getId(), s.getCompartment());
            return false;
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
            return !s.isSetSpatialSizeUnits();
          }

          return true;
        }
      };
      break;

    case CORE_20603:
      func = new AbstractValidationFunction<Species>() {


        @Override
        public boolean check(ValidationContext ctx, Species s) {

          if (s.getModel() != null)
          {
            Compartment c = s.getCompartmentInstance();

            if (c != null && c.getSpatialDimensions() == 0 && s.isSetSpatialSizeUnits()) {

              ValidationConstraint.logError(ctx, CORE_20603, s, s.getId(), s.getCompartment());

              return false;
            }
          }

          return true;
        }
      };
      break;

    case CORE_20604:
      func = new AbstractValidationFunction<Species>() {
        @Override
        public boolean check(ValidationContext ctx, Species s) {

          Compartment c = s.getCompartmentInstance();

          if (c != null && c.getSpatialDimensions() == 0 && s.isSetInitialConcentration()) {

            ValidationConstraint.logError(ctx, CORE_20604, s, s.getId(), s.getCompartment());

            return false;
          }

          return true;
        }
      };
      break;

    case CORE_20605:
      func = new AbstractValidationFunction<Species>() {
        @Override
        public boolean check(ValidationContext ctx, Species s) {

          Compartment c = s.getCompartmentInstance();
          boolean checkResult = true;
          
          if (c != null && c.getSpatialDimensions() == 1 && s.isSetSpatialSizeUnits()) {
            UnitDefinition def = s.getSpatialSizeUnitsInstance();

            if (def == null)
            {
              checkResult = false;
            }

            boolean isLength = checkResult && def.isVariantOfLength();

            if (ctx.getLevel() == 2 && ctx.getLevel() == 1 && !isLength) {
              checkResult = false;
            }

            if (ctx.getLevel() >= 2 && (! (def.isVariantOfDimensionless() || isLength)) ) {
              checkResult = false;
            }
          }

          if (!checkResult) {
            // report error
            ValidationConstraint.logError(ctx, CORE_20605, s, s.getId(), s.getCompartment(), s.getSpatialSizeUnits());
          }
          
          return checkResult;
        }
      };
      break;

    case CORE_20606:
      func = new AbstractValidationFunction<Species>() {
        @Override
        public boolean check(ValidationContext ctx, Species s) {

          Compartment c = s.getCompartmentInstance();
          boolean checkResult = true;
          
          if (c != null && c.getSpatialDimensions() == 2 && s.isSetSpatialSizeUnits()) {

            UnitDefinition def = s.getSpatialSizeUnitsInstance();

            if (def == null)
            {
              checkResult = false;
            }

            boolean isArea = checkResult && def.isVariantOfArea();

            if (ctx.getLevel() == 2 && ctx.getLevel() == 1 && !isArea) {
              checkResult = false;
            }

            if (ctx.getLevel() >= 2 && (! (def.isVariantOfDimensionless() || isArea))) {
              checkResult = false;
            }
          }

          if (!checkResult) {
            // report error
            ValidationConstraint.logError(ctx, CORE_20606, s, s.getId(), s.getCompartment(), s.getSpatialSizeUnits());
          }
          
          return checkResult;
        }
      };
      break;

    case CORE_20607:
      func = new AbstractValidationFunction<Species>() {

        @Override
        public boolean check(ValidationContext ctx, Species s) {

          Compartment c = s.getCompartmentInstance();
          boolean checkResult = true;
          
          if (c != null && c.getSpatialDimensions() == 3 && s.isSetSpatialSizeUnits()) {

            UnitDefinition def = s.getSpatialSizeUnitsInstance();

            if (def == null)
            {
              checkResult = false;
            }

            boolean isVolume = checkResult && def.isVariantOfVolume();

            if (checkResult && ctx.getLevel() == 2 && ctx.getLevel() == 1 && !isVolume) {
              checkResult = false;
            }

            if (checkResult && ctx.getLevel() >= 2 && (! (def.isVariantOfDimensionless() || isVolume))) {
              checkResult = false;
            }
          }

          if (!checkResult) {
            // report error
            ValidationConstraint.logError(ctx, CORE_20607, s, s.getId(), s.getCompartment(), s.getSpatialSizeUnits());
          }
          
          return checkResult;
        }
      };
      break;
    case CORE_20608:
      func = new AbstractValidationFunction<Species>() {


        @Override
        public boolean check(ValidationContext ctx, Species s) {

          boolean checkResult = true;
          
          if (s.isSetSubstanceUnits())
          {
            UnitDefinition ud = s.getSubstanceUnitsInstance();

            if (ud != null)
            {
              boolean isVariantOfSubstance = ud.isVariantOfSubstance();
              // boolean isVariantOfDimensionless = ud.isVariantOfDimensionless();

              if ((ctx.getLevel() == 1 || (ctx.getLevel() == 2 && ctx.getVersion() == 1)) 
                  && !isVariantOfSubstance)
              {
                checkResult = false;
              }
              else if (! (isVariantOfSubstance || ud.isVariantOfDimensionless()))
              {
                checkResult = false;
              }
            } else {
              checkResult = false;
            }
          }
          
          if (!checkResult) {
            // report error
            ValidationConstraint.logError(ctx, CORE_20608, s, s.getId(), s.getSubstanceUnits());
          }
          
          return checkResult;
        }
      };
      break;

    case CORE_20609:
      func = new ValidationFunction<Species>() {


        @Override
        public boolean check(ValidationContext ctx, Species s) {

          if (s.isSetUserObjects() && s.getUserObject(JSBML.UNKNOWN_XML) != null)
          {
            XMLNode unknownNode = (XMLNode) s.getUserObject(JSBML.UNKNOWN_XML);

            if (unknownNode.getAttributesLength() > 0) {
              return (unknownNode.getAttrIndex("initialConcentration") == -1) && (unknownNode.getAttrIndex("initialAmount") == -1);
            }
          }

          return true;
        }
      };
      break;

    case CORE_20610:
      func = new AbstractValidationFunction<Species>() {

        @Override
        public boolean check(ValidationContext ctx, Species s) {
          Model m = s.getModel();

          if (!s.isBoundaryCondition() &&
              !s.isConstant() &&
              m != null)
          {

            boolean found = false;

            if (m.isSetListOfRules()) {
              for (Rule r: m.getListOfRules())
              {
                if (r.isAssignment() || r.isRate())
                {
                  ExplicitRule er = (ExplicitRule) r;
                  if (er.getVariable().equals(s.getId()))
                  {
                    found = true;
                    break;
                  }
                }
              }
            }

            // If the Species is not assigned by a rule, there couldn't be a collision
            if (!found)
            {
              return true;
            }

            // TODO ? - instead of going through all reactions for each species, we could create a map of Species ids to check and do the actual check inside SpeciesReferenceConstraint 
            
            // This species can't be part of a Reaction
            if (m.isSetListOfReactions()) {
              for (Reaction r: m.getListOfReactions())
              {
                if (r.getProductCount() > 0) {
                  for (SpeciesReference sr: r.getListOfProducts())
                  {
                    if (sr.getSpecies().equals(s.getId()))
                    {
                      ValidationConstraint.logError(ctx, CORE_20610, s, s.getId(), r.getId());
                      return false;
                    }
                  }
                }

                if (r.getReactantCount() > 0) {
                  for (SpeciesReference sr: r.getListOfReactants())
                  {
                    if (sr.getSpecies().equals(s.getId()))
                    {
                      ValidationConstraint.logError(ctx, CORE_20610, s, s.getId(), r.getId());
                      return false;
                    }
                  }
                }
              }
            }
          }

          return true;
        }
      };
      break;

    // case CORE_20611: // Done in SpeciesReferenceConstraints
      
    case CORE_20612:
      func = new AbstractValidationFunction<Species>() {

        @Override
        public boolean check(ValidationContext ctx, Species s) {
          if (s.isSetSpeciesType() && s.getSpeciesTypeInstance() == null) {

            ValidationConstraint.logError(ctx, CORE_20612, s, s.getId(), s.getSpeciesType());
            return false;
          }
          return true;
        }
      };
      break;

    case CORE_20613:
      func = new ValidationFunction<Species>() {

        @Override
        public boolean check(ValidationContext ctx, Species s) {

          // TODO - change the way we report the error, to report the error per compartment, only once.
          // And we should be able to avoid a loop over the full listofSpecies for each species
          
          Model m = s.getModel();
          if (s.isSetSpeciesType() && m != null)
          {
            String st = s.getSpeciesType();

            for (Species spec: m.getListOfSpecies())
            {
              // Are species in same compartment but not the same?
              if (spec.isSetSpeciesType() &&
                  spec.getCompartment().equals(s.getCompartment()) &&
                  !spec.getId().equals(s.getId()))
              {
                // Must have different Types
                if (spec.getSpeciesType().equals(st))
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

    case CORE_20616:
      func = new ValidationFunction<Species>() {
        @Override
        public boolean check(ValidationContext ctx, Species s) {
          return s.isSetSubstanceUnits() || s.getModel().isSetSubstanceUnits();
        }
      };
      break;

    case CORE_20617:
      func = new AbstractValidationFunction<Species>() {
        @Override
        public boolean check(ValidationContext ctx, Species s) {
          
          if (s.isSetConversionFactor() && s.getConversionFactorInstance() == null) {
            
            ValidationConstraint.logError(ctx, CORE_20617, s, s.getId(), s.getConversionFactor());
            return false;
          }

          return true;
        }
      };
      break;

    case CORE_20623:
      func = new UnknownAttributeValidationFunction<Species>() {

        @Override
        public boolean check(ValidationContext ctx, Species c) {
          // id 'compartment', 'hasOnlySubstanceUnits', 'boundaryCondition'and constant are mandatory attributes
          if (!c.isSetId() || !c.isSetConstant() || !c.isSetHasOnlySubstanceUnits()
              || !c.isSetBoundaryCondition() || !c.isSetCompartment())
          {
            return false;
          }
          return super.check(ctx, c);
        }
      };
      break;

    case CORE_20705:
      func = new AbstractValidationFunction<Species>() {
        @Override
        public boolean check(ValidationContext ctx, Species s) {
          if (s.isSetConversionFactor())
          {
            Parameter fac = s.getConversionFactorInstance();

            // don't report if the parameter is null, that's the job of CORE_20617
            if (fac != null && !fac.isConstant()) {
              
              ValidationConstraint.logError(ctx, CORE_20705, s, s.getConversionFactor());
              return  false;
            }
          }

          return true;
        }
      };
      break;

    case CORE_80601:
      func = new ValidationFunction<Species>() {
        @Override
        public boolean check(ValidationContext ctx, Species s) {

          Model m = s.getModel();

          if (m != null && !s.isSetInitialAmount() && !s.isSetInitialConcentration())
          {
            boolean setByAssignment = false;

            if (s.isSetId())
            {
              setByAssignment = m.getInitialAssignmentBySymbol(s.getId()) != null;

              if (!setByAssignment)
              {
                Rule r = m.getRuleByVariable(s.getId());
                setByAssignment = r != null && r.isAssignment();
              }
            }

            if (!setByAssignment) {
              // TODO - build a nice error message
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
