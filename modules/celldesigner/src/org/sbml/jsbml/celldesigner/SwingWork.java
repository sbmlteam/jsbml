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

import java.awt.Dimension;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import jp.sbi.celldesigner.plugin.PluginModel;

import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;


/**
 * @author Ibrahim Vazirabad
 * @version $Rev$
 * @since 1.0
 * @date Jun 10, 2014
 */
public class SwingWork extends SwingWorker<SBMLDocument, Throwable> {

  PluginSBMLReader sbmlReader;
  PluginModel pluginModel;

    /* (non-Javadoc)
   * @see javax.swing.SwingWorker#process(java.util.List)
   */
  @Override
  protected void process(List<Throwable> chunks) {
    for (Throwable e:chunks)
    {
    StringBuilder builder = new StringBuilder();
    if (e.getMessage()==null && e.getCause()!=null) {
      builder.append(e.getCause().getMessage());
    } else {
      builder.append(e.getMessage());
    }
    builder.append("\n");
    for (StackTraceElement element: e.getStackTrace())
    {
      builder.append(element.toString());
      builder.append("\n");
    }
    if (e.getCause()!=null)
    {
      Throwable cause = e.getCause();
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
    JOptionPane.showMessageDialog(null, pane,e.getClass().getSimpleName(),JOptionPane.ERROR_MESSAGE);
    }
  }

    /**
   * @param reader
   * @param selectedModel
   */
  public SwingWork(PluginSBMLReader reader, PluginModel selectedModel) {
    sbmlReader=reader;
    pluginModel=selectedModel;

  }

    @Override
    protected SBMLDocument doInBackground() throws Exception {
      try{
        Model model = sbmlReader.convertModel(pluginModel);
        SBMLDocument doc = new SBMLDocument(model.getLevel(), model.getVersion());
        doc.setModel(model);
        return doc;
      }
      catch (Throwable e)
      {
        publish(e);
      }
      return null;
    }
  };
