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

import org.sbml.jsbml.JSBML;
import org.sbml.jsbml.ext.render.GradientBase;
import org.sbml.jsbml.ext.render.RenderConstants;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageElementValidationFunction;
import org.sbml.jsbml.xml.XMLNode;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the
 * {@link GradientBase} class.
 * 
 * @author David Emanuel Vetter
 */
public class GradientBaseConstraints extends AbstractConstraintDeclaration {

  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category, ValidationContext context) {
    switch(category) {
    case GENERAL_CONSISTENCY:
      addRangeToSet(set, RENDER_20901, RENDER_20906);
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
    ValidationFunction<GradientBase> func = null;
    switch(errorCode) {
    case RENDER_20901:
      func = new UnknownCoreAttributeValidationFunction<GradientBase>();
      break;
    case RENDER_20902:
      func = new UnknownCoreElementValidationFunction<GradientBase>();
      break;
    case RENDER_20903:
      func = new UnknownPackageAttributeValidationFunction<GradientBase>(RenderConstants.shortLabel) {
        @Override
        public boolean check(ValidationContext ctx, GradientBase base) {
          return super.check(ctx, base) && base.isSetId();
        }
      };
      break;
    case RENDER_20904:
      func = new UnknownPackageElementValidationFunction<GradientBase>(RenderConstants.shortLabel) {
        @Override
        public boolean check(ValidationContext ctx, GradientBase base) {
          boolean hasGradientStops = base.isSetListOfGradientStops()
            && !base.getListOfGradientStops().isEmpty();
          return hasGradientStops && super.check(ctx, base);
        }
      };
      break;
    case RENDER_20905:
      func = new ValidationFunction<GradientBase>() {
        @Override
        public boolean check(ValidationContext ctx, GradientBase t) {
          // Optional 'name' is a String: nothing to check.
          return true;
        }
      };
      break;
    case RENDER_20906:
      func = new ValidationFunction<GradientBase>() {

        @Override
        public boolean check(ValidationContext ctx, GradientBase base) {
          if(base.getUserObject(JSBML.UNKNOWN_XML) != null) {
            XMLNode unknown = (XMLNode) base.getUserObject(JSBML.UNKNOWN_XML);
            return unknown.getAttrIndex(RenderConstants.spreadMethod) == -1;
            // TODO 2020/03: does this work?
          }
          return true;
        }
        
      };
      break;
    }
    return func;
  }
}
