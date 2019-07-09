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
import org.sbml.jsbml.ext.spatial.ParametricGeometry;
import org.sbml.jsbml.ext.spatial.ParametricObject;
import org.sbml.jsbml.ext.spatial.SpatialConstants;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.DuplicatedElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageElementValidationFunction;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the {@link ParametricGeometry} class.
 * 
 * @author Bhavye Jain
 * @since 1.5
 */
public class ParametricGeometryConstraints extends AbstractConstraintDeclaration {

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
        addRangeToSet(set, SPATIAL_22001, SPATIAL_22005);
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
    ValidationFunction<ParametricGeometry> func = null;

    switch (errorCode) {

    case SPATIAL_22001:
    {
      // A ParametricGeometry object may have the optional SBML Level 3 Core attributes metaid and 
      // sboTerm. No other attributes from the SBML Level 3 Core namespaces are permitted on a 
      // ParametricGeometry.

      func = new UnknownCoreAttributeValidationFunction<ParametricGeometry>();
      break;
    }

    case SPATIAL_22002:
    {
      // A ParametricGeometry object may have the optional SBML Level 3 Core subobjects for notes 
      // and annotations. No other elements from the SBML Level 3 Core namespaces are permitted on 
      // a ParametricGeometry.

      func = new UnknownCoreElementValidationFunction<ParametricGeometry>();
      break;
    }

    case SPATIAL_22003:
    {
      // A ParametricGeometry object may contain one and only one instance of each of the SpatialPoints  
      // and ListOfParametricObjects elements. No other elements from the SBML Level 3 Spatial Processes 
      // namespaces are permitted on a ParametricGeometry object.

      func = new ValidationFunction<ParametricGeometry>() {
        
        @Override
        public boolean check(ValidationContext ctx, ParametricGeometry pg) {
          
          return new DuplicatedElementValidationFunction<ParametricGeometry>(SpatialConstants.spatialPoints).check(ctx, pg)
              && new DuplicatedElementValidationFunction<ParametricGeometry>(SpatialConstants.listOfParametricObjects).check(ctx, pg)
              && new UnknownPackageElementValidationFunction<ParametricGeometry>(SpatialConstants.shortLabel).check(ctx, pg);
        }
      };
      break;
    }

    case SPATIAL_22004:
    {
      // Apart from the general notes and annotations subobjects permitted on all SBML objects, a 
      // ListOfParametricObjects container object may only contain ParametricObject objects.

      func = new ValidationFunction<ParametricGeometry>() {
        
        @Override
        public boolean check(ValidationContext ctx, ParametricGeometry pg) {
          
          if(pg.isSetListOfParametricObjects()) {
            return new UnknownElementValidationFunction<ListOf<ParametricObject>>().check(ctx, pg.getListOfParametricObjects());
          }
          return true;
        }
      };
      break;
    }

    case SPATIAL_22005:
    {
      // A ListOfParametricObjects object may have the optional SBML Level 3 Core attributes metaid 
      // and sboTerm. No other attributes from the SBML Level 3 Core namespaces are permitted on a 
      // ListOfParametricObjects object.

      func = new ValidationFunction<ParametricGeometry>() {
        
        @Override
        public boolean check(ValidationContext ctx, ParametricGeometry pg) {
          
          if(pg.isSetListOfParametricObjects()) {
            return new UnknownAttributeValidationFunction<ListOf<ParametricObject>>().check(ctx, pg.getListOfParametricObjects());
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
