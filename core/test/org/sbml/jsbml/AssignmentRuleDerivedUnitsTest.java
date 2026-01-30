package org.sbml.jsbml;

import static org.junit.Assert.*;

import java.io.File;
import java.net.URL;

import org.junit.Test;

public class AssignmentRuleDerivedUnitsTest {

  /**
   * Regression test for issue #268.
   * Calling getDerivedUnits() on an AssignmentRule in BIOMD0000000327.xml
   * should not throw a NullPointerException.
   */
  @Test
  public void testGetDerivedUnitsDoesNotThrowNPE() throws Exception {
    // Load the example model from test resources
    ClassLoader cl = getClass().getClassLoader();
    URL url = cl.getResource("org/sbml/jsbml/testdata/BIOMD0000000327.xml");
    assertNotNull("Test SBML file not found on classpath", url);

    SBMLDocument doc = new SBMLReader().readSBML(new File(url.toURI()));
    Model model = doc.getModel();
    assertNotNull(model);

    // Find the assignment rule for variable "japl"
    AssignmentRule targetRule = null;
    for (int i = 0; i < model.getRuleCount(); i++) {
    Rule r = model.getRule(i);
    if (r instanceof AssignmentRule) {
        AssignmentRule ar = (AssignmentRule) r;
        if ("japl".equals(ar.getVariable())) {
        targetRule = ar;
        break;
        }
    }
    }

    assertNotNull("AssignmentRule for variable 'japl' not found", targetRule);

    // The call to getDerivedUnits() must not throw a NullPointerException
    try {
      targetRule.getDerivedUnits();
    } catch (NullPointerException npe) {
      fail("getDerivedUnits() must not throw NullPointerException: " + npe);
    }
  }
}