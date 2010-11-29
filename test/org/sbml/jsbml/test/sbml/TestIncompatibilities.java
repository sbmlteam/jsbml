package org.sbml.jsbml.test.sbml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.Delay;
import org.sbml.jsbml.Event;
import org.sbml.jsbml.EventAssignment;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.Trigger;
import org.sbml.jsbml.text.parser.ParseException;

public class TestIncompatibilities {

	  private Compartment C;
	  private Event E;

	  @Before public void setUp() throws Exception
	  {
	    C = new  Compartment(2,4);
	    E = new  Event(2,4);
	  }

	  @After public void tearDown() throws Exception
	  {
	    C = null;
	  }
	
	  @Test public void test_Compartment_get_type()
	  {
		  assertTrue(false);
//	    assertTrue( C.getTypeCode() == libsbml.SBML_COMPARTMENT ); // we need to implement the getTypeCode function in jsbml
	  }

	  @Test public void test_Compartment_createWithNS()
	  {
		  assertTrue(false); // We have no constructor with namespaces yet.
		  
		  /*
	    XMLNamespaces xmlns = new  XMLNamespaces();
	    xmlns.add( "http://www.sbml.org", "testsbml");
	    SBMLNamespaces sbmlns = new  SBMLNamespaces(2,1);
	    sbmlns.addNamespaces(xmlns); */
		  
	     Compartment c = new  Compartment(2,1); // new  Compartment(sbmlns); 
//	    assertTrue( c.getTypeCode() == libsbml.SBML_COMPARTMENT );
	    assertTrue( c.getMetaId().equals("") == true );
//	    assertTrue( c.getNotes() == null );
	    assertTrue( c.getAnnotation() == null );
	    assertTrue( c.getLevel() == 2 );
	    assertTrue( c.getVersion() == 1 );
	    assertTrue( c.getNamespaces() != null );
//	    assertTrue( c.getNamespaces().getLength() == 2 );
	    assertTrue( c.getName().equals("") == true );
	    assertTrue( c.getSpatialDimensions() == 3 );
	    assertTrue( c.getConstant() == true );
	    c = null;
	  }

	  @Test public void test_Compartment_unsetVolume()
	  {
	    C.setVolume(1.0); // Volume is not an SBML level 2 attribute, so we throw an exception to tell the user
	    assertTrue( C.getVolume() == 1.0 );
	    C.unsetVolume();
	    assertEquals( false, C.isSetVolume() );
	  }

	  @Test public void test_Compartment_getAnnotation()
	  {
		  assertTrue( C.getAnnotation() == null ); // If not annotation are defined, we create an empty one
	  }
	  
	  @Test public void test_Compartment_getUnits()
	  {
	    C.setUnits("");
	    assertEquals( false, C.isSetUnits() );
	    assertTrue(C.getUnits() == null); // we return ""
	  }

	  @Test public void test_L3_Parameter_hasRequiredAttributes()
	  {
		  assertTrue(false); // TODO : we need to implement the method sbase.hasRequiredAttributes() for all element
		  
	    Parameter p = new  Parameter(3,1);
//	    assertEquals( false, p.hasRequiredAttributes() );
	    p.setId( "id");
//	    assertEquals( false, p.hasRequiredAttributes() );
	    p.setConstant(false);
//	    assertEquals( true, p.hasRequiredAttributes() );
	    p = null;
	  }

	  @Test public void test_Event_addIdenticalObjectToAListOf()
	  {
	    @SuppressWarnings("unused")
		EventAssignment o1,o2,o3;
	    o1 = E.createEventAssignment(); // We cannot add several time the same element to a list of anything
	    o2 = E.createEventAssignment();
	    o3 = E.createEventAssignment();
	    o3.setVariable("test");
	    assertTrue( E.getNumEventAssignments() == 3 );
	    
	  }
	  
	  @Test public void test_Event_setDelayCopy()
	  {
	    ASTNode math1 = null;
		try {
			math1 = ASTNode.parseFormula("0");
		} catch (ParseException e) {
			assertTrue(false);
		}
	    Delay delay = new  Delay(2,4);
	    delay.setMath(math1);
	    E.setDelay(delay);
	    assertTrue(E.getDelay() != null);
	    assertEquals( true, E.isSetDelay() );
	    if (E.getDelay() == delay);
	    {
	    }
	    E.setDelay(E.getDelay());
	    assertTrue(E.getDelay() != delay); // We are not doing a copy of the object, so it is the same
	    E.setDelay(null);
	    assertEquals( false, E.isSetDelay() );
	    if (E.getDelay() != null);
	    {
	    }
	  }

	  @Test public void test_Event_setTriggerCopy()
	  {
		  ASTNode math1 = null;
		  try {
			  math1 = ASTNode.parseFormula("0");
		  } catch (ParseException e) {
			  assertTrue(false);
		  }
		  Trigger trigger = new  Trigger(2,4);
		  trigger.setMath(math1);
	    E.setTrigger(trigger);
	    assertTrue(E.getTrigger() != null);
	    assertEquals( true, E.isSetTrigger() );
	    if (E.getTrigger() == trigger);
	    {
	    }
	    E.setTrigger(E.getTrigger());
	    assertTrue(E.getTrigger() != trigger); // We are not doing a copy of the object, so it is the same 
	    E.setTrigger(null);
	    assertEquals( false, E.isSetTrigger() );
	    if (E.getTrigger() != null);
	    {
	    }
	  }

	  @Test public void test_FormulaOutput()
	  {
		  ASTNode math = null;
		  try {
			  math = ASTNode.parseFormula("k1 * S1");
		  } catch (ParseException e) {
			  assertTrue(false);
		  }

		  try {
			assertTrue(math.toFormula().equals("k1*S1"));
			assertTrue(math.toFormula().equals("k1 * S1")); // We are not putting the same space in the formula

		} catch (SBMLException e) {
			assertTrue(false);		} 
		  
	  }
	  
	  @Test public void testKindAPI() {

		  assertTrue(false);
		  // assertTrue(ud.getUnit(0).getKind() == Unit.Kind.LITRE); 
		  // TODO : API changes to document

	  }
	  
}
