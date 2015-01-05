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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;


/**
 * Main window for the SBML Bar Graph.
 * @file    Main.java
 * @brief   Main window for SBML Bar Graph.
 * @author  Michael Hucka
 * @date    Created in 2012
 */
public class MainWindow
extends JFrame
{
  //
  // --------------------------- Public methods -----------------------------
  //

  /**
   * Creates the main application frame.
   * 
   * @param file A file to read and graph upon start up.  If null, nothing
   * is graphed initially.
   */
  public MainWindow(File file)
  {
    Log.note("Creating main application window.");
    setPlatformProperties();

    setBackground(new Color(255, 255, 255));
    setMaximumSize(new Dimension(2000, 1000));
    setMinimumSize(new Dimension(600, 400));
    setTitle("SBML Bar Graph");
    setSize(new Dimension(600, 400));
    setPreferredSize(new Dimension(600, 400));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 550, 400);
    contentPane = new JPanel();
    contentPane.setSize(new Dimension(600, 400));
    contentPane.setPreferredSize(new Dimension(600, 400));
    contentPane.setMinimumSize(new Dimension(600, 400));
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    contentPane.setLayout(new BorderLayout(0, 0));
    setContentPane(contentPane);

    fileNamePanel = new JPanel();
    fileNamePanel.setPreferredSize(new Dimension(590, 20));
    fileNamePanel.setMinimumSize(new Dimension(590, 20));
    fileNamePanel.setLayout(new BorderLayout(0, 0));
    contentPane.add(fileNamePanel, BorderLayout.NORTH);

    fileNameField = new JTextField();
    fileNameField.setHorizontalAlignment(SwingConstants.CENTER);
    fileNameField.setText("No file selected");
    fileNameField.setDisabledTextColor(Color.LIGHT_GRAY);
    fileNameField.setFont(new Font("Lucida Grande", Font.ITALIC, 11));
    fileNameField.setBorder(null);
    fileNameField.setOpaque(true);
    fileNameField.setBackground(UIManager.getColor("Separator.foreground"));
    fileNameField.setEnabled(false);
    fileNameField.setSize(new Dimension(590, 0));
    fileNameField.setPreferredSize(new Dimension(590, 28));
    fileNameField.setMinimumSize(new Dimension(590, 28));
    fileNameField.setColumns(10);
    fileNamePanel.add(fileNameField, BorderLayout.CENTER);

    // The bar graph panel.

    chartPanel = new ChartPanel(createModelBarGraph(), false);
    contentPane.add(chartPanel, BorderLayout.CENTER);
    updatePanelForSBMLFile(file);

    // Manual additions for File menu.

    fileMenu = new JMenu();
    fileMenu.setText("File");

    openFileMenuItem = new JMenuItem();
    openFileMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
      java.awt.event.KeyEvent.VK_O, shortcutKeyMask));
    openFileMenuItem.setText("Open...");
    openFileMenuItem.setToolTipText("Open file to be graphed");
    openFileMenuItem.addActionListener(new java.awt.event.ActionListener() {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        openFileHandler(evt);
      }
    });

    fileMenu.add(openFileMenuItem);
    menuBar = new javax.swing.JMenuBar();
    menuBar.add(fileMenu);

    setJMenuBar(menuBar);

    // Replace the default Java window icon with our own.
    // This only has an effect on non-Mac OS systems.

    URL iconImageURL = getClass().getResource(Config.RES_ICON_APP);
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Image img = toolkit.createImage(iconImageURL);
    setIconImage(img);

    Log.note("Finished constructing panel and menu bar");
  }


  /**
   * Creates an empty bar graph.
   * @return
   */
  public JFreeChart createModelBarGraph()
  {
    // Initialize the style. Have to do this before creating charts.

    BarRenderer.setDefaultShadowsVisible(false);
    ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme());

    // Create the actual chart.

    chartData = new DefaultCategoryDataset();
    JFreeChart chart = ChartFactory.createBarChart(
      null, // chart title
      null, // domain axis label
      "Total count", // range axis label
      chartData, // data
      PlotOrientation.HORIZONTAL, // orientation
      false, // include legend
      false, // tooltips?
      false // URLs?
        );

    CategoryPlot plot = (CategoryPlot) chart.getPlot();
    plot.setBackgroundPaint(Color.white);
    plot.setRangeGridlinePaint(Color.lightGray);
    plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);

    NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
    rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
    rangeAxis.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 12));

    CategoryAxis domainAxis = plot.getDomainAxis();
    domainAxis.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 12));

    BarRenderer renderer = (BarRenderer) plot.getRenderer();
    renderer.setSeriesPaint(0, new Color(0, 101, 178));

    return chart;
  }


  /**
   * Updates the bar graph and other panels in the main window using
   * data derived by reading the given @p file.
   * 
   * @param file The SBML file to parse and analyze for the graph.
   */
  public void updatePanelForSBMLFile(File file)
  {
    if (file == null) {
      return;
    }

    SBMLStats stats;
    try
    {
      stats = new SBMLStats(file);
    }
    catch (javax.xml.stream.XMLStreamException e)
    {
      Log.warning("Exception while parsing '" + file.getPath() + "':");
      Log.warning(e.getMessage());
      Dialog.error(this, "Unable to parse the given SBML File: "
          + e.getMessage(), "File parsing error");
      return;
    }
    catch (java.io.IOException e)
    {
      Log.warning("Exception while parsing '" + file.getPath() + "':");
      Log.warning(e.getMessage());
      Dialog.error(this, "I/O error trying to read the given SBML File: "
          + e.getMessage(), "File parsing error");
      return;
    }

    Log.note("Graphing data: " + stats.asString());

    fileNameField.setText(stats.getFile().getPath());
    fileNameField.setDisabledTextColor(Color.BLACK);

    // The row could be called anything, since we don't display the name.
    String row = "Row 1";

    chartData.clear();
    chartData.addValue(stats.getNumSpecies(), row, "Species");
    chartData.addValue(stats.getNumCompartments(), row, "Compartments");
    chartData.addValue(stats.getNumReactions(), row, "Reactions");
    chartData.addValue(stats.getNumParameters(), row, "Parameters");
    chartData.addValue(stats.getNumRules(), row, "Rules");
    chartData.addValue(stats.getNumUnitDefinitions(), row,
        "Unit definitions");

    if (stats.getSBMLLevel() >= 2)
    {
      chartData.addValue(stats.getNumEvents(), row, "Events");
      chartData.addValue(stats.getNumFunctionDefinitions(), row,
          "Function definitions");

      if (stats.getSBMLVersion() >= 2)
      {
        chartData.addValue(stats.getNumConstraints(), row,
            "Constraints");
        chartData.addValue(stats.getNumInitialAssignments(), row,
            "Initial assignments");
      }

      if (stats.getSBMLVersion() >= 2 && stats.getSBMLLevel() < 3)
      {
        chartData.addValue(stats.getNumSpeciesTypes(), row,
            "Species types");
        chartData.addValue(stats.getNumCompartmentTypes(), row,
            "Compartment types");
      }
    }

    validate();
    repaint();
  }


  /**
   * Configure the windows for platform the user is running.
   */
  public void setPlatformProperties()
  {
    if (Config.runningMac())
    {
      // Extra settings for making the app look natural on Mac OS X.

      Log.note("This is a Mac; setting properties appropriately.");
      System.setProperty("apple.laf.useScreenMenuBar", "true");
      System.setProperty("com.apple.mrj.application.apple.menu.about.name",
        Config.APP_NAME);
      System.setProperty("apple.awt.showGrowBox", "true");
    }

    try
    {
      // Set System L&F
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch (UnsupportedLookAndFeelException e)
    {
      Log.warning("Unable to set look and feel. Proceeding anyway.");
    }
    catch (Exception e)
    {
      Log.warning("Unexpected look & feel configuration failure -- "
          + "something more serious may be wrong");
    }
  }


  /**
   * Performs misc. initializations and makes the main window visible.
   * @throws Exception
   */
  public void initAndShow()
      throws Exception
  {
    pack();
    setLocationRelativeTo(null);

    if (Config.runningMac())
    {
      Log.note("Registering for Mac OS X GUI events");
      registerForMacOSXEvents();
    }
    else
    {
      Log.note("Registering for GUI events");
      registerForEvents();
    }

    Log.note("Making main application window visible.");
    setVisible(true);
  }


  /**
   * Sets up menus using adapters for the Mac OS X look and feel.
   */
  public void registerForMacOSXEvents()
  {
    Log.note("Setting up the Mac OS X application menu.");
    try
    {
      // Generate and register the OSXAdapter, passing it a hash
      // of all the methods we wish to use as delegates for
      // various com.apple.eawt.ApplicationListener methods

      Class<?> c   = getClass();
      Method quit  = c.getDeclaredMethod("quit", (Class[]) null);
      Method about = c.getDeclaredMethod("about", (Class[]) null);

      OSXAdapter.setQuitHandler(this, quit);
      OSXAdapter.setAboutHandler(this, about);
    }
    catch (Exception e)
    {
      Log.error("Unable to set up MacOS application menu", e);
      Main.quit(Main.STATUS_ERROR);
    }
  }


  /**
   * Set up menus when running under Windows.
   */
  public void registerForEvents()
  {
    Log.note("Setting up the Windows menus.");

    JMenuItem fileExitItem = new JMenuItem();
    fileExitItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
      java.awt.event.KeyEvent.VK_W, shortcutKeyMask));
    fileExitItem.setMnemonic(java.awt.event.KeyEvent.VK_C);
    fileExitItem.setText("Exit");
    fileExitItem.setToolTipText("Exit application");
    fileExitItem.addActionListener(new java.awt.event.ActionListener() {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        quit();
      }
    });

    fileMenu.addSeparator();
    fileMenu.add(fileExitItem);

    JMenu helpMenu = new JMenu();
    helpMenu.setText("Help");

    JMenuItem aboutMenuItem = new JMenuItem();
    aboutMenuItem.setText("About " + Config.APP_NAME);
    aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        about();
      }
    });

    helpMenu.add(aboutMenuItem);
    menuBar.add(helpMenu);
  }


  /**
   * Handles the "Open" menu item in the File menu.
   * @param evt
   */
  public void openFileHandler(java.awt.event.ActionEvent evt)
  {
    Log.note("'Open' menu item invoked.");
    FileDialog dialog = new FileDialog(this, "Choose file to graph");
    dialog.setVisible(true);

    if (dialog.getFile() == null)
    {
      Log.note("User cancelled file selection");
      return;
    }

    VerifiableFile theFile
    = new VerifiableFile(dialog.getDirectory() + dialog.getFile());
    if (theFile.isVerifiedFile())
    {
      Log.note("User selected file '" + theFile.getPath() + "'");
      try
      {
        updatePanelForSBMLFile(theFile);
      }
      catch (Exception e)
      {
        Log.error(e.getMessage());
      }
    }
    else
    {
      Log.note("Invalid file selected: '" + theFile.getPath() + "'");
      Dialog.error(this, "Invalid file selected: " + theFile.getName(),
          "File error");
    }
  }


  /**
   * Handles the "About" menu item.
   */
  public void about()
  {
    Log.note("'About' invoked.");
    AboutPane aboutPane = new AboutPane();
    aboutPane.setVisible(true);
  }


  /**
   * Handles the "Quit" menu item and window closings.
   * 
   * @return returns a boolean value if user wants to quit
   */
  public boolean quit()
  {
    String action;
    if (Config.runningMac()) {
      action = "Quit";
    } else {
      action = "Exit";
    }

    Log.note("'" + action + "' invoked.");
    if (Dialog.yesNo(this, action + " the " + Config.APP_NAME + "?",
      action + "?")) {
      Main.quit(Main.STATUS_NORMAL);
    }
    return false;
  }


  /**
   * Testing harness.
   * @param args
   */
  public static void main(String[] args)
  {
    EventQueue.invokeLater(new Runnable() {
      @Override
      public void run()
      {
        try
        {
          MainWindow mainWindow = new MainWindow(null);
          mainWindow.initAndShow();
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
    });
  }


  //
  // ---------------------- Private data members ----------------------------
  //

  /**
   * 
   */
  private JPanel contentPane;
  /**
   * 
   */
  private JPanel fileNamePanel;
  /**
   * 
   */
  private JTextField fileNameField;
  /**
   * 
   */
  private JMenuBar menuBar;
  /**
   * 
   */
  private JMenu fileMenu;
  /**
   * 
   */
  private JMenuItem openFileMenuItem;
  /**
   * 
   */
  private ChartPanel chartPanel;
  /**
   * 
   */
  private DefaultCategoryDataset chartData;
  /**
   * 
   */
  private static int shortcutKeyMask
  = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

}
