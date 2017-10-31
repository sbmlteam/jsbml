/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * 
 * Copyright (C) 2009-2017 jointly by the following organizations:
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

import org.sbml.jsbml.ext.qual.QualConstants;
import org.sbml.jsbml.ext.qual.QualitativeSpecies;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.InvalidAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;;

/**
 * @author Nicolas Rodriguez, Lisa Falk
 * @since 1.3
 */
public class QualitativeSpeciesConstraints extends AbstractConstraintDeclaration {

  /* (non-Javadoc)
   * @see org.sbml.jsbml.validator.offline.constraints.ConstraintDeclaration#addErrorCodesForAttribute(java.util.Set, int, int, java.lang.String)
   */
  @Override
  public void addErrorCodesForAttribute(Set<Integer> set, int level,
    int version, String attributeName, ValidationContext context) {
    // TODO 

  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.validator.offline.constraints.ConstraintDeclaration#addErrorCodesForCheck(java.util.Set, int, int, org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY)
   */
  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category, ValidationContext context) {

    switch (category) {
    case GENERAL_CONSISTENCY:

      addRangeToSet(set, QUAL_20301, QUAL_20313); // TODO - not sure all rules should go into that category
      
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
    ValidationFunction<QualitativeSpecies> func = null;

    switch (errorCode) {
    
      case QUAL_20301: {
        func = new UnknownCoreAttributeValidationFunction<QualitativeSpecies>();
        break;
      }
      case QUAL_20302: {
        // TODO - for the rules about unknown elements to work, the QualParser need to be modified to register those unknown elements if necessary
        func = new UnknownCoreElementValidationFunction<QualitativeSpecies>();
        break;
      }

      case QUAL_20303: {
        func = new UnknownPackageAttributeValidationFunction<QualitativeSpecies>(QualConstants.shortLabel) {

          /* (non-Javadoc)
           * @see org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction#check(org.sbml.jsbml.validator.offline.ValidationContext, org.sbml.jsbml.util.TreeNodeWithChangeSupport)
           */
          @Override
          public boolean check(ValidationContext ctx, QualitativeSpecies t) {
            // id, compartment and constant are required
            if (!t.isSetConstant() || !t.isSetCompartment() || !t.isSetId()) {
              return false;
            }
            
            return super.check(ctx, t);
          }
          
          
        };
        break;
      }

      case QUAL_20304: {
        func = new InvalidAttributeValidationFunction<QualitativeSpecies>(QualConstants.constant);
        break;
      }
    }

    return func;
  }

}
