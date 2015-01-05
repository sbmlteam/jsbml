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
package org.sbml.jsbml.gui;

import java.awt.Color;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;

import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.xml.stax.SBMLReader;

/**
 * A test GUI that displays the structure of a given SBML file in a
 * {@link JTree}.
 * 
 * @author Andreas Dr&auml;ger
 * @date 2010-05-27
 * @since 0.8
 * @version $Rev$
 */
public class JTreeOfSBML extends JDialog {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 1299792977025662080L;

  /**
   * 
   * @param fileName
   *            The path to an SBML file.
   */
  public JTreeOfSBML(String fileName) {
    super();
    try {
      SBMLDocument doc = new SBMLReader().readSBML(fileName);
      showGUI(doc);
    } catch (Exception exc) {
      exc.printStackTrace();
      JOptionPane.showMessageDialog(this, exc.getMessage(), exc
        .getClass().getSimpleName(), JOptionPane.ERROR_MESSAGE);
      dispose();
    }
  }

  /**
   * @return
   */
  private SBMLDocument createDefaultDocument() {
    SBMLDocument doc = new SBMLDocument(2, 4);
    Model m = doc.createModel("untitled");
    m.createSpecies("s1");
    return doc;
  }

  /**
   * artificial model for testing.
   */
  public JTreeOfSBML() {
    super();
    showGUI(createDefaultDocument());
  }

  /**
   * 
   * @param doc
   */
  public JTreeOfSBML(SBMLDocument doc) {
    super();
    showGUI(doc);
  }

  /**
   * Displays the structure of the given {@link SBMLDocument} to the user.
   * 
   * @param doc
   */
  private void showGUI(SBMLDocument doc) {
    if (doc.isSetModel()) {
      Model m = doc.getModel();
      String title = "Content of model \"";
      if (m.isSetName()) {
        title += m.getName();
      } else if (m.isSetId()) {
        title += m.getId();
      } else {
        title += "undefined";
      }
      setTitle(title + "\"");
    } else {
      setTitle("SBML content visualizer");
    }
    JTree tree = new JTree(doc);
    tree.setBackground(Color.WHITE);
    tree.expandRow(tree.getRowCount() - 1);
    getContentPane().add(
      new JScrollPane(tree, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED));
    setEnabled(true);
    setResizable(true);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    pack();
    setLocationRelativeTo(null);
    setModal(true);
    setVisible(true);
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      new JTreeOfSBML();
    } else {
      new JTreeOfSBML(args[0]);
    }
  }

}
