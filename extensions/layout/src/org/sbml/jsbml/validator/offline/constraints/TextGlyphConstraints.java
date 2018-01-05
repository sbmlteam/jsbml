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

import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.layout.GraphicalObject;
import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.ext.layout.LayoutConstants;
import org.sbml.jsbml.ext.layout.TextGlyph;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.SyntaxChecker;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.DuplicatedElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the {@link TextGlyph} class.
 *  
 * @author rodrigue
 * @since 1.3
 */
public class TextGlyphConstraints extends AbstractConstraintDeclaration {

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

      addRangeToSet(set, LAYOUT_20901, LAYOUT_20912 );
      
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
    ValidationFunction<TextGlyph> func = null;

    switch (errorCode) {

    case LAYOUT_20901:
    {
      func = new UnknownCoreElementValidationFunction<TextGlyph>(); // Could we use the same ValidationFunction for all GraphicalObjects
      break;
    }
    case LAYOUT_20902:
    {
      func = new UnknownCoreAttributeValidationFunction<TextGlyph>();
      break;
    }
    case LAYOUT_20903:
    {
      func = new ValidationFunction<TextGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, TextGlyph textGlyph) {
          
          return textGlyph.isSetBoundingBox()
              && new DuplicatedElementValidationFunction<TextGlyph>(LayoutConstants.boundingBox).check(ctx, textGlyph);
        }
      };
      break;
    }
    case LAYOUT_20904:
    {
      func = new UnknownPackageAttributeValidationFunction<TextGlyph>(LayoutConstants.shortLabel) {

        @Override
        public boolean check(ValidationContext ctx, TextGlyph textGlyph) {
        
          if (!textGlyph.isSetId()) {
            return false;
          }
          
          return super.check(ctx, textGlyph);
        }

      };
      break;
    }
    case LAYOUT_20905:
    {
      func = new ValidationFunction<TextGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, TextGlyph textGlyph) {
          
          if (textGlyph.isSetMetaidRef()) {
            return SyntaxChecker.isValidMetaId(textGlyph.getMetaidRef());
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_20906:
    {
      func = new ValidationFunction<TextGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, TextGlyph textGlyph) {
          
          if (textGlyph.isSetMetaidRef()) {
            return textGlyph.getSBMLDocument().getElementByMetaId(textGlyph.getMetaidRef()) != null;
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_20907:
    {
      func = new ValidationFunction<TextGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, TextGlyph textGlyph) {
          
          if (textGlyph.isSetOriginOfText()) {
            return SyntaxChecker.isValidId(textGlyph.getOriginOfText(), textGlyph.getLevel(), textGlyph.getVersion());
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_20908:
    {
      func = new ValidationFunction<TextGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, TextGlyph textGlyph) {
          
          if (textGlyph.isSetOriginOfText()) {
            return textGlyph.getModel().getElementBySId(textGlyph.getOriginOfText()) != null;
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_20909:
    {
      func = new ValidationFunction<TextGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, TextGlyph textGlyph) {

          if (textGlyph.isSetGraphicalObject() && textGlyph.isSetMetaidRef()) {
            SBase sbase = textGlyph.getModel().getElementBySId(textGlyph.getGraphicalObject());
            SBase sbaseFromMetaidRef = textGlyph.getSBMLDocument().getElementByMetaId(textGlyph.getMetaidRef());

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
    case LAYOUT_20910:
    {
      func = new ValidationFunction<TextGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, TextGlyph textGlyph) {
          
          if (textGlyph.isSetGraphicalObject()) {
            return SyntaxChecker.isValidId(textGlyph.getGraphicalObject(), textGlyph.getLevel(), textGlyph.getVersion());
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_20911:
    {
      func = new ValidationFunction<TextGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, TextGlyph textGlyph) {
          
          if (textGlyph.isSetGraphicalObject()) {
            Layout layout = (Layout) textGlyph.getParent().getParent();
            SBase sbase = layout.getElementBySId(textGlyph.getGraphicalObject());
            
            return sbase != null && sbase instanceof GraphicalObject;
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_20712:
    {
      func = new ValidationFunction<TextGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, TextGlyph textGlyph) {

          // The attribute layout:text of a TextGlyph must be of the data type string.
          // Nothing to test here, it should be always true.
          
          return true;
        }
      };
      break;
    }
    }

    return func;
  }
}
