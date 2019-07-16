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
import org.sbml.jsbml.ext.spatial.ParametricObject;
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
 * Defines validation rules (as {@link ValidationFunction} instances) for the {@link ParametricObject} class.
 * 
 * @author Bhavye Jain
 * @since 1.5
 */
public class ParametricObjectConstraints extends AbstractConstraintDeclaration {

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
        addRangeToSet(set, SPATIAL_22101, SPATIAL_22110);
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
    ValidationFunction<ParametricObject> func = null;

    switch (errorCode) {
    
    case SPATIAL_22101:
    {
      // A ParametricObject object may have the optional SBML Level 3 Core attributes metaid and 
      // sboTerm. No other attributes from the SBML Level 3 Core namespaces are permitted on a 
      // ParametricObject.

      func = new UnknownCoreAttributeValidationFunction<ParametricObject>();
      break;
    }
    
    case SPATIAL_22102:
    {
      // A ParametricObject object may have the optional SBML Level 3 Core subobjects for notes and 
      // annotations. No other elements from the SBML Level 3 Core namespaces are permitted on a 
      // ParametricObject.

      func = new UnknownCoreElementValidationFunction<ParametricObject>();
      break;
    }
    
    case SPATIAL_22103:
    {
      // A ParametricObject object must have the required attributes spatial:id, spatial:polygonType, 
      // spatial:domainType, spatial:pointIndex, spatial:pointIndexLength and spatial:compression, 
      // and may have the optional attributes spatial:name and spatial:dataType. No other attributes 
      // from the SBML Level 3 Spatial Processes namespaces are permitted on a ParametricObject object.

      func = new UnknownPackageAttributeValidationFunction<ParametricObject>(SpatialConstants.shortLabel) {
        
        @Override
        public boolean check(ValidationContext ctx, ParametricObject po) {
          
          if(!po.isSetId()) {
            return false;
          }
          if(!po.isSetPolygonType()) {
            return false;
          }
          if(!po.isSetDomainType()) {
            return false;
          }
          if(!po.isSetPointIndex()) {
            return false;
          }
          if(!po.isSetPointIndexLength()) {
            return false;
          }
          if(!po.isSetCompression()){
            return false;
          }
          
          return super.check(ctx, po);
        }
      };
      break;
    }
    
    case SPATIAL_22104:
    {
      // The value of the attribute spatial:polygonType of a ParametricObject object must conform 
      // to the syntax of SBML data type PolygonKind and may only take on the allowed values of 
      // PolygonKind defined in SBML; that is, the value must be one of the following: “triangle” 
      // or “quadrilateral”. 

      func = new InvalidAttributeValidationFunction<ParametricObject>(SpatialConstants.polygonType);
      break;
    }
    
    case SPATIAL_22105:
    {
      // The value of the attribute spatial:domainType of a ParametricObject object must be the 
      // identifier of an existing DomainType object defined in the enclosing Model object.

      func = new ValidationFunction<ParametricObject>() {
        
        @Override
        public boolean check(ValidationContext ctx, ParametricObject po) {
          
          if(po.isSetDomainType()) {
            SpatialModelPlugin smp = (SpatialModelPlugin) po.getModel().getPlugin(SpatialConstants.shortLabel);
            if(smp.isSetGeometry()) {
              Geometry g = smp.getGeometry();
              if(g.getDomainType(po.getDomainType()) != null) {
                return true;
              }
              return false;
            }
          }
          
          return true;
        }
      };
      break;
    }
    
    case SPATIAL_22106:
    {
      // The value of the attribute spatial:pointIndex of a ParametricObject object must be an array 
      // of values of type int.
      func = new ValidationFunction<ParametricObject>() {
        
        @Override
        public boolean check(ValidationContext ctx, ParametricObject po) {
          
          if(po.isSetPointIndex()) {
            String pointIndex = po.getPointIndex().trim();
            StringTokenizer test = new StringTokenizer(pointIndex, " ;");
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
    
    case SPATIAL_22107:
    {
      // The attribute spatial:pointIndexLength on a ParametricObject must have a value of data 
      // type integer. 

      func = new InvalidAttributeValidationFunction<ParametricObject>(SpatialConstants.pointIndexLength);
      break;
    }
    
    case SPATIAL_22108:
    {
      // The value of the attribute spatial:compression of a ParametricObject object must conform 
      // to the syntax of SBML data type CompressionKind and may only take on the allowed values 
      // of CompressionKind defined in SBML; that is, the value must be one of the following: 
      // “uncompressed” or “deflated”.

      func = new InvalidAttributeValidationFunction<ParametricObject>(SpatialConstants.compression);
      break;
    }
    
    case SPATIAL_22109:
    {
      // The attribute spatial:name on a ParametricObject must have a value of data type string.

      func = new ValidationFunction<ParametricObject>() {
        
        @Override
        public boolean check(ValidationContext ctx, ParametricObject po) {
          
          // nothing to check as Java can read any kiind of string
          return true;
        }
      };
      break;
    }
    
    case SPATIAL_22110:
    {
      // The value of the attribute spatial:dataType of a ParametricObject object must conform to 
      // the syntax of SBML data type DataKind and may only take on the allowed values of DataKind 
      // defined in SBML; that is, the value must be one of the following: “double”, “float”, “uint8”, 
      // “uint16” or “uint32”.

      func = new InvalidAttributeValidationFunction<ParametricObject>(SpatialConstants.dataType);
      break;
    }
    }    

    return func;
  }

}
