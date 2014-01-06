/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2014 jointly by the following organizations:
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
package org.sbml.jsbml.cdplugin;

import static org.sbml.jsbml.cdplugin.CellDesignerConstants.LINK_TO_CELLDESIGNER;
import static org.sbml.jsbml.xml.libsbml.LibSBMLConstants.LINK_TO_LIBSBML;

import java.beans.PropertyChangeEvent;
import java.text.MessageFormat;

import javax.swing.tree.TreeNode;

import jp.sbi.celldesigner.plugin.CellDesignerPlugin;
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
import jp.sbi.celldesigner.plugin.PluginSpecies;
import jp.sbi.celldesigner.plugin.PluginSpeciesAlias;
import jp.sbi.celldesigner.plugin.PluginSpeciesReference;
import jp.sbi.celldesigner.plugin.PluginSpeciesType;
import jp.sbi.celldesigner.plugin.PluginUnit;
import jp.sbi.celldesigner.plugin.PluginUnitDefinition;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.AbstractMathContainer;
import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.AbstractNamedSBaseWithUnit;
import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.AbstractTreeNode;
import org.sbml.jsbml.AlgebraicRule;
import org.sbml.jsbml.Annotation;
import org.sbml.jsbml.AnnotationElement;
import org.sbml.jsbml.AssignmentRule;
import org.sbml.jsbml.CVTerm;
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
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.LocalParameter;
import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.ModifierSpeciesReference;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.Priority;
import org.sbml.jsbml.QuantityWithUnit;
import org.sbml.jsbml.RateRule;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.Rule;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBO;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.SimpleSpeciesReference;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.SpeciesType;
import org.sbml.jsbml.StoichiometryMath;
import org.sbml.jsbml.Symbol;
import org.sbml.jsbml.Trigger;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.Variable;
import org.sbml.jsbml.util.TreeNodeChangeEvent;
import org.sbml.jsbml.util.TreeNodeChangeListener;
import org.sbml.jsbml.util.TreeNodeRemovedEvent;
import org.sbml.jsbml.xml.XMLToken;
import org.sbml.libsbml.XMLNode;
import org.sbml.libsbml.libsbml;
import org.sbml.libsbml.libsbmlConstants;

/**
 * @author Alexander Peltzer
 * @author Andreas Dr&auml;ger
 * @version $Rev$
 * @date 10:50:22
 */
@SuppressWarnings("deprecation")
public class PluginChangeListener implements TreeNodeChangeListener {

  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(PluginChangeListener.class);

  private static final String NO_CORRESPONDING_CLASS_IN_CELLDESIGNER = "No counter class for {0} in CellDesigner.";
  private static final String COULD_NOT_SAVE_PROPERTIES_OF = "Couldn't save math properties of {0}";
  private static final String CANNOT_ADD_NODE = "Cannot add node {0}";
  private static final String CHANGING_VALUE_NOT_SUPPORTED = "Changing {0} in the Model not supported";
  private static final String CANNOT_FIRE_PROPERTY_CHANGE = "Cannot fire property change {0}";
  private static final String UNUSED_PROPERTY_CHANGE = "Unused property change {0}";
  private static final String CHANGING_VALUE_IN_MODEL_REQURES_L3 = "Changing {0} in the Model only supported with SBML Level > 2.";
  private static final String UNUSED_FUNCTION_IN_MODEL = "Unused function {0} in the Model.";
  private static final String NO_SBO_TERM_DEFINED = "No SBO term defined for {0}, using {1,number,integer}";
  private static final String CANNOT_CREATE_SPECIES_REFERENCE = "Cannot create PluginSpeciesReference due to missing species annotation.";
  private static final String PARSING_OF_NODE_UNSUCCESSFUL = "Parsing of node {0} not successful.";
  private static final String COULD_NOT_PROCESS = "Could not process {0}.";
  private static final String TRYING_TO_REMOVE_ELEMENT = "Trying to remove {0} from element {1}, but there is no remove method. Please check the result.";
  private static final String UNUSED_METHOD = "Unused method {0} in the model.";

  /**
   * A reference to the main CellDesigner plugin.
   */
  private CellDesignerPlugin plugin;

  /**
   * This listener only deals with one model, the model that is selected by the
   * user inside of CellDesigner at the moment when this object is created. All
   * other models that might be used or opened in CellDesigner at a different
   * time, will not be considered - a new instance of {@link PluginChangeListener}
   * must be created to cover these.
   */
  private PluginModel plugModel;

  /**
   * 
   * @param plugin
   */
  public PluginChangeListener(CellDesignerPlugin plugin) {
    this.plugin = plugin;
    plugModel = plugin.getSelectedModel();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeChangeListener#nodeAdded(javax.swing.tree.TreeNode)
   */
  @Override
  public void nodeAdded(TreeNode node) {
    if (node instanceof AbstractSBase) {
      if (node instanceof AbstractNamedSBase) {
        if (node instanceof CompartmentType) {
          CompartmentType ct = (CompartmentType) node;
          PluginCompartmentType pt = new PluginCompartmentType(
            ct.getId());
          if (ct.isSetName() && !pt.getName().equals(ct.getName())) {
            pt.setName(ct.getName());
            plugin.notifySBaseAdded(pt);
          } else {
            logger.log(Level.DEBUG, MessageFormat.format(CANNOT_ADD_NODE,
              node.getClass().getSimpleName()));
          }
        } else if (node instanceof UnitDefinition) {
          UnitDefinition undef = (UnitDefinition) node;
          PluginUnitDefinition plugundef = new PluginUnitDefinition(
            undef.getId());
          if (undef.isSetName()
              && !plugundef.getName().equals(undef.getName())) {
            plugundef.setName(undef.getName());
            plugin.notifySBaseAdded(plugundef);
          } else {
            logger.log(Level.DEBUG, MessageFormat.format(CANNOT_ADD_NODE,
              node.getClass().getSimpleName()));
          }
        } else if (node instanceof Reaction) {
          Reaction react = (Reaction) node;
          PluginReaction plugreac = new PluginReaction();
          if (react.isSetName()
              && !react.getName().equals(plugreac.getName())) {
            plugreac.setName(react.getName());
            plugin.notifySBaseAdded(plugreac);
          } else {
            logger.log(Level.DEBUG, MessageFormat.format(CANNOT_ADD_NODE, node.getClass().getSimpleName()));
          }
        } else if (node instanceof SpeciesType) {
          SpeciesType speciestype = (SpeciesType) node;
          PluginSpeciesType plugspectype = new PluginSpeciesType(
            speciestype.getId());
          if (speciestype.isSetName()
              && !speciestype.getName().equals(
                plugspectype.getName())) {
            plugspectype.setName(speciestype.getName());
            plugin.notifySBaseAdded(plugspectype);
          } else {
            logger.log(Level.DEBUG, MessageFormat.format(CANNOT_ADD_NODE, node.getClass().getSimpleName()));
          }
        } else if (node instanceof SimpleSpeciesReference) {
          SimpleSpeciesReference simspec = (SimpleSpeciesReference) node;
          String type = SBO.convertSBO2Alias(simspec.getSBOTerm());
          if (node instanceof ModifierSpeciesReference) {
            if (type.length() == 0) {
              // use "unknown"
              int sbo = 285;
              type = SBO.convertSBO2Alias(sbo);
              logger.log(Level.DEBUG, MessageFormat.format(NO_SBO_TERM_DEFINED, simspec.getElementName(), sbo));
            }
            if (simspec.isSetSpecies()) {
              PluginSpeciesAlias alias = new PluginSpeciesAlias(
                plugModel.getSpecies(simspec.getSpecies()),
                type);
              PluginModifierSpeciesReference plugModRef = new PluginModifierSpeciesReference(
                plugModel.getReaction(((Reaction) simspec.getParent()).getId()), alias);
              plugin.notifySBaseAdded(plugModRef);
            } else {
              logger.log(Level.DEBUG, CANNOT_CREATE_SPECIES_REFERENCE);
            }

          } else if (node instanceof SpeciesReference) {
            if (type.length() == 0) {
              // use "unknown"
              int sbo = 285;
              type = SBO.convertSBO2Alias(sbo);
              logger.log(Level.DEBUG, MessageFormat.format(
                NO_SBO_TERM_DEFINED,
                simspec.getElementName(), sbo));
            }
            // TODO: use SBML layout extension (later than JSBML
            // 0.8)
            if (simspec.isSetSpecies()) {
              PluginSpeciesAlias alias = new PluginSpeciesAlias(
                plugModel.getSpecies(simspec.getSpecies()),
                type);
              PluginSpeciesReference plugspecRef = new PluginSpeciesReference(
                plugModel.getReaction(((Reaction) simspec.getParent()).getId()), alias);
              plugin.notifySBaseAdded(plugspecRef);
            } else {
              logger.log(Level.DEBUG, CANNOT_CREATE_SPECIES_REFERENCE);
            }
          }
        } else if (node instanceof AbstractNamedSBaseWithUnit) {
          if (node instanceof Event) {
            Event event = (Event) node;
            PluginEvent plugevent = new PluginEvent(event.getId());
            if (event.isSetName()
                && !event.getName().equals(plugevent.getName())) {
              plugevent.setName(event.getName());
              plugin.notifySBaseAdded(plugevent);
            } else {
              logger.log(Level.DEBUG, MessageFormat.format(CANNOT_ADD_NODE, node.getClass().getSimpleName()));
            }
          } else if (node instanceof QuantityWithUnit) {
            if (node instanceof LocalParameter) {
              LocalParameter locparam = (LocalParameter) node;
              ListOf<LocalParameter> lop = locparam
                  .getParentSBMLObject();
              KineticLaw kl = (KineticLaw) lop
                  .getParentSBMLObject();
              for (LocalParameter p : kl
                  .getListOfLocalParameters()) {
                if (p.isSetUnits()
                    && !Unit.isUnitKind(p.getUnits(),
                      p.getLevel(), p.getVersion())
                      && plugModel.getUnitDefinition(p
                        .getUnits()) == null) {
                  PluginUnitDefinition plugUnitDefinition = new PluginUnitDefinition(
                    p.getUnitsInstance().getId());
                  plugModel
                  .addUnitDefinition(plugUnitDefinition);
                  plugin.notifySBaseAdded(plugUnitDefinition);
                }
              }

            } else if (node instanceof Symbol) {
              if (node instanceof Compartment) {
                Compartment comp = (Compartment) node;
                PluginCompartment plugcomp = new PluginCompartment(
                  comp.getCompartmentType());
                if (comp.isSetName()
                    && !plugcomp.getName().equals(
                      comp.getName())) {
                  plugcomp.setName(comp.getName());
                  plugin.notifySBaseAdded(plugcomp);
                } else {
                  logger.log(Level.DEBUG, MessageFormat.format(CANNOT_ADD_NODE, node.getClass().getSimpleName()));
                }
              } else if (node instanceof Species) {
                Species sp = (Species) node;
                PluginSpecies plugsp = new PluginSpecies(
                  sp.getSpeciesType(), sp.getName());
                if (sp.isSetName()
                    && !sp.getName().equals(
                      plugsp.getName())) {
                  plugin.notifySBaseAdded(plugsp);
                } else {
                  logger.log(Level.DEBUG, MessageFormat.format(CANNOT_ADD_NODE, node.getClass().getSimpleName()));
                }
              } else if (node instanceof org.sbml.jsbml.Parameter) {
                org.sbml.jsbml.Parameter param = (org.sbml.jsbml.Parameter) node;
                if (param.getParent() instanceof KineticLaw) {
                  PluginParameter plugparam = new PluginParameter(
                    (PluginKineticLaw) param
                    .getParent());
                  if (param.isSetName()
                      && !param.getName().equals(
                        plugparam.getName())) {
                    plugparam.setName(param.getName());
                    plugin.notifySBaseAdded(plugparam);
                  } else {
                    logger.log(Level.DEBUG, MessageFormat.format(CANNOT_ADD_NODE, node.getClass().getSimpleName()));
                  }
                } else if (param.getParent() instanceof Model) {
                  PluginParameter plugparam = new PluginParameter(
                    (PluginModel) param.getParent());
                  if (param.isSetName()
                      && !param.getName().equals(
                        plugparam.getName())) {
                    plugparam.setName(param.getName());
                    plugin.notifySBaseAdded(plugparam);
                  } else {
                    logger.log(Level.DEBUG, MessageFormat.format(CANNOT_ADD_NODE, node.getClass().getSimpleName()));
                  }
                }
              }
            }
          }
        }
      }
      if (node instanceof Unit) {
        Unit ut = (Unit) node;
        PluginUnitDefinition plugUnitDef = new PluginUnitDefinition(
          ((UnitDefinition) ut.getParentSBMLObject()).getId());
        PluginUnit plugut = new PluginUnit(plugUnitDef);
        switch (ut.getKind()) {
        case AMPERE:
          plugut.setKind(libsbmlConstants.UNIT_KIND_AMPERE);
          break;
        case BECQUEREL:
          plugut.setKind(libsbmlConstants.UNIT_KIND_BECQUEREL);
          break;
        case CANDELA:
          plugut.setKind(libsbmlConstants.UNIT_KIND_CANDELA);
          break;
        case CELSIUS:
          plugut.setKind(libsbmlConstants.UNIT_KIND_CELSIUS);
          break;
        case COULOMB:
          plugut.setKind(libsbmlConstants.UNIT_KIND_COULOMB);
          break;
        case DIMENSIONLESS:
          plugut.setKind(libsbmlConstants.UNIT_KIND_DIMENSIONLESS);
          break;
        case FARAD:
          plugut.setKind(libsbmlConstants.UNIT_KIND_FARAD);
          break;
        case GRAM:
          plugut.setKind(libsbmlConstants.UNIT_KIND_GRAM);
          break;
        case GRAY:
          plugut.setKind(libsbmlConstants.UNIT_KIND_GRAY);
          break;
        case HENRY:
          plugut.setKind(libsbmlConstants.UNIT_KIND_HENRY);
          break;
        case HERTZ:
          plugut.setKind(libsbmlConstants.UNIT_KIND_HERTZ);
          break;
        case INVALID:
          plugut.setKind(libsbmlConstants.UNIT_KIND_INVALID);
          break;
        case ITEM:
          plugut.setKind(libsbmlConstants.UNIT_KIND_ITEM);
          break;
        case JOULE:
          plugut.setKind(libsbmlConstants.UNIT_KIND_JOULE);
          break;
        case KATAL:
          plugut.setKind(libsbmlConstants.UNIT_KIND_KATAL);
          break;
        case KELVIN:
          plugut.setKind(libsbmlConstants.UNIT_KIND_KELVIN);
          break;
        case KILOGRAM:
          plugut.setKind(libsbmlConstants.UNIT_KIND_KILOGRAM);
          break;
        case LITER:
          plugut.setKind(libsbmlConstants.UNIT_KIND_LITER);
          break;
        case LITRE:
          plugut.setKind(libsbmlConstants.UNIT_KIND_LITRE);
          break;
        case LUMEN:
          plugut.setKind(libsbmlConstants.UNIT_KIND_LUMEN);
          break;
        case LUX:
          plugut.setKind(libsbmlConstants.UNIT_KIND_LUX);
          break;
        case METER:
          plugut.setKind(libsbmlConstants.UNIT_KIND_METER);
          break;
        case METRE:
          plugut.setKind(libsbmlConstants.UNIT_KIND_METRE);
          break;
        case MOLE:
          plugut.setKind(libsbmlConstants.UNIT_KIND_MOLE);
          break;
        case NEWTON:
          plugut.setKind(libsbmlConstants.UNIT_KIND_NEWTON);
          break;
        case OHM:
          plugut.setKind(libsbmlConstants.UNIT_KIND_OHM);
          break;
        case PASCAL:
          plugut.setKind(libsbmlConstants.UNIT_KIND_PASCAL);
          break;
        case RADIAN:
          plugut.setKind(libsbmlConstants.UNIT_KIND_RADIAN);
          break;
        case SECOND:
          plugut.setKind(libsbmlConstants.UNIT_KIND_SECOND);
          break;
        case SIEMENS:
          plugut.setKind(libsbmlConstants.UNIT_KIND_SIEMENS);
          break;
        case SIEVERT:
          plugut.setKind(libsbmlConstants.UNIT_KIND_SIEVERT);
          break;
        case STERADIAN:
          plugut.setKind(libsbmlConstants.UNIT_KIND_STERADIAN);
          break;
        case TESLA:
          plugut.setKind(libsbmlConstants.UNIT_KIND_TESLA);
          break;
        case VOLT:
          plugut.setKind(libsbmlConstants.UNIT_KIND_VOLT);
          break;
        case WATT:
          plugut.setKind(libsbmlConstants.UNIT_KIND_WATT);
          break;
        case WEBER:
          plugut.setKind(libsbmlConstants.UNIT_KIND_WEBER);
          break;
        case AVOGADRO:
          plugut.setKind(libsbmlConstants.UNIT_KIND_AVOGADRO);
          break;
        default:
          break;
        }
        plugut.setExponent((int) Math.round(ut.getExponent()));
        plugut.setMultiplier(ut.getMultiplier());
        plugut.setOffset(ut.getOffset());
        plugut.setScale(ut.getScale());
        plugin.notifySBaseAdded(plugut);
      } else if (node instanceof SBMLDocument) {
        logger.log(Level.DEBUG, MessageFormat.format(NO_CORRESPONDING_CLASS_IN_CELLDESIGNER, node.getClass().getSimpleName()));
      } else if (node instanceof ListOf<?>) {
        ListOf<?> listOf = (ListOf<?>) node;
        //PluginListOf pluli = new PluginListOf();
        //PluginReaction ro = plugModel.getReaction(((Reaction) listOf.getParentSBMLObject()).getId());
        switch (listOf.getSBaseListType()) {
        //TODO This has to be fixed somehow, the usual way like in PluginSBMLWriter does not work at all....
        case listOfCompartments:
        case listOfCompartmentTypes:
          break;
        case listOfConstraints:
          break;
        case listOfEventAssignments:
          break;
        case listOfEvents:
          break;
        case listOfFunctionDefinitions:
          break;
        case listOfInitialAssignments:
          break;
        case listOfLocalParameters:
          break;
        case listOfModifiers:
          break;
        case listOfParameters:
          break;
        case listOfProducts:
          break;
        case listOfReactants:
          break;
        case listOfReactions:
          break;
        case listOfRules:
          break;
        case listOfSpecies:
          break;
        case listOfSpeciesTypes:
          break;
        case listOfUnitDefinitions:
          break;
        case listOfUnits:
          break;
        case other:
          // TODO for JSBML packages (later than 0.8).
        default:
          // unknown
          break;
        }

      } else if (node instanceof AbstractMathContainer) {
        if (node instanceof FunctionDefinition) {
          FunctionDefinition funcdef = (FunctionDefinition) node;
          PluginFunctionDefinition plugfuncdef = new PluginFunctionDefinition(
            funcdef.getId());
          if (funcdef.isSetName()
              && !plugfuncdef.getName().equals(funcdef.getName())) {
            plugfuncdef.setName(funcdef.getName());
            plugin.notifySBaseAdded(plugfuncdef);
          } else {
            logger.log(Level.DEBUG, MessageFormat.format(CANNOT_ADD_NODE, node.getClass().getSimpleName()));
          }
        } else if (node instanceof KineticLaw) {
          KineticLaw klaw = (KineticLaw) node;
          Reaction parentreaction = klaw.getParentSBMLObject();
          PluginKineticLaw plugklaw = plugModel.getReaction(
            parentreaction.getId()).getKineticLaw();
          PluginReaction plugreac = plugModel
              .getReaction(parentreaction.getId());
          plugreac.setKineticLaw(plugklaw);
          plugin.notifySBaseAdded(plugreac);
        } else if (node instanceof InitialAssignment) {
          InitialAssignment iAssign = (InitialAssignment) node;
          PluginInitialAssignment plugiassign = new PluginInitialAssignment(
            iAssign.getSymbol());
          plugiassign.setMath(iAssign.getMathMLString());
          plugiassign.setNotes(iAssign.getNotesString());
          plugin.notifySBaseAdded(plugiassign);
        } else if (node instanceof EventAssignment) {
          EventAssignment ea = (EventAssignment) node;
          PluginEventAssignment pea = (PluginEventAssignment) ea.getUserObject(LINK_TO_CELLDESIGNER);
          pea.setMath(PluginUtils.convert(ea.getMath()));
          pea.setNotes(ea.getNotesString());
          plugin.notifySBaseAdded(pea);
        } else if (node instanceof StoichiometryMath) {
          logger.log(Level.DEBUG, MessageFormat.format(
            NO_CORRESPONDING_CLASS_IN_CELLDESIGNER, node
            .getClass().getSimpleName()));
        } else if (node instanceof Trigger) {
          Trigger trig = (Trigger) node;
          PluginEvent plugEvent = new PluginEvent(trig.getParent()
            .getId());
          plugEvent.setTrigger(PluginUtils.convert(trig.getMath()));
          plugin.notifySBaseAdded(plugEvent);
        } else if (node instanceof Constraint) {
          Constraint ct = (Constraint) node;
          PluginConstraint plugct = new PluginConstraint(
            ct.getMathMLString());
          plugct.setMessage(ct.getMessageString());
          plugct.setNotes(ct.getNotesString());
          plugin.notifySBaseAdded(plugct);
        } else if (node instanceof Delay) {
          Delay dl = (Delay) node;
          PluginEvent plugEvent = new PluginEvent(dl.getParent()
            .getId());
          plugEvent.setDelay(PluginUtils.convert(dl.getMath()));
          plugin.notifySBaseAdded(plugEvent);
        } else if (node instanceof Priority) {
          logger.log(Level.DEBUG, MessageFormat.format(
            NO_CORRESPONDING_CLASS_IN_CELLDESIGNER, node
            .getClass().getSimpleName()));
        } else if (node instanceof Rule) {
          if (node instanceof AlgebraicRule) {
            AlgebraicRule alrule = (AlgebraicRule) node;
            PluginAlgebraicRule plugalrule = new PluginAlgebraicRule(
              plugModel);
            plugalrule
            .setMath(PluginUtils.convert(alrule.getMath()));
            plugin.notifySBaseAdded(plugalrule);
          } else if (node instanceof ExplicitRule) {
            if (node instanceof RateRule) {
              RateRule rule = (RateRule) node;
              PluginRateRule plugraterule = new PluginRateRule(
                plugModel);

              plugraterule.setMath(PluginUtils.convert(rule
                .getMath()));
              plugraterule.setVariable(rule.getVariable());
              plugraterule.setNotes(rule.getNotes().getName());
              plugin.notifySBaseAdded(plugraterule);

            } else if (node instanceof AssignmentRule) {
              AssignmentRule assignRule = (AssignmentRule) node;
              PluginAssignmentRule plugassignRule = new PluginAssignmentRule(
                plugModel);

              plugassignRule.setL1TypeCode(assignRule.getLevel());
              plugassignRule.setMath(PluginUtils
                .convert(assignRule.getMath()));
              plugassignRule
              .setVariable(assignRule.getVariable());
              plugassignRule
              .setNotes(assignRule.getNotesString());
              plugin.notifySBaseAdded(plugassignRule);
            }
          } else {
            Rule rule = (Rule) node;
            PluginRule plugrule = new PluginRule();
            plugrule.setMath(PluginUtils.convert(rule.getMath()));
            plugrule.setNotes(rule.getNotesString());
            plugin.notifySBaseAdded(plugrule);
          }
        }
      }
    } else if (node instanceof AbstractTreeNode) {
      if (node instanceof XMLToken) {
        if (node instanceof XMLNode) {
          logger.log(Level.DEBUG, MessageFormat.format(PARSING_OF_NODE_UNSUCCESSFUL, node.getClass().getSimpleName()));
        }
      } else if (node instanceof ASTNode) {
        logger.log(Level.DEBUG, MessageFormat.format(PARSING_OF_NODE_UNSUCCESSFUL, node.getClass().getSimpleName()));
      } else if (node instanceof AnnotationElement) {
        if (node instanceof CVTerm) {
          //TODO this has to be done using libsbml - I couldn't get libsbml to run on my machine however :/
        } else if (node instanceof History) {
          logger.log(Level.DEBUG, MessageFormat.format(NO_CORRESPONDING_CLASS_IN_CELLDESIGNER, node.getClass().getSimpleName()));
        } else if (node instanceof Creator) {
          logger.log(Level.DEBUG, MessageFormat.format(NO_CORRESPONDING_CLASS_IN_CELLDESIGNER, node.getClass().getSimpleName()));
        } else {
          logger.warn(MessageFormat.format(COULD_NOT_PROCESS, node.toString()));
        }
      }
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeChangeListener#nodeRemoved(org.sbml.jsbml.util.TreeNodeRemovedEvent)
   */
  @Override
  public void nodeRemoved(TreeNodeRemovedEvent evt) {
    TreeNode node = evt.getSource();
    TreeNode parent = evt.getPreviousParent();
    if (node instanceof AbstractSBase) {
      if (node instanceof AbstractNamedSBase) {
        if (node instanceof CompartmentType) {
          CompartmentType ct = (CompartmentType) node;
          PluginCompartmentType pt = plugModel.getCompartmentType(ct.getId());
          plugModel.removeCompartmentType(ct.getId());
          plugin.notifySBaseDeleted(pt);
        } else if (node instanceof UnitDefinition) {
          UnitDefinition undef = (UnitDefinition) node;
          PluginUnitDefinition plugUndef = plugModel.getUnitDefinition(undef.getId());
          plugModel.removeUnitDefinition(undef.getId());
          plugin.notifySBaseDeleted(plugUndef);
        } else if (node instanceof Reaction) {
          Reaction react = (Reaction) node;
          PluginReaction preac = plugModel.getReaction(react.getId());
          plugModel.removeReaction(react.getId());
          plugin.notifySBaseDeleted(preac);
        } else if (node instanceof SpeciesType) {
          SpeciesType speciestype = (SpeciesType) node;
          PluginSpeciesType pspec = plugModel
              .getSpeciesType(speciestype.getId());
          plugModel.removeSpeciesType(pspec);
          plugin.notifySBaseDeleted(pspec);
        } else if (node instanceof SimpleSpeciesReference) {
          SimpleSpeciesReference simspec = (SimpleSpeciesReference) node;
          String type = SBO.convertSBO2Alias(simspec.getSBOTerm());
          if (node instanceof ModifierSpeciesReference) {
            ModifierSpeciesReference modSpecRef = (ModifierSpeciesReference) node;
            PluginSpeciesAlias alias = plugModel.getSpecies(
              modSpecRef.getId()).getSpeciesAlias(type);
            PluginModifierSpeciesReference ref = new PluginModifierSpeciesReference(
              plugModel.getReaction(simspec.getId()), alias);
            plugModel.getReaction(simspec.getId()).removeModifier(
              ref);
            plugin.notifySBaseDeleted(ref);
          } else if (node instanceof SpeciesReference) {
            SpeciesReference specref = (SpeciesReference) node;
            PluginSpeciesAlias alias = plugModel.getSpecies(
              specref.getId()).getSpeciesAlias(type);
            PluginSpeciesReference ref = new PluginSpeciesReference(
              plugModel.getReaction(simspec.getId()), alias);
            plugModel.getReaction(simspec.getId()).removeProduct(
              ref);
            plugin.notifySBaseDeleted(ref);
          }
        } else if (node instanceof AbstractNamedSBaseWithUnit) {
          if (node instanceof Event) {
            Event event = (Event) node;
            PluginEvent plugEvent = plugModel.getEvent(event
              .getId());
            plugModel.removeEvent(event.getId());
            plugin.notifySBaseDeleted(plugEvent);
          } else if (node instanceof QuantityWithUnit) {
            if (node instanceof LocalParameter) {
              LocalParameter loc = (LocalParameter) node;
              PluginParameter ppam = plugModel.getParameter(loc.getId());
              plugModel.removeParameter(loc.getId());
              plugin.notifySBaseDeleted(ppam);
            } else if (node instanceof Symbol) {
              if (node instanceof Compartment) {
                Compartment comp = (Compartment) node;
                PluginCompartment plugComp = plugModel
                    .getCompartment(comp.getId());
                plugModel.removeCompartment(comp.getId());
                plugin.notifySBaseDeleted(plugComp);
              } else if (node instanceof Species) {
                Species sp = (Species) node;
                PluginSpecies ps = plugModel.getSpecies(sp
                  .getId());
                plugModel.removeSpecies(sp.getId());
                plugin.notifySBaseDeleted(ps);
              } else if (node instanceof org.sbml.jsbml.Parameter) {
                org.sbml.jsbml.Parameter param = (org.sbml.jsbml.Parameter) node;
                PluginParameter plugParam = plugModel
                    .getParameter(param.getId());
                plugModel.removeParameter(param.getId());
                plugin.notifySBaseDeleted(plugParam);
              }
            }
          }
        }
      }
      if (node instanceof Unit) {
        //TODO Units can not be accessed this easily
      } else if (node instanceof SBMLDocument) {
        // Check if we can access the SBMLDocument. In my opinion we can't access this due to a limitation of CellDesigner.
      } else if (node instanceof ListOf<?>) {
        //TODO How to parse the lists -- same problem as in nodeAddedMethod, can be probably outsourced into a common method for generic ListOf<?>
        ListOf<?> listOf = (ListOf<?>) node;
        switch (listOf.getSBaseListType()) {
        case listOfCompartments:
          break;
        case listOfCompartmentTypes:
          break;
        case listOfConstraints:
          break;
        case listOfEventAssignments:
          break;
        case listOfEvents:
          break;
        case listOfFunctionDefinitions:
          break;
        case listOfInitialAssignments:
          break;
        case listOfLocalParameters:
          break;
        case listOfModifiers:
          break;
        case listOfParameters:
          break;
        case listOfProducts:
          break;
        case listOfReactants:
          break;
        case listOfReactions:
          break;
        case listOfRules:
          break;
        case listOfSpecies:
          break;
        case listOfSpeciesTypes:
          break;
        case listOfUnitDefinitions:
          break;
        case listOfUnits:
          break;
        case other:
          // TODO for JSBML packages (later than 0.8).
        default:
          // unknown
          break;
        }

      } else if (node instanceof AbstractMathContainer) {
        if (node instanceof FunctionDefinition) {
          FunctionDefinition funcdef = (FunctionDefinition) node;
          PluginFunctionDefinition plugFuncdef = plugModel
              .getFunctionDefinition(funcdef.getId());
          plugModel.removeFunctionDefinition(funcdef.getId());
          plugin.notifySBaseDeleted(plugFuncdef);
        } else if (node instanceof KineticLaw) {
          KineticLaw klaw = (KineticLaw) node;
          PluginReaction plugReac = (PluginReaction) klaw.getParent().getUserObject(LINK_TO_CELLDESIGNER);
          PluginKineticLaw plugklaw = plugReac.getKineticLaw();
          plugReac.setKineticLaw(null);
          plugin.notifySBaseDeleted(plugklaw);
        } else if (node instanceof InitialAssignment) {
          InitialAssignment iAssign = (InitialAssignment) node;
          PluginInitialAssignment plugiAssign = plugModel
              .getInitialAssignment(iAssign.getSymbol());
          plugModel.removeInitialAssignment(plugiAssign);
          plugin.notifySBaseDeleted(plugiAssign);
        } else if (node instanceof EventAssignment) {
          EventAssignment eAssign = (EventAssignment) node;
          ListOf<EventAssignment> elist = (ListOf<EventAssignment>) parent;
          Event e = (Event) elist.getParentSBMLObject();
          PluginEventAssignment plugEventAssignment = plugModel.getEvent(e.getId()).getEventAssignment(eAssign.getIndex(node));
          plugin.notifySBaseDeleted(plugEventAssignment);
        } else if (node instanceof StoichiometryMath) {
          logger.log(Level.DEBUG, MessageFormat.format(
            NO_CORRESPONDING_CLASS_IN_CELLDESIGNER, node
            .getClass().getSimpleName()));
        } else if (node instanceof Trigger) {
          Trigger trig = (Trigger) node;
          PluginEvent plugEvent = plugModel.getEvent(((Event) parent).getId());
          logger.log(Level.DEBUG, MessageFormat.format(TRYING_TO_REMOVE_ELEMENT, trig.getElementName(), plugEvent.getId()));
          plugEvent.setTrigger((org.sbml.libsbml.Trigger) null);
          plugin.notifySBaseChanged(plugEvent);
        } else if (node instanceof Constraint) {
          Constraint ct = (Constraint) node;
          PluginConstraint plugct = plugModel.getConstraint(ct
            .getMathMLString());
          plugModel.removeConstraint(ct.getMathMLString());
          plugin.notifySBaseDeleted(plugct);
        } else if (node instanceof Delay) {
          Delay dl = (Delay) node;
          PluginEvent plugEvent = (PluginEvent) dl.getUserObject(LINK_TO_CELLDESIGNER);
          Delay dlnew = new Delay();
          plugEvent.setDelay(PluginUtils.convert(dlnew.getMath()));
          plugin.notifySBaseChanged(plugEvent);
        } else if (node instanceof Priority) {
          logger.log(Level.DEBUG, MessageFormat.format(
            NO_CORRESPONDING_CLASS_IN_CELLDESIGNER, node
            .getClass().getSimpleName()));
        } else if (node instanceof Rule) {
          if (node instanceof AlgebraicRule) {
            AlgebraicRule alrule = (AlgebraicRule) node;
            //TODO How to find the corresponding element in the model to remove it?
          } else if (node instanceof ExplicitRule) {
            if (node instanceof RateRule) {
              RateRule rrule = (RateRule) node;
              //TODO How to find the corresponding element in the model to remove it?
            } else if (node instanceof AssignmentRule) {
              AssignmentRule assignRule = (AssignmentRule) node;
              //TODO How to find the corresponding element in the model to remove it?
            }
          }
        }
      }
    } else if (node instanceof AbstractTreeNode) {
      if (node instanceof XMLToken) {
        if (node instanceof XMLNode) {
          logger.log(Level.DEBUG, MessageFormat.format(PARSING_OF_NODE_UNSUCCESSFUL, node.getClass().getSimpleName()));
        }
      } else if (node instanceof ASTNode) {
        logger.log(Level.DEBUG, MessageFormat.format(PARSING_OF_NODE_UNSUCCESSFUL, node.getClass().getSimpleName()));
      } else if (node instanceof AnnotationElement) {
        if (node instanceof CVTerm) {
          CVTerm term = (CVTerm) node;
          // TODO use libsbml to get the corresponding CVTerm
        } else if (node instanceof History) {
          logger.log(Level.DEBUG, MessageFormat.format(NO_CORRESPONDING_CLASS_IN_CELLDESIGNER, node.getClass().getSimpleName()));
        } else if (node instanceof Creator) {
          logger.log(Level.DEBUG, MessageFormat.format(NO_CORRESPONDING_CLASS_IN_CELLDESIGNER, node.getClass().getSimpleName()));
        }
      }
    }
  }

  /* (non-Javadoc)
   * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
   */
  @Override
  public void propertyChange(PropertyChangeEvent event) {
    Object eventsource = event.getSource();
    String prop = event.getPropertyName();
    if (prop.equals(TreeNodeChangeEvent.addCVTerm)) {
      Annotation anno = (Annotation) eventsource;
      CVTerm term = event.getNewValue() != null? (CVTerm) event.getNewValue(): null;
      // TODO: add CVTerm to Plugin element
      //anno.addCVTerm(term);
    } else if (prop.equals(TreeNodeChangeEvent.addExtension)) {
      logger.log(Level.DEBUG, MessageFormat.format(CHANGING_VALUE_NOT_SUPPORTED, eventsource.getClass().getSimpleName()));
      //    } else if (prop.equals(TreeNodeChangeEvent.addNamespace)) {
      //      logger.log(Level.DEBUG, MessageFormat.format(CHANGING_VALUE_NOT_SUPPORTED, eventsource.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.annotation)) {
      logger.log(Level.DEBUG, MessageFormat.format(CHANGING_VALUE_NOT_SUPPORTED, eventsource.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.annotationNameSpaces)) {
      logger.log(Level.DEBUG, MessageFormat.format(CHANGING_VALUE_NOT_SUPPORTED, eventsource.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.areaUnits)) {
      logger.log(Level.DEBUG, MessageFormat.format(CHANGING_VALUE_IN_MODEL_REQURES_L3, eventsource.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.baseListType)) {
      //Method is only called in creating a new List, which means we don't need to update anything here.
    } else if (prop.equals(TreeNodeChangeEvent.boundaryCondition)) {
      Species species = (Species) eventsource;
      PluginSpecies plugSpec = plugModel.getSpecies(species.getId());
      plugSpec.setBoundaryCondition(species.getBoundaryCondition());
      plugin.notifySBaseChanged(plugSpec);
    } else if (prop.equals(TreeNodeChangeEvent.charge)) {
      Species species = (Species) eventsource;
      PluginSpecies plugSpec = plugModel.getSpecies(species.getId());
      plugSpec.setCharge(species.getCharge());
      plugin.notifySBaseChanged(plugSpec);
    } else if (prop.equals(TreeNodeChangeEvent.childNode)) {
      ASTNode node = (ASTNode) eventsource;
      MathContainer mc = node.getParentSBMLObject();
      mc.setMath((ASTNode) event.getNewValue());
      plugin.notifySBaseChanged((PluginSBase) mc.getUserObject(LINK_TO_CELLDESIGNER));
    } else if (prop.equals(TreeNodeChangeEvent.className)) {
      ASTNode node = (ASTNode) eventsource;
      MathContainer mc = node.getParentSBMLObject();
      mc.setMath((ASTNode) event.getNewValue());
      plugin.notifySBaseChanged((PluginSBase) mc.getUserObject(LINK_TO_CELLDESIGNER));
    } else if (prop.equals(TreeNodeChangeEvent.compartment)) {
      if (eventsource instanceof Species) {
        Species spec = (Species) eventsource;
        PluginSpecies plugSpecies = plugModel.getSpecies(spec.getId());
        plugSpecies.setCompartment(spec.getCompartment());
        plugin.notifySBaseChanged(plugSpecies);
      } else if (eventsource instanceof Reaction) {
        logger.log(Level.DEBUG, MessageFormat.format(CHANGING_VALUE_IN_MODEL_REQURES_L3, eventsource.getClass().getSimpleName()));
      }
    } else if (prop.equals(TreeNodeChangeEvent.compartmentType)) {
      Compartment c = (Compartment) eventsource;
      PluginCompartment pc = plugModel.getCompartment(c.getId());
      pc.setCompartmentType(c.getCompartmentType());
      plugin.notifySBaseChanged(pc);
    } else if (prop.equals(TreeNodeChangeEvent.constant)) {
      if (event.getSource() instanceof SpeciesReference) {
        logger.log(Level.DEBUG, MessageFormat.format(CHANGING_VALUE_IN_MODEL_REQURES_L3, eventsource.getClass().getSimpleName()));
      } else if (event.getSource() instanceof Symbol) {
        if (eventsource instanceof Compartment) {
          Compartment c = (Compartment) eventsource;
          PluginCompartment pc = plugModel.getCompartment(c.getId());
          pc.setConstant(c.getConstant());
          plugin.notifySBaseChanged(pc);
        } else if (eventsource instanceof Parameter) {
          Parameter p = (Parameter) eventsource;
          PluginParameter pp = plugModel.getParameter(p.getId());
          pp.setConstant(p.getConstant());
          plugin.notifySBaseChanged(pp);
        } else if (eventsource instanceof Species) {
          Species sp = (Species) eventsource;
          PluginSpecies pspec = plugModel.getSpecies(sp.getId());
          pspec.setConstant(sp.getConstant());
          plugin.notifySBaseChanged(pspec);
        }
      }
    } else if (prop.equals(TreeNodeChangeEvent.conversionFactor)) {
      logger.log(Level.DEBUG, MessageFormat.format(CHANGING_VALUE_IN_MODEL_REQURES_L3, eventsource.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.createdDate)) {
      logger.log(Level.DEBUG, MessageFormat.format(CHANGING_VALUE_NOT_SUPPORTED, eventsource.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.creator)) {
      logger.log(Level.DEBUG, MessageFormat.format(CHANGING_VALUE_NOT_SUPPORTED, eventsource.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.currentList)) {
      logger.log(Level.DEBUG, MessageFormat.format(UNUSED_PROPERTY_CHANGE, event.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.definitionURL)) {
      ASTNode node = (ASTNode) eventsource;
      ASTNode currNode = (ASTNode) node.getUserObject(LINK_TO_LIBSBML);
      currNode.setDefinitionURL((String) event.getNewValue());
      plugin.notifySBaseChanged((PluginSBase) node.getParentSBMLObject().getUserObject(LINK_TO_CELLDESIGNER));
    } else if (prop.equals(TreeNodeChangeEvent.denominator)) {
      if (eventsource instanceof SpeciesReference) {
        //PluginSpeciesReference does not allow the setting of a Denominator
      } else {
        logger.log(Level.DEBUG, MessageFormat.format(CHANGING_VALUE_NOT_SUPPORTED, event.getClass().getSimpleName()));
      }
    } else if (prop.equals(TreeNodeChangeEvent.email)) {
      logger.log(Level.DEBUG, MessageFormat.format(CHANGING_VALUE_NOT_SUPPORTED, eventsource.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.encoding)) {
      ASTNode n = (ASTNode) eventsource;
      MathContainer mc = n.getParentSBMLObject();
      n.setEncoding((String) event.getNewValue());
      plugin.notifySBaseChanged((PluginSBase) mc.getUserObject(LINK_TO_CELLDESIGNER));
    } else if (prop.equals(TreeNodeChangeEvent.exponent)) {
      if (event.getSource() instanceof ASTNode) {
        ASTNode node = (ASTNode) eventsource;
        MathContainer mc = node.getParentSBMLObject();
        mc.setMath((ASTNode) event.getNewValue());
        plugin.notifySBaseChanged((PluginSBase) mc.getUserObject(LINK_TO_CELLDESIGNER));
      } else if (event.getSource() instanceof Unit) {
        Unit ut = (Unit) event.getSource();
        PluginUnit pluginUnit = (PluginUnit) ut.getUserObject(LINK_TO_CELLDESIGNER);
        pluginUnit.setExponent((Integer) event.getNewValue());
        plugin.notifySBaseChanged(pluginUnit);
      }
    } else if (prop.equals(TreeNodeChangeEvent.extentUnits)) {
      logger.log(Level.DEBUG, MessageFormat.format(CHANGING_VALUE_IN_MODEL_REQURES_L3, eventsource.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.familyName)) {
      logger.log(Level.DEBUG, MessageFormat.format(CHANGING_VALUE_NOT_SUPPORTED, eventsource.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.fast)) {
      Reaction r = (Reaction) eventsource;
      PluginReaction plugR = plugModel.getReaction(r.getId());
      plugR.setFast(r.getFast());
      plugin.notifySBaseChanged(plugR);
    } else if (prop.equals(TreeNodeChangeEvent.formula)) {
      logger.log(Level.DEBUG, MessageFormat.format(CANNOT_FIRE_PROPERTY_CHANGE, event.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.givenName)) {
      logger.log(Level.DEBUG, MessageFormat.format(CHANGING_VALUE_NOT_SUPPORTED, eventsource.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.hasOnlySubstanceUnits)) {
      Species spec = (Species) eventsource;
      PluginSpecies plugSpec = plugModel.getSpecies(spec.getId());
      plugSpec.setHasOnlySubstanceUnits(spec.getHasOnlySubstanceUnits());
      plugin.notifySBaseChanged(plugSpec);
    } else if (prop.equals(TreeNodeChangeEvent.history)) {
      logger.log(Level.DEBUG, MessageFormat.format(CHANGING_VALUE_NOT_SUPPORTED, eventsource.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.id)) {
      //This is unused in  this form
    } else if (prop.equals(TreeNodeChangeEvent.initialAmount)) {
      Species spec = (Species) eventsource;
      PluginSpecies plugSpec = plugModel.getSpecies(spec.getId());
      plugSpec.setInitialAmount(spec.getInitialAmount());
      plugSpec.setInitialConcentration(spec.getInitialConcentration());
    } else if (prop.equals(TreeNodeChangeEvent.initialValue)) {
      if (eventsource instanceof Trigger) {
        logger.log(Level.DEBUG, MessageFormat.format(CHANGING_VALUE_IN_MODEL_REQURES_L3, eventsource.getClass().getSimpleName()));
      } else if (eventsource instanceof ASTNode) {
        logger.log(Level.DEBUG, MessageFormat.format(CHANGING_VALUE_IN_MODEL_REQURES_L3, eventsource.getClass().getSimpleName()));
      }
    } else if (prop.equals(TreeNodeChangeEvent.isEOF)) {
      logger.log(Level.DEBUG, MessageFormat.format(CANNOT_FIRE_PROPERTY_CHANGE, event.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.isExplicitlySetConstant)) {
      LocalParameter lpam = (LocalParameter) eventsource;
      PluginParameter ppam = (PluginParameter) lpam.getUserObject(LINK_TO_CELLDESIGNER);
      plugin.notifySBaseChanged(ppam);
    } else if (prop.equals(TreeNodeChangeEvent.isSetNumberType)) {
      ASTNode n = (ASTNode) eventsource;
      MathContainer mc = n.getParentSBMLObject();
      n.setIsSetNumberType((Boolean) event.getNewValue());
      plugin.notifySBaseChanged((PluginSBase) mc.getParentSBMLObject());
    } else if (prop.equals(TreeNodeChangeEvent.kind)) {
      Unit ut = (Unit) eventsource;
      PluginUnit plugUnit = (PluginUnit) ut.getUserObject(LINK_TO_CELLDESIGNER);
      //the new value can be either a string or an integer ==> therefore this separation is necessary
      if (event.getNewValue() instanceof String) {
        plugUnit.setKind((String) event.getNewValue());
      } else {
        plugUnit.setKind((Integer) event.getNewValue());
      }
    } else if (prop.equals(TreeNodeChangeEvent.kineticLaw)) {
      logger.log(Level.DEBUG, MessageFormat.format(CANNOT_FIRE_PROPERTY_CHANGE, event.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.lengthUnits)) {
      logger.log(Level.DEBUG, MessageFormat.format(CHANGING_VALUE_IN_MODEL_REQURES_L3, eventsource.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.level)) {
      AbstractSBase base = (AbstractSBase) eventsource;
      base.setLevel((Integer) event.getNewValue());
      PluginSBase pbase = (PluginSBase) base.getUserObject(LINK_TO_CELLDESIGNER);
      plugin.notifySBaseChanged(pbase);
    } else if (prop.equals(TreeNodeChangeEvent.listOfUnits)) {
      logger.log(Level.DEBUG, MessageFormat.format(UNUSED_FUNCTION_IN_MODEL, eventsource.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.mantissa)) {
      ASTNode n = (ASTNode) eventsource;
      n.setValue((Double) event.getNewValue());
      MathContainer mc = n.getParentSBMLObject();
      plugin.notifySBaseChanged((PluginSBase) mc.getUserObject(LINK_TO_CELLDESIGNER));
    } else if (prop.equals(TreeNodeChangeEvent.math)) {
      MathContainer mathContainer = (MathContainer) event.getSource();
      saveMathContainerProperties(mathContainer);
    } else if (prop.equals(TreeNodeChangeEvent.message)) {
      logger.log(Level.DEBUG, MessageFormat.format(CANNOT_FIRE_PROPERTY_CHANGE, event.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.messageBuffer)) {
      logger.log(Level.DEBUG, MessageFormat.format(CANNOT_FIRE_PROPERTY_CHANGE, event.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.metaId)) {
      logger.log(Level.DEBUG, MessageFormat.format(CANNOT_FIRE_PROPERTY_CHANGE, event.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.model)) {
      logger.log(Level.DEBUG, MessageFormat.format(CHANGING_VALUE_NOT_SUPPORTED, eventsource.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.modifiedDate)) {
      logger.log(Level.DEBUG, MessageFormat.format(CANNOT_FIRE_PROPERTY_CHANGE, eventsource.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.multiplier)) {
      Unit u = (Unit) eventsource;
      UnitDefinition ud = (UnitDefinition) u.getParentSBMLObject().getParentSBMLObject();
      PluginUnitDefinition pud = plugModel.getUnitDefinition(ud.getId());
      u.setMultiplier((Double) event.getNewValue());
      plugin.notifySBaseChanged(pud);
    } else if (prop.equals(TreeNodeChangeEvent.name)) {
      if (event.getSource() instanceof FunctionDefinition) {
        FunctionDefinition funcDef = (FunctionDefinition) event.getSource();
        PluginFunctionDefinition plugFuncDef = plugModel.getFunctionDefinition(funcDef.getId());
        plugFuncDef.setName(funcDef.getName());
        plugin.notifySBaseChanged(plugFuncDef);
      }
    } else if (prop.equals(TreeNodeChangeEvent.namespace)) {
      logger.log(Level.DEBUG, MessageFormat.format(CANNOT_FIRE_PROPERTY_CHANGE, event.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.nonRDFAnnotation)) {
      logger.log(Level.DEBUG, MessageFormat.format(CHANGING_VALUE_NOT_SUPPORTED, eventsource.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.notes)) {
      logger.log(Level.DEBUG, MessageFormat.format(CANNOT_FIRE_PROPERTY_CHANGE, event.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.notesBuffer)) {
      logger.log(Level.DEBUG, MessageFormat.format(CANNOT_FIRE_PROPERTY_CHANGE, event.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.numerator)) {
      ASTNode n = (ASTNode) eventsource;
      n.setValue((Double) event.getNewValue());
      MathContainer mc = n.getParentSBMLObject();
      plugin.notifySBaseChanged((PluginSBase) mc.getUserObject(LINK_TO_CELLDESIGNER));
    } else if (prop.equals(TreeNodeChangeEvent.offset)) {
      Unit u = (Unit) eventsource;
      PluginUnit plugU = (PluginUnit) u.getUserObject(LINK_TO_CELLDESIGNER);
      plugU.setOffset((Double) event.getNewValue());
      plugin.notifySBaseChanged(plugU);
    } else if (prop.equals(TreeNodeChangeEvent.organization)) {
      logger.log(Level.DEBUG, MessageFormat.format(CHANGING_VALUE_NOT_SUPPORTED, eventsource.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.outside)) {
      Compartment c = (Compartment) event.getSource();
      PluginCompartment plugC = plugModel.getCompartment(c.getId());
      plugC.setOutside(c.getOutside());
      plugin.notifySBaseChanged(plugC);
    } else if (prop.equals(TreeNodeChangeEvent.parentSBMLObject)) {
      logger.log(Level.DEBUG, MessageFormat.format(CHANGING_VALUE_NOT_SUPPORTED, eventsource.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.persistent)) {
      Trigger t = (Trigger) eventsource;
      Event evt = t.getParent();
      PluginEvent pevt = plugModel.getEvent(evt.getId());
      pevt.setTrigger(PluginUtils.convert(evt.getTrigger().getMath()));
      plugin.notifySBaseChanged(pevt);
    } else if (prop.equals(TreeNodeChangeEvent.priority)) {
      logger.log(Level.DEBUG, MessageFormat.format(CHANGING_VALUE_NOT_SUPPORTED, eventsource.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.qualifier)) {
      CVTerm cv = (CVTerm) eventsource;
      cv.setQualifierType(((Integer) event.getNewValue()).intValue());
      PluginSBase base = (PluginSBase) ((SBase) cv.getParent()).getUserObject(LINK_TO_CELLDESIGNER);
      plugin.notifySBaseChanged(base);
    } else if (prop.equals(TreeNodeChangeEvent.rdfAnnotationNamespaces)) {
      logger.log(Level.DEBUG, MessageFormat.format(CHANGING_VALUE_NOT_SUPPORTED, eventsource.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.resource)) {
      logger.log(Level.DEBUG, MessageFormat.format(UNUSED_METHOD, eventsource.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.reversible)) {
      Reaction r = (Reaction) event.getSource();
      PluginReaction plugR = plugModel.getReaction(r.getId());
      plugR.setReversible(r.getReversible());
    } else if (prop.equals(TreeNodeChangeEvent.sboTerm)) {
      AbstractSBase abs = (AbstractSBase) eventsource;
      abs.setSBOTerm((Integer) event.getNewValue());
      PluginSBase pabs = (PluginSBase) abs.getUserObject(LINK_TO_CELLDESIGNER);
      plugin.notifySBaseChanged(pabs);
    } else if (prop.equals(TreeNodeChangeEvent.scale)) {
      Unit u = (Unit) eventsource;
      u.setScale((Integer) event.getNewValue());
      PluginSBase psb = (PluginSBase) u.getUserObject(LINK_TO_CELLDESIGNER);
      plugin.notifySBaseChanged(psb);
    } else if (prop.equals(TreeNodeChangeEvent.setAnnotation)) {
      AbstractSBase abs = (AbstractSBase) eventsource;
      abs.setAnnotation((Annotation) event.getNewValue());
      PluginSBase pabs = (PluginSBase) abs.getUserObject(LINK_TO_CELLDESIGNER);
      plugin.notifySBaseChanged(pabs);
    } else if (prop.equals(TreeNodeChangeEvent.size)) {
      Compartment c = (Compartment) event.getSource();
      PluginCompartment plugC = plugModel.getCompartment(c.getId());
      plugC.setSize(c.getSize());
      plugin.notifySBaseChanged(plugC);
    } else if (prop.equals(TreeNodeChangeEvent.spatialDimensions)) {
      Compartment c = (Compartment) event.getSource();
      PluginCompartment plugC = plugModel.getCompartment(c.getId());
      plugC.setSpatialDimensions((long) c.getSpatialDimensions());
      plugin.notifySBaseChanged(plugC);
    } else if (prop.equals(TreeNodeChangeEvent.spatialSizeUnits)) {
      Species spec = (Species) event.getSource();
      PluginSpecies plugSpec = plugModel.getSpecies(spec.getId());
      plugSpec.setSpatialSizeUnits(spec.getSpatialSizeUnits());
      plugin.notifySBaseChanged(plugSpec);
    } else if (prop.equals(TreeNodeChangeEvent.species)) {
      if (event.getSource() instanceof SpeciesReference) {
        SpeciesReference specRef = (SpeciesReference) event.getSource();
        specRef.setSpecies((Species) event.getNewValue());
        PluginSpeciesReference pSpecRef = (PluginSpeciesReference) specRef.getUserObject(LINK_TO_CELLDESIGNER);
        plugin.notifySBaseChanged(pSpecRef);
      } else if (event.getSource() instanceof ModifierSpeciesReference) {
        ModifierSpeciesReference modspecRef = (ModifierSpeciesReference) event.getSource();
        modspecRef.setSpecies((Species) event.getNewValue());
        PluginModifierSpeciesReference pmodspecRef = (PluginModifierSpeciesReference) modspecRef.getUserObject(LINK_TO_CELLDESIGNER);
        plugin.notifySBaseChanged(pmodspecRef);
      }
    } else if (prop.equals(TreeNodeChangeEvent.speciesType)) {
      Species spec = (Species) event.getSource();
      PluginSpecies plugSpec = plugModel.getSpecies(spec.getId());
      plugSpec.setSpeciesType(spec.getSpeciesType());
      plugin.notifySBaseChanged(plugSpec);
    } else if (prop.equals(TreeNodeChangeEvent.stoichiometry)) {
      logger.log(Level.DEBUG, MessageFormat.format(UNUSED_METHOD, event.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.style)) {
      ASTNode n = (ASTNode) eventsource;
      n.setStyle((String) event.getNewValue());
      MathContainer mc = n.getParentSBMLObject();
      plugin.notifySBaseChanged((PluginSBase) mc.getUserObject(LINK_TO_CELLDESIGNER));
    } else if (prop.equals(TreeNodeChangeEvent.substanceUnits)) {
      KineticLaw klaw = (KineticLaw) event.getSource();
      PluginReaction plugReac = plugModel.getReaction(klaw.getParent().getId());
      plugReac.getKineticLaw().setSubstanceUnits(klaw.getSubstanceUnits());
      plugin.notifySBaseChanged(plugReac);
    } else if (prop.equals(TreeNodeChangeEvent.symbol)) {
      logger.log(Level.DEBUG, MessageFormat.format(CANNOT_FIRE_PROPERTY_CHANGE, event.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.SBMLDocumentAttributes)) {
      logger.log(Level.DEBUG, MessageFormat.format(CANNOT_FIRE_PROPERTY_CHANGE, event.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.text)) {
      logger.log(Level.DEBUG, MessageFormat.format(CANNOT_FIRE_PROPERTY_CHANGE, event.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.timeUnits)) {
      if (eventsource instanceof Event) {
        Event evt = (Event) eventsource;
        PluginEvent pEvt = plugModel.getEvent(evt.getId());
        pEvt.setTimeUnits(evt.getTimeUnits());
        plugin.notifySBaseChanged(pEvt);
      } else if (eventsource instanceof KineticLaw) {
        KineticLaw klaw = (KineticLaw) eventsource;
        PluginReaction pReac = plugModel.getReaction(klaw.getParent().getId());
        PluginKineticLaw pKlaw = pReac.getKineticLaw();
        pKlaw.setTimeUnits(klaw.getTimeUnits());
        plugin.notifySBaseChanged(pKlaw);
      } else if (eventsource instanceof Model) {
        logger.log(Level.DEBUG, MessageFormat.format(CHANGING_VALUE_IN_MODEL_REQURES_L3, eventsource.getClass().getSimpleName()));
      }
    } else if (prop.equals(TreeNodeChangeEvent.type)) {
      ASTNode n = (ASTNode) eventsource;
      n.setType((String) event.getNewValue());
      MathContainer mc = n.getParentSBMLObject();
      plugin.notifySBaseChanged((PluginSBase) mc.getUserObject(LINK_TO_CELLDESIGNER));
    } else if (prop.equals(TreeNodeChangeEvent.units)) {
      if (eventsource instanceof KineticLaw) {
        KineticLaw klaw = (KineticLaw) event.getSource();
        klaw.setUnits((Unit) event.getNewValue());
        PluginKineticLaw pklaw = (PluginKineticLaw) klaw.getUserObject(LINK_TO_CELLDESIGNER);
        plugin.notifySBaseChanged(pklaw);
      } else if (eventsource instanceof ExplicitRule) {
        ExplicitRule er = (ExplicitRule) eventsource;
        er.setUnits((Unit) event.getNewValue());
        PluginRule pr = (PluginRule) er.getUserObject(LINK_TO_CELLDESIGNER);
        plugin.notifySBaseChanged(pr);
      } else if (eventsource instanceof ASTNode) {
        ASTNode n = (ASTNode) eventsource;
        n.setUnits((String) event.getNewValue());
        MathContainer mc = n.getParentSBMLObject();
        plugin.notifySBaseChanged((PluginSBase) mc.getUserObject(LINK_TO_CELLDESIGNER));
      } else if (eventsource instanceof Model) {
        //TODO This can be potentially ignored, since we're not able to change the model in CellDesigner anyway.
      }
    } else if (prop.equals(TreeNodeChangeEvent.unsetCVTerms)) {
      if (eventsource instanceof AbstractSBase) {
        AbstractSBase abs = (AbstractSBase) eventsource;
        abs.unsetCVTerms();
        PluginSBase psb = (PluginSBase) abs.getUserObject(LINK_TO_CELLDESIGNER);
        plugin.notifySBaseChanged(psb);
      }
    } else if (prop.equals(TreeNodeChangeEvent.userObject)) {
      // NO user objects in CellDesigner.
      //			ASTNode n = (ASTNode) eventsource;
      //			n.setUserObject(event.getNewValue());
      //			MathContainer mc = n.getParentSBMLObject();
      //			plugin.notifySBaseChanged(getCorrespondingElementInJSBML(mc));
    } else if (prop.equals(TreeNodeChangeEvent.useValuesFromTriggerTime)) {
      Event evt = (Event) event.getSource();
      PluginEvent plugEvt = plugModel.getEvent(evt.getId());
      plugEvt.setUseValuesFromTriggerTime(evt.getUseValuesFromTriggerTime());
      plugin.notifySBaseChanged(plugEvt);
    } else if (prop.equals(TreeNodeChangeEvent.value)) {
      ASTNode n = (ASTNode) eventsource;
      n.setValue((Double) event.getNewValue());
      MathContainer mc = n.getParentSBMLObject();
      plugin.notifySBaseChanged((PluginSBase) mc.getUserObject(LINK_TO_CELLDESIGNER));
    } else if (prop.equals(TreeNodeChangeEvent.variable)) {
      Object evtSrc = event.getSource();
      if (evtSrc instanceof EventAssignment) {
        EventAssignment ea = (EventAssignment) eventsource;
        ea.setVariable((Variable) event.getNewValue());
        PluginSBase base = (PluginSBase) ea.getUserObject(LINK_TO_CELLDESIGNER);
        plugin.notifySBaseChanged(base);
      } else if (evtSrc instanceof ExplicitRule) {
        ExplicitRule er = (ExplicitRule) eventsource;
        er.setVariable((Variable) event.getNewValue());
        PluginSBase base = (PluginSBase) er.getUserObject(LINK_TO_CELLDESIGNER);
        plugin.notifySBaseChanged(base);
      } else if (evtSrc instanceof InitialAssignment) {
        InitialAssignment ia = (InitialAssignment) evtSrc;
        ia.setVariable((Variable) event.getNewValue());
        PluginSBase base = (PluginSBase) ia.getUserObject(LINK_TO_CELLDESIGNER);
        plugin.notifySBaseChanged(base);
      }
    } else if (prop.equals(TreeNodeChangeEvent.version)) {
      AbstractSBase asb = (AbstractSBase) eventsource;
      asb.setVersion(((Integer) event.getNewValue()).intValue());
      PluginSBase base = (PluginSBase) asb.getUserObject(LINK_TO_CELLDESIGNER);
      plugin.notifySBaseChanged(base);
    } else if (prop.equals(TreeNodeChangeEvent.volume)) {
      Compartment c = (Compartment) event.getSource();
      PluginCompartment plugC = plugModel.getCompartment(c.getId());
      plugC.setVolume(c.getVolume());
      plugin.notifySBaseChanged(plugC);
    } else if (prop.equals(TreeNodeChangeEvent.volumeUnits)) {
      logger.log(Level.DEBUG, MessageFormat.format(CHANGING_VALUE_IN_MODEL_REQURES_L3, eventsource.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.xmlTriple)) {
      logger.log(Level.DEBUG, MessageFormat.format(CANNOT_FIRE_PROPERTY_CHANGE, event.getClass().getSimpleName()));
    }
  }

  /**
   * This method saves the properties of a MathContainer input Object.
   * 
   * @param mathcontainer
   */
  private void saveMathContainerProperties(MathContainer mathcontainer) {
    if (mathcontainer instanceof FunctionDefinition) {
      FunctionDefinition funcDef = (FunctionDefinition) mathcontainer;
      PluginFunctionDefinition plugFuncDef = plugModel.getFunctionDefinition(funcDef.getId());
      boolean equals = plugFuncDef.getMath() != null && funcDef.isSetMath() && PluginUtils.equal(funcDef.getMath(), plugFuncDef.getMath());
      if (funcDef.isSetMath() && !equals) {
        plugFuncDef.setMath(PluginUtils.convert(funcDef.getMath()));
      } else {
        logger.log(Level.DEBUG, MessageFormat.format(COULD_NOT_SAVE_PROPERTIES_OF, funcDef.getClass().getSimpleName()));
      }
    } else if (mathcontainer instanceof KineticLaw) {
      Reaction r = ((KineticLaw) mathcontainer).getParent();
      PluginReaction plugReac = plugModel.getReaction(r.getId());
      if (plugReac != null) {
        PluginKineticLaw plugKl = plugReac.getKineticLaw();
        plugKl.setMath(PluginUtils.convert(r.getKineticLaw().getMath()));
        plugin.notifySBaseChanged(plugKl);
      } else {
        logger.log(Level.DEBUG, MessageFormat.format(COULD_NOT_SAVE_PROPERTIES_OF, r.getClass().getSimpleName()));
      }
    } else if (mathcontainer instanceof InitialAssignment) {
      InitialAssignment initAss = (InitialAssignment) mathcontainer;
      PluginInitialAssignment pluginit = plugModel.getInitialAssignment(initAss.getSymbol());
      boolean equals = initAss.getMath() != null && initAss.isSetMath() && PluginUtils.equal(initAss.getMath(), libsbml.parseFormula(pluginit.getMath()));
      if (initAss.isSetMath() && !equals) {
        pluginit.setMath(libsbml.formulaToString(PluginUtils.convert(initAss.getMath())));
        plugin.notifySBaseChanged(pluginit);
      } else {
        logger.log(Level.DEBUG, MessageFormat.format(COULD_NOT_SAVE_PROPERTIES_OF, initAss.getClass().getSimpleName()));
      }
    } else if (mathcontainer instanceof EventAssignment) {
      EventAssignment ea = (EventAssignment) mathcontainer;
      PluginEventAssignment plea = (PluginEventAssignment) ea.getUserObject(LINK_TO_CELLDESIGNER);
      boolean equals = ea.getMath() != null && ea.isSetMath();
      if (ea.isSetMath() && !equals) {
        plea.setMath(PluginUtils.convert(ea.getMath()));
        plugin.notifySBaseChanged(plea);
      } else {
        logger.log(Level.DEBUG, MessageFormat.format(COULD_NOT_SAVE_PROPERTIES_OF, ea.getClass().getSimpleName()));
      }
    } else if (mathcontainer instanceof StoichiometryMath) {
      //TODO This does not exist in CellDesigner and therefore can be ignored.
    } else if (mathcontainer instanceof Trigger) {
      Trigger trig = (Trigger) mathcontainer;
      PluginEvent plugEvent = plugModel.getEvent(trig.getParent().getId());
      boolean equals = plugEvent.getTrigger().isSetMath() && trig.isSetMath() && PluginUtils.equal(trig.getMath(), plugEvent.getTrigger().getMath());
      if (trig.isSetMath() && !equals) {
        plugEvent.getTrigger().setMath(PluginUtils.convert(trig.getMath()));
        plugin.notifySBaseChanged(plugEvent);
      } else {
        logger.log(Level.DEBUG, MessageFormat.format(COULD_NOT_SAVE_PROPERTIES_OF, trig.getClass().getSimpleName()));
      }
    } else if (mathcontainer instanceof Rule) {
      if (mathcontainer instanceof AlgebraicRule) {
        //TODO AlgebraicRules are not accessible that easily. How to do that ?

      } else if (mathcontainer instanceof ExplicitRule) {
        if (mathcontainer instanceof RateRule) {
          //TODO RateRules are not accessible that easily. How to do that ?
        }else if (mathcontainer instanceof AssignmentRule) {
          //TODO AssignmentRules are not accessible that easily. How to do that ?
        }
      }
    } else if (mathcontainer instanceof Constraint) {
      Constraint c = (Constraint) mathcontainer;
      PluginConstraint plugC  = plugModel.getConstraint(c.getMathMLString());
      boolean equals = plugC.getMath() != null && c.isSetMath() && PluginUtils.equal(c.getMath(), libsbml.parseFormula(plugC.getMath()));
      if (c.isSetMath() && !equals) {
        logger.log(Level.DEBUG, MessageFormat.format(COULD_NOT_SAVE_PROPERTIES_OF, c.getClass().getSimpleName()));
      }
    } else if (mathcontainer instanceof Delay) {
      Delay d = (Delay) mathcontainer;
      PluginEvent plugEvent = plugModel.getEvent(d.getParent().getId());
      org.sbml.libsbml.Delay plugDelay = plugEvent.getDelay();
      boolean equals = plugDelay.getMath() != null && d.isSetMath() && PluginUtils.equal(d.getMath(), plugDelay.getMath());
      if (d.isSetMath() && !equals) {
        plugDelay.setMath(PluginUtils.convert(d.getMath()));
        plugin.notifySBaseChanged(plugEvent);
      } else {
        logger.log(Level.DEBUG, MessageFormat.format(COULD_NOT_SAVE_PROPERTIES_OF, d.getClass().getSimpleName()));
      }
    } else if (mathcontainer instanceof Priority) {
      Priority p = (Priority) mathcontainer;
      PluginEvent plugEvent = plugModel.getEvent(p.getParent().getId());
      org.sbml.libsbml.Delay plugPriority = plugEvent.getDelay();
      boolean equals = plugPriority.getMath() != null && p.isSetMath() && PluginUtils.equal(p.getMath(), plugPriority.getMath());
      if (p.isSetMath() && !equals) {
        plugPriority.setMath(PluginUtils.convert(p.getMath()));
        plugin.notifySBaseChanged(plugEvent);
      } else {
        logger.log(Level.DEBUG, MessageFormat.format(COULD_NOT_SAVE_PROPERTIES_OF, p.getClass().getSimpleName()));
      }
    }
  }

  /**
   * Searches a kineticlaw and returns the index of a specific localparameter p
   * @param k
   * @param p
   * @return
   */
  public int searchKineticLaw(KineticLaw k, LocalParameter p) {
    ListOf<LocalParameter> lp = k.getListOfParameters();
    int temp = 0;
    for (int i = 0; i < lp.size(); i++) {
      LocalParameter locp = lp.get(i);
      if (locp.equals(p)) {
        temp = i;
        return temp;
      } else {
        continue;
      }
    }
    return temp;
  }

}
