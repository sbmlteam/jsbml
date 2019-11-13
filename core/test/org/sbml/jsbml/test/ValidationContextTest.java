package org.sbml.jsbml.test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.stream.XMLStreamException;

import org.junit.Before;
import org.junit.Test;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.test.sbml.TestReadFromFile5;
import org.sbml.jsbml.util.ValuePair;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.ConstraintGroup;

/**
 * 
 * @author Onur Özel
 *
 */
public class ValidationContextTest {
	
	/**
	 * 
	 */
	private SBMLDocument doc;
	
	/**
	 * 
	 */
	private ValidationContext ctx; 
	
	/**
	 * 
	 */
	private ConstraintGroup constGroup;
	

	
	 /**
	   * loads testfile with id BIOMD0000000228 
	   * @throws IOException
	   * @throws XMLStreamException
	   */
	  @Before public void setUp()  throws IOException, XMLStreamException{
		  InputStream fileStream = TestReadFromFile5.class.getResourceAsStream("/org/sbml/jsbml/xml/test/data/l2v4/BIOMD0000000228.xml");
		  doc = new SBMLReader().readSBMLFromStream(fileStream);
		  ctx = new ValidationContext(doc.getLevel(),doc.getVersion()); //ctx with lvl and version of testdocument 
		  
		  //set up check categories
		  ctx.enableCheckCategory(CHECK_CATEGORY.GENERAL_CONSISTENCY, true); //determines the loaded constraints
		  
		  //create some constraints 
		  constGroup = new ConstraintGroup<SBMLDocument>(); //type of object to check is SBML Document? 
  
		  //initial root constraint is a simple dummy constraint
		  ctx.setRootConstraint(constGroup, this.getClass()); //TODO - not sure if type is this class or not
	  }
	  
	  @Test
	  public void clearTest() {
		  ctx.clear();
		  assertTrue(ctx.getRootConstraint() == null);
		  assertTrue(ctx.getConstraintType() == null);
	  }
	  
	  @Test 
	  public void enableCheckCategoryTest() {
		  //removes category, initially there was only one category enabled
		  //so the array should be empty
		  ctx.enableCheckCategory(CHECK_CATEGORY.GENERAL_CONSISTENCY, false);
		  assertTrue(ctx.getCheckCategories().length == 0);
		  
		  //set up test array which should match the enabled categories array 
		  CHECK_CATEGORY[] cgTestTrue = new CHECK_CATEGORY[1];
		  cgTestTrue[0] = CHECK_CATEGORY.IDENTIFIER_CONSISTENCY;
		  assertTrue(cgTestTrue.length == 1);
		  
		  //add back a category to the currently empty array of enabled constraints
		  //and test it against the created array
		  ctx.enableCheckCategory(CHECK_CATEGORY.IDENTIFIER_CONSISTENCY, true);
		  assertTrue(ctx.getCheckCategories().length == 1);
		  assertEquals(ctx.getCheckCategories()[0], cgTestTrue[0]);
	  }
	  
	  @Test 
	  public void loadConstraintsTest() {
		  ctx.setRootConstraint(null, null);
		  ctx.loadConstraints(this.getClass()); //TODO - how to load some constraints for this class?  
	  }

	  @Test
	  public void getCheckCategoriesTest() {  		  
		  CHECK_CATEGORY[] ccs = ctx.getCheckCategories(); 
		  assertTrue(ccs.length == 1); //initially 1 category is enabled (see setUp())
		   
		  CHECK_CATEGORY[] ccsTestFalse = new CHECK_CATEGORY[2];
		  ccsTestFalse[0] = CHECK_CATEGORY.MATHML_CONSISTENCY;
		  ccsTestFalse[1] = CHECK_CATEGORY.IDENTIFIER_CONSISTENCY;
		  assertTrue(ccs[0].equals(ccsTestFalse[0]) == false);
		  
		  CHECK_CATEGORY[] ccsTestTrue = new CHECK_CATEGORY[1];
		  ccsTestTrue[0] = CHECK_CATEGORY.GENERAL_CONSISTENCY;
		  assertTrue(ccs[0].equals(ccsTestTrue[0]) == true);
	  }
	  
	  @Test 
	  public void getConstraintTypeTest() {
		  assertEquals(ctx.getConstraintType(), this.getClass());
	  }
	  
	  @Test 
	  public void getHashMapTest() {
		  //TODO
	  }
	  
	  @Test
	  public void getLevelTest() {
		  assertTrue(ctx.getLevel() == doc.getLevel());
	  }
	  
	  @Test
	  public void getLevelAndVersionTest() {
		  ValuePair<Integer, Integer> lv = ctx.getLevelAndVersion();
		  assertTrue(lv.getL() == doc.getLevel());
		  assertTrue(lv.getV() == doc.getVersion());
	  }
	  
	  @Test
	  public void setLevelAndVersionTest() {
		  int docLevel = doc.getLevel();
		  int docVersion = doc.getVersion();
		  
		  //if values don't differ it shouldn't clear root constraint
		  ctx.setLevelAndVersion(docLevel, docVersion);
		  assertTrue(ctx.getLevel() == docLevel);
		  assertTrue(ctx.getVersion() == docVersion);
		  assertEquals(ctx.getRootConstraint(), constGroup);
		  assertEquals(ctx.getConstraintType(), this.getClass());
		  
		  //if values differ from current values root constraint should be cleared
		  ctx.setLevelAndVersion(docLevel + 1, docVersion + 1);
		  assertTrue(ctx.getLevel() == docLevel + 1);
		  assertTrue(ctx.getVersion() == docVersion + 1);
		  assertTrue(ctx.getRootConstraint() == null);
		  assertTrue(ctx.getConstraintType() == null); 
	  }
	  
	  @Test
	  public void setRootConstraintTest() {
		  ctx.setRootConstraint(null, null);
		  assertTrue(ctx.getRootConstraint() == null);
		  
		  ctx.setRootConstraint(constGroup, this.getClass()); 
		  assertEquals(ctx.getRootConstraint(), constGroup);
	  }
	  
	  @Test 
	  public void getRootConstraintTest() {
		  assertEquals(ctx.getRootConstraint(), constGroup);
	  }
}
