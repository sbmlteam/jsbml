/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * 
 * Copyright (C) 2009-2018 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 5. The Babraham Institute, Cambridge, UK
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

import org.sbml.jsbml.JSBML;
import org.sbml.jsbml.ext.layout.Dimensions;
import org.sbml.jsbml.ext.layout.LayoutConstants;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageElementValidationFunction;
import org.sbml.jsbml.xml.XMLNode;;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the {@link Dimensions} class.
 *  
 * @author rodrigue
 * @since 1.3
 */
public class DimensionsConstraints extends AbstractConstraintDeclaration {

  /* (non-Javadoc)
   * @see org.sbml.jsbml.validator.offline.constraints.ConstraintDeclaration#addErrorCodesForAttribute(java.util.Set, int, int, java.lang.String)
   */
  @Override
  public void addErrorCodesForAttribute(Set<Integer> set, int level,
    int version, String attributeName, ValidationContext context) 
  {
    // TODO - implement

  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.validator.offline.constraints.ConstraintDeclaration#addErrorCodesForCheck(java.util.Set, int, int, org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY)
   */
  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category, ValidationContext context) {

    switch (category) {
    case GENERAL_CONSISTENCY:

      addRangeToSet(set, LAYOUT_21701, LAYOUT_21704);
      
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
  public ValidationFunction<?> getValidationFunction(int errorCode, ValidationContext context) {
    ValidationFunction<Dimensions> func = null;

    switch (errorCode) {

    case LAYOUT_21701:
    {
      func = new ValidationFunction<Dimensions>() {

        @Override
        public boolean check(ValidationContext ctx, Dimensions dimensions) {
          
          return new UnknownCoreElementValidationFunction<Dimensions>().check(ctx, dimensions)
              && new UnknownPackageElementValidationFunction<Dimensions>(LayoutConstants.shortLabel).check(ctx, dimensions);
        }
      };
      break;
    }
    case LAYOUT_21702:
    {
      func = new UnknownCoreAttributeValidationFunction<Dimensions>();
      break;
    }
    case LAYOUT_21703:
    {
      func = new UnknownPackageAttributeValidationFunction<Dimensions>(LayoutConstants.shortLabel) {

        @Override
        public boolean check(ValidationContext ctx, Dimensions dimensions) {
        
          if (! (dimensions.isSetWidth() && dimensions.isSetHeight())) {
            return false;
          }
          
          return super.check(ctx, dimensions);
        }
      };
      break;
    }
    case LAYOUT_21704:
    {
      func = new ValidationFunction<Dimensions>() {

        @Override
        public boolean check(ValidationContext ctx, Dimensions dimensions) {

          // check if width, height and depth are of type double
          if (dimensions.getUserObject(JSBML.UNKNOWN_XML) != null) {
            // checking if the width, height or depth attribute are there (meaning a NumberFormatException was encountered).
            XMLNode unknown = (XMLNode) dimensions.getUserObject(JSBML.UNKNOWN_XML);

            if ((!dimensions.isSetWidth()) && (unknown.getAttrIndex(LayoutConstants.width) != -1)) { 
              // TODO - create error message
              return false;
            }
            if ((!dimensions.isSetHeight()) && (unknown.getAttrIndex(LayoutConstants.height) != -1)) { 
              // TODO - create error message
              return false;
            }
            if ((!dimensions.isSetDepth()) && (unknown.getAttrIndex(LayoutConstants.depth) != -1)) { 
              // TODO - create error message
              return false;
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
