/*
 * $Id$
 * $URL$
 *
 * 
 *==================================================================================
 * Copyright (c) 2009 The jsbml team.
 *
 * This file is part of jsbml, the pure java SBML library. Please visit
 * http://sbml.org for more information about SBML, and http://jsbml.sourceforge.net/
 * to get the latest version of jsbml.
 *
 * jsbml is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * jsbml is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with jsbml.  If not, see <http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html>.
 *
 *===================================================================================
 *
 */

package org.sbml.jsbml.io;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import org.sbml.jsbml.AlgebraicRule;
import org.sbml.jsbml.AssignmentRule;
import org.sbml.jsbml.CVTerm;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.CompartmentType;
import org.sbml.jsbml.Constraint;
import org.sbml.jsbml.Delay;
import org.sbml.jsbml.Event;
import org.sbml.jsbml.EventAssignment;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.InitialAssignment;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.ModelCreator;
import org.sbml.jsbml.ModelHistory;
import org.sbml.jsbml.ModifierSpeciesReference;
import org.sbml.jsbml.NamedSBase;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.RateRule;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.Rule;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.SpeciesType;
import org.sbml.jsbml.StoichiometryMath;
import org.sbml.jsbml.Symbol;
import org.sbml.jsbml.Trigger;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.CVTerm.Qualifier;
import org.sbml.libsbml.SBMLError;
import org.sbml.libsbml.libsbmlConstants;

/**
 * @author Andreas Dr&auml;ger <a
 *         href="mailto:andreas.draeger@uni-tuebingen.de">
 *         andreas.draeger@uni-tuebingen.de</a>
 * 
 */
public class LibSBMLReader extends AbstractSBMLReader {

	private static final String error = " must be an instance of ";

	/**
	 * Converts a libSBML SBMLError object into a jsbml SBMLException object.
	 * 
	 * @param error
	 * @return
	 */
	public static final SBMLException convert(SBMLError error) {
		SBMLException exc = new SBMLException(error.getMessage());
		if (error.getCategory() == libsbmlConstants.LIBSBML_CAT_GENERAL_CONSISTENCY)
			exc.setCategory(SBMLException.Category.GENERAL_CONSISTENCY);
		else if (error.getCategory() == libsbmlConstants.LIBSBML_CAT_IDENTIFIER_CONSISTENCY)
			exc.setCategory(SBMLException.Category.IDENTIFIER_CONSISTENCY);
		else if (error.getCategory() == libsbmlConstants.LIBSBML_CAT_INTERNAL)
			exc.setCategory(SBMLException.Category.INTERNAL);
		else if (error.getCategory() == libsbmlConstants.LIBSBML_CAT_INTERNAL_CONSISTENCY)
			exc.setCategory(SBMLException.Category.INTERNAL_CONSISTENCY);
		else if (error.getCategory() == libsbmlConstants.LIBSBML_CAT_MATHML_CONSISTENCY)
			exc.setCategory(SBMLException.Category.MATHML_CONSISTENCY);
		else if (error.getCategory() == libsbmlConstants.LIBSBML_CAT_MODELING_PRACTICE)
			exc.setCategory(SBMLException.Category.MODELING_PRACTICE);
		else if (error.getCategory() == libsbmlConstants.LIBSBML_CAT_OVERDETERMINED_MODEL)
			exc.setCategory(SBMLException.Category.OVERDETERMINED_MODEL);
		else if (error.getCategory() == libsbmlConstants.LIBSBML_CAT_SBML)
			exc.setCategory(SBMLException.Category.SBML);
		else if (error.getCategory() == libsbmlConstants.LIBSBML_CAT_SBML_L1_COMPAT)
			exc.setCategory(SBMLException.Category.SBML_L1_COMPAT);
		else if (error.getCategory() == libsbmlConstants.LIBSBML_CAT_SBML_L2V1_COMPAT)
			exc.setCategory(SBMLException.Category.SBML_L2V1_COMPAT);
		else if (error.getCategory() == libsbmlConstants.LIBSBML_CAT_SBML_L2V2_COMPAT)
			exc.setCategory(SBMLException.Category.SBML_L2V2_COMPAT);
		else if (error.getCategory() == libsbmlConstants.LIBSBML_CAT_SBML_L2V3_COMPAT)
			exc.setCategory(SBMLException.Category.SBML_L2V3_COMPAT);
		else if (error.getCategory() == libsbmlConstants.LIBSBML_CAT_SBML_L2V4_COMPAT)
			exc.setCategory(SBMLException.Category.SBML_L2V4_COMPAT);
		else if (error.getCategory() == libsbmlConstants.LIBSBML_CAT_SBO_CONSISTENCY)
			exc.setCategory(SBMLException.Category.SBO_CONSISTENCY);
		else if (error.getCategory() == libsbmlConstants.LIBSBML_CAT_SYSTEM)
			exc.setCategory(SBMLException.Category.SYSTEM);
		else if (error.getCategory() == libsbmlConstants.LIBSBML_CAT_UNITS_CONSISTENCY)
			exc.setCategory(SBMLException.Category.UNITS_CONSISTENCY);
		else if (error.getCategory() == libsbmlConstants.LIBSBML_CAT_XML)
			exc.setCategory(SBMLException.Category.XML);
		exc.setShortMessage(error.getShortMessage());
		if (error.getErrorId() == libsbmlConstants.UnknownError)
			exc.setCode(SBMLException.Code.UnknownError);
		else if (error.getErrorId() == libsbmlConstants.NotUTF8)
			exc.setCode(SBMLException.Code.NotUTF8);
		else if (error.getErrorId() == libsbmlConstants.UnrecognizedElement)
			exc.setCode(SBMLException.Code.UnrecognizedElement);
		else if (error.getErrorId() == libsbmlConstants.NotSchemaConformant)
			exc.setCode(SBMLException.Code.NotSchemaConformant);
		else if (error.getErrorId() == libsbmlConstants.InvalidMathElement)
			exc.setCode(SBMLException.Code.InvalidMathElement);
		else if (error.getErrorId() == libsbmlConstants.DisallowedMathMLSymbol)
			exc.setCode(SBMLException.Code.DisallowedMathMLSymbol);
		else if (error.getErrorId() == libsbmlConstants.DisallowedMathMLEncodingUse)
			exc.setCode(SBMLException.Code.DisallowedMathMLEncodingUse);
		else if (error.getErrorId() == libsbmlConstants.DisallowedDefinitionURLUse)
			exc.setCode(SBMLException.Code.DisallowedDefinitionURLUse);
		else if (error.getErrorId() == libsbmlConstants.BadCsymbolDefinitionURLValue)
			exc.setCode(SBMLException.Code.BadCsymbolDefinitionURLValue);
		else if (error.getErrorId() == libsbmlConstants.DisallowedMathTypeAttributeUse)
			exc.setCode(SBMLException.Code.DisallowedMathTypeAttributeUse);
		else if (error.getErrorId() == libsbmlConstants.DisallowedMathTypeAttributeValue)
			exc.setCode(SBMLException.Code.DisallowedMathTypeAttributeValue);
		else if (error.getErrorId() == libsbmlConstants.LambdaOnlyAllowedInFunctionDef)
			exc.setCode(SBMLException.Code.LambdaOnlyAllowedInFunctionDef);
		else if (error.getErrorId() == libsbmlConstants.BooleanOpsNeedBooleanArgs)
			exc.setCode(SBMLException.Code.BooleanOpsNeedBooleanArgs);
		else if (error.getErrorId() == libsbmlConstants.NumericOpsNeedNumericArgs)
			exc.setCode(SBMLException.Code.NumericOpsNeedNumericArgs);
		else if (error.getErrorId() == libsbmlConstants.ArgsToEqNeedSameType)
			exc.setCode(SBMLException.Code.ArgsToEqNeedSameType);
		else if (error.getErrorId() == libsbmlConstants.PiecewiseNeedsConsistentTypes)
			exc.setCode(SBMLException.Code.PiecewiseNeedsConsistentTypes);
		else if (error.getErrorId() == libsbmlConstants.PieceNeedsBoolean)
			exc.setCode(SBMLException.Code.PieceNeedsBoolean);
		else if (error.getErrorId() == libsbmlConstants.ApplyCiMustBeUserFunction)
			exc.setCode(SBMLException.Code.ApplyCiMustBeUserFunction);
		else if (error.getErrorId() == libsbmlConstants.ApplyCiMustBeModelComponent)
			exc.setCode(SBMLException.Code.ApplyCiMustBeModelComponent);
		else if (error.getErrorId() == libsbmlConstants.KineticLawParametersAreLocalOnly)
			exc.setCode(SBMLException.Code.KineticLawParametersAreLocalOnly);
		else if (error.getErrorId() == libsbmlConstants.MathResultMustBeNumeric)
			exc.setCode(SBMLException.Code.MathResultMustBeNumeric);
		else if (error.getErrorId() == libsbmlConstants.OpsNeedCorrectNumberOfArgs)
			exc.setCode(SBMLException.Code.OpsNeedCorrectNumberOfArgs);
		else if (error.getErrorId() == libsbmlConstants.InvalidNoArgsPassedToFunctionDef)
			exc.setCode(SBMLException.Code.InvalidNoArgsPassedToFunctionDef);
		else if (error.getErrorId() == libsbmlConstants.DuplicateComponentId)
			exc.setCode(SBMLException.Code.DuplicateComponentId);
		else if (error.getErrorId() == libsbmlConstants.DuplicateUnitDefinitionId)
			exc.setCode(SBMLException.Code.DuplicateUnitDefinitionId);
		else if (error.getErrorId() == libsbmlConstants.DuplicateLocalParameterId)
			exc.setCode(SBMLException.Code.DuplicateLocalParameterId);
		else if (error.getErrorId() == libsbmlConstants.MultipleAssignmentOrRateRules)
			exc.setCode(SBMLException.Code.MultipleAssignmentOrRateRules);
		else if (error.getErrorId() == libsbmlConstants.MultipleEventAssignmentsForId)
			exc.setCode(SBMLException.Code.MultipleEventAssignmentsForId);
		else if (error.getErrorId() == libsbmlConstants.EventAndAssignmentRuleForId)
			exc.setCode(SBMLException.Code.EventAndAssignmentRuleForId);
		else if (error.getErrorId() == libsbmlConstants.DuplicateMetaId)
			exc.setCode(SBMLException.Code.DuplicateMetaId);
		else if (error.getErrorId() == libsbmlConstants.InvalidSBOTermSyntax)
			exc.setCode(SBMLException.Code.InvalidSBOTermSyntax);
		else if (error.getErrorId() == libsbmlConstants.InvalidMetaidSyntax)
			exc.setCode(SBMLException.Code.InvalidMetaidSyntax);
		else if (error.getErrorId() == libsbmlConstants.InvalidIdSyntax)
			exc.setCode(SBMLException.Code.InvalidIdSyntax);
		else if (error.getErrorId() == libsbmlConstants.InvalidUnitIdSyntax)
			exc.setCode(SBMLException.Code.InvalidUnitIdSyntax);
		else if (error.getErrorId() == libsbmlConstants.MissingAnnotationNamespace)
			exc.setCode(SBMLException.Code.MissingAnnotationNamespace);
		else if (error.getErrorId() == libsbmlConstants.DuplicateAnnotationNamespaces)
			exc.setCode(SBMLException.Code.DuplicateAnnotationNamespaces);
		else if (error.getErrorId() == libsbmlConstants.SBMLNamespaceInAnnotation)
			exc.setCode(SBMLException.Code.SBMLNamespaceInAnnotation);
		else if (error.getErrorId() == libsbmlConstants.InconsistentArgUnits)
			exc.setCode(SBMLException.Code.InconsistentArgUnits);
		else if (error.getErrorId() == libsbmlConstants.AssignRuleCompartmentMismatch)
			exc.setCode(SBMLException.Code.AssignRuleCompartmentMismatch);
		else if (error.getErrorId() == libsbmlConstants.AssignRuleSpeciesMismatch)
			exc.setCode(SBMLException.Code.AssignRuleSpeciesMismatch);
		else if (error.getErrorId() == libsbmlConstants.AssignRuleParameterMismatch)
			exc.setCode(SBMLException.Code.AssignRuleParameterMismatch);
		else if (error.getErrorId() == libsbmlConstants.InitAssignCompartmenMismatch)
			exc.setCode(SBMLException.Code.InitAssignCompartmenMismatch);
		else if (error.getErrorId() == libsbmlConstants.InitAssignSpeciesMismatch)
			exc.setCode(SBMLException.Code.InitAssignSpeciesMismatch);
		else if (error.getErrorId() == libsbmlConstants.InitAssignParameterMismatch)
			exc.setCode(SBMLException.Code.InitAssignParameterMismatch);
		else if (error.getErrorId() == libsbmlConstants.RateRuleCompartmentMismatch)
			exc.setCode(SBMLException.Code.RateRuleCompartmentMismatch);
		else if (error.getErrorId() == libsbmlConstants.RateRuleSpeciesMismatch)
			exc.setCode(SBMLException.Code.RateRuleSpeciesMismatch);
		else if (error.getErrorId() == libsbmlConstants.RateRuleParameterMismatch)
			exc.setCode(SBMLException.Code.RateRuleParameterMismatch);
		else if (error.getErrorId() == libsbmlConstants.KineticLawNotSubstancePerTime)
			exc.setCode(SBMLException.Code.KineticLawNotSubstancePerTime);
		else if (error.getErrorId() == libsbmlConstants.DelayUnitsNotTime)
			exc.setCode(SBMLException.Code.DelayUnitsNotTime);
		else if (error.getErrorId() == libsbmlConstants.EventAssignCompartmentMismatch)
			exc.setCode(SBMLException.Code.EventAssignCompartmentMismatch);
		else if (error.getErrorId() == libsbmlConstants.EventAssignSpeciesMismatch)
			exc.setCode(SBMLException.Code.EventAssignSpeciesMismatch);
		else if (error.getErrorId() == libsbmlConstants.EventAssignParameterMismatch)
			exc.setCode(SBMLException.Code.EventAssignParameterMismatch);
		else if (error.getErrorId() == libsbmlConstants.OverdeterminedSystem)
			exc.setCode(SBMLException.Code.OverdeterminedSystem);
		else if (error.getErrorId() == libsbmlConstants.InvalidModelSBOTerm)
			exc.setCode(SBMLException.Code.InvalidModelSBOTerm);
		else if (error.getErrorId() == libsbmlConstants.InvalidFunctionDefSBOTerm)
			exc.setCode(SBMLException.Code.InvalidFunctionDefSBOTerm);
		else if (error.getErrorId() == libsbmlConstants.InvalidParameterSBOTerm)
			exc.setCode(SBMLException.Code.InvalidParameterSBOTerm);
		else if (error.getErrorId() == libsbmlConstants.InvalidInitAssignSBOTerm)
			exc.setCode(SBMLException.Code.InvalidInitAssignSBOTerm);
		else if (error.getErrorId() == libsbmlConstants.InvalidRuleSBOTerm)
			exc.setCode(SBMLException.Code.InvalidRuleSBOTerm);
		else if (error.getErrorId() == libsbmlConstants.InvalidConstraintSBOTerm)
			exc.setCode(SBMLException.Code.InvalidConstraintSBOTerm);
		else if (error.getErrorId() == libsbmlConstants.InvalidReactionSBOTerm)
			exc.setCode(SBMLException.Code.InvalidReactionSBOTerm);
		else if (error.getErrorId() == libsbmlConstants.InvalidSpeciesReferenceSBOTerm)
			exc.setCode(SBMLException.Code.InvalidSpeciesReferenceSBOTerm);
		else if (error.getErrorId() == libsbmlConstants.InvalidKineticLawSBOTerm)
			exc.setCode(SBMLException.Code.InvalidKineticLawSBOTerm);
		else if (error.getErrorId() == libsbmlConstants.InvalidEventSBOTerm)
			exc.setCode(SBMLException.Code.InvalidEventSBOTerm);
		else if (error.getErrorId() == libsbmlConstants.InvalidEventAssignmentSBOTerm)
			exc.setCode(SBMLException.Code.InvalidEventAssignmentSBOTerm);
		else if (error.getErrorId() == libsbmlConstants.InvalidCompartmentSBOTerm)
			exc.setCode(SBMLException.Code.InvalidCompartmentSBOTerm);
		else if (error.getErrorId() == libsbmlConstants.InvalidSpeciesSBOTerm)
			exc.setCode(SBMLException.Code.InvalidSpeciesSBOTerm);
		else if (error.getErrorId() == libsbmlConstants.InvalidCompartmentTypeSBOTerm)
			exc.setCode(SBMLException.Code.InvalidCompartmentTypeSBOTerm);
		else if (error.getErrorId() == libsbmlConstants.InvalidSpeciesTypeSBOTerm)
			exc.setCode(SBMLException.Code.InvalidSpeciesTypeSBOTerm);
		else if (error.getErrorId() == libsbmlConstants.InvalidTriggerSBOTerm)
			exc.setCode(SBMLException.Code.InvalidTriggerSBOTerm);
		else if (error.getErrorId() == libsbmlConstants.InvalidDelaySBOTerm)
			exc.setCode(SBMLException.Code.InvalidDelaySBOTerm);
		else if (error.getErrorId() == libsbmlConstants.NotesNotInXHTMLNamespace)
			exc.setCode(SBMLException.Code.NotesNotInXHTMLNamespace);
		else if (error.getErrorId() == libsbmlConstants.NotesContainsXMLDecl)
			exc.setCode(SBMLException.Code.NotesContainsXMLDecl);
		else if (error.getErrorId() == libsbmlConstants.NotesContainsDOCTYPE)
			exc.setCode(SBMLException.Code.NotesContainsDOCTYPE);
		else if (error.getErrorId() == libsbmlConstants.InvalidNotesContent)
			exc.setCode(SBMLException.Code.InvalidNotesContent);
		else if (error.getErrorId() == libsbmlConstants.InvalidNamespaceOnSBML)
			exc.setCode(SBMLException.Code.InvalidNamespaceOnSBML);
		else if (error.getErrorId() == libsbmlConstants.MissingOrInconsistentLevel)
			exc.setCode(SBMLException.Code.MissingOrInconsistentLevel);
		else if (error.getErrorId() == libsbmlConstants.MissingOrInconsistentVersion)
			exc.setCode(SBMLException.Code.MissingOrInconsistentVersion);
		else if (error.getErrorId() == libsbmlConstants.AnnotationNotesNotAllowedLevel1)
			exc.setCode(SBMLException.Code.AnnotationNotesNotAllowedLevel1);
		else if (error.getErrorId() == libsbmlConstants.MissingModel)
			exc.setCode(SBMLException.Code.MissingModel);
		else if (error.getErrorId() == libsbmlConstants.IncorrectOrderInModel)
			exc.setCode(SBMLException.Code.IncorrectOrderInModel);
		else if (error.getErrorId() == libsbmlConstants.EmptyListElement)
			exc.setCode(SBMLException.Code.EmptyListElement);
		else if (error.getErrorId() == libsbmlConstants.NeedCompartmentIfHaveSpecies)
			exc.setCode(SBMLException.Code.NeedCompartmentIfHaveSpecies);
		else if (error.getErrorId() == libsbmlConstants.FunctionDefMathNotLambda)
			exc.setCode(SBMLException.Code.FunctionDefMathNotLambda);
		else if (error.getErrorId() == libsbmlConstants.InvalidApplyCiInLambda)
			exc.setCode(SBMLException.Code.InvalidApplyCiInLambda);
		else if (error.getErrorId() == libsbmlConstants.RecursiveFunctionDefinition)
			exc.setCode(SBMLException.Code.RecursiveFunctionDefinition);
		else if (error.getErrorId() == libsbmlConstants.InvalidCiInLambda)
			exc.setCode(SBMLException.Code.InvalidCiInLambda);
		else if (error.getErrorId() == libsbmlConstants.InvalidFunctionDefReturnType)
			exc.setCode(SBMLException.Code.InvalidFunctionDefReturnType);
		else if (error.getErrorId() == libsbmlConstants.InvalidUnitDefId)
			exc.setCode(SBMLException.Code.InvalidUnitDefId);
		else if (error.getErrorId() == libsbmlConstants.InvalidSubstanceRedefinition)
			exc.setCode(SBMLException.Code.InvalidSubstanceRedefinition);
		else if (error.getErrorId() == libsbmlConstants.InvalidLengthRedefinition)
			exc.setCode(SBMLException.Code.InvalidLengthRedefinition);
		else if (error.getErrorId() == libsbmlConstants.InvalidAreaRedefinition)
			exc.setCode(SBMLException.Code.InvalidAreaRedefinition);
		else if (error.getErrorId() == libsbmlConstants.InvalidTimeRedefinition)
			exc.setCode(SBMLException.Code.InvalidTimeRedefinition);
		else if (error.getErrorId() == libsbmlConstants.InvalidVolumeRedefinition)
			exc.setCode(SBMLException.Code.InvalidVolumeRedefinition);
		else if (error.getErrorId() == libsbmlConstants.VolumeLitreDefExponentNotOne)
			exc.setCode(SBMLException.Code.VolumeLitreDefExponentNotOne);
		else if (error.getErrorId() == libsbmlConstants.VolumeMetreDefExponentNot3)
			exc.setCode(SBMLException.Code.VolumeMetreDefExponentNot3);
		else if (error.getErrorId() == libsbmlConstants.EmptyListOfUnits)
			exc.setCode(SBMLException.Code.EmptyListOfUnits);
		else if (error.getErrorId() == libsbmlConstants.InvalidUnitKind)
			exc.setCode(SBMLException.Code.InvalidUnitKind);
		else if (error.getErrorId() == libsbmlConstants.OffsetNoLongerValid)
			exc.setCode(SBMLException.Code.OffsetNoLongerValid);
		else if (error.getErrorId() == libsbmlConstants.CelsiusNoLongerValid)
			exc.setCode(SBMLException.Code.CelsiusNoLongerValid);
		else if (error.getErrorId() == libsbmlConstants.ZeroDimensionalCompartmentSize)
			exc.setCode(SBMLException.Code.ZeroDimensionalCompartmentSize);
		else if (error.getErrorId() == libsbmlConstants.ZeroDimensionalCompartmentUnits)
			exc.setCode(SBMLException.Code.ZeroDimensionalCompartmentUnits);
		else if (error.getErrorId() == libsbmlConstants.ZeroDimensionalCompartmentConst)
			exc.setCode(SBMLException.Code.ZeroDimensionalCompartmentConst);
		else if (error.getErrorId() == libsbmlConstants.UndefinedOutsideCompartment)
			exc.setCode(SBMLException.Code.UndefinedOutsideCompartment);
		else if (error.getErrorId() == libsbmlConstants.RecursiveCompartmentContainment)
			exc.setCode(SBMLException.Code.RecursiveCompartmentContainment);
		else if (error.getErrorId() == libsbmlConstants.ZeroDCompartmentContainment)
			exc.setCode(SBMLException.Code.ZeroDCompartmentContainment);
		else if (error.getErrorId() == libsbmlConstants.Invalid1DCompartmentUnits)
			exc.setCode(SBMLException.Code.Invalid1DCompartmentUnits);
		else if (error.getErrorId() == libsbmlConstants.Invalid2DCompartmentUnits)
			exc.setCode(SBMLException.Code.Invalid2DCompartmentUnits);
		else if (error.getErrorId() == libsbmlConstants.Invalid3DCompartmentUnits)
			exc.setCode(SBMLException.Code.Invalid3DCompartmentUnits);
		else if (error.getErrorId() == libsbmlConstants.InvalidCompartmentTypeRef)
			exc.setCode(SBMLException.Code.InvalidCompartmentTypeRef);
		else if (error.getErrorId() == libsbmlConstants.InvalidSpeciesCompartmentRef)
			exc.setCode(SBMLException.Code.InvalidSpeciesCompartmentRef);
		else if (error.getErrorId() == libsbmlConstants.HasOnlySubsNoSpatialUnits)
			exc.setCode(SBMLException.Code.HasOnlySubsNoSpatialUnits);
		else if (error.getErrorId() == libsbmlConstants.NoSpatialUnitsInZeroD)
			exc.setCode(SBMLException.Code.NoSpatialUnitsInZeroD);
		else if (error.getErrorId() == libsbmlConstants.NoConcentrationInZeroD)
			exc.setCode(SBMLException.Code.NoConcentrationInZeroD);
		else if (error.getErrorId() == libsbmlConstants.SpatialUnitsInOneD)
			exc.setCode(SBMLException.Code.SpatialUnitsInOneD);
		else if (error.getErrorId() == libsbmlConstants.SpatialUnitsInTwoD)
			exc.setCode(SBMLException.Code.SpatialUnitsInTwoD);
		else if (error.getErrorId() == libsbmlConstants.SpatialUnitsInThreeD)
			exc.setCode(SBMLException.Code.SpatialUnitsInThreeD);
		else if (error.getErrorId() == libsbmlConstants.InvalidSpeciesSusbstanceUnits)
			exc.setCode(SBMLException.Code.InvalidSpeciesSusbstanceUnits);
		else if (error.getErrorId() == libsbmlConstants.BothAmountAndConcentrationSet)
			exc.setCode(SBMLException.Code.BothAmountAndConcentrationSet);
		else if (error.getErrorId() == libsbmlConstants.NonBoundarySpeciesAssignedAndUsed)
			exc.setCode(SBMLException.Code.NonBoundarySpeciesAssignedAndUsed);
		else if (error.getErrorId() == libsbmlConstants.NonConstantSpeciesUsed)
			exc.setCode(SBMLException.Code.NonConstantSpeciesUsed);
		else if (error.getErrorId() == libsbmlConstants.InvalidSpeciesTypeRef)
			exc.setCode(SBMLException.Code.InvalidSpeciesTypeRef);
		else if (error.getErrorId() == libsbmlConstants.MultSpeciesSameTypeInCompartment)
			exc.setCode(SBMLException.Code.MultSpeciesSameTypeInCompartment);
		else if (error.getErrorId() == libsbmlConstants.MissingSpeciesCompartment)
			exc.setCode(SBMLException.Code.MissingSpeciesCompartment);
		else if (error.getErrorId() == libsbmlConstants.SpatialSizeUnitsRemoved)
			exc.setCode(SBMLException.Code.SpatialSizeUnitsRemoved);
		else if (error.getErrorId() == libsbmlConstants.InvalidParameterUnits)
			exc.setCode(SBMLException.Code.InvalidParameterUnits);
		else if (error.getErrorId() == libsbmlConstants.InvalidInitAssignSymbol)
			exc.setCode(SBMLException.Code.InvalidInitAssignSymbol);
		else if (error.getErrorId() == libsbmlConstants.MultipleInitAssignments)
			exc.setCode(SBMLException.Code.MultipleInitAssignments);
		else if (error.getErrorId() == libsbmlConstants.InitAssignmentAndRuleForSameId)
			exc.setCode(SBMLException.Code.InitAssignmentAndRuleForSameId);
		else if (error.getErrorId() == libsbmlConstants.InvalidAssignRuleVariable)
			exc.setCode(SBMLException.Code.InvalidAssignRuleVariable);
		else if (error.getErrorId() == libsbmlConstants.InvalidRateRuleVariable)
			exc.setCode(SBMLException.Code.InvalidRateRuleVariable);
		else if (error.getErrorId() == libsbmlConstants.AssignmentToConstantEntity)
			exc.setCode(SBMLException.Code.AssignmentToConstantEntity);
		else if (error.getErrorId() == libsbmlConstants.RateRuleForConstantEntity)
			exc.setCode(SBMLException.Code.RateRuleForConstantEntity);
		else if (error.getErrorId() == libsbmlConstants.CircularRuleDependency)
			exc.setCode(SBMLException.Code.CircularRuleDependency);
		else if (error.getErrorId() == libsbmlConstants.ConstraintMathNotBoolean)
			exc.setCode(SBMLException.Code.ConstraintMathNotBoolean);
		else if (error.getErrorId() == libsbmlConstants.IncorrectOrderInConstraint)
			exc.setCode(SBMLException.Code.IncorrectOrderInConstraint);
		else if (error.getErrorId() == libsbmlConstants.ConstraintNotInXHTMLNamespace)
			exc.setCode(SBMLException.Code.ConstraintNotInXHTMLNamespace);
		else if (error.getErrorId() == libsbmlConstants.ConstraintContainsXMLDecl)
			exc.setCode(SBMLException.Code.ConstraintContainsXMLDecl);
		else if (error.getErrorId() == libsbmlConstants.ConstraintContainsDOCTYPE)
			exc.setCode(SBMLException.Code.ConstraintContainsDOCTYPE);
		else if (error.getErrorId() == libsbmlConstants.InvalidConstraintContent)
			exc.setCode(SBMLException.Code.InvalidConstraintContent);
		else if (error.getErrorId() == libsbmlConstants.NoReactantsOrProducts)
			exc.setCode(SBMLException.Code.NoReactantsOrProducts);
		else if (error.getErrorId() == libsbmlConstants.IncorrectOrderInReaction)
			exc.setCode(SBMLException.Code.IncorrectOrderInReaction);
		else if (error.getErrorId() == libsbmlConstants.EmptyListInReaction)
			exc.setCode(SBMLException.Code.EmptyListInReaction);
		else if (error.getErrorId() == libsbmlConstants.InvalidReactantsProductsList)
			exc.setCode(SBMLException.Code.InvalidReactantsProductsList);
		else if (error.getErrorId() == libsbmlConstants.InvalidModifiersList)
			exc.setCode(SBMLException.Code.InvalidModifiersList);
		else if (error.getErrorId() == libsbmlConstants.InvalidSpeciesReference)
			exc.setCode(SBMLException.Code.InvalidSpeciesReference);
		else if (error.getErrorId() == libsbmlConstants.BothStoichiometryAndMath)
			exc.setCode(SBMLException.Code.BothStoichiometryAndMath);
		else if (error.getErrorId() == libsbmlConstants.UndeclaredSpeciesRef)
			exc.setCode(SBMLException.Code.UndeclaredSpeciesRef);
		else if (error.getErrorId() == libsbmlConstants.IncorrectOrderInKineticLaw)
			exc.setCode(SBMLException.Code.IncorrectOrderInKineticLaw);
		else if (error.getErrorId() == libsbmlConstants.EmptyListInKineticLaw)
			exc.setCode(SBMLException.Code.EmptyListInKineticLaw);
		else if (error.getErrorId() == libsbmlConstants.NonConstantLocalParameter)
			exc.setCode(SBMLException.Code.NonConstantLocalParameter);
		else if (error.getErrorId() == libsbmlConstants.SubsUnitsNoLongerValid)
			exc.setCode(SBMLException.Code.SubsUnitsNoLongerValid);
		else if (error.getErrorId() == libsbmlConstants.TimeUnitsNoLongerValid)
			exc.setCode(SBMLException.Code.TimeUnitsNoLongerValid);
		else if (error.getErrorId() == libsbmlConstants.UndeclaredSpeciesInStoichMath)
			exc.setCode(SBMLException.Code.UndeclaredSpeciesInStoichMath);
		else if (error.getErrorId() == libsbmlConstants.MissingTriggerInEvent)
			exc.setCode(SBMLException.Code.MissingTriggerInEvent);
		else if (error.getErrorId() == libsbmlConstants.TriggerMathNotBoolean)
			exc.setCode(SBMLException.Code.TriggerMathNotBoolean);
		else if (error.getErrorId() == libsbmlConstants.MissingEventAssignment)
			exc.setCode(SBMLException.Code.MissingEventAssignment);
		else if (error.getErrorId() == libsbmlConstants.TimeUnitsEvent)
			exc.setCode(SBMLException.Code.TimeUnitsEvent);
		else if (error.getErrorId() == libsbmlConstants.IncorrectOrderInEvent)
			exc.setCode(SBMLException.Code.IncorrectOrderInEvent);
		else if (error.getErrorId() == libsbmlConstants.ValuesFromTriggerTimeNeedDelay)
			exc.setCode(SBMLException.Code.ValuesFromTriggerTimeNeedDelay);
		else if (error.getErrorId() == libsbmlConstants.InvalidEventAssignmentVariable)
			exc.setCode(SBMLException.Code.InvalidEventAssignmentVariable);
		else if (error.getErrorId() == libsbmlConstants.EventAssignmentForConstantEntity)
			exc.setCode(SBMLException.Code.EventAssignmentForConstantEntity);
		else if (error.getErrorId() == libsbmlConstants.CompartmentShouldHaveSize)
			exc.setCode(SBMLException.Code.CompartmentShouldHaveSize);
		else if (error.getErrorId() == libsbmlConstants.ParameterShouldHaveUnits)
			exc.setCode(SBMLException.Code.ParameterShouldHaveUnits);
		else if (error.getErrorId() == libsbmlConstants.LocalParameterShadowsId)
			exc.setCode(SBMLException.Code.LocalParameterShadowsId);
		else if (error.getErrorId() == libsbmlConstants.CannotConvertToL1V1)
			exc.setCode(SBMLException.Code.CannotConvertToL1V1);
		else if (error.getErrorId() == libsbmlConstants.NoEventsInL1)
			exc.setCode(SBMLException.Code.NoEventsInL1);
		else if (error.getErrorId() == libsbmlConstants.NoFunctionDefinitionsInL1)
			exc.setCode(SBMLException.Code.NoFunctionDefinitionsInL1);
		else if (error.getErrorId() == libsbmlConstants.NoConstraintsInL1)
			exc.setCode(SBMLException.Code.NoConstraintsInL1);
		else if (error.getErrorId() == libsbmlConstants.NoInitialAssignmentsInL1)
			exc.setCode(SBMLException.Code.NoInitialAssignmentsInL1);
		else if (error.getErrorId() == libsbmlConstants.NoSpeciesTypesInL1)
			exc.setCode(SBMLException.Code.NoSpeciesTypesInL1);
		else if (error.getErrorId() == libsbmlConstants.NoCompartmentTypeInL1)
			exc.setCode(SBMLException.Code.NoCompartmentTypeInL1);
		else if (error.getErrorId() == libsbmlConstants.NoNon3DComparmentsInL1)
			exc.setCode(SBMLException.Code.NoNon3DComparmentsInL1);
		else if (error.getErrorId() == libsbmlConstants.NoFancyStoichiometryMathInL1)
			exc.setCode(SBMLException.Code.NoFancyStoichiometryMathInL1);
		else if (error.getErrorId() == libsbmlConstants.NoNonIntegerStoichiometryInL1)
			exc.setCode(SBMLException.Code.NoNonIntegerStoichiometryInL1);
		else if (error.getErrorId() == libsbmlConstants.NoUnitMultipliersOrOffsetsInL1)
			exc.setCode(SBMLException.Code.NoUnitMultipliersOrOffsetsInL1);
		else if (error.getErrorId() == libsbmlConstants.SpeciesCompartmentRequiredInL1)
			exc.setCode(SBMLException.Code.SpeciesCompartmentRequiredInL1);
		else if (error.getErrorId() == libsbmlConstants.NoSpeciesSpatialSizeUnitsInL1)
			exc.setCode(SBMLException.Code.NoSpeciesSpatialSizeUnitsInL1);
		else if (error.getErrorId() == libsbmlConstants.NoSBOTermsInL1)
			exc.setCode(SBMLException.Code.NoSBOTermsInL1);
		else if (error.getErrorId() == libsbmlConstants.StrictUnitsRequiredInL1)
			exc.setCode(SBMLException.Code.StrictUnitsRequiredInL1);
		else if (error.getErrorId() == libsbmlConstants.NoConstraintsInL2v1)
			exc.setCode(SBMLException.Code.NoConstraintsInL2v1);
		else if (error.getErrorId() == libsbmlConstants.NoInitialAssignmentsInL2v1)
			exc.setCode(SBMLException.Code.NoInitialAssignmentsInL2v1);
		else if (error.getErrorId() == libsbmlConstants.NoSpeciesTypeInL2v1)
			exc.setCode(SBMLException.Code.NoSpeciesTypeInL2v1);
		else if (error.getErrorId() == libsbmlConstants.NoCompartmentTypeInL2v1)
			exc.setCode(SBMLException.Code.NoCompartmentTypeInL2v1);
		else if (error.getErrorId() == libsbmlConstants.NoSBOTermsInL2v1)
			exc.setCode(SBMLException.Code.NoSBOTermsInL2v1);
		else if (error.getErrorId() == libsbmlConstants.NoIdOnSpeciesReferenceInL2v1)
			exc.setCode(SBMLException.Code.NoIdOnSpeciesReferenceInL2v1);
		else if (error.getErrorId() == libsbmlConstants.NoDelayedEventAssignmentInL2v1)
			exc.setCode(SBMLException.Code.NoDelayedEventAssignmentInL2v1);
		else if (error.getErrorId() == libsbmlConstants.StrictUnitsRequiredInL2v1)
			exc.setCode(SBMLException.Code.StrictUnitsRequiredInL2v1);
		else if (error.getErrorId() == libsbmlConstants.SBOTermNotUniversalInL2v2)
			exc.setCode(SBMLException.Code.SBOTermNotUniversalInL2v2);
		else if (error.getErrorId() == libsbmlConstants.NoUnitOffsetInL2v2)
			exc.setCode(SBMLException.Code.NoUnitOffsetInL2v2);
		else if (error.getErrorId() == libsbmlConstants.NoKineticLawTimeUnitsInL2v2)
			exc.setCode(SBMLException.Code.NoKineticLawTimeUnitsInL2v2);
		else if (error.getErrorId() == libsbmlConstants.NoKineticLawSubstanceUnitsInL2v2)
			exc.setCode(SBMLException.Code.NoKineticLawSubstanceUnitsInL2v2);
		else if (error.getErrorId() == libsbmlConstants.NoDelayedEventAssignmentInL2v2)
			exc.setCode(SBMLException.Code.NoDelayedEventAssignmentInL2v2);
		else if (error.getErrorId() == libsbmlConstants.ModelSBOBranchChangedBeyondL2v2)
			exc.setCode(SBMLException.Code.ModelSBOBranchChangedBeyondL2v2);
		else if (error.getErrorId() == libsbmlConstants.StrictUnitsRequiredInL2v2)
			exc.setCode(SBMLException.Code.StrictUnitsRequiredInL2v2);
		else if (error.getErrorId() == libsbmlConstants.StrictSBORequiredInL2v2)
			exc.setCode(SBMLException.Code.StrictSBORequiredInL2v2);
		else if (error.getErrorId() == libsbmlConstants.DuplicateAnnotationInvalidInL2v2)
			exc.setCode(SBMLException.Code.DuplicateAnnotationInvalidInL2v2);
		else if (error.getErrorId() == libsbmlConstants.NoUnitOffsetInL2v3)
			exc.setCode(SBMLException.Code.NoUnitOffsetInL2v3);
		else if (error.getErrorId() == libsbmlConstants.NoKineticLawTimeUnitsInL2v3)
			exc.setCode(SBMLException.Code.NoKineticLawTimeUnitsInL2v3);
		else if (error.getErrorId() == libsbmlConstants.NoKineticLawSubstanceUnitsInL2v3)
			exc.setCode(SBMLException.Code.NoKineticLawSubstanceUnitsInL2v3);
		else if (error.getErrorId() == libsbmlConstants.NoSpeciesSpatialSizeUnitsInL2v3)
			exc.setCode(SBMLException.Code.NoSpeciesSpatialSizeUnitsInL2v3);
		else if (error.getErrorId() == libsbmlConstants.NoEventTimeUnitsInL2v3)
			exc.setCode(SBMLException.Code.NoEventTimeUnitsInL2v3);
		else if (error.getErrorId() == libsbmlConstants.NoDelayedEventAssignmentInL2v3)
			exc.setCode(SBMLException.Code.NoDelayedEventAssignmentInL2v3);
		else if (error.getErrorId() == libsbmlConstants.ModelSBOBranchChangedBeyondL2v3)
			exc.setCode(SBMLException.Code.ModelSBOBranchChangedBeyondL2v3);
		else if (error.getErrorId() == libsbmlConstants.StrictUnitsRequiredInL2v3)
			exc.setCode(SBMLException.Code.StrictUnitsRequiredInL2v3);
		else if (error.getErrorId() == libsbmlConstants.StrictSBORequiredInL2v3)
			exc.setCode(SBMLException.Code.StrictSBORequiredInL2v3);
		else if (error.getErrorId() == libsbmlConstants.DuplicateAnnotationInvalidInL2v3)
			exc.setCode(SBMLException.Code.DuplicateAnnotationInvalidInL2v3);
		else if (error.getErrorId() == libsbmlConstants.NoUnitOffsetInL2v4)
			exc.setCode(SBMLException.Code.NoUnitOffsetInL2v4);
		else if (error.getErrorId() == libsbmlConstants.NoKineticLawTimeUnitsInL2v4)
			exc.setCode(SBMLException.Code.NoKineticLawTimeUnitsInL2v4);
		else if (error.getErrorId() == libsbmlConstants.NoKineticLawSubstanceUnitsInL2v4)
			exc.setCode(SBMLException.Code.NoKineticLawSubstanceUnitsInL2v4);
		else if (error.getErrorId() == libsbmlConstants.NoSpeciesSpatialSizeUnitsInL2v4)
			exc.setCode(SBMLException.Code.NoSpeciesSpatialSizeUnitsInL2v4);
		else if (error.getErrorId() == libsbmlConstants.NoEventTimeUnitsInL2v4)
			exc.setCode(SBMLException.Code.NoEventTimeUnitsInL2v4);
		else if (error.getErrorId() == libsbmlConstants.ModelSBOBranchChangedInL2v4)
			exc.setCode(SBMLException.Code.ModelSBOBranchChangedInL2v4);
		else if (error.getErrorId() == libsbmlConstants.DuplicateAnnotationInvalidInL2v4)
			exc.setCode(SBMLException.Code.DuplicateAnnotationInvalidInL2v4);
		else if (error.getErrorId() == libsbmlConstants.InvalidSBMLLevelVersion)
			exc.setCode(SBMLException.Code.InvalidSBMLLevelVersion);
		else if (error.getErrorId() == libsbmlConstants.InvalidRuleOrdering)
			exc.setCode(SBMLException.Code.InvalidRuleOrdering);
		else if (error.getErrorId() == libsbmlConstants.SubsUnitsAllowedInKL)
			exc.setCode(SBMLException.Code.SubsUnitsAllowedInKL);
		else if (error.getErrorId() == libsbmlConstants.TimeUnitsAllowedInKL)
			exc.setCode(SBMLException.Code.TimeUnitsAllowedInKL);
		else if (error.getErrorId() == libsbmlConstants.FormulaInLevel1KL)
			exc.setCode(SBMLException.Code.FormulaInLevel1KL);
		else if (error.getErrorId() == libsbmlConstants.TimeUnitsRemoved)
			exc.setCode(SBMLException.Code.TimeUnitsRemoved);
		else if (error.getErrorId() == libsbmlConstants.BadMathML)
			exc.setCode(SBMLException.Code.BadMathML);
		else if (error.getErrorId() == libsbmlConstants.FailedMathMLReadOfDouble)
			exc.setCode(SBMLException.Code.FailedMathMLReadOfDouble);
		else if (error.getErrorId() == libsbmlConstants.FailedMathMLReadOfInteger)
			exc.setCode(SBMLException.Code.FailedMathMLReadOfInteger);
		else if (error.getErrorId() == libsbmlConstants.FailedMathMLReadOfExponential)
			exc.setCode(SBMLException.Code.FailedMathMLReadOfExponential);
		else if (error.getErrorId() == libsbmlConstants.FailedMathMLReadOfRational)
			exc.setCode(SBMLException.Code.FailedMathMLReadOfRational);
		else if (error.getErrorId() == libsbmlConstants.BadMathMLNodeType)
			exc.setCode(SBMLException.Code.BadMathMLNodeType);
		else if (error.getErrorId() == libsbmlConstants.NoTimeSymbolInFunctionDef)
			exc.setCode(SBMLException.Code.NoTimeSymbolInFunctionDef);
		else if (error.getErrorId() == libsbmlConstants.UndeclaredUnits)
			exc.setCode(SBMLException.Code.UndeclaredUnits);
		else if (error.getErrorId() == libsbmlConstants.UnrecognisedSBOTerm)
			exc.setCode(SBMLException.Code.UnrecognisedSBOTerm);
		else if (error.getErrorId() == libsbmlConstants.ObseleteSBOTerm)
			exc.setCode(SBMLException.Code.ObseleteSBOTerm);
		else if (error.getErrorId() == libsbmlConstants.IncorrectCompartmentSpatialDimensions)
			exc
					.setCode(SBMLException.Code.IncorrectCompartmentSpatialDimensions);
		else if (error.getErrorId() == libsbmlConstants.CompartmentTypeNotValidAttribute)
			exc.setCode(SBMLException.Code.CompartmentTypeNotValidAttribute);
		else if (error.getErrorId() == libsbmlConstants.ConstantNotValidAttribute)
			exc.setCode(SBMLException.Code.ConstantNotValidAttribute);
		else if (error.getErrorId() == libsbmlConstants.MetaIdNotValidAttribute)
			exc.setCode(SBMLException.Code.MetaIdNotValidAttribute);
		else if (error.getErrorId() == libsbmlConstants.SBOTermNotValidAttributeBeforeL2V3)
			exc.setCode(SBMLException.Code.SBOTermNotValidAttributeBeforeL2V3);
		else if (error.getErrorId() == libsbmlConstants.InvalidL1CompartmentUnits)
			exc.setCode(SBMLException.Code.InvalidL1CompartmentUnits);
		else if (error.getErrorId() == libsbmlConstants.L1V1CompartmentVolumeReqd)
			exc.setCode(SBMLException.Code.L1V1CompartmentVolumeReqd);
		else if (error.getErrorId() == libsbmlConstants.CompartmentTypeNotValidComponent)
			exc.setCode(SBMLException.Code.CompartmentTypeNotValidComponent);
		else if (error.getErrorId() == libsbmlConstants.ConstraintNotValidComponent)
			exc.setCode(SBMLException.Code.ConstraintNotValidComponent);
		else if (error.getErrorId() == libsbmlConstants.EventNotValidComponent)
			exc.setCode(SBMLException.Code.EventNotValidComponent);
		else if (error.getErrorId() == libsbmlConstants.SBOTermNotValidAttributeBeforeL2V2)
			exc.setCode(SBMLException.Code.SBOTermNotValidAttributeBeforeL2V2);
		else if (error.getErrorId() == libsbmlConstants.FuncDefNotValidComponent)
			exc.setCode(SBMLException.Code.FuncDefNotValidComponent);
		else if (error.getErrorId() == libsbmlConstants.InitialAssignNotValidComponent)
			exc.setCode(SBMLException.Code.InitialAssignNotValidComponent);
		else if (error.getErrorId() == libsbmlConstants.VariableNotValidAttribute)
			exc.setCode(SBMLException.Code.VariableNotValidAttribute);
		else if (error.getErrorId() == libsbmlConstants.UnitsNotValidAttribute)
			exc.setCode(SBMLException.Code.UnitsNotValidAttribute);
		else if (error.getErrorId() == libsbmlConstants.ConstantSpeciesNotValidAttribute)
			exc.setCode(SBMLException.Code.ConstantSpeciesNotValidAttribute);
		else if (error.getErrorId() == libsbmlConstants.SpatialSizeUnitsNotValidAttribute)
			exc.setCode(SBMLException.Code.SpatialSizeUnitsNotValidAttribute);
		else if (error.getErrorId() == libsbmlConstants.SpeciesTypeNotValidAttribute)
			exc.setCode(SBMLException.Code.SpeciesTypeNotValidAttribute);
		else if (error.getErrorId() == libsbmlConstants.HasOnlySubsUnitsNotValidAttribute)
			exc.setCode(SBMLException.Code.HasOnlySubsUnitsNotValidAttribute);
		else if (error.getErrorId() == libsbmlConstants.IdNotValidAttribute)
			exc.setCode(SBMLException.Code.IdNotValidAttribute);
		else if (error.getErrorId() == libsbmlConstants.NameNotValidAttribute)
			exc.setCode(SBMLException.Code.NameNotValidAttribute);
		else if (error.getErrorId() == libsbmlConstants.SpeciesTypeNotValidComponent)
			exc.setCode(SBMLException.Code.SpeciesTypeNotValidComponent);
		else if (error.getErrorId() == libsbmlConstants.StoichiometryMathNotValidComponent)
			exc.setCode(SBMLException.Code.StoichiometryMathNotValidComponent);
		else if (error.getErrorId() == libsbmlConstants.MultiplierNotValidAttribute)
			exc.setCode(SBMLException.Code.MultiplierNotValidAttribute);
		else if (error.getErrorId() == libsbmlConstants.OffsetNotValidAttribute)
			exc.setCode(SBMLException.Code.OffsetNotValidAttribute);

		exc.setFatal(error.isFatal());
		exc.setError(error.isError());
		exc.setInfo(error.isInfo());
		exc.setInternal(error.isInternal());
		exc.setSystem(error.isSystem());
		exc.setWarning(error.isWarning());
		exc.setXML(error.isXML());

		return exc;
	}

	private org.sbml.libsbml.Model originalModel;

	private Set<org.sbml.libsbml.SBMLDocument> setOfDocuments;

	public LibSBMLReader() {
		setOfDocuments = new HashSet<org.sbml.libsbml.SBMLDocument>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jlibsbml.SBMLReader#convertDate(java.lang.Object)
	 */
	public Date convertDate(Object date) {
		if (!(date instanceof org.sbml.libsbml.Date))
			throw new IllegalArgumentException("date" + error
					+ "org.sbml.libsbml.Date.");
		org.sbml.libsbml.Date d = (org.sbml.libsbml.Date) date;
		Calendar c = Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone(TimeZone.getAvailableIDs((int) (d
				.getSignOffset()
				* d.getMinutesOffset() * 60000))[0]));
		c.set((int) d.getYear(), (int) d.getMonth(), (int) d.getDay(), (int) d
				.getHour(), (int) d.getMinute(), (int) d.getSecond());
		return c.getTime();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jlibsbml.SBMLReader#getNumErrors()
	 */
	public int getNumErrors() {
		org.sbml.libsbml.SBMLDocument doc = originalModel.getSBMLDocument();
		doc.checkConsistency();
		return (int) doc.getNumErrors();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.squeezer.io.AbstractSBMLReader#getOriginalModel()
	 */
	// @Override
	public org.sbml.libsbml.Model getOriginalModel() {
		return originalModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jlibsbml.SBMLReader#getWarnings()
	 */
	public List<SBMLException> getWarnings() {
		org.sbml.libsbml.SBMLDocument doc = originalModel.getSBMLDocument();
		doc.checkConsistency();
		List<SBMLException> excl = new LinkedList<SBMLException>();
		for (int i = 0; i < doc.getNumErrors(); i++)
			excl.add(convert(doc.getError(i)));
		return excl;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBMLReader#readCompartment(java.lang.Object)
	 */
	public Compartment readCompartment(Object compartment) {
		if (!(compartment instanceof org.sbml.libsbml.Compartment))
			throw new IllegalArgumentException("compartment" + error
					+ "org.sbml.libsbml.Compartment");
		org.sbml.libsbml.Compartment comp = (org.sbml.libsbml.Compartment) compartment;
		Compartment c = new Compartment(comp.getId(), (int) comp.getLevel(),
				(int) comp.getVersion());
		copyNamedSBaseProperties(c, comp);
		if (comp.isSetOutside()) {
			Model m = getModel();
			if (m.getCompartment(comp.getOutside()) == null)
				m.addCompartment(readCompartment(comp.getModel()
						.getCompartment(comp.getOutside())));
			Compartment outside = getModel().getCompartment(comp.getOutside());
			c.setOutside(outside);
		}
		if (comp.isSetCompartmentType())
			c
					.setCompartmentType(readCompartmentType(comp
							.getCompartmentType()));
		c.setConstant(comp.getConstant());
		c.setSize(comp.isSetSize() ? comp.getSize() : Double.NaN);
		c.setSpatialDimensions((short) comp.getSpatialDimensions());
		if (comp.isSetUnits())
			c.setUnits(getModel().getUnitDefinition(comp.getUnits()));
		return c;
	}

	/**
	 * 
	 * @param compartmenttype
	 * @return
	 */
	public CompartmentType readCompartmentType(Object compartmenttype) {
		if (!(compartmenttype instanceof org.sbml.libsbml.CompartmentType))
			throw new IllegalArgumentException("compartmenttype" + error
					+ "org.sbml.libsbml.CompartmentType");
		org.sbml.libsbml.CompartmentType comp = (org.sbml.libsbml.CompartmentType) compartmenttype;
		CompartmentType com = new CompartmentType(comp.getId(), (int) comp
				.getLevel(), (int) comp.getVersion());
		copyNamedSBaseProperties(com, comp);
		return com;
	}

	/**
	 * 
	 * @param constraint
	 * @return
	 */
	public Constraint readConstraint(Object constraint) {
		if (!(constraint instanceof org.sbml.libsbml.Constraint))
			throw new IllegalArgumentException("constraint" + error
					+ "org.sbml.libsml.Constraint");
		org.sbml.libsbml.Constraint cons = (org.sbml.libsbml.Constraint) constraint;
		Constraint con = new Constraint((int) cons.getLevel(), (int) cons
				.getVersion());
		copySBaseProperties(con, cons);
		if (cons.isSetMath())
			con.setMath(convert(cons.getMath(), con));
		if (cons.isSetMessage())
			con.setMessage(cons.getMessageString());
		return con;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jlibsbml.SBMLReader#readCVTerm(java.lang.Object)
	 */
	public CVTerm readCVTerm(Object term) {
		if (!(term instanceof org.sbml.libsbml.CVTerm))
			throw new IllegalArgumentException("term" + error
					+ "org.sbml.libsbml.CVTerm.");
		org.sbml.libsbml.CVTerm libCVt = (org.sbml.libsbml.CVTerm) term;
		CVTerm t = new CVTerm();
		switch (libCVt.getQualifierType()) {
		case libsbmlConstants.MODEL_QUALIFIER:
			t.setQualifierType(CVTerm.Type.MODEL_QUALIFIER);
			switch (libCVt.getModelQualifierType()) {
			case libsbmlConstants.BQM_IS:
				t.setModelQualifierType(Qualifier.BQM_IS);
				break;
			case libsbmlConstants.BQM_IS_DESCRIBED_BY:
				t.setModelQualifierType(Qualifier.BQM_IS_DESCRIBED_BY);
				break;
			case libsbmlConstants.BQM_UNKNOWN:
				t.setModelQualifierType(Qualifier.BQM_UNKNOWN);
				break;
			default:
				break;
			}
			break;
		case libsbmlConstants.BIOLOGICAL_QUALIFIER:
			t.setQualifierType(CVTerm.Type.BIOLOGICAL_QUALIFIER);
			switch (libCVt.getBiologicalQualifierType()) {
			case libsbmlConstants.BQB_ENCODES:
				t.setBiologicalQualifierType(Qualifier.BQB_ENCODES);
				break;
			case libsbmlConstants.BQB_HAS_PART:
				t.setBiologicalQualifierType(Qualifier.BQB_HAS_PART);
				break;
			case libsbmlConstants.BQB_HAS_VERSION:
				t.setBiologicalQualifierType(Qualifier.BQB_HAS_VERSION);
				break;
			case libsbmlConstants.BQB_IS:
				t.setBiologicalQualifierType(Qualifier.BQB_IS);
				break;
			case libsbmlConstants.BQB_IS_DESCRIBED_BY:
				t.setBiologicalQualifierType(Qualifier.BQB_IS_DESCRIBED_BY);
				break;
			case libsbmlConstants.BQB_IS_ENCODED_BY:
				t.setBiologicalQualifierType(Qualifier.BQB_IS_ENCODED_BY);
				break;
			case libsbmlConstants.BQB_IS_HOMOLOG_TO:
				t.setBiologicalQualifierType(Qualifier.BQB_IS_HOMOLOG_TO);
				break;
			case libsbmlConstants.BQB_IS_PART_OF:
				t.setBiologicalQualifierType(Qualifier.BQB_IS_PART_OF);
				break;
			case libsbmlConstants.BQB_IS_VERSION_OF:
				t.setBiologicalQualifierType(Qualifier.BQB_IS_VERSION_OF);
				break;
			case libsbmlConstants.BQB_OCCURS_IN:
				t.setBiologicalQualifierType(Qualifier.BQB_OCCURS_IN);
				break;
			case libsbmlConstants.BQB_UNKNOWN:
				t.setBiologicalQualifierType(Qualifier.BQB_UNKNOWN);
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}
		for (int j = 0; j < libCVt.getNumResources(); j++) {
			t.addResourceURI(libCVt.getResourceURI(j));
		}
		return t;
	}

	/**
	 * 
	 * @param delay
	 * @return
	 */
	public Delay readDelay(Object delay) {
		if (!(delay instanceof org.sbml.libsbml.Delay))
			throw new IllegalArgumentException("delay" + error
					+ "org.sbml.libsbml.Delay");
		org.sbml.libsbml.Delay del = (org.sbml.libsbml.Delay) delay;
		Delay de = new Delay((int) del.getLevel(), (int) del.getVersion());
		copySBaseProperties(de, del);
		if (del.isSetMath())
			de.setMath(convert(del.getMath(), de));
		return de;
	}

	/**
	 * 
	 * @param event
	 * @return
	 */
	public Event readEvent(Object event) {
		if (!(event instanceof org.sbml.libsbml.Event))
			throw new IllegalArgumentException("event" + error
					+ "org.sbml.libsbml.Event");
		org.sbml.libsbml.Event eve = (org.sbml.libsbml.Event) event;
		Event ev = new Event((int) eve.getLevel(), (int) eve.getVersion());
		copyNamedSBaseProperties(ev, eve);
		if (eve.isSetTrigger())
			ev.setTrigger(readTrigger(eve.getTrigger()));
		if (eve.isSetDelay())
			ev.setDelay(readDelay(eve.getDelay()));
		for (int i = 0; i < eve.getNumEventAssignments(); i++) {
			ev.addEventAssignement(readEventAssignment(eve
					.getEventAssignment(i)));
		}
		if (eve.isSetTimeUnits())
			ev.setTimeUnits(eve.getTimeUnits());
		ev.setUseValuesFromTriggerTime(eve.getUseValuesFromTriggerTime());
		return ev;

	}

	/**
	 * 
	 * @param eventAssignment
	 * @return
	 */
	public EventAssignment readEventAssignment(Object eventass) {
		if (!(eventass instanceof org.sbml.libsbml.EventAssignment))
			throw new IllegalArgumentException("eventassignment" + error
					+ "org.sbml.libsbml.EventAssignment");
		org.sbml.libsbml.EventAssignment eve = (org.sbml.libsbml.EventAssignment) eventass;
		EventAssignment ev = new EventAssignment((int) eve.getLevel(),
				(int) eve.getVersion());
		copySBaseProperties(ev, eve);
		if (eve.isSetVariable()) {
			Symbol variable = model.findSymbol(eve.getVariable());
			if (variable == null)
				ev.setVariable(eve.getVariable());
			else
				ev.setVariable(variable);
		}
		if (eve.isSetMath())
			ev.setMath(convert(eve.getMath(), ev));
		return ev;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBMLReader#readFunctionDefinition(java.lang.Object)
	 */
	public FunctionDefinition readFunctionDefinition(Object functionDefinition) {
		if (!(functionDefinition instanceof org.sbml.libsbml.FunctionDefinition))
			throw new IllegalArgumentException("functionDefinition" + error
					+ "org.sbml.libsbml.FunctionDefinition.");
		org.sbml.libsbml.FunctionDefinition fd = (org.sbml.libsbml.FunctionDefinition) functionDefinition;
		FunctionDefinition f = new FunctionDefinition(fd.getId(), (int) fd
				.getLevel(), (int) fd.getVersion());
		copyNamedSBaseProperties(f, fd);
		if (fd.isSetMath())
			f.setMath(convert(fd.getMath(), f));
		return f;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBMLReader#readInitialAssignment(java.lang.Object)
	 */
	public InitialAssignment readInitialAssignment(Object initialAssignment) {
		if (!(initialAssignment instanceof org.sbml.libsbml.InitialAssignment))
			throw new IllegalArgumentException("initialAssignment" + error
					+ "org.sbml.libsbml.InitialAssignment.");
		org.sbml.libsbml.InitialAssignment sbIA = (org.sbml.libsbml.InitialAssignment) initialAssignment;
		if (!sbIA.isSetSymbol())
			throw new IllegalArgumentException(
					"Symbol attribute not set for InitialAssignment");
		InitialAssignment ia = new InitialAssignment(model.findSymbol(sbIA
				.getSymbol()));
		copySBaseProperties(ia, sbIA);
		if (sbIA.isSetMath())
			ia.setMath(convert(sbIA.getMath(), ia));
		return ia;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBMLReader#readKineticLaw(java.lang.Object)
	 */
	public KineticLaw readKineticLaw(Object kineticLaw) {
		if (!(kineticLaw instanceof org.sbml.libsbml.KineticLaw))
			throw new IllegalArgumentException("kineticLaw" + error
					+ "org.sbml.libsbml.KineticLaw.");
		org.sbml.libsbml.KineticLaw kl = (org.sbml.libsbml.KineticLaw) kineticLaw;
		KineticLaw kinlaw = new KineticLaw((int) kl.getLevel(), (int) kl
				.getVersion());
		copySBaseProperties(kinlaw, kl);
		for (int i = 0; i < kl.getNumParameters(); i++)
			kinlaw.addParameter(readParameter(kl.getParameter(i)));
		if (kl.isSetMath())
			kinlaw.setMath(convert(kl.getMath(), kinlaw));
		addAllSBaseChangeListenersTo(kinlaw);
		return kinlaw;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBMLReader#readModel(java.lang.Object)
	 */
	public Model readModel(Object model) {
		if (model instanceof String) {
			org.sbml.libsbml.SBMLDocument doc = (new org.sbml.libsbml.SBMLReader())
					.readSBML(model.toString());
			setOfDocuments.add(doc);
			model = doc.getModel();
		}
		if (model instanceof org.sbml.libsbml.Model) {
			this.originalModel = (org.sbml.libsbml.Model) model;
			SBMLDocument sbmldoc = new SBMLDocument((int) originalModel
					.getLevel(), (int) originalModel.getVersion());
			copySBaseProperties(sbmldoc, originalModel.getSBMLDocument());
			this.model = sbmldoc.createModel(originalModel.getId());
			copyNamedSBaseProperties(this.model, originalModel);
			int i;
			if (originalModel.isSetModelHistory()) {
				ModelHistory mh = new ModelHistory();
				org.sbml.libsbml.ModelHistory libHist = originalModel
						.getModelHistory();
				for (i = 0; i < libHist.getNumCreators(); i++) {
					ModelCreator mc = new ModelCreator();
					org.sbml.libsbml.ModelCreator creator = originalModel
							.getModelHistory().getCreator(i);
					mc.setGivenName(creator.getGivenName());
					mc.setFamilyName(creator.getFamilyName());
					mc.setEmail(creator.getEmail());
					mc.setOrganization(creator.getOrganization());
					mh.addCreator(mc);
				}
				if (libHist.isSetCreatedDate())
					mh.setCreatedDate(convertDate(libHist.getCreatedDate()));
				if (libHist.isSetModifiedDate())
					mh.setModifiedDate(convertDate(libHist.getModifiedDate()));
				for (i = 0; i < libHist.getNumModifiedDates(); i++)
					mh.addModifiedDate(convertDate(libHist.getModifiedDate(i)));
				this.model.setModelHistory(mh);
			}
			for (i = 0; i < originalModel.getNumFunctionDefinitions(); i++)
				this.model
						.addFunctionDefinition(readFunctionDefinition(originalModel
								.getFunctionDefinition(i)));
			for (i = 0; i < originalModel.getNumUnitDefinitions(); i++)
				this.model.addUnitDefinition(readUnitDefinition(originalModel
						.getUnitDefinition(i)));
			// This is something, libSBML wouldn't do...
			addPredefinedUnitDefinitions(this.model);
			for (i = 0; i < originalModel.getNumCompartmentTypes(); i++)
				this.model.addCompartmentType(readCompartmentType(originalModel
						.getCompartmentType(i)));
			for (i = 0; i < originalModel.getNumSpeciesTypes(); i++)
				this.model.addSpeciesType(readSpeciesType(originalModel
						.getSpeciesType(i)));
			for (i = 0; i < originalModel.getNumCompartments(); i++)
				this.model.addCompartment(readCompartment(originalModel
						.getCompartment(i)));
			for (i = 0; i < originalModel.getNumSpecies(); i++)
				this.model.addSpecies(readSpecies(originalModel.getSpecies(i)));
			for (i = 0; i < originalModel.getNumParameters(); i++)
				this.model.addParameter(readParameter(originalModel
						.getParameter(i)));
			for (i = 0; i < originalModel.getNumInitialAssignments(); i++)
				this.model
						.addInitialAssignment(readInitialAssignment(originalModel
								.getInitialAssignment(i)));
			for (i = 0; i < originalModel.getNumRules(); i++)
				this.model.addRule(readRule(originalModel.getRule(i)));
			for (i = 0; i < originalModel.getNumConstraints(); i++)
				this.model.addConstraint(readConstraint(originalModel
						.getConstraint(i)));
			for (i = 0; i < originalModel.getNumReactions(); i++) {
				org.sbml.libsbml.Reaction rOrig = originalModel.getReaction(i);
				Reaction r = readReaction(rOrig);
				this.model.addReaction(r);
				if (rOrig.isSetKineticLaw())
					r.setKineticLaw(readKineticLaw(rOrig.getKineticLaw()));
			}
			for (i = 0; i < originalModel.getNumEvents(); i++)
				this.model.addEvent(readEvent(originalModel.getEvent(i)));
			addAllSBaseChangeListenersTo(this.model);
			return this.model;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBMLReader#readModifierSpeciesReference(java.lang.Object)
	 */
	public ModifierSpeciesReference readModifierSpeciesReference(
			Object modifierSpeciesReference) {
		if (!(modifierSpeciesReference instanceof org.sbml.libsbml.ModifierSpeciesReference))
			throw new IllegalArgumentException("modifierSpeciesReference"
					+ error + "org.sbml.libsbml.ModifierSpeciesReference.");
		org.sbml.libsbml.ModifierSpeciesReference msr = (org.sbml.libsbml.ModifierSpeciesReference) modifierSpeciesReference;
		ModifierSpeciesReference mod = new ModifierSpeciesReference(model
				.getSpecies(msr.getSpecies()));
		copyNamedSBaseProperties(mod, msr);
		if (msr.isSetSBOTerm()) {
			mod.setSBOTerm(msr.getSBOTerm());
			/*
			 * if (!SBO.isEnzymaticCatalysis(mod.getSBOTerm()) &&
			 * possibleEnzymes.contains(Integer.valueOf(mod
			 * .getSpeciesInstance().getSBOTerm())))
			 * mod.setSBOTerm(SBO.getEnzymaticCatalysis());
			 */
		}
		addAllSBaseChangeListenersTo(mod);
		return mod;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBMLReader#readParameter(java.lang.Object)
	 */
	public Parameter readParameter(Object parameter) {
		if (!(parameter instanceof org.sbml.libsbml.Parameter))
			throw new IllegalArgumentException("parameter" + error
					+ "org.sbml.libsbml.Parameter.");
		org.sbml.libsbml.Parameter p = (org.sbml.libsbml.Parameter) parameter;
		Parameter para = new Parameter(p.getId(), (int) p.getLevel(), (int) p
				.getVersion());
		copyNamedSBaseProperties(para, p);
		if (p.isSetValue())
			para.setValue(p.getValue());
		para.setConstant(p.getConstant());
		if (p.isSetUnits()) {
			UnitDefinition ud = this.model.getUnitDefinition(p.getUnits());
			if (ud != null)
				para.setUnits(ud);
			else
				para.setUnits(Unit.Kind.valueOf(p.getUnits().toUpperCase()));
		}
		addAllSBaseChangeListenersTo(para);
		return para;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBMLReader#readReaction(java.lang.Object)
	 */
	public Reaction readReaction(Object reac) {
		if (!(reac instanceof org.sbml.libsbml.Reaction))
			throw new IllegalArgumentException("reaction" + error
					+ "org.sbml.libsbml.Reaction.");
		org.sbml.libsbml.Reaction r = (org.sbml.libsbml.Reaction) reac;
		Reaction reaction = new Reaction(r.getId(), (int) r.getLevel(), (int) r
				.getVersion());
		copyNamedSBaseProperties(reaction, r);
		for (int i = 0; i < r.getNumReactants(); i++)
			reaction.addReactant(readSpeciesReference(r.getReactant(i)));
		for (int i = 0; i < r.getNumProducts(); i++)
			reaction.addProduct(readSpeciesReference(r.getProduct(i)));
		for (int i = 0; i < r.getNumModifiers(); i++)
			reaction
					.addModifier(readModifierSpeciesReference(r.getModifier(i)));
		reaction.setFast(r.getFast());
		reaction.setReversible(r.getReversible());
		addAllSBaseChangeListenersTo(reaction);
		return reaction;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBMLReader#readRule(java.lang.Object)
	 */
	public Rule readRule(Object rule) {
		if (!(rule instanceof org.sbml.libsbml.Rule))
			throw new IllegalArgumentException("rule" + error
					+ "org.sbml.libsbml.Rule.");
		org.sbml.libsbml.Rule libRule = (org.sbml.libsbml.Rule) rule;
		Rule r;
		if (libRule.isAlgebraic())
			r = new AlgebraicRule((int) libRule.getLevel(), (int) libRule
					.getVersion());
		else {
			Symbol s = model.findSymbol(libRule.getVariable());
			if (libRule.isAssignment())
				r = new AssignmentRule(s);
			else
				r = new RateRule(s);
		}
		copySBaseProperties(r, libRule);
		if (libRule.isSetMath())
			r.setMath(convert(libRule.getMath(), r));
		return r;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBMLReader#readSpecies(java.lang.Object)
	 */
	public Species readSpecies(Object species) {
		if (!(species instanceof org.sbml.libsbml.Species))
			throw new IllegalArgumentException("species" + error
					+ "org.sbml.libsbml.Species.");
		org.sbml.libsbml.Species spec = (org.sbml.libsbml.Species) species;
		Species s = new Species(spec.getId(), (int) spec.getLevel(), (int) spec
				.getVersion());
		copyNamedSBaseProperties(s, spec);
		if (spec.isSetCharge())
			s.setCharge(spec.getCharge());
		if (spec.isSetCompartment())
			s.setCompartment(getModel().getCompartment(spec.getCompartment()));
		s.setBoundaryCondition(spec.getBoundaryCondition());
		s.setConstant(spec.getConstant());
		s.setHasOnlySubstanceUnits(spec.getHasOnlySubstanceUnits());
		if (spec.isSetInitialAmount())
			s.setInitialAmount(spec.getInitialAmount());
		else if (spec.isSetInitialConcentration())
			s.setInitialConcentration(spec.getInitialConcentration());
		if (spec.isSetSubstanceUnits())
			s.setSubstanceUnits(this.model.getUnitDefinition(spec.getUnits()));
		if (spec.isSetSpeciesType())
			s.setSpeciesType(this.model.getSpeciesType(spec.getSpeciesType()));
		addAllSBaseChangeListenersTo(s);
		return s;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBMLReader#readSpeciesReference(java.lang.Object)
	 */
	public SpeciesReference readSpeciesReference(Object speciesReference) {
		if (!(speciesReference instanceof org.sbml.libsbml.SpeciesReference))
			throw new IllegalArgumentException("speciesReference" + error
					+ "org.sbml.libsbml.SpeciesReference.");
		org.sbml.libsbml.SpeciesReference specref = (org.sbml.libsbml.SpeciesReference) speciesReference;
		SpeciesReference spec = new SpeciesReference(model.getSpecies(specref
				.getSpecies()));
		copyNamedSBaseProperties(spec, specref);
		if (specref.isSetStoichiometryMath())
			spec.setStoichiometryMath(readStoichiometricMath(specref
					.getStoichiometryMath()));
		else
			spec.setStoichiometry(specref.getStoichiometry());
		addAllSBaseChangeListenersTo(spec);
		return spec;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBMLReader#readSpeciesType(java.lang.Object)
	 */
	public SpeciesType readSpeciesType(Object speciesType) {
		if (!(speciesType instanceof org.sbml.libsbml.SpeciesType))
			throw new IllegalArgumentException("speciesType" + error
					+ "org.sbml.libsbml.SpeciesType.");
		org.sbml.libsbml.SpeciesType libST = (org.sbml.libsbml.SpeciesType) speciesType;
		SpeciesType st = new SpeciesType(libST.getId(), (int) libST.getLevel(),
				(int) libST.getVersion());
		copyNamedSBaseProperties(st, libST);
		return st;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBMLReader#readStoichiometricMath(java.lang.Object)
	 */
	public StoichiometryMath readStoichiometricMath(Object stoichiometryMath) {
		org.sbml.libsbml.StoichiometryMath s = (org.sbml.libsbml.StoichiometryMath) stoichiometryMath;
		StoichiometryMath sm = new StoichiometryMath((int) s.getLevel(),
				(int) s.getVersion());
		copySBaseProperties(sm, s);
		if (s.isSetMath())
			sm.setMath(convert(s.getMath(), sm));
		return sm;
	}

	/**
	 * 
	 * @param trigger
	 * @return
	 */
	public Trigger readTrigger(Object trigger) {
		if (!(trigger instanceof org.sbml.libsbml.Trigger))
			throw new IllegalArgumentException("trigger" + error
					+ "org.sbml.libsbml.Trigger");
		org.sbml.libsbml.Trigger trigg = (org.sbml.libsbml.Trigger) trigger;
		Trigger trig = new Trigger((int) trigg.getLevel(), (int) trigg
				.getVersion());
		copySBaseProperties(trig, trigg);
		if (trigg.isSetMath())
			trig.setMath(convert(trigg.getMath(), trig));
		return trig;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBMLReader#readUnit(java.lang.Object)
	 */
	public Unit readUnit(Object unit) {
		if (!(unit instanceof org.sbml.libsbml.Unit))
			throw new IllegalArgumentException("unit" + error
					+ "org.sbml.libsbml.Unit");
		org.sbml.libsbml.Unit libUnit = (org.sbml.libsbml.Unit) unit;
		Unit u = new Unit((int) libUnit.getLevel(), (int) libUnit.getVersion());
		copySBaseProperties(u, libUnit);
		switch (libUnit.getKind()) {
		case libsbmlConstants.UNIT_KIND_AMPERE:
			u.setKind(Unit.Kind.AMPERE);
			break;
		case libsbmlConstants.UNIT_KIND_BECQUEREL:
			u.setKind(Unit.Kind.BECQUEREL);
			break;
		case libsbmlConstants.UNIT_KIND_CANDELA:
			u.setKind(Unit.Kind.CANDELA);
			break;
		case libsbmlConstants.UNIT_KIND_CELSIUS:
			u.setKind(Unit.Kind.CELSIUS);
			break;
		case libsbmlConstants.UNIT_KIND_COULOMB:
			u.setKind(Unit.Kind.COULOMB);
			break;
		case libsbmlConstants.UNIT_KIND_DIMENSIONLESS:
			u.setKind(Unit.Kind.DIMENSIONLESS);
			break;
		case libsbmlConstants.UNIT_KIND_FARAD:
			u.setKind(Unit.Kind.FARAD);
			break;
		case libsbmlConstants.UNIT_KIND_GRAM:
			u.setKind(Unit.Kind.GRAM);
			break;
		case libsbmlConstants.UNIT_KIND_GRAY:
			u.setKind(Unit.Kind.GRAY);
			break;
		case libsbmlConstants.UNIT_KIND_HENRY:
			u.setKind(Unit.Kind.HENRY);
			break;
		case libsbmlConstants.UNIT_KIND_HERTZ:
			u.setKind(Unit.Kind.HERTZ);
			break;
		case libsbmlConstants.UNIT_KIND_INVALID:
			u.setKind(Unit.Kind.INVALID);
			break;
		case libsbmlConstants.UNIT_KIND_ITEM:
			u.setKind(Unit.Kind.ITEM);
			break;
		case libsbmlConstants.UNIT_KIND_JOULE:
			u.setKind(Unit.Kind.JOULE);
			break;
		case libsbmlConstants.UNIT_KIND_KATAL:
			u.setKind(Unit.Kind.KATAL);
			break;
		case libsbmlConstants.UNIT_KIND_KELVIN:
			u.setKind(Unit.Kind.KELVIN);
			break;
		case libsbmlConstants.UNIT_KIND_KILOGRAM:
			u.setKind(Unit.Kind.KILOGRAM);
			break;
		case libsbmlConstants.UNIT_KIND_LITER:
			u.setKind(Unit.Kind.LITER);
			break;
		case libsbmlConstants.UNIT_KIND_LITRE:
			u.setKind(Unit.Kind.LITRE);
			break;
		case libsbmlConstants.UNIT_KIND_LUMEN:
			u.setKind(Unit.Kind.LUMEN);
			break;
		case libsbmlConstants.UNIT_KIND_LUX:
			u.setKind(Unit.Kind.LUX);
			break;
		case libsbmlConstants.UNIT_KIND_METER:
			u.setKind(Unit.Kind.METER);
			break;
		case libsbmlConstants.UNIT_KIND_METRE:
			u.setKind(Unit.Kind.METRE);
			break;
		case libsbmlConstants.UNIT_KIND_MOLE:
			u.setKind(Unit.Kind.MOLE);
			break;
		case libsbmlConstants.UNIT_KIND_NEWTON:
			u.setKind(Unit.Kind.NEWTON);
			break;
		case libsbmlConstants.UNIT_KIND_OHM:
			u.setKind(Unit.Kind.OHM);
			break;
		case libsbmlConstants.UNIT_KIND_PASCAL:
			u.setKind(Unit.Kind.PASCAL);
			break;
		case libsbmlConstants.UNIT_KIND_RADIAN:
			u.setKind(Unit.Kind.RADIAN);
			break;
		case libsbmlConstants.UNIT_KIND_SECOND:
			u.setKind(Unit.Kind.SECOND);
			break;
		case libsbmlConstants.UNIT_KIND_SIEMENS:
			u.setKind(Unit.Kind.SIEMENS);
			break;
		case libsbmlConstants.UNIT_KIND_SIEVERT:
			u.setKind(Unit.Kind.SIEVERT);
			break;
		case libsbmlConstants.UNIT_KIND_STERADIAN:
			u.setKind(Unit.Kind.STERADIAN);
			break;
		case libsbmlConstants.UNIT_KIND_TESLA:
			u.setKind(Unit.Kind.TESLA);
			break;
		case libsbmlConstants.UNIT_KIND_VOLT:
			u.setKind(Unit.Kind.VOLT);
			break;
		case libsbmlConstants.UNIT_KIND_WATT:
			u.setKind(Unit.Kind.WATT);
			break;
		case libsbmlConstants.UNIT_KIND_WEBER:
			u.setKind(Unit.Kind.WEBER);
			break;
		}
		u.setExponent(libUnit.getExponent());
		u.setMultiplier(libUnit.getMultiplier());
		u.setScale(libUnit.getScale());
		u.setOffset(libUnit.getOffset());
		return u;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBMLReader#readUnitDefinition(java.lang.Object)
	 */
	public UnitDefinition readUnitDefinition(Object unitDefinition) {
		if (!(unitDefinition instanceof org.sbml.libsbml.UnitDefinition))
			throw new IllegalArgumentException("unitDefinition" + error
					+ "org.sbml.libsbml.UnitDefinition");
		org.sbml.libsbml.UnitDefinition libUD = (org.sbml.libsbml.UnitDefinition) unitDefinition;
		UnitDefinition ud = new UnitDefinition(libUD.getId(), (int) libUD
				.getLevel(), (int) libUD.getVersion());
		copyNamedSBaseProperties(ud, libUD);
		for (int i = 0; i < libUD.getNumUnits(); i++)
			ud.addUnit(readUnit(libUD.getUnit(i)));
		return ud;
	}

	/**
	 * 
	 * @param sbase
	 * @param libSBase
	 */
	private void copyNamedSBaseProperties(NamedSBase sbase,
			org.sbml.libsbml.SBase libSBase) {
		copySBaseProperties(sbase, libSBase);
		if (libSBase instanceof org.sbml.libsbml.Compartment) {
			org.sbml.libsbml.Compartment c = (org.sbml.libsbml.Compartment) libSBase;
			if (c.isSetId())
				sbase.setId(c.getId());
			if (c.isSetName())
				sbase.setName(c.getName());
		} else if (libSBase instanceof org.sbml.libsbml.CompartmentType) {
			org.sbml.libsbml.CompartmentType c = (org.sbml.libsbml.CompartmentType) libSBase;
			if (c.isSetId())
				sbase.setId(c.getId());
			if (c.isSetName())
				sbase.setName(c.getName());
		} else if (libSBase instanceof org.sbml.libsbml.Event) {
			org.sbml.libsbml.Event c = (org.sbml.libsbml.Event) libSBase;
			if (c.isSetId())
				sbase.setId(c.getId());
			if (c.isSetName())
				sbase.setName(c.getName());
		} else if (libSBase instanceof org.sbml.libsbml.FunctionDefinition) {
			org.sbml.libsbml.FunctionDefinition c = (org.sbml.libsbml.FunctionDefinition) libSBase;
			if (c.isSetId())
				sbase.setId(c.getId());
			if (c.isSetName())
				sbase.setName(c.getName());
		} else if (libSBase instanceof org.sbml.libsbml.Model) {
			org.sbml.libsbml.Model c = (org.sbml.libsbml.Model) libSBase;
			if (c.isSetId())
				sbase.setId(c.getId());
			if (c.isSetName())
				sbase.setName(c.getName());
		} else if (libSBase instanceof org.sbml.libsbml.Parameter) {
			org.sbml.libsbml.Parameter c = (org.sbml.libsbml.Parameter) libSBase;
			if (c.isSetId())
				sbase.setId(c.getId());
			if (c.isSetName())
				sbase.setName(c.getName());
		} else if (libSBase instanceof org.sbml.libsbml.Reaction) {
			org.sbml.libsbml.Reaction c = (org.sbml.libsbml.Reaction) libSBase;
			if (c.isSetId())
				sbase.setId(c.getId());
			if (c.isSetName())
				sbase.setName(c.getName());
		} else if (libSBase instanceof org.sbml.libsbml.SimpleSpeciesReference) {
			org.sbml.libsbml.SimpleSpeciesReference c = (org.sbml.libsbml.SimpleSpeciesReference) libSBase;
			if (c.isSetId())
				sbase.setId(c.getId());
			if (c.isSetName())
				sbase.setName(c.getName());
		} else if (libSBase instanceof org.sbml.libsbml.Species) {
			org.sbml.libsbml.Species c = (org.sbml.libsbml.Species) libSBase;
			if (c.isSetId())
				sbase.setId(c.getId());
			if (c.isSetName())
				sbase.setName(c.getName());
		} else if (libSBase instanceof org.sbml.libsbml.SpeciesType) {
			org.sbml.libsbml.SpeciesType c = (org.sbml.libsbml.SpeciesType) libSBase;
			if (c.isSetId())
				sbase.setId(c.getId());
			if (c.isSetName())
				sbase.setName(c.getName());
		} else if (libSBase instanceof org.sbml.libsbml.UnitDefinition) {
			org.sbml.libsbml.UnitDefinition c = (org.sbml.libsbml.UnitDefinition) libSBase;
			if (c.isSetId())
				sbase.setId(c.getId());
			if (c.isSetName())
				sbase.setName(c.getName());
		}
	}

	/**
	 * 
	 * @param sbase
	 * @param libSBase
	 */
	private void copySBaseProperties(SBase sbase,
			org.sbml.libsbml.SBase libSBase) {
		if (libSBase.isSetMetaId())
			sbase.setMetaId(libSBase.getMetaId());
		if (libSBase.isSetSBOTerm())
			sbase.setSBOTerm(libSBase.getSBOTerm());
		if (libSBase.isSetNotes())
			sbase.setNotes(libSBase.getNotesString());
		for (int i = 0; i < libSBase.getNumCVTerms(); i++)
			sbase.addCVTerm(readCVTerm(libSBase.getCVTerm(i)));
	}
}
