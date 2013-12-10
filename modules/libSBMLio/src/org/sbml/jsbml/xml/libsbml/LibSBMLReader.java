/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2013 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.AlgebraicRule;
import org.sbml.jsbml.AssignmentRule;
import org.sbml.jsbml.CallableSBase;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.CompartmentType;
import org.sbml.jsbml.Constraint;
import org.sbml.jsbml.Creator;
import org.sbml.jsbml.Delay;
import org.sbml.jsbml.Event;
import org.sbml.jsbml.EventAssignment;
import org.sbml.jsbml.ExplicitRule;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.History;
import org.sbml.jsbml.InitialAssignment;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.LocalParameter;
import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.ModifierSpeciesReference;
import org.sbml.jsbml.NamedSBase;
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
import org.sbml.jsbml.Variable;
import org.sbml.jsbml.util.SBMLtools;
import org.sbml.jsbml.util.TreeNodeChangeListener;
import org.sbml.libsbml.SBMLError;
import org.sbml.libsbml.XMLNode;
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
   * 
   * @param model
   */
  private static final void addPredefinedUnitDefinitions(Model model) {
    boolean isL3 = model.getLevelAndVersion().compareTo(Integer.valueOf(3), Integer.valueOf(1)) >= 0;
    if (model.getUnitDefinition(UnitDefinition.SUBSTANCE) == null) {
      model.addUnitDefinition(SBMLtools.setLevelAndVersion(UnitDefinition.substance(2, 4), model.getLevel(), model.getVersion()));
      if (isL3) {
        model.setSubstanceUnits(UnitDefinition.SUBSTANCE);
      }
    }
    if (model.getUnitDefinition(UnitDefinition.VOLUME) == null) {
      model.addUnitDefinition(SBMLtools.setLevelAndVersion(UnitDefinition.volume(2, 4), model.getLevel(), model.getVersion()));
      if (isL3) {
        model.setVolumeUnits(UnitDefinition.VOLUME);
      }
    }
    if (model.getUnitDefinition(UnitDefinition.AREA) == null) {
      model.addUnitDefinition(SBMLtools.setLevelAndVersion(UnitDefinition.area(2, 4), model.getLevel(), model.getVersion()));
      if (isL3) {
        model.setAreaUnits(UnitDefinition.AREA);
      }
    }
    if (model.getUnitDefinition(UnitDefinition.LENGTH) == null) {
      model.addUnitDefinition(SBMLtools.setLevelAndVersion(UnitDefinition.length(2, 4), model.getLevel(), model.getVersion()));
      if (isL3) {
        model.setLengthUnits(UnitDefinition.LENGTH);
      }
    }
    if (model.getUnitDefinition(UnitDefinition.TIME) == null) {
      model.addUnitDefinition(SBMLtools.setLevelAndVersion(UnitDefinition.time(2, 4), model.getLevel(), model.getVersion()));
      if (isL3) {
        model.setTimeUnits(UnitDefinition.TIME);
      }
    }
  }

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
   */
  protected LinkedList<TreeNodeChangeListener> listOfTreeNodeChangeListeners;

  /**
   * 
   */
  private Model model;

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

  /**
   * 
   * @param math
   * @param parent
   * @return
   */
  private ASTNode convert(org.sbml.libsbml.ASTNode math, MathContainer parent) {
    ASTNode ast;
    switch (math.getType()) {
    case libsbmlConstants.AST_REAL:
      ast = new ASTNode(ASTNode.Type.REAL, parent);
      ast.setValue(math.getReal());
      break;
    case libsbmlConstants.AST_INTEGER:
      ast = new ASTNode(ASTNode.Type.INTEGER, parent);
      ast.setValue(math.getInteger());
      break;
    case libsbmlConstants.AST_FUNCTION_LOG:
      ast = new ASTNode(ASTNode.Type.FUNCTION_LOG, parent);
      break;
    case libsbmlConstants.AST_POWER:
      ast = new ASTNode(ASTNode.Type.POWER, parent);
      break;
    case libsbmlConstants.AST_PLUS:
      ast = new ASTNode(ASTNode.Type.PLUS, parent);
      break;
    case libsbmlConstants.AST_MINUS:
      ast = new ASTNode(ASTNode.Type.MINUS, parent);
      break;
    case libsbmlConstants.AST_TIMES:
      ast = new ASTNode(ASTNode.Type.TIMES, parent);
      break;
    case libsbmlConstants.AST_DIVIDE:
      ast = new ASTNode(ASTNode.Type.DIVIDE, parent);
      break;
    case libsbmlConstants.AST_RATIONAL:
      ast = new ASTNode(ASTNode.Type.RATIONAL, parent);
      ast.setValue(math.getNumerator(), math.getDenominator());
      break;
    case libsbmlConstants.AST_NAME_TIME:
      ast = new ASTNode(ASTNode.Type.NAME_TIME, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_DELAY:
      ast = new ASTNode(ASTNode.Type.FUNCTION_DELAY, parent);
      break;
    case libsbmlConstants.AST_NAME:
      ast = new ASTNode(ASTNode.Type.NAME, parent);
      if (parent instanceof KineticLaw) {
        for (LocalParameter p : ((KineticLaw) parent).getListOfLocalParameters()) {
          if (p.getId().equals(math.getName())) {
            ast.setVariable(p);
            break;
          }
        }
      }
      if (ast.getVariable() == null) {
        CallableSBase nsb = model.findCallableSBase(math.getName());
        if (nsb == null) {
          ast.setName(math.getName());
        } else {
          ast.setVariable(nsb);
        }
      }
      break;
    case libsbmlConstants.AST_CONSTANT_PI:
      ast = new ASTNode(ASTNode.Type.CONSTANT_PI, parent);
      break;
    case libsbmlConstants.AST_CONSTANT_E:
      ast = new ASTNode(ASTNode.Type.CONSTANT_E, parent);
      break;
    case libsbmlConstants.AST_CONSTANT_TRUE:
      ast = new ASTNode(ASTNode.Type.CONSTANT_TRUE, parent);
      break;
    case libsbmlConstants.AST_CONSTANT_FALSE:
      ast = new ASTNode(ASTNode.Type.CONSTANT_FALSE, parent);
      break;
    case libsbmlConstants.AST_REAL_E:
      ast = new ASTNode(ASTNode.Type.REAL_E, parent);
      ast.setValue(math.getMantissa(), math.getExponent());
      break;
    case libsbmlConstants.AST_FUNCTION_ABS:
      ast = new ASTNode(ASTNode.Type.FUNCTION_ABS, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_ARCCOS:
      ast = new ASTNode(ASTNode.Type.FUNCTION_ARCCOS, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_ARCCOSH:
      ast = new ASTNode(ASTNode.Type.FUNCTION_ARCCOSH, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_ARCCOT:
      ast = new ASTNode(ASTNode.Type.FUNCTION_ARCCOT, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_ARCCOTH:
      ast = new ASTNode(ASTNode.Type.FUNCTION_ARCCOTH, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_ARCCSC:
      ast = new ASTNode(ASTNode.Type.FUNCTION_ARCCSC, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_ARCCSCH:
      ast = new ASTNode(ASTNode.Type.FUNCTION_ARCCSCH, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_ARCSEC:
      ast = new ASTNode(ASTNode.Type.FUNCTION_ARCSEC, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_ARCSECH:
      ast = new ASTNode(ASTNode.Type.FUNCTION_ARCSECH, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_ARCSIN:
      ast = new ASTNode(ASTNode.Type.FUNCTION_ARCSIN, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_ARCSINH:
      ast = new ASTNode(ASTNode.Type.FUNCTION_ARCSINH, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_ARCTAN:
      ast = new ASTNode(ASTNode.Type.FUNCTION_ARCTAN, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_ARCTANH:
      ast = new ASTNode(ASTNode.Type.FUNCTION_ARCTANH, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_CEILING:
      ast = new ASTNode(ASTNode.Type.FUNCTION_CEILING, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_COS:
      ast = new ASTNode(ASTNode.Type.FUNCTION_COS, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_COSH:
      ast = new ASTNode(ASTNode.Type.FUNCTION_COSH, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_COT:
      ast = new ASTNode(ASTNode.Type.FUNCTION_COT, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_COTH:
      ast = new ASTNode(ASTNode.Type.FUNCTION_COTH, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_CSC:
      ast = new ASTNode(ASTNode.Type.FUNCTION_CSC, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_CSCH:
      ast = new ASTNode(ASTNode.Type.FUNCTION_CSCH, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_EXP:
      ast = new ASTNode(ASTNode.Type.FUNCTION_EXP, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_FACTORIAL:
      ast = new ASTNode(ASTNode.Type.FUNCTION_FACTORIAL, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_FLOOR:
      ast = new ASTNode(ASTNode.Type.FUNCTION_FLOOR, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_LN:
      ast = new ASTNode(ASTNode.Type.FUNCTION_LN, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_POWER:
      ast = new ASTNode(ASTNode.Type.FUNCTION_POWER, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_ROOT:
      ast = new ASTNode(ASTNode.Type.FUNCTION_ROOT, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_SEC:
      ast = new ASTNode(ASTNode.Type.FUNCTION_SEC, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_SECH:
      ast = new ASTNode(ASTNode.Type.FUNCTION_SECH, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_SIN:
      ast = new ASTNode(ASTNode.Type.FUNCTION_SIN, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_SINH:
      ast = new ASTNode(ASTNode.Type.FUNCTION_SINH, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_TAN:
      ast = new ASTNode(ASTNode.Type.FUNCTION_TAN, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_TANH:
      ast = new ASTNode(ASTNode.Type.FUNCTION_TANH, parent);
      break;
    case libsbmlConstants.AST_FUNCTION:
      ast = new ASTNode(ASTNode.Type.FUNCTION, parent);
      ast.setName(math.getName());
      break;
    case libsbmlConstants.AST_LAMBDA:
      ast = new ASTNode(ASTNode.Type.LAMBDA, parent);
      break;
    case libsbmlConstants.AST_LOGICAL_AND:
      ast = new ASTNode(ASTNode.Type.LOGICAL_AND, parent);
      break;
    case libsbmlConstants.AST_LOGICAL_XOR:
      ast = new ASTNode(ASTNode.Type.LOGICAL_XOR, parent);
      break;
    case libsbmlConstants.AST_LOGICAL_OR:
      ast = new ASTNode(ASTNode.Type.LOGICAL_OR, parent);
      break;
    case libsbmlConstants.AST_LOGICAL_NOT:
      ast = new ASTNode(ASTNode.Type.LOGICAL_NOT, parent);
      break;
    case libsbmlConstants.AST_FUNCTION_PIECEWISE:
      ast = new ASTNode(ASTNode.Type.FUNCTION_PIECEWISE, parent);
      break;
    case libsbmlConstants.AST_RELATIONAL_EQ:
      ast = new ASTNode(ASTNode.Type.RELATIONAL_EQ, parent);
      break;
    case libsbmlConstants.AST_RELATIONAL_GEQ:
      ast = new ASTNode(ASTNode.Type.RELATIONAL_GEQ, parent);
      break;
    case libsbmlConstants.AST_RELATIONAL_GT:
      ast = new ASTNode(ASTNode.Type.RELATIONAL_GT, parent);
      break;
    case libsbmlConstants.AST_RELATIONAL_NEQ:
      ast = new ASTNode(ASTNode.Type.RELATIONAL_NEQ, parent);
      break;
    case libsbmlConstants.AST_RELATIONAL_LEQ:
      ast = new ASTNode(ASTNode.Type.RELATIONAL_LEQ, parent);
      break;
    case libsbmlConstants.AST_RELATIONAL_LT:
      ast = new ASTNode(ASTNode.Type.RELATIONAL_LT, parent);
      break;
    default:
      ast = new ASTNode(ASTNode.Type.UNKNOWN, parent);
      break;
    }
    for (int i = 0; i < math.getNumChildren(); i++) {
      org.sbml.libsbml.ASTNode child = math.getChild(i);
      ast.addChild(convert(child, parent));
    }
    if (math.isSetUnits()) {
      ast.setUnits(math.getUnits());
    }
    if (math.isSetStyle()) {
      ast.setStyle(math.getStyle());
    }
    ast.putUserObject(LINK_TO_LIBSBML, math);
    return ast;
  }

  /**
   * 
   * @param date
   * @return
   */
  private Date convertDate(org.sbml.libsbml.Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeZone(TimeZone.getTimeZone(TimeZone.getAvailableIDs((int) (date.getSignOffset() * date.getMinutesOffset() * 60000l))[0]));
    calendar.set((int) date.getYear(), (int) date.getMonth(), (int) date.getDay(), (int) date.getHour(), (int) date.getMinute(), (int) date.getSecond());
    return calendar.getTime();
  }

  /* (non-Javadoc)
   * @see org.sbml.SBMLReader#readModel(java.lang.Object)
   */
  @Override
  public Model convertModel(org.sbml.libsbml.Model originalModel) {

    model = new Model(originalModel.getId(), (int) originalModel.getLevel(), (int) originalModel.getVersion());
    transferNamedSBaseProperties(model, originalModel);

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

    int i;
    for (i = 0; i < originalModel.getNumUnitDefinitions(); i++) {
      model.addUnitDefinition(readUnitDefinition(originalModel.getUnitDefinition(i)));
    }
    for (i = 0; i < originalModel.getNumFunctionDefinitions(); i++) {
      model.addFunctionDefinition(readFunctionDefinition(originalModel.getFunctionDefinition(i)));
    }
    // This is something, libSBML wouldn't do...
    addPredefinedUnitDefinitions(model);
    for (i = 0; i < originalModel.getNumCompartmentTypes(); i++) {
      model.addCompartmentType(readCompartmentType(originalModel.getCompartmentType(i)));
    }
    for (i = 0; i < originalModel.getNumSpeciesTypes(); i++) {
      model.addSpeciesType(readSpeciesType(originalModel.getSpeciesType(i)));
    }
    for (i = 0; i < originalModel.getNumCompartments(); i++) {
      model.addCompartment(readCompartment(originalModel.getCompartment(i)));
    }
    for (i = 0; i < originalModel.getNumSpecies(); i++) {
      model.addSpecies(readSpecies(originalModel.getSpecies(i)));
    }
    for (i = 0; i < originalModel.getNumParameters(); i++) {
      model.addParameter(readParameter(originalModel.getParameter(i)));
    }
    for (i = 0; i < originalModel.getNumInitialAssignments(); i++) {
      model.addInitialAssignment(readInitialAssignment(originalModel.getInitialAssignment(i)));
    }
    for (i = 0; i < originalModel.getNumRules(); i++) {
      model.addRule(readRule(originalModel.getRule(i)));
    }
    for (i = 0; i < originalModel.getNumConstraints(); i++) {
      model.addConstraint(readConstraint(originalModel.getConstraint(i)));
    }
    for (i = 0; i < originalModel.getNumReactions(); i++) {
      org.sbml.libsbml.Reaction rOrig = originalModel.getReaction(i);
      Reaction r = readReaction(rOrig);
      model.addReaction(r);
      if (rOrig.isSetKineticLaw()) {
        r.setKineticLaw(readKineticLaw(rOrig.getKineticLaw()));
      }
    }
    for (i = 0; i < originalModel.getNumEvents(); i++) {
      model.addEvent(readEvent(originalModel.getEvent(i)));
    }

    if (originalModel.getNumPlugins() > 0) {
      // TODO: Implement package support
      org.sbml.libsbml.SBasePlugin plugin = originalModel.getPlugin("layout");
      if ((plugin == null) || (plugin.getListOfAllElements().getSize() > 0)) {
        logger.warn("The SBML document contains exension packages. These are not supported by the libSBML reader and will therefore be lost after reading.");
      }
    }

    addAllTreeNodeChangeListenersTo(model);
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
   * @param doc
   * @return
   * @throws Exception
   */
  public SBMLDocument convertSBMLDocument(org.sbml.libsbml.SBMLDocument libDoc) throws Exception {
    SBMLDocument doc = new SBMLDocument((int) libDoc.getLevel(), (int) libDoc.getVersion());
    transferSBaseProperties(doc, libDoc);
    if (libDoc.getModel() != null) {
      doc.setModel(convertModel(libDoc.getModel()));
    }
    org.sbml.libsbml.XMLNamespaces libNamespaces = libDoc.getNamespaces();
    for (int i = 0; i < libNamespaces.getNumNamespaces(); i++) {
      doc.addNamespace(libNamespaces.getURI(i));
    }
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
    transferNamedSBaseProperties(c, comp);
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
      c.setUnits(getModel().getUnitDefinition(comp.getUnits()));
    }
    return c;
  }

  /**
   * 
   * @param compartmenttype
   * @return
   */
  private CompartmentType readCompartmentType(org.sbml.libsbml.CompartmentType compartmentType) {
    CompartmentType com = new CompartmentType(compartmentType.getId(), (int) compartmentType.getLevel(), (int) compartmentType.getVersion());
    transferNamedSBaseProperties(com, compartmentType);
    return com;
  }

  /**
   * 
   * @param constraint
   * @return
   */
  private Constraint readConstraint(org.sbml.libsbml.Constraint constraint) {
    Constraint con = new Constraint((int) constraint.getLevel(), (int) constraint.getVersion());
    transferSBaseProperties(con, constraint);
    if (constraint.isSetMath()) {
      con.setMath(convert(constraint.getMath(), con));
    }
    if (constraint.isSetMessage()) {
      con.setMessage(constraint.getMessageString());
    }
    return con;
  }

  /**
   * 
   * @param delay
   * @return
   */
  private Delay readDelay(org.sbml.libsbml.Delay delay) {
    Delay de = new Delay((int) delay.getLevel(), (int) delay.getVersion());
    transferSBaseProperties(de, delay);
    if (delay.isSetMath()) {
      de.setMath(convert(delay.getMath(), de));
    }
    return de;
  }

  /**
   * 
   * @param event
   * @return
   */
  private Event readEvent(org.sbml.libsbml.Event event) {
    Event ev = new Event((int) event.getLevel(), (int) event.getVersion());
    transferNamedSBaseProperties(ev, event);
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
    transferSBaseProperties(ev, eventAssignment);
    if (eventAssignment.isSetVariable()) {
      Variable variable = model.findVariable(eventAssignment.getVariable());
      if (variable == null) {
        ev.setVariable(eventAssignment.getVariable());
      } else {
        ev.setVariable(variable);
      }
    }
    if (eventAssignment.isSetMath()) {
      ev.setMath(convert(eventAssignment.getMath(), ev));
    }
    return ev;
  }

  /**
   * 
   * @param functionDefinition
   * @return
   */
  private FunctionDefinition readFunctionDefinition(org.sbml.libsbml.FunctionDefinition functionDefinition) {
    FunctionDefinition fd = new FunctionDefinition(functionDefinition.getId(), (int) functionDefinition.getLevel(), (int) functionDefinition.getVersion());
    transferNamedSBaseProperties(fd, functionDefinition);
    if (functionDefinition.isSetMath()) {
      fd.setMath(convert(functionDefinition.getMath(), fd));
    }
    return fd;
  }

  /**
   * 
   * @param libHist
   * @return
   */
  private History readHistory(org.sbml.libsbml.ModelHistory libHist) {
    int i;
    History history = new History();
    for (i = 0; i < libHist.getNumCreators(); i++) {
      Creator creator = new Creator();
      org.sbml.libsbml.ModelCreator libCreator = libHist.getCreator(i);
      creator.setGivenName(libCreator.getGivenName());
      creator.setFamilyName(libCreator.getFamilyName());
      creator.setEmail(libCreator.getEmail());
      creator.setOrganization(libCreator.getOrganization());
      creator.putUserObject(LINK_TO_LIBSBML, libCreator);
      history.addCreator(creator);
    }
    if (libHist.isSetCreatedDate()) {
      history.setCreatedDate(convertDate(libHist.getCreatedDate()));
    }
    if (libHist.isSetModifiedDate()) {
      history.setModifiedDate(convertDate(libHist.getModifiedDate()));
    }
    for (i = 0; i < libHist.getNumModifiedDates(); i++) {
      history.addModifiedDate(convertDate(libHist.getModifiedDate(i)));
    }
    history.putUserObject(LINK_TO_LIBSBML, libHist);
    return history;
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
    InitialAssignment ia = new InitialAssignment(model.findVariable(initialAssignment.getSymbol()));
    transferSBaseProperties(ia, initialAssignment);
    if (initialAssignment.isSetMath()) {
      ia.setMath(convert(initialAssignment.getMath(), ia));
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
    transferSBaseProperties(kinlaw, kineticLaw);
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
      kinlaw.setMath(convert(kineticLaw.getMath(), kinlaw));
    }
    addAllTreeNodeChangeListenersTo(kinlaw);
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
    ModifierSpeciesReference mod = new ModifierSpeciesReference(model.getSpecies(modifierSpeciesReference.getSpecies()));
    transferNamedSBaseProperties(mod, modifierSpeciesReference);
    addAllTreeNodeChangeListenersTo(mod);
    return mod;
  }

  /**
   * 
   * @param parameter
   * @return
   */
  private Parameter readParameter(org.sbml.libsbml.Parameter parameter) {
    Parameter param = new Parameter(parameter.getId(), (int) parameter.getLevel(), (int) parameter.getVersion());
    transferNamedSBaseProperties(param, parameter);
    if (parameter.isSetValue()) {
      param.setValue(parameter.getValue());
    }
    if (parameter.isSetConstant() && ((parameter.getLevel() != 2) || !parameter.getConstant())) {
      param.setConstant(parameter.getConstant());
    }
    if (parameter.isSetUnits()) {
      if (Unit.isUnitKind(parameter.getUnits(), param.getLevel(), param.getVersion())) {
        param.setUnits(Unit.Kind.valueOf(parameter.getUnits().toUpperCase()));
      } else {
        param.setUnits(model.getUnitDefinition(parameter.getUnits()));
      }
    }
    addAllTreeNodeChangeListenersTo(param);
    return param;
  }

  /**
   * 
   * @param priority
   * @return
   */
  private Priority readPriority(org.sbml.libsbml.Priority priority) {
    Priority p = new Priority();
    transferSBaseProperties(p, priority);
    if (priority.isSetMath()) {
      convert(priority.getMath(), p);
    }
    return p;
  }

  /**
   * 
   * @param reaction
   * @return
   */
  private Reaction readReaction(org.sbml.libsbml.Reaction reaction) {
    Reaction r = new Reaction(reaction.getId(), (int) reaction.getLevel(), (int) reaction.getVersion());
    transferNamedSBaseProperties(r, reaction);
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
    addAllTreeNodeChangeListenersTo(r);
    return r;
  }

  /**
   * 
   * @param rule
   * @return
   */
  private Rule readRule(org.sbml.libsbml.Rule rule) {
    Rule r;
    if (rule.isAlgebraic()) {
      r = new AlgebraicRule((int) rule.getLevel(), (int) rule.getVersion());
    } else {
      Variable s = model.findVariable(rule.getVariable());
      if (rule.isScalar() || rule.isAssignment()) {
        r = new AssignmentRule(s);
      } else {
        r = new RateRule(s);
      }
    }
    transferSBaseProperties(r, rule);
    if (rule.isSetMath()) {
      r.setMath(convert(rule.getMath(), r));
    }
    if ((r instanceof ExplicitRule) && r.isSetLevel() && r.isSetVersion()
        && (r.getLevelAndVersion().compareTo(2, 1) < 0)
        && rule.isSetUnits() && rule.isParameter()) {
      ((ExplicitRule) r).setUnits(rule.getUnits());
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
    org.sbml.libsbml.SBMLDocument doc = (new org.sbml.libsbml.SBMLReader()).readSBML(file.getAbsolutePath());
    setOfDocuments.add(doc);
    return doc;
  }

  /**
   * 
   * @param species
   * @return
   */
  private Species readSpecies(org.sbml.libsbml.Species species) {
    Species spec = new Species(species.getId(), (int) species.getLevel(), (int) species.getVersion());
    transferNamedSBaseProperties(spec, species);
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
    if (species.isSetSubstanceUnits()) {
      spec.setSubstanceUnits(model.getUnitDefinition(species.getUnits()));
    }
    if (species.isSetSpeciesType()) {
      spec.setSpeciesType(model.getSpeciesType(species.getSpeciesType()));
    }
    if (species.isSetConversionFactor()) {
      spec.setConversionFactor(species.getConversionFactor());
    }
    addAllTreeNodeChangeListenersTo(spec);
    return spec;
  }

  /**
   * 
   * @param speciesReference
   * @return
   */
  private SpeciesReference readSpeciesReference(org.sbml.libsbml.SpeciesReference speciesReference) {
    SpeciesReference spec = new SpeciesReference(model.getSpecies(speciesReference.getSpecies()));
    transferNamedSBaseProperties(spec, speciesReference);
    if (speciesReference.isSetStoichiometryMath()) {
      spec.setStoichiometryMath(readStoichiometricMath(speciesReference.getStoichiometryMath()));
    } else if (speciesReference.isSetStoichiometry()){
      spec.setStoichiometry(speciesReference.getStoichiometry());
    }
    if (speciesReference.isSetConstant()) {
      spec.setConstant(speciesReference.getConstant());
    }
    addAllTreeNodeChangeListenersTo(spec);
    return spec;
  }

  /**
   * 
   * @param speciesType
   * @return
   */
  private SpeciesType readSpeciesType(org.sbml.libsbml.SpeciesType speciesType) {
    SpeciesType st = new SpeciesType(speciesType.getId(), (int) speciesType.getLevel(), (int) speciesType.getVersion());
    transferNamedSBaseProperties(st, speciesType);
    return st;
  }

  /**
   * 
   * @param stoichiometryMath
   * @return
   */
  private StoichiometryMath readStoichiometricMath(org.sbml.libsbml.StoichiometryMath stoichiometryMath) {
    StoichiometryMath sm = new StoichiometryMath((int) stoichiometryMath.getLevel(), (int) stoichiometryMath.getVersion());
    transferSBaseProperties(sm, stoichiometryMath);
    if (stoichiometryMath.isSetMath()) {
      sm.setMath(convert(stoichiometryMath.getMath(), sm));
    }
    return sm;
  }

  /**
   * 
   * @param trigger
   * @return
   */
  private Trigger readTrigger(org.sbml.libsbml.Trigger trigger) {
    Trigger trig = new Trigger((int) trigger.getLevel(), (int) trigger.getVersion());
    transferSBaseProperties(trig, trigger);
    if (trigger.isSetInitialValue()) {
      trig.setInitialValue(trigger.getInitialValue());
    }
    if (trigger.isSetPersistent()) {
      trig.setPersistent(trigger.getPersistent());
    }
    if (trigger.isSetMath()) {
      trig.setMath(convert(trigger.getMath(), trig));
    }
    return trig;

  }

  /**
   * 
   * @param unit
   * @return
   */
  private Unit readUnit(org.sbml.libsbml.Unit unit) {
    Unit u = new Unit((int) unit.getLevel(), (int) unit.getVersion());
    transferSBaseProperties(u, unit);
    switch (unit.getKind()) {
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
   */
  private UnitDefinition readUnitDefinition(org.sbml.libsbml.UnitDefinition unitDefinition) {
    UnitDefinition ud = new UnitDefinition(unitDefinition.getId(), (int) unitDefinition.getLevel(), (int) unitDefinition.getVersion());
    transferNamedSBaseProperties(ud, unitDefinition);
    for (int i = 0; i < unitDefinition.getNumUnits(); i++) {
      ud.addUnit(readUnit(unitDefinition.getUnit(i)));
    }
    return ud;
  }

  /**
   * 
   * @param sbase
   * @param libSBase
   */
  private void transferNamedSBaseProperties(NamedSBase sbase, org.sbml.libsbml.SBase libSBase) {
    transferSBaseProperties(sbase, libSBase);
    if (libSBase instanceof org.sbml.libsbml.Compartment) {
      org.sbml.libsbml.Compartment c = (org.sbml.libsbml.Compartment) libSBase;
      if (c.isSetId()) {
        sbase.setId(c.getId());
      }
      if (c.isSetName()) {
        sbase.setName(c.getName());
      }
    } else if (libSBase instanceof org.sbml.libsbml.CompartmentType) {
      org.sbml.libsbml.CompartmentType c = (org.sbml.libsbml.CompartmentType) libSBase;
      if (c.isSetId()) {
        sbase.setId(c.getId());
      }
      if (c.isSetName()) {
        sbase.setName(c.getName());
      }
    } else if (libSBase instanceof org.sbml.libsbml.Event) {
      org.sbml.libsbml.Event c = (org.sbml.libsbml.Event) libSBase;
      if (c.isSetId()) {
        sbase.setId(c.getId());
      }
      if (c.isSetName()) {
        sbase.setName(c.getName());
      }
    } else if (libSBase instanceof org.sbml.libsbml.FunctionDefinition) {
      org.sbml.libsbml.FunctionDefinition c = (org.sbml.libsbml.FunctionDefinition) libSBase;
      if (c.isSetId()) {
        sbase.setId(c.getId());
      }
      if (c.isSetName()) {
        sbase.setName(c.getName());
      }
    } else if (libSBase instanceof org.sbml.libsbml.Model) {
      org.sbml.libsbml.Model c = (org.sbml.libsbml.Model) libSBase;
      if (c.isSetId()) {
        sbase.setId(c.getId());
      }
      if (c.isSetName()) {
        sbase.setName(c.getName());
      }
    } else if (libSBase instanceof org.sbml.libsbml.Parameter) {
      org.sbml.libsbml.Parameter c = (org.sbml.libsbml.Parameter) libSBase;
      if (c.isSetId()) {
        sbase.setId(c.getId());
      }
      if (c.isSetName()) {
        sbase.setName(c.getName());
      }
    } else if (libSBase instanceof org.sbml.libsbml.Reaction) {
      org.sbml.libsbml.Reaction c = (org.sbml.libsbml.Reaction) libSBase;
      if (c.isSetId()) {
        sbase.setId(c.getId());
      }
      if (c.isSetName()) {
        sbase.setName(c.getName());
      }
    } else if (libSBase instanceof org.sbml.libsbml.SimpleSpeciesReference) {
      org.sbml.libsbml.SimpleSpeciesReference c = (org.sbml.libsbml.SimpleSpeciesReference) libSBase;
      if (c.isSetId()) {
        sbase.setId(c.getId());
      }
      if (c.isSetName()) {
        sbase.setName(c.getName());
      }
    } else if (libSBase instanceof org.sbml.libsbml.Species) {
      org.sbml.libsbml.Species c = (org.sbml.libsbml.Species) libSBase;
      if (c.isSetId()) {
        sbase.setId(c.getId());
      }
      if (c.isSetName()) {
        sbase.setName(c.getName());
      }
    } else if (libSBase instanceof org.sbml.libsbml.SpeciesType) {
      org.sbml.libsbml.SpeciesType c = (org.sbml.libsbml.SpeciesType) libSBase;
      if (c.isSetId()) {
        sbase.setId(c.getId());
      }
      if (c.isSetName()) {
        sbase.setName(c.getName());
      }
    } else if (libSBase instanceof org.sbml.libsbml.UnitDefinition) {
      org.sbml.libsbml.UnitDefinition c = (org.sbml.libsbml.UnitDefinition) libSBase;
      if (c.isSetId()) {
        sbase.setId(c.getId());
      }
      if (c.isSetName()) {
        sbase.setName(c.getName());
      }
    }
  }

  /**
   * 
   * @param sbase
   * @param libSBase
   */
  private void transferSBaseProperties(SBase sbase,
    org.sbml.libsbml.SBase libSBase) {

    // Memorize the corresponding original element for each SBase:
    sbase.putUserObject(LINK_TO_LIBSBML, libSBase);

    if (libSBase.isSetSBOTerm()) {
      sbase.setSBOTerm(libSBase.getSBOTerm());
    }
    if (libSBase.isSetNotes()) {
      try {
        // TODO: convert all UTF-8 characters appropriately.
        sbase.setNotes(libSBase.getNotesString());
      } catch (Throwable t) {
        logger.warn(t.getLocalizedMessage());
      }
    }
    if (libSBase.getAnnotation() != null) {
      for (int i = 0; i < libSBase.getNumCVTerms(); i++) {
        sbase.addCVTerm(LibSBMLUtils.convertCVTerm(libSBase.getCVTerm(i)));
      }
      if (libSBase.isSetModelHistory()) {
        sbase.setHistory(readHistory(libSBase.getModelHistory()));
      }
      // Parse the XML annotation nodes that are non-RDF
      XMLNode annotation = libSBase.getAnnotation();
      StringBuilder sb = new StringBuilder();
      boolean newLine = false;
      for (long i = 0; i < annotation.getNumChildren(); i++) {
        String annot = annotation.getChild(i).toXMLString();
        if (!annot.trim().startsWith("<rdf:")) {
          if (newLine) {
            sb.append('\n');
          }
          sb.append(annot);
          newLine = true;
        }
      }
      if (sb.toString().trim().length() > 0) {
        sbase.getAnnotation().setNonRDFAnnotation(sb.toString());
      }
      sbase.getAnnotation().putUserObject(LINK_TO_LIBSBML, libSBase.getAnnotation());
    }
    if (libSBase.isSetMetaId()) {
      sbase.setMetaId(libSBase.getMetaId());
    }
  }

}
