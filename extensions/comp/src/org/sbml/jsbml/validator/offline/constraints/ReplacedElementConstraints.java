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

import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.comp.CompConstants;
import org.sbml.jsbml.ext.comp.CompModelPlugin;
import org.sbml.jsbml.ext.comp.Deletion;
import org.sbml.jsbml.ext.comp.ReplacedElement;
import org.sbml.jsbml.ext.comp.Submodel;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the {@link ReplacedElement} class.
 *  
 * @author rodrigue
 * @since 1.5
 */
public class ReplacedElementConstraints extends AbstractConstraintDeclaration {

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

      addRangeToSet(set, COMP_21001, COMP_21006 );
      
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
    ValidationFunction<ReplacedElement> func = null;

    switch (errorCode) {

    case COMP_21001:
    {
      // must have a value for one of the following attributes: portRef, idRef, unitRef, metaIdRef, or deletion.
      func = new ValidationFunction<ReplacedElement>() {

        @Override
        public boolean check(ValidationContext ctx, ReplacedElement replEl) {
          
          return replEl.isSetPortRef() || replEl.isSetIdRef() || replEl.isSetUnitRef() 
              || replEl.isSetMetaIdRef() || replEl.isSetDeletion();
        }
      };
      break;
    }
    case COMP_21002:
    {
      // can only have a value for one of the following attributes: portRef, idRef, unitRef, metaIdRef or deletion.
      func = new ValidationFunction<ReplacedElement>() {

        @Override
        public boolean check(ValidationContext ctx, ReplacedElement replEl) {
          int nbDefined = 0;
          
          if (replEl.isSetPortRef()) {
            nbDefined++;
          }
          if (replEl.isSetIdRef()) {
            nbDefined++;
          }
          if (replEl.isSetUnitRef()) {
            nbDefined++;
          }
          if (replEl.isSetMetaIdRef()) {
            nbDefined++;
          }
          if (replEl.isSetDeletion()) {
            nbDefined++;
          }
          
          return nbDefined <= 1;
        }
      };
      break;
    }
    case COMP_21003:
    {
      func = new ValidationFunction<ReplacedElement>() {

        @Override
        public boolean check(ValidationContext ctx, ReplacedElement replEl) {
          // must have a value for the required attribute submodelRef
          if (!replEl.isSetSubmodelRef()) {
            return false;
          }

          // can only have a value for one and only one of the following attributes: portRef, idRef, unitRef, metaIdRef or deletion.
          int nbDefined = 0;
          
          if (replEl.isSetPortRef()) {
            nbDefined++;
          }
          if (replEl.isSetIdRef()) {
            nbDefined++;
          }
          if (replEl.isSetUnitRef()) {
            nbDefined++;
          }
          if (replEl.isSetMetaIdRef()) {
            nbDefined++;
          }
          if (replEl.isSetDeletion()) {
            nbDefined++;
          }
          
          // No other attributes from the HierarchicalModel Composition namespace are permitted on a ReplacedBy object.
          boolean otherAttributes = new UnknownPackageAttributeValidationFunction<ReplacedElement>(CompConstants.shortLabel).check(ctx, replEl);
          
          // TODO - custom error messages for each different issues?
          
          return nbDefined == 1 && otherAttributes;
        }
      };
      break;
    }
    case COMP_21004:
    {
      // The value of a submodelRef attribute on a ReplacedElement object must be the identifier of
      // a Submodel present in ReplacedElement object's parent Model
      func = new ValidationFunction<ReplacedElement>() {

        @Override
        public boolean check(ValidationContext ctx, ReplacedElement replEl) {
          
          if (replEl.isSetSubmodelRef()) {
            Model m = replEl.getModel();

            if (m != null && m.isSetPlugin(CompConstants.shortLabel)) {
              CompModelPlugin compM = (CompModelPlugin) m.getPlugin(CompConstants.shortLabel);

              Submodel subModel = compM.getSubmodel(replEl.getSubmodelRef());
              
              return subModel != null;
            }
          }
          
          return true;
        }
      };
      break;
    }
    case COMP_21005: 
    {
      // The value of a deletion attribute on a ReplacedElement object must be the identifier of
      // a Deletion present in ReplacedElement object's parent Model
      func = new ValidationFunction<ReplacedElement>() {

        @Override
        public boolean check(ValidationContext ctx, ReplacedElement replEl) {
          
          if (replEl.isSetDeletion()) {
            Model m = replEl.getModel();

            if (m != null) {

              SBase deletion = m.getSBaseById(replEl.getDeletion());
              
              return deletion != null && deletion instanceof Deletion;
            }
          }
          
          return true;
        }
      };
      break;
    }
    case COMP_21006: 
    {
      // The value of a conversionFactor attribute on a ReplacedElement object must be the identifier of
      // a Parameter present in ReplacedElement object's parent Model
      func = new ValidationFunction<ReplacedElement>() {

        @Override
        public boolean check(ValidationContext ctx, ReplacedElement replEl) {
          
          if (replEl.isSetConversionFactor()) {
            Model m = replEl.getModel();

            if (m != null) {

              SBase parameter = m.getSBaseById(replEl.getConversionFactor());
              
              return parameter != null && parameter instanceof Parameter;
            }
          }
          
          return true;
        }
      };
      break;
    }
    // case COMP_21007: // removed
    // case COMP_21008: // removed
    // case COMP_21009: // removed
    // case COMP_21010: // implemented in the CompModelPluginConstraints
    case COMP_21011: // ?? 
    {
      // TODO
      
      break;
    }
    }

    return func;
  }
}
