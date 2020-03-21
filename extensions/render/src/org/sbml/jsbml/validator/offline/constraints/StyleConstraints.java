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
import org.sbml.jsbml.ext.render.Style;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.DuplicatedElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageElementValidationFunction;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the
 * {@link Style} class.<br> 
 * <b>Note:</b> Since global styles are not explicitly implemented
 * in JSBML, this class combines the constraints placed on both Style and
 * GlobalStyle (which is not a problem, since GlobalStyle does not add any
 * meaningfully new constraints)
 * 
 * @author David Emanuel Vetter
 */
public class StyleConstraints extends AbstractConstraintDeclaration {

  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category, ValidationContext context) {
    switch(category) {
    case GENERAL_CONSISTENCY:
      addRangeToSet(set, RENDER_20801, RENDER_20802); // Constraints on GlobalStyle
      addRangeToSet(set, RENDER_22801, RENDER_22807); // Constraints on any Style
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
    ValidationFunction<Style> func = null;
    switch(errorCode) {
    case RENDER_20801:
    case RENDER_22801:
      func = new UnknownCoreAttributeValidationFunction<Style>();
      break;
    case RENDER_20802:
    case RENDER_22802:
      func = new UnknownCoreElementValidationFunction<Style>();
      break;
      
    case RENDER_22803:
      func = new UnknownPackageAttributeValidationFunction<Style>(RenderConstants.shortLabel);
      break;
    case RENDER_22804:
      func = new UnknownPackageElementValidationFunction<Style>(RenderConstants.shortLabel) {
        public boolean check(ValidationContext ctx, Style style) {
          return super.check(ctx, style)
            && new DuplicatedElementValidationFunction<Style>(
              RenderConstants.group).check(ctx, style);
        }
      };
      break;
    case RENDER_22805:
    case RENDER_22806:
    case RENDER_22807:
      func = new ValidationFunction<Style>() {
        @Override // any string
        public boolean check(ValidationContext ctx, Style t) { return true; }
      };    
      break;
    }
    return func;
  }
}
