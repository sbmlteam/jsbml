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
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.celldesigner;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.tree.DefaultTreeModel;

import jp.sbi.celldesigner.plugin.PluginMenu;
import jp.sbi.celldesigner.plugin.PluginMenuItem;
import jp.sbi.celldesigner.plugin.PluginSBase;

import org.sbml.jsbml.gui.JSBMLvisualizer;


/**
 * A simple plug-in for CellDesigner that displays the SBML data structure as a
 * {@link DefaultTreeModel}. When the underlying SBMLDocument is changed by
 * CellDesigner,
 * the tree will refresh itself and all nodes will become unexpanded.
 *
 * @author Andreas Dr&auml;ger
 * @version $Rev$
 * @since 1.0
 * @date 16.04.2014
 */
public class SimpleCellDesignerPlugin extends AbstractCellDesignerPlugin {

  public static final String ACTION = "Display full model tree";
  public static final String APPLICATION_NAME = "Simple Plugin";
  protected DefaultTreeModel modelTree = null;

  /**
   * Creates a new CellDesigner plug-in with an entry in the menu bar.
   */
  public SimpleCellDesignerPlugin() {
    super();
    addPluginMenu();
  }

  /* (non-Javadoc)
   * @see jp.sbi.celldesigner.plugin.CellDesignerPlug#addPluginMenu()
   */
  @Override
  public void addPluginMenu() {
    // Initializing CellDesigner's menu entries
    PluginMenu menu = new PluginMenu(APPLICATION_NAME);
    PluginMenuItem menuItem = new PluginMenuItem(
      ACTION, new SimpleCellDesignerPluginAction(this));
    menuItem.setToolTipText("Displays the data structure of the model.");
    menu.add(menuItem);
    addCellDesignerPluginMenu(menu);
  }



  /* (non-Javadoc)
   * @see org.sbml.jsbml.celldesigner.AbstractCellDesignerPlugin#modelSelectChanged(jp.sbi.celldesigner.plugin.PluginSBase)
   */
  /**
   * After the model is changed, refreshes the Tree by resetting the Root node.
   */
  @Override
  public void modelSelectChanged(PluginSBase sbase) {
    super.modelSelectChanged(sbase);
    modelTree.setRoot(getSBMLDocument());
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.celldesigner.AbstractCellDesignerPlugin#SBaseAdded(jp.sbi.celldesigner.plugin.PluginSBase)
   */
  /**
   * After a PluginSBase addition, refreshes the Tree by resetting the Root node.
   */
  @Override
  public void SBaseAdded(PluginSBase sbase) {
    super.SBaseAdded(sbase);
    modelTree.setRoot(getSBMLDocument());
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.celldesigner.AbstractCellDesignerPlugin#SBaseChanged(jp.sbi.celldesigner.plugin.PluginSBase)
   */
  /**
   * After a PluginSBase modification, refreshes the Tree by resetting the Root node.
   */
  @Override
  public void SBaseChanged(PluginSBase sbase) {
    super.SBaseChanged(sbase);
    modelTree.setRoot(getSBMLDocument());
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.celldesigner.AbstractCellDesignerPlugin#SBaseDeleted(jp.sbi.celldesigner.plugin.PluginSBase)
   */
  /**
   * After a PluginSBase deletion, refreshes the Tree by resetting the Root node.
   */
  @Override
  public void SBaseDeleted(PluginSBase sbase) {
    super.SBaseDeleted(sbase);
    modelTree.setRoot(getSBMLDocument());
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.celldesigner.AbstractCellDesignerPlugin#modelClosed(jp.sbi.celldesigner.plugin.PluginSBase)
   */
  /**
   * If the CellDesigner model is closed, we nullify the Tree.
   */
  @Override
  public void modelClosed(PluginSBase sbase) {
    super.modelClosed(sbase);
    modelTree.setRoot(null);
  }

  /**
   * Initializes plugin and sets WindowClosed() events.
   */
  @Override
  public void run() {
    modelTree = new DefaultTreeModel(getSBMLDocument());
    JSBMLvisualizer visualizer = new JSBMLvisualizer(getSBMLDocument());
    visualizer.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosed(WindowEvent e) {
        setStarted(false);
        getReader().clearMap();
      }
    });
  }
}