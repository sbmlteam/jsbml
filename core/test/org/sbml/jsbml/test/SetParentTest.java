package org.sbml.jsbml.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.beans.PropertyChangeEvent;

import javax.swing.tree.TreeNode;
import javax.xml.stream.XMLStreamException;

import org.junit.Before;
import org.junit.Test;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.ext.fbc.FBCConstants;
import org.sbml.jsbml.ext.fbc.FBCModelPlugin;
import org.sbml.jsbml.ext.fbc.GeneProduct;
import org.sbml.jsbml.util.TreeNodeChangeListener;
import org.sbml.jsbml.util.TreeNodeRemovedEvent;

/**
 * 
 * @author Onur &Ouml;zel		
 * @since 1.5
 */

//TODO this is the current situation before unifying 
//setParent, setParentSBML, setParentSBMLObject to a single method
//after unifying this testclass should be updated 
public class SetParentTest {

  private SBMLDocument doc;
  private Model model;


  @Before
  public void setUp() {
    doc = new SBMLDocument(3,1);
    model = new Model("test");
  }

  @Test
  public void setParentTest() {
    assertTrue(model.isSetParent() == false);
    model.setParent(doc);
    assertEquals(model.getParent(), doc); 
    
    model.removeFromParent();
    assertEquals(model.getParent(), null); 
    
    Model model2 = new Model("test2");
    assertTrue(model.isSetParent() == false);
    model2.setParent(doc);
    assertEquals(model2.getParent(), doc);
  }

  @Test
  public void registerChildTest() {
    doc.addTreeNodeChangeListener(new TreeNodeCustom());
    assertTrue(model.isSetParent() == false);
    
    doc.registerChild(model); //change listener correctly detects node added 
    assertEquals(model.getParent(), doc); //parent gets set correctly
    
    assertTrue((doc.getChildCount() == 0) == true); //but doc doesn't know that it has a child now
    assertTrue((model.getChildCount() == 0) == true);
  }

  //check if the listeners get also assigned to the newly created childnodes
  @Test
  public void assignedListenerTest() {
    doc.addTreeNodeChangeListener(new TreeNodeCustom());
    Model testModel = doc.createModel("test3");

    assertTrue((doc.getChildCount() == 1) == true);
    assertTrue((testModel.getChildCount() == 0) == true);
    assertEquals(doc.getTreeNodeChangeListenerCount(), 1);
    assertEquals(testModel.getTreeNodeChangeListenerCount(), 1);

    FBCModelPlugin fbcModel = (FBCModelPlugin) testModel.getPlugin(FBCConstants.shortLabel);
    FBCModelPlugin clonedFbcModel = fbcModel.clone();
    GeneProduct gene = fbcModel.createGeneProduct();
    gene.addExtension(FBCConstants.shortLabel, clonedFbcModel); 

    assertTrue((fbcModel.getChildCount() == 1) == true);
    assertTrue((clonedFbcModel.getChildCount() == 0) == true);
    assertTrue((fbcModel.getTreeNodeChangeListenerCount() == 1) == true); 
    assertTrue((clonedFbcModel.getTreeNodeChangeListenerCount() == 1) == true);
  }
  
  //TODO firePropertyChange and TreeNodeChangeLister seem to use different information 
  //test if the correct events are fired, in particular if adding/removing nodes
  //and manipulating attributes use separate events like they should
  @Test
  public void correctEventTest() throws XMLStreamException {
    TreeNodeCustom testListener = new TreeNodeCustom();
    doc.addTreeNodeChangeListener(testListener);

    //current count of fired events will be tested against new values 
    //after firing single events to guarantee that only one event is fired at a time
    int fireCount = testListener.getCounter();

    //only node added should be fired -> firePropertyChange doesn't choose the type of change at all
    doc.createModel("testListener2");
    assertTrue(testListener.getLastFired().equals("nodeAdded") == true);
    assertTrue((testListener.getCounter() == (fireCount + 1)) == true);

    //only node removed should be fired -> firePropertyChange doesn't choose the type of change at all
    doc.unsetModel();  
    assertTrue(testListener.getLastFired().equals("nodeRemoved") == true);
    assertTrue((testListener.getCounter() == (fireCount + 2)) == true);
    
    //only property change should be fired 
    //TODO firePropertyChange says "element added" but TreeNodeChangeListener gets "property change"
    doc.setName("newName");
    assertTrue(testListener.getLastFired().equals("propertyChange") == true);
    assertTrue((testListener.getCounter() == (fireCount + 3)) == true);
    
    //TODO here we have two fired events!!!
    //not only property change but also node added fired afterwards
    //therefore fireCount is not 4 like expected but 5...
    doc.setNotes("some notes");
    assertTrue(testListener.getLastFired().equals("propertyChange") == true); 
    assertTrue((testListener.getCounter() == (fireCount + 4)) == true); 
  }

  
  //modified TreeNodeChangeListener to provide specific testcases
  //it provides ability to check which event was fired the last time 
  //and also a counter to check if only one event gets fired at a time 
  private class TreeNodeCustom implements TreeNodeChangeListener{
    private String lastFired = "";
    private int counter = 0; 
    
    @Override
    public void propertyChange(PropertyChangeEvent arg0) {
      System.out.println("TreenodeListener: property change");
      lastFired = "propertyChange";
      counter += 1;
    }

    @Override
    public void nodeAdded(TreeNode node) {
      System.out.println("TreeNodeListener: node was added");
      lastFired = "nodeAdded";
      counter += 1;
    }

    @Override
    public void nodeRemoved(TreeNodeRemovedEvent event) {
      // TODO Auto-generated method stub
      System.out.println("TreeNodeListener: node was removed");
      lastFired = "nodeRemoved";
      counter += 1;
    }
    
    public String getLastFired() {
      return lastFired;
    }
    
    public int getCounter() {
      return counter; 
    }
    
  }
}




