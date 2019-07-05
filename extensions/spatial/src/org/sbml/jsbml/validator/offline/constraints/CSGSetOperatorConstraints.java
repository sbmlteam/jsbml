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

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.ext.spatial.CSGNode;
import org.sbml.jsbml.ext.spatial.CSGSetOperator;
import org.sbml.jsbml.ext.spatial.SpatialConstants;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.DuplicatedElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.InvalidAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageElementValidationFunction;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the {@link CSGSetOperator} class.
 * 
 * @author Bhavye Jain
 * @since 1.5
 */
public class CSGSetOperatorConstraints extends AbstractConstraintDeclaration {

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
        addRangeToSet(set, SPATIAL_23201, SPATIAL_23209);
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
    ValidationFunction<CSGSetOperator> func = null;

    switch (errorCode) {
    
    case SPATIAL_23201:
    {
      // A CSGSetOperator object may have the optional SBML Level 3 Core attributes metaid and 
      // sboTerm. No other attributes from the SBML Level 3 Core namespaces are permitted on a 
      // CSGSetOperator. 

      func = new UnknownCoreAttributeValidationFunction<CSGSetOperator>();
      break;
    }
    
    case SPATIAL_23202:
    {
      // A CSGSetOperator object may have the optional SBML Level 3 Core subobjects for notes and 
      // annotations. No other elements from the SBML Level 3 Core namespaces are permitted on a 
      // CSGSetOperator. 

      func = new UnknownCoreElementValidationFunction<CSGSetOperator>();
      break;
    }
    
    case SPATIAL_23203:
    {
      // A CSGSetOperator object must have the required attribute spatial:operationType, and 
      // may have the optional attributes spatial:complementA and spatial:complementB. No 
      // other attributes from the SBML Level 3 Spatial Processes namespaces are permitted on a 
      // CSGSetOperator object.

      func = new UnknownPackageAttributeValidationFunction<CSGSetOperator>(SpatialConstants.shortLabel) {
        
        @Override
        public boolean check(ValidationContext ctx, CSGSetOperator csgso) {
          
          if(!csgso.isSetOperationType()) {
            return false;
          }
          
          return super.check(ctx, csgso);
        }
      };
      
      break;
    }
    
    case SPATIAL_23204:
    {
      // A CSGSetOperator object may contain one and only one instance of the ListOfCSGNodes element. 
      // No other elements from the SBML Level 3 Spatial Processes namespaces are permitted on a 
      // CSGSetOperator object.

      func = new ValidationFunction<CSGSetOperator>() {
        
        @Override
        public boolean check(ValidationContext ctx, CSGSetOperator csgso) {
          
          return new DuplicatedElementValidationFunction<CSGSetOperator>(SpatialConstants.listOfCSGNodes).check(ctx, csgso)
              && new UnknownPackageElementValidationFunction<CSGSetOperator>(SpatialConstants.shortLabel).check(ctx, csgso);
        }
      };
      
      break;
    }
    
    case SPATIAL_23205:
    {
      // The value of the attribute spatial:operationType of a CSGSetOperator object must conform 
      // to the syntax of SBML data type SetOperation and may only take on the allowed values of 
      // SetOperation defined in SBML; that is, the value must be one of the following: “union”, 
      // “intersection” or “difference”.

      func = new InvalidAttributeValidationFunction<CSGSetOperator>(SpatialConstants.operationType);
      break;
    }
    
    case SPATIAL_23206:
    {
      // The value of the attribute spatial:complementA of a CSGSetOperator object must be the 
      // identifier of an existing CSGNode object defined in the enclosing Model object.

      func = new ValidationFunction<CSGSetOperator>() {
        
        @Override
        public boolean check(ValidationContext ctx, CSGSetOperator csgso) {
          if(csgso.isSetComplementA()) {
            if(csgso.isSetListOfCSGNodes()) {
              ListOf<CSGNode> losn = csgso.getListOfCSGNodes();
              for(int i = 0; i < losn.getNumChildren(); i++) {
                if(losn.getChildAt(i) instanceof CSGNode) {
                  CSGNode node = (CSGNode) losn.getChildAt(i);
                  if(node.getId().equals(csgso.getComplementA())){
                    return true;
                  }
                }
              }
              return false;
            }
            else {
              return false;
            }
          }
          
          return true;
        }
      };
      
      break;
    }
    
    case SPATIAL_23207:
    {
      // The value of the attribute spatial:complementB of a CSGSetOperator object must be the 
      // identifier of an existing CSGNode object defined in the enclosing Model object.

      func = new ValidationFunction<CSGSetOperator>() {
        
        @Override
        public boolean check(ValidationContext ctx, CSGSetOperator csgso) {
          if(csgso.isSetComplementB()) {
            if(csgso.isSetListOfCSGNodes()) {
              ListOf<CSGNode> losn = csgso.getListOfCSGNodes();
              for(int i = 0; i < losn.getNumChildren(); i++) {
                if(losn.getChildAt(i) instanceof CSGNode) {
                  CSGNode node = (CSGNode) losn.getChildAt(i);
                  if(node.getId().equals(csgso.getComplementB())){
                    return true;
                  }
                }
              }
              return false;
            }
            else {
              return false;
            }
          }
          
          return true;
        }
      };
      
      break;
    }
    
    case SPATIAL_23208:
    {
      // Apart from the general notes and annotations subobjects permitted on all SBML objects,  
      // a ListOfCSGNodes container object may only contain CSGNode objects. 

      func = new ValidationFunction<CSGSetOperator>() {
        
        @Override
        public boolean check(ValidationContext ctx, CSGSetOperator csgso) {
          
          if(csgso.isSetListOfCSGNodes()) {
            return new UnknownElementValidationFunction<ListOf<CSGNode>>().check(ctx, csgso.getListOfCSGNodes());
          }
          
          return true;
        }
      };
      
      break;
    }
    
    case SPATIAL_23209:
    {
      // A ListOfCSGNodes object may have the optional SBML Level 3 Core attributes metaid and 
      // sboTerm. No other attributes from the SBML Level 3 Core namespaces are permitted on a 
      // ListOfCSGNodes object.

      func = new ValidationFunction<CSGSetOperator>() {
        
        @Override
        public boolean check(ValidationContext ctx, CSGSetOperator csgso) {
          
          if(csgso.isSetListOfCSGNodes()) {
            return new UnknownAttributeValidationFunction<ListOf<CSGNode>>().check(ctx, csgso.getListOfCSGNodes());
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