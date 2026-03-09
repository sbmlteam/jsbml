package org.sbml.jsbml.math.compiler;

import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Test;
import org.sbml.jsbml.math.ASTNode2;
// no import needed for ASTNode2Value since we are in the same package
// or, if you prefer, you could import:
// import org.sbml.jsbml.math.compiler.ASTNode2Value;

/**
 * Regression tests for lambda handling in {@link UnitsCompiler}.
 */
public class UnitsCompilerLambdaTest {

  /**
   * Issue #248: Deriving units for a lambda expression while its child list
   * is still empty must not cause an IndexOutOfBoundsException.
   */
  @Test
  public void testLambdaWithNoChildrenDoesNotThrow() throws Exception {
    UnitsCompiler compiler = new UnitsCompiler(3, 1); // arbitrary level/version

    ASTNode2Value<?> result = null;
    try {
      result = compiler.lambda(Collections.<ASTNode2>emptyList());
    } catch (IndexOutOfBoundsException ex) {
      fail("lambda() must not throw IndexOutOfBoundsException when values list is empty: " + ex);
    }

    assertNotNull("lambda() should return a non-null ASTNode2Value", result);
    assertTrue("lambda() with no children should return invalid units",
               result.getUnits().isInvalid());
  }
}