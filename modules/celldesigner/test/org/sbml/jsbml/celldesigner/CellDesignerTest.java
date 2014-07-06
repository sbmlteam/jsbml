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

import javax.swing.SwingWorker;
import javax.xml.stream.XMLStreamException;

import jp.sbi.celldesigner.plugin.PluginMenu;
import jp.sbi.celldesigner.plugin.PluginMenuItem;
import jp.sbi.celldesigner.plugin.PluginSBase;

import org.sbml.jsbml.SBMLDocument;


/**
 * @author Ibrahim Vazirabad
 * @version $Rev$
 * @since 1.0
 * @date Jun 30, 2014
 */
public class CellDesignerTest extends AbstractCellDesignerPlugin  implements PropertyChangeListener {

  public static final String ACTION = "Test CD Listeners";
  public static final String APPLICATION_NAME = "Test the Listeners";

  private final CDPropertyChangeVis propertyChangeVis;

  /**
   * Creates a new CellDesigner plug-in with an entry in the menu bar.
   */
  public CellDesignerTest() {
    super();
    addPluginMenu();
    propertyChangeVis = new CDPropertyChangeVis();
    setStarted(true);
  }

  /* (non-Javadoc)
   * @see jp.sbi.celldesigner.plugin.CellDesignerPlug#addPluginMenu()
   */
  @Override
  public void addPluginMenu() {
    // Initializing CellDesigner's menu entries
    PluginMenu menu = new PluginMenu(APPLICATION_NAME);
    PluginMenuItem menuItem = new PluginMenuItem(
      ACTION, new CellDesignerTestAction(this));
    menuItem.setToolTipText("Tests the CD Listeners");
    menu.add(menuItem);
    addCellDesignerPluginMenu(menu);
  }

  /**
   * Performs the action for which this plug-in is designed.
   *
   * @throws XMLStreamException If the given SBML model contains errors.
   */
  public void startPlugin() throws XMLStreamException {
    setStarted(true);
    // Synchronize changes from this plug-in to CellDesigner:
    SwingWorker<SBMLDocument, Throwable> worker = new SwingWork(getReader(),getSelectedModel());
    worker.addPropertyChangeListener(this);
    worker.execute();
  }

  @Override
  public void SBaseAdded(PluginSBase sbase) {

    propertyChangeVis.addSBase(sbase.toString());
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.celldesigner.AbstractCellDesignerPlugin#modelSelectChanged(jp.sbi.celldesigner.plugin.PluginSBase)
   */
  @Override
  public void modelSelectChanged(PluginSBase sbase) {
    // TODO Auto-generated method stub
    super.modelSelectChanged(sbase);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.celldesigner.AbstractCellDesignerPlugin#SBaseChanged(jp.sbi.celldesigner.plugin.PluginSBase)
   */
  @Override
  public void SBaseChanged(PluginSBase sbase) {
    propertyChangeVis.changeSBase(sbase.toString());
  }

  @Override
  public void modelClosed(PluginSBase sbase) {
    propertyChangeVis.modelClosed(sbase.toString());
  }

  @Override
  public void modelOpened(PluginSBase sbase) {
    propertyChangeVis.modelOpened(sbase.toString());
  }

  @Override
  public void SBaseDeleted(PluginSBase sbase) {
    propertyChangeVis.deleteSBase(sbase.toString());
  }

  /* (non-Javadoc)
   * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
   */
  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    if (evt.getPropertyName().equals("state") && evt.getNewValue().equals(SwingWorker.StateValue.DONE)) {
      try {
        SBMLDocument doc = ((SwingWork)evt.getSource()).get();
        doc.addTreeNodeChangeListener(new PluginChangeListener(this));
        new CDPropertyChangeVis();
      } catch (Throwable e) {
        new GUIErrorConsole(e);
      }
    }
  }
}

