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

import org.sbml.jsbml.ext.render.Rectangle;
import org.sbml.jsbml.ext.render.RenderConstants;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.InvalidAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the
 * {@link Rectangle} class.
 * 
 * @author David Emanuel Vetter
 */
public class RectangleConstraints extends AbstractConstraintDeclaration {

  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category, ValidationContext context) {
    switch(category) {
    case GENERAL_CONSISTENCY:
      addRangeToSet(set, RENDER_21901, RENDER_21911);
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
    ValidationFunction<Rectangle> func;
    switch(errorCode) {
    case RENDER_21901:
      func = new UnknownCoreAttributeValidationFunction<Rectangle>();
      break;
    case RENDER_21902:
      func = new UnknownCoreElementValidationFunction<Rectangle>();
      break;
    case RENDER_21903:
      func = new UnknownPackageAttributeValidationFunction<Rectangle>(RenderConstants.shortLabel) {
        @Override
        public boolean check(ValidationContext ctx, Rectangle r) {
          return super.check(ctx, r) && r.isSetX() && r.isSetY()
            && r.isSetWidth() && r.isSetHeight();
        }
      };
      break;
    case RENDER_21904:
      func = new ValidationFunction<Rectangle>() {
        @Override
        public boolean check(ValidationContext ctx, Rectangle r) {
          return r.isSetX()
            && (r.getX().isSetAbsoluteValue() || r.getX().isSetRelativeValue());
        }
      };
      break;
    case RENDER_21905:
      func = new ValidationFunction<Rectangle>() {
        @Override
        public boolean check(ValidationContext ctx, Rectangle r) {
          return r.isSetY()
            && (r.getY().isSetAbsoluteValue() || r.getY().isSetRelativeValue());
        }
      };
      break;
    case RENDER_21906:
      func = new ValidationFunction<Rectangle>() {
        @Override
        public boolean check(ValidationContext ctx, Rectangle r) {
          return r.isSetWidth()
            && (r.getWidth().isSetAbsoluteValue() || r.getWidth().isSetRelativeValue());
        }
      };
      break;
    case RENDER_21907:
      func = new ValidationFunction<Rectangle>() {
        @Override
        public boolean check(ValidationContext ctx, Rectangle r) {
          return r.isSetHeight()
            && (r.getHeight().isSetAbsoluteValue() || r.getHeight().isSetRelativeValue());
        }
      };
      break;
    case RENDER_21908:
      func = new InvalidAttributeValidationFunction<Rectangle>(RenderConstants.ratio);
      break;
    case RENDER_21909:
      func = new ValidationFunction<Rectangle>() {
        @Override
        public boolean check(ValidationContext ctx, Rectangle r) {
          return !r.isSetZ() || r.getZ().isSetAbsoluteValue()
            || r.getZ().isSetRelativeValue();
        }
      };
      break;
    case RENDER_21910:
      func = new ValidationFunction<Rectangle>() {
        @Override
        public boolean check(ValidationContext ctx, Rectangle r) {
          return !r.isSetRx() || r.getRx().isSetAbsoluteValue()
            || r.getRx().isSetRelativeValue();
        }
      };
      break;
    case RENDER_21911:
      func = new ValidationFunction<Rectangle>() {
        @Override
        public boolean check(ValidationContext ctx, Rectangle r) {
          return !r.isSetRy() || r.getRy().isSetAbsoluteValue()
            || r.getRy().isSetRelativeValue();
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
