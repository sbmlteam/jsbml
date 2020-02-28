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
import org.sbml.jsbml.ext.multi.MultiModelPlugin;
import org.sbml.jsbml.ext.qual.QualModelPlugin;
import org.sbml.jsbml.ext.spatial.SpatialConstants;
import org.sbml.jsbml.ext.spatial.SpatialModelPlugin;
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
    //TODO - how to add math? 
    CompModelPlugin compModel = (CompModelPlugin) m.getPlugin("comp");
    LayoutModelPlugin layoutModel = (LayoutModelPlugin) m.getPlugin("layout");
    ArraysSBasePlugin arraysModel = (ArraysSBasePlugin) m.getPlugin("arrays");
    DistribSBasePlugin distribModel = (DistribSBasePlugin) m.getPlugin("distrib");
    DynSBasePlugin dynModel = (DynSBasePlugin) m.getPlugin("dyn");
    FBCModelPlugin fbcModel = (FBCModelPlugin) m.getPlugin("fbc");
    GroupsModelPlugin groupsModel = (GroupsModelPlugin) m.getPlugin("groups");
    MultiModelPlugin multiModel = (MultiModelPlugin) m.getPlugin("multi");
    QualModelPlugin qualModel = (QualModelPlugin) m.getPlugin("qual");
    SpatialModelPlugin spatialModel  = (SpatialModelPlugin) m.getPlugin(SpatialConstants.shortLabel);
    //RenderLayoutPlugin renderModel = (RenderLayoutPlugin) m.getPlugin("render");
    
    pDisabler = new PackageDisabler(doc);
  }
  
  @Test
  public void disableUnusedTest() {
    
  }
  
  @Test
  public void removePackageTest() {
    assertTrue(doc.isPackageEnabled("comp") == true); 
    assertTrue(doc.isPackageEnabled("layout") == true);
    assertTrue(doc.isPackageEnabled("arrays") == true);
    assertTrue(doc.isPackageEnabled("distrib") == true); 
    assertTrue(doc.isPackageEnabled("dyn") == true);
    assertTrue(doc.isPackageEnabled("fbc") == true);
    assertTrue(doc.isPackageEnabled("groups") == true);
    assertTrue(doc.isPackageEnabled("multi") == true);
    assertTrue(doc.isPackageEnabled("qual") == true);
    assertTrue(doc.isPackageEnabled(SpatialConstants.shortLabel) == true);
    //assertTrue(doc.isPackageEnabled("render") == true); //is not enabled 
    
    pDisabler.removePackage("comp");
    pDisabler.removePackage("layout");
    pDisabler.removePackage("arrays");
    pDisabler.removePackage("distrib");
    pDisabler.removePackage("dyn");
    pDisabler.removePackage("fbc");
    pDisabler.removePackage("groups");
    pDisabler.removePackage("multi");
    pDisabler.removePackage("qual");
    pDisabler.removePackage(SpatialConstants.shortLabel);
    //pDisabler.removePackage("render");
    
    assertTrue(doc.isPackageEnabled("comp") == false); 
    assertTrue(doc.isPackageEnabled("layout") == false);
    assertTrue(doc.isPackageEnabled("arrays") == false);
    assertTrue(doc.isPackageEnabled("distrib") == false); 
    assertTrue(doc.isPackageEnabled("dyn") == false);
    assertTrue(doc.isPackageEnabled("fbc") == false);
    assertTrue(doc.isPackageEnabled("groups") == false);
    assertTrue(doc.isPackageEnabled("multi") == false);
    assertTrue(doc.isPackageEnabled("qual") == false);
    assertTrue(doc.isPackageEnabled(SpatialConstants.shortLabel) == false);
    //assertTrue(doc.isPackageEnabled("render") == false);
  }
}
