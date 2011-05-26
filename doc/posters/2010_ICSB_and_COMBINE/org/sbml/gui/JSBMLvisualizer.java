package org.sbml.gui;

import javax.swing.*;
import org.sbml.jsbml.*;

/** Displays the content of an SBML file in a {@link JTree} */
public class JSBMLvisualizer extends JFrame {

	public JSBMLvisualizer(SBase sbase) {
		super("SBML Visualizer");
		getContentPane().add(new JScrollPane(new JTree(sbase)));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	/** @param args Expects a valid path to an SBML file. */ 
	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		new JSBMLvisualizer(SBMLReader.read(new java.io.File(args[0])));
	}

}
