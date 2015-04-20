/*
 * $Id: PluginUtils.java 2109 2015-01-05 04:50:45Z andreas-draeger $
 * $URL: svn://svn.code.sf.net/p/jsbml/code/trunk/modules/celldesigner/src/org/sbml/jsbml/celldesigner/PluginUtils.java $
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
package org.sbml.jsbml.celldesigner;

import static org.sbml.jsbml.celldesigner.CellDesignerConstants.LINK_TO_CELLDESIGNER;
import static org.sbml.jsbml.celldesigner.libsbml.LibSBMLConstants.LINK_TO_LIBSBML;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.xml.stream.XMLStreamException;

import jp.sbi.celldesigner.plugin.PluginAlgebraicRule;
import jp.sbi.celldesigner.plugin.PluginAssignmentRule;
import jp.sbi.celldesigner.plugin.PluginCompartment;
import jp.sbi.celldesigner.plugin.PluginCompartmentType;
import jp.sbi.celldesigner.plugin.PluginConstraint;
import jp.sbi.celldesigner.plugin.PluginEvent;
import jp.sbi.celldesigner.plugin.PluginEventAssignment;
import jp.sbi.celldesigner.plugin.PluginFunctionDefinition;
import jp.sbi.celldesigner.plugin.PluginInitialAssignment;
import jp.sbi.celldesigner.plugin.PluginKineticLaw;
import jp.sbi.celldesigner.plugin.PluginModel;
import jp.sbi.celldesigner.plugin.PluginModifierSpeciesReference;
import jp.sbi.celldesigner.plugin.PluginParameter;
import jp.sbi.celldesigner.plugin.PluginRateRule;
import jp.sbi.celldesigner.plugin.PluginReaction;
import jp.sbi.celldesigner.plugin.PluginRule;
import jp.sbi.celldesigner.plugin.PluginSBase;
import jp.sbi.celldesigner.plugin.PluginSimpleSpeciesReference;
import jp.sbi.celldesigner.plugin.PluginSpecies;
import jp.sbi.celldesigner.plugin.PluginSpeciesAlias;
import jp.sbi.celldesigner.plugin.PluginSpeciesReference;
import jp.sbi.celldesigner.plugin.PluginSpeciesType;
import jp.sbi.celldesigner.plugin.PluginUnit;
import jp.sbi.celldesigner.plugin.PluginUnitDefinition;
import jp.sbi.celldesigner.plugin.util.PluginCompartmentSymbolType;
import jp.sbi.celldesigner.plugin.util.PluginReactionSymbolType;

import org.apache.log4j.Logger;
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
import org.sbml.jsbml.SBO;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.SimpleSpeciesReference;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.SpeciesType;
import org.sbml.jsbml.StoichiometryMath;
import org.sbml.jsbml.Trigger;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.celldesigner.libsbml.LibSBMLUtils;
import org.sbml.jsbml.util.ResourceManager;
import org.sbml.libsbml.XMLNode;

/**
 * This class shall be used for methods that are necessary for the CellDesigner
 * synchronization and the libSBML synchronization.
 * 
 * @author Andreas Dr&auml;ger
 * @author Alexander Peltzer
 * @version $Rev: 2109 $
 * @since 1.0
 */
@SuppressWarnings("deprecation")
public class PluginUtils {

  /**
   * The default shape for new compartments in CellDesigner.
   */
  private static String DEFAULT_COMPARTMENT_SYMBOL_TYPE = PluginCompartmentSymbolType.SQUARE;
  /**
   * A {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger(PluginUtils.class);
  /**
   * Localization support.
   */
  private static final ResourceBundle bundle = ResourceManager.getBundle("org.sbml.jsbml.celldesigner.Messages");

  /**
   * 
   * @param compartment
   * @return
   * @throws XMLStreamException
   */
  public static PluginCompartment convertCompartment(Compartment compartment) throws XMLStreamException {
    PluginCompartment pluginCompartment = (PluginCompartment) compartment.getUserObject(LINK_TO_CELLDESIGNER);
    if (pluginCompartment == null) {
      pluginCompartment = new PluginCompartment(DEFAULT_COMPARTMENT_SYMBOL_TYPE);
      compartment.putUserObject(LINK_TO_CELLDESIGNER, pluginCompartment);
    }
    transferNamedSBaseProperties(compartment, pluginCompartment);
    if (compartment.isSetCompartmentType()) {
      pluginCompartment.setCompartmentType(compartment.getCompartmentType());
    }
    if (compartment.isSetOutside()) {
      pluginCompartment.setOutside(compartment.getOutside());
    }
    if (compartment.isSetSize()) {
      pluginCompartment.setSize(compartment.getSize());
    }
    if (compartment.isSetUnits()) {
      pluginCompartment.setUnits(compartment.getUnits());
    }
    if (compartment.isSetConstant()) {
      pluginCompartment.setConstant(compartment.getConstant());
    }
    if (compartment.isSetSpatialDimensions()) {
      long spatialDimensions = (long) compartment.getSpatialDimensions();
      pluginCompartment.setSpatialDimensions(spatialDimensions);
      if (spatialDimensions - compartment.getSpatialDimensions() != 0d) {
        logger.warn(MessageFormat.format(
          "Cannot correctly convert non-integer spatial dimensions {0,number} of compartment {1}.",
          compartment.getSpatialDimensions(), compartment.getId()));
      }
    }
    return pluginCompartment;
  }

  /**
   * 
   * @param compartmentType
   * @return
   * @throws XMLStreamException
   */
  public static PluginCompartmentType convertCompartmentType(CompartmentType compartmentType) throws XMLStreamException {
    PluginCompartmentType pluginCompartmentType = (PluginCompartmentType) compartmentType.getUserObject(LINK_TO_CELLDESIGNER);
    if (pluginCompartmentType == null) {
      pluginCompartmentType = new PluginCompartmentType(compartmentType.getId());
      compartmentType.putUserObject(LINK_TO_CELLDESIGNER, pluginCompartmentType);
    }
    transferNamedSBaseProperties(compartmentType, pluginCompartmentType);
    return pluginCompartmentType;
  }

  /**
   * 
   * @param constraint
   * @return
   * @throws XMLStreamException
   */
  public static PluginConstraint convertConstraint(Constraint constraint) throws XMLStreamException {
    PluginConstraint pluginConstraint = (PluginConstraint) constraint.getUserObject(LINK_TO_CELLDESIGNER);
    if (pluginConstraint == null) {
      pluginConstraint = new PluginConstraint(constraint.getMath().toFormula());
      constraint.putUserObject(LINK_TO_CELLDESIGNER, pluginConstraint);
    }
    transferMathContainerProperties(constraint, pluginConstraint);
    if (constraint.isSetMessage()) {
      pluginConstraint.setMessage(constraint.getMessageString());
    }
    return pluginConstraint;
  }

  /**
   * 
   * @param event
   * @return
   * @throws XMLStreamException
   */
  public static PluginEvent convertEvent(Event event) throws XMLStreamException {
    PluginEvent pluginEvent = (PluginEvent) event.getUserObject(LINK_TO_CELLDESIGNER);
    if (pluginEvent == null) {
      pluginEvent = new PluginEvent(event.getId());
      event.putUserObject(LINK_TO_CELLDESIGNER, pluginEvent);
    }
    transferNamedSBaseProperties(event, pluginEvent);
    if (event.isSetDelay()) {
      pluginEvent.setDelay(LibSBMLUtils.convertASTNode(event.getDelay().getMath()));
      LibSBMLUtils.transferMathContainerProperties(event.getDelay(), pluginEvent.getDelay());
    }
    if (event.isSetPriority()) {
      // Nothing to do...
    }
    if (event.isSetListOfEventAssignments()) {
      for (EventAssignment ea : event.getListOfEventAssignments()) {
        PluginEventAssignment pea = new PluginEventAssignment(pluginEvent);
        ea.putUserObject(LINK_TO_CELLDESIGNER, pea);
        convertEventAssignment(ea);
      }
      event.getListOfEventAssignments().putUserObject(LINK_TO_CELLDESIGNER, pluginEvent.getListOfEventAssignments());
    }
    if (event.isSetTimeUnits()) {
      pluginEvent.setTimeUnits(event.getTimeUnits());
    }
    if (event.isSetTrigger()) {
      pluginEvent.setTrigger(LibSBMLUtils.convertTrigger(event.getTrigger()));
      event.getTrigger().putUserObject(LINK_TO_CELLDESIGNER, pluginEvent.getTrigger());
    }
    if (event.isSetUseValuesFromTriggerTime()) {
      pluginEvent.setUseValuesFromTriggerTime(event.getUseValuesFromTriggerTime());
    }
    return pluginEvent;
  }

  /**
   * 
   * @param eventAssignment
   * @return
   * @throws XMLStreamException
   */
  public static PluginEventAssignment convertEventAssignment(
    EventAssignment eventAssignment) throws XMLStreamException {
    PluginEventAssignment pea = (PluginEventAssignment) eventAssignment.getUserObject(LINK_TO_CELLDESIGNER);
    if (pea == null) {
      pea = new PluginEventAssignment((PluginEvent) eventAssignment.getParent().getParent().getUserObject(LINK_TO_CELLDESIGNER));
      eventAssignment.putUserObject(LINK_TO_CELLDESIGNER, pea);
    }
    transferMathContainerProperties(eventAssignment, pea);
    if (eventAssignment.isSetVariable()) {
      pea.setVariable(eventAssignment.getVariable());
    }
    return pea;
  }

  /**
   * 
   * @param functionDefinition
   * @return
   * @throws XMLStreamException
   */
  public static PluginFunctionDefinition convertFunctionDefinition(
    FunctionDefinition functionDefinition) throws XMLStreamException {
    PluginFunctionDefinition pfd = (PluginFunctionDefinition) functionDefinition.getUserObject(LINK_TO_CELLDESIGNER);
    if (pfd == null) {
      pfd = new PluginFunctionDefinition(functionDefinition.getId());
      functionDefinition.putUserObject(LINK_TO_CELLDESIGNER, pfd);
    }
    transferMathContainerProperties(functionDefinition, pfd);
    return pfd;
  }

  /**
   * 
   * @param initialAssignment
   * @return
   * @throws XMLStreamException
   */
  public static PluginInitialAssignment convertInitialAssignment(
    InitialAssignment initialAssignment) throws XMLStreamException {
    PluginInitialAssignment pia = (PluginInitialAssignment) initialAssignment.getUserObject(LINK_TO_CELLDESIGNER);
    if (pia == null) {
      pia = new PluginInitialAssignment(initialAssignment.getVariable());
      initialAssignment.putUserObject(LINK_TO_CELLDESIGNER, pia);
    }
    transferMathContainerProperties(initialAssignment, pia);
    return pia;
  }

  /**
   * 
   * @param kineticLaw
   * @return
   * @throws XMLStreamException
   */
  public static PluginKineticLaw convertKineticLaw(KineticLaw kineticLaw) throws XMLStreamException {
    PluginKineticLaw pkl = (PluginKineticLaw) kineticLaw.getUserObject(LINK_TO_CELLDESIGNER);
    if (pkl == null) {
      pkl = new PluginKineticLaw((PluginReaction) kineticLaw.getParent().getUserObject(LINK_TO_CELLDESIGNER));
      kineticLaw.putUserObject(LINK_TO_CELLDESIGNER, pkl);
    }
    transferMathContainerProperties(kineticLaw, pkl);
    if (kineticLaw.isSetListOfLocalParameters()) {
      for (LocalParameter p : kineticLaw.getListOfLocalParameters()) {
        pkl.addParameter(convertLocalParameter(p));
      }
      kineticLaw.getListOfLocalParameters().putUserObject(LINK_TO_CELLDESIGNER, pkl.getListOfParameters());
    }
    return pkl;
  }

  /**
   * 
   * @param localParameter
   * @return
   * @throws XMLStreamException
   */
  public static PluginParameter convertLocalParameter(LocalParameter localParameter) throws XMLStreamException {
    PluginParameter pp = (PluginParameter) localParameter.getUserObject(LINK_TO_CELLDESIGNER);
    if (pp == null) {
      pp = new PluginParameter((PluginKineticLaw) localParameter.getParent().getParent().getUserObject(LINK_TO_CELLDESIGNER));
      localParameter.putUserObject(LINK_TO_CELLDESIGNER, pp);
    }
    transferNamedSBaseProperties(localParameter, pp);
    if (localParameter.isExplicitlySetConstant()) {
      pp.setConstant(true);
    }
    if (localParameter.isSetUnits()) {
      pp.setUnits(localParameter.getUnits());
    }
    if (localParameter.isSetValue()) {
      pp.setValue(localParameter.getValue());
    }
    return pp;
  }

  /**
   * @param model
   * @return
   * @throws XMLStreamException
   */
  public static PluginModel convertModel(Model model) throws XMLStreamException {
    PluginModel pm = (PluginModel) model.getUserObject(LINK_TO_CELLDESIGNER);
    if (pm == null) {
      pm = new PluginModel(new org.sbml.libsbml.Model(model.getLevel(), model.getVersion()));
      model.putUserObject(LINK_TO_CELLDESIGNER, pm);
    }
    transferNamedSBaseProperties(model, pm);

    if (model.isSetListOfUnitDefinitions()) {
      for (UnitDefinition ud : model.getListOfUnitDefinitions()) {
        pm.addUnitDefinition(convertUnitDefinition(ud));
      }
      model.getListOfUnitDefinitions().putUserObject(LINK_TO_CELLDESIGNER, pm.getListOfUnitDefinitions());
    }
    if (model.isSetListOfFunctionDefinitions()) {
      for (FunctionDefinition fd : model.getListOfFunctionDefinitions()) {
        pm.addFunctionDefinition(convertFunctionDefinition(fd));
      }
      model.getListOfFunctionDefinitions().putUserObject(LINK_TO_CELLDESIGNER, pm.getListOfFunctionDefinitions());
    }
    if (model.isSetListOfCompartmentTypes()) {
      for (CompartmentType ct : model.getListOfCompartmentTypes()) {
        pm.addCompartmentType(convertCompartmentType(ct));
      }
      model.getListOfCompartmentTypes().putUserObject(LINK_TO_CELLDESIGNER, pm.getListOfCompartmentTypes());
    }
    if (model.isSetListOfSpeciesTypes()) {
      for (SpeciesType st : model.getListOfSpeciesTypes()) {
        pm.addSpeciesType(convertSpeciesType(st));
      }
      model.getListOfCompartmentTypes().putUserObject(LINK_TO_CELLDESIGNER, pm.getListOfCompartmentTypes());
    }
    if (model.isSetListOfCompartments()) {
      for (Compartment c : model.getListOfCompartments()) {
        pm.addCompartment(convertCompartment(c));
      }
      model.getListOfCompartments().putUserObject(LINK_TO_CELLDESIGNER, pm.getListOfCompartments());
    }
    if (model.isSetListOfSpecies()) {
      for (Species s : model.getListOfSpecies()) {
        pm.addSpecies(convertSpecies(s));
      }
      model.getListOfSpecies().putUserObject(LINK_TO_CELLDESIGNER, pm.getListOfSpecies());
    }
    if (model.isSetListOfParameters()) {
      for (Parameter p : model.getListOfParameters()) {
        pm.addParameter(convertParameter(p));
      }
      model.getListOfParameters().putUserObject(LINK_TO_CELLDESIGNER, pm.getListOfParameters());
    }
    if (model.isSetListOfConstraints()) {
      for (Constraint c : model.getListOfConstraints()) {
        pm.addConstraint(convertConstraint(c));
      }
      model.getListOfConstraints().putUserObject(LINK_TO_CELLDESIGNER, pm.getListOfConstraints());
    }
    if (model.isSetListOfInitialAssignments()) {
      for (InitialAssignment ia : model.getListOfInitialAssignments()) {
        pm.addInitialAssignment(convertInitialAssignment(ia));
      }
      model.getListOfInitialAssignments().putUserObject(LINK_TO_CELLDESIGNER, pm.getListOfInitialAssignments());
    }
    if (model.isSetListOfRules()) {
      for (Rule r : model.getListOfRules()) {
        pm.addRule(convertRule(r));
        convertRule(r);
      }
      model.getListOfRules().putUserObject(LINK_TO_CELLDESIGNER, pm.getListOfRules());
    }
    if (model.isSetListOfReactions()) {
      for (Reaction r : model.getListOfReactions()) {
        pm.addReaction(convertReaction(r));
      }
      model.getListOfReactions().putUserObject(LINK_TO_CELLDESIGNER, pm.getListOfReactions());
    }
    if (model.isSetListOfEvents()) {
      for (Event e : model.getListOfEvents()) {
        pm.addEvent(convertEvent(e));
      }
      model.getListOfEvents().putUserObject(LINK_TO_CELLDESIGNER, pm.getListOfEvents());
    }

    return pm;
  }

  /**
   * 
   * @param modifierSpeciesReference
   * @return
   * @throws XMLStreamException
   */
  public static PluginModifierSpeciesReference convertModifierSpeciesReference(ModifierSpeciesReference modifierSpeciesReference) throws XMLStreamException {
    PluginModifierSpeciesReference pmsr = (PluginModifierSpeciesReference) modifierSpeciesReference.getUserObject(LINK_TO_CELLDESIGNER);
    if (pmsr == null) {
      pmsr = new PluginModifierSpeciesReference(
        (PluginReaction) ((SBase) modifierSpeciesReference.getParent().getParent()).getUserObject(LINK_TO_CELLDESIGNER),
        new PluginSpeciesAlias(
          (PluginSpecies) modifierSpeciesReference.getSpeciesInstance().getUserObject(LINK_TO_CELLDESIGNER),
          SBO.convertSBO2Alias(modifierSpeciesReference.isSetSBOTerm() ? modifierSpeciesReference.getSBOTerm() : SBO.getModulation())));
      modifierSpeciesReference.putUserObject(LINK_TO_CELLDESIGNER, pmsr);
    }
    transferNamedSBaseProperties(modifierSpeciesReference, pmsr);
    if (modifierSpeciesReference.isSetSpecies()) {
      pmsr.setAlias(new PluginSpeciesAlias(
        (PluginSpecies) modifierSpeciesReference.getSpeciesInstance().getUserObject(LINK_TO_CELLDESIGNER),
        SBO.convertSBO2Alias(modifierSpeciesReference.isSetSBOTerm() ? modifierSpeciesReference.getSBOTerm() : SBO.getModulation())));
    }
    return pmsr;
  }

  /**
   * 
   * @param parameter
   * @return
   * @throws XMLStreamException
   */
  public static PluginParameter convertParameter(Parameter parameter) throws XMLStreamException {
    PluginParameter pp = (PluginParameter) parameter.getUserObject(LINK_TO_CELLDESIGNER);
    if (pp == null) {
      pp = new PluginParameter((PluginModel) parameter.getModel().getUserObject(LINK_TO_CELLDESIGNER));
      parameter.putUserObject(LINK_TO_CELLDESIGNER, pp);
    }
    transferNamedSBaseProperties(parameter, pp);
    pp.setConstant(parameter.isConstant());
    if (parameter.isSetUnits()) {
      pp.setUnits(parameter.getUnits());
    }
    if (parameter.isSetValue()) {
      pp.setValue(parameter.getValue());
    }
    return pp;
  }

  /**
   * 
   * @param reaction
   * @return
   * @throws XMLStreamException
   */
  public static PluginReaction convertReaction(Reaction reaction) throws XMLStreamException {
    PluginReaction pr = (PluginReaction) reaction.getUserObject(LINK_TO_CELLDESIGNER);
    if (pr == null) {
      pr = new PluginReaction();
      reaction.putUserObject(LINK_TO_CELLDESIGNER, reaction);
    }
    transferNamedSBaseProperties(reaction, pr);
    if (reaction.isSetFast()) {
      pr.setFast(reaction.isFast());
    }
    if (reaction.isSetReversible()) {
      pr.setReversible(reaction.isReversible());
    }
    if (reaction.isSetKineticLaw()) {
      pr.setKineticLaw(convertKineticLaw(reaction.getKineticLaw()));
    }
    if (reaction.isSetCompartment()) {
      // nothing to do
    }
    if (reaction.isSetListOfReactants()) {
      for (SpeciesReference sr : reaction.getListOfReactants()) {
        pr.addReactant(convertSpeciesReference(sr));
      }
      reaction.getListOfReactants().putUserObject(LINK_TO_CELLDESIGNER, pr.getListOfReactants());
    }
    if (reaction.isSetListOfProducts()) {
      for (SpeciesReference sr : reaction.getListOfProducts()) {
        pr.addProduct(convertSpeciesReference(sr));
      }
      reaction.getListOfProducts().putUserObject(LINK_TO_CELLDESIGNER, pr.getListOfProducts());
    }
    if (reaction.isSetListOfModifiers()) {
      for (ModifierSpeciesReference mr : reaction.getListOfModifiers()) {
        pr.addModifier(convertModifierSpeciesReference(mr));
      }
      reaction.getListOfModifiers().putUserObject(LINK_TO_CELLDESIGNER, pr.getListOfModifiers());
    }
    return pr;
  }

  /**
   * 
   * @param rule
   * @return
   * @throws XMLStreamException
   */
  public static PluginRule convertRule(Rule rule) throws XMLStreamException {
    PluginRule pr = (PluginRule) rule.getUserObject(LINK_TO_CELLDESIGNER);
    if (rule.isAlgebraic()) {
      if (pr == null) {
        pr = new PluginAlgebraicRule((PluginModel) rule.getModel().getUserObject(LINK_TO_CELLDESIGNER));
      }
    } else {
      if (rule.isAssignment()) {
        if (pr == null) {
          pr = new PluginAssignmentRule((PluginModel) rule.getModel().getUserObject(LINK_TO_CELLDESIGNER));
        }
        if (((AssignmentRule) rule).isSetVariable()) {
          ((PluginAssignmentRule) pr).setVariable(((AssignmentRule) rule).getVariable());
        }
      } else {
        if (pr == null) {
          pr = new PluginRateRule((PluginModel) rule.getModel().getUserObject(LINK_TO_CELLDESIGNER));
        }
        if (((RateRule) rule).isSetVariable()) {
          ((PluginRateRule) pr).setVariable(((RateRule) rule).getVariable());
        }
      }
    }
    transferMathContainerProperties(rule, pr);
    rule.putUserObject(LINK_TO_CELLDESIGNER, pr);
    return pr;
  }

  /**
   * 
   * @param species
   * @return
   * @throws XMLStreamException
   */
  public static PluginSpecies convertSpecies(Species species) throws XMLStreamException {
    PluginSpecies ps = (PluginSpecies) species.getUserObject(LINK_TO_CELLDESIGNER);
    if (ps == null) {
      ps = new PluginSpecies(SBO.convertSBO2Alias(species.isSetSBOTerm() ? species.getSBOTerm() : SBO.getUnknownMolecule()), species.getId());
      species.putUserObject(LINK_TO_CELLDESIGNER, ps);
    }
    transferNamedSBaseProperties(species, ps);
    if (species.isSetBoundaryCondition()) {
      ps.setBoundaryCondition(species.getBoundaryCondition());
    }
    if (species.isSetCharge()) {
      ps.setCharge(species.getCharge());
    }
    if (species.isSetCompartment()) {
      ps.setCompartment(species.getCompartment());
    }
    if (species.isSetConstant()) {
      ps.setConstant(species.getConstant());
    }
    if (species.isSetHasOnlySubstanceUnits()) {
      ps.setHasOnlySubstanceUnits(species.hasOnlySubstanceUnits());
    }
    if (species.isSetInitialAmount()) {
      ps.setInitialAmount(species.getInitialAmount());
    } else if (species.isSetInitialConcentration()) {
      ps.setInitialConcentration(species.getInitialConcentration());
    }
    if (species.isSetSpeciesType()) {
      ps.setSpeciesType(species.getSpeciesType());
    }
    if (species.isSetSubstanceUnits()) {
      ps.setSubstanceUnits(species.getSubstanceUnits());
    }
    return ps;
  }

  /**
   * 
   * @param speciesReference
   * @return
   * @throws XMLStreamException
   */
  public static PluginSpeciesReference convertSpeciesReference(SpeciesReference speciesReference) throws XMLStreamException {
    PluginSpeciesReference psr = (PluginSpeciesReference) speciesReference.getUserObject(LINK_TO_CELLDESIGNER);
    if (psr == null) {
      int sbo = speciesReference.getSBOTerm();
      if (sbo < 1) {
        sbo = speciesReference.getParent().getElementName().toLowerCase().contains("reactant") ? SBO.getReactant() : SBO.getProduct();
      }
      psr = new PluginSpeciesReference(
        (PluginReaction) ((SBase) speciesReference.getParent().getParent()).getUserObject(LINK_TO_CELLDESIGNER),
        new PluginSpeciesAlias(
          (PluginSpecies) speciesReference.getSpeciesInstance().getUserObject(LINK_TO_CELLDESIGNER),
          SBO.convertSBO2Alias(sbo)));
      speciesReference.putUserObject(LINK_TO_CELLDESIGNER, psr);
    }
    transferSpeciesReferenceProperties(speciesReference, psr);
    return psr;
  }

  /**
   * @param speciesType
   * @return
   * @throws XMLStreamException
   */
  public static PluginSpeciesType convertSpeciesType(SpeciesType speciesType) throws XMLStreamException {
    PluginSpeciesType pst = (PluginSpeciesType) speciesType.getUserObject(LINK_TO_CELLDESIGNER);
    if (pst == null) {
      pst = new PluginSpeciesType(speciesType.getId());
      speciesType.putUserObject(LINK_TO_CELLDESIGNER, pst);
    }
    transferNamedSBaseProperties(speciesType, pst);
    return pst;
  }

  /**
   * 
   * @param unitDefinition
   * @return
   * @throws XMLStreamException
   */
  public static PluginUnitDefinition convertUnitDefinition(UnitDefinition unitDefinition) throws XMLStreamException {
    PluginUnitDefinition pu = (PluginUnitDefinition) unitDefinition.getUserObject(LINK_TO_CELLDESIGNER);
    if (pu == null) {
      pu = new PluginUnitDefinition(unitDefinition.getId());
      unitDefinition.putUserObject(LINK_TO_CELLDESIGNER, pu);
    }
    transferNamedSBaseProperties(unitDefinition, pu);
    if (unitDefinition.isSetListOfUnits()) {
      for (Unit unit : unitDefinition.getListOfUnits()) {
        pu.addUnit(convertUnit(unit));
      }
      unitDefinition.getListOfUnits().putUserObject(LINK_TO_CELLDESIGNER, pu.getListOfUnits());
    }
    return pu;
  }

  /**
   * 
   * @param unit
   * @return
   * @throws XMLStreamException
   */
  public static PluginUnit convertUnit(Unit unit) throws XMLStreamException {
    PluginUnit pu = (PluginUnit) unit.getUserObject(LINK_TO_CELLDESIGNER);
    if (pu == null) {
      pu = new PluginUnit((PluginUnitDefinition) unit.getParent().getParent().getUserObject(LINK_TO_CELLDESIGNER));
      unit.putUserObject(LINK_TO_CELLDESIGNER, pu);
    }
    transferSBaseProperties(unit, pu);
    if (unit.isSetKind()) {
      pu.setKind(LibSBMLUtils.convertUnitKind(unit.getKind()));
    }
    if (unit.isSetExponent()) {
      pu.setExponent((int) unit.getExponent());
      double error = unit.getExponent() - ((int) unit.getExponent());
      if (error != 0d) {
        logger.warn(MessageFormat.format(bundle.getString("ROUNDING_ERROR"), error));
      }
    }
    if (unit.isSetMultiplier()) {
      pu.setMultiplier(unit.getMultiplier());
    }
    if (unit.isSetOffset()) {
      pu.setOffset(unit.getOffset());
    }
    if (unit.isSetScale()) {
      pu.setScale(unit.getScale());
    }
    return pu;
  }

  /**
   * Copies the math in the JSBML ojbect into the CellDesigner object.
   * 
   * @param mathContainer
   * @param sbase
   */
  public static void transferMath(MathContainer mathContainer,
    PluginSBase sbase) {
    if (mathContainer.isSetMath()) {
      org.sbml.libsbml.ASTNode libASTNode = LibSBMLUtils.convertASTNode(mathContainer.getMath());
      if (sbase instanceof PluginKineticLaw) {
        ((PluginKineticLaw) sbase).setMath(libASTNode);
        LibSBMLUtils.link(mathContainer.getMath(), libASTNode);
      } else if (sbase instanceof PluginInitialAssignment) {
        ((PluginRule) sbase).setMath(libASTNode);
        LibSBMLUtils.link(mathContainer.getMath(), libASTNode);
      } else if (sbase instanceof PluginRule) {
        ((PluginRule) sbase).setMath(libASTNode);
        LibSBMLUtils.link(mathContainer.getMath(), libASTNode);
      } else if (sbase instanceof PluginConstraint) {
        // Nothing to do because math can only be set in the constructor.
      } else if ((mathContainer instanceof Delay) ||
          (mathContainer instanceof Priority) ||
          (mathContainer instanceof StoichiometryMath) ||
          (mathContainer instanceof Trigger)) {
        // Nothing to do because here math is stored differently...
      } else if ((sbase instanceof PluginFunctionDefinition)) {
        ((PluginFunctionDefinition) sbase).setMath(libASTNode);
        LibSBMLUtils.link(mathContainer.getMath(), libASTNode);
      }
    }
  }

  /**
   * Copies all properties from the JSBML object to the CellDesigner object.
   * 
   * @param mathContainer
   * @param sbase
   * @throws XMLStreamException
   */
  public static void transferMathContainerProperties(MathContainer mathContainer, PluginSBase sbase) throws XMLStreamException {
    if (mathContainer instanceof NamedSBase) {
      transferNamedSBaseProperties((NamedSBase) mathContainer, sbase);
    } else {
      transferSBaseProperties(mathContainer, sbase);
    }
    transferMath(mathContainer, sbase);
  }

  /**
   * Transfers properties from the CellDesigner plugin to the corresponding JSBML element.
   * @param pluginSBase
   * @param sbase
   */
  public static void transferNamedSBaseProperties(PluginSBase pluginSBase, NamedSBase sbase) {
    transferSBaseProperties(pluginSBase, sbase);
    if (pluginSBase instanceof PluginCompartment) {
      PluginCompartment c = (PluginCompartment) pluginSBase;
      if ((c.getId() != null) && (c.getId().length() > 0)) {
        sbase.setId(c.getId());
      }
      if ((c.getName() != null) && (c.getName().length() > 0)) {
        sbase.setName(c.getName());
      }
    } else if (pluginSBase instanceof PluginCompartmentType) {
      PluginCompartmentType c = (PluginCompartmentType) pluginSBase;
      if ((c.getId() != null) && (c.getId().length() > 0)) {
        sbase.setId(c.getId());
      }
      if ((c.getName() != null) && (c.getName().length() > 0)) {
        sbase.setName(c.getName());
      }
    } else if (pluginSBase instanceof PluginEvent) {
      PluginEvent c = (PluginEvent) pluginSBase;
      if ((c.getId() != null) && (c.getId().length() > 0)) {
        sbase.setId(c.getId());
      }
      if ((c.getName() != null) && (c.getName().length() > 0)) {
        sbase.setName(c.getName());
      }
    } else if (pluginSBase instanceof PluginFunctionDefinition) {
      PluginFunctionDefinition c = (PluginFunctionDefinition) pluginSBase;
      if ((c.getId() != null) && (c.getId().length() > 0)) {
        sbase.setId(c.getId());
      }
      if ((c.getName() != null) && (c.getName().length() > 0)) {
        sbase.setName(c.getName());
      }
    } else if (pluginSBase instanceof PluginModel) {
      PluginModel c = (PluginModel) pluginSBase;
      if ((c.getId() != null) && (c.getId().length() > 0)) {
        sbase.setId(c.getId());
      }
      if ((c.getName() != null) && (c.getName().length() > 0)) {
        sbase.setName(c.getName());
      }
    } else if (pluginSBase instanceof PluginParameter) {
      PluginParameter c = (PluginParameter) pluginSBase;
      if ((c.getId() != null) && (c.getId().length() > 0)) {
        sbase.setId(c.getId());
      }
      if ((c.getName() != null) && (c.getName().length() > 0)) {
        sbase.setName(c.getName());
      }
    } else if (pluginSBase instanceof PluginReaction) {
      PluginReaction c = (PluginReaction) pluginSBase;
      if ((c.getId() != null) && (c.getId().length() > 0)) {
        sbase.setId(c.getId());
      }
      if ((c.getName() != null) && (c.getName().length() > 0)) {
        sbase.setName(c.getName());
      }
    } else if (pluginSBase instanceof PluginSimpleSpeciesReference) {
      //      PluginSimpleSpeciesReference c = (PluginSimpleSpeciesReference) pluginSBase;
      //      if ((c.getId() != null) && (c.getId().length() > 0)) {
      //        sbase.setId(c.getId());
      //      }
      //      if ((c.getName() != null) && (c.getName().length() > 0)) {
      //        sbase.setName(c.getName());
      //    }
    } else if (pluginSBase instanceof PluginSpecies) {
      PluginSpecies c = (PluginSpecies) pluginSBase;
      if ((c.getId() != null) && (c.getId().length() > 0)) {
        sbase.setId(c.getId());
      }
      if ((c.getName() != null) && (c.getName().length() > 0)) {
        sbase.setName(c.getName());
      }
    } else if (pluginSBase instanceof PluginSpeciesType) {
      PluginSpeciesType c = (PluginSpeciesType) pluginSBase;
      if ((c.getId() != null) && (c.getId().length() > 0)) {
        sbase.setId(c.getId());
      }
      if ((c.getName() != null) && (c.getName().length() > 0)) {
        sbase.setName(c.getName());
      }
    } else if (pluginSBase instanceof PluginUnitDefinition) {
      PluginUnitDefinition c = (PluginUnitDefinition) pluginSBase;
      if ((c.getId() != null) && (c.getId().length() > 0)) {
        sbase.setId(c.getId());
      }
      if ((c.getName() != null) && (c.getName().length() > 0)) {
        sbase.setName(c.getName());
      }
    }
  }

  /**
   * Transfers all properties from the JSBML object to the corresponding CellDesigner
   * plugin object.
   * 
   * @param sbase
   * @param pluginSBase
   * @throws XMLStreamException
   */
  public static void transferNamedSBaseProperties(NamedSBase sbase, PluginSBase pluginSBase) throws XMLStreamException {
    transferSBaseProperties(sbase, pluginSBase);
    if (pluginSBase instanceof PluginCompartment) {
      PluginCompartment c = (PluginCompartment) pluginSBase;
      if ((sbase.getId() != null) && (sbase.getId().length() > 0)) {
        //        c.setId(sbase.getId());
      }
      if ((sbase.getName() != null) && (sbase.getName().length() > 0)) {
        c.setName(sbase.getName());
      }
    } else if (pluginSBase instanceof PluginCompartmentType) {
      PluginCompartmentType c = (PluginCompartmentType) pluginSBase;
      if ((sbase.getId() != null) && (sbase.getId().length() > 0)) {
        //        c.setId(sbase.getId());
      }
      if ((sbase.getName() != null) && (sbase.getName().length() > 0)) {
        c.setName(sbase.getName());
      }
    } else if (pluginSBase instanceof PluginEvent) {
      PluginEvent c = (PluginEvent) pluginSBase;
      if ((sbase.getId() != null) && (sbase.getId().length() > 0)) {
        //        c.setId(sbase.getId());
      }
      if ((sbase.getName() != null) && (sbase.getName().length() > 0)) {
        c.setName(sbase.getName());
      }
    } else if (pluginSBase instanceof PluginFunctionDefinition) {
      PluginFunctionDefinition c = (PluginFunctionDefinition) pluginSBase;
      if ((sbase.getId() != null) && (sbase.getId().length() > 0)) {
        //        c.setId(sbase.getId());
      }
      if ((sbase.getName() != null) && (sbase.getName().length() > 0)) {
        c.setName(sbase.getName());
      }
    } else if (pluginSBase instanceof PluginModel) {
      PluginModel c = (PluginModel) pluginSBase;
      if ((sbase.getId() != null) && (sbase.getId().length() > 0)) {
        //        c.setId(sbase.getId());
      }
      if ((sbase.getName() != null) && (sbase.getName().length() > 0)) {
        c.setName(sbase.getName());
      }
    } else if (pluginSBase instanceof PluginParameter) {
      PluginParameter c = (PluginParameter) pluginSBase;
      if ((sbase.getId() != null) && (sbase.getId().length() > 0)) {
        c.setId(sbase.getId());
      }
      if ((sbase.getName() != null) && (sbase.getName().length() > 0)) {
        c.setName(sbase.getName());
      }
    } else if (pluginSBase instanceof PluginReaction) {
      PluginReaction c = (PluginReaction) pluginSBase;
      if ((sbase.getId() != null) && (sbase.getId().length() > 0)) {
        //        c.setId(sbase.getId());
      }
      if ((sbase.getName() != null) && (sbase.getName().length() > 0)) {
        c.setName(sbase.getName());
      }
    } else if (pluginSBase instanceof PluginSimpleSpeciesReference) {
      //      PluginSimpleSpeciesReference c = (PluginSimpleSpeciesReference) pluginSBase;
      //      if ((sbase.getId() != null) && (sbase.getId().length() > 0)) {
      //        n.setId(c.getId());
      //      }
      //      if ((sbase.getName() != null) && (sbase.getName().length() > 0)) {
      //        c.setName(sbase.getName());
      //      }
    } else if (pluginSBase instanceof PluginSpecies) {
      //      PluginSpecies c = (PluginSpecies) pluginSBase;
      if ((sbase.getId() != null) && (sbase.getId().length() > 0)) {
        //        c.setId(sbase.getId());
      }
      if ((sbase.getName() != null) && (sbase.getName().length() > 0)) {
        //        c.setName(sbase.getName());
      }
    } else if (pluginSBase instanceof PluginSpeciesType) {
      PluginSpeciesType c = (PluginSpeciesType) pluginSBase;
      if ((sbase.getId() != null) && (sbase.getId().length() > 0)) {
        //        c.setId(sbase.getId());
      }
      if ((sbase.getName() != null) && (sbase.getName().length() > 0)) {
        c.setName(sbase.getName());
      }
    } else if (pluginSBase instanceof PluginUnitDefinition) {
      PluginUnitDefinition c = (PluginUnitDefinition) pluginSBase;
      if ((sbase.getId() != null) && (sbase.getId().length() > 0)) {
        //        c.setId(sbase.getId());
      }
      if ((sbase.getName() != null) && (sbase.getName().length() > 0)) {
        c.setName(sbase.getName());
      }
    }
  }

  /**
   * Copies all properties of the given CellDesigner object to the corresponding
   * JSBML object.
   * 
   * @param sbase
   * @param pluginSBase
   */
  public static void transferSBaseProperties(PluginSBase pluginSBase, SBase sbase) {

    // Memorize the corresponding original element for each SBase:
    sbase.putUserObject(LINK_TO_CELLDESIGNER, pluginSBase);

    String notes = pluginSBase.getNotesString();
    if ((notes != null) && (notes.length() > 0)) {
      try {
        sbase.setNotes(notes);
      } catch (XMLStreamException exc) {
        logger.warn(exc.getLocalizedMessage() != null ? exc.getLocalizedMessage() : exc.getMessage(), exc);
      }
    }
    for (int i = 0; i < pluginSBase.getNumCVTerms(); i++) {
      sbase.addCVTerm(LibSBMLUtils.convertCVTerm(pluginSBase.getCVTerm(i)));
      logger.debug("Created CVTerm " + sbase.getCVTerm(i));
    }

    if (pluginSBase.getAnnotation() != null) {
      for (int i = 0; i < pluginSBase.getNumCVTerms(); i++) {
        sbase.addCVTerm(LibSBMLUtils.convertCVTerm(pluginSBase.getCVTerm(i)));
      }
      // Parse the XML annotation nodes that are non-RDF
      XMLNode annotation = pluginSBase.getAnnotation();
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
        try {
          sbase.getAnnotation().setNonRDFAnnotation(sb.toString());
        } catch (XMLStreamException exc) {
          logger.warn(exc.getLocalizedMessage() != null ? exc.getLocalizedMessage() : exc.getMessage(), exc);
        }
      }
      sbase.getAnnotation().putUserObject(LINK_TO_LIBSBML, pluginSBase.getAnnotation());
    }
    // no MetaId accessible.
  }

  /**
   * Copies all properties of the JSBML object to the CellDesigner object.
   * 
   * @param sbase
   * @param pSBase
   * @throws XMLStreamException
   */
  public static void transferSBaseProperties(SBase sbase, PluginSBase pSBase) throws XMLStreamException {
    if (sbase.isSetMetaId()) {
      // No metaId accessible in CellDesigner
    }
    if (sbase.isSetSBOTerm()) {
      convertSBOterm(sbase, pSBase);
    }
    if (sbase.isSetNotes()) {
      pSBase.setNotes(sbase.getNotesString());
    }
    if (sbase.isSetAnnotation()) {
      if (sbase.getAnnotation().isSetNonRDFannotation()) {
        pSBase.setAnnotationString(sbase.getAnnotationString());
      }
      if (sbase.isSetHistory()) {
        // no history accessible in CellDesigner
      }
      if (sbase.getCVTermCount()  > 0) {
        if (pSBase.getNumCVTerms() > 0) {
          for (int i = pSBase.getNumCVTerms() - 1; i >= 0; i--) {
            logger.warn(MessageFormat.format(bundle.getString("CANNOT_DELETE_CVTERM"), pSBase, pSBase.getCVTerm(i)));
          }
        }
        for (CVTerm term : sbase.getCVTerms()) {
          pSBase.addCVTerm(LibSBMLUtils.convertCVTerm(term));
          term.putUserObject(LINK_TO_CELLDESIGNER, pSBase.getCVTerm(pSBase.getNumCVTerms() - 1));
        }
      }
    }
  }

  /**
   * 
   * @param sbase
   * @param pSBase
   * @return
   */
  public static boolean convertSBOterm(SBase sbase, PluginSBase pSBase) {
    // SBO term cannot be directly set.

    if (sbase instanceof ModifierSpeciesReference) {
      PluginModifierSpeciesReference pmsr = (PluginModifierSpeciesReference) pSBase;
      pmsr.setAlias(new PluginSpeciesAlias(
        (PluginSpecies) ((SimpleSpeciesReference) sbase).getSpeciesInstance().getUserObject(LINK_TO_CELLDESIGNER),
        sbase.isSetSBOTerm() ? SBO.convertSBO2Alias(sbase.getSBOTerm()) : PluginReactionSymbolType.MODULATION));
      return true;

    } else if (sbase instanceof SpeciesReference) {
      PluginSpeciesReference psr = (PluginSpeciesReference) pSBase;
      psr.setAlias(new PluginSpeciesAlias(
        (PluginSpecies) ((SimpleSpeciesReference) sbase).getSpeciesInstance().getUserObject(LINK_TO_CELLDESIGNER),
        SBO.convertSBO2Alias(sbase.getSBOTerm())));
      return true;

    }

    return false;
  }

  /**
   * 
   * @param speciesReference
   * @param pluginSpeciesReference
   * @throws XMLStreamException
   */
  public static void transferSimpleSpeciesReferenceProperties(
    SimpleSpeciesReference speciesReference,
    PluginSimpleSpeciesReference pluginSpeciesReference) throws XMLStreamException {
    transferNamedSBaseProperties(speciesReference, pluginSpeciesReference);
    if (speciesReference.isSetSpecies()) {
      pluginSpeciesReference.setAlias(new PluginSpeciesAlias(
        (PluginSpecies) speciesReference.getSpeciesInstance().getUserObject(
          LINK_TO_CELLDESIGNER), pluginSpeciesReference.getAlias().getType()));
    }
  }

  /**
   * 
   * @param speciesReference
   * @param pluginSpeciesReference
   * @throws XMLStreamException
   */
  public static void transferSpeciesReferenceProperties(SpeciesReference speciesReference,
    PluginSpeciesReference pluginSpeciesReference) throws XMLStreamException {
    transferSimpleSpeciesReferenceProperties(speciesReference, pluginSpeciesReference);
    if (speciesReference.isSetConstant()) {
      // nothing to do
    }
    if (speciesReference.isSetStoichiometry()) {
      pluginSpeciesReference.setStoichiometry(speciesReference.getStoichiometry());
    } else if (speciesReference.isSetStoichiometryMath()) {
      pluginSpeciesReference.setStoichiometryMath(LibSBMLUtils.convertStoichiometryMath(speciesReference.getStoichiometryMath()));
    }
  }

}
