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

import org.sbml.jsbml.JSBML;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.ext.layout.LayoutConstants;
import org.sbml.jsbml.ext.layout.SpeciesGlyph;
import org.sbml.jsbml.ext.layout.SpeciesReferenceGlyph;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.SyntaxChecker;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.DuplicatedElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageElementValidationFunction;
import org.sbml.jsbml.xml.XMLNode;;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the {@link SpeciesReferenceGlyph} class.
 *  
 * @author rodrigue
 * @since 1.3
 */
public class SpeciesReferenceGlyphConstraints extends AbstractConstraintDeclaration {

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

      addRangeToSet(set, LAYOUT_21001, LAYOUT_21012 );
      
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
    ValidationFunction<SpeciesReferenceGlyph> func = null;

    switch (errorCode) {

    case LAYOUT_21001:
    {
      func = new UnknownCoreElementValidationFunction<SpeciesReferenceGlyph>();
      break;
    }
    case LAYOUT_21002:
    {
      func = new UnknownCoreAttributeValidationFunction<SpeciesReferenceGlyph>();
      break;
    }
    case LAYOUT_21003:
    {
      func = new ValidationFunction<SpeciesReferenceGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, SpeciesReferenceGlyph speciesRefGlyph) {
          
          return new DuplicatedElementValidationFunction<SpeciesReferenceGlyph>(LayoutConstants.boundingBox).check(ctx, speciesRefGlyph)
              && new DuplicatedElementValidationFunction<SpeciesReferenceGlyph>(LayoutConstants.curve).check(ctx, speciesRefGlyph)
              && new UnknownPackageElementValidationFunction<SpeciesReferenceGlyph>(LayoutConstants.shortLabel).check(ctx, speciesRefGlyph);
        }
      };
      break;
    }
    case LAYOUT_21004:
    {
      func = new UnknownPackageAttributeValidationFunction<SpeciesReferenceGlyph>(LayoutConstants.shortLabel) {

        @Override
        public boolean check(ValidationContext ctx, SpeciesReferenceGlyph speciesRefGlyph) {
        
          if (! (speciesRefGlyph.isSetId() && speciesRefGlyph.isSetSpeciesGlyph())) {
            return false;
          }
          
          return super.check(ctx, speciesRefGlyph);
        }

      };
      break;
    }
    case LAYOUT_21005:
    {
      func = new ValidationFunction<SpeciesReferenceGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, SpeciesReferenceGlyph speciesRefGlyph) {
          
          if (speciesRefGlyph.isSetMetaidRef()) {
            return SyntaxChecker.isValidMetaId(speciesRefGlyph.getMetaidRef());
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_21006:
    {
      func = new ValidationFunction<SpeciesReferenceGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, SpeciesReferenceGlyph speciesRefGlyph) {
          
          if (speciesRefGlyph.isSetMetaidRef()) {
            return speciesRefGlyph.getSBMLDocument().getElementByMetaId(speciesRefGlyph.getMetaidRef()) != null;
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_21007:
    {
      func = new ValidationFunction<SpeciesReferenceGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, SpeciesReferenceGlyph speciesRefGlyph) {
          
          if (speciesRefGlyph.isSetSpeciesReference()) {
            return SyntaxChecker.isValidId(speciesRefGlyph.getSpeciesReference(), speciesRefGlyph.getLevel(), speciesRefGlyph.getVersion());
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_21008:
    {
      func = new ValidationFunction<SpeciesReferenceGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, SpeciesReferenceGlyph speciesRefGlyph) {
          
          if (speciesRefGlyph.isSetSpeciesReference()) {
            SBase sbase = speciesRefGlyph.getModel().getElementBySId(speciesRefGlyph.getSpeciesReference());
            
            return sbase != null && sbase instanceof SpeciesReference;
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_21009:
    {
      func = new ValidationFunction<SpeciesReferenceGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, SpeciesReferenceGlyph speciesRefGlyph) {
          
          if (speciesRefGlyph.isSetSpeciesReference() && speciesRefGlyph.isSetMetaidRef()) {
            SBase c = speciesRefGlyph.getModel().getElementBySId(speciesRefGlyph.getSpeciesReference());
            SBase cFromRef = speciesRefGlyph.getSBMLDocument().getElementByMetaId(speciesRefGlyph.getMetaidRef());

            if ((c == null || cFromRef == null) && c != cFromRef) {
              return false;
            } else if (! (c != null && cFromRef != null && c.equals(cFromRef))) {
              return false;
            }
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_21010:
    {
      func = new ValidationFunction<SpeciesReferenceGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, SpeciesReferenceGlyph speciesRefGlyph) {
          
          if (speciesRefGlyph.isSetSpeciesGlyph()) {
            return SyntaxChecker.isValidId(speciesRefGlyph.getSpeciesGlyph(), speciesRefGlyph.getLevel(), speciesRefGlyph.getVersion());
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_21011:
    {
      func = new ValidationFunction<SpeciesReferenceGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, SpeciesReferenceGlyph speciesRefGlyph) {
          
          if (speciesRefGlyph.isSetSpeciesGlyph()) {
            Layout layout = (Layout) speciesRefGlyph.getParent().getParent().getParent().getParent();
            SBase sbase = layout.getElementBySId(speciesRefGlyph.getSpeciesGlyph()); // TODO - do a separate HashMap on Layout for validation ?
            
            return sbase != null && sbase instanceof SpeciesGlyph;
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_21012:
    {
      func = new ValidationFunction<SpeciesReferenceGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, SpeciesReferenceGlyph speciesRefGlyph) {

          // check if role is valid
          if (!speciesRefGlyph.isSetSpeciesReferenceRole() && speciesRefGlyph.getUserObject(JSBML.UNKNOWN_XML) != null) {
            // checking if the role attribute is there (meaning a wrong enum value was encountered).
            XMLNode unknown = (XMLNode) speciesRefGlyph.getUserObject(JSBML.UNKNOWN_XML);

            if (unknown.getAttrIndex(LayoutConstants.role) != -1) {
              // TODO - create error message
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
