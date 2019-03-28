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

import org.sbml.jsbml.ext.comp.CompConstants;
import org.sbml.jsbml.ext.comp.ExternalModelDefinition;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.SyntaxChecker;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the {@link ExternalModelDefinition} class.
 *  
 * @author rodrigue
 * @since 1.5
 */
public class ExternalModelDefinitionConstraints extends AbstractConstraintDeclaration {

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

      addRangeToSet(set, COMP_20301, COMP_20310 );
      
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
    ValidationFunction<ExternalModelDefinition> func = null;

    switch (errorCode) {

    case COMP_20301:
    {
      func = new UnknownCoreAttributeValidationFunction<ExternalModelDefinition>();
      break;
    }
    case COMP_20302:
    {
      func = new UnknownElementValidationFunction<ExternalModelDefinition>();
      break;
    }
    case COMP_20303:
    {
      func = new ValidationFunction<ExternalModelDefinition>() {

        @Override
        public boolean check(ValidationContext ctx, ExternalModelDefinition extM) {
          
          if (!extM.isSetId() || !extM.isSetSource()) {
            return false;
          }
          
          return new UnknownPackageAttributeValidationFunction<ExternalModelDefinition>(CompConstants.shortLabel).check(ctx, extM);
        }
      };
      break;
    }
    case COMP_20304:
    {
      // The value of the source attribute on an ExternalModelDefinition object must reference
      // an SBML Level 3 Version 1 document
      // TODO
      break;
    }
    case COMP_20305:
    {
      // The value of the modelRef attribute on an ExternalModelDefinition object must be the
      // value of an id attribute on a Model or ExternalModelDefinition object in the SBML document
      // referenced by the source attribute
      // TODO
      break;
    }
    case COMP_20306:
    {
      // TODO
      // The value of the md5 attribute, if present on an ExternalModelDefinition object, should
      // match the calculated MD5 checksum of the SBML document referenced by the source
      // attribute.
      break;
    }
    case COMP_20307:
    {
      // The value of a source attribute on an ExternalModelDefinition object must always conform to
      // the syntax of the XML Schema 1.0 data type anyURI.
      // TODO
      break;
    }
    case COMP_20308:
    {
      func = new ValidationFunction<ExternalModelDefinition>() {

        @Override
        public boolean check(ValidationContext ctx, ExternalModelDefinition extM) {
          
          if (extM.isSetModelRef()) {
            return SyntaxChecker.isValidId(extM.getModelRef(), ctx.getLevel(), ctx.getVersion());
          }
          
          return true;
        }
      };
      break;
    }
    case COMP_20309:      
    {
      // The value of a md5 attribute on an ExternalModelDefinition object must always conform to the syntax of type string
      // nothing to do.
      break;
    }
    case COMP_20310:
    {
      // An ExternalModelDefinition object must not reference an ExternalModelDefinition in a different
      // SBML document that, in turn, refers back to the original ExternalModelDefinition object,
      // whether directly or indirectly through a chain of ExternalModelDefinition objects.
      // TODO
      break;
    }
    }

    return func;
  }
}
