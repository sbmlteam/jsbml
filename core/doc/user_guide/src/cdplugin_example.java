package org.sbml.jsbml.cdplugin;

import javax.swing.*;
import jp.sbi.celldesigner.plugin.*;
import org.sbml.jsbml.*;
import org.sbml.jsbml.gui.*;

/** A very simple implementation of a plugin for CellDesigner. */
public class SimpleCellDesignerPlugin extends CellDesignerPlugin {

    public static final String ACTION = "Display full model tree";
    public static final String APPLICATION_NAME = "Simple Plugin";

    /** Creates a new CellDesigner plugin with an entry in the menu bar. */
    public SimpleCellDesignerPlugin() {
        super();
        try {
            System.out.printf("\n\nLoading %s\n\n", APPLICATION_NAME);
            SimpleCellDesignerPluginAction action = new SimpleCellDesignerPluginAction(this);
            PluginMenu menu = new PluginMenu(APPLICATION_NAME);
            PluginMenuItem menuItem = new PluginMenuItem(ACTION, action);
            menu.add(menuItem);
            addCellDesignerPluginMenu(menu);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    /** This method is to be called by our CellDesignerPluginAction. */
    public void startPlugin() {
        PluginSBMLReader reader = new PluginSBMLReader(getSelectedModel(), SBO
                                                       .getDefaultPossibleEnzymes());
        Model model = reader.getModel();
        SBMLDocument doc = new SBMLDocument(model.getLevel(), model
                                            .getVersion());
        doc.setModel(model);
        new JSBMLvisualizer(doc);
    }

    // Include also methods from superclass, not needed in this example.
    public void addPluginMenu() { }
    public void modelClosed(PluginSBase psb) { }
    public void modelOpened(PluginSBase psb) { }
    public void modelSelectChanged(PluginSBase psb) { }
    public void SBaseAdded(PluginSBase psb) { }
    public void SBaseChanged(PluginSBase psb) { }
    public void SBaseDeleted(PluginSBase psb) { }
}
