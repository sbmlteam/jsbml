/*
 *
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2020 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 
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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.arrays.ArraysConstants;
import org.sbml.jsbml.ext.arrays.ArraysSBasePlugin;
import org.sbml.jsbml.ext.arrays.Dimension;
import org.sbml.jsbml.ext.comp.CompConstants;
import org.sbml.jsbml.ext.comp.CompModelPlugin;
import org.sbml.jsbml.ext.comp.Submodel;
import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.ext.layout.LayoutConstants;
import org.sbml.jsbml.ext.layout.LayoutModelPlugin;



/**
 * Tests for the {@link SBase} class, mainly the {@link SBase#hasExtension(String)} method.
 * 
 * @author Onur &Ouml;zel
 * @since 1.5
 */
public class TestSBaseHasExtension {

  /**
   * 
   */
  private SBase sbase;

  /**
   * @throws Exception
   */
  @Before public void setUp() throws Exception
  {
    sbase = new Model(2,4);
  }

  /**
   * @throws Exception
   */
  @After public void tearDown() throws Exception
  {
  }


  /**
   * Verifies behavior of checking the specific extensions.
   */
  @Test public void test_SBase_hasExtension() {
    
    CompModelPlugin compModel = (CompModelPlugin) sbase.getPlugin(CompConstants.shortLabel);
    LayoutModelPlugin layoutModel = (LayoutModelPlugin) sbase.getPlugin(LayoutConstants.shortLabel);
    Model sbase2 = new Model(1,1);
    ArraysSBasePlugin arraysModel = (ArraysSBasePlugin) sbase2.getPlugin(ArraysConstants.shortLabel);
    
    CompModelPlugin clonedCompModel = compModel.clone();
    Submodel sm1 = compModel.createSubmodel("submodel1");
    sm1.addExtension(CompConstants.shortLabel, clonedCompModel);
    
    LayoutModelPlugin clonedLayoutModel = layoutModel.clone();
    Layout layout = layoutModel.createLayout("layout1");
    layout.addExtension(LayoutConstants.shortLabel, clonedLayoutModel);
    
    ArraysSBasePlugin clonedArraysModel = arraysModel.clone();
    Dimension dim = arraysModel.createDimension();
    dim.addExtension(ArraysConstants.shortLabel, clonedArraysModel);
    
    assertTrue(sm1.hasExtension(CompConstants.shortLabel) == true);
    assertTrue(sm1.hasExtension(LayoutConstants.shortLabel) == false);
    assertTrue(sm1.hasExtension(ArraysConstants.shortLabel) == false);
    
    assertTrue(layout.hasExtension(CompConstants.shortLabel) == false);
    assertTrue(layout.hasExtension(LayoutConstants.shortLabel) == true);
    assertTrue(layout.hasExtension(ArraysConstants.shortLabel) == false);
    
    assertTrue(dim.hasExtension(CompConstants.shortLabel) == false);
    assertTrue(dim.hasExtension(LayoutConstants.shortLabel) == false);
    assertTrue(dim.hasExtension(ArraysConstants.shortLabel) == true);
  }

}

