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
import org.sbml.jsbml.ext.comp.Port;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the {@link Port} class.
 *  
 * @author rodrigue
 * @since 1.5
 */
public class PortConstraints extends AbstractConstraintDeclaration {

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

      addRangeToSet(set, COMP_20801, COMP_20803 );
      
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
    ValidationFunction<Port> func = null;

    switch (errorCode) {

    case COMP_20801:
    {
      // must have a value for one of the following attributes: idRef, unitRef, or metaIdRef.
      func = new ValidationFunction<Port>() {

        @Override
        public boolean check(ValidationContext ctx, Port port) {
          
          return port.isSetIdRef() || port.isSetUnitRef() 
              || port.isSetMetaIdRef();
        }
      };
      break;
    }
    case COMP_20802:
    {
      // can only have a value for one of the following attributes: idRef, unitRef, or metaIdRef.
      func = new ValidationFunction<Port>() {

        @Override
        public boolean check(ValidationContext ctx, Port port) {
          int nbDefined = 0;
          
          if (port.isSetPortRef()) {
            nbDefined++;
            nbDefined++; // because portRef is invalid
          }
          if (port.isSetIdRef()) {
            nbDefined++;
          }
          if (port.isSetUnitRef()) {
            nbDefined++;
          }
          if (port.isSetMetaIdRef()) {
            nbDefined++;
          }
          
          return nbDefined <= 1;
        }
      };
      break;
    }
    case COMP_20803:
    {
      func = new ValidationFunction<Port>() {

        @Override
        public boolean check(ValidationContext ctx, Port port) {
          int nbDefined = 0;

          // must have a value for the required attribute id
          if (!port.isSetId()) {
            return false;
          }
          
          // can only have a value for one and only one of the following attributes: idRef, unitRef, or metaIdRef.          
          if (port.isSetPortRef()) {
            nbDefined++;
            nbDefined++; // because portRef is invalid
          }
          if (port.isSetIdRef()) {
            nbDefined++;
          }
          if (port.isSetUnitRef()) {
            nbDefined++;
          }
          if (port.isSetMetaIdRef()) {
            nbDefined++;
          }
          
          // No other attributes from the HierarchicalModel Composition namespace are permitted on a Port object.
          boolean otherAttributes = new UnknownPackageAttributeValidationFunction<Port>(CompConstants.shortLabel).check(ctx, port);
          
          // TODO - custom error messages for each different issues?
          
          return nbDefined == 1 && otherAttributes;
        }
      };      
      break;
    }
    // case COMP_20804: // implemented in the CompModelPluginConstraints 
    }

    return func;
  }
}
