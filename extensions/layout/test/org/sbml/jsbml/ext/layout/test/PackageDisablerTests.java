package org.sbml.jsbml.ext.layout.test;

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
import org.sbml.jsbml.ext.SBasePlugin;
import org.sbml.jsbml.ext.layout.LayoutConstants;
import org.sbml.jsbml.util.PackageDisabler;

/**
 * @author Onur &Ouml;zel
 * @since 1.5
 */
public class PackageDisablerTests {
  //TODO- make this tests abstract and add them to the packages 
  private SBMLDocument doc; 
  private Model m;
  private PackageDisabler pDisabler;
  private String name; 
  private String uri; 

  @Before
  public void setUp() {
    
    //Test model from TreeSearchTest 
    doc = new SBMLDocument(3, 1);  
    m = doc.createModel("test_model");
    name = LayoutConstants.shortLabel; //why not packageName? 
    uri = LayoutConstants.getNamespaceURI(doc.getLevel(), doc.getVersion());
    
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
    
    
  }
  
  @Test
  public void testAddingPackage() {
    //add package with name
    SBasePlugin packagePlugin1 = m.getPlugin(name);
    String pluginName = packagePlugin1.getPackageName(); //TODO - change name of get method to getShortLabel??
    assertTrue(name.equals(pluginName) == true);
    assertTrue(m.isPackageEnabled(name) == true);
    
    //add package with uri
    SBasePlugin packagePlugin2 = m.getPlugin(uri);
    String pluginUri = packagePlugin2.getURI();
    assertTrue(uri.equals(pluginUri) == true);
    assertTrue(m.isPackageEnabled(uri) == true); 
    
    assertTrue(packagePlugin1 == packagePlugin2);
  }
  
  @Test
  public void disableUnusedTest() {
    //unused package because no children: 
    
    pDisabler = new PackageDisabler(doc);
    m.getPlugin(name);
    pDisabler.disableUnused(); //TODO - check the warn and error messages
    assertTrue(m.isPackageEnabled(name) == false);
    
    //used package: 
    pDisabler = new PackageDisabler(doc);
    SBasePlugin packagePlugin = m.getPlugin(name);
    //TODO - how to add children -> textglyph  
    pDisabler.disableUnused();
    assertTrue(m.isPackageEnabled(name) == true);
  }
  
  @Test
  public void removePackageTest() { 
    pDisabler = new PackageDisabler(doc);
    m.getPlugin(name);
    pDisabler.removePackage(name);
    assertTrue(m.isPackageEnabled(name) == false);
  }
}
