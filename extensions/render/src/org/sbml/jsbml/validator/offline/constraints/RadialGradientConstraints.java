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

import org.sbml.jsbml.ext.render.RadialGradient;
import org.sbml.jsbml.ext.render.RenderConstants;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the
 * {@link RadialGradient} class.
 * 
 * @author David Emanuel Vetter
 */
public class RadialGradientConstraints extends AbstractConstraintDeclaration {

  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category, ValidationContext context) {
    switch(category) {
    case GENERAL_CONSISTENCY:
      addRangeToSet(set, RENDER_21801, RENDER_21810);
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
    ValidationFunction<RadialGradient> func = null;
    switch(errorCode) {
    case RENDER_21801:
      func = new UnknownCoreAttributeValidationFunction<RadialGradient>();
      break;
    case RENDER_21802:
      func = new UnknownCoreElementValidationFunction<RadialGradient>();
      break;
    case RENDER_21803:
      func = new UnknownPackageAttributeValidationFunction<RadialGradient>(RenderConstants.shortLabel);
      break;
    case RENDER_21804:
      func = new ValidationFunction<RadialGradient>() {
        @Override
        public boolean check(ValidationContext ctx, RadialGradient gradient) {
          return !gradient.isSetCx() || gradient.getCx().isSetAbsoluteValue()
            || gradient.getCx().isSetRelativeValue();
        }
      };
      break;
    case RENDER_21805:
      func = new ValidationFunction<RadialGradient>() {
        @Override
        public boolean check(ValidationContext ctx, RadialGradient gradient) {
          return !gradient.isSetCy() || gradient.getCy().isSetAbsoluteValue()
            || gradient.getCy().isSetRelativeValue();
        }
      };
      break;
    case RENDER_21806:
      func = new ValidationFunction<RadialGradient>() {
        @Override
        public boolean check(ValidationContext ctx, RadialGradient gradient) {
          return !gradient.isSetCz() || gradient.getCz().isSetAbsoluteValue()
            || gradient.getCz().isSetRelativeValue();
        }
      };
      break;
    case RENDER_21807:
      func = new ValidationFunction<RadialGradient>() {
        @Override
        public boolean check(ValidationContext ctx, RadialGradient gradient) {
          return !gradient.isSetR() || gradient.getR().isSetAbsoluteValue()
            || gradient.getR().isSetRelativeValue();
        }
      };
      break;
    case RENDER_21808:
      func = new ValidationFunction<RadialGradient>() {
        @Override
        public boolean check(ValidationContext ctx, RadialGradient gradient) {
          return !gradient.isSetFx() || gradient.getFx().isSetAbsoluteValue()
            || gradient.getFx().isSetRelativeValue();
        }
      };
      break;
    case RENDER_21809:
      func = new ValidationFunction<RadialGradient>() {
        @Override
        public boolean check(ValidationContext ctx, RadialGradient gradient) {
          return !gradient.isSetFy() || gradient.getFy().isSetAbsoluteValue()
            || gradient.getFy().isSetRelativeValue();
        }
      };
      break;
    case RENDER_21810:
      func = new ValidationFunction<RadialGradient>() {
        @Override
        public boolean check(ValidationContext ctx, RadialGradient gradient) {
          return !gradient.isSetFz() || gradient.getFz().isSetAbsoluteValue()
            || gradient.getFz().isSetRelativeValue();
        }
      };
      break;
    }
    return func;
  }
}
