package org.sbml.jsbml.test;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.ModifierSpeciesReference;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.util.PackageDisabler;

/**
 * @author Onur &Ouml;zel
 * @since 1.5
 */
public class PackageDisablerTests {
  
  private SBMLDocument doc; 
  private PackageDisabler pDisabler;
  
  @Before
  public void setUp() {
    doc = new SBMLDocument(3, 2);
    
    //Test model from TreeSearchTest
    Model m = doc.createModel("test_model");

    Compartment c = m.createCompartment("default");
    c.setSpatialDimensions(3d);

    Species s1 = m.createSpecies("s1", "species1", c);
    Species s2 = m.createSpecies("s2", "species2", c);
    Species s3 = m.createSpecies("s3", "species3", c);
    Species s4 = m.createSpecies("s4", "species4", c);

    Reaction r1 = m.createReaction("r1");
    r1.setName("reaction1");
    r1.setCompartment(c);
    SpeciesReference sr1 = r1.createReactant("sr1", s1);
    sr1.setName("reactant1");
    sr1.setStoichiometry(1d);
    SpeciesReference sr2 = r1.createProduct("sr2", s2);
    sr2.setName("product1");
    sr2.setStoichiometry(1d);
    ModifierSpeciesReference msr1 = r1.createModifier("msr1", s3);
    msr1.setName("modifier");

    Reaction r2 = m.createReaction("r2");
    r2.setName("reaction2");
    r2.setCompartment(c);
    SpeciesReference sr3 = r2.createReactant("sr3", s1);
    sr3.setName("reactant2");
    sr3.setStoichiometry(2d);
    SpeciesReference sr4 = r2.createProduct("sr4", s4);
    sr4.setName("product2");
    sr4.setStoichiometry(1d);
    ModifierSpeciesReference msr2 = r2.createModifier("msr2", s3);
    msr2.setName("modifier");
    
    //TODO - how to activate some packages? 
    doc.addDeclaredNamespace("layout", "Layout");
    
    pDisabler = new PackageDisabler(doc);
  }
  
  @Test
  public void disableUnusedTest() {
    
  }
  
  @Test
  public void removePackageTest() {
    assertTrue(doc.isPackageEnabled("Layout")); //should be true
    pDisabler.removePackage("Layout");
  }
}
