/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * 
 * Copyright (C) 2009-2020 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
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
import org.sbml.jsbml.ext.fbc.FBCConstants;
import org.sbml.jsbml.ext.fbc.FluxObjective;
import org.sbml.jsbml.ext.fbc.Objective;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.DuplicatedOrMissingElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.InvalidAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;;

/**
 * @author Nicolas Rodriguez
 * @since 1.5
 */
public class ObjectiveConstraints extends AbstractConstraintDeclaration {

  /* (non-Javadoc)
   * @see org.sbml.jsbml.validator.offline.constraints.ConstraintDeclaration#addErrorCodesForAttribute(java.util.Set, int, int, java.lang.String)
   */
  @Override
  public void addErrorCodesForAttribute(Set<Integer> set, int level,
    int version, String attributeName, ValidationContext context) 
  {
    // TODO

  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.validator.offline.constraints.ConstraintDeclaration#addErrorCodesForCheck(java.util.Set, int, int, org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY)
   */
  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category, ValidationContext context) 
  {
    // may be package version not need for Objective
    Integer packageVersionToCheck = context.getPackageVersion(FBCConstants.shortLabel);

    if (packageVersionToCheck == null || packageVersionToCheck == -1) {
      return;
    }
    
    switch (category) {
    case GENERAL_CONSISTENCY:

      addRangeToSet(set, FBC_20501, FBC_20509);
      
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
  public ValidationFunction<?> getValidationFunction(int errorCode, ValidationContext context) {
    ValidationFunction<Objective> func = null;

    switch (errorCode) {
    
    case FBC_20501:
      func = new UnknownCoreAttributeValidationFunction<Objective>();
      break;
    case FBC_20502:{
      func = new UnknownCoreElementValidationFunction<Objective>();
      break;
    }
    case FBC_20503:{
      // must have the required attributes fbc:id and fbc:type, no other attributes from fbc allowed
      func = new UnknownPackageAttributeValidationFunction<Objective>(FBCConstants.shortLabel){
        
        @Override
        public boolean check(ValidationContext ctx, Objective o) {
          
          if (!(o.isSetId() && o.isSetType())) {
            return false;
          }
          
          return super.check(ctx, o);
        }
      };
      break;
    }
    // case FBC_20504: nothing to check
    case FBC_20505:{
      // The attribute fbc:type on an Objective must be of the data type FbcType and thus its value
      // must be one of 'minimize' or 'maximize'.
      func = new InvalidAttributeValidationFunction<>(FBCConstants.type);
      break;
    }
    case FBC_20506:{
      // An Objective object must have one and only one instance of the ListOfFluxObjectives object.
      func = new DuplicatedOrMissingElementValidationFunction<Objective>(FBCConstants.listOfFluxObjectives);
      break;
    }
    case FBC_20507:{
      // The ListOfFluxObjectives subobject within an Objective object must not be empty.
      func = new ValidationFunction<Objective>(){
        
        @Override
        public boolean check(ValidationContext ctx, Objective o) {

          if (o.isSetListOfFluxObjectives()) {
            return o.getListOfFluxObjectives().size() > 0;
          }
          
          return true;
        }
      };
      break;
    }
    case FBC_20508:{
      // Apart from the general notes and annotation subobjects permitted on all SBML objects, a
      // ListOfFluxObjectives container object may only contain FluxObjective objects.
      func = new ValidationFunction<Objective>(){
        
        @Override
        public boolean check(ValidationContext ctx, Objective o) {

          if (o.isSetListOfFluxObjectives()) {
            return new UnknownElementValidationFunction<ListOf<FluxObjective>>().check(ctx, o.getListOfFluxObjectives());
          }
          
          return true;
        }
      };
      break;
    }
    case FBC_20509:{
      // A ListOfFluxObjectives object may have the optional metaid and sboTerm defined by SBML
      // Level 3 Core. No other attributes from the SBML Level 3 Core namespace or the Flux Balance
      // Constraints namespace are permitted on a ListOfFluxObjectives object.
      func = new ValidationFunction<Objective>(){
        
        @Override
        public boolean check(ValidationContext ctx, Objective o) {

          if (o.isSetListOfFluxObjectives()) {
            return new UnknownAttributeValidationFunction<ListOf<FluxObjective>>().check(ctx, o.getListOfFluxObjectives());
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
