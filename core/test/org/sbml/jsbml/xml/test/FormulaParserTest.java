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

package org.sbml.jsbml.xml.test;

import java.io.StringReader;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.text.parser.FormulaParser;
import org.sbml.jsbml.text.parser.ParseException;

/**
 * 
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * @version $Rev$
 */
public class FormulaParserTest {

	private static String[] testCases = {
	// "23 + 52^2 - 3",
			// "a - log10(5)^11",
			// "f(a, b, c, d)",
			// "23 + (52^2 - 3 + 5)/7 - 45",
			// "10+ --3.5e7 *5",
			"5/4 + 4/5*10", "10 + -0.3E-5*10", "ceil (-2.9)"
	// "Vf*(A*B - P*Q/Keq)/(Kma + A*(1 + P/Kip) + (Vf/(Vr*Keq)) * Kmq*P + Kmp*Q + P*Q)",
	// "(a * (b + c) * d)/(e +  3) *   5"
	};

	/**
	 * 
	 * @param args
	 * @throws SBMLException
	 * @throws UnsupportedLookAndFeelException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	public static void main(String args[]) throws SBMLException,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {

		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		Model m = new Model("test", 2, 4);
		m.createFunctionDefinition("f");
		m.createRateRule();
		FormulaParser parser;

		for (int i = 0; i < testCases.length; i++) {
			System.out.printf("%d.\treading:\t%s\n", i, testCases[i]);
			parser = new FormulaParser(new StringReader(testCases[i]));
			ASTNode node;
			try {
				node = parser.parse();
				System.out.printf("%d.\tLaTeX:\t%s\n", i, node.toLaTeX());

				JDialog d = new JDialog();
				d.setTitle("Node output");
				d.setModal(true);
				d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				JTree tree = new JTree(node);
				d.getContentPane().add(
						new JScrollPane(tree,
								JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
								JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
				d.pack();
				d.setLocationRelativeTo(null);
				d.setVisible(true);
			} catch (ParseException e) {

				e.printStackTrace();
			}

		}
	}

}
