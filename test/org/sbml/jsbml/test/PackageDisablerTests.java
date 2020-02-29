package org.sbml.jsbml.test;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.ext.arrays.ArraysConstants;
import org.sbml.jsbml.ext.arrays.ArraysSBasePlugin;
import org.sbml.jsbml.ext.comp.CompConstants;
import org.sbml.jsbml.ext.comp.CompModelPlugin;
import org.sbml.jsbml.ext.distrib.DistribConstants;
import org.sbml.jsbml.ext.distrib.DistribSBasePlugin;
import org.sbml.jsbml.ext.dyn.DynConstants;
import org.sbml.jsbml.ext.dyn.DynSBasePlugin;
import org.sbml.jsbml.ext.fbc.FBCConstants;
import org.sbml.jsbml.ext.fbc.FBCModelPlugin;
import org.sbml.jsbml.ext.groups.GroupsConstants;
import org.sbml.jsbml.ext.groups.GroupsModelPlugin;
import org.sbml.jsbml.ext.layout.LayoutConstants;
import org.sbml.jsbml.ext.layout.LayoutModelPlugin;
import org.sbml.jsbml.ext.multi.MultiConstants;
import org.sbml.jsbml.ext.multi.MultiModelPlugin;
import org.sbml.jsbml.ext.qual.QualConstants;
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
    assertTrue(doc.isPackageEnabled(CompConstants.shortLabel) == true); 
    assertTrue(doc.isPackageEnabled(LayoutConstants.shortLabel) == true);
    assertTrue(doc.isPackageEnabled(ArraysConstants.shortLabel) == true);
    assertTrue(doc.isPackageEnabled(DistribConstants.shortLabel) == true); 
    assertTrue(doc.isPackageEnabled(DynConstants.shortLabel) == true);
    assertTrue(doc.isPackageEnabled(FBCConstants.shortLabel) == true);
    assertTrue(doc.isPackageEnabled(GroupsConstants.shortLabel) == true);
    assertTrue(doc.isPackageEnabled(MultiConstants.shortLabel) == true);
    assertTrue(doc.isPackageEnabled(QualConstants.shortLabel) == true);
    assertTrue(doc.isPackageEnabled(SpatialConstants.shortLabel) == true);
    //assertTrue(doc.isPackageEnabled("render") == true); //is not enabled 
    
    pDisabler.removePackage(CompConstants.shortLabel);
    pDisabler.removePackage(LayoutConstants.shortLabel);
    pDisabler.removePackage(ArraysConstants.shortLabel);
    pDisabler.removePackage(DistribConstants.shortLabel);
    pDisabler.removePackage(DynConstants.shortLabel);
    pDisabler.removePackage(FBCConstants.shortLabel);
    pDisabler.removePackage(GroupsConstants.shortLabel);
    pDisabler.removePackage(MultiConstants.shortLabel);
    pDisabler.removePackage(QualConstants.shortLabel);
    pDisabler.removePackage(SpatialConstants.shortLabel);
    //pDisabler.removePackage("render");
    
    assertTrue(doc.isPackageEnabled(CompConstants.shortLabel) == false); 
    assertTrue(doc.isPackageEnabled(LayoutConstants.shortLabel) == false);
    assertTrue(doc.isPackageEnabled(ArraysConstants.shortLabel) == false);
    assertTrue(doc.isPackageEnabled(DistribConstants.shortLabel) == false); 
    assertTrue(doc.isPackageEnabled(DynConstants.shortLabel) == false);
    assertTrue(doc.isPackageEnabled(FBCConstants.shortLabel) == false);
    assertTrue(doc.isPackageEnabled(GroupsConstants.shortLabel) == false);
    assertTrue(doc.isPackageEnabled(MultiConstants.shortLabel) == false);
    assertTrue(doc.isPackageEnabled(QualConstants.shortLabel) == false);
    assertTrue(doc.isPackageEnabled(SpatialConstants.shortLabel) == false);
    //assertTrue(doc.isPackageEnabled("render") == false);
  }
}
