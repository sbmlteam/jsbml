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

import org.sbml.jsbml.ext.spatial.CSGObject;
import org.sbml.jsbml.ext.spatial.Geometry;
import org.sbml.jsbml.ext.spatial.SpatialConstants;
import org.sbml.jsbml.ext.spatial.SpatialModelPlugin;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.DuplicatedElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.InvalidAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageElementValidationFunction;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the {@link CSGObject} class.
 * 
 * @author Bhavye Jain
 * @since 1.5
 */
public class CSGObjectConstraints extends AbstractConstraintDeclaration {

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
        addRangeToSet(set, SPATIAL_22301, SPATIAL_22307);
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
    ValidationFunction<CSGObject> func = null;

    switch (errorCode) {

    case SPATIAL_22301:
    {
      // A CSGObject object may have the optional SBML Level 3 Core attributes metaid and sboTerm. 
      // No other attributes from the SBML Level 3 Core namespaces are permitted on a CSGObject.
      
      func = new UnknownCoreAttributeValidationFunction<CSGObject>();
      break;
    }
    
    case SPATIAL_22302:
    {
      // A CSGObject object may have the optional SBML Level 3 Core subobjects for notes and 
      // annotations. No other elements from the SBML Level 3 Core namespaces are permitted on 
      // a CSGObject.

      func = new UnknownCoreElementValidationFunction<CSGObject>();
      break;
    }
    
    case SPATIAL_22303:
    {
      // A CSGObject object must have the required attributes spatial:id and spatial:domainType, 
      // and may have the optional attributes spatial:name and spatial:ordinal. No other attributes 
      // from the SBML Level 3 Spatial Processes namespaces are permitted on a CSGObject object.

      func = new UnknownPackageAttributeValidationFunction<CSGObject>(SpatialConstants.shortLabel) {
        
        @Override
        public boolean check(ValidationContext ctx, CSGObject csgobject) {
          
          if(!csgobject.isSetId()) {
            return false;
          }
          if(!csgobject.isSetDomainType()) {
            return false;
          }
          
          return super.check(ctx, csgobject);
        }
      };
      break;
    }
    
    case SPATIAL_22304:
    {
      // A CSGObject object must contain one and only one instance of the CSGNode element. No 
      // other elements from the SBML Level 3 Spatial Processes namespaces are permitted on a 
      // CSGObject object.

      func = new ValidationFunction<CSGObject>() {
        
        @Override
        public boolean check(ValidationContext ctx, CSGObject csgobject) {
      
          return new DuplicatedElementValidationFunction<CSGObject>(SpatialConstants.csgSetOperator).check(ctx, csgobject)
              && new DuplicatedElementValidationFunction<CSGObject>(SpatialConstants.csgPrimitive).check(ctx, csgobject)
              && new DuplicatedElementValidationFunction<CSGObject>(SpatialConstants.csgTransformation).check(ctx, csgobject)
              && new UnknownPackageElementValidationFunction<CSGObject>(SpatialConstants.shortLabel).check(ctx, csgobject);
        }
      };
      break;
    }
    
    case SPATIAL_22305:
    {
      // The value of the attribute spatial:domainType of a CSGObject object must be the identifier 
      // of an existing DomainType object defined in the enclosing Model object.

      func = new ValidationFunction<CSGObject>() {
        
        @Override
        public boolean check(ValidationContext ctx, CSGObject csgobject) {
          
          if(csgobject.isSetDomainType()) {
            SpatialModelPlugin smp = (SpatialModelPlugin) csgobject.getModel().getPlugin(SpatialConstants.shortLabel);
            if(smp.isSetGeometry()) {
              Geometry g = smp.getGeometry();
              if(g.getDomainType(csgobject.getDomainType()) == null) {
                return false;
              }
            }
          }          
          return true;
        }
      };
      break;
    }
    
    case SPATIAL_22306:
    {
      // The attribute spatial:name on a CSGObject must have a value of data type string.
      
      //nothing to check as Java reads any kind of string
      break;
    }
    
    case SPATIAL_22307:
    {
      // The attribute spatial:ordinal on a CSGObject must have a value of data type integer.

      func = new InvalidAttributeValidationFunction<CSGObject>(SpatialConstants.ordinal);
      break;
    }
    }    

    return func;
  }

}
