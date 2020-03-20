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

import org.sbml.jsbml.ext.render.RenderConstants;
import org.sbml.jsbml.ext.render.Text;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.InvalidAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the
 * {@link Text} class.
 * 
 * @author David Emanuel Vetter
 */
public class TextConstraints extends AbstractConstraintDeclaration {

  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category, ValidationContext context) {
    switch(category) {
    case GENERAL_CONSISTENCY:
      addRangeToSet(set, RENDER_22301, RENDER_22312);
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
    ValidationFunction<Text> func = null;
    switch(errorCode) {
    case RENDER_22301:
      func = new UnknownCoreAttributeValidationFunction<Text>();
      break;
    case RENDER_22302:
      func = new UnknownCoreElementValidationFunction<Text>();
      break;
    case RENDER_22303:
      func = new UnknownPackageAttributeValidationFunction<Text>(RenderConstants.shortLabel) {
        public boolean check(ValidationContext ctx, Text t) {
          return super.check(ctx, t) && t.isSetX() && t.isSetY();
        }
      };
      break;
    case RENDER_22304:
      func = new ValidationFunction<Text>() {
        @Override
        public boolean check(ValidationContext ctx, Text t) {
          return t.isSetX()
            && (t.getX().isSetAbsoluteValue() || t.getX().isSetRelativeValue());
        }
      };
      break;
    case RENDER_22305:
      func = new ValidationFunction<Text>() {
        @Override
        public boolean check(ValidationContext ctx, Text t) {
          return t.isSetY()
            && (t.getY().isSetAbsoluteValue() || t.getY().isSetRelativeValue());
        }
      };
      break;
    case RENDER_22306:
      func = new ValidationFunction<Text>() {
        @Override
        public boolean check(ValidationContext ctx, Text t) {
          // Any string
          return true;
        }
      };
      break;
    case RENDER_22307:
      func = new InvalidAttributeValidationFunction<Text>(
          RenderConstants.fontWeightBold);
      break;
    case RENDER_22308:
      func = new InvalidAttributeValidationFunction<Text>(
          RenderConstants.fontStyleItalic);
      break;
    case RENDER_22309:
      func = new InvalidAttributeValidationFunction<Text>(
          RenderConstants.textAnchor);
      break;
    case RENDER_22310:
      func = new InvalidAttributeValidationFunction<Text>(
          RenderConstants.vTextAnchor);
      break;
    case RENDER_22311:
      func = new ValidationFunction<Text>() {
        @Override
        public boolean check(ValidationContext ctx, Text t) {
          return !t.isSetZ() || t.getZ().isSetAbsoluteValue()
            || t.getZ().isSetRelativeValue();
        }
      };
      break;
    case RENDER_22312:
      func = new ValidationFunction<Text>() {
        @Override
        public boolean check(ValidationContext ctx, Text t) {
          return !t.isSetFontSize() || t.getFontSize().isSetAbsoluteValue()
            || t.getZ().isSetRelativeValue();
        }
      };
      break;
    }
    return func;
  }
}
