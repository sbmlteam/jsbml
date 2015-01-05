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

import javax.swing.*;
import java.net.*;


/**
 * Pop-up dialog window helper class.
 * @file    Dialog.java
 * @brief   Pop-up dialog window helper class.
 * @author  Michael Hucka
 * @date    Created in 2012
 */
public class Dialog
{
  /**
   * Creates a modal yes/no dialog.
   *
   * @param parent The parent window JFrame object.
   * @param question The question to ask in the body of the dialog.
   * @param title A string to use as the title of the dialog window.
   * @return
   */
  public static boolean yesNo(JFrame parent, String question, String title)
  {
    Log.note("Creating yes/no dialog with title '" + title + "'.");

    int dialogValue = 0;
    URL iconURL = Dialog.class.getResource(Config.RES_ICON_QUESTION);

    if (iconURL == null) {
      Log.note("Unable to get icon from " + Config.RES_ICON_QUESTION
        + "; reverting to Java defaults.");
    }

    // If we don't already have a frame (probably because we haven't
    // even managed to start up yet), create a fake one.

    if (parent == null)
    {
      parent = new JFrame(Config.APP_NAME);
      parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    try
    {
      if (iconURL != null)
      {
        ImageIcon icon = new ImageIcon(iconURL);
        dialogValue
        = JOptionPane.showConfirmDialog(parent, question, title,
          JOptionPane.YES_NO_OPTION,
          JOptionPane.QUESTION_MESSAGE,
          icon);
      }
      else
      {
        dialogValue
        = JOptionPane.showConfirmDialog(parent, question, title,
          JOptionPane.YES_NO_OPTION,
          JOptionPane.QUESTION_MESSAGE);
      }
    }
    catch (Exception ex)
    {
      Log.error("Unable to create question dialog.", ex);
      return false;
    }

    boolean ans = (dialogValue == JOptionPane.YES_OPTION);
    Log.note("User answered " + ans + " to dialog titled '" + title + "'.");
    return ans;
  }


  /**
   * Creates a modal error dialog.
   *
   * The dialog window will have a single button for "OK".  The user
   * will need to click the button to make the dialog go away.
   *
   * @param parent The parent window JFrame object.
   * @param message The message to put in the body of the dialog.
   * @param title A string to use as the title of the dialog window.
   */
  public static void error(JFrame parent, String message, String title)
  {
    Log.note("Creating error dialog with title '" + title + "'.");

    URL iconURL = Dialog.class.getResource(Config.RES_ICON_ERROR);

    if (iconURL == null) {
      Log.note("Unable to get icon from " + Config.RES_ICON_ERROR
        + "; reverting to defaults.");
    }

    // If we don't already have a frame (probably because we haven't
    // even managed to start up yet), create a fake one.

    if (parent == null)
    {
      parent = new JFrame(Config.APP_NAME);
      parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    try
    {
      if (iconURL != null)
      {
        ImageIcon icon = new ImageIcon(iconURL);
        JOptionPane.showMessageDialog(parent, message, title,
          JOptionPane.INFORMATION_MESSAGE, icon);
      }
      else
      {
        JOptionPane.showMessageDialog(parent, message, title,
          JOptionPane.ERROR_MESSAGE);
      }
    }
    catch (Exception ex)
    {
      Log.error("Unable to create error dialog.", ex);
    }
  }


  /**
   * Main for testing only.
   * 
   * @param args
   */
  public static void main(String[] args)
  {
    boolean ans = Dialog.yesNo(new JFrame(), "Is this a yes/no?", "Q1");
    Dialog.yesNo(new JFrame(), "You clicked " + (ans ? "yes" : "no"), "A1");
    Dialog.yesNo(new JFrame(), "Are you ready for an error?", "Q2");
    Dialog.error(new JFrame(), "This is an error dialog", "A2");
    System.exit(0);
  }
}
