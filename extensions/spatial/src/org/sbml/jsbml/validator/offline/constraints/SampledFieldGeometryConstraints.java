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

import java.util.ArrayList;
import java.util.Set;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.ext.spatial.Geometry;
import org.sbml.jsbml.ext.spatial.SampledFieldGeometry;
import org.sbml.jsbml.ext.spatial.SampledVolume;
import org.sbml.jsbml.ext.spatial.SpatialConstants;
import org.sbml.jsbml.ext.spatial.SpatialModelPlugin;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.DuplicatedElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageElementValidationFunction;
import org.sbml.jsbml.validator.offline.factory.SBMLErrorCodes;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the {@link SampledFieldGeometry} class.
 * 
 * @author Bhavye Jain
 * @since 1.5
 */
public class SampledFieldGeometryConstraints extends AbstractConstraintDeclaration {

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
        addRangeToSet(set, SPATIAL_21501, SPATIAL_21507);
        set.add(SPATIAL_21752);
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
    ValidationFunction<SampledFieldGeometry> func = null;

    switch (errorCode) {
    case SPATIAL_21501:
    {
      // A SampledFieldGeometry object may have the optional SBML Level 3 Core attributes metaid 
      // and sboTerm. No other attributes from the SBML Level 3 Core namespaces are permitted on a 
      // SampledFieldGeometry.

      func = new UnknownCoreAttributeValidationFunction<SampledFieldGeometry>();
      break;
    }

    case SPATIAL_21502:
    {
      // A SampledFieldGeometry object may have the optional SBML Level 3 Core subobjects for
      // notes and annotations. No other elements from the SBML Level 3 Core namespaces are 
      // permitted on a SampledFieldGeometry. 

      func = new UnknownCoreElementValidationFunction<SampledFieldGeometry>();
      break;
    }

    case SPATIAL_21503:
    {
      // A SampledFieldGeometry object must have the required attribute spatial:sampledField. 
      // No other attributes from the SBML Level 3 Spatial Processes namespaces are permitted 
      // on a SampledFieldGeometry object.

      func = new UnknownPackageAttributeValidationFunction<SampledFieldGeometry>(SpatialConstants.shortLabel) {

        @Override
        public boolean check(ValidationContext ctx, SampledFieldGeometry sfg) {

          if(!sfg.isSetSampledField()) {
            return false;
          }

          return super.check(ctx, sfg);
        }
      };
      break;
    }

    case SPATIAL_21504:
    {
      // A SampledFieldGeometry object may contain one and only one instance of the ListOfSampledVolumes 
      // element. No other elements from the SBML Level 3 Spatial Processes namespaces are permitted on 
      // a SampledFieldGeometry object.

      func = new ValidationFunction<SampledFieldGeometry>() {

        @Override
        public boolean check(ValidationContext ctx, SampledFieldGeometry sfg) {

          return new DuplicatedElementValidationFunction<SampledFieldGeometry>(SpatialConstants.listOfSampledVolumes).check(ctx, sfg)
              && new UnknownPackageElementValidationFunction<SampledFieldGeometry>(SpatialConstants.shortLabel).check(ctx, sfg);
        }
      };                
      break;
    }

    case SPATIAL_21505:
    {
      // The value of the attribute spatial:sampledField of a SampledFieldGeometry object must be  
      // the identifier of an existing SampledField object defined in the enclosing Model object.

      func = new ValidationFunction<SampledFieldGeometry>() {

        @Override
        public boolean check(ValidationContext ctx, SampledFieldGeometry sfg) {

          if(sfg.isSetSampledField()) {
            SpatialModelPlugin smp = (SpatialModelPlugin) sfg.getModel().getPlugin(SpatialConstants.shortLabel);
            if(smp.isSetGeometry()) {
              Geometry g = smp.getGeometry();
              if(g.getSampledField(sfg.getSampledField()) == null) {
                return false;
              }
            }
          }

          return true;
        }
      };
      break;
    }

    case SPATIAL_21506:
    {
      // Apart from the general notes and annotations subobjects permitted on all SBML objects, a 
      // ListOfSampledVolumes container object may only contain SampledVolume objects.

      func = new ValidationFunction<SampledFieldGeometry>() {

        @Override
        public boolean check(ValidationContext ctx, SampledFieldGeometry sfg) {

          if(sfg.isSetListOfSampledVolumes()) {
            return new UnknownElementValidationFunction<ListOf<SampledVolume>>().check(ctx, sfg.getListOfSampledVolumes());
          }
          return true;
        }
      };
      break;
    }

    case SPATIAL_21507:
    {
      // A ListOfSampledVolumes object may have the optional SBML Level 3 Core attributes metaid 
      // and sboTerm. No other attributes from the SBML Level 3 Core namespaces are permitted on a 
      // ListOfSampledVolumes object.

      func = new ValidationFunction<SampledFieldGeometry>() {

        @Override
        public boolean check(ValidationContext ctx, SampledFieldGeometry sfg) {

          if(sfg.isSetListOfSampledVolumes()) {
            return new UnknownAttributeValidationFunction<ListOf<SampledVolume>>().check(ctx, sfg.getListOfSampledVolumes());
          }
          return true;
        }
      };
      break;
    }
    
    case SPATIAL_21752: // as well SPATIAL_21753 and SPATIAL_21754
    {
      // SPATIAL_21752: The spatial:sampledValue of one SampledVolume may not be the same as a spatial:sam-
      // pledValue from a different SampledVolume from the same SampledFieldGeometry. 

      // spatial-21753: The spatial:sampledValue from one SampledVolume may not be less than the value of
      // the attribute spatial:maxValue and greater than or equal to the value of the attribute
      // spatial:minValue of a different SampledVolume from the same SampledFieldGeometry.

      // spatial-21754: The spatial:minValue and spatial:maxValue attribute values from one SampledVolume
      //  may not define a range that overlaps the spatial:minValue and spatial:maxValue attribute
      //  values of a different SampledVolume from the same SampledFieldGeometry, with the exception
      //  that the spatial:maxValue of one SampledVolume may equal the spatial:minValue
      //  of another SampledVolume from the same SampledFieldGeometry.
      func = new AbstractValidationFunction<SampledFieldGeometry>() {
        
        @Override
        public boolean check(ValidationContext ctx, SampledFieldGeometry sfg) {
          
          boolean check = true;
          ArrayList<Double> sampledValues = new ArrayList<Double>();
          ArrayList<Double> minValues = new ArrayList<Double>();
          ArrayList<Double> maxValues = new ArrayList<Double>();

          for (SampledVolume sv : sfg.getListOfSampledVolumes()) {

            if (sv.isSetMinValue() && sv.isSetMaxValue() && sv.getMinValue() < sv.getMaxValue()) {

              for (int i = 0; i < minValues.size(); i++) {
                if (sv.getMinValue() >= minValues.get(i) && sv.getMinValue() < maxValues.get(i)) {
                  // adding error SPATIAL_21754
                  ValidationConstraint.logError(ctx, SBMLErrorCodes.SPATIAL_21754, sfg);
                  check = false;
                }
                if (sv.getMaxValue() > minValues.get(i) && sv.getMaxValue() < maxValues.get(i)) {
                  // adding error SPATIAL_21754
                  ValidationConstraint.logError(ctx, SBMLErrorCodes.SPATIAL_21754, sfg);
                  check = false;
                }
                if (sv.getMinValue() <= minValues.get(i) && sv.getMaxValue() >= maxValues.get(i)) {
                  // adding error SPATIAL_21754
                  ValidationConstraint.logError(ctx, SBMLErrorCodes.SPATIAL_21754, sfg);
                  check = false;
                }
              }

              minValues.add(sv.getMinValue());
              maxValues.add(sv.getMaxValue());
            }            
          }

          for (SampledVolume sv : sfg.getListOfSampledVolumes()) {
            if (sv.isSetSampledValue()) {
              if (sampledValues.contains(sv.getSampledValue())) {
                // adding error SPATIAL_21752
                ValidationConstraint.logError(ctx, SBMLErrorCodes.SPATIAL_21752, sfg);                
                check = false;
              } else {
                sampledValues.add(sv.getSampledValue());
              }
              
              for (int i = 0; i < minValues.size(); i++) {
                if (sv.getSampledValue() >= minValues.get(i) && sv.getSampledValue() < maxValues.get(i)) {
                  // adding error SPATIAL_21753
                  ValidationConstraint.logError(ctx, SBMLErrorCodes.SPATIAL_21753, sfg);
                  check = false;
                }
              }
            }
          }
          
          return check;
        }
      };
      break;
    }
    }    

    return func;
  }

}