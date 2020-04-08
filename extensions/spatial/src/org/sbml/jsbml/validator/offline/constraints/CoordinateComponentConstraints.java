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

import org.sbml.jsbml.ext.spatial.CoordinateComponent;
import org.sbml.jsbml.ext.spatial.SpatialConstants;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.DuplicatedElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.InvalidAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageElementValidationFunction;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the {@link CoordinateComponent} class.
 * 
 * @author Bhavye Jain
 * @since 1.5
 */
public class CoordinateComponentConstraints extends AbstractConstraintDeclaration {

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
        addRangeToSet(set, SPATIAL_21401, SPATIAL_21407);
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
    ValidationFunction<CoordinateComponent> func = null;

    switch (errorCode) {

    case SPATIAL_21401:
    {
      // A CoordinateComponent object may have the optional SBML Level 3 Core attributes metaid 
      // and sboTerm. No other attributes from the SBML Level 3 Core namespaces are permitted on  
      // a CoordinateComponent.

      func = new UnknownCoreAttributeValidationFunction<CoordinateComponent>();
      break;
    }

    case SPATIAL_21402:
    {
      // A CoordinateComponent object may have the optional SBML Level 3 Core subobjects for notes
      // and annotations. No other elements from the SBML Level 3 Core namespaces are permitted 
      // on a CoordinateComponent.

      func = new UnknownCoreElementValidationFunction<CoordinateComponent>();
      break;
    }

    case SPATIAL_21403:
    {
      // A CoordinateComponent object must have the required attributes spatial:id and spatial:type, 
      // and may have the optional attributes spatial:name and spatial:unit. No other attributes from  
      // the SBML Level 3 Spatial Processes namespaces are permitted on a CoordinateComponent object.

      func = new UnknownPackageAttributeValidationFunction<CoordinateComponent>(SpatialConstants.shortLabel) {

        @Override
        public boolean check(ValidationContext ctx, CoordinateComponent cc) {

          if(!cc.isSetId()) {
            return false;
          }
          if(!cc.isSetType()) {
            return false;
          }
          return super.check(ctx, cc);
        }
      };
      break;
    }

    case SPATIAL_21404:
    {
      // A CoordinateComponent object must contain one and only one instance of each of the two 
      // Boundary elements "boundaryMin" and "boundaryMax". No other elements from the SBML 
      // Level 3 Spatial Processes namespaces are permitted on a CoordinateComponent object.

      func = new ValidationFunction<CoordinateComponent>() {

        @Override
        public boolean check(ValidationContext ctx, CoordinateComponent cc) {
          boolean boundary_min = cc.isSetBoundaryMinimum() && new DuplicatedElementValidationFunction<CoordinateComponent>(SpatialConstants.boundaryMinimum).check(ctx, cc);
          boolean boundary_max = cc.isSetBoundaryMaximum() && new DuplicatedElementValidationFunction<CoordinateComponent>(SpatialConstants.boundaryMaximum).check(ctx, cc);
          boolean noOtherElement = new UnknownPackageElementValidationFunction<CoordinateComponent>(SpatialConstants.shortLabel).check(ctx, cc);
          return boundary_min && boundary_max && noOtherElement;
        }
      };
      break;
    }

    case SPATIAL_21405:
    {
      // The value of the attribute spatial:type of a CoordinateComponent object must conform 
      // to the syntax of SBML data type coordinateKind and may only take on the allowed values 
      // of coordinateKind defined in SBML; that is, the value must be one of the following: 
      // 'cartesianX', 'cartesianY' or 'cartesianZ'

      func = new InvalidAttributeValidationFunction<CoordinateComponent>(SpatialConstants.type);
      break;
    }

    case SPATIAL_21406:
    {
      // The attribute spatial:name on a CoordinateComponent must have a value of data type string.

      func = new ValidationFunction<CoordinateComponent>() {

        @Override
        public boolean check(ValidationContext ctx, CoordinateComponent cc) {

          // nothing to check as Java reads any kind of string.
          return true;
        }
      };
      break;
    }

    case SPATIAL_21407:
    {
      // The value of the attribute spatial:unit on a CoordinateComponent must have a taken from 
      // the following: the identifier of a UnitDefinition object in the enclosing Model, or one  
      // of the base units in SBML.

      func = new ValidationFunction<CoordinateComponent>() {

        @Override
        public boolean check(ValidationContext ctx, CoordinateComponent cc) {

          if(cc.isSetUnits()) {		  					
            return cc.getModel().getUnitDefinition(cc.getUnits()) != null;
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