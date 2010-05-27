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

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTree;

import org.sbml.jsbml.xml.stax.SBMLReader;

/**
 * A test GUI that displays the structure of a given SBML file in a
 * {@link JTree}.
 * 
 * @author Andreas Dr&auml;ger
 * @date 2010-05-27
 */
public class JTreeOfSBML extends JFrame {

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
		super("SBML content visualizer");
		try {
			getContentPane().add(
					new JScrollPane(new JTree(SBMLReader.readSBML(fileName)),
							JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
							JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			pack();
			setLocationRelativeTo(null);
			setVisible(true);
		} catch (Exception exc) {
			exc.printStackTrace();
			JOptionPane.showMessageDialog(this, exc.getMessage(), exc
					.getClass().getSimpleName(), JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new JTreeOfSBML(args[0]);
	}

}
