/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * 
 * Copyright (C) 2009-2019 jointly by the following organizations:
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

import org.sbml.jsbml.ext.comp.SBaseRef;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the {@link SBaseRef} class.
 *  
 * @author rodrigue
 * @since 1.5
 */
public class SBaseRefConstraints extends AbstractConstraintDeclaration {

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

      addRangeToSet(set, COMP_20701, COMP_20714 );
      
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
    ValidationFunction<SBaseRef> func = null;

    switch (errorCode) {

    case COMP_20701: // portRef
    {
      // TODO
      break;
    }
    case COMP_20702: // idRef
    {
      // TODO
      break;
    }
    case COMP_20703: // unitRef
    {
      // TODO
      break;
    }
    case COMP_20704: // metaidRef
    {
      // TODO
      break;
    }
    case COMP_20705: // metaidRef
    {
      // If an SBaseRef object contains an SBaseRef child, the parent SBaseRef must point to a
      // Submodel object, or a Port that itself points to a Submodel object.
      // TODO
      break;
    }
    case COMP_20706: // portRef syntax
    {
      // TODO
      break;
    }
    case COMP_20707: // idRef syntax
    {
      // TODO
      break;
    }
    case COMP_20708: // unitRef syntax
    {
      // TODO
      break;
    }
    case COMP_20709: // metaidRef syntax
    {
      // TODO
      break;
    }
    case COMP_20710: // no other elements than notes, annotation and SBaseRef 
    {
      // TODO
      func = new UnknownCoreElementValidationFunction<SBaseRef>();
      break;
    }
    // 20711 ?? The 'sbaseRef' spelling of an SBaseRef child of an SBaseRef object is considered deprecated, and 'sBaseRef' should be used instead.
    case COMP_20712: // must always have a value for one of the attributes portRef, idRef, unitRef, or metaIdRef. 
    {
      // TODO
      break;
    }
    case COMP_20713: // can only have a value for one of the attributes portRef, idRef, unitRef, or metaIdRef. 
    {
      // No other attributes from the HierarchicalModel Composition namespace are permitted on an SBaseRef object
      // TODO
      break;
    }
    case COMP_20714: 
    {
      // 20714 - Any one SBML object may only be referenced in one of the following ways: referenced by a single
      // Port object; referenced by a single Deletion object; referenced by a single ReplacedElement;
      // be the parent of a single ReplacedBy child; be referenced by one ormore ReplacedBy objects;
      // or be referenced by one or more ReplacedElement objects all using the deletion attribute.
      // Essentially, once an object has been referenced in one of these ways it cannot be referenced
      // again.
      // TODO
      break;
    }
    }

    return func;
  }
}
