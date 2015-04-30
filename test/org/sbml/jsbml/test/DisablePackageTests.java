/*
 * $Id$
 * $URL$
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
import org.sbml.jsbml.LocalParameter;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBMLWriter;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.ext.comp.CompConstants;
import org.sbml.jsbml.ext.comp.CompModelPlugin;
import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.ext.layout.LayoutConstants;
import org.sbml.jsbml.ext.layout.LayoutModelPlugin;


/**
 * @author Nicolas Rodriguez
 * @version $Rev$
 * @since 1.0
 * @date 2014-06-17
 */
public class DisablePackageTests {

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
  @BeforeClass public static void initialSetUp() {}
  /**
   * 
   */
  @Before public void setUp() {
    doc = new SBMLDocument(3, 1);
    model = doc.createModel("model");

    CompModelPlugin compMainModel = (CompModelPlugin) model.getPlugin("comp");
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
  @Test
  public void testDisablePackage() {
    try {

      assertTrue(doc.getSBMLDocumentAttributes().containsKey("comp:required") == true);
      assertTrue(doc.getSBMLDocumentAttributes().containsKey("layout:required") == true);
      assertTrue(doc.getDeclaredNamespaces().containsKey("xmlns:layout") == true);
      assertTrue(doc.getDeclaredNamespaces().containsKey("xmlns:comp") == true);

      doc.disablePackage("comp");
      doc.disablePackage(LayoutConstants.namespaceURI_L3V1V1);

      assertTrue(doc.isPackageEnabled("comp") == false);
      assertTrue(doc.isPackageEnabled("layout") == false);
      assertTrue(doc.isPackageURIEnabled("comp") == false);
      assertTrue(doc.isPackageURIEnabled("layout") == false);
      assertTrue(doc.isPackageURIEnabled(CompConstants.namespaceURI_L3V1V1) == false);
      assertTrue(doc.isPackageURIEnabled(LayoutConstants.namespaceURI_L3V1V1) == false);

      assertTrue(doc.getSBMLDocumentAttributes().containsKey("comp:required") == false);
      assertTrue(doc.getSBMLDocumentAttributes().containsKey("layout:required") == false);
      assertTrue(doc.getDeclaredNamespaces().containsKey("xmlns:layout") == false);
      assertTrue(doc.getDeclaredNamespaces().containsKey("xmlns:comp") == false);

      String docWithoutPackageString = new SBMLWriter().writeSBMLToString(doc);

      // check that disabled package are still disabled after writing to XML
      assertTrue(doc.isPackageEnabled("comp") == false);
      assertTrue(doc.isPackageEnabled("layout") == false);

      // System.out.println("Document without package:\n" + docWithoutPakageString);

      SBMLDocument docWithoutPackage = new SBMLReader().readSBMLFromString(docWithoutPackageString);

      assertTrue(docWithoutPackage.isPackageEnabled("comp") == false);
      assertTrue(docWithoutPackage.isPackageEnabled("layout") == false);
      assertTrue(docWithoutPackage.isPackageURIEnabled("comp") == false);
      assertTrue(docWithoutPackage.isPackageURIEnabled("layout") == false);
      assertTrue(docWithoutPackage.isPackageEnabled(CompConstants.namespaceURI_L3V1V1) == false);
      assertTrue(docWithoutPackage.isPackageURIEnabled(CompConstants.namespaceURI_L3V1V1) == false);
      assertTrue(docWithoutPackage.isPackageURIEnabled(LayoutConstants.namespaceURI_L3V1V1) == false);

      assertTrue(docWithoutPackage.getSBMLDocumentAttributes().containsKey("comp:required") == false);
      assertTrue(docWithoutPackage.getSBMLDocumentAttributes().containsKey("layout:required") == false);
      assertTrue(docWithoutPackage.getDeclaredNamespaces().containsKey("xmlns:layout") == false);
      assertTrue(docWithoutPackage.getDeclaredNamespaces().containsKey("xmlns:comp") == false);

      assertTrue(docWithoutPackage.getModel().getExtensionCount() == 0);

      // enabling back the layout package
      doc.enablePackage("layout");

      assertTrue(doc.getSBMLDocumentAttributes().containsKey("layout:required") == true);
      assertTrue(doc.getDeclaredNamespaces().containsKey("xmlns:layout") == true);
      assertTrue(doc.isPackageEnabled("layout") == true);
      assertTrue(doc.isPackageURIEnabled("layout") == true);
      assertTrue(doc.isPackageURIEnabled("comp") == false);

      String docWitLayoutString = new SBMLWriter().writeSBMLToString(doc);

      // System.out.println("Document with only layout package:\n" + docWitLayoutString);

      SBMLDocument docWitLayout = new SBMLReader().readSBMLFromString(docWitLayoutString);

      assertTrue(docWitLayout.isPackageEnabled("comp") == false);
      assertTrue(docWitLayout.isPackageEnabled("layout") == true);
      assertTrue(docWitLayout.isPackageURIEnabled("comp") == false);
      assertTrue(docWitLayout.isPackageURIEnabled("layout") == true);
      assertTrue(docWitLayout.isPackageURIEnabled(CompConstants.namespaceURI_L3V1V1) == false);
      assertTrue(docWitLayout.isPackageURIEnabled(LayoutConstants.namespaceURI_L3V1V1) == true);

      assertTrue(docWitLayout.getSBMLDocumentAttributes().containsKey("comp:required") == false);
      assertTrue(docWitLayout.getSBMLDocumentAttributes().containsKey("layout:required") == true);
      assertTrue(docWitLayout.getDeclaredNamespaces().containsKey("xmlns:layout") == true);
      assertTrue(docWitLayout.getDeclaredNamespaces().containsKey("xmlns:comp") == false);

      assertTrue(docWitLayout.getModel().getExtensionCount() == 1);
      assertTrue(docWitLayout.getModel().isSetPlugin("layout"));
      assertTrue(docWitLayout.getModel().isSetPlugin(LayoutConstants.namespaceURI_L3V1V1));
      assertTrue(docWitLayout.getModel().getExtension("layout") != null);


    } catch (XMLStreamException e) {
      e.printStackTrace();
      assertTrue(false);
    }
  }

  /**
   * 
   */
  @Test public void testCloning() {

    SBMLDocument clonedDoc = new SBMLDocument(3, 1);
    clonedDoc.setModel(model.clone());

    // package not enable after cloning something other than the SBMLDocument
    assertTrue(clonedDoc.isPackageEnabled("comp") == false);
    assertTrue(clonedDoc.isPackageEnabled("layout") == false);

    String docString;
    try {
      docString = new SBMLWriter().writeSBMLToString(clonedDoc);

      // package are enabled after a call to the SBMLWriter write methods
      assertTrue(clonedDoc.isPackageEnabled("comp"));
      assertTrue(clonedDoc.isPackageEnabled("layout"));

      System.out.println("Document with only layout package:\n" + docString);

      SBMLDocument docWithoutPackNamespace = new SBMLReader().readSBMLFromString(docString);

      // package enabled automatically after reading a wrong sbml file
      assertTrue(docWithoutPackNamespace.isPackageEnabled("comp") == true);
      assertTrue(docWithoutPackNamespace.isPackageEnabled("layout") == true);

      assertTrue(docWithoutPackNamespace.getSBMLDocumentAttributes().containsKey("comp:required") == true);
      assertTrue(docWithoutPackNamespace.getSBMLDocumentAttributes().containsKey("layout:required") == true);
      assertTrue(docWithoutPackNamespace.getDeclaredNamespaces().containsKey("xmlns:layout") == true);
      assertTrue(docWithoutPackNamespace.getDeclaredNamespaces().containsKey("xmlns:comp") == true);

      assertTrue(docWithoutPackNamespace.getModel().getExtensionCount() == 2);
      assertTrue(docWithoutPackNamespace.getModel().isSetPlugin("layout"));
      assertTrue(docWithoutPackNamespace.getModel().isSetPlugin(LayoutConstants.namespaceURI_L3V1V1));
      assertTrue(docWithoutPackNamespace.getModel().getExtension("layout") != null);

      //      docString = new SBMLWriter().writeSBMLToString(docWithoutPackNamespace);
      //      System.out.println("Document with only layout package:\n" + docString);

    } catch (SBMLException e) {
      e.printStackTrace();
      assertTrue(false);
    } catch (XMLStreamException e) {
      e.printStackTrace();
      assertTrue(false);
    }

  }

}
