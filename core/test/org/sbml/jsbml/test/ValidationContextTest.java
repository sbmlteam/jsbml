package org.sbml.jsbml.test;

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
import org.sbml.jsbml.validator.offline.constraints.AbstractConstraint;
import org.sbml.jsbml.validator.offline.constraints.ConstraintGroup;

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
	 * 
	 */
	private SimpleConstraint simpleConst;
	

	
	
	
	//TODO create new Constraint for testpurposes? 
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
		  simpleConst = new SimpleConstraint<SBMLDocument>(0); //errorcode is set to 0
	  }

	  @Test
	  public void getLevelTest() {
		  assertTrue(ctx.getLevel() == doc.getLevel());
	  }
	  
	  @Test 
	  public void setLevelTest() {
		  ctx.setLevel(-100); //TODO: get a unique level, should not be same as doc.getLevel()
		  assertTrue(ctx.getLevel() == -100);
		  ctx.setLevel(doc.getLevel()+1);
		  assertTrue(ctx.getLevel() == doc.getLevel()+1);
		  ctx.setLevel(doc.getLevel()-1);
		  assertTrue(ctx.getLevel() == doc.getLevel()-1);
	  }
	  
	  @Test
	  public void getLevelAndVersionTest() {
		  ValuePair<Integer, Integer> lv = ctx.getLevelAndVersion();
		  assertTrue(lv.getL() == doc.getLevel());
		  assertTrue(lv.getV() == doc.getVersion());
	  }
	  
	  @Test
	  public void setLevelAndVersionTest() {
		  ctx.setLevelAndVersion(doc.getLevel()+1, doc.getVersion()+1);
		  assertTrue(ctx.getLevel() == doc.getLevel()+1);
		  assertTrue(ctx.getVersion() == doc.getVersion()+1);
	  }
	  
	  @Test
	  public void setRootConstraintTest() {
		  ctx.setRootConstraint(constGroup, ConstraintGroup.class); 
		  assertTrue(ctx.getRootConstraint().equals(constGroup));
		  
		  ctx.setRootConstraint(simpleConst, SimpleConstraint.class);
		  assertTrue(ctx.getRootConstraint().equals(simpleConst));
		  
		  ctx.setRootConstraint(null, null);
		  assertTrue(ctx.getRootConstraint() == null);
	  }
	  
	  
	  //TODO: How to get out the values of Array and compare to single element?
	  //why does it not match? 
	  @Test
	  public void getCheckCategoriesTest() {  
		  
		  CHECK_CATEGORY[] cg = ctx.getCheckCategories(); //loads enabled checkcategories
		  assertTrue(cg.length == 1);
		  
		  System.out.println(cg.toString());
		  
		  CHECK_CATEGORY[] cgTest = new CHECK_CATEGORY[1];
		  cgTest[0] = CHECK_CATEGORY.GENERAL_CONSISTENCY;
		  
		  System.out.println(cgTest.toString());
		  assertTrue(cg.equals(cgTest));
		  
	  }
	  
	  //TODO continue with constraintType 
}


//TODO Before Class? 
//A simple Constraint Type only for this tests
final class SimpleConstraint<T> extends AbstractConstraint<T> {

	public SimpleConstraint(int errorCode) {
		super(errorCode);
		// TODO Auto-generated constructor stub
	}


	@Override
	public boolean check(ValidationContext context, T object) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
