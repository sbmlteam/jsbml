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

import org.sbml.jsbml.ext.qual.Input;
import org.sbml.jsbml.ext.qual.InputTransitionEffect;
import org.sbml.jsbml.ext.qual.QualConstants;
import org.sbml.jsbml.ext.qual.QualitativeSpecies;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.InvalidAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeAbstractValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the {@link Input} class.
 * 
 * @author Nicolas Rodriguez, Lisa Falk
 * @since 1.3
 */
public class InputConstraints extends AbstractConstraintDeclaration {

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
		    addRangeToSet(set, QUAL_20501, QUAL_20510);
		  }
		  
		case MODELING_PRACTICE:
			break;
		case SBO_CONSISTENCY:
			break;
		case IDENTIFIER_CONSISTENCY:
			break;
		case MATHML_CONSISTENCY:
			break;
		case OVERDETERMINED_MODEL:
			break;
		case UNITS_CONSISTENCY:

		}
	}

	@Override
	public ValidationFunction<?> getValidationFunction(int errorCode, ValidationContext context) {
		ValidationFunction<Input> func = null;

		switch (errorCode) {
		case QUAL_20501:
			// May have the optional attributes metaid and sboTerm.
			// No other namespaces are permitted.

      func = new AbstractValidationFunction<Input>() {
        @Override
        public boolean check(ValidationContext ctx, Input i) {
          return new UnknownCoreAttributeAbstractValidationFunction<Input>().check(ctx, i, QUAL_20501);
        }
      };
      break;

		case QUAL_20502:
			// May have the optional subobjects for notes and annotations.
			// No other namespaces are permitted.

			func = new UnknownCoreElementValidationFunction<Input>();
			break;

		case QUAL_20503:
			// must have the attributes qual:qualitativeSpecies, qual:transitionEffect
			// may have the attributes qual:id, qual:name, qual:sign, qual:thresholdLevel
			// No other attributes are permitted.

			func = new UnknownPackageAttributeValidationFunction<Input>(QualConstants.shortLabel) {

				@Override
				public boolean check(ValidationContext ctx, Input i) {
					// id, compartment and constant are required
					if (!i.isSetQualitativeSpecies() || !i.isSetTransitionEffect()) {
						return false;
					}
					return super.check(ctx, i);
				}
			};
			break;

		case QUAL_20504:
			// The attribute qual:name in Input must be of the data type string.

			func = new InvalidAttributeValidationFunction<Input>(QualConstants.name);
			break;

		case QUAL_20505:
			// The value of the attribute qual:sign of must conform the syntax of the SBML
			// data type sign and may only take on the allowed values of sign defined in
			// SBML; that is, the value must be one of the following: 'positive',
			// 'negative', 'dual' or 'unknown'.

			func = new InvalidAttributeValidationFunction<Input>(QualConstants.sign);
			break;

		case QUAL_20506:
			// The value of the attribute qual:transitionEffect must conform to the
			// syntax of the SBML data type transitionInputEffect and may only take on the
			// allowed values of transitionInputEffect defined in SBML; that is, the value
			// must be one of the following: 'none' or 'consumption'.

			func = new InvalidAttributeValidationFunction<Input>(QualConstants.transitionEffect);
			break;

		case QUAL_20507:
			// The attribute qual:thresholdLevel in Input must be of the data type integer.

			func = new InvalidAttributeValidationFunction<Input>(QualConstants.thresholdLevel);
			break;

		case QUAL_20508:
      // The value of the attribute qual:qualitativeSpecies must be the identifier
      // of an existing QualitativeSpecies object defined in the enclosing Model

		  func = new AbstractValidationFunction<Input>() {
		    @Override
		    public boolean check(ValidationContext ctx, Input i) {

		      if (i.isSetQualitativeSpecies() && i.getQualitativeSpeciesInstance() == null) {
		        ValidationConstraint.logError(ctx, QUAL_20508, i, i.getQualitativeSpecies());
		        return false;
		      }
		      return true;  
		    }
		  };
		  break;

    case QUAL_20509:
      // An Input that refers to a QualitativeSpecies that has a qual:constant
      // attribute set to 'true' cannot have the attribute qual:transitionEffect set
      // to 'consumption'.
      
      func = new AbstractValidationFunction<Input>() {
        @Override
        public boolean check(ValidationContext ctx, Input i) {
          
          if (i.isSetQualitativeSpecies() && i.isSetTransitionEffect()) { 
            QualitativeSpecies qs = i.getQualitativeSpeciesInstance();
            if (qs != null && qs.isSetConstant() && qs.getConstant() && i.getTransitionEffect() == InputTransitionEffect.consumption) {
              ValidationConstraint.logError(ctx, QUAL_20509, i, i.getQualitativeSpecies(), i.getId());
              return false;
            }
          }
          return true;
        }
      };
      break;


		case QUAL_20510:
			// The attribute qual:thresholdLevel in Input must not be negative
			func = new AbstractValidationFunction<Input>() {
				@Override
				public boolean check(ValidationContext ctx, Input i) {
				  if (i.isSetThresholdLevel() && i.getThresholdLevel() < 0) {
				    ValidationConstraint.logError(ctx, QUAL_20510, i, i.getId(), Integer.toString(i.getThresholdLevel()));
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
