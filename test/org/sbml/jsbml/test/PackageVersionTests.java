/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2014 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 5. The Babraham Institute, Cambridge, UK
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */

package org.sbml.jsbml.test;

import javax.xml.stream.XMLStreamException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.Event;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLWriter;
import org.sbml.jsbml.ext.arrays.ArraysConstants;
import org.sbml.jsbml.ext.arrays.ArraysSBasePlugin;
import org.sbml.jsbml.ext.comp.CompConstants;
import org.sbml.jsbml.ext.comp.CompModelPlugin;
import org.sbml.jsbml.ext.comp.CompSBMLDocumentPlugin;
import org.sbml.jsbml.ext.comp.Submodel;
import org.sbml.jsbml.ext.distrib.DistribConstants;
import org.sbml.jsbml.ext.distrib.util.DistribModelBuilder;
import org.sbml.jsbml.ext.dyn.CBO;
import org.sbml.jsbml.ext.dyn.DynCompartmentPlugin;
import org.sbml.jsbml.ext.dyn.DynConstants;
import org.sbml.jsbml.ext.dyn.DynEventPlugin;
import org.sbml.jsbml.ext.dyn.SpatialKind;
import org.sbml.jsbml.ext.fbc.And;
import org.sbml.jsbml.ext.fbc.FBCConstants;
import org.sbml.jsbml.ext.fbc.FBCModelPlugin;
import org.sbml.jsbml.ext.fbc.FBCReactionPlugin;
import org.sbml.jsbml.ext.fbc.FluxBound;
import org.sbml.jsbml.ext.fbc.GeneProduct;
import org.sbml.jsbml.ext.fbc.GeneProductAssociation;
import org.sbml.jsbml.ext.fbc.GeneProductRef;
import org.sbml.jsbml.ext.fbc.Objective;
import org.sbml.jsbml.ext.fbc.Or;
import org.sbml.jsbml.ext.groups.Group;
import org.sbml.jsbml.ext.groups.GroupsConstants;
import org.sbml.jsbml.ext.groups.GroupsModelPlugin;
import org.sbml.jsbml.ext.qual.Input;
import org.sbml.jsbml.ext.qual.Output;
import org.sbml.jsbml.ext.qual.QualConstants;
import org.sbml.jsbml.ext.qual.QualModelPlugin;
import org.sbml.jsbml.ext.qual.Transition;
import org.sbml.jsbml.ext.req.ReqConstants;
import org.sbml.jsbml.xml.parsers.PackageUtil;
import org.sbml.jsbml.xml.stax.SBMLReader;

/**
 * 
 * @author Nicolas Rodriguez
 * @version $Rev$
 * @since 1.1
 * @date 20.04.2015
 */
@SuppressWarnings("deprecation")
public class PackageVersionTests {

  /**
   * 
   */
  private SBMLDocument doc;
  /**
   * 
   */
  private Model m;

  /**
   * 
   */
  @BeforeClass public static void initialSetUp() {}
  /**
   * 
   */
  @Before public void setUp() {
    doc = new SBMLDocument(3, 1);

    doc.enablePackage(ArraysConstants.namespaceURI_L3V1V1);
    doc.enablePackage(FBCConstants.namespaceURI_L3V1V1);
    doc.enablePackage(DistribConstants.namespaceURI_L3V1V1);
    doc.enablePackage(QualConstants.namespaceURI_L3V1V1);
    doc.enablePackage(CompConstants.namespaceURI_L3V1V1);
    doc.enablePackage(ReqConstants.namespaceURI_L3V1V1);

    m = doc.createModel("test");

    Compartment cell = m.createCompartment("cell");
    m.createReaction("R1");
    m.createSpecies("S1");
    m.createSpecies("S2");

    Event event = m.createEvent("E1");

    FBCModelPlugin fbcModel = (FBCModelPlugin) m.getPlugin("fbc");

    FluxBound fb1 = fbcModel.createFluxBound("fb1");
    fbcModel.createFluxBound("fb2");

    fb1.setPackageVersion(1);

    fbcModel.createObjective("fbc_O1");


    fb1.setReaction("R1");

    FunctionDefinition f = m.createFunctionDefinition("f");
    DistribModelBuilder.createDistribution(f, "NormalDistribution", new String[] { "mean", "stddev" }, new String[] {"avg", "sd"});

    CompSBMLDocumentPlugin compDoc = (CompSBMLDocumentPlugin) doc.getPlugin("comp");
    compDoc.createExternalModelDefinition("C_EMD1");
    compDoc.createModelDefinition("C_MD1");

    CompModelPlugin compModel = (CompModelPlugin) m.getPlugin("comp");
    Submodel subModel = compModel.createSubmodel("C_SB1");
    compModel.createPort("C_P1");
    compModel.createReplacedBy();
    compModel.createReplacedElement();

    ArraysSBasePlugin arraySubModel = (ArraysSBasePlugin) subModel.getPlugin("arrays");
    arraySubModel.createDimension("A_D1");
    arraySubModel.createIndex();

    DynCompartmentPlugin dynCell = (DynCompartmentPlugin) cell.getPlugin(DynConstants.namespaceURI_L3V1V1);
    dynCell.createSpatialComponent("Dy_SP1").setSpatialIndex(SpatialKind.cartesianX);
    dynCell.createSpatialComponent("Dy_SP2");
    dynCell.setCBOTerm(CBO.getTerm("CellDeath"));

    DynEventPlugin dynEvent = (DynEventPlugin) event.getPlugin("dyn");
    dynEvent.createDynElement("D_cell").setIdRef("cell");

    GroupsModelPlugin groupModel = (GroupsModelPlugin) m.getPlugin(GroupsConstants.namespaceURI_L3V1V1);
    groupModel.createGroup("G_G1");
    Group g2 = groupModel.createGroup("G_G2", new String[]{"G_M1", "G_M2", "G_M3"});
    g2.createMemberWithIdRef("G_M4", "S1");
    g2.getMember(0).setIdRef("S2");
    g2.getMember("G_M2").setIdRef(cell);
    g2.createMemberConstraint("G_MC1").setDistinctAttribute("test");

    QualModelPlugin qualModel = (QualModelPlugin) m.getPlugin(QualConstants.shortLabel);
    qualModel.createQualitativeSpecies("Q_QS1");
    qualModel.createQualitativeSpecies("Q_QS2");
    Transition tr1 = qualModel.createTransition("Q_T1");
    tr1.createInput("Q_I1");
    tr1.createOutput("Q_O1");
    qualModel.createTransition("Q_T2", new Input("Q_I2"), new Output("Q_O2"));

    // check package version and namespaces
    System.out.println("Checking packages:");
    PackageUtil.checkPackages(doc, false, false);
  }


  /**
   * Checks that package version and namespace are set properly for FBC version 1.
   */
  @Test public void testFbcPackageVersion() {


    FBCModelPlugin fbcModel = ((FBCModelPlugin) m.getPlugin("fbc"));
    ListOf<FluxBound> fluxBounds = fbcModel.getListOfFluxBounds();

    Assert.assertTrue(fbcModel.getPackageVersion() == 1);
    Assert.assertTrue(fbcModel.getPackageName().equals(FBCConstants.shortLabel));
    Assert.assertTrue(fbcModel.getElementNamespace().equals(FBCConstants.namespaceURI_L3V1V1));


    Assert.assertTrue(fluxBounds.getPackageVersion() == 1);
    Assert.assertTrue(fluxBounds.getPackageName().equals(FBCConstants.shortLabel));
    Assert.assertTrue(fluxBounds.getNamespace().equals(FBCConstants.namespaceURI_L3V1V1));

    for (FluxBound fluxBound : fluxBounds) {
      Assert.assertTrue(fluxBound.getPackageVersion() == 1);
      Assert.assertTrue(fluxBound.getPackageName().equals(FBCConstants.shortLabel));
      Assert.assertTrue(fluxBound.getNamespace().equals(FBCConstants.namespaceURI_L3V1V1));
    }

    ListOf<Objective> objectives = fbcModel.getListOfObjectives();

    Assert.assertTrue(objectives.getPackageVersion() == 1);
    Assert.assertTrue(objectives.getPackageName().equals(FBCConstants.shortLabel));
    Assert.assertTrue(objectives.getNamespace().equals(FBCConstants.namespaceURI_L3V1V1));

    for (Objective objective : objectives) {
      Assert.assertTrue(objective.getPackageVersion() == 1);
      Assert.assertTrue(objective.getPackageName().equals(FBCConstants.shortLabel));
      Assert.assertTrue(objective.getNamespace().equals(FBCConstants.namespaceURI_L3V1V1));
    }
  }

  
  /**
   * Checks that package version and namespace are set properly for FBC version 2.
   */
  @Test public void testFbcPackageVersion2() {

    doc = new SBMLDocument(3, 1);
    m = doc.createModel("test");
    
    // using the namespace to create the plugin so that the test is valid even when there is a version 3 or more of fbc
    FBCModelPlugin fbcModel = ((FBCModelPlugin) m.getPlugin(FBCConstants.namespaceURI_L3V1V2)); 
    
    Assert.assertTrue(fbcModel.getPackageVersion() == 2);
    Assert.assertTrue(fbcModel.getPackageName().equals(FBCConstants.shortLabel));
    Assert.assertTrue(fbcModel.getElementNamespace().equals(FBCConstants.namespaceURI_L3V1V2));

    fbcModel.createObjective("fbc_O1");
    
    ListOf<Objective> objectives = fbcModel.getListOfObjectives();

    Assert.assertTrue(objectives.getPackageVersion() == 2);
    Assert.assertTrue(objectives.getPackageName().equals(FBCConstants.shortLabel));
    Assert.assertTrue(objectives.getNamespace().equals(FBCConstants.namespaceURI_L3V1V2));

    for (Objective objective : objectives) {
      Assert.assertTrue(objective.getPackageVersion() == 2);
      Assert.assertTrue(objective.getPackageName().equals(FBCConstants.shortLabel));
      Assert.assertTrue(objective.getNamespace().equals(FBCConstants.namespaceURI_L3V1V2));
    }
    
    fbcModel.createGeneProduct("fbc_GP1");
    fbcModel.createGeneProduct("fbc_GP2");
    fbcModel.createGeneProduct("fbc_GP3");
    
    ListOf<GeneProduct> geneProducts = fbcModel.getListOfGeneProducts();
    
    Assert.assertTrue(geneProducts.getPackageVersion() == 2);
    Assert.assertTrue(geneProducts.getPackageName().equals(FBCConstants.shortLabel));
    Assert.assertTrue(geneProducts.getNamespace().equals(FBCConstants.namespaceURI_L3V1V2));

    for (GeneProduct geneProduct : geneProducts) {
      Assert.assertTrue(geneProduct.getPackageVersion() == 2);
      Assert.assertTrue(geneProduct.getPackageName().equals(FBCConstants.shortLabel));
      Assert.assertTrue(geneProduct.getNamespace().equals(FBCConstants.namespaceURI_L3V1V2));      
    }
    
    Reaction r1 = m.createReaction("R1");
    
    FBCReactionPlugin fbcReaction = (FBCReactionPlugin) r1.getPlugin("fbc");
    
    GeneProductAssociation gpa = fbcReaction.createGeneProductAssociation("fbc_GPA1");
    
    Or association = new Or();
    association.addAssociation(new GeneProductRef("fbc_GPR1"));
    
    And and = new And();
    and.addAssociation(new GeneProductRef("fbc_GPR2"));
    and.addAssociation(new GeneProductRef("fbc_GPR3"));
    association.addAssociation(and);
    
    gpa.setAssociation(association);
    
    Assert.assertTrue(and.getPackageVersion() == 2);
    Assert.assertTrue(and.getPackageName().equals(FBCConstants.shortLabel));
    Assert.assertTrue(and.getNamespace().equals(FBCConstants.namespaceURI_L3V1V2));
    
    System.out.println("Checking packages for FBC v2 SBMLDocument:");
    PackageUtil.checkPackages(doc, false, false);
    System.out.println("Checking packages for FBC v2 SBMLDocument done.");
    
    
    SBMLDocument newDoc = new SBMLDocument(3, 1);
    Model clonedModel = m.clone();
    newDoc.setModel(clonedModel);

    Assert.assertFalse(newDoc.isPackageEnabled("fbc"));

    Assert.assertTrue(clonedModel.findNamedSBase("fbc_GPR2") != null);
    Assert.assertTrue(clonedModel.findNamedSBase("fbc_GPR2").getPackageVersion() == 2);
    Assert.assertTrue(clonedModel.findNamedSBase("fbc_GPR2").getNamespace().equals(FBCConstants.namespaceURI_L3V1V2));

    
    try {
      String docStr = new SBMLWriter().writeSBMLToString(newDoc);

      Assert.assertTrue(newDoc.isPackageEnabled("fbc"));

      SBMLDocument doc3 = new SBMLReader().readSBMLFromString(docStr);
      
      Assert.assertTrue(doc3.isPackageEnabled("fbc"));
      
      Model m3 = doc3.getModel();
      
      Assert.assertTrue(m3.findNamedSBase("fbc_GPR1") != null);
      Assert.assertTrue(m3.findNamedSBase("fbc_GPR1").getPackageVersion() == 2);
      Assert.assertTrue(m3.findNamedSBase("fbc_GPR1").getNamespace().equals(FBCConstants.namespaceURI_L3V1V2));
      
      Assert.assertTrue(m3.findNamedSBase("fbc_GPA1") != null);
      Assert.assertTrue(m3.findNamedSBase("fbc_GPA1").getPackageVersion() == 2);
      Assert.assertTrue(m3.findNamedSBase("fbc_GPA1").getNamespace().equals(FBCConstants.namespaceURI_L3V1V2));
      
      System.out.println("Checking packages for FBC v2 SBMLDocument cloned + read/write:");
      PackageUtil.checkPackages(doc3, false, false);
      System.out.println("Checking packages for FBC v2 SBMLDocument done.");

      
    } catch (SBMLException e) {
      e.printStackTrace();
    } catch (XMLStreamException e) {
      e.printStackTrace();
    }
  }


  /**
   * Checks that package version and namespace are set properly after cloning the Model element
   * only and writing the new SBMLDocument to String or XML.
   * 
   */
  @Test public void testModelCloning() {

    SBMLDocument newDoc = new SBMLDocument(3, 1);
    Model clonedModel = m.clone();
    newDoc.setModel(clonedModel);

    Assert.assertFalse(newDoc.isPackageEnabled("arrays"));
    Assert.assertFalse(newDoc.isPackageEnabled("comp"));
    Assert.assertFalse(newDoc.isPackageEnabled("distrib"));
    Assert.assertFalse(newDoc.isPackageEnabled("fbc"));
    Assert.assertFalse(newDoc.isPackageEnabled("qual"));
    Assert.assertFalse(newDoc.isPackageEnabled("req"));

    try {
      System.out.println(new SBMLWriter().writeSBMLToString(newDoc));
    } catch (SBMLException e) {
      e.printStackTrace();
    } catch (XMLStreamException e) {
      e.printStackTrace();
    }

    Assert.assertTrue(newDoc.isPackageEnabled("arrays"));
    Assert.assertTrue(newDoc.isPackageEnabled("comp"));
    Assert.assertTrue(newDoc.isPackageEnabled("distrib"));
    Assert.assertTrue(newDoc.isPackageEnabled("fbc"));
    Assert.assertTrue(newDoc.isPackageEnabled("groups"));
    Assert.assertTrue(newDoc.isPackageEnabled("dyn"));
    Assert.assertTrue(newDoc.isPackageEnabled("qual"));

    Assert.assertFalse(newDoc.isPackageEnabled("req"));
  }
}
