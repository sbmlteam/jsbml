/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2017 jointly by the following organizations:
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
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.DuplicatedElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.InvalidAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeValidationFunction;
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
			set.add(QUAL_20401);
			set.add(QUAL_20402);
			set.add(QUAL_20403);
			set.add(QUAL_20404);
			set.add(QUAL_20405);
			set.add(QUAL_20406);
			set.add(QUAL_20407);
			set.add(QUAL_20408);
			set.add(QUAL_20409);
			set.add(QUAL_20410);
			set.add(QUAL_20411);
			set.add(QUAL_20412);
			set.add(QUAL_20413);
			set.add(QUAL_20414);

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

			func = new UnknownCoreAttributeValidationFunction<Transition>();
			break;

		case QUAL_20402:
			// may have the optional subobjects for notes and annotations
			// no other namespaces are permitted

			func = new UnknownCoreElementValidationFunction<Transition>();
			break;

		case QUAL_20403:
			// may have the optional attributes qual:id, name
			// no other namespaces are permitted

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

			func = new ValidationFunction<Transition>() {

				@Override
				public boolean check(ValidationContext ctx, Transition t) {
					if (t.isSetListOfFunctionTerms()) {
						return new DuplicatedElementValidationFunction<Transition>("listOfReactants").check(ctx, t)
								&& new DuplicatedElementValidationFunction<Transition>("listOfInputs").check(ctx, t)
								&& new DuplicatedElementValidationFunction<Transition>("listOfOutputs").check(ctx, t);
					}
					return false;
				}
			};
			break;

		case QUAL_20406:
			// ListOfInputs and ListOfOutputs subobjects on a are optional,
			// but if present, these container object must not be empty.

			func = new ValidationFunction<Transition>() {

				@Override
				public boolean check(ValidationContext ctx, Transition t) {
					// if ListOfInputs or ListOfOutputs are empty, we return false, else true
					return !((t.isSetListOfInputs() && t.getListOfInputs().isEmpty())
							|| (t.isSetListOfOutputs() && t.getListOfOutputs().isEmpty()));
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

		case QUAL_20410:
			// A ListOfInputs object may have the optional metaid and sboTerm
			// no other namespaces are permitted on a ListOfInputs object

			func = new ValidationFunction<Transition>() {

				@Override
				public boolean check(ValidationContext ctx, Transition t) {

					if (t.isSetListOfInputs()) {
						UnknownAttributeValidationFunction<ListOf<Input>> unFunc = new UnknownAttributeValidationFunction<ListOf<Input>>();
						return unFunc.check(ctx, t.getListOfInputs());
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
						UnknownAttributeValidationFunction<ListOf<Output>> unFunc = new UnknownAttributeValidationFunction<ListOf<Output>>();
						return unFunc.check(ctx, t.getListOfOutputs());
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
						UnknownAttributeValidationFunction<ListOf<FunctionTerm>> unFunc = new UnknownAttributeValidationFunction<ListOf<FunctionTerm>>();
						return unFunc.check(ctx, t.getListOfFunctionTerms());
					}
					return true;
				}
			};
			break;

		case QUAL_20413:
			// No element of the ListOfFunctionTerms object may cause the level of a
			// QualitativeSpecies to exceed the value qual:maxLevel attribute

		case QUAL_20414:
			// No element of the ListOfFunctionTerms object may cause the level of a
			// QualitativeSpecies to become negative
		}
		return func;
	}
}
