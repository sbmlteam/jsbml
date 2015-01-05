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
package org.sbml.jsbml.celldesigner;

import static org.sbml.jsbml.celldesigner.CellDesignerConstants.LINK_TO_CELLDESIGNER;
import static org.sbml.jsbml.celldesigner.libsbml.LibSBMLConstants.LINK_TO_LIBSBML;

import java.beans.PropertyChangeEvent;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.swing.tree.TreeNode;
import javax.xml.stream.XMLStreamException;

import jp.sbi.celldesigner.plugin.CellDesignerPlugin;
import jp.sbi.celldesigner.plugin.PluginAssignmentRule;
import jp.sbi.celldesigner.plugin.PluginCompartment;
import jp.sbi.celldesigner.plugin.PluginCompartmentType;
import jp.sbi.celldesigner.plugin.PluginConstraint;
import jp.sbi.celldesigner.plugin.PluginEvent;
import jp.sbi.celldesigner.plugin.PluginEventAssignment;
import jp.sbi.celldesigner.plugin.PluginFunctionDefinition;
import jp.sbi.celldesigner.plugin.PluginInitialAssignment;
import jp.sbi.celldesigner.plugin.PluginKineticLaw;
import jp.sbi.celldesigner.plugin.PluginListOf;
import jp.sbi.celldesigner.plugin.PluginModel;
import jp.sbi.celldesigner.plugin.PluginModifierSpeciesReference;
import jp.sbi.celldesigner.plugin.PluginParameter;
import jp.sbi.celldesigner.plugin.PluginRateRule;
import jp.sbi.celldesigner.plugin.PluginReaction;
import jp.sbi.celldesigner.plugin.PluginRule;
import jp.sbi.celldesigner.plugin.PluginSBase;
import jp.sbi.celldesigner.plugin.PluginSimpleSpeciesReference;
import jp.sbi.celldesigner.plugin.PluginSpecies;
import jp.sbi.celldesigner.plugin.PluginSpeciesReference;
import jp.sbi.celldesigner.plugin.PluginSpeciesType;
import jp.sbi.celldesigner.plugin.PluginUnit;
import jp.sbi.celldesigner.plugin.PluginUnitDefinition;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.AbstractNamedSBaseWithUnit;
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
import org.sbml.jsbml.NamedSBase;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.Priority;
import org.sbml.jsbml.QuantityWithUnit;
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
import org.sbml.jsbml.celldesigner.libsbml.LibSBMLUtils;
import org.sbml.jsbml.ext.SBasePlugin;
import org.sbml.jsbml.ext.layout.GraphicalObject;
import org.sbml.jsbml.util.ResourceManager;
import org.sbml.jsbml.util.SBMLtools;
import org.sbml.jsbml.util.TreeNodeAdapter;
import org.sbml.jsbml.util.TreeNodeChangeEvent;
import org.sbml.jsbml.util.TreeNodeChangeListener;
import org.sbml.jsbml.util.TreeNodeRemovedEvent;
import org.sbml.jsbml.util.TreeNodeWithChangeSupport;
import org.sbml.jsbml.xml.XMLToken;
import org.sbml.libsbml.XMLNode;

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
  /**
   * Localization support.
   */
  private static final ResourceBundle bundle = ResourceManager.getBundle("org.sbml.jsbml.celldesigner.Messages");

  /**
   * A reference to the main CellDesigner plugin.
   */
  private final CellDesignerPlugin plugin;

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
   * @throws XMLStreamException
   */
  public PluginChangeListener(CellDesignerPlugin plugin) throws XMLStreamException {
    this.plugin = plugin;
    plugModel = plugin.getSelectedModel();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeChangeListener#nodeAdded(javax.swing.tree.TreeNode)
   */
  @Override
  public void nodeAdded(TreeNode node) {
    if (node instanceof SBase) {
      try {
        SBase parent = (SBase) node.getParent();
        PluginSBase plugSBase = null;
        PluginListOf pList = null;

        if (node instanceof NamedSBase) {
          NamedSBase nsb = (NamedSBase) node;
          if (node instanceof GraphicalObject)
          {
            LayoutPluginChangeListener.addOrChangeGlyphInfoToCellDesigner((GraphicalObject)node, plugModel);
          }
          else if (node instanceof CompartmentType) {
            plugSBase = PluginUtils.convertCompartmentType((CompartmentType) nsb);
            plugModel.addCompartmentType((PluginCompartmentType) plugSBase);
            pList = plugModel.getListOfCompartments();
          } else if (node instanceof UnitDefinition) {
            plugSBase = PluginUtils.convertUnitDefinition((UnitDefinition) nsb);
            plugModel.addUnitDefinition((PluginUnitDefinition) plugSBase);
            pList = plugModel.getListOfUnitDefinitions();
          } else if (node instanceof Reaction) {
            plugSBase = PluginUtils.convertReaction((Reaction) nsb);
            plugModel.addReaction((PluginReaction) plugSBase);
            pList = plugModel.getListOfReactions();
          } else if (node instanceof SpeciesType) {
            plugSBase = PluginUtils.convertSpeciesType((SpeciesType) nsb);
            plugModel.addSpeciesType((PluginSpeciesType) plugSBase);
            pList = plugModel.getListOfSpeciesTypes();
          } else if (node instanceof SimpleSpeciesReference) {
            PluginReaction plugReaction = (PluginReaction) ((Reaction) parent.getParent()).getUserObject(LINK_TO_CELLDESIGNER);
            if (node instanceof ModifierSpeciesReference) {
              plugSBase = PluginUtils.convertModifierSpeciesReference((ModifierSpeciesReference) nsb);
              plugReaction.addModifier((PluginModifierSpeciesReference) plugSBase);
              pList = plugReaction.getListOfModifiers();
            } else {
              plugSBase = PluginUtils.convertSpeciesReference((SpeciesReference) nsb);
              if (parent.getElementName().toLowerCase().contains("reactant")) {
                plugReaction.addReactant((PluginSpeciesReference) plugSBase);
                pList = plugReaction.getListOfReactants();
              } else {
                plugReaction.addProduct((PluginSpeciesReference) plugSBase);
                pList = plugReaction.getListOfProducts();
              }
            }
          } else if (node instanceof Model) {
            plugSBase = PluginUtils.convertModel((Model) nsb);
            plugModel = (PluginModel) plugSBase;
          } else if (node instanceof FunctionDefinition) {
            plugSBase = PluginUtils.convertFunctionDefinition((FunctionDefinition) nsb);
            plugModel.addFunctionDefinition((PluginFunctionDefinition) plugSBase);
            pList = plugModel.getListOfFunctionDefinitions();
          } else if (node instanceof AbstractNamedSBaseWithUnit) {
            if (node instanceof Event) {
              plugSBase = PluginUtils.convertEvent((Event) nsb);
              plugModel.addEvent((PluginEvent) plugSBase);
              pList = plugModel.getListOfEvents();
            } else if (node instanceof QuantityWithUnit) {
              if (node instanceof LocalParameter) {
                plugSBase = PluginUtils.convertLocalParameter((LocalParameter) nsb);
                PluginKineticLaw pluKin = (PluginKineticLaw) ((KineticLaw) parent.getParent()).getUserObject(LINK_TO_CELLDESIGNER);
                pluKin.addParameter((PluginParameter) plugSBase);
                pList = pluKin.getListOfParameters();
              } else if (node instanceof Symbol) {
                if (node instanceof Compartment) {
                  plugSBase = PluginUtils.convertCompartment((Compartment) nsb);
                  plugModel.addCompartment((PluginCompartment) plugSBase);
                  pList = plugModel.getListOfCompartments();
                } else if (node instanceof Species) {
                  plugSBase = PluginUtils.convertSpecies((Species) nsb);
                  plugModel.addSpecies((PluginSpecies) plugSBase);
                  pList = plugModel.getListOfSpecies();
                } else if (node instanceof Parameter) {
                  plugSBase = PluginUtils.convertParameter((Parameter) nsb);
                  plugModel.addParameter((PluginParameter) plugSBase);
                  pList = plugModel.getListOfParameters();
                }
              }
            }
          }

        } else if (node instanceof MathContainer) {
          MathContainer mc = (MathContainer) node;

          if (mc instanceof KineticLaw) {
            plugSBase = PluginUtils.convertKineticLaw((KineticLaw) mc);
            ((PluginReaction) ((Reaction) parent).getUserObject(LINK_TO_CELLDESIGNER)).setKineticLaw((PluginKineticLaw) plugSBase);
          } else if (mc instanceof InitialAssignment) {
            plugSBase = PluginUtils.convertInitialAssignment((InitialAssignment) mc);
            plugModel.addInitialAssignment((PluginInitialAssignment) plugSBase);
            pList = plugModel.getListOfInitialAssignments();
          } else if (mc instanceof EventAssignment) {
            plugSBase = PluginUtils.convertEventAssignment((EventAssignment) mc);
            PluginEvent pluEvt = (PluginEvent) ((Event) parent.getParent()).getUserObject(LINK_TO_CELLDESIGNER);
            pluEvt.addEventAssignment((PluginEventAssignment) plugSBase);
            pList = pluEvt.getListOfEventAssignments();
          } else if (mc instanceof Rule) {
            plugSBase = PluginUtils.convertRule((Rule) mc);
            plugModel.addRule((PluginRule) plugSBase);
            pList = plugModel.getListOfRules();
          } else if (mc instanceof Constraint) {
            plugSBase = PluginUtils.convertConstraint((Constraint) mc);
            plugModel.addConstraint((PluginConstraint) plugSBase);
            pList = plugModel.getListOfConstraints();
          } else if (mc instanceof Delay) {
            Event event = (Event) parent;
            PluginEvent pluginEvent = (PluginEvent) event.getUserObject(LINK_TO_CELLDESIGNER);
            pluginEvent.setDelay(LibSBMLUtils.convertASTNode(event.getDelay().getMath()));
            LibSBMLUtils.transferMathContainerProperties(event.getDelay(), pluginEvent.getDelay());
            plugin.notifySBaseChanged(pluginEvent);
          } else if (mc instanceof Priority) {
            logger.log(Level.DEBUG, MessageFormat.format(
              bundle.getString("NO_CORRESPONDING_CLASS_IN_CELLDESIGNER"),
              node.getClass().getSimpleName()));
          } else if (mc instanceof StoichiometryMath) {
            SpeciesReference speciesReference = (SpeciesReference) parent;
            PluginSpeciesReference pluginSpeciesReference = (PluginSpeciesReference) speciesReference.getUserObject(LINK_TO_CELLDESIGNER);
            pluginSpeciesReference.setStoichiometryMath(LibSBMLUtils.convertStoichiometryMath(speciesReference.getStoichiometryMath()));
            plugin.notifySBaseChanged(pluginSpeciesReference);
          } else if (mc instanceof Trigger) {
            Event event = (Event) parent;
            PluginEvent pluginEvent = (PluginEvent) event.getUserObject(LINK_TO_CELLDESIGNER);
            pluginEvent.setTrigger(LibSBMLUtils.convertTrigger(event.getTrigger()));
            event.getTrigger().putUserObject(LINK_TO_CELLDESIGNER, pluginEvent.getTrigger());
            plugin.notifySBaseChanged(pluginEvent);
          }

        } else if (node instanceof Unit) {
          plugSBase = PluginUtils.convertUnit((Unit) node);
          PluginUnitDefinition plugUDef = (PluginUnitDefinition) ((UnitDefinition) parent.getParent()).getUserObject(LINK_TO_CELLDESIGNER);
          plugUDef.addUnit((PluginUnit) plugSBase);
          pList = plugUDef.getListOfUnits();
        } else if (node instanceof SBMLDocument) {
          logger.log(Level.DEBUG, MessageFormat.format(
            bundle.getString("NO_CORRESPONDING_CLASS_IN_CELLDESIGNER"),
            node.getClass().getSimpleName()));
        } else if (node instanceof ListOf<?>) {
          // What can we do here? Nothing, will be treated as soon as list is being filled.
        }

        if (plugSBase != null) {
          plugin.notifySBaseAdded(plugSBase);
          if (parent instanceof ListOf<?>) {
            Object plugObj = ((ListOf<?>) parent).getUserObject(LINK_TO_CELLDESIGNER);
            if ((plugObj == null) && (pList != null)) {
              parent.putUserObject(LINK_TO_CELLDESIGNER, pList);
            }
          }
        }
      } catch (Throwable exc) {
        exc.printStackTrace();
      }

    } else if (node instanceof AnnotationElement) {

      TreeNode parent = null;
      do {
        parent = node.getParent();
      } while (!(parent instanceof SBase));
      SBase parentSBase = (SBase) parent;
      PluginSBase plugParentSBase = (PluginSBase) parentSBase.getUserObject(LINK_TO_CELLDESIGNER);

      if (node instanceof CVTerm) {
        CVTerm cvt = (CVTerm) node;
        plugParentSBase.addCVTerm(LibSBMLUtils.convertCVTerm(cvt));
        cvt.putUserObject(LINK_TO_LIBSBML, plugParentSBase.getCVTerm(plugParentSBase.getNumCVTerms() - 1));
      } else if (node instanceof History) {
        logger.log(Level.DEBUG, MessageFormat.format(
          bundle.getString("NO_CORRESPONDING_CLASS_IN_CELLDESIGNER"),
          node.getClass().getSimpleName()));
      } else if (node instanceof Annotation) {
        logger.log(Level.DEBUG, MessageFormat.format(
          bundle.getString("NO_CORRESPONDING_CLASS_IN_CELLDESIGNER"),
          node.getClass().getSimpleName()));
      } else if (node instanceof Creator) {
        logger.log(Level.DEBUG, MessageFormat.format(
          bundle.getString("NO_CORRESPONDING_CLASS_IN_CELLDESIGNER"),
          node.getClass().getSimpleName()));
      }

    } else if (node instanceof ASTNode) {
      ASTNode astnode = (ASTNode) node;
      Object libParent = ((TreeNodeWithChangeSupport) astnode.getParent()).getUserObject(LINK_TO_LIBSBML);
      if (libParent instanceof org.sbml.libsbml.ASTNode) {
        org.sbml.libsbml.ASTNode libParentAST = (org.sbml.libsbml.ASTNode) libParent;
        libParentAST.addChild(LibSBMLUtils.convertASTNode(astnode));
        LibSBMLUtils.link((ASTNode) astnode.getParent(), libParentAST);
      } else {
        PluginUtils.transferMath(astnode.getParentSBMLObject(), (PluginSBase) libParent);
      }

    } else if (node instanceof XMLToken) {
      logger.log(Level.DEBUG, MessageFormat.format(
        bundle.getString("PARSING_OF_NODE_UNSUCCESSFUL"),
        node.getClass().getSimpleName()));

    } else {
      logger.log(Level.DEBUG, MessageFormat.format(
        bundle.getString("COULD_NOT_PROCESS"), node.toString()));
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeChangeListener#nodeRemoved(org.sbml.jsbml.util.TreeNodeRemovedEvent)
   */
  @Override
  public void nodeRemoved(TreeNodeRemovedEvent evt) {
    TreeNode node = evt.getSource();
    TreeNode parent = evt.getPreviousParent();

    if (node instanceof SBase) {
      PluginSBase psbase = (PluginSBase) ((SBase) node).getUserObject(LINK_TO_CELLDESIGNER);
      if (node instanceof NamedSBase) {

        NamedSBase nsb = (NamedSBase) node;
        if (node instanceof GraphicalObject)
        {
          LayoutPluginChangeListener.removeGlyphInfoFromCellDesigner((GraphicalObject)nsb, plugModel);
        }
        else if (node instanceof UnitDefinition) {
          plugModel.removeUnitDefinition(nsb.getId());
          plugin.notifySBaseDeleted(psbase);
        } else if (node instanceof CompartmentType) {
          plugModel.removeCompartmentType(nsb.getId());
          plugin.notifySBaseDeleted(psbase);
        } else if (node instanceof SpeciesType) {
          plugModel.removeSpeciesType(nsb.getId());
          plugin.notifySBaseDeleted(psbase);
        } else if (node instanceof Reaction) {
          plugModel.removeReaction(nsb.getId());
          plugin.notifySBaseDeleted(psbase);
        } else if (node instanceof SimpleSpeciesReference) {
          SimpleSpeciesReference simspec = (SimpleSpeciesReference) node;
          if (node instanceof ModifierSpeciesReference) {
            PluginModifierSpeciesReference pmsr = (PluginModifierSpeciesReference) psbase;
            PluginReaction preaction = pmsr.getParentReaction();
            preaction.removeModifier(pmsr);
            plugin.notifySBaseDeleted(pmsr);
          } else {
            PluginSpeciesReference ref = (PluginSpeciesReference) psbase;
            PluginReaction preaction = ref.getParentReaction();
            if (SBO.convertSBO2Alias(simspec.getSBOTerm()) == PluginSimpleSpeciesReference.REACTANT) {
              preaction.removeReactant(ref);
            } else {
              preaction.removeProduct(ref);
            }
            plugin.notifySBaseDeleted(ref);
          }
        } else if (node instanceof Model) {
          // Can actually never be done, I guess...
          plugin.notifySBaseDeleted(plugModel);
        } else if (node instanceof FunctionDefinition) {
          plugModel.removeFunctionDefinition(nsb.getId());
          plugin.notifySBaseDeleted(psbase);
        } else if (node instanceof Event) {
          plugModel.removeEvent(nsb.getId());
          plugin.notifySBaseDeleted(psbase);
        } else if (node instanceof LocalParameter) {
          PluginParameter ppam = (PluginParameter) psbase;
          PluginReaction pr = ppam.getParentReaction();
          pr.getKineticLaw().removeParameter(ppam);
          plugin.notifySBaseDeleted(ppam);
        } else if (node instanceof Compartment) {
          plugModel.removeCompartment(nsb.getId());
          plugin.notifySBaseDeleted(psbase);
        } else if (node instanceof Species) {
          PluginListOf listofSpeciesAliases = plugModel.getSpecies(nsb.getId()).getListOfSpeciesAlias();
          for (int i = 0; i < listofSpeciesAliases.size(); i++)
          {
            listofSpeciesAliases.remove(0);
          }
          plugModel.removeSpecies(nsb.getId());
          plugin.notifySBaseDeleted(psbase);
        } else if (node instanceof Parameter) {
          plugModel.removeParameter(nsb.getId());
          plugin.notifySBaseDeleted(psbase);
        }
      }
      if (node instanceof Unit) {
        PluginUnit punit = (PluginUnit) psbase;
        PluginUnitDefinition pud = punit.getParentUnitDefinition();
        pud.removeUnit(punit);
        plugin.notifySBaseDeleted(punit);
      } else if (node instanceof SBMLDocument) {
        // Can never be done by CellDesigner.
      } else if (node instanceof ListOf<?>) {
        PluginListOf plist = (PluginListOf) psbase;
        /* There is no general way to delete a list from its parent
         * so the approach is to empty the list instead.
         */
        for (int i = plist.size() - 1; i > 0; i--) {
          plugin.notifySBaseDeleted(plist.remove(i));
        }
        plugin.notifySBaseDeleted(plist);

      } else if (node instanceof MathContainer) {
        MathContainer mathContainer = (MathContainer) node;
        // FunctionDefinition is already done (NamedSBase)
        if (node instanceof KineticLaw) {
          PluginKineticLaw plugklaw = (PluginKineticLaw) psbase;
          plugklaw.getParentReaction().setKineticLaw(null);
          plugin.notifySBaseDeleted(plugklaw);
        } else if (node instanceof InitialAssignment) {
          PluginInitialAssignment plugiAssign = (PluginInitialAssignment) psbase;
          plugModel.removeInitialAssignment(plugiAssign);
          plugin.notifySBaseDeleted(plugiAssign);
        } else if (node instanceof EventAssignment) {
          PluginEventAssignment pEAssign = (PluginEventAssignment) psbase;
          pEAssign.getParentEvent().removeEventAssignment(pEAssign);
          plugin.notifySBaseDeleted(pEAssign);
        } else if (node instanceof StoichiometryMath) {
          SpeciesReference specRef = (SpeciesReference) parent;
          PluginSpeciesReference pSpecRef = (PluginSpeciesReference) specRef.getUserObject(LINK_TO_CELLDESIGNER);
          logger.log(Level.DEBUG, MessageFormat.format(
            bundle.getString("TRYING_TO_REMOVE_ELEMENT"),
            mathContainer.getElementName(), pSpecRef));
          pSpecRef.setStoichiometryMath((org.sbml.libsbml.StoichiometryMath) null);
          plugin.SBaseChanged(pSpecRef);
        } else if (node instanceof Trigger) {
          PluginEvent plugEvent = (PluginEvent) ((SBase) parent).getUserObject(LINK_TO_CELLDESIGNER);
          logger.log(Level.DEBUG, MessageFormat.format(
            bundle.getString("TRYING_TO_REMOVE_ELEMENT"),
            mathContainer.getElementName(), plugEvent.getId()));
          plugEvent.setTrigger((org.sbml.libsbml.Trigger) null);
          plugin.notifySBaseChanged(plugEvent);
        } else if (node instanceof Constraint) {
          PluginConstraint plugct = (PluginConstraint) mathContainer.getUserObject(LINK_TO_CELLDESIGNER);
          plugModel.removeConstraint(plugct);
          plugin.notifySBaseDeleted(plugct);
        } else if (node instanceof Delay) {
          PluginEvent plugEvent = (PluginEvent) ((SBase) parent).getUserObject(LINK_TO_CELLDESIGNER);
          logger.log(Level.DEBUG, MessageFormat.format(
            bundle.getString("TRYING_TO_REMOVE_ELEMENT"),
            mathContainer.getElementName(), plugEvent.getId()));
          plugEvent.setDelay((org.sbml.libsbml.ASTNode) null);
          plugin.notifySBaseChanged(plugEvent);
        } else if (node instanceof Priority) {
          logger.log(Level.DEBUG, MessageFormat.format(
            bundle.getString("NO_CORRESPONDING_CLASS_IN_CELLDESIGNER"),
            node.getClass().getSimpleName()));
        } else if (node instanceof Rule) {
          PluginRule pRule = (PluginRule) mathContainer.getUserObject(LINK_TO_CELLDESIGNER);
          plugModel.removeRule(pRule);
          plugin.notifySBaseDeleted(pRule);
        }
      }

    } else if (node instanceof AnnotationElement) {
      if (node instanceof CVTerm) {
        CVTerm term = (CVTerm) node;
        logger.log(Level.DEBUG, MessageFormat.format(bundle.getString("CANNOT_DELETE_CVTERM"), parent, term));
      } else if (node instanceof History) {
        logger.log(Level.DEBUG, MessageFormat.format(
          bundle.getString("NO_CORRESPONDING_CLASS_IN_CELLDESIGNER"),
          node.getClass().getSimpleName()));
      } else if (node instanceof Creator) {
        logger.log(Level.DEBUG, MessageFormat.format(
          bundle.getString("NO_CORRESPONDING_CLASS_IN_CELLDESIGNER"),
          node.getClass().getSimpleName()));
      }
    } else if (node instanceof Annotation) {
      PluginSBase psbase = (PluginSBase) ((SBase) parent).getUserObject(LINK_TO_CELLDESIGNER);
      psbase.setAnnotation((org.sbml.libsbml.XMLNode) null);
      plugin.SBaseChanged(psbase);

    } else if (node instanceof ASTNode) {
      if (parent instanceof ASTNode) {
        org.sbml.libsbml.ASTNode libAST = (org.sbml.libsbml.ASTNode) ((ASTNode) node).getUserObject(LINK_TO_LIBSBML);
        org.sbml.libsbml.ASTNode libParentAST = (org.sbml.libsbml.ASTNode) ((ASTNode) parent).getUserObject(LINK_TO_LIBSBML);
        // find the corresponding ASTNode
        for (int i = 0; i < libParentAST.getNumChildren(); i++) {
          if (libAST == libParentAST.getChild(i)) {
            libParentAST.removeChild(i);
            break;
          }
        }
      } else {
        // must be some PluginSBase
        PluginSBase libParent = (PluginSBase) ((TreeNodeWithChangeSupport) parent).getUserObject(LINK_TO_CELLDESIGNER);
        try {
          Class<?> clazz = Class.forName("jp.sbi.celldesigner.plugin.Plugin" + parent.getClass().getSimpleName()); // the corresponding CellDesigner class
          Method method = clazz.getMethod("setMath", org.sbml.libsbml.ASTNode.class);
          method.invoke(clazz.cast(libParent), new Object[] {null});
        } catch (Throwable exc) {
          logger.log(Level.DEBUG, exc.getLocalizedMessage() != null ? exc.getLocalizedMessage() : exc.getMessage(), exc);
        }
      }

    } else if (node instanceof TreeNodeAdapter) {
      // This can actually never happen, because then the actual change is directed to propertyChange
      logger.log(Level.DEBUG, MessageFormat.format(
        bundle.getString("CANNOT_REMOVE_ELEMENT"),
        node.getClass().getSimpleName()));
    } else if (node instanceof XMLToken) {
      logger.log(Level.DEBUG, MessageFormat.format(
        bundle.getString("CANNOT_REMOVE_ELEMENT"),
        node.getClass().getSimpleName()));
    } else if (node instanceof SBasePlugin) {
      logger.log(Level.DEBUG, MessageFormat.format(
        bundle.getString("PLUGINS_CURRENTLY_NOT_SUPPORTED"),
        node.getClass().getSimpleName()));
    }
  }

  /* (non-Javadoc)
   * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
   */
  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    Object evtSrc = evt.getSource();
    String prop = evt.getPropertyName();

    if (prop.equals(TreeNodeChangeEvent.addExtension)) {
      logger.log(Level.DEBUG, MessageFormat.format(
        bundle.getString("CHANGING_VALUE_NOT_SUPPORTED"),
        evtSrc.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.annotation)) {
      logger.log(Level.DEBUG, MessageFormat.format(
        bundle.getString("CHANGING_VALUE_NOT_SUPPORTED"),
        evtSrc.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.annotationNameSpaces)) {
      logger.log(Level.DEBUG, MessageFormat.format(
        bundle.getString("CHANGING_VALUE_NOT_SUPPORTED"),
        evtSrc.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.areaUnits)) {
      logger.log(Level.DEBUG, MessageFormat.format(
        bundle.getString("CHANGING_VALUE_IN_MODEL_REQURES_L3"),
        evtSrc.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.boundaryCondition)) {
      Species species = (Species) evtSrc;
      PluginSpecies plugSpec = plugModel.getSpecies(species.getId());
      plugSpec.setBoundaryCondition(((Boolean) evt.getNewValue()).booleanValue());
      plugin.notifySBaseChanged(plugSpec);
    } else if (prop.equals(TreeNodeChangeEvent.charge)) {
      Species species = (Species) evtSrc;
      PluginSpecies plugSpec = plugModel.getSpecies(species.getId());
      plugSpec.setCharge(((Integer) evt.getNewValue()).intValue());
      plugin.notifySBaseChanged(plugSpec);
    } else if (prop.equals(TreeNodeChangeEvent.childNode) || prop.equals(TreeNodeChangeEvent.className)) {
      ASTNode node = (ASTNode) evtSrc;
      MathContainer mathContainer = node.getParentSBMLObject();
      Object plugSBase = mathContainer.getUserObject(LINK_TO_LIBSBML);
      try {
        Method method = plugSBase.getClass().getMethod("setMath", org.sbml.libsbml.ASTNode.class);
        method.invoke(plugSBase, LibSBMLUtils.convertASTNode(mathContainer.getMath()));
        plugin.notifySBaseChanged((PluginSBase) plugSBase);
      } catch (Throwable exc) {
        logger.log(Level.DEBUG, MessageFormat.format(
          bundle.getString("CANNOT_CHANGE_ELEMENT"), node.getClass().getSimpleName()));
      }
    } else if (prop.equals(TreeNodeChangeEvent.compartment)) {
      if (evtSrc instanceof Species) {
        Species spec = (Species) evtSrc;
        PluginSpecies plugSpecies = (PluginSpecies) spec.getUserObject(LINK_TO_CELLDESIGNER);
        plugSpecies.setCompartment(evt.getNewValue().toString());
        plugin.notifySBaseChanged(plugSpecies);
      } else if (evtSrc instanceof Reaction) {
        logger.log(Level.DEBUG, MessageFormat.format(
          bundle.getString("CHANGING_VALUE_IN_MODEL_REQURES_L3"),
          evtSrc.getClass().getSimpleName()));
      }
    } else if (prop.equals(TreeNodeChangeEvent.compartmentType)) {
      Compartment c = (Compartment) evtSrc;
      PluginCompartment pc = (PluginCompartment) c.getUserObject(LINK_TO_CELLDESIGNER);
      pc.setCompartmentType(evt.getNewValue().toString());
      plugin.notifySBaseChanged(pc);
    } else if (prop.equals(TreeNodeChangeEvent.constant)) {
      if (evtSrc instanceof SpeciesReference) {
        logger.log(Level.DEBUG, MessageFormat.format(
          bundle.getString("CHANGING_VALUE_IN_MODEL_REQURES_L3"),
          evtSrc.getClass().getSimpleName()));
      } else if (evtSrc instanceof Symbol) {
        if (evtSrc instanceof Compartment) {
          Compartment c = (Compartment) evtSrc;
          PluginCompartment pc = plugModel.getCompartment(c.getId());
          pc.setConstant(((Boolean) evt.getNewValue()).booleanValue());
          plugin.notifySBaseChanged(pc);
        } else if (evtSrc instanceof Parameter) {
          Parameter p = (Parameter) evtSrc;
          PluginParameter pp = plugModel.getParameter(p.getId());
          pp.setConstant(((Boolean) evt.getNewValue()).booleanValue());
          plugin.notifySBaseChanged(pp);
        } else if (evtSrc instanceof Species) {
          Species sp = (Species) evtSrc;
          PluginSpecies pspec = plugModel.getSpecies(sp.getId());
          pspec.setConstant(((Boolean) evt.getNewValue()).booleanValue());
          plugin.notifySBaseChanged(pspec);
        }
      }
    } else if (prop.equals(TreeNodeChangeEvent.conversionFactor)) {
      logger.log(Level.DEBUG, MessageFormat.format(
        bundle.getString("CHANGING_VALUE_IN_MODEL_REQURES_L3"),
        evtSrc.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.createdDate)) {
      logger.log(Level.DEBUG, MessageFormat.format(
        bundle.getString("CHANGING_VALUE_NOT_SUPPORTED"),
        evtSrc.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.creator)) {
      logger.log(Level.DEBUG, MessageFormat.format(
        bundle.getString("CHANGING_VALUE_NOT_SUPPORTED"),
        evtSrc.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.definitionURL)) {
      // no such method for setting definition URL!
      logger.log(Level.DEBUG, MessageFormat.format(
        bundle.getString("CANNOT_CHANGE_ELEMENT"),
        evtSrc.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.denominator)) {
      if (evtSrc instanceof SpeciesReference) {
        //PluginSpeciesReference does not allow the setting of a denominator
      } else {
        ASTNode node = (ASTNode) evtSrc;
        ((org.sbml.libsbml.ASTNode) node.getUserObject(LINK_TO_LIBSBML)).setValue(node.getNumerator(), node.getDenominator());
      }
    } else if (prop.equals(TreeNodeChangeEvent.email)) {
      logger.log(Level.DEBUG, MessageFormat.format(
        bundle.getString("CHANGING_VALUE_NOT_SUPPORTED"),
        evtSrc.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.encoding)) {
      // no such method for encoding!
      logger.log(Level.DEBUG, MessageFormat.format(
        bundle.getString("CANNOT_CHANGE_ELEMENT"),
        evtSrc.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.exponent)) {
      if (evtSrc instanceof ASTNode) {
        ASTNode node = (ASTNode) evtSrc;
        MathContainer mc = node.getParentSBMLObject();
        ((org.sbml.libsbml.ASTNode) node.getUserObject(LINK_TO_LIBSBML)).setValue(node.getMantissa(), node.getExponent());
        plugin.notifySBaseChanged((PluginSBase) mc.getUserObject(LINK_TO_CELLDESIGNER));
      } else if (evtSrc instanceof Unit) {
        Unit ut = (Unit) evtSrc;
        PluginUnit pluginUnit = (PluginUnit) ut.getUserObject(LINK_TO_CELLDESIGNER);
        Double val = (Double) evt.getNewValue();
        pluginUnit.setExponent((val != null) ? (int) val.doubleValue() : 0);
        if (pluginUnit.getExponent() != ut.getExponent()) {
          logger.log(Level.DEBUG, MessageFormat.format(bundle.getString("ROUNDING_ERROR"),
            (pluginUnit.getExponent() - ut.getExponent())));
        }
        plugin.notifySBaseChanged(pluginUnit);
      }
    } else if (prop.equals(TreeNodeChangeEvent.extentUnits)) {
      logger.log(Level.DEBUG, MessageFormat.format(
        bundle.getString("CHANGING_VALUE_IN_MODEL_REQURES_L3"),
        evtSrc.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.familyName)) {
      logger.log(Level.DEBUG, MessageFormat.format(
        bundle.getString("CHANGING_VALUE_NOT_SUPPORTED"),
        evtSrc.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.fast)) {
      Reaction r = (Reaction) evtSrc;
      PluginReaction plugR = plugModel.getReaction(r.getId());
      plugR.setFast((Boolean) evt.getNewValue());
      plugin.notifySBaseChanged(plugR);
    } else if (prop.equals(TreeNodeChangeEvent.formula)) {
      MathContainer mathContainer = (MathContainer) evtSrc;
      Object plugSBase = mathContainer.getUserObject(LINK_TO_CELLDESIGNER);
      try {
        Method method = plugSBase.getClass().getMethod("setFormula", String.class);
        method.invoke(plugSBase, evt.getNewValue().toString());
      } catch (Throwable exc) {
        logger.log(Level.DEBUG, MessageFormat.format(
          bundle.getString("CANNOT_CHANGE_ELEMENT"), mathContainer.getClass().getSimpleName()));
      }
    } else if (prop.equals(TreeNodeChangeEvent.givenName)) {
      logger.log(Level.DEBUG, MessageFormat.format(
        bundle.getString("CHANGING_VALUE_NOT_SUPPORTED"),
        evtSrc.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.hasOnlySubstanceUnits)) {
      Species spec = (Species) evtSrc;
      PluginSpecies plugSpec = (PluginSpecies) spec.getUserObject(LINK_TO_CELLDESIGNER);
      plugSpec.setHasOnlySubstanceUnits((Boolean) evt.getNewValue());
      plugin.notifySBaseChanged(plugSpec);
    } else if (prop.equals(TreeNodeChangeEvent.history)) {
      logger.log(Level.DEBUG, MessageFormat.format(
        bundle.getString("CHANGING_VALUE_NOT_SUPPORTED"),
        evtSrc.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.id)) {
      //This is unused in  this form
    } else if (prop.equals(TreeNodeChangeEvent.initialAmount)) {
      Species spec = (Species) evtSrc;
      PluginSpecies plugSpec = (PluginSpecies) spec.getUserObject(LINK_TO_CELLDESIGNER);
      plugSpec.setInitialAmount(spec.getInitialAmount());
      plugSpec.setInitialConcentration(spec.getInitialConcentration());
    } else if (prop.equals(TreeNodeChangeEvent.initialValue)) {
      if (evtSrc instanceof Trigger) {
        logger.log(Level.DEBUG, MessageFormat.format(
          bundle.getString("CHANGING_VALUE_IN_MODEL_REQURES_L3"),
          evtSrc.getClass().getSimpleName()));
      } else if (evtSrc instanceof ASTNode) {
        // Do nothing.
      }
    } else if (prop.equals(TreeNodeChangeEvent.isEOF)) {
      logger.log(Level.DEBUG, MessageFormat.format(
        bundle.getString("CANNOT_FIRE_PROPERTY_CHANGE"),
        evt.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.isExplicitlySetConstant)) {
      LocalParameter lpam = (LocalParameter) evtSrc;
      PluginParameter ppam = (PluginParameter) lpam.getUserObject(LINK_TO_CELLDESIGNER);
      if ((Boolean) evt.getNewValue()) {
        ppam.setConstant(true);
        plugin.notifySBaseChanged(ppam);
      }
    } else if (prop.equals(TreeNodeChangeEvent.isSetNumberType)) {
      logger.log(Level.DEBUG, MessageFormat.format(
        bundle.getString("CANNOT_CHANGE_ELEMENT"),
        evtSrc.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.kind)) {
      Unit ut = (Unit) evtSrc;
      PluginUnit plugUnit = (PluginUnit) ut.getUserObject(LINK_TO_CELLDESIGNER);
      //the new value can be either a string or an integer ==> therefore this separation is necessary
      if (evt.getNewValue() instanceof String) {
        plugUnit.setKind(evt.getNewValue().toString());
      } else {
        plugUnit.setKind((Integer) evt.getNewValue());
      }
      plugin.notifySBaseChanged(plugUnit);
    } else if (prop.equals(TreeNodeChangeEvent.kineticLaw)) {
      logger.log(Level.DEBUG, MessageFormat.format(
        bundle.getString("CANNOT_FIRE_PROPERTY_CHANGE"),
        evt.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.lengthUnits)) {
      logger.log(Level.DEBUG, MessageFormat.format(
        bundle.getString("CHANGING_VALUE_IN_MODEL_REQURES_L3"),
        evtSrc.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.level)) {
      logger.log(Level.DEBUG, MessageFormat.format(
        bundle.getString("CANNOT_FIRE_PROPERTY_CHANGE"),
        evt.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.listOfUnits)) {
      // This would be a nodeAdded event
    } else if (prop.equals(TreeNodeChangeEvent.mantissa)) {
      ASTNode n = (ASTNode) evtSrc;
      MathContainer mc = n.getParentSBMLObject();
      ASTNode node = (ASTNode) evtSrc;
      ((org.sbml.libsbml.ASTNode) node.getUserObject(LINK_TO_LIBSBML)).setValue(((Double) evt.getNewValue()).doubleValue(), node.getExponent());
      plugin.notifySBaseChanged((PluginSBase) mc.getUserObject(LINK_TO_CELLDESIGNER));
    } else if (prop.equals(TreeNodeChangeEvent.math)) {
      // This will be node added or node deleted
    } else if (prop.equals(TreeNodeChangeEvent.message)) {
      PluginConstraint pcon = (PluginConstraint) ((SBase) evtSrc).getUserObject(LINK_TO_CELLDESIGNER);
      XMLNode message = (XMLNode) evt.getNewValue();
      pcon.setMessage((message == null) ? null : message.toXMLString());
    } else if (prop.equals(TreeNodeChangeEvent.metaId)) {
      logger.log(Level.DEBUG, MessageFormat.format(
        bundle.getString("CANNOT_FIRE_PROPERTY_CHANGE"),
        evt.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.model)) {
      // This will be node added or node deleted
    } else if (prop.equals(TreeNodeChangeEvent.modifiedDate)) {
      logger.log(Level.DEBUG, MessageFormat.format(
        bundle.getString("CANNOT_FIRE_PROPERTY_CHANGE"),
        evtSrc.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.multiplier)) {
      Unit u = (Unit) evtSrc;
      UnitDefinition ud = (UnitDefinition) u.getParentSBMLObject().getParentSBMLObject();
      PluginUnitDefinition pud = plugModel.getUnitDefinition(ud.getId());
      u.setMultiplier(((Double) evt.getNewValue()).doubleValue());
      plugin.notifySBaseChanged(pud);
    } else if (prop.equals(TreeNodeChangeEvent.name)) {
      PluginSBase plugSBase = (PluginSBase) ((SBase) evtSrc).getUserObject(LINK_TO_CELLDESIGNER);
      try {
        Method method = plugSBase.getClass().getMethod("setName", String.class);
        method.invoke(plugSBase, evt.getNewValue().toString());
        plugin.notifySBaseChanged(plugSBase);
      } catch (Throwable exc) {
        logger.log(Level.DEBUG, MessageFormat.format(
          bundle.getString("CANNOT_CHANGE_ELEMENT"), plugSBase.getClass().getSimpleName()));
      }
    } else if (prop.equals(TreeNodeChangeEvent.namespace)) {
      logger.log(Level.DEBUG, MessageFormat.format(
        bundle.getString("CANNOT_FIRE_PROPERTY_CHANGE"),
        evt.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.nonRDFAnnotation)) {
      logger.log(Level.DEBUG, MessageFormat.format(
        bundle.getString("CHANGING_VALUE_NOT_SUPPORTED"),
        evtSrc.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.notes)) {
      SBase sbase = (SBase) evtSrc;
      PluginSBase plugSBase = (PluginSBase) sbase.getUserObject(LINK_TO_CELLDESIGNER);
      plugSBase.setNotes(SBMLtools.toXML(sbase.getNotes()));
    } else if (prop.equals(TreeNodeChangeEvent.numerator)) {
      ASTNode n = (ASTNode) evtSrc;
      MathContainer mc = n.getParentSBMLObject();
      ASTNode node = (ASTNode) evtSrc;
      ((org.sbml.libsbml.ASTNode) node.getUserObject(LINK_TO_LIBSBML)).setValue((Double) evt.getNewValue(), node.getDenominator());
      plugin.notifySBaseChanged((PluginSBase) mc.getUserObject(LINK_TO_CELLDESIGNER));
    } else if (prop.equals(TreeNodeChangeEvent.offset)) {
      Unit u = (Unit) evtSrc;
      PluginUnit plugU = (PluginUnit) u.getUserObject(LINK_TO_CELLDESIGNER);
      plugU.setOffset(((Double) evt.getNewValue()).doubleValue());
      plugin.notifySBaseChanged(plugU);
    } else if (prop.equals(TreeNodeChangeEvent.organization)) {
      logger.log(Level.DEBUG, MessageFormat.format(
        bundle.getString("CHANGING_VALUE_NOT_SUPPORTED"),
        evtSrc.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.outside)) {
      Compartment c = (Compartment) evtSrc;
      PluginCompartment plugC = (PluginCompartment) c.getUserObject(LINK_TO_CELLDESIGNER);
      plugC.setOutside(c.getOutside());
      plugin.notifySBaseChanged(plugC);
    } else if (prop.equals(TreeNodeChangeEvent.parentSBMLObject)) {
      // We do not care bout that here!
    } else if (prop.equals(TreeNodeChangeEvent.persistent)) {
      logger.log(Level.DEBUG, MessageFormat.format(
        bundle.getString("CHANGING_VALUE_IN_MODEL_REQURES_L3"),
        evtSrc.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.priority)) {
      logger.log(Level.DEBUG, MessageFormat.format(
        bundle.getString("CHANGING_VALUE_NOT_SUPPORTED"),
        evtSrc.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.qualifier)) {
      CVTerm cvt = (CVTerm) evtSrc;
      org.sbml.libsbml.CVTerm libTerm = (org.sbml.libsbml.CVTerm) cvt.getUserObject(LINK_TO_LIBSBML);
      libTerm.setQualifierType(LibSBMLUtils.convertCVTermQualifierType(cvt.getQualifierType()));
      PluginSBase base = (PluginSBase) ((SBase) cvt.getParent().getParent()).getUserObject(LINK_TO_CELLDESIGNER);
      plugin.notifySBaseChanged(base);
    } else if (prop.equals(TreeNodeChangeEvent.reversible)) {
      Reaction r = (Reaction) evtSrc;
      PluginReaction plugR = (PluginReaction) r.getUserObject(LINK_TO_CELLDESIGNER);
      plugR.setReversible((Boolean) evt.getNewValue());
      plugin.SBaseChanged(plugR);
    } else if (prop.equals(TreeNodeChangeEvent.sboTerm)) {
      SBase abs = (SBase) evtSrc;
      PluginSBase pabs = (PluginSBase) abs.getUserObject(LINK_TO_CELLDESIGNER);
      if (PluginUtils.convertSBOterm(abs, pabs)) {
        plugin.notifySBaseChanged(pabs);
      }
    } else if (prop.equals(TreeNodeChangeEvent.scale)) {
      Unit u = (Unit) evtSrc;
      PluginUnit psb = (PluginUnit) u.getUserObject(LINK_TO_CELLDESIGNER);
      psb.setScale((Integer) evt.getNewValue());
      plugin.notifySBaseChanged(psb);
    } else if (prop.equals(TreeNodeChangeEvent.setAnnotation)) {
      SBase abs = (SBase) evtSrc;
      Annotation annotation = (Annotation) evt.getNewValue();
      if (annotation.getCVTermCount() > 0) {
        PluginSBase pabs = (PluginSBase) abs.getUserObject(LINK_TO_CELLDESIGNER);
        for (CVTerm term : annotation.getListOfCVTerms()) {
          pabs.addCVTerm(LibSBMLUtils.convertCVTerm(term));
        }
        plugin.notifySBaseChanged(pabs);
      }
    } else if (prop.equals(TreeNodeChangeEvent.size)) {
      Compartment c = (Compartment) evtSrc;
      PluginCompartment plugC = (PluginCompartment) c.getUserObject(LINK_TO_CELLDESIGNER);
      plugC.setSize(((Double) evt.getNewValue()).doubleValue());
      plugin.notifySBaseChanged(plugC);
    } else if (prop.equals(TreeNodeChangeEvent.spatialDimensions)) {
      Compartment c = (Compartment) evtSrc;
      PluginCompartment plugC = (PluginCompartment) c.getUserObject(LINK_TO_CELLDESIGNER);
      Double value = (Double) evt.getNewValue();
      if (value != null) {
        long spatialDim = (long) value.doubleValue();
        double diff = spatialDim - value.doubleValue();
        plugC.setSpatialDimensions(spatialDim);
        plugin.notifySBaseChanged(plugC);
        if (diff != 0d) {
          logger.log(Level.DEBUG, MessageFormat.format(bundle.getString("ROUNDING_ERROR"), diff));
        }
      }
    } else if (prop.equals(TreeNodeChangeEvent.spatialSizeUnits)) {
      Species spec = (Species) evtSrc;
      PluginSpecies plugSpec = (PluginSpecies) spec.getUserObject(LINK_TO_CELLDESIGNER);
      plugSpec.setSpatialSizeUnits(evt.getNewValue().toString());
      plugin.notifySBaseChanged(plugSpec);
    } else if (prop.equals(TreeNodeChangeEvent.species)) {
      // CellDesigner does not provide a method to change the species in a Plugin(Simple/Modifier)SpeciesReference.
      logger.log(Level.DEBUG, MessageFormat.format(
        bundle.getString("CANNOT_CHANGE_ELEMENT"), evtSrc));
    } else if (prop.equals(TreeNodeChangeEvent.speciesType)) {
      PluginSpecies plugSpec = (PluginSpecies) ((Species) evtSrc).getUserObject(LINK_TO_CELLDESIGNER);
      plugSpec.setSpeciesType(evt.getNewValue().toString());
      plugin.notifySBaseChanged(plugSpec);
    } else if (prop.equals(TreeNodeChangeEvent.stoichiometry)) {
      PluginSpeciesReference plugSpec = (PluginSpeciesReference) ((SpeciesReference) evtSrc).getUserObject(LINK_TO_CELLDESIGNER);
      plugSpec.setStoichiometry(((Double) evt.getNewValue()).doubleValue());
      plugin.notifySBaseChanged(plugSpec);
    } else if (prop.equals(TreeNodeChangeEvent.style)) {
      //      ASTNode n = (ASTNode) evtSrc;
      //      org.sbml.libsbml.ASTNode libAST = (org.sbml.libsbml.ASTNode) n.getUserObject(LINK_TO_LIBSBML);
      //libAST.setStyle(evt.getNewValue().toString());
      //plugin.notifySBaseChanged((PluginSBase) n.getParentSBMLObject().getUserObject(LINK_TO_CELLDESIGNER));
    } else if (prop.equals(TreeNodeChangeEvent.substanceUnits)) {
      if (evtSrc instanceof KineticLaw) {
        KineticLaw klaw = (KineticLaw) evtSrc;
        PluginReaction plugReac = plugModel.getReaction(klaw.getParent().getId());
        plugReac.getKineticLaw().setSubstanceUnits(klaw.getSubstanceUnits());
        plugin.notifySBaseChanged(plugReac);
      } else if (evtSrc instanceof Model) {
        logger.log(Level.DEBUG, MessageFormat.format(
          bundle.getString("CHANGING_VALUE_IN_MODEL_REQURES_L3"), prop));
      }
    } else if (prop.equals(TreeNodeChangeEvent.SBMLDocumentAttributes)) {
      logger.log(Level.DEBUG, MessageFormat.format(
        bundle.getString("CANNOT_FIRE_PROPERTY_CHANGE"),
        evt.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.text)) {
      XMLToken token = (XMLToken) evtSrc;
      org.sbml.libsbml.XMLToken libToken = (org.sbml.libsbml.XMLToken) token.getUserObject(LINK_TO_LIBSBML);
      String chars = libToken.getCharacters();
      libToken.append((evt.getNewValue().toString()).substring(chars.length()));
    } else if (prop.equals(TreeNodeChangeEvent.timeUnits)) {
      if (evtSrc instanceof Event) {
        PluginEvent pEvt = (PluginEvent) ((Event) evtSrc).getUserObject(LINK_TO_CELLDESIGNER);
        pEvt.setTimeUnits(evt.getNewValue().toString());
        plugin.notifySBaseChanged(pEvt);
      } else if (evtSrc instanceof KineticLaw) {
        PluginKineticLaw pKlaw = (PluginKineticLaw) ((KineticLaw) evtSrc).getUserObject(LINK_TO_CELLDESIGNER);
        pKlaw.setTimeUnits(evt.getNewValue().toString());
        plugin.notifySBaseChanged(pKlaw);
      } else if (evtSrc instanceof Model) {
        logger.log(Level.DEBUG, MessageFormat.format(
          bundle.getString("CHANGING_VALUE_IN_MODEL_REQURES_L3"),
          evtSrc.getClass().getSimpleName()));
      }
    } else if (prop.equals(TreeNodeChangeEvent.type)) {
      if (evtSrc instanceof ASTNode) {
        ASTNode ast = (ASTNode) evtSrc;
        ((org.sbml.libsbml.ASTNode) ast.getUserObject(LINK_TO_LIBSBML)).setType(LibSBMLUtils.convertASTNodeType((ASTNode.Type) evt.getNewValue()));
        plugin.notifySBaseChanged((PluginSBase) ast.getParentSBMLObject().getUserObject(LINK_TO_CELLDESIGNER));
      } else if (evtSrc instanceof CVTerm) {
        CVTerm term = (CVTerm) evtSrc;
        ((org.sbml.libsbml.CVTerm) term.getUserObject(LINK_TO_LIBSBML)).setQualifierType(LibSBMLUtils.convertCVTermQualifierType(((CVTerm.Type) evt.getNewValue())));
        plugin.notifySBaseChanged((PluginSBase) ((SBase) term.getParent().getParent()).getUserObject(LINK_TO_CELLDESIGNER));
      }
    } else if (prop.equals(TreeNodeChangeEvent.units)) {
      if (evtSrc instanceof ASTNode) {
        //ASTNode n = (ASTNode) evtSrc;
        //((org.sbml.libsbml.ASTNode) n.getUserObject(LINK_TO_LIBSBML)).setUnits(evt.getNewValue().toString());
        //plugin.notifySBaseChanged((PluginSBase) n.getParentSBMLObject().getUserObject(LINK_TO_CELLDESIGNER));
      } else if (evtSrc instanceof ExplicitRule) {
        logger.log(Level.DEBUG, MessageFormat.format(
          bundle.getString("CANNOT_CHANGE_ELEMENT"), evtSrc));
      } else {
        PluginSBase pSBase = (PluginSBase) ((SBase) evtSrc).getUserObject(LINK_TO_CELLDESIGNER);
        if (evtSrc instanceof KineticLaw) {
          PluginKineticLaw pklaw = (PluginKineticLaw) pSBase;
          pklaw.setTimeUnits(evt.getNewValue().toString());
        } else if ((evtSrc instanceof LocalParameter) || (evtSrc instanceof Parameter)) {
          PluginParameter pparam = (PluginParameter) pSBase;
          pparam.setUnits(evt.getNewValue().toString());
        } else if (evtSrc instanceof Species) {
          PluginSpecies pSpec = (PluginSpecies) pSBase;
          pSpec.setUnits(evt.getNewValue().toString());
        } else if (evtSrc instanceof Compartment) {
          PluginCompartment pComp = (PluginCompartment) pSBase;
          pComp.setUnits(evt.getNewValue().toString());
        }
        plugin.notifySBaseChanged(pSBase);
      }
    } else if (prop.equals(TreeNodeChangeEvent.unsetCVTerms)) {
      logger.log(Level.DEBUG, MessageFormat.format(
        bundle.getString("CANNOT_CHANGE_ELEMENT"), evtSrc));
    } else if (prop.equals(TreeNodeChangeEvent.userObject)) {
      // NO user objects in CellDesigner.
    } else if (prop.equals(TreeNodeChangeEvent.useValuesFromTriggerTime)) {
      PluginEvent plugEvt = (PluginEvent) ((Event) evtSrc).getUserObject(LINK_TO_CELLDESIGNER);
      plugEvt.setUseValuesFromTriggerTime((Boolean) evt.getNewValue());
      plugin.notifySBaseChanged(plugEvt);
    } else if (prop.equals(TreeNodeChangeEvent.value)) {
      if (evtSrc instanceof ASTNode) {
        ASTNode n = (ASTNode) evtSrc;
        if (evt.getNewValue() instanceof Double) {
          ((org.sbml.libsbml.ASTNode) n.getUserObject(LINK_TO_LIBSBML)).setValue(((Double) evt.getNewValue()).doubleValue());
        } else {
          ((org.sbml.libsbml.ASTNode) n.getUserObject(LINK_TO_LIBSBML)).setValue((Character) evt.getNewValue());
        }
        plugin.notifySBaseChanged((PluginSBase) (n.getParentSBMLObject()).getUserObject(LINK_TO_CELLDESIGNER));
      } else {
        PluginSBase plugSBase = (PluginSBase) ((SBase) evtSrc).getUserObject(LINK_TO_CELLDESIGNER);
        if ((evtSrc instanceof LocalParameter) || (evtSrc instanceof Parameter)) {
          ((PluginParameter) plugSBase).setValue(((Double) evt.getNewValue()).doubleValue());
        } else if (evtSrc instanceof Species) {
          Species species = (Species) evtSrc;
          if (species.isSetInitialAmount()) {
            ((PluginSpecies) plugSBase).setInitialAmount(((Double) evt.getNewValue()).doubleValue());
          } else {
            ((PluginSpecies) plugSBase).setInitialConcentration(((Double) evt.getNewValue()).doubleValue());
          }
        } else if (evtSrc instanceof Compartment) {
          ((PluginCompartment) plugSBase).setSize(((Double) evt.getNewValue()).doubleValue());
        }
        plugin.notifySBaseChanged(plugSBase);
      }
    } else if (prop.equals(TreeNodeChangeEvent.variable)) {
      if (evtSrc instanceof ASTNode) {
        String id = evt.getNewValue() instanceof NamedSBase ? ((NamedSBase) evt.getNewValue()).getId() : evt.getNewValue().toString();
        ((org.sbml.libsbml.ASTNode) ((ASTNode) evtSrc).getUserObject(LINK_TO_LIBSBML)).setName(id);
      } else {
        PluginSBase pSBase = (PluginSBase) ((SBase) evtSrc).getUserObject(LINK_TO_CELLDESIGNER);
        if (evtSrc instanceof EventAssignment) {
          ((PluginEventAssignment) pSBase).setVariable(evt.getNewValue().toString());
        } else if (evtSrc instanceof ExplicitRule) {
          if (evtSrc instanceof AssignmentRule) {
            ((PluginAssignmentRule) pSBase).setVariable(evt.getNewValue().toString());
          } else {
            ((PluginRateRule) pSBase).setVariable(evt.getNewValue().toString());
          }
        } else if (evtSrc instanceof InitialAssignment) {
          logger.log(Level.DEBUG, MessageFormat.format(
            bundle.getString("CANNOT_CHANGE_ELEMENT"),
            evtSrc.getClass().getSimpleName()));
        }
        plugin.notifySBaseChanged(pSBase);
      }
    } else if (prop.equals(TreeNodeChangeEvent.version)) {
      logger.log(Level.DEBUG, MessageFormat.format(
        bundle.getString("CHANGING_VALUE_NOT_SUPPORTED"),
        TreeNodeChangeEvent.version));
    } else if (prop.equals(TreeNodeChangeEvent.volume)) {
      // won't happen because it is changing the "value" of the compartment.
    } else if (prop.equals(TreeNodeChangeEvent.volumeUnits)) {
      logger.log(Level.DEBUG, MessageFormat.format(
        bundle.getString("CHANGING_VALUE_IN_MODEL_REQURES_L3"),
        evtSrc.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.xmlTriple)) {
      logger.log(Level.DEBUG, MessageFormat.format(
        bundle.getString("CANNOT_FIRE_PROPERTY_CHANGE"),
        evtSrc.getClass().getSimpleName()));
    }
    else if (evtSrc instanceof GraphicalObject)
    {
      LayoutPluginChangeListener.addOrChangeGlyphInfoToCellDesigner((GraphicalObject)evtSrc, plugModel);
    }
  }

}
