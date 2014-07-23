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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
   * @throws XMLStreamException
   * 
   */
  public void startPlugin() throws XMLStreamException {
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
      Model model;
      try {
        model = reader.convertModel((PluginModel)sbase);
        document = new SBMLDocument(model.getLevel(), model.getVersion());
        document.setModel(model);
      } catch (XMLStreamException e) {
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
    Layout layout = ((LayoutModelPlugin)model.getExtension("layout")).getLayout(0);
    List<TreeNodeChangeListener> treeNodeList = copyTreeNodeChangeListeners();
    document.removeAllTreeNodeChangeListeners();

    if (sbase instanceof PluginCompartment)
    {
      PluginCompartment pCompartment = (PluginCompartment)sbase;
      Compartment newCompartment = null;
      try {
        newCompartment = reader.readCompartment(pCompartment);
      } catch (XMLStreamException e) {
        new GUIErrorConsole(e);
      }
      model.addCompartment(newCompartment);
      LayoutConverter.extractLayout((PluginCompartment)sbase, layout);
    }
    else if (sbase instanceof PluginSpecies)
    {
      PluginSpecies pSpecies = (PluginSpecies) sbase;
      Species newSpecies = null;
      try {
        newSpecies = reader.readSpecies(pSpecies);
      } catch (XMLStreamException e) {
        new GUIErrorConsole(e);
      }
      model.addSpecies(newSpecies);
    }
    else if (sbase instanceof PluginSpeciesAlias)
    {
      PluginSpeciesAlias pSpeciesAlias = (PluginSpeciesAlias) sbase;
      LayoutConverter.extractLayout(pSpeciesAlias, layout);
    }
    else if (sbase instanceof PluginReaction)
    {
      PluginReaction pReaction = (PluginReaction) sbase;
      Reaction newReaction = null;
      try {
        newReaction = reader.readReaction(pReaction);
      } catch (XMLStreamException e) {
        new GUIErrorConsole(e);
      }
      model.addReaction(newReaction);
      LayoutConverter.extractLayout(pReaction, layout);
    }
    document.addAllChangeListeners(treeNodeList);
  }

  /* (non-Javadoc)
   * @see jp.sbi.celldesigner.plugin.CellDesignerPlug#SBaseChanged(jp.sbi.celldesigner.plugin.PluginSBase)
   */
  @Override
  public void SBaseChanged(PluginSBase sbase) {
    Model model = document.getModel();
    Map<PluginSBase, SBase> map = reader.getSBaseMappings();
    Layout layout = ((LayoutModelPlugin)model.getExtension("layout")).getLayout(0);
    List<TreeNodeChangeListener> treeNodeList = copyTreeNodeChangeListeners();
    document.removeAllTreeNodeChangeListeners(true);

    if (sbase instanceof PluginCompartment)
    {
      PluginCompartment pCompartment = (PluginCompartment)sbase;
      Compartment newCompartment = null;
      try {
        newCompartment = reader.readCompartment(pCompartment);
        model.removeCompartment(newCompartment.getId());
        model.addCompartment(newCompartment);
      } catch (Throwable e) {
        new GUIErrorConsole(e);
      }

      layout.removeCompartmentGlyph("cGlyph_" + pCompartment.getId());
      layout.removeTextGlyph("tGlyph_" + pCompartment.getId());
      LayoutConverter.extractLayout(pCompartment, layout);
      map.put(pCompartment, newCompartment);
    }
    else if (sbase instanceof PluginSpecies)
    {
      PluginSpecies pSpecies = (PluginSpecies) sbase;
      Species newSpecies = null;
      try {
        newSpecies = reader.readSpecies(pSpecies);
        model.removeSpecies(pSpecies.getId());
        model.addSpecies(newSpecies);
      } catch (Throwable e) {
        new GUIErrorConsole(e);
      }
    }
    else if (sbase instanceof PluginSpeciesAlias)
    {
      PluginSpeciesAlias pSpeciesAlias = (PluginSpeciesAlias) sbase;
      layout.removeSpeciesGlyph("sGlyph_" + pSpeciesAlias.getAliasID());
      layout.removeTextGlyph("tGlyph_" + pSpeciesAlias.getAliasID());
      LayoutConverter.extractLayout(pSpeciesAlias, layout);
    }
    else if (sbase instanceof PluginReaction)
    {
      PluginReaction pReaction = (PluginReaction) sbase;
      Reaction newReaction = null;
      try {
        model.removeReaction(pReaction.getId());
        newReaction = reader.readReaction(pReaction);
        model.addReaction(newReaction);
        layout.removeReactionGlyph("rGlyph_" + pReaction.getId());
        layout.removeTextGlyph("tGlyph_" + pReaction.getId());
        LayoutConverter.extractLayout(pReaction, layout);
      } catch (Throwable e) {
        new GUIErrorConsole(e);
      }
    }
    document.addAllChangeListeners(treeNodeList);
  }

  /* (non-Javadoc)
   * @see jp.sbi.celldesigner.plugin.CellDesignerPlug#SBaseDeleted(jp.sbi.celldesigner.plugin.PluginSBase)
   */
  @Override
  public void SBaseDeleted(PluginSBase sbase) {
    Model model = document.getModel();
    Layout layout = ((LayoutModelPlugin)model.getExtension("layout")).getLayout(0);
    List<TreeNodeChangeListener> treeNodeList = copyTreeNodeChangeListeners();
    document.removeAllTreeNodeChangeListeners();
    if (sbase instanceof PluginCompartment)
    {
      PluginCompartment pCompartment = (PluginCompartment)sbase;

      layout.removeCompartmentGlyph("cGlyph_" + pCompartment.getId());
      layout.removeTextGlyph("tGlyph_" + pCompartment.getId());
      model.removeCompartment(pCompartment.getId());
    }
    else if (sbase instanceof PluginSpecies)
    {
      PluginSpecies pSpecies = (PluginSpecies) sbase;
      model.removeSpecies(pSpecies.getId());
    }
    else if (sbase instanceof PluginSpeciesAlias)
    {
      PluginSpeciesAlias pSpeciesAlias = (PluginSpeciesAlias) sbase;
      layout.removeSpeciesGlyph("sGlyph_" + pSpeciesAlias.getSpecies().getId());
      layout.removeTextGlyph("tGlyph_" + pSpeciesAlias.getSpecies().getId());
    }
    else if (sbase instanceof PluginReaction)
    {
      PluginReaction pReaction = (PluginReaction) sbase;
      layout.removeReactionGlyph("rGlyph_" + pReaction.getId());
      layout.removeTextGlyph("tGlyph_" + pReaction.getId());
      model.removeReaction(pReaction.getId());
    }
    document.addAllChangeListeners(treeNodeList);
  }

}
