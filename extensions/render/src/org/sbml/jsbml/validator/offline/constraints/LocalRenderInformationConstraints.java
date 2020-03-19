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
import org.sbml.jsbml.ext.render.LocalRenderInformation;
import org.sbml.jsbml.ext.render.LocalStyle;
import org.sbml.jsbml.ext.render.RenderConstants;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.DuplicatedElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageElementValidationFunction;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the
 * {@link LocalRenderInformation} class.
 * 
 * @author David Emanuel Vetter
 */
public class LocalRenderInformationConstraints
  extends AbstractConstraintDeclaration {

  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category, ValidationContext context) {
    switch(category) {
    case GENERAL_CONSISTENCY:
      addRangeToSet(set, RENDER_21501, RENDER_21506);
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
    ValidationFunction<LocalRenderInformation> func = null;
    switch(errorCode) {
    case RENDER_21501:
      func = new UnknownCoreAttributeValidationFunction<LocalRenderInformation>();
      break;
    case RENDER_21502:
      func = new UnknownCoreElementValidationFunction<LocalRenderInformation>();
      break;
    case RENDER_21503:
      func = new UnknownPackageElementValidationFunction<LocalRenderInformation>(RenderConstants.shortLabel) {
        @Override
        public boolean check(ValidationContext ctx, LocalRenderInformation lri) {
          return super.check(ctx, lri) 
              && new DuplicatedElementValidationFunction<LocalRenderInformation>(
                RenderConstants.listOfLocalStyles).check(ctx, lri);
        }
      };
      break;
    case RENDER_21504:
      func = new ValidationFunction<LocalRenderInformation>() {
        @Override
        public boolean check(ValidationContext ctx, LocalRenderInformation lri) {
          // TODO 2020/03: problem: isSet also considers empty to be unset. Go via userObjects?
          return !lri.isSetListOfLocalStyles() || !lri.getListOfLocalStyles().isEmpty();
        }
      };
      break;
    case RENDER_21505:
      func = new ValidationFunction<LocalRenderInformation>() {

        @Override
        public boolean check(ValidationContext ctx, LocalRenderInformation lri) {
          if (lri.isSetListOfLocalStyles()) {
            return new UnknownCoreElementValidationFunction<ListOf<LocalStyle>>().check(
              ctx, lri.getListOfLocalStyles())
              && new UnknownPackageElementValidationFunction<ListOf<LocalStyle>>(
                RenderConstants.shortLabel).check(ctx,
                  lri.getListOfLocalStyles());
          }
          return true;
        }
      };
      break;
    case RENDER_21506:
      func = new ValidationFunction<LocalRenderInformation>() {
        @Override
        public boolean check(ValidationContext ctx, LocalRenderInformation lri) {
          if (lri.isSetListOfLocalStyles()) {
            return new UnknownCoreAttributeValidationFunction<ListOf<LocalStyle>>().check(
              ctx, lri.getListOfLocalStyles());
          }
          return true;
        }
      };
      break;
    }
    return func;
  }
}
