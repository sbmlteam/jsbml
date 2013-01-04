/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2013 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.test.gui;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.UIManager;

import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLReader;

/** Displays the content of an SBML file in a {@link JTree} */
public class JSBMLvisualizer extends JFrame {

	/** Generated serial version identifier */
	private static final long serialVersionUID = -6800051247041441688L;
	
	/** @param document The sbml root node of an SBML file */
	public JSBMLvisualizer(SBMLDocument document) {
		super("JSBML viz");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().add(new JScrollPane(new JTree(document)));
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/** @param args Expects a valid path to an SBML file. */ 
	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		new JSBMLvisualizer(SBMLReader.read(new File(args[0])));
	}

}
