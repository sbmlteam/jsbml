/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2019 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The Babraham Institute, Cambridge, UK
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

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.ext.multi.MultiCompartmentPlugin;
import org.sbml.jsbml.ext.multi.MultiModelPlugin;
import org.sbml.jsbml.ext.multi.MultiSpeciesType;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownElementValidationFunction;

/**
 * Constraints declaration for the {@link MultiCompartmentPlugin} class.
 * 
 * @author Nicolas Rodriguez
 * @since 1.5
 */
public class MultiModelPluginConstraints extends AbstractConstraintDeclaration{

  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category, ValidationContext context) {

    switch (category) {
    case GENERAL_CONSISTENCY:

      if (level >= 3)
      {
        // extended SBML constraints
        addRangeToSet(set, MULTI_20101, MULTI_20103);
        
        addRangeToSet(set, MULTI_20201, MULTI_20204);
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
  }

  @Override
  public void addErrorCodesForAttribute(Set<Integer> set, int level,
    int version, String attributeName, ValidationContext context) {

    /*switch (attributeName) {
      // TODO
    }*/


  }

  @Override
  public ValidationFunction<MultiModelPlugin> getValidationFunction(int errorCode, ValidationContext context) {
    ValidationFunction<MultiModelPlugin> func = null;

    switch (errorCode) {

    case MULTI_20201:
    {
      func = new UnknownAttributeValidationFunction<MultiModelPlugin>() {
        @Override
        public boolean check(ValidationContext ctx, MultiModelPlugin multiM) {

          // TODO
          // There may be at most one ListOfSpeciesTypes container object within a Model object
          return true;
        }
      };
      break;
    }
    case MULTI_20202:
    {
      func = new ValidationFunction<MultiModelPlugin>() {
        @Override
        public boolean check(ValidationContext ctx, MultiModelPlugin multiM) {

          // A ListOfSpeciesTypes object within an extended Model object is optional, but if present, must not be empty
          if (multiM.isSetListOfSpeciesTypes()) {
            return multiM.getListOfSpeciesTypes().size() > 0;
          }
          
          return true;
        }
      };
      break;
    }
    case MULTI_20203:
    {
      func = new ValidationFunction<MultiModelPlugin>() {
        @Override
        public boolean check(ValidationContext ctx, MultiModelPlugin multiM) {

          // A ListOfSpeciesTypes object may have the optional SBML core attributes metaid and sboTerm.
          // No other attributes from the SBML Level 3 Core namespace or the Multi namespace are
          // permitted on a ListOfSpeciesTypes object
          // TODO
          
          return true;
        }
      };
      break;
    }
    case MULTI_20204:
    {
      func = new ValidationFunction<MultiModelPlugin>() {
        @Override
        public boolean check(ValidationContext ctx, MultiModelPlugin multiM) {
          // Apart from the general notes and annotation subobjects permitted on all SBML objects,
          // a ListOfSpeciesTypes container object may only contain SpeciesType objects
          
          return new UnknownElementValidationFunction<ListOf<MultiSpeciesType>>().check(ctx, multiM.getListOfSpeciesTypes());
        }
      };
      break;
    }
    case MULTI_20101: // TODO - we need to find a way to validate those rules even if the MultiModelPlugin is not there
    {
      func = new ValidationFunction<MultiModelPlugin>() {
        @Override
        public boolean check(ValidationContext ctx, MultiModelPlugin multiM) {

          // The multi:required attribute is required on the <sbml> element in the Multi package.
          // TODO
          return true;
        }
      };
      break;
    }
    case MULTI_20102:
    {
      func = new ValidationFunction<MultiModelPlugin>() {
        @Override
        public boolean check(ValidationContext ctx, MultiModelPlugin multiM) {
          
          // The required attribute on the <sbml> element must be Boolean.
          // TODO
          return true;
        }
      };
      break;
    }
    case MULTI_20103:
    {
      func = new ValidationFunction<MultiModelPlugin>() {
        @Override
        public boolean check(ValidationContext ctx, MultiModelPlugin multiM) {

          // The value of the multi:required attribute on the <sbml> element must be true.
          // TODO
          return true;
        }
      };
      break;
    }
    }
    
    return func;
  }
}
