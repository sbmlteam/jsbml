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
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.celldesigner;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;

import javax.swing.SwingWorker;
import javax.xml.stream.XMLStreamException;

import jp.sbi.celldesigner.plugin.PluginMenu;
import jp.sbi.celldesigner.plugin.PluginMenuItem;

import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.gui.SBMLStructureVisualizer;


/**
 * A simple plug-in for CellDesigner that displays the SBML data structure as a
 * tree.
 *
 * @author Andreas Dr&auml;ger
 * @version $Rev$
 * @since 1.0
 * @date 16.04.2014
 */
public class SBMLTreeVisualizationPlugin extends AbstractCellDesignerPlugin {

  public static final String ACTION = "Display JSBML JTree";
  public static final String APPLICATION_NAME = "SBML Structure Visualization";

  /**
   * Creates a new CellDesigner plug-in with an entry in the menu bar.
   */
  public SBMLTreeVisualizationPlugin() {
    super();
    addPluginMenu();
  }

  /* (non-Javadoc)
   * @see jp.sbi.celldesigner.plugin.CellDesignerPlug#addPluginMenu()
   */
  @Override
  public void addPluginMenu() {
    setStarted(true);
    // Initializing CellDesigner's menu entries
    PluginMenu menu = new PluginMenu(APPLICATION_NAME);
    PluginMenuItem menuItem = new PluginMenuItem(
      ACTION, new SBMLTreeVisualizationPluginAction(this));
    menuItem.setToolTipText("Displays the data structure of the model.");
    menu.add(menuItem);
    addCellDesignerPluginMenu(menu);
  }

  /**
   * Performs the action for which this plug-in is designed.
   *
   * @throws XMLStreamException If the given SBML model contains errors.
   */
  public void startPlugin() throws XMLStreamException {
    // Synchronize changes from this plug-in to CellDesigner:
    final SwingWorker<SBMLDocument, Throwable> worker = new SwingWork(getReader(),getSelectedModel());
    worker.addPropertyChangeListener(this);
    worker.execute();
  }

  /* (non-Javadoc)
   * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
   */
  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    if (evt.getPropertyName().equals("state") && evt.getNewValue().equals(SwingWorker.StateValue.DONE)) {
      try {
        SBMLDocument doc=((SwingWork)evt.getSource()).get();
        doc.addTreeNodeChangeListener(new PluginChangeListener(this));
        SBMLStructureVisualizer visualizer = new SBMLStructureVisualizer(doc);
        visualizer.addWindowListener(new WindowListener() {

          @Override
          public void windowOpened(WindowEvent e) {
            // TODO Auto-generated method stub
          }


          @Override
          public void windowIconified(WindowEvent e) {
            // TODO Auto-generated method stub
          }


          @Override
          public void windowDeiconified(WindowEvent e) {
            // TODO Auto-generated method stub
          }


          @Override
          public void windowDeactivated(WindowEvent e) {
            // TODO Auto-generated method stub
          }


          @Override
          public void windowClosing(WindowEvent e) {
            // TODO Auto-generated method stub
          }


          @Override
          public void windowClosed(WindowEvent e) {
            setStarted(false);
          }


          @Override
          public void windowActivated(WindowEvent e) {
            // TODO Auto-generated method stub
          }
        });
      } catch (Throwable e) {
        new GUIErrorConsole(e);
      }
    }
  }
}
