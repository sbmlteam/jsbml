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

import org.sbml.jsbml.ext.spatial.Geometry;
import org.sbml.jsbml.ext.spatial.SampledVolume;
import org.sbml.jsbml.ext.spatial.SpatialConstants;
import org.sbml.jsbml.ext.spatial.SpatialModelPlugin;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.InvalidAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the {@link SampledVolume} class.
 * 
 * @author Bhavye Jain
 * @since 1.5
 */
public class SampledVolumeConstraints extends AbstractConstraintDeclaration {

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
        addRangeToSet(set, SPATIAL_21701, SPATIAL_21708);
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
    ValidationFunction<SampledVolume> func = null;

    switch (errorCode) {
    
    case SPATIAL_21701:
    {
      // A SampledVolume object may have the optional SBML Level 3 Core attributes metaid and 
      // sboTerm. No other attributes from the SBML Level 3 Core namespaces are permitted on a 
      // SampledVolume.
      
      func = new UnknownCoreAttributeValidationFunction<SampledVolume>();
      break;
    }
    
    case SPATIAL_21702:
    {
      // A SampledVolume object may have the optional SBML Level 3 Core subobjects for notes and 
      // annotations. No other elements from the SBML Level 3 Core namespaces are permitted on a 
      // SampledVolume.
      
      func = new UnknownCoreElementValidationFunction<SampledVolume>();
      break;
    }
    
    case SPATIAL_21703:
    {
      // A SampledVolume object must have the required attributes spatial:id and spatial:domainType, 
      // and may have the optional attributes spatial:name, spatial:sampledValue, spatial:minValue
      // and spatial:maxValue. No other attributes from the SBML Level 3 Spatial Processes namespaces 
      // are permitted on a SampledVolume object.
      
      func = new UnknownPackageAttributeValidationFunction<SampledVolume>(SpatialConstants.shortLabel) {
        
        @Override
        public boolean check(ValidationContext ctx, SampledVolume sv) {
          
          if(!sv.isSetId()) {
            return false;
          }
          if(!sv.isSetDomainType()) {
            return false;
          }
          
          return super.check(ctx, sv);
        }
      };
      break;
    }
    
    case SPATIAL_21704:
    {
      // The value of the attribute spatial:domainType of a SampledVolume object must be the 
      // identifier of an existing DomainType object defined in the enclosing Model object.
      
      func = new ValidationFunction<SampledVolume>() {
        
        @Override
        public boolean check(ValidationContext ctx, SampledVolume sv) {
          
          if(sv.isSetDomainType()) {
            SpatialModelPlugin smp = (SpatialModelPlugin) sv.getModel().getPlugin(SpatialConstants.shortLabel);
            if(smp.isSetGeometry()) {
              Geometry g = smp.getGeometry();
              if(g.getDomainType(sv.getDomainType()) == null) {
                return false;
              }
            }
          }
          
          return true;
        }
      };
      break;
    }
    
    case SPATIAL_21705:
    {
      // The attribute spatial:name on a SampledVolume must have a value of data type string.
      
      // nothing to check as Java reads all kinds of string
      break;
    }
    
    case SPATIAL_21706:
    {
      // The attribute spatial:sampledValue on a SampledVolume must have a value of data type double.     
      
      func = new InvalidAttributeValidationFunction<SampledVolume>(SpatialConstants.sampledValue);
      break;
    }
    
    case SPATIAL_21707:
    {
      // The attribute spatial:minValue on a SampledVolume must have a value of data type double.

      func = new InvalidAttributeValidationFunction<SampledVolume>(SpatialConstants.minValue);
      break;
    }
    
    case SPATIAL_21708:
    {
      // The attribute spatial:maxValue on a SampledVolume must have a value of data type double.

      func = new InvalidAttributeValidationFunction<SampledVolume>(SpatialConstants.maxValue);
      break;
    }
    }    

    return func;
  }

}