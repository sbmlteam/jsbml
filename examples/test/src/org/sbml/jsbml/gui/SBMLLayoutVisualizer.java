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
package org.sbml.jsbml.gui;

import java.awt.Button;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBMLWriter;


/**
 * @author Ibrahim Vazirabad
 * @version $Rev 1776$
 * @since 1.0
 * @date Jun 3, 2014
 */
public class SBMLLayoutVisualizer extends JFrame implements ActionListener {
  /** Generated serial version identifier */
  private static final long serialVersionUID = -6800051247041441688L;
  private String SBMLString=""; //the String representation of the

  /** @param document The sbml root node of an SBML file
   * @throws XMLStreamException
   * @throws SBMLException */
  public SBMLLayoutVisualizer(SBMLDocument document) throws SBMLException, XMLStreamException {
    super("SBML Layout Viz");
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    //set FlowLayout
    Container contentPane = getContentPane();
    contentPane.setLayout(new FlowLayout());
    //creating Button and listening for click
    Button SBMLExportButton=new Button("Export SBML to File");
    SBMLExportButton.addActionListener(this);

    SBMLWriter writer=new SBMLWriter();
    JTextArea SBMLViewerArea=new JTextArea(50,120);
    //writing SBML to String
    SBMLString=writer.writeSBMLToString(document);
    //adding it to the Viewing Area
    SBMLViewerArea.append(SBMLString);
    contentPane.add(new JScrollPane(SBMLViewerArea));
    //Adding Button to the Pane
    contentPane.add(SBMLExportButton);
    pack();
    setLocationRelativeTo(null);
    setAlwaysOnTop(true);
    setVisible(true);
  }

  /** @param args Expects a valid path to an SBML file. */
  public static void main(String[] args) throws Exception {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    new SBMLLayoutVisualizer(SBMLReader.read(new File(args[0])));
  }

  /* (non-Javadoc)
   * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
   * Saves SMBL Layout to File
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    JFileChooser chooser = new JFileChooser(new File(System
      .getProperties().getProperty("user.home") + "/Desktop"));
    SBMLFileFilter fileFilter=new SBMLFileFilter();
    chooser.setFileFilter(fileFilter);
    int retrival = chooser.showSaveDialog(null);
    if (retrival == JFileChooser.APPROVE_OPTION) {
        try {
            File outputFile=chooser.getSelectedFile();
            FileWriter fw = new FileWriter(outputFile+".xml");
            BufferedWriter out = new BufferedWriter(fw);
            out.write(SBMLString);
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
  }


}
