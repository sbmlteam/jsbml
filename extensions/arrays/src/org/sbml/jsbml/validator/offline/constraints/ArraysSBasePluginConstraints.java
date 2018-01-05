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

import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.ext.arrays.ArraysSBasePlugin;
import org.sbml.jsbml.ext.arrays.validator.ExtendedSBaseValidator;
import org.sbml.jsbml.ext.arrays.validator.constraints.ArraysMathCheck;
import org.sbml.jsbml.ext.arrays.validator.constraints.DimensionArrayDimCheck;
import org.sbml.jsbml.ext.arrays.validator.constraints.IndexArrayDimCheck;
import org.sbml.jsbml.ext.arrays.validator.constraints.SBaseWithDimensionCheck;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;

/**
 * @author rodrigue
 *
 */
public class ArraysSBasePluginConstraints extends AbstractConstraintDeclaration {

  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
      CHECK_CATEGORY category, ValidationContext context) 
  {
    switch (category) {
    case GENERAL_CONSISTENCY:
    {
      set.add(ARRAYS_10207);
      set.add(ARRAYS_10211);
      set.add(ARRAYS_20103);
      set.add(ARRAYS_20104);
      set.add(ARRAYS_20107);
      addRangeToSet(set, ARRAYS_20110, ARRAYS_20111);
      break;
    }
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
  }

  @Override
  public void addErrorCodesForAttribute(Set<Integer> set, int level,
      int version, String attributeName, ValidationContext context) {
    // TODO Auto-generated method stub
    
  }

  // TODO - create a cache so that we don't run the constraint several time
  
  @Override
  public ValidationFunction<?> getValidationFunction(int errorCode, ValidationContext context) {
    ValidationFunction<ArraysSBasePlugin> func = null;

    switch (errorCode) {

    case ARRAYS_10207:
    {
      func = new ValidationFunction<ArraysSBasePlugin>() {
        @Override
        public boolean check(ValidationContext ctx, ArraysSBasePlugin c) {

          if (c.getExtendedSBase() instanceof MathContainer) 
          {
            ArraysMathCheck check = new ArraysMathCheck(c.getExtendedSBase().getModel(), (MathContainer) c.getExtendedSBase());
            check.check();

            if (check.getListOfErrors().size() > 0) {
              // TODO - build a proper error message

              // check in the list of errors that we have the right error             
              return !ArraysUtils.checkListOfErrors(check.getListOfErrors(), ARRAYS_10207);
            }
          }

          return true;
        }
      };
      break;
    }
    case ARRAYS_10211:
    {
      func = new ValidationFunction<ArraysSBasePlugin>() {
        @Override
        public boolean check(ValidationContext ctx, ArraysSBasePlugin c) {

          if (c.getExtendedSBase() instanceof MathContainer) 
          {
            ArraysMathCheck check = new ArraysMathCheck(c.getExtendedSBase().getModel(), (MathContainer) c.getExtendedSBase());
            check.check();

            if (check.getListOfErrors().size() > 0) {
              // TODO - build a proper error message

              // check in the list of errors that we have the right error             
              return !ArraysUtils.checkListOfErrors(check.getListOfErrors(), ARRAYS_10207);
            }
          }

          return true;
        }
      };
      break;
    }
    case ARRAYS_20103:
    {
      func = new ValidationFunction<ArraysSBasePlugin>() {
        @Override
        public boolean check(ValidationContext ctx, ArraysSBasePlugin c) {

          if (ExtendedSBaseValidator.canHaveDimension(c.getExtendedSBase())) 
          {
            DimensionArrayDimCheck check = new DimensionArrayDimCheck(c.getExtendedSBase().getModel(), c.getExtendedSBase());
            check.check();

            if (check.getListOfErrors().size() > 0) {
              // TODO - build a proper error message

              // check in the list of errors that we have the right error             
              return !ArraysUtils.checkListOfErrors(check.getListOfErrors(), ARRAYS_20103);
            }
          }

          return true;
        }
      };
      break;
    }
    case ARRAYS_20104:
    {
      func = new ValidationFunction<ArraysSBasePlugin>() {
        @Override
        public boolean check(ValidationContext ctx, ArraysSBasePlugin c) {

          if (ExtendedSBaseValidator.canHaveDimension(c.getExtendedSBase())) 
          {
            DimensionArrayDimCheck check = new DimensionArrayDimCheck(c.getExtendedSBase().getModel(), c.getExtendedSBase());
            check.check();

            if (check.getListOfErrors().size() > 0) {
              // TODO - build a proper error message

              // check in the list of errors that we have the right error             
              return !ArraysUtils.checkListOfErrors(check.getListOfErrors(), ARRAYS_20104);
            }
          }

          return true;
        }
      };
      break;
    }
    case ARRAYS_20107:
    {
      func = new ValidationFunction<ArraysSBasePlugin>() {
        @Override
        public boolean check(ValidationContext ctx, ArraysSBasePlugin c) {

          if (!ExtendedSBaseValidator.canHaveDimension(c.getExtendedSBase())) 
          {
            SBaseWithDimensionCheck check = new SBaseWithDimensionCheck(c.getExtendedSBase().getModel(), c.getExtendedSBase());
            check.check();

            if (check.getListOfErrors().size() > 0) {
              // TODO - build a proper error message

              // check in the list of errors that we have the right error             
              return !ArraysUtils.checkListOfErrors(check.getListOfErrors(), ARRAYS_20107);
            }
          }

          return true;
        }
      };
      break;
    }
    case ARRAYS_20110:
    {
      func = new ValidationFunction<ArraysSBasePlugin>() {
        @Override
        public boolean check(ValidationContext ctx, ArraysSBasePlugin c) {

          IndexArrayDimCheck check = new IndexArrayDimCheck(c.getExtendedSBase().getModel(), c.getExtendedSBase());
          check.check();

          if (check.getListOfErrors().size() > 0) {
            // TODO - build a proper error message

            // check in the list of errors that we have the right error             
            return !ArraysUtils.checkListOfErrors(check.getListOfErrors(), ARRAYS_20110);
          }

          return true;
        }
      };
      break;
    }
    case ARRAYS_20111:
    {
      func = new ValidationFunction<ArraysSBasePlugin>() {
        @Override
        public boolean check(ValidationContext ctx, ArraysSBasePlugin c) {

          IndexArrayDimCheck check = new IndexArrayDimCheck(c.getExtendedSBase().getModel(), c.getExtendedSBase());
          check.check();

          if (check.getListOfErrors().size() > 0) {
            // TODO - build a proper error message

            // check in the list of errors that we have the right error             
            return !ArraysUtils.checkListOfErrors(check.getListOfErrors(), ARRAYS_20111);
          }

          return true;
        }
      };
      break;
    }
    }

    return func;
  }



}
