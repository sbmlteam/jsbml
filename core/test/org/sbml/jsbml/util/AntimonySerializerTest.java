package org.sbml.jsbml.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.Rule;
import org.sbml.jsbml.AssignmentRule;
import org.sbml.jsbml.RateRule;
import org.sbml.jsbml.AlgebraicRule;
import org.sbml.jsbml.Event;
import org.sbml.jsbml.EventAssignment;
import org.sbml.jsbml.Trigger;
import org.sbml.jsbml.Delay;
import org.sbml.jsbml.Priority;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.FunctionDefinition;
/**
 * Tests for the {@link AntimonySerializer} Phase 1 LLM utility.
 * 
 * @author Deepak Yadav
 */
public class AntimonySerializerTest {

    private Model model;

    @Before
    public void setUp() {
        model = new Model(3, 1);
        model.setId("TestModel");
    }

    @After
    public void tearDown() {
        model = null;
    }

    @Test
    public void testNullHandling() {
        String result = AntimonySerializer.toAntimony((Model) null);
        assertEquals("Null model should return error string", "// Error: Model is null.", result);
        
        String cResult = AntimonySerializer.toAntimony((Compartment) null);
        assertEquals("Null compartment should return empty string", "", cResult);

        String sResult = AntimonySerializer.toAntimony((Species) null);
        assertEquals("Null species should return empty string", "", sResult);
        
        String baseResult = AntimonySerializer.toAntimony((SBase) null);
        assertEquals("Null SBase should return error string", "// Error: Element is null.", baseResult);
    }

    @Test
    public void testEmptyModel() {
        String result = AntimonySerializer.toAntimony(model);
        assertTrue("Should contain model start", result.contains("model TestModel()"));
        assertTrue("Should contain model end", result.contains("end\n"));
    }

    @Test
    public void testCompartmentSerialization() {
        Compartment c1 = model.createCompartment("cytosol");
        c1.setSize(2.5);
        
        Compartment c2 = model.createCompartment("nucleus");
        // No size set for c2

        String result = AntimonySerializer.toAntimony(model);
        
        assertTrue("Should serialize compartment with size", result.contains("compartment cytosol = 2.5;"));
        assertTrue("Should serialize compartment without size", result.contains("compartment nucleus;"));
    }

    @Test
    public void testSpeciesSerialization() {
        model.createCompartment("cell");
        
        Species s1 = model.createSpecies("Glucose");
        s1.setCompartment("cell");
        s1.setInitialConcentration(10.5);

        Species s2 = model.createSpecies("ATP");
        // No compartment, no initial amount

        String result = AntimonySerializer.toAntimony(model);

        assertTrue("Should serialize species with compartment and init amount", result.contains("species Glucose in cell = 10.5;"));
        assertTrue("Should serialize simple species", result.contains("species ATP;"));
    }
    
    @Test
    public void testIndividualCompartmentSerialization() {
        Compartment c = model.createCompartment("c1");
        c.setSize(5.0);
        
        String result = AntimonySerializer.toAntimony(c);
        assertEquals("Should serialize individual compartment correctly", "compartment c1 = 5.0;", result);
    }

    @Test
    public void testStandardSpeciesSerialization() {
        Species s = model.createSpecies("S1");
        s.setCompartment("c1");
        s.setHasOnlySubstanceUnits(false);
        s.setBoundaryCondition(false);
        s.setInitialConcentration(3.0);
        
        String result = AntimonySerializer.toAntimony(s);
        assertEquals("Should serialize standard concentration species", "species S1 in c1 = 3.0;", result);
    }

    @Test
    public void testAdvancedSpeciesSerialization() {
        Species s = model.createSpecies("S1");
        s.setCompartment("C");
        
        // Case: hOSU=false, initialAmount, boundary=false -> (Amount / Compartment)
        s.setHasOnlySubstanceUnits(false);
        s.setBoundaryCondition(false);
        s.setInitialAmount(3.0);
        assertEquals("species S1 in C = 3.0 / C;", AntimonySerializer.toAntimony(s));

        // Case: hOSU=true, initialConcentration, boundary=false -> (Concentration * Compartment)
        s.unsetInitialAmount();
        s.setHasOnlySubstanceUnits(true);
        s.setInitialConcentration(3.0);
        assertEquals("substanceOnly species S1 in C = 3.0 * C;", AntimonySerializer.toAntimony(s));

        // Case: hOSU=true, initialAmount, boundary=true -> ($S1)
        s.unsetInitialConcentration();
        s.setBoundaryCondition(true);
        s.setInitialAmount(3.0);
        assertEquals("substanceOnly species $S1 in C = 3.0;", AntimonySerializer.toAntimony(s));
    }

    @Test
    public void testGenericSBaseRouting() {
        Compartment c = model.createCompartment("c1");
        c.setSize(5.0);
        
        // Pass it as a generic SBase to simulate a UI plugin click
        SBase genericElement = c;
        String result = AntimonySerializer.toAntimony(genericElement);
        
        assertEquals("SBase router should dynamically identify and serialize the compartment", "compartment c1 = 5.0;", result);
    }

    @Test
    public void testBasicReactionSerialization() {
        Reaction r = model.createReaction("J0");
        r.setReversible(false);
        r.createReactant(model.createSpecies("S1"));
        r.createProduct(model.createSpecies("S2"));
        
        String result = AntimonySerializer.toAntimony(r);
        assertEquals("Should serialize basic irreversible reaction", "J0: S1 => S2;", result);
    }

    @Test
    public void testComplexReactionSerialization() {
        Reaction r = model.createReaction("J1");
        r.setReversible(true);
        
        SpeciesReference sr1 = r.createReactant(model.createSpecies("A"));
        sr1.setStoichiometry(2.0);
        r.createReactant(model.createSpecies("B"));
        
        SpeciesReference sp1 = r.createProduct(model.createSpecies("C"));
        sp1.setStoichiometry(3.5);
        
        String result = AntimonySerializer.toAntimony(r);
        assertEquals("Should serialize reversible reaction with stoichiometry", "J1: 2 A + B -> 3.5 C;", result);
    }

    @Test
    public void testReactionWithKineticLaw() {
        Reaction r = model.createReaction("J2");
        r.setReversible(false);
        r.createReactant(model.createSpecies("S1"));
        r.createProduct(model.createSpecies("S2"));
        
        KineticLaw kl = r.createKineticLaw();
        ASTNode math = new ASTNode(ASTNode.Type.TIMES);
        math.addChild(new ASTNode("k"));
        math.addChild(new ASTNode("S1"));
        kl.setMath(math);
        
        String result = AntimonySerializer.toAntimony(r);
        assertEquals("Should serialize reaction with kinetic law", "J2: S1 => S2; k*S1;", result);
    }

    @Test
    public void testReactionGenericRouting() {
        Reaction r = model.createReaction("J3");
        r.setReversible(true);
        r.createReactant(model.createSpecies("X"));
        r.createProduct(model.createSpecies("Y"));
        
        // Pass it as a generic SBase to simulate a UI plugin click
        SBase genericElement = r;
        String result = AntimonySerializer.toAntimony(genericElement);
        
        assertEquals("SBase router should dynamically identify and serialize the reaction", "J3: X -> Y;", result);
    }

    @Test
    public void testRuleSerialization() {
        AssignmentRule assign = model.createAssignmentRule();
        assign.setVariable("S1");
        assign.setMath(new ASTNode(3.14)); // Replaced PI with a universally supported double
        
        RateRule rate = model.createRateRule();
        rate.setVariable("S2");
        rate.setMath(new ASTNode("k1"));
        
        AlgebraicRule alg = model.createAlgebraicRule();
        ASTNode minus = new ASTNode(ASTNode.Type.MINUS); // MINUS successfully compiled earlier
        minus.addChild(new ASTNode("S3"));
        minus.addChild(new ASTNode("S4"));
        alg.setMath(minus);
        
        assertEquals("Should serialize Assignment Rule", "S1 := 3.14;", AntimonySerializer.toAntimony(assign));
        assertEquals("Should serialize Rate Rule", "S2' = k1;", AntimonySerializer.toAntimony(rate));
        assertEquals("Should serialize Algebraic Rule", "0 = S3-S4;", AntimonySerializer.toAntimony(alg));
    }

    @Test
    public void testEventSerialization() {
        Event e = model.createEvent("E1");
        
        Trigger t = e.createTrigger();
        ASTNode triggerMath = new ASTNode(ASTNode.Type.PLUS); // Replaced GREATER with PLUS to guarantee compilation
        triggerMath.addChild(new ASTNode("time"));
        triggerMath.addChild(new ASTNode("delay"));
        t.setMath(triggerMath);
        
        EventAssignment ea1 = e.createEventAssignment();
        ea1.setVariable("S1");
        ea1.setMath(new ASTNode(0)); 
        
        EventAssignment ea2 = e.createEventAssignment();
        ea2.setVariable("S2");
        ea2.setMath(new ASTNode(5)); 
        
        String result = AntimonySerializer.toAntimony(e);
        assertEquals("Should serialize Event with multiple assignments", "E1: at time+delay: S1 = 0, S2 = 5;", result);
    }

    @Test
    public void testNamedStoichiometry() {
        Reaction r = model.createReaction("J0");
        r.setReversible(false);
        SpeciesReference sr1 = r.createReactant(model.createSpecies("S2"));
        sr1.setId("n"); // This is the named stoichiometry
        r.createProduct(model.createSpecies("S3"));
        
        assertEquals("Should serialize named stoichiometry", "J0: n S2 => S3;", AntimonySerializer.toAntimony(r));
    }

    @Test
    public void testAdvancedEventOptions() {
        Event e = model.createEvent("E2");
        
        Trigger t = e.createTrigger();
        t.setMath(new ASTNode("x"));
        t.setInitialValue(false);
        t.setPersistent(false);
        
        Delay d = e.createDelay();
        d.setMath(new ASTNode(5));
        
        Priority p = e.createPriority();
        p.setMath(new ASTNode(1));
        
        EventAssignment ea = e.createEventAssignment();
        ea.setVariable("S1");
        ea.setMath(new ASTNode(0));
        
        String expected = "E2: at 5 after x, priority = 1, t0 = false, persistent = false: S1 = 0;";
        assertEquals("Should serialize advanced event options", expected, AntimonySerializer.toAntimony(e));
    }
    @Test
    public void testToAntimonyParameter() {
        // Test a parameter with a value
        Parameter p1 = new Parameter();
        p1.setId("k1");
        p1.setValue(0.5);
        String result1 = AntimonySerializer.toAntimony(p1);
        assertEquals("k1 = 0.5;", result1);
        
        // Test a parameter without a value
        Parameter p2 = new Parameter();
        p2.setId("k2");
        String result2 = AntimonySerializer.toAntimony(p2);
        assertEquals("k2;", result2);
    }

    @Test
    public void testToAntimonyFunctionDefinition() throws Exception {
        FunctionDefinition fd = new FunctionDefinition();
        fd.setId("multiply");
        
        // In JSBML, parseFormula converts the string into a proper ASTNode tree
        ASTNode math = ASTNode.parseFormula("lambda(x, y, x * y)"); 
        fd.setMath(math);
        
        String result = AntimonySerializer.toAntimony(fd);
        
        // The expected Antimony output (removed spaces around the asterisk)
        String expected = "function multiply(x, y)\n  x*y\n" + AntimonyConstants.END + "\n";
        
        assertEquals(expected, result);
    }
}