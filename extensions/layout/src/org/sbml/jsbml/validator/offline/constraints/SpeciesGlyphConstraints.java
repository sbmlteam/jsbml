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

import org.sbml.jsbml.Species;
import org.sbml.jsbml.ext.layout.LayoutConstants;
import org.sbml.jsbml.ext.layout.SpeciesGlyph;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.SyntaxChecker;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.DuplicatedElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageElementValidationFunction;;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the {@link SpeciesGlyph} class.
 *  
 * @author rodrigue
 * @since 1.3
 */
public class SpeciesGlyphConstraints extends AbstractConstraintDeclaration {

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

      addRangeToSet(set, LAYOUT_20601, LAYOUT_20609);
      
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
    ValidationFunction<SpeciesGlyph> func = null;

    switch (errorCode) {

    case LAYOUT_20601:
    {
      func = new UnknownCoreElementValidationFunction<SpeciesGlyph>();
      break;
    }
    case LAYOUT_20602:
    {
      func = new UnknownCoreAttributeValidationFunction<SpeciesGlyph>();
      break;
    }
    case LAYOUT_20603:
    {
      func = new ValidationFunction<SpeciesGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, SpeciesGlyph speciesGlyph) {
          
          return new DuplicatedElementValidationFunction<SpeciesGlyph>(LayoutConstants.boundingBox).check(ctx, speciesGlyph)
              && new UnknownPackageElementValidationFunction<SpeciesGlyph>(LayoutConstants.shortLabel).check(ctx, speciesGlyph);
        }
      };
      break;
    }
    case LAYOUT_20604:
    {
      func = new UnknownPackageAttributeValidationFunction<SpeciesGlyph>(LayoutConstants.shortLabel) {

        @Override
        public boolean check(ValidationContext ctx, SpeciesGlyph speciesGlyph) {
        
          if (!speciesGlyph.isSetId()) {
            return false;
          }
          
          return super.check(ctx, speciesGlyph);
        }

      };
      break;
    }
    case LAYOUT_20605:
    {
      func = new ValidationFunction<SpeciesGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, SpeciesGlyph speciesGlyph) {
          
          if (speciesGlyph.isSetMetaidRef()) {
            return SyntaxChecker.isValidMetaId(speciesGlyph.getMetaidRef());
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_20606:
    {
      func = new ValidationFunction<SpeciesGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, SpeciesGlyph speciesGlyph) {
          
          if (speciesGlyph.isSetMetaidRef()) {
            return speciesGlyph.getSBMLDocument().getElementByMetaId(speciesGlyph.getMetaidRef()) != null;
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_20607:
    {
      func = new ValidationFunction<SpeciesGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, SpeciesGlyph speciesGlyph) {
          
          if (speciesGlyph.isSetSpecies()) {
            return SyntaxChecker.isValidId(speciesGlyph.getSpecies(), speciesGlyph.getLevel(), speciesGlyph.getVersion());
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_20608:
    {
      func = new ValidationFunction<SpeciesGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, SpeciesGlyph speciesGlyph) {
          
          if (speciesGlyph.isSetSpecies()) {
            return speciesGlyph.getModel().getSpecies(speciesGlyph.getSpecies()) != null;
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_20609:
    {
      func = new ValidationFunction<SpeciesGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, SpeciesGlyph speciesGlyph) {
          
          if (speciesGlyph.isSetSpecies() && speciesGlyph.isSetMetaidRef()) {
            try {
              Species c = speciesGlyph.getModel().getSpecies(speciesGlyph.getSpecies());
              Species cFromRef = (Species) speciesGlyph.getSBMLDocument().getElementByMetaId(speciesGlyph.getMetaidRef());

              if ((c == null || cFromRef == null) && c != cFromRef) {
                return false;
              } else if (! (c != null && cFromRef != null && c.equals(cFromRef))) {
                return false;
              }
            } catch (ClassCastException e) {
              // can happen if the metaidRef point to an other element than a Species
              return false;
            }
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
