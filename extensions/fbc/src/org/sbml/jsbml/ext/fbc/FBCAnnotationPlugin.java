package org.sbml.jsbml.ext.fbc;
import java.util.ArrayList;
import java.util.List;
import javax.swing.tree.TreeNode;
import org.sbml.jsbml.ext.SBasePlugin;
import org.sbml.jsbml.ext.fbc.KeyValuePair;

/**
 * A FBCAnnotatioPlugin will contain the list Of Key Value Pairs
 * 
 * @author  Mahdi Sadeghi
 * @author  Andreas Drager
 * @author  Nikko Rodrigue
 */

public class FBCAnnotationPlugin extends AbstractFBCSBasePlugin{
  
  /**
   * 
   */
  private static final long serialVersionUID = 3137871409386974857L;
  /**
   * contains all the KeyValue Pairs
   */
  private List<KeyValuePair> ListofKeyValuePairS;
  
  
  public FBCAnnotationPlugin() {
    super();
  }
  
  /**
   * Returns the list of ListOfKeyValue Pairs. If they are no KeyValuePairs, an empty list is returned.
   * 
   * @return the list of KeyValuePairs.
   */
  public List<KeyValuePair> getListOfKeyValuePairs() {
    if (ListofKeyValuePairS == null) {
      ListofKeyValuePairS = new ArrayList<KeyValuePair>();
    }
    return ListofKeyValuePairS;
  }
  
  
  /**
   * Creates a new instance by cloning the given pair.
   */
  public FBCAnnotationPlugin(FBCAnnotationPlugin fbcannotationplugin) {
    super(fbcannotationplugin);

    for (KeyValuePair pair : fbcannotationplugin.getListOfKeyValuePairs()) {
      getListOfKeyValuePairs().add((KeyValuePair) pair.clone());
    }
  }
  
  /**
   * Creates an instance from a list of {@link KeyValuePair}
   * objects.
   * @param cvTerms
   *            the list of {@link KeyValuePair}.
   */
  public FBCAnnotationPlugin(List<KeyValuePair> Pair) {
    this();
    ListofKeyValuePairS = Pair;
  }

 
  @Override
  public boolean readAttribute(String attributeName, String prefix,
    String value) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public TreeNode getChildAt(int childIndex) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int getChildCount() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public boolean getAllowsChildren() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public SBasePlugin clone() {
    // TODO Auto-generated method stub
    return null;
  }
  
}
  
  

