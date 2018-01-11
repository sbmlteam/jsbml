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

import java.util.HashSet;
import java.util.Set;

import org.sbml.jsbml.Model;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.ext.fbc.FBCConstants;
import org.sbml.jsbml.ext.fbc.FBCModelPlugin;
import org.sbml.jsbml.ext.fbc.GeneProduct;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;;

/**
 * @author Nicolas Rodriguez, Thomas M Hamm
 * @since 1.3
 */
public class GeneProductConstraints extends AbstractConstraintDeclaration {

  public static final String JSBML_DUPLICATED_GENE_PRODUCT_SET = "jsbml.validator.fbc.duplicated.geneProduct";
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.validator.offline.constraints.ConstraintDeclaration#addErrorCodesForAttribute(java.util.Set, int, int, java.lang.String)
   */
  @Override
  public void addErrorCodesForAttribute(Set<Integer> set, int level,
    int version, String attributeName, ValidationContext context) {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.validator.offline.constraints.ConstraintDeclaration#addErrorCodesForCheck(java.util.Set, int, int, org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY)
   */
  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category, ValidationContext context) {

    switch (category) {
    case GENERAL_CONSISTENCY:
      set.add(LAYOUT_20315);
      addRangeToSet(set, FBC_21201, FBC_21207);
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
  @SuppressWarnings("deprecation")
  public ValidationFunction<?> getValidationFunction(int errorCode, ValidationContext context) {
    ValidationFunction<GeneProduct> func = null;

    switch (errorCode) {
    case FBC_21201:
      func = new UnknownCoreAttributeValidationFunction<GeneProduct>();
      break;
    case FBC_21202:
      func = new UnknownCoreElementValidationFunction<GeneProduct>();
      break;
    case FBC_21203:
      func = new UnknownPackageAttributeValidationFunction<GeneProduct>(FBCConstants.shortLabel){
        
        @Override
        public boolean check(ValidationContext ctx, GeneProduct g) {
          // fbc:id and fbc:label are mandatory attributes
          if (!g.isSetId() || !g.isSetLabel()) {
            return false;
          }
          return super.check(ctx, g);
        }
      };
      break;
    case FBC_21204:
      func = new ValidationFunction<GeneProduct>() {

        @Override
        public boolean check(ValidationContext ctx, GeneProduct g) {
          // JSBML can read any type of string. That is why we don't need to validate this rule. 
          return true;
        }
      };
      break;
    case FBC_21205:
      func = new ValidationFunction<GeneProduct>() {

        @Override
        public boolean check(ValidationContext ctx, GeneProduct g) {

          Model m = g.getModel();

          if (m != null && m.isSetPlugin("fbc")) {
            FBCModelPlugin fbcModel = (FBCModelPlugin) m.getPlugin("fbc");
            Set<String> fbcDuplicatedGeneProductLabelSet = null;


            if (fbcModel.getUserObject(JSBML_DUPLICATED_GENE_PRODUCT_SET) == null) {
              fbcDuplicatedGeneProductLabelSet = new HashSet<String>();
              fbcModel.putUserObject(JSBML_DUPLICATED_GENE_PRODUCT_SET, fbcDuplicatedGeneProductLabelSet);
              checkFbcLabelGeneProductUnique(fbcModel, fbcDuplicatedGeneProductLabelSet);
            }
            else 
            {
              fbcDuplicatedGeneProductLabelSet = (Set<String>) fbcModel.getUserObject(JSBML_DUPLICATED_GENE_PRODUCT_SET);
            }

            boolean isUnique = fbcDuplicatedGeneProductLabelSet.contains(g.getLabel());
            return !isUnique;
          }

          return true;
        }
      };
      break;
    case FBC_21206:
      func = new ValidationFunction<GeneProduct>() {

        @Override
        public boolean check(ValidationContext ctx, GeneProduct g) {
          // JSBML can read any type of string. That is why we don't need to validate this rule. 
          return true;
        }
      };
      break;
    case FBC_21207:
      func = new ValidationFunction<GeneProduct>() {

        @Override
        public boolean check(ValidationContext ctx, GeneProduct g) {
          
          if (g.isSetAssociatedSpecies()) {
            String speciesId = g.getAssociatedSpecies();
            Model m = g.getModel();
            Species species = m.getSpecies(speciesId);
            if ( species== null){
             return false;
            }
          }
          return true;
          
        }
      };
      break;
    
    }

    return func;
  }

  public void checkFbcLabelGeneProductUnique(FBCModelPlugin fbcModel, Set<String> fbcDuplicatedGeneProductLabelSet){
    HashSet<String> fbcLabelsGeneProductSet = new HashSet<String>();

    if (fbcModel != null) {

      if (fbcModel.getGeneProductCount() > 0) {
        for (GeneProduct g : fbcModel.getListOfGeneProducts()) {

          boolean isUnique = fbcLabelsGeneProductSet.add(g.getLabel());
          if (!isUnique) {
            fbcDuplicatedGeneProductLabelSet.add(g.getLabel());
          }
        }
      }
    }
  }

}
