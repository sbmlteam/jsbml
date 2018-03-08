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
import org.sbml.jsbml.ext.layout.CompartmentGlyph;
import org.sbml.jsbml.ext.layout.GraphicalObject;
import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.ext.layout.LayoutConstants;
import org.sbml.jsbml.ext.layout.ReactionGlyph;
import org.sbml.jsbml.ext.layout.SpeciesGlyph;
import org.sbml.jsbml.ext.layout.TextGlyph;
import org.sbml.jsbml.util.TreeNodeWithChangeSupport;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.DuplicatedElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the {@link Layout} class.
 * 
 * @author Roman Schulte
 * @author rodrigue
 * @since 1.2
 */
public class LayoutConstraints extends AbstractConstraintDeclaration {

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

      addRangeToSet(set, LAYOUT_20301, LAYOUT_20317);
      
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
    ValidationFunction<Layout> func = null;

    switch (errorCode) {

    case LAYOUT_20301:
    {
      func = new UnknownCoreElementValidationFunction<Layout>();
      break;
    }
    case LAYOUT_20302:
    {
      func = new UnknownCoreAttributeValidationFunction<Layout>();
      break;
    }
    case LAYOUT_20303:
    {
      func = new ValidationFunction<Layout>() {

        @Override
        public boolean check(ValidationContext ctx, Layout layout) {
          
          return new DuplicatedElementValidationFunction<Layout>(LayoutConstants.listOfCompartmentGlyphs).check(ctx, layout) 
              && new DuplicatedElementValidationFunction<Layout>(LayoutConstants.listOfSpeciesGlyphs).check(ctx, layout) 
              && new DuplicatedElementValidationFunction<Layout>(LayoutConstants.listOfReactionGlyphs).check(ctx, layout) 
              && new DuplicatedElementValidationFunction<Layout>(LayoutConstants.listOfTextGlyphs).check(ctx, layout) 
              && new DuplicatedElementValidationFunction<Layout>(LayoutConstants.listOfAdditionalGraphicalObjects).check(ctx, layout); 
        }
      };
      break;
    }
    case LAYOUT_20304:
    {
      func = new ValidationFunction<Layout>() {

        @Override
        public boolean check(ValidationContext ctx, Layout layout) {
          
          // checking that if present the listOfs are not empty
          
          if (layout.isSetListOfCompartmentGlyphs() && layout.getListOfCompartmentGlyphs().size() == 0) {
            return false;
          }
          if (layout.isSetListOfSpeciesGlyphs() && layout.getListOfSpeciesGlyphs().size() == 0) {
            return false;
          }
          if (layout.isSetListOfReactionGlyphs() && layout.getListOfReactionGlyphs().size() == 0) {
            return false;
          }
          if (layout.isSetListOfTextGlyphs() && layout.getListOfTextGlyphs().size() == 0) {
            return false;
          }
          if (layout.isSetListOfAdditionalGraphicalObjects() && layout.getListOfAdditionalGraphicalObjects().size() == 0) {
            return false;
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_20305:
    {
      func = new UnknownPackageAttributeValidationFunction<Layout>(LayoutConstants.shortLabel) {

        @Override
        public boolean check(ValidationContext ctx, Layout layout) {
        
          if (!layout.isSetId()) {
            return false;
          }
          
          return super.check(ctx, layout);
        }

      };
      break;
    }
    case LAYOUT_20306:
    {
      func = new ValidationFunction<Layout>() {

        @Override
        public boolean check(ValidationContext ctx, Layout layout) {
          
          // nothing to check as java read any kind of String
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_20307:
    {
      func = new ValidationFunction<Layout>() {

        @Override
        public boolean check(ValidationContext ctx, Layout layout) {
          
          // checking that ListOfCompartmentGlyphs has only sboTerm and metaid from core
          if (layout.isSetListOfCompartmentGlyphs()) {
            return new UnknownCoreAttributeValidationFunction<ListOf<CompartmentGlyph>>().check(ctx, layout.getListOfCompartmentGlyphs())
                && new UnknownPackageAttributeValidationFunction<ListOf<CompartmentGlyph>>(LayoutConstants.shortLabel).check(ctx, layout.getListOfCompartmentGlyphs());
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_20308:
    {
      func = new ValidationFunction<Layout>() {

        @Override
        public boolean check(ValidationContext ctx, Layout layout) {
          
          // checking that ListOfCompartmentGlyphs contains only notes, annotation and CompartmentGlyph objects
          if (layout.isSetListOfCompartmentGlyphs()) {
            return new UnknownElementValidationFunction<ListOf<CompartmentGlyph>>().check(ctx, layout.getListOfCompartmentGlyphs());
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_20309:
    {
      func = new ValidationFunction<Layout>() {

        @Override
        public boolean check(ValidationContext ctx, Layout layout) {
          
          // checking that ListOfSpeciesGlyphs has only sboTerm and metaid from core
          if (layout.isSetListOfSpeciesGlyphs()) {
            return new UnknownCoreAttributeValidationFunction<ListOf<SpeciesGlyph>>().check(ctx, layout.getListOfSpeciesGlyphs())
                && new UnknownPackageAttributeValidationFunction<ListOf<SpeciesGlyph>>(LayoutConstants.shortLabel).check(ctx, layout.getListOfSpeciesGlyphs());
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_20310:
    {
      func = new ValidationFunction<Layout>() {

        @Override
        public boolean check(ValidationContext ctx, Layout layout) {
          
          // checking that ListOfSpeciesGlyphs contains only notes, annotation and SpeciesGlyph objects
          if (layout.isSetListOfSpeciesGlyphs()) {
            return new UnknownElementValidationFunction<ListOf<SpeciesGlyph>>().check(ctx, layout.getListOfSpeciesGlyphs());
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_20311:
    {
      func = new ValidationFunction<Layout>() {

        @Override
        public boolean check(ValidationContext ctx, Layout layout) {
          
          // checking that ListOfReactionGlyphs has only sboTerm and metaid from core
          if (layout.isSetListOfReactionGlyphs()) {
            return new UnknownCoreAttributeValidationFunction<ListOf<ReactionGlyph>>().check(ctx, layout.getListOfReactionGlyphs())
                && new UnknownPackageAttributeValidationFunction<ListOf<ReactionGlyph>>(LayoutConstants.shortLabel).check(ctx, layout.getListOfReactionGlyphs());
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_20312:
    {
      func = new ValidationFunction<Layout>() {

        @Override
        public boolean check(ValidationContext ctx, Layout layout) {
          
          // checking that ListOfReactionGlyphs contains only notes, annotation and ReactionGlyph objects
          if (layout.isSetListOfReactionGlyphs()) {
            return new UnknownElementValidationFunction<ListOf<ReactionGlyph>>().check(ctx, layout.getListOfReactionGlyphs());
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_20313:
    {
      func = new ValidationFunction<Layout>() {

        @Override
        public boolean check(ValidationContext ctx, Layout layout) {
          
          // checking that ListOfAdditionalGraphicalObjectGlyphs has only sboTerm and metaid from core
          if (layout.isSetListOfAdditionalGraphicalObjects()) {
            return new UnknownCoreAttributeValidationFunction<ListOf<GraphicalObject>>().check(ctx, layout.getListOfAdditionalGraphicalObjects())
                && new UnknownPackageAttributeValidationFunction<ListOf<GraphicalObject>>(LayoutConstants.shortLabel).check(ctx, layout.getListOfAdditionalGraphicalObjects());
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_20314:
    {
      func = new ValidationFunction<Layout>() {

        @Override
        public boolean check(ValidationContext ctx, Layout layout) {
          
          // checking that ListOfAdditionalGraphicalObjectGlyphs contains only notes, annotation and AdditionalGraphicalObjectGlyph objects
          if (layout.isSetListOfAdditionalGraphicalObjects()) {
            return new UnknownElementValidationFunction<ListOf<GraphicalObject>>().check(ctx, layout.getListOfAdditionalGraphicalObjects());
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_20315:
    {
      func = new ValidationFunction<Layout>() {

        @Override
        public boolean check(ValidationContext ctx, Layout layout) {
          
          // checking if there is one and no more than one xml dimensions element
          
          // System.out.println("20315! " + l.isSetDimensions());
          if  (!layout.isSetDimensions()) {
            return false;
          }
          
          return new DuplicatedElementValidationFunction<TreeNodeWithChangeSupport>(LayoutConstants.dimensions).check(ctx, layout);
        }
      };
      break;
    }
    case LAYOUT_20316:
    {
      func = new ValidationFunction<Layout>() {

        @Override
        public boolean check(ValidationContext ctx, Layout layout) {
          
          // checking that ListOfTextGlyphs has only sboTerm and metaid from core
          if (layout.isSetListOfTextGlyphs()) {
            return new UnknownCoreAttributeValidationFunction<ListOf<TextGlyph>>().check(ctx, layout.getListOfTextGlyphs())
                && new UnknownPackageAttributeValidationFunction<ListOf<TextGlyph>>(LayoutConstants.shortLabel).check(ctx, layout.getListOfTextGlyphs());
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_20317:
    {
      func = new ValidationFunction<Layout>() {

        @Override
        public boolean check(ValidationContext ctx, Layout layout) {
          
          // checking that ListOfTextGlyphs contains only notes, annotation and TextGlyph objects
          if (layout.isSetListOfTextGlyphs()) {
            return new UnknownElementValidationFunction<ListOf<TextGlyph>>().check(ctx, layout.getListOfTextGlyphs());
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
