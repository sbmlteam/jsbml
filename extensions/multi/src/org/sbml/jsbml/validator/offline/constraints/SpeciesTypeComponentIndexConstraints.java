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
import org.sbml.jsbml.ext.multi.SpeciesTypeComponentIndex;
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
public class SpeciesTypeComponentIndexConstraints extends AbstractConstraintDeclaration{

  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category, ValidationContext context) {

    switch (category) {
    case GENERAL_CONSISTENCY:

      if (level >= 3)
      {
        addRangeToSet(set, MULTI_20901, MULTI_20907);
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
  public ValidationFunction<SpeciesTypeComponentIndex> getValidationFunction(int errorCode, ValidationContext context) {
    ValidationFunction<SpeciesTypeComponentIndex> func = null;

    switch (errorCode) {

    case MULTI_20901:
    {
      // A SpeciesTypeComponentIndex object may have the optional SBML Level 3 Core attributes metaid and
      // sboTerm. No other attributes from the SBML Level 3 Core namespace are permitted on a
      // SpeciesTypeComponentIndex object
      func = new UnknownCoreAttributeValidationFunction<SpeciesTypeComponentIndex>();
      break;
    }
    case MULTI_20902:
    {
      // A SpeciesTypeComponentIndex object may have the optional SBML Level 3 Core subobjects for notes
      // and annotation. No other elements from the SBML Level 3 Core namespace are permitted
      // on a SpeciesTypeComponentIndex object
      func = new UnknownCoreElementValidationFunction<SpeciesTypeComponentIndex>();
      break;
    }
    case MULTI_20903:
    {
      func = new ValidationFunction<SpeciesTypeComponentIndex>() {
        @Override
        public boolean check(ValidationContext ctx, SpeciesTypeComponentIndex stci) {

          // A SpeciesTypeComponentIndex object must have the required attributes id and
          // component, and may have the optional attributes name and identifyingParent.
          // No other attributes from the Multi namespace are permitted on a SpeciesTypeComponentIndex object.
          if (!stci.isSetId() || !stci.isSetComponent()) {
            return false;
          }
          
          return new UnknownPackageAttributeValidationFunction<SpeciesTypeComponentIndex>(MultiConstants.shortLabel).check(ctx, stci);
        }
      };
      break;
    }
    case MULTI_20904:
    {
      func = new ValidationFunction<SpeciesTypeComponentIndex>() {
        @Override
        public boolean check(ValidationContext ctx, SpeciesTypeComponentIndex stci) {
          
          // The value of the component attribute on a given SpeciesTypeComponentIndex object
          // must be the identifier of a SpeciesTypeInstance object, or a SpeciesTypeComponentIndex
          // object under the SpeciesType object that this SpeciesTypeComponentIndex object belongs
          // to, or the SpeciesType object itself
          // TODO
          return true;
        }
      };
      break;
    }
    case MULTI_20907:
    {
      func = new ValidationFunction<SpeciesTypeComponentIndex>() {
        @Override
        public boolean check(ValidationContext ctx, SpeciesTypeComponentIndex stci) {
          
          // The value of the identifyingParent attribute on a given SpeciesTypeComponentIndex
          // object must be the identifier of a component object under the SpeciesType object
          // that this SpeciesTypeComponentIndex object belongs to. A component object can be an
          //object of SpeciesTypeInstance, SpeciesTypeComponentIndex or SpeciesType
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
