package org.sbml.jsbml.cdplugin;

import java.awt.event.ActionEvent;

import javax.swing.JMenuItem;

import jp.sbi.celldesigner.plugin.PluginAction;

public class SimpleCellDesignerPluginAction extends PluginAction {

	private static final long serialVersionUID = 5031855383766373790L;

	private SimpleCellDesignerPlugin plugin;

	public SimpleCellDesignerPluginAction(SimpleCellDesignerPlugin plugin) {
		this.plugin = plugin;
	}

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
