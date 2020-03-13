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

import org.sbml.jsbml.ext.render.ColorDefinition;
import org.sbml.jsbml.ext.render.RenderConstants;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the {@link ColorDefinition} class.
 * 
 * @author David Emanuel Vetter
 */
public class ColorDefinitionConstraints extends AbstractConstraintDeclaration {
  
  
  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category, ValidationContext context) {
    switch(category) {
    case GENERAL_CONSISTENCY:
      addRangeToSet(set, RENDER_20501, RENDER_20505);
      break;
    default:
      break;
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.validator.offline.constraints.ConstraintDeclaration#addErrorCodesForAttribute(java.util.Set, int, int, java.lang.String)
   */
  @Override
  public void addErrorCodesForAttribute(Set<Integer> set, int level,
    int version, String attributeName, ValidationContext context) {
    // TODO Auto-generated method stub
  }


  @Override
  public ValidationFunction<?> getValidationFunction(int errorCode,
    ValidationContext context) {
    ValidationFunction<ColorDefinition> func = null;
    switch(errorCode) {
    case RENDER_20501:
      func = new UnknownCoreAttributeValidationFunction<ColorDefinition>();
      break;
    case RENDER_20502:
      func = new UnknownCoreElementValidationFunction<ColorDefinition>();
      break;
    case RENDER_20503:
      func = new ValidationFunction<ColorDefinition>() {
        @Override
        public boolean check(ValidationContext ctx, ColorDefinition t) {
          return t.isSetId() && t.isSetValue()
            && new UnknownPackageAttributeValidationFunction<ColorDefinition>(
              RenderConstants.shortLabel).check(ctx, t);
        }
      };
      break;
    case RENDER_20504:
      func = new ValidationFunction<ColorDefinition>() {

        @Override
        public boolean check(ValidationContext ctx, ColorDefinition t) {
          // Check that render:value is of type String
          // TODO 2020/03: on p. 79, only String is required, but value is
          // actually supposed to be a colorString
          return true;
        }
      };
      break;
    case RENDER_20505:
      func = new ValidationFunction<ColorDefinition>() {
        @Override
        public boolean check(ValidationContext ctx, ColorDefinition t) {
          // cf. ReferenceGlyphConstraints->LAYOUT_21112
          return true;
        }
      };
      break;
    }
    return func;
  }
}
