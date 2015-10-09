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

import java.awt.Dimension;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


/**
 * @author Ibrahim Vazirabad
 * @version $Rev$
 * @since 1.0
 * @date Jun 21, 2014
 */
public class GUIErrorConsole {

  /**
   * Generates an GUI Error Console reporting errors in a program's execution.
   * @param error Contains exception/error information
   */
  public GUIErrorConsole(Throwable error)
  {
    StringBuilder builder = new StringBuilder();
    if (error.getMessage() == null && error.getCause()!=null) {
      builder.append(error.getCause().getMessage());
    } else {
      builder.append(error.getMessage());
    }
    builder.append("\n");
    for (StackTraceElement element: error.getStackTrace())
    {
      builder.append(element.toString());
      builder.append("\n");
    }
    if (error.getCause()!=null)
    {
      Throwable cause = error.getCause();
      builder.append("Caused by:\n");
      for (StackTraceElement element: cause.getStackTrace())
      {
        builder.append(element.toString());
        builder.append("\n");
      }
    }
    JTextArea area = new JTextArea(builder.toString());
    JScrollPane pane = new JScrollPane(area);
    pane.setPreferredSize(new Dimension(640, 480));
    JOptionPane.showMessageDialog(null, pane,error.getClass().getSimpleName(),JOptionPane.ERROR_MESSAGE);
  }
}
