/*
 * $Id: AllTests.java 2165 2015-03-30 16:11:24Z niko-rodrigue $
 * $URL: svn://svn.code.sf.net/p/jsbml/code/trunk/test/org/sbml/jsbml/test/AllTests.java $
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2015 jointly by the following organizations:
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
import org.sbml.jsbml.ext.dyn.test.TestL3Dyn;
import org.sbml.jsbml.ext.layout.test.LayoutJUnitTests;
import org.sbml.jsbml.ext.render.test.RenderJUnitTests;
import org.sbml.jsbml.math.test.RecursionTest;
import org.sbml.jsbml.xml.test.ASTNode2Tests;
import org.sbml.jsbml.xml.test.LibsbmlCompatibilityTests;
import org.sbml.jsbml.xml.test.Tests;

/**
 * JUnit {@link TestSuite} to regroup all the tests done for jsbml-core and the L3 packages.
 *
 * @author Nicolas Rodriguez
 * @version $Rev: 2165 $
 * @since 1.0
 */
@RunWith(value=Suite.class)
@SuiteClasses(value={Tests.class, LibsbmlCompatibilityTests.class, LayoutJUnitTests.class, RenderJUnitTests.class,
  UnregisterPackageTests.class, ArraysJUnitTests.class, DisablePackageTests.class, ASTNode2Tests.class, TestL3Dyn.class,
  PackageVersionTests.class, RecursionTest.class})
public class AllTests {

  /**
   * Sets the environment
   */
  @BeforeClass public static void setUp() {}

}
