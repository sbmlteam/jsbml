/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2014  jointly by the following organizations:
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

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import javax.xml.stream.XMLStreamException;

import jp.sbi.celldesigner.plugin.CellDesignerPlugin;
import jp.sbi.celldesigner.plugin.PluginCompartment;
import jp.sbi.celldesigner.plugin.PluginModel;
import jp.sbi.celldesigner.plugin.PluginReaction;
import jp.sbi.celldesigner.plugin.PluginSBase;
import jp.sbi.celldesigner.plugin.PluginSpecies;
import jp.sbi.celldesigner.plugin.PluginSpeciesAlias;

import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.ext.layout.LayoutModelPlugin;
import org.sbml.jsbml.util.TreeNodeChangeListener;

/**
 * An implementation of a CellDesigner plug-in that takes care of
 * synchronization between CellDesigner and corresponding JSBML data structures.
 *
 * @author Andreas Dr&auml;ger
 * @version $Rev$
 * @since 1.0
 * @date 13.02.2014
 */
public abstract class AbstractCellDesignerPlugin extends CellDesignerPlugin implements Runnable {

  public static final String ACTION = "Abstract CellDesigner Plugin";
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
   * 
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
   * @throws XMLStreamException
   * Get the SBMLDocument from the currently selected CellDesigner PluginModel
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
      treeNodeList = new ArrayList<TreeNodeChangeListener>();
      treeNodeList.addAll(document.getListOfTreeNodeChangeListeners());
    }
    return treeNodeList;
  }

  /* (non-Javadoc)
   * @see jp.sbi.celldesigner.plugin.CellDesignerPlug#modelClosed(jp.sbi.celldesigner.plugin.PluginSBase)
   */
  @Override
  public void modelClosed(PluginSBase sbase) {
    /*There is nothing to do when a CellDesigner model is closed. See modelSelectChanged(PluginSBase)
     for events related to a model closing */
  }

  /* (non-Javadoc)
   * @see jp.sbi.celldesigner.plugin.CellDesignerPlug#modelOpened(jp.sbi.celldesigner.plugin.PluginSBase)
   */
  @Override
  public void modelOpened(PluginSBase sbase) {
    /*modelOpened(PluginSBase sbase) is ALWAYS followed by a call to modelSelectChanged(PluginSBase)
     *  See modelSelectChanged(PluginSBase) for events related to the opening of a new model */
  }

  /* (non-Javadoc)
   * @see jp.sbi.celldesigner.plugin.CellDesignerPlug#modelSelectChanged(jp.sbi.celldesigner.plugin.PluginSBase)
   */
  @Override
  public void modelSelectChanged(PluginSBase sbase) {
    if (sbase instanceof PluginModel) //it will always be a PluginModel
    {
      Map<PluginSBase, Set<SBase>> map = reader.getPluginSBase_SBaseMappings();
      map.clear(); //The current model is changed. Time to clear the current mappings
      try {
        PluginModel pModel = (PluginModel)sbase;
        Model model = reader.convertModel(pModel);
        document = new SBMLDocument(model.getLevel(), model.getVersion());
        document.setModel(model);

        Set<SBase> sBaseSet = new HashSet<SBase>();
        sBaseSet.add(model);
        PluginUtils.transferNamedSBaseProperties(pModel, model);

        //the map was just cleared, so we can just add it with no worries.
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
  @Override
  public void SBaseAdded(PluginSBase sbase) {
    Model model = document.getModel();
    Map<PluginSBase, Set<SBase>> map = reader.getPluginSBase_SBaseMappings();
    Layout layout = ((LayoutModelPlugin)model.getExtension("layout")).getLayout(0);
    List<TreeNodeChangeListener> treeNodeList = copyTreeNodeChangeListeners();
    document.removeAllTreeNodeChangeListeners(true);

    if (sbase instanceof PluginCompartment)
    {
      PluginCompartment pCompartment = (PluginCompartment)sbase;
      Compartment newCompartment = null;
      try {
        newCompartment = reader.readCompartment(pCompartment);
        model.addCompartment(newCompartment);

        Set<SBase> sBaseSet= LayoutConverter.extractLayout((PluginCompartment)sbase, layout);
        sBaseSet.add(newCompartment);
        map.put(pCompartment, sBaseSet);

        PluginUtils.transferNamedSBaseProperties(pCompartment, newCompartment);

      } catch (Throwable e) {
        new GUIErrorConsole(e);
      }
    }
    else if (sbase instanceof PluginSpecies)
    {
      PluginSpecies pSpecies = (PluginSpecies) sbase;
      Species newSpecies = null;
      try {
        newSpecies = reader.readSpecies(pSpecies);
        model.addSpecies(newSpecies);
        PluginUtils.transferNamedSBaseProperties(pSpecies, newSpecies);

      } catch (Throwable e) {
        new GUIErrorConsole(e);
      }

      Set<SBase> sBaseSet = new HashSet<SBase>();
      sBaseSet.add(newSpecies);
      map.put(pSpecies, sBaseSet);
    }
    else if (sbase instanceof PluginSpeciesAlias)
    {
      try {
        PluginSpeciesAlias pSpeciesAlias = (PluginSpeciesAlias) sbase;
        Set<SBase> sBaseSet = LayoutConverter.extractLayout(pSpeciesAlias, layout);

        map.put(pSpeciesAlias, sBaseSet);
      }
      catch (Throwable e) {
        new GUIErrorConsole(e);
      }
    }
    else if (sbase instanceof PluginReaction)
    {
      PluginReaction pReaction = (PluginReaction) sbase;
      Reaction newReaction = null;
      try {
        newReaction = reader.readReaction(pReaction);
        model.addReaction(newReaction);
        Set<SBase> sBaseSet = LayoutConverter.extractLayout(pReaction, layout);
        sBaseSet.add(newReaction);

        PluginUtils.transferNamedSBaseProperties(pReaction, newReaction);

        map.put(pReaction, sBaseSet);
      } catch (Throwable e) {
        new GUIErrorConsole(e);
      }
    }

    document.addAllChangeListeners(treeNodeList);
  }

  /* (non-Javadoc)
   * @see jp.sbi.celldesigner.plugin.CellDesignerPlug#SBaseChanged(jp.sbi.celldesigner.plugin.PluginSBase)
   */
  @Override
  public void SBaseChanged(PluginSBase sbase) {
    try {
      Map<PluginSBase, Set<SBase>> map = reader.getPluginSBase_SBaseMappings();
      Model model = document.getModel();
      Layout layout = ((LayoutModelPlugin)model.getExtension("layout")).getLayout(0);
      List<TreeNodeChangeListener> treeNodeList = copyTreeNodeChangeListeners();
      document.removeAllTreeNodeChangeListeners(true);

      if (sbase instanceof PluginCompartment)
      {
        PluginCompartment pCompartment = (PluginCompartment)sbase;
        Compartment newCompartment = null;
        newCompartment = reader.readCompartment(pCompartment);
        model.removeCompartment(newCompartment.getId());
        model.addCompartment(newCompartment);

        layout.removeCompartmentGlyph("cGlyph_" + pCompartment.getId());
        layout.removeTextGlyph("tGlyph_" + pCompartment.getId());
        Set<SBase> sBaseSet = LayoutConverter.extractLayout(pCompartment, layout);

        PluginUtils.transferNamedSBaseProperties(pCompartment, newCompartment);

        sBaseSet.add(newCompartment);

        if (map.isEmpty()) {
          map.put(pCompartment, sBaseSet);
        } else {
          for (PluginSBase pluginSBase: map.keySet())
          {
            if (pluginSBase instanceof PluginCompartment)
            {
              PluginCompartment compartment = (PluginCompartment)pluginSBase;
              if (compartment.getId().equals(pCompartment.getId())) {
                map.put(pluginSBase, sBaseSet);
              }
            }
          }
        }
      }

      else if (sbase instanceof PluginSpecies)
      {
        PluginSpecies pSpecies = (PluginSpecies) sbase;
        Species newSpecies = null;

        newSpecies = reader.readSpecies(pSpecies);
        model.removeSpecies(pSpecies.getId());
        model.addSpecies(newSpecies);

        PluginUtils.transferNamedSBaseProperties(pSpecies, newSpecies);

        Set<SBase> listOfSBases = new HashSet<SBase>();
        listOfSBases.add(newSpecies);

        if (map.isEmpty()) {
          map.put(pSpecies, listOfSBases);
        } else {
          String speciesID = "";
          for (PluginSBase pluginSBase: map.keySet())
          {
            if (pluginSBase instanceof PluginSpecies)
            {
              PluginSpecies species = (PluginSpecies)pluginSBase;
              if (species.getId().equals(pSpecies.getId())) {
                map.put(pluginSBase, listOfSBases);
                speciesID = species.getId();
              }
            }
          }//only adds the mapping if the new sbase is an un-represented sbase
          if (!pSpecies.getId().equals(speciesID)) {
            map.put(pSpecies, listOfSBases);
          }
        }
      }

      else if (sbase instanceof PluginSpeciesAlias)
      {
        PluginSpeciesAlias pSpeciesAlias = (PluginSpeciesAlias) sbase;
        layout.removeSpeciesGlyph("sGlyph_" + pSpeciesAlias.getAliasID());
        layout.removeTextGlyph("tGlyph_" + pSpeciesAlias.getAliasID());
        Set<SBase> listOfSBases = LayoutConverter.extractLayout(pSpeciesAlias, layout);
        if (map.isEmpty()) {
          map.put(pSpeciesAlias, listOfSBases);
        } else {
          String speciesAliasID = "";
          for (PluginSBase pluginSBase: map.keySet())
          {
            if (pluginSBase instanceof PluginSpeciesAlias)
            {
              PluginSpeciesAlias speciesAlias = (PluginSpeciesAlias)pluginSBase;
              if (speciesAlias.getAliasID().equals(pSpeciesAlias.getAliasID())) {
                map.put(speciesAlias, listOfSBases);
                speciesAliasID = speciesAlias.getAliasID();
              }
            }
          } //only adds the mapping if the new sbase is an un-represented sbase
          if (!pSpeciesAlias.getAliasID().equals(speciesAliasID)) {
            map.put(pSpeciesAlias, listOfSBases);
          }
        }
      }

      else if (sbase instanceof PluginReaction)
      {
        PluginReaction pReaction = (PluginReaction) sbase;
        Reaction newReaction = null;
        model.removeReaction(pReaction.getId());
        newReaction = reader.readReaction(pReaction);
        model.addReaction(newReaction);

        PluginUtils.transferNamedSBaseProperties(pReaction, newReaction);

        layout.removeReactionGlyph("rGlyph_" + pReaction.getId());
        layout.removeTextGlyph("tGlyph_" + pReaction.getId());
        Set<SBase> listOfSBases = LayoutConverter.extractLayout(pReaction, layout);

        listOfSBases.add(newReaction);

        if (map.isEmpty()) {
          map.put(pReaction, listOfSBases);
        } else {
          String reactionID = "";
          for (PluginSBase pluginSBase: map.keySet())
          {
            if (pluginSBase instanceof PluginReaction)
            {
              PluginReaction reaction = (PluginReaction)pluginSBase;
              if (reaction.getId().equals(pReaction.getId())) {
                map.put(reaction, listOfSBases);
                reactionID = reaction.getId();
              }
            }
          } //only adds the mapping if the new sbase is an un-represented sbase
          if (!pReaction.getId().equals(reactionID)) {
            map.put(pReaction, listOfSBases);
          }
        }

      }
      document.addAllChangeListeners(treeNodeList);
      JOptionPane.showMessageDialog(null, new JScrollPane(new JTextArea(reader.printMap())));
    }
    catch (Throwable e) {
      new GUIErrorConsole(e);
    }
  }


  /* (non-Javadoc)
   * @see jp\nbi.celldesigner.plugin.CellDesignerPlug#SBaseDeleted(jp.sbi.celldesigner.plugin.PluginSBase)
   */
  @Override
  public void SBaseDeleted(PluginSBase sbase) {
    Model model = document.getModel();
    Map<PluginSBase, Set<SBase>> sBaseMap = reader.getPluginSBase_SBaseMappings();
    Layout layout = ((LayoutModelPlugin)model.getExtension("layout")).getLayout(0);

    List<TreeNodeChangeListener> treeNodeList = copyTreeNodeChangeListeners();
    document.removeAllTreeNodeChangeListeners(true);

    if (sbase instanceof PluginCompartment)
    {
      PluginCompartment pCompartment = (PluginCompartment)sbase;

      layout.removeCompartmentGlyph("cGlyph_" + pCompartment.getId());
      layout.removeTextGlyph("tGlyph_" + pCompartment.getId());

      model.removeCompartment(pCompartment.getId());

      {
        for (PluginSBase pluginSBase: sBaseMap.keySet())
        {
          PluginCompartment compartment = (PluginCompartment)pluginSBase;
          if (compartment.getId().equals(pCompartment.getId())) {
            sBaseMap.remove(pluginSBase);
          }
        }
      }
    }
    else if (sbase instanceof PluginSpecies)
    {
      PluginSpecies pSpecies = (PluginSpecies) sbase;
      model.removeSpecies(pSpecies.getId());

      if (!sBaseMap.isEmpty())
      {
        for (PluginSBase pluginSBase: sBaseMap.keySet())
        {
          PluginSpecies species = (PluginSpecies)pluginSBase;
          if (species.getId().equals(pSpecies.getId())) {
            sBaseMap.remove(pluginSBase);
          }
        }
      }
    }
    else if (sbase instanceof PluginSpeciesAlias)
    {
      PluginSpeciesAlias pSpeciesAlias = (PluginSpeciesAlias) sbase;

      layout.removeSpeciesGlyph("sGlyph_" + pSpeciesAlias.getAliasID());
      layout.removeTextGlyph("tGlyph_" + pSpeciesAlias.getAliasID());

      if (!sBaseMap.isEmpty())
      {
        for (PluginSBase pluginSBase: sBaseMap.keySet())
        {
          PluginSpeciesAlias speciesAlias = (PluginSpeciesAlias)pluginSBase;
          if (speciesAlias.getAliasID().equals(pSpeciesAlias.getAliasID())) {
            sBaseMap.remove(pluginSBase);
          }
        }
      }
    }
    else if (sbase instanceof PluginReaction)
    {
      PluginReaction pReaction = (PluginReaction) sbase;

      layout.removeReactionGlyph("rGlyph_" + pReaction.getId());
      layout.removeTextGlyph("tGlyph_" + pReaction.getId());

      model.removeReaction(pReaction.getId());

      for (PluginSBase pluginSBase: sBaseMap.keySet())
      {
        PluginReaction reaction = (PluginReaction)pluginSBase;
        if (reaction.getId().equals(pReaction.getId()))
        {
          sBaseMap.remove(pluginSBase);
        }
      }
    }
    document.addAllChangeListeners(treeNodeList);
  }
}