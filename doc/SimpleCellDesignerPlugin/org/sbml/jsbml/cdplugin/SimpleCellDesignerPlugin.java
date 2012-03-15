package org.sbml.jsbml.cdplugin;

import javax.swing.*;

import jp.sbi.celldesigner.plugin.*;

import org.sbml.jsbml.*;
import org.sbml.jsbml.test.gui.JTreeOfSBML;

public class SimpleCellDesignerPlugin extends CellDesignerPlugin {

	public static final String ACTION = "Display full model tree";
	public static final String APPLICATION_NAME = "Simple Plugin";

	public SimpleCellDesignerPlugin() {
		super();
		System.out.printf("\n\nLoading %s\n\n", APPLICATION_NAME);
		try {
			SimpleCellDesignerPluginAction action = new SimpleCellDesignerPluginAction(
					this);
			PluginMenu menu = new PluginMenu(APPLICATION_NAME);
			PluginMenuItem menuItem = new PluginMenuItem(ACTION, action);
			menu.add(menuItem);
			addCellDesignerPluginMenu(menu);
		} catch (Exception exc) {
			System.err.printf("unable to initialize %s\n", APPLICATION_NAME);
			exc.printStackTrace();
		}
	}

	public void addPluginMenu() {
	}

	public void modelClosed(PluginSBase psb) {
	}

	public void modelOpened(PluginSBase psb) {
	}

	public void modelSelectChanged(PluginSBase psb) {
	}

	public void SBaseAdded(PluginSBase psb) {
	}

	public void SBaseChanged(PluginSBase psb) {
	}

	public void SBaseDeleted(PluginSBase psb) {
	}

	public void startPlugin() {
		PluginSBMLReader reader = new PluginSBMLReader(getSelectedModel(), SBO
				.getDefaultPossibleEnzymes());
		Model model = reader.getModel();
		SBMLDocument doc = new SBMLDocument(model.getLevel(), model
				.getVersion());
		doc.setModel(model);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception exc) {
		}
		new JSBMLvisualizer(APPLICATION_NAME);
	}
}
