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

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.ext.comp.CompConstants;
import org.sbml.jsbml.ext.distrib.DistribConstants;
import org.sbml.jsbml.ext.distrib.util.DistribModelBuilder;
import org.sbml.jsbml.ext.fbc.FBCConstants;
import org.sbml.jsbml.ext.fbc.FBCModelPlugin;
import org.sbml.jsbml.ext.fbc.FluxBound;
import org.sbml.jsbml.ext.fbc.Objective;
import org.sbml.jsbml.ext.qual.QualConstants;
import org.sbml.jsbml.xml.parsers.PackageUtil;


@SuppressWarnings("deprecation")
public class PackageVersionTests {
  
  private SBMLDocument doc;
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
    
    doc.enablePackage(FBCConstants.namespaceURI_L3V1V1);
    doc.enablePackage(DistribConstants.namespaceURI_L3V1V1);
    doc.enablePackage(QualConstants.namespaceURI_L3V1V1);
    doc.enablePackage(CompConstants.namespaceURI_L3V1V1);
    
    m = doc.createModel("test");
    
    m.createReaction("R1");
    m.createSpecies("S1");
    m.createSpecies("S2");
    
    FBCModelPlugin fbcModel = (FBCModelPlugin) m.getPlugin("fbc");

    FluxBound fb1 = fbcModel.createFluxBound("fb1");
    fbcModel.createFluxBound("fb2");

    fb1.setPackageVersion(1);
    
    fbcModel.createObjective("fbc_O1");
    
    
    fb1.setReaction("R1");
    
    FunctionDefinition f = m.createFunctionDefinition("f");
    DistribModelBuilder.createDistribution(f, "NormalDistribution", new String[] { "mean", "stddev" }, new String[] {"avg", "sd"});
    
    // check and fix package version and namespaces
    // TODO - update when jsbml will be fixed to set properly package version and namespace - now the fix is done in SBMLCoreParser#processEndDocument
    PackageUtil.checkPackages(doc, false, true);
    
  }

  
  /**
   * Checks that package version and namespace are set properly for FBC version 1.
   */
  @Test public void testFbcPackageVersion() {

    
    FBCModelPlugin fbcModel = ((FBCModelPlugin) m.getPlugin("fbc"));
    ListOf<FluxBound> fluxBounds = fbcModel.getListOfFluxBounds();
    
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
}
