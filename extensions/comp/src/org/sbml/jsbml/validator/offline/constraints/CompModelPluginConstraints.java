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
import org.sbml.jsbml.ext.comp.CompConstants;
import org.sbml.jsbml.ext.comp.CompModelPlugin;
import org.sbml.jsbml.ext.comp.Port;
import org.sbml.jsbml.ext.comp.Submodel;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.DuplicatedElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownElementValidationFunction;;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the {@link CompModelPlugin} class.
 *  
 * @author rodrigue
 * @since 1.5
 */
public class CompModelPluginConstraints extends AbstractConstraintDeclaration {

  /* (non-Javadoc)
   * @see org.sbml.jsbml.validator.offline.constraints.ConstraintDeclaration#addErrorCodesForAttribute(java.util.Set, int, int, java.lang.String)
   */
  @Override
  public void addErrorCodesForAttribute(Set<Integer> set, int level,
    int version, String attributeName, ValidationContext context) 
  {
    // no specific attribute, so nothing to do.

  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.validator.offline.constraints.ConstraintDeclaration#addErrorCodesForCheck(java.util.Set, int, int, org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY)
   */
  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category, ValidationContext context) {

    switch (category) {
    case GENERAL_CONSISTENCY:

      addRangeToSet(set, COMP_20501, COMP_20506 );
      
      set.add(COMP_20804);
      set.add(COMP_21010);
      
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
    ValidationFunction<CompModelPlugin> func = null;

    switch (errorCode) {

    case COMP_20501:
    {
      func = new ValidationFunction<CompModelPlugin>() {

        @Override
        public boolean check(ValidationContext ctx, CompModelPlugin compM) {

          // There may be at most one ListOfPorts and listOfSubmodels container object within a CompModelPlugin object
          return new DuplicatedElementValidationFunction<CompModelPlugin>(CompConstants.listOfSubmodels).check(ctx, compM)
              && new DuplicatedElementValidationFunction<CompModelPlugin>(CompConstants.listOfPorts).check(ctx, compM);
        }
      };
      break;
    }
    case COMP_20502:
    {
      func = new ValidationFunction<CompModelPlugin>() {

        @Override
        public boolean check(ValidationContext ctx, CompModelPlugin compM) {
          boolean isValid = true;
          
          if (compM.isSetListOfSubmodels()) {
            isValid = compM.getSubmodelCount() > 0;
          }
          if (compM.isSetListOfPorts()) {
            isValid = isValid && compM.getPortCount() > 0;
          }
          
          return isValid;
        }
      };
      break;
    }
    case COMP_20503:
    {
      func = new ValidationFunction<CompModelPlugin>() {

        @Override
        public boolean check(ValidationContext ctx, CompModelPlugin subM) {
          
          if (subM.isSetListOfSubmodels()) {
            return new UnknownElementValidationFunction<ListOf<Submodel>>().check(ctx, subM.getListOfSubmodels());
          }
          
          return true;
        }
      };
      break;
    }
    case COMP_20504:
    {
      func = new ValidationFunction<CompModelPlugin>() {

        @Override
        public boolean check(ValidationContext ctx, CompModelPlugin subM) {
          
          if (subM.isSetListOfPorts()) {
            return new UnknownElementValidationFunction<ListOf<Port>>().check(ctx, subM.getListOfPorts());
          }
          
          return true;
        }
      };
      break;
    }
    case COMP_20505:
    {
      func = new ValidationFunction<CompModelPlugin>() {

        @Override
        public boolean check(ValidationContext ctx, CompModelPlugin subM) {
          
          // TODO - check if the ListOf are not cloned at some point before the check 
          
          if (subM.isSetListOfSubmodels()) {
            return new UnknownAttributeValidationFunction<ListOf<Submodel>>().check(ctx, subM.getListOfSubmodels());
          }
          
          return true;
        }
      };
      break;
    }
    case COMP_20506:
    {
      func = new ValidationFunction<CompModelPlugin>() {

        @Override
        public boolean check(ValidationContext ctx, CompModelPlugin subM) {
          
          if (subM.isSetListOfPorts()) {
            return new UnknownAttributeValidationFunction<ListOf<Port>>().check(ctx, subM.getListOfPorts());
          }
          
          return true;
        }
      };
      break;
    }
    case COMP_20804:
    {
      // TODO
      // No two Port objects in the same Model may reference the same object
      
      break;
    }
    case COMP_21010:
    {
      // TODO
      // No two ReplacedElement objects in the same Model may reference the same object unless that
      // object is a Deletion
      
      break;
    }
    }

    return func;
  }
}
