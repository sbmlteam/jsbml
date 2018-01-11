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

import org.sbml.jsbml.ext.layout.BoundingBox;
import org.sbml.jsbml.ext.layout.LayoutConstants;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.DuplicatedElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageElementValidationFunction;;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the {@link BoundingBox} class.
 *  
 * @author rodrigue
 * @since 1.3
 */
public class BoundingBoxConstraints extends AbstractConstraintDeclaration {

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

      addRangeToSet(set, LAYOUT_21301, LAYOUT_21305);
      
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
    ValidationFunction<BoundingBox> func = null;

    switch (errorCode) {

    case LAYOUT_21301:
    {
      func = new UnknownCoreElementValidationFunction<BoundingBox>();
      break;
    }
    case LAYOUT_21302:
    {
      func = new UnknownCoreAttributeValidationFunction<BoundingBox>();
      break;
    }
    case LAYOUT_21303:
    {
      func = new ValidationFunction<BoundingBox>() {

        @Override
        public boolean check(ValidationContext ctx, BoundingBox boundingBox) {
          
          return boundingBox.isSetPosition() && boundingBox.isSetDimensions()
              && new DuplicatedElementValidationFunction<BoundingBox>(LayoutConstants.dimensions).check(ctx, boundingBox)
              && new DuplicatedElementValidationFunction<BoundingBox>(LayoutConstants.position).check(ctx, boundingBox)
              && new UnknownPackageElementValidationFunction<BoundingBox>(LayoutConstants.shortLabel).check(ctx, boundingBox);
        }
      };
      break;
    }
    case LAYOUT_21304:
    {
      func = new UnknownPackageAttributeValidationFunction<BoundingBox>(LayoutConstants.shortLabel);
      break;
    }
    case LAYOUT_21305:
    {
      func = new ValidationFunction<BoundingBox>() {

        @Override
        public boolean check(ValidationContext ctx, BoundingBox boundingBox) {
          
          if (boundingBox.isSetPosition() && boundingBox.isSetDimensions()) {
            return boundingBox.getPosition().isSetZ() ? true : !boundingBox.getDimensions().isSetDepth();
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
