package org.sbml.jsbml.validator;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBase;

/**
 * Tests for {@link OverdeterminationValidator}.
 */
public class OverdeterminationValidatorTest {

  /**
   * In SBML Level 3 Version 2, the csymbol {@code rateOf} can target
   * a reaction identifier. The {@link OverdeterminationValidator}
   * must treat that identifier as a referenced variable in the MathML
   * expression.
   *
   * This test checks that when an {@link ASTNode} refers to a reaction
   * by its id, and {@code getVariable()} is not set, the validator still
   * resolves the identifier via the model and collects the reaction as
   * a variable.
   */
  @Test
  public void testGetVariablesResolvesReactionIdWhenVariableNotSet() throws Exception {
    // Create minimal L3V2 model with one reaction
    Model model = new Model(3, 2);
    model.setId("m");
    Reaction r1 = model.createReaction();
    r1.setId("R1");

    // Create an AST node that refers to the reaction by its id.
    // In some L3V2 rateOf constructs, getVariable() may not be set
    // on such nodes; the validator must then resolve the id via the model.
    ASTNode nameNode = new ASTNode("R1");

    OverdeterminationValidator validator = new OverdeterminationValidator(model);

    // Prepare list to receive variables
    List<SBase> vars = new ArrayList<SBase>();

    // Call the private getVariables(..) method via reflection
    Method m = OverdeterminationValidator.class.getDeclaredMethod(
        "getVariables",
        org.sbml.jsbml.ListOf.class,
        ASTNode.class,
        List.class,
        int.class);
    m.setAccessible(true);
    m.invoke(validator, null, nameNode, vars, model.getLevel());

    assertTrue("The reaction referenced by id must be collected as a variable",
               vars.contains(r1));
  }
}