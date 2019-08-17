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

import org.sbml.jsbml.ext.spatial.DomainType;
import org.sbml.jsbml.ext.spatial.Geometry;
import org.sbml.jsbml.ext.spatial.SpatialConstants;
import org.sbml.jsbml.ext.spatial.SpatialModelPlugin;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.InvalidAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the {@link DomainType} class.
 * 
 * @author Bhavye Jain
 * @since 1.5
 */
public class DomainTypeConstraints extends AbstractConstraintDeclaration {

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
        addRangeToSet(set, SPATIAL_20701, SPATIAL_20705);
        addRangeToSet(set, SPATIAL_20750, SPATIAL_20752);
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
    ValidationFunction<DomainType> func = null;

    switch (errorCode) {
    case SPATIAL_20701:
    {
      // A DomainType object may have the optional SBML Level 3 Core attributes metaid and sboTerm. 
      // No other attributes from the SBML Level 3 Core namespaces are permitted on a DomainType.

      func = new UnknownCoreAttributeValidationFunction<DomainType>();
      break;
    }

    case SPATIAL_20702:
    {
      // A DomainType object may have the optional SBML Level 3 Core subobjects for notes and annotations.
      // No other elements from the SBML Level 3 Core namespaces are permitted on a DomainType.

      func = new UnknownCoreElementValidationFunction<DomainType>();
      break;
    }

    case SPATIAL_20703:
    {
      // A DomainType object must have the required attributes spatial:id and spatial:spatialDimensions.
      // No other attributes from the SBML Level 3 Spatial Processes namespaces are permitted on 
      // a DomainType object.

      func = new UnknownPackageAttributeValidationFunction<DomainType>(SpatialConstants.shortLabel) {

        @Override
        public boolean check(ValidationContext ctx, DomainType dt) {

          if(!dt.isSetId()) {
            return false;
          }
          if(!dt.isSetSpatialDimensions()) {
            return false;
          }
          return super.check(ctx, dt);
        }
      };

      break;
    }

    case SPATIAL_20704:
    {
      // The attribute spatial:spatialDimensions on a DomainType must have a value of data type integer.

      func = new InvalidAttributeValidationFunction<DomainType>(SpatialConstants.spatialDimensions);
      break;
    }

    case SPATIAL_20705:
    {
      // The attribute spatial:name on a DomainType must have a value of data type string.

      func = new ValidationFunction<DomainType>() {

        @Override
        public boolean check(ValidationContext ctx, DomainType dt) {

          // nothing to check as java read any kind of String

          return true;
        }
      };

      break;
    }
    
    case SPATIAL_20750:
    {
      func = new ValidationFunction<DomainType>() {
        
        @Override
        public boolean check(ValidationContext ctx, DomainType dt) {
          
          SpatialModelPlugin smp = (SpatialModelPlugin) dt.getModel().getPlugin(SpatialConstants.shortLabel);
          Geometry g = smp.getGeometry();
          int coord_components = g.getListOfCoordinateComponents().getNumChildren();
          
          if(coord_components == 3) {
            if(dt.getSpatialDimensions() == 2 || dt.getSpatialDimensions() == 3) {
              return true;
            }
            
            return false;
          }
          
          return true;
        }
      };
    }
    
    case SPATIAL_20751:
    {
      func = new ValidationFunction<DomainType>() {
        
        @Override
        public boolean check(ValidationContext ctx, DomainType dt) {
          
          SpatialModelPlugin smp = (SpatialModelPlugin) dt.getModel().getPlugin(SpatialConstants.shortLabel);
          Geometry g = smp.getGeometry();
          int coord_components = g.getListOfCoordinateComponents().getNumChildren();
          
          if(coord_components == 2) {
            if(dt.getSpatialDimensions() == 1 || dt.getSpatialDimensions() == 2) {
              return true;
            }
            
            return false;
          }
          
          return true;
        }
      };
    }
    
    case SPATIAL_20752:
    {
      func = new ValidationFunction<DomainType>() {
        
        @Override
        public boolean check(ValidationContext ctx, DomainType dt) {
          
          SpatialModelPlugin smp = (SpatialModelPlugin) dt.getModel().getPlugin(SpatialConstants.shortLabel);
          Geometry g = smp.getGeometry();
          int coord_components = g.getListOfCoordinateComponents().getNumChildren();
          
          if(coord_components == 1) {
            if(dt.getSpatialDimensions() == 0 || dt.getSpatialDimensions() == 1) {
              return true;
            }
            
            return false;
          }
          
          return true;
        }
      };
    }
    }		  

    return func;
  }

}
