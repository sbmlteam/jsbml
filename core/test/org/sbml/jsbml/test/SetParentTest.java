package org.sbml.jsbml.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;

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

  @Test
  public void assignedListenerTest() {
    //TODO
  }

  @Test
  public void addChildEventTest() {
    //TODO
  }

  @Test
  public void removeChildEventTest() {
    //TODO
  }

}


