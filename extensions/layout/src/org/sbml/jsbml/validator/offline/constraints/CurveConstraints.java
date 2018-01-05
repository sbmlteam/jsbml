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

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.ext.layout.Curve;
import org.sbml.jsbml.ext.layout.CurveSegment;
import org.sbml.jsbml.ext.layout.LayoutConstants;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.DuplicatedElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageElementValidationFunction;;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the {@link Curve} class.
 *  
 * @author rodrigue
 * @since 1.3
 */
public class CurveConstraints extends AbstractConstraintDeclaration {

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

      addRangeToSet(set, LAYOUT_21401, LAYOUT_21407 );
      
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
    ValidationFunction<Curve> func = null;

    switch (errorCode) {

    case LAYOUT_21401:
    {
      func = new UnknownCoreElementValidationFunction<Curve>();
      break;
    }
    case LAYOUT_21402:
    {
      func = new UnknownCoreAttributeValidationFunction<Curve>();
      break;
    }
    case LAYOUT_21403:
    {
      func = new ValidationFunction<Curve>() {

        @Override
        public boolean check(ValidationContext ctx, Curve curve) {
          
          return curve.isSetListOfCurveSegments()
              && new DuplicatedElementValidationFunction<Curve>(LayoutConstants.listOfCurveSegments).check(ctx, curve)
              && new UnknownPackageElementValidationFunction<Curve>(LayoutConstants.shortLabel).check(ctx, curve);
        }
      };
      break;
    }
    case LAYOUT_21404:
    {
      func = new UnknownPackageAttributeValidationFunction<Curve>(LayoutConstants.shortLabel);
      break;
    }
    case LAYOUT_21405:
    {
      func = new ValidationFunction<Curve>() {

        @Override
        public boolean check(ValidationContext ctx, Curve curve) {
          
          if (curve.isSetListOfCurveSegments()) {
            return new UnknownCoreAttributeValidationFunction<ListOf<CurveSegment>>().check(ctx, curve.getListOfCurveSegments())
                && new UnknownPackageAttributeValidationFunction<ListOf<CurveSegment>>(LayoutConstants.shortLabel).check(ctx, curve.getListOfCurveSegments());
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_21406:
    {
      func = new ValidationFunction<Curve>() {

        @Override
        public boolean check(ValidationContext ctx, Curve curve) {
          
          if (curve.isSetListOfCurveSegments()) {
            return new UnknownElementValidationFunction<ListOf<CurveSegment>>().check(ctx, curve.getListOfCurveSegments());
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_21407:
    {
      func = new ValidationFunction<Curve>() {

        @Override
        public boolean check(ValidationContext ctx, Curve curve) {
          
          if (curve.isSetListOfCurveSegments()) {
            return curve.getCurveSegmentCount() > 0;
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
