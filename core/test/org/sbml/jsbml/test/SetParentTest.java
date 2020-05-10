package org.sbml.jsbml.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.beans.PropertyChangeEvent;

import javax.swing.tree.TreeNode;

import org.junit.Before;
import org.junit.Test;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
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
    Model model = doc.createModel("testListener");
    
    System.out.println("Listener count of document: " + doc.getTreeNodeChangeListenerCount());
    System.out.println("Listener count of model: " + model.getTreeNodeChangeListenerCount());
  }

  @Test
  public void addChildEventTest() {
    //TODO
  }

  @Test
  public void removeChildEventTest() {
    //TODO
  }
  
  
  private class TreeNodeCustom implements TreeNodeChangeListener{
    
    @Override
    public void propertyChange(PropertyChangeEvent arg0) {
      // TODO Auto-generated method stub
      
    }

    @Override
    public void nodeAdded(TreeNode node) {
      System.out.println("TreeNodeListener: node was added");
      
    }

    @Override
    public void nodeRemoved(TreeNodeRemovedEvent event) {
      // TODO Auto-generated method stub
      System.out.println("TreeNodeListener: node was removed");
    }
    
  }
}




