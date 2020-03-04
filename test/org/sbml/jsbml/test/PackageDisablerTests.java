package org.sbml.jsbml.test;

import static org.junit.Assert.assertTrue;

import java.io.BufferedOutputStream;

import javax.xml.stream.XMLStreamException;

import org.junit.Before;
import org.junit.Test;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBMLWriter;
import org.sbml.jsbml.ext.arrays.ArraysConstants;
import org.sbml.jsbml.ext.arrays.ArraysSBasePlugin;
import org.sbml.jsbml.ext.comp.CompConstants;
import org.sbml.jsbml.ext.comp.CompModelPlugin;
import org.sbml.jsbml.ext.comp.Port;
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
  private Model m;
  private PackageDisabler pDisabler;
  private CompModelPlugin compModel;
  private LayoutModelPlugin layoutModel;
  private ArraysSBasePlugin arraysModel;
  private DistribSBasePlugin distribModel;
  private DynSBasePlugin dynModel;
  private FBCModelPlugin fbcModel;
  private GroupsModelPlugin groupsModel;
  private MultiModelPlugin multiModel;
  private QualModelPlugin qualModel;
  private SpatialModelPlugin spatialModel;
  //  private RenderLayoutPlugin renderModel;

  @Before
  public void setUp() {
    doc = new SBMLDocument(3, 1);
    m = doc.createModel("test_model");

    //packages to be tested
    //TODO - how to add math? 
    compModel = (CompModelPlugin) m.getPlugin(CompConstants.shortLabel);
//    layoutModel = (LayoutModelPlugin) m.getPlugin(LayoutConstants.shortLabel);
//    arraysModel = (ArraysSBasePlugin) m.getPlugin(ArraysConstants.shortLabel);
//    distribModel = (DistribSBasePlugin) m.getPlugin(DistribConstants.shortLabel);
//    dynModel = (DynSBasePlugin) m.getPlugin(DynConstants.shortLabel);
//    fbcModel = (FBCModelPlugin) m.getPlugin(FBCConstants.shortLabel);
//    groupsModel = (GroupsModelPlugin) m.getPlugin(GroupsConstants.shortLabel);
//    multiModel = (MultiModelPlugin) m.getPlugin(MultiConstants.shortLabel);
//    qualModel = (QualModelPlugin) m.getPlugin(QualConstants.shortLabel);
//    spatialModel  = (SpatialModelPlugin) m.getPlugin(SpatialConstants.shortLabel);
    
    // renderModel = (RenderLayoutPlugin) m.getPlugin("render");

    pDisabler = new PackageDisabler(doc);
  }

  @Test
  public void disableUnusedTest() {
    
    assertTrue(doc.isPackageEnabled(CompConstants.shortLabel) == true); 
//    assertTrue(doc.isPackageEnabled(LayoutConstants.shortLabel) == true);
//    assertTrue(doc.isPackageEnabled(ArraysConstants.shortLabel) == true);
//    assertTrue(doc.isPackageEnabled(DistribConstants.shortLabel) == true); 
//    assertTrue(doc.isPackageEnabled(DynConstants.shortLabel) == true);
//    assertTrue(doc.isPackageEnabled(FBCConstants.shortLabel) == true);
//    assertTrue(doc.isPackageEnabled(GroupsConstants.shortLabel) == true);
//    assertTrue(doc.isPackageEnabled(MultiConstants.shortLabel) == true);
//    assertTrue(doc.isPackageEnabled(QualConstants.shortLabel) == true);
//    assertTrue(doc.isPackageEnabled(SpatialConstants.shortLabel) == true);

    //simulate used compModel 
    assertTrue((compModel.getChildCount() == 0) == true);
    compModel.createSubmodel("submodel1");
    compModel.createPort();
    assertTrue((compModel.getChildCount() == 2) == true);
    
    try {
      new SBMLWriter().write(doc, new BufferedOutputStream(System.out),
        "SBMLWriterTest", "");
    } catch (XMLStreamException e) {
      e.printStackTrace();
    } catch (SBMLException e) {
      e.printStackTrace();
    }
    
    pDisabler.disableUnused(); 

    assertTrue(doc.isPackageEnabled(CompConstants.shortLabel) == false); 
//    assertTrue(doc.isPackageEnabled(LayoutConstants.shortLabel) == false);
//    assertTrue(doc.isPackageEnabled(ArraysConstants.shortLabel) == false);
//    assertTrue(doc.isPackageEnabled(DistribConstants.shortLabel) == false); 
//    assertTrue(doc.isPackageEnabled(DynConstants.shortLabel) == false);
//    assertTrue(doc.isPackageEnabled(FBCConstants.shortLabel) == false);
//    assertTrue(doc.isPackageEnabled(GroupsConstants.shortLabel) == false);
//    assertTrue(doc.isPackageEnabled(MultiConstants.shortLabel) == false);
//    assertTrue(doc.isPackageEnabled(QualConstants.shortLabel) == false);
//    assertTrue(doc.isPackageEnabled(SpatialConstants.shortLabel) == false);

    //TODO - make packages used 
  }

//  @Test
//  public void removePackageTest() {
//    assertTrue(doc.isPackageEnabled(CompConstants.shortLabel) == true); 
//    assertTrue(doc.isPackageEnabled(LayoutConstants.shortLabel) == true);
//    assertTrue(doc.isPackageEnabled(ArraysConstants.shortLabel) == true);
//    assertTrue(doc.isPackageEnabled(DistribConstants.shortLabel) == true); 
//    assertTrue(doc.isPackageEnabled(DynConstants.shortLabel) == true);
//    assertTrue(doc.isPackageEnabled(FBCConstants.shortLabel) == true);
//    assertTrue(doc.isPackageEnabled(GroupsConstants.shortLabel) == true);
//    assertTrue(doc.isPackageEnabled(MultiConstants.shortLabel) == true);
//    assertTrue(doc.isPackageEnabled(QualConstants.shortLabel) == true);
//    assertTrue(doc.isPackageEnabled(SpatialConstants.shortLabel) == true);
//    //assertTrue(doc.isPackageEnabled("render") == true); //is not enabled 
//
//    pDisabler.removePackage(CompConstants.shortLabel);
//    pDisabler.removePackage(LayoutConstants.shortLabel);
//    pDisabler.removePackage(ArraysConstants.shortLabel);
//    pDisabler.removePackage(DistribConstants.shortLabel);
//    pDisabler.removePackage(DynConstants.shortLabel);
//    pDisabler.removePackage(FBCConstants.shortLabel);
//    pDisabler.removePackage(GroupsConstants.shortLabel);
//    pDisabler.removePackage(MultiConstants.shortLabel);
//    pDisabler.removePackage(QualConstants.shortLabel);
//    pDisabler.removePackage(SpatialConstants.shortLabel);
//    //pDisabler.removePackage("render");
//
//    assertTrue(doc.isPackageEnabled(CompConstants.shortLabel) == false); 
//    assertTrue(doc.isPackageEnabled(LayoutConstants.shortLabel) == false);
//    assertTrue(doc.isPackageEnabled(ArraysConstants.shortLabel) == false);
//    assertTrue(doc.isPackageEnabled(DistribConstants.shortLabel) == false); 
//    assertTrue(doc.isPackageEnabled(DynConstants.shortLabel) == false);
//    assertTrue(doc.isPackageEnabled(FBCConstants.shortLabel) == false);
//    assertTrue(doc.isPackageEnabled(GroupsConstants.shortLabel) == false);
//    assertTrue(doc.isPackageEnabled(MultiConstants.shortLabel) == false);
//    assertTrue(doc.isPackageEnabled(QualConstants.shortLabel) == false);
//    assertTrue(doc.isPackageEnabled(SpatialConstants.shortLabel) == false);
//    //assertTrue(doc.isPackageEnabled("render") == false);
//  }
}
