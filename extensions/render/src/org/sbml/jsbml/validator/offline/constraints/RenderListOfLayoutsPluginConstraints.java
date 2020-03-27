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

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.ext.render.ListOfGlobalRenderInformation;
import org.sbml.jsbml.ext.render.RenderConstants;
import org.sbml.jsbml.ext.render.RenderListOfLayoutsPlugin;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.DuplicatedElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.InvalidAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageElementValidationFunction;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the
 * {@link RenderListOfLayoutsPlugin} class.
 * 
 * @author David Emanuel Vetter
 */
public class RenderListOfLayoutsPluginConstraints
  extends AbstractConstraintDeclaration {

  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category, ValidationContext context) {
    switch(category) {
    case GENERAL_CONSISTENCY:
      addRangeToSet(set, RENDER_20401, RENDER_20407);
      break;
    default:
      break;
    }
  }


  @Override
  public void addErrorCodesForAttribute(Set<Integer> set, int level,
    int version, String attributeName, ValidationContext context) {
    // TODO Auto-generated method stub
  }


  @Override
  public ValidationFunction<?> getValidationFunction(int errorCode,
    ValidationContext context) {
    ValidationFunction<RenderListOfLayoutsPlugin> func = null;
    switch(errorCode) {
    case RENDER_20401:
      func = new ValidationFunction<RenderListOfLayoutsPlugin>() {

        @Override
        public boolean check(ValidationContext ctx,
          RenderListOfLayoutsPlugin rlp) {
          // Coupling: RenderListOfLayouts is known to extend a ListOf<Layout>
          @SuppressWarnings("unchecked") 
          ListOf<Layout> extendedList = (ListOf<Layout>) rlp.getExtendedSBase();
          return new DuplicatedElementValidationFunction<ListOf<Layout>>(
            RenderConstants.shortLabel + ":"
              + RenderConstants.listOfGlobalRenderInformation).check(ctx,
                extendedList)
            && new UnknownPackageElementValidationFunction<ListOf<Layout>>(
              RenderConstants.shortLabel).check(ctx, extendedList);
        }
      };
      break;
      
    case RENDER_20402:
      func = new ValidationFunction<RenderListOfLayoutsPlugin>() {

        @Override
        public boolean check(ValidationContext ctx, RenderListOfLayoutsPlugin t) {
          return !t.isListOfGlobalRenderInformationEmpty();
        }
      };
      break;
      
    case RENDER_20403:
      func = new ValidationFunction<RenderListOfLayoutsPlugin>() {

        @Override
        public boolean check(ValidationContext ctx, RenderListOfLayoutsPlugin rlp) {
          if(rlp.isSetListOfGlobalRenderInformation()) {
            return new UnknownElementValidationFunction<ListOfGlobalRenderInformation>().check(
              ctx, rlp.getListOfGlobalRenderInformation());
          }
          return true;
        }
      };
      break;
      
    case RENDER_20404:
      func = new ValidationFunction<RenderListOfLayoutsPlugin>() {

        @Override
        public boolean check(ValidationContext ctx, RenderListOfLayoutsPlugin rlp) {
          if(rlp.isSetListOfGlobalRenderInformation()) {
            return new UnknownCoreAttributeValidationFunction<ListOfGlobalRenderInformation>().check(
              ctx, rlp.getListOfGlobalRenderInformation());
          }
          return true;
        }
      };
      break;
      
    case RENDER_20405:
      func = new ValidationFunction<RenderListOfLayoutsPlugin>() {

        @Override
        public boolean check(ValidationContext ctx, RenderListOfLayoutsPlugin rlp) {
          if(rlp.isSetListOfGlobalRenderInformation()) {
            return new UnknownPackageAttributeValidationFunction<ListOfGlobalRenderInformation>(
              RenderConstants.shortLabel).check(ctx,
                rlp.getListOfGlobalRenderInformation());
          }
          return true;
        }
      };
      break;
      
    case RENDER_20406:
      func=new ValidationFunction<RenderListOfLayoutsPlugin>() {

        @Override
        public boolean check(ValidationContext ctx, RenderListOfLayoutsPlugin rlp) {

          if (rlp.isSetListOfGlobalRenderInformation()) {
            ListOfGlobalRenderInformation gris = rlp.getListOfGlobalRenderInformation();
            if (gris.isSetVersionMajor()) {
              return gris.getVersionMajor() >= 0;
            } else {
              return new InvalidAttributeValidationFunction<ListOfGlobalRenderInformation>(
                  RenderConstants.versionMajor).check(ctx, gris); 
            }
          }
                    
          return true;
        }
      };
      break;
      
    case RENDER_20407:
      func=new ValidationFunction<RenderListOfLayoutsPlugin>() {

        @Override
        public boolean check(ValidationContext ctx, RenderListOfLayoutsPlugin rlp) {

          if (rlp.isSetListOfGlobalRenderInformation()) {
            ListOfGlobalRenderInformation gris = rlp.getListOfGlobalRenderInformation();
            if (gris.isSetVersionMinor()) {
              return gris.getVersionMinor() >= 0;
            } else {
              return new InvalidAttributeValidationFunction<ListOfGlobalRenderInformation>(
                  RenderConstants.versionMinor).check(ctx, gris); 
            }
          }
                    
          return true;
        }
      };
      break;    
    }
    
    return func;
  }
}
