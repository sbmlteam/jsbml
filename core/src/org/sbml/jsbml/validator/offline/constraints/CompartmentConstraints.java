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
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;;

public class CompartmentConstraints extends AbstractConstraintDeclaration{

  @Override
  public AnyConstraint<?> createConstraints(int level, int version,
    CHECK_CATEGORY category) {

    Set<Integer> set = new HashSet<Integer>();

    switch (category) {
    case GENERAL_CONSISTENCY:
      if (level == 1)
      {
        addRangeToSet(set, CORE_20504, CORE_20505);
        set.add(CORE_20509);
      }
      else if (level == 2)
      {
        addRangeToSet(set, CORE_20501, CORE_20509);

        if (version > 1)
        {
          set.add(CORE_20510);
        }
      }
      else if (level == 3)
      {
        addRangeToSet(set, CORE_20507, CORE_20509);
        set.add(CORE_20517);
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
    ValidationFunction<Compartment> func = null;

    switch (errorCode) {
    case CORE_20501:

      func = new ValidationFunction<Compartment>() {
        @Override
        public boolean check(ValidationContext ctx, Compartment c) {
          /*
           * Invalid use of the 'size' attribute for a
           * zero-dimensional compartment
           */
          if (c.getSpatialDimensions() == 0)
          {
            return !c.isSetSize();
          }

          return true;
        }
      };
      break;

    case CORE_20502:
      func = new ValidationFunction<Compartment>() {
        @Override
        public boolean check(ValidationContext ctx, Compartment c) {
          /*
           * Invalid use of the 'units' attribute for a
           * zero-dimensional compartment
           */

          if (c.getSpatialDimensions() == 0)
          {
            return !c.isSetUnits();
          }

          return true;
        }
      };
      break;

    case CORE_20503:
      func = new ValidationFunction<Compartment>() {
        @Override
        public boolean check(ValidationContext ctx, Compartment c) {

          if (c.getSpatialDimensions() == 0)
          {
            return c.isConstant();
          }

          return true;
        }
      };
      break;

    case CORE_20504:
      func = new ValidationFunction<Compartment>() {
        @Override
        public boolean check(ValidationContext ctx, Compartment c) {

          if (c.isSetOutside())
          {
            return c.getOutsideInstance() != null;
          }

          return true;
        }
      };
      break;

    case CORE_20505:
      func = new ValidationFunction<Compartment>() {
        HashSet<Compartment> outsideSet =  new HashSet<Compartment>();

        @Override
        public boolean check(ValidationContext ctx, Compartment c) {
          
          Compartment com = c;

          while(com != null && com.isSetOutside())
          {
            // Clears set
            outsideSet.clear();
            // add returns false if the compartment is already in the set
            if(!outsideSet.add(com))
            {
              return false;
            }

            com = com.getOutsideInstance();
          }

          return true;
        }
      };
      break;

    case CORE_20506:
      func = new ValidationFunction<Compartment>() {
        @Override
        public boolean check(ValidationContext ctx, Compartment c) {

          if (c.isSetOutside() && c.getSpatialDimensions() == 0) {

            Compartment outside = c.getOutsideInstance();

            if (outside != null) {
              return outside.getSpatialDimensions() == 0;
            }

          }
          return true;
        }
      };
      break;

    case CORE_20507:
      func = new ValidationFunction<Compartment>() {
        @Override
        public boolean check(ValidationContext ctx, Compartment c) {

          if (c.getSpatialDimensions() == 1 && c.isSetUnits()) {

            String unit = c.getUnits();
            UnitDefinition def = c.getUnitsInstance();

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

    case CORE_20508:
      func = new ValidationFunction<Compartment>() {
        @Override
        public boolean check(ValidationContext ctx, Compartment c) {

          if (c.getSpatialDimensions() == 2 && c.isSetUnits()) {
            String unit = c.getUnits();
            UnitDefinition def = c.getUnitsInstance();


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

    case CORE_20509:
      func = new ValidationFunction<Compartment>() {
        @Override
        public boolean check(ValidationContext ctx, Compartment c) {

          if (c.getSpatialDimensions() == 2 && c.isSetUnits()) {
            String unit = c.getUnits();
            UnitDefinition def = c.getUnitsInstance();


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

    case CORE_20510:
      func = new ValidationFunction<Compartment>() {
        // @SuppressWarnings("deprecation")
        @Override
        public boolean check(ValidationContext ctx, Compartment c) {

          if (c.isSetCompartmentType()) {
            return c.getCompartmentTypeInstance() != null;
          }

          return true;
        }
      };
      break;

    }

    return func;
  }
}
