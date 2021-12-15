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

import org.sbml.jsbml.ext.render.LineEnding;
import org.sbml.jsbml.ext.render.RenderConstants;
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
 * {@link LineEnding} class.
 * 
 * @author David Emanuel Vetter
 */
public class LineEndingConstraints extends AbstractConstraintDeclaration {

  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category, ValidationContext context) {
    switch(category) {
    case GENERAL_CONSISTENCY:
      addRangeToSet(set, RENDER_21301, RENDER_21305);
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
    ValidationFunction<LineEnding> func;
    switch(errorCode) {
    case RENDER_21301:
      func = new UnknownCoreAttributeValidationFunction<LineEnding>();
      break;
    case RENDER_21302:
      func = new UnknownCoreElementValidationFunction<LineEnding>();
      break;
    case RENDER_21303:
      func = new UnknownPackageAttributeValidationFunction<LineEnding>(RenderConstants.shortLabel) {
        @Override
        public boolean check(ValidationContext ctx, LineEnding ending) {
          return super.check(ctx, ending) && ending.isSetId();
        }
      };
      break;
    case RENDER_21304:
      func = new UnknownPackageElementValidationFunction<LineEnding>(RenderConstants.shortLabel) {
        @Override
        public boolean check(ValidationContext ctx, LineEnding ending) {
          return super.check(ctx, ending)
            && new DuplicatedElementValidationFunction<LineEnding>(
              RenderConstants.boundingBox).check(ctx, ending)
            && new DuplicatedElementValidationFunction<LineEnding>(
              RenderConstants.group).check(ctx, ending);
        }
      };
      break;
    case RENDER_21305:
      func = new InvalidAttributeValidationFunction<LineEnding>(RenderConstants.enableRotationMapping);
      break;

    default:
      func = null;
      break;
    }
    return func;
  }
}
