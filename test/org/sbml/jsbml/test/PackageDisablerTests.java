package org.sbml.jsbml.test;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.ext.arrays.ArraysSBasePlugin;
import org.sbml.jsbml.ext.comp.CompModelPlugin;
import org.sbml.jsbml.ext.distrib.DistribSBasePlugin;
import org.sbml.jsbml.ext.dyn.DynSBasePlugin;
import org.sbml.jsbml.ext.fbc.FBCModelPlugin;
import org.sbml.jsbml.ext.groups.GroupsModelPlugin;
import org.sbml.jsbml.ext.layout.LayoutModelPlugin;
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
    Model m = doc.createModel("test_model");
    
    //packages to be tested
    CompModelPlugin compModel = (CompModelPlugin) m.getPlugin("comp");
    LayoutModelPlugin layoutModel = (LayoutModelPlugin) m.getPlugin("layout");
    ArraysSBasePlugin arraysModel = (ArraysSBasePlugin) m.getPlugin("arrays");
    DistribSBasePlugin distribModel = (DistribSBasePlugin) m.getPlugin("distrib");
    DynSBasePlugin dynModel = (DynSBasePlugin) m.getPlugin("dyn");
    FBCModelPlugin fbcModel = (FBCModelPlugin) m.getPlugin("fbc");
    GroupsModelPlugin groupsModel = (GroupsModelPlugin) m.getPlugin("groups");
    
    pDisabler = new PackageDisabler(doc);
  }
  
  @Test
  public void disableUnusedTest() {
    
  }
  
  @Test
  public void removePackageTest() {
    assertTrue(doc.isPackageEnabled("comp")); //should be true
    assertTrue(doc.isPackageEnabled("layout"));
    assertTrue(doc.isPackageEnabled("arrays"));
    assertTrue(doc.isPackageEnabled("distrib")); 
    assertTrue(doc.isPackageEnabled("dyn"));
    assertTrue(doc.isPackageEnabled("fbc"));
    assertTrue(doc.isPackageEnabled("groups"));
    pDisabler.removePackage("comp");
    pDisabler.removePackage("layout");
    pDisabler.removePackage("arrays");
    pDisabler.removePackage("distrib");
    pDisabler.removePackage("dyn");
    pDisabler.removePackage("fbc");
    pDisabler.removePackage("groups");
  }
}
