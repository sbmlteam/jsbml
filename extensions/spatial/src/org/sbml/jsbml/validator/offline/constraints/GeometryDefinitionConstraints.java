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

import org.sbml.jsbml.ext.spatial.GeometryDefinition;
import org.sbml.jsbml.ext.spatial.SpatialConstants;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.InvalidAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the {@link GeometryDefinition} class.
 * 
 * @author Bhavye Jain
 * @since 1.5
 */
public class GeometryDefinitionConstraints extends AbstractConstraintDeclaration {

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
        addRangeToSet(set, SPATIAL_21201, SPATIAL_21205);
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
    ValidationFunction<GeometryDefinition> func = null;

    switch (errorCode) {

    case SPATIAL_21201:
    {
      // A GeometryDefinition object may have the optional SBML Level 3 Core attributes metaid and 
      // sboTerm. No other attributes from the SBML Level 3 Core namespaces are permitted on a GeometryDefinition.

      func = new UnknownCoreAttributeValidationFunction<GeometryDefinition>();
      break;
    }

    case SPATIAL_21202:
    {
      // A GeometryDefinition object may have the optional SBML Level 3 Core subobjects for notes 
      // and annotations. No other elements from the SBML Level 3 Core namespaces are permitted 
      // on a GeometryDefinition. 

      func = new UnknownCoreElementValidationFunction<GeometryDefinition>();
      break;
    }

    case SPATIAL_21203:
    {
      // A GeometryDefinition object must have the required attributes spatial:id and spatial:
      // isActive, and may have the optional attribute spatial:name. No other attributes from 
      // the SBML Level 3 Spatial Processes namespaces are permitted on a GeometryDefinition object. 		  	

      func = new UnknownPackageAttributeValidationFunction<GeometryDefinition>(SpatialConstants.shortLabel) {

        @Override
        public boolean check(ValidationContext ctx, GeometryDefinition gdef) {

          if(!gdef.isSetId()) {
            return false;
          }
          if(!gdef.isSetIsActive()) {
            return false;
          }
          return super.check(ctx, gdef);
        }
      };
      break;
    }

    case SPATIAL_21204:
    {
      // The attribute spatial:isActive on a GeometryDefinition must have a value of data type boolean.

      func = new InvalidAttributeValidationFunction<GeometryDefinition>(SpatialConstants.isActive);
      break;
    }

    case SPATIAL_21205:
    {
      // The attribute spatial:name on a GeometryDefinition must have a value of data type string.

      func = new ValidationFunction<GeometryDefinition>() {

        @Override
        public boolean check(ValidationContext ctx, GeometryDefinition gdef) {

          //nothing to check as Java reads any kind of string
          return true;
        }
      };

      break;
    }
    }

    return func;
  }

}