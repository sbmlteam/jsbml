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

import javax.swing.SwingWorker;

import jp.sbi.celldesigner.plugin.PluginModel;

import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.ext.fbc.FBCConstants;
import org.sbml.jsbml.ext.layout.LayoutConstants;
import org.sbml.jsbml.ext.render.RenderConstants;


/**
 * @author Ibrahim Vazirabad
 * @version $Rev$
 * @since 1.0
 * @date Jun 10, 2014
 */
public class SBMLDocumentWorker extends SwingWorker<SBMLDocument, Throwable> {

  /**
   * 
   */
  private final PluginSBMLReader sbmlReader;
  /**
   * 
   */
  private final PluginModel pluginModel;


  /**
   * @return the pluginModel
   */
  public PluginModel getPluginModel() {
    return pluginModel;
  }

  /**
   * @param reader
   * @param selectedModel
   */
  public SBMLDocumentWorker(PluginSBMLReader reader, PluginModel selectedModel) {
    sbmlReader=reader;
    pluginModel=selectedModel;
  }

  /**
   * Receives the Model from the PluginSBMLReader and creates a SBMLDocument with Level 3 Extensions.
   */
  @SuppressWarnings("deprecation")
  @Override
  protected SBMLDocument doInBackground() throws Exception {
    try{
      Model model = sbmlReader.convertModel(pluginModel);
      SBMLDocument doc = new SBMLDocument(model.getLevel(), model.getVersion());
      doc.addNamespace(LayoutConstants.shortLabel, "xmlns", LayoutConstants.getNamespaceURI(doc.getLevel(), doc.getVersion()));
      doc.addNamespace(RenderConstants.shortLabel, "xmlns", RenderConstants.getNamespaceURI(doc.getLevel(), doc.getVersion()));
      doc.addNamespace(FBCConstants.shortLabel, "xmlns", FBCConstants.getNamespaceURI(doc.getLevel(), doc.getVersion()));
      doc.getSBMLDocumentAttributes().put(LayoutConstants.shortLabel + ":required", "false");
      doc.getSBMLDocumentAttributes().put(RenderConstants.shortLabel + ":required", "false");
      doc.getSBMLDocumentAttributes().put(FBCConstants.shortLabel + ":required", "false");
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
