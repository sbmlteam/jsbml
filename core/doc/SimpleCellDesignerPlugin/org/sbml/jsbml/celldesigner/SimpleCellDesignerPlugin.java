package org.sbml.jsbml.celldesigner;

import javax.swing.UIManager;

import jp.sbi.celldesigner.plugin.*;

import org.sbml.jsbml.*;
import org.sbml.jsbml.celldesigner.PluginSBMLReader;
import org.sbml.jsbml.gui.JSBMLvisualizer;

public class SimpleCellDesignerPlugin extends AbstractCellDesignerPlugin {

  public static final String ACTION = "Display full model tree";
  public static final String APPLICATION_NAME = "Simple Plugin";

  public SimpleCellDesignerPlugin() {
    super();
    System.out.printf("\n\nLoading %s\n\n", APPLICATION_NAME);
    try {
      SimpleCellDesignerPluginAction action =
          new SimpleCellDesignerPluginAction(this);
      PluginMenu menu = new PluginMenu(APPLICATION_NAME);
      PluginMenuItem menuItem = new PluginMenuItem(ACTION, action);
      menu.add(menuItem);
      addCellDesignerPluginMenu(menu);
    } catch (Exception exc) {
      System.err.printf("unable to initialize %s\n", APPLICATION_NAME);
      exc.printStackTrace();
    }
  }

  @Override
  public void addPluginMenu() {
  }

  public void startPlugin() {
    try {
      PluginSBMLReader reader = new PluginSBMLReader(getSelectedModel(),
        SBO.getDefaultPossibleEnzymes());
      Model model = reader.getModel();
      SBMLDocument doc = new SBMLDocument(model.getLevel(), model.getVersion());
      doc.setModel(model);
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      new JSBMLvisualizer(doc);
    } catch (Exception exc) {
    }
  }

}
