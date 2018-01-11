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

import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.JSBML;
import org.sbml.jsbml.ext.layout.CompartmentGlyph;
import org.sbml.jsbml.ext.layout.LayoutConstants;
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
 * Defines validation rules (as {@link ValidationFunction} instances) for the {@link CompartmentGlyph} class.
 *  
 * @author rodrigue
 * @since 1.3
 */
public class CompartmentGlyphConstraints extends AbstractConstraintDeclaration {

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

      addRangeToSet(set, LAYOUT_20501, LAYOUT_20510);
      
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
    ValidationFunction<CompartmentGlyph> func = null;

    switch (errorCode) {

    case LAYOUT_20501:
    {
      func = new UnknownCoreElementValidationFunction<CompartmentGlyph>();
      break;
    }
    case LAYOUT_20502:
    {
      func = new UnknownCoreAttributeValidationFunction<CompartmentGlyph>();
      break;
    }
    case LAYOUT_20503:
    {
      func = new ValidationFunction<CompartmentGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, CompartmentGlyph compartmentGlyph) {
          
          return new DuplicatedElementValidationFunction<CompartmentGlyph>(LayoutConstants.boundingBox).check(ctx, compartmentGlyph)
              && new UnknownPackageElementValidationFunction<CompartmentGlyph>(LayoutConstants.shortLabel).check(ctx, compartmentGlyph);
        }
      };
      break;
    }
    case LAYOUT_20504:
    {
      func = new UnknownPackageAttributeValidationFunction<CompartmentGlyph>(LayoutConstants.shortLabel) {

        @Override
        public boolean check(ValidationContext ctx, CompartmentGlyph compartmentGlyph) {
        
          if (!compartmentGlyph.isSetId()) {
            return false;
          }
          
          return super.check(ctx, compartmentGlyph);
        }

      };
      break;
    }
    case LAYOUT_20505:
    {
      func = new ValidationFunction<CompartmentGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, CompartmentGlyph compartmentGlyph) {
          
          if (compartmentGlyph.isSetMetaidRef()) {
            return SyntaxChecker.isValidMetaId(compartmentGlyph.getMetaidRef());
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_20506:
    {
      func = new ValidationFunction<CompartmentGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, CompartmentGlyph compartmentGlyph) {
          
          if (compartmentGlyph.isSetMetaidRef()) {
            return compartmentGlyph.getSBMLDocument().getElementByMetaId(compartmentGlyph.getMetaidRef()) != null;
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_20507:
    {
      func = new ValidationFunction<CompartmentGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, CompartmentGlyph compartmentGlyph) {
          
          if (compartmentGlyph.isSetCompartment()) {
            return SyntaxChecker.isValidId(compartmentGlyph.getCompartment(), 
                compartmentGlyph.getLevel(), compartmentGlyph.getVersion());
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_20508:
    {
      func = new ValidationFunction<CompartmentGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, CompartmentGlyph compartmentGlyph) {
          
          if (compartmentGlyph.isSetCompartment()) {
            return compartmentGlyph.getModel().getCompartment(compartmentGlyph.getCompartment()) != null;
          }
          
          return true;
        }
      };
      break;
    }
    case LAYOUT_20509:
    {
      func = new ValidationFunction<CompartmentGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, CompartmentGlyph compartmentGlyph) {
          
          if (compartmentGlyph.isSetCompartment() && compartmentGlyph.isSetMetaidRef()) {
            try {
              Compartment c = compartmentGlyph.getModel().getCompartment(compartmentGlyph.getCompartment());
              Compartment cFromRef = (Compartment) compartmentGlyph.getSBMLDocument().getElementByMetaId(compartmentGlyph.getMetaidRef());

              if ((c == null || cFromRef == null) && c != cFromRef) {
                return false;
              } else if (! (c != null && cFromRef != null && c.equals(cFromRef))) {
                return false;
              }
            } catch (ClassCastException e) {
              // can happen if the metaidRef point to an other element than a Compartment
              return false;
            }
          }

          return true;
        }
      };
      break;
    }
    case LAYOUT_20510:
    {
      func = new ValidationFunction<CompartmentGlyph>() {

        @Override
        public boolean check(ValidationContext ctx, CompartmentGlyph compartmentGlyph) {

          // check if order is of type double
          if ((!compartmentGlyph.isSetOrder()) && compartmentGlyph.getUserObject(JSBML.UNKNOWN_XML) != null) {
            // checking if the order attribute is there (meaning a NumberFormatException was encountered).
            XMLNode unknown = (XMLNode) compartmentGlyph.getUserObject(JSBML.UNKNOWN_XML);

            if (unknown.getAttrIndex(LayoutConstants.order) != -1) {
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
