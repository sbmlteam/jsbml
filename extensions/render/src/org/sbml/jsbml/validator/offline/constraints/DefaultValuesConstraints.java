package org.sbml.jsbml.validator.offline.constraints;

import java.util.Set;

import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.render.DefaultValues;
import org.sbml.jsbml.ext.render.HTextAnchor;
import org.sbml.jsbml.ext.render.LineEnding;
import org.sbml.jsbml.ext.render.RelAbsVector;
import org.sbml.jsbml.ext.render.RenderConstants;
import org.sbml.jsbml.ext.render.VTextAnchor;
import org.sbml.jsbml.ext.render.GradientBase.Spread;
import org.sbml.jsbml.ext.render.GraphicalPrimitive2D.FillRule;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;


public class DefaultValuesConstraints extends AbstractConstraintDeclaration {

  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category, ValidationContext context) {
    switch(category) {
    case GENERAL_CONSISTENCY:
      addRangeToSet(set, RENDER_23001, RENDER_23032);
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
    ValidationFunction<DefaultValues> func = null;
    switch(errorCode) {
    case RENDER_23001:
      func = new UnknownCoreAttributeValidationFunction<DefaultValues>();
      break;
    case RENDER_23002:
      func = new UnknownCoreElementValidationFunction<DefaultValues>();
      break;
    case RENDER_23003:
      func = new UnknownPackageAttributeValidationFunction<DefaultValues>(RenderConstants.shortLabel);
      break;
      
    case RENDER_23004:
    case RENDER_23006:
    case RENDER_23008:
    case RENDER_23010:
      func = new ValidationFunction<DefaultValues>() {
        @Override // Any string
        public boolean check(ValidationContext ctx, DefaultValues t) { return true; }
      };
      break;
      
    case RENDER_23005:
      func = new ValidationFunction<DefaultValues>() {
        @Override
        public boolean check(ValidationContext ctx, DefaultValues t) {
          String value = t.getDefaultValue(RenderConstants.spreadMethod);
          if(value != null) {
            try {
              Spread.valueOf(value.toUpperCase());
            } catch (Exception e) {
              return false;
            }
          }
          return true;
        }
      };
      break;
    case RENDER_23007:
      func = new ValidationFunction<DefaultValues>() {
        @Override
        public boolean check(ValidationContext ctx, DefaultValues t) {
          String value = t.getDefaultValue(RenderConstants.fillRule);
          if(value != null) {
            try {
              FillRule.valueOf(value.toUpperCase());
            } catch (Exception e) {
              return false;
            }
          }
          return true;
        }
        
      };
      break;
    case RENDER_23009:
      func = new ValidationFunction<DefaultValues>() {
        @Override
        public boolean check(ValidationContext ctx, DefaultValues t) {
          String value = t.getDefaultValue(RenderConstants.strokeWidth);
          if(value != null) {
            try {
              Double.parseDouble(value.trim());
            } catch (Exception e) {
              return false;
            }
          }
          return true;
        }
      };
      break;
    case RENDER_23011:
      func = new ValidationFunction<DefaultValues>() {
        @Override
        public boolean check(ValidationContext ctx, DefaultValues t) {
          String value = t.getDefaultValue(RenderConstants.fontWeightBold);
          if(value != null) {
            value = value.toLowerCase();
            return value.equals(RenderConstants.fontWeightBoldFalse)
              || value.equals(RenderConstants.fontWeightBoldTrue);
          }
          return true;
        }
      };
      break;
    case RENDER_23012:
      func = new ValidationFunction<DefaultValues>() {
        @Override
        public boolean check(ValidationContext ctx, DefaultValues t) {
          String value = t.getDefaultValue(RenderConstants.fontStyleItalic);
          if(value != null) {
            value = value.toLowerCase();
            return value.equals(RenderConstants.fontStyleItalicFalse)
              || value.equals(RenderConstants.fontStyleItalicTrue);
          }
          return true;
        }
      };
      break;
    case RENDER_23013:
      func = new ValidationFunction<DefaultValues>() {
        @Override
        public boolean check(ValidationContext ctx, DefaultValues t) {
          String value = t.getDefaultValue(RenderConstants.textAnchor);
          if(value != null) {
            try {
              HTextAnchor.valueOf(value.toUpperCase());
            } catch (Exception e) {
              return false;
            }
          }
          return true;
        }
      };
      break;
    case RENDER_23014:
      func = new ValidationFunction<DefaultValues>() {
        @Override
        public boolean check(ValidationContext ctx, DefaultValues t) {
          String value = t.getDefaultValue(RenderConstants.vTextAnchor);
          if(value != null) {
            try {
              VTextAnchor.valueOf(value.toUpperCase());
            } catch (Exception e) {
              return false;
            }
          }
          return true;
        }
      };
      break;
    case RENDER_23015:
      func = new ValidationFunction<DefaultValues>() {
        @Override
        public boolean check(ValidationContext ctx, DefaultValues t) {
          String value = t.getDefaultValue(RenderConstants.startHead);
          if(value != null) {
            SBase referenced = t.getModel().getSBaseById(value);
            return referenced != null && referenced instanceof LineEnding;
          }
          return true;
        }
      };
      break;
    case RENDER_23016:
      func = new ValidationFunction<DefaultValues>() {
        @Override
        public boolean check(ValidationContext ctx, DefaultValues t) {
          String value = t.getDefaultValue(RenderConstants.endHead);
          if(value != null) {
            SBase referenced = t.getModel().getSBaseById(value);
            return referenced != null && referenced instanceof LineEnding;
          }
          return true;
        }
      };
      break;
    case RENDER_23017:
      func = new ValidationFunction<DefaultValues>() {
        @Override
        public boolean check(ValidationContext ctx, DefaultValues t) {
          String value = t.getDefaultValue(RenderConstants.enableRotationMapping);
          if(value != null) {
            try { Boolean.parseBoolean(value); } catch (Exception e) { return false; }
          }
          return true;
        }
      };
      break;
    case RENDER_23018:
      func = new ValidationFunction<DefaultValues>() {
        @Override
        public boolean check(ValidationContext ctx, DefaultValues t) {
          String value = t.getDefaultValue(RenderConstants.linearGradient_x1);
          if(value != null) {
            RelAbsVector vec = new RelAbsVector(value);
            return vec.isSetAbsoluteValue() || vec.isSetRelativeValue();
          }
          return true;
        }
      };
      break;
    case RENDER_23019:
      func = new ValidationFunction<DefaultValues>() {
        @Override
        public boolean check(ValidationContext ctx, DefaultValues t) {
          String value = t.getDefaultValue(RenderConstants.linearGradient_y1);
          if(value != null) {
            RelAbsVector vec = new RelAbsVector(value);
            return vec.isSetAbsoluteValue() || vec.isSetRelativeValue();
          }
          return true;
        }
      };
      break;
    case RENDER_23020:
      func = new ValidationFunction<DefaultValues>() {
        @Override
        public boolean check(ValidationContext ctx, DefaultValues t) {
          String value = t.getDefaultValue(RenderConstants.linearGradient_z1);
          if(value != null) {
            RelAbsVector vec = new RelAbsVector(value);
            return vec.isSetAbsoluteValue() || vec.isSetRelativeValue();
          }
          return true;
        }
      };
      break;
    case RENDER_23021:
      func = new ValidationFunction<DefaultValues>() {
        @Override
        public boolean check(ValidationContext ctx, DefaultValues t) {
          String value = t.getDefaultValue(RenderConstants.linearGradient_x2);
          if(value != null) {
            RelAbsVector vec = new RelAbsVector(value);
            return vec.isSetAbsoluteValue() || vec.isSetRelativeValue();
          }
          return true;
        }
      };
      break;
    case RENDER_23022:
      func = new ValidationFunction<DefaultValues>() {
        @Override
        public boolean check(ValidationContext ctx, DefaultValues t) {
          String value = t.getDefaultValue(RenderConstants.linearGradient_y2);
          if(value != null) {
            RelAbsVector vec = new RelAbsVector(value);
            return vec.isSetAbsoluteValue() || vec.isSetRelativeValue();
          }
          return true;
        }
      };
      break;
    case RENDER_23023:
      func = new ValidationFunction<DefaultValues>() {
        @Override
        public boolean check(ValidationContext ctx, DefaultValues t) {
          String value = t.getDefaultValue(RenderConstants.linearGradient_z2);
          if(value != null) {
            RelAbsVector vec = new RelAbsVector(value);
            return vec.isSetAbsoluteValue() || vec.isSetRelativeValue();
          }
          return true;
        }
      };
      break;
    case RENDER_23024:
      func = new ValidationFunction<DefaultValues>() {
        @Override
        public boolean check(ValidationContext ctx, DefaultValues t) {
          String value = t.getDefaultValue(RenderConstants.radialGradient_cx);
          if(value != null) {
            RelAbsVector vec = new RelAbsVector(value);
            return vec.isSetAbsoluteValue() || vec.isSetRelativeValue();
          }
          return true;
        }
      };
      break;
    case RENDER_23025:
      func = new ValidationFunction<DefaultValues>() {
        @Override
        public boolean check(ValidationContext ctx, DefaultValues t) {
          String value = t.getDefaultValue(RenderConstants.radialGradient_cy);
          if(value != null) {
            RelAbsVector vec = new RelAbsVector(value);
            return vec.isSetAbsoluteValue() || vec.isSetRelativeValue();
          }
          return true;
        }
      };
      break;
    case RENDER_23026:
      func = new ValidationFunction<DefaultValues>() {
        @Override
        public boolean check(ValidationContext ctx, DefaultValues t) {
          String value = t.getDefaultValue(RenderConstants.radialGradient_cz);
          if(value != null) {
            RelAbsVector vec = new RelAbsVector(value);
            return vec.isSetAbsoluteValue() || vec.isSetRelativeValue();
          }
          return true;
        }
      };
      break;
    case RENDER_23027:
      func = new ValidationFunction<DefaultValues>() {
        @Override
        public boolean check(ValidationContext ctx, DefaultValues t) {
          String value = t.getDefaultValue(RenderConstants.radialGradient_r);
          if(value != null) {
            RelAbsVector vec = new RelAbsVector(value);
            return vec.isSetAbsoluteValue() || vec.isSetRelativeValue();
          }
          return true;
        }
      };
      break;
    case RENDER_23028:
      func = new ValidationFunction<DefaultValues>() {
        @Override
        public boolean check(ValidationContext ctx, DefaultValues t) {
          String value = t.getDefaultValue(RenderConstants.radialGradient_fx);
          if(value != null) {
            RelAbsVector vec = new RelAbsVector(value);
            return vec.isSetAbsoluteValue() || vec.isSetRelativeValue();
          }
          return true;
        }
      };
      break;
    case RENDER_23029:
      func = new ValidationFunction<DefaultValues>() {
        @Override
        public boolean check(ValidationContext ctx, DefaultValues t) {
          String value = t.getDefaultValue(RenderConstants.radialGradient_fy);
          if(value != null) {
            RelAbsVector vec = new RelAbsVector(value);
            return vec.isSetAbsoluteValue() || vec.isSetRelativeValue();
          }
          return true;
        }
      };
      break;
    case RENDER_23030:
      func = new ValidationFunction<DefaultValues>() {
        @Override
        public boolean check(ValidationContext ctx, DefaultValues t) {
          String value = t.getDefaultValue(RenderConstants.radialGradient_fz);
          if(value != null) {
            RelAbsVector vec = new RelAbsVector(value);
            return vec.isSetAbsoluteValue() || vec.isSetRelativeValue();
          }
          return true;
        }
      };
      break;
    case RENDER_23031:
      func = new ValidationFunction<DefaultValues>() {
        @Override
        public boolean check(ValidationContext ctx, DefaultValues t) {
          String value = t.getDefaultValue(RenderConstants.default_z);
          if(value != null) {
            RelAbsVector vec = new RelAbsVector(value);
            return vec.isSetAbsoluteValue() || vec.isSetRelativeValue();
          }
          return true;
        }
      };
      break;
    case RENDER_23032:
      func = new ValidationFunction<DefaultValues>() {
        @Override
        public boolean check(ValidationContext ctx, DefaultValues t) {
          String value = t.getDefaultValue(RenderConstants.fontSize);
          if(value != null) {
            RelAbsVector vec = new RelAbsVector(value);
            return vec.isSetAbsoluteValue() || vec.isSetRelativeValue();
          }
          return true;
        }
      };
      break;
    }
    return func;
  }
}
