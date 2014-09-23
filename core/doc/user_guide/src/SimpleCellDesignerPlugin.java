import [...]

/** A simple plugin for CellDesigner that displays the SBML data structure as a
 * {@link #DefaultTreeModel}. When the underlying SBMLDocument is changed by CellDesigner,
 * the tree will refresh itself and all nodes will become unexpanded. */
public class SimpleCellDesignerPlugin extends AbstractCellDesignerPlugin {

  public static final String ACTION = "Display full model tree";
  public static final String APPLICATION_NAME = "Simple Plugin";
  protected DefaultTreeModel modelTree = null;

  /** Creates a new CellDesigner plug-in with an entry in the menu bar.  */
  public SimpleCellDesignerPlugin() {
    super();
    addPluginMenu();
  }

  public void addPluginMenu() {
    // Initializing CellDesigner's menu entries
    PluginMenu menu = new PluginMenu(APPLICATION_NAME);
    PluginMenuItem menuItem = new PluginMenuItem(
      ACTION, new SimpleCellDesignerPluginAction(this));
    menuItem.setToolTipText("Displays the data structure of the model.");
    menu.add(menuItem);
    addCellDesignerPluginMenu(menu);
  }

  public void modelSelectChanged(PluginSBase sbase) {
    super.modelSelectChanged(sbase);
    // After the model is changed, refreshes the Tree by resetting the Root node.
    modelTree.setRoot(getSBMLDocument());
  }

  public void SBaseAdded(PluginSBase sbase) {
    super.SBaseAdded(sbase);
    // After a PluginSBase addition, refreshes the Tree by resetting the Root node.
    modelTree.setRoot(getSBMLDocument());
  }

  public void SBaseChanged(PluginSBase sbase) {
    super.SBaseChanged(sbase);
    // After a PluginSBase modification, refreshes the Tree by resetting the Root node.
    modelTree.setRoot(getSBMLDocument());
  }

  public void SBaseDeleted(PluginSBase sbase) {
    super.SBaseDeleted(sbase);
    // After a PluginSBase deletion, refreshes the Tree by resetting the Root node.
    modelTree.setRoot(getSBMLDocument());
  }

  @Override
  public void modelClosed(PluginSBase sbase) {
    super.modelClosed(sbase);
    modelTree.setRoot(null); // If the CellDesigner model is closed, we nullify the Tree.
  }

  /** Initializes plugin and sets WindowClosed() events. */
  public void run() {
    modelTree = new DefaultTreeModel(getSBMLDocument());
    JSBMLvisualizer visualizer = new JSBMLvisualizer(modelTree);
  }
}