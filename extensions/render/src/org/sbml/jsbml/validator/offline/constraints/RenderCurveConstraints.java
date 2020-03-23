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
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.render.LineEnding;
import org.sbml.jsbml.ext.render.RenderConstants;
import org.sbml.jsbml.ext.render.RenderCubicBezier;
import org.sbml.jsbml.ext.render.RenderCurve;
import org.sbml.jsbml.ext.render.RenderPoint;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.DuplicatedElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageElementValidationFunction;


/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the
 * {@link RenderCurve} class.
 * 
 * @author David Emanuel Vetter
 */
public class RenderCurveConstraints extends AbstractConstraintDeclaration {

  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category, ValidationContext context) {
    switch(category) {
    case GENERAL_CONSISTENCY:
      addRangeToSet(set, RENDER_22101, RENDER_22106);
      addRangeToSet(set, RENDER_23040, RENDER_23043);
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
    ValidationFunction<RenderCurve> func = null;
    switch(errorCode) {
    case RENDER_22101:
      func = new UnknownCoreAttributeValidationFunction<RenderCurve>();
      break;
    case RENDER_22102:
      func = new UnknownCoreElementValidationFunction<RenderCurve>();
      break;
    case RENDER_22103:
      func = new UnknownPackageAttributeValidationFunction<RenderCurve>(RenderConstants.shortLabel);
      break;
    case RENDER_22104:
      func = new UnknownPackageElementValidationFunction<RenderCurve>(RenderConstants.shortLabel) {
        public boolean check(ValidationContext ctx, RenderCurve rc) {
          return super.check(ctx, rc)
            && new DuplicatedElementValidationFunction<RenderCurve>(
              RenderConstants.listOfElements).check(ctx, rc);
        }
      };
      break;
    case RENDER_22105:
      func = new ValidationFunction<RenderCurve>() {
        @Override
        public boolean check(ValidationContext ctx, RenderCurve rc) {
          if (rc.isSetStartHead()) {
            SBase referenced = rc.getModel().getSBaseById(rc.getStartHead());
            return referenced != null && referenced instanceof LineEnding;
          }
          return true;
        }
      };
      break;
    case RENDER_22106:
      func = new ValidationFunction<RenderCurve>() {
        @Override
        public boolean check(ValidationContext ctx, RenderCurve rc) {
          if (rc.isSetEndHead()) {
            SBase referenced = rc.getModel().getSBaseById(rc.getEndHead());
            return referenced != null && referenced instanceof LineEnding;
          }
          return true;
        }
      };
      break;
      
    // Cases common to RenderCurve and Polygon:
    case RENDER_23040:
      func = new ValidationFunction<RenderCurve>() {
        @Override
        public boolean check(ValidationContext ctx, RenderCurve t) {
          if(t.isSetListOfElements()) {
            // TODO 2020/03: add methods isListOf___Empty() to all the relevant classes
            return t.getListOfElements().isEmpty();
          }
          return true;
        }
      };
      break;
    case RENDER_23041:
      func = new ValidationFunction<RenderCurve>() {
        @Override
        public boolean check(ValidationContext ctx, RenderCurve t) {
          if(t.isSetListOfElements()) {
            return new UnknownCoreElementValidationFunction<ListOf<RenderPoint>>().check(
              ctx, t.getListOfElements())
              && new UnknownPackageElementValidationFunction<ListOf<RenderPoint>>(
                RenderConstants.shortLabel).check(ctx, t.getListOfElements());
          }
          return true;
        }
      };
      break;
    case RENDER_23042:
      func = new ValidationFunction<RenderCurve>() {
        @Override
        public boolean check(ValidationContext ctx, RenderCurve t) {
          if(t.isSetListOfElements()) {
            return new UnknownCoreAttributeValidationFunction<ListOf<RenderPoint>>().check(
              ctx, t.getListOfElements());
          }
          return true;
        }
      };
      break;
    case RENDER_23043:
      func = new ValidationFunction<RenderCurve>() {
        @Override
        public boolean check(ValidationContext ctx, RenderCurve t) {
          if(t.isSetListOfElements()) {
            ListOf<RenderPoint> list = t.getListOfElements();
            return !list.isEmpty() 
                && (list.get(0) instanceof RenderPoint) // not really needed, but here for completeness
                && !(list.get(0) instanceof RenderCubicBezier);
          }
          return true;
        }
      };
      break;
    }
    return func;
  }
}
