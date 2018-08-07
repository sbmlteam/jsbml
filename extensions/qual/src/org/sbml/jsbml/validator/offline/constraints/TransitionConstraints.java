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
import org.sbml.jsbml.ext.qual.FunctionTerm;
import org.sbml.jsbml.ext.qual.Input;
import org.sbml.jsbml.ext.qual.Output;
import org.sbml.jsbml.ext.qual.QualConstants;
import org.sbml.jsbml.ext.qual.Transition;
import org.sbml.jsbml.util.TreeNodeWithChangeSupport;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.DuplicatedElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.InvalidAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeAbstractValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;

/**
 * @author Nicolas Rodriguez, Lisa Falk
 * @since 1.3
 */
public class TransitionConstraints extends AbstractConstraintDeclaration {

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
		    addRangeToSet(set, QUAL_20401, QUAL_20414);
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
		ValidationFunction<Transition> func = null;

		switch (errorCode) {
		case QUAL_20401:
			// may have the optional attributes metaid and sboTerm
			// no other namespaces are permitted

	     func = new AbstractValidationFunction<Transition>() {
	        @Override
	        public boolean check(ValidationContext ctx, Transition t) {
	          return new UnknownCoreAttributeAbstractValidationFunction<Transition>().check(ctx, t, QUAL_20401);
	        }
	      };
	      break;
	    
		case QUAL_20402:
			// may have the optional subobjects for notes and annotations
			// no other namespaces are permitted

			func = new UnknownCoreElementValidationFunction<Transition>();
			break;

		case QUAL_20403:
			// may have the optional attributes qual:id and qual:name
			// no other attributes from the qual namespace are permitted

			func = new UnknownPackageAttributeValidationFunction<Transition>(QualConstants.shortLabel);
			break;

		case QUAL_20404:
			// The attribute name must be of the data type String
			// edit Transition.readAttribute

			func = new InvalidAttributeValidationFunction<Transition>(QualConstants.name);
			break;

		case QUAL_20405:
			// must have one and only one instance of the ListOfFunctionTerms objects
			// and may have at most one instance of the ListOfInputs and ListOfOutputs
			// objects
			  
		  func = new AbstractValidationFunction<Transition>() {

		    @Override
		    public boolean check(ValidationContext ctx, Transition t) {
		      Boolean functionTerm = false;
		      if (t.isSetListOfFunctionTerms() && t.getListOfFunctionTerms().size() > 0) {
		        functionTerm = new DuplicatedElementValidationFunction<Transition>(QualConstants.listOfFunctionTerms).check(ctx, t);
		      }
		      Boolean input = new DuplicatedElementValidationFunction<Transition>(QualConstants.listOfInputs).check(ctx, t);
		      Boolean output = new DuplicatedElementValidationFunction<Transition>(QualConstants.listOfOutputs).check(ctx, t);
		      if ((functionTerm && input && output) == false){
		        ValidationConstraint.logError(ctx, QUAL_20405, t, Boolean.toString(t.isSetListOfFunctionTerms()), Boolean.toString(input), Boolean.toString(output));
		        return false;
		      }
		    return true;
		    }
		  };
		  break;

		case QUAL_20406:
			// ListOfInputs and ListOfOutputs subobjects on a are optional,
			// but if present, these container object must not be empty.

			func = new AbstractValidationFunction<Transition>() {

				@Override
				public boolean check(ValidationContext ctx, Transition t) {
					if ((t.isSetListOfInputs() && t.getListOfInputs().isEmpty())
              || (t.isSetListOfOutputs() && t.getListOfOutputs().isEmpty())) {
					  ValidationConstraint.logError(ctx, QUAL_20406, t, Integer.toString(t.getListOfInputs().size()), Integer.toString(t.getListOfOutputs().size()));
            return false;
					}
				  return true;
				}
			};
			break;

		case QUAL_20407:
			// ListOfInputs container object may only contain Input objects

			func = new ValidationFunction<Transition>() {

				@Override
				public boolean check(ValidationContext ctx, Transition t) {
					if (t.isSetListOfInputs() || t.getListOfInputs().isEmpty()) {
						UnknownElementValidationFunction<ListOf<Input>> unFunc = new UnknownElementValidationFunction<ListOf<Input>>();
						return unFunc.check(ctx, t.getListOfInputs());
					}
					return true;
				}
			};
			break;

		case QUAL_20408:
			// ListOfOutputs container object may only contain Output objects

			func = new ValidationFunction<Transition>() {

				@Override
				public boolean check(ValidationContext ctx, Transition t) {
					if (t.isSetListOfOutputs() || t.getListOfOutputs().isEmpty()) {
						UnknownElementValidationFunction<ListOf<Output>> unFunc = new UnknownElementValidationFunction<ListOf<Output>>();
						return unFunc.check(ctx, t.getListOfOutputs());
					}
					return true;
				}
			};
			break;

		case QUAL_20409:
			// ListOfFunctionTerms container object must contain one and only one
			// DefaultTerm object and then may only contain FunctionTerm objects.
		  
		  func = new ValidationFunction<Transition>() {
		    @Override
		    public boolean check(ValidationContext ctx, Transition t) {
		      if (t.isSetListOfFunctionTerms() && !t.getListOfFunctionTerms().isEmpty()) {
		        Boolean hasDefaultTerm = false;
		        for (FunctionTerm ft : t.getListOfFunctionTerms()) {
		          if (ft.isDefaultTerm()) {
		            hasDefaultTerm = true;
		          }
		        }
		        Boolean onlyDefaultTerm = new DuplicatedElementValidationFunction<ListOf<FunctionTerm>>(
		            QualConstants.defaultTerm).check(ctx, t.getListOfFunctionTerms());
		        Boolean onlyFuntionTermObjects = new UnknownElementValidationFunction<ListOf<FunctionTerm>>()
		            .check(ctx, t.getListOfFunctionTerms());
		        return (hasDefaultTerm && onlyDefaultTerm && onlyFuntionTermObjects);
		      }
		      return true;
		    }
		  };
		  break;

		case QUAL_20410:
			// A ListOfInputs object may have the optional metaid and sboTerm
			// no other namespaces are permitted on a ListOfInputs object

			func = new ValidationFunction<Transition>() {

				@Override
				public boolean check(ValidationContext ctx, Transition t) {

				  if (t.isSetListOfInputs()) {
				    return new UnknownAttributeValidationFunction<TreeNodeWithChangeSupport>().check(ctx, t.getListOfInputs());
				  }
				  return true;
				}
			};
			break;

		case QUAL_20411:
			// A ListOfOutputs object may have the optional metaid and sboTerm
			// no other namespaces are permitted on a ListOfOutputs object

			func = new ValidationFunction<Transition>() {

				@Override
				public boolean check(ValidationContext ctx, Transition t) {

					if (t.isSetListOfOutputs()) {
					  return new UnknownAttributeValidationFunction<TreeNodeWithChangeSupport>().check(ctx, t.getListOfOutputs());
					  }
					return true;
				}
			};
			break;

		case QUAL_20412:
			// A ListOfFunctionTerms object may have the optional metaid and sboTerm
			// no other namespaces are permitted on a ListOfFunctionTerms object;

			func = new ValidationFunction<Transition>() {

				@Override
				public boolean check(ValidationContext ctx, Transition t) {

					if (t.isSetListOfFunctionTerms()) {
					  return new UnknownAttributeValidationFunction<TreeNodeWithChangeSupport>().check(ctx, t.getListOfFunctionTerms());
					}
					return true;
				}
			};
			break;

		case QUAL_20413:
			// No element of the ListOfFunctionTerms object may cause the level of a
			// QualitativeSpecies to exceed the value qual:maxLevel attribute

		  func = new ValidationFunction<Transition>() {

		    @Override
		    public boolean check(ValidationContext ctx, Transition t) {
		      int maxLevel = Integer.MIN_VALUE;
		      String QualSpecToExceed = "";
		      for (Output o : t.getListOfOutputs()) {
		        if (o.isSetQualitativeSpecies() && o.getQualitativeSpeciesInstance() != null && o.getQualitativeSpeciesInstance().isSetMaxLevel()) {
		          int newMaxLevel = o.getQualitativeSpeciesInstance().getMaxLevel();
		          if (newMaxLevel > maxLevel) {
		            QualSpecToExceed = o.getQualitativeSpecies();
		            maxLevel = newMaxLevel;
		          }
		        } else {
		          // if the maxLevel is not known, we continue with the next output
		          continue;
		        }
		        
		        for (FunctionTerm ft : t.getListOfFunctionTerms()) {
		          if (ft.isSetResultLevel()) {
		            int resultLevel = ft.getResultLevel();
		            if (resultLevel > maxLevel) {
		              ValidationConstraint.logError(ctx, QUAL_20413, t, t.getId(), QualSpecToExceed);
		              return false;
		            }
		          }
		        }
		      }
		      return true;
		    }
		  };
		  break;

		case QUAL_20414:
			// No element of the ListOfFunctionTerms object may cause the level of a
			// QualitativeSpecies to become negative
		  
      func = new AbstractValidationFunction<Transition>() {

        @Override
        public boolean check(ValidationContext ctx, Transition t) {
          for (FunctionTerm ft : t.getListOfFunctionTerms()) {
            if (ft.isSetResultLevel() && ft.getResultLevel() < 0) {
              ValidationConstraint.logError(ctx, QUAL_20414, t, Integer.toString(ft.getResultLevel()));
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
}
