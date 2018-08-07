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

import java.util.HashSet;
import java.util.Set;

import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Rule;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.util.TreeNodeChangeEvent;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.SBOValidationConstraints;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.ValidationTools;

/**
 * ConstraintDeclaration for Compartment class
 * 
 * @author Roman
 * @since 1.2
 */
public class CompartmentConstraints extends AbstractConstraintDeclaration {

  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category, ValidationContext context) {

    switch (category) {
    case GENERAL_CONSISTENCY:

      if (level == 1)
      {
        set.add(CORE_20504);
        set.add(CORE_20505);
      }
      else if (level == 2)
      {
        addRangeToSet(set, CORE_20501, CORE_20506);

        if (version > 1)
        {
          set.add(CORE_20510);
        }
      }
      else if (level >= 3)
      {
        set.add(CORE_20517);
      }

      break;
    case IDENTIFIER_CONSISTENCY:
      break;
    case MATHML_CONSISTENCY:
      break;
    case MODELING_PRACTICE:
      if (level >= 2)
      {
        set.add(CORE_80501);
      }
      break;
    case OVERDETERMINED_MODEL:
      break;
    case SBO_CONSISTENCY:
      if ((level == 2 && version > 2) || level > 2)
      {
        set.add(CORE_10712);
      }
      break;
    case UNITS_CONSISTENCY:
      if (level > 1)
      {
        addRangeToSet(set, CORE_20507, CORE_20509);
      }
      else
      {
        set.add(CORE_20509);
      }

      if (level > 2)
      {
        addRangeToSet(set, CORE_20511, CORE_20513);
        set.add(CORE_20518);
        set.add(CORE_99508);
      }
      break;
    }
  }

  @Override
  public void addErrorCodesForAttribute(Set<Integer> set, int level,
		  int version, String attributeName, ValidationContext context) {

	  if (attributeName == TreeNodeChangeEvent.sboTerm) {
		  if (level == 2 && version == 3) {
			  set.add(CORE_10712);
		  }
	  } else if (attributeName == TreeNodeChangeEvent.spatialDimensions) {
		  if (level == 1) {
			  set.add(CORE_20509);
		  } else if (level == 2) {
			  if (version > 1) {
				  set.add(CORE_20506);
			  }

			  addRangeToSet(set, CORE_20501, CORE_20503);
			  addRangeToSet(set, CORE_20507, CORE_20509);
		  }
	  } else if (attributeName == TreeNodeChangeEvent.compartmentType) {
		  if (level == 2 && version > 1)
		  {
			  set.add(CORE_20510);
		  }
	  } else if (attributeName == TreeNodeChangeEvent.outside) {
		  if (level == 2 && version > 1) {
			  set.add(CORE_20505);
			  set.add(CORE_20506);
		  }
	  } else if (attributeName == TreeNodeChangeEvent.units) {
		  if (level == 1) {
			  set.add(CORE_20509);
		  } else if (level == 2) {
			  set.add(CORE_20502);
			  addRangeToSet(set, CORE_20507, CORE_20509);
		  }

	  }


  }

  @Override
  @SuppressWarnings("deprecation")
  public ValidationFunction<?> getValidationFunction(int errorCode, ValidationContext context) {
    ValidationFunction<Compartment> func = null;

    switch (errorCode) {
    case CORE_10712:
      return SBOValidationConstraints.isMaterialEntity;

    case CORE_20501:

      func = new ValidationFunction<Compartment>() {
        @Override
        public boolean check(ValidationContext ctx, Compartment c) {
          /*
           * Invalid use of the 'size' attribute for a
           * zero-dimensional compartment
           */
          if (c.getSpatialDimensions() == 0 && c.isSetSize())
          {
            return false;
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

          if (c.getSpatialDimensions() == 0 && c.isSetUnits())
          {
            return false;
          }

          return true;
        }
      };
      break;

    case CORE_20503:
      func = new ValidationFunction<Compartment>() {
        @Override
        public boolean check(ValidationContext ctx, Compartment c) {

          if (c.getSpatialDimensions() == 0 && (!c.isConstant()))
          {
            return false;
          }

          return true;
        }
      };
      break;

    case CORE_20504:
      func = new AbstractValidationFunction<Compartment>() {
        @Override
        public boolean check(ValidationContext ctx, Compartment c) {

          if (c.isSetOutside() && (c.getOutsideInstance() == null))
          {
            ValidationConstraint.logError(ctx, CORE_20504, c, c.getId(), c.getOutside());
            return false;
          }

          return true;
        }
      };
      break;

    case CORE_20505:
      func = new ValidationFunction<Compartment>() {

        @Override
        public boolean check(ValidationContext ctx, Compartment c) {

          Compartment com = c;
          
          if (!c.isSetId()) {
            return true;
          }

          // We use a context global set here to avoid to report several times the same outside loop.
          // for example: 'a -> b -> c -> a' will be reported only once for compartment with id 'a' and not for 'b' and 'c' who are in the same loop.
          @SuppressWarnings("unchecked")
          Set<Compartment> visitedCompartments = (Set<Compartment>) ctx.getHashMap().get("OUTSIDE_SET"); 

          if (visitedCompartments == null) {
            visitedCompartments = new HashSet<Compartment>();
            ctx.getHashMap().put("OUTSIDE_SET", visitedCompartments);
          }

          String compartmentId = c.getId();
          
          while (com != null && com.isSetOutside())
          {
            
            boolean visited = visitedCompartments.add(com);
            
            if (compartmentId.equals(com.getOutside()))
            {
              return false; // TODO - we could check the exact compartment loop to display a nicer error message
            }

            if (!visited) {
              break;
            }
            
            com = com.getOutsideInstance();            
          }

          return true;
        }
      };
      break;

    case CORE_20506:
      func = new AbstractValidationFunction<Compartment>() {
        @Override
        public boolean check(ValidationContext ctx, Compartment c) {

          if (c.isSetOutside() && c.getSpatialDimensions() == 0) {

            Compartment outside = c.getOutsideInstance();

            if (outside != null && outside.getSpatialDimensions() != 0) {
              
              ValidationConstraint.logError(ctx, CORE_20506, c, c.getId(), outside.getId());
              
              return false;
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


            UnitDefinition def = c.getUnitsInstance();

            if (def == null)
            {
              return false;
            }

            if (ctx.getLevel() == 2 && ctx.getVersion() == 1) {
              return def.isVariantOfLength();
            }
            else
            {
              boolean isDimensionless = def.isVariantOfDimensionless();

              return isDimensionless || def.isVariantOfLength();
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

            UnitDefinition def = c.getUnitsInstance();

            if (def == null)
            {
              return false;
            }

            boolean isArea = def.isVariantOfArea();
            // boolean isDimensionless = def.isVariantOfDimensionless(); 
            
            if (ctx.getLevel() == 2 && ctx.getVersion() == 1) {
              return isArea;
            }
            else {
              boolean isDimensionless = def.isVariantOfDimensionless();

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

          if (c.getSpatialDimensions() == 3 && c.isSetUnits()) {
            UnitDefinition def = c.getUnitsInstance();

            if (def == null)
            {
              return false;
            }

            boolean isVolume = def.isVariantOfVolume();

            if (ctx.getLevel() == 2 && ctx.getVersion() == 1) {
              return isVolume;
            }
            else  {
              boolean isDimensionless = def.isVariantOfDimensionless();

              return isDimensionless || isVolume;
            }
          }

          return true;
        }
      };
      break;

    case CORE_20510:
      func = new AbstractValidationFunction<Compartment>() {
        // @SuppressWarnings("deprecation")
        @Override
        public boolean check(ValidationContext ctx, Compartment c) {

          if (c.isSetCompartmentType() && c.getCompartmentTypeInstance() == null) {
            
            ValidationConstraint.logError(ctx, CORE_20510, c, c.getId(), c.getCompartmentType());
            
            return false;
          }

          return true;
        }
      };
      break;

    case CORE_20511:
      func = new ValidationFunction<Compartment>() {

        @Override
        public boolean check(ValidationContext ctx, Compartment c) {

          if (c.isSetSpatialDimensions() && c.getSpatialDimensions() == 1) {
            return c.isSetUnits() || c.getModel().isSetLengthUnits();
          }

          return true;
        }
      };
      break;

    case CORE_20512:
      func = new ValidationFunction<Compartment>() {

        @Override
        public boolean check(ValidationContext ctx, Compartment c) {

          if (c.isSetSpatialDimensions() && c.getSpatialDimensions() == 2) {
            return c.isSetUnits() || c.getModel().isSetAreaUnits();
          }

          return true;
        }
      };
      break;

    case CORE_20513:
      func = new ValidationFunction<Compartment>() {

        @Override
        public boolean check(ValidationContext ctx, Compartment c) {

          if (c.isSetSpatialDimensions() && c.getSpatialDimensions() == 3) {
            return c.isSetUnits() || c.getModel().isSetVolumeUnits();
          }

          return true;
        }
      };
      break;

    case CORE_20517:
      func = new UnknownAttributeValidationFunction<Compartment>() {

        @Override
        public boolean check(ValidationContext ctx, Compartment c) {
          // id and constant are mandatory attributes
          if (!c.isSetId() || !c.isSetConstant()) {
            return false;
          }
          return super.check(ctx, c);
        }
      };
      break;

    case CORE_20518:

      func = new ValidationFunction<Compartment>() {

        @Override
        public boolean check(ValidationContext ctx, Compartment c) {

          return c.isSetUnits() || c.isSetSpatialDimensions();
        }
      };
      break;

    case CORE_80501:
      func = new ValidationFunction<Compartment>() {

        @Override
        public boolean check(ValidationContext ctx, Compartment c) {

          Model m = c.getModel();

          if (m != null && !c.isSetSize())
          {
            if (c.isSetSpatialDimensions() && c.getSpatialDimensions() == 0) {
              return true;
            }
            if (ctx.getLevel() >= 3 && (!c.isSetSpatialDimensions())) {
              // There are no default for spatialDimensions in L3, so don't report this error if spatialDimensions is not set
              return true;
            }
            
            boolean sizeByAssignment = false;
            
            if (c.isSetId())
            {
              sizeByAssignment = m.getInitialAssignmentBySymbol(c.getId()) != null;

              if (!sizeByAssignment)
              {
                Rule r = m.getRuleByVariable(c.getId());

                sizeByAssignment = r != null && r.isAssignment();
              }
            }

            return sizeByAssignment;
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
