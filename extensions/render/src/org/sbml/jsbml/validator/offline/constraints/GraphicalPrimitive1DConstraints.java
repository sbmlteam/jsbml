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

import org.sbml.jsbml.ext.render.GraphicalPrimitive1D;
import org.sbml.jsbml.ext.render.RenderConstants;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.InvalidAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the
 * {@link GraphicalPrimitive1D} class.
 * 
 * @author David Emanuel Vetter
 */
public class GraphicalPrimitive1DConstraints
  extends AbstractConstraintDeclaration {

  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category, ValidationContext context) {
    switch(category) {
    case GENERAL_CONSISTENCY:
      addRangeToSet(set, RENDER_22601, RENDER_22606);
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
    ValidationFunction<GraphicalPrimitive1D> func;
    switch(errorCode) {
    case RENDER_22601:
      func = new UnknownCoreAttributeValidationFunction<GraphicalPrimitive1D>();
      break;
    case RENDER_22602:
      func = new UnknownCoreElementValidationFunction<GraphicalPrimitive1D>();
      break;
    case RENDER_22603:
      func =
        new UnknownPackageAttributeValidationFunction<GraphicalPrimitive1D>(
          RenderConstants.shortLabel);
      break;
    case RENDER_22604:
      func = new ValidationFunction<GraphicalPrimitive1D>() {
        @Override
        public boolean check(ValidationContext ctx, GraphicalPrimitive1D t) { return true; }
      };
      break;
    case RENDER_22605:
      func = new InvalidAttributeValidationFunction<GraphicalPrimitive1D>(
        RenderConstants.strokeWidth);
      break;
    case RENDER_22606:
      func = new InvalidAttributeValidationFunction<GraphicalPrimitive1D>(
        RenderConstants.strokeDashArray);
      break;

    default:
      func = null;
      break;
    }
    return func;
  }
}
