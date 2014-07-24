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
 * A basic implementation of a CellDesigner plug-in that takes care of
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
   * A singular SBMLDocument associated with this plugin
   */
  protected SBMLDocument document = null;
  /**
   * 
   */
  final AbstractCellDesignerPlugin plugin = this;

  /**
   *
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
   * @throws Throwable
   * 
   */
  public void startPlugin() throws XMLStreamException{
    setStarted(true);
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
   * 
   * @return the current list of TreeNodeChangeListeners for this SBMLDocument
   */
  private List<TreeNodeChangeListener> copyTreeNodeChangeListeners()
  {
    List<TreeNodeChangeListener> treeNodeList = null;
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
    //seems like there is nothing to do here
  }

  /* (non-Javadoc)
   * @see jp.sbi.celldesigner.plugin.CellDesignerPlug#modelOpened(jp.sbi.celldesigner.plugin.PluginSBase)
   */
  @Override
  public void modelOpened(PluginSBase sbase) {
    //seems like there is nothing to do here
  }

  /* (non-Javadoc)
   * @see jp.sbi.celldesigner.plugin.CellDesignerPlug#modelSelectChanged(jp.sbi.celldesigner.plugin.PluginSBase)
   */
  @Override
  public void modelSelectChanged(PluginSBase sbase) {
    if (sbase instanceof PluginModel)
    {
      Map<PluginSBase, Set<SBase>> map = reader.getPluginSBase_SBaseMappings();
      Model model;
      try {
        PluginModel pModel = (PluginModel)sbase;
        model = reader.convertModel(pModel);
        document = new SBMLDocument(model.getLevel(), model.getVersion());
        document.setModel(model);

        Set<SBase> sBaseSet = new HashSet<SBase>();
        sBaseSet.add(model);
        if (map.isEmpty()) {
          map.put(pModel, sBaseSet);
        } else {
          for (PluginSBase pluginSBase: map.keySet())
          {
            if (pluginSBase instanceof PluginModel)
            {
              PluginModel pluginModel = (PluginModel)pluginSBase;
              if (pluginModel.getId().equals(pModel.getId())) {
                map.put(pluginSBase, sBaseSet);
                PluginUtils.transferNamedSBaseProperties(pModel, model);
              }
            }
          }
        }
      } catch (Throwable e) {
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
        LayoutConverter.extractLayout((PluginCompartment)sbase, layout);
        PluginUtils.transferNamedSBaseProperties(pCompartment, newCompartment);
        PluginUtils.transferNamedSBaseProperties(pCompartment,
          layout.getCompartmentGlyph("cGlyph_" + pCompartment.getId()));
        PluginUtils.transferNamedSBaseProperties(pCompartment,
          layout.getTextGlyph("tGlyph_" + pCompartment.getId()));
      } catch (Throwable e) {
        new GUIErrorConsole(e);
      }

      Set<SBase> sBaseSet = new HashSet<SBase>();
      sBaseSet.add(newCompartment);
      map.put(pCompartment, sBaseSet);
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
      PluginSpeciesAlias pSpeciesAlias = (PluginSpeciesAlias) sbase;
      LayoutConverter.extractLayout(pSpeciesAlias, layout);
      PluginUtils.transferNamedSBaseProperties(pSpeciesAlias,
        layout.getSpeciesGlyph("sGlyph_" + pSpeciesAlias.getSpecies().getId()));
      Set<SBase> sBaseSet = new HashSet<SBase>();
      sBaseSet.add(layout.getSpeciesGlyph("sGlyph_" + pSpeciesAlias.getSpecies().getId()));
      map.put(pSpeciesAlias, sBaseSet);
    }
    else if (sbase instanceof PluginReaction)
    {
      PluginReaction pReaction = (PluginReaction) sbase;
      Reaction newReaction = null;
      try {
        newReaction = reader.readReaction(pReaction);
        model.addReaction(newReaction);
        LayoutConverter.extractLayout(pReaction, layout);
        PluginUtils.transferNamedSBaseProperties(pReaction, newReaction);
      } catch (Throwable e) {
        new GUIErrorConsole(e);
      }
      Set<SBase> sBaseSet = new HashSet<SBase>();
      sBaseSet.add(newReaction);
      map.put(pReaction, sBaseSet);
    }
    document.addAllChangeListeners(treeNodeList);
  }

  /* (non-Javadoc)
   * @see jp.sbi.celldesigner.plugin.CellDesignerPlug#SBaseChanged(jp.sbi.celldesigner.plugin.PluginSBase)
   */
  @Override
  public void SBaseChanged(PluginSBase sbase) {
    Model model = document.getModel();
    Map<PluginSBase, Set<SBase>> map = reader.getPluginSBase_SBaseMappings();
    Layout layout = ((LayoutModelPlugin)model.getExtension("layout")).getLayout(0);
    List<TreeNodeChangeListener> treeNodeList = copyTreeNodeChangeListeners();
    document.removeAllTreeNodeChangeListeners(true);

    if (sbase instanceof PluginCompartment)
    {
      PluginCompartment pCompartment = (PluginCompartment)sbase;
      Set<SBase> sBaseSet = new HashSet<SBase>();
      Compartment newCompartment = null;

      try {
        newCompartment = reader.readCompartment(pCompartment);
        model.removeCompartment(newCompartment.getId());
        model.addCompartment(newCompartment);
        sBaseSet.add(newCompartment);

        layout.removeCompartmentGlyph("cGlyph_" + pCompartment.getId());
        layout.removeTextGlyph("tGlyph_" + pCompartment.getId());
        LayoutConverter.extractLayout(pCompartment, layout);
        PluginUtils.transferNamedSBaseProperties(pCompartment, newCompartment);
        PluginUtils.transferNamedSBaseProperties(pCompartment,
          layout.getCompartmentGlyph("cGlyph_" + pCompartment.getId()));

        sBaseSet.add(layout.getCompartmentGlyph("cGlyph_" + pCompartment.getId()));
        sBaseSet.add(layout.getTextGlyph("tGlyph_" + pCompartment.getId()));

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
      } catch (Throwable e) {
        new GUIErrorConsole(e);
      }
    }

    else if (sbase instanceof PluginSpecies)
    {
      PluginSpecies pSpecies = (PluginSpecies) sbase;
      Set<SBase> listOfSBases = new HashSet<SBase>();
      Species newSpecies = null;

      try {
        newSpecies = reader.readSpecies(pSpecies);
        model.removeSpecies(pSpecies.getId());
        model.addSpecies(newSpecies);
        PluginUtils.transferNamedSBaseProperties(pSpecies, newSpecies);
        listOfSBases.add(newSpecies);

        if (map.isEmpty()) {
          map.put(pSpecies, listOfSBases);
        } else {
          for (PluginSBase pluginSBase: map.keySet())
          {
            if (pluginSBase instanceof PluginSpecies)
            {
              PluginSpecies species = (PluginSpecies)pluginSBase;
              if (species.getId().equals(pSpecies.getId())) {
                map.put(pluginSBase, listOfSBases);
              }
            }
          }
        }
      } catch (Throwable e) {
        new GUIErrorConsole(e);
      }
    }
    else if (sbase instanceof PluginSpeciesAlias)
    {
      Set<SBase> listOfSBases = new HashSet<SBase>();
      try {
        PluginSpeciesAlias pSpeciesAlias = (PluginSpeciesAlias) sbase;
        layout.removeSpeciesGlyph("sGlyph_" + pSpeciesAlias.getSpecies().getId());
        layout.removeTextGlyph("tGlyph_" + pSpeciesAlias.getSpecies().getId());
        LayoutConverter.extractLayout(pSpeciesAlias, layout);
        PluginUtils.transferNamedSBaseProperties(pSpeciesAlias,
          layout.getSpeciesGlyph("sGlyph_" + pSpeciesAlias.getSpecies().getId()));

        listOfSBases.add(layout.getSpeciesGlyph("sGlyph_" + pSpeciesAlias.getSpecies().getId()));
        listOfSBases.add(layout.getTextGlyph("tGlyph_" + pSpeciesAlias.getSpecies().getId()));
        if (map.isEmpty()) {
          map.put(pSpeciesAlias, listOfSBases);
          JOptionPane.showMessageDialog(null, new JScrollPane(new JTextArea("emptymap: "+pSpeciesAlias)));
        } else {
          String checker = "";
          for (PluginSBase pluginSBase: map.keySet())
          {
            if (pluginSBase instanceof PluginSpeciesAlias)
            {
              PluginSpeciesAlias speciesAlias = (PluginSpeciesAlias)pluginSBase;
              if (speciesAlias.getSpecies().getId().equals(pSpeciesAlias.getSpecies().getId())) {
                JOptionPane.showMessageDialog(null, new JScrollPane(new JTextArea("addition: "
                    +"\t"+speciesAlias+"\t"+pSpeciesAlias)));
                map.put(speciesAlias, listOfSBases);
                checker = speciesAlias.getSpecies().getId();
              }
            }
          } //cannot determine if the new sbase is an un-represented sbase or a represented sbase under a new name
          if (!pSpeciesAlias.getSpecies().getId().equals(checker)) {
            map.put(pSpeciesAlias, listOfSBases);
          }
        }
      } catch (Throwable e) {
        new GUIErrorConsole(e);
      }
    }
    else if (sbase instanceof PluginReaction)
    {
      Set<SBase> listOfSBases = new HashSet<SBase>();
      PluginReaction pReaction = (PluginReaction) sbase;
      Reaction newReaction = null;
      try {
        model.removeReaction(pReaction.getId());
        newReaction = reader.readReaction(pReaction);
        model.addReaction(newReaction);
        listOfSBases.add(newReaction);

        PluginUtils.transferNamedSBaseProperties(pReaction, newReaction);
        layout.removeReactionGlyph("rGlyph_" + pReaction.getId());
        layout.removeTextGlyph("tGlyph_" + pReaction.getId());
        LayoutConverter.extractLayout(pReaction, layout);
        // PluginUtils.transferNamedSBaseProperties(pReaction, layout.getReactionGlyph("rGlyph_" + pReaction.getId()));
        //PluginUtils.transferNamedSBaseProperties(pReaction, layout.getTextGlyph("tGlyph_" + pReaction.getId()));

        listOfSBases.add(layout.getReactionGlyph("rGlyph_" + pReaction.getId()));
        listOfSBases.add(layout.getReactionGlyph("tGlyph_" + pReaction.getId()));

        if (map.isEmpty()) {
          map.put(pReaction, listOfSBases);
        } else {
          for (PluginSBase pluginSBase: map.keySet())
          {
            if (pluginSBase instanceof PluginReaction)
            {
              PluginReaction reaction = (PluginReaction)pluginSBase;
              if (reaction.getId().equals(reaction.getId())) {
                map.put(pluginSBase, listOfSBases);
              }
            }
          }
          map.put(pReaction, listOfSBases);
        }

      } catch (Throwable e) {
        new GUIErrorConsole(e);
      }
    }

    document.addAllChangeListeners(treeNodeList);
    String ss = "";
    for (PluginSBase s: map.keySet())
    {
      ss+=s.toString()+"\n";
    }
    JOptionPane.showMessageDialog(null, new JScrollPane(new JTextArea("completion:\n"+ss)));
  }

  /* (non-Javadoc)
   * @see jp\nbi.celldesigner.plugin.CellDesignerPlug#SBaseDeleted(jp.sbi.celldesigner.plugin.PluginSBase)
   */
  @Override
  public void SBaseDeleted(PluginSBase sbase) {
    Model model = document.getModel();
    Map<PluginSBase, Set<SBase>> map = reader.getPluginSBase_SBaseMappings();
    Layout layout = ((LayoutModelPlugin)model.getExtension("layout")).getLayout(0);
    List<TreeNodeChangeListener> treeNodeList = copyTreeNodeChangeListeners();
    document.removeAllTreeNodeChangeListeners(true);
    if (sbase instanceof PluginCompartment)
    {
      PluginCompartment pCompartment = (PluginCompartment)sbase;

      layout.removeCompartmentGlyph("cGlyph_" + pCompartment.getId());
      layout.removeTextGlyph("tGlyph_" + pCompartment.getId());
      model.removeCompartment(pCompartment.getId());
      if (!map.isEmpty())
      {
        for (PluginSBase pluginSBase: map.keySet())
        {
          PluginCompartment compartment = (PluginCompartment)pluginSBase;
          if (compartment.getId().equals(pCompartment.getId())) {
            map.remove(pluginSBase);
          }
        }
      }
    }
    else if (sbase instanceof PluginSpecies)
    {
      PluginSpecies pSpecies = (PluginSpecies) sbase;
      model.removeSpecies(pSpecies.getId());
      if (!map.isEmpty())
      {
        for (PluginSBase pluginSBase: map.keySet())
        {
          PluginSpecies species = (PluginSpecies)pluginSBase;
          if (species.getId().equals(pSpecies.getId())) {
            map.remove(pluginSBase);
          }
        }
      }
    }
    else if (sbase instanceof PluginSpeciesAlias)
    {
      PluginSpeciesAlias pSpeciesAlias = (PluginSpeciesAlias) sbase;
      layout.removeSpeciesGlyph("sGlyph_" + pSpeciesAlias.getSpecies().getId());
      layout.removeTextGlyph("tGlyph_" + pSpeciesAlias.getSpecies().getId());
      if (!map.isEmpty())
      {
        for (PluginSBase pluginSBase: map.keySet())
        {
          PluginSpeciesAlias speciesAlias = (PluginSpeciesAlias)pluginSBase;
          if (speciesAlias.getSpecies().getId().equals(pSpeciesAlias.getSpecies().getId())) {
            map.remove(pluginSBase);
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
      if (!map.isEmpty())
      {
        for (PluginSBase pluginSBase: map.keySet())
        {
          PluginReaction reaction = (PluginReaction)pluginSBase;
          if (reaction.getId().equals(reaction.getId()))
          {
            map.remove(pluginSBase);
          }
        }
      }
    }
    document.addAllChangeListeners(treeNodeList);
  }
}
