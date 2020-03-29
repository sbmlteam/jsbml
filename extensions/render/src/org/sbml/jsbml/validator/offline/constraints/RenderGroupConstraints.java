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

import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.render.LineEnding;
import org.sbml.jsbml.ext.render.RenderConstants;
import org.sbml.jsbml.ext.render.RenderGroup;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.DuplicatedElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.InvalidAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageElementValidationFunction;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the
 * {@link RenderGroup} class.
 * 
 * @author David Emanuel Vetter
 */
public class RenderGroupConstraints extends AbstractConstraintDeclaration {

  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category, ValidationContext context) {
    switch(category) {
    case GENERAL_CONSISTENCY:
      addRangeToSet(set, RENDER_21101, RENDER_21111);
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
    ValidationFunction<RenderGroup> func;
    switch(errorCode) {
    case RENDER_21101:
      func = new UnknownCoreAttributeValidationFunction<RenderGroup>();
      break;
    case RENDER_21102:
      func = new UnknownCoreElementValidationFunction<RenderGroup>();
      break;
    case RENDER_21103:
      func = new UnknownPackageAttributeValidationFunction<RenderGroup>(RenderConstants.shortLabel);
      break;
    case RENDER_21104:
      func = new UnknownPackageElementValidationFunction<RenderGroup>(RenderConstants.shortLabel) {
        @Override
        public boolean check(ValidationContext ctx, RenderGroup group) {
          return super.check(ctx, group)
            && new DuplicatedElementValidationFunction<RenderGroup>(
              RenderConstants.shortLabel + ":" + RenderConstants.listOfElements)
                                                                              .check(
                                                                                ctx,
                                                                                group);
        }
      };
      break;
    case RENDER_21105:
      func = new ValidationFunction<RenderGroup>() {
        @Override
        public boolean check(ValidationContext ctx, RenderGroup group) {
          if(group.isSetStartHead()) {
            SBase referenced = group.getModel().getSBaseById(group.getStartHead());
            return referenced != null && referenced instanceof LineEnding;
          }
          return true;
        }
      };
      break;
    case RENDER_21106:
      func = new ValidationFunction<RenderGroup>() {
        @Override
        public boolean check(ValidationContext ctx, RenderGroup group) {
          if(group.isSetEndHead()) {
            SBase referenced = group.getModel().getSBaseById(group.getEndHead());
            return referenced != null && referenced instanceof LineEnding;
          }
          return true;
        }
      };
      break;
    case RENDER_21107:
      func = new ValidationFunction<RenderGroup>() {
        @Override
        public boolean check(ValidationContext ctx, RenderGroup t) {
          // Any string.
          return true;
        }
      };
      break;
    case RENDER_21108:
      func = new InvalidAttributeValidationFunction<RenderGroup>(
          RenderConstants.fontWeightBold);
      break;
    case RENDER_21109:
      func = new InvalidAttributeValidationFunction<RenderGroup>(
        RenderConstants.fontStyleItalic);
      break;
    case RENDER_21110:
      func = new InvalidAttributeValidationFunction<RenderGroup>(
        RenderConstants.textAnchor);
      break;
    case RENDER_21111:
      func = new InvalidAttributeValidationFunction<RenderGroup>(
        RenderConstants.vTextAnchor);
      break;

    default:
      func = null;
      break;
    }
    return func;
  }
}
