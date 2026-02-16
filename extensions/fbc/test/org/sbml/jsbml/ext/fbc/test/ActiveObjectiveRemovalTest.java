package org.sbml.jsbml.ext.fbc.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.ext.fbc.FBCConstants;
import org.sbml.jsbml.ext.fbc.FBCModelPlugin;
import org.sbml.jsbml.ext.fbc.Objective;

/**
 * Regression test for issue #250:
 * Removing objectives from a model should unset the active objective
 * if the active one is removed.
 */
public class ActiveObjectiveRemovalTest {

  @Test
  public void removingAllObjectivesUnsetsActiveObjective() {
    Model m = new Model(2, 4);
    FBCModelPlugin fbcPlugin = (FBCModelPlugin) m.getPlugin(FBCConstants.shortLabel);

    Objective o1 = fbcPlugin.createObjective("obj1");
    fbcPlugin.setActiveObjective(o1);

    // Sanity check before removal
    assertTrue(fbcPlugin.isSetActiveObjective());
    assertEquals("obj1", fbcPlugin.getActiveObjective());
    assertEquals(1, fbcPlugin.getObjectiveCount());

    // Remove all objectives (as in the issue reproduction)
    fbcPlugin.getListOfObjectives().removeAll(fbcPlugin.getListOfObjectives());

    // After removal, the active objective should be unset
    assertFalse("Active objective should be unset after all objectives are removed.",
        fbcPlugin.isSetActiveObjective());
    assertEquals("", fbcPlugin.getActiveObjective());
    assertEquals(0, fbcPlugin.getObjectiveCount());
  }
}