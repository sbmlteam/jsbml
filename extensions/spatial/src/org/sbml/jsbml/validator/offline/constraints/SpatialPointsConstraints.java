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
import java.util.StringTokenizer;

import org.sbml.jsbml.ext.spatial.SpatialConstants;
import org.sbml.jsbml.ext.spatial.SpatialPoints;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.InvalidAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the {@link SpatialPoints} class.
 * 
 * @author Bhavye Jain
 * @since 1.5
 */
public class SpatialPointsConstraints extends AbstractConstraintDeclaration {

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
        addRangeToSet(set, SPATIAL_24001, SPATIAL_24008);
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
    ValidationFunction<SpatialPoints> func = null;

    switch (errorCode) {
    
    case SPATIAL_24001:
    {
      // A SpatialPoints object may have the optional SBML Level 3 Core attributes metaid and 
      // sboTerm. No other attributes from the SBML Level 3 Core namespaces are permitted on 
      // a SpatialPoints.

      func = new UnknownCoreAttributeValidationFunction<SpatialPoints>();
      break;
    }
    
    case SPATIAL_24002:
    {
      // A SpatialPoints object may have the optional SBML Level 3 Core subobjects for notes and 
      // annotations. No other elements from the SBML Level 3 Core namespaces are permitted on a 
      // SpatialPoints.

      func = new UnknownCoreElementValidationFunction<SpatialPoints>();
      break;
    }
    
    case SPATIAL_24003:
    {
      // A SpatialPoints object must have the required attributes spatial:compression, spatial:arrayData 
      // and spatial:arrayDataLength, and may have the optional attributes spatial:id, spatial:name  
      // and spatial:dataType. No other attributes from the SBML Level 3 Spatial Processes namespaces are 
      // permitted on a SpatialPoints object.

      func = new UnknownPackageAttributeValidationFunction<SpatialPoints>(SpatialConstants.shortLabel) {
        
        @Override
        public boolean check(ValidationContext ctx, SpatialPoints sp) {
          
          if(!sp.isSetCompression()) {
            return false;
          }
          if(!sp.isSetArrayData()) {
            return false;
          }
          if(!sp.isSetArrayDataLength()) {
            return false;
          }
          
          return super.check(ctx, sp);
        }
      };
      break;
    }
    
    case SPATIAL_24004:
    {
      // The value of the attribute spatial:compression of a SpatialPoints object must conform 
      // to the syntax of SBML data type CompressionKind and may only take on the allowed values 
      // of CompressionKind defined in SBML; that is, the value must be one of the following: 
      // “uncompressed” or “deflated”.

      func = new InvalidAttributeValidationFunction<SpatialPoints>(SpatialConstants.compression);
      break;
    }
    
    case SPATIAL_24005:
    {
      // The value of the attribute spatial:arrayData of a SpatialPoints object must be an array of 
      // values of type double.

      func = new ValidationFunction<SpatialPoints>() {
        
        @Override
        public boolean check(ValidationContext ctx, SpatialPoints sp) {
          
          if(sp.isSetArrayData()) {
            String arrayData = sp.getArrayData().trim();
            StringTokenizer test = new StringTokenizer(arrayData, " ;");
            while(test.hasMoreTokens()) {
              try {
                StringTools.parseSBMLDoubleStrict(test.nextToken());
              } catch (Exception e) {
                return false;
              }
            }
            return true;
          }
          
          return true;
        }
      };
      break;
    }
    
    case SPATIAL_24006:
    {
      // The attribute spatial:arrayDataLength on a SpatialPoints must have a value of data type integer.

      func = new InvalidAttributeValidationFunction<SpatialPoints>(SpatialConstants.arrayDataLength);
      break;
    }
    
    case SPATIAL_24007:
    {
      // The attribute spatial:name on a SpatialPoints must have a value of data type string.

      func = new ValidationFunction<SpatialPoints>() {
        
        @Override
        public boolean check(ValidationContext ctx, SpatialPoints sp) {
          
          // nothing to check as Java can read any kind of string 
          return true;
        }
      };
      break;
    }
    
    case SPATIAL_24008:
    {
      // The value of the attribute spatial:dataType of a SpatialPoints object must conform to the 
      // syntax of SBML data type DataKind and may only take on the allowed values of DataKind 
      // defined in SBML; that is, the value must be one of the following: “double”, “float”, “uint8”, 
      // “uint16” or “uint32”.

      func = new InvalidAttributeValidationFunction<SpatialPoints>(SpatialConstants.dataType);
      break;
    }
    }    

    return func;
  }

}
