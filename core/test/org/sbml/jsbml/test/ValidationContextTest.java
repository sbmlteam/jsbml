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
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.AnyConstraint;
import org.sbml.jsbml.validator.offline.constraints.CompartmentConstraints;
import org.sbml.jsbml.validator.offline.constraints.ConstraintGroup;
import org.sbml.jsbml.validator.offline.constraints.TriggerConstraints;
import org.sbml.jsbml.validator.offline.constraints.ValidationConstraint;

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
		  
		  //create some constraints 
		  constGroup = new ConstraintGroup<SBMLDocument>();
		   
	  }

	  @Test
	  public void getLevelTest() {
		  assertTrue(ctx.getLevel() == doc.getLevel());
	  }
	  
	  @Test 
	  public void setLevelTest() {
		  ctx.setLevel(-100); //TODO: get a unique level, should not be same es doc.getLevel()
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
		  ctx.setRootConstraint(constGroup, this.getClass());
		  assertTrue(ctx.getRootConstraint().equals(constGroup));
		  
		  ctx.setRootConstraint(null, this.getClass());
		  assertTrue(ctx.getRootConstraint() == null);
	  }
}
