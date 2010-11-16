/*
 * $Id:  JTreeOfSBML.java 17:49:52 draeger $
 * $URL: JTreeOfSBML.java $
 *
 * 
 *==================================================================================
 * Copyright (c) 2009 The jsbml team.
 *
 * This file is part of jsbml, the pure java SBML library. Please visit
 * http://sbml.org for more information about SBML, and http://jsbml.sourceforge.net/
 * to get the latest version of jsbml.
 *
 * jsbml is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * jsbml is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with jsbml.  If not, see <http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html>.
 *
 *===================================================================================
 *
 */
package org.sbml.jsbml.test.gui;

import java.awt.Color;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTree;

import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.xml.stax.SBMLReader;

/**
 * A test GUI that displays the structure of a given SBML file in a
 * {@link JTree}.
 * 
 * @author Andreas Dr&auml;ger
 * @date 2010-05-27
 */
public class JTreeOfSBML extends JDialog {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = 1299792977025662080L;

	/**
	 * 
	 * @param fileName
	 *            The path to an SBML file.
	 */
	public JTreeOfSBML(String fileName) {
		super();
		try {
			SBMLDocument doc = SBMLReader.readSBML(fileName);
			showGUI(doc);
		} catch (Exception exc) {
			exc.printStackTrace();
			JOptionPane.showMessageDialog(this, exc.getMessage(), exc
					.getClass().getSimpleName(), JOptionPane.ERROR_MESSAGE);
			dispose();
		}
	}

	private SBMLDocument createDefaultDocument() {
		SBMLDocument doc = new SBMLDocument(2, 4);
		Model m = doc.createModel("untitled");
		m.createSpecies("s1");
		return doc;
	}
	
	/**
	 * artificial model for testing.
	 */
	public JTreeOfSBML() {
		super();
		showGUI(createDefaultDocument());
	}
	
	/**
	 * 
	 * @param doc
	 */
	public JTreeOfSBML(SBMLDocument doc) {
		super();
		showGUI(doc);
	}

	/**
	 * Displays the structure of the given {@link SBMLDocument} to the user.
	 * 
	 * @param doc
	 */
	private void showGUI(SBMLDocument doc) {
		if (doc.isSetModel()) {
			Model m = doc.getModel();
			String title = "Content of model \"";
			if (m.isSetName()) {
				title += m.getName();
			} else if (m.isSetId()) {
				title += m.getId();
			} else {
				title += "undefined";
			}
			setTitle(title + "\"");
		} else {
			setTitle("SBML content visualizer");
		}
		JTree tree = new JTree(doc);
		tree.setBackground(Color.WHITE);
		tree.expandRow(tree.getRowCount() - 1);
		getContentPane().add(
				new JScrollPane(tree, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
						JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
		setEnabled(true);
		setResizable(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setModal(true);
		setVisible(true);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			new JTreeOfSBML();
		} else {
			new JTreeOfSBML(args[0]);
		}
	}

}
