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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
  protected SBMLDocument document;
  /**
   * Map of CellDesigner PluginModels and JSBML Models
   */
  protected final Map<PluginModel, Model> modelMap = Collections.synchronizedMap(new HashMap<PluginModel, Model>());

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
   * @throws XMLStreamException
   * 
   */
  public void startPlugin() throws XMLStreamException {
    setStarted(true);
    // Synchronize changes from this plug-in to CellDesigner:
    if (!compareModels(getSelectedModel())) {
      SwingWorker<SBMLDocument, Throwable> worker = new CellDesignerModelConverter(getReader(), getSelectedModel());
      final AbstractCellDesignerPlugin plugin = this;
      worker.addPropertyChangeListener(new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
          if (evt.getPropertyName().equals("state") && evt.getNewValue().equals(SwingWorker.StateValue.DONE)) {
            try {
              CellDesignerModelConverter swing = (CellDesignerModelConverter)evt.getSource();
              //JOptionPane.showMessageDialog(null, new JScrollPane(new JTextArea("Called inside startPlugin")));
              document = swing.get();
              document.addTreeNodeChangeListener(new PluginChangeListener(plugin));
              modelMap.put(swing.getPluginModel(), document.getModel());
              run();
            } catch (Throwable e) {
              new GUIErrorConsole(e);
            }
          }
        }
      });
      worker.execute();
    } else {
      run();
    }
  }


  public SBMLDocument getSelectedSBMLDocument()
  {
    return document;
  }

  public boolean compareModels(PluginModel sbase)
  {
    for (PluginModel key : modelMap.keySet()) {
      PluginModel pModel = key;
      //      JOptionPane.showMessageDialog(null, new JScrollPane(new JTextArea("checkSize of the mapInside: "+modelMap.size()+"\t"+sbase.getId().trim()+"\t"+pModel.getId().trim()+
      //        "\t"+pModel.getId().trim().equals(sbase.getId().trim()))));
      if (pModel.getId().trim().equals(sbase.getId().trim()))
      {
        return true;
      }
    }
    return false;
  }

  public Model getAssociatedModel(PluginModel pModel)
  {
    for (Map.Entry<PluginModel, Model> entry : modelMap.entrySet()) {
      PluginModel pluginModel = entry.getKey();
      Model model = entry.getValue();
      if (pluginModel.getId().equals(pModel.getId())) {
        return model;
      }
    }
    return null;
  }

  public void printMap()
  {
    String output = "";
    for (Map.Entry<PluginModel, Model> entry : modelMap.entrySet()) {
      PluginModel pModel = entry.getKey();
      Model model = entry.getValue();
      output += pModel.getId() + "\tmodel Id: " + model.getId() + "\tSize of the map: "+modelMap.size()+"\n";
    }
    JOptionPane.showMessageDialog(null, new JScrollPane(new JTextArea(output)));
  }

  /* (non-Javadoc)
   * @see jp.sbi.celldesigner.plugin.CellDesignerPlug#modelClosed(jp.sbi.celldesigner.plugin.PluginSBase)
   */
  @Override
  public void modelClosed(PluginSBase sbase) {
    if (sbase instanceof PluginModel)
    {
      PluginModel pModel = (PluginModel)sbase;
    }
  }

  /* (non-Javadoc)
   * @see jp.sbi.celldesigner.plugin.CellDesignerPlug#modelOpened(jp.sbi.celldesigner.plugin.PluginSBase)
   */
  @Override
  public void modelOpened(PluginSBase sbase) {
    if (sbase instanceof PluginModel)
    {
      PluginModel pModel = (PluginModel) sbase;
      SwingWorker<SBMLDocument, Throwable> worker = new CellDesignerModelConverter(getReader(), pModel);
      if (!compareModels(pModel)) {
        try {
          modelMap.put(pModel, reader.convertModel(pModel).getModel());
        } catch (XMLStreamException e) {
          new GUIErrorConsole(e);
        }
      }
    }
  }

  /* (non-Javadoc)
   * @see jp.sbi.celldesigner.plugin.CellDesignerPlug#modelSelectChanged(jp.sbi.celldesigner.plugin.PluginSBase)
   */
  @Override
  public void modelSelectChanged(PluginSBase sbase) {
    if (sbase instanceof PluginModel)
    {
      PluginModel pModel = (PluginModel)sbase;
    }
  }

  /* (non-Javadoc)
   * @see jp.sbi.celldesigner.plugin.CellDesignerPlug#SBaseAdded(jp.sbi.celldesigner.plugin.PluginSBase)
   */
  @Override
  public void SBaseAdded(PluginSBase sbase) {
    // TODO Auto-generated method stub
    System.out.println("SBaseAdded " + sbase);
  }

  /* (non-Javadoc)
   * @see jp.sbi.celldesigner.plugin.CellDesignerPlug#SBaseChanged(jp.sbi.celldesigner.plugin.PluginSBase)
   */
  @Override
  public void SBaseChanged(PluginSBase sbase) {
    PluginModel currentModel = getSelectedModel();
    Model associatedJSBMLModel = getAssociatedModel(currentModel);
    Layout layout = ((LayoutModelPlugin)associatedJSBMLModel.getExtension("layout")).getLayout(0);
    List<TreeNodeChangeListener> treeNodeList = new ArrayList<TreeNodeChangeListener>();
    document = new SBMLDocument(associatedJSBMLModel.getLevel(), associatedJSBMLModel.getVersion());
    document.setModel(associatedJSBMLModel);

    treeNodeList.addAll(document.getListOfTreeNodeChangeListeners());
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

      layout.removeCompartmentGlyph("cGlyph_" + pCompartment.getId());
      layout.removeTextGlyph("tGlyph_" + pCompartment.getId());
      LayoutConverter.extractLayout((PluginCompartment)sbase, layout);
      associatedJSBMLModel.removeCompartment(newCompartment.getId());
      associatedJSBMLModel.addCompartment(newCompartment);
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
      associatedJSBMLModel.removeSpecies(pSpecies.getId());
      associatedJSBMLModel.addSpecies(newSpecies);
    }
    else if (sbase instanceof PluginSpeciesAlias)
    {
      PluginSpeciesAlias pSpeciesAlias = (PluginSpeciesAlias) sbase;
      layout.removeSpeciesGlyph("sGlyph_" + pSpeciesAlias.getSpecies().getId());
      layout.removeTextGlyph("tGlyph_" + pSpeciesAlias.getSpecies().getId());
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
      layout.removeReactionGlyph("rGlyph_" + pReaction.getId());
      layout.removeTextGlyph("tGlyph_" + pReaction.getId());
      LayoutConverter.extractLayout(pReaction, layout);
      associatedJSBMLModel.removeReaction(newReaction.getId());
      associatedJSBMLModel.addReaction(newReaction);
    }
    document.setModel(associatedJSBMLModel);
    document.addAllChangeListeners(treeNodeList);
  }

  /* (non-Javadoc)
   * @see jp.sbi.celldesigner.plugin.CellDesignerPlug#SBaseDeleted(jp.sbi.celldesigner.plugin.PluginSBase)
   */
  @Override
  public void SBaseDeleted(PluginSBase sbase) {
    // TODO Auto-generated method stub
    System.out.println("SBaseDeleted "  + sbase);
  }

}
