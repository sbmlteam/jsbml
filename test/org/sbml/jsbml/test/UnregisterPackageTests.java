/*
 * $Id: UnregisterPackageTests.java 2168 2015-03-31 12:59:32Z niko-rodrigue $
 * $URL: svn://svn.code.sf.net/p/jsbml/code/trunk/test/org/sbml/jsbml/test/UnregisterPackageTests.java $
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2015 jointly by the following organizations:
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

import static org.junit.Assert.assertTrue;

import javax.xml.stream.XMLStreamException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.Constraint;
import org.sbml.jsbml.IdentifierException;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.LocalParameter;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBMLWriter;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.ext.AbstractSBasePlugin;
import org.sbml.jsbml.ext.arrays.ArraysConstants;
import org.sbml.jsbml.ext.arrays.ArraysSBasePlugin;
import org.sbml.jsbml.ext.arrays.Dimension;
import org.sbml.jsbml.ext.arrays.test.ArraysWriteTest;
import org.sbml.jsbml.ext.comp.CompConstants;
import org.sbml.jsbml.ext.comp.CompModelPlugin;
import org.sbml.jsbml.ext.comp.CompSBMLDocumentPlugin;
import org.sbml.jsbml.ext.comp.CompSBasePlugin;
import org.sbml.jsbml.ext.comp.ModelDefinition;
import org.sbml.jsbml.ext.comp.Port;
import org.sbml.jsbml.ext.comp.ReplacedBy;
import org.sbml.jsbml.ext.comp.ReplacedElement;
import org.sbml.jsbml.ext.comp.SBaseRef;
import org.sbml.jsbml.ext.comp.Submodel;
import org.sbml.jsbml.ext.fbc.FBCConstants;
import org.sbml.jsbml.ext.fbc.FBCModelPlugin;
import org.sbml.jsbml.ext.fbc.FBCSpeciesPlugin;
import org.sbml.jsbml.ext.fbc.FluxBound;
import org.sbml.jsbml.ext.fbc.FluxBound.Operation;
import org.sbml.jsbml.ext.fbc.Objective;
import org.sbml.jsbml.ext.groups.Group;
import org.sbml.jsbml.ext.groups.GroupsConstants;
import org.sbml.jsbml.ext.groups.GroupsModelPlugin;
import org.sbml.jsbml.ext.groups.Member;
import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.ext.layout.LayoutConstants;
import org.sbml.jsbml.ext.layout.LayoutModelPlugin;
import org.sbml.jsbml.ext.qual.QualConstants;
import org.sbml.jsbml.ext.qual.QualModelPlugin;
import org.sbml.jsbml.ext.qual.QualitativeSpecies;
import org.sbml.jsbml.ext.render.Image;
import org.sbml.jsbml.ext.render.LocalRenderInformation;
import org.sbml.jsbml.ext.render.LocalStyle;
import org.sbml.jsbml.ext.render.RenderConstants;
import org.sbml.jsbml.ext.render.RenderGroup;
import org.sbml.jsbml.ext.render.RenderLayoutPlugin;
import org.sbml.jsbml.ext.render.RenderListOfLayoutsPlugin;
import org.sbml.jsbml.ext.spatial.Geometry;
import org.sbml.jsbml.ext.spatial.SpatialConstants;
import org.sbml.jsbml.ext.spatial.SpatialModelPlugin;

/**
 * Tests the registration and un-registration of global or local id using
 * package elements.
 * 
 * @version $Rev: 2168 $
 * @since 1.0
 */
@SuppressWarnings("deprecation")
public class UnregisterPackageTests {

  /**
   * 
   */
  SBMLDocument doc;
  /**
   * 
   */
  Model model;
  /**
   * 
   */
  CompModelPlugin compMainModel;

  /**
   * 
   */
  @BeforeClass public static void initialSetUp() {}
  /**
   * 
   */
  @Before public void setUp() {
    doc = new SBMLDocument(3, 1);
    model = doc.createModel("model");

    compMainModel = (CompModelPlugin) model.getPlugin("comp");
    compMainModel.createSubmodel("submodel1");
    LayoutModelPlugin layoutModelPlugin =  (LayoutModelPlugin) model.getPlugin("layout");
    Layout layout = layoutModelPlugin.createLayout("layout1");
    layout.setMetaId("layout_metaid1");

    Compartment comp = model.createCompartment("cell");
    comp.setMetaId("cell");

    Species s1 = model.createSpecies("S1", comp);
    s1.setMetaId("S1");

    Species s2 = model.createSpecies("S2", comp);
    s2.setMetaId("S2");

    Reaction r1 = model.createReaction("R1");
    r1.setMetaId("R1");

    SpeciesReference reactant = model.createReactant("SP1");
    reactant.setMetaId("SP1");
    reactant.setSpecies(s1);

    SpeciesReference product = model.createProduct("SP2");
    product.setMetaId("SP2");
    product.setSpecies(s2);

    LocalParameter lp1 = r1.createKineticLaw().createLocalParameter("LP1");
    lp1.setMetaId("LP1");

    Constraint c1 = model.createConstraint();
    c1.setMetaId("c0");
  }



  /**
   * 
   */
  @Test public void testCompPort() {

    Species s3 = new Species();
    s3.setId("S3");
    model.addSpecies(s3);

    assertTrue(compMainModel.getPortCount() == 0);

    // create a Port with the same id as any existing one from the SIdRef scope.
    Port portS3 = null;
    try {
      portS3 = compMainModel.createPort("S3");
      assertTrue(true);
    } catch (IllegalArgumentException e) {
      assertTrue("We should be allowed to create a Port with the same id as a Species in the model", false);
    }

    assertTrue(compMainModel.getPortCount() == 1);

    // create a second Port with the same id as the first Port and check that this is not allowed
    try {
      compMainModel.createPort("S3");
      assertTrue("We should not be allowed to have several Port with the same id inside the same model", false);
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }

    assertTrue(compMainModel.getPortCount() == 1);

    // create a second Port with a different id. Create a core element with the same id and check that it is allowed
    Port portPo1 = null;
    try {
      portPo1 = compMainModel.createPort("Po1");
      assertTrue(true);

      model.createParameter("Po1");
      assertTrue(true);
    } catch (IllegalArgumentException e) {
      assertTrue("We should be allowed to create a core element with the same id as an existing Port", false);
    }

    assertTrue(compMainModel.getPortCount() == 2);

    assertTrue(compMainModel.getPort("Po1") != null);
    assertTrue(compMainModel.getPort("Po1").equals(portPo1));
    assertTrue(compMainModel.getPort("S3") != null);
    assertTrue(compMainModel.getPort("S3").equals(portS3));

    assertTrue(! portS3.equals(portPo1));

    CompSBMLDocumentPlugin compDoc = (CompSBMLDocumentPlugin) doc.getPlugin("comp");

    ModelDefinition modelDef = (ModelDefinition) compDoc.createModelDefinition("modelDef1");

    CompModelPlugin compModelDef = (CompModelPlugin) modelDef.getPlugin("comp");

    // create a ModelDefinition with a portId and id the same as in the main model
    compModelDef.createPort("S3");
    modelDef.createSpecies("S3");

    ModelDefinition modelDef2 = (ModelDefinition) compDoc.createModelDefinition("modelDef2");

    // modelDefinition id belong to the id namespace of the main model
    // TODO - add test for those when implementation is done
    // TODO - problem here, if a SBMLDocument is created without the main model element defined

    CompModelPlugin compModelDef2 = (CompModelPlugin) modelDef2.getPlugin("comp");

    // create a ModelDefinition with a portId and id the same as in the main model
    modelDef2.createSpecies("S3");
    compModelDef2.createPort("S3");

    assertTrue(modelDef.getSpecies("S3") != modelDef2.getSpecies("S3"));
    assertTrue(modelDef.getSpecies("S3").equals(modelDef2.getSpecies("S3")));

    // create a second Port with the same id as the first Port in a ModelDefinition and check that this is not allowed
    try {
      compModelDef2.createPort("S3");
      assertTrue("We should not be allowed to have several Port with the same id inside the same modelDefinition", false);
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }

    // create a second Species with the same id as the first Species in a ModelDefinition and check that this is not allowed
    try {
      modelDef2.createSpecies("S3");
      assertTrue("We should not be allowed to have several Species with the same id inside the same modelDefinition", false);
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }

    assertTrue(modelDef2.getSpeciesCount() == 1);
    assertTrue(compModelDef2.getPortCount() == 1);

    compModelDef2.createPort("test1");
    compModelDef2.createPort("test2");
    compModelDef2.createSubmodel("subM1");
    compModelDef2.createSubmodel("subM2");

    assertTrue(modelDef2.findUniqueNamedSBase("subM1") != null);
    assertTrue(model.findUniqueNamedSBase("subM1") == null);
    assertTrue(modelDef.findUniqueNamedSBase("subM1") == null);

    SBMLDocument clonedDoc = doc.clone();
    Model clonedModel = clonedDoc.getModel();
    CompModelPlugin compMainModelPluginCloned = (CompModelPlugin) clonedModel.getPlugin("comp");
    ModelDefinition clonedModelDef2 = ((CompSBMLDocumentPlugin) clonedDoc.getPlugin("comp")).getListOfModelDefinitions().get("modelDef2");
    CompModelPlugin clonedModelDef2Plugin = (CompModelPlugin) clonedModelDef2.getPlugin("comp");

    assertTrue(model.getSpeciesCount() == 3);

    assertTrue(compMainModelPluginCloned.getPortCount() == 2);
    assertTrue(clonedModel.getSpeciesCount() == 3);
    assertTrue(clonedModel.findUniqueNamedSBase("S2") != null);
    assertTrue(clonedModel.findUniqueNamedSBase("S3") != null);
    assertTrue(compMainModelPluginCloned.getPort(0).getId().equals("S3"));
    assertTrue(compMainModelPluginCloned.getPort("S3") != null);

    assertTrue(clonedModelDef2.findUniqueNamedSBase("subM1") != null);
    assertTrue(clonedModelDef2Plugin.getPortCount() == 3);
    assertTrue(clonedModelDef2Plugin.getPort("test1") != null);

    // Testing un-registration of Port
    compMainModel.removePort(0);

    assertTrue(compMainModel.getPortCount() == 1);
    assertTrue(compMainModel.getPort("Po1") != null);
    assertTrue(compMainModel.getPort("S3") == null);

    compMainModel.removePort(portPo1);

    assertTrue(compMainModel.getPortCount() == 0);
    assertTrue(compMainModel.getPort("Po1") == null);

    // Testing un-registration of SubModel

    compModelDef2.removeSubmodel("subM1");
    assertTrue(modelDef2.findUniqueNamedSBase("subM1") == null);

    compModelDef2.unsetListOfSubmodels();
    compModelDef2.unsetListOfPorts();
    assertTrue(modelDef2.findUniqueNamedSBase("subM2") == null);
    assertTrue(compModelDef2.getPortCount() == 0);
    assertTrue(compModelDef2.getPort("test1") == null);

  }

  /**
   * Tests the {@link ReplacedBy} class as it is using the method {@link AbstractSBasePlugin#firePropertyChange(String, Object, Object)}.
   * 
   */
  @Test public void testCompReplacedBy() {

    Species s1 = model.getSpecies(0);

    CompSBasePlugin compSpecies = (CompSBasePlugin) s1.getPlugin("comp");

    ReplacedBy replacedBy = compSpecies.createReplacedBy();

    // create an element with an existing metaid and check that this is not allowed
    try {
      replacedBy.setMetaId("S1");
      assertTrue("We should not be allowed to have several element with the same metaid inside the same SBMLDocument", false);
    } catch (IdentifierException e) {
      assertTrue(true);
    }

    replacedBy.setMetaId("CRB1");

    assertTrue(doc.findSBase("CRB1").equals(replacedBy));

    compSpecies.createReplacedElement();
    ReplacedElement replacedElement = compSpecies.createReplacedElement();
    replacedElement.setMetaId("CRE2");

    assertTrue(compSpecies.getReplacedElementCount() == 2);
    assertTrue(doc.findSBase("CRE2").equals(replacedElement));

    replacedBy.removeFromParent();

    assertTrue(compSpecies.isSetReplacedBy() == false);
    assertTrue(doc.findSBase("CRB1") == null);

    replacedElement.removeFromParent();

    assertTrue(compSpecies.getReplacedElementCount() == 1);
    assertTrue(doc.findSBase("CRE2") == null);
  }

  /**
   * 
   */
  @Test public void testKineticLaw() {

    Reaction r1 = model.getReaction(0);

    KineticLaw kl = r1.createKineticLaw();
    kl.setMetaId("KL1");

    assertTrue(r1.isSetKineticLaw() == true);
    assertTrue(doc.findSBase("KL1").equals(kl));

    kl.removeFromParent();

    assertTrue(r1.isSetKineticLaw() == false);
    assertTrue(doc.findSBase("KL1") == null);
  }

  /**
   * 
   */
  @Test public void testCompCloning() {

    assertTrue(model.findUniqueNamedSBase("layout1") != null);

    ListOf<Submodel> listOfSubmodels = compMainModel.getListOfSubmodels();
    listOfSubmodels.setMetaId("metaid_listOfSubmodels");

    Submodel subModel2 = compMainModel.createSubmodel("submodel2");
    subModel2.setMetaId("metaid_submodel2");

    LayoutModelPlugin clonedLayout = (LayoutModelPlugin) model.getPlugin("layout").clone();

    SBMLDocument clonedDoc = new SBMLDocument(3, 1);
    clonedDoc.setModel(model.clone());

    clonedDoc.getModel().unsetPlugin("layout");

    assertTrue(clonedDoc.getModel().findUniqueNamedSBase("layout1") == null);
    assertTrue(clonedDoc.findSBase("layout_metaid1") == null);

    clonedDoc.getModel().addPlugin("layout", clonedLayout);

    assertTrue(clonedDoc.findSBase("layout_metaid1") != null);
    assertTrue(clonedDoc.getModel().findUniqueNamedSBase("layout1") != null);

    // the SBasePlugin present is unset automatically in this case
    clonedDoc.getModel().addPlugin("layout", clonedLayout.clone());

    assertTrue(clonedDoc.findSBase("layout_metaid1") != null);
    assertTrue(clonedDoc.getModel().findUniqueNamedSBase("layout1") != null);

    // the package will be enabled automatically before writing
    assertTrue(!clonedDoc.isPackageEnabled("comp"));
    
    try {
      System.out.println(new SBMLWriter().writeSBMLToString(clonedDoc));
    } catch (SBMLException e) {
      e.printStackTrace();
    } catch (XMLStreamException e) {
      e.printStackTrace();
    }

    assertTrue(clonedDoc.isPackageEnabled("comp"));
  }


  /**
   * The Port 'id' is registered a first time in the CompSBasePlugin
   * when it is created. And a second time when the plugin is added
   * to the Model.
   * 
   */
  @Test public void testCompRegisteringTwice() {
    Reaction r2 = model.createReaction("R2");
    SpeciesReference ref = new SpeciesReference(3,1);
    ref.setId("SP3");
    ref.setSpecies("S1");
    CompSBasePlugin sBasePlugin = (CompSBasePlugin) ref.getPlugin(CompConstants.shortLabel);
    ReplacedBy replacedBy = sBasePlugin.createReplacedBy();
    replacedBy.setIdRef("S1");
    replacedBy.setMetaId("CMeta1");
    r2.addReactant(ref);

    assertTrue(doc.findSBase("CMeta1").equals(replacedBy));
    ref.unsetPlugin(CompConstants.shortLabel);
    assertTrue(doc.findSBase("CMeta1") == null);

    CompModelPlugin comp = new CompModelPlugin(model);

    Port port = comp.createPort();
    port.setId("c");
    port.setIdRef("s");
    port.setMetaId("CPortMeta1");
    SBaseRef sbaseRef = port.createSBaseRef();
    sbaseRef.setPortRef("S1");
    sbaseRef.setMetaId("CMeta1");
    SBaseRef sbaseRefRecursive = sbaseRef.createSBaseRef();
    sbaseRefRecursive.setMetaId("CMeta2");

    model.addExtension(CompConstants.namespaceURI, comp);

    assertTrue(doc.findSBase("CPortMeta1").equals(port));
    assertTrue(doc.findSBase("CMeta2").equals(sbaseRefRecursive));
    assertTrue(doc.findSBase("CMeta1").equals(sbaseRef));

    String docString = null;

    try {
      docString = new SBMLWriter().writeSBMLToString(doc);

      SBMLDocument docReadFromStr = new SBMLReader().readSBMLFromString(docString);

      assertTrue(docReadFromStr.findSBase("CMeta1").equals(sbaseRef));
      assertTrue(docReadFromStr.findSBase("CMeta2").equals(sbaseRefRecursive));
      assertTrue(docReadFromStr.findSBase("CPortMeta1").equals(port));

    } catch (SBMLException e) {
      e.printStackTrace();
      assertTrue(false);
    } catch (XMLStreamException e) {
      e.printStackTrace();
      assertTrue(false);
    }

    SBMLDocument clonedDoc = doc.clone();

    assertTrue(clonedDoc.findSBase("CPortMeta1") != null);
    assertTrue(clonedDoc.findSBase("CMeta1").equals(sbaseRef));
    assertTrue(clonedDoc.findSBase("CMeta2").equals(sbaseRefRecursive));

  }


  /**
   * 
   */
  @Test public void testFbc() {

    FBCModelPlugin fbcModel = (FBCModelPlugin) model.getPlugin(FBCConstants.namespaceURI_L3V1V1);

    FluxBound fluxBound1 = fbcModel.createFluxBound("FB1");
    fluxBound1.setMetaId("FB1");
    fluxBound1.setReaction("R1");
    fluxBound1.setOperation(FluxBound.Operation.GREATER_EQUAL);
    fluxBound1.setValue(800);

    fbcModel.createFluxBound("FB2");
    fbcModel.createFluxBound("FB3");
    fbcModel.createObjective("O1");
    fbcModel.createObjective("O2");

    assertTrue(fbcModel.getFluxBoundCount() == 3);
    assertTrue(fbcModel.getObjectiveCount() == 2);

    assertTrue(fbcModel.getFluxBound(0).getLevel() == 3);
    assertTrue(fbcModel.getFluxBound(0).getVersion() == 1);
    assertTrue(fbcModel.getListOfFluxBounds().getLevel() == 3);
    assertTrue(fbcModel.getListOfFluxBounds().getVersion() == 1);

    assertTrue(model.findUniqueNamedSBase("FB2") != null);
    assertTrue(model.findUniqueNamedSBase("O1") != null);

    assertTrue(doc.findSBase("FB1").equals(fluxBound1));

    Species s3 = model.createSpecies("S3");

    FBCSpeciesPlugin s3FbcPlugin = (FBCSpeciesPlugin) s3.getPlugin("fbc");
    s3FbcPlugin.setCharge(8);
    s3FbcPlugin.setChemicalFormula("H20");
    s3.setMetaId("S3");


    // trying to add a fluxbound/objective with an existing id, metaid
    try {
      fbcModel.createFluxBound("O1");
      assertTrue("We should not be allowed to have several element with the same id inside the same model", false);
    } catch (IllegalArgumentException e) {
      assertTrue(true);
      assertTrue(fbcModel.getFluxBoundCount() == 3);
    }

    try {
      FluxBound f4 = new FluxBound("FB4");
      f4.setMetaId("FB1");
      fbcModel.addFluxBound(f4);
      assertTrue("We should not be allowed to have several element with the same metaid inside the same model", false);
    } catch (IllegalArgumentException e) {
      assertTrue(true);
      assertTrue(fbcModel.getFluxBoundCount() == 3);
    }

    try {
      Objective o3 = new Objective("O3");
      o3.setMetaId("S2");
      fbcModel.addObjective(o3);
      assertTrue("We should not be allowed to have several element with the same metaid inside the same model", false);
    } catch (IllegalArgumentException e) {
      assertTrue(true);
      assertTrue(fbcModel.getObjectiveCount() == 2);
    }


    // Cloning the FBCModelPlugin
    FBCModelPlugin clonedFbcModel = fbcModel.clone();
    assertTrue(clonedFbcModel.getListOfFluxBounds().getLevel() == 3);
    assertTrue(clonedFbcModel.getListOfFluxBounds().getVersion() == 1);

    model.unsetPlugin("fbc");

    assertTrue(model.findUniqueNamedSBase("FB2") == null);
    assertTrue(model.findUniqueNamedSBase("O1") == null);
    assertTrue(doc.findSBase("FB1") == null);

    Model clonedModel = model.clone();
    clonedModel.addExtension("fbc", clonedFbcModel);

    SBMLDocument newDoc = new SBMLDocument(3, 1);
    newDoc.setModel(clonedModel);

    assertTrue(clonedFbcModel.getFluxBoundCount() == 3);
    assertTrue(clonedFbcModel.getObjectiveCount() == 2);

    assertTrue(clonedFbcModel.getFluxBound(0).getLevel() == 3);
    assertTrue(clonedFbcModel.getFluxBound(0).getVersion() == 1);
    assertTrue(clonedFbcModel.getListOfFluxBounds().getLevel() == 3);
    assertTrue(clonedFbcModel.getListOfFluxBounds().getVersion() == 1);

    assertTrue(clonedModel.findUniqueNamedSBase("FB2") != null);
    assertTrue(clonedModel.findUniqueNamedSBase("O1") != null);

    assertTrue(newDoc.findSBase("FB1").equals(fluxBound1));

    FluxBound clonedFluxBound1 = (FluxBound) newDoc.findSBase("FB1");

    assertTrue(clonedFluxBound1.getReaction().equals("R1"));
    clonedFluxBound1.setValue(550);
    assertTrue(!clonedFluxBound1.equals(fluxBound1));
    assertTrue(clonedFluxBound1.hashCode() != fluxBound1.hashCode());

    clonedFluxBound1.setReaction("R2");
    clonedFluxBound1.setOperation(Operation.EQUAL);

    assertTrue(clonedFluxBound1.getValue() == 550);
    assertTrue(fluxBound1.getValue() == 800);
    assertTrue(clonedFluxBound1.getReaction().equals("R2"));
    assertTrue(fluxBound1.getReaction().equals("R1"));
    assertTrue(clonedFluxBound1.getOperation().equals(Operation.EQUAL));
    assertTrue(fluxBound1.getOperation().equals(Operation.GREATER_EQUAL));


  }


  /**
   * Create a SBasePlugin by hand without extenddeSBase set, add it to an element and check that the ids are registered.
   * 
   */
  @Test public void testQual() {

    QualModelPlugin qualModel = new QualModelPlugin((Model) null);
    QualitativeSpecies qs1 = qualModel.createQualitativeSpecies("QS1");
    qs1.setMetaId("QS1");
    qs1.setInitialLevel(1);
    qs1.setMaxLevel(4);
    qs1.setCompartment(model.getCompartment(0));
    qs1.setConstant(false);

    qualModel.createQualitativeSpecies("QS2");
    qualModel.createQualitativeSpecies("QS3");
    qualModel.createQualitativeSpecies("QS4");

    qualModel.createTransition("T1");
    qualModel.createTransition("T2");

    model.addPlugin(QualConstants.shortLabel, qualModel);

    assertTrue(model.findUniqueNamedSBase("QS1").equals(qs1));
    assertTrue(model.findUniqueNamedSBase("QS3") != null);
    assertTrue(model.findUniqueNamedSBase("T2") != null);
    assertTrue(doc.findSBase("QS1") != null);

    // cloning the whole document
    SBMLDocument clonedDoc = doc.clone();
    Model clonedModel = clonedDoc.getModel();
    QualModelPlugin clonedQualModel = (QualModelPlugin) clonedModel.getExtension(QualConstants.shortLabel);

    assertTrue(clonedModel.isSetPlugin(QualConstants.shortLabel) == true);
    assertTrue(clonedModel.isSetPlugin(LayoutConstants.shortLabel) == true);
    assertTrue(clonedModel.isSetPlugin(GroupsConstants.shortLabel) == false);
    assertTrue(clonedModel.isSetPlugin(CompConstants.shortLabel) == true);
    assertTrue(clonedDoc.isPackageEnabled(QualConstants.shortLabel) == true);
    assertTrue(clonedDoc.isPackageEnabled(LayoutConstants.shortLabel) == true);
    assertTrue(clonedDoc.isPackageEnabled(GroupsConstants.shortLabel) == false);
    assertTrue(clonedDoc.isPackageEnabled(CompConstants.shortLabel) == true);

    assertTrue(clonedQualModel.getQualitativeSpeciesCount() == 4);
    assertTrue(clonedModel.findUniqueNamedSBase("QS1").equals(qs1));
    assertTrue(clonedModel.findUniqueNamedSBase("QS3") != null);
    assertTrue(clonedModel.findUniqueNamedSBase("T2") != null);
    assertTrue(clonedDoc.findSBase("QS1") != null);

    // test qualitativeSpecies clone method
    QualitativeSpecies clonedQs1 = clonedQualModel.getQualitativeSpecies("QS1");

    assertTrue(qs1.hashCode() == clonedQs1.hashCode());
    assertTrue(qs1.equals(clonedQs1));

    clonedQs1.setInitialLevel(0);
    assertTrue(qs1.hashCode() != clonedQs1.hashCode());
  }

  /**
   * 
   */
  @Test public void testGroups() {

    GroupsModelPlugin groupsModel = (GroupsModelPlugin) model.getPlugin(GroupsConstants.shortLabel);
    Group group = groupsModel.createGroup();
    group.setId("G1");

    // TODO - add it later when setId register and unregister the id - ((ListOfMemberConstraint) group.getListOfMemberConstraints()).setId("GLMC1");

    group.createMember("GM1");
    group.createMemberWithIdRef("GM2", "S2");
    Member member3 = group.createMemberWithIdRef("GM3", "S1");
    member3.setMetaId("GM3");
    group.createMemberConstraint("GMC1");

    assertTrue(groupsModel.getGroupCount() == 1);
    assertTrue(group.getMemberCount() == 3);
    assertTrue(group.getMemberConstraintCount() == 1);

    try {
      Group g2 = groupsModel.createGroup();
      g2.setId("G1");
      assertTrue("We should not be allowed to have several element with the same id inside the same model", false);
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
    try {
      groupsModel.createGroup("GM1");
      assertTrue("We should not be allowed to have several element with the same id inside the same model", false);
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
    try {
      groupsModel.createGroup("GMC1");
      assertTrue("We should not be allowed to have several element with the same id inside the same model", false);
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
    //    try {
    //      groupsModel.createGroup("GLMC1");
    //      assertTrue("We should not be allowed to have several element with the same id inside the same model", false);
    //    } catch (IllegalArgumentException e) {
    //        assertTrue(true);
    //    }
    assertTrue(groupsModel.getGroupCount() == 2);

    try {
      group.createMember("S1");
      assertTrue("We should not be allowed to have several element with the same id inside the same model", false);
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
    try {
      group.createMember("G1");
      assertTrue("We should not be allowed to have several element with the same id inside the same model", false);
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
    assertTrue(group.getMemberCount() == 3);

    SBMLDocument clonedDoc = doc.clone();
    Model clonedModel = clonedDoc.getModel();
    GroupsModelPlugin clonedGroupModelPlugin = (GroupsModelPlugin) clonedModel.getPlugin("groups");

    assertTrue(clonedGroupModelPlugin.getGroupCount() == 2);
    assertTrue(clonedDoc.findSBase("GM3") != null);
    assertTrue(clonedModel.findUniqueNamedSBase("GM3") != null);
    assertTrue(clonedModel.findUniqueNamedSBase("G1") != null);
    assertTrue(clonedModel.findUniqueNamedSBase("GMC1") != null);

  }

  /**
   * 
   */
  @Test public void testLayout() {

    LayoutModelPlugin layoutModel = (LayoutModelPlugin) model.getPlugin(LayoutConstants.shortLabel);
    Layout layout = layoutModel.createLayout("L1");

    layout.createCompartmentGlyph("LCG1");
    layout.createGeneralGlyph("LGG1");
    layout.createReactionGlyph("LRG1");
    layout.createSpeciesGlyph("LSG1");
    layout.createTextGlyph("LTG1");

    assertTrue(layoutModel.getLayoutCount() == 2);
    assertTrue(model.findUniqueNamedSBase("L1") != null);
    assertTrue(model.findUniqueNamedSBase("LCG1") != null);
    assertTrue(model.findUniqueNamedSBase("LGG1") != null);
    assertTrue(model.findUniqueNamedSBase("LRG1") != null);
    assertTrue(model.findUniqueNamedSBase("LSG1") != null);
    assertTrue(model.findUniqueNamedSBase("LTG1") != null);

    SBMLDocument clonedDoc = doc.clone();
    Model clonedModel = clonedDoc.getModel();

    assertTrue(clonedModel.findUniqueNamedSBase("LRG1") != null);
    assertTrue(clonedModel.findUniqueNamedSBase("L1") != null);
    assertTrue(clonedModel.findUniqueNamedSBase("submodel1") != null);


    layout.removeReactionGlyph(0);
    layout.removeSpeciesGlyph("LSG1");
    layout.removeTextGlyph(layout.getTextGlyph(0));

    assertTrue(model.findUniqueNamedSBase("LCG1") != null);
    assertTrue(model.findUniqueNamedSBase("LGG1") != null);
    assertTrue(model.findUniqueNamedSBase("LRG1") == null);
    assertTrue(model.findUniqueNamedSBase("LSG1") == null);
    assertTrue(model.findUniqueNamedSBase("LTG1") == null);

    assertTrue(clonedModel.findUniqueNamedSBase("LCG1") != null);
    assertTrue(clonedModel.findUniqueNamedSBase("LGG1") != null);
    assertTrue(clonedModel.findUniqueNamedSBase("LRG1") != null);
    assertTrue(clonedModel.findUniqueNamedSBase("LSG1") != null);
    assertTrue(clonedModel.findUniqueNamedSBase("LTG1") != null);

  }

  /**
   * 
   */
  @Test public void testRender() {

    LayoutModelPlugin layoutModel = (LayoutModelPlugin) model.getPlugin(LayoutConstants.shortLabel);
    Layout layout = layoutModel.createLayout("L1");

    RenderListOfLayoutsPlugin renderListOfLayoutsPlugin = (RenderListOfLayoutsPlugin) layoutModel.getListOfLayouts().getPlugin(RenderConstants.shortLabel);
    RenderLayoutPlugin renderLayoutPlugin = (RenderLayoutPlugin) layout.createPlugin(RenderConstants.shortLabel);

    renderListOfLayoutsPlugin.createGlobalRenderInformation("RGRI1");

    LocalRenderInformation lri1 = renderLayoutPlugin.createLocalRenderInformation("RLRI1");
    renderLayoutPlugin.createLocalRenderInformation("RLRI2");

    RenderGroup g1 = new RenderGroup("RGr1", 3, 1);
    g1.setFill("fillTest");
    g1.setStroke("testStroke");
    LocalStyle ls1 = new LocalStyle(3, 1, g1);
    ls1.setId("RLS1");
    lri1.addLocalStyle(ls1);
    g1.createRectangle().setX(4.5);
    g1.createText().setAbsoluteX(true);
    Image image = g1.createImage();
    image.setAbsoluteX(false);
    image.setX(33.);

    image.setMetaId("RI1");

    //    System.out.println("nb Child of LocalStyle 1 = " + ls1.getChildCount());
    //    System.out.println("Child 0 of LocalStyle 1 = " + ls1.getChildAt(0));
    //
    //    try {
    //      System.out.println(new SBMLWriter().writeSBMLToString(doc));
    //    } catch (SBMLException e) {
    //      // TODO Auto-generated catch block
    //      e.printStackTrace();
    //    } catch (XMLStreamException e) {
    //      // TODO Auto-generated catch block
    //      e.printStackTrace();
    //    }
    //

    assertTrue(model.findUniqueNamedSBase("L1") != null);
    assertTrue(model.findUniqueNamedSBase("RGRI1") != null);
    assertTrue(model.findUniqueNamedSBase("RLRI1") != null);
    assertTrue(model.findUniqueNamedSBase("RLS1") != null);
    assertTrue(model.findUniqueNamedSBase("RGr1") != null);
    assertTrue(doc.findSBase("RI1") != null);

    SBMLDocument clonedDoc = doc.clone();
    Model clonedModel = clonedDoc.getModel();

    assertTrue(clonedModel.findUniqueNamedSBase("L1") != null);
    assertTrue(clonedModel.findUniqueNamedSBase("RLRI1") != null);
    assertTrue(clonedModel.findUniqueNamedSBase("RGRI1") != null);
    assertTrue(clonedModel.findUniqueNamedSBase("RLS1") != null);
    assertTrue(clonedModel.findUniqueNamedSBase("RGr1") != null);


  }

  /**
   * 
   */
  @Test public void testSpatial() {
    SpatialModelPlugin spatialModel  = (SpatialModelPlugin) model.getPlugin(SpatialConstants.shortLabel);

    Geometry geometry = spatialModel.createGeometry();
    geometry.setMetaId("SG1");

    // TODO - create plenty of spatial new objects to be able to test their id or metaid

    assertTrue(geometry.getParent() != null);
    assertTrue(doc.findSBase("SG1").equals(geometry));
    // TODO - add some tests for the id and metaids created

    // TODO - add some tests trying to add a new element using an existing id or metaid.

    //    try {
    //      Group g2 = groupsModel.createGroup();
    //      g2.setId("G1");
    //      assertTrue("We should not be allowed to have several element with the same id inside the same model", false);
    //    } catch (IllegalArgumentException e) {
    //        assertTrue(true);
    //    }


    // cloning to test if the ids or metaids are still registered properly
    SpatialModelPlugin clonedSpatialModel  = spatialModel.clone();
    SBMLDocument newDoc = new SBMLDocument(3, 1);
    Model clonedModel = newDoc.createModel("clonedModel");
    clonedModel.addExtension(SpatialConstants.shortLabel, clonedSpatialModel);

    Geometry clonedGeometry = clonedSpatialModel.getGeometry();

    assertTrue(clonedGeometry.equals(geometry));
    assertTrue(clonedGeometry.getParent() != null); // parent should be set by the firePropertyChange method. id or metaid not registered at the moment when cloning

    assertTrue(newDoc.findSBase("SG1").equals(geometry));
    // TODO - add again the same tests for the id and metaids on the new SBMLDocument and Model
  }


  /**
   * 
   */
  @Test public void testMulti() {
    // TODO - when the package code is updated
  }

  /**
   * 
   */
  @Test public void testArrays() {

    ArraysSBasePlugin arraysTestPlugin = (ArraysSBasePlugin) model.getPlugin(ArraysConstants.shortLabel);

    Dimension d1 = arraysTestPlugin.createDimension("AD1");
    d1.setMetaId("AD1");

    assertTrue(model.findNamedSBase("AD1") == null);
    assertTrue(doc.findSBase("AD1").equals(d1));

    arraysTestPlugin = (ArraysSBasePlugin) model.getSpecies(0).getPlugin(ArraysConstants.shortLabel);

    arraysTestPlugin.createDimension("AD1");
    arraysTestPlugin.createDimension("AD2");

    try {
      arraysTestPlugin.createDimension("AD1");
      assertTrue("We should not be allowed to have several dimensions with the same id inside the same ArraysSBasePlugin", false);
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }

    Dimension d = arraysTestPlugin.createDimension();
    d.setMetaId("AD4");

    try {
      d.setId("AD2");
      assertTrue("We should not be allowed to have several dimensions with the same id inside the same ArraysSBasePlugin", false);
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }

    assertTrue(arraysTestPlugin.getDimensionCount() == 3);
    assertTrue(arraysTestPlugin.getDimension(2).isSetId() == false);
    assertTrue(doc.findSBase("AD4").equals(d));

    // TODO - add tests for Index as well
    // TODO - test cloning

    // Testing removeFromParent
    d1.removeFromParent();

    assertTrue(doc.findSBase("AD1") == null);

    d.removeFromParent();
    arraysTestPlugin.removeDimension("AD2");

    assertTrue(arraysTestPlugin.getDimensionCount() == 1);
    assertTrue(doc.findSBase("AD4") == null);

    arraysTestPlugin.getDimension(0).setMetaId("AD_metaid1");

    assertTrue(doc.findSBase("AD_metaid1") != null);

    arraysTestPlugin.removeFromParent();

    assertTrue(doc.findSBase("AD_metaid1") == null);

    assertTrue(model.getSpecies(0).getExtensionCount() == 0);

  }

  /**
   * The Dimension 'id' is registered a first time in the ArraysSBasePlugin
   * when it is created. And a second time when the SpeciesReference is added
   * to the Reaction.
   * 
   */
  @Test public void testArraysRegisteringTwice() {
    Reaction r2 = model.createReaction("R2");
    SpeciesReference ref = new SpeciesReference(3,1);
    ref.setId("SP3");
    ref.setSpecies("S1");
    ArraysSBasePlugin sBasePlugin = (ArraysSBasePlugin) ref.getPlugin(ArraysConstants.shortLabel);
    Dimension dimX = sBasePlugin.createDimension("rd0");
    dimX.setSize("n");
    dimX.setArrayDimension(0);
    dimX.setMetaId("ADMeta1");
    r2.addReactant(ref);

    assertTrue(doc.findSBase("ADMeta1").equals(dimX));
  }

  /**
   * 
   */
  @Test public void testArraysUnsetListOfDimensions() {

    SpeciesReference sp1 = (SpeciesReference) model.findNamedSBase("SP1");
    ArraysSBasePlugin arraysTestPlugin = (ArraysSBasePlugin) sp1.getPlugin(ArraysConstants.shortLabel);

    Dimension d1 = arraysTestPlugin.createDimension("AD1");
    d1.setMetaId("AD1");

    arraysTestPlugin.createDimension("AD2");

    assertTrue(model.findNamedSBase("AD1") == null);
    assertTrue(doc.findSBase("AD1").equals(d1));

    assertTrue(arraysTestPlugin.getDimensionCount() == 2);
    assertTrue(arraysTestPlugin.getNumDimensions() == 2);
    assertTrue(arraysTestPlugin.getDimension(0).isSetId() == true);

    arraysTestPlugin.unsetListOfDimensions();

    assertTrue(arraysTestPlugin.getDimensionCount() == 0);
    assertTrue(model.findNamedSBase("AD1") == null);
    assertTrue(doc.findSBase("AD1") == null);
    assertTrue(arraysTestPlugin.getDimension("AD1") == null);
    assertTrue(arraysTestPlugin.getDimension("AD2") == null);

    arraysTestPlugin.createDimension("AD1").setMetaId("AD1");;
    arraysTestPlugin.createDimension("AD2");
    arraysTestPlugin.createDimension("AD3");

    assertTrue(arraysTestPlugin.getDimensionCount() == 3);
    assertTrue(model.findNamedSBase("AD1") == null);
    assertTrue(doc.findSBase("AD1") != null);
    assertTrue(arraysTestPlugin.getDimension("AD1") != null);
    assertTrue(arraysTestPlugin.getDimension("AD2") != null);

  }

  /**
   * 
   */
  @Test
  public void testSetId() {
    try {
      SBMLDocument doc = SBMLReader.read(ArraysWriteTest.class.getResourceAsStream("/org/sbml/jsbml/xml/test/data/arrays/example.xml"));

      Model m = doc.getModel();

      m.setId("new_model_id");

      assertTrue(m.isSetId());
      assertTrue(m.getId().equals("new_model_id"));

      doc.getModel().setId("id_not_existing");

      assertTrue(m.getId().equals("id_not_existing"));

      // TODO - add tests for FunctionDefinition, Port, Deletion, ListOfMemberConstraints

    } catch (XMLStreamException e) {
      e.printStackTrace();
      assertTrue(false);
    }
  }
  
  // TODO - add some tests about distrib and fbc V2 elements

}
