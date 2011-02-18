import javax.swing.*;
import org.sbml.jsbml.*;

/** Displays the content of an SBML file in a {@link JTree} */
public class JSBMLvisualizer extends JFrame {

	/** @param document The sbml root node of an SBML file */
	public JSBMLvisualizer(SBMLDocument document) {
		super(document.getModel().getId());
		getContentPane().add(new JScrollPane(new JTree(document)));
		pack();
		setVisible(true);
	}
	/** @param args Expects a valid path to an SBML file. */ 
	public static void main(String[] args) throws Exception {
		new JSBMLvisualizer((new SBMLReader()).readSBML(args[0]));
	}
}