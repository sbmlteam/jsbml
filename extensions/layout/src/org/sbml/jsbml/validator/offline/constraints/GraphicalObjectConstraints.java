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

import org.sbml.jsbml.ext.layout.GraphicalObject;
import org.sbml.jsbml.ext.layout.LayoutConstants;
import org.sbml.jsbml.util.TreeNodeWithChangeSupport;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.SyntaxChecker;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.DuplicatedElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageElementValidationFunction;;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the {@link GraphicalObject} class.
 *  
 * @author rodrigue
 * @since 1.3
 */
public class GraphicalObjectConstraints extends AbstractConstraintDeclaration {

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

      addRangeToSet(set, LAYOUT_20401, LAYOUT_20407);
      
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
    ValidationFunction<GraphicalObject> func = null;

    switch (errorCode) {

    case LAYOUT_20401:
    {
      func = new UnknownCoreElementValidationFunction<GraphicalObject>();
      break;
    }
    case LAYOUT_20402:
    {
      func = new UnknownCoreAttributeValidationFunction<GraphicalObject>();
      break;
    }
    case LAYOUT_20403:
    {
      func = new ValidationFunction<GraphicalObject>() {

        @Override
        public boolean check(ValidationContext ctx, GraphicalObject graphicalObject) {
          
          return new UnknownPackageElementValidationFunction<GraphicalObject>(LayoutConstants.shortLabel).check(ctx, graphicalObject)
              && new DuplicatedElementValidationFunction<TreeNodeWithChangeSupport>(LayoutConstants.boundingBox).check(ctx, graphicalObject); 
        }
      };
      break;
    }
    case LAYOUT_20404:
    {
      func = new UnknownPackageAttributeValidationFunction<GraphicalObject>(LayoutConstants.shortLabel) {

        @Override
        public boolean check(ValidationContext ctx, GraphicalObject graphicalObject) {
          
          if (!graphicalObject.isSetId()) {
            return false;
          }
          return super.check(ctx, graphicalObject); 
        }
      };
      break;
    }
    case LAYOUT_20405:
    {
      func = new ValidationFunction<GraphicalObject>() {

        @Override
        public boolean check(ValidationContext ctx, GraphicalObject graphicalObject) {
          
          if (graphicalObject.isSetMetaidRef()) {
            return SyntaxChecker.isValidMetaId(graphicalObject.getMetaidRef());
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_20406:
    {
      func = new ValidationFunction<GraphicalObject>() {

        @Override
        public boolean check(ValidationContext ctx, GraphicalObject graphicalObject) {

          if (graphicalObject.isSetMetaidRef()) {
            return graphicalObject.getSBMLDocument().getElementByMetaId(graphicalObject.getMetaidRef()) != null;
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_20407:
    {
      func = new ValidationFunction<GraphicalObject>() {

        @Override
        public boolean check(ValidationContext ctx, GraphicalObject graphicalObject) {
          
          return graphicalObject.isSetBoundingBox() && new DuplicatedElementValidationFunction<TreeNodeWithChangeSupport>(LayoutConstants.boundingBox).check(ctx, graphicalObject); 
        }
      };
      break;
    }
    }

    return func;
  }
}
