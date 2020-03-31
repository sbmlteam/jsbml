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

import java.util.List;
import java.util.Set;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.ext.layout.CurveSegment;
import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.ext.layout.LayoutConstants;
import org.sbml.jsbml.ext.layout.LayoutModelPlugin;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.util.filters.Filter;
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

      set.add(CORE_10102);
      
      addRangeToSet(set, LAYOUT_20101, LAYOUT_20103);
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

    case CORE_10102:
    {
      func = new ValidationFunction<LayoutModelPlugin>() {

        @Override
        public boolean check(ValidationContext ctx, LayoutModelPlugin layoutMP) {
          
          List<? extends TreeNode> wrongCurveSegmentList = layoutMP.filter(new Filter() {
            
            @Override
            public boolean accepts(Object o) {

              if (o instanceof CurveSegment && !(((CurveSegment) o).isSetType())) { // TODO - we need to register somehow that the type attribute was not present
                return true;
              }
              
              return false;
            }
          });
          
          if (wrongCurveSegmentList != null && wrongCurveSegmentList.size() > 0) {
            
          }
          
          return true;
        }
      };
      break;
    }
    
    case LAYOUT_20101:
    {
      // must have a value for the layout:required attribute
      func = new ValidationFunction<LayoutModelPlugin>() {

        @Override
        public boolean check(ValidationContext ctx, LayoutModelPlugin lm) {

          SBMLDocument doc = lm.getSBMLDocument();
          
          // For this one, we add automatically the required attribute if not present
          // TODO - we might have to do a check before creating the LayoutModelPlugin 
          String required = doc.getSBMLDocumentAttributes().get(LayoutConstants.shortLabel + ":required");
          
          return required != null;
        }
      };
      break;
    }
    case LAYOUT_20102: 
    {
      // layout:required should be of type boolean
      func = new ValidationFunction<LayoutModelPlugin>() {

        @Override
        public boolean check(ValidationContext ctx, LayoutModelPlugin lm) {

          SBMLDocument doc = lm.getSBMLDocument();
          
          String required = doc.getSBMLDocumentAttributes().get(LayoutConstants.shortLabel + ":required");

          try {
            StringTools.parseSBMLBooleanStrict(required);
          } catch (IllegalArgumentException e) {
            return false;
          }
          return true;
        }
      };
      break;
    }    
    case LAYOUT_20103: 
    {
      // layout:required should be false
      func = new ValidationFunction<LayoutModelPlugin>() {

        @Override
        public boolean check(ValidationContext ctx, LayoutModelPlugin lm) {

          SBMLDocument doc = lm.getSBMLDocument();
          
          String requiredStr = doc.getSBMLDocumentAttributes().get(LayoutConstants.shortLabel + ":required");

          try {
            boolean required = StringTools.parseSBMLBooleanStrict(requiredStr);
            
            return required == false;
            
          } catch (IllegalArgumentException e) {
            // Do nothing for this check
          }
          
          return true;
        }
      };
      break;
    }

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
          return !(layoutMP.isSetListOfLayouts() && layoutMP.getListOfLayouts().size() == 0); 
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
