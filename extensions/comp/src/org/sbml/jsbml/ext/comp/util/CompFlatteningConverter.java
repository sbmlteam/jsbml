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
package org.sbml.jsbml.ext.comp.util;

import org.sbml.jsbml.*;
import org.sbml.jsbml.ext.comp.*;
import org.sbml.jsbml.util.Pair;

import java.util.*;
import java.util.logging.Logger;

/**
 * The {@link CompFlatteningConverter} object translates a hierarchical model defined with the SBML Level 3
 * Hierarchical Model Composition package into a 'flattened' version of the same model. This means the the hierarchical
 * structure is dissolved and all objects are built into a single model that does no longer require the comp package.
 *
 * @author Christoph Blessing
 * @author Eike Pertuch
 * @since 1.0
 */
public class CompFlatteningConverter {

    private final static Logger LOGGER = Logger.getLogger(CompFlatteningConverter.class.getName());

    private ListOf<ModelDefinition> modelDefinitionListOf;

    // Map containing prefix for each submodel
    private Map<List<String>, String> subModelPrefixes;

    private List<Submodel> listOfSubmodelsToFlatten;

    // Map of Replaced Elements in the form pathToModel -> idType (id, metaID, ...) -> replacedId -> replacedElementInfo
    private Map<List<String>, Map<IdType, Map<String, ReplacedElementInfo>>> replacedAndDeletedElementsHashMap;

    // Contains newly created (generally copied) initial assignments
    private Map<List<String>, List<InitialAssignment>> newInitAssignHashMap;

    // Contains newly created (generally copied) rules
    private Map<List<String>, List<Rule>> newRulesHashMap;

    // Contains replaced units (their references can only be changed at the end)
    private Map<String, String> newUnitDefMaps;

    // Final flattened model that is merged into bottom-up in the submodel tree
    private Model flattenedModel;

    // Path to currently visited submodel (node)
    private List<String> curPath;

    // Previously visited models
    private Map<String, Model> visitedModels;

    public CompFlatteningConverter() {
        this.listOfSubmodelsToFlatten = new ArrayList<>();

        this.modelDefinitionListOf = new ListOf<>();

        this.replacedAndDeletedElementsHashMap = new HashMap<>();

        this.newInitAssignHashMap = new HashMap<>();

        this.newRulesHashMap = new HashMap<>();

        this.newUnitDefMaps = new HashMap<>();

        this.flattenedModel = new Model();

        this.subModelPrefixes = new HashMap<List<String>, String>();

        this.curPath = new ArrayList<>();

        this.visitedModels = new HashMap<>();

    }


    /**
     * Public method to call on a CompflatteningConverter object.
     * Takes a SBML Document and flattens the models of the comp plugin.
     * Returns the SBML Document with a flattend model.
     *
     * @param document SBML Document to flatten
     * @return SBML Document with flattened model
     */
    public SBMLDocument flatten(SBMLDocument document) {

        if (document.isPackageEnabled(CompConstants.shortLabel)) {

            CompSBMLDocumentPlugin compSBMLDocumentPlugin = (CompSBMLDocumentPlugin) document.getExtension(CompConstants.shortLabel);

            this.modelDefinitionListOf = compSBMLDocumentPlugin.getListOfModelDefinitions();

            if (document.isSetModel() && document.getModel().getExtension(CompConstants.shortLabel) != null) {

                //this.flattenedModel = document.getModel();
                this.flattenedModel.setLevel(document.getLevel());
                this.flattenedModel.setVersion(document.getVersion());
                // Perform flattening of model
                performFlattening(document.getModel(), null, new ArrayList<String>(), new ArrayList<String>());
                // Set model units of flattened model
                setModelUnits(document.getModel());
                // replace Units in flattened model that were replaced
                replaceUnits();

            } else {
                LOGGER.warning("No comp package found in Model. Cannot flatten.");
            }

        } else {
            LOGGER.warning("No comp package found in Document. Cannot flatten.");
        }

        // Remove lists that are now empty from final model
        for(ListOf<? extends AbstractSBase> modelList: getAllListsOfModel(this.flattenedModel)) {
            if(modelList.isEmpty()) {
                modelList.removeFromParent();
            }
        }

        this.flattenedModel.unsetExtension(CompConstants.shortLabel);
        this.flattenedModel.unsetPlugin(CompConstants.shortLabel);
        this.flattenedModel.setId(document.getModel().getId());
        this.flattenedModel.setName(document.getModel().getName());
        this.flattenedModel.unsetExtension(CompConstants.shortLabel);

        document.unsetExtension(CompConstants.shortLabel);
        document.disablePackage(CompConstants.shortLabel);
        document.setModel(this.flattenedModel);

        return document;
    }

    /**
     * Method that performs the flattening of the model
     * Recursively calls itself to flatten models in submodel tree
     * depth-first postorder.
     *
     * @param model currentModel (to be flattened)
     * @param subModelID Id of submodel (as defined in submodel list of parent model)
     * @param timeConvFactors Time Conversion Factors applied to current and all parent models
     * @param extentConvFactors Extent Conversion Factors applied to current and all parent models
     * @return
     */
    private void performFlattening(Model model, String subModelID,
                                    List<String> timeConvFactors, List<String> extentConvFactors) {


        // Model needs to be cloned to be accessible after recursion call
        Model modelCopy = model.clone();

        this.visitedModels.put(modelCopy.getId(), modelCopy.clone());

        // Initialize time and extent conversion factors list to save current factors for
        List<String> curTimeConvFactors = new ArrayList<>(timeConvFactors);
        List<String> curExtentConvFactors = new ArrayList<>(extentConvFactors);

        this.curPath.add(subModelID == null ? (model.getId().isEmpty()? "mainModel": model.getId())  : subModelID);
        CompModelPlugin compModelPlugin = (CompModelPlugin) modelCopy.getExtension(CompConstants.shortLabel);

        // STEP 1: If current model contains submodels, flatten those submodels first
        // Also collect all replaced elements in submodels of current model in this.replacedElementsHashMap
        parseAllListsOfReplacedElements(modelCopy, curPath);

        String subModelPrefix;
        // Perform steps 2-6 only for submodels (not for the main model)
        // STEP 2: Find prefix unique prefix containing Submodel_id
        if (subModelID != null) {

            CompFlatteningIDExpanderTS idExpander = new CompFlatteningIDExpanderTS();
            subModelPrefix = idExpander.expandID(modelCopy, this.curPath);

        } else {
            subModelPrefix = "";
        }
        this.subModelPrefixes.put(new ArrayList<String>(this.curPath), subModelPrefix);

        // If current model contains compModelPlugin and has submodels -> go into submodels
        if (compModelPlugin != null && compModelPlugin.getSubmodelCount() > 0) {
            for (Submodel submodel : compModelPlugin.getListOfSubmodels()) {
                // ParseListOfDeletions in current submodel and add to replacedAndDeletedElementsHashMap
                parseListOfDeletions(submodel.getListOfDeletions(), curPath, submodel.getId());
                Model submodelModel = this.modelDefinitionListOf.get(submodel.getModelRef()).getModel().clone();

                // Add time and extent conversion factors of model to list of active conversion factors
                if (submodel.isSetTimeConversionFactor()) {
                    String timeConvFactorName = submodel.getTimeConversionFactor();
                    if (timeConvFactorName != null) {
                        String timeConvFactor = model.getParameter(timeConvFactorName).getId();
                        timeConvFactors.add(subModelPrefix + timeConvFactor);
                    }
                }
                if (submodel.isSetExtentConversionFactor()) {
                    String extentConvFactorName = submodel.getExtentConversionFactor();
                    if (extentConvFactorName != null) {
                        String extentConvFactor = model.getParameter(extentConvFactorName).getId();
                        extentConvFactors.add(subModelPrefix + extentConvFactor);
                    }
                }

                // Recursively call flattening for each submodel
                performFlattening(submodelModel, submodel.getId(), timeConvFactors, extentConvFactors);
            }
        }

        // Now parse ReplacedBy Elements and add to replacedAndDeletedElementsHashMap
        parseAllReplacedBy(modelCopy, this.curPath);

        // STEP 3: Remove all objects which were either deleted or replaced
        Map<String, ReplacedElementInfo> replacedElements = removeReplacedAndDeletedElements(modelCopy, compModelPlugin);

        // STEP 4: Add newly created InitialAssignments and Rules
        addNewElementsWithVariables(modelCopy);

        // STEP 5: Add prefix to all objects
        addPrefixesToModelObjects(modelCopy, subModelPrefix);

        // STEP 6: Add prefix to all references and adjust them according to replacements
        ASTNode timeConvFactorNode = createNewConvNode(modelCopy, curTimeConvFactors);
        ASTNode extentConvFactorNode = createNewConvNode(modelCopy, curExtentConvFactors);
        addPrefixesAndReplacementsToModelReferences(modelCopy, subModelPrefix, replacedElements, timeConvFactorNode,
                extentConvFactorNode);

        // STEP 7: Merge model into flattened model
        this.flattenedModel = mergeModels(this.flattenedModel, modelCopy);

        // Submodel flattenening finished -> go up in submodel tree;
        this.curPath.remove(this.curPath.size() - 1);
    }


    //////////////////////////////////////////
    /// Replacements/Deletions parsers //////
    ////////////////////////////////////////

    /**
     *
     * Parse deletions in current model
     *
     * @param deletions
     * @param curPath
     * @param submodelID
     */
    private void parseListOfDeletions(List<Deletion> deletions, List<String> curPath, String submodelID) {

        Map<String, ReplacedElementInfo> replacedIDElementsInModel = null;
        if(this.replacedAndDeletedElementsHashMap.containsKey(curPath)) {
            replacedIDElementsInModel = this.replacedAndDeletedElementsHashMap.get(curPath).get(IdType.ID);
        }

        for (Deletion deletion : deletions) {
            // If deleted Element was added previously -> skip
            if(replacedIDElementsInModel != null && replacedIDElementsInModel.containsKey(deletion.getId())) {
                continue;
            }
            List<String> deletionPath = new ArrayList<>(curPath);
            deletionPath.add(submodelID);
            IdType deletedElementType;
            String deletedElementRef;
            Pair<String, IdType> returnValue;
            // If deletion is nested (sBaseRef set) get final sBaseRef and extend deletionPath accordingly
            if (deletion.isSetSBaseRef()) {
                deletionPath.add(deletion.getIdRef());
                SBaseRef finalSBaseRef = determineSubmodelPath(deletion.getSBaseRef(), deletionPath);
                returnValue = selectCorrectRef(finalSBaseRef);
            } else {
                returnValue = selectCorrectRef(deletion);
            }
            deletedElementRef = returnValue.getKey();
            deletedElementType = returnValue.getValue();

            // Add deleted element to replacedAndDeletedElementsHashMap
            if (deletedElementRef != null) {
                Map<IdType, Map<String, ReplacedElementInfo>> deletedElementsForModel = this.replacedAndDeletedElementsHashMap.get(deletionPath);
                if (deletedElementsForModel == null) {
                    Map<String, ReplacedElementInfo> innerHashMap = new HashMap<>();
                    innerHashMap.put(deletedElementRef, null);
                    Map<IdType, Map<String, ReplacedElementInfo>> idHashMap = new HashMap<>();
                    idHashMap.put(deletedElementType, innerHashMap);
                    this.replacedAndDeletedElementsHashMap.put(deletionPath, idHashMap);
                } else {
                    Map<String, ReplacedElementInfo> idHashMap = deletedElementsForModel.get(deletedElementType);
                    if (idHashMap != null) {
                        idHashMap.put(deletedElementRef, null);
                    } else {
                        Map<String, ReplacedElementInfo> deletedIDs = new HashMap<>();
                        deletedIDs.put(deletedElementRef, null);
                        deletedElementsForModel.put(deletedElementType, deletedIDs);
                    }
                }
            }
        }
    }

    /**
     *
     * Parses all lists of replacedElements in model and add them to replacedAndDeletedElementsHashMap
     *
     * @param model
     * @param curPath
     */
    private void parseAllListsOfReplacedElements(Model model, List<String> curPath) {

        List<List<? extends AbstractSBase>> listOfListsOfSBases = getListOfListsOfSBases(model);

        // Iterate over every SBase list in model
        for (List<? extends AbstractSBase> listOfSBases : listOfListsOfSBases) {
            for (AbstractSBase sBase : listOfSBases) {
                String sBaseID = sBase.getId();
                CompSBasePlugin curCompSBasePlugin = (CompSBasePlugin) sBase.getExtension(CompConstants.shortLabel);

                // Parse list of elements that are replaced by this element
                if (curCompSBasePlugin != null) {
                    ListOf<ReplacedElement> listOfReplacedElements = curCompSBasePlugin.getListOfReplacedElements();
                    for (ReplacedElement replacedElement : listOfReplacedElements) {
                        List<String> submodelPath = new ArrayList<>(curPath);

                        ReplacedElementInfo replacedElementInfo = new ReplacedElementInfo(
                                sBaseID,
                                model.getId(),
                                IdType.ID,
                                replacedElement.isSetConversionFactor()? replacedElement.getConversionFactor(): null,
                                new ArrayList<String>(submodelPath)
                        );

                        String replacedElementRef = null;
                        IdType replacedElementType = null;
                        submodelPath.add(replacedElement.getSubmodelRef());
                        // If replacedElement is nested (sBaseRef set) get final sBaseRef and extend submodelPath accordingly
                        if (replacedElement.getSBaseRef() != null) {
                            submodelPath.add(replacedElement.getIdRef());
                            // Get last sBaseRef in nested sBaseRef and extend current submodelPath accordingly
                            SBaseRef finalSBaseRef = determineSubmodelPath(replacedElement.getSBaseRef(), submodelPath);
                            Pair<String, IdType> returnValue = selectCorrectRef(finalSBaseRef);
                            replacedElementRef = returnValue.getKey();
                            replacedElementType = returnValue.getValue();
                        }
                        else {
                            Pair<String, IdType> returnValue = selectCorrectRef(replacedElement);
                            replacedElementRef = returnValue.getKey();
                            replacedElementType = returnValue.getValue();
                        }
                        // If replaceElement is not null add to replacedAndDeletedElementsHashMap
                        if (replacedElementRef != null) {
                            Map<IdType, Map<String, ReplacedElementInfo>> hashMapOfModel = this.replacedAndDeletedElementsHashMap.get(submodelPath);
                            if (hashMapOfModel == null) {
                                Map<IdType, Map<String, ReplacedElementInfo>> typeHashMap = new HashMap<IdType, Map<String, ReplacedElementInfo>>();
                                Map<String, ReplacedElementInfo> replacementHashMap = new HashMap<String, ReplacedElementInfo>();
                                replacementHashMap.put(replacedElementRef, replacedElementInfo);
                                typeHashMap.put(replacedElementType, replacementHashMap);
                                this.replacedAndDeletedElementsHashMap.put(
                                        submodelPath,
                                        typeHashMap
                                );
                            } else {
                                Map<String, ReplacedElementInfo> hashMapOfRefType = hashMapOfModel.get(replacedElementType);
                                if (hashMapOfRefType == null) {
                                    Map<String, ReplacedElementInfo> replacementHashMap = new HashMap<String, ReplacedElementInfo>();
                                    replacementHashMap.put(replacedElementRef, replacedElementInfo);
                                    hashMapOfModel.put(replacedElementType, replacementHashMap);
                                } else {
                                    hashMapOfRefType.put(replacedElementRef, replacedElementInfo);
                                }
                            }
                        }
                    }
                    if(!curCompSBasePlugin.isSetReplacedBy()) {
                        sBase.unsetExtension(CompConstants.shortLabel);
                        sBase.disablePackage(CompConstants.shortLabel);
                    }
                }
            }
        }
    }

    /**
     *
     * Parse all replacedBy elements and add them to replacedAndDeletedElementsHashMap
     *
     * @param model
     * @param submodelPath
     */
    private void parseAllReplacedBy(Model model, List<String> submodelPath) {

        List<String> submodelPathOg = new ArrayList<>(submodelPath);
        List<String> submodelPathCopy = new ArrayList<>(submodelPath);

        List<List<? extends AbstractSBase>> listOfListsOfSBases = getListOfListsOfSBases(model);

        for (List<? extends AbstractSBase> listOfSBases : listOfListsOfSBases) {
            for (AbstractSBase sBase : listOfSBases) {
                submodelPathCopy = new ArrayList<>(submodelPath);
                String sBaseID = sBase.getId();
                CompSBasePlugin curCompSBasePlugin = (CompSBasePlugin) sBase.getExtension(CompConstants.shortLabel);
                // Parse list of elements that are replaced by this element
                if (curCompSBasePlugin != null) {
                    // Get ReplacedBy and add
                    ReplacedBy replacedBy = curCompSBasePlugin.getReplacedBy();
                    if (replacedBy != null) {
                        if (!submodelPathCopy.isEmpty()) {
                            Map<IdType, Map<String, ReplacedElementInfo>> hashMapOfModel = this.replacedAndDeletedElementsHashMap.get(submodelPathOg);
                            String replacedByRef = null;
                            IdType replacedByType = null;
                            submodelPathCopy.add(replacedBy.getSubmodelRef());

                            //If replacedBy is nested (sBaseRef set) get final sBaseRef and extend submodelPath accordingly
                            if (replacedBy.getSBaseRef() != null) {
                                SBaseRef finalSBaseRef = determineSubmodelPath(replacedBy.getSBaseRef(), submodelPathCopy);
                                if (finalSBaseRef.isSetIdRef()) {
                                    replacedByRef = finalSBaseRef.getIdRef();
                                    replacedByType = IdType.ID;
                                } else if (finalSBaseRef.isSetMetaIdRef()) {
                                    replacedByRef = finalSBaseRef.getMetaIdRef();
                                    replacedByType = IdType.META_ID;
                                } else if (finalSBaseRef.isSetPortRef()) {
                                    replacedByRef = finalSBaseRef.getPortRef();
                                    replacedByType = IdType.PORT;
                                } else if (finalSBaseRef.isSetUnitRef()) {
                                    replacedByRef = finalSBaseRef.getUnitRef();
                                    replacedByType = IdType.UNIT_ID;
                                }
                            } else {
                                if (replacedBy.isSetIdRef()) {
                                    replacedByRef = replacedBy.getIdRef();
                                    replacedByType = IdType.ID;
                                } else if (replacedBy.isSetMetaIdRef()) {
                                    replacedByRef = replacedBy.getMetaIdRef();
                                    replacedByType = IdType.META_ID;
                                } else if (replacedBy.isSetPortRef()) {
                                    replacedByRef = replacedBy.getPortRef();
                                    replacedByType = IdType.PORT;
                                } else if (replacedBy.isSetUnitRef()) {
                                    replacedByRef = replacedBy.getUnitRef();
                                    replacedByType = IdType.UNIT_ID;
                                }
                            }

                            String modelRef = ((CompModelPlugin)model.getExtension(CompConstants.shortLabel)).getListOfSubmodels().get(replacedBy.getSubmodelRef()).getModelRef();
                            ReplacedElementInfo replacedElementInfo = new ReplacedElementInfo(
                                    replacedByRef,
                                    modelRef,
                                    replacedByType,
                                    null,
                                    submodelPathCopy
                            );


                            // Add replacement to replacedAndDeletedElementsHashMap
                            if (hashMapOfModel == null) {
                                Map<IdType, Map<String, ReplacedElementInfo>> typeHashMap = new HashMap<IdType, Map<String, ReplacedElementInfo>>();
                                Map<String, ReplacedElementInfo> replacementHashMap = new HashMap<String, ReplacedElementInfo>();
                                replacementHashMap.put(sBaseID, replacedElementInfo);
                                typeHashMap.put(IdType.ID, replacementHashMap);
                                this.replacedAndDeletedElementsHashMap.put(
                                        submodelPathOg,
                                        typeHashMap
                                );
                            } else {
                                Map<String, ReplacedElementInfo> hashMapOfRefType = hashMapOfModel.get(IdType.ID);
                                if (hashMapOfRefType == null) {
                                    Map<String, ReplacedElementInfo> replacementHashMap = new HashMap<String, ReplacedElementInfo>();
                                    replacementHashMap.put(sBaseID, replacedElementInfo);
                                    hashMapOfModel.put(IdType.ID, replacementHashMap);
                                } else {
                                    hashMapOfRefType.put(sBaseID, replacedElementInfo);
                                }
                            }
                        }
                    }
                    sBase.unsetExtension(CompConstants.shortLabel);
                    sBase.disablePackage(CompConstants.shortLabel);
                }
            }
        }
    }

    //////////////////////////////////////////
    /// References modifier methods /////////
    ////////////////////////////////////////

    /**
     *
     * Add prefixes to references, replace references to elements that were replaced
     * and add conversion factors appropriately (as defined in comp package docs)
     *
     * @param model
     * @param subModelPrefix
     * @param replacedElementsHashMap
     * @param timeConvFactorNode
     * @param extentConvFactorNode
     */
    private void addPrefixesAndReplacementsToModelReferences(Model model, String subModelPrefix,
                                                             Map<String, ReplacedElementInfo> replacedElementsHashMap,
                                                             ASTNode timeConvFactorNode,
                                                             ASTNode extentConvFactorNode) {


        // Rules
        for (Rule rule : model.getListOfRules()) {
            Class<? extends Rule> classRule = rule.getClass();
            String variable;

            replaceVariables(rule.getMath(), replacedElementsHashMap, subModelPrefix, timeConvFactorNode);
            if (!classRule.equals(AlgebraicRule.class)) {
                if (classRule.equals(RateRule.class)) {
                    RateRule rRule = (RateRule) rule;
                    variable = rRule.getVariable();
                    if (replacedElementsHashMap.containsKey(variable)) {
                        rRule.setVariable(replacedElementsHashMap.get(variable).id);
                        String convFactor = replacedElementsHashMap.get(variable).conversionFactor;
                        if (convFactor != null) {
                            ASTNode convNode = new ASTNode(convFactor);
                            rRule.getMath().multiplyWith(convNode);
                        }
                        if (timeConvFactorNode != null) {
                            rRule.getMath().divideBy(timeConvFactorNode);
                        }
                    } else {
                        rRule.setVariable(subModelPrefix + variable);
                    }
                } else if (classRule.equals(AssignmentRule.class)) {
                    AssignmentRule aRule = (AssignmentRule) rule;
                    variable = aRule.getVariable();
                    if (replacedElementsHashMap.containsKey(variable)) {
                        aRule.setVariable(replacedElementsHashMap.get(variable).id);
                        String convFactor = replacedElementsHashMap.get(variable).conversionFactor;
                        if (convFactor != null) {
                            ASTNode convNode = new ASTNode(convFactor);
                            aRule.getMath().multiplyWith(convNode);
                        }
                    } else {
                        aRule.setVariable(subModelPrefix + variable);
                    }
                }
            }
        }

        // Initial Assignments
        for (InitialAssignment initAssign : model.getListOfInitialAssignments()) {
            replaceVariables(initAssign.getMath(), replacedElementsHashMap, subModelPrefix, timeConvFactorNode);
            String initAssignVariable = initAssign.getVariable();
            if (replacedElementsHashMap.containsKey(initAssignVariable)) {
                initAssign.setVariable(replacedElementsHashMap.get(initAssignVariable).id);
                String convFactor = replacedElementsHashMap.get(initAssignVariable).conversionFactor;
                if (convFactor != null) {
                    ASTNode convNode = new ASTNode(convFactor);
                    initAssign.getMath().multiplyWith(convNode);
                }
            } else {
                initAssign.setVariable(subModelPrefix + initAssignVariable);
            }
        }

        for(Compartment compartment: model.getListOfCompartments()) {
            updateUnits(compartment, replacedElementsHashMap, subModelPrefix, model);
        }

        // Species compartments
        for (Species species : model.getListOfSpecies()) {
            String compartment = species.getCompartment();
            updateUnits(species, replacedElementsHashMap, subModelPrefix, model);
            if (replacedElementsHashMap.containsKey(compartment)) {
                species.setCompartment(replacedElementsHashMap.get(compartment).id);
            } else {
                species.setCompartment(subModelPrefix + compartment);
            }
        }

        // Constraints
        for (Constraint constraint : model.getListOfConstraints()) {
            replaceVariables(constraint.getMath(), replacedElementsHashMap, subModelPrefix, timeConvFactorNode);
        }

        // Reactions and more specifically Reactants, Products and Kinetic laws
        for (Reaction reaction : model.getListOfReactions()) {

            for (SpeciesReference speciesRef : reaction.getListOfReactants()) {
                String species = speciesRef.getSpecies();
                if (replacedElementsHashMap.containsKey(species)) {
                    speciesRef.setSpecies(replacedElementsHashMap.get(species).id);
                } else {
                    speciesRef.setSpecies(subModelPrefix + species);
                }
                updateIDs(speciesRef, replacedElementsHashMap, subModelPrefix);
            }

            for (SpeciesReference speciesRef : reaction.getListOfProducts()) {
                String species = speciesRef.getSpecies();
                if (replacedElementsHashMap.containsKey(species)) {
                    speciesRef.setSpecies(replacedElementsHashMap.get(species).id);
                } else {
                    speciesRef.setSpecies(subModelPrefix + species);
                }
                updateIDs(speciesRef, replacedElementsHashMap, subModelPrefix);
            }

            if (reaction.isSetKineticLaw()) {
                replaceVariables(reaction.getKineticLaw().getMath(), replacedElementsHashMap, subModelPrefix, timeConvFactorNode);
                ASTNode convFactorNode;
                if (extentConvFactorNode != null && timeConvFactorNode != null) {
                    convFactorNode = extentConvFactorNode.divideBy(timeConvFactorNode);
                    reaction.getKineticLaw().getMath().multiplyWith(convFactorNode);
                } else if (extentConvFactorNode != null) {
                    convFactorNode = extentConvFactorNode;
                    reaction.getKineticLaw().getMath().multiplyWith(convFactorNode);
                } else if (timeConvFactorNode != null) {
                    convFactorNode = new ASTNode(1).divideBy(timeConvFactorNode);
                    reaction.getKineticLaw().getMath().multiplyWith(convFactorNode);
                }

                for (LocalParameter localParameter : reaction.getKineticLaw().getListOfLocalParameters()) {
                    updateUnits(localParameter, replacedElementsHashMap, subModelPrefix, model);
                    updateIDs(localParameter, replacedElementsHashMap, subModelPrefix);
                }
            }
        }

        // Delays, triggers and event assignments
        for (Event event : model.getListOfEvents()) {
            Delay delay = event.getDelay();
            if (delay != null) {
                replaceVariables(delay.getMath(), replacedElementsHashMap, subModelPrefix, timeConvFactorNode);
                if (timeConvFactorNode != null) {
                    delay.getMath().multiplyWith(timeConvFactorNode);
                }
                updateIDs(delay, replacedElementsHashMap, subModelPrefix);
            }

            Trigger trigger = event.getTrigger();
            if (trigger != null) {
                replaceVariables(trigger.getMath(), replacedElementsHashMap, subModelPrefix, timeConvFactorNode);
                updateIDs(trigger, replacedElementsHashMap, subModelPrefix);
            }

            Priority priority = event.getPriority();
            if (priority != null) {
                replaceVariables(priority.getMath(), replacedElementsHashMap, subModelPrefix, timeConvFactorNode);
                updateIDs(priority, replacedElementsHashMap, subModelPrefix);
            }

            for (EventAssignment eventAssignment : event.getListOfEventAssignments()) {
                replaceVariables(eventAssignment.getMath(), replacedElementsHashMap, subModelPrefix, timeConvFactorNode);
                if (replacedElementsHashMap.containsKey(eventAssignment.getVariable())) {
                    ReplacedElementInfo value = replacedElementsHashMap.get(eventAssignment.getVariable());
                    eventAssignment.setVariable(value.id);
                    if (value.conversionFactor != null) {
                        eventAssignment.getMath().multiplyWith(new ASTNode(value.conversionFactor));
                    }
                }
                else {
                    eventAssignment.setVariable(subModelPrefix + eventAssignment.getVariable());
                }
                updateIDs(eventAssignment, replacedElementsHashMap, subModelPrefix);
            }
        }
    }

    /**
     * Set units of newly created model according to original model
     *
     * @param ogModel original model
     */
    private void setModelUnits(Model ogModel) {

        if(ogModel.isSetAreaUnits()) {
            this.flattenedModel.setAreaUnits(ogModel.getAreaUnits());
        }
        if(ogModel.isSetExtentUnits()) {
            this.flattenedModel.setExtentUnits(ogModel.getExtentUnits());
        }
        if(ogModel.isSetLengthUnits()) {
            this.flattenedModel.setLengthUnits(ogModel.getLengthUnits());
        }
        if(ogModel.isSetTimeUnits()) {
            this.flattenedModel.setTimeUnits(ogModel.getTimeUnits());
        }
        if(ogModel.isSetSubstanceUnits()) {
            this.flattenedModel.setSubstanceUnits(ogModel.getSubstanceUnits());
        }
        if(ogModel.isSetVolumeUnits()) {
            this.flattenedModel.setVolumeUnits(ogModel.getSubstanceUnits());
        }

    }

    /**
     *
     * Replace Units that were replaced
     *
     */
    private void replaceUnits() {

        // Replace units (references) in compartments
        for(Compartment comp: this.flattenedModel.getListOfCompartments()) {
            if(comp.isSetUnits() && this.newUnitDefMaps.containsKey(comp.getUnits())) {
                comp.setUnits(newUnitDefMaps.get(comp.getUnits()));
            }
        }

        // Replace units (references) in species
        for (Species spec : this.flattenedModel.getListOfSpecies()) {
            if (spec.isSetUnits() && this.newUnitDefMaps.containsKey(spec.getUnits())) {
                spec.setUnits(this.newUnitDefMaps.get(spec.getUnits()));
            }
        }

        // Replace units (references) in local parameters of kinetic laws
        for(Reaction reac: this.flattenedModel.getListOfReactions()) {
            if(reac.isSetKineticLaw() && reac.getKineticLaw().isSetListOfLocalParameters()) {
                for(LocalParameter lp: reac.getKineticLaw().getListOfLocalParameters()) {
                    if(this.newUnitDefMaps.containsKey(lp.getUnits())) {
                        lp.setUnits(this.newUnitDefMaps.get(lp.getUnits()));
                    }
                }
            }
        }
    }

    /**
     *
     * Recursively replace variables/symbols in math elements
     *
     * @param parent
     * @param replacedElementsHashMap
     * @param subModelPrefix
     * @param timeConvFactorNode
     */
    private void replaceVariables(ASTNode parent, Map<String, ReplacedElementInfo> replacedElementsHashMap,
                                  String subModelPrefix, ASTNode timeConvFactorNode) {

        int i = 0;
        List<ASTNode> listOfNodes = new ArrayList<>(parent.getListOfNodes());
        for (ASTNode node : listOfNodes) {
            List<ASTNode> children = node.getChildren();
            // If leaf is reached -> apply Conversion Factor
            // Otherwise recall with children of node
            if (children.isEmpty()) {
                applyConvFactorToNode(node, parent, replacedElementsHashMap, subModelPrefix, timeConvFactorNode, i);
            } else {
                replaceVariables(node, replacedElementsHashMap, subModelPrefix, timeConvFactorNode);
            }
            i++;
        }
        applyConvFactorToNode(parent, parent, replacedElementsHashMap, subModelPrefix, timeConvFactorNode, i);
    }

    /**
     *
     * Update the id of a given sBase by either adding a prefix or replacing it if present in replacedElementHashMap
     *
     * @param sBase
     * @param replacedElementsHashMap
     * @param subModelPrefix
     */
    private void updateIDs(SBase sBase, Map<String, ReplacedElementInfo> replacedElementsHashMap, String subModelPrefix) {

        if(sBase.isSetId()) {
            if (replacedElementsHashMap.containsKey(sBase.getId())) {
                String value = replacedElementsHashMap.get(sBase.getId()).id;
                sBase.setId(value);
            } else {
                sBase.setId(subModelPrefix + sBase.getId());
            }
        }
        if(sBase.isSetMetaId()) {
            if (replacedElementsHashMap.containsKey(sBase.getMetaId())) {
                String value = replacedElementsHashMap.get(sBase.getMetaId()).id;
                sBase.setMetaId(value);
            } else {
                sBase.setMetaId(subModelPrefix + sBase.getMetaId());
            }
        }
    }

    /**
     *
     * Update Units of givem SBase depending on if its present in replacedElementsHashMap
     *
     * @param sBase
     * @param replacedElementsHashMap
     * @param subModelPrefix
     * @param model
     */
    private void updateUnits(SBaseWithUnit sBase, Map<String, ReplacedElementInfo> replacedElementsHashMap,
                               String subModelPrefix, Model model) {

        if(sBase.isSetUnits()) {
            if (replacedElementsHashMap.containsKey(sBase.getUnits())) {
                String subPrefix = this.subModelPrefixes.get(replacedElementsHashMap.get(sBase.getUnits()).replacedElementPath);
                this.newUnitDefMaps.put(sBase.getUnits(), subPrefix + replacedElementsHashMap.get(sBase.getUnits()).id);
            } else if (model.getPredefinedUnitDefinition(sBase.getUnits()) == null) {
                this.newUnitDefMaps.put(sBase.getUnits(), subModelPrefix + sBase.getUnits());
            }
        }
    }

    /**
     * Remove elements that were either replaced or deleted in the model
     * and at them to the replacedElementsHashmap to later update ID references accordingly
     *
     * @param modelCopy
     * @param compModelPlugin
     * @return Map of replaced elements
     */
    private Map<String, ReplacedElementInfo> removeReplacedAndDeletedElements(Model modelCopy, CompModelPlugin compModelPlugin) {

        // Get all replaced and deleted Elements for current model and initialize ReplacedElements map
        Map<IdType, Map<String, ReplacedElementInfo>> replacedElementsInModel = this.replacedAndDeletedElementsHashMap.get(curPath);
        Map<String, ReplacedElementInfo> replacedElements = new HashMap<>();

        Model ogModelCopy = modelCopy.clone();

        if (replacedElementsInModel != null) {
            // Iterate over every entry in map of replaced and deleted elements
            for (IdType key : replacedElementsInModel.keySet()) {
                // Different replacement procedures are needed depending on idType (id, metaID, portID, unitID) of the element that is replaced
                switch (key) {
                    case PORT:
                        if (compModelPlugin != null) {
                            for (Map.Entry<String, ReplacedElementInfo> entry : replacedElementsInModel.get(IdType.PORT).entrySet()) {
                                // Obtain id of element from port
                                Port port = compModelPlugin.getListOfPorts().get(entry.getKey());
                                Pair<String, IdType> result = selectCorrectRef(port);
                                // Remove element and port from parent
                                if (result.getValue().equals(IdType.META_ID)) {
                                    modelCopy.getElementByMetaId(result.getKey()).removeFromParent();
                                } else {
                                    modelCopy.getElementBySId(result.getKey()).removeFromParent();
                                }
                                port.removeFromParent();
                                // If element was replaced (not deleted) add the element that it's replaced by to replacedElements
                                if (entry.getValue() != null) {
                                    replacedElements.put(result.getKey(), entry.getValue());
                                }
                            }
                        }
                        break;
                    case ID:
                        for (Map.Entry<String, ReplacedElementInfo> entry : replacedElementsInModel.get(IdType.ID).entrySet()) {
                            SBase replacedSBase = modelCopy.getElementBySId(entry.getKey());
                            SBase ogReplacedSBase = ogModelCopy.getElementBySId(entry.getKey());
                            if (replacedSBase != null) {
                                // Species References need special treatment because id needs to stay the same if replaced
                                // and new Initial Assignments might be created
                                if((replacedSBase.getClass().equals(SpeciesReference.class)) && (entry.getValue() != null)) {
                                    ReplacedElementInfo repElInfo = entry.getValue().clone();
                                    Model replacedElementModel = this.visitedModels.get(repElInfo.modelId);
                                    String submodelPrefix = this.subModelPrefixes.get(repElInfo.replacedElementPath);
                                    SpeciesReference specRef = null;

                                    switch (repElInfo.idType) {
                                        case PORT:
                                            CompModelPlugin cmp = (CompModelPlugin) replacedElementModel.getPlugin(CompConstants.shortLabel);
                                            Port port = cmp.getPort(repElInfo.id);
                                            specRef = replacedElementModel.getModel().findSpeciesReference(port.getIdRef()).clone();
                                            break;
                                        case ID:
                                            specRef = replacedElementModel.getModel().findSpeciesReference(entry.getValue().id).clone();
                                            break;
                                        case META_ID:
                                            specRef = (SpeciesReference) replacedElementModel.getModel().getElementByMetaId(entry.getValue().id).clone();
                                            break;
                                        default:
                                            LOGGER.warning("Replacing SpeciesReference has neither a port or (meta) id!");
                                            break;
                                    }

                                    if(specRef != null) {
                                        // Update speciesReference with replacedElements value except ID and MetaID (as replacement would lead to duplicate IDs)
                                        SpeciesReference replacedSpecRef = (SpeciesReference) replacedSBase;
                                        replacedSpecRef.setSpecies(submodelPrefix + specRef.getSpecies());
                                        replacedSpecRef.setStoichiometry(specRef.getStoichiometry());
                                        replacedSpecRef.setConstant(specRef.getConstant());
                                        replacedSpecRef.setValue(specRef.getValue());
                                        replacedSpecRef.setAnnotation(specRef.getAnnotation());
                                        if(entry.getValue() != null) {
                                            checkInitialAssignmentsAndRules(modelCopy, ogReplacedSBase.getId(), entry.getValue(), replacedElements);
                                        }
                                    }
                                }
                                else {
                                    replacedSBase.removeFromParent();
                                    // If element was replaced (not deleted) add the element that it's replaced by to replacedElements
                                    if(entry.getValue() != null) {
                                        replacedElements.put(replacedSBase.getId(), entry.getValue());
                                    }
                                }
                            }
                            // Replacement could be UnitDefinition (can not be got by getElementBySID)
                            else if(modelCopy.getUnitDefinition(entry.getKey()) != null) {
                                UnitDefinition unitDef = modelCopy.getUnitDefinition(entry.getKey());
                                unitDef.removeFromParent();
                                if (entry.getValue() != null) {
                                    Map<String, String> hashMap = new HashMap<>();
                                    hashMap.put(entry.getKey(), entry.getValue().id);
                                    replacedElements.put(entry.getKey(), entry.getValue());
                                }
                            }
                            else if(ogReplacedSBase != null && entry.getValue() != null) {
                                checkInitialAssignmentsAndRules(modelCopy, ogReplacedSBase.getId(), entry.getValue(), replacedElements);
                            }
                        }
                        break;
                    case META_ID:
                        for (Map.Entry<String, ReplacedElementInfo> entry : replacedElementsInModel.get(IdType.META_ID).entrySet()) {
                            SBase replacedSBase = modelCopy.getElementByMetaId(entry.getKey());
                            SBase ogReplacedSBase = ogModelCopy.getElementByMetaId(entry.getKey());
                            if (replacedSBase != null) {
                                // Species References need special treatment because id needs to stay the same if replaced
                                // and new Initial Assignments might be created
                                if(replacedSBase.getClass().equals(SpeciesReference.class) && entry.getValue() != null) {
                                    Model replacedElementModel = this.visitedModels.get(entry.getValue().modelId);
                                    ReplacedElementInfo repElInfo = entry.getValue().clone();
                                    String submodelPrefix = this.subModelPrefixes.get(repElInfo.replacedElementPath);
                                    SpeciesReference specRef = null;

                                    switch (repElInfo.idType) {
                                        case PORT:
                                            CompModelPlugin cmp = (CompModelPlugin) replacedElementModel.getPlugin(CompConstants.shortLabel);
                                            Port port = cmp.getPort(repElInfo.id);
                                            specRef = replacedElementModel.getModel().findSpeciesReference(port.getIdRef()).clone();
                                            break;
                                        case ID:
                                            specRef = replacedElementModel.getModel().findSpeciesReference(entry.getValue().id).clone();
                                            break;
                                        case META_ID:
                                            specRef = (SpeciesReference) replacedElementModel.getModel().getElementByMetaId(entry.getValue().id).clone();
                                            break;
                                        default:
                                            LOGGER.warning("Replacing SpeciesReference has neither a port or (meta) id!");
                                            break;
                                    }


                                    if(specRef != null) {
                                        // Update speciesReference with replacedElements value except ID and MetaID (as replacement would lead to duplicate IDs)
                                        SpeciesReference replacedSpecRef = (SpeciesReference) replacedSBase;
                                        replacedSpecRef.setSpecies(submodelPrefix + specRef.getSpecies());
                                        replacedSpecRef.setStoichiometry(specRef.getStoichiometry());
                                        replacedSpecRef.setConstant(specRef.getConstant());
                                        replacedSpecRef.setValue(specRef.getValue());
                                        replacedSpecRef.setAnnotation(specRef.getAnnotation());
                                        if(entry.getValue() != null) {
                                            checkInitialAssignmentsAndRules(modelCopy, replacedSBase.getId(), entry.getValue(), replacedElements);
                                        }
                                    }
                                }
                                else {
                                    replacedSBase.removeFromParent();
                                    if (entry.getValue() != null) {
                                        replacedElements.put(replacedSBase.getId(), entry.getValue());
                                    }
                                }
                            }
                            // Replacement could be UnitDefinition (can not be got by getElementByMetaID)
                            else if(modelCopy.getUnitDefinition(entry.getKey()) != null) {
                                UnitDefinition unitDef = modelCopy.getUnitDefinition(entry.getKey());
                                unitDef.removeFromParent();
                                if (entry.getValue() != null) {
                                    Map<String, String> hashMap = new HashMap<>();
                                    hashMap.put(entry.getKey(), entry.getValue().id);
                                    replacedElements.put(entry.getKey(), entry.getValue());
                                }
                            }
                            else if(ogReplacedSBase != null && entry.getValue() != null) {
                                checkInitialAssignmentsAndRules(modelCopy, ogReplacedSBase.getId(), entry.getValue(), replacedElements);
                            }
                        }
                        break;
                    case UNIT_ID:
                        for (Map.Entry<String, ReplacedElementInfo> entry : replacedElementsInModel.get(IdType.UNIT_ID).entrySet()) {
                            UnitDefinition unitDef = modelCopy.getUnitDefinition(entry.getKey());
                            //modelCopy.getElementBySId(unitDef.getId()).removeFromParent();
                            unitDef.removeFromParent();
                            if (entry.getValue() != null) {
                                Map<String, String> hashMap = new HashMap<>();
                                hashMap.put(entry.getKey(), entry.getValue().id);
                                //newUnitDefMaps.add(hashMap);
                                replacedElements.put(entry.getKey(), entry.getValue());
                            }
                        }
                        break;
                }
            }
        }

        return replacedElements;
    }


    //////////////////////////////////////////
    /// Utility /////////////////////////////
    ////////////////////////////////////////

    /**
     * Adds newly created initial Assignment and Rules to model
     *
     * @param modelCopy
     */
    private void addNewElementsWithVariables(Model modelCopy) {
        if(this.newInitAssignHashMap.containsKey(curPath)) {
            modelCopy.getListOfInitialAssignments().addAll(this.newInitAssignHashMap.get(curPath));
        }
        if(this.newRulesHashMap.containsKey(curPath)) {
            modelCopy.getListOfRules().addAll(this.newRulesHashMap.get(curPath));
        }
    }

    /**
     * Create ASTNode for all conversion factors of current model by multiplying them
     *
     * @param modelCopy
     * @param convFactors list of conversion factors
     * @return ASTNode created by multiplying all conversion factors
     */
    private ASTNode createNewConvNode(Model modelCopy, List<String> convFactors) {


        if (convFactors.size() > 0) {
            StringBuilder convNodeName = new StringBuilder(convFactors.get(0));
            ASTNode initAssignConvNode = new ASTNode(convNodeName.toString());
            for (String convFactor : convFactors.subList(1, convFactors.size())) {
                initAssignConvNode.multiplyWith(new ASTNode(convFactor));
                convNodeName.append("_times_").append(convFactor);
            }

            if (!this.flattenedModel.containsParameter(convNodeName.toString()) && convFactors.size() > 1) {
                InitialAssignment initAssignConv = new InitialAssignment(this.flattenedModel.getLevel(), this.flattenedModel.getVersion());
                initAssignConv.setVariable(convNodeName.toString());
                initAssignConv.setMath(initAssignConvNode);
                this.flattenedModel.getListOfInitialAssignments().add(initAssignConv);

                Parameter parameterConv = new Parameter(this.flattenedModel.getLevel(), this.flattenedModel.getVersion());
                parameterConv.setId(convNodeName.toString());
                this.flattenedModel.getListOfParameters().add(parameterConv);
            }

            return new ASTNode(convNodeName.toString());
        }

        return null;
    }

    /**
     * If species reference is replaced initial assignment/rule for it have to be updated.
     * There are two possibilities:
     * 1. Initial Assignment/Rule of replacedElement also references another element -> create new InitialAssignment that's a copy of it
     * 2. Initial Assignment/Rule of replacedElement does not reference another element -> simply replace variable of assignment/rule
     *
     *
     * @param modelCopy
     * @param replacedID
     * @param newElInfo
     * @param replacedElements
     */
    private void checkInitialAssignmentsAndRules(Model modelCopy, String replacedID, ReplacedElementInfo newElInfo, Map<String, ReplacedElementInfo> replacedElements) {


        // Initial Assignments
        List<InitialAssignment> listOfInitialAssignments = modelCopy.getListOfInitialAssignments();
        List<InitialAssignment> newInitialAssignments = new ArrayList<>();

        for(InitialAssignment initAssign: listOfInitialAssignments) {
            if(replacedID.equals(initAssign.getVariable())) {
                if(modelCopy.getElementBySId(replacedID) == null) {
                    replacedElements.put(replacedID, newElInfo);
                }
                else {
                    InitialAssignment initAssignNew = initAssign.clone();
                    initAssignNew.setVariable(newElInfo.id);
                    newInitialAssignments.add(initAssignNew);
                    break;
                }
            }
        }
        if(!newInitialAssignments.isEmpty()) {
            if (this.newInitAssignHashMap.containsKey(newElInfo.replacedElementPath)) {
                this.newInitAssignHashMap.get(newElInfo.replacedElementPath).addAll(newInitialAssignments);
            } else {
                this.newInitAssignHashMap.put(newElInfo.replacedElementPath, newInitialAssignments);
            }
        }

        // Rules
        List<Rule> listOfRules = modelCopy.getListOfRules();
        List<Rule> newRules = new ArrayList<>();

        for(Rule rule: listOfRules) {
            if(rule.getClass().equals(AssignmentRule.class)) {
                AssignmentRule aRule = (AssignmentRule) rule;
                if(replacedID.equals(aRule.getVariable())) {
                    if(modelCopy.getElementBySId(replacedID) == null) {
                        replacedElements.put(replacedID, newElInfo);
                    }
                    else {
                        AssignmentRule aRuleNew = aRule.clone();
                        aRuleNew.setVariable(newElInfo.id);
                        newRules.add(aRuleNew);
                    }
                }
            }
            else if(rule.getClass().equals(RateRule.class)) {
                RateRule rRule = (RateRule) rule;
                if(replacedID.equals(rRule.getVariable())) {
                    if(modelCopy.getElementBySId(replacedID) == null) {
                        replacedElements.put(replacedID, newElInfo);
                    }
                    else {
                        RateRule rRuleNew = rRule.clone();
                        rRuleNew.setVariable(newElInfo.id);
                        newRules.add(rRuleNew);
                    }
                }
            }
        }
        if(!newRules.isEmpty()) {
            if (this.newRulesHashMap.containsKey(newElInfo.replacedElementPath)) {
                this.newRulesHashMap.get(newElInfo.replacedElementPath).addAll(newRules);
            } else {
                this.newRulesHashMap.put(newElInfo.replacedElementPath, newRules);
            }
        }
    }

    /**
     *
     * Given sBase reference returns its id and idType as a pair
     *
     * @param sBaseRef
     * @return pair of id and idType
     */
    private Pair<String, IdType> selectCorrectRef(SBaseRef sBaseRef) {

        String elementRef = null;
        IdType elementType = null;

        if (sBaseRef.isSetIdRef()) {
            elementRef = sBaseRef.getIdRef();
            elementType = IdType.ID;
        } else if (sBaseRef.isSetMetaIdRef()) {
            elementRef = sBaseRef.getMetaIdRef();
            elementType = IdType.META_ID;
        } else if (sBaseRef.isSetPortRef()) {
            elementRef = sBaseRef.getPortRef();
            elementType = IdType.PORT;
        } else if (sBaseRef.isSetUnitRef()) {
            elementRef = sBaseRef.getUnitRef();
            elementType = IdType.UNIT_ID;
        }

        return new Pair<String, IdType>(
                elementRef,
                elementType
        );
    }


    /**
     *
     * Recursively iterate through nested sBaseRef until last sBaseRef and return it
     * Also extends submodelPath accordingly
     *
     * @param sBaseRef
     * @param submodelPath
     * @return
     */
    private SBaseRef determineSubmodelPath(SBaseRef sBaseRef, List<String> submodelPath) {


        if (sBaseRef.getSBaseRef() != null) {
            if (!sBaseRef.getIdRef().isEmpty()) {
                submodelPath.add(sBaseRef.getIdRef());
            } else if (!sBaseRef.getMetaIdRef().isEmpty()) {
                submodelPath.add(sBaseRef.getMetaIdRef());
            }
            return determineSubmodelPath(sBaseRef.getSBaseRef(), submodelPath);
        } else {
            return sBaseRef;
        }

    }


    /**
     *
     * Get all lists including the list in each reaction
     *
     * @param model
     * @return
     */
    private List<List<? extends AbstractSBase>> getListOfListsOfSBases(Model model) {

        List<List<? extends AbstractSBase>> listOfListsOfSBases = new ArrayList<>();

        listOfListsOfSBases.addAll(Arrays.asList(
                model.getListOfCompartments(),
                model.getListOfParameters(),
                model.getListOfEvents(),
                model.getListOfSpecies(),
                model.getListOfFunctionDefinitions(),
                model.getListOfRules(),
                model.getListOfConstraints(),
                model.getListOfUnitDefinitions(),
                model.getListOfReactions(),
                model.getListOfInitialAssignments()
        ));

        for (Reaction reaction : model.getListOfReactions()) {
            listOfListsOfSBases.add(reaction.getListOfProducts());
            listOfListsOfSBases.add(reaction.getListOfReactants());
        }

        return listOfListsOfSBases;
    }

    /**
     * Get all lists excluding the list in each reaction
     *
     * @param model
     * @return
     */
    private List<? extends ListOf<? extends AbstractSBase>> getAllListsOfModel(Model model) {
        return Arrays.asList(
                model.getListOfCompartments(),
                model.getListOfSpecies(),
                model.getListOfFunctionDefinitions(),
                model.getListOfRules(),
                model.getListOfEvents(),
                model.getListOfUnitDefinitions(),
                model.getListOfReactions(),
                model.getListOfConstraints(),
                model.getListOfParameters(),
                model.getListOfInitialAssignments()
        );
    }


    /**
     *
     * Apply the conversion factor to a node
     *
     * @param node
     * @param parent
     * @param replacedElementsHashMap
     * @param subModelPrefix
     * @param timeConvFactorNode
     * @param i
     */
    private void applyConvFactorToNode(ASTNode node, ASTNode parent,
                                       Map<String, ReplacedElementInfo> replacedElementsHashMap,
                                       String subModelPrefix, ASTNode timeConvFactorNode, int i) {

        if (node.getType() == ASTNode.Type.NAME) {
            if (replacedElementsHashMap.containsKey(node.toString())) {
                ASTNode newNode = new ASTNode(replacedElementsHashMap.get(node.toString()).id);
                // Apply conversion factor to element
                String convFactor = replacedElementsHashMap.get(node.toString()).conversionFactor;
                if (convFactor != null) {
                    ASTNode convNode = new ASTNode(convFactor);
                    newNode.divideBy(convNode);
                }
                parent.replaceChild(i, newNode);
            } else {
                parent.replaceChild(i, new ASTNode(subModelPrefix + node));
            }
        } else if (node.getType() == ASTNode.Type.NAME_TIME && timeConvFactorNode != null) {
            node.divideBy(timeConvFactorNode);
        } else if (node.getType() == ASTNode.Type.FUNCTION_DELAY && timeConvFactorNode != null) {
            node.getChild(1).multiplyWith(timeConvFactorNode);
        }
    }


    /**
     * All remaining elements are placed in a single Model object
     * The original Model, ModelDefinition, and ExternalModelDefinition objects are all deleted
     *
     * @param previousModel
     * @param currentModel
     * @return mergedModel
     */
    private Model mergeModels(Model previousModel, Model currentModel) {

        Model mergedModel = new Model();

        // Merging of SBML models should be done in the order
        // Compartments -> Species -> Function Definitions -> Rules -> Events -> Units -> Reactions -> Parameters
        // If done in this order, potential conflicts are resolved incrementally along the way.

        // Set level/version from whichever model is available
        if (previousModel != null) {
            mergedModel.setLevel(previousModel.getLevel());
            mergedModel.setVersion(previousModel.getVersion());
        } else if (currentModel != null) {
            mergedModel.setLevel(currentModel.getLevel());
            mergedModel.setVersion(currentModel.getVersion());
        }

        // IMPORTANT: merge the current (parent) model first,
        // then the previously flattened submodels, so that
        // original objects (like param1) appear before
        // flattened submodel objects (like submod1__subparam2).
        if (currentModel != null) {
            for (ListOf<? extends AbstractSBase> modelList : getAllListsOfModel(currentModel)) {
                if (!modelList.isEmpty()) {
                    mergeListsOfModels(modelList, currentModel, mergedModel);
                }
            }
        }

        if (previousModel != null) {
            for (ListOf<? extends AbstractSBase> modelList : getAllListsOfModel(previousModel)) {
                if (!modelList.isEmpty()) {
                    mergeListsOfModels(modelList, previousModel, mergedModel);
                }
            }
        }

        // TODO: delete original model, ModelDefinition, and ExternalModelDefinition objects
        // QUESTION: can a model def be instantiated more than one time?
        return mergedModel;
    }


    private void mergeListsOfModels(ListOf listOfObjects, Model sourceModel, Model targetModel) {

        // TODO: generify with listOf SBase ?


        if (listOfObjects.getSBaseListType() == ListOf.Type.listOfReactions) {


/* for(Reaction reaction: targetModel.getListOfReactions()) {
                for (SpeciesReference specRef : reaction.getListOfProducts()) {
                    System.out.println("Target SR: " + targetModel.getElementBySId("z_stoich"));
                }
            }*/


            ListOf<Reaction> reactionListOf = sourceModel.getListOfReactions().clone();
            sourceModel.getListOfReactions().removeFromParent();

            for (Reaction reaction : reactionListOf) {
                targetModel.addReaction(reaction.clone());
            }
        }

        if (listOfObjects.getSBaseListType() == ListOf.Type.listOfCompartments) {

            ListOf<Compartment> compartmentListOf = sourceModel.getListOfCompartments().clone();
            sourceModel.getListOfCompartments().removeFromParent();

            for (Compartment compartment : compartmentListOf) {
                targetModel.addCompartment(compartment.clone());
            }
        }

        if (listOfObjects.getSBaseListType() == ListOf.Type.listOfConstraints) {

            ListOf<Constraint> constraintListOf = sourceModel.getListOfConstraints().clone();
            sourceModel.getListOfConstraints().removeFromParent();

            for (Constraint constraint : constraintListOf) {
                targetModel.addConstraint(constraint.clone());
            }
        }


        if (listOfObjects.getSBaseListType() == ListOf.Type.listOfSpecies) {

            ListOf<Species> speciesListOf = sourceModel.getListOfSpecies().clone();
            sourceModel.getListOfSpecies().removeFromParent();

            for (Species species : speciesListOf) {
                targetModel.addSpecies(species.clone());
            }
        }

        if (listOfObjects.getSBaseListType() == ListOf.Type.listOfEvents) {

            ListOf<Event> eventListOf = sourceModel.getListOfEvents().clone();
            sourceModel.getListOfEvents().removeFromParent();

            for (Event event : eventListOf) {
                targetModel.addEvent(event.clone());
            }
        }

        if (listOfObjects.getSBaseListType() == ListOf.Type.listOfFunctionDefinitions) {

            ListOf<FunctionDefinition> functionalDefinitionsListOf = sourceModel.getListOfFunctionDefinitions().clone();
            sourceModel.getListOfFunctionDefinitions().removeFromParent();

            for (FunctionDefinition functionalDefinition : functionalDefinitionsListOf) {
                targetModel.addFunctionDefinition(functionalDefinition.clone());
            }
        }

        if (listOfObjects.getSBaseListType() == ListOf.Type.listOfInitialAssignments) {

            ListOf<InitialAssignment> initialAssignmentListOf = sourceModel.getListOfInitialAssignments().clone();
            sourceModel.getListOfInitialAssignments().removeFromParent();

            for (InitialAssignment initialAssignment : initialAssignmentListOf) {
                targetModel.addInitialAssignment(initialAssignment.clone());
            }
        }

        if (listOfObjects.getSBaseListType() == ListOf.Type.listOfParameters) {

            ListOf<Parameter> parameterListOf = sourceModel.getListOfParameters().clone();
            sourceModel.getListOfParameters().removeFromParent();

            for (Parameter parameter : parameterListOf) {

                targetModel.addParameter(parameter.clone());
            }
        }

        if (listOfObjects.getSBaseListType() == ListOf.Type.listOfRules) {

            ListOf<Rule> ruleListOf = sourceModel.getListOfRules().clone();
            sourceModel.getListOfRules().removeFromParent();

            for (Rule rule : ruleListOf) {
                targetModel.addRule(rule.clone());
            }
        }

        if (listOfObjects.getSBaseListType() == ListOf.Type.listOfUnitDefinitions) {

            ListOf<UnitDefinition> unitDefinitionListOf = sourceModel.getListOfUnitDefinitions().clone();
            sourceModel.getListOfUnitDefinitions().removeFromParent();

            for (UnitDefinition unit : unitDefinitionListOf) {
                targetModel.addUnitDefinition(unit.clone());
            }
        }

        if (listOfObjects.getSBaseListType() == ListOf.Type.listOfLocalParameters) {

            sourceModel.getLocalParameterCount();

        }


        //TODO:
        // no longer supported? there are no get methods for this
//        ListOf.Type.listOfCompartmentTypes
//        ListOf.Type.listOfEventAssignments
//        ListOf.Type.listOfLocalParameters: there is no getter method :(
//        ListOf.Type.listOfModifiers
//        ListOf.Type.listOfSpeciesTypes
//        ListOf.Type.listOfUnits

        // maybe they are already in listOfReactions?
//        ListOf.Type.listOfProducts
//        ListOf.Type.listOfReactants
    }


    private void addPrefixesToSBaseList(Model modelOfSubmodel, ListOf listOfSBase, String subModelPrefix) {

        ListOf<SBase> list = (ListOf<SBase>) listOfSBase;

        for (SBase sBase : list) {

            if (!sBase.getId().equals("")) {
                sBase.setId(subModelPrefix + sBase.getId());
            }

            if (!sBase.getMetaId().equals("")) {
                sBase.setMetaId(subModelPrefix + sBase.getMetaId());
            }
        }

    }

    private Model addPrefixesToModelObjects(Model modelOfSubmodel, String subModelPrefix) {

        addPrefixesToSBaseList(modelOfSubmodel, modelOfSubmodel.getListOfReactions(), subModelPrefix);
        addPrefixesToSBaseList(modelOfSubmodel, modelOfSubmodel.getListOfCompartments(), subModelPrefix);
        addPrefixesToSBaseList(modelOfSubmodel, modelOfSubmodel.getListOfConstraints(), subModelPrefix);
        addPrefixesToSBaseList(modelOfSubmodel, modelOfSubmodel.getListOfEvents(), subModelPrefix);

        addPrefixesToSBaseList(modelOfSubmodel, modelOfSubmodel.getListOfFunctionDefinitions(), subModelPrefix);
        addPrefixesToSBaseList(modelOfSubmodel, modelOfSubmodel.getListOfParameters(), subModelPrefix);
        addPrefixesToSBaseList(modelOfSubmodel, modelOfSubmodel.getListOfRules(), subModelPrefix);
        addPrefixesToSBaseList(modelOfSubmodel, modelOfSubmodel.getListOfSpecies(), subModelPrefix);
        addPrefixesToSBaseList(modelOfSubmodel, modelOfSubmodel.getListOfUnitDefinitions(), subModelPrefix);

        for(Reaction reaction: modelOfSubmodel.getListOfReactions()) {
            //addPrefixesToSBaseList(modelOfSubmodel, reaction.getListOfProducts(), subModelPrefix);
            //addPrefixesToSBaseList(modelOfSubmodel, reaction.getListOfReactants(), subModelPrefix);
            //addPrefixesToSBaseList(modelOfSubmodel, reaction.getListOfModifiers(), subModelPrefix);
        }

        return modelOfSubmodel;
    }


    /**
     * Collects any {@link ExternalModelDefinition}s that might be contained in
     * the given {@link SBMLDocument} and transfers them into local
     * {@link ModelDefinition}s (recursively, if the external models themselves
     * include external models; in that case, renaming may occur).
     * <br>
     * The given {@link SBMLDocument} need have its locationURI set!
     * <br>
     * Opaque URIs (URNs) will not be dealt with in any defined way, resolve them
     * first (make sure all relevant externalModelDefinitions' source-attributes
     * are URLs or relative paths)
     *
     * @param document an {@link SBMLDocument}, which might, but need not, contain
     * {@link ExternalModelDefinition}s to be transferred into its local
     * {@link ModelDefinition}s. The locationURI of the given document need
     * be set ({@link SBMLDocument#isSetLocationURI})!
     * @return a new {@link SBMLDocument} without {@link
     * ExternalModelDefinition}s, but containing the same information as
     * the given one
     * @throws Exception if given document's locationURI is not set. Set it with
     * {@link SBMLDocument#setLocationURI}
     */
    public static SBMLDocument internaliseExternalModelDefinitions(
            SBMLDocument document) throws Exception {

        if (!document.isSetLocationURI()) {
            LOGGER.warning("Location URI is not set: " + document);
            throw new Exception(
                    "document's locationURI need be set. But it was not.");
        }
        SBMLDocument result = document.clone(); // no side-effects intended
        ArrayList<String> usedIds = new ArrayList<String>();
        if (result.isSetModel()) {
            usedIds.add(result.getModel().getId());
        }

        CompSBMLDocumentPlugin compSBMLDocumentPlugin =
                (CompSBMLDocumentPlugin) result.getExtension(CompConstants.shortLabel);

        // There is nothing to retrieve:
        if (compSBMLDocumentPlugin == null || !compSBMLDocumentPlugin.isSetListOfExternalModelDefinitions()) {
            return result;
        } else {
            /** For name-collision-avoidance */
            for (ExternalModelDefinition emd : compSBMLDocumentPlugin.getListOfExternalModelDefinitions()) {
                usedIds.add(emd.getId());
            }

            for (ExternalModelDefinition emd : compSBMLDocumentPlugin.getListOfExternalModelDefinitions()) {
                // general note: Be careful when using clone/cloning-constructors, they
                // do not preserve parent-child-relations
                Model referenced = emd.getReferencedModel();
                SBMLDocument referencedDocument = referenced.getSBMLDocument();
                SBMLDocument flattened = internaliseExternalModelDefinitions(referencedDocument);
                // Guarantee: flattened does not contain any externalModelDefinitions, only local MDs
                // (and main model)
                // use this, and migrate the MDs into the current compSBMLDocumentPlugin
                StringBuilder prefixBuilder = new StringBuilder(emd.getModelRef());
                /** For name-collision-avoidance */
                boolean contained = false;
                do {
                    contained = false;
                    prefixBuilder.append("_");
                    for (String id : usedIds) {
                        contained |= id.startsWith(prefixBuilder.toString());
                        if (contained) {
                            break;
                        }
                    }
                } while (contained);
                String newPrefix = prefixBuilder.toString();

                CompSBMLDocumentPlugin referencedDocumentPlugin =
                        (CompSBMLDocumentPlugin) flattened.getExtension(
                                CompConstants.shortLabel);

                ListOf<ModelDefinition> workingList;
                if (referencedDocumentPlugin == null) {
                    // This may happen, if the main model of a non-comp-file is referenced
                    workingList = new ListOf<ModelDefinition>();
                    workingList.setLevel(referenced.getLevel());
                    workingList.setVersion(referenced.getVersion());
                } else {
                    workingList = referencedDocumentPlugin.getListOfModelDefinitions().clone();
                }

                // Check whether the main model is needed; Do not internalise it, if not necessary
                boolean isMainReferenced = flattened.getModel().getId().equals(emd.getModelRef());
                for (ModelDefinition md : workingList) {
                    if (isMainReferenced) {
                        break;
                    }
                    CompModelPlugin cmp = (CompModelPlugin) md.getExtension(CompConstants.shortLabel);
                    if (cmp != null) {
                        for (Submodel sm : cmp.getListOfSubmodels()) {
                            isMainReferenced |= flattened.getModel().getId().equals(sm.getModelRef());
                        }
                    }
                }

                if (isMainReferenced) {
                    ModelDefinition localisedMain = new ModelDefinition(flattened.getModel());
                    workingList.add(0, localisedMain);
                }

                for (ModelDefinition md : workingList) {
                    ModelDefinition internalised = new ModelDefinition(md);
                    // i.e. current one is the one directly referenced => take referent's place
                    if (md.getId().equals(referenced.getId())) {
                        internalised.setId(emd.getId());
                    } else {
                        internalised.setId(newPrefix + internalised.getId());
                    }

                    CompModelPlugin notYetInternalisedModelPlugin =
                            (CompModelPlugin) internalised.getExtension(CompConstants.shortLabel);
                    if (notYetInternalisedModelPlugin != null && notYetInternalisedModelPlugin.isSetListOfSubmodels()) {
                        for (Submodel sm : notYetInternalisedModelPlugin.getListOfSubmodels()) {
                            sm.setModelRef(newPrefix + sm.getModelRef());
                        }
                    }

                    compSBMLDocumentPlugin.addModelDefinition(internalised);
                }
            }
            compSBMLDocumentPlugin.unsetListOfExternalModelDefinitions();
            return result;
        }
    }

    /**
     * Type of identifier used in replaced/deleted element references.
     */
    private static enum IdType {
        ID,
        META_ID,
        PORT,
        UNIT_ID;
    }

    /**
     * Information about a replaced element: where it lives, how it is referenced,
     * and any conversion factor to apply.
     */
    private static class ReplacedElementInfo implements Cloneable {
        String id;                        // id/metaId/portId/unitId of the replacing element
        String modelId;                   // id of the model that contains the replacing element
        IdType idType;                    // how to interpret 'id'
        String conversionFactor;          // optional parameter id for conversion factor
        List<String> replacedElementPath; // path of submodel ids from root to the replaced element

        ReplacedElementInfo(String id,
                            String modelId,
                            IdType idType,
                            String conversionFactor,
                            List<String> replacedElementPath) {
            this.id = id;
            this.modelId = modelId;
            this.idType = idType;
            this.conversionFactor = conversionFactor;
            this.replacedElementPath = (replacedElementPath == null)
                    ? null
                    : new ArrayList<String>(replacedElementPath);
        }

        @Override
        public ReplacedElementInfo clone() {
            return new ReplacedElementInfo(
                    this.id,
                    this.modelId,
                    this.idType,
                    this.conversionFactor,
                    this.replacedElementPath == null
                            ? null
                            : new ArrayList<String>(this.replacedElementPath));
        }
    }
}