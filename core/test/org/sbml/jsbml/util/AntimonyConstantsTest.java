package org.sbml.jsbml.util;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link AntimonyConstants} interface.
 * Ensures that core Antimony keywords and operators remain safely unchanged 
 * to prevent parser regressions.
 *
 * @author Deepak Yadav
 */

public class AntimonyConstantsTest {

    @Test
    public void testCoreConstants() {
        // Verify that the core structural constants haven't been accidentally altered
        assertEquals("model ", AntimonyConstants.MODEL);
        assertEquals("end\n", AntimonyConstants.END);
        assertEquals("species ", AntimonyConstants.SPECIES);
        assertEquals("compartment ", AntimonyConstants.COMPARTMENT);
    }
    
    @Test
    public void testOperatorConstants() {
        // Verify reaction and assignment operators
        assertEquals(" => ", AntimonyConstants.IRREVERSIBLE);
        assertEquals(" -> ", AntimonyConstants.REVERSIBLE);
        assertEquals(" := ", AntimonyConstants.ASSIGNMENT);
        assertEquals("' = ", AntimonyConstants.RATE);
    }
}