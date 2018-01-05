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
import org.sbml.jsbml.Model;
import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.ext.layout.LayoutConstants;
import org.sbml.jsbml.ext.layout.LayoutModelPlugin;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.DuplicatedElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageElementValidationFunction;;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the {@link LayoutModelPlugin} class.
 *  
 * @author rodrigue
 * @since 1.3
 */
public class LayoutModelPluginConstraints extends AbstractConstraintDeclaration {

  /* (non-Javadoc)
   * @see org.sbml.jsbml.validator.offline.constraints.ConstraintDeclaration#addErrorCodesForAttribute(java.util.Set, int, int, java.lang.String)
   */
  @Override
  public void addErrorCodesForAttribute(Set<Integer> set, int level,
    int version, String attributeName, ValidationContext context) 
  {
    // TODO 

  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.validator.offline.constraints.ConstraintDeclaration#addErrorCodesForCheck(java.util.Set, int, int, org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY)
   */
  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category, ValidationContext context) {

    switch (category) {
    case GENERAL_CONSISTENCY:

      addRangeToSet(set, LAYOUT_20201, LAYOUT_20204);
      
      break;
    case IDENTIFIER_CONSISTENCY:
      break;
    case MATHML_CONSISTENCY:
      break;
    case MODELING_PRACTICE:
      break;
    case OVERDETERMINED_MODEL:
      break;
    case SBO_CONSISTENCY:
      break;
    case UNITS_CONSISTENCY:
      break;
    }
  }


  @Override
  public ValidationFunction<?> getValidationFunction(int errorCode, ValidationContext context) {
    ValidationFunction<LayoutModelPlugin> func = null;

    switch (errorCode) {

    case LAYOUT_20201:
    {
      func = new ValidationFunction<LayoutModelPlugin>() {

        @Override
        public boolean check(ValidationContext ctx, LayoutModelPlugin layoutMP) {
          
          return new DuplicatedElementValidationFunction<Model>(LayoutConstants.shortLabel + ":" + LayoutConstants.listOfLayouts).check(ctx, layoutMP.getModel())
              && new UnknownPackageElementValidationFunction<Model>(LayoutConstants.shortLabel).check(ctx, layoutMP.getModel()); 
        }
      };
      break;
    }
    case LAYOUT_20202:
    {
      func = new ValidationFunction<LayoutModelPlugin>() {

        @Override
        public boolean check(ValidationContext ctx, LayoutModelPlugin layoutMP) {
          
          return layoutMP.isSetListOfLayouts() && layoutMP.getListOfLayouts().size() == 0; 
        }
      };
      break;
    }
    case LAYOUT_20203:
    {
      func = new ValidationFunction<LayoutModelPlugin>() {

        @Override
        public boolean check(ValidationContext ctx, LayoutModelPlugin layoutMP) {
          
          // checking that ListOfLayouts contains only notes, annotation and Layout objects if defined
          if (layoutMP.isSetListOfLayouts()) {
            return new UnknownElementValidationFunction<ListOf<Layout>>().check(ctx, layoutMP.getListOfLayouts());
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_20204:
    {
      func = new ValidationFunction<LayoutModelPlugin>() {

        @Override
        public boolean check(ValidationContext ctx, LayoutModelPlugin layoutMP) {
          
          // checking that ListOfLayouts has only sboTerm and metaid from core
          if (layoutMP.isSetListOfLayouts()) {
            return new UnknownCoreAttributeValidationFunction<ListOf<Layout>>().check(ctx, layoutMP.getListOfLayouts())
                && new UnknownPackageAttributeValidationFunction<ListOf<Layout>>(LayoutConstants.shortLabel).check(ctx, layoutMP.getListOfLayouts());
          }
          
          return true;
        }
      };
      break;
    }
    }

    return func;
  }
}
