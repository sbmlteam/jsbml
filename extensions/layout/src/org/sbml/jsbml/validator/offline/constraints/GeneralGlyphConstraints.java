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
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.layout.GeneralGlyph;
import org.sbml.jsbml.ext.layout.GraphicalObject;
import org.sbml.jsbml.ext.layout.LayoutConstants;
import org.sbml.jsbml.ext.layout.ReferenceGlyph;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.SyntaxChecker;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.DuplicatedElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageElementValidationFunction;;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the {@link GeneralGlyph} class.
 *  
 * @author rodrigue
 * @since 1.3
 */
public class GeneralGlyphConstraints extends AbstractConstraintDeclaration {

  /* (non-Javadoc)
   * @see org.sbml.jsbml.validator.offline.constraints.ConstraintDeclaration#addErrorCodesForAttribute(java.util.Set, int, int, java.lang.String)
   */
  @Override
  public void addErrorCodesForAttribute(Set<Integer> set, int level,
    int version, String attributeName, ValidationContext context) 
  {
    // TODO - implement

  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.validator.offline.constraints.ConstraintDeclaration#addErrorCodesForCheck(java.util.Set, int, int, org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY)
   */
  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category, ValidationContext context) {

    switch (category) {
    case GENERAL_CONSISTENCY:

      addRangeToSet(set, LAYOUT_20801, LAYOUT_20813 );
      
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
    ValidationFunction<GeneralGlyph> func = null;

    switch (errorCode) {

    case LAYOUT_20801:
    {
      func = new UnknownCoreElementValidationFunction<GeneralGlyph>();
      break;
    }
    case LAYOUT_20802:
    {
      func = new UnknownCoreAttributeValidationFunction<GeneralGlyph>();
      break;
    }
    case LAYOUT_20803:
    {
      func = new ValidationFunction<GeneralGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, GeneralGlyph generalGlyph) {
          
          return new DuplicatedElementValidationFunction<GeneralGlyph>(LayoutConstants.listOfReferenceGlyphs).check(ctx, generalGlyph)
              && new DuplicatedElementValidationFunction<GeneralGlyph>(LayoutConstants.boundingBox).check(ctx, generalGlyph)
              && new DuplicatedElementValidationFunction<GeneralGlyph>(LayoutConstants.curve).check(ctx, generalGlyph)
              && new DuplicatedElementValidationFunction<GeneralGlyph>(LayoutConstants.listOfSubGlyphs).check(ctx, generalGlyph)
              && new UnknownPackageElementValidationFunction<GeneralGlyph>(LayoutConstants.shortLabel).check(ctx, generalGlyph);
        }
      };
      break;
    }
    case LAYOUT_20804:
    {
      func = new UnknownPackageAttributeValidationFunction<GeneralGlyph>(LayoutConstants.shortLabel) {

        @Override
        public boolean check(ValidationContext ctx, GeneralGlyph generalGlyph) {
        
          if (!generalGlyph.isSetId()) {
            return false;
          }
          
          return super.check(ctx, generalGlyph);
        }

      };
      break;
    }
    case LAYOUT_20805:
    {
      func = new ValidationFunction<GeneralGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, GeneralGlyph generalGlyph) {
          
          if (generalGlyph.isSetMetaidRef()) {
            return SyntaxChecker.isValidMetaId(generalGlyph.getMetaidRef());
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_20806:
    {
      func = new ValidationFunction<GeneralGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, GeneralGlyph generalGlyph) {
          
          if (generalGlyph.isSetMetaidRef()) {
            return generalGlyph.getSBMLDocument().getElementByMetaId(generalGlyph.getMetaidRef()) != null;
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_20807:
    {
      func = new ValidationFunction<GeneralGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, GeneralGlyph generalGlyph) {
          
          if (generalGlyph.isSetReference()) {
            return SyntaxChecker.isValidId(generalGlyph.getReference(), generalGlyph.getLevel(), generalGlyph.getVersion());
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_20808:
    {
      func = new ValidationFunction<GeneralGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, GeneralGlyph generalGlyph) {
          
          if (generalGlyph.isSetReference()) {
            return generalGlyph.getModel().getElementBySId(generalGlyph.getReference()) != null;
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_20809:
    {
      func = new ValidationFunction<GeneralGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, GeneralGlyph generalGlyph) {
          
          if (generalGlyph.isSetReference() && generalGlyph.isSetMetaidRef()) {
            SBase sbase = generalGlyph.getModel().getElementBySId(generalGlyph.getReference());
            SBase sbaseFromMetaidRef = generalGlyph.getSBMLDocument().getElementByMetaId(generalGlyph.getMetaidRef());

            if ((sbase == null || sbaseFromMetaidRef == null) && sbase != sbaseFromMetaidRef) {
              return false;
            } else if (! (sbase != null && sbaseFromMetaidRef != null && sbase.equals(sbaseFromMetaidRef))) {
              return false;
            }
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_20810:
    {
      func = new ValidationFunction<GeneralGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, GeneralGlyph generalGlyph) {
          
          if (generalGlyph.isSetListOfReferenceGlyphs()) {
            return new UnknownElementValidationFunction<ListOf<ReferenceGlyph>>().check(ctx, generalGlyph.getListOfReferenceGlyphs());
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_20811:
    {
      func = new ValidationFunction<GeneralGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, GeneralGlyph generalGlyph) {
          
          if (generalGlyph.isSetListOfReferenceGlyphs()) {
            return new UnknownCoreAttributeValidationFunction<ListOf<ReferenceGlyph>>().check(ctx, generalGlyph.getListOfReferenceGlyphs())
                && new UnknownPackageAttributeValidationFunction<ListOf<ReferenceGlyph>>(LayoutConstants.shortLabel).check(ctx, generalGlyph.getListOfReferenceGlyphs());
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_20812:
    {
      func = new ValidationFunction<GeneralGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, GeneralGlyph generalGlyph) {
          
          if (generalGlyph.isSetListOfSubGlyphs()) {
            return new UnknownElementValidationFunction<ListOf<GraphicalObject>>().check(ctx, generalGlyph.getListOfSubGlyphs());
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_20813:
    {
      func = new ValidationFunction<GeneralGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, GeneralGlyph generalGlyph) {
          
          if (generalGlyph.isSetListOfSubGlyphs()) {
            return new UnknownCoreAttributeValidationFunction<ListOf<GraphicalObject>>().check(ctx, generalGlyph.getListOfSubGlyphs())
                && new UnknownPackageAttributeValidationFunction<ListOf<GraphicalObject>>(LayoutConstants.shortLabel).check(ctx, generalGlyph.getListOfSubGlyphs());
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
