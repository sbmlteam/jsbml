package org.sbml.jsbml.celldesigner;

import java.awt.event.ActionEvent;
import javax.swing.JMenuItem;
import jp.sbi.celldesigner.plugin.PluginAction;

/** A simple implementation of an action for a CellDesigner plug-in,
 *  which invokes the actual plug-in program. */
public class SimpleCellDesignerPluginAction extends PluginAction {

  /** Generated serial version identifier. */
  private static final long serialVersionUID = 2214922004018714669L;
  
  /** Memorizes a pointer to the actual plug-in program. */
  private SimpleCellDesignerPlugin plugin;

  /** Constructor memorizes the plug-in data structure. */
  public SimpleCellDesignerPluginAction(SimpleCellDesignerPlugin plugin) {
    this.plugin = plugin;
  }

  /** Executes an action if the given command occurs. */
  @Override
  public void myActionPerformed(ActionEvent ae) {
    if (ae.getSource() instanceof JMenuItem) {
      String itemText = ((JMenuItem) ae.getSource()).getText();
      if (itemText.equals(SimpleCellDesignerPlugin.ACTION)) {
        plugin.startPlugin();
      }
    } else {
      System.err.printf("Unsupported source of action %s\n", ae
        .getSource().getClass().getName());
    }
  }

}
