package org.sbml.jsbml.celldesigner;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.tree.DefaultTreeModel;
import jp.sbi.celldesigner.plugin.PluginMenu;
import jp.sbi.celldesigner.plugin.PluginMenuItem;
import jp.sbi.celldesigner.plugin.PluginSBase;
import org.sbml.jsbml.gui.JSBMLvisualizer;


/**
 * A simple plugin for CellDesigner that displays the SBML data structure as a
 * {@link #DefaultTreeModel}. When the underlying SBMLDocument is changed by CellDesigner,
 * the tree will refresh itself and all nodes will become unexpanded.
 *
 * @author Andreas Dr&auml;ger
 * @version $Rev$
 * @since 1.0
 * @date 16.04.2014
 */
public class SimpleCellDesignerPlugin extends AbstractCellDesignerPlugin {

  public static final String ACTION = "Display full model tree";
  public static final String APPLICATION_NAME = "Simple Plugin";
  protected DefaultTreeModel modelTree = null;

  /**
   * Creates a new CellDesigner plug-in with an entry in the menu bar.
   */
  public SimpleCellDesignerPlugin() {
    super();
    addPluginMenu();
  }

  @Override
  public void addPluginMenu() {
    // Initializing CellDesigner's menu entries
    PluginMenu menu = new PluginMenu(APPLICATION_NAME);
    PluginMenuItem menuItem = new PluginMenuItem(
      ACTION, new SimpleCellDesignerPluginAction(this));
    menuItem.setToolTipText("Displays the data structure of the model.");
    menu.add(menuItem);
    addCellDesignerPluginMenu(menu);
  }

  /**
   * After the model is changed, refreshes the Tree by resetting the Root node.
   */
  @Override
  public void modelSelectChanged(PluginSBase sbase) {
    super.modelSelectChanged(sbase);
    modelTree.setRoot(getSBMLDocument());
  }

  /**
   * After a PluginSBase addition, refreshes the Tree by resetting the Root node.
   */
  @Override
  public void SBaseAdded(PluginSBase sbase) {
    super.SBaseAdded(sbase);
    modelTree.setRoot(getSBMLDocument());
  }

  /**
   * After a PluginSBase modification, refreshes the Tree by resetting the Root node.
   */
  @Override
  public void SBaseChanged(PluginSBase sbase) {
    super.SBaseChanged(sbase);
    modelTree.setRoot(getSBMLDocument());
  }

  /**
   * After a PluginSBase deletion, refreshes the Tree by resetting the Root node.
   */
  @Override
  public void SBaseDeleted(PluginSBase sbase) {
    super.SBaseDeleted(sbase);
    modelTree.setRoot(getSBMLDocument());
  }

  /**
   * If the CellDesigner model is closed, we nullify the Tree.
   */
  @Override
  public void modelClosed(PluginSBase sbase) {
    super.modelClosed(sbase);
    modelTree.setRoot(null);
  }

  /**
   * Initializes plugin and sets WindowClosed() events.
   */
  @Override
  public void run() {
    modelTree = new DefaultTreeModel(getSBMLDocument());
    JSBMLvisualizer visualizer = new JSBMLvisualizer(modelTree);
    visualizer.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosed(WindowEvent e) {
        setStarted(false);
        getReader().clearMap();
      }
    });
  }
}
