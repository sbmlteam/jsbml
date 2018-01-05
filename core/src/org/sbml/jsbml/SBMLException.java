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
package org.sbml.jsbml;

import org.sbml.jsbml.xml.XMLException;

/**
 * This class roughly corresponds to libSBML's SBMLError class.
 * 
 * @author Andreas Dr&auml;ger
 * @since 0.8
 */
public class SBMLException extends XMLException {

  /**
   * Category of this exception.
   * 
   * @author Andreas Dr&auml;ger
   * 
   */
  public enum Category {
    /**
     * Category of errors that can occur while validating general SBML
     * constructs. With respect to the SBML specification, these concern
     * failures in applying the validation rules numbered 2xxxx in the Level
     * 2 Versions 2 and 3 specifications.
     */
    GENERAL_CONSISTENCY,
    /**
     * Category of errors that can occur while validating symbol identifiers
     * in a model. With respect to the SBML specification, these concern
     * failures in applying the validation rules numbered 103xx in the Level
     * 2 Versions 2 and 3 specifications.
     */
    IDENTIFIER_CONSISTENCY,
    /**
     * This category of errors are basically a catch for something
     * completely unexpected so as to avoid just crashing.
     */
    INTERNAL,
    /**
     * Category of errors that can occur while validating libSBML's internal
     * representation of SBML constructs. (These are tests performed by
     * libSBML and do not have equivalent SBML validation rules.)
     */
    INTERNAL_CONSISTENCY,
    /**
     * Category of errors that can occur while validating MathML formulas in
     * a model. With respect to the SBML specification, these concern
     * failures in applying the validation rules numbered 102xx in the Level
     * 2 Versions 2 and 3 specifications.
     */
    MATHML_CONSISTENCY,
    /**
     * Category of warnings about recommended good practices involving SBML
     * and computational modeling. (These are tests performed by libSBML and
     * do not have equivalent SBML validation rules.)
     */
    MODELING_PRACTICE,
    /**
     * Error in the system of equations in the model: the system is
     * overdetermined, therefore violating a tenet of proper SBML. With
     * respect to the SBML specification, this is validation rule #10601 in
     * the SBML Level 2 Versions 2 and 3 specifications.
     */
    OVERDETERMINED_MODEL,
    /**
     * General error not falling into another category below.
     */
    SBML,
    /**
     * Category of errors that can only occur during attempted translation
     * from one Level/Version of SBML to another. This particular category
     * applies to errors encountered while trying to convert a model from
     * SBML Level 2 to SBML Level 1.
     */
    SBML_L1_COMPAT,
    /**
     * Category of errors that can only occur during attempted translation
     * from one Level/Version of SBML to another. This particular category
     * applies to errors encountered while trying to convert a model to SBML
     * Level 2 Version 1.
     */
    SBML_L2V1_COMPAT,
    /**
     * Category of errors that can only occur during attempted translation
     * from one Level/Version of SBML to another. This particular category
     * applies to errors encountered while trying to convert a model to SBML
     * Level 2 Version 2.
     */
    SBML_L2V2_COMPAT,
    /**
     * Category of errors that can only occur during attempted translation
     * from one Level/Version of SBML to another. This particular category
     * applies to errors encountered while trying to convert a model to SBML
     * Level 2 Version 3.
     */
    SBML_L2V3_COMPAT,
    /**
     * Category of errors that can only occur during attempted translation
     * from one Level/Version of SBML to another. This particular category
     * applies to errors encountered while trying to convert a model to SBML
     * Level 2 Version 4.
     */
    SBML_L2V4_COMPAT,
    /**
     * Category of errors that can occur while validating SBO identifiers in
     * a model. With respect to the SBML specification, these concern
     * failures in applying the validation rules numbered 107xx in the Level
     * 2 Versions 2 and 3 specifications.
     */
    SBO_CONSISTENCY,
    /**
     * These are operating system errors - like 'out of memory' and would
     * have numbers in the range 00001 - 00005.
     */
    SYSTEM,
    /**
     * Category of errors that can occur while validating the units of
     * measurement on quantities in a model. With respect to the SBML
     * specification, these concern failures in applying the validation
     * rules numbered 105xx in the Level 2 Versions 2 and 3 specifications.
     */
    UNITS_CONSISTENCY,
    /**
     * These are errors in the XML syntax that cause the underlying XML
     * parsers to fail like a missing " or tag. These have numbers 01001 -
     * 01036 and libsbml does put a fair amount of effort in making sure the
     * same error is reported regardless of which XML parser is being used.
     */
    XML
  }

  /**
   * Error codes.
   * 
   * @author Andreas Dr&auml;ger <a href="mailto:andreas.draeger@uni-tuebingen.de">andreas.draeger@uni-tuebingen.de</a>
   */
  public enum Code {
    /**
     * Annotation on &lt;sbml&gt; not permitted in SBML Level 1
     */
    AnnotationNotesNotAllowedLevel1,
    /**
     * &lt;ci&gt;'s value is not a component in this model
     */
    ApplyCiMustBeModelComponent,
    /**
     * &lt;ci&gt; does not refer to a function definition
     */
    ApplyCiMustBeUserFunction,
    /**
     * Arguments to &lt;eq&gt; or &lt;neq&gt; have inconsistent data types
     */
    ArgsToEqNeedSameType,
    /**
     * Cannot reassign a constant in an assignment rule
     */
    AssignmentToConstantEntity,
    /**
     * Mismatched units in assignment rule for compartment
     */
    AssignRuleCompartmentMismatch,
    /**
     * Mismatched units in assignment rule for parameter
     */
    AssignRuleParameterMismatch,
    /**
     * Mismatched units in assignment rule for species
     */
    AssignRuleSpeciesMismatch,
    /**
     * Invalid &lt;csymbol&gt; definitionURL attribute value
     */
    BadCsymbolDefinitionURLValue,
    /**
     * Invalid MathML expression
     */
    BadMathML,
    /**
     * Invalid MathML element
     */
    BadMathMLNodeType,
    /**
     * Non-boolean argument given to boolean operator
     */
    BooleanOpsNeedBooleanArgs,
    /**
     * Cannot set both initialConcentration and initialAmount
     */
    BothAmountAndConcentrationSet,
    /**
     * Cannot use both stoichiometry and &lt;stoichiometryMath&gt; simultaneously
     */
    BothStoichiometryAndMath,
    /**
     * Cannot convert to SBML Level 1 Version 1
     */
    CannotConvertToL1V1,
    /**
     * Celsius not defined in this Level+Version of SBML
     */
    CelsiusNoLongerValid,
    /**
     * Circular dependency involving rules and reactions
     */
    CircularRuleDependency,
    /**
     * It's best to define a size for every compartment in a model
     */
    CompartmentShouldHaveSize,
    /**
     * Compartment types not supported in this Level+Version of SBML
     */
    CompartmentTypeNotValidAttribute,
    /**
     * Compartment types not supported in this Level+Version of SBML
     */
    CompartmentTypeNotValidComponent,
    /**
     * Attribute constant not supported on this component in SBML Level 1
     */
    ConstantNotValidAttribute,
    /**
     * Attribute constant on species not supported in SBML Level 1
     */
    ConstantSpeciesNotValidAttribute,
    /**
     * XML DOCTYPE not permitted in constraint messages
     */
    ConstraintContainsDOCTYPE,
    /**
     * XML declarations not permitted in constraint messages
     */
    ConstraintContainsXMLDecl,
    /**
     * Non-boolean math expression in constraint definition
     */
    ConstraintMathNotBoolean,
    /**
     * Constraint message is not in XHTML XML namespace
     */
    ConstraintNotInXHTMLNamespace,
    /**
     * Constraints not supported in this Level+Version of SBML
     */
    ConstraintNotValidComponent,
    /**
     * Units of delay are not units of time
     */
    DelayUnitsNotTime,
    /**
     * Disallowed use of MathML definitionURL attribute
     */
    DisallowedDefinitionURLUse,
    /**
     * Disallowed use of MathML encoding attribute
     */
    DisallowedMathMLEncodingUse,
    /**
     * Disallowed MathML symbol
     */
    DisallowedMathMLSymbol,
    /**
     * Disallowed use of MathML type attribute
     */
    DisallowedMathTypeAttributeUse,
    /**
     * Disallowed MathML type attribute value
     */
    DisallowedMathTypeAttributeValue,
    /**
     * Duplicate top level annotations invalid for this SBML Level+Version
     */
    DuplicateAnnotationInvalidInL2v2,
    /**
     * Duplicate top level annotations invalid for this SBML Level+Version
     */
    DuplicateAnnotationInvalidInL2v3,
    /**
     * Duplicate top level annotations invalid for this SBML Level+Version
     */
    DuplicateAnnotationInvalidInL2v4,
    /**
     * Multiple annotations using same XML namespace
     */
    DuplicateAnnotationNamespaces,
    /**
     * Duplicate component identifier
     */
    DuplicateComponentId,
    /**
     * Duplicate local parameter identifier
     */
    DuplicateLocalParameterId,
    /**
     * Duplicate metaid identifier
     */
    DuplicateMetaId,
    /**
     * Duplicate unit definition identifier
     */
    DuplicateUnitDefinitionId,
    /**
     * A given listOf___, if present, cannot be empty
     */
    EmptyListElement,
    /**
     * The list of parameters component, if present, cannot be empty
     */
    EmptyListInKineticLaw,
    /**
     * Reaction components, if present, cannot be empty
     */
    EmptyListInReaction,
    /**
     * Empty list of units not permitted
     */
    EmptyListOfUnits,
    /**
     * variable value used in both event assignments and assignment rules
     */
    EventAndAssignmentRuleForId,
    /**
     * Mismatched units in event assignment for compartment
     */
    EventAssignCompartmentMismatch,
    /**
     * Cannot assign to a constant component in an event assignment
     */
    EventAssignmentForConstantEntity,
    /**
     * Mismatched units in event assignment for parameter
     */
    EventAssignParameterMismatch,
    /**
     * Mismatched units in event assignment for species
     */
    EventAssignSpeciesMismatch,
    /**
     * Events not supported in this Level+Version of SBML
     */
    EventNotValidComponent,
    /**
     * Failed to read floating-point number
     */
    FailedMathMLReadOfDouble,
    /**
     * Failed to read an exponential expression
     */
    FailedMathMLReadOfExponential,
    /**
     * Failed to read an integer
     */
    FailedMathMLReadOfInteger,
    /**
     * Failed to read a rational expression
     */
    FailedMathMLReadOfRational,
    /**
     * Only predefined functions are permitted in SBML Level 1 formulas
     */
    FormulaInLevel1KL,
    /**
     * Function definitions are not supported in this Level+Version of SBML
     */
    FuncDefNotValidComponent,
    /**
     * Invalid expression in function definition
     */
    FunctionDefMathNotLambda,
    /**
     * No spatialSizeUnits permitted if hasOnlySubstanceUnits=true
     */
    HasOnlySubsNoSpatialUnits,
    /**
     * Attribute hasOnlySubstanceUnits on species not supported in SBML
     * Level 1
     */
    HasOnlySubsUnitsNotValidAttribute,
    /**
     * Attribute id on species references not supported in SBML Level 1
     */
    IdNotValidAttribute,
    /**
     * Units of arguments to function call do not match function's
     * definition
     */
    InconsistentArgUnits,
    /**
     * in SBML Level 1, only three-dimensional compartments are permitted
     */
    IncorrectCompartmentSpatialDimensions,
    /**
     * Incorrect order of elements in constraint definition
     */
    IncorrectOrderInConstraint,
    /**
     * Incorrect ordering of components in event definition
     */
    IncorrectOrderInEvent,
    /**
     * Incorrect ordering of components in kinetic law definition
     */
    IncorrectOrderInKineticLaw,
    /**
     * Incorrect ordering of components in model definition
     */
    IncorrectOrderInModel,
    /**
     * Incorrect ordering of components in reaction definition
     */
    IncorrectOrderInReaction,
    /**
     * Mismatched units in initial assignment to compartment
     */
    InitAssignCompartmenMismatch,
    /**
     * Cannot set a value with both initial assignments and assignment rules
     * simultaneously
     */
    InitAssignmentAndRuleForSameId,
    /**
     * Mismatched units in initial assignment to parameter
     */
    InitAssignParameterMismatch,
    /**
     * Mismatched units in initial assignment to species
     */
    InitAssignSpeciesMismatch,
    /**
     * Initial assignments are not supported in this Level+Version of SBML
     */
    InitialAssignNotValidComponent,
    /**
     * Invalid value of units for a one-dimensional compartment
     */
    Invalid1DCompartmentUnits,
    /**
     * Invalid value of units for a two-dimensional compartment
     */
    Invalid2DCompartmentUnits,
    /**
     * Invalid value of units for a three-dimensional compartment
     */
    Invalid3DCompartmentUnits,
    /**
     * Invalid forward reference in &lt;apply&gt;&lt;ci&gt;...&lt;/ci&gt;&lt;/apply&gt; value
     */
    InvalidApplyCiInLambda,
    /**
     * Invalid redefinition of area
     */
    InvalidAreaRedefinition,
    /**
     * Invalid variable reference in assignment rule
     */
    InvalidAssignRuleVariable,
    /**
     * Unknown &lt;ci&gt; reference in &lt;lambda&gt;
     */
    InvalidCiInLambda,
    /**
     * Invalid sboTerm value for compartment
     */
    InvalidCompartmentSBOTerm,
    /**
     * Invalid compartmentType reference
     */
    InvalidCompartmentTypeRef,
    /**
     * Invalid sboTerm value for compartment type
     */
    InvalidCompartmentTypeSBOTerm,
    /**
     * Invalid content for constraint message
     */
    InvalidConstraintContent,
    /**
     * Invalid sboTerm value for constraint
     */
    InvalidConstraintSBOTerm,
    /**
     * Invalid sboTerm value for event delay
     */
    InvalidDelaySBOTerm,
    /**
     * Invalid sboTerm value for event assignment
     */
    InvalidEventAssignmentSBOTerm,
    /**
     * Invalid value for variable in event assignment
     */
    InvalidEventAssignmentVariable,
    /**
     * Invalid sboTerm value for event
     */
    InvalidEventSBOTerm,
    /**
     * Function return type must be either numerical or boolean
     */
    InvalidFunctionDefReturnType,
    /**
     * Invalid sboTerm value for function definition
     */
    InvalidFunctionDefSBOTerm,
    /**
     * Invalid identifier syntax
     */
    InvalidIdSyntax,
    /**
     * Invalid sboTerm value for initial assignment
     */
    InvalidInitAssignSBOTerm,
    /**
     * Invalid symbol reference in initial assignment
     */
    InvalidInitAssignSymbol,
    /**
     * Invalid sboTerm value for kinetic law
     */
    InvalidKineticLawSBOTerm,
    /**
     * Invalid units for a compartment in SBML Level 1
     */
    InvalidL1CompartmentUnits,
    /**
     * Invalid redefinition of length
     */
    InvalidLengthRedefinition,
    /**
     * Invalid MathML
     */
    InvalidMathElement,
    /**
     * Invalid metaid value syntax
     */
    InvalidMetaidSyntax,
    /**
     * Invalid sboTerm value for model
     */
    InvalidModelSBOTerm,
    /**
     * Invalid element in list of modifiers
     */
    InvalidModifiersList,
    /**
     * Invalid XML namespace for SBML container
     */
    InvalidNamespaceOnSBML,
    /**
     * Incorrect number of arguments to function
     */
    InvalidNoArgsPassedToFunctionDef,
    /**
     * Invalid notes content
     */
    InvalidNotesContent,
    /**
     * Invalid sboTerm value for parameter
     */
    InvalidParameterSBOTerm,
    /**
     * Invalid value for units in parameter definition
     */
    InvalidParameterUnits,
    /**
     * Invalid variable reference in rate rule
     */
    InvalidRateRuleVariable,
    /**
     * Invalid element in list of reactants or products
     */
    InvalidReactantsProductsList,
    /**
     * Invalid sboTerm value for reaction
     */
    InvalidReactionSBOTerm,
    /**
     * Invalid ordering of rules
     */
    InvalidRuleOrdering,
    /**
     * Invalid sboTerm value for rule
     */
    InvalidRuleSBOTerm,
    /**
     * Invalid SBML Level and Version
     */
    InvalidSBMLLevelVersion,
    /**
     * Invalid sboTerm value syntax
     */
    InvalidSBOTermSyntax,
    /**
     * Invalid compartment reference
     */
    InvalidSpeciesCompartmentRef,
    /**
     * Invalid species value in species reference
     */
    InvalidSpeciesReference,
    /**
     * Invalid sboTerm value for species reference
     */
    InvalidSpeciesReferenceSBOTerm,
    /**
     * Invalid sboTerm value for species
     */
    InvalidSpeciesSBOTerm,
    /**
     * Invalid value of units
     */
    InvalidSpeciesSusbstanceUnits,
    /**
     * Invalid speciesType reference
     */
    InvalidSpeciesTypeRef,
    /**
     * Invalid sboTerm value for species type
     */
    InvalidSpeciesTypeSBOTerm,
    /**
     * Invalid redefinition of substance
     */
    InvalidSubstanceRedefinition,
    /**
     * Invalid redefinition of time
     */
    InvalidTimeRedefinition,
    /**
     * Invalid sboTerm value for event trigger
     */
    InvalidTriggerSBOTerm,
    /**
     * Invalid id value for unit definition
     */
    InvalidUnitDefId,
    /**
     * Invalid unit identifier syntax
     */
    InvalidUnitIdSyntax,
    /**
     * Invalid value of kind in unit definition
     */
    InvalidUnitKind,
    /**
     * Invalid redefinition of volume
     */
    InvalidVolumeRedefinition,
    /**
     * Kinetic law units are not substance/time
     */
    KineticLawNotSubstancePerTime,
    /**
     * Cannot use &lt;kineticLaw&gt; parameter outside local scope
     */
    KineticLawParametersAreLocalOnly,
    /**
     * Compartment volume must be specified
     */
    L1V1CompartmentVolumeReqd,
    /**
     * Use of &lt;lambda&gt; not permitted outside of a &lt;functionDefinition&gt;
     */
    LambdaOnlyAllowedInFunctionDef,
    /**
     * Local parameters defined in a kinetic law shadow global parameters
     */
    LocalParameterShadowsId,
    /**
     * Formula result is not a numerical value
     */
    MathResultMustBeNumeric,
    /**
     * Attribute metaid not supported in SBML Level 1
     */
    MetaIdNotValidAttribute,
    /**
     * Missing declaration of XML namespace for annotation
     */
    MissingAnnotationNamespace,
    /**
     * Missing event assignment in event definition
     */
    MissingEventAssignment,
    /**
     * Missing model
     */
    MissingModel,
    /**
     * Missing or inconsistent value for level attribute
     */
    MissingOrInconsistentLevel,
    /**
     * Missing or inconsistent value for version attribute
     */
    MissingOrInconsistentVersion,
    /**
     * Missing compartment value for species
     */
    MissingSpeciesCompartment,
    /**
     * Missing trigger in event definition
     */
    MissingTriggerInEvent,
    /**
     * The allowable sboTerm values for model differ for this SBML
     * Level+Version
     */
    ModelSBOBranchChangedBeyondL2v2,
    /**
     * The allowable sboTerm values for model differ for this SBML
     * Level+Version
     */
    ModelSBOBranchChangedBeyondL2v3,
    /**
     * The allowable sboTerm values for model differ for this SBML
     * Level+Version
     */
    ModelSBOBranchChangedInL2v4,
    /**
     * Multiple rules for the same variable
     */
    MultipleAssignmentOrRateRules,
    /**
     * Multiple event assignments for the same variable
     */
    MultipleEventAssignmentsForId,
    /**
     * Multiple initial assignments for the same symbol value
     */
    MultipleInitAssignments,
    /**
     * Attribute multiplier on units not supported in SBML Level 1
     */
    MultiplierNotValidAttribute,
    /**
     * Cannot have multiple species of the same type in the same compartment
     */
    MultSpeciesSameTypeInCompartment,
    /**
     * Attribute name on species references not supported in SBML Level 1
     */
    NameNotValidAttribute,
    /**
     * Missing compartment in species definition
     */
    NeedCompartmentIfHaveSpecies,
    /**
     * SBML Level 1 does not support compartment types
     */
    NoCompartmentTypeInL1,
    /**
     * SBML Level 2 Version 1 does not support compartment types
     */
    NoCompartmentTypeInL2v1,
    /**
     * No initialConcentration permitted if compartment is zero-dimensional
     */
    NoConcentrationInZeroD,
    /**
     * SBML Level 1 does not support constraints
     */
    NoConstraintsInL1,
    /**
     * SBML Level 2 Version 1 does not support constraints
     */
    NoConstraintsInL2v1,
    /**
     * Attribute useValuesFromTriggerTime not supported in this
     * Level+Version of SBML
     */
    NoDelayedEventAssignmentInL2v1,
    /**
     * Attribute useValuesFromTriggerTime not supported in this
     * Level+Version of SBML
     */
    NoDelayedEventAssignmentInL2v2,
    /**
     * Attribute useValuesFromTriggerTime not supported in this
     * Level+Version of SBML
     */
    NoDelayedEventAssignmentInL2v3,
    /**
     * SBML Level 1 does not support events
     */
    NoEventsInL1,
    /**
     * Attribute timeUnits not supported in this Level+Version of SBML
     */
    NoEventTimeUnitsInL2v3,
    /**
     * The timeUnits attribute is invalid in this Level+Version of SBML
     */
    NoEventTimeUnitsInL2v4,
    /**
     * SBML Level 1 does not support non-integer nor non-rational
     * stoichiometry formulas
     */
    NoFancyStoichiometryMathInL1,
    /**
     * SBML Level 1 does not support function definitions
     */
    NoFunctionDefinitionsInL1,
    /**
     * SBML Level 2 Version 1 does not support the id attribute on species
     * references
     */
    NoIdOnSpeciesReferenceInL2v1,
    /**
     * SBML Level 1 does not support initial assignments
     */
    NoInitialAssignmentsInL1,
    /**
     * SBML Level 2 Version 1 does not support initial assignments
     */
    NoInitialAssignmentsInL2v1,
    /**
     * The substanceUnits attribute is invalid in this Level+Version of SBML
     */
    NoKineticLawSubstanceUnitsInL2v2,
    /**
     * Attribute substanceUnits not supported in this Level+Version of SBML
     */
    NoKineticLawSubstanceUnitsInL2v3,
    /**
     * The substanceUnits attribute is invalid in this Level+Version of SBML
     */
    NoKineticLawSubstanceUnitsInL2v4,
    /**
     * The timeUnits attribute is invalid in this Level+Version of SBML
     */
    NoKineticLawTimeUnitsInL2v2,
    /**
     * Attribute timeUnits not supported in this Level+Version of SBML
     */
    NoKineticLawTimeUnitsInL2v3,
    /**
     * The timeUnits attribute is invalid in this Level+Version of SBML
     */
    NoKineticLawTimeUnitsInL2v4,
    /**
     * Cannot use non-boundary species in both reactions and rules
     * simultaneously
     */
    NonBoundarySpeciesAssignedAndUsed,
    /**
     * Parameters local to a kinetic law must have constant=true
     */
    NonConstantLocalParameter,
    /**
     * Cannot use non-boundary, constant species as reactant or product
     */
    NonConstantSpeciesUsed,
    /**
     * SBML Level 1 only supports three-dimensional compartments
     */
    NoNon3DComparmentsInL1,
    /**
     * SBML Level 1 does not support non-integer stoichiometry attribute
     * values
     */
    NoNonIntegerStoichiometryInL1,
    /**
     * Cannot have a reaction with neither reactants nor products
     */
    NoReactantsOrProducts,
    /**
     * SBML Level 1 does not support the sboTerm attribute
     */
    NoSBOTermsInL1,
    /**
     * SBML Level 2 Version 1 does not support the sboTerm attribute
     */
    NoSBOTermsInL2v1,
    /**
     * No spatialSizeUnits permitted if compartment is zero-dimensional
     */
    NoSpatialUnitsInZeroD,
    /**
     * SBML Level 1 does not support species spatialSizeUnits settings
     */
    NoSpeciesSpatialSizeUnitsInL1,
    /**
     * Attribute spatialSizeUnits not supported in this Level+Version of
     * SBML
     */
    NoSpeciesSpatialSizeUnitsInL2v3,
    /**
     * The spatialSizeUnits attribute is invalid in this Level+Version of
     * SBML
     */
    NoSpeciesSpatialSizeUnitsInL2v4,
    /**
     * SBML Level 2 Version 1 does not support species types
     */
    NoSpeciesTypeInL2v1,
    /**
     * SBML Level 1 does not support species types
     */
    NoSpeciesTypesInL1,
    /**
     * XML DOCTYPE not permitted in notes
     */
    NotesContainsDOCTYPE,
    /**
     * XML declarations not permitted in notes
     */
    NotesContainsXMLDecl,
    /**
     * Notes not placed in XHTML namespace
     */
    NotesNotInXHTMLNamespace,
    /**
     * &lt;csymbol&gt; for time used within the &lt;math&gt; of a function definition
     */
    NoTimeSymbolInFunctionDef,
    /**
     * Not conformant to SBML XML schema
     */
    NotSchemaConformant,
    /**
     * Not UTF8
     */
    NotUTF8,
    /**
     * SBML Level 1 does not support multipliers or offsets in unit
     * definitions
     */
    NoUnitMultipliersOrOffsetsInL1,
    /**
     * The unit offset attribute is invalid in this Level+Version of SBML
     */
    NoUnitOffsetInL2v2,
    /**
     * Attribute offset not supported in this Level+Version of SBML
     */
    NoUnitOffsetInL2v3,
    /**
     * The unit offset attribute is invalid in this Level+Version of SBML
     */
    NoUnitOffsetInL2v4,
    /**
     * Non-numerical argument given to numerical operator
     */
    NumericOpsNeedNumericArgs,
    /**
     * Obsolete sboTerm value
     */
    ObseleteSBOTerm,
    /**
     * offset not supported in this Level+Version of SBML
     */
    OffsetNoLongerValid,
    /**
     * Attribute offset on units only available in SBML Level 2 Version 1
     */
    OffsetNotValidAttribute,
    /**
     * Incorrect number of arguments to operator
     */
    OpsNeedCorrectNumberOfArgs,
    /**
     * Model is overdetermined
     */
    OverdeterminedSystem,
    /**
     * It's best to declare units for every parameter in a model
     */
    ParameterShouldHaveUnits,
    /**
     * Second argument of &lt;piece&gt; must yield a boolean value
     */
    PieceNeedsBoolean,
    /**
     * &lt;piecewise&gt; terms have inconsistent data types
     */
    PiecewiseNeedsConsistentTypes,
    /**
     * Mismatched units in rate rule for compartment
     */
    RateRuleCompartmentMismatch,
    /**
     * Cannot reassign a constant in a rate rule
     */
    RateRuleForConstantEntity,
    /**
     * Mismatched units in rate rule for parameter
     */
    RateRuleParameterMismatch,
    /**
     * Mismatched units in rate rule for species
     */
    RateRuleSpeciesMismatch,
    /**
     * Recursive nesting of compartments via outside
     */
    RecursiveCompartmentContainment,
    /**
     * Recursive function definition
     */
    RecursiveFunctionDefinition,
    /**
     * Invalid use of SBML XML namespace in annotation
     */
    SBMLNamespaceInAnnotation,
    /**
     * The sboTerm attribute is invalid for this component in Level 2
     * Version 2
     */
    SBOTermNotUniversalInL2v2,
    /**
     * The sboTerm attribute is invalid for this component before Level 2
     * Version 2
     */
    SBOTermNotValidAttributeBeforeL2V2,
    /**
     * sboTerm not available on this component before SBML Level 2 Version 3
     */
    SBOTermNotValidAttributeBeforeL2V3,
    /**
     * Attribute spatialSizeUnits on species not supported in SBML Level 1
     */
    SpatialSizeUnitsNotValidAttribute,
    /**
     * Attribute spatialSizeUnits not supported in this Level+Version of
     * SBML
     */
    SpatialSizeUnitsRemoved,
    /**
     * Invalid value of spatialSizeUnits for a one-dimensional compartment
     */
    SpatialUnitsInOneD,
    /**
     * Invalid value of spatialSizeUnits for a three-dimensional compartment
     */
    SpatialUnitsInThreeD,
    /**
     * Invalid value of spatialSizeUnits for a two-dimensional compartment
     */
    SpatialUnitsInTwoD,
    /**
     * In SBML Level 1, a value for compartment is mandatory in species
     * definitions
     */
    SpeciesCompartmentRequiredInL1,
    /**
     * Attribute speciesType on species not supported in SBML Level 1
     */
    SpeciesTypeNotValidAttribute,
    /**
     * Species types not supported in SBML Level 1
     */
    SpeciesTypeNotValidComponent,
    /**
     * lt;stoichiometryMath&gt; not supported in SBML Level 1
     */
    StoichiometryMathNotValidComponent,
    /**
     * SBML Level 2 Version 2 requires strict sbo consistency
     */
    StrictSBORequiredInL2v2,
    /**
     * SBML Level 2 Version 3 requires strict sbo consistency
     */
    StrictSBORequiredInL2v3,
    /**
     * SBML Level 1 requires strict unit consistency
     */
    StrictUnitsRequiredInL1,
    /**
     * SBML Level 2 Version 1 requires strict unit consistency
     */
    StrictUnitsRequiredInL2v1,
    /**
     * SBML Level 2 Version 2 requires strict unit consistency
     */
    StrictUnitsRequiredInL2v2,
    /**
     * SBML Level 2 Version 3 requires strict unit consistency
     */
    StrictUnitsRequiredInL2v3,
    /**
     * Disallowed value for attribute substanceUnits
     */
    SubsUnitsAllowedInKL,
    /**
     * substanceUnits not supported in this Level+Version of SBML
     */
    SubsUnitsNoLongerValid,
    /**
     * Disallowed value for attribute timeUnits
     */
    TimeUnitsAllowedInKL,
    /**
     * Units of timeUnits are not time units
     */
    TimeUnitsEvent,
    /**
     * timeUnits not supported in this Level+Version of SBML
     */
    TimeUnitsNoLongerValid,
    /**
     * The timeUnits attribute is invalid in this Level+Version of SBML
     */
    TimeUnitsRemoved,
    /**
     * Non-boolean math expression in trigger definition
     */
    TriggerMathNotBoolean,
    /**
     * Undeclared species referenced in &lt;stoichiometryMath&gt; formula
     */
    UndeclaredSpeciesInStoichMath,
    /**
     * Undeclared species referenced in kinetic law formula
     */
    UndeclaredSpeciesRef,
    /**
     * Undeclared units
     */
    UndeclaredUnits,
    /**
     * Undefined compartment used as outside value
     */
    UndefinedOutsideCompartment,
    /**
     * Attribute units not valid
     */
    UnitsNotValidAttribute,
    /**
     * Unknown internal libSBML error
     */
    UnknownError,
    /**
     * Unrecognized sboTerm value
     */
    UnrecognisedSBOTerm,
    /**
     * Unrecognized element
     */
    UnrecognizedElement,
    /**
     * useValuesFromTriggerTime=false, but no delay defined in event
     */
    ValuesFromTriggerTimeNeedDelay,
    /**
     * Attribute variable not valid
     */
    VariableNotValidAttribute,
    /**
     * Must use exponent=1 when defining volume in terms of litres
     */
    VolumeLitreDefExponentNotOne,
    /**
     * Must use exponent=3 when defining volume in terms of metres
     */
    VolumeMetreDefExponentNot3,
    /**
     * Invalid nesting of zero-dimensional compartments
     */
    ZeroDCompartmentContainment,
    /**
     * Zero-dimensional compartments cannot be non-constant
     */
    ZeroDimensionalCompartmentConst,
    /**
     * Use of size is invalid for a zero-dimensional compartment
     */
    ZeroDimensionalCompartmentSize,
    /**
     * Use of units is invalid for a zero-dimensional compartment
     */
    ZeroDimensionalCompartmentUnits
  }

  /**
   * 
   * @author Andreas Dr&auml;ger
   * 
   */
  public enum Type {
    /**
     * 
     */
    ERROR,
    /**
     * 
     */
    FATAL,
    /**
     * 
     */
    INFO,
    /**
     * 
     */
    INTERNAL,
    /**
     * 
     */
    SYSTEM,
    /**
     * 
     */
    WARNING,
    /**
     * 
     */
    XML
  }

  /**
   * Generated serial version id.
   */
  private static final long serialVersionUID = 7680394947436628860L;

  /**
   * The category of this exception.
   */
  private Category category;

  /**
   * 
   */
  private Code code;

  /**
   * 
   */
  private String shortMessage;

  /**
   * 
   */
  private Type type;

  /**
   * Creates a new {@link SBMLException} instance.
   */
  public SBMLException() {
    super();
  }

  /**
   * Creates a new {@link SBMLException} instance.
   * 
   * @param message the exception message.
   */
  public SBMLException(String message) {
    super(message);
  }

  /**
   * Creates a new {@link SBMLException} instance.
   * 
   * @param message the exception message.
   * @param cause a {@link Throwable} that is the cause of the original problem.
   */
  public SBMLException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Creates a new {@link SBMLException} instance.
   * 
   * @param cause a {@link Throwable} that is the cause of the original problem.
   */
  public SBMLException(Throwable cause) {
    super(cause);
  }

  /**
   * 
   * @return
   */
  public Category getCategory() {
    return category;
  }

  /**
   * 
   * @return
   */
  public Code getCode() {
    return code;
  }

  /**
   * 
   * @return
   */
  public String getShortMessage() {
    return shortMessage;
  }

  /**
   * 
   * @return
   */
  public Type getType() {
    return type;
  }

  /**
   * 
   * @return
   */
  public boolean isError() {
    return type == Type.ERROR;
  }

  /**
   * 
   * @return
   */
  public boolean isFatal() {
    return type == Type.FATAL;
  }

  /**
   * 
   * @return
   */
  public boolean isInfo() {
    return type == Type.INFO;
  }

  /**
   * 
   * @return
   */
  public boolean isInternal() {
    return type == Type.INTERNAL;
  }

  /**
   * 
   * @return
   */
  public boolean isSystem() {
    return type == Type.SYSTEM;
  }

  /**
   * 
   * @return
   */
  public boolean isWarning() {
    return type == Type.WARNING;
  }

  /**
   * 
   * @return
   */
  public boolean isXML() {
    return type == Type.XML;
  }

  /**
   * 
   * @param category
   */
  public void setCategory(Category category) {
    this.category = category;
  }

  /**
   * 
   * @param code
   */
  public void setCode(Code code) {
    this.code = code;
  }

  /**
   * 
   * @param error
   */
  public void setError(boolean error) {
    if (error) {
      type = Type.ERROR;
    }
  }

  /**
   * 
   * @param fatal
   */
  public void setFatal(boolean fatal) {
    if (fatal) {
      type = Type.FATAL;
    }
  }

  /**
   * 
   * @param info
   */
  public void setInfo(boolean info) {
    if (info) {
      type = Type.INFO;
    }
  }

  /**
   * 
   * @param internal
   */
  public void setInternal(boolean internal) {
    if (internal) {
      type = Type.INTERNAL;
    }
  }

  /**
   * 
   * @param shortMessage
   */
  public void setShortMessage(String shortMessage) {
    this.shortMessage = shortMessage;
  }

  /**
   * 
   * @param system
   */
  public void setSystem(boolean system) {
    if (system) {
      type = Type.SYSTEM;
    }
  }

  /**
   * 
   * @param warning
   */
  public void setWarning(boolean warning) {
    if (warning) {
      type = Type.WARNING;
    }
  }

  /**
   * 
   * @param xml
   */
  public void setXML(boolean xml) {
    if (xml) {
      type = Type.XML;
    }
  }
}
