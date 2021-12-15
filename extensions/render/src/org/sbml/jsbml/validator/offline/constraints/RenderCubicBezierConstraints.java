/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2022 jointly by the following organizations:
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

import org.sbml.jsbml.ext.render.RenderConstants;
import org.sbml.jsbml.ext.render.RenderCubicBezier;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;


/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the
 * {@link RenderCubicBezier} class.
 * 
 * @author David Emanuel Vetter
 */
public class RenderCubicBezierConstraints
  extends AbstractConstraintDeclaration {

  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category, ValidationContext context) {
    switch(category) {
    case GENERAL_CONSISTENCY:
      addRangeToSet(set, RENDER_22001, RENDER_22009);
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
    ValidationFunction<RenderCubicBezier> func;
    switch(errorCode) {
    case RENDER_22001:
      func = new UnknownCoreAttributeValidationFunction<RenderCubicBezier>();
      break;
    case RENDER_22002:
      func = new UnknownCoreElementValidationFunction<RenderCubicBezier>();
      break;
    case RENDER_22003:
      func = new UnknownPackageAttributeValidationFunction<RenderCubicBezier>(RenderConstants.shortLabel) {
        @Override
        public boolean check(ValidationContext ctx, RenderCubicBezier rcb) {
          return super.check(ctx, rcb) && rcb.isSetX1() && rcb.isSetY1()
            && rcb.isSetX2() && rcb.isSetY2();
        }
      };
      break;
    case RENDER_22004:
      func = new ValidationFunction<RenderCubicBezier>() {
        @Override
        public boolean check(ValidationContext ctx, RenderCubicBezier r) {
          return r.isSetX1() && (r.getX1().isSetAbsoluteValue()
            || r.getX1().isSetRelativeValue());
        }
      };
      break;
    case RENDER_22005:
      func = new ValidationFunction<RenderCubicBezier>() {
        @Override
        public boolean check(ValidationContext ctx, RenderCubicBezier r) {
          return r.isSetY1() && (r.getY1().isSetAbsoluteValue()
            || r.getY1().isSetRelativeValue());
        }
      };
      break;
    case RENDER_22006:
      func = new ValidationFunction<RenderCubicBezier>() {
        @Override
        public boolean check(ValidationContext ctx, RenderCubicBezier r) {
          return r.isSetX2() && (r.getX2().isSetAbsoluteValue()
            || r.getX2().isSetRelativeValue());
        }
      };
      break;
    case RENDER_22007:
      func = new ValidationFunction<RenderCubicBezier>() {
        @Override
        public boolean check(ValidationContext ctx, RenderCubicBezier r) {
          return r.isSetY2() && (r.getY2().isSetAbsoluteValue()
            || r.getY2().isSetRelativeValue());
        }
      };
      break;
    case RENDER_22008:
      func = new ValidationFunction<RenderCubicBezier>() {
        @Override
        public boolean check(ValidationContext ctx, RenderCubicBezier r) {
          return r.isSetZ1() && (r.getZ1().isSetAbsoluteValue()
            || r.getZ1().isSetRelativeValue());
        }
      };
      break;
    case RENDER_22009:
      func = new ValidationFunction<RenderCubicBezier>() {
        @Override
        public boolean check(ValidationContext ctx, RenderCubicBezier r) {
          return r.isSetZ2() && (r.getZ2().isSetAbsoluteValue()
            || r.getZ2().isSetRelativeValue());
        }
      };
      break;

    default:
      func = null;
      break;
    }
    return func;
  }
}
