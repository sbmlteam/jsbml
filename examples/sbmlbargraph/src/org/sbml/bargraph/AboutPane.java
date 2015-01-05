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
package org.sbml.bargraph;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;


/**
 * Encapsulates the "About SBML Bar Graph" window.
 * @file    About.java
 * @brief   The "About" window for SBML Bar Graph.
 * @author  Michael Hucka
 * @date    Created in 2012
 */
public class AboutPane
extends JFrame
{
  //
  // --------------------------- Public methods -----------------------------
  //

  /**
   * Constructor.
   */
  public AboutPane()
  {
    Log.note("Creating 'About' window.");

    jPanel1 = new JPanel();
    appIcon = new JLabel();
    nameHeader = new JLabel();
    versionLabel = new JLabel();
    versionNum = new JLabel();
    descriptionText = new JTextArea();
    authorsText = new JTextArea();
    moreInfoText = new JTextArea();
    urlButton = new JButton(Config.APP_HOME_URL);
    sbmlIcon = new JLabel();

    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    setTitle("About the " + Config.APP_NAME);
    setResizable(false);

    jPanel1.setDoubleBuffered(false);
    jPanel1.setMaximumSize(new java.awt.Dimension(panelWidth, panelHeight));
    jPanel1.setPreferredSize(new java.awt.Dimension(panelWidth, panelHeight));

    addKeyHandlers(jPanel1);

    appIcon.setIcon(new ImageIcon(getClass().getResource(Config.RES_ICON_APP)));
    appIcon.setFocusable(false);
    appIcon.setRequestFocusEnabled(false);

    nameHeader.setFont(new java.awt.Font("SansSerif", 1, 17));
    nameHeader.setText(Config.APP_NAME);
    nameHeader.setFocusable(false);
    nameHeader.setRequestFocusEnabled(false);

    versionLabel.setFont(new java.awt.Font("SansSerif", java.awt.Font.ITALIC, 12));
    versionLabel.setText("Version");
    versionLabel.setFocusable(false);
    versionLabel.setRequestFocusEnabled(false);

    versionNum.setFont(new java.awt.Font("SansSerif", java.awt.Font.ITALIC, 12));
    versionNum.setText("versionNum");
    versionNum.setToolTipText("The version number of this copy of the "
        + Config.APP_NAME + ".");
    versionNum.setFocusable(false);
    versionNum.setRequestFocusEnabled(false);

    descriptionText.setEditable(false);
    descriptionText.setFont(new java.awt.Font("SansSerif", 0, 11));
    descriptionText.setForeground(new java.awt.Color(51, 51, 51));
    descriptionText.setLineWrap(true);
    descriptionText.setRows(3);
    descriptionText.setText(Config.APP_NAME +
      " is a simple demonstration program written in Java. It" +
      " creates a bar graph of the number of model components" +
        " in an SBML file.");
    descriptionText.setWrapStyleWord(true);
    descriptionText.setAutoscrolls(false);
    descriptionText.setBorder(BorderFactory.createEmptyBorder());
    descriptionText.setDragEnabled(false);
    descriptionText.setFocusable(false);
    descriptionText.setOpaque(false);
    descriptionText.setRequestFocusEnabled(false);

    authorsText.setEditable(false);
    authorsText.setFont(new java.awt.Font("SansSerif", 1, 11));
    authorsText.setForeground(new java.awt.Color(51, 51, 51));
    authorsText.setLineWrap(true);
    authorsText.setRows(1);
    authorsText.setText("Written by Michael Hucka (mhucka@caltech.edu).");
    authorsText.setWrapStyleWord(true);
    authorsText.setAutoscrolls(false);
    authorsText.setBorder(BorderFactory.createEmptyBorder());
    authorsText.setDragEnabled(false);
    authorsText.setFocusable(false);
    authorsText.setOpaque(false);
    authorsText.setRequestFocusEnabled(false);

    moreInfoText.setEditable(false);
    moreInfoText.setFont(new java.awt.Font("SansSerif", 0, 11));
    moreInfoText.setForeground(new java.awt.Color(51, 51, 51));
    moreInfoText.setLineWrap(true);
    moreInfoText.setRows(2);
    moreInfoText.setText("For more information about this software, SBML," +
        " and other software, please visit:");
    moreInfoText.setWrapStyleWord(true);
    moreInfoText.setAutoscrolls(false);
    moreInfoText.setBorder(BorderFactory.createEmptyBorder());
    moreInfoText.setDragEnabled(false);
    moreInfoText.setFocusable(false);
    moreInfoText.setOpaque(false);
    moreInfoText.setRequestFocusEnabled(false);

    java.awt.event.ActionListener visitSBMLurl= new java.awt.event.ActionListener() {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent event)
      {
        Log.note("Open home URL action invoked.");
        Main.openInBrowser(Config.APP_HOME_URL);
      }
    };

    urlButton.setHorizontalAlignment(SwingConstants.CENTER);
    urlButton.setVerticalTextPosition(SwingConstants.CENTER);
    urlButton.setHorizontalTextPosition(SwingConstants.LEADING);
    urlButton.setEnabled(true);
    urlButton.setContentAreaFilled(false);
    urlButton.setForeground(java.awt.Color.blue);
    urlButton.setBorder(BorderFactory.createEmptyBorder());
    urlButton.setBorderPainted(false);
    urlButton.setFocusable(false);
    urlButton.addActionListener(visitSBMLurl);

    sbmlIcon.setIcon(new ImageIcon(getClass().getResource(Config.RES_ICON_SBML))); // NOI18N

    org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
      jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(jPanel1Layout.createSequentialGroup()
        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
          .add(jPanel1Layout.createSequentialGroup()
            .add(5, 5, 5)
            .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
              .add(jPanel1Layout.createSequentialGroup()
                .add(40, 40, 40)
                .add(appIcon)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 10, 20)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                  .add(jPanel1Layout.createSequentialGroup()
                    .add(versionLabel)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 5, 5)
                    .add(versionNum))
                    .add(nameHeader)))
                    .add(descriptionText, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 320, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(moreInfoText, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 315, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(authorsText, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 315, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .add(jPanel1Layout.createSequentialGroup()
                      .add(15, 15, 15)
                      .add(urlButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 300, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                      .add(jPanel1Layout.createSequentialGroup()
                        .add(120, 120, 120)
                        .add(sbmlIcon)))
                        .addContainerGap(5, 5))
        );
    jPanel1Layout.setVerticalGroup(
      jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(jPanel1Layout.createSequentialGroup()
        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
          .add(jPanel1Layout.createSequentialGroup()
            .addContainerGap()
            .add(appIcon))
            .add(jPanel1Layout.createSequentialGroup()
              .add(16, 16, 16)
              .add(nameHeader, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
              .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
              .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(versionNum)
                .add(versionLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 15, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(descriptionText, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 47, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(authorsText, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 15, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(moreInfoText, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 30, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(urlButton)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(sbmlIcon))
        );

    versionNum.setText(Version.asString());

    org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(0, 341, Short.MAX_VALUE)
      .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        .add(layout.createSequentialGroup()
          .add(0, 9, Short.MAX_VALUE)
          .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
          .add(0, 10, Short.MAX_VALUE)))
        );
    layout.setVerticalGroup(
      layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(0, panelHeight, Short.MAX_VALUE)
      .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        .add(layout.createSequentialGroup()
          .add(0, 0, Short.MAX_VALUE)
          .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, panelHeight, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
          .add(0, 0, Short.MAX_VALUE)))
        );

    // Replace the default Java window icon with our own.
    // This only has an effect on non-Mac OS systems.

    URL iconImageURL = getClass().getResource(Config.RES_ICON_APP);
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Image img = toolkit.createImage(iconImageURL);
    setIconImage(img);

    pack();
    setLocationRelativeTo(null);
    setAlwaysOnTop(true);
  }


  /**
   * @param panel
   */
  private void addKeyHandlers(JPanel panel) {
    KeyStroke closeKey = KeyStroke.getKeyStroke(
      KeyEvent.VK_W, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());

    Action closeWindowAction = new AbstractAction("Close Window") {
      /* (non-Javadoc)
       * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
       */
      @Override
      public void actionPerformed(ActionEvent e)
      {
        Log.note("Got keyboard window-close event.");
        dispose();
      }
      private static final long serialVersionUID = 1L;
    };

    panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(closeKey, "closeWindow");
    panel.getActionMap().put("closeWindow", closeWindowAction);
  }


  /**
   * Testing harness.
   * 
   * Shows the About dialog for a fixed length of time, without having to
   * launch the whole application.
   * 
   * @param args the command line arguments
   */
  public static void main(String args[])
  {
    Log.note("Starting AboutPane test run.");
    java.awt.EventQueue.invokeLater(new Runnable() {
      @Override
      public void run() {
        AboutPane pane = new AboutPane();
        pane.setVisible(true);
      }

    });

    try
    {
      Thread.sleep(5000);
    }
    catch (InterruptedException e)
    {
      System.out.println(e);
    }

    Log.note("Stopping AboutPane test run.");
    System.exit(0);
  }


  //
  // -------------------------- Private variables ---------------------------
  //

  /**
   * 
   */
  private JTextArea authorsText;
  /**
   * 
   */
  private JTextArea descriptionText;
  /**
   * 
   */
  private JPanel jPanel1;
  /**
   * 
   */
  private JTextArea moreInfoText;
  /**
   * 
   */
  private JLabel nameHeader;
  /**
   * 
   */
  private JLabel sbmlIcon;
  /**
   * 
   */
  private JLabel appIcon;
  /**
   * 
   */
  private JButton urlButton;
  /**
   * 
   */
  private JLabel versionLabel;
  /**
   * 
   */
  private JLabel versionNum;

  //
  // -------------------------- Private constants ---------------------------
  //

  /**
   * in pixels
   */
  final static int panelHeight = 290;
  /**
   * in pixels.
   */
  final static int panelWidth  = 325;

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
}
