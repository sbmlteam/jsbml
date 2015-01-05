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
package org.sbml.jsbml.celldesigner;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import jp.sbi.celldesigner.plugin.CellDesignerPlugin;
import jp.sbi.celldesigner.plugin.PluginModel;
import jp.sbi.celldesigner.plugin.PluginSBase;

import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLOutputConverter;

/**
 * @author Andreas Dr&auml;ger
 * @version $Rev$
 * @since 0.8
 */
public class PluginSBMLWriter implements SBMLOutputConverter<PluginModel> {

  /**
   * 
   */
  private static final String error = " must be an instance of ";
  /**
   * 
   */
  private CellDesignerPlugin plugin;
  /**
   * 
   */
  private PluginModel pluginModel;

  /**
   * 
   * @param plugin
   */
  public PluginSBMLWriter(CellDesignerPlugin plugin) {
    this.plugin = plugin;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBMLWriter#getWriteWarnings(java.lang.Object)
   */
  @Override
  public List<SBMLException> getWriteWarnings(PluginModel sbase) {
    return new LinkedList<SBMLException>();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBMLOutputConverter#writeSBML(java.lang.Object, java.lang.String)
   */
  @Override
  public boolean writeSBML(PluginModel sbmlModel, String filename)
      throws IOException, SBMLException {
    return writeSBML(sbmlModel, filename, null, null);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBMLWriter#writeSBML(java.lang.Object,java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean writeSBML(PluginModel sbmlModel, String filename,
    String programName, String versionNumber) throws SBMLException,
    IOException {
    if (!(sbmlModel instanceof PluginSBase)) {
      throw new IllegalArgumentException("sbmlModel" + error + "PluginSBase");
    }
    PluginSBase sbase = sbmlModel;
    BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
    if ((programName != null) || (versionNumber != null)) {
      bw.append("<!-- ");
      if (programName != null) {
        bw.append("created by ");
        bw.append(programName);
        if (versionNumber != null) {
          bw.append(' ');
        }
      }
      if (versionNumber != null) {
        bw.append("version ");
        bw.append(versionNumber);
      }
      bw.append(" -->");
      bw.newLine();
    }
    bw.append(sbase.toSBML());
    bw.close();
    return true;
  }

}
