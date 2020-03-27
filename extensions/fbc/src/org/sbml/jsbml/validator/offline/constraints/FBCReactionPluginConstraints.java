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

import org.sbml.jsbml.ext.fbc.FBCConstants;
import org.sbml.jsbml.ext.fbc.FBCReactionPlugin;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.SyntaxChecker;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.DuplicatedElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;;

/**
 * @author Nicolas Rodriguez
 * @since 1.5
 */
public class FBCReactionPluginConstraints extends AbstractConstraintDeclaration {

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
    Integer packageVersionToCheck = context.getPackageVersion(FBCConstants.shortLabel);

    if (packageVersionToCheck == null || packageVersionToCheck == -1) {
      return;
    }
    
    switch (category) {
    case GENERAL_CONSISTENCY:

      if (packageVersionToCheck >= 2) {
        
        addRangeToSet(set, FBC_20701, FBC_20716);
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
  public ValidationFunction<?> getValidationFunction(int errorCode, ValidationContext context) {
    ValidationFunction<FBCReactionPlugin> func = null;

    switch (errorCode) {
    
    case FBC_20701:
      // There may be at most one instance of a GeneProductAssociation within a Reaction object
      // using Flux Balance Constraints.
      // TODO - modify the fbc parser to count the number of elements when needed
      func = new DuplicatedElementValidationFunction<FBCReactionPlugin>(FBCConstants.geneProductAssociation);
      break;
    case FBC_20702:{
      // TODO - make sure the unknown attribute is processed 
      func = new UnknownPackageAttributeValidationFunction<FBCReactionPlugin>(FBCConstants.shortLabel);
      break;
    }
    case FBC_20703:{
      // The attribute fbc:lowerFluxBound of a Reaction must be of the data type SIdRef.
      func = new ValidationFunction<FBCReactionPlugin>(){
        
        @Override
        public boolean check(ValidationContext ctx, FBCReactionPlugin rp) {

          if (rp.isSetLowerFluxBound()) {
            return SyntaxChecker.isValidId(rp.getLowerFluxBound(), ctx.getLevel(), ctx.getVersion());
          }
          
          return true;
        }
      };
      break;
    }
    case FBC_20704:{
      // The attribute fbc:upperFluxBound of a Reaction must be of the data type SIdRef.
      func = new ValidationFunction<FBCReactionPlugin>(){
        
        @Override
        public boolean check(ValidationContext ctx, FBCReactionPlugin rp) {

          if (rp.isSetUpperFluxBound()) {
            return SyntaxChecker.isValidId(rp.getUpperFluxBound(), ctx.getLevel(), ctx.getVersion());
          }
          
          return true;
        }
      };
      break;
    }
    case FBC_20705:{
      // The attribute fbc:upperFluxBound of a Reaction must point to an existing Parameter in the model.
      func = new ValidationFunction<FBCReactionPlugin>(){
        
        @Override
        public boolean check(ValidationContext ctx, FBCReactionPlugin rp) {

          if (rp.isSetLowerFluxBound()) {
            return rp.getLowerFluxBoundInstance() != null;
          }
          
          return true;
        }
      };
      break;
    }
    case FBC_20706:{
      // The attribute fbc:upperFluxBound of a Reaction must point to an existing Parameter in the model.
      func = new ValidationFunction<FBCReactionPlugin>(){
        
        @Override
        public boolean check(ValidationContext ctx, FBCReactionPlugin rp) {

          if (rp.isSetUpperFluxBound()) {
            return rp.getUpperFluxBoundInstance() != null;
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
