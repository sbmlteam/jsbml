/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2022 jointly by the following organizations:
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

import java.io.File;
import java.util.Set;

import org.sbml.jsbml.ext.render.Image;
import org.sbml.jsbml.ext.render.RenderConstants;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the
 * {@link Image} class.
 * 
 * @author David Emanuel Vetter
 */
public class ImageConstraints extends AbstractConstraintDeclaration {

  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category, ValidationContext context) {
    switch(category) {
    case GENERAL_CONSISTENCY:
      addRangeToSet(set, RENDER_21201, RENDER_21210);
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
    ValidationFunction<Image> func;
    switch(errorCode) {
    case RENDER_21201:
      func = new UnknownCoreAttributeValidationFunction<Image>();
      break;
    case RENDER_21202:
      func = new UnknownCoreElementValidationFunction<Image>();
      break;
    case RENDER_21203:
      func = new UnknownPackageAttributeValidationFunction<Image>(RenderConstants.shortLabel) {
        @Override
        public boolean check(ValidationContext ctx, Image image) {
          return super.check(ctx, image) 
              && image.isSetHref() 
              && image.isSetX() && image.isSetY() 
              && image.isSetWidth() && image.isSetHeight();
        }
      };
      break;
    case RENDER_21204:
      func = new ValidationFunction<Image>() {
        @Override
        public boolean check(ValidationContext ctx, Image image) {
          // must be set, any string; for content cf. render-21209
          return image.isSetHref();
        }
      };
      break;
    case RENDER_21205:
      func = new ValidationFunction<Image>() {
        @Override
        public boolean check(ValidationContext ctx, Image image) {
          return image.isSetX() && (image.getX().isSetAbsoluteValue()
            || image.getX().isSetRelativeValue());
        }
      };
      break;
    case RENDER_21206:
      func = new ValidationFunction<Image>() {
        @Override
        public boolean check(ValidationContext ctx, Image image) {
          return image.isSetY() && (image.getY().isSetAbsoluteValue()
            || image.getY().isSetRelativeValue());
        }
      };
      break;
    case RENDER_21207:
      func = new ValidationFunction<Image>() {
        @Override
        public boolean check(ValidationContext ctx, Image image) {
          return image.isSetWidth() && (image.getWidth().isSetAbsoluteValue()
            || image.getWidth().isSetRelativeValue());
        }
      };
      break;
    case RENDER_21208:
      func = new ValidationFunction<Image>() {
        @Override
        public boolean check(ValidationContext ctx, Image image) {
          return image.isSetHeight() && (image.getHeight().isSetAbsoluteValue()
            || image.getHeight().isSetRelativeValue());
        }
      };
      break;
    case RENDER_21209:
      func = new ValidationFunction<Image>() {
        @Override
        public boolean check(ValidationContext ctx, Image image) {
          if(image.isSetHref()) {
            // TODO 2020/03: COULD check, whether file exists, but it is unclear
            // from the spec whether that is actually required
            // Could further check for whether local or URL
            File file = new File(image.getHref()); 
            return !file.isDirectory()
              && (image.getHref().toLowerCase().endsWith(".png")
                || image.getHref().toLowerCase().endsWith(".jpeg")
                || image.getHref().toLowerCase().endsWith(".jpg")); // jpg=jpeg (synonymy)
          }
          return false;
        }
      };
      break;
    case RENDER_21210:
      func = new ValidationFunction<Image>() {
        @Override
        public boolean check(ValidationContext ctx, Image image) {
          return !image.isSetZ() || image.getZ().isSetAbsoluteValue()
            || image.getZ().isSetRelativeValue();
        }
      };
      break;

    default:
      func = null;
      break;
    }
    return func;
  }
}
