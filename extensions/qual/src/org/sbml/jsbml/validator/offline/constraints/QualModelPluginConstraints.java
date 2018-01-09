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
import org.sbml.jsbml.ext.qual.QualConstants;
import org.sbml.jsbml.ext.qual.QualModelPlugin;
import org.sbml.jsbml.ext.qual.QualitativeSpecies;
import org.sbml.jsbml.ext.qual.Transition;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.DuplicatedElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownElementValidationFunction;

/**
 * Defines validation rules (as {@link ValidationFunction} instances) for the {@link QualModelPlugin} class.
 * 
 * @author Nicolas Rodriguez, Lisa Falk
 * @since 1.3
 */
public class QualModelPluginConstraints extends AbstractConstraintDeclaration {

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
		    addRangeToSet(set, QUAL_10101, QUAL_20102);
		    set.add(QUAL_10301);
		    addRangeToSet(set, QUAL_20201, QUAL_20206);
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
	  ValidationFunction<QualModelPlugin> func = null;

	  switch (errorCode) {
		case QUAL_10101:
			// To conform to the QualitativeModels package specification for SBML Level 3 Version 1, an
		  // SBML document must declare the use of the following XML Namespace:
		  //  'http://www.sbml.org/sbml/level3/version1/qual/version1'.

			break;

		case QUAL_10102:
			// Wherever they appear in an SBML document, elements and attributes from the Qualitative
		  // Models package must be declared either implicitly or explicitly to be in the XML namespace
		  // 'http://www.sbml.org/sbml/level3/version1/qual/version1'. 
		  
			break;
			
		case QUAL_10301:
//		  (Extends validation rule #10301 in the SBML Level 3 Version 1 Core specification.) Within a
//		  Model the values of the attributes id and qual:id on every instance of the following classes of
//		  objects must be unique across the set of all id and qual:id attribute values of all such objects
//		  in a model: the Model itself, plus all contained FunctionDefinition, Compartment, Species,
//		  Reaction, SpeciesReference, ModifierSpeciesReference, Event, and Parameter objects, plus
//		  the QualitativeSpecies, Transition, Input and Output objects defined by the QualitativeModels
//		  package.
		  
		  break;
		 
		case QUAL_20201:
		  // There may be at most one instance of each of the following kinds of objects within a Model
		  // object using QualitativeModels: ListOfTransitions and ListOfQualitativeSpecies.

		  func = new ValidationFunction<QualModelPlugin>() {

		    @Override
		    public boolean check(ValidationContext ctx, QualModelPlugin qmp) {		      
		      Boolean onlyOneListOfTransition = new DuplicatedElementValidationFunction<QualModelPlugin>(QualConstants.listOfTransitions).check(ctx, qmp);
		      Boolean onlyOneListOfQualSpec = new DuplicatedElementValidationFunction<QualModelPlugin>(QualConstants.listOfQualitativeSpecies).check(ctx, qmp);
		      
		      return (onlyOneListOfTransition && onlyOneListOfQualSpec);
		    }
		  };
		  break;

		case QUAL_20202:
		  // The various ListOf subobjects with an Model object are optional, but if present, these
		  // container object must not be empty. Specifically, if any of the following classes of objects
		  // are present on the Model, it must not be empty: ListOfQualitativeSpecies and ListOfTransitions.

		  break;

    case QUAL_20203:
      // Apart from the general notes and annotation subobjects permitted on all SBML objects, a
      // ListOfTransitions container object may only contain Transition objects.
      
      func = new ValidationFunction<QualModelPlugin>() {

        @Override
        public boolean check(ValidationContext ctx, QualModelPlugin qmp) {
          if (qmp.isSetListOfTransitions()) {
            return new UnknownElementValidationFunction<ListOf<Transition>>().check(ctx, qmp.getListOfTransitions());
          }
          return true;
        }
      };
      break;
      
    case QUAL_20204:
      // Apart from the general notes and annotation subobjects permitted on all SBML objects, a
      // ListOfQualitativeSpecies container object may only contain QualitativeSpecies objects.
      
      func = new ValidationFunction<QualModelPlugin>() {

        @Override
        public boolean check(ValidationContext ctx, QualModelPlugin qmp) {
          if (qmp.isSetListOfQualitativeSpecies()) {
            return new UnknownElementValidationFunction<ListOf<QualitativeSpecies>>().check(ctx, qmp.getListOfQualitativeSpecies());
          }
          return true;
        }
      };
      
      break;
      
    case QUAL_20205:
      // A ListOfQualitativeSpecies object may have the optional metaid and sboTerm defined by
      // SBML Level 3 Core. No other attributes from the SBML Level 3 Core namespace or the Qualitative
      // Models namespace are permitted on a ListOfQualitativeSpecies object.
      func = new ValidationFunction<QualModelPlugin>() {

        @Override
        public boolean check(ValidationContext ctx, QualModelPlugin qmp) {
          if (qmp.isSetListOfQualitativeSpecies()) {
            return new UnknownAttributeValidationFunction<ListOf<QualitativeSpecies>>().check(ctx, qmp.getListOfQualitativeSpecies());
          }
          return true;
        }
      };
      break;

    case QUAL_20206:
      // A ListOfTransitions object may have the optional attributes metaid and sboTerm defined
      // by SBML Level 3 Core. No other attributes from the SBML Level 3 Core namespace or the
      // QualitativeModels namespace are permitted on a ListOfTransitions object.
      
      // same as QUAL_20401

      func = new ValidationFunction<QualModelPlugin>() {

        @Override
        public boolean check(ValidationContext ctx, QualModelPlugin qmp) {
          if (qmp.isSetListOfTransitions()) {
            return new UnknownAttributeValidationFunction<ListOf<Transition>>().check(ctx, qmp.getListOfTransitions());
          }
          return true;
        }
      };
      break;
		}
		return func;
	}
}
