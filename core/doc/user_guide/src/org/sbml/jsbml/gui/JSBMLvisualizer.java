package org.sbml.jsbml.gui;
import java.io.File;
import javax.swing.*;
import org.sbml.jsbml.*;

public class JSBMLvisualizer extends JFrame {
  /** @param tree The sbml root node of an SBML file */
  public JSBMLvisualizer(SBase tree) {
    super("SBML Structure Visualization");
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    getContentPane().add(new JScrollPane(new JTree(tree)));
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
