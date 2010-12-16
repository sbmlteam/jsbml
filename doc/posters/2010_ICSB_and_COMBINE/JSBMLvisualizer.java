package org.sbml.gui;

import javax.swing.*;

import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.xml.stax.SBMLReader;

public class JSBMLvisualizer extends JFrame {

	public JSBMLvisualizer(SBMLDocument document) {
		super(document.isSetModel() ? document.getModel().getId() : "SBML Visualizer");
		getContentPane().add(
				new JScrollPane(new JTree(document),
						JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
						JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		new JSBMLvisualizer(SBMLReader.readSBML(args[0]));
	}

}
