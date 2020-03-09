package org.sbml.jsbml.test;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.ext.arrays.ArraysConstants;
import org.sbml.jsbml.ext.arrays.ArraysSBasePlugin;
import org.sbml.jsbml.ext.arrays.Dimension;
import org.sbml.jsbml.ext.comp.CompConstants;
import org.sbml.jsbml.ext.comp.CompModelPlugin;
import org.sbml.jsbml.ext.comp.Port;
import org.sbml.jsbml.ext.comp.Submodel;
import org.sbml.jsbml.ext.distrib.DistribConstants;
import org.sbml.jsbml.ext.distrib.DistribSBasePlugin;
import org.sbml.jsbml.ext.distrib.Uncertainty;
import org.sbml.jsbml.ext.dyn.DynConstants;
import org.sbml.jsbml.ext.fbc.FBCConstants;
import org.sbml.jsbml.ext.fbc.FBCModelPlugin;
import org.sbml.jsbml.ext.fbc.GeneProduct;
import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.ext.layout.LayoutConstants;
import org.sbml.jsbml.ext.layout.LayoutModelPlugin;
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
  private FBCModelPlugin fbcModel;

  
  @Before
  public void setUp() {
    doc = new SBMLDocument(3, 1);
    m = doc.createModel("test_model");

    //some packages to be tested
    compModel = (CompModelPlugin) m.getPlugin(CompConstants.shortLabel);
    layoutModel = (LayoutModelPlugin) m.getPlugin(LayoutConstants.shortLabel);
    arraysModel = (ArraysSBasePlugin) m.getPlugin(ArraysConstants.shortLabel);
    distribModel = (DistribSBasePlugin) m.getPlugin(DistribConstants.shortLabel);
    fbcModel = (FBCModelPlugin) m.getPlugin(FBCConstants.shortLabel);

    pDisabler = new PackageDisabler(doc);
  }

  @Test
  public void disableUnusedTest() { 
    assertTrue(doc.isPackageEnabled(CompConstants.shortLabel) == true); 
    assertTrue(doc.isPackageEnabled(LayoutConstants.shortLabel) == true);
    assertTrue(doc.isPackageEnabled(ArraysConstants.shortLabel) == true);
    assertTrue(doc.isPackageEnabled(DistribConstants.shortLabel) == true); 
    assertTrue(doc.isPackageEnabled(FBCConstants.shortLabel) == true);

    //used compModel 
    assertTrue((compModel.getChildCount() == 0) == true);
    CompModelPlugin clonedCompModel = compModel.clone();
    Submodel sm1 = compModel.createSubmodel("submodel1");
    Submodel sm2 = compModel.createSubmodel("submodel2");
    Port port1 = compModel.createPort();
    sm1.addExtension(CompConstants.shortLabel, clonedCompModel);  
    assertTrue((sm1.getExtensionCount() > 0) == true);
    assertTrue((compModel.getChildCount() == 2) == true);
    
    //used layoutModel
    assertTrue((layoutModel.getChildCount() == 0) == true); 
    LayoutModelPlugin clonedLayoutModel = layoutModel.clone();
    Layout layout = layoutModel.createLayout("layout1");
    layout.addExtension(LayoutConstants.shortLabel, clonedLayoutModel);
    assertTrue((layout.getExtensionCount() > 0) == true);
    assertTrue((layoutModel.getChildCount() == 1) == true);
    
    //used arrayModel
    assertTrue((arraysModel.getChildCount() == 0) == true); 
    ArraysSBasePlugin clonedArraysModel = arraysModel.clone();
    Dimension dim = arraysModel.createDimension();
    dim.addExtension(ArraysConstants.shortLabel, clonedArraysModel);
    assertTrue((layout.getExtensionCount() > 0) == true);
    assertTrue((layoutModel.getChildCount() == 1) == true);
    
    //used distribModel
    assertTrue((distribModel.getChildCount() == 0) == true); 
    DistribSBasePlugin clonedDistribModel = (DistribSBasePlugin) distribModel.clone();
    Uncertainty uncertainty = distribModel.createUncertainty();
    uncertainty.addExtension(DistribConstants.shortLabel, clonedDistribModel);
    assertTrue((uncertainty.getExtensionCount() > 0) == true);
    assertTrue((distribModel.getChildCount() == 1) == true);
    
    //used fbcModel
    assertTrue((fbcModel.getChildCount() == 0) == true);
    FBCModelPlugin clonedFbcModel = fbcModel.clone();
    GeneProduct gene = fbcModel.createGeneProduct();
    gene.addExtension(FBCConstants.shortLabel, clonedFbcModel);  
    assertTrue((gene.getExtensionCount() > 0) == true);
    assertTrue((fbcModel.getChildCount() == 1) == true);
    
    
    pDisabler.disableUnused(); 
    
    assertTrue(doc.isPackageEnabled(CompConstants.shortLabel) == true);
    assertTrue(doc.isPackageEnabled(LayoutConstants.shortLabel) == true);
    assertTrue(doc.isPackageEnabled(ArraysConstants.shortLabel) == true);
    assertTrue(doc.isPackageEnabled(DistribConstants.shortLabel) == true); 
    assertTrue(doc.isPackageEnabled(FBCConstants.shortLabel) == true);
    
    
    //test unused packages 
    setUp();
    pDisabler.disableUnused();
    assertTrue(doc.isPackageEnabled(CompConstants.shortLabel) == false);
    assertTrue(doc.isPackageEnabled(LayoutConstants.shortLabel) == false);
    assertTrue(doc.isPackageEnabled(ArraysConstants.shortLabel) == false);
    assertTrue(doc.isPackageEnabled(DistribConstants.shortLabel) == false); 
    assertTrue(doc.isPackageEnabled(FBCConstants.shortLabel) == false);
  }
  

  @Test
  public void removePackageTest() {
    assertTrue(doc.isPackageEnabled(CompConstants.shortLabel) == true); 
    assertTrue(doc.isPackageEnabled(LayoutConstants.shortLabel) == true);
    assertTrue(doc.isPackageEnabled(ArraysConstants.shortLabel) == true);
    assertTrue(doc.isPackageEnabled(DistribConstants.shortLabel) == true); 
    assertTrue(doc.isPackageEnabled(FBCConstants.shortLabel) == true);

    pDisabler.removePackage(CompConstants.shortLabel);
    pDisabler.removePackage(LayoutConstants.shortLabel);
    pDisabler.removePackage(ArraysConstants.shortLabel);
    pDisabler.removePackage(DistribConstants.shortLabel);
    pDisabler.removePackage(DynConstants.shortLabel);
    pDisabler.removePackage(FBCConstants.shortLabel);

    assertTrue(doc.isPackageEnabled(CompConstants.shortLabel) == false); 
    assertTrue(doc.isPackageEnabled(LayoutConstants.shortLabel) == false);
    assertTrue(doc.isPackageEnabled(ArraysConstants.shortLabel) == false);
    assertTrue(doc.isPackageEnabled(DistribConstants.shortLabel) == false); 
    assertTrue(doc.isPackageEnabled(FBCConstants.shortLabel) == false);
  }
}
