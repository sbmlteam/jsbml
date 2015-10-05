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

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBMLWriter;
import org.sbml.jsbml.gui.SBMLFileFilter;

/**
 * @author Ibrahim Vazirabad
 * @version $Rev 1776$
 * @since 1.0
 * @date Jun 3, 2014
 */
public class SBMLLayoutVisualizer extends JFrame implements ActionListener {
  /** Generated serial version identifier */
  private static final long serialVersionUID = -6800051247041441688L;
  /**
   * 
   */
  private final SBMLDocument sbmlDocument;

  /**
   * @param document
   *        The sbml root node of an SBML file
   * @throws XMLStreamException
   * @throws SBMLException
   */
  public SBMLLayoutVisualizer(SBMLDocument document) throws XMLStreamException {
    super("Textual SBML Visualization");
    sbmlDocument=document;
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    JButton SBMLExportButton = new JButton("Export SBML to File");
    SBMLExportButton.addActionListener(this);
    SBMLExportButton.setIcon(new ImageIcon(this.getClass().getResource("/org/sbml/jsbml/celldesigner/save_file.png")));
    SBMLExportButton.setVerticalTextPosition(SwingConstants.BOTTOM);
    SBMLExportButton.setHorizontalTextPosition(SwingConstants.CENTER);

    SBMLWriter writer = new SBMLWriter();
    JTextArea SBMLViewerArea = new JTextArea(40,100);
    //writing SBML to String
    String SBMLString = writer.writeSBMLToString(document);
    SBMLViewerArea.append(SBMLString);

    //Adding JToolBar to the Pane
    JToolBar toolBar = new JToolBar("Export SBML to File");
    toolBar.setLayout(new FlowLayout(FlowLayout.CENTER));
    toolBar.add(SBMLExportButton);

    Container contentPane = getContentPane();
    contentPane.add(new JScrollPane(SBMLViewerArea), BorderLayout.CENTER);
    contentPane.add(toolBar, BorderLayout.PAGE_START);
    pack();
    setAlwaysOnTop(true);
    setLocationRelativeTo(null);
    setVisible(true);
  }

  /**
   * @param args
   *        Expects a valid path to an SBML file.
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    new SBMLLayoutVisualizer(SBMLReader.read(new File(args[0])));
  }

  /**
   * Saves SMBL Layout to File
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    JFileChooser chooser = new JFileChooser(new File(System
      .getProperties().getProperty("user.home") + "/Desktop"));
    SBMLFileFilter fileFilter = new SBMLFileFilter();
    chooser.setFileFilter(fileFilter);
    int retrival = chooser.showSaveDialog(null);
    if (retrival == JFileChooser.APPROVE_OPTION) {
      try {
        File outputFile = chooser.getSelectedFile();
        SBMLWriter.write(sbmlDocument, outputFile, "SBMLLayoutVisualizer", "1.0");
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }
}
