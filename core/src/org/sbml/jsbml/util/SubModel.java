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
package org.sbml.jsbml.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.CompartmentType;
import org.sbml.jsbml.Event;
import org.sbml.jsbml.EventAssignment;
import org.sbml.jsbml.ExplicitRule;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.LocalParameter;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.Rule;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SimpleSpeciesReference;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesType;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.util.filters.SpeciesReferenceFilter;

/**
 * Generates a sub-{@link Model} containing the elements passed as argument and all
 * the necessary dependencies.
 * 
 * <p> You do not need to fill all of the parameter arrays. Here is how the sub-{@link Model} generation works:
 * <ul>
 * <li>If you give some {@link Compartment} ids, all the species inside these {@link Compartment} are included.
 * <li>If you give some {@link Species} ids (and all added {@link Species} from the {@link Compartment}s), all the {@link Reaction}s where one of these {@link Species} is present are included
 * <li>All the {@link Species} involved in the {@link Reaction}s given as parameter or added automatically from the given {@link Species} are included.
 * <li>All {@link Compartment}s where one of the included species is present is included.
 * <li>All needed {@link UnitDefinition}s, {@link SpeciesType}s and {@link CompartmentType}s are included.
 * <li>All the {@link Rule}s and events passed as parameter along the one needed are included.
 * <li>All the global {@link Parameter}s are added automatically at the moment, without checking if there are used or not.
 * <li>All the {@link FunctionDefinition}s used in the included MathML are added automatically.
 * </ul>
 * 
 * @author Nicolas Rodriguez
 * @author chenli
 * @since 0.8
 */
@SuppressWarnings("deprecation")
public class SubModel {

  /**
   * Generates a sub-model containing the elements passed as argument and all
   * the necessary dependencies.
   * 
   * @param model
   * @param compartmentsIds
   * @param speciesIds
   * @param reactsIds
   * @return
   */
  public static SBMLDocument generateSubModel(
    Model model,
    String[] compartmentsIds,
    String[] speciesIds,
    String[] reactsIds) {
    return generateSubModel(model, compartmentsIds, speciesIds, reactsIds, null, null, true, true, true, false, false);
  }

  /**
   * Generates a sub-model containing the elements passed as argument and all
   * the necessary dependencies.
   * <p>
   * You do not need to fill all of the parameter arrays. Here is how the sub-{@link Model} generation works:
   * <ul>
   * <li>If you give some {@link Compartment} ids, all the species inside these {@link Compartment} are included.
   * <li>If you give some {@link Species} ids (and all added {@link Species} from the {@link Compartment}s), all the {@link Reaction}s where one of these {@link Species} is present are included
   * <li>All the {@link Species} involved in the {@link Reaction}s given as parameter or added automatically from the given {@link Species} are included.
   * <li>All {@link Compartment}s where one of the included species is present is included.
   * <li>All needed {@link UnitDefinition}s, {@link SpeciesType}s and {@link CompartmentType}s are included.
   * <li>All the {@link Rule}s and events passed as parameter along the one needed are included.
   * <li>All the global {@link Parameter}s are added automatically at the moment, without checking if there are used or not.
   * <li>All the {@link FunctionDefinition}s used in the included MathML are added automatically.
   * </ul>
   *
   * <p>
   * @param model the SBML model where we want to extract a sub-model.
   * @param compartmentsIds the list of compartment to keep
   * @param speciesIds the list of species to keep
   * @param reactsIds the list of reactions to keep
   * @param rulesIds the list of rules to keep
   * @param eventsIds the list of events to keep
   * @return a sub-model containing the elements passed as argument and all
   * the necessary dependencies.
   */
  public static SBMLDocument generateSubModel(
    Model model,
    String[] compartmentsIds,
    String[] speciesIds,
    String[] reactsIds,
    String[] rulesIds,
    String[] eventsIds)
  {
    return generateSubModel(model, compartmentsIds, speciesIds, reactsIds, rulesIds, eventsIds, true, true, true, true, true);
  }

  /**
   * Creates a sub-model with the option to ignore certain items.
   * 
   * @param model
   * @param compartmentsIds
   * @param speciesIds
   * @param reactsIds
   * @param rulesIds
   * @param eventsIds
   * @param inclComp
   * @param inclSpecies
   * @param inclReact
   * @param inclRules
   * @param inclEvents
   * @return
   */
  private static SBMLDocument generateSubModel(
    Model model,
    String[] compartmentsIds,
    String[] speciesIds,
    String[] reactsIds,
    String[] rulesIds,
    String[] eventsIds,
    boolean inclComp, boolean inclSpecies, boolean inclReact, boolean inclRules, boolean inclEvents) {

    // TODO: need to be sure that all needed elements have a metaid, likes rules, events, ...

    // TODO: would probably be easier to use only ArrayList, instead of arrays. Not sure if it would be better to store the Objects instead of the ids.

    // TODO: need to check for InitialAssignment and Constraint

    //
    // initial
    //

    // submodel id
    Calendar current = Calendar.getInstance();
    String subModelId = "SUBMODEL" + Long.toString(current.getTimeInMillis());

    int modelLevel = model.getLevel();
    int modelVersion = model.getVersion();

    // SBMLDocument object for submodel
    SBMLDocument subModelSbmlDocument = new SBMLDocument(modelLevel, modelVersion);
    SBMLDocument originalDocument = model.getSBMLDocument();

    if (originalDocument != null) {

      // Copying namespaces
      if (originalDocument.getSBMLDocumentNamespaces() != null && originalDocument.getSBMLDocumentNamespaces().size() > 0)
      {
        for (String prefix : originalDocument.getSBMLDocumentNamespaces().keySet()) {
          String namespaceURI = originalDocument.getSBMLDocumentNamespaces().get(prefix);

          subModelSbmlDocument.addNamespace(prefix, null, namespaceURI);
        }
      }

      // Copying attributes
      if (originalDocument.getSBMLDocumentAttributes() != null && originalDocument.getSBMLDocumentAttributes().size() > 0)
      {
        for (String attributeName : originalDocument.getSBMLDocumentAttributes().keySet()) {
          String attributeValue = originalDocument.getSBMLDocumentAttributes().get(attributeName);

          subModelSbmlDocument.readAttribute(attributeName, null, attributeValue);
        }
      }
    }

    // creating submodel object
    Model subModel = subModelSbmlDocument.createModel(subModelId);
    subModel.setMetaId(subModelId);

    if (!inclComp) {
      compartmentsIds = null;
    }
    if (inclComp && inclSpecies) {
      speciesIds = getRelatedSpecies(model, null, compartmentsIds, speciesIds);
      reactsIds = getRelatedReactions(model, reactsIds, speciesIds);
    } else {
      speciesIds = reactsIds = null;
    }

    Set<String> relatedFunctionsIdSet = new HashSet<String>();
    Set<String> allFunctionsIdSet = new HashSet<String>();
    Map<String, UnitDefinition> unitsMap = new HashMap<String, UnitDefinition>();

    for (FunctionDefinition functionDefinition : model.getListOfFunctionDefinitions()) {
      allFunctionsIdSet.add(functionDefinition.getId());
    }

    // TODO: the added rules or events can contain some species, compartment or speciesReferences (for L3) that could
    // not be present in the list of included elements !!!

    //
    // Reactions.
    //

    if (reactsIds != null) {
      for (int i = 0; i < reactsIds.length; i++) {
        Reaction relatedReaction = model.getReaction(reactsIds[i]);

        subModel.addReaction(relatedReaction.clone());

        if (relatedReaction.getKineticLaw() != null) {
          // TODO: Parameters!
          getRelatedFunctionsId(relatedFunctionsIdSet, allFunctionsIdSet, relatedReaction.getKineticLaw().getMath());
          processUnitsMap(unitsMap, model, relatedReaction.getKineticLaw());
        }
      }
    }


    //
    // compartments and compartments type
    //

    speciesIds = getRelatedSpecies(model, reactsIds, compartmentsIds, speciesIds);
    compartmentsIds = getRelatedCompartments(model, compartmentsIds, speciesIds);

    if (compartmentsIds != null) {
      for (int i = 0; i < compartmentsIds.length; i++) {
        Compartment relatedCompartment = model.getCompartment(compartmentsIds[i]);

        subModel.addCompartment(relatedCompartment.clone());
        processUnitsMap(unitsMap, model, relatedCompartment.getUnits());

        // check compartment type
        if ((relatedCompartment.getCompartmentTypeInstance() != null)
            && (subModel.getCompartmentType(relatedCompartment.getCompartmentType()) == null)) {
          subModel.addCompartmentType(relatedCompartment.getCompartmentTypeInstance().clone());
        }
      }
    }


    //
    // species and species type
    //

    if (speciesIds != null) {
      for (int i = 0; i < speciesIds.length; i++) {
        Species relatedSpecies = model.getSpecies(speciesIds[i]);
        
        if (subModel.getSBaseById(relatedSpecies.getId()) != null) {
          // we have already added this species
          continue;
        }
        subModel.addSpecies(relatedSpecies.clone());
        processUnitsMap(unitsMap, model, relatedSpecies.getSubstanceUnits());

        // check species type
        if ((relatedSpecies.getSpeciesTypeInstance() != null)
            && (subModel.getSpeciesType(relatedSpecies.getSpeciesType()) == null)) {
          subModel.addSpeciesType(relatedSpecies.getSpeciesTypeInstance().clone());
        }
      }
    }


    //
    // rules
    //

    if (inclRules) {
      rulesIds = getRelatedRules(model, rulesIds, compartmentsIds, speciesIds);

      if ((rulesIds != null) && (rulesIds.length > 0)) {
        for (String ruleId : rulesIds) {
          for (Rule modelRule : model.getListOfRules()) {
            if (modelRule.getMetaId().equals(ruleId)) {
              subModel.addRule(modelRule.clone());
              if (modelRule.getMath() != null) {
                getRelatedFunctionsId(relatedFunctionsIdSet, allFunctionsIdSet, modelRule.getMath());
                // TODO: analyze the math to add missing Species, ...
              }
            }
          }
        }
      }
    }

    //
    // events
    //

    if (inclEvents) {
      eventsIds = getRelatedEvents(model, eventsIds, compartmentsIds, speciesIds);

      if ((eventsIds != null) && (eventsIds.length > 0)) {
        for (int i = 0; i < eventsIds.length; i++) {
          for (Event modelEvent : model.getListOfEvents()) {
            if (modelEvent.getMetaId().equals(eventsIds[i])) {
              subModel.addEvent(modelEvent.clone());
              if (modelEvent.getTrigger() != null) {
                getRelatedFunctionsId(relatedFunctionsIdSet, allFunctionsIdSet, modelEvent.getTrigger().getMath());
              }
              if (modelEvent.getDelay() != null) {
                getRelatedFunctionsId(relatedFunctionsIdSet, allFunctionsIdSet, modelEvent.getDelay().getMath());
              }
            }
          }
        }
      }
    }


    //
    // parameters.
    //

    // TODO: try not to add all the parameters !!
    // When we do getRelatedFunctions, we could search for the related species and parameter !!

    for (Parameter parameter : model.getListOfParameters()) {
      subModel.addParameter(parameter.clone());
      processUnitsMap(unitsMap, model, parameter.getUnits());
    }


    //
    // units
    //

    for (UnitDefinition unitDefinition : unitsMap.values()) {
      subModel.addUnitDefinition(unitDefinition.clone());
    }


    //
    // FunctionDefinition
    //
    for (String functionDefinitionId : relatedFunctionsIdSet) {
      FunctionDefinition func = model.getFunctionDefinition(functionDefinitionId);
      getRelatedFunctionsId(relatedFunctionsIdSet, allFunctionsIdSet, func.getMath());
    }
    for (String functionDefinitionId : relatedFunctionsIdSet) {
      subModel.addFunctionDefinition(model.getFunctionDefinition(functionDefinitionId).clone());
    }

    return subModelSbmlDocument;
  }

  /**
   * 
   * @param unitsMap
   * @param model the original model from which a sub-model is to be created.
   * @param elementUnits
   */
  private static void processUnitsMap(Map<String, UnitDefinition> unitsMap, Model model, String elementUnits) {

    Logger debugLogger = Logger.getLogger(SubModel.class);
    debugLogger.debug("processUnitsMap called with " + elementUnits);

    UnitDefinition unit = model.getUnitDefinition(elementUnits);

    debugLogger.debug("processUnitsMap: unit = " + elementUnits);

    if (unit != null && !unitsMap.containsKey(unit.getId())) {
      unitsMap.put(unit.getId(), unit);
    }
  }

  /**
   * 
   * @param unitsMap
   * @param model the original model from which a sub-model is to be created.
   * @param kineticLaw
   */
  private static void processUnitsMap(Map<String, UnitDefinition> unitsMap, Model model, KineticLaw kineticLaw) {

    if (kineticLaw.getLocalParameterCount() > 0) {
      for (LocalParameter parameter : kineticLaw.getListOfLocalParameters()) {
        processUnitsMap(unitsMap, model, parameter.getUnits());
      }
    }

    // TODO: the mathML can have some units as well, on the cn element in SBML L3V1
  }
  //
  //
  //
  //
  //

  /**
   * 
   * @param speciesReferences
   * @param species
   * @return
   */
  private static boolean contains(ListOf<? extends SimpleSpeciesReference> speciesReferences,	Species species) {
    return speciesReferences.firstHit(new SpeciesReferenceFilter(species)) != null;
  }

  /**
   * 
   * @param model the original model from which a sub-model is to be created.
   * @param compartmentsIds
   * @param speciesIds
   * @return
   */
  public static String[] getRelatedCompartments(
    Model model,
    String[] compartmentsIds,
    String[] speciesIds) {

    Logger debugLogger = Logger.getLogger(SubModel.class);
    debugLogger.debug("getRelatedCompartments");

    // get all related compartments
    List<String> relatedCompartmentsList = new ArrayList<String>();

    if ((compartmentsIds != null) || (speciesIds != null)) {
      for (Compartment compartment : model.getListOfCompartments()) {

        String compartmentId = compartment.getId();

        boolean thisCompartmentSelected = false;

        if (compartmentsIds != null) {
          for (int j = 0; (j < compartmentsIds.length) && !thisCompartmentSelected; j++) {
            if (compartmentId.equals(compartmentsIds[j])) {
              thisCompartmentSelected = true;
            }
          }
        }

        if (!thisCompartmentSelected && (speciesIds != null)) {
          for (int j = 0; (j < speciesIds.length) && !thisCompartmentSelected; j++) {
            Species species = model.getSpecies(speciesIds[j]);
            thisCompartmentSelected = species.getCompartment().equals(compartmentId);
          }
        }

        if (thisCompartmentSelected) {
          relatedCompartmentsList.add(compartmentId);
        }
      }
    }
    // convert to array
    if ((relatedCompartmentsList == null) || relatedCompartmentsList.isEmpty()) {
      compartmentsIds = null;

      debugLogger.debug("getRelatedCompartments: related compartmentsIds is empty !!!!\n\n");

    } else {
      compartmentsIds = relatedCompartmentsList.toArray(new String[relatedCompartmentsList.size()]);

      debugLogger.debug("getRelatedCompartments: related compartmentsIds = " + Arrays.toString(compartmentsIds) + "\n\n");

    }

    return compartmentsIds;
  }


  //
  //
  //
  //
  //

  /**
   * 
   * @param model the original model from which a sub-model is to be created.
   * @param reactsIds
   * @param compartmentsIds
   * @param speciesIds
   * @return an array of related species id or null if no related species are found.
   */
  public static String[] getRelatedSpecies(
    Model model,
    String[] reactsIds,
    String[] compartmentsIds,
    String[] speciesIds)
  {

    Logger debugLogger = Logger.getLogger(SubModel.class);
    debugLogger.debug("getRelatedSpecies ");
    debugLogger.debug("getRelatedSpecies: reactsIds: " + Arrays.toString(reactsIds));
    debugLogger.debug("getRelatedSpecies: compartmentsIds: " + Arrays.toString(compartmentsIds));
    debugLogger.debug("getRelatedSpecies: speciesIds: " + Arrays.toString(speciesIds) + '\n');

    // get the related species
    List<String> selectedSpecies = new ArrayList<String>();

    if ((speciesIds != null) || (compartmentsIds != null) || (reactsIds != null)) {

      for (Species species : model.getListOfSpecies()) {

        String speciesId = species.getId();
        boolean thisSpeciesSelected = false;

        if (speciesIds != null) {
          for (int j = 0; j < speciesIds.length; j++) {
            if (speciesId.equals(speciesIds[j])) {
              thisSpeciesSelected = true;
              break;
            }
          }
        }

        if (!thisSpeciesSelected && (compartmentsIds != null)) {
          for (int j = 0; j < compartmentsIds.length; j++) {
            if ((species.getCompartment()).equals(compartmentsIds[j])) {
              thisSpeciesSelected = true;
              break;
            }
          }
        }

        if (!thisSpeciesSelected && (reactsIds != null)) {
          for (int j = 0; j < reactsIds.length; j++) {
            Reaction reaction = model.getReaction(reactsIds[j]);

            thisSpeciesSelected = contains(reaction.getListOfReactants(), species);

            if (!thisSpeciesSelected) {
              thisSpeciesSelected = contains(reaction.getListOfProducts(), species);
            }
            if (!thisSpeciesSelected) {
              thisSpeciesSelected = contains(reaction.getListOfModifiers(), species);
            }

            if (thisSpeciesSelected) {
              break;
            }
          }
        }

        if (thisSpeciesSelected) {
          selectedSpecies.add(speciesId);
        }
      }
    }
    // convert to array
    if ((selectedSpecies == null) || selectedSpecies.isEmpty()) {
      speciesIds = null;

      debugLogger.debug("getRelatedSpecies: related speciesIds is empty !!!!! \n\n");

    } else {
      speciesIds = selectedSpecies.toArray(new String[selectedSpecies.size()]);

      debugLogger.debug("\ngetRelatedSpecies: related speciesIds = " + Arrays.toString(speciesIds) + "\n\n");

    }

    return speciesIds;
  }

  /**
   * 
   * @param model the original model from which a sub-model is to be created.
   * @param rulesIds
   * @param compartmentsIds
   * @param speciesIds
   * @return
   */
  private static String[] getRelatedRules(
    Model model,
    String[] rulesIds,
    String[] compartmentsIds,
    String[] speciesIds)
  {
    Logger debugLogger = Logger.getLogger(SubModel.class);
    debugLogger.debug("getRelatedRules ");
    debugLogger.debug("getRelatedRules: rulesIds: " + Arrays.toString(rulesIds));
    debugLogger.debug("getRelatedRules: compartmentsIds: " + Arrays.toString(compartmentsIds));
    debugLogger.debug("getRelatedRules: speciesIds: " + Arrays.toString(speciesIds) + '\n');

    // get the related rules
    List<String> selectedRules = new ArrayList<String>();

    if (rulesIds != null) {
      for (String ruleId : rulesIds) {
        selectedRules.add(ruleId);
      }
    }

    for (Rule rule : model.getListOfRules()) {

      if (rule instanceof ExplicitRule) {

        String variableId = ((ExplicitRule) rule).getVariable();

        if (speciesIds != null) {
          for (String speciesId : speciesIds) {
            if (speciesId.equals(variableId)) {
              selectedRules.add(rule.getMetaId());
            }
          }
        }
        if (compartmentsIds != null) {
          for (String compartmentId : compartmentsIds) {
            if (compartmentId.equals(variableId)) {
              selectedRules.add(rule.getMetaId());
            }
          }
        }
        for (Parameter parameter : model.getListOfParameters()) {
          if (parameter.getId().equals(variableId)) {
            selectedRules.add(rule.getMetaId());
          }
        }

      } else {
        String ruleId = rule.getMetaId();
        if (ruleId != null) {
          selectedRules.add(ruleId);
        }
      }
    }

    // TODO: check the math of rule to check if we need to add any more species, compartment or other

    // convert to array
    if ((selectedRules == null) || selectedRules.isEmpty()) {
      rulesIds = null;

      debugLogger.debug("getRelatedRules: related RulesIds is empty !!!!! \n\n");

    } else {
      rulesIds = selectedRules.toArray(new String[selectedRules.size()]);

      debugLogger.debug("\ngetRelatedRules: related RulesIds = " + Arrays.toString(rulesIds) + "\n\n");

    }

    return rulesIds;
  }

  /**
   * 
   * @param model the original model from which a sub-model is to be created.
   * @param eventsIds
   * @param compartmentsIds
   * @param speciesIds
   * @return
   */
  private static String[] getRelatedEvents(
    Model model,
    String[] eventsIds,
    String[] compartmentsIds,
    String[] speciesIds)
  {
    Logger debugLogger = Logger.getLogger(SubModel.class);
    debugLogger.debug("getRelatedEvents ");
    debugLogger.debug("getRelatedEvents: eventsIds: " + Arrays.toString(eventsIds));
    debugLogger.debug("getRelatedEvents: compartmentsIds: " + Arrays.toString(compartmentsIds));
    debugLogger.debug("getRelatedEvents: speciesIds: " + Arrays.toString(speciesIds) + '\n');

    // get the related events
    ArrayList<String> selectedEvents = new ArrayList<String>();

    if (eventsIds != null) {
      for (String eventId : eventsIds) {
        selectedEvents.add(eventId);
      }
    }

    for (Event event : model.getListOfEvents()) {

      // TODO: check the trigger math
      // TODO: check the delay math

      for (EventAssignment eventAssigment : event.getListOfEventAssignments()) {

        String variableId = eventAssigment.getVariable();

        for (String speciesId : speciesIds) {
          if (speciesId.equals(variableId) && !selectedEvents.contains(event.getMetaId())) {
            selectedEvents.add(event.getMetaId());
          }
        }
        for (String compartmentId : compartmentsIds) {
          if (compartmentId.equals(variableId) && !selectedEvents.contains(event.getMetaId())) {
            selectedEvents.add(event.getMetaId());
          }
        }
        for (Parameter parameter : model.getListOfParameters()) {
          if (parameter.getId().equals(variableId) && !selectedEvents.contains(event.getMetaId())) {
            selectedEvents.add(event.getMetaId());
          }
        }
      }
    }

    // convert to array
    if ((selectedEvents == null) || selectedEvents.isEmpty()) {
      eventsIds = null;

      debugLogger.debug("getRelatedEvents: related EventsIds is empty !!!!! \n\n");

    } else {
      eventsIds = selectedEvents.toArray(new String[selectedEvents.size()]);

      debugLogger.debug("\ngetRelatedEvents: related EventsIds = " + Arrays.toString(eventsIds) + "\n\n");

    }

    return eventsIds;
  }


  //
  //
  //
  //
  //

  /**
   * Returns an array of reaction id related to the {@code reactsIds} or {@code speciesIds}.
   * 
   * All the reactions in {@code reactsIds} should be part of the result if
   * the function is called properly with valid reacionId.
   * <br>
   * The {@link Reaction}s, where the {@link Species} in the {@code speciesIds} array are involved as reactant,
   * product or modifier, are added in the returned reaction id array.
   * 
   * @param model the original model from which a sub-model is to be created.
   * @param reactsIds the list of {@link Reaction}s selected by the user to create a sub-model
   * @param speciesIds the list of {@link Species} selected by the user to create a sub-model
   * @return an array of related {@link Reaction}s id or {@code null} if no related reactions are found.
   */
  public static String[] getRelatedReactions(
    Model model,
    String[] reactsIds,
    String[] speciesIds) {

    Logger debugLogger = Logger.getLogger(SubModel.class);

    debugLogger.debug("getRelatedReactions ");
    debugLogger.debug("getRelatedReactions: selected reactsIds: " + Arrays.toString(reactsIds));
    debugLogger.debug("getRelatedReactions: selected speciesIds: " + Arrays.toString(speciesIds) + '\n');

    // get all related reactions
    List<String> relatedReactsList = new ArrayList<String>();

    if ((reactsIds != null) || (speciesIds != null)) {

      for (Reaction reaction : model.getListOfReactions()) {

        String reactionId = reaction.getId();

        debugLogger.debug("getRelatedReactions: reaction = " + reactionId);

        boolean thisReactionSelected = false;

        if (reactsIds != null) {
          for (int j = 0; (j < reactsIds.length) && !thisReactionSelected; j++) {
            if (reactionId.equals(reactsIds[j])) {
              thisReactionSelected = true;
            }
          }
        }

        if (!thisReactionSelected && (speciesIds != null)) {
          for (int j = 0; (j < speciesIds.length) && !thisReactionSelected; j++) {

            Species species = model.getSpecies(speciesIds[j]);

            // debugLogger.debug("getRelatedReactions: speciesId = " + speciesIds[j]);
            // debugLogger.debug("getRelatedReactions: reactant = " + Arrays.toString(reaction.getReactants().toArray()));
            // debugLogger.debug("getRelatedReactions: product = " + Arrays.toString(reaction.getProducts().toArray()));
            // debugLogger.debug("getRelatedReactions: modifier = " + Arrays.toString(reaction.getModifiers().toArray()));

            // thisReactionSelected = reaction.getReactants().contains(species); // not working !! to investigate may be !!
            thisReactionSelected = contains(reaction.getListOfReactants(), species);

            // debugLogger.debug("getRelatedReactions: contains.own = " + contains(reaction.getReactants(), species));

            if (!thisReactionSelected) {
              thisReactionSelected = contains(reaction.getListOfProducts(), species);
            }
            if (!thisReactionSelected) {
              thisReactionSelected = contains(reaction.getListOfModifiers(), species);
            }
          }
        }

        if (thisReactionSelected) {
          relatedReactsList.add(reactionId);
        }
      }
    }

    // convert to array
    if ((relatedReactsList == null) || relatedReactsList.isEmpty()) {
      debugLogger.debug("getRelatedReactions: related reactsIds is empty !!!!\n\n");

      return null;
    }

    debugLogger.debug("getRelatedReactions: related reactsIds: " + Arrays.toString(reactsIds) + "!!!!\n\n");

    return relatedReactsList.toArray(new String[relatedReactsList.size()]);
  }

  //
  //
  //
  //
  //

  /**
   * 
   * @param relatedFunctionsSet
   * @param allFunctionsIdSet
   * @param mathNode
   */
  private static void getRelatedFunctionsId(Set<String> relatedFunctionsSet, Set<String> allFunctionsIdSet, ASTNode mathNode) {
    getRelatedFunctions(relatedFunctionsSet, allFunctionsIdSet, mathNode);
  }

  //
  //
  //
  //
  //

  /**
   * 
   * @param relatedFunctionsSet
   * @param allFunctionsIdSet
   * @param mathNode
   */
  private static void getRelatedFunctions(Set<String> relatedFunctionsSet, Set<String> allFunctionsIdSet, ASTNode mathNode) {

    Logger debugLogger = Logger.getLogger(SubModel.class);

    debugLogger.debug("getRelatedFunctions ");
    debugLogger.debug("getRelatedFunctions: math = " + mathNode);

    if (mathNode == null)
    {
      return;
    }

    if ((mathNode.isName() || mathNode.isFunction())
        && mathNode.getName() != null
        && allFunctionsIdSet.contains(mathNode.getName().trim())
        && !relatedFunctionsSet.contains(mathNode.getName().trim()))
    {
      relatedFunctionsSet.add(mathNode.getName().trim());
    }

    if (debugLogger.isDebugEnabled()
        && (mathNode.isName() || mathNode.isFunction())
        && mathNode.getName() == null)
    {
      debugLogger.debug("getRelatedFunctions: mathNode.getName() == null !!! - type = " + mathNode.getType());
    }

    for (int i = 0; i < mathNode.getChildCount(); i++) {
      getRelatedFunctions(relatedFunctionsSet, allFunctionsIdSet, mathNode.getChild(i));
    }
  }

}
