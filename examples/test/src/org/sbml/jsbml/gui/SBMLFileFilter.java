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
package org.sbml.jsbml.gui;

import java.io.File;
import java.io.FileFilter;
import java.util.ResourceBundle;

import javax.swing.filechooser.FileNameExtensionFilter;

import org.sbml.jsbml.util.ResourceManager;

/**
 * @author Ibrahim Vazirabad
 * @version $Rev$
 * @since 1.0
 * @date Jun 10, 2014
 * File filter that permits GUI-based classes to display only .sbml and .xml files.
 */
public class SBMLFileFilter extends javax.swing.filechooser.FileFilter implements
FileFilter {

  /**
   * 
   */
  private final FileNameExtensionFilter filter;
  /**
   * 
   */
  private static final ResourceBundle bundle = ResourceManager.getBundle("org.sbml.jsbml.gui.UserMessages");

  /**
   *
   */
  public SBMLFileFilter() {
    super();
    filter= new FileNameExtensionFilter(
      bundle.getString("sbmlFileFilter.EveryDescription"),"xml", "sbml");
  }

  /* (non-Javadoc)
   * @see java.io.FileFilter#accept(java.io.File)
   */
  @Override
  public boolean accept(File pathname) {
    return filter.accept(pathname);
  }

  /* (non-Javadoc)
   * @see javax.swing.filechooser.FileFilter#getDescription()
   */
  @Override
  public String getDescription() {
    return filter.getDescription();
  }

}
