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

import org.sbml.jsbml.ext.qual.Output;
import org.sbml.jsbml.ext.qual.QualConstants;
import org.sbml.jsbml.ext.qual.QualModelPlugin;
import org.sbml.jsbml.ext.qual.QualitativeSpecies;
import org.sbml.jsbml.ext.qual.Transition;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.InvalidAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreAttributeAbstractValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownCoreElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownPackageAttributeValidationFunction;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the {@link QualitativeSpecies} class.
 * 
 * @author Nicolas Rodriguez, Lisa Falk
 * @since 1.3
 */
public class QualitativeSpeciesConstraints extends AbstractConstraintDeclaration {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.validator.offline.constraints.ConstraintDeclaration#
	 * addErrorCodesForAttribute(java.util.Set, int, int, java.lang.String)
	 */
	@Override
	public void addErrorCodesForAttribute(Set<Integer> set, int level, int version, String attributeName,
			ValidationContext context) {
		// TODO

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.validator.offline.constraints.ConstraintDeclaration#
	 * addErrorCodesForCheck(java.util.Set, int, int,
	 * org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY)
	 */
	@Override
	public void addErrorCodesForCheck(Set<Integer> set, int level, int version, CHECK_CATEGORY category,
			ValidationContext context) {

		switch (category) {
		case GENERAL_CONSISTENCY:
			if (level >= 3) {
				addRangeToSet(set, QUAL_20301, QUAL_20310);
				addRangeToSet(set, QUAL_20312, QUAL_20313);
			}
			break;

		case IDENTIFIER_CONSISTENCY:
			break;

		case MATHML_CONSISTENCY:
			break;

		case MODELING_PRACTICE:
			break;

		case OVERDETERMINED_MODEL:
			break;

		case SBO_CONSISTENCY:
			break;

		case UNITS_CONSISTENCY:
			break;
		}
	}

	@Override
	public ValidationFunction<?> getValidationFunction(int errorCode, ValidationContext context) {
		ValidationFunction<QualitativeSpecies> func = null;

		switch (errorCode) {

		case QUAL_20301: {
			// may have the optional attributes metaid and sboTerm
			// no other attributes in the core namespace are permitted
		  
		  func = new AbstractValidationFunction<QualitativeSpecies>() {
		    @Override
		    public boolean check(ValidationContext ctx, QualitativeSpecies q) {
		      return new UnknownCoreAttributeAbstractValidationFunction<QualitativeSpecies>().check(ctx, q, QUAL_20301);
		    }
		  };
		  break;
		}
		
		case QUAL_20302: {
			// may have the optional subobjects for notes and annotations
			// no other elements in the core namespace are permitted
			func = new UnknownCoreElementValidationFunction<QualitativeSpecies>();
			break;
		}

		case QUAL_20303: {
			// must have the required attributes qual:id, qual:compartment and qual:constant
			// may have the optional attributes qual:name, qual:initialLevel and qual:maxLevel
			// no other attributes in the qual namespace are permitted

			func = new UnknownPackageAttributeValidationFunction<QualitativeSpecies>(QualConstants.shortLabel) {

				/*
				 * (non-Javadoc)
				 * 
				 * @see org.sbml.jsbml.validator.offline.constraints.helper.
				 * UnknownPackageAttributeValidationFunction#check(org.sbml.jsbml.validator.
				 * offline.ValidationContext, org.sbml.jsbml.util.TreeNodeWithChangeSupport)
				 */
				@Override
				public boolean check(ValidationContext ctx, QualitativeSpecies t) {
					// id, compartment and constant are required
					if (!t.isSetConstant() || !t.isSetCompartment() || !t.isSetId()) {
						return false;
					}

					return super.check(ctx, t);
				}
			};
			break;
		}

		case QUAL_20304: {
			// The attribute qual:constant must be of the data type boolean
			func = new InvalidAttributeValidationFunction<QualitativeSpecies>(QualConstants.constant);
			break;
		}

		case QUAL_20305:
			// The attribute qual:name must be of the data type string
			func = new InvalidAttributeValidationFunction<QualitativeSpecies>(QualConstants.name);
			break;

		case QUAL_20306:
			// The attribute qual:initialLevel must be of the data type integer
			func = new InvalidAttributeValidationFunction<QualitativeSpecies>(QualConstants.initialLevel);
			break;

		case QUAL_20307:
			// The attribute qual:maxLevel must be of the data type integer
			func = new InvalidAttributeValidationFunction<QualitativeSpecies>(QualConstants.maxLevel);
			break;

		case QUAL_20308:
			// The value of the attribute qual:compartment must be the identifier
			// of an existing Compartment object defined in the enclosing Model object

			func = new AbstractValidationFunction<QualitativeSpecies>() {
				@Override
				public boolean check(ValidationContext ctx, QualitativeSpecies qs) {
					if (qs.isSetCompartment() && qs.getModel() != null && qs.getCompartmentInstance() == null) {
						ValidationConstraint.logError(ctx, QUAL_20308, qs, qs.getId(), qs.getCompartment());
						return false;
					}
					return true;
				}
			};
			break;

		case QUAL_20309:
			// The value of the attribute qual:initialLevel cannot be greater than the
			// value of the qual:maxLevel attribute for the given QualitativeSpecies object
			func = new AbstractValidationFunction<QualitativeSpecies>() {
				@Override
				public boolean check(ValidationContext ctx, QualitativeSpecies qs) {
					if (qs.isSetInitialLevel() && qs.isSetMaxLevel() && qs.getInitialLevel() > qs.getMaxLevel()) {
					  ValidationConstraint.logError(ctx, QUAL_20309, qs, qs.getId(), Integer.toString(qs.getInitialLevel()), Integer.toString(qs.getMaxLevel()));
						return false;
					}
					return true;
				}
			};
			break;

		case QUAL_20310:
			// If the attribute qual:constant set to true it can only be referred to by an
			// Input
			// It cannot be the subject of an Output in a Transition
			func = new AbstractValidationFunction<QualitativeSpecies>() {
				@Override
				public boolean check(ValidationContext ctx, QualitativeSpecies qs) {
					if (qs.isSetConstant() && qs.getConstant()) {
						QualModelPlugin qmp = (QualModelPlugin) qs.getModel().getPlugin(qs.getNamespace());
						for (Transition trans : qmp.getListOfTransitions()) {
							for (Output output : trans.getListOfOutputs()) {
								if (output.getQualitativeSpecies().equals(qs.getId())) {
								  ValidationConstraint.logError(ctx, QUAL_20310, qs, qs.getId());
									return false;
								}
							}
						}
					}
					return true;
				}
			};
			break;

		case QUAL_20311:
		  // Implemented in the OutputConstraints class
		  
			// A QualitativeSpecies that is referenced by an Output with the
			// qual:transitionEffect attribute
			// set to 'assignmentLevel' cannot be referenced by any other Output with the
			// same transitionEffect throughout the set of transitions for the containing model.
			break;

		case QUAL_20312:
			// The attribute qual:initialLevel must not be negative
			func = new AbstractValidationFunction<QualitativeSpecies>() {
				@Override
				public boolean check(ValidationContext ctx, QualitativeSpecies qs) {
					if (qs.isSetInitialLevel() && qs.getInitialLevel() < 0) {
					  ValidationConstraint.logError(ctx, QUAL_20312, qs, qs.getId(), Integer.toString(qs.getInitialLevel()));
						return false;
					}
					return true;
				}
			};
			break;

		case QUAL_20313:
			// The attribute qual:maxLevel must not be negative.
			func = new AbstractValidationFunction<QualitativeSpecies>() {
				@Override
				public boolean check(ValidationContext ctx, QualitativeSpecies qs) {
					if (qs.isSetMaxLevel() && qs.getMaxLevel() < 0) {
					  ValidationConstraint.logError(ctx, QUAL_20313, qs, qs.getId(), Integer.toString(qs.getMaxLevel()));
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
