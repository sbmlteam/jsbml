package org.sbml.jsbml.test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

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
 * @since 1.0
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
	private ConstraintGroup<Object> constGroup;
	

	
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
		  constGroup = new ConstraintGroup<Object>(); //type of object to check is SBML Document? 
		  ctx.setRootConstraint(constGroup, SBMLDocument.class); 
		  
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
		  CHECK_CATEGORY[] ccsTestTrue = new CHECK_CATEGORY[1];
		  ccsTestTrue[0] = CHECK_CATEGORY.IDENTIFIER_CONSISTENCY;
		  assertTrue(ccsTestTrue.length == 1);
		  
		  //add back a category to the currently empty array of enabled constraints
		  //and test it against the created array
		  ctx.enableCheckCategory(CHECK_CATEGORY.IDENTIFIER_CONSISTENCY, true);
		  assertTrue(ctx.getCheckCategories().length == 1);
		  assertEquals(ctx.getCheckCategories()[0], ccsTestTrue[0]);
	  }
	  
	  @Test
	  public void enableCheckCategoriesTest() {
		  //set up categories array to use it as param
		  CHECK_CATEGORY[] ccsTest = new CHECK_CATEGORY[2];
		  ccsTest[0] = CHECK_CATEGORY.MODELING_PRACTICE;
		  ccsTest[1] = CHECK_CATEGORY.OVERDETERMINED_MODEL;
		  
		  //test for enabling categories 
		  ctx.enableCheckCategories(ccsTest, true);
		  CHECK_CATEGORY[] ctxCheckCategories = ctx.getCheckCategories();
		  assertTrue(ctxCheckCategories.length == 3); //enabled 1 category initially and 2 with ccsTestTrue
		  //convert to list to be able to test if categories are contained 
		  List<CHECK_CATEGORY> ctxCats = Arrays.asList(ctxCheckCategories);
		  assertTrue(ctxCats.contains(ccsTest[0]) == true);
		  assertTrue(ctxCats.contains(ccsTest[1]) == true);
		  assertTrue(ctxCats.contains(CHECK_CATEGORY.GENERAL_CONSISTENCY) == true); //should not be affected 
		  assertTrue(ctxCats.contains(CHECK_CATEGORY.SBO_CONSISTENCY) == false);
		  
		  //test for disabling categories
		  ctx.enableCheckCategories(ccsTest, false);
		  ctxCheckCategories = ctx.getCheckCategories();
		  assertTrue(ctxCheckCategories.length == 1);
		  ctxCats = Arrays.asList(ctxCheckCategories);
		  assertTrue(ctxCats.contains(ccsTest[0]) == false);
		  assertTrue(ctxCats.contains(ccsTest[1]) == false);
		  assertTrue(ctxCats.contains(CHECK_CATEGORY.GENERAL_CONSISTENCY) == true); //should not be affected 
	  }
	  
	  @Test 
	  public void loadConstraintsTest() {
		  ctx.loadConstraints(SBMLDocument.class); 
		  assertTrue(ctx.getRootConstraint().equals(constGroup) == false); //old rootConstraint should be reseted 
		  assertEquals(ctx.getConstraintType(), SBMLDocument.class);
		  
		  //if there was no root constraint before it should have one after loading 
		  ctx.setRootConstraint(null, null);
		  ctx.loadConstraints(SBMLDocument.class);
		  assertTrue(ctx.getRootConstraint() != null);
		  assertEquals(ctx.getConstraintType(), SBMLDocument.class);
	  }
	  
	  @Test
	  public void loadConstraintsForAttributeTest() {
		  //TODO
	  }

	  @Test
	  public void getCheckCategoriesTest() {  		  
		  CHECK_CATEGORY[] ccs = ctx.getCheckCategories(); 
		  assertTrue(ccs.length == 1); //initially 1 category is enabled (see setUp())
		   
		  CHECK_CATEGORY[] ccsTestFalse = new CHECK_CATEGORY[2];
		  ccsTestFalse[0] = CHECK_CATEGORY.MATHML_CONSISTENCY;
		  ccsTestFalse[1] = CHECK_CATEGORY.IDENTIFIER_CONSISTENCY;
		  assertTrue(ccs[0].equals(ccsTestFalse[0]) == false);
		  assertTrue(ccs[0].equals(ccsTestFalse[1]) == false);
		  
		  CHECK_CATEGORY[] ccsTestTrue = new CHECK_CATEGORY[1];
		  ccsTestTrue[0] = CHECK_CATEGORY.GENERAL_CONSISTENCY;
		  assertTrue(ccs[0].equals(ccsTestTrue[0]) == true);
	  }
	  
	  @Test 
	  public void getConstraintTypeTest() {
		  assertEquals(ctx.getConstraintType(), SBMLDocument.class);
	  }
	  
	  @Test 
	  public void getHashMapTest() {
		  //TODO how to simulate some tests? 
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
	  public void getRootConstraintTest() {
		  assertEquals(ctx.getRootConstraint(), constGroup);
	  }
	  
	  @Test
	  public void getVersionTest() {
		  assertEquals(ctx.getVersion(), doc.getLevel());
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
		  assertEquals(ctx.getConstraintType(), SBMLDocument.class);
		  
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
		  
		  ctx.setRootConstraint(constGroup, SBMLDocument.class); 
		  assertEquals(ctx.getRootConstraint(), constGroup);
	  }
	 
}
