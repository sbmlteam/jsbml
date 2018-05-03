/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2018 jointly by the following organizations:
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

import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.multi.CompartmentReference;
import org.sbml.jsbml.ext.multi.MultiCompartmentPlugin;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownElementValidationFunction;

/**
 * Constraints declaration for the {@link MultiCompartmentPlugin} class.
 * 
 * @author Nicolas Rodriguez
 * @since 1.2
 */
public class CompartmentReferenceConstraints extends AbstractConstraintDeclaration{

  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category, ValidationContext context) {

    switch (category) {
    case GENERAL_CONSISTENCY:

      if (level >= 3)
      {
        set.add(MULTI_20204);
        addRangeToSet(set, MULTI_20301, MULTI_20304);
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
  public ValidationFunction<CompartmentReference> getValidationFunction(int errorCode, ValidationContext context) {
    ValidationFunction<CompartmentReference> func = null;

    switch (errorCode) {

      case MULTI_20204:
      {
        func = new ValidationFunction<CompartmentReference>() {
          @Override
          public boolean check(ValidationContext ctx, CompartmentReference cr) {

            Compartment c_ref = cr.getCompartmentInstance();
            MultiCompartmentPlugin c_refp = c_ref != null ? (MultiCompartmentPlugin) c_ref.getPlugin("multi") : null;
            MultiCompartmentPlugin c_parent = (MultiCompartmentPlugin) ((SBase) cr.getParent().getParent()).getPlugin("multi");

            if (c_refp == null) {
              // something is wrong with the model, we cannot validate this constraint
              return true;
            }
            
            boolean valid = c_refp.isSetIsType() == c_parent.isSetIsType();

            if (valid && c_refp.isSetIsType()) {
              valid = c_refp.isType() == c_parent.isType();
            }

            return valid;
          }
        };
        break;
      }    
      case MULTI_20301:
      {
        func = new UnknownAttributeValidationFunction<CompartmentReference>(); // TODO - report only core attributes
        break;
      }
      case MULTI_20302:
      {
        func = new UnknownElementValidationFunction<CompartmentReference>();
        break;
      }
      case MULTI_20303:
      {
        // TODO - report only multi attributes ?
        func = new UnknownAttributeValidationFunction<CompartmentReference>() {
          @Override
          public boolean check(ValidationContext ctx, CompartmentReference cr) {

            if (!cr.isSetCompartment())
            {
              return false;
            }

            return super.check(ctx, cr);
          }
        };
        break;
      }
      case MULTI_20304:
      {
        func = new ValidationFunction<CompartmentReference>() {
          @Override
          public boolean check(ValidationContext ctx, CompartmentReference cr) {

            if (cr.isSetCompartment())
            {
              return cr.getCompartmentInstance() != null;
            }

            return true;
          }
        };
        break;
      }    
      // case MULTI_20205: defined in MultiCompartmentPluginConstraints
    }

    return func;
  }
}
