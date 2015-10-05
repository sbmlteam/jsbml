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

import java.awt.event.ActionEvent;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.xml.stream.XMLStreamException;

import jp.sbi.celldesigner.plugin.PluginAction;


/**
 * @author Ibrahim Vazirabad
 * @version $Rev$
 * @since 1.0
 * @date Jul 8, 2014
 */
public abstract class AbstractCellDesignerPluginAction extends PluginAction {

  /**
   * Generated serial version identifier
   */
  private static final long serialVersionUID = 779167434967181327L;
  /**
   * The plugin that is triggered when this object receives appropriate actions.
   */
  private final AbstractCellDesignerPlugin plugin;

  /**
   * @param plugin
   */
  public AbstractCellDesignerPluginAction(AbstractCellDesignerPlugin plugin)
  {
    super();
    this.plugin = plugin;
  }

  /* (non-Javadoc)
   * @see jp.sbi.celldesigner.plugin.PluginActionListener#myActionPerformed(java.awt.event.ActionEvent)
   */
  @Override
  public void myActionPerformed(ActionEvent evt) {
    if (evt.getSource() instanceof JMenuItem) {
      JMenuItem item = (JMenuItem) evt.getSource();
      if (item.getText().equals(AbstractCellDesignerPlugin.ACTION)) {
        try {
          plugin.startPlugin();
        } catch (XMLStreamException exc) {
          JOptionPane.showMessageDialog(item, exc.getMessage(),
            exc.getClass().toString(), JOptionPane.ERROR_MESSAGE);
          new GUIErrorConsole(exc);
        }
      }
    } else {
      JOptionPane.showMessageDialog(null, "Unsupported source of action "
          + evt.getSource().getClass().getName(), "Invalid Action",
          JOptionPane.WARNING_MESSAGE);
    }
  }
}
