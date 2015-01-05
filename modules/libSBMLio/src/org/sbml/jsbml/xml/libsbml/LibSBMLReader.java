/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2015 jointly by the following organizations:
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
package org.sbml.jsbml.xml.libsbml;

import static org.sbml.jsbml.xml.libsbml.LibSBMLConstants.LINK_TO_LIBSBML;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javax.xml.stream.XMLStreamException;

import org.apache.log4j.Logger;
import org.sbml.jsbml.AlgebraicRule;
import org.sbml.jsbml.AssignmentRule;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.CompartmentType;
import org.sbml.jsbml.Constraint;
import org.sbml.jsbml.Delay;
import org.sbml.jsbml.Event;
import org.sbml.jsbml.EventAssignment;
import org.sbml.jsbml.ExplicitRule;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.InitialAssignment;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.LocalParameter;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.ModifierSpeciesReference;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.Priority;
import org.sbml.jsbml.RateRule;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.Rule;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLInputConverter;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.SpeciesType;
import org.sbml.jsbml.StoichiometryMath;
import org.sbml.jsbml.Trigger;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.util.ProgressListener;
import org.sbml.jsbml.util.ResourceManager;
import org.sbml.jsbml.util.SBMLtools;
import org.sbml.jsbml.util.TreeNodeChangeListener;
import org.sbml.libsbml.SBMLError;
import org.sbml.libsbml.libsbmlConstants;

/**
 * This class can be used to parse SBML documents with libSBML and to copy all
 * data structures contained therein into JSBML's data objects. Note that this
 * class is only implemented to work with certain versions of libSBML
 * (successfully tested with version 5.8) and does currently not support SBML
 * packages. However, the advantage of using this class is that it provides the
 * offline validation capability of libSBML. The downside is that it again
 * introduces the platform dependency of libSBML also to the JSBML-based
 * program.
 * 
 * @author Andreas Dr&auml;ger
 * @version $Rev$
 * @since 0.8
 */
@SuppressWarnings("deprecation")
public class LibSBMLReader implements SBMLInputConverter<org.sbml.libsbml.Model> {

  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(LibSBMLReader.class);

  /**
   * Localization support.
   */
  private static final transient ResourceBundle bundle = ResourceManager.getBundle("org.sbml.jsbml.xml.libsbml.Messages");

  /**
   * Converts a libSBML SBMLError object into a JSBML SBMLException object.
   * 
   * @param error
   * @return
   */
  static final SBMLException convert(SBMLError error) {
    SBMLException exc = new SBMLException(error.getMessage());
    if (error.getCategory() == libsbmlConstants.LIBSBML_CAT_GENERAL_CONSISTENCY) {
      exc.setCategory(SBMLException.Category.GENERAL_CONSISTENCY);
    } else if (error.getCategory() == libsbmlConstants.LIBSBML_CAT_IDENTIFIER_CONSISTENCY) {
      exc.setCategory(SBMLException.Category.IDENTIFIER_CONSISTENCY);
    } else if (error.getCategory() == libsbmlConstants.LIBSBML_CAT_INTERNAL) {
      exc.setCategory(SBMLException.Category.INTERNAL);
    } else if (error.getCategory() == libsbmlConstants.LIBSBML_CAT_INTERNAL_CONSISTENCY) {
      exc.setCategory(SBMLException.Category.INTERNAL_CONSISTENCY);
    } else if (error.getCategory() == libsbmlConstants.LIBSBML_CAT_MATHML_CONSISTENCY) {
      exc.setCategory(SBMLException.Category.MATHML_CONSISTENCY);
    } else if (error.getCategory() == libsbmlConstants.LIBSBML_CAT_MODELING_PRACTICE) {
      exc.setCategory(SBMLException.Category.MODELING_PRACTICE);
    } else if (error.getCategory() == libsbmlConstants.LIBSBML_CAT_OVERDETERMINED_MODEL) {
      exc.setCategory(SBMLException.Category.OVERDETERMINED_MODEL);
    } else if (error.getCategory() == libsbmlConstants.LIBSBML_CAT_SBML) {
      exc.setCategory(SBMLException.Category.SBML);
    } else if (error.getCategory() == libsbmlConstants.LIBSBML_CAT_SBML_L1_COMPAT) {
      exc.setCategory(SBMLException.Category.SBML_L1_COMPAT);
    } else if (error.getCategory() == libsbmlConstants.LIBSBML_CAT_SBML_L2V1_COMPAT) {
      exc.setCategory(SBMLException.Category.SBML_L2V1_COMPAT);
    } else if (error.getCategory() == libsbmlConstants.LIBSBML_CAT_SBML_L2V2_COMPAT) {
      exc.setCategory(SBMLException.Category.SBML_L2V2_COMPAT);
    } else if (error.getCategory() == libsbmlConstants.LIBSBML_CAT_SBML_L2V3_COMPAT) {
      exc.setCategory(SBMLException.Category.SBML_L2V3_COMPAT);
    } else if (error.getCategory() == libsbmlConstants.LIBSBML_CAT_SBML_L2V4_COMPAT) {
      exc.setCategory(SBMLException.Category.SBML_L2V4_COMPAT);
    } else if (error.getCategory() == libsbmlConstants.LIBSBML_CAT_SBO_CONSISTENCY) {
      exc.setCategory(SBMLException.Category.SBO_CONSISTENCY);
    } else if (error.getCategory() == libsbmlConstants.LIBSBML_CAT_SYSTEM) {
      exc.setCategory(SBMLException.Category.SYSTEM);
    } else if (error.getCategory() == libsbmlConstants.LIBSBML_CAT_UNITS_CONSISTENCY) {
      exc.setCategory(SBMLException.Category.UNITS_CONSISTENCY);
    } else if (error.getCategory() == libsbmlConstants.LIBSBML_CAT_XML) {
      exc.setCategory(SBMLException.Category.XML);
    }
    exc.setShortMessage(error.getShortMessage());
    if (error.getErrorId() == libsbmlConstants.UnknownError) {
      exc.setCode(SBMLException.Code.UnknownError);
    } else if (error.getErrorId() == libsbmlConstants.NotUTF8) {
      exc.setCode(SBMLException.Code.NotUTF8);
    } else if (error.getErrorId() == libsbmlConstants.UnrecognizedElement) {
      exc.setCode(SBMLException.Code.UnrecognizedElement);
    } else if (error.getErrorId() == libsbmlConstants.NotSchemaConformant) {
      exc.setCode(SBMLException.Code.NotSchemaConformant);
    } else if (error.getErrorId() == libsbmlConstants.InvalidMathElement) {
      exc.setCode(SBMLException.Code.InvalidMathElement);
    } else if (error.getErrorId() == libsbmlConstants.DisallowedMathMLSymbol) {
      exc.setCode(SBMLException.Code.DisallowedMathMLSymbol);
    } else if (error.getErrorId() == libsbmlConstants.DisallowedMathMLEncodingUse) {
      exc.setCode(SBMLException.Code.DisallowedMathMLEncodingUse);
    } else if (error.getErrorId() == libsbmlConstants.DisallowedDefinitionURLUse) {
      exc.setCode(SBMLException.Code.DisallowedDefinitionURLUse);
    } else if (error.getErrorId() == libsbmlConstants.BadCsymbolDefinitionURLValue) {
      exc.setCode(SBMLException.Code.BadCsymbolDefinitionURLValue);
    } else if (error.getErrorId() == libsbmlConstants.DisallowedMathTypeAttributeUse) {
      exc.setCode(SBMLException.Code.DisallowedMathTypeAttributeUse);
    } else if (error.getErrorId() == libsbmlConstants.DisallowedMathTypeAttributeValue) {
      exc.setCode(SBMLException.Code.DisallowedMathTypeAttributeValue);
    } else if (error.getErrorId() == libsbmlConstants.LambdaOnlyAllowedInFunctionDef) {
      exc.setCode(SBMLException.Code.LambdaOnlyAllowedInFunctionDef);
    } else if (error.getErrorId() == libsbmlConstants.BooleanOpsNeedBooleanArgs) {
      exc.setCode(SBMLException.Code.BooleanOpsNeedBooleanArgs);
    } else if (error.getErrorId() == libsbmlConstants.NumericOpsNeedNumericArgs) {
      exc.setCode(SBMLException.Code.NumericOpsNeedNumericArgs);
    } else if (error.getErrorId() == libsbmlConstants.ArgsToEqNeedSameType) {
      exc.setCode(SBMLException.Code.ArgsToEqNeedSameType);
    } else if (error.getErrorId() == libsbmlConstants.PiecewiseNeedsConsistentTypes) {
      exc.setCode(SBMLException.Code.PiecewiseNeedsConsistentTypes);
    } else if (error.getErrorId() == libsbmlConstants.PieceNeedsBoolean) {
      exc.setCode(SBMLException.Code.PieceNeedsBoolean);
    } else if (error.getErrorId() == libsbmlConstants.ApplyCiMustBeUserFunction) {
      exc.setCode(SBMLException.Code.ApplyCiMustBeUserFunction);
    } else if (error.getErrorId() == libsbmlConstants.ApplyCiMustBeModelComponent) {
      exc.setCode(SBMLException.Code.ApplyCiMustBeModelComponent);
    } else if (error.getErrorId() == libsbmlConstants.KineticLawParametersAreLocalOnly) {
      exc.setCode(SBMLException.Code.KineticLawParametersAreLocalOnly);
    } else if (error.getErrorId() == libsbmlConstants.MathResultMustBeNumeric) {
      exc.setCode(SBMLException.Code.MathResultMustBeNumeric);
    } else if (error.getErrorId() == libsbmlConstants.OpsNeedCorrectNumberOfArgs) {
      exc.setCode(SBMLException.Code.OpsNeedCorrectNumberOfArgs);
    } else if (error.getErrorId() == libsbmlConstants.InvalidNoArgsPassedToFunctionDef) {
      exc.setCode(SBMLException.Code.InvalidNoArgsPassedToFunctionDef);
    } else if (error.getErrorId() == libsbmlConstants.DuplicateComponentId) {
      exc.setCode(SBMLException.Code.DuplicateComponentId);
    } else if (error.getErrorId() == libsbmlConstants.DuplicateUnitDefinitionId) {
      exc.setCode(SBMLException.Code.DuplicateUnitDefinitionId);
    } else if (error.getErrorId() == libsbmlConstants.DuplicateLocalParameterId) {
      exc.setCode(SBMLException.Code.DuplicateLocalParameterId);
    } else if (error.getErrorId() == libsbmlConstants.MultipleAssignmentOrRateRules) {
      exc.setCode(SBMLException.Code.MultipleAssignmentOrRateRules);
    } else if (error.getErrorId() == libsbmlConstants.MultipleEventAssignmentsForId) {
      exc.setCode(SBMLException.Code.MultipleEventAssignmentsForId);
    } else if (error.getErrorId() == libsbmlConstants.EventAndAssignmentRuleForId) {
      exc.setCode(SBMLException.Code.EventAndAssignmentRuleForId);
    } else if (error.getErrorId() == libsbmlConstants.DuplicateMetaId) {
      exc.setCode(SBMLException.Code.DuplicateMetaId);
    } else if (error.getErrorId() == libsbmlConstants.InvalidSBOTermSyntax) {
      exc.setCode(SBMLException.Code.InvalidSBOTermSyntax);
    } else if (error.getErrorId() == libsbmlConstants.InvalidMetaidSyntax) {
      exc.setCode(SBMLException.Code.InvalidMetaidSyntax);
    } else if (error.getErrorId() == libsbmlConstants.InvalidIdSyntax) {
      exc.setCode(SBMLException.Code.InvalidIdSyntax);
    } else if (error.getErrorId() == libsbmlConstants.InvalidUnitIdSyntax) {
      exc.setCode(SBMLException.Code.InvalidUnitIdSyntax);
    } else if (error.getErrorId() == libsbmlConstants.MissingAnnotationNamespace) {
      exc.setCode(SBMLException.Code.MissingAnnotationNamespace);
    } else if (error.getErrorId() == libsbmlConstants.DuplicateAnnotationNamespaces) {
      exc.setCode(SBMLException.Code.DuplicateAnnotationNamespaces);
    } else if (error.getErrorId() == libsbmlConstants.SBMLNamespaceInAnnotation) {
      exc.setCode(SBMLException.Code.SBMLNamespaceInAnnotation);
    } else if (error.getErrorId() == libsbmlConstants.InconsistentArgUnits) {
      exc.setCode(SBMLException.Code.InconsistentArgUnits);
    } else if (error.getErrorId() == libsbmlConstants.AssignRuleCompartmentMismatch) {
      exc.setCode(SBMLException.Code.AssignRuleCompartmentMismatch);
    } else if (error.getErrorId() == libsbmlConstants.AssignRuleSpeciesMismatch) {
      exc.setCode(SBMLException.Code.AssignRuleSpeciesMismatch);
    } else if (error.getErrorId() == libsbmlConstants.AssignRuleParameterMismatch) {
      exc.setCode(SBMLException.Code.AssignRuleParameterMismatch);
    } else if (error.getErrorId() == libsbmlConstants.InitAssignCompartmenMismatch) {
      exc.setCode(SBMLException.Code.InitAssignCompartmenMismatch);
    } else if (error.getErrorId() == libsbmlConstants.InitAssignSpeciesMismatch) {
      exc.setCode(SBMLException.Code.InitAssignSpeciesMismatch);
    } else if (error.getErrorId() == libsbmlConstants.InitAssignParameterMismatch) {
      exc.setCode(SBMLException.Code.InitAssignParameterMismatch);
    } else if (error.getErrorId() == libsbmlConstants.RateRuleCompartmentMismatch) {
      exc.setCode(SBMLException.Code.RateRuleCompartmentMismatch);
    } else if (error.getErrorId() == libsbmlConstants.RateRuleSpeciesMismatch) {
      exc.setCode(SBMLException.Code.RateRuleSpeciesMismatch);
    } else if (error.getErrorId() == libsbmlConstants.RateRuleParameterMismatch) {
      exc.setCode(SBMLException.Code.RateRuleParameterMismatch);
    } else if (error.getErrorId() == libsbmlConstants.KineticLawNotSubstancePerTime) {
      exc.setCode(SBMLException.Code.KineticLawNotSubstancePerTime);
    } else if (error.getErrorId() == libsbmlConstants.DelayUnitsNotTime) {
      exc.setCode(SBMLException.Code.DelayUnitsNotTime);
    } else if (error.getErrorId() == libsbmlConstants.EventAssignCompartmentMismatch) {
      exc.setCode(SBMLException.Code.EventAssignCompartmentMismatch);
    } else if (error.getErrorId() == libsbmlConstants.EventAssignSpeciesMismatch) {
      exc.setCode(SBMLException.Code.EventAssignSpeciesMismatch);
    } else if (error.getErrorId() == libsbmlConstants.EventAssignParameterMismatch) {
      exc.setCode(SBMLException.Code.EventAssignParameterMismatch);
    } else if (error.getErrorId() == libsbmlConstants.OverdeterminedSystem) {
      exc.setCode(SBMLException.Code.OverdeterminedSystem);
    } else if (error.getErrorId() == libsbmlConstants.InvalidModelSBOTerm) {
      exc.setCode(SBMLException.Code.InvalidModelSBOTerm);
    } else if (error.getErrorId() == libsbmlConstants.InvalidFunctionDefSBOTerm) {
      exc.setCode(SBMLException.Code.InvalidFunctionDefSBOTerm);
    } else if (error.getErrorId() == libsbmlConstants.InvalidParameterSBOTerm) {
      exc.setCode(SBMLException.Code.InvalidParameterSBOTerm);
    } else if (error.getErrorId() == libsbmlConstants.InvalidInitAssignSBOTerm) {
      exc.setCode(SBMLException.Code.InvalidInitAssignSBOTerm);
    } else if (error.getErrorId() == libsbmlConstants.InvalidRuleSBOTerm) {
      exc.setCode(SBMLException.Code.InvalidRuleSBOTerm);
    } else if (error.getErrorId() == libsbmlConstants.InvalidConstraintSBOTerm) {
      exc.setCode(SBMLException.Code.InvalidConstraintSBOTerm);
    } else if (error.getErrorId() == libsbmlConstants.InvalidReactionSBOTerm) {
      exc.setCode(SBMLException.Code.InvalidReactionSBOTerm);
    } else if (error.getErrorId() == libsbmlConstants.InvalidSpeciesReferenceSBOTerm) {
      exc.setCode(SBMLException.Code.InvalidSpeciesReferenceSBOTerm);
    } else if (error.getErrorId() == libsbmlConstants.InvalidKineticLawSBOTerm) {
      exc.setCode(SBMLException.Code.InvalidKineticLawSBOTerm);
    } else if (error.getErrorId() == libsbmlConstants.InvalidEventSBOTerm) {
      exc.setCode(SBMLException.Code.InvalidEventSBOTerm);
    } else if (error.getErrorId() == libsbmlConstants.InvalidEventAssignmentSBOTerm) {
      exc.setCode(SBMLException.Code.InvalidEventAssignmentSBOTerm);
    } else if (error.getErrorId() == libsbmlConstants.InvalidCompartmentSBOTerm) {
      exc.setCode(SBMLException.Code.InvalidCompartmentSBOTerm);
    } else if (error.getErrorId() == libsbmlConstants.InvalidSpeciesSBOTerm) {
      exc.setCode(SBMLException.Code.InvalidSpeciesSBOTerm);
    } else if (error.getErrorId() == libsbmlConstants.InvalidCompartmentTypeSBOTerm) {
      exc.setCode(SBMLException.Code.InvalidCompartmentTypeSBOTerm);
    } else if (error.getErrorId() == libsbmlConstants.InvalidSpeciesTypeSBOTerm) {
      exc.setCode(SBMLException.Code.InvalidSpeciesTypeSBOTerm);
    } else if (error.getErrorId() == libsbmlConstants.InvalidTriggerSBOTerm) {
      exc.setCode(SBMLException.Code.InvalidTriggerSBOTerm);
    } else if (error.getErrorId() == libsbmlConstants.InvalidDelaySBOTerm) {
      exc.setCode(SBMLException.Code.InvalidDelaySBOTerm);
    } else if (error.getErrorId() == libsbmlConstants.NotesNotInXHTMLNamespace) {
      exc.setCode(SBMLException.Code.NotesNotInXHTMLNamespace);
    } else if (error.getErrorId() == libsbmlConstants.NotesContainsXMLDecl) {
      exc.setCode(SBMLException.Code.NotesContainsXMLDecl);
    } else if (error.getErrorId() == libsbmlConstants.NotesContainsDOCTYPE) {
      exc.setCode(SBMLException.Code.NotesContainsDOCTYPE);
    } else if (error.getErrorId() == libsbmlConstants.InvalidNotesContent) {
      exc.setCode(SBMLException.Code.InvalidNotesContent);
    } else if (error.getErrorId() == libsbmlConstants.InvalidNamespaceOnSBML) {
      exc.setCode(SBMLException.Code.InvalidNamespaceOnSBML);
    } else if (error.getErrorId() == libsbmlConstants.MissingOrInconsistentLevel) {
      exc.setCode(SBMLException.Code.MissingOrInconsistentLevel);
    } else if (error.getErrorId() == libsbmlConstants.MissingOrInconsistentVersion) {
      exc.setCode(SBMLException.Code.MissingOrInconsistentVersion);
    } else if (error.getErrorId() == libsbmlConstants.AnnotationNotesNotAllowedLevel1) {
      exc.setCode(SBMLException.Code.AnnotationNotesNotAllowedLevel1);
    } else if (error.getErrorId() == libsbmlConstants.MissingModel) {
      exc.setCode(SBMLException.Code.MissingModel);
    } else if (error.getErrorId() == libsbmlConstants.IncorrectOrderInModel) {
      exc.setCode(SBMLException.Code.IncorrectOrderInModel);
    } else if (error.getErrorId() == libsbmlConstants.EmptyListElement) {
      exc.setCode(SBMLException.Code.EmptyListElement);
    } else if (error.getErrorId() == libsbmlConstants.NeedCompartmentIfHaveSpecies) {
      exc.setCode(SBMLException.Code.NeedCompartmentIfHaveSpecies);
    } else if (error.getErrorId() == libsbmlConstants.FunctionDefMathNotLambda) {
      exc.setCode(SBMLException.Code.FunctionDefMathNotLambda);
    } else if (error.getErrorId() == libsbmlConstants.InvalidApplyCiInLambda) {
      exc.setCode(SBMLException.Code.InvalidApplyCiInLambda);
    } else if (error.getErrorId() == libsbmlConstants.RecursiveFunctionDefinition) {
      exc.setCode(SBMLException.Code.RecursiveFunctionDefinition);
    } else if (error.getErrorId() == libsbmlConstants.InvalidCiInLambda) {
      exc.setCode(SBMLException.Code.InvalidCiInLambda);
    } else if (error.getErrorId() == libsbmlConstants.InvalidFunctionDefReturnType) {
      exc.setCode(SBMLException.Code.InvalidFunctionDefReturnType);
    } else if (error.getErrorId() == libsbmlConstants.InvalidUnitDefId) {
      exc.setCode(SBMLException.Code.InvalidUnitDefId);
    } else if (error.getErrorId() == libsbmlConstants.InvalidSubstanceRedefinition) {
      exc.setCode(SBMLException.Code.InvalidSubstanceRedefinition);
    } else if (error.getErrorId() == libsbmlConstants.InvalidLengthRedefinition) {
      exc.setCode(SBMLException.Code.InvalidLengthRedefinition);
    } else if (error.getErrorId() == libsbmlConstants.InvalidAreaRedefinition) {
      exc.setCode(SBMLException.Code.InvalidAreaRedefinition);
    } else if (error.getErrorId() == libsbmlConstants.InvalidTimeRedefinition) {
      exc.setCode(SBMLException.Code.InvalidTimeRedefinition);
    } else if (error.getErrorId() == libsbmlConstants.InvalidVolumeRedefinition) {
      exc.setCode(SBMLException.Code.InvalidVolumeRedefinition);
    } else if (error.getErrorId() == libsbmlConstants.VolumeLitreDefExponentNotOne) {
      exc.setCode(SBMLException.Code.VolumeLitreDefExponentNotOne);
    } else if (error.getErrorId() == libsbmlConstants.VolumeMetreDefExponentNot3) {
      exc.setCode(SBMLException.Code.VolumeMetreDefExponentNot3);
    } else if (error.getErrorId() == libsbmlConstants.EmptyListOfUnits) {
      exc.setCode(SBMLException.Code.EmptyListOfUnits);
    } else if (error.getErrorId() == libsbmlConstants.InvalidUnitKind) {
      exc.setCode(SBMLException.Code.InvalidUnitKind);
    } else if (error.getErrorId() == libsbmlConstants.OffsetNoLongerValid) {
      exc.setCode(SBMLException.Code.OffsetNoLongerValid);
    } else if (error.getErrorId() == libsbmlConstants.CelsiusNoLongerValid) {
      exc.setCode(SBMLException.Code.CelsiusNoLongerValid);
    } else if (error.getErrorId() == libsbmlConstants.ZeroDimensionalCompartmentSize) {
      exc.setCode(SBMLException.Code.ZeroDimensionalCompartmentSize);
    } else if (error.getErrorId() == libsbmlConstants.ZeroDimensionalCompartmentUnits) {
      exc.setCode(SBMLException.Code.ZeroDimensionalCompartmentUnits);
    } else if (error.getErrorId() == libsbmlConstants.ZeroDimensionalCompartmentConst) {
      exc.setCode(SBMLException.Code.ZeroDimensionalCompartmentConst);
    } else if (error.getErrorId() == libsbmlConstants.UndefinedOutsideCompartment) {
      exc.setCode(SBMLException.Code.UndefinedOutsideCompartment);
    } else if (error.getErrorId() == libsbmlConstants.RecursiveCompartmentContainment) {
      exc.setCode(SBMLException.Code.RecursiveCompartmentContainment);
    } else if (error.getErrorId() == libsbmlConstants.ZeroDCompartmentContainment) {
      exc.setCode(SBMLException.Code.ZeroDCompartmentContainment);
    } else if (error.getErrorId() == libsbmlConstants.Invalid1DCompartmentUnits) {
      exc.setCode(SBMLException.Code.Invalid1DCompartmentUnits);
    } else if (error.getErrorId() == libsbmlConstants.Invalid2DCompartmentUnits) {
      exc.setCode(SBMLException.Code.Invalid2DCompartmentUnits);
    } else if (error.getErrorId() == libsbmlConstants.Invalid3DCompartmentUnits) {
      exc.setCode(SBMLException.Code.Invalid3DCompartmentUnits);
    } else if (error.getErrorId() == libsbmlConstants.InvalidCompartmentTypeRef) {
      exc.setCode(SBMLException.Code.InvalidCompartmentTypeRef);
    } else if (error.getErrorId() == libsbmlConstants.InvalidSpeciesCompartmentRef) {
      exc.setCode(SBMLException.Code.InvalidSpeciesCompartmentRef);
    } else if (error.getErrorId() == libsbmlConstants.HasOnlySubsNoSpatialUnits) {
      exc.setCode(SBMLException.Code.HasOnlySubsNoSpatialUnits);
    } else if (error.getErrorId() == libsbmlConstants.NoSpatialUnitsInZeroD) {
      exc.setCode(SBMLException.Code.NoSpatialUnitsInZeroD);
    } else if (error.getErrorId() == libsbmlConstants.NoConcentrationInZeroD) {
      exc.setCode(SBMLException.Code.NoConcentrationInZeroD);
    } else if (error.getErrorId() == libsbmlConstants.SpatialUnitsInOneD) {
      exc.setCode(SBMLException.Code.SpatialUnitsInOneD);
    } else if (error.getErrorId() == libsbmlConstants.SpatialUnitsInTwoD) {
      exc.setCode(SBMLException.Code.SpatialUnitsInTwoD);
    } else if (error.getErrorId() == libsbmlConstants.SpatialUnitsInThreeD) {
      exc.setCode(SBMLException.Code.SpatialUnitsInThreeD);
    } else if (error.getErrorId() == libsbmlConstants.InvalidSpeciesSusbstanceUnits) {
      exc.setCode(SBMLException.Code.InvalidSpeciesSusbstanceUnits);
    } else if (error.getErrorId() == libsbmlConstants.BothAmountAndConcentrationSet) {
      exc.setCode(SBMLException.Code.BothAmountAndConcentrationSet);
    } else if (error.getErrorId() == libsbmlConstants.NonBoundarySpeciesAssignedAndUsed) {
      exc.setCode(SBMLException.Code.NonBoundarySpeciesAssignedAndUsed);
    } else if (error.getErrorId() == libsbmlConstants.NonConstantSpeciesUsed) {
      exc.setCode(SBMLException.Code.NonConstantSpeciesUsed);
    } else if (error.getErrorId() == libsbmlConstants.InvalidSpeciesTypeRef) {
      exc.setCode(SBMLException.Code.InvalidSpeciesTypeRef);
    } else if (error.getErrorId() == libsbmlConstants.MultSpeciesSameTypeInCompartment) {
      exc.setCode(SBMLException.Code.MultSpeciesSameTypeInCompartment);
    } else if (error.getErrorId() == libsbmlConstants.MissingSpeciesCompartment) {
      exc.setCode(SBMLException.Code.MissingSpeciesCompartment);
    } else if (error.getErrorId() == libsbmlConstants.SpatialSizeUnitsRemoved) {
      exc.setCode(SBMLException.Code.SpatialSizeUnitsRemoved);
    } else if (error.getErrorId() == libsbmlConstants.InvalidParameterUnits) {
      exc.setCode(SBMLException.Code.InvalidParameterUnits);
    } else if (error.getErrorId() == libsbmlConstants.InvalidInitAssignSymbol) {
      exc.setCode(SBMLException.Code.InvalidInitAssignSymbol);
    } else if (error.getErrorId() == libsbmlConstants.MultipleInitAssignments) {
      exc.setCode(SBMLException.Code.MultipleInitAssignments);
    } else if (error.getErrorId() == libsbmlConstants.InitAssignmentAndRuleForSameId) {
      exc.setCode(SBMLException.Code.InitAssignmentAndRuleForSameId);
    } else if (error.getErrorId() == libsbmlConstants.InvalidAssignRuleVariable) {
      exc.setCode(SBMLException.Code.InvalidAssignRuleVariable);
    } else if (error.getErrorId() == libsbmlConstants.InvalidRateRuleVariable) {
      exc.setCode(SBMLException.Code.InvalidRateRuleVariable);
    } else if (error.getErrorId() == libsbmlConstants.AssignmentToConstantEntity) {
      exc.setCode(SBMLException.Code.AssignmentToConstantEntity);
    } else if (error.getErrorId() == libsbmlConstants.RateRuleForConstantEntity) {
      exc.setCode(SBMLException.Code.RateRuleForConstantEntity);
    } else if (error.getErrorId() == libsbmlConstants.CircularRuleDependency) {
      exc.setCode(SBMLException.Code.CircularRuleDependency);
    } else if (error.getErrorId() == libsbmlConstants.ConstraintMathNotBoolean) {
      exc.setCode(SBMLException.Code.ConstraintMathNotBoolean);
    } else if (error.getErrorId() == libsbmlConstants.IncorrectOrderInConstraint) {
      exc.setCode(SBMLException.Code.IncorrectOrderInConstraint);
    } else if (error.getErrorId() == libsbmlConstants.ConstraintNotInXHTMLNamespace) {
      exc.setCode(SBMLException.Code.ConstraintNotInXHTMLNamespace);
    } else if (error.getErrorId() == libsbmlConstants.ConstraintContainsXMLDecl) {
      exc.setCode(SBMLException.Code.ConstraintContainsXMLDecl);
    } else if (error.getErrorId() == libsbmlConstants.ConstraintContainsDOCTYPE) {
      exc.setCode(SBMLException.Code.ConstraintContainsDOCTYPE);
    } else if (error.getErrorId() == libsbmlConstants.InvalidConstraintContent) {
      exc.setCode(SBMLException.Code.InvalidConstraintContent);
    } else if (error.getErrorId() == libsbmlConstants.NoReactantsOrProducts) {
      exc.setCode(SBMLException.Code.NoReactantsOrProducts);
    } else if (error.getErrorId() == libsbmlConstants.IncorrectOrderInReaction) {
      exc.setCode(SBMLException.Code.IncorrectOrderInReaction);
    } else if (error.getErrorId() == libsbmlConstants.EmptyListInReaction) {
      exc.setCode(SBMLException.Code.EmptyListInReaction);
    } else if (error.getErrorId() == libsbmlConstants.InvalidReactantsProductsList) {
      exc.setCode(SBMLException.Code.InvalidReactantsProductsList);
    } else if (error.getErrorId() == libsbmlConstants.InvalidModifiersList) {
      exc.setCode(SBMLException.Code.InvalidModifiersList);
    } else if (error.getErrorId() == libsbmlConstants.InvalidSpeciesReference) {
      exc.setCode(SBMLException.Code.InvalidSpeciesReference);
    } else if (error.getErrorId() == libsbmlConstants.BothStoichiometryAndMath) {
      exc.setCode(SBMLException.Code.BothStoichiometryAndMath);
    } else if (error.getErrorId() == libsbmlConstants.UndeclaredSpeciesRef) {
      exc.setCode(SBMLException.Code.UndeclaredSpeciesRef);
    } else if (error.getErrorId() == libsbmlConstants.IncorrectOrderInKineticLaw) {
      exc.setCode(SBMLException.Code.IncorrectOrderInKineticLaw);
    } else if (error.getErrorId() == libsbmlConstants.EmptyListInKineticLaw) {
      exc.setCode(SBMLException.Code.EmptyListInKineticLaw);
    } else if (error.getErrorId() == libsbmlConstants.NonConstantLocalParameter) {
      exc.setCode(SBMLException.Code.NonConstantLocalParameter);
    } else if (error.getErrorId() == libsbmlConstants.SubsUnitsNoLongerValid) {
      exc.setCode(SBMLException.Code.SubsUnitsNoLongerValid);
    } else if (error.getErrorId() == libsbmlConstants.TimeUnitsNoLongerValid) {
      exc.setCode(SBMLException.Code.TimeUnitsNoLongerValid);
    } else if (error.getErrorId() == libsbmlConstants.UndeclaredSpeciesInStoichMath) {
      exc.setCode(SBMLException.Code.UndeclaredSpeciesInStoichMath);
    } else if (error.getErrorId() == libsbmlConstants.MissingTriggerInEvent) {
      exc.setCode(SBMLException.Code.MissingTriggerInEvent);
    } else if (error.getErrorId() == libsbmlConstants.TriggerMathNotBoolean) {
      exc.setCode(SBMLException.Code.TriggerMathNotBoolean);
    } else if (error.getErrorId() == libsbmlConstants.MissingEventAssignment) {
      exc.setCode(SBMLException.Code.MissingEventAssignment);
    } else if (error.getErrorId() == libsbmlConstants.TimeUnitsEvent) {
      exc.setCode(SBMLException.Code.TimeUnitsEvent);
    } else if (error.getErrorId() == libsbmlConstants.IncorrectOrderInEvent) {
      exc.setCode(SBMLException.Code.IncorrectOrderInEvent);
    } else if (error.getErrorId() == libsbmlConstants.ValuesFromTriggerTimeNeedDelay) {
      exc.setCode(SBMLException.Code.ValuesFromTriggerTimeNeedDelay);
    } else if (error.getErrorId() == libsbmlConstants.InvalidEventAssignmentVariable) {
      exc.setCode(SBMLException.Code.InvalidEventAssignmentVariable);
    } else if (error.getErrorId() == libsbmlConstants.EventAssignmentForConstantEntity) {
      exc.setCode(SBMLException.Code.EventAssignmentForConstantEntity);
    } else if (error.getErrorId() == libsbmlConstants.CompartmentShouldHaveSize) {
      exc.setCode(SBMLException.Code.CompartmentShouldHaveSize);
    } else if (error.getErrorId() == libsbmlConstants.ParameterShouldHaveUnits) {
      exc.setCode(SBMLException.Code.ParameterShouldHaveUnits);
    } else if (error.getErrorId() == libsbmlConstants.LocalParameterShadowsId) {
      exc.setCode(SBMLException.Code.LocalParameterShadowsId);
    } else if (error.getErrorId() == libsbmlConstants.CannotConvertToL1V1) {
      exc.setCode(SBMLException.Code.CannotConvertToL1V1);
    } else if (error.getErrorId() == libsbmlConstants.NoEventsInL1) {
      exc.setCode(SBMLException.Code.NoEventsInL1);
    } else if (error.getErrorId() == libsbmlConstants.NoFunctionDefinitionsInL1) {
      exc.setCode(SBMLException.Code.NoFunctionDefinitionsInL1);
    } else if (error.getErrorId() == libsbmlConstants.NoConstraintsInL1) {
      exc.setCode(SBMLException.Code.NoConstraintsInL1);
    } else if (error.getErrorId() == libsbmlConstants.NoInitialAssignmentsInL1) {
      exc.setCode(SBMLException.Code.NoInitialAssignmentsInL1);
    } else if (error.getErrorId() == libsbmlConstants.NoSpeciesTypesInL1) {
      exc.setCode(SBMLException.Code.NoSpeciesTypesInL1);
    } else if (error.getErrorId() == libsbmlConstants.NoCompartmentTypeInL1) {
      exc.setCode(SBMLException.Code.NoCompartmentTypeInL1);
    } else if (error.getErrorId() == libsbmlConstants.NoNon3DCompartmentsInL1) {
      exc.setCode(SBMLException.Code.NoNon3DComparmentsInL1);
    } else if (error.getErrorId() == libsbmlConstants.NoFancyStoichiometryMathInL1) {
      exc.setCode(SBMLException.Code.NoFancyStoichiometryMathInL1);
    } else if (error.getErrorId() == libsbmlConstants.NoNonIntegerStoichiometryInL1) {
      exc.setCode(SBMLException.Code.NoNonIntegerStoichiometryInL1);
    } else if (error.getErrorId() == libsbmlConstants.NoUnitMultipliersOrOffsetsInL1) {
      exc.setCode(SBMLException.Code.NoUnitMultipliersOrOffsetsInL1);
    } else if (error.getErrorId() == libsbmlConstants.SpeciesCompartmentRequiredInL1) {
      exc.setCode(SBMLException.Code.SpeciesCompartmentRequiredInL1);
    } else if (error.getErrorId() == libsbmlConstants.NoSpeciesSpatialSizeUnitsInL1) {
      exc.setCode(SBMLException.Code.NoSpeciesSpatialSizeUnitsInL1);
    } else if (error.getErrorId() == libsbmlConstants.NoSBOTermsInL1) {
      exc.setCode(SBMLException.Code.NoSBOTermsInL1);
    } else if (error.getErrorId() == libsbmlConstants.StrictUnitsRequiredInL1) {
      exc.setCode(SBMLException.Code.StrictUnitsRequiredInL1);
    } else if (error.getErrorId() == libsbmlConstants.NoConstraintsInL2v1) {
      exc.setCode(SBMLException.Code.NoConstraintsInL2v1);
    } else if (error.getErrorId() == libsbmlConstants.NoInitialAssignmentsInL2v1) {
      exc.setCode(SBMLException.Code.NoInitialAssignmentsInL2v1);
    } else if (error.getErrorId() == libsbmlConstants.NoSpeciesTypeInL2v1) {
      exc.setCode(SBMLException.Code.NoSpeciesTypeInL2v1);
    } else if (error.getErrorId() == libsbmlConstants.NoCompartmentTypeInL2v1) {
      exc.setCode(SBMLException.Code.NoCompartmentTypeInL2v1);
    } else if (error.getErrorId() == libsbmlConstants.NoSBOTermsInL2v1) {
      exc.setCode(SBMLException.Code.NoSBOTermsInL2v1);
    } else if (error.getErrorId() == libsbmlConstants.NoIdOnSpeciesReferenceInL2v1) {
      exc.setCode(SBMLException.Code.NoIdOnSpeciesReferenceInL2v1);
    } else if (error.getErrorId() == libsbmlConstants.NoDelayedEventAssignmentInL2v1) {
      exc.setCode(SBMLException.Code.NoDelayedEventAssignmentInL2v1);
    } else if (error.getErrorId() == libsbmlConstants.StrictUnitsRequiredInL2v1) {
      exc.setCode(SBMLException.Code.StrictUnitsRequiredInL2v1);
    } else if (error.getErrorId() == libsbmlConstants.SBOTermNotUniversalInL2v2) {
      exc.setCode(SBMLException.Code.SBOTermNotUniversalInL2v2);
    } else if (error.getErrorId() == libsbmlConstants.NoUnitOffsetInL2v2) {
      exc.setCode(SBMLException.Code.NoUnitOffsetInL2v2);
    } else if (error.getErrorId() == libsbmlConstants.NoKineticLawTimeUnitsInL2v2) {
      exc.setCode(SBMLException.Code.NoKineticLawTimeUnitsInL2v2);
    } else if (error.getErrorId() == libsbmlConstants.NoKineticLawSubstanceUnitsInL2v2) {
      exc.setCode(SBMLException.Code.NoKineticLawSubstanceUnitsInL2v2);
    } else if (error.getErrorId() == libsbmlConstants.NoDelayedEventAssignmentInL2v2) {
      exc.setCode(SBMLException.Code.NoDelayedEventAssignmentInL2v2);
    } else if (error.getErrorId() == libsbmlConstants.ModelSBOBranchChangedBeyondL2v2) {
      exc.setCode(SBMLException.Code.ModelSBOBranchChangedBeyondL2v2);
    } else if (error.getErrorId() == libsbmlConstants.StrictUnitsRequiredInL2v2) {
      exc.setCode(SBMLException.Code.StrictUnitsRequiredInL2v2);
    } else if (error.getErrorId() == libsbmlConstants.StrictSBORequiredInL2v2) {
      exc.setCode(SBMLException.Code.StrictSBORequiredInL2v2);
    } else if (error.getErrorId() == libsbmlConstants.DuplicateAnnotationInvalidInL2v2) {
      exc.setCode(SBMLException.Code.DuplicateAnnotationInvalidInL2v2);
    } else if (error.getErrorId() == libsbmlConstants.NoUnitOffsetInL2v3) {
      exc.setCode(SBMLException.Code.NoUnitOffsetInL2v3);
    } else if (error.getErrorId() == libsbmlConstants.NoKineticLawTimeUnitsInL2v3) {
      exc.setCode(SBMLException.Code.NoKineticLawTimeUnitsInL2v3);
    } else if (error.getErrorId() == libsbmlConstants.NoKineticLawSubstanceUnitsInL2v3) {
      exc.setCode(SBMLException.Code.NoKineticLawSubstanceUnitsInL2v3);
    } else if (error.getErrorId() == libsbmlConstants.NoSpeciesSpatialSizeUnitsInL2v3) {
      exc.setCode(SBMLException.Code.NoSpeciesSpatialSizeUnitsInL2v3);
    } else if (error.getErrorId() == libsbmlConstants.NoEventTimeUnitsInL2v3) {
      exc.setCode(SBMLException.Code.NoEventTimeUnitsInL2v3);
    } else if (error.getErrorId() == libsbmlConstants.NoDelayedEventAssignmentInL2v3) {
      exc.setCode(SBMLException.Code.NoDelayedEventAssignmentInL2v3);
    } else if (error.getErrorId() == libsbmlConstants.ModelSBOBranchChangedBeyondL2v3) {
      exc.setCode(SBMLException.Code.ModelSBOBranchChangedBeyondL2v3);
    } else if (error.getErrorId() == libsbmlConstants.StrictUnitsRequiredInL2v3) {
      exc.setCode(SBMLException.Code.StrictUnitsRequiredInL2v3);
    } else if (error.getErrorId() == libsbmlConstants.StrictSBORequiredInL2v3) {
      exc.setCode(SBMLException.Code.StrictSBORequiredInL2v3);
    } else if (error.getErrorId() == libsbmlConstants.DuplicateAnnotationInvalidInL2v3) {
      exc.setCode(SBMLException.Code.DuplicateAnnotationInvalidInL2v3);
    } else if (error.getErrorId() == libsbmlConstants.NoUnitOffsetInL2v4) {
      exc.setCode(SBMLException.Code.NoUnitOffsetInL2v4);
    } else if (error.getErrorId() == libsbmlConstants.NoKineticLawTimeUnitsInL2v4) {
      exc.setCode(SBMLException.Code.NoKineticLawTimeUnitsInL2v4);
    } else if (error.getErrorId() == libsbmlConstants.NoKineticLawSubstanceUnitsInL2v4) {
      exc.setCode(SBMLException.Code.NoKineticLawSubstanceUnitsInL2v4);
    } else if (error.getErrorId() == libsbmlConstants.NoSpeciesSpatialSizeUnitsInL2v4) {
      exc.setCode(SBMLException.Code.NoSpeciesSpatialSizeUnitsInL2v4);
    } else if (error.getErrorId() == libsbmlConstants.NoEventTimeUnitsInL2v4) {
      exc.setCode(SBMLException.Code.NoEventTimeUnitsInL2v4);
    } else if (error.getErrorId() == libsbmlConstants.ModelSBOBranchChangedInL2v4) {
      exc.setCode(SBMLException.Code.ModelSBOBranchChangedInL2v4);
    } else if (error.getErrorId() == libsbmlConstants.DuplicateAnnotationInvalidInL2v4) {
      exc.setCode(SBMLException.Code.DuplicateAnnotationInvalidInL2v4);
    } else if (error.getErrorId() == libsbmlConstants.InvalidSBMLLevelVersion) {
      exc.setCode(SBMLException.Code.InvalidSBMLLevelVersion);
    } else if (error.getErrorId() == libsbmlConstants.InvalidRuleOrdering) {
      exc.setCode(SBMLException.Code.InvalidRuleOrdering);
    } else if (error.getErrorId() == libsbmlConstants.SubsUnitsAllowedInKL) {
      exc.setCode(SBMLException.Code.SubsUnitsAllowedInKL);
    } else if (error.getErrorId() == libsbmlConstants.TimeUnitsAllowedInKL) {
      exc.setCode(SBMLException.Code.TimeUnitsAllowedInKL);
    } else if (error.getErrorId() == libsbmlConstants.FormulaInLevel1KL) {
      exc.setCode(SBMLException.Code.FormulaInLevel1KL);
    } else if (error.getErrorId() == libsbmlConstants.TimeUnitsRemoved) {
      exc.setCode(SBMLException.Code.TimeUnitsRemoved);
    } else if (error.getErrorId() == libsbmlConstants.BadMathML) {
      exc.setCode(SBMLException.Code.BadMathML);
    } else if (error.getErrorId() == libsbmlConstants.FailedMathMLReadOfDouble) {
      exc.setCode(SBMLException.Code.FailedMathMLReadOfDouble);
    } else if (error.getErrorId() == libsbmlConstants.FailedMathMLReadOfInteger) {
      exc.setCode(SBMLException.Code.FailedMathMLReadOfInteger);
    } else if (error.getErrorId() == libsbmlConstants.FailedMathMLReadOfExponential) {
      exc.setCode(SBMLException.Code.FailedMathMLReadOfExponential);
    } else if (error.getErrorId() == libsbmlConstants.FailedMathMLReadOfRational) {
      exc.setCode(SBMLException.Code.FailedMathMLReadOfRational);
    } else if (error.getErrorId() == libsbmlConstants.BadMathMLNodeType) {
      exc.setCode(SBMLException.Code.BadMathMLNodeType);
    } else if (error.getErrorId() == libsbmlConstants.NoTimeSymbolInFunctionDef) {
      exc.setCode(SBMLException.Code.NoTimeSymbolInFunctionDef);
    } else if (error.getErrorId() == libsbmlConstants.UndeclaredUnits) {
      exc.setCode(SBMLException.Code.UndeclaredUnits);
    } else if (error.getErrorId() == libsbmlConstants.UnrecognisedSBOTerm) {
      exc.setCode(SBMLException.Code.UnrecognisedSBOTerm);
    } else if (error.getErrorId() == libsbmlConstants.ObseleteSBOTerm) {
      exc.setCode(SBMLException.Code.ObseleteSBOTerm);
    } else if (error.getErrorId() == libsbmlConstants.IncorrectCompartmentSpatialDimensions) {
      exc.setCode(SBMLException.Code.IncorrectCompartmentSpatialDimensions);
    } else if (error.getErrorId() == libsbmlConstants.CompartmentTypeNotValidAttribute) {
      exc.setCode(SBMLException.Code.CompartmentTypeNotValidAttribute);
    } else if (error.getErrorId() == libsbmlConstants.ConstantNotValidAttribute) {
      exc.setCode(SBMLException.Code.ConstantNotValidAttribute);
    } else if (error.getErrorId() == libsbmlConstants.MetaIdNotValidAttribute) {
      exc.setCode(SBMLException.Code.MetaIdNotValidAttribute);
    } else if (error.getErrorId() == libsbmlConstants.SBOTermNotValidAttributeBeforeL2V3) {
      exc.setCode(SBMLException.Code.SBOTermNotValidAttributeBeforeL2V3);
    } else if (error.getErrorId() == libsbmlConstants.InvalidL1CompartmentUnits) {
      exc.setCode(SBMLException.Code.InvalidL1CompartmentUnits);
    } else if (error.getErrorId() == libsbmlConstants.L1V1CompartmentVolumeReqd) {
      exc.setCode(SBMLException.Code.L1V1CompartmentVolumeReqd);
    } else if (error.getErrorId() == libsbmlConstants.CompartmentTypeNotValidComponent) {
      exc.setCode(SBMLException.Code.CompartmentTypeNotValidComponent);
    } else if (error.getErrorId() == libsbmlConstants.ConstraintNotValidComponent) {
      exc.setCode(SBMLException.Code.ConstraintNotValidComponent);
    } else if (error.getErrorId() == libsbmlConstants.EventNotValidComponent) {
      exc.setCode(SBMLException.Code.EventNotValidComponent);
    } else if (error.getErrorId() == libsbmlConstants.SBOTermNotValidAttributeBeforeL2V2) {
      exc.setCode(SBMLException.Code.SBOTermNotValidAttributeBeforeL2V2);
    } else if (error.getErrorId() == libsbmlConstants.FuncDefNotValidComponent) {
      exc.setCode(SBMLException.Code.FuncDefNotValidComponent);
    } else if (error.getErrorId() == libsbmlConstants.InitialAssignNotValidComponent) {
      exc.setCode(SBMLException.Code.InitialAssignNotValidComponent);
    } else if (error.getErrorId() == libsbmlConstants.VariableNotValidAttribute) {
      exc.setCode(SBMLException.Code.VariableNotValidAttribute);
    } else if (error.getErrorId() == libsbmlConstants.UnitsNotValidAttribute) {
      exc.setCode(SBMLException.Code.UnitsNotValidAttribute);
    } else if (error.getErrorId() == libsbmlConstants.ConstantSpeciesNotValidAttribute) {
      exc.setCode(SBMLException.Code.ConstantSpeciesNotValidAttribute);
    } else if (error.getErrorId() == libsbmlConstants.SpatialSizeUnitsNotValidAttribute) {
      exc.setCode(SBMLException.Code.SpatialSizeUnitsNotValidAttribute);
    } else if (error.getErrorId() == libsbmlConstants.SpeciesTypeNotValidAttribute) {
      exc.setCode(SBMLException.Code.SpeciesTypeNotValidAttribute);
    } else if (error.getErrorId() == libsbmlConstants.HasOnlySubsUnitsNotValidAttribute) {
      exc.setCode(SBMLException.Code.HasOnlySubsUnitsNotValidAttribute);
    } else if (error.getErrorId() == libsbmlConstants.IdNotValidAttribute) {
      exc.setCode(SBMLException.Code.IdNotValidAttribute);
    } else if (error.getErrorId() == libsbmlConstants.NameNotValidAttribute) {
      exc.setCode(SBMLException.Code.NameNotValidAttribute);
    } else if (error.getErrorId() == libsbmlConstants.SpeciesTypeNotValidComponent) {
      exc.setCode(SBMLException.Code.SpeciesTypeNotValidComponent);
    } else if (error.getErrorId() == libsbmlConstants.StoichiometryMathNotValidComponent) {
      exc.setCode(SBMLException.Code.StoichiometryMathNotValidComponent);
    } else if (error.getErrorId() == libsbmlConstants.MultiplierNotValidAttribute) {
      exc.setCode(SBMLException.Code.MultiplierNotValidAttribute);
    } else if (error.getErrorId() == libsbmlConstants.OffsetNotValidAttribute) {
      exc.setCode(SBMLException.Code.OffsetNotValidAttribute);
    }

    exc.setFatal(error.isFatal());
    exc.setError(error.isError());
    exc.setInfo(error.isInfo());
    exc.setInternal(error.isInternal());
    exc.setSystem(error.isSystem());
    exc.setWarning(error.isWarning());
    exc.setXML(error.isXML());

    return exc;
  }

  /**
   * 
   * @param delay
   * @return
   */
  public static Delay readDelay(org.sbml.libsbml.Delay delay) {
    Delay de = new Delay((int) delay.getLevel(), (int) delay.getVersion());
    LibSBMLUtils.transferSBaseProperties(delay, de);
    if (delay.isSetMath()) {
      de.setMath(LibSBMLUtils.convert(delay.getMath(), de));
    }
    return de;
  }

  /**
   * 
   * @param stoichiometryMath
   * @return
   * @throws XMLStreamException
   */
  public static StoichiometryMath readStoichiometricMath(org.sbml.libsbml.StoichiometryMath stoichiometryMath) throws XMLStreamException {
    StoichiometryMath sm = new StoichiometryMath((int) stoichiometryMath.getLevel(), (int) stoichiometryMath.getVersion());
    LibSBMLUtils.transferSBaseProperties(sm, stoichiometryMath);
    if (stoichiometryMath.isSetMath()) {
      sm.setMath(LibSBMLUtils.convert(stoichiometryMath.getMath(), sm));
    }
    return sm;
  }

  /**
   * 
   * @param trigger
   * @return
   * @throws XMLStreamException
   */
  public static Trigger readTrigger(org.sbml.libsbml.Trigger trigger) throws XMLStreamException {
    Trigger trig = new Trigger((int) trigger.getLevel(), (int) trigger.getVersion());
    LibSBMLUtils.transferSBaseProperties(trig, trigger);
    if (trigger.isSetInitialValue()) {
      trig.setInitialValue(trigger.getInitialValue());
    }
    if (trigger.isSetPersistent()) {
      trig.setPersistent(trigger.getPersistent());
    }
    if (trigger.isSetMath()) {
      trig.setMath(LibSBMLUtils.convert(trigger.getMath(), trig));
    }
    return trig;
  }

  /**
   * 
   */
  protected LinkedList<TreeNodeChangeListener> listOfTreeNodeChangeListeners;

  /**
   * 
   */
  private Model model;

  /**
   * 
   */
  private ProgressListener listener;

  /**
   * 
   */
  private Set<org.sbml.libsbml.SBMLDocument> setOfDocuments;

  /**
   * 
   */
  public LibSBMLReader() {
    listOfTreeNodeChangeListeners = new LinkedList<TreeNodeChangeListener>();
    setOfDocuments = new HashSet<org.sbml.libsbml.SBMLDocument>();
  }

  /**
   * 
   * @param sb
   */
  private void addAllTreeNodeChangeListenersTo(SBase sb) {
    for (TreeNodeChangeListener listener : listOfTreeNodeChangeListeners) {
      sb.addTreeNodeChangeListener(listener);
    }
  }

  /**
   * 
   * @param sbcl
   */
  public void addTreeNodeChangeListener(TreeNodeChangeListener sbcl) {
    if (!listOfTreeNodeChangeListeners.contains(sbcl)) {
      listOfTreeNodeChangeListeners.add(sbcl);
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.SBMLReader#readModel(java.lang.Object)
   */
  @Override
  public Model convertModel(org.sbml.libsbml.Model originalModel) throws XMLStreamException {

    int total = 0, curr = 0;

    total = (int) originalModel.getNumCVTerms();
    total += originalModel.getNumUnitDefinitions();
    total += originalModel.getNumFunctionDefinitions();
    total += originalModel.getNumCompartmentTypes();
    total += originalModel.getNumSpeciesTypes();
    total += originalModel.getNumCompartments();
    total += originalModel.getNumSpecies();
    total += originalModel.getNumParameters();
    total += originalModel.getNumInitialAssignments();
    total += originalModel.getNumRules();
    total += originalModel.getNumConstraints();
    total += originalModel.getNumReactions();
    total += originalModel.getNumEvents();

    if (listener != null) {
      listener.progressStart(total);
    }

    model = new Model(originalModel.getId(), (int) originalModel.getLevel(), (int) originalModel.getVersion());
    LibSBMLUtils.transferNamedSBaseProperties(originalModel, model);
    if (listener != null) {
      curr += originalModel.getNumCVTerms();
      listener.progressUpdate(curr, null);
    }

    int i;
    for (i = 0; i < originalModel.getNumUnitDefinitions(); i++) {
      model.addUnitDefinition(readUnitDefinition(originalModel.getUnitDefinition(i)));
      if (listener != null) {
        listener.progressUpdate(++curr, "Unit definitions");
      }
    }
    if (originalModel.isSetAreaUnits()) {
      model.setAreaUnits(originalModel.getAreaUnits());
    }
    if (originalModel.isSetConversionFactor()) {
      model.setConversionFactor(originalModel.getConversionFactor());
    }
    if (originalModel.isSetExtentUnits()) {
      model.setExtentUnits(originalModel.getExtentUnits());
    }
    if (originalModel.isSetLengthUnits()) {
      model.setLengthUnits(originalModel.getExtentUnits());
    }
    if (originalModel.isSetSubstanceUnits()) {
      model.setSubstanceUnits(originalModel.getSubstanceUnits());
    }
    if (originalModel.isSetTimeUnits()) {
      model.setTimeUnits(originalModel.getTimeUnits());
    }
    if (originalModel.isSetVolumeUnits()) {
      model.setVolumeUnits(originalModel.getVolumeUnits());
    }

    // This is something, libSBML wouldn't do...
    SBMLtools.addPredefinedUnitDefinitions(model);
    for (i = 0; i < originalModel.getNumFunctionDefinitions(); i++) {
      model.addFunctionDefinition(readFunctionDefinition(originalModel.getFunctionDefinition(i)));
      if (listener != null) {
        listener.progressUpdate(++curr, "Function definitions");
      }
    }
    for (i = 0; i < originalModel.getNumCompartmentTypes(); i++) {
      model.addCompartmentType(readCompartmentType(originalModel.getCompartmentType(i)));
      if (listener != null) {
        listener.progressUpdate(++curr, "Compartment types");
      }
    }
    for (i = 0; i < originalModel.getNumSpeciesTypes(); i++) {
      model.addSpeciesType(readSpeciesType(originalModel.getSpeciesType(i)));
      if (listener != null) {
        listener.progressUpdate(++curr, "Species types");
      }
    }
    for (i = 0; i < originalModel.getNumCompartments(); i++) {
      model.addCompartment(readCompartment(originalModel.getCompartment(i)));
      if (listener != null) {
        listener.progressUpdate(++curr, "Compartments");
      }
    }
    for (i = 0; i < originalModel.getNumSpecies(); i++) {
      model.addSpecies(readSpecies(originalModel.getSpecies(i)));
      if (listener != null) {
        listener.progressUpdate(++curr, "Species");
      }
    }
    for (i = 0; i < originalModel.getNumParameters(); i++) {
      model.addParameter(readParameter(originalModel.getParameter(i)));
      if (listener != null) {
        listener.progressUpdate(++curr, "Parameters");
      }
    }
    for (i = 0; i < originalModel.getNumInitialAssignments(); i++) {
      model.addInitialAssignment(readInitialAssignment(originalModel.getInitialAssignment(i)));
      if (listener != null) {
        listener.progressUpdate(++curr, "Initial assignments");
      }
    }
    for (i = 0; i < originalModel.getNumRules(); i++) {
      model.addRule(readRule(originalModel.getRule(i)));
      if (listener != null) {
        listener.progressUpdate(++curr, "Rules");
      }
    }
    for (i = 0; i < originalModel.getNumConstraints(); i++) {
      model.addConstraint(readConstraint(originalModel.getConstraint(i)));
      if (listener != null) {
        listener.progressUpdate(++curr, "Constraints");
      }
    }
    for (i = 0; i < originalModel.getNumReactions(); i++) {
      model.addReaction(readReaction(originalModel.getReaction(i)));
      if (listener != null) {
        listener.progressUpdate(++curr, "Reactions");
      }
    }
    for (i = 0; i < originalModel.getNumEvents(); i++) {
      model.addEvent(readEvent(originalModel.getEvent(i)));
      if (listener != null) {
        listener.progressUpdate(++curr, "Events");
      }
    }

    if (originalModel.getNumPlugins() > 0) {
      // TODO: Implement package support
      org.sbml.libsbml.SBasePlugin plugin = originalModel.getPlugin("layout");
      if ((plugin == null) || (plugin.getListOfAllElements().getSize() > 0)) {
        logger.warn(bundle.getString("PLUGINS_ARE_IGNORED"));
      }
    }

    addAllTreeNodeChangeListenersTo(model);

    if (listener != null) {
      listener.progressFinish();
    }

    return model;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBMLInputConverter#convertSBMLDocument(java.io.File)
   */
  @Override
  public SBMLDocument convertSBMLDocument(File sbmlFile) throws Exception {
    return convertSBMLDocument(readSBMLfromFile(sbmlFile.getAbsolutePath()));
  }

  /**
   * 
   * @param libDoc
   * @return
   * @throws Exception
   */
  public SBMLDocument convertSBMLDocument(org.sbml.libsbml.SBMLDocument libDoc) throws Exception {
    SBMLDocument doc = new SBMLDocument((int) libDoc.getLevel(), (int) libDoc.getVersion());
    LibSBMLUtils.transferSBaseProperties(libDoc, doc);
    if (libDoc.getModel() != null) {
      doc.setModel(convertModel(libDoc.getModel()));
    }
    //    org.sbml.libsbml.XMLNamespaces libNamespaces = libDoc.getNamespaces();
    //    for (int i = 0; i < libNamespaces.getNumNamespaces(); i++) {
    //      doc.addNamespace(libNamespaces.getURI(i));
    //    }
    doc.addTreeNodeChangeListener(new LibSBMLChangeListener(libDoc));
    return doc;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBMLInputConverter#convertSBMLDocument(java.lang.String)
   */
  @Override
  public SBMLDocument convertSBMLDocument(String fileName) throws Exception {
    return convertSBMLDocument(new File(fileName));
  }

  /**
   * 
   * @return
   */
  private Model getModel() {
    return model;
  }

  /* (non-Javadoc)
   * @see org.sbml.squeezer.io.AbstractSBMLReader#getOriginalModel()
   */
  @Override
  public org.sbml.libsbml.Model getOriginalModel() {
    return (org.sbml.libsbml.Model) model.getUserObject(LINK_TO_LIBSBML);
  }

  /* (non-Javadoc)
   * @see org.sbml.jlibsbml.SBMLReader#getWarnings()
   */
  @Override
  public List<SBMLException> getWarnings() {
    org.sbml.libsbml.SBMLDocument doc = ((org.sbml.libsbml.Model) model.getUserObject(LINK_TO_LIBSBML)).getSBMLDocument();
    doc.checkConsistency();
    List<SBMLException> excl = new LinkedList<SBMLException>();
    for (int i = 0; i < doc.getNumErrors(); i++) {
      excl.add(convert(doc.getError(i)));
    }
    return excl;
  }

  /**
   * 
   * @param comp
   * @return
   */
  private Compartment readCompartment(org.sbml.libsbml.Compartment comp) {
    Compartment c = new Compartment(comp.getId(), (int) comp.getLevel(), (int) comp.getVersion());
    LibSBMLUtils.transferNamedSBaseProperties(comp, c);
    if (comp.isSetOutside()) {
      Model m = getModel();
      if (m.getCompartment(comp.getOutside()) == null) {
        m.addCompartment(readCompartment(comp.getModel().getCompartment(comp.getOutside())));
      }
      Compartment outside = getModel().getCompartment(comp.getOutside());
      c.setOutside(outside);
    }
    if (comp.isSetCompartmentType()) {
      c.setCompartmentType(comp.getCompartmentType());
    }
    // Here we have to distinguish arbitrary special cases because of the way how libSBML works:
    if (comp.isSetConstant() && ((comp.getLevel() != 2) || !comp.getConstant())) {
      c.setConstant(comp.getConstant());
    }
    if (comp.isSetSize()) {
      c.setSize(comp.getSize());
    }
    if (comp.isSetSpatialDimensions() && ((comp.getLevel() > 2) || (comp.getSpatialDimensionsAsDouble() != 3d))) {
      c.setSpatialDimensions(comp.getSpatialDimensionsAsDouble());
    }
    if (comp.isSetUnits()) {
      c.setUnits(comp.getUnits());
    }
    return c;
  }

  /**
   * 
   * @param compartmentType
   * @return
   */
  private CompartmentType readCompartmentType(org.sbml.libsbml.CompartmentType compartmentType) {
    CompartmentType com = new CompartmentType(compartmentType.getId(), (int) compartmentType.getLevel(), (int) compartmentType.getVersion());
    LibSBMLUtils.transferNamedSBaseProperties(compartmentType, com);
    return com;
  }

  /**
   * 
   * @param constraint
   * @return
   */
  private Constraint readConstraint(org.sbml.libsbml.Constraint constraint) {
    Constraint con = new Constraint((int) constraint.getLevel(), (int) constraint.getVersion());
    LibSBMLUtils.transferSBaseProperties(constraint, con);
    if (constraint.isSetMath()) {
      con.setMath(LibSBMLUtils.convert(constraint.getMath(), con));
    }
    if (constraint.isSetMessage()) {
      try {
        con.setMessage(constraint.getMessageString());
      } catch (XMLStreamException exc) {
        logger.warn(exc.getLocalizedMessage() != null ? exc.getLocalizedMessage() : exc.getMessage(), exc);
      }
    }
    return con;
  }

  /**
   * 
   * @param event
   * @return
   * @throws XMLStreamException
   */
  private Event readEvent(org.sbml.libsbml.Event event) throws XMLStreamException {
    Event ev = new Event((int) event.getLevel(), (int) event.getVersion());
    LibSBMLUtils.transferNamedSBaseProperties(event, ev);
    if (event.isSetTrigger()) {
      ev.setTrigger(readTrigger(event.getTrigger()));
    }
    if (event.isSetPriority()) {
      ev.setPriority(readPriority(event.getPriority()));
    }
    if (event.isSetDelay()) {
      ev.setDelay(readDelay(event.getDelay()));
    }
    for (int i = 0; i < event.getNumEventAssignments(); i++) {
      ev.addEventAssignment(readEventAssignment(event.getEventAssignment(i)));
    }
    if (event.isSetTimeUnits()) {
      ev.setTimeUnits(event.getTimeUnits());
    }
    if (event.isSetUseValuesFromTriggerTime() &&
        (ev.getLevelAndVersion().compareTo(Integer.valueOf(2), Integer.valueOf(4)) > 0)) {
      // There is a bug in libSBML that the isSetUseValuesFromTriggerTime returns true, even if the level is not appropriate!
      ev.setUseValuesFromTriggerTime(event.getUseValuesFromTriggerTime());
    }
    return ev;

  }

  /**
   * 
   * @param eventAssignment
   * @return
   */
  private EventAssignment readEventAssignment(org.sbml.libsbml.EventAssignment eventAssignment) {
    EventAssignment ev = new EventAssignment((int) eventAssignment.getLevel(),	(int) eventAssignment.getVersion());
    LibSBMLUtils.transferSBaseProperties(eventAssignment, ev);
    if (eventAssignment.isSetVariable()) {
      ev.setVariable(eventAssignment.getVariable());
    }
    if (eventAssignment.isSetMath()) {
      ev.setMath(LibSBMLUtils.convert(eventAssignment.getMath(), ev));
    }
    return ev;
  }

  /**
   * 
   * @param functionDefinition
   * @return
   */
  private FunctionDefinition readFunctionDefinition(org.sbml.libsbml.FunctionDefinition functionDefinition) {
    FunctionDefinition fd = new FunctionDefinition((int) functionDefinition.getLevel(), (int) functionDefinition.getVersion());
    LibSBMLUtils.transferNamedSBaseProperties(functionDefinition, fd);
    if (functionDefinition.isSetMath()) {
      fd.setMath(LibSBMLUtils.convert(functionDefinition.getMath(), fd));
    }
    return fd;
  }

  /**
   * 
   * @param initialAssignment
   * @return
   */
  private InitialAssignment readInitialAssignment(org.sbml.libsbml.InitialAssignment initialAssignment) {
    if (!initialAssignment.isSetSymbol()) {
      throw new IllegalArgumentException(
          "Symbol attribute not set for InitialAssignment");
    }
    InitialAssignment ia = new InitialAssignment((int) initialAssignment.getLevel(), (int) initialAssignment.getVersion());
    ia.setVariable(initialAssignment.getSymbol());
    LibSBMLUtils.transferSBaseProperties(initialAssignment, ia);
    if (initialAssignment.isSetMath()) {
      ia.setMath(LibSBMLUtils.convert(initialAssignment.getMath(), ia));
    }
    return ia;
  }

  /**
   * 
   * @param kineticLaw
   * @return
   */
  private KineticLaw readKineticLaw(org.sbml.libsbml.KineticLaw kineticLaw) {
    KineticLaw kinlaw = new KineticLaw((int) kineticLaw.getLevel(), (int) kineticLaw.getVersion());
    LibSBMLUtils.transferSBaseProperties(kineticLaw, kinlaw);
    if (kineticLaw.getNumLocalParameters() > 0) {
      for (int i = 0; i < kineticLaw.getNumLocalParameters(); i++) {
        kinlaw.addLocalParameter(readLocalParameter(kineticLaw.getLocalParameter(i)));
      }
    } else if (kineticLaw.getNumParameters() > 0) {
      // This distinction does not make sense but libSBML is programmed like this!
      for (int i = 0; i < kineticLaw.getNumParameters(); i++) {
        kinlaw.addLocalParameter(new LocalParameter(readParameter(kineticLaw.getParameter(i))));
      }
    }
    if (kineticLaw.isSetMath()) {
      kinlaw.setMath(LibSBMLUtils.convert(kineticLaw.getMath(), kinlaw));
    }
    return kinlaw;
  }

  /**
   * 
   * @param parameter
   * @return
   */
  private LocalParameter readLocalParameter(org.sbml.libsbml.LocalParameter parameter) {
    return new LocalParameter(readParameter(parameter));
  }

  /**
   * 
   * @param modifierSpeciesReference
   * @return
   */
  private ModifierSpeciesReference readModifierSpeciesReference(org.sbml.libsbml.ModifierSpeciesReference modifierSpeciesReference) {
    ModifierSpeciesReference mod = new ModifierSpeciesReference((int) modifierSpeciesReference.getLevel(), (int) modifierSpeciesReference.getVersion());
    mod.setSpecies(modifierSpeciesReference.getSpecies());
    LibSBMLUtils.transferNamedSBaseProperties(modifierSpeciesReference, mod);
    return mod;
  }

  /**
   * 
   * @param parameter
   * @return
   */
  private Parameter readParameter(org.sbml.libsbml.Parameter parameter) {
    Parameter param = new Parameter((int) parameter.getLevel(), (int) parameter.getVersion());
    LibSBMLUtils.transferNamedSBaseProperties(parameter, param);
    if (parameter.isSetValue()) {
      param.setValue(parameter.getValue());
    }
    if (parameter.isSetConstant() && ((parameter.getLevel() != 2) || !parameter.getConstant())) {
      param.setConstant(parameter.getConstant());
    }
    if (parameter.isSetUnits()) {
      param.setUnits(parameter.getUnits());
    }
    return param;
  }

  /**
   * 
   * @param priority
   * @return
   * @throws XMLStreamException
   */
  private Priority readPriority(org.sbml.libsbml.Priority priority) throws XMLStreamException {
    Priority p = new Priority();
    LibSBMLUtils.transferSBaseProperties(p, priority);
    if (priority.isSetMath()) {
      LibSBMLUtils.convert(priority.getMath(), p);
    }
    return p;
  }

  /**
   * 
   * @param reaction
   * @return
   * @throws XMLStreamException
   */
  private Reaction readReaction(org.sbml.libsbml.Reaction reaction) throws XMLStreamException {
    Reaction r = new Reaction((int) reaction.getLevel(), (int) reaction.getVersion());
    LibSBMLUtils.transferNamedSBaseProperties(reaction, r);
    for (int i = 0; i < reaction.getNumReactants(); i++) {
      r.addReactant(readSpeciesReference(reaction.getReactant(i)));
    }
    for (int i = 0; i < reaction.getNumProducts(); i++) {
      r.addProduct(readSpeciesReference(reaction.getProduct(i)));
    }
    for (int i = 0; i < reaction.getNumModifiers(); i++) {
      r.addModifier(readModifierSpeciesReference(reaction.getModifier(i)));
    }
    if (reaction.isSetFast()) {
      r.setFast(reaction.getFast());
    }
    if (reaction.isSetReversible()) {
      r.setReversible(reaction.getReversible());
    }
    if (reaction.isSetCompartment()) {
      r.setCompartment(reaction.getCompartment());
    }
    if (reaction.isSetKineticLaw()) {
      r.setKineticLaw(readKineticLaw(reaction.getKineticLaw()));
    }
    return r;
  }

  /**
   * 
   * @param rule
   * @return
   * @throws XMLStreamException
   */
  private Rule readRule(org.sbml.libsbml.Rule rule) throws XMLStreamException {
    Rule r;
    if (rule.isAlgebraic()) {
      r = new AlgebraicRule((int) rule.getLevel(), (int) rule.getVersion());
    } else {
      if (rule.isScalar() || rule.isAssignment()) {
        r = new AssignmentRule((int) rule.getLevel(), (int) rule.getVersion());
      } else {
        r = new RateRule((int) rule.getLevel(), (int) rule.getVersion());
      }
      ExplicitRule er = (ExplicitRule) r;
      er.setVariable(rule.getVariable());
      if (er.isSetLevel() && er.isSetVersion()
          && (er.getLevelAndVersion().compareTo(2, 1) < 0)
          && rule.isSetUnits() && rule.isParameter()) {
        er.setUnits(rule.getUnits());
      }
    }
    LibSBMLUtils.transferSBaseProperties(r, rule);
    if (rule.isSetMath()) {
      r.setMath(LibSBMLUtils.convert(rule.getMath(), r));
    }
    return r;
  }

  /**
   * 
   * @param fileName
   * @return
   * @throws IOException
   */
  private org.sbml.libsbml.SBMLDocument readSBMLfromFile(String fileName)
      throws IOException {
    File file = new File(fileName);
    if (!file.exists() || !file.isFile()) {
      throw new FileNotFoundException(file.getAbsolutePath());
    }
    if (!file.canRead()) {
      throw new IOException(file.getAbsolutePath());
    }
    org.sbml.libsbml.SBMLDocument doc = new org.sbml.libsbml.SBMLReader().readSBML(file.getAbsolutePath());
    setOfDocuments.add(doc);
    return doc;
  }

  /**
   * 
   * @param species
   * @return
   */
  private Species readSpecies(org.sbml.libsbml.Species species) {
    Species spec = new Species((int) species.getLevel(), (int) species.getVersion());
    LibSBMLUtils.transferNamedSBaseProperties(species, spec);
    if (species.isSetCharge()) {
      spec.setCharge(species.getCharge());
    }
    if (species.isSetCompartment()) {
      spec.setCompartment(getModel().getCompartment(species.getCompartment()));
    }
    if (species.isSetBoundaryCondition() && ((species.getLevel() > 2) || species.getBoundaryCondition())) {
      spec.setBoundaryCondition(species.getBoundaryCondition());
    }
    if (species.isSetConstant() && ((species.getLevel() != 2) || species.getConstant())) {
      spec.setConstant(species.getConstant());
    }
    if (spec.isSetHasOnlySubstanceUnits()) {
      spec.setHasOnlySubstanceUnits(species.getHasOnlySubstanceUnits());
    }
    if (species.isSetInitialAmount()) {
      spec.setInitialAmount(species.getInitialAmount());
    } else if (species.isSetInitialConcentration()) {
      spec.setInitialConcentration(species.getInitialConcentration());
    }
    if (species.isSetSpatialSizeUnits()) {
      spec.setSpatialSizeUnits(species.getSpatialSizeUnits());
    }
    if (species.isSetSubstanceUnits()) {
      spec.setSubstanceUnits(species.getUnits());
    }
    if (species.isSetSpeciesType()) {
      spec.setSpeciesType(model.getSpeciesType(species.getSpeciesType()));
    }
    if (species.isSetConversionFactor()) {
      spec.setConversionFactor(species.getConversionFactor());
    }
    return spec;
  }

  /**
   * 
   * @param speciesReference
   * @return
   * @throws XMLStreamException
   */
  private SpeciesReference readSpeciesReference(org.sbml.libsbml.SpeciesReference speciesReference) throws XMLStreamException {
    SpeciesReference spec = new SpeciesReference(model.getSpecies(speciesReference.getSpecies()));
    LibSBMLUtils.transferNamedSBaseProperties(speciesReference, spec);
    if (speciesReference.isSetStoichiometryMath()) {
      spec.setStoichiometryMath(readStoichiometricMath(speciesReference.getStoichiometryMath()));
    } else if (speciesReference.isSetStoichiometry()){
      spec.setStoichiometry(speciesReference.getStoichiometry());
    }
    if (speciesReference.isSetConstant()) {
      spec.setConstant(speciesReference.getConstant());
    }
    return spec;
  }

  /**
   * 
   * @param speciesType
   * @return
   */
  private SpeciesType readSpeciesType(org.sbml.libsbml.SpeciesType speciesType) {
    SpeciesType st = new SpeciesType(speciesType.getId(), (int) speciesType.getLevel(), (int) speciesType.getVersion());
    LibSBMLUtils.transferNamedSBaseProperties(speciesType, st);
    return st;
  }

  /**
   * 
   * @param unit
   * @return
   * @throws XMLStreamException
   */
  private Unit readUnit(org.sbml.libsbml.Unit unit) throws XMLStreamException {
    Unit u = new Unit((int) unit.getLevel(), (int) unit.getVersion());
    LibSBMLUtils.transferSBaseProperties(u, unit);
    u.setKind(LibSBMLUtils.convertUnitKind(unit.getKind()));
    if (unit.isSetExponent()) {
      u.setExponent(unit.getExponentAsDouble());
    }
    if (unit.isSetMultiplier()) {
      u.setMultiplier(unit.getMultiplier());
    }
    if (unit.isSetScale()) {
      u.setScale(unit.getScale());
    }
    if (unit.getOffset() != 0d) {
      u.setOffset(unit.getOffset());
    }
    return u;
  }

  /**
   * 
   * @param unitDefinition
   * @return
   * @throws XMLStreamException
   */
  private UnitDefinition readUnitDefinition(org.sbml.libsbml.UnitDefinition unitDefinition) throws XMLStreamException {
    UnitDefinition ud = new UnitDefinition((int) unitDefinition.getLevel(), (int) unitDefinition.getVersion());
    LibSBMLUtils.transferNamedSBaseProperties(unitDefinition, ud);
    for (int i = 0; i < unitDefinition.getNumUnits(); i++) {
      ud.addUnit(readUnit(unitDefinition.getUnit(i)));
    }
    return ud;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBMLInputConverter#setListener(org.sbml.jsbml.util.ProgressListener)
   */
  @Override
  public void setListener(ProgressListener listener) {
    this.listener = listener;
  }

}
