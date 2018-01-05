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
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.ext.layout.LayoutConstants;
import org.sbml.jsbml.ext.layout.ReactionGlyph;
import org.sbml.jsbml.ext.layout.SpeciesReferenceGlyph;
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
 * Defines validation rules (as {@link ValidationFunction} instances) for the {@link ReactionGlyph} class.
 *  
 * @author rodrigue
 * @since 1.3
 */
public class ReactionGlyphConstraints extends AbstractConstraintDeclaration {

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

      addRangeToSet(set, LAYOUT_20701, LAYOUT_20712 );
      
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
    ValidationFunction<ReactionGlyph> func = null;

    switch (errorCode) {

    case LAYOUT_20701:
    {
      func = new UnknownCoreElementValidationFunction<ReactionGlyph>();
      break;
    }
    case LAYOUT_20702:
    {
      func = new UnknownCoreAttributeValidationFunction<ReactionGlyph>();
      break;
    }
    case LAYOUT_20703:
    {
      func = new ValidationFunction<ReactionGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, ReactionGlyph reactionGlyph) {
          
          return reactionGlyph.isSetListOfSpeciesReferenceGlyphs()
              && new DuplicatedElementValidationFunction<ReactionGlyph>(LayoutConstants.boundingBox).check(ctx, reactionGlyph)
              && new DuplicatedElementValidationFunction<ReactionGlyph>(LayoutConstants.curve).check(ctx, reactionGlyph)
              && new DuplicatedElementValidationFunction<ReactionGlyph>(LayoutConstants.listOfSpeciesReferenceGlyphs).check(ctx, reactionGlyph)
              && new UnknownPackageElementValidationFunction<ReactionGlyph>(LayoutConstants.shortLabel).check(ctx, reactionGlyph);
        }
      };
      break;
    }
    case LAYOUT_20704:
    {
      func = new UnknownPackageAttributeValidationFunction<ReactionGlyph>(LayoutConstants.shortLabel) {

        @Override
        public boolean check(ValidationContext ctx, ReactionGlyph reactionGlyph) {
        
          if (!reactionGlyph.isSetId()) {
            return false;
          }
          
          return super.check(ctx, reactionGlyph);
        }

      };
      break;
    }
    case LAYOUT_20705:
    {
      func = new ValidationFunction<ReactionGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, ReactionGlyph reactionGlyph) {
          
          if (reactionGlyph.isSetMetaidRef()) {
            return SyntaxChecker.isValidMetaId(reactionGlyph.getMetaidRef());
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_20706:
    {
      func = new ValidationFunction<ReactionGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, ReactionGlyph reactionGlyph) {
          
          if (reactionGlyph.isSetMetaidRef()) {
            return reactionGlyph.getSBMLDocument().getElementByMetaId(reactionGlyph.getMetaidRef()) != null;
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_20707:
    {
      func = new ValidationFunction<ReactionGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, ReactionGlyph reactionGlyph) {
          
          if (reactionGlyph.isSetReaction()) {
            return SyntaxChecker.isValidId(reactionGlyph.getReaction(), reactionGlyph.getLevel(), reactionGlyph.getVersion());
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_20708:
    {
      func = new ValidationFunction<ReactionGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, ReactionGlyph reactionGlyph) {
          
          if (reactionGlyph.isSetReaction()) {
            return reactionGlyph.getModel().getReaction(reactionGlyph.getReaction()) != null;
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_20709:
    {
      func = new ValidationFunction<ReactionGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, ReactionGlyph reactionGlyph) {
          
          if (reactionGlyph.isSetReaction() && reactionGlyph.isSetMetaidRef()) {
            try {
              Reaction c = reactionGlyph.getModel().getReaction(reactionGlyph.getReaction());
              Reaction cFromRef = (Reaction) reactionGlyph.getSBMLDocument().getElementByMetaId(reactionGlyph.getMetaidRef());

              if ((c == null || cFromRef == null) && c != cFromRef) {
                return false;
              } else if (! (c != null && cFromRef != null && c.equals(cFromRef))) {
                return false;
              }
            } catch (ClassCastException e) {
              // can happen if the metaidRef point to an other element than a Reaction
              return false;
            }
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_20710:
    {
      func = new ValidationFunction<ReactionGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, ReactionGlyph reactionGlyph) {
          
          if (reactionGlyph.isSetListOfSpeciesReferenceGlyphs()) {
            return new UnknownElementValidationFunction<ListOf<SpeciesReferenceGlyph>>().check(ctx, reactionGlyph.getListOfSpeciesReferenceGlyphs());
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_20711:
    {
      func = new ValidationFunction<ReactionGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, ReactionGlyph reactionGlyph) {
          
          if (reactionGlyph.isSetListOfSpeciesReferenceGlyphs()) {
            return new UnknownCoreAttributeValidationFunction<ListOf<SpeciesReferenceGlyph>>().check(ctx, reactionGlyph.getListOfSpeciesReferenceGlyphs())
                && new UnknownPackageAttributeValidationFunction<ListOf<SpeciesReferenceGlyph>>(LayoutConstants.shortLabel).check(ctx, reactionGlyph.getListOfSpeciesReferenceGlyphs());
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_20712:
    {
      func = new ValidationFunction<ReactionGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, ReactionGlyph reactionGlyph) {
          
          if (reactionGlyph.isSetListOfSpeciesReferenceGlyphs()) {
            return reactionGlyph.getSpeciesReferenceGlyphCount() > 0;
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
