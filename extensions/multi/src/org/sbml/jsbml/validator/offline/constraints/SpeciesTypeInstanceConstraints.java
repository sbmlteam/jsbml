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
import org.sbml.jsbml.ext.multi.SpeciesTypeInstance;
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
public class SpeciesTypeInstanceConstraints extends AbstractConstraintDeclaration{

  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category, ValidationContext context) {

    switch (category) {
    case GENERAL_CONSISTENCY:

      if (level >= 3)
      {
        addRangeToSet(set, MULTI_20801, MULTI_20806);
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
  public ValidationFunction<SpeciesTypeInstance> getValidationFunction(int errorCode, ValidationContext context) {
    ValidationFunction<SpeciesTypeInstance> func = null;

    switch (errorCode) {

    case MULTI_20801:
    {
      // A SpeciesTypeInstance object may have the optional SBML Level 3 Core attributes metaid and
      // sboTerm. No other attributes from the SBML Level 3 Core namespace are permitted on a
      // SpeciesTypeInstance object
      func = new UnknownCoreAttributeValidationFunction<SpeciesTypeInstance>();
      break;
    }
    case MULTI_20802:
    {
      // A SpeciesTypeInstance object may have the optional SBML Level 3 Core subobjects for notes
      // and annotation. No other elements from the SBML Level 3 Core namespace are permitted
      // on a SpeciesTypeInstance object
      func = new UnknownCoreElementValidationFunction<SpeciesTypeInstance>();
      break;
    }
    case MULTI_20803:
    {
      func = new ValidationFunction<SpeciesTypeInstance>() {
        @Override
        public boolean check(ValidationContext ctx, SpeciesTypeInstance sti) {

          // A SpeciesTypeInstance object must have the required attributes id and speciesType,
          // and may have the optional attributes name and compartmentReference.
          // No other attributes from the Multi namespace are permitted on a SpeciesTypeInstance object
          if (!sti.isSetId() || !sti.isSetSpeciesType()) {
            return false;
          }
          
          return new UnknownPackageAttributeValidationFunction<SpeciesTypeInstance>(MultiConstants.shortLabel).check(ctx, sti);
        }
      };
      break;
    }
    case MULTI_20805:
    {
      func = new ValidationFunction<SpeciesTypeInstance>() {
        @Override
        public boolean check(ValidationContext ctx, SpeciesTypeInstance sti) {

          // The value of the speciesType attribute on a given SpeciesTypeInstance object must
          // be the identifier of a SpeciesType object defined in the same Model object.
          // TODO
          return true;
        }
      };
      break;
    }
    case MULTI_20806:
    {
      func = new ValidationFunction<SpeciesTypeInstance>() {
        @Override
        public boolean check(ValidationContext ctx, SpeciesTypeInstance sti) {

          // The value of the compartmentReference attribute, if present on a given SpeciesTypeInstance object,
          // must be the identifier of a CompartmentReference object defined in the same Model object.
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
