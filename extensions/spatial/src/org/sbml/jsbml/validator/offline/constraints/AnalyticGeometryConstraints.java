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
import org.sbml.jsbml.ext.spatial.AnalyticGeometry;
import org.sbml.jsbml.ext.spatial.AnalyticVolume;
import org.sbml.jsbml.ext.spatial.SpatialConstants;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.DuplicatedElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageElementValidationFunction;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the {@link AnalyticGeometry} class.
 * 
 * @author Bhavye Jain
 * @since 1.5
 */
public class AnalyticGeometryConstraints extends AbstractConstraintDeclaration {

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
        addRangeToSet(set, SPATIAL_21801, SPATIAL_21805);
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
    ValidationFunction<AnalyticGeometry> func = null;

    switch (errorCode) {
    case SPATIAL_21801:
    {
      // An AnalyticGeometry object may have the optional SBML Level 3 Core attributes metaid and 
      // sboTerm. No other attributes from the SBML Level 3 Core namespaces are permitted on an 
      // AnalyticGeometry.

      func = new UnknownCoreAttributeValidationFunction<AnalyticGeometry>();
      break;
    }

    case SPATIAL_21802:
    {
      // An AnalyticGeometry object may have the optional SBML Level 3 Core subobjects for notes 
      // and annotations. No other elements from the SBML Level 3 Core namespaces are permitted 
      // on an AnalyticGeometry. 

      func = new UnknownCoreElementValidationFunction<AnalyticGeometry>();
      break;
    }

    case SPATIAL_21803:
    {
      // An AnalyticGeometry object may contain one and only one instance of the ListOfAnalyticVolumes
      // element. No other elements from the SBML Level 3 Spatial Processes namespaces are permitted
      // on an AnalyticGeometry object.

      func = new ValidationFunction<AnalyticGeometry>() {

        @Override
        public boolean check(ValidationContext ctx, AnalyticGeometry ag) {

          boolean loav = ag.isSetListOfAnalyticVolumes() && new DuplicatedElementValidationFunction<AnalyticGeometry>(SpatialConstants.listOfAnalyticVolumes).check(ctx, ag);
          boolean noOtherElement = new UnknownPackageElementValidationFunction<AnalyticGeometry>(SpatialConstants.shortLabel).check(ctx, ag);
          return loav && noOtherElement;
        }
      };
      break;
    }

    case SPATIAL_21804:
    {
      // Apart from the general notes and annotations subobjects permitted on all SBML objects, a 
      // ListOfAnalyticVolumes container object may only contain AnalyticVolume objects. 

      func = new ValidationFunction<AnalyticGeometry>() {

        @Override
        public boolean check(ValidationContext ctx, AnalyticGeometry ag) {

          if(ag.isSetListOfAnalyticVolumes()) {
            return new UnknownElementValidationFunction<ListOf<AnalyticVolume>>().check(ctx, ag.getListOfAnalyticVolumes());
          }
          return true;
        }
      };
      break;
    }

    case SPATIAL_21805:
    {
      // A ListOfAnalyticVolumes object may have the optional SBML Level 3 Core attributes metaid 
      // and sboTerm. No other attributes from the SBML Level 3 Core namespaces are permitted on a 
      // ListOfAnalyticVolumes object.

      func = new ValidationFunction<AnalyticGeometry>() {

        @Override
        public boolean check(ValidationContext ctx, AnalyticGeometry ag) {

          if(ag.isSetListOfAnalyticVolumes()) {
            return new UnknownCoreAttributeValidationFunction<ListOf<AnalyticVolume>>().check(ctx, ag.getListOfAnalyticVolumes())
                && new UnknownPackageAttributeValidationFunction<ListOf<AnalyticVolume>>(SpatialConstants.shortLabel).check(ctx, ag.getListOfAnalyticVolumes());
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