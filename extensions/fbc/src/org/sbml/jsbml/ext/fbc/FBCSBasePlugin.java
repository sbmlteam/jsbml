package org.sbml.jsbml.ext.fbc;
import java.util.ArrayList;
import java.util.List;
import javax.swing.tree.TreeNode;
import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.SBasePlugin;
import org.sbml.jsbml.ext.fbc.KeyValuePair;

/**
 * A FBCAnnotatioPlugin will contain the list Of Key Value Pairs
 * 
 * @author  Mahdi Sadeghi
 * @author  Andreas Drager
 * @author  Nikko Rodrigue
 */

public class FBCSBasePlugin extends AbstractFBCSBasePlugin{
  
  /**
   * 
   */
  private static final long serialVersionUID = 3137871409386974857L;
  /**
   * contains all the KeyValue Pairs
   */
  private List<KeyValuePair> ListofKeyValuePairS;
  /**
   * 
   */
  
  
  public FBCSBasePlugin() {
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
   * Returns the list of ListOfKeyValue Pairs. If they are no KeyValuePairs, an empty list is returned.
   * 
   * @return the list of KeyValuePairs.
   */
  public List<KeyValuePair> setListOfKeyValuePairs() {
    if (ListofKeyValuePairS == null) {
      ListofKeyValuePairS = new ArrayList<KeyValuePair>();
    }
    return ListofKeyValuePairS;
  }
  
  /**
   * Sets the given KeyValuePair list
   */
  public void setListOfKeyValuePairs(List<KeyValuePair> List_Key_Value) {
    unsetListOfKeyValues();
    this.ListofKeyValuePairS = List_Key_Value;

    if (isSetExtendedSBase()) {
      extendedSBase.registerChild((SBase) this.ListofKeyValuePairS);
    }
  }
  
  public boolean isSetListOfKeyValuePairs() {
    // cannot use the isEmpty() test here to avoid loosing the activeObject attribute
    // when calling the getListOfObjectives() when there are not yet any objective object added to the list.
    // This happen for example when reading a file.
    return ListofKeyValuePairS != null;
  }

  
  public boolean unsetListOfKeyValues() {
    if (isSetListOfKeyValuePairs()) {
      List<KeyValuePair> oldObjectives = ListofKeyValuePairS;
      ListofKeyValuePairS = null;
      ((AbstractSBase) oldObjectives).fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  
  /**
   * Creates a new instance by cloning the given pair.
   */
  public FBCSBasePlugin(FBCSBasePlugin fbcbaseplugin) {
    super(fbcbaseplugin);

    for (KeyValuePair pair : fbcbaseplugin.getListOfKeyValuePairs()) {
      getListOfKeyValuePairs().add((KeyValuePair) pair.clone());
    }
  }
  
  /**
   * Creates an instance from a list of {@link KeyValuePair}
   * objects.
   * @param cvTerms
   *            the list of {@link KeyValuePair}.
   */
  public FBCSBasePlugin(List<KeyValuePair> Pair) {
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