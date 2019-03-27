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

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.comp.CompConstants;
import org.sbml.jsbml.ext.comp.CompSBMLDocumentPlugin;
import org.sbml.jsbml.ext.comp.Deletion;
import org.sbml.jsbml.ext.comp.ExternalModelDefinition;
import org.sbml.jsbml.ext.comp.ModelDefinition;
import org.sbml.jsbml.ext.comp.Submodel;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.SyntaxChecker;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.DuplicatedElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the {@link Submodel} class.
 *  
 * @author rodrigue
 * @since 1.5
 */
public class SubmodelConstraints extends AbstractConstraintDeclaration {

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

      addRangeToSet(set, COMP_20601, COMP_20608 );
      addRangeToSet(set, COMP_20613, COMP_20617 );
      addRangeToSet(set, COMP_20622, COMP_20623 );
      
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
    ValidationFunction<Submodel> func = null;

    switch (errorCode) {

    case COMP_20601:
    {
      func = new UnknownCoreAttributeValidationFunction<Submodel>();
      break;
    }
    case COMP_20602:
    {
      func = new UnknownCoreElementValidationFunction<Submodel>();
      break;
    }
    case COMP_20603:
    {
      // There may be at most one ListOfDeletions container object within a Submodel object
      func = new DuplicatedElementValidationFunction<Submodel>(CompConstants.listOfDeletions);
      break;
    }
    case COMP_20604:
    {
      func = new ValidationFunction<Submodel>() {

        @Override
        public boolean check(ValidationContext ctx, Submodel subM) {
          
          if (subM.isSetListOfDeletions()) {
            return subM.getDeletionCount() > 0;
          }
          
          return true;
        }
      };
      break;
    }
    case COMP_20605:
    {
      func = new ValidationFunction<Submodel>() {

        @Override
        public boolean check(ValidationContext ctx, Submodel subM) {
          
          if (subM.isSetListOfDeletions()) {
            return new UnknownElementValidationFunction<ListOf<Deletion>>().check(ctx, subM.getListOfDeletions());
          }
          
          return true;
        }
      };
      break;
    }
    case COMP_20606:
    {
      func = new ValidationFunction<Submodel>() {

        @Override
        public boolean check(ValidationContext ctx, Submodel subM) {
          
          if (subM.isSetListOfDeletions()) {
            return new UnknownAttributeValidationFunction<ListOf<Deletion>>().check(ctx, subM.getListOfDeletions());
          }
          
          return true;
        }
      };
      break;
    }
    case COMP_20607:
    {
      func = new ValidationFunction<Submodel>() {

        @Override
        public boolean check(ValidationContext ctx, Submodel subM) {
          
          if (!subM.isSetId() && !subM.isSetModelRef()) {
            return false;
          }
          
          return new UnknownPackageAttributeValidationFunction<Submodel>(CompConstants.shortLabel).check(ctx, subM);
        }
      };
      break;
    }
    case COMP_20608:
    {
      func = new ValidationFunction<Submodel>() {

        @Override
        public boolean check(ValidationContext ctx, Submodel subM) {
          
          if (subM.isSetModelRef()) {
            return SyntaxChecker.isValidId(subM.getModelRef(), ctx.getLevel(), ctx.getVersion());
          }
          
          return true;
        }
      };
      break;
    }
    case COMP_20613:
    {
      func = new ValidationFunction<Submodel>() {

        @Override
        public boolean check(ValidationContext ctx, Submodel subM) {
          
          if (subM.isSetTimeConversionFactor()) {
            return SyntaxChecker.isValidId(subM.getTimeConversionFactor(), ctx.getLevel(), ctx.getVersion());
          }
          
          return true;
        }
      };
      break;
    }
    case COMP_20614:
    {
      func = new ValidationFunction<Submodel>() {

        @Override
        public boolean check(ValidationContext ctx, Submodel subM) {
          
          if (subM.isSetExtentConversionFactor()) {
            return SyntaxChecker.isValidId(subM.getExtentConversionFactor(), ctx.getLevel(), ctx.getVersion());
          }
          
          return true;
        }
      };
      break;
    }
    case COMP_20615:
    {
      func = new ValidationFunction<Submodel>() {

        @Override
        public boolean check(ValidationContext ctx, Submodel subM) {
          
          if (subM.isSetModelRef()) {
            SBMLDocument doc = subM.getSBMLDocument();

            if (subM.getModelRef().equals(doc.getModel().getId())) {
              return true;
            }

            if (doc != null && doc.isSetPlugin(CompConstants.shortLabel)) {
              CompSBMLDocumentPlugin compDoc = (CompSBMLDocumentPlugin) doc.getPlugin(CompConstants.shortLabel);

              ModelDefinition md = compDoc.getModelDefinition(subM.getModelRef());
              ExternalModelDefinition emd = compDoc.getExternalModelDefinition(subM.getModelRef()); 

              if (md != null || emd != null) {
                return true;
              } else {
                return false;
              }
            }
          }

          return true;
        }
      };
      break;
    }
    case COMP_20616:
    {
      func = new ValidationFunction<Submodel>() {

        @Override
        public boolean check(ValidationContext ctx, Submodel subM) {
          
          if (subM.isSetModelRef()) {
            Model m = subM.getModel();

            if (m != null && subM.getModelRef().equals(m.getId())) {
              return false;
            }
          }

          return true;
        }
      };
      break;
    }
    case COMP_20617:
    {
      // A Model object must not contain a Submodel which references that Model indirectly. That is,
      // the modelRef attribute of a Submodel may not point to the id of a Model containing
      // a Submodel object that references the original Model directly or indirectly through a chain
      // of Model/Submodel pairs.
      // TODO
      break;
    }
    case COMP_20622:
    {
      func = new ValidationFunction<Submodel>() {

        @Override
        public boolean check(ValidationContext ctx, Submodel subM) {
          
          if (subM.isSetTimeConversionFactor()) {
            Model m = subM.getModel();

            if (m != null) {
              SBase sbase = m.getSBaseById(subM.getTimeConversionFactor());
              
              return sbase != null && sbase instanceof Parameter;
            }
          }

          return true;
        }
      };
      break;
    }
    case COMP_20623:
    {
      func = new ValidationFunction<Submodel>() {

        @Override
        public boolean check(ValidationContext ctx, Submodel subM) {
          
          if (subM.isSetExtentConversionFactor()) {
            Model m = subM.getModel();

            if (m != null) {
              SBase sbase = m.getSBaseById(subM.getExtentConversionFactor());
              
              return sbase != null && sbase instanceof Parameter;
            }
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
