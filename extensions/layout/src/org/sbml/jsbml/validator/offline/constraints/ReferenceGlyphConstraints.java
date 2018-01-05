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
import org.sbml.jsbml.ext.layout.ReferenceGlyph;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.SyntaxChecker;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.DuplicatedElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageElementValidationFunction;;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the {@link ReferenceGlyph} class.
 *  
 * @author rodrigue
 * @since 1.3
 */
public class ReferenceGlyphConstraints extends AbstractConstraintDeclaration {

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

      addRangeToSet(set, LAYOUT_21101, LAYOUT_21112 );
      
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
    ValidationFunction<ReferenceGlyph> func = null;

    switch (errorCode) {

    case LAYOUT_21101:
    {
      func = new UnknownCoreElementValidationFunction<ReferenceGlyph>();
      break;
    }
    case LAYOUT_21102:
    {
      func = new UnknownCoreAttributeValidationFunction<ReferenceGlyph>();
      break;
    }
    case LAYOUT_21103:
    {
      func = new ValidationFunction<ReferenceGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, ReferenceGlyph referenceGlyph) {
          
          return new DuplicatedElementValidationFunction<ReferenceGlyph>(LayoutConstants.boundingBox).check(ctx, referenceGlyph)
              && new DuplicatedElementValidationFunction<ReferenceGlyph>(LayoutConstants.curve).check(ctx, referenceGlyph)
              && new UnknownPackageElementValidationFunction<ReferenceGlyph>(LayoutConstants.shortLabel).check(ctx, referenceGlyph);
        }
      };
      break;
    }
    case LAYOUT_21104:
    {
      func = new UnknownPackageAttributeValidationFunction<ReferenceGlyph>(LayoutConstants.shortLabel) {

        @Override
        public boolean check(ValidationContext ctx, ReferenceGlyph referenceGlyph) {
        
          if (! (referenceGlyph.isSetId() && referenceGlyph.isSetGlyph())) {
            return false;
          }
          
          return super.check(ctx, referenceGlyph);
        }

      };
      break;
    }
    case LAYOUT_21105:
    {
      func = new ValidationFunction<ReferenceGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, ReferenceGlyph referenceGlyph) {
          
          if (referenceGlyph.isSetMetaidRef()) {
            return SyntaxChecker.isValidMetaId(referenceGlyph.getMetaidRef());
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_21106:
    {
      func = new ValidationFunction<ReferenceGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, ReferenceGlyph referenceGlyph) {
          
          if (referenceGlyph.isSetMetaidRef()) {
            return referenceGlyph.getSBMLDocument().getElementByMetaId(referenceGlyph.getMetaidRef()) != null;
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_21107:
    {
      func = new ValidationFunction<ReferenceGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, ReferenceGlyph referenceGlyph) {
          
          if (referenceGlyph.isSetReference()) {
            return SyntaxChecker.isValidId(referenceGlyph.getReference(), referenceGlyph.getLevel(), referenceGlyph.getVersion());
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_21108:
    {
      func = new ValidationFunction<ReferenceGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, ReferenceGlyph referenceGlyph) {
          
          if (referenceGlyph.isSetReference()) {
            SBase sbase = referenceGlyph.getModel().getElementBySId(referenceGlyph.getReference());
            
            return sbase != null;
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_21109:
    {
      func = new ValidationFunction<ReferenceGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, ReferenceGlyph referenceGlyph) {
          
          if (referenceGlyph.isSetReference() && referenceGlyph.isSetMetaidRef()) {
            SBase sbase = referenceGlyph.getModel().getElementBySId(referenceGlyph.getReference());
            SBase sbaseFromMetaidRef = referenceGlyph.getSBMLDocument().getElementByMetaId(referenceGlyph.getMetaidRef());

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
    case LAYOUT_21110:
    {
      func = new ValidationFunction<ReferenceGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, ReferenceGlyph referenceGlyph) {
          
          if (referenceGlyph.isSetGlyph()) {
            return SyntaxChecker.isValidId(referenceGlyph.getGlyph(), referenceGlyph.getLevel(), referenceGlyph.getVersion());
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_21111:
    {
      func = new ValidationFunction<ReferenceGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, ReferenceGlyph referenceGlyph) {
          
          if (referenceGlyph.isSetGlyph()) {
            Layout layout = (Layout) referenceGlyph.getParent().getParent().getParent().getParent();
            SBase sbase = layout.getElementBySId(referenceGlyph.getGlyph()); // TODO - do a separate HashMap on Layout for validation ?
            
            return sbase != null && sbase instanceof GraphicalObject;
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_21112:
    {
      func = new ValidationFunction<ReferenceGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, ReferenceGlyph referenceGlyph) {

          // check if role is valid
          // nothing to do as it can be any String

          return true;
        }
      };
      break;
    }
    }

    return func;
  }
}
