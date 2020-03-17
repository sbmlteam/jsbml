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

import org.sbml.jsbml.ext.render.GradientStop;
import org.sbml.jsbml.ext.render.RenderConstants;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the
 * {@link GradientStop} class.
 * 
 * @author David Emanuel Vetter
 */
public class GradientStopConstraints extends AbstractConstraintDeclaration {

  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category, ValidationContext context) {
    switch(category) {
    case GENERAL_CONSISTENCY:
      addRangeToSet(set, RENDER_21001, RENDER_21005);
      // TODO 2020/03: there are two more constraints, but they are soft
      break;
    default:
      break;
    }
  }


  @Override
  public void addErrorCodesForAttribute(Set<Integer> set, int level,
    int version, String attributeName, ValidationContext context) {
    // TODO Auto-generated method stub
  }


  @Override
  public ValidationFunction<?> getValidationFunction(int errorCode,
    ValidationContext context) {
    ValidationFunction<GradientStop> func = null;
    switch(errorCode) {
    case RENDER_21001:
      func = new UnknownCoreAttributeValidationFunction<GradientStop>();
      break;
    case RENDER_21002:
      func = new UnknownCoreElementValidationFunction<GradientStop>();
      break;
    case RENDER_21003:
      func = new UnknownPackageAttributeValidationFunction<GradientStop>(RenderConstants.shortLabel) {
        @Override
        public boolean check(ValidationContext ctx, GradientStop stop) {
          return super.check(ctx, stop) && stop.isSetStopColor() && stop.isSetOffset();
        }
      };
      break;
    case RENDER_21004:
      func = new ValidationFunction<GradientStop>() {
        @Override
        public boolean check(ValidationContext ctx, GradientStop t) {
          // Any string ok.
          return true;
        }
      };
      break;
    case RENDER_21005:
      func = new ValidationFunction<GradientStop>() {
        @Override
        public boolean check(ValidationContext ctx, GradientStop stop) {
          return stop.isSetOffset() && (stop.getOffset().isSetAbsoluteValue()
            || stop.getOffset().isSetRelativeValue());
        }
      };
      break;
    }
    return func;
  }
}
