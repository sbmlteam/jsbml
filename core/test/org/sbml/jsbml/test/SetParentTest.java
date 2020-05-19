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
  }

  @Test
  public void registerChildTest() {
    //TODO 
  }

  //check if the listeners get also assigned to the newly created childnodes
  @Test
  public void assignedListenerTest() {
    doc.addTreeNodeChangeListener(new TreeNodeCustom());
    Model listenerTestModel = doc.createModel("testListener");

    assertTrue((doc.getChildCount() == 1) == true);
    assertTrue((listenerTestModel.getChildCount() == 0) == true);
    assertEquals(doc.getTreeNodeChangeListenerCount(), 1);
    assertEquals(listenerTestModel.getTreeNodeChangeListenerCount(), 1);

    FBCModelPlugin fbcModel = (FBCModelPlugin) listenerTestModel.getPlugin(FBCConstants.shortLabel);
    FBCModelPlugin clonedFbcModel = fbcModel.clone();
    GeneProduct gene = fbcModel.createGeneProduct();
    gene.addExtension(FBCConstants.shortLabel, clonedFbcModel); 

    assertTrue((fbcModel.getChildCount() == 1) == true);
    assertTrue((clonedFbcModel.getChildCount() == 0) == true);
    assertTrue((fbcModel.getTreeNodeChangeListenerCount() == 1) == true); 
    assertTrue((clonedFbcModel.getTreeNodeChangeListenerCount() == 1) == true);
  }
  
  //TODO what is defined as attribute change and what as child adding/ removing? 
  //test if the correct events are fired, in particular if adding/removing nodes
  //and manipulating attributes use different events like they should
  @Test
  public void correctEventTest() throws XMLStreamException {
    TreeNodeCustom testListener = new TreeNodeCustom();
    doc.addTreeNodeChangeListener(testListener);

    //current count of fired events will be tested against new values 
    //after firing single events to guarantee that only one event is fired at a time
    int fireCount = testListener.getCounter();

    //only node added should be fired
    doc.createModel("testListener2");
    assertTrue(testListener.getLastFired().equals("nodeAdded") == true);
    assertTrue((testListener.getCounter() == (fireCount + 1)) == true);

    //only node removed should be fired
    doc.unsetModel();  
    assertTrue(testListener.getLastFired().equals("nodeRemoved") == true);
    assertTrue((testListener.getCounter() == (fireCount + 2)) == true);
    
    //TODO: tests for property change case 
    doc.setName("newName");
    assertTrue(testListener.getLastFired().equals("propertyChange") == true);
    assertTrue((testListener.getCounter() == (fireCount + 3)) == true);
    
//    TODO here we have two fired events!!!
//    not only property change but also node added fired afterwards
    
//    doc.setNotes("some notes");
//    assertTrue(testListener.getLastFired().equals("propertyChange") == true); 
//    assertTrue((testListener.getCounter() == (fireCount + 5)) == true); 
  }

  @Test
  public void addChildEventTest() {
    //TODO
  }

  @Test
  public void removeChildEventTest() {
    //TODO
  }
  
  //modified TreeNodeChangeListener to provide specific testcases
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




