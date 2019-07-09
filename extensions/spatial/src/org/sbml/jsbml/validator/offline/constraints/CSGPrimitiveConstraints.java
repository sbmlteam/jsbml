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

import org.sbml.jsbml.ext.spatial.CSGPrimitive;
import org.sbml.jsbml.ext.spatial.SpatialConstants;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.InvalidAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the {@link CSGPrimitive} class.
 * 
 * @author Bhavye Jain
 * @since 1.5
 */
public class CSGPrimitiveConstraints extends AbstractConstraintDeclaration {

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
    CHECK_CATEGORY category, ValidationContext context) {
    switch (category) {
    case GENERAL_CONSISTENCY:
      if(level >= 3){        
        addRangeToSet(set, SPATIAL_23101, SPATIAL_23104);
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
  public ValidationFunction<?> getValidationFunction(int errorCode, ValidationContext context){
    ValidationFunction<CSGPrimitive> func = null;

    switch (errorCode) {
    
    case SPATIAL_23101:
    {
      // A CSGPrimitive object may have the optional SBML Level 3 Core attributes metaid and 
      // sboTerm. No other attributes from the SBML Level 3 Core namespaces are permitted on 
      // a CSGPrimitive.

      func = new UnknownCoreAttributeValidationFunction<CSGPrimitive>();
      break;
    }
    
    case SPATIAL_23102:
    {
      // A CSGPrimitive object may have the optional SBML Level 3 Core subobjects for notes and 
      // annotations. No other elements from the SBML Level 3 Core namespaces are permitted on a 
      // CSGPrimitive.

      func = new UnknownCoreElementValidationFunction<CSGPrimitive>();
      break;
    }
    
    case SPATIAL_23103:
    {
      // A CSGPrimitive object must have the required attribute spatial:primitiveType. No other
      // attributes from the SBML Level 3 Spatial Processes namespaces are permitted on a
      // CSGPrimitive object.

      func = new UnknownPackageAttributeValidationFunction<CSGPrimitive>(SpatialConstants.shortLabel) {
        
        @Override
        public boolean check(ValidationContext ctx, CSGPrimitive csgp) {
          
          if(!csgp.isSetPrimitiveType()) {
            return false;
          }
          
          return super.check(ctx, csgp);
        }
      };
      break;
    }
    
    case SPATIAL_23104:
    {
      // The value of the attribute spatial:primitiveType of a CSGPrimitive object must conform 
      // to the syntax of SBML data type PrimitiveKind and may only take on the allowed values of 
      // PrimitiveKind defined in SBML; that is, the value must be one of the following: “sphere”, 
      // “cube”, “cylinder”, “cone”, “circle” or “square”. 

      func = new InvalidAttributeValidationFunction<CSGPrimitive>(SpatialConstants.primitiveType);
      break;
    }
    }    

    return func;
  }

}