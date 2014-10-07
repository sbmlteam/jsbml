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
import org.sbml.jsbml.ext.layout.test.LayoutJUnitTests;
import org.sbml.jsbml.ext.render.test.RenderJUnitTests;
import org.sbml.jsbml.xml.test.ASTNode2Tests;
import org.sbml.jsbml.xml.test.LibsbmlCompatibilityTests;
import org.sbml.jsbml.xml.test.Tests;


/**
 * Junit {@link TestSuite} to regroup all the tests done for jsbml-core and the L3 packages.
 *
 * @author Nicolas Rodriguez
 * @version $Rev$
 * @since 1.0
 */
@RunWith(value=Suite.class)
@SuiteClasses(value={Tests.class, LibsbmlCompatibilityTests.class, LayoutJUnitTests.class, RenderJUnitTests.class,
  UnregisterPackageTests.class, ArraysJUnitTests.class, DisablePackageTests.class, ASTNode2Tests.class})
public class AllTests {
  
  public static String DATA_FOLDER = null;

  static {

    DATA_FOLDER = "core/test/org/sbml/jsbml/xml/test/data";

    if (System.getProperty("DATA_FOLDER") != null || System.getenv("DATA_FOLDER") != null) {
      DATA_FOLDER = System.getProperty("DATA_FOLDER");
      if (DATA_FOLDER == null) {
        DATA_FOLDER = System.getenv("DATA_FOLDER");
      }
    } else {
      System.setProperty("DATA_FOLDER", DATA_FOLDER);
    }
  }

  /**
   * Sets the environment correctly so that the test files are found, even
   * when run through eclipse (with the standard eclipse setup, on trunk).
   * 
   */
  @BeforeClass public static void setUp() {

    if (System.getProperty("DATA_FOLDER") != null || System.getenv("DATA_FOLDER") != null) {
      DATA_FOLDER = System.getProperty("DATA_FOLDER");
      if (DATA_FOLDER == null) {
        DATA_FOLDER = System.getenv("DATA_FOLDER");
      }
    } else {
      System.setProperty("DATA_FOLDER", DATA_FOLDER);
    }

    System.out.println("AllTests - DATA_FOLDER SET to '" + DATA_FOLDER + "'");
  }
}
