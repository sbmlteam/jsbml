package org.sbml.jsbml.cdplugin;

import java.awt.event.ActionEvent;
import javax.swing.JMenuItem;
import jp.sbi.celldesigner.plugin.PluginAction;

/** A simple implementation of an action for a CellDesigner plug-in,
 *  which invokes the actual plug-in program. */
public class SimpleCellDesignerPluginAction extends PluginAction {

  /** Memorizes a pointer to the actual plug-in program. */
	private SimpleCellDesignerPlugin plugin;

	/** Constructor memorizes the plug-in data structure. */
	public SimpleCellDesignerPluginAction(SimpleCellDesignerPlugin plugin) {
		this.plugin = plugin;
	}

	/** Executes an action if the given commant occurs. */
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
