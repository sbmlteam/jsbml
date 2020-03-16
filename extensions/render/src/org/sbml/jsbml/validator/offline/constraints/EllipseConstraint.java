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
import org.sbml.jsbml.ext.render.Ellipse;
import org.sbml.jsbml.ext.render.RenderConstants;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;
import org.sbml.jsbml.xml.XMLNode;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the
 * {@link Ellipse} class.
 * 
 * @author David Emanuel Vetter
 */
public class EllipseConstraint extends AbstractConstraintDeclaration {

  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category, ValidationContext context) {
    switch(category) {
    case GENERAL_CONSISTENCY:
      addRangeToSet(set, RENDER_20601, RENDER_20609);
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
    ValidationFunction<Ellipse> func = null;
    switch(errorCode) {
    case RENDER_20601:
      func = new UnknownCoreAttributeValidationFunction<Ellipse>();
      break;
    case RENDER_20602:
      func = new UnknownCoreElementValidationFunction<Ellipse>();
      break;
    case RENDER_20603:
      func = new UnknownPackageAttributeValidationFunction<Ellipse>(RenderConstants.shortLabel) {
        @Override
        public boolean check(ValidationContext ctx, Ellipse ellipse) {
          return super.check(ctx, ellipse) && ellipse.isSetCx()
            && ellipse.isSetCy() && ellipse.isSetRx();
        }
      };
      break;
    case RENDER_20604:
      func = new ValidationFunction<Ellipse>() {
        @Override
        public boolean check(ValidationContext ctx, Ellipse ellipse) {
          // See implementation of RelAbsVector: If the string does not conform
          // with the rel-abs-vector-format, relative and absolute will be set
          // to NaN (i.e. neither isSet); In any case: relative or absolute
          // ought to be set when reading from file
          return ellipse.isSetCx() && (ellipse.getCx().isSetAbsoluteValue()
            || ellipse.getCx().isSetRelativeValue());
        }
      };
      break;
    case RENDER_20605:
      func = new ValidationFunction<Ellipse>() {
        @Override
        public boolean check(ValidationContext ctx, Ellipse ellipse) {
          return ellipse.isSetCy() && (ellipse.getCy().isSetAbsoluteValue()
            || ellipse.getCy().isSetRelativeValue());
        }
      };
      break;
    case RENDER_20606:
      func = new ValidationFunction<Ellipse>() {
        @Override
        public boolean check(ValidationContext ctx, Ellipse ellipse) {
          return ellipse.isSetRx() && (ellipse.getRx().isSetAbsoluteValue()
            || ellipse.getRx().isSetRelativeValue());
        }
      };
      break;
    case RENDER_20607:
      func = new ValidationFunction<Ellipse>() {
        @Override
        public boolean check(ValidationContext ctx, Ellipse ellipse) {
          if(ellipse.getUserObject(JSBML.UNKNOWN_XML) != null) {
            XMLNode unknown = (XMLNode) ellipse.getUserObject(JSBML.UNKNOWN_XML);
            return unknown.getAttrIndex(RenderConstants.ratio) == -1;
            // != -1 means that ratio was not in a Number-format (invalid)
          }
          return true; // ratio may be infinite
        }
      };
      break;
    case RENDER_20608:
      func = new ValidationFunction<Ellipse>() {
        @Override
        public boolean check(ValidationContext ctx, Ellipse ellipse) {
          return !ellipse.isSetCz() || ellipse.getCz().isSetAbsoluteValue()
            || ellipse.getCz().isSetRelativeValue();
        }
      };
      break;
    case RENDER_20609:
      func = new ValidationFunction<Ellipse>() {
        @Override
        public boolean check(ValidationContext ctx, Ellipse ellipse) {
          return !ellipse.isSetRy() || ellipse.getRy().isSetAbsoluteValue()
            || ellipse.getRy().isSetRelativeValue();
        }
      };
      break;
    }
    return func;
  }
}
