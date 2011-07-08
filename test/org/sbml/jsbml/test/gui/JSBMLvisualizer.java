package org.sbml.jsbml.test.gui;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.UIManager;

import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLReader;

/** Displays the content of an SBML file in a {@link JTree} */
public class JSBMLvisualizer extends JFrame {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -6800051247041441688L;
	
	/** @param document The sbml root node of an SBML file */
	public JSBMLvisualizer(SBMLDocument document) {
		super(document.getModel().getId());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().add(new JScrollPane(new JTree(document)));
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/** @param args Expects a valid path to an SBML file. */ 
	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		new JSBMLvisualizer((new SBMLReader()).readSBML(args[0]));
	}
}