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
import org.sbml.jsbml.Model;
import org.sbml.jsbml.ext.multi.InSpeciesTypeBond;
import org.sbml.jsbml.ext.multi.MultiCompartmentPlugin;
import org.sbml.jsbml.ext.multi.MultiConstants;
import org.sbml.jsbml.ext.multi.MultiSpeciesType;
import org.sbml.jsbml.ext.multi.SpeciesFeatureType;
import org.sbml.jsbml.ext.multi.SpeciesTypeComponentIndex;
import org.sbml.jsbml.ext.multi.SpeciesTypeInstance;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.DuplicatedElementValidationFunction;
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
public class MultiSpeciesTypeConstraints extends AbstractConstraintDeclaration{

  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category, ValidationContext context) {

    switch (category) {
    case GENERAL_CONSISTENCY:

      if (level >= 3)
      {
        addRangeToSet(set, MULTI_20401, MULTI_20417);
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
  public ValidationFunction<MultiSpeciesType> getValidationFunction(int errorCode, ValidationContext context) {
    ValidationFunction<MultiSpeciesType> func = null;

    switch (errorCode) {

    case MULTI_20401:
    {
      // A SpeciesType object may have the optional SBMLLevel 3 Core attributes metaid and sboTerm.
      // No other attributes from the SBML Level 3 Core namespace are permitted on a SpeciesType
      // object.
      func = new UnknownCoreAttributeValidationFunction<MultiSpeciesType>();
      break;
    }
    case MULTI_20402:
    {
      // A SpeciesType object may have the optional SBML Level 3 Core subobjects for notes and
      // annotation. No other elements from the SBML Level 3 Core namespace are permitted on a
      // SpeciesType object
      func = new UnknownCoreElementValidationFunction<MultiSpeciesType>();
      break;
    }
    case MULTI_20403:
    {
      // A SpeciesType object must have the required attribute id, and may have the optional
      // attributes name and compartment. No other attributes from the Multi namespace
      // are permitted on a SpeciesType object
      func = new UnknownPackageAttributeValidationFunction<MultiSpeciesType>(MultiConstants.shortLabel);
      break;
    }
    case MULTI_20404:
    {
      func = new ValidationFunction<MultiSpeciesType>() {
        @Override
        public boolean check(ValidationContext ctx, MultiSpeciesType st) {

          // The value of the compartment attribute, if set on a given SpeciesType object, must be
          // the value of an id attribute on an existing Compartment object in the SId namespace of the
          // parent Model object
          if (st.isSetCompartment()) {
            Model m = st.getModel();
            
            if (m != null && m.getCompartment(st.getCompartment()) == null) {
              return false;
            }
          }
          
          return true;
        }
      };
      break;
    }
    case MULTI_20405:
    {
      func = new ValidationFunction<MultiSpeciesType>() {
        @Override
        public boolean check(ValidationContext ctx, MultiSpeciesType st) {
          boolean isValid = true;
          
          // The various ListOf subobjects within a SpeciesType object are optional, but if present, these
          // container objects must not be empty. Specifically, if any of the following classes of objects are
          // present with a SpeciesType object, it must not be empty: ListOfSpeciesFeatureTypes, ListOf-
          // SpeciesTypeInstances, ListOfSpeciesTypeComponentIndexes and ListOfInSpeciesTypeBond
          if (st.isSetListOfSpeciesFeatureTypes()) {
            isValid = st.getSpeciesFeatureTypeCount() > 0;
          }
          if (st.isSetListOfSpeciesTypeInstances()) {
            isValid &= st.getSpeciesTypeInstanceCount() > 0;
          }
          if (st.isSetListOfSpeciesTypeComponentIndexes()) {
            isValid &= st.getSpeciesTypeComponentIndexCount() > 0;
          }
          if (st.isSetListOfInSpeciesTypeBonds()) {
            isValid &= st.getInSpeciesTypeBondCount() > 0;
          }
          
          return isValid;
        }
      };
      break;
    }
    case MULTI_20406:
    {
      // There may be at most one ListOfSpeciesFeatureTypes container object within a SpeciesType object.
      func = new DuplicatedElementValidationFunction<>(MultiConstants.listOfSpeciesFeatures);
      break;
    }
    case MULTI_20407:
    {
      func = new ValidationFunction<MultiSpeciesType>() {
        @Override
        public boolean check(ValidationContext ctx, MultiSpeciesType st) {

          if (st.isSetListOfSpeciesFeatureTypes()) {
            // Apart from the general notes and annotation subobjects permitted on all SBML objects, a
            // ListOfSpeciesFeatureTypes container object may only contain SpeciesFeatureType objects
            return new UnknownElementValidationFunction<ListOf<SpeciesFeatureType>>().check(ctx, st.getListOfSpeciesFeatureTypes());
          }

          return true;
        }
      };
      break;
    }
    case MULTI_20408:
    {
      func = new ValidationFunction<MultiSpeciesType>() {
        @Override
        public boolean check(ValidationContext ctx, MultiSpeciesType st) {

          if (st.isSetListOfSpeciesFeatureTypes()) {
            // A ListOfSpeciesFeatureTypes object may have the optional SBML core attributes metaid and
            // sboTerm. No other attributes from the SBML Level 3 Core namespace or the Multi namespace
            // are permitted on a ListOfSpeciesFeatureTypes object.
            return new UnknownAttributeValidationFunction<ListOf<SpeciesFeatureType>>().check(ctx, st.getListOfSpeciesFeatureTypes());
          }

          return true;
        }
      };
      break;
    }
    case MULTI_20409:
    {
      // There may be at most one ListOfSpeciesTypeInstances container object within a SpeciesType object
      func = new DuplicatedElementValidationFunction<>(MultiConstants.listOfSpeciesTypeInstances);
      break;
    }
    case MULTI_20410:
    {
      func = new ValidationFunction<MultiSpeciesType>() {
        @Override
        public boolean check(ValidationContext ctx, MultiSpeciesType st) {

          if (st.isSetListOfSpeciesTypeInstances()) {
            // Apart from the general notes and annotation subobjects permitted on all SBML objects, a
            // ListOfSpeciesTypeInstances container object may only contain SpeciesTypeInstance objects
            return new UnknownElementValidationFunction<ListOf<SpeciesTypeInstance>>().check(ctx, st.getListOfSpeciesTypeInstances());
          }
          
          return true;
        }
      };
      break;
    }
    case MULTI_20411:
    {
      func = new ValidationFunction<MultiSpeciesType>() {
        @Override
        public boolean check(ValidationContext ctx, MultiSpeciesType st) {

          if (st.isSetListOfSpeciesTypeInstances()) {
          // A ListOfSpeciesTypeInstances object may have the optional SBML core attributes metaid and
          // sboTerm. No other attributes from the SBML Level 3 Core namespace or the Multi namespace
          // are permitted on a ListOfSpeciesTypeInstances
            return new UnknownAttributeValidationFunction<ListOf<SpeciesTypeInstance>>().check(ctx, st.getListOfSpeciesTypeInstances());
          }

          return true;
        }
      };
      break;
    }
    case MULTI_20412:
    {
      // There may be at most one ListOfSpeciesTypeComponentIndexes container object within a SpeciesType object
      func = new DuplicatedElementValidationFunction<>(MultiConstants.listOfSpeciesTypeComponentIndexes);
      break;
    }
    case MULTI_20413:
    {
      func = new ValidationFunction<MultiSpeciesType>() {
        @Override
        public boolean check(ValidationContext ctx, MultiSpeciesType st) {

          if (st.isSetListOfSpeciesTypeComponentIndexes()) {
            // Apart from the general notes and annotation subobjects permitted on all SBML objects, a
            // ListOfSpeciesTypeComponentIndexes container object may only contain
            // SpeciesTypeComponentIndex objects
            return new UnknownElementValidationFunction<ListOf<SpeciesTypeComponentIndex>>().check(ctx, st.getListOfSpeciesTypeComponentIndexes());
          }

          return true;
        }
      };
      break;
    }
    case MULTI_20414:
    {
      func = new ValidationFunction<MultiSpeciesType>() {
        @Override
        public boolean check(ValidationContext ctx, MultiSpeciesType st) {

          if (st.isSetListOfSpeciesTypeComponentIndexes()) {
            // A ListOfSpeciesTypeComponentIndexes object may have the optional SBML core attributes
            // metaid and sboTerm. No other attributes from the SBML Level 3 Core namespace or the Multi
            // namespace are permitted on a ListOfSpeciesTypeComponentIndexes object
            return new UnknownAttributeValidationFunction<ListOf<SpeciesTypeComponentIndex>>().check(ctx, st.getListOfSpeciesTypeComponentIndexes());
          }

          return true;
        }
      };
      break;
    }
    case MULTI_20415:
    {
      // There may be at most one ListOfInSpeciesTypeBonds container object within a SpeciesType object.
      func = new DuplicatedElementValidationFunction<>(MultiConstants.listOfInSpeciesTypeBonds);
      break;
    }
    case MULTI_20416:
    {
      func = new ValidationFunction<MultiSpeciesType>() {
        @Override
        public boolean check(ValidationContext ctx, MultiSpeciesType st) {

          if (st.isSetListOfInSpeciesTypeBonds()) {
            // Apart from the general notes and annotation subobjects permitted on all SBML objects, a
            // ListOfInSpeciesTypeBonds container object may only contain InSpeciesTypeBond objects
            return new UnknownElementValidationFunction<ListOf<InSpeciesTypeBond>>().check(ctx, st.getListOfInSpeciesTypeBonds());
          }

          return true;
        }
      };
      break;
    }
    case MULTI_20417:
    {
      func = new ValidationFunction<MultiSpeciesType>() {
        @Override
        public boolean check(ValidationContext ctx, MultiSpeciesType st) {

          if (st.isSetListOfInSpeciesTypeBonds()) {
            // A ListOfInSpeciesTypeBonds object may have the optional SBML core attributes metaid and
            // sboTerm. No other attributes from the SBML Level 3 Core namespace or the Multi namespace
            // are permitted on a ListOfInSpeciesTypeBonds object
            return new UnknownAttributeValidationFunction<ListOf<InSpeciesTypeBond>>().check(ctx, st.getListOfInSpeciesTypeBonds());
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
