/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * 
 * Copyright (C) 2009-2017 jointly by the following organizations:
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

import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.ext.layout.LayoutConstants;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;;

/**
 * @author Roman Schulte
 * @since 1.2
 */
public class LayoutConstraints extends AbstractConstraintDeclaration {

  /* (non-Javadoc)
   * @see org.sbml.jsbml.validator.offline.constraints.ConstraintDeclaration#addErrorCodesForAttribute(java.util.Set, int, int, java.lang.String)
   */
  @Override
  public void addErrorCodesForAttribute(Set<Integer> set, int level,
    int version, String attributeName) {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.validator.offline.constraints.ConstraintDeclaration#addErrorCodesForCheck(java.util.Set, int, int, org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY)
   */
  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category) {

    switch (category) {
    case GENERAL_CONSISTENCY:

      System.out.println("Layout rules definition");
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
  public ValidationFunction<?> getValidationFunction(int errorCode) {
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
          
          // TODO - check that there is at most one instance of the listOf
          // ListOfCompartmentGlyphs, ListOfSpeciesGlyphs, ListOfReactionGlyphs, ListOfTextGlyphs, ListOfAdditionalGraphicalObjects.
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_20304:
    {
      func = new ValidationFunction<Layout>() {

        @Override
        public boolean check(ValidationContext ctx, Layout layout) {
          
          // TODO - check that if present the listOf is not empty
          // ListOfCompartmentGlyphs, ListOfSpeciesGlyphs, ListOfReactionGlyphs, ListOfTextGlyphs, ListOfAdditionalGraphicalObjects.
          
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
          
          // TODO - check that the name was valid -> unknownAttributes ? or it will be set ?
          
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
          
          // TODO - check that ListOfCompartmentGlyphs has only sboTerm and metaid from core
          
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
          
          // TODO - check that ListOfCompartmentGlyphs contains only CompartmentGlyph objects
          // TODO - check unknownElements
          
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
          
          // TODO - check that ListOfSpeciesGlyphs has only sboTerm and metaid from core
          
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
          
          // TODO - check that ListOfSpeciesGlyphs contains only SpeciesGlyph objects
          // TODO - check unknownElements
          
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
          
          // TODO - check if there was more than one xml dimensions element
          
          // System.out.println("20315! " + l.isSetDimensions());
          return layout.isSetDimensions();
        }
      };
      break;
    }
    }

    return func;
  }
}
