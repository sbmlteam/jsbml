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

import org.sbml.jsbml.ext.spatial.Geometry;
import org.sbml.jsbml.ext.spatial.SampledField;
import org.sbml.jsbml.ext.spatial.SpatialConstants;
import org.sbml.jsbml.ext.spatial.SpatialModelPlugin;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.InvalidAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the {@link SampledField} class.
 * 
 * @author Bhavye Jain
 * @since 1.5
 */
public class SampledFieldConstraints extends AbstractConstraintDeclaration {

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
        addRangeToSet(set, SPATIAL_21601, SPATIAL_21612);
        addRangeToSet(set, SPATIAL_21650, SPATIAL_21652);
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
    ValidationFunction<SampledField> func = null;

    switch (errorCode) {
    
    case SPATIAL_21601:
    {
      // A SampledField object may have the optional SBML Level 3 Core attributes metaid and 
      // sboTerm. No other attributes from the SBML Level 3 Core namespaces are permitted on 
      // a SampledField.

      func = new UnknownCoreAttributeValidationFunction<SampledField>();
      break;
    }
    
    case SPATIAL_21602:
    {
      // A SampledField object may have the optional SBML Level 3 Core subobjects for notes and 
      // annotations. No other elements from the SBML Level 3 Core namespaces are permitted on a 
      // SampledField.

      func = new UnknownCoreElementValidationFunction<SampledField>();
      break;
    }
    
    case SPATIAL_21603:
    {
      // A SampledField object must have the required attributes spatial:id, spatial:dataType, 
      // spatial:numSamplesOne, spatial:interpolationType, spatial:compression, spatial:samples 
      // and spatial:samplesLength, and may have the optional attributes spatial:name, 
      // spatial:numSamplesTwo and spatial:numSamplesThree. No other attributes from the 
      // SBML Level 3 Spatial Processes namespaces are permitted on a SampledField object.

      func = new UnknownPackageAttributeValidationFunction<SampledField>(SpatialConstants.shortLabel) {
        
        @Override
        public boolean check(ValidationContext ctx, SampledField sf) {
          
          if(!sf.isSetId()) {
            return false;
          }
          if(!sf.isSetDataType()) {
            return false;
          }
          if(!sf.isSetNumSamples1()) {
            return false;
          }
          if(!sf.isSetInterpolationType()) {
            return false;
          }
          if(!sf.isSetCompression()) {
            return false;
          }
          if(!sf.isSetSamples()) {
            return false;
          }
          if(!sf.isSetSamplesLength()) {
            return false;
          }
          
          return super.check(ctx, sf);
        }
      };
      break;
    }
    
    case SPATIAL_21604:
    {
      // The value of the attribute spatial:dataType of a SampledField object must conform to the 
      // syntax of SBML data type DataKind and may only take on the allowed values of DataKind 
      // defined in SBML; that is, the value must be one of the following: “double”, “float”, “uint8”, 
      // “uint16” or “uint32”.

      func = new InvalidAttributeValidationFunction<SampledField>(SpatialConstants.dataType);
      break;
    }
    
    case SPATIAL_21605:
    {
      // The attribute spatial:numSamplesOne on a SampledField must have a value of data type integer.

      func = new InvalidAttributeValidationFunction<SampledField>(SpatialConstants.numSamples1);
      break;
    }
    
    case SPATIAL_21606:
    {
      // The value of the attribute spatial:interpolationType of a SampledField object must conform 
      // to the syntax of SBML data type InterpolationKind and may only take on the allowed values  
      // of InterpolationKind defined in SBML; that is, the value must be one of the following: 
      // “nearestNeighbor” or “linear”.

      func = new InvalidAttributeValidationFunction<SampledField>(SpatialConstants.interpolationType);
      break;
    }
    
    case SPATIAL_21607:
    {
      // The value of the attribute spatial:compression of a SampledField object must conform 
      // to the syntax of SBML data type CompressionKind and may only take on the allowed values 
      // of CompressionKind defined in SBML; that is, the value must be one of the following:
      // “uncompressed” or “deflated”.  

      func = new InvalidAttributeValidationFunction<SampledField>(SpatialConstants.compression);
      break;
    }
    
    case SPATIAL_21608:
    {
      // The value of the attribute spatial:samples of a SampledField object must be an array of 
      // values of type int.

      func = new ValidationFunction<SampledField>() {
        
        @Override
        public boolean check(ValidationContext ctx, SampledField sf) {
          
          if(sf.isSetSamples()) {
            String samples = sf.getSamples().trim();
            StringTokenizer test = new StringTokenizer(samples);
            while(test.hasMoreTokens()) {
              try {
                StringTools.parseSBMLInt(test.nextToken());
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
    
    case SPATIAL_21609:
    {
      // The attribute spatial:samplesLength on a SampledField must have a value of data type integer.

      func = new InvalidAttributeValidationFunction<SampledField>(SpatialConstants.samplesLength);
      break;
    }
    
    case SPATIAL_21610:
    {
      // The attribute spatial:name on a SampledField must have a value of data type string.

      func = new ValidationFunction<SampledField>() {
        
        @Override
        public boolean check(ValidationContext ctx, SampledField sf) {
          
          // nothing to check as Java can read any kind of string
          return true;
        }
      };
      break;
    }
    
    case SPATIAL_21611:
    {
      // The attribute spatial:numSamplesTwo on a SampledField must have a value of data type integer.

      func = new InvalidAttributeValidationFunction<SampledField>(SpatialConstants.numSamples2);
      break;
    }
    
    case SPATIAL_21612:
    {
      // The attribute spatial:numSamplesThree on a SampledField must have a value of data type integer.

      func = new InvalidAttributeValidationFunction<SampledField>(SpatialConstants.numSamples3);
      break;
    }
    
    case SPATIAL_21650:
    {
      func = new ValidationFunction<SampledField>() {
        
        @Override
        public boolean check(ValidationContext ctx, SampledField sf) {
          
          SpatialModelPlugin smp = (SpatialModelPlugin) sf.getModel().getPlugin(SpatialConstants.shortLabel);
          if(smp.isSetGeometry()) {
            Geometry g = smp.getGeometry();
            if(g.getCoordinateComponentCount() == 1) {
              if(!sf.isSetNumSamples1() || (sf.isSetNumSamples2() || sf.isSetNumSamples3())) {
                return false;
              }
            }
          }
          
          return true;
        }
      };
      break;
    }
    
    case SPATIAL_21651:
    {
      func = new ValidationFunction<SampledField>() {
        
        @Override
        public boolean check(ValidationContext ctx, SampledField sf) {
          
          SpatialModelPlugin smp = (SpatialModelPlugin) sf.getModel().getPlugin(SpatialConstants.shortLabel); 
          if(smp.isSetGeometry()) {
            Geometry g = smp.getGeometry();
            if(g.getCoordinateComponentCount() == 2) {
              if(!sf.isSetNumSamples1() || !sf.isSetNumSamples2() || sf.isSetNumSamples3()) {
                return false;
              }
            }
          }
          
          return true;
        }
      };
      break;
    }
    
    case SPATIAL_21652:
    {
      func = new ValidationFunction<SampledField>() {
        
        @Override
        public boolean check(ValidationContext ctx, SampledField sf) {
          
          SpatialModelPlugin smp = (SpatialModelPlugin) sf.getModel().getPlugin(SpatialConstants.shortLabel); 
          if(smp.isSetGeometry()) {
            Geometry g = smp.getGeometry();
            if(g.getCoordinateComponentCount() == 3) {
              if(!sf.isSetNumSamples1() || !sf.isSetNumSamples2() || !sf.isSetNumSamples3()) {
                return false;
              }
            }
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