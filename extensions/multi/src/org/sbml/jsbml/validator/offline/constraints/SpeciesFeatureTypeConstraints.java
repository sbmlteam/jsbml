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

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.ext.multi.MultiCompartmentPlugin;
import org.sbml.jsbml.ext.multi.MultiConstants;
import org.sbml.jsbml.ext.multi.PossibleSpeciesFeatureValue;
import org.sbml.jsbml.ext.multi.SpeciesFeatureType;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;

/**
 * Constraints declaration for the {@link MultiCompartmentPlugin} class.
 * 
 * @author Nicolas Rodriguez
 * @since 1.5
 */
public class SpeciesFeatureTypeConstraints extends AbstractConstraintDeclaration{

  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category, ValidationContext context) {

    switch (category) {
    case GENERAL_CONSISTENCY:

      if (level >= 3)
      {
        addRangeToSet(set, MULTI_20601, MULTI_20608);
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
  public ValidationFunction<SpeciesFeatureType> getValidationFunction(int errorCode, ValidationContext context) {
    ValidationFunction<SpeciesFeatureType> func = null;

    switch (errorCode) {

    case MULTI_20601:
    {
      // A SpeciesFeatureType object may have the optional SBML Level 3 Core attributes metaid and
      // sboTerm. No other attributes from the SBML Level 3 Core namespace are permitted on a
      // SpeciesFeatureType object
      func = new UnknownCoreAttributeValidationFunction<SpeciesFeatureType>();
      break;
    }
    case MULTI_20602:
    {
      // A SpeciesFeatureType object may have the optional SBML Level 3 Core subobjects for notes
      // and annotation. No other elements from the SBML Level 3 Core namespace are permitted
      // on a SpeciesFeatureType object
      func = new UnknownCoreElementValidationFunction<SpeciesFeatureType>();
      break;
    }
    case MULTI_20603:
    {
      func = new ValidationFunction<SpeciesFeatureType>() {
        @Override
        public boolean check(ValidationContext ctx, SpeciesFeatureType sft) {

          // A SpeciesFeatureType object must have the required attributes id and occur,
          // and may have the optional attribute name. No other attributes from the Multi namespace
          // are permitted on a SpeciesFeatureType object
          if (!sft.isSetId() || !sft.isSetOccur()) {
            return false;
          }
          
          return new UnknownPackageAttributeValidationFunction<SpeciesFeatureType>(MultiConstants.shortLabel).check(ctx, sft);
        }
      };
      break;
    }
    case MULTI_20604:
    {
      func = new ValidationFunction<SpeciesFeatureType>() {
        @Override
        public boolean check(ValidationContext ctx, SpeciesFeatureType sft) {
          
          // The value of the occur attribute on a given SpeciesFeatureType object must conform
          // to the syntax of the SBML data type positive Integer
          // TODO
          return true;
        }
      };
      break;
    }
    case MULTI_20605:
    {
      func = new ValidationFunction<SpeciesFeatureType>() {
        @Override
        public boolean check(ValidationContext ctx, SpeciesFeatureType sft) {

          // One ListOfPossibleSpeciesFeatureValues subobject in a SpeciesFeatureType object is required
          // TODO
          return true;
        }
      };
      break;
    }
    case MULTI_20606:
    {
      // A ListOfPossibleSpeciesFeatureValues object may have the optional SBML core attributes
      // metaid and sboTerm. No other attributes from the SBML Level 3 Core namespace or the
      // Multi namespace are permitted on a ListOfPossibleSpeciesFeatureValues object
      func = new ValidationFunction<SpeciesFeatureType>() {
        @Override
        public boolean check(ValidationContext ctx, SpeciesFeatureType sft) {

          if (sft.isSetListOfPossibleSpeciesFeatureValues()) {
            return new UnknownAttributeValidationFunction<ListOf<PossibleSpeciesFeatureValue>>().check(ctx, sft.getListOfPossibleSpeciesFeatureValues());
          }

          return true;
        }
      };
      break;
    }
    case MULTI_20607:
    {
      func = new ValidationFunction<SpeciesFeatureType>() {
        @Override
        public boolean check(ValidationContext ctx, SpeciesFeatureType sft) {
          
          if (sft.isSetListOfPossibleSpeciesFeatureValues()) {
          // Apart from the general notes and annotation subobjects permitted on all SBML objects,
          // a ListOfPossibleSpeciesFeatureValues container object may only contain PossibleSpecies-
          // FeatureValue objects.
            return new UnknownElementValidationFunction<ListOf<PossibleSpeciesFeatureValue>>().check(ctx, sft.getListOfPossibleSpeciesFeatureValues());
          }
          
          return true;
        }
      };
      break;
    }
    case MULTI_20608:
    {
      func = new ValidationFunction<SpeciesFeatureType>() {
        @Override
        public boolean check(ValidationContext ctx, SpeciesFeatureType sft) {
          
          if (sft.isSetListOfPossibleSpeciesFeatureValues()) {
            // A ListOfPossibleSpeciesFeatureValues object must not be empty
            return sft.getPossibleSpeciesFeatureValueCount() > 0;
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
