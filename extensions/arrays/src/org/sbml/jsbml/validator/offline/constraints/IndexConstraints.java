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

import org.sbml.jsbml.ext.arrays.Index;
import org.sbml.jsbml.ext.arrays.validator.constraints.IndexAttributesCheck;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;

/**
 * @author rodrigue
 *
 */
public class IndexConstraints extends AbstractConstraintDeclaration {

  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
      CHECK_CATEGORY category, ValidationContext context) 
  {
    switch (category) {
    case GENERAL_CONSISTENCY:
    {
      addRangeToSet(set, ARRAYS_20302, ARRAYS_20305);
      set.add(ARRAYS_20307);
      set.add(ARRAYS_20308);
      
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
    ValidationFunction<Index> func = null;

    switch (errorCode) {

    case ARRAYS_20302:
    {
      func = new ValidationFunction<Index>() {
        @Override
        public boolean check(ValidationContext ctx, Index c) {

          IndexAttributesCheck check = new IndexAttributesCheck(c.getModel(), c);
          check.check();

          if (check.getListOfErrors().size() > 0) {
            // TODO - build a proper error message

            // check in the list of errors that we have the right error           	
            return !ArraysUtils.checkListOfErrors(check.getListOfErrors(), ARRAYS_20302);
          }

          return true;
        }
      };
      break;
    }
    case ARRAYS_20303:
    {
      func = new ValidationFunction<Index>() {
        @Override
        public boolean check(ValidationContext ctx, Index c) {

          IndexAttributesCheck check = new IndexAttributesCheck(c.getModel(), c);
          check.check();

          if (check.getListOfErrors().size() > 0) {
            // TODO - build a proper error message

            // check in the list of errors that we have the right error             
            return !ArraysUtils.checkListOfErrors(check.getListOfErrors(), ARRAYS_20303);
          }

          return true;
        }
      };
      break;
    }
    case ARRAYS_20304:
    {
      func = new ValidationFunction<Index>() {
        @Override
        public boolean check(ValidationContext ctx, Index c) {

          IndexAttributesCheck check = new IndexAttributesCheck(c.getModel(), c);
          check.check();

          if (check.getListOfErrors().size() > 0) {
            // TODO - build a proper error message

            // check in the list of errors that we have the right error             
            return !ArraysUtils.checkListOfErrors(check.getListOfErrors(), ARRAYS_20304);
          }

          return true;
        }
      };
      break;
    }
    case ARRAYS_20305:
    {
      func = new ValidationFunction<Index>() {
        @Override
        public boolean check(ValidationContext ctx, Index c) {

          IndexAttributesCheck check = new IndexAttributesCheck(c.getModel(), c);
          check.check();

          if (check.getListOfErrors().size() > 0) {
            // TODO - build a proper error message

            // check in the list of errors that we have the right error             
            return !ArraysUtils.checkListOfErrors(check.getListOfErrors(), ARRAYS_20305);
          }

          return true;
        }
      };
      break;
    }
    case ARRAYS_20307:
    {
      func = new ValidationFunction<Index>() {
        @Override
        public boolean check(ValidationContext ctx, Index c) {

          IndexAttributesCheck check = new IndexAttributesCheck(c.getModel(), c);
          check.check();

          if (check.getListOfErrors().size() > 0) {
            // TODO - build a proper error message

            // check in the list of errors that we have the right error             
            return !ArraysUtils.checkListOfErrors(check.getListOfErrors(), ARRAYS_20307);
          }

          return true;
        }
      };
      break;
    }
    case ARRAYS_20308:
    {
      func = new ValidationFunction<Index>() {
        @Override
        public boolean check(ValidationContext ctx, Index c) {

          IndexAttributesCheck check = new IndexAttributesCheck(c.getModel(), c);
          check.check();

          if (check.getListOfErrors().size() > 0) {
            // TODO - build a proper error message

            // check in the list of errors that we have the right error             
            return !ArraysUtils.checkListOfErrors(check.getListOfErrors(), ARRAYS_20308);
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
