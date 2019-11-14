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
	  public void addValidationListenerTest() {
		  //TODO
	  }
	  
	  @Test
	  public void clearTest() {
		  ctx.clear();
		  assertTrue(ctx.getRootConstraint() == null);
		  assertTrue(ctx.getConstraintType() == null);
	  }
	  
	  @Test
	  public void didValidateTest() {
		  
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
		  
		  //test for enabling some categories 
		  ctx.enableCheckCategories(ccsTest, true);
		  CHECK_CATEGORY[] ctxCheckCategories = ctx.getCheckCategories();
		  assertTrue(ctxCheckCategories.length == 3); //enabled 1 category initially and 2 with ccsTestTrue
		  //convert to list to be able to test if categories are contained 
		  List<CHECK_CATEGORY> ctxCats = Arrays.asList(ctxCheckCategories);
		  assertTrue(ctxCats.contains(ccsTest[0]) == true);
		  assertTrue(ctxCats.contains(ccsTest[1]) == true);
		  assertTrue(ctxCats.contains(CHECK_CATEGORY.GENERAL_CONSISTENCY) == true); //was initially enabled
		  assertTrue(ctxCats.contains(CHECK_CATEGORY.SBO_CONSISTENCY) == false);
		  assertTrue(ctxCats.contains(CHECK_CATEGORY.IDENTIFIER_CONSISTENCY) == false);
		  assertTrue(ctxCats.contains(CHECK_CATEGORY.MATHML_CONSISTENCY) == false);
		  assertTrue(ctxCats.contains(CHECK_CATEGORY.SBO_CONSISTENCY) == false);
		  assertTrue(ctxCats.contains(CHECK_CATEGORY.UNITS_CONSISTENCY) == false);
		  
		  //test for disabling some categories 
		  ctx.enableCheckCategories(ccsTest, false);
		  ctxCheckCategories = ctx.getCheckCategories();
		  assertTrue(ctxCheckCategories.length == 1);
		  ctxCats = Arrays.asList(ctxCheckCategories);
		  assertTrue(ctxCats.contains(ccsTest[0]) == false);
		  assertTrue(ctxCats.contains(ccsTest[1]) == false);
		  assertTrue(ctxCats.contains(CHECK_CATEGORY.GENERAL_CONSISTENCY) == true); //should not be affected 
		  
		  //load every category for testing enabling/disabling all 
		  CHECK_CATEGORY[] ccsTestAll = new CHECK_CATEGORY[8];
		  ccsTestAll[0] = CHECK_CATEGORY.MODELING_PRACTICE;
		  ccsTestAll[1] = CHECK_CATEGORY.OVERDETERMINED_MODEL;
		  ccsTestAll[2] = CHECK_CATEGORY.GENERAL_CONSISTENCY;
		  ccsTestAll[3] = CHECK_CATEGORY.SBO_CONSISTENCY;
		  ccsTestAll[4] = CHECK_CATEGORY.IDENTIFIER_CONSISTENCY;
		  ccsTestAll[5] = CHECK_CATEGORY.MATHML_CONSISTENCY;
		  ccsTestAll[6] = CHECK_CATEGORY.SBO_CONSISTENCY;
		  ccsTestAll[7] = CHECK_CATEGORY.UNITS_CONSISTENCY;
		  
		  //test for disabling all categories 
		  ctx.enableCheckCategories(ccsTestAll, false);
		  ctxCheckCategories = ctx.getCheckCategories();
		  ctxCats = Arrays.asList(ctxCheckCategories);
		  assertTrue(ctxCats.isEmpty() == true);
		  
		  //test for enabling all categories
		  ctx.enableCheckCategories(ccsTestAll, true);
		  ctxCheckCategories = ctx.getCheckCategories();
		  ctxCats = Arrays.asList(ctxCheckCategories);
		  assertTrue(ctxCats.contains(CHECK_CATEGORY.MODELING_PRACTICE) == true); 
		  assertTrue(ctxCats.contains(CHECK_CATEGORY.OVERDETERMINED_MODEL) == true);
		  assertTrue(ctxCats.contains(CHECK_CATEGORY.GENERAL_CONSISTENCY) == true); 
		  assertTrue(ctxCats.contains(CHECK_CATEGORY.SBO_CONSISTENCY) == true);
		  assertTrue(ctxCats.contains(CHECK_CATEGORY.IDENTIFIER_CONSISTENCY) == true);
		  assertTrue(ctxCats.contains(CHECK_CATEGORY.MATHML_CONSISTENCY) == true);
		  assertTrue(ctxCats.contains(CHECK_CATEGORY.SBO_CONSISTENCY) == true);
		  assertTrue(ctxCats.contains(CHECK_CATEGORY.UNITS_CONSISTENCY) == true);
	  }
	  
	  @Test 
	  public void loadConstraintsTest() {
		  ctx.loadConstraints(SBMLDocument.class); 
		  //old rootConstraint should be reseted so shouldn't be the initial constGroup 
		  assertTrue(ctx.getRootConstraint().equals(constGroup) == false); 
		  assertEquals(ctx.getConstraintType(), SBMLDocument.class);
		  
		  //if there was no root constraint before it should have one after loading 
		  ctx.setRootConstraint(null, null);
		  ctx.loadConstraints(SBMLDocument.class);
		  assertTrue(ctx.getRootConstraint() != null);
		  assertEquals(ctx.getConstraintType(), SBMLDocument.class);
		  
		  //TODO - test also overloaded methods
	  }
	  
	  @Test
	  public void loadConstraintsForAttributeTest() {
		  //TODO
	  }

	  @Test
	  public void getCheckCategoriesTest() {  		  
		  CHECK_CATEGORY[] ctxCategories = ctx.getCheckCategories(); 
		  assertTrue(ctxCategories.length == 1); //initially 1 category is enabled
		  List<CHECK_CATEGORY> ctxCats = Arrays.asList(ctxCategories); 
		  
		  assertTrue(ctxCats.contains(CHECK_CATEGORY.GENERAL_CONSISTENCY) == true);
		  assertTrue(ctxCats.contains(CHECK_CATEGORY.IDENTIFIER_CONSISTENCY) == false);
		  assertTrue(ctxCats.contains(CHECK_CATEGORY.MATHML_CONSISTENCY) == false);
		  assertTrue(ctxCats.contains(CHECK_CATEGORY.MODELING_PRACTICE) == false);
		  assertTrue(ctxCats.contains(CHECK_CATEGORY.OVERDETERMINED_MODEL) == false);
		  assertTrue(ctxCats.contains(CHECK_CATEGORY.SBO_CONSISTENCY) == false);
		  assertTrue(ctxCats.contains(CHECK_CATEGORY.UNITS_CONSISTENCY) == false);
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
		  assertEquals(ctx.getVersion(), doc.getVersion());
	  }
	  
	  @Test
	  public void getValidateRecursively() {
		  assertTrue(ctx.getValidateRecursively() == true);
	  }
	  
	  @Test
	  public void isEnabledCategoryTest() {
		  assertTrue(ctx.isEnabledCategory(CHECK_CATEGORY.GENERAL_CONSISTENCY) == true);
		  assertTrue(ctx.isEnabledCategory(CHECK_CATEGORY.IDENTIFIER_CONSISTENCY) == false);
		  assertTrue(ctx.isEnabledCategory(CHECK_CATEGORY.MATHML_CONSISTENCY) == false);
		  assertTrue(ctx.isEnabledCategory(CHECK_CATEGORY.MODELING_PRACTICE) == false);
		  assertTrue(ctx.isEnabledCategory(CHECK_CATEGORY.OVERDETERMINED_MODEL) == false);
		  assertTrue(ctx.isEnabledCategory(CHECK_CATEGORY.SBO_CONSISTENCY) == false);
		  assertTrue(ctx.isEnabledCategory(CHECK_CATEGORY.UNITS_CONSISTENCY) == false);
	  }
	  
	  @Test
	  public void isLevelAndVersionLessThanTest() {
		  int level = doc.getLevel();
		  int version = doc.getVersion();
		  
		  //if level is smaller than the given newLevel, method should return true
		  int newLevel = level + 1;
		  assertTrue(ctx.isLevelAndVersionLessThan(newLevel, version) == true);
		  assertTrue(ctx.isLevelAndVersionLessThan(newLevel, version - 1) == true);
		  //level equal to newLevel and version smaller than the given newVersion should return true 
		  int newVersion = version + 1;
		  newLevel = level;
		  assertTrue(ctx.isLevelAndVersionLessThan(newLevel, newVersion) == true);
		  
		  //if both stay same result should be false
		  newVersion = version;
		  newLevel = level;
		  assertTrue(ctx.isLevelAndVersionLessThan(level, version) == false);
		  
		  //if level is bigger than newLevel then it is false no matter what newVersion is
		  newLevel = level - 1;
		  newVersion = version + 1;
		  assertTrue(ctx.isLevelAndVersionLessThan(newLevel, newVersion) == false);
		  newVersion = version - 1;
		  assertTrue(ctx.isLevelAndVersionLessThan(newLevel, newVersion) == false);
	  }
	  
	  @Test
	  public void isLevelAndVersionGreaterThanTest() {
		  int level = doc.getLevel();
		  int version = doc.getVersion();
		  
		  //if level is greater than the given newLevel, method should return true
		  int newLevel = level - 1;
		  assertTrue(ctx.isLevelAndVersionGreaterThan(newLevel, version) == true);
		  assertTrue(ctx.isLevelAndVersionGreaterThan(newLevel, version - 1) == true);
		  //level equal to newLevel and version greater than the given newVersion should return true 
		  int newVersion = version - 1;
		  newLevel = level;
		  assertTrue(ctx.isLevelAndVersionGreaterThan(newLevel, newVersion) == true);
		  
		  //if both stay same result should be false
		  newVersion = version;
		  newLevel = level;
		  assertTrue(ctx.isLevelAndVersionGreaterThan(level, version) == false);
		  
		  //if level is smaller than newLevel then it is false no matter what newVersion is
		  newLevel = level + 1;
		  newVersion = version + 1;
		  assertTrue(ctx.isLevelAndVersionGreaterThan(newLevel, newVersion) == false);
		  newVersion = version - 1;
		  assertTrue(ctx.isLevelAndVersionGreaterThan(newLevel, newVersion) == false);
	  }
	  
	  @Test
	  public void isLevelAndVersionEqualToTest() {
		  int level = doc.getLevel();
		  int version = doc.getVersion();
		  
		  assertTrue(ctx.isLevelAndVersionEqualTo(level, version) == true);
		  assertTrue(ctx.isLevelAndVersionEqualTo(level + 1, version) == false);
		  assertTrue(ctx.isLevelAndVersionEqualTo(level - 1, version) == false);
		  assertTrue(ctx.isLevelAndVersionEqualTo(level, version + 1) == false);
		  assertTrue(ctx.isLevelAndVersionEqualTo(level, version - 1) == false);
		  assertTrue(ctx.isLevelAndVersionEqualTo(level + 1, version + 1) == false);
		  assertTrue(ctx.isLevelAndVersionEqualTo(level - 1, version - 1) == false);
		  assertTrue(ctx.isLevelAndVersionEqualTo(level + 1, version - 1) == false);
		  assertTrue(ctx.isLevelAndVersionEqualTo(level - 1, version + 1) == false);
	  }
	  
	  @Test
	  public void isLevelAndVersionGreaterEqualThanTest() {
		  int level = doc.getLevel();
		  int version = doc.getVersion();
		  
		  //if level is greater than the given newLevel, method should return true
		  int newLevel = level - 1;
		  assertTrue(ctx.isLevelAndVersionGreaterEqualThan(newLevel, version) == true);
		  assertTrue(ctx.isLevelAndVersionGreaterEqualThan(newLevel, version - 1) == true);
		  //level equal to newLevel and version greater than the given newVersion should return true 
		  int newVersion = version - 1;
		  newLevel = level;
		  assertTrue(ctx.isLevelAndVersionGreaterEqualThan(newLevel, newVersion) == true);
		  
		  //if both stay same result should be true because greater or equal should be true
		  newVersion = version;
		  newLevel = level;
		  assertTrue(ctx.isLevelAndVersionGreaterEqualThan(level, version) == true);
		  
		  //if level is smaller than newLevel then it is false no matter what newVersion is
		  newLevel = level + 1;
		  newVersion = version + 1;
		  assertTrue(ctx.isLevelAndVersionGreaterEqualThan(newLevel, newVersion) == false);
		  newVersion = version - 1;
		  assertTrue(ctx.isLevelAndVersionGreaterEqualThan(newLevel, newVersion) == false);
	  }
	  
	  @Test
	  public void isLevelAndVersionLesserEqualThanTest() {
		  int level = doc.getLevel();
		  int version = doc.getVersion();
		  
		  //if level is smaller than the given newLevel, method should return true
		  int newLevel = level + 1;
		  assertTrue(ctx.isLevelAndVersionLesserEqualThan(newLevel, version) == true);
		  assertTrue(ctx.isLevelAndVersionLesserEqualThan(newLevel, version - 1) == true);
		  //level equal to newLevel and version smaller than the given newVersion should return true 
		  int newVersion = version + 1;
		  newLevel = level;
		  assertTrue(ctx.isLevelAndVersionLesserEqualThan(newLevel, newVersion) == true);
		  
		  //if both stay same result should be true
		  newVersion = version;
		  newLevel = level;
		  assertTrue(ctx.isLevelAndVersionLesserEqualThan(level, version) == true);
		  
		  //if level is bigger than newLevel then it is false no matter what newVersion is
		  newLevel = level - 1;
		  newVersion = version + 1;
		  assertTrue(ctx.isLevelAndVersionLesserEqualThan(newLevel, newVersion) == false);
		  newVersion = version - 1;
		  assertTrue(ctx.isLevelAndVersionLesserEqualThan(newLevel, newVersion) == false);
	  }
	  
	  @Test
	  public void removeValidationListenerTest() {
		  //TODO
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
	  
	  @Test
	  public void setValidateRecursivelyTest() {
		  ctx.setValidateRecursively(false);
		  assertTrue(ctx.getValidateRecursively() == false);
		  ctx.setValidateRecursively(true);
		  assertTrue(ctx.getValidateRecursively() == true);
	  }
	  
	  @Test
	  public void validateTest() {
		  //TODO
	  }
	  
	  @Test
	  public void willValidateTest() {
		  
	  }
}
