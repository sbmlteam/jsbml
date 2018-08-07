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

import org.sbml.jsbml.ext.qual.FunctionTerm;
import org.sbml.jsbml.ext.qual.QualConstants;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.DuplicatedMathValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.InvalidAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeAbstractValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the {@link FunctionTerm} class.
 * 
 * @author Nicolas Rodriguez, Lisa Falk
 * @since 1.3
 */
public class FunctionTermConstraints extends AbstractConstraintDeclaration {

	@Override
	public void addErrorCodesForAttribute(Set<Integer> set, int level, int version, String attributeName,
			ValidationContext context) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addErrorCodesForCheck(Set<Integer> set, int level, int version, CHECK_CATEGORY category,
			ValidationContext context) {
		switch (category) {
		case GENERAL_CONSISTENCY:
		  if (level >= 3) {
		    addRangeToSet(set, QUAL_20801, QUAL_20806);
		    addRangeToSet(set, QUAL_20701, QUAL_20705);
		  }
		  
		case MODELING_PRACTICE:
			break;
		case SBO_CONSISTENCY:
			break;
		case IDENTIFIER_CONSISTENCY:
			break;
		case MATHML_CONSISTENCY:
		  if (level >= 3) {
		    addRangeToSet(set, QUAL_10201, QUAL_10202);
      }
			break;
		case OVERDETERMINED_MODEL:
			break;
		case UNITS_CONSISTENCY:

		}
	}

	@Override
	public ValidationFunction<?> getValidationFunction(int errorCode, ValidationContext context) {
		ValidationFunction<FunctionTerm> func = null;

		switch (errorCode) {
    case QUAL_10201:
		// The MathML math element in a FunctionTerm object should evaluate to a value of type
		// boolean.
		
      func = new AbstractValidationFunction<FunctionTerm>() {

        @Override
        public boolean check(ValidationContext ctx, FunctionTerm ft) {
          if (ft.isSetMath()) {
            if (!ft.getMath().isBoolean()) {
              ValidationConstraint.logError(ctx, QUAL_10201, ft, ft.getId());
            }
            return ft.getMath().isBoolean(); 
          }
          return true;
        }
      };
      break;
      
    case QUAL_10202:
      // The MathML math element in a FunctionTerm object should not use the csymbol elements
      // 'time' and 'delay' as these explicitly introduce time into the model.
      
      break;
      
    case QUAL_20701:
      // May have the optional attributes metaid and sboTerm.
      // No other namespaces are permitted.

      // same as 20801
      
      func = new AbstractValidationFunction<FunctionTerm>() {
        @Override
        public boolean check(ValidationContext ctx, FunctionTerm ft) {
          boolean func = true;
          if (ft.isDefaultTerm()) {
            func = new UnknownCoreAttributeAbstractValidationFunction<FunctionTerm>().check(ctx, ft, QUAL_20701);
            return func;      
          }
          return true;
        }
      };
      break;


    case QUAL_20702:
      // May have the optional subobjects for notes and annotations.
      // No other namespaces are permitted.

      // same as 20802
      
      func = new UnknownCoreElementValidationFunction<FunctionTerm>() {

        @Override
        public boolean check(ValidationContext ctx, FunctionTerm ft) {
          
          // only testing if it is a defaultTerm
          if (ft.isDefaultTerm()) {
            return super.check(ctx, ft);
          }
          
          return true;
        }
      };
      break;

    case QUAL_20703:
      // must have the required attributes qual:resultLevel
      // No other attributes are permitted.

      // same as 20803
      
      func = new UnknownPackageAttributeValidationFunction<FunctionTerm>(QualConstants.shortLabel) {

        @Override
        public boolean check(ValidationContext ctx, FunctionTerm ft) {
          if (ft.isDefaultTerm() && !ft.isSetResultLevel()) {
            return false;
          } else if (ft.isDefaultTerm()) {
            return super.check(ctx, ft);
          }
          
          return true;
        }
      };
      break;

    case QUAL_20704:
      // The attribute qual:resultLevel in DefaultTerm must be of the data type integer

      // same as 20805
      
      func = new InvalidAttributeValidationFunction<FunctionTerm>(QualConstants.resultLevel) {

        @Override
        public boolean check(ValidationContext ctx, FunctionTerm ft) {
          
          // only testing if it is a defaultTerm
          if (ft.isDefaultTerm()) {
            return super.check(ctx, ft);
          }
          
          return true;
        }
      };
      break;

    case QUAL_20705:
      // The attribute qual:resultLevel in DefaultTerm must not be negative.
      // same as 20806

      func = new AbstractValidationFunction<FunctionTerm>() {
        @Override
        public boolean check(ValidationContext ctx, FunctionTerm ft) {
          if (ft.isDefaultTerm() && ft.isSetResultLevel() && ft.getResultLevel() < 0) {
            ValidationConstraint.logError(ctx, QUAL_20705, ft, "Default Term", Integer.valueOf(ft.getResultLevel()).toString());
            return false;
          }
          
          return true;
        }
      };
      break;

		
		case QUAL_20801:
			// May have the optional attributes metaid and sboTerm.
			// No other namespaces are permitted.

		  func = new AbstractValidationFunction<FunctionTerm>() {
        @Override
        public boolean check(ValidationContext ctx, FunctionTerm ft) {
          boolean func = true;
          
          if (!ft.isDefaultTerm()) {
            func = new UnknownCoreAttributeValidationFunction<FunctionTerm>().check(ctx, ft);
            if (func == false) {
              ValidationConstraint.logError(ctx, QUAL_20801, ft, "TheBrokenAtt");
            }
            return func;
          }
          
          return true;
        }
      };
      break;


		case QUAL_20802:
		  // May have the optional subobjects for notes and annotations.
		  // No other namespaces are permitted.

		  func = new UnknownCoreElementValidationFunction<FunctionTerm>(){

		    @Override
		    public boolean check(ValidationContext ctx, FunctionTerm ft) {

		      // only testing if it is not a defaultTerm
		      if (!ft.isDefaultTerm()) {
		        return super.check(ctx, ft);
		      }

		      return true;
		    }
		  };
		  break;

		case QUAL_20803:
			// must have the required attribute qual:resultLevel
			// No other attributes are permitted.

		     func = new UnknownPackageAttributeValidationFunction<FunctionTerm>(QualConstants.shortLabel) {

		        @Override
		        public boolean check(ValidationContext ctx, FunctionTerm ft) {
		          if ((!ft.isDefaultTerm()) && !ft.isSetResultLevel()) {
		            return false;
		          } else if (!ft.isDefaultTerm()) {
		            return super.check(ctx, ft);
		          }
		          
		          return true;
		        }
		      };
		      break;

		case QUAL_20804:
		  // A FunctionTerm object may (interpreted as 'must' for now) contain exactly oneMathML qual:math element.
		  // No other elements are permitted.
		  
		  func = new DuplicatedMathValidationFunction<FunctionTerm>(true) {

            @Override
            public boolean check(ValidationContext ctx, FunctionTerm ft) {
              
              // only testing if it is not a defaultTerm
              if (!ft.isDefaultTerm()) {
                return super.check(ctx, ft);
              }
              
              return true;
            }
          };
          break;

		case QUAL_20805:
		  // The attribute qual:resultLevel in FunctionTerm must be of the data type integer.

		  func = new InvalidAttributeValidationFunction<FunctionTerm>(QualConstants.resultLevel) {

		    @Override
		    public boolean check(ValidationContext ctx, FunctionTerm ft) {

		      // only testing if it is not a defaultTerm
		      if (!ft.isDefaultTerm()) {
		        return super.check(ctx, ft);
		      }

		      return true;
		    }
		  };
		  break;

	
		case QUAL_20806:
      // The attribute qual:resultLevel in FunctionTerm must not be negative.
      
      func = new AbstractValidationFunction<FunctionTerm>() {
        @Override
        public boolean check(ValidationContext ctx, FunctionTerm ft) {
          if (!ft.isDefaultTerm() && ft.isSetResultLevel() && ft.getResultLevel() < 0) {
            ValidationConstraint.logError(ctx, QUAL_20806, ft, ft.getElementName(), Integer.valueOf(ft.getResultLevel()).toString());
            return false;
          }
          return true;
        }
      };
      break;
    }
    return func;
  }
}
