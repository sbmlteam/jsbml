/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2022 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.test;

import junit.framework.TestSuite;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.sbml.jsbml.ext.arrays.test.ArraysJUnitTests;
import org.sbml.jsbml.ext.comp.test.CompJUnitTests;
import org.sbml.jsbml.ext.dyn.test.TestL3Dyn;
import org.sbml.jsbml.ext.fbc.test.FbcJUnitTests;
import org.sbml.jsbml.ext.groups.test.GroupsJUnitTests;
import org.sbml.jsbml.ext.layout.test.LayoutJUnitTests;
import org.sbml.jsbml.ext.render.test.RenderJUnitTests;
import org.sbml.jsbml.xml.test.LibsbmlCompatibilityTests;
import org.sbml.jsbml.xml.test.Tests;

/**
 * JUnit {@link TestSuite} to regroup all the tests done for jsbml-core and the L3 packages.
 *
 * @author Nicolas Rodriguez
 * @since 1.0
 */
@RunWith(value=Suite.class)
@SuiteClasses(value={
  // core and packages Test suites
  Tests.class, CompJUnitTests.class, FbcJUnitTests.class, LayoutJUnitTests.class,
  ArraysJUnitTests.class, TestL3Dyn.class, RenderJUnitTests.class, GroupsJUnitTests.class,
  
  // other tests
  UnregisterPackageTests.class, TestSBaseHasExtension.class, DisablePackageTests.class,
  PackageVersionTests.class, UTF8Tests.class, LibsbmlCompatibilityTests.class, })
public class AllTests {

  /**
   * Sets the environment
   */
  @BeforeClass public static void setUp() {}

}
