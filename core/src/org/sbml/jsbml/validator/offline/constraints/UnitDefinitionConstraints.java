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
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.Unit.Kind;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.util.TreeNodeWithChangeSupport;
import org.sbml.jsbml.util.ValuePair;
import org.sbml.jsbml.validator.SyntaxChecker;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.DuplicatedElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UniqueValidation;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.ValidationTools;;

/**
 * @author Roman
 * @since 1.2
 */
public class UnitDefinitionConstraints extends AbstractConstraintDeclaration {

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
        addRangeToSet(set, CORE_20401, CORE_20407);
        set.add(CORE_20410);
      } else if (level == 2) {
        addRangeToSet(set, CORE_20401, CORE_20406);
        set.add(CORE_20410);

        if (version < 4) {
          set.add(CORE_20407);
          set.add(CORE_20408);
        }

        if (version > 1) {
          set.add(CORE_20411);
          set.add(CORE_20412);
        }
      } else if (level == 3) {
        set.add(CORE_20401);
        set.add(CORE_20410);
        
        if (version == 1) {
          set.add(CORE_20413);
        }
        
        set.add(CORE_20414);
        set.add(CORE_20415);
        set.add(CORE_20419);
        set.add(CORE_20420);
      }
      
      // For level and version before L3V2
      if (ValuePair.of(level, version).compareTo(3, 2) < 0) {
        set.add(CORE_20409);        
      }

      break;
    case IDENTIFIER_CONSISTENCY:
      
      set.add(CORE_10302);
      
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
  public ValidationFunction<?> getValidationFunction(int errorCode, ValidationContext context) {
    ValidationFunction<UnitDefinition> func = null;

    switch (errorCode) {
      
      case CORE_10302: {
        func = new ValidationFunction<UnitDefinition>() {

          @Override
          public boolean check(ValidationContext ctx, UnitDefinition ud) {

            if (!ud.isSetId()) {

              // checking in the invalid user object
              String invalidId = ValidationTools.checkInvalidAttribute(ctx, ud, "id");

              if (invalidId != null && SyntaxChecker.isValidId(invalidId, ctx.getLevel(), ctx.getVersion())) {
                // If the id has a valid syntax, then it is a duplicated id.
                // TODO - create a nice error message
                return false;
              }

              if (ctx.getLevel() == 1) {
                invalidId = ValidationTools.checkInvalidAttribute(ctx, ud, "name");

                if (invalidId != null && SyntaxChecker.isValidId(invalidId, ctx.getLevel(), ctx.getVersion())) {
                  return false;
                }
              }
            }  

            return true;
          }
        };
        break;
      }

    case CORE_20401:
      func = new ValidationFunction<UnitDefinition>() {

        @Override
        public boolean check(ValidationContext ctx, UnitDefinition ud) {

          // it is allowed to have substance, area, time, ... as id for UnitDefinition - && !Unit.isPredefined(ud)

          return ValidationTools.isId(ud.getId(), ctx.getLevel(), ctx.getVersion()) 
              && !Unit.isUnitKind(ud.getId(), ctx.getLevel(), ctx.getVersion());
        }
      };
      break;

    case CORE_20402:
      func = new ValidationFunction<UnitDefinition>() {

        @Override
        public boolean check(ValidationContext ctx, UnitDefinition ud) {

          if (ud.getId().equals("substance")) {
            if (ctx.isLevelAndVersionLesserEqualThan(2, 1)) {
              return ud.isVariantOfSubstance();
            } else {
              return ud.isVariantOfSubstance() || ud.isVariantOfDimensionless();
            }
          }

          return true;
        }
      };
      break;

    case CORE_20403:
      func = new ValidationFunction<UnitDefinition>() {

        @Override
        public boolean check(ValidationContext ctx, UnitDefinition ud) {

          if (ud.getId().equals("length")) {
            if (ctx.isLevelAndVersionLesserEqualThan(2, 1)) {
              // Meter not allowed on L2V1
              if (ctx.isLevelAndVersionEqualTo(2, 1) && ud.getUnitCount() > 0 && ud.getUnit(0).getKind() == Kind.METER) {
                return false;
              }
              return ud.isVariantOfLength();
            } else {
              return ud.isVariantOfLength() || ud.isVariantOfDimensionless();
            }
          }

          return true;
        }
      };
      break;

    case CORE_20404:
      func = new ValidationFunction<UnitDefinition>() {

        @Override
        public boolean check(ValidationContext ctx, UnitDefinition ud) {

          if (ud.getId().equals("area")) {
            if (ctx.isLevelAndVersionLesserEqualThan(2, 1)) {
              return isVariantOfArea(ctx, ud);
            } else {
              return isVariantOfArea(ctx, ud) || ud.isVariantOfDimensionless();
            }
          }

          return true;
        }


        private boolean isVariantOfArea(ValidationContext ctx,
          UnitDefinition ud) {
          // TODO - to really use the level and version of the context, we need to clone the UnitDefinition 
          // and change the level and version of the UnitDefinition and Unit instances so that all the methods
          // on UnitDefinition or Unit use this level and version as well.
          if (ctx.getLevel() == 1) {
            return ud.isVariantOfArea();
          }
          ud = ud.clone().simplify(); 
          
          if (ud.getUnitCount() == 1) {
            Unit u = ud.getUnit(0);
            return u.getKind() == Kind.METRE && u.getExponent() == 2;
          }

          return false;
        }
      };

      break;

    case CORE_20405:
      func = new ValidationFunction<UnitDefinition>() {

        @Override
        public boolean check(ValidationContext ctx, UnitDefinition ud) {

          if (ud.getId().equals("time")) {
            if (ctx.isLevelAndVersionLesserEqualThan(2, 1)) {
              return ud.isVariantOfTime();
            } else {
              return ud.isVariantOfTime() || ud.isVariantOfDimensionless();
            }
          }

          return true;
        }
      };
      break;

    case CORE_20406:
      func = new ValidationFunction<UnitDefinition>() {

        @Override
        public boolean check(ValidationContext ctx, UnitDefinition ud) {

          if (ud.getId().equals("volume")) {
            if (ctx.isLevelAndVersionLessThan(2, 4)) {

              if (ud.getUnitCount() == 1) {
                Unit u = ud.getUnit(0);
                if (ctx.getLevel() == 1) {

                  return u.isLitre();
                } else if (ctx.isLevelAndVersionEqualTo(2, 1)) {
                  return u.getKind() == Kind.LITRE
                    || (u.getKind() == Kind.METRE && u.getExponent() == 3);
                } else {
                  return u.getKind() == Kind.LITRE
                    || (u.getKind() == Kind.METRE && u.getExponent() == 3)
                    || ud.isVariantOfDimensionless();
                }
              } else {

                return ud.isVariantOfVolume();

              }

            } else {
              return ud.isVariantOfVolume()
                || (ud.getNumUnits() == 1 && ud.getUnit(0).isDimensionless());
            }
          }

          return true;
        }
      };
      break;

    case CORE_20407:
      func = new ValidationFunction<UnitDefinition>() {

        @Override
        public boolean check(ValidationContext ctx, UnitDefinition ud) {
          if (ctx.isLevelAndVersionLessThan(2, 4) && ud.getId().equals("volume")
            && ud.getNumUnits() == 1) {
            Unit u = ud.getUnit(0);

            if (u.isLitre()) {
              return u.getExponent() == 1;
            }

          }
          return true;
        }
      };
      break;

    case CORE_20408:
      func = new ValidationFunction<UnitDefinition>() {

        @Override
        public boolean check(ValidationContext ctx, UnitDefinition ud) {

          if (ctx.isLevelAndVersionLessThan(2, 4) && ud.getId().equals("volume")
            && ud.getNumUnits() == 1) {
            Unit u = ud.getUnit(0);

            if (u.isMetre()) {
              return u.getExponent() == 3;
            }

          }
          return true;
        }
      };
      break;

    case CORE_20409:
      func = new ValidationFunction<UnitDefinition>() {

        @Override
        public boolean check(ValidationContext ctx, UnitDefinition ud) {

          return ! ud.isListOfUnitsEmpty();
        }
      };
      break;

    case CORE_20410:
      func = new ValidationFunction<UnitDefinition>() {

        @Override
        public boolean check(ValidationContext ctx, UnitDefinition ud) {

          boolean success = true;

          if (ud.isSetListOfUnits()) {
            for (Unit u : ud.getListOfUnits()) {
              if (!u.isCelsius()) {
                success = success && Unit.isUnitKind(u.getKind(), ctx.getLevel(),
                    ctx.getVersion());
              }
            }
          }

          return success;
        }
      };
      break;

    case CORE_20411:
      func = new ValidationFunction<UnitDefinition>() {

        @Override
        public boolean check(ValidationContext ctx, UnitDefinition ud) {
          boolean success = true;

          if (ud.isSetListOfUnits()) {
            for (Unit u : ud.getListOfUnits()) {
              success = success && u.getOffset() == 0;
            }
          }

          return success;
        }

      };
      break;

    case CORE_20413:
      func = new ValidationFunction<UnitDefinition>() {

        @Override
        public boolean check(ValidationContext ctx, UnitDefinition ud) {
          return !ud.isListOfUnitsEmpty();
        }

      };
      break;

    case CORE_20414:
      func = new DuplicatedElementValidationFunction<UnitDefinition>("listOfUnits");
      break;

    case CORE_20415:
      func = new ValidationFunction<UnitDefinition>() {

        @Override
        public boolean check(ValidationContext ctx, UnitDefinition ud) {
          
          if (ud.isSetListOfUnits() || ud.isListOfUnitsEmpty()) {
            return new UnknownElementValidationFunction<TreeNodeWithChangeSupport>().check(ctx, ud.getListOfUnits());
          }
          
          return true;
        }

      };
      break;

    case CORE_20419:
      func = new UnknownAttributeValidationFunction<UnitDefinition>() {

        @Override
        public boolean check(ValidationContext ctx, UnitDefinition ud) {
          // 'id' is a mandatory attribute
          if (!ud.isSetId())
          {
            return false;
          }
          return super.check(ctx, ud);
        }
      };
      break;
      
    case CORE_20420:
      func = new ValidationFunction<UnitDefinition>() {

        @Override
        public boolean check(ValidationContext ctx, UnitDefinition ud) {

          if (ud.isSetListOfUnits() || ud.isListOfUnitsEmpty()) {
            return new UnknownAttributeValidationFunction<TreeNodeWithChangeSupport>().check(ctx, ud.getListOfUnits());
          }

          return true;
        }
      };
      break;

    }

  return func;
}}
