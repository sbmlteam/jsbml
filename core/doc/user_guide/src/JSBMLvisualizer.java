package org.sbml.jsbml.gui;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;

public class JSBMLvisualizer extends JFrame {
  /** @param document The sbml root node of an SBML file */
  public JSBMLvisualizer(DefaultTreeModel tree) {
    super("SBML Structure Visualization");
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    DefaultTreeModel treeModel = tree;
    JTree jTree = new JTree(treeModel);
    getContentPane().add(new JScrollPane(jTree));
    pack();
    setAlwaysOnTop(true);
    setLocationRelativeTo(null);
    setVisible(true);
  }

  /** Main. Note: this doesn't perform error checking, but should. It is an illustration only. */
  public static void main(String[] args) throws Exception {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    new JSBMLvisualizer(SBMLReader.read(new File(args[0])));
  }
}
