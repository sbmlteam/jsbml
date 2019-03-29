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

import org.sbml.jsbml.ext.multi.MultiCompartmentPlugin;
import org.sbml.jsbml.ext.multi.MultiConstants;
import org.sbml.jsbml.ext.multi.PossibleSpeciesFeatureValue;
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
public class PossibleSpeciesFeatureValueConstraints extends AbstractConstraintDeclaration{

  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category, ValidationContext context) {

    switch (category) {
    case GENERAL_CONSISTENCY:

      if (level >= 3)
      {
        addRangeToSet(set, MULTI_20701, MULTI_20704);
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
  public ValidationFunction<PossibleSpeciesFeatureValue> getValidationFunction(int errorCode, ValidationContext context) {
    ValidationFunction<PossibleSpeciesFeatureValue> func = null;

    switch (errorCode) {

    case MULTI_20701:
    {
      // A PossibleSpeciesFeatureValue object may have the optional SBML Level 3 Core attributes metaid and
      // sboTerm. No other attributes from the SBML Level 3 Core namespace are permitted on a
      // PossibleSpeciesFeatureValue object
      func = new UnknownCoreAttributeValidationFunction<PossibleSpeciesFeatureValue>();
      break;
    }
    case MULTI_20702:
    {
      // A PossibleSpeciesFeatureValue object may have the optional SBML Level 3 Core subobjects for notes
      // and annotation. No other elements from the SBML Level 3 Core namespace are permitted
      // on a PossibleSpeciesFeatureValue object
      func = new UnknownCoreElementValidationFunction<PossibleSpeciesFeatureValue>();
      break;
    }
    case MULTI_20703:
    {
      func = new ValidationFunction<PossibleSpeciesFeatureValue>() {
        @Override
        public boolean check(ValidationContext ctx, PossibleSpeciesFeatureValue psfv) {

          // A PossibleSpeciesFeatureValue object must have the required attributes id,
          // and may have the optional attribute name and numericValue. No other attributes from the Multi namespace
          // are permitted on a PossibleSpeciesFeatureValue object
          if (!psfv.isSetId()) {
            return false;
          }
          
          return new UnknownPackageAttributeValidationFunction<PossibleSpeciesFeatureValue>(MultiConstants.shortLabel).check(ctx, psfv);
        }
      };
      break;
    }
    case MULTI_20704:
    {
      func = new ValidationFunction<PossibleSpeciesFeatureValue>() {
        @Override
        public boolean check(ValidationContext ctx, PossibleSpeciesFeatureValue psfv) {
          
          // The value of the numericValue attribute on a given PossibleSpeciesFeatureValue object
          // must be the identifier of a Parameter object defined in the same Model object
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
