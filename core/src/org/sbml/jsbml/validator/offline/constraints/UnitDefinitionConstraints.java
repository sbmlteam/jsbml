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

import org.sbml.jsbml.Unit;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;;

public class UnitDefinitionConstraints extends AbstractConstraintDeclaration {

  @Override
  public AnyConstraint<?> createConstraints(int level, int version,
    CHECK_CATEGORY category) {

    Set<Integer> set = new HashSet<Integer>();

    switch (category) {
    case GENERAL_CONSISTENCY:

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
    ValidationFunction<UnitDefinition> func = null;

    switch (errorCode) {
    case CORE_20401:
      func = new ValidationFunction<UnitDefinition>() {

        @Override
        public boolean check(ValidationContext ctx, UnitDefinition ud) {

          return Unit.isUnitKind(ud.getId(), ctx.getLevel(), ctx.getVersion());
        }
      };
      break;

    case CORE_20402:
      func = new ValidationFunction<UnitDefinition>() {

        @Override
        public boolean check(ValidationContext ctx, UnitDefinition ud) {

          if (ud.getId() == "substance") {
            if (ctx.isLevelAndVersionLesserEqualThan(2, 1)) {
              return ud.isVariantOfSubstance();
            } else {
              return ud.isVariantOfSubstance()
                  || (ud.getNumUnits() == 1 && ud.getUnit(0).isDimensionless());
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
          // TODO Auto-generated method stub

          if (ud.getId() == "length") {
            if (ctx.isLevelAndVersionLesserEqualThan(2, 1)) {
              return ud.isVariantOfLength();
            } else {
              return ud.isVariantOfLength()
                  || (ud.getNumUnits() == 1 && ud.getUnit(0).isDimensionless());
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
          // TODO Auto-generated method stub

          if (ud.getId() == "area") {
            if (ctx.isLevelAndVersionLesserEqualThan(2, 1)) {
              return ud.isVariantOfArea();
            } else {
              return ud.isVariantOfArea()
                  || (ud.getNumUnits() == 1 && ud.getUnit(0).isDimensionless());
            }
          }

          return true;
        }
      };
      break;
    case CORE_20405:
      func = new ValidationFunction<UnitDefinition>() {

        @Override
        public boolean check(ValidationContext ctx, UnitDefinition ud) {
          // TODO Auto-generated method stub

          if (ud.getId() == "time") {
            if (ctx.isLevelAndVersionLesserEqualThan(2, 1)) {
              return ud.isVariantOfTime();
            } else {
              return ud.isVariantOfTime()
                  || (ud.getNumUnits() == 1 && ud.getUnit(0).isDimensionless());
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
          // TODO Auto-generated method stub

          if (ud.getId() == "volume") {
            if (ctx.isLevelAndVersionLessThan(2, 4)) {
              if (ud.getUnitCount() == 1) {
                Unit u = ud.getUnit(0);
                if (ctx.getLevel() == 1) {
                  return u.isLitre();
                } else if (ctx.isLevelAndVersionEqualTo(2, 1)) {
                  return u.isLitre() || u.isMetre();
                } else {
                  return u.isLitre() || u.isMetre() || u.isDimensionless();
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
          if (ctx.isLevelAndVersionLessThan(2, 4) && ud.getId() == "volume"
              && ud.getNumUnits() == 1) {
            Unit u = ud.getUnit(0);

            return u.isLitre() && u.getExponent() == 1;
          }
          return true;
        }
      };
      break;

    case CORE_20408:
      func = new ValidationFunction<UnitDefinition>() {

        @Override
        public boolean check(ValidationContext ctx, UnitDefinition ud) {

          if (ctx.isLevelAndVersionLessThan(2, 4) && ud.getId() == "volume"
              && ud.getNumUnits() == 1) {
            Unit u = ud.getUnit(0);

            return u.isMetre() && u.getExponent() == 3;
          }
          return true;
        }
      };
      break;

    case CORE_20409:
      func = new ValidationFunction<UnitDefinition>() {

        @Override
        public boolean check(ValidationContext ctx, UnitDefinition ud) {

          return ud.getListOfUnits().size() > 0;
        }
      };
      break;

    case CORE_20410:
      func = new ValidationFunction<UnitDefinition>() {

        @Override
        public boolean check(ValidationContext ctx, UnitDefinition ud) {

          boolean success = true;

          for (Unit u : ud.getListOfUnits()) {
            if (!u.isCelsius()) {
              success = success && Unit.isUnitKind(u.getKind(), ctx.getLevel(),
                ctx.getVersion());
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

          for (Unit u : ud.getListOfUnits()) {
            success = success && u.getOffset() == 0;
          }

          return success;
        }
      };
      break;

    }

    return func;
  }
}
