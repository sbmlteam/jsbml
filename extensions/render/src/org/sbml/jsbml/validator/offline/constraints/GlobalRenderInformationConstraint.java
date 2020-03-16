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
 * 6. Marquette University, Milwaukee, WI, USA
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
import org.sbml.jsbml.ext.render.GlobalRenderInformation;
import org.sbml.jsbml.ext.render.LocalStyle;
import org.sbml.jsbml.ext.render.RenderConstants;
import org.sbml.jsbml.ext.render.Style;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.DuplicatedElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageElementValidationFunction;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the
 * {@link GlobalRenderInformation} class.
 * 
 * @author David Emanuel Vetter
 */
public class GlobalRenderInformationConstraint
  extends AbstractConstraintDeclaration {

  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category, ValidationContext context) {
    switch(category) {
    case GENERAL_CONSISTENCY:
      addRangeToSet(set, RENDER_20701, RENDER_20706);
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
    ValidationFunction<GlobalRenderInformation> func = null;
    switch(errorCode) {
    case RENDER_20701:
      func = new UnknownCoreAttributeValidationFunction<GlobalRenderInformation>();
      break;
    case RENDER_20702:
      func = new UnknownCoreElementValidationFunction<GlobalRenderInformation>();
      break;
    case RENDER_20703:
      func = new ValidationFunction<GlobalRenderInformation>() {

        @Override
        public boolean check(ValidationContext ctx, GlobalRenderInformation t) {
          return new DuplicatedElementValidationFunction<GlobalRenderInformation>(
            RenderConstants.shortLabel + ":" + RenderConstants.listOfStyles)
                                                                            .check(
                                                                              ctx,
                                                                              t)
            && new UnknownPackageElementValidationFunction<GlobalRenderInformation>(
              RenderConstants.shortLabel).check(ctx, t);
        }
        
      };
      break;
    case RENDER_20704:
      func = new ValidationFunction<GlobalRenderInformation>() {
        @Override
        public boolean check(ValidationContext ctx, GlobalRenderInformation t) {
          if(t.isSetListOfStyles()) {
            // TODO 2020/03: Issue: GlobalRenderInformation#isSetListOfStyles counts empty as unset
            return !t.getListOfStyles().isEmpty();
          }
          return true;
        }
      };
      break;
    case RENDER_20705:
      func = new ValidationFunction<GlobalRenderInformation>() {

        @Override
        public boolean check(ValidationContext ctx, GlobalRenderInformation t) {
          if(t.isSetListOfStyles()) {
            ListOf<Style> styles = t.getListOfStyles();
            boolean coreElementsOk =
              new UnknownCoreElementValidationFunction<ListOf<Style>>().check(
                ctx, styles);
            boolean allStylesGlobal = true;
            // All the elements must be Style, but not LocalStyle  
            for(Style style : styles)
              allStylesGlobal &= !(style instanceof LocalStyle);
            
            return coreElementsOk && allStylesGlobal;
          }
          return true;
        }
        
      };
      break;
    case RENDER_20706:
      func = new ValidationFunction<GlobalRenderInformation>() {
        @Override
        public boolean check(ValidationContext ctx, GlobalRenderInformation t) {
          if (t.isSetListOfStyles()) {
            return new UnknownCoreAttributeValidationFunction<ListOf<Style>>().check(
              ctx, t.getListOfStyles());
          }
          return true;
        }
      };
      break;
    }
    return func;
  }
}
