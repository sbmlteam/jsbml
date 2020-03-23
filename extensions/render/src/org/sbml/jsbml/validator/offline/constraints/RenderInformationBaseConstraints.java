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
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.ext.render.RenderConstants;
import org.sbml.jsbml.ext.render.ColorDefinition;
import org.sbml.jsbml.ext.render.GradientBase;
import org.sbml.jsbml.ext.render.LineEnding;
import org.sbml.jsbml.ext.render.RenderInformationBase;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.DuplicatedElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageElementValidationFunction;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the
 * {@link RenderInformationBase} class.
 * 
 * @author David Emanuel Vetter
 */
public class RenderInformationBaseConstraints
  extends AbstractConstraintDeclaration {

  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category, ValidationContext context) {
    switch(category) {
    case GENERAL_CONSISTENCY:
      addRangeToSet(set, RENDER_22901, RENDER_22916);
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
    ValidationFunction<RenderInformationBase> func = null;
    switch(errorCode) {
    case RENDER_22901:
      func = new UnknownCoreAttributeValidationFunction<RenderInformationBase>();
      break;
    case RENDER_22902:
      func = new UnknownCoreElementValidationFunction<RenderInformationBase>();
      break;
    case RENDER_22903:
      func =
        new UnknownPackageAttributeValidationFunction<RenderInformationBase>(
          RenderConstants.shortLabel)
        {
          public boolean check(ValidationContext ctx,
            RenderInformationBase rib) {
            return super.check(ctx, rib) && rib.isSetId();
          }
        };
      break;
    case RENDER_22904:
      func =
      new UnknownPackageElementValidationFunction<RenderInformationBase>(
        RenderConstants.shortLabel)
      {
        public boolean check(ValidationContext ctx,
          RenderInformationBase rib) {
          return super.check(ctx, rib)
              && new DuplicatedElementValidationFunction<RenderInformationBase>(
                RenderConstants.listOfColorDefinitions).check(ctx, rib)
              && new DuplicatedElementValidationFunction<RenderInformationBase>(
                  RenderConstants.listOfGradientDefinitions).check(ctx, rib)
              && new DuplicatedElementValidationFunction<RenderInformationBase>(
                  RenderConstants.listOfLineEndings).check(ctx, rib);
        }
      };
      break;
    case RENDER_22905:
    case RENDER_22906:
    case RENDER_22907:
    case RENDER_22909:
      func = new ValidationFunction<RenderInformationBase>() {
        @Override // any string
        public boolean check(ValidationContext ctx, RenderInformationBase t) { return true; }
      };
      break;
    case RENDER_22908:
      func = new ValidationFunction<RenderInformationBase>() {
        @Override
        public boolean check(ValidationContext ctx, RenderInformationBase rib) {
          if(rib.isSetReferenceRenderInformation()) {
            SBase referenced = rib.getModel().getSBaseById(rib.getReferenceRenderInformation());
            return referenced != null && referenced instanceof RenderInformationBase;
          }
          return true;
        }
      };
      break;
    case RENDER_22910:
      func = new ValidationFunction<RenderInformationBase>() {
        @Override
        public boolean check(ValidationContext ctx, RenderInformationBase t) {
          return !(t.isListOfColorDefinitionsEmpty()
            || t.isListOfGradientDefinitionsEmpty()
            || t.isListOfLineEndingsEmpty());
        }
      };
      break;
    case RENDER_22911:
      func = new ValidationFunction<RenderInformationBase>() {
        @Override
        public boolean check(ValidationContext ctx, RenderInformationBase t) {
          if(t.isSetListOfColorDefinitions()) {
            return new UnknownPackageElementValidationFunction<ListOf<ColorDefinition>>(
              RenderConstants.shortLabel).check(ctx,
                t.getListOfColorDefinitions())
              && new UnknownCoreElementValidationFunction<ListOf<ColorDefinition>>().check(
                ctx, t.getListOfColorDefinitions());
          }
          return true;
        }
      };
      break;
    case RENDER_22912:
      func = new ValidationFunction<RenderInformationBase>() {
        @Override
        public boolean check(ValidationContext ctx, RenderInformationBase t) {
          if (t.isSetListOfGradientDefinitions()) {
            return new UnknownPackageElementValidationFunction<ListOf<GradientBase>>(
              RenderConstants.shortLabel).check(ctx,
                t.getListOfGradientDefinitions())
              && new UnknownCoreElementValidationFunction<ListOf<GradientBase>>().check(
                ctx, t.getListOfGradientDefinitions());
          }
          return true;
        }
      };
      break;
    case RENDER_22913:
      func = new ValidationFunction<RenderInformationBase>() {
        @Override
        public boolean check(ValidationContext ctx, RenderInformationBase t) {
          if(t.isSetListOfLineEndings()) {
            return new UnknownPackageElementValidationFunction<ListOf<LineEnding>>(
              RenderConstants.shortLabel).check(ctx, t.getListOfLineEndings())
              && new UnknownCoreElementValidationFunction<ListOf<LineEnding>>().check(
                ctx, t.getListOfLineEndings());
          }
          return true;
        }
      };
      break;
    case RENDER_22914:
      func = new ValidationFunction<RenderInformationBase>() {
        @Override
        public boolean check(ValidationContext ctx, RenderInformationBase t) {
          if(t.isSetListOfColorDefinitions()) {
            return new UnknownCoreAttributeValidationFunction<ListOf<ColorDefinition>>().check(ctx,
                t.getListOfColorDefinitions());
          }
          return true;
        }
      };
      break;
    case RENDER_22915:
      func = new ValidationFunction<RenderInformationBase>() {
        @Override
        public boolean check(ValidationContext ctx, RenderInformationBase t) {
          if(t.isSetListOfGradientDefinitions()) {
            return new UnknownCoreAttributeValidationFunction<ListOf<GradientBase>>().check(ctx,
                t.getListOfGradientDefinitions());
          }
          return true;
        }
      };
      break;
    case RENDER_22916:
      func = new ValidationFunction<RenderInformationBase>() {
        @Override
        public boolean check(ValidationContext ctx, RenderInformationBase t) {
          if(t.isSetListOfLineEndings()) {
            return new UnknownCoreAttributeValidationFunction<ListOf<LineEnding>>().check(ctx,
                t.getListOfLineEndings());
          }
          return true;
        }
      };
      break;
    }
    return func;
  }
}
