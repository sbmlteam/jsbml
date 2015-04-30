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

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;

/**
 * @author Ibrahim Vazirabad
 * @version $Rev$
 * @since 1.0
 * @date Jul 6, 2014
 */
/** Displays the content of an SBML file in a {@link JTree} */
public class SBMLStructureVisualizer extends JFrame {

  /** Generated serial version identifier */
  private static final long serialVersionUID = -6800051247041441688L;

  /** @param tree The sbml root node of an SBML file */
  public SBMLStructureVisualizer(DefaultTreeModel tree) {
    super("SBML Layout Visualization");
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    DefaultTreeModel treeModel = tree;
    JTree jTree = new JTree(treeModel);
    getContentPane().add(new JScrollPane(jTree));
    pack();
    setAlwaysOnTop(true);
    setLocationRelativeTo(null);
    setVisible(true);
  }
}