/*
 * $Id: AbstractCellDesignerPlugin.java 2109 2015-01-05 04:50:45Z andreas-draeger $
 * $URL: svn://svn.code.sf.net/p/jsbml/code/trunk/modules/celldesigner/src/org/sbml/jsbml/celldesigner/AbstractCellDesignerPlugin.java $
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
 * 6. Marquette University, Milwaukee, WI, USA
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.celldesigner;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingWorker;
import javax.xml.stream.XMLStreamException;

import jp.sbi.celldesigner.plugin.CellDesignerPlugin;
import jp.sbi.celldesigner.plugin.PluginAlgebraicRule;
import jp.sbi.celldesigner.plugin.PluginAssignmentRule;
import jp.sbi.celldesigner.plugin.PluginCompartment;
import jp.sbi.celldesigner.plugin.PluginCompartmentType;
import jp.sbi.celldesigner.plugin.PluginConstraint;
import jp.sbi.celldesigner.plugin.PluginEvent;
import jp.sbi.celldesigner.plugin.PluginFunctionDefinition;
import jp.sbi.celldesigner.plugin.PluginInitialAssignment;
import jp.sbi.celldesigner.plugin.PluginModel;
import jp.sbi.celldesigner.plugin.PluginParameter;
import jp.sbi.celldesigner.plugin.PluginRateRule;
import jp.sbi.celldesigner.plugin.PluginReaction;
import jp.sbi.celldesigner.plugin.PluginSBase;
import jp.sbi.celldesigner.plugin.PluginSpecies;
import jp.sbi.celldesigner.plugin.PluginSpeciesAlias;
import jp.sbi.celldesigner.plugin.PluginSpeciesType;
import jp.sbi.celldesigner.plugin.PluginUnitDefinition;

import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.util.TreeNodeChangeListener;

/**
 * An implementation of a CellDesigner plug-in that takes care of
 * synchronization between CellDesigner and corresponding JSBML data structures.
 *
 * @author Andreas Dr&auml;ger
 * @version $Rev: 2109 $
 * @since 1.0
 * @date 13.02.2014
 */
public abstract class AbstractCellDesignerPlugin extends CellDesignerPlugin implements Runnable {

  /**
   * 
   */
  public static final String ACTION = "Abstract CellDesigner Plugin";
  /**
   * 
   */
  public static final String APPLICATION_NAME = "Abstraction";

  /**
   * Converts CellDesigner's plug-in data structure into a JSBML data structure.
   */
  private PluginSBMLReader reader;
  /**
   * Creates CellDesigner's plug-in data structure for a given JSBML structure.
   */
  private PluginSBMLWriter writer;
  /**
   * The current SBMLDocument in this plugin
   */
  protected SBMLDocument document = null;
  /**
   * An instance of AbstractCellDesignerPlugin.
   */
  final AbstractCellDesignerPlugin plugin = this;

  /**
   * Start up the minimal plugin read/write interface
   */
  public AbstractCellDesignerPlugin() {
    super();
    System.out.println("Loading plugin " + getClass().getName());
    try {
      // Initialize CellDesigner/JSBML communication interface
      reader = new PluginSBMLReader();
      writer = new PluginSBMLWriter(this);
    } catch (Throwable t) {
      new GUIErrorConsole(t);
    }
  }

  /**
   * @return the reader
   */
  public PluginSBMLReader getReader() {
    return reader;
  }

  /**
   * @return the writer
   */
  public PluginSBMLWriter getWriter() {
    return writer;
  }

  /**
   * 
   * @return the SBMLDocument
   */
  protected SBMLDocument getSBMLDocument()
  {
    return document;
  }

  /**
   * Visualizes underlying HashMap which stores all
   * PluginSBases and their associated JSBML objects.
   */
  protected void printMap()
  {
    reader.printMap();
  }

  /**
   * @throws XMLStreamException
   * Get the SBMLDocument from the currently selected CellDesigner PluginModel and run the plugin.
   */
  public void startPlugin() throws XMLStreamException
  {
    setStarted(true);
    getReader().clearMap();
    SwingWorker<SBMLDocument, Throwable> worker = new SBMLDocumentWorker(getReader(), getSelectedModel());
    worker.addPropertyChangeListener(new PropertyChangeListener() {
      @Override
      public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state") && evt.getNewValue().equals(SwingWorker.StateValue.DONE)) {
          try {
            SBMLDocumentWorker swing = (SBMLDocumentWorker)evt.getSource();
            document = swing.get();
            document.addTreeNodeChangeListener(new PluginChangeListener(plugin));
            run();
          } catch (Throwable e) {
            new GUIErrorConsole(e);
          }
        }
      }
    });
    worker.execute();
  }

  /**
   * @return the current list of TreeNodeChangeListeners for this SBMLDocument
   */
  private List<TreeNodeChangeListener> copyTreeNodeChangeListeners()
  {
    List<TreeNodeChangeListener> treeNodeList = new ArrayList<TreeNodeChangeListener>();
    if (document.getTreeNodeChangeListenerCount()>0)
    {
      treeNodeList.addAll(document.getListOfTreeNodeChangeListeners());
    }
    return treeNodeList;
  }

  /* (non-Javadoc)
   * @see jp.sbi.celldesigner.plugin.CellDesignerPlug#modelClosed(jp.sbi.celldesigner.plugin.PluginSBase)
   */
  /**
   * There is nothing that needs to be done when a CellDesigner model is closed. See {@link #modelSelectChanged(PluginSBase)}
   * for events related to a model closing.
   */
  @Override
  public void modelClosed(PluginSBase sbase) {
  }

  /* (non-Javadoc)
   * @see jp.sbi.celldesigner.plugin.CellDesignerPlug#modelOpened(jp.sbi.celldesigner.plugin.PluginSBase)
   */
  /**
   * This method is always followed by a call to modelSelectChanged(PluginSBase). <p>
   * See {@link #modelSelectChanged(PluginSBase)} for events related to the opening of a new model.
   */
  @Override
  public void modelOpened(PluginSBase sbase) {
  }

  /* (non-Javadoc)
   * @see jp.sbi.celldesigner.plugin.CellDesignerPlug#modelSelectChanged(jp.sbi.celldesigner.plugin.PluginSBase)
   */
  /**
   * Called by CellDesigner when a new CellDesigner model is focused on by the user. As such, we need to
   * convert this model to JSBML and add the model to the HashMap which stores all PluginSBases and their associated JSBML objects.
   */
  @Override
  public void modelSelectChanged(PluginSBase sbase) {
    if (sbase instanceof PluginModel)
    {
      Map<PluginSBase, Set<SBase>> map = reader.getPluginSBase_SBaseMappings();
      map.clear();
      try {
        PluginModel pModel = (PluginModel)sbase;
        Model model = reader.convertModel(pModel);
        document = new SBMLDocument(model.getLevel(), model.getVersion());
        document.setModel(model);

        Set<SBase> sBaseSet = new HashSet<SBase>();
        sBaseSet.add(model);
        PluginUtils.transferNamedSBaseProperties(pModel, model);

        map.put(pModel, sBaseSet);
      }
      catch (Throwable e) {
        new GUIErrorConsole(e);
      }
    }
  }

  /* (non-Javadoc)
   * @see jp.sbi.celldesigner.plugin.CellDesignerPlug#SBaseAdded(jp.sbi.celldesigner.plugin.PluginSBase)
   */
  /**
   * Called by CellDesigner when a new PluginSBase is added to the CellDesigner Model. The identity of the
   * PluginSBase is determined and then passed onto another method for addition.
   */
  @Override
  public void SBaseAdded(PluginSBase sbase) {
    Map<PluginSBase, Set<SBase>> map = reader.getPluginSBase_SBaseMappings();
    List<TreeNodeChangeListener> treeNodeList = copyTreeNodeChangeListeners();
    document.removeAllTreeNodeChangeListeners(true);

    if (sbase instanceof PluginCompartment)
    {
      PluginCompartment pCompartment = (PluginCompartment)sbase;
      PluginSBaseEventUtils.pluginCompartmentAdded(reader, pCompartment, map, document);
    }
    else if (sbase instanceof PluginSpecies)
    {
      PluginSpecies pSpecies = (PluginSpecies) sbase;
      PluginSBaseEventUtils.pluginSpeciesAdded(reader, pSpecies, map, document);
    }
    else if (sbase instanceof PluginSpeciesAlias)
    {
      PluginSpeciesAlias pSpeciesAlias = (PluginSpeciesAlias) sbase;
      PluginSBaseEventUtils.pluginSpeciesAliasAdded(pSpeciesAlias, map, document);
    }
    else if (sbase instanceof PluginReaction)
    {
      PluginReaction pReaction = (PluginReaction) sbase;
      PluginSBaseEventUtils.pluginReactionAdded(reader, pReaction, map, document);
    }
    else if (sbase instanceof PluginParameter)
    {
      PluginParameter pParameter = (PluginParameter) sbase;
      PluginSBaseEventUtils.pluginParameterAdded(reader, pParameter, map, document);
    }
    else if (sbase instanceof PluginAlgebraicRule)
    {
      PluginAlgebraicRule pAlgebraicRule = (PluginAlgebraicRule) sbase;
      PluginSBaseEventUtils.pluginAlgebraicRuleAdded(reader, pAlgebraicRule, map, document);
    }
    else if (sbase instanceof PluginAssignmentRule)
    {
      PluginAssignmentRule pAssignmentRule = (PluginAssignmentRule) sbase;
      PluginSBaseEventUtils.pluginAssignmentRuleAdded(reader, pAssignmentRule, map, document);
    }
    else if (sbase instanceof PluginRateRule)
    {
      PluginRateRule pRateRule = (PluginRateRule) sbase;
      PluginSBaseEventUtils.pluginRateRuleAdded(reader, pRateRule, map, document);
    }
    else if (sbase instanceof PluginUnitDefinition)
    {
      PluginUnitDefinition pUnitDefinition = (PluginUnitDefinition) sbase;
      PluginSBaseEventUtils.pluginUnitDefinitionAdded(reader, pUnitDefinition, map, document);
    }
    else if (sbase instanceof PluginInitialAssignment)
    {
      PluginInitialAssignment pInitialAssignment = (PluginInitialAssignment) sbase;
      PluginSBaseEventUtils.pluginInitialAssignmentAdded(reader, pInitialAssignment, map, document);
    }
    else if (sbase instanceof PluginEvent)
    {
      PluginEvent pEvent = (PluginEvent)sbase;
      PluginSBaseEventUtils.pluginEventAdded(reader, pEvent, map, document);
    }
    else if (sbase instanceof PluginConstraint)
    {
      PluginConstraint pConstraint = (PluginConstraint)sbase;
      PluginSBaseEventUtils.pluginConstraintAdded(reader, pConstraint, map, document);
    }
    else if (sbase instanceof PluginFunctionDefinition)
    {
      PluginFunctionDefinition pFunctionDefinition = (PluginFunctionDefinition)sbase;
      PluginSBaseEventUtils.pluginFunctionDefinitionAdded(reader, pFunctionDefinition, map, document);
    }
    else if (sbase instanceof PluginCompartmentType)
    {
      PluginCompartmentType pCompartmentType = (PluginCompartmentType)sbase;
      PluginSBaseEventUtils.pluginCompartmentTypeAdded(reader, pCompartmentType, map, document);
    }
    else if (sbase instanceof PluginSpeciesType)
    {
      PluginSpeciesType pSpeciesType = (PluginSpeciesType)sbase;
      PluginSBaseEventUtils.pluginSpeciesTypeAdded(reader, pSpeciesType, map, document);
    }
    document.addAllChangeListeners(treeNodeList);
  }

  /* (non-Javadoc)
   * @see jp.sbi.celldesigner.plugin.CellDesignerPlug#SBaseChanged(jp.sbi.celldesigner.plugin.PluginSBase)
   */
  /**
   * Called by CellDesigner when a PluginSBase is changed. The identity of the PluginSBase is
   * determined and then passed onto another method for rebuilding of the HashMap which stores all
   * PluginSBases and their associated JSBML objects.
   */
  @Override
  public void SBaseChanged(PluginSBase sbase) {
    try {
      Map<PluginSBase, Set<SBase>> map = reader.getPluginSBase_SBaseMappings();
      List<TreeNodeChangeListener> treeNodeList = copyTreeNodeChangeListeners();
      document.removeAllTreeNodeChangeListeners(true);

      if (sbase instanceof PluginCompartment)
      {
        PluginCompartment pCompartment = (PluginCompartment)sbase;
        PluginSBaseEventUtils.pluginCompartmentChanged(reader, pCompartment, map, document);
      }

      else if (sbase instanceof PluginSpecies)
      {
        PluginSBaseEventUtils.pluginSpeciesChangedOrDeleted(reader, getSelectedModel(), map, document);
      }

      else if (sbase instanceof PluginSpeciesAlias)
      {
        PluginSBaseEventUtils.pluginSpeciesAliasChanged(reader, getSelectedModel(), map, document);
      }

      else if (sbase instanceof PluginReaction)
      {
        PluginSBaseEventUtils.pluginReactionChanged(reader, getSelectedModel(), map, document);
      }
      else if (sbase instanceof PluginParameter)
      {
        PluginSBaseEventUtils.pluginParameterChangedOrDeleted(reader, getSelectedModel(), map, document);
      }
      else if (sbase instanceof PluginAlgebraicRule)
      {
        PluginSBaseEventUtils.pluginRuleChangedOrDeleted(reader, getSelectedModel(), map, document);
      }
      else if (sbase instanceof PluginAssignmentRule)
      {
        PluginSBaseEventUtils.pluginRuleChangedOrDeleted(reader, getSelectedModel(), map, document);
      }
      else if (sbase instanceof PluginRateRule)
      {
        PluginSBaseEventUtils.pluginRuleChangedOrDeleted(reader, getSelectedModel(), map, document);
      }
      else if (sbase instanceof PluginUnitDefinition)
      {
        PluginSBaseEventUtils.pluginUnitDefinitionChangedOrDeleted(reader, getSelectedModel(), map, document);
      }
      else if (sbase instanceof PluginInitialAssignment)
      {
        PluginSBaseEventUtils.pluginInitialAssignmentChangedOrDeleted(reader, getSelectedModel(), map, document);
      }
      else if (sbase instanceof PluginEvent)
      {
        PluginSBaseEventUtils.pluginEventChangedOrDeleted(reader, getSelectedModel(), map, document);
      }
      else if (sbase instanceof PluginConstraint)
      {
        PluginSBaseEventUtils.pluginConstraintChangedOrDeleted(reader, getSelectedModel(), map, document);
      }
      else if (sbase instanceof PluginFunctionDefinition)
      {
        PluginSBaseEventUtils.pluginFunctionDefinitionChangedOrDeleted(reader, getSelectedModel(), map, document);
      }
      else if (sbase instanceof PluginCompartmentType)
      {
        PluginSBaseEventUtils.pluginCompartmentTypeChangedOrDeleted(reader, getSelectedModel(), map, document);
      }
      else if (sbase instanceof PluginSpeciesType)
      {
        PluginSBaseEventUtils.pluginSpeciesTypeChangedOrDeleted(reader, getSelectedModel(), map, document);
      }
      document.addAllChangeListeners(treeNodeList);
    }
    catch (Throwable e) {
      new GUIErrorConsole(e);
    }
  }


  /* (non-Javadoc)
   * @see jp\nbi.celldesigner.plugin.CellDesignerPlug#SBaseDeleted(jp.sbi.celldesigner.plugin.PluginSBase)
   */
  /**
   * Called by CellDesigner when a PluginSBase is deleted. The identity of the PluginSBase is
   * determined and then passed onto another method for rebuilding of the HashMap which stores all
   * PluginSBases and their associated JSBML objects.
   */
  @Override
  public void SBaseDeleted(PluginSBase sbase) {
    Map<PluginSBase, Set<SBase>> map = reader.getPluginSBase_SBaseMappings();

    List<TreeNodeChangeListener> treeNodeList = copyTreeNodeChangeListeners();
    document.removeAllTreeNodeChangeListeners(true);

    if (sbase instanceof PluginCompartment)
    {
      PluginCompartment pCompartment = (PluginCompartment)sbase;
      PluginSBaseEventUtils.pluginCompartmentDeleted(reader, pCompartment, map, document);
    }
    else if (sbase instanceof PluginSpecies)
    {
      PluginSBaseEventUtils.pluginSpeciesChangedOrDeleted(reader, getSelectedModel(), map, document);
    }
    else if (sbase instanceof PluginSpeciesAlias)
    {
      PluginSpeciesAlias pSpeciesAlias = (PluginSpeciesAlias) sbase;
      PluginSBaseEventUtils.pluginSpeciesAliasDeleted(reader, getSelectedModel(), map, document, pSpeciesAlias);
    }
    else if (sbase instanceof PluginReaction)
    {
      PluginReaction pReaction = (PluginReaction) sbase;
      PluginSBaseEventUtils.pluginReactionDeleted(reader, getSelectedModel(), map, document, pReaction);
    }
    else if (sbase instanceof PluginParameter)
    {
      PluginSBaseEventUtils.pluginParameterChangedOrDeleted(reader, getSelectedModel(), map, document);
    }
    else if (sbase instanceof PluginAlgebraicRule)
    {
      PluginSBaseEventUtils.pluginRuleChangedOrDeleted(reader, getSelectedModel(), map, document);
    }
    else if (sbase instanceof PluginAssignmentRule)
    {
      PluginSBaseEventUtils.pluginRuleChangedOrDeleted(reader, getSelectedModel(), map, document);
    }
    else if (sbase instanceof PluginRateRule)
    {
      PluginSBaseEventUtils.pluginRuleChangedOrDeleted(reader, getSelectedModel(), map, document);
    }
    else if (sbase instanceof PluginUnitDefinition)
    {
      PluginSBaseEventUtils.pluginUnitDefinitionChangedOrDeleted(reader, getSelectedModel(), map, document);
    }
    else if (sbase instanceof PluginInitialAssignment)
    {
      PluginSBaseEventUtils.pluginInitialAssignmentChangedOrDeleted(reader, getSelectedModel(), map, document);
    }
    else if (sbase instanceof PluginEvent)
    {
      PluginSBaseEventUtils.pluginEventChangedOrDeleted(reader, getSelectedModel(), map, document);
    }
    else if (sbase instanceof PluginConstraint)
    {
      PluginSBaseEventUtils.pluginConstraintChangedOrDeleted(reader, getSelectedModel(), map, document);
    }
    else if (sbase instanceof PluginFunctionDefinition)
    {
      PluginSBaseEventUtils.pluginFunctionDefinitionChangedOrDeleted(reader, getSelectedModel(), map, document);
    }
    else if (sbase instanceof PluginCompartmentType)
    {
      PluginSBaseEventUtils.pluginCompartmentTypeChangedOrDeleted(reader, getSelectedModel(), map, document);
    }
    else if (sbase instanceof PluginSpeciesType)
    {
      PluginSBaseEventUtils.pluginSpeciesTypeChangedOrDeleted(reader, getSelectedModel(), map, document);
    }
    document.addAllChangeListeners(treeNodeList);
  }
}