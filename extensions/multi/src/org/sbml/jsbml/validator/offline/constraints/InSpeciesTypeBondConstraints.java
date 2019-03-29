/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2019 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The Babraham Institute, Cambridge, UK
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

import org.sbml.jsbml.ext.multi.InSpeciesTypeBond;
import org.sbml.jsbml.ext.multi.MultiCompartmentPlugin;
import org.sbml.jsbml.ext.multi.MultiConstants;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;

/**
 * Constraints declaration for the {@link MultiCompartmentPlugin} class.
 * 
 * @author Nicolas Rodriguez
 * @since 1.5
 */
public class InSpeciesTypeBondConstraints extends AbstractConstraintDeclaration{

  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category, ValidationContext context) {

    switch (category) {
    case GENERAL_CONSISTENCY:

      if (level >= 3)
      {
        addRangeToSet(set, MULTI_21101, MULTI_21106);
      }

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
  public void addErrorCodesForAttribute(Set<Integer> set, int level,
    int version, String attributeName, ValidationContext context) {

    /*switch (attributeName) {
      // TODO
    }*/


  }

  @Override
  public ValidationFunction<InSpeciesTypeBond> getValidationFunction(int errorCode, ValidationContext context) {
    ValidationFunction<InSpeciesTypeBond> func = null;

    switch (errorCode) {

    case MULTI_21101:
    {
      // A InSpeciesTypeBond object may have the optional SBML Level 3 Core attributes metaid and
      // sboTerm. No other attributes from the SBML Level 3 Core namespace are permitted on a
      // InSpeciesTypeBond object
      func = new UnknownCoreAttributeValidationFunction<InSpeciesTypeBond>();
      break;
    }
    case MULTI_21102:
    {
      // A InSpeciesTypeBond object may have the optional SBML Level 3 Core subobjects for notes
      // and annotation. No other elements from the SBML Level 3 Core namespace are permitted
      // on a InSpeciesTypeBond object
      func = new UnknownCoreElementValidationFunction<InSpeciesTypeBond>();
      break;
    }
    case MULTI_21103:
    {
      func = new ValidationFunction<InSpeciesTypeBond>() {
        @Override
        public boolean check(ValidationContext ctx, InSpeciesTypeBond istb) {

          // An InSpeciesTypeBond object must have the required attributes, bindingSite1 and
          // bindingSite2, and may have the optional attributes, id and name. No
          // other attributes from the Multi namespace are permitted on an InSpeciesTypeBond object.
          if (!istb.isSetBindingSite1() || !istb.isSetBindingSite2()) {
            return false;
          }
          
          return new UnknownPackageAttributeValidationFunction<InSpeciesTypeBond>(MultiConstants.shortLabel).check(ctx, istb);
        }
      };
      break;
    }
    case MULTI_21104:
    {
      func = new ValidationFunction<InSpeciesTypeBond>() {
        @Override
        public boolean check(ValidationContext ctx, InSpeciesTypeBond istb) {
          
          // The value of the bindingSite1 attribute on a given InSpeciesTypeBond object must
          // be the identifier of a SpeciesTypeInstance object or SpeciesTypeComponentIndex which ultimately
          // reference a object of BindingSiteSpeciesType
          // TODO
          return true;
        }
      };
      break;
    }
    case MULTI_21105:
    {
      func = new ValidationFunction<InSpeciesTypeBond>() {
        @Override
        public boolean check(ValidationContext ctx, InSpeciesTypeBond istb) {

          // The value of the bindingSite2 attribute on a given InSpeciesTypeBond object must
          // be the identifier of a SpeciesTypeInstance object or SpeciesTypeComponentIndex which ultimately
          // reference a object of BindingSiteSpeciesType
          // TODO
          return true;
        }
      };
      break;
    }
    case MULTI_21106:
    {
      func = new ValidationFunction<InSpeciesTypeBond>() {
        @Override
        public boolean check(ValidationContext ctx, InSpeciesTypeBond istb) {

          // The bindingSite1 and bindingSite2 attributes must not reference the same BindingSiteSpeciesType object
          // TODO
          return true;
        }
      };
      break;
    }
    }
    
    return func;
  }
}
