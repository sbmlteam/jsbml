package org.sbml.jsbml.celldesigner;

import java.text.MessageFormat;
import javax.swing.UIManager;
import jp.sbi.celldesigner.plugin.*;
import org.apache.log4j.Logger;
import org.sbml.jsbml.*;
import org.sbml.jsbml.gui.JSBMLvisualizer;

/** A simple plugin for CellDesigner that displays the SBML data structure */
public class SimpleCellDesignerPlugin extends AbstractCellDesignerPlugin {

  public static final String ACTION = "Display full model tree";
  public static final String APPLICATION_NAME = "Simple Plugin";
  private static final transient Logger logger = Logger.getLogger(SimpleCellDesignerPlugin.class);

  private SimpleCellDesignerPluginAction action;

  /** Initialize the plugin (one-time) */
  public SimpleCellDesignerPlugin() {
    super();
    logger.info(MessageFormat.format("\n\nLoading {0}\n\n", APPLICATION_NAME));
    try {
      action = new SimpleCellDesignerPluginAction(this);
      addPluginMenu();
    } catch (Exception exc) {
      logger.info(MessageFormat.format("unable to initialize {0}\n", APPLICATION_NAME));
      exc.printStackTrace();
    }
  }

  /* (non-Javadoc)
   * @see jp.sbi.celldesigner.plugin.CellDesignerPlug#addPluginMenu()
   */
  @Override
  public void addPluginMenu() {
    PluginMenu menu = new PluginMenu(APPLICATION_NAME);
    PluginMenuItem menuItem = new PluginMenuItem(ACTION, action);
    menu.add(menuItem);
    addCellDesignerPluginMenu(menu);
  }

  /** This launches the actual plugin. */
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
      logger.warn(exc.getLocalizedMessage() != null ? exc.getLocalizedMessage() : exc.getMessage());
    }
  }

}
