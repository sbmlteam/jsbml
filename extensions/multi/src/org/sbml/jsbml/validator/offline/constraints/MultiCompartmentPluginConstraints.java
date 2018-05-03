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
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.ext.multi.CompartmentReference;
import org.sbml.jsbml.ext.multi.MultiCompartmentPlugin;
import org.sbml.jsbml.ext.multi.MultiConstants;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.DuplicatedElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownElementValidationFunction;

/**
 * Constraints declaration for the {@link MultiCompartmentPlugin} class.
 * 
 * @author Nicolas Rodriguez
 * @since 1.2
 */
public class MultiCompartmentPluginConstraints extends AbstractConstraintDeclaration{

  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category, ValidationContext context) {

    switch (category) {
    case GENERAL_CONSISTENCY:

      if (level >= 3)
      {
        addRangeToSet(set, MULTI_20201, MULTI_20203);
        addRangeToSet(set, MULTI_20205, MULTI_20209);
        set.add(MULTI_20305);
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
  public ValidationFunction<MultiCompartmentPlugin> getValidationFunction(int errorCode, ValidationContext context) {
    ValidationFunction<MultiCompartmentPlugin> func = null;

    switch (errorCode) {

    case MULTI_20201:
    {
      func = new UnknownAttributeValidationFunction<MultiCompartmentPlugin>() {
        @Override
        public boolean check(ValidationContext ctx, MultiCompartmentPlugin c) {

          if (!c.isSetIsType())
          {
            return false;
          }

          return super.check(ctx, c);
        }
      };
      break;
    }
    case MULTI_20202:
    {
      func = new ValidationFunction<MultiCompartmentPlugin>() {
        @Override
        public boolean check(ValidationContext ctx, MultiCompartmentPlugin c) {

          if (!c.isSetIsType())
          {
            // TODO - check if there is an invalid String for this attribute
          }

          return true;
        }
      };
      break;
    }
    case MULTI_20203:
    {
      func = new ValidationFunction<MultiCompartmentPlugin>() {
        @Override
        public boolean check(ValidationContext ctx, MultiCompartmentPlugin c) {

          if (!c.isSetIsType())
          {
            return false;
          }

          return true;
        }
      };
      break;
    }
//  case MULTI_20204: defined in CompartmentReferenceConstraints
    case MULTI_20205:
    {
      func = new ValidationFunction<MultiCompartmentPlugin>() {
        @Override
        public boolean check(ValidationContext ctx, MultiCompartmentPlugin c) {

          if (c.isSetIsType() && c.isType())
          {
            return !c.isSetCompartmentType();
          }

          return true;
        }
      };
      break;
    }
    case MULTI_20206:
    {
      func = new ValidationFunction<MultiCompartmentPlugin>() {
        @Override
        public boolean check(ValidationContext ctx, MultiCompartmentPlugin c) {

          ValidationFunction<Compartment> f = new DuplicatedElementValidationFunction<Compartment>(MultiConstants.listOfCompartmentReferences);
          
          return f.check(ctx, c.getParent());
        }
      };
      break;
    }
    case MULTI_20207:
    {
      func = new ValidationFunction<MultiCompartmentPlugin>() {
        @Override
        public boolean check(ValidationContext ctx, MultiCompartmentPlugin c) {
          
          if (c.isSetListOfCompartmentReferences()) {
            return c.getCompartmentReferenceCount() > 0;
          }
          
          return true;
        }
      };
      break;
    }
    case MULTI_20208:
    {
      func = new ValidationFunction<MultiCompartmentPlugin>() {
        @Override
        public boolean check(ValidationContext ctx, MultiCompartmentPlugin c) {

          if (c.isSetListOfCompartmentReferences()) {
            UnknownAttributeValidationFunction<ListOf<CompartmentReference>> unknownFunc = new UnknownAttributeValidationFunction<ListOf<CompartmentReference>>();
            return unknownFunc.check(ctx, c.getListOfCompartmentReferences());
          }
          
          return true;
        }
      };
      break;
    }
    case MULTI_20209:
    {
      func = new ValidationFunction<MultiCompartmentPlugin>() {
        @Override
        public boolean check(ValidationContext ctx, MultiCompartmentPlugin c) {
          
          if (c.isSetListOfCompartmentReferences()) {
            UnknownElementValidationFunction<ListOf<CompartmentReference>> unknownFunc = new UnknownElementValidationFunction<ListOf<CompartmentReference>>();
            return unknownFunc.check(ctx, c.getListOfCompartmentReferences());
          }
          
          return true;
        }
      };
      break;
    }    
    case MULTI_20305:
    {
      func = new ValidationFunction<MultiCompartmentPlugin>() {
        @Override
        public boolean check(ValidationContext ctx, MultiCompartmentPlugin c) {
          
          if (c.isSetListOfCompartmentReferences()) {
            
            // TODO - check if any CompartmentReference refer to the same Compartment. If yes, they must have an id.
            
            // TODO - report several errors if needed
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
